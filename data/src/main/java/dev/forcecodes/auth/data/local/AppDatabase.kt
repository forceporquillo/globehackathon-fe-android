package dev.forcecodes.auth.data.local

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import dev.forcecodes.auth.data.api.Faq
import kotlinx.coroutines.flow.Flow

@Database(
    entities = [FAQ::class, Conversation::class, Customer::class, Ticket::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun conversations(): MessageDao
    abstract fun faqDao(): FAQDao
    abstract fun ticketDao(): TicketDao
    abstract fun customerDao(): CustomerDao

}

@Dao
interface MessageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMessage(message: Conversation)

    @Query("SELECT * FROM conversation")
    fun getMessages(): Flow<List<Conversation>>
}


@Dao
interface FAQDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFaq(faq: List<FAQ>)

    @Query("SELECT * FROM faq where faqId=:id")
    suspend fun getFaq(id: String): Faq

}

@Dao
interface CustomerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCustomer(customer: List<Customer>)

    @Query("SELECT * FROM customer WHERE accountNumber=:accountNumber")
    fun getCustomerByAccountNumber(accountNumber: Long): Customer
}

@Dao
interface TicketDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTicket(ticket: List<Ticket>)

    @Query("SELECT * FROM ticket WHERE customerId=:accountId")
    suspend fun getTicket(accountId: Int): Ticket
}

fun mapCategoriesToQueries(categories: List<String>): List<String> {
    val queryMappings = mapOf(
        "Account/Billing" to "Billing_Query",
        "Troubleshooting" to "Slow_Internet",
        "Billing" to "Billing_Query",
        "Account Management" to "Router_Setup",
        "Product Information" to "Slow_Internet",
        "Network_Setup" to "Router_Setup",
        "General Inquiry" to "Slow_Internet"
    )

    return categories.mapNotNull { queryMappings[it] }
}

@Entity
data class Conversation(
    @PrimaryKey
    val id: String,

    val input: String,
    val isBot: Boolean
)

@Entity
data class Customer(
    @PrimaryKey
    val customerId: Int,

    val accountNumber: Long,
    val name: String,
    val isTechSavvy: Boolean
)

@Entity
data class FAQ(
    @PrimaryKey
    val faqId: Int,

    val question: String,
    val answer: String,
    val category: String
)

@Entity
data class Ticket(
    @PrimaryKey
    val ticketId: Int,
    val customerId: Int,
    val issueType: String,
    val resolutionStatus: String
)
