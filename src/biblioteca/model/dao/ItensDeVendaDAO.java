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
import biblioteca.model.domain.ItensDeVenda;
import biblioteca.model.domain.Livro;
import biblioteca.model.domain.Venda;

public class ItensDeVendaDAO {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(ItensDeVenda itemDeVenda) {
        String sql = "INSERT INTO itensdevenda(quantidade, valor_item, cod_livro_iv, cod_venda_iv) VALUES(?,?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, itemDeVenda.getQuantidade());
            stmt.setDouble(2, itemDeVenda.getValor());
            stmt.setInt(3, itemDeVenda.getLivro().getCdLivro());
            stmt.setInt(4, itemDeVenda.getVenda().getCdVenda());
            
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ItensDeVendaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean alterar(ItensDeVenda itemDeVenda) {
        return true;
    }

    public boolean remover(ItensDeVenda itemDeVenda) {
        String sql = "DELETE FROM itensDeVenda WHERE cod_itens_venda=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, itemDeVenda.getCdItemDeVenda());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ItensDeVendaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<ItensDeVenda> listar() {
        String sql = "SELECT * FROM itensDeVenda";
        List<ItensDeVenda> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                ItensDeVenda itemDeVenda = new ItensDeVenda();
                Livro livro = new Livro();
                Venda venda = new Venda();
                itemDeVenda.setCdItemDeVenda(resultado.getInt("cod_itens_venda"));
                itemDeVenda.setQuantidade(resultado.getInt("quantidade"));
                itemDeVenda.setValor(resultado.getDouble("valor_item"));
                
                livro.setCdLivro(resultado.getInt("cod_livro"));
                venda.setCdVenda(resultado.getInt("cod_venda"));
                
                //Obtendo os dados completos do Produto associado ao Item de Venda
                LivroDAO produtoDAO = new LivroDAO();
                produtoDAO.setConnection(connection);
                livro = produtoDAO.buscar(livro);
                
                VendaDAO vendaDAO = new VendaDAO();
                vendaDAO.setConnection(connection);
                venda = vendaDAO.buscar(venda);
                
                itemDeVenda.setLivro(livro);
                itemDeVenda.setVenda(venda);
                
                retorno.add(itemDeVenda);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ItensDeVendaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
    public List<ItensDeVenda> listarPorVenda(Venda venda) {
        String sql = "SELECT * FROM itensDeVenda WHERE cod_venda=?";
        List<ItensDeVenda> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, venda.getCdVenda());
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                ItensDeVenda itemDeVenda = new ItensDeVenda();
                Livro livro = new Livro();
                Venda v = new Venda();
                itemDeVenda.setCdItemDeVenda(resultado.getInt("cod_itens_venda"));
                itemDeVenda.setQuantidade(resultado.getInt("quantidade"));
                itemDeVenda.setValor(resultado.getDouble("valor_item"));
                
                livro.setCdLivro(resultado.getInt("cod_livro"));
                venda.setCdVenda(resultado.getInt("cod_venda"));
                
                //Obtendo os dados completos do Produto associado ao Item de Venda
                LivroDAO livroDAO = new LivroDAO();
                livroDAO.setConnection(connection);
                livro = livroDAO.buscar(livro);
                
                itemDeVenda.setLivro(livro);
                itemDeVenda.setVenda(v);
                
                retorno.add(itemDeVenda);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ItensDeVendaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public ItensDeVenda buscar(ItensDeVenda itemDeVenda) {
        String sql = "SELECT * FROM itensDeVenda WHERE cod_itens_venda=?";
        ItensDeVenda retorno = new ItensDeVenda();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, itemDeVenda.getCdItemDeVenda());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                Livro livro = new Livro();
                Venda venda = new Venda();
                itemDeVenda.setCdItemDeVenda(resultado.getInt("cod_itens_venda"));
                itemDeVenda.setQuantidade(resultado.getInt("quantidade"));
                itemDeVenda.setValor(resultado.getDouble("valor_item"));
                
                livro.setCdLivro(resultado.getInt("cod_livro"));
                venda.setCdVenda(resultado.getInt("cod_venda"));
                
                //Obtendo os dados completos do Cliente associado à Venda
                LivroDAO produtoDAO = new LivroDAO();
                produtoDAO.setConnection(connection);
                livro = produtoDAO.buscar(livro);
                
                VendaDAO vendaDAO = new VendaDAO();
                vendaDAO.setConnection(connection);
                venda = vendaDAO.buscar(venda);
                
                itemDeVenda.setLivro(livro);
                itemDeVenda.setVenda(venda);
                
                retorno = itemDeVenda;
            }
        } catch (SQLException ex) {
            Logger.getLogger(VendaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
}
