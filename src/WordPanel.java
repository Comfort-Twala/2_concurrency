import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Class where the words are displayed and contained.
 */
public class WordPanel extends JPanel implements Runnable, ActionListener {
		public static volatile boolean done;
		private WordRecord[] words;
		private int noWords;
		private int maxY;

		/**
		 * Method that paints Components onto the GUI
		 */
		public void paintComponent(Graphics g) {
		    int width = getWidth();
		    int height = getHeight();
		    g.clearRect(0,0,width,height);
		    g.setColor(Color.red);
		    g.fillRect(0,maxY-10,width,height);

		    g.setColor(Color.black);
		    g.setFont(new Font("Helvetica", Font.PLAIN, 26));
		   //draw the words
		   //animation must be added 
		    for (int i=0;i<noWords;i++){	    	
		    	g.drawString(words[i].getWord(),words[i].getX(),words[i].getY());	
		    }
		   
		  }
		
		/**
		 * Constructor initialising the panel
		 * @param words
		 * @param maxY
		 */
		WordPanel(WordRecord[] words, int maxY) {
			this.words=words;
			noWords = words.length;
			done=false;
			this.maxY=maxY;		
		}
		
		/**
		 * Method to start up the Wordpanel
		 */
		public void run() {
			Timer timer = new Timer(150, this);
			timer.start();
		}

		/**
		 * ActionListener Method for the Word panel class
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			for (WordRecord word : words) {
				word.drop(word.getSpeed());
			}
			repaint();
		}

	}


