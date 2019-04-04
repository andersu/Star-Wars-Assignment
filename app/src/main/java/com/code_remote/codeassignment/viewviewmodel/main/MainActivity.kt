package com.code_remote.codeassignment.viewviewmodel.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.LifecycleOwner
import com.code_remote.codeassignment.R
import com.code_remote.codeassignment.utils.SW_MAIN_TAB_POS
import kotlinx.android.synthetic.main.main_activity.*


class MainActivity : AppCompatActivity(), LifecycleOwner {

    private lateinit var mainPagerAdapter: MainPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.code_remote.codeassignment.R.layout.main_activity)
        mainPagerAdapter = MainPagerAdapter(supportFragmentManager)
        mainPagerAdapter.tabTitles = resources.getStringArray(R.array.sw_tab_titles)
        pager.adapter = mainPagerAdapter
        pager.offscreenPageLimit = 2
    }

    class MainPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        lateinit var tabTitles: Array<String>

        override fun getCount(): Int {
            return 3
        }

        override fun getItem(position: Int): Fragment {
            return MainFragment().apply { arguments = Bundle().apply { putInt(SW_MAIN_TAB_POS, position) } }
        }

        override fun getPageTitle(position: Int): CharSequence {
            return tabTitles[position]
        }
    }

}

