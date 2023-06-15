import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Dimension;
import javax.swing.JLabel;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.awt.Component;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AnalyzePlayerForm {

	private JFrame frmAnalyzeTeam;
	private PlayerEvaluator pe;
	private TextReader tr;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AnalyzePlayerForm window = new AnalyzePlayerForm();
					window.frmAnalyzeTeam.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws FileNotFoundException 
	 */
	public AnalyzePlayerForm() throws FileNotFoundException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws FileNotFoundException 
	 */
	private void initialize() throws FileNotFoundException {
		frmAnalyzeTeam = new JFrame();
		frmAnalyzeTeam.getContentPane().setFont(new Font("Microsoft Sans Serif", Font.BOLD, 40));
		frmAnalyzeTeam.setSize(new Dimension(816, 489));
		frmAnalyzeTeam.setTitle("Analyze Team");
		frmAnalyzeTeam.setBounds(100, 100, 450, 300);
		frmAnalyzeTeam.setSize(816, 489);
		frmAnalyzeTeam.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAnalyzeTeam.getContentPane().setLayout(null);
		
		//Initializes the player evaluator and the text reader
		pe = new PlayerEvaluator();
		tr = new TextReader();
		
		JLabel lblNewLabel = new JLabel("Player Analyzer");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblNewLabel.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 40));
		lblNewLabel.setBounds(10, 11, 780, 50);
		frmAnalyzeTeam.getContentPane().add(lblNewLabel);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(addPlayerNames(comboBox)));
		//comboBox.setModel(new DefaultComboBoxModel(new String[] {"NBA Team 1", "NBA Team 2", "NBA Team 3", "NBA Team 4", "NBA Team 5"}));
		comboBox.setSize(new Dimension(180, 20));
		comboBox.setEditable(true);
		comboBox.setName("comboBoxNBATeams1");
		comboBox.setMaximumRowCount(5);
		comboBox.setBounds(69, 103, 170, 20);
		frmAnalyzeTeam.getContentPane().add(comboBox);
		
		JLabel lblAveragePPG = new JLabel("Average Points Per Game (APPG): ");
		lblAveragePPG.setBounds(69, 165, 263, 14);
		frmAnalyzeTeam.getContentPane().add(lblAveragePPG);
		
		JLabel lblAverageRPG = new JLabel("Average Rebounds Per Game (ARPG): ");
		lblAverageRPG.setBounds(69, 223, 263, 14);
		frmAnalyzeTeam.getContentPane().add(lblAverageRPG);
		
		JLabel lblAverageAPG = new JLabel("Average Assists Per Game (AAPG): ");
		lblAverageAPG.setBounds(69, 283, 263, 14);
		frmAnalyzeTeam.getContentPane().add(lblAverageAPG);
		
		JLabel lblAverageFGP = new JLabel("Average Field Goal Percentage (AFGP): ");
		lblAverageFGP.setBounds(490, 165, 300, 14);
		frmAnalyzeTeam.getContentPane().add(lblAverageFGP);
		
		JLabel lblAverageFTP = new JLabel("Average Free Throw Percentage (AFTP): ");
		lblAverageFTP.setBounds(490, 223, 300, 14);
		frmAnalyzeTeam.getContentPane().add(lblAverageFTP);
		
		JLabel lblAveragePER = new JLabel("Average Player Efficiency Rating (APER): ");
		lblAveragePER.setBounds(490, 283, 300, 14);
		frmAnalyzeTeam.getContentPane().add(lblAveragePER);
		
		JLabel lblRank = new JLabel("Rank: ");
		lblRank.setBounds(490, 103, 76, 14);
		frmAnalyzeTeam.getContentPane().add(lblRank);
		
		JButton btnSeeTeamStats = new JButton("See Player Stats");
		btnSeeTeamStats.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setPlayerData((String)comboBox.getSelectedItem(), lblRank, lblAveragePPG, lblAverageRPG, lblAverageAPG, lblAverageFGP, lblAverageFTP, lblAveragePER);
			}
		});
		btnSeeTeamStats.setBounds(298, 102, 146, 23);
		frmAnalyzeTeam.getContentPane().add(btnSeeTeamStats);
		

		
	}
	/*
	 * Average Points Per Game (PPG)
	 * Average Rebounds Per Game (RPG)
	 * Average Assists Per Game (APG)
	 * Average Field Goal Percentage (FGP)
	 * Average Free Throw Percentage (FTP)
	 * Average Player Efficiency Rating (PER)
	 * */
	
	private void setPlayerData(String player,JLabel lblRank, JLabel lblAveragePPG, JLabel lblAverageRPG, JLabel lblAverageAPG, JLabel lblAverageFGP, JLabel lblAverageFTP, JLabel lblAveragePER) {
		String playerRank = tr.getPlayerStat(player, "RANK");
		String avgPPG = tr.getPlayerStat(player, "PPG");
		String avgRPG = tr.getPlayerStat(player, "RPG");
		String avgAPG = tr.getPlayerStat(player, "APG");
		String avgFGP = tr.getPlayerStat(player, "FGP");
		String avgFTP = tr.getPlayerStat(player, "FTP");
		String avgPER = tr.getPlayerStat(player, "PER");
		
		
		lblRank.setText("Rank: "+playerRank);
		lblAveragePPG.setText("Average Points Per Game (APPG): "+avgPPG);
		lblAverageRPG.setText("Average Rebounds Per Game (ARPG): "+avgRPG);
		lblAverageAPG.setText("Average Assists Per Game (AAPG): "+avgAPG);
		lblAverageFGP.setText("Average Field Goal Percentage (AFGP): "+avgFGP);
		lblAverageFTP.setText("Average Free Throw Percentage (AFTP): "+avgFTP);
		lblAveragePER.setText("Average Player Efficiency Rating (APER): "+avgPER);
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






