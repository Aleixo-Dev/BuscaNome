package br.com.nicolas.buscanome.utils

sealed class DataEvent {
    data class Search(val name : String) : DataEvent()
    object Popular : DataEvent()
}
