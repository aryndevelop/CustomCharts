package com.aryndevelop.customcharts

import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aryndevelop.customcharts.databinding.ActivityMainBinding
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.LegendEntry
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.hb.vts.utils.CustomBarChartRenderer
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val foodCount = arrayListOf(4f, 10f, 1f, 6f, 0f, 2.5f, 3f, 7f, 0.5f, 0f, 2f)
        val foodType = arrayListOf("agriculture", "bean", "cultivate", "dairy food", "domesticate", "grain", "honey", "nut", "poultry", "raise", "seafood")

        barChart(binding.barChartView, FoodData(food1 = foodCount, foodType = foodType))

    }

    data class FoodData(
        var food1: ArrayList<Float>? = arrayListOf(),
        var food2: ArrayList<Float>? = arrayListOf(),
        var foodType: ArrayList<String>? = arrayListOf()
    )

    private fun barChart(barChartView: BarChart, foodData: FoodData) {
        val barEntries = ArrayList<BarEntry>()

        foodData.food1?.forEachIndexed { index, value -> barEntries.add(BarEntry(index.toFloat(), value)) }

        // get font from assets folder
        val gilroyMedium = Typeface.createFromAsset(assets, "font/gilroy_medium.ttf")

        binding.apply {
            if (foodData.food1?.isEmpty() == true) {
                barChartView.setNoDataText("No chart data available")
                barChartView.setNoDataTextTypeface(gilroyMedium)
                barChartView.setNoDataTextColor(Color.BLACK)
                barChartView.notifyDataSetChanged()
                barChartView.invalidate()
            }
            else {

                val barDataSet = BarDataSet(barEntries, "")
                // hide highlight
                barDataSet.highLightColor = Color.TRANSPARENT
                barDataSet.highLightAlpha = 0

                val colorClassArray = mutableListOf(Color.parseColor("#db3700"))
                barDataSet.colors = colorClassArray

                // set data to view
                val barData = BarData(barDataSet)
                barData.barWidth = 0.3f
                barData.setDrawValues(false)
                barChartView.data = barData

                val barChartRender = CustomBarChartRenderer(barChartView, barChartView.animator, barChartView.viewPortHandler, 15)
                barChartView.renderer = barChartRender // top rounded corner

                val marker = CustomMarkerView(this@MainActivity, R.layout.custom_graph_maker_layout,
                    isPie = false,
                    isStaggeredBarChart = false
                )
                barChartView.marker = marker //Custom marker
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

                val xAxisFormatter = IndexAxisValueFormatter(foodData.foodType)
                val xAxis = barChartView.xAxis
                xAxis.position = XAxis.XAxisPosition.BOTTOM // set xAxis to bottom
                xAxis.typeface = gilroyMedium // set font to xAxis labels
                xAxis.setDrawGridLines(false) // hide gridlines of xXis
                xAxis.granularity = 1f // only intervals of 1 day
                xAxis.textSize = 10f
                xAxis.textColor = Color.parseColor("#555555")
                xAxis.valueFormatter = xAxisFormatter

                barChartView.setVisibleXRangeMaximum(6f) // set range to show current visible values

                val barSpace = 0.05f
                val groupSpace = 0.3f
                barChartView.axisLeft.axisMinimum = 0f
                barChartView.axisRight.axisMinimum = 0f
                barChartView.axisRight.isEnabled = false // remove right axis lines
                barChartView.axisLeft.isEnabled = false // remove left axis lines
                // set custom labels and colors
                val stack1 = LegendEntry("On trip", Legend.LegendForm.CIRCLE, 10f, 2f, null, Color.parseColor("#db3700"))
                l.setCustom(mutableListOf(stack1))

                barChartView.description.isEnabled = false

                barChartView.xAxis.setDrawLabels(true)
                barChartView.extraBottomOffset = 10f
                barChartView.setPinchZoom(false)
                barChartView.setScaleEnabled(false)
                barChartView.notifyDataSetChanged()
                barChartView.invalidate()
            }
        }
    }

}