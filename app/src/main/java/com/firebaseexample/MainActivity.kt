package com.firebaseexample

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.firebaseexample.adapter.ProductListAdapter
import com.firebaseexample.databinding.ActivityMainBinding
import com.firebaseexample.model.BaseRes
import com.firebaseexample.viewmodel.MainViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.resultsLiveData.observe(this, { response ->
            when (response) {
                is BaseRes.Loading -> {
                    showProgressBar()
                }
                is BaseRes.Success -> {
                    hideProgressBar()
                    response.data?.let { successRes ->
                        //resultsAdapter.differ.submitList(successRes.results.toList())

                        // Setting dummy data
                        //val productList = ArrayList<Product>()
                        /*for (i in 1..15) {
                            productList.add(
                                Product(
                                    product_pic = "https://vismaifood.com/storage/app/uploads/public/8b4/19e/427/thumb__700_0_0_0_auto.jpg",
                                    product_title = "Mysore Masala Dosa",
                                    product_desc = "Masala dosa is a popular South Indian breakfast where a crispy crepe made of fermented rice and lentil batter is served with flavorful spiced potato curry. It is a wholesome meal in itself as it is served with potato masala, Coconut chutney and Sambar.",
                                    product_price = "120"
                                )
                            )
                        }*/
                        val productListAdapter = ProductListAdapter(successRes as ArrayList<String>)
                        binding.rvProductList.adapter = productListAdapter

                        /*// Write a message to the database
                        val database = FirebaseDatabase.getInstance()
                        //val myRef = database.getReference("message")
                        val myRef = database.reference

                        // Read from the database
                        myRef.addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                // This method is called once with the initial value and again
                                // whenever data at this location is updated
                                for (data in dataSnapshot.children) {
                                    *//*if (data.key == "address") {*//*
                                    val orderNumber = data.value.toString()
                                    Log.e("mk", "Child: ---> $data")
                                    *//*}*//*
                                }
                            }

                            override fun onCancelled(error: DatabaseError) {
                                // Failed to read value
                                Log.w("mk", "Failed to read value.", error.toException())
                            }
                        })*/
                    }
                }
                is BaseRes.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Toast.makeText(
                            this,
                            "An error occurred: $message", Toast.LENGTH_SHORT
                        ).show()
                        Log.e("mk", "Error: $message")
                    }
                }
            }
        })
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}