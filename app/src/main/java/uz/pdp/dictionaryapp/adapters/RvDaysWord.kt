package uz.pdp.dictionaryapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.like.LikeButton
import com.like.OnLikeListener
import uz.pdp.dictionaryapp.Room.Database.AppDatabase
import uz.pdp.dictionaryapp.Room.Entities.WordInfo
import uz.pdp.dictionaryapp.databinding.ItemDayswordBinding
import java.text.SimpleDateFormat

class RvDaysWord(var context:Context,var list:ArrayList<WordInfo>,var listener:RvDaysWord.onclick):RecyclerView.Adapter<RvDaysWord.Vh>() {
    var appDatabase=AppDatabase.getInstance(context)
     inner class Vh(var itemRvBinding: ItemDayswordBinding):
             RecyclerView.ViewHolder(itemRvBinding.root){
         fun onBind(wordInfo: WordInfo,position: Int) {
             itemRvBinding.wordname.text=wordInfo.name
             var simpleDateFormat = SimpleDateFormat("dd.MM.yyyy")
             itemRvBinding.date.text=simpleDateFormat.format(wordInfo.date)
             itemRvBinding.audio.setOnClickListener {
                 listener.onaudioclick(wordInfo,position)
             }
             itemRvBinding.share.setOnClickListener {
                 listener.onshare(wordInfo,position)
             }
             itemRvBinding.copy.setOnClickListener {
                 listener.oncopy(wordInfo,position)
             }
             itemRvBinding.like.isLiked = wordInfo.save
             itemRvBinding.like.setOnLikeListener(object: OnLikeListener{
                 override fun liked(likeButton: LikeButton?) {
                     wordInfo.save=true
                     appDatabase.wordinfodao().editword(wordInfo)
                 }

                 override fun unLiked(likeButton: LikeButton?) {
                      wordInfo.save=false
                     appDatabase.wordinfodao().editword(wordInfo)
                 }

             })

         }

     }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemDayswordBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position],position)
    }

    override fun getItemCount(): Int {
        return list.size
    }
    interface onclick{
        fun onaudioclick(wordDays: WordInfo,position: Int)
        fun oncopy(wordDays: WordInfo,position: Int)
        fun onshare(wordDays: WordInfo,position: Int)
    }
}