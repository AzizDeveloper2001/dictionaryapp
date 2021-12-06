package uz.pdp.dictionaryapp


import android.content.IntentFilter
import android.os.Bundle

import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.eagskunst.libraries.bottomindicatorview.BottomNavigationIndicatorView
import com.eagskunst.libraries.bottomindicatorview.MultiListenerBottomNavigationView
import com.google.android.material.bottomnavigation.BottomNavigationView
import uz.pdp.dictionaryapp.Receivers.InternetReciever
import uz.pdp.dictionaryapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    lateinit var internetReciever: InternetReciever
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
       internetReciever= InternetReciever()

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        val bottomnavigationview = findViewById<MultiListenerBottomNavigationView>(R.id.bottomNavView)


        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.mainFragment, R.id.searchFragment, R.id.saveFragment,R.id.historyFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomnavigationview.setupWithNavController(navController)
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

//    override fun onStart() {
//        super.onStart()
//        registerReceiver(internetReciever,IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"))
//
//    }
//
//    override fun onStop() {
//        super.onStop()
//        unregisterReceiver(internetReciever)
//    }
}
