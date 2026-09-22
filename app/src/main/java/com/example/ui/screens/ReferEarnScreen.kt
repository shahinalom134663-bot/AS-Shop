package com.example.ui.screens

import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ReferralRecord
import com.example.data.model.UserAccount
import com.example.data.model.WithdrawRecord
import com.example.ui.theme.BkashPink
import com.example.ui.theme.BrandSky
import com.example.ui.theme.BrandTeal
import com.example.ui.theme.GoldEarn
import com.example.ui.theme.NagadOrange
import com.example.ui.theme.RocketPurple
import com.example.ui.theme.Slate100
import com.example.ui.theme.Slate500
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate900
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ReferEarnScreen(
    userAccount: UserAccount?,
    referrals: List<ReferralRecord>,
    withdrawals: List<WithdrawRecord>,
    showWithdrawDialog: Boolean,
    onOpenWithdrawDialog: () -> Unit,
    onCloseWithdrawDialog: () -> Unit,
    onSubmitWithdraw: (method: String, accountNumber: String, amount: Double) -> Unit,
    onSimulateReferral: (friendName: String, friendPhone: String) -> Unit,
    onShowToast: (String) -> Unit
) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current

    val myCode = userAccount?.referralCode ?: "MBZ8942"
    val walletBalance = userAccount?.walletBalance ?: 350.0
    val totalEarned = userAccount?.totalEarned ?: 350.0

    var showSimulateModal by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .testTag("refer_earn_screen"),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Wallet Balance Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("referral_wallet_card"),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.horizontalGradient(
                                listOf(Color(0xFF0F766E), Color(0xFF0D9488), Color(0xFF14B8A6))
                            )
                        )
                        .padding(20.dp)
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(CircleShape)
                                        .background(Color.White.copy(alpha = 0.2f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.AccountBalanceWallet,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "রেফারেল ইনকাম ওয়ালেট",
                                    color = Color.White,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }

                            Surface(
                                shape = RoundedCornerShape(20.dp),
                                color = Color.White.copy(alpha = 0.2f)
                            ) {
                                Text(
                                    text = "ক্যাশআউটযোগ্য",
                                    color = Color.White,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = "৳${walletBalance.toInt()}",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.White
                        )

                        Text(
                            text = "মোট অর্জিত আয়: ৳${totalEarned.toInt()} | সফল রেফার: ${referrals.size} জন",
                            color = Color.White.copy(alpha = 0.85f),
                            fontSize = 12.sp
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Button(
                                onClick = onOpenWithdrawDialog,
                                modifier = Modifier
                                    .weight(1f)
                                    .height(42.dp)
                                    .testTag("withdraw_funds_button"),
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                            ) {
                                Text(
                                    text = "টাকা উত্তোলন করুন",
                                    color = BrandTeal,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp
                                )
                            }

                            Button(
                                onClick = { showSimulateModal = true },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(42.dp)
                                    .testTag("simulate_referral_button"),
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.25f))
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PersonAdd,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "টেস্ট রেফার (+৳১০০)",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }
            }
        }

        // Referral Code & Share Card
        item {
            ElevatedCard(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "আপনার ইউনিক রেফারেল কোড",
                        fontSize = 13.sp,
                        color = Slate500,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Slate100, RoundedCornerShape(12.dp))
                            .padding(horizontal = 14.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = myCode,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Black,
                            color = Slate900,
                            letterSpacing = 2.sp
                        )

                        Row {
                            Button(
                                onClick = {
                                    clipboardManager.setText(AnnotatedString(myCode))
                                    onShowToast("রেফারেল কোড $myCode কপি হয়েছে!")
                                },
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = BrandTeal),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                                modifier = Modifier.height(34.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ContentCopy,
                                    contentDescription = null,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("কপি", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            OutlinedButton(
                                onClick = {
                                    val shareText = "মার্কেটবাজার (MarketBazaar) অ্যাপে যোগ দিয়ে ফেসবুক মার্কেটপ্লেসের সেরা অফার উপভোগ করুন! আমার রেফারেল কোড $myCode ব্যবহার করলে আপনি পাবেন ৳৫০ অতিরিক্ত ওয়েলকাম বোনাস।"
                                    val intent = Intent(Intent.ACTION_SEND).apply {
                                        type = "text/plain"
                                        putExtra(Intent.EXTRA_TEXT, shareText)
                                    }
                                    context.startActivity(Intent.createChooser(intent, "রেফারেল কোড শেয়ার করুন"))
                                },
                                shape = RoundedCornerShape(8.dp),
                                border = BorderStroke(1.dp, BrandSky),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                                modifier = Modifier.height(34.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Share,
                                    contentDescription = null,
                                    tint = BrandSky,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("শেয়ার", fontSize = 12.sp, color = BrandSky, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }

        // How it works Steps
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "রেফার করে আয়ের সহজ নিয়ম:",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate900
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    val rules = listOf(
                        "১. বন্ধুদের ফেসবুক, মেসেঞ্জার বা হোয়াটসঅ্যাপে রেফারেল কোড পাঠান।",
                        "২. বন্ধু মোবাইল নাম্বার দিয়ে একাউন্ট করলেই আপনি পাবেন ৳১০০ ইনস্ট্যান্ট বোনাস।",
                        "৩. বন্ধু প্রথমবার যেকোনো প্রোডাক্ট অর্ডার করলে আপনি আরও ৳৫০ ক্যাশব্যাক পাবেন।",
                        "৪. ওয়ালেটে ৳২০০ হলেই বিকাশ (bKash) বা নগদে ক্যাশআউট করতে পারবেন।"
                    )

                    rules.forEach { rule ->
                        Row(
                            modifier = Modifier.padding(vertical = 4.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = BrandTeal,
                                modifier = Modifier
                                    .size(16.dp)
                                    .padding(top = 2.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = rule,
                                fontSize = 12.sp,
                                color = Slate700,
                                lineHeight = 18.sp
                            )
                        }
                    }
                }
            }
        }

        // Referral History
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "রেফারেল হিস্ট্রি (${referrals.size})",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Slate900
                )
            }
        }

        if (referrals.isEmpty()) {
            item {
                Text(
                    text = "এখনো কোনো বন্ধুকে রেফার করেননি। রেফারেল কোড শেয়ার করে আয় শুরু করুন!",
                    fontSize = 12.sp,
                    color = Slate500,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
            }
        } else {
            items(referrals) { ref ->
                val dateStr = SimpleDateFormat("dd MMM, hh:mm a", Locale.getDefault()).format(Date(ref.timestamp))
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
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(BrandTeal.copy(alpha = 0.15f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.People,
                                    contentDescription = null,
                                    tint = BrandTeal,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = ref.friendName,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = Slate900
                                )
                                Text(
                                    text = "${ref.friendPhone} • $dateStr",
                                    fontSize = 11.sp,
                                    color = Slate500
                                )
                            }
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "+৳${ref.rewardAmount.toInt()}",
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 14.sp,
                                color = BrandTeal
                            )
                            Text(
                                text = ref.status,
                                fontSize = 10.sp,
                                color = Slate700
                            )
                        }
                    }
                }
            }
        }

        // Withdrawals Section
        if (withdrawals.isNotEmpty()) {
            item {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "টাকা উত্তোলনের বিবরণী",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Slate900
                )
            }

            items(withdrawals) { w ->
                val dateStr = SimpleDateFormat("dd MMM, hh:mm a", Locale.getDefault()).format(Date(w.timestamp))
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
                                text = "${w.method} ক্যাশআউট (${w.accountNumber})",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = Slate900
                            )
                            Text(
                                text = dateStr,
                                fontSize = 11.sp,
                                color = Slate500
                            )
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "-৳${w.amount.toInt()}",
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

    // Withdraw Dialog (bKash / Nagad / Rocket)
    if (showWithdrawDialog) {
        WithdrawDialog(
            currentBalance = walletBalance,
            onDismiss = onCloseWithdrawDialog,
            onSubmit = onSubmitWithdraw
        )
    }

    // Simulate Referral Dialog
    if (showSimulateModal) {
        SimulateReferralDialog(
            onDismiss = { showSimulateModal = false },
            onConfirm = { name, phone ->
                onSimulateReferral(name, phone)
                showSimulateModal = false
            }
        )
    }
}

@Composable
fun WithdrawDialog(
    currentBalance: Double,
    onDismiss: () -> Unit,
    onSubmit: (method: String, accountNumber: String, amount: Double) -> Unit
) {
    var selectedMethod by remember { mutableStateOf("bKash") }
    var accountNumber by remember { mutableStateOf("") }
    var amountText by remember { mutableStateOf("200") }
    var errorMsg by remember { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "টাকা উত্তোলন করুন (Withdraw)",
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
                color = Slate900
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = "বর্তমান ব্যালেন্স: ৳${currentBalance.toInt()} (সর্বনিম্ন উত্তোলন ৳২০০)",
                    fontSize = 12.sp,
                    color = Slate700
                )

                // Method selector: bKash, Nagad, Rocket
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val methods = listOf("bKash", "Nagad", "Rocket")
                    methods.forEach { method ->
                        val isSelected = selectedMethod == method
                        val color = when (method) {
                            "bKash" -> BkashPink
                            "Nagad" -> NagadOrange
                            else -> RocketPurple
                        }
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = if (isSelected) color else Slate100,
                            modifier = Modifier
                                .weight(1f)
                                .clickable { selectedMethod = method }
                                .padding(vertical = 4.dp)
                        ) {
                            Text(
                                text = method,
                                color = if (isSelected) Color.White else Slate700,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp),
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = accountNumber,
                    onValueChange = { accountNumber = it },
                    label = { Text("$selectedMethod নাম্বার") },
                    placeholder = { Text("017XXXXXXXX") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    singleLine = true
                )

                OutlinedTextField(
                    value = amountText,
                    onValueChange = { amountText = it },
                    label = { Text("উত্তোলনের পরিমাণ (টাকা)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    singleLine = true
                )

                if (errorMsg != null) {
                    Text(
                        text = errorMsg ?: "",
                        color = Color.Red,
                        fontSize = 11.sp
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val amount = amountText.toDoubleOrNull()
                    if (amount == null || amount < 200) {
                        errorMsg = "সর্বনিম্ন উত্তোলনের পরিমাণ ৳২০০"
                        return@Button
                    }
                    if (amount > currentBalance) {
                        errorMsg = "আপনার ওয়ালেটে পর্যাপ্ত ব্যালেন্স নেই।"
                        return@Button
                    }
                    if (accountNumber.trim().length < 11) {
                        errorMsg = "সঠিক ১১ ডিজিটের মোবাইল নাম্বার লিখুন।"
                        return@Button
                    }
                    onSubmit(selectedMethod, accountNumber, amount)
                },
                colors = ButtonDefaults.buttonColors(containerColor = BrandTeal)
            ) {
                Text("উত্তোলন নিশ্চিত করুন", fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("বাতিল", color = Slate700)
            }
        }
    )
}

@Composable
fun SimulateReferralDialog(
    onDismiss: () -> Unit,
    onConfirm: (name: String, phone: String) -> Unit
) {
    var name by remember { mutableStateOf("রাকিব হাসান (Rakib)") }
    var phone by remember { mutableStateOf("01799${(10000..99999).random()}") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "সিমুলেট রেফারেল বোনাস",
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = "একটি কাল্পনিক বন্ধু রেজিস্ট্রেশন টেস্ট করুন এবং ওয়ালেটে ৳১০০ যোগ করে দেখুন:",
                    fontSize = 12.sp,
                    color = Slate700
                )

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("বন্ধুর নাম") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    singleLine = true
                )

                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("বন্ধুর মোবাইল নাম্বার") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    singleLine = true
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onConfirm(name, phone) },
                colors = ButtonDefaults.buttonColors(containerColor = GoldEarn)
            ) {
                Text("রেফার সম্পন্ন করুন (+৳১০০)", fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("বাতিল")
            }
        }
    )
}
