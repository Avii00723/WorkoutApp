package aw.project.a7minuteworkout

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history-table")
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0, // Use an integer primary key
    val date: String = "",
    val totalExerciseDone: Int = 0
)
