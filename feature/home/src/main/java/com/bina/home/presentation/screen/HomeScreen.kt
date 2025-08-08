package com.bina.home.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.bina.home.domain.model.UserDomain
import com.bina.home.presentation.viewmodel.HomeUiState
import com.bina.home.presentation.viewmodel.HomeViewModel
import androidx.navigation.NavHostController
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    navController: NavHostController,
) {
    val viewModel: HomeViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsState()
    HomeScreenContent(uiState = uiState)
}

@Composable
private fun HomeScreenContent(uiState: HomeUiState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorPrimary),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (uiState) {
            is HomeUiState.Loading -> {
                CircularProgressIndicator()
            }
            is HomeUiState.Success -> {
                val users = uiState.users
                Text(
                    text = "Contatos",
                    style = Typography.displayLarge,
                    modifier = Modifier.padding(Dimens.spacing16).fillMaxWidth(1f)
                )
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(Dimens.spacing2)
                ) {
                    items(users) { user ->
                        UserCard(
                            avatar = rememberAsyncImagePainter(user.img),
                            name = user.name ?: "",
                            username = user.username ?: "",
                        )
                    }
                }
            }
            is HomeUiState.Error -> {
                val message = uiState.message
                Text(text = message)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    Theme {
        val users = listOf(
            UserDomain(
                img = "https://randomuser.me/api/portraits/men/1.jpg",
                name = "João Silva",
                id = "1",
                username = "joaosilva"
            ),
            UserDomain(
                img = "https://randomuser.me/api/portraits/women/2.jpg",
                name = "Maria Souza",
                id = "2",
                username = "mariasouza"
            )
        )
        HomeScreenContent(
            uiState = HomeUiState.Success(users)
        )
    }
}
