package valdes;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Created by neiklotv on 10/6/17.
 */
public class VentanaPrincipalController {

    @FXML
    private Label lblInfo;
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
        if(nombreImgEntrada.equals(""))
            nombreImgEntrada = "test.jpg";

        try {
            importarImagenACSVYPintar(nombreImgEntrada, porcentajeAleatorio);
            lblInfo.setText("Imagen cargada: " + nombreImgEntrada);
            txtPorcAleatorio.setText(String.valueOf(porcentajeAleatorio));
            txtNombreImagen.setText(nombreImgEntrada);
        } catch (IOException e) {
            System.out.println("Error leyendo la imagen");
            AlertHelper.mostrarError("No se pudo importar la imagen");
        }
    }

    @FXML
    private void clickImportarImagen() {
        importarImagen();
    }

    private int getPorcentajeAleatorio() {
        String auxPorcAleatorio = txtPorcAleatorio.getText();
        int porcentajeAleatorio = 2;

        if(auxPorcAleatorio.equals("")) {
            return 2;
        }

        try {
            porcentajeAleatorio = Integer.parseInt(auxPorcAleatorio);
        } catch (Exception ex) {
            AlertHelper.mostrarError("El formato del porcentaje introducido no es correcto.");
        }

        return porcentajeAleatorio;
    }

    private void importarImagenACSVYPintar(String nombreImagen, int porcAleatorio) throws IOException {
        this.nodosOriginales = this.getNodosDesdeImagen(nombreImagen, porcAleatorio);

        CSVHelper.escribirNodosIntEnCSV(nodosOriginales, "salida.csv");

        pintarNodosIntEnCanvas(nodosOriginales);
    }

    private List<NodoInt> getNodosDesdeImagen(String nombreImagen, int porcAleatorio) throws IOException {
        if (nombreImagen.equals(""))
            nombreImagen = "test.jpg";
        return ImageHelper.getNodosDesdeImagen(nombreImagen, porcAleatorio);
    }

    private void pintarCSVEnCanvas() {
        String nombreArchivo = "salida.csv";
        if (!txtNombreCSV.getText().equals(""))
            nombreArchivo = txtNombreCSV.getText();

        try {
            this.nodosOriginales = (List<NodoInt>) (Object) CSVHelper.leerNodosDeCSV(nombreArchivo, true);

            this.pintarNodosIntEnCanvas(this.nodosOriginales);

            this.lblInfo.setText("CSV cargado: " + nombreArchivo);
            this.txtNombreCSV.setText(nombreArchivo);
        } catch (FileNotFoundException e) {
            AlertHelper.mostrarError("No se encontró el archivo CSV");
        } catch (IOException e) {
            AlertHelper.mostrarError("Hubo un error al procesar el archivo CSV");
        } catch (Exception e) {
            AlertHelper.mostrarError("Archivo CSV con formato incorrecto");
        }
    }

    private void pintarNodosIntEnCanvas(List<NodoInt> nodos) {
        int[] resolucion = NodoHelper.getResolucion(nodos);
        resetearDimensionesCanvas(resolucion[0], resolucion[1]);
        pintarPuntos(nodos);
    }

    @FXML
    private void clickImportarCSV() {
        pintarCSVEnCanvas();
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
        if(nodosOriginales == null || nodosOriginales.size() == 0) {
            AlertHelper.mostrarError("No hay datos cargados");
            return;
        }

        boolean error = false;

        int numPuntos = 100;
        double fraccion1 = 0.25;
        double fraccion2 = 0.125;

        if(txtPuntosASimplificar.getText().equals("")) {
            txtPuntosASimplificar.setText(String.valueOf(numPuntos));
        } else {
            try {
                numPuntos = Integer.parseInt(txtPuntosASimplificar.getText());
            } catch (Exception e) {
                error = true;
            }
        }

        if(txtFraccion1.getText().equals("")) {
            txtFraccion1.setText(String.valueOf(fraccion1));
        } else {
            try {
                fraccion1 = Double.parseDouble(txtFraccion1.getText());
            } catch (Exception e) {
                error = true;
            }
            if(fraccion1 < 0 || fraccion1 > 1) {
                error = true;
            }
        }

        if(txtFraccion2.getText().equals("")) {
            txtFraccion2.setText(String.valueOf(fraccion2));
        } else {
            try {
                fraccion2 = Double.parseDouble(txtFraccion2.getText());
            } catch (Exception e) {
                error = true;
            }
            if(fraccion2 < 0 || fraccion2 > 1) {
                error = true;
            }
        }

        if(error) {
            AlertHelper.mostrarError("Alguno de los parámetros introducidos es inválido");
            return;
        }

        List<NodoInt> nodosSimplificados = NodoHelper.simplificar(this.nodosOriginales, numPuntos, fraccion1, fraccion2);
        try {
            CSVHelper.escribirNodosIntEnCSV(nodosSimplificados, "salida_simplificada.csv");
        } catch (IOException e) {
            AlertHelper.mostrarError("No se pudo exportar a CSV");
        }
        this.pintarPuntos(nodosSimplificados);
    }

    private void resetearDimensionesCanvas(int ancho, int alto) {
        canvas.setWidth(ancho);
        canvas.setHeight(alto);

        if (ancho > stage.getWidth()) {
            stage.setWidth(ancho + 2);
        }

        if (alto > stage.getHeight()) {
            stage.setHeight(alto + 220);
        }
    }

}
