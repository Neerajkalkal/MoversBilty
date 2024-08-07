package com.example.gangapackagesolution.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.gangapackagesolution.screens.loadingAndErrorScreen.ErrorScreen
import com.example.gangapackagesolution.screens.screenName.Screens
import com.example.gangapackagesolution.ui.theme.lato
import com.google.accompanist.systemuicontroller.rememberSystemUiController


@Composable
fun Login(
    loginViewmodel: loginViewmodel,
    navController: NavHostController
         ) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        color = Color(0xFFF44336), // Change to your desired color
        darkIcons = false,

        )
    val phoneNo = remember {
        mutableStateOf("")
    }

    val otp = remember {
        mutableStateOf("")
    }

    val otpEnable = remember {
        mutableStateOf(false)
    }

    val phoneEnable = remember {
        mutableStateOf(true)
    }

    val error = remember {
        mutableStateOf("")
    }

    if (error.value.isNotEmpty()) {
        ErrorScreen(error.value) {
//
            error.value = ""
        }
    }

    // State
    val SendState = loginViewmodel.otpRequestState.collectAsState()
    val verifyState = loginViewmodel.otpVerifyState.collectAsState()

    if (!SendState.value.data.isNullOrBlank()) {
        if (SendState.value.data == phoneNo.value) {
            otpEnable.value = true
            phoneEnable.value = false
        }
    }

    if (
        verifyState.value.data != null
    ) {
        if (verifyState.value.data?.jwt !="Invalid Otp") {
            loginViewmodel.saveJwt(verifyState.value.data!!.jwt)
            loginViewmodel.saveNewUser(verifyState.value.data!!.newUser)
            loginViewmodel.saveEmail(email = phoneNo.value)

            if (verifyState.value.data!!.newUser) {
                navController.navigate(Screens.NewUser.name)

            } else {
                navController.navigate(Screens.Home.name)
            }
        }
        else{
            error.value="Invalid Otp"
            loginViewmodel.resetError()

        }
    }


    Box(modifier = Modifier.fillMaxSize()) {
        Column {


            WaveWithGradient()

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(start = 40.dp, end = 40.dp)
                  ) {


                Text(
                    text = "Hello",
                    fontWeight = FontWeight.W800,
                    fontFamily = lato,
                    fontSize = 50.sp
                    )

                Text(
                    text = "Sign in/Sign up to your Account",
                    fontWeight = FontWeight.W800,
                    fontFamily = lato,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(10.dp),
                    textAlign = TextAlign.Center
                    )

                TexInput(phoneNo, "Email", Icons.Default.Email, phoneEnable.value)

                TexInput(phoneNo = otp, s = "Otp", call = Icons.Default.Info, otpEnable.value)

                Spacer(modifier = Modifier.height(60.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                   ) {
                    Text(
                        text = if (otpEnable.value) "Confirm"
                        else "Send Otp",
                        fontWeight = FontWeight.W800,
                        fontFamily = lato,
                        fontSize = 22.sp,
                        )
                    Spacer(modifier = Modifier.width(5.dp))

                    if (SendState.value.loading || verifyState.value.loading) {
                        CircularProgressIndicator(modifier = Modifier.size(25.dp))

                    } else {
                        Box(
                            modifier = Modifier
                                .background(
                                    brush = Brush.linearGradient(
                                        colors = listOf(
                                            Color(0xFFF44336), Color(0xFFF44336)
                                                       )
                                                                ), shape = CircleShape
                                           )
                                .clickable {

                                    if (!otpEnable.value) {
                                        loginViewmodel.requestOtp(
                                            phoneNo.value
                                                                 )
                                    }

                                    if (!phoneEnable.value) {
                                        if (otp.value.length == 6) {
                                            loginViewmodel.verifyOtp(
                                                phoneNumber = phoneNo.value,
                                                otp = otp.value
                                                                    )

                                        } else {
                                            error.value ="Otp Should Be Of Six Digit"
                                        }
                                    }

                                }

                           ) {
                            Icon(
                                imageVector = Icons.Default.ArrowForward, contentDescription = "",
                                tint = Color.White,
                                modifier = Modifier.padding(
                                    start = 10.dp,
                                    end = 10.dp,
                                    top = 2.dp,
                                    bottom = 2.dp
                                                           )
                                )
                        }
                    }
                }

                if (SendState.value.e != null) {
                    ShowDialogue(SendState.value.e.toString(), loginViewmodel)
                }
                if (verifyState.value.e != null) {
                    ShowDialogue(verifyState.value.e.toString(), loginViewmodel)
                }

            }

        }
    }
}

@Composable
fun ShowDialogue(
    SendState: String,
    loginViewmodel: loginViewmodel
                ) {
    Dialog(onDismissRequest = {

    }) {

        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp)
               ) {
            Column(
                modifier = Modifier.padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
                  ) {


                Text(
                    text = "Something Went Wrong:\n Error:${SendState}",
                    fontFamily = lato,
                    fontWeight = FontWeight.Black,
                    modifier = Modifier.padding(10.dp)

                    )
                Box(
                    modifier = Modifier
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFFF44336), Color(0xFFF44336)
                                               )
                                                        ), shape = CircleShape
                                   )
                        .clickable {

                            loginViewmodel.resetError()


                        }

                   ) {

                    Text(
                        text = "Try Again",
                        color = Color.White,
                        fontFamily = lato,
                        fontWeight = FontWeight.Black,
                        modifier = Modifier.padding(10.dp)
                        )

                }


            }
        }

    }
}

@Composable
fun TexInput(
    phoneNo: MutableState<String>,
    s: String,
    call: ImageVector,
    otpEnable: Boolean
            ) {
    TextField(
        value = phoneNo.value,
        onValueChange = {
            phoneNo.value = it
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedLabelColor = Color.White,
            focusedIndicatorColor = Color.White,
            unfocusedContainerColor = Color.White,
            unfocusedIndicatorColor = Color.White,
            disabledContainerColor = Color.White,
            disabledIndicatorColor = Color.White
                                         ),
        modifier = Modifier
            .padding(top = 40.dp)
            .shadow(33.dp)
            .clip(
                shape = RoundedCornerShape(30.dp)
                 )
            .fillMaxWidth(),
        placeholder = {
            Text(
                text = s,
                fontWeight = FontWeight.W800,
                fontFamily = lato,
                )
        },
        leadingIcon = {
            Icon(imageVector = call, contentDescription = "")
        },
        enabled = otpEnable,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Done
                                         )

             )
}

@Preview(showSystemUi = true)
@Composable
fun pre() {

}

