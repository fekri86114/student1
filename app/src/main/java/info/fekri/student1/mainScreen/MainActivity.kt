package info.fekri.student1.mainScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.pedant.SweetAlert.SweetAlertDialog
import info.fekri.student1.addStudent.AddStudentActivity
import info.fekri.student1.databinding.ActivityMainBinding
import info.fekri.student1.extra.asyncRequest
import info.fekri.student1.extra.showToast
import info.fekri.student1.model.MainRepository
import info.fekri.student1.model.local.Student
import io.reactivex.CompletableObserver
import io.reactivex.SingleObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class MainActivity : AppCompatActivity(), StudentAdapter.StudentEvent {
    private lateinit var binding: ActivityMainBinding
    private lateinit var myAdapter: StudentAdapter
    private val compositeDisposable = CompositeDisposable()
    private lateinit var mainViewModel: MainScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarMain)
        mainViewModel = MainScreenViewModel(MainRepository())

        binding.btnAddStudent.setOnClickListener {
            startActivity(Intent(this, AddStudentActivity::class.java))
        }

        compositeDisposable.add(
            mainViewModel.progressSubject.subscribe {
                if (it == true) {
                    runOnUiThread {
                        binding.progressMain.visibility = View.VISIBLE
                        binding.recyclerMain.visibility = View.INVISIBLE
                    }
                } else {
                    runOnUiThread {
                        binding.progressMain.visibility = View.INVISIBLE
                        binding.recyclerMain.visibility = View.VISIBLE
                    }
                }
            }
        )

    }

    override fun onResume() {
        super.onResume()
        mainViewModel
            .getAllStudents()
            .asyncRequest()
            .subscribe(object : SingleObserver<List<Student>> {
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onError(e: Throwable) {
                    showToast("Error: " + e.message ?: "null")
                }

                override fun onSuccess(t: List<Student>) {
                    setDataRecycler(t)
                }
            })
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

    override fun onItemClicked(student: Student, position: Int) {
        val intent = Intent(this, AddStudentActivity::class.java)
        intent.putExtra("student", student)
        startActivity(intent)
    }

    override fun onItemLongClicked(student: Student, position: Int) {
        val dialog = SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
        dialog.contentText = "Delete this Item?"
        dialog.cancelText = "Cancel"
        dialog.confirmText = "Confirm"

        dialog.setOnCancelListener { dialog.cancel() }
        dialog.setConfirmClickListener {
            deleteDataFromServer(student, position)
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun deleteDataFromServer(student: Student, position: Int) {
        // remove student from server
        mainViewModel
            .removeStudent(student.name)
            .asyncRequest()
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onComplete() {
                    showToast("Student removed!")
                }

                override fun onError(e: Throwable) {
                    showToast("Error: " + e.message ?: "null")
                }
            })
        // remove student from adapter
        myAdapter.removeItem(student, position)
    }

    private fun setDataRecycler(data: List<Student>) {
        val myData = ArrayList(data)
        myAdapter = StudentAdapter(myData, this)
        binding.recyclerMain.adapter = myAdapter
        binding.recyclerMain.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }

}