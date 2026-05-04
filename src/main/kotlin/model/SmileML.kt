package org.example.model

import org.example.data.PerfilNormalizado
import org.example.data.Perfil
import org.example.data.PerfilRepository
import smile.classification.DecisionTree
import smile.data.DataFrame
import smile.data.formula.Formula
import smile.data.vector.DoubleVector
import smile.data.vector.IntVector
import java.time.Period

//Usamos Smile para hacer arboles de decision
class SmileML(nombreArchivo : String){

    val listaTraining : List<PerfilNormalizado>
    val listaTest : List<PerfilNormalizado>

    val modelo : DecisionTree

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

    fun generarDataFrame(valoresModelo : ValoresModelo) : DataFrame {

        val nFilas = valoresModelo.matriz.size
        val nColumnas = valoresModelo.matriz[0].size

        val columnas = (0 until nColumnas).map{ colIndex ->
            val columna = DoubleArray(nFilas){ row->
                valoresModelo.matriz[row][colIndex]
            }
            DoubleVector.of("feature_$colIndex", columna)
        }

        val target = IntVector.of("purchased", valoresModelo.arreglo)

        return DataFrame.of(*(columnas + target).toTypedArray())
    }

    init{
        val listaPerfiles : List<Perfil> = PerfilRepository.leerCSV(nombreArchivo)
        val resultado = DataProcessor.dividirConjunto(listaPerfiles, 0.75)

        val listaTrainingCruda = resultado.first
        val listaTestCruda = resultado.second

        val estadisticas = DataProcessor.calcularEstadisticas(listaTrainingCruda)

        this.listaTraining = DataProcessor.normalizar(listaTrainingCruda, estadisticas)
        this.listaTest = DataProcessor.normalizar(listaTestCruda, estadisticas)

        val valoresModelo : ValoresModelo = parsearListToArrayList(this.listaTraining)

        val formula = Formula.lhs("purchased")
        val dataframe = generarDataFrame(valoresModelo)
        this.modelo = DecisionTree.fit(formula, dataframe)

        println(formula)
    }
}