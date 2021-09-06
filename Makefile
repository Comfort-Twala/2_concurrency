# Assignment 2 program makefile
# Comfort Twala

JAVA=/usr/bin/java
JAVAC=/usr/bin/javac
JAVADOC=/usr/bin/javadoc
.SUFFIXES: .java .class
SRCDIR=src
BINDIR=bin
DOCDIR=doc

$(BINDIR)/%.class:$(SRCDIR)/%.java
		$(JAVAC) -d $(BINDIR)/ $(SRCDIR)/*.java

CLASSES=Score.class WordDictionary.class WordRecord.class WordPanel.class WordApp.class

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
