package com.muiezarif.livewallpaper.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.muiezarif.livewallpaper.fragments.AnalogClockSettingsFragment
import com.muiezarif.livewallpaper.fragments.DigitalClockSettingsFragment


class MainTabLayoutAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        when(position){
            0->{
                return AnalogClockSettingsFragment()
            }
            1->{
                return DigitalClockSettingsFragment()
            }
            else->{
                return AnalogClockSettingsFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position){
            0->{
                return "Analog Clock Settings"
            }
            1->{
                return "Digital Clock Settings"
            }
            else->{
                return ""
            }
        }
    }
}