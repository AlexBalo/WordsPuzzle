package com.balocco.words.common.extensions

import android.annotation.SuppressLint
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import com.balocco.words.R
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@SuppressLint("CommitTransaction")
class FragmentManagerExtensionsTest {

    companion object {
        private val LAYOUT_ID = 9874
        private val ANIMATION_TYPE = AnimationType.FADE
    }

    @Mock private lateinit var manager: FragmentManager
    @Mock private lateinit var transaction: FragmentTransaction
    @Mock private lateinit var fragment: Fragment

    @Before fun setUp() {
        MockitoAnnotations.initMocks(this)

        whenever(manager.beginTransaction()).thenReturn(transaction)
    }

    @Test fun `When replace without back stack, replaces fragment and commit change`() {
        manager.replaceWithoutBackStack(LAYOUT_ID, fragment, ANIMATION_TYPE)

        verify(transaction).replace(LAYOUT_ID, fragment)
        verify(transaction).commit()
    }

    @Test fun `When replace without back stack with none animation, sets no custom animations`() {
        manager.replaceWithoutBackStack(LAYOUT_ID, fragment, AnimationType.NONE)

        verify(transaction, never()).setCustomAnimations(any(), any(), any(), any())
    }

    @Test fun `When replace without back stack with slide animation, sets custom animations`() {
        manager.replaceWithoutBackStack(LAYOUT_ID, fragment, AnimationType.SLIDE)

        verify(transaction).setCustomAnimations(
                R.anim.open_from_right,
                R.anim.close_to_left,
                R.anim.open_from_left,
                R.anim.close_to_right)
    }

    @Test fun `When replace without back stack with fade animation, sets custom animations`() {
        manager.replaceWithoutBackStack(LAYOUT_ID, fragment, AnimationType.FADE)

        verify(transaction).setCustomAnimations(
                android.R.anim.fade_in,
                android.R.anim.fade_out,
                android.R.anim.fade_in,
                android.R.anim.fade_out)
    }

}