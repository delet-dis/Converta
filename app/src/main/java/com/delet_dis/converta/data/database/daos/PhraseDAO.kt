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

    @Query("SELECT * FROM Phrase WHERE associatedCategoryId = :categoryId")
    fun getAllPhrasesByCategory(categoryId:Int):Flow<List<Phrase>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(phrase: Phrase)

    @Update
    suspend fun update(phrase: Phrase)

    @Delete
    suspend fun delete(phrase: Phrase)
}