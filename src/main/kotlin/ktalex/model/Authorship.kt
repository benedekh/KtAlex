package ktalex.model

import ktalex.utils.EnumUtil

data class Authorship(
    val author: DehydratedAuthor,
    val authorPosition: String,
    val institutions: List<DehydratedInstitution>,
    val isCorresponding: Boolean,
    val rawAffiliationString: String
) {
    val authorPositionEnum: AuthorPosition? = EnumUtil.valueOfOrNull<AuthorPosition>(authorPosition)
}

enum class AuthorPosition {
    FIRST, MIDDLE, LAST
}

