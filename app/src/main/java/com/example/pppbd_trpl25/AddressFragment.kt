package com.example.pppbd_trpl25

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.pppbd_trpl25.databinding.FragmentAddressBinding

class AddressFragment : Fragment() {

    private var _binding: FragmentAddressBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            val provinces = resources.getStringArray(R.array.provinces)
            val adapterProvinces = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                provinces
            )
            adapterProvinces.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerProvinces.adapter = adapterProvinces

            btnDone.setOnClickListener {
                val nav = findNavController()
                nav.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set("address", spinnerProvinces.selectedItem.toString())
                nav.navigateUp()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}