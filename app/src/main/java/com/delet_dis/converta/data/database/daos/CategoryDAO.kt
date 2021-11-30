package com.delet_dis.converta.data.database.daos

import androidx.room.*
import com.delet_dis.converta.data.database.entities.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDAO {
    @Query("SELECT * FROM Category")
    fun getAllCategoriesAsList(): List<Category>

    @Query("SELECT * FROM Category")
    fun getAllCategoriesAsFlow(): Flow<List<Category>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(category: Category)

    @Update
    suspend fun update(category: Category)

    @Delete
    suspend fun delete(category: Category)

    @Query("DELETE FROM Category WHERE id = :id")
    suspend fun deleteById(id: Int)
}