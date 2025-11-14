package net.azarquiel.apprecetaexamen.screens

import net.azarquiel.apprecetaexamen.R
import net.azarquiel.apprecetaexamen.viewmodel.MainViewModel

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import net.azarquiel.apprecetaexamen.model.RecetaConIngredientes


@Composable
fun RecetasScreen(navController: NavHostController, viewModel: MainViewModel) {
    val textState = remember { mutableStateOf(TextFieldValue("")) }
    val icono = viewModel.indiceIcono.observeAsState()

    Scaffold(
        topBar = { RecetasTopBar(viewModel, textState,icono) },
        content = { padding ->
            RecetasContent(padding, viewModel,navController,textState,icono)
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecetasTopBar(
    viewModel: MainViewModel,
    textState: MutableState<TextFieldValue>,
    icono: State<Int?>
) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(4.dp)
            ) {
                Text(text = "Recetas", Modifier
                    .padding(end = 10.dp))

                Box(modifier = Modifier.weight(1f)) {
                    SearchView(textState, icono)
                }
                Icon(
                    imageVector = when (icono.value) {
                        0 -> Icons.Default.Edit
                        1 -> Icons.Default.List
                        else -> Icons.Default.Place
                    },
                    contentDescription = "Filter Icon",
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .width(30.dp)
                        .height(30.dp)
                        .clickable {
                            viewModel.iconoSiguiente()
                        },
                    tint = Color.Black
                )

            }
        },
        colors = topAppBarColors(
            containerColor = colorResource(R.color.azulclaro),
            titleContentColor = Color.Black
        ),

    )
}
@Composable
fun RecetasContent(
    padding: PaddingValues,
    viewModel: MainViewModel,
    navController: NavHostController,
    textState: MutableState<TextFieldValue>,
    icono: State<Int?>
) {
    val recetas = viewModel.recetas.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .background(colorResource(R.color.azuloscuro)),
    )
    {
        lazyColumnsFiltradas(icono, recetas, textState,viewModel,navController)
    }
}

@Composable
fun lazyColumnsFiltradas(
    icono: State<Int?>,
    recetas: State<List<RecetaConIngredientes>?>,
    textState: MutableState<TextFieldValue>,
    viewModel: MainViewModel,
    navController: NavHostController
) {
    when(icono.value){
        0-> LazyColumn {
            items(recetas.value?.filter {
                it.recetaCompleta.receta.meal
                    .contains(textState.value.text, ignoreCase = true)
            } ?: emptyList()) { receta ->
                recetaItem(receta,viewModel,navController)
            }
        }

        1-> LazyColumn {
            items(recetas.value?.filter {
                it.recetaCompleta.categoria.name
                    .contains(textState.value.text, ignoreCase = true)
            } ?: emptyList()) { receta ->
                recetaItem(receta,viewModel,navController)
            }
        }

        2-> LazyColumn {
            items(recetas.value?.filter {
                it.recetaCompleta.area.name
                    .contains(textState.value.text, ignoreCase = true)
            } ?: emptyList()) { receta ->
                recetaItem(receta,viewModel,navController)
            }
        }
    }
}

@Composable
fun recetaItem(
    receta: RecetaConIngredientes,
    viewModel: MainViewModel,
    navController: NavHostController
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp, 3.dp)
            .shadow(4.dp)
            .clickable(onClick = {
                viewModel.setRecetaPulsada(receta)
                navController.navigate("RecetaDetail")
            })
        ,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.azulclaro),
            contentColor = colorResource(R.color.azuloscuro)),
    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(4.dp)) {
            Column (
                modifier = Modifier.weight(1f).padding(4.dp)
            ) {
                AsyncImage(
                    model = "http://www.ies-azarquiel.es/paco/apirecetas/img/${receta.recetaCompleta.receta.image}",
                    contentDescription = receta.recetaCompleta.receta.meal,
                    modifier = Modifier.clip(RoundedCornerShape(12.dp)),
                )
            }
            Column (modifier = Modifier.weight(2f).padding(4.dp)) {
                Row {
                    Text(text = receta.recetaCompleta.receta.meal,
                        modifier = Modifier
                            .align(Alignment.CenterVertically),
                        textAlign = TextAlign.Start,
                        color = Color.Red,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Row {
                    Text(text ="Area ${receta.recetaCompleta.area.name}" ,
                        modifier = Modifier
                            .align(Alignment.CenterVertically),
                        textAlign = TextAlign.Start,
                        color = Color.Black
                    )
                    Icon(
                        Icons.Default.Place,
                        contentDescription = "Area Icon",
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(start = 4.dp),
                        tint = Color.LightGray
                    )
                }

                Row {
                    Text(text = receta.recetaCompleta.categoria.name,
                        modifier = Modifier
                            .align(Alignment.CenterVertically),
                        textAlign = TextAlign.Start,
                        color = Color.Black
                    )

                }
            }
        }
    }
}


// Filtrar

@Composable
fun SearchView(state: MutableState<TextFieldValue>, icono: State<Int?>) {
    BasicTextField(
        value = state.value,
        onValueChange = { value ->
            state.value = value
        },
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(colorResource(R.color.azulclaro))
            .padding(16.dp, 3.dp),
        maxLines = 1,
        singleLine = true,
        textStyle = TextStyle(
            color = colorResource(R.color.azuloscuro), fontSize = 20.sp,
        ),
        decorationBox = { innerTextField ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (state.value.text.isEmpty()) {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "Search Icon",
                        Modifier.wrapContentSize(),
                        tint = colorResource(R.color.azuloscuro)
                    )
                    Text(
                        text = when(icono.value)
                        {
                            0-> "Buscar por Nombre"
                            1-> "Buscar por Categoria"
                            else-> "Buscar por Area"
                        },
                        color = colorResource(R.color.azuloscuro),
                        fontSize = 16.sp
                    )
                }
            }
            innerTextField()
        }
    )
}


