package com.example.datastore_proto.di

import com.example.datastore_proto.DataStoreProtoManager
import com.example.datastore_proto.DataStoreProtoManagerImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataStoreProtoManagerModule {

    @Singleton
    @Binds
    abstract fun bindDataStoreService(impl: DataStoreProtoManagerImpl): DataStoreProtoManager

}

