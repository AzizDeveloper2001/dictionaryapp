package uz.pdp.dictionaryapp.Room.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uz.pdp.dictionaryapp.Room.Dao.WordInfoDao
import uz.pdp.dictionaryapp.Room.Entities.WordInfo


@Database(entities = [WordInfo::class],version = 2)
    abstract class AppDatabase:RoomDatabase() {
    abstract fun wordinfodao():WordInfoDao
        companion object{
            private var appDatabase:AppDatabase?=null
            @Synchronized
            fun getInstance(context: Context):AppDatabase{
                if(appDatabase==null){
                    appDatabase= Room.databaseBuilder(context,AppDatabase::class.java,"my_db")
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()


                }
                return appDatabase!!
            }
        }
    }
