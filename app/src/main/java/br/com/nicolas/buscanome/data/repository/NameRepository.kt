package br.com.nicolas.buscanome.data.repository

import br.com.nicolas.buscanome.data.mapper.Name
import br.com.nicolas.buscanome.data.mapper.Names
import br.com.nicolas.buscanome.data.mapper.PopularName
import br.com.nicolas.buscanome.data.response.ResponsePopularityNames
import br.com.nicolas.buscanome.data.response.ResponseSearchName
import kotlinx.coroutines.flow.Flow

interface NameRepository {

    suspend fun getName(name : String) : Flow<List<Name>>
    suspend fun getAllNames() : Flow<List<PopularName>>

}