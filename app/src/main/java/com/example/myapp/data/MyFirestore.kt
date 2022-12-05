package com.example.myapp.data

import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class MyFirestore {
    private val tag = "APPLE/ Firestore"
    private val db: FirebaseFirestore = Firebase.firestore

    suspend fun create(dto: Dto) {
        db.collection(dto.collection)
            .document(dto.documentId)
            .set(dto.data).await()
    }

    suspend fun add(dto: Dto) {
        db.collection(dto.collection)
            .add(dto.data).await()
    }

    suspend fun get(collection: String, documentId: String): DocumentSnapshot {
        return suspendCoroutine { continuation ->
            db.collection(collection).document(documentId).get()
                .addOnSuccessListener {
                    continuation.resume(it)
                }
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }
    }

    suspend fun get(collection: String, condition: Pair<String, Any>): ArrayList<QueryDocumentSnapshot>{
        val rtn = arrayListOf<QueryDocumentSnapshot>()
        return suspendCoroutine { continuation ->
            db.collection(collection)
                .whereEqualTo(condition.first, condition.second)
                .get()
                .addOnSuccessListener { documents ->
                    for(doc in documents) {
                        rtn.add(doc)
                    }
                    continuation.resume(rtn)
                }
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }
    }

    suspend fun chkUnique(collection: String, condition: Pair<String, Any>):Boolean? {
        var rtn = false
        return suspendCoroutine { continuation ->
            db.collection(collection)
                .whereEqualTo(condition.first, condition.second)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    rtn = querySnapshot.documents.isEmpty()
                    continuation.resume(rtn)
                }
                .addOnFailureListener {
                    continuation.resume(null)
                }
        }
    }

    fun listen(collection: String): ListenerRegistration {
        return db.collection(collection)
            .addSnapshotListener { documents, e ->
                if(e != null) return@addSnapshotListener
                documents?.documentChanges
            }
    }

    fun listen(collection: String, order: String): ListenerRegistration {
        return db.collection(collection)
            .orderBy(order)
            .addSnapshotListener { documents, e ->
                if(e != null) return@addSnapshotListener
                documents?.documentChanges
            }
    }

    suspend fun update(collection: String, documentId: String, key: String, value: String): Void? {
        return db.collection(collection)
            .document(documentId)
            .update(key, value)
            .await()
    }

    suspend fun delete(collection: String, documentId: String): Void {
        return db.collection(collection)
            .document(documentId)
            .delete()
            .await()
    }
}