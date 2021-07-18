# alterar para o caminho onde estao os ficheiros
TOOLS=~/

JAVA = java
JAVAFLAGS =
JAVAC = javac
JAVACFLAGS =

JCLASSPATH = .:$(TOOLS)

CUP = java -classpath $(JCLASSPATH) java_cup/Main
CUPFLAGS =

JLEX = java -classpath $(JCLASSPATH) JLex/Main
JLEXFLAGS =

CLASSES = parser.class Yylex.class \
	  Main.class TISC.class
	  # etc ...

INTERMEDIATE_FILES = regact.lex.java sym.java parser.java Yylex.java

.INTERMEDIATE: sym.java
.SECONDARY: regact.lex.java Yylex.java

.PHONY: all clean run

%.class: %.java
	CLASSPATH=$(JCLASSPATH) $(JAVAC) $(JAVACFLAGS) $<

all: $(CLASSES)

Main.class: Main.java TISC.class parser.class

parser.java sym.java: regact.cup
	$(CUP) $(CUPFLAGS) $<
	@[ -s $@ ] || { rm -f $@; exit 1; }

parser.class: parser.java Yylex.class
	CLASSPATH=$(JCLASSPATH) $(JAVAC) $(JAVACFLAGS) $<

sym.class: sym.java

regact.lex.java: regact.lex
	$(JLEX) $(JLEXFLAGS) $<

Yylex.java:
	ln -sf regact.lex.java $@

Yylex.class: Yylex.java regact.lex.java sym.class
	CLASSPATH=$(JCLASSPATH) $(JAVAC) $(JAVACFLAGS) $<

clean:
	$(RM) $(INTERMEDIATE_FILES)
	$(RM) *.class *~

run:
	CLASSPATH=$(JCLASSPATH) $(JAVA) $(JAVAFLAGS) Main
