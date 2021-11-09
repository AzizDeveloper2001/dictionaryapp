package uz.pdp.dictionaryapp.Room.Dao

import androidx.room.*
import uz.pdp.dictionaryapp.Room.Entities.WordInfo

@Dao
interface WordInfoDao {
    @Insert
    fun addWordInfo(wordInfoDao: WordInfo)
    @Query("select * from wordinfo")
    fun getallWord():List<WordInfo>


    @Query("select * from wordinfo where type=='day'")
    fun getallworddays():List<WordInfo>

    @Query("select * from wordinfo where type=='search'")
    fun getallwordsearch():List<WordInfo>


    @Query(value = "select * from wordinfo where save==1")
    fun getallsaved():List<WordInfo>

    @Delete
    fun deleteword(wordInfo: WordInfo)

    @Update
    fun editword(wordInfo: WordInfo)
}