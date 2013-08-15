import java.awt.Desktop;
import java.net.*;
import java.util.ArrayList;

public class Test {

	public static void main(String[] args) {
		try {

			YoutubeCrawler x = new YoutubeCrawler();
			ArrayList<Video> test = x.query("KRS ONE outta here");
			
			for (Video v : test)
				System.out.println(v);

			String URL = "http://www.youtube.com/watch?v="+test.get(0).getId();
			java.awt.Desktop.getDesktop().browse(java.net.URI.create(URL));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
		
