package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.AddBusiness
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.R
import com.example.data.model.OrderRecord
import com.example.data.model.Product
import com.example.data.model.UserAccount
import com.example.data.model.WithdrawRecord
import com.example.ui.theme.BkashPink
import com.example.ui.theme.BrandSky
import com.example.ui.theme.BrandTeal
import com.example.ui.theme.FacebookBlue
import com.example.ui.theme.GoldEarn
import com.example.ui.theme.NagadOrange
import com.example.ui.theme.RocketPurple
import com.example.ui.theme.Slate100
import com.example.ui.theme.Slate500
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate900
import com.example.ui.viewmodel.AppScreen
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun DashboardScreen(
    userAccount: UserAccount?,
    orders: List<OrderRecord>,
    myPosts: List<Product>,
    withdrawals: List<WithdrawRecord>,
    onOpenAuthDialog: () -> Unit,
    onOpenWithdrawDialog: () -> Unit,
    onNavigate: (AppScreen) -> Unit,
    onShowToast: (String) -> Unit
) {
    val context = LocalContext.current
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabTitles = listOf("আমার অর্ডার", "আমার পোস্ট", "উত্তোলন ও হিস্ট্রি")

    val userName = userAccount?.name ?: "গেস্ট ইউজার"
    val userPhone = userAccount?.phone ?: "017XXXXXXXX"
    val userAddress = userAccount?.address ?: "ঢাকা, বাংলাদেশ"
    val userReferralCode = userAccount?.referralCode ?: "MBZ8942"
    val walletBalance = userAccount?.walletBalance ?: 350.0

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .testTag("dashboard_screen"),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Profile Header Card
        item {
            ElevatedCard(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(54.dp)
                                    .clip(CircleShape)
                                    .background(BrandTeal),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(32.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = userName,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                        color = Slate900
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = "Verified",
                                        tint = BrandTeal,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                                Text(
                                    text = userPhone,
                                    fontSize = 12.sp,
                                    color = Slate500
                                )
                                Text(
                                    text = userAddress,
                                    fontSize = 11.sp,
                                    color = Slate700,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }

                        OutlinedButton(
                            onClick = onOpenAuthDialog,
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(1.dp, BrandTeal),
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                            modifier = Modifier.height(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = null,
                                tint = BrandTeal,
                                modifier = Modifier.size(12.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("এডিট", fontSize = 11.sp, color = BrandTeal, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Stats Grid Row (Wallet, Orders, Posts)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Stat 1: Wallet
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = MaterialTheme.colorScheme.tertiaryContainer,
                            modifier = Modifier
                                .weight(1f)
                                .clickable { onNavigate(AppScreen.REFER_EARN) }
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text(
                                    text = "ওয়ালেট",
                                    fontSize = 11.sp,
                                    color = Slate700
                                )
                                Text(
                                    text = "৳${walletBalance.toInt()}",
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = MaterialTheme.colorScheme.onTertiaryContainer
                                )
                            }
                        }

                        // Stat 2: Orders
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = Slate100,
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text(
                                    text = "মোট অর্ডার",
                                    fontSize = 11.sp,
                                    color = Slate700
                                )
                                Text(
                                    text = "${orders.size}টি",
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Slate900
                                )
                            }
                        }

                        // Stat 3: FB Posts
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = BrandSky.copy(alpha = 0.12f),
                            modifier = Modifier
                                .weight(1f)
                                .clickable { onNavigate(AppScreen.POST_PRODUCT) }
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text(
                                    text = "FB লিস্টিং",
                                    fontSize = 11.sp,
                                    color = BrandSky
                                )
                                Text(
                                    text = "${myPosts.size}টি",
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = BrandSky
                                )
                            }
                        }
                    }
                }
            }
        }

        // Tab Selector Row
        item {
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = BrandTeal,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                        color = BrandTeal
                    )
                }
            ) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = {
                            Text(
                                text = title,
                                fontSize = 12.sp,
                                fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Medium
                            )
                        }
                    )
                }
            }
        }

        // TAB 0: Orders List
        if (selectedTab == 0) {
            if (orders.isEmpty()) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 30.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.ReceiptLong,
                            contentDescription = null,
                            tint = Slate500,
                            modifier = Modifier.size(44.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "এখনো কোনো অর্ডার করেননি",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Slate700
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Button(
                            onClick = { onNavigate(AppScreen.HOME) },
                            colors = ButtonDefaults.buttonColors(containerColor = BrandTeal)
                        ) {
                            Text("প্রোডাক্ট ব্রাউজ করুন")
                        }
                    }
                }
            } else {
                items(orders) { order ->
                    val dateStr = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
                        .format(Date(order.timestamp))
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.ReceiptLong,
                                        contentDescription = null,
                                        tint = BrandTeal,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = order.orderNumber,
                                        fontWeight = FontWeight.ExtraBold,
                                        fontSize = 14.sp,
                                        color = Slate900
                                    )
                                }

                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = BrandTeal.copy(alpha = 0.12f)
                                ) {
                                    Text(
                                        text = order.orderStatus,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = BrandTeal,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(60.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                ) {
                                    if (order.localDrawableRes != 0) {
                                        Image(
                                            painter = painterResource(id = order.localDrawableRes),
                                            contentDescription = null,
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier.fillMaxSize()
                                        )
                                    } else {
                                        Image(
                                            painter = painterResource(id = R.drawable.img_marketplace_hero),
                                            contentDescription = null,
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier.fillMaxSize()
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.width(12.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = order.productTitle,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp,
                                        color = Slate900,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(
                                        text = "পরিমাণ: ${order.quantity}টি | মোট: ৳${order.totalAmount.toInt()}",
                                        fontSize = 12.sp,
                                        color = BrandTeal,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Text(
                                        text = "পেমেন্ট: ${order.paymentMethod} ${if (order.transactionId.isNotBlank()) "(Trx: ${order.transactionId})" else ""}",
                                        fontSize = 11.sp,
                                        color = Slate500
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "ডেলিভারি ঠিকানা: ${order.deliveryAddress} • $dateStr",
                                fontSize = 10.sp,
                                color = Slate500
                            )
                        }
                    }
                }
            }
        }

        // TAB 1: My Posted Facebook Marketplace Products
        if (selectedTab == 1) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "আপনার পোস্ট করা প্রোডাক্ট (${myPosts.size}টি)",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate900
                    )

                    Button(
                        onClick = { onNavigate(AppScreen.POST_PRODUCT) },
                        colors = ButtonDefaults.buttonColors(containerColor = BrandTeal),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                        modifier = Modifier.height(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.AddBusiness,
                            contentDescription = null,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("নতুন পোস্ট", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            if (myPosts.isEmpty()) {
                item {
                    Text(
                        text = "আপনি এখনো কোনো ফেসবুক মার্কেটপ্লেস প্রোডাক্ট পোস্ট করেননি।",
                        fontSize = 12.sp,
                        color = Slate500,
                        modifier = Modifier.padding(vertical = 20.dp)
                    )
                }
            } else {
                items(myPosts) { post ->
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(70.dp)
                                    .clip(RoundedCornerShape(8.dp))
                            ) {
                                if (post.localDrawableRes != 0) {
                                    Image(
                                        painter = painterResource(id = post.localDrawableRes),
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                } else {
                                    AsyncImage(
                                        model = post.imageUrl,
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = post.banglaTitle.ifBlank { post.title },
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    color = Slate900
                                )

                                Text(
                                    text = "৳${post.price.toInt()}",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = BrandTeal
                                )

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Visibility,
                                        contentDescription = null,
                                        tint = Slate500,
                                        modifier = Modifier.size(12.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "${post.viewsCount} ভিউ",
                                        fontSize = 10.sp,
                                        color = Slate500
                                    )
                                }
                            }

                            // View FB listing button
                            IconButton(
                                onClick = {
                                    try {
                                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.fbMarketplaceUrl))
                                        context.startActivity(intent)
                                    } catch (e: Exception) {
                                        onShowToast("FB লিংক: ${post.fbMarketplaceUrl}")
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.OpenInNew,
                                    contentDescription = "FB",
                                    tint = FacebookBlue
                                )
                            }
                        }
                    }
                }
            }
        }

        // TAB 2: Withdrawals & Gateways
        if (selectedTab == 2) {
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "সাপোর্টেড পেমেন্ট ও ক্যাশআউট গেটওয়ে",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Slate900
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        val gateways = listOf(
                            Triple("bKash (বিকাশ)", BkashPink, "মার্চেন্ট নাম্বার: 01712-345678"),
                            Triple("Nagad (নগদ)", NagadOrange, "মার্চেন্ট নাম্বার: 01812-345678"),
                            Triple("Rocket (রকেট)", RocketPurple, "মার্চেন্ট নাম্বার: 01912-345678-9")
                        )

                        gateways.forEach { (name, color, info) ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = color,
                                    modifier = Modifier.size(16.dp)
                                ) {}
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = name,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Slate900
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = info,
                                    fontSize = 11.sp,
                                    color = Slate500
                                )
                            }
                        }
                    }
                }
            }

            if (withdrawals.isEmpty()) {
                item {
                    Text(
                        text = "এখনো কোনো ক্যাশআউট রিকোয়েস্ট নেই। রেফার করে টাকা আয় করুন এবং ওয়ালেট থেকে ক্যাশআউট নিন!",
                        fontSize = 12.sp,
                        color = Slate500,
                        modifier = Modifier.padding(vertical = 10.dp)
                    )
                }
            } else {
                items(withdrawals) { w ->
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(
                                    text = "${w.method} ক্যাশআউট",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = Slate900
                                )
                                Text(
                                    text = "একাউন্ট: ${w.accountNumber}",
                                    fontSize = 11.sp,
                                    color = Slate500
                                )
                            }

                            Column(horizontalAlignment = Alignment.End) {
                                Text(
                                    text = "৳${w.amount.toInt()}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = Color.Red
                                )
                                Text(
                                    text = w.status,
                                    fontSize = 10.sp,
                                    color = BrandTeal,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
