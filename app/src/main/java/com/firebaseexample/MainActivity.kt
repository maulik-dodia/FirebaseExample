package com.firebaseexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.firebaseexample.adapter.ProductListAdapter
import com.firebaseexample.databinding.ActivityMainBinding
import com.firebaseexample.model.Product

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setting dummy data
        val productList = ArrayList<Product>()
        for (i in 1..15) {
            productList.add(
                    Product(
                            product_pic = "https://vismaifood.com/storage/app/uploads/public/8b4/19e/427/thumb__700_0_0_0_auto.jpg",
                            product_title = "Mysore Masala Dosa",
                            product_desc = "Masala dosa is a popular South Indian breakfast where a crispy crepe made of fermented rice and lentil batter is served with flavorful spiced potato curry. It is a wholesome meal in itself as it is served with potato masala, Coconut chutney and Sambar.",
                            product_price = "120"
                    )
            )
        }
        val productListAdapter = ProductListAdapter(productList)
        binding.rvProductList.adapter = productListAdapter
    }
}