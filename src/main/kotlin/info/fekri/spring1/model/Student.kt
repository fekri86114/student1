package info.fekri.spring1.model

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
data class Student(
    @Id
    var name: String,
    var course: String,
    var score: Int
) {
    constructor(): this("", "", -1)
}