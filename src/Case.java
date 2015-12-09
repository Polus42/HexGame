public class Case {
	
	private int X_;
	private int Y_;
	private int couleur_; // 0 incolore 1 J1 et 2 J2
	//private Case classe_ ;
	// C'est 4 bool indique si la case touche le coté indiqué
	private boolean coteG_;
	private boolean coteD_;
	private boolean coteH_;
	private boolean coteB_;
	private Tree Cunion_;
	
	public Case (int X, int Y){
		
			X_=X;
			Y_=Y;
			coteG_=false;
			coteD_=false;
			coteH_=false;
			coteB_=false;
			Cunion_= new Tree(this);
	}
	public Case (int X, int Y, boolean G, boolean D, boolean H, boolean B) {
		
		//setClasse(this);
		X_=X;
		Y_=Y;
		couleur_=0;
		setCoteG(G);
		setCoteD(D);
		setCoteH(H);
		setCoteB(B);
		Cunion_= new Tree(this);
		
	}
/*public Case ( int X, int Y,String c1) {
		
		//setClasse(this);
		setX(X);
		setY(Y);
		setCouleur(0);
		cote_.add(c1);
		
		
	}
	public Case ( int X, int Y,String c1, String c2) {
		
		setClasse(this);
		setX(X);
		setY(Y);
		setCouleur(0);
		cote_.add(c1);
		cote_.add(c2);
		
	}
*/
	public int getX() {
		return X_;
	}

	public void setX(int x_) {
		X_ = x_;
	}

	public int getY() {
		return Y_;
	}

	public void setY(int y_) {
		Y_ = y_;
	}

	public int getCouleur() {
		return couleur_;
	}

	public void setCouleur(int couleur_) {
		this.couleur_ = couleur_;
	}

	
	
	public void ajouterPion (int col, Plateau plateau) {
		
		this.setCouleur(col);
		
		
	}
	public boolean getCoteG() {
		return coteG_;
	}
	public void setCoteG(boolean coteG_) {
		this.coteG_ = coteG_;
	}
	public boolean getCoteD() {
		return coteD_;
	}
	public void setCoteD(boolean coteD_) {
		this.coteD_ = coteD_;
	}
	public boolean getCoteH() {
		return coteH_;
	}
	public void setCoteH(boolean coteH_) {
		this.coteH_ = coteH_;
	}
	public boolean getCoteB() {
		return coteB_;
	}
	public void setCoteB(boolean coteB_) {
		this.coteB_ = coteB_;
	}
	
	public Tree getCunion(){return Cunion_;}
	
	public boolean equals(Object o){
		if (o == this)
		{
			return true;
		}
		if (o instanceof Case)
		{
			Case c = (Case)o;
			return ( (this.X_==c.X_)&&(this.Y_==c.Y_));
		}
		return false;
		
	}
	
	public boolean goodCase(int x,int y){
			return ((X_==x)&&(Y_==y));
	}
	
	public void Union(Case c) {
		Tree t=this.Cunion_.Union(c.Cunion_);
		this.Cunion_=t;
		c.Cunion_=t;
	}
	public String toString()
	{
		return "X = " +X_+"Y = "+ Y_; 
	}
	/**
	 * Retourne la distance en case entre deux cases
	 * @param c La case de destination
	 * @return Distance en nombre de cases
	 */
	public int distance(Case c)
	{
	        int deltaX = Math.abs(c.X_-X_);
	        int deltaY = Math.abs(c.Y_-Y_);
	        int z1 = -(X_ + Y_);
	        int z2 = -(c.X_ + c.Y_);
	        int deltaZ = Math.abs(z2-z1);
	        int firstmax = Math.max(deltaX, deltaY);
	        return Math.max(firstmax,deltaZ);
	}
}
