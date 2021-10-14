package com.kerubyte.engagecommerce.data.database

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot

interface DatabaseInteractor {

    suspend fun getWholeCollection(collection: String): Task<QuerySnapshot>

    suspend fun getSingleDocument(
        collection: String,
        documentUid: String
    ): Task<DocumentSnapshot>

    suspend fun getItemsFromCollection(
        collection: String,
        itemsUid: List<String>
    ): Task<QuerySnapshot>

    suspend fun setDocumentInCollection(
        collection: String,
        documentUid: String,
        data: Any
    ): Task<Void>

    suspend fun addToFieldInDocument(
        collection: String,
        documentUid: String,
        itemUid: String
    ): Task<Void>

    suspend fun removeFromFieldInDocument(
        collection: String,
        documentUid: String,
        itemUid: String
    ): Task<Void>

    suspend fun deleteFieldInDocument(
        collection: String,
        documentUid: String
    ): Task<Void>

    suspend fun updateDocument(
        collection: String,
        documentUid: String,
        data: Map<String, String>
    ): Task<Void>

    suspend fun createDocumentInCollection(
        collection: String,
        documentUid: String,
        data: Map<String, Any>,
        customUid: String
    ): Task<Void>
}