import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class GroundplanFrame extends Frame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -360885512080963508L;
	private Groundplan plan = null;
	private GroundplanCanvas groundplanCanvas = null;

	public GroundplanFrame(){
	 	// frame instellingen:
		setTitle("Heuristieken 2012 - Amstelhaege!");
		setLayout(new BorderLayout());
		setSize(1100,850);
		setVisible(true);
		
		this.addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				System.exit(0);
			}
		});
		
		this.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				super.keyPressed(e);
				if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
					System.exit(0);
				}
			}
			
		});
	}
	
	public void repaint(){
		super.repaint();
	}

	public void setPlan(Groundplan plan) {
		this.plan = plan;
		if(groundplanCanvas != null){
			remove(groundplanCanvas);
		}
		groundplanCanvas = new GroundplanCanvas(plan);
		add(this.groundplanCanvas, BorderLayout.CENTER);
		
		this.invalidate();
		this.validate();
	}
}