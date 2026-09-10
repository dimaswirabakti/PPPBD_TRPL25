package com.example.pppbd_trpl25

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pppbd_trpl25.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var followerCount = 512

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Keep content clear of the status bar / navigation bar in edge-to-edge mode
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
        binding.btnEditProfile.setOnClickListener { showEditBioDialog() }

        binding.btnShareProfile.setOnClickListener {
            shareProfile()
            followerCount += 1
            binding.tvFollowerCount.text = followerCount.toString()
            Toast.makeText(this, getString(R.string.toast_new_follower), Toast.LENGTH_SHORT).show()
        }
    }

    private fun showEditBioDialog() {
        val input = EditText(this)
        input.setText(binding.tvBioTagline.text)

        AlertDialog.Builder(this)
            .setTitle(getString(R.string.dialog_edit_bio_title))
            .setView(input)
            .setPositiveButton(getString(R.string.dialog_save)) { _, _ ->
                binding.tvBioTagline.text = input.text.toString()
                Toast.makeText(this, getString(R.string.toast_profile_updated), Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton(getString(R.string.dialog_cancel), null)
            .show()
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
                Toast.makeText(
                    this,
                    getString(R.string.toast_photo_clicked, index + 1),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}