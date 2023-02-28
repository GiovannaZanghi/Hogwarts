package com.generation.hogwarts.entities;
import java.util.HashMap;
import java.util.Map;
import com.generation.utility.entities.Entity;

public abstract class Factory
{
	public static Entity make(String casa)
	{
		
		Entity e = null;
		
		switch(casa.toLowerCase())
		{
			case "grifondoro"	:
				e = new Grifondoro();
			break;
			case "tassorosso":
				e = new Tassorosso();
			break;
			case "corvonero":
				e = new Corvonero();
			break;
			case "serpeverde":
				e = new Serpeverde();
		}
		return e;
	}
	
	public static Entity make(String casa, Map<String, String> riga)
	{
		Entity e = make(casa);
		if(e != null)
			e.fromMap(riga);
		return e;
	}
	
	public static Entity makeFromRequest(String casa, Map<String, String[]> request)
	{
		Map<String, String> riga = new HashMap<String,String>();
		for(String chiave : request.keySet())
		{
			riga.put(chiave, request.get(chiave)[0]);
		}
		return make(casa,riga);
	}
}
