package com.example.gangapackagesolution.screens.notifications

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.gangapackagesolution.R
import com.example.gangapackagesolution.models.notifications.Notifications
import com.example.gangapackagesolution.screens.MainViewModel
import com.example.gangapackagesolution.screens.loadingAndErrorScreen.ErrorScreen
import com.example.gangapackagesolution.screens.loadingAndErrorScreen.LoadingScreen
import com.example.gangapackagesolution.screens.quotationScreen.Header
import java.time.LocalDate

@Composable
fun NotificationScreen(
    mainViewModel: MainViewModel,
    value: Color,
    navController: NavController
                      ) {

    val state = mainViewModel.notifications.collectAsState()

    LaunchedEffect(key1 = Unit) {
        mainViewModel.getNotifications()
        mainViewModel.sendNotificationsUpdate()
    }


    if (state.value.loading) {
        LoadingScreen(color = Color.White, indicatorColor = value)
    }
    if (state.value.e != null) {
        ErrorScreen {
            mainViewModel.getNotifications()
        }
    }
    if (state.value.data != null) {

        state.value.data?.let { data ->
            val notificationsSorted = data.sortedByDescending {
                it.id
            }

            Surface(modifier = Modifier.fillMaxSize()) {
                Column {


                    Header(text = "Notifications", color = value) {
                        mainViewModel.getUserDetails()
                        navController.popBackStack()
                    }

                    LazyColumn {
                        items(notificationsSorted) {
                            NotificationDisplay(it)
                        }
                    }


                }
            }

        }
    }
}

@Composable
fun NotificationDisplay(it: Notifications) {
    Column(modifier = Modifier.padding(top = 20.dp, bottom = 10.dp, start = 4.dp, end = 6.dp)) {


        Row(verticalAlignment = Alignment.CenterVertically) {
            if (it.image.isNotEmpty()) {
                Image(
                    painter = rememberAsyncImagePainter(model = it.image), contentDescription = "",
                    modifier = Modifier
                        .size(80.dp)
                        .weight(0.3f), contentScale = ContentScale.FillHeight
                     )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.notification__1_),
                    contentDescription = "",
                    modifier = Modifier
                        .weight(0.3f)
                        .size(40.dp)

                     )
            }

            Text(
                text = it.body,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f)
                )


        }

        Text(
            text = "🕛 ${it.date}", modifier = Modifier
                .align(Alignment.End)
                .padding(10.dp)
            )
        HorizontalDivider()
    }
}

