package br.com.nicolas.buscanome.utils

import br.com.nicolas.buscanome.data.mapper.Name
import br.com.nicolas.buscanome.data.mapper.Names
import br.com.nicolas.buscanome.data.mapper.PopularName
import br.com.nicolas.buscanome.data.response.ResponseSearchName

data class DataState(
    val loading: Boolean = false,
    val error: String? = "",
    val successNames: List<Name>? = emptyList(),
    val successPopular: List<PopularName>? = emptyList(),
    val showNames: Boolean = false,
    val showPopular: Boolean = true,
)