package biblioteca.controller;

import biblioteca.model.dao.GeneroDAO;
import biblioteca.model.dao.VendaDAO;
import biblioteca.model.database.Database;
import biblioteca.model.database.DatabaseFactory;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

/**
 * FXML Controller class
 *
 * @author Hilda
 */
public class FXMLAnchorPaneGraficosVendasPorGeneroController implements Initializable {
    @FXML
    private BarChart<String, Integer> barChartGenero;
    @FXML
    private CategoryAxis categoryAxis;
    @FXML
    private NumberAxis numberAxis;
    
    //Atributos para manipulação de Banco de Dados
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final VendaDAO vendaDAO = new VendaDAO();
    private final GeneroDAO generoDAO = new GeneroDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        vendaDAO.setConnection(connection);
        generoDAO.setConnection(connection);
        
        Map<Integer, ArrayList> dados = vendaDAO.listarQuantidadeVendasPorGenero();
        
        for (Map.Entry<Integer, ArrayList> dadosItem : dados.entrySet()) {
            
            XYChart.Series<String, Integer> series = new XYChart.Series<>();
            series.setName(dadosItem.getKey().toString());
            for (int i = 0; i < dadosItem.getValue().size(); i = i + 2) {
                String tipo_genero;
                Integer quantidade;
                tipo_genero = (String) dadosItem.getValue().get(i);
                quantidade = (Integer) dadosItem.getValue().get(i + 1);
                series.getData().add(new XYChart.Data<>(tipo_genero, quantidade));
            }
            barChartGenero.getData().add(series);
        }
    }    
    
}
