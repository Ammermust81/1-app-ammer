/*     */ package crosswordexpress;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Insets;
/*     */ 
/*     */ public class Def
/*     */ {
/*   8 */   static String ver = "20220207";
/*     */ 
/*     */   
/*  11 */   static String sixpackPuz = "6-Pack Puzzles No. ";
/*  12 */   static String sixpackSol = "6-Pack Solutions No. ";
/*     */ 
/*     */   
/*     */   static final int focusWord = 61132;
/*     */   
/*  17 */   static final int[] themeColor = new int[] { 16777215, 16764108, 16764159, 16777164, 13434879 };
/*     */   
/*     */   static final String hiLite = "<html><font color=006644 size=3>";
/*  20 */   static final Color COLOR_BLACK = new Color(0);
/*  21 */   static final Color COLOR_WHITE = new Color(16777215);
/*  22 */   static final Color COLOR_RED = new Color(16711680);
/*  23 */   static final Color COLOR_HELPBUTTON = new Color(16777215);
/*  24 */   static final Color COLOR_GREEN = new Color(65280);
/*  25 */   static final Color COLOR_TREE_GREEN = new Color(21760);
/*  26 */   static final Color COLOR_GRASS_GREEN = new Color(7838071);
/*  27 */   static final Color COLOR_TENT = new Color(11184759);
/*  28 */   static final Color COLOR_BLUE = new Color(255);
/*  29 */   static final Color COLOR_PALEBLUE = new Color(16056319);
/*  30 */   static final Color COLOR_GRAY = new Color(8947848);
/*  31 */   static final Color COLOR_DARKGRAY = new Color(4473924);
/*  32 */   static final Color COLOR_LIGHTGRAY = new Color(13421772);
/*  33 */   static final Color COLOR_VERYLIGHTGRAY = new Color(15658734);
/*  34 */   static final Color COLOR_ERROR = new Color(16746632);
/*  35 */   static final Color COLOR_CURSOR = new Color(47991);
/*  36 */   static final Color COLOR_HILITE = new Color(4473992);
/*  37 */   static final Color COLOR_DARKGREEN = new Color(34816);
/*  38 */   static final Color COLOR_HEADBG = new Color(15658717);
/*  39 */   static final Color COLOR_BROWN = new Color(4473856);
/*  40 */   static final Color COLOR_CYAN = new Color(65535);
/*  41 */   static final Color COLOR_YELLOW = new Color(16776960);
/*  42 */   static final Color COLOR_MENUBAR = new Color(15663086);
/*  43 */   static final Color COLOR_FRAMEBG = new Color(13426124);
/*  44 */   static final Color COLOR_BUTTONBG = new Color(16777198);
/*  45 */   static final Color COLOR_BUTTONHL = new Color(56763);
/*  46 */   static final Color COLOR_MENUBG = new Color(12303274);
/*  47 */   static final Color COLOR_DIALOGBG = new Color(14540236);
/*  48 */   static final Color COLOR_SELECTFRAME = new Color(26214);
/*  49 */   static final Color COLOR_HINT_R = new Color(16737894);
/*  50 */   static final Color COLOR_HINT_G = new Color(11206570);
/*  51 */   static final Color COLOR_HINT_B = new Color(52479);
/*  52 */   static final Color COLOR_LABEL = new Color(7798835);
/*  53 */   static final Color COLOR_HELPBG = new Color(16777208);
/*     */   
/*     */   static final int ITEM_TITLE = 0;
/*     */   
/*     */   static final int ITEM_AUTHOR = 1;
/*     */   
/*     */   static final int ITEM_COPYRIGHT = 2;
/*     */   
/*     */   static final int ITEM_NUMBER = 3;
/*     */   
/*     */   static final int ITEM_NOTES = 4;
/*     */   
/*     */   static final int ITEM_PUZZLE = 5;
/*     */   
/*     */   static final int ITEM_SOLTITLE = 6;
/*     */   
/*     */   static final int ITEM_SOLUTION = 7;
/*     */   
/*     */   static final int ITEM_CLUE1 = 8;
/*     */   
/*     */   static final int ITEM_CLUE2 = 9;
/*     */   
/*     */   static final int ITEM_CLUE3 = 10;
/*     */   static final int ITEM_CLUE4 = 11;
/*     */   static final int ITEM_CLUE5 = 12;
/*     */   static final int ITEM_CLUE6 = 13;
/*     */   static final int ITEM_CLUE7 = 14;
/*     */   static final int ITEM_CLUE8 = 15;
/*     */   static final int ITEM_CLUE9 = 16;
/*     */   static final int ITEM_CLUE10 = 17;
/*     */   static final int ITEM_CLUE11 = 18;
/*     */   static final int ITEM_CLUE12 = 19;
/*     */   static final int ITEM_ACROSTIC = 20;
/*     */   static final int ITEM_CODEWORD = 21;
/*     */   static final int ITEM_WORDPOOL = 22;
/*     */   static int puzzleMode;
/*     */   static int prevMode;
/*     */   static final int pM_MENU = 1;
/*     */   static final int pM_GRID_MTCE = 2;
/*     */   static final int pM_DICTIONARY_MTCE = 3;
/*     */   static final int pM_CROSSWORD_BUILD = 4;
/*     */   static final int pM_CROSSWORD_SOLVE = 5;
/*     */   static final int pM_FREEFORM_BUILD = 6;
/*     */   static final int pM_ARROWWORD_PRINT = 7;
/*     */   static final int pM_SIXPACK = 8;
/*     */   static final int pM_WORD_TOOLS = 9;
/*     */   static final int pM_FRENCH_STYLE = 12;
/*     */   static final int pM_FFFILLIN_PRINT = 13;
/*     */   static final int pM_ACROSTIC_BUILD = 10;
/*     */   static final int pM_ACROSTIC_SOLVE = 11;
/*     */   static final int pM_AKARI_BUILD = 20;
/*     */   static final int pM_AKARI_SOLVE = 21;
/*     */   static final int pM_CODEWORD_SOLVE = 30;
/*     */   static final int pM_DOMINO_BUILD = 40;
/*     */   static final int pM_DOMINO_SOLVE = 41;
/*     */   static final int pM_DOUBLET_BUILD = 50;
/*     */   static final int pM_DOUBLET_SOLVE = 51;
/*     */   static final int pM_FILLIN_SOLVE = 60;
/*     */   static final int pM_FFFILLIN_SOLVE = 61;
/*     */   static final int pM_FILLOMINO_BUILD = 70;
/*     */   static final int pM_FILLOMINO_SOLVE = 71;
/*     */   static final int pM_FINDAQUOTE = 72;
/*     */   static final int pM_FUTOSHIKI_BUILD = 80;
/*     */   static final int pM_FUTOSHIKI_SOLVE = 81;
/*     */   static final int pM_GOKIGEN_BUILD = 90;
/*     */   static final int pM_GOKIGEN_SOLVE = 91;
/*     */   static final int pM_HAILSTONE = 92;
/*     */   static final int pM_KAKURO_BUILD = 100;
/*     */   static final int pM_KAKURO_SOLVE = 101;
/*     */   static final int pM_KENDOKU_BUILD = 110;
/*     */   static final int pM_KENDOKU_SOLVE = 111;
/*     */   static final int pM_KSUDOKU_BUILD = 112;
/*     */   static final int pM_KSUDOKU_SOLVE = 113;
/*     */   static final int pM_LADDERWORD_BUILD = 120;
/*     */   static final int pM_LADDERWORD_SOLVE = 121;
/*     */   static final int pM_LETTERDROP_BUILD = 130;
/*     */   static final int pM_LETTERDROP_SOLVE = 131;
/*     */   static final int pM_MARUPEKE_BUILD = 230;
/*     */   static final int pM_MARUPEKE_SOLVE = 231;
/*     */   static final int pM_MINESWEEPER_BUILD = 132;
/*     */   static final int pM_MINESWEEPER_SOLVE = 133;
/*     */   static final int pM_OUROBOROS_BUILD = 134;
/*     */   static final int pM_OUROBOROS_SOLVE = 135;
/*     */   static final int pM_PYRAMIDWORD_BUILD = 140;
/*     */   static final int pM_PYRAMIDWORD_SOLVE = 141;
/*     */   static final int pM_ROUNDABOUTS_BUILD = 150;
/*     */   static final int pM_ROUNDABOUTS_SOLVE = 151;
/*     */   static final int pM_SIKAKU_BUILD = 160;
/*     */   static final int pM_SIKAKU_SOLVE = 161;
/*     */   static final int pM_SLITHERLINK_BUILD = 170;
/*     */   static final int pM_SLITHERLINK_SOLVE = 171;
/*     */   static final int pM_SUDOKU_BUILD = 180;
/*     */   static final int pM_SUDOKU_SOLVE = 181;
/*     */   static final int pM_TATAMI_BUILD = 182;
/*     */   static final int pM_TATAMI_SOLVE = 183;
/*     */   static final int pM_TENTS_BUILD = 190;
/*     */   static final int pM_TENTS_SOLVE = 191;
/*     */   static final int pM_WORDSEARCH_BUILD = 200;
/*     */   static final int pM_WORDSEARCH_SOLVE = 201;
/*     */   static final int pM_WORDWEAVE_PRINT = 202;
/*     */   static final int pM_WORDSQUARE_BUILD = 210;
/*     */   static final int pM_WORDSQUARE_SOLVE = 211;
/*     */   static final int pM_DEVANAGARI_BUILD = 220;
/*     */   static final int CELL_ACTIVE = 0;
/*     */   static final int CELL_BLACK = 1;
/*     */   static final int CELL_NULL = 2;
/*     */   static final int CELL_WALL = 3;
/*     */   static final int CELL_FLOOR = 4;
/*     */   static final int CELL_CORNER = 5;
/*     */   static final int CELL_HORIZ = 6;
/*     */   static final int CELL_VERT = 7;
/*     */   static final int CELL_BL = 8;
/*     */   static final int CELL_TL = 9;
/*     */   static final int CELL_BR = 10;
/*     */   static final int CELL_TR = 11;
/*     */   static final int CELL_LEFT = 12;
/*     */   static final int CELL_UP = 13;
/*     */   static final int CELL_BOTH = 14;
/*     */   static boolean selecting = false;
/*     */   static boolean printing = false;
/*     */   static boolean isMac;
/*     */   static int extraH;
/*     */   static int extraW;
/*     */   static Insets insets;
/*     */   static int building;
/*     */   static final int STATE_IDLE = 0;
/*     */   static final int STATE_BUILDING = 1;
/*     */   static final int STATE_INTERRUPTED = 2;
/*     */   static boolean audienceMode = false;
/* 182 */   static Boolean dispSolArray = Boolean.valueOf(false);
/* 183 */   static Boolean dispGuideDigits = Boolean.valueOf(false);
/* 184 */   static Boolean dispCursor = Boolean.valueOf(false);
/* 185 */   static Boolean dispErrors = Boolean.valueOf(false);
/* 186 */   static Boolean dispSlitherlinkSol = Boolean.valueOf(false);
/* 187 */   static Boolean dispSlitherlinkPuz = Boolean.valueOf(false);
/* 188 */   static Boolean dispNullCells = Boolean.valueOf(false);
/* 189 */   static Boolean dispWSloops = Boolean.valueOf(false);
/* 190 */   static Boolean dispPrinting = Boolean.valueOf(false);
/* 191 */   static Boolean dispToPrinter = Boolean.valueOf(false);
/* 192 */   static Boolean dispWithColor = Boolean.valueOf(true);
/* 193 */   static Boolean dispWithShadow = Boolean.valueOf(false);
/* 194 */   static Boolean dispWordSearchCircles = Boolean.valueOf(false);
/* 195 */   static Boolean displaySubmission = Boolean.valueOf(false);
/* 196 */   static Boolean displaySubmissionPuz = Boolean.valueOf(false);
/*     */   static final int ALIGN_LEFT = 0;
/*     */   static final int ALIGN_RIGHT = 1;
/*     */   static final int ALIGN_CENTER = 2;
/*     */   static final int ALIGN_JUSTIFY = 3;
/*     */   static final int VALIGN_TOP = 4;
/*     */   static final int VALIGN_CENTER = 5;
/*     */   static final int STANDARD_DIC = 0;
/*     */   static final int THEME_DIC = 1;
/*     */   static final int DIR_ACROSS = 0;
/*     */   static final int DIR_DOWN = 1;
/*     */   static final int WORDS = 0;
/*     */   static final int CLUES = 1;
/*     */   static final int WORDS_AND_CLUES = 2;
/*     */   static final int PUZZLE_GRID = 3;
/*     */   static final String HELP_TEXT = "<html><font size=6 color=BB0000 face=Serif>Help ";
/*     */   static final String HELP_ICON = "graphics/help.png";
/*     */   static final String DARROW_ICON = "graphics/darrow.png";
/*     */   static final String UARROW_ICON = "graphics/uarrow.png";
/*     */ }


/* Location:              C:\Users\mustapha\Desktop\Crossword-Express.jar!\crosswordexpress\Def.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */