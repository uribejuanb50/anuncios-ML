package org.example.data

object Factory {
    fun construirPerfil(listaAtributos : List<String>) : Perfil{

        val age : Int = listaAtributos[2].toInt()
        val estimatedSalary : Double = listaAtributos[3].toDouble()
        val purchased : Int = listaAtributos[4].toInt()

        val perfil = Perfil(age = age, estimatedSalary = estimatedSalary, purchased = purchased)

        return perfil
    }

    fun construirPerfilNormalizado(age : Double, estimatedSalary : Double, purchased : Int ) : PerfilNormalizado {
        return PerfilNormalizado(age, estimatedSalary, purchased)
    }
}