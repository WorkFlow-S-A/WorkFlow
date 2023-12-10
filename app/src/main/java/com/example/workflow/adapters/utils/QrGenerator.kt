package com.example.workflow.adapters.utils

import android.graphics.Bitmap
import android.net.Uri
import com.example.workflow.adapters.repositories.firebase.CompanyFirebaseRepository
import com.google.firebase.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream

class QrGenerator {
    companion object{
        private val db = Firebase.storage

        private fun generateRandom6DigitNumber(): String {
            val randomNumber = (100000..999999).random()
            return randomNumber.toString()
        }

        private fun generateQrCode(data: String): Bitmap {
            val multiFormatWriter = MultiFormatWriter()
            val bitMatrix: BitMatrix = multiFormatWriter.encode(data, BarcodeFormat.QR_CODE, 300, 300)
            val barcodeEncoder = BarcodeEncoder()
            return barcodeEncoder.createBitmap(bitMatrix)
        }

        suspend fun getQr() : String?{
            var downloadUrl : String? = null
            var generated = false
            val code = generateRandom6DigitNumber()
            CompanyFirebaseRepository.updateQr(code)
            val qrCode = generateQrCode(code)

            val baos = ByteArrayOutputStream()
            qrCode.compress(Bitmap.CompressFormat.PNG,100, baos)
            val data = baos.toByteArray()
            db.reference.child("QRCodes/").child("qrCode.png").putBytes(data)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        generated = true
                    } else {
                        throw Error(task.exception)
                    }
                }.await()
            if(generated){
                downloadUrl = db.reference.child("QRCodes/").child("qrCode.png").downloadUrl.await().toString()
            }
            return downloadUrl

        }
    }



}