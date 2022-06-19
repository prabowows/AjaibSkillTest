package com.skilltest.model

import junit.framework.TestCase
import org.junit.Test

class UserModelTest {

    private var fakeId = 1
    private var fakeScore = 100
    private var fakeLogin = "Title"

    @Test
    fun `validating model return correctly`() {
        var fakeModel = UserModel(
            id = fakeId,
            login = fakeLogin,
            score = fakeScore
        )

        TestCase.assertEquals(fakeModel.score, fakeScore)
        TestCase.assertEquals(fakeModel.id, fakeId)
        TestCase.assertEquals(fakeModel.login, fakeLogin)
    }
}