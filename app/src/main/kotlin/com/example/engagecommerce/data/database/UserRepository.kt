package com.example.engagecommerce.data.database

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.engagecommerce.application.repo.UserDatabase
import com.example.engagecommerce.application.state.UserCartState
import com.example.engagecommerce.application.util.Constants.COLLECTION_USERS
import com.example.engagecommerce.data.database.mappers.NullableInputUserEntityMapper
import com.example.engagecommerce.data.database.mappers.NullableOutputUserEntityMapper
import com.example.engagecommerce.data.entity.UserEntity
import com.example.engagecommerce.domain.model.Product
import com.example.engagecommerce.domain.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.user.sdk.UserCom
import com.user.sdk.customer.Customer
import com.user.sdk.customer.CustomerUpdateCallback
import javax.inject.Inject

class UserRepository
@Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val userCom: UserCom,
    private val customerUpdateCallback: CustomerUpdateCallback,
    private val inputUserMapper: NullableInputUserEntityMapper,
    private val outputUserMapper: NullableOutputUserEntityMapper
) : UserDatabase {

    private val _navigate = MutableLiveData<Boolean>()
    val navigate: LiveData<Boolean>
        get() = _navigate

    private val _currentUser = MutableLiveData<User>()
    val currentUser: LiveData<User>
        get() = _currentUser

    private val _userCartState: MutableLiveData<UserCartState.UserCart> = MutableLiveData()
    val userCartState: LiveData<UserCartState.UserCart>
        get() = _userCartState

    override fun getCurrentUser(): DocumentReference? {
        if (auth.currentUser != null) {
            return firestore.collection(COLLECTION_USERS)
                .document(auth.currentUser!!.uid)
        }
        return null
    }

    override fun getUserData(): LiveData<User>? {
        val responseResult = MutableLiveData<User>()

        if (auth.currentUser != null) {
            val uid = auth.currentUser?.uid

            firestore.collection(COLLECTION_USERS)
                .document(uid!!)
                .get()
                .addOnSuccessListener {
                    val userEntity = it.toObject(UserEntity::class.java)
                    val user = inputUserMapper.mapFromEntity(userEntity!!)
                    responseResult.value = user
                    _currentUser.value = user
                }
            return responseResult
        }
        return null
    }

    override fun getUserCart() {

        if (auth.currentUser != null) {
            val uid = auth.currentUser?.uid

            firestore.collection(COLLECTION_USERS)
                .document(uid!!)
                .get()
                .addOnSuccessListener {
                    val userEntity = it.toObject(UserEntity::class.java)
                    val user = inputUserMapper.mapFromEntity(userEntity!!)
                    if (user.cart.isNullOrEmpty()) {
                        _userCartState.value = UserCartState.UserCart.Empty
                    } else {
                        _userCartState.value = UserCartState.UserCart.NotEmpty(user.cart)
                    }
                }
                .addOnFailureListener {
                    _userCartState.value = UserCartState.UserCart.Error
                }
        }
    }

    override fun addToCart(product: Product) {
        firestore.collection(COLLECTION_USERS)
            .document(auth.currentUser?.uid!!)
            .update("cart", FieldValue.arrayUnion(product))
            .addOnSuccessListener {
                Log.d("cart", "Added to Cart")
            }
            .addOnFailureListener {
                Log.d("cart", it.toString())
            }
    }

    override fun removeFromCart(product: Product) {
        firestore.collection(COLLECTION_USERS)
            .document(auth.currentUser?.uid!!)
            .update("cart", FieldValue.arrayRemove(product))
            .addOnSuccessListener {
                Log.d("cart", "Removed from Cart")
            }
            .addOnFailureListener {
                Log.d("cartError", it.localizedMessage)
            }
    }

    override fun clearUserCart() {
        firestore.collection(COLLECTION_USERS)
            .document(auth.currentUser?.uid!!)
            .update(
                mapOf(
                    "cart" to FieldValue.delete()
                )
            )
    }

    override fun createNewUser(user: User) {
        val userEntity = outputUserMapper.mapToEntity(user)

        firestore.collection(COLLECTION_USERS)
            .document(user.uid)
            .set(userEntity)
            .addOnSuccessListener {
                Log.d("createUser", "success")
            }
            .addOnFailureListener { e ->

                Log.d("createUser", e.toString())
            }
    }

    override fun createAccount(
        email: String,
        password: String,
        firstName: String,
        lastName: String
    ) {

        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                val user = User(
                    auth.currentUser!!.uid,
                    firstName = firstName,
                    lastName = lastName,
                    auth.currentUser?.email!!,
                    listOf()
                )
                val customer = Customer()
                    .id(auth.currentUser!!.uid)
                    .firstName(firstName)
                    .lastName(lastName)
                    .email(email)

                createNewUser(user)
                userCom
                    .register(customer, customerUpdateCallback)

                _navigate.value = true
            }
            .addOnFailureListener { exc ->
                Log.d("register", exc.toString())
            }
    }

    override fun loginUser(email: String, password: String) {

        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                val customer = Customer()
                    .id(auth.currentUser!!.uid)
                    .email(email)

                userCom
                    .register(customer, customerUpdateCallback)

                _navigate.value = true
            }
            .addOnFailureListener { exc ->
                Log.d("login", exc.toString())
            }
    }

    override fun signOut() {
        Firebase.auth.signOut()
        userCom.logout()
        _navigate.value = true
    }

    override fun onDoneNavigating() {
        _navigate.value = false
    }

    init {
        _navigate.value = false
    }
}
