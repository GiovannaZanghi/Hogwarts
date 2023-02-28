package com.generation.hogwarts.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.generation.hogwarts.dao.DAOTassorosso;
import com.generation.hogwarts.entities.Factory;
import com.generation.utility.entities.Entity;
import com.generation.utility.viste.GestoreTemplate;

/**
 * Servlet implementation class IndexGrifondoro
 */
@WebServlet("/IndexTassorosso")
public class IndexTassorosso extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public GestoreTemplate GT = null;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html");
		if(GT == null)
			GT = new GestoreTemplate(request.getServletContext().getRealPath("/viste/Tassorosso/"));
		
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
			case "elencotassorosso":
				ris = GT.graficaEntity(DAOTassorosso.getInstance().read("select * from tassorosso"), "templateTassorosso.html" );
			break;
			case "eliminatassorosso":
				int idKill = Integer.parseInt(request.getParameter("id"));
				if(DAOTassorosso.getInstance().delete(idKill))
					System.out.println("eliminato con sucesso");
				else
					System.out.println("errore nell'eliminazione");
				
				ris = GT.leggiHTML("../home.html");		
			break;
			
			case "formnuovotassorosso":
				ris = GT.leggiHTML("formnuovotassorosso.html");
			
			break;
			
			case "nuovotassorosso":
				
				Entity e = Factory.makeFromRequest("tassorosso", request.getParameterMap());
				if(DAOTassorosso.getInstance().create(e))
					System.out.println("Tassorosso creato con sucesso");
				else
					System.out.println("Problema nella creazione di Tassorosso");
				ris = GT.leggiHTML("formnuovotassorosso.html");
			break;
			case "formmodificatassorosso"	:
				int idModifica = Integer.parseInt(request.getParameter("id"));
				Entity serpModifica = DAOTassorosso.getInstance().cercaPerId(idModifica);
				ris = GT.graficaEntity(serpModifica, "formmodificatassorosso.html");
			break;
			case "modificatassorosso"	:
				Entity eMod = Factory.makeFromRequest("tassorosso", request.getParameterMap());
				System.out.println(eMod);
				if(DAOTassorosso.getInstance().update(eMod))
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
