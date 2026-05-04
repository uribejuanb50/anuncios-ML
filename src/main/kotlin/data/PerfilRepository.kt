package org.example.data

object PerfilRepository {

    //Se le entrega un archivo CSV con el formato "/ + nombreArchivo + .scv" y devuelve una lista
    //del data class Perfil()
    fun leerCSV(nombreArchivo : String) : List<Perfil>{

        //usa paths, no rutas directas
        val inputStream = this::class.java.getResourceAsStream(nombreArchivo)?:
            throw IllegalArgumentException("Archivo no encontrado")

        val listaPerfiles : MutableList<Perfil> = mutableListOf()

        //.drop(1) para ignorar el header del doc
        inputStream.bufferedReader().useLines { lineas ->
            lineas.drop(1).forEach { linea ->
                val lista : List<String> = linea.split(",")

                val perfil = Factory.construirPerfil(lista)

                listaPerfiles.add(perfil)
            }
        }

        //lo devuelve inmutable
        return listaPerfiles.toList()
    }
}