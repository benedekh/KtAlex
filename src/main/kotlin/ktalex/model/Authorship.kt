package ktalex.model

import kotlinx.serialization.Serializable
import ktalex.model.serialization.AuthorPositionSerializer
import ktalex.model.serialization.SerializedEnum

@Serializable
data class Authorship(
    val author: DehydratedAuthor?,
    @Serializable(with = AuthorPositionSerializer::class)
    val authorPosition: SerializedEnum<AuthorPosition>?,
    val countries: List<String>?,
    val institutions: List<DehydratedInstitution>?,
    val isCorresponding: Boolean?,
    val rawAffiliationString: String?,
    val rawAffiliationStrings: List<String>?
)

enum class AuthorPosition {
    FIRST, MIDDLE, LAST
}

