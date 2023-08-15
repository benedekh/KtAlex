package ktalex.model.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import ktalex.utils.EnumUtil

@Serializable(with = SerializedEnumSerializer::class)
data class SerializedEnum<E : Enum<E>>(
    val serialized: String,
    val enum: Enum<E>?,
)

class SerializedEnumSerializer : KSerializer<SerializedEnum<*>> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("SerializedEnumSerializer", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: SerializedEnum<*>) {
        encoder.encodeString(value.serialized)
    }

    override fun deserialize(decoder: Decoder): SerializedEnum<*> {
        val serialized = decoder.decodeString()
        val enum = EnumUtil.guessEnum(serialized)
        return SerializedEnum(serialized, enum)
    }

}

