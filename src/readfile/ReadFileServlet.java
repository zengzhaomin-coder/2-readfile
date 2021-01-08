package readfile;

import java.io.File; // 传统的表示文件的一个类
import java.io.IOException;
import java.nio.file.Files;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/read.html")
public class ReadFileServlet extends HttpServlet {
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String dirString = getServletContext().getRealPath("/wenben");

		File file = getRandomFileFromDirectory(dirString);
		String content = getFileContent(file);

		String html = "<html>" + "<head><meta charset=utf-8></head>"
				
				+ "<form action='/readfile/write'>"
				+ "  <input name='fileName' placeholder='file name'>"
				+ "  <input name='content' placeholder='content'>"
				+ "  <button>Submit</button>"
				+ "</form>"
				
				+ "<hr>"
				+ "<h3>" + file.getName() + "</h3>"
				 
				+ "<p><pre style='display:none'>" + content + "</pre></p>"
				+ "<button class='xx'>Click Me</button><button class='rf'>Refresh</button>"
				+ "<hr>"
				+ "<script>"
				+ "function xxx() { alert(document.querySelector('pre').innerText) }"
				+ "document.querySelector('.xx').onclick=xxx;"
				+ "document.querySelector('.rf').onclick = () => location.reload()" + "</script>" + "</html>";
		resp.getWriter().write(html);
	}

	File getRandomFileFromDirectory(String dirName) {
		// 1. 应该列出文件夹下面的所有文件，按照道理返回的是一个数组才对
		// 2. 从上面的数组里，随机获取一个元素。怎么去随机

		File dir = new File(dirName);
		File[] files = dir.listFiles();

		// return files[new Random().nextInt(files.length)];

		Random random = new Random();
		int index = random.nextInt(files.length);
		File rFile = files[index];
		return rFile;
	}

	String getFileContent(File file) throws IOException {
		// 从 JDK7 开始，提供了 Files 的一个静态类，封装了很多方便操作文件内容的方法
		// 由于 File 类出现早起设计不够合理，所以 JDK7 开始，推出了一个新的 Path 来替代 File
		byte[] bs = Files.readAllBytes(file.toPath());
		return new String(bs);
	}
}
