BASEDIR=$(dirname "$0")
ORIGINALDIR=$(pwd)
cd $BASEDIR
rm -rf build
mkdir build
find "$PWD" -name "*.java" > build/sources.txt
cd build
javac -d . -Xlint:deprecation @sources.txt > $BASEDIR/compiler_output.txt
rm sources.txt
cd $ORIGINALDIR