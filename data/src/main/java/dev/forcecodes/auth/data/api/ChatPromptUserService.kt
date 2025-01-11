@file:Suppress("unused")

package dev.forcecodes.auth.data.api

import com.squareup.moshi.JsonClass
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST

interface ChatPromptUserService {

    @POST("/api/get-active-ticket")
    fun activeTicket(accountNumber: String): Response<ActiveStatus>

    @GET("/api/get-faq")
    fun getFAQCategories(categories: List<String>): Response<List<Faq>>
}

@JsonClass(generateAdapter = true)
data class ActiveStatus(

    val ticketStatus: String? = null,
    val category: String? = null
)

@JsonClass(generateAdapter = true)
data class Faq(

    val id: Int? = null,
    val question: String? = null,
    val answer: String? = null,
    val category: String? = null
)
