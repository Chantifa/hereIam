package ch.ffhs.esa.hereiam.screens.profile

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ch.ffhs.esa.hereiam.HereIAmApplication
import ch.ffhs.esa.hereiam.services.StorageService
import ch.ffhs.esa.hereiam.services.StorageServiceFirebase

class ProfileViewModel : ViewModel() {

    private val userPicture = MutableLiveData<String>()
    private val storageService: StorageService = StorageServiceFirebase()
    private val folder = "profileImages"

    suspend fun uploadImage(bitmap: Bitmap) {
        val userId = HereIAmApplication.currentUser?.uid
        userPicture.value = storageService.uploadImage(bitmap, folder, userId ?: "")
    }
}