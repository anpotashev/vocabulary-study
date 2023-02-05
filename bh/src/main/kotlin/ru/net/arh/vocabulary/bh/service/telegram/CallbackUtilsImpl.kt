package ru.net.arh.vocabulary.bh.service.telegram

import org.springframework.stereotype.Service
import ru.net.arh.vocabulary.bh.data.CallbackData
import ru.net.arh.vocabulary.bh.service.telegram.repository.CallbackDataRepository

@Service
class CallbackUtilsImpl(
    private val callbackDataRepository: CallbackDataRepository
) : CallbackUtils {
    override fun saveCallbackDataString(callbackName: String, params: Map<String, Any?>): String {
        return callbackDataRepository.saveCallbackData(
            CallbackData(
                name = callbackName,
                params = params
            )
        ).toString()
    }

    override fun getCallbackDataFromString(callbackString: String): Pair<String, Map<String, Any?>> {
        return callbackDataRepository.getCallbackData(callbackString.toLong())
            .let {
                it.name to it.params
            }
    }
}