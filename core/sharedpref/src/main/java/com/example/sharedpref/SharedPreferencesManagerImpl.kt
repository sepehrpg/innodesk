package com.example.sharedpref

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import com.example.sharedpref.model.FakeUser
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SharedPreferencesManagerImpl @Inject constructor(
    @ApplicationContext context: Context,
    private val gson: Gson
) : SharedPreferencesManager {

    private val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)


    //Model :: Save And Get
    //..............................................................................................
    /** Use Fake User Data And You Should Add Real User Data */
    override fun saveUser(user: FakeUser) {
        sharedPref.edit {
            putString(PREF_FIRST_NAME, user.firstName)
            putString(PREF_LAST_NAME, user.lastName)
            putLong(PREF_BIRTH_DAY, user.birthDay)
        }
    }
    override fun getUser(): FakeUser {
        val firstName = sharedPref.getString(PREF_FIRST_NAME, "")
        val lastName = sharedPref.getString(PREF_LAST_NAME, "")
        val birthDay = sharedPref.getLong(PREF_BIRTH_DAY, 0)
        return FakeUser(firstName = firstName ?: "", lastName = lastName ?: "", birthDay = birthDay)
    }
    //..............................................................................................



    //Object :: Save And Get
    //..............................................................................................
    override fun <T> saveObject(data: T) {
        val jsonData: String = gson.toJson(data)
        sharedPref.edit {
            putString(PREF_TEST_JSON_OBJECT,jsonData)
        }
    }
    /**
     * Get object
     * Example Usage :: val objectData = sharedPrefManager.getObject(FakeUser::class.java)
     * @param T
     * @param clazz
     * @return
     */
    override fun <T> getObject(clazz: Class<T>): T? {
        val json: String? = sharedPref.getString(PREF_TEST_JSON_OBJECT, null)
        return gson.fromJson(json, clazz)
    }
    //..............................................................................................



    //String :: Save And Get
    //..............................................................................................
    override fun saveString(value: String) {
        sharedPref.edit {
            putString(PREF_TEST_STRING,value)
        }
    }
    override fun getString(key: String): String {
        return sharedPref.getString(key, "")?:""
    }
    //..............................................................................................


    //Boolean :: Save And Get
    //..............................................................................................
    override fun saveBoolean(value: Boolean) {
        sharedPref.edit {
            putBoolean(PREF_TEST_BOOLEAN,value)
        }
    }
    override fun getBoolean(key: String): Boolean {
        return sharedPref.getBoolean(key, false)?:false
    }
    //..............................................................................................


    //Integer :: Save And Get
    //..............................................................................................
    override fun saveInteger(value: Int) {
        sharedPref.edit {
            putInt(PREF_TEST_INTEGER,value)
        }
    }
    override fun getInteger(key: String): Int {
        return sharedPref.getInt(key, -1)?:-1
    }
    //..............................................................................................



    //Clear And Remove
    //..............................................................................................
    @RequiresApi(Build.VERSION_CODES.GINGERBREAD) //You Can Use Commit Instead Of Apply
    override fun clearSharedPref() {
        sharedPref.edit().clear().apply()
    }
    @RequiresApi(Build.VERSION_CODES.GINGERBREAD) //You Can Use Commit Instead Of Apply
    override fun removeSharedPrefValue(key: String) {
        sharedPref.edit().remove(key).apply()
    }
    //..............................................................................................



    //Companion Object
    //..............................................................................................
    companion object {
         const val PREF_FIRST_NAME = "PREF_FIRST_NAME"
         const val PREF_LAST_NAME = "PREF_LAST_NAME"
         const val PREF_BIRTH_DAY = "PREF_BIRTH_DAY"

         const val PREF_TEST_STRING = "PREF_TEST_STRING"
         const val PREF_TEST_BOOLEAN = "PREF_TEST_BOOLEAN"
         const val PREF_TEST_INTEGER = "PREF_TEST_INTEGER"
         const val PREF_TEST_JSON_OBJECT = "PREF_TEST_JSON_OBJECT"
    }
    //..............................................................................................

}