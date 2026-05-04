package org.example.model

import org.example.data.PerfilNormalizado
import org.example.data.Perfil
import org.example.data.PerfilRepository
import smile.data.DataFrame

//Usamos Smile para hacer arboles de decision
class SmileML(
    listaTraining : List<PerfilNormalizado>,
    listaTest : List<PerfilNormalizado>
){

    fun SmileML(nombreArchivo : String){
        val listaPerfiles :
    }


    //pasar de list a arraylist de objetos perfil normizado
    fun parsearListToArrayList(listaPerfilesNormalizados : List<PerfilNormalizado>) : ValoresModelo{

        //genera una matriz de doubles no de Doubles en términos Java, más optimizados
        val x : Array<DoubleArray> = listaPerfilesNormalizados.map{ perfil ->
            doubleArrayOf(perfil.age, perfil.estimatedSalary)
        }.toTypedArray()

        //genera un arreglo de ints no de Intes en términos Java, más optimizado
        val y : IntArray = listaPerfilesNormalizados.map{ perfil ->
            perfil.purchased
        }.toIntArray()

        val valoresModelo : ValoresModelo = ValoresModelo(x,y)

        return valoresModelo.copy()
    }
}