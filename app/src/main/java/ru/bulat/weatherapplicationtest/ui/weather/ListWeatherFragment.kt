package ru.bulat.weatherapplicationtest.ui.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.list_weather_fragment.*
import ru.bulat.weatherapplicationtest.R
import ru.bulat.weatherapplicationtest.databinding.ListWeatherFragmentBinding
import ru.bulat.weatherapplicationtest.base.BaseFragment


class ListWeatherFragment : BaseFragment() {
    lateinit var binding: ListWeatherFragmentBinding
    lateinit var viewModel: ListWeatherViewModel
    private var latitude = 59.950015
    private var longtitude = 30.316599

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            ListWeatherFragmentBinding.inflate(
                inflater,
                container,
                false
            )
        binding.dayWeatherList.layoutManager = LinearLayoutManager(
            context,
            RecyclerView.VERTICAL,
            false
        )
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.text?.toString()) {
                    resources.getString(R.string.st_petersburg) -> {
                        latitude = 59.950015
                        longtitude = 30.316599
                    }
                    resources.getString(R.string.moscow) -> {
                        latitude = 55.753913
                        longtitude = 37.620836
                    }
                }
                viewModel.getWeatherByCoordinate(latitude, longtitude)
            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ListWeatherViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.recyclerViewUpdateCallback = {
            binding.dayWeatherList.adapter = viewModel.dayWeatherListAdapter
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner, Observer {
            showToast(it)
        })
        viewModel.getWeatherByCoordinate(latitude, longtitude)
    }
}