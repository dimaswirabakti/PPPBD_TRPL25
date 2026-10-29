package com.example.pppbd_trpl25

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pppbd_trpl25.databinding.ActivityMainBinding
import androidx.recyclerview.widget.LinearLayoutManager
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val adapterDisaster = DisasterAdapter(generateDummy()) { disaster ->
            Toast.makeText(this@MainActivity, "You clicked on ${disaster.nameDisaster}", Toast.LENGTH_SHORT).show()
        }
        with(binding) {
//            linearlayoutmanager
//            rvDisaster.apply {
//                adapter = adapterDisaster
//                layoutManager = LinearLayoutManager(this@MainActivity)
//            }
//            gridlayoutmanager
            rvDisaster.apply {
                adapter = adapterDisaster
                layoutManager = GridLayoutManager(this@MainActivity, 2)
            }
        }
    }

    fun generateDummy(): List<Disaster> {
        return listOf(
            Disaster("Tsunami", "Natural", R.drawable.img_tsunami),
            Disaster("Volcanic Eruption", "Natural", R.drawable.img_volcanic_eruption),
            Disaster("Earthquake", "Natural", R.drawable.img_earthquake),
            Disaster("Flood", "Natural", R.drawable.img_flood),
            Disaster("Fire", "Natural", R.drawable.img_fire),
            Disaster("Nuclear Accident", "Man-made", R.drawable.img_nuclear_accident),
            Disaster("Terrorist Attack", "Man-made", R.drawable.img_terrorist_attack),
            Disaster("War", "Man-made", R.drawable.img_war)
        )
    }
}