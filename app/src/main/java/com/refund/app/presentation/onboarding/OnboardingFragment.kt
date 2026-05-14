package com.refund.app.presentation.onboarding

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.button.MaterialButton
import com.refund.app.R
import com.refund.app.utils.PermissionUtils

/**
 * Фрагмент онбординга - первый запуск приложения
 */
class OnboardingFragment : Fragment() {

    private var _binding: android.view.View? = null
    private val binding get() = _binding!!

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        // Переходим к главному экрану независимо от результата
        navigateToMain()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_onboarding, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = view

        val viewPager = binding.findViewById<ViewPager2>(R.id.view_pager)
        val buttonNext = binding.findViewById<MaterialButton>(R.id.btn_next)

        // Установка адаптера для ViewPager2 (упрощённо - без слайдов для MVP)
        viewPager.adapter = OnboardingAdapter(this)

        buttonNext.setOnClickListener {
            requestNotificationPermission()
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        } else {
            navigateToMain()
        }
    }

    private fun navigateToMain() {
        // Сохраняем флаг, что онбординг пройден
        val prefs = requireContext().getSharedPreferences("refund_prefs", 0)
        prefs.edit().putBoolean("onboarding_completed", true).apply()

        findNavController().navigate(R.id.action_onboarding_to_main)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
