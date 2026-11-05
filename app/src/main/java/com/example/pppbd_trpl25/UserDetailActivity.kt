package com.example.pppbd_trpl25

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pppbd_trpl25.databinding.ActivityUserDetailBinding
import com.example.pppbd_trpl25.model.UserDetail
import com.example.pppbd_trpl25.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom)
            insets
        }

        val id = intent.getIntExtra(EXTRA_ID, -1)
        if (id == -1) {
            finish()
            return
        }

        ApiClient.getInstance().getUserById(id).enqueue(object : Callback<UserDetail> {
            override fun onResponse(call: Call<UserDetail>, response: Response<UserDetail>) {
                val body = response.body()
                if (!response.isSuccessful || body == null) {
                    Toast.makeText(
                        this@UserDetailActivity,
                        "Server error: ${response.code()}",
                        Toast.LENGTH_LONG
                    ).show()
                    return
                }

                val user = body.data
                binding.tvName.text = "Name: ${user.employeeName}"
                binding.tvId.text = "ID: ${user.id}"
                binding.tvAge.text = "Age: ${user.employeeAge}"
                binding.tvSalary.text = "Salary: ${user.employeeSalary}"
            }

            override fun onFailure(call: Call<UserDetail>, t: Throwable) {
                Toast.makeText(this@UserDetailActivity, "Koneksi error", Toast.LENGTH_LONG).show()
            }
        })
    }

    companion object {
        const val EXTRA_ID = "extra_id"
    }
}