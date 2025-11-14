package net.azarquiel.apprecetaexamen.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import net.azarquiel.apprecetaexamen.screens.RecetaDetail
import net.azarquiel.apprecetaexamen.screens.RecetasScreen
import net.azarquiel.apprecetaexamen.viewmodel.MainViewModel


@Composable
fun AppNavigation(viewModel: MainViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = AppScreens.RecetasScreen.route){
        composable(AppScreens.RecetasScreen.route){
            RecetasScreen(navController, viewModel)
        }
        composable(AppScreens.RecetaDetail.route) {
            RecetaDetail(navController, viewModel)
        }

    }
}
sealed class AppScreens(val route: String) {
    object RecetasScreen: AppScreens(route = "RecetasScreen")
    object RecetaDetail: AppScreens(route = "RecetaDetail")

}
