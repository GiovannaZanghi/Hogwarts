package com.generation.hogwarts.dao;
import java.util.*;
import com.generation.hogwarts.entities.*;
import com.generation.utility.dao.Database;
import com.generation.utility.entities.Entity;

public class DAOTassorosso
{
	private Database db = Config.DB;
	
	private static DAOTassorosso INSTANCE;
	
	private DAOTassorosso()
	{
		// Costruttore vuoto
	}
	
	public static DAOTassorosso getInstance()
	{
		if(INSTANCE == null)
			INSTANCE = new DAOTassorosso();
		return INSTANCE;
	}
	
	public List<Entity> read(String query, String... params)
	{
		List<Entity> ris = new ArrayList<Entity>();

		List<Map<String, String>> righe = db.rows(query);
		
		Entity e;
		
		for(Map<String,String> riga : righe)
		{
			e = Factory.make("tassorosso",riga);
			ris.add(e);
		}
		return ris;
	}//Fine di read()
	
	public Entity cercaPerId(int id)
	{
		return read("select * from tassorosso where id = " + id).get(0);
	}

	public boolean delete(int id)
	{
		String query = "delete from tassorosso where id = ?";
		return db.update(query, id + "");
	}
	
	public boolean create(Entity e)
	{
		Tassorosso s = (Tassorosso) e;
		String query = "insert into tassorosso (nome,cognome,eta,quidditch) values (?,?,?,?)";
		return db.update(query, s.getNome(), s.getCognome(), s.getEta() + "", s.isQuidditch() + "");
	}
	
	public boolean update(Entity e)
	{
		Tassorosso s = (Tassorosso) e;
		String query = "update tassorosso set nome = ?, cognome = ?, eta = ?, quidditch = ? where id = ?";
		return db.update(query, s.getNome(), s.getCognome(), s.getEta() + "", s.isQuidditch() + "", s.getId() + "");
	}
}
