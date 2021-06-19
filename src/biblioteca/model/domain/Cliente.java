/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biblioteca.model.domain;

import java.io.Serializable;

public class Cliente implements Serializable{
    private int cod_Cliente;
    private String nome;
    private String telefone;

    public Cliente(){
    }
    
    public Cliente(int cod_Cliente, String nome) {
        this.cod_Cliente = cod_Cliente;
        this.nome = nome;
    }

    public int getCdCliente() {
        return cod_Cliente;
    }

    public void setCdCliente(int cod_Cliente) {
        this.cod_Cliente = cod_Cliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public String toString() {
        return this.nome;
    }
}
