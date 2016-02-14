package proteinhp;



import java.awt.EventQueue;

import javax.swing.JFrame;



public class TimerClass extends JFrame{



    public TimerClass(){

        initUI();

    }



    private void initUI(){

        add(new Plot());



        //setResizable(false);

        pack();



        setTitle("H-P structure");

        //setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }



    public static void main(String[] args){



        EventQueue.invokeLater(new Runnable(){

            public void run(){

                JFrame ex = new TimerClass();

                ex.setSize(1000, 1000);

                ex.setVisible(true);

            }

        });

    }

}
