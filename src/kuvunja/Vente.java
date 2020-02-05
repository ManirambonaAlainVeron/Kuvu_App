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

public class Vente implements Serializable {
	
	 	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		private int veId;
	    private String nomUn;
	    private float prix;
	    private String nomDeux;
	    private String type;
	    							
	    private String info;
	    
	    private boolean disable=false;
	    
	    private List<Vente> list_donne;
	    
	    private Vente ve_click;
	    
	    private List<SelectItem> list_monnaie;
	    
	    public Vente(){}
	   
		public int getVeId() {
			return veId;
		}
		public void setVeId(int veId) {
			this.veId = veId;
		}
		public String getNomUn() {
			return nomUn;
		}
		public void setNomUn(String nomUn) {
			this.nomUn = nomUn;
		}
		public float getPrix() {
			return prix;
		}
		public void setPrix(float prix) {
			this.prix = prix;
		}
		public String getNomDeux() {
			return nomDeux;
		}
		public void setNomDeux(String nomDeux) {
			this.nomDeux = nomDeux;
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
		
		
		public Vente getVe_click() {
			
			return ve_click;
		}
		public void setVe_click(Vente ve_click) {
			this.ve_click = ve_click;
		}
		
		
		
		public boolean isDisable() {
			return disable;
		}

		public void setDisable(boolean disable) {
			this.disable = disable;
		}

		public static long getSerialversionuid() {
			return serialVersionUID;
		}

		public List<SelectItem> getList_monnaie() {
			ResultSet reso=db.Db_con.extraireData("select *from monnaie");
			
			
				if(list_monnaie==null)
				{
					list_monnaie=new ArrayList<SelectItem>();
				}
				else
				{
					list_monnaie.clear();
				}
				list_monnaie.add(new SelectItem("Selectionnez la monnaie","Selectionnez la monnaie"));
				
				if(reso!=null)
				{
				
				try {
					while(reso.next())
					{
						list_monnaie.add(new SelectItem(reso.getString("nom")));
						
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return list_monnaie;
		}
		
		public void setList_monnaie(List<SelectItem> list_monnaie) {
			this.list_monnaie = list_monnaie;
		}
		
		public void detaille_vente()
		{
			
			if(ve_click!=null)
			{
				this.veId=ve_click.veId;
				this.nomUn=ve_click.nomUn;
				this.prix=ve_click.prix;
				this.nomDeux=ve_click.nomDeux;
				this.type=ve_click.type;
				this.info=" ";
				this.disable=true;
				
			}
			
		}
		public List<Vente> getList_donne() {
			ResultSet resu=db.Db_con.extraireData("select *from vente");
			Vente ve;
			if(resu!=null)
			{
				if(list_donne==null)
				{
					list_donne=new ArrayList<Vente>();
				}
				else
				{
					list_donne.clear();
				}
				try {
					while(resu.next())
					{
						ve=new Vente();
						ve.veId=resu.getInt("Id");
						ve.nomUn=resu.getString("nom1");
						ve.prix=resu.getFloat("prix");
						ve.nomDeux=resu.getString("nom2");
						ve.type=resu.getString("type");
						
						list_donne.add(ve);
					}
				} catch (NumberFormatException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return list_donne;
		}
		
		public void Effacer_Message(ValueChangeEvent ex)
		{
			this.info="";
		}
		public void setList_donne(List<Vente> list_donne) {
			this.list_donne = list_donne;
		}
		public void enregistre_Donne()
	    {
			if(this.nomUn.equalsIgnoreCase("Selectionnez la monnaie"))
			{
				this.info="Selectionnez s'il vous plait la monnaie!";
				return;
			}
			if(this.prix==0)
			{
				this.info="Saisissez s'il vous plait le prix!";
				return;
			}
			if(this.nomDeux.equalsIgnoreCase("Selectionnez la monnaie"))
			{
				this.info="Selectionnez s'il vous plait la monnaie!";
				return;
			}
			if(this.type.equalsIgnoreCase("selectionnez le type"))
			{
				this.info="Selectionnez s'il vous plait le type!";
				return;
			}
	    	
	    	if(db.Db_con.modifierDB("insert into vente(nom1,prix,nom2,type)values('"+this.nomUn+"',"+this.prix+",'"+this.nomDeux+"','"+this.type+"')")>0)
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
		
		public void Actualiser()
		{
			this.info="";
			
		}
	    public void supprimer_Donne()
	    {
	    	if(ve_click!=null) {
	    		this.veId=ve_click.veId;
	    		
			    	if(db.Db_con.modifierDB("delete from vente where Id="+this.veId+"")>0)
			    	{
			    		this.info="La ligne est supprimée !";
			    		return;
			    	}
			    	else
			    	{
			    		this.info="Echec de la suppression !";
			    		return;
			    	}
	    	}
	    }
	    
	    public void Change_listener(ValueChangeEvent e)
	    {
	    	this.info="";
	    }
	    public void modifier_Donne()
	    {
	    	
	
	    	
	    	if(this.veId<1)
	    	{
	    		this.info="Cliquez d'abord sur le bouton editer de la ligne à modifier";
	    		return;
	    	}
	    	
	    	if(db.Db_con.modifierDB("Update vente set nom1='"+this.nomUn+"',prix="+this.prix+",nom2='"+this.nomDeux+"',type='"+this.type+"' where Id="+this.veId+"")>0)
	    	{
	    		this.veId=-1;
	    		this.info="La ligne est modifier";
	    		this.nomUn="Selectionnez la monnaie";
	    		this.prix=0;
	    		this.nomDeux="Selectionnez la monnaie";
	    		this.type="selectionnez le type";
	    		this.disable=false;
	    		
	    		return;
	    	}
	    	else
	    	{
	    		this.info="Echec de la modification";
	    		return;
	    	}
	    	
	    }
	   
}
