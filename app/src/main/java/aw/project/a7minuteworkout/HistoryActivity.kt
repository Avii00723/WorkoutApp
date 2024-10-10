package aw.project.a7minuteworkout

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import aw.project.a7minuteworkout.databinding.ActivityHistoryBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {

    private var binding: ActivityHistoryBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarHistoryActivity)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "HISTORY"
        }

        binding?.toolbarHistoryActivity?.setNavigationOnClickListener {
            onBackPressed()
        }

        // Get the DAO and call function to retrieve data
        val dao = (application as WorkoutApp).db.historyDao()
        getAllCompletedDates(dao)
    }

    private fun getAllCompletedDates(historyDao: HistoryDao) {
        Log.e("Date: ", "getAllCompletedDates run")

        // Use lifecycleScope to collect from the Flow
        lifecycleScope.launch {
            historyDao.fetchAllHistory().collect { allCompletedDatesList ->
                if (allCompletedDatesList.isNotEmpty()) {
                    // Show RecyclerView and hide "No Data Available" text
                    binding?.tvHistory?.visibility = View.VISIBLE
                    binding?.rvHistory?.visibility = View.VISIBLE
                    binding?.tvNoDataAvailable?.visibility = View.INVISIBLE

                    // Setup RecyclerView with LinearLayoutManager
                    binding?.rvHistory?.layoutManager = LinearLayoutManager(this@HistoryActivity)

                    // Pass the list of HistoryEntity objects directly to the adapter
                    val historyAdapter = HistoryAdapter(allCompletedDatesList)
                    binding?.rvHistory?.adapter = historyAdapter
                } else {
                    // Show "No Data Available" text and hide RecyclerView
                    binding?.tvHistory?.visibility = View.GONE
                    binding?.rvHistory?.visibility = View.GONE
                    binding?.tvNoDataAvailable?.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null // Prevent memory leaks by clearing the binding reference
    }
}
