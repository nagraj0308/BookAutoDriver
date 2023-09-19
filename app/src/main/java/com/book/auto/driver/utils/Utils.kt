package com.book.auto.driver.utils


import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage


class FBS {
    companion object {
        fun getReference(gId: String): StorageReference {
            val storageRef = Firebase.storage.reference;
            return storageRef.child("BookAuto/$gId")
        }
    }
}