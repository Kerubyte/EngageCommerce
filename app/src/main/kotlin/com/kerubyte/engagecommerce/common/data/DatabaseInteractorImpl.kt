package com.kerubyte.engagecommerce.common.data

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.kerubyte.engagecommerce.common.domain.DatabaseInteractor
import com.kerubyte.engagecommerce.common.util.Constants.DOCUMENT_FIELD_ADDRESS
import com.kerubyte.engagecommerce.common.util.Constants.DOCUMENT_FIELD_CART
import com.kerubyte.engagecommerce.common.util.Constants.DOCUMENT_FIELD_ID
import javax.inject.Inject

class DatabaseInteractorImpl
@Inject
constructor(
    private val firestore: FirebaseFirestore
) : DatabaseInteractor {

    override suspend fun getWholeCollection(collection: String): Task<QuerySnapshot> {
        return firestore.collection(collection).get()
    }

    override suspend fun getSingleDocument(
        collection: String,
        documentUid: String
    ): Task<DocumentSnapshot> {
        return firestore.collection(collection).document(documentUid).get()
    }

    override suspend fun getItemsFromCollection(
        collection: String,
        itemsUid: List<String>
    ): Task<QuerySnapshot> {
        return firestore.collection(collection)
            .whereIn(DOCUMENT_FIELD_ID, itemsUid)
            .get()
    }

    override suspend fun setDocumentInCollection(
        collection: String,
        documentUid: String,
        data: Any
    ): Task<Void> {
        return firestore.collection(collection)
            .document(documentUid)
            .set(data)
    }

    override suspend fun addToFieldInDocument(
        collection: String,
        documentUid: String,
        itemUid: String
    ): Task<Void> {
        return firestore.collection(collection)
            .document(documentUid)
            .update(DOCUMENT_FIELD_CART, FieldValue.arrayUnion(itemUid))
    }

    override suspend fun removeFromFieldInDocument(
        collection: String,
        documentUid: String,
        itemUid: String
    ): Task<Void> {
        return firestore.collection(collection)
            .document(documentUid)
            .update(DOCUMENT_FIELD_CART, FieldValue.arrayRemove(itemUid))
    }

    override suspend fun deleteFieldInDocument(
        collection: String,
        documentUid: String
    ): Task<Void> {
        return firestore.collection(collection)
            .document(documentUid)
            .update(mapOf(DOCUMENT_FIELD_CART to FieldValue.delete()))
    }

    override suspend fun updateDocument(
        collection: String,
        documentUid: String,
        data: Map<String, String>
    ): Task<Void> {
        return firestore.collection(collection)
            .document(documentUid)
            .update(DOCUMENT_FIELD_ADDRESS, data)
    }

    override suspend fun createDocumentInCollection(
        collection: String,
        documentUid: String,
        data: Map<String, Any>,
        customUid: String
    ): Task<Void> {
        return firestore.collection(collection)
            .document(documentUid)
            .collection(collection)
            .document(customUid)
            .set(data)
    }
}