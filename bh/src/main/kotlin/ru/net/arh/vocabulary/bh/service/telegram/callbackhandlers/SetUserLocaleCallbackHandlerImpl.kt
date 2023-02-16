package ru.net.arh.vocabulary.bh.service.telegram.callbackhandlers

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import ru.net.arh.vocabulary.bh.service.common.UserProfileService
import ru.net.arh.vocabulary.bh.service.telegram.sendmessagefactories.LocaleSetSendMessageFactory
import java.util.*

/**
 * CallbackHandler implementation.
 * Used when the user selects language to set as the interface language.
 */
@Service(SetUserLocaleCallbackHandlerImpl.NAME)
class SetUserLocaleCallbackHandlerImpl(
        private val userProfileService: UserProfileService,
        private val localeSetSendMessageFactory: LocaleSetSendMessageFactory,
): CallbackHandler {

    companion object {
        const val NAME = "setUserLocale"
        val log = LoggerFactory.getLogger(SetUserLocaleCallbackHandlerImpl::class.java)
    }

    override fun onUpdate(callbackParams: Map<String, Any?>, chatId: Long, messageId: Int, updateId: Int): SendMessage? {
        val locale = Locale(callbackParams.get("locale").toString())
        val userProfile = userProfileService.getUserProfile(chatId)
        userProfile.locale = locale
        userProfileService.save(userProfile)
        return localeSetSendMessageFactory.getInstance(chatId, locale)
    }
}