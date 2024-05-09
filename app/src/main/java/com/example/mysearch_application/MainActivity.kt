package com.example.mysearch_application

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.android.mysearch_application.presentation.main.SearchFragment
import com.example.mysearch_application.databinding.ActivityMainBinding
import com.example.mysearch_application.fragment.CollectFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var currentFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initFragment(SearchFragment())

        binding.apply {
            btnSearch.setOnClickListener {
                if (currentFragment !is SearchFragment) {
                    switchFragment(SearchFragment())
                }
            }
            btnCollection.setOnClickListener {
                if (currentFragment !is  CollectFragment) {
                    switchFragment(CollectFragment())
                }
            }
        }
    }

    private fun initFragment(fragment: Fragment) {
        currentFragment = fragment
        supportFragmentManager.commit {
            replace(R.id.main, fragment)
            setReorderingAllowed(true)
        }
    }

    private fun switchFragment(fragment: Fragment) {
        currentFragment = fragment
        supportFragmentManager.commit {
            replace(R.id.main, fragment)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }
}
