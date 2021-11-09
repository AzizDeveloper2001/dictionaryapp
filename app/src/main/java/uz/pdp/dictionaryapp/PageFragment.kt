package uz.pdp.dictionaryapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import uz.pdp.dictionaryapp.databinding.FragmentPageBinding
import uz.pdp.dictionaryapp.models.VpModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class PageFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: VpModel? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getSerializable(ARG_PARAM1) as VpModel
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var binding:FragmentPageBinding
    lateinit var vpModel: VpModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentPageBinding.inflate(inflater,container,false)
        binding.name.text=param1?.name
        binding.desc.text=param1?.desc
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: VpModel) =
            PageFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}