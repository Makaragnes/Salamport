package com.example.salamport.ui.fragments

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.salamport.R
import com.example.salamport.database.REF_DATABASE_ROOT
import com.example.salamport.database.USER
import com.example.salamport.utilits.APP_ACTIVITY
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import kotlinx.android.synthetic.main.fragment_q_r.*
import kotlinx.android.synthetic.main.fragment_settings.*


class QRFragment : BaseFragment(R.layout.fragment_q_r) {

    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.title = "Ваш QR-id"

        val imageView = image_qr
        val content = USER.username
        val writer = QRCodeWriter()
        val bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, 512, 512)
        val width = bitMatrix.width
        val height = bitMatrix.height
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        for (x in 0 until width) {
            for (y in 0 until height) {
                bitmap.setPixel(x, y, if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE)
            }
        }
        imageView.setImageBitmap(bitmap)
    }
}