package uz.pdp.dictionaryapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.daimajia.swipe.SwipeLayout
import uz.pdp.dictionaryapp.R
import uz.pdp.dictionaryapp.Room.Entities.WordInfo
import uz.pdp.dictionaryapp.databinding.ItemhistoryBinding
import java.text.SimpleDateFormat


class HistoryAdapter (
    val listItems: ArrayList<WordInfo>,
    val onItemClickListener: HistoryAdapter.OnItemClickListener
    ) : RecyclerView.Adapter<HistoryAdapter.MyViewHolder>() {


        interface OnItemClickListener {
            fun onItemdelete(wordInfo: WordInfo,position: Int)
            fun viewvisible()


        }

        private var lastOpenedItem: SwipeLayout? = null


        inner class MyViewHolder(val itemBinding: ItemhistoryBinding) :
            RecyclerView.ViewHolder(itemBinding.root) {

            fun onBind(wordInfo: WordInfo, position: Int) {

                itemBinding.apply {
                    wordname.text = wordInfo.name
                    var simpleDateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm")
                    date.text=simpleDateFormat.format(wordInfo.date).substring(0,10)
                    watch.text=simpleDateFormat.format(wordInfo.date).substring(11)

                    deletebtn.setOnClickListener(View.OnClickListener {
                        onItemClickListener.onItemdelete(wordInfo,position)
                    if(listItems.size==0){
                        onItemClickListener.viewvisible()
                    }
                    })

                    menu.setOnClickListener {
                        if (root.openStatus == SwipeLayout.Status.Open) {
                            root.close()
                        } else {
                            root.open()
                        }

                        if (lastOpenedItem != null && lastOpenedItem != root) {
                            lastOpenedItem!!.close()
                        }
                    }

                    root.isSwipeEnabled = false

                    root.addSwipeListener(object : SwipeLayout.SwipeListener {
                        override fun onStartOpen(layout: SwipeLayout?) {
                            menu.setImageResource(R.drawable.ic_baseline_cancel_24)
                        }

                        override fun onOpen(layout: SwipeLayout?) {
                            lastOpenedItem = root
                        }

                        override fun onStartClose(layout: SwipeLayout?) {
                            menu.setImageResource(R.drawable.ic_vector__28_)
                        }

                        override fun onClose(layout: SwipeLayout?) {
                        }

                        override fun onUpdate(layout: SwipeLayout?, leftOffset: Int, topOffset: Int) {

                        }

                        override fun onHandRelease(layout: SwipeLayout?, xvel: Float, yvel: Float) {

                        }

                    })
                }
            }

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder = MyViewHolder(
            ItemhistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        )

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.onBind(listItems[position], position)


        }

        override fun getItemCount(): Int = listItems.size

    }