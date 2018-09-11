import amelioration.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.util.Timer;
import java.util.concurrent.atomic.AtomicInteger;

public class Main extends Application {
    public static void main(String[] args) {launch(args);}
    public static AtomicInteger point= new AtomicInteger();
    public static Amelioration doubleClick= new DoubleClic();
    public static Amelioration rabais= new Rabais();
    public static Amelioration clicAutomatique= new ClicAuto();
    public static Amelioration nombreClicArgent= new Argent();
    public static Label points = new Label("0");
    public static Amelioration cookie= new Surprise();

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


        point.set(point.get()+clicAutomatique.getDps());
        points.setText(Integer.toString(point.get()));

        points.setScaleX(3);
        points.setScaleY(3);
        points.setTranslateX(600);
        points.setTranslateY(10);

        final Timeline timer = new Timeline(new KeyFrame(Duration.millis(1000), event -> {
            point.set(point.get()+clicAutomatique.getDps());
            points.setText(Integer.toString(point.get()));
        }));
        timer.setCycleCount(timer.INDEFINITE);
        timer.play();

        Button rab= new Button("Rabais= "+rabais.getPourcentRabais()+"%  prix= "+rabais.getCost()+" cookies");
        rab.setPrefSize(300,50);
        rab.setDisable(true);
        rab.setTranslateX(50);
        rab.setTranslateY(100);

        Button bouton= new Button("Cookie");
        bouton.setPrefSize(300,300);
        bouton.setTranslateX(200);
        bouton.setTranslateY(350);

        Button click2=  new Button("Double click= "+doubleClick.getCost()+ " cookies");
        click2.setPrefSize(300,50);
        click2.setTranslateY(50);
        click2.setTranslateX(50);
        click2.setDisable(true);

        Button autoclic = new Button("Clic auto = "+ (clicAutomatique.getDps()+2)+"/s   prix: "+clicAutomatique.getCost()+" cookies");
        autoclic.setPrefSize(300,50);
        autoclic.setTranslateX(50);
        autoclic.setTranslateY(150);
        autoclic.setDisable(true);

        Button argent = new Button("X clics for bonus: "+nombreClicArgent.getCost()+" cookies" );
        argent.setPrefSize(300,50);
        argent.setTranslateX(50);
        argent.setTranslateY(200);
        argent.setDisable(true);

        Button cookieBut = new Button("Surprise: "+ cookie.getCost()+ " cookies" );
        cookieBut.setPrefSize(300,50);
        cookieBut.setTranslateX(50);
        cookieBut.setTranslateY(250);
        cookieBut.setDisable(true);

        bouton.setOnAction((event)->{
                    point.set(augmenterBouton(point.get(),argent));
                    points.setText(Integer.toString(point.get()));
                    checkup(click2,rab,autoclic,argent,point,cookieBut);
                }
        );

        click2.setOnAction((event)->{
            point.set(point.get()-doubleClick.getCost());
            points.setText(Integer.toString(point.get()));
            doubleClick.setClicParSec(doubleClick.getClicParSec()*2);
            doubleClick.setCost((doubleClick.getCost()*3)+100);
            click2.setText("Double clic= "+Integer.toString(doubleClick.getCost())+ " cookies");
            checkup(click2,rab,autoclic,argent,point,cookieBut);
        });

        rab.setOnAction((event)->{


                point.set(point.get()-rabais.getCost());
                points.setText(Integer.toString(point.get()));
                rabais.setCost((rabais.getCost()*2)+200);
                doubleClick.setCost(doubleClick.getCost()-((doubleClick.getCost()*rabais.getPourcentRabais())/100));
                clicAutomatique.setCost(clicAutomatique.getCost()-((clicAutomatique.getCost()*rabais.getPourcentRabais())/100));
                nombreClicArgent.setCost(nombreClicArgent.getCost()-((nombreClicArgent.getCost()*rabais.getPourcentRabais())/100));
                cookie.setCost(cookie.getCost()-((cookie.getCost()*rabais.getPourcentRabais())/100));
                cookieBut.setText("Surprise: "+ cookie.getCost()+ " cookies");
                autoclic.setText("Clic auto = "+ (clicAutomatique.getDps()+1)+"/s   prix: "+clicAutomatique.getCost()+" cookies");
                click2.setText("Double clic= "+Integer.toString(doubleClick.getCost())+ "cookies");
                rabais.setPourcentRabais(rabais.getPourcentRabais()*2);
                if (nombreClicArgent.getCost()==400 || nombreClicArgent.getCost()==380 || nombreClicArgent.getCost()==342 || nombreClicArgent.getCost()==274){
                    argent.setText("X clics for bonus: "+nombreClicArgent.getCost()+" cookies" );
                }else {
                    argent.setText("Nombre de clic avant bonus: "+nombreClicArgent.getNombreClic()+" "+nombreClicArgent.getCost()+"$= + gros bonus");
                }

                rab.setText("Rabais= "+rabais.getPourcentRabais()+"%  prix= "+ Integer.toString(rabais.getCost())+ " cookies");
                checkup(click2,rab,autoclic,argent,point,cookieBut);
        });

        autoclic.setOnAction((event )->{
            point.set(point.get()-clicAutomatique.getCost());
            points.setText(Integer.toString(point.get()));
            clicAutomatique.setCost(clicAutomatique.getCost()*2);
            clicAutomatique.setDps(clicAutomatique.getDps()+2);
            autoclic.setText("Clic auto = "+ (clicAutomatique.getDps()+2)+"/s   prix: "+clicAutomatique.getCost());
            checkup(click2,rab,autoclic,argent,point,cookieBut);
        });

        argent.setOnAction((event)->{
            point.set(point.get()-nombreClicArgent.getCost());
            points.setText(Integer.toString(point.get()));
            nombreClicArgent.setCost((int)(nombreClicArgent.getCost()*(2.5)));
            argent.setText("Nombre de clic avant bonus: "+nombreClicArgent.getNombreClic()+" "+nombreClicArgent.getCost()+"$= + gros bonus");
            checkup(click2,rab,autoclic,argent,point,cookieBut);
        });

        cookieBut.setOnAction((event)->{
            point.set(point.get()-cookie.getCost());
            points.setText(Integer.toString(point.get()));
            cookie.setCost(cookie.getCost()*2);
            cookieBut.setText("Maxed");
            Image image = new Image("photo.png");
            ImageView photo =new ImageView(image);
            photo.setFitWidth(300);
            photo.setFitHeight(300);
            bouton.setGraphic(photo);
            bouton.setText("");
            checkup(click2,rab,autoclic,argent,point,cookieBut);
        });

        Group root= new Group(points,bouton,text,rab,click2,autoclic,argent,cookieBut);



        stage.setScene(
                new Scene(root)
        );

        stage.show();
}

public int augmenterBouton(int point,Button gold2){
        boolean gold=false;
        int cadeau=350;
        if (nombreClicArgent.getCost()>500){
            nombreClicArgent.setNombreClic(nombreClicArgent.getNombreClic()-1);
            gold2.setText("Nombre de clic avant bonus: "+nombreClicArgent.getNombreClic()+" "+nombreClicArgent.getCost()+"$= + gros bonus");
            if (nombreClicArgent.getNombreClic()==0){
                nombreClicArgent.setNombreClic(800);
                gold=true;
            }
        }
        if (gold){
            point=(point+cadeau+(1*doubleClick.getClicParSec()));
            cadeau=(cadeau+150);
            nombreClicArgent.setNombreClic(nombreClicArgent.getNombreClic()+50);
            gold=false;
        }else {
            point=point+(100*doubleClick.getClicParSec());



            //Changer le 1 pour un autre chiffre pour tester le programme
            //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!



        }
        return point;
}
public void checkup(Button clicX2,Button butRabais, Button butClicAuto, Button butArgent,AtomicInteger point, Button butSurprise){
        if (rabais.getPourcentRabais()>20){
            butRabais.setText("Maxed");
            butRabais.setDisable(true);
        }
        else if (point.get()<=rabais.getCost()){
        butRabais.setDisable(true);
        }
        else {
        butRabais.setDisable(false);
    }
    if (point.get()>=doubleClick.getCost()){
        clicX2.setDisable(false);
    }
    else {
        clicX2.setDisable(true);
    }
    if (point.get()>=clicAutomatique.getCost()){
        butClicAuto.setDisable(false);
    }
    else{
        butClicAuto.setDisable(true);
    }
    if (point.get()>= nombreClicArgent.getCost()){
        butArgent.setDisable(false);
    }
    else{
        butArgent.setDisable(true);
    }
    if (point.get()< cookie.getCost() || cookie.getCost()>= 701){
        butSurprise.setDisable(true);
    }
    else {
        butSurprise.setDisable(false);
    }
}
}
