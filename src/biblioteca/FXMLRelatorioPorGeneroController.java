/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biblioteca.controller;

import biblioteca.model.dao.GeneroDAO;
import biblioteca.model.database.Database;
import biblioteca.model.database.DatabaseFactory;
import biblioteca.model.domain.Genero;
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
    private TableView<Genero> tabelaPorGenero;
    
    @FXML
    private TableColumn<Genero,Integer> tabelaPorGeneroCodigo;
    
    @FXML
    private TableColumn<Genero,String> tabelaPorGeneroGeneroNome;
    
    @FXML
    private TableColumn<Genero,Integer> tabelaPorGeneroQuantidade;
    
    @FXML
    private Button botaoImprimir;
    
    
    private List<Genero> listGeneros;
    private ObservableList<Genero> observableListGeneros;
    
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final GeneroDAO generoDAO = new GeneroDAO();
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        generoDAO.setConnection(connection);
        carregarTableViewGeneros();
        
    }    
    public void carregarTableViewGeneros() {
        tabelaPorGeneroCodigo.setCellValueFactory(new PropertyValueFactory<>("cdGenero"));
        tabelaPorGeneroGeneroNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tabelaPorGeneroQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));

        listGeneros = generoDAO.listar();

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
