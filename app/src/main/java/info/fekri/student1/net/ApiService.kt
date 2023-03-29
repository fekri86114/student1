package info.fekri.student1.net

import com.google.gson.JsonObject
import info.fekri.student1.recycler.Student
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("/student")
    fun getAllStudents(): Call<List<Student>>

    @POST("/student")
    fun insertStudent(
        @Body body: JsonObject
    ): Call<String>

}