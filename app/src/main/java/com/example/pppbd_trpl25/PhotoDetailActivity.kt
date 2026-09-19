package com.example.pppbd_trpl25

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pppbd_trpl25.databinding.ActivityPhotoDetailBinding

class PhotoDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPhotoDetailBinding

    companion object {
        const val EXTRA_PHOTO_INDEX = "extra_photo_index"
        const val EXTRA_ACCOUNT_NAME = "extra_account_name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPhotoDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val photoIndex = intent.getIntExtra(EXTRA_PHOTO_INDEX, 1)
        val accountName = intent.getStringExtra(EXTRA_ACCOUNT_NAME) ?: getString(R.string.username_handle)

        binding.tvPhotoLabel.text = getString(R.string.photo_detail_label, photoIndex)
        binding.tvAccountName.text = accountName
    }
}