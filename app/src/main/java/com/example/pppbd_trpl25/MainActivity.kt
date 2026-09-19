package com.example.pppbd_trpl25

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pppbd_trpl25.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var followerCount = 512

    private val editProfileLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                val newName = data?.getStringExtra(EditProfileActivity.EXTRA_NAME)
                val newBio = data?.getStringExtra(EditProfileActivity.EXTRA_BIO)
                if (newName != null) binding.tvFullName.text = newName
                if (newBio != null) binding.tvBioTagline.text = newBio
                Toast.makeText(this, getString(R.string.toast_profile_updated), Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupTopBar()
        setupProfileActions()
        setupPhotoGrid()
    }

    private fun setupTopBar() {
        binding.ivMenu.setOnClickListener {
            Toast.makeText(this, getString(R.string.toast_menu_soon), Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupProfileActions() {
        binding.btnEditProfile.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java).apply {
                putExtra(EditProfileActivity.EXTRA_NAME, binding.tvFullName.text.toString())
                putExtra(EditProfileActivity.EXTRA_BIO, binding.tvBioTagline.text.toString())
            }
            editProfileLauncher.launch(intent)
        }

        binding.btnShareProfile.setOnClickListener {
            shareProfile()
            followerCount += 1
            binding.tvFollowerCount.text = followerCount.toString()
            Toast.makeText(this, getString(R.string.toast_new_follower), Toast.LENGTH_SHORT).show()
        }
    }

    private fun shareProfile() {
        val shareText = getString(
            R.string.share_profile_text,
            getString(R.string.nama),
            getString(R.string.username_handle)
        )
        val sendIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, shareText)
        }
        startActivity(Intent.createChooser(sendIntent, getString(R.string.btn_share_profile)))
    }

    private fun setupPhotoGrid() {
        val cells: List<RelativeLayout> = listOf(
            binding.cell1, binding.cell2, binding.cell3,
            binding.cell4, binding.cell5, binding.cell6,
            binding.cell7, binding.cell8, binding.cell9
        )
        cells.forEachIndexed { index, cell ->
            cell.setOnClickListener {
                val intent = Intent(this, PhotoDetailActivity::class.java).apply {
                    putExtra(PhotoDetailActivity.EXTRA_PHOTO_INDEX, index + 1)
                    putExtra(PhotoDetailActivity.EXTRA_ACCOUNT_NAME, binding.tvUsernameHandle.text.toString())
                }
                startActivity(intent)
            }
        }
    }
}