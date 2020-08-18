package ru.bulat.weatherapplicationtest.base

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.bulat.weatherapplicationtest.App
import javax.inject.Inject

open class BaseFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        retainInstance = true
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    fun component() = (requireActivity().application as App).component

    protected fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}