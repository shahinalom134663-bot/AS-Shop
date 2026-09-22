package com.example.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.R
import com.example.data.model.Product
import com.example.ui.theme.BrandSky
import com.example.ui.theme.BrandTeal
import com.example.ui.theme.GoldEarn
import com.example.ui.theme.Slate100
import com.example.ui.theme.Slate500
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate900
import com.example.ui.viewmodel.VisualSearchUiState

@Composable
fun VisualSearchScreen(
    state: VisualSearchUiState,
    onPickImageUri: (Uri) -> Unit,
    onSampleSearch: (sampleKey: String, category: String, color: String, query: String) -> Unit,
    onResetSearch: () -> Unit,
    onProductClick: (Product) -> Unit,
    onBuyProduct: (Product) -> Unit
) {
    // Gallery picker launcher
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            onPickImageUri(uri)
        }
    }

    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .testTag("visual_search_screen"),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Header Card
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(BrandTeal.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.CameraAlt,
                                contentDescription = null,
                                tint = BrandTeal,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "ছবি দিয়ে সার্চ করুন",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Slate900
                            )
                            Text(
                                text = "AI Visual Product Recognition",
                                fontSize = 11.sp,
                                color = BrandTeal,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "ফেসবুকে দেখা কোনো প্রোডাক্টের স্ক্রিনশট বা ছবি দিয়ে ইনস্ট্যান্ট সার্চ করুন। আমাদের ভিজ্যুয়াল অ্যালগরিদম অনুরূপ প্রোডাক্ট খুঁজে বের করবে।",
                        fontSize = 12.sp,
                        color = Slate700,
                        lineHeight = 18.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Action buttons: Pick from Gallery or Capture Camera
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = { galleryLauncher.launch("image/*") },
                            modifier = Modifier
                                .weight(1f)
                                .height(44.dp)
                                .testTag("pick_gallery_image_button"),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = BrandTeal)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Image,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "গ্যালারি থেকে ছবি",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        OutlinedButton(
                            onClick = {
                                // Direct camera test with smartwatch preset
                                onSampleSearch("smartwatch", "স্মার্ট গ্যাজেটস", "Black", "watch amoled")
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(44.dp)
                                .testTag("capture_camera_button"),
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, BrandTeal)
                        ) {
                            Icon(
                                imageVector = Icons.Default.CameraAlt,
                                contentDescription = null,
                                tint = BrandTeal,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "ক্যামেরা ছবি",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = BrandTeal
                            )
                        }
                    }
                }
            }
        }

        // Quick Visual Search Presets
        item {
            Text(
                text = "অথবা নিচের স্যাম্পল ছবি দিয়ে টেস্ট করুন:",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Slate900
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                val samples = listOf(
                    Triple("স্মার্টওয়াচ", "watch smart bluetooth black", R.drawable.img_product_smartwatch),
                    Triple("লেদার হ্যান্ডব্যাগ", "bag handbag leather brown", R.drawable.img_product_handbag),
                    Triple("পাঞ্জাবি কালেকশন", "panjabi cotton white clothing", R.drawable.img_marketplace_hero),
                    Triple("গেমিং ইয়ারবাডস", "earbuds tws bluetooth black", R.drawable.img_app_icon)
                )

                items(samples) { (name, query, resId) ->
                    Card(
                        modifier = Modifier
                            .width(130.dp)
                            .clickable {
                                onSampleSearch(name, "", "", query)
                            }
                            .testTag("sample_search_$name"),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Slate100)
                    ) {
                        Column {
                            Image(
                                painter = painterResource(id = resId),
                                contentDescription = name,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(80.dp)
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = null,
                                    modifier = Modifier.size(14.dp),
                                    tint = BrandTeal
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = name,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    color = Slate900
                                )
                            }
                        }
                    }
                }
            }
        }

        // Searching / Scanning State Animation
        if (state.isAnalyzing) {
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = BrandTeal.copy(alpha = 0.08f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(70.dp)
                                .scale(pulseScale)
                                .clip(CircleShape)
                                .background(BrandTeal.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(50.dp),
                                color = BrandTeal,
                                strokeWidth = 4.dp
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = "ছবি বিশ্লেষণ করা হচ্ছে...",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = Slate900
                        )

                        Text(
                            text = "অবজেক্ট, টেক্সচার এবং কালার ম্যাচ করে মার্কেটপ্লেসে অনুসন্ধান চলছে",
                            fontSize = 12.sp,
                            color = Slate700
                        )
                    }
                }
            }
        }

        // Results Section
        if (state.hasSearched && !state.isAnalyzing) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = null,
                                tint = GoldEarn,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "শনাক্ত ফলাফল (${state.results.size}টি মিল পাওয়া গেছে)",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Slate900
                            )
                        }
                        if (state.detectedAttributes.isNotBlank()) {
                            Text(
                                text = state.detectedAttributes,
                                fontSize = 11.sp,
                                color = BrandTeal,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    Text(
                        text = "রিসেট",
                        fontSize = 12.sp,
                        color = BrandSky,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .clickable { onResetSearch() }
                            .padding(4.dp)
                    )
                }
            }

            if (state.results.isEmpty()) {
                item {
                    Text(
                        text = "এই ছবির সাথে হুবহু কোনো প্রোডাক্ট মেলেনি। অন্য ছবি ব্যবহার করুন।",
                        fontSize = 13.sp,
                        color = Slate500,
                        modifier = Modifier.padding(vertical = 20.dp)
                    )
                }
            } else {
                items(state.results) { matchResult ->
                    val prod = matchResult.product
                    ElevatedCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onProductClick(prod) }
                            .testTag("visual_result_card_${prod.id}"),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Product Image Thumbnail
                            Box(
                                modifier = Modifier
                                    .size(90.dp)
                                    .clip(RoundedCornerShape(12.dp))
                            ) {
                                if (prod.localDrawableRes != 0) {
                                    Image(
                                        painter = painterResource(id = prod.localDrawableRes),
                                        contentDescription = prod.title,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                } else {
                                    AsyncImage(
                                        model = prod.imageUrl,
                                        contentDescription = prod.title,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }

                                // Visual Match % badge
                                Surface(
                                    shape = RoundedCornerShape(bottomEnd = 8.dp),
                                    color = if (matchResult.matchScore >= 80) BrandTeal else GoldEarn,
                                    modifier = Modifier.align(Alignment.TopStart)
                                ) {
                                    Text(
                                        text = "${matchResult.matchScore}% মিল",
                                        color = Color.White,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            // Details
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = prod.banglaTitle.ifBlank { prod.title },
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis,
                                    color = Slate900
                                )

                                Spacer(modifier = Modifier.height(4.dp))

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "৳${prod.price.toInt()}",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = BrandTeal
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = prod.category,
                                        fontSize = 11.sp,
                                        color = Slate500
                                    )
                                }

                                Spacer(modifier = Modifier.height(4.dp))

                                // Feature tags
                                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                    matchResult.matchedFeatures.take(2).forEach { feature ->
                                        Surface(
                                            shape = RoundedCornerShape(4.dp),
                                            color = Slate100
                                        ) {
                                            Text(
                                                text = feature,
                                                fontSize = 10.sp,
                                                color = Slate700,
                                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                            )
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Button(
                                    onClick = { onBuyProduct(prod) },
                                    shape = RoundedCornerShape(8.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = BrandTeal),
                                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                                    modifier = Modifier.height(32.dp)
                                ) {
                                    Text(
                                        text = "অর্ডার করুন",
                                        fontSize = 11.sp,
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
}
