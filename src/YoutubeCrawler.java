/*	YoutubeCrawler fetches raw HTML data from www.youtube.com
	by Ian Zapolsky (8/14/13) */

import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.ArrayList;

public class YoutubeCrawler {

	private Scanner in;

	public YoutubeCrawler() {}

	public ArrayList<Video> query(String query) throws IOException {
		ArrayList<Video> results = new ArrayList<Video>();
		
		String target = "http://www.youtube.com/results?search_query="+URLEncoder.encode(query, "UTF-8");
		URL u = new URL(target);
		in = new Scanner(new InputStreamReader(u.openStream()));
		String raw_html;

		for (int i = 0; i < 19; i++) {
			in.findWithinHorizon("context-data-item\"", 0);
			raw_html = in.nextLine()+"\n";
			raw_html += in.nextLine()+"\n";
			results.add(new Video(raw_html));
		}
	
		return results;
	}
}
			
