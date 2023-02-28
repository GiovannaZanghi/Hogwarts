package com.generation.hogwarts.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.generation.hogwarts.dao.DAOCorvonero;
import com.generation.hogwarts.entities.Factory;
import com.generation.utility.entities.Entity;
import com.generation.utility.viste.GestoreTemplate;

/**
 * Servlet implementation class IndexGrifondoro
 */
@WebServlet("/IndexCorvonero")
public class IndexCorvonero extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public GestoreTemplate GT = null;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html");
		if(GT == null)
			GT = new GestoreTemplate(request.getServletContext().getRealPath("/viste/Corvonero/"));
		
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
			case "elencocorvonero":
				ris = GT.graficaEntity(DAOCorvonero.getInstance().read("select * from corvonero"), "templateCorvonero.html" );
			break;
			case "eliminacorvonero":
				int idKill = Integer.parseInt(request.getParameter("id"));
				if(DAOCorvonero.getInstance().delete(idKill))
					System.out.println("eliminato con sucesso");
				else
					System.out.println("errore nell'eliminazione");
				
				ris = GT.leggiHTML("../home.html");		
			break;
			
			case "formnuovocorvonero":
				ris = GT.leggiHTML("formnuovocorvonero.html");
			
			break;
			
			case "nuovocorvonero":
				
				Entity e = Factory.makeFromRequest("corvonero", request.getParameterMap());
				if(DAOCorvonero.getInstance().create(e))
					System.out.println("Corvonero creato con sucesso");
				else
					System.out.println("Problema nella creazione di Corvonero");
				ris = GT.leggiHTML("formnuovocorvonero.html");
			break;
			case "formmodificacorvonero"	:
				int idModifica = Integer.parseInt(request.getParameter("id"));
				Entity serpModifica = DAOCorvonero.getInstance().cercaPerId(idModifica);
				ris = GT.graficaEntity(serpModifica, "formmodificacorvonero.html");
			break;
			case "modificacorvonero"	:
				Entity eMod = Factory.makeFromRequest("corvonero", request.getParameterMap());
				System.out.println(eMod);
				if(DAOCorvonero.getInstance().update(eMod))
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
