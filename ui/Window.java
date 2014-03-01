package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

import main.Main;

/**A custom window that can display the world along with other content in a sidebar**/
public class Window extends javax.swing.JFrame {
	private static final long serialVersionUID = 1L;
	private JLabel generation = new JLabel("Step: " + NumberFormat.getNumberInstance(Locale.US).format(Main.step));
	private JLabel critters = new JLabel("Critters: 0");
	private JSplitPane contentPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	private JTextArea description = new JTextArea("This simulator was created by Evan Shimoniak with Java 6");
	//JPanel that displays world
	private RenderPane renderPane = new RenderPane();
	private JButton pause = new JButton("Pause");
	private Legend legend = new Legend();
	//Sidebar
	private JPanel statusPane = new SideBar();
	
	public Window() {
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setTitle("Lamarck vs. Darwin");
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Main.exit();
			}
		});
		
		
		//Add a custom content pane to the JFrame
		this.add(contentPane);
			contentPane.add(renderPane);
			contentPane.add(statusPane);
		
		
		this.pack();
		//Center display in screen
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public void refresh() {
		//Returns generation # with commas for thousands
		generation.setText("Step: " + NumberFormat.getNumberInstance(Locale.US).format(Main.step));
		critters.setText("Critters: " + NumberFormat.getNumberInstance(Locale.US).format(Main.critters.size()));
		//Redraw canvas
		renderPane.repaint();
		legend.repaint();
	}
	
	private class SideBar extends JPanel {
		private static final long serialVersionUID = 1L;

		public SideBar() {
			this.setPreferredSize(new Dimension(160, 480));
			this.setLayout(new GridLayout(0, 1));
			this.add(generation);
			this.add(pause);
				pause.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						Main.paused = !Main.paused;
						if (Main.paused == true) {
							pause.setText("Un-Pause");
						} else {
							pause.setText("Pause");
						}
					}
				});
			this.add(description);
				description.setPreferredSize(new Dimension(154, 160));
				description.setEditable(false);
				//Turn on word wrap
				description.setWrapStyleWord(true);
				description.setLineWrap(true);
				//Sets font to match default JLabel font
				description.setFont(new Font("Arial", Font.BOLD, 12));
				//Sets background color to default gray
				description.setBackground(new Color(238, 238, 238));
				//Make text non-highlightable
				description.setHighlighter(null);
			this.add(critters);
			this.add(legend);
		}
	}
}