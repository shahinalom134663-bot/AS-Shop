package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.OrderRecord
import com.example.data.model.Product
import com.example.data.model.ReferralRecord
import com.example.data.model.UserAccount
import com.example.data.model.WithdrawRecord
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {

    // --- Products ---
    @Query("SELECT * FROM products ORDER BY timestamp DESC")
    fun getAllProducts(): Flow<List<Product>>

    @Query("SELECT * FROM products WHERE isMyPost = 1 ORDER BY timestamp DESC")
    fun getMyPostedProducts(): Flow<List<Product>>

    @Query("SELECT * FROM products WHERE category = :category ORDER BY timestamp DESC")
    fun getProductsByCategory(category: String): Flow<List<Product>>

    @Query("SELECT * FROM products WHERE id = :id LIMIT 1")
    suspend fun getProductById(id: Long): Product?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: Product): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<Product>)

    @Query("SELECT COUNT(*) FROM products")
    suspend fun getProductCount(): Int

    @Query("UPDATE products SET viewsCount = viewsCount + 1 WHERE id = :id")
    suspend fun incrementViews(id: Long)

    @Query("DELETE FROM products WHERE id = :id")
    suspend fun deleteProduct(id: Long)

    // --- User Account ---
    @Query("SELECT * FROM user_account WHERE id = 1 LIMIT 1")
    fun getUserAccount(): Flow<UserAccount?>

    @Query("SELECT * FROM user_account WHERE id = 1 LIMIT 1")
    suspend fun getUserAccountDirect(): UserAccount?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateUser(user: UserAccount)

    @Update
    suspend fun updateUser(user: UserAccount)

    @Query("UPDATE user_account SET walletBalance = walletBalance + :amount, totalEarned = totalEarned + :amount WHERE id = 1")
    suspend fun addWalletBalance(amount: Double)

    @Query("UPDATE user_account SET walletBalance = walletBalance - :amount WHERE id = 1 AND walletBalance >= :amount")
    suspend fun deductWalletBalance(amount: Double): Int

    // --- Referrals ---
    @Query("SELECT * FROM referrals ORDER BY timestamp DESC")
    fun getAllReferrals(): Flow<List<ReferralRecord>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReferral(referral: ReferralRecord): Long

    // --- Withdrawals ---
    @Query("SELECT * FROM withdrawals ORDER BY timestamp DESC")
    fun getAllWithdrawals(): Flow<List<WithdrawRecord>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWithdrawal(withdraw: WithdrawRecord): Long

    // --- Orders ---
    @Query("SELECT * FROM orders ORDER BY timestamp DESC")
    fun getAllOrders(): Flow<List<OrderRecord>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: OrderRecord): Long
}
