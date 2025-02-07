package com.picpay.desafio.android.presentation.compose

import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertAny
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.picpay.desafio.android.presentation.compose.fakedata.UserFakeData
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserItemComposableKtTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun givenUserWHENAllDataIsVisible() {
        composeTestRule.setContent {
            MaterialTheme {
                UserItemComposable(
                    modifier = Modifier.testTag(TEST_TAG),
                    user = UserFakeData().user
                )
            }
        }

        composeTestRule.onNodeWithTag(TEST_TAG)
            .onChildren()
            .assertAny(hasTestTag(UserItemIDs.IMG))
            .assertAny(hasTestTag(UserItemIDs.USER))
            .assertAny(hasTestTag(UserItemIDs.USERNAME))
            .assertAny(hasTestTag(UserItemIDs.PROGRESS))

        composeTestRule.onNodeWithTag(UserItemIDs.IMG)
            .assertIsDisplayed()
    }

    companion object {
        const val TEST_TAG = "ITEM_TEST_TAG"
    }
}