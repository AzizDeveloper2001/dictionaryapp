package uz.pdp.dictionaryapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import uz.pdp.dictionaryapp.PageFragment
import uz.pdp.dictionaryapp.models.VpModel

class VpAdapter (var list:ArrayList<VpModel>,fragmentManager: FragmentManager): FragmentPagerAdapter(fragmentManager) {
    override fun getCount(): Int {
        return 3
    }

    override fun getItem(position: Int): Fragment {

        return PageFragment.newInstance(list[position])
    }
}