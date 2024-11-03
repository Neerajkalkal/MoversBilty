package com.example.gangapackagesolution.screens.profile

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
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
import com.amazonaws.auth.CognitoCachingCredentialsProvider
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.example.gangapackagesolution.R
import com.example.gangapackagesolution.repository.TokenManagement
import com.example.gangapackagesolution.screens.MainViewModel
import com.example.gangapackagesolution.screens.loadingAndErrorScreen.LoadingScreen
import com.example.gangapackagesolution.screens.screenName.Screens
import com.example.gangapackagesolution.ui.theme.latosemibold
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File

@Composable
fun ProfileScreen(
    mainViewModel: MainViewModel,
    navController: NavHostController,
    color: MutableState<Color>,
    TokenManagement: TokenManagement
                 ) {
    val context = LocalContext.current

    val selection = remember { mutableIntStateOf(0) }
    var imagePath by remember { mutableStateOf<String?>(null) }
    val imageBitmap = remember { mutableStateOf<Bitmap?>(null) }
val imageUri = remember {
    mutableStateOf<Uri?>(null)
}
    // Launcher for the image picker intent
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
                                                               ) { uri: Uri? ->
        uri?.let {
            imageUri.value = uri
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

    if (state.value.loading) {
        LoadingScreen(color = Color.White, indicatorColor = Color.Blue)
    } else {
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
                    if (state.value.data?.companyLogo?.isEmpty() == true) {
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
                    } else {
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
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Choose App Color-",
                        style = MaterialTheme.typography.titleMedium
                        )

                    ColorPicker(color,TokenManagement)

                }

            }






            if (imageBitmap.value != null) {
                state.value.data?.phoneNumber?.let {
                    ShowUploadDialog(imageBitmap, selection, imagePath, s3Client, mainViewModel, phoneNumber = state.value.data!!.phoneNumber,imageUri)
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
                if (state.value.data?.signature.toString().isEmpty()) {
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
                } else {

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
                if (state.value.data?.qrCode?.isEmpty() == true) {
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
                } else {
                    Image(
                        painter = rememberAsyncImagePainter(
                            model = state.value.data?.qrCode
                                                           ), contentDescription = "",
                        modifier = Modifier,
                        contentScale = ContentScale.FillBounds
                         )
                }

            }
        }
    }


}

@Composable
fun ColorPicker(
    color: MutableState<Color>,
    TokenManagement: TokenManagement
               ) {
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.horizontalScroll(
        rememberScrollState())) {

        val list = listOf( Color.Black,Color(0xFF673AB7), Color(0xFFF44336), Color(0xFF00BCD4), Color(0xFFE91E63),
                           Color(0xFFFF0019),Color(0xFFFF9800)
                         )
        list.forEach {
            ColorSurface(color =it , selected =it ==color.value ,color,TokenManagement)
        }
    }
}

@Composable
fun ColorSurface(
    color: Color,
    selected: Boolean,
    color1: MutableState<Color>,
    TokenManagement: TokenManagement
                ) {
    Box(
        modifier = Modifier
            .clickable {
                color1.value = color
                TokenManagement.setColor(color)
            }
            .size(70.dp)
            .background(color = color, shape = RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp)),
       ) {
        if (selected) {
            Icon(
                imageVector = Icons.Default.Check, contentDescription = "",
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(40.dp), tint = Color.White
                )
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
    phoneNumber: String,
    imageUri: MutableState<Uri?>
                    ) {
    var uploadStatus = remember { mutableStateOf<String?>(null) }

    Dialog(onDismissRequest = { imageBitmap.value = null }) {
        Surface(shape = RoundedCornerShape(10.dp)) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
                  ) {
                Text(
                    text = if (selection.intValue == 0) "Upload As Signature" else if (selection.intValue == 1) "Upload As QR Code" else "Company Logo",
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
                val x= LocalContext.current.contentResolver
                Button(
                    onClick = {
                        GlobalScope.launch {
                            uploadStatus.value = "Uploading..."
                            val link = imageUri.value?.let {
                                uploadFileToS3(
                                    imageUri = it,
                                    bucketName = "moversbilty",
                                    s3Client = s3Client,
                                  s =  "${phoneNumber}_${selection.intValue}",
                                    contentResolver = x,
                                    uploadStatus = uploadStatus

                                    )
                            }
                            mainViewModel.sendImage(
                                type = if (selection.intValue == 0) "sign" else if (selection.intValue == 1) "qr" else "profile",
                                onError = {

                                    uploadStatus.value = it
                                },
                                onComplete = {
                                    uploadStatus.value = "Uploaded"
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

                Text(text = uploadStatus.value ?: "")
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

@SuppressLint("SuspiciousIndentation")
fun uploadFileToS3(
    imageUri: Uri,
    contentResolver: ContentResolver,
    bucketName: String,
    s3Client: AmazonS3Client,
    s: String,
    uploadStatus:MutableState<String?>
                  ): String? {
    return try {
        // Step 1: Check the image MIME type
        val mimeType = contentResolver.getType(imageUri)
        val supportedMimeTypes = listOf("image/jpeg", "image/png", "image/bmp", "image/gif")

        if (mimeType !in supportedMimeTypes) {
      uploadStatus.value =      "Unsupported image format"
            return null
        }

        // Step 2: Convert the image to a Bitmap
        val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)

        // Step 3: Compress the Bitmap to JPEG
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream) // Adjust the compression quality as needed
        val compressedImageBytes = outputStream.toByteArray()

        // Step 4: Convert ByteArray to InputStream
        val inputStream = ByteArrayInputStream(compressedImageBytes)

        // Step 5: Set metadata for the object
        val metadata = ObjectMetadata().apply {
            contentLength = compressedImageBytes.size.toLong()
            contentType = "image/jpeg"
        }

        // Step 6: Create a unique key for the file
        val fileName = "${System.currentTimeMillis()}_${File(imageUri.path).name}.jpg"
        val key = "uploads/${s + fileName}"

        // Step 7: Create a PutObjectRequest
        val putObjectRequest = PutObjectRequest(bucketName, key, inputStream, metadata)

        // Step 8: Upload the file to S3
        s3Client.putObject(putObjectRequest)

        // Step 9: Get the URL of the uploaded file
        s3Client.getUrl(bucketName, key).toString()

    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
