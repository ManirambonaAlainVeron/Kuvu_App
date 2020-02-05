package kuvunja;


import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;


@ViewScoped
public class Echange implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private float taux;
	private float Somme;
	private String type;
	private String info;
	private int echId;
	
	private List<SelectItem> list_echange;
	
	public Echange() {}

	public float getTaux() {
		return taux;
	}

	public void setTaux(float taux) {
		this.taux = taux;
	}
	
	public float getSomme() {
		return Somme;
	}

	public void setSomme(float somme) {
		Somme = somme;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public List<SelectItem> getList_echange() {
		if(list_echange==null)
		{
			list_echange=new ArrayList<SelectItem>();
			list_echange.add(new SelectItem("-1","Liste des echanges"));
		}
		return list_echange;
	}

	public void setList_echange(List<SelectItem> list_echange) {
		this.list_echange = list_echange;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	
	public int getEchId() {
		return echId;
	}

	public void setEchId(int echId) {
		this.echId = echId;
	}
	public void echange_listener(ValueChangeEvent ex)
	{
		this.info="";
		
	
	}
	public void echange_listenere(ValueChangeEvent ex)
	{
		
		this.info="";
		
	}
	
	public void chargerEchange(ValueChangeEvent ex)
	{
		
		this.taux=0;
		this.Somme=0;
		if(list_echange==null)
		{
			list_echange=new ArrayList<SelectItem>();
		}
		else
		{
			list_echange.clear();
		}
		list_echange.add(new SelectItem("-1","Liste des echanges"));
		
		if(ex!=null && ex.getNewValue()!=null && ex.getNewValue().toString().equalsIgnoreCase("Selectionnez le type"))
		{
			return;
		}
		
			this.type=ex.getNewValue().toString();
			ResultSet resu=db.Db_con.extraireData("select *from vente where type='"+this.type+"'");
			this.info="";
			if(resu!=null)
			{
				try {
					while(resu.next())
					{
						list_echange.add(new SelectItem(resu.getInt("Id"),resu.getString("nom1")+"|"+resu.getFloat("prix")+"|"+resu.getString("nom2")+"|"+resu.getString("type")));
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		public void Effacer_somme()
		{
			this.Somme=0;
		}
		public void Echange_Argent()
		{
			float prix=0;
			if(this.type.equalsIgnoreCase("Selesctionnez le type"))
			{
				this.info="Selectionnez s'il vous plait le type!!";
				return;
			}
			if(this.echId<1)
			{
				this.info="Selectionner s'il vous plait l'echange!!";
				return;
			}
			if(this.taux==0)
			{
				this.info="Saisissez s'il vous plait le Taux!!";
				return;
			}
			ResultSet resulta=db.Db_con.extraireData("select prix from vente where Id="+this.echId+"");
			
			try {
				while(resulta.next())
				prix=resulta.getFloat("prix");
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(this.type.equalsIgnoreCase("vente"))
			{
				this.Somme=prix*this.taux;
			}
			if(this.type.equalsIgnoreCase("achat"))
			{
				
				this.Somme=this.taux/prix;
			
			}
			
		}
	}
	

