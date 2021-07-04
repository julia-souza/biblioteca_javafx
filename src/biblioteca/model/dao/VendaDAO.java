/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biblioteca.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import biblioteca.model.domain.Cliente;
import biblioteca.model.domain.ItensDeVenda;
import biblioteca.model.domain.Livro;
import biblioteca.model.domain.Venda;

public class VendaDAO {
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Venda venda) {
        String sql = "INSERT INTO venda(data_venda,valor_venda, cod_cliente_v) VALUES(?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setDate(1, Date.valueOf(venda.getData()));
            stmt.setDouble(2, venda.getValor());
            stmt.setInt(3, venda.getCliente().getCdCliente());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(VendaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean alterar(Venda venda) {
        String sql = "UPDATE venda SET data_venda=?,valor_venda=?, cod_cliente_v=? WHERE cod_venda=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setDate(1, Date.valueOf(venda.getData()));
            stmt.setDouble(2, venda.getValor());
            stmt.setInt(3, venda.getCliente().getCdCliente());
            stmt.setInt(4, venda.getCdVenda());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(VendaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean remover(Venda venda) {
        String sql = "DELETE FROM venda WHERE cod_venda=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, venda.getCdVenda());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(VendaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<Venda> listar() {
        //String sql = "SELECT * FROM venda";
        String sql = "SELECT v.cod_venda, v.valor_venda,c.nome_cliente,iv.cod_itens_venda,iv.valor_item,l.titulo,v.data_venda,v.cod_cliente_v FROM venda v INNER JOIN cliente c ON v.cod_cliente_v = c.cod_cliente INNER JOIN itens_venda iv ON v.cod_venda = iv.cod_venda_iv INNER JOIN livro l ON iv.cod_livro_iv = l.cod_livro;";
        String sql2 = "SELECT * FROM livro WHERE titulo=?";
        /*String sql2 = "SELECT v.cod_venda, v.valor_venda,l.titulo"
                + ",v.data_venda,v.cod_cliente_v FROM venda v INNER JOIN cliente c ON v.cod_cliente_v = c.cod_cliente "
                + "INNER JOIN itens_venda iv ON v.cod_venda = iv.cod_venda_iv INNER JOIN livro l ON iv.cod_livro_iv = l.cod_livro WHERE titulo=?";*/
        List<Venda> retorno = new ArrayList<>();
        
        
        /*
         String sql = "SELECT * FROM livro WHERE cod_autor_l=?";
        List<Livro> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, autor.getCdAutor());
            ResultSet resultado = stmt.executeQuery();
        
        try{
            PreparedStatement stmt2 = connection.prepareStatement(sql2);
            stmt2.setString(1, livro.getTitulo());
            ResultSet resultado2 = stmt2.executeQuery();
            while(resultado2.next() ) {
                livro.setTitulo(resultado2.getString("titulo"));
                /*LivroDAO livroDAO = new LivroDAO();
                livroDAO.setConnection(connection);
                livro = livroDAO.buscar(livro);
                venda.setLivro(livro);
                retorno.add(venda);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VendaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
*/
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
           
            
            while (resultado.next() ) {
                
                Cliente cliente = new Cliente();
                List<ItensDeVenda> itensDeVenda = new ArrayList();
                Livro livro = new Livro();
                Venda venda = new Venda();

                venda.setData(resultado.getDate("data_venda").toLocalDate());
                venda.setCdVenda(resultado.getInt("cod_venda"));
                venda.setValor(resultado.getDouble("valor_venda"));
                cliente.setCdCliente(resultado.getInt("cod_cliente_v"));
                //stmt.setInt(4, itemDeVenda.getVenda().getCdVenda());
                livro.setTitulo(resultado.getString("titulo"));
                

                //Obtendo os dados completos do Cliente associado à Venda
                ClienteDAO clienteDAO = new ClienteDAO();
                clienteDAO.setConnection(connection);
                cliente = clienteDAO.buscar(cliente);
                
                LivroDAO livroDAO = new LivroDAO();
                livroDAO.setConnection(connection);
                livro = livroDAO.buscar(livro);
                
                //Obtendo os dados completos dos Itens de Venda associados à Venda
                ItensDeVendaDAO itemDeVendaDAO = new ItensDeVendaDAO();
                itemDeVendaDAO.setConnection(connection);
                itensDeVenda = itemDeVendaDAO.listarPorVenda(venda);
                
                //itensDeVenda.getVenda().getCdVenda();
                venda.setLivro(livro);
                venda.setCliente(cliente);
                venda.setItensDeVenda(itensDeVenda);
                retorno.add(venda);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VendaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

public List<Venda> listarFisicos() {
        //String sql = "SELECT * FROM venda";
        String sql = "SELECT v.cod_venda, v.valor_venda,c.nome_cliente,iv.cod_itens_venda,iv.valor_item,l.titulo,v.data_venda,v.cod_cliente_v,copia_fisica FROM venda v INNER JOIN cliente c ON v.cod_cliente_v = c.cod_cliente INNER JOIN itens_venda iv ON v.cod_venda = iv.cod_venda_iv INNER JOIN livro l ON iv.cod_livro_iv = l.cod_livro;";
        String sql2 = "SELECT * FROM livro WHERE titulo=?";
        /*String sql2 = "SELECT v.cod_venda, v.valor_venda,l.titulo"
                + ",v.data_venda,v.cod_cliente_v FROM venda v INNER JOIN cliente c ON v.cod_cliente_v = c.cod_cliente "
                + "INNER JOIN itens_venda iv ON v.cod_venda = iv.cod_venda_iv INNER JOIN livro l ON iv.cod_livro_iv = l.cod_livro WHERE titulo=?";*/
        List<Venda> retorno = new ArrayList<>();
        
        
        /*
         String sql = "SELECT * FROM livro WHERE cod_autor_l=?";
        List<Livro> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, autor.getCdAutor());
            ResultSet resultado = stmt.executeQuery();
        
        try{
            PreparedStatement stmt2 = connection.prepareStatement(sql2);
            stmt2.setString(1, livro.getTitulo());
            ResultSet resultado2 = stmt2.executeQuery();
            while(resultado2.next() ) {
                livro.setTitulo(resultado2.getString("titulo"));
                /*LivroDAO livroDAO = new LivroDAO();
                livroDAO.setConnection(connection);
                livro = livroDAO.buscar(livro);
                venda.setLivro(livro);
                retorno.add(venda);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VendaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
*/
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
           
            
            while (resultado.next()) {
                
                Cliente cliente = new Cliente();
                List<ItensDeVenda> itensDeVenda = new ArrayList();
                Livro livro = new Livro();
                Venda venda = new Venda();

                if((resultado.getInt("copia_fisica"))==1){
                
                    venda.setData(resultado.getDate("data_venda").toLocalDate());
                    venda.setCdVenda(resultado.getInt("cod_venda"));
                    venda.setValor(resultado.getDouble("valor_venda"));
                    cliente.setCdCliente(resultado.getInt("cod_cliente_v"));
                    //stmt.setInt(4, itemDeVenda.getVenda().getCdVenda());
                    livro.setTitulo(resultado.getString("titulo"));


                    //Obtendo os dados completos do Cliente associado à Venda
                    ClienteDAO clienteDAO = new ClienteDAO();
                    clienteDAO.setConnection(connection);
                    cliente = clienteDAO.buscar(cliente);

                    LivroDAO livroDAO = new LivroDAO();
                    livroDAO.setConnection(connection);
                    livro = livroDAO.buscar(livro);

                    //Obtendo os dados completos dos Itens de Venda associados à Venda
                    ItensDeVendaDAO itemDeVendaDAO = new ItensDeVendaDAO();
                    itemDeVendaDAO.setConnection(connection);
                    itensDeVenda = itemDeVendaDAO.listarPorVenda(venda);

                    //itensDeVenda.getVenda().getCdVenda();
                    venda.setLivro(livro);
                    venda.setCliente(cliente);
                    venda.setItensDeVenda(itensDeVenda);
                    retorno.add(venda);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(VendaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public Venda buscar(Venda venda) {
        String sql = "SELECT * FROM venda WHERE cod_venda=?";
        Venda retorno = new Venda();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, venda.getCdVenda());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                Cliente cliente = new Cliente();
                venda.setCdVenda(resultado.getInt("cod_venda"));
                venda.setData(resultado.getDate("data_venda").toLocalDate());
                venda.setValor(resultado.getDouble("valor_venda"));
                cliente.setCdCliente(resultado.getInt("cod_cliente_v"));
                venda.setCliente(cliente);
                retorno = venda;
            }
        } catch (SQLException ex) {
            Logger.getLogger(VendaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public Venda buscarUltimaVenda() {
        String sql = "SELECT max(cod_venda) FROM venda";
        Venda retorno = new Venda();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();

            if (resultado.next()) {
                retorno.setCdVenda(resultado.getInt("max"));
                return retorno;
            }
        } catch (SQLException ex) {
            Logger.getLogger(VendaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
    public Venda gerarCopiaFisica(){
        String sql = "";
        Venda retorno = new Venda();
        return retorno;
    }
    
    public boolean removerCopiaFisica(Venda venda){
        String sql = "UPDATE venda SET copia_fisica=0 WHERE cod_venda=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, venda.getCdVenda());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(VendaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public Map<Integer, ArrayList> listarQuantidadeVendasPorMes() {
        String sql = "select count(cod_venda), extract(year from data) as ano, extract(month from data) as mes from vendas group by ano, mes order by ano, mes";
        Map<Integer, ArrayList> retorno = new HashMap();
        
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();

            while (resultado.next()) {
                ArrayList linha = new ArrayList();
                if (!retorno.containsKey(resultado.getInt("ano")))
                {
                    linha.add(resultado.getInt("mes"));
                    linha.add(resultado.getInt("count"));
                    retorno.put(resultado.getInt("ano"), linha);
                }else{
                    ArrayList linhaNova = retorno.get(resultado.getInt("ano"));
                    linhaNova.add(resultado.getInt("mes"));
                    linhaNova.add(resultado.getInt("count"));
                }
            }
            return retorno;
        } catch (SQLException ex) {
            Logger.getLogger(VendaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
    
    public Map<Integer, ArrayList> listarQuantidadeVendasPorGenero() {
        String sql = "select count(cod_Venda), extract(year from data) as ano, extract(month from data) as mes from vendas"
                + "FROM venda v, INNER JOIN itens_venda iv ON v.cod_venda = iv.cod_venda_iv,"
                + "INNER JOIN livro l ON iv.cod_livro_iv = l.cod_livro, INNER JOIN genero gen ON l.cod_livro = gen.cod_livro, group by gen.cod_genero";
        Map<Integer, ArrayList> retorno = new HashMap();
        
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();

            while (resultado.next()) {
                ArrayList linha = new ArrayList();
                if (!retorno.containsKey(resultado.getInt("ano")))
                {
                    linha.add(resultado.getInt("gen.cod_genero"));
                    linha.add(resultado.getInt("count"));
                    retorno.put(resultado.getInt("ano"), linha);
                }else{
                    ArrayList linhaNova = retorno.get(resultado.getInt("ano"));
                    linhaNova.add(resultado.getInt("gen.cod_genero"));
                    linhaNova.add(resultado.getInt("count"));
                }
            }
            return retorno;
        } catch (SQLException ex) {
            Logger.getLogger(VendaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
}
