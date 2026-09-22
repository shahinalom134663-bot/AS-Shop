package com.example

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.MarketBottomNav
import com.example.ui.components.MarketTopBar
import com.example.ui.screens.AuthDialog
import com.example.ui.screens.CheckoutDialog
import com.example.ui.screens.DashboardScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.PostProductScreen
import com.example.ui.screens.ProductDetailDialog
import com.example.ui.screens.ReferEarnScreen
import com.example.ui.screens.VisualSearchScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.MarketplaceViewModel

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    androidx.compose.material3.Text(text = "Hello $name!", modifier = modifier)
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MarketBazaarApp()
            }
        }
    }
}

@Composable
fun MarketBazaarApp(
    viewModel: MarketplaceViewModel = viewModel()
) {
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    val currentScreen by viewModel.currentScreen.collectAsStateWithLifecycle()
    val products by viewModel.products.collectAsStateWithLifecycle()
    val myPosts by viewModel.myPosts.collectAsStateWithLifecycle()
    val userAccount by viewModel.userAccount.collectAsStateWithLifecycle()
    val referrals by viewModel.referrals.collectAsStateWithLifecycle()
    val withdrawals by viewModel.withdrawals.collectAsStateWithLifecycle()
    val orders by viewModel.orders.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()
    val visualSearchState by viewModel.visualSearchState.collectAsStateWithLifecycle()
    val checkoutState by viewModel.checkoutState.collectAsStateWithLifecycle()
    val showWithdrawDialog by viewModel.showWithdrawDialog.collectAsStateWithLifecycle()
    val showAuthDialog by viewModel.showAuthDialog.collectAsStateWithLifecycle()
    val selectedProduct by viewModel.selectedProduct.collectAsStateWithLifecycle()
    val toastMessage by viewModel.toastMessage.collectAsStateWithLifecycle()

    // Handle feedback toasts
    LaunchedEffect(toastMessage) {
        toastMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.clearToast()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            MarketTopBar(
                searchQuery = searchQuery,
                onSearchQueryChange = {
                    viewModel.setSearchQuery(it)
                    if (currentScreen != AppScreen.HOME) {
                        viewModel.navigateTo(AppScreen.HOME)
                    }
                },
                onVisualSearchClick = {
                    viewModel.navigateTo(AppScreen.VISUAL_SEARCH)
                },
                walletBalance = userAccount?.walletBalance ?: 350.0,
                onWalletClick = {
                    viewModel.navigateTo(AppScreen.REFER_EARN)
                },
                onProfileClick = {
                    viewModel.navigateTo(AppScreen.DASHBOARD)
                }
            )
        },
        bottomBar = {
            MarketBottomNav(
                currentScreen = currentScreen,
                onScreenSelect = { viewModel.navigateTo(it) }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentScreen) {
                AppScreen.HOME -> {
                    HomeScreen(
                        products = products,
                        userAccount = userAccount,
                        searchQuery = searchQuery,
                        selectedCategory = selectedCategory,
                        onCategorySelect = { viewModel.setCategory(it) },
                        onProductClick = { viewModel.selectProduct(it) },
                        onBuyProduct = { viewModel.startCheckout(it) },
                        onNavigate = { viewModel.navigateTo(it) },
                        onShowToast = { viewModel.showToast(it) }
                    )
                }
                AppScreen.VISUAL_SEARCH -> {
                    VisualSearchScreen(
                        state = visualSearchState,
                        onPickImageUri = { viewModel.runVisualSearchWithImageUri(it) },
                        onSampleSearch = { key, cat, col, query ->
                            viewModel.runVisualSearchWithSample(key, cat, col, query)
                        },
                        onResetSearch = { viewModel.resetVisualSearch() },
                        onProductClick = { viewModel.selectProduct(it) },
                        onBuyProduct = { viewModel.startCheckout(it) }
                    )
                }
                AppScreen.POST_PRODUCT -> {
                    PostProductScreen(
                        sellerPhoneDefault = userAccount?.phone ?: "01712345678",
                        onPostProduct = { title, banglaTitle, price, origPrice, cat, fbUrl, desc, phone, cond, loc, tags ->
                            viewModel.postFacebookMarketplaceProduct(
                                title = title,
                                banglaTitle = banglaTitle,
                                price = price,
                                originalPrice = origPrice,
                                category = cat,
                                fbUrl = fbUrl,
                                description = desc,
                                sellerPhone = phone,
                                condition = cond,
                                location = loc,
                                tags = tags
                            )
                        },
                        onShowToast = { viewModel.showToast(it) }
                    )
                }
                AppScreen.REFER_EARN -> {
                    ReferEarnScreen(
                        userAccount = userAccount,
                        referrals = referrals,
                        withdrawals = withdrawals,
                        showWithdrawDialog = showWithdrawDialog,
                        onOpenWithdrawDialog = { viewModel.openWithdrawDialog() },
                        onCloseWithdrawDialog = { viewModel.closeWithdrawDialog() },
                        onSubmitWithdraw = { method, account, amount ->
                            viewModel.submitWithdrawRequest(method, account, amount)
                        },
                        onSimulateReferral = { name, phone ->
                            viewModel.triggerSimulatedReferral(name, phone)
                        },
                        onShowToast = { viewModel.showToast(it) }
                    )
                }
                AppScreen.DASHBOARD -> {
                    DashboardScreen(
                        userAccount = userAccount,
                        orders = orders,
                        myPosts = myPosts,
                        withdrawals = withdrawals,
                        onOpenAuthDialog = { viewModel.toggleAuthDialog(true) },
                        onOpenWithdrawDialog = { viewModel.openWithdrawDialog() },
                        onNavigate = { viewModel.navigateTo(it) },
                        onShowToast = { viewModel.showToast(it) }
                    )
                }
            }
        }
    }

    // Product Detail Modal
    if (selectedProduct != null) {
        ProductDetailDialog(
            product = selectedProduct!!,
            onDismiss = { viewModel.selectProduct(null) },
            onBuyNow = {
                val prod = selectedProduct!!
                viewModel.selectProduct(null)
                viewModel.startCheckout(prod)
            },
            onShowToast = { viewModel.showToast(it) }
        )
    }

    // Checkout & Payment Gateway Modal
    if (checkoutState.isOpen && checkoutState.product != null) {
        CheckoutDialog(
            state = checkoutState,
            onDismiss = { viewModel.closeCheckout() },
            onUpdateForm = { qty, name, phone, addr, insideDhaka, method, trx ->
                viewModel.updateCheckoutForm(qty, name, phone, addr, insideDhaka, method, trx)
            },
            onConfirmOrder = { viewModel.confirmOrderPayment() },
            onViewOrders = {
                viewModel.closeCheckout()
                viewModel.navigateTo(AppScreen.DASHBOARD)
            }
        )
    }

    // Mobile Number Registration & Login Dialog
    if (showAuthDialog) {
        AuthDialog(
            currentUser = userAccount,
            onDismiss = { viewModel.toggleAuthDialog(false) },
            onRegisterSuccess = { name, phone, address, referralCode ->
                viewModel.registerOrLoginWithPhone(name, phone, address, referralCode)
            },
            onShowToast = { viewModel.showToast(it) }
        )
    }
}
