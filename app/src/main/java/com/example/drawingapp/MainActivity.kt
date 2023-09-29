package com.example.drawingapp

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.get
import com.example.drawingapp.databinding.ActivityMainBinding
import com.example.drawingapp.databinding.DialogBrushSizeBinding
//import kotlin.coroutines.jvm.internal.CompletedContinuation.context

class MainActivity : AppCompatActivity() {

//    private var dialog : Dialog? = null
//    private var binding : ActivityMainBinding? = null
    // ok
    private var drawingView : DrawingView? = null
    private var mImageButtonCurrentPaint : ImageButton ? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_DrawingApp)
        setContentView(R.layout.activity_main)

        drawingView = findViewById(R.id.drawing_view)

        val ibBrush: ImageButton = findViewById(R.id.brush_button)
        drawingView?.setSizeForBrush( 5.toFloat() )

        val linearLayoutPaintColors = findViewById<LinearLayout>(R.id.paint_colors)

        mImageButtonCurrentPaint = linearLayoutPaintColors[2] as ImageButton


        ibBrush.setOnClickListener {
            showBrushSizeChooserDialog()
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

}