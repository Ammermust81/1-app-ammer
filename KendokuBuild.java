/*      */ package crosswordexpress;
/*      */ import java.awt.FontMetrics;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.Point;
/*      */ import java.awt.Toolkit;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.io.DataInputStream;
/*      */ import java.io.DataOutputStream;
/*      */ import java.util.Random;
/*      */ import javax.swing.JComboBox;
/*      */ import javax.swing.JDialog;
/*      */ import javax.swing.JLabel;
/*      */ import javax.swing.JMenuItem;
/*      */ import javax.swing.KeyStroke;
/*      */ 
/*      */ public class KendokuBuild extends JPanel {
/*      */   static JFrame jfKendoku;
/*      */   static JMenuBar menuBar;
/*      */   JMenu menu;
/*      */   JMenu submenu;
/*      */   JMenuItem menuItem;
/*      */   JMenuItem buildMenuItem;
/*   23 */   int howMany = 1; static JPanel pp; static int panelW; static int panelH; static JLabel jl1; static JLabel jl2; Timer myTimer; Thread thread; int startPuz = Integer.parseInt((new SimpleDateFormat("yyyyMMdd")).format(new Date())); int hmCount;
/*      */   boolean sixpack;
/*   25 */   static String rules = "Place numbers into the grid so that each row and column contains just one of the digits from 1 to <n1>. The puzzle is divided into domains, and each domain contains a number which is the result of performing the given mathematical operation on the digits contained within the domain.";
/*      */ 
/*      */ 
/*      */   
/*   29 */   byte[][][] status = new byte[9][9][9];
/*      */   int undoIndex;
/*   31 */   int[] undoI = new int[750];
/*   32 */   int[] undoM = new int[750];
/*   33 */   int[] undoS = new int[750];
/*   34 */   int[] undoX = new int[750];
/*   35 */   int[] undoY = new int[750];
/*   36 */   int[] bit = new int[] { 1, 2, 4, 8, 16, 32, 64, 128, 256 };
/*   37 */   int[] invbit = new int[] { 510, 509, 507, 503, 495, 479, 447, 383, 255 };
/*      */   
/*   39 */   String kendokuHelp = "<div><b>KENDOKU</b> puzzles are built on square grids of typically 5x5 cells (although puzzles having sizes in the range 4x4 up to 9x9 can also be made). To solve them you must place numbers into the puzzle cells in such a way that each row and column contains each of the digits from 1 up to the size of the puzzle. In this respect they are similar to <b>Sudoku</b> puzzles. Unlike <b>Sudoku</b> puzzles, you are not given any starting digits. Instead, the puzzle is divided into <b>Domains</b> which are areas surrounded by a bold outline, and containing from two up to four cells. Each domain contains a hint consisting of a number and one of the mathematical symbols <b>+ x &mdash; /</b>. The number is the result of applying the mathematical operation represented by the symbol to the digits contained within the domain. This will provide enough information to allow each of the digits to be determined. Each puzzle has a unique solution, and no guessing is required.<br/><br/></div><span class='m'>Menu Functions</span><ul><li/><span class='s'>File Menu</span><ul><li/><span>Load a Puzzle</span><br/>Use this option to choose your puzzle from the pool of KENDOKU puzzles currently available on your computer.<p/><li/><span>SaveAs</span><br/>This option allows you to make an exact copy of the current puzzle using a different file name. The copy will be saved in the <b>kendoku</b> folder along with all of the Kendoku puzzles you have made. Alternatively, if you don't enter a new name for the puzzle, you can change the Puzzle Description, or any of the other descriptive items without changing the puzzle name.<p/><li/><span>Quit Construction</span><br/>Returns you to the Crossword Express opening screen.</ul><li/><span class='s'>Build Menu</span><ul><li/><span>Start a New Puzzle</span><br/>This option presents you with a dialog into which you can enter a file name for your new puzzle. You can also enter several other pieces of information such as a <b>Puzzle Title, Author</b> and <b>Copyright</b> information.<p/><li/><span>Build Options</span><br/>Use this option to access a Build Options dialog where you can set some rules to be followed by the puzzle building function as it builds your puzzle.<p/><li/><span>Start Building / Stop Building</span><br/>Construction of the puzzle will commence when you select the <b>Start Building</b> option. If puzzle building is successful you will receive a message containing the name of the puzzle file, and the location where it was saved. If it becomes necessary, you can interrupt the building process by selecting this option a second time. Note that during the construction phase, the text of this option is changed to <b>Stop Building</b></ul><li/><span class='s'>View Menu</span><ul><li/><span>Display Options</span><br/>This leads you to a dialog box in which you can change the colors of various elements within the puzzle, and control the fonts which will be used for the puzzle's text components. You can also decide if printing of the puzzle will be done in black and white or in color.</ul><li/><span class='s'>Export Menu</span><br/><ul><li/><span>Print a Kendoku KDP puzzle book.</span><br/>The letters KDP stand for <b>Kindle Direct Publishing</b>. This is a free publishing service operared by Amazon, in which they handle all matters related to printing, advertising and sales of books created by members of the public. A portion of the proceeds are retained by Amazon while the remainder is paid to the author. Fifteen of the Puzzles created by Crossword Express can be printed into PDF format files ready for publication by Amazon. When you select this option, you will be presented with a dialog which allows you to control the process. Please study the Help offered by this dialog before attempting to make use of it.</ul><li/><span class='s'>Tasks Menu</span><ul><li/><span>Print this Puzzle</span><br/>This will take you to a custom print screen where you can control the details involved with printing your puzzle.<p/><li/><span>Solve this Puzzle</span><br/>This will take you to a Solve screen which provides a fully interactive environment for solving the puzzle.<p/><li/><span>Delete this Puzzle</span><br/>Use this option to eliminate unwanted KENDOKU puzzles from your file system.</ul><li/><span class='s'>Help Menu</span><ul><li/><span>Kendoku Help</span><br/>Displays the Help screen which you are now reading.</ul></ul></body>";
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
/*  107 */   String kendokuOptions = "<div>Before you give the command to build a <b>Kendoku</b> puzzle, you might like to set some options which the program will use during the construction process.</div><br/><ul><li/><b>Kendoku Size: </b>Enter a single digit which tells the program how big you want the puzzle to be. This number can range from 4 to 9. To some extent, this will have an impact on the difficulty of solving the puzzle.<p/><li/><b>Difficulty: </b>A combo box allows you to choose between difficulty settings of <b>Easy, Moderate</b> and<b>Hard</b>.<p/><li/>If you want to make a number of puzzles all having the same dimensions, simply type a number into the <b>How many puzzles</b> input field. When you issue the Make command, Crossword Express will make that number of puzzles. The puzzle names will be numbers which represent a date in <b>yyyymmdd</b> format. The default value presented by Crossword Express is always the current date, but you can change this to any date that suits your needs. As the series of puzzles is created, CWE will automatically step on to the next date in the sequence, taking into account such factors as the varying number of days in the months, and of course leap years. Virtually any number of puzzles can be made in a single operation using this feature.<p/><li/><b>HOWEVER:</b> If you prefer a simpler numbering scheme for your puzzles, you can enter any number of 7 digits or less to be used for your first puzzle, and Crossword Express will number the remainder of the puzzles sequentially starting with your number.<p/><li/>If you do choose to make multiple puzzles, then by default, Crossword Express will change the difficulty of the resulting puzzles over a cycle of seven puzzles. This would be useful for a daily newspaper so that the week could start with a very easy puzzle, with quite difficult puzzles reserved for the weekend. If you don't want this feature, clearing the <b>Vary Difficulty on 7 day cycle</b> check-box will disable it.</ul></body>";
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
/*      */   static void def() {
/*  132 */     Op.updateOption(Op.KE.KeW.ordinal(), "500", Op.ke);
/*  133 */     Op.updateOption(Op.KE.KeH.ordinal(), "580", Op.ke);
/*  134 */     Op.updateOption(Op.KE.KeSize.ordinal(), "5", Op.ke);
/*  135 */     Op.updateOption(Op.KE.KeDifficulty.ordinal(), "1", Op.ke);
/*  136 */     Op.updateOption(Op.KE.KeCell.ordinal(), "CCFFEE", Op.ke);
/*  137 */     Op.updateOption(Op.KE.KeGrid.ordinal(), "008888", Op.ke);
/*  138 */     Op.updateOption(Op.KE.KeLine.ordinal(), "993300", Op.ke);
/*  139 */     Op.updateOption(Op.KE.KeNumber.ordinal(), "006666", Op.ke);
/*  140 */     Op.updateOption(Op.KE.KeGuide.ordinal(), "000066", Op.ke);
/*  141 */     Op.updateOption(Op.KE.KeHint.ordinal(), "0000FF", Op.ke);
/*  142 */     Op.updateOption(Op.KE.KeError.ordinal(), "FF0000", Op.ke);
/*  143 */     Op.updateOption(Op.KE.KePuz.ordinal(), "sample.kendoku", Op.ke);
/*  144 */     Op.updateOption(Op.KE.KeFont.ordinal(), "SansSerif", Op.ke);
/*  145 */     Op.updateOption(Op.KE.KeGuideFont.ordinal(), "SansSerif", Op.ke);
/*  146 */     Op.updateOption(Op.KE.KeHintFont.ordinal(), "SansSerif", Op.ke);
/*  147 */     Op.updateOption(Op.KE.KePuzColor.ordinal(), "true", Op.ke);
/*  148 */     Op.updateOption(Op.KE.KeSolColor.ordinal(), "true", Op.ke);
/*      */   }
/*      */   
/*      */   KendokuBuild(JFrame jf, boolean auto, int hm, int start) {
/*  152 */     Def.puzzleMode = 110;
/*  153 */     Grid.xSz = Grid.ySz = Op.getInt(Op.KE.KeSize.ordinal(), Op.ke);
/*  154 */     Def.building = 0;
/*  155 */     Def.dispCursor = Boolean.valueOf(false); Def.dispGuideDigits = Boolean.valueOf(false);
/*  156 */     makeGrid();
/*      */     
/*  158 */     jfKendoku = new JFrame("Kendoku");
/*  159 */     if (Op.getInt(Op.KE.KeH.ordinal(), Op.ke) > Methods.scrH - 200) {
/*  160 */       int diff = Op.getInt(Op.KE.KeH.ordinal(), Op.ke) - Op.getInt(Op.KE.KeW.ordinal(), Op.ke);
/*  161 */       Op.setInt(Op.KE.KeH.ordinal(), Methods.scrH - 200, Op.ke);
/*  162 */       Op.setInt(Op.KE.KeW.ordinal(), Methods.scrH - 200 + diff, Op.ke);
/*  163 */     }  jfKendoku.setSize(Op.getInt(Op.KE.KeW.ordinal(), Op.ke), Op.getInt(Op.KE.KeH.ordinal(), Op.ke));
/*  164 */     int frameX = (jf.getX() + jfKendoku.getWidth() > Methods.scrW) ? (Methods.scrW - jfKendoku.getWidth() - 10) : jf.getX();
/*  165 */     jfKendoku.setLocation(frameX, jf.getY());
/*  166 */     jfKendoku.setLayout((LayoutManager)null);
/*  167 */     jfKendoku.setDefaultCloseOperation(0);
/*  168 */     jfKendoku
/*  169 */       .addComponentListener(new ComponentAdapter() {
/*      */           public void componentResized(ComponentEvent ce) {
/*  171 */             int oldw = Op.getInt(Op.KE.KeW.ordinal(), Op.ke);
/*  172 */             int oldh = Op.getInt(Op.KE.KeH.ordinal(), Op.ke);
/*  173 */             Methods.frameResize(KendokuBuild.jfKendoku, oldw, oldh, 500, 580);
/*  174 */             Op.setInt(Op.KE.KeW.ordinal(), KendokuBuild.jfKendoku.getWidth(), Op.ke);
/*  175 */             Op.setInt(Op.KE.KeH.ordinal(), KendokuBuild.jfKendoku.getHeight(), Op.ke);
/*  176 */             KendokuBuild.restoreFrame();
/*      */           }
/*      */         });
/*      */     
/*  180 */     jfKendoku
/*  181 */       .addWindowListener(new WindowAdapter() {
/*      */           public void windowClosing(WindowEvent we) {
/*  183 */             if (Def.building == 1 || Def.selecting)
/*  184 */               return;  Op.saveOptions("kendoku.opt", Op.ke);
/*  185 */             CrosswordExpress.transfer(1, KendokuBuild.jfKendoku);
/*      */           }
/*      */         });
/*      */     
/*  189 */     Methods.closeHelp();
/*      */ 
/*      */     
/*  192 */     Runnable buildThread = () -> {
/*      */         if (this.howMany == 1) {
/*      */           buildKendoku();
/*      */         } else {
/*      */           multiBuild();
/*      */           
/*      */           if (this.sixpack) {
/*      */             Sixpack.trigger();
/*      */             jfKendoku.dispose();
/*      */             Def.building = 0;
/*      */             return;
/*      */           } 
/*      */         } 
/*      */         this.buildMenuItem.setText("Start Building");
/*      */         if (Def.building == 2) {
/*      */           Def.building = 0;
/*      */           Methods.interrupted(jfKendoku);
/*      */           makeGrid();
/*      */           restoreFrame();
/*      */           return;
/*      */         } 
/*      */         Methods.havePuzzle = true;
/*      */         restoreFrame();
/*      */         Methods.puzzleSaved(jfKendoku, "kendoku", Op.ke[Op.KE.KePuz.ordinal()]);
/*      */         Def.building = 0;
/*      */       };
/*  218 */     jl1 = new JLabel(); jfKendoku.add(jl1);
/*  219 */     jl2 = new JLabel(); jfKendoku.add(jl2);
/*      */ 
/*      */     
/*  222 */     menuBar = new JMenuBar();
/*  223 */     menuBar.setBackground(Def.COLOR_MENUBAR);
/*  224 */     jfKendoku.setJMenuBar(menuBar);
/*      */     
/*  226 */     this.menu = new JMenu("File");
/*  227 */     menuBar.add(this.menu);
/*  228 */     this.menuItem = new JMenuItem("Load a Puzzle");
/*  229 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(76, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  230 */     this.menu.add(this.menuItem);
/*  231 */     this.menuItem
/*  232 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1)
/*      */             return; 
/*      */           pp.invalidate();
/*      */           pp.repaint();
/*      */           new Select(jfKendoku, "kendoku", "kendoku", Op.ke, Op.KE.KePuz.ordinal(), false);
/*      */         });
/*  239 */     this.menuItem = new JMenuItem("SaveAs");
/*  240 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(65, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  241 */     this.menu.add(this.menuItem);
/*  242 */     this.menuItem
/*  243 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           Methods.puzzleDescriptionDialog(jfKendoku, Op.ke[Op.KE.KePuz.ordinal()].substring(0, Op.ke[Op.KE.KePuz.ordinal()].indexOf(".kendoku")), "kendoku", ".kendoku");
/*      */           if (Methods.clickedOK) {
/*      */             saveKendoku(Op.ke[Op.KE.KePuz.ordinal()] = Methods.theFileName);
/*      */             restoreFrame();
/*      */             Methods.puzzleSaved(jfKendoku, "kendoku", Op.ke[Op.KE.KePuz.ordinal()]);
/*      */           } 
/*      */         });
/*  254 */     this.menuItem = new JMenuItem("Quit Construction");
/*  255 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(81, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  256 */     this.menu.add(this.menuItem);
/*  257 */     this.menuItem
/*  258 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           
/*      */           Op.saveOptions("kendoku.opt", Op.ke);
/*      */           CrosswordExpress.transfer(1, jfKendoku);
/*      */         });
/*  266 */     this.menu = new JMenu("Build");
/*  267 */     menuBar.add(this.menu);
/*  268 */     this.menuItem = new JMenuItem("Start a new Puzzle");
/*  269 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(78, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  270 */     this.menu.add(this.menuItem);
/*  271 */     this.menuItem
/*  272 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           Methods.puzzleDescriptionDialog(jfKendoku, Op.ke[Op.KE.KePuz.ordinal()].substring(0, Op.ke[Op.KE.KePuz.ordinal()].indexOf(".kendoku")), "kendoku", ".kendoku");
/*      */           if (Methods.clickedOK) {
/*      */             Op.ke[Op.KE.KePuz.ordinal()] = Methods.theFileName;
/*      */             makeGrid();
/*      */           } 
/*      */           restoreFrame();
/*      */         });
/*  283 */     this.menuItem = new JMenuItem("Build Options");
/*  284 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(79, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  285 */     this.menu.add(this.menuItem);
/*  286 */     this.menuItem
/*  287 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1)
/*      */             return; 
/*      */           kendokuOptions();
/*      */           if (Methods.clickedOK) {
/*      */             makeGrid();
/*      */             if (this.howMany > 1)
/*      */               Op.ke[Op.KE.KePuz.ordinal()] = "" + this.startPuz + ".kendoku"; 
/*      */           } 
/*      */           restoreFrame();
/*      */         });
/*  298 */     this.buildMenuItem = new JMenuItem("Start Building");
/*  299 */     this.buildMenuItem.setAccelerator(KeyStroke.getKeyStroke(66, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  300 */     this.menu.add(this.buildMenuItem);
/*  301 */     this.buildMenuItem
/*  302 */       .addActionListener(ae -> {
/*      */           if (Op.ke[Op.KE.KePuz.ordinal()].length() == 0 && this.howMany == 1) {
/*      */             Methods.noName(jfKendoku);
/*      */             
/*      */             return;
/*      */           } 
/*      */           
/*      */           if (Def.building == 0) {
/*      */             this.thread = new Thread(paramRunnable);
/*      */             
/*      */             this.thread.start();
/*      */             Def.building = 1;
/*      */             this.buildMenuItem.setText("Stop Building");
/*      */           } else {
/*      */             Def.building = 2;
/*      */           } 
/*      */         });
/*  319 */     this.menu = new JMenu("View");
/*  320 */     menuBar.add(this.menu);
/*  321 */     this.menuItem = new JMenuItem("Display Options");
/*  322 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(89, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  323 */     this.menu.add(this.menuItem);
/*  324 */     this.menuItem
/*  325 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           
/*      */           printOptions(jfKendoku, "Display Options");
/*      */           restoreFrame();
/*      */         });
/*  333 */     this.menu = new JMenu("Export");
/*  334 */     menuBar.add(this.menu);
/*  335 */     this.menuItem = new JMenuItem("Print a Kendoku KDP puzzle book.");
/*  336 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(75, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  337 */     this.menu.add(this.menuItem);
/*  338 */     this.menuItem
/*  339 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           
/*      */           Methods.printKdpDialog(jfKendoku, 110, 6);
/*      */         });
/*  346 */     this.menu = new JMenu("Tasks");
/*  347 */     menuBar.add(this.menu);
/*  348 */     this.menuItem = new JMenuItem("Print this Puzzle");
/*  349 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(80, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  350 */     this.menu.add(this.menuItem);
/*  351 */     this.menuItem
/*  352 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           CrosswordExpress.toPrint(jfKendoku, Op.ke[Op.KE.KePuz.ordinal()]);
/*      */         });
/*  358 */     this.menuItem = new JMenuItem("Solve this Puzzle");
/*  359 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(86, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  360 */     this.menu.add(this.menuItem);
/*  361 */     this.menuItem
/*  362 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1)
/*      */             return; 
/*      */           if (Methods.havePuzzle) {
/*      */             CrosswordExpress.transfer(111, jfKendoku);
/*      */           } else {
/*      */             Methods.noPuzzle(jfKendoku, "Solve");
/*      */           } 
/*      */         });
/*  371 */     this.menuItem = new JMenuItem("Delete this Puzzle");
/*  372 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(68, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  373 */     this.menu.add(this.menuItem);
/*  374 */     this.menuItem
/*  375 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           
/*      */           if (Methods.deleteAPuzzle(jfKendoku, Op.ke[Op.KE.KePuz.ordinal()], "kendoku", pp)) {
/*      */             makeGrid();
/*      */             loadKendoku(Op.ke[Op.KE.KePuz.ordinal()]);
/*      */             restoreFrame();
/*      */           } 
/*      */         });
/*  386 */     this.menu = new JMenu("Help");
/*  387 */     menuBar.add(this.menu);
/*  388 */     this.menuItem = new JMenuItem("Kendoku Help");
/*  389 */     this.menu.add(this.menuItem);
/*  390 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(72, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  391 */     this.menuItem
/*  392 */       .addActionListener(ae -> Methods.cweHelp(jfKendoku, null, "Building Kendoku Puzzles", this.kendokuHelp));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  397 */     pp = new KendokuPP(0, 37);
/*  398 */     jfKendoku.add(pp);
/*      */     
/*  400 */     pp
/*  401 */       .addMouseMotionListener(new MouseAdapter() {
/*      */           public void mouseMoved(MouseEvent e) {
/*  403 */             if (Def.isMac) {
/*  404 */               KendokuBuild.jfKendoku.setResizable((KendokuBuild.jfKendoku.getWidth() - e.getX() < 15 && KendokuBuild.jfKendoku
/*  405 */                   .getHeight() - e.getY() < 95));
/*      */             }
/*      */           }
/*      */         });
/*      */     
/*  410 */     jfKendoku
/*  411 */       .addKeyListener(new KeyAdapter() {
/*      */           public void keyPressed(KeyEvent e) {
/*  413 */             KendokuBuild.this.handleKeyPressed(e);
/*      */           }
/*      */         });
/*      */ 
/*      */     
/*  418 */     loadKendoku(Op.ke[Op.KE.KePuz.ordinal()]);
/*  419 */     restoreFrame();
/*      */ 
/*      */     
/*  422 */     ActionListener timerAL = ae -> {
/*      */         this.myTimer.stop();
/*      */         this.thread = new Thread(paramRunnable);
/*      */         this.thread.start();
/*      */         Def.building = 1;
/*      */       };
/*  428 */     this.myTimer = new Timer(1000, timerAL);
/*      */     
/*  430 */     if (auto) {
/*  431 */       this.sixpack = true;
/*  432 */       this.howMany = hm; this.startPuz = start;
/*  433 */       this.myTimer.start();
/*      */     } 
/*      */   }
/*      */   
/*      */   static void restoreFrame() {
/*  438 */     jfKendoku.setVisible(true);
/*  439 */     Insets insets = jfKendoku.getInsets();
/*  440 */     panelW = jfKendoku.getWidth() - insets.left + insets.right;
/*  441 */     panelH = jfKendoku.getHeight() - insets.top + insets.bottom + 37 + menuBar.getHeight();
/*  442 */     pp.setSize(panelW, panelH);
/*  443 */     jfKendoku.requestFocusInWindow();
/*  444 */     pp.repaint();
/*  445 */     Methods.infoPanel(jl1, jl2, "Build Kendoku", "Puzzle : " + Op.ke[Op.KE.KePuz.ordinal()], panelW);
/*      */   }
/*      */   
/*      */   static void setSizesAndOffsets(int x, int y, int width, int height, int inset) {
/*  449 */     int i = (width - inset) / Grid.xSz;
/*  450 */     int j = (height - inset) / Grid.ySz;
/*  451 */     Grid.xCell = Grid.yCell = (i < j) ? i : j;
/*  452 */     Grid.xOrg = x + ((Def.puzzleMode == 8) ? ((width - Grid.xSz * Grid.xCell) / 2) : 10);
/*  453 */     Grid.yOrg = y + ((Def.puzzleMode == 8) ? ((height - Grid.ySz * Grid.yCell) / 2) : 10);
/*      */   }
/*      */   
/*      */   private void kendokuOptions() {
/*  457 */     String[] diffString = { "  Easy", "  Moderate", "  Hard" };
/*      */ 
/*      */ 
/*      */     
/*  461 */     JDialog jdlgKendoku = new JDialog(jfKendoku, "Kendoku Options", true);
/*  462 */     jdlgKendoku.setSize(270, 285);
/*  463 */     jdlgKendoku.setResizable(false);
/*  464 */     jdlgKendoku.setLayout((LayoutManager)null);
/*  465 */     jdlgKendoku.setLocation(jfKendoku.getX(), jfKendoku.getY());
/*      */     
/*  467 */     jdlgKendoku
/*  468 */       .addWindowListener(new WindowAdapter() {
/*      */           public void windowClosing(WindowEvent we) {
/*  470 */             Methods.closeHelp();
/*      */           }
/*      */         });
/*      */     
/*  474 */     Methods.closeHelp();
/*      */     
/*  476 */     JLabel jlSize = new JLabel("Kendoku Size:");
/*  477 */     jlSize.setForeground(Def.COLOR_LABEL);
/*  478 */     jlSize.setSize(120, 20);
/*  479 */     jlSize.setLocation(30, 8);
/*  480 */     jlSize.setHorizontalAlignment(4);
/*  481 */     jdlgKendoku.add(jlSize);
/*      */     
/*  483 */     JComboBox<Integer> jcbbSize = new JComboBox<>();
/*  484 */     for (int i = 4; i <= 9; i++)
/*  485 */       jcbbSize.addItem(Integer.valueOf(i)); 
/*  486 */     jcbbSize.setSize(80, 23);
/*  487 */     jcbbSize.setLocation(160, 8);
/*  488 */     jdlgKendoku.add(jcbbSize);
/*  489 */     jcbbSize.setBackground(Def.COLOR_BUTTONBG);
/*  490 */     jcbbSize.setSelectedIndex(Op.getInt(Op.KE.KeSize.ordinal(), Op.ke) - 4);
/*      */     
/*  492 */     JLabel jlDiff = new JLabel("Difficulty:");
/*  493 */     jlDiff.setForeground(Def.COLOR_LABEL);
/*  494 */     jlDiff.setSize(80, 20);
/*  495 */     jlDiff.setLocation(30, 38);
/*  496 */     jlDiff.setHorizontalAlignment(4);
/*  497 */     jdlgKendoku.add(jlDiff);
/*      */     
/*  499 */     JComboBox<String> jcbbDifficulty = new JComboBox<>(diffString);
/*  500 */     jcbbDifficulty.setSize(120, 23);
/*  501 */     jcbbDifficulty.setLocation(120, 38);
/*  502 */     jdlgKendoku.add(jcbbDifficulty);
/*  503 */     jcbbDifficulty.setBackground(Def.COLOR_BUTTONBG);
/*  504 */     jcbbDifficulty.setSelectedIndex(Op.getInt(Op.KE.KeDifficulty.ordinal(), Op.ke));
/*      */     
/*  506 */     HowManyPuzzles hmp = new HowManyPuzzles(jdlgKendoku, 10, 67, this.howMany, this.startPuz, true);
/*      */     
/*  508 */     JButton jbOK = Methods.cweButton("OK", 13, 185, 80, 26, null);
/*  509 */     jbOK.addActionListener(e -> {
/*      */           int i = paramJComboBox1.getSelectedIndex() + 4;
/*      */           Grid.xSz = Grid.ySz = i;
/*      */           Op.setInt(Op.KE.KeSize.ordinal(), i, Op.ke);
/*      */           this.howMany = Integer.parseInt(paramHowManyPuzzles.jtfHowMany.getText());
/*      */           this.startPuz = Integer.parseInt(paramHowManyPuzzles.jtfStartPuz.getText());
/*      */           Op.setInt(Op.KE.KeDifficulty.ordinal(), paramJComboBox2.getSelectedIndex(), Op.ke);
/*      */           Op.setBool(Op.SX.VaryDiff.ordinal(), Boolean.valueOf(paramHowManyPuzzles.jcbVaryDiff.isSelected()), Op.sx);
/*      */           Methods.clickedOK = true;
/*      */           paramJDialog.dispose();
/*      */           Methods.closeHelp();
/*      */         });
/*  521 */     jdlgKendoku.add(jbOK);
/*      */     
/*  523 */     JButton jbCancel = Methods.cweButton("Cancel", 13, 219, 80, 26, null);
/*  524 */     jbCancel.addActionListener(e -> {
/*      */           Methods.clickedOK = false;
/*      */           paramJDialog.dispose();
/*      */           Methods.closeHelp();
/*      */         });
/*  529 */     jdlgKendoku.add(jbCancel);
/*      */     
/*  531 */     JButton jbHelp = Methods.cweButton("<html><font size=6 color=BB0000 face=Serif>Help ", 100, 185, 155, 60, new ImageIcon("graphics/help.png"));
/*  532 */     jbHelp.addActionListener(e -> Methods.cweHelp(null, paramJDialog, "Kendoku Options", this.kendokuOptions));
/*      */     
/*  534 */     jdlgKendoku.add(jbHelp);
/*      */     
/*  536 */     jdlgKendoku.getRootPane().setDefaultButton(jbOK);
/*  537 */     Methods.setDialogSize(jdlgKendoku, 260, 255);
/*      */   }
/*      */   
/*      */   static void printOptions(JFrame jf, String type) {
/*  541 */     String[] colorLabel = { "Cell Color", "Grid Color", "Border Color", "Number Color", "Guide Digit Color", "Hint Color", "Error Color" };
/*  542 */     int[] colorInt = { Op.KE.KeCell.ordinal(), Op.KE.KeGrid.ordinal(), Op.KE.KeLine.ordinal(), Op.KE.KeNumber.ordinal(), Op.KE.KeGuide.ordinal(), Op.KE.KeHint.ordinal(), Op.KE.KeError.ordinal() };
/*  543 */     String[] fontLabel = { "Puzzle Font", "Guide Digit Font", "Hint Font" };
/*  544 */     int[] fontInt = { Op.KE.KeFont.ordinal(), Op.KE.KeGuideFont.ordinal(), Op.KE.KeHintFont.ordinal() };
/*  545 */     String[] checkLabel = { "PPrint Puzzle with color.", "SPrint Solution with color." };
/*  546 */     int[] checkInt = { Op.KE.KePuzColor.ordinal(), Op.KE.KeSolColor.ordinal() };
/*  547 */     Methods.stdPrintOptions(jf, "Kendoku " + type, Op.ke, colorLabel, colorInt, fontLabel, fontInt, checkLabel, checkInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void saveKendoku(String kendokuName) {
/*      */     try {
/*  557 */       DataOutputStream dataOut = new DataOutputStream(new FileOutputStream("kendoku/" + kendokuName));
/*  558 */       dataOut.writeInt(Grid.xSz);
/*  559 */       dataOut.writeInt(Grid.ySz);
/*  560 */       dataOut.writeByte(Methods.noReveal);
/*  561 */       dataOut.writeByte(Methods.noErrors);
/*  562 */       for (int i = 0; i < 54; i++)
/*  563 */         dataOut.writeByte(0); 
/*  564 */       for (int j = 0; j < Grid.ySz; j++) {
/*  565 */         for (int k = 0; k < Grid.xSz; k++) {
/*  566 */           dataOut.writeInt(Grid.mode[k][j]);
/*  567 */           dataOut.writeInt(Grid.sol[k][j]);
/*  568 */           dataOut.writeInt(Grid.letter[k][j]);
/*      */         } 
/*  570 */       }  dataOut.writeUTF(Methods.puzzleTitle);
/*  571 */       dataOut.writeUTF(Methods.author);
/*  572 */       dataOut.writeUTF(Methods.copyright);
/*  573 */       dataOut.writeUTF(Methods.puzzleNumber);
/*  574 */       dataOut.writeUTF(Methods.puzzleNotes);
/*  575 */       dataOut.close();
/*      */     }
/*  577 */     catch (IOException exc) {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void loadKendoku(String kendokuName) {
/*      */     
/*  585 */     try { File fl = new File("kendoku/" + kendokuName);
/*  586 */       if (!fl.exists()) {
/*  587 */         fl = new File("kendoku/");
/*  588 */         String[] s = fl.list(); int k;
/*  589 */         for (k = 0; k < s.length && (
/*  590 */           s[k].lastIndexOf(".kendoku") == -1 || s[k].charAt(0) == '.'); k++);
/*      */         
/*  592 */         if (k == s.length) { makeGrid(); return; }
/*  593 */          kendokuName = s[k];
/*  594 */         Op.ke[Op.KE.KePuz.ordinal()] = kendokuName;
/*      */       } 
/*      */       
/*  597 */       DataInputStream dataIn = new DataInputStream(new FileInputStream("kendoku/" + kendokuName));
/*  598 */       Grid.xSz = dataIn.readInt();
/*  599 */       Grid.ySz = dataIn.readInt();
/*  600 */       Methods.noReveal = dataIn.readByte();
/*  601 */       Methods.noErrors = dataIn.readByte(); int i;
/*  602 */       for (i = 0; i < 54; i++)
/*  603 */         dataIn.readByte(); 
/*  604 */       for (int j = 0; j < Grid.ySz; j++) {
/*  605 */         for (i = 0; i < Grid.xSz; i++) {
/*  606 */           Grid.mode[i][j] = dataIn.readInt();
/*  607 */           Grid.sol[i][j] = dataIn.readInt();
/*  608 */           Grid.letter[i][j] = dataIn.readInt();
/*      */         } 
/*  610 */       }  Methods.puzzleTitle = dataIn.readUTF();
/*  611 */       Methods.author = dataIn.readUTF();
/*  612 */       Methods.copyright = dataIn.readUTF();
/*  613 */       Methods.puzzleNumber = dataIn.readUTF();
/*  614 */       Methods.puzzleNotes = dataIn.readUTF();
/*  615 */       dataIn.close(); }
/*      */     
/*  617 */     catch (IOException exc) { return; }
/*  618 */      Methods.havePuzzle = true;
/*      */   }
/*      */ 
/*      */   
/*      */   static void drawKendoku(Graphics2D g2, int[][] puzzleArray) {
/*  623 */     String op = "+x-/123456789";
/*      */ 
/*      */     
/*  626 */     Stroke normalStroke = new BasicStroke(Grid.xCell / 25.0F, 2, 2);
/*  627 */     g2.setStroke(normalStroke);
/*      */     
/*  629 */     RenderingHints rh = g2.getRenderingHints();
/*  630 */     rh.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
/*  631 */     g2.setRenderingHints(rh);
/*      */     
/*      */     int j;
/*  634 */     for (j = 0; j < Grid.ySz; j++) {
/*  635 */       for (int i = 0; i < Grid.xSz; i++) {
/*  636 */         if (Grid.mode[i][j] != 2) {
/*  637 */           g2.setColor(new Color(Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.KE.KeCell.ordinal(), Op.ke) : 16777215));
/*  638 */           g2.fillRect(Grid.xOrg + i * Grid.xCell, Grid.yOrg + j * Grid.yCell, Grid.xCell, Grid.yCell);
/*      */         } 
/*      */       } 
/*  641 */     }  for (int k = 1; k < 2; k++) {
/*  642 */       for (j = 0; j < Grid.ySz; j++) {
/*  643 */         for (int i = 0; i < Grid.xSz; i++) {
/*  644 */           int x = Grid.xOrg + i * Grid.xCell, y = Grid.yOrg + j * Grid.yCell;
/*  645 */           if (k == 1 && Grid.mode[i][j] > 65536) {
/*  646 */             g2.setFont(new Font(Op.ke[Op.KE.KeHintFont.ordinal()], 0, Grid.yCell / 4));
/*  647 */             g2.setColor(new Color(Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.KE.KeHint.ordinal(), Op.ke) : 0));
/*  648 */             FontMetrics fontMetrics = g2.getFontMetrics();
/*  649 */             g2.drawString("" + (Grid.mode[i][j] / 65536) + op.charAt(Grid.mode[i][j] / 256 % 256), x + Grid.xCell / 10, y + fontMetrics.getAscent());
/*      */           }  int c;
/*  651 */           for (c = 3; c < 768; c *= 4) {
/*  652 */             int v = ((Grid.mode[i][j] & c) == 0) ? 0 : 1;
/*  653 */             g2.setColor(new Color(Def.dispWithColor.booleanValue() ? ((v == 0) ? Op.getColorInt(Op.KE.KeGrid.ordinal(), Op.ke) : Op.getColorInt(Op.KE.KeLine.ordinal(), Op.ke)) : 0));
/*  654 */             if (v == k)
/*  655 */               switch (c) { case 3:
/*  656 */                   g2.drawLine(x, y, x + Grid.xCell, y); break;
/*  657 */                 case 12: g2.drawLine(x + Grid.xCell, y, x + Grid.xCell, y + Grid.yCell); break;
/*  658 */                 case 48: g2.drawLine(x, y + Grid.yCell, x + Grid.xCell, y + Grid.yCell); break;
/*  659 */                 case 192: g2.drawLine(x, y + Grid.yCell, x, y); break; }  
/*      */           } 
/*      */         } 
/*      */       } 
/*  663 */     }  g2.setColor(new Color(Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.KE.KeLine.ordinal(), Op.ke) : 0));
/*  664 */     g2.drawRect(Grid.xOrg, Grid.yOrg, Grid.xSz * Grid.xCell, Grid.ySz * Grid.yCell);
/*      */     
/*  666 */     if (Def.dispCursor.booleanValue()) {
/*  667 */       int c; for (c = 3; c < 768; c *= 4) {
/*  668 */         int x = Grid.xOrg + Grid.xCur * Grid.xCell, y = Grid.yOrg + Grid.yCur * Grid.yCell;
/*  669 */         int v = ((Grid.mode[Grid.xCur][Grid.yCur] & c) == 0) ? 0 : 1;
/*  670 */         g2.setColor(Def.COLOR_RED);
/*  671 */         switch (c) { case 3:
/*  672 */             g2.drawLine(x, y, x + Grid.xCell, y); break;
/*  673 */           case 12: g2.drawLine(x + Grid.xCell, y, x + Grid.xCell, y + Grid.yCell); break;
/*  674 */           case 48: g2.drawLine(x, y + Grid.yCell, x + Grid.xCell, y + Grid.yCell); break;
/*  675 */           case 192: g2.drawLine(x, y + Grid.yCell, x, y); break; }
/*      */       
/*      */       } 
/*      */     } 
/*  679 */     g2.setStroke(normalStroke);
/*      */ 
/*      */     
/*  682 */     g2.setFont(new Font(Op.ke[Op.KE.KeFont.ordinal()], 0, 8 * Grid.yCell / 10));
/*  683 */     FontMetrics fm = g2.getFontMetrics();
/*  684 */     g2.setColor(new Color(Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.KE.KeNumber.ordinal(), Op.ke) : 0));
/*  685 */     for (j = 0; j < Grid.ySz; j++) {
/*  686 */       for (int i = 0; i < Grid.xSz; i++) {
/*  687 */         char ch = (char)puzzleArray[i][j];
/*  688 */         if (Character.isDigit(ch)) {
/*  689 */           int w = fm.stringWidth("" + ch);
/*  690 */           g2.drawString("" + ch, Grid.xOrg + i * Grid.xCell + (Grid.xCell - w) / 2, Grid.yOrg + j * Grid.yCell + (Grid.yCell + fm.getAscent() - fm.getDescent()) / 2);
/*      */         } 
/*      */       } 
/*      */     } 
/*  694 */     g2.setStroke(new BasicStroke(1.0F));
/*      */   }
/*      */   
/*      */   static void printPuz(Graphics2D g2, int left, int top, int width, int height) {
/*  698 */     loadKendoku(Op.ke[Op.KE.KePuz.ordinal()]);
/*  699 */     setSizesAndOffsets(left, top, width, height, 0);
/*  700 */     Methods.clearGrid(Grid.sol);
/*  701 */     Def.dispWithColor = Op.getBool(Op.KE.KePuzColor.ordinal(), Op.ke);
/*  702 */     KendokuSolve.drawKendoku(g2, Grid.grid);
/*  703 */     Def.dispWithColor = Boolean.valueOf(true);
/*      */   }
/*      */   
/*      */   static void printSol(Graphics2D g2, int left, int top, int width, int height, String solutionPuzzle) {
/*  707 */     loadKendoku(solutionPuzzle);
/*  708 */     setSizesAndOffsets(left, top, width, height, 0);
/*  709 */     Def.dispWithColor = Op.getBool(Op.KE.KeSolColor.ordinal(), Op.ke);
/*  710 */     drawKendoku(g2, Grid.letter);
/*  711 */     Def.dispWithColor = Boolean.valueOf(true);
/*  712 */     loadKendoku(Op.ke[Op.KE.KePuz.ordinal()]);
/*      */   }
/*      */   
/*      */   static void printSolTitle(Graphics2D g2, int left, int top, int width, int height, String solutionPuzzle) {
/*  716 */     loadKendoku(solutionPuzzle);
/*  717 */     Print.outputTextItem(g2, left, top, width, height, "SansSerif", 0, Methods.puzzleTitle);
/*  718 */     loadKendoku(Op.ke[Op.KE.KePuz.ordinal()]);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static void printSixpackPuz(Graphics2D g2, int left, int top, int dim, int gap, String puzName) {
/*  724 */     FontMetrics fm = g2.getFontMetrics();
/*      */     
/*  726 */     String st = Op.sx[Op.SX.SxKe.ordinal()];
/*  727 */     if (st.length() < 3) st = "KENDOKU"; 
/*  728 */     int w = fm.stringWidth(st);
/*  729 */     g2.drawString(st, left + (dim - w) / 2, top - gap / 3);
/*  730 */     KendokuSolve.loadKendoku(puzName + ".kendoku");
/*  731 */     setSizesAndOffsets(left, top, dim, dim, 0);
/*  732 */     Methods.clearGrid(Grid.sol);
/*  733 */     KendokuSolve.drawKendoku(g2, Grid.grid);
/*  734 */     if (Op.sx[Op.SX.SxRuleLang.ordinal()].equals("English")) {
/*  735 */       st = rules;
/*      */     } else {
/*  737 */       st = Op.ke[Op.KE.KeRule1.ordinal() + Op.getInt(Op.SX.SxRuleLangIndex.ordinal(), Op.sx) - 1];
/*  738 */     }  String n1 = "" + Grid.xSz;
/*  739 */     st = st.replace("<n1>", n1);
/*  740 */     if (Op.getBool(Op.SX.SxInstructions.ordinal(), Op.sx).booleanValue()) {
/*  741 */       Methods.renderText(g2, left, top + dim + dim / 50, dim, dim / 4, "SansSerif", 1, st, 3, 4, true, 0, 0);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static void printSixpackSol(Graphics2D g2, int left, int top, int dim, int gap, String solName) {
/*  747 */     FontMetrics fm = g2.getFontMetrics();
/*      */     
/*  749 */     String st = Op.sx[Op.SX.SxKe.ordinal()];
/*  750 */     if (st.length() < 3) st = "KENDOKU"; 
/*  751 */     int w = fm.stringWidth(st);
/*  752 */     g2.drawString(st, left + (dim - w) / 2, top - gap / 3);
/*  753 */     loadKendoku(solName + ".kendoku");
/*  754 */     setSizesAndOffsets(left, top, dim, dim, 0);
/*  755 */     drawKendoku(g2, Grid.letter);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static void printKDPPuz(Graphics2D g2, int left, int top, int dim, int gap, String puzName) {
/*  761 */     FontMetrics fm = g2.getFontMetrics();
/*      */     
/*  763 */     String st = puzName;
/*  764 */     int w = fm.stringWidth(st);
/*  765 */     g2.drawString(st, left + (dim - w) / 2, top - gap / 3);
/*  766 */     KendokuSolve.loadKendoku(puzName + ".kendoku");
/*  767 */     setSizesAndOffsets(left, top, dim, dim, 0);
/*  768 */     Methods.clearGrid(Grid.sol);
/*  769 */     KendokuSolve.drawKendoku(g2, Grid.grid);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static void printKDPSol(Graphics2D g2, int left, int top, int dim, int gap, String solName) {
/*  775 */     FontMetrics fm = g2.getFontMetrics();
/*      */     
/*  777 */     String st = solName;
/*  778 */     int w = fm.stringWidth(st);
/*  779 */     g2.drawString(st, left + (dim - w) / 2, top - gap / 3);
/*  780 */     loadKendoku(solName + ".kendoku");
/*  781 */     setSizesAndOffsets(left, top, dim, dim, 0);
/*  782 */     drawKendoku(g2, Grid.letter);
/*      */   }
/*      */   
/*      */   private static void makeGrid() {
/*  786 */     Methods.havePuzzle = false;
/*  787 */     Grid.clearGrid();
/*  788 */     for (int y = 0; y < Grid.ySz; y++) {
/*  789 */       for (int x = 0; x < Grid.xSz; x++)
/*  790 */         Grid.mode[x][y] = 85; 
/*      */     } 
/*      */   }
/*      */   Boolean UpdateDomainCell(int x, int y, int side) {
/*  794 */     int[] mask = { 3, 12, 48, 192, 252, 243, 207, 63 };
/*  795 */     int[] inc = { 1, 4, 16, 64 };
/*      */     
/*  797 */     if (x < 0 || y < 0 || x >= Grid.xSz || y >= Grid.ySz) return Boolean.valueOf(false); 
/*  798 */     if ((Grid.mode[x][y] & mask[side]) != 0) {
/*  799 */       Grid.mode[x][y] = Grid.mode[x][y] & mask[side + 4];
/*      */     } else {
/*  801 */       Grid.mode[x][y] = Grid.mode[x][y] + inc[side];
/*  802 */     }  return Boolean.valueOf(true);
/*      */   }
/*      */   
/*      */   void UpdateDomain(int x, int y, int side) {
/*  806 */     UpdateDomainCell(x, y, side);
/*      */     
/*  808 */     switch (side) { case 0:
/*  809 */         UpdateDomainCell(x, --y, 2); break;
/*  810 */       case 1: UpdateDomainCell(++x, y, 3); break;
/*  811 */       case 2: UpdateDomainCell(x, ++y, 0); break;
/*  812 */       case 3: UpdateDomainCell(--x, y, 1);
/*      */         break; }
/*      */   
/*      */   }
/*      */   
/*      */   void fillKendoku() {
/*  818 */     boolean duplicate = false;
/*  819 */     Random r = new Random();
/*      */     int y;
/*  821 */     for (y = 0; y < Grid.ySz; y++) {
/*  822 */       for (int x = 0; x < Grid.xSz; x++)
/*  823 */       { Grid.letter[x][y] = r.nextInt(Grid.xSz - 1); Grid.sol[x][y] = r.nextInt(Grid.xSz - 1); } 
/*  824 */     }  for (int j = 0; j < Grid.xSz * Grid.xSz; ) {
/*  825 */       int x = j % Grid.xSz; y = j / Grid.xSz;
/*  826 */       if (duplicate) {
/*  827 */         Grid.sol[x][y] = (Grid.sol[x][y] + 1) % Grid.xSz;
/*  828 */         if (Grid.sol[x][y] == Grid.letter[x][y]) {
/*  829 */           j--;
/*      */           continue;
/*      */         } 
/*      */       } 
/*      */       while (true) {
/*  834 */         duplicate = false; int i;
/*  835 */         for (i = 0; i < x; i++) {
/*  836 */           if (Grid.sol[i][y] == Grid.sol[x][y])
/*  837 */             duplicate = true; 
/*  838 */         }  for (i = 0; i < y; i++) {
/*  839 */           if (Grid.sol[x][i] == Grid.sol[x][y])
/*  840 */             duplicate = true; 
/*  841 */         }  if (duplicate) {
/*  842 */           Grid.sol[x][y] = (Grid.sol[x][y] + 1) % Grid.xSz;
/*  843 */           if (Grid.sol[x][y] == Grid.letter[x][y])
/*  844 */             j--; 
/*      */           continue;
/*      */         } 
/*      */         break;
/*      */       } 
/*  849 */       j++;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  854 */     for (y = 0; y < Grid.ySz; y++) {
/*  855 */       for (int x = 0; x < Grid.xSz; x++) {
/*  856 */         Grid.letter[x][y] = Grid.sol[x][y] + 49;
/*  857 */         Grid.sol[x][y] = 0;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   boolean buildIt() {
/*  863 */     int[] xinc = { 0, 1, 0, -1 }, yinc = { -1, 0, 1, 0 };
/*  864 */     int[] c = { 1, 4, 16, 64 }, side = { 16, 64, 1, 4 };
/*  865 */     Random r = new Random();
/*  866 */     int[] rand = new int[81];
/*  867 */     Node[] theNode = new Node[40];
/*  868 */     int nodeCount = 0;
/*      */     int i, k;
/*  870 */     for (i = 0, k = Grid.xSz * Grid.ySz; i < k; rand[i] = i++);
/*  871 */     for (i = 0; i < k; i++) {
/*  872 */       int j = r.nextInt(k - 1);
/*  873 */       int l = rand[i]; rand[i] = rand[j]; rand[j] = l;
/*      */     } 
/*      */     
/*  876 */     if (Grid.xSz > 5) {
/*  877 */       int a; for (a = 0; a < ((Grid.xSz < 8) ? 2 : 4); a++) {
/*      */         
/*  879 */         theNode[a] = new Node();
/*  880 */         int b = 65 + a;
/*  881 */         switch (a) { case 0:
/*  882 */             Grid.letter[2][2] = b; (theNode[0]).cellLoc[0] = new Point(2, 2); break;
/*  883 */           case 1: Grid.letter[Grid.xSz - 3][Grid.xSz - 3] = b; (theNode[1]).cellLoc[0] = new Point(Grid.xSz - 3, Grid.xSz - 3); break;
/*  884 */           case 2: Grid.letter[2][Grid.xSz - 3] = b; (theNode[2]).cellLoc[0] = new Point(2, Grid.xSz - 3); break;
/*  885 */           case 3: Grid.letter[Grid.xSz - 3][2] = b; (theNode[3]).cellLoc[0] = new Point(Grid.xSz - 3, 2); break; }
/*      */       
/*      */       } 
/*  888 */       nodeCount = a;
/*      */       
/*  890 */       for (a = 0; a < ((Grid.xSz < 8) ? 2 : 4); a++) {
/*      */         
/*  892 */         int b = 65 + a;
/*  893 */         for (int m = 0; m < 3; m++) {
/*  894 */           for (i = 0; i < k; i++) {
/*  895 */             int x = rand[i] % Grid.xSz, j = rand[i] / Grid.xSz;
/*  896 */             if (Grid.letter[x][j] == 49) {
/*  897 */               int n; int l; for (l = r.nextInt(3), n = 0; n < 4; n++, l = (l + 1) % 4) {
/*  898 */                 int x2 = x + xinc[l], y2 = j + yinc[l];
/*  899 */                 if (x2 >= 0 && y2 >= 0 && x2 != Grid.xSz && y2 != Grid.ySz && 
/*  900 */                   Grid.letter[x2][y2] == b) {
/*  901 */                   (theNode[a]).cellLoc[m + 1] = new Point(x, j);
/*  902 */                   (theNode[a]).length = 4;
/*  903 */                   Grid.mode[x][j] = Grid.mode[x][j] ^ c[l]; Grid.letter[x][j] = b;
/*  904 */                   Grid.mode[x2][y2] = Grid.mode[x2][y2] ^ side[l];
/*  905 */                   i = k;
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*  913 */     for (i = 0; i < k; i++) {
/*  914 */       int x = rand[i] % Grid.xSz, j = rand[i] / Grid.xSz;
/*  915 */       if (Grid.letter[x][j] == 49) {
/*  916 */         int m; int l; for (l = r.nextInt(3), m = 0; m < 4; m++, l = (l + 1) % 4) {
/*  917 */           int x2 = x + xinc[l], y2 = j + yinc[l];
/*  918 */           if (x2 >= 0 && y2 >= 0 && x2 != Grid.xSz && y2 != Grid.ySz) {
/*  919 */             theNode[nodeCount] = new Node();
/*  920 */             if (Grid.letter[x2][y2] == 49) {
/*  921 */               Grid.mode[x][j] = Grid.mode[x][j] ^ c[l]; Grid.letter[x][j] = 50;
/*  922 */               Grid.mode[x2][y2] = Grid.mode[x2][y2] ^ side[l]; Grid.letter[x2][y2] = 50;
/*  923 */               (theNode[nodeCount]).cellLoc[0] = new Point(x, j);
/*  924 */               (theNode[nodeCount]).cellLoc[1] = new Point(x2, y2);
/*  925 */               (theNode[nodeCount]).length = 2;
/*  926 */               Grid.sol[x2][y2] = nodeCount++; Grid.sol[x][j] = nodeCount++; break;
/*      */             } 
/*      */           } 
/*      */         } 
/*  930 */         if (m == 4)
/*  931 */           for (l = r.nextInt(3), m = 0; m < 4; m++, l = (l + 1) % 4) {
/*  932 */             int x2 = x + xinc[l], y2 = j + yinc[l];
/*  933 */             if (x2 >= 0 && y2 >= 0 && x2 != Grid.xSz && y2 != Grid.ySz && 
/*  934 */               Grid.letter[x2][y2] == 50) {
/*  935 */               Grid.sol[x][j] = Grid.sol[x2][y2];
/*  936 */               (theNode[Grid.sol[x][j]]).cellLoc[2] = new Point(x, j);
/*  937 */               (theNode[Grid.sol[x][j]]).length = 3;
/*  938 */               Grid.mode[x][j] = Grid.mode[x][j] ^ c[l]; Grid.letter[x][j] = 51;
/*  939 */               Grid.mode[x2][y2] = Grid.mode[x2][y2] ^ side[l]; Grid.letter[x2][y2] = 51; int n;
/*  940 */               for (m = 0, n = 3; m < 4; m++, n *= 4) {
/*  941 */                 int x3 = x2 + xinc[m], y3 = y2 + yinc[m];
/*  942 */                 if (x3 >= 0 && y3 >= 0 && x3 != Grid.xSz && y3 != Grid.ySz && 
/*  943 */                   Grid.letter[x3][y3] == 50 && (Grid.mode[x2][y2] & n) == 0) {
/*  944 */                   Grid.letter[x3][y3] = 51; break;
/*      */                 } 
/*      */               } 
/*      */               break;
/*      */             } 
/*      */           }  
/*      */       } 
/*      */     } 
/*  952 */     for (int y = 0; y < Grid.ySz; y++) {
/*  953 */       for (int x = 0; x < Grid.ySz; x++) {
/*  954 */         if (Grid.letter[x][y] == 49)
/*  955 */           return false; 
/*      */       } 
/*      */     } 
/*  958 */     fillKendoku();
/*      */ 
/*      */     
/*  961 */     for (i = 0; i < nodeCount; i++) {
/*  962 */       (theNode[i]).x = 10;
/*  963 */       (theNode[i]).y = 10;
/*  964 */       for (int j = 0; j < (theNode[i]).length; j++) {
/*  965 */         if (((theNode[i]).cellLoc[j]).y < (theNode[i]).y || (((theNode[i]).cellLoc[j]).y == (theNode[i]).y && ((theNode[i]).cellLoc[j]).x < (theNode[i]).x)) {
/*  966 */           (theNode[i]).x = ((theNode[i]).cellLoc[j]).x;
/*  967 */           (theNode[i]).y = ((theNode[i]).cellLoc[j]).y;
/*      */         } 
/*  969 */       }  if ((theNode[i]).length > 2) {
/*  970 */         if (r.nextInt(2) == 0) {
/*      */           int hint;
/*  972 */           for (hint = k = 0; k < (theNode[i]).length; k++)
/*  973 */             hint += Grid.letter[((theNode[i]).cellLoc[k]).x][((theNode[i]).cellLoc[k]).y] - 48; 
/*  974 */           Grid.mode[(theNode[i]).x][(theNode[i]).y] = Grid.mode[(theNode[i]).x][(theNode[i]).y] + hint * 65536;
/*  975 */           (theNode[i]).kendokuValue = hint;
/*  976 */           (theNode[i]).kendokuOp = 0;
/*      */         } else {
/*      */           int hint;
/*      */           
/*  980 */           for (hint = 1, k = 0; k < (theNode[i]).length; k++)
/*  981 */             hint *= Grid.letter[((theNode[i]).cellLoc[k]).x][((theNode[i]).cellLoc[k]).y] - 48; 
/*  982 */           Grid.mode[(theNode[i]).x][(theNode[i]).y] = Grid.mode[(theNode[i]).x][(theNode[i]).y] + hint * 65536 + 256;
/*  983 */           (theNode[i]).kendokuValue = hint;
/*  984 */           (theNode[i]).kendokuOp = 1;
/*      */         } 
/*      */       } else {
/*      */         int a; int b; int hint;
/*  988 */         switch (r.nextInt(7)) {
/*      */           case 3:
/*      */           case 6:
/*  991 */             a = Grid.letter[((theNode[i]).cellLoc[0]).x][((theNode[i]).cellLoc[0]).y] - 48;
/*  992 */             b = Grid.letter[((theNode[i]).cellLoc[1]).x][((theNode[i]).cellLoc[1]).y] - 48;
/*  993 */             hint = 0;
/*  994 */             if (a / b * b == a) { hint = a / b; }
/*  995 */             else if (b / a * a == b) { hint = b / a; }
/*  996 */              if (hint > 0) {
/*  997 */               Grid.mode[(theNode[i]).x][(theNode[i]).y] = Grid.mode[(theNode[i]).x][(theNode[i]).y] + hint * 65536 + 768;
/*  998 */               (theNode[i]).kendokuValue = hint;
/*  999 */               (theNode[i]).kendokuOp = 3;
/*      */               break;
/*      */             } 
/*      */           case 0:
/* 1003 */             hint = Grid.letter[((theNode[i]).cellLoc[0]).x][((theNode[i]).cellLoc[0]).y] - Grid.letter[((theNode[i]).cellLoc[1]).x][((theNode[i]).cellLoc[1]).y];
/* 1004 */             if (hint < 0) hint = -hint; 
/* 1005 */             Grid.mode[(theNode[i]).x][(theNode[i]).y] = Grid.mode[(theNode[i]).x][(theNode[i]).y] + hint * 65536 + 512;
/* 1006 */             (theNode[i]).kendokuValue = hint;
/* 1007 */             (theNode[i]).kendokuOp = 2;
/*      */             break;
/*      */           case 1:
/*      */           case 4:
/* 1011 */             for (hint = k = 0; k < (theNode[i]).length; k++)
/* 1012 */               hint += Grid.letter[((theNode[i]).cellLoc[k]).x][((theNode[i]).cellLoc[k]).y] - 48; 
/* 1013 */             Grid.mode[(theNode[i]).x][(theNode[i]).y] = Grid.mode[(theNode[i]).x][(theNode[i]).y] + hint * 65536;
/* 1014 */             (theNode[i]).kendokuValue = hint;
/* 1015 */             (theNode[i]).kendokuOp = 0;
/*      */             break;
/*      */           case 2:
/*      */           case 5:
/* 1019 */             for (hint = 1, k = 0; k < (theNode[i]).length; k++)
/* 1020 */               hint *= Grid.letter[((theNode[i]).cellLoc[k]).x][((theNode[i]).cellLoc[k]).y] - 48; 
/* 1021 */             Grid.mode[(theNode[i]).x][(theNode[i]).y] = Grid.mode[(theNode[i]).x][(theNode[i]).y] + hint * 65536 + 256;
/* 1022 */             (theNode[i]).kendokuValue = hint;
/* 1023 */             (theNode[i]).kendokuOp = 1;
/*      */             break;
/*      */         } 
/*      */ 
/*      */       
/*      */       } 
/*      */     } 
/* 1030 */     if (!uniqueSolution(theNode, nodeCount)) return false;
/*      */     
/* 1032 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   void updateKendokuStatus(int x, int y, int i) {
/* 1038 */     for (int j = 0; j < Grid.xSz; j++) {
/* 1039 */       if (j != i) Grid.xstatus[x][y][j] = 0; 
/* 1040 */       if (j != x) Grid.xstatus[j][y][i] = 0; 
/* 1041 */       if (j != y) Grid.xstatus[x][j][i] = 0;
/*      */     
/*      */     } 
/*      */   }
/*      */   
/*      */   void recalculateKendokuStatus() {
/*      */     int y;
/* 1048 */     for (y = 0; y < Grid.ySz; y++) {
/* 1049 */       for (int x = 0; x < Grid.xSz; x++) {
/* 1050 */         for (int k = 0; k < Grid.xSz; k++)
/* 1051 */           Grid.xstatus[x][y][k] = 1; 
/*      */       } 
/* 1053 */     }  for (y = 0; y < Grid.ySz; y++) {
/* 1054 */       for (int x = 0; x < Grid.xSz; x++) {
/* 1055 */         if (Grid.sol[x][y] != 0)
/* 1056 */           updateKendokuStatus(x, y, Grid.sol[x][y] - 49); 
/*      */       } 
/*      */     } 
/*      */   } boolean domainScan(Node[] theNode, int nodeCount) {
/* 1060 */     int temp = 0;
/* 1061 */     int[] x = new int[4], y = new int[4], a = new int[4];
/* 1062 */     byte[][][] memStatus = new byte[9][9][9];
/*      */     
/*      */     int i;
/*      */     
/* 1066 */     for (i = 0; i < 9; i++) {
/* 1067 */       for (int j = 0; j < 9; j++) {
/* 1068 */         for (int k = 0; k < 9; k++)
/* 1069 */           memStatus[i][j][k] = 0; 
/*      */       } 
/* 1071 */     }  for (i = 0; i < nodeCount; ) {
/* 1072 */       int len = (theNode[i]).length;
/* 1073 */       int val = (theNode[i]).kendokuValue; int j;
/* 1074 */       for (j = 0; j < len; j++) {
/* 1075 */         x[j] = ((theNode[i]).cellLoc[j]).x;
/* 1076 */         y[j] = ((theNode[i]).cellLoc[j]).y;
/*      */       } 
/*      */ 
/*      */       
/* 1080 */       for (j = 0; j < len; j++) {
/* 1081 */         a[j] = 0;
/*      */       }
/* 1083 */       j = 0; while (true) {
/* 1084 */         a[j] = a[j] + 1;
/* 1085 */         if (a[j] > Grid.xSz) {
/* 1086 */           j--;
/* 1087 */           if (j < 0)
/*      */             i++; 
/*      */           continue;
/*      */         } 
/* 1091 */         if (Grid.xstatus[x[j]][y[j]][a[j] - 1] != 0) {
/* 1092 */           if (j < len - 1) {
/* 1093 */             j++;
/* 1094 */             a[j] = 0; continue;
/*      */           }  int k;
/*      */           boolean fail;
/* 1097 */           for (fail = false, k = 0; k < len - 1; k++) {
/* 1098 */             for (int m = k + 1; m < len; m++)
/* 1099 */             { if (a[k] == a[m] && (x[k] == x[m] || y[k] == y[m]))
/* 1100 */                 fail = true;  } 
/* 1101 */           }  if (fail)
/*      */             continue; 
/* 1103 */           switch ((theNode[i]).kendokuOp) {
/*      */             case 0:
/* 1105 */               for (temp = 0, k = 0; k < len; k++)
/* 1106 */                 temp += a[k]; 
/*      */               break;
/*      */             case 1:
/* 1109 */               for (temp = 1, k = 0; k < len; k++)
/* 1110 */                 temp *= a[k]; 
/*      */               break;
/*      */             case 2:
/* 1113 */               if ((temp = a[0] - a[1]) < 0)
/* 1114 */                 temp = a[1] - a[0]; 
/*      */               break;
/*      */             case 3:
/* 1117 */               temp = (a[0] > a[1]) ? (a[0] / a[1]) : (a[1] / a[0]);
/*      */               break;
/*      */           } 
/* 1120 */           if (temp == val) {
/* 1121 */             for (k = 0; k < len; k++) {
/* 1122 */               memStatus[x[k]][y[k]][a[k] - 1] = 1;
/*      */             }
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/* 1128 */     for (i = 0; i < 9; i++) {
/* 1129 */       for (int j = 0; j < 9; j++) {
/* 1130 */         for (int k = 0; k < 9; k++)
/* 1131 */           Grid.xstatus[i][j][k] = memStatus[i][j][k]; 
/*      */       } 
/* 1133 */     }  return true;
/*      */   }
/*      */   
/*      */   boolean uniqueTest() {
/* 1137 */     Node[] theNode = new Node[40];
/*      */     
/* 1139 */     int[] xinc = { 0, 1, 0, -1 }, yinc = { -1, 0, 1, 0 };
/*      */     int y, nodeCount;
/* 1141 */     for (nodeCount = y = 0; y < Grid.ySz; y++) {
/* 1142 */       for (int x = 0; x < Grid.xSz; x++) {
/* 1143 */         Grid.sol[x][y] = -1; int val;
/* 1144 */         if ((val = Grid.mode[x][y]) > 256) {
/* 1145 */           theNode[nodeCount] = new Node();
/* 1146 */           (theNode[nodeCount]).kendokuValue = val / 65536;
/* 1147 */           (theNode[nodeCount]).kendokuOp = val / 256 % 256;
/* 1148 */           (theNode[nodeCount]).length = 1;
/* 1149 */           (theNode[nodeCount]).x = x;
/* 1150 */           (theNode[nodeCount]).y = y;
/* 1151 */           (theNode[nodeCount]).cellLoc[0] = new Point(x, y);
/* 1152 */           Grid.sol[x][y] = nodeCount;
/* 1153 */           nodeCount++;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1158 */     for (int i = 0; i < 3; i++) {
/* 1159 */       for (y = 0; y < Grid.ySz; y++) {
/* 1160 */         for (int x = 0; x < Grid.xSz; x++) {
/* 1161 */           if (Grid.sol[x][y] == -1) {
/* 1162 */             int m; for (int j = 0; j < 4; j++, m *= 4)
/* 1163 */             { if ((Grid.mode[x][y] & m) == 0)
/* 1164 */               { int z = Grid.sol[x + xinc[j]][y + yinc[j]];
/* 1165 */                 if (z != -1)
/* 1166 */                 { (theNode[z]).cellLoc[(theNode[z]).length++] = new Point(x, y);
/* 1167 */                   Grid.sol[x][y] = z; }  }  } 
/*      */           } 
/*      */         } 
/*      */       } 
/* 1171 */     }  for (y = 0; y < Grid.ySz; y++) {
/* 1172 */       for (int x = 0; x < Grid.xSz; x++)
/* 1173 */         Grid.sol[x][y] = 0; 
/*      */     } 
/* 1175 */     if (uniqueSolution(theNode, nodeCount)) {
/* 1176 */       return true;
/*      */     }
/* 1178 */     return false;
/*      */   }
/*      */   
/*      */   boolean uniqueSolution(Node[] theNode, int nodeCount) {
/* 1182 */     boolean diff1 = false, diff2 = false;
/*      */     
/* 1184 */     recalculateKendokuStatus();
/*      */     while (true) {
/* 1186 */       domainScan(theNode, nodeCount);
/* 1187 */       if (singleCandidate())
/* 1188 */         continue;  if (singlePosition()) { diff1 = true; continue; }
/* 1189 */        if (nakedSets()) { diff2 = true; continue; }
/*      */        break;
/*      */     } 
/* 1192 */     if (Op.getInt(Op.KE.KeDifficulty.ordinal(), Op.ke) == 0 && (diff1 || diff2)) return false; 
/* 1193 */     if (Op.getInt(Op.KE.KeDifficulty.ordinal(), Op.ke) == 1 && (!diff1 || diff2)) return false;
/*      */ 
/*      */     
/* 1196 */     for (int j = 0; j < Grid.xSz; j++) {
/* 1197 */       for (int i = 0; i < Grid.ySz; i++) {
/* 1198 */         if (Grid.sol[i][j] == 0)
/* 1199 */           return false; 
/*      */       } 
/* 1201 */     }  return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean singleCandidate() {
/* 1209 */     boolean found = false;
/*      */     
/* 1211 */     for (int y = 0; y < Grid.ySz; y++) {
/* 1212 */       for (int x = 0; x < Grid.xSz; x++) {
/* 1213 */         if (Grid.sol[x][y] == 0) {
/* 1214 */           int c; int i; for (c = i = 0; i < Grid.xSz; i++) {
/* 1215 */             if (Grid.xstatus[x][y][i] != 0)
/* 1216 */               c++; 
/* 1217 */           }  if (c == 1) {
/* 1218 */             found = true;
/* 1219 */             for (i = 0; i < Grid.xSz; i++) {
/* 1220 */               if (Grid.xstatus[x][y][i] != 0)
/* 1221 */               { Grid.sol[x][y] = i + 49;
/* 1222 */                 updateKendokuStatus(x, y, i); break; } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/* 1227 */     }  return found;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean singlePosition() {
/* 1234 */     int[] count = new int[Grid.xSz];
/* 1235 */     boolean found = false;
/*      */     
/*      */     int y;
/* 1238 */     for (y = 0; y < Grid.ySz; y++) {
/* 1239 */       for (int j = 0; j < Grid.xSz; count[j++] = 0); int k;
/* 1240 */       for (k = 0; k < Grid.xSz; k++) {
/* 1241 */         if (Grid.sol[k][y] == 0)
/* 1242 */           for (int m = 0; m < Grid.xSz; m++)
/* 1243 */           { if (Grid.xstatus[k][y][m] != 0)
/* 1244 */               count[m] = count[m] + 1;  }  
/* 1245 */       }  for (int i = 0; i < Grid.xSz; i++) {
/* 1246 */         if (count[i] == 1) {
/* 1247 */           found = true;
/* 1248 */           for (k = 0; k < Grid.xSz; k++) {
/* 1249 */             if (Grid.xstatus[k][y][i] != 0) {
/* 1250 */               Grid.sol[k][y] = i + 49;
/* 1251 */               updateKendokuStatus(k, y, i);
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/* 1258 */     for (int x = 0; x < Grid.xSz; x++) {
/* 1259 */       for (int j = 0; j < Grid.ySz; count[j++] = 0);
/* 1260 */       for (y = 0; y < Grid.ySz; y++) {
/* 1261 */         if (Grid.sol[x][y] == 0)
/* 1262 */           for (int k = 0; k < Grid.xSz; k++)
/* 1263 */           { if (Grid.xstatus[x][y][k] != 0)
/* 1264 */               count[k] = count[k] + 1;  }  
/* 1265 */       }  for (int i = 0; i < Grid.xSz; i++) {
/* 1266 */         if (count[i] == 1)
/* 1267 */           for (y = 0; y < Grid.ySz; y++) {
/* 1268 */             found = true;
/* 1269 */             if (Grid.xstatus[x][y][i] != 0) {
/* 1270 */               Grid.sol[x][y] = i + 49;
/* 1271 */               updateKendokuStatus(x, y, i); break;
/*      */             } 
/*      */           }  
/*      */       } 
/*      */     } 
/* 1276 */     return found;
/*      */   }
/*      */   
/*      */   boolean nakedSets() {
/* 1280 */     int[] guideMask = new int[36], guideMaskHits = new int[36];
/* 1281 */     boolean found = false;
/*      */     int i, m;
/* 1283 */     for (m = 0, i = 1; i < 256; i *= 2) {
/* 1284 */       for (int j = i * 2; j < 512; j *= 2)
/* 1285 */         guideMask[m++] = i | j; 
/*      */     } 
/*      */     int y;
/* 1288 */     for (y = 0; y < Grid.ySz; y++) {
/* 1289 */       int j; for (j = 0; j < 36; guideMaskHits[j++] = 0); int k;
/* 1290 */       for (k = 0; k < Grid.xSz; k++) {
/* 1291 */         int theMask; for (theMask = i = 0, j = 1; i < Grid.xSz; i++, j *= 2) {
/* 1292 */           if (Grid.xstatus[k][y][i] > 0)
/* 1293 */             theMask |= j; 
/* 1294 */         }  for (m = 0; m < 36; m++) {
/* 1295 */           if (theMask == guideMask[m])
/* 1296 */             guideMaskHits[m] = guideMaskHits[m] + 1; 
/*      */         } 
/* 1298 */       }  for (m = 0; m < 36; m++) {
/* 1299 */         if (guideMaskHits[m] == 2) {
/* 1300 */           for (k = 0; k < Grid.xSz; k++) {
/* 1301 */             int theMask; for (theMask = i = 0, j = 1; i < Grid.xSz; i++, j *= 2) {
/* 1302 */               if (Grid.xstatus[k][y][i] > 0)
/* 1303 */                 theMask |= j; 
/* 1304 */             }  if ((theMask & guideMask[m]) != 0 && (
/* 1305 */               theMask & (guideMask[m] ^ 0x1FF)) != 0) {
/* 1306 */               int n; for (j = 1, n = 0; n < Grid.xSz; n++, j *= 2) {
/* 1307 */                 if ((guideMask[m] & j) != 0)
/* 1308 */                   Grid.xstatus[k][y][n] = 0; 
/* 1309 */               }  found = true;
/*      */             } 
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1316 */     for (int x = 0; x < Grid.xSz; x++) {
/* 1317 */       int j; for (j = 0; j < 36; guideMaskHits[j++] = 0);
/* 1318 */       for (y = 0; y < Grid.ySz; y++) {
/* 1319 */         int theMask; for (theMask = i = 0, j = 1; i < Grid.xSz; i++, j *= 2) {
/* 1320 */           if (Grid.xstatus[x][y][i] > 0)
/* 1321 */             theMask |= j; 
/* 1322 */         }  for (m = 0; m < 36; m++) {
/* 1323 */           if (theMask == guideMask[m])
/* 1324 */             guideMaskHits[m] = guideMaskHits[m] + 1; 
/*      */         } 
/* 1326 */       }  for (m = 0; m < 36; m++) {
/* 1327 */         if (guideMaskHits[m] == 2)
/* 1328 */           for (y = 0; y < Grid.ySz; y++) {
/* 1329 */             int theMask; for (theMask = i = 0, j = 1; i < Grid.xSz; i++, j *= 2) {
/* 1330 */               if (Grid.xstatus[x][y][i] > 0)
/* 1331 */                 theMask |= j; 
/* 1332 */             }  if ((theMask & guideMask[m]) != 0 && (
/* 1333 */               theMask & (guideMask[m] ^ 0x1FF)) != 0) {
/* 1334 */               int k; for (j = 1, k = 0; k < Grid.xSz; k++, j *= 2) {
/* 1335 */                 if ((guideMask[m] & j) != 0)
/* 1336 */                   Grid.xstatus[x][y][k] = 0; 
/* 1337 */               }  found = true;
/*      */             } 
/*      */           }  
/*      */       } 
/*      */     } 
/* 1342 */     return found;
/*      */   }
/*      */   
/*      */   private void multiBuild() {
/* 1346 */     String title = Methods.puzzleTitle;
/* 1347 */     int[] sizeDef = { 5, 5, 5, 6, 6, 6, 7 };
/* 1348 */     int[] diffDef = { 0, 1, 2, 0, 1, 2, 2 };
/*      */ 
/*      */     
/* 1351 */     int saveKendokuSize = Op.getInt(Op.KE.KeSize.ordinal(), Op.ke);
/* 1352 */     int saveKendokuDiff = Op.getInt(Op.KE.KeDifficulty.ordinal(), Op.ke);
/*      */ 
/*      */     
/* 1355 */     SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
/* 1356 */     Calendar c = Calendar.getInstance();
/*      */     
/* 1358 */     for (this.hmCount = 1; this.hmCount <= this.howMany; this.hmCount++) {
/* 1359 */       if (this.startPuz > 9999999) { try {
/* 1360 */           c.setTime(sdf.parse("" + this.startPuz));
/* 1361 */         } catch (ParseException ex) {}
/* 1362 */         this.startPuz = Integer.parseInt(sdf.format(c.getTime())); }
/*      */       
/* 1364 */       Methods.puzzleTitle = "KENDOKU Puzzle : " + this.startPuz;
/* 1365 */       if (Op.getBool(Op.SX.VaryDiff.ordinal(), Op.sx).booleanValue()) {
/* 1366 */         Grid.xSz = Grid.ySz = sizeDef[(this.startPuz - 1) % 7];
/* 1367 */         Op.setInt(Op.KE.KeSize.ordinal(), sizeDef[(this.startPuz - 1) % 7], Op.ke);
/* 1368 */         Op.setInt(Op.KE.KeDifficulty.ordinal(), diffDef[(this.startPuz - 1) % 7], Op.ke);
/*      */       } 
/*      */       
/* 1371 */       Methods.buildProgress(jfKendoku, Op.ke[Op.KE.KePuz
/* 1372 */             .ordinal()] = "" + this.startPuz + ".kendoku");
/* 1373 */       buildKendoku();
/* 1374 */       restoreFrame();
/* 1375 */       Wait.shortWait(100);
/* 1376 */       if (Def.building == 2)
/*      */         return; 
/* 1378 */       this.startPuz++;
/*      */     } 
/* 1380 */     this.howMany = 1;
/* 1381 */     Methods.puzzleTitle = title;
/*      */ 
/*      */     
/* 1384 */     Op.setInt(Op.KE.KeSize.ordinal(), saveKendokuSize, Op.ke);
/* 1385 */     Op.setInt(Op.KE.KeDifficulty.ordinal(), saveKendokuDiff, Op.ke);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean buildKendoku() {
/*      */     do {
/* 1392 */       for (int y = 0; y < 9; y++) {
/* 1393 */         for (int x = 0; x < 9; x++)
/* 1394 */         { Grid.letter[x][y] = 49;
/* 1395 */           Grid.mode[x][y] = 85;
/* 1396 */           Grid.sol[x][y] = 0; } 
/*      */       } 
/* 1398 */     } while (!buildIt());
/*      */     
/* 1400 */     for (int j = 0; j < Grid.xSz; j++) {
/* 1401 */       for (int i = 0; i < Grid.ySz; i++)
/* 1402 */         Grid.sol[i][j] = 0; 
/*      */     } 
/* 1404 */     saveKendoku(Op.ke[Op.KE.KePuz.ordinal()]);
/* 1405 */     return true;
/*      */   }
/*      */   
/*      */   void handleKeyPressed(KeyEvent e) {
/* 1409 */     if (Def.building == 1)
/* 1410 */       return;  if (e.isAltDown())
/* 1411 */       return;  switch (e.getKeyCode()) { case 38:
/* 1412 */         if (Grid.yCur > 0) Grid.yCur--;  break;
/* 1413 */       case 40: if (Grid.yCur < Grid.ySz - 1) Grid.yCur++;  break;
/* 1414 */       case 37: if (Grid.xCur > 0) Grid.xCur--;  break;
/* 1415 */       case 39: if (Grid.xCur < Grid.xSz - 1) Grid.xCur++;  break;
/* 1416 */       case 36: Grid.xCur = 0; break;
/* 1417 */       case 35: Grid.xCur = Grid.xSz - 1; break;
/* 1418 */       case 33: Grid.yCur = 0; break;
/* 1419 */       case 34: Grid.yCur = Grid.ySz - 1; break;
/*      */       case 8:
/*      */       case 127:
/* 1422 */         Grid.sol[Grid.xCur][Grid.yCur] = 0;
/* 1423 */         Grid.color[Grid.xCur][Grid.yCur] = 16777215;
/*      */         break; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1431 */     restoreFrame();
/*      */   }
/*      */ }


/* Location:              C:\Users\mustapha\Desktop\Crossword-Express.jar!\crosswordexpress\KendokuBuild.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */