package de.htw_berlin.barzettel

import android.content.Context
import androidx.lifecycle.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.*

class CostumerDetailViewModel(private val context: Context, val costumerId : Int) : ViewModel() {
    val repository: CostumerRepository
    private var costumer: Costumer
    private val _liveCostumer: MutableLiveData<Costumer> = MutableLiveData()
    fun costumer(): LiveData<Costumer>{
        return _liveCostumer
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
        repository = CostumerRepository(context)
        runBlocking {
            val deferred = async(Dispatchers.IO) { repository.getCostumer(costumerId) }
            costumer = deferred.await()
            _liveCostumer.value = costumer
        }
    }


    fun onPlus(artikelId : Int){
        var newCount = 0
        val count = costumer.artikel[artikelId]
        if(count == null){
            costumer.artikel[artikelId] = 1
        }
        else{
            costumer.artikel[artikelId] = count + 1
        }
        calculateTotalPrice()
        _liveCostumer.value = costumer
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateCostumer(costumer)
        }
    }

    fun onMinus(artikelId: Int){
        val count = costumer.artikel[artikelId]
        if (count == null || count == 0){
            return
        }
        val newCount = count - 1
        costumer.artikel[artikelId] = newCount
        if (newCount == 0){
            costumer.artikel.remove(artikelId)
        }
        calculateTotalPrice()
        _liveCostumer.value = costumer
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateCostumer(costumer)
        }
    }

    private fun calculateTotalPrice(){
        var totalPrice: Int = 0
        costumer.artikel.forEach { i, i2 ->
            val artikel = articles.find { it.id == i }
            if (artikel != null){
                totalPrice = totalPrice + (artikel.price * i2)
            }
        }
        costumer.price = totalPrice
    }
}