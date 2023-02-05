package ru.net.arh.vocabulary.bh.service.common

import org.springframework.stereotype.Service
import java.util.*

@Service
class MessageTemplateProviderImpl(
    private val userProfileService: UserProfileService
) : MessageTemplateProvider {

    private val resourceBundlesMap = mutableMapOf<Locale, ResourceBundle>()
    override fun <E : Enum<E>> getMessage(chatId: Long, messageCode: E): String {
        return getString(getLocale(chatId), messageCode.toString())
    }

    override fun <E : Enum<E>> getMessage(locale: Locale, messageCode: E): String {
        return getString(locale, messageCode.toString())
    }

    private fun getString(locale: Locale, code: String): String {
        val resourceBundle = getResourceBundle(locale)
        return resourceBundle.getString(code)
    }

    private fun getResourceBundle(locale: Locale) = resourceBundlesMap.getOrPut(
        locale,
        { ResourceBundle.getBundle("locale/messages", locale) })

    fun getLocale(chatId: Long) = userProfileService.getUserProfile(chatId).locale

}