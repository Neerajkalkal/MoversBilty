package com.example.gangapackagesolution

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.gangapackagesolution.models.bill.BillState
import com.example.gangapackagesolution.screens.BillScreen.BillScreenForm
import com.example.gangapackagesolution.screens.BillScreen.showbill.ShowBillScreen
import com.example.gangapackagesolution.screens.nav.MainNav
import com.example.gangapackagesolution.screens.packagingList.packagingScreenViewmodel
import com.example.gangapackagesolution.ui.theme.GangaPackageSolutionTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {


            GangaPackageSolutionTheme {

                MainNav(context = this)

            }
        }
    }
}
@Composable
fun ChangeStatusBarColor() {
    val systemUiController = rememberSystemUiController()


    systemUiController.setSystemBarsColor(
        color = Color(0xFF673AB7), // Change to your desired color
        darkIcons = false,

        )
}

