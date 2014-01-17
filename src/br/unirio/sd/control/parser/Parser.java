//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 2 "expressao.y"
package br.unirio.sd.control.parser;

import br.unirio.sd.model.common.Expressao;
import br.unirio.sd.model.common.TipoOperacao;
//#line 22 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short MIN=257;
public final static short MAX=258;
public final static short IF=259;
public final static short AND=260;
public final static short OR=261;
public final static short NOT=262;
public final static short ID=263;
public final static short CONST=264;
public final static short LOOKUP=265;
public final static short L_PARENT=266;
public final static short R_PARENT=267;
public final static short ASTER=268;
public final static short PLUS=269;
public final static short COMMA=270;
public final static short MINUS=271;
public final static short DIV=272;
public final static short POTENC=273;
public final static short LE=274;
public final static short LT=275;
public final static short DIF=276;
public final static short GT=277;
public final static short GE=278;
public final static short EQUAL=279;
public final static short SEMICOLON=280;
public final static short BOUNDED=281;
public final static short ROUND=282;
public final static short LN=283;
public final static short EXP=284;
public final static short DT=285;
public final static short TIME=286;
public final static short POINT=287;
public final static short GROUPSUM=288;
public final static short GROUPMAX=289;
public final static short GROUPMIN=290;
public final static short L_COLCH=291;
public final static short R_COLCH=292;
public final static short COUNT=293;
public final static short BOUND=294;
public final static short SELECT=295;
public final static short VALUES=296;
public final static short UMINUS=297;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    3,    3,    1,    1,    2,    2,    2,
    2,    2,
};
final static short yylen[] = {                            2,
    1,    1,    3,    3,    3,    3,    2,    3,    3,    3,
    3,    3,    3,    3,    4,    4,    4,    8,    4,    4,
    4,   10,    3,    4,    4,    1,    1,    4,    6,    6,
    6,    3,    4,    3,    1,    3,    1,    1,    3,    3,
    6,    6,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    1,    0,    0,
    0,    0,    0,    0,   26,   27,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    7,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    8,    0,    0,    0,
   38,    0,    0,    0,   40,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   23,    0,    0,    0,    0,    0,
    0,    0,   20,    0,   19,    0,   16,   17,   15,    0,
   21,   24,   25,    0,    0,    0,    0,   28,    0,    0,
    0,   33,    0,    0,    0,    0,   39,    0,    0,    0,
    0,   34,    0,    0,   29,   30,   31,   41,   42,    0,
    0,   18,    0,    0,   22,
};
final static short yydgoto[] = {                         59,
   60,   26,   80,
};
final static short yysindex[] = {                      -103,
 -259, -252, -248, -246, -242, -225,    0,    0, -219, -103,
 -103, -205, -202, -201,    0,    0, -200, -199, -198, -258,
 -197, -196, -194, -193,  239, -281, -103, -103, -103, -103,
 -103, -103, -187, -156,    0, -103, -103, -103, -263, -263,
 -263, -215, -263, -263, -263, -235, -103, -103, -103, -103,
 -103, -103, -103, -103, -103, -103, -103, -182,  239, -237,
 -224,  191, -213, -212,  -73, -188,    0,  -60,  -47,  -34,
    0, -268, -266, -262,    0, -264, -261, -260, -186, -184,
 -173, -228, -228, -173,    0, -234, -234, -234, -234, -234,
 -234,    0,    0, -103,    0, -103,    0,    0,    0, -103,
    0,    0,    0, -178, -177, -176, -161,    0, -160, -103,
 -235,    0,  239,  203,  215, -163,    0, -162, -159, -158,
  -21,    0, -103, -103,    0,    0,    0,    0,    0,  166,
  227,    0, -103,  179,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    1,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0, -211,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0, -157,    0,
   36,   62,   75,   49,    0,   88,  101,  114,  127,  140,
  153,   22,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0, -207,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                        42,
   20,  -28,   -5,
};
final static int YYTABLESIZE=518;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         71,
    2,  104,  108,  106,   42,   58,   27,  107,  109,  110,
   72,   73,   74,   28,   76,   77,   78,   29,  105,   30,
  105,   32,  105,   31,  105,  105,  105,   20,   79,   93,
   22,   23,   94,   47,   48,    5,   49,   50,   51,   47,
   32,   25,   95,   50,   51,   94,   33,   61,    6,   63,
   64,   34,   35,   97,   98,   37,   94,   94,   37,   36,
   36,    3,   36,   37,   38,   39,   40,   41,   43,   44,
   62,   45,   46,   65,    4,   66,   75,   68,   69,   70,
   92,  100,  112,  111,  116,  117,  118,   14,   81,   82,
   83,   84,   85,   86,   87,   88,   89,   90,   91,   51,
   13,  119,  120,  125,  126,  122,    0,  127,  128,   35,
   67,   47,   48,   10,   49,   50,   51,   52,   53,   54,
   55,   56,   57,    0,    0,    0,   11,    0,    0,    0,
    0,    0,    0,    0,    0,  113,    0,  114,    0,   12,
    0,  115,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  121,    9,    1,    2,    3,    4,    5,    6,    7,
    8,    9,   10,    0,  130,  131,    0,   11,    0,    0,
    0,    0,    0,    0,  134,    0,    0,    0,   12,   13,
   14,   15,   16,    0,   17,   18,   19,   20,    0,   21,
   22,   23,   24,   99,   47,   48,    0,   49,   50,   51,
   52,   53,   54,   55,   56,   57,  101,   47,   48,    0,
   49,   50,   51,   52,   53,   54,   55,   56,   57,  102,
   47,   48,    0,   49,   50,   51,   52,   53,   54,   55,
   56,   57,  103,   47,   48,    0,   49,   50,   51,   52,
   53,   54,   55,   56,   57,  129,   47,   48,    0,   49,
   50,   51,   52,   53,   54,   55,   56,   57,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    2,    2,    2,
    2,    2,    2,    2,    2,    2,    2,    2,    2,    2,
    0,    0,    0,    0,    0,    0,    0,   38,   32,   32,
   32,   32,   32,   32,   32,   32,   32,   32,   32,   32,
   32,    0,    5,    5,    5,    5,    5,    5,   39,    5,
    5,    5,    5,    5,    5,    6,    6,    6,    6,    6,
    6,    0,    6,    6,    6,    6,    6,    6,    3,    0,
    3,    3,    3,    0,    0,    3,    3,    3,    3,    3,
    3,    4,    0,    4,    4,    4,    0,    0,    4,    4,
    4,    4,    4,    4,   14,    0,    0,   14,    0,    0,
    0,   14,   14,   14,   14,   14,   14,   13,    0,    0,
   13,    0,    0,    0,   13,   13,   13,   13,   13,   13,
   10,    0,    0,   10,    0,    0,    0,   10,   10,   10,
   10,   10,   10,   11,    0,    0,   11,    0,    0,    0,
   11,   11,   11,   11,   11,   11,   12,    0,    0,   12,
    0,    0,    0,   12,   12,   12,   12,   12,   12,    9,
    0,    0,    9,    0,    0,    0,    9,    9,    9,    9,
    9,    9,  132,   47,   48,    0,   49,   50,   51,   52,
   53,   54,   55,   56,   57,  135,   47,   48,    0,   49,
   50,   51,   52,   53,   54,   55,   56,   57,   47,   48,
   96,   49,   50,   51,   52,   53,   54,   55,   56,   57,
   47,   48,  123,   49,   50,   51,   52,   53,   54,   55,
   56,   57,   47,   48,  124,   49,   50,   51,   52,   53,
   54,   55,   56,   57,   47,   48,  133,   49,   50,   51,
   52,   53,   54,   55,   56,   57,   47,   48,    0,   49,
   50,   51,   52,   53,   54,   55,   56,   57,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                        263,
    0,  270,  267,  270,  263,  287,  266,  270,  270,  270,
   39,   40,   41,  266,   43,   44,   45,  266,  287,  266,
  287,    0,  287,  266,  287,  287,  287,  291,  264,  267,
  294,  295,  270,  268,  269,    0,  271,  272,  273,  268,
  266,    0,  267,  272,  273,  270,  266,   28,    0,   30,
   31,   10,   11,  267,  267,  267,  270,  270,  270,  267,
  266,    0,  270,  266,  266,  266,  266,  266,  266,  266,
   29,  266,  266,   32,    0,  263,  292,   36,   37,   38,
  263,  270,  267,  270,  263,  263,  263,    0,   47,   48,
   49,   50,   51,   52,   53,   54,   55,   56,   57,  273,
    0,  263,  263,  267,  267,  111,   -1,  267,  267,  267,
  267,  268,  269,    0,  271,  272,  273,  274,  275,  276,
  277,  278,  279,   -1,   -1,   -1,    0,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   94,   -1,   96,   -1,    0,
   -1,  100,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  110,    0,  257,  258,  259,  260,  261,  262,  263,
  264,  265,  266,   -1,  123,  124,   -1,  271,   -1,   -1,
   -1,   -1,   -1,   -1,  133,   -1,   -1,   -1,  282,  283,
  284,  285,  286,   -1,  288,  289,  290,  291,   -1,  293,
  294,  295,  296,  267,  268,  269,   -1,  271,  272,  273,
  274,  275,  276,  277,  278,  279,  267,  268,  269,   -1,
  271,  272,  273,  274,  275,  276,  277,  278,  279,  267,
  268,  269,   -1,  271,  272,  273,  274,  275,  276,  277,
  278,  279,  267,  268,  269,   -1,  271,  272,  273,  274,
  275,  276,  277,  278,  279,  267,  268,  269,   -1,  271,
  272,  273,  274,  275,  276,  277,  278,  279,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  267,  268,  269,
  270,  271,  272,  273,  274,  275,  276,  277,  278,  279,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  287,  267,  268,
  269,  270,  271,  272,  273,  274,  275,  276,  277,  278,
  279,   -1,  267,  268,  269,  270,  271,  272,  287,  274,
  275,  276,  277,  278,  279,  267,  268,  269,  270,  271,
  272,   -1,  274,  275,  276,  277,  278,  279,  267,   -1,
  269,  270,  271,   -1,   -1,  274,  275,  276,  277,  278,
  279,  267,   -1,  269,  270,  271,   -1,   -1,  274,  275,
  276,  277,  278,  279,  267,   -1,   -1,  270,   -1,   -1,
   -1,  274,  275,  276,  277,  278,  279,  267,   -1,   -1,
  270,   -1,   -1,   -1,  274,  275,  276,  277,  278,  279,
  267,   -1,   -1,  270,   -1,   -1,   -1,  274,  275,  276,
  277,  278,  279,  267,   -1,   -1,  270,   -1,   -1,   -1,
  274,  275,  276,  277,  278,  279,  267,   -1,   -1,  270,
   -1,   -1,   -1,  274,  275,  276,  277,  278,  279,  267,
   -1,   -1,  270,   -1,   -1,   -1,  274,  275,  276,  277,
  278,  279,  267,  268,  269,   -1,  271,  272,  273,  274,
  275,  276,  277,  278,  279,  267,  268,  269,   -1,  271,
  272,  273,  274,  275,  276,  277,  278,  279,  268,  269,
  270,  271,  272,  273,  274,  275,  276,  277,  278,  279,
  268,  269,  270,  271,  272,  273,  274,  275,  276,  277,
  278,  279,  268,  269,  270,  271,  272,  273,  274,  275,
  276,  277,  278,  279,  268,  269,  270,  271,  272,  273,
  274,  275,  276,  277,  278,  279,  268,  269,   -1,  271,
  272,  273,  274,  275,  276,  277,  278,  279,
};
}
final static short YYFINAL=25;
final static short YYMAXTOKEN=297;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"MIN","MAX","IF","AND","OR","NOT","ID","CONST","LOOKUP",
"L_PARENT","R_PARENT","ASTER","PLUS","COMMA","MINUS","DIV","POTENC","LE","LT",
"DIF","GT","GE","EQUAL","SEMICOLON","BOUNDED","ROUND","LN","EXP","DT","TIME",
"POINT","GROUPSUM","GROUPMAX","GROUPMIN","L_COLCH","R_COLCH","COUNT","BOUND",
"SELECT","VALUES","UMINUS",
};
final static String yyrule[] = {
"$accept : expr",
"expr : CONST",
"expr : ID",
"expr : expr PLUS expr",
"expr : expr MINUS expr",
"expr : expr ASTER expr",
"expr : expr DIV expr",
"expr : MINUS expr",
"expr : L_PARENT expr R_PARENT",
"expr : expr EQUAL expr",
"expr : expr DIF expr",
"expr : expr GT expr",
"expr : expr GE expr",
"expr : expr LT expr",
"expr : expr LE expr",
"expr : NOT L_PARENT expr R_PARENT",
"expr : AND L_PARENT params R_PARENT",
"expr : OR L_PARENT params R_PARENT",
"expr : IF L_PARENT expr COMMA expr COMMA expr R_PARENT",
"expr : MAX L_PARENT params R_PARENT",
"expr : MIN L_PARENT params R_PARENT",
"expr : ROUND L_PARENT expr R_PARENT",
"expr : LOOKUP L_PARENT ID COMMA expr COMMA expr COMMA expr R_PARENT",
"expr : expr POTENC expr",
"expr : LN L_PARENT expr R_PARENT",
"expr : EXP L_PARENT expr R_PARENT",
"expr : DT",
"expr : TIME",
"expr : COUNT L_PARENT objset R_PARENT",
"expr : GROUPSUM L_PARENT objset COMMA ID R_PARENT",
"expr : GROUPMAX L_PARENT objset COMMA ID R_PARENT",
"expr : GROUPMIN L_PARENT objset COMMA ID R_PARENT",
"expr : objset POINT ID",
"expr : VALUES L_PARENT values R_PARENT",
"values : CONST COMMA values",
"values : CONST",
"params : params COMMA expr",
"params : expr",
"objset : ID",
"objset : objset POINT ID",
"objset : L_COLCH ID R_COLCH",
"objset : BOUND L_PARENT objset COMMA ID R_PARENT",
"objset : SELECT L_PARENT objset COMMA expr R_PARENT",
};

//#line 256 "expressao.y"

private Lexico lexico;
private String equacao;

public Parser(String s)
{
	this.equacao = s;
	this.lexico = new Lexico(s);
}

public Expressao execute()
{
	int result = yyparse();
	return (result != 0) ? null : yyval.obj;
}

private int yylex()
{
	Token token = lexico.proximo();
	
	if (token == null)
		return 0;
	
	switch(token.getTipo())
	{
		case CONST:
			yylval = new ParserVal(token.getValor());
			return CONST;
			
		case ID:
			yylval = new ParserVal(token.getNome());
			return ID;
			
		default:
			return token.getTipo();
	}
}

private void yyerror(String message)
{
	ErrorManager.getInstance().add(message + "(" + equacao + ")");
}
//#line 432 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 68 "expressao.y"
{
			yyval = new ParserVal(new Expressao(val_peek(0).dval));
		}
break;
case 2:
//#line 72 "expressao.y"
{
			yyval = new ParserVal(new Expressao(val_peek(0).sval));
		}
break;
case 3:
//#line 76 "expressao.y"
{
			yyval = new ParserVal(new Expressao(TipoOperacao.SOMA, val_peek(2).obj, val_peek(0).obj));
		}
break;
case 4:
//#line 80 "expressao.y"
{
			yyval = new ParserVal(new Expressao(TipoOperacao.SUBTRACAO, val_peek(2).obj, val_peek(0).obj));
		}
break;
case 5:
//#line 84 "expressao.y"
{
			yyval = new ParserVal(new Expressao(TipoOperacao.PRODUTO, val_peek(2).obj, val_peek(0).obj));
		}
break;
case 6:
//#line 88 "expressao.y"
{
			yyval = new ParserVal(new Expressao(TipoOperacao.DIVISAO, val_peek(2).obj, val_peek(0).obj));
		}
break;
case 7:
//#line 92 "expressao.y"
{
			yyval = new ParserVal(new Expressao(TipoOperacao.UMINUS, val_peek(0).obj, null));
		}
break;
case 8:
//#line 96 "expressao.y"
{
			yyval = new ParserVal(new Expressao(TipoOperacao.PARENTESES, val_peek(1).obj, null));
		}
break;
case 9:
//#line 100 "expressao.y"
{
			yyval = new ParserVal(new Expressao(TipoOperacao.IGUAL, val_peek(2).obj, val_peek(0).obj));
		}
break;
case 10:
//#line 104 "expressao.y"
{
			yyval = new ParserVal(new Expressao(TipoOperacao.DIFERENTE, val_peek(2).obj, val_peek(0).obj));
		}
break;
case 11:
//#line 108 "expressao.y"
{
			yyval = new ParserVal(new Expressao(TipoOperacao.MAIOR, val_peek(2).obj, val_peek(0).obj));
		}
break;
case 12:
//#line 112 "expressao.y"
{
			yyval = new ParserVal(new Expressao(TipoOperacao.MAIORIG, val_peek(2).obj, val_peek(0).obj));
		}
break;
case 13:
//#line 116 "expressao.y"
{
			yyval = new ParserVal(new Expressao(TipoOperacao.MENOR, val_peek(2).obj, val_peek(0).obj));
		}
break;
case 14:
//#line 120 "expressao.y"
{
			yyval = new ParserVal(new Expressao(TipoOperacao.MENORIG, val_peek(2).obj, val_peek(0).obj));
		}
break;
case 15:
//#line 124 "expressao.y"
{
			yyval = new ParserVal(new Expressao(TipoOperacao.NOT, val_peek(1).obj, null));
		}
break;
case 16:
//#line 128 "expressao.y"
{
			yyval = new ParserVal(new Expressao(TipoOperacao.AND, val_peek(1).obj, null));
		}
break;
case 17:
//#line 132 "expressao.y"
{
			yyval = new ParserVal(new Expressao(TipoOperacao.OR, val_peek(1).obj, null));
		}
break;
case 18:
//#line 136 "expressao.y"
{
			yyval = new ParserVal(new Expressao(TipoOperacao.COMMA, val_peek(3).obj, val_peek(1).obj));
			yyval = new ParserVal(new Expressao(TipoOperacao.IF, val_peek(5).obj, yyval.obj));
		}
break;
case 19:
//#line 141 "expressao.y"
{
			yyval = new ParserVal(new Expressao(TipoOperacao.MAX, val_peek(1).obj, null));
		}
break;
case 20:
//#line 145 "expressao.y"
{
			yyval = new ParserVal(new Expressao(TipoOperacao.MIN, val_peek(1).obj, null));
		}
break;
case 21:
//#line 149 "expressao.y"
{
			yyval = new ParserVal(new Expressao(TipoOperacao.ROUND, val_peek(1).obj, null));
		}
break;
case 22:
//#line 153 "expressao.y"
{
			Expressao e = new Expressao(val_peek(7).sval);
			yyval = new ParserVal(new Expressao(TipoOperacao.COMMA, val_peek(3).obj, val_peek(1).obj));
			yyval = new ParserVal(new Expressao(TipoOperacao.COMMA, val_peek(5).obj, yyval.obj));
			yyval = new ParserVal(new Expressao(TipoOperacao.COMMA, e, yyval.obj));
			yyval = new ParserVal(new Expressao(TipoOperacao.LOOKUP, yyval.obj, null));
		}
break;
case 23:
//#line 161 "expressao.y"
{
			yyval = new ParserVal(new Expressao(TipoOperacao.POTENC, val_peek(2).obj, val_peek(0).obj));
		}
break;
case 24:
//#line 165 "expressao.y"
{
			yyval = new ParserVal(new Expressao(TipoOperacao.LOGN, val_peek(1).obj, null));
		}
break;
case 25:
//#line 169 "expressao.y"
{
			yyval = new ParserVal(new Expressao(TipoOperacao.EXPN, val_peek(1).obj, null));
		}
break;
case 26:
//#line 173 "expressao.y"
{
			yyval = new ParserVal(new Expressao(TipoOperacao.DT, null, null));
		}
break;
case 27:
//#line 177 "expressao.y"
{
			yyval = new ParserVal(new Expressao(TipoOperacao.TIME, null, null));
		}
break;
case 28:
//#line 181 "expressao.y"
{
			yyval = new ParserVal(new Expressao(TipoOperacao.COUNT, val_peek(1).obj, null));
		}
break;
case 29:
//#line 185 "expressao.y"
{
			Expressao e1 = new Expressao(val_peek(1).sval);
			yyval = new ParserVal(new Expressao(TipoOperacao.GRUPO_SOMA, val_peek(3).obj, e1));
		}
break;
case 30:
//#line 190 "expressao.y"
{
			Expressao e1 = new Expressao(val_peek(1).sval);
			yyval = new ParserVal(new Expressao(TipoOperacao.GRUPO_MAX, val_peek(3).obj, e1));
		}
break;
case 31:
//#line 195 "expressao.y"
{
			Expressao e1 = new Expressao(val_peek(1).sval);
			yyval = new ParserVal(new Expressao(TipoOperacao.GRUPO_MIN, val_peek(3).obj, e1));
		}
break;
case 32:
//#line 200 "expressao.y"
{
			Expressao e1 = new Expressao(val_peek(2).sval);
			Expressao e2 = new Expressao(val_peek(0).sval);
			yyval = new ParserVal(new Expressao(TipoOperacao.PONTO, e1, e2));
		}
break;
case 33:
//#line 206 "expressao.y"
{
			yyval = val_peek(1);
		}
break;
case 34:
//#line 212 "expressao.y"
{
			yyval = new ParserVal(new Expressao(TipoOperacao.COMMA, new Expressao(val_peek(2).dval), val_peek(0).obj));
		}
break;
case 35:
//#line 216 "expressao.y"
{
			yyval = new ParserVal(new Expressao(val_peek(0).dval));
		}
break;
case 36:
//#line 222 "expressao.y"
{
			yyval = new ParserVal(new Expressao(TipoOperacao.COMMA, val_peek(2).obj, val_peek(0).obj));
		}
break;
case 37:
//#line 226 "expressao.y"
{
			yyval = new ParserVal(val_peek(0).obj);
		}
break;
case 38:
//#line 232 "expressao.y"
{
			yyval = new ParserVal(new Expressao(val_peek(0).sval));
		}
break;
case 39:
//#line 236 "expressao.y"
{
			Expressao e1 = new Expressao(val_peek(0).sval);
			yyval = new ParserVal(new Expressao(TipoOperacao.PONTO, val_peek(2).obj, e1));
		}
break;
case 40:
//#line 241 "expressao.y"
{
			Expressao e1 = new Expressao(val_peek(1).sval);
			yyval = new ParserVal(new Expressao(TipoOperacao.CLSSET, e1, null));
		}
break;
case 41:
//#line 246 "expressao.y"
{
			Expressao e1 = new Expressao(val_peek(1).sval);
			yyval = new ParserVal(new Expressao(TipoOperacao.BOUND, val_peek(3).obj, e1));
		}
break;
case 42:
//#line 251 "expressao.y"
{
			yyval = new ParserVal(new Expressao(TipoOperacao.SELECT, val_peek(3).obj, val_peek(1).obj));
		}
break;
//#line 846 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
