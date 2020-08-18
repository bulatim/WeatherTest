package ru.bulat.weatherapplicationtest.ui.weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import ru.bulat.weatherapplicationtest.R
import ru.bulat.weatherapplicationtest.databinding.ItemDayWeatherBinding
import ru.bulat.weatherapplicationtest.model.api.response.Datum_

class WeatherListAdapter() : Adapter<WeatherListAdapter.ViewHolder>() {
    private lateinit var dayWeatherList: List<Datum_>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemDayWeatherBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_day_weather,
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dayWeatherList[position])
    }

    override fun getItemCount(): Int {
        return if (::dayWeatherList.isInitialized) dayWeatherList.size else 0
    }

    fun updateDayWeatherList(dayWeatherList: List<Datum_>) {
        this.dayWeatherList = dayWeatherList
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemDayWeatherBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val viewModel = DayWeatherViewModel()

        fun bind(weatherDate: Datum_) {
            viewModel.bind(weatherDate)
            binding.weatherDate = weatherDate
            binding.viewModel = viewModel
        }
    }
}