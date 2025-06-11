/*     */ package crosswordexpress;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.awt.event.MouseEvent;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JCheckBox;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JMenu;
/*     */ import javax.swing.JMenuItem;
/*     */ import javax.swing.KeyStroke;
/*     */ 
/*     */ public final class KsudokuSolve extends JPanel {
/*     */   static JFrame jfSolveKsudoku;
/*     */   static JMenuBar menuBar;
/*     */   JMenu menu;
/*     */   JMenu submenu;
/*     */   JMenuItem menuItem;
/*     */   static JPanel pp;
/*     */   static int panelW;
/*  22 */   static int[] undoX = new int[750]; static int panelH; static JLabel jl1; static JLabel jl2; Timer myTimer; Runnable solveThread; int memMode; boolean assist = true; static int undoIndex;
/*  23 */   static int[] undoY = new int[750];
/*  24 */   static int[] undoI = new int[750];
/*  25 */   static int[] undoM = new int[750];
/*  26 */   static int[] undoS = new int[750];
/*  27 */   static int[] bit = new int[] { 2, 4, 8, 16, 32, 64, 128, 256, 512 };
/*  28 */   static int[] invbit = new int[] { 1020, 1018, 1014, 1006, 990, 958, 894, 766, 510 };
/*     */   
/*  30 */   static Color STRONG_LINK = new Color(34816);
/*  31 */   static Color WEAK_LINK = new Color(8978312);
/*     */   
/*  33 */   static int solution = 0; static int delete = 1;
/*     */   
/*     */   static int hintIndexb;
/*     */   
/*  37 */   static int[] hintXb = new int[16]; static int[] hintYb = new int[16];
/*     */   static int hintIndexg;
/*  39 */   static int[] hintXg = new int[16]; static int[] hintYg = new int[16];
/*     */   static int hintIndexr;
/*  41 */   static int[] hintXr = new int[16]; static int[] hintYr = new int[16];
/*     */   
/*     */   static int hintUnitIndex;
/*  44 */   static String[] hintUnit = new String[20]; static int hintCellIndex;
/*     */   static int hintCandidate;
/*  46 */   static int[] hintCellx = new int[20];
/*  47 */   static int[] hintCelly = new int[20];
/*     */   static int weakLinkIndex;
/*  49 */   static int[] weakLinkx1 = new int[32]; static int[] weakLinky1 = new int[32]; static int[] weakLinkx2 = new int[32]; static int[] weakLinky2 = new int[32];
/*     */   
/*     */   static int candIndex;
/*  52 */   static int[] candx = new int[50]; static int[] candy = new int[50]; static int[] candv = new int[50];
/*     */   
/*  54 */   String sudokuSolveHelp = "<div>The solving of a KILLER SUDOKU puzzle is guided by a red focus cell which can be moved about the puzzle by pointing and clicking with the mouse or by means of the cursor keys.<p/>Any character which is typed at the keyboard will be placed into the focus cell.<p/>Under default conditions, the program displays in each unsolved cell, the set of candidate symbols which might legally be placed into that cell. Whenever a solution symbol is placed into one of the cells, the list of candidate symbols in other cells is automatically updated.<p/> If you are familiar with some of the more advanced SUDOKU solving techniques (X wing, Swordfish etc), you may see that certain of the listed candidate symbols cannot possibly be a solution for the cell in which they are shown. Such candidates may be removed by pointing and clicking with the mouse. When the last candidate symbol is removed from a cell, it becomes the solution symbol for that cell.<br/><br/></div><span class='m'>Menu Functions</span><br/><br/><ul><li/><span class='s'>File Menu</span><ul><li/><span>Load a Puzzle</span><br/>Use this option to choose the puzzle you want to solve from the pool of KILLER SUDOKU puzzles currently available on your computer.<p/><li/><span class='menusub'>Quit Solving</span><br/>Returns you to the KILLER SUDOKU Construction screen.<p/></ul><li/><span class='s'>Options Menu</span><ul><li/><span>Display Options</span><br/>This leads you to a dialog box in which you can change the colors of various elements within the puzzle, and control the fonts which will be used for the puzzle's text components. You can also decide if printing of the puzzle will be done in black and white or in color.<p/><li/><span class='menusub'>Solve Options</span><br/>There are two options involved with the solving of KILLER SUDOKU puzzles, and the are both related to the handling of the candidate numbers which Crossword Express displays in the puzzle cells while the puzzle is being solved.<p/></ul><li/><span class='s'>Tasks Menu</span><ul><li/><span>Undo</span><br/>This option will undo in reverse order, each of the operations you have done on the puzzle.<p/><li/><span>Set Cage Candidate Values</span><br/>When you begin solving a new puzzle, the default condition is that each cell of the puzzle will have all nine candidates displayed. You can use the rules of KILLER SUDOKU to remove those candidates which cannot be used to contribute to the cage total. This is a quite tedious process, and you will most likely tire of it quite quickly. If so, you can use this function to quickly remove the inappropriate candidates. If you prefer, you can use the <b>Solve Options</b> mentioned above to change the default so that the puzzle always starts with the inappropriate values already removed.<p/><li/><span>Reveal One Symbol</span><br/>If you need a little help to get started, this option will place the correct symbol into the current focus cell.<p/><li/><span>Reveal Errors</span><br/>If you think you may have made errors, this option will show you where they are by highlighting them for a period of 1.5 seconds.<p/><li/><span>Reveal Solution</span><br/>The entire solution can be seen by selecting this option.<p/><li/><span>Request a Hint</span><br/>This option will highlight one or more puzzle cells in blue to indicate the location within the puzzle where further progress can be made. In addition an automatic Help screen will appear giving you a detailed description of the strategy you will need to apply.<p/><li/><span>Begin Again</span><br/>You can restart the entire solution process at any time by selecting this option.<p/></ul><li/><span class='s'>Help Menu</span><ul><li/><span>Ksudoku Help</span><br/>Displays the Help screen which you are now reading.<p/></ul></ul></body>";
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
/* 117 */   String SolveKsudokuOptions = "<div> There are just two options available to modify the behaviour of the Ksudoku solve function, and they are both involved with the candidate values which occupy all unsolved cells within the puzzle.</div><br><ul><li/><b>Update candidate values automatically when a solution value is entered:</b><br>Whenever a solution character is entered into a cell, any cells in the same row, column or box which contain that character as a candidate must have it removed. Under default conditions, it is the responsibility of the solver to remove these characters. This is a simple mechanical process, and for an experienced solver, quickly becomes quite tedious. If you select this check box, Crossword Express will remove them for you automatically.<p/><li/><b>Use cage score to set candidate values when starting a new puzzle: </b><br>The first step in solving a Killer Sudoku puzzle is usually to use the cage score and cage size information to remove those candidate numbers from the cage cells which cannot contribute to the required sum. For example in the case of cage size 2 and cage sum 5, the only candidates which can be solutions are 1, 2, 3, and 4. It is a useful exercise to do this a few times, but you will soon tire of it. If you select this check box, Crossword Express will domplete this operation for you, each time you start solving a new puzzle.<p/></ul></body>";
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
/*     */   KsudokuSolve(JFrame jf) {
/* 135 */     this.memMode = Def.puzzleMode;
/* 136 */     Def.puzzleMode = 113;
/* 137 */     Def.dispCursor = Boolean.valueOf(true); Def.dispGuideDigits = Boolean.valueOf(true);
/* 138 */     SudokuBuild.linkDatIndex = 0;
/*     */ 
/*     */     
/* 141 */     Grid.clearGrid();
/*     */     
/* 143 */     jfSolveKsudoku = new JFrame("Ksudoku");
/* 144 */     jfSolveKsudoku.setSize(Op.getInt(Op.KS.KsW.ordinal(), Op.ks), Op.getInt(Op.KS.KsH.ordinal(), Op.ks));
/* 145 */     int frameX = (jf.getX() + jfSolveKsudoku.getWidth() > Methods.scrW) ? (Methods.scrW - jfSolveKsudoku.getWidth() - 10) : jf.getX();
/* 146 */     jfSolveKsudoku.setLocation(frameX, jf.getY());
/* 147 */     jfSolveKsudoku.setLayout((LayoutManager)null);
/* 148 */     jfSolveKsudoku.setDefaultCloseOperation(0);
/* 149 */     jfSolveKsudoku
/* 150 */       .addComponentListener(new ComponentAdapter() {
/*     */           public void componentResized(ComponentEvent ce) {
/* 152 */             int oldw = Op.getInt(Op.KS.KsW.ordinal(), Op.ks);
/* 153 */             int oldh = Op.getInt(Op.KS.KsH.ordinal(), Op.ks);
/* 154 */             Methods.frameResize(KsudokuSolve.jfSolveKsudoku, oldw, oldh, 500, 580);
/* 155 */             Op.setInt(Op.KS.KsW.ordinal(), KsudokuSolve.jfSolveKsudoku.getWidth(), Op.ks);
/* 156 */             Op.setInt(Op.KS.KsH.ordinal(), KsudokuSolve.jfSolveKsudoku.getHeight(), Op.ks);
/* 157 */             KsudokuSolve.restoreFrame("");
/*     */           }
/*     */         });
/*     */     
/* 161 */     jfSolveKsudoku
/* 162 */       .addWindowListener(new WindowAdapter() {
/*     */           public void windowClosing(WindowEvent we) {
/* 164 */             if (Def.selecting)
/* 165 */               return;  KsudokuSolve.this.restoreIfDone();
/* 166 */             KsudokuBuild.saveKsudoku(Op.ks[Op.KS.KsPuz.ordinal()]);
/* 167 */             Methods.clearHintData();
/* 168 */             CrosswordExpress.transfer(112, KsudokuSolve.jfSolveKsudoku);
/*     */           }
/*     */         });
/*     */     
/* 172 */     Methods.closeHelp();
/*     */ 
/*     */     
/* 175 */     this.solveThread = (() -> {
/*     */         for (int j = 0; j < 9; j++) {
/*     */           for (int i = 0; i < 9; i++) {
/*     */             if (Grid.grid[i][j] != Grid.sol[i][j])
/*     */               return; 
/*     */           } 
/*     */         }  Methods.congratulations(jfSolveKsudoku); clearSolution();
/*     */         undoIndex = 0;
/*     */         if ((Op.getInt(Op.KS.KsAssist.ordinal(), Op.ks) & 0x2) == 2) {
/*     */           setCageValues();
/*     */         } else {
/*     */           recalculateStatus();
/*     */         } 
/*     */       });
/* 189 */     jl1 = new JLabel(); jfSolveKsudoku.add(jl1);
/* 190 */     jl2 = new JLabel(); jfSolveKsudoku.add(jl2);
/*     */ 
/*     */     
/* 193 */     menuBar = new JMenuBar();
/* 194 */     menuBar.setBackground(Def.COLOR_MENUBAR);
/* 195 */     jfSolveKsudoku.setJMenuBar(menuBar);
/*     */     
/* 197 */     this.menu = new JMenu("File");
/* 198 */     menuBar.add(this.menu);
/* 199 */     this.menuItem = new JMenuItem("Load a Puzzle");
/* 200 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(76, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 201 */     this.menu.add(this.menuItem);
/* 202 */     this.menuItem
/* 203 */       .addActionListener(ae -> {
/*     */           if (Def.building == 1) {
/*     */             return;
/*     */           }
/*     */           Methods.clearHintData();
/*     */           restoreIfDone();
/*     */           KsudokuBuild.saveKsudoku(Op.ks[Op.KS.KsPuz.ordinal()]);
/*     */           new Select(jfSolveKsudoku, "ksudoku", "ksudoku", Op.ks, Op.KS.KsPuz.ordinal(), false);
/*     */         });
/* 212 */     this.menuItem = new JMenuItem("Quit Solving");
/* 213 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(81, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 214 */     this.menu.add(this.menuItem);
/* 215 */     this.menuItem
/* 216 */       .addActionListener(ae -> {
/*     */           restoreIfDone();
/*     */           
/*     */           KsudokuBuild.saveKsudoku(Op.ks[Op.KS.KsPuz.ordinal()]);
/*     */           
/*     */           Methods.clearHintData();
/*     */           
/*     */           CrosswordExpress.transfer(112, jfSolveKsudoku);
/*     */         });
/* 225 */     this.menu = new JMenu("Options");
/* 226 */     menuBar.add(this.menu);
/* 227 */     this.menuItem = new JMenuItem("Display Options");
/* 228 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(89, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 229 */     this.menu.add(this.menuItem);
/* 230 */     this.menuItem
/* 231 */       .addActionListener(ae -> {
/*     */           KsudokuBuild.printOptions(jfSolveKsudoku, "Display Options");
/*     */           
/*     */           restoreFrame("");
/*     */         });
/*     */     
/* 237 */     this.menuItem = new JMenuItem("Solve Options");
/* 238 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(86, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 239 */     this.menu.add(this.menuItem);
/* 240 */     this.menuItem
/* 241 */       .addActionListener(ae -> {
/*     */           if (Def.building == 1) {
/*     */             return;
/*     */           }
/*     */           
/*     */           ksudokuSolveOptions();
/*     */           restoreFrame("");
/*     */         });
/* 249 */     this.menu = new JMenu("Tasks");
/* 250 */     menuBar.add(this.menu);
/* 251 */     this.menuItem = new JMenuItem("Undo");
/* 252 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(85, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 253 */     this.menu.add(this.menuItem);
/* 254 */     this.menuItem
/* 255 */       .addActionListener(ae -> {
/*     */           Methods.clearHintData();
/*     */           
/*     */           if (undoIndex > 0) {
/*     */             do {
/*     */               undoIndex--;
/*     */               
/*     */               Grid.xCur = undoX[undoIndex];
/*     */               Grid.yCur = undoY[undoIndex];
/*     */               Grid.status[Grid.xCur][Grid.yCur] = Grid.status[Grid.xCur][Grid.yCur] | bit[undoI[undoIndex]];
/*     */               Grid.grid[Grid.xCur][Grid.yCur] = 0;
/*     */             } while (undoS[undoIndex] == 0);
/*     */           }
/*     */           restoreFrame("");
/*     */         });
/* 270 */     this.menuItem = new JMenuItem("Set Cage Candidate Values");
/* 271 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(67, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 272 */     this.menu.add(this.menuItem);
/* 273 */     this.menuItem
/* 274 */       .addActionListener(ae -> {
/*     */           setCageValues();
/*     */           
/*     */           undoIndex = 0;
/*     */           
/*     */           restoreFrame("");
/*     */         });
/* 281 */     this.menuItem = new JMenuItem("Reveal One Symbol");
/* 282 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(79, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 283 */     this.menu.add(this.menuItem);
/* 284 */     this.menuItem
/* 285 */       .addActionListener(ae -> {
/*     */           if (Methods.noReveal == 0) {
/*     */             char ch = (char)Grid.grid[Grid.xCur][Grid.yCur];
/*     */             
/*     */             if (ch == '\000') {
/*     */               int i = Grid.sol[Grid.xCur][Grid.yCur] - 1;
/*     */               
/*     */               Grid.grid[Grid.xCur][Grid.yCur] = Grid.sol[Grid.xCur][Grid.yCur];
/*     */               
/*     */               updateUndo(Grid.xCur, Grid.yCur, i);
/*     */               undoS[undoIndex - 1] = 1;
/*     */               updateSolveStatus(Grid.xCur, Grid.yCur, i);
/*     */             } 
/*     */           } else {
/*     */             Methods.noReveal(jfSolveKsudoku);
/*     */           } 
/*     */           restoreFrame("");
/*     */         });
/* 303 */     ActionListener errorTimer = ae -> {
/*     */         Def.dispErrors = Boolean.valueOf(false);
/*     */         pp.repaint();
/*     */         this.myTimer.stop();
/*     */       };
/* 308 */     this.myTimer = new Timer(1500, errorTimer);
/*     */     
/* 310 */     this.menuItem = new JMenuItem("Reveal Errors");
/* 311 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(69, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 312 */     this.menu.add(this.menuItem);
/* 313 */     this.menuItem
/* 314 */       .addActionListener(ae -> {
/*     */           if (Methods.noErrors == 0) {
/*     */             this.myTimer.start();
/*     */             
/*     */             Def.dispErrors = Boolean.valueOf(true);
/*     */           } else {
/*     */             Methods.noReveal(jfSolveKsudoku);
/*     */           } 
/*     */           
/*     */           pp.repaint();
/*     */         });
/* 325 */     this.menuItem = new JMenuItem("Reveal Solution");
/* 326 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(83, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 327 */     this.menu.add(this.menuItem);
/* 328 */     this.menuItem
/* 329 */       .addActionListener(ae -> {
/*     */           if (Methods.noReveal == 0) {
/*     */             for (int j = 0; j < 9; j++) {
/*     */               for (int i = 0; i < 9; i++) {
/*     */                 Grid.grid[i][j] = Grid.sol[i][j];
/*     */               }
/*     */             } 
/*     */             clearStatus();
/*     */             recalculateStatus();
/*     */             undoIndex = 0;
/*     */           } else {
/*     */             Methods.noReveal(jfSolveKsudoku);
/*     */           } 
/*     */           restoreFrame("");
/*     */         });
/* 344 */     this.menuItem = new JMenuItem("Request a Hint");
/* 345 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(82, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 346 */     this.menu.add(this.menuItem);
/* 347 */     this.menuItem
/* 348 */       .addActionListener(ae -> {
/*     */           Grid.findHint = Boolean.valueOf(true);
/*     */           
/*     */           SudokuBuild.hintIndexb = 0;
/*     */           
/*     */           int hintNum = KsudokuBuild.nextHint();
/*     */           
/*     */           Methods.closeHelp();
/*     */           String str = Methods.hintReplacements(SudokuBuild.sudokuHint[hintNum], Grid.hintWrdCnt);
/*     */           Methods.cweHelp(jfSolveKsudoku, null, Grid.hintTitle, str);
/*     */           restoreFrame("");
/*     */         });
/* 360 */     this.menuItem = new JMenuItem("Begin again");
/* 361 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(66, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 362 */     this.menu.add(this.menuItem);
/* 363 */     this.menuItem
/* 364 */       .addActionListener(ae -> {
/*     */           Methods.clearHintData();
/*     */           
/*     */           clearSolution();
/*     */           
/*     */           undoIndex = 0;
/*     */           if ((Op.getInt(Op.KS.KsAssist.ordinal(), Op.ks) & 0x2) == 2) {
/*     */             setCageValues();
/*     */           } else {
/*     */             recalculateStatus();
/*     */           } 
/*     */           restoreFrame("");
/*     */         });
/* 377 */     this.menu = new JMenu("Help");
/* 378 */     menuBar.add(this.menu);
/* 379 */     this.menuItem = new JMenuItem("Ksudoku Help");
/* 380 */     this.menu.add(this.menuItem);
/* 381 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(72, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 382 */     this.menuItem
/* 383 */       .addActionListener(ae -> Methods.cweHelp(jfSolveKsudoku, null, "Solving Ksudoku Puzzles", this.sudokuSolveHelp));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 389 */     KsudokuBuild.loadKsudoku(Op.ks[Op.KS.KsPuz.ordinal()]);
/* 390 */     KsudokuBuild.buildVirtualCages();
/* 391 */     pp = new KsudokuSolvePP(0, 37);
/* 392 */     jfSolveKsudoku.add(pp);
/*     */     
/* 394 */     pp
/* 395 */       .addMouseListener(new MouseAdapter() {
/*     */           public void mousePressed(MouseEvent e) {
/* 397 */             KsudokuSolve.this.updateGrid(e);
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 402 */     pp
/* 403 */       .addMouseMotionListener(new MouseAdapter() {
/*     */           public void mouseMoved(MouseEvent e) {
/* 405 */             if (Def.isMac) {
/* 406 */               KsudokuSolve.jfSolveKsudoku.setResizable((KsudokuSolve.jfSolveKsudoku.getWidth() - e.getX() < 15 && KsudokuSolve.jfSolveKsudoku
/* 407 */                   .getHeight() - e.getY() < 95));
/*     */             }
/*     */           }
/*     */         });
/*     */     
/* 412 */     jfSolveKsudoku
/* 413 */       .addKeyListener(new KeyAdapter() {
/*     */           public void keyPressed(KeyEvent e) {
/* 415 */             KsudokuSolve.this.handleKeyPressed(e);
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 420 */     restoreFrame("");
/*     */   }
/*     */   
/*     */   static void restoreFrame(String hint) {
/* 424 */     jfSolveKsudoku.setVisible(true);
/* 425 */     Insets insets = jfSolveKsudoku.getInsets();
/* 426 */     panelW = jfSolveKsudoku.getWidth() - insets.left + insets.right;
/* 427 */     panelH = jfSolveKsudoku.getHeight() - insets.top + insets.bottom + 37 + menuBar.getHeight();
/* 428 */     pp.setSize(panelW, panelH);
/* 429 */     setSizesAndOffsets(0, 0, panelW, panelH, 20);
/* 430 */     jfSolveKsudoku.requestFocusInWindow();
/* 431 */     pp.repaint();
/* 432 */     Methods.infoPanel(jl1, jl2, "Solve Killer Sudoku", (hint.length() > 0) ? ("  Hint : " + hint) : ("Puzzle : " + Op.ks[Op.KS.KsPuz.ordinal()]), panelW);
/*     */   }
/*     */   
/*     */   static void setSizesAndOffsets(int x, int y, int width, int height, int inset) {
/* 436 */     int i = 2 * (width - inset) / (2 * Grid.xSz + 1);
/* 437 */     int j = 2 * (height - inset) / (2 * Grid.ySz + 1);
/* 438 */     Grid.xCell = Grid.yCell = (i < j) ? i : j;
/* 439 */     Grid.xOrg = x + (width - Grid.xSz * Grid.xCell - Grid.xCell / 2) / 2 + Grid.xCell / 2;
/* 440 */     Grid.yOrg = y + (height - Grid.ySz * Grid.yCell - Grid.yCell / 2) / 2 + Grid.yCell / 2;
/*     */   }
/*     */   
/*     */   private void ksudokuSolveOptions() {
/* 444 */     JDialog jdlgSolveKsudoku = new JDialog(jfSolveKsudoku, "Killer Sudoku Options", true);
/* 445 */     jdlgSolveKsudoku.setSize(500, 145);
/* 446 */     jdlgSolveKsudoku.setResizable(false);
/* 447 */     jdlgSolveKsudoku.setLayout((LayoutManager)null);
/* 448 */     jdlgSolveKsudoku.setLocation(jfSolveKsudoku.getX(), jfSolveKsudoku.getY());
/*     */     
/* 450 */     jdlgSolveKsudoku
/* 451 */       .addWindowListener(new WindowAdapter() {
/*     */           public void windowClosing(WindowEvent we) {
/* 453 */             Methods.closeHelp();
/*     */           }
/*     */         });
/*     */     
/* 457 */     Methods.closeHelp();
/*     */     
/* 459 */     JCheckBox op1 = new JCheckBox("Update candidate values automatically when a solution value is entered.", ((Op.getInt(Op.KS.KsAssist.ordinal(), Op.ks) & 0x1) == 1));
/* 460 */     op1.setForeground(Def.COLOR_LABEL);
/* 461 */     op1.setOpaque(false);
/* 462 */     op1.setSize(480, 30);
/* 463 */     op1.setLocation(10, 5);
/* 464 */     jdlgSolveKsudoku.add(op1);
/*     */     
/* 466 */     JCheckBox op2 = new JCheckBox("Use cage score to set candidate values when starting a new puzzle.", ((Op.getInt(Op.KS.KsAssist.ordinal(), Op.ks) & 0x2) == 2));
/* 467 */     op2.setForeground(Def.COLOR_LABEL);
/* 468 */     op2.setOpaque(false);
/* 469 */     op2.setSize(480, 30);
/* 470 */     op2.setLocation(10, 35);
/* 471 */     jdlgSolveKsudoku.add(op2);
/*     */     
/* 473 */     JButton jbOK = Methods.cweButton("OK", 129, 75, 80, 26, null);
/* 474 */     jbOK.addActionListener(e -> {
/*     */           Methods.clickedOK = true; int v = 0; if (paramJCheckBox1.isSelected())
/*     */             v |= 0x1; 
/*     */           if (paramJCheckBox2.isSelected())
/*     */             v |= 0x2; 
/*     */           Op.setInt(Op.KS.KsAssist.ordinal(), v, Op.ks);
/*     */           paramJDialog.dispose();
/*     */           Methods.closeHelp();
/*     */         });
/* 483 */     jdlgSolveKsudoku.add(jbOK);
/*     */     
/* 485 */     JButton jbCancel = Methods.cweButton("Cancel", 129, 109, 80, 26, null);
/* 486 */     jbCancel.addActionListener(e -> {
/*     */           paramJDialog.dispose();
/*     */           Methods.closeHelp();
/*     */         });
/* 490 */     jdlgSolveKsudoku.add(jbCancel);
/*     */     
/* 492 */     JButton jbHelp = Methods.cweButton("<html><font size=6 color=BB0000 face=Serif>Help ", 216, 75, 155, 60, new ImageIcon("graphics/help.png"));
/* 493 */     jbHelp.addActionListener(e -> Methods.cweHelp(null, paramJDialog, "Ksudoku Solve Options", this.SolveKsudokuOptions));
/*     */     
/* 495 */     jdlgSolveKsudoku.add(jbHelp);
/*     */     
/* 497 */     jdlgSolveKsudoku.getRootPane().setDefaultButton(jbOK);
/* 498 */     Methods.setDialogSize(jdlgSolveKsudoku, 500, 145);
/*     */   }
/*     */   
/*     */   static void setCageValues() {
/* 502 */     int[] startSum = { 3, 6, 10, 15, 21, 28, 36, 45 };
/* 503 */     int[] startAdr = { 0, 15, 34, 55, 76, 95, 110, 119 };
/*     */ 
/*     */     
/* 506 */     int[] cageDat = { 6, 10, 30, 54, 126, 238, 510, 990, 1020, 952, 1008, 864, 960, 640, 768, 14, 22, 62, 126, 254, 510, 1022, 1022, 1022, 1022, 1022, 1022, 1022, 1020, 1016, 1008, 992, 832, 896, 30, 46, 126, 254, 510, 1022, 1022, 1022, 1022, 1022, 1022, 1022, 1022, 1022, 1022, 1022, 1020, 1016, 1008, 928, 960, 62, 94, 254, 510, 1022, 1022, 1022, 1022, 1022, 1022, 1022, 1022, 1022, 1022, 1022, 1022, 1022, 1020, 1016, 976, 992, 126, 190, 510, 1022, 1022, 1022, 1022, 1022, 1022, 1022, 1022, 1022, 1022, 1022, 1022, 1022, 1020, 1000, 1008, 254, 382, 1022, 1022, 1022, 1022, 1022, 1022, 1022, 1022, 1022, 1022, 1022, 1012, 1016, 510, 766, 894, 958, 990, 1006, 1014, 1018, 1020, 1022 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 516 */     for (int j = 0; j < 9; j++) {
/* 517 */       for (int i = 0; i < 9; i++) {
/* 518 */         int cNum = Grid.mode[i][j];
/* 519 */         int cageSize = (KsudokuBuild.cData[cNum]).area;
/* 520 */         int cageSum = (KsudokuBuild.cData[cNum]).score;
/* 521 */         int thisAdr = startAdr[cageSize - 2] + cageSum - startSum[cageSize - 2];
/* 522 */         Grid.status[i][j] = cageDat[thisAdr];
/*     */       } 
/*     */     } 
/*     */   }
/*     */   void updateUndo(int x, int y, int i) {
/* 527 */     if ((Grid.status[x][y] & bit[i]) > 0) {
/* 528 */       Grid.status[x][y] = Grid.status[x][y] & invbit[i];
/* 529 */       undoX[undoIndex] = x;
/* 530 */       undoY[undoIndex] = y;
/* 531 */       undoI[undoIndex] = i;
/* 532 */       undoS[undoIndex++] = 0;
/*     */     } 
/*     */   }
/*     */   
/*     */   static void clearSolution() {
/* 537 */     for (int j = 0; j < 9; j++) {
/* 538 */       for (int i = 0; i < 9; i++)
/* 539 */         Grid.grid[i][j] = 0; 
/*     */     } 
/*     */   }
/*     */   void restoreIfDone() {
/* 543 */     for (int j = 0; j < 9; j++) {
/* 544 */       for (int i = 0; i < 9; i++)
/* 545 */       { if (Grid.grid[i][j] == 0)
/*     */           return;  } 
/* 547 */     }  clearSolution();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void updateSolveStatus(int x, int y, int i) {
/* 553 */     Grid.status[x][y] = Grid.status[x][y] & invbit[i];
/* 554 */     if ((Op.getInt(Op.KS.KsAssist.ordinal(), Op.ks) & 0x1) != 1)
/*     */       return; 
/*     */     int j;
/* 557 */     for (j = 0; j < 9; j++) {
/* 558 */       updateUndo(x, y, j);
/* 559 */       updateUndo(j, y, i);
/* 560 */       updateUndo(x, j, i);
/*     */     } 
/* 562 */     int a = x / 3 * 3, b = y / 3 * 3;
/* 563 */     for (j = 0; j < 3; j++) {
/* 564 */       for (int k = 0; k < 3; k++)
/* 565 */         updateUndo(a + j, b + k, i); 
/*     */     } 
/* 567 */     for (int cNum = 0; (KsudokuBuild.cData[cNum]).area > 0; cNum++) {
/* 568 */       int cells = (KsudokuBuild.cData[cNum]).area;
/* 569 */       for (j = 0; j < cells; j++) {
/* 570 */         if ((KsudokuBuild.cData[cNum]).x[j] == x && (KsudokuBuild.cData[cNum]).y[j] == y) {
/* 571 */           for (j = 0; j < cells; j++) {
/* 572 */             updateUndo((KsudokuBuild.cData[cNum]).x[j], (KsudokuBuild.cData[cNum]).y[j], i);
/*     */           }
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   static void updateStatus(int x, int y, int z) {
/* 582 */     if (z == -1)
/* 583 */       return;  int a = x / 3 * 3, b = y / 3 * 3;
/* 584 */     for (int j = 0; j < 9; j++) {
/* 585 */       Grid.status[x][y] = Grid.status[x][y] & invbit[j];
/* 586 */       Grid.status[j][y] = Grid.status[j][y] & invbit[z];
/* 587 */       Grid.status[x][j] = Grid.status[x][j] & invbit[z];
/* 588 */       Grid.status[a + j % 3][b + j / 3] = Grid.status[a + j % 3][b + j / 3] & invbit[z];
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   static void recalculateStatus() {
/*     */     int y;
/* 595 */     for (y = 0; y < 9; y++) {
/* 596 */       for (int x = 0; x < 9; x++)
/* 597 */         Grid.status[x][y] = 1022; 
/*     */     } 
/* 599 */     for (y = 0; y < 9; y++) {
/* 600 */       for (int x = 0; x < 9; x++)
/* 601 */         updateStatus(x, y, Grid.grid[x][y] - 1); 
/*     */     } 
/*     */   }
/*     */   static void clearStatus() {
/* 605 */     for (int y = 0; y < 9; y++) {
/* 606 */       for (int x = 0; x < 9; x++)
/* 607 */         Grid.status[x][y] = 0; 
/*     */     } 
/*     */   }
/*     */   void updateGrid(MouseEvent e) {
/* 611 */     int x = e.getX(), y = e.getY();
/* 612 */     int cBoxDim = 22 * Grid.xCell / 100, candOffset = 9 * Grid.xCell / 30;
/*     */     
/* 614 */     Grid.errorExists = Boolean.valueOf(false);
/* 615 */     if (x < Grid.xOrg || y < Grid.yOrg)
/* 616 */       return;  int i = (x - Grid.xOrg) / Grid.xCell;
/* 617 */     int j = (y - Grid.yOrg) / Grid.yCell;
/* 618 */     if (i >= Grid.xSz || j >= Grid.ySz)
/* 619 */       return;  Grid.xCur = i; Grid.yCur = j;
/*     */     
/* 621 */     int xOrg = x - Grid.xOrg - i * Grid.xCell - candOffset;
/* 622 */     int yOrg = y - Grid.yOrg - j * Grid.yCell - candOffset;
/* 623 */     if (xOrg < 0 || yOrg < 0) { restoreFrame(""); return; }
/*     */     
/* 625 */     i = xOrg / cBoxDim;
/* 626 */     j = yOrg / cBoxDim;
/* 627 */     if (i > 2 || j > 2)
/* 628 */       return;  int val = j * 3 + i;
/*     */     
/* 630 */     for (x = 0; x < SudokuBuild.candIndex; x++) {
/* 631 */       if (SudokuBuild.candx[x] == Grid.xCur && SudokuBuild.candy[x] == Grid.yCur && SudokuBuild.candv[x] == val && SudokuBuild.candc[x] == 0) {
/*     */ 
/*     */ 
/*     */         
/* 635 */         updateUndo(Grid.xCur, Grid.yCur, val);
/* 636 */         undoS[undoIndex - 1] = 1;
/* 637 */         Grid.grid[Grid.xCur][Grid.yCur] = val + 1;
/* 638 */         updateSolveStatus(Grid.xCur, Grid.yCur, val);
/* 639 */         (new Thread(this.solveThread)).start();
/* 640 */         restoreFrame("");
/*     */         return;
/*     */       } 
/*     */     } 
/* 644 */     if ((Grid.status[Grid.xCur][Grid.yCur] & bit[val]) > 0) {
/* 645 */       updateUndo(Grid.xCur, Grid.yCur, val);
/* 646 */       undoS[undoIndex - 1] = 1;
/* 647 */       if (Grid.status[Grid.xCur][Grid.yCur] == 0) {
/* 648 */         Grid.grid[Grid.xCur][Grid.yCur] = val + 1;
/* 649 */         updateSolveStatus(Grid.xCur, Grid.yCur, val);
/* 650 */         (new Thread(this.solveThread)).start();
/* 651 */         restoreFrame("");
/*     */         return;
/*     */       } 
/* 654 */       restoreFrame("");
/*     */       return;
/*     */     } 
/* 657 */     Methods.clearHintData();
/* 658 */     restoreFrame("");
/*     */   }
/*     */   void handleKeyPressed(KeyEvent e) {
/*     */     int i;
/* 662 */     if (e.isAltDown())
/* 663 */       return;  Grid.errorExists = Boolean.valueOf(false);
/* 664 */     switch (e.getKeyCode()) { case 38:
/* 665 */         if (Grid.yCur > 0) Grid.yCur--;  break;
/* 666 */       case 40: if (Grid.yCur < Grid.ySz - 1) Grid.yCur++;  break;
/* 667 */       case 37: if (Grid.xCur > 0) Grid.xCur--;  break;
/* 668 */       case 39: if (Grid.xCur < Grid.xSz - 1) Grid.xCur++;  break;
/* 669 */       case 36: Grid.xCur = 0; break;
/* 670 */       case 35: Grid.xCur = Grid.xSz - 1; break;
/* 671 */       case 33: Grid.yCur = 0; break;
/* 672 */       case 34: Grid.yCur = Grid.ySz - 1; break;
/*     */       default:
/* 674 */         i = e.getKeyChar() - 49;
/* 675 */         if (i >= 0 && i < 9) {
/* 676 */           if ((Grid.status[Grid.xCur][Grid.yCur] & bit[i]) > 0) {
/* 677 */             updateUndo(Grid.xCur, Grid.yCur, i);
/* 678 */             undoS[undoIndex - 1] = 1;
/* 679 */             Grid.grid[Grid.xCur][Grid.yCur] = i + 1;
/* 680 */             updateSolveStatus(Grid.xCur, Grid.yCur, i);
/* 681 */             (new Thread(this.solveThread)).start();
/*     */             break;
/*     */           } 
/* 684 */           Methods.noCandidate(jfSolveKsudoku);
/*     */         }  break; }
/*     */     
/* 687 */     restoreFrame("");
/*     */   }
/*     */ }


/* Location:              C:\Users\mustapha\Desktop\Crossword-Express.jar!\crosswordexpress\KsudokuSolve.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */