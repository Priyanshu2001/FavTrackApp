package com.prbansal.favtrack.components

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.prbansal.favtrack.ui.theme.Shapes

@Composable
fun InputDialogView(onDismiss:() -> Unit, onSuccess:(ownerName: String, repoName: String) -> Unit) {
    val context = LocalContext.current
    var ownerName by remember {mutableStateOf("") }
    var repoName by remember {mutableStateOf("") }

    var isOwnerError by remember { mutableStateOf(false) }
    var isRepoNameError by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = { onDismiss() }) {
        Card(
            //shape = MaterialTheme.shapes.medium,
            shape = Shapes.medium,
            // modifier = modifier.size(280.dp, 240.dp)
            modifier = Modifier.padding(8.dp),
            elevation = 8.dp
        ) {
            Column(
                modifier = Modifier.padding(8.dp)
            ) {

                Text(
                    text = "Add Repo",
                    modifier = Modifier.padding(8.dp),
                    fontSize = 20.sp,
                    color = MaterialTheme.colors.primary
                )

                OutlinedTextField(
                    value = ownerName,
                    onValueChange = {
                        ownerName = it
                        if(!ownerName.isEmpty())
                            isOwnerError = false
                                    }, modifier = Modifier.padding(8.dp),
                    label = { Text(text = "Owner Name") },
                    textStyle = TextStyle(color = MaterialTheme.colors.primary, fontWeight = FontWeight.Bold),
                    isError = isOwnerError,
                    singleLine = true
                )
                OutlinedTextField(
                    value = repoName,
                    onValueChange = {
                        repoName = it
                        if(! repoName.isEmpty()){
                            isRepoNameError = false
                        }
                                    }, modifier = Modifier.padding(8.dp),
                    label = { Text(text = "Repository Name") },
                    textStyle = TextStyle(color = MaterialTheme.colors.primary, fontWeight = FontWeight.Bold),
                    isError = isRepoNameError,
                    singleLine = true
                )

                Row {
                    OutlinedButton(
                        onClick = { onDismiss() }, border = BorderStroke(2.dp, color = MaterialTheme.colors.primary),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .weight(1F)
                    ) {
                        Text(text = "Cancel")
                    }


                    Button(
                        onClick = {
                            if(ownerName.isEmpty())
                                isOwnerError = true
                            else if( repoName.isEmpty()){
                                isRepoNameError = true
                            }
                            else{
                            onSuccess(ownerName, repoName)
                            onDismiss()
                                isRepoNameError = false
                                isOwnerError = false
                            }
                                  },
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .weight(1F)
                    ) {
                        Text(text = "Add", color = MaterialTheme.colors.surface)
                    }
                }


            }
        }
    }
}

@Composable
fun LoadingView(onDismiss:() -> Unit) {
    Dialog(onDismissRequest = { onDismiss() }) {

        Card(
            shape = Shapes.medium,
            modifier = Modifier,
            elevation = 8.dp
        ) {
            Column(
                Modifier
                    .background(Color.White)
                    .padding(12.dp)
            ) {
                Text(
                    text = "Loading.. Please wait..",
                    Modifier
                        .padding(8.dp), textAlign = TextAlign.Center
                )

                CircularProgressIndicator(
                    strokeWidth = 4.dp,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(8.dp)
                )
            }
        }
    }
}