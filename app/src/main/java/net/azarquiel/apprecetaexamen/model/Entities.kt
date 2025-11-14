package net.azarquiel.apprecetaexamen.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.io.Serializable


@Entity(tableName = "area")
data class Area(@PrimaryKey
                @ColumnInfo(name = "id") // nombre columna en tabla
                var id: Int=0,          // atributo en entity
                @ColumnInfo(name = "name")
                var name:String="",
): Serializable

@Entity(tableName = "categoria")
data class Categoria(@PrimaryKey
                     @ColumnInfo(name = "id") // nombre columna en tabla
                     var id: Int=0,          // atributo en entity
                     @ColumnInfo(name = "name")
                     var name:String="",
): Serializable
@Entity(tableName = "ingrediente")
data class Ingrediente(@PrimaryKey
                       @ColumnInfo(name = "id") // nombre columna en tabla
                       var id: Int=0,          // atributo en entity
                       @ColumnInfo(name = "name")
                       var name:String="",
): Serializable

@Entity(tableName = "receta",
    foreignKeys = [ForeignKey(entity = Categoria::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("categoria_id")),
        ForeignKey(entity = Area::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("area_id"))])
data class Receta(@PrimaryKey
                  @ColumnInfo(name = "idMeal") // nombre en la tabla
                  var idMeal: Int=0,          // atributo en entity
                  @ColumnInfo(name = "meal")
                  var meal:String="",
                  @ColumnInfo(name = "instructions")
                  var instructions:String="",
                  @ColumnInfo(name = "categoria_id") // nombre en la tabla
                  var categoria_id: Int=0,
                  @ColumnInfo(name = "area_id") // nombre en la tabla
                  var area_id: Int=0,   // atributo en entity
                  @ColumnInfo(name = "image")
                  var image:String="",

                  ): Serializable

@Entity(tableName = "receta_ingrediente",
    // Definir la clave primaria compuesta
    primaryKeys = ["receta_id", "ingrediente_id"],
    foreignKeys =[
        ForeignKey(
            entity = Receta::class,
            parentColumns = arrayOf("idMeal"),
            childColumns = arrayOf("receta_id")),
        ForeignKey(
            entity = Ingrediente::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("ingrediente_id"))
    ])
data class RecetaIngrediente(
    var receta_id: Int,
    var ingrediente_id: Int,
    var cantidad:String=""
):Serializable

data class RecetaCompleta(
    @Embedded
    val receta: Receta,
    @Relation(
        parentColumn = "area_id",
        entityColumn = "id"
    )
    val area: Area,
    @Relation(
        parentColumn = "categoria_id",
        entityColumn = "id"
    )
    val categoria: Categoria
): Serializable

data class RecetaConIngredientes(
    @Embedded
    val recetaCompleta: RecetaCompleta,
    @Relation(
        parentColumn = "idMeal",
        entity = Ingrediente::class,
        entityColumn = "id",
        associateBy = Junction(
            RecetaIngrediente::class,
            parentColumn = "receta_id",
            entityColumn = "ingrediente_id"
        )
    )
    val ingredientes: List<Ingrediente>
)

