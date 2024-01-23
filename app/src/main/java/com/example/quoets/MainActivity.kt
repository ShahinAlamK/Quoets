package com.example.quoets

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.quoets.components.ErrorScreen
import com.example.quoets.components.LoadingScreen
import com.example.quoets.data.ApiStatus
import com.example.quoets.data.models.QuoteModel
import com.example.quoets.data.presentations.QuoteViewModel
import com.example.quoets.ui.theme.QuoetsTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val quoteViewModel:QuoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuoetsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LaunchedEffect(
                        key1 = true,
                        block = {quoteViewModel.fetchQuotes()})
                    HomeScreen(quoteViewModel = quoteViewModel)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(quoteViewModel: QuoteViewModel) {
    val scope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = stringResource(id = R.string.app_name))},
                actions = {
                    IconButton(onClick = { scope.launch { quoteViewModel.fetchQuotes() }}) {
                        Icon(imageVector = Icons.Default.Refresh, contentDescription ="" )
                    }
                }
            )
        }
    ) {
        paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 15.dp)
        )
        {
            when(val results = quoteViewModel.response.value){
                is ApiStatus.Loading->{ LoadingScreen()}
                is ApiStatus.Failure->{ ErrorScreen(error = results.msg.toString()) }
                is ApiStatus.Success->{ MsgCard(quoteModel = results.data.body()!!)}
                is ApiStatus.Empty->{ }
            }
        }
    }
}

@Composable
fun MsgCard(quoteModel: QuoteModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Text(text = quoteModel.content, textAlign = TextAlign.Center, style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(40.dp))

        Text(text = quoteModel.author, style = MaterialTheme.typography.bodySmall)

        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Added Date ${quoteModel.dateAdded}", style = MaterialTheme.typography.bodySmall)
            Text(text = "Modified ${quoteModel.dateModified}", style = MaterialTheme.typography.bodySmall)
        }
    }
}