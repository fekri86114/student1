package info.fekri.student1.mainScreen

import info.fekri.student1.model.MainRepository
import info.fekri.student1.model.Student
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit

class MainScreenViewModel(
    private val mainRepository: MainRepository
) {
    val progressSubject = BehaviorSubject.create<Boolean>()

    fun getAllStudents(): Single<List<Student>> {
        progressSubject.onNext(true)

        return mainRepository
            .getAllStudents()
            .delay(2, TimeUnit.SECONDS)
            .doFinally {
                progressSubject.onNext(false)
            }
    }

    fun removeStudent(studentName: String): Completable {
        return mainRepository.removeStudent(studentName)
    }
}