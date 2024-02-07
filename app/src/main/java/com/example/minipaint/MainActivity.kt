package com.example.minipaint

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val myCanvasView = MyCanvasView(this)
        myCanvasView.contentDescription = getString(R.string.canvasContentDescription)

        val windowInsetsController =
            WindowCompat.getInsetsController(window, window.decorView)
        // Configure the behavior of the hidden system bars.
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        // Add a listener to update the behavior of the toggle fullscreen button when
        // the system bars are hidden or revealed.
//        window.decorView.setOnApplyWindowInsetsListener { view, windowInsets ->
//            // You can hide the caption bar even when the other system bars are visible.
//            // To account for this, explicitly check the visibility of navigationBars()
//            // and statusBars() rather than checking the visibility of systemBars().
//            if (windowInsets.isVisible(WindowInsetsCompat.Type.navigationBars())
//                || windowInsets.isVisible(WindowInsetsCompat.Type.statusBars())) {
//                binding.toggleFullscreenButton.setOnClickListener {
//                    // Hide both the status bar and the navigation bar.
//                    windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
//                }
//            } else {
//                binding.toggleFullscreenButton.setOnClickListener {
//                    // Show both the status bar and the navigation bar.
//                    windowInsetsController.show(WindowInsetsCompat.Type.systemBars())
//                }
//            }
//            view.onApplyWindowInsets(windowInsets)
//        }
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
        setContentView(myCanvasView)
    }
}