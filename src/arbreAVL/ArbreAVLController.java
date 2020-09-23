
package arbreAVL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class ArbreAVLController {

    public AnchorPane panel;
    ArbreAVL aavl=null;

    @FXML
    private Label label;
    @FXML
    private TextField champ_text1;
    @FXML
    private Button btn11;
    @FXML
    private Button btn12;
    @FXML
    private Button btn13;
    @FXML
    private Button btn2;
    @FXML
    private Button btn3;
    @FXML
    private AnchorPane pan;

    //Permet d'ajouter un élément entré par l'utilisateur dans la zone de texte.
    @FXML
    private void ajouterElem() {
        if(champ_text1.getText().equals("")){
            Alert care = new Alert(Alert.AlertType.WARNING);
            care.setContentText("Veuillez entrer une valeur !");
            care.show();
            return;
        }
        int numbre=Integer.parseInt(champ_text1.getText()); //Récupère le texte entré par l'utilisateur.
        aavl = ArbreAVL.inserer(aavl,numbre);               //Insère la valeur.
        label.setText("le nombre "+numbre+" a été ajouté"); //Informe que la valeur a été ajoutée.
        tracer_aAVL(aavl);
    }

    //Méthode appelé pour chercher un élément dans l'arbre
    @FXML
    private void chercherElem(ActionEvent event) {
        if(champ_text1.getText().equals("")){
            Alert care = new Alert(Alert.AlertType.WARNING);
            care.setContentText("Veuillez entrer une valeur !");
            care.show();
            return;
        }
        int numbre=Integer.parseInt(champ_text1.getText());
        if(ArbreAVL.chercher(aavl,numbre)){
            tracer_aAVL(aavl,numbre);
            label.setText("le nombre "+numbre+" a été trouvé");
        }
        else label.setText("Ce nombre n'existe pas dans l'arbre");

    }

    //Permet de supprimer un élément, entré par l'utilisateur, de l'arbre.
    @FXML
    private void supprimerElem() {
        if(champ_text1.getText().equals("")){
            Alert care = new Alert(Alert.AlertType.WARNING);
            care.setContentText("Veuillez entrer une valeur !");
            care.show();
            return;
        }
        int numbre=Integer.parseInt(champ_text1.getText());
        if(ArbreAVL.chercher(aavl,numbre)){
            aavl = ArbreAVL.supprimer(aavl,numbre);         //Supprime la valeur entrée dans la zone de texte.
            tracer_aAVL(aavl);
            label.setText("La valeur à été supprimé");      //Informe que la valeur a été supprimée.
        }

        //Si elle n'existe pas dans l'arbre, l'utilisateur est informé.
        else label.setText("Cette valeur n'existe pas dans l'arbre");
    }

    //Permet d'effacer l'arbre généré.
    @FXML
    private void reinitialiserArbre() {
        aavl = null;
        pan.getChildren().clear();
        label.setText("L'arbre a été réinitialisé");
    }

    //récupère la position
    private float position(float x1, float x2, ArbreAVL a){
        int nbFeuille = ArbreAVL.GetnbFeuille(a);
        if(nbFeuille == 0)
            nbFeuille =1;
        int nbFeuilleG = ArbreAVL.GetnbFeuille(a.filsG);
        if(nbFeuilleG == 0)
            nbFeuilleG =1;

        return ((nbFeuilleG*100/nbFeuille)*(x2-x1)/100 + x1) ;
    }

    private void tracer_aAVL(ArbreAVL a){
        if(a != null){
            pan.getChildren().clear();
            tracer_aAVL(0,(float)pan.getWidth()-20,a,40);
        }
    }

    private void tracer_aAVL(ArbreAVL a,Comparable o) {
        if (a != null) {
            pan.getChildren().clear();
            if (o == null)
                tracer_aAVL(0, (float) pan.getWidth() - 20, a, 40);
            else
                tracer_after_search(0, (float) pan.getWidth() - 20, a, 40, o);
        }
    }

    //Fonction permettant de tracer l'arbre
    private float tracer_aAVL(float x1,float x2,ArbreAVL a,float y){
        float xd, xg;
        float x = position(x1, x2, a);
        //après le calcule de la postion du noeud actuel je trace la cercle avec son contenu
        tracer_cercle(x,y,a.contenu);

        if( a.filsD != null){
            xd =tracer_aAVL(x, x2, a.filsD, y+50);
            tracer_droite(x+15, y+8, xd, y+35);
        }

        if(a.filsG !=null){
            xg =tracer_aAVL(x1, x, a.filsG, y+50);
            tracer_droite(x-15, y+8, xg, y+35);
        }

        return x;
    }

    //Permet d'indiquer la valeur recherchée.
    private float tracer_after_search(float x1,float x2,ArbreAVL a,float y,Comparable o){
        float xd, xg;
        float x = position(x1, x2, a);

        if(a.contenu.equals(o)) {
            ImageView im = new ImageView(new Image("ressources/new.gif"));
            im.setFitHeight(30);
            im.setFitWidth(30);
            im.setLayoutX(x-15);
            im.setLayoutY(y-35);
            pan.getChildren().addAll(im);
        }
        tracer_cercle(x,y,a.contenu);

        if( a.filsD != null){
            xd =tracer_after_search(x, x2, a.filsD, y+50,o);
            tracer_droite(x+15, y+8, xd, y+35);
        }

        if(a.filsG !=null){
            xg =tracer_after_search(x1, x, a.filsG, y+50,o);
            tracer_droite(x-15, y+8, xg, y+35);
        }
        return x;
    }

    //Fonction FX, permettant l'affichage d'un cercle
    private void tracer_cercle(float x,float y,Comparable info){
        Circle cercle=new Circle(x,y,15);
        Label label = new Label(""+info);
        if(Integer.parseInt(info.toString())<100)
            label.setLayoutX(x-6);
        else  label.setLayoutX(x-9);
        label.setLayoutY(y-9);
        label.setTextFill(Color.WHITE);
        pan.getChildren().add(cercle);
        pan.getChildren().add(label);
    }

    //Fonction FX, permettant l'affichage d'une droite a partir de 2 point A(x1,y1) et B(x2,y2)
    private void tracer_droite(float x1,float y1,float x2,float y2){
        Line line = new Line();
        line.setStartX(x1);
        line.setStartY(y1);
        line.setEndX(x2);
        line.setEndY(y2);
        pan.getChildren().add(line);
    }
}
    

