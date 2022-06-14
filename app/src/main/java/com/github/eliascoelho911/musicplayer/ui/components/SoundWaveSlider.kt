package com.github.eliascoelho911.musicplayer.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.floor

@Composable
fun SoundWaveSlider(
    value: Int,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    onValueFinished: (() -> Unit)? = null,
    valueRange: IntRange = 0..100,
    thumbColor: Color = SoundWaveSliderDefaults.ThumbColor(),
    trackColor: Color = SoundWaveSliderDefaults.TrackColor(),
    soundWaveSliderState: SoundWaveSliderState = rememberSoundWaveSliderState(),
) {

    Canvas(modifier = Modifier
        .height(SoundWaveSliderDefaults.Height)
        .then(modifier)
    ) {
        val barWidth = SoundWaveSliderDefaults.BarWidth.toPx()
        val minSpacingBetweenBars = SoundWaveSliderDefaults.MinSpacingBetweenBars.toPx()
        val requiredWidthWithSpace = barWidth + minSpacingBetweenBars
        val amountOfItems = floor(size.width / requiredWidthWithSpace).toInt()
        val finalSpace = (size.width - (amountOfItems * barWidth)) / (amountOfItems - 1)
        val amountOfItemsWithTrackColor = floor(amountOfItems * (value / 100f)).toInt()
        var posXOfLastBar = 0f

        for (i in 0 until amountOfItems) {
            val barHeight = (soundWaveSliderState.getBarFractionalHeight(i) * size.height)
                .takeIf { it > 0 } ?: SoundWaveSliderDefaults.BarMinHeight.toPx()
            val x = if (i == 0) 0f else posXOfLastBar + barWidth + finalSpace
            val y = center.y - barHeight / 2
            val color = if (i in 0 until amountOfItemsWithTrackColor) trackColor else thumbColor
            drawRoundRect(color,
                topLeft = Offset(x, y),
                Size(barWidth, barHeight),
                cornerRadius = CornerRadius(barWidth / 2))
            posXOfLastBar = x
        }
    }
}

@Composable
fun rememberSoundWaveSliderState() = remember { SoundWaveSliderState() }

class SoundWaveSliderState {
    private val _data = mutableListOf<IndexedValue<Int>>()

    fun registerAmplitude(amplitude: Int) {
        registerAmplitude(_data.lastIndex + 1, amplitude)
    }

    fun registerAmplitude(index: Int, amplitude: Int) {
        _data.add(IndexedValue(index, amplitude))
    }

    fun getBarFractionalHeight(index: Int): Float {
        val maxAmplitude = _data.maxOfOrNull { it.value } ?: return 0f
        val minAmplitude = 0
        val amplitude = _data.singleOrNull { it.index == index } ?: return 0f

        when (amplitude.value) {
            maxAmplitude -> return 1f
            minAmplitude -> return 0f
        }

        val delta = maxAmplitude - minAmplitude

        return (amplitude.value - minAmplitude) / delta.toFloat()
    }
}

private object SoundWaveSliderDefaults {
    val MinSpacingBetweenBars = 4.dp
    val BarWidth = 4.dp
    val BarMinHeight = 4.dp
    val Height = 48.dp
    val TrackColor: @Composable () -> Color = { MaterialTheme.colorScheme.primary }
    val ThumbColor: @Composable () -> Color = { MaterialTheme.colorScheme.surfaceVariant }
}

@Preview("sound wave 0%")
@Composable
private fun SoundWaveSlider0Preview() {
    SoundWaveSliderPreview(value = 0)
}

@Preview("sound wave 50%")
@Composable
private fun SoundWaveSlider50Preview() {
    SoundWaveSliderPreview(value = 50)
}

@Preview("sound wave 100%")
@Composable
private fun SoundWaveSlider100Preview() {
    SoundWaveSliderPreview(value = 100)
}

@Composable
private fun SoundWaveSliderPreview(value: Int) {
    Surface(color = MaterialTheme.colorScheme.surface) {
        val soundWaveSliderState = rememberSoundWaveSliderState().apply {
            registerAmplitude(1, 501)
            registerAmplitude(2, 1000)
            registerAmplitude(4, 901)
            registerAmplitude(5, 1320)
        }
        SoundWaveSlider(value, modifier = Modifier.width(200.dp),
            soundWaveSliderState = soundWaveSliderState, onValueChange = {})
    }
}