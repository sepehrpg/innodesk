package co.koja.app.ui.component.common

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.designsystem.theme.Orange20


@Composable
fun AppCheckBox(
    label:String,
    defChecked:Boolean,
    enabled:Boolean? = null,
    style: TextStyle = TextStyle(fontSize = 12.sp),
    sizeCheckBox: Dp =20.dp,
    onValueChange:(Boolean)->Unit,
    onclickCheckBox:(Boolean)-> Unit
) {
    var checked by remember { mutableStateOf(defChecked) }
    checked=defChecked
    Row(Modifier.clickable {
        if (!checked && enabled!=false) {
            checked=true
            onclickCheckBox(checked)
        } else {
            checked=false
            onclickCheckBox(checked)
        }
    },verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            //enabled= enabled?:true,
            modifier= Modifier
                .size(sizeCheckBox)
                .padding(1.dp),
            checked = checked,
            onCheckedChange = { checked_ ->
                if (enabled != false){
                    checked = checked_
                    onValueChange(checked_)
                    onclickCheckBox(checked)
                }
                else{
                    onclickCheckBox(checked)
                }
            },
            colors = CheckboxDefaults.colors(
                checkedColor = if(enabled != false) Orange20 else Color.Gray,
                uncheckedColor = if(enabled != false) Orange20 else Color.Gray,
                disabledUncheckedColor = Color.Gray,
                disabledCheckedColor = Color.Gray,
            )
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(text = label,style = style)
    }
}




@Composable
fun AppSimpleCheckBox(modifier: Modifier=Modifier,title:String,checkedState:MutableState<Boolean>,onValueChange: (Boolean) -> Unit){
    Row(
        modifier.clickable {
            checkedState.value = !checkedState.value
            onValueChange(checkedState.value)
        },
        verticalAlignment = Alignment.CenterVertically
    ){
        Checkbox(
            checked = checkedState.value,
            colors = CheckboxDefaults.colors(
                checkedColor = Color(0xFF339AF0),
                uncheckedColor = Color.Gray
            ),
            onCheckedChange = {
                checkedState.value = it
                onValueChange(it)
            },
        )
        Text(text = title, style =MaterialTheme.typography.bodyMedium)
    }
}


@Composable
fun AppCheckBoxCustomized(
    label: String? = null,
    isChecked: Boolean,
    modifier: Modifier = Modifier,
    size: Float = 24f,
    checkedColor: Color = Orange20,
    uncheckedColor: Color = Orange20,
    backgroundColor: Color = Color.Transparent,
    onValueChange: (Boolean) -> Unit
) {
    val checkboxColor: Color by animateColorAsState(if (isChecked) checkedColor else uncheckedColor)
    val density = LocalDensity.current
    val duration = 200

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .heightIn(48.dp) // height of 48dp to comply with minimum touch target size
            .toggleable(
                value = isChecked,
                role = Role.Checkbox,
                onValueChange = onValueChange,
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(radius = 8.dp)
            )
    ) {
        Box(
            modifier = Modifier
                .size(size.dp)
                .background(color = backgroundColor, shape = RoundedCornerShape(8.dp))
                .border(width = 1.5.dp, color = checkedColor, shape = RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.animation.AnimatedVisibility(
                visible = isChecked,
                enter = slideInHorizontally(animationSpec = tween(duration)) {
                    with(density) { (size * -0.5).dp.roundToPx() }
                } + expandHorizontally(
                    expandFrom = Alignment.Start,
                    animationSpec = tween(duration)
                ),
                exit = fadeOut()
            ) {
                Icon(
                    Icons.Default.Check,
                    contentDescription = null,
                    tint = uncheckedColor
                )
            }
        }
        if (label!=null){
            Text(
                modifier = Modifier.padding(start = 5.dp),
                text = label,
            )
        }

    }
}





@Preview(showBackground = true, showSystemUi = true,locale="fa")
@Composable
fun CheckBoxPreview(){
    val value = remember {
        mutableStateOf(true)
    }
    Column(Modifier
        .fillMaxSize()
        .padding(10.dp)) {

        AppCheckBox(
            label = "تست",
            defChecked = true,
            onclickCheckBox = {

            },
            onValueChange = {

            }
        )
        Spacer(modifier = Modifier.height(10.dp))

        var checked by remember { mutableStateOf(false) }
        AppCheckBoxCustomized(
            label = "Checkbox label",
            isChecked = checked,
            onValueChange = { checked = it },
            modifier = Modifier.padding(12.dp)
        )

        AppSimpleCheckBox(
            title = "test",
            onValueChange = {

            },
            checkedState = value
        )

    }
}





