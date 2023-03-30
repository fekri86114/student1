package info.fekri.student1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import com.google.gson.JsonObject
import info.fekri.student1.databinding.ActivityMain2Binding
import info.fekri.student1.net.ApiManager
import info.fekri.student1.net.ApiService
import info.fekri.student1.recycler.Student

class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding
    private val apiManager = ApiManager()
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

        val testMode = intent.getParcelableExtra<Student>("student")
        isInserting = (testMode == null)

        if (!isInserting) {

            val dataFromIntent = intent.getParcelableExtra<Student>("student")!!
            binding.edtCourse.setText(dataFromIntent.course)
            binding.edtScore.setText(dataFromIntent.score.toString())

            val splittedName = dataFromIntent.name.split(" ")
            binding.edtFirstName.setText(splittedName[0])
            binding.edtLastName.setText(splittedName[splittedName.size - 1])
        }

        binding.btnDone.setOnClickListener {

            if (isInserting) {
                addNewStudent()
            } else {
                updateStudent()
            }

        }

    }

    private fun updateStudent() {
        binding.btnDone.text = "Update"

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
            val jsonObject = JsonObject()
            jsonObject.addProperty("name", "$firstname $lastname")
            jsonObject.addProperty("course", course)
            jsonObject.addProperty("score", score.toInt())

            apiManager.updateStudent(
                "$firstname $lastname",
                jsonObject,
                object : ApiManager.ApiCallback<String> {
                    override fun onSuccess(data: String) { /*do nothing(yet)*/ }
                    override fun onError(msg: String) { /*do nothing(yet)*/ }
                })

            Toast.makeText(this, "Update finished!", Toast.LENGTH_SHORT).show()
            onBackPressed()
            
        } else {
            Toast.makeText(this, "Please, fill out the blanks!", Toast.LENGTH_SHORT).show()
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
            val jsonObject = JsonObject()
            jsonObject.addProperty("name", "$firstname $lastname")
            jsonObject.addProperty("course", course)
            jsonObject.addProperty("score", score.toInt())

            apiManager.insertStudent(jsonObject,
                object : ApiManager.ApiCallback<String> {
                    override fun onSuccess(data: String) { /* do nothing (yet) */
                    }

                    override fun onError(msg: String) {
                        Log.v("insertStudentMain2Log", msg)
                    }
                })

            // back to MainActivity (main)
            Toast.makeText(this, "Insert finished!", Toast.LENGTH_SHORT).show()
            onBackPressed()

        } else {
            Toast
                .makeText(
                    this,
                    "Please, fill out the blanks!",
                    Toast.LENGTH_SHORT
                ).show()
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