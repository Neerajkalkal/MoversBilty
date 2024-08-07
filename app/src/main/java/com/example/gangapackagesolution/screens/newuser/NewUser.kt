package com.example.gangapackagesolution.screens.newuser

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gangapackagesolution.models.DetailsToSend
import com.example.gangapackagesolution.repository.TokenManagement
import com.example.gangapackagesolution.screens.MainViewModel
import com.example.gangapackagesolution.screens.TexInput
import com.example.gangapackagesolution.screens.loadingAndErrorScreen.ErrorScreen
import com.example.gangapackagesolution.screens.screenName.Screens
import com.example.gangapackagesolution.ui.theme.latosemibold
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun NewUser(
    navController: NavController,
    token: String?,
    onDone1: String?,
    mainViewModel: MainViewModel,
    TokenManagement: TokenManagement,
    onDone: () -> Unit
           ) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        color = Color(0xFFF44336), // Change to your desired color
        darkIcons = false,

        )
    val name = remember {
        mutableStateOf("")
    }

    val mobile = remember {
        mutableStateOf("")
    }
    val adress = remember {
        mutableStateOf("")
    }
    val CompanyName = remember {
        mutableStateOf("")
    }
    val bankNumber = remember {
        mutableStateOf("")
    }
    val ifsc = remember {
        mutableStateOf("")
    }

    val state =  mainViewModel.newUserState.collectAsState()

    val error = remember {
        mutableStateOf(false)
    }

    if (error.value){
        ErrorScreen(error ="The Fields Denoted By * Are Compulsory") {
        error.value = false
        }
    }

    if (state.value.e!=null){
        ErrorScreen(error = state.value.e.toString()) {
            mainViewModel.resetError()
        }
    }


    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFF44336)

           ) {


        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(20.dp)
                .verticalScroll(rememberScrollState())
              ) {
            Text(
                text = "Thanks For Choosing Us!",
                fontFamily = latosemibold,
                color = Color.White,
                style = MaterialTheme.typography.titleLarge,
                letterSpacing = 3.sp,
                textAlign = TextAlign.Center
                )


            TexInput(
                phoneNo = name, s = "*Name", call = Icons.Default.AccountCircle, otpEnable = true
                    )
            TexInput(
                phoneNo = mobile, s = "*Mobile Number", call = Icons.Default.Call, otpEnable = true
                    )

            TexInput(
                phoneNo = CompanyName, s = "*Company Name", call = Icons.Default.Home,
                otpEnable = true
                    )

            TexInput(
                phoneNo = adress, s = "*Address", call = Icons.Default.LocationOn, otpEnable = true
                    )

            TexInput(
                phoneNo = bankNumber, s = "Bank Account Number", call = Icons.Default.AccountBox,
                otpEnable = true
                    )

            TexInput(
                phoneNo = ifsc, s = "IFSC Code", call = Icons.Default.Info, otpEnable = true
                    )


            Spacer(modifier = Modifier.height(40.dp))

            if (state.value.loading){
                CircularProgressIndicator(color = Color.White, strokeCap = StrokeCap.Round)
            }
            else {
                Button(
                    onClick = {
                        if (name.value.isNotEmpty() && mobile.value.isNotEmpty() && CompanyName.value.isNotEmpty() && adress.value.isNotEmpty()) {

                            val details = onDone1?.let {
                                DetailsToSend(
                                    name = name.value,
                                    mobileNo = mobile.value,
                                    companyName = CompanyName.value,
                                    address = adress.value,
                                    bankAccount = bankNumber.value,
                                    ifscCode = ifsc.value,
                                    email = it
                                             )
                            }


                            details?.let {
                                mainViewModel.saveNewUser(
                                    detailsToSend = it) {
                                    TokenManagement.clearNewUser()
                                    TokenManagement.newUser(false)
                                    Log.d("jndjendejndenednednejdnefbefdevg ", TokenManagement.isNewUser().toString())
                                    navController.navigate(Screens.Home.name)
                                }
                            }


                        }

                        else{
                           error.value = true
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White
                                                        )
                      ) {
                    Text(
                        text = "Submit",
                        color = Color.Black
                        )
                }
            }
        }
    }
}