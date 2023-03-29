package info.fekri.student1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.gson.Gson
import info.fekri.student1.databinding.ActivityMainBinding
import info.fekri.student1.extra.BASE_URL
import info.fekri.student1.net.ApiManager
import info.fekri.student1.net.ApiService
import info.fekri.student1.recycler.Student
import info.fekri.student1.recycler.StudentAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() , StudentAdapter.StudentEvent{
    private lateinit var binding: ActivityMainBinding
    lateinit var myAdapter: StudentAdapter
    lateinit var apiService: ApiService
    private val apiManager = ApiManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarMain)

        val retrofit = Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        apiService = retrofit.create(ApiService::class.java)

        binding.btnAddStudent.setOnClickListener {
            startActivity(Intent(this, MainActivity2::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        getDataFromApi()
    }

    private fun getDataFromApi() {

        apiManager.getAllStudents(object : ApiManager.ApiCallback<List<Student>>{
            override fun onSuccess(data: List<Student>) {
                setDataRecycler(data)
            }

            override fun onError(msg: String) {
                Log.v("getDataForRecyclerMainLog", msg)
            }
        })

    }

    fun setDataRecycler(data: List<Student>) {
        val myData = ArrayList(data)
        myAdapter = StudentAdapter(myData, this)
        binding.recyclerMain.adapter = myAdapter
        binding.recyclerMain.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

    }

    override fun onItemClicked(student: Student, position: Int) {
        updateDataInServer(student, position)
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

    private fun updateDataInServer(student: Student, position: Int) {



    }

    private fun deleteDataFromServer(student: Student, position: Int) {

    }

}