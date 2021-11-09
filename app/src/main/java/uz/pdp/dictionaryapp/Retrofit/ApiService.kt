package uz.pdp.dictionaryapp.Retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url
import uz.pdp.dictionaryapp.retrofitmodel.Word
import uz.pdp.dictionaryapp.retrofitmodel.WordsDay

interface ApiService {
    @GET("api/v2/entries/en/{word}")
    fun getinformation(@Path("word") word: String): Call<List<Word>>
    @GET
    fun getwordforday(@Url url:String="https://random-words-api.vercel.app/word"):Call<List<WordsDay>>
}