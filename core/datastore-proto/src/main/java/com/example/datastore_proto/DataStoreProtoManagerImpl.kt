package com.example.datastore_proto

import androidx.datastore.core.DataStore
import com.example.datastore_proto.model.FakeUser
import com.project.datastore.proto.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreProtoManagerImpl @Inject constructor(
    private val userProtoDataStore: DataStore<UserPreferences>
) : DataStoreProtoManager {


    override suspend fun saveUserToProtoStore(user: FakeUser) {
        userProtoDataStore.updateData { currentUserData ->
            currentUserData.toBuilder()
                .setId(user.id)
                .setFirstName(user.firstName)
                .setLastName(user.lastName)
                .setBirthDay(user.birthDay)
                .build()
        }
    }

    override fun getUserFromProtoStore(): Flow<FakeUser> =
        userProtoDataStore.data.map { user ->
            FakeUser(
                id = user.id,
                firstName = user.firstName,
                lastName = user.lastName,
                birthDay = user.birthDay
            )
        }

    override suspend fun clearUserFromProtoStore() {
        userProtoDataStore.updateData { it.toBuilder().clear().build() }
    }

}
