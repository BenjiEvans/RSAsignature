sh build.sh
app=$1
if [ $app == "authenticate" ]; 
then
	 java -classpath ./build Main 
elif [  $app == "key_gen" ]
then
	java -classpath ./build KeyGen
fi