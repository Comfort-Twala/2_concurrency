# Assignment 2 program makefile
# Comfort Twala

JAVA=/usr/bin/java
JAVAC=/usr/bin/javac
JAVADOC=/usr/bin/javadoc
PYTHON=/usr/bin/python3
.SUFFIXES: .java .class
SRCDIR=src
BINDIR=bin
DOCDIR=doc
SCRIPTDIR=script

$(BINDIR)/%.class:$(SRCDIR)/%.java
		$(JAVAC) -d $(BINDIR)/ *.java

CLASSES=Score.class WordDictionary.class WorldRecord.class WorldPanel.class WordApp.class

CLASS_FILES=$(CLASSES:%.class=$(BINDIR)/%.class)

default: $(CLASS_FILES)

clean:
		$(RM) $(BINDIR)/*.class

run: $(CLASS_FILES)
		$(JAVA) -cp $(BINDIR) WordApp $(tot_words) $(on_screen) $(dict)


docs:
		$(JAVADOC) -d $(DOCDIR) $(SRCDIR)/*

clean-doc:
		rm -r $(DOCDIR)/*
