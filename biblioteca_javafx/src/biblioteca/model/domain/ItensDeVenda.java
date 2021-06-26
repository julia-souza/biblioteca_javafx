/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biblioteca.model.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
/**
 *
 * @author Yesod
 */

public class ItensDeVenda implements Serializable{
    private int cod_itens_venda;
    private double valor_item;
    private int quantidade;
    private Livro livro;
    private Venda venda;

    public ItensDeVenda(){
    }

    public int getCdItemDeVenda() {
        return cod_itens_venda;
    }

    public void setCdItemDeVenda(int cod_itens_venda) {
        this.cod_itens_venda = cod_itens_venda;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    
    public double getValor() {
        return valor_item;
    }

    public void setValor(double valor_item) {
        this.valor_item = valor_item;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }
}
