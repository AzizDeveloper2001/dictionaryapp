package uz.pdp.dictionaryapp.Fragments

import android.app.Activity.RESULT_OK
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.eagskunst.libraries.bottomindicatorview.MultiListenerBottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.pdp.dictionaryapp.R
import uz.pdp.dictionaryapp.Retrofit.ApiClient
import uz.pdp.dictionaryapp.Room.Database.AppDatabase
import uz.pdp.dictionaryapp.Room.Entities.WordInfo
import uz.pdp.dictionaryapp.adapters.RvAdapter
import uz.pdp.dictionaryapp.adapters.RvDaysWord
import uz.pdp.dictionaryapp.databinding.FragmentMainBinding
import uz.pdp.dictionaryapp.retrofitmodel.Word
import uz.pdp.dictionaryapp.retrofitmodel.WordsDay
import uz.pdp.dictionaryapp.utils.NetworkHelper
import java.text.SimpleDateFormat


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MainFragment : Fragment(),MediaPlayer.OnPreparedListener {

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var binding:FragmentMainBinding
    lateinit var rvAdapter: RvAdapter
    lateinit var rvDaysWord: RvDaysWord
    lateinit var wordlist:ArrayList<WordInfo>
    lateinit var appDatabase: AppDatabase
    lateinit var list: ArrayList<WordInfo>
    lateinit var mediaPlayer: MediaPlayer
    lateinit var networkHelper: NetworkHelper
    private  val TAG = "MainFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (requireActivity() as AppCompatActivity).supportActionBar?.elevation=0f
        networkHelper= NetworkHelper(requireContext())
       binding= FragmentMainBinding.inflate(inflater,container,false)
        appDatabase= AppDatabase.getInstance(requireContext())
        list=ArrayList()
        wordlist= ArrayList()

            list= ArrayList(appDatabase.wordinfodao().getallwordsearch())
            wordlist=ArrayList(appDatabase.wordinfodao().getallworddays())


        list.reverse()
        rvAdapter= RvAdapter(requireContext(),list,object :RvAdapter.playaudio{
            override fun play(wordInfo: WordInfo) {
                val myUri = "https:/"+wordInfo.audio
                mediaPlayer = MediaPlayer()
                mediaPlayer?.setDataSource(myUri)
                mediaPlayer?.setOnPreparedListener(this@MainFragment)
                mediaPlayer?.prepareAsync()
            }

        })
        binding.rv.adapter=rvAdapter
        wordlist.reverse()
        rvDaysWord= RvDaysWord(requireContext(),wordlist,object :RvDaysWord.onclick {
            override fun onaudioclick(wordDays: WordInfo, position: Int) {
                val myUri = "https:/"+wordDays.audio
                mediaPlayer = MediaPlayer()
                mediaPlayer?.setDataSource(myUri)
                mediaPlayer?.setOnPreparedListener(this@MainFragment)
                mediaPlayer?.prepareAsync()
            }

            override fun oncopy(wordDays: WordInfo, position: Int) {
                var clipboard = requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                var clip = ClipData.newPlainText("label", wordDays.name)
                clipboard.setPrimaryClip(clip)
                Toast.makeText(requireContext(), "Copied to clipboard", Toast.LENGTH_SHORT).show()
            }

            override fun onshare(wordDays: WordInfo, position: Int) {
                val sendIntent = Intent()
                sendIntent.action = Intent.ACTION_SEND
                sendIntent.putExtra(Intent.EXTRA_TEXT, wordDays.name)
                sendIntent.type = "text/plain"
                startActivity(sendIntent)
            }
        })
        binding.vp.adapter=rvDaysWord
        binding.alldayword.setOnClickListener {
            findNavController().navigate(R.id.allDayWordFragment)
        }
        binding.allsearchs.setOnClickListener {
            val bottomnavigationview = requireActivity().findViewById<MultiListenerBottomNavigationView>(R.id.bottomNavView)
           bottomnavigationview.selectedItemId=R.id.searchFragment

        }

         var simpleDateFormat = SimpleDateFormat("dd.MM.yyyy")


            if((wordlist.size>0 && simpleDateFormat.format(wordlist[0].date)!=simpleDateFormat.format(System.currentTimeMillis())) || wordlist.size==0){
               if(networkHelper.isNetWorkConnected()){
                   ApiClient.apiService.getwordforday().enqueue(object :Callback<List<WordsDay>>{
                       override fun onResponse(
                           call: Call<List<WordsDay>>,
                           response: Response<List<WordsDay>>
                       ) {
                           if(response.isSuccessful){
                               var l=ArrayList<WordsDay>(response.body())
                               if(l.size>0){
                                   ApiClient.apiService.getinformation(l[0].word!!).enqueue(object :Callback<List<Word>>{
                                       override fun onResponse(
                                           call: Call<List<Word>>,
                                           response: Response<List<Word>>
                                       ) {
                                           if(response.isSuccessful){
                                               if(response.body()?.size!!> 0){
                                                   var wordDays=WordInfo(name = l[0].word!!,definition = response.body()?.get(0)?.meanings?.get(0)?.definitions?.get(0)?.definition!!,audio =
                                                   response.body()?.get(0)?.phonetics?.get(0)?.audio!!,date = System.currentTimeMillis(),save = false,type = "day")
                                                   appDatabase.wordinfodao().addWordInfo(wordDays)
                                                   wordlist.add(0,wordDays)
                                                   rvDaysWord.notifyItemInserted(wordlist.size)
                                                   rvDaysWord.notifyItemChanged(wordlist.size)
                                               }

                                           }
                                       }

                                       override fun onFailure(call: Call<List<Word>>, t: Throwable) {
                                           Toast.makeText(requireContext(), "${t.message}", Toast.LENGTH_SHORT).show()
                                       }

                                   })
                               }
                           }
                       }

                       override fun onFailure(call: Call<List<WordsDay>>, t: Throwable) {
                           Toast.makeText(requireContext(), "${t.message}", Toast.LENGTH_SHORT).show()
                       }

                   })
               } else {
                   Toast.makeText(requireContext(), "No internet connection", Toast.LENGTH_SHORT).show()
               }

            }


        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
        binding.searchtext.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {

                      if(networkHelper.isNetWorkConnected()){
                          ApiClient.apiService.getinformation(query.toString()).enqueue(object :Callback<List<Word>>{
                              override fun onResponse(call: Call<List<Word>>, response: Response<List<Word>>) {
                                  if(response.isSuccessful){
                                      var wordInfo=response.body()
                                      var definition=  wordInfo?.get(0)?.meanings?.get(0)?.definitions?.get(0)?.definition
                                      var name=wordInfo?.get(0)?.word
                                      var audio=wordInfo?.get(0)?.phonetics?.get(0)?.audio
                                      appDatabase.wordinfodao().addWordInfo(WordInfo(name = name!!,definition = definition!!,save = false,audio = audio!!,date = System.currentTimeMillis(),type = "search"))
                                      list.add(0, WordInfo(name = name,definition = definition,save = false,audio = audio!!,date = System.currentTimeMillis(),type = "search"))
                                      rvAdapter.notifyItemInserted(0)
                                      rvAdapter.notifyDataSetChanged()




                                  }
                              }

                              override fun onFailure(call: Call<List<Word>>, t: Throwable) {
                                  Toast.makeText(requireContext(), "${t.message}", Toast.LENGTH_SHORT).show()
                                  Log.d(TAG, "onFailure: ${t.message}")
                              }

                          })
                      } else{
                          Toast.makeText(requireContext(), "No internet connection", Toast.LENGTH_SHORT).show()
                      }


                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

               return true

            }

        })

        binding.voice.setOnClickListener {
            val intent=Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            startActivityForResult(intent,200)
        }
        binding.copy.setOnClickListener {
            if(binding.searchtext.query.isNotEmpty()){
                var clipboard = requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                var clip = ClipData.newPlainText("label", binding.searchtext.query.toString())
                clipboard.setPrimaryClip(clip)
                Toast.makeText(requireContext(), "Copied to clipboard", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==200 && resultCode==RESULT_OK){
           var arraylist= data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            binding.searchtext.setIconifiedByDefault(false)


            binding.searchtext.setQuery(arraylist?.get(0)!!,true)






        }
    }



    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainFragment().apply {
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