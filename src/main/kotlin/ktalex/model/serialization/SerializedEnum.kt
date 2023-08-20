package ktalex.model.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import ktalex.dal.autocomplete.EntityType
import ktalex.model.*
import java.util.*
import kotlin.reflect.KClass

data class SerializedEnum<E : Enum<E>>(
    val serialized: String,
    val enum: Enum<E>?,
)

sealed class SerializedEnumSerializer<E : Enum<E>>(
    private val enumCls: KClass<E>
) : KSerializer<SerializedEnum<E>> {

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("SerializedEnumSerializer", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: SerializedEnum<E>) {
        encoder.encodeString(value.serialized)
    }

    override fun deserialize(decoder: Decoder): SerializedEnum<E> {
        val serialized = decoder.decodeString()
        val preparedEnumValue = serialized.uppercase(Locale.ENGLISH).replace(" ", "_")
        val enum = enumCls.java.enumConstants.firstOrNull { it.name == preparedEnumValue }
        return SerializedEnum(serialized, enum)
    }

}

object AuthorPositionSerializer : SerializedEnumSerializer<AuthorPosition>(AuthorPosition::class)
object RoleEnumSerializer : SerializedEnumSerializer<RoleEnum>(RoleEnum::class)
object InstitutionTypeSerializer : SerializedEnumSerializer<InstitutionType>(InstitutionType::class)
object RelationshipTypeSerializer : SerializedEnumSerializer<RelationshipType>(RelationshipType::class)
object OaStatusSerializer : SerializedEnumSerializer<OaStatus>(OaStatus::class)
object SourceTypeSerializer : SerializedEnumSerializer<SourceType>(SourceType::class)
object EntityTypeSerializer : SerializedEnumSerializer<EntityType>(EntityType::class)
