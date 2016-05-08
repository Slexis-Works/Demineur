// Développeurs : Alexis Cabodi et Mohamed Lakhal ; Groupe de TP 5 (I4-CMI) ; Sujet n°4
public class DemineurProjet {
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
  
  /**
   * Structure avec des données concernant le jeu
   * - nc : nombre de colonnes sur la grille
   * - nl : nombre de lignes sur la grille
   * - nm : nombre de mine dans la grille
   * - tempsDebut : heure du système au moment du premier clic sur la grille
   * - tailleX|Y : dimensions en pixels de la grille (pour des calculs de position)
   */
  static class Demineur {
    int nc, nl, nm;
    double tempsDebut = -1.0;
    int tailleX, tailleY;
  }
  
  // Fonctions
  /**
   * Initialisation de la fenêtre en prenant en compte le nombre de cases et de la place pour le message de fin de partie
   *
   * @param nc nombre de colonnes de la grille
   * @param nl nombre de lignes de la grille
   */
  static void initialiserFenetre(Demineur dem) {
    dem.tailleX = 10 + dem.nc * (LARGEUR_CASE+1);
    dem.tailleY = 10 + dem.nl * (HAUTEUR_CASE+1);
    EcranGraphique.init(50, 50, dem.tailleX+390, dem.tailleY+110, dem.tailleX + 350, dem.tailleY + 30, "Démineur");
    EcranGraphique.setClearColor(0, 0, 0);
  }
	
  /**
   * Initialisation de la grille en prenant en compte le nombre de cases et de mines
   * Tirage aléatoire des coordonnées des cases minées
   * Comptage des mines adjacentes aux cases (x, y)
   */
  static Case[][] initialiserGrille(Demineur dem) {
    Case[][] grille = new Case[dem.nc][dem.nl] ;    
    for (int l = 0; l < dem.nl; l++) {
      for (int c = 0; c < dem.nc; c++) {
        grille[c][l] = new Case();
      }
    }
    for (int m = 0; m < dem.nm; m++) {
      int x, y;
      do {
        x = (int)(Math.random()*dem.nc);
        y = (int)(Math.random()*dem.nl);
      } while(grille[x][y].contenu == MINE);
      grille[x][y].contenu = MINE;
    }
    for(int y = 0; y < dem.nl; y++){
      for(int x = 0; x < dem.nc; x++){
        if(grille[x][y].contenu != MINE){
          int compteur = 0;
          if(x == 0){
            if(y == 0){
              if(grille[x+1][y].contenu == MINE) compteur++;
              if(grille[x+1][y+1].contenu == MINE) compteur++;
              if(grille[x][y+1].contenu == MINE) compteur++;
            }
            else if(y == dem.nl-1){
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
          else if(x == dem.nc-1){
            if(y == 0){
              if(grille[x-1][y].contenu == MINE) compteur++;
              if(grille[x-1][y+1].contenu == MINE) compteur++;
              if(grille[x][y+1].contenu == MINE) compteur++;
            }
            else if(y == dem.nl-1){
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
            else if(y == dem.nl-1){
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
  static void afficher(Demineur dem, Case [][] grille, int[][] imageFlag, int[][] imageMine, int[][] imageClk) {
    EcranGraphique.clear();
    EcranGraphique.setColor(0, 255, 0);
    EcranGraphique.drawRect(4, 4, 1+(LARGEUR_CASE+1)*dem.nc, 1+(HAUTEUR_CASE+1)*dem.nl);
    for(int l = 0; l < dem.nl; l++) {
      for(int c = 0; c < dem.nc; c++) {
        EcranGraphique.setColor(0, 255, 0);
        EcranGraphique.drawRect(5+(LARGEUR_CASE+1)*c, 5+(HAUTEUR_CASE+1)*l, LARGEUR_CASE, HAUTEUR_CASE);
        if (grille[c][l].visible) {
          if (grille[c][l].contenu == MINE) {
            // Draw image ou cercle rouge
            EcranGraphique.drawImage(6+(LARGEUR_CASE+1)*c, 6+(HAUTEUR_CASE+1)*l, imageMine);
          } else if (grille[c][l].contenu > 0) {
            EcranGraphique.drawString(15+(LARGEUR_CASE+1)*c, 26+(HAUTEUR_CASE+1)*l, EcranGraphique.COLABA8x13, ""+grille[c][l].contenu);
          } // "0" non affichés
        } else if (grille[c][l].drapeau) {
          /*EcranGraphique.setColor(240, 0, 0);
          EcranGraphique.fillRect(6+(LARGEUR_CASE+1)*c, 6+(HAUTEUR_CASE+1)*l, LARGEUR_CASE-1, HAUTEUR_CASE-1);*/
          EcranGraphique.drawImage(6+(LARGEUR_CASE+1)*c, 6+(HAUTEUR_CASE+1)*l, imageFlag);
        } else { // Non visible, "cache" bleu
          EcranGraphique.setColor(0, 0, 240);
          EcranGraphique.fillRect(6+(LARGEUR_CASE+1)*c, 6+(HAUTEUR_CASE+1)*l, LARGEUR_CASE-1, HAUTEUR_CASE-1);
        }
      }
    }
    EcranGraphique.setColor(0, 255, 0);
    EcranGraphique.drawString(30 + dem.tailleX, 30, EcranGraphique.COLABA8x13, "JEU : DEMINEUR");
    if (dem.tempsDebut == -1.0)
      EcranGraphique.drawString(220 + dem.tailleX, 30, EcranGraphique.COLABA8x13, "Attente...");
    else
      EcranGraphique.drawString(220 + dem.tailleX, 30, EcranGraphique.COLABA8x13, Math.floor(((double)System.currentTimeMillis()/1000.0-dem.tempsDebut)*10)/10 + "s.");
    EcranGraphique.drawString(30 + dem.tailleX, 70, EcranGraphique.COLABA8x13, "CLIC GAUCHE : LIBEREZ UNE CASE");
    EcranGraphique.drawString(30 + dem.tailleX, 90, EcranGraphique.COLABA8x13, "CLIC DROIT : POSER UN DRAPEAU");
    EcranGraphique.drawImage(180 + dem.tailleX, 10, imageClk);
    EcranGraphique.flush();
  }
  
  /**
   * Fonction qui va attendre un clic du joueur et agir en conséquence
   */
  static boolean traiterEntree(Demineur dem, Case [][] grille) {
    if (EcranGraphique.getMouseState()!=2) {
      //EcranGraphique.wait(10); // Ne pas surcharger le processeur
      return false;
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
    if (x >= 0 && x < dem.nc && y >= 0 && y < dem.nl) {
      if (grille[x][y].drapeau) {
        if (clic == 1) {
          Ecran.afficherln("CLIC GAUCHE IMPOSSIBLE SUR UN DRAPEAU !");
          return false;
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
        decouvrirCase(dem, grille, x, y);
      }
      return true;
    }
    else {
      return false;
    }
  }
  
  /**
   * Fonction qui permet de découvrir une case
   * et de continuer de manière récursive s'il n'y a pas de mines
   */
  static void decouvrirCase(Demineur dem, Case [][] grille, int c, int l) {
    grille[c][l].visible = true;
    if (grille[c][l].contenu == 0) {
      // Exploration dans les 8 directions
      if (l > 0 && !grille[c][l-1].visible && grille[c][l-1].contenu != MINE)
        decouvrirCase(dem, grille, c, l-1); // Continuer en haut
      if (l < dem.nl-1 && !grille[c][l+1].visible && grille[c][l+1].contenu != MINE)
        decouvrirCase(dem, grille, c, l+1); // Continuer en bas
      if (c > 0 && !grille[c-1][l].visible && grille[c-1][l].contenu != MINE)
        decouvrirCase(dem, grille, c-1, l); // Continuer à gauche
      if (c < dem.nc-1 && !grille[c+1][l].visible && grille[c+1][l].contenu != MINE)
        decouvrirCase(dem, grille, c+1, l); // Continuer à droite
      
      if (l > 0 && c > 0 && !grille[c-1][l-1].visible && grille[c-1][l-1].contenu != MINE)
        decouvrirCase(dem, grille, c-1, l-1); // Continuer en haut à gauche
      if (l > 0 && c < dem.nc-1 && !grille[c+1][l-1].visible && grille[c+1][l-1].contenu != MINE)
        decouvrirCase(dem, grille, c+1, l-1); // Continuer en haut à droite
      if (l < dem.nl-1 && c > 0 && !grille[c-1][l+1].visible && grille[c-1][l+1].contenu != MINE)
        decouvrirCase(dem, grille, c-1, l+1); // Continuer en bas à gauche
      if (l < dem.nl-1 && c < dem.nc-1 && !grille[c+1][l+1].visible && grille[c+1][l+1].contenu != MINE)
        decouvrirCase(dem, grille, c+1, l+1); // Continuer en bas à droite
    }
  }
  
  /**
   * Fonction pour tester si le joueur a terminé sa partie ou non.
   * Si oui, on précise si elle est gagnée ou perdue.
   */
  static int statutGrille(Demineur dem, Case [][] grille) {
    boolean aMineDecouverte = false, aCasePasDecouverte = false;
    int c=0, l=0, nbMinesMarquees = 0;
    while (l<dem.nl && !aMineDecouverte) {
      if (grille[c][l].contenu == MINE && grille[c][l].visible) {
        aMineDecouverte = true;
      } else {
        if (grille[c][l].drapeau) {
          if (grille[c][l].contenu == MINE)
            nbMinesMarquees++;
          else
            nbMinesMarquees = dem.nm+1; // Empêche de gagner en mettant des drapeaux partout
        }
        c++;
        if (c == dem.nc) {
          c = 0;
          l++;
        }
      }
    }
    if (nbMinesMarquees == dem.nm)
      return GAGNEE;
    if (aMineDecouverte)
      return PERDUE;
    
    c=0;
    l=0;
    while (l<dem.nl && !aCasePasDecouverte) {
      if (grille[c][l].contenu != MINE && !grille[c][l].visible) {
        aCasePasDecouverte = true;
      } else {
        c++;
        if (c == dem.nc) {
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
    Demineur dem = new Demineur();
    Case [][] grille;
    int [][] imageFlag = EcranGraphique.loadPNGFile("drapeau.png");
    int [][] imageMine = EcranGraphique.loadPNGFile("mine.png");
    int [][] imageClk  = EcranGraphique.loadPNGFile("clock.png");
    int statutPartie = JOUABLE;
    Ecran.afficherln("Bienvenue dans le jeu du démineur !\nVous allez définir votre partie :");
    do {
      Ecran.afficher("Nombre de lignes : ");
      dem.nl = Clavier.saisirInt();
      if (dem.nl < 3) {
        Ecran.afficherln("Nombre de lignes trop petit. Merci de mettre une taille d'au moins 3.");
      } else if (dem.nl > 50) {
        Ecran.afficherln("Nombre de lignes trop élevé. Merci de ne pas dépasser 50.");
      }
    } while(dem.nl < 3 || dem.nl > 50);
  
    do {
      Ecran.afficher("Nombre de colonnes : ");
      dem.nc = Clavier.saisirInt();
      if (dem.nc < 3) {
        Ecran.afficherln("Nombre de colonnes trop petit. Merci de mettre une taille d'au moins 3.");
      } else if (dem.nc > 80) {
        Ecran.afficherln("Nombre de colonnes trop élevé. Merci de ne pas dépasser 80.");
      }
    } while(dem.nc < 3 || dem.nc > 80);
  
    do {
      Ecran.afficher("Nombre de mines : ");
      dem.nm = Clavier.saisirInt();
      if (dem.nm < 1) {
        Ecran.afficherln("Nombre de mines trop petit. Merci de mettre une taille d'au moins 1.");
      } else if (dem.nm >= dem.nc * dem.nl) {
        Ecran.afficherln("Il y a trop de mines. Merci d'en mettre moins que ", dem.nc*dem.nl, ".");
      }
    } while(dem.nm < 1 || dem.nm >= dem.nc*dem.nl);
  
    // Dimensions de la grille pour s'en servir
    initialiserFenetre(dem);
    grille = initialiserGrille(dem);
    afficher(dem, grille, imageFlag, imageMine, imageClk);
    
    do {
      if (traiterEntree(dem, grille)) {
        statutPartie = statutGrille(dem, grille);
        if (dem.tempsDebut == -1.0)
          dem.tempsDebut = System.currentTimeMillis()/1000;
      }
      afficher(dem, grille, imageFlag, imageMine, imageClk);
    } while (statutPartie == JOUABLE);

    // Message pour le joueur
    if (statutPartie == PERDUE) {
      EcranGraphique.setColor(255,0,0);
      EcranGraphique.drawString(130 + dem.tailleX, 130, EcranGraphique.COLABA8x13, "LOSER!!");
    }
    else {
      EcranGraphique.setColor(0,0,255);
      EcranGraphique.drawString(130 + dem.tailleX, 130, EcranGraphique.COLABA8x13, "GGWP!!");
    }
    EcranGraphique.flush();
  }
}
