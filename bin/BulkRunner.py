from NoOfDiscreteStructures import *
import sys
import os
import multiprocessing

from multiprocessing import Pool
args = sys.argv
work_to_do = []

for i in range(int(args[1]) + 1):
    work_to_do.append(i)

def worker(noOfP):
    runner = Runner((int)(args[1]), noOfP)
    runner.run_parallel("experiments/DiscreteStructuresVsP")
    path = str(args[1])+"Residues" + "/"
    if not os.path.exists(path):
        os.makedirs(path)
    runner.writeToFile(os.path.join(path + "" + str(runner.noOfResidues) + "R" + str(runner.noOfP) + "P" + ".txt"))


p = Pool(multiprocessing.cpu_count() - 2)

p.map(worker, work_to_do)
