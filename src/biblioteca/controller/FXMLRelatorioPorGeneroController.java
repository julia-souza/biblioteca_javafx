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
    private TableColumn<Livro,String> tabelaPorGeneroAutor;
    
    @FXML
    private TableColumn<Livro,String> tabelaPorGeneroTitulo;
        
    
    @FXML
    private TableColumn<Livro,String> tabelaPorGeneroEdicao;
    
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
        tabelaPorGeneroCodigo.setCellValueFactory(new PropertyValueFactory<>("cdLivro"));
        tabelaPorGeneroGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
        tabelaPorGeneroAutor.setCellValueFactory(new PropertyValueFactory<>("autor"));
        tabelaPorGeneroTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        tabelaPorGeneroEdicao.setCellValueFactory(new PropertyValueFactory<>("edicao"));

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
