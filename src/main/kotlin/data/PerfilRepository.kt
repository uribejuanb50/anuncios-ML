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
                val lista : List<String> = this.separarPorCaracter(linea, ",")

                val perfil = Factory.construirPerfil(lista)

                listaPerfiles.add(perfil)
            }
        }

        return listaPerfiles
    }

    //devuelve una lista a partir de un string separado por un caracter
    fun separarPorCaracter(strSepara : String, caracter : String) : List<String>{
        return strSepara.split(caracter)
    }
}