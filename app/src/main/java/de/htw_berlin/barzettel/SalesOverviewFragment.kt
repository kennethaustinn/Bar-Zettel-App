package de.htw_berlin.barzettel

import android.content.Context
import android.os.Bundle
import android.print.PrintAttributes
import android.print.PrintManager
import android.util.Log
import android.view.*
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_print -> {
                doWebViewPrint()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun createWebPrintJob(webView: WebView) {

        // Get a PrintManager instance
        (requireActivity().getSystemService(Context.PRINT_SERVICE) as? PrintManager)?.let { printManager ->

            val jobName = "Test Document"

            // Get a print adapter instance
            val printAdapter = webView.createPrintDocumentAdapter(jobName)

            // Create a print job with name and adapter instance
            printManager.print(
                jobName,
                printAdapter,
                PrintAttributes.Builder()
                    .setMinMargins(PrintAttributes.Margins.NO_MARGINS)
                    .setMediaSize(PrintAttributes.MediaSize.ISO_A4)
                    .build()
            )
        }
    }

    private fun doWebViewPrint() {
        // Create a WebView object specifically for printing
        val webView = WebView(requireActivity())
        webView.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest) = false

            override fun onPageFinished(view: WebView, url: String) {
                Log.i("Test Webview", "page finished loading $url")
                createWebPrintJob(view)

            }
        }

        // Generate an HTML document on the fly:
        webView.loadDataWithBaseURL(null, viewModel.renderSalesOfDayToHTML(), "text/HTML", "UTF-8", null)

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