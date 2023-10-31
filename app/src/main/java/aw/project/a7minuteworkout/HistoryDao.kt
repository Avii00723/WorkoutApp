package aw.project.a7minuteworkout

import androidx.room.*
import aw.project.a7minuteworkout.HistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {
    @Insert
    suspend fun insert(historyEntity: HistoryEntity)


    @Query("SELECT * FROM `history-table`")
    fun fetchAllHistory(): Flow<List<HistoryEntity>>


}