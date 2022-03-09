package com.laco.sample.architecture.ui.user

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.AlertDialog
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.laco.sample.architecture.ui.model.UserModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                UserScreen()
            }
        }
    }
}

@Composable
fun UserScreen(viewModel: UserViewModel = viewModel()) {
    val state by viewModel.state.collectAsState()
    UserScreen(
        state = state,
        onLogin = { viewModel.login() },
        onRetry = { viewModel.refresh() },
        onRefresh = { viewModel.refresh() }
    )
}

@Composable
fun UserScreen(
    state: UserState,
    onLogin: () -> Unit,
    onRetry: () -> Unit,
    onRefresh: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        when (state) {
            UserState.Loading -> CircularProgressIndicator()
            UserState.LoginNeeded -> TextButton(
                onClick = { onLogin() },
                content = { Text(text = "로그인") })
            is UserState.Success -> UserItem(
                user = state.user,
                onClick = { onRefresh() }
            )
            UserState.Failure -> ErrorDialog { onRetry() }
        }
    }
}

@Composable
private fun UserItem(
    modifier: Modifier = Modifier,
    user: UserModel,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.wrapContentSize(),
        elevation = 4.dp,
        border = BorderStroke(1.dp, Color.Black)
    ) {
        Row(modifier = Modifier
            .clickable { onClick() }
            .padding(12.dp)) {
            AsyncImage(
                model = user.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .clip(MaterialTheme.shapes.small)
                    .size(48.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(text = "Name", style = MaterialTheme.typography.caption)
                Text(text = user.name)
            }
        }
    }
}

@Composable
private fun ErrorDialog(onRetry: () -> Unit) {
    AlertDialog(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        onDismissRequest = { },
        title = {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "오류 발생")
            }
        },
        buttons = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                contentAlignment = Alignment.Center,
            ) {
                TextButton(
                    onClick = { onRetry() },
                    content = { Text(text = "재시도") })
            }
        }
    )
}
