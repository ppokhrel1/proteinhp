import sys
import itertools
import os.path, subprocess
from subprocess import STDOUT, PIPE
#for email
from email.utils import *
from email.MIMEMultipart import MIMEMultipart
from email.MIMEBase import MIMEBase
from email.mime.text import MIMEText
from email import Encoders

#for multithreading
import threading
from time import sleep


class Runner:
    
    def __init__(self, noOfResidues, noOfP):
        self.noOfResidues = noOfResidues
        self.noOfP = noOfP
        
        self.thread_list = []
        self.outputs = [] 

    def getAllChainPossibilities(self):
        noOfH = self.noOfResidues - self.noOfP
        ensemble = ""

        #confirm that noOfP < noOfResidues
        if self.noOfP > self.noOfResidues:
            raise ValueError('No of P is greater than number of residues')
        for i in range(self.noOfP):
            ensemble += "P"
        for i in range(noOfH):
            ensemble += "H"
        #print ensemble        
        temp =  itertools.permutations(ensemble)
        
        print "All possible residue combinations generated"

        return temp

    def unique(self, obj):
        temp = set()
        for p in obj:
            if p in temp:
                continue
            temp.add(p)
            yield p

    def __compile(self, java_file):
        subprocess.check_call(['javac', java_file])

    def __execute(self, java_file, _input): #stdin is th parameter to the program we are executing
        java_class, ext = os.path.splitext(java_file)
        #print _input + "pp"
        cmd = ['java','-Xmx10g',  java_class, _input]
        

        proc = subprocess.Popen(cmd, stdin=PIPE, stdout = PIPE, stderr = STDOUT, shell = False)
        stdout, stderr = proc.communicate()
        self.outputs.append(stdout)
        #print self.outputs


    def runJava(self, java_file, param):
        #self. __compile(java_file)
        self.__execute(java_file, param)

    def run_parallel(self, java_file):
        for i in self.unique(self.getAllChainPossibilities()):
            ensemble = ""
            for p in i:
                ensemble += p
            print ensemble
            t = threading.Thread(target = self.runJava(java_file, ensemble), args = (i, ))
            print ensemble
            self.thread_list.append(t)
        

        #start the threads
        for thread in self.thread_list:
            thread.start()
        #block calling thread until threads whose join method is called is terminated
            thread.join()

        print "Done"
    def writeToFile(self, name):
        file = open(name, "w")
        for i in self.outputs:
            file.write(i)

        file.close()
    

#Now the actual running
#Run the code below only if the code is directly called
if __name__ == "__main__":

    #get command line arguments
    args = sys.argv
    print args

    name = Runner((int)(args[1]), (int)(args[2]))
    

    name.run_parallel("experiments/DiscreteStructuresVsP")
    
    name.writeToFile(" " + str(name.noOfResidues) + "R" + str(name.noOfP) + "P" + ".txt")
