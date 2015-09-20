# Multiple-Conversions
# Authors: Luyao Ma, Yu Ma  
This is a java-based and python-based multi-servers application that does a multi-step conversion, which tells you how much 
does a given grams bananas worth in yens!

There are totally five conversion servers and a proxy server, two conversion servers aare wirtten in python, else are all in java.

The forward conversion is in following steps:

grams(g) => kilograms(kg) (1000:1)  
kilograms(kg) => pounds(lbs) (1:2.2)  
pounds(lbs) => ounces(oz) (1:16)  
ounces(oz) of bananas => dollars($) (16: 1)  
dollars($) => yens(y) (1:120)  
The conversion in reverse order can also be accepted.  

#Configuration
The conversion servers must be set up with following steps.  
python ConvServerOne.py 5555  
java ConvServerTwo 6666  
java ConvServerThree 7777  
python ConvServerFour.py 8888  
java ConvServerFive 9999  

The proxy server can be set with any valid portnumber.
