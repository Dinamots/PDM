package fr.m1miage.tmdb.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager

class BasicViewPagerAdapter(
    val fragments: List<Fragment>,
    val fragmentManager: FragmentManager,
    behavior: Int
) : FragmentPagerAdapter(fragmentManager, behavior) {


    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = fragments.size

}