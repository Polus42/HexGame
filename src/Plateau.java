import java.util.*;

public class Plateau {
	
	int hauteur_;
	int longeur_;
	private Case[][] Lcase_;
	
/**
 * Constructeur de Plateau
 * Initialise un plateau avec que des cases de couleur 0
 * @param hauteur
 * @param longeur
 */
	public Plateau (int hauteur, int longeur ) {
		Lcase_ = new Case[hauteur][longeur];
		hauteur_=hauteur;
		longeur_=longeur;

		for ( int i=0; i<longeur;++i) {

			for ( int n=0;n<hauteur;++n) {
				Case c= new Case(0,0);
				c.setX(i);
				c.setY(n);
				c.setCoteG(false);
				c.setCoteD(false);
				c.setCoteB(false);
				c.setCoteH(false);

				if (i==0) {
					c.setCoteG(true);
				}	
				if (i==longeur-1) {
					c.setCoteD(true);
				}
				
				if (n==0) {
					c.setCoteH(true);
				}
				if ( n==hauteur-1) {
					c.setCoteB(true);
				}
				Lcase_[i][n] = c;
				}
			
		}
		
	}

	public Case[][] getLcase() {
		return Lcase_;
	}

	public void setLcase(Case[][] lcase_) {
		Lcase_ = lcase_;
	}

	public int getHauteur(){return hauteur_;}
	public void setHauteur(int h){hauteur_=h;}
	
	public int getLongeur(){return longeur_;}
	public void setLongeur(int h){longeur_=h;}
	
	/**
	 * Renvoie la case aux coordonnée indiquées, si la case est en dehors du plateau, renvoi null
	 * @param X
	 * @param Y
	 * @return null si la case n'existe pas, la case si elle existe
	 */
	public Case trouverCase(int X, int Y){
		if ((X<longeur_)&&(X>=0)&&(Y<hauteur_)&&(Y>=0))
		{
			return Lcase_[X][Y];
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * Retourne une liste des voisins d'une case
	 * @param c
	 * @return ArrayList de Case
	 */
	public ArrayList<Case> Voisin (Case c) {
		
		ArrayList<Case> voisin = new ArrayList<Case>();
		int x=c.getX();
		int y=c.getY();
		
		Case ajout;
		if (x-1>=0){
		ajout=this.trouverCase(x-1,y);
		voisin.add(ajout);
		}
		
		if (y-1>=0){
			ajout=this.trouverCase(x,y-1);
			voisin.add(ajout);
		}
		
		if (x+1<longeur_){
			ajout=this.trouverCase(x+1,y);
			voisin.add(ajout);
		}
		
		if (y+1<hauteur_){
			ajout=this.trouverCase(x,y+1);
			voisin.add(ajout);
		}
		
		if ((y+1<hauteur_)&&(x-1>=0)){
			ajout=this.trouverCase(x-1,y+1);
			voisin.add(ajout);
		}
		
		if ((y-1>=0)&&(x+1<longeur_)){
			
			ajout=this.trouverCase(x+1,y-1);
			voisin.add(ajout);
		}
		return voisin;
	}
	/**
	 * Permet de dessiner le plateau dans le terminal
	 */
	public void dessiner()
	{
		String indice = "   ";
		for (int z = 0;z<longeur_;z++)
		{
			indice+= z +"  ";
		}
		System.out.println(indice);
		String offset = "";
		for (int i = 0;i< hauteur_;i++)
		{
			String s = "";
			offset+=" ";
			s+=offset+i;
			for (int k = 0;k<longeur_;k++)
			{
				Case c = trouverCase(k, i);
				switch (c.getCouleur()) {
				// incolore
				case 0:
					s+= "[ ]";
					break;
				// joueur 1
				case 1:
					s+="[\u2661]";
					break;
				// joueur 2
				case 2:
					s+="[\u2665]";
					break;

				default:
					break;
				}
			}
			System.out.println(s);
		}
	}
	/**
	 * Demande au joueur 1 de jouer, retourne vrai si il a gagné lors de l'ajout du pion
	 * @return vrai si il a gagné lors de l'ajout du pion
	 */
	public boolean askJ1()
	{
		Scanner s = new Scanner(System.in);
		System.out.println("C'est au J1 de jouer\n Coord X ?");
		int x = s.nextInt();
		System.out.println("Coord Y ?");
		int y = s.nextInt();
		while (!ajoutePion(1, x, y))
		{
			System.out.println("Veuillez entrer des coordonnées valides");
			System.out.println("Coord X ?");
			x = s.nextInt();
			System.out.println("Coord Y ?");
			y = s.nextInt();
		}
		Case c = trouverCase(x,y);
		if ((c.getCunion().Classe().getCoteH())&&(c.getCunion().Classe().getCoteB())) // Cunion en cout est en logarithme amorti (quasiment O(1))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	/**
	 * Demande au joueur 2 de jouer, retourne vrai si il a gagné lors de l'ajout du pion
	 * @return vrai si il a gagné lors de l'ajout du pion
	 */
	public boolean askJ2()
	{
		Scanner s = new Scanner(System.in);
		System.out.println("C'est au J2 de jouer\n Coord X ?");
		int x = s.nextInt();
		System.out.println("Coord Y ?");
		int y = s.nextInt();
		while (!ajoutePion(2, x, y))
		{
			System.out.println("Veuillez entrer des coordonnées valides");
			System.out.println("Coord X ?");
			x = s.nextInt();
			System.out.println("Coord Y ?");
			y = s.nextInt();
		}
		Case c = trouverCase(x,y);
		if ((c.getCunion().Classe().getCoteG())&&(c.getCunion().Classe().getCoteD())) // Cunion en cout est en logarithme amorti (quasiment O(1))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	/**
	 * 	Afficher la composante d’un pion x de couleur c situe sur le
	 *	plateau, c’est-a-dire l’ensemble des pions de couleur c relies a x par un chemin
	 *	de couleur c
	 * @param x Pion
	 */
	public void afficheComposante(Case c)
	{
		for (Case c1 : composante(c))
		{
			System.out.println(c1.toString());
		}
	}
	/**
	 * Retourne une liste de cases correspondant à la composante de la case x
	 * @param x
	 * @return liste de cases
	 */
	public ArrayList<Case> composante(Case x)
	{
		ArrayList<Case> listecompo = new ArrayList<Case>();
		// On trouve la classe de la case x
		Tree t = x.getCunion().Classe();
		// On parcours les cases et on trouve celles de la même classe
		for (int i=0;i<longeur_;i++)
		{
			for (int j=0;j<hauteur_;j++)
			{
				if (Lcase_[i][j].getCunion().Classe()==t)
				{
					listecompo.add(Lcase_[i][j]);
				}
			}
		}
		return listecompo;
	}
	/**
	 * Retourne vrai si il existe un chemin d'une même couleur entre les deux cases
	 * @param c1 Première case
	 * @param c2 Deuxième case
	 * @return Vrai si il existe un chemin
	 */
	public boolean existeCheminCases(Case c1, Case c2)
	{
		if (c1.getCunion().Classe()==c2.getCunion().Classe())
		{
			return true;
		}
		else {
			return false;
		}
	}
	/**
	 *  Teste si il y a un chemin entre le cote premiercote et le cote deuxiemecote
	 *  On a : 0 = cote Haut / 1 = cote Droit / 2 = cote Bas / 3 = cote Gauche
	 * @param premiercote
	 * @param deuxiemecote
	 * @param couleur
	 * @return Vrai si il existe un chemin d'une meme couleur entre ces deux cotes
	 */
	public boolean existeCheminCotes(int premiercote,int deuxiemecote,int couleur)
	{
		// Droit et Gauche
		if ((premiercote==1)&&(deuxiemecote==3)||(premiercote==3)&&(deuxiemecote==1))
		{
			for (int i=0;i<hauteur_;i++)
			{
				if (Lcase_[0][i].getCunion().getCoteG()&&Lcase_[0][i].getCunion().getCoteD())
				{
					return couleur==Lcase_[i][0].getCouleur();
				}
			}
		}
		// Haut et Bas
		if ((premiercote==0)&&(deuxiemecote==2)||(premiercote==2)&&(deuxiemecote==0))
		{
			for (int i=0;i<longeur_;i++)
			{
				if (Lcase_[i][0].getCunion().getCoteH()&&Lcase_[i][0].getCunion().getCoteB())
				{
					return couleur==Lcase_[i][0].getCouleur();
				}
			}
		}
		return false;
	}
	/**
	 * Permet d'ajouter un pion sur le plateau
	 * @param joueur qui ajoute ce pion
	 * @param X position en largeur de la case
	 * @param Y position en hauteur de la case
	 * @return vrai si le pion a bien été ajouté
	 */
	public boolean ajoutePion(int joueur,int X, int Y){ // l'entier indique qu'elle joueur joue le pion 
		Case c=trouverCase(X,Y);
		if (c.getCouleur()==0&&trouverCase(X, Y)!=null)
		{
			if (joueur==1){
				c.setCouleur(1);// la couleur du joueur 1
			}else {
				c.setCouleur(2);// la couleur du joueur 2
			}
			// Si le pion que l'on insere touche un coté
			if (X==longeur_-1)
			{
				c.setCoteD(true);
				c.getCunion().setCoteD(true);
			}
			if (X==0)
			{
				c.setCoteG(true);
				c.getCunion().setCoteG(true);
			}
			if (Y==hauteur_-1)
			{
				c.setCoteB(true);
				c.getCunion().setCoteB(true);
			}
			if (Y==0)
			{
				c.setCoteH(true);
				c.getCunion().setCoteH(true);
			}

			ArrayList<Case> voisin=this.Voisin(c);
			for (Case i : voisin){ // On va vérifier si il existe des voisin de pion apartenent au même joueur
				if ((c.getCouleur()==i.getCouleur())&&(c.getCunion().Classe()!=i.getCunion().Classe())){ // Même couleur pour le voisin et classe uninon différent ( sinon sa sert à rien de faire l'union
					c.Union(i);
				}
			}
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Teste si l'ajout d'un pion de couleur c relie deux composantes de couleur c existant avant l'ajout du pion
	 * @param couleur la couleur du pion qu'on ajoute
	 * @param X sa coordonnée en x
	 * @param Y sa coordonnée en y
	 * @return vrai si le pion relie deux composantes
	 */
	public boolean relieComposantes(int couleur,int X,int Y)
	{
		ArrayList<Case> voisins = this.Voisin(trouverCase(X,Y));
		int compteur = 0;
		int i=0;
		while ((compteur<2)&&(i<voisins.size()))
		{
			if (voisins.get(i).getCouleur()==couleur)
			{
				compteur++;
			}
			++i;
		}
		return compteur==2;
	}
	/**
	 * Calcule le nombre de pion à ajouter pour faire se rejoindre les deux composantes
	 * Les deux cases données doivent être de la même composante
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return La distance en nombre de case, retourne -1 si on ne peut relier les deux cases
	 */
	public int calculeDistance(int x, int y,int x2, int y2)
	{
		//on stocke la comosante de la case d'arrivé
		ArrayList<Case> compoc2 = composante(trouverCase(x2, y2));
		
		//on garde en memoire la couleur
		int couleur = trouverCase(x, y).getCouleur();
		ArrayList<Case> visited = new ArrayList<Case>();

		// vecteur de vecteur
		Vector<Case> etape[] = (Vector<Case>[]) new Vector[longeur_*hauteur_+1];
		for (int h=0;h<=longeur_*hauteur_;h++)
		{
			etape[h] = new Vector<Case>();
		}
		//on ajoute toutes les cases de la composante donnée
		ArrayList<Case> cases = composante(trouverCase(x, y));
		for (Case case1 : cases)
		{
			etape[0].add(case1);
			visited.add(case1);
		}
		boolean atteint = false;
		int k = 1;
		// a chaque boucle on ajoute toute les cases que l'on peut parcourir en ajoutant k pions
		while (!atteint)
		{
			//pour toutes les cases ajoutées au tour d'avant, on regarde quels sont les voisins que l'on peut atteindre
			//et on les ajoute
			for (Case c : etape[k-1])
			{
				for (Case case1 : Voisin(c))
				{
					if ((case1!=null)&&(case1.getCouleur()==0|case1.getCouleur()==couleur)&&!(visited.contains(case1)))
					{
						etape[k].add(case1);
						visited.add(case1);
						if (compoc2.contains(case1))
						{
							atteint = true;
						}
						// si la case rencontrée a un voisin de la meme couleur, on peut ateindre tous les voisins de sa composante ensuite
						for (Case case3 : Voisin(case1))
						{
							//si un de ses voisins est de la meme couleur alors
							if (case3.getCouleur()==couleur)
							{
								//on ajoute toutes les cases de sa composante aux cases visitées/ajoutée
								ArrayList<Case> compo = composante(case3);
								for (Case case2 : compo)
								{
									visited.add(case2);
									if (compoc2.contains(case2))
									{
										atteint = true;
									}
									etape[k].add(case2);
								}
							}
						}
					}
				}
			}
			// si on boucle trop longtemps, c'est qu'on est bloqué, on renvoie donc -1
			if (k > hauteur_*longeur_)
			{
				k = -1;
				atteint = true;
			}
			k++;
		}
		return k-1;
	}
	/**
	 * Joue un ordi en placant les pions au hasard
	 * @return vrai si l'ordi a gagné
	 */
	public boolean joueOrdiStrategieHasard()
	{
		int x = (int)(Math.random()*longeur_);
		int y = (int)(Math.random()*hauteur_);
		while (trouverCase(x, y).getCouleur()!=0)
		{
			x = (int)(Math.random()*longeur_);
			y = (int)(Math.random()*hauteur_);
		}
		ajoutePion(2, x, y);
		Case c = trouverCase(x,y);
		if ((c.getCunion().Classe().getCoteG())&&(c.getCunion().Classe().getCoteD())) // Cunion en cout est en logarithme amorti (quasiment O(1))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
/**
 * Joue une strategie qui consiste à faire une simple ligne de gauche à droite
 * @return vrai si 'lordi a gagné
 */
	public boolean joueOrdiStrategieLigne()
	{
		int x = 0,y =5;
		int i=0;
		boolean trouve = false;
		while (i<longeur_&&!trouve)
		{
			if (trouverCase(i, 5).getCouleur()==1)
			{
				ArrayList<Case> voisins = Voisin(trouverCase(i, 5));
				for (Case c : voisins)
				{
					if (c.getCouleur()==0)
					{
						x=c.getX();
						y=c.getY();
						trouve=true;
					}
				}
			}
			i++;
		}
		trouve=false;
		while (i<longeur_&&!trouve)
		{
			if (trouverCase(i, 5).getCouleur()==0)
			{
				x=i;
				y=5;
				trouve=true;
			}
			i++;
		}
		ajoutePion(2, x, y);
		Case c = trouverCase(x,y);
		if ((c.getCunion().Classe().getCoteG())&&(c.getCunion().Classe().getCoteD())) // Cunion en cout est en logarithme amorti (quasiment O(1))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
/**
 * Joue une strategie qui joue une case à gauche, puis une case à droite et qui essaye de les rejoindre
 * @return vrai si l'ordi a gagné lors de l'ajout de son pion
 */
	public boolean joueOrdiStrategieForte()
	{
		int x=0,y=0;
		// On regarde toute les composantes de notre couleur deja présentes
		ArrayList<Tree> dejala = new ArrayList<Tree>();
		for (int i=0;i<longeur_;i++)
		{
			for (int j=0;j<hauteur_;j++)
			{
				if (Lcase_[i][j].getCouleur()==2)
				{
					if (!dejala.contains(Lcase_[i][j].getCunion().Classe()))
					{
						dejala.add(Lcase_[i][j].getCunion().Classe());
					}
				}
			}
		}
		// Si on a pas deja deux composantes on les créé en ajoutant un pion a gauche puis un pion à droite
		if (dejala.size() <2)
		{
			//si il n'y avait aucune composante on ajoute à gauche en cherchant une case libre de haut en bas
			if (dejala.size()==0)
			{
				boolean ajoute = false;
				int i=1;
				while (!ajoute&&i<hauteur_)
				{
					if (Lcase_[0][i].getCouleur()==0)
					{
						x=0;
						y=i;
						ajoute=true;
					}
					i++;
				}
			}
			// sinon on ajoute à droite en charchant une case libre de haut en bas
			else
			{
				boolean ajoute = false;
				int i=1;
				while (!ajoute&&i<hauteur_)
				{
					if (Lcase_[longeur_-1][i].getCouleur()==0)
					{
						x=longeur_-1;
						y=i;
						ajoute=true;
					}
					i++;
				}
			}

		}
		// si il y a deja les deux composantes à gauche et à droite on essaye de les rejoindre
		else
		{
			int bestdist = longeur_*hauteur_;
			Case bestcase = null;
			// pour chaque voisins de la composante de gauche, on va chercher quel est le meilleur voisin à jouer
			for (Case case1 : VoisinComposante(dejala.get(0)))
			{
				//on fait croire que la case est de notre couleur pour simuler qu'on la joue
				int couleurprecd = case1.getCouleur();
				case1.setCouleur(2);
				int dist = calculeDistance(case1.getX(),case1.getY(), dejala.get(1).getCase().getX(),dejala.get(1).getCase().getY());
				if (dist<bestdist)
				{
					bestdist= dist;
					bestcase = case1;
				}
				case1.setCouleur(couleurprecd);
			}
			//on met en memoire la meilleure case à jouer
			x = bestcase.getX();
			y = bestcase.getY();
		}
		ajoutePion(2, x, y);
		Case c = trouverCase(x,y);
		// si l'ordi a gagné on retourne vrai
		if ((c.getCunion().Classe().getCoteG())&&(c.getCunion().Classe().getCoteD())) // Cunion en cout est en logarithme amorti (quasiment O(1))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	/**
	 * Retourne tous les voisins de couleur 0 d'une composante
	 * @param t la composante
	 * @return une liste des voisins de couleur 0 d'une composante
	 */
	public ArrayList<Case> VoisinComposante(Tree t)
	{
		ArrayList<Case> compo = composante(t.getCase());
		ArrayList<Case> voisins = new ArrayList<Case>();
		for (Case case1 : compo)
		{
			for (Case voisin : Voisin(case1))
			{
				if (voisin.getCouleur()==0)
				{
					voisins.add(voisin);
				}
			}
		}
		return voisins;
	}
	
}

	
