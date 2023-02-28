package com.generation.hogwarts.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.generation.hogwarts.dao.DAOSerpeverde;
import com.generation.hogwarts.entities.Factory;
import com.generation.utility.entities.Entity;
import com.generation.utility.viste.GestoreTemplate;

/**
 * Servlet implementation class IndexGrifondoro
 */
@WebServlet("/IndexSerpeverde")
public class IndexSerpeverde extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public GestoreTemplate GT = null;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html");
		if(GT == null)
			GT = new GestoreTemplate(request.getServletContext().getRealPath("/viste/Serpeverde/"));
		
		//FRONT-CONTROLLER
		String comando = request.getParameter("comando");
		if(comando == null)
			comando = "home";
		
		String ris = "";
		switch(comando.toLowerCase())
		{
			case "home"	:
				ris = GT.leggiHTML("../home.html");
			break;
			case "elencoserpeverde":
				ris = GT.graficaEntity(DAOSerpeverde.getInstance().read("select * from serpeverde"), "templateSerpeverde.html" );
			break;
			case "eliminaserpeverde":
				int idKill = Integer.parseInt(request.getParameter("id"));
				if(DAOSerpeverde.getInstance().delete(idKill))
					System.out.println("eliminato con sucesso");
				else
					System.out.println("errore nell'eliminazione");
				
				ris = GT.leggiHTML("../home.html");		
			break;
			
			case "formnuovoserpeverde":
				ris = GT.leggiHTML("formnuovoserpeverde.html");
			
			break;
			
			case "nuovoserpeverde":
				
				Entity e = Factory.makeFromRequest("serpeverde", request.getParameterMap());
				if(DAOSerpeverde.getInstance().create(e))
					System.out.println("Serpeverde creato con sucesso");
				else
					System.out.println("Problema nella creazione di Serpeverde");
				ris = GT.leggiHTML("formnuovoserpeverde.html");
			break;
			case "formmodificaserpeverde"	:
				int idModifica = Integer.parseInt(request.getParameter("id"));
				Entity serpModifica = DAOSerpeverde.getInstance().cercaPerId(idModifica);
				ris = GT.graficaEntity(serpModifica, "formmodificaserpeverde.html");
			break;
			case "modificaserpeverde"	:
				Entity eMod = Factory.makeFromRequest("serpeverde", request.getParameterMap());
				System.out.println(eMod);
				if(DAOSerpeverde.getInstance().update(eMod))
					System.out.println("Modifica avvenuta con successo.");
				else
					System.out.println("Errore nella modifica");
				ris = GT.leggiHTML("../home.html");
			break;
			default: 
				System.out.println("Errore 404 - Metodo non riconosciuto");
			break;
		}//Fine switch
		response.getWriter().append(ris);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}

}
