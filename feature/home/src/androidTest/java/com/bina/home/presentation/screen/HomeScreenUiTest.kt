package com.bina.home.presentation.screen

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.assertIsDisplayed
import com.bina.home.domain.model.User
import com.bina.home.presentation.viewmodel.HomeUiState
import org.junit.Rule
import org.junit.Test

class HomeScreenUiTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun homeScreen_showsUserList_whenSuccess() {
        // given
        val users = listOf(User("img", "Nome do Usuário", "1", "username")).map { it.toUi() }
        // when
        composeTestRule.setContent {
            HomeScreenContent(HomeUiState.Success(users),{})
        }
        // then
        composeTestRule.onNodeWithText("Nome do Usuário").assertIsDisplayed()
        composeTestRule.onNodeWithText("username").assertIsDisplayed()
    }

    @Test
    fun homeScreen_showsError_whenError() {
        // given
        val errorMessage = "Erro de rede"
        // when
        composeTestRule.setContent {
            HomeScreenContent(HomeUiState.Error(errorMessage),{})
        }

        // then
        composeTestRule.onNodeWithText(errorMessage).assertIsDisplayed()
    }
}