package net.azarquiel.apprecetaexamen.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction


@Dao
interface RecetaDao {
    @Transaction
    @Query("SELECT * FROM receta")
    fun getRecetasWithIngredientes(): LiveData<List<RecetaConIngredientes>>
    @Query("SELECT * FROM  receta_ingrediente where receta_id = :idMeal")
    fun getRecetasWithCantidades(idMeal: Int): LiveData<List<RecetaIngrediente>>
}

