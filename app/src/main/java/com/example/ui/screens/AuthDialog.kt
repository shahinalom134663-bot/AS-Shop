package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.UserAccount
import com.example.ui.theme.BrandTeal
import com.example.ui.theme.GoldEarn
import com.example.ui.theme.Slate500
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate900

@Composable
fun AuthDialog(
    currentUser: UserAccount?,
    onDismiss: () -> Unit,
    onRegisterSuccess: (name: String, phone: String, address: String, referralCode: String) -> Unit,
    onShowToast: (String) -> Unit
) {
    var name by remember { mutableStateOf(currentUser?.name ?: "") }
    var phone by remember { mutableStateOf(currentUser?.phone ?: "") }
    var address by remember { mutableStateOf(currentUser?.address ?: "") }
    var friendReferralCode by remember { mutableStateOf("") }
    var otpStep by remember { mutableStateOf(false) }
    var otpCode by remember { mutableStateOf("4829") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(BrandTeal.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.PhoneAndroid,
                        contentDescription = null,
                        tint = BrandTeal,
                        modifier = Modifier.size(22.dp)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = if (!otpStep) "মোবাইল নাম্বার রেজিস্ট্রেশন" else "OTP কোড যাচাই করুন",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Slate900
                    )
                    Text(
                        text = "মার্কেটবাজার নিরাপদ একাউন্ট",
                        fontSize = 11.sp,
                        color = Slate500
                    )
                }
            }
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                if (!otpStep) {
                    Text(
                        text = "আপনার মোবাইল নাম্বার দিয়ে সহজে একাউন্ট খুলুন অথবা লগইন করুন। এতে আপনার অর্ডার ও রেফারেল ওয়ালেট সুরক্ষিত থাকবে।",
                        fontSize = 12.sp,
                        color = Slate700
                    )

                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("আপনার পূর্ণ নাম") },
                        placeholder = { Text("যেমন: শাহিন আলম") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("auth_name_input"),
                        shape = RoundedCornerShape(10.dp),
                        singleLine = true,
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = Slate500) }
                    )

                    OutlinedTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        label = { Text("মোবাইল নাম্বার (১১ ডিজিট)") },
                        placeholder = { Text("017XXXXXXXX") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("auth_phone_input"),
                        shape = RoundedCornerShape(10.dp),
                        singleLine = true,
                        leadingIcon = { Icon(Icons.Default.PhoneAndroid, contentDescription = null, tint = Slate500) }
                    )

                    OutlinedTextField(
                        value = address,
                        onValueChange = { address = it },
                        label = { Text("ডেলিভারি ঠিকানা") },
                        placeholder = { Text("হাউজ, রোড, এলাকা, জেলা") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("auth_address_input"),
                        shape = RoundedCornerShape(10.dp),
                        singleLine = true,
                        leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null, tint = Slate500) }
                    )

                    OutlinedTextField(
                        value = friendReferralCode,
                        onValueChange = { friendReferralCode = it },
                        label = { Text("বন্ধুর রেফারেল কোড (ঐচ্ছিক)") },
                        placeholder = { Text("যেমন: MBZ1234 (ওয়েলকাম বোনাস)") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("auth_referral_input"),
                        shape = RoundedCornerShape(10.dp),
                        singleLine = true,
                        leadingIcon = { Icon(Icons.Default.CardGiftcard, contentDescription = null, tint = GoldEarn) }
                    )
                } else {
                    Text(
                        text = "$phone নাম্বারে ৪-ডিজিটের একটি ওটিপি কোড পাঠানো হয়েছে। নিচে কোডটি নিশ্চিত করুন:",
                        fontSize = 12.sp,
                        color = Slate700
                    )

                    OutlinedTextField(
                        value = otpCode,
                        onValueChange = { otpCode = it },
                        label = { Text("OTP ভেরিফিকেশন কোড") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("auth_otp_input"),
                        shape = RoundedCornerShape(10.dp),
                        singleLine = true
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (!otpStep) {
                        if (name.isBlank()) {
                            onShowToast("অনুগ্রহ করে আপনার নাম লিখুন।")
                            return@Button
                        }
                        if (phone.trim().length < 11) {
                            onShowToast("সঠিক ১১ ডিজিটের মোবাইল নাম্বার লিখুন।")
                            return@Button
                        }
                        otpStep = true
                    } else {
                        if (otpCode.length < 4) {
                            onShowToast("৪ ডিজিটের সঠিক OTP লিখুন।")
                            return@Button
                        }
                        onRegisterSuccess(name, phone, address, friendReferralCode)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = BrandTeal),
                modifier = Modifier.testTag("auth_submit_button")
            ) {
                Text(
                    text = if (!otpStep) "ওটিপি পাঠান (Next)" else "লগইন সম্পন্ন করুন",
                    fontWeight = FontWeight.Bold
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("বাতিল", color = Slate700)
            }
        }
    )
}
