package br.com.nicolas.buscanome.data.response


import com.google.gson.annotations.SerializedName

data class ResponseSearchNameItem(
    @SerializedName("localidade")
    val localidade: String,
    @SerializedName("nome")
    val nome: String,
    @SerializedName("res")
    val res: List<Re>,
    @SerializedName("sexo")
    val sexo: Any
)