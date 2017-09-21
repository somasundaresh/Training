import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;

/**
 * Servlet implementation class Hello
 */
@WebServlet("/Hello")
public class Hello extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String url = "jdbc:mysql://192.168.16.109:3306/batch2";
	String uname = "sabose";
	String upass = "sabose";
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String name = request.getParameter("p1");
		String age = request.getParameter("p2");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, uname, upass);
			if (con != null) {
				System.out.println("Connected");
			}
			ps = con.prepareStatement("insert into somu values(?,?)");
			ps.setString(1, name);
			ps.setString(2, age);
			int i = ps.executeUpdate();
			if (i > 0)
				out.print("you are successfully registered...");
			else
				out.print("not register");
			ps = con.prepareStatement("select * from  somu");
			out.print("<table width=50% border=1>");
			out.print("<caption>Result:</caption>");
			ResultSet rs = ps.executeQuery();
			/* Printing column names */
			ResultSetMetaData rsmd = rs.getMetaData();
			int total = rsmd.getColumnCount();
			out.print("<tr>");
			for (i = 1; i <= total; i++) {
				out.print("<th>" + rsmd.getColumnName(i) + "</th>");
			}
			out.print("</tr>");
			/* Printing result */
			while (rs.next()) {
				out.print("<tr><td>" + rs.getString(1) + "</td><td>" + rs.getString(2) + "</td></tr>");
			}
			out.print("</table>");

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (con != null) {
			try {
				con.close();
			} catch (Exception e) {
				System.out.println("connection exception");
				e.printStackTrace();
			}
		}
	}
}
