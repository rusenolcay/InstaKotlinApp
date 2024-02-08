package com.rusen.instagramcloneapp.utils

import android.app.ProgressDialog
import android.content.Context
import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

fun uploadImage(uri: Uri, folderName: String, callback:(String?)-> Unit) {
    var imageUrl: String?
    FirebaseStorage.getInstance().getReference(folderName).child(UUID.randomUUID().toString())
        .putFile(uri)
        .addOnSuccessListener {
            it.storage.downloadUrl.addOnSuccessListener {
                imageUrl = it.toString()
                callback(imageUrl)
            }
        }
}

fun uploadVideo(uri: Uri, folderName: String,context: Context, progressDiolag: ProgressDialog, callback:(String?)-> Unit) {
    var videoUrl: String?
    progressDiolag.setTitle("Uploading . . .")
    progressDiolag.show()
    FirebaseStorage.getInstance().getReference(folderName).child(UUID.randomUUID().toString())
        .putFile(uri)
        .addOnSuccessListener {
            it.storage.downloadUrl.addOnSuccessListener {
                videoUrl = it.toString()
                progressDiolag.dismiss()
                callback(videoUrl)
            }
        }
        .addOnProgressListener {
            val uploadedValue: Long = it.bytesTransferred/it.totalByteCount
            progressDiolag.setMessage("Uploaded $uploadedValue % ")
        }
}