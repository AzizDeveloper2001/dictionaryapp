package uz.pdp.dictionaryapp.Fragments

import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import uz.pdp.dictionaryapp.R
import uz.pdp.dictionaryapp.Room.Database.AppDatabase
import uz.pdp.dictionaryapp.Room.Database.AppDatabase_Impl
import uz.pdp.dictionaryapp.Room.Entities.WordInfo
import uz.pdp.dictionaryapp.adapters.SearchAdapter
import uz.pdp.dictionaryapp.databinding.FragmentSearchBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SearchFragment : Fragment(),MediaPlayer.OnPreparedListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var binding:FragmentSearchBinding
    lateinit var searchAdapter: SearchAdapter
    lateinit var appdatabase: AppDatabase
    lateinit var wordInfolist: ArrayList<WordInfo>
    lateinit var mediaPlayer:MediaPlayer
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSearchBinding.inflate(inflater,container,false)
        appdatabase= AppDatabase.getInstance(requireContext())
        wordInfolist= ArrayList(appdatabase.wordinfodao().getallwordsearch())
        wordInfolist.reverse()
        if(wordInfolist.size==0){
            binding.animationView.visibility=View.VISIBLE
        } else{
            binding.animationView.visibility=View.INVISIBLE
        }
        searchAdapter= SearchAdapter(requireContext(),wordInfolist,object :SearchAdapter.playaudio{
            override fun play(wordInfo: WordInfo) {
                val myUri = "https:/"+wordInfo.audio
                mediaPlayer = MediaPlayer()
                mediaPlayer?.setDataSource(myUri)
                mediaPlayer?.setOnPreparedListener(this@SearchFragment)
                mediaPlayer?.prepareAsync()
            }

        })
        binding.rv.adapter=searchAdapter
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onPrepared(mp: MediaPlayer?) {
        mp?.start()
    }
}