package com.aryndevelop.customcharts

import android.content.Context
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import kotlin.math.roundToInt

private const val TAG = "CustomMarkerView"

class CustomMarkerView(context: Context?, layoutResource: Int, var isPie: Boolean?=true, private var isStaggeredBarChart: Boolean = false) : MarkerView(context, layoutResource) {

    private var txtViewData: TextView? = null
    private var txtViewDataOnTrip: AppCompatTextView? = null
    private var txtViewDataAvailable: AppCompatTextView? = null
    private var txtViewDataTotal: AppCompatTextView? = null

    init {
        txtViewData = findViewById(R.id.txtViewData)
        if (isStaggeredBarChart) {
            txtViewDataOnTrip = findViewById(R.id.txtViewDataOnTrip)
            txtViewDataAvailable = findViewById(R.id.txtViewDataAvailable)
            txtViewDataTotal = findViewById(R.id.txtViewDataTotal)
        }

    }

    override fun refreshContent(e: Entry?, highlight: Highlight?) {

        if(isPie == true){
            val xAxis = (e as PieEntry)?.label.toString() ?: "0"
            val yAxis = e?.y?.roundToInt().toString() ?: "0"
            txtViewData?.text = "$xAxis  $yAxis"
        }else{
            val xAxis = (e as BarEntry)
            val yAxis = e?.y?.roundToInt().toString() ?: "0"

            if (isStaggeredBarChart) {
                val onTrip = e.yVals[0].roundToInt()
                val available = e.yVals[1].roundToInt()

                txtViewDataOnTrip?.text = "$onTrip"
                txtViewDataAvailable?.text = "$available"
                txtViewDataTotal?.text = "$yAxis"

            } else {
                txtViewData?.text = "$yAxis"

            }

        }



        super.refreshContent(e, highlight)
    }

    override fun getOffset(): MPPointF {
        return MPPointF(-(width / 2f), -height.toFloat())
    }

}