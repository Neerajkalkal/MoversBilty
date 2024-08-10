package com.example.gangapackagesolution.repository

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.FileProvider
import com.example.gangapackagesolution.constants.Constants
import com.example.gangapackagesolution.models.DataOrException
import com.example.gangapackagesolution.models.DetailsToSend
import com.example.gangapackagesolution.models.Outcome
import com.example.gangapackagesolution.models.Quotation.Quotation
import com.example.gangapackagesolution.models.UserDetails
import com.example.gangapackagesolution.models.bill.bill
import com.example.gangapackagesolution.models.lr_bilty.LrBilty
import com.example.gangapackagesolution.models.moneyreceipt.MoneyReceipt
import com.example.gangapackagesolution.models.otpResponse
import com.example.gangapackagesolution.models.packageList.PackageList
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Error

object Repository {


    // Suspend function to get OTP
    suspend fun getOtp(
        phone: String,
        otpRequestState: MutableStateFlow<DataOrException<String, Exception>>
                      ) {
        val client = OkHttpClient()

        val requestBody = phone.toRequestBody("text/plain".toMediaTypeOrNull())
        val request = Request.Builder()
            .url("${Constants.baseUrl}OtpRequest")
            .post(requestBody)
            .build()

        // Set loading to true before starting the request
        otpRequestState.value = DataOrException(loading = true)

        try {
            val response = withContext(Dispatchers.IO) {
                client.newCall(request).execute()
            }

            if (response.isSuccessful) {
                val responseBody = response.body?.string()
                if (responseBody != null) {
                    otpRequestState.value = DataOrException(data = responseBody, loading = false)
                } else {
                    otpRequestState.value =
                        DataOrException(e = IOException("Empty response body"), loading = false)
                }
            } else {
                otpRequestState.value = DataOrException(
                    e = IOException("Unsuccessful response: ${response.message}"),
                    loading = false
                                                       )
            }
        } catch (e: Exception) {
            otpRequestState.value = DataOrException(e = e, loading = false)
        }
    }


    // verify otp
    @SuppressLint("SuspiciousIndentation")
    suspend fun verifyOtp(
        phone: String,
        otp: String,
        otpVerifyState: MutableStateFlow<DataOrException<otpResponse, Exception>>
                         ) {

        val client = OkHttpClient()
        val json = """
            {
                "mobile": "$phone",
                "otp": "$otp"
            }
        """.trimIndent()

        try {
            withContext(Dispatchers.IO) {
                val requestBody = json.toRequestBody("application/json".toMediaTypeOrNull())
                val request = Request.Builder()
                    .url("${Constants.baseUrl}OtpVerify")
                    .post(requestBody)
                    .build()

                otpVerifyState.value = DataOrException(loading = true)


                val response = client.newCall(request).execute()

                if (response.isSuccessful) {
                    val responseBody = response.body?.string()


                    if (responseBody != null) {
                        val body = Gson().fromJson(responseBody, otpResponse::class.java)
                        otpVerifyState.value = DataOrException(data = body, loading = false)
                    } else {
                        otpVerifyState.value =
                            DataOrException(e = IOException("Empty response body"), loading = false)
                    }


                }
            }

        } catch (e: Exception) {
            otpVerifyState.value = DataOrException(e = e, loading = false)
        }
    }

    // get the quotation form
    suspend fun getTheQuotationForm(
        quotationState: MutableStateFlow<DataOrException<Quotation, Exception>>,
        token: String?
                                   ) {
        withContext(Dispatchers.IO) {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url("${Constants.baseUrl}getQuotationForm/$token")
                .build()

            try {
                quotationState.value = DataOrException(loading = true)
                val response = client.newCall(request).execute()

                if (response.isSuccessful) {
                    response.body?.use { responseBody ->
                        val quotation =
                            Gson().fromJson(responseBody.string(), Quotation::class.java)
                        quotationState.value = DataOrException(data = quotation, loading = false)


                    } ?: run {

                        quotationState.value =
                            DataOrException(e = IOException("Empty response body"), loading = false)
                    }
                } else {

                    quotationState.value =
                        DataOrException(e = IOException("Something went wrong"), loading = false)
                }
            } catch (e: Exception) {

                quotationState.value = DataOrException(e = e, loading = false)
            }
        }
    }

    suspend fun gettingListOfQuotation(

        quotationState: MutableStateFlow<DataOrException<List<Quotation>, Exception>>,
        token: String?
                                      ) {
        withContext(Dispatchers.IO) {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url("${Constants.baseUrl}quotationList/$token")
                .build()

            try {
                quotationState.value = DataOrException(loading = true)
                val response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    response.body?.use { responseBody ->
                        val quotation =
                            Gson().fromJson(responseBody.string(), Array<Quotation>::class.java)
                                .toList()
                        quotationState.value = DataOrException(data = quotation, loading = false)
                    }
                        ?: run {
                            quotationState.value =
                                DataOrException(
                                    e = IOException("Empty response body"),
                                    loading = false
                                               )
                        }

                }
            } catch (e: Exception) {
                quotationState.value = DataOrException(e = e, loading = false)
            }
        }
    }


    // save edited
    suspend fun saveEdited(
        quotation: Quotation,
        quotationState: MutableStateFlow<DataOrException<List<Quotation>, Exception>>,
        token: String?,
        refresh: () -> Unit
                          ) {
        withContext(Dispatchers.IO) {

            val json = Gson().toJson(quotation)
            val requestBody = json.toRequestBody("application/json".toMediaTypeOrNull())
            val client = OkHttpClient()
            val request = Request.Builder()
                .url("${Constants.baseUrl}saveEditedQuotation/${quotation.id}/$token")
                .post(requestBody)
                .build()

            try {

                quotationState.value = DataOrException(loading = true)
                val response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    response.body?.use { responseBody ->
                        refresh()
                    }

                } else {
                    Log.d("djxd", "dnxdxndjxn")
                    quotationState.value =
                        DataOrException(e = IOException("Something went wrong"), loading = false)
                }

            } catch (e: Exception) {
                quotationState.value =
                    DataOrException(e = IOException("Something went wrong"), loading = false)
            }

        }
    }

    // saving normal Quotation
    suspend fun saveQuotation(
        quotation: Quotation,
        quotationState: MutableStateFlow<DataOrException<String, Exception>>,
        token: String?,
        refresh: () -> Unit
                             ) {
        withContext(Dispatchers.IO) {
            val json = Gson().toJson(quotation)
            val requestBody = json.toRequestBody("application/json".toMediaTypeOrNull())
            val client = OkHttpClient()
            val request = Request.Builder()
                .url("${Constants.baseUrl}saveQuotation/$token")
                .post(requestBody)
                .build()

            try {
                quotationState.value = DataOrException(loading = true)
                val response = client.newCall(request).execute()
                if (response.isSuccessful) {

                    withContext(Dispatchers.Main) {
                        refresh()
                    }
                }


            } catch (e: IOException) {
                withContext(Dispatchers.Main) {
                    Log.e("saveQuotation", "Network error: ${e.message}", e)
                    quotationState.value = DataOrException(e = e, loading = false)
                }
            } catch (e: Exception) {
                quotationState.value = DataOrException(e = e, loading = false)
            }
        }


    }

    // downloading the Quotation Pdf

    suspend fun downloadPdf(
        context: Context,
        id: String,
        pdfState: MutableStateFlow<DataOrException<String, Exception>>,
        share: Boolean,
        token: String?
                           ) {
        pdfState.value = DataOrException(loading = true)
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("${Constants.baseUrl}download/$id/$token")
            .get()
            .build()

        try {
            withContext(Dispatchers.IO) {
                val response = client.newCall(request).execute()

                if (response.isSuccessful) {
                    response.body?.let { responseBody ->
                        val pdfBytes = responseBody.bytes()
                        val file = File(context.cacheDir, "Quotation$id.pdf")

                        FileOutputStream(file).use { fos ->
                            fos.write(pdfBytes)
                        }
                        val uri = FileProvider.getUriForFile(
                            context,
                            "${context.packageName}.provider",
                            file
                                                            )
                        if (!share) {
                            // View the PDF

                            val intent = Intent(Intent.ACTION_VIEW).apply {
                                setDataAndType(uri, "application/pdf")
                                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                            }
                            context.startActivity(intent)
                            pdfState.value =
                                DataOrException(data = "Download Successful", loading = false)
                        } else {


                            // Share the PDF
                            val intent = Intent(Intent.ACTION_SEND).apply {
                                type = "application/pdf"
                                putExtra(Intent.EXTRA_STREAM, uri)
                                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                            }
                            val shareIntent = Intent.createChooser(intent, "Share Quotation")
                            context.startActivity(shareIntent)
                            pdfState.value =
                                DataOrException(data = "Share Successful", loading = false)

                        }
                    }

                        ?: throw IOException("Response body is null")
                } else {
                    pdfState.value = DataOrException(
                        e = IOException("Server responded with error: ${response.code}"),
                        loading = false
                                                    )
                }
            }
        } catch (e: IOException) {
            pdfState.value = DataOrException(e = e, loading = false)
        } catch (e: Exception) {
            pdfState.value = DataOrException(e = e, loading = false)
        }
    }

    // delete quotation
    suspend fun deleteQuotation(
        id: String,
        token: String?,
        refresh: () -> Unit
                               ) {

        val client = OkHttpClient()
        val request = Request.Builder()
            .url("${Constants.baseUrl}deleteQuote/$id/$token")
            .get()
            .build()

        try {

            withContext(Dispatchers.IO) {
                val response = client.newCall(request).execute()

                if (response.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        refresh()
                    }
                } else {
                    Log.d("success", "Deleted")
                }


            }
        } catch (e: Exception) {
            Log.d("djxd", "dnxdxndjxn")
        }
    }

    // adding to packageList
    suspend fun addFunction(
        State: MutableStateFlow<DataOrException<String, Exception>>,
        List: Any,
        onCompleted: () -> Unit,
        url: String = "addPackagingList",
        token: String?
                           ) {
        State.value = DataOrException(loading = true)
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("${Constants.baseUrl}$url/$token")
            .post(
                Gson().toJson(List).toRequestBody("application/json".toMediaTypeOrNull())
                 )
            .build()
        try {
            withContext(
                Dispatchers.IO
                       ) {

                val response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        onCompleted()
                        State.value =
                            DataOrException(data = "Added to Package List", loading = false)
                    }
                } else {
                    State.value =
                        DataOrException(e = IOException("Something went wrong"), loading = false)
                }

            }
        } catch (e: Exception) {
            State.value =
                DataOrException(e = IOException("Something went wrong"), loading = false)
        }


    }

    // gettingPackagingList
    suspend fun getPackagingList(
        packageListState: MutableStateFlow<DataOrException<List<PackageList>, Exception>>,
        token: String?
                                ) {
        packageListState.value = DataOrException(loading = true)
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("${Constants.baseUrl}getPackagingList/$token")
            .get()
            .build()
        try {

            withContext(Dispatchers.IO) {
                client.newCall(request).execute().use { response ->
                    if (response.isSuccessful) {
                        val packageList =
                            Gson().fromJson(response.body?.string(), Array<PackageList>::class.java)
                                .toList()
                        packageListState.value =
                            DataOrException(data = packageList, loading = false)
                    } else {
                        packageListState.value =
                            DataOrException(
                                e = IOException("Something went wrong"), loading = false
                                           )
                    }
                }

            }
        } catch (e: Exception) {
            packageListState.value =
                DataOrException(e = IOException("Something went wrong"), loading = false)
        }
    }


    // delete packageList
    suspend fun deletePackageList(
        id: String,

        packageListState: MutableStateFlow<DataOrException<List<PackageList>, Exception>>,
        token: String?,
        refresh: () -> Unit,
                                 ) {
        packageListState.value = DataOrException(loading = true)

        val client = OkHttpClient()
        val request = Request.Builder()
            .url("${Constants.baseUrl}deletePackagingList/$id/$token")
            .get()
            .build()
        try {
            withContext(Dispatchers.IO) {
                val response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        refresh()
                    }
                } else {
                    packageListState.value =
                        DataOrException(e = IOException("Something went wrong"), loading = false)
                }
            }
        } catch (e: Exception) {
            packageListState.value =
                DataOrException(e = IOException("Something went wrong"), loading = false)
        }
    }


//getting lrBilty

    suspend fun getLrBill(
        lrbills: MutableStateFlow<DataOrException<List<LrBilty>, Exception>>,
        token: String?
                         ) {
        lrbills.value = DataOrException(loading = true)
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("${Constants.baseUrl}getLrBills/$token")
            .get()
            .build()
        try {

            withContext(Dispatchers.IO) {
                client.newCall(request).execute().use { response ->
                    if (response.isSuccessful) {
                        val packageList =
                            Gson().fromJson(response.body?.string(), Array<LrBilty>::class.java)
                                .toList()
                        lrbills.value =
                            DataOrException(data = packageList, loading = false)
                    } else {
                        lrbills.value =
                            DataOrException(
                                e = IOException("Something went wrong"), loading = false
                                           )
                    }
                }

            }
        } catch (e: Exception) {
            lrbills.value =
                DataOrException(e = IOException("Something went wrong"), loading = false)
        }
    }


    // delete LrBilty
    suspend fun deleteLrBilty(
        id: String,
        packageListState: MutableStateFlow<DataOrException<List<LrBilty>, Exception>>,
        token: String?,
        refresh: () -> Unit,
                             ) {
        packageListState.value = DataOrException(loading = true)

        val client = OkHttpClient()
        val request = Request.Builder()
            .url("${Constants.baseUrl}deleteLrBill/$id/$token")
            .get()
            .build()
        try {
            withContext(Dispatchers.IO) {
                val response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        refresh()
                    }
                } else {
                    packageListState.value =
                        DataOrException(e = IOException("Something went wrong"), loading = false)
                }
            }
        } catch (e: Exception) {
            packageListState.value =
                DataOrException(e = IOException("Something went wrong"), loading = false)
        }
    }


//getting Bills

    suspend fun getBill(
        lrbills: MutableStateFlow<DataOrException<List<bill>, Exception>>,
        token: String?
                       ) {
        lrbills.value = DataOrException(loading = true)
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("${Constants.baseUrl}getBill/$token")
            .get()
            .build()
        try {

            withContext(Dispatchers.IO) {
                client.newCall(request).execute().use { response ->
                    if (response.isSuccessful) {
                        val packageList =
                            Gson().fromJson(response.body?.string(), Array<bill>::class.java)
                                .toList()
                        lrbills.value =
                            DataOrException(data = packageList, loading = false)
                    } else {
                        lrbills.value =
                            DataOrException(
                                e = IOException("Something went wrong"), loading = false
                                           )
                    }
                }

            }
        } catch (e: Exception) {
            lrbills.value =
                DataOrException(e = IOException("Something went wrong"), loading = false)
        }
    }


// delete bill

    // delete LrBilty
    suspend fun deleteBill(
        id: String,
        packageListState: MutableStateFlow<DataOrException<List<bill>, Exception>>,
        token: String?,
        refresh: () -> Unit,
                          ) {
        packageListState.value = DataOrException(loading = true)

        val client = OkHttpClient()
        val request = Request.Builder()
            .url("${Constants.baseUrl}deleteBill/$id/$token")
            .get()
            .build()
        try {
            withContext(Dispatchers.IO) {
                val response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        refresh()
                    }
                } else {
                    packageListState.value =
                        DataOrException(e = IOException("Something went wrong"), loading = false)
                }
            }
        } catch (e: Exception) {
            packageListState.value =
                DataOrException(e = IOException("Something went wrong"), loading = false)
        }
    }

    // getting moneyreciept
    suspend fun getMoneyReceipt(
        moneyReceipt: MutableStateFlow<DataOrException<List<MoneyReceipt>, Exception>>,
        token: String?
                               ) {
        moneyReceipt.value = DataOrException(loading = true)
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("${Constants.baseUrl}getMoneyReceipt/$token")
            .get()
            .build()
        try {

            withContext(Dispatchers.IO) {
                client.newCall(request).execute().use { response ->
                    if (response.isSuccessful) {
                        val packageList =
                            Gson().fromJson(
                                response.body?.string(), Array<MoneyReceipt>::class.java
                                           )
                                .toList()
                        moneyReceipt.value =
                            DataOrException(data = packageList, loading = false)
                    } else {
                        moneyReceipt.value =
                            DataOrException(
                                e = IOException("Something went wrong"), loading = false
                                           )
                    }
                }

            }
        } catch (e: Exception) {
            moneyReceipt.value =
                DataOrException(e = IOException("Something went wrong"), loading = false)
        }
    }


    // delete LrBilty
    suspend fun deleteMoneyReceipt(
        id: String,
        moneyReceiptState: MutableStateFlow<DataOrException<List<MoneyReceipt>, Exception>>,
        token: String?,
        refresh: () -> Unit,
                                  ) {
        moneyReceiptState.value = DataOrException(loading = true)

        val client = OkHttpClient()
        val request = Request.Builder()
            .url("${Constants.baseUrl}deleteMoneyReceipt/$id/$token")
            .get()
            .build()
        try {
            withContext(Dispatchers.IO) {
                val response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        refresh()
                    }
                } else {
                    moneyReceiptState.value =
                        DataOrException(e = IOException("Something went wrong"), loading = false)
                }
            }
        } catch (e: Exception) {
            moneyReceiptState.value =
                DataOrException(e = IOException("Something went wrong"), loading = false)
        }
    }


    // get user details

    suspend fun getUserDetails(
        userDetails: MutableStateFlow<DataOrException<UserDetails, Exception>>,
        token: String?,
        token1: String?
                              ) {
        userDetails.value = DataOrException(loading = true)



        try {

            withContext(Dispatchers.IO) {
                val client = OkHttpClient()
                val request = Request.Builder()
                    .url("${Constants.baseUrl}getUserDetails/${token.toString()}")
                    .get()
                    .build()


                client.newCall(request).execute().use { response ->
                    if (response.isSuccessful) {
                        val packageList =
                            Gson().fromJson(response.body?.string(), UserDetails::class.java)
                        userDetails.value =
                            DataOrException(data = packageList, loading = false)
                    } else {
                        userDetails.value =
                            DataOrException(
                                e = IOException("Something went wrong"), loading = false
                                           )
                    }
                }
            }

        } catch (e: Exception) {
            userDetails.value =
                DataOrException(e = IOException("Something went wrong"), loading = false)
        }

    }

    // save the user details
    suspend fun saveUserDetails(
        userDetails: DetailsToSend,
        userDetailsState: MutableStateFlow<DataOrException<Outcome, Exception>>,
        jwt: String,
        done1: String,
        done: () -> Unit

                               ) {
        userDetailsState.value = DataOrException(loading = true)

        val client = OkHttpClient()
        val request = Request.Builder()
            .url("${Constants.baseUrl}createNewUser/${jwt}")
            .post(
                Gson().toJson(userDetails).toRequestBody("application/json".toMediaTypeOrNull())
                 )
            .build()


        try {

            withContext(Dispatchers.IO) {
                val response = client.newCall(request).execute()
                if (response.isSuccessful) {

                    if (response.body?.string() == "T") {
                        userDetailsState.value = DataOrException(loading = false)
                        withContext(Dispatchers.Main) {
                            done()
                        }
                    } else {
                        userDetailsState.value =
                            DataOrException(
                                e = IOException("Something went wrong"), loading = false
                                           )
                    }
                } else {
                    userDetailsState.value =
                        DataOrException(e = IOException("Something went wrong"), loading = false)
                }
            }
        } catch (e: Exception) {
            userDetailsState.value =
                DataOrException(e = IOException("Something went wrong"), loading = false)
        }

    }

    // sending image
    suspend fun sendImage(
        jwt: String,
        type: String,
        link: String,
        onCompleted: () -> Unit,
        error: (String) -> Unit
                         ) {
        val requestBody = link.toRequestBody("text/plain".toMediaTypeOrNull())
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("${Constants.baseUrl}SendImage/$jwt/$type")
            .post(requestBody)
            .build()

        withContext(Dispatchers.IO) {
            try {
                client.newCall(request).execute().use { response ->
                    if (response.isSuccessful) {
                        val responseBody = response.body?.string()
                        if (responseBody == "Done") {
                            onCompleted()
                        } else {
                            error("Something went wrong: $responseBody")
                        }
                    } else {
                        error("HTTP error: ${response.code}")
                    }
                }
            } catch (e: Exception) {
                error(e.message.toString())
            }
        }
    }

}