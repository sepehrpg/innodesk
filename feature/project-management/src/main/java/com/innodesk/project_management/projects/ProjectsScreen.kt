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
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectsScreen(){
    var isVisible by remember { mutableStateOf(false) }


    Box(Modifier.fillMaxSize().padding(top = 10.dp), contentAlignment = Alignment.BottomCenter){
        LazyColumn(Modifier.fillMaxSize()){
            items(4){
                ProjectItem()
            }
            item { Spacer(Modifier.height(70.dp)) }
        }

        Box(Modifier.fillMaxWidth().padding(bottom = 10.dp,end = 10.dp, start = 10.dp), contentAlignment = Alignment.BottomEnd){
            AppExtendedFloatingActionButton(
                onClick = {isVisible=true},
                containerColor = PrimaryColor,
                icon = { Icon(AppIcons.PostAdd, contentDescription = "") },
                text = { AppText("New Project", color = Color.White) }
            )
        }
    }


    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded=true)

    AppModalBottomSheet(
        isVisible = isVisible,
        onDismissRequest = { isVisible = false },
        containerColor = Color.White,
        sheetState = sheetState ,
        content = {
            AddProjectBottomSheetContent(onCloseBottomSheet = { isVisible=false })
        }
    )
}



@Composable
private fun ProjectItem(){
    Column(Modifier.fillMaxWidth()){
        Box(Modifier.fillMaxWidth().padding(vertical = 20.dp,horizontal = 15.dp), contentAlignment = Alignment.Center){
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
                Box(){
                    Icon(AppIcons.Folder, contentDescription = "Folder", tint = Color.Gray)
                }
                Spacer(Modifier.width(5.dp))
                Box(Modifier.weight(1f)){
                    AppText("Project One", color = Color.Black)
                }
                Spacer(Modifier.width(5.dp))
                AppText("1")
                Spacer(Modifier.width(5.dp))
                Box(){
                    Icon(AppIcons.MoreHoriz, contentDescription = "MoreHoriz", tint = Color.Gray)
                }
            }
        }
        HorizontalDivider(thickness = 4.dp, color = Color(0xFFEEEEEE))
    }
}


@Composable
private fun ProjectTemplateItem(checked:Boolean){
    Column(Modifier.fillMaxWidth()){
        Box(Modifier.fillMaxWidth().padding(vertical = 10.dp,horizontal = 4.dp), contentAlignment = Alignment.Center){
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
                /*Box(){
                    if (checked){
                        Icon(AppIcons.Done, contentDescription = "Folder", tint = PrimaryColor)
                    }
                    else{
                        Icon(AppIcons.Folder, contentDescription = "Folder", tint = Color.Transparent)
                    }
                }*/
                Spacer(Modifier.width(5.dp))
                Box(Modifier.weight(1f)){
                    AppText("Kanban", color = Color.Gray)
                }
                Spacer(Modifier.width(5.dp))
                Box(){
                    Icon(AppIcons.MoreHoriz, contentDescription = "MoreHoriz", tint = Color.Gray)
                }
            }
        }
        HorizontalDivider(thickness = 0.1.dp, color = Color(0xFFEEEEEE))
    }
}




@Composable
private fun AddProjectBottomSheetContent(onCloseBottomSheet:()->Unit){
    var projectName by remember { mutableStateOf("") }
    LazyColumn (
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
    ) {
        item {
            val textStyle = TextStyle(color = Color.Black, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Box(Modifier.padding(horizontal = 5.dp)){
                AppBasicTextField(
                    value = projectName,
                    onValueChange = {projectName=it},
                    textStyle = textStyle,
                    placeholder = { AppText("Project Name", style = textStyle.copy(color = Color.Gray)) },
                )
            }
        }

        item{
            Column(Modifier.padding(top = 20.dp)){
                Box(Modifier.padding(horizontal = 5.dp)){
                    AppText("Access", fontSize = 18.sp, color = Color.DarkGray)
                }
                val list = listOf(
                    AppCustomLeadingIconTabItem(id = 1,text = {Text("Company", fontSize = 11.sp, fontWeight = FontWeight.Bold)},
                        icon = { Icon(modifier = Modifier.size(20.dp), imageVector = Icons.Default.Groups, contentDescription = "Home Icon") },
                    ),
                    AppCustomLeadingIconTabItem(id = 2,text ={Text("Private", fontSize = 10.sp, fontWeight = FontWeight.Bold)},
                        icon = { Icon(modifier = Modifier.size(20.dp),imageVector = Icons.Default.Group, contentDescription = "Home Icon") } ),
                    AppCustomLeadingIconTabItem(id = 3,text ={Text("Personal", fontSize = 10.sp, fontWeight = FontWeight.Bold)},
                        icon = { Icon(modifier = Modifier.size(20.dp),imageVector = Icons.Default.Person, contentDescription = "Home Icon") } ,),
                )
                Box(Modifier.fillMaxWidth()){
                    AppCustomLeadingIconTab(
                        item = list,
                        selectedContentColor = PrimaryColor,
                        unselectedContentColor = Color.Gray,
                        onClick = {
                            list.map { it.nonSelected() }
                            list[it].isSelected()
                        }
                    )
                }
            }
        }


        item{
            Column(Modifier.padding(top = 40.dp)){
                Box(Modifier.padding(horizontal = 5.dp)){
                    AppText("Template", fontSize = 18.sp, color = Color.DarkGray)
                }

                repeat(3){
                    ProjectTemplateItem(if(it==1) true else false)
                }

            }
        }

        item{ Spacer(Modifier.height(20.dp)) }

        item {
            Box(Modifier.fillMaxWidth().padding(horizontal = 5.dp, vertical = 10.dp)){
                AppButton(
                    onClick = {},
                    content = { Text("Create") },
                    modifier = Modifier.fillMaxWidth(),
                    elevation =  ButtonDefaults.buttonElevation(defaultElevation=10.dp),
                    buttonColors = ButtonDefaults.buttonColors(containerColor = PrimaryColor),
                    contentPadding = PaddingValues(15.dp),
                    enabled = true,
                    border = null,
                    shape = RoundedCornerShape(10.dp),
                )
            }
        }


        item{ Spacer(Modifier.height(10.dp)) }

    }
}



@Preview(showBackground = true)
@Composable
fun ProjectScreenPreview(){
    ProjectsScreen()
}