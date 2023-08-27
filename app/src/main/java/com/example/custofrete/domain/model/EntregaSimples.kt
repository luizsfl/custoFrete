package com.example.custofrete.domain.model

data class EntregaSimples constructor(
    var totalKm:Double= 0.0,
    var valorInformado:Double= 0.0,
    var valorDespExtra :Double= 0.0,
    var tipoCalc: Int=0,
    var valorTpCalc:Double= 0.0,
    var idDocument: String = "",
    var titulo: String = "",
    var valorEntrega :Double = 0.0,
    var idUsuario: String = "",
    var valorEntregaCalculado :Double = 0.0
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
