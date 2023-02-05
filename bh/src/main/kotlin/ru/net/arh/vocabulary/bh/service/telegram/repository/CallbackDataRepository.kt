package ru.net.arh.vocabulary.bh.service.telegram.repository

import ru.net.arh.vocabulary.bh.data.CallbackData

interface CallbackDataRepository {
    fun saveCallbackData(callbackData: CallbackData): Long
    fun getCallbackData(id: Long): CallbackData
}