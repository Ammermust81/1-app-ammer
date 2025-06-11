/*      */ package crosswordexpress;
/*      */ import java.awt.FontMetrics;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.Toolkit;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.io.DataInputStream;
/*      */ import java.io.DataOutputStream;
/*      */ import javax.swing.JButton;
/*      */ import javax.swing.JComboBox;
/*      */ import javax.swing.JDialog;
/*      */ import javax.swing.JLabel;
/*      */ import javax.swing.JMenuItem;
/*      */ import javax.swing.KeyStroke;
/*      */ 
/*      */ public class FillominoBuild {
/*      */   static JFrame jfFillomino;
/*      */   static JMenuBar menuBar;
/*      */   JMenu menu;
/*      */   JMenu submenu;
/*      */   JMenuItem menuItem;
/*      */   JMenuItem buildMenuItem;
/*      */   static JPanel pp;
/*   24 */   int howMany = 1; static int panelW; static int panelH; static JLabel jl1; static JLabel jl2; static Timer myTimer; static Timer listenTimer; Thread thread; int startPuz = Integer.parseInt((new SimpleDateFormat("yyyyMMdd")).format(new Date())); int hmCount;
/*      */   static boolean sixpack;
/*   26 */   int[] depopulateList = new int[50];
/*      */   static Point screenLoc;
/*   28 */   static String rules = "Place numbers in the blank squares so that the puzzle is divided into zones with the area of each zone equal to the number contained within its squares. Zones having the same area may not touch each other.";
/*      */ 
/*      */ 
/*      */   
/*      */   static void def() {
/*   33 */     Op.updateOption(Op.FI.FiW.ordinal(), "500", Op.fi);
/*   34 */     Op.updateOption(Op.FI.FiH.ordinal(), "580", Op.fi);
/*   35 */     Op.updateOption(Op.FI.FiAcross.ordinal(), "8", Op.fi);
/*   36 */     Op.updateOption(Op.FI.FiDown.ordinal(), "8", Op.fi);
/*   37 */     Op.updateOption(Op.FI.Fi1.ordinal(), "FF5555", Op.fi);
/*   38 */     Op.updateOption(Op.FI.Fi2.ordinal(), "88FF88", Op.fi);
/*   39 */     Op.updateOption(Op.FI.Fi3.ordinal(), "8888FF", Op.fi);
/*   40 */     Op.updateOption(Op.FI.Fi4.ordinal(), "FFFF88", Op.fi);
/*   41 */     Op.updateOption(Op.FI.Fi5.ordinal(), "00FFFF", Op.fi);
/*   42 */     Op.updateOption(Op.FI.Fi6.ordinal(), "FF88FF", Op.fi);
/*   43 */     Op.updateOption(Op.FI.Fi7.ordinal(), "AAAAAA", Op.fi);
/*   44 */     Op.updateOption(Op.FI.Fi8.ordinal(), "00AAAA", Op.fi);
/*   45 */     Op.updateOption(Op.FI.FiEmpty.ordinal(), "FFFFFF", Op.fi);
/*   46 */     Op.updateOption(Op.FI.FiNumber.ordinal(), "000000", Op.fi);
/*   47 */     Op.updateOption(Op.FI.FiLines.ordinal(), "000000", Op.fi);
/*   48 */     Op.updateOption(Op.FI.FiError.ordinal(), "FF0000", Op.fi);
/*   49 */     Op.updateOption(Op.FI.FiPuz.ordinal(), "sample.fillomino", Op.fi);
/*   50 */     Op.updateOption(Op.FI.FiFont.ordinal(), "SansSerif", Op.fi);
/*   51 */     Op.updateOption(Op.FI.FiPuzColor.ordinal(), "false", Op.fi);
/*   52 */     Op.updateOption(Op.FI.FiSolColor.ordinal(), "false", Op.fi);
/*      */   }
/*      */   
/*   55 */   String fillominoHelp = "<div>A FILLOMINO puzzle consists of a square grid in which some of the squares contain a single digit number. To complete the puzzle, a Solver must place numbers in the blank squares so that the puzzle is divided into zones with the area of each zone equal to the number contained within its squares. The zones may take on any shape, but zones having the same area may not touch each other. Each puzzle has a unique solution which does no require any guesswork to achieve. Puzzles can be built in sizes from 4x4 up to 8x8.<br/><br/></div><span class='m'>Menu Functions</span><ul><li/><span class='s'>File Menu</span><ul><li/><span>Load a Puzzle</span><br/>Use this option to choose your puzzle from the pool of FILLOMINO puzzles currently available on your computer.<p/><li/><span>SaveAs</span><br/>This option allows you to make an exact copy of the current puzzle using a different file name. The copy will be saved in the <b>fillomino</b> folder along with all of the Fillomino puzzles you have made. Alternatively, if you don't enter a new name for the puzzle, you can change the Puzzle Description, or any of the other descriptive items without changing the puzzle name.<p/><li/><span>Quit Construction</span><br/>Returns you to the Crossword Express opening screen.<br/></ul><li/><span class='s'>Build Menu</span><ul><li/><span>Start a New Puzzle</span><br/>This option presents you with a dialog into which you can enter a file name for your new puzzle. You can also enter several other pieces of information such as a <b>Puzzle Title, Author</b> and <b>Copyright</b> information.<p/><li/><span>Build Options</span><br/>Use this option to access a Build Options dialog where you can set some rules to be followed by the puzzle building function as it builds your puzzle.<p/><li/><span>Start Building / Stop Building</span><br/>Construction of the puzzle will commence when you select the <b>Start Building</b> option. If puzzle building is successful you will receive a message containing the name of the puzzle file, and the location where it was saved. If it becomes necessary, you can interrupt the building process by selecting this option a second time. Note that during the construction phase, the text of this option is changed to <b>Stop Building</b><br/></ul><li/><span class='s'>View Menu</span><ul><li/><span>Display Options</span><br/>This leads you to a dialog box in which you can change the colors of various elements within the puzzle, and control the fonts which will be used for the puzzle's text components. You can also decide if printing of the puzzle will be done in black and white or in color.<br/></ul><li/><span class='s'>Export Menu</span><br/><ul><li/><span>Print a Fillomino KDP puzzle book.</span><br/>The letters KDP stand for <b>Kindle Direct Publishing</b>. This is a free publishing service operared by Amazon, in which they handle all matters related to printing, advertising and sales of books created by members of the public. A portion of the proceeds are retained by Amazon while the remainder is paid to the author. Fifteen of the Puzzles created by Crossword Express can be printed into PDF format files ready for publication by Amazon. When you select this option, you will be presented with a dialog which allows you to control the process. Please study the Help offered by this dialog before attempting to make use of it.<p/></ul><li/><span class='s'>Tasks Menu</span><ul><li/><span>Print this Puzzle</span><br/>This will take you to a custom print screen where you can control the details involved with printing your puzzle.<p/><li/><span>Solve this Puzzle</span><br/>This will take you to a Solve screen which provides a fully interactive environment for solving the puzzle.<p/><li/><span>Delete this Puzzle</span><br/>Use this option to eliminate unwanted FILLOMINO puzzles from your file system.<br/></ul><li/><span class='s'>Help Menu</span><ul><li/><span>Fillomino Help</span><br/>Displays the Help screen which you are now reading.<br/></ul></ul></body>";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  120 */   String fillominoOptions = "<div>Before you give the command to build the <b>Fillomino</b> puzzle, you can set some options which the program will use during the construction process.</div><br/><ul><li/>Fillomino puzzles can be made in sizes ranging from 4x4 up to 8x8. This can be controlled using the <b>Puzzle Size</b> Combo-box.<p/><li/>If you want to make a number of puzzles all having the same dimensions, simply type a number into the <b>How many puzzles</b> input field. When you issue the Make command, Crossword Express will make that number of puzzles. The puzzle names will be numbers which represent a date in <b>yyyymmdd</b> format. The default value presented by Crossword Express is always the current date, but you can change this to any date that suits your needs. As the series of puzzles is created, CWE will automatically step on to the next date in the sequence, taking into account such factors as the varying number of days in the months, and of course leap years. Virtually any number of puzzles can be made in a single operation using this feature.<p/><li/><b>HOWEVER:</b> If you prefer a simpler numbering scheme for your puzzles, you can enter any number of 7 digits or less to be used for your first puzzle, and Crossword Express will number the remainder of the puzzles sequentially starting with your number.<p/><li/>If you do choose to make multiple puzzles, then by default, Crossword Express will change the difficulty of the resulting puzzles over a cycle of seven puzzles. This would be useful for a daily newspaper so that the week could start with a very easy puzzle, with quite difficult puzzles reserved for the weekend. If you don't want this feature, clearing the <b>Vary Difficulty on 7 day cycle</b> checkbox will disable it.</ul></body>";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   FillominoBuild(JFrame jf, boolean auto, int hm, int start) {
/*  143 */     Def.puzzleMode = 70;
/*  144 */     Def.building = 0;
/*  145 */     Def.dispCursor = Boolean.valueOf(true);
/*  146 */     makeGrid();
/*      */     
/*  148 */     jfFillomino = new JFrame("Fillomino");
/*  149 */     if (Op.getInt(Op.FI.FiH.ordinal(), Op.fi) > Methods.scrH - 200) {
/*  150 */       int diff = Op.getInt(Op.FI.FiH.ordinal(), Op.fi) - Op.getInt(Op.FI.FiW.ordinal(), Op.fi);
/*  151 */       Op.setInt(Op.FI.FiH.ordinal(), Methods.scrH - 200, Op.fi);
/*  152 */       Op.setInt(Op.FI.FiW.ordinal(), Methods.scrH - 200 + diff, Op.fi);
/*      */     } 
/*  154 */     jfFillomino.setSize(Op.getInt(Op.FI.FiW.ordinal(), Op.fi), Op.getInt(Op.FI.FiH.ordinal(), Op.fi));
/*  155 */     int frameX = (jf.getX() + jfFillomino.getWidth() > Methods.scrW) ? (Methods.scrW - jfFillomino.getWidth() - 10) : jf.getX();
/*  156 */     jfFillomino.setLocation(frameX, jf.getY());
/*  157 */     jfFillomino.setLayout((LayoutManager)null);
/*  158 */     jfFillomino.setDefaultCloseOperation(0);
/*  159 */     jfFillomino
/*  160 */       .addComponentListener(new ComponentAdapter() {
/*      */           public void componentResized(ComponentEvent ce) {
/*  162 */             int oldw = Op.getInt(Op.FI.FiW.ordinal(), Op.fi);
/*  163 */             int oldh = Op.getInt(Op.FI.FiH.ordinal(), Op.fi);
/*  164 */             Methods.frameResize(FillominoBuild.jfFillomino, oldw, oldh, 500, 580);
/*  165 */             Op.setInt(Op.FI.FiW.ordinal(), FillominoBuild.jfFillomino.getWidth(), Op.fi);
/*  166 */             Op.setInt(Op.FI.FiH.ordinal(), FillominoBuild.jfFillomino.getHeight(), Op.fi);
/*  167 */             FillominoBuild.restoreFrame();
/*      */           }
/*      */         });
/*      */ 
/*      */     
/*  172 */     jfFillomino
/*  173 */       .addWindowListener(new WindowAdapter() {
/*      */           public void windowClosing(WindowEvent we) {
/*  175 */             if (Def.building == 1 || Def.selecting)
/*  176 */               return;  Op.saveOptions("fillomino.opt", Op.fi);
/*  177 */             CrosswordExpress.transfer(1, FillominoBuild.jfFillomino);
/*      */           }
/*      */         });
/*      */     
/*  181 */     Methods.closeHelp();
/*      */ 
/*      */     
/*  184 */     Runnable buildThread = () -> {
/*      */         if (this.howMany == 1) {
/*      */           buildFillomino();
/*      */         } else {
/*      */           multiBuild();
/*      */           
/*      */           if (sixpack) {
/*      */             Sixpack.trigger();
/*      */             jfFillomino.dispose();
/*      */             Def.building = 0;
/*      */             return;
/*      */           } 
/*      */         } 
/*      */         this.buildMenuItem.setText("Start Building");
/*      */         if (Def.building == 2) {
/*      */           Def.building = 0;
/*      */           Methods.interrupted(jfFillomino);
/*      */           makeGrid();
/*      */           restoreFrame();
/*      */           return;
/*      */         } 
/*      */         Methods.havePuzzle = true;
/*      */         restoreFrame();
/*      */         Methods.puzzleSaved(jfFillomino, "fillomino", Op.fi[Op.FI.FiPuz.ordinal()]);
/*      */         Def.building = 0;
/*      */       };
/*  210 */     jl1 = new JLabel(); jfFillomino.add(jl1);
/*  211 */     jl2 = new JLabel(); jfFillomino.add(jl2);
/*      */ 
/*      */     
/*  214 */     menuBar = new JMenuBar();
/*  215 */     menuBar.setBackground(Def.COLOR_MENUBAR);
/*  216 */     jfFillomino.setJMenuBar(menuBar);
/*      */     
/*  218 */     this.menu = new JMenu("File");
/*  219 */     menuBar.add(this.menu);
/*  220 */     this.menuItem = new JMenuItem("Load a Puzzle");
/*  221 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(76, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  222 */     this.menu.add(this.menuItem);
/*  223 */     this.menuItem
/*  224 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1)
/*      */             return; 
/*      */           pp.invalidate();
/*      */           pp.repaint();
/*      */           new Select(jfFillomino, "fillomino", "fillomino", Op.fi, Op.FI.FiPuz.ordinal(), false);
/*      */         });
/*  231 */     this.menuItem = new JMenuItem("SaveAs");
/*  232 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(65, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  233 */     this.menu.add(this.menuItem);
/*  234 */     this.menuItem
/*  235 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           Methods.puzzleDescriptionDialog(jfFillomino, Op.fi[Op.FI.FiPuz.ordinal()].substring(0, Op.fi[Op.FI.FiPuz.ordinal()].indexOf(".fillomino")), "fillomino", ".fillomino");
/*      */           if (Methods.clickedOK) {
/*      */             saveFillomino(Op.fi[Op.FI.FiPuz.ordinal()] = Methods.theFileName);
/*      */             restoreFrame();
/*      */             Methods.puzzleSaved(jfFillomino, "fillomino", Op.fi[Op.FI.FiPuz.ordinal()]);
/*      */           } 
/*      */         });
/*  246 */     this.menuItem = new JMenuItem("Quit Construction");
/*  247 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(81, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  248 */     this.menu.add(this.menuItem);
/*  249 */     this.menuItem
/*  250 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           
/*      */           Op.saveOptions("fillomino.opt", Op.fi);
/*      */           CrosswordExpress.transfer(1, jfFillomino);
/*      */         });
/*  258 */     this.menu = new JMenu("Build");
/*  259 */     menuBar.add(this.menu);
/*  260 */     this.menuItem = new JMenuItem("Start a new Puzzle");
/*  261 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(78, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  262 */     this.menu.add(this.menuItem);
/*  263 */     this.menuItem
/*  264 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           Methods.puzzleDescriptionDialog(jfFillomino, Op.fi[Op.FI.FiPuz.ordinal()].substring(0, Op.fi[Op.FI.FiPuz.ordinal()].indexOf(".fillomino")), "fillomino", ".fillomino");
/*      */           if (Methods.clickedOK) {
/*      */             Op.fi[Op.FI.FiPuz.ordinal()] = Methods.theFileName;
/*      */             makeGrid();
/*      */           } 
/*      */           restoreFrame();
/*      */         });
/*  275 */     this.menuItem = new JMenuItem("Build Options");
/*  276 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(79, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  277 */     this.menu.add(this.menuItem);
/*  278 */     this.menuItem
/*  279 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1)
/*      */             return; 
/*      */           fillominoOptions();
/*      */           if (Methods.clickedOK) {
/*      */             makeGrid();
/*      */             if (this.howMany > 1)
/*      */               Op.fi[Op.FI.FiPuz.ordinal()] = "" + this.startPuz + ".fillomino"; 
/*      */           } 
/*      */           restoreFrame();
/*      */         });
/*  290 */     this.buildMenuItem = new JMenuItem("Start Building");
/*  291 */     this.buildMenuItem.setAccelerator(KeyStroke.getKeyStroke(66, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  292 */     this.menu.add(this.buildMenuItem);
/*  293 */     this.buildMenuItem
/*  294 */       .addActionListener(ae -> {
/*      */           if (Op.fi[Op.FI.FiPuz.ordinal()].length() == 0 && this.howMany == 1) {
/*      */             Methods.noName(jfFillomino);
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
/*      */             this.buildMenuItem.setText("Stop Building");
/*      */           } else {
/*      */             Def.building = 2;
/*      */             restoreFrame();
/*      */           } 
/*      */         });
/*  313 */     this.menu = new JMenu("View");
/*  314 */     menuBar.add(this.menu);
/*  315 */     this.menuItem = new JMenuItem("Display Options");
/*  316 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(89, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  317 */     this.menu.add(this.menuItem);
/*  318 */     this.menuItem
/*  319 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           
/*      */           printOptions(jfFillomino, "Display Options");
/*      */           restoreFrame();
/*      */         });
/*  327 */     this.menu = new JMenu("Export");
/*  328 */     menuBar.add(this.menu);
/*  329 */     this.menuItem = new JMenuItem("Print a Fillomino KDP puzzle book.");
/*  330 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(75, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  331 */     this.menu.add(this.menuItem);
/*  332 */     this.menuItem
/*  333 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           
/*      */           Methods.printKdpDialog(jfFillomino, 70, 6);
/*      */         });
/*  340 */     this.menu = new JMenu("Tasks");
/*  341 */     menuBar.add(this.menu);
/*  342 */     this.menuItem = new JMenuItem("Print this Puzzle");
/*  343 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(80, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  344 */     this.menu.add(this.menuItem);
/*  345 */     this.menuItem
/*  346 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           CrosswordExpress.toPrint(jfFillomino, Op.fi[Op.FI.FiPuz.ordinal()]);
/*      */         });
/*  352 */     this.menuItem = new JMenuItem("Solve this Puzzle");
/*  353 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(86, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  354 */     this.menu.add(this.menuItem);
/*  355 */     this.menuItem
/*  356 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1)
/*      */             return; 
/*      */           if (Methods.havePuzzle) {
/*      */             CrosswordExpress.transfer(71, jfFillomino);
/*      */           } else {
/*      */             Methods.noPuzzle(jfFillomino, "Solve");
/*      */           } 
/*      */         });
/*  365 */     this.menuItem = new JMenuItem("Delete this Puzzle");
/*  366 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(68, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  367 */     this.menu.add(this.menuItem);
/*  368 */     this.menuItem
/*  369 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           
/*      */           if (Methods.deleteAPuzzle(jfFillomino, Op.fi[Op.FI.FiPuz.ordinal()], "fillomino", pp)) {
/*      */             makeGrid();
/*      */             loadFillomino(Op.fi[Op.FI.FiPuz.ordinal()]);
/*      */             restoreFrame();
/*      */           } 
/*      */         });
/*  380 */     this.menu = new JMenu("Help");
/*  381 */     menuBar.add(this.menu);
/*      */     
/*  383 */     this.menuItem = new JMenuItem("Fillomino Help");
/*  384 */     this.menu.add(this.menuItem);
/*  385 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(72, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  386 */     this.menuItem
/*  387 */       .addActionListener(ae -> Methods.cweHelp(jfFillomino, null, "Building Fillomino Puzzles", this.fillominoHelp));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  392 */     pp = new FillominoBuildPP(0, 37);
/*  393 */     jfFillomino.add(pp);
/*      */     
/*  395 */     pp
/*  396 */       .addMouseListener(new MouseAdapter() {
/*      */           public void mousePressed(MouseEvent e) {
/*  398 */             FillominoBuild.this.updateGrid(e);
/*      */           }
/*      */         });
/*      */ 
/*      */     
/*  403 */     pp
/*  404 */       .addMouseMotionListener(new MouseAdapter() {
/*      */           public void mouseMoved(MouseEvent e) {
/*  406 */             if (Def.isMac) {
/*  407 */               FillominoBuild.jfFillomino.setResizable((FillominoBuild.jfFillomino.getWidth() - e.getX() < 15 && FillominoBuild.jfFillomino
/*  408 */                   .getHeight() - e.getY() < 95));
/*      */             }
/*      */           }
/*      */         });
/*      */     
/*  413 */     jfFillomino
/*  414 */       .addKeyListener(new KeyAdapter() {
/*      */           public void keyPressed(KeyEvent e) {
/*  416 */             FillominoBuild.this.handleKeyPressed(e);
/*      */           }
/*      */         });
/*  419 */     loadFillomino(Op.fi[Op.FI.FiPuz.ordinal()]);
/*  420 */     restoreFrame();
/*      */     
/*  422 */     ActionListener timerAL = ae -> {
/*      */         myTimer.stop();
/*      */         this.thread = new Thread(paramRunnable);
/*      */         this.thread.start();
/*      */         Def.building = 1;
/*      */       };
/*  428 */     myTimer = new Timer(1000, timerAL);
/*      */     
/*  430 */     if (auto) {
/*  431 */       sixpack = true;
/*  432 */       this.howMany = hm; this.startPuz = start;
/*  433 */       myTimer.start();
/*      */     } 
/*      */   }
/*      */   
/*      */   static void restoreFrame() {
/*  438 */     jfFillomino.setVisible(true);
/*  439 */     Insets insets = jfFillomino.getInsets();
/*  440 */     panelW = jfFillomino.getWidth() - insets.left + insets.right;
/*  441 */     panelH = jfFillomino.getHeight() - insets.top + insets.bottom + 37 + menuBar.getHeight();
/*  442 */     pp.setSize(panelW, panelH);
/*  443 */     jfFillomino.requestFocusInWindow();
/*  444 */     pp.repaint();
/*  445 */     Methods.infoPanel(jl1, jl2, "Build Fillomino", "Puzzle : " + Op.fi[Op.FI.FiPuz.ordinal()], panelW);
/*      */   }
/*      */   
/*      */   static void setSizesAndOffsets(int x, int y, int width, int height, int inset, boolean print) {
/*  449 */     int i = (width - inset) / Grid.xSz;
/*  450 */     int j = (height - inset) / Grid.ySz;
/*  451 */     Grid.xCell = Grid.yCell = (i < j) ? i : j;
/*  452 */     Grid.xOrg = x + (print ? ((width - Grid.xSz * Grid.xCell) / 2) : 10);
/*  453 */     Grid.yOrg = y + (print ? ((height - Grid.ySz * Grid.yCell) / 2) : 10);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void fillominoOptions() {
/*  459 */     JDialog jdlgFillomino = new JDialog(jfFillomino, "Fillomino Options", true);
/*  460 */     jdlgFillomino.setSize(270, 270);
/*  461 */     jdlgFillomino.setResizable(false);
/*  462 */     jdlgFillomino.setLayout((LayoutManager)null);
/*  463 */     jdlgFillomino.setLocation(jfFillomino.getX(), jfFillomino.getY());
/*      */     
/*  465 */     jdlgFillomino
/*  466 */       .addWindowListener(new WindowAdapter() {
/*      */           public void windowClosing(WindowEvent we) {
/*  468 */             Methods.closeHelp();
/*      */           }
/*      */         });
/*      */     
/*  472 */     Methods.closeHelp();
/*      */     
/*  474 */     JLabel jlSize = new JLabel("Puzzle Size:");
/*  475 */     jlSize.setForeground(Def.COLOR_LABEL);
/*  476 */     jlSize.setSize(120, 20);
/*  477 */     jlSize.setLocation(10, 15);
/*  478 */     jlSize.setHorizontalAlignment(4);
/*  479 */     jdlgFillomino.add(jlSize);
/*      */     
/*  481 */     JComboBox<Integer> jcbbSize = new JComboBox<>();
/*  482 */     for (int i = 4; i <= 8; i++)
/*  483 */       jcbbSize.addItem(Integer.valueOf(i)); 
/*  484 */     jcbbSize.setSize(50, 20);
/*  485 */     jcbbSize.setLocation(140, 15);
/*  486 */     jdlgFillomino.add(jcbbSize);
/*  487 */     jcbbSize.setBackground(Def.COLOR_BUTTONBG);
/*  488 */     jcbbSize.setSelectedIndex(Op.getInt(Op.FI.FiAcross.ordinal(), Op.fi) - 4);
/*      */     
/*  490 */     HowManyPuzzles hmp = new HowManyPuzzles(jdlgFillomino, 10, 55, this.howMany, this.startPuz, Op.getBool(Op.SX.VaryDiff.ordinal(), Op.sx).booleanValue());
/*      */     
/*  492 */     JButton jbOK = Methods.cweButton("OK", 10, 169, 80, 26, null);
/*  493 */     jbOK.addActionListener(e -> {
/*      */           Grid.xSz = Grid.ySz = paramJComboBox.getSelectedIndex() + 4;
/*      */           Op.setInt(Op.FI.FiAcross.ordinal(), Grid.xSz, Op.fi);
/*      */           Op.setInt(Op.FI.FiDown.ordinal(), Grid.xSz, Op.fi);
/*      */           this.howMany = Integer.parseInt(paramHowManyPuzzles.jtfHowMany.getText());
/*      */           this.startPuz = Integer.parseInt(paramHowManyPuzzles.jtfStartPuz.getText());
/*      */           Op.setBool(Op.SX.VaryDiff.ordinal(), Boolean.valueOf(paramHowManyPuzzles.jcbVaryDiff.isSelected()), Op.sx);
/*      */           Methods.clickedOK = true;
/*      */           paramJDialog.dispose();
/*      */           Methods.closeHelp();
/*      */         });
/*  504 */     jdlgFillomino.add(jbOK);
/*      */     
/*  506 */     JButton jbCancel = Methods.cweButton("Cancel", 10, 204, 80, 26, null);
/*  507 */     jbCancel.addActionListener(e -> {
/*      */           Methods.clickedOK = false;
/*      */           paramJDialog.dispose();
/*      */           Methods.closeHelp();
/*      */         });
/*  512 */     jdlgFillomino.add(jbCancel);
/*      */     
/*  514 */     JButton jbHelp = Methods.cweButton("<html><font size=6 color=BB0000 face=Serif>Help ", 100, 169, 150, 61, new ImageIcon("graphics/help.png"));
/*  515 */     jbHelp.addActionListener(e -> Methods.cweHelp(null, paramJDialog, "Fillomino Options", this.fillominoOptions));
/*      */     
/*  517 */     jdlgFillomino.add(jbHelp);
/*      */     
/*  519 */     jdlgFillomino.getRootPane().setDefaultButton(jbOK);
/*  520 */     Methods.setDialogSize(jdlgFillomino, 260, 240);
/*      */   }
/*      */   
/*      */   static void printOptions(JFrame jf, String type) {
/*  524 */     String[] colorLabel = { "Cell 1 Color", "Cell 2 Color", "Cell 3 Color", "Cell 4 Color", "Cell 5 Color", "Cell 6 Color", "Cell 7 Color", "Cell 8 Color", "Empty cells", "Number color", "Line color", "Error color" };
/*  525 */     int[] colorInt = { Op.FI.Fi1.ordinal(), Op.FI.Fi2.ordinal(), Op.FI.Fi3.ordinal(), Op.FI.Fi4.ordinal(), Op.FI.Fi5.ordinal(), Op.FI.Fi6.ordinal(), Op.FI.Fi7.ordinal(), Op.FI.Fi8.ordinal(), Op.FI.FiEmpty.ordinal(), Op.FI.FiNumber.ordinal(), Op.FI.FiLines.ordinal(), Op.FI.FiError.ordinal() };
/*  526 */     String[] fontLabel = { "Puzzle Font" };
/*  527 */     int[] fontInt = { Op.FI.FiFont.ordinal() };
/*  528 */     String[] checkLabel = { "PPrint Puzzle with color.", "SPrint Solution with color." };
/*  529 */     int[] checkInt = { Op.FI.FiPuzColor.ordinal(), Op.FI.FiSolColor.ordinal() };
/*  530 */     Methods.stdPrintOptions(jf, "Fillomino " + type, Op.fi, colorLabel, colorInt, fontLabel, fontInt, checkLabel, checkInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void saveFillomino(String fillominoName) {
/*      */     try {
/*  539 */       DataOutputStream dataOut = new DataOutputStream(new FileOutputStream("fillomino/" + fillominoName));
/*  540 */       dataOut.writeInt(Grid.xSz);
/*  541 */       dataOut.writeInt(Grid.ySz);
/*  542 */       dataOut.writeByte(Methods.noReveal);
/*  543 */       dataOut.writeByte(Methods.noErrors);
/*  544 */       for (int i = 0; i < 54; i++)
/*  545 */         dataOut.writeByte(0); 
/*  546 */       for (int j = 0; j < Grid.ySz; j++) {
/*  547 */         for (int k = 0; k < Grid.xSz; k++) {
/*  548 */           dataOut.writeInt(Grid.sol[k][j]);
/*  549 */           dataOut.writeInt(Grid.letter[k][j]);
/*  550 */           dataOut.writeInt(Grid.copy[k][j]);
/*      */         } 
/*  552 */       }  dataOut.writeUTF(Methods.puzzleTitle);
/*  553 */       dataOut.writeUTF(Methods.author);
/*  554 */       dataOut.writeUTF(Methods.copyright);
/*  555 */       dataOut.writeUTF(Methods.puzzleNumber);
/*  556 */       dataOut.writeUTF(Methods.puzzleNotes);
/*  557 */       dataOut.close();
/*      */     }
/*  559 */     catch (IOException exc) {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void loadFillomino(String fillominoName) {
/*      */     
/*  567 */     try { File fl = new File("fillomino/" + fillominoName);
/*  568 */       if (!fl.exists()) {
/*  569 */         fl = new File("fillomino/");
/*  570 */         String[] s = fl.list(); int k;
/*  571 */         for (k = 0; k < s.length && (
/*  572 */           s[k].lastIndexOf(".fillomino") == -1 || s[k].charAt(0) == '.'); k++);
/*      */         
/*  574 */         if (k == s.length) { makeGrid(); return; }
/*  575 */          fillominoName = s[k];
/*  576 */         Op.fi[Op.FI.FiPuz.ordinal()] = fillominoName;
/*      */       } 
/*      */ 
/*      */       
/*  580 */       DataInputStream dataIn = new DataInputStream(new FileInputStream("fillomino/" + fillominoName));
/*  581 */       Grid.xSz = dataIn.readInt();
/*  582 */       Grid.ySz = dataIn.readInt();
/*  583 */       Methods.noReveal = dataIn.readByte();
/*  584 */       Methods.noErrors = dataIn.readByte(); int i;
/*  585 */       for (i = 0; i < 54; i++)
/*  586 */         dataIn.readByte(); 
/*  587 */       for (int j = 0; j < Grid.ySz; j++) {
/*  588 */         for (i = 0; i < Grid.xSz; i++) {
/*  589 */           Grid.sol[i][j] = dataIn.readInt();
/*  590 */           Grid.letter[i][j] = dataIn.readInt();
/*  591 */           Grid.copy[i][j] = dataIn.readInt();
/*      */         } 
/*  593 */       }  Methods.puzzleTitle = dataIn.readUTF();
/*  594 */       Methods.author = dataIn.readUTF();
/*  595 */       Methods.copyright = dataIn.readUTF();
/*  596 */       Methods.puzzleNumber = dataIn.readUTF();
/*  597 */       Methods.puzzleNotes = dataIn.readUTF();
/*  598 */       dataIn.close(); }
/*      */     
/*  600 */     catch (IOException exc) { return; }
/*  601 */      Methods.havePuzzle = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void drawFillomino(Graphics2D g2, int[][] puzzleArray) {
/*  609 */     Stroke normalStroke = new BasicStroke(Grid.xCell / 25.0F, 2, 2);
/*  610 */     Stroke wideStroke = new BasicStroke(Grid.xCell / 10.0F, 2, 2);
/*  611 */     g2.setStroke(normalStroke);
/*      */     
/*  613 */     RenderingHints rh = g2.getRenderingHints();
/*  614 */     rh.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
/*  615 */     rh.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
/*  616 */     g2.setRenderingHints(rh);
/*      */     
/*      */     int j;
/*  619 */     for (j = 0; j < Grid.ySz; j++) {
/*  620 */       for (int i = 0; i < Grid.xSz; i++) {
/*  621 */         int theColor = Def.dispWithColor.booleanValue() ? ((Grid.letter[i][j] == 0) ? Op.getColorInt(Op.FI.FiEmpty.ordinal(), Op.fi) : Op.getColorInt(Op.FI.Fi1.ordinal() + Grid.letter[i][j] - 49, Op.fi)) : 16777215;
/*  622 */         g2.setColor(new Color(theColor));
/*  623 */         g2.fillRect(Grid.xOrg + i * Grid.xCell, Grid.yOrg + j * Grid.yCell, Grid.xCell, Grid.yCell);
/*  624 */         g2.setColor(new Color(Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.FI.FiLines.ordinal(), Op.fi) : 0));
/*      */       } 
/*      */     } 
/*      */     
/*  628 */     for (j = 0; j < Grid.ySz + 1; j++)
/*  629 */       g2.drawLine(Grid.xOrg, Grid.yOrg + j * Grid.yCell, Grid.xOrg + Grid.xSz * Grid.xCell, Grid.yOrg + j * Grid.yCell); 
/*  630 */     for (j = 0; j < Grid.xSz + 1; j++) {
/*  631 */       g2.drawLine(Grid.xOrg + j * Grid.xCell, Grid.yOrg, Grid.xOrg + j * Grid.xCell, Grid.yOrg + Grid.xSz * Grid.yCell);
/*      */     }
/*      */     
/*  634 */     g2.setFont(new Font(Op.fi[Op.FI.FiFont.ordinal()], 0, 8 * Grid.yCell / 10));
/*  635 */     FontMetrics fm = g2.getFontMetrics();
/*  636 */     g2.setColor(new Color(Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.FI.FiNumber.ordinal(), Op.fi) : 0));
/*  637 */     for (j = 0; j < Grid.ySz; j++) {
/*  638 */       for (int i = 0; i < Grid.xSz; i++) {
/*  639 */         char ch = (char)puzzleArray[i][j];
/*  640 */         if (Character.isDigit(ch) || Character.isLetter(ch)) {
/*  641 */           int w = fm.stringWidth("" + ch);
/*  642 */           g2.drawString("" + ch, Grid.xOrg + i * Grid.xCell + (Grid.xCell - w) / 2, Grid.yOrg + j * Grid.yCell + (Grid.yCell + fm.getAscent() - fm.getDescent()) / 2);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  647 */     if (Def.building == 1)
/*      */       return; 
/*  649 */     if (Def.dispCursor.booleanValue()) {
/*  650 */       g2.setStroke(wideStroke);
/*  651 */       g2.setColor(Def.COLOR_RED);
/*  652 */       g2.drawRect(Grid.xOrg + Grid.xCur * Grid.xCell, Grid.yOrg + Grid.yCur * Grid.yCell, Grid.xCell, Grid.yCell);
/*      */     } 
/*      */     
/*  655 */     g2.setStroke(new BasicStroke(1.0F));
/*      */   }
/*      */   
/*      */   static void printPuz(Graphics2D g2, int left, int top, int width, int height) {
/*  659 */     loadFillomino(Op.fi[Op.FI.FiPuz.ordinal()]);
/*  660 */     FillominoSolve.clearSolution();
/*  661 */     setSizesAndOffsets(left, top, width, height, 0, false);
/*  662 */     Def.dispWithColor = Op.getBool(Op.FI.FiPuzColor.ordinal(), Op.fi);
/*  663 */     FillominoSolve.drawFillomino(g2, Grid.copy);
/*  664 */     Def.dispWithColor = Boolean.valueOf(true);
/*      */   }
/*      */   
/*      */   static void printSol(Graphics2D g2, int left, int top, int width, int height, String solutionPuzzle) {
/*  668 */     loadFillomino(solutionPuzzle);
/*  669 */     setSizesAndOffsets(left, top, width, height, 0, false);
/*  670 */     Def.dispWithColor = Op.getBool(Op.FI.FiSolColor.ordinal(), Op.fi);
/*  671 */     drawFillomino(g2, Grid.letter);
/*  672 */     Def.dispWithColor = Boolean.valueOf(true);
/*  673 */     loadFillomino(Op.fi[Op.FI.FiPuz.ordinal()]);
/*      */   }
/*      */   
/*      */   static void printSolTitle(Graphics2D g2, int left, int top, int width, int height, String solutionPuzzle) {
/*  677 */     loadFillomino(solutionPuzzle);
/*  678 */     Print.outputTextItem(g2, left, top, width, height, "SansSerif", 0, Methods.puzzleTitle);
/*  679 */     loadFillomino(Op.fi[Op.FI.FiPuz.ordinal()]);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static void printSixpackPuz(Graphics2D g2, int left, int top, int dim, int gap, String puzName) {
/*  685 */     FontMetrics fm = g2.getFontMetrics();
/*      */     
/*  687 */     String st = Op.sx[Op.SX.SxFi.ordinal()];
/*  688 */     if (st.length() < 3) st = "FILLOMINO"; 
/*  689 */     int w = fm.stringWidth(st);
/*  690 */     g2.drawString(st, left + (dim - w) / 2, top - gap / 3);
/*  691 */     FillominoSolve.loadFillomino(puzName + ".fillomino");
/*  692 */     setSizesAndOffsets(left, top, dim, dim, 0, true);
/*  693 */     FillominoSolve.clearSolution();
/*  694 */     FillominoSolve.drawFillomino(g2, Grid.copy);
/*  695 */     if (Op.sx[Op.SX.SxRuleLang.ordinal()].equals("English")) {
/*  696 */       st = rules;
/*      */     } else {
/*  698 */       st = Op.fi[Op.FI.FiRule1.ordinal() + Op.getInt(Op.SX.SxRuleLangIndex.ordinal(), Op.sx) - 1];
/*  699 */     }  if (Op.getBool(Op.SX.SxInstructions.ordinal(), Op.sx).booleanValue()) {
/*  700 */       Methods.renderText(g2, left, top + dim + dim / 50, dim, dim / 4, "SansSerif", 1, st, 3, 4, true, 0, 0);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static void printSixpackSol(Graphics2D g2, int left, int top, int dim, int gap, String solName) {
/*  706 */     FontMetrics fm = g2.getFontMetrics();
/*      */     
/*  708 */     String st = Op.sx[Op.SX.SxFi.ordinal()];
/*  709 */     if (st.length() < 3) st = "FILLOMINO"; 
/*  710 */     int w = fm.stringWidth(st);
/*  711 */     g2.drawString(st, left + (dim - w) / 2, top - gap / 3);
/*  712 */     loadFillomino(solName + ".fillomino");
/*  713 */     setSizesAndOffsets(left, top, dim, dim, 0, true);
/*  714 */     drawFillomino(g2, Grid.letter);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static void printKDPPuz(Graphics2D g2, int left, int top, int dim, int gap, String puzName) {
/*  720 */     FontMetrics fm = g2.getFontMetrics();
/*      */     
/*  722 */     String st = puzName;
/*  723 */     int w = fm.stringWidth(st);
/*  724 */     g2.drawString(st, left + (dim - w) / 2, top - gap / 3);
/*  725 */     FillominoSolve.loadFillomino(puzName + ".fillomino");
/*  726 */     setSizesAndOffsets(left, top, dim, dim, 0, true);
/*  727 */     FillominoSolve.clearSolution();
/*  728 */     FillominoSolve.drawFillomino(g2, Grid.copy);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static void printKDPSol(Graphics2D g2, int left, int top, int dim, int gap, String solName) {
/*  734 */     FontMetrics fm = g2.getFontMetrics();
/*      */     
/*  736 */     String st = solName;
/*  737 */     int w = fm.stringWidth(st);
/*  738 */     g2.drawString(st, left + (dim - w) / 2, top - gap / 3);
/*  739 */     loadFillomino(solName + ".fillomino");
/*  740 */     setSizesAndOffsets(left, top, dim, dim, 0, true);
/*  741 */     drawFillomino(g2, Grid.letter);
/*      */   }
/*      */   
/*      */   static void makeGrid() {
/*  745 */     Methods.havePuzzle = false;
/*  746 */     Grid.clearGrid();
/*  747 */     Grid.xSz = Op.getInt(Op.FI.FiAcross.ordinal(), Op.fi);
/*  748 */     Grid.ySz = Op.getInt(Op.FI.FiDown.ordinal(), Op.fi);
/*      */   }
/*      */   
/*      */   void updateGrid(MouseEvent e) {
/*  752 */     int x = e.getX(), y = e.getY();
/*      */     
/*  754 */     if (Def.building == 1)
/*  755 */       return;  if (x < Grid.xOrg || y < Grid.yOrg)
/*  756 */       return;  x = (x - Grid.xOrg) / Grid.xCell;
/*  757 */     y = (y - Grid.yOrg) / Grid.yCell;
/*  758 */     if (x >= Grid.xSz || y >= Grid.ySz)
/*      */       return; 
/*  760 */     Grid.xCur = x; Grid.yCur = y;
/*  761 */     restoreFrame();
/*      */   }
/*      */   
/*      */   boolean validateOneCell(int x, int y, int area) {
/*  765 */     if (x < 0 || y < 0 || x >= Grid.xSz || y >= Grid.ySz) return true; 
/*  766 */     if (Grid.scratch[x][y] == 0 && Grid.sol[x][y] == area) return false; 
/*  767 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   boolean validateFillomino(int area) {
/*  773 */     for (int j = 0; j < Grid.ySz; j++) {
/*  774 */       for (int i = 0; i < Grid.xSz; i++) {
/*  775 */         if (Grid.scratch[i][j] == area)
/*  776 */         { if (!validateOneCell(i + 1, j, area)) return false; 
/*  777 */           if (!validateOneCell(i, j + 1, area)) return false; 
/*  778 */           if (!validateOneCell(i - 1, j, area)) return false; 
/*  779 */           if (!validateOneCell(i, j - 1, area)) return false;  } 
/*      */       } 
/*  781 */     }  return true;
/*      */   }
/*      */ 
/*      */   
/*      */   boolean recurse(int x, int y, int area, int step) {
/*  786 */     boolean found = false;
/*      */     
/*  788 */     step++;
/*  789 */     Grid.scratch[x][y] = area;
/*  790 */     if (step == area - 48) {
/*  791 */       if (validateFillomino(area)) {
/*  792 */         found = true;
/*  793 */         for (int i = 0; i < Grid.ySz; i++) {
/*  794 */           for (int k = 0; k < Grid.xSz; k++) {
/*  795 */             if (Grid.scratch[k][i] == 0)
/*  796 */             { Grid.control[k][i] = 0; }
/*      */             else
/*  798 */             { Grid.sig[k][i] = (Grid.sig[k][i] == 0) ? area : -1; } 
/*      */           } 
/*      */         } 
/*  801 */       }  step--;
/*  802 */       Grid.scratch[x][y] = 0;
/*  803 */       return found;
/*      */     } 
/*      */     
/*  806 */     for (int j = 0; j < Grid.ySz; j++) {
/*  807 */       for (int i = 0; i < Grid.xSz; i++) {
/*  808 */         if (Grid.scratch[i][j] == 0 && (Grid.sol[i][j] == 0 || Grid.sol[i][j] == area) && ((
/*  809 */           i > 0 && Grid.scratch[i - 1][j] != 0) || (j > 0 && Grid.scratch[i][j - 1] != 0) || (i < Grid.xSz - 1 && Grid.scratch[i + 1][j] != 0) || (j < Grid.ySz - 1 && Grid.scratch[i][j + 1] != 0)))
/*      */         {
/*      */ 
/*      */           
/*  813 */           found |= recurse(i, j, area, step); } 
/*      */       } 
/*      */     } 
/*  816 */     step--;
/*  817 */     Grid.scratch[x][y] = 0;
/*  818 */     return found;
/*      */   }
/*      */ 
/*      */   
/*      */   boolean solveFillomino() {
/*  823 */     boolean found = true;
/*      */ 
/*      */ 
/*      */     
/*      */     int j;
/*      */ 
/*      */     
/*  830 */     for (j = 0; j < Grid.ySz; j++) {
/*  831 */       for (int i = 0; i < Grid.xSz; i++)
/*  832 */         Grid.sol[i][j] = Grid.copy[i][j]; 
/*      */     } 
/*  834 */     while (found) {
/*  835 */       found = false; int y;
/*  836 */       for (y = 0; y < Grid.ySz; y++) {
/*  837 */         for (int x = 0; x < Grid.xSz; x++)
/*  838 */           Grid.sig[x][y] = 0; 
/*      */       } 
/*  840 */       for (j = 0; j < Grid.ySz; j++) {
/*  841 */         for (int i = 0; i < Grid.xSz; i++) {
/*  842 */           if (Grid.copy[i][j] != 0) {
/*  843 */             for (y = 0; y < Grid.ySz; y++) {
/*  844 */               for (int x = 0; x < Grid.xSz; x++) {
/*  845 */                 Grid.scratch[x][y] = 0;
/*  846 */                 Grid.control[x][y] = 1;
/*      */               } 
/*  848 */             }  if (recurse(i, j, Grid.copy[i][j], 0))
/*  849 */               for (y = 0; y < Grid.ySz; y++) {
/*  850 */                 for (int x = 0; x < Grid.xSz; x++)
/*  851 */                 { if (Grid.control[x][y] == 1 && Grid.sol[x][y] == 0)
/*  852 */                   { Grid.sol[x][y] = Grid.copy[i][j];
/*  853 */                     found = true; }  } 
/*      */               }  
/*      */           } 
/*      */         } 
/*  857 */       }  for (y = 0; y < Grid.ySz; y++) {
/*  858 */         for (int x = 0; x < Grid.xSz; x++) {
/*  859 */           if (Grid.sig[x][y] > 0)
/*  860 */             Grid.sol[x][y] = Grid.sig[x][y]; 
/*      */         } 
/*      */       } 
/*  863 */     }  for (j = 0; j < Grid.ySz; j++) {
/*  864 */       for (int i = 0; i < Grid.xSz; i++)
/*  865 */       { if (Grid.letter[i][j] != 0)
/*  866 */         { if (Grid.sol[i][j] != Grid.letter[i][j]) {
/*  867 */             return false;
/*      */           } }
/*      */         
/*  870 */         else if (Grid.sol[i][j] == 0)
/*  871 */         { return false; }  } 
/*  872 */     }  return true;
/*      */   }
/*      */   
/*      */   void depopulate2() {
/*  876 */     Random r = new Random();
/*  877 */     int sz = Grid.xSz * Grid.ySz, list[] = new int[64];
/*      */     int i;
/*  879 */     for (i = 0; i < sz; ) { list[i] = i; i++; }
/*  880 */      for (i = 0; i < sz; i++) {
/*  881 */       int v = r.nextInt(sz);
/*  882 */       int j = list[i]; list[i] = list[v]; list[v] = j;
/*      */     } 
/*      */     
/*  885 */     for (i = 0; i < sz; i++) {
/*  886 */       int mem = Grid.copy[list[i] % Grid.xSz][list[i] / Grid.xSz];
/*  887 */       Grid.copy[list[i] % Grid.xSz][list[i] / Grid.xSz] = 0;
/*  888 */       if (!solveFillomino())
/*  889 */         Grid.copy[list[i] % Grid.xSz][list[i] / Grid.xSz] = mem; 
/*      */     } 
/*      */   }
/*      */   
/*      */   boolean fillGrid() {
/*  894 */     Random r = new Random();
/*      */     
/*  896 */     int[][] size = { { 1 }, { 1 }, { 1 }, { 1 }, { 5, 4, 3, 2, 1, 1, 0 }, { 6, 5, 4, 3, 3, 2, 1, 1, 0 }, { 6, 5, 5, 4, 4, 3, 3, 2, 2, 1, 1, 0 }, { 7, 6, 5, 5, 4, 4, 3, 3, 3, 2, 2, 2, 1, 1, 1, 0 }, { 8, 7, 6, 6, 5, 5, 4, 4, 3, 3, 3, 3, 2, 2, 1, 1, 1, 0 } };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  903 */     makeGrid(); int x;
/*  904 */     for (x = 0; x < 50; this.depopulateList[x++] = -1);
/*  905 */     int dLcount = 0; int val;
/*  906 */     for (int piece = 0; (val = 48 + size[Grid.xSz][piece]) > 48; piece++) {
/*  907 */       for (int seg = 0, count = seg; seg < val - 48; ) {
/*  908 */         int i = r.nextInt(Grid.xSz * Grid.ySz);
/*  909 */         x = i % Grid.xSz; int j = i / Grid.xSz;
/*  910 */         if (Grid.copy[x][j] == 0 && (
/*  911 */           x == 0 || Grid.copy[x - 1][j] != val) && (
/*  912 */           j == 0 || Grid.copy[x][j - 1] != val) && (
/*  913 */           x == Grid.xSz - 1 || Grid.copy[x + 1][j] != val) && (
/*  914 */           j == Grid.ySz - 1 || Grid.copy[x][j + 1] != val) && (
/*  915 */           seg == 0 || (x > 0 && Grid.copy[x - 1][j] == 88) || (j > 0 && Grid.copy[x][j - 1] == 88) || (x < Grid.xSz - 1 && Grid.copy[x + 1][j] == 88) || (j < Grid.ySz - 1 && Grid.copy[x][j + 1] == 88))) {
/*      */ 
/*      */ 
/*      */           
/*  919 */           if (seg % 2 == 1) this.depopulateList[dLcount++] = x + j * Grid.xSz; 
/*  920 */           Grid.copy[x][j] = 88;
/*  921 */           seg++;
/*      */         } 
/*  923 */         if (++count == 200)
/*  924 */           return false; 
/*      */       } 
/*  926 */       for (int y = 0; y < Grid.ySz; y++) {
/*  927 */         for (x = 0; x < Grid.xSz; x++)
/*  928 */         { if (Grid.copy[x][y] == 88)
/*  929 */             Grid.copy[x][y] = val;  } 
/*      */       } 
/*  931 */     }  return true;
/*      */   }
/*      */   
/*      */   private void multiBuild() {
/*  935 */     String title = Methods.puzzleTitle;
/*  936 */     int[] sizeDef = { 4, 5, 6, 7, 7, 8, 8 };
/*  937 */     String saveAcross = Op.fi[Op.FI.FiAcross.ordinal()];
/*  938 */     String saveDown = Op.fi[Op.FI.FiDown.ordinal()];
/*      */     
/*  940 */     SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
/*  941 */     Calendar c = Calendar.getInstance();
/*      */     
/*  943 */     for (this.hmCount = 1; this.hmCount <= this.howMany; this.hmCount++) {
/*  944 */       if (this.startPuz > 9999999) { try {
/*  945 */           c.setTime(sdf.parse("" + this.startPuz));
/*  946 */         } catch (ParseException ex) {}
/*  947 */         this.startPuz = Integer.parseInt(sdf.format(c.getTime())); }
/*      */ 
/*      */       
/*  950 */       Methods.puzzleTitle = "FILLOMINO Puzzle : " + this.startPuz;
/*  951 */       if (Op.getBool(Op.SX.VaryDiff.ordinal(), Op.sx).booleanValue()) {
/*  952 */         Op.setInt(Op.FI.FiAcross.ordinal(), sizeDef[(this.startPuz - 1) % 7], Op.fi);
/*  953 */         Op.setInt(Op.FI.FiDown.ordinal(), sizeDef[(this.startPuz - 1) % 7], Op.fi);
/*      */       } 
/*      */       
/*  956 */       Methods.buildProgress(jfFillomino, Op.fi[Op.FI.FiPuz
/*  957 */             .ordinal()] = "" + this.startPuz + ".fillomino");
/*  958 */       buildFillomino();
/*  959 */       restoreFrame();
/*  960 */       Wait.shortWait(100);
/*  961 */       if (Def.building == 2)
/*      */         return; 
/*  963 */       this.startPuz++;
/*      */     } 
/*  965 */     this.howMany = 1;
/*  966 */     Methods.puzzleTitle = title;
/*  967 */     Op.fi[Op.FI.FiAcross.ordinal()] = saveAcross;
/*  968 */     Op.fi[Op.FI.FiDown.ordinal()] = saveDown;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean buildFillomino() {
/*  974 */     for (int loop = 0;; loop++) {
/*  975 */       if (!fillGrid())
/*  976 */         continue;  for (int j = 0; j < Grid.ySz; j++) {
/*  977 */         for (int k = 0; k < Grid.xSz; k++)
/*  978 */           Grid.letter[k][j] = Grid.copy[k][j]; 
/*      */       } 
/*  980 */       for (int i = 0; this.depopulateList[i] > -1; i++)
/*  981 */         Grid.copy[this.depopulateList[i] % Grid.xSz][this.depopulateList[i] / Grid.xSz] = 0; 
/*  982 */       if (solveFillomino())
/*      */         break; 
/*  984 */       if (Def.building == 2)
/*  985 */         return true; 
/*  986 */       if (this.howMany == 1 && loop % 10 == 0) {
/*  987 */         restoreFrame();
/*  988 */         Methods.buildProgress(jfFillomino, Op.fi[Op.FI.FiPuz.ordinal()]);
/*      */       } 
/*      */     } 
/*      */     
/*  992 */     depopulate2();
/*      */     
/*  994 */     for (byte b = 0; b < Grid.ySz; b++) {
/*  995 */       for (byte b1 = 0; b1 < Grid.xSz; b1++)
/*  996 */         Grid.sol[b1][b] = Grid.copy[b1][b]; 
/*  997 */     }  saveFillomino(Op.fi[Op.FI.FiPuz.ordinal()]);
/*  998 */     return true;
/*      */   }
/*      */   void handleKeyPressed(KeyEvent e) {
/*      */     char ch;
/* 1002 */     if (Def.building == 1)
/* 1003 */       return;  if (e.isAltDown())
/* 1004 */       return;  switch (e.getKeyCode()) { case 38:
/* 1005 */         if (Grid.yCur > 0) Grid.yCur--;  break;
/* 1006 */       case 40: if (Grid.yCur < Grid.ySz - 1) Grid.yCur++;  break;
/* 1007 */       case 37: if (Grid.xCur > 0) Grid.xCur--;  break;
/* 1008 */       case 39: if (Grid.xCur < Grid.xSz - 1) Grid.xCur++;  break;
/* 1009 */       case 36: Grid.xCur = 0; break;
/* 1010 */       case 35: Grid.xCur = Grid.xSz - 1; break;
/* 1011 */       case 33: Grid.yCur = 0; break;
/* 1012 */       case 34: Grid.yCur = Grid.ySz - 1; break;
/*      */       case 8:
/*      */       case 10:
/*      */       case 32:
/*      */       case 127:
/* 1017 */         Grid.copy[Grid.xCur][Grid.yCur] = 0;
/*      */         break;
/*      */       default:
/* 1020 */         ch = e.getKeyChar();
/* 1021 */         if (Character.isDigit(ch) && ch != '0') {
/* 1022 */           Grid.letter[Grid.xCur][Grid.yCur] = ch; Grid.copy[Grid.xCur][Grid.yCur] = ch;
/*      */         }  break; }
/*      */     
/* 1025 */     restoreFrame();
/*      */   }
/*      */ }


/* Location:              C:\Users\mustapha\Desktop\Crossword-Express.jar!\crosswordexpress\FillominoBuild.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */