package ru.net.arh.vocabulary.bh.data

import javax.persistence.*

/**
 * Dictionary
 */
@Entity
@Table(name = "dict")
data class Dictionary(
        /**
         * record identifier
         */
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,

        /**
         * Dictionary name
         */
        @Column(name = "name")
        var name: String = "",

        /**
         * User profile
         */
        @ManyToOne
        @JoinColumn(name = "chat_id", referencedColumnName = "chat_id")
        var userProfile: UserProfile
)
