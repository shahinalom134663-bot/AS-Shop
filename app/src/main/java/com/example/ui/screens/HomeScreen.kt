package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.Product
import com.example.data.model.UserAccount
import com.example.ui.components.ProductCard
import com.example.ui.theme.BrandSky
import com.example.ui.theme.BrandTeal
import com.example.ui.theme.GoldEarn
import com.example.ui.theme.Slate500
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate900
import com.example.ui.viewmodel.AppScreen
import java.util.Locale

@Composable
fun HomeScreen(
    products: List<Product>,
    userAccount: UserAccount?,
    searchQuery: String,
    selectedCategory: String,
    onCategorySelect: (String) -> Unit,
    onProductClick: (Product) -> Unit,
    onBuyProduct: (Product) -> Unit,
    onNavigate: (AppScreen) -> Unit,
    onShowToast: (String) -> Unit
) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current

    val categories = listOf(
        "সকল",
        "স্মার্ট গ্যাজেটস",
        "ফ্যাশন ও ব্যাগ",
        "পুরুষদের ফ্যাশন",
        "জুতো ও ফুটওয়্যার",
        "হোম ও লিভিং"
    )

    val filteredProducts = products.filter { product ->
        val matchesCategory = (selectedCategory == "সকল" || product.category == selectedCategory)
        val query = searchQuery.trim().lowercase(Locale.ROOT)
        val matchesQuery = query.isEmpty() ||
                product.title.lowercase(Locale.ROOT).contains(query) ||
                product.banglaTitle.lowercase(Locale.ROOT).contains(query) ||
                product.tags.lowercase(Locale.ROOT).contains(query) ||
                product.category.lowercase(Locale.ROOT).contains(query)
        matchesCategory && matchesQuery
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .testTag("home_screen_grid"),
        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Hero Banner Item (Span full width)
        item(span = { GridItemSpan(2) }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("hero_banner_card"),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_marketplace_hero),
                        contentDescription = "Marketplace Hero",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                    // Gradient overlay
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    listOf(
                                        Color.Black.copy(alpha = 0.2f),
                                        Color.Black.copy(alpha = 0.85f)
                                    )
                                )
                            )
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = BrandTeal
                        ) {
                            Text(
                                text = "Facebook Marketplace & F-Commerce",
                                fontSize = 10.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "ফেসবুক মার্কেটপ্লেস সেরা প্রোডাক্ট কালেকশন",
                            color = Color.White,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 17.sp,
                            lineHeight = 22.sp
                        )

                        Text(
                            text = "ছবি দিয়ে সার্চ করুন অথবা রেফার করে টাকা ইনকাম করুন!",
                            color = Color(0xFFE2E8F0),
                            fontSize = 12.sp
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Button(
                                onClick = { onNavigate(AppScreen.VISUAL_SEARCH) },
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = BrandTeal),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                                modifier = Modifier.height(34.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CameraAlt,
                                    contentDescription = null,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("ছবি দিয়ে খুঁজুন", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }

                            Button(
                                onClick = { onNavigate(AppScreen.POST_PRODUCT) },
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                                modifier = Modifier.height(34.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AddCircle,
                                    contentDescription = null,
                                    tint = Slate900,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("প্রোডাক্ট ছাড়ুন", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Slate900)
                            }
                        }
                    }
                }
            }
        }

        // Visual Search Interactive Prompt Card
        item(span = { GridItemSpan(2) }) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                border = BorderStroke(1.dp, BrandTeal.copy(alpha = 0.3f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNavigate(AppScreen.VISUAL_SEARCH) }
                    .testTag("visual_search_promo_card")
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                            .background(BrandTeal),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.CameraAlt,
                            contentDescription = "Visual Search",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "ছবি দিয়ে নিমেষেই প্রোডাক্ট খুঁজুন!",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Slate900
                        )
                        Text(
                            text = "গ্যালারির যেকোনো ছবি বা ফটো আপলোড করলেই ম্যাচিং প্রোডাক্ট চলে আসবে।",
                            fontSize = 11.sp,
                            color = Slate700
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = BrandTeal,
                        modifier = Modifier.padding(start = 6.dp)
                    ) {
                        Text(
                            text = "ট্রাই করুন",
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
                        )
                    }
                }
            }
        }

        // Referral Earnings Teaser Card
        item(span = { GridItemSpan(2) }) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.tertiaryContainer,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNavigate(AppScreen.REFER_EARN) }
                    .testTag("refer_earn_promo_card")
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(GoldEarn),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.CardGiftcard,
                            contentDescription = "Gift",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "রেফার করে টাকা ইনকাম করুন!",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onTertiaryContainer
                            )
                        }
                        Text(
                            text = "প্রতি সফল রেফারে ইনস্ট্যান্ট ৳১০০ বোনাস। বিকাশ/নগদে সরাসরি ক্যাশআউট।",
                            fontSize = 11.sp,
                            color = Slate700
                        )
                    }

                    val code = userAccount?.referralCode ?: "MBZ8942"
                    OutlinedButton(
                        onClick = {
                            clipboardManager.setText(AnnotatedString(code))
                            onShowToast("রেফারেল কোড $code কপি করা হয়েছে!")
                        },
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, GoldEarn),
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                        modifier = Modifier.height(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ContentCopy,
                            contentDescription = "Copy",
                            tint = GoldEarn,
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = code,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = GoldEarn
                        )
                    }
                }
            }
        }

        // Category Filter Chips Row (Span full width)
        item(span = { GridItemSpan(2) }) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                categories.forEach { category ->
                    val isSelected = selectedCategory == category
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = if (isSelected) BrandTeal else MaterialTheme.colorScheme.surface,
                        border = if (isSelected) null else BorderStroke(1.dp, Color(0xFFE2E8F0)),
                        modifier = Modifier
                            .clickable { onCategorySelect(category) }
                            .testTag("category_chip_$category")
                    ) {
                        Text(
                            text = category,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) Color.White else Slate700,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                        )
                    }
                }
            }
        }

        // Section Title
        item(span = { GridItemSpan(2) }) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "মার্কেটপ্লেস প্রোডাক্টসমূহ (${filteredProducts.size})",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Slate900
                )

                Text(
                    text = "সব দেখুন",
                    fontSize = 12.sp,
                    color = BrandTeal,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.clickable { onCategorySelect("সকল") }
                )
            }
        }

        // Products Grid
        if (filteredProducts.isEmpty()) {
            item(span = { GridItemSpan(2) }) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 40.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.ShoppingBag,
                        contentDescription = null,
                        modifier = Modifier.size(50.dp),
                        tint = Slate500
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "কোনো প্রোডাক্ট পাওয়া যায়নি",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate700
                    )
                    Text(
                        text = "অন্য কোনো নাম বা ক্যাটাগরি দিয়ে সার্চ করে দেখুন।",
                        fontSize = 12.sp,
                        color = Slate500
                    )
                }
            }
        } else {
            items(filteredProducts, key = { it.id }) { product ->
                ProductCard(
                    product = product,
                    onCardClick = { onProductClick(product) },
                    onBuyClick = { onBuyProduct(product) },
                    onFbMarketplaceClick = {
                        try {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(product.fbMarketplaceUrl))
                            context.startActivity(intent)
                        } catch (e: Exception) {
                            onShowToast("লিংক: ${product.fbMarketplaceUrl}")
                        }
                    }
                )
            }
        }
    }
}
