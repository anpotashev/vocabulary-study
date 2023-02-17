package ru.net.arh.vocabulary.bh.service.telegram.simplemessagehandlers

import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import ru.net.arh.vocabulary.bh.data.Word
import ru.net.arh.vocabulary.bh.exceptions.VocabularyBhException
import ru.net.arh.vocabulary.bh.exceptions.VocabularyErrorCode
import ru.net.arh.vocabulary.bh.service.businesslogic.repository.WordRepository
import ru.net.arh.vocabulary.bh.service.common.UserProfileService
import ru.net.arh.vocabulary.bh.service.telegram.sendmessagefactories.OperationCanceledSendMessageFactory
import ru.net.arh.vocabulary.bh.service.telegram.sendmessagefactories.WordSavedSendMessageFactory

@Service(SaveWordSimpleMessageHandler.NAME)
class SaveWordSimpleMessageHandler(
        private val userProfileService: UserProfileService,
        private val operationCanceledSendMessageFactory: OperationCanceledSendMessageFactory,
        private val wordRepository: WordRepository,
        private val wordSavedSendMessageFactory: WordSavedSendMessageFactory,
): SimpleMessageHandler {

    companion object {
        const val NAME = "saveWordSimpleMessageHandler"
        val log = LoggerFactory.getLogger(RenameDictionarySimpleMessageHandlerImpl::class.java)
    }

    override fun onUpdate(simpleMessageParams: Map<String, Any?>, chatId: Long, messageId: Int, updateId: Int, messageText: String): SendMessage? {
        AddWordSimpleMessageHandlerImpl.log.debug("Start processing a text message in the process of editing word. ChatId={}", chatId)
        if (messageText == "CANCEL") {
            ConfirmDeleteDictionarySimpleMessageSendMessageHandlerImpl.log.debug("User canceled.")
            userProfileService.clearSimpleMessageData(chatId)
            return operationCanceledSendMessageFactory.getInstance(chatId)
        }
        val strings = messageText.split("\n").map { it.trim() }
        when {
            strings.size > 3 -> throw VocabularyBhException(VocabularyErrorCode.WRONG_MESSAGE_TO_SAVE_THE_WORD)
            strings.size == 3 && strings.get(2).isNotBlank() -> throw VocabularyBhException(VocabularyErrorCode.WRONG_MESSAGE_TO_SAVE_THE_WORD)
            strings.size < 2 -> throw VocabularyBhException(VocabularyErrorCode.WRONG_MESSAGE_TO_SAVE_THE_WORD)
            strings.get(0).isBlank() or strings.get(1).isBlank() -> throw VocabularyBhException(VocabularyErrorCode.WRONG_MESSAGE_TO_SAVE_THE_WORD)
        }
        val wordId = simpleMessageParams.get("word_id").toString().toLong()
        val word: Word = wordRepository.findByIdOrNull(id = wordId) ?: throw VocabularyBhException(VocabularyErrorCode.WORD_NOT_FOUND)
        word.original = strings.get(0)
        word.translated = strings.get(1)
        wordRepository.save(word)
        return wordSavedSendMessageFactory.getInstance(chatId)
    }
}