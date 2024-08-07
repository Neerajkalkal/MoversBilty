package com.example.gangapackagesolution.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.gangapackagesolution.R
import com.example.gangapackagesolution.screens.MainViewModel
import com.example.gangapackagesolution.ui.theme.latosemibold

@Composable
fun ProfileScreen(
    mainViewModel: MainViewModel,
    navController: NavHostController
                 ) {
    val state = mainViewModel.userDetails.collectAsState()

    Column {

        Surface(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth(),
            color = Color(0xFF673AB7),
            shape = RoundedCornerShape(
                bottomStart = 40.dp,
                bottomEnd = 40.dp
                                      )


               ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Your Profile", color = Color.White,
                    style = MaterialTheme.typography.titleLarge,
                    fontFamily = latosemibold, modifier = Modifier.padding(10.dp)
                    )


                Spacer(modifier = Modifier.height(10.dp))
                Image(
                    painter = rememberAsyncImagePainter(model = state.value.data!!.companyLogo),
                    contentDescription = "",
                    modifier = Modifier
                        .clip(CircleShape)
                        .border(
                            width = 1.dp, color = Color.White,
                            CircleShape
                               )
                        .size(100.dp)
                     )

                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Company Logo",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium,
                    fontFamily = latosemibold
                    )


            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
            ) {
            Column(modifier = Modifier.padding(10.dp)) {

                Text(
                    text = "Name:${state.value.data!!.name}",
                    style = MaterialTheme.typography.titleMedium
                    )


                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Company Name: ${state.value.data!!.companyName}",
                    style = MaterialTheme.typography.titleMedium
                    )

                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Total Bills Made: ${state.value.data!!.totalBill}",
                    style = MaterialTheme.typography.titleMedium
                    )

                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Total Quotation Made: ${state.value.data!!.totalQuotation}",
                    style = MaterialTheme.typography.titleMedium
                    )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Total Money Receipt Made: ${state.value.data!!.totalMoneyReceipt}",
                    style = MaterialTheme.typography.titleMedium
                    )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Phone Number : ${state.value.data!!.phoneNumber}",
                    style = MaterialTheme.typography.titleMedium
                    )
            }
        }



        Text(text = "   Signature:")

        Surface(modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .size(200.dp)
            .fillMaxWidth()) {

            Image(
                painter = rememberAsyncImagePainter(model = "https://www.shutterstock.com/image-vector/fake-autograph-samples-handdrawn-signatures-260nw-2325821623.jpg"),
                contentDescription = "",
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.FillBounds
                 )
        }


    }



}