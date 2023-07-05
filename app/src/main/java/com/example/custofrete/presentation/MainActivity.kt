package com.example.custofrete.presentation

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import com.example.custofrete.R
import com.example.custofrete.presentation.home.HomeFragment
import com.example.custofrete.presentation.home.HomeFragmentDirections
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onBackPressed() {

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val activeFragment = navHostFragment.childFragmentManager.fragments[0]

        if( activeFragment is HomeFragment){
            finish()
        }

        onBackPressedDispatcher.onBackPressed()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_principal, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val activeFragment = navHostFragment.childFragmentManager.fragments[0]

        return when(item.itemId){
            R.id.nav_carro -> {

                if( activeFragment is HomeFragment){
                    val action =  HomeFragmentDirections.actionHomeFragmentToDadosVeiculoFragment()
                    findNavController(activeFragment).navigate(action)
                }

                true
            }R.id.nav_sair ->{
                FirebaseAuth.getInstance().signOut();

                if( activeFragment is HomeFragment){
                    val action =  HomeFragmentDirections.actionHomeFragmentToLoginFragment()
                    findNavController(activeFragment).navigate(action)
                }

                true
            }
            else->  super.onOptionsItemSelected(item)
        }
    }

}
