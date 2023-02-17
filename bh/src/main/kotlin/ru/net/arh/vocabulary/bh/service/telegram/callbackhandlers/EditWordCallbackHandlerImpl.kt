package ru.net.arh.vocabulary.bh.service.telegram.callbackhandlers

import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import ru.net.arh.vocabulary.bh.aop.ClearSimpleMessageBeforeRunThisMethod
import ru.net.arh.vocabulary.bh.exceptions.VocabularyBhException
import ru.net.arh.vocabulary.bh.exceptions.VocabularyErrorCode
import ru.net.arh.vocabulary.bh.service.businesslogic.repository.WordRepository
import ru.net.arh.vocabulary.bh.service.telegram.sendmessagefactories.EditWordSendMessageFactory

/**
 * CallbackHandler implementation.
 * Used when user starts editing the word
 */
@Service(EditWordCallbackHandlerImpl.NAME)
class EditWordCallbackHandlerImpl(
        private val wordRepository: WordRepository,
        private val editWordSendMessageFactory: EditWordSendMessageFactory,
): CallbackHandler {

    companion object {
        const val NAME = "editWord"
        val log = LoggerFactory.getLogger(EditWordCallbackHandlerImpl::class.java)
    }

    @ClearSimpleMessageBeforeRunThisMethod
    override fun onUpdate(callbackParams: Map<String, Any?>, chatId: Long, messageId: Int, updateId: Int): SendMessage? {
        log.debug("Start processing 'editWord' callback. ChatId={}", chatId)
        val wordId = callbackParams.get("word_id").toString().toLong()
        val word = wordRepository.findByIdOrNull(wordId) ?: throw VocabularyBhException(VocabularyErrorCode.WORD_NOT_FOUND)
        return editWordSendMessageFactory.getInstance(chatId, word)
    }
}