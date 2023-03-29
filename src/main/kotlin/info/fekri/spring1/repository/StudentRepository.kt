package info.fekri.spring1.repository

import info.fekri.spring1.model.Student
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface StudentRepository : CrudRepository<Student, String> {



}