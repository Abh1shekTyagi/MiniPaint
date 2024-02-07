package com.example.minipaint

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Rect
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import androidx.core.content.res.ResourcesCompat
import kotlin.math.abs

private const val STROKE_WIDTH = 12f // has to be float

class MyCanvasView(context: Context) : View(context) {
//    private lateinit var extraCanvas: Canvas
//    private lateinit var extraBitmap: Bitmap
    private val backgroundColor = ResourcesCompat.getColor(resources, R.color.black, null)
    private val drawColor = ResourcesCompat.getColor(resources, R.color.black, null)
    // Path representing the drawing so far
    private val drawing = Path()

    // Path representing what's currently being drawn
    private val curPath = Path()
    // Set up the paint with which to draw.
    private val paint = Paint().apply {
        color = drawColor
        // Smooths out edges of what is drawn without affecting shape.
        isAntiAlias = true
        // Dithering affects how colors with higher-precision than the device are down-sampled.
        isDither = true
        style = Paint.Style.STROKE // default: FILL
        strokeJoin = Paint.Join.ROUND // default: MITER
        strokeCap = Paint.Cap.ROUND // default: BUTT
        strokeWidth = STROKE_WIDTH // default: Hairline-width (really thin)
    }
    private val currentPath = Path()
    private var motionTouchEventX = 0f
    private var motionTouchEventY = 0f

    private var currentX = 0f
    private var currentY = 0f

    private val touchTolerance = ViewConfiguration.get(context).scaledTouchSlop
    private lateinit var frame: Rect

    private fun touchMove() {
        val dx = abs(motionTouchEventX - currentX)
        val dy = abs(motionTouchEventY - currentY)
        if (dx >= touchTolerance || dy >= touchTolerance) {
            // QuadTo() adds a quadratic bezier from the last point,
            // approaching control point (x1,y1), and ending at (x2,y2).
            currentPath.quadTo(
                currentX,
                currentY,
                (motionTouchEventX + currentX) / 2,
                (motionTouchEventY + currentY) / 2
            )
            currentX = motionTouchEventX
            currentY = motionTouchEventY
            // Draw the path in the extra bitmap to cache it.
//            extraCanvas.drawPath(path, paint)
        }
        invalidate()
    }

    private fun touchStart() {
        currentPath.reset()
        currentPath.moveTo(motionTouchEventX, motionTouchEventY)
        currentX = motionTouchEventX
        currentY = motionTouchEventY
    }

    private fun touchUp() {
        drawing.addPath(currentPath)
        currentPath.reset()
    }

    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(width, height, oldWidth, oldHeight)
//        if (::extraBitmap.isInitialized) extraBitmap.recycle()
//        extraBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
//        extraCanvas = Canvas(extraBitmap)
//        extraCanvas.drawColor(backgroundColor)
        // Calculate a rectangular frame around the picture.
        val inset = 40
        frame = Rect(inset, inset, width - inset, height - inset)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
//        canvas.drawBitmap(extraBitmap, 0f, 0f, null)
//        canvas.drawRect(frame, paint)
        // Draw the drawing so far
        canvas.drawPath(drawing, paint)
// Draw any current squiggle
        canvas.drawPath(currentPath, paint)
// Draw a frame around the canvas
        canvas.drawRect(frame, paint)
    }

//    override fun performClick(): Boolean {
//        return super.performClick()
//
//    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        motionTouchEventX = event.x
        motionTouchEventY = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> touchStart()
            MotionEvent.ACTION_MOVE -> touchMove()
            MotionEvent.ACTION_UP -> touchUp()
        }
//        performClick()
        return true
    }
}