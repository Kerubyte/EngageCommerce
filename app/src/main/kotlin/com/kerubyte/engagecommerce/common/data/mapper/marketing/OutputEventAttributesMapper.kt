package com.kerubyte.engagecommerce.common.data.mapper.marketing

import com.kerubyte.engagecommerce.common.util.Constants.ATTR_EMAIL
import com.kerubyte.engagecommerce.common.util.Constants.ATTR_FIRST_NAME
import com.kerubyte.engagecommerce.common.util.Constants.ATTR_LAST_NAME
import com.kerubyte.engagecommerce.common.util.DatabaseMapper

class OutputEventAttributesMapper : DatabaseMapper<Any, HashMap<String, Any>> {

    fun mapFromRegistrationInput(
        firstName: String,
        lastName: String,
        email: String
    ): HashMap<String, Any> =
        hashMapOf(
            ATTR_FIRST_NAME to firstName,
            ATTR_LAST_NAME to lastName,
            ATTR_EMAIL to email
        )

    fun mapFromLoginInput(
        email: String
    ): HashMap<String, Any> =
        hashMapOf(
            ATTR_EMAIL to email
        )
}