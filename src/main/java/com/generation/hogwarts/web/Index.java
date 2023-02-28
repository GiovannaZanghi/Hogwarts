package com.generation.hogwarts.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.generation.utility.viste.GestoreTemplate;
@WebServlet("/Index")
public class Index extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	public GestoreTemplate GT = null;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html");
		if(GT == null)
			GT = new GestoreTemplate(request.getServletContext().getRealPath("/viste/"));
		
		String comando = request.getParameter("comando");
		if(comando == null)
			comando = "home";
	
		String ris = "";
		switch(comando.toLowerCase())
		{
			case "home"	:
				ris = GT.leggiHTML("home.html");
			break;
			case "pagecasate" :
                ris = GT.leggiHTML("pagecasate.html");
                break;
			case "aggiungimago" :
				 ris = GT.leggiHTML("aggiungimago.html");
			break;
			default	:
				ris = "404 - COMANDO NON RICONOSCIUTO";
			break;
			
		}
		ris = GT.leggiHTML("menu.html") + ris;
		response.getWriter().append(ris);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
			doGet(request, response);
	}
}