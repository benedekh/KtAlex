package ktalex.model

import kotlinx.serialization.Serializable
import ktalex.utils.EnumUtil

@Serializable
data class Authorship(
    val author: DehydratedAuthor,
    val authorPosition: String,
    val countries: List<String>,
    val institutions: List<DehydratedInstitution>,
    val isCorresponding: Boolean,
    val rawAffiliationString: String,
    val rawAffiliationStrings: List<String>
) {
    val authorPositionEnum: AuthorPosition? = EnumUtil.valueOfOrNull<AuthorPosition>(authorPosition)
}

enum class AuthorPosition {
    FIRST, MIDDLE, LAST
}

