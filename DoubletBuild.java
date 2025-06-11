/*     */ package crosswordexpress;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JMenuItem;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.KeyStroke;
/*     */ 
/*     */ public class DoubletBuild {
/*     */   static JFrame jfDoublet;
/*     */   static JMenuBar menuBar;
/*     */   JMenu menu;
/*     */   JMenu submenu;
/*     */   JMenuItem menuItem;
/*     */   JMenuItem buildMenuItem;
/*     */   static JPanel pp;
/*  22 */   static String[] Anchor = new String[5]; static int panelW; static int panelH; static JLabel jl1; static JLabel jl2; Thread thread; JList<String> jlWord; DefaultListModel<String> lmWord; int doubletRule;
/*  23 */   int doubletLen = 1;
/*     */   
/*     */   static void def() {
/*  26 */     Op.updateOption(Op.DB.DbW.ordinal(), "500", Op.db);
/*  27 */     Op.updateOption(Op.DB.DbH.ordinal(), "650", Op.db);
/*  28 */     Op.updateOption(Op.DB.DbCell.ordinal(), "FFFFDD", Op.db);
/*  29 */     Op.updateOption(Op.DB.DbGrid.ordinal(), "000000", Op.db);
/*  30 */     Op.updateOption(Op.DB.DbAnchor.ordinal(), "33CCFF", Op.db);
/*  31 */     Op.updateOption(Op.DB.DbFocus.ordinal(), "00CC99", Op.db);
/*  32 */     Op.updateOption(Op.DB.DbLetter.ordinal(), "660033", Op.db);
/*  33 */     Op.updateOption(Op.DB.DbID.ordinal(), "006633", Op.db);
/*  34 */     Op.updateOption(Op.DB.DbClue.ordinal(), "666666", Op.db);
/*  35 */     Op.updateOption(Op.DB.DbError.ordinal(), "FF0000", Op.db);
/*  36 */     Op.updateOption(Op.DB.DbPuz.ordinal(), "sample.doublet", Op.db);
/*  37 */     Op.updateOption(Op.DB.DbDic.ordinal(), "english", Op.db);
/*  38 */     Op.updateOption(Op.DB.DbFont.ordinal(), "SansSerif", Op.db);
/*  39 */     Op.updateOption(Op.DB.DbIDFont.ordinal(), "SansSerif", Op.db);
/*  40 */     Op.updateOption(Op.DB.DbClueFont.ordinal(), "SansSerif", Op.db);
/*  41 */     Op.updateOption(Op.DB.DbPuzColor.ordinal(), "true", Op.db);
/*  42 */     Op.updateOption(Op.DB.DbSolColor.ordinal(), "true", Op.db);
/*     */   }
/*     */   
/*  45 */   String doubletHelp = "<div>A DOUBLET puzzle consists of a list of words, all of the same length. The first and last words in the list are given, and are called <b>Anchor Words.</b> Each of the intervening words is defined by a clue, just as in a crossword puzzle, but in addition it is formed from the previous word in the list by the application of one of a set of <b>Doublet Rules.</b> Currently, there are three rules, giving rise to a family of three types of DOUBLET puzzles.<br/><br/></div><span class='m'>Menu Functions</span><ul><li/><span class='s'>File Menu</span><ul><li/><span>Select a Dictionary</span><br/>When loading a new puzzle into the Build screen, you begin by selecting the dictionary which was used to build the DOUBLET puzzle which you want to load.<p/><li/><span>Load a Puzzle</span><br/>Then you choose your puzzle from the pool of DOUBLET puzzles currently available in the selected dictionary.<p/><li/><span>SaveAs</span><br/>This option allows you to make an exact copy of the current puzzle using a different file name. The copy will be saved in the folder of the dictionary that was used to construct it. Alternatively, if you don't enter a new name for the puzzle, you can change the Puzzle Description, or any of the other descriptive items without changing the puzzle name.<p/><li/><span>Quit Construction</span><br/>Returns you to the Crossword Express opening screen.<</ul><li/><span class='s'>Build Menu</span><ul><li/><span>Select a Dictionary</span><br/>Use this option to select the dictionary which you want to use to build the new DOUBLET puzzle.<p/><li/><span>Start a New Puzzle</span><br/>This option presents you with a dialog into which you can enter a file name for your new puzzle. You can also enter several other pieces of information such as a <b>Puzzle Title, Author</b> and <b>Copyright</b> information.<p/><li/><span>Build Options</span><br/>Use this option to access a Build Options dialog where you can set some rules to be followed by the puzzle building function as it builds your puzzle.<p/><li/><span>Start Building / Stop Building</span><br/>Construction of the puzzle will commence when you select the <b>Start Building</b> option. If puzzle building is successful you will receive a message containing the name of the puzzle file, and the location where it was saved. If it becomes necessary, you can interrupt the building process by selecting this option a second time. Note that during the construction phase, the text of this option is changed to <b>Stop Building</b></ul><li/><span class='s'>View Menu</span><ul><li/><span>Display Options</span><br/>This leads you to a dialog box in which you can change the colors of various elements within the puzzle, and control the fonts which will be used for the puzzle's text components. You can also decide if printing of the puzzle will be done in black and white or in color.</ul><li/><span class='s'>Tasks Menu</span><ul><li/><span>Print this Puzzle</span><br/>This will take you to a custom print screen where you can control the details involved with printing your puzzle.<p/><li/><span>Solve this Puzzle</span><br/>This will take you to a Solve screen which provides a fully interactive environment for solving the puzzle.<p/><li/><span>Export Puzzle as Text</span><br/>Under normal circumstances, the Print function will provide all of the layout flexibility you will need when printing your puzzles. Inevitably of course special cases will arise where you need to intervene in the printing of either the words or the clues to achieve some special effect. To meet this need, a text export feature offers the following choices:-<ul><li/><b>Export Words.</b> Each line of text has the format <b>1. WORD</b><li/><b>Export Clues.</b> Each line of text has the format <b>1. Clue</b><li/><b>Export Words and Clues.</b> Each line of text has the format <b>1. WORD : Clue</b><li/><b>Export Puzzle Grid.</b> The puzzle grid is exported as a simple square or rectangular array of letters.</ul>In addition, you have the choice of exporting the text to a text file located anywhere on your computer's hard drive, or to the System Clipboard from where you can Paste into any Word Processor or Desk Top Publishing application.<p/><li/><span>Delete this Puzzle</span><br/>Use this option to eliminate unwanted DOUBLET puzzles from your file system.</ul><li/><span class='s'>Help Menu</span><ul><li/><span>Doublet Help</span><br/>Displays the Help screen which you are now reading.</ul></ul></body>";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 116 */   String doubletOptions = "<div>Before you give the command to build a <b>Doublet</b> puzzle, you must first set some options which the program will use during the construction process.</div><ul><li/>Most importantly, you must enter the <b>Anchor Words.</b> A minimum of two such words are required, but you can enter as many as five if you wish. The program will ensure that any intermediate anchor words will appear in the finished puzzle between the first and last words. All anchor words that you do enter must be of exactly the same length.<p/><li/>Set the <b>Doublet Rule</b> which you want the program to use for this construction. The three available rules are:-<ul><li/><b>Change Letter(s):</b> Each word in the puzzle is formed from the previous word by changing one or more of the letters. The remaining letters retain their identity and location. See below for information on setting the number of letters which change at each step. An example of such a puzzle would be <b>WATCH  LATCH LETCH  LEACH  PEACH  PEACE  PLACE  PLANE  PLANK  PLONK  CLONK  CLOCK</b><p/><li/><b>Change Letter(s) plus Anagram:</b> Each word in the puzzle is formed from the previous word by changing one or more of the letters, and then rearranging the letters in the manner of an Anagram. See below for information on setting the number of letters which change at each step. An example of such a puzzle would be <b>WATCH  WHACK  CHALK  CLOAK  CLOCK</b><p/><li/><b>Link from previous word:</b> Each word in the puzzle begins with the terminating letters of the previous word. See below for information on setting the number of overlapping letters. An example of such a puzzle would be <b>LEMON  ONSET  ETHIC  ICILY  LYNCH CHEAP  APPLE</b></ul><li/>Set the number of <b>Doublet Letters:</b> The number of letters involved in each rule can be set to 1, 2, 3 or 4. For the first two rules, increasing the number of Doublet Letters will improve the chances of successfully building a puzzle. For the third rule, decreasing the number of Doublet letters will have the same effect.</ul></body>";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   DoubletBuild(JFrame jf) {
/* 141 */     Def.puzzleMode = 50;
/* 142 */     Def.dispCursor = Boolean.valueOf(false);
/* 143 */     Def.building = 0;
/* 144 */     makeGrid();
/*     */     
/* 146 */     jfDoublet = new JFrame("Doublet Construction");
/* 147 */     jfDoublet.setSize(Op.getInt(Op.DB.DbW.ordinal(), Op.db), Op.getInt(Op.DB.DbH.ordinal(), Op.db));
/* 148 */     int frameX = (jf.getX() + jfDoublet.getWidth() > Methods.scrW) ? (Methods.scrW - jfDoublet.getWidth() - 10) : jf.getX();
/* 149 */     jfDoublet.setLocation(frameX, jf.getY());
/* 150 */     jfDoublet.setLayout((LayoutManager)null);
/* 151 */     jfDoublet.setDefaultCloseOperation(0);
/* 152 */     jfDoublet
/* 153 */       .addComponentListener(new ComponentAdapter()
/*     */         {
/*     */           public void componentResized(ComponentEvent ce) {
/* 156 */             int w = (DoubletBuild.jfDoublet.getWidth() < 500) ? 500 : DoubletBuild.jfDoublet.getWidth();
/* 157 */             int h = (DoubletBuild.jfDoublet.getHeight() < 650) ? 650 : DoubletBuild.jfDoublet.getHeight();
/* 158 */             DoubletBuild.jfDoublet.setSize(w, h);
/* 159 */             Op.setInt(Op.DB.DbW.ordinal(), w, Op.db);
/* 160 */             Op.setInt(Op.DB.DbH.ordinal(), h, Op.db);
/* 161 */             DoubletBuild.restoreFrame();
/*     */           }
/*     */         });
/*     */     
/* 165 */     jfDoublet
/* 166 */       .addWindowListener(new WindowAdapter() {
/*     */           public void windowClosing(WindowEvent we) {
/* 168 */             if (Def.building == 1 || Def.selecting)
/* 169 */               return;  Op.saveOptions("doublet.opt", Op.db);
/* 170 */             CrosswordExpress.transfer(1, DoubletBuild.jfDoublet);
/*     */           }
/*     */         });
/*     */     
/* 174 */     Methods.closeHelp();
/*     */ 
/*     */     
/* 177 */     Runnable buildThread = () -> {
/*     */         pp.repaint();
/*     */         
/*     */         Wait.shortWait(50);
/*     */         
/*     */         Methods.havePuzzle = buildDoublet().booleanValue();
/*     */         
/*     */         this.buildMenuItem.setText("Start Building");
/*     */         if (Def.building == 2) {
/*     */           Def.building = 0;
/*     */           Methods.interrupted(jfDoublet);
/*     */           makeGrid();
/*     */           restoreFrame();
/*     */           return;
/*     */         } 
/*     */         Def.building = 0;
/*     */         if (Methods.havePuzzle) {
/*     */           saveDoublet(Op.db[Op.DB.DbPuz.ordinal()]);
/*     */           restoreFrame();
/*     */           Methods.puzzleSaved(jfDoublet, Op.db[Op.DB.DbDic.ordinal()] + ".dic", Op.db[Op.DB.DbPuz.ordinal()]);
/*     */         } else {
/*     */           makeGrid();
/*     */           restoreFrame();
/*     */           Methods.cantBuild(jfDoublet);
/*     */         } 
/*     */       };
/* 203 */     jl1 = new JLabel(); jfDoublet.add(jl1);
/* 204 */     jl2 = new JLabel(); jfDoublet.add(jl2);
/*     */ 
/*     */     
/* 207 */     menuBar = new JMenuBar();
/* 208 */     menuBar.setBackground(Def.COLOR_MENUBAR);
/* 209 */     jfDoublet.setJMenuBar(menuBar);
/*     */     
/* 211 */     this.menu = new JMenu("File");
/* 212 */     menuBar.add(this.menu);
/* 213 */     this.menuItem = new JMenuItem("Select a Dictionary");
/* 214 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(68, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 215 */     this.menu.add(this.menuItem);
/* 216 */     this.menuItem
/* 217 */       .addActionListener(ae -> {
/*     */           if (Def.building == 1) {
/*     */             return;
/*     */           }
/*     */           Methods.selectDictionary(jfDoublet, Op.db[Op.DB.DbDic.ordinal()], 1);
/*     */           Op.db[Op.DB.DbDic.ordinal()] = Methods.dictionaryName;
/*     */           loadDoublet(Op.db[Op.DB.DbPuz.ordinal()]);
/*     */           restoreFrame();
/*     */         });
/* 226 */     this.menuItem = new JMenuItem("Load a Puzzle");
/* 227 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(76, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 228 */     this.menu.add(this.menuItem);
/* 229 */     this.menuItem
/* 230 */       .addActionListener(ae -> {
/*     */           if (Def.building == 1)
/*     */             return; 
/*     */           pp.invalidate();
/*     */           pp.repaint();
/*     */           new Select(jfDoublet, Op.db[Op.DB.DbDic.ordinal()] + ".dic", "doublet", Op.db, Op.DB.DbPuz.ordinal(), false);
/*     */         });
/* 237 */     this.menuItem = new JMenuItem("SaveAs");
/* 238 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(65, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 239 */     this.menu.add(this.menuItem);
/* 240 */     this.menuItem
/* 241 */       .addActionListener(ae -> {
/*     */           if (Def.building == 1) {
/*     */             return;
/*     */           }
/*     */           Methods.puzzleDescriptionDialog(jfDoublet, Op.db[Op.DB.DbPuz.ordinal()].substring(0, Op.db[Op.DB.DbPuz.ordinal()].indexOf(".doublet")), Op.db[Op.DB.DbDic.ordinal()] + ".dic", ".doublet");
/*     */           if (Methods.clickedOK) {
/*     */             saveDoublet(Op.db[Op.DB.DbPuz.ordinal()] = Methods.theFileName);
/*     */             restoreFrame();
/*     */             Methods.puzzleSaved(jfDoublet, Op.db[Op.DB.DbDic.ordinal()] + ".dic", Op.db[Op.DB.DbPuz.ordinal()]);
/*     */           } 
/*     */         });
/* 252 */     this.menuItem = new JMenuItem("Quit Construction");
/* 253 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(81, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 254 */     this.menu.add(this.menuItem);
/* 255 */     this.menuItem
/* 256 */       .addActionListener(ae -> {
/*     */           if (Def.building == 1) {
/*     */             return;
/*     */           }
/*     */           
/*     */           Op.saveOptions("doublet.opt", Op.db);
/*     */           CrosswordExpress.transfer(1, jfDoublet);
/*     */         });
/* 264 */     this.menu = new JMenu("Build");
/* 265 */     menuBar.add(this.menu);
/* 266 */     this.menuItem = new JMenuItem("Select a Dictionary");
/* 267 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(68, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 268 */     this.menu.add(this.menuItem);
/* 269 */     this.menuItem
/* 270 */       .addActionListener(ae -> {
/*     */           if (Def.building == 1) {
/*     */             return;
/*     */           }
/*     */           Methods.selectDictionary(jfDoublet, Op.db[Op.DB.DbDic.ordinal()], 1);
/*     */           Op.db[Op.DB.DbDic.ordinal()] = Methods.dictionaryName;
/*     */           loadDoublet(Op.db[Op.DB.DbPuz.ordinal()]);
/*     */           restoreFrame();
/*     */         });
/* 279 */     this.menuItem = new JMenuItem("Start a new Puzzle");
/* 280 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(78, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 281 */     this.menu.add(this.menuItem);
/* 282 */     this.menuItem
/* 283 */       .addActionListener(ae -> {
/*     */           if (Def.building == 1) {
/*     */             return;
/*     */           }
/*     */           Methods.puzzleDescriptionDialog(jfDoublet, Op.db[Op.DB.DbPuz.ordinal()].substring(0, Op.db[Op.DB.DbPuz.ordinal()].indexOf(".doublet")), Op.db[Op.DB.DbDic.ordinal()] + ".dic", ".doublet");
/*     */           if (Methods.clickedOK) {
/*     */             Op.db[Op.DB.DbPuz.ordinal()] = Methods.theFileName;
/*     */             makeGrid();
/*     */           } 
/*     */           restoreFrame();
/*     */         });
/* 294 */     this.menuItem = new JMenuItem("Build Options");
/* 295 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(79, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 296 */     this.menu.add(this.menuItem);
/* 297 */     this.menuItem
/* 298 */       .addActionListener(ae -> {
/*     */           if (Def.building == 1) {
/*     */             return;
/*     */           }
/*     */           doubletOptions();
/*     */           if (Methods.clickedOK)
/*     */             makeGrid(); 
/*     */           restoreFrame();
/*     */         });
/* 307 */     this.buildMenuItem = new JMenuItem("Start Building");
/* 308 */     this.buildMenuItem.setAccelerator(KeyStroke.getKeyStroke(66, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 309 */     this.menu.add(this.buildMenuItem);
/* 310 */     this.buildMenuItem
/* 311 */       .addActionListener(ae -> {
/*     */           if (Op.db[Op.DB.DbPuz.ordinal()].length() == 0) {
/*     */             Methods.noName(jfDoublet);
/*     */             
/*     */             return;
/*     */           } 
/*     */           
/*     */           if (Anchor[0] == null || Anchor[0].length() == 0) {
/*     */             JOptionPane.showMessageDialog(jfDoublet, "<html>Please use <font color=880000>Doublet Options</font> to enter two or more Anchor Words of equal length.");
/*     */             
/*     */             return;
/*     */           } 
/*     */           if (Def.building == 0) {
/*     */             this.thread = new Thread(paramRunnable);
/*     */             this.thread.start();
/*     */             Def.building = 1;
/*     */             this.buildMenuItem.setText("Stop Building");
/*     */           } else {
/*     */             Def.building = 2;
/*     */           } 
/*     */         });
/* 332 */     this.menu = new JMenu("View");
/* 333 */     menuBar.add(this.menu);
/* 334 */     this.menuItem = new JMenuItem("Display Options");
/* 335 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(86, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 336 */     this.menu.add(this.menuItem);
/* 337 */     this.menuItem
/* 338 */       .addActionListener(ae -> {
/*     */           if (Def.building == 1) {
/*     */             return;
/*     */           }
/*     */           
/*     */           printOptions(jfDoublet, "Display Options");
/*     */           restoreFrame();
/*     */         });
/* 346 */     this.menu = new JMenu("Tasks");
/* 347 */     menuBar.add(this.menu);
/* 348 */     this.menuItem = new JMenuItem("Print this Puzzle");
/* 349 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(80, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 350 */     this.menu.add(this.menuItem);
/* 351 */     this.menuItem
/* 352 */       .addActionListener(ae -> {
/*     */           if (Def.building == 1) {
/*     */             return;
/*     */           }
/*     */           CrosswordExpress.toPrint(jfDoublet, Op.db[Op.DB.DbPuz.ordinal()]);
/*     */         });
/* 358 */     this.menuItem = new JMenuItem("Solve this Puzzle");
/* 359 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(86, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 360 */     this.menu.add(this.menuItem);
/* 361 */     this.menuItem
/* 362 */       .addActionListener(ae -> {
/*     */           if (Def.building == 1)
/*     */             return; 
/*     */           if (Methods.havePuzzle) {
/*     */             CrosswordExpress.transfer(51, jfDoublet);
/*     */           } else {
/*     */             Methods.noPuzzle(jfDoublet, "Solve");
/*     */           } 
/*     */         });
/* 371 */     this.menuItem = new JMenuItem("Export as Text");
/* 372 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(84, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 373 */     this.menu.add(this.menuItem);
/* 374 */     this.menuItem
/* 375 */       .addActionListener(ae -> {
/*     */           if (Def.building == 1)
/*     */             return; 
/*     */           if (Methods.havePuzzle) {
/*     */             NodeList.exportText(jfDoublet, true);
/*     */           } else {
/*     */             Methods.noPuzzle(jfDoublet, "Export");
/*     */           } 
/*     */         });
/* 384 */     this.menuItem = new JMenuItem("Delete this Puzzle");
/* 385 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(90, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 386 */     this.menu.add(this.menuItem);
/* 387 */     this.menuItem
/* 388 */       .addActionListener(ae -> {
/*     */           if (Def.building == 1) {
/*     */             return;
/*     */           }
/*     */           
/*     */           if (Methods.deleteAPuzzle(jfDoublet, Op.db[Op.DB.DbPuz.ordinal()], Op.db[Op.DB.DbDic.ordinal()] + ".dic", pp)) {
/*     */             loadDoublet(Op.db[Op.DB.DbPuz.ordinal()]);
/*     */             restoreFrame();
/*     */           } 
/*     */         });
/* 398 */     this.menu = new JMenu("Help");
/* 399 */     menuBar.add(this.menu);
/* 400 */     this.menuItem = new JMenuItem("Doublet Help");
/* 401 */     this.menu.add(this.menuItem);
/* 402 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(72, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 403 */     this.menuItem
/* 404 */       .addActionListener(ae -> Methods.cweHelp(jfDoublet, null, "Building Doublet Puzzles", this.doubletHelp));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 409 */     pp = new DoubletPP(0, 37);
/* 410 */     jfDoublet.add(pp);
/*     */     
/* 412 */     jfDoublet
/* 413 */       .addMouseMotionListener(new MouseAdapter() {
/*     */           public void mouseMoved(MouseEvent e) {
/* 415 */             if (Def.isMac) {
/* 416 */               DoubletBuild.jfDoublet.setResizable((DoubletBuild.jfDoublet.getWidth() - e.getX() < 15 && DoubletBuild.jfDoublet
/* 417 */                   .getHeight() - e.getY() < 15));
/*     */             }
/*     */           }
/*     */         });
/*     */     
/* 422 */     loadDoublet(Op.db[Op.DB.DbPuz.ordinal()]);
/* 423 */     restoreFrame();
/*     */   }
/*     */   
/*     */   static void restoreFrame() {
/* 427 */     jfDoublet.setVisible(true);
/* 428 */     Insets insets = jfDoublet.getInsets();
/* 429 */     panelW = jfDoublet.getWidth() - insets.left + insets.right;
/* 430 */     panelH = jfDoublet.getHeight() - insets.top + insets.bottom + 37 + menuBar.getHeight();
/* 431 */     pp.setSize(panelW, panelH);
/* 432 */     jfDoublet.requestFocusInWindow();
/* 433 */     pp.repaint();
/* 434 */     Methods.infoPanel(jl1, jl2, "Build Doublet", "Dictionary : " + Op.db[Op.DB.DbDic.ordinal()] + "  -|-  Puzzle : " + Op.db[Op.DB.DbPuz
/* 435 */           .ordinal()], panelW);
/*     */   }
/*     */   
/*     */   static void setSizesAndOffsets(int x, int y, int width, int height, int inset) {
/* 439 */     int i = (width - 20) / Grid.xSz;
/* 440 */     int j = (height - 20) / Grid.ySz;
/* 441 */     Grid.xCell = Grid.yCell = (i < j) ? i : j;
/* 442 */     Grid.xOrg = x + (width - Grid.xCell * Grid.xSz) / 2;
/* 443 */     Grid.yOrg = y + (height - Grid.yCell * Grid.ySz) / 2;
/*     */   }
/*     */   
/*     */   private void doubletOptions() {
/* 447 */     JTextField[] jtfAnchor = new JTextField[5];
/*     */ 
/*     */     
/* 450 */     String[] rule = { "Change Letter(s)", "Change Letter(s) Plus Anagram", "Link from Previous Word" };
/*     */     
/* 452 */     JDialog jdlgDoublet = new JDialog(jfDoublet, "Doublet Options", true);
/* 453 */     jdlgDoublet.setSize(280, 374);
/* 454 */     jdlgDoublet.setResizable(false);
/* 455 */     jdlgDoublet.setLayout((LayoutManager)null);
/* 456 */     jdlgDoublet.setLocation(jfDoublet.getX(), jfDoublet.getY());
/*     */     
/* 458 */     jdlgDoublet
/* 459 */       .addWindowListener(new WindowAdapter() {
/*     */           public void windowClosing(WindowEvent we) {
/* 461 */             Methods.closeHelp();
/*     */           }
/*     */         });
/*     */     
/* 465 */     Methods.closeHelp();
/*     */     
/* 467 */     JLabel jlAnchor = new JLabel("Enter 2 to 5 Anchor Words:");
/* 468 */     jlAnchor.setForeground(Def.COLOR_LABEL);
/* 469 */     jlAnchor.setSize(250, 20);
/* 470 */     jlAnchor.setLocation(10, 5);
/* 471 */     jlAnchor.setHorizontalAlignment(2);
/* 472 */     jdlgDoublet.add(jlAnchor);
/*     */     
/* 474 */     for (int i = 0; i < 5; i++) {
/* 475 */       jtfAnchor[i] = new JTextField(Anchor[i], 15);
/* 476 */       jtfAnchor[i].setSize(255, 23);
/* 477 */       jtfAnchor[i].setLocation(10, 25 + i * 28);
/* 478 */       jtfAnchor[i].selectAll();
/* 479 */       jtfAnchor[i].setHorizontalAlignment(2);
/* 480 */       jdlgDoublet.add(jtfAnchor[i]);
/* 481 */       jtfAnchor[i].setFont(new Font("SansSerif", 1, 13));
/*     */     } 
/*     */     
/* 484 */     JLabel jlRule = new JLabel("Select the Doublet Rule:");
/* 485 */     jlRule.setForeground(Def.COLOR_LABEL);
/* 486 */     jlRule.setSize(250, 20);
/* 487 */     jlRule.setLocation(10, 162);
/* 488 */     jlRule.setHorizontalAlignment(2);
/* 489 */     jdlgDoublet.add(jlRule);
/*     */     
/* 491 */     JComboBox<String> jcbbRule = new JComboBox<>(rule);
/* 492 */     jcbbRule.setSize(215, 25);
/* 493 */     jcbbRule.setLocation(10, 185);
/* 494 */     jcbbRule.setBackground(Def.COLOR_BUTTONBG);
/* 495 */     jcbbRule.setSelectedIndex(this.doubletRule);
/* 496 */     jdlgDoublet.add(jcbbRule);
/*     */     
/* 498 */     JLabel jlLetters = new JLabel("Select number of Doublet Letters:");
/* 499 */     jlLetters.setForeground(Def.COLOR_LABEL);
/* 500 */     jlLetters.setSize(250, 20);
/* 501 */     jlLetters.setLocation(10, 214);
/* 502 */     jlLetters.setHorizontalAlignment(2);
/* 503 */     jdlgDoublet.add(jlLetters);
/*     */     
/* 505 */     JComboBox<Integer> jcbbLen = new JComboBox<>();
/* 506 */     for (int j = 1; j <= 4; j++)
/* 507 */       jcbbLen.addItem(Integer.valueOf(j)); 
/* 508 */     jcbbLen.setSize(100, 25);
/* 509 */     jcbbLen.setLocation(10, 237);
/* 510 */     jcbbLen.setBackground(Def.COLOR_BUTTONBG);
/* 511 */     jcbbLen.setSelectedIndex(this.doubletLen - 1);
/* 512 */     jdlgDoublet.add(jcbbLen);
/*     */     
/* 514 */     JButton jbOK = Methods.cweButton("OK", 10, 272, 90, 26, null);
/* 515 */     jbOK.addActionListener(e -> {
/*     */           int count = 0;
/*     */           for (int i = 0; i < 5; i++) {
/*     */             Anchor[i] = paramArrayOfJTextField[i].getText().toUpperCase();
/*     */             if (Anchor[i].length() > 0)
/*     */               count++; 
/*     */             if (i > 0 && Anchor[i].length() > 0 && Anchor[i].length() != Anchor[i - 1].length()) {
/*     */               JOptionPane.showMessageDialog(paramJDialog, "All Anchor Words must be the same length!");
/*     */               return;
/*     */             } 
/*     */           } 
/*     */           if (count == 1) {
/*     */             JOptionPane.showMessageDialog(paramJDialog, "There must be more than one Anchor Word!");
/*     */             return;
/*     */           } 
/*     */           this.doubletRule = paramJComboBox1.getSelectedIndex();
/*     */           this.doubletLen = paramJComboBox2.getSelectedIndex() + 1;
/*     */           paramJDialog.dispose();
/*     */           Methods.closeHelp();
/*     */         });
/* 535 */     jdlgDoublet.add(jbOK);
/*     */     
/* 537 */     JButton jbCancel = Methods.cweButton("Cancel", 10, 307, 90, 26, null);
/* 538 */     jbCancel.addActionListener(e -> {
/*     */           Methods.clickedOK = false;
/*     */           paramJDialog.dispose();
/*     */           Methods.closeHelp();
/*     */         });
/* 543 */     jdlgDoublet.add(jbCancel);
/*     */     
/* 545 */     JButton jbHelp = Methods.cweButton("<html><font size=6 color=BB0000 face=Serif>Help ", 110, 272, 155, 61, new ImageIcon("graphics/help.png"));
/* 546 */     jbHelp.addActionListener(e -> Methods.cweHelp(null, paramJDialog, "Doublet Options", this.doubletOptions));
/*     */     
/* 548 */     jdlgDoublet.add(jbHelp);
/*     */     
/* 550 */     jdlgDoublet.getRootPane().setDefaultButton(jbOK);
/* 551 */     Methods.setDialogSize(jdlgDoublet, 275, 343);
/*     */   }
/*     */   
/*     */   static void printOptions(JFrame jf, String type) {
/* 555 */     String[] colorLabel = { "Cell Color", "Grid Color", "Anchor Color", "Focus Color", "Letter Color", "ID Digit Color", "Clue Color", "Error Color" };
/* 556 */     int[] colorInt = { Op.DB.DbCell.ordinal(), Op.DB.DbGrid.ordinal(), Op.DB.DbAnchor.ordinal(), Op.DB.DbFocus.ordinal(), Op.DB.DbLetter.ordinal(), Op.DB.DbID.ordinal(), Op.DB.DbClue.ordinal(), Op.DB.DbError.ordinal() };
/* 557 */     String[] fontLabel = { "Select Puzzle Font", "Select ID Digit Font", "Select Clue Font" };
/* 558 */     int[] fontInt = { Op.DB.DbFont.ordinal(), Op.DB.DbIDFont.ordinal(), Op.DB.DbClueFont.ordinal() };
/* 559 */     String[] checkLabel = { "PPrint Puzzle with color.", "SPrint Solution with color." };
/* 560 */     int[] checkInt = { Op.DB.DbPuzColor.ordinal(), Op.DB.DbSolColor.ordinal() };
/* 561 */     Methods.stdPrintOptions(jf, "Doublet " + type, Op.db, colorLabel, colorInt, fontLabel, fontInt, checkLabel, checkInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void saveDoublet(String doubletName) {
/*     */     try {
/* 570 */       DataOutputStream dataOut = new DataOutputStream(new FileOutputStream(Op.db[Op.DB.DbDic.ordinal()] + ".dic/" + doubletName));
/* 571 */       dataOut.writeInt(Grid.xSz);
/* 572 */       dataOut.writeInt(Grid.ySz);
/* 573 */       dataOut.writeByte(Methods.noReveal);
/* 574 */       dataOut.writeByte(Methods.noErrors);
/* 575 */       for (int k = 0; k < 54; k++)
/* 576 */         dataOut.writeByte(0); 
/* 577 */       for (int j = 0; j < Grid.ySz; j++) {
/* 578 */         for (int m = 0; m < Grid.xSz; m++) {
/* 579 */           dataOut.writeInt(Grid.sol[m][j]);
/* 580 */           dataOut.writeInt(Grid.color[m][j]);
/* 581 */           dataOut.writeInt(Grid.letter[m][j]);
/*     */         } 
/* 583 */       }  dataOut.writeUTF(Methods.puzzleTitle);
/* 584 */       dataOut.writeUTF(Methods.author);
/* 585 */       dataOut.writeUTF(Methods.copyright);
/* 586 */       dataOut.writeUTF(Methods.puzzleNumber);
/* 587 */       dataOut.writeUTF(Methods.puzzleNotes);
/*     */       
/* 589 */       for (int i = 0; i < NodeList.nodeListLength; i++) {
/* 590 */         dataOut.writeUTF((NodeList.nodeList[i]).word);
/* 591 */         dataOut.writeUTF(((NodeList.nodeList[i]).clue == null) ? "No clue" : (NodeList.nodeList[i]).clue);
/*     */       } 
/* 593 */       dataOut.close();
/*     */     }
/* 595 */     catch (IOException exc) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void loadDoublet(String doubletName) {
/* 602 */     Op.db[Op.DB.DbDic.ordinal()] = Methods.confirmDictionary(Op.db[Op.DB.DbDic.ordinal()] + ".dic", false);
/*     */     
/*     */     try {
/* 605 */       File fl = new File(Op.db[Op.DB.DbDic.ordinal()] + ".dic/" + doubletName);
/* 606 */       if (!fl.exists()) {
/* 607 */         fl = new File(Op.db[Op.DB.DbDic.ordinal()] + ".dic/");
/* 608 */         String[] s = fl.list(); int k;
/* 609 */         for (k = 0; k < s.length && (
/* 610 */           s[k].lastIndexOf(".doublet") == -1 || s[k].charAt(0) == '.'); k++);
/*     */         
/* 612 */         if (k == s.length) { makeGrid(); return; }
/* 613 */          doubletName = s[k];
/* 614 */         Op.db[Op.DB.DbPuz.ordinal()] = doubletName;
/*     */       } 
/*     */ 
/*     */       
/* 618 */       DataInputStream dataIn = new DataInputStream(new FileInputStream(Op.db[Op.DB.DbDic.ordinal()] + ".dic/" + doubletName));
/* 619 */       Grid.xSz = dataIn.readInt();
/* 620 */       Grid.ySz = dataIn.readInt();
/* 621 */       Methods.noReveal = dataIn.readByte();
/* 622 */       Methods.noErrors = dataIn.readByte(); int i;
/* 623 */       for (i = 0; i < 54; i++)
/* 624 */         dataIn.readByte(); 
/* 625 */       for (int j = 0; j < Grid.ySz; j++) {
/* 626 */         for (i = 0; i < Grid.xSz; i++) {
/* 627 */           Grid.sol[i][j] = dataIn.readInt();
/* 628 */           Grid.color[i][j] = dataIn.readInt();
/* 629 */           Grid.letter[i][j] = dataIn.readInt();
/*     */         } 
/* 631 */       }  Methods.puzzleTitle = dataIn.readUTF();
/* 632 */       Methods.author = dataIn.readUTF();
/* 633 */       Methods.copyright = dataIn.readUTF();
/* 634 */       Methods.puzzleNumber = dataIn.readUTF();
/* 635 */       Methods.puzzleNotes = dataIn.readUTF();
/*     */       
/* 637 */       for (int anchorCount = i = 0, id = 1; i < Grid.ySz; i++) {
/* 638 */         NodeList.nodeList[NodeList.nodeListLength] = new Node();
/* 639 */         (NodeList.nodeList[NodeList.nodeListLength]).direction = 0;
/* 640 */         (NodeList.nodeList[NodeList.nodeListLength]).id = (Grid.color[0][i] != 16777215) ? 0 : id++;
/* 641 */         (NodeList.nodeList[NodeList.nodeListLength]).x = 0;
/* 642 */         (NodeList.nodeList[NodeList.nodeListLength]).y = i;
/* 643 */         (NodeList.nodeList[NodeList.nodeListLength]).length = Grid.xSz;
/* 644 */         (NodeList.nodeList[NodeList.nodeListLength]).word = dataIn.readUTF();
/* 645 */         (NodeList.nodeList[NodeList.nodeListLength]).clue = dataIn.readUTF();
/* 646 */         if ((NodeList.nodeList[NodeList.nodeListLength]).id == 0)
/* 647 */           Anchor[anchorCount++] = (NodeList.nodeList[NodeList.nodeListLength]).word; 
/* 648 */         NodeList.nodeListLength++;
/*     */       } 
/* 650 */       dataIn.close();
/*     */     }
/* 652 */     catch (IOException exc) {
/*     */       return;
/* 654 */     }  Grid.xCur = 0; Grid.yCur = 1;
/* 655 */     Methods.havePuzzle = true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static void drawDoublet(Graphics2D g2) {
/* 661 */     Stroke normalStroke = new BasicStroke(Grid.xCell / 20.0F, 2, 0);
/* 662 */     Stroke wideStroke = new BasicStroke((Grid.xCell < 30) ? 4.0F : (4 * Grid.xCell / 30), 2, 0);
/* 663 */     g2.setStroke(normalStroke);
/*     */     
/* 665 */     RenderingHints rh = g2.getRenderingHints();
/* 666 */     rh.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
/* 667 */     g2.setRenderingHints(rh);
/*     */     
/*     */     int j;
/* 670 */     for (j = 0; j < Grid.ySz; j++) {
/* 671 */       for (int n = 0; n < Grid.xSz; n++) {
/* 672 */         int theColor; if (Grid.color[n][j] == Op.getColorInt(Op.DB.DbAnchor.ordinal(), Op.db)) {
/* 673 */           theColor = Op.getColorInt(Op.DB.DbAnchor.ordinal(), Op.db);
/*     */         } else {
/* 675 */           theColor = Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.DB.DbCell.ordinal(), Op.db) : 16777215;
/* 676 */         }  g2.setColor(new Color(theColor));
/* 677 */         g2.fillRect(Grid.xOrg + n * Grid.xCell, Grid.yOrg + j * Grid.yCell, Grid.xCell, Grid.yCell);
/*     */       } 
/*     */     } 
/*     */     
/* 681 */     g2.setColor(new Color(Op.getColorInt(Op.DB.DbGrid.ordinal(), Op.db)));
/* 682 */     for (j = 0; j < Grid.ySz; j++) {
/* 683 */       for (int n = 0; n < Grid.xSz; n++)
/* 684 */         g2.drawRect(Grid.xOrg + n * Grid.xCell, Grid.yOrg + j * Grid.yCell, Grid.xCell, Grid.yCell); 
/*     */     } 
/* 686 */     g2.setFont(new Font(Op.db[Op.DB.DbFont.ordinal()], 0, 8 * Grid.yCell / 10));
/* 687 */     g2.setColor(new Color(Op.getColorInt(Op.DB.DbLetter.ordinal(), Op.db)));
/* 688 */     FontMetrics fm = g2.getFontMetrics();
/* 689 */     for (int k = 0; k < Grid.ySz; k++) {
/* 690 */       for (int n = 0; n < Grid.xSz; n++) {
/* 691 */         char ch = (char)Grid.letter[n][k];
/* 692 */         if (ch != '\000') {
/* 693 */           int w = fm.stringWidth("" + ch);
/* 694 */           g2.drawString("" + ch, Grid.xOrg + n * Grid.xCell + (Grid.xCell - w) / 2, Grid.yOrg + k * Grid.yCell + (Grid.yCell + fm.getAscent() - fm.getDescent()) / 2);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 699 */     g2.setColor(new Color(Op.getColorInt(Op.DB.DbID.ordinal(), Op.db)));
/* 700 */     g2.setFont(new Font(Op.db[Op.DB.DbIDFont.ordinal()], 0, Grid.yCell / 3));
/* 701 */     fm = g2.getFontMetrics();
/* 702 */     for (int i = 1, m = 0; m < Grid.ySz; m++) {
/* 703 */       if (Grid.color[0][m] == 16777215 || m == Grid.yCur) {
/* 704 */         g2.drawString("" + i++, Grid.xOrg + Grid.xCell / 10, Grid.yOrg + m * Grid.yCell + fm.getAscent());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void printPuz(Graphics2D g2, int left, int top, int width, int height) {
/* 717 */     loadDoublet(Op.db[Op.DB.DbPuz.ordinal()]);
/* 718 */     for (int y = 0; y < Grid.ySz; y++) {
/* 719 */       for (int x = 0; x < Grid.xSz; x++)
/* 720 */       { if (Grid.color[x][y] == 16777215)
/* 721 */           Grid.sol[x][y] = 0;  } 
/* 722 */     }  setSizesAndOffsets(left, top, width, height, 0);
/* 723 */     DoubletSolve.drawDoublet(g2);
/*     */   }
/*     */   
/*     */   static void printSol(Graphics2D g2, int left, int top, int width, int height, String solutionPuzzle) {
/* 727 */     loadDoublet(solutionPuzzle);
/* 728 */     setSizesAndOffsets(left, top, width, height, 0);
/* 729 */     drawDoublet(g2);
/* 730 */     loadDoublet(Op.db[Op.DB.DbPuz.ordinal()]);
/*     */   }
/*     */   
/*     */   static void printSolTitle(Graphics2D g2, int left, int top, int width, int height, String solutionPuzzle) {
/* 734 */     loadDoublet(solutionPuzzle);
/* 735 */     Print.outputTextItem(g2, left, top, width, height, "SansSerif", 0, Methods.puzzleTitle);
/* 736 */     loadDoublet(Op.db[Op.DB.DbPuz.ordinal()]);
/*     */   }
/*     */   
/*     */   static void makeGrid() {
/* 740 */     Methods.havePuzzle = false;
/* 741 */     Grid.clearGrid();
/* 742 */     Grid.xSz = 5; Grid.ySz = 10;
/*     */   }
/*     */   
/*     */   static void clearSol() {
/* 746 */     for (int j = 0; j < Grid.ySz; j++) {
/* 747 */       if (Grid.color[0][j] == 16777215 || j == Grid.yCur) {
/* 748 */         for (int i = 0; i < Grid.xSz; i++) {
/* 749 */           Grid.sol[i][j] = 0;
/*     */         }
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   Boolean buildDoubletList(String start, String end) {
/* 756 */     Boolean solFound = Boolean.valueOf(false), itemFound = Boolean.valueOf(false);
/*     */     
/* 758 */     Random r = new Random();
/*     */     
/* 760 */     int len = start.length();
/*     */ 
/*     */     
/* 763 */     this.lmWord = new DefaultListModel<>();
/* 764 */     this.jlWord = new JList<>(this.lmWord);
/* 765 */     this.lmWord.addElement(start);
/*     */     
/*     */     try {
/* 768 */       DataInputStream dataIn = new DataInputStream(new FileInputStream(Op.db[Op.DB.DbDic.ordinal()] + ".dic/xword.dic"));
/* 769 */       for (int m = 0; m < 128; m++)
/* 770 */         dataIn.readByte(); 
/* 771 */       while (dataIn.available() > 2) {
/* 772 */         dataIn.readInt();
/* 773 */         String s = dataIn.readUTF();
/* 774 */         if (s.length() == len)
/* 775 */           this.lmWord.addElement(s); 
/* 776 */         dataIn.readUTF();
/*     */       } 
/* 778 */       dataIn.close();
/*     */     }
/* 780 */     catch (IOException exc) {}
/*     */     
/*     */     int i, j;
/* 783 */     for (j = this.lmWord.size(), i = 1; i < j; i++) {
/* 784 */       String s = this.lmWord.getElementAt(i); int m;
/* 785 */       while ((m = r.nextInt(j - 1)) == 0);
/* 786 */       this.lmWord.setElementAt(this.lmWord.getElementAt(m), i);
/* 787 */       this.lmWord.setElementAt(s, m);
/*     */     } 
/* 789 */     int[] link = new int[j];
/*     */     int depositItem;
/* 791 */     for (int indexItem = 0; indexItem != depositItem; indexItem++) {
/* 792 */       if (Def.building == 2) return Boolean.valueOf(false); 
/* 793 */       String copy = this.lmWord.getElementAt(indexItem);
/* 794 */       for (int scanItem = depositItem; scanItem < this.lmWord.size(); scanItem++) {
/* 795 */         int count; char[] ca; String scan = this.lmWord.getElementAt(scanItem);
/* 796 */         switch (this.doubletRule) {
/*     */           case 0:
/* 798 */             for (count = i = 0; i < len; i++) {
/* 799 */               if (scan.charAt(i) != copy.charAt(i))
/* 800 */                 count++; 
/* 801 */             }  itemFound = Boolean.valueOf((count == this.doubletLen));
/*     */             break;
/*     */           case 1:
/* 804 */             ca = copy.toCharArray();
/* 805 */             for (count = i = 0; i < len; i++) {
/* 806 */               for (j = 0; j < len; j++) {
/* 807 */                 if (scan.charAt(i) == ca[j]) {
/* 808 */                   ca[j] = ' '; break;
/*     */                 } 
/*     */               } 
/* 811 */               if (j == len) count++; 
/*     */             } 
/* 813 */             itemFound = Boolean.valueOf((count == this.doubletLen));
/*     */             break;
/*     */           case 2:
/* 816 */             itemFound = Boolean.valueOf(false);
/* 817 */             if (copy.substring(copy.length() - this.doubletLen).equals(scan.substring(0, this.doubletLen))) {
/* 818 */               itemFound = Boolean.valueOf(true);
/*     */             }
/*     */             break;
/*     */         } 
/* 822 */         if (itemFound.booleanValue()) {
/* 823 */           solFound = Boolean.valueOf(scan.equals(end));
/* 824 */           link[depositItem] = indexItem;
/* 825 */           String s = this.lmWord.getElementAt(depositItem);
/* 826 */           this.lmWord.setElementAt(this.lmWord.getElementAt(scanItem), depositItem);
/* 827 */           this.lmWord.setElementAt(s, scanItem);
/* 828 */           depositItem++;
/*     */           
/* 830 */           if (Def.building == 2)
/* 831 */             return Boolean.valueOf(false); 
/* 832 */           if (solFound.booleanValue())
/*     */             break; 
/*     */         } 
/* 835 */       }  if (solFound.booleanValue())
/*     */         break; 
/* 837 */     }  if (!solFound.booleanValue()) return Boolean.valueOf(false);
/*     */ 
/*     */     
/* 840 */     for (i = depositItem; i < this.lmWord.size();) {
/* 841 */       this.lmWord.remove(i);
/*     */     }
/*     */     int k;
/* 844 */     for (i = depositItem - 2, k = link[i + 1]; i > 0; ) {
/* 845 */       while (i > k)
/* 846 */         this.lmWord.remove(i--); 
/* 847 */       k = link[i--];
/*     */     } 
/* 849 */     return Boolean.valueOf(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Boolean buildDoublet() {
/* 856 */     Grid.xSz = Anchor[0].length(); Grid.ySz = 0;
/* 857 */     NodeList.nodeListLength = 0; int id = 1;
/* 858 */     for (int k = 1; k < 5 && 
/* 859 */       Anchor[k] != null && Anchor[k].length() != 0; k++) {
/*     */       
/* 861 */       if (!buildDoubletList(Anchor[k - 1], Anchor[k]).booleanValue()) return Boolean.valueOf(false); 
/* 862 */       for (int j = (NodeList.nodeListLength == 0) ? 0 : 1; j < this.lmWord.size(); j++) {
/* 863 */         String s = this.lmWord.getElementAt(j);
/* 864 */         for (int i = 0; i < Grid.xSz; i++) {
/* 865 */           Grid.letter[i][Grid.ySz] = s.charAt(i);
/* 866 */           if (j == 0 || j == this.lmWord.size() - 1) {
/* 867 */             Grid.color[i][Grid.ySz] = Op.getColorInt(Op.DB.DbAnchor.ordinal(), Op.db);
/* 868 */             Grid.sol[i][Grid.ySz] = s.charAt(i);
/*     */           } else {
/*     */             
/* 871 */             Grid.color[i][Grid.ySz] = 16777215;
/* 872 */             Grid.sol[i][Grid.ySz] = 0;
/*     */           } 
/*     */         } 
/* 875 */         NodeList.nodeList[NodeList.nodeListLength] = new Node();
/* 876 */         (NodeList.nodeList[NodeList.nodeListLength]).direction = 0;
/* 877 */         (NodeList.nodeList[NodeList.nodeListLength]).id = (Grid.color[0][Grid.ySz] == 16777215) ? id++ : 0;
/* 878 */         (NodeList.nodeList[NodeList.nodeListLength]).x = 0;
/* 879 */         (NodeList.nodeList[NodeList.nodeListLength]).y = Grid.ySz;
/* 880 */         (NodeList.nodeList[NodeList.nodeListLength]).length = Grid.xSz;
/* 881 */         (NodeList.nodeList[NodeList.nodeListLength]).word = s;
/* 882 */         NodeList.nodeListLength++;
/* 883 */         Grid.ySz++;
/*     */       } 
/*     */     } 
/* 886 */     if (Grid.ySz == 0) return Boolean.valueOf(false);
/*     */     
/* 888 */     NodeList.attachClues(Op.db[Op.DB.DbDic.ordinal()], Boolean.valueOf(false));
/* 889 */     Grid.yCur = 1; Grid.xCur = 0;
/* 890 */     return Boolean.valueOf(true);
/*     */   }
/*     */ }


/* Location:              C:\Users\mustapha\Desktop\Crossword-Express.jar!\crosswordexpress\DoubletBuild.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */