package com.dmabram15.androidmovieapp.view

import android.annotation.SuppressLint
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dmabram15.androidmovieapp.view.bcreceivers.ConnectivityReceiver
import com.dmabram15.androidmoviesapp.R
import com.dmabram15.androidmovieapp.viewmodel.MainViewModel
import com.dmabram15.androidmoviesapp.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private val receiver: ConnectivityReceiver by lazy {
        ConnectivityReceiver()
    }

    private val binding: MainActivityBinding by lazy {
        MainActivityBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        showFragment()

        registerReceiver(receiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.history_menu_item -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, HistoryFragment.newInstance())
                    .addToBackStack("")
                    .commitAllowingStateLoss()
                true
            }

            R.id.settings_menu_item -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, SettingFragment.newInstance())
                    .addToBackStack("")
                    .commitAllowingStateLoss()
                true
            }

            R.id.contacts_menu_item -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, ContactsFragment.newInstance())
                    .addToBackStack("")
                    .commitAllowingStateLoss()
                true
            }

            R.id.maps_menu_item -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MapsFragment.newInstance())
                    .addToBackStack("")
                    .commitAllowingStateLoss()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showFragment() {
        val count = supportFragmentManager.backStackEntryCount
        val fragment = when {
            count > 0 -> supportFragmentManager.fragments[count - 1]
            else -> MoviesFragment.newInstance()
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commitNow()
    }

    override fun onDestroy() {
        unregisterReceiver(receiver)
        super.onDestroy()
    }
}