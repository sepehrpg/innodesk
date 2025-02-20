package com.innodesk.project_management.projects

import android.util.Log
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.MoreHoriz
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import com.example.designsystem.component.AppBasicTextField
import com.example.designsystem.component.AppBottomSheetScaffold
import com.example.designsystem.component.AppButton
import com.example.designsystem.component.AppCustomLeadingIconTab
import com.example.designsystem.component.AppCustomLeadingIconTabItem
import com.example.designsystem.component.AppExtendedFloatingActionButton
import com.example.designsystem.component.AppFilledTonalIconButton
import com.example.designsystem.component.AppModalBottomSheet
import com.example.designsystem.component.AppText
import com.example.designsystem.icon.AppIcons
import com.example.designsystem.theme.GradientColor1
import com.example.designsystem.theme.PrimaryColor
import com.example.designsystem.theme.ThemePreviews
import com.example.designsystem.theme.ThemePreviewsWithBackground
import com.example.designsystem.theme.ThemePreviewsWithShowBackgroundAndShowSystemUi
import com.example.designsystem.theme.ThemePreviewsWithShowSystemUi
import com.innodesk.project_management.projects.component.BottomSheetsProject
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectsScreen(){
    var isVisible = remember { mutableStateOf(false) }

    Box(Modifier.fillMaxSize().padding(top = 10.dp), contentAlignment = Alignment.BottomCenter){
        LazyColumn(Modifier.fillMaxSize()){
            items(4){
                ProjectItem()
            }
            item { Spacer(Modifier.height(70.dp)) }
        }

        Box(Modifier.fillMaxWidth().padding(bottom = 10.dp,end = 10.dp, start = 10.dp), contentAlignment = Alignment.BottomEnd){
            AppExtendedFloatingActionButton(
                onClick = {isVisible.value=true},
                containerColor = PrimaryColor,
                icon = { Icon(AppIcons.PostAdd, contentDescription = "") },
                text = { AppText("New Project", fontSize = 14.sp, color = Color.White) }
            )
        }
    }
    BottomSheetsProject(isVisible, onCancelClick = {}, onDoneClick = {})
}



@Composable
private fun ProjectItem(){
    Column(Modifier.fillMaxWidth()){
        Box(Modifier.fillMaxWidth().padding(vertical = 20.dp,horizontal = 15.dp), contentAlignment = Alignment.Center){
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
                Box(){
                    Icon(AppIcons.Folder, contentDescription = "Folder", tint = Color.Gray, modifier = Modifier.size(22.dp))
                }
                Spacer(Modifier.width(5.dp))
                Box(Modifier.weight(1f)){
                    AppText("Project One", color = Color.Black, fontSize = 14.sp)
                }
                Spacer(Modifier.width(5.dp))
                AppText("1", fontSize = 14.sp)
                Spacer(Modifier.width(5.dp))
                Box(){
                    Icon(AppIcons.MoreHoriz, contentDescription = "MoreHoriz", tint = Color.Gray, modifier = Modifier.size(22.dp))
                }
            }
        }
        HorizontalDivider(thickness = 4.dp, color = Color(0xFFEEEEEE))
    }
}











@Preview(showBackground = true)
@Composable
fun ProjectScreenPreview(){
    ProjectsScreen()
}