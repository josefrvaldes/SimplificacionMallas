package valdes;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.List;

/**
 * Created by neiklotv on 10/6/17.
 */
public class VentanaPrincipalController {

    @FXML
    private TextField txtNombreCSV;
    @FXML
    private TextField txtNombreImagen;
    @FXML
    private TextField txtPorcAleatorio;
    @FXML
    private TextField txtFraccion1;
    @FXML
    private TextField txtFraccion2;
    @FXML
    private TextField txtPuntosASimplificar;
    @FXML
    private Canvas canvas;

    private Stage stage;
    private List<NodoInt> nodosOriginales;
    private int vecesPintado = 0;

    @FXML
    private void initialize() {

    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void importarImagen() {
        int porcentajeAleatorio = this.getPorcentajeAleatorio();
        String nombreImgEntrada = txtNombreImagen.getText();
        importarImagenACSVYPintar(nombreImgEntrada, porcentajeAleatorio);
    }

    @FXML
    private void clickImportarImagen() {
        importarImagen();
    }

    private int getPorcentajeAleatorio() {
        String auxPorcAleatorio = txtPorcAleatorio.getText();
        int porcentajeAleatorio = 2;
        try {
            porcentajeAleatorio = Integer.parseInt(auxPorcAleatorio);
        } catch (Exception ex) {
            System.out.println("Se utilizará el porcentaje aleatorio por defecto");
        }
        return porcentajeAleatorio;
    }

    private void importarImagenACSVYPintar(String nombreImagen, int porcAleatorio) {
        this.nodosOriginales = this.getNodosDesdeImagen(nombreImagen, porcAleatorio);

        CSVHelper.escribirNodosIntEnCSV(nodosOriginales, "salida.csv");

        pintarNodosIntEnCanvas(nodosOriginales);
    }

    private List<NodoInt> getNodosDesdeImagen(String nombreImagen, int porcAleatorio) {
        if (nombreImagen.equals(""))
            nombreImagen = "test.jpg";
        return ImageHelper.getNodosDesdeImagen(nombreImagen, porcAleatorio);
    }

    private void pintarCSVEnCanvas(String nombreArchivo) {
        if (nombreArchivo.equals("")) {
            nombreArchivo = txtNombreCSV.getText();
        }

        if (nombreArchivo.equals(""))
            nombreArchivo = "salida.csv";

        this.nodosOriginales = (List<NodoInt>) (Object) CSVHelper.leerNodosDeCSV(nombreArchivo, true);

        this.pintarNodosIntEnCanvas(this.nodosOriginales);
    }

    private void pintarNodosIntEnCanvas(List<NodoInt> nodos) {
        int[] resolucion = NodoHelper.getResolucion(nodos);
        resetearDimensionesCanvas(resolucion[0], resolucion[1]);
        //pintarPuntos(mallaInt.getMallaOriginal(), canvas);
        pintarPuntos(nodos);
        //System.out.println(mallaInt);
    }

    @FXML
    private void clickImportarCSV() {
        pintarCSVEnCanvas("");
    }

    private void pintarPuntos(List<NodoInt> nodos) {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        //gc.clearRect(0, 0, width, height);

        switch (vecesPintado) {
            case 0:
                gc.setFill(Color.BLACK);
                break;
            case 1:
                gc.setFill(Color.RED);
                break;
            case 2:
                gc.setFill(Color.GREEN);
                break;
            case 3:
                gc.setFill(Color.DARKSALMON);
                break;
            case 4:
                gc.setFill(Color.BLUE);
                break;
        }

        if (vecesPintado < 4) vecesPintado++;
        else vecesPintado = 0;

        int[] minMax = NodoHelper.obtenerValoresMinimosYMaximos(nodos);
        int minX = minMax[0];
        int minY = minMax[1];

        for (NodoInt n : nodos) {
            gc.fillOval(n.getX() - minX, n.getY() - minY, 3, 3);
        }
    }

    @Deprecated
    private void pintarPuntos(byte[][] puntos, Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        int width = puntos.length;
        int height = puntos[0].length;

        //gc.clearRect(0, 0, width, height);

        switch (vecesPintado) {
            case 0:
                gc.setFill(Color.BLACK);
                break;
            case 1:
                gc.setFill(Color.RED);
                break;
            case 2:
                gc.setFill(Color.GREEN);
                break;
            case 3:
                gc.setFill(Color.DARKSALMON);
                break;
            case 4:
                gc.setFill(Color.BLUE);
                break;
        }

        if (vecesPintado < 4) vecesPintado++;
        else vecesPintado = 0;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (puntos[i][j] == 1) {
                    gc.fillOval(i, j, 3, 3);
                }
            }
        }
    }

    @FXML
    private void clickSimplificar() {
        simplificar();
    }

    @FXML
    private void clickBorrarCanvas() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    private void simplificar() {
        int numPuntos = Integer.parseInt(txtPuntosASimplificar.getText());
        double fraccion1 = Double.parseDouble(txtFraccion1.getText());
        double fraccion2 = Double.parseDouble(txtFraccion2.getText());
        List<NodoInt> nodosSimplificados = NodoHelper.simplificar(this.nodosOriginales,numPuntos, fraccion1, fraccion2);
        CSVHelper.escribirNodosIntEnCSV(nodosSimplificados, "salida_simplificada.csv");
        this.pintarPuntos(nodosSimplificados);
    }

    private void resetearDimensionesCanvas(int ancho, int alto) {
        canvas.setWidth(ancho);
        canvas.setHeight(alto);

        if (ancho > stage.getWidth()) {
            stage.setWidth(ancho + 2);
        }

        if (alto > stage.getHeight()) {
            stage.setHeight(alto + 210);
        }
    }

}
