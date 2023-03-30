package info.fekri.student1.mainScreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import info.fekri.student1.databinding.ItemMainBinding
import info.fekri.student1.model.Student

class StudentAdapter(private val data: ArrayList<Student>, private val studentEvent: StudentEvent) :
    RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {
    private lateinit var binding: ItemMainBinding

    inner class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(student: Student) {
            binding.txtName.text = student.name
            binding.txtCourse.text = student.course
            binding.txtScore.text = student.score.toString()
            binding.txtHarfAval.text = student.name[0].uppercaseChar().toString()

            itemView.setOnClickListener {
                studentEvent.onItemClicked(student, adapterPosition)
            }
            itemView.setOnLongClickListener {
                studentEvent.onItemLongClicked(student, adapterPosition)
                true
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemMainBinding.inflate(inflater, parent, false)
        return StudentViewHolder(binding.root)
    }
    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.bind(data[position])
    }
    override fun getItemCount(): Int = data.size

    fun removeItem(student: Student, position: Int) {
        data.remove(student)
        notifyItemRemoved(position)
    }

    interface StudentEvent {
        fun onItemClicked(student: Student, position: Int)
        fun onItemLongClicked(student: Student, position: Int)
    }
}