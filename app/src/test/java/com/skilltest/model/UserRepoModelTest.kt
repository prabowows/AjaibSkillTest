package com.skilltest.model

import junit.framework.TestCase
import org.junit.Test

class UserRepoModelTest {

    private var fakeId = 1
    private var fakeStar = 10
    private var fakeName = "Title"

    @Test
    fun `validating model return correctly`() {
        var fakeModel = UserRepoModel(
            id = fakeId,
            stargazersCount = fakeStar,
            name = fakeName
        )

        TestCase.assertEquals(fakeModel.stargazersCount, fakeStar)
        TestCase.assertEquals(fakeModel.id, fakeId)
        TestCase.assertEquals(fakeModel.name, fakeName)
    }
}