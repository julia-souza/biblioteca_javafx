/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biblioteca.model.domain;

import java.io.Serializable;
/**
 *
 * @author Yesod
 */
public class Genero implements Serializable{
    private int cod_Genero;
    private String tipo_Genero;

    public Genero(){
    }
    
    public Genero(int cod_Genero, String tipo_Genero) {
        this.cod_Genero = cod_Genero;
        this.tipo_Genero = tipo_Genero;
    }

    public int getCdGenero() {
        return cod_Genero;
    }

    public void setCdGenero(int cod_Genero) {
        this.cod_Genero = cod_Genero;
    }

    public String getTipo_Genero() {
        return tipo_Genero;
    }

    public void setTipo_Genero(String tipo_Genero) {
        this.tipo_Genero = tipo_Genero;
    }

    @Override
    public String toString() {
        return this.tipo_Genero;
    }
}
