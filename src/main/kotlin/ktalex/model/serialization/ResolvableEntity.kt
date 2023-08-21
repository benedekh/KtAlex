package ktalex.model.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import ktalex.dal.client.AuthorClient
import ktalex.dal.client.ConceptClient
import ktalex.dal.client.FunderClient
import ktalex.dal.client.InstitutionClient
import ktalex.dal.client.PublisherClient
import ktalex.dal.client.SourceClient
import ktalex.dal.client.WorkClient

@Serializable(with = ResolvableEntitySerializer::class)
data class ResolvableEntity<T>(
    override val url: String,
    override val id: String,
) : BaseSerializedId {
    @Suppress("UNCHECKED_CAST")
    fun resolveEntity(): T? {
        if (id.isEmpty()) return null
        return when (id[0]) {
            'W' -> WorkClient().getByOpenAlexId(id) as T
            'A' -> AuthorClient().getByOpenAlexId(id) as T
            'S' -> SourceClient().getByOpenAlexId(id) as T
            'I' -> InstitutionClient().getByOpenAlexId(id) as T
            'C' -> ConceptClient().getByOpenAlexId(id) as T
            'P' -> PublisherClient().getByOpenAlexId(id) as T
            'F' -> FunderClient().getByOpenAlexId(id) as T
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
