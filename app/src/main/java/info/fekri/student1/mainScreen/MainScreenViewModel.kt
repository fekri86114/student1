package info.fekri.student1.mainScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import info.fekri.student1.model.MainRepository
import info.fekri.student1.model.local.student.Student
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit

class MainScreenViewModel(
    private val mainRepository: MainRepository
) : ViewModel() {
    private lateinit var netDisposable: Disposable
    private val errorData = MutableLiveData<String>()

    init {

        mainRepository.refreshData()
            .subscribeOn(Schedulers.io())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    netDisposable = d
                }

                override fun onComplete() {}
                override fun onError(e: Throwable) {
                    errorData.postValue(e.message ?: "Unknown Error!")
                }
            })

    }

    fun getAllData(): LiveData<List<Student>> = mainRepository.getAllStudent()
    fun getErrorData(): LiveData<String> = errorData
    fun removeStudent(studentName: String): Completable =
        mainRepository.removeStudent(studentName)

    override fun onCleared() {
        netDisposable.dispose()
        super.onCleared()
    }
}