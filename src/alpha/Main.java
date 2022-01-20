package alpha;

import data.Data;
import engine.Engine;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import specifications.DataService;
import specifications.EngineService;
import specifications.ViewerService;
import tools.Game;
import tools.User;
import userinterface.Viewer;


public class Main extends Application {

    //---VARIABLES---//
    private static DataService data;
    private static EngineService engine;
    private static ViewerService viewer;
    private static AnimationTimer timer;


    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainframe.fxml"));
        loader.setController(engine);
        Scene scene = new Scene(loader.load(), 1280, 800);
        stage.setTitle("JavaFX");
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.LEFT) engine.setPlayerCommand(User.COMMAND.LEFT);
            if (event.getCode() == KeyCode.RIGHT) engine.setPlayerCommand(User.COMMAND.RIGHT);
            if (event.getCode() == KeyCode.UP) engine.setPlayerCommand(User.COMMAND.UP);
            if (event.getCode() == KeyCode.DOWN) engine.setPlayerCommand(User.COMMAND.DOWN);
            if (event.getCode() == KeyCode.P) {
                if (data.getGameStatus() == Game.STATUS.PAUSED) {
                    engine.start();
                } else {
                    engine.stop();
                }
            }
            event.consume();
        });
        scene.setOnKeyReleased(event -> {
        });
        stage.setScene(scene);
        stage.setOnShown(event -> engine.start());
        stage.setOnCloseRequest(event -> engine.stop());
        stage.show();
        scene.getRoot().requestFocus();
        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                //new MediaPlayer(new Media(getHostServices().getDocumentBase() + "src/sound/waterdrip.mp3")).play();
            }
        };
        timer.start();
    }

    //---EXECUTABLE---//
    public static void main(String[] args) {
        engine = new Engine();
        data = new Data();
        viewer = new Viewer();

        ((Viewer) viewer).bindReadService(data);
        ((Engine) engine).bindDataService(data);

        engine.init();
        data.init();
        viewer.init();

        launch(args);
    }
}
