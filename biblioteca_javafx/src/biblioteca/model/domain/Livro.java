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
public class Livro implements Serializable{
    private int cdLivro;
    private String titulo;
    private int edicao;
    private Autor autor;
    private Genero genero;

    public Livro() {
    }

    public Livro(int cdLivro, String titulo, int edicao) {
        this.cdLivro = cdLivro;
        this.titulo = titulo;
        this.edicao = edicao;
    }

    public int getCdLivro() {
        return cdLivro;
    }

    public void setCdLivro(int cdLivro) {
        this.cdLivro = cdLivro;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getEdicao() {
        return edicao;
    }

    public void setEdicao(int edicao) {
        this.edicao = edicao;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }
    
    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    @Override
    public String toString() {
        return this.titulo;
    }
    
}
