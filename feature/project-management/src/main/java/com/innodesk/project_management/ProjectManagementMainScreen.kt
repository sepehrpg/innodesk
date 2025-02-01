package com.innodesk.project_management

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.designsystem.theme.AppTheme


@Composable
fun ProjectManagementMainScreen(){
    AppTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Box(Modifier.padding(innerPadding)){
                Greeting(
                    name = "Project Management",
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}


@Composable
private fun Greeting(name: String, modifier: Modifier = Modifier) {
    Box(Modifier.fillMaxSize()){
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter){
            Image(painterResource(R.drawable.bottombar), contentDescription = "", contentScale = ContentScale.FillWidth)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GreetingPreview() {
    AppTheme {
        Greeting("Android")
    }
}