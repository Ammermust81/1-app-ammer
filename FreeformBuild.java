/*      */ package crosswordexpress;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.Toolkit;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.io.DataInputStream;
/*      */ import java.io.DataOutputStream;
/*      */ import java.io.File;
/*      */ import javax.swing.AbstractAction;
/*      */ import javax.swing.Action;
/*      */ import javax.swing.JButton;
/*      */ import javax.swing.JCheckBox;
/*      */ import javax.swing.JDialog;
/*      */ import javax.swing.JFileChooser;
/*      */ import javax.swing.JLabel;
/*      */ import javax.swing.JMenuItem;
/*      */ import javax.swing.JPanel;
/*      */ import javax.swing.JTextField;
/*      */ import javax.swing.KeyStroke;
/*      */ 
/*      */ public class FreeformBuild extends JPanel {
/*      */   static JFrame jfFreeform;
/*      */   static JMenuBar menuBar;
/*      */   JMenu menu;
/*      */   JMenu submenu;
/*      */   JMenuItem menuItem;
/*      */   JMenuItem buildMenuItem;
/*   28 */   static int howMany = 1; static JPanel pp; static int panelW; static int panelH; static JLabel jl1; static JLabel jl2; Thread thread;
/*   29 */   static int startPuz = Integer.parseInt((new SimpleDateFormat("yyyyMMdd"))
/*   30 */       .format(new Date())); static boolean ffMulti = false;
/*      */   static int multiPuzNum;
/*      */   int wCount;
/*      */   int cCount;
/*   34 */   int iCount = 0; char[][] chWord; int[] busy; int[] wNum;
/*      */   int[] wPos;
/*      */   int[] wNeg;
/*   37 */   int[] iIndex = new int[10000]; char[] wChar;
/*   38 */   char[] cIndex = new char[10000]; static int scoreW; static int scoreC; static int scoreL; static int area; static int bestScoreW; static int bestScoreC; static int bestScoreL; static int bestArea;
/*      */   static int bestEnforcedLink;
/*      */   int sNum;
/*      */   int sCount;
/*      */   Slot[] slot;
/*      */   static String thePic;
/*   44 */   String theFilter = "*";
/*      */   
/*      */   static boolean geminiMode = false;
/*      */   static boolean enforceLinkMode;
/*      */   static boolean draw = true;
/*      */   
/*      */   static void def() {
/*   51 */     Op.updateOption(Op.FF.FfPuz.ordinal(), "sample.crossword", Op.ff);
/*   52 */     Op.updateOption(Op.FF.FfTemplate.ordinal(), "", Op.ff);
/*   53 */     Op.updateOption(Op.FF.FfDic.ordinal(), "english", Op.ff);
/*   54 */     Op.updateOption(Op.FF.FfW.ordinal(), "600", Op.ff);
/*   55 */     Op.updateOption(Op.FF.FfH.ordinal(), "690", Op.ff);
/*   56 */     Op.updateOption(Op.FF.FfAcross.ordinal(), "10", Op.ff);
/*   57 */     Op.updateOption(Op.FF.FfDown.ordinal(), "10", Op.ff);
/*   58 */     Op.updateOption(Op.FF.FfOptimize.ordinal(), "2", Op.ff);
/*   59 */     Op.updateOption(Op.FF.FfEnforceLink.ordinal(), "false", Op.ff);
/*      */   }
/*      */   
/*   62 */   String freeformHelp = "<div>A <b>FREE-FORM</b> puzzle differs from a standard crossword in the following ways:-</div><ul><li/>It is constructed from a very limited set of words. The Theme dictionaries are usually used for this purpose, and may have as many as several hundred words, or as few as 10 words. Such dictionaries usually follow a specific theme such as Football, Surfing, Great Composers, Botany, or any other topic you may care to name.<p/><li/>It is constructed on a blank grid, with the program inserting black squares as required, so that the end result does not in general exhibit any symmetry.<p/><li/>The degree of interlinking of words is quite low, with less than half of the total letters actually interlinking.<br/><br/></ul><span class='m'>Menu Functions</span><ul><li/><span class='s'>File Menu</span><ul><li/><span>Select a Dictionary</span><br/>When loading a new puzzle into the Build screen, you begin by selecting the dictionary which was used to build the FREE-FORM puzzle which you want to load.<p/><li/><span>Load a Puzzle</span><br/>Then you choose your puzzle from the pool of FREE-FORM puzzles currently available in the selected dictionary.<p/><li/><span>Create a Dictionary</span><br/>The Theme dictionaries used to build these puzzles are usually quite small, and can be typed in quite quickly. This option allows you to create a new Theme dictionary, type in a number of words and clues, and to save it for future use.<p/><li/><span>Edit Dictionary</span><br/>You can return at any time to a Theme Dictionary created by the previous option, and edit it in any way that suits your needs.<p/><li/><span>SaveAs</span><br/>This option allows you to make an exact copy of the current puzzle using a different file name. The copy will be saved in the folder of the dictionary that was used to construct it. Alternatively, if you don't enter a new name for the puzzle, you can change the Puzzle Description, or any of the other descriptive items without changing the puzzle name.<p/><li/><span>Quit Construction</span><br/>Returns you to the Crossword Express opening screen.</ul><li/><span class='s'>Build Menu</span><ul><li/><span>Select a Dictionary</span><br/>Use this option to select the dictionary which you want to use to build the new FREE-FORM puzzle.<p/><li/><span>Start a New Puzzle</span><br/>This option presents you with a dialog into which you can enter a file name for your new puzzle. You can also enter several other pieces of information such as a <b>Puzzle Title, Author</b> and <b>Copyright</b> information.<p/><li/><span>Build Options</span><br/>Use this option to access a Build Options dialog where you can set some rules to be followed by the puzzle building function as it builds your puzzle.<p/><li/><span>Start Building / Stop Building</span><br/>Construction of the puzzle will commence when you select the <b>Start Building</b> option. A series of Free-form puzzles will be built, but only the best one, based on the criteria you specify under Build Options, will be retained, saved to disk, and displayed for you to see. When you are happy with the puzzle currently being displayed, you can stop the construction process by selecting this menu option for a second time. Note that during the construction phase, the text of this option is changed to <b>Stop Building</b></ul><li/><span class='s'>View Menu</span><ul><li/><span>Display Options</span><br/>This leads you to a dialog box in which you can change the colors of various elements within the puzzle, and control the fonts which will be used for the puzzle's text components. You can also decide if printing of the puzzle will be done in black and white or in color.</ul><li/><span class='s'>Tasks Menu</span><ul><li/><span>Print as Crossword Puzzle</span><br/>This will take you to a custom print screen where you can control the details involved with printing your puzzle.<p/><li/><span>Print as Fillin Puzzle</span><br/>The current puzzle can be printed as a FILLIN puzzle.<p/><li/><span>Solve this Puzzle</span><br/>This will take you to a fully interactive solve screen. A useful feature of this function is the fact that any solution characters entered into the puzzle will be printed along with the otherwise blank grid when the puzzle is printed or exported to a graphics file. This allows hint characters to be inserted into puzzles which are to be used as student exercises.<p/><li/><span>Print Gemini Crossword</span><br/>You should only ever attempt to print this style of puzzle using a Free-form crossword generated according to the Gemini criteria as set in the Build Options. The program will print two versions of the puzzle on consecutive pages. One of these versions has all of the  across words in place, while the other contains all of the down words. Such puzzles are especially useful in language education, and ESL teachers will find them indispensable. Full instructions on how to make use of them will be found on the puzzle sheets when they are printed.<p/><li/><span>Export Puzzle as Text</span><br/>Under normal circumstances, the Print function will provide all of the layout flexibility you will need when printing your puzzles. Inevitably of course special cases will arise where you need to intervene in the printing of either the words or the clues to achieve some special effect. To meet this need, a text export feature offers the following choices:-<ul><li/><b>Export Words.</b> Each line of text has the format <b>1. WORD</b><li/><b>Export Clues.</b> Each line of text has the format <b>1. Clue</b><li/><b>Export Words and Clues.</b> Each line of text has the format <b>1. WORD : Clue</b><li/><b>Export Puzzle Grid.</b> The puzzle grid is exported as a simple square or rectangular array of letters.</ul>In addition, you have the choice of exporting the text to a text file located anywhere on your computer's hard drive, or to the System Clipboard from where you can Paste into any Word Processor or Desk Top Publishing application.<p/><li/><span>Delete this Puzzle</span><br/>Use this option to eliminate unwanted FREE-FORM puzzles from your file system.</ul><li/><span class='s'>Help Menu</span><ul><li/><span>Free-form Help</span><br/>Displays the Help screen which you are now reading.</ul></ul></body>";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  154 */   String freeformOptions = "<div>The following options are available for you to adjust before you start building the Free-form puzzle:-</div><li/>If you want to make a number of puzzles all having the same dimensions, simply type that number into the <b>How many puzzles</b> input field, and the number of the first puzzle to be made into the first puzzle field. When you issue the Make command, Crossword Express will make that number of puzzles. The names of the puzzles will be numbers starting from the number you chose for the first puzzle, and increasing by 1 with each new puzzle. Virtually any number of puzzles can be made in a single operation using this feature.<p/><li/>The size and shape of a <b>Free-form</b> Puzzle can specified in two different ways:-<ul><li/>Specify the size of the rectangle which will contain the puzzle:-<ul><li/><b>Cells Across:</b> Insert a value in the range 5 to 50 to specify the number of columns of cells in the finished puzzle.<li/><b>Cells Down:</b> Insert a value in the range 5 to 50 to specify the number of rows of cells in the finished puzzle.</ul><div>These values are treated by the program as maximum values. If it can fit all of the words in your dictionary into a smaller space, them it will do so.</div><p/><li/>Select a <b>Template</b> from a list of available templates. A template enables you to make <b>Free-form</b>Puzzles in shapes of your own choosing, such as a circle or a heart. The Grid Maintenance function provides all of the facilities you will need to design your own templates.</ul><li/>A graphic image contained in any of the file types <b>BMP, GIF, JPG or PNG</b> can be inserted into a Free-form Crossword puzzle to fill all of the spaces not occupied by a puzzle letter. The file to be used for this purpose is selected by means of the <b>Select File</b> button, and may be located anywhere on your hard drive. If you make such a selection, then when you click <b>OK</b> to exit the Options dialog you will see a rectangular matrix of blank cells which matches the size you selected in <b>Cells Across</b> and <b>Cells Down</b>. Issuing a mouse click into these cells will toggle them between being a blank cell, and being a small section of the underlying graphic image. When you subsequently issue the <b>Start Building</b> menu command, Crossword Express will avoid placing a letter into any of those cells which contain a portion of the graphic image. Use this facility to create puzzles in which key portions of the image are clearly visible within the puzzle.<p/><li/>When building a Free-form puzzle, Crossword Express tailors its activities according to a set of Build Criteria which you can select from a set of four criteria. They are:-<ul><li/>Maximise words.<li/>Maximise letters.<li/>Maximise linked letters.<li/>Build a Gemini compatible puzzle. Gemini puzzles are discussed in detail in the Help available from the Free-form Construction screen. In this mode, not only is the number of linked letters maximised, but the numbers of Across and Down words are constrained to be equal.</ul><div>The default optimisation is to maximise linked letters, but you can select either of the others via the combo box provided.</div><p/><li/>The <b>Filter</b> option allows you to enter a Filter which will be applied to the words in your dictionary. Only those words accepted by the filter will be used in constructing the puzzle. The default filter is <b>*</b> which results in all words being accepted. Read <b<Filter Description</b> below for details on how to use the filter.<p/><li/><b>Links per word.</b> Most Free-form crossword puzzle generators produce puzzles in which a number of words, especially those near the edge of the puzzle, have only a single interlinking letter. School teachers using these puzzles for vocabulary revision usually consider this to be a bad feature. By default, Crossword Express also behaves this way, but if you select the <b>Enforce at least two links per word</b> check box, you will find that none of the words in the puzzle will contain less than two interlinking letters.<br/><br/></ul><span class='m'>Filter Description</span><div>The filter consists of a string of standard characters interleaved with <b>wild card</b> characters in a way which will be quite familiar to users of <b>DOS</b> and <b>UNIX.</b><br/><br/>The components of a filter are:-</div><ul><li/><b>Standard Characters.</b>   The characters in a <b>Word</b> must match exactly any corresponding <b> Standard Characters</b> in the <b>Filter</b>.<p/><li/><b>The ? wild card.</b>   A <b>?</b> character at any position in the <b>Filter</b> will match any character in the corresponding position in the <b>Word</b>.<p/><li/><b>The * wild card.</b>   A <b>*</b> character at any position in the <b>Filter</b> will match any number of characters in the <b>Word</b>, including zero characters.<p/><li/><b>The [ABC...XYZ] wild card.</b> A set of square brackets surrounding a string of standard characters will match the corresponding character in <b>Word</b> if that character is included in the string of standard characters. The string of standard characters can include the - character to denote a run of consecutive characters. eg. A-G is equivalent to ABCDEFG.</ul><div>A few examples may help to clarify these rules:-</div><ul><li/><b>T?E </b>will match the words THE, TOE etc.<p/><li/><b>T*E </b>will match the words THE, TOE, TIME, TROUBLE etc.<p/><li/><b>A[CL]E </b>will match the words ACE and ALE, but not AGE etc.<p/><li/><b>*[A-RT-Z] </b>will match any word not ending with S<p/><li/><b>*ABLE </b>will match any words which end with ABLE.<p/><li/><b>*CEI* </b>will match any word containing the sub-string CEI.<p/><li/><b>*C?E[LNP]? </b>will match the words CREPE, ACCENT, ACCEPT, ANCIENT, NICKELS etc.</ul></body>";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   FreeformBuild(JFrame jfCWE) {
/*  239 */     Def.puzzleMode = 6;
/*  240 */     Def.dispSolArray = Boolean.valueOf(true); Def.dispNullCells = Boolean.valueOf(true);
/*  241 */     Def.building = 0;
/*  242 */     Op.ff[Op.FF.FfTemplate.ordinal()] = "";
/*  243 */     makeGrid();
/*  244 */     thePic = "";
/*      */     
/*  246 */     jfFreeform = new JFrame("Freeform Crossword Construction");
/*  247 */     if (Op.getInt(Op.FF.FfH.ordinal(), Op.ff) > Methods.scrH - 200) {
/*  248 */       int diff = Op.getInt(Op.FF.FfH.ordinal(), Op.ff) - Op.getInt(Op.FF.FfW.ordinal(), Op.ff);
/*  249 */       Op.setInt(Op.FF.FfH.ordinal(), Methods.scrH - 200, Op.ff);
/*  250 */       Op.setInt(Op.FF.FfW.ordinal(), Methods.scrH - 200 + diff, Op.ff);
/*      */     } 
/*  252 */     jfFreeform.setSize(Op.getInt(Op.FF.FfW.ordinal(), Op.ff), Op.getInt(Op.FF.FfH.ordinal(), Op.ff));
/*  253 */     int frameX = (jfCWE.getX() + jfFreeform.getWidth() > Methods.scrW) ? (Methods.scrW - jfFreeform.getWidth() - 10) : jfCWE.getX();
/*  254 */     jfFreeform.setLocation(frameX, jfCWE.getY());
/*  255 */     jfFreeform.setLayout((LayoutManager)null);
/*  256 */     jfFreeform.setDefaultCloseOperation(0);
/*  257 */     jfFreeform
/*  258 */       .addComponentListener(new ComponentAdapter() {
/*      */           public void componentResized(ComponentEvent ce) {
/*  260 */             int oldw = Op.getInt(Op.FF.FfW.ordinal(), Op.ff);
/*  261 */             int oldh = Op.getInt(Op.FF.FfH.ordinal(), Op.ff);
/*  262 */             Methods.frameResize(FreeformBuild.jfFreeform, oldw, oldh, 500, 580);
/*  263 */             Op.setInt(Op.FF.FfW.ordinal(), FreeformBuild.jfFreeform.getWidth(), Op.ff);
/*  264 */             Op.setInt(Op.FF.FfH.ordinal(), FreeformBuild.jfFreeform.getHeight(), Op.ff);
/*  265 */             FreeformBuild.restoreFrame(1);
/*      */           }
/*      */         });
/*      */ 
/*      */     
/*  270 */     jfFreeform
/*  271 */       .addWindowListener(new WindowAdapter() {
/*      */           public void windowClosing(WindowEvent we) {
/*  273 */             if (Def.building == 1 || Def.selecting) { FreeformBuild.draw = false; return; }
/*  274 */              Op.saveOptions("freeform.opt", Op.ff);
/*  275 */             CrosswordExpress.jfCWE.setVisible(true);
/*  276 */             FreeformBuild.jfFreeform.dispose();
/*  277 */             Methods.closeHelp();
/*  278 */             Def.puzzleMode = 1; Def.dispNullCells = Boolean.valueOf(false);
/*      */           }
/*      */         });
/*      */     
/*  282 */     Methods.closeHelp();
/*      */ 
/*      */     
/*  285 */     Runnable buildThread = () -> {
/*      */         this.iCount = 0;
/*      */         if (ffMulti) {
/*      */           buildFFmulti();
/*      */         } else {
/*      */           Methods.havePuzzle = buildFreeformPuzzle();
/*      */         } 
/*      */       };
/*  293 */     jl1 = new JLabel(); jfFreeform.add(jl1);
/*  294 */     jl2 = new JLabel(); jfFreeform.add(jl2);
/*      */ 
/*      */     
/*  297 */     menuBar = new JMenuBar();
/*  298 */     menuBar.setBackground(Def.COLOR_MENUBAR);
/*  299 */     jfFreeform.setJMenuBar(menuBar);
/*      */     
/*  301 */     this.menu = new JMenu("File");
/*  302 */     menuBar.add(this.menu);
/*  303 */     this.menuItem = new JMenuItem("Select a Dictionary");
/*  304 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(68, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  305 */     this.menu.add(this.menuItem);
/*  306 */     this.menuItem
/*  307 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             draw = false; return;
/*      */           } 
/*      */           Methods.selectDictionary(jfFreeform, Op.ff[Op.FF.FfDic.ordinal()], 3);
/*      */           Op.ff[Op.FF.FfDic.ordinal()] = Methods.dictionaryName;
/*      */           loadCrossword(Op.ff[Op.FF.FfPuz.ordinal()]);
/*      */           restoreFrame(1);
/*      */         });
/*  316 */     this.menuItem = new JMenuItem("Load a Puzzle");
/*  317 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(76, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  318 */     this.menu.add(this.menuItem);
/*  319 */     this.menuItem
/*  320 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             draw = false; return;
/*      */           }  pp.invalidate();
/*      */           pp.repaint();
/*      */           new Select(jfFreeform, Op.ff[Op.FF.FfDic.ordinal()] + ".dic/", "crossword", Op.ff, Op.FF.FfPuz.ordinal(), false);
/*      */         });
/*  327 */     this.menuItem = new JMenuItem("Create a Dictionary");
/*  328 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(67, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  329 */     this.menu.add(this.menuItem);
/*  330 */     this.menuItem
/*  331 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             draw = false; return;
/*      */           } 
/*      */           DictionaryMtce.createDic(jfFreeform, 1);
/*      */           if (Methods.clickedOK) {
/*      */             DictionaryMtce.editDictionary(jfFreeform, Op.ff[Op.FF.FfDic.ordinal()]);
/*      */             restoreFrame(1);
/*      */           } 
/*      */         });
/*  341 */     this.menuItem = new JMenuItem("Edit Dictionary");
/*  342 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(69, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  343 */     this.menu.add(this.menuItem);
/*  344 */     this.menuItem
/*  345 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             draw = false; return;
/*      */           } 
/*      */           DictionaryMtce.editDictionary(jfFreeform, Op.ff[Op.FF.FfDic.ordinal()]);
/*      */         });
/*  351 */     this.menuItem = new JMenuItem("SaveAs");
/*  352 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(65, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  353 */     this.menu.add(this.menuItem);
/*  354 */     this.menuItem
/*  355 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             draw = false; return;
/*      */           } 
/*      */           Methods.puzzleDescriptionDialog(jfFreeform, Op.ff[Op.FF.FfPuz.ordinal()].substring(0, Op.ff[Op.FF.FfPuz.ordinal()].indexOf(".crossword")), Op.ff[Op.FF.FfDic.ordinal()] + ".dic", ".crossword");
/*      */           if (Methods.clickedOK) {
/*      */             saveCrossword(Op.ff[Op.FF.FfPuz.ordinal()] = Methods.theFileName);
/*      */             restoreFrame(1);
/*      */             Methods.puzzleSaved(jfFreeform, Op.ff[Op.FF.FfDic.ordinal()] + ".dic", Op.ff[Op.FF.FfPuz.ordinal()]);
/*      */           } 
/*      */         });
/*  366 */     this.menuItem = new JMenuItem("Quit Construction");
/*  367 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(81, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  368 */     this.menu.add(this.menuItem);
/*  369 */     this.menuItem
/*  370 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             draw = false; return;
/*      */           } 
/*      */           Op.saveOptions("freeform.opt", Op.ff);
/*      */           CrosswordExpress.jfCWE.setVisible(true);
/*      */           jfFreeform.dispose();
/*      */           Methods.closeHelp();
/*      */           Def.puzzleMode = 1;
/*      */           Def.dispNullCells = Boolean.valueOf(false);
/*      */         });
/*  381 */     this.menu = new JMenu("Build");
/*  382 */     menuBar.add(this.menu);
/*  383 */     this.menuItem = new JMenuItem("Select a Dictionary");
/*  384 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(68, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  385 */     this.menu.add(this.menuItem);
/*  386 */     this.menuItem
/*  387 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             draw = false; return;
/*      */           } 
/*      */           Methods.selectDictionary(jfFreeform, Op.ff[Op.FF.FfDic.ordinal()], 3);
/*      */           Op.ff[Op.FF.FfDic.ordinal()] = Methods.dictionaryName;
/*      */           loadCrossword(Op.ff[Op.FF.FfPuz.ordinal()]);
/*      */           restoreFrame(1);
/*      */         });
/*  396 */     this.menuItem = new JMenuItem("Start a new Puzzle");
/*  397 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(78, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  398 */     this.menu.add(this.menuItem);
/*  399 */     this.menuItem
/*  400 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             draw = false; return;
/*      */           } 
/*      */           Methods.puzzleDescriptionDialog(jfFreeform, Op.ff[Op.FF.FfPuz.ordinal()].substring(0, Op.ff[Op.FF.FfPuz.ordinal()].indexOf(".crossword")), Op.ff[Op.FF.FfDic.ordinal()] + ".dic", ".crossword");
/*      */           if (Methods.clickedOK) {
/*      */             Op.ff[Op.FF.FfPuz.ordinal()] = Methods.theFileName;
/*      */             thePic = "";
/*      */             Op.ff[Op.FF.FfTemplate.ordinal()] = "";
/*      */             makeGrid();
/*      */           } 
/*      */           restoreFrame(1);
/*      */         });
/*  413 */     this.menuItem = new JMenuItem("Build Options");
/*  414 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(79, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  415 */     this.menu.add(this.menuItem);
/*  416 */     this.menuItem
/*  417 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             draw = false; return;
/*      */           }  freeformOptions(); if (Methods.clickedOK) {
/*      */             makeGrid(); if (thePic.length() > 0) {
/*      */               Grid.scratch[0][Grid.ySz - 1] = 2; Grid.scratch[Grid.xSz - 1][0] = 2;
/*      */               Grid.mode[0][Grid.ySz - 1] = 2;
/*      */               Grid.mode[Grid.xSz - 1][0] = 2;
/*      */             } 
/*      */             NodeList.buildNodeList();
/*      */           } else {
/*      */             Methods.clearGrid(Grid.letter);
/*      */           } 
/*      */           restoreFrame(1);
/*      */         });
/*  432 */     this.buildMenuItem = new JMenuItem("Start Building");
/*  433 */     this.buildMenuItem.setAccelerator(KeyStroke.getKeyStroke(66, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  434 */     this.menu.add(this.buildMenuItem);
/*  435 */     this.buildMenuItem
/*  436 */       .addActionListener(ae -> {
/*      */           if (Op.ff[Op.FF.FfDic.ordinal()].length() < 2) {
/*      */             Methods.noDictionary(jfFreeform);
/*      */             
/*      */             return;
/*      */           } 
/*      */           
/*      */           if (Op.ff[Op.FF.FfPuz.ordinal()].length() == 0) {
/*      */             Methods.noName(jfFreeform);
/*      */             
/*      */             return;
/*      */           } 
/*      */           
/*      */           if (Def.building == 0) {
/*      */             this.thread = new Thread(paramRunnable);
/*      */             
/*      */             this.thread.start();
/*      */             
/*      */             Def.building = 1;
/*      */             
/*      */             this.buildMenuItem.setText("Stop Building");
/*      */           } else if (!ffMulti && Def.building == 1) {
/*      */             Def.building = 2;
/*      */           } 
/*      */         });
/*  461 */     this.menu = new JMenu("View");
/*  462 */     menuBar.add(this.menu);
/*  463 */     this.menuItem = new JMenuItem("Display Options");
/*  464 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(89, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  465 */     this.menu.add(this.menuItem);
/*  466 */     this.menuItem
/*  467 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             draw = false;
/*      */             return;
/*      */           } 
/*      */           printOptions(jfFreeform, "Display Options");
/*      */           restoreFrame(1);
/*      */         });
/*  475 */     this.menu = new JMenu("Tasks");
/*  476 */     menuBar.add(this.menu);
/*  477 */     this.menuItem = new JMenuItem("Print as Crossword Puzzle");
/*  478 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(80, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  479 */     this.menu.add(this.menuItem);
/*  480 */     this.menuItem
/*  481 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             draw = false; return;
/*      */           } 
/*      */           CrosswordExpress.toPrint(jfFreeform, Op.cw[Op.CW.CwPuz.ordinal()]);
/*      */         });
/*  487 */     this.menuItem = new JMenuItem("Print as Fillin Puzzle");
/*  488 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(70, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  489 */     this.menu.add(this.menuItem);
/*  490 */     this.menuItem
/*  491 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           
/*      */           CrosswordExpress.toPrint(jfFreeform, Op.ff[Op.FF.FfPuz.ordinal()]);
/*      */           Def.puzzleMode = 13;
/*      */         });
/*  499 */     this.menuItem = new JMenuItem("Solve this Puzzle");
/*  500 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(83, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  501 */     this.menu.add(this.menuItem);
/*  502 */     this.menuItem
/*  503 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           if (Methods.havePuzzle) {
/*      */             CrosswordExpress.transfer(5, jfFreeform);
/*      */           } else {
/*      */             Methods.noPuzzle(jfFreeform, "Solve");
/*      */           } 
/*      */         });
/*  513 */     this.menuItem = new JMenuItem("Print Gemini Crossword");
/*  514 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(71, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  515 */     this.menu.add(this.menuItem);
/*  516 */     this.menuItem
/*  517 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             draw = false; return;
/*      */           } 
/*      */           geminiPrint();
/*      */         });
/*  523 */     this.menuItem = new JMenuItem("Export as Text");
/*  524 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(84, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  525 */     this.menu.add(this.menuItem);
/*  526 */     this.menuItem
/*  527 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             draw = false; return;
/*      */           }  if (Methods.havePuzzle) {
/*      */             NodeList.exportText(jfFreeform, true);
/*      */           } else {
/*      */             Methods.noPuzzle(jfFreeform, "Export");
/*      */           } 
/*      */         });
/*  536 */     this.menuItem = new JMenuItem("Delete this Puzzle");
/*  537 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(90, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  538 */     this.menu.add(this.menuItem);
/*  539 */     this.menuItem
/*  540 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             draw = false;
/*      */             return;
/*      */           } 
/*      */           if (Methods.deleteAPuzzle(jfFreeform, Op.ff[Op.FF.FfPuz.ordinal()], Op.ff[Op.FF.FfDic.ordinal()] + ".dic", pp)) {
/*      */             loadCrossword(Op.ff[Op.FF.FfPuz.ordinal()]);
/*      */             restoreFrame(1);
/*      */           } 
/*      */         });
/*  550 */     this.menu = new JMenu("Help");
/*  551 */     menuBar.add(this.menu);
/*  552 */     this.menuItem = new JMenuItem("Freeform Help");
/*  553 */     this.menu.add(this.menuItem);
/*  554 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(72, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  555 */     this.menuItem
/*  556 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           Methods.cweHelp(jfFreeform, null, "Building Freeform Puzzles", this.freeformHelp);
/*      */         });
/*  562 */     pp = new FreeformPP(0, 37);
/*  563 */     jfFreeform.add(pp);
/*      */     
/*  565 */     pp
/*  566 */       .addMouseListener(new MouseAdapter() {
/*      */           public void mousePressed(MouseEvent e) {
/*  568 */             FreeformBuild.this.processMouse(e);
/*      */           }
/*      */         });
/*      */ 
/*      */     
/*  573 */     pp
/*  574 */       .addMouseMotionListener(new MouseAdapter() {
/*      */           public void mouseMoved(MouseEvent e) {
/*  576 */             if (Def.isMac) {
/*  577 */               FreeformBuild.jfFreeform.setResizable((FreeformBuild.jfFreeform.getWidth() - e.getX() < 15 && FreeformBuild.jfFreeform
/*  578 */                   .getHeight() - e.getY() < 95));
/*      */             }
/*      */           }
/*      */         });
/*      */     
/*  583 */     loadCrossword(Op.ff[Op.FF.FfPuz.ordinal()]);
/*  584 */     Methods.havePuzzle = true;
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  589 */       DataInputStream dataIn = new DataInputStream(new FileInputStream(Op.ff[Op.FF.FfDic.ordinal()] + ".dic/xword.dic"));
/*  590 */       dataIn.read(DictionaryMtce.dicHeader, 0, 128);
/*  591 */       dataIn.close();
/*  592 */     } catch (IOException exc) {}
/*      */     
/*  594 */     restoreFrame(1);
/*      */   }
/*      */   
/*      */   static void restoreFrame(int mode) {
/*  598 */     jfFreeform.setVisible(true);
/*  599 */     Insets insets = jfFreeform.getInsets();
/*  600 */     panelW = jfFreeform.getWidth() - insets.left + insets.right;
/*  601 */     panelH = jfFreeform.getHeight() - insets.top + insets.bottom + 37 + menuBar.getHeight();
/*  602 */     pp.setSize(panelW, panelH);
/*  603 */     jfFreeform.requestFocusInWindow();
/*  604 */     pp.repaint();
/*  605 */     Wait.shortWait(5);
/*  606 */     if (mode == 1) {
/*  607 */       Methods.infoPanel(jl1, jl2, "Build Freeform", "Dictionary : " + Op.ff[Op.FF.FfDic.ordinal()] + "  -|-  Puzzle : " + Op.ff[Op.FF.FfPuz
/*  608 */             .ordinal()], panelW);
/*      */     } else {
/*  610 */       String puzName = ffMulti ? ("" + (startPuz + multiPuzNum)) : Op.ff[Op.FF.FfDic.ordinal()];
/*  611 */       Methods.infoPanel(jl1, jl2, "Build Freeform", "Puzzle " + puzName + ".crossword   " + scoreW + " words : " + scoreC + " letters : " + scoreL + " links", panelW);
/*      */     } 
/*      */   }
/*      */   
/*      */   static void setSizesAndOffsets(int x, int y, int width, int height, int inset, boolean print) {
/*  616 */     int i = (width - inset) / Grid.xSz;
/*  617 */     int j = (height - inset) / Grid.ySz;
/*  618 */     Grid.xCell = Grid.yCell = (i < j) ? i : j;
/*  619 */     Grid.xOrg = print ? (x + (width - Grid.xSz * Grid.xCell) / 2) : 10;
/*  620 */     Grid.yOrg = print ? (y + (height - Grid.ySz * Grid.yCell) / 2) : 10;
/*      */   }
/*      */   
/*      */   private void freeformOptions() {
/*  624 */     String[] optimize = { "  Maximize Words", "  Maximize Letters", "  Maximize Links", "  Gemini Puzzle" };
/*      */ 
/*      */     
/*  627 */     final JDialog jdlgFreeform = new JDialog(jfFreeform, "Freeform Options", true);
/*  628 */     jdlgFreeform.setSize(275, 448);
/*  629 */     jdlgFreeform.setResizable(false);
/*  630 */     jdlgFreeform.setLayout((LayoutManager)null);
/*  631 */     jdlgFreeform.setLocation(jfFreeform.getX(), jfFreeform.getY());
/*      */     
/*  633 */     jdlgFreeform
/*  634 */       .addWindowListener(new WindowAdapter() {
/*      */           public void windowClosing(WindowEvent we) {
/*  636 */             Methods.closeHelp();
/*      */           }
/*      */         });
/*      */     
/*  640 */     Methods.closeHelp();
/*      */     
/*  642 */     final HowManyPuzzles hmp = new HowManyPuzzles(jdlgFreeform, 10, 10, howMany, startPuz, true);
/*  643 */     hmp.jcbVaryDiff.setEnabled(false);
/*      */     
/*  645 */     JPanel jpFFO = new JPanel();
/*  646 */     jpFFO.setLayout((LayoutManager)null);
/*  647 */     jpFFO.setLocation(10, 120);
/*  648 */     jpFFO.setSize(250, 120);
/*  649 */     jpFFO.setOpaque(true);
/*  650 */     jpFFO.setBorder(BorderFactory.createEtchedBorder());
/*  651 */     jdlgFreeform.add(jpFFO);
/*      */     
/*  653 */     JLabel jl = new JLabel("EITHER:");
/*  654 */     jl.setForeground(Def.COLOR_LABEL);
/*  655 */     jl.setSize(55, 20);
/*  656 */     jl.setLocation(10, 10);
/*  657 */     jl.setHorizontalAlignment(2);
/*  658 */     jpFFO.add(jl);
/*      */     
/*  660 */     JLabel jlAcross = new JLabel("Cells Across:");
/*  661 */     jlAcross.setForeground(Def.COLOR_LABEL);
/*  662 */     jlAcross.setSize(100, 20);
/*  663 */     jlAcross.setLocation(85, 10);
/*  664 */     jlAcross.setHorizontalAlignment(2);
/*  665 */     jpFFO.add(jlAcross);
/*      */     
/*  667 */     final JTextField jtfAcross = new JTextField("" + Op.getInt(Op.FF.FfAcross.ordinal(), Op.ff), 15);
/*  668 */     jtfAcross.setSize(60, 20);
/*  669 */     jtfAcross.setLocation(180, 10);
/*  670 */     jtfAcross.selectAll();
/*  671 */     jtfAcross.setHorizontalAlignment(2);
/*  672 */     jpFFO.add(jtfAcross);
/*      */     
/*  674 */     JLabel jlDown = new JLabel("Cells Down:");
/*  675 */     jlDown.setForeground(Def.COLOR_LABEL);
/*  676 */     jlDown.setSize(100, 20);
/*  677 */     jlDown.setLocation(85, 40);
/*  678 */     jlDown.setHorizontalAlignment(2);
/*  679 */     jpFFO.add(jlDown);
/*      */     
/*  681 */     final JTextField jtfDown = new JTextField("" + Op.getInt(Op.FF.FfDown.ordinal(), Op.ff), 15);
/*  682 */     jtfDown.setSize(60, 20);
/*  683 */     jtfDown.setLocation(180, 40);
/*  684 */     jtfDown.selectAll();
/*  685 */     jtfDown.setHorizontalAlignment(2);
/*  686 */     jpFFO.add(jtfDown);
/*      */     
/*  688 */     JLabel jl2 = new JLabel("OR:");
/*  689 */     jl2.setForeground(Def.COLOR_LABEL);
/*  690 */     jl2.setSize(40, 20);
/*  691 */     jl2.setLocation(10, 70);
/*  692 */     jl2.setHorizontalAlignment(2);
/*  693 */     jpFFO.add(jl2);
/*      */     
/*  695 */     final JLabel jlTemplate = new JLabel("");
/*  696 */     jlTemplate.setForeground(Def.COLOR_LABEL);
/*  697 */     jlTemplate.setSize(150, 20);
/*  698 */     jlTemplate.setLocation(50, 94);
/*  699 */     jlTemplate.setHorizontalAlignment(0);
/*  700 */     jpFFO.add(jlTemplate);
/*      */     
/*  702 */     Action doTemplate = new AbstractAction("Select a Template") {
/*      */         public void actionPerformed(ActionEvent e) {
/*  704 */           String oldName = Op.ff[Op.FF.FfTemplate.ordinal()];
/*  705 */           Def.puzzleMode = 2;
/*  706 */           JFileChooser chooser = new JFileChooser(System.getProperty("user.dir") + "/grids");
/*  707 */           chooser.setFileFilter(new FileNameExtensionFilter("Template", new String[] { "template" }));
/*  708 */           File fl = new File(Op.ff[Op.FF.FfTemplate.ordinal()].equalsIgnoreCase("") ? ".template" : Op.ff[Op.FF.FfTemplate.ordinal()]);
/*  709 */           chooser.setSelectedFile(fl);
/*  710 */           chooser.setAccessory(new Preview(chooser));
/*  711 */           if (chooser.showDialog(FreeformBuild.jfFreeform, "Select Template") == 0) {
/*  712 */             Op.ff[Op.FF.FfTemplate.ordinal()] = chooser.getSelectedFile().getName();
/*  713 */             fl = new File("grids/" + Op.ff[Op.FF.FfTemplate.ordinal()]);
/*  714 */             if (!fl.exists()) {
/*  715 */               JOptionPane.showMessageDialog(FreeformBuild.jfFreeform, "There is no file by that name!");
/*  716 */               Op.ff[Op.FF.FfTemplate.ordinal()] = oldName;
/*      */             } 
/*      */           } 
/*  719 */           Def.puzzleMode = 6;
/*  720 */           if (Op.ff[Op.FF.FfTemplate.ordinal()].length() > 0)
/*  721 */             jlTemplate.setText("<html><font color=006644 size=3>" + Op.ff[Op.FF.FfTemplate.ordinal()]); 
/*      */         }
/*      */       };
/*  724 */     JButton jbSelectTemplate = Methods.newButton("doTemplate", doTemplate, 84, 50, 68, 190, 26);
/*  725 */     jpFFO.add(jbSelectTemplate);
/*      */     
/*  727 */     JLabel jlbg = new JLabel("Background Graphic");
/*  728 */     jlbg.setForeground(Def.COLOR_LABEL);
/*  729 */     jlbg.setSize(130, 20);
/*  730 */     jlbg.setLocation(10, 250);
/*  731 */     jlbg.setHorizontalAlignment(2);
/*  732 */     jdlgFreeform.add(jlbg);
/*      */     
/*  734 */     Action doSelectGraphic = new AbstractAction("Select File") {
/*      */         public void actionPerformed(ActionEvent e) {
/*  736 */           JFileChooser chooser = new JFileChooser(System.getProperty("user.dir") + "/");
/*  737 */           chooser.setDialogTitle("Select Puzzle Background File");
/*  738 */           chooser.setFileFilter(new FileNameExtensionFilter("BMP GIF JPG PNG", new String[] { "jpg", "gif", "png", "bmp" }));
/*  739 */           if (chooser.showOpenDialog(null) == 0) {
/*  740 */             FreeformBuild.thePic = chooser.getSelectedFile().getAbsolutePath();
/*      */           }
/*      */         }
/*      */       };
/*  744 */     JButton jbSelectGraphic = Methods.newButton("doSelectGraphic", doSelectGraphic, 83, 150, 250, 110, 26);
/*  745 */     jdlgFreeform.add(jbSelectGraphic);
/*      */     
/*  747 */     JLabel jlOpt = new JLabel("Build Criteria :");
/*  748 */     jlOpt.setForeground(Def.COLOR_LABEL);
/*  749 */     jlOpt.setSize(100, 20);
/*  750 */     jlOpt.setLocation(10, 284);
/*  751 */     jlOpt.setHorizontalAlignment(4);
/*  752 */     jdlgFreeform.add(jlOpt);
/*      */     
/*  754 */     final JComboBox<String> jcbbOpt = new JComboBox<>(optimize);
/*  755 */     jcbbOpt.setSize(140, 23);
/*  756 */     jcbbOpt.setLocation(120, 284);
/*  757 */     jdlgFreeform.add(jcbbOpt);
/*  758 */     jcbbOpt.setBackground(Def.COLOR_BUTTONBG);
/*  759 */     jcbbOpt.setSelectedIndex(Op.getInt(Op.FF.FfOptimize.ordinal(), Op.ff));
/*      */     
/*  761 */     JLabel jlFilter = new JLabel("Filter :");
/*  762 */     jlFilter.setForeground(Def.COLOR_LABEL);
/*  763 */     jlFilter.setSize(100, 25);
/*  764 */     jlFilter.setLocation(10, 316);
/*  765 */     jlFilter.setHorizontalAlignment(4);
/*  766 */     jdlgFreeform.add(jlFilter);
/*      */     
/*  768 */     final JTextField jtfFilter = new JTextField(this.theFilter, 20);
/*  769 */     jtfFilter.setSize(140, 25);
/*  770 */     jtfFilter.setLocation(120, 314);
/*  771 */     jtfFilter.selectAll();
/*  772 */     jtfFilter.setHorizontalAlignment(2);
/*  773 */     jdlgFreeform.add(jtfFilter);
/*      */     
/*  775 */     final JCheckBox jcb = new JCheckBox("Enforce at least two links per word", Op.getBool(Op.FF.FfEnforceLink.ordinal(), Op.ff).booleanValue());
/*  776 */     jcb.setForeground(Def.COLOR_LABEL);
/*  777 */     jcb.setOpaque(false);
/*  778 */     jcb.setSize(250, 20);
/*  779 */     jcb.setLocation(10, 346);
/*  780 */     jdlgFreeform.add(jcb);
/*      */     
/*  782 */     Action doOK = new AbstractAction("OK") {
/*      */         public void actionPerformed(ActionEvent e) {
/*  784 */           Methods.clickedOK = true;
/*  785 */           FreeformBuild.this.theFilter = jtfFilter.getText().toUpperCase();
/*  786 */           Op.setInt(Op.FF.FfOptimize.ordinal(), jcbbOpt.getSelectedIndex(), Op.ff);
/*  787 */           Op.setInt(Op.FF.FfAcross.ordinal(), Integer.parseInt(jtfAcross.getText()), Op.ff);
/*  788 */           Op.setInt(Op.FF.FfDown.ordinal(), Integer.parseInt(jtfDown.getText()), Op.ff);
/*  789 */           if (Op.getInt(Op.FF.FfAcross.ordinal(), Op.ff) < 5) Op.setInt(Op.FF.FfAcross.ordinal(), 5, Op.ff); 
/*  790 */           if (Op.getInt(Op.FF.FfAcross.ordinal(), Op.ff) > 50) Op.setInt(Op.FF.FfAcross.ordinal(), 50, Op.ff); 
/*  791 */           if (Op.getInt(Op.FF.FfDown.ordinal(), Op.ff) < 5) Op.setInt(Op.FF.FfDown.ordinal(), 5, Op.ff); 
/*  792 */           if (Op.getInt(Op.FF.FfDown.ordinal(), Op.ff) > 50) Op.setInt(Op.FF.FfDown.ordinal(), 50, Op.ff); 
/*  793 */           FreeformBuild.howMany = Integer.parseInt(hmp.jtfHowMany.getText());
/*  794 */           FreeformBuild.startPuz = Integer.parseInt(hmp.jtfStartPuz.getText());
/*  795 */           FreeformBuild.ffMulti = (FreeformBuild.howMany > 1);
/*  796 */           Op.setBool(Op.FF.FfEnforceLink.ordinal(), Boolean.valueOf(jcb.isSelected()), Op.ff);
/*  797 */           if (FreeformBuild.thePic.length() > 0) {
/*  798 */             Op.ff[Op.FF.FfTemplate.ordinal()] = "";
/*      */           } else {
/*  800 */             Grid.loadGrid(Op.ff[Op.FF.FfTemplate.ordinal()]);
/*  801 */           }  Methods.closeHelp();
/*  802 */           jdlgFreeform.dispose();
/*      */         }
/*      */       };
/*  805 */     JButton jbOK = Methods.newButton("doOK", doOK, 79, 10, 374, 90, 26);
/*  806 */     jdlgFreeform.add(jbOK);
/*      */     
/*  808 */     Action doCancel = new AbstractAction("Cancel") {
/*      */         public void actionPerformed(ActionEvent e) {
/*  810 */           Methods.clickedOK = false;
/*  811 */           jdlgFreeform.dispose();
/*  812 */           Methods.closeHelp();
/*      */         }
/*      */       };
/*  815 */     JButton jbCancel = Methods.newButton("doCancel", doCancel, 67, 10, 409, 90, 26);
/*  816 */     jdlgFreeform.add(jbCancel);
/*      */     
/*  818 */     Action doHelp = new AbstractAction("<html><font size=6 color=BB0000 face=Serif>Help ", new ImageIcon("graphics/help.png")) {
/*      */         public void actionPerformed(ActionEvent e) {
/*  820 */           Methods.cweHelp(null, jdlgFreeform, "Freeform Puzzle Options", FreeformBuild.this.freeformOptions);
/*      */         }
/*      */       };
/*  823 */     JButton jbHelp = Methods.newButton("doHelp", doHelp, 72, 110, 374, 150, 61);
/*  824 */     jdlgFreeform.add(jbHelp);
/*      */     
/*  826 */     jdlgFreeform.getRootPane().setDefaultButton(jbOK);
/*  827 */     Methods.setDialogSize(jdlgFreeform, 270, 448);
/*      */   }
/*      */   
/*      */   static void printOptions(JFrame jf, String type) {
/*  831 */     String[] colorLabel = { "Cell Color", "Grid Line Color", "Letter Color", "ID Number Color", "Shadow Color", "Pattern Color", "Error Color", "Clue Color", "Gemini A Color", "Gemini B Color", "Reverse Arrow Color" };
/*  832 */     int[] colorInt = { Op.CW.CwCellC.ordinal(), Op.CW.CwGridC.ordinal(), Op.CW.CwLettersC.ordinal(), Op.CW.CwIDC.ordinal(), Op.CW.CwShadowC.ordinal(), Op.CW.CwPatternC.ordinal(), Op.CW.CwErrorC.ordinal(), Op.CW.CwClueC.ordinal(), Op.CW.CwGeminiA.ordinal(), Op.CW.CwGeminiB.ordinal(), Op.CW.CwReverseArrow.ordinal() };
/*  833 */     String[] fontLabel = { "Select Puzzle Font", "Select ID Font", "Select Clue Font" };
/*  834 */     int[] fontInt = { Op.CW.CwFont.ordinal(), Op.CW.CwIDFont.ordinal(), Op.CW.CwClueFont.ordinal() };
/*  835 */     String[] checkLabel = { "PPrint Puzzle with color.", "SPrint Solution with color.", "AFrench Puzzles : Across Clues use letter IDs", "DFrench Puzzles : Down Clues use letter IDs", "IInsert word separators", "NInclude IDs in Solution" };
/*  836 */     int[] checkInt = { Op.CW.CwPuzC.ordinal(), Op.CW.CwSolC.ordinal(), Op.CW.CwFrenchAcrossId.ordinal(), Op.CW.CwFrenchDownId.ordinal(), Op.CW.CwCrosswordSep.ordinal(), Op.CW.CwIdInSol.ordinal() };
/*  837 */     Methods.stdPrintOptions(jf, "Crossword " + type, Op.cw, colorLabel, colorInt, fontLabel, fontInt, checkLabel, checkInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void saveCrossword(String crosswordName) {
/*      */     try {
/*  846 */       DataOutputStream dataOut = new DataOutputStream(new FileOutputStream(Op.ff[Op.FF.FfDic.ordinal()] + ".dic/" + crosswordName));
/*  847 */       dataOut.writeInt(Grid.xSz);
/*  848 */       dataOut.writeInt(Grid.ySz);
/*  849 */       dataOut.writeByte(Methods.noReveal);
/*  850 */       dataOut.writeByte(Methods.noErrors);
/*  851 */       for (int k = 0; k < 54; k++)
/*  852 */         dataOut.writeByte(0); 
/*  853 */       for (int j = 0; j < Grid.ySz; j++) {
/*  854 */         for (int m = 0; m < Grid.xSz; m++) {
/*  855 */           dataOut.writeInt(Grid.mode[m][j]);
/*  856 */           dataOut.writeInt(Grid.letter[m][j]);
/*  857 */           dataOut.writeInt(Grid.sol[m][j]);
/*  858 */           dataOut.writeInt(Grid.color[m][j]);
/*      */         } 
/*  860 */       }  dataOut.writeUTF(Methods.puzzleTitle);
/*  861 */       dataOut.writeUTF(Methods.author);
/*  862 */       dataOut.writeUTF(Methods.copyright);
/*  863 */       dataOut.writeUTF(Methods.puzzleNumber);
/*  864 */       dataOut.writeUTF(Methods.puzzleNotes);
/*      */       
/*  866 */       for (int i = 0; i < NodeList.nodeListLength; i++) {
/*  867 */         dataOut.writeUTF((NodeList.nodeList[i]).word);
/*  868 */         dataOut.writeUTF((NodeList.nodeList[i]).clue);
/*      */       } 
/*  870 */       dataOut.close();
/*      */     }
/*  872 */     catch (IOException exc) {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void loadCrossword(String crosswordName) {
/*  879 */     Op.ff[Op.FF.FfDic.ordinal()] = Methods.confirmDictionary(Op.ff[Op.FF.FfDic.ordinal()] + ".dic", true);
/*      */ 
/*      */     
/*  882 */     try { File fl = new File(Op.ff[Op.FF.FfDic.ordinal()] + ".dic/" + crosswordName);
/*  883 */       if (!fl.exists()) {
/*  884 */         fl = new File(Op.ff[Op.FF.FfDic.ordinal()] + ".dic/");
/*  885 */         String[] s = fl.list(); int k;
/*  886 */         for (k = 0; k < s.length && (
/*  887 */           s[k].lastIndexOf(".crossword") == -1 || s[k].charAt(0) == '.'); k++);
/*      */         
/*  889 */         if (k == s.length) { makeGrid(); return; }
/*  890 */          crosswordName = s[k];
/*  891 */         Op.ff[Op.FF.FfPuz.ordinal()] = crosswordName;
/*      */       } 
/*      */ 
/*      */       
/*  895 */       DataInputStream dataIn = new DataInputStream(new FileInputStream(Op.ff[Op.FF.FfDic.ordinal()] + ".dic/" + crosswordName));
/*      */       
/*  897 */       Grid.xSz = dataIn.readInt();
/*  898 */       Grid.ySz = dataIn.readInt();
/*  899 */       Methods.noReveal = dataIn.readByte();
/*  900 */       Methods.noErrors = dataIn.readByte(); int i;
/*  901 */       for (i = 0; i < 54; i++)
/*  902 */         dataIn.readByte(); 
/*  903 */       for (int j = 0; j < Grid.ySz; j++) {
/*  904 */         for (i = 0; i < Grid.xSz; i++) {
/*  905 */           Grid.mode[i][j] = dataIn.readInt();
/*  906 */           Grid.letter[i][j] = dataIn.readInt();
/*  907 */           Grid.sol[i][j] = dataIn.readInt();
/*  908 */           Grid.color[i][j] = dataIn.readInt();
/*      */         } 
/*  910 */       }  Methods.puzzleTitle = dataIn.readUTF();
/*  911 */       Methods.author = dataIn.readUTF();
/*  912 */       Methods.copyright = dataIn.readUTF();
/*  913 */       Methods.puzzleNumber = dataIn.readUTF();
/*  914 */       Methods.puzzleNotes = dataIn.readUTF();
/*      */       
/*  916 */       NodeList.buildNodeList();
/*      */       
/*  918 */       for (i = 0; i < NodeList.nodeListLength; i++) {
/*  919 */         (NodeList.nodeList[i]).word = dataIn.readUTF();
/*  920 */         (NodeList.nodeList[i]).clue = dataIn.readUTF();
/*  921 */         if ((NodeList.nodeList[i]).clue.length() < 2)
/*  922 */           (NodeList.nodeList[i]).clue = "No clue"; 
/*      */       } 
/*  924 */       dataIn.close(); }
/*      */     
/*  926 */     catch (IOException exc) { return; }
/*  927 */      Methods.havePuzzle = true;
/*      */     
/*  929 */     Grid.xCur = (NodeList.nodeList[0]).x;
/*  930 */     Grid.yCur = (NodeList.nodeList[0]).y;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void drawCrossword(Graphics2D g2) {
/*  937 */     Stroke normalStroke = new BasicStroke((Grid.xCell >= 20) ? (Grid.xCell / 20) : 1.0F, 2, 0);
/*  938 */     Stroke wideStroke = new BasicStroke((Grid.xCell >= 20) ? (Grid.xCell / 10) : 2.0F, 0, 0);
/*  939 */     g2.setStroke(normalStroke);
/*      */     
/*  941 */     RenderingHints rh = g2.getRenderingHints();
/*  942 */     rh.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
/*  943 */     rh.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
/*  944 */     g2.setRenderingHints(rh);
/*      */ 
/*      */     
/*  947 */     if (thePic.length() > 0) {
/*  948 */       try { BufferedImage img = ImageIO.read(new File(thePic));
/*  949 */         g2.drawImage(img, Grid.xOrg, Grid.yOrg, Grid.xCell * Grid.xSz + Grid.xCell / 6, Grid.yCell * Grid.ySz + Grid.yCell / 6, null);
/*  950 */         g2.drawRect(Grid.xOrg, Grid.yOrg, Grid.xCell * Grid.xSz + Grid.xCell / 6, Grid.yCell * Grid.ySz + Grid.yCell / 6); }
/*      */       
/*  952 */       catch (IOException e) {}
/*      */     }
/*      */     int j;
/*  955 */     for (j = 0; j < Grid.ySz; j++) {
/*  956 */       for (int i = 0; i < Grid.xSz; i++) {
/*  957 */         if (Grid.mode[i][j] != 2) {
/*  958 */           int drawI = Grid.RorL(i);
/*  959 */           if (Grid.mode[i][j] != 1) {
/*  960 */             g2.setColor(new Color(Op.getColorInt(Op.CW.CwShadowC.ordinal(), Op.cw)));
/*  961 */             g2.fillRect(Grid.xOrg + drawI * Grid.xCell + ((Grid.xCell / 6 > 1) ? (Grid.xCell / 6) : 1), Grid.yOrg + j * Grid.yCell + ((Grid.yCell / 6 > 1) ? (Grid.yCell / 6) : 1), Grid.xCell, Grid.yCell);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*  966 */     for (j = 0; j < Grid.ySz; j++) {
/*  967 */       for (int i = 0; i < Grid.xSz; i++) {
/*  968 */         if (Grid.mode[i][j] != 2) {
/*  969 */           int drawI = Grid.RorL(i);
/*  970 */           if (Grid.mode[i][j] != 1) {
/*  971 */             int theColor; if (Def.dispWithColor.booleanValue()) {
/*  972 */               if (Grid.color[i][j] != 16777215) {
/*  973 */                 theColor = Grid.color[i][j];
/*      */               } else {
/*  975 */                 theColor = Op.getColorInt(Op.CW.CwCellC.ordinal(), Op.cw);
/*      */               } 
/*      */             } else {
/*  978 */               theColor = 16777215;
/*  979 */             }  g2.setColor(new Color(theColor));
/*  980 */             g2.fillRect(Grid.xOrg + drawI * Grid.xCell, Grid.yOrg + j * Grid.yCell, Grid.xCell, Grid.yCell);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*  985 */     g2.setStroke(normalStroke);
/*  986 */     g2.setColor(Def.dispWithColor.booleanValue() ? new Color(Op.getColorInt(Op.CW.CwGridC.ordinal(), Op.cw)) : Def.COLOR_BLACK);
/*  987 */     for (j = 0; j < Grid.ySz; j++) {
/*  988 */       for (int i = 0; i < Grid.xSz; i++) {
/*  989 */         if (Grid.mode[i][j] != 2 && Grid.mode[i][j] != 1) {
/*  990 */           int drawI = Grid.RorL(i);
/*  991 */           g2.drawRect(Grid.xOrg + drawI * Grid.xCell, Grid.yOrg + j * Grid.yCell, Grid.xCell, Grid.yCell);
/*      */         } 
/*      */       } 
/*  994 */     }  g2.setFont(new Font(Op.cw[Op.CW.CwFont.ordinal()], 0, 8 * Grid.yCell / 10));
/*  995 */     FontMetrics fm = g2.getFontMetrics();
/*  996 */     g2.setColor(Def.dispWithColor.booleanValue() ? new Color(Op.getColorInt(Op.CW.CwLettersC.ordinal(), Op.cw)) : Def.COLOR_BLACK);
/*  997 */     if (geminiMode) {
/*  998 */       for (j = 0; j < Grid.ySz; j++) {
/*  999 */         for (int i = 0; i < Grid.xSz; i++) {
/* 1000 */           if (Grid.sig[i][j] == 1) {
/* 1001 */             char ch = (char)Grid.letter[i][j];
/* 1002 */             if (ch != '\000') {
/* 1003 */               int w = fm.stringWidth("" + ch);
/* 1004 */               int drawI = Grid.RorL(i);
/* 1005 */               g2.drawString("" + ch, Grid.xOrg + drawI * Grid.xCell + (Grid.xCell - w) / 2, Grid.yOrg + j * Grid.yCell + 9 * Grid.yCell / 10);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/* 1010 */     } else if (Def.dispSolArray.booleanValue()) {
/* 1011 */       for (j = 0; j < Grid.ySz; j++) {
/* 1012 */         for (int i = 0; i < Grid.xSz; i++) {
/* 1013 */           char ch = (char)Grid.letter[i][j];
/* 1014 */           if (ch != '\000') {
/* 1015 */             int w = fm.stringWidth("" + ch);
/* 1016 */             int drawI = Grid.RorL(i);
/* 1017 */             g2.drawString("" + ch, Grid.xOrg + drawI * Grid.xCell + (Grid.xCell - w) / 2, Grid.yOrg + j * Grid.yCell + 9 * Grid.yCell / 10);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } else {
/* 1022 */       for (j = 0; j < Grid.ySz; j++) {
/* 1023 */         for (int i = 0; i < Grid.xSz; i++) {
/* 1024 */           char ch = (char)Grid.sol[i][j];
/* 1025 */           if (ch != '\000') {
/* 1026 */             int w = fm.stringWidth("" + ch);
/* 1027 */             int drawI = Grid.RorL(i);
/* 1028 */             g2.drawString("" + ch, Grid.xOrg + drawI * Grid.xCell + (Grid.xCell - w) / 2, Grid.yOrg + j * Grid.yCell + 9 * Grid.yCell / 10);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1034 */     g2.setStroke(wideStroke);
/* 1035 */     Grid.drawBars(g2);
/*      */ 
/*      */     
/* 1038 */     if (Def.puzzleMode != 13) {
/* 1039 */       g2.setFont(new Font(Op.cw[Op.CW.CwIDFont.ordinal()], 0, Grid.yCell / 3));
/* 1040 */       g2.setColor(Def.dispWithColor.booleanValue() ? new Color(Op.getColorInt(Op.CW.CwIDC.ordinal(), Op.cw)) : Def.COLOR_BLACK);
/* 1041 */       fm = g2.getFontMetrics();
/* 1042 */       if (draw)
/* 1043 */         for (int i = 0; NodeList.nodeList[i] != null; i++) {
/* 1044 */           int drawI = Grid.RorL((NodeList.nodeList[i]).x);
/* 1045 */           int xCoord = Grid.xOrg + drawI * Grid.xCell + ((DictionaryMtce.dicHeader[DictionaryMtce.R2L_WORD] == 1) ? (Grid.xCell - fm.stringWidth("" + (NodeList.nodeList[i]).id) - 1) : ((Grid.xCell / 20 > 1) ? (Grid.xCell / 20) : 1));
/*      */           
/* 1047 */           g2.drawString("" + (NodeList.nodeList[i]).id, xCoord, Grid.yOrg + (NodeList.nodeList[i]).y * Grid.yCell + fm.getAscent());
/*      */         }  
/* 1049 */       draw = true;
/*      */     } 
/*      */     
/* 1052 */     g2.setStroke(new BasicStroke(1.0F));
/*      */   }
/*      */   
/*      */   static void printPuz(Graphics2D g2, int left, int top, int width, int height, boolean print) {
/* 1056 */     loadCrossword(Op.ff[Op.FF.FfPuz.ordinal()]);
/* 1057 */     setSizesAndOffsets(left, top, width, height, 0, print);
/* 1058 */     Def.dispWithColor = Op.getBool(Op.CW.CwPuzC.ordinal(), Op.cw);
/* 1059 */     Def.dispSolArray = Boolean.valueOf(false);
/* 1060 */     drawCrossword(g2);
/* 1061 */     Def.dispSolArray = Boolean.valueOf(true);
/* 1062 */     Def.dispWithColor = Boolean.valueOf(true);
/*      */   }
/*      */   
/*      */   static void printSol(Graphics2D g2, int left, int top, int width, int height, String solutionPuzzle, boolean print) {
/* 1066 */     loadCrossword(solutionPuzzle);
/* 1067 */     setSizesAndOffsets(left, top, width, height, 0, print);
/* 1068 */     Def.dispGuideDigits = Boolean.valueOf(false);
/* 1069 */     Def.dispWithColor = Op.getBool(Op.CW.CwSolC.ordinal(), Op.cw);
/* 1070 */     Def.dispSolArray = Boolean.valueOf(true);
/* 1071 */     drawCrossword(g2);
/* 1072 */     Def.dispSolArray = Boolean.valueOf(false);
/* 1073 */     Def.dispWithColor = Boolean.valueOf(true);
/* 1074 */     Def.dispGuideDigits = Boolean.valueOf(true);
/* 1075 */     loadCrossword(Op.ff[Op.FF.FfPuz.ordinal()]);
/*      */   }
/*      */   
/*      */   static void printSolTitle(Graphics2D g2, int left, int top, int width, int height, String solutionPuzzle) {
/* 1079 */     loadCrossword(solutionPuzzle);
/* 1080 */     Print.outputTextItem(g2, left, top, width, height, "SansSerif", 0, Methods.puzzleTitle);
/* 1081 */     loadCrossword(Op.ff[Op.FF.FfPuz.ordinal()]);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static void makeGrid() {
/* 1087 */     Methods.havePuzzle = false;
/* 1088 */     Grid.clearGrid();
/*      */     
/* 1090 */     if (Op.ff[Op.FF.FfTemplate.ordinal()].length() > 0 && Op.ff[Op.FF.FfTemplate.ordinal()].charAt(0) != '.') {
/* 1091 */       Grid.loadGrid(Op.ff[Op.FF.FfTemplate.ordinal()]);
/* 1092 */       for (int j = 0; j < Grid.ySz; j++) {
/* 1093 */         for (int i = 0; i < Grid.xSz; i++)
/* 1094 */           Grid.scratch[i][j] = Grid.mode[i][j]; 
/* 1095 */       }  Op.setInt(Op.FF.FfAcross.ordinal(), Grid.xSz, Op.ff); Op.setInt(Op.FF.FfDown.ordinal(), Grid.ySz, Op.ff);
/*      */     } else {
/*      */       
/* 1098 */       Grid.clearGrid();
/* 1099 */     }  Grid.xSz = Op.getInt(Op.FF.FfAcross.ordinal(), Op.ff); Grid.ySz = Op.getInt(Op.FF.FfDown.ordinal(), Op.ff);
/* 1100 */     NodeList.buildNodeList();
/*      */   }
/*      */   
/*      */   boolean loadDictionary(String theFilter) {
/* 1104 */     int numWords = 200;
/* 1105 */     int[] mask = new int[numWords];
/*      */ 
/*      */     
/* 1108 */     Random r = new Random();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1114 */       DataInputStream dataIn = new DataInputStream(new FileInputStream(Op.ff[Op.FF.FfDic.ordinal()] + ".dic/xword.dic"));
/*      */       
/* 1116 */       for (int m = 0; m < 128; ) { dataIn.readByte(); m++; }
/* 1117 */        for (this.wCount = 0; dataIn.available() > 2; ) {
/* 1118 */         int lock = dataIn.readInt();
/* 1119 */         String word = dataIn.readUTF();
/* 1120 */         if (lock != 255 && WordTools.filter(word, theFilter))
/* 1121 */           this.wCount++; 
/* 1122 */         dataIn.readUTF();
/*      */       } 
/* 1124 */       dataIn.close();
/*      */     }
/* 1126 */     catch (IOException exc) {}
/*      */     
/* 1128 */     if (this.wCount == 0) {
/* 1129 */       return false;
/*      */     }
/* 1131 */     if (this.wCount <= numWords) {
/* 1132 */       for (int m = 0; m < this.wCount; mask[m] = m++);
/*      */     } else {
/* 1134 */       int m; for (m = 0; m < numWords; ) {
/*      */         while (true) {
/* 1136 */           int n = r.nextInt(this.wCount); int i1;
/* 1137 */           for (i1 = 0; i1 < m && 
/* 1138 */             n != mask[i1]; i1++);
/*      */           
/* 1140 */           if (i1 == m) {
/* 1141 */             mask[m] = n; break;
/*      */           } 
/*      */         } 
/*      */         m++;
/*      */       } 
/* 1146 */       for (m = 0; m < 255; m++) {
/* 1147 */         for (int n = m + 1; n < numWords; n++) {
/* 1148 */           if (mask[m] > mask[n])
/* 1149 */           { int i1 = mask[m];
/* 1150 */             mask[m] = mask[n];
/* 1151 */             mask[n] = i1; } 
/*      */         } 
/* 1153 */       }  this.wCount = numWords;
/*      */     } 
/*      */     
/* 1156 */     this.busy = new int[this.wCount];
/* 1157 */     this.chWord = new char[this.wCount][50];
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1162 */       DataInputStream dataIn = new DataInputStream(new FileInputStream(Op.ff[Op.FF.FfDic.ordinal()] + ".dic/xword.dic"));
/*      */       
/* 1164 */       for (byte b = 0; b < ''; ) { dataIn.readByte(); b++; }
/* 1165 */        for (int m = 0; dataIn.available() > 2; ) {
/* 1166 */         int lock = dataIn.readInt();
/* 1167 */         String word = dataIn.readUTF();
/* 1168 */         if (lock != 255 && WordTools.filter(word, theFilter)) {
/* 1169 */           if (m == mask[this.wCount]) {
/* 1170 */             this.chWord[this.wCount++] = word.toCharArray();
/* 1171 */             this.cCount += word.length();
/*      */           } 
/* 1173 */           m++;
/* 1174 */           if (this.wCount == numWords)
/*      */             break; 
/*      */         } 
/* 1177 */         dataIn.readUTF();
/*      */       } 
/* 1179 */       dataIn.close();
/*      */     }
/* 1181 */     catch (IOException exc) {}
/*      */     
/* 1183 */     this.wChar = new char[this.cCount];
/* 1184 */     this.wNum = new int[this.cCount];
/* 1185 */     this.wPos = new int[this.cCount];
/* 1186 */     this.wNeg = new int[this.cCount];
/*      */     
/*      */     int i, k;
/* 1189 */     for (k = i = 0; i < this.wCount; i++) {
/* 1190 */       int len = (this.chWord[i]).length;
/* 1191 */       for (int m = 0; m < len; m++) {
/* 1192 */         this.wChar[k] = this.chWord[i][m];
/* 1193 */         this.wNum[k] = i;
/* 1194 */         this.wPos[k] = m;
/* 1195 */         this.wNeg[k] = len - m - 1;
/* 1196 */         k++;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1201 */     for (i = 0; i < this.cCount - 1; i++) {
/* 1202 */       for (int m = i + 1; m < this.cCount; m++) {
/* 1203 */         if (this.wChar[i] > this.wChar[m]) {
/* 1204 */           char ch = this.wChar[i]; this.wChar[i] = this.wChar[m]; this.wChar[m] = ch;
/* 1205 */           k = this.wNum[i]; this.wNum[i] = this.wNum[m]; this.wNum[m] = k;
/* 1206 */           k = this.wPos[i]; this.wPos[i] = this.wPos[m]; this.wPos[m] = k;
/* 1207 */           k = this.wNeg[i]; this.wNeg[i] = this.wNeg[m]; this.wNeg[m] = k;
/*      */         } 
/*      */       } 
/*      */     } 
/* 1211 */     this.cIndex[0] = this.wChar[0];
/* 1212 */     this.iIndex[0] = 0; int j;
/* 1213 */     for (i = j = 1; i < this.cCount; i++) {
/* 1214 */       if (this.wChar[i] != this.cIndex[j - 1]) {
/* 1215 */         this.cIndex[j] = this.wChar[i];
/* 1216 */         this.iIndex[j++] = i;
/*      */       } 
/* 1218 */     }  this.cIndex[j] = this.wChar[i - 1];
/* 1219 */     this.iIndex[j] = i;
/* 1220 */     this.iIndex[j + 1] = 0;
/* 1221 */     return true;
/*      */   }
/*      */   
/*      */   boolean displayGrid() {
/* 1225 */     int[] wordsDir = new int[2];
/*      */     
/*      */     int jF;
/* 1228 */     for (jF = 0;; jF++) {
/* 1229 */       int i; for (i = 0; i < Op.getInt(Op.FF.FfAcross.ordinal(), Op.ff) && 
/* 1230 */         Grid.copy[i][jF] <= 4 && Grid.scratch[i][jF] != 2; i++);
/* 1231 */       if (i < Op.getInt(Op.FF.FfAcross.ordinal(), Op.ff))
/*      */         break; 
/*      */     }  int iF;
/* 1234 */     for (iF = 0;; iF++) {
/* 1235 */       int j; for (j = 0; j < Op.getInt(Op.FF.FfDown.ordinal(), Op.ff) && 
/* 1236 */         Grid.copy[iF][j] <= 4 && Grid.scratch[iF][j] != 2; j++);
/* 1237 */       if (j < Op.getInt(Op.FF.FfDown.ordinal(), Op.ff))
/*      */         break; 
/*      */     }  int jL;
/* 1240 */     for (jL = Op.getInt(Op.FF.FfDown.ordinal(), Op.ff) - 1;; jL--) {
/* 1241 */       byte b1; for (b1 = 0; b1 < Op.getInt(Op.FF.FfAcross.ordinal(), Op.ff) && 
/* 1242 */         Grid.copy[b1][jL] <= 4 && Grid.scratch[b1][jL] != 2; b1++);
/* 1243 */       if (b1 < Op.getInt(Op.FF.FfAcross.ordinal(), Op.ff))
/*      */         break; 
/*      */     }  int iL;
/* 1246 */     for (iL = Op.getInt(Op.FF.FfAcross.ordinal(), Op.ff) - 1;; iL--) {
/* 1247 */       byte b1; for (b1 = 0; b1 < Op.getInt(Op.FF.FfDown.ordinal(), Op.ff) && 
/* 1248 */         Grid.copy[iL][b1] <= 4 && Grid.scratch[iL][b1] != 2; b1++);
/* 1249 */       if (b1 < Op.getInt(Op.FF.FfDown.ordinal(), Op.ff))
/*      */         break; 
/* 1251 */     }  Grid.xSz = iL - iF + 1;
/* 1252 */     Grid.ySz = jL - jF + 1;
/*      */     byte b;
/* 1254 */     for (b = 0; b < Grid.ySz; b++) {
/* 1255 */       for (byte b1 = 0; b1 < Grid.xSz; b1++) {
/* 1256 */         if (Grid.copy[iF + b1][jF + b] > 4)
/* 1257 */         { Grid.letter[b1][b] = Grid.copy[iF + b1][jF + b];
/* 1258 */           Grid.mode[b1][b] = 0; }
/*      */         else
/*      */         
/* 1261 */         { Grid.letter[b1][b] = 0;
/* 1262 */           Grid.mode[b1][b] = Grid.scratch[iF + b1][jF + b];
/* 1263 */           if (Grid.mode[b1][b] == 0) Grid.mode[b1][b] = 1;  } 
/*      */       } 
/* 1265 */     }  NodeList.buildNodeList();
/* 1266 */     boolean ret = false;
/*      */     
/* 1268 */     if (enforceLinkMode) {
/* 1269 */       Methods.clearGrid(Grid.sig); byte b1;
/* 1270 */       for (b1 = 0; b1 < NodeList.nodeListLength; b1++) {
/* 1271 */         for (b = 0; b < (NodeList.nodeList[b1]).length; b++) {
/* 1272 */           if (Grid.letter[((NodeList.nodeList[b1]).cellLoc[b]).x][((NodeList.nodeList[b1]).cellLoc[b]).y] > 0)
/* 1273 */             Grid.sig[((NodeList.nodeList[b1]).cellLoc[b]).x][((NodeList.nodeList[b1]).cellLoc[b]).y] = Grid.sig[((NodeList.nodeList[b1]).cellLoc[b]).x][((NodeList.nodeList[b1]).cellLoc[b]).y] + 1; 
/*      */         } 
/* 1275 */       }  for (boolean doagain = true; doagain; ) {
/* 1276 */         doagain = false;
/* 1277 */         for (b1 = 0; b1 < NodeList.nodeListLength; b1++) {
/* 1278 */           int counter; for (counter = b = 0; b < (NodeList.nodeList[b1]).length; b++) {
/* 1279 */             if (Grid.sig[((NodeList.nodeList[b1]).cellLoc[b]).x][((NodeList.nodeList[b1]).cellLoc[b]).y] == 2)
/* 1280 */               counter++; 
/* 1281 */           }  if (counter == 1) {
/* 1282 */             doagain = true;
/* 1283 */             for (b = 0; b < (NodeList.nodeList[b1]).length; b++) {
/* 1284 */               if (Grid.sig[((NodeList.nodeList[b1]).cellLoc[b]).x][((NodeList.nodeList[b1]).cellLoc[b]).y] == 2) {
/* 1285 */                 Grid.sig[((NodeList.nodeList[b1]).cellLoc[b]).x][((NodeList.nodeList[b1]).cellLoc[b]).y] = 1;
/* 1286 */                 scoreL--;
/*      */               } else {
/*      */                 
/* 1289 */                 Grid.sig[((NodeList.nodeList[b1]).cellLoc[b]).x][((NodeList.nodeList[b1]).cellLoc[b]).y] = 0;
/* 1290 */                 Grid.letter[((NodeList.nodeList[b1]).cellLoc[b]).x][((NodeList.nodeList[b1]).cellLoc[b]).y] = 0;
/* 1291 */                 Grid.mode[((NodeList.nodeList[b1]).cellLoc[b]).x][((NodeList.nodeList[b1]).cellLoc[b]).y] = 1;
/* 1292 */                 scoreC--;
/*      */               } 
/* 1294 */             }  scoreW--;
/*      */           } 
/*      */         } 
/*      */       } 
/* 1298 */       if (scoreW > bestEnforcedLink) {
/* 1299 */         bestEnforcedLink = scoreW;
/* 1300 */         NodeList.buildNodeList();
/*      */         
/* 1302 */         for (b1 = 0; b1 < NodeList.nodeListLength; b1++) {
/* 1303 */           (NodeList.nodeList[b1]).word = (NodeList.nodeList[b1]).template;
/* 1304 */           (NodeList.nodeList[b1]).source = 0;
/*      */         } 
/* 1306 */         NodeList.attachClues(Op.ff[Op.FF.FfDic.ordinal()], Boolean.valueOf(false));
/*      */         
/* 1308 */         NodeList.sortNodeList(2);
/* 1309 */         saveCrossword(Op.ff[Op.FF.FfPuz.ordinal()]);
/* 1310 */         restoreFrame(2);
/* 1311 */         ret = true;
/*      */       } 
/*      */     } else {
/*      */       
/* 1315 */       area = Grid.xSz * Grid.ySz;
/* 1316 */       if ((Op.getInt(Op.FF.FfOptimize.ordinal(), Op.ff) == 0 && (scoreW > bestScoreW || (scoreW == bestScoreW && area < bestArea))) || (
/* 1317 */         Op.getInt(Op.FF.FfOptimize.ordinal(), Op.ff) == 1 && (scoreC > bestScoreC || (scoreC == bestScoreC && area < bestArea))) || (
/* 1318 */         Op.getInt(Op.FF.FfOptimize.ordinal(), Op.ff) == 2 && (scoreL > bestScoreL || (scoreL == bestScoreL && area < bestArea))) || (
/* 1319 */         Op.getInt(Op.FF.FfOptimize.ordinal(), Op.ff) == 3 && (scoreL > bestScoreL || (scoreL == bestScoreL && area < bestArea)) && scoreW % 2 == 0)) {
/* 1320 */         bestArea = area;
/* 1321 */         ret = true;
/*      */         
/* 1323 */         for (wordsDir[1] = b = 0, wordsDir[0] = b = 0; b < NodeList.nodeListLength; b++)
/* 1324 */           wordsDir[(NodeList.nodeList[b]).direction] = wordsDir[(NodeList.nodeList[b]).direction] + 1; 
/* 1325 */         if (Op.getInt(Op.FF.FfOptimize.ordinal(), Op.ff) != 3 || wordsDir[0] == wordsDir[1]) {
/* 1326 */           for (byte b1 = 0; b1 < NodeList.nodeListLength; b1++) {
/* 1327 */             (NodeList.nodeList[b1]).word = (NodeList.nodeList[b1]).template;
/* 1328 */             (NodeList.nodeList[b1]).source = 0;
/*      */           } 
/* 1330 */           NodeList.attachClues(Op.ff[Op.FF.FfDic.ordinal()], Boolean.valueOf(false));
/*      */           
/* 1332 */           NodeList.sortNodeList(2);
/* 1333 */           saveCrossword(Op.ff[Op.FF.FfPuz.ordinal()]);
/* 1334 */           restoreFrame(2);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1338 */     return ret;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   boolean wordFits(int x, int y, int direction, char[] word, int i) {
/* 1344 */     int x1 = x, y1 = y;
/* 1345 */     if (direction == 72) { x1 += this.wNeg[i] + 1; } else { y1 += this.wNeg[i] + 1; }
/* 1346 */      if (x1 > Op.getInt(Op.FF.FfAcross.ordinal(), Op.ff) || y1 > Op.getInt(Op.FF.FfDown.ordinal(), Op.ff)) return false; 
/* 1347 */     if (x1 < Op.getInt(Op.FF.FfAcross.ordinal(), Op.ff) && y1 < Op.getInt(Op.FF.FfDown.ordinal(), Op.ff) && 
/* 1348 */       Grid.copy[x1][y1] > 4) return false;
/*      */     
/* 1350 */     if (direction == 72) { x -= this.wPos[i] + 1; } else { y -= this.wPos[i] + 1; }
/* 1351 */      if (x < -1 || y < -1) return false; 
/* 1352 */     if (x >= 0 && y >= 0 && 
/* 1353 */       Grid.copy[x][y] > 4) return false;
/*      */     
/* 1355 */     for (int j = 0; j < word.length; j++) {
/* 1356 */       if (direction == 72) { x++; } else { y++; }
/* 1357 */        if ((Grid.copy[x][y] != 0 && Grid.copy[x][y] != word[j]) || Grid.scratch[x][y] != 0)
/* 1358 */         return false; 
/*      */     } 
/* 1360 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   boolean wordFitsExactly(int x, int y, int direction, char[] word, int i) {
/* 1366 */     int x1 = x, y1 = y;
/* 1367 */     if (direction == 72) { x1 += this.wNeg[i] + 1; } else { y1 += this.wNeg[i] + 1; }
/* 1368 */      if (x1 > Op.getInt(Op.FF.FfAcross.ordinal(), Op.ff) || y1 > Op.getInt(Op.FF.FfDown.ordinal(), Op.ff)) return false; 
/* 1369 */     if (x1 < Op.getInt(Op.FF.FfAcross.ordinal(), Op.ff) && y1 < Op.getInt(Op.FF.FfDown.ordinal(), Op.ff) && 
/* 1370 */       Grid.copy[x1][y1] > 4) return false;
/*      */     
/* 1372 */     if (direction == 72) { x -= this.wPos[i] + 1; } else { y -= this.wPos[i] + 1; }
/* 1373 */      if (x < -1 || y < -1) return false; 
/* 1374 */     if (x >= 0 && y >= 0 && 
/* 1375 */       Grid.copy[x][y] > 4) return false;
/*      */     
/* 1377 */     for (int j = 0; j < word.length; j++) {
/* 1378 */       if (direction == 72) { x++; } else { y++; }
/* 1379 */        if (Grid.copy[x][y] != word[j])
/* 1380 */         return false; 
/*      */     } 
/* 1382 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   void wordInsert(int x, int y, int direction, char[] word, int i) {
/* 1388 */     scoreW++;
/* 1389 */     if (direction == 72) { x -= this.wPos[i] + 1; } else { y -= this.wPos[i] + 1; }
/* 1390 */      if (x > -1 && y > -1) Grid.copy[x][y] = Grid.copy[x][y] + 1; 
/* 1391 */     (this.slot[this.sNum]).wNum = this.wNum[i];
/*      */     
/* 1393 */     for (int j = 0; j < word.length; j++) {
/* 1394 */       if (direction == 72) { x++; } else { y++; }
/* 1395 */        if (Grid.copy[x][y] == 0) {
/*      */         
/* 1397 */         Grid.copy[x][y] = word[j];
/* 1398 */         (this.slot[this.sNum]).x = x;
/* 1399 */         (this.slot[this.sNum]).y = y;
/* 1400 */         (this.slot[this.sNum]).direction = 158 - direction;
/* 1401 */         this.sNum++;
/* 1402 */         scoreC++;
/* 1403 */         (this.slot[this.sNum]).wNum = -1;
/*      */       } else {
/*      */         
/* 1406 */         scoreL++;
/*      */       } 
/* 1408 */     }  if (direction == 72) { x++; } else { y++; }
/*      */     
/* 1410 */     if ((this.slot[this.sNum]).wNum > -1) {
/* 1411 */       (this.slot[this.sNum]).direction = 88;
/* 1412 */       this.sNum++;
/* 1413 */       (this.slot[this.sNum]).wNum = -1;
/*      */     } 
/*      */     
/* 1416 */     if (x < Op.getInt(Op.FF.FfAcross.ordinal(), Op.ff) && y < Op.getInt(Op.FF.FfDown.ordinal(), Op.ff)) Grid.copy[x][y] = Grid.copy[x][y] + 1; 
/* 1417 */     (this.slot[this.sNum]).scoreW = scoreW;
/* 1418 */     (this.slot[this.sNum]).scoreC = scoreC;
/* 1419 */     (this.slot[this.sNum]).scoreL = scoreL;
/*      */   }
/*      */   
/*      */   void wordExtract(int x, int y, int direction, char[] word, int i) {
/* 1423 */     if (direction == 72) { x -= this.wPos[i] + 1; } else { y -= this.wPos[i] + 1; }
/* 1424 */      if (x > -1 && y > -1) Grid.copy[x][y] = Grid.copy[x][y] - 1; 
/* 1425 */     if (direction == 72) { x += word.length + 1; } else { y += word.length + 1; }
/* 1426 */      if (x < Op.getInt(Op.FF.FfAcross.ordinal(), Op.ff) && y < Op.getInt(Op.FF.FfDown.ordinal(), Op.ff)) Grid.copy[x][y] = Grid.copy[x][y] - 1;
/*      */     
/* 1428 */     this.sNum--; for (;; this.sNum--) {
/* 1429 */       if ((this.slot[this.sNum]).direction != 88)
/* 1430 */         Grid.copy[(this.slot[this.sNum]).x][(this.slot[this.sNum]).y] = 0; 
/* 1431 */       (this.slot[this.sNum]).x = 0;
/* 1432 */       (this.slot[this.sNum]).y = 0;
/* 1433 */       (this.slot[this.sNum]).direction = 0;
/* 1434 */       if ((this.slot[this.sNum]).wNum > -1) {
/* 1435 */         (this.slot[this.sNum]).wNum = -1;
/* 1436 */         scoreW = (this.slot[this.sNum]).scoreW;
/* 1437 */         scoreC = (this.slot[this.sNum]).scoreC;
/* 1438 */         scoreL = (this.slot[this.sNum]).scoreL;
/*      */         break;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   boolean lookAhead(int item) {
/* 1447 */     if ((this.slot[item]).direction == 88) {
/* 1448 */       return true;
/*      */     }
/* 1450 */     for (;; item++) {
/* 1451 */       int direction = (this.slot[item]).direction;
/* 1452 */       if (direction == 0)
/*      */         break; 
/* 1454 */       int x = (this.slot[item]).x;
/* 1455 */       int y = (this.slot[item]).y;
/* 1456 */       int letter = Grid.copy[x][y];
/* 1457 */       int indexPtr = 0;
/* 1458 */       while (this.cIndex[indexPtr] != letter)
/*      */         indexPtr++;  int i, wordNum;
/* 1460 */       for (i = this.iIndex[indexPtr]; i < this.iIndex[indexPtr + 1] && (
/* 1461 */         (this.busy[wordNum = this.wNum[i]] == 0) ? 
/* 1462 */         wordFits(x, y, direction, this.chWord[wordNum], i) : 
/*      */ 
/*      */ 
/*      */         
/* 1466 */         wordFitsExactly(x, y, direction, this.chWord[wordNum], i)); i++);
/*      */ 
/*      */ 
/*      */       
/* 1470 */       if (i == this.iIndex[indexPtr + 1])
/* 1471 */         return false; 
/*      */     } 
/* 1473 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean lookAheadOK(int item) {
/* 1480 */     int x, y, laSlot = this.sNum;
/* 1481 */     int direction = (this.slot[item]).direction;
/*      */     
/* 1483 */     if (direction == 88) return true;
/*      */     
/* 1485 */     for (;; item++) {
/* 1486 */       if ((this.slot[item]).direction != direction) {
/* 1487 */         return true;
/*      */       }
/* 1489 */       x = (this.slot[item]).x;
/* 1490 */       y = (this.slot[item]).y;
/* 1491 */       if (direction == 72) { x--; } else { y--; }
/* 1492 */        if (x >= 0 && y >= 0 && Grid.copy[x][y] > 4)
/*      */         break; 
/* 1494 */       if (direction == 72) { x += 2; } else { y += 2; }
/* 1495 */        if (x < Op.getInt(Op.FF.FfAcross.ordinal(), Op.ff) && y < Op.getInt(Op.FF.FfDown.ordinal(), Op.ff) && Grid.copy[x][y] > 4) {
/*      */         break;
/*      */       }
/*      */     } 
/*      */     
/* 1500 */     int letter = Grid.copy[x][y];
/* 1501 */     int indexPtr = 0;
/* 1502 */     while (this.cIndex[indexPtr] != letter)
/*      */       indexPtr++;  int i; boolean done;
/* 1504 */     for (done = false, i = this.iIndex[indexPtr]; i < this.iIndex[indexPtr + 1]; i++) {
/* 1505 */       int wordNum; if (this.busy[wordNum = this.wNum[i]] == 0) {
/* 1506 */         if (wordFits(x, y, direction, this.chWord[wordNum], i)) {
/* 1507 */           wordInsert(x, y, direction, this.chWord[wordNum], i);
/* 1508 */           this.busy[wordNum] = 1;
/*      */           
/* 1510 */           if (lookAhead(laSlot))
/* 1511 */             if ((this.slot[item + 1]).direction == direction) {
/* 1512 */               done = lookAheadOK(item + 1);
/*      */             } else {
/* 1514 */               done = true;
/*      */             }  
/* 1516 */           wordExtract(x, y, direction, this.chWord[wordNum], i);
/* 1517 */           this.busy[wordNum] = 0;
/* 1518 */           if (done) return true;
/*      */         
/*      */         }
/*      */       
/* 1522 */       } else if (wordFitsExactly(x, y, direction, this.chWord[wordNum], i)) {
/* 1523 */         if ((this.slot[item + 1]).direction == direction) {
/* 1524 */           done = lookAheadOK(item + 1);
/*      */         } else {
/* 1526 */           done = true;
/* 1527 */         }  if (done)
/* 1528 */           return true; 
/*      */       } 
/*      */     } 
/* 1531 */     return false;
/*      */   }
/*      */   int insertWordAt(int item) {
/*      */     int indexPtr, first, last, start;
/* 1535 */     Random r = new Random();
/*      */ 
/*      */ 
/*      */     
/* 1539 */     while ((this.slot[item]).direction == 88) {
/* 1540 */       item++;
/*      */     }
/* 1542 */     if ((ffMulti && ++this.iCount > 10000000) || (!ffMulti && Def.building == 2))
/*      */     {
/* 1544 */       return (this.slot[1]).wNum - 1; } 
/* 1545 */     if ((this.slot[item]).direction == 0) {
/* 1546 */       if ((Op.getInt(Op.FF.FfOptimize.ordinal(), Op.ff) == 0 && scoreW >= bestScoreW) || (
/* 1547 */         Op.getInt(Op.FF.FfOptimize.ordinal(), Op.ff) == 1 && scoreC >= bestScoreC) || (
/* 1548 */         Op.getInt(Op.FF.FfOptimize.ordinal(), Op.ff) == 2 && scoreL >= bestScoreL) || (
/* 1549 */         Op.getInt(Op.FF.FfOptimize.ordinal(), Op.ff) == 3 && scoreL >= bestScoreL && scoreW % 2 == 0) || enforceLinkMode)
/*      */       {
/* 1551 */         if (displayGrid()) {
/* 1552 */           this.iCount = 0;
/* 1553 */           this.sCount += bestScoreW * 100;
/* 1554 */           bestScoreW = scoreW;
/* 1555 */           bestScoreC = scoreC;
/* 1556 */           bestScoreL = scoreL;
/*      */         } 
/*      */       }
/*      */       
/* 1560 */       this.sCount -= bestScoreW - scoreW;
/* 1561 */       if (this.sCount <= 0) {
/* 1562 */         this.sCount = bestScoreW * 20;
/* 1563 */         return (this.slot[1]).wNum;
/*      */       } 
/* 1565 */       return -1;
/*      */     } 
/*      */     
/* 1568 */     int laSlot = this.sNum;
/* 1569 */     int x = (this.slot[item]).x;
/* 1570 */     int y = (this.slot[item]).y;
/* 1571 */     int direction = (this.slot[item]).direction;
/* 1572 */     int letter = Grid.copy[x][y];
/* 1573 */     if (letter == 0) {
/* 1574 */       indexPtr = 1;
/* 1575 */       while (this.iIndex[indexPtr] != 0)
/*      */         indexPtr++; 
/* 1577 */       first = this.iIndex[0];
/* 1578 */       last = this.iIndex[indexPtr - 1];
/* 1579 */       indexPtr = r.nextInt(indexPtr - 1);
/* 1580 */       i = start = this.iIndex[indexPtr];
/*      */     } else {
/*      */       
/* 1583 */       indexPtr = 0;
/* 1584 */       while (this.cIndex[indexPtr] != letter)
/*      */         indexPtr++; 
/* 1586 */       i = this.iIndex[indexPtr + 1] - this.iIndex[indexPtr];
/* 1587 */       i = start = this.iIndex[indexPtr] + r.nextInt(i);
/* 1588 */       first = this.iIndex[indexPtr];
/* 1589 */       last = this.iIndex[indexPtr + 1];
/*      */     } 
/*      */     while (true) {
/*      */       int wordNum;
/* 1593 */       if (this.busy[wordNum = this.wNum[i]] == 0 && 
/* 1594 */         wordFits(x, y, direction, this.chWord[wordNum], i)) {
/* 1595 */         int j; this.busy[wordNum] = item + 1;
/* 1596 */         wordInsert(x, y, direction, this.chWord[wordNum], i);
/* 1597 */         if (lookAheadOK(laSlot)) {
/* 1598 */           j = insertWordAt(item + 1);
/*      */         } else {
/* 1600 */           j = -1;
/*      */         } 
/* 1602 */         wordExtract(x, y, direction, this.chWord[wordNum], i);
/* 1603 */         this.busy[wordNum] = 0;
/* 1604 */         if (j != -1 && (
/* 1605 */           j != wordNum || Def.building == 2)) {
/* 1606 */           return j;
/*      */         }
/*      */       } 
/* 1609 */       if (++i == last)
/* 1610 */         i = first; 
/* 1611 */       if (i == start) {
/* 1612 */         if (item > 0) {
/*      */           break;
/*      */         }
/*      */         do {
/* 1616 */           if (++x != Op.getInt(Op.FF.FfAcross.ordinal(), Op.ff))
/* 1617 */             continue;  x = 0;
/* 1618 */           if (++y != Op.getInt(Op.FF.FfDown.ordinal(), Op.ff))
/* 1619 */             continue;  y = 0;
/*      */         }
/* 1621 */         while (Grid.scratch[x][y] != 0);
/*      */ 
/*      */         
/* 1624 */         this.sCount = 100;
/* 1625 */         (this.slot[item]).x = x;
/* 1626 */         (this.slot[item]).y = y;
/*      */       } 
/*      */     }  int i;
/*      */     boolean filled;
/* 1630 */     for (filled = false, i = this.iIndex[indexPtr]; i < this.iIndex[indexPtr + 1]; i++) {
/* 1631 */       int j; if (this.busy[j = this.wNum[i]] != 0 && 
/* 1632 */         wordFitsExactly(x, y, direction, this.chWord[j], i)) {
/* 1633 */         int checkSlot = this.busy[j] - 1;
/* 1634 */         if (direction == 72) {
/* 1635 */           if (y == (this.slot[checkSlot]).y && ((
/* 1636 */             x > (this.slot[checkSlot]).x && x - (this.slot[checkSlot]).x < this.wPos[i]) || (x < (this.slot[checkSlot]).x && (this.slot[checkSlot]).x - x < this.wNeg[i])))
/* 1637 */             filled = true; 
/*      */           break;
/*      */         } 
/* 1640 */         if (x == (this.slot[checkSlot]).x && ((
/* 1641 */           y > (this.slot[checkSlot]).y && y - (this.slot[checkSlot]).y < this.wPos[i]) || (y < (this.slot[checkSlot]).y && (this.slot[checkSlot]).y - y < this.wNeg[i]))) {
/* 1642 */           filled = true;
/*      */         }
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/* 1648 */     int goBack = 0;
/* 1649 */     if (!filled) {
/* 1650 */       if (direction == 72) { x++; } else { y++; }
/* 1651 */        if (x < Op.getInt(Op.FF.FfAcross.ordinal(), Op.ff) && y < Op.getInt(Op.FF.FfDown.ordinal(), Op.ff) && Grid.copy[x][y] > 4) {
/* 1652 */         goBack = 1;
/*      */       } else {
/* 1654 */         if (direction == 72) { x -= 2; } else { y -= 2; }
/* 1655 */          if (x >= 0 && y >= 0 && Grid.copy[x][y] > 4) {
/* 1656 */           goBack = 1;
/*      */         }
/*      */       } 
/*      */     } 
/* 1660 */     if (goBack != 0) {
/* 1661 */       direction = 158 - direction;
/* 1662 */       letter = Grid.copy[x][y];
/* 1663 */       indexPtr = 0;
/* 1664 */       while (this.cIndex[indexPtr] != letter)
/*      */         indexPtr++; 
/* 1666 */       for (i = this.iIndex[indexPtr]; i < this.iIndex[indexPtr + 1]; i++) {
/* 1667 */         int j; if (this.busy[j = this.wNum[i]] != 0 && 
/* 1668 */           wordFitsExactly(x, y, direction, this.chWord[j], i))
/* 1669 */           return j; 
/*      */       } 
/*      */     } 
/* 1672 */     return insertWordAt(item + 1);
/*      */   }
/*      */   
/*      */   private boolean buildFreeformPuzzle() {
/* 1676 */     int besti = 0, bestj = 0, bestk = 0;
/*      */     
/* 1678 */     scoreW = scoreC = scoreL = 0;
/* 1679 */     bestScoreW = bestScoreC = bestScoreL = 0;
/* 1680 */     bestArea = 3000; bestEnforcedLink = 0;
/*      */     
/* 1682 */     Op.getBool(Op.CW.CwSolC.ordinal(), Op.cw);
/* 1683 */     enforceLinkMode = Op.getBool(Op.FF.FfEnforceLink.ordinal(), Op.ff).booleanValue();
/*      */     
/* 1685 */     this.slot = new Slot[2000]; int i;
/* 1686 */     for (i = 0; i < 2000; i++)
/* 1687 */       this.slot[i] = new Slot(); 
/*      */     int j;
/* 1689 */     for (j = 0; j < Grid.gridMax; j++) {
/* 1690 */       for (i = 0; i < Grid.gridMax; i++)
/* 1691 */         Grid.copy[i][j] = 0; 
/* 1692 */     }  if (!loadDictionary(this.theFilter)) {
/* 1693 */       return false;
/*      */     }
/* 1695 */     if (Op.ff[Op.FF.FfTemplate.ordinal()].length() > 0)
/* 1696 */     { for (j = 0; j < Op.getInt(Op.FF.FfDown.ordinal(), Op.ff); j++) {
/* 1697 */         for (i = 0; i < Op.getInt(Op.FF.FfAcross.ordinal(), Op.ff); i++) {
/* 1698 */           if (Grid.scratch[i][j] == 0) {
/* 1699 */             int k; for (k = 1; i + k < Op.getInt(Op.FF.FfAcross.ordinal(), Op.ff) && Grid.scratch[i + k][j] == 0; k++);
/* 1700 */             if (k > bestk) {
/* 1701 */               besti = i;
/* 1702 */               bestj = j;
/* 1703 */               bestk = k;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       }  }
/* 1708 */     else { besti = 1;
/* 1709 */       bestj = Op.getInt(Op.FF.FfDown.ordinal(), Op.ff) / 2;
/* 1710 */       bestk = Op.getInt(Op.FF.FfAcross.ordinal(), Op.ff); }
/*      */ 
/*      */     
/* 1713 */     (this.slot[this.sNum = 0]).x = besti + bestk / 2;
/* 1714 */     (this.slot[this.sNum]).y = bestj;
/* 1715 */     (this.slot[this.sNum]).direction = 72;
/* 1716 */     this.sNum++;
/* 1717 */     (this.slot[this.sNum]).scoreW = (this.slot[this.sNum]).scoreC = (this.slot[this.sNum]).scoreL = 0;
/*      */     
/* 1719 */     this.sCount = 100;
/* 1720 */     if (enforceLinkMode) jfFreeform.setResizable(false); 
/* 1721 */     insertWordAt(0);
/* 1722 */     if (howMany > 1) lockoutWords(); 
/* 1723 */     jfFreeform.setResizable(true);
/* 1724 */     loadCrossword(Op.ff[Op.FF.FfPuz.ordinal()]);
/* 1725 */     restoreFrame(1);
/* 1726 */     Def.building = 0;
/* 1727 */     if (!ffMulti)
/* 1728 */       this.buildMenuItem.setText("Start Building"); 
/* 1729 */     this.iCount = 0;
/*      */     
/* 1731 */     return true;
/*      */   }
/*      */   
/*      */   private void buildFFmulti() {
/* 1735 */     for (multiPuzNum = 0; multiPuzNum < howMany; multiPuzNum++) {
/* 1736 */       Op.ff[Op.FF.FfPuz.ordinal()] = "" + (startPuz + multiPuzNum) + ".crossword";
/* 1737 */       Methods.havePuzzle = buildFreeformPuzzle();
/*      */     } 
/* 1739 */     this.buildMenuItem.setText("Start Building");
/* 1740 */     howMany = 1; ffMulti = false;
/*      */   }
/*      */   
/*      */   private void geminiPrint() {
/* 1744 */     PrinterJob job = PrinterJob.getPrinterJob();
/* 1745 */     job.setPrintable(new PrintGemini());
/* 1746 */     if (job.printDialog()) {
/* 1747 */       try { job.print(); }
/* 1748 */       catch (Exception pe) {}
/*      */     }
/*      */   }
/*      */   
/*      */   void processMouse(MouseEvent e) {
/* 1753 */     int x = e.getX(), y = e.getY();
/*      */     
/* 1755 */     if (thePic.length() == 0)
/* 1756 */       return;  if (x < Grid.xOrg || y < Grid.yOrg)
/* 1757 */       return;  int i = (x - Grid.xOrg) / Grid.xCell;
/* 1758 */     int j = (y - Grid.yOrg) / Grid.yCell;
/* 1759 */     if (i < Grid.xSz && j < Grid.ySz) {
/* 1760 */       Grid.mode[i][j] = 2 - Grid.mode[i][j]; Grid.scratch[i][j] = 2 - Grid.mode[i][j];
/* 1761 */       NodeList.buildNodeList();
/* 1762 */       Grid.saveGrid("freeform.template");
/* 1763 */       Op.ff[Op.FF.FfTemplate.ordinal()] = "freeform.template";
/* 1764 */       pp.repaint();
/*      */     } 
/*      */   }
/*      */   
/*      */   static void lockoutWords() {
/* 1769 */     String word = "", clue = "";
/*      */ 
/*      */     
/* 1772 */     int wordDat = 0;
/* 1773 */     boolean read = true;
/*      */ 
/*      */     
/* 1776 */     NodeList.sortNodeList(0);
/*      */     try {
/* 1778 */       DataInputStream oldDic = new DataInputStream(new FileInputStream(Op.ff[Op.FF.FfDic.ordinal()] + ".dic/xword.dic"));
/* 1779 */       DataOutputStream newDic = new DataOutputStream(new FileOutputStream(Op.ff[Op.FF.FfDic.ordinal()] + ".dic/xword.new")); int i;
/* 1780 */       for (i = 0; i < 128; i++)
/* 1781 */         newDic.writeByte(oldDic.readByte()); 
/* 1782 */       for (i = 0; oldDic.available() > 2; ) {
/* 1783 */         if (read) {
/* 1784 */           wordDat = oldDic.readInt();
/* 1785 */           word = oldDic.readUTF();
/* 1786 */           clue = oldDic.readUTF();
/*      */         } 
/* 1788 */         read = true;
/* 1789 */         int comp = word.compareToIgnoreCase((NodeList.nodeList[i]).word);
/* 1790 */         if (comp == 0) {
/* 1791 */           int loCount = wordDat % 256; int clueCount;
/* 1792 */           for (int j = 0; j < clue.length(); j++) {
/* 1793 */             if (clue.charAt(j) == '*')
/* 1794 */               clueCount++; 
/* 1795 */           }  if (loCount != 255 && 
/* 1796 */             ++loCount == clueCount)
/* 1797 */             loCount = 255; 
/* 1798 */           wordDat = wordDat & 0xFFFFFF00 | loCount;
/* 1799 */           newDic.writeInt(wordDat);
/* 1800 */           newDic.writeUTF(word);
/* 1801 */           newDic.writeUTF(clue);
/*      */           
/* 1803 */           if (i < NodeList.nodeListLength - 1)
/* 1804 */             i++;  continue;
/*      */         } 
/* 1806 */         if (comp < 0) {
/* 1807 */           newDic.writeInt(wordDat);
/* 1808 */           newDic.writeUTF(word);
/* 1809 */           newDic.writeUTF(clue); continue;
/*      */         } 
/* 1811 */         if (comp > 0) {
/* 1812 */           if (i < NodeList.nodeListLength - 1) {
/* 1813 */             i++;
/* 1814 */             read = false;
/*      */             continue;
/*      */           } 
/* 1817 */           newDic.writeInt(wordDat);
/* 1818 */           newDic.writeUTF(word);
/* 1819 */           newDic.writeUTF(clue);
/*      */         } 
/*      */       } 
/*      */       
/* 1823 */       newDic.close();
/* 1824 */       oldDic.close();
/*      */     } catch (IOException exc) {
/* 1826 */       System.out.println("Error");
/* 1827 */     }  File fl = new File(Op.ff[Op.FF.FfDic.ordinal()] + ".dic/xword.dic");
/* 1828 */     fl.delete();
/* 1829 */     fl = new File(Op.ff[Op.FF.FfDic.ordinal()] + ".dic/xword.new");
/* 1830 */     fl.renameTo(new File(Op.ff[Op.FF.FfDic.ordinal()] + ".dic/xword.dic"));
/* 1831 */     NodeList.sortNodeList(2);
/*      */   }
/*      */ }


/* Location:              C:\Users\mustapha\Desktop\Crossword-Express.jar!\crosswordexpress\FreeformBuild.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */