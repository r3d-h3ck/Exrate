package com.example.exrate

import com.example.exrate.data.CoingeckoService
import com.example.exrate.data.CoinRepositoryImpl
import com.example.exrate.domain.CoinRepository
import com.example.exrate.presentation.details.DetailsViewModel
import com.example.exrate.presentation.list.ListViewModel
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.create

@OptIn(ExperimentalSerializationApi::class)
fun createApplicationModule(): Module = module {
    single<CoinRepository> {
        val json = Json {
            ignoreUnknownKeys = true
        }
        val mediaType = "application/json".toMediaType()
        val converterFactory = json.asConverterFactory(mediaType)
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.coingecko.com/api/v3/")
            .addConverterFactory(converterFactory)
            .build()
        val service = retrofit.create<CoingeckoService>()
        CoinRepositoryImpl(
            dispatcher = Dispatchers.IO,
            service = service
        )
    }
    viewModel {
        ListViewModel(get<CoinRepository>())
    }
    viewModel { (coinId: Int) ->
        DetailsViewModel(
            coinId = coinId,
            repository = get<CoinRepository>()
        )
    }
}