package info.fekri.student1.model

import androidx.lifecycle.LiveData
import info.fekri.student1.extra.BASE_URL
import info.fekri.student1.extra.studentToJsonObject
import info.fekri.student1.model.local.student.Student
import info.fekri.student1.model.api.ApiService
import info.fekri.student1.model.local.student.StudentDao
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainRepository(
    private val apiService: ApiService,
    private val studentDao: StudentDao
) {

    fun getAllStudent(): LiveData<List<Student>> = studentDao.getAllData()

    // caching data
    fun refreshData(): Completable {
        return apiService.getAllStudents()
            .doOnSuccess {
                studentDao.insertAll(it)
            }.ignoreElement()
    }

    fun insertStudent(student: Student): Completable =
        apiService.insertStudent(studentToJsonObject(student))
            .doOnComplete { studentDao.insertOrUpdate(student) }

    fun updateStudent(student: Student): Completable =
        apiService.updateStudent(student.name, studentToJsonObject(student))
            .doOnComplete { studentDao.insertOrUpdate(student) }

    fun removeStudent(studentName: String): Completable =
        apiService.deleteStudent(studentName)
            .doOnComplete { studentDao.remove(studentName) }

}