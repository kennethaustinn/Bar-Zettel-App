package de.htw_berlin.barzettel

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("price")
fun TextView.setLabel(costumer: Costumer){
    val dprice = costumer.price/100.0
    val df = java.text.DecimalFormat("#0.00")
    text = "${df.format(dprice)} €"
}

@BindingAdapter("priceDetail")
fun TextView.setLabel(price: Int){
    val dprice = price/100.0
    val df = java.text.DecimalFormat("#0.00")
    text = "${df.format(dprice)} €"
}
