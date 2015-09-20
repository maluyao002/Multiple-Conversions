#!/usr/bin/env python

#******************************************************************************
#  Autho: Luyao Ma, Yu Ma
#  CS 6421 - multiple Conversation
#  Execution:    python ConvServerTwo.py portnum
#  Description: This is a conversion server that converse ounces(oz) to dollars($) or viceversa
#******************************************************************************
from __future__ import division
import socket
import sys

## Function to process requests
def process(conn):
    #conn.send("Welcome to the Ounce of banana (oz) to dollars (y) conversion server!\n")

    # read userInput from client
    userInput = conn.recv(BUFFER_SIZE)
    if not userInput:
        print "Error reading message\n"
        sys.exit(1)
    userInput = userInput.strip()
    mylist = userInput.split(" ")
    if len(mylist) != 3:
        print "Input format incorrect!\n"
        sys.eixt(1)
    print "Received message: ", userInput
    # TODO: add convertion function here, reply = func(userInput)
    print mylist
    reply = tfunction(mylist)
    print reply
    if reply == (-1):
        conn.send("Input format incorrect!\n")
    else: 
        conn.send('%s' % reply)
    conn.close()


### Main code run when program is started
BUFFER_SIZE = 1024
interface = ""

# if input arguments are wrong, print out usage
if len(sys.argv) != 2:
    print >> sys.stderr, "usage: python {0} portnum\n".format(sys.argv[0])
    sys.exit(1)

portnum = int(sys.argv[1])

# create socket
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.bind((interface, portnum))
s.listen(5)

def tfunction(Input):
    try: 
        first = Input[0]
        second = Input[1]
        amount = float(Input[2])
    except ValueError:   
        return (-1)
    if first == 'oz' and second == '$':
        return float('%.3f' % (amount/16))
    elif first == '$' and second =='oz':
        return float('%.3f' % (amount*16))
    else: 
        return (-1)

while True:
    # accept connection and print out info of client
    conn, addr = s.accept()
    print 'Accepted connection from client', addr
    process(conn)
s.close()