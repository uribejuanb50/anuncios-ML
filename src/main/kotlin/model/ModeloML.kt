package org.example.model

import org.example.data.PerfilNormalizado

class ModeloML {

    //pasar de list a arraylist de objetos perfil normizado
    fun parsearListToArrayList(listaPerfilesNormalizados : List<PerfilNormalizado>) : ValoresModelo{

        val x : Array<DoubleArray> = listaPerfilesNormalizados.map{ perfil ->
            doubleArrayOf(perfil.age, perfil.estimatedSalary)
        }.toTypedArray()

        val y : IntArray = listaPerfilesNormalizados.map{ perfil ->
            perfil.purchased
        }.toIntArray()

        val valoresModelo : ValoresModelo = ValoresModelo(x,y)

        return valoresModelo.copy()
    }
}