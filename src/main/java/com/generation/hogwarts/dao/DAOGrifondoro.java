package com.generation.hogwarts.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.generation.hogwarts.entities.Factory;
import com.generation.hogwarts.entities.Grifondoro;
import com.generation.utility.dao.Database;
import com.generation.utility.entities.Entity;

public class DAOGrifondoro
{
	private Database db = Config.DB;
	
	//SINGLETON
	
	private static DAOGrifondoro INSTANCE;
	private DAOGrifondoro()
	{
		
	}
	
	public static DAOGrifondoro getInstance()
	{
		if(INSTANCE == null)
			INSTANCE = new DAOGrifondoro();
		return INSTANCE;
	}
	
	//Metodo read con richiamo a factory
	
	public List<Entity> read (String query, String... params)
	{
		List<Entity> ris = new ArrayList <Entity>();
		List <Map<String,String>> righe = db.rows(query);
		Entity e;
		for(Map<String,String> riga : righe)
		{
			e = Factory.make("grifondoro",riga);
			ris.add(e);
		}
		return ris;
	}
	public Entity cercaPerId(int id)
	{
		return read("select *from grifondoro where id = " + id).get(0);
	}
	
	public boolean delete(int id)
	{
		String query = "delete from grifondoro where id = ?";
		return db.update(query, id + "");
	}
	
	public boolean create (Entity e)
	{
		Grifondoro g = (Grifondoro) e;
		String query = "insert into grifondoro (nome,cognome,eta,quidditch) values (?,?,?,?)";
		String quidditch = g.isQuidditch()?"1":"0";
		return db.update(query, g.getNome(),g.getCognome(), g.getEta() + "",quidditch);
				
	}
	public boolean update(Entity eMod)
	{
		Grifondoro g = (Grifondoro) eMod;
		String query = "update grifondoro set nome = ?,cognome = ?,eta = ?,quidditch = ? where id = ?";
		String quidditch = g.isQuidditch()?"1":"0";
		return db.update(query, g.getNome(),g.getCognome(), g.getEta() + "",quidditch, g.getId() + "");
		
	}

}

