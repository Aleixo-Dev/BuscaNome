package br.com.nicolas.buscanome.data.response


import com.google.gson.annotations.SerializedName

data class Re(
    @SerializedName("frequencia")
    val frequencia: Int,
    @SerializedName("periodo")
    val periodo: String
)