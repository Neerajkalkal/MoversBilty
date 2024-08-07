package com.example.gangapackagesolution.screens

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gangapackagesolution.models.DataOrException
import com.example.gangapackagesolution.models.otpResponse
import com.example.gangapackagesolution.repository.Repository
import com.example.gangapackagesolution.repository.TokenManagement
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class loginViewmodel(context: Context) : ViewModel() {

    val tokenManagement = TokenManagement(context)

    private val _otpRequestState = MutableStateFlow(DataOrException<String, Exception>())
    val otpRequestState: StateFlow<DataOrException<String, Exception>> = _otpRequestState

    private val _otpVerifyState = MutableStateFlow(DataOrException<otpResponse, Exception>())
    val otpVerifyState: StateFlow<DataOrException<otpResponse, Exception>> = _otpVerifyState




    fun resetError() {
        _otpRequestState.value = DataOrException(null, false, null)
        _otpVerifyState.value = DataOrException(null, false, null)
    }


    // to Save
    fun saveJwt(token: String) {
        tokenManagement.saveToken(token)
    }

    // save new user
    fun saveNewUser(token: Boolean) {
        tokenManagement.newUser(token)
    }
    // save the email
    fun saveEmail(email: String) {
        tokenManagement.saveEmail(email)
    }



    // verify otp
    fun verifyOtp(otp: String, phoneNumber: String) {
        viewModelScope.launch {
            Repository.verifyOtp(
                phone = phoneNumber,
                otp = otp,
                otpVerifyState = _otpVerifyState
            )
        }
    }

    fun requestOtp(number: String) {
        viewModelScope.launch {
            Repository.getOtp(
                number,
                _otpRequestState
            )
        }
    }
}