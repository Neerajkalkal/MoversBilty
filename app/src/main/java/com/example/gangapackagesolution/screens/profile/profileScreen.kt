package com.example.gangapackagesolution.screens.profile

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.amazonaws.auth.CognitoCachingCredentialsProvider
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.PutObjectRequest
import com.example.gangapackagesolution.R
import com.example.gangapackagesolution.screens.MainViewModel
import com.example.gangapackagesolution.screens.loadingAndErrorScreen.LoadingScreen
import com.example.gangapackagesolution.screens.screenName.Screens
import com.example.gangapackagesolution.ui.theme.latosemibold
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File

@Composable
fun ProfileScreen(
    mainViewModel: MainViewModel,
    navController: NavHostController
                 ) {
    val context = LocalContext.current

    val selection = remember { mutableIntStateOf(0) }
    var imagePath by remember { mutableStateOf<String?>(null) }
    val imageBitmap = remember { mutableStateOf<Bitmap?>(null) }

    // Launcher for the image picker intent
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
                                                               ) { uri: Uri? ->
        uri?.let {
            val bitmap = BitmapFactory.decodeStream(context.contentResolver.openInputStream(uri))
            imageBitmap.value = bitmap

            // Convert Uri to file path (if needed)
            val filePath = getFilePathFromUri(context, uri)
            imagePath = filePath
        }
    }

    // AWS credentials
    val credentialsProvider = CognitoCachingCredentialsProvider(
        context, "", Regions                                                               )
    val s3Client = AmazonS3Client(credentialsProvider)

    val state = mainViewModel.userDetails.collectAsState()

    if (state.value.loading){
        LoadingScreen(color = Color.White, indicatorColor = Color.Blue)
    }


    else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
              ) {
            Surface(
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(
                    bottomStart = 40.dp, bottomEnd = 40.dp
                                          )
                   ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack, contentDescription = "",
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(10.dp)
                                .clickable {
                                    navController.navigate(Screens.Home.name)
                                }
                            )

                        Text(
                            text = "Your Profile", style = MaterialTheme.typography.titleLarge,
                            fontFamily = latosemibold, modifier = Modifier
                                .padding(10.dp)
                                .align(Alignment.TopCenter)
                            )
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    if (state.value.data?.companyLogo?.isEmpty() == true){
                        Image(
                            painter = painterResource(
                                R.drawable.image
                                                               ),
                            contentDescription = state.value.data?.companyLogo, modifier = Modifier
                                .clip(CircleShape)
                                .size(100.dp)
                                .clickable {
                                    selection.intValue = 2
                                    imagePickerLauncher.launch("image/*")

                                },
                            contentScale = ContentScale.FillBounds
                             )
                    }
                    else {
                        Image(
                            painter = rememberAsyncImagePainter(
                                model = state.value.data?.companyLogo
                                                               ),
                            contentDescription = state.value.data?.companyLogo, modifier = Modifier
                                .clip(CircleShape)
                                .border(
                                    width = 1.dp, color = Color.Black, CircleShape
                                       )
                                .size(100.dp)
                                .clickable {
                                    selection.intValue = 2
                                    imagePickerLauncher.launch("image/*")

                                },
                            contentScale = ContentScale.FillBounds
                             )
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Company Logo", style = MaterialTheme.typography.bodyMedium,
                        fontFamily = latosemibold
                        )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                shape = RoundedCornerShape(5.dp),
                color = Color.Transparent,
                   ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(
                        text = "Name: ${state.value.data?.name}",
                        style = MaterialTheme.typography.titleMedium
                        )

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Company Name: ${state.value.data?.companyName}",
                        style = MaterialTheme.typography.titleMedium
                        )

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Total Bills Made: ${state.value.data?.totalBill}",
                        style = MaterialTheme.typography.titleMedium
                        )

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Total Quotation Made: ${state.value.data?.totalQuotation}",
                        style = MaterialTheme.typography.titleMedium
                        )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Total Money Receipt Made: ${state.value.data?.totalMoneyReceipt}",
                        style = MaterialTheme.typography.titleMedium
                        )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Phone Number : ${state.value.data?.phoneNumber}",
                        style = MaterialTheme.typography.titleMedium
                        )
                }
            }

            if (imageBitmap.value != null) {
                state.value.data?.phoneNumber?.let {
                    ShowUploadDialog(imageBitmap, selection, imagePath, s3Client, mainViewModel, it)
                }
            }

            Row(modifier = Modifier.padding(20.dp)) {
                Text(text = "Signature:")
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    imageVector = Icons.Default.Edit, contentDescription = "",
                    modifier = Modifier.clickable {
                        selection.intValue = 0
                        imagePickerLauncher.launch("image/*")
                    })
            }

            Surface(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(150.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(10.dp)
                   ) {
                if(state.value.data?.signature.toString().isEmpty()){
                    Image(
                        painter = painterResource(
                            R.drawable.image
                                                 ),
                        contentDescription = state.value.data?.companyLogo, modifier = Modifier
                            .clip(CircleShape)
                            .size(100.dp)
                            .clickable {
                                selection.intValue = 0
                                imagePickerLauncher.launch("image/*")

                            },
                        contentScale = ContentScale.FillBounds
                         )
                }
                else {

                    Image(
                        painter = rememberAsyncImagePainter(
                            model = state.value.data?.signature.toString()
                                                           ), contentDescription = "",
                        modifier = Modifier
                            .height(200.dp)
                            .fillMaxWidth(),
                        contentScale = ContentScale.FillBounds
                         )
                }
            }
            Row(modifier = Modifier.padding(20.dp)) {
                Text(text = "Payment QR Code:")
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    imageVector = Icons.Default.Edit, contentDescription = "",
                    modifier = Modifier.clickable {
                        selection.intValue = 1
                        imagePickerLauncher.launch("image/*")
                    })
            }

            Surface(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(150.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(20.dp)
                   ) {
                if (state.value.data?.qrCode?.isEmpty() == true){
                    Image(
                        painter = painterResource(
                            R.drawable.image
                                                 ),
                        contentDescription = state.value.data?.companyLogo, modifier = Modifier
                            .clip(CircleShape)
                            .size(100.dp)
                            .clickable {
                                selection.intValue = 1
                                imagePickerLauncher.launch("image/*")

                            },
                        contentScale = ContentScale.FillBounds
                         )
                }
                else{
                Image(
                    painter = rememberAsyncImagePainter(
                        model = state.value.data?.qrCode
                                                       ), contentDescription = "",
                    modifier = Modifier,
                    contentScale = ContentScale.FillBounds
                     )}

            }
        }
    }


}

@Composable
fun ShowUploadDialog(
    imageBitmap: MutableState<Bitmap?>,
    selection: MutableIntState,
    imagePath: String?,
    s3Client: AmazonS3Client,
    mainViewModel: MainViewModel,
    phoneNumber: String
                    ) {
    var uploadStatus by remember { mutableStateOf<String?>(null) }

    Dialog(onDismissRequest = { imageBitmap.value = null }) {
        Surface(shape = RoundedCornerShape(10.dp)) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
                  ) {
                Text(
                    text = if (selection.intValue == 0) "Upload As Signature" else if (selection.intValue ==1) "Upload As QR Code" else "Company Logo",
                    style = MaterialTheme.typography.titleMedium
                    )

                Spacer(modifier = Modifier.height(30.dp))

                imageBitmap.value?.let {
                    Image(
                        bitmap = it.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier
                            .size(200.dp)
                            .clip(RoundedCornerShape(20.dp)),
                        contentScale = ContentScale.FillBounds
                         )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        GlobalScope.launch {
                            uploadStatus = "Uploading..."
                            val link = uploadFileToS3(
                                file = File(imagePath.toString()),
                                bucketName = "moversbilty",
                                s3Client = s3Client,
                                "${phoneNumber}_${selection.intValue}"

                                                     )
                            mainViewModel.sendImage(
                                type = if (selection.intValue == 0) "sign" else if(selection.intValue==1) "qr" else "profile",
                                onError = {

                                    uploadStatus = it
                                },
                                onComplete = {
                                    uploadStatus = "Uploaded"
                                    imageBitmap.value = null
                                    mainViewModel.getUserDetails()


                                },
                                link = link.toString()
                                                   )

                        }
                    }) {
                    Text(text = "Upload")
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(text = uploadStatus ?: "")
            }
        }
    }

}


fun getFilePathFromUri(
    context: Context,
    uri: Uri
                      ): String? {
    if (uri.scheme.equals("content", ignoreCase = true)) {
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            val columnIndex = it.getColumnIndex("_data")
            if (columnIndex != -1 && it.moveToFirst()) {
                return it.getString(columnIndex)
            }
        }

    }
    // Check if the URI is a file URI
    else if (uri.scheme.equals("file", ignoreCase = true)) {
        return uri.path
    }
    return null
}


fun uploadFileToS3(
    file: File,
    bucketName: String,
    s3Client: AmazonS3Client,
    s: String
                  ): String? {
    return try {
        val key = "uploads/${s+file.name}"
        s3Client.putObject(PutObjectRequest(bucketName, key, file))
        s3Client.getUrl(bucketName, key).toString()


    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
