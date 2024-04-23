package com.pandora.apodbrowser.ui

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertWidthIsEqualTo
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.unit.dp
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import androidx.paging.testing.asPagingSourceFactory
import com.pandora.apodbrowser.ui.model.PicOfTheDayItem
import com.pandora.apodbrowser.ui.model.toDomainModel
import com.pandora.apodbrowser.ui.model.toItem
import com.pandora.domain.model.PicOfTheDay
import com.pandora.domain.usecases.FetchPaginatedPicsUsecase
import de.mannodermaus.junit5.compose.createComposeExtension
import kotlinx.coroutines.flow.map
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

@ExperimentalTestApi
class ListComponentsTest {

    @JvmField
    @RegisterExtension
    val extension = createComposeExtension()

    private val picOfTheDayItem = PicOfTheDayItem(
        title = "sample_image",
        date = "2022-05-01",
        url = "about:blank",
        explanation = "This is a sample picture",
        hdUrl = "about:blank"
    )
    private val validPicList = listOf(
        picOfTheDayItem,
        picOfTheDayItem.copy(
            title = "sample_image_2",
            date = "2022-05-02",
        ),
        picOfTheDayItem.copy(
            title = "sample_image_3",
            date = "2022-05-03",
        )
    )
    private var isOnItemClickedCalled = false

    @Test
    @DisplayName("Given a provided click listener, When clicking on LatestCollectionCard, Then it should call the listener")
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

    @Test
    @DisplayName("Given a list of three items, When composition happens, Then it should display three LatestCollectionCard")
    fun latestCollectionRowShouldDisplayThreeItems() {
        val testTag = "test_tag"
        extension.use {
            setContent {
                LatestCollectionRow(data = validPicList, modifier = Modifier.testTag(testTag)) {}
            }
            val parentNode = onNodeWithTag(testTag)
            parentNode.onChildAt(0).assertExists()
            parentNode.performScrollToIndex(1)
            parentNode.onChildAt(1).assertExists()
            parentNode.performScrollToIndex(2)
            parentNode.onChildAt(2).assertExists()

        }
    }

    @Test
    @DisplayName("Given a valid item, When composition happens, Then it should have the right size")
    fun latestCollectionRowItemsShouldBeTheCorrectSize() {
        val testTag = "test_tag"
        extension.use {
            setContent {
                LatestCollectionRow(
                    data = listOf(picOfTheDayItem),
                    modifier = Modifier.testTag(testTag)
                ) {}
            }
            val parentNode = onNodeWithTag(testTag)
            parentNode.onChildAt(0).assertHeightIsEqualTo(144.dp)
            parentNode.onChildAt(0).assertWidthIsEqualTo(256.dp)
        }
    }

    @Test
    @DisplayName("Given a provided click listener, When clicking on the RandomPicsGridCard, Then it should call the listener")
    fun randomPicsGridCardShouldCallOnItemClickWhenClicked() {
        extension.use {
            setContent {
                RandomPicsGridCard(item = picOfTheDayItem) {
                    isOnItemClickedCalled = true
                }
            }

            onNode(hasClickAction()).performClick()
            assertThat(isOnItemClickedCalled).isTrue()
        }
    }

    @Test
    @DisplayName("Given a valid item, When composition happen, Then it be the right height")
    fun randomPicsGridCardShouldBeTheRightHeight() {
        extension.use {
            setContent {
                RandomPicsGridCard(item = picOfTheDayItem) {}
            }

            onNode(hasClickAction()).assertHeightIsEqualTo(255.dp)
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    @Test
    @DisplayName("Given a list of three items, When composition happens, Then it should display three RandomPicsGridCard")
    fun randomPicsGridShouldDisplayThreeItems() {
        val testTag = "test_tag"
        val fakePager: Pager<Int, PicOfTheDay> = Pager(
            config = PagingConfig(pageSize = FetchPaginatedPicsUsecase.PAGE_SIZE),
            pagingSourceFactory = validPicList.map { it.toDomainModel() }.asPagingSourceFactory(),
            remoteMediator = null
        )
        extension.use {
            setContent {
                val dataFlow = fakePager.flow.map { it.map { it.toItem() } }
                RandomPicsGrid(modifier = Modifier.testTag(testTag), dataFlow = dataFlow) {}
            }
            val parentNode = onNodeWithTag(testTag)
            parentNode.onChildAt(0).assertExists()
            parentNode.performScrollToIndex(1)
            parentNode.onChildAt(1).assertExists()
            parentNode.performScrollToIndex(2)
            parentNode.onChildAt(2).assertExists()

        }
    }

    @Test
    @DisplayName("Given a provided click listener, When clicking on the FullWidthPictureItem, Then it should call the listener")
    fun fullWidthPictureItemShouldCallOnItemClickWhenClicked() {
        extension.use {
            setContent {
                FullWidthPictureItem(item = picOfTheDayItem) {
                    isOnItemClickedCalled = true
                }
            }

            onNode(hasClickAction()).performClick()
            assertThat(isOnItemClickedCalled).isTrue()
        }
    }
}