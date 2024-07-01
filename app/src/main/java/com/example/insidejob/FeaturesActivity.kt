package com.example.insidejob

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.emreesen.sntoast.SnToast
import com.emreesen.sntoast.Type
import com.example.insidejob.databinding.ActivityFeaturesBinding
import com.shashank.sony.fancytoastlib.FancyToast
import taimoor.sultani.sweetalert2.Sweetalert
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle

class FeaturesActivity : AppCompatActivity() {

    private val binding : ActivityFeaturesBinding by lazy {
        ActivityFeaturesBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //customToast
        binding.customtoast.setOnClickListener {
            SnToast.Builder()
                .context(this@FeaturesActivity)
                .type(Type.INFORMATION)
                .message("It's Useless 🙂") //.cancelable(false or true) Optional Default: False
                // .iconSize(int size) Optional Default: 34dp
                // .textSize(int size) Optional Default 18sp
                .animation(true) //Optional Default: True
                .duration(3000)// Optional Default: 3000ms
                // .backgroundColor(R.color.example) Default: It is filled according to the toast type. If an assignment is made, the assigned value is used
                // .icon(R.drawable.example) Default: It is filled according to the toast type. If an assignment is made, the assigned value is used
                .build()
        }

       binding.sweetalert.setOnClickListener {

            var alert = Sweetalert(this, Sweetalert.SUCCESS_TYPE)
                .setTitleText("Success Sweet")
                .setContentText("Booyah!")
                .setCancelButton("Cancel") {
                    it.dismissWithAnimation()
                }
                .setCancelButtonBackgroundColor("#03A9F4")
            alert.show()
        }

        binding.Bundlepassing.setOnClickListener {
            intent = Intent(this, BundlePassingActivity::class.java)
            startActivity(intent)
        }

        binding.loading.setOnClickListener {
            intent = Intent(this, CustomLoadingActivity::class.java)
            startActivity(intent)
        }

        binding.Motiontoast.setOnClickListener {

            MotionToast.darkColorToast(
                this,
                "Failed ☹️",
                "Profile Update Failed!",
                MotionToastStyle.ERROR,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                ResourcesCompat.getFont(this, www.sanju.motiontoast.R.font.helvetica_regular)
            )

//            MotionToast.createToast(this,
//                "Failed ☹️",
//                "Profile Update Failed!",
//                MotionToastStyle.ERROR,
//                MotionToast.GRAVITY_BOTTOM,
//                MotionToast.LONG_DURATION,
//                ResourcesCompat.getFont(this, www.sanju.motiontoast.R.font.helvetica_regular))
        }


        //alert dialog box
        binding.alertbox.setOnClickListener {
            val dialog = AlertDialog.Builder(this)
            dialog.setTitle("Reminder")
            dialog.setMessage(R.string.des)
            dialog.setIcon(R.drawable.baseline_access_time_24)

            dialog.setPositiveButton("YES"){dialogInterface,which ->
                Toast.makeText(this, "Implement yourself!", Toast.LENGTH_SHORT).show()
            }
            dialog.setNegativeButton("NO"){dialogInterface,which ->
                Toast.makeText(this, "why not ? ", Toast.LENGTH_SHORT).show()
            }
            dialog.setNeutralButton("Cancel"){dialogInterface,which ->}

            val alertDialog: AlertDialog = dialog.create()
            alertDialog.setCancelable(false)
            alertDialog.show()
            true
        }

        binding.share.setOnClickListener {
            val intent = Intent(this,Implicit_intentActivity::class.java)
            startActivity(intent)
        }

        binding.fancytoast.setOnClickListener {
            FancyToast.makeText(this,"hypothetically😢", FancyToast.LENGTH_SHORT, FancyToast.WARNING,true).show()
        }

        binding.uploadpic.setOnClickListener {
            val intent = Intent(this,UploadPicActivity::class.java)
            startActivity(intent)
        }

        binding.video.setOnClickListener {
            val intent = Intent(this,VideoActivity::class.java)
            startActivity(intent)
        }


    }
    private fun enableEdgeToEdge(){

    }

}