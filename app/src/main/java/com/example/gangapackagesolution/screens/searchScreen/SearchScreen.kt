package com.example.gangapackagesolution.screens.searchScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.gangapackagesolution.screens.MainViewModel
import com.example.gangapackagesolution.screens.homeScreen.SearchBox
import com.example.gangapackagesolution.screens.quotationScreen.Header


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    mainViewModel: MainViewModel,
    navController: NavHostController,
    value: Color
                ) {


    val searchQuery = remember {
        mutableStateOf("")
    }
    Surface(modifier = Modifier.fillMaxSize()) {
        Column {
            Header(text = "Search", color = value)

            Row(modifier = Modifier.padding(top = 10.dp, start = 5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp)) {

                Surface(
                    onClick = { /*TODO*/ },
                    border = BorderStroke(width = 1.dp, color = value),
                    shape = CircleShape
                       ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {

                        Text(text = "Quotations", modifier = Modifier.padding(10.dp), color = value)
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown, contentDescription = "",
                            tint = value
                            )

                    }
                }



           SearchBar(query =searchQuery.value, onQueryChange = {searchQuery.value = it}, onSearch ={} , active =false , onActiveChange ={}
                    , placeholder = { Text(text = "Search With Name or Number")}) {

           }



            }
        }
    }

}