package ru.net.arh.vocabulary.bh.data

import javax.persistence.*

/**
 * Entity for word|expression|sentence
 */
@Entity
@Table(name = "words")
data class Word(
        /**
         * Record identifier
         */
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,

        /**
         * Original word|expression|sentence
         */
        @Column
        var original: String = "",

        /**
         * translated word|expression|sentence
         */
        @Column
        var translated: String  = "",

        /**
         * success answers count
         */
        @Column(name = "success_results_count")
        var successCount: Long = 0,

        /**
         * wrong answers count
         */
        @Column(name = "failed_results_count")
        var failCount: Long = 0,

        /**
         * Dictionary containing this word|expression|sentence
         * @see Dictionary
         */
        @ManyToOne
        @JoinColumn(name = "dict_id", referencedColumnName = "id")
        var dictionary: Dictionary
)