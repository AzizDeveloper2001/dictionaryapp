package uz.pdp.dictionaryapp.Fragments

import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import uz.pdp.dictionaryapp.R
import uz.pdp.dictionaryapp.Room.Database.AppDatabase
import uz.pdp.dictionaryapp.Room.Entities.WordInfo
import uz.pdp.dictionaryapp.adapters.HistoryAdapter
import uz.pdp.dictionaryapp.adapters.SearchAdapter
import uz.pdp.dictionaryapp.databinding.FragmentAllDayWordBinding


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class AllDayWordFragment : Fragment(),MediaPlayer.OnPreparedListener {
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

    lateinit var binding:FragmentAllDayWordBinding
    lateinit var adapter: SearchAdapter
    lateinit var list:ArrayList<WordInfo>
    lateinit var appDatabase: AppDatabase
    lateinit var mediaPlayer:MediaPlayer
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentAllDayWordBinding.inflate(inflater,container,false)
        appDatabase= AppDatabase.getInstance(requireContext())
        list= ArrayList(appDatabase.wordinfodao().getallworddays())
        list.reverse()
        adapter= SearchAdapter(requireContext(),list,object :SearchAdapter.playaudio{
            override fun play(wordInfo: WordInfo) {

                mediaPlayer = MediaPlayer()
                mediaPlayer?.setDataSource(wordInfo.audio)
                mediaPlayer?.setOnPreparedListener(this@AllDayWordFragment)
                mediaPlayer?.prepareAsync()
            }

        })
        binding.rv.adapter=adapter
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AllDayWordFragment().apply {
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