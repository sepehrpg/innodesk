/*
package com.example.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults.outlinedTextFieldColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.example.designsystem.config.direction.AppDirection
import com.example.common.extension.mirrorReverse
import com.example.designsystem.icon.AppIcons
import com.example.designsystem.theme.Orange20
import com.example.designsystem.theme.Blue20
import com.example.designsystem.theme.iranSans





@Composable
fun AppPrimaryBasicTextField(
    modifier: Modifier = Modifier,
    value: MutableState<String>,
    singleLine: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(
        keyboardType = keyboardType,
        imeAction = imeAction,
    ),
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium.copy(textAlign = TextAlign.Center),
    cursorBrush: Brush = SolidColor(Blue20),
    readOnly: Boolean = false,
    language: String = "fa",
    trail: String = "",
    isPassword: Boolean = false,
    placeHolder: String = "",
    placeHolderColor: Color = Color.LightGray,
    enableCustomCopyPaste: Boolean = false,
    defaultModifier: Boolean = true,
    trailingIcon: @Composable (() -> Unit)? = null,
    onPasteRequested: () -> Unit = {},
    onValueChange: (String) -> Unit,
) {
    val context = LocalContext.current
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    var showPassword by remember { mutableStateOf(false) }

    var enabled by remember { mutableStateOf(true) }
    val interactionSource = remember { MutableInteractionSource() }
    val customTextSelectionColors = TextSelectionColors(
        handleColor = if (enableCustomCopyPaste) Color.Transparent else Blue20,
        backgroundColor = Orange20,
    )
    CompositionLocalProvider(AppDirection.appDirectionLayoutProperty(language),LocalTextSelectionColors provides customTextSelectionColors) {
        Box(if (defaultModifier) Modifier
            .height(48.dp)
            .padding(horizontal = 7.dp, vertical = 7.dp) else modifier.padding(horizontal = 7.dp, vertical = 7.dp), contentAlignment = Alignment.CenterStart) {
            Row(Modifier.fillMaxHeight().fillMaxWidth(),verticalAlignment = Alignment.CenterVertically) {
                BasicTextField(
                    visualTransformation = if (isPassword && !showPassword) PasswordVisualTransformation() else VisualTransformation.None,
                    readOnly = readOnly,
                    singleLine = singleLine,
                    keyboardOptions = keyboardOptions,
                    keyboardActions = KeyboardActions(
                        onDone = { focusManager.clearFocus(true) }
                    ),
                    modifier = modifier
                        .focusRequester(focusRequester)
                        .background(Color.White)
                        .weight(1f),
                    enabled = enabled,
                    textStyle = textStyle.copy(fontFamily = iranSans, textAlign = TextAlign.Start),
                    value = value.value + trail,
                    onValueChange = {
                        onValueChange(it)
                        value.value = it
                    },
                    cursorBrush = cursorBrush,
                    interactionSource = interactionSource,
                    decorationBox = { innerTextField ->
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.CenterStart) {
                            if (value.value.isEmpty()) {
                                AppText(placeHolder, style = textStyle.copy(fontFamily = iranSans, color = placeHolderColor))
                            }
                            innerTextField()
                        }
                    }
                )
                if (trailingIcon != null) {
                    trailingIcon()
                }
            }
        }
    }

}







@Composable
fun AppSearchTextFieldWithMenu(
    modifier: Modifier = Modifier,
    value: MutableState<String>,
    singleLine: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(
        keyboardType = keyboardType,
        imeAction = imeAction,
    ),
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium.copy(textAlign = TextAlign.Center),
    cursorBrush: Brush = SolidColor(Blue20),
    readOnly: Boolean = false,
    language: String = "fa",
    trail: String = "",
    isPassword: Boolean = false,
    placeHolder: String = "",
    placeHolderColor: Color = Color.LightGray,
    enableCustomCopyPaste: Boolean = false,
    defaultModifier: Boolean = true,
    trailingIcon: @Composable (() -> Unit)? = null,
    onPasteRequested: () -> Unit = {},
    onValueChange: (String) -> Unit,
) {
    val context = LocalContext.current
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    var showPassword by remember { mutableStateOf(false) }

    var enabled by remember { mutableStateOf(true) }
    val interactionSource = remember { MutableInteractionSource() }
    val customTextSelectionColors = TextSelectionColors(
        handleColor = if (enableCustomCopyPaste) Color.Transparent else Blue20,
        backgroundColor = Orange20,
    )
    CompositionLocalProvider(AppDirection.appDirectionLayoutProperty(language),LocalTextSelectionColors provides customTextSelectionColors) {
        Box(if (defaultModifier) Modifier
            .height(48.dp)
            .padding(horizontal = 7.dp, vertical = 7.dp) else modifier.padding(horizontal = 7.dp, vertical = 7.dp), contentAlignment = Alignment.CenterStart) {
            Row(Modifier.fillMaxHeight().fillMaxWidth(),verticalAlignment = Alignment.CenterVertically) {
                BasicTextField(
                    visualTransformation = if (isPassword && !showPassword) PasswordVisualTransformation() else VisualTransformation.None,
                    readOnly = readOnly,
                    singleLine = singleLine,
                    keyboardOptions = keyboardOptions,
                    keyboardActions = KeyboardActions(
                        onDone = { focusManager.clearFocus(true) }
                    ),
                    modifier = modifier
                        .focusRequester(focusRequester)
                        .background(Color.White)
                        .weight(1f),
                    enabled = enabled,
                    textStyle = textStyle.copy(fontFamily = iranSans, textAlign = TextAlign.Start),
                    value = value.value + trail,
                    onValueChange = {
                        onValueChange(it)
                        value.value = it
                    },
                    cursorBrush = cursorBrush,
                    interactionSource = interactionSource,
                    decorationBox = { innerTextField ->
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.CenterStart) {
                            if (value.value.isEmpty()) {
                                AppText(placeHolder, style = textStyle.copy(fontFamily = iranSans, color = placeHolderColor))
                            }
                            innerTextField()
                        }
                    }
                )
                if (trailingIcon != null) {
                    trailingIcon()
                }
            }
        }
    }

}











@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppOutLineTextFieldType1(
    containerModifier: Modifier = Modifier,
    outlineTextFieldModifier: Modifier = Modifier,
    placeholder: String,
    defaultTextValue: String = "",
    trailingIcon: ImageVector?,
    label: @Composable (() -> Unit)? = null,
    isPassword: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(
        keyboardType = KeyboardType.Text
    ),
    outLineColors: TextFieldColors = OutlinedTextFieldDefaults.colors(),
    onValueChange: (String) -> Unit
) {
    var text by remember { mutableStateOf(defaultTextValue) }
    var showPassword by remember { mutableStateOf(false) }
    Box(containerModifier) {
        OutlinedTextField(
            visualTransformation = if (isPassword && !showPassword) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = keyboardOptions,
            modifier = outlineTextFieldModifier,
            textStyle = TextStyle(fontSize = 13.sp, fontFamily = iranSans),
            value = text,
            onValueChange = {
                text = it
                onValueChange(text)
            },
            placeholder = { AppText(text = placeholder, style = TextStyle(color = Color.Gray), fontSize = 13.sp) },
            label = label,
            colors = outLineColors,
            trailingIcon = {
                if (trailingIcon!=null){
                    Icon(
                        imageVector = if (!isPassword) trailingIcon else if(showPassword) Icons.Rounded.VisibilityOff else Icons.Rounded.Visibility,
                        contentDescription = "contentDescription",
                        modifier = Modifier.clickable {
                            if (isPassword){
                                showPassword = !showPassword
                            }
                        },
                        tint = Color.Gray
                    )
                }
            }
        )
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true,locale="fa")
@Composable
fun TextFieldPreview() {
    val value = remember { mutableStateOf("") }
    Column(Modifier.fillMaxSize().padding(20.dp)) {
        

        Box(Modifier.height(52.dp)
            .padding(horizontal = 30.dp)
            .shadow(2.dp, RoundedCornerShape(10.dp),true)
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)
        ){
            AppPrimaryBasicTextField(
                modifier = Modifier.height(52.dp),
                value = value,
                keyboardType = KeyboardType.Number,
                placeHolder = "شماره تلفن خود را وارد کنید",
                trailingIcon = {
                    Icon(
                        imageVector = AppIcons.Call, // You can use any icon here
                        contentDescription = "Clear text",
                        tint = Orange20,
                        modifier = Modifier.mirrorReverse(true).padding(horizontal = 5.dp)
                    )
                }
            ) {

            }
        }
        Spacer(Modifier.height(10.dp))
        AppOutLineTextFieldType1(
                containerModifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFEEEEEE)),
                outlineTextFieldModifier = Modifier,
                placeholder = "نام کاربری",
                outLineColors = outlinedTextFieldColors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    disabledBorderColor = Color.Transparent
                ),
                trailingIcon = Icons.Rounded.Visibility,
                isPassword = true
            ){

            }

    }


    
}



@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AppOutlinedTextFieldSearchView(value: String, searchText: String = "username", textColor: Color = Color.White, hintColor: Color = Color.White, haveIcon: Boolean = true, onValueChange: (String) -> Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    var expanded by remember { mutableStateOf(false) }
    var size by remember { mutableStateOf(Size.Zero) }
    OutlinedTextField(
        textStyle = MaterialTheme.typography.labelSmall.copy(color = textColor),
        value = value,
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clickable { expanded = !expanded }
            .onGloballyPositioned { coordinates ->
                // This value is used to assign to
                // the DropDown the same width
                size = coordinates.size.toSize()
            },
        //label = { Text(text = stringResource(id = R.string.selecting), fontSize = 12.sp)},
        placeholder = {
            AppText(
                text = searchText,
                color = hintColor,
                style = MaterialTheme.typography.labelSmall
                //textStyle = MaterialTheme.typography.bodySmall.copy(color = textColor),
            )
        },
        trailingIcon = {
            if (haveIcon) {
                Icon(
                    Icons.Default.Search, "contentDescription",
                    Modifier.clickable {
                        expanded = !expanded
                    }, tint = Color.Gray
                )
            }
        },
        colors = outlinedTextFieldColors(
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            disabledBorderColor = Color.Transparent
        )
    )
}


*/
/*@Composable
fun TextFieldWithCustomToolBar(
    supportingText: String,
    onPasteRequested: () -> Unit
) {

    var expanded by remember {
        mutableStateOf(false)
    }
    val viewConfiguration = LocalViewConfiguration.current

    val text = remember { mutableStateOf("") }
    val interactionSource = remember { MutableInteractionSource() }

    //wait for a long press action
    LaunchedEffect(interactionSource) {
        var isDown = false
        interactionSource.interactions.collect { interaction ->
            when (interaction) {
                is PressInteraction.Press -> {
                    isDown = true
                    CoroutineScope(Dispatchers.Main).launch {
                        delay(viewConfiguration.longPressTimeoutMillis)
                        if (isDown) expanded = true
                    }
                }

                is PressInteraction.Release -> {
                    isDown = false
                }
            }
        }
    }
    Box {
        TextField(
            interactionSource = interactionSource,
            value = text.value,
            onValueChange = { text.value = it },
            supportingText = { Text(text = supportingText) }
        )

        DropdownMenu(
            expanded = expanded,
            properties = PopupProperties(focusable = false),
            onDismissRequest = { expanded = false },
        ) {
            DropdownMenuItem({ Text(text = "Paste") }, onClick = {
                expanded = false
                onPasteRequested.invoke()
            })
        }
    }
}*//*


*/
