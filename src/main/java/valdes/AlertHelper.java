package valdes;

import javafx.scene.control.Alert;

/**
 * Created by neiklotv on 12/6/17.
 */
public class AlertHelper {

    public static void mostrarError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public static void mostrarError(String mensaje) {
        mostrarError("Hubo un error", mensaje);
    }
}
