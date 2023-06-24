ARGS=sla.txt

all: compile run

compile:
	cd src && \
	javac -d ../bin com/lang/Main.java

run:
	cd bin && \
	java com.lang.Main $(ARGS)
