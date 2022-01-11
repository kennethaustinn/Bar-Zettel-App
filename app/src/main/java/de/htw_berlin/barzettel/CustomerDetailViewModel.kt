package de.htw_berlin.barzettel

import android.content.Context
import androidx.lifecycle.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.*

class CustomerDetailViewModel(private val context: Context, val costumerId : Int) : ViewModel() {
    val repository: CustomerRepository
    private var customer: Customer
    private val _liveCustomer: MutableLiveData<Customer> = MutableLiveData()
    fun costumer(): LiveData<Customer>{
        return _liveCustomer
    }

    companion object {

        val articles: List<Article>

        init {
            val file = "res/raw/price_list.json"
            val jsonText = this.javaClass.classLoader.getResourceAsStream(file).bufferedReader().readText()
            articles = Gson().fromJson<List<Article>>(jsonText, object : TypeToken<List<Article>>() {}.type)
        }

    }

    init {
        repository = CustomerRepository(context)
        runBlocking {
            val deferred = async(Dispatchers.IO) { repository.getCostumer(costumerId) }
            customer = deferred.await()
            _liveCustomer.value = customer
        }
    }


    fun onPlus(artikelId : Int){
        var newCount = 0
        val count = customer.artikel[artikelId]
        if(count == null){
            customer.artikel[artikelId] = 1
        }
        else{
            customer.artikel[artikelId] = count + 1
        }
        calculateTotalPrice()
        _liveCustomer.value = customer
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateCostumer(customer)
        }
    }

    fun onMinus(artikelId: Int){
        val count = customer.artikel[artikelId]
        if (count == null || count == 0){
            return
        }
        val newCount = count - 1
        customer.artikel[artikelId] = newCount
        if (newCount == 0){
            customer.artikel.remove(artikelId)
        }
        calculateTotalPrice()
        _liveCustomer.value = customer
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateCostumer(customer)
        }
    }

    private fun calculateTotalPrice(){
        var totalPrice: Int = 0
        customer.artikel.forEach { i, i2 ->
            val artikel = articles.find { it.id == i }
            if (artikel != null){
                totalPrice = totalPrice + (artikel.price * i2)
            }
        }
        customer.price = totalPrice
    }

    fun createTextRechnung() : String{
        var res = ""
        customer.artikel.forEach { id, quantity ->
            val artikel = articles.find { it.id==id }
            if (artikel != null){
                val dprice = (artikel.price * quantity)/100.0
                val df = java.text.DecimalFormat("#0.00")
                val a = quantity.toString() + "x " + artikel.description + " " + df.format(dprice) + " €\n"
                res = res + a
            }
        }
        return res
    }
}