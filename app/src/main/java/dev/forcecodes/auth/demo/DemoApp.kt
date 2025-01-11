package dev.forcecodes.auth.demo

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import dev.forcecodes.auth.data.local.Customer
import dev.forcecodes.auth.data.local.CustomerDao
import dev.forcecodes.auth.data.local.FAQ
import dev.forcecodes.auth.data.local.FAQDao
import dev.forcecodes.auth.data.local.MessageDao
import dev.forcecodes.auth.data.local.Ticket
import dev.forcecodes.auth.data.local.TicketDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.Executors
import javax.inject.Inject

@HiltAndroidApp
class DemoApp : Application() {

    @Inject
    lateinit var ticketDao: TicketDao

    @Inject
    lateinit var customerDao: CustomerDao

    @Inject
    lateinit var faqDao: FAQDao

    @Inject
    lateinit var messageDao: MessageDao

    override fun onCreate() {
        super.onCreate()

        val faqList = listOf(
            FAQ(1, "How do I check my data balance?", "You can check your data balance by texting DATA BAL to 8080, or by logging into your account on our website/mobile app.", "Account/Billing"),
            FAQ(2, "Why is my internet slow?", "Slow internet speeds can be caused by various factors, including network congestion, Wi-Fi interference, or issues with your modem/router. Please try restarting your devices, or contact us for further assistance.", "Troubleshooting"),
            FAQ(3, "How do I pay my bill?", "You can pay your bill through various methods, including online banking, mobile wallets (GCash, PayMaya), or by visiting any of our authorized payment centers nationwide.", "Billing"),
            FAQ(4, "How can I upgrade my internet plan?", "To upgrade your plan, you can visit any of our stores, log in to your account online, or call our hotline.", "Account Management"),
            FAQ(5, "What are the different prepaid promos available?", "We offer a variety of prepaid promos with different data allocations, validity periods, and call/text inclusions. You can check them out on our website or mobile app.", "Product Information"),
            FAQ(6, "How do I configure my new Wi-Fi router?", "You can configure your router by accessing its settings page through a web browser. Refer to your router's manual for specific instructions, or contact our technical support team for assistance.", "Network_Setup"),
            FAQ(7, "I'm experiencing intermittent disconnections. What should I do?", "Intermittent disconnections can be caused by signal interference, faulty wiring, or technical issues in your area. Please check your connections, restart your modem/router, and if the problem persists, contact our support team.", "Troubleshooting"),
            FAQ(8, "How do I change my Wi-Fi password?", "You can change your Wi-Fi password by accessing your router's settings page. Look for the Wireless Security or Wi-Fi Password section, and follow the instructions to update it.", "Network_Setup"),
            FAQ(9, "What is the Globe Rewards program and how can I earn points?", "Globe Rewards is a loyalty program where you can earn points for every peso you spend on Globe services. You can redeem these points for various rewards like discounts, freebies, and more.", "Account/Billing"),
            FAQ(10, "How do I report a lost or stolen SIM card?", "To report a lost or stolen SIM card, please contact our hotline immediately to suspend your account and prevent unauthorized usage. You can then request a SIM replacement at any of our Globe stores.", "Account Management"),
            FAQ(11, "What are the requirements for applying for a new postpaid plan?", "To apply for a new postpaid plan, you'll need a valid ID and proof of billing address. You can apply online, at any Globe store, or through our authorized dealers.", "Product Information"),
            FAQ(12, "How can I access my account statement online?", "You can access your account statement by logging into your account on our website or mobile app. You can also opt to receive your statement via email.", "Account/Billing"),
            FAQ(13, "Can I use my Globe prepaid load for international calls/texts?", "Yes, you can use your prepaid load for international calls and texts. You can subscribe to different international call and text promos to get discounted rates.", "Product Information"),
            FAQ(14, "How do I activate roaming services on my Globe mobile number?", "To activate roaming services, you can text ROAM ON to 8080, or activate it through the GlobeOne app. Make sure you have sufficient load balance or an active data roaming promo.", "Account Management"),
            FAQ(15, "What are the latest smartphones available with Globe postpaid plans?", "We offer a wide range of the latest smartphones with our postpaid plans. You can check out our available devices and plans on our website or visit any Globe store.", "Product Information"),
            FAQ(16, "How can I contact Globe customer service?", "You can reach our customer service through various channels: call our hotline, chat with us through the GlobeOne app or website, or send us a message on our social media pages.", "General Inquiry"),
            FAQ(17, "What is the coverage area of Globe's 5G network?", "Our 5G network is currently available in select areas nationwide. You can check our website for the latest coverage map and list of 5G-enabled devices.", "Network_Setup"),
            FAQ(18, "How do I set up a Globe At Home Prepaid WiFi modem?", "You can set up your Globe At Home Prepaid WiFi modem by following the instructions in the user manual. You'll need to insert the SIM card, connect the modem to a power source, and then connect your devices to the Wi-Fi network.", "Network_Setup"),
            FAQ(19, "What are the different types of broadband plans offered by Globe?", "We offer various broadband plans with different speeds, data allowances, and contract options. You can choose from fiber, DSL, or wireless broadband depending on your needs and location.", "Product Information"),
            FAQ(20, "How do I troubleshoot my Globe At Home Prepaid WiFi modem if it's not working?", "If your modem is not working, you can try restarting it, checking the connections, and ensuring that your account has sufficient load balance. If the problem persists, you can contact our technical support team for assistance.", "Troubleshooting")
        )

        val customers = listOf(
            Customer(1, 10000001, "Juan Dela Cruz", false),
            Customer(2, 10000002, "Maria Santos", true),
            Customer(3, 10000003, "Pedro Penduko", false),
            Customer(4, 10000004, "Ana Cordero", false),
            Customer(5, 10000005, "Jose Rizal", true),
            Customer(6, 10000006, "Maria Makiling", false),
            Customer(7, 10000007, "Andres Bonifacio", true),
            Customer(8, 10000008, "Lapu-Lapu", false),
            Customer(9, 10000009, "Gabriela Silang", true),
            Customer(10, 10000010, "Melchora Aquino", false),
            Customer(11, 10000011, "Emilio Aguinaldo", true),
            Customer(12, 10000012, "Apolinario Mabini", false),
            Customer(13, 10000013, "Marcelo H. Del Pilar", true),
            Customer(14, 10000014, "Graciano Lopez Jaena", false),
            Customer(15, 10000015, "Antonio Luna", true),
            Customer(16, 10000016, "Gregorio del Pilar", false),
            Customer(17, 10000017, "Jose Abad Santos", true),
            Customer(18, 10000018, "Manuel L. Quezon", false),
            Customer(19, 10000019, "Sergio Osmeña", true),
            Customer(20, 10000020, "Manuel Roxas", false)
        )

        CoroutineScope(Dispatchers.IO).launch {
            customerDao.insertCustomer(customers)
        }

        CoroutineScope(Dispatchers.IO).launch {
            faqDao.insertFaq(faqList)
        }

        val tickets = listOf(
            Ticket(1, 3, "Slow_Internet", "Resolved"),
            Ticket(2, 8, "Router_Setup", "Pending"),
            Ticket(3, 15, "Billing_Query", "Open"),
            Ticket(4, 1, "Slow_Internet", "Resolved"),
            Ticket(5, 9, "Router_Setup", "Resolved"),
            Ticket(6, 12, "Billing_Query", "Open"),
            Ticket(7, 6, "Slow_Internet", "Pending"),
            Ticket(8, 18, "Router_Setup", "Resolved"),
            Ticket(9, 4, "Billing_Query", "Open"),
            Ticket(10, 2, "Slow_Internet", "Resolved"),
            Ticket(11, 10, "Router_Setup", "Pending"),
            Ticket(12, 16, "Billing_Query", "Open"),
            Ticket(13, 5, "Slow_Internet", "Resolved"),
            Ticket(14, 11, "Router_Setup", "Resolved"),
            Ticket(15, 17, "Billing_Query", "Open"),
            Ticket(16, 7, "Slow_Internet", "Pending"),
            Ticket(17, 13, "Router_Setup", "Resolved"),
            Ticket(18, 19, "Billing_Query", "Open"),
            Ticket(19, 20, "Slow_Internet", "Resolved"),
            Ticket(20, 14, "Router_Setup", "Resolved")
        )

        CoroutineScope(Dispatchers.IO).launch {
            ticketDao.insertTicket(tickets)
        }
    }
}
