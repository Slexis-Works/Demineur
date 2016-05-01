// Développeurs : Alexis Cabodi et Mohamed Lakhal ; Groupe de TP 5 (I4-CMI) ; Sujet n°4
public class Demineur {
  // Constantes pour la lisibilité
  final static int MINE = -1;
  final static int LARGEUR_CASE = 29; // 29 pixels plus 2 de bordure
  final static int HAUTEUR_CASE = 29;
  // Statuts de la grille
  final static int JOUABLE = 0;
  final static int GAGNEE = 1;
  final static int PERDUE = 2;

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
   * Initialisation de la fenêtre en prenant en compte le nombre de cases et de la place pour le message de fin de partie
   *
   * @param nc nombre de colonnes de la grille
   * @param nl nombre de lignes de la grille
   */
  static void initialiserFenetre(int nc, int nl) {
    int tailleX = 200 + nc * (LARGEUR_CASE+1);
    int tailleY = 30 + nl * (HAUTEUR_CASE+1);
    EcranGraphique.init(50, 50, tailleX+50, tailleY+90, tailleX, tailleY, "Démineur");
    EcranGraphique.setClearColor(0, 0, 0);
  }
	
  /**
   * Initialisation de la grille en prenant en compte le nombre de cases et de mines
   * Tirage aléatoire des coordonnées des cases minées
   * Comptage des mines adjacentes aux cases (x, y)
   */
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
  
  /**
   * Affichage avec la classe EcranGraphique (drapeau, grille, case) selon leur visibilité
   */
  static void afficher(int nc, int nl, Case [][] grille, int[][] image) {
    EcranGraphique.clear();
    EcranGraphique.setColor(0, 255, 0);
    EcranGraphique.drawRect(4, 4, 1+(LARGEUR_CASE+1)*nc, 1+(HAUTEUR_CASE+1)*nl);
    for(int l = 0; l < nl; l++) {
      for(int c = 0; c < nc; c++) {
        EcranGraphique.setColor(0, 255, 0);
        EcranGraphique.drawRect(5+(LARGEUR_CASE+1)*c, 5+(HAUTEUR_CASE+1)*l, LARGEUR_CASE, HAUTEUR_CASE);
        if (grille[c][l].visible) {
          if (grille[c][l].contenu == MINE) {
            // Draw image ou cercle rouge
          } else if (grille[c][l].contenu > 0) {
            EcranGraphique.drawString(15+(LARGEUR_CASE+1)*c, 26+(HAUTEUR_CASE+1)*l, EcranGraphique.COLABA8x13, ""+grille[c][l].contenu);
          } // "0" non affichés
        } else if (grille[c][l].drapeau) {
          /*EcranGraphique.setColor(240, 0, 0);
          EcranGraphique.fillRect(6+(LARGEUR_CASE+1)*c, 6+(HAUTEUR_CASE+1)*l, LARGEUR_CASE-1, HAUTEUR_CASE-1);*/
          EcranGraphique.drawImage(6+(LARGEUR_CASE+1)*c, 6+(HAUTEUR_CASE+1)*l, image);
        } else { // Non visible, "cache" bleu
          EcranGraphique.setColor(0, 0, 240);
          EcranGraphique.fillRect(6+(LARGEUR_CASE+1)*c, 6+(HAUTEUR_CASE+1)*l, LARGEUR_CASE-1, HAUTEUR_CASE-1);
        }
      }
    }
    EcranGraphique.setColor(0, 255, 0);
    EcranGraphique.drawString(30 + nc * (LARGEUR_CASE+1), 30, EcranGraphique.COLABA8x13, "JEU : DEMINEUR");
    EcranGraphique.flush();
  }
  
  /**
   * Fonction qui va attendre un clic du joueur et agir en conséquence
   */
  static void traiterEntree(int nc, int nl, Case [][] grille) {
    while (EcranGraphique.getMouseState()!=2) {
      EcranGraphique.wait(10); // Ne pas surcharger le processeur
    }
    // getMouseButton pour savoir si c'était un clic gauche ou droit
    int clic = EcranGraphique.getMouseButton();
    // getMouseX|Y pour la position
    EcranGraphique.getMouseX();
    EcranGraphique.getMouseY();
    // faire le calcul qui détermine la cellule cliquée (au mieux on évite les bordures)
    int x = (EcranGraphique.getMouseX()-3)/(LARGEUR_CASE+1);
    int y = (EcranGraphique.getMouseY()-3)/(HAUTEUR_CASE+1);
    // si y'a un drapeau on empêche le clic gauche
    if (x >= 0 && x < nc && y >= 0 && y < nl) {
      if (grille[x][y].drapeau) {
        if (clic == 1) {
          Ecran.afficherln("CLIC GAUCHE IMPOSSIBLE SUR UN DRAPEAU !");
        }
        // si clic droit, on inverse l'état du drapeau
        else if (clic == 3) {
          grille[x][y].drapeau = false;
        }
      }
      else {
        if (clic == 3) grille[x][y].drapeau = true;
      }
      // si on peut découvrir la case, appeler decouvrirCase
      if (clic == 1 && !grille[x][y].drapeau) {
        decouvrirCase(nc, nl, grille, x, y);
      }
    }
  }
  
  /**
   * Fonction qui permet de découvrir une case
   * et de continuer de manière récursive s'il n'y a pas de mines
   */
  static void decouvrirCase(int nc, int nl, Case [][] grille, int c, int l) {
    grille[c][l].visible = true;
    if (grille[c][l].contenu == 0) {
      // Exploration dans les 4 directions
      if (l > 0 && !grille[c][l-1].visible && grille[c][l-1].contenu != MINE)
        decouvrirCase(nc, nl, grille, c, l-1); // Continuer en haut
      if (l < nl-1 && !grille[c][l+1].visible && grille[c][l+1].contenu != MINE)
        decouvrirCase(nc, nl, grille, c, l+1); // Continuer en bas
      if (c > 0 && !grille[c-1][l].visible && grille[c-1][l].contenu != MINE)
        decouvrirCase(nc, nl, grille, c-1, l); // Continuer à gauche
      if (c < nc-1 && !grille[c+1][l].visible && grille[c+1][l].contenu != MINE)
        decouvrirCase(nc, nl, grille, c+1, l); // Continuer à droite
    }
  }
  
  /**
   * Fonction pour tester si le joueur a terminé sa partie ou non.
   * Si oui, on précise si elle est gagnée ou perdue.
   */
  static int statutGrille(int nc, int nl, int nm, Case [][] grille) {
    boolean aMineDecouverte = false, aCasePasDecouverte = false;
    int c=0, l=0, nbMinesMarquees = 0;
    while (l<nl && !aMineDecouverte) {
      if (grille[c][l].contenu == MINE && grille[c][l].visible) {
        aMineDecouverte = true;
      } else {
        if (grille[c][l].drapeau) {
          if (grille[c][l].contenu == MINE)
            nbMinesMarquees++;
          else
            nbMinesMarquees = nm+1; // Empêche de gagner en mettant des drapeaux partout
        }
        c++;
        if (c == nc) {
          c = 0;
          l++;
        }
      }
    }
    if (nbMinesMarquees == nm)
      return GAGNEE;
    if (aMineDecouverte)
      return PERDUE;
    
    c=0;
    l=0;
    while (l<nl && !aCasePasDecouverte) {
      if (grille[c][l].contenu != MINE && !grille[c][l].visible) {
        aCasePasDecouverte = true;
      } else {
        c++;
        if (c == nc) {
          c = 0;
          l++;
        }
      }
    }
    if (!aCasePasDecouverte)
      return GAGNEE;
    
    return JOUABLE;
  }
  
  public static void main(String[] args) {
    int tailleX;
    int tailleY;
    int nl, nc, nm;
    Case [][] grille;
    int [][] image = EcranGraphique.loadPNGFile("drapeau.png");
    int statutPartie = 0;
    Ecran.afficherln("Bienvenue dans le jeu du démineur !\nVous allez définir votre partie :");
    do {
      do {
        Ecran.afficher("Nombre de lignes : ");
        nl = Clavier.saisirInt();
        if (nl < 1) {
          Ecran.afficherln("Nombre de lignes trop petit. Merci de mettre une taille d'au moins 1.");
        } else if (nl > 50) {
          Ecran.afficherln("Nombre de lignes trop élevé. Merci de ne pas dépasser 50.");
        }
      } while(nl < 1 || nl > 50);
    
      do {
        Ecran.afficher("Nombre de colonnes : ");
        nc = Clavier.saisirInt();
        if (nc < 1) {
          Ecran.afficherln("Nombre de colonnes trop petit. Merci de mettre une taille d'au moins 1.");
        } else if (nc > 80) {
          Ecran.afficherln("Nombre de colonnes trop élevé. Merci de ne pas dépasser 80.");
        }
      } while(nc < 1 || nc > 80);
      if (nc*nl == 1) Ecran.afficherln("Taille 1*1 interdite !"); 
    } while(nc*nl == 1);
  
    do {
      Ecran.afficher("Nombre de mines : ");
      nm = Clavier.saisirInt();
      if (nm < 1) {
        Ecran.afficherln("Nombre de mines trop petit. Merci de mettre une taille d'au moins 1.");
      } else if (nm >= nc * nl) {
        Ecran.afficherln("Il y a trop de mines. Merci d'en mettre moins que ", nc*nl, ".");
      }
    } while(nm < 1 || nm >= nc*nl);
  
    // Dimensions de la grille pour s'en servir
    tailleX = 10 + nc * (LARGEUR_CASE+1);
    tailleY = 10 + nl * (HAUTEUR_CASE+1);
    initialiserFenetre(nc, nl);
    grille = initialiserGrille(nc, nl, nm);
    afficher(nc, nl, grille, image);
    
    do {
      traiterEntree(nc, nl, grille);
      statutPartie = statutGrille(nc, nl, nm, grille);
      
      afficher(nc, nl, grille, image);
    } while (statutPartie == JOUABLE);
    Ecran.afficherln(statutPartie);
    // Message pour le joueur
    if (statutPartie == PERDUE) {
      EcranGraphique.setColor(255,0,0);
      EcranGraphique.drawString(10, tailleY+13, EcranGraphique.COLABA8x13, "LOSER!!");
    }
    else {
      EcranGraphique.setColor(0,0,255);
      EcranGraphique.drawString(10, tailleY+13, EcranGraphique.COLABA8x13, "GGWP!!");
    }
    EcranGraphique.flush();
  }
}
