package com.example.pppbd_trpl25

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pppbd_trpl25.databinding.ActivityUserFormBinding
import com.example.pppbd_trpl25.model.ApiResponse
import com.example.pppbd_trpl25.model.UserRequest
import com.example.pppbd_trpl25.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserFormActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserFormBinding
    private var userId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUserFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom)
            insets
        }

        userId = intent.getIntExtra(EXTRA_ID, -1)
        if (userId == -1) {
            binding.tvTitle.text = "Tambah User"
        } else {
            binding.tvTitle.text = "Edit User"
            binding.etName.setText(intent.getStringExtra(EXTRA_NAME))
            binding.etAge.setText(intent.getIntExtra(EXTRA_AGE, 0).toString())
            binding.etSalary.setText(intent.getIntExtra(EXTRA_SALARY, 0).toString())
        }

        binding.btnSimpan.setOnClickListener { save() }
    }

    private fun save() {
        val name = binding.etName.text.toString().trim()
        val age = binding.etAge.text.toString().toIntOrNull()
        val salary = binding.etSalary.text.toString().toIntOrNull()

        if (name.isEmpty() || age == null || salary == null) {
            Toast.makeText(this, "Isi nama, umur, dan gaji dengan benar", Toast.LENGTH_SHORT).show()
            return
        }

        val request = UserRequest(name, salary, age)
        val client = ApiClient.getInstance()
        val call = if (userId == -1) {
            client.createUser(request)
        } else {
            client.updateUser(userId, request)
        }

        binding.btnSimpan.isEnabled = false
        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                val body = response.body()
                if (!response.isSuccessful || body?.status != "success") {
                    Toast.makeText(
                        this@UserFormActivity,
                        "Gagal menyimpan: ${response.code()}",
                        Toast.LENGTH_LONG
                    ).show()
                    binding.btnSimpan.isEnabled = true
                    return
                }
                Toast.makeText(this@UserFormActivity, "Berhasil disimpan", Toast.LENGTH_SHORT).show()
                finish()
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Toast.makeText(this@UserFormActivity, "Koneksi error", Toast.LENGTH_LONG).show()
                binding.btnSimpan.isEnabled = true
            }
        })
    }

    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_AGE = "extra_age"
        const val EXTRA_SALARY = "extra_salary"
    }
}