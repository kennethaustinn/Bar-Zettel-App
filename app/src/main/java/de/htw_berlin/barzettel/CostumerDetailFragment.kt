package de.htw_berlin.barzettel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import de.htw_berlin.barzettel.databinding.FragmentCostumerDetailBinding


class CostumerDetailFragment : Fragment() {

    internal lateinit var binding: FragmentCostumerDetailBinding
    internal lateinit var viewModelFactory: CostumerDetailViewModelFactory
    internal lateinit var viewModel : CostumerDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_costumer_detail, container, false)
        viewModelFactory = CostumerDetailViewModelFactory(requireContext(), CostumerDetailFragmentArgs.fromBundle(requireArguments()).costumerId)
        viewModel = ViewModelProvider(this, viewModelFactory).get(CostumerDetailViewModel::class.java)

        binding.costumerDetailViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }
}