import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

/**
 * The executable class for the program.
 * 
 * Credit to Zach Tomaszewski as the original author of portions of this code.
 * 
 * @author Jack Lam
 * @author Branden Ogata
 *
 */

public class AgentRunner implements ActionListener, ChangeListener {
	
	  private static Student student = new Student();
	
	  private Room room;
	  private Room.GUI roomGUI;
	  private JFrame window;
	  private JPanel leftPane;
	  private JPanel rightPane;
	  //private JTextArea output;
	  private JSlider speedSlider;
	  private int animationDelay;
	  private JButton goButton;
	  private JMenuItem load;
	  private JMenuItem quit;
	  private File roomFile;
	public static void main(String[] args) {

		// start up GUI
		new AgentRunner();

	}

	/**
	 * Creates the GUI for this application.
	 * 
	 */
	
	 public AgentRunner() {
		    //create a window
		    //this.window = new JFrame("GUI Vroomba Runner: " + vroomba.getClass().getName());
		    this.window = new JFrame("GUI Agent Runner");
		    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		    //add a temporary LEFT PANE, to be replaced once a room is loaded
		    this.leftPane = new JPanel();
		    JLabel loadInstruction = new JLabel("Load a room file to start.");
		    loadInstruction.setBorder(new EmptyBorder(50, 0, 0, 0)); //top padding
		    leftPane.add(loadInstruction);
		    window.add(leftPane, BorderLayout.CENTER);

		    //now built a RIGHT PANE to hold the output text area, slider, and button
		    this.rightPane = new JPanel();
		    rightPane.setLayout(new BoxLayout(rightPane, BoxLayout.PAGE_AXIS));

		    //output: for displaying move output
		   // this.output = new JTextArea("MOVES:\n", 10, 25);
		  //  output.setEditable(false);
		  //  output.setTabSize(2);
		    //wrap it in a scroll pane as adding it so it'll scroll
		   // rightPane.add(new JScrollPane(output,
		    //                              JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
		    //                              JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));

		    //slider
		    this.speedSlider = new JSlider(0, 1000, 200);  //low, high, initial
		    speedSlider.setBorder(new TitledBorder(speedSlider.getBorder(), "Animation Delay"));
		    speedSlider.addChangeListener(this);
		    this.animationDelay = speedSlider.getValue();
		    rightPane.add(speedSlider);

		    //goButton
		    this.goButton = new JButton("Go");
		    goButton.setEnabled(false);  //can't clean until room loaded
		    goButton.addActionListener(this);

		    //a little panel to allow padding and aligning of goButton
		    JPanel buttonHolder = new JPanel();
		    buttonHolder.setLayout(new BoxLayout(buttonHolder, BoxLayout.LINE_AXIS));
		    buttonHolder.add(Box.createHorizontalGlue()); //force center align of button
		    buttonHolder.add(goButton);
		    buttonHolder.add(Box.createHorizontalGlue()); //other half of center align
		    EmptyBorder margin = new EmptyBorder(8, 12, 8, 12);  //top, left, bottom, right
		    buttonHolder.setBorder(margin);
		    rightPane.add(buttonHolder);

		    window.add(rightPane, BorderLayout.EAST);
		    //done with panes

		    //add a MENU
		    JMenuBar menuBar = new JMenuBar();
		    JMenu file = new JMenu("File");
		    file.setMnemonic(KeyEvent.VK_F);

		    this.load = new JMenuItem("Load Room...");
		    load.setMnemonic(KeyEvent.VK_L);
		    load.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
		    load.addActionListener(this);

		    this.quit = new JMenuItem("Quit");
		    quit.setMnemonic(KeyEvent.VK_Q);
		    quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
		    quit.addActionListener(this);

		    file.add(load);
		    file.add(quit);
		    menuBar.add(file);
		    window.setJMenuBar(menuBar);

		    //pack and display the entire constructed window
		    window.setPreferredSize(new Dimension(700, 450));
		    window.pack();
		    window.setLocationRelativeTo(null);  //center new window on screen
		    window.setVisible(true);
		  }
	
	 /**
	  * Handles any changes to the speed slider.
	  * 
	  * @param e    The ChangeEvent that recorded the change.
	  * 
	  */
	 
	public void stateChanged(ChangeEvent e) {
		 if (e.getSource() == this.speedSlider) {
		      this.animationDelay = this.speedSlider.getValue();
		    }		
	}
	
	/**
	 * Handles a variety of different actions such as menu selection and button clicking.
	 * 
	 * @param ae    The ActionEvent that recorded the action.
	 * 
	 */

	public void actionPerformed(ActionEvent ae) {
	    //determine which component generate this action, and respond accordingly
	    if (ae.getSource() == this.quit) {
	      System.exit(0); //normal termination

	    }else if (ae.getSource() == this.load) {
	      this.loadNewRoom();

	    }else if (ae.getSource() == this.goButton) {
	      if (this.goButton.getText().equals("Again!")) {
	        //repeating current room, so need to refresh it first
	        this.refreshRoom();
	      }
	      //disable goButton and loading of new map until done cleaning
	      this.goButton.setEnabled(false);
	      this.load.setEnabled(false);
	      //use anonymous inner class to create a new thread
	      //Necessary so animation doesn't block action processing
	      Thread t = new Thread() {
	                    public void run() { clean(); }
	                 };
	      t.start();
	    }
	}
	
	/**
	 * Cleans the Room shown in the GUI.
	 * 
	 */
	
	 public void clean() {
		    if (this.room == null || this.roomGUI == null || this.room.getResult() != null) {
		      //no room ever loaded, or last room not reset, so no room to clean!
		    }else {
		      //clean
		      this.student.reset();
		      while (room.moveAgent()) {
		        roomGUI.update();  //update the display after moving
		        try {
		          if (this.animationDelay > 0) {
		            Thread.sleep(this.animationDelay);
		          }
		          //update output, scrolling down the text if necessary
		        //  this.output.append(room.getStatus());
		        //  this.output.setCaretPosition(this.output.getText().length());
		        }catch (InterruptedException ie) {
		          //shouldn't ever happen.
		          //(If it ever does, probably want to now about it though, so tell console)
		          System.out.println("Board sleep interrupted! Continuing to animate though...");
		        }
		      }
		    //  this.output.append(room.getStatus(true) + "\n");  //print last move
		     // this.output.append(room.getStatus());             //result
		     // this.output.setCaretPosition(this.output.getText().length()); //force scroll
		    }
		    this.goButton.setText("Again!");
		    this.goButton.setEnabled(true);  //enable cleaning and loading again
		    this.load.setEnabled(true);
		  }
	
	 /**
	  * Loads a new Room.
	  * 
	  */
	 
	public void loadNewRoom() {
	    //try opening the rooms subdirectory, if it exists; otherwise, current dir
	    File startingDir = new File("rooms");
	    startingDir = (startingDir.exists() && startingDir.isDirectory()) ?
	                  startingDir :
	                  new File(".");
	    JFileChooser chooser = new JFileChooser(startingDir);

	    int result = chooser.showOpenDialog(this.window);
	    if (result == JFileChooser.APPROVE_OPTION) {
	      //user successfully chose a file
	      this.roomFile = chooser.getSelectedFile();
	      this.refreshRoom();
	      this.goButton.setText("Go");
	    }
	  }
	
	/**
	 * Refreshes the Room.
	 * 
	 */
	
	protected void refreshRoom() {
	   // try {
	      //create/load a new room from file
	    //  this.room = new Room(this.roomFile.getCanonicalPath(), this.vroomba);
	      this.room = new Room(this.student);
	      this.roomGUI = this.room.new GUI();

	      //clear left pane and load with new room
	      this.leftPane.removeAll();        //remove load label or room
	    //  this.output.setText("MOVES:\n");  //reset output
	      /*
	       * FIX: setting the roomGUI's preferredSize to that of its parent pane
	       * works well on initial load, but still doesn't handle window resizes.
	       */
	      roomGUI.setPreferredSize(leftPane.getSize());
	      leftPane.add(roomGUI);
	      this.window.validate();
	      this.goButton.setEnabled(true);
	  }
	
}
