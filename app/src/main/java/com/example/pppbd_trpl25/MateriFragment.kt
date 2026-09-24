package com.example.pppbd_trpl25

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pppbd_trpl25.databinding.FragmentMateriBinding

class MateriFragment : Fragment() {

    private var _binding: FragmentMateriBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMateriBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val materiList = listOf(
            "Pengenalan Android Studio",
            "Layout & UI Components",
            "Activity & Intent",
            "Options Menu",
            "Tab Layout & ViewPager2",
            "RecyclerView & Adapter"
        )

        binding.rvMateri.layoutManager = LinearLayoutManager(requireContext())
        binding.rvMateri.adapter = MateriAdapter(materiList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}