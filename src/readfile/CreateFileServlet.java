package readfile;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/write")
public class CreateFileServlet extends HttpServlet {
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fileName = req.getParameter("fileName");
		String content = req.getParameter("content");
		
		String root = "e:/temp/";

		// 将文件保存为 fileName，内容为 content
		Files.write(Paths.get(root + fileName), content.getBytes());
		
		// 设定使用 GBK 的方式进行转码
		resp.setCharacterEncoding("GBK");
		
		PrintWriter writer = resp.getWriter();
		writer.write("<html>");
		writer.write("<div>保存成功！</div>");
		writer.write("<div><a href='/readfile/read.html'>return back</a></div>");
		writer.write("</html>");
	}
}




