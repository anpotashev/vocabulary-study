package ru.net.arh.vocabulary.bh.service.telegram.repository

import ru.net.arh.vocabulary.bh.data.CallbackData

/**
 * Repository to work with callback data
 */
interface CallbackDataRepository {

    /**
     * Saves callback data
     * @param callbackData - callbackData
     * @see ru.net.arh.vocabulary.bh.data.CallbackData
     * @return saved data identifier
     */
    fun saveCallbackData(callbackData: CallbackData): Long

    /**
     * retrieves callback data
     * @param callback data identifier
     * @return callback data
     * @see ru.net.arh.vocabulary.bh.data.CallbackData
     */
    fun getCallbackData(id: Long): CallbackData
}