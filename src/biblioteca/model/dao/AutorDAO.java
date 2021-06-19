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
import biblioteca.model.domain.Autor;
/**
 *
 * @author Yesod
 */
public class AutorDAO {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Autor autor) {
        String sql = "INSERT INTO autor(nome_autor) VALUES(?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, autor.getNome());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(AutorDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean alterar(Autor autor) {
        String sql = "UPDATE autor SET nome_autor=? WHERE cod_autor=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, autor.getNome());
            stmt.setInt(2, autor.getCdAutor());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(AutorDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean remover(Autor autor) {
        String sql = "DELETE FROM autor WHERE cod_autor=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, autor.getCdAutor());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(AutorDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<Autor> listar() {
        String sql = "SELECT * FROM autor";
        List<Autor> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Autor autor = new Autor();
                autor.setCdAutor(resultado.getInt("cod_autor"));
                autor.setNome(resultado.getString("nome_autor"));
                retorno.add(autor);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AutorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public Autor buscar(Autor autor) {
        String sql = "SELECT * FROM autor WHERE cod_autor=?";
        Autor retorno = new Autor();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, autor.getCdAutor());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                autor.setNome(resultado.getString("nome_autor"));
                retorno = autor;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AutorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
}
