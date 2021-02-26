package com.scout48.auto.ui

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.scout48.auto.R


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var mDrawerLayout: DrawerLayout? = null
    private var mNavigationView: NavigationView? = null

    /**
     * Our MainActivity is only responsible for setting the content view that contains the
     * Navigation Host.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        } // Else, need to wait for onRestoreInstanceState

        mDrawerLayout = findViewById(R.id.drawer_layout)
        mNavigationView = findViewById(R.id.nav_view)

        initActionBar()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // Now that BottomNavigationBar has restored its instance state
        // and its selectedItemId, we can proceed with setting up the
        // BottomNavigationBar with Navigation
        setupBottomNavigationBar()
    }

    private fun initActionBar() {
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController, mDrawerLayout)
        NavigationUI.setupWithNavController(mNavigationView!!, navController)
        mNavigationView!!.setNavigationItemSelectedListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            return if (mDrawerLayout!!.isDrawerOpen(GravityCompat.START)) {
                mDrawerLayout!!.closeDrawer(GravityCompat.START)
                true
            } else {
                false
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.nav_log_in -> {
                Toast.makeText(this, "TODO", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_settings -> {
                Toast.makeText(this, "TODO", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_about -> {
                Toast.makeText(this, "TODO", Toast.LENGTH_SHORT).show()
            }
        }
        menuItem.isChecked = true
        mDrawerLayout!!.closeDrawer(GravityCompat.START)
        return true
    }

    // Needed for up-button to work.
    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(
                Navigation.findNavController(this, R.id.nav_host_fragment),
                mDrawerLayout
        )
        // If you don't have a DrawerLayout this code also works.
//        return (Navigation.findNavController(this, R.id.nav_host_fragment).navigateUp()
//                || super.onSupportNavigateUp())
    }

    /**
     * Called on first creation and when restoring state.
     */
    private fun setupBottomNavigationBar() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)
        // TODO
    }
}
