package ru.net.arh.vocabulary.bh.service.telegram.simplemessagehandlers

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import ru.net.arh.vocabulary.bh.data.Dictionary
import ru.net.arh.vocabulary.bh.exceptions.VocabularyBhException
import ru.net.arh.vocabulary.bh.exceptions.VocabularyErrorCode
import ru.net.arh.vocabulary.bh.service.businesslogic.DictionaryManagementService
import ru.net.arh.vocabulary.bh.service.common.UserProfileService
import ru.net.arh.vocabulary.bh.service.telegram.commandhandlers.QuestCommandHandlerImpl
import ru.net.arh.vocabulary.bh.service.telegram.sendmessagefactories.DictionaryCreatedSendMessageFactory
import ru.net.arh.vocabulary.bh.service.telegram.sendmessagefactories.OperationCanceledSendMessageFactory

/**
 * Handler used when user is creating a new dictionary.
 * Expects name of creating dictionary or cancellation message
 */
@Service(CreateDictionarySimpleMessageHandlerImpl.NAME)
class CreateDictionarySimpleMessageHandlerImpl(
        private val userProfileService: UserProfileService,
        private val dictionaryManagementService: DictionaryManagementService,
        private val dictionaryCreatedSendMessageFactory: DictionaryCreatedSendMessageFactory,
        private val operationCanceledSendMessageFactory: OperationCanceledSendMessageFactory
): SimpleMessageHandler {

    companion object {
        const val NAME = "createDictionarySimpleMessageHandler"
        val log = LoggerFactory.getLogger(CreateDictionarySimpleMessageHandlerImpl::class.java)
    }

    override fun onUpdate(simpleMessageParams: Map<String, Any?>, chatId: Long, messageId: Int, updateId: Int, messageText: String): SendMessage? {
        log.debug("Start processing a text message in the process of creating dictionary. ChatId={}", chatId)
        if (messageText == "CANCEL") {
            log.debug("User canceled")
            userProfileService.clearSimpleMessageData(chatId)
            return operationCanceledSendMessageFactory.getInstance(chatId)
        }
        if (messageText.trim().isBlank()) {
            log.info("Dictionary name is null. Throwing an exception.")
            throw VocabularyBhException(VocabularyErrorCode.UNKNOWN_COMMAND)
        }
        log.debug("Clearing current user status.")
        userProfileService.clearSimpleMessageData(chatId)
        val userProfile = userProfileService.getUserProfile(chatId)
        log.debug("Saving dictionary")
        val dictionary = dictionaryManagementService.save(Dictionary(
                name = messageText.trim(),
                userProfile = userProfile
        ))
        return dictionaryCreatedSendMessageFactory.getInstance(chatId, dictionary.name)
    }
}