package ru.net.arh.vocabulary.bh.service.telegram

interface CallbackUtils {
    /**
     * saves callback-data and returns string using that we could get the source data
     * @param callbackName - handlers name
     * @param params - any map we need to save
     * @return string message we use as a callback-data
     */
    fun saveCallbackDataString(callbackName: String, params: Map<String, Any?>): String

    /**
     * processes callback-data string. Returns that we have saved in saveCallbackDataString
     * @param callbackString - data from callback-data field in telegram-u
     * @return callback's handler name and a map of source data
     */
    fun getCallbackDataFromString(callbackString: String): Pair<String, Map<String, Any?>>
}