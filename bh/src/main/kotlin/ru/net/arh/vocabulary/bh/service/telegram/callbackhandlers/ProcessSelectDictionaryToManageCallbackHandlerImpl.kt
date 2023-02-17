package ru.net.arh.vocabulary.bh.service.telegram.callbackhandlers

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import ru.net.arh.vocabulary.bh.aop.ClearSimpleMessageBeforeRunThisMethod
import ru.net.arh.vocabulary.bh.service.businesslogic.DictionaryManagementService
import ru.net.arh.vocabulary.bh.service.telegram.sendmessagefactories.ManageDictionarySendMessageFactory

/**
 * CallbackHandler implementation.
 * Used when user selects dictionary to manage.
 */
@Service(ProcessSelectDictionaryToManageCallbackHandlerImpl.NAME)
class ProcessSelectDictionaryToManageCallbackHandlerImpl(
        private val dictionaryManagementService: DictionaryManagementService,
        private val manageDictionarySendMessageFactory: ManageDictionarySendMessageFactory
): CallbackHandler {

    companion object {
        const val NAME = "processSelectDictionaryToManage"
        val log = LoggerFactory.getLogger(ProcessSelectDictionaryToManageCallbackHandlerImpl::class.java)
    }

    @ClearSimpleMessageBeforeRunThisMethod
    override fun onUpdate(callbackParams: Map<String, Any?>, chatId: Long, messageId: Int, updateId: Int): SendMessage? {
        log.debug("Starting processing 'processSelectDictionaryToManage' callback. ChatId={}", chatId)
        val dictId = callbackParams.get("dict_id").toString().toLong()
        val dictionary = dictionaryManagementService.get(dictId)
        return manageDictionarySendMessageFactory.getInstance(chatId, dictionary)
    }
}