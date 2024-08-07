package com.example.gangapackagesolution.screens.BillScreen.Components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gangapackagesolution.ui.theme.latosemibold

@Composable
fun ExtendedField(
    title: String,
    stateHolder: MutableState<String>,
    height: Dp = 200.dp,
    modifier: Modifier = Modifier
) {

    Text(
        text = title,
        style = MaterialTheme.typography.bodyMedium,
        fontFamily = latosemibold,
        color = Color.White
    )
    Surface(
        modifier = modifier,
        color = Color.White,
        shape = RoundedCornerShape(10.dp)
    ) {
        TextField(
            value = stateHolder.value,
            onValueChange = { stateHolder.value = it },
            singleLine = false,
            textStyle = TextStyle(fontSize = 20.sp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
        )
    }

}