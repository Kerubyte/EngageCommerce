package com.example.engagecommerce.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.engagecommerce.R
import com.example.engagecommerce.data.Order

class OrderAdapter : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {


    private val ordersList = ArrayList<Order>()

    fun setOrders(list: List<Order>) {
        ordersList.clear()
        ordersList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_row_order, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        bindData(holder)
    }

    override fun getItemCount(): Int {
        return ordersList.size
    }

    private fun bindData(holder: OrderAdapter.OrderViewHolder) {
        val value = holder.itemView.findViewById<TextView>(R.id.text_order_value)
        val timeStamp = holder.itemView.findViewById<TextView>(R.id.text_order_timestamp)

        value.text = ordersList[holder.adapterPosition].value
        timeStamp.text = ordersList[holder.adapterPosition].time
    }


    inner class OrderViewHolder(view: View) : RecyclerView.ViewHolder(view)
}