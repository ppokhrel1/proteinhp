import re

for i in range(11):
    data = ''
    with open("10R" + str(i) + "P.txt", 'r') as myFile:
        data += myFile.read()
    #print data
    numbers = re.findall(r'\b\d+\b', data)

    #print numbers

    values = []
    for s in numbers:
        values.append(float(s))
    total = 0
    for value in values:
        total += value
    average = total / len(values)
    print average
    #print values
