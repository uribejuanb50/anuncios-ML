package org.example.model

import org.example.data.Factory
import org.example.data.Perfil
import org.example.data.PerfilNormalizado

import kotlin.math.pow
import kotlin.random.Random
object DataProcessor {

    //sacar dos conjuntos a partir de uno dado un ratio (recibe una lista inmutable)
    fun dividirConjunto(listaPerfiles : List<Perfil>, ratio : Double) : Pair<List<Perfil>,List<Perfil>> {

        if (ratio < 0 || ratio > 1) throw IllegalArgumentException("El ratio debe estar entre 0 y 1")

        val semilla = Random(42)
        val mezclados = listaPerfiles.shuffled(semilla)
        val pivote = (listaPerfiles.size * ratio).toInt()

        val listaTomados = mezclados.take(pivote)
        val listaResiduos = mezclados.drop(pivote)

        return Pair(listaTomados, listaResiduos)
    }
    @JvmName("calcularMediaDouble")
    fun calcularMedia(lista : List<Double>) : Double{
        if(lista.isEmpty()) return 0.0

        val suma : Double = lista.sum()

        return suma / lista.size
    }
    @JvmName("calcularMediaInt")
    fun calcularMedia(lista : List<Int>) : Double{
        if(lista.isEmpty()) return 0.0

        val suma : Double = lista.sum().toDouble()

        return suma / lista.size
    }

    //calcula sqrt((sum(pow2(x - ẍ))/n) que es la desviacion estandar
    @JvmName("calcularDesviacionEstandarDouble")
    fun calcularDesviacionEstandar(lista : List<Double>, media : Double) : Double{
        if(lista.isEmpty()) return 0.0

        var suma : Double = 0.0

        for(digito in lista){
            val sumaAux : Double = digito - media
            suma += sumaAux.pow(0.5)
        }

        val division : Double = suma / lista.size

        return division.pow(1/2)
    }
    //lo mismo pero recibe int
    @JvmName("calcularDesviacionEstandarInt")
    fun calcularDesviacionEstandar(lista : List<Int>, media : Double) : Double{
        if(lista.isEmpty()) return 0.0

        var suma : Double = 0.0

        for(digito in lista){
            val sumaAux : Double = digito - media
            suma += sumaAux.pow(2)
        }

        val division : Double = suma / lista.size

        return division.pow(1/2)
    }

    fun calcularEstadisticas(listaPerfiles : List<Perfil>) : Estadisticas{
        if(listaPerfiles.isEmpty()) throw IllegalArgumentException("La lista está vacía")

        val listaAge = listaPerfiles.map{ it.age }
        val listaEstimatedSalary = listaPerfiles.map{ it.estimatedSalary }

        val mediaAge = this.calcularMedia(listaAge)
        val mediaEstimatedSalary = this.calcularMedia(listaEstimatedSalary)

        val deAge = this.calcularDesviacionEstandar(listaAge, mediaAge)
        val deEstimatedSalary = this.calcularDesviacionEstandar(listaEstimatedSalary, mediaEstimatedSalary)

        return Estadisticas(mediaAge, mediaEstimatedSalary, deAge, deEstimatedSalary)
    }

    //hace (x - ẍ)/desv.estanda
    @JvmName("normalizarUnitario")
    fun normalizar(valor : Double, media : Double, desvEstandar : Double) : Double {
        if(desvEstandar == 0.0) throw IllegalArgumentException("Desviación estandar en 0")
        return (valor - media) / desvEstandar
    }
    //hace por iteracion : (x - ẍ) / des. estandar
    @JvmName("normalizarLista")
    fun normalizar(lista : List<Perfil>, estadisticas : Estadisticas) : List<PerfilNormalizado>{

        val listaNormalizada = lista.map{ perfil ->
            val age : Double = normalizar(perfil.age.toDouble(), estadisticas.mediaAge, estadisticas.desviacionEstandarAge)
            val estimatedSalary : Double = normalizar(perfil.estimatedSalary, estadisticas.mediaEstimatedSalary, estadisticas.desviacionEstandarEstimatedSalary)

            Factory.construirPerfilNormalizado(age, estimatedSalary, perfil.purchased)
        }

        return listaNormalizada
    }
}