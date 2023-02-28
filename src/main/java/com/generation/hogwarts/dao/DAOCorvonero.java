package com.generation.hogwarts.dao;
import java.util.*;
import com.generation.hogwarts.entities.*;
import com.generation.utility.dao.Database;
import com.generation.utility.entities.Entity;

public class DAOCorvonero
{
	private Database db = Config.DB;
	
	private static DAOCorvonero INSTANCE;
	
	private DAOCorvonero()
	{
		// Costruttore vuoto
	}
	
	public static DAOCorvonero getInstance()
	{
		if(INSTANCE == null)
			INSTANCE = new DAOCorvonero();
		return INSTANCE;
	}
	
	public List<Entity> read(String query, String... params)
	{
		List<Entity> ris = new ArrayList<Entity>();

		List<Map<String, String>> righe = db.rows(query);
		
		Entity e;
		
		for(Map<String,String> riga : righe)
		{
			e = Factory.make("corvonero",riga);
			ris.add(e);
		}
		return ris;
	}//Fine di read()
	
	public Entity cercaPerId(int id)
	{
		return read("select * from corvonero where id = " + id).get(0);
	}

	public boolean delete(int id)
	{
		String query = "delete from corvonero where id = ?";
		return db.update(query, id + "");
	}
	
	public boolean create(Entity e)
	{
		Corvonero s = (Corvonero) e;
		String query = "insert into corvonero (nome,cognome,eta,quidditch) values (?,?,?,?)";
		return db.update(query, s.getNome(), s.getCognome(), s.getEta() + "", s.isQuidditch() + "");
	}
	
	public boolean update(Entity e)
	{
		Corvonero s = (Corvonero) e;
		String query = "update corvonero set nome = ?, cognome = ?, eta = ?, quidditch = ? where id = ?";
		return db.update(query, s.getNome(), s.getCognome(), s.getEta() + "", s.isQuidditch() + "", s.getId() + "");
	}
}
