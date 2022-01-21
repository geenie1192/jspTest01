package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FrontController extends HttpServlet {
	private Map<String, Controller> uriMap = null;
	@Override
	public void init() throws ServletException {
	
		uriMap = new HashMap<String, Controller>();
		uriMap.put("/", new IndexController());
		uriMap.put("/admin", new AdminController());
	}
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		
		String url = req.getRequestURI();
		String contextPath = req.getContextPath();
		String path = url.substring(contextPath.length());
		
		Controller c = uriMap.get(path);
		String view = c.execute(req, resp);
		RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/views/" + view + ".jsp");
		rd.forward(req, resp);
	}
}
