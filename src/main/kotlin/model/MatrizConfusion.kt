package org.example.model

class MatrizConfusion(
    listaActuales : IntArray,
    listaPrediccion : IntArray
) {
    val listaActuales : IntArray
    val listaPrediccion : IntArray

    fun conteo
    init {
        this.listaActuales = listaActuales.copyOf()
        this.listaPrediccion = listaPrediccion.copyOf()
    }
}