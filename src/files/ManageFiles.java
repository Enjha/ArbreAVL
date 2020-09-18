package files;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

// Gérer les fichiers .txt

public class ManageFiles {

    private static String scanF(String txt){
        try {
            System.out.println(txt);
            BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
            return bufferRead.readLine();
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static void createFolder(){
        File file = new File("AVL/ressources");
        if (!file.exists()) {
            if (file.mkdir()) {
                System.out.println("Ajout du dossier : " + file.getAbsolutePath());
            } else {
                System.out.println("Echec sur le dossier : " + file.getAbsolutePath());
            }
        }
    }

    public static void createFile(){
        createFolder();
        try {
            String name = scanF("Entrez le nom du fichier que vous voulez créer: ");
            File f = new File("AVL/ressources\\"+name+".txt");
            if (f.createNewFile())
                System.out.println("File "+name+" has been created.");
            else {
                System.out.println("File "+name+" already exist.");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void removeFile(){
        try{
            String name = scanF("Entrez le nom du fichier que vous voulez supprimé: ");
            File file = new File("AVL/ressources\\"+name+".txt");
            if(file.delete()){
                System.out.println(file.getName() + " est supprimé.");
            }else{
                System.out.println("Opération de suppression echouée");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
