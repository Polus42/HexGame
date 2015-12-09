/**
 * Classe Partie
 * @author figiel-paul
 *
 */
public class Partie {
	private Plateau plateau_;
	private boolean fini = false;
	
	public Partie(int n)
	{
		plateau_=new Plateau(n, n);
	}
	/**
	 * Lance une partie avec deux humains
	 */
	public void joueDeuxHumains()
	{
		int i =1; // indique le joueur qui joue
		while (!fini)
		{
			System.out.println(plateau_.calculeDistance(0, 4, 9, 4));
			if (i==1)
			{
				// Demande au J1 de jouer
				plateau_.dessiner();
				if (plateau_.askJ1())
				{
					System.out.println("Le joueur 1 a gagné");
					fini=true;
				}
				i =2;
			}
			else
			{
				// Demande au J2 de jouer
				plateau_.dessiner();
				if (plateau_.askJ2())
				{
					System.out.println("Le joueur 2 a gagné");
					fini=true;
				}
				i=1;
			}
		}
	}
/**
 * Lance une partie avec le j1 humain et le j2 en ordi
 * @param choix 1 pour une strategie au hasard, 2 pour une strategie en ligne, 3 pour une strategie "forte"
 */
	public void joueOrdiHumain(int choix)
	{
		int i =1; // indique le joueur qui joue
		while (!fini)
		{
			if (i==1)
			{
				// Demande au J1 de jouer
				plateau_.dessiner();
				if (plateau_.askJ1())
				{
					System.out.println("Le joueur 1 a gagné");
					fini=true;
				}
				i =2;
			}
			else
			{
				// Fait jouer l'ordi
				plateau_.dessiner();
				switch (choix) {
				case 1:
					if (plateau_.joueOrdiStrategieHasard())
					{
						System.out.println("L'ordi a gagné");
						fini=true;
					}
					i=1;
					break;
				case 2:
					if (plateau_.joueOrdiStrategieLigne())
					{
						System.out.println("L'ordi a gagné");
						fini=true;
					}
					i=1;
				break;
				case 3:
					if (plateau_.joueOrdiStrategieForte())
					{
						System.out.println("L'ordi a gagné");
						fini=true;
					}
					i=1;
				break;

				default:
					break;
				}
			}
		}
	}

}
