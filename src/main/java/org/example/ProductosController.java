package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;
import java.io.InputStream;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import static org.example.App.*;


/**
 * Esta clase se encarga de controlar los productos que pide cada mesa y generar su factura
 */
public class ProductosController {
    @FXML
    private ListView<Producto> lvproductos;
    @FXML
    private ListView<Producto> lvpedido;
    @FXML
    private Button factura;
    @FXML
    private Button pagar;
    @FXML
    private Button borrar;
    private ObservableList<Producto> productos = FXCollections.observableArrayList();
    private Mesa mesa;
    public Button mesaButt;
    private boolean facturado=false;

    /**
     * Realiza la conexión con la BBDD
     * @return
     * @throws SQLException
     */
    public Connection connectionDB() throws SQLException {
        String db = "bar";
        String host = "localhost";
        String port = "3307";
        String urlConnection = "jdbc:mysql://" + host + ":" + port + "/" + db;
        String user = "root";
        String pwd = "";
        Connection c = DriverManager.getConnection(urlConnection, user, pwd);
        return c;
    }


    /**
     * Aquí se inician las dos listas con sus productos y se gestionan los botones a la hora de pagar
     *
     * @throws SQLException
     */
    public void initialize() throws SQLException {
        mesaButt=mesaButton;
        mesa=mesaActual;
        //Aquí se llena la listView de productos con los productos disponibles en la BBDD
        Statement s=connectionDB().createStatement();
        ResultSet rs=s.executeQuery("SELECT * FROM productos;");
        while(rs.next()){
            productos.add(new Producto(rs.getInt(1), rs.getString(2), rs.getDouble(3)));
        }
        lvproductos.setItems(productos);

        //En este listener se guardan en el objeto mesa los productos que se seleccionen
        lvproductos.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<Producto>() {
            @Override
            public void onChanged(Change<? extends Producto> change) {
                mesa.addProducto(lvproductos.getSelectionModel().getSelectedItem());
            }
        });
        lvpedido.setItems(mesa.getProductos());


        //Con este botón se borra el elemento seleccionado de la listView de pedido
        borrar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                lvpedido.getItems().remove(lvpedido.getSelectionModel().getSelectedItem());
            }
        });

        //Este botón se encarga de generar la factura con los productos pedidos
        factura.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                PreparedStatement s= null;
                mesa.pagar();
                try {
                    s=connectionDB().prepareStatement("INSERT INTO `mesas`(`ID_mesa`, `num_mesa`, `precio_total`, `fecha`, `hora`) VALUES (?,?,?,?,?)");
                    s.setInt(1, mesa.getId());
                    s.setInt(2, mesa.getNum_mesa());
                    s.setDouble(3, mesa.getPrecio_total());
                    s.setString(4, ""+mesa.getFecha());
                    s.setString(5, ""+mesa.getHora());
                    s.executeUpdate();
                    for (Producto p: mesa.getProductos()) {
                        s = connectionDB().prepareStatement("INSERT INTO `mesa_producto`(`ID_mesa`, `ID_producto`) VALUES ("+mesa.getId()+","+p.getId()+")");
                        s.executeUpdate();
                    }
                    s.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                Map<String, Object> param=new HashMap<>();
                param.put("P_IdMesa", String.valueOf(mesa.getId()));
                param.put("P_IdMesa2", String.valueOf(mesa.getId()));
                InputStream reportFile = getClass().getResourceAsStream("/Factura.jrxml");
                JasperReport jasperReport = null;
                try {
                    jasperReport = JasperCompileManager.compileReport(reportFile);
                } catch (JRException e) {
                    throw new RuntimeException(e);
                }
                JasperPrint jasperPrint = null;
                try {
                    jasperPrint = JasperFillManager.fillReport(jasperReport, param, connectionDB());
                } catch (JRException e) {
                    throw new RuntimeException(e);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                JasperViewer.viewReport(jasperPrint, false);
                facturado=true;
            }
        });


        //No se puede pagar sin haber facturado antes, al pagar se quita la mesa de la lista de mesas y se pone el
        //imageButton como disponible
        pagar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (facturado) {
                    mesas.remove(mesa);
                    ImageView view = new ImageView("mesa.png");
                    view.setFitHeight(BUTTON_HEIGHT);
                    view.setPreserveRatio(true);
                    mesaButt.setGraphic(view);
                    Stage stage = (Stage) pagar.getScene().getWindow();
                    stage.close();
                }
            }
        });


    }
}