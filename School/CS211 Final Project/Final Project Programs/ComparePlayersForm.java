import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ComparePlayersForm {

	private JFrame frmComparePlayers;
	private PlayerComparator pc;
	private TextReader tr;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ComparePlayersForm window = new ComparePlayersForm();
					window.frmComparePlayers.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ComparePlayersForm() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmComparePlayers = new JFrame();
		frmComparePlayers.setResizable(false);
		frmComparePlayers.setTitle("Compare Players");
		frmComparePlayers.setBackground(UIManager.getColor("Button.background"));
		frmComparePlayers.setForeground(Color.WHITE);
		frmComparePlayers.setName("");
		frmComparePlayers.setSize(new Dimension(801, 463));
		frmComparePlayers.setBounds(100, 100, 450, 300);
		frmComparePlayers.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmComparePlayers.getContentPane().setLayout(null);
		
		//Initializes player comparator and text reader
		tr = new TextReader();
		pc = new PlayerComparator();
		
		JLabel lblPlayerComparator = new JLabel("Player Comparator");
		lblPlayerComparator.setHorizontalAlignment(SwingConstants.CENTER);
		lblPlayerComparator.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 30));
		lblPlayerComparator.setBounds(10, 11, 414, 48);
		frmComparePlayers.getContentPane().add(lblPlayerComparator);
		
		JComboBox comboBoxPlayer1 = new JComboBox();
		comboBoxPlayer1.setModel(new DefaultComboBoxModel(addPlayerNames(comboBoxPlayer1)));
		comboBoxPlayer1.setBounds(92, 84, 192, 20);
		frmComparePlayers.getContentPane().add(comboBoxPlayer1);
		
		JComboBox comboBoxPlayer2 = new JComboBox();
		comboBoxPlayer2.setModel(new DefaultComboBoxModel(addPlayerNames(comboBoxPlayer2)));
		comboBoxPlayer2.setBounds(92, 115, 192, 20);
		frmComparePlayers.getContentPane().add(comboBoxPlayer2);
		
		JButton btnComparePlayers = new JButton("Compare Players");
		btnComparePlayers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Compares the two players
			}
		});
		btnComparePlayers.setBounds(294, 83, 140, 23);
		frmComparePlayers.getContentPane().add(btnComparePlayers);
		
		/*
		 * Average Points Per Game (PPG)
		 * Average Rebounds Per Game (RPG)
		 * Average Assists Per Game (APG)
		 * Average Field Goal Percentage (FGP)
		 * Average Free Throw Percentage (FTP)
		 * Average Player Efficiency Rating (PER)
		 * */
		
		JComboBox comboBoxStats = new JComboBox();
		comboBoxStats.setModel(new DefaultComboBoxModel(new String[] {"Average Points Per Game (PPG)", "Average Rebounds Per Game (RPG)", "Average Assists Per Game (APG)", "Average Field Goal Percentage (FGP)", "Average Free Throw Percentage (FTP)", "Average Player Efficiency Rating (PER)"}));
		comboBoxStats.setBounds(92, 146, 192, 20);
		frmComparePlayers.getContentPane().add(comboBoxStats);
		
		JLabel lblPlayer = new JLabel("Player 1");
		lblPlayer.setBounds(22, 87, 60, 14);
		frmComparePlayers.getContentPane().add(lblPlayer);
		
		JLabel lblPlayer_1 = new JLabel("Player 2");
		lblPlayer_1.setBounds(22, 118, 60, 14);
		frmComparePlayers.getContentPane().add(lblPlayer_1);
		
		JLabel lblStat = new JLabel("Stat");
		lblStat.setBounds(22, 149, 60, 14);
		frmComparePlayers.getContentPane().add(lblStat);
		
		JLabel lblTheBetterPlayer = new JLabel("The better player is");
		lblTheBetterPlayer.setVisible(false);
		lblTheBetterPlayer.setBounds(10, 187, 414, 45);
		frmComparePlayers.getContentPane().add(lblTheBetterPlayer);
	}
	
	private void playerComparator(String player1, String player2, String stat) {
		if(player1.equals(player2)) {
			
		}
	}
	
	private Object[] addPlayerNames(JComboBox comboBox) {
		ArrayList pNames = tr.getStat("PLAYER");
		Object[] names = new Object[pNames.size()];
		int count = 0;
		for(Object o : pNames) {
			names[count] = o;
			count++;
		}
		return names;
	}
}
