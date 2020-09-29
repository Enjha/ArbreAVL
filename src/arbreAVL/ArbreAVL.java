package arbreAVL;

public class ArbreAVL{

    Comparable contenu;
    int haut;
    ArbreAVL filsG,filsD;

    ArbreAVL(Comparable c, ArbreAVL filsG, ArbreAVL filsD){
        this.contenu = c;
        this.filsG= filsG;
        this. filsD= filsD;
        this.haut= 1 + Math.max(hauteur(filsG), hauteur(filsD));
    }

    // Initialise le contenu à vide.
    public ArbreAVL(Comparable c){
        this(c,null,null);
    }

    // Permet de donner la hauteur d'une branche à partir d'une feuille donnée.
    public static int hauteur(ArbreAVL a){
        if(a== null) return -1;
        // Fonction qui permet de calculer le Max de deux valeurs.
        return 1+Math.max(hauteur(a.filsG),hauteur(a.filsD));
    }

    // Permet de calculer la hauteur de l'arbre.
    private static void calculerHauteur(ArbreAVL a){
        if(a!=null){
            calculerHauteur(a.filsG);
            calculerHauteur(a.filsD);
            // Fonction qui permet de calculer le Max de deux valeurs.
            a.haut= 1 + Math.max(hauteur(a.filsG), hauteur(a.filsD));
        }
    }

    // Si l'arbre est déséquilibré à gauche alors :
    private static ArbreAVL equilbreG(ArbreAVL a){
        ArbreAVL b= a.filsD;
        ArbreAVL c = new ArbreAVL(a.contenu, a.filsG, b.filsG);
        return new ArbreAVL(b.contenu,c,b.filsD);
    }
    private static ArbreAVL equilibreD(ArbreAVL a){
        ArbreAVL c = a.filsG;
        ArbreAVL b = new ArbreAVL(a.contenu, c.filsD, a.filsD);
        return new ArbreAVL(c.contenu, c.filsG,b);
    }
    private static ArbreAVL equilibrer(ArbreAVL a){
        calculerHauteur(a);
        // On compare la hauteur à partir d'une racine afin de vérifier si elle est équilibrée ou non.
        // Si elle ne l'est pas, alors on effectue l'équilibrage grâce aux fonctions ci-dessus suivant si elles sont
        // déséquilibrée par la gauche ou la droite.
        if (hauteur(a.filsG) -hauteur(a.filsD) == 2)
        {
            if (hauteur(a.filsG.filsG) < hauteur(a.filsG.filsD))
                a.filsG= equilbreG(a.filsG);
            return equilibreD(a);
        } // else
        if (hauteur(a.filsG)-hauteur(a.filsD) == -2)
        {
            if (hauteur(a.filsD.filsD) < hauteur(a.filsD.filsG))
                a.filsD= equilibreD(a.filsD);
            return equilbreG(a);
        }
        return a;
    }

    // Permet de donner le nombre de feuille de l'abre AVL.
    public static int GetnbFeuille(ArbreAVL r){
        if(r==null)
            return 0;
        return 1+(GetnbFeuille(r.filsD)+GetnbFeuille(r.filsG));
    }

    // Permet d'insérer une valeur demandée :
    public static ArbreAVL inserer(ArbreAVL a, Comparable x){
        // Si l'arbre est vide alors on en créé un avec comme première valeur x.
        if (a == null)
            return new ArbreAVL(x);
        // S'il existe déjà alors on insère la valeur x à l'emplacement qui convient en utilisant la fonction
        // CompareTo. Cela permet de l'ajouter à la fin de l'arbre sans penser à l'équilibrage mais à gauche si plus
        // petit ou à droite si plus grand.
        if (a.contenu.compareTo(x)>0)
            a.filsG= inserer(a.filsG,x);
        else
            a.filsD= inserer(a.filsD,x);

        // On lance la fonction équilibrage afin d'équilibré si nécessaire.
        return equilibrer(a);
    }

    // On supprime une valeur demandée :
    public static ArbreAVL supprimer(ArbreAVL a, Comparable x){

        // On vérifie que la valeur entrée est égale à une valeur de l'abre si non on la retourne.

        if(a.contenu.compareTo(x)==0)
            return supprimerRacine(a);
        if(a.contenu.compareTo(x) > 0)
            a.filsG=supprimer(a.filsG,x);
        else
            a.filsD=supprimer(a.filsD,x);

        // Après la suppression, on l'équilibre de nouveau.
        return equilibrer(a);
    }

    // Permet de chercher une valeur demandée :
    public static boolean chercher(ArbreAVL a , Comparable x){
        //On vérifie que la valeur demandée est présente dans l'arbre. Si oui, on la trouve, si non, on retourne faux.
        if(a== null)
            return false;
        if(a.contenu.compareTo(x)==0)
            return true;
        if(a.contenu.compareTo(x) < 0)
            return chercher(a.filsD, x);

        return chercher(a.filsG, x);
    }

    // Permet de supprimer la racine de l'arbre AVL :
    private static ArbreAVL supprimerRacine(ArbreAVL a){
        // Si elle n'a pas de fils gauche, on retourne le fils droit.
        if(a.filsG==null)
            return a.filsD;
        // Si elle n'a pas de fils droit, on retourne le fils gauche.
        if(a.filsD==null)
            return a.filsG;

        ArbreAVL r1 = a.filsG;
        ArbreAVL pere= a;
        // Recherche du plus grand élément du sous arbre gauche afin de le placer en racine.
        while(r1.filsD != null) {
            pere= r1;
            r1 = r1.filsD;
        }
        a.contenu=r1.contenu;
        if(pere == a)
            pere.filsG=r1.filsG;
        else
            pere.filsD=r1.filsG;
        return a;
    }

}









