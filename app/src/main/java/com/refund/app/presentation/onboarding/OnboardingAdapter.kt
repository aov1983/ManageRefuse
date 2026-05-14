package com.refund.app.presentation.onboarding

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * Адаптер для слайдов онбординга
 */
class OnboardingAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> OnboardingSlideFragment.newInstance(
                "Что такое Refund?",
                "Контролируйте все свои подписки в одном месте",
                R.drawable.ic_onboarding_1
            )
            1 -> OnboardingSlideFragment.newInstance(
                "Как это работает?",
                "Добавьте подписку → Получите напоминание → Отмените ненужное",
                R.drawable.ic_onboarding_2
            )
            2 -> OnboardingSlideFragment.newInstance(
                "Начните экономить",
                "Первая подписка бесплатно!",
                R.drawable.ic_onboarding_3
            )
            else -> throw IllegalArgumentException("Invalid position")
        }
    }
}
