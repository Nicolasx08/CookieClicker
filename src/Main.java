import amelioration.Amelioration;
import amelioration.ClicAuto;
import amelioration.DoubleClic;
import amelioration.Rabais;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Timer;
import java.util.concurrent.atomic.AtomicInteger;

public class Main extends Application {
    public static void main(String[] args) {launch(args);}
    public static AtomicInteger point= new AtomicInteger();
    public static Amelioration doubleClick= new DoubleClic();
    public static Amelioration rabais= new Rabais();
    public static Amelioration clicAutomatique= new ClicAuto();
    public void start(Stage stage){
        stage.setHeight(700);
        stage.setWidth(700);
        stage.setTitle("Cookie Clicker");
        stage.setResizable(false);



        Label text= new Label("Nombre de cookies:");
        text.setTranslateX(400);
        text.setTranslateY(10);
        text.setScaleX(2);
        text.setScaleY(1.5);



        Label points = new Label("0");
        points.setScaleX(3);
        points.setScaleY(3);
        points.setTranslateX(600);
        points.setTranslateY(10);

        Button rab= new Button("Rabais= "+rabais.getPourcentRabais()+"%  prix= "+rabais.getCost());
        rab.setPrefSize(200,50);
        rab.setDisable(true);
        rab.setTranslateX(50);
        rab.setTranslateY(100);

        Button bouton= new Button("Cookie");
        bouton.setScaleX(2.5);
        bouton.setScaleY(1.8);
        bouton.setTranslateX(325);
        bouton.setTranslateY(450);

        Button click2=  new Button("Double click= "+doubleClick.getCost()+ " cookies");
        click2.setPrefSize(200,50);
        click2.setTranslateY(50);
        click2.setTranslateX(50);
        click2.setDisable(true);

        bouton.setOnAction((event)->{
                    point.set(augmenterBouton(point.get()));
                    points.setText(Integer.toString(point.get()));
                    if (point.get()>=doubleClick.getCost()){
                        click2.setDisable(false);
                    }
                    else {
                        click2.setDisable(true);
                    }
                    if (point.get()<=rabais.getCost() || rabais.getPourcentRabais()>20){
                        rab.setDisable(true);
                    }
                    else {
                        rab.setDisable(false);
                    }
                }
        );

        click2.setOnAction((event)->{
            point.set(point.get()-doubleClick.getCost());
            points.setText(Integer.toString(point.get()));
            doubleClick.setClicParSec(doubleClick.getClicParSec()*2);
            doubleClick.setCost((doubleClick.getCost()*6)+100);
            click2.setText("Double clic= "+Integer.toString(doubleClick.getCost())+ "cookies");
            if (point.get()>= doubleClick.getCost()){
                click2.setDisable(false);
            }
            else {
                click2.setDisable(true);
            }
        });

        rab.setOnAction((event)->{

            if (rabais.getPourcentRabais()<20){
                point.set(point.get()-rabais.getCost());
                points.setText(Integer.toString(point.get()));
                rabais.setCost((rabais.getCost()*2)+200);
                rabais.setPourcentRabais(rabais.getPourcentRabais()*2);
                doubleClick.setCost(doubleClick.getCost()-((doubleClick.getCost()*rabais.getPourcentRabais())/100));
                click2.setText("Double clic= "+Integer.toString(doubleClick.getCost())+ "cookies");
                rab.setText("Rabais= "+rabais.getPourcentRabais()+"%  prix= "+ Integer.toString(rabais.getCost())+ " cookies");
                if (point.get()>=rabais.getCost()){
                    rab.setDisable(false);
                }
                else {
                    rab.setDisable(true);
                }
                if (point.get()>=doubleClick.getCost()){
                    click2.setDisable(false);
                }
                else {
                    click2.setDisable(true);
                }
            }
            else {
                rab.setText("Maxed");
                rab.setDisable(true);
                rabais.setPourcentRabais(21);
            }


        });

        final Timeline timeline = new Timeline();
        new KeyFrame(Duration.millis(1000),event ->{
            point.set(point.get()+clicAutomatique.getDps());
            points.setText(Integer.toString(point.get()));
        }
        );
        timeline.setCycleCount(timeline.INDEFINITE);
        timeline.play();

        Group root= new Group(points,bouton,text,rab,click2);



        stage.setScene(
                new Scene(root)
        );

        stage.show();
}
public int augmenterBouton(int point){
        point=point+(1*doubleClick.getClicParSec());
        return point;
}
}