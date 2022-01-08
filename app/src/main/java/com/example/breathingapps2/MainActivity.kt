package com.example.breathingapps2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.breathingapps2.databinding.ActivityMainBinding
import com.example.breathingapps2.fragments.ArticleFragment
import com.example.breathingapps2.fragments.HomeFragment
import com.example.breathingapps2.fragments.QuotesFragment

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        init()
    }

    private fun init(){
        mainBinding.btmNavigationMain.setOnItemSelectedListener { id ->
            when(id){
                R.id.action_home -> openFragment(HomeFragment())
                R.id.action_articles -> openFragment(ArticleFragment())
                R.id.action_quotes -> openFragment(QuotesFragment())
            }
        }

        openHomeFragment()
    }

    private fun openHomeFragment(){
        mainBinding.btmNavigationMain.setItemSelected(R.id.action_home)
    }

    private fun openFragment(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_main, fragment)
            .commit()
    }

}