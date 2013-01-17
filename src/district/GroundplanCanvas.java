package district;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import districtobjects.Residence;
import districtobjects.WaterBody;


public class GroundplanCanvas extends Canvas {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2544621875608225004L;
	private static final int MARGINLEFT = 50, MARGINTOP = 50;
	private static final int SCALE = 6;
	private Groundplan plan = null;
	
	public GroundplanCanvas(Groundplan plan){
		this.plan = plan;
	}
	
	public void paint(Graphics g){
		setBackground(Color.white);
	    g.setColor(Color.DARK_GRAY);
	    g.drawRect(GroundplanCanvas.MARGINLEFT-1, GroundplanCanvas.MARGINTOP-1, (int)(Groundplan.WIDTH * SCALE) +2, (int)(Groundplan.HEIGHT * SCALE) +2);
	    g.drawRect(GroundplanCanvas.MARGINLEFT-2, GroundplanCanvas.MARGINTOP-2, (int)(Groundplan.WIDTH * SCALE) +4, (int)(Groundplan.HEIGHT * SCALE) +4);
	    this.tekenVeld(g);
	}
	
	// Deze methode tekent het hele veld
	private void tekenVeld(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		if(plan != null) {
			for(WaterBody waterbody : plan.getWaterBodies()){
				g2d.setColor(Color.BLUE);
				g2d.fillRect((int)(waterbody.getX()*SCALE)+GroundplanCanvas.MARGINLEFT,(int)(waterbody.getY()*SCALE)+GroundplanCanvas.MARGINTOP, (int)waterbody.getWidth()*SCALE, (int)waterbody.getHeight()*SCALE);
				g2d.setColor(Color.GRAY);
				g2d.drawRect((int)(waterbody.getX()*SCALE)+GroundplanCanvas.MARGINLEFT,(int)(waterbody.getY()*SCALE)+GroundplanCanvas.MARGINTOP, (int)waterbody.getWidth()*SCALE, (int)waterbody.getHeight()*SCALE);
			}
			for(Residence residence : plan.getResidences()){
				if(residence.getType().equals("Mansion")){
					g2d.setColor(Color.GREEN);
					g.fillRect((int)(residence.getX()*SCALE)+GroundplanCanvas.MARGINLEFT,(int)(residence.getY()*SCALE)+GroundplanCanvas.MARGINTOP, (int)residence.getWidth()*SCALE, (int)residence.getHeight()*SCALE);
					g.setColor(Color.GRAY);
					g.drawRect((int)(residence.getX()*SCALE)+GroundplanCanvas.MARGINLEFT,(int)(residence.getY()*SCALE)+GroundplanCanvas.MARGINTOP, (int)residence.getWidth()*SCALE, (int)residence.getHeight()*SCALE);
				}
			}
			for(Residence residence : plan.getResidences()){
				if(residence.getType().equals("Bungalow")){
					g.setColor(Color.MAGENTA);
					g.fillRect((int)(residence.getX()*SCALE)+GroundplanCanvas.MARGINLEFT,(int)(residence.getY()*SCALE)+GroundplanCanvas.MARGINTOP, (int)residence.getWidth()*SCALE, (int)residence.getHeight()*SCALE);
					g.setColor(Color.GRAY);
					g.drawRect((int)(residence.getX()*SCALE)+GroundplanCanvas.MARGINLEFT,(int)(residence.getY()*SCALE)+GroundplanCanvas.MARGINTOP, (int)residence.getWidth()*SCALE, (int)residence.getHeight()*SCALE);
				}
			}
			for(Residence residence : plan.getResidences()){
				if(residence.getType().equals("Cottage")){
					g.setColor(Color.ORANGE);
					g.fillRect((int)(residence.getX()*SCALE)+GroundplanCanvas.MARGINLEFT,(int)(residence.getY()*SCALE)+GroundplanCanvas.MARGINTOP, (int)residence.getWidth()*SCALE, (int)residence.getHeight()*SCALE);
					g.setColor(Color.GRAY);
					g.drawRect((int)(residence.getX()*SCALE)+GroundplanCanvas.MARGINLEFT,(int)(residence.getY()*SCALE)+GroundplanCanvas.MARGINTOP, (int)residence.getWidth()*SCALE, (int)residence.getHeight()*SCALE);
				}
			}
		}
	}
}
