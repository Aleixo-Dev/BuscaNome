package br.com.nicolas.buscanome.di

import br.com.nicolas.buscanome.data.repository.NameRepository
import br.com.nicolas.buscanome.data.repository.NameRepositoryImpl
import br.com.nicolas.buscanome.data.service.NameService
import br.com.nicolas.buscanome.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideService(nameService: NameService): NameRepository {
        return NameRepositoryImpl(nameService)
    }

    @Provides
    fun provideServiceApi(): NameService {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NameService::class.java)
    }
}