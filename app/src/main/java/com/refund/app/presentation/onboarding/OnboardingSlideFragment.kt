package com.refund.app.presentation.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.refund.app.R

/**
 * Фрагмент слайда онбординга
 */
class OnboardingSlideFragment : Fragment() {

    companion object {
        private const val ARG_TITLE = "title"
        private const val ARG_DESCRIPTION = "description"
        private const val ARG_ICON = "icon"

        fun newInstance(title: String, description: String, iconResId: Int): OnboardingSlideFragment {
            return OnboardingSlideFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TITLE, title)
                    putString(ARG_DESCRIPTION, description)
                    putInt(ARG_ICON, iconResId)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_onboarding_slide, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val title = arguments?.getString(ARG_TITLE, "") ?: ""
        val description = arguments?.getString(ARG_DESCRIPTION, "") ?: ""
        val iconResId = arguments?.getInt(ARG_ICON, 0) ?: 0

        view.findViewById<TextView>(R.id.slide_title).text = title
        view.findViewById<TextView>(R.id.slide_description).text = description
        view.findViewById<ImageView>(R.id.slide_icon).setImageResource(iconResId)
    }
}
