package com.firebaseexample.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
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

class ProductListAdapter(private val mList: ArrayList<String>) :
    RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemsViewModel = mList[position]
        holder.apply {

            // Write a message to the database
            val database = FirebaseDatabase.getInstance()
            //val myRef = database.getReference("message")
            val myRef = database.reference

            // Read from the database
            myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.

                    /*val name = dataSnapshot.child("PRODUCT_NAME/$itemsViewModel")
                    Log.e("kk", "Product Name: ---> $name")
                    tvProductTitle.text = name.value.toString()*/

                    for (data in dataSnapshot.children) {
                        when (data.key) {
                            PRODUCT_NAME -> {
                                val productNameKeyValue: HashMap<String, String> =
                                    data.value as HashMap<String, String>
                                val singleProductObj =
                                    productNameKeyValue.entries.find { it.key == itemsViewModel }
                                val productName = singleProductObj?.value ?: "N/A"
                                tvProductTitle.text = productName
                            }
                            PRODUCT_IMAGE -> {
                                val productNameKeyValue: HashMap<String, String> =
                                    data.value as HashMap<String, String>
                                val singleProductObj =
                                    productNameKeyValue.entries.find { it.key == itemsViewModel }

                                val productPic = singleProductObj?.value ?: "N/A"
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
                            PRODUCT_DESC -> {
                                val productNameKeyValue: HashMap<String, String> =
                                    data.value as HashMap<String, String>
                                val singleProductObj =
                                    productNameKeyValue.entries.find { it.key == itemsViewModel }

                                val productDesc = singleProductObj?.value ?: "N/A"

                                tvProductDesc.text = productDesc
                            }
                            PRODUCT_PRICE -> {
                                val productNameKeyValue: HashMap<String, Long> =
                                    data.value as HashMap<String, Long>
                                val singleProductObj =
                                    productNameKeyValue.entries.find { it.key == itemsViewModel }

                                val productPrice = singleProductObj?.value ?: "-"

                                tvProductPrice.text = String.format(
                                    tvProductPrice.context.getString(R.string.str_price_with_currency),
                                    productPrice
                                )
                            }
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    Log.w("mk", "Failed to read value.", error.toException())
                }
            })

            /*Picasso.get()
                .load(itemsViewModel.product_pic)
                .into(ivProductPic, object : Callback {
                    override fun onSuccess() {

                    }

                    override fun onError(e: Exception?) {

                    }
                })*/
            //tvProductTitle.text = itemsViewModel
            /*tvProductDesc.text = itemsViewModel.product_desc
            tvProductPrice.text = String.format(
                tvProductPrice.context.getString(R.string.str_price_with_currency),
                itemsViewModel.product_price
            )*/
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