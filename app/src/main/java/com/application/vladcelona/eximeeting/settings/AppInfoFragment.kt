package com.application.vladcelona.eximeeting.settings

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.application.vladcelona.eximeeting.R
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

private const val TAG = "AppInfoFragment"

class AppInfoFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var appQRCode: ImageView
    private val link: String = "https://github.com/vladcelona/Eximeeting_Samsung/releases/" +
            "download/Test_Release/app-debug.apk"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_app_info, container, false)

        appQRCode = view.findViewById(R.id.app_qr_code)
        setQRCode()

        return view
    }

    private fun setQRCode() {
        val writer = QRCodeWriter()

        try {

            val bitMatrix: BitMatrix = writer.encode(link, BarcodeFormat.QR_CODE, 512, 512)
            val width: Int = bitMatrix.width
            val height: Int = bitMatrix.height
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)

            for (x in 0 until width) {
                for (y in 0 until height) {
                    bitmap.setPixel(x, y, if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE)
                }
            }

            appQRCode.setImageBitmap(bitmap)
        } catch (e: WriterException) {
            Toast.makeText(context, "Failed to generate QR-code", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(): AppInfoFragment {
            return AppInfoFragment()
        }
    }
}