package com.example.data.repository

import com.example.R
import com.example.data.local.AppDao
import com.example.data.model.OrderRecord
import com.example.data.model.Product
import com.example.data.model.ReferralRecord
import com.example.data.model.UserAccount
import com.example.data.model.VisualMatchResult
import com.example.data.model.WithdrawRecord
import kotlinx.coroutines.flow.Flow
import java.util.Locale
import kotlin.random.Random

class MarketplaceRepository(private val dao: AppDao) {

    val allProducts: Flow<List<Product>> = dao.getAllProducts()
    val myPostedProducts: Flow<List<Product>> = dao.getMyPostedProducts()
    val userAccount: Flow<UserAccount?> = dao.getUserAccount()
    val allReferrals: Flow<List<ReferralRecord>> = dao.getAllReferrals()
    val allWithdrawals: Flow<List<WithdrawRecord>> = dao.getAllWithdrawals()
    val allOrders: Flow<List<OrderRecord>> = dao.getAllOrders()

    suspend fun initDefaultDataIfEmpty() {
        if (dao.getProductCount() == 0) {
            val initialProducts = listOf(
                Product(
                    title = "Ultra Watch Series 9 (Amoled Display & Calling)",
                    banglaTitle = "আল্ট্রা স্মার্ট ওয়াচ সিরিজ ৯ (অ্যামোলেড ডিসপ্লে ও ব্লুটুথ কলিং)",
                    price = 1450.0,
                    originalPrice = 2200.0,
                    category = "স্মার্ট গ্যাজেটস",
                    imageUrl = "",
                    localDrawableRes = R.drawable.img_product_smartwatch,
                    description = "অরিজিনাল মেটাল কেসিং, ওয়্যারলেস চার্জার, রিয়েল হার্ট রেট সেন্সর, ফুল ওয়াটার রেজিস্ট্যান্ট ও ৭ দিনের দীর্ঘ ব্যাটারি ব্যাকআপ। ফেসবুক মার্কেটপ্লেসে বেস্ট সেলার প্রোডাক্ট!",
                    fbMarketplaceUrl = "https://www.facebook.com/marketplace/item/109283746581923",
                    sellerName = "শাহীনা টেক হাব (Shahin Tech Hub)",
                    sellerPhone = "01812345678",
                    condition = "নতুন (Brand New)",
                    location = "মিরপুর-১০, ঢাকা",
                    tags = "watch, smartwatch, smart watch, amoled, bluetooth, black, digital, ঘড়ি, স্মার্টওয়াচ",
                    visualCategory = "ঘড়ি ও গ্যাজেট",
                    visualColor = "Black",
                    viewsCount = 384,
                    isMyPost = true
                ),
                Product(
                    title = "Premium Leather Handbag for Women",
                    banglaTitle = "প্রিমিয়াম লেদার লেডিস হ্যান্ডব্যাগ (ফ্যাশনেবল কালেকশন)",
                    price = 1850.0,
                    originalPrice = 2750.0,
                    category = "ফ্যাশন ও ব্যাগ",
                    imageUrl = "",
                    localDrawableRes = R.drawable.img_product_handbag,
                    description = "উন্নত মানের সিন্থেটিক প্রিমিয়াম পিইউ লেদার। আধুনিক ডিজাইন, একাধিক চেম্বার ও লং স্ট্র্যাপ সহ। পার্টি ও অফিস ব্যবহারের জন্য সেরা।",
                    fbMarketplaceUrl = "https://www.facebook.com/marketplace/item/849201948271639",
                    sellerName = "গ্ল্যামার ফ্যাশন বিডি (Glamour Fashion)",
                    sellerPhone = "01723456789",
                    condition = "নতুন (Brand New)",
                    location = "ধানমন্ডি, ঢাকা",
                    tags = "bag, handbag, leather, purse, women, fashion, brown, ব্যাগ, লেডিস ব্যাগ, হ্যান্ডব্যাগ",
                    visualCategory = "ব্যাগ ও পার্স",
                    visualColor = "Brown",
                    viewsCount = 295,
                    isMyPost = true
                ),
                Product(
                    title = "Pro Gaming Wireless TWS Earbuds (ENC & Bass)",
                    banglaTitle = "প্রো গেমিং ট্রু ওয়্যারলেস ব্লুটুথ ইয়ারবাডস (সুপার বাস)",
                    price = 950.0,
                    originalPrice = 1600.0,
                    category = "স্মার্ট গ্যাজেটস",
                    imageUrl = "",
                    localDrawableRes = R.drawable.img_app_icon,
                    description = "লো লেটেন্সি গেমিং মোড, এনভায়রনমেন্টাল নয়েজ ক্যান্সেলেশন (ENC), ক্লিয়ার ভয়েস কল ও ৩৫ ঘণ্টা প্লেব্যাক টাইম। সাথে স্টাইলিশ আরজিবি লাইটিং কেস।",
                    fbMarketplaceUrl = "https://www.facebook.com/marketplace/item/738291048561928",
                    sellerName = "গ্যাজেট কর্নার ঢাকা",
                    sellerPhone = "01912345678",
                    condition = "নতুন (Brand New)",
                    location = "উত্তরা, ঢাকা",
                    tags = "earbuds, tws, bluetooth, headphone, earphone, bass, audio, হেডফোন, ইয়ারবাডস",
                    visualCategory = "অডিও ও হেডফোন",
                    visualColor = "Black",
                    viewsCount = 512,
                    isMyPost = false
                ),
                Product(
                    title = "Exclusive Semi-Pure Cotton Panjabi",
                    banglaTitle = "এক্সক্লুসিভ সেমি-পিওর কটন ডিজাইনার পাঞ্জাবি",
                    price = 1250.0,
                    originalPrice = 1950.0,
                    category = "পুরুষদের ফ্যাশন",
                    imageUrl = "",
                    localDrawableRes = R.drawable.img_marketplace_hero,
                    description = "১০০% প্রিমিয়াম সুতি কাপড়। আরামদায়ক ও নিখুঁত এমব্রয়ডারি ওয়ার্ক। ঈদ, বিয়ে কিংবা যেকোনো উৎসবে পারফেক্ট লুক। সব সাইজ (৩৮, ৪০, ৪২, ৪৪) অ্যাভেইলেবল।",
                    fbMarketplaceUrl = "https://www.facebook.com/marketplace/item/627193049182746",
                    sellerName = "রয়েল ক্লথিং হাউস",
                    sellerPhone = "01612345678",
                    condition = "নতুন (Brand New)",
                    location = "নিউ মার্কেট, ঢাকা",
                    tags = "panjabi, cotton, men, fashion, clothing, kurta, white, পাঞ্জাবি, পোশাক",
                    visualCategory = "পোশাক ও জামাকাপড়",
                    visualColor = "White",
                    viewsCount = 420,
                    isMyPost = false
                ),
                Product(
                    title = "Casual Lightweight Running Sneakers",
                    banglaTitle = "ক্যাজুয়াল লাইটওয়েট রানিং স্নিকার্স জুতো",
                    price = 1550.0,
                    originalPrice = 2400.0,
                    category = "জুতো ও ফুটওয়্যার",
                    imageUrl = "",
                    localDrawableRes = R.drawable.img_marketplace_hero,
                    description = "অত্যন্ত আরামদায়ক মেমরি ফোম ইনসোল, ব্রিদেবল মেশ আপার এবং নন-স্লিপ রাবার গ্রিপ। দীর্ঘ সময় হেঁটেও কোনো ক্লান্তি নেই।",
                    fbMarketplaceUrl = "https://www.facebook.com/marketplace/item/518294029174829",
                    sellerName = "ফুটওয়্যার জোন বিডি",
                    sellerPhone = "01512345678",
                    condition = "নতুন (Brand New)",
                    location = "জিইসি মোড়, চট্টগ্রাম",
                    tags = "shoes, sneakers, running, footwear, sport, black, white, জুতো, স্নিকার্স",
                    visualCategory = "জুতো ও স্নিকার্স",
                    visualColor = "Black",
                    viewsCount = 189,
                    isMyPost = false
                ),
                Product(
                    title = "20000mAh Fast Charging Power Bank 22.5W",
                    banglaTitle = "২০,০০০ এমএএইচ ফাস্ট চার্জিং পাওয়ার ব্যাংক (২২.৫ ওয়াট)",
                    price = 1350.0,
                    originalPrice = 2100.0,
                    category = "স্মার্ট গ্যাজেটস",
                    imageUrl = "",
                    localDrawableRes = R.drawable.img_product_smartwatch,
                    description = "পিডি ও কিউসি ৩.০ সাপোর্ট সহ একসাথে ৩টি ডিভাইস ফাস্ট চার্জ করা যায়। ডিজিটাল এলইডি ডিসপ্লেতে ব্যাটারি পার্সেন্টেজ দেখা যাবে।",
                    fbMarketplaceUrl = "https://www.facebook.com/marketplace/item/409281749281903",
                    sellerName = "পাওয়ার টেক বিডি",
                    sellerPhone = "01787654321",
                    condition = "নতুন (Brand New)",
                    location = "জিন্দা বাজার, সিলেট",
                    tags = "powerbank, charger, battery, fast charging, gadget, পাওয়ার ব্যাংক",
                    visualCategory = "চার্জার ও ব্যাটারি",
                    visualColor = "Black",
                    viewsCount = 310,
                    isMyPost = false
                )
            )
            dao.insertProducts(initialProducts)
        }

        // Initialize User if empty
        if (dao.getUserAccountDirect() == null) {
            val defaultUser = UserAccount(
                id = 1,
                name = "শাহিন আলম (Shahin)",
                phone = "01712345678",
                address = "হাউজ-১৪, রোড-৫, মিরপুর-১০, ঢাকা",
                referralCode = "MBZ8942",
                referredBy = "",
                walletBalance = 350.0, // ৳১৫০ বোনাস + ৳২০০ পূর্ববর্তী রেফার ইনকাম
                totalEarned = 350.0,
                totalReferredCount = 2,
                isLoggedIn = true,
                isSeller = true
            )
            dao.insertOrUpdateUser(defaultUser)

            // Seed sample referrals
            dao.insertReferral(
                ReferralRecord(
                    friendName = "তানভীর আহমেদ (Tanvir)",
                    friendPhone = "01823****90",
                    rewardAmount = 100.0,
                    status = "সফল (Credited)"
                )
            )
            dao.insertReferral(
                ReferralRecord(
                    friendName = "সাকিব হাসান (Sakib)",
                    friendPhone = "01945****12",
                    rewardAmount = 100.0,
                    status = "সফল (Credited)"
                )
            )

            // Seed sample order
            dao.insertOrder(
                OrderRecord(
                    orderNumber = "MB-78214",
                    productId = 1,
                    productTitle = "Ultra Watch Series 9",
                    productImage = "",
                    localDrawableRes = R.drawable.img_product_smartwatch,
                    quantity = 1,
                    totalAmount = 1520.0, // 1450 + 70 delivery
                    paymentMethod = "bKash (বিকাশ)",
                    transactionId = "BK9X4M781Q",
                    deliveryAddress = "মিরপুর-১০, ঢাকা",
                    customerPhone = "01712345678",
                    orderStatus = "ডেলিভারিতে আছে (Out for Delivery)"
                )
            )
        }
    }

    suspend fun addProduct(product: Product): Long {
        return dao.insertProduct(product)
    }

    suspend fun incrementViews(id: Long) {
        dao.incrementViews(id)
    }

    suspend fun registerOrUpdateUser(
        name: String,
        phone: String,
        address: String,
        usedReferralCode: String
    ): UserAccount {
        val existing = dao.getUserAccountDirect()
        val newReferralCode = "MBZ" + Random.nextInt(1000, 9999)
        val initialBalance = if (usedReferralCode.isNotBlank()) 200.0 else 150.0

        val user = existing?.copy(
            name = name,
            phone = phone,
            address = address,
            isLoggedIn = true,
            referredBy = usedReferralCode
        ) ?: UserAccount(
            name = name,
            phone = phone,
            address = address,
            referralCode = newReferralCode,
            referredBy = usedReferralCode,
            walletBalance = initialBalance,
            totalEarned = initialBalance,
            isLoggedIn = true
        )

        dao.insertOrUpdateUser(user)
        return user
    }

    suspend fun applyReferralReward(friendName: String, friendPhone: String) {
        dao.addWalletBalance(100.0)
        dao.insertReferral(
            ReferralRecord(
                friendName = friendName,
                friendPhone = friendPhone,
                rewardAmount = 100.0,
                status = "সফল (Credited)"
            )
        )
    }

    suspend fun requestWithdraw(method: String, accountNumber: String, amount: Double): Boolean {
        val deducted = dao.deductWalletBalance(amount)
        if (deducted > 0) {
            dao.insertWithdrawal(
                WithdrawRecord(
                    method = method,
                    accountNumber = accountNumber,
                    amount = amount,
                    status = "প্রসেসিং (Processing)"
                )
            )
            return true
        }
        return false
    }

    suspend fun placeOrder(
        product: Product,
        quantity: Int,
        paymentMethod: String,
        trxId: String,
        address: String,
        phone: String
    ): OrderRecord {
        val total = (product.price * quantity) + 70.0 // delivery charge
        val orderNo = "MB-" + Random.nextInt(10000, 99999)
        val order = OrderRecord(
            orderNumber = orderNo,
            productId = product.id,
            productTitle = product.title,
            productImage = product.imageUrl,
            localDrawableRes = product.localDrawableRes,
            quantity = quantity,
            totalAmount = total,
            paymentMethod = paymentMethod,
            transactionId = trxId,
            deliveryAddress = address,
            customerPhone = phone,
            orderStatus = if (paymentMethod == "Cash on Delivery") "কনফার্মড (COD)" else "পেমেন্ট ভেরিফাইড (Paid)"
        )
        dao.insertOrder(order)
        return order
    }

    // Visual Search Engine: matches query attributes, keywords, tags and colors against product catalog
    suspend fun performVisualSearch(
        queryKeyword: String,
        detectedColor: String = "",
        detectedCategory: String = "",
        allProds: List<Product>
    ): List<VisualMatchResult> {
        val results = mutableListOf<VisualMatchResult>()
        val lowerQuery = queryKeyword.lowercase(Locale.ROOT)

        for (product in allProds) {
            var score = 0
            val matchedTags = mutableListOf<String>()

            // Category match
            if (detectedCategory.isNotBlank() &&
                (product.visualCategory.contains(detectedCategory, ignoreCase = true) ||
                 product.category.contains(detectedCategory, ignoreCase = true))
            ) {
                score += 45
                matchedTags.add("ক্যাটাগরি মিল: ${product.category}")
            }

            // Color match
            if (detectedColor.isNotBlank() && product.visualColor.equals(detectedColor, ignoreCase = true)) {
                score += 25
                matchedTags.add("রং মিল: $detectedColor")
            }

            // Keyword / Tag match
            val prodKeywords = (product.tags + " " + product.title + " " + product.banglaTitle).lowercase(Locale.ROOT)
            val tokens = lowerQuery.split(" ", ",", "-", "/")
            for (token in tokens) {
                val cleanToken = token.trim()
                if (cleanToken.length >= 2 && prodKeywords.contains(cleanToken)) {
                    score += 20
                    matchedTags.add("#$cleanToken")
                }
            }

            if (score > 0 || lowerQuery.isEmpty()) {
                val finalScore = (score + Random.nextInt(10, 20)).coerceIn(40, 98)
                results.add(
                    VisualMatchResult(
                        product = product,
                        matchScore = finalScore,
                        matchedFeatures = matchedTags.distinct().ifEmpty { listOf("ভিজ্যুয়াল সাদৃশ্য পাওয়া গেছে") }
                    )
                )
            }
        }

        return results.sortedByDescending { it.matchScore }
    }
}
