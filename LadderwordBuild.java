/*      */ package crosswordexpress;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.io.DataInputStream;
/*      */ import java.io.DataOutputStream;
/*      */ import javax.swing.JComboBox;
/*      */ import javax.swing.JDialog;
/*      */ import javax.swing.JLabel;
/*      */ import javax.swing.JMenuItem;
/*      */ import javax.swing.KeyStroke;
/*      */ 
/*      */ public class LadderwordBuild extends JPanel {
/*      */   static JFrame jfLadderword;
/*      */   static JMenuBar menuBar;
/*      */   JMenu menu;
/*      */   JMenu submenu;
/*      */   JMenuItem menuItem;
/*      */   JMenuItem buildMenuItem;
/*   19 */   static String anchor1 = ""; static JPanel pp; static int panelW; static int panelH; static JLabel jl1; static JLabel jl2; Thread thread; File[] grids; static String anchor2 = "";
/*   20 */   List<String> anagrams = new ArrayList<>();
/*      */   
/*      */   static void def() {
/*   23 */     Op.updateOption(Op.LW.LwW.ordinal(), "600", Op.lw);
/*   24 */     Op.updateOption(Op.LW.LwH.ordinal(), "500", Op.lw);
/*   25 */     Op.updateOption(Op.LW.LwRows.ordinal(), "6", Op.lw);
/*   26 */     Op.updateOption(Op.LW.LwFirstLen.ordinal(), "5", Op.lw);
/*   27 */     Op.updateOption(Op.LW.LwCell.ordinal(), "FFFFFF", Op.lw);
/*   28 */     Op.updateOption(Op.LW.LwGrid.ordinal(), "000000", Op.lw);
/*   29 */     Op.updateOption(Op.LW.LwFocus.ordinal(), "33FFCC", Op.lw);
/*   30 */     Op.updateOption(Op.LW.LwLetter.ordinal(), "000000", Op.lw);
/*   31 */     Op.updateOption(Op.LW.LwID.ordinal(), "000000", Op.lw);
/*   32 */     Op.updateOption(Op.LW.LwClue.ordinal(), "666666", Op.lw);
/*   33 */     Op.updateOption(Op.LW.LwError.ordinal(), "FF0000", Op.lw);
/*   34 */     Op.updateOption(Op.LW.LwPuz.ordinal(), "sample.ladderword", Op.lw);
/*   35 */     Op.updateOption(Op.LW.LwDic.ordinal(), "english", Op.lw);
/*   36 */     Op.updateOption(Op.LW.LwFont.ordinal(), "SansSerif", Op.lw);
/*   37 */     Op.updateOption(Op.LW.LwIDFont.ordinal(), "SansSerif", Op.lw);
/*   38 */     Op.updateOption(Op.LW.LwClueFont.ordinal(), "SansSerif", Op.lw);
/*   39 */     Op.updateOption(Op.LW.LwPuzColor.ordinal(), "true", Op.lw);
/*   40 */     Op.updateOption(Op.LW.LwSolColor.ordinal(), "true", Op.lw);
/*      */   }
/*      */   
/*   43 */   String ladderwordHelp = "<div>A <b>LADDER-WORD</b> Puzzle consists of a number of rows of cells having the following form:-<p/><b>FLIER  &nbsp; T &nbsp; TRIFLE &nbsp; E &nbsp; FERTILE</b><p/>It is easy to see that the second word is an anagram of the first word plus the first single letter. Similarly, the third word is an anagram of the second word plus the second single letter. When a number of such lines are collected together into a puzzle, the single letters combine to form two vertical <b>Anchor</b> words.</div><p/><span class='m'>Menu Functions</span><ul><li/><span class='s'>File Menu</span><ul><li/><span >Select a Dictionary</span><br/>When loading a new puzzle into the Build screen, you begin by selecting the dictionary which was used to build the LADDER-WORD puzzle which you want to load.<p/><li/><span >Load a Puzzle</span><br/>Then you choose your puzzle from the pool of LADDER-WORD puzzles currently available in the selected dictionary.<p/><li/><span >Save</span><br/>If you have done some manual editing of the puzzle, this option will save those changes under the existing file name.<p/><li/><span >SaveAs</span><br/>This option allows you to make an exact copy of the current puzzle using a different file name. The copy will be saved in the folder of the dictionary that was used to construct it. Alternatively, if you don't enter a new name for the puzzle, you can change the Puzzle Description, or any of the other descriptive items without changing the puzzle name.<p/><li/><span >Quit Construction</span><br/>Returns you to the Crossword Express opening screen.</ul><li/><span class='s'>Build Menu</span><ul><li/><span >Select a Dictionary</span><br/>Use this option to select the dictionary which you want to use to build the new LADDER-WORD puzzle.<p/><li/><span >Start a New Puzzle</span><br/>This option presents you with a dialog into which you can enter a file name for your new puzzle. You can also enter several other pieces of information such as a <b>Puzzle Title, Author</b> and <b>Copyright</b> information.<p/><li/><span >Build Options</span><br/>Use this option to access a Build Options dialog where you can set some rules to be followed by the puzzle building function as it builds your puzzle.<p/><li/><span >Start Building / Stop Building</span><br/>Construction of the puzzle will commence when you select the <b>Start Building</b> option. If puzzle building is successful you will receive a message containing the name of the puzzle file, and the location where it was saved. If it becomes necessary, you can interrupt the building process by selecting this option a second time. Note that during the construction phase, the text of this option is changed to <b>Stop Building</b><p/><li/><span >Suggest Anagrams</span><br/>If you don't like any of the words which have been inserted into the puzzle you have an option of changing them. Mouse click into the word you don't like, and then select this option. You will be presented with a selection box containing all of the possibilities. Select the one you want and click <b>OK</b>. Your selection will be placed automatically into the correct location in the puzzle.<p/><li/><span class=\"menusub\">Edit a Clue</span><br/>Mouse clicking into any word in the puzzle, and then selecting this option will give you an opportunity to edit the existing clue if there is one, or to enter a new clue if there isn't. If you click <b>OK</b> in the Edit Clue dialog, the puzzle will be saved automatically with the edited clue in place. If you decide not to save your editing, simply click <b>Cancel</b>.</ul> <li/><span class='s'>View Menu</span><ul><li/><span >Display Options</span><br/>This leads you to a dialog box in which you can change the colors of various elements within the puzzle, and control the fonts which will be used for the puzzle's text components. You can also decide if printing of the puzzle will be done in black and white or in color.</ul><li/><span class='s'>Tasks Menu</span><ul><li/><span >Print this Puzzle</span><br/>This will take you to a custom print screen where you can control the details involved with printing your puzzle.<p/><li/><span >Solve this Puzzle</span><br/>This will take you to a Solve screen which provides a fully interactive environment for solving the puzzle.<p/><li/><span >Export Puzzle as Text</span><br/>Under normal circumstances, the Print function will provide all of the layout flexibility you will need when printing your puzzles. Inevitably of course special cases will arise where you need to intervene in the printing of either the words or the clues to achieve some special effect. To meet this need, a text export feature offers the following choices:-<ul><li/><b>Export Words.</b> Each line of text has the format <b>1. WORD</b><li/><b>Export Clues.</b> Each line of text has the format <b>1. Clue</b><li/><b>Export Words and Clues.</b> Each line of text has the format <b>1. WORD : Clue</b><li/><b>Export Puzzle Grid.</b> The puzzle grid is exported as a simple square or rectangular array of letters.</ul>In addition, you have the choice of exporting the text to a text file located anywhere on your computer's hard drive, or to the System Clipboard from where you can Paste into any Word Processor or Desk Top Publishing application.<p/><li/><span >Delete this Puzzle</span><br/>Use this option to eliminate unwanted LADDER-WORD puzzles from your file system.</ul><li/><span class='s'>Help Menu</span><ul><li/><span >Ladder-word Help</span><br/>Displays the Help screen which you are now reading.<p/></ul></ul></body>";
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
/*  126 */   String ladderwordOptions = "<div>Before you give the command to build a <b>Ladder-word</b> puzzle, you will need to set some options which the program will use during the construction process.</div><ul> <li/><b>Number of Rows: </b>Use this combo-box to select the number of rows of words to appear in your puzzle. Numbers in the range 3 to 10 can be selected.<p/><li/><b>Length of First Word: </b>Use this combo-box to select the length of the first word in each row of the puzzle. Lengths in the range 3 to 10 can be selected.<p/><li/><b>Enter 2 Anchor Words: </b>These MUST be entered before a puzzle can be built. In addition, they must be equal in length, and equal to the <b>Number of Rows</b> which you entered previously. If you don't meet this requirement, you will receive an appropriate message when you click the <b>OK</b> button.</ul></body>";
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
/*      */   LadderwordBuild(JFrame jf) {
/*  140 */     Def.puzzleMode = 120;
/*  141 */     Def.building = 0;
/*  142 */     Def.dispSolArray = Boolean.valueOf(false); Def.dispCursor = Boolean.valueOf(true); Def.dispGuideDigits = Boolean.valueOf(false);
/*      */ 
/*      */     
/*  145 */     makeGrid();
/*      */     
/*  147 */     jfLadderword = new JFrame("Ladderword Construction");
/*  148 */     jfLadderword.setSize(Op.getInt(Op.LW.LwW.ordinal(), Op.lw), Op.getInt(Op.LW.LwH.ordinal(), Op.lw));
/*  149 */     int frameX = (jf.getX() + jfLadderword.getWidth() > Methods.scrW) ? (Methods.scrW - jfLadderword.getWidth() - 10) : jf.getX();
/*  150 */     jfLadderword.setLocation(frameX, jf.getY());
/*  151 */     jfLadderword.setLayout((LayoutManager)null);
/*  152 */     jfLadderword.setDefaultCloseOperation(0);
/*  153 */     jfLadderword
/*  154 */       .addComponentListener(new ComponentAdapter()
/*      */         {
/*      */           public void componentResized(ComponentEvent ce) {
/*  157 */             int w = (LadderwordBuild.jfLadderword.getWidth() < 600) ? 600 : LadderwordBuild.jfLadderword.getWidth();
/*  158 */             int h = (LadderwordBuild.jfLadderword.getHeight() < 500) ? 500 : LadderwordBuild.jfLadderword.getHeight();
/*  159 */             LadderwordBuild.jfLadderword.setSize(w, h);
/*  160 */             Op.setInt(Op.LW.LwW.ordinal(), w, Op.lw);
/*  161 */             Op.setInt(Op.LW.LwH.ordinal(), h, Op.lw);
/*  162 */             LadderwordBuild.restoreFrame();
/*      */           }
/*      */         });
/*      */     
/*  166 */     jfLadderword
/*  167 */       .addWindowListener(new WindowAdapter() {
/*      */           public void windowClosing(WindowEvent we) {
/*  169 */             if (Def.building == 1 || Def.selecting)
/*  170 */               return;  Op.saveOptions("ladderword.opt", Op.lw);
/*  171 */             CrosswordExpress.transfer(1, LadderwordBuild.jfLadderword);
/*      */           }
/*      */         });
/*      */     
/*  175 */     Methods.closeHelp();
/*      */ 
/*      */     
/*  178 */     Runnable buildThread = () -> {
/*      */         boolean res;
/*      */         if (res = buildLadderword()) {
/*      */           saveLadderword(Op.lw[Op.LW.LwPuz.ordinal()]);
/*      */         }
/*      */         this.buildMenuItem.setText("Start Building");
/*      */         if (Def.building == 2) {
/*      */           Def.building = 0;
/*      */           Methods.interrupted(jfLadderword);
/*      */           makeGrid();
/*      */           restoreFrame();
/*      */           return;
/*      */         } 
/*      */         if (res) {
/*      */           Methods.puzzleSaved(jfLadderword, Op.lw[Op.LW.LwDic.ordinal()] + ".dic", Op.lw[Op.LW.LwPuz.ordinal()]);
/*      */           Methods.havePuzzle = true;
/*      */         } else {
/*      */           JOptionPane.showMessageDialog(jfLadderword, "<html>It is not possible to build this puzzle.<br><center>Please read the Help.");
/*      */         } 
/*      */         restoreFrame();
/*      */         Def.building = 0;
/*      */       };
/*  200 */     jl1 = new JLabel(); jfLadderword.add(jl1);
/*  201 */     jl2 = new JLabel(); jfLadderword.add(jl2);
/*      */ 
/*      */     
/*  204 */     menuBar = new JMenuBar();
/*  205 */     menuBar.setBackground(Def.COLOR_MENUBAR);
/*  206 */     jfLadderword.setJMenuBar(menuBar);
/*      */     
/*  208 */     this.menu = new JMenu("File");
/*  209 */     menuBar.add(this.menu);
/*  210 */     this.menuItem = new JMenuItem("Select a Dictionary");
/*  211 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(68, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  212 */     this.menu.add(this.menuItem);
/*  213 */     this.menuItem
/*  214 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           Methods.selectDictionary(jfLadderword, Op.lw[Op.LW.LwDic.ordinal()], 1);
/*      */           Op.lw[Op.LW.LwDic.ordinal()] = Methods.dictionaryName;
/*      */           loadLadderword(Op.lw[Op.LW.LwPuz.ordinal()]);
/*      */           restoreFrame();
/*      */         });
/*  223 */     this.menuItem = new JMenuItem("Load a Puzzle");
/*  224 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(76, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  225 */     this.menu.add(this.menuItem);
/*  226 */     this.menuItem
/*  227 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1)
/*      */             return; 
/*      */           pp.invalidate();
/*      */           pp.repaint();
/*      */           new Select(jfLadderword, Op.lw[Op.LW.LwDic.ordinal()] + ".dic/", "ladderword", Op.lw, Op.LW.LwPuz.ordinal(), false);
/*      */         });
/*  234 */     this.menuItem = new JMenuItem("Save");
/*  235 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(83, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  236 */     this.menu.add(this.menuItem);
/*  237 */     this.menuItem
/*  238 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1)
/*      */             return;  if (!Methods.havePuzzle) {
/*      */             Methods.noPuzzle(jfLadderword, "Save"); return;
/*      */           } 
/*      */           saveLadderword(Op.lw[Op.LW.LwPuz.ordinal()]);
/*      */           Methods.puzzleSaved(jfLadderword, Op.lw[Op.LW.LwDic.ordinal()] + ".dic", Op.lw[Op.LW.LwPuz.ordinal()]);
/*      */           restoreFrame();
/*      */         });
/*  247 */     this.menuItem = new JMenuItem("SaveAs");
/*  248 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(65, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  249 */     this.menu.add(this.menuItem);
/*  250 */     this.menuItem
/*  251 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           Methods.puzzleDescriptionDialog(jfLadderword, Op.lw[Op.LW.LwPuz.ordinal()].substring(0, Op.lw[Op.LW.LwPuz.ordinal()].indexOf(".ladderword")), Op.lw[Op.LW.LwDic.ordinal()] + ".dic", ".ladderword");
/*      */           if (Methods.clickedOK) {
/*      */             saveLadderword(Op.lw[Op.LW.LwPuz.ordinal()] = Methods.theFileName);
/*      */             restoreFrame();
/*      */             Methods.puzzleSaved(jfLadderword, Op.lw[Op.LW.LwDic.ordinal()] + ".dic", Op.lw[Op.LW.LwPuz.ordinal()]);
/*      */           } 
/*      */         });
/*  262 */     this.menuItem = new JMenuItem("Quit Construction");
/*  263 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(81, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  264 */     this.menu.add(this.menuItem);
/*  265 */     this.menuItem
/*  266 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           
/*      */           Op.saveOptions("ladderword.opt", Op.lw);
/*      */           CrosswordExpress.transfer(1, jfLadderword);
/*      */         });
/*  274 */     this.menu = new JMenu("Build");
/*  275 */     menuBar.add(this.menu);
/*  276 */     this.menuItem = new JMenuItem("Select a Dictionary");
/*  277 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(68, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  278 */     this.menu.add(this.menuItem);
/*  279 */     this.menuItem
/*  280 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           Methods.selectDictionary(jfLadderword, Op.lw[Op.LW.LwDic.ordinal()], 1);
/*      */           Op.lw[Op.LW.LwDic.ordinal()] = Methods.dictionaryName;
/*      */           loadLadderword(Op.lw[Op.LW.LwPuz.ordinal()]);
/*      */           restoreFrame();
/*      */         });
/*  289 */     this.menuItem = new JMenuItem("Start a new Puzzle");
/*  290 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(78, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  291 */     this.menu.add(this.menuItem);
/*  292 */     this.menuItem
/*  293 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           Methods.puzzleDescriptionDialog(jfLadderword, Op.lw[Op.LW.LwPuz.ordinal()].substring(0, Op.lw[Op.LW.LwPuz.ordinal()].indexOf(".ladderword")), Op.lw[Op.LW.LwDic.ordinal()] + ".dic", ".ladderword");
/*      */           if (Methods.clickedOK) {
/*      */             Op.lw[Op.LW.LwPuz.ordinal()] = Methods.theFileName;
/*      */             makeGrid();
/*      */           } 
/*      */           restoreFrame();
/*      */         });
/*  304 */     this.menuItem = new JMenuItem("Build Options");
/*  305 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(79, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  306 */     this.menu.add(this.menuItem);
/*  307 */     this.menuItem
/*  308 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           ladderwordOptions();
/*      */           if (Methods.clickedOK)
/*      */             makeGrid(); 
/*      */           restoreFrame();
/*      */         });
/*  317 */     this.buildMenuItem = new JMenuItem("Start Building");
/*  318 */     this.buildMenuItem.setAccelerator(KeyStroke.getKeyStroke(66, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  319 */     this.menu.add(this.buildMenuItem);
/*  320 */     this.buildMenuItem
/*  321 */       .addActionListener(ae -> {
/*      */           if (anchor1.length() != Grid.ySz || anchor2.length() != Grid.ySz) {
/*      */             JOptionPane.showMessageDialog(jfLadderword, "Please use Build Options to enter two Anchor words.");
/*      */             
/*      */             return;
/*      */           } 
/*      */           
/*      */           if (Op.lw[Op.LW.LwPuz.ordinal()].length() == 0) {
/*      */             Methods.noName(jfLadderword);
/*      */             
/*      */             return;
/*      */           } 
/*      */           if (Def.building == 0) {
/*      */             this.thread = new Thread(paramRunnable);
/*      */             this.thread.start();
/*      */             Def.building = 1;
/*      */             this.buildMenuItem.setText("Stop Building");
/*      */           } else {
/*      */             Def.building = 2;
/*      */           } 
/*      */         });
/*  342 */     this.menuItem = new JMenuItem("Suggest Anagrams");
/*  343 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(71, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  344 */     this.menu.add(this.menuItem);
/*  345 */     this.menuItem
/*  346 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           suggestAnagrams(Grid.yCur);
/*      */           if (Methods.clickedOK) {
/*      */             saveLadderword(Op.lw[Op.LW.LwPuz.ordinal()]);
/*      */             restoreFrame();
/*      */           } 
/*      */           restoreFrame();
/*      */         });
/*  357 */     this.menuItem = new JMenuItem("Edit a Clue");
/*  358 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(67, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  359 */     this.menu.add(this.menuItem);
/*  360 */     this.menuItem
/*  361 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           
/*      */           CrosswordBuild.addAClue(jfLadderword);
/*      */           if (Methods.clickedOK) {
/*      */             saveLadderword(Op.lw[Op.LW.LwPuz.ordinal()]);
/*      */             restoreFrame();
/*      */           } 
/*      */           restoreFrame();
/*      */         });
/*  373 */     this.menu = new JMenu("View");
/*  374 */     menuBar.add(this.menu);
/*  375 */     this.menuItem = new JMenuItem("Display Options");
/*  376 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(89, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  377 */     this.menu.add(this.menuItem);
/*  378 */     this.menuItem
/*  379 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           
/*      */           printOptions(jfLadderword, "Display Options");
/*      */           restoreFrame();
/*      */         });
/*  387 */     this.menu = new JMenu("Tasks");
/*  388 */     menuBar.add(this.menu);
/*  389 */     this.menuItem = new JMenuItem("Print this Puzzle");
/*  390 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(80, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  391 */     this.menu.add(this.menuItem);
/*  392 */     this.menuItem
/*  393 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           CrosswordExpress.toPrint(jfLadderword, Op.lw[Op.LW.LwPuz.ordinal()]);
/*      */         });
/*  399 */     this.menuItem = new JMenuItem("Solve this Puzzle");
/*  400 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(86, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  401 */     this.menu.add(this.menuItem);
/*  402 */     this.menuItem
/*  403 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1)
/*      */             return; 
/*      */           if (Methods.havePuzzle) {
/*      */             CrosswordExpress.transfer(121, jfLadderword);
/*      */           } else {
/*      */             Methods.noPuzzle(jfLadderword, "Solve");
/*      */           } 
/*      */         });
/*  412 */     this.menuItem = new JMenuItem("Export as Text");
/*  413 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(84, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  414 */     this.menu.add(this.menuItem);
/*  415 */     this.menuItem
/*  416 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1)
/*      */             return; 
/*      */           if (Methods.havePuzzle) {
/*      */             NodeList.exportText(jfLadderword, true);
/*      */           } else {
/*      */             Methods.noPuzzle(jfLadderword, "Export");
/*      */           } 
/*      */         });
/*  425 */     this.menuItem = new JMenuItem("Delete this Puzzle");
/*  426 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(90, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  427 */     this.menu.add(this.menuItem);
/*  428 */     this.menuItem
/*  429 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           
/*      */           if (Methods.deleteAPuzzle(jfLadderword, Op.lw[Op.LW.LwPuz.ordinal()], Op.lw[Op.LW.LwDic.ordinal()] + ".dic", pp)) {
/*      */             loadLadderword(Op.lw[Op.LW.LwPuz.ordinal()]);
/*      */             restoreFrame();
/*      */           } 
/*      */         });
/*  439 */     this.menu = new JMenu("Help");
/*  440 */     menuBar.add(this.menu);
/*  441 */     this.menuItem = new JMenuItem("Ladderword Help");
/*  442 */     this.menu.add(this.menuItem);
/*  443 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(72, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  444 */     this.menuItem
/*  445 */       .addActionListener(ae -> Methods.cweHelp(jfLadderword, null, "Building Ladderword Puzzles", this.ladderwordHelp));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  450 */     pp = new LadderwordPP(0, 37);
/*  451 */     jfLadderword.add(pp);
/*      */     
/*  453 */     pp
/*  454 */       .addMouseListener(new MouseAdapter() {
/*      */           public void mousePressed(MouseEvent e) {
/*  456 */             LadderwordBuild.this.updateGrid(e);
/*      */           }
/*      */         });
/*      */ 
/*      */     
/*  461 */     pp
/*  462 */       .addMouseMotionListener(new MouseAdapter() {
/*      */           public void mouseMoved(MouseEvent e) {
/*  464 */             if (Def.isMac) {
/*  465 */               LadderwordBuild.jfLadderword.setResizable((LadderwordBuild.jfLadderword.getWidth() - e.getX() < 15 && LadderwordBuild.jfLadderword
/*  466 */                   .getHeight() - e.getY() < 95));
/*      */             }
/*      */           }
/*      */         });
/*      */     
/*  471 */     jfLadderword
/*  472 */       .addKeyListener(new KeyAdapter() {
/*      */           public void keyPressed(KeyEvent e) {
/*  474 */             LadderwordBuild.this.handleKeyPressed(e);
/*      */           }
/*      */         });
/*      */ 
/*      */     
/*  479 */     loadLadderword(Op.lw[Op.LW.LwPuz.ordinal()]);
/*  480 */     restoreFrame();
/*      */   }
/*      */   
/*      */   static void restoreFrame() {
/*  484 */     jfLadderword.setVisible(true);
/*  485 */     Insets insets = jfLadderword.getInsets();
/*  486 */     panelW = jfLadderword.getWidth() - insets.left + insets.right;
/*  487 */     panelH = jfLadderword.getHeight() - insets.top + insets.bottom + 37 + menuBar.getHeight();
/*  488 */     pp.setSize(panelW, panelH);
/*  489 */     jfLadderword.requestFocus();
/*  490 */     pp.repaint();
/*  491 */     Methods.infoPanel(jl1, jl2, "Build Ladderword", "Dictionary : " + Op.lw[Op.LW.LwDic.ordinal()] + "  -|-  Puzzle : " + Op.lw[Op.LW.LwPuz
/*  492 */           .ordinal()], panelW);
/*      */   }
/*      */   
/*      */   static void setSizesAndOffsets(int x, int y, int width, int height, int inset) {
/*  496 */     int i = (width - inset) / Grid.xSz;
/*  497 */     int j = (height - inset) / Grid.ySz;
/*  498 */     Grid.xCell = Grid.yCell = (i < j) ? i : j;
/*  499 */     Grid.xOrg = x + (width - Grid.xCell * Grid.xSz) / 2;
/*  500 */     Grid.yOrg = y + (height - Grid.yCell * Grid.ySz) / 2;
/*      */   }
/*      */   
/*      */   private void ladderwordOptions() {
/*  504 */     final JTextField[] jtfAnchor = new JTextField[2];
/*      */     
/*  506 */     final JDialog jdlgLadderword = new JDialog(jfLadderword, "Ladderword Options", true);
/*  507 */     jdlgLadderword.setSize(280, 253);
/*  508 */     jdlgLadderword.setResizable(false);
/*  509 */     jdlgLadderword.setLayout((LayoutManager)null);
/*  510 */     jdlgLadderword.setLocation(jfLadderword.getX(), jfLadderword.getY());
/*      */     
/*  512 */     jdlgLadderword
/*  513 */       .addWindowListener(new WindowAdapter() {
/*      */           public void windowClosing(WindowEvent we) {
/*  515 */             Methods.closeHelp();
/*      */           }
/*      */         });
/*      */     
/*  519 */     Methods.closeHelp();
/*      */     
/*  521 */     JLabel jlLines = new JLabel("Number of Rows:");
/*  522 */     jlLines.setForeground(Def.COLOR_LABEL);
/*  523 */     jlLines.setSize(165, 20);
/*  524 */     jlLines.setLocation(10, 8);
/*  525 */     jlLines.setHorizontalAlignment(4);
/*  526 */     jdlgLadderword.add(jlLines);
/*      */     
/*  528 */     final JComboBox<Integer> jcbbLines = new JComboBox<>();
/*  529 */     for (int i = 3; i <= 12; i++)
/*  530 */       jcbbLines.addItem(Integer.valueOf(i)); 
/*  531 */     jcbbLines.setSize(80, 23);
/*  532 */     jcbbLines.setLocation(190, 8);
/*  533 */     jdlgLadderword.add(jcbbLines);
/*  534 */     jcbbLines.setBackground(Def.COLOR_BUTTONBG);
/*  535 */     jcbbLines.setSelectedIndex(Op.getInt(Op.LW.LwRows.ordinal(), Op.lw) - 3);
/*      */     
/*  537 */     JLabel jlFirstLen = new JLabel("Length of First Word:");
/*  538 */     jlFirstLen.setForeground(Def.COLOR_LABEL);
/*  539 */     jlFirstLen.setSize(165, 20);
/*  540 */     jlFirstLen.setLocation(10, 38);
/*  541 */     jlFirstLen.setHorizontalAlignment(4);
/*  542 */     jdlgLadderword.add(jlFirstLen);
/*      */     
/*  544 */     final JComboBox<Integer> jcbbFirstLen = new JComboBox<>();
/*  545 */     for (int j = 3; j <= 10; j++)
/*  546 */       jcbbFirstLen.addItem(Integer.valueOf(j)); 
/*  547 */     jcbbFirstLen.setSize(80, 23);
/*  548 */     jcbbFirstLen.setLocation(190, 38);
/*  549 */     jdlgLadderword.add(jcbbFirstLen);
/*  550 */     jcbbFirstLen.setBackground(Def.COLOR_BUTTONBG);
/*  551 */     jcbbFirstLen.setSelectedIndex(Op.getInt(Op.LW.LwFirstLen.ordinal(), Op.lw) - 3);
/*      */     
/*  553 */     JLabel jlAnchor = new JLabel("Enter 2 Anchor Words:");
/*  554 */     jlAnchor.setForeground(Def.COLOR_LABEL);
/*  555 */     jlAnchor.setSize(250, 20);
/*  556 */     jlAnchor.setLocation(10, 68);
/*  557 */     jlAnchor.setHorizontalAlignment(2);
/*  558 */     jdlgLadderword.add(jlAnchor);
/*      */     
/*  560 */     for (int k = 0; k < 2; k++) {
/*  561 */       jtfAnchor[k] = new JTextField((k == 0) ? anchor1 : anchor2, 15);
/*  562 */       jtfAnchor[k].setSize(260, 23);
/*  563 */       jtfAnchor[k].setLocation(10, 88 + k * 28);
/*  564 */       jtfAnchor[k].selectAll();
/*  565 */       jtfAnchor[k].setHorizontalAlignment(2);
/*  566 */       jdlgLadderword.add(jtfAnchor[k]);
/*  567 */       jtfAnchor[k].setFont(new Font("SansSerif", 1, 13));
/*      */     } 
/*      */     
/*  570 */     Action doOK = new AbstractAction("OK") {
/*      */         public void actionPerformed(ActionEvent e) {
/*  572 */           Op.setInt(Op.LW.LwRows.ordinal(), jcbbLines.getSelectedIndex() + 3, Op.lw);
/*  573 */           Op.setInt(Op.LW.LwFirstLen.ordinal(), jcbbFirstLen.getSelectedIndex() + 3, Op.lw);
/*  574 */           LadderwordBuild.anchor1 = jtfAnchor[0].getText().toUpperCase();
/*  575 */           LadderwordBuild.anchor2 = jtfAnchor[1].getText().toUpperCase();
/*  576 */           if (LadderwordBuild.anchor1.length() != Op.getInt(Op.LW.LwRows.ordinal(), Op.lw) || LadderwordBuild.anchor2.length() != Op.getInt(Op.LW.LwRows.ordinal(), Op.lw)) {
/*  577 */             JOptionPane.showMessageDialog(jdlgLadderword, "Both Anchor Words must have a length equal to the Number of Rows.");
/*      */             return;
/*      */           } 
/*  580 */           Methods.clickedOK = true;
/*  581 */           jdlgLadderword.dispose();
/*  582 */           Methods.closeHelp();
/*      */         }
/*      */       };
/*  585 */     JButton jbOK = Methods.newButton("doOK", doOK, 79, 10, 150, 90, 26);
/*  586 */     jdlgLadderword.add(jbOK);
/*      */     
/*  588 */     Action doCancel = new AbstractAction("Cancel") {
/*      */         public void actionPerformed(ActionEvent e) {
/*  590 */           Methods.clickedOK = false;
/*  591 */           jdlgLadderword.dispose();
/*  592 */           Methods.closeHelp();
/*      */         }
/*      */       };
/*  595 */     JButton jbCancel = Methods.newButton("doCancel", doCancel, 67, 10, 185, 90, 26);
/*  596 */     jdlgLadderword.add(jbCancel);
/*      */     
/*  598 */     Action doHelp = new AbstractAction("<html><font size=6 color=BB0000 face=Serif>Help ", new ImageIcon("graphics/help.png")) {
/*      */         public void actionPerformed(ActionEvent e) {
/*  600 */           Methods.cweHelp(null, jdlgLadderword, "Ladderword Options", LadderwordBuild.this.ladderwordOptions);
/*      */         }
/*      */       };
/*  603 */     JButton jbHelp = Methods.newButton("doHelp", doHelp, 72, 110, 150, 160, 61);
/*  604 */     jdlgLadderword.add(jbHelp);
/*      */     
/*  606 */     jdlgLadderword.getRootPane().setDefaultButton(jbOK);
/*  607 */     Methods.setDialogSize(jdlgLadderword, 280, 221);
/*      */   }
/*      */   
/*      */   static void printOptions(JFrame jf, String type) {
/*  611 */     String[] colorLabel = { "Cell Color", "Grid Color", "Focus Color", "Letter Color", "ID Digit Color", "Clue Color", "Error Color" };
/*  612 */     int[] colorInt = { Op.LW.LwCell.ordinal(), Op.LW.LwGrid.ordinal(), Op.LW.LwFocus.ordinal(), Op.LW.LwLetter.ordinal(), Op.LW.LwID.ordinal(), Op.LW.LwClue.ordinal(), Op.LW.LwError.ordinal() };
/*  613 */     String[] fontLabel = { "Select Puzzle Font", "Select ID Digit Font", "Select Clue Font" };
/*  614 */     int[] fontInt = { Op.LW.LwFont.ordinal(), Op.LW.LwIDFont.ordinal(), Op.LW.LwClueFont.ordinal() };
/*  615 */     String[] checkLabel = { "PPrint Puzzle with color.", "SPrint Solution with color." };
/*  616 */     int[] checkInt = { Op.LW.LwPuzColor.ordinal(), Op.LW.LwSolColor.ordinal() };
/*  617 */     Methods.stdPrintOptions(jf, "Ladderword ", Op.lw, colorLabel, colorInt, fontLabel, fontInt, checkLabel, checkInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void saveLadderword(String ladderwordName) {
/*      */     try {
/*  626 */       DataOutputStream dataOut = new DataOutputStream(new FileOutputStream(Op.lw[Op.LW.LwDic.ordinal()] + ".dic/" + ladderwordName));
/*  627 */       dataOut.writeInt(Grid.xSz);
/*  628 */       dataOut.writeInt(Grid.ySz);
/*  629 */       dataOut.writeByte(Methods.noReveal);
/*  630 */       dataOut.writeByte(Methods.noErrors);
/*  631 */       for (int k = 0; k < 54; k++)
/*  632 */         dataOut.writeByte(0); 
/*  633 */       for (int j = 0; j < Grid.ySz; j++) {
/*  634 */         for (int m = 0; m < Grid.xSz; m++) {
/*  635 */           dataOut.writeInt(Grid.mode[m][j]);
/*  636 */           dataOut.writeInt(Grid.letter[m][j]);
/*  637 */           dataOut.writeInt(Grid.sol[m][j]);
/*  638 */           dataOut.writeInt(Grid.color[m][j]);
/*      */         } 
/*  640 */       }  dataOut.writeUTF(Methods.puzzleTitle);
/*  641 */       dataOut.writeUTF(Methods.author);
/*  642 */       dataOut.writeUTF(Methods.copyright);
/*  643 */       dataOut.writeUTF(Methods.puzzleNumber);
/*  644 */       dataOut.writeUTF(Methods.puzzleNotes);
/*      */       
/*  646 */       for (int i = 0; i < NodeList.nodeListLength; i++) {
/*  647 */         dataOut.writeUTF((NodeList.nodeList[i]).word);
/*  648 */         dataOut.writeUTF((NodeList.nodeList[i]).clue);
/*      */       } 
/*  650 */       dataOut.close();
/*      */     }
/*  652 */     catch (IOException exc) {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void loadLadderword(String ladderwordName) {
/*  659 */     Op.lw[Op.LW.LwDic.ordinal()] = Methods.confirmDictionary(Op.lw[Op.LW.LwDic.ordinal()] + ".dic", false);
/*      */ 
/*      */     
/*  662 */     try { File fl = new File(Op.lw[Op.LW.LwDic.ordinal()] + ".dic/" + ladderwordName);
/*  663 */       if (!fl.exists()) {
/*  664 */         fl = new File(Op.lw[Op.LW.LwDic.ordinal()] + ".dic/");
/*  665 */         String[] s = fl.list(); int k;
/*  666 */         for (k = 0; k < s.length && (
/*  667 */           s[k].lastIndexOf(".ladderword") == -1 || s[k].charAt(0) == '.'); k++);
/*      */         
/*  669 */         if (k == s.length) { makeGrid(); return; }
/*  670 */          ladderwordName = s[k];
/*  671 */         Op.lw[Op.LW.LwPuz.ordinal()] = ladderwordName;
/*      */       } 
/*      */ 
/*      */       
/*  675 */       DataInputStream dataIn = new DataInputStream(new FileInputStream(Op.lw[Op.LW.LwDic.ordinal()] + ".dic/" + ladderwordName));
/*  676 */       Grid.xSz = dataIn.readInt();
/*  677 */       Grid.ySz = dataIn.readInt();
/*  678 */       Methods.noReveal = dataIn.readByte();
/*  679 */       Methods.noErrors = dataIn.readByte(); int i;
/*  680 */       for (i = 0; i < 54; i++)
/*  681 */         dataIn.readByte(); 
/*  682 */       for (int j = 0; j < Grid.ySz; j++) {
/*  683 */         for (i = 0; i < Grid.xSz; i++) {
/*  684 */           Grid.mode[i][j] = dataIn.readInt();
/*  685 */           Grid.letter[i][j] = dataIn.readInt();
/*  686 */           Grid.sol[i][j] = dataIn.readInt();
/*  687 */           Grid.color[i][j] = dataIn.readInt();
/*      */         } 
/*  689 */       }  Methods.puzzleTitle = dataIn.readUTF();
/*  690 */       Methods.author = dataIn.readUTF();
/*  691 */       Methods.copyright = dataIn.readUTF();
/*  692 */       Methods.puzzleNumber = dataIn.readUTF();
/*  693 */       Methods.puzzleNotes = dataIn.readUTF();
/*  694 */       NodeList.buildNodeList();
/*  695 */       for (i = 0; i < NodeList.nodeListLength; i++) {
/*  696 */         (NodeList.nodeList[i]).word = dataIn.readUTF();
/*  697 */         (NodeList.nodeList[i]).clue = dataIn.readUTF();
/*      */       } 
/*  699 */       dataIn.close(); }
/*      */     
/*  701 */     catch (IOException exc) { return; }
/*  702 */      Op.setInt(Op.LW.LwRows.ordinal(), Grid.ySz, Op.lw);
/*  703 */     Op.setInt(Op.LW.LwFirstLen.ordinal(), (Grid.xSz - 5) / 3, Op.lw);
/*  704 */     anchor1 = (NodeList.nodeList[1]).word;
/*  705 */     anchor2 = (NodeList.nodeList[3]).word;
/*      */     
/*  707 */     Methods.havePuzzle = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void drawLadderword(Graphics2D g2) {
/*  714 */     Stroke normalStroke = new BasicStroke(Grid.xCell / 20.0F, 2, 0);
/*  715 */     Stroke wideStroke = new BasicStroke(Grid.xCell / 8.0F, 0, 0);
/*  716 */     RenderingHints rh = g2.getRenderingHints();
/*  717 */     rh.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
/*  718 */     g2.setRenderingHints(rh);
/*  719 */     g2.setStroke(normalStroke);
/*      */     
/*      */     int j;
/*  722 */     for (j = 0; j < Grid.ySz; j++) {
/*  723 */       for (int k = 0; k < Grid.xSz; k++) {
/*  724 */         int theColor = Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.LW.LwCell.ordinal(), Op.lw) : 16777215;
/*  725 */         g2.setColor(new Color(theColor));
/*  726 */         g2.fillRect(Grid.xOrg + k * Grid.xCell, Grid.yOrg + j * Grid.yCell, Grid.xCell, Grid.yCell);
/*      */       } 
/*      */     } 
/*      */     
/*  730 */     g2.setColor(new Color(Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.LW.LwGrid.ordinal(), Op.lw) : 0));
/*  731 */     for (j = 0; j < Grid.ySz; j++) {
/*  732 */       for (int k = 0; k < Grid.xSz; k++) {
/*  733 */         if (Grid.mode[k][j] != 2)
/*  734 */           g2.drawRect(Grid.xOrg + k * Grid.xCell, Grid.yOrg + j * Grid.yCell, Grid.xCell, Grid.yCell); 
/*      */       } 
/*      */     } 
/*  737 */     g2.setStroke(wideStroke);
/*  738 */     for (j = 0; j < Grid.ySz; j++) {
/*  739 */       for (int k = 0; k < Grid.xSz; k++) {
/*  740 */         int right = Grid.xOrg + (k + 1) * Grid.xCell;
/*  741 */         int bottom = Grid.yOrg + (j + 1) * Grid.yCell;
/*  742 */         switch (Grid.mode[k][j]) {
/*      */           case 3:
/*      */           case 5:
/*  745 */             g2.drawLine(right, bottom - Grid.yCell, right, bottom);
/*  746 */             if (Grid.mode[k][j] == 3);
/*      */             break;
/*      */         } 
/*      */       
/*      */       } 
/*      */     } 
/*  752 */     g2.setFont(new Font(Op.lw[Op.LW.LwFont.ordinal()], 0, 8 * Grid.yCell / 10));
/*  753 */     g2.setColor(new Color(Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.LW.LwLetter.ordinal(), Op.lw) : 0));
/*  754 */     FontMetrics fm = g2.getFontMetrics();
/*  755 */     for (j = 0; j < Grid.ySz; j++) {
/*  756 */       for (int k = 0; k < Grid.xSz; k++) {
/*  757 */         char ch = (char)(Def.dispSolArray.booleanValue() ? Grid.sol[k][j] : Grid.letter[k][j]);
/*  758 */         if (ch != '\000') {
/*  759 */           int w = fm.stringWidth("" + ch);
/*  760 */           g2.drawString("" + ch, Grid.xOrg + k * Grid.xCell + (Grid.xCell - w) / 2, Grid.yOrg + j * Grid.yCell + 9 * Grid.yCell / 10);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  765 */     if (Def.dispCursor.booleanValue()) {
/*  766 */       g2.setColor(Def.COLOR_RED);
/*  767 */       g2.setStroke(wideStroke);
/*  768 */       g2.drawRect(Grid.xOrg + Grid.xCur * Grid.xCell, Grid.yOrg + Grid.yCur * Grid.yCell, Grid.xCell, Grid.yCell);
/*      */     } 
/*      */ 
/*      */     
/*  772 */     g2.setFont(new Font(Op.lw[Op.LW.LwIDFont.ordinal()], 0, Grid.yCell / 3));
/*  773 */     g2.setColor(new Color(Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.LW.LwID.ordinal(), Op.lw) : 0));
/*  774 */     fm = g2.getFontMetrics();
/*  775 */     for (int i = 0; NodeList.nodeList[i] != null; i++) {
/*  776 */       g2.drawString("" + (NodeList.nodeList[i]).id, Grid.xOrg + (NodeList.nodeList[i]).x * Grid.xCell + Grid.xCell / 8, Grid.yOrg + (NodeList.nodeList[i]).y * Grid.yCell + fm
/*  777 */           .getAscent());
/*      */     }
/*  779 */     g2.setStroke(new BasicStroke(1.0F));
/*      */   }
/*      */   
/*      */   static void printPuz(Graphics2D g2, int left, int top, int width, int height) {
/*  783 */     loadLadderword(Op.lw[Op.LW.LwPuz.ordinal()]);
/*  784 */     setSizesAndOffsets(left, top, width, height, 0);
/*  785 */     Methods.clearGrid(Grid.sol);
/*  786 */     Def.dispWithColor = Op.getBool(Op.LW.LwPuzColor.ordinal(), Op.lw);
/*  787 */     LadderwordSolve.drawLadderword(g2);
/*  788 */     Def.dispWithColor = Boolean.valueOf(true);
/*      */   }
/*      */   
/*      */   static void printSol(Graphics2D g2, int left, int top, int width, int height, String solutionPuzzle) {
/*  792 */     loadLadderword(solutionPuzzle);
/*  793 */     setSizesAndOffsets(left, top, width, height, 0);
/*  794 */     Def.dispWithColor = Op.getBool(Op.LW.LwSolColor.ordinal(), Op.lw);
/*  795 */     drawLadderword(g2);
/*  796 */     Def.dispWithColor = Boolean.valueOf(true);
/*  797 */     loadLadderword(Op.lw[Op.LW.LwPuz.ordinal()]);
/*      */   }
/*      */   
/*      */   static void printSolTitle(Graphics2D g2, int left, int top, int width, int height, String solutionPuzzle) {
/*  801 */     loadLadderword(solutionPuzzle);
/*  802 */     Print.outputTextItem(g2, left, top, width, height, "SansSerif", 0, Methods.puzzleTitle);
/*  803 */     loadLadderword(Op.lw[Op.LW.LwPuz.ordinal()]);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static void makeGrid() {
/*  809 */     Methods.havePuzzle = false;
/*  810 */     Grid.xSz = Op.getInt(Op.LW.LwFirstLen.ordinal(), Op.lw) * 3 + 5;
/*  811 */     Grid.ySz = Op.getInt(Op.LW.LwRows.ordinal(), Op.lw);
/*  812 */     Grid.clearGrid(); int y;
/*  813 */     for (y = 0; y < Grid.ySz; y++) {
/*  814 */       for (int x = 0; x < Grid.xSz; x++)
/*  815 */         Grid.mode[x][y] = 4; 
/*      */     } 
/*  817 */     for (y = 0; y < Grid.ySz; y++) {
/*  818 */       Grid.mode[Op.getInt(Op.LW.LwFirstLen.ordinal(), Op.lw) - 1][y] = 5;
/*  819 */       Grid.mode[Op.getInt(Op.LW.LwFirstLen.ordinal(), Op.lw)][y] = 3;
/*  820 */       Grid.mode[2 * Op.getInt(Op.LW.LwFirstLen.ordinal(), Op.lw) + 1][y] = 5;
/*  821 */       Grid.mode[2 * Op.getInt(Op.LW.LwFirstLen.ordinal(), Op.lw) + 2][y] = 3;
/*      */     } 
/*      */     
/*  824 */     Grid.xCur = Grid.yCur = 0;
/*  825 */     NodeList.buildNodeList();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void suggestAnagrams(final int row) {
/*  833 */     final JDialog jdlgAnagrams = new JDialog(jfLadderword, "Suggested Anagrams", true);
/*  834 */     jdlgAnagrams.setSize(310, 510);
/*  835 */     jdlgAnagrams.setResizable(false);
/*  836 */     jdlgAnagrams.setLayout((LayoutManager)null);
/*  837 */     jdlgAnagrams.setLocation(jfLadderword.getX(), jfLadderword.getY());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  846 */     DefaultListModel<String> lmMatch = new DefaultListModel<>();
/*  847 */     final JList<String> jl = new JList<>(lmMatch);
/*  848 */     JScrollPane jsp = new JScrollPane(jl);
/*  849 */     jsp.setSize(285, 385);
/*  850 */     jsp.setLocation(10, 31);
/*  851 */     jdlgAnagrams.add(jsp);
/*      */     
/*  853 */     findAnagrams((char)Grid.letter[Op.getInt(Op.LW.LwFirstLen.ordinal(), Op.lw)][row], (char)Grid.letter[2 * Op.getInt(Op.LW.LwFirstLen.ordinal(), Op.lw) + 2][row], Op.getInt(Op.LW.LwFirstLen.ordinal(), Op.lw), true);
/*  854 */     this.anagrams.stream()
/*  855 */       .forEach(anagram -> paramDefaultListModel.addElement(anagram));
/*      */ 
/*      */ 
/*      */     
/*  859 */     this.anagrams.clear();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  868 */     Action doOK = new AbstractAction("OK")
/*      */       {
/*      */         public void actionPerformed(ActionEvent e) {
/*  871 */           if (jl.getSelectedIndex() != -1) {
/*  872 */             String s = ((String)jl.getSelectedValue()).trim();
/*  873 */             for (int j = 0, i = j; i < 3 * Op.getInt(Op.LW.LwFirstLen.ordinal(), Op.lw) + 5; i++, j++) {
/*  874 */               if (s.charAt(j) == ' ') { i++; j += 3; }
/*  875 */                Grid.letter[i][row] = s.charAt(j);
/*      */             } 
/*  877 */             int st = (row == 0) ? 0 : (2 + 3 * row);
/*  878 */             int inc = (row == 0) ? 2 : 1;
/*  879 */             (NodeList.nodeList[st]).word = s.substring(0, Op.getInt(Op.LW.LwFirstLen.ordinal(), Op.lw));
/*  880 */             (NodeList.nodeList[st += inc]).word = s.substring(Op.getInt(Op.LW.LwFirstLen.ordinal(), Op.lw) + 3, 2 * Op.getInt(Op.LW.LwFirstLen.ordinal(), Op.lw) + 4);
/*  881 */             (NodeList.nodeList[st += inc]).word = s.substring(2 * Op.getInt(Op.LW.LwFirstLen.ordinal(), Op.lw) + 7, 3 * Op.getInt(Op.LW.LwFirstLen.ordinal(), Op.lw) + 9);
/*  882 */             NodeList.attachClues(Op.lw[Op.LW.LwDic.ordinal()], Boolean.valueOf(false));
/*  883 */             NodeList.sortNodeList(2);
/*  884 */             NodeList.rebuildHorzAndVert();
/*      */             
/*  886 */             Methods.clickedOK = true;
/*  887 */             jdlgAnagrams.dispose();
/*  888 */             Methods.closeHelp();
/*      */           } 
/*      */         }
/*      */       };
/*  892 */     JButton jbOK = Methods.newButton("doOK", doOK, 79, 40, 442, 100, 26);
/*  893 */     jdlgAnagrams.add(jbOK);
/*      */     
/*  895 */     Action doCancel = new AbstractAction("Cancel") {
/*      */         public void actionPerformed(ActionEvent e) {
/*  897 */           Methods.clickedOK = false;
/*  898 */           jdlgAnagrams.dispose();
/*      */         }
/*      */       };
/*  901 */     JButton jbCancel = Methods.newButton("doCancel", doCancel, 67, 160, 442, 100, 26);
/*  902 */     jdlgAnagrams.add(jbCancel);
/*      */     
/*  904 */     jdlgAnagrams.getRootPane().setDefaultButton(jbOK);
/*  905 */     Methods.setDialogSize(jdlgAnagrams, 305, 478);
/*      */   }
/*      */   
/*      */   void findAnagrams(char ch1, char ch2, int len1, boolean mode) {
/*  909 */     int s1 = 0, s2 = 0, s3 = 0;
/*  910 */     String descriptor = "";
/*      */     
/*  912 */     List<String> words = new ArrayList<>();
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  917 */       DataInputStream dataIn = new DataInputStream(new FileInputStream(Op.lw[Op.LW.LwDic.ordinal()] + ".dic/xword.dic")); int j;
/*  918 */       for (j = 0; j < 128; j++)
/*  919 */         dataIn.readByte(); 
/*  920 */       while (dataIn.available() > 2) {
/*  921 */         int index; dataIn.readInt();
/*  922 */         String dicWord = dataIn.readUTF();
/*  923 */         char[] thisWord = dicWord.toCharArray();
/*  924 */         dataIn.readUTF();
/*  925 */         int len = dicWord.length();
/*  926 */         if (len == len1) {
/*  927 */           index = 1;
/*  928 */         } else if (len == len1 + 1) {
/*  929 */           if ((j = dicWord.indexOf(ch1)) == -1)
/*  930 */             continue;  thisWord[j] = '\001';
/*  931 */           index = 2;
/*      */         }
/*      */         else {
/*      */           
/*  935 */           thisWord[j] = '\001';
/*  936 */           if (len != len1 + 2 || (j = dicWord.indexOf(ch1)) == -1 || (j = (new String(thisWord)).indexOf(ch2)) == -1)
/*  937 */             continue;  thisWord[j] = '\001';
/*  938 */           index = 3;
/*      */         } 
/*      */ 
/*      */         
/*  942 */         for (j = 0; j < len - 1; j++) {
/*  943 */           for (int k = j + 1; k < len; k++) {
/*  944 */             if (thisWord[j] > thisWord[k])
/*  945 */             { char ch = thisWord[j];
/*  946 */               thisWord[j] = thisWord[k];
/*  947 */               thisWord[k] = ch; } 
/*      */           } 
/*  949 */         }  String result = new String(thisWord, index - 1, len1) + index + dicWord;
/*  950 */         words.add(result);
/*      */       } 
/*  952 */       dataIn.close();
/*      */     }
/*  954 */     catch (IOException exc) {}
/*      */     
/*  956 */     Collections.sort(words);
/*      */     
/*  958 */     for (int i = 0, state = i; i < words.size(); i++) {
/*  959 */       String str = words.get(i);
/*  960 */       String desc = str.substring(0, len1);
/*  961 */       int seq = str.charAt(len1) - 48;
/*  962 */       switch (state) {
/*      */         case 0:
/*  964 */           if (seq == 1) {
/*  965 */             descriptor = desc;
/*  966 */             s1 = i;
/*  967 */             state = 1;
/*      */           } 
/*      */           break;
/*      */         case 1:
/*  971 */           if (descriptor.equals(desc)) {
/*  972 */             if (seq == 2) {
/*  973 */               s2 = i;
/*  974 */               state = 2;
/*      */             } 
/*      */             break;
/*      */           } 
/*  978 */           if (seq == 1) {
/*  979 */             descriptor = desc;
/*  980 */             s1 = i;
/*  981 */             state = 1;
/*      */             break;
/*      */           } 
/*  984 */           state = 0;
/*      */           break;
/*      */         case 2:
/*  987 */           if (descriptor.equals(desc)) {
/*  988 */             if (seq == 3) {
/*  989 */               s3 = i;
/*  990 */               state = 3;
/*      */             } 
/*      */             break;
/*      */           } 
/*  994 */           if (seq == 1) {
/*  995 */             descriptor = desc;
/*  996 */             s1 = i;
/*  997 */             state = 1;
/*      */             break;
/*      */           } 
/* 1000 */           state = 0;
/*      */           break;
/*      */         case 3:
/* 1003 */           if (!descriptor.equals(desc) || i == words.size() - 1) {
/* 1004 */             int end = (i == words.size() - 1) ? words.size() : i;
/* 1005 */             for (int x = s1; x < s2; x++) {
/* 1006 */               for (int y = s2; y < s3; y++)
/* 1007 */               { for (int z = s3; z < end; z++)
/* 1008 */                   this.anagrams.add((mode ? "  " : "") + ((String)words.get(x)).substring(len1 + 1) + (mode ? "   " : (String)Character.valueOf(ch1)) + ((String)words.get(y)).substring(len1 + 1) + (mode ? "   " : (String)Character.valueOf(ch2)) + ((String)words.get(z)).substring(len1 + 1));  } 
/* 1009 */             }  if (seq == 1) {
/* 1010 */               descriptor = desc;
/* 1011 */               s1 = i;
/* 1012 */               state = 1;
/*      */               break;
/*      */             } 
/* 1015 */             state = 0;
/*      */           } 
/*      */           break;
/*      */       } 
/*      */     } 
/* 1020 */     Collections.sort(this.anagrams);
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean buildLadderword() {
/* 1025 */     Random r = new Random();
/* 1026 */     boolean built = true;
/*      */     
/* 1028 */     NodeList.sortNodeList(1); int n;
/* 1029 */     for (int i = 0; i < Op.getInt(Op.LW.LwRows.ordinal(), Op.lw); i++) {
/* 1030 */       if (Def.building == 2)
/* 1031 */         return false; 
/* 1032 */       findAnagrams(anchor1.charAt(i), anchor2.charAt(i), Op.getInt(Op.LW.LwFirstLen.ordinal(), Op.lw), false); int k;
/* 1033 */       if ((k = this.anagrams.size()) > 0) {
/* 1034 */         k = r.nextInt(k);
/* 1035 */         String s = this.anagrams.get(k);
/* 1036 */         for (int j = 0; j < 3 * Op.getInt(Op.LW.LwFirstLen.ordinal(), Op.lw) + 5; j++)
/* 1037 */           Grid.letter[j][i] = s.charAt(j); 
/* 1038 */         (NodeList.nodeList[n++]).word = s.substring(0, Op.getInt(Op.LW.LwFirstLen.ordinal(), Op.lw));
/* 1039 */         (NodeList.nodeList[n++]).word = s.substring(Op.getInt(Op.LW.LwFirstLen.ordinal(), Op.lw) + 1, 2 * Op.getInt(Op.LW.LwFirstLen.ordinal(), Op.lw) + 2);
/* 1040 */         (NodeList.nodeList[n++]).word = s.substring(2 * Op.getInt(Op.LW.LwFirstLen.ordinal(), Op.lw) + 3, 3 * Op.getInt(Op.LW.LwFirstLen.ordinal(), Op.lw) + 5);
/*      */       } else {
/* 1042 */         built = false;
/* 1043 */       }  this.anagrams.clear();
/* 1044 */       restoreFrame();
/*      */     } 
/* 1046 */     (NodeList.nodeList[n++]).word = anchor1;
/* 1047 */     (NodeList.nodeList[n++]).word = anchor2;
/*      */     
/* 1049 */     if (built) {
/* 1050 */       NodeList.attachClues(Op.lw[Op.LW.LwDic.ordinal()], Boolean.valueOf(false));
/* 1051 */       NodeList.sortNodeList(2);
/* 1052 */       NodeList.rebuildHorzAndVert();
/*      */     } 
/* 1054 */     restoreFrame();
/* 1055 */     return built;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static void findCurrentNode() {
/* 1061 */     for (Grid.nCur = 0; Grid.nCur < NodeList.nodeListLength; Grid.nCur++) {
/* 1062 */       for (int i = 0; i < (NodeList.nodeList[Grid.nCur]).length; i++) {
/* 1063 */         if (((NodeList.nodeList[Grid.nCur]).cellLoc[i]).x == Grid.xCur && ((NodeList.nodeList[Grid.nCur]).cellLoc[i]).y == Grid.yCur)
/*      */           return; 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   void updateGrid(MouseEvent e) {
/* 1069 */     int x = e.getX(), y = e.getY();
/*      */     
/* 1071 */     if (Def.building == 1)
/* 1072 */       return;  if (x < Grid.xOrg || y < Grid.yOrg)
/* 1073 */       return;  x = (x - Grid.xOrg) / Grid.xCell;
/* 1074 */     y = (y - Grid.yOrg) / Grid.yCell;
/* 1075 */     if (x >= Grid.xSz || y >= Grid.ySz)
/* 1076 */       return;  Grid.yCur = y;
/* 1077 */     Grid.xCur = x;
/* 1078 */     findCurrentNode();
/* 1079 */     restoreFrame();
/*      */   }
/*      */   
/*      */   void handleKeyPressed(KeyEvent e) {
/* 1083 */     if (Def.building == 1)
/* 1084 */       return;  if (e.isAltDown())
/* 1085 */       return;  switch (e.getKeyCode()) { case 38:
/* 1086 */         if (Grid.yCur > 0) Grid.yCur--;  break;
/* 1087 */       case 40: if (Grid.yCur < Grid.ySz - 1) Grid.yCur++;  break;
/* 1088 */       case 37: if (Grid.xCur > 0) Grid.xCur--;  break;
/* 1089 */       case 39: if (Grid.xCur < Grid.xSz - 1) Grid.xCur++;  break;
/* 1090 */       case 33: Grid.yCur = 0; break;
/* 1091 */       case 34: Grid.yCur = Grid.ySz - 1; break;
/* 1092 */       case 36: Grid.xCur = 0; break;
/* 1093 */       case 35: Grid.xCur = Grid.xSz - 1; break; }
/*      */     
/* 1095 */     findCurrentNode();
/* 1096 */     restoreFrame();
/*      */   }
/*      */ }


/* Location:              C:\Users\mustapha\Desktop\Crossword-Express.jar!\crosswordexpress\LadderwordBuild.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */