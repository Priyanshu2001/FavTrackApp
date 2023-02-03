package com.prbansal.favtrack.components

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.prbansal.favtrack.*
import com.prbansal.favtrack.R
import com.prbansal.favtrack.database.RepoData
import com.prbansal.favtrack.ui.theme.ColorAccent
import com.prbansal.favtrack.ui.theme.ColorSecondary
import com.prbansal.favtrack.ui.theme.FavTrackTheme
import com.prbansal.favtrack.ui.theme.Shapes


@Composable
fun addRepo() {
    if (viewModel.showCustomDialog.value) {
        InputDialogView ({ configureAddDialog() }, {ownerName: String, repoName: String -> viewModel.onAddRepoClicked(ownerName, repoName) {
            Toast.makeText(
                ctx, "No Data Found. Bad Request", Toast.LENGTH_SHORT
            ).show()
        }
        })
    }
}

fun configureAddDialog(){
    viewModel.showCustomDialog.value = !(viewModel.showCustomDialog.value)
}
fun deleteRepo(repoData: RepoData) {
    viewModel.onDeleteRepoClicked(repoData)
}
fun openURL(url : String){
    val urlIntent = Intent(
        Intent.ACTION_VIEW,
        Uri.parse(url)
    )
    ctx.startActivity(urlIntent)
}
fun shareRepo(url : String){
    val intent = Intent(Intent.ACTION_SEND)
    val body = "Check out this Github Repository :/n/n $url"
    intent.setType("text/plain")
    intent.putExtra(Intent.EXTRA_TEXT, body)
    ctx.startActivity(intent)
}

@Composable
fun AppBarView() {

    TopAppBar(
        title = { Text("FavTrack", color = MaterialTheme.colors.surface) } ,
        backgroundColor = MaterialTheme.colors.onPrimary,
        actions = {
            IconButton(onClick = {
                configureAddDialog()
            }) {
                Icon(Icons.Default.Add, "", tint = MaterialTheme.colors.surface)
            }
        }
    )
    addRepo()

}

@Composable
fun LottieAnimationVew(){
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.no_list))
    LottieAnimation(composition = composition, iterations = LottieConstants.IterateForever, modifier = Modifier
        .height(240.dp)
        .width(240.dp))
}

@Composable
fun NoListView(){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .wrapContentHeight(align = Alignment.CenterVertically))
    {
        LottieAnimationVew()
        Text(text = "Track your favourite Repo", color = Color.White,textAlign = TextAlign.Center,modifier= Modifier
            .padding(8.dp))
        Button(onClick = { configureAddDialog() }, shape = Shapes.large, colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)) {
            Text(text = "Add Now", textAlign = TextAlign.Center, color = MaterialTheme.colors.primary, modifier = Modifier)

        }

    }
}

@Composable
fun HomeScreen(navController: NavHostController){
    val  appContext = LocalContext.current.applicationContext as Application
    appCtx = appContext
    ctx = LocalContext.current

    viewModel = viewModel(factory = RepoViewModelFactory(appContext))
    val allRepos by viewModel.allRepos.observeAsState(listOf())

    Column {
        AppBarView()
        if(allRepos.size == 0)
            NoListView()
        else
            RepoListView(allRepos)
    }
}

@Composable
fun RepoListView(allRepos: List<RepoData>) {
    LazyColumn( modifier = Modifier
        .padding(4.dp)
        .fillMaxSize() ) {
        items(allRepos){ repo ->
            RepoCards(repoData = repo)
        }
    }

}

@Composable
fun RepoCards(repoData: RepoData){
    Card(modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth()
        .wrapContentHeight(), backgroundColor = MaterialTheme.colors.onPrimary, border = BorderStroke(1.dp, color = MaterialTheme.colors.secondary)
    ) {
        Column(modifier = Modifier.padding(4.dp, 2.dp)) {


            Text(
                modifier = Modifier.padding(8.dp, 2.dp),
                text = repoData.repoName,
                color = MaterialTheme.colors.surface,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                modifier = Modifier.padding(8.dp, 4.dp),
                text = repoData.ownerName,
                color = MaterialTheme.colors.onSecondary,
                fontSize = 14.sp,
            )

            repoData.description?.let {
                Text(
                    text = it,
                    color = Color.White,
                    modifier = Modifier.padding(8.dp, 2.dp),
                    fontSize = 16.sp,
                    maxLines = 2
                )
            }
            Row(
                modifier = Modifier
                    .padding(2.dp)
                    .wrapContentHeight(align = Alignment.CenterVertically),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = { openURL(repoData.link)},
                    shape = Shapes.large,
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary),
                    modifier = Modifier
                        .padding(4.dp)
                        .weight(3F)
                ) {
                    Text(
                        text = "View Code",
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colors.primary,
                        modifier = Modifier.padding(2.dp)
                    )

                }
                Button(onClick = { shareRepo(repoData.link) }, border = BorderStroke(1.dp, color = ColorSecondary), modifier = Modifier
                    .weight(1F)
                    .padding(4.dp), shape = Shapes.large ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_baseline_share_24),
                        contentDescription = "Delete"
                    )

                }
                Button(onClick = { deleteRepo(repoData) }, border = BorderStroke(1.dp, color = ColorAccent), modifier = Modifier
                    .weight(1F)
                    .padding(4.dp), shape = Shapes.large ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_baseline_delete_outline_24),
                        contentDescription = "Delete"
                    )

                }
            }

        }
    }
}
