package ktalex.model.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable(with = SerializedIdSerializer::class)
data class SerializedId (
    val url: String,
    val id: String
)

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