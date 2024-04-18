package com.pandora.apodbrowser.ui

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.performClick
import com.pandora.apodbrowser.ui.model.PicOfTheDayItem
import de.mannodermaus.junit5.compose.createComposeExtension
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

@ExperimentalTestApi
class ListComponentsTest {

    @JvmField
    @RegisterExtension
    val extension = createComposeExtension()

    val picOfTheDayItem = PicOfTheDayItem(
        title = "sample_image",
        date = "2022-05-01",
        url = "about:blank",
        explanation = "This is a sample picture",
        hdUrl = "about:blank"
    )
    var isOnItemClickedCalled = false

    @Test
    @DisplayName("Given a provided click listener, When clicking on the card, Then it should call the listener")
    fun latestCollectionCardShouldCallOnItemClickWhenClicked() {
        extension.use {
            setContent {
                LatestCollectionCard(picture = picOfTheDayItem) {
                    isOnItemClickedCalled = true
                }
            }

            onNode(hasClickAction()).performClick()
            assertThat(isOnItemClickedCalled).isTrue()
        }
    }
}