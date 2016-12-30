all: WPMaker.class WPGUI.class
	java WPGUI

WPMaker.class: WPMaker.java
	javac WPMaker.java

WPGUI.class: WPGUI.java
	javac WPGUI.java

clean:
	rm *.jpg
	rm *.png
	rm *.jpeg
	rm *~
	rm *.class
