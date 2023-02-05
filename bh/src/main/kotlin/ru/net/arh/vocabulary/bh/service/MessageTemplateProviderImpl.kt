package ru.net.arh.vocabulary.bh.service

import org.springframework.stereotype.Service
import java.text.MessageFormat
import java.util.*

@Service
class MessageTemplateProviderImpl(
    private val userProfileService: UserProfileService
): MessageTemplateProvider {

    private val resourceBundlesMap = mutableMapOf<Locale, ResourceBundle>()
    override fun <E : Enum<E>> getMessage(chatId: Long, messageCode: E): String {
        return getString(chatId, messageCode.toString())
    }

    private fun getString(chatId: Long, code: String): String {
        val userLocale = getLocale(chatId)
        val resourceBundle = getResourceBundle(userLocale)
        return resourceBundle.getString(code)
    }

    private fun getResourceBundle(locale: Locale) = resourceBundlesMap.getOrPut(
        locale,
        { ResourceBundle.getBundle("locale/messages", locale) })

    fun getLocale(chatId: Long) = userProfileService.getUserProfile(chatId).locale

}