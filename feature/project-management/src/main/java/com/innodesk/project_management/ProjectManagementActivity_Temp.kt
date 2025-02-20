package com.innodesk.project_management

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.database.RoomDb
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ProjectManagementActivity_Temp : ComponentActivity() {

    @Inject
    lateinit var database: RoomDb

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dao = database.templatesDao()
        lifecycleScope.launch(Dispatchers.IO){
            val templates = dao.getAllArticle()
            Log.d("TemplatesApplication", templates.toString())
        }


        enableEdgeToEdge()
        setContent {
            ProjectManagementMainScreen()
        }
    }
}




