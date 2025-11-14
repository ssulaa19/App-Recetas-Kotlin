package net.azarquiel.apprecetaexamen.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import net.azarquiel.apprecetaexamen.model.RecetaConIngredientes
import net.azarquiel.apprecetaexamen.model.RecetaDB
import net.azarquiel.apprecetaexamen.model.RecetaIngrediente

class RecetaRepository(application: Application) {

    val recetaDAO = RecetaDB.getDatabase(application).recetaDao()
    // select
    fun getRecetaWithIngrediente(): LiveData<List<RecetaConIngredientes>> {
        return recetaDAO.getRecetasWithIngredientes()
    }

    fun getRecetasConCantidades(idMeal : Int): LiveData<List<RecetaIngrediente>> {
        return recetaDAO.getRecetasWithCantidades(idMeal)
    }
}