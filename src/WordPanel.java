import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Class where the words are displayed and contained.
 */
public class WordPanel extends JPanel implements Runnable {
	public static volatile boolean done;
	private WordRecord[] words;
	private int noWords;
	private int maxY;
	private Timer timer;
	private int dropped;

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
	
	public int getDropped() {
		return dropped;
	}

	public void setDropped(int dropped) {
		this.dropped = dropped;
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
	@Override
	public void run() {
		timer = new Timer(300, animation);
		timer.start();
	}

	public void stop() {
		timer.stop();
	}

	/**
	 * ActionListener Method for the Word panel class
	 */
	ActionListener animation = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			for (WordRecord word : words) {
				word.drop(word.getSpeed());
				if (word.dropped()){
					if (!(word.getWord().equals(""))) {
						dropped++;
					}
					word.resetWord();	
				}
			}
			repaint();
		}
	};

}