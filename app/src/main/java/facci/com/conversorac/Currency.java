package facci.com.conversorac;

import java.io.Serializable;

public class Currency implements Serializable {
    String moneda;
    double valor;

    public Currency (String moneda, double valor){
        this.moneda = moneda;
        this.valor = valor;
    }

    public String toString(){
        return "Currency: " + moneda + "  Valor: " + valor;
    }

}

