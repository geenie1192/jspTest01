package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vo.WelcomeVo;

public class IndexController implements Controller{
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String connStr = "jdbc:oracle:thin:@localhost:1521:xe";
			con = DriverManager.getConnection(
					connStr, "system", "1234");
			String sql="SELECT * FROM welcome_tbl";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			List<WelcomeVo> list = new ArrayList<>();
			while(rs.next()) {
				int id=rs.getInt("id");
				String msg = rs.getString("msg");
				System.out.println(id + ", " + msg);
				WelcomeVo vo = new WelcomeVo(id, msg);
				list.add(vo);
			}
			Random rnd = new Random();
			int idx = rnd.nextInt(list.size());
			req.setAttribute("abc", list.get(idx));
		}catch(Exception e){
			System.out.println("오라클 데이터를 가져오지 못했습니다.");
			e.printStackTrace();
		}finally {
			if(rs != null) try {rs.close();} catch(Exception e) {}
			if(pstmt != null) try {pstmt.close();} catch(Exception e) {}
			if(con != null) try {con.close();} catch(Exception e) {}
		}
		return "main";
	}
}
