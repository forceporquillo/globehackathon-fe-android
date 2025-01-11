package dev.forcecodes.auth.demo.presentation

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import dev.forcecodes.auth.demo.R

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class WelcomeFragment : Fragment(R.layout.fragment_welcome) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
//            // do nothing
//        }
    }
}
