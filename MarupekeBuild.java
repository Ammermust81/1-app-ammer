/*     */ package crosswordexpress;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.util.Random;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JMenuItem;
/*     */ import javax.swing.KeyStroke;
/*     */ 
/*     */ public class MarupekeBuild {
/*     */   static JFrame jfMarupeke;
/*     */   static JMenuBar menuBar;
/*     */   JMenu menu;
/*     */   JMenu submenu;
/*     */   JMenuItem menuItem;
/*     */   JMenuItem buildMenuItem;
/*     */   static JPanel pp;
/*  24 */   int howMany = 1; static int panelW; static int panelH; static JLabel jl1; static JLabel jl2; static Timer myTimer; static Timer listenTimer; Thread thread; int startPuz = Integer.parseInt((new SimpleDateFormat("yyyyMMdd")).format(new Date())); int hmCount;
/*     */   static boolean sixpack;
/*  26 */   int[] depopulateList = new int[50];
/*     */   static Point screenLoc;
/*  28 */   static String rules = "Into each blank square place an O or an X in such a way that there are never three Os or three Xs in line, either horizontally, vertically or diagonally.";
/*     */   int leastCount;
/*     */   int loop;
/*     */   
/*     */   static void def() {
/*  33 */     Op.updateOption(Op.MA.MaW.ordinal(), "500", Op.ma);
/*  34 */     Op.updateOption(Op.MA.MaH.ordinal(), "580", Op.ma);
/*  35 */     Op.updateOption(Op.MA.MaAcross.ordinal(), "8", Op.ma);
/*  36 */     Op.updateOption(Op.MA.MaDown.ordinal(), "8", Op.ma);
/*  37 */     Op.updateOption(Op.MA.Ma1.ordinal(), "FF5555", Op.ma);
/*  38 */     Op.updateOption(Op.MA.Ma2.ordinal(), "88FF88", Op.ma);
/*  39 */     Op.updateOption(Op.MA.Ma3.ordinal(), "8888FF", Op.ma);
/*  40 */     Op.updateOption(Op.MA.Ma4.ordinal(), "FFFF88", Op.ma);
/*  41 */     Op.updateOption(Op.MA.Ma5.ordinal(), "00FFFF", Op.ma);
/*  42 */     Op.updateOption(Op.MA.Ma6.ordinal(), "FF88FF", Op.ma);
/*  43 */     Op.updateOption(Op.MA.Ma7.ordinal(), "AAAAAA", Op.ma);
/*  44 */     Op.updateOption(Op.MA.Ma8.ordinal(), "00AAAA", Op.ma);
/*  45 */     Op.updateOption(Op.MA.MaEmpty.ordinal(), "FFFFFF", Op.ma);
/*  46 */     Op.updateOption(Op.MA.MaNumber.ordinal(), "000000", Op.ma);
/*  47 */     Op.updateOption(Op.MA.MaLines.ordinal(), "000000", Op.ma);
/*  48 */     Op.updateOption(Op.MA.MaError.ordinal(), "FF0000", Op.ma);
/*  49 */     Op.updateOption(Op.MA.MaPuz.ordinal(), "sample.marupeke", Op.ma);
/*  50 */     Op.updateOption(Op.MA.MaFont.ordinal(), "SansSerif", Op.ma);
/*  51 */     Op.updateOption(Op.MA.MaPuzColor.ordinal(), "false", Op.ma);
/*  52 */     Op.updateOption(Op.MA.MaSolColor.ordinal(), "false", Op.ma);
/*     */   }
/*     */   
/*  55 */   String marupekeHelp = "<div>A MARUPEKE puzzle consists of a square grid in which some of the squares are blacked out in the manner of a crossword puzzle. Some of the remaining squares contain either an <b>X</b> or a <b>O</b>. To solve such a puzzle, the solver must place an <b>X</b> or a <b>O</b> into each of the remaining squares in such a way that there are never three <b>Xs</b> or three <b>Os</b> in line, either horizontally, vertically or diagonally. Each puzzle has a unique solution which does not require any guesswork to achieve. Puzzles can be built in sizes from 4x4 up to 10x10.<br/><br/></div><span class='m'>Menu Functions</span><ul><li/><span class='s'>File Menu</span><ul><li/><span>Load a Puzzle</span><br/>Use this option to choose your puzzle from the pool of MARUPEKE puzzles currently available on your computer.<p/><li/><span>SaveAs</span><br/>This option allows you to make an exact copy of the current puzzle using a different file name. The copy will be saved in the <b>marupeke</b> folder along with all of the Marupeke puzzles you have made. Alternatively, if you don't enter a new name for the puzzle, you can change the Puzzle Description, or any of the other descriptive items without changing the puzzle name.<p/><li/><span>Quit Construction</span><br/>Returns you to the Crossword Express opening screen.<br/></ul><li/><span class='s'>Build Menu</span><ul><li/><span>Start a New Puzzle</span><br/>This option presents you with a dialog into which you can enter a file name for your new puzzle. You can also enter several other pieces of information such as a <b>Puzzle Title, Author</b> and <b>Copyright</b> information.<p/><li/><span>Build Options</span><br/>Use this option to access a Build Options dialog where you can set some rules to be followed by the puzzle building function as it builds your puzzle.<p/><li/><span>Start Building / Stop Building</span><br/>Construction of the puzzle will commence when you select the <b>Start Building</b> option. If puzzle building is successful you will receive a message containing the name of the puzzle file, and the location where it was saved. If it becomes necessary, you can interrupt the building process by selecting this option a second time. Note that during the construction phase, the text of this option is changed to <b>Stop Building</b><br/></ul><li/><span class='s'>Export Menu</span><br/><ul><li/><span>Print a Marupeke KDP puzzle book.</span><br/>The letters KDP stand for <b>Kindle Direct Publishing</b>. This is a free publishing service operared by Amazon, in which they handle all matters related to printing, advertising and sales of books created by members of the public. A portion of the proceeds are retained by Amazon while the remainder is paid to the author. Fifteen of the Puzzles created by Crossword Express can be printed into PDF format files ready for publication by Amazon. When you select this option, you will be presented with a dialog which allows you to control the process. Please study the Help offered by this dialog before attempting to make use of it.</ul><li/><span class='s'>Tasks Menu</span><ul><li/><span>Print this Puzzle</span><br/>This will take you to a custom print screen where you can control the details involved with printing your puzzle.<p/><li/><span>Solve this Puzzle</span><br/>This will take you to a Solve screen which provides a fully interactive environment for solving the puzzle.<p/><li/><span>Delete this Puzzle</span><br/>Use this option to eliminate unwanted MARUPEKE puzzles from your file system.<br/></ul><li/><span class='s'>Help Menu</span><ul><li/><span>Marupeke Help</span><br/>Displays the Help screen which you are now reading.<br/></ul></ul></body>";
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
/* 114 */   String marupekeOptions = "<div>Before you give the command to build the <b>Marupeke</b> puzzle, you can set some options which the program will use during the construction process.</div><br/><ul><li/>Marupeke puzzles can be made in sizes ranging from 4x4 up to 10x10. This can be controlled using the <b>Puzzle Size</b> Combo-box.<p/><li/>If you want to make a number of puzzles all having the same dimensions, simply type a number into the <b>How many puzzles</b> input field. When you issue the Make command, Crossword Express will make that number of puzzles. The puzzle names will be numbers which represent a date in <b>yyyymmdd</b> format. The default value presented by Crossword Express is always the current date, but you can change this to any date that suits your needs. As the series of puzzles is created, CWE will automatically step on to the next date in the sequence, taking into account such factors as the varying number of days in the months, and of course leap years. Virtually any number of puzzles can be made in a single operation using this feature.<p/><li/><b>HOWEVER:</b> If you prefer a simpler numbering scheme for your puzzles, you can enter any number of 7 digits or less to be used for your first puzzle, and Crossword Express will number the remainder of the puzzles sequentially starting with your number.<p/><li/>If you do choose to make multiple puzzles, then by default, Crossword Express will change the difficulty of the resulting puzzles over a cycle of seven puzzles. This would be useful for a daily newspaper so that the week could start with a very easy puzzle, with quite difficult puzzles reserved for the weekend. If you don't want this feature, clearing the <b>Vary Difficulty on 7 day cycle</b> checkbox will disable it.</ul></body>";
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
/*     */   MarupekeBuild(JFrame jf, boolean auto, int hm, int start) {
/* 136 */     Def.puzzleMode = 230;
/* 137 */     Def.building = 0;
/* 138 */     Def.dispCursor = Boolean.valueOf(true);
/* 139 */     makeGrid();
/* 140 */     jfMarupeke = new JFrame("Marupeke");
/* 141 */     if (Op.getInt(Op.MA.MaH.ordinal(), Op.ma) > Methods.scrH - 200) {
/* 142 */       int diff = Op.getInt(Op.MA.MaH.ordinal(), Op.ma) - Op.getInt(Op.MA.MaW.ordinal(), Op.ma);
/* 143 */       Op.setInt(Op.MA.MaH.ordinal(), Methods.scrH - 200, Op.ma);
/* 144 */       Op.setInt(Op.MA.MaW.ordinal(), Methods.scrH - 200 + diff, Op.ma);
/*     */     } 
/* 146 */     jfMarupeke.setSize(Op.getInt(Op.MA.MaW.ordinal(), Op.ma), Op.getInt(Op.MA.MaH.ordinal(), Op.ma));
/* 147 */     int frameX = (jf.getX() + jfMarupeke.getWidth() > Methods.scrW) ? (Methods.scrW - jfMarupeke.getWidth() - 10) : jf.getX();
/* 148 */     jfMarupeke.setLocation(frameX, jf.getY());
/* 149 */     jfMarupeke.setLayout((LayoutManager)null);
/* 150 */     jfMarupeke.setDefaultCloseOperation(0);
/* 151 */     jfMarupeke
/* 152 */       .addComponentListener(new ComponentAdapter() {
/*     */           public void componentResized(ComponentEvent ce) {
/* 154 */             int oldw = Op.getInt(Op.MA.MaW.ordinal(), Op.ma);
/* 155 */             int oldh = Op.getInt(Op.MA.MaH.ordinal(), Op.ma);
/* 156 */             Methods.frameResize(MarupekeBuild.jfMarupeke, oldw, oldh, 500, 580);
/* 157 */             Op.setInt(Op.MA.MaW.ordinal(), MarupekeBuild.jfMarupeke.getWidth(), Op.ma);
/* 158 */             Op.setInt(Op.MA.MaH.ordinal(), MarupekeBuild.jfMarupeke.getHeight(), Op.ma);
/* 159 */             MarupekeBuild.restoreFrame();
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 164 */     jfMarupeke
/* 165 */       .addWindowListener(new WindowAdapter() {
/*     */           public void windowClosing(WindowEvent we) {
/* 167 */             if (Def.building == 1 || Def.selecting)
/* 168 */               return;  Op.saveOptions("marupeke.opt", Op.ma);
/* 169 */             CrosswordExpress.transfer(1, MarupekeBuild.jfMarupeke);
/*     */           }
/*     */         });
/*     */     
/* 173 */     Methods.closeHelp();
/*     */     
/* 175 */     Runnable buildThread = () -> {
/*     */         if (this.howMany == 1) {
/*     */           this.leastCount = 50; this.loop = 0;
/*     */           while (this.loop < 50) {
/*     */             int v = buildMarupeke();
/*     */             if (v < this.leastCount) {
/*     */               this.leastCount = v;
/*     */               saveMarupeke(Op.ma[Op.MA.MaPuz.ordinal()]);
/*     */             } 
/*     */             System.out.println();
/*     */             this.loop++;
/*     */           } 
/*     */           loadMarupeke(Op.ma[Op.MA.MaPuz.ordinal()]);
/*     */         } else {
/*     */           multiBuild();
/*     */           if (sixpack) {
/*     */             Sixpack.trigger();
/*     */             jfMarupeke.dispose();
/*     */             Def.building = 0;
/*     */             return;
/*     */           } 
/*     */         } 
/*     */         this.buildMenuItem.setText("Start Building");
/*     */         if (Def.building == 2) {
/*     */           Def.building = 0;
/*     */           Methods.interrupted(jfMarupeke);
/*     */           makeGrid();
/*     */           restoreFrame();
/*     */           return;
/*     */         } 
/*     */         Methods.havePuzzle = true;
/*     */         if (this.howMany == 1)
/*     */           restoreFrame(); 
/*     */         Methods.puzzleSaved(jfMarupeke, "marupeke", Op.ma[Op.MA.MaPuz.ordinal()]);
/*     */         Def.building = 0;
/*     */       };
/* 211 */     jl1 = new JLabel(); jfMarupeke.add(jl1);
/* 212 */     jl2 = new JLabel(); jfMarupeke.add(jl2);
/*     */ 
/*     */     
/* 215 */     menuBar = new JMenuBar();
/* 216 */     menuBar.setBackground(Def.COLOR_MENUBAR);
/* 217 */     jfMarupeke.setJMenuBar(menuBar);
/*     */     
/* 219 */     this.menu = new JMenu("File");
/* 220 */     menuBar.add(this.menu);
/* 221 */     this.menuItem = new JMenuItem("Load a Puzzle");
/* 222 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(76, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 223 */     this.menu.add(this.menuItem);
/* 224 */     this.menuItem
/* 225 */       .addActionListener(ae -> {
/*     */           if (Def.building == 1)
/*     */             return; 
/*     */           pp.invalidate();
/*     */           pp.repaint();
/*     */           new Select(jfMarupeke, "marupeke", "marupeke", Op.ma, Op.MA.MaPuz.ordinal(), false);
/*     */         });
/* 232 */     this.menuItem = new JMenuItem("SaveAs");
/* 233 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(65, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 234 */     this.menu.add(this.menuItem);
/* 235 */     this.menuItem
/* 236 */       .addActionListener(ae -> {
/*     */           if (Def.building == 1)
/*     */             return; 
/*     */           Methods.puzzleDescriptionDialog(jfMarupeke, Op.ma[Op.MA.MaPuz.ordinal()].substring(0, Op.ma[Op.MA.MaPuz.ordinal()].indexOf(".marupeke")), "marupeke", ".marupeke");
/*     */           if (Methods.clickedOK) {
/*     */             for (int j = 0; j < Grid.ySz; j++) {
/*     */               for (int i = 0; i < Grid.xSz; i++)
/*     */                 Grid.work[i][j] = Grid.puz[i][j]; 
/*     */             } 
/*     */             solveMarupeke(true);
/*     */             saveMarupeke(Op.ma[Op.MA.MaPuz.ordinal()] = Methods.theFileName);
/*     */             restoreFrame();
/*     */             Methods.puzzleSaved(jfMarupeke, "marupeke", Op.ma[Op.MA.MaPuz.ordinal()]);
/*     */           } 
/*     */         });
/* 251 */     this.menuItem = new JMenuItem("Quit Construction");
/* 252 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(81, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 253 */     this.menu.add(this.menuItem);
/* 254 */     this.menuItem
/* 255 */       .addActionListener(ae -> {
/*     */           if (Def.building == 1) {
/*     */             return;
/*     */           }
/*     */           
/*     */           Op.saveOptions("marupeke.opt", Op.ma);
/*     */           CrosswordExpress.transfer(1, jfMarupeke);
/*     */         });
/* 263 */     this.menu = new JMenu("Build");
/* 264 */     menuBar.add(this.menu);
/* 265 */     this.menuItem = new JMenuItem("Start a new Puzzle");
/* 266 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(78, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 267 */     this.menu.add(this.menuItem);
/* 268 */     this.menuItem
/* 269 */       .addActionListener(ae -> {
/*     */           if (Def.building == 1) {
/*     */             return;
/*     */           }
/*     */           Methods.puzzleDescriptionDialog(jfMarupeke, Op.ma[Op.MA.MaPuz.ordinal()].substring(0, Op.ma[Op.MA.MaPuz.ordinal()].indexOf(".marupeke")), "marupeke", ".marupeke");
/*     */           if (Methods.clickedOK) {
/*     */             Op.ma[Op.MA.MaPuz.ordinal()] = Methods.theFileName;
/*     */             makeGrid();
/*     */           } 
/*     */           restoreFrame();
/*     */         });
/* 280 */     this.menuItem = new JMenuItem("Build Options");
/* 281 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(79, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 282 */     this.menu.add(this.menuItem);
/* 283 */     this.menuItem
/* 284 */       .addActionListener(ae -> {
/*     */           if (Def.building == 1) {
/*     */             return;
/*     */           }
/*     */           marupekeOptions();
/*     */           if (Methods.clickedOK)
/*     */             makeGrid(); 
/*     */           restoreFrame();
/*     */         });
/* 293 */     this.buildMenuItem = new JMenuItem("Start Building");
/* 294 */     this.buildMenuItem.setAccelerator(KeyStroke.getKeyStroke(66, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 295 */     this.menu.add(this.buildMenuItem);
/* 296 */     this.buildMenuItem
/* 297 */       .addActionListener(ae -> {
/*     */           if (Op.ma[Op.MA.MaPuz.ordinal()].length() == 0) {
/*     */             Methods.noName(jfMarupeke);
/*     */             
/*     */             return;
/*     */           } 
/*     */           
/*     */           if (Def.building == 0) {
/*     */             this.thread = new Thread(paramRunnable);
/*     */             
/*     */             this.thread.start();
/*     */             
/*     */             Def.building = 1;
/*     */             this.buildMenuItem.setText("Stop Building");
/*     */           } else {
/*     */             Def.building = 2;
/*     */             restoreFrame();
/*     */           } 
/*     */         });
/* 316 */     this.menu = new JMenu("Export");
/* 317 */     menuBar.add(this.menu);
/* 318 */     this.menuItem = new JMenuItem("Print a Marupeke KDP puzzle book.");
/* 319 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(75, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 320 */     this.menu.add(this.menuItem);
/* 321 */     this.menuItem
/* 322 */       .addActionListener(ae -> {
/*     */           if (Def.building == 1) {
/*     */             return;
/*     */           }
/*     */           
/*     */           Methods.printKdpDialog(jfMarupeke, 230, 6);
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
/*     */           CrosswordExpress.toPrint(jfMarupeke, Op.ma[Op.MA.MaPuz.ordinal()]);
/*     */         });
/* 341 */     this.menuItem = new JMenuItem("Solve this Puzzle");
/* 342 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(86, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 343 */     this.menu.add(this.menuItem);
/* 344 */     this.menuItem
/* 345 */       .addActionListener(ae -> {
/*     */           if (Def.building == 1)
/*     */             return; 
/*     */           Methods.havePuzzle = true;
/*     */           if (Methods.havePuzzle) {
/*     */             CrosswordExpress.transfer(231, jfMarupeke);
/*     */           } else {
/*     */             Methods.noPuzzle(jfMarupeke, "Solve");
/*     */           } 
/*     */         });
/* 355 */     this.menuItem = new JMenuItem("Delete this Puzzle");
/* 356 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(68, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 357 */     this.menu.add(this.menuItem);
/* 358 */     this.menuItem
/* 359 */       .addActionListener(ae -> {
/*     */           if (Def.building == 1) {
/*     */             return;
/*     */           }
/*     */           
/*     */           if (Methods.deleteAPuzzle(jfMarupeke, Op.ma[Op.MA.MaPuz.ordinal()], "marupeke", pp)) {
/*     */             makeGrid();
/*     */             loadMarupeke(Op.ma[Op.MA.MaPuz.ordinal()]);
/*     */             restoreFrame();
/*     */           } 
/*     */         });
/* 370 */     this.menu = new JMenu("Help");
/* 371 */     menuBar.add(this.menu);
/*     */     
/* 373 */     this.menuItem = new JMenuItem("Marupeke Help");
/* 374 */     this.menu.add(this.menuItem);
/* 375 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(72, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 376 */     this.menuItem
/* 377 */       .addActionListener(ae -> Methods.cweHelp(jfMarupeke, null, "Building Marupeke Puzzles", this.marupekeHelp));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 382 */     pp = new MarupekeBuildPP(0, 37);
/* 383 */     jfMarupeke.add(pp);
/*     */     
/* 385 */     pp
/* 386 */       .addMouseMotionListener(new MouseAdapter() {
/*     */           public void mouseMoved(MouseEvent e) {
/* 388 */             if (Def.isMac) {
/* 389 */               MarupekeBuild.jfMarupeke.setResizable((MarupekeBuild.jfMarupeke.getWidth() - e.getX() < 15 && MarupekeBuild.jfMarupeke
/* 390 */                   .getHeight() - e.getY() < 95));
/*     */             }
/*     */           }
/*     */         });
/*     */     
/* 395 */     loadMarupeke(Op.ma[Op.MA.MaPuz.ordinal()]);
/* 396 */     restoreFrame();
/*     */     
/* 398 */     ActionListener timerAL = ae -> {
/*     */         myTimer.stop();
/*     */         this.thread = new Thread(paramRunnable);
/*     */         this.thread.start();
/*     */         Def.building = 1;
/*     */       };
/* 404 */     myTimer = new Timer(1000, timerAL);
/*     */     
/* 406 */     if (auto) {
/* 407 */       sixpack = true;
/* 408 */       this.howMany = hm; this.startPuz = start;
/* 409 */       myTimer.start();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   static void restoreFrame() {
/* 415 */     jfMarupeke.setVisible(true);
/* 416 */     Insets insets = jfMarupeke.getInsets();
/* 417 */     panelW = jfMarupeke.getWidth() - insets.left + insets.right;
/* 418 */     panelH = jfMarupeke.getHeight() - insets.top + insets.bottom + 37 + menuBar.getHeight();
/* 419 */     pp.setSize(panelW, panelH);
/* 420 */     jfMarupeke.requestFocusInWindow();
/* 421 */     pp.repaint();
/* 422 */     Methods.infoPanel(jl1, jl2, "Build Marupeke", "Puzzle : " + Op.ma[Op.MA.MaPuz.ordinal()], panelW);
/*     */   }
/*     */   
/*     */   static void setSizesAndOffsets(int x, int y, int width, int height, int inset) {
/* 426 */     int i = (width - inset) / Grid.xSz;
/* 427 */     int j = (height - inset) / Grid.ySz;
/* 428 */     Grid.xCell = Grid.yCell = (i < j) ? i : j;
/* 429 */     Grid.xOrg = x + ((Def.puzzleMode == 8) ? ((width - Grid.xSz * Grid.xCell) / 2) : 10);
/* 430 */     Grid.yOrg = y + ((Def.puzzleMode == 8) ? ((height - Grid.ySz * Grid.yCell) / 2) : 10);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void marupekeOptions() {
/* 436 */     JDialog jdlgMarupeke = new JDialog(jfMarupeke, "Marupeke Options", true);
/* 437 */     jdlgMarupeke.setSize(260, 260);
/* 438 */     jdlgMarupeke.setResizable(false);
/* 439 */     jdlgMarupeke.setLayout((LayoutManager)null);
/* 440 */     jdlgMarupeke.setLocation(jfMarupeke.getX(), jfMarupeke.getY());
/*     */     
/* 442 */     jdlgMarupeke
/* 443 */       .addWindowListener(new WindowAdapter() {
/*     */           public void windowClosing(WindowEvent we) {
/* 445 */             Methods.closeHelp();
/*     */           }
/*     */         });
/*     */     
/* 449 */     Methods.closeHelp();
/*     */     
/* 451 */     JLabel jlSize = new JLabel("Puzzle Size:");
/* 452 */     jlSize.setForeground(Def.COLOR_LABEL);
/* 453 */     jlSize.setSize(120, 20);
/* 454 */     jlSize.setLocation(10, 15);
/* 455 */     jlSize.setHorizontalAlignment(4);
/* 456 */     jdlgMarupeke.add(jlSize);
/*     */     
/* 458 */     JComboBox<Integer> jcbbSize = new JComboBox<>();
/* 459 */     for (int i = 4; i <= 13; i++)
/* 460 */       jcbbSize.addItem(Integer.valueOf(i)); 
/* 461 */     jcbbSize.setSize(50, 20);
/* 462 */     jcbbSize.setLocation(140, 15);
/* 463 */     jdlgMarupeke.add(jcbbSize);
/* 464 */     jcbbSize.setBackground(Def.COLOR_BUTTONBG);
/* 465 */     jcbbSize.setSelectedIndex(Op.getInt(Op.MA.MaAcross.ordinal(), Op.ma) - 4);
/*     */     
/* 467 */     HowManyPuzzles hmp = new HowManyPuzzles(jdlgMarupeke, 10, 55, this.howMany, this.startPuz, Op.getBool(Op.SX.VaryDiff.ordinal(), Op.sx).booleanValue());
/*     */     
/* 469 */     JButton jbOK = Methods.cweButton("OK", 10, 169, 80, 26, null);
/* 470 */     jbOK.addActionListener(e -> {
/*     */           Grid.xSz = Grid.ySz = paramJComboBox.getSelectedIndex() + 4;
/*     */           Op.setInt(Op.MA.MaAcross.ordinal(), Grid.xSz, Op.ma);
/*     */           Op.setInt(Op.MA.MaDown.ordinal(), Grid.xSz, Op.ma);
/*     */           this.howMany = Integer.parseInt(paramHowManyPuzzles.jtfHowMany.getText());
/*     */           this.startPuz = Integer.parseInt(paramHowManyPuzzles.jtfStartPuz.getText());
/*     */           Op.setBool(Op.SX.VaryDiff.ordinal(), Boolean.valueOf(paramHowManyPuzzles.jcbVaryDiff.isSelected()), Op.sx);
/*     */           Methods.clickedOK = true;
/*     */           paramJDialog.dispose();
/*     */           Methods.closeHelp();
/*     */         });
/* 481 */     jdlgMarupeke.add(jbOK);
/*     */     
/* 483 */     JButton jbCancel = Methods.cweButton("Cancel", 10, 204, 80, 26, null);
/* 484 */     jbCancel.addActionListener(e -> {
/*     */           Methods.clickedOK = false;
/*     */           paramJDialog.dispose();
/*     */           Methods.closeHelp();
/*     */         });
/* 489 */     jdlgMarupeke.add(jbCancel);
/*     */     
/* 491 */     JButton jbHelp = Methods.cweButton("<html><font size=6 color=BB0000 face=Serif>Help ", 100, 169, 150, 61, new ImageIcon("graphics/help.png"));
/* 492 */     jbHelp.addActionListener(e -> Methods.cweHelp(null, paramJDialog, "Marupeke Options", this.marupekeOptions));
/*     */     
/* 494 */     jdlgMarupeke.add(jbHelp);
/*     */     
/* 496 */     jdlgMarupeke.getRootPane().setDefaultButton(jbOK);
/* 497 */     Methods.setDialogSize(jdlgMarupeke, 260, 240);
/*     */   }
/*     */   
/*     */   static void printOptions(JFrame jf, String type) {
/* 501 */     String[] colorLabel = { "Cell 1 Color", "Cell 2 Color", "Cell 3 Color", "Cell 4 Color", "Cell 5 Color", "Cell 6 Color", "Cell 7 Color", "Cell 8 Color", "Empty cells", "Number color", "Line color", "Error color" };
/* 502 */     int[] colorInt = { Op.MA.Ma1.ordinal(), Op.MA.Ma2.ordinal(), Op.MA.Ma3.ordinal(), Op.MA.Ma4.ordinal(), Op.MA.Ma5.ordinal(), Op.MA.Ma6.ordinal(), Op.MA.Ma7.ordinal(), Op.MA.Ma8.ordinal(), Op.MA.MaEmpty.ordinal(), Op.MA.MaNumber.ordinal(), Op.MA.MaLines.ordinal(), Op.MA.MaError.ordinal() };
/* 503 */     String[] fontLabel = { "Puzzle Font" };
/* 504 */     int[] fontInt = { Op.MA.MaFont.ordinal() };
/* 505 */     String[] checkLabel = { "PPrint Puzzle with color.", "SPrint Solution with color." };
/* 506 */     int[] checkInt = { Op.MA.MaPuzColor.ordinal(), Op.MA.MaSolColor.ordinal() };
/* 507 */     Methods.stdPrintOptions(jf, "Marupeke " + type, Op.ma, colorLabel, colorInt, fontLabel, fontInt, checkLabel, checkInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void saveMarupeke(String marupekeName) {
/*     */     try {
/* 516 */       DataOutputStream dataOut = new DataOutputStream(new FileOutputStream("marupeke/" + marupekeName));
/* 517 */       dataOut.writeInt(Grid.xSz);
/* 518 */       dataOut.writeInt(Grid.ySz);
/* 519 */       dataOut.writeByte(Methods.noReveal);
/* 520 */       dataOut.writeByte(Methods.noErrors);
/* 521 */       for (int i = 0; i < 54; i++)
/* 522 */         dataOut.writeByte(0); 
/* 523 */       for (int j = 0; j < Grid.ySz; j++) {
/* 524 */         for (int k = 0; k < Grid.xSz; k++) {
/* 525 */           dataOut.writeInt(Grid.sol[k][j]);
/* 526 */           dataOut.writeInt(Grid.puz[k][j]);
/* 527 */           dataOut.writeInt(Grid.work[k][j]);
/*     */         } 
/*     */       } 
/* 530 */       dataOut.writeUTF(Methods.puzzleTitle);
/* 531 */       dataOut.writeUTF(Methods.author);
/* 532 */       dataOut.writeUTF(Methods.copyright);
/* 533 */       dataOut.writeUTF(Methods.puzzleNumber);
/* 534 */       dataOut.writeUTF(Methods.puzzleNotes);
/* 535 */       dataOut.close();
/*     */     }
/* 537 */     catch (IOException exc) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void loadMarupeke(String marupekeName) {
/*     */     
/* 545 */     try { File fl = new File("marupeke/" + marupekeName);
/* 546 */       if (!fl.exists()) {
/* 547 */         fl = new File("marupeke/");
/* 548 */         String[] s = fl.list(); int k;
/* 549 */         for (k = 0; k < s.length && (
/* 550 */           s[k].lastIndexOf(".marupeke") == -1 || s[k].charAt(0) == '.'); k++);
/*     */         
/* 552 */         if (k == s.length) { makeGrid(); return; }
/* 553 */          marupekeName = s[k];
/* 554 */         Op.ma[Op.MA.MaPuz.ordinal()] = marupekeName;
/*     */       } 
/*     */ 
/*     */       
/* 558 */       DataInputStream dataIn = new DataInputStream(new FileInputStream("marupeke/" + marupekeName));
/* 559 */       Grid.xSz = dataIn.readInt();
/* 560 */       Grid.ySz = dataIn.readInt();
/* 561 */       Methods.noReveal = dataIn.readByte();
/* 562 */       Methods.noErrors = dataIn.readByte(); int i;
/* 563 */       for (i = 0; i < 54; i++)
/* 564 */         dataIn.readByte(); 
/* 565 */       for (int j = 0; j < Grid.ySz; j++) {
/* 566 */         for (i = 0; i < Grid.xSz; i++) {
/* 567 */           Grid.sol[i][j] = dataIn.readInt();
/* 568 */           Grid.puz[i][j] = dataIn.readInt();
/* 569 */           Grid.work[i][j] = dataIn.readInt();
/*     */         } 
/* 571 */       }  Methods.puzzleTitle = dataIn.readUTF();
/* 572 */       Methods.author = dataIn.readUTF();
/* 573 */       Methods.copyright = dataIn.readUTF();
/* 574 */       Methods.puzzleNumber = dataIn.readUTF();
/* 575 */       Methods.puzzleNotes = dataIn.readUTF();
/* 576 */       dataIn.close(); }
/*     */     
/* 578 */     catch (IOException exc) { return; }
/* 579 */      Methods.havePuzzle = true;
/*     */   }
/*     */ 
/*     */   
/*     */   static void drawMarupeke(Graphics2D g2, int[][] puzzleArray) {
/* 584 */     char[] symbol = { ' ', 'X', 'O', ' ', ' ', 'X', 'O', ' ', ' ' };
/*     */ 
/*     */     
/* 587 */     Stroke normalStroke = new BasicStroke(Grid.xCell / 25.0F, 2, 2);
/* 588 */     g2.setStroke(normalStroke);
/*     */     
/* 590 */     RenderingHints rh = g2.getRenderingHints();
/* 591 */     rh.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
/* 592 */     rh.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
/* 593 */     g2.setRenderingHints(rh);
/*     */     
/*     */     int j;
/* 596 */     for (j = 0; j < Grid.ySz; j++) {
/* 597 */       for (int i = 0; i < Grid.xSz; i++) {
/* 598 */         int bgColor = (puzzleArray[i][j] == 8) ? 0 : 16777215;
/* 599 */         if (Def.dispErrors.booleanValue() && 
/* 600 */           Grid.work[i][j] != 0 && Grid.work[i][j] != Grid.sol[i][j]) bgColor = 16711680; 
/* 601 */         if (i == MarupekeSolve.hintx && j == MarupekeSolve.hinty) {
/* 602 */           bgColor = 65535;
/* 603 */           MarupekeSolve.hintx = MarupekeSolve.hinty = -1;
/*     */         } 
/* 605 */         g2.setColor(new Color(bgColor));
/* 606 */         g2.fillRect(Grid.xOrg + i * Grid.xCell, Grid.yOrg + j * Grid.yCell, Grid.xCell, Grid.yCell);
/* 607 */         g2.setColor(new Color(Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.MA.MaLines.ordinal(), Op.ma) : 0));
/*     */       } 
/*     */     } 
/* 610 */     for (j = 0; j < Grid.ySz + 1; j++)
/* 611 */       g2.drawLine(Grid.xOrg, Grid.yOrg + j * Grid.yCell, Grid.xOrg + Grid.xSz * Grid.xCell, Grid.yOrg + j * Grid.yCell); 
/* 612 */     for (j = 0; j < Grid.xSz + 1; j++) {
/* 613 */       g2.drawLine(Grid.xOrg + j * Grid.xCell, Grid.yOrg, Grid.xOrg + j * Grid.xCell, Grid.yOrg + Grid.xSz * Grid.yCell);
/*     */     }
/* 615 */     Def.dispErrors = Boolean.valueOf(false);
/*     */ 
/*     */     
/* 618 */     g2.setFont(new Font(Op.ma[Op.MA.MaFont.ordinal()], 0, 8 * Grid.yCell / 10));
/* 619 */     FontMetrics fm = g2.getFontMetrics();
/* 620 */     for (j = 0; j < Grid.ySz; j++) {
/* 621 */       for (int i = 0; i < Grid.xSz; i++) {
/* 622 */         g2.setColor(new Color((puzzleArray[i][j] < 3) ? 0 : (Def.dispWithColor.booleanValue() ? 192 : 0)));
/* 623 */         char ch = symbol[puzzleArray[i][j]];
/* 624 */         int w = fm.stringWidth("" + ch);
/* 625 */         g2.drawString("" + ch, Grid.xOrg + i * Grid.xCell + (Grid.xCell - w) / 2, Grid.yOrg + j * Grid.yCell + (Grid.yCell + fm.getAscent() - fm.getDescent()) / 2);
/*     */       } 
/* 627 */     }  g2.setStroke(new BasicStroke(1.0F));
/*     */   }
/*     */   
/*     */   static void printPuz(Graphics2D g2, int left, int top, int width, int height) {
/* 631 */     loadMarupeke(Op.ma[Op.MA.MaPuz.ordinal()]);
/* 632 */     MarupekeSolve.clearSolution();
/* 633 */     setSizesAndOffsets(left, top, width, height, 0);
/* 634 */     Def.dispWithColor = Op.getBool(Op.MA.MaPuzColor.ordinal(), Op.ma);
/* 635 */     drawMarupeke(g2, Grid.puz);
/* 636 */     Def.dispWithColor = Boolean.valueOf(true);
/*     */   }
/*     */   
/*     */   static void printSol(Graphics2D g2, int left, int top, int width, int height, String solutionPuzzle) {
/* 640 */     loadMarupeke(solutionPuzzle);
/* 641 */     setSizesAndOffsets(left, top, width, height, 0);
/* 642 */     Def.dispWithColor = Op.getBool(Op.MA.MaSolColor.ordinal(), Op.ma);
/* 643 */     drawMarupeke(g2, Grid.sol);
/* 644 */     Def.dispWithColor = Boolean.valueOf(true);
/* 645 */     loadMarupeke(Op.ma[Op.MA.MaPuz.ordinal()]);
/*     */   }
/*     */   
/*     */   static void printSolTitle(Graphics2D g2, int left, int top, int width, int height, String solutionPuzzle) {
/* 649 */     loadMarupeke(solutionPuzzle);
/* 650 */     Print.outputTextItem(g2, left, top, width, height, "SansSerif", 0, Methods.puzzleTitle);
/* 651 */     loadMarupeke(Op.ma[Op.MA.MaPuz.ordinal()]);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static void printSixpackPuz(Graphics2D g2, int left, int top, int dim, int gap, String puzName) {
/* 657 */     FontMetrics fm = g2.getFontMetrics();
/*     */     
/* 659 */     String st = Op.sx[Op.SX.SxMa.ordinal()];
/* 660 */     if (st.length() < 3) st = "MARUPEKE"; 
/* 661 */     int w = fm.stringWidth(st);
/* 662 */     g2.drawString(st, left + (dim - w) / 2, top - gap / 3);
/* 663 */     MarupekeSolve.loadMarupeke(puzName + ".marupeke");
/* 664 */     setSizesAndOffsets(left, top, dim, dim, 0);
/* 665 */     MarupekeSolve.clearSolution();
/* 666 */     drawMarupeke(g2, Grid.puz);
/* 667 */     if (Op.sx[Op.SX.SxRuleLang.ordinal()].equals("English")) {
/* 668 */       st = rules;
/*     */     } else {
/* 670 */       st = Op.ma[Op.MA.MaRule1.ordinal() + Op.getInt(Op.SX.SxRuleLangIndex.ordinal(), Op.sx) - 1];
/* 671 */     }  if (Op.getBool(Op.SX.SxInstructions.ordinal(), Op.sx).booleanValue()) {
/* 672 */       Methods.renderText(g2, left, top + dim + dim / 50, dim, dim / 4, "SansSerif", 1, st, 3, 4, true, 0, 0);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static void printSixpackSol(Graphics2D g2, int left, int top, int dim, int gap, String solName) {
/* 678 */     FontMetrics fm = g2.getFontMetrics();
/*     */     
/* 680 */     String st = Op.sx[Op.SX.SxMa.ordinal()];
/* 681 */     if (st.length() < 3) st = "MARUPEKE"; 
/* 682 */     int w = fm.stringWidth(st);
/* 683 */     g2.drawString(st, left + (dim - w) / 2, top - gap / 3);
/* 684 */     loadMarupeke(solName + ".marupeke");
/* 685 */     setSizesAndOffsets(left, top, dim, dim, 0);
/* 686 */     drawMarupeke(g2, Grid.sol);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static void printKDPPuz(Graphics2D g2, int left, int top, int dim, int gap, String puzName) {
/* 692 */     FontMetrics fm = g2.getFontMetrics();
/*     */     
/* 694 */     String st = puzName;
/* 695 */     int w = fm.stringWidth(st);
/* 696 */     g2.drawString(st, left + (dim - w) / 2, top - gap / 3);
/* 697 */     MarupekeSolve.loadMarupeke(puzName + ".marupeke");
/* 698 */     setSizesAndOffsets(left, top, dim, dim, 0);
/* 699 */     MarupekeSolve.clearSolution();
/* 700 */     drawMarupeke(g2, Grid.puz);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static void printKDPSol(Graphics2D g2, int left, int top, int dim, int gap, String solName) {
/* 706 */     FontMetrics fm = g2.getFontMetrics();
/*     */     
/* 708 */     String st = solName;
/* 709 */     int w = fm.stringWidth(st);
/* 710 */     g2.drawString(st, left + (dim - w) / 2, top - gap / 3);
/* 711 */     loadMarupeke(solName + ".marupeke");
/* 712 */     setSizesAndOffsets(left, top, dim, dim, 0);
/* 713 */     drawMarupeke(g2, Grid.sol);
/*     */   }
/*     */   
/*     */   static void makeGrid() {
/* 717 */     Methods.havePuzzle = false;
/* 718 */     Grid.clearGrid();
/* 719 */     Grid.xSz = Op.getInt(Op.MA.MaAcross.ordinal(), Op.ma);
/* 720 */     Grid.ySz = Op.getInt(Op.MA.MaDown.ordinal(), Op.ma);
/*     */   }
/*     */ 
/*     */   
/*     */   static boolean solveMarupeke(boolean relocate) {
/* 725 */     boolean solved = true;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     int j;
/*     */ 
/*     */ 
/*     */     
/* 734 */     for (j = 0; j < Grid.ySz + 4; j++) {
/* 735 */       for (int i = 0; i < Grid.xSz + 4; i++)
/* 736 */         Grid.scratch[i][j] = 0; 
/*     */     } 
/* 738 */     for (j = 0; j < Grid.ySz; j++) {
/* 739 */       for (int i = 0; i < Grid.xSz; i++)
/* 740 */         Grid.scratch[i + 2][j + 2] = Grid.puz[i][j] & 0xB; 
/*     */     } 
/* 742 */     for (int x = 0; x < 40; x++) {
/* 743 */       for (j = 2; j < Grid.ySz + 2; j++) {
/* 744 */         for (int i = 2; i < Grid.xSz + 2; i++) {
/* 745 */           if (Grid.scratch[i][j] == 0)
/* 746 */           { int v = Grid.scratch[i - 1][j - 1]; if ((v == 1 || v == 2) && v == Grid.scratch[i + 1][j + 1]) Grid.scratch[i][j] = 3 - v; 
/* 747 */             v = Grid.scratch[i][j - 1]; if ((v == 1 || v == 2) && v == Grid.scratch[i][j + 1]) Grid.scratch[i][j] = 3 - v; 
/* 748 */             v = Grid.scratch[i + 1][j - 1]; if ((v == 1 || v == 2) && v == Grid.scratch[i - 1][j + 1]) Grid.scratch[i][j] = 3 - v; 
/* 749 */             v = Grid.scratch[i + 1][j]; if ((v == 1 || v == 2) && v == Grid.scratch[i - 1][j]) Grid.scratch[i][j] = 3 - v; 
/* 750 */             v = Grid.scratch[i - 1][j - 1]; if ((v == 1 || v == 2) && v == Grid.scratch[i - 2][j - 2]) Grid.scratch[i][j] = 3 - v; 
/* 751 */             v = Grid.scratch[i][j - 1]; if ((v == 1 || v == 2) && v == Grid.scratch[i][j - 2]) Grid.scratch[i][j] = 3 - v; 
/* 752 */             v = Grid.scratch[i + 1][j - 1]; if ((v == 1 || v == 2) && v == Grid.scratch[i + 2][j - 2]) Grid.scratch[i][j] = 3 - v; 
/* 753 */             v = Grid.scratch[i + 1][j]; if ((v == 1 || v == 2) && v == Grid.scratch[i + 2][j]) Grid.scratch[i][j] = 3 - v; 
/* 754 */             v = Grid.scratch[i + 1][j + 1]; if ((v == 1 || v == 2) && v == Grid.scratch[i + 2][j + 2]) Grid.scratch[i][j] = 3 - v; 
/* 755 */             v = Grid.scratch[i][j + 1]; if ((v == 1 || v == 2) && v == Grid.scratch[i][j + 2]) Grid.scratch[i][j] = 3 - v; 
/* 756 */             v = Grid.scratch[i - 1][j + 1]; if ((v == 1 || v == 2) && v == Grid.scratch[i - 2][j + 2]) Grid.scratch[i][j] = 3 - v; 
/* 757 */             v = Grid.scratch[i - 1][j]; if ((v == 1 || v == 2) && v == Grid.scratch[i - 2][j]) Grid.scratch[i][j] = 3 - v;  } 
/*     */         } 
/*     */       } 
/* 760 */     }  for (j = 0; j < Grid.ySz; j++) {
/* 761 */       for (int i = 0; i < Grid.xSz; i++) {
/* 762 */         int v = Grid.scratch[i + 2][j + 2] = Grid.scratch[i + 2][j + 2] | Grid.puz[i][j];
/* 763 */         if (v == 0) solved = false; 
/* 764 */         Grid.sol[i][j] = v;
/*     */       } 
/* 766 */     }  return solved;
/*     */   }
/*     */   
/*     */   private void multiBuild() {
/* 770 */     String title = Methods.puzzleTitle;
/* 771 */     int[] sizeDef = { 4, 5, 6, 7, 8, 9, 10 };
/* 772 */     String saveAcross = Op.ma[Op.MA.MaAcross.ordinal()];
/* 773 */     String saveDown = Op.ma[Op.MA.MaDown.ordinal()];
/*     */     
/* 775 */     SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
/* 776 */     Calendar c = Calendar.getInstance();
/*     */     
/* 778 */     for (this.hmCount = 1; this.hmCount <= this.howMany; this.hmCount++) {
/* 779 */       if (this.startPuz > 9999999) { try {
/* 780 */           c.setTime(sdf.parse("" + this.startPuz));
/* 781 */         } catch (ParseException ex) {}
/* 782 */         this.startPuz = Integer.parseInt(sdf.format(c.getTime())); }
/*     */ 
/*     */       
/* 785 */       Methods.puzzleTitle = "MARUPEKE Puzzle : " + this.startPuz;
/* 786 */       if (Op.getBool(Op.SX.VaryDiff.ordinal(), Op.sx).booleanValue()) {
/* 787 */         Op.setInt(Op.MA.MaAcross.ordinal(), sizeDef[(this.hmCount - 1) % 7], Op.ma);
/* 788 */         Op.setInt(Op.MA.MaDown.ordinal(), sizeDef[(this.hmCount - 1) % 7], Op.ma);
/*     */       } 
/*     */       
/* 791 */       Methods.buildProgress(jfMarupeke, Op.ma[Op.MA.MaPuz
/* 792 */             .ordinal()] = "" + this.startPuz + ".marupeke");
/*     */       
/* 794 */       for (this.leastCount = 50, this.loop = 0; this.loop < 50; this.loop++) {
/* 795 */         int v = buildMarupeke();
/* 796 */         if (v < this.leastCount) {
/* 797 */           this.leastCount = v;
/* 798 */           saveMarupeke(Op.ma[Op.MA.MaPuz.ordinal()]);
/*     */         } 
/*     */       } 
/* 801 */       loadMarupeke(Op.ma[Op.MA.MaPuz.ordinal()]);
/*     */       
/* 803 */       restoreFrame();
/* 804 */       Wait.shortWait(100);
/* 805 */       if (Def.building == 2)
/*     */         return; 
/* 807 */       this.startPuz++;
/*     */     } 
/* 809 */     this.howMany = 1;
/* 810 */     Methods.puzzleTitle = title;
/* 811 */     Op.ma[Op.MA.MaAcross.ordinal()] = saveAcross;
/* 812 */     Op.ma[Op.MA.MaDown.ordinal()] = saveDown;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean validMarupekeSymbol(int i, int j, int mem) {
/* 817 */     return (i != 1 && (Grid.build[i - 2][j] % 4 != mem || Grid.build[i - 1][j] % 4 != mem) && (Grid.build[i - 2][j - 2] % 4 != mem || Grid.build[i - 1][j - 1] % 4 != mem) && (Grid.build[i][j - 2] % 4 != mem || Grid.build[i][j - 1] % 4 != mem) && (Grid.build[i + 1][j - 1] % 4 != mem || Grid.build[i + 2][j - 2] % 4 != mem));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int buildMarupeke() {
/* 826 */     Random r = new Random();
/* 827 */     int symbols = 0;
/* 828 */     int[][] pat = new int[60][];
/* 829 */     pat[0] = new int[60];
/* 830 */     (new int[3])[0] = 16; (new int[3])[1] = 34; (new int[3])[2] = 0; pat[4] = new int[3];
/* 831 */     (new int[6])[0] = 32; (new int[6])[1] = 17; (new int[6])[2] = 65; (new int[6])[3] = 34; (new int[6])[4] = 52; (new int[6])[5] = 0; pat[5] = new int[6];
/* 832 */     (new int[9])[0] = 32; (new int[9])[1] = 1; (new int[9])[2] = 81; (new int[9])[3] = 18; (new int[9])[4] = 50; (new int[9])[5] = 67; (new int[9])[6] = 20; (new int[9])[7] = 69; (new int[9])[8] = 0; pat[6] = new int[9];
/* 833 */     (new int[13])[0] = 16; (new int[13])[1] = 80; (new int[13])[2] = 33; (new int[13])[3] = 66; (new int[13])[4] = 98; (new int[13])[5] = 19; (new int[13])[6] = 83; (new int[13])[7] = 4; (new int[13])[8] = 68; (new int[13])[9] = 21; (new int[13])[10] = 54; (new int[13])[11] = 86; (new int[13])[12] = 0; pat[7] = new int[13];
/* 834 */     (new int[16])[0] = 80; (new int[16])[1] = 33; (new int[16])[2] = 65; (new int[16])[3] = 113; (new int[16])[4] = 18; (new int[16])[5] = 98; (new int[16])[6] = 51; (new int[16])[7] = 20; (new int[16])[8] = 36; (new int[16])[9] = 100; (new int[16])[10] = 53; (new int[16])[11] = 85; (new int[16])[12] = 22; (new int[16])[13] = 39; (new int[16])[14] = 103; (new int[16])[15] = 0; pat[8] = new int[16];
/* 835 */     (new int[18])[0] = 32; (new int[18])[1] = 17; (new int[18])[2] = 49; (new int[18])[3] = 97; (new int[18])[4] = 82; (new int[18])[5] = 130; (new int[18])[6] = 35; (new int[18])[7] = 20; (new int[18])[8] = 68; (new int[18])[9] = 116; (new int[18])[10] = 85; (new int[18])[11] = 22; (new int[18])[12] = 7; (new int[18])[13] = 55; (new int[18])[14] = 71; (new int[18])[15] = 119; (new int[18])[16] = 104; (new int[18])[17] = 0; pat[9] = new int[18];
/* 836 */     (new int[20])[0] = 48; (new int[20])[1] = 17; (new int[20])[2] = 113; (new int[20])[3] = 34; (new int[20])[4] = 82; (new int[20])[5] = 130; (new int[20])[6] = 20; (new int[20])[7] = 52; (new int[20])[8] = 100; (new int[20])[9] = 116; (new int[20])[10] = 69; (new int[20])[11] = 117; (new int[20])[12] = 22; (new int[20])[13] = 150; (new int[20])[14] = 39; (new int[20])[15] = 119; (new int[20])[16] = 72; (new int[20])[17] = 104; (new int[20])[18] = 41; (new int[20])[19] = 0; pat[10] = new int[20];
/* 837 */     (new int[25])[0] = 6; (new int[25])[1] = 17; (new int[25])[2] = 24; (new int[25])[3] = 34; (new int[25])[4] = 38; (new int[25])[5] = 41; (new int[25])[6] = 52; (new int[25])[7] = 64; (new int[25])[8] = 71; (new int[25])[9] = 73; (new int[25])[10] = 83; (new int[25])[11] = 86; (new int[25])[12] = 98; (new int[25])[13] = 105; (new int[25])[14] = 112; (new int[25])[15] = 117; (new int[25])[16] = 119; (new int[25])[17] = 130; (new int[25])[18] = 137; (new int[25])[19] = 147; (new int[25])[20] = 161; (new int[25])[21] = 167; (new int[25])[22] = 90; (new int[25])[23] = 154; (new int[25])[24] = 0; pat[11] = new int[25];
/* 838 */     (new int[29])[0] = 6; (new int[29])[1] = 17; (new int[29])[2] = 24; (new int[29])[3] = 34; (new int[29])[4] = 38; (new int[29])[5] = 41; (new int[29])[6] = 52; (new int[29])[7] = 64; (new int[29])[8] = 71; (new int[29])[9] = 73; (new int[29])[10] = 83; (new int[29])[11] = 86; (new int[29])[12] = 98; (new int[29])[13] = 105; (new int[29])[14] = 112; (new int[29])[15] = 117; (new int[29])[16] = 119; (new int[29])[17] = 130; (new int[29])[18] = 137; (new int[29])[19] = 147; (new int[29])[20] = 161; (new int[29])[21] = 167; (new int[29])[22] = 90; (new int[29])[23] = 154; (new int[29])[24] = 179; (new int[29])[25] = 179; (new int[29])[26] = 75; (new int[29])[27] = 123; (new int[29])[28] = 0; pat[12] = new int[29];
/* 839 */     (new int[35])[0] = 48; (new int[35])[1] = 80; (new int[35])[2] = 160; (new int[35])[3] = 17; (new int[35])[4] = 97; (new int[35])[5] = 129; (new int[35])[6] = 193; (new int[35])[7] = 34; (new int[35])[8] = 66; (new int[35])[9] = 3; (new int[35])[10] = 67; (new int[35])[11] = 115; (new int[35])[12] = 163; (new int[35])[13] = 84; (new int[35])[14] = 196; (new int[35])[15] = 53; (new int[35])[16] = 133; (new int[35])[17] = 38; (new int[35])[18] = 182; (new int[35])[19] = 7; (new int[35])[20] = 87; (new int[35])[21] = 151; (new int[35])[22] = 24; (new int[35])[23] = 120; (new int[35])[24] = 200; (new int[35])[25] = 57; (new int[35])[26] = 137; (new int[35])[27] = 169; (new int[35])[28] = 74; (new int[35])[29] = 27; (new int[35])[30] = 107; (new int[35])[31] = 187; (new int[35])[32] = 92; (new int[35])[33] = 156; (new int[35])[34] = 0; pat[13] = new int[35];
/*     */ 
/*     */     
/* 842 */     makeGrid(); int y;
/* 843 */     for (y = 0; y < Grid.ySz + 4; y++) {
/* 844 */       for (int k = 0; k < Grid.xSz + 4; k++)
/* 845 */         Grid.build[k][y] = 0; 
/* 846 */     }  pat[0] = pat[Grid.xSz];
/*     */     
/* 848 */     if (r.nextInt(100) % 2 == 1) {
/* 849 */       for (int k = 0; pat[0][k] > 0; k++) {
/* 850 */         int v = pat[0][k];
/* 851 */         pat[0][k] = v % 16 * 16 + v / 16;
/*     */       } 
/*     */     }
/*     */     
/* 855 */     if (r.nextInt(100) % 2 == 1) {
/* 856 */       for (int k = 0; pat[0][k] > 0; k++) {
/* 857 */         int v = pat[0][k];
/* 858 */         pat[0][k] = (Grid.xSz - 1 - v / 16) * 16 + v % 16;
/*     */       } 
/*     */     }
/*     */     
/* 862 */     if (r.nextInt(100) % 2 == 1) {
/* 863 */       for (int k = 0; pat[0][k] > 0; k++) {
/* 864 */         int v = pat[0][k];
/* 865 */         pat[0][k] = v - v % 16 + Grid.xSz - 1 - v % 16;
/*     */       } 
/*     */     }
/*     */     int x;
/* 869 */     for (x = 0; pat[0][x] > 0; x++) {
/* 870 */       Grid.build[pat[0][x] / 16 + 2][pat[0][x] % 16 + 2] = 8;
/*     */     }
/* 872 */     symbols = 0; int j = 2, i = 1; while (true) {
/* 873 */       i++;
/* 874 */       if (i == Grid.xSz + 2) {
/* 875 */         i = 2; j++;
/* 876 */         if (j == Grid.ySz + 2) {
/* 877 */           for (y = 0; y < Grid.ySz; y++) {
/* 878 */             for (x = 0; x < Grid.xSz; x++) {
/* 879 */               Grid.build[x + 2][y + 2] = Grid.build[x + 2][y + 2] & 0xB; Grid.copy[x][y] = Grid.build[x + 2][y + 2] & 0xB; Grid.puz[x][y] = Grid.build[x + 2][y + 2] & 0xB;
/*     */             } 
/* 881 */           }  depopulate();
/* 882 */           for (y = 0; y < Grid.ySz; y++) {
/* 883 */             for (x = 0; x < Grid.xSz; x++) {
/* 884 */               Grid.work[x][y] = Grid.puz[x][y] + (((Grid.puz[x][y] & 0x3) > 0) ? 4 : 0);
/*     */               
/* 886 */               Grid.sol[x][y] = Grid.copy[x][y];
/* 887 */               if ((Grid.puz[x][y] & 0x3) > 0) {
/* 888 */                 Grid.sol[x][y] = Grid.sol[x][y] | 0x4; Grid.puz[x][y] = Grid.sol[x][y] | 0x4;
/* 889 */                 symbols++;
/*     */               } 
/*     */             } 
/* 892 */           }  return symbols;
/*     */         } 
/*     */       } 
/* 895 */       if (Grid.build[i][j] == 8)
/* 896 */         continue;  int mem = 1 + r.nextInt(100) % 2;
/* 897 */       if (validMarupekeSymbol(i, j, mem)) {
/* 898 */         Grid.build[i][j] = mem; continue;
/*     */       } 
/* 900 */       mem = 3 - mem;
/* 901 */       if (validMarupekeSymbol(i, j, mem)) {
/* 902 */         Grid.build[i][j] = mem + 4; continue;
/*     */       } 
/* 904 */       while (i > 1) {
/* 905 */         if (--i == 1 && j > 2) { i = Grid.xSz - 1; j--; }
/* 906 */          if (Grid.build[i][j] == 8)
/* 907 */           continue;  mem = Grid.build[i][j];
/* 908 */         Grid.build[i][j] = 0;
/* 909 */         if ((mem & 0x4) != 4) {
/* 910 */           mem = 3 - mem;
/* 911 */           if (validMarupekeSymbol(i, j, mem)) {
/* 912 */             Grid.build[i][j] = mem + 4;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void depopulate() {
/* 923 */     Random r = new Random();
/* 924 */     int sz = Grid.xSz * Grid.ySz, vec[] = new int[sz];
/*     */     
/* 926 */     for (int k = 0; k < 5; k++) {
/* 927 */       int i; for (i = 0; i < sz; ) { vec[i] = i; i++; }
/* 928 */        for (i = 0; i < sz; i++) {
/* 929 */         int j = r.nextInt(sz);
/* 930 */         int v = vec[i]; vec[i] = vec[j]; vec[j] = v;
/*     */       } 
/*     */       
/* 933 */       for (i = 0; i < sz; i++) {
/* 934 */         int x = vec[i] % Grid.xSz, y = vec[i] / Grid.ySz;
/* 935 */         if ((Grid.puz[x][y] & 0x3) > 0) {
/* 936 */           int hold = Grid.puz[x][y];
/* 937 */           Grid.puz[x][y] = 0;
/* 938 */           if (!solveMarupeke(false))
/* 939 */             Grid.puz[x][y] = hold; 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mustapha\Desktop\Crossword-Express.jar!\crosswordexpress\MarupekeBuild.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */