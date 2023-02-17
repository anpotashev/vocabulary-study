package ru.net.arh.vocabulary.bh.service.telegram.callbackhandlers

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import ru.net.arh.vocabulary.bh.aop.ClearSimpleMessageBeforeRunThisMethod
import ru.net.arh.vocabulary.bh.service.businesslogic.DictionaryManagementService
import ru.net.arh.vocabulary.bh.service.common.UserProfileService
import ru.net.arh.vocabulary.bh.service.telegram.sendmessagefactories.*

/**
 * CallbackHandler implementation.
 * Used when user clicks on action-button on manage dictionary form.
 */
@Service(ProcessManageDictionaryActionCallbackHandlerImpl.NAME)
class ProcessManageDictionaryActionCallbackHandlerImpl(
        private val dictionaryManagementService: DictionaryManagementService,
        private val dictionaryDeletedSendMessageFactory: DictionaryDeletedSendMessageFactory,
        private val confirmDeleteNonEmptyDictionarySendMessageFactory: ConfirmDeleteNonEmptyDictionarySendMessageFactory,
        private val dictionarySetActiveSendMessageFactory: DictionarySetActiveSendMessageFactory,
        private val renameDictionarySendMessageFactory: RenameDictionarySendMessageFactory,
        private val startAddingWordsSendMessageFactory: StartAddingWordsSendMessageFactory,
        private val userProfileService: UserProfileService,
): CallbackHandler {

    companion object {
        const val NAME = "processManageDictionaryAction"
        val log = LoggerFactory.getLogger(ProcessManageDictionaryActionCallbackHandlerImpl::class.java)
    }

    @ClearSimpleMessageBeforeRunThisMethod
    override fun onUpdate(callbackParams: Map<String, Any?>, chatId: Long, messageId: Int, updateId: Int): SendMessage {
        log.debug("Start processing 'processManageDictionaryAction' callback. ChatId={}", chatId)
        log.debug("Retrieving data from data-map")
        val dictId = callbackParams.get("dict_id").toString().toLong()
        val action = Actions.valueOf(callbackParams.get("action").toString())
        log.debug("Data: action: {}, dictionary_id: {}", action, dictId)
        return when(action) {
            Actions.DELETE -> processDelete(dictId, chatId)
            Actions.SET_ACTIVE -> processSetActive(dictId, chatId)
            Actions.RENAME -> processRename(dictId, chatId)
            Actions.ADD_WORDS -> processManageWords(dictId, chatId)
        }
    }

    private fun processManageWords(dictId: Long, chatId: Long): SendMessage {
        log.debug("Processing start adding words in dictionary.")
        return startAddingWordsSendMessageFactory.getInstance(chatId, dictId)
    }

    private fun processRename(dictId: Long, chatId: Long): SendMessage {
        log.debug("Processing start of renaming dictionary.")
        return renameDictionarySendMessageFactory.getInstance(chatId, dictionaryManagementService.get(dictId))
    }

    private fun processSetActive(dictId: Long, chatId: Long): SendMessage {
        log.debug("Processing start of setting dictionary active.")
        val userProfile = userProfileService.getUserProfile(chatId)
        val dictionary = dictionaryManagementService.get(dictId)
        userProfile.activeDictionary = dictionary
        userProfileService.save(userProfile)
        return dictionarySetActiveSendMessageFactory.getInstance(chatId, dictionary)
    }

    private fun processDelete(dictId: Long, chatId: Long): SendMessage {
        log.debug("Processing start of deleting dictionary.")
        val dictionary = dictionaryManagementService.get(dictId)
        if (dictionaryManagementService.isEmpty(dictId)) {
            log.debug("Dictionary contains no word. Deleting without confirmation.")
            dictionaryManagementService.delete(dictId)
            return dictionaryDeletedSendMessageFactory.getInstance(chatId, dictionary)
        }
        log.debug("Dictionary contains words. To delete need confirmation.")
        return confirmDeleteNonEmptyDictionarySendMessageFactory.getInstance(chatId, dictionary)
    }

    enum class Actions {
        DELETE,
        SET_ACTIVE,
        RENAME,
        ADD_WORDS
    }
}
