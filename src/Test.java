import java.awt.Desktop;
import java.net.*;
import java.util.ArrayList;
import javax.swing.*;

public class Test {

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); 

			GUI main = new GUI();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
		
