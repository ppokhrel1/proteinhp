import sys
import itertools
import os.path, subprocess
from subprocess import STDOUT, PIPE
#for email
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
        temp = [''.join(p) for p in itertools.permutations(ensemble)]
        
        structures = list(set(temp))
        return structures
    
    def __compile(self, java_file):
        subprocess.check_call(['javac', java_file])

    def __execute(self, java_file, stdin): #stdin is th parameter to the program we are executing
        java_class, ext = os.path.splitext(java_file)
        cmd = ['java', '-cp', java_class, stdin]
        #print java_class
        #print ext
        proc = subprocess.Popen(cmd, stdin=PIPE, stdout = PIPE, stderr = STDOUT, shell = True)
        stdout, stderr = proc.communicate()
        self.outputs.append(stdout)
        #print self.outputs


    def runJava(self, java_file, param):
        #self. __compile(java_file)
        self.__execute(java_file, param)

    def run_parallel(self, java_file):
        for i in self.getAllChainPossibilities():
            t = threading.Thread(target = self.runJava(java_file, i), args = (i, ))
            print i
            self.thread_list.append(t)
        
        #start the threads
        for thread in self.thread_list:
            thread.start()
        #block calling thread until threads whose join method is called is terminated
        for thread in self.thread_list:
            thread.join()

        print "Done"
    def writeToFile(self, name):
        file = open(name, "w")
        for i in self.outputs:
            file.write(i)

        file.close()
    
    def sendEmail(self, send_from, send_to, subject, text, files = [], server = 'localhost', port = 587, username = '', password = '', isTls = True):
        #create a new message
        msg = MIMEMultipart()
        msg['From'] = send_from
        msg['To'] = COMMASPACE.join(send_to)
        msg['Date'] = formatdate(localtime = True)
        msg['Subject'] = subject
        
        #attach files
        for f in files:
            part = MIMEBase('application', 'octet-stream')
            part.set_payload(open(f, "rb").read())
            encoders.encode_base64(part)
            part.add_header('Content-Disposition', 'attachment; filename = "{0}"'.format(os.path.basename(f)))
            msg.attach(part)


            #now, send the email from smtp server
            smtp = stmplib.SMTP(server, port)
            if isTls:
                smtp.starttls()
            smtp.login(username, password)
            smtp.sendmail(send_from, send_to, msg.as_string())
            smtp.quit()


#Now the actual running
#Run the code below only if the code is directly called
if __name__ == "__main__":

    #get command line arguments
    args = sys.argv
    print args

    name = Runner((int)(args[1]), (int)(args[2]))
    #for i in name.getAllChainPossibilities():
    #    print i
    #    name.runJava('DiscreteStructuresVsP', i)
    name.run_parallel("experiments.DiscreteStructuresVsP")
    name.writeToFile(" " + str(name.noOfResidues) + "R" + str(name.noOfP) + "P" + ".txt")
