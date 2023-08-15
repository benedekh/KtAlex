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
import java.time.LocalDateTime

@Serializable(with = SerializedDateTimeSerializer::class)
data class SerializedDateTime(
    val serialized: String,
    val date: LocalDate?,
    val dateTime: LocalDateTime?,
)

class SerializedDateTimeSerializer : KSerializer<SerializedDateTime> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("SerializedDateTimeSerializer", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: SerializedDateTime) {
        encoder.encodeString(value.serialized)
    }

    override fun deserialize(decoder: Decoder): SerializedDateTime {
        val serialized = decoder.decodeString()
        val dateTime = DateUtil.toDateTime(serialized)
        val date = if (dateTime != null) {
            dateTime.toLocalDate()
        } else {
            DateUtil.toDate(serialized)
        }
        return SerializedDateTime(serialized, date, dateTime)
    }

}