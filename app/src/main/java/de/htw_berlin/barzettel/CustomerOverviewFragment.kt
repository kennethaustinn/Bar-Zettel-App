package de.htw_berlin.barzettel

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import de.htw_berlin.barzettel.databinding.FragmentCostumerOverviewBinding

class CustomerOverviewFragment : Fragment() {

    internal lateinit var binding: FragmentCostumerOverviewBinding
    internal lateinit var viewModel : CustomerOverviewViewModel
    internal lateinit var viewModelFactory: CustomerOverviewViewModelFactory
    internal lateinit var adapter: CustomerListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_costumer_overview, container, false)
        setHasOptionsMenu(true)

        viewModelFactory = CustomerOverviewViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(CustomerOverviewViewModel::class.java)
        binding.costumerOverviewViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner


        viewModel.isDialogVisible.observe(viewLifecycleOwner, {
            if(it == true){
                val dialog = AddNewCostumerDialog(viewModel::onOkDialogClicked)
                dialog.show(parentFragmentManager, "Dialog New Costumer")
            }
        })

        adapter = CustomerListAdapter(viewModel::onListItemLongClicked, viewModel::onListItemClicked)
        binding.listCostumerOverview.adapter = adapter
        binding.listCostumerOverview.layoutManager = LinearLayoutManager(context)
        viewModel.kundenToday.observe(viewLifecycleOwner) { costumers ->
            adapter.submitList(costumers)
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_costumer_overview, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_sales_overview -> {
                findNavController().navigate(R.id.action_costumerOverviewFragment_to_salesOverviewFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}