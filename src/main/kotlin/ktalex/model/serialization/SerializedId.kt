package ktalex.model.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import ktalex.dal.client.*

abstract class BaseSerializedId {
    abstract val url: String
    abstract val id: String
}

@Serializable(with = SerializedIdSerializer::class)
data class SerializedId(
    override val url: String,
    override val id: String
) : BaseSerializedId()

class SerializedIdSerializer : KSerializer<SerializedId> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("SerializedIdSerializer", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: SerializedId) {
        encoder.encodeString(value.url)
    }

    override fun deserialize(decoder: Decoder): SerializedId {
        val url = decoder.decodeString()
        val id = url.split("/").last()
        return SerializedId(url, id)
    }

}