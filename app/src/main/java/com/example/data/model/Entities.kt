package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val banglaTitle: String,
    val price: Double,
    val originalPrice: Double,
    val category: String,
    val imageUrl: String,
    val localDrawableRes: Int = 0,
    val description: String,
    val fbMarketplaceUrl: String = "",
    val sellerName: String = "Market Seller",
    val sellerPhone: String = "01700000000",
    val condition: String = "নতুন (Brand New)",
    val location: String = "ঢাকা, বাংলাদেশ",
    val tags: String = "",
    val visualCategory: String = "",
    val visualColor: String = "",
    val viewsCount: Int = 0,
    val isMyPost: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "user_account")
data class UserAccount(
    @PrimaryKey
    val id: Long = 1,
    val name: String,
    val phone: String,
    val address: String = "ঢাকা, বাংলাদেশ",
    val referralCode: String,
    val referredBy: String = "",
    val walletBalance: Double = 150.0, // Welcome bonus of ৳150
    val totalEarned: Double = 150.0,
    val totalReferredCount: Int = 0,
    val isLoggedIn: Boolean = false,
    val isSeller: Boolean = true,
    val createdTimestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "referrals")
data class ReferralRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val friendName: String,
    val friendPhone: String,
    val rewardAmount: Double = 100.0,
    val status: String = "সফল (Credited)",
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "withdrawals")
data class WithdrawRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val method: String, // bKash, Nagad, Rocket
    val accountNumber: String,
    val amount: Double,
    val status: String = "প্রসেসিং (Processing)",
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "orders")
data class OrderRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val orderNumber: String,
    val productId: Long,
    val productTitle: String,
    val productImage: String,
    val localDrawableRes: Int = 0,
    val quantity: Int = 1,
    val totalAmount: Double,
    val paymentMethod: String, // bKash, Nagad, Rocket, Cash on Delivery
    val transactionId: String = "",
    val deliveryAddress: String,
    val customerPhone: String,
    val orderStatus: String = "কনফার্মড (Confirmed)",
    val timestamp: Long = System.currentTimeMillis()
)

data class VisualMatchResult(
    val product: Product,
    val matchScore: Int,
    val matchedFeatures: List<String>
)
