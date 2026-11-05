package com.example.pppbd_trpl25

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pppbd_trpl25.databinding.ActivityMainBinding
import com.example.pppbd_trpl25.model.Users
import com.example.pppbd_trpl25.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom)
            insets
        }

        val client = ApiClient.getInstance()
        val response = client.getAllUsers()
        val userList = ArrayList<String>()

        response.enqueue(object : Callback<Users> {
            override fun onResponse(call: Call<Users>, response: Response<Users>) {
                val body = response.body()
                if (!response.isSuccessful || body == null) {
                    Toast.makeText(
                        this@MainActivity,
                        "Server error: ${response.code()}",
                        Toast.LENGTH_LONG
                    ).show()
                    return
                }

                for (i in body.data) {
                    userList.add(i.employeeName)
                }

                val listAdapter = ArrayAdapter(
                    this@MainActivity,
                    android.R.layout.simple_list_item_1,
                    userList
                )
                binding.lvNama.adapter = listAdapter
            }

            override fun onFailure(call: Call<Users>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Koneksi error", Toast.LENGTH_LONG).show()
            }
        })
    }
}