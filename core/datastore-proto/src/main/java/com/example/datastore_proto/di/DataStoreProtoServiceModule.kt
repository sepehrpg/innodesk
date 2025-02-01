package com.example.datastore_proto.di
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.example.datastore_proto.UserPreferencesSerializer
import com.project.datastore.proto.UserPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DataStoreServiceModule {

    private val Context.userProtoDataStore: DataStore<UserPreferences> by dataStore(
        fileName = "user.pb", //Protocol Buffers
        serializer = UserPreferencesSerializer,
    )

    @Singleton
    @Provides
    fun provideUserProtoDataStore(
        @ApplicationContext app: Context
    ): DataStore<UserPreferences> = app.userProtoDataStore

}