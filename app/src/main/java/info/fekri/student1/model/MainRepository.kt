package info.fekri.student1.model

import info.fekri.student1.extra.BASE_URL
import info.fekri.student1.extra.studentToJsonObject
import info.fekri.student1.model.retrofit.ApiService
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainRepository {
    private val apiService: ApiService

    init {
        val retrofit = Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)
    }

    fun getAllStudents(): Single<List<Student>> = apiService.getAllStudents()

    fun insertStudent(student: Student): Completable =
        apiService.insertStudent(studentToJsonObject(student))

    fun updateStudent(student: Student): Completable =
        apiService.updateStudent(student.name, studentToJsonObject(student))

    fun removeStudent(studentName: String): Completable =
        apiService.deleteStudent(studentName)

}