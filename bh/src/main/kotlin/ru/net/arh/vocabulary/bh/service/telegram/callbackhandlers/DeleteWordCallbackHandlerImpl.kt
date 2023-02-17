package ru.net.arh.vocabulary.bh.service.telegram.callbackhandlers

import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import ru.net.arh.vocabulary.bh.aop.ClearSimpleMessageBeforeRunThisMethod
import ru.net.arh.vocabulary.bh.exceptions.VocabularyBhException
import ru.net.arh.vocabulary.bh.exceptions.VocabularyErrorCode
import ru.net.arh.vocabulary.bh.service.businesslogic.repository.WordRepository
import ru.net.arh.vocabulary.bh.service.telegram.sendmessagefactories.WordDeletedSendMessageFactory

/**
 * CallbackHandler implementation.
 * Used when the user clicks on button to delete the word.
 */
@Service(DeleteWordCallbackHandlerImpl.NAME)
class DeleteWordCallbackHandlerImpl(
        private val wordRepository: WordRepository,
        private val wordDeletedSendMessageFactory: WordDeletedSendMessageFactory,
): CallbackHandler {

    companion object {
        const val NAME = "deleteWord"
        val log = LoggerFactory.getLogger(DeleteWordCallbackHandlerImpl::class.java)
    }

    @ClearSimpleMessageBeforeRunThisMethod
    override fun onUpdate(callbackParams: Map<String, Any?>, chatId: Long, messageId: Int, updateId: Int): SendMessage? {
        log.debug("Start processing 'deleteWord' callback. ChatId={}", chatId)
        val wordId = callbackParams.get("word_id").toString().toLong()
        val word = wordRepository.findByIdOrNull(wordId)
        if (word == null) {
            throw VocabularyBhException(VocabularyErrorCode.WORD_NOT_FOUND)
        }
        wordRepository.deleteById(wordId)
        return wordDeletedSendMessageFactory.getInstance(chatId, word.original)
    }
}