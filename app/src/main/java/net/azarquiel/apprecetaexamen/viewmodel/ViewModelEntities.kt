package net.azarquiel.apprecetaexamen.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import net.azarquiel.apprecetaexamen.model.RecetaConIngredientes
import net.azarquiel.apprecetaexamen.model.RecetaIngrediente
import net.azarquiel.apprecetaexamen.repositories.RecetaRepository

class RecetaViewModel (application: Application) : AndroidViewModel(application) {

    private var repository = RecetaRepository(application)

    fun getRecetaWithIngredientes(): LiveData<List<RecetaConIngredientes>> {
        return repository.getRecetaWithIngrediente()
    }

    fun getRecetaWithCantidades(idMeal : Int): LiveData<List<RecetaIngrediente>> {
        return repository.getRecetasConCantidades(idMeal)
    }
}