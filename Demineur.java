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
  static class Case {
    int contenu = 0;
    boolean visible = false;
    boolean drapeau = false;
  }
  

  
  // Fonctions
  /**
   * Initialisation de la fenêtre en prenant en compte le nombre de grille
   *
   * @param nc nombre de colonnes de la grille
   * @param nl nombre de lignes de la grille
   */
  static void initialiserFenetre(int nc, int nl) {
    int tailleX = nc * (LARGEUR_CASE+2);
    int tailleY = nl * (HAUTEUR_CASE+2);
    EcranGraphique.init(50, 50, tailleX+50, tailleY+90, tailleX, tailleY, "Démineur");
  }

  static Case[][] initialiserGrille(int nc, int nl, int nm) {
    Case[][] grille = new Case[nc][nl] ;    
    for (int l = 0; l < nl; l++) {
      for (int c = 0; c < nc; c++) {
        grille[c][l] = new Case();
      }
    }
    for (int m = 0; m < nm; m++) {
      int x, y;
      do {
        x = (int)(Math.random()*nc);
        y = (int)(Math.random()*nl);
      }while( grille[x][y].contenu == MINE);
      grille[x][y].contenu = MINE;
    }
    for(int y = 0; y < nl; y++){
      for(int x = 0; x < nc; x++){
        if(grille[x][y].contenu != MINE){
          int compteur = 0;
          if(x == 0){
            if(y == 0){
              if(grille[x+1][y].contenu == MINE) compteur++;
              if(grille[x+1][y+1].contenu == MINE) compteur++;
              if(grille[x][y+1].contenu == MINE) compteur++;
            }
            else if(y == nl-1){
              if(grille[x+1][y].contenu == MINE) compteur++;
              if(grille[x+1][y-1].contenu == MINE) compteur++;
              if(grille[x][y-1].contenu == MINE) compteur++;
            }
            else{
              if(grille[x+1][y+1].contenu == MINE) compteur++;
              if(grille[x+1][y].contenu == MINE) compteur++;
              if(grille[x+1][y-1].contenu == MINE) compteur++;
              if(grille[x][y-1].contenu == MINE) compteur++;
              if(grille[x][y+1].contenu == MINE) compteur++;
            }
          }
          else if(x == nc-1){
            if(y == 0){
              if(grille[x-1][y].contenu == MINE) compteur++;
              if(grille[x-1][y+1].contenu == MINE) compteur++;
              if(grille[x][y+1].contenu == MINE) compteur++;
            }
            else if(y == nl-1){
              if(grille[x-1][y].contenu == MINE) compteur++;
              if(grille[x-1][y-1].contenu == MINE) compteur++;
              if(grille[x][y-1].contenu == MINE) compteur++;
            }
            else{
              if(grille[x-1][y+1].contenu == MINE) compteur++;
              if(grille[x-1][y].contenu == MINE) compteur++;
              if(grille[x-1][y-1].contenu == MINE) compteur++;
              if(grille[x][y-1].contenu == MINE) compteur++;
              if(grille[x][y+1].contenu == MINE) compteur++;
            }
          }
          else{
            if(y == 0){
              if(grille[x+1][y+1].contenu == MINE) compteur++;
              if(grille[x][y+1].contenu == MINE) compteur++;
              if(grille[x-1][y+1].contenu == MINE) compteur++;
              if(grille[x+1][y].contenu == MINE) compteur++;
              if(grille[x-1][y].contenu == MINE) compteur++;
            }
            else if(y == nl-1){
              if(grille[x-1][y-1].contenu == MINE) compteur++;
              if(grille[x][y-1].contenu == MINE) compteur++;
              if(grille[x+1][y-1].contenu == MINE) compteur++;
              if(grille[x+1][y].contenu == MINE) compteur++;
              if(grille[x-1][y].contenu == MINE) compteur++;
            }
            else{
              if(grille[x+1][y].contenu == MINE) compteur++;
              if(grille[x+1][y-1].contenu == MINE) compteur++;
              if(grille[x+1][y+1].contenu == MINE) compteur++;
              if(grille[x][y+1].contenu == MINE) compteur++;
              if(grille[x][y-1].contenu == MINE) compteur++;
              if(grille[x-1][y].contenu == MINE) compteur++;
              if(grille[x-1][y-1].contenu == MINE) compteur++;
              if(grille[x-1][y+1].contenu == MINE) compteur++;
            }
          }
          grille[x][y].contenu = compteur;
        }
      }
    }
    return grille;
  }
  
  static void afficher(int nc, int nl, Case [][] grille) {
    for(int l = 0; l < nl; l++) {
      for(int c = 0; c < nc; c++) {
        EcranGraphique.drawRect(3+(LARGEUR_CASE+1)*c, 3+(HAUTEUR_CASE+1)*l, LARGEUR_CASE, HAUTEUR_CASE);
        EcranGraphique.drawString(9+(LARGEUR_CASE+1)*c, 22+(HAUTEUR_CASE+1)*l, EcranGraphique.COLABA8x13, ""+grille[c][l].contenu);
      }
    }
  }
  
  
  public static void main(String[] args) {
   int nl, nc, nm;
    Case [][] grille;
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
  
    initialiserFenetre(nc, nl);
    grille = initialiserGrille(nc, nl, nm);
    afficher(nc, nl, grille);
  }
}
