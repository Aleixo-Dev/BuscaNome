package br.com.nicolas.buscanome.data.service

import br.com.nicolas.buscanome.data.response.ResponsePopularityNames
import br.com.nicolas.buscanome.data.response.ResponseSearchName
import retrofit2.http.GET
import retrofit2.http.Path

interface NameService {

    @GET("api/v2/censos/nomes/{nome}")
    suspend fun getName(@Path("nome") name: String) : ResponseSearchName

    @GET("api/v2/censos/nomes")
    suspend fun getPopularityNames() : ResponsePopularityNames
}