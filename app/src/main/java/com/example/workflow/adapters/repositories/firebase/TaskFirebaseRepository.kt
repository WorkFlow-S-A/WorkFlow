package com.example.workflow.adapters.repositories.firebase

import android.util.Log
import com.example.workflow.adapters.dtos.TaskDTO
import com.example.workflow.domain.entities.Task
import com.example.workflow.ports.repository.TaskRemoteRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.util.UUID

class TaskFirebaseRepository: TaskRemoteRepository {

    private val db = FirebaseFirestore.getInstance().collection("Company")

    override fun getTaskByIdStream(id: UUID): Flow<Task?>  = flow{
        var task : Task?  = null
        db.document(CompanyFirebaseRepository.getCurrentCompanyId()).collection("Tasks")
            .document(id.toString()).get()
            .addOnSuccessListener {
                if(it.exists()){
                    task = TaskDTO.toTask(it.toObject(TaskDTO::class.java)!!)
                }else{
                    Log.w("Firestore Operation","Task not found")
                }
            }.addOnFailureListener {
                Log.w("Firestore Operation", it)
            }.await()
        emit(task)
    }

    override fun getAllTasksStream(): Flow<List<Task>> = flow{
        var tasks : List<Task> = emptyList()
        db.document(CompanyFirebaseRepository.getCurrentCompanyId()).collection("Tasks")
            .get().addOnSuccessListener {
                val taskDTOList = it.toObjects(TaskDTO::class.java)
                tasks = taskDTOList.map { taskDTO ->
                    TaskDTO.toTask(taskDTO)
                }
            }.await()
        emit(tasks)
    }

    override suspend fun saveAll(tasks: List<Task>) {
        val taskCollection = db.document(CompanyFirebaseRepository.getCurrentCompanyId()).collection("Tasks")
        tasks.forEach{
            taskCollection.document(it.id.toString()).set(TaskDTO.fromTask(it))
        }
    }

    override suspend fun insertTask(task: Task) {
        db.document(CompanyFirebaseRepository.getCurrentCompanyId()).collection("Tasks")
            .document(task.id.toString()).set(TaskDTO.fromTask(task))
            .addOnSuccessListener {
                Log.d("Firebase Operation", "Task added success")
            }
            .addOnFailureListener {
                Log.w("Firebase Operation", it.message?: "Task did not add")
            }
    }

    override suspend fun deleteTask(task: Task) {
        db.document(CompanyFirebaseRepository.getCurrentCompanyId()).collection("Tasks")
            .document(task.id.toString()).delete()

    }

    override suspend fun updateTask(task: Task) {
        TODO("Not yet implemented")
    }
}