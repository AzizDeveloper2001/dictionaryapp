package uz.pdp.dictionaryapp.Room.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WordInfo(
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    var name:String,
    var definition:String,
    var save:Boolean,
    var audio:String,
    var date:Long,
    var type:String
)
