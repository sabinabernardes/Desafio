package com.bina.home.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import coil.compose.rememberAsyncImagePainter
import androidx.compose.ui.tooling.preview.Preview
import com.bina.core.designsystem.Typography.Typography
import com.bina.core.designsystem.colors.ColorPrimary
import com.bina.core.designsystem.colors.Theme
import com.bina.core.designsystem.components.UserCard
import com.bina.core.designsystem.dimens.Dimens
import com.bina.home.domain.model.User
import com.bina.home.presentation.viewmodel.HomeUiState
import com.bina.home.presentation.viewmodel.HomeViewModel
import androidx.navigation.NavHostController
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeRoute(
    navController: NavHostController,
) {
    HomeScreen(
        onRetry = { /* opcional: mostrar snackbar/telemetria */ }
    )
}

@Composable
private fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
    onRetry: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    HomeScreenContent(
        uiState = uiState,
        onRetry = onRetry
    )
}

@Composable
fun HomeScreenContent(
    uiState: HomeUiState,
    onRetry: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorPrimary),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (uiState) {
            is HomeUiState.Loading -> LoadingSection()
            is HomeUiState.Error -> ErrorSection(
                message = uiState.message,
                onRetry = onRetry
            )
            is HomeUiState.Success -> UsersSection(
                title = "Contatos",
                users = uiState.users,
            )
        }
    }
}

@Composable
private fun LoadingSection() {
    CircularProgressIndicator()
}

@Composable
private fun ErrorSection(
    message: String,
    onRetry: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(Dimens.spacing16)
    ) {
        Text(text = message, style = Typography.bodyLarge)
        Spacer(Modifier.height(Dimens.spacing8))
        Button(onClick = onRetry) {
            Text("Tentar novamente")
        }
    }
}

@Composable
private fun UsersSection(
    title: String,
    users: List<UserUi>
) {
    Text(
        text = title,
        style = Typography.displayLarge,
        modifier = Modifier
            .padding(Dimens.spacing16)
            .fillMaxWidth()
    )
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(Dimens.spacing2),
        modifier = Modifier.fillMaxSize()
    ) {
        items(
            items = users,
            key = { it.name }
        ) { user ->
            UserCard(
                avatar = rememberAsyncImagePainter(user.imageUrl),
                name = user.name,
                username = user.username,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    Theme {
        val users = listOf(
            User(
                img = "https://randomuser.me/api/portraits/men/1.jpg",
                name = "João Silva",
                id = "1",
                username = "joaosilva"
            ),
            User(
                img = "https://randomuser.me/api/portraits/women/2.jpg",
                name = "Maria Souza",
                id = "2",
                username = "mariasouza"
            )
        ).map { it.toUi() }
        HomeScreenContent(
            uiState = HomeUiState.Success(users),
            onRetry = {}
        )
    }
}
