package ie.wit.myapplication

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import ie.wit.myapplication.activities.CryptoAdapter
import ie.wit.myapplication.databinding.ActivityMainBinding
import ie.wit.myapplication.fragments.NoteFragment
import ie.wit.myapplication.models.CryptoModel



class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private var cryptoRecycler: RecyclerView? = null
    private var cryptoArray: ArrayList<CryptoModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cryptoRecycler = findViewById(R.id.recycler_view2)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.toolbar)
        replaceFragment(NoteFragment.newInstance(),false)

        //Part of template, fab unresolved reference
        //binding.appBarMain.fab.setOnClickListener { view ->
        //    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
       //         .setAction("Action", null).show()
      //  }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // creating a new ArrayList and adding data entries for the card layout
        cryptoArray = ArrayList()
        cryptoArray!!.add(CryptoModel(R.drawable.cryptoad, "Bitcoin", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas auctor, felis non accumsan laoreet, " +
                "sem massa scelerisque ante, at aliquam nisl massa quis nibh. Nullam arcu ligula, molestie sed faucibus vitae, ornare ut lacus."))

        cryptoArray!!.add(CryptoModel(R.drawable.cryptoad, "Ethereum", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas auctor, felis non accumsan laoreet, "+
            "sem massa scelerisque ante, at aliquam nisl massa quis nibh. Nullam arcu ligula, molestie sed faucibus vitae, ornare ut lacus."))

        cryptoArray!!.add(CryptoModel(R.drawable.cryptoad, "Cardano", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas auctor, felis non accumsan laoreet, " +
                "sem massa scelerisque ante, at aliquam nisl massa quis nibh. Nullam arcu ligula, molestie sed faucibus vitae, ornare ut lacus."))

        cryptoArray!!.add(CryptoModel(R.drawable.cryptoad, "Stellar", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas auctor, felis non accumsan laoreet, " +
                "sem massa scelerisque ante, at aliquam nisl massa quis nibh. Nullam arcu ligula, molestie sed faucibus vitae, ornare ut lacus."))

        cryptoArray!!.add(CryptoModel(R.drawable.cryptoad, "Compound", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas auctor, felis non accumsan laoreet, " +
                "sem massa scelerisque ante, at aliquam nisl massa quis nibh. Nullam arcu ligula, molestie sed faucibus vitae, ornare ut lacus."))

        cryptoArray!!.add(CryptoModel(R.drawable.cryptoad, "VeThor", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas auctor, felis non accumsan laoreet, " +
                "sem massa scelerisque ante, at aliquam nisl massa quis nibh. Nullam arcu ligula, molestie sed faucibus vitae, ornare ut lacus."))


        // Passing the array list to the adapter
        val courseAdapter = CryptoAdapter(this, cryptoArray!!)

        // vertical list layout manager for recyclerview
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


        // setting layout manager and adapter to recycler view.
        cryptoRecycler!!.layoutManager = linearLayoutManager
        cryptoRecycler!!.adapter = courseAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun replaceFragment(fragment: Fragment, istransition:Boolean){
        val fragmentTransition = supportFragmentManager.beginTransaction()
        if (istransition){
            fragmentTransition.setCustomAnimations(android.R.anim.slide_out_right,android.R.anim.slide_in_left)
        }
        fragmentTransition.add(R.id.frame_layout,fragment).addToBackStack(fragment.javaClass.simpleName).commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val fragments = supportFragmentManager.fragments
        if (fragments.size == 0){
            finish()
        }
    }
}