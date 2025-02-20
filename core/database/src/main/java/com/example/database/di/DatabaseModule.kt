package com.example.database.di
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.database.RoomDb
import com.example.database.model.pm.templates.TemplatesEntity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.annotations.Async.Execute
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {
    @Provides
    @Singleton
    fun providesDatabase(
        @ApplicationContext context: Context,
    ): RoomDb = Room.databaseBuilder(
        context,
        RoomDb::class.java,
        "app_db",
    ).addCallback(object : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            Executors.newSingleThreadExecutor().execute {
                val dao = providesDatabase(context).templatesDao()
                CoroutineScope(Dispatchers.IO).launch {
                    dao.insertAllArticles(
                        listOf(
                            TemplatesEntity( name = "Kanban"),
                            TemplatesEntity( name = "Scrum"),
                        )
                    )
                }
            }
        }
    }).fallbackToDestructiveMigration().build()
}
