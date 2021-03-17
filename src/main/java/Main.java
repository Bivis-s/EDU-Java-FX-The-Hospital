import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("fxmls/start.fxml"));
        primaryStage.setTitle("The hospital");
        primaryStage.setScene(new Scene(root));
        primaryStage.setMinHeight(720);
        primaryStage.setMaxHeight(720);
        primaryStage.setHeight(720);
        primaryStage.setMinWidth(1280);
        primaryStage.setMaxWidth(1280);
        primaryStage.setWidth(1280);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
