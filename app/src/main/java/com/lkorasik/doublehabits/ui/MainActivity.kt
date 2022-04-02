package com.lkorasik.doublehabits.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.lkorasik.doublehabits.R
import com.lkorasik.doublehabits.databinding.ActivityMainBinding


class MainActivity: AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configureToolbar()

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_host) as NavHostFragment
        navController = navHostFragment.navController

        binding.navigationView.setupWithNavController(navController)

        //TODO: Используй тут дефотный ActionBar, у активити есть setupWithNavController
        //TODO: Toolbar должен быть над NavDrawer

        val appBarConfig = AppBarConfiguration(navController.graph, drawerLayout = binding.drawerLayout)
//        binding.toolbar.setupWithNavController(navController, appBarConfig)
        setupActionBarWithNavController(navController, appBarConfig)
    }

    private fun configureToolbar() {
//        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                binding.drawerLayout.openDrawer(GravityCompat.START)
                true
            }
            else -> {
                val fragment = supportFragmentManager.findFragmentByTag(EDITOR_FRAGMENT_TAG)
                fragment?.onOptionsItemSelected(item) ?: super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onBackPressed() {
        val drawer = binding.drawerLayout

        when {
            drawer.isDrawerOpen(GravityCompat.START) -> drawer.closeDrawer(GravityCompat.START)
            supportFragmentManager.backStackEntryCount == 1 -> finish()
            else -> super.onBackPressed()
        }
    }

    //TODO: списки привычек в viewModel, который ц HabitListBaseFragment
    companion object {
        private const val EDITOR_FRAGMENT_TAG = "Editor"
    }
}
