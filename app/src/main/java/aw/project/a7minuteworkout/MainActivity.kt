package aw.project.a7minuteworkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.Toast
import aw.project.a7minuteworkout.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var binding:ActivityMainBinding?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.flStart?.setOnClickListener {

            val intent = Intent(this, ExerciseActivity::class.java)
            startActivity(intent)

        }
        binding?.flBMI?.setOnClickListener{
            val intent=Intent(this,BMIActivity::class.java)
            startActivity(intent)
        }
        binding?.flHISTORY?.setOnClickListener{
            val intent=Intent(this,HistoryActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        binding=null
    }
}