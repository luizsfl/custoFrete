package com.example.custofrete.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.NavHostFragment
import com.example.custofrete.R
import com.example.custofrete.presentation.home.HomeFragment
import com.example.custofrete.presentation.login.LoginFragment

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
}