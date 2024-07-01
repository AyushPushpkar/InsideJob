package com.example.insidejob

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts

public class CustomMenu {


    @SuppressLint("DiscouragedPrivateApi")
    fun showMenu(context: Context, view: View) {
        val pop = PopupMenu(context, view)
        pop.inflate(R.menu.menu)

        pop.setOnMenuItemClickListener { menuItem ->
            when (menuItem!!.itemId) {

                R.id.feature -> {
                    val intent = Intent(context,FeaturesActivity::class.java)
                    context.startActivity(intent)
                    true
                }
                R.id.setting-> {
                    val intent = Intent(context, SettingAct::class.java)
                    context.startActivity(intent)
                    true
                }
                R.id.login-> {
                val intent = Intent(context, UIUXactivity::class.java)
                context.startActivity(intent)
                true
                }
                else -> false
            }
        }

        //to force icons to be displayed in a PopupMenu
        try {
            val fieldMpopup = PopupMenu::class.java.getDeclaredField("mPopup")
            fieldMpopup.isAccessible = true
            val mPopup = fieldMpopup.get(pop)
            val popupClass = mPopup.javaClass
            val setForceIcons = popupClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java)
            setForceIcons.invoke(mPopup, true)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            pop.show()
        }
    }
}