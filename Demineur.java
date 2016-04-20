// Développeurs : Alexis Cabodi et Mohamed Lakhal ; Groupe de TP 5 (I4-CMI) ; Sujet n°4
public class Demineur {
  // Constantes pour la lisibilité
  final static int MINE = -1;
  final static int LARGEUR_CASE = 29; // 29 pixels plus 2 de bordure
  final static int HAUTEUR_CASE = 29;

  // Types agrégés
  
  /**
   * Une case du plateau
   * - contenu : nombre indiquant si la case contient une mine ou sinon le nombre de mines adjacentes
   * - visible : indique si la case est découverte ou pas
   * - drapeau : indique si la case a un drapeau ou non
   */
  public class Case {
    int contenu = 0;
    boolean visible = false;
    boolean drapeau = false;
  }
  

  
  // Fonctions
  /**
   * Initialisation de la fenêtre en prenant en compte le nombre de cases
   *
   * @param nc nombre de colonnes de la grille
   * @param nl nombre de lignes de la grille
   */
  static void initialiser(int nc, int nl) {
    int tailleX = nc * LARGEUR_CASE;
    int tailleY = nl * HAUTEUR_CASE;
    EcranGraphique.init(50, 50, tailleX+50, tailleY+90, tailleX, tailleY, "Démineur");
  }
  
  
  public static void main(String[] args) {
    int nl, nc, nm;
  
    Ecran.afficherln("Bienvenue dans le jeu du démineur !\nVous allez définir votre partie :");
    do {
      Ecran.afficher("Nombre de lignes : ");
      nl = Clavier.saisirInt();
      if (nl < 1) {
        Ecran.afficherln("La taille est trop petite. Merci de mettre une taille d'au moins 1.");
      } else if (nl > 50) {
        Ecran.afficherln("La taille est trop grande. Merci de ne pas dépasser 50.");
      }
    } while(nl < 1 || nl > 50);
  
    do {
      Ecran.afficher("Nombre de colonnes : ");
      nc = Clavier.saisirInt();
      if (nc < 1) {
        Ecran.afficherln("La taille est trop petite. Merci de mettre une taille d'au moins 1.");
      } else if (nc > 80) {
        Ecran.afficherln("La taille est trop grande. Merci de ne pas dépasser 80.");
      }
    } while(nc < 1 || nc > 80);
  
    do {
      Ecran.afficher("Nombre de mines : ");
      nm = Clavier.saisirInt();
      if (nm < 1) {
        Ecran.afficherln("La taille est trop petite. Merci de mettre une taille d'au moins 1.");
      } else if (nm >= nc * nl) {
        Ecran.afficherln("Il y a trop de mines. Merci d'en mettre moins que ", nc*nl, ".");
      }
    } while(nm < 1 || nm >= nc*nl);
  
    initialiser(nc, nl);
  
  
  }
}
