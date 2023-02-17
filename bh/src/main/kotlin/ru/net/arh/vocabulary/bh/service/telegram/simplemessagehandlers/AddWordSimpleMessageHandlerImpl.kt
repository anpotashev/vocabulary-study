package ru.net.arh.vocabulary.bh.service.telegram.simplemessagehandlers

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import ru.net.arh.vocabulary.bh.data.Word
import ru.net.arh.vocabulary.bh.exceptions.VocabularyBhException
import ru.net.arh.vocabulary.bh.exceptions.VocabularyErrorCode
import ru.net.arh.vocabulary.bh.service.businesslogic.DictionaryManagementService
import ru.net.arh.vocabulary.bh.service.businesslogic.repository.WordRepository
import ru.net.arh.vocabulary.bh.service.common.UserProfileService
import ru.net.arh.vocabulary.bh.service.telegram.sendmessagefactories.AddedNWordsSendMessageFactory
import ru.net.arh.vocabulary.bh.service.telegram.sendmessagefactories.UserFinishedAddingWordsSendMessageFactory

/**
 * Handler used when user adds word to dictionary
 * Expects word-data or finish message
 */
@Service(AddWordSimpleMessageHandlerImpl.NAME)
class AddWordSimpleMessageHandlerImpl(
        private val wordRepository: WordRepository,
        private val dictionaryManagementService: DictionaryManagementService,
        private val userProfileService: UserProfileService,
        private val userFinishedAddingWordsSendMessageFactory: UserFinishedAddingWordsSendMessageFactory,
        private val addedNWordsSendMessageFactory: AddedNWordsSendMessageFactory,
) : SimpleMessageHandler {

    companion object {
        const val NAME = "addWordSimpleMessageHandlerImpl"
        val log = LoggerFactory.getLogger(ConfirmDeleteDictionarySimpleMessageSendMessageHandlerImpl::class.java)
    }

    override fun onUpdate(simpleMessageParams: Map<String, Any?>, chatId: Long, messageId: Int, updateId: Int, messageText: String): SendMessage? {
        log.debug("Start processing a text message in the process of adding words to dictionary. ChatId={}", chatId)
        if (messageText == "FINISH") {
            ConfirmDeleteDictionarySimpleMessageSendMessageHandlerImpl.log.debug("User finished.")
            userProfileService.clearSimpleMessageData(chatId)
            return userFinishedAddingWordsSendMessageFactory.getInstance(chatId)
        }
        val dictId = simpleMessageParams.get("dict_id").toString().toLong()
        val dictionary = dictionaryManagementService.get(dictId)
        val stringPars = messageText.split("\n")
                .map { it.trim() }
                .chunked(2)
                .toMutableList()
        if (stringPars.last().size == 1) {
            if (stringPars.last().get(0).isBlank()) {
                stringPars.removeLast()
            } else {
                throw VocabularyBhException(VocabularyErrorCode.ODD_LINE_COUNT_ON_ADDING_WORDS)
            }
        }
        val words = stringPars.map {
            Word(
                    original = it.get(0),
                    translated = it.get(1),
                    dictionary = dictionary
            )
        }
        wordRepository.saveAll(words)
        return addedNWordsSendMessageFactory.getInstance(chatId, dictionary.name, words.size)
    }
}