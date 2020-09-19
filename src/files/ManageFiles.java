package files;

import java.io.File;

// Gérer les fichiers .txt et dossier

public class ManageFiles {

    private static String fileName = "avl";

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
            File f = new File("AVL/ressources\\"+fileName+".txt");
            if (f.createNewFile())
                System.out.println("File "+fileName+" has been created.");
            else {
                System.out.println("File "+fileName+" already exist.");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void removeFile(){
        try{
            File file = new File("AVL/ressources\\"+fileName+".txt");
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
