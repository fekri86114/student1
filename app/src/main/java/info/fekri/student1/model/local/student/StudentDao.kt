package info.fekri.student1.model.local.student

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface StudentDao {

    @Query("SELECT * FROM student")
    fun getAllData(): LiveData<List<Student>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(student: Student)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(students: List<Student>)

    @Query("DELETE FROM student WHERE name = :studentName")
    fun remove(studentName: String)

}