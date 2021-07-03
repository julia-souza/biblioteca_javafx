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
public class Venda implements Serializable{
    private int cdVenda;
    private double valor;
    private LocalDate data;
    private List<ItensDeVenda> itensDeVenda;
    private Cliente cliente;
    private Livro livro;

    public Venda() {
    }

    public Venda(int cdvenda, LocalDate data, double valor) {
        this.cdVenda = cdvenda;
        this.valor = valor;
        this.data = data;
    }
    
    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public int getCdVenda() {
        return cdVenda;
    }

    public void setCdVenda(int cdVenda) {
        this.cdVenda = cdVenda;
    }
    public double getValor() {
        return valor;
    }
    public List<ItensDeVenda> getItensDeVenda() {
        return itensDeVenda;
    }

    public void setItensDeVenda(List<ItensDeVenda> itensDeVenda) {
        this.itensDeVenda = itensDeVenda;
    }
    public void setValor(double valor) {
        this.valor = valor;
    }
    public Cliente getCliente() {
        return cliente;
    }
    
    public Livro getLivro(){
        return livro;
    }
    
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    public void setLivro(Livro livro) {
        this.livro = livro;
    }
}
