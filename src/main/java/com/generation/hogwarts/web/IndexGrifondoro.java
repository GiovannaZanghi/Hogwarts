package com.generation.hogwarts.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.generation.hogwarts.dao.DAOGrifondoro;
import com.generation.hogwarts.entities.Factory;
import com.generation.utility.entities.Entity;
import com.generation.utility.viste.GestoreTemplate;

/**
 * Servlet implementation class IndexGrifondoro
 */
@WebServlet("/IndexGrifondoro")
public class IndexGrifondoro extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public GestoreTemplate GT = null;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html");
		if(GT == null)
			GT = new GestoreTemplate(request.getServletContext().getRealPath("/viste/Grifondoro/"));
		
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
			case "elencogrifondoro":
				String elenco = GT.graficaEntity(DAOGrifondoro.getInstance().read("select * from grifondoro"), "templateGrifondoro.html" );
				ris = GT.leggiHTML("prova.html");
				ris = ris.replace("[elencogrifondoro]", elenco);
				//response.getWriter().append(ris2);
			break;
			case "eliminagrifondoro":
				int idKill = Integer.parseInt(request.getParameter("id"));
				if(DAOGrifondoro.getInstance().delete(idKill))
					System.out.println("eliminato con sucesso");
				else
					System.out.println("errore nell'eliminazione");
				
				ris = GT.leggiHTML("../home.html");		
			break;
			
			case "formnuovogrifondoro":
				ris = GT.leggiHTML("formnuovogrifondoro.html");
			
			break;
			
			case "nuovogrifondoro":
				
				Entity e = Factory.makeFromRequest("grifondoro", request.getParameterMap());
				if(DAOGrifondoro.getInstance().create(e))
					System.out.println("Grifondoro creato con sucesso");
				else
					System.out.println("Problema nella creazione di Grifondoro");
				ris = GT.leggiHTML("formnuovogrifondoro.html");
			break;
			case "formmodificagrifondoro"	:
				int idModifica = Integer.parseInt(request.getParameter("id"));
				Entity grifModifica = DAOGrifondoro.getInstance().cercaPerId(idModifica);
				ris = GT.graficaEntity(grifModifica, "formmodificagrifondoro.html");
			break;
			case "modificagrifondoro"	:
				Entity eMod = Factory.makeFromRequest("grifondoro", request.getParameterMap());
				System.out.println(eMod);
				if(DAOGrifondoro.getInstance().update(eMod))
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
