// Développeurs : Alexis Cabodi et Mohamed Lakhal ; Groupe de TP 5 (I4-CMI) ; Sujet n°4
public class Demineur {
	// Types agrégés



	// Fonctions
	import java.util.*; //librairie utile pour fonction random et nextInt
	public class champsMine {
		final int VIDE = -1; // marque une case vide 
		final int MINE = -2; //marque une case minée
		private int grille[][];
		private int taille;
		private int cases_decouvertes; 
		private int nb_mines;
		
		void initialiser(int nbre) { //le nombre de mines à placer dans la grille
			Random generateur = new Random(System.currentTimeMillis()); //retourne le temps actuel en millisecondes, générateur de nombres aléatoires
			int placees = 0;
			int i = 0;
			int j = 0;
			
			//initialisation de la grille à vide
			for(i=0; i<taille ;i++){ 
				for(j=0; j<taille; j++){ 
					grille[i][j] = VIDE; 
				} 
			}
			
			//positionnement des nbre mines
			while(placees<nbre){ 
				i=generateur.nextInt(taille);  //méthode nextInt(int n) retourne le prochain nombre aléatoire compris entre 0 (inclus) et n (exclus)
				j=generateur.nextInt(taille);
				
				//on check bien que la case (0,0) sera libre pour commencer
				if ((grille[i][j] == VIDE) &&(i!=0)&&(j!=0)){ 
					grille[i][j] = MINE; 
					placees++; 
				} 
			}
			
			//calcul des cases adjacentes non minées
			for(i=0;i<taille;i++){ 
				for(j=0;j<taille;j++){ 
					if (grille[i][j] != MINE) 
						grille[i][j] = nombreDeVoisins(i, j, taille, grille); //fonction après
				} 
			} 
			nb_mines = nbre; 
			cases_decouvertes = 0; 
		}
		
		int nombreDeVoisins (int i, int j, int taille, int grille[][]) { //retourne le nombre de mines présentes dans les 8 cases adjacentes à la case grille[i][j]
			int x1, x2, y1, y2; 
			int nb_mines = 0; 
			x1 = i-1;  if (x1<0) x1=0; 			//on prends en compte les bords de la grille
			x2 = i+1; if (x2>taille-1) x2=taille-1; 
			y1 = j-1;  if (y1<0) y1=0; 
			y2 = j+1; if (y2>taille-1) y2=taille-1;
	
			for(int k=x1; k<=x2; k++) { 
				for(int l=y1; l<=y2; l++) { 
					if (grille[k][l] == MINE) 
						nb_mines++; 
				} 
			} 
		return nb_mines; 
		}
		
		//SCORE
		private long score;
		public void reset () {	//réinitialise le score à zéro
			score = 0;
		}
		
		public void add (long i) {	//ajoute i au score
			score += i;
		}
		
		public long get() {	//permet de récupérer le score courant
			return score;
		}
	}


	
	public static void main(String[] args) {
		
		
		
	}
}
