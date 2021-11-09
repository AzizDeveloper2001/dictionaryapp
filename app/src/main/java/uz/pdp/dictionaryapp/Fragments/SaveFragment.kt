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
import uz.pdp.dictionaryapp.adapters.SaveAdapter
import uz.pdp.dictionaryapp.databinding.FragmentSaveBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SaveFragment : Fragment(),MediaPlayer.OnPreparedListener {
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

    lateinit var binding:FragmentSaveBinding
    lateinit var list:ArrayList<WordInfo>
    lateinit var appDatabase: AppDatabase
    lateinit var saveAdapter: SaveAdapter
    lateinit var mediaPlayer:MediaPlayer
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSaveBinding.inflate(inflater,container,false)
        appDatabase= AppDatabase.getInstance(requireContext())
        list= ArrayList(appDatabase.wordinfodao().getallsaved())
        list.reverse()
        if(list.size==0){
            binding.animationView.visibility=View.VISIBLE
        } else{
            binding.animationView.visibility=View.INVISIBLE
        }
        saveAdapter= SaveAdapter(requireContext(),list,object :SaveAdapter.playaudio{
            override fun play(wordInfo: WordInfo) {
                val myUri = "https:/"+wordInfo.audio
                mediaPlayer = MediaPlayer()
                mediaPlayer?.setDataSource(myUri)
                mediaPlayer?.setOnPreparedListener(this@SaveFragment)
                mediaPlayer?.prepareAsync()
            }

            override fun viewvisible() {
                binding.animationView.visibility=View.VISIBLE
            }

        })
        binding.rv.adapter=saveAdapter

        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SaveFragment().apply {
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