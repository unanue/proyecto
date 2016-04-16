package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import classes.Cuenta;
import classes.Usuario;
import model.Connect;

/**
 * Servlet implementation class LoadAnalisisPersonalizado
 */
@WebServlet("/protected_area/loadAnalisisPersonalizado")
public class LoadAnalisisPersonalizado extends HttpServlet {
	
	Connect c = new Connect();
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoadAnalisisPersonalizado() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		List<Usuario> listaUsuarios = c.getIDao().getUsers();
		request.setAttribute("listaUsuarios", listaUsuarios);
		List<Cuenta> listaCuentas = c.getIDao().getCuentas();		 
		request.setAttribute("listaCuentas", listaCuentas);
		
		request.getRequestDispatcher("/protected_area/consultaAnalisisPersonalizado.jsp").forward(request, response);	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
