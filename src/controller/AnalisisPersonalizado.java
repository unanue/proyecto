package controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.classes.ClaseGasto;
import model.classes.ClaseIngreso;
import model.classes.Cuenta;
import model.classes.Movimiento;
import model.data.Connect;

/**
 * Servlet implementation class AnalisisPersonalizado
 */
@WebServlet("/protected_area/analisisPersonalizado")
public class AnalisisPersonalizado extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Connect c = new Connect();

	// String consulta = "";
	List<String> consulta;
	String mensaje = "";

	boolean filtroFecha;
	boolean filtroTipo;
	boolean filtroClase;
	boolean filtroUsuario;
	boolean filtroCuenta;

	String tipo;
	Timestamp fechaInicio;
	Timestamp fechaFin;
	int id_clase;
	String username;
	int id_cuenta;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AnalisisPersonalizado() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		if (request.getParameter("filtroFecha").equals("fecha_si")) {
			filtroFecha = true;
		} else {
			filtroFecha = false;
		}
		if (request.getParameter("filtroUsuario").equals("usuario_si")) {
			filtroUsuario = true;
		} else {
			filtroUsuario = false;
		}

		if (request.getParameter("filtroCuenta").equals("cuenta_si")) {
			filtroCuenta = true;
		} else {
			filtroCuenta = false;
		}

		System.out.println(filtroFecha);
		System.out.println(filtroUsuario);
		System.out.println(filtroCuenta);

		if (checkForm(request, response)) {
			consulta = new ArrayList<String>();
			if (filtroFecha == true) {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				Date aux = null;
				try {
					aux = format.parse(request.getParameter("fecha_inicio"));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.fechaInicio = new Timestamp(aux.getTime());

				try {
					aux = format.parse(request.getParameter("fecha_fin"));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.fechaFin = new Timestamp(aux.getTime());

				// aqui
				System.out.println("\nDentro");

				if (checkFechas(request, response)) {

					System.out.println("\nDentro bien");
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
					String fechaI = dateFormat.format(fechaInicio);
					String fechaF = dateFormat.format(fechaFin);
			
					consulta.add(fechaI + " a " + fechaF);
				} else {
					System.out.println("\nDentro mal");
					request.getSession().setAttribute("mensaje", mensaje);
					request.getRequestDispatcher("/protected_area/loadConsultaMovimientos").forward(request, response);
				}
			} else {
				consulta.add(null);
				System.out.println("\nFuera");
			}
			if (filtroUsuario == true) {
				this.username = request.getParameter("username");
				consulta.add(username);
			}
			else{
				consulta.add(null);
			}

			if (filtroCuenta == true) {
				this.id_cuenta = Integer.parseInt(request.getParameter("cuenta"));
				consulta.add(c.getIDao().getCuenta(id_cuenta).getDescripcion());
			}
			else{
				consulta.add(null);
			}
			
			request.getSession().setAttribute("consulta", consulta);
			
			System.out.println(fechaInicio);

			List<Movimiento> listaMovimientosPersonalizado = c.getIDao().getGenerarConsultaMovimientos(filtroFecha,
					false, false, filtroUsuario, filtroCuenta, null, fechaInicio, fechaFin, 0,
					username, id_cuenta);

			request.setAttribute("listaMovimientosPersonalizado", listaMovimientosPersonalizado);
			
			System.out.println("----------------- Movimientos personalizado --------------");
			for(int i=0;i<listaMovimientosPersonalizado.size();i++){
				System.out.println(listaMovimientosPersonalizado.get(i).getId_movimiento());
			}
			System.out.println("----------------- End Movimientos personalizado --------------");
			
		//continuacion
			
			
			Timestamp fromYear;	
			Timestamp toYear;	
			
			
			Timestamp fromMonth;	
			Timestamp toMonth;	
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			
			
			
			//fromYear toYear
			
			
			Calendar cal = Calendar.getInstance();
			
			int year = cal.get(Calendar.YEAR);
			
			
			Date dateFromYear = null;
			try {
				dateFromYear = format.parse(year+"-01-01");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			fromYear = new Timestamp(dateFromYear.getTime());
			
			Date dateToYear = null;
			try {
				dateToYear = format.parse(year+"-12-31");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			toYear = new Timestamp(dateToYear.getTime());
			
			
			//fromMonth toMonth
			
			
			int month = cal.get(Calendar.MONTH) + 1;
			

			Date dateFromMonth = null;
			try {
				if(month>9){
					dateFromMonth = format.parse(year+"-"+month+"-01");
				}
				else
				{
					dateFromMonth = format.parse(year+"-0"+month+"-01");
				}
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			fromMonth = new Timestamp(dateFromMonth.getTime());
			
			Calendar cal2 = Calendar.getInstance();
			
			cal2.add(Calendar.MONTH, month-1);  
			cal2.set(Calendar.DAY_OF_MONTH, 1);  
			cal2.add(Calendar.DATE, -1);  
			int lastDay = cal2.get(Calendar.DAY_OF_MONTH);
			
			
			
			Date dateToMonth = null;
			try {
				if(month>9){
					dateToMonth = format.parse(year+"-"+month+"-01");
				}
				else
				{
					dateToMonth = format.parse(year+"-0"+month+"-"+lastDay);
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			toMonth = new Timestamp(dateToMonth.getTime());
			
			System.out.println(fromYear);
			System.out.println(toYear);
			System.out.println(fromMonth);
			System.out.println(toMonth);
			
			
			//Evolucion year
			
			
			List<String>listaMeses = new ArrayList<String>(Arrays.asList(new String[] {"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"}));
			List<Integer>listaMesesUltimoDia = new ArrayList<Integer>(Arrays.asList(31,29,31,30,31,30,31,31,30,31,30,31));
			List<Timestamp>listaMesesFechasFrom = new ArrayList <Timestamp>();
			List<Timestamp>listaMesesFechasTo = new ArrayList <Timestamp>();
			int contador = 0;
			for(int i=0;i<listaMeses.size();i++){
				Date date = null;
				try {
					date = format.parse(year+"-"+(i+1)+"-01");
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Timestamp from = new Timestamp(date.getTime());
				listaMesesFechasFrom.add(from);
				
				Date date2 = null;
				try {
					date2 = format.parse(year+"-"+(i+1)+"-"+listaMesesUltimoDia.get(i));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				Timestamp to = new Timestamp(date2.getTime());
				listaMesesFechasTo.add(to);
			}
			System.out.println("Meses fechas FROM:----------------");
			for(int i=0;i<listaMesesFechasFrom.size();i++){
				System.out.println(listaMesesFechasFrom.get(i));
			}
			System.out.println("Meses fechas TO:----------------");
			for(int i=0;i<listaMesesFechasTo.size();i++){
				System.out.println(listaMesesFechasTo.get(i));
			}
			List<Movimiento>listaMesesMovimientos = new ArrayList<Movimiento>();
			List <Float> listaMesesIngresos = new ArrayList<Float>();
			List <Float> listaMesesGastos = new ArrayList<Float>();
			List <Float> listaMesesBeneficios = new ArrayList<Float>();
			for(int i=0;i<listaMeses.size();i++){
				listaMesesIngresos.add((float) 0);
				listaMesesGastos.add((float) 0);
				listaMesesBeneficios.add((float) 0);
			}
			
			List <Movimiento> listaMovimientosEvolucionYear;
			for(int i=0;i<listaMeses.size();i++){
				listaMovimientosEvolucionYear = c.getIDao().getGenerarAnalisisEstandar(listaMesesFechasFrom.get(i), listaMesesFechasTo.get(i));
				float totalIngresos = 0;
				float totalGastos = 0;
				for(int j=0;j<listaMovimientosEvolucionYear.size();j++){
					if(listaMovimientosEvolucionYear.get(j).getTipo().equals("Ingreso")){
						totalIngresos = totalIngresos + listaMovimientosEvolucionYear.get(j).getImporte();
					}
					else{
						totalGastos = totalGastos + listaMovimientosEvolucionYear.get(j).getImporte();
					}
				}
				listaMesesIngresos.set(i, totalIngresos);
				listaMesesGastos.set(i, totalGastos);
				listaMesesBeneficios.set(i, totalIngresos-totalGastos);
			}
			
		
			
			System.out.println("listaMesesIngresos :----------------");
			for(int i=0;i<listaMesesIngresos.size();i++){
				System.out.println(listaMesesIngresos.get(i));
			}
			System.out.println("listaMesesGastos :----------------");
			for(int i=0;i<listaMesesGastos.size();i++){
				System.out.println(listaMesesGastos.get(i));
			}
			System.out.println("listaMesesBeneficios :----------------");
			for(int i=0;i<listaMesesBeneficios.size();i++){
				System.out.println(listaMesesBeneficios.get(i));
			}

			request.setAttribute("listaMeses", listaMeses);
			request.setAttribute("listaMesesIngresos", listaMesesIngresos);
			request.setAttribute("listaMesesGastos", listaMesesGastos);
			request.setAttribute("listaMesesBeneficios", listaMesesBeneficios);
			
				
			
			//Movimientos
			List <Movimiento> listaMovimientosYear = c.getIDao().getGenerarAnalisisEstandar(fromYear, toYear);
			request.setAttribute("listaMovimientosYear", listaMovimientosYear);
			
			//Movimientos Year (Ingresos y Gastos) 
			List <Movimiento> listaIngresosYear = new ArrayList<Movimiento>();
			List <Movimiento> listaGastosYear = new ArrayList<Movimiento>();
			for(int i=0;i<listaMovimientosYear.size();i++){
				if(listaMovimientosYear.get(i).getTipo().equals("Ingreso")){
					listaIngresosYear.add(listaMovimientosYear.get(i));
				}
				else{
					listaGastosYear.add(listaMovimientosYear.get(i));
				}
			}
			request.setAttribute("listaIngresosYear", listaIngresosYear);
			request.setAttribute("listaGastosYear", listaGastosYear);
			
			System.out.println("listaIngresosYear:" +listaIngresosYear);
			for(int i=0;i<listaIngresosYear.size();i++){
				System.out.println(listaIngresosYear.get(i).getImporte());
			}
			System.out.println("-------------------------------------");
			//Total Ingresos Year
			float totalIngresosYear = 0;
			for(int i=0;i<listaIngresosYear.size();i++){
				totalIngresosYear = totalIngresosYear + listaIngresosYear.get(i).getImporte();
			}
			System.out.println("totalIngresosYear:" +totalIngresosYear);
			
			//Total Gastos Year
			float totalGastosYear = 0;
			for(int i=0;i<listaGastosYear.size();i++){
				totalGastosYear = totalGastosYear + listaGastosYear.get(i).getImporte();
			}
			System.out.println("totalGastosYear: " +totalGastosYear);
			
			//Beneficio Year
			float beneficioYear = totalIngresosYear-totalGastosYear;
			System.out.println("beneficioYear: " +beneficioYear);
			
		
			request.setAttribute("totalIngresosYear", totalIngresosYear);
			request.setAttribute("totalGastosYear", totalGastosYear);
			request.setAttribute("beneficioYear", beneficioYear);
			
			//Movimientos Month (Ingresos y Gastos) 
			List <Movimiento> listaMovimientosMonth = c.getIDao().getGenerarAnalisisEstandar(fromMonth, toMonth);
			request.setAttribute("listaMovimientosMonth", listaMovimientosMonth);
			
			List <Movimiento> listaIngresosMonth = new ArrayList<Movimiento>();
			List <Movimiento> listaGastosMonth = new ArrayList<Movimiento>();
			for(int i=0;i<listaMovimientosMonth.size();i++){
				if(listaMovimientosMonth.get(i).getTipo().equals("Ingreso")){
					listaIngresosMonth.add(listaMovimientosMonth.get(i));
				}
				else{
					listaGastosMonth.add(listaMovimientosMonth.get(i));
				}
			}
			request.setAttribute("listaIngresosMonth", listaIngresosMonth);
			request.setAttribute("listaGastosMonth", listaGastosMonth);
			
			
			
			//Total Ingresos Month
			float totalIngresosMonth = 0;
			for(int i=0;i<listaIngresosMonth.size();i++){
				totalIngresosMonth = totalIngresosMonth + listaIngresosMonth.get(i).getImporte();
			}
			//Total Gastos Month
			float totalGastosMonth = 0;
			for(int i=0;i<listaGastosMonth.size();i++){
				totalGastosMonth = totalGastosMonth + listaGastosMonth.get(i).getImporte();
			}
			//Beneficio Month
			float beneficioMonth = totalIngresosMonth-totalGastosMonth;
			
			request.setAttribute("totalIngresosMonth", totalIngresosMonth);
			request.setAttribute("totalGastosMonth", totalGastosMonth);
			request.setAttribute("beneficioMonth", beneficioMonth);
			
			
			//ClaseIngreso y ClaseGasto
			List <ClaseIngreso> listaClaseIngreso = c.getIDao().getClaseIngreso();
			request.setAttribute("listaClaseIngreso", listaClaseIngreso);
			System.out.println("listaClaseIngreso size:" +listaClaseIngreso.size());
			
			List <ClaseGasto> listaClaseGasto = c.getIDao().getClaseGasto();
			request.setAttribute("listaClaseGasto", listaClaseGasto);
			
			//ClaseIngreso Year
			List <Float> listaClaseIngresoYear = new ArrayList<Float>();
			for(int i=0;i<listaClaseIngreso.size();i++){
				listaClaseIngresoYear.add((float) 0);
			}
			System.out.println("listaClaseIngresoYear size: " +listaIngresosYear.size());
//			System.out.println("listaClaseIngresoYear:" +listaIngresosYear);
			for(int i=0;i<listaClaseIngresoYear.size();i++){
				System.out.println(listaClaseIngresoYear.get(i));
			}
			System.out.println("-------------------------------------");
			System.out.println("-------------------------------------");
			System.out.println(listaClaseIngresoYear.size());
			for(int i=0;i<listaIngresosYear.size();i++){
				int puntero=(listaIngresosYear.get(i).getId_clase())-1;
				System.out.println("puntero: "+puntero);
				float total = 0;
				System.out.println("anterior: "+listaClaseIngresoYear.get(puntero));
				System.out.println("importe: "+listaIngresosYear.get(i).getImporte());
				total = listaClaseIngresoYear.get(puntero) + listaIngresosYear.get(i).getImporte();
				System.out.println("total: "+total);
				listaClaseIngresoYear.set(puntero,total);
				for(int j=0;j<listaClaseIngresoYear.size();j++){
					System.out.println(listaClaseIngresoYear.get(j));
				}
			}
			request.setAttribute("listaClaseIngresoYear", listaClaseIngresoYear);
			System.out.println("-------------");
			for(int i=0;i<listaClaseIngresoYear.size();i++){
				System.out.println(listaClaseIngresoYear.get(i));
			}
			System.out.println("-------------");
			System.out.println("-------------------------------------");
			System.out.println("-------------------------------------");
			
			//ClaseIngreso Month
			List <Float> listaClaseIngresoMonth = new ArrayList<Float>();
			for(int i=0;i<listaClaseIngreso.size();i++){
				listaClaseIngresoMonth.add((float) 0);
			}
			for(int i=0;i<listaIngresosMonth.size();i++){
				int puntero=(listaIngresosMonth.get(i).getId_clase())-1;
				System.out.println("puntero: "+puntero);
				float total = 0;
				System.out.println("anterior: "+listaClaseIngresoMonth.get(puntero));
				System.out.println("importe: "+listaIngresosMonth.get(i).getImporte());
				total = listaClaseIngresoMonth.get(puntero) + listaIngresosMonth.get(i).getImporte();
				System.out.println("total: "+total);
				listaClaseIngresoMonth.set(puntero,total);
				for(int j=0;j<listaClaseIngresoMonth.size();j++){
					System.out.println(listaClaseIngresoMonth.get(j));
				}
			}
			request.setAttribute("listaClaseIngresoMonth", listaClaseIngresoMonth);
			
			//ClaseGasto Year
			List <Float> listaClaseGastoYear = new ArrayList<Float>();
			for(int i=0;i<listaClaseGasto.size();i++){
				listaClaseGastoYear.add((float) 0);
			}
			for(int i=0;i<listaGastosYear.size();i++){
				int puntero=(listaGastosYear.get(i).getId_clase())-1;
				System.out.println("puntero: "+puntero);
				float total = 0;
				System.out.println("anterior: "+listaClaseGastoYear.get(puntero));
				System.out.println("importe: "+listaGastosYear.get(i).getImporte());
				total = listaClaseGastoYear.get(puntero) + listaGastosYear.get(i).getImporte();
				System.out.println("total: "+total);
				listaClaseGastoYear.set(puntero,total);
				for(int j=0;j<listaClaseGastoYear.size();j++){
					System.out.println(listaClaseGastoYear.get(j));
				}
			}
			request.setAttribute("listaClaseGastoYear", listaClaseGastoYear);
			
			//ClaseGasto Month
			List <Float> listaClaseGastoMonth = new ArrayList<Float>();
			for(int i=0;i<listaClaseGasto.size();i++){
				listaClaseGastoMonth.add((float) 0);
			}
			for(int i=0;i<listaGastosMonth.size();i++){
				int puntero=(listaGastosMonth.get(i).getId_clase())-1;
				System.out.println("puntero: "+puntero);
				float total = 0;
				System.out.println("anterior: "+listaClaseGastoMonth.get(puntero));
				System.out.println("importe: "+listaGastosMonth.get(i).getImporte());
				total = listaClaseGastoMonth.get(puntero) + listaGastosMonth.get(i).getImporte();
				System.out.println("total: "+total);
				listaClaseGastoMonth.set(puntero,total);
				for(int j=0;j<listaClaseGastoMonth.size();j++){
					System.out.println(listaClaseGastoMonth.get(j));
				}
			}
			request.setAttribute("listaClaseGastoMonth", listaClaseGastoMonth);
			
			//Cuentas
			List <Cuenta> listaCuentas = c.getIDao().getCuentas();
			request.setAttribute("listaCuentas", listaCuentas);
			
			
		//hasta aqui
			
			List <Movimiento> listaIngresosPersonalizado = new ArrayList<Movimiento>();
			List <Movimiento> listaGastosPersonalizado = new ArrayList<Movimiento>();
			for(int i=0;i<listaMovimientosPersonalizado.size();i++){
				if(listaMovimientosPersonalizado.get(i).getTipo().equals("Ingreso")){
					listaIngresosPersonalizado.add(listaMovimientosPersonalizado.get(i));
				}
				else{
					listaGastosPersonalizado.add(listaMovimientosPersonalizado.get(i));
				}
			}
			request.setAttribute("listaIngresosPersonalizado", listaIngresosPersonalizado);
			request.setAttribute("listaGastosPersonalizado", listaGastosPersonalizado);
			
			System.out.println("----------------- listaIngresosPersonalizado --------------");
			for(int i=0;i<listaIngresosPersonalizado.size();i++){
				System.out.println(listaIngresosPersonalizado.get(i).getId_movimiento());
			}
			System.out.println("----------------- End listaIngresosPersonalizado --------------");
			System.out.println("----------------- listaGastosPersonalizado --------------");
			for(int i=0;i<listaGastosPersonalizado.size();i++){
				System.out.println(listaGastosPersonalizado.get(i).getId_movimiento());
			}
			System.out.println("----------------- End listaGastosPersonalizado --------------");
			
			
			//Total Ingresos Personalizado 
			float totalIngresosPersonalizado = 0;
			for(int i=0;i<listaIngresosPersonalizado.size();i++){
				totalIngresosPersonalizado = totalIngresosPersonalizado + listaIngresosPersonalizado.get(i).getImporte();
			}
			//Total Gastos Personalizado
			float totalGastosPersonalizado = 0;
			for(int i=0;i<listaGastosPersonalizado.size();i++){
				totalGastosPersonalizado = totalGastosPersonalizado + listaGastosPersonalizado.get(i).getImporte();
			}
			//Beneficio Personalizado
			float beneficioPersonalizado = totalIngresosPersonalizado-totalGastosPersonalizado;
			
			System.out.println("Ingresos personalizado "+totalIngresosPersonalizado);
			System.out.println("Gastos personalizado "+totalGastosPersonalizado);
			System.out.println("Beneficio personalizado "+beneficioPersonalizado);
			
			request.setAttribute("totalIngresosPersonalizado", totalIngresosPersonalizado);
			request.setAttribute("totalGastosPersonalizado", totalGastosPersonalizado);
			request.setAttribute("beneficioPersonalizado", beneficioPersonalizado);
			
			//ClaseIngreso Personalizado
			List <Float> listaClaseIngresoPersonalizado = new ArrayList<Float>();
			for(int i=0;i<listaClaseIngreso.size();i++){
				listaClaseIngresoPersonalizado.add((float) 0);
			}
			for(int i=0;i<listaIngresosPersonalizado.size();i++){
				int puntero=(listaIngresosPersonalizado.get(i).getId_clase())-1;
				System.out.println("puntero: "+puntero);
				float total = 0;
//				System.out.println("anterior: "+listaClaseIngresoPersonalizado.get(puntero));
//				System.out.println("importe: "+listaIngresosPersonalizado.get(i).getImporte());
				total = listaClaseIngresoPersonalizado.get(puntero) + listaIngresosPersonalizado.get(i).getImporte();
//				System.out.println("total: "+total);
				listaClaseIngresoPersonalizado.set(puntero,total);
//				for(int j=0;j<listaClaseIngresoPersonalizado.size();j++){
//					System.out.println(listaClaseIngresoPersonalizado.get(j));
//				}
			}
			for(int j=0;j<listaClaseIngresoPersonalizado.size();j++){
				System.out.println(listaClaseIngresoPersonalizado.get(j));
			}
			request.setAttribute("listaClaseIngresoPersonalizado", listaClaseIngresoPersonalizado);
			
			//ClaseGasto Personalizado
			List <Float> listaClaseGastoPersonalizado = new ArrayList<Float>();
			for(int i=0;i<listaClaseGasto.size();i++){
				listaClaseGastoPersonalizado.add((float) 0);
			}
			for(int i=0;i<listaGastosPersonalizado.size();i++){
				int puntero=(listaGastosPersonalizado.get(i).getId_clase())-1;
				System.out.println("puntero: "+puntero);
				float total = 0;
//				System.out.println("anterior: "+listaClaseGastoPersonalizado.get(puntero));
//				System.out.println("importe: "+listaGastosPersonalizado.get(i).getImporte());
				total = listaClaseGastoPersonalizado.get(puntero) + listaGastosPersonalizado.get(i).getImporte();
//				System.out.println("total: "+total);
				listaClaseGastoPersonalizado.set(puntero,total);
//				for(int j=0;j<listaClaseGastoPersonalizado.size();j++){
//					System.out.println(listaClaseGastoPersonalizado.get(j));
//				}
			}
			for(int j=0;j<listaClaseGastoPersonalizado.size();j++){
				System.out.println(listaClaseGastoPersonalizado.get(j));
			}
			request.setAttribute("listaClaseGastoPersonalizado", listaClaseGastoPersonalizado);
			String personalizado = "personalizado";
			request.setAttribute("personalizado", personalizado);
			
			
			System.out.println("llego per");
			
			request.getRequestDispatcher("/protected_area/analisisPersonalizado.jsp").forward(request, response);

		} else {
			System.out.println("llega a mensaje");
			request.getSession().setAttribute("mensaje", mensaje);
			request.getRequestDispatcher("/protected_area/loadConsultaMovimientos").forward(request, response);
		}

	}

	private boolean checkForm(HttpServletRequest request, HttpServletResponse response) {
		if (filtroFecha == true) {
			if ((request.getParameter("fecha_inicio").equals("")) || (request.getParameter("fecha_fin").equals(""))) {
				mensaje = "Escoja las fechas que correspondan.";
				System.out.println(mensaje);
				return false;
			} else {
				return true;
			}
		} else {
			return true;
		}

	}

	private boolean checkFechas(HttpServletRequest request, HttpServletResponse response) {
		if (filtroFecha == true) {
			if (fechaInicio.after(fechaFin)) {
				mensaje = "La Fecha Inicio no puede ser mayor que la Fecha Fin.";
				System.out.println(mensaje);
				return false;
			} else {
				return true;
			}
		} else {
			return true;
		}

	}

}