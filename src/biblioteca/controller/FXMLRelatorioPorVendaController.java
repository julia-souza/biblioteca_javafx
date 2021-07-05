/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biblioteca.controller;

import biblioteca.model.dao.LivroDAO;
import biblioteca.model.dao.VendaDAO;
import biblioteca.model.database.Database;
import biblioteca.model.database.DatabaseFactory;
import biblioteca.model.domain.Venda;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.util.Date;
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
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
/**
 * FXML Controller class
 *
 * @author vicen
 */
public class FXMLRelatorioPorVendaController implements Initializable {

    @FXML 
    private TableView<Venda> tabelaPorVenda;
    
    @FXML
    private TableColumn<Venda,Integer> tabelaPorVendaCodigo;
    
    @FXML
    private TableColumn<Venda,String> tabelaPorVendaNome;
    
    @FXML
    private TableColumn<Venda,Integer> tabelaPorVendaQuantidade;
    
    @FXML
    private TableColumn<Venda,String> tabelaPorVendaCliente;
    
    @FXML
    private TableColumn<Venda,Double> tabelaPorVendaPreço;
    
    @FXML
    private TableColumn<Venda,Date> tabelaPorVendaData;
    
    @FXML
    private Button botaoImprimir;
    
    
    private List<Venda> listVendas;
    private ObservableList<Venda> observableListVendas;
    
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final VendaDAO vendaDAO = new VendaDAO();
    //private final ItensDeVendaDAO itensDeVendaDAO = new ItensDeVendaDAO();
    private final LivroDAO livroDAO = new LivroDAO();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        vendaDAO.setConnection(connection);
        //itensDeVendaDAO.setConnection(connection);
        carregarTableViewVendas();
        
    }    
    public void carregarTableViewVendas() {
        tabelaPorVendaCodigo.setCellValueFactory(new PropertyValueFactory<>("cdVenda"));
        tabelaPorVendaCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        tabelaPorVendaPreço.setCellValueFactory(new PropertyValueFactory<>("valor"));
        tabelaPorVendaData.setCellValueFactory(new PropertyValueFactory<>("data"));
        

        listVendas = vendaDAO.listar();
        

        observableListVendas = FXCollections.observableArrayList(listVendas);
        tabelaPorVenda.setItems(observableListVendas);
    }   
    public void handleImprimir() throws JRException{

        InputStream url = getClass().getResourceAsStream("biblioteca/relatorios/reportBibliotecaData.jasper");
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(url);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, connection);//null: caso não existam filtros
        JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);//false: não deixa fechar a aplicação principal
        jasperViewer.setVisible(true);
    }
}
