package ru.net.arh.vocabulary.bh.service.telegram.callbackhandlers

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import ru.net.arh.vocabulary.bh.service.telegram.sendmessagefactories.CreateDictionarySendMessageFactory

/**
 * CallbackHandler implementation.
 * Used when user clicks on "create dictionary" button
 */
@Service(CreateDictionaryCallbackHandlerImpl.NAME)
class CreateDictionaryCallbackHandlerImpl(
        private val createDictionarySendMessageFactory: CreateDictionarySendMessageFactory
): CallbackHandler {

    companion object {
        const val NAME = "createDictionary"
        val log = LoggerFactory.getLogger(CreateDictionaryCallbackHandlerImpl::class.java)
    }

    override fun onUpdate(callbackParams: Map<String, Any?>, chatId: Long, messageId: Int, updateId: Int): SendMessage? {
        log.debug("Starting processing 'createDictionary' callback. ChatId={}", chatId)
        return createDictionarySendMessageFactory.getInstance(chatId)
    }

}