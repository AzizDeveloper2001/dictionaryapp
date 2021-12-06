package uz.pdp.dictionaryapp.Fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import uz.pdp.dictionaryapp.R
import uz.pdp.dictionaryapp.Room.Database.AppDatabase
import uz.pdp.dictionaryapp.Room.Entities.WordInfo
import uz.pdp.dictionaryapp.adapters.HistoryAdapter
import uz.pdp.dictionaryapp.databinding.FragmentHistoryBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class HistoryFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var binding:FragmentHistoryBinding
    lateinit var historyAdapter: HistoryAdapter
    lateinit var list:ArrayList<WordInfo>
    lateinit var appDatabase: AppDatabase
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentHistoryBinding.inflate(inflater,container,false)
        appDatabase= AppDatabase.getInstance(requireContext())
        list= ArrayList(appDatabase.wordinfodao().getallwordsearch())
        list.reverse()
        if(list.size==0){
            binding.animationView.visibility=View.VISIBLE
        } else{
            binding.animationView.visibility=View.INVISIBLE
        }
        historyAdapter= HistoryAdapter(list,object :HistoryAdapter.OnItemClickListener{
            override fun onItemdelete(wordInfo: WordInfo,position:Int) {
                         var dialog=AlertDialog.Builder(requireContext())
                dialog.setMessage("Do you want to delete?")
                dialog.setPositiveButton("Yes",object :DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        appDatabase.wordinfodao().deleteword(wordInfo)
                        list.removeAt(position)
                        historyAdapter.notifyItemRemoved(position)
                        historyAdapter.notifyItemRangeChanged(position,list.size)
                        Toast.makeText(requireContext(), "Successfully deleted!", Toast.LENGTH_SHORT).show()

                        dialog?.dismiss()
                    }

                })
                dialog.setNegativeButton("No",object :DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        dialog?.dismiss()
                    }

                })
                dialog.show()

            }

            override fun viewvisible() {
                binding.animationView.visibility=View.VISIBLE
            }

        })
        binding.rv.adapter=historyAdapter
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HistoryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}