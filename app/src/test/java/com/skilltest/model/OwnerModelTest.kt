package com.skilltest.model

import junit.framework.TestCase
import org.junit.Test

class OwnerModelTest {

    private var fakeId = 1
    private var fakeType = "User"
    private var fakeLogin = "Title"

    @Test
    fun `validating model return correctly`() {
        var fakeModel = OwnerModel(
            id = fakeId,
            login = fakeLogin,
            type = fakeType
        )

        TestCase.assertEquals(fakeModel.type, fakeType)
        TestCase.assertEquals(fakeModel.id, fakeId)
        TestCase.assertEquals(fakeModel.login, fakeLogin)
    }
}