package org.example.model

import org.example.data.PerfilNormalizado
import org.example.data.Perfil
import org.example.data.PerfilRepository
import smile.data.DataFrame
import java.time.Period

//Usamos Smile para hacer arboles de decision
class SmileML(nombreArchivo : String){

    val listaTraining : List<PerfilNormalizado>
    val listaTest : List<PerfilNormalizado>

    //pasar de list a arraylist de objetos perfil normizado
    fun parsearListToArrayList(listaPerfilesNormalizados : List<PerfilNormalizado>) : ValoresModelo{

        //genera una matriz de doubles no de Doubles en términos Java, más optimizados
        val x : Array<DoubleArray> = listaPerfilesNormalizados.map{ perfil ->
            doubleArrayOf(perfil.age, perfil.estimatedSalary)
        }.toTypedArray()

        //genera un arreglo de ints no de Ints en términos Java, más optimizado
        val y : IntArray = listaPerfilesNormalizados.map{ perfil ->
            perfil.purchased
        }.toIntArray()

        val valoresModelo : ValoresModelo = ValoresModelo(x,y)

        return valoresModelo.copy()
    }

    init{
        val listaPerfiles : List<Perfil> = PerfilRepository.leerCSV(nombreArchivo)
        val resultado = DataProcessor.dividirConjunto(listaPerfiles, 0.75)

        val listaTrainingCruda = resultado.first
        val listaTestCruda = resultado.second

        val estadisticas = DataProcessor.calcularEstadisticas(listaTrainingCruda)

        this.listaTraining = DataProcessor.normalizar(listaTrainingCruda, estadisticas)
        this.listaTest = DataProcessor.normalizar(listaTestCruda, estadisticas)
    }
}