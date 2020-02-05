package kuvunja;


import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;



import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpSession;



@ViewScoped
public class Monnaie implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int IDmo;
	private String nom;
	
	private Monnaie mo_click;
	
	private List<Monnaie>list_monnaie;
	
	private String info;
	
	public Monnaie() {}

	public int getIDmo() {
		return IDmo;
	}

	public void setIDmo(int iDmo) {
		IDmo = iDmo;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
	
	
	
	
	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Monnaie getMo_click() {
		return mo_click;
	}

	public void setMo_click(Monnaie mo_click) {
		this.mo_click = mo_click;
	}

	public List<Monnaie> getList_monnaie() {
		ResultSet result=db.Db_con.extraireData("select *from monnaie");
		Monnaie m;
		if(result!=null)
		{
			if(list_monnaie==null)
			{
				list_monnaie=new ArrayList<Monnaie>();
			}
			else
			{
				list_monnaie.clear();
			}
			try {
				while(result.next())
				{
					m=new Monnaie();
					m.IDmo=result.getInt("id");
					m.nom=result.getString("nom");
					
					list_monnaie.add(m);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list_monnaie;
	}

	public void setList_monnaie(List<Monnaie> list_monnaie) {
		this.list_monnaie = list_monnaie;
	}
	
	public void detaille_monnaie()
	{
		if(mo_click!=null)
		{
			this.nom=mo_click.nom;
			this.IDmo=mo_click.IDmo;
		}
	}
	
	public void enregistrerMonnaie() {
		
		if(db.Db_con.modifierDB("insert into monnaie(nom)values('"+this.nom+"')")>0)
		{
			this.info="L'enregistrement est reussi !";
			return;
		}
		else
		{
			this.info="Echec de l'enregistrement !";
			return;
		}
		
		
		 
		
	}
	HttpSession user=null;
	
	public String menu_yanje()
	{
		String outcome=null;
		user=(HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		user.setAttribute("", true);
		outcome="new_vente";
		return outcome;
	}
	public void supprimer_information(ValueChangeEvent ex)
	{
		this.info="";
	}
	public void supprimerMonnaie() {
		if(mo_click!=null)
		{
			this.nom=mo_click.nom;
			this.IDmo=mo_click.IDmo;
			
			if(db.Db_con.modifierDB("delete from monnaie where id="+this.IDmo+"")>0)
			{
				this.info="Une ligne est supprimée !";
				return;
				}
			else {
				this.info="Echac de la suppression !";
				return;
				}
		}
		
		
	}
	
	
}
