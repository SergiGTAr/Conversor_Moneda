package facci.com.conversorac;

import java.io.Serializable;

public class Currency implements Serializable {
    String name;
    double valor;

    public Currency (String name, double valor){
        this.name = name;
        this.valor = valor;
    }

    public String toString(){
        return "Currency: " + name + "  Valor: " + valor;
    }

}

