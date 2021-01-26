package com.example.engagecommerce.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.engagecommerce.R
import com.example.engagecommerce.data.Product
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class ProductAdapter(private val listener: OnProductClick) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private val productsList = ArrayList<Product>()

    fun setProducts(list: List<Product>) {
        productsList.clear()
        productsList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_row, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        bindData(holder)
    }

    override fun getItemCount(): Int {
        return productsList.size
    }

    private fun priceFormat(price: Long?): String {
        val input = DecimalFormat("£###,###0.00")
        return input.format(price)
    }

    private fun bindData(holder: ProductViewHolder) {
        val name = holder.itemView.findViewById<TextView>(R.id.text_product_name)
        val price = holder.itemView.findViewById<TextView>(R.id.text_product_price_cart)
        val image = holder.itemView.findViewById<ImageView>(R.id.image_product_image)
        val deliveryOption = holder.itemView.findViewById<TextView>(R.id.text_free_delivery)

        name.text = productsList[holder.adapterPosition].name
        deliveryOption.isVisible = productsList[holder.adapterPosition].delivery
        price.text = priceFormat(productsList[holder.adapterPosition].price!!)
        Glide.with(holder.itemView)
            .load(productsList[holder.adapterPosition].imageUrl)
            .into(image)
    }

    inner class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            view.setOnClickListener {
                listener.onProductClick(productsList[adapterPosition], adapterPosition)
            }
        }
    }
}

interface OnProductClick {
    fun onProductClick(product: Product, position: Int)
}