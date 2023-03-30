package info.fekri.student1.net

import com.google.gson.JsonObject
import info.fekri.student1.extra.BASE_URL
import info.fekri.student1.recycler.Student
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiManager {
    private val apiService: ApiService

    init {
        val retrofit = Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)
    }

    fun getAllStudents(apiCallback: ApiCallback<List<Student>>) {

        apiService.getAllStudents().enqueue(object : Callback<List<Student>>{
            override fun onResponse(call: Call<List<Student>>, response: Response<List<Student>>) {
                apiCallback.onSuccess(response.body()!!)
            }

            override fun onFailure(call: Call<List<Student>>, t: Throwable) {
                apiCallback.onError(t.message!!)
            }
        })

    }

    fun insertStudent(body: JsonObject, apiCallback: ApiCallback<String>) {
        apiService.insertStudent(body).enqueue(object: Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                apiCallback.onSuccess(response.body()!!)
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                apiCallback.onError(t.message!!)
            }
        })
    }

    fun updateStudent(name: String, body: JsonObject, apiCallback: ApiCallback<String>) {
        apiService.updateStudent(name, body).enqueue(object: Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                apiCallback.onSuccess(response.body()!!)
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                apiCallback.onError(t.message!!)
            }
        })
    }

    interface ApiCallback<T> {
        fun onSuccess(data: T)
        fun onError(msg: String)
    }
}