package uz.pdp.dictionaryapp.Retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    var baseUrl="https://api.dictionaryapi.dev/"
    fun getRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    }
    val apiService= getRetrofit().create(ApiService::class.java)
}