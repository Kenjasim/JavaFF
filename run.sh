BASEDIR=$(dirname "$0")
ORIGINALDIR=$(pwd)
cd $BASEDIR/build
java javaff.JavaFF $1 $2 $3 $4
cd $ORIGINALDIR