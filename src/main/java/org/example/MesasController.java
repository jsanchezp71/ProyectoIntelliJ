package org.example;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import static org.example.App.*;

/**
 * Esta clase se encarga de controlar el fxml con todas las mesas
 */
public class MesasController {
    private static Scene scene;
    private static Stage stage;
    private ImageView view;
    @FXML
    private Button mesa1;
    @FXML
    private Button mesa2;
    @FXML
    private Button mesa3;
    @FXML
    private Button mesa4;
    @FXML
    private Button mesa5;
    @FXML
    private Button mesa6;
    @FXML
    private Button buttonHistoricoMesas;

    /**
     * Insertamos las imagenes en los botones(mesas) y los ponemos a disposición de pedir
     * @throws SQLException
     */
    public void initialize() throws SQLException {
        idMesa=nuevoIdMesa();

        mesa1.setGraphic(viewMesa("mesa.png"));
        mesa2.setGraphic(viewMesa("mesa.png"));
        mesa3.setGraphic(viewMesa("mesa.png"));
        mesa4.setGraphic(viewMesa("mesa.png"));
        mesa5.setGraphic(viewMesa("mesa.png"));
        mesa6.setGraphic(viewMesa("mesa.png"));

        pedir(mesa1, 1);
        pedir(mesa2, 2);
        pedir(mesa3, 3);
        pedir(mesa4, 4);
        pedir(mesa5, 5);
        pedir(mesa6, 6);


        //Al pulsar aquí genera un informe con lo pedido por todas las mesas
        //que han ido pasando por el bar
        buttonHistoricoMesas.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                InputStream reportFile = getClass().getResourceAsStream("/Historico.jrxml");
                JasperReport jasperReport = null;
                try {
                    jasperReport = JasperCompileManager.compileReport(reportFile);
                } catch (JRException e) {
                    throw new RuntimeException(e);
                }
                JasperPrint jasperPrint = null;
                try {
                    jasperPrint = JasperFillManager.fillReport(jasperReport, null, connectionDB());
                } catch (JRException e) {
                    throw new RuntimeException(e);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                JasperViewer.viewReport(jasperPrint, false);
            }
        });
    }

    /**
     * Inserta la imagen en cada botón
     * @param foto
     * @return
     */
    public ImageView viewMesa(String foto){
        view=new ImageView(new Image(foto));
        view.setFitHeight(BUTTON_HEIGHT);
        view.setPreserveRatio(true);
        return view;
    }


    /**
     * Abre una nueva ventana y crea una nueva mesa con los datos que se disponen,
     * también pone el imageButton en negro simulando que está ocupada
     * @param mesaB
     * @param num_mesa
     */
    public void pedir(Button mesaB, int num_mesa){
        mesaB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    if (!existeMesa(num_mesa)){
                        idMesa++;
                        mesas.add(new Mesa(idMesa, num_mesa, 0, LocalDate.now(), LocalTime.now()));
                    }
                    existeMesa(num_mesa);
                    mesaButton=mesaB;
                    mesaB.setGraphic(viewMesa("mesaOcupada.png"));
                    stage = new Stage();
                    stage.setTitle("Detalles de Tarea");
                    scene = new Scene(loadFXML("productos"), 500, 500);
                    stage.setScene(scene);
                    stage.show();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Se le da valor a la static mesaActual para pasarle los datos a la nueva ventana
     * @param num_mesa
     * @return
     */
    public boolean existeMesa(int num_mesa){
        for (Mesa m: mesas) {
            if (m.getNum_mesa()==num_mesa){
                mesaActual=m;
                return true;
            }
        }
        return false;
    }


    /**
     * Devuelve el ultimo idMesa de la BBDD
     * @return
     * @throws SQLException
     */
    public int nuevoIdMesa() throws SQLException {
        Statement s = connectionDB().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs=s.executeQuery("SELECT ID_mesa FROM mesas;");
        rs.last();
        return rs.getInt(1);
    }

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
     * Carga el fxml de productos
     * @param fxml
     * @return
     * @throws IOException
     */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
}
