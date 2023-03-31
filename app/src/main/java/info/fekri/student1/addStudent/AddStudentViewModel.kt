package info.fekri.student1.addStudent

import info.fekri.student1.model.MainRepository
import info.fekri.student1.model.local.Student
import io.reactivex.Completable

class AddStudentViewModel(private val mainRepository: MainRepository) {

    fun insertNewStudent(student: Student): Completable {
        return mainRepository.insertStudent(student)
    }

    fun updateStudent(student: Student): Completable {
        return mainRepository.updateStudent(student)
    }

}