package co.koja.app.ui.component.common
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.designsystem.theme.Red20
import com.example.designsystem.theme.Blue20
import com.example.designsystem.theme.Green20



@Composable
fun AppSnackBarPrimaryResponseMessage(status:String, message: String, performAction: () -> Unit) {
    Column(Modifier.fillMaxHeight(),verticalArrangement = Arrangement.Top) {
        Box(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 20.dp)
                .fillMaxWidth()
                .background(
                    color = if (status == "ok") Green20 else Red20,
                    shape = RoundedCornerShape(10.dp)
                )
        ) {
            Row(
                Modifier.padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(Modifier.weight(1f)) {
                    Text(
                        text = message,
                        fontSize = 14.sp,
                        color = if (status=="ok") Color.White else Color.White,
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .fillMaxWidth()
                    )
                }
                Box(){
                    Icon(
                        imageVector = if (status=="ok") Icons.Filled.Done else Icons.Filled.Error,
                        contentDescription = "",
                        tint = if (status=="ok") Color.White else Color.White
                    )
                }

            }
        }
    }
}



@Composable
fun AppSnackBarSecondaryResponseMessage(status:String, message: String, performAction: () -> Unit) {
    Column(Modifier.fillMaxHeight(),verticalArrangement = Arrangement.Bottom) {
        Box(
            Modifier
                .fillMaxWidth()
                //.padding(horizontal = 20.dp, vertical = 20.dp)
                //.background(color = if (status=="ok") doneColorSnackBAr else errorColorSnackBar, shape = RoundedCornerShape(10.dp))
                .background(color = if (status == "ok") Green20 else Red20)
        ) {
            Row(
                Modifier.padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(Modifier.weight(1f)) {
                    Text(
                        text = message,
                        fontSize = 14.sp,
                        color = if (status=="ok") Color.White else Color.White,
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .fillMaxWidth()
                    )
                }
                Box(){
                    Icon(
                        imageVector = if (status=="ok") Icons.Filled.Done else Icons.Filled.Error,
                        contentDescription = "",
                        tint = if (status=="ok") Color.White else Color.White
                    )
                }

            }
        }
    }
}



@Composable
fun AppSnackBarTertiaryResponseMessageTypeThree(status:String, message: String, performAction: () -> Unit) {
    Column(verticalArrangement = Arrangement.Bottom) {
        Box(
            Modifier
                //.padding(horizontal = 20.dp, vertical = 20.dp)
                //.background(color = if (status=="ok") doneColorSnackBAr else errorColorSnackBar, shape = RoundedCornerShape(10.dp))
                .background(color = if (status=="ok" || status=="200") Green20 else if(status=="help.txt") Color(0xFF1971C2) else Red20)
        ) {
            Row(
                Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(){
                    Icon(
                        imageVector = if (status=="ok" || status=="200") Icons.Filled.Done else Icons.Filled.Error,
                        contentDescription = "",
                        tint = if (status=="ok" || status=="200") Color.White else Color.White
                    )
                }
                Box(Modifier.weight(1f), contentAlignment = Alignment.CenterEnd) {
                    Text(
                        text = message,
                        fontSize = 14.sp,
                        color = if (status=="ok" || status=="200") Color.White else Color.White,
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .fillMaxWidth(),
                    )
                }
            }
        }
    }
}





@Composable
fun AppSnackBarExitApplication(status:String, message: String, performAction: () -> Unit) {
    Column(verticalArrangement = Arrangement.Bottom) {
        Box(
            Modifier
                //.padding(horizontal = 20.dp, vertical = 20.dp)
                //.background(color = if (status=="ok") doneColorSnackBAr else errorColorSnackBar, shape = RoundedCornerShape(10.dp))
                .background(color = Blue20)
        ) {
            Row(
                Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(){
                    Icon(
                        imageVector = if (status=="ok" || status=="200") Icons.Filled.Done else Icons.Filled.Error,
                        contentDescription = "",
                        tint = if (status=="ok" || status=="200") Color.White else Color.White
                    )
                }
                Box(Modifier.weight(1f), contentAlignment = Alignment.CenterEnd) {
                    Text(
                        text = message,
                        fontSize = 14.sp,
                        color = if (status=="ok" || status=="200") Color.White else Color.White,
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .fillMaxWidth(),
                    )
                }
            }
        }
    }


}