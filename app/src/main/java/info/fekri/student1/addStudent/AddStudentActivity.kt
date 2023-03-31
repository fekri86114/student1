package info.fekri.student1.addStudent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import info.fekri.student1.databinding.ActivityMain2Binding
import info.fekri.student1.extra.asyncRequest
import info.fekri.student1.extra.showToast
import info.fekri.student1.model.MainRepository
import info.fekri.student1.model.local.Student
import io.reactivex.CompletableObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class AddStudentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding
    private lateinit var addStudentViewModel: AddStudentViewModel
    private val compositeDisposable = CompositeDisposable()
    private var isInserting = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarMain2)
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        // focus on firstname edt -->
        binding.edtFirstName.requestFocus()
        addStudentViewModel = AddStudentViewModel(MainRepository())

        val testMode = intent.getParcelableExtra<Student>("student")
        isInserting = (testMode == null)

        if (!isInserting) updateStudentLogic()

        binding.btnDone.setOnClickListener {
            if (isInserting) addNewStudent()
            else updateStudent()
        }

    }
    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

    private fun updateStudentLogic() {
        binding.btnDone.text = "Update"

        val dataFromIntent = intent.getParcelableExtra<Student>("student")!!
        binding.edtCourse.setText(dataFromIntent.course)
        binding.edtScore.setText(dataFromIntent.score.toString())

        val splittedName = dataFromIntent.name.split(" ")
        binding.edtFirstName.setText(splittedName[0])
        binding.edtLastName.setText(splittedName[splittedName.size - 1])
    }
    private fun updateStudent() {
        val firstname = binding.edtFirstName.text.toString()
        val lastname = binding.edtLastName.text.toString()
        val course = binding.edtCourse.text.toString()
        val score = binding.edtScore.text.toString()

        if (
            firstname.isNotEmpty() &&
            lastname.isNotEmpty() &&
            course.isNotEmpty() &&
            score.isNotEmpty()
        ) {
            addStudentViewModel.updateStudent(
                Student(
                    "$firstname $lastname",
                    course,
                    score.toInt()
                )
            )
                .asyncRequest()
                .subscribe(object : CompletableObserver {
                    override fun onSubscribe(d: Disposable) {
                        compositeDisposable.add(d)
                    }

                    override fun onComplete() {
                        showToast("Update Student is done!")
                        onBackPressed()
                    }

                    override fun onError(e: Throwable) {
                        showToast("Error: " + e.message ?: "null")
                    }
                })
        } else {
            showToast("Please, fill out the blanks!")
            binding.edtFirstName.error = "Name is empty!"
            binding.edtLastName.error = "Lastname is empty!"
            binding.edtCourse.error = "Course is empty!"
            binding.edtScore.error = "Score is empty!"
        }
    }
    private fun addNewStudent() {
        val firstname = binding.edtFirstName.text.toString()
        val lastname = binding.edtLastName.text.toString()
        val course = binding.edtCourse.text.toString()
        val score = binding.edtScore.text.toString()

        if (firstname.isNotEmpty() &&
            lastname.isNotEmpty() &&
            course.isNotEmpty() &&
            score.isNotEmpty()
        ) {
            // add/send student
            addStudentViewModel
                .insertNewStudent(
                    Student("$firstname $lastname", course, score.toInt())
                )
                .asyncRequest()
                .subscribe(object : CompletableObserver {
                    override fun onSubscribe(d: Disposable) {
                        compositeDisposable.add(d)
                    }

                    override fun onComplete() {
                        // back to MainActivity (main)
                        showToast("Student inserted!")
                        onBackPressed()
                    }

                    override fun onError(e: Throwable) {
                        showToast("Error: " + e.message ?: "null")
                    }
                })

        } else {
            showToast("Please, fill out the blanks!")
            binding.edtFirstName.error = "Name is empty!"
            binding.edtLastName.error = "Lastname is empty!"
            binding.edtCourse.error = "Course is empty!"
            binding.edtScore.error = "Score is empty!"
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) onBackPressed()
        return true
    }
}