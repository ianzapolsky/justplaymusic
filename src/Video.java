/* 	Video represents a video and its data, including its unique URL id
	by Ian Zapolsky (6/14/13) */

import java.util.Scanner;

public class Video {
	private String title;
	private String id;
	private String time;
	private String views;
	private String user;
	
	public Video(String raw_html) {
		init(raw_html);	
	}

	private void init(String raw_html) {
		String[] data = raw_html.split("\"");
		for (int i = 0; i < data.length; i++) {
			if (data[i].contains("data-context-item-title="))
				title = data[i+1];
			else if (data[i].contains("data-context-item-id="))
				id = data[i+1];
			else if (data[i].contains("data-context-item-time="))
				time = data[i+1];
			else if (data[i].contains("data-context-item-views="))
				views = data[i+1];
			else if (data[i].contains("data-context-item-user="))
				user = data[i+1];
		}
	}

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String toString() {
		return title+": "+id;
	}
}
