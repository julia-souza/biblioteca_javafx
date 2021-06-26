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
/**
 *
 * @author Yesod
 */
public class GeneroDAO {
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Genero genero) {
        String sql = "INSERT INTO genero(tipo_genero) VALUES(?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, genero.getTipo_Genero());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(GeneroDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean alterar(Genero genero) {
        String sql = "UPDATE genero SET tipo_genero=? WHERE cod_genero=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, genero.getTipo_Genero());
            stmt.setInt(2, genero.getCdGenero());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(GeneroDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean remover(Genero genero) {
        String sql = "DELETE FROM genero WHERE cod_genero=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, genero.getCdGenero());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(GeneroDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<Genero> listar() {
        String sql = "SELECT * FROM genero";
        List<Genero> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Genero genero = new Genero();
                genero.setCdGenero(resultado.getInt("cod_genero"));
                genero.setTipo_Genero(resultado.getString("tipo_Genero"));
                retorno.add(genero);
            }
        } catch (SQLException ex) {
            Logger.getLogger(GeneroDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public Genero buscar(Genero genero) {
        String sql = "SELECT * FROM genero WHERE cod_genero=?";
        Genero retorno = new Genero();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, genero.getCdGenero());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                genero.setTipo_Genero(resultado.getString("descricao"));
                retorno = genero;
            }
        } catch (SQLException ex) {
            Logger.getLogger(GeneroDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
}
