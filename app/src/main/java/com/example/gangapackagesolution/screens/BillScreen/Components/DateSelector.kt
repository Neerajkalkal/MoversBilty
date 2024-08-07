package com.example.gangapackagesolution.screens.BillScreen.Components

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gangapackagesolution.ui.theme.lato
import com.example.gangapackagesolution.ui.theme.latosemibold

@Composable
fun DateSelector(selectedDate: MutableState<String>, modifier: Modifier, title: String? = null) {
    val context = LocalContext.current

    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
            selectedDate.value = "$selectedYear-${selectedMonth + 1}-$selectedDay"
        }, year, month, day
    )


    Column(modifier = modifier.clickable {
        datePickerDialog.show()
    }) {

        Text(text = title ?: "", style = MaterialTheme.typography.titleMedium, color = Color.White,
             fontFamily = latosemibold)

        Surface(
            modifier = modifier, shape = RoundedCornerShape(10.dp), color = Color.White
        ) {
            Text(
                text = selectedDate.value,
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}