/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biblioteca.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import biblioteca.model.domain.Genero;
import biblioteca.model.domain.Autor;
import biblioteca.model.domain.Livro;

/**
 *
 * @author Yesod
 */
public class LivroDAO {
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Livro livro) {
        String sql = "INSERT INTO livro(titulo, edicao, cod_genero_l, cod_autor_l) VALUES(?,?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, livro.getTitulo());
            stmt.setInt(2, livro.getEdicao());
            stmt.setInt(3, livro.getGenero().getCdGenero());
            stmt.setInt(4, livro.getAutor().getCdAutor());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(LivroDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean alterar(Livro livro) {
        String sql = "UPDATE livro SET titulo=?, edicao=?, cod_genero_l=?, cod_autor_l=? WHERE cod_livro=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, livro.getTitulo());
            stmt.setInt(2, livro.getEdicao());
            stmt.setInt(3, livro.getGenero().getCdGenero());
            stmt.setInt(4, livro.getAutor().getCdAutor());
            stmt.setInt(5, livro.getCdLivro());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(LivroDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean remover(Livro livro) {
        String sql = "DELETE FROM livro WHERE cod_livro=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, livro.getCdLivro());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(LivroDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<Livro> listar() {
        String sql = "SELECT * FROM livro";
        List<Livro> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Livro  livro  = new Livro();
                Genero genero = new Genero();
                Autor  autor  = new Autor ();
                livro.setCdLivro(resultado.getInt("cod_livro"));
                livro.setTitulo(resultado.getString("titulo"));
                livro.setEdicao(resultado.getInt("edicao"));
                genero.setCdGenero(resultado.getInt("cod_genero_l"));
                autor.setCdAutor(resultado.getInt("cod_autor_l"));

                //Obtendo os dados completos da Categoria associada ao Produto
                GeneroDAO generoDAO = new GeneroDAO();
                generoDAO.setConnection(connection);
                genero = generoDAO.buscar(genero);
                

                //Obtendo os dados completos da Categoria associada ao Produto
                AutorDAO autorDAO = new AutorDAO();
                autorDAO.setConnection(connection);
                autor = autorDAO.buscar(autor);
                
                livro.setGenero(genero);
                livro.setAutor(autor);
                
                retorno.add(livro);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LivroDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
    public List<Livro> listarPorAutor(Autor autor, Genero genero) {
        String sql = "SELECT * FROM livro WHERE cod_autor_l=?";
        List<Livro> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, autor.getCdAutor());
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Livro livro = new Livro();
                livro.setCdLivro(resultado.getInt("cod_livro"));
                livro.setTitulo(resultado.getString("titulo"));
                livro.setEdicao(resultado.getInt("edicao"));
                autor.setCdAutor(resultado.getInt("cod_autor_l"));
                livro.setAutor(autor);
                genero.setCdGenero(resultado.getInt("cod_genero_l"));
                livro.setGenero(genero);
                retorno.add(livro);
            }
        } catch (SQLException ex) {
            Logger.getLogger(GeneroDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public Livro buscar(Livro livro) {
        String sql = "SELECT * FROM livro WHERE cod_livro=?";
        Livro retorno = new Livro();
        Genero genero = new Genero();
        Autor  autor  = new Autor ();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, livro.getCdLivro());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                retorno.setCdLivro(resultado.getInt("cod_livro"));
                retorno.setTitulo(resultado.getString("titulo"));
                retorno.setEdicao(resultado.getInt("edicao"));
                genero.setCdGenero(resultado.getInt("cod_genero_l"));
                retorno.setGenero(genero);
                autor.setCdAutor(resultado.getInt("cod_autor_l"));
                retorno.setAutor(autor);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LivroDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
}
