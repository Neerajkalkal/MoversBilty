package com.example.gangapackagesolution.screens.Help

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.gangapackagesolution.R
import com.example.gangapackagesolution.ui.theme.latosemibold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Help(showHelp: MutableState<Boolean>) {


    ModalBottomSheet(onDismissRequest = { showHelp.value = false },
                     containerColor = Color.White) {
        HelpContent()
    }
}

@Composable
fun HelpContent() {
    Surface(
        modifier = Modifier
            .padding(20.dp),
        color = Color.White
           ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
              ) {


            Image(
                painter = painterResource(id = R.drawable.help), contentDescription = "",
                modifier = Modifier
                    .padding(20.dp)
                    .size(100.dp)
                 )

            Text(
                text = "We are here to help you",
                style = MaterialTheme.typography.titleMedium,
                fontFamily = latosemibold,
                textAlign = TextAlign.Center
                )

            Column(modifier = Modifier.fillMaxWidth()) {

                Spacer(modifier = Modifier.padding(10.dp))
                Text(
                    text = "If You have Any Kind of Problem related to if any function is not working or any Subscription issue then please Whatsapp on below Number with Screenshot our technical team will reach out you with in 24 hours",
                    style = MaterialTheme.typography.bodyMedium,
                    fontFamily = latosemibold,
                    textAlign = TextAlign.Center,
                    color = Color.Gray
                    )
                Spacer(modifier = Modifier.padding(10.dp))


            }
            val context = LocalContext.current
            Row(modifier = Modifier.clickable {
                val phoneNumber = "+919802360213"
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse("https://wa.me/$phoneNumber")
                }
                context.startActivity(intent)
            }) {

                Image(
                    painter = painterResource(id = R.drawable.whatsapp), contentDescription = "",
                    modifier = Modifier
                        .size(30.dp)
                        .padding(5.dp)
                     )
                Text(text = "9802360213")

            }
            Spacer(modifier = Modifier.height(60.dp))
        }

    }
}
