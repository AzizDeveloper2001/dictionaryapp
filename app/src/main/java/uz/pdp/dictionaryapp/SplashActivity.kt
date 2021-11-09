package uz.pdp.dictionaryapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import uz.pdp.dictionaryapp.adapters.VpAdapter
import uz.pdp.dictionaryapp.databinding.ActivitySplashBinding
import uz.pdp.dictionaryapp.models.VpModel

class SplashActivity : AppCompatActivity() {
    lateinit var binding:ActivitySplashBinding
    lateinit var vpAdapter: VpAdapter
    lateinit var list:ArrayList<VpModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadData()
        vpAdapter= VpAdapter(list,supportFragmentManager)
        binding.vp.adapter=vpAdapter
        var s=0
        binding.springDotsIndicator.setViewPager(binding.vp)
        var countDownTimer=object :CountDownTimer(3000,1000){
            override fun onTick(millisUntilFinished: Long) {

                binding.vp.currentItem=s
                s+=1
            }

            override fun onFinish() {
                val intent=Intent(this@SplashActivity,MainActivity::class.java)
                startActivity(intent)
                finish()
            }

        }
        countDownTimer.start()
    }

    private fun loadData() {
        list= ArrayList()
        list.add(VpModel("Definition of the words","The ability to review the definitions of words in detail"))
        list.add(VpModel("Synonyms of the words","Study with the meanings of words and see through examples"))
        list.add(VpModel("Voice search options","Search for words by voice search and get information about them"))
    }
}