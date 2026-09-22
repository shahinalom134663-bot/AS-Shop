package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddBusiness
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.PriceCheck
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BrandSky
import com.example.ui.theme.BrandTeal
import com.example.ui.theme.FacebookBlue
import com.example.ui.theme.Slate100
import com.example.ui.theme.Slate500
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate900

@Composable
fun PostProductScreen(
    sellerPhoneDefault: String,
    onPostProduct: (
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
    ) -> Unit,
    onShowToast: (String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var banglaTitle by remember { mutableStateOf("") }
    var priceText by remember { mutableStateOf("") }
    var originalPriceText by remember { mutableStateOf("") }
    var fbUrl by remember { mutableStateOf("https://www.facebook.com/marketplace/item/") }
    var description by remember { mutableStateOf("") }
    var sellerPhone by remember { mutableStateOf(sellerPhoneDefault.ifBlank { "01712345678" }) }
    var location by remember { mutableStateOf("ঢাকা, বাংলাদেশ") }
    var tags by remember { mutableStateOf("") }

    val categories = listOf(
        "স্মার্ট গ্যাজেটস",
        "ফ্যাশন ও ব্যাগ",
        "পুরুষদের ফ্যাশন",
        "জুতো ও ফুটওয়্যার",
        "হোম ও লিভিং",
        "অন্যান্য"
    )
    var selectedCategory by remember { mutableStateOf(categories[0]) }

    val conditions = listOf("নতুন (Brand New)", "যেমন নতুন (Like New)", "ব্যবহৃত (Used)")
    var selectedCondition by remember { mutableStateOf(conditions[0]) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .testTag("post_product_screen"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .background(FacebookBlue, RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.AddBusiness,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = "ফেসবুক মার্কেটপ্লেস প্রোডাক্ট ছাড়ুন",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate900
                    )
                    Text(
                        text = "আপনার ফেসবুক পেজ বা মার্কেটপ্লেস প্রোডাক্ট লিস্টিং",
                        fontSize = 11.sp,
                        color = FacebookBlue,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        // Form Fields
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "প্রোডাক্টের প্রাথমিক তথ্য",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Slate900
                )

                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("প্রোডাক্টের নাম (ইংরেজিতে)") },
                    placeholder = { Text("e.g. Wireless Smart Watch Series 9") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("post_product_title_input"),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )

                OutlinedTextField(
                    value = banglaTitle,
                    onValueChange = { banglaTitle = it },
                    label = { Text("প্রোডাক্টের নাম (বাংলায়)") },
                    placeholder = { Text("যেমন: আল্ট্রা স্মার্ট ওয়াচ অ্যামোলেড ডিসপ্লে") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("post_product_bangla_title_input"),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedTextField(
                        value = priceText,
                        onValueChange = { priceText = it },
                        label = { Text("বিক্রয় মূল্য (৳)") },
                        placeholder = { Text("1450") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("post_product_price_input"),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true,
                        leadingIcon = {
                            Icon(Icons.Default.PriceCheck, contentDescription = null, tint = BrandTeal)
                        }
                    )

                    OutlinedTextField(
                        value = originalPriceText,
                        onValueChange = { originalPriceText = it },
                        label = { Text("আগের মূল্য (৳)") },
                        placeholder = { Text("2200") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("post_product_original_price_input"),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true
                    )
                }

                // Category Selection
                Text(
                    text = "ক্যাটাগরি নির্বাচন করুন:",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Slate700
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    categories.forEach { cat ->
                        val isSelected = selectedCategory == cat
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = if (isSelected) BrandTeal else Slate100,
                            modifier = Modifier
                                .clickable { selectedCategory = cat }
                                .testTag("post_cat_$cat")
                        ) {
                            Text(
                                text = cat,
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) Color.White else Slate700,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                    }
                }

                // Condition Selection
                Text(
                    text = "পণ্যের অবস্থা:",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Slate700
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    conditions.forEach { cond ->
                        val isSelected = selectedCondition == cond
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = if (isSelected) FacebookBlue else Slate100,
                            modifier = Modifier
                                .clickable { selectedCondition = cond }
                                .testTag("post_cond_$cond")
                        ) {
                            Text(
                                text = cond,
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) Color.White else Slate700,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                    }
                }

                // Facebook Marketplace URL
                OutlinedTextField(
                    value = fbUrl,
                    onValueChange = { fbUrl = it },
                    label = { Text("ফেসবুক মার্কেটপ্লেস লিংক (Facebook Listing URL)") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("post_product_fb_url_input"),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    leadingIcon = {
                        Icon(Icons.Default.Link, contentDescription = null, tint = FacebookBlue)
                    }
                )

                // Location and Phone
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedTextField(
                        value = location,
                        onValueChange = { location = it },
                        label = { Text("লোকেশন") },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("post_product_location_input"),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true,
                        leadingIcon = {
                            Icon(Icons.Default.LocationOn, contentDescription = null, tint = Slate500)
                        }
                    )

                    OutlinedTextField(
                        value = sellerPhone,
                        onValueChange = { sellerPhone = it },
                        label = { Text("যোগাযোগ ফোন") },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("post_product_phone_input"),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true,
                        leadingIcon = {
                            Icon(Icons.Default.Phone, contentDescription = null, tint = Slate500)
                        }
                    )
                }

                // Description
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("পণ্যের বিস্তারিত বিবরণ") },
                    placeholder = { Text("পণ্যের ফিচার, ওয়ারেন্টি এবং ডেলিভারি সংক্রান্ত বিস্তারিত তথ্য লিখুন...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .testTag("post_product_description_input"),
                    shape = RoundedCornerShape(12.dp),
                    maxLines = 4
                )

                // Tags for Visual Search
                OutlinedTextField(
                    value = tags,
                    onValueChange = { tags = it },
                    label = { Text("সার্চ ট্যাগস (কমা দিয়ে লিখুন)") },
                    placeholder = { Text("watch, gadget, smart, কালো, ঘড়ি") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("post_product_tags_input"),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    leadingIcon = {
                        Icon(Icons.Default.Tag, contentDescription = null, tint = Slate500)
                    }
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Submit Button
                Button(
                    onClick = {
                        if (title.isBlank()) {
                            onShowToast("অনুগ্রহ করে প্রোডাক্টের নাম লিখুন।")
                            return@Button
                        }
                        val price = priceText.toDoubleOrNull()
                        if (price == null || price <= 0) {
                            onShowToast("অনুগ্রহ করে সঠিক মূল্য লিখুন।")
                            return@Button
                        }
                        val origPrice = originalPriceText.toDoubleOrNull() ?: (price * 1.25)

                        onPostProduct(
                            title,
                            banglaTitle.ifBlank { title },
                            price,
                            origPrice,
                            selectedCategory,
                            fbUrl,
                            description.ifBlank { "ফেসবুক মার্কেটপ্লেসের প্রিমিয়াম কোয়ালিটি পণ্য।" },
                            sellerPhone,
                            selectedCondition,
                            location,
                            tags
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("submit_post_product_button"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = BrandTeal)
                ) {
                    Icon(
                        imageVector = Icons.Default.AddBusiness,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "মার্কেটপ্লেসে পোস্ট করুন",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
