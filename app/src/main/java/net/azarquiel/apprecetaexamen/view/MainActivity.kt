package net.azarquiel.apprecetaexamen.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import net.azarquiel.apprecetaexamen.navigation.AppNavigation
import net.azarquiel.apprecetaexamen.ui.theme.AppRecetaExamenTheme
import net.azarquiel.apprecetaexamen.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppRecetaExamenTheme {
                val mainViewModel : MainViewModel = remember { MainViewModel(this@MainActivity) }
                AppNavigation(mainViewModel)
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppRecetaExamenTheme {
        Greeting("Android")
    }
}