package uz.pdp.dictionaryapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.like.LikeButton
import com.like.OnLikeListener
import uz.pdp.dictionaryapp.Room.Database.AppDatabase
import uz.pdp.dictionaryapp.Room.Entities.WordInfo
import uz.pdp.dictionaryapp.databinding.ItemSearchedBinding
import uz.pdp.dictionaryapp.databinding.ItemsBinding

class SearchAdapter(var context:Context, var list:ArrayList<WordInfo>,var listener: playaudio):RecyclerView.Adapter<SearchAdapter.Vh>() {
     var appDatabase=AppDatabase.getInstance(context)
     inner class Vh(var itemRvBinding: ItemsBinding):
             RecyclerView.ViewHolder(itemRvBinding.root){
         fun onBind(wordInfo: WordInfo) {
             itemRvBinding.definition.text=wordInfo.definition
             itemRvBinding.word.text=wordInfo.name
             itemRvBinding.like.isLiked = wordInfo.save
             itemRvBinding.like.setOnLikeListener(object: OnLikeListener {
                 override fun liked(likeButton: LikeButton?) {
                     wordInfo.save=true
                     appDatabase.wordinfodao().editword(wordInfo)
                 }

                 override fun unLiked(likeButton: LikeButton?) {
                     wordInfo.save=false
                     appDatabase.wordinfodao().editword(wordInfo)
                 }

             })
             itemRvBinding.play.setOnClickListener {
                 listener.play(wordInfo)
             }

         }

     }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
    interface playaudio{
        fun play(wordInfo: WordInfo)
    }
}