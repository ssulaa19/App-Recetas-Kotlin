package net.azarquiel.apprecetaexamen.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Receta::class, Categoria::class, Ingrediente::class, Area::class , RecetaIngrediente::class], version = 1)
abstract class RecetaDB: RoomDatabase() {
    abstract fun recetaDao(): RecetaDao
    companion object {
        // Singleton prevents multiple instances of database opening at the same time.
        @Volatile
        private var INSTANCE:  RecetaDB? = null

        fun getDatabase(context: Context): RecetaDB {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RecetaDB::class.java,   "recetas.db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}