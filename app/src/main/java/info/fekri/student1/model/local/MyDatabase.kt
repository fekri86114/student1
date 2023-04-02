package info.fekri.student1.model.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import info.fekri.student1.model.local.student.Student
import info.fekri.student1.model.local.student.StudentDao

@Database(entities = [Student::class], version = 1, exportSchema = false)
abstract class MyDatabase : RoomDatabase() {
    abstract val studentDao: StudentDao

    companion object {
        @Volatile
        private var database: MyDatabase? = null

        fun getDatabase(context: Context): MyDatabase {
            synchronized(this) {
                if (database == null) {
                    database = Room.databaseBuilder(
                        context.applicationContext,
                        MyDatabase::class.java,
                        "myDatabase.db"
                    ).build()
                }
                return database!!
            }
        }
    }

}