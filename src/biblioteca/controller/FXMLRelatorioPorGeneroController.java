/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biblioteca.controller;

import biblioteca.model.dao.LivroDAO;
import biblioteca.model.database.Database;
import biblioteca.model.database.DatabaseFactory;
import biblioteca.model.domain.ItensDeVenda;
import biblioteca.model.domain.Livro;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author vicen
 */
public class FXMLRelatorioPorGeneroController implements Initializable {

    @FXML 
    private TableView<Livro> tabelaPorGenero;
    
    @FXML
    private TableColumn<Livro,Integer> tabelaPorGeneroCodigo;
    
    @FXML
    private TableColumn<Livro,String> tabelaPorGeneroGenero;
    
    @FXML
    private TableColumn<Livro,ItensDeVenda> tabelaPorGeneroQuantidade; /*acessa a tabela livro e itens de venda*/
    
    @FXML
    private Button botaoImprimir;
   
    
    private List<Livro> listGeneros;
    private ObservableList<Livro> observableListGeneros;
    
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final LivroDAO livroDAO = new LivroDAO();
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        livroDAO.setConnection(connection);
        carregarTableViewGeneros();
        
    }    
    public void carregarTableViewGeneros() {
        /*
        tableColumnCod.setCellValueFactory((param) -> new SimpleStringProperty(String.valueOf(param.getValue().getCod())));
        tableColumnTipodeEmprestimo.setCellValueFactory(new PropertyValueFactory<>("emprestimo"));
        tableColumnQuantiadoEmprestimo.setCellValueFactory(new PropertyValueFactory<>("valor"));
        tableColumnQtdParcelas.setCellValueFactory(new PropertyValueFactory<>("parcela"));
        tableColumnNomeCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));

        listEmprestimoConfirmado = emprestimoConfirmadoDao.listar();
        observableListEmprestimoConfirmado = FXCollections.observableArrayList(listEmprestimoConfirmado);
        tableViewRelatorio.setItems(observableListEmprestimoConfirmado);
        */
        
        
        tabelaPorGeneroCodigo.setCellValueFactory(new PropertyValueFactory<>("cod_genero"));
        tabelaPorGeneroGenero.setCellValueFactory(new PropertyValueFactory<>("tipo_genero"));
        tabelaPorGeneroQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));

        listGeneros = livroDAO.listar();

        observableListGeneros = FXCollections.observableArrayList(listGeneros);
        tabelaPorGenero.setItems(observableListGeneros);
    }
    /*
    public void handleImprimir() throws JRException{
        URL url = getClass().getResource("/javafxmvc/relatorios/JAVAFXMVCRelatorioProdutos.jasper");
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(url);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, connection);//null: caso não existam filtros
        JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);//false: não deixa fechar a aplicação principal
        jasperViewer.setVisible(true);
    }
*/
}
