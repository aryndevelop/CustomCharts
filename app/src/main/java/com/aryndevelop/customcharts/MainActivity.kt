package com.aryndevelop.customcharts

import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aryndevelop.customcharts.databinding.ActivityMainBinding
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.hb.vts.utils.CustomBarChartRenderer
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val chartData = arrayListOf(4f, 10f, 1f, 6f, 0f, 2.5f, 3f, 7f, 0.5f, 0f, 2f)
        val vehicleType = arrayListOf("cars", "trucks", "sedan", "limousine", "semi truck", "bike", "bicycle", "coupe", "sports car", "wagon", "convertible")

        val barEntries = ArrayList<BarEntry>()

        chartData.forEachIndexed { index, value -> barEntries.add(BarEntry(index.toFloat(), value)) }

        // get font from assets folder
        val gilroyMedium = Typeface.createFromAsset(assets, "font/gilroy_medium.ttf")

        binding.apply {
            if (chartData.isEmpty()) {
                barChartView.setNoDataText("No chart data available")
                barChartView.setNoDataTextTypeface(gilroyMedium)
                barChartView.setNoDataTextColor(Color.BLACK)
                barChartView.notifyDataSetChanged()
                barChartView.invalidate()
            }
            else {
                val xAxisFormatter = IndexAxisValueFormatter(vehicleType)
                val xAxis = barChartView.xAxis
                xAxis.position = XAxis.XAxisPosition.BOTTOM // set xAxis to bottom
                xAxis.typeface = gilroyMedium // set font to xAxis labels
                xAxis.setDrawGridLines(false) // hide gridlines of xXis
                xAxis.granularity = 1f // only intervals of 1 day
                xAxis.textSize = 10f
                xAxis.textColor = Color.parseColor("#555555")
                xAxis.valueFormatter = xAxisFormatter

                val barDataSet = BarDataSet(barEntries, "")
                barDataSet.highLightColor = Color.TRANSPARENT;
                barDataSet.highLightAlpha = 0
                val colorClassArray = mutableListOf(Color.parseColor("#db3700"))
                barDataSet.colors = colorClassArray

                // set data to view
                val barData = BarData(barDataSet)
                barData.barWidth = 0.3f
                barData.setDrawValues(false)
                barChartView.data = barData

                val barChartRender = CustomBarChartRenderer(barChartView, barChartView.animator, barChartView.viewPortHandler, 15)
                barChartView.renderer = barChartRender

                val marker = CustomMarkerView(
                    this@MainActivity, R.layout.custom_bar_graph_maker_layout,
                    isPie = false,
                    isStaggeredBarChart = true
                )
                barChartView.marker = marker
                barChartView.setDrawMarkers(true)

                barChartView.setMaxVisibleValueCount(3)

                val l = barChartView.legend;
                l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
                l.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
                l.orientation = Legend.LegendOrientation.HORIZONTAL
                l.setDrawInside(false)
                l.typeface = gilroyMedium
                l.textSize = 14f
                l.textColor = Color.BLACK
                l.xEntrySpace = 15f // set the space between the legend entries on the x-axis

/*                val stack1 = BaseApplication.applicationContext()
                    ?.getColor(R.color.staggered_bar_chart_color2)
                    ?.let {
                        LegendEntry(
                            "On trip",
                            Legend.LegendForm.CIRCLE,
                            10f,
                            2f,
                            null,
                            it
                        )
                    }
                val stack2 = BaseApplication.applicationContext()
                    ?.getColor(R.color.staggered_bar_chart_color1)
                    ?.let {
                        LegendEntry(
                            "Available",
                            Legend.LegendForm.CIRCLE,
                            10f,
                            2f,
                            null,
                            it
                        )
                    }

                // set custom labels and colors
                l.setCustom(mutableListOf(stack1, stack2))*/
                barChartView.setVisibleXRangeMaximum(4f)
            }
        }
    }

}