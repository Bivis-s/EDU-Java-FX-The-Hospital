import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static constants.FxmlValues.LOGIN_FXML_PATH;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource(LOGIN_FXML_PATH));
        primaryStage.setTitle("The hospital");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    // запуск приложения
    public static void main(String[] args) {
        launch(args); // запускает метод start
    }

    // TODO REMOVING ACCOUNTS
}
