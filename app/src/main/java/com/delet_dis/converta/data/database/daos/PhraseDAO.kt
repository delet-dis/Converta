package com.delet_dis.converta.data.database.daos

import androidx.room.*
import com.delet_dis.converta.data.database.entities.Phrase
import kotlinx.coroutines.flow.Flow

@Dao
interface PhraseDAO {
    @Query("SELECT * FROM Phrase")
    fun getAllPhrasesAsList(): List<Phrase>

    @Query("SELECT * FROM Phrase")
    fun getAllPhrasesAsFlow(): Flow<List<Phrase>>


    @Insert
    suspend fun insert(phrase: Phrase)

    @Update
    suspend fun update(phrase: Phrase)

    @Delete
    suspend fun delete(phrase: Phrase)

    @Query("DELETE FROM phrase WHERE associatedCategoryId = :categoryId")
    suspend fun removePhraseByCategoryId(categoryId: Int)
}