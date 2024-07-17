package live.ixnoah.housetils.utils

import com.google.gson.*
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import kotlin.reflect.*
import kotlin.reflect.full.isSubtypeOf
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.javaType
import com.google.gson.internal.`$Gson$Types` as InternalGsonTypes

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class KSerializable


@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.PROPERTY)
annotation class ExtraData


class KotlinTypeAdapterFactory : TypeAdapterFactory {

    internal data class ParameterInfo(
        val param: KParameter,
        val adapter: TypeAdapter<Any?>,
        val name: String,
        val field: KProperty1<Any, Any?>
    )

    override fun <T : Any> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {
        val kotlinClass = type.rawType.kotlin as KClass<T>
        if (kotlinClass.findAnnotation<KSerializable>() == null) return null
        if (!kotlinClass.isData) return null
        val primaryConstructor = kotlinClass.primaryConstructor ?: return null
        val params = primaryConstructor.parameters.filter { it.findAnnotation<ExtraData>() == null }
        val extraDataParam = primaryConstructor.parameters
            .find { it.findAnnotation<ExtraData>() != null && typeOf<MutableMap<String, JsonElement>>().isSubtypeOf(it.type) }
            ?.let { param ->
                param to kotlinClass.memberProperties.find { it.name == param.name && it.returnType.isSubtypeOf(typeOf<Map<String, JsonElement>>()) } as KProperty1<Any, Map<String, JsonElement>>
            }
        val parameterInfos = params.map { param ->
            ParameterInfo(
                param,
                gson.getAdapter(
                    TypeToken.get(InternalGsonTypes.resolve(type.type, type.rawType, param.type.javaType))
                ) as TypeAdapter<Any?>,
                param.findAnnotation<SerializedName>()?.value ?: param.name!!,
                kotlinClass.memberProperties.find { it.name == param.name }!! as KProperty1<Any, Any?>
            )
        }.associateBy { it.name }
        val jsonElementAdapter = gson.getAdapter(JsonElement::class.java)

        return object : TypeAdapter<T>() {
            override fun write(out: JsonWriter, value: T?) {
                if (value == null) {
                    out.nullValue()
                    return
                }
                out.beginObject()
                parameterInfos.forEach { (name, paramInfo) ->
                    out.name(name)
                    paramInfo.adapter.write(out, paramInfo.field.get(value))
                }
                if (extraDataParam != null) {
                    val extraData = extraDataParam.second.get(value)
                    extraData.forEach { (extraName, extraValue) ->
                        out.name(extraName)
                        jsonElementAdapter.write(out, extraValue)
                    }
                }
                out.endObject()
            }

            override fun read(reader: JsonReader): T? {
                if (reader.peek() == JsonToken.NULL) {
                    reader.nextNull()
                    return null
                }
                reader.beginObject()
                val args = mutableMapOf<KParameter, Any?>()
                val extraData = mutableMapOf<String, JsonElement>()
                while (reader.peek() != JsonToken.END_OBJECT) {
                    val name = reader.nextName()
                    val paramData = parameterInfos[name]
                    if (paramData == null) {
                        extraData[name] = jsonElementAdapter.read(reader)
                        continue
                    }
                    val value = paramData.adapter.read(reader)
                    args[paramData.param] = value
                }
                reader.endObject()
                if (extraDataParam == null) {
                    if (extraData.isNotEmpty()) {
                        throw JsonParseException("Encountered unknown keys ${extraData.keys} while parsing $type")
                    }
                } else {
                    args[extraDataParam.first] = extraData
                }
                return primaryConstructor.callBy(args)
            }
        }
    }
}

inline fun <reified T> KAnnotatedElement.findAnnotation(): T? {
    return this.annotations.filterIsInstance<T>().firstOrNull()
}