/*
package com.example.designsystem.component
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalViewConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.PopupProperties
import androidx.compose.ui.zIndex
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun AppPrimaryBasicTextField(
    modifier: Modifier = Modifier,
    value: MutableState<String>,
    singleLine:Boolean,
    keyboardType:KeyboardType = KeyboardType.Text,
    keyboardOptions:KeyboardOptions=KeyboardOptions.Default.copy(
        keyboardType = keyboardType,
        imeAction = ImeAction.Done,
    ),
    textStyle:TextStyle=MaterialTheme.typography.labelMedium.copy(textAlign = TextAlign.Center),
    cursorBrush: Brush = SolidColor(Color.Transparent),
    readOnly:Boolean=false,
    language:String="en",
    trail:String="",
    isPassword: Boolean = false,
    placeHolder:String="",
    placeHolderColor:Color=Color.LightGray,
    enableCustomCopyPaste:Boolean = false,
    defaultModifier : Boolean = true,
    onPasteRequested: () -> Unit = {},
    onValueChange: (String) -> Unit,
){
    val context = LocalContext.current
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    var showPassword by remember { mutableStateOf(false) }

    var enabled by remember { mutableStateOf(true) }
    val interactionSource = remember { MutableInteractionSource() }
    val customTextSelectionColors = TextSelectionColors(
        handleColor = if(enableCustomCopyPaste) Color.Transparent else AppSecondaryLightColor,
        backgroundColor = AppSecondaryLightColor
    )
    Box(if(defaultModifier) Modifier.height(48.dp) else modifier, contentAlignment = Alignment.Center){
        CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
            BasicTextField(
                visualTransformation = if (isPassword && !showPassword) PasswordVisualTransformation() else VisualTransformation.None,
                readOnly = readOnly,
                singleLine = singleLine,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done,
                ),
                keyboardActions= KeyboardActions(
                    onDone = { focusManager.clearFocus(true) }
                ),
                modifier = modifier.focusRequester(focusRequester),
                textStyle = textStyle.copy(fontFamily = iranSans),
                value = value.value+trail,
                onValueChange = {
                    onValueChange(it)
                    value.value = it
                },
                cursorBrush = cursorBrush,
                interactionSource = interactionSource,
                decorationBox = @Composable { innerTextField ->
                    TextFieldDefaults.TextFieldDecorationBox(
                        value = value.value,
                        contentPadding = PaddingValues(0.dp),
                        innerTextField = innerTextField,
                        enabled = enabled,
                        singleLine = singleLine,
                        visualTransformation = VisualTransformation.None,
                        interactionSource = interactionSource,
                        placeholder =  { Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterStart) {
                            Text(placeHolder, style = textStyle.copy(fontFamily = iranSans,color = placeHolderColor))
                        } }
                    )
                }
            )

            if (enableCustomCopyPaste){
                var expanded by remember {
                    mutableStateOf(false)
                }
                val clipboardManager = LocalClipboardManager.current
                val viewConfiguration = LocalViewConfiguration.current
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
                //if (clipboardManager.getText().toString().isNotEmpty() && clipboardManager.getText().toString()!="null"){
                if (true){
                    DropdownMenu(
                        modifier = Modifier.zIndex(99999f)
                            //.width(60.dp)
                            //.height(30.dp)
                            .background(Color.White)
                            .clip(shape = RoundedCornerShape(12.dp)),
                        expanded = expanded,
                        properties = PopupProperties(focusable = false),
                        onDismissRequest = { expanded = false },
                        //offset = DpOffset(0.dp,(-10).dp)
                    ) {
                        DropdownMenuItem(
                            {
                                Spacer(modifier = Modifier.height(5.dp))
                                Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.End) {
                                    Box(modifier=Modifier.fillMaxWidth().clickable {
                                        expanded = false
                                        CopyToClipBoard.copy(context,value.value)
                                    }, contentAlignment = Alignment.CenterEnd) {
                                        Text(text = "copy", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold, color = Color.Black))
                                    }
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Box(modifier=Modifier.fillMaxWidth().clickable {
                                        expanded = false
                                        onPasteRequested.invoke()
                                        Log.i("BASICTEXTFIELDDATA",clipboardManager.getText().toString())
                                        onValueChange(clipboardManager.getText().toString())
                                        value.value = clipboardManager.getText().toString()
                                    }, contentAlignment = Alignment.CenterEnd) {
                                        Text(text = "paste", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold, color = Color.Black))
                                    }
                                }
                            },
                            onClick = {

                            },
                            modifier = Modifier,
                            //.width(60.dp).height(30.dp)
                            //contentPadding= PaddingValues(0.dp)
                        )

                    }
                }

            }

        }
    }

}





@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AppOutlinedTextFieldSearchView(value: String, searchText:String= LocalContext.current.getString(
    R.string.search), textColor:Color=Color.White, hintColor: Color = Color.White, haveIcon:Boolean=true, onValueChange: (String) -> Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    var expanded by remember { mutableStateOf(false) }
    var size by remember { mutableStateOf(Size.Zero) }
    androidx.compose.material.OutlinedTextField(
        textStyle = MaterialTheme.typography.labelSmall.copy(color = textColor),
        value = value,
        onValueChange = onValueChange,
        keyboardOptions=KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        keyboardActions=KeyboardActions(onDone = {focusManager.clearFocus()}),
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
            Text(
                text = searchText,
                color = hintColor,
                style = MaterialTheme.typography.labelSmall
                //textStyle = MaterialTheme.typography.bodySmall.copy(color = textColor),
            )
        },
        trailingIcon = {
            if (haveIcon){
                Icon(
                    Icons.Default.Search, "contentDescription",
                    Modifier.clickable {
                        expanded = !expanded
                    },tint = Color.Gray
                )
            }
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            disabledBorderColor = Color.Transparent
        )
    )
}







@Composable
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
}

*/
