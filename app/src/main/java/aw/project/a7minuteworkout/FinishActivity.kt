package aw.project.a7minuteworkout

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import aw.project.a7minuteworkout.databinding.ActivityFinishBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class FinishActivity : AppCompatActivity() {

    private var binding: ActivityFinishBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // Set up the toolbar as the action bar
        setSupportActionBar(binding?.toolbarFinishActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Handle the back button click
        binding?.toolbarFinishActivity?.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Finish the activity when the finish button is clicked
        binding?.btnFinish?.setOnClickListener {
            finish()
        }

        // Get the DAO and add the date to the database
        val dao = (application as WorkoutApp).db.historyDao()
        addDateToDatabase(dao)
    }

    private fun addDateToDatabase(historyDao: HistoryDao) {
        val calendar = Calendar.getInstance()
        val datetime = calendar.time
        Log.e("Date: ", "$datetime")

        // Format the date to the required format
        val sdf = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault())
        val date = sdf.format(datetime)
        Log.e("Formatted Date : ", date)

        // Insert the date into the database as a string instead of trying to convert it to an integer
        lifecycleScope.launch {
            // Store the date as a string in the HistoryEntity
            historyDao.insert(HistoryEntity(date = date))


            Log.e("Date:", "Added to database successfully")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null // Prevent memory leaks by clearing the binding reference
    }
}
