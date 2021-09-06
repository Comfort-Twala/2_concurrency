import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
//model is separate from the view.

/**
 * Main App driver class
 */
public class WordApp {
//shared variables
	static int noWords=4;
	static int totalWords;

   	static int frameX=1000;
	static int frameY=600;
	static int yLimit=480;

	static WordDictionary dict = new WordDictionary(); //use default dictionary, to read from file eventually

	static WordRecord[] words;
	static volatile boolean done;  //must be volatile
	static 	Score score = new Score();

	static WordPanel w;
	static Timer missedTimer;
	static Clip inputAudio;
	static AudioInputStream inputAudioStream;
	

	
	/**
	 * Building the GUI for the program
	 * @param frameX
	 * @param frameY
	 * @param yLimit
	 */	
	public static void setupGUI(int frameX,int frameY,int yLimit) {
		// Frame init and dimensions
    	JFrame frame = new JFrame("WordGame"); 
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setSize(frameX, frameY);
		JPanel g = new JPanel();
		g.setLayout(new BoxLayout(g, BoxLayout.PAGE_AXIS)); 
		g.setSize(frameX,frameY);
    	
		w = new WordPanel(words,yLimit);
		w.setSize(frameX,yLimit+100);
		g.add(w); 
		
		JOptionPane.showMessageDialog(null, 
			"""
			Welcome to WordGame!
			
			Rules are simple. You Gotta Catch 'em all!
			- Catch all the words to win
			- Miss total amount of words to lose
			- Once word is caught it won't come up again

			Enjoy!
			"""
		);

		JButton startB = new JButton("Start");;
		JButton endB = new JButton("End");;
		
		JPanel txt = new JPanel();
		txt.setLayout(new BoxLayout(txt, BoxLayout.LINE_AXIS)); 
		JLabel caught =new JLabel("Caught: " + score.getCaught() + "    ");
		JLabel missed =new JLabel("Missed:" + score.getMissed()+ "    ");
		JLabel scr =new JLabel("Score:" + score.getScore()+ "    ");    
		txt.add(caught);
		txt.add(missed);
		txt.add(scr);
		
			
		final JTextField textEntry = new JTextField("",20);
		textEntry.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt) {
				String text = textEntry.getText();
				boolean used = false;
				for (WordRecord word: words){
					if (word.matchWord(text)){
						if (!(word.getWord().equals(" "))) {
							used = true;
						}
					}
				}
				if (used){
					try {
						inputAudioStream = AudioSystem.getAudioInputStream(new File("assets/caught.wav").getAbsoluteFile());
						inputAudio = AudioSystem.getClip();
						inputAudio.open(inputAudioStream);
					} catch (Exception e) {
						e.printStackTrace();
					}
					inputAudio.start();
					score.caughtWord(text.length());
					if (score.getCaught() == totalWords){
						try {
							inputAudioStream = AudioSystem.getAudioInputStream(new File("assets/gta-mission-passed.wav").getAbsoluteFile());
							inputAudio = AudioSystem.getClip();
							inputAudio.open(inputAudioStream);
						} catch (Exception err) {
							err.printStackTrace();
						}
						inputAudio.start();
						endB.doClick();
						JOptionPane.showMessageDialog(null, 
							"You WON !\nScore: " + score.getScore() + "\nCaught: " + score.getCaught() + "\nMissed: " + score.getMissed() + "\nTotal: " + score.getTotal()
						);
					}
					updateTxt();
				}
				textEntry.setText("");
				textEntry.requestFocus();
			}
			
			public void updateTxt() {
				caught.setText("Caught: " + score.getCaught() + "    ");;
				scr.setText("Score:" + score.getScore()+ "    ");
			}
		});
		
		
		txt.add(textEntry);
		txt.setMaximumSize( txt.getPreferredSize() );
		g.add(txt);
		
		JPanel b = new JPanel();
		b.setLayout(new BoxLayout(b, BoxLayout.LINE_AXIS)); 
		endB.setEnabled(false);
		
		// add the listener to the jbutton to handle the "pressed" event
		startB.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				SwingUtilities.invokeLater(w);
				SwingUtilities.invokeLater(new Runnable(){
					@Override
					public void run() {
						missedTimer = new Timer(300, new ActionListener(){
							public void actionPerformed(ActionEvent e){
								for (int i = 0; i < w.getDropped(); i++){
									score.missedWord();
									missed.setText("Missed:" + score.getMissed()+ "    ");
									try {
										inputAudioStream = AudioSystem.getAudioInputStream(new File("assets/missed.wav").getAbsoluteFile());
										inputAudio = AudioSystem.getClip();
										inputAudio.open(inputAudioStream);
									} catch (Exception err) {
										err.printStackTrace();
									}
									inputAudio.start();
								}
								w.setDropped(0);
								if (score.getMissed() == totalWords){
									try {
										inputAudioStream = AudioSystem.getAudioInputStream(new File("assets/gta-death.wav").getAbsoluteFile());
										inputAudio = AudioSystem.getClip();
										inputAudio.open(inputAudioStream);
									} catch (Exception err) {
										err.printStackTrace();
									}
									inputAudio.start();
									endB.doClick();
									JOptionPane.showMessageDialog(null, 
										"You Lost !\nScore: " + score.getScore() + "\nCaught: " + score.getCaught() + "\nMissed: " + score.getMissed() + "\nTotal: " + score.getTotal()
									);
								}
							}
						});
						missedTimer.start();
					}				
				});
				startB.setEnabled(false);
				endB.setEnabled(true);
				startB.setText("Start");
				textEntry.requestFocus();  //return focus to the text entry field
				caught.setText("Caught: " + score.getCaught() + "    ");;
				missed.setText("Missed:" + score.getMissed()+ "    ");
				scr.setText("Score:" + score.getScore()+ "    ");
			}
		});
		
		// add the listener to the jbutton to handle the "pressed" event
		endB.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				startB.setText("Restart");
				startB.setEnabled(true);
				endB.setEnabled(false);
				dict.reset();
				for (WordRecord word: words){
					word.resetWord();
				}
				score.resetScore();
				missedTimer.stop();
				w.stop();
				w.repaint();
			}
		});
		
		JButton quitB = new JButton("Quit");;
		quitB.setBackground(Color.RED);
		quitB.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		b.add(startB);
		b.add(endB);
		b.add(quitB);
		
		g.add(b);
   	
		frame.setLocationRelativeTo(null);  // Center window on screen.
		frame.add(g); //add contents to window
		frame.setContentPane(g);     
		//frame.pack();  // don't do this - packs it into small space
		frame.setVisible(true);
	}


	/**
	 * Method to load words from file to a dictionary
	 * @param filename
	 * @return
	 */
	public static String[] getDictFromFile(String filename) {
		String [] dictStr = null;
		try {
			Scanner dictReader = new Scanner(new FileInputStream(filename));
			int dictLength = dictReader.nextInt();
			// System.out.println("read '" + dictLength+"'");

			dictStr=new String[dictLength+1];
			for (int i=0;i<dictLength;i++) {
				dictStr[i]=new String(dictReader.next());
				// System.out.println(i+ " read '" + dictStr[i]+"'"); //for checking
			}
			dictStr[dictLength] = new String("  ");
			dictReader.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Problem reading file " + filename + " default dictionary will be used");
	    }
		return dictStr;
	}

	
	/**
	 * Main driver Method to run the game
	 * @param args
	 */
	public static void main(String[] args) {
		//deal with command line arguments
		totalWords=Integer.parseInt(args[0]);  //total words to fall
		noWords=Integer.parseInt(args[1]); // total words falling at any point
		assert(totalWords>=noWords); // this could be done more neatly
		String[] tmpDict=getDictFromFile("dictionary/" + args[2]); //file of words
		if (tmpDict!=null)
			dict= new WordDictionary(tmpDict);
			
		WordRecord.dict=dict; //set the class dictionary for the words.
			
		words = new WordRecord[noWords];  //shared array of current words
			
		//[snip]
		
		setupGUI(frameX, frameY, yLimit);  
		
		int x_inc=(int)frameX/noWords;
		//initialize shared array of current words
		
		for (int i=0; i<noWords; i++){
			words[i] = new WordRecord(dict.getNewWord(),i*x_inc,yLimit);
		}

		for (WordRecord word : words) {
			System.out.println(word.getWord() + ":" + word.getWord().length());
		}

	}
}