package arbreAVL;

public class ArbreAVL{

    Comparable contenu;
    int haut;
    ArbreAVL filsG,filsD;

    //Création de l'objet arbre.
    //Il possède un contenu, un fils gauche, fils droite et une hauteur.
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

    // Si l'arbre est déséquilibré à gauche alors :
    private static ArbreAVL equilbreG(ArbreAVL a){
        //Met sous forme d'ensemble comme dans les exemple afin de ne pas perdre les sous arbres.
        ArbreAVL b= a.filsD;
        ArbreAVL c = new ArbreAVL(a.contenu, a.filsG, b.filsG);
        //Effectue le changement de place.
        return new ArbreAVL(b.contenu,c,b.filsD);
    }

    private static ArbreAVL equilibreD(ArbreAVL a){
        //Met sous forme d'ensemble comme dans les exemple afin de ne pas perdre les sous arbres.
        ArbreAVL c = a.filsG;
        ArbreAVL b = new ArbreAVL(a.contenu, c.filsD, a.filsD);
        //Effectue le changement de place.
        return new ArbreAVL(c.contenu, c.filsG,b);
    }
    private static ArbreAVL equilibrer(ArbreAVL a){
        //Calcule la hauteur.
        hauteur(a);
        // On compare la hauteur à partir d'un noeud afin de vérifier si elle est équilibrée ou non.
        // Si elle ne l'est pas, alors on effectue l'équilibrage grâce aux fonctions ci-dessus.

        //On vérifie si le déséquilibrage se situe à gauche.
        if (hauteur(a.filsG) -hauteur(a.filsD) == 2)
        {
            //On vérifie ici si elles sont déséquilibrées par la l'extérieur ou l'intérieur).
            //Si à l'intérieur :
            if (hauteur(a.filsG.filsG) < hauteur(a.filsG.filsD))
                a.filsG= equilbreG(a.filsG);
            //Si à l'exterieur :
            return equilibreD(a);
        }

        //On vérifie si le déséquilibrage se situe à droite.
        if (hauteur(a.filsG)-hauteur(a.filsD) == -2)
        {
            //On vérifie ici si elles sont déséquilibrées par la l'extérieur ou l'intérieur).
            //Si à l'intérieur :
            if (hauteur(a.filsD.filsD) < hauteur(a.filsD.filsG))
                a.filsD= equilibreD(a.filsD);
            //Si à l'extérieur :
            return equilbreG(a);
        }
        //S'il n'y a pas de déséquilibre, alors on return l'arbre.
        return a;
    }

    // Permet de donner le nombre de feuille de l'abre AVL. Sert pour la partie graphique.
    public static int GetnbFeuille(ArbreAVL r){
        if(r==null)
            return 0;
        return 1+(GetnbFeuille(r.filsD)+GetnbFeuille(r.filsG));
    }

    // Permet d'insérer une valeur demandée :
    public static ArbreAVL inserer(ArbreAVL a, Comparable x){
        int h = hauteur(a);
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

        //Si la hauteur de la taille est modifiée alors pas besoin d'équilibrer.
        if(h == a.haut)
            return a;
        else
            return equilibrer(a);

    }

    // On supprime une valeur demandée :
    public static ArbreAVL supprimer(ArbreAVL a, Comparable x){
        int h = hauteur(a);
        // On vérifie que la valeur entrée est égale à une valeur de l'abre si non on la retourne.
        //Si la valeur qu'on veut supprimer est la racine, c'est unc as particulier, voir fonction en dessous "supprimerRacine"
        if(a.contenu.compareTo(x)==0)
            return supprimerRacine(a);
        //Ces conditions permettent de prendre tous les cas possibles en considération.
        if(a.contenu.compareTo(x) > 0)
            a.filsG=supprimer(a.filsG,x);
        else
            a.filsD=supprimer(a.filsD,x);

        // Après la suppression, on l'équilibre de nouveau.
        //Si la hauteur de la taille est modifiée alors pas besoin d'équilibrer.
        if(h == a.haut)
            return a;
        else
            return equilibrer(a);
    }

    // Permet de chercher une valeur demandée :
    public static boolean chercher(ArbreAVL a , Comparable x){
        //On vérifie que la valeur demandée est présente dans l'arbre. Si oui, on la trouve, si non, on retourne faux.
        if(a== null)
            return false;
        //Vérifie pour toutes les valeurs >=0.
        if(a.contenu.compareTo(x)==0)
            return true;
        if(a.contenu.compareTo(x) < 0)
            return chercher(a.filsD, x);
        //On return la valeur trouvée.
        return chercher(a.filsG, x);
    }


    private static ArbreAVL supprimerRacine(ArbreAVL a){
        // Si elle n'a pas de fils gauche, on retourne le fils droit en tant que nouvelle racine.
        if(a.filsG==null)
            return a.filsD;
        // Si elle n'a pas de fils droit, on retourne le fils gauche en ntant que nouvelle racine.
        if(a.filsD==null) {
            return a.filsG;
        }

        ArbreAVL r1 = a.filsG;
        ArbreAVL pere= a;

        //tant qu'on atteint la valeur la plus a droite de la branche de Gauche de l'arbre principale
        while(r1.filsD != null) {
            pere= r1;    // on réaffecte le vrai père du fils tant que l'on descend d'un étage
            r1 = r1.filsD; // on réaffecte aussi l'arbre temporaire
        }
        a.contenu=r1.contenu;
        if(pere == a)
            pere.filsG=r1.filsG;
        else
            pere.filsD=r1.filsG;
        return a;
    }

}









