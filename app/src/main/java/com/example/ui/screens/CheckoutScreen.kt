package com.example.ui.screens

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.R
import com.example.data.model.OrderRecord
import com.example.data.model.Product
import com.example.ui.theme.BkashPink
import com.example.ui.theme.BrandTeal
import com.example.ui.theme.NagadOrange
import com.example.ui.theme.RocketPurple
import com.example.ui.theme.Slate100
import com.example.ui.theme.Slate500
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate900
import com.example.ui.viewmodel.CheckoutState

@Composable
fun CheckoutDialog(
    state: CheckoutState,
    onDismiss: () -> Unit,
    onUpdateForm: (
        quantity: Int,
        name: String,
        phone: String,
        address: String,
        isInsideDhaka: Boolean,
        paymentMethod: String,
        trxId: String
    ) -> Unit,
    onConfirmOrder: () -> Unit,
    onViewOrders: () -> Unit
) {
    val prod = state.product ?: return

    // Order Success Confirmation Modal
    if (state.confirmedOrder != null) {
        OrderSuccessDialog(
            order = state.confirmedOrder,
            onDismiss = onDismiss,
            onViewOrders = onViewOrders
        )
        return
    }

    val deliveryFee = if (state.isInsideDhaka) 70.0 else 130.0
    val subtotal = prod.price * state.quantity
    val grandTotal = subtotal + deliveryFee

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .testTag("checkout_dialog"),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // Top Header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onDismiss) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = Slate900)
                    }

                    Text(
                        text = "চেকআউট ও পেমেন্ট গেটওয়ে",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Slate900
                    )

                    Spacer(modifier = Modifier.width(48.dp))
                }

                // Scrollable Form
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // Item Card with Quantity
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
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
                                if (prod.localDrawableRes != 0) {
                                    Image(
                                        painter = painterResource(id = prod.localDrawableRes),
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
                                    text = prod.banglaTitle,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = Slate900,
                                    maxLines = 1
                                )
                                Text(
                                    text = "একক মূল্য: ৳${prod.price.toInt()}",
                                    fontSize = 12.sp,
                                    color = BrandTeal,
                                    fontWeight = FontWeight.SemiBold
                                )

                                Spacer(modifier = Modifier.height(6.dp))

                                // Quantity selector
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = Slate100,
                                        modifier = Modifier
                                            .size(28.dp)
                                            .clickable {
                                                if (state.quantity > 1) {
                                                    onUpdateForm(
                                                        state.quantity - 1,
                                                        state.customerName,
                                                        state.customerPhone,
                                                        state.deliveryAddress,
                                                        state.isInsideDhaka,
                                                        state.paymentMethod,
                                                        state.transactionId
                                                    )
                                                }
                                            }
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Icon(Icons.Default.Remove, contentDescription = "Minus", modifier = Modifier.size(16.dp))
                                        }
                                    }

                                    Text(
                                        text = "${state.quantity}",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 12.dp)
                                    )

                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = Slate100,
                                        modifier = Modifier
                                            .size(28.dp)
                                            .clickable {
                                                onUpdateForm(
                                                    state.quantity + 1,
                                                    state.customerName,
                                                    state.customerPhone,
                                                    state.deliveryAddress,
                                                    state.isInsideDhaka,
                                                    state.paymentMethod,
                                                    state.transactionId
                                                )
                                            }
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Icon(Icons.Default.Add, contentDescription = "Plus", modifier = Modifier.size(16.dp))
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // Delivery Address Form
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Text(
                                text = "ডেলিভারি সংক্রান্ত তথ্য",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = Slate900
                            )

                            OutlinedTextField(
                                value = state.customerName,
                                onValueChange = {
                                    onUpdateForm(
                                        state.quantity,
                                        it,
                                        state.customerPhone,
                                        state.deliveryAddress,
                                        state.isInsideDhaka,
                                        state.paymentMethod,
                                        state.transactionId
                                    )
                                },
                                label = { Text("আপনার নাম") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("checkout_name_input"),
                                shape = RoundedCornerShape(10.dp),
                                singleLine = true,
                                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = Slate500) }
                            )

                            OutlinedTextField(
                                value = state.customerPhone,
                                onValueChange = {
                                    onUpdateForm(
                                        state.quantity,
                                        state.customerName,
                                        it,
                                        state.deliveryAddress,
                                        state.isInsideDhaka,
                                        state.paymentMethod,
                                        state.transactionId
                                    )
                                },
                                label = { Text("মোবাইল নাম্বার") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("checkout_phone_input"),
                                shape = RoundedCornerShape(10.dp),
                                singleLine = true,
                                leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null, tint = Slate500) }
                            )

                            OutlinedTextField(
                                value = state.deliveryAddress,
                                onValueChange = {
                                    onUpdateForm(
                                        state.quantity,
                                        state.customerName,
                                        state.customerPhone,
                                        it,
                                        state.isInsideDhaka,
                                        state.paymentMethod,
                                        state.transactionId
                                    )
                                },
                                label = { Text("পূর্ণাঙ্গ ঠিকানা (বাসা/রোড/এলাকা)") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("checkout_address_input"),
                                shape = RoundedCornerShape(10.dp),
                                maxLines = 2,
                                leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null, tint = Slate500) }
                            )

                            // Delivery location selection
                            Text(
                                text = "ডেলিভারি এরিয়া:",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Slate700
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = if (state.isInsideDhaka) BrandTeal else Slate100,
                                    modifier = Modifier
                                        .weight(1f)
                                        .clickable {
                                            onUpdateForm(
                                                state.quantity,
                                                state.customerName,
                                                state.customerPhone,
                                                state.deliveryAddress,
                                                true,
                                                state.paymentMethod,
                                                state.transactionId
                                            )
                                        }
                                ) {
                                    Column(
                                        modifier = Modifier.padding(10.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            "ঢাকার ভেতরে",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 12.sp,
                                            color = if (state.isInsideDhaka) Color.White else Slate900
                                        )
                                        Text(
                                            "চার্জ ৳৭০ (২৪-৪৮ ঘণ্টা)",
                                            fontSize = 10.sp,
                                            color = if (state.isInsideDhaka) Color.White.copy(alpha = 0.8f) else Slate500
                                        )
                                    }
                                }

                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = if (!state.isInsideDhaka) BrandTeal else Slate100,
                                    modifier = Modifier
                                        .weight(1f)
                                        .clickable {
                                            onUpdateForm(
                                                state.quantity,
                                                state.customerName,
                                                state.customerPhone,
                                                state.deliveryAddress,
                                                false,
                                                state.paymentMethod,
                                                state.transactionId
                                            )
                                        }
                                ) {
                                    Column(
                                        modifier = Modifier.padding(10.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            "ঢাকার বাইরে",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 12.sp,
                                            color = if (!state.isInsideDhaka) Color.White else Slate900
                                        )
                                        Text(
                                            "চার্জ ৳১৩০ (২-৩ দিন)",
                                            fontSize = 10.sp,
                                            color = if (!state.isInsideDhaka) Color.White.copy(alpha = 0.8f) else Slate500
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Payment Gateways Section (bKash, Nagad, Rocket, COD)
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Text(
                                text = "পেমেন্ট গেটওয়ে নির্বাচন করুন",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = Slate900
                            )

                            val paymentMethods = listOf(
                                Triple("bKash", BkashPink, "01712-345678 (মার্চেন্ট পেমেন্ট)"),
                                Triple("Nagad", NagadOrange, "01812-345678 (মার্চেন্ট পেমেন্ট)"),
                                Triple("Rocket", RocketPurple, "01912-345678-9 (বিল পে)"),
                                Triple("Cash on Delivery", Slate700, "পণ্য হাতে পেয়ে নগদ মূল্য দিন")
                            )

                            paymentMethods.forEach { (method, color, desc) ->
                                val isSelected = state.paymentMethod == method
                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    border = BorderStroke(
                                        if (isSelected) 2.dp else 1.dp,
                                        if (isSelected) color else Color(0xFFE2E8F0)
                                    ),
                                    color = if (isSelected) color.copy(alpha = 0.07f) else MaterialTheme.colorScheme.surface,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            onUpdateForm(
                                                state.quantity,
                                                state.customerName,
                                                state.customerPhone,
                                                state.deliveryAddress,
                                                state.isInsideDhaka,
                                                method,
                                                state.transactionId
                                            )
                                        }
                                        .testTag("payment_method_$method")
                                ) {
                                    Row(
                                        modifier = Modifier.padding(10.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        RadioButton(
                                            selected = isSelected,
                                            onClick = {
                                                onUpdateForm(
                                                    state.quantity,
                                                    state.customerName,
                                                    state.customerPhone,
                                                    state.deliveryAddress,
                                                    state.isInsideDhaka,
                                                    method,
                                                    state.transactionId
                                                )
                                            },
                                            colors = RadioButtonDefaults.colors(selectedColor = color)
                                        )

                                        Spacer(modifier = Modifier.width(6.dp))

                                        Column {
                                            Text(
                                                text = method,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 13.sp,
                                                color = Slate900
                                            )
                                            Text(
                                                text = desc,
                                                fontSize = 11.sp,
                                                color = Slate500
                                            )
                                        }
                                    }
                                }
                            }

                            // If bKash, Nagad, Rocket: show Merchant instructions & TrxID field
                            if (state.paymentMethod != "Cash on Delivery") {
                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = Slate100,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(modifier = Modifier.padding(10.dp)) {
                                        Text(
                                            text = "${state.paymentMethod} পেমেন্ট নির্দেশিকা:",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 12.sp,
                                            color = Slate900
                                        )
                                        Text(
                                            text = "১. ${state.paymentMethod} অ্যাপ থেকে 'Payment' বা 'Send Money' করুন মোট ৳${grandTotal.toInt()} টাকা।",
                                            fontSize = 11.sp,
                                            color = Slate700
                                        )
                                        Text(
                                            text = "২. পেমেন্ট শেষে পাওয়া ট্রানজেকশন আইডি (TrxID) নিচে লিখে নিশ্চিত করুন।",
                                            fontSize = 11.sp,
                                            color = Slate700
                                        )
                                    }
                                }

                                OutlinedTextField(
                                    value = state.transactionId,
                                    onValueChange = {
                                        onUpdateForm(
                                            state.quantity,
                                            state.customerName,
                                            state.customerPhone,
                                            state.deliveryAddress,
                                            state.isInsideDhaka,
                                            state.paymentMethod,
                                            it
                                        )
                                    },
                                    label = { Text("${state.paymentMethod} TrxID (ট্রানজেকশন আইডি)") },
                                    placeholder = { Text("e.g. BK9X4M781Q") },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .testTag("checkout_trx_id_input"),
                                    shape = RoundedCornerShape(10.dp),
                                    singleLine = true
                                )
                            }
                        }
                    }

                    // Price Summary
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = Slate100),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("পণ্যের মূল্য (${state.quantity}টি)", fontSize = 12.sp, color = Slate700)
                                Text("৳${subtotal.toInt()}", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Slate900)
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("ডেলিভারি চার্জ", fontSize = 12.sp, color = Slate700)
                                Text("৳${deliveryFee.toInt()}", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Slate900)
                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("সর্বমোট প্রদেয় টাকা", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Slate900)
                                Text(
                                    text = "৳${grandTotal.toInt()}",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Black,
                                    color = BrandTeal
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Bottom Sticky Confirm Button
                Surface(
                    color = MaterialTheme.colorScheme.surface,
                    shadowElevation = 8.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Button(
                            onClick = onConfirmOrder,
                            enabled = !state.isSubmitting,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .testTag("confirm_order_button"),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = BrandTeal)
                        ) {
                            if (state.isSubmitting) {
                                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("পেমেন্ট যাচাই করা হচ্ছে...")
                            } else {
                                Icon(Icons.Default.CreditCard, contentDescription = null, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "অর্ডার ও পেমেন্ট নিশ্চিত করুন (৳${grandTotal.toInt()})",
                                    fontSize = 14.sp,
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

@Composable
fun OrderSuccessDialog(
    order: OrderRecord,
    onDismiss: () -> Unit,
    onViewOrders: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(54.dp)
                        .clip(CircleShape)
                        .background(BrandTeal),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Success",
                        tint = Color.White,
                        modifier = Modifier.size(36.dp)
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "অর্ডার সফল হয়েছে!",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Slate900
                )
            }
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = "অর্ডার নম্বর: ${order.orderNumber}",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 14.sp,
                    color = BrandTeal
                )
                Text(
                    text = "প্রোডাক্ট: ${order.productTitle}",
                    fontSize = 12.sp,
                    color = Slate700
                )
                Text(
                    text = "পরিশোধিত মূল্য: ৳${order.totalAmount.toInt()} (${order.paymentMethod})",
                    fontSize = 12.sp,
                    color = Slate700
                )
                if (order.transactionId.isNotBlank()) {
                    Text(
                        text = "TrxID: ${order.transactionId}",
                        fontSize = 11.sp,
                        color = Slate500
                    )
                }
                Text(
                    text = "ডেলিভারি ঠিকানা: ${order.deliveryAddress}",
                    fontSize = 11.sp,
                    color = Slate500
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "আমরা খুব দ্রুত আপনার ঠিকানায় পণ্যটি পৌঁছে দেব। ডেলিভারি সংক্রান্ত যেকোনো আপডেটের জন্য ড্যাশবোর্ড চেক করুন।",
                    fontSize = 11.sp,
                    color = Slate700
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onViewOrders,
                colors = ButtonDefaults.buttonColors(containerColor = BrandTeal)
            ) {
                Text("আমার অর্ডার দেখুন", fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = Slate100)
            ) {
                Text("ঠিক আছে", color = Slate900)
            }
        }
    )
}
