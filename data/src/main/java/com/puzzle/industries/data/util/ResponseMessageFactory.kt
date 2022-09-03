package com.puzzle.industries.data.util

import android.content.Context
import com.puzzle.industries.data.R
import com.puzzle.industries.domain.common.response.Response

class ResponseMessageFactory(val context: Context) {

    inline fun buildInsertMessage(create: () -> Boolean): Response<Boolean> {
        return try {
            val success = create()
            val resId = if (success) R.string.insert_success else R.string.insert_failed
            constructResponse(response = success, resId = resId)
        } catch (ex: Exception) {
            constructResponse(
                response = false,
                resId = R.string.insert_failed_already_exist,
                exception = ex
            )
        }
    }

    inline fun buildDeleteMessage(expectedAffectedRow: Int, delete: () -> Int): Response<Boolean> {
        val result = delete()
        val resId = when {
            result == expectedAffectedRow -> R.string.delete_success
            result > 0 -> R.string.delete_partial
            else -> R.string.delete_failed
        }

        return constructResponse(result > 0, resId)
    }

    inline fun buildUpdateMessage(expectedAffectedRow: Int, update: () -> Int): Response<Boolean> {
        return try {
            val result = update()
            val resId = when {
                result == expectedAffectedRow -> R.string.update_success
                result > 0 -> R.string.update_partial
                else -> R.string.update_failed
            }
            constructResponse(response = result > 0, resId = resId)
        } catch (ex: Exception) {
            constructResponse(
                response = false,
                resId = R.string.update_failed,
                exception = ex
            )
        }
    }


    fun constructResponse(
        response: Boolean,
        resId: Int,
        exception: Exception? = null
    ): Response<Boolean> {
        val message = context.getString(resId)
        return Response(response = response, message = message, exception = exception)
    }
}