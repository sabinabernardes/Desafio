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
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.bina.core.designsystem.colors.ColorBackground
import com.bina.core.designsystem.colors.ColorPrimary
import com.bina.core.designsystem.components.UserCard
import com.bina.core.designsystem.dimens.Dimens
import com.bina.core.designsystem.picpaytheme.PicPayTheme
import com.bina.core.designsystem.typography.Typography
import com.bina.home.domain.model.User
import com.bina.home.presentation.viewmodel.HomeUiState
import com.bina.home.presentation.viewmodel.HomeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeRoute(
    navController: NavHostController,
) {
    HomeScreen(
        onRetry = {
            navController.navigate("home")
        }
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
        modifier = Modifier
            .fillMaxSize()
            .background(ColorPrimary),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = message, style = Typography.bodyLarge)
        Spacer(Modifier.height(Dimens.spacing16))
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
            .padding(
                top = Dimens.spacing32,
                bottom = Dimens.spacing16,
                start = Dimens.spacing16,
                end = Dimens.spacing16
            )
            .fillMaxWidth()
    )
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(Dimens.spacing2),
        modifier = Modifier
            .fillMaxSize()
            .background(ColorBackground)
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
    PicPayTheme {
        val users = listOf(
            User(
                img = "https://randomuser.me/api/portraits/men/1.jpg",
                name = "Jaiminho o Carteiro",
                id = "1",
                username = "Evitar a Fadiga"
            ),
            User(
                img = "https://randomuser.me/api/portraits/women/2.jpg",
                name = "Chiquinha",
                id = "2",
                username = "Pois é, pois é, pois é"
            )
        ).map { it.toUi() }
        HomeScreenContent(
            uiState = HomeUiState.Success(users),
            onRetry = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenErrorPreview() {
    PicPayTheme {
        HomeScreenContent(
            uiState = HomeUiState.Error("Erro de rede. Tente novamente."),
            onRetry = {}
        )
    }
}
