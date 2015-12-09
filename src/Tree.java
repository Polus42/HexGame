import java.util.*;
public class Tree {

	
	private Case case_;
	private Tree parent_;
	private int nbElem_;
	private boolean coteG_;
	private boolean coteD_;
	private boolean coteH_;
	private boolean coteB_;
	
	public Tree (Case c ) {
		setCase(c);
		nbElem_=1;
		parent_=null;
		coteG_=c.getCoteG();
		coteD_=c.getCoteD();
		coteH_=c.getCoteH();
		coteB_=c.getCoteB();
	}
	public Case getCase() {
		return case_;
	}



	public void setCase(Case case_) {
		this.case_ = case_;
	}
	
	public Tree getParent(){return parent_;}
	public void setParent(Tree t){parent_=t;}
	
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
	
	// Return le noeud le plus en haut de l'abre
	public Tree Classe () {
		Tree t= this.parent_;
		ArrayList<Tree> stock= new ArrayList<Tree>();
		
		
		if (t==null){
			return this;
		}
		while(t.parent_ != null) {
			stock.add(t);
			t=t.parent_;
		}
		for ( Tree t2 : stock) {
			t2.parent_=t;
		}
		return t;
	}
	
	// Met à jours les 4 bool en fonction d'un autre arbre passer en paramètre
	
	public void MiseAJour(Tree t){

			if (t.coteB_){this.setCoteB(true);}
			if (t.coteD_){this.setCoteD(true);}
			if (t.coteG_){this.setCoteG(true);}
			if (t.coteH_){this.setCoteH(true);}
	}
	
	
	
	// Union de 2 arbres
	public Tree Union ( Tree t){
			Tree t1=this.Classe();
			Tree t2=t.Classe();
			if ( t1.nbElem_>=t.nbElem_){
				t2.parent_=t1;
				t1.nbElem_+=t2.nbElem_;
				t1.MiseAJour(t2);
				return t1;
			}
			else {
				t1.parent_=t2;
				t2.nbElem_+=t1.nbElem_;
				t2.MiseAJour(t1);
				return t2;
			}
	}




}
