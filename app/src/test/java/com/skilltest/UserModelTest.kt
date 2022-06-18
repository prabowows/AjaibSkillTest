package com.skilltest

import com.skilltest.model.UserModel
import junit.framework.TestCase
import org.junit.Test

class UserModelTest {

    private var fakeId = 1
    private var fakeUserId = 99
    private var fakeTitle = "Title"

    @Test
    fun `validating model return correctly`() {
        var fakeModel = UserModel(
            id = fakeId,
        )

        TestCase.assertEquals(fakeModel.id, fakeId)

    }
}