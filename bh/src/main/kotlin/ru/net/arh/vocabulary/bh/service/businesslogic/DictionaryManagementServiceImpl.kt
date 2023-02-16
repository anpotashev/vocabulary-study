package ru.net.arh.vocabulary.bh.service.businesslogic

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ru.net.arh.vocabulary.bh.data.Dictionary
import ru.net.arh.vocabulary.bh.exceptions.VocabularyBhException
import ru.net.arh.vocabulary.bh.exceptions.VocabularyErrorCode
import ru.net.arh.vocabulary.bh.service.businesslogic.repository.DictionaryRepository
import ru.net.arh.vocabulary.bh.service.businesslogic.repository.WordRepository

@Service
class DictionaryManagementServiceImpl(
        private val dictionaryRepository: DictionaryRepository,
        private val wordRepository: WordRepository,
): DictionaryManagementService {

    override fun getAll(chatId: Long): List<Dictionary> {
        return dictionaryRepository.findAllByUserProfileChatId(chatId)
    }

    override fun save(dictionary: Dictionary): Dictionary {
        return dictionaryRepository.save(dictionary)
    }

    override fun get(dictId: Long): Dictionary {
        return dictionaryRepository.findByIdOrNull(dictId)
                ?: throw VocabularyBhException(VocabularyErrorCode.DICTIONARY_NOT_FOUND)
    }

    override fun delete(dictId: Long) {
        dictionaryRepository.deleteById(dictId)
    }

    override fun isEmpty(dictId: Long): Boolean {
        return !wordRepository.existsByDictionaryId(dictId)
    }
}