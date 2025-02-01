package com.example.datastore_proto

import com.example.datastore_proto.model.FakeUser
import kotlinx.coroutines.flow.Flow

interface DataStoreProtoManager {
    suspend fun saveUserToProtoStore(user: FakeUser)
    fun getUserFromProtoStore(): Flow<FakeUser>
    suspend fun clearUserFromProtoStore()
}