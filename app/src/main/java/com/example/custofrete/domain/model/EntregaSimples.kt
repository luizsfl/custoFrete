package com.example.custofrete.domain.model

data class EntregaSimples(
    val totalKm:Double,
    val valorInformado:Double,
    val valorDespExtra :Double,
    val tipoCalc: Int,
    val valorTpCalc:Double,
):java.io.Serializable  {

    fun descricaoCalculo():String {
        return if (this.tipoCalc == 0) {
           "Km(${this.totalKm}) * valor(${this.valorInformado}) * (${this.valorTpCalc})" +
                   " =  ${this.valorCalculado() - this.valorDespExtra}"

        }else if (this.tipoCalc == 1) {
            "Km (${this.totalKm}) * R$(${this.valorInformado })  = ${this.totalKm * this.valorInformado}" +
                    "\r\n" +
                    "procentagem(${this.valorTpCalc}/100) *  ${this.totalKm * this.valorInformado} = ${(this.valorTpCalc/100) * this.totalKm * this.valorInformado}"
        }else{
            "Km(${this.totalKm}) * valor(${this.valorInformado}) = ${this.valorCalculado() - this.valorDespExtra}"
        }
    }

    fun valorCalculado():Double{
       return if (this.tipoCalc == 0) {
           ((this.valorInformado * this.totalKm) * this.valorTpCalc) + this.valorDespExtra
        }else if (this.tipoCalc == 1){
            (this.valorInformado * this.totalKm) * this.valorTpCalc/100 + (this.valorInformado * this.totalKm) + (this.valorDespExtra)
        }else{
           ((this.valorInformado * this.totalKm)  + this.valorDespExtra)
       }
    }
}
