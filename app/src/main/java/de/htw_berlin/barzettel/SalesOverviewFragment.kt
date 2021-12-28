package de.htw_berlin.barzettel

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import de.htw_berlin.barzettel.databinding.FragmentCostumerOverviewBinding
import de.htw_berlin.barzettel.databinding.FragmentSalesOverviewBinding

class SalesOverviewFragment : Fragment() {

    internal lateinit var binding: FragmentSalesOverviewBinding
    internal lateinit var viewModel : SalesOverviewViewModel
    internal lateinit var viewModelFactory: SalesOverviewViewModelFactory
    private lateinit var adapter: SalesMonthListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sales_overview, container, false)
        viewModelFactory = SalesOverviewViewModelFactory(requireContext())
        viewModel = ViewModelProvider(this, viewModelFactory).get(SalesOverviewViewModel::class.java)
        binding.salesOverviewViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setHasOptionsMenu(true)

        adapter = SalesMonthListAdapter()
        binding.SalesRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.SalesRecyclerView.adapter = adapter

        viewModel.salesMonth.observe(viewLifecycleOwner,{
            adapter.submitList(it)
        })

        binding.editTextDate.setOnClickListener {
            val dateFragment = MonthYearPicker(viewModel.date.value!!,viewModel)
            dateFragment.show(this.parentFragmentManager, "Month Year Picker")
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_sales_overview, menu)
    }
}

class SalesOverviewViewModelFactory(private val context: Context) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SalesOverviewViewModel::class.java)) {
            return SalesOverviewViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}