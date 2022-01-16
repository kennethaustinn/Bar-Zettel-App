package de.htw_berlin.barzettel

import android.app.Application
import androidx.lifecycle.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.*

class CustomerDetailViewModel(application: Application,private val costumerId : Int) : AndroidViewModel(application) {
    private val repository = CustomerRepository.getInstance(application)
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
            articles = Gson().fromJson(jsonText, object : TypeToken<List<Article>>() {}.type)
        }

    }

    init {
        runBlocking {
            val deferred = async(Dispatchers.IO) { repository.getCostumer(costumerId) }
            customer = deferred.await()
            _liveCustomer.value = customer
        }
    }


    fun onPlus(artikelId : Int){
        val count = customer.article[artikelId]
        if(count == null){
            customer.article[artikelId] = 1
        }
        else{
            customer.article[artikelId] = count + 1
        }
        calculateTotalPrice()
        _liveCustomer.value = customer
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                repository.updateCostumer(customer)
            }
        }
    }

    fun onMinus(artikelId: Int){
        val count = customer.article[artikelId]
        if (count == null || count == 0){
            return
        }
        val newCount = count - 1
        customer.article[artikelId] = newCount
        if (newCount == 0){
            customer.article.remove(artikelId)
        }
        calculateTotalPrice()
        _liveCustomer.value = customer
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                repository.updateCostumer(customer)
            }
        }
    }

    private fun calculateTotalPrice(){
        var totalPrice = 0
        customer.article.forEach { i, i2 ->
            val artikel = articles.find { it.id == i }
            if (artikel != null){
                totalPrice = totalPrice + (artikel.price * i2)
            }
        }
        customer.price = totalPrice
    }

    fun createTextRechnung() : String{
        var res = ""
        customer.article.forEach { id, quantity ->
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