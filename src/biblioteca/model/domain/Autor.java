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
public class Autor implements Serializable{
    private int cod_Autor;
    private String nome;

    public Autor(){
    }
    
    public Autor(int cod_Autor, String nome) {
        this.cod_Autor = cod_Autor;
        this.nome = nome;
    }

    public int getCdAutor() {
        return cod_Autor;
    }

    public void setCdAutor(int cod_Autor) {
        this.cod_Autor = cod_Autor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return this.nome;
    }
}
