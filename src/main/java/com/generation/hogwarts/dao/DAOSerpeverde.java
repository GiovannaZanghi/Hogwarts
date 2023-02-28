package com.generation.hogwarts.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.generation.hogwarts.entities.Factory;
import com.generation.hogwarts.entities.Serpeverde;
import com.generation.utility.dao.Database;
import com.generation.utility.entities.Entity;

public class DAOSerpeverde 
{
		private Database db = Config.DB;
		
		private static DAOSerpeverde INSTANCE;
		private DAOSerpeverde()
		{
			
		}
		
		public static DAOSerpeverde getInstance()
		{
			if(INSTANCE == null)
				INSTANCE = new DAOSerpeverde();
			return INSTANCE;
		}
		
		public List<Entity> read (String query, String... params)
		{
			List<Entity> ris = new ArrayList <Entity>();
			List <Map<String,String>> righe = db.rows(query);
			Entity e;
			for(Map<String,String> riga : righe)
			{
				e = Factory.make("serpeverde",riga);
				ris.add(e);
			}
			return ris;
		}
		public Entity cercaPerId(int id)
		{
			return read("select *from serpeverde where id = " + id).get(0);
		}
		
		public boolean delete(int id)
		{
			String query = "delete from serpeverde where id = ?";
			return db.update(query, id + "");
		}
		
		public boolean create (Entity e)
		{
			Serpeverde s = (Serpeverde) e;
			String query = "insert into serpeverde (nome,cognome,eta,quidditch) values (?,?,?,?)";
			String quidditch = s.isQuidditch()?"1":"0";
			return db.update(query, s.getNome(),s.getCognome(), s.getEta() + "",quidditch);
					
		}
		public boolean update(Entity eMod)
		{
			Serpeverde s = (Serpeverde) eMod;
			String query = "update serpeverde set nome = ?,cognome = ?,eta = ?,quidditch = ? where id = ?";
			String quidditch = s.isQuidditch()?"1":"0";
			return db.update(query, s.getNome(),s.getCognome(), s.getEta() + "",quidditch);
			
		}

	}

