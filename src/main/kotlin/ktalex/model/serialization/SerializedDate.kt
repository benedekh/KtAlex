package ktalex.model.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import ktalex.utils.DateUtil
import java.time.LocalDate

@Serializable(with = SerializedDateSerializer::class)
data class SerializedDate(
    val serialized: String,
    val date: LocalDate?,
)

class SerializedDateSerializer : KSerializer<SerializedDate> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("SerializedDateSerializer", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: SerializedDate) {
        encoder.encodeString(value.serialized)
    }

    override fun deserialize(decoder: Decoder): SerializedDate {
        val serialized = decoder.decodeString()
        val date = DateUtil.toDate(serialized)
        return SerializedDate(serialized, date)
    }
}
