package com.example.insidejob

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.MimeTypeMap
import android.widget.MediaController
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.example.insidejob.databinding.ActivityVideoBinding
import com.google.firebase.database.database
import com.google.firebase.database.ktx.database
import com.google.firebase.storage.storage
import com.shashank.sony.fancytoastlib.FancyToast
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storage
import com.google.firebase.ktx.Firebase


class VideoActivity : AppCompatActivity() {
    private val binding :ActivityVideoBinding by lazy {
        ActivityVideoBinding.inflate(layoutInflater)
    }
    private lateinit var progressDialog : ProgressDialog
    private var videoDatabaseKey: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.videoView.isVisible = false

        progressDialog = ProgressDialog(this)

        binding.startvid.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_PICK
            intent.type = "video/*"
            videoLauncher.launch(intent)
        }
    }
    val videoLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {

            if (it.data != null) {
                progressDialog.setTitle("Uploading...")
                progressDialog.show()

                val ref = Firebase.storage.reference.child("Videos/video" + System.currentTimeMillis() + "." + getFileType(it.data!!.data))

                ref.putFile(it.data!!.data!!).addOnSuccessListener {
                    ref.downloadUrl.addOnSuccessListener {uri ->

//                        Firebase.database.reference.child("Videos").push().setValue(it.toString())

                        // Store the reference to the database entry
                        val databaseRef = Firebase.database.reference.child("Videos").push()
                        databaseRef.setValue(uri.toString())
                        videoDatabaseKey = databaseRef.key

                        progressDialog.dismiss()
                        FancyToast.makeText(this@VideoActivity, "Video Uploaded", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show()

                        binding.startvid.isVisible = false
                        binding.videoView.isVisible = true

                        val mediaController = MediaController(this@VideoActivity)
                        mediaController.setAnchorView(binding.videoView)

                        binding.videoView.setVideoURI(uri)
                        binding.videoView.setMediaController(mediaController)
                        binding.videoView.start()
                        binding.videoView.setOnCompletionListener {
                            ref.delete().addOnSuccessListener {
                                // Delete the database entry
                                videoDatabaseKey?.let { key ->
                                    Firebase.database.reference.child("Videos").child(key).removeValue()
                                }
                                FancyToast.makeText(this@VideoActivity,"Video deleted",FancyToast.LENGTH_LONG ,FancyToast.WARNING,true).show()
                            }
                        }

                    }
                }
                    .addOnProgressListener{

                        // Calculate the upload progress
                        val progress = (100.0 * it.bytesTransferred / it.totalByteCount)
                        // Update the progress dialog title with the correct percentage
                        progressDialog.setTitle("Uploading... ${progress.toInt()}%")
                    }
            }
        }
    }

    //dynamic names
    private fun getFileType(data : Uri?):String? {

        val  r = contentResolver
        val mimeTypes = MimeTypeMap.getSingleton()
        return mimeTypes.getMimeTypeFromExtension(r.getType(data!!))
    }

    private fun enableEdgeToEdge(){

    }
}