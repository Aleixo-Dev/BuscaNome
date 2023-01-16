package br.com.nicolas.buscanome.data.repository

import br.com.nicolas.buscanome.data.mapper.*
import br.com.nicolas.buscanome.data.response.ResponsePopularityNames
import br.com.nicolas.buscanome.data.response.ResponseSearchName
import br.com.nicolas.buscanome.data.service.NameService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NameRepositoryImpl @Inject constructor(
    private val nameService: NameService
) : NameRepository {

    override suspend fun getName(name: String): Flow<List<Name>> = flow {
        val response = nameService.getName(name)
        emit(response.toNames())
    }

    override suspend fun getAllNames(): Flow<List<PopularName>> = flow {
        val response = nameService.getPopularityNames()
        emit(response.toData())
    }
}