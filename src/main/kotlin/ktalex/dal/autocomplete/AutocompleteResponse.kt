package ktalex.dal.autocomplete

import kotlinx.serialization.Serializable
import ktalex.dal.query.MetaInfo
import ktalex.model.serialization.EntityTypeSerializer
import ktalex.model.serialization.SerializedEnum

@Serializable
data class AutocompleteResponse(
    val meta: MetaInfo?,
    val results: List<AutocompleteResult>?,
)

/**
 * See [OpenAlex documentation](https://docs.openalex.org/how-to-use-the-api/get-lists-of-entities/autocomplete-entities#response-format).
 */
@Serializable
data class AutocompleteResult(
    val id: String?,
    val citedByCount: Int?,
    val displayName: String?,
    val externalId: String?,
    @Serializable(with = EntityTypeSerializer::class)
    val entityType: SerializedEnum<EntityType>?,
    val filterKey: String?,
    val hint: String?,
    val worksCount: Int?,
)

enum class EntityType {
    AUTHOR,
    CONCEPT,
    INSTITUTION,
    SOURCE,
    PUBLISHER,
    FUNDER,
    WORK,
}
