package info.fekri.student1.addStudent

import androidx.lifecycle.ViewModel
import info.fekri.student1.model.MainRepository
import info.fekri.student1.model.local.student.Student
import io.reactivex.Completable

class AddStudentViewModel(private val mainRepository: MainRepository) : ViewModel() {

    fun insertNewStudent(student: Student): Completable {
        return mainRepository.insertStudent(student)
    }

    fun updateStudent(student: Student): Completable {
        return mainRepository.updateStudent(student)
    }

}