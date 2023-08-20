package ktalex.model.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import ktalex.dal.client.AuthorsClient
import ktalex.dal.client.ConceptsClient
import ktalex.dal.client.FundersClient
import ktalex.dal.client.InstitutionsClient
import ktalex.dal.client.PublishersClient
import ktalex.dal.client.SourcesClient
import ktalex.dal.client.WorksClient

@Serializable(with = ResolvableEntitySerializer::class)
data class ResolvableEntity<T>(
    override val url: String,
    override val id: String,
) : BaseSerializedId {
    @Suppress("UNCHECKED_CAST")
    fun resolveEntity(): T? {
        if (id.isEmpty()) return null
        return when (id[0]) {
            'W' -> WorksClient().getByOpenAlexId(id) as T
            'A' -> AuthorsClient().getByOpenAlexId(id) as T
            'S' -> SourcesClient().getByOpenAlexId(id) as T
            'I' -> InstitutionsClient().getByOpenAlexId(id) as T
            'C' -> ConceptsClient().getByOpenAlexId(id) as T
            'P' -> PublishersClient().getByOpenAlexId(id) as T
            'F' -> FundersClient().getByOpenAlexId(id) as T
            else -> null
        }
    }
}

class ResolvableEntitySerializer<T> : KSerializer<ResolvableEntity<T>> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("ResolvableEntitySerializer", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: ResolvableEntity<T>) {
        encoder.encodeString(value.url)
    }

    override fun deserialize(decoder: Decoder): ResolvableEntity<T> {
        val url = decoder.decodeString()
        val id = url.split("/").last()
        return ResolvableEntity(url, id)
    }
}
