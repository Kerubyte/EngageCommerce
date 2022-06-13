package com.kerubyte.engagecommerce.feature.product.listing

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kerubyte.engagecommerce.databinding.RecyclerTitleItemBinding
import com.kerubyte.engagecommerce.common.domain.model.ProductModel
import com.kerubyte.engagecommerce.common.util.BindingAdapter.loadImage

class TitleAdapter : RecyclerView.Adapter<TitleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecyclerTitleItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = differ.currentList[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val differCallback = object : DiffUtil.ItemCallback<ProductModel>() {
        override fun areItemsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
            return oldItem.uid == newItem.uid
        }

        override fun areContentsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    inner class ViewHolder(val binding: RecyclerTitleItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ProductModel) {
            binding.item = item
            loadImage(binding.imageTitleItemImage, item.imageUrl)
            binding.root.setOnClickListener {
                onItemClickListener?.let { it(item) }
            }
            binding.executePendingBindings()
        }
    }

    private var onItemClickListener: ((ProductModel) -> Unit)? = null

    fun setOnItemClickListener(listener: (ProductModel) -> Unit) {
        onItemClickListener = listener
    }
}