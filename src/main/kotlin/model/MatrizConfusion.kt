package org.example.model

class MatrizConfusion(
    listaActuales : IntArray,
    listaPrediccion : IntArray
) {
    val listaActuales : IntArray
    val listaPrediccion : IntArray

    val strMC : String
    val exactitud : Double

    //hace la comparación iterando al tiempo entre dos listas y cataloga por cuadrantes su concordancia
    fun conteoCasosMC() : DatosMC {
        val listaActuales = this.listaActuales.copyOf()
        val listaPrediccion = this.listaPrediccion.copyOf()

        var falsosPositivos : Int = 0
        var falsosNegativos : Int = 0
        var verdaderosPositivos : Int = 0
        var verdaderosNegativos : Int = 0

        listaActuales.zip(listaPrediccion).forEach{ (actual, prediccion) ->
            if(actual == 1 && prediccion == 1) verdaderosPositivos++
            else if(actual == 0 && prediccion == 0) verdaderosNegativos++
            else if(actual == 0 && prediccion == 1) falsosPositivos++
            else if(actual == 1 && prediccion == 0) falsosNegativos++
            else throw IllegalArgumentException("No es un campo válido o $actual o $prediccion")
        }

        return DatosMC(verdaderosPositivos, verdaderosNegativos, falsosPositivos, falsosNegativos)
    }

    fun strMatrizConfusion(datosMC : DatosMC) : String {
        var str : String = "         MATRIZ DE CONFUSION       " + "\n"
        str +=              "tamaños -> lista REALES: ${listaActuales.size} , lista PREDICCION: ${listaPrediccion.size}" + "\n"
        str +=              "                      casos REALES" + "\n"
        str +=              "                       SI      NO " + "\n"
        str +=              "casos             SI   ${datosMC.verdaderosPositivos}      ${datosMC.falsosPositivos}" + "\n"
        str +=              "PREDICCION        NO   ${datosMC.falsosNegativos}       ${datosMC.verdaderosNegativos}"

        return str
    }

    fun calcularExactitud(datosMC : DatosMC) : Double {
        val sumDiag : Int = datosMC.verdaderosPositivos + datosMC.verdaderosNegativos
        val sumTotal : Int = datosMC.verdaderosPositivos + datosMC.verdaderosNegativos + datosMC.falsosPositivos + datosMC.falsosNegativos

        return (sumDiag.toDouble()/sumTotal.toDouble())
    }
    init {
        this.listaActuales = listaActuales.copyOf()
        this.listaPrediccion = listaPrediccion.copyOf()

        val datosMC = conteoCasosMC()
        this.strMC = strMatrizConfusion(datosMC)
        this.exactitud = calcularExactitud(datosMC)
    }
}