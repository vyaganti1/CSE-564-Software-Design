import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class MessageDisplayer extends JPanel {
	private static MessageDisplayer messageDisplayer;
	private JLabel msgLabel;
	
	private MessageDisplayer() {
		msgLabel = new JLabel("Let's play for a while.");
		msgLabel.setForeground(Color.GRAY);
		msgLabel.setFont(new Font("Serif", Font.BOLD, 30));
		msgLabel.setVerticalAlignment(JLabel.CENTER);
		add(msgLabel);
	}
	
	public static MessageDisplayer getInstance() {
		if (messageDisplayer == null) {
			messageDisplayer =  new MessageDisplayer();
		}
		
		return messageDisplayer;
	}
		
	public void setMessage(String msg) {
		msgLabel.setText(msg);
	}

	public void setMessageColor(Color color) {
		msgLabel.setForeground(color);
	}
}
