package com.github.eliascoelho911.musicplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.github.eliascoelho911.musicplayer.ui.theme.MusicPlayerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MusicPlayerTheme {
//                val soundWaveState = SoundWaveState().apply {
//                    registerAmplitude(1, 501)
//                    registerAmplitude(2, 1000)
//                    registerAmplitude(4, 901)
//                    registerAmplitude(5, 1320)
//                }
//                SoundWaveProgressIndicator(modifier = Modifier.width(200.dp), soundWaveProgressIndicatorState = soundWaveState)
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MusicPlayerTheme {
        Greeting("Android")
    }
}