package com.prbansal.favtrack

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.Intent.EXTRA_TEXT
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.prbansal.favtrack.components.InputDialogView
import com.prbansal.favtrack.components.SetupNavGraph
import com.prbansal.favtrack.database.RepoData
import com.prbansal.favtrack.navigation.Screen
import com.prbansal.favtrack.ui.theme.ColorAccent
import com.prbansal.favtrack.ui.theme.ColorSecondary
import com.prbansal.favtrack.ui.theme.FavTrackTheme
import com.prbansal.favtrack.ui.theme.Shapes

lateinit var viewModel : RepoViewModel
lateinit var ctx : Context
lateinit var appCtx: Application
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FavTrackTheme {
                val navController = rememberNavController()
                SetupNavGraph(navController = navController)
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colors.background
//                ) {
//                    RootView()
//                }
            }
        }
    }
}
