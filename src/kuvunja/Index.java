package kuvunja;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;



import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
//import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSession;




@ViewScoped

public class Index implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String login;
	private String password;
	private String info;
	
	
	public Index() {}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	
	public void change_message(ValueChangeEvent ex)
	{
		this.info="";
	}
	
	HttpSession user=null;
	
	public String se_connecter()
	{
		String fromoutcome=null;
		
		user=(HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		
		ResultSet resu=db.Db_con.extraireData("select *from connecter where login='"+this.login+"'and password='"+this.password+"'");
		
		if(resu!=null)
		{
			try {
				if(resu.next())
				{
					if(resu.getString("profil").equalsIgnoreCase("echange"))
					{
						user.setAttribute("new_monnaie", true);
						fromoutcome="echange";
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(fromoutcome==null)
		{
			
			this.info="Désolé,impossible de Vous connecter au Système!";
			
		}
		
		return fromoutcome;
		
	}
	
}
