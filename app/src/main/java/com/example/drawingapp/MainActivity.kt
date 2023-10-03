package com.example.drawingapp

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.get
import com.example.drawingapp.databinding.ActivityMainBinding
import com.example.drawingapp.databinding.DialogBrushSizeBinding
//import kotlin.coroutines.jvm.internal.CompletedContinuation.context

class MainActivity : AppCompatActivity() {

//    private var dialog : Dialog? = null
//    private var binding : ActivityMainBinding? = null
    // hihijbihb
    private var drawingView : DrawingView? = null
    private var mImageButtonCurrentPaint : ImageButton ? = null

    private val memoryPermissionLauncher : ActivityResultLauncher<Array<String>> =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ){
            permissions ->
            permissions.entries.forEach{
                var permissionName = it.key
                var isGranted = it.value
                if(isGranted){
                    Toast.makeText(this, "Access is Given , Thanks", Toast.LENGTH_SHORT).show()
                }else{
                    if(permissionName == Manifest.permission.READ_EXTERNAL_STORAGE){
                        Toast.makeText(applicationContext,"Permisson denied for storage access", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_DrawingApp)
        setContentView(R.layout.activity_main)

        val galleryBtn : ImageButton = findViewById(R.id.gallery_button)
        drawingView = findViewById(R.id.drawing_view)

        val ibBrush: ImageButton = findViewById(R.id.brush_button)
        drawingView?.setSizeForBrush( 5.toFloat() )

        val linearLayoutPaintColors = findViewById<LinearLayout>(R.id.paint_colors)

        mImageButtonCurrentPaint = linearLayoutPaintColors[2] as ImageButton


        ibBrush.setOnClickListener {
            showBrushSizeChooserDialog()
        }

        galleryBtn.setOnClickListener {
            requestForPermission()

        }


    }
    private fun showBrushSizeChooserDialog() {

        val brushDialog = Dialog(this)
        brushDialog.setContentView(R.layout.dialog_brush_size)
        brushDialog.setTitle("Brush size :")

        val smallBtn: ImageButton = brushDialog.findViewById(R.id.image_small_button)

        smallBtn.setOnClickListener(View.OnClickListener {
            drawingView?.setSizeForBrush( 10.toFloat() )
            brushDialog.dismiss()
        })
        val mediumBtn: ImageButton = brushDialog.findViewById(R.id.image_medium_button)
        mediumBtn.setOnClickListener(View.OnClickListener {
            drawingView?.setSizeForBrush(20.toFloat())
            brushDialog.dismiss()
        })

        val largeBtn: ImageButton = brushDialog.findViewById(R.id.image_large_button)
        largeBtn.setOnClickListener(View.OnClickListener {
            drawingView?.setSizeForBrush(30.toFloat())
            brushDialog.dismiss()
        })
        brushDialog.show()
    }

    fun paintClicked(view : View){
        if(view != mImageButtonCurrentPaint){
            val imageButton = view as ImageButton
            val colorTag = imageButton.tag.toString()
            drawingView?.setColor(colorTag)

            imageButton.setImageDrawable(
                ContextCompat.getDrawable(this, R.drawable.pallet_selected)
            )

            mImageButtonCurrentPaint?.setImageDrawable(
                ContextCompat.getDrawable(this, R.drawable.pallet_normal)
            )

            mImageButtonCurrentPaint = view

        }

    }

    fun rationalDialogue( title : String , msg : String){
        val build : AlertDialog.Builder = AlertDialog.Builder(this)
        build.setTitle(title)
            .setMessage(msg)
        build.setPositiveButton("Cancel"){ dialogInterface,view ->
            Toast.makeText(applicationContext,"Permission Denied",Toast.LENGTH_SHORT).show()
            dialogInterface.dismiss()

        }
        build.create().show()

    }

    private fun requestForPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)){
            rationalDialogue("DEnied Permisson ", "App cant work without accsesing camera permission")
        }else{
            memoryPermissionLauncher.launch(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE))
        }
    }



}