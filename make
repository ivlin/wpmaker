all: WPMaker.class
     java WPMaker

WPMaker.class: WPMaker.java
	javac WPMaker.java

clean:
	rm *~ *.class

