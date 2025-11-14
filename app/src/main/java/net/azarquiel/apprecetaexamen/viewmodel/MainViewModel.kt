package net.azarquiel.apprecetaexamen.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import net.azarquiel.alltricks.util.Util
import net.azarquiel.apprecetaexamen.model.RecetaConIngredientes
import net.azarquiel.apprecetaexamen.model.RecetaIngrediente
import net.azarquiel.apprecetaexamen.view.MainActivity

class MainViewModel(activity: MainActivity) : ViewModel() {
    val activity = activity

    private var recetaViewModel: RecetaViewModel
    private val _recetas: MutableLiveData<List<RecetaConIngredientes>> = MutableLiveData()
    val recetas: LiveData<List<RecetaConIngredientes>> = _recetas

    private lateinit var recetaPulsada: RecetaConIngredientes

    var listaIconos = listOf(0,1,2)

    var indiceIcono: MutableLiveData<Int> = MutableLiveData(0)

    private val _cantidades: MutableLiveData<List<RecetaIngrediente>> = MutableLiveData()
    val cantidades: LiveData<List<RecetaIngrediente>> = _cantidades

    init {
        Util.inyecta(activity.baseContext, "recetas.db")

        recetaViewModel = ViewModelProvider(activity).get(RecetaViewModel::class.java)

        recetaViewModel.getRecetaWithIngredientes().observe(activity, Observer { recetas ->
            _recetas.value = recetas
        })

    }

    fun setRecetaPulsada(receta: RecetaConIngredientes) {
        recetaPulsada = receta
    }

    fun getRecetaPulsada(): RecetaConIngredientes {
        return recetaPulsada
    }

    fun iconoSiguiente() {
        val current = indiceIcono.value ?: 0
        indiceIcono.value = (current + 1) % listaIconos.size
    }

    fun getRecetaConCantidades(idMeal: Int) {
        recetaViewModel.getRecetaWithCantidades(idMeal).observe(activity, Observer { cantidades ->
            _cantidades.value = cantidades
        })
    }


}