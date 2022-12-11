package kr.ac.hallym.portfolio

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import kr.ac.hallym.portfolio.databinding.ActivityEditInfoBinding
import java.io.ByteArrayOutputStream

class EditInfoActivity : AppCompatActivity() {
    lateinit var binding : ActivityEditInfoBinding
    lateinit var prefs : SharedPreferences
    var sImg :String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_edit_info)
        binding = ActivityEditInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.editInfoToolbar)

        // 인트로 이미지 불러오기
        var prefs = getSharedPreferences("img", 0)
        if(prefs?.getString("img", "") == "") {
            binding.editUserImage.setImageResource(R.drawable.user_basic)
        } else {
            val sImg = prefs?.getString("img", "")
            val encodeByte = Base64.decode(sImg, Base64.DEFAULT)
            val img = BitmapFactory.decodeByteArray(encodeByte, 0 , encodeByte.size)

            binding.editUserImage.setImageBitmap(img)
        }

        // 갤러리 연동
        val requestGalleryLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) {
            try {
                val calRatio = calculateInSampleSize(
                    it.data!!.data!!,
                    resources.getDimensionPixelSize(R.dimen.imgSize),
                    resources.getDimensionPixelSize(R.dimen.pfimgsize)
                )
                val option = BitmapFactory.Options()
                option.inSampleSize = calRatio

                var inputStream = contentResolver?.openInputStream(it.data!!.data!!)
                var bitmap = BitmapFactory.decodeStream(inputStream, null, option)
                inputStream!!.close()
                inputStream = null
                bitmap?.let{
                    binding.editUserImage.setImageBitmap(bitmap)
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

        binding.editUserImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            requestGalleryLauncher.launch(intent)
        }

        prefs = getSharedPreferences("infoName", 0)
        if(prefs == null) binding.editInfoName.setText(R.string.name)
        else binding.editInfoName.setText(prefs?.getString("infoName", ""))

        prefs = getSharedPreferences("infoBirth", 0)
        if(prefs == null) binding.editInfoBirth.setText(R.string.birth)
        else binding.editInfoBirth.setText(prefs?.getString("infoBirth", ""))

        prefs = getSharedPreferences("infoMajor", 0)
        if(prefs == null) binding.editInfoMajor.setText(R.string.major)
        else binding.editInfoMajor.setText(prefs?.getString("infoMajor", ""))

        prefs = getSharedPreferences("infoPhone", 0)
        if(prefs == null) binding.editInfoPhone.setText(R.string.phone)
        else binding.editInfoPhone.setText(prefs?.getString("infoPhone", ""))

        prefs = getSharedPreferences("infoEmail", 0)
        if(prefs == null) binding.editInfoEmail.setText(R.string.email)
        else binding.editInfoEmail.setText(prefs?.getString("infoEmail", ""))

        prefs = getSharedPreferences("infoGit", 0)
        if(prefs == null) binding.editInfoGit.setText(R.string.git)
        else binding.editInfoGit.setText(prefs?.getString("infoGit", ""))

        prefs = getSharedPreferences("infoMore", 0)
        if(prefs == null) binding.editInfoMore.setText(R.string.more)
        else binding.editInfoMore.setText(prefs?.getString("infoMore", ""))
    }

    private fun calculateInSampleSize(fileUri: Uri, reqWidth: Int, reqHeight: Int): Int {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        try {
            var inputStream = contentResolver?.openInputStream(fileUri)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId) {
        R.id.menu_add_save -> {
            val inputName = binding.editInfoName.text.toString()
            val inputBirth = binding.editInfoBirth.text.toString()
            val inputMajor = binding.editInfoMajor.text.toString()
            val inputPhone = binding.editInfoPhone.text.toString()
            val inputEmail = binding.editInfoEmail.text.toString()
            val inputGit = binding.editInfoGit.text.toString()
            val inputMore = binding.editInfoMore.text.toString()

            prefs = getSharedPreferences("infoName",0)
            prefs?.edit()?.putString("infoName", inputName)?.apply()

            prefs = getSharedPreferences("infoBirth",0)
            prefs?.edit()?.putString("infoBirth", inputBirth)?.apply()

            prefs = getSharedPreferences("infoMajor",0)
            prefs?.edit()?.putString("infoMajor", inputMajor)?.apply()

            prefs = getSharedPreferences("infoPhone",0)
            prefs?.edit()?.putString("infoPhone", inputPhone)?.apply()

            prefs = getSharedPreferences("infoEmail",0)
            prefs?.edit()?.putString("infoEmail", inputEmail)?.apply()

            prefs = getSharedPreferences("infoGit",0)
            prefs?.edit()?.putString("infoGit", inputGit)?.apply()

            prefs = getSharedPreferences("infoMore",0)
            prefs?.edit()?.putString("infoMore", inputMore)?.apply()

            prefs = getSharedPreferences("img", 0)
            if(sImg != null)
                prefs?.edit()?.putString("img", sImg)?.apply()


            setResult(Activity.RESULT_OK, intent)

            finish()
            true
        }
        else -> true
    }
}