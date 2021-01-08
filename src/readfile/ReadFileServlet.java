package readfile;

import java.io.File; // ��ͳ�ı�ʾ�ļ���һ����
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
		// 1. Ӧ���г��ļ�������������ļ������յ����ص���һ������Ŷ�
		// 2. �����������������ȡһ��Ԫ�ء���ôȥ���

		File dir = new File(dirName);
		File[] files = dir.listFiles();

		// return files[new Random().nextInt(files.length)];

		Random random = new Random();
		int index = random.nextInt(files.length);
		File rFile = files[index];
		return rFile;
	}

	String getFileContent(File file) throws IOException {
		// �� JDK7 ��ʼ���ṩ�� Files ��һ����̬�࣬��װ�˺ܶ෽������ļ����ݵķ���
		// ���� File �����������Ʋ����������� JDK7 ��ʼ���Ƴ���һ���µ� Path ����� File
		byte[] bs = Files.readAllBytes(file.toPath());
		return new String(bs);
	}
}
