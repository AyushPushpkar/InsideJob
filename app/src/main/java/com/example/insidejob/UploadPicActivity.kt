package com.example.insidejob

import android.app.Activity
import android.content.ContentResolver
import android.content.ContentResolver.MimeTypeInfo
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.insidejob.databinding.ActivityUploadPicBinding
import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.google.firebase.storage.storage
import com.shashank.sony.fancytoastlib.FancyToast
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class UploadPicActivity : AppCompatActivity() {

    private val  binding : ActivityUploadPicBinding by lazy {
        ActivityUploadPicBinding.inflate(layoutInflater)
    }
    // List to store the keys of uploaded photos
    private val photoKeys = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.uploadimage.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_PICK
            intent.type = "image/*"
            imageLauncher.launch(intent)
        }

        binding.downloadimage.setOnClickListener {
            if (photoKeys.isNotEmpty()) {
                val photoKey = photoKeys.last() // For example, downloading the last uploaded image
                downloadImage(photoKey)
            } else {
                Toast.makeText(this, "No photos to download", Toast.LENGTH_SHORT).show()
            }
        }
        binding.deleteimage.setOnClickListener {
            if (photoKeys.isNotEmpty()) {
                val photoKey = photoKeys.last() // For example, downloading the last uploaded image
                deleteImage(photoKey)
            } else {
                Toast.makeText(this, "No photos to delete", Toast.LENGTH_SHORT).show()
            }
        }

        fetchImageUrls()

    }

    val imageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if(it.resultCode == Activity.RESULT_OK ){

            if ( it.data != null){

                val ref = Firebase.storage.reference.child("Photos/photo" + System.currentTimeMillis() + "." + getFileType(it.data!!.data))
                ref.putFile(it.data!!.data!!).addOnSuccessListener {
                    ref.downloadUrl.addOnSuccessListener { uri ->

//                        Firebase.database.reference.child("Photos").push().setValue(it.toString())

                        val photoKey = Firebase.database.reference.child("Photos").push().key
                        if (photoKey != null) {
                            Firebase.database.reference.child("Photos").child(photoKey).setValue(uri.toString())
                            //add key to list
                            photoKeys.add(photoKey)
                        }

//                        binding.imageView.setImageURI(it)
                        FancyToast.makeText(this@UploadPicActivity, "Photo Uploaded", FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show()

                        //show image
                        Picasso.get().load(uri.toString()).into(binding.imageView);
                    }
                }
            }
        }
    }

    //create diff ref for multiple images
    private fun getFileType(data : Uri?):String? {

        val  r = contentResolver
        val mimeTypes = MimeTypeMap.getSingleton()
        return mimeTypes.getMimeTypeFromExtension(r.getType(data!!))
    }

    private fun downloadImage(photoKey: String) = CoroutineScope(Dispatchers.IO).launch {
        try {
            val urlRef = Firebase.database.reference.child("Photos").child(photoKey)
            val dataSnapshot = urlRef.get().await()
            val url = dataSnapshot.getValue(String::class.java)
            if (url != null) {
                val maxDownloadSize = 5L * 1024 * 1024 // 5 MB
                val bytes = Firebase.storage.getReferenceFromUrl(url).getBytes(maxDownloadSize).await()
                val bitMap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                withContext(Dispatchers.Main) {
                    binding.ivdownload.setImageBitmap(bitMap)
                    FancyToast.makeText(this@UploadPicActivity,"Image Downloaded",FancyToast.LENGTH_LONG,FancyToast.DEFAULT,true).show()
                }
            }
        }
        catch (e : Exception){
            withContext(Dispatchers.Main){
                Toast.makeText(this@UploadPicActivity, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun deleteImage(photoKey: String) = CoroutineScope(Dispatchers.IO).launch {
        try {
            val urlRef = Firebase.database.reference.child("Photos").child(photoKey)
            val dataSnapshot = urlRef.get().await()
            val url = dataSnapshot.getValue(String::class.java)
            if (url != null) {
                // Delete image from Firebase Storage
                Firebase.storage.getReferenceFromUrl(url).delete().await()

                // Delete the database reference
                urlRef.removeValue().await()

                // Remove the key from the list
                photoKeys.remove(photoKey)

                withContext(Dispatchers.Main) {
                    FancyToast.makeText(this@UploadPicActivity,"Image Deleted",FancyToast.LENGTH_LONG,FancyToast.WARNING,true).show()
                }
            }
        }
        catch (e : Exception){
            withContext(Dispatchers.Main){
                Toast.makeText(this@UploadPicActivity, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun fetchImageUrls() = CoroutineScope(Dispatchers.IO).launch {
        try {
            val photoRef = Firebase.database.reference.child("Photos")
            val dataSnapshot = photoRef.get().await()
            val urls = dataSnapshot.children.mapNotNull { it.getValue(String::class.java) }

            withContext(Dispatchers.Main) {
                val imageAdapter = ImageAdapter(urls)
                binding.imagestoragerecycler.adapter = imageAdapter
                binding.imagestoragerecycler.layoutManager = LinearLayoutManager(this@UploadPicActivity)
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(this@UploadPicActivity, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun enableEdgeToEdge(){

    }

}