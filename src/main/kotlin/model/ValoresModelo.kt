package org.example.model

data class ValoresModelo(
    val matriz : Array<DoubleArray>,
    val arreglo : IntArray
)

fun ValoresModelo.copy() : ValoresModelo{
    val newMatriz = matriz.map{ it.copyOf() }.toTypedArray()
    val newArreglo = arreglo.copyOf()

    return ValoresModelo(newMatriz, newArreglo)
}