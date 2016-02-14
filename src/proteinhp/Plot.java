package proteinhp;


import java.awt.Color;

import java.awt.Dimension;

import java.awt.Graphics;

import java.awt.Image;

import java.awt.Toolkit;

import java.util.Timer;

import java.util.TimerTask;

import javax.swing.ImageIcon;

import javax.swing.JPanel;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;





import java.awt.geom.*; 

import javax.swing.*;

import java.awt.*;



public class Plot extends JPanel implements ActionListener{



    public final int width = 100;

    public final int height = 100;



    private Timer timer;

    //private int x, y;

    private int initialDelay = 20;

    private int periodInterval = 05;



    protected JLabel energy;

    

    private Chain chain;

    private Chain.ChainIterator itr;

    private MonteCarlo mntCarlo;

    private final float[] dash1 = {2f, 0f, 2f};

    private int iteration = 0;





    protected Chain preferredChain;

    protected Double lowestEnergy;

    

    public Plot(){

        initPlot();

        chain = new Chain("HHPHHPHH");

        itr = chain.new ChainIterator();

        int x = 0;

		int y = 0;

		while(!(itr.isEnd())){

			Residue res = itr.getResidue();

			res.setCoordinates(x++, y);

			itr.next();

		}

		

    }



    public void paintComponent(Graphics g){

        super.paintComponent(g);

        draw(g);

    }



    private void draw(Graphics g){

        itr.reset();



        energy.setFont(new Font("Serif", Font.PLAIN, 25));

        energy.setText("Energy is " + EnergyCalculator.calculateEnergy(chain));

        energy.setOpaque(true);

        

        

        Graphics2D g2d = (Graphics2D) g;

        

        while(!(itr.isEnd())){

            Residue res = itr.getResidue();

            g.setColor(Color.RED);

            if (res.getType() == 'H')

                g.setColor(Color.BLUE);

            g.fillOval(res.getX() * 30 + 400, res.getY() * 30 + 400, 7, 7);

            if(!(res.getNextResidue() == null)){

                Residue res2 = res.getNextResidue();

                //g2d.setStroke(bs1);

                g2d.setColor(Color.BLACK);

                g2d.draw(new Line2D.Float(res2.getX()*30 + 400, res2.getY()*30 + 400, res.getX()*30 + 400, res.getY()*30 + 400));

            } 

            itr.next();

        }

    

    }



    public void actionPerformed(ActionEvent e){

        repaint();

    }

    public void initPlot(){

        //setBackgound(Color.White);

        setPreferredSize(new Dimension(width, height));

        setDoubleBuffered(true);

        energy = new JLabel("", JLabel.CENTER);

        add(energy);



        timer = new Timer();

        timer.scheduleAtFixedRate(new ScheduleTask(), initialDelay, periodInterval);

    }



    private class ScheduleTask extends TimerTask{

        public void run(){

            if (iteration < 2000){

                mntCarlo = new MonteCarlo(Plot.this.chain, 10);

                Plot.this.chain = mntCarlo.run();

                iteration++;

            }

            repaint();

               

        }

    }

}