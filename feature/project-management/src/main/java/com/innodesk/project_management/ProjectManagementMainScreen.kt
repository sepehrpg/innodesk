package com.innodesk.project_management

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material.icons.rounded.PersonAddAlt
import androidx.compose.material.icons.rounded.UnfoldMore
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilledIconToggleButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.FilledTonalIconToggleButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.designsystem.component.AppBackground
import com.example.designsystem.component.AppGradientBackground
import com.example.designsystem.theme.AppTheme
import com.example.designsystem.theme.GradientColors


@Composable
fun ProjectManagementMainScreen(){
    AppTheme {
        AppGradientBackground(
            //gradientColors = GradientColors(Color(0xFFee9ca7),Color(0xFFffdde1)),
            //gradientColors = GradientColors(Color(0xFF2980B9),Color(0xFF6DD5FA)),
            modifier = Modifier.fillMaxSize()
        ){
            Body()
        }
    }
}


@Composable
private fun Body() {


    Box(Modifier.fillMaxSize().safeContentPadding()){
        Box(Modifier.fillMaxWidth().padding(vertical = 10.dp, horizontal = 15.dp)){
            Row(Modifier.fillMaxWidth()){
                Box(Modifier.weight(1f)){
                    Icon(painterResource(com.example.designsystem.R.drawable.core_designsystem_user_add), contentDescription = "")
                }
                Box(Modifier.weight(2f), contentAlignment = Alignment.Center){
                    Row(verticalAlignment = Alignment.CenterVertically){
                        Text("Sepehrpg", color = Color.Black, fontWeight = FontWeight.Bold)
                        Icon(Icons.Rounded.UnfoldMore, contentDescription = "",Modifier.size(15.dp))
                    }
                }
                Box(Modifier.weight(1f)){
                }
            }
        }


        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter){
            Image(painterResource(R.drawable.bottombar), contentDescription = "", contentScale = ContentScale.FillWidth)
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun ProjectManagementMainScreenPreview() {
    AppTheme {
        Body()
    }
}