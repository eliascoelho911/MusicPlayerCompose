package com.github.eliascoelho911.musicplayer.ui.components

import org.junit.Assert.assertEquals
import org.junit.Test

class SoundWaveStateTest {

    @Test
    fun testGetBarFractionalHeight() {
        val state = SoundWaveState().apply {
            registerAmplitude(20)
            registerAmplitude(40)
            registerAmplitude(60)
            registerAmplitude(80)
            registerAmplitude(100)
            registerAmplitude(120)
        }

        assertEquals(0f, state.getBarFractionalHeight(0))
        assertEquals(0.2f, state.getBarFractionalHeight(1))
        assertEquals(0.4f, state.getBarFractionalHeight(2))
        assertEquals(0.6f, state.getBarFractionalHeight(3))
        assertEquals(0.8f, state.getBarFractionalHeight(4))
        assertEquals(1f, state.getBarFractionalHeight(5))
    }
}