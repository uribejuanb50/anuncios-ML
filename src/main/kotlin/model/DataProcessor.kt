package org.example.model

import org.example.data.Perfil
object DataProcessor {

    //sacar dos conjuntos a partir de uno dado un ratio (recibe una lista inmutable)
    fun dividirConjunto(listaPerfiles : List<Perfil>, ratio : Double) : Pair<List<Perfil>,List<Perfil> {

        if (ratio < 0 || ratio > 1) throw IllegalArgumentException("El ratio debe estar entre 0 y 1")

        val pivote = (listaPerfiles.size * ratio).toInt()

        val listaTomados = listaPerfiles.take(pivote)
        val listaResiduos = listaPerfiles.drop(pivote)

        return Pair(listaTomados, listaResiduos)
    }

    fun calcularMedia(listaPerfiles : List<Perfil>) : Double{
        if(listaPerfiles.isEmpty()) return 0.0

    }
}