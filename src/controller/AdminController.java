package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vo.WelcomeVo;

public class AdminController implements Controller{
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String method = req.getMethod();
		System.out.println(method);
		if(method.equalsIgnoreCase("POST")) { 
			String msg = req.getParameter("msg");
			int id = Integer.parseInt(req.getParameter("id"));
			
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				String connStr = "jdbc:oracle:thin:@localhost:1521:xe";
				con = DriverManager.getConnection(connStr, "system", "1234");
				String sql = "INSERT INTO welcome_tbl(id, msg) Values(?,?)";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, id);
				pstmt.setString(2, msg);
				int result = pstmt.executeUpdate();
				System.out.println("리다이렉트 명령");
				return "redirect::/admin";
			}catch (Exception e) {
				
			}
			
			
		}
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String connStr = "jdbc:oracle:thin:@localhost:1521:xe";
			con = DriverManager.getConnection(connStr, "system", "1234");
			String sql = "SELECT MAX(id) +1 maxId FROM welcome_tbl";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			int maxId=1;
			if(rs.next()) {
				maxId = rs.getInt("maxId");
			}
			req.setAttribute("maxId", maxId);
			sql = "SELECT * FROM welcome_tbl ORDER BY id DESC";
			pstmt.close();
			rs.close();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			List<WelcomeVo> list = new ArrayList<>();
			while(rs.next()) {
				int id = rs.getInt("id");
				String msg = rs.getString("msg");
				
				WelcomeVo vo = new WelcomeVo(id, msg);
				list.add(vo);
			}
			req.setAttribute("list", list);
		}catch (Exception e) {
			System.out.println("오라클 오류");
			e.printStackTrace();
		}finally {
			if(rs !=null) try {rs.close();} catch(Exception e) {}
			if(pstmt !=null) try {pstmt.close();} catch(Exception e) {}
			if(con !=null) try {con.close();} catch(Exception e) {}
		}
		return "admin";
	}
}
