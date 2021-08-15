package com.firebaseexample.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.marginBottom
import androidx.recyclerview.widget.RecyclerView
import com.firebaseexample.R
import com.firebaseexample.model.Product
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class ProductListAdapter(private val mList: ArrayList<Product>) :
    RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemsViewModel = mList[position]
        holder.apply {
            Picasso.get()
                .load(itemsViewModel.product_pic)
                .into(ivProductPic, object : Callback {
                    override fun onSuccess() {

                    }

                    override fun onError(e: Exception?) {

                    }
                })
            tvProductTitle.text = itemsViewModel.product_title
            tvProductDesc.text = itemsViewModel.product_desc
            tvProductPrice.text = String.format(
                tvProductPrice.context.getString(R.string.str_price_with_currency),
                itemsViewModel.product_price
            )
            if (position == mList.size - 1) {
                tvDivider.apply {
                    visibility = View.GONE
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val ivProductPic: ShapeableImageView = itemView.findViewById(R.id.iv_product_pic)
        val tvProductTitle: AppCompatTextView = itemView.findViewById(R.id.tv_product_title)
        val tvProductDesc: AppCompatTextView = itemView.findViewById(R.id.tv_product_desc)
        val tvProductPrice: AppCompatTextView = itemView.findViewById(R.id.tv_product_price)
        val tvDivider: AppCompatTextView = itemView.findViewById(R.id.tv_divider)
    }
}