package ru.net.arh.vocabulary.bh.service.telegram.simplemessagehandlers

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import ru.net.arh.vocabulary.bh.exceptions.VocabularyBhException
import ru.net.arh.vocabulary.bh.exceptions.VocabularyErrorCode
import ru.net.arh.vocabulary.bh.service.businesslogic.DictionaryManagementService
import ru.net.arh.vocabulary.bh.service.common.UserProfileService
import ru.net.arh.vocabulary.bh.service.telegram.commandhandlers.QuestCommandHandlerImpl
import ru.net.arh.vocabulary.bh.service.telegram.sendmessagefactories.DictionaryRenamedSendMessageFactory
import ru.net.arh.vocabulary.bh.service.telegram.sendmessagefactories.OperationCanceledSendMessageFactory

/**
 * Handler used when user is renaming the dictionary.
 * Expects a new name of creating dictionary or cancellation message
 */
@Service(RenameDictionarySimpleMessageHandlerImpl.NAME)
class RenameDictionarySimpleMessageHandlerImpl(
        private val userProfileService: UserProfileService,
        private val dictionaryManagementService: DictionaryManagementService,
        private val dictionaryRenamedSendMessageFactory: DictionaryRenamedSendMessageFactory,
        private val operationCanceledSendMessageFactory: OperationCanceledSendMessageFactory
) : SimpleMessageHandler {

    companion object {
        const val NAME = "renameDictionarySimpleMessageHandler"
        val log = LoggerFactory.getLogger(RenameDictionarySimpleMessageHandlerImpl::class.java)
    }

    override fun onUpdate(simpleMessageParams: Map<String, Any?>, chatId: Long, messageId: Int, updateId: Int, messageText: String): SendMessage? {
        log.debug("Start processing a text message in the process of renaming dictionary. ChatId={}", chatId)
        if (messageText == "CANCEL") {
            log.debug("User canceled")
            userProfileService.clearSimpleMessageData(chatId)
            return operationCanceledSendMessageFactory.getInstance(chatId)
        }
        if (messageText.trim().isBlank()) {
            log.info("New dictionary name is null. Throwing an exception.")
            throw VocabularyBhException(VocabularyErrorCode.UNKNOWN_COMMAND)
        }
        log.debug("Clearing current user status.")
        userProfileService.clearSimpleMessageData(chatId)
        val dictId = simpleMessageParams.get("dict_id").toString().toLong()
        val dictionary = dictionaryManagementService.get(dictId)
        val oldName = dictionary.name
        dictionary.name = messageText.trim()
        log.debug("Saving dictionary")
        dictionaryManagementService.save(dictionary)
        return dictionaryRenamedSendMessageFactory.getInstance(chatId, oldName, dictionary.name)
    }
}