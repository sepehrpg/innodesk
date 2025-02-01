package com.example.sharedpref

import com.example.sharedpref.model.FakeUser

interface SharedPreferencesManager {

    fun saveUser(user: FakeUser)
    fun getUser(): FakeUser


    fun <T> saveObject(data: T)
    fun <T> getObject(clazz: Class<T>): T?


    fun saveString(value:String)
    fun getString(key:String):String


    fun saveBoolean(value:Boolean)
    fun getBoolean(key:String):Boolean


    fun saveInteger(value:Int)
    fun getInteger(key:String):Int



    fun clearSharedPref()
    fun removeSharedPrefValue(key:String)

}