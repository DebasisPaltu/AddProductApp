package com.example.productapp.Adpter

import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.productapp.Model.ProductDataItem
import com.example.productapp.R



class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    private var productList: List<ProductDataItem> = emptyList()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageViewProduct: ImageView = itemView.findViewById(R.id.imageViewProduct)
        private val textViewProductName: TextView = itemView.findViewById(R.id.textViewProductName)
        private val textViewProductType: TextView = itemView.findViewById(R.id.textViewProductType)
        private val textViewPrice: TextView = itemView.findViewById(R.id.textViewProductPrice)
        private val textViewTax: TextView = itemView.findViewById(R.id.textViewProductTax)

        fun bind(product : ProductDataItem) {
            Glide.with(itemView.context)
                .load(product.image)
                .placeholder(R.drawable.ic_launcher_background)
                .into(imageViewProduct)

            textViewProductName.text = product.product_name
            textViewProductType.text = product.product_type
            textViewPrice.text = "Price: ${product.price}"
            textViewTax.text = "Tax: ${product.tax}"
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = productList[position]
        holder.bind(product)
    }
    override fun getItemCount(): Int {
        return productList.size
    }
    fun submitList(products: List<ProductDataItem>) {
        productList = products
        notifyDataSetChanged()
    }
}