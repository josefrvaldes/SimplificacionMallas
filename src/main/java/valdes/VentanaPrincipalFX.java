package valdes;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * Created by neiklotv on 1/6/17.
 */
public class VentanaPrincipalFX extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Trabajo final Jose Valdés Sirvent");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../ventana_principal_fx_layout.fxml"));
        Parent root = loader.load();
        VentanaPrincipalController controller = loader.getController();
        controller.setStage(stage);
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }
}
