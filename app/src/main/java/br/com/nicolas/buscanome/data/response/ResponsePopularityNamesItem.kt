package br.com.nicolas.buscanome.data.response


import com.google.gson.annotations.SerializedName

data class ResponsePopularityNamesItem(
    @SerializedName("freq")
    val freq: Int,
    @SerializedName("nome")
    val nome: String,
    @SerializedName("rank")
    val rank: Int,
    @SerializedName("regiao")
    val regiao: Int,
    @SerializedName("sexo")
    val sexo: String
)