package com.picpay.desafio.android.presentation.compose

import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertAny
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.picpay.desafio.android.DummyStatusListener
import com.picpay.desafio.android.compose.ScreenStatusIDs
import com.picpay.desafio.android.compose.ScreenTestIDs
import com.picpay.desafio.android.compose.UsersScreenComposable
import com.picpay.desafio.android.compose.fakedata.UserScreenFakeData
import desafio_android.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UsersScreenComposableKtTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun givenScreenWithSuccessStatusWHENDataIsLoadedTHENItemsAreVisible(){
        composeTestRule.setContent {
            MaterialTheme {
                UsersScreenComposable(
                    modifier = Modifier.testTag(TEST_TAG),
                    screen = UserScreenFakeData().readyUserScreen,
                    listener = DummyStatusListener
                )
            }
        }

        composeTestRule.onNodeWithTag(TEST_TAG)
            .onChildren()
            .assertAny(hasTestTag(ScreenTestIDs.TITLE))
            .assertAny(hasTestTag(ScreenTestIDs.LIST))
            .assertAny(hasText(context.getString(R.string.title)))


        composeTestRule.onNodeWithTag(ScreenTestIDs.LIST)
            .assertIsDisplayed()


        composeTestRule.onNodeWithTag(ScreenTestIDs.TITLE)
            .assertIsDisplayed()

        composeTestRule.onNodeWithTag(ScreenStatusIDs.ERROR)
            .isNotDisplayed()

        composeTestRule.onNodeWithTag(ScreenStatusIDs.LOADING)
            .isNotDisplayed()
    }

    @Test
    fun givenScreenWithSuccessStatusWHENDataIsLoadedTHENListIsNotNull() {
        composeTestRule.setContent {
            MaterialTheme {
                UsersScreenComposable(
                    modifier = Modifier.testTag(TEST_TAG),
                    screen = UserScreenFakeData().readyUserScreen,
                    listener = DummyStatusListener
                )
            }
        }

        composeTestRule.onNodeWithTag(ScreenTestIDs.LIST)
            .onChildren()
            .assertCountEquals(2)

        composeTestRule.onNodeWithTag(ScreenTestIDs.LIST)
            .assertIsDisplayed()
    }

    @Test
    fun givenScreenWithErrorStatusWHENDataIsLoadedTHENItemsAreVisible() {
        composeTestRule.setContent {
            MaterialTheme {
                UsersScreenComposable(
                    modifier = Modifier.testTag(TEST_TAG),
                    screen = UserScreenFakeData().errorUserScreen,
                    listener = DummyStatusListener
                )
            }
        }

        composeTestRule.onNodeWithTag(TEST_TAG)
            .onChildren()
            .assertAny(hasTestTag(ScreenStatusIDs.ERROR))
            .assertAny(hasText("Nao foi possível carregar a tela"))

        composeTestRule.onNodeWithTag(ScreenStatusIDs.ERROR)
            .assertIsDisplayed()
    }

    @Test
    fun givenScreenWithLoadingStatusWHENDataIsLoadedTHENItemsAreVisible() {
        composeTestRule.setContent {
            MaterialTheme {
                UsersScreenComposable(
                    modifier = Modifier.testTag(TEST_TAG),
                    screen = UserScreenFakeData().loadingUserScreen,
                    listener = DummyStatusListener
                )
            }
        }

        composeTestRule.onNodeWithTag(TEST_TAG)
            .onChildren()
            .assertAny(hasTestTag(ScreenStatusIDs.LOADING))

        composeTestRule.onNodeWithTag(ScreenStatusIDs.ERROR)
            .isNotDisplayed()

        composeTestRule.onNodeWithTag(ScreenStatusIDs.LOADING)
            .assertIsDisplayed()

    }

    companion object {
        const val TEST_TAG = "SCREEN_TEST_TAG"
    }
}