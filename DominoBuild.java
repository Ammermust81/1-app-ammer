/*     */ package crosswordexpress;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JMenuItem;
/*     */ import javax.swing.KeyStroke;
/*     */ 
/*     */ public class DominoBuild {
/*     */   static JFrame jfDomino;
/*     */   static JMenuBar menuBar;
/*     */   JMenu menu;
/*     */   JMenu submenu;
/*     */   JMenuItem menuItem;
/*     */   JMenuItem buildMenuItem;
/*  23 */   int howMany = 1; static JPanel pp; static int panelW; static int panelH; static JLabel jl1; static JLabel jl2; Timer myTimer; Thread thread; int startPuz = Integer.parseInt((new SimpleDateFormat("yyyyMMdd")).format(new Date())); int hmCount;
/*     */   boolean sixpack;
/*  25 */   int[][] busyBit = new int[10][10];
/*  26 */   static String rules = "A set of dominos from 0-0 to <n1> has been laid out horizontally and vertically into a rectangular array. Draw a line around pairs of numbers to show the location of each domino. There is a single unique solution, and no guessing is required.";
/*     */ 
/*     */ 
/*     */   
/*     */   static void def() {
/*  31 */     Op.updateOption(Op.DM.DmW.ordinal(), "700", Op.dm);
/*  32 */     Op.updateOption(Op.DM.DmH.ordinal(), "680", Op.dm);
/*  33 */     Op.updateOption(Op.DM.DmSize.ordinal(), "7", Op.dm);
/*  34 */     Op.updateOption(Op.DM.DmCell.ordinal(), "AAAA77", Op.dm);
/*  35 */     Op.updateOption(Op.DM.DmSolved.ordinal(), "003333", Op.dm);
/*  36 */     Op.updateOption(Op.DM.DmLine.ordinal(), "FFFFFF", Op.dm);
/*  37 */     Op.updateOption(Op.DM.DmNumber.ordinal(), "000000", Op.dm);
/*  38 */     Op.updateOption(Op.DM.DmSolvedNumber.ordinal(), "FFFFFF", Op.dm);
/*  39 */     Op.updateOption(Op.DM.DmError.ordinal(), "FF0000", Op.dm);
/*  40 */     Op.updateOption(Op.DM.DmPuz.ordinal(), "sample.domino", Op.dm);
/*  41 */     Op.updateOption(Op.DM.DmFont.ordinal(), "SansSerif", Op.dm);
/*  42 */     Op.updateOption(Op.DM.DmPuzColor.ordinal(), "false", Op.dm);
/*  43 */     Op.updateOption(Op.DM.DmSolColor.ordinal(), "false", Op.dm);
/*     */   }
/*     */   
/*  46 */   String dominoHelp = "<div>A <b>DOMINO</b> puzzle consists of a rectangular grid, with each cell of the grid occupied by a single digit number. The grid represents a set of domino pieces laid out in a rectangular array. For example, if you use a \"standard\" domino set having pieces ranging from 0-0 spots to 6-6 spots, then the rectangle will be 8 cells across and 7 cells down. The puzzles can be made in various sizes, depending on the size of the domino set you use. To solve such a puzzle, it is necessary to outline pairs of adjacent numbers in such a way that every piece of the domino set is revealed. Each puzzle has a unique solution, and can be solved without recourse to guesswork.<br/><br/</div><span class='m'>Menu Functions</span><ul><li/><span class='s'>File Menu</span><ul><li/><span>Load a Puzzle</span><br/>Use this option to choose your puzzle from the pool of DOMINO puzzles currently available on your computer.<p/><li/><span>SaveAs</span><br/>This option allows you to make an exact copy of the current puzzle using a different file name. The copy will be saved in the <b>domino</b> folder along with all of the Domino puzzles you have made. Alternatively, if you don't enter a new name for the puzzle, you can change the Puzzle Description, or any of the other descriptive items without changing the puzzle name.<p/><li/><span>Quit Construction</span><br/>Returns you to the Crossword Express opening screen.</ul><li/><span class='s'>Build Menu</span><ul><li/><span>Start a New Puzzle</span><br/>This option presents you with a dialog into which you can enter a file name for your new puzzle. You can also enter several other pieces of information such as a <b>Puzzle Title, Author</b> and <b>Copyright</b> information.<p/><li/><span>Build Options</span><br/>Use this option to access a Build Options dialog where you can set some rules to be followed by the puzzle building function as it builds your puzzle.<p/><li/><span>Start Building / Stop Building</span><br/>Construction of the puzzle will commence when you select the <b>Start Building</b> option.If puzzle building is successful you will receive a message containing the name of the puzzle file, and the location where it was saved. If it becomes necessary, you can interrupt the building process by selecting this option a second time. Note that during the construction phase, the text of this option is changed to <b>Stop Building</b></ul><li/><span class='s'>View Menu</span><ul><li/><span>Display Options</span><br/>This leads you to a dialog box in which you can change the colors of various elements within the puzzle, and control the fonts which will be used for the puzzle's text components. You can also decide if printing of the puzzle will be done in black and white or in color.</ul><li/><span class='s'>Export Menu</span><br/><ul><li/><span>Print a Domino KDP puzzle book.</span><br/>The letters KDP stand for <b>Kindle Direct Publishing</b>. This is a free publishing service operared by Amazon, in which they handle all matters related to printing, advertising and sales of books created by members of the public. A portion of the proceeds are retained by Amazon while the remainder is paid to the author. Fifteen of the Puzzles created by Crossword Express can be printed into PDF format files ready for publication by Amazon. When you select this option, you will be presented with a dialog which allows you to control the process. Please study the Help offered by this dialog before attempting to make use of it.</ul><li/><span class='s'>Tasks Menu</span><ul><li/><span>Print this Puzzle</span><br/>This will take you to a custom print screen where you can control the details involved with printing your puzzle.<p/><li/><span>Solve this Puzzle</span><br/>This will take you to a Solve screen which provides a fully interactive environment for solving the puzzle.<p/><li/><span>Delete this Puzzle</span><br/>Use this option to eliminate unwanted DOMINO puzzles from your file system.</ul><li/><span class='s'>Help Menu</span><ul><li/><span>Domino Help</span><br/>Displays the Help screen which you are now reading.</ul></ul></body>";
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
/* 111 */   String dominoOptions = "<div>Before you give the command to build a <b>Domino</b> puzzle, you might like to set some options which the program will use during the construction process.</div><br/><ul><li/><b>Domino Size: </b>Select a number in the range 3 to 9 from the drop down list, to inform the program the size of the set of dominoes which you want to be used in constructing the puzzle. For example, if you select 5, then the program will use the domino set which includes pieces ranging from 0-0 to 5-5.<p/><li/>If you want to make a number of puzzles all having the same dimensions, simply type a number into the <b>How many puzzles</b> input field. When you issue the Make command, Crossword Express will make that number of puzzles. The puzzle names will be numbers which represent a date in <b>yyyymmdd</b> format. The default value presented by Crossword Express is always the current date, but you can change this to any date that suits your needs. As the series of puzzles is created, CWE will automatically step on to the next date in the sequence, taking into account such factors as the varying number of days in the months, and of course leap years. Virtually any number of puzzles can be made in a single operation using this feature.<p/><li/><b>HOWEVER:</b> If you prefer a simpler numbering scheme for your puzzles, you can enter any number of 7 digits or less to be used for your first puzzle, and Crossword Express will number the remainder of the puzzles sequentially starting with your number.<p/><li/>If you do choose to make multiple puzzles, then by default, Crossword Express will change the difficulty of the resulting puzzles over a cycle of seven puzzles. This would be useful for a daily newspaper so that the week could start with a very easy puzzle, with quite difficult puzzles reserved for the weekend. If you don't want this feature, clearing the <b>Vary Difficulty on 7 day cycle</b> check-box will disable it.</ul></body>";
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
/*     */   DominoBuild(JFrame jf, boolean auto, int hm, int start) {
/* 135 */     Def.puzzleMode = 40;
/* 136 */     Grid.xSz = Op.getInt(Op.DM.DmSize.ordinal(), Op.dm) + 2; Grid.ySz = Op.getInt(Op.DM.DmSize.ordinal(), Op.dm) + 1;
/* 137 */     Def.building = 0;
/* 138 */     Def.dispCursor = Boolean.valueOf(false);
/* 139 */     makeGrid();
/*     */     
/* 141 */     jfDomino = new JFrame("Domino");
/* 142 */     if (Op.getInt(Op.DM.DmH.ordinal(), Op.dm) > Methods.scrH - 200) {
/* 143 */       int diff = Op.getInt(Op.DM.DmH.ordinal(), Op.dm) - Op.getInt(Op.DM.DmW.ordinal(), Op.dm);
/* 144 */       Op.setInt(Op.DM.DmH.ordinal(), Methods.scrH - 200, Op.dm);
/* 145 */       Op.setInt(Op.DM.DmW.ordinal(), Methods.scrH - 200 + diff, Op.dm);
/*     */     } 
/* 147 */     jfDomino.setSize(Op.getInt(Op.DM.DmW.ordinal(), Op.dm), Op.getInt(Op.DM.DmH.ordinal(), Op.dm));
/* 148 */     int frameX = (jf.getX() + jfDomino.getWidth() > Methods.scrW) ? (Methods.scrW - jfDomino.getWidth() - 10) : jf.getX();
/* 149 */     jfDomino.setLocation(frameX, jf.getY());
/* 150 */     jfDomino.setLayout((LayoutManager)null);
/* 151 */     jfDomino.setDefaultCloseOperation(0);
/* 152 */     jfDomino
/* 153 */       .addComponentListener(new ComponentAdapter() {
/*     */           public void componentResized(ComponentEvent ce) {
/* 155 */             int oldw = Op.getInt(Op.DM.DmW.ordinal(), Op.dm);
/* 156 */             int oldh = Op.getInt(Op.DM.DmH.ordinal(), Op.dm);
/* 157 */             Methods.frameResize(DominoBuild.jfDomino, oldw, oldh, 500, 480);
/* 158 */             Op.setInt(Op.DM.DmW.ordinal(), DominoBuild.jfDomino.getWidth(), Op.dm);
/* 159 */             Op.setInt(Op.DM.DmH.ordinal(), DominoBuild.jfDomino.getHeight(), Op.dm);
/* 160 */             DominoBuild.restoreFrame();
/*     */           }
/*     */         });
/*     */     
/* 164 */     jfDomino
/* 165 */       .addWindowListener(new WindowAdapter() {
/*     */           public void windowClosing(WindowEvent we) {
/* 167 */             if (Def.building == 1 || Def.selecting)
/* 168 */               return;  Op.saveOptions("domino.opt", Op.dm);
/* 169 */             CrosswordExpress.transfer(1, DominoBuild.jfDomino);
/*     */           }
/*     */         });
/*     */     
/* 173 */     Methods.closeHelp();
/*     */ 
/*     */     
/* 176 */     Runnable buildThread = () -> {
/*     */         if (this.howMany == 1) {
/*     */           buildDomino();
/*     */         } else {
/*     */           multiBuild();
/*     */           
/*     */           if (this.sixpack) {
/*     */             Sixpack.trigger();
/*     */             jfDomino.dispose();
/*     */             Def.building = 0;
/*     */             return;
/*     */           } 
/*     */         } 
/*     */         this.buildMenuItem.setText("Start Building");
/*     */         if (Def.building == 2) {
/*     */           Def.building = 0;
/*     */           Methods.interrupted(jfDomino);
/*     */           makeGrid();
/*     */           restoreFrame();
/*     */           return;
/*     */         } 
/*     */         Methods.havePuzzle = true;
/*     */         restoreFrame();
/*     */         Methods.puzzleSaved(jfDomino, "domino", Op.dm[Op.DM.DmPuz.ordinal()]);
/*     */         Def.building = 0;
/*     */       };
/* 202 */     jl1 = new JLabel(); jfDomino.add(jl1);
/* 203 */     jl2 = new JLabel(); jfDomino.add(jl2);
/*     */ 
/*     */     
/* 206 */     menuBar = new JMenuBar();
/* 207 */     menuBar.setBackground(Def.COLOR_MENUBAR);
/* 208 */     jfDomino.setJMenuBar(menuBar);
/*     */     
/* 210 */     this.menu = new JMenu("File");
/* 211 */     menuBar.add(this.menu);
/* 212 */     this.menuItem = new JMenuItem("Load a Puzzle");
/* 213 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(76, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 214 */     this.menu.add(this.menuItem);
/* 215 */     this.menuItem
/* 216 */       .addActionListener(ae -> {
/*     */           if (Def.building == 1)
/*     */             return; 
/*     */           pp.invalidate();
/*     */           pp.repaint();
/*     */           new Select(jfDomino, "domino", "domino", Op.dm, Op.DM.DmPuz.ordinal(), false);
/*     */         });
/* 223 */     this.menuItem = new JMenuItem("SaveAs");
/* 224 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(65, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 225 */     this.menu.add(this.menuItem);
/* 226 */     this.menuItem
/* 227 */       .addActionListener(ae -> {
/*     */           if (Def.building == 1) {
/*     */             return;
/*     */           }
/*     */           Methods.puzzleDescriptionDialog(jfDomino, Op.dm[Op.DM.DmPuz.ordinal()].substring(0, Op.dm[Op.DM.DmPuz.ordinal()].indexOf(".domino")), "domino", ".domino");
/*     */           if (Methods.clickedOK) {
/*     */             saveDomino(Op.dm[Op.DM.DmPuz.ordinal()] = Methods.theFileName);
/*     */             restoreFrame();
/*     */             Methods.puzzleSaved(jfDomino, "domino", Op.dm[Op.DM.DmPuz.ordinal()]);
/*     */           } 
/*     */         });
/* 238 */     this.menuItem = new JMenuItem("Quit Construction");
/* 239 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(81, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 240 */     this.menu.add(this.menuItem);
/* 241 */     this.menuItem
/* 242 */       .addActionListener(ae -> {
/*     */           if (Def.building == 1) {
/*     */             return;
/*     */           }
/*     */           
/*     */           Op.saveOptions("domino.opt", Op.dm);
/*     */           CrosswordExpress.transfer(1, jfDomino);
/*     */         });
/* 250 */     this.menu = new JMenu("Build");
/* 251 */     menuBar.add(this.menu);
/* 252 */     this.menuItem = new JMenuItem("Start a new Puzzle");
/* 253 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(78, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 254 */     this.menu.add(this.menuItem);
/* 255 */     this.menuItem
/* 256 */       .addActionListener(ae -> {
/*     */           if (Def.building == 1) {
/*     */             return;
/*     */           }
/*     */           Methods.puzzleDescriptionDialog(jfDomino, Op.dm[Op.DM.DmPuz.ordinal()].substring(0, Op.dm[Op.DM.DmPuz.ordinal()].indexOf(".domino")), "domino", ".domino");
/*     */           if (Methods.clickedOK) {
/*     */             Op.dm[Op.DM.DmPuz.ordinal()] = Methods.theFileName;
/*     */             makeGrid();
/*     */           } 
/*     */           restoreFrame();
/*     */         });
/* 267 */     this.menuItem = new JMenuItem("Build Options");
/* 268 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(79, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 269 */     this.menu.add(this.menuItem);
/* 270 */     this.menuItem
/* 271 */       .addActionListener(ae -> {
/*     */           if (Def.building == 1)
/*     */             return; 
/*     */           dominoOptions();
/*     */           if (Methods.clickedOK) {
/*     */             makeGrid();
/*     */             if (this.howMany > 1)
/*     */               Op.dm[Op.DM.DmPuz.ordinal()] = "" + this.startPuz + ".domino"; 
/*     */           } 
/*     */           restoreFrame();
/*     */         });
/* 282 */     this.buildMenuItem = new JMenuItem("Start Building");
/* 283 */     this.buildMenuItem.setAccelerator(KeyStroke.getKeyStroke(66, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 284 */     this.menu.add(this.buildMenuItem);
/* 285 */     this.buildMenuItem
/* 286 */       .addActionListener(ae -> {
/*     */           if (Op.dm[Op.DM.DmPuz.ordinal()].length() == 0 && this.howMany == 1) {
/*     */             Methods.noName(jfDomino);
/*     */             
/*     */             return;
/*     */           } 
/*     */           
/*     */           if (Def.building == 0) {
/*     */             this.thread = new Thread(paramRunnable);
/*     */             
/*     */             this.thread.start();
/*     */             Def.building = 1;
/*     */             this.buildMenuItem.setText("Stop Building");
/*     */           } else {
/*     */             Def.building = 2;
/*     */           } 
/*     */         });
/* 303 */     this.menu = new JMenu("View");
/* 304 */     menuBar.add(this.menu);
/* 305 */     this.menuItem = new JMenuItem("Display Options");
/* 306 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(89, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 307 */     this.menu.add(this.menuItem);
/* 308 */     this.menuItem
/* 309 */       .addActionListener(ae -> {
/*     */           if (Def.building == 1) {
/*     */             return;
/*     */           }
/*     */           printOptions(jfDomino, "Display Options");
/*     */           restoreFrame();
/*     */         });
/* 316 */     this.menu = new JMenu("Export");
/* 317 */     menuBar.add(this.menu);
/* 318 */     this.menuItem = new JMenuItem("Print a Domino KDP puzzle book.");
/* 319 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(75, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 320 */     this.menu.add(this.menuItem);
/* 321 */     this.menuItem
/* 322 */       .addActionListener(ae -> {
/*     */           if (Def.building == 1) {
/*     */             return;
/*     */           }
/*     */           
/*     */           Methods.printKdpDialog(jfDomino, 40, 6);
/*     */         });
/* 329 */     this.menu = new JMenu("Tasks");
/* 330 */     menuBar.add(this.menu);
/* 331 */     this.menuItem = new JMenuItem("Print this Puzzle");
/* 332 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(80, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 333 */     this.menu.add(this.menuItem);
/* 334 */     this.menuItem
/* 335 */       .addActionListener(ae -> {
/*     */           if (Def.building == 1) {
/*     */             return;
/*     */           }
/*     */           CrosswordExpress.toPrint(jfDomino, Op.dm[Op.DM.DmPuz.ordinal()]);
/*     */         });
/* 341 */     this.menuItem = new JMenuItem("Solve this Puzzle");
/* 342 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(86, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 343 */     this.menu.add(this.menuItem);
/* 344 */     this.menuItem
/* 345 */       .addActionListener(ae -> {
/*     */           if (Def.building == 1)
/*     */             return; 
/*     */           if (Methods.havePuzzle) {
/*     */             CrosswordExpress.transfer(41, jfDomino);
/*     */           } else {
/*     */             Methods.noPuzzle(jfDomino, "Solve");
/*     */           } 
/*     */         });
/* 354 */     this.menuItem = new JMenuItem("Delete this Puzzle");
/* 355 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(68, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 356 */     this.menu.add(this.menuItem);
/* 357 */     this.menuItem
/* 358 */       .addActionListener(ae -> {
/*     */           if (Def.building == 1) {
/*     */             return;
/*     */           }
/*     */           
/*     */           if (Methods.deleteAPuzzle(jfDomino, Op.dm[Op.DM.DmPuz.ordinal()], "domino", pp)) {
/*     */             makeGrid();
/*     */             loadDomino(Op.dm[Op.DM.DmPuz.ordinal()]);
/*     */             Methods.havePuzzle = true;
/*     */             restoreFrame();
/*     */           } 
/*     */         });
/* 370 */     this.menu = new JMenu("Help");
/* 371 */     menuBar.add(this.menu);
/* 372 */     this.menuItem = new JMenuItem("Domino Help");
/* 373 */     this.menu.add(this.menuItem);
/* 374 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(72, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 375 */     this.menuItem
/* 376 */       .addActionListener(ae -> Methods.cweHelp(jfDomino, null, "Building Domino Puzzles", this.dominoHelp));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 382 */     loadDomino(Op.dm[Op.DM.DmPuz.ordinal()]);
/* 383 */     pp = new DominoPP(0, 37);
/* 384 */     jfDomino.add(pp);
/*     */     
/* 386 */     pp
/* 387 */       .addMouseMotionListener(new MouseAdapter() {
/*     */           public void mouseMoved(MouseEvent e) {
/* 389 */             if (Def.isMac) {
/* 390 */               DominoBuild.jfDomino.setResizable((DominoBuild.jfDomino.getWidth() - e.getX() < 15 && DominoBuild.jfDomino
/* 391 */                   .getHeight() - e.getY() < 95));
/*     */             }
/*     */           }
/*     */         });
/* 395 */     restoreFrame();
/*     */ 
/*     */     
/* 398 */     ActionListener timerAL = ae -> {
/*     */         this.myTimer.stop();
/*     */         this.thread = new Thread(paramRunnable);
/*     */         this.thread.start();
/*     */         Def.building = 1;
/*     */       };
/* 404 */     this.myTimer = new Timer(1000, timerAL);
/*     */     
/* 406 */     if (auto) {
/* 407 */       this.sixpack = true;
/* 408 */       this.howMany = hm; this.startPuz = start;
/* 409 */       this.myTimer.start();
/*     */     } 
/*     */   }
/*     */   
/*     */   static void restoreFrame() {
/* 414 */     jfDomino.setVisible(true);
/* 415 */     Insets insets = jfDomino.getInsets();
/* 416 */     panelW = jfDomino.getWidth() - insets.left + insets.right;
/* 417 */     panelH = jfDomino.getHeight() - insets.top + insets.bottom + 37 + menuBar.getHeight();
/* 418 */     pp.setSize(panelW, panelH);
/* 419 */     jfDomino.requestFocusInWindow();
/* 420 */     pp.repaint();
/* 421 */     Methods.infoPanel(jl1, jl2, "Build Domino", "Puzzle : " + Op.dm[Op.DM.DmPuz.ordinal()], panelW);
/*     */   }
/*     */   
/*     */   static void setSizesAndOffsets(int x, int y, int width, int height, int inset) {
/* 425 */     int i = (width - inset) / Grid.xSz;
/* 426 */     int j = (height - inset) / Grid.ySz;
/* 427 */     Grid.xCell = Grid.yCell = (i < j) ? i : j;
/* 428 */     Grid.xOrg = x + (width - Grid.xSz * Grid.xCell) / 2;
/* 429 */     Grid.yOrg = y + (height - Grid.ySz * Grid.yCell) / 2;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void dominoOptions() {
/* 435 */     JDialog jdlgDomino = new JDialog(jfDomino, "Domino Options", true);
/* 436 */     jdlgDomino.setSize(270, 258);
/* 437 */     jdlgDomino.setResizable(false);
/* 438 */     jdlgDomino.setLayout((LayoutManager)null);
/* 439 */     jdlgDomino.setLocation(jfDomino.getX(), jfDomino.getY());
/*     */     
/* 441 */     jdlgDomino
/* 442 */       .addWindowListener(new WindowAdapter() {
/*     */           public void windowClosing(WindowEvent we) {
/* 444 */             Methods.closeHelp();
/*     */           }
/*     */         });
/*     */     
/* 448 */     Methods.closeHelp();
/*     */     
/* 450 */     JLabel jlSize = new JLabel("Domino Size:");
/* 451 */     jlSize.setForeground(Def.COLOR_LABEL);
/* 452 */     jlSize.setSize(100, 20);
/* 453 */     jlSize.setLocation(30, 8);
/* 454 */     jlSize.setHorizontalAlignment(4);
/* 455 */     jdlgDomino.add(jlSize);
/*     */     
/* 457 */     JComboBox<Integer> jcbbSize = new JComboBox<>();
/* 458 */     for (int i = 3; i <= 9; i++)
/* 459 */       jcbbSize.addItem(Integer.valueOf(i)); 
/* 460 */     jcbbSize.setSize(80, 23);
/* 461 */     jcbbSize.setLocation(140, 8);
/* 462 */     jdlgDomino.add(jcbbSize);
/* 463 */     jcbbSize.setBackground(Def.COLOR_BUTTONBG);
/* 464 */     jcbbSize.setSelectedIndex(Op.getInt(Op.DM.DmSize.ordinal(), Op.dm) - 3);
/*     */     
/* 466 */     HowManyPuzzles hmp = new HowManyPuzzles(jdlgDomino, 10, 40, this.howMany, this.startPuz, true);
/*     */     
/* 468 */     JButton jbOK = Methods.cweButton("OK", 10, 153, 80, 26, null);
/* 469 */     jbOK.addActionListener(e -> {
/*     */           int i = paramJComboBox.getSelectedIndex() + 3;
/*     */           Op.setInt(Op.DM.DmSize.ordinal(), i, Op.dm);
/*     */           jfDomino.setTitle("Domino Construction");
/*     */           this.howMany = Integer.parseInt(paramHowManyPuzzles.jtfHowMany.getText());
/*     */           this.startPuz = Integer.parseInt(paramHowManyPuzzles.jtfStartPuz.getText());
/*     */           Op.setBool(Op.SX.VaryDiff.ordinal(), Boolean.valueOf(paramHowManyPuzzles.jcbVaryDiff.isSelected()), Op.sx);
/*     */           Methods.clickedOK = true;
/*     */           paramJDialog.dispose();
/*     */           Methods.closeHelp();
/*     */         });
/* 480 */     jdlgDomino.add(jbOK);
/*     */     
/* 482 */     JButton jbCancel = Methods.cweButton("Cancel", 10, 187, 80, 26, null);
/* 483 */     jbCancel.addActionListener(e -> {
/*     */           Methods.clickedOK = false;
/*     */           paramJDialog.dispose();
/*     */           Methods.closeHelp();
/*     */         });
/* 488 */     jdlgDomino.add(jbCancel);
/*     */     
/* 490 */     JButton jbHelp = Methods.cweButton("<html><font size=6 color=BB0000 face=Serif>Help ", 100, 153, 150, 60, new ImageIcon("graphics/help.png"));
/* 491 */     jbHelp.addActionListener(e -> Methods.cweHelp(null, paramJDialog, "Domino Options", this.dominoOptions));
/*     */     
/* 493 */     jdlgDomino.add(jbHelp);
/*     */     
/* 495 */     jdlgDomino.getRootPane().setDefaultButton(jbOK);
/* 496 */     Methods.setDialogSize(jdlgDomino, 260, 223);
/*     */   }
/*     */   
/*     */   static void printOptions(JFrame jf, String type) {
/* 500 */     String[] colorLabel = { "Cell Color", "Solved Cell", "Line Color", "Number Color", "Solved Number", "Error Color" };
/* 501 */     int[] colorInt = { Op.DM.DmCell.ordinal(), Op.DM.DmSolved.ordinal(), Op.DM.DmLine.ordinal(), Op.DM.DmNumber.ordinal(), Op.DM.DmSolvedNumber.ordinal(), Op.DM.DmError.ordinal() };
/* 502 */     String[] fontLabel = { "Puzzle Font" };
/* 503 */     int[] fontInt = { Op.DM.DmFont.ordinal() };
/* 504 */     String[] checkLabel = { "PPrint Puzzle with color.", "SPrint Solution with color." };
/* 505 */     int[] checkInt = { Op.DM.DmPuzColor.ordinal(), Op.DM.DmSolColor.ordinal() };
/* 506 */     Methods.stdPrintOptions(jf, "Domino " + type, Op.dm, colorLabel, colorInt, fontLabel, fontInt, checkLabel, checkInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void saveDomino(String dominoName) {
/*     */     try {
/* 516 */       DataOutputStream dataOut = new DataOutputStream(new FileOutputStream("domino/" + dominoName));
/* 517 */       dataOut.writeInt(Grid.xSz);
/* 518 */       dataOut.writeInt(Grid.ySz);
/* 519 */       dataOut.writeByte(Methods.noReveal);
/* 520 */       dataOut.writeByte(Methods.noErrors);
/* 521 */       for (int i = 0; i < 54; i++)
/* 522 */         dataOut.writeByte(0); 
/* 523 */       for (int j = 0; j < Grid.ySz; j++) {
/* 524 */         for (int k = 0; k < Grid.xSz; k++) {
/* 525 */           dataOut.writeInt(Grid.mode[k][j]);
/* 526 */           dataOut.writeInt(Grid.sol[k][j]);
/* 527 */           dataOut.writeInt(Grid.letter[k][j]);
/*     */         } 
/* 529 */       }  dataOut.writeUTF(Methods.puzzleTitle);
/* 530 */       dataOut.writeUTF(Methods.author);
/* 531 */       dataOut.writeUTF(Methods.copyright);
/* 532 */       dataOut.writeUTF(Methods.puzzleNumber);
/* 533 */       dataOut.writeUTF(Methods.puzzleNotes);
/* 534 */       dataOut.close();
/*     */     }
/* 536 */     catch (IOException exc) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void loadDomino(String dominoName) {
/*     */     try {
/* 545 */       File fl = new File("domino/" + dominoName);
/* 546 */       if (!fl.exists()) {
/* 547 */         fl = new File("domino/");
/* 548 */         String[] s = fl.list(); int k;
/* 549 */         for (k = 0; k < s.length && (
/* 550 */           s[k].lastIndexOf(".domino") == -1 || s[k].charAt(0) == '.'); k++);
/*     */         
/* 552 */         dominoName = s[k];
/* 553 */         Op.dm[Op.DM.DmPuz.ordinal()] = dominoName;
/*     */       } 
/*     */       
/* 556 */       DataInputStream dataIn = new DataInputStream(new FileInputStream("domino/" + dominoName));
/* 557 */       Grid.xSz = dataIn.readInt();
/* 558 */       Grid.ySz = dataIn.readInt();
/* 559 */       Methods.noReveal = dataIn.readByte();
/* 560 */       Methods.noErrors = dataIn.readByte(); int i;
/* 561 */       for (i = 0; i < 54; i++)
/* 562 */         dataIn.readByte(); 
/* 563 */       for (int j = 0; j < Grid.ySz; j++) {
/* 564 */         for (i = 0; i < Grid.xSz; i++) {
/* 565 */           Grid.mode[i][j] = dataIn.readInt();
/* 566 */           Grid.sol[i][j] = dataIn.readInt();
/* 567 */           Grid.letter[i][j] = dataIn.readInt();
/*     */         } 
/* 569 */       }  Methods.puzzleTitle = dataIn.readUTF();
/* 570 */       Methods.author = dataIn.readUTF();
/* 571 */       Methods.copyright = dataIn.readUTF();
/* 572 */       Methods.puzzleNumber = dataIn.readUTF();
/* 573 */       Methods.puzzleNotes = dataIn.readUTF();
/* 574 */       dataIn.close();
/*     */     }
/* 576 */     catch (IOException exc) {
/*     */       return;
/* 578 */     }  Methods.havePuzzle = true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static void drawDomino(Graphics2D g2, int[][] puzzleArray) {
/* 584 */     Stroke normalStroke = new BasicStroke(Grid.xCell / 25.0F, 2, 2);
/* 585 */     g2.setStroke(normalStroke);
/*     */     
/* 587 */     RenderingHints rh = g2.getRenderingHints();
/* 588 */     rh.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
/* 589 */     g2.setRenderingHints(rh);
/*     */ 
/*     */     
/* 592 */     g2.setColor(new Color(Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.DM.DmSolved.ordinal(), Op.dm) : 0)); int j;
/* 593 */     for (j = 0; j < Grid.ySz; j++) {
/* 594 */       for (int i = 0; i < Grid.xSz; i++) {
/* 595 */         g2.fillRect(Grid.xOrg + i * Grid.xCell, Grid.yOrg + j * Grid.yCell, Grid.xCell, Grid.yCell);
/*     */       }
/*     */     } 
/* 598 */     g2.setColor(new Color(Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.DM.DmLine.ordinal(), Op.dm) : 16777215));
/* 599 */     for (j = 0; j < Grid.ySz; j++) {
/* 600 */       for (int i = 0; i < Grid.xSz; i++) {
/* 601 */         int x = Grid.xOrg + i * Grid.xCell;
/* 602 */         int y = Grid.yOrg + j * Grid.yCell;
/* 603 */         for (int c = 1; c < 16; c *= 2) {
/* 604 */           if ((Grid.mode[i][j] & c) == 0)
/* 605 */             switch (c) { case 1:
/* 606 */                 g2.drawLine(x, y, x + Grid.xCell, y); break;
/* 607 */               case 2: g2.drawLine(x + Grid.xCell, y, x + Grid.xCell, y + Grid.yCell); break;
/* 608 */               case 4: g2.drawLine(x, y + Grid.yCell, x + Grid.xCell, y + Grid.yCell); break;
/* 609 */               case 8: g2.drawLine(x, y + Grid.yCell, x, y);
/*     */                 break; }
/*     */              
/*     */         } 
/*     */       } 
/*     */     } 
/* 615 */     g2.setColor(new Color(Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.DM.DmSolvedNumber.ordinal(), Op.dm) : 16777215));
/* 616 */     g2.setFont(new Font(Op.dm[Op.DM.DmFont.ordinal()], 0, 8 * Grid.yCell / 10));
/* 617 */     FontMetrics fm = g2.getFontMetrics();
/* 618 */     for (j = 0; j < Grid.ySz; j++) {
/* 619 */       for (int i = 0; i < Grid.xSz; i++) {
/* 620 */         char ch = (char)puzzleArray[i][j];
/* 621 */         if (Character.isDigit(ch)) {
/* 622 */           int w = fm.stringWidth("" + ch);
/* 623 */           g2.drawString("" + ch, Grid.xOrg + i * Grid.xCell + (Grid.xCell - w) / 2, Grid.yOrg + j * Grid.yCell + (Grid.yCell + fm.getAscent() - fm.getDescent()) / 2);
/*     */         } 
/*     */       } 
/* 626 */     }  g2.setStroke(new BasicStroke(1.0F));
/*     */   }
/*     */   
/*     */   static void printPuz(Graphics2D g2, int left, int top, int width, int height) {
/* 630 */     loadDomino(Op.dm[Op.DM.DmPuz.ordinal()]);
/* 631 */     setSizesAndOffsets(left, top, width, height, 0);
/* 632 */     Methods.clearGrid(Grid.mode);
/* 633 */     Def.dispWithColor = Op.getBool(Op.DM.DmPuzColor.ordinal(), Op.dm);
/* 634 */     DominoSolve.drawDomino(g2, Grid.letter);
/* 635 */     Def.dispWithColor = Boolean.valueOf(true);
/*     */   }
/*     */   
/*     */   static void printSol(Graphics2D g2, int left, int top, int width, int height, String solutionPuzzle) {
/* 639 */     loadDomino(solutionPuzzle);
/* 640 */     setSizesAndOffsets(left, top, width, height, 0);
/* 641 */     Def.dispWithColor = Op.getBool(Op.DM.DmSolColor.ordinal(), Op.dm);
/* 642 */     drawDomino(g2, Grid.letter);
/* 643 */     Def.dispWithColor = Boolean.valueOf(true);
/* 644 */     loadDomino(Op.dm[Op.DM.DmPuz.ordinal()]);
/*     */   }
/*     */   
/*     */   static void printSolTitle(Graphics2D g2, int left, int top, int width, int height, String solutionPuzzle) {
/* 648 */     loadDomino(solutionPuzzle);
/* 649 */     Print.outputTextItem(g2, left, top, width, height, "SansSerif", 0, Methods.puzzleTitle);
/* 650 */     loadDomino(Op.dm[Op.DM.DmPuz.ordinal()]);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static void printSixpackPuz(Graphics2D g2, int left, int top, int dim, int gap, String puzName) {
/* 656 */     FontMetrics fm = g2.getFontMetrics();
/*     */     
/* 658 */     String st = Op.sx[Op.SX.SxDm.ordinal()];
/* 659 */     if (st.length() < 3) st = "DOMINO"; 
/* 660 */     int w = fm.stringWidth(st);
/* 661 */     g2.drawString(st, left + (dim - w) / 2, top - gap / 3);
/* 662 */     DominoSolve.loadDomino(puzName + ".domino");
/* 663 */     setSizesAndOffsets(left, top, dim, dim, 0);
/* 664 */     Methods.clearGrid(Grid.mode);
/* 665 */     DominoSolve.drawDomino(g2, Grid.letter);
/* 666 */     if (Op.sx[Op.SX.SxRuleLang.ordinal()].equals("English")) {
/* 667 */       st = rules;
/*     */     } else {
/* 669 */       st = Op.dm[Op.DM.DmRule1.ordinal() + Op.getInt(Op.SX.SxRuleLangIndex.ordinal(), Op.sx) - 1];
/* 670 */     }  String n1 = "" + Op.getInt(Op.DM.DmSize.ordinal(), Op.dm) + "-" + Op.getInt(Op.DM.DmSize.ordinal(), Op.dm);
/* 671 */     st = st.replace("<n1>", n1);
/* 672 */     if (Op.getBool(Op.SX.SxInstructions.ordinal(), Op.sx).booleanValue()) {
/* 673 */       Methods.renderText(g2, left, top + dim + dim / 50, dim, dim / 4, "SansSerif", 1, st, 3, 4, true, 0, 0);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static void printSixpackSol(Graphics2D g2, int left, int top, int dim, int gap, String solName) {
/* 679 */     FontMetrics fm = g2.getFontMetrics();
/*     */     
/* 681 */     String st = Op.sx[Op.SX.SxDm.ordinal()];
/* 682 */     if (st.length() < 3) st = "DOMINO"; 
/* 683 */     int w = fm.stringWidth(st);
/* 684 */     g2.drawString(st, left + (dim - w) / 2, top - gap / 3);
/* 685 */     loadDomino(solName + ".domino");
/* 686 */     setSizesAndOffsets(left, top, dim, dim, 0);
/* 687 */     drawDomino(g2, Grid.letter);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static void printKDPPuz(Graphics2D g2, int left, int top, int dim, int gap, String puzName) {
/* 693 */     FontMetrics fm = g2.getFontMetrics();
/*     */     
/* 695 */     String st = puzName;
/* 696 */     int w = fm.stringWidth(st);
/* 697 */     g2.drawString(st, left + (dim - w) / 2, top - gap / 3);
/* 698 */     DominoSolve.loadDomino(puzName + ".domino");
/* 699 */     setSizesAndOffsets(left, top, dim, dim, 0);
/* 700 */     Methods.clearGrid(Grid.mode);
/* 701 */     DominoSolve.drawDomino(g2, Grid.letter);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static void printKDPSol(Graphics2D g2, int left, int top, int dim, int gap, String solName) {
/* 707 */     FontMetrics fm = g2.getFontMetrics();
/*     */     
/* 709 */     String st = solName;
/* 710 */     int w = fm.stringWidth(st);
/* 711 */     g2.drawString(st, left + (dim - w) / 2, top - gap / 3);
/* 712 */     loadDomino(solName + ".domino");
/* 713 */     setSizesAndOffsets(left, top, dim, dim, 0);
/* 714 */     drawDomino(g2, Grid.letter);
/*     */   }
/*     */   
/*     */   static void clearSol() {
/* 718 */     for (int y = 0; y < Grid.ySz; y++) {
/* 719 */       for (int x = 0; x < Grid.xSz; x++)
/* 720 */         Grid.sol[x][y] = 0; 
/*     */     } 
/*     */   }
/*     */   private static void makeGrid() {
/* 724 */     Methods.havePuzzle = false;
/* 725 */     Grid.xSz = Op.getInt(Op.DM.DmSize.ordinal(), Op.dm) + 2; Grid.ySz = Op.getInt(Op.DM.DmSize.ordinal(), Op.dm) + 1;
/* 726 */     Grid.clearGrid();
/*     */   }
/*     */ 
/*     */   
/*     */   boolean determineLayout() {
/* 731 */     Random r = new Random();
/*     */     
/* 733 */     for (int i = 0; i < Grid.xSz * Grid.ySz; i++) {
/* 734 */       int x = i % Grid.xSz;
/* 735 */       int y = i / Grid.xSz;
/* 736 */       int c = 0;
/* 737 */       if (Grid.mode[x][y] == 0) {
/* 738 */         if (x + 1 < Grid.xSz && Grid.mode[x + 1][y] == 0) c++; 
/* 739 */         if (y + 1 < Grid.ySz && Grid.mode[x][y + 1] == 0) c += 2; 
/* 740 */         switch (c) { case 0:
/* 741 */             return false;
/* 742 */           case 1: Grid.mode[x][y] = 2; Grid.mode[x + 1][y] = 8; break;
/* 743 */           case 2: Grid.mode[x][y] = 4; Grid.mode[x][y + 1] = 1; break;
/* 744 */           case 3: if (r.nextInt(2) == 0) {
/* 745 */               Grid.mode[x][y] = 2;
/* 746 */               Grid.mode[x + 1][y] = 8;
/*     */               break;
/*     */             } 
/* 749 */             Grid.mode[x][y] = 4;
/* 750 */             Grid.mode[x][y + 1] = 1;
/*     */             break; }
/*     */       
/*     */       } 
/*     */     } 
/* 755 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   void insertDominoes() {
/* 760 */     int v = Grid.xSz * Grid.ySz / 2;
/* 761 */     int[] dominoList = new int[v];
/* 762 */     Random r = new Random();
/*     */     
/*     */     int i, x;
/* 765 */     for (i = x = 0; x < Grid.xSz; x++) {
/* 766 */       for (int j = x; j < Grid.ySz; j++) {
/* 767 */         dominoList[i++] = 10 * x + j;
/*     */       }
/*     */     } 
/* 770 */     for (i = 0; i < v; i++) {
/* 771 */       x = r.nextInt(v);
/* 772 */       int j = dominoList[i];
/* 773 */       dominoList[i] = dominoList[x];
/* 774 */       dominoList[x] = j;
/*     */     } 
/*     */ 
/*     */     
/* 778 */     for (int y = 0; y < Grid.ySz; y++) {
/* 779 */       for (x = 0; x < Grid.xSz; x++) {
/* 780 */         if (Grid.letter[x][y] == 0) {
/* 781 */           if (Grid.mode[x][y] == 2) {
/* 782 */             Grid.letter[x + 1][y] = 48 + dominoList[i] / 10;
/*     */           } else {
/* 784 */             Grid.letter[x][y + 1] = 48 + dominoList[i] / 10;
/* 785 */           }  Grid.letter[x][y] = 48 + dominoList[i++] % 10;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   } void busy(int a, int b) {
/* 790 */     this.busyBit[a - 48][b - 48] = 1;
/* 791 */     this.busyBit[b - 48][a - 48] = 1;
/*     */   }
/*     */   
/*     */   boolean free(int a, int b) {
/* 795 */     return (this.busyBit[a - 48][b - 48] == 0);
/*     */   }
/*     */   
/*     */   boolean solveDomino() {
/* 799 */     int minorCount, memX = 0, memY = 0, memDir = 0;
/*     */     
/*     */     int y;
/* 802 */     for (y = 0; y < Grid.ySz; y++) {
/* 803 */       for (int x = 0; x < Grid.xSz; x++) {
/* 804 */         Grid.mode[x][y] = 0;
/*     */       }
/*     */     } 
/* 807 */     for (y = 0; y < 10; y++) {
/* 808 */       for (int x = 0; x < 10; x++)
/* 809 */         this.busyBit[x][y] = 0; 
/*     */     } 
/* 811 */     int masterCount = 0; do {
/*     */       int a;
/* 813 */       for (minorCount = 0, a = 48; a < 57; a++) {
/* 814 */         for (int b = a; b < 57; b++) {
/* 815 */           if (free(a, b)) {
/* 816 */             int count; for (count = y = 0; y < Grid.ySz; y++) {
/* 817 */               for (int x = 0; x < Grid.xSz - 1; x++) {
/* 818 */                 if (Grid.mode[x][y] == 0 && Grid.mode[x + 1][y] == 0 && ((Grid.letter[x][y] == a && Grid.letter[x + 1][y] == b) || (Grid.letter[x][y] == b && Grid.letter[x + 1][y] == a)))
/*     */                 
/*     */                 { 
/* 821 */                   count++;
/* 822 */                   memX = x; memY = y; memDir = 0; } 
/*     */               } 
/* 824 */             }  for (y = 0; y < Grid.ySz - 1; y++) {
/* 825 */               for (int x = 0; x < Grid.xSz; x++) {
/* 826 */                 if (Grid.mode[x][y] == 0 && Grid.mode[x][y + 1] == 0 && ((Grid.letter[x][y] == a && Grid.letter[x][y + 1] == b) || (Grid.letter[x][y] == b && Grid.letter[x][y + 1] == a))) {
/*     */ 
/*     */                   
/* 829 */                   count++;
/* 830 */                   memX = x; memY = y; memDir = 1;
/*     */                 } 
/*     */               } 
/* 833 */             }  if (count == 1) {
/* 834 */               if (memDir == 0) {
/* 835 */                 Grid.mode[memX][memY] = 2;
/* 836 */                 Grid.mode[memX + 1][memY] = 8;
/*     */               } else {
/*     */                 
/* 839 */                 Grid.mode[memX][memY] = 4;
/* 840 */                 Grid.mode[memX][memY + 1] = 1;
/*     */               } 
/* 842 */               busy(a, b);
/* 843 */               minorCount++;
/* 844 */               masterCount++;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/* 849 */       for (y = 0; y < Grid.ySz; y++) {
/* 850 */         for (int x = 0; x < Grid.xSz - 1; x++) {
/* 851 */           if (Grid.mode[x][y] == 0)
/* 852 */           { int count = 0;
/* 853 */             if (y > 0 && Grid.mode[x][y - 1] == 0) { count++; memDir = 0; }
/* 854 */              if (x < Grid.xSz - 1 && Grid.mode[x + 1][y] == 0) { count++; memDir = 1; }
/* 855 */              if (y < Grid.ySz - 1 && Grid.mode[x][y + 1] == 0) { count++; memDir = 2; }
/* 856 */              if (x > 0 && Grid.mode[x - 1][y] == 0) { count++; memDir = 3; }
/* 857 */              if (count == 1)
/* 858 */             { switch (memDir) { case 0:
/* 859 */                   Grid.mode[x][y] = 1; Grid.mode[x][y - 1] = 4; busy(Grid.letter[x][y], Grid.letter[x][y - 1]); break;
/* 860 */                 case 1: Grid.mode[x][y] = 2; Grid.mode[x + 1][y] = 8; busy(Grid.letter[x][y], Grid.letter[x + 1][y]); break;
/* 861 */                 case 2: Grid.mode[x][y] = 4; Grid.mode[x][y + 1] = 1; busy(Grid.letter[x][y], Grid.letter[x][y + 1]); break;
/* 862 */                 case 3: Grid.mode[x][y] = 8; Grid.mode[x - 1][y] = 2; busy(Grid.letter[x][y], Grid.letter[x - 1][y]); break; }
/*     */               
/* 864 */               minorCount++;
/* 865 */               masterCount++; }  } 
/*     */         } 
/*     */       } 
/* 868 */     } while (minorCount != 0);
/*     */     
/* 870 */     return (masterCount == (Op.getInt(Op.DM.DmSize.ordinal(), Op.dm) + 2) * (Op.getInt(Op.DM.DmSize.ordinal(), Op.dm) + 1) / 2);
/*     */   }
/*     */   
/*     */   private void multiBuild() {
/* 874 */     String title = Methods.puzzleTitle;
/* 875 */     int[] sizeDef = { 3, 4, 5, 5, 6, 6, 7 };
/* 876 */     int saveDominoSize = Op.getInt(Op.DM.DmSize.ordinal(), Op.dm);
/*     */     
/* 878 */     SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
/* 879 */     Calendar c = Calendar.getInstance();
/*     */     
/* 881 */     for (this.hmCount = 1; this.hmCount <= this.howMany; this.hmCount++) {
/* 882 */       if (this.startPuz > 9999999) { try {
/* 883 */           c.setTime(sdf.parse("" + this.startPuz));
/* 884 */         } catch (ParseException ex) {}
/* 885 */         this.startPuz = Integer.parseInt(sdf.format(c.getTime())); }
/*     */ 
/*     */       
/* 888 */       Methods.puzzleTitle = "DOMINO Puzzle : " + this.startPuz;
/* 889 */       if (Op.getBool(Op.SX.VaryDiff.ordinal(), Op.sx).booleanValue()) {
/* 890 */         Op.setInt(Op.DM.DmSize.ordinal(), sizeDef[(this.startPuz - 1) % 7], Op.dm);
/*     */       }
/* 892 */       Methods.buildProgress(jfDomino, Op.dm[Op.DM.DmPuz
/* 893 */             .ordinal()] = "" + this.startPuz + ".domino");
/* 894 */       buildDomino();
/* 895 */       restoreFrame();
/* 896 */       Wait.shortWait(100);
/* 897 */       if (Def.building == 2)
/*     */         return; 
/* 899 */       this.startPuz++;
/*     */     } 
/* 901 */     this.howMany = 1;
/* 902 */     Methods.puzzleTitle = title;
/* 903 */     Op.setInt(Op.DM.DmSize.ordinal(), saveDominoSize, Op.dm);
/*     */   }
/*     */   
/*     */   private boolean buildDomino() {
/* 907 */     int loop = 0;
/*     */     while (true) {
/* 909 */       makeGrid(); if (determineLayout()) {
/* 910 */         insertDominoes();
/* 911 */         if (Def.building == 2)
/* 912 */           return true; 
/* 913 */         if (this.howMany == 1 && loop++ % 100 == 0) {
/* 914 */           restoreFrame();
/*     */         }
/* 916 */         if (solveDomino()) {
/* 917 */           saveDomino(Op.dm[Op.DM.DmPuz.ordinal()]);
/* 918 */           return true;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mustapha\Desktop\Crossword-Express.jar!\crosswordexpress\DominoBuild.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */