package com.bina.home.presentation.screen

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.assertIsDisplayed
import com.bina.home.domain.model.UserDomain
import com.bina.home.presentation.viewmodel.HomeUiState
import org.junit.Rule
import org.junit.Test

class HomeScreenUiTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun homeScreen_showsUserList_whenSuccess() {
        // given
        val users = listOf(UserDomain("img", "Nome do Usuário", "1", "username"))

        // when
        composeTestRule.setContent {
            HomeScreenContent(HomeUiState.Success(users))
        }

        // then
        composeTestRule.onNodeWithText("Nome do Usuário").assertIsDisplayed()
    }

    @Test
    fun homeScreen_showsError_whenError() {
        // given
        val errorMessage = "Erro de rede"

        // when
        composeTestRule.setContent {
            HomeScreenContent(HomeUiState.Error(errorMessage))
        }

        // then
        composeTestRule.onNodeWithText(errorMessage).assertIsDisplayed()
    }
}