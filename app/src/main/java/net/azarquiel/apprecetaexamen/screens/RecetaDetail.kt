package net.azarquiel.apprecetaexamen.screens

import android.util.Log
import net.azarquiel.apprecetaexamen.R
import net.azarquiel.apprecetaexamen.viewmodel.MainViewModel

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import net.azarquiel.apprecetaexamen.model.RecetaConIngredientes


@Composable
fun RecetaDetail(navController: NavHostController, viewModel: MainViewModel) {
    Scaffold(
        topBar = { RecetaDetailTopBar(viewModel, viewModel.getRecetaPulsada()) },
        content = { padding ->
            RecetaDetailContent(padding, viewModel,navController)
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecetaDetailTopBar(viewModel: MainViewModel, receta : RecetaConIngredientes) {
    TopAppBar(
        title = { Text(text = receta.recetaCompleta.receta.meal) },
        colors = topAppBarColors(
            containerColor = colorResource(R.color.azulclaro),
            titleContentColor = Color.Black
        ),
        actions = {

        }
    )
}
@Composable
fun RecetaDetailContent(
    padding: PaddingValues,
    viewModel: MainViewModel,
    navController: NavHostController
) {
    val recetaPulsada = viewModel.getRecetaPulsada()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .background(Color.White)
            .verticalScroll(rememberScrollState()),
    )
    {

        recetaDetail(recetaPulsada,viewModel)
    }
}


@Composable
fun recetaDetail(
    receta: RecetaConIngredientes,
    viewModel: MainViewModel
) {
    viewModel.getRecetaConCantidades(receta.recetaCompleta.receta.idMeal)

    val cantidades =  viewModel.cantidades.observeAsState()

    Column (
        modifier = Modifier.fillMaxWidth()
            .background(colorResource(R.color.moradoclaro))
            .padding(10.dp)
    ) {
        Row (
        ) {
            AsyncImage(
                model = "http://www.ies-azarquiel.es/paco/apirecetas/img/${receta.recetaCompleta.receta.image}",
                contentDescription = receta.recetaCompleta.receta.meal,
                modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(7.dp)),
                contentScale = ContentScale.FillWidth
            )
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp, 3.dp)
            ,
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = colorResource(R.color.azulclaro),
                contentColor = colorResource(R.color.azuloscuro)),
        ) {
            Column {
                Column (modifier = Modifier.padding(5.dp)) {
                    Text(
                        receta.recetaCompleta.receta.meal,
                        fontWeight = FontWeight.Bold,
                        color = Color.Red,
                        fontSize = 24.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )

                    Text(
                        "· Area: ${receta.recetaCompleta.area.name}",
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.azuloscuro),
                    )

                    Text(
                        "· Categoria: ${receta.recetaCompleta.categoria.name}",
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.azuloscuro),
                    )
                }
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp, 3.dp)
            ,
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = colorResource(R.color.azulclaro),
                contentColor = colorResource(R.color.azuloscuro)),
        ) {
            Column(modifier = Modifier.padding(11.dp)) {
                Text(
                    text = "Ingredientes:",
                    modifier = Modifier
                        .padding(bottom = 8.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.Red
                )
                for (ingrediente in receta.ingredientes) {
                    Text(
                        text = "· ${ingrediente.name} (${cantidades.value?.find { it.ingrediente_id == ingrediente.id }?.cantidad})",
                        modifier = Modifier
                            .padding(bottom = 4.dp).border(1.dp, Color.Gray, RoundedCornerShape(4.dp)).padding(4.dp)
                    )
                }
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp, 3.dp)
            ,
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = colorResource(R.color.azulclaro),
                contentColor = colorResource(R.color.azuloscuro)),
        ) {
            Column(
                modifier = Modifier
                    .padding(11.dp)
            ) {
                Text(
                    text = "Instrucciones:",
                    modifier = Modifier
                        .padding(top = 16.dp, bottom = 8.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.Red
                )
                Text(
                    text = receta.recetaCompleta.receta.instructions
                )
            }

        }


    }
}


