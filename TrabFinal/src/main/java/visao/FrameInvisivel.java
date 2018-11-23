package visao;

import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.jtattoo.plaf.acryl.AcrylLookAndFeel;

public class FrameInvisivel extends JFrame {

	private static final long serialVersionUID = 1L;

	public FrameInvisivel() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);

		try
		{
			Properties props = new Properties();
			props.put("logoString", "");
			AcrylLookAndFeel.setCurrentTheme(props);
			
			String lookAndFeel = "AcrylLookAndFeel";
			
			if (lookAndFeel.equals("WindowsClassicLookAndFeel"))
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
			else if (lookAndFeel.equals("SmartLookAndFeel"))
				UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
			else if (lookAndFeel.equals("AcrylLookAndFeel"))
				UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
			else if (lookAndFeel.equals("AeroLookAndFeel"))
				UIManager.setLookAndFeel("com.jtattoo.plaf.aero.AeroLookAndFeel");
			else if (lookAndFeel.equals("AluminiumLookAndFeel"))
				UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
			else if (lookAndFeel.equals("BernsteinLookAndFeel"))
				UIManager.setLookAndFeel("com.jtattoo.plaf.bernstein.BernsteinLookAndFeel");
			else if (lookAndFeel.equals("FastLookAndFeel"))
				UIManager.setLookAndFeel("com.jtattoo.plaf.fast.FastLookAndFeel");
			else if (lookAndFeel.equals("GraphiteLookAndFeel"))
				UIManager.setLookAndFeel("com.jtattoo.plaf.graphite.GraphiteLookAndFeel");
			else if (lookAndFeel.equals("HiFiLookAndFeel"))
				UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
			else if (lookAndFeel.equals("LunaLookAndFeel"))
				UIManager.setLookAndFeel("com.jtattoo.plaf.luna.LunaLookAndFeel");
			else if (lookAndFeel.equals("McWinLookAndFeel"))
				UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
			else if (lookAndFeel.equals("MintLookAndFeel"))
		        UIManager.setLookAndFeel("com.jtattoo.plaf.mint.MintLookAndFeel");
			else if (lookAndFeel.equals("NoireLookAndFeel"))
		        UIManager.setLookAndFeel("com.jtattoo.plaf.noire.NoireLookAndFeel");
			else 
		        UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
		}
		catch(ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
		
		new FramePrincipal().setVisible(true);
	}
}
