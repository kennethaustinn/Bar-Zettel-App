package de.htw_berlin.barzettel

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import de.htw_berlin.barzettel.databinding.FragmentCostumerOverviewBinding

class CostumerOverviewFragment : Fragment() {

    internal lateinit var binding: FragmentCostumerOverviewBinding
    internal lateinit var viewModel : CostumerOverviewViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_costumer_overview, container, false)
        viewModel = ViewModelProvider(this).get(CostumerOverviewViewModel::class.java)
        binding.costumerOverviewViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.isDialogVisible.observe(viewLifecycleOwner, {
            if(it == true){
                val dialog = AddNewCostumerDialog(viewModel::onOkDialogClicked)
                dialog.show(parentFragmentManager, "Dialog New Costumer")
            }
        })

        return binding.root
    }
}