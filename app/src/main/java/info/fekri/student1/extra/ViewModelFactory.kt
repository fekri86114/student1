package info.fekri.student1.extra

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import info.fekri.student1.addStudent.AddStudentViewModel
import info.fekri.student1.mainScreen.MainScreenViewModel
import info.fekri.student1.model.MainRepository

class MainViewModelFactory(private val mainRepository: MainRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        MainScreenViewModel(mainRepository) as T

}

class AddStudentViewModelFactory(private val mainRepository: MainRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        AddStudentViewModel(mainRepository) as T

}
