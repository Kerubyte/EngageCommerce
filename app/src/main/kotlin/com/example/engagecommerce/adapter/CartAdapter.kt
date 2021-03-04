package com.example.engagecommerce.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.engagecommerce.R
import com.example.engagecommerce.data.Product
import com.example.engagecommerce.utils.Utils

class CartAdapter(private val listener: OnProductClick) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    val cartList = ArrayList<Product>()

    fun setCartProducts(list: List<Product>) {
        cartList.clear()
        cartList.addAll(list)
        notifyDataSetChanged()
    }

    fun removeFromCart(product: Product, position: Int) {
        cartList.remove(product)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_row_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        bindCartData(holder)
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    private fun bindCartData(holder: CartViewHolder) {
        val name = holder.itemView.findViewById<TextView>(R.id.text_product_name_cart)
        val price = holder.itemView.findViewById<TextView>(R.id.text_product_price_cart)
        val image = holder.itemView.findViewById<ImageView>(R.id.image_product_image_cart)

        name.text = cartList[holder.adapterPosition].name
        price.text = Utils.formatPrice.format(cartList[holder.adapterPosition].price)
        Glide.with(holder.itemView)
            .load(cartList[holder.adapterPosition].imageUrl)
            .into(image)
     }

    inner class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            view.findViewById<ImageView>(R.id.button_remove_from_cart)
                .setOnClickListener{
                    listener.onProductClick(cartList[adapterPosition], adapterPosition)
                }
        }
    }
}
