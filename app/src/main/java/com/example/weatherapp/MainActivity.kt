package com.example.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.weatherapp.sampledata.WeatherModel
import com.example.weatherapp.screens.DialogSearch
import com.example.weatherapp.screens.MainCard
import com.example.weatherapp.screens.TabLayout
import com.example.weatherapp.screens.getResult
import com.example.weatherapp.ui.theme.WeatherAppTheme
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherAppTheme {
                val daysList = remember {
                    mutableStateOf(listOf<WeatherModel>())
                }
                val dialogState = remember {
                    mutableStateOf(false)
                }
                val currentDay = remember {
                    mutableStateOf(
                        WeatherModel(
                            "", "", "0.0", "",
                            "", "0.0", "0.0", ""
                        )
                    )
                }
                if (dialogState.value) {
                    DialogSearch(dialogState, onSubmit = {
                        getResult(it, this, daysList, currentDay)
                    })
                }
                getResult("London", this, daysList, currentDay)
                Image(
                    painter = painterResource(id = R.drawable.blue_sky_clouds),
                    contentDescription = "im1",
                    modifier = Modifier
                        .alpha(0.5f)
                        .fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )
                Column {
                    MainCard(
                        currentDay,
                        onClickSync = {
                        getResult("London", this@MainActivity, daysList, currentDay)
                    },
                        onClickSearch = {
                            dialogState.value = true
                        })
                    TabLayout(daysList, currentDay)
                }
            }
        }
    }
}



//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier, context: Context) {
//    val state = remember { mutableStateOf("Unknown") }
//    Column(modifier.fillMaxSize()) {
//        Box(
//            modifier = Modifier
//                .fillMaxHeight(0.5f)
//                .fillMaxWidth(),
//            contentAlignment = Alignment.Center
//        ) {
//            Text(text = "Temp in $name is ${state.value} C")
//        }
//        Box(
//            modifier = Modifier
//                .fillMaxHeight()
//                .fillMaxWidth(),
//            contentAlignment = Alignment.BottomCenter
//        ) {
//            Button(
//                onClick = {
//                    getResult(name, state, context)
//                },
//                modifier = Modifier
//                    .padding(5.dp)
//                    .fillMaxWidth()
//            ) {
//                Text(text = "Refresh")
//            }
//        }
//    }
//}
//
//private fun getResult(city: String, state: MutableState<String>, context: Context) {
//    val apiKey = context.getString(R.string.api)
//    val url = "https://api.weatherapi.com/v1/current.json?key=$apiKey&q=$city&aqi=no"
//    val queue = Volley.newRequestQueue(context)
//    val stringRequest = StringRequest(
//        Request.Method.GET,
//        url,
//        { response ->
//            val obj = JSONObject(response)
//            state.value = obj.getJSONObject("current").getString("temp_c")
//        },
//        { error ->
//            Log.d("MyLog", error.toString())
//            error.printStackTrace()
//            if (error.networkResponse != null) {
//                val statusCode = error.networkResponse.statusCode
//                val responseBody = String(error.networkResponse.data, Charsets.UTF_8)
//                Log.e("WeatherAPIError", "Status Code: $statusCode")
//                Log.e("WeatherAPIError", "Response Body: $responseBody")
//            }
//        }
//    )
//    queue.add(stringRequest)
//}