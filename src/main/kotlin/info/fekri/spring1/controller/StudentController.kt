package info.fekri.spring1.controller

import com.google.gson.Gson
import info.fekri.spring1.model.Student
import info.fekri.spring1.repository.StudentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class StudentController {
    private lateinit var studentRepository: StudentRepository

    @Autowired
    fun setStudentRepository(studentRepo: StudentRepository) {
        studentRepository = studentRepo
    }

    @GetMapping("/student")
    fun getAllStudents(): ResponseEntity<Iterable<Student>> {
        val dataFromDatabase = studentRepository.findAll() // returns the all the students
        return ResponseEntity.ok(dataFromDatabase)
    }

    @PostMapping("/student")
    fun insertStudent(
        @RequestBody data: String
    ): ResponseEntity<String> {
        val gson = Gson()
        val newStudent = gson.fromJson<Student>(data, Student::class.java)

        studentRepository.save(newStudent) // save student

        return ResponseEntity.ok("Success")
    }

    @PutMapping("/student/updating{name}")
    fun updateStudent(
        @PathVariable("name") name: String,
        @RequestBody data: String
    ): ResponseEntity<String> {
        val gson = Gson()
        val newStudent: Student = gson.fromJson(data, Student::class.java)

        studentRepository.save(newStudent)

        println(name)
        return ResponseEntity.ok("Success")
    }

}