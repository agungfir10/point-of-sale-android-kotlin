package com.agungfir.pointofsale.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.agungfir.pointofsale.R
import com.agungfir.pointofsale.model.Product
import java.text.DecimalFormat

class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    private val products = arrayOf(
        Product("Minyak Goreng", 20000, 10),
        Product("Mie Sedaap Goreng", 30000, 20),
        Product("Teh Tarik", 5000, 6),
        Product("Kopi Kapal Api Mix", 1500, 5),
        Product("Signature", 18000, 7),
        Product("Sampoerna Mild", 25000, 7),
        Product("Jelly Drink", 1000, 7),
        Product("Teh Zegar", 1000, 20),
        Product("Salonpas", 1000, 10),
        Product("Salonpas", 1000, 10),
    )

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(product: Product) {
            itemView.findViewById<ImageView>(R.id.decrement_item).visibility = View.GONE
            itemView.findViewById<TextView>(R.id.tv_total_product_purchases).visibility = View.GONE
            val tvNameProduct: TextView = itemView.findViewById(R.id.tv_name_product)
            val tvPriceProduct: TextView = itemView.findViewById(R.id.tv_price_product)
            val tvStockProduct: TextView = itemView.findViewById(R.id.tv_stock_product)

            val (name, price, stock) = product
            val decimalFormat = DecimalFormat("#.###")
            tvNameProduct.text = name
            tvPriceProduct.text = "Rp. ${decimalFormat.format(price)}"
            tvStockProduct.text = stock.toString()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position == products.size - 1) {
            holder.itemView.findViewById<View>(R.id.view_divider).visibility = View.INVISIBLE
        }
        holder.bind(products[position])
    }

    override fun getItemCount(): Int = products.size
}