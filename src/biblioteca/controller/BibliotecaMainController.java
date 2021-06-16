/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biblioteca.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Yesod
 */
public class BibliotecaMainController implements Initializable {

    @FXML
    private MenuItem cadastro_cliente_menuitem;
    
    @FXML
    private MenuItem cadastro_livro_menuitem;
    
    @FXML
    private MenuItem listar_livros_menuitem;
    
    @FXML
    private MenuItem listar_vendas_menuitem;
    
    @FXML
    private MenuItem grafico_genero_menuitem;
    
    @FXML
    private MenuItem grafico_data_menuitem;

    @FXML
    private MenuItem relatorio_genero_menuitem;
    
    @FXML
    private MenuItem relatorio_data_menuitem;
    
    @FXML
    private AnchorPane anchorPane;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
