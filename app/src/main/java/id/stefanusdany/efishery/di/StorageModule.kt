package id.stefanusdany.efishery.di

import androidx.room.Room
import id.stefanusdany.efishery.data.Repository
import id.stefanusdany.efishery.data.source.local.repository.LocalDataSource
import id.stefanusdany.efishery.data.source.local.room.RoomDatabase
import id.stefanusdany.efishery.data.source.remote.ApiService
import id.stefanusdany.efishery.data.source.remote.RemoteDataSource
import id.stefanusdany.efishery.ui.addData.AddDataViewModel
import id.stefanusdany.efishery.ui.filter.FilterViewModel
import id.stefanusdany.efishery.ui.homepage.MainViewModel
import id.stefanusdany.efishery.utils.AppExecutors
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<RoomDatabase>().commodityDao() }
    factory { get<RoomDatabase>().areaDao() }
    factory { get<RoomDatabase>().sizeDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            RoomDatabase::class.java, "room_database.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://stein.efishery.com/v1/storages/5e1edf521073e315924ceab4/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get(), get(), get()) }
    single { RemoteDataSource(get()) }
    factory { AppExecutors() }
    single { Repository(get(), get(), get()) }
}

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { AddDataViewModel(get())}
    viewModel { FilterViewModel(get())}
}