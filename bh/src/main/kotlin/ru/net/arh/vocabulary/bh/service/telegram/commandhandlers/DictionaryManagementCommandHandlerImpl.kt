package ru.net.arh.vocabulary.bh.service.telegram.commandhandlers

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import ru.net.arh.vocabulary.bh.aop.ClearSimpleMessageBeforeRunThisMethod
import ru.net.arh.vocabulary.bh.service.businesslogic.DictionaryManagementService
import ru.net.arh.vocabulary.bh.service.telegram.callbackhandlers.ProcessSelectDictionaryToManageCallbackHandlerImpl
import ru.net.arh.vocabulary.bh.service.telegram.sendmessagefactories.DictionaryManagementSendMessageFactory

/**
 * Handler for /dictionary command
 */
@Service("dictionary")
class DictionaryManagementCommandHandlerImpl(
        private val dictionaryManagementService: DictionaryManagementService,
        private val dictionaryManagementSendMessageFactory: DictionaryManagementSendMessageFactory
): CommandHandler {

    companion object {
        val log = LoggerFactory.getLogger(DictionaryManagementCommandHandlerImpl::class.java)
    }

    @ClearSimpleMessageBeforeRunThisMethod
    override fun onUpdate(params: String, chatId: Long, messageId: Int, updateId: Int): SendMessage {
        log.debug("Starting processing 'dictionary' command. ChatId={}", chatId)
        val dictionaries = dictionaryManagementService.getAll(chatId)
        return dictionaryManagementSendMessageFactory.getInstance(chatId, dictionaries)
    }
}