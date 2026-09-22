package com.example.ui.viewmodel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.R
import com.example.data.local.AppDatabase
import com.example.data.model.OrderRecord
import com.example.data.model.Product
import com.example.data.model.ReferralRecord
import com.example.data.model.UserAccount
import com.example.data.model.VisualMatchResult
import com.example.data.model.WithdrawRecord
import com.example.data.repository.MarketplaceRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class AppScreen {
    HOME,
    VISUAL_SEARCH,
    POST_PRODUCT,
    REFER_EARN,
    DASHBOARD
}

data class CheckoutState(
    val isOpen: Boolean = false,
    val product: Product? = null,
    val quantity: Int = 1,
    val customerName: String = "",
    val customerPhone: String = "",
    val deliveryAddress: String = "",
    val isInsideDhaka: Boolean = true,
    val paymentMethod: String = "bKash", // bKash, Nagad, Rocket, Cash on Delivery
    val transactionId: String = "",
    val isSubmitting: Boolean = false,
    val confirmedOrder: OrderRecord? = null
)

data class VisualSearchUiState(
    val isAnalyzing: Boolean = false,
    val selectedImageUri: Uri? = null,
    val selectedSampleKey: String = "",
    val detectedAttributes: String = "",
    val results: List<VisualMatchResult> = emptyList(),
    val hasSearched: Boolean = false
)

class MarketplaceViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MarketplaceRepository

    init {
        val db = AppDatabase.getDatabase(application)
        repository = MarketplaceRepository(db.appDao())
        viewModelScope.launch {
            repository.initDefaultDataIfEmpty()
        }
    }

    val products: StateFlow<List<Product>> = repository.allProducts
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val myPosts: StateFlow<List<Product>> = repository.myPostedProducts
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val userAccount: StateFlow<UserAccount?> = repository.userAccount
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val referrals: StateFlow<List<ReferralRecord>> = repository.allReferrals
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val withdrawals: StateFlow<List<WithdrawRecord>> = repository.allWithdrawals
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val orders: StateFlow<List<OrderRecord>> = repository.allOrders
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Navigation & Screen selection
    private val _currentScreen = MutableStateFlow(AppScreen.HOME)
    val currentScreen: StateFlow<AppScreen> = _currentScreen.asStateFlow()

    // Selected product for details view
    private val _selectedProduct = MutableStateFlow<Product?>(null)
    val selectedProduct: StateFlow<Product?> = _selectedProduct.asStateFlow()

    // Search and filter
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow("সকল")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    // Visual search state
    private val _visualSearchState = MutableStateFlow(VisualSearchUiState())
    val visualSearchState: StateFlow<VisualSearchUiState> = _visualSearchState.asStateFlow()

    // Checkout state
    private val _checkoutState = MutableStateFlow(CheckoutState())
    val checkoutState: StateFlow<CheckoutState> = _checkoutState.asStateFlow()

    // Withdraw Dialog state
    private val _showWithdrawDialog = MutableStateFlow(false)
    val showWithdrawDialog: StateFlow<Boolean> = _showWithdrawDialog.asStateFlow()

    // Feedback message (Toast / Snack)
    private val _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage: StateFlow<String?> = _toastMessage.asStateFlow()

    // Auth screen dialog
    private val _showAuthDialog = MutableStateFlow(false)
    val showAuthDialog: StateFlow<Boolean> = _showAuthDialog.asStateFlow()

    fun navigateTo(screen: AppScreen) {
        _currentScreen.value = screen
    }

    fun selectProduct(product: Product?) {
        _selectedProduct.value = product
        if (product != null) {
            viewModelScope.launch {
                repository.incrementViews(product.id)
            }
        }
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setCategory(category: String) {
        _selectedCategory.value = category
    }

    fun showToast(msg: String) {
        _toastMessage.value = msg
    }

    fun clearToast() {
        _toastMessage.value = null
    }

    // --- Visual Search Actions ---
    fun runVisualSearchWithSample(sampleKey: String, category: String, color: String, query: String) {
        viewModelScope.launch {
            _visualSearchState.value = _visualSearchState.value.copy(
                isAnalyzing = true,
                selectedSampleKey = sampleKey,
                selectedImageUri = null,
                detectedAttributes = "শনাক্ত: $category | রঙ: $color",
                hasSearched = true
            )
            delay(800) // Realistic image analysis simulation
            val results = repository.performVisualSearch(
                queryKeyword = query,
                detectedColor = color,
                detectedCategory = category,
                allProds = products.value
            )
            _visualSearchState.value = _visualSearchState.value.copy(
                isAnalyzing = false,
                results = results
            )
        }
    }

    fun runVisualSearchWithImageUri(uri: Uri) {
        viewModelScope.launch {
            _visualSearchState.value = _visualSearchState.value.copy(
                isAnalyzing = true,
                selectedImageUri = uri,
                selectedSampleKey = "custom_image",
                detectedAttributes = "ছবি বিশ্লেষণ করা হচ্ছে... অবজেক্ট সনাক্তকরণ সম্পন্ন!",
                hasSearched = true
            )
            delay(1200) // Analysis delay
            // Match against visual products
            val results = repository.performVisualSearch(
                queryKeyword = "smart watch gadget handbag fashion",
                detectedColor = "Black",
                detectedCategory = "স্মার্ট গ্যাজেটস",
                allProds = products.value
            )
            _visualSearchState.value = _visualSearchState.value.copy(
                isAnalyzing = false,
                detectedAttributes = "শনাক্ত ফলাফল: ফ্যাশন ও স্মার্ট গ্যাজেটস সামগ্রী",
                results = results
            )
        }
    }

    fun resetVisualSearch() {
        _visualSearchState.value = VisualSearchUiState()
    }

    // --- Authentication & Mobile Registration ---
    fun toggleAuthDialog(show: Boolean) {
        _showAuthDialog.value = show
    }

    fun registerOrLoginWithPhone(
        name: String,
        phone: String,
        address: String,
        referralCode: String
    ) {
        viewModelScope.launch {
            repository.registerOrUpdateUser(name, phone, address, referralCode)
            _showAuthDialog.value = false
            showToast("অভিনন্দন $name! আপনার একাউন্ট সফলভাবে সক্রিয় হয়েছে।")
        }
    }

    // --- Refer & Earn Actions ---
    fun triggerSimulatedReferral(friendName: String, friendPhone: String) {
        viewModelScope.launch {
            repository.applyReferralReward(friendName, friendPhone)
            showToast("দারুণ! আপনার রেফারে নতুন বন্ধু যুক্ত হয়েছে। ৳১০০ যোগ হয়েছে!")
        }
    }

    fun openWithdrawDialog() {
        _showWithdrawDialog.value = true
    }

    fun closeWithdrawDialog() {
        _showWithdrawDialog.value = false
    }

    fun submitWithdrawRequest(method: String, accountNumber: String, amount: Double) {
        viewModelScope.launch {
            val user = userAccount.value
            if (user == null || user.walletBalance < amount) {
                showToast("দুঃখিত! ওয়ালেটে পর্যাপ্ত ব্যালেন্স নেই।")
                return@launch
            }
            if (amount < 200.0) {
                showToast("সর্বনিম্ন উত্তোলনের পরিমাণ ৳২০০")
                return@launch
            }
            val success = repository.requestWithdraw(method, accountNumber, amount)
            if (success) {
                _showWithdrawDialog.value = false
                showToast("৳${amount.toInt()} $method নাম্বারে উত্তোলনের আবেদন সফল হয়েছে!")
            } else {
                showToast("উত্তোলন সম্পন্ন করা সম্ভব হয়নি। আবার চেষ্টা করুন।")
            }
        }
    }

    // --- Facebook Marketplace Product Posting ---
    fun postFacebookMarketplaceProduct(
        title: String,
        banglaTitle: String,
        price: Double,
        originalPrice: Double,
        category: String,
        fbUrl: String,
        description: String,
        sellerPhone: String,
        condition: String,
        location: String,
        tags: String
    ) {
        viewModelScope.launch {
            val user = userAccount.value
            val newProduct = Product(
                title = title,
                banglaTitle = if (banglaTitle.isNotBlank()) banglaTitle else title,
                price = price,
                originalPrice = if (originalPrice > price) originalPrice else (price * 1.3),
                category = category,
                imageUrl = "",
                localDrawableRes = when (category) {
                    "স্মার্ট গ্যাজেটস" -> R.drawable.img_product_smartwatch
                    "ফ্যাশন ও ব্যাগ" -> R.drawable.img_product_handbag
                    else -> R.drawable.img_marketplace_hero
                },
                description = description,
                fbMarketplaceUrl = if (fbUrl.isNotBlank()) fbUrl else "https://www.facebook.com/marketplace",
                sellerName = user?.name ?: "Market Seller",
                sellerPhone = if (sellerPhone.isNotBlank()) sellerPhone else (user?.phone ?: "01700000000"),
                condition = condition,
                location = location,
                tags = tags,
                visualCategory = category,
                visualColor = "Black",
                viewsCount = 1,
                isMyPost = true
            )
            repository.addProduct(newProduct)
            showToast("আপনার ফেসবুক মার্কেটপ্লেস প্রোডাক্টটি সফলভাবে পোস্ট হয়েছে!")
            _currentScreen.value = AppScreen.HOME
        }
    }

    // --- Checkout & Payment Gateway ---
    fun startCheckout(product: Product) {
        val user = userAccount.value
        _checkoutState.value = CheckoutState(
            isOpen = true,
            product = product,
            quantity = 1,
            customerName = user?.name ?: "",
            customerPhone = user?.phone ?: "",
            deliveryAddress = user?.address ?: "ঢাকা, বাংলাদেশ",
            isInsideDhaka = true,
            paymentMethod = "bKash"
        )
    }

    fun updateCheckoutForm(
        quantity: Int = _checkoutState.value.quantity,
        name: String = _checkoutState.value.customerName,
        phone: String = _checkoutState.value.customerPhone,
        address: String = _checkoutState.value.deliveryAddress,
        isInsideDhaka: Boolean = _checkoutState.value.isInsideDhaka,
        paymentMethod: String = _checkoutState.value.paymentMethod,
        trxId: String = _checkoutState.value.transactionId
    ) {
        _checkoutState.value = _checkoutState.value.copy(
            quantity = quantity,
            customerName = name,
            customerPhone = phone,
            deliveryAddress = address,
            isInsideDhaka = isInsideDhaka,
            paymentMethod = paymentMethod,
            transactionId = trxId
        )
    }

    fun closeCheckout() {
        _checkoutState.value = CheckoutState()
    }

    fun confirmOrderPayment() {
        val state = _checkoutState.value
        val prod = state.product ?: return
        if (state.customerPhone.isBlank()) {
            showToast("অনুগ্রহ করে আপনার মোবাইল নাম্বার লিখুন।")
            return
        }
        if (state.deliveryAddress.isBlank()) {
            showToast("অনুগ্রহ করে আপনার ডেলিভারি ঠিকানা লিখুন।")
            return
        }
        if (state.paymentMethod != "Cash on Delivery" && state.transactionId.isBlank()) {
            showToast("অনুগ্রহ করে ${state.paymentMethod} ট্রানজেকশন আইডি (TrxID) লিখুন।")
            return
        }

        viewModelScope.launch {
            _checkoutState.value = _checkoutState.value.copy(isSubmitting = true)
            delay(1000) // Payment processing simulation
            val order = repository.placeOrder(
                product = prod,
                quantity = state.quantity,
                paymentMethod = state.paymentMethod,
                trxId = state.transactionId,
                address = state.deliveryAddress,
                phone = state.customerPhone
            )
            _checkoutState.value = _checkoutState.value.copy(
                isSubmitting = false,
                confirmedOrder = order
            )
            showToast("অর্ডার সফল হয়েছে! অর্ডার নম্বর: ${order.orderNumber}")
        }
    }
}
