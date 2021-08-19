package com.firebaseexample.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.firebaseexample.ui.MainActivity
import com.firebaseexample.R
import com.firebaseexample.utils.PRODUCT_DESC
import com.firebaseexample.utils.PRODUCT_IMAGE
import com.firebaseexample.utils.PRODUCT_NAME
import com.firebaseexample.utils.PRODUCT_PRICE
import com.google.android.material.imageview.ShapeableImageView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class ProductListAdapter(private val mContext: Context, private val mList: ArrayList<String>) : RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemsViewModel = mList[position]
        holder.apply {
            val firebaseDBRef = FirebaseDatabase.getInstance().reference
            firebaseDBRef.apply {
                child(PRODUCT_NAME).addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        (mContext as MainActivity).hideProgressBar()
                        val productNameKeyValue: HashMap<String, String> = snapshot.value as HashMap<String, String>
                        val singleProduct = productNameKeyValue.entries.find { it.key == itemsViewModel }
                        tvProductTitle.text = singleProduct?.value ?: tvProductTitle.context.getString(R.string.str_na)
                    }
                    override fun onCancelled(error: DatabaseError) {
                    }
                })
                child(PRODUCT_IMAGE).addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val productNameKeyValue: HashMap<String, String> = snapshot.value as HashMap<String, String>
                        val singleProduct = productNameKeyValue.entries.find { it.key == itemsViewModel }
                        val productPic = singleProduct?.value ?: pbProductPic.context.getString(R.string.str_na)
                        holder.pbProductPic.visibility = View.VISIBLE
                        Picasso.get()
                            .load(productPic)
                            .into(ivProductPic, object : Callback {
                                override fun onSuccess() {
                                    holder.pbProductPic.visibility = View.GONE
                                }
                                override fun onError(e: Exception?) {
                                    holder.pbProductPic.visibility = View.GONE
                                }
                            })
                    }
                    override fun onCancelled(error: DatabaseError) {
                    }
                })
                child(PRODUCT_DESC).addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val productNameKeyValue: HashMap<String, String> = snapshot.value as HashMap<String, String>
                        val singleProduct = productNameKeyValue.entries.find { it.key == itemsViewModel }
                        tvProductDesc.text = singleProduct?.value ?: tvProductDesc.context.getString(R.string.str_na)
                    }
                    override fun onCancelled(error: DatabaseError) {
                    }
                })
                child(PRODUCT_PRICE).addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val productNameKeyValue: HashMap<String, Long> = snapshot.value as HashMap<String, Long>
                        val singleProduct = productNameKeyValue.entries.find { it.key == itemsViewModel }
                        tvProductPrice.text = String.format(
                            tvProductPrice.context.getString(R.string.str_price_with_currency),
                            singleProduct?.value ?: tvProductDesc.context.getString(R.string.str_hyphen)
                        )
                    }
                    override fun onCancelled(error: DatabaseError) {
                    }
                })
            }
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
        val pbProductPic: ProgressBar = itemView.findViewById(R.id.pb_product_pic)
        val ivProductPic: ShapeableImageView = itemView.findViewById(R.id.iv_product_pic)
        val tvProductTitle: AppCompatTextView = itemView.findViewById(R.id.tv_product_title)
        val tvProductDesc: AppCompatTextView = itemView.findViewById(R.id.tv_product_desc)
        val tvProductPrice: AppCompatTextView = itemView.findViewById(R.id.tv_product_price)
        val tvDivider: AppCompatTextView = itemView.findViewById(R.id.tv_divider)
    }
}