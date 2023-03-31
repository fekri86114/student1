package info.fekri.student1.extra

import com.google.gson.JsonObject
import info.fekri.student1.model.local.Student

fun studentToJsonObject(student: Student): JsonObject {
    val jsonObject = JsonObject()
    jsonObject.addProperty("name", student.name)
    jsonObject.addProperty("course", student.course)
    jsonObject.addProperty("score", student.score)

    return jsonObject
}