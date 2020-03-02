package com.example.buscalc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

	private lateinit var viewModel: BusViewModel
	private lateinit var distanceInput: EditText
	private lateinit var stopsInput: EditText
	private lateinit var peopleInput: EditText
	private lateinit var ticketInput: EditText
	private lateinit var electInput: EditText
	private lateinit var dieselInput: EditText
	private lateinit var gasInput: EditText
	private lateinit var outputCheapest: TextView
	private lateinit var outputFastest: TextView
	private lateinit var outputCleanest: TextView
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		outputCheapest = output_cheapest
		outputFastest = output_fastest
		outputCleanest = output_cleanest
		viewModel = ViewModelProvider(this).get(BusViewModel::class.java)
		viewModel.busLiveData.observe(this, Observer {
			outputCheapest.text = it[CHEAPEST]?.run {
				"Cheapest bus: ${busType.javaClass.simpleName}, \n" +
						"cost: ${cost.roundToTwoDecimals()}, \n" +
						"income: ${income.roundToTwoDecimals()}, \n" +
						"CO2 used: ${airDirt.roundToTwoDecimals()}"
			} ?: ""
			outputFastest.text = it[FASTEST]?.run {
				"Fastest bus: ${busType.javaClass.simpleName}, \n" +
						"cost: ${cost.roundToTwoDecimals()}, \n" +
						"income: ${income.roundToTwoDecimals()}, \n" +
						"CO2 used: ${airDirt.roundToTwoDecimals()}"
			} ?: ""
			outputCleanest.text = it[CLEANEST]?.run {
				"Cleanest bus: ${busType.javaClass.simpleName}, \n" +
						"cost: ${cost.roundToTwoDecimals()}, \n" +
						"income: ${income.roundToTwoDecimals()}, \n" +
						"CO2 used: ${airDirt.roundToTwoDecimals()}"
			} ?: ""
		})
		distanceInput = input_route_distance
		stopsInput = input_stops_count
		peopleInput = input_people
		ticketInput = input_ticket_price
		electInput = input_elect_price
		dieselInput = input_diesel_price
		gasInput = input_gas_price
		object : TextWatcher {
			override fun afterTextChanged(s: Editable?) {}

			override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

			override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
				viewModel.getBusChoices(buildRouteInfo())
			}

		}.let {
			distanceInput.addTextChangedListener(it)
			stopsInput.addTextChangedListener(it)
			peopleInput.addTextChangedListener(it)
			ticketInput.addTextChangedListener(it)
			electInput.addTextChangedListener(it)
			dieselInput.addTextChangedListener(it)
			gasInput.addTextChangedListener(it)
		}
	}

	private fun buildRouteInfo(): RouteInfo? {
		return RouteInfo(
				distanceInput.validateFloat() ?: return null,
				stopsInput.validateInt() ?: return null,
				peopleInput.validateInt() ?: return null,
				ticketInput.validateFloat() ?: return null,
				electInput.validateFloat() ?: return null,
				dieselInput.validateFloat() ?: return null,
				gasInput.validateFloat() ?: return null
		)
	}
}
