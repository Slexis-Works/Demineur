// Développeurs : Alexis Cabodi et Mohamed Lakhal du Groupe 5 (I4-CMI) Sujet 4 
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
   * Une case du plateau. Ce type agrégé permet de regrouper diverses informations dans une case du tableau de la grille
   * - contenu : nombre (entier) indiquant si la case contient une mine (-1) ou sinon le nombre de mines adjacentes
   * - visible : indique si la case est découverte ou pas
   * - drapeau : indique si la case a un drapeau ou non
   */
  static class Case {
    int contenu = 0;
    boolean visible = false;
    boolean drapeau = false;
  }
  
  /**
   * Structure avec des données concernant le jeu qui sont utilisées dans la plupart des fonctions
   * - nc : nombre (entier positif) de colonnes sur la grille
   * - nl : nombre (entier positif) de lignes sur la grille
   * - nm : nombre (entier positif) de mines dans la grille
   * - tempsDebut : heure en secondes (jusqu'aux millisecondes après la virgule) du système au moment du premier clic utile sur la grille
   * - tailleX|Y : dimensions en pixels de la grille (pour des calculs de position)
   */
  static class Demineur {
    int nc, nl, nm;
    double tempsDebut = -1.0;
    int tailleX, tailleY;
  }
  
  // Fonctions

  /**
   * Initialisation de la fenêtre en prenant en compte le nombre de cases et la place pour les messages adjacents à la grille
   *
   * @param dem Structure contenant nc et nl et qui recevra tailleX et tailleY
   */
  static void initialiserFenetre(Demineur dem) {
    dem.tailleX = 10 + dem.nc * (LARGEUR_CASE+1);
    dem.tailleY = 10 + dem.nl * (HAUTEUR_CASE+1);
    EcranGraphique.init(50, 50, dem.tailleX+390, dem.tailleY+110, dem.tailleX + 350, dem.tailleY + 30, "Démineur");
    EcranGraphique.setClearColor(0, 0, 0);
  }
	
  /**
   * Création de la grille en prenant en compte le nombre de cases et de mines
   *
   * La fonction effectue un tirage aléatoire des coordonnées (colonne, ligne) des cases minées
   * et compte les mines adjacentes de chaque case (x, y) non minée
   * 
   * @param dem Structure qui permet à la grille d'être initialisée selon nc, nl et nm
   * 
   * @return La grille nouvellement crée
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
   * Affichage avec la classe EcranGraphique de l'état du jeu (drapeau, grillage, cases…)
   * 
   * @param dem       Structure avec des informations sur la grille et le temps de départ
   * @param grille    Ensemble de cases à afficher
   * @param imageFlag Image du drapeau au format d'EcranGraphique
   * @param imageMine Image d'une mine
   * @param imageClk  Image de l'horloge
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
            EcranGraphique.drawImage(6+(LARGEUR_CASE+1)*c, 6+(HAUTEUR_CASE+1)*l, imageMine);
          } else if (grille[c][l].contenu > 0) {
            EcranGraphique.drawString(15+(LARGEUR_CASE+1)*c, 26+(HAUTEUR_CASE+1)*l, EcranGraphique.COLABA8x13, ""+grille[c][l].contenu);
          } // "0" non affichés
        } else if (grille[c][l].drapeau) {
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
      EcranGraphique.drawString(220 + dem.tailleX, 30, EcranGraphique.COLABA8x13, Math.floor(((double)System.currentTimeMillis()/1000.0-dem.tempsDebut)*10.0)/10.0 + "s.");
    EcranGraphique.drawString(30 + dem.tailleX, 60, EcranGraphique.COLABA8x13, "CLIC GAUCHE : LIBEREZ UNE CASE");
    EcranGraphique.drawString(30 + dem.tailleX, 80, EcranGraphique.COLABA8x13, "CLIC DROIT : POSER UN DRAPEAU");
    EcranGraphique.drawImage(180 + dem.tailleX, 10, imageClk);
    EcranGraphique.flush();
  }
  
  /**
   * Fonction qui va possiblement altérer la grille si le joueur a cliqué
   * 
   * @param dem     Structure avec des informations utiles : les dimensions de la grille
   * @param grille  Grille qui sera modifiée
   * 
   * @return vrai si la fonction a fait quelque chose ou false si le clic était inutile ou absent
   */
  static boolean traiterEntree(Demineur dem, Case [][] grille) {
    if (EcranGraphique.getMouseState()!=2) {
      EcranGraphique.wait(10); // Ne pas surcharger le processeur
      return false;
    }
    // getMouseButton pour savoir si c'était un clic gauche ou droit
    int clic = EcranGraphique.getMouseButton();
    // calcul qui détermine la cellule cliquée (bordure incluse)
    int x = (EcranGraphique.getMouseX()-5)/(LARGEUR_CASE+1);
    int y = (EcranGraphique.getMouseY()-5)/(HAUTEUR_CASE+1);
    if (x >= 0 && x < dem.nc && y >= 0 && y < dem.nl) {
      if (grille[x][y].drapeau) {
        if (clic == 1) { // s'il y a un drapeau on empêche le clic gauche
          Ecran.afficherln("CLIC GAUCHE IMPOSSIBLE SUR UN DRAPEAU !");
          return false;
        }
        // si clic droit, on enlève drapeau
        else if (clic == 3) {
          grille[x][y].drapeau = false;
        }
      } // s'il n'y a pas de drapeau et qu'on fait un clic droit
      else if (clic == 3) {
        grille[x][y].drapeau = true;
      } // si on peut découvrir la case
      else if (clic == 1) {
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
   * 
   * @param dem     Structure avec des informations utiles : les dimensions de la grille
   * @param grille  Grille contenant la case à découvrir
   * @param c       Colonne de la case à découvrir
   * @param l       Ligne de la case à découvrir
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
   * 
   * @param dem     Structure avec nc, nl et nm correspondant à la grille
   * @param grille  Grille dont on veut l'état
   * 
   * @return  Renvoie un nombre indiquant l'état de la grille correspondant aux
   *          constantes JOUABLE, GAGNEE ou PERDUE
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
  
  /**
   * Méthode principale qui va demander à l'utilisateur de configurer sa partie pour ensuite la faire tourner jusqu'à sa fin
   * 
   * @param args Arguments non supportés
   */
  public static void main(String[] args) {
    Demineur dem = new Demineur();
    Case [][] grille;
    int [][] imageFlag = EcranGraphique.loadPNGFile("drapeau.png");
    int [][] imageMine = EcranGraphique.loadPNGFile("mine.png");
    int [][] imageClk  = EcranGraphique.loadPNGFile("clock.png");
    int statutPartie = JOUABLE;
    
    // Configuration
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
  
    // Initialisation
    initialiserFenetre(dem);
    grille = initialiserGrille(dem);
    
    // Boucle de jeu
    do {
      if (traiterEntree(dem, grille)) {
        statutPartie = statutGrille(dem, grille);
        if (dem.tempsDebut == -1.0)
          dem.tempsDebut = (double)System.currentTimeMillis()/1000.0;
      }
      afficher(dem, grille, imageFlag, imageMine, imageClk);
    } while (statutPartie == JOUABLE);

    // Message de fin de partie pour le joueur
    if (statutPartie == PERDUE) {
      EcranGraphique.setColor(255,0,0);
      EcranGraphique.drawString(130 + dem.tailleX, 115, EcranGraphique.COLABA8x13, "PERDU...");
    }
    else {
      EcranGraphique.setColor(0,0,255);
      EcranGraphique.drawString(130 + dem.tailleX, 115, EcranGraphique.COLABA8x13, "BRAVO !!!");
    }
    EcranGraphique.flush();
  }
}
