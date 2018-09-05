import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.concurrent.atomic.AtomicInteger;

public class Main extends Application {
    public static void main(String[] args) {launch(args);}{


    }
    public void start(Stage stage){
        stage.setHeight(700);
        stage.setWidth(700);
        stage.setTitle("Cookie Clicker");
        stage.setResizable(false);
        Label text= new Label("Nombre de cookies:");
        text.setTranslateX(400);
        text.setScaleX(2);
        text.setScaleY(1.5);
        AtomicInteger point= new AtomicInteger();
        Button bouton= new Button("Cookie");
        bouton.setScaleX(2.5);
        bouton.setScaleY(1.8);
        bouton.setTranslateX(325);
        bouton.setTranslateY(450);
        Label points = new Label("0");
        points.setScaleX(1.1);
        points.setScaleY(1.1);
        points.setTranslateX(600);
        bouton.setOnAction((event)->{
                    point.set(augmenterBouton(point.get()));
                    points.setText(Integer.toString(point.get()));
                }


        );
        Group root= new Group(points,bouton,text);
        if ((point.get()) == 50){
            Button click2=  new Button("Double click=50 cookies");
            click2.setScaleX(1.2);
            click2.setScaleY(1.2);
            click2.setTranslateY(200);
            root.getChildren().add(click2);
        }
        stage.setScene(
                new Scene(root)
        );

        stage.show();
}
public int augmenterBouton(int point){
        point++;
        return point;
}
}