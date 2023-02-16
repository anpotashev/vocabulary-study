package ru.net.arh.vocabulary.bh.service.telegram.simplemessagehandlers

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import ru.net.arh.vocabulary.bh.exceptions.VocabularyBhException
import ru.net.arh.vocabulary.bh.exceptions.VocabularyErrorCode
import ru.net.arh.vocabulary.bh.service.businesslogic.DictionaryManagementService
import ru.net.arh.vocabulary.bh.service.common.UserProfileService
import ru.net.arh.vocabulary.bh.service.telegram.sendmessagefactories.DictionaryDeletedSendMessageFactory
import ru.net.arh.vocabulary.bh.service.telegram.sendmessagefactories.OperationCanceledSendMessageFactory

/**
 * Handler used when user trying to delete non empty dictionary.
 * Expects confirmation or cancellation message
 */
@Service(ConfirmDeleteDictionarySimpleMessageSendMessageHandlerImpl.NAME)
class ConfirmDeleteDictionarySimpleMessageSendMessageHandlerImpl(
        private val userProfileService: UserProfileService,
        private val dictionaryManagementService: DictionaryManagementService,
        private val dictionaryDeletedSendMessageFactory: DictionaryDeletedSendMessageFactory,
        private val operationCanceledSendMessageFactory: OperationCanceledSendMessageFactory
) : SimpleMessageHandler {

    companion object {
        const val NAME = "confirmDeleteDictionarySimpleMessageHandler"
        val log = LoggerFactory.getLogger(ConfirmDeleteDictionarySimpleMessageSendMessageHandlerImpl::class.java)
    }

    override fun onUpdate(simpleMessageParams: Map<String, Any?>, chatId: Long, messageId: Int, updateId: Int, messageText: String): SendMessage? {
        log.debug("Start processing a text message in the process of deleting non empty dictionary. ChatId={}", chatId)
        if (messageText == "CANCEL") {
            log.debug("User canceled.")
            userProfileService.clearSimpleMessageData(chatId)
            return operationCanceledSendMessageFactory.getInstance(chatId)
        }
        if (messageText != "DELETE") {
            log.debug("User send unexpected text. Throwing an exception.")
            throw VocabularyBhException(VocabularyErrorCode.UNKNOWN_COMMAND)
        }
        log.debug("Clearing current user status.")
        userProfileService.clearSimpleMessageData(chatId)
        val dictId = simpleMessageParams.get("dict_id").toString().toLong()
        val dictionary = dictionaryManagementService.get(dictId)
        log.debug("Deleting dictionary")
        dictionaryManagementService.delete(dictId)
        return dictionaryDeletedSendMessageFactory.getInstance(chatId, dictionary)
    }


}