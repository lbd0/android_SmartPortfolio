package kr.ac.hallym.portfolio

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.contentValuesOf
import kr.ac.hallym.portfolio.databinding.FragmentIntroOneBinding
import java.io.ByteArrayOutputStream
import java.util.prefs.Preferences

// 인트로 쓰기모드 프래그먼트
class IntroOneFragment : Fragment() {
    var sImg: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_intro_one, container, false)
        val binding = FragmentIntroOneBinding.inflate(inflater, container, false)

        var prefs = context?.getSharedPreferences("name", 0)

        if(prefs?.getString("name", "") == "") {
            binding.introEdit.setText(R.string.name)
        }
        binding.introEdit.setText(prefs?.getString("name", ""))


        binding.introSavebtn.setOnClickListener {
            prefs = context?.getSharedPreferences("name", 0)
            if(binding.introEdit.text.toString() != "") {
                prefs?.edit()?.putString("name", binding.introEdit.text.toString())?.apply()
                Toast.makeText(activity, R.string.saved, Toast.LENGTH_SHORT).show()
            }
            val prefs = context?.getSharedPreferences("img", 0)
            if(sImg != null)
                prefs?.edit()?.putString("img", sImg)?.apply()
            Toast.makeText(activity, R.string.saved, Toast.LENGTH_SHORT).show()

        }

        prefs = context?.getSharedPreferences("img", 0)
        if(prefs?.getString("img", "") == "") {
            binding.introUserImageView.setImageResource(R.drawable.user_basic)
        } else {
            val sImg = prefs?.getString("img", "")
            val encodeByte = Base64.decode(sImg, Base64.DEFAULT)
            val img = BitmapFactory.decodeByteArray(encodeByte, 0 , encodeByte.size)

            binding.introUserImageView.setImageBitmap(img)
        }

        // 갤러리 연동
        val requestGalleryLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) {
            try {
                val calRatio = calculateInSampleSize(
                    it.data!!.data!!,
                    resources.getDimensionPixelSize(R.dimen.imgSize),
                    resources.getDimensionPixelSize(R.dimen.imgSize)
                )
                val option = BitmapFactory.Options()
                option.inSampleSize = calRatio

                var inputStream = activity?.contentResolver?.openInputStream(it.data!!.data!!)
                var bitmap = BitmapFactory.decodeStream(inputStream, null, option)
                inputStream!!.close()
                inputStream = null
                bitmap?.let{
                    binding.introUserImageView.setImageBitmap(bitmap)
                    val baos= ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.PNG, 70, baos)
                    val byte = baos.toByteArray()
                    sImg = Base64.encodeToString(byte, Base64.DEFAULT)

                }?: let {
                    Log.d("bada", "bitmap null")
                }
            } catch (e : Exception) {
                Log.d("bada", "bitmap null")
            }
        }

        binding.introUserImageView.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            requestGalleryLauncher.launch(intent)
        }



        return binding.root
    }

    private fun calculateInSampleSize(fileUri: Uri, reqWidth: Int, reqHeight: Int): Int {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        try {
            var inputStream = activity?.contentResolver?.openInputStream(fileUri)
            BitmapFactory.decodeStream(inputStream, null, options)
            inputStream!!.close()
            inputStream = null
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val (height: Int, width: Int) = options.run { outHeight to outWidth }
        var inSampleSize = 1
        if(height > reqHeight || width > reqWidth) {
            val halfHeight: Int = height / 2
            val halfWidth:  Int = width / 2
            while(halfHeight / inSampleSize >= reqHeight &&
                    halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }
}