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

class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding
    private val apiManager = ApiManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarMain2)
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        // focus on firstname edt -->
        binding.edtFirstName.requestFocus()

        binding.btnDone.setOnClickListener { addNewStudent() }

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
                    override fun onSuccess(data: String) { /* do nothing (yet) */ }
                    override fun onError(msg: String) { Log.v("insertStudentMain2Log", msg) }
                })

            // back to MainActivity (main)
            onBackPressed()

        } else {
            Toast
                .makeText(
                    this,
                    "Please, fill out the blanks!",
                    Toast.LENGTH_SHORT
                ).show()
            binding.edtFirstName.error = "Name is empty!"
            binding.edtLastName.error = "Name is empty!"
            binding.edtCourse.error = "Name is empty!"
            binding.edtScore.error = "Name is empty!"
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) onBackPressed()
        return true
    }
}