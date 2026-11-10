package com.example.pppbd_trpl25

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pppbd_trpl25.databinding.ActivityUserDetailBinding
import com.example.pppbd_trpl25.model.ApiResponse
import com.example.pppbd_trpl25.model.Data
import com.example.pppbd_trpl25.model.UserDetail
import com.example.pppbd_trpl25.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailBinding
    private var userId = -1
    private var currentUser: Data? = null

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

        userId = intent.getIntExtra(EXTRA_ID, -1)
        if (userId == -1) {
            finish()
            return
        }

        binding.btnEdit.setOnClickListener {
            val user = currentUser ?: return@setOnClickListener
            val formIntent = Intent(this, UserFormActivity::class.java)
            formIntent.putExtra(UserFormActivity.EXTRA_ID, user.id)
            formIntent.putExtra(UserFormActivity.EXTRA_NAME, user.employeeName)
            formIntent.putExtra(UserFormActivity.EXTRA_AGE, user.employeeAge)
            formIntent.putExtra(UserFormActivity.EXTRA_SALARY, user.employeeSalary)
            startActivity(formIntent)
        }

        binding.btnHapus.setOnClickListener { confirmDelete() }
    }

    override fun onResume() {
        super.onResume()
        loadUser()
    }

    private fun loadUser() {
        ApiClient.getInstance().getUserById(userId).enqueue(object : Callback<UserDetail> {
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
                currentUser = user
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

    private fun confirmDelete() {
        AlertDialog.Builder(this)
            .setTitle("Hapus user")
            .setMessage("Yakin ingin menghapus user ini?")
            .setPositiveButton("Hapus") { _, _ -> deleteUser() }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun deleteUser() {
        binding.btnHapus.isEnabled = false
        ApiClient.getInstance().deleteUser(userId).enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                val body = response.body()
                if (!response.isSuccessful || body?.status != "success") {
                    Toast.makeText(
                        this@UserDetailActivity,
                        "Gagal menghapus: ${response.code()}",
                        Toast.LENGTH_LONG
                    ).show()
                    binding.btnHapus.isEnabled = true
                    return
                }
                Toast.makeText(this@UserDetailActivity, "User dihapus", Toast.LENGTH_SHORT).show()
                finish()
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Toast.makeText(this@UserDetailActivity, "Koneksi error", Toast.LENGTH_LONG).show()
                binding.btnHapus.isEnabled = true
            }
        })
    }

    companion object {
        const val EXTRA_ID = "extra_id"
    }
}