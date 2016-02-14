import java.util.ArrayList;


public class TestClass{



    public static void main(String[] args){
        String ensemble = "";
        int noOfR = args[1];
        int noOfP = args[2];
        int noOfH = noOfR - noOfP;

        for (int i = 0; i < noOfP; i++){
            ensemble += "P";
        }

        for (int i = 0; i < noOfH; i++({
            ensemble += "H";
        }

        ArrayList list = ensemble.asList();

        for (int i = 0; i < list.length; i++){
            String chain = "";
            chain += list.get(i);
            for (int j = 0; j < list.length; j++){
                if (i != j){
                    chain += list.get(j);
                }
            }
            Process exec = Runtime.getRunTime().exec(new String[] {"java", "", chain});


        }

