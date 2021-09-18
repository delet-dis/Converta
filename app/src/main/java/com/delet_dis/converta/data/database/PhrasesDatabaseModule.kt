package com.delet_dis.converta.data.database

import android.content.Context
import androidx.room.Room
import com.delet_dis.converta.data.database.daos.CategoryDAO
import com.delet_dis.converta.data.database.daos.PhraseDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PhrasesDatabaseModule {

    @Provides
    @Singleton
    fun providePhrasesDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            PhrasesDatabase::class.java,
            "phrasesDB"
        )
            .build()

    @Provides
    @Singleton
    fun provideCategoryDao(phrasesDatabase: PhrasesDatabase): CategoryDAO =
        phrasesDatabase.categoryDao()

    @Provides
    @Singleton
    fun providePhraseDao(phrasesDatabase: PhrasesDatabase): PhraseDAO =
        phrasesDatabase.phraseDao()
}