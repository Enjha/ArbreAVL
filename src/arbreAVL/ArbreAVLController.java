
//Cette classe permet le controle de l'interface graphique de l'application.

package arbreAVL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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
    private TextField valueField;
    @FXML
    private AnchorPane pan;

    //Permet d'ajouter un élément entré par l'utilisateur dans la zone de texte.
    @FXML
    private void ajouterElem() {
        if(valueField.getText().equals("")){
            Alert care = new Alert(Alert.AlertType.WARNING);
            care.setContentText("Veuillez entrer une valeur !");
            care.show();
            return;
        }
        int numbre=Integer.parseInt(valueField.getText()); //Récupère le texte entré par l'utilisateur.
        aavl = ArbreAVL.inserer(aavl,numbre);               //Insère la valeur.
        label.setText("Le nombre "+numbre+" a été ajouté !"); //Informe que la valeur a été ajoutée.
        tracer_aAVL(aavl);
    }

    //Méthode appelé pour chercher un élément dans l'arbre
    @FXML
    private void chercherElem(ActionEvent event) {
        if(valueField.getText().equals("")){
            Alert care = new Alert(Alert.AlertType.WARNING);
            care.setContentText("Veuillez entrer une valeur !");
            care.show();
            return;
        }
        int numbre=Integer.parseInt(valueField.getText());
        if(ArbreAVL.chercher(aavl,numbre)){
            tracer_aAVL(aavl,numbre);
            label.setText("Le nombre "+numbre+" a été trouvé ! ");
        }
        else label.setText("Ce nombre n'existe pas dans l'arbre.");

    }

    //Permet de supprimer un élément, entré par l'utilisateur, de l'arbre.
    @FXML
    private void supprimerElem() {
        if(valueField.getText().equals("")){
            Alert care = new Alert(Alert.AlertType.WARNING);
            care.setContentText("Veuillez entrer une valeur !");
            care.show();
            return;
        }
        int numbre=Integer.parseInt(valueField.getText());
        if(ArbreAVL.chercher(aavl,numbre)){
            aavl = ArbreAVL.supprimer(aavl,numbre);         //Supprime la valeur entrée dans la zone de texte.
            tracer_aAVL(aavl);                              //Retrace l'arbre sans la valeur supprimé.
            label.setText("La valeur à été supprimé.");      //Informe que la valeur a été supprimée.
        }

        //Si elle n'existe pas dans l'arbre, l'utilisateur est informé.
        else label.setText("Cette valeur n'existe pas dans l'arbre.");
    }

    //Permet d'effacer l'arbre généré.
    @FXML
    private void reinitialiserArbre() {
        aavl = null;
        pan.getChildren().clear();
        label.setText("L'arbre a été réinitialisé.");
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

    //3 fonction tracer_aAVL sont utilisée mais avec des parmètres
    // différents cela s'apelle de la surcharge.

    private void tracer_aAVL(ArbreAVL a){
        if(a != null){
            pan.getChildren().clear();
            tracer_aAVL(20,(float)pan.getWidth()-20,a,40);
        }
    }

    private void tracer_aAVL(ArbreAVL a,Comparable o) {
        if (a != null) {
            pan.getChildren().clear();
            if (o == null)
                tracer_aAVL(0, (float) pan.getWidth()/2, a, 40);
            else
                tracer_after_search(0, (float) pan.getWidth() - 20, a, 40, o);
        }
    }

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
            xg =tracer_aAVL(x1+20, x-50, a.filsG, y+50);
            tracer_droite(x-15, y+8, xg, y+35);
        }
        return x;
    }

    //Fonction permettant de tracer l'arbre
    private float tracer_after_search(float x1,float x2,ArbreAVL a,float y,Comparable o){
        float xd, xg;
        float x = position(x1, x2, a);

        if(a.contenu.equals(o)) {                                   //Permet d'indiquer la valeur recherchée.
            ImageView im = new ImageView(new Image("ressources/Flèche.jpg"));
            im.setFitHeight(30);
            im.setFitWidth(30);
            im.setLayoutX(x-15);
            im.setLayoutY(y-40);
            pan.getChildren().addAll(im);
        }
        tracer_cercle(x,y,a.contenu);

        if( a.filsD != null){
            xd =tracer_after_search(x, x2, a.filsD, y+50,o);
            tracer_droite(x+15, y+8, xd, y+35);
        }

        if(a.filsG !=null){
            xg =tracer_after_search(x1+20, x-50, a.filsG, y+50,o);
            tracer_droite(x-15, y+8, xg, y+35);
        }
        return x;
    }

    //Fonction FX, permettant l'affichage d'un cercle
    private void tracer_cercle(float x,float y,Comparable info){
        Circle cercle=new Circle(x,y,15);
        cercle.setStroke(Color.BLACK);
        cercle.setFill(null);
        Label label = new Label(""+info);
        if(Integer.parseInt(info.toString())<100)
            label.setLayoutX(x-8);
        else  label.setLayoutX(x-12);
        label.setLayoutY(y-10);
        label.setTextFill(Color.BLACK);
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
    

