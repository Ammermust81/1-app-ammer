/*     */ package crosswordexpress;
/*     */ import java.awt.Color;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.Stroke;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JMenu;
/*     */ import javax.swing.JMenuItem;
/*     */ import javax.swing.KeyStroke;
/*     */ 
/*     */ public final class KendokuSolve extends JPanel {
/*     */   static JFrame jfSolveKendoku;
/*     */   static JMenuBar menuBar;
/*     */   JMenu menu;
/*     */   JMenu submenu;
/*     */   JMenuItem menuItem;
/*     */   static JPanel pp;
/*     */   static int panelW;
/*  27 */   int[] undoX = new int[750]; static int panelH; static JLabel jl1; static JLabel jl2; Timer myTimer; Runnable solveThread; int memMode; boolean assist = true; int undoIndex;
/*  28 */   int[] undoY = new int[750];
/*  29 */   int[] undoI = new int[750];
/*  30 */   int[] undoM = new int[750];
/*  31 */   int[] undoS = new int[750];
/*     */   
/*  33 */   String kendokuSolve = "<div><b>Kendoku</b> puzzles are built on square grids of typically 5x5 cells (although puzzles having sizes in the range 4x4 up to 9x9 can also be made). To solve them you must place numbers into the puzzle cells in such a way that each row and column contains each of the digits from 1 up to the size of the puzzle. In this respect they are similar to <b>Sudoku</b> puzzles. Unlike <b>Sudoku</b> puzzles, you are not given any starting digits. Instead, the puzzle is divided into <b>Domains</b> which are areas surrounded by a bold outline, and containing from two up to four cells. Each domain contains a hint consisting of a number and one of the mathematical symbols <b>+ x &mdash; /</b>. The number is the result of applying the mathematical operation represented by the symbol to the digits contained within the domain. This will provide enough information to allow each of the digits to be determined. Each puzzle has a unique solution, and no guessing is required.<p/>Any character which is typed at the keyboard will be placed into the focus cell (outlined in red), provided that it is a digit, and is not greater than the dimension of the puzzle.<p/>The location of the focus cell may be shifted by means of the cursor control keys or by pointing and clicking with the mouse.<p/>Under default conditions, the program displays in each unsolved cell, the set of candidate digits which might legally be placed into that cell. Whenever a solution digit is placed into one of the cells, the list of candidate digits in other cells is automatically updated.<p/>By the application of some logical reasoning, you will see that certain of the listed candidate digits cannot possibly be a solution for the cell in which they are shown. Such candidates may be removed by pointing and clicking with the mouse. When the last candidate digit is removed from a cell, it becomes the solution digit for that cell.<br/><br/></div><span class='m'>Menu Functions</span><ul><li/><span class='s'>File Menu</span><ul><li/><span>Load a Puzzle</span><br/>Use this option to choose the puzzle you want to solve from the pool of KENDOKU puzzles currently available on your computer.<p/><li/><span>Quit Solving</span><br/>Returns you to the KENDOKU Construction screen.</ul><li/><span class='s'>View Menu</span><ul><li/><span>Display Options</span><br/>This leads you to a dialog box in which you can change the colors of various elements within the puzzle, and control the fonts which will be used for the puzzle's text components. You can also decide if printing of the puzzle will be done in black and white or in color.<p/><li/><span>Toggle Assist Mode</span><br/>If you prefer to solve these puzzles without the assistance of the candidate symbols, they can be turned on and off using this option.</ul><li/><span class='s'>Tasks Menu</span><ul><li/><span>Undo</span><br/>This option will undo in reverse order, each of the operations you have done on the puzzle.<p/><li/><span>Reveal One Number</span><br/>If you need a little help to get started, this option will place the correct number into the current focus cell.<p/><li/><span>Reveal Errors</span><br/>If you think you may have made errors, this option will show you where they are by highlighting them for a period of 1.5 seconds.<p/><li/><span>Reveal Solution</span><br/>The entire solution can be seen by selecting this option.<p/><li/><span>Begin Again</span><br/>You can restart the entire solution process at any time by selecting this option.</ul><li/><span class='s'>Help Menu</span><ul><li/><span>Kendoku Help</span><br/>Displays the Help screen which you are now reading.</ul></ul></body>";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   KendokuSolve(JFrame jf) {
/*  88 */     this.memMode = Def.puzzleMode;
/*  89 */     Def.puzzleMode = 111;
/*  90 */     Def.dispCursor = Boolean.valueOf(true);
/*  91 */     Def.dispGuideDigits = Boolean.valueOf(true);
/*     */ 
/*     */     
/*  94 */     Grid.clearGrid();
/*     */     
/*  96 */     jfSolveKendoku = new JFrame("Kendoku");
/*  97 */     jfSolveKendoku.setSize(Op.getInt(Op.KE.KeW.ordinal(), Op.ke), Op.getInt(Op.KE.KeH.ordinal(), Op.ke));
/*  98 */     int frameX = (jf.getX() + jfSolveKendoku.getWidth() > Methods.scrW) ? (Methods.scrW - jfSolveKendoku.getWidth() - 10) : jf.getX();
/*  99 */     jfSolveKendoku.setLocation(frameX, jf.getY());
/* 100 */     jfSolveKendoku.setLayout((LayoutManager)null);
/* 101 */     jfSolveKendoku.setDefaultCloseOperation(0);
/* 102 */     jfSolveKendoku
/* 103 */       .addComponentListener(new ComponentAdapter() {
/*     */           public void componentResized(ComponentEvent ce) {
/* 105 */             int oldw = Op.getInt(Op.KE.KeW.ordinal(), Op.ke);
/* 106 */             int oldh = Op.getInt(Op.KE.KeH.ordinal(), Op.ke);
/* 107 */             Methods.frameResize(KendokuSolve.jfSolveKendoku, oldw, oldh, 500, 580);
/* 108 */             Op.setInt(Op.KE.KeW.ordinal(), KendokuSolve.jfSolveKendoku.getWidth(), Op.ke);
/* 109 */             Op.setInt(Op.KE.KeH.ordinal(), KendokuSolve.jfSolveKendoku.getHeight(), Op.ke);
/* 110 */             KendokuSolve.restoreFrame();
/*     */           }
/*     */         });
/*     */     
/* 114 */     jfSolveKendoku
/* 115 */       .addWindowListener(new WindowAdapter() {
/*     */           public void windowClosing(WindowEvent we) {
/* 117 */             if (Def.selecting)
/* 118 */               return;  Op.saveOptions("kendoku.opt", Op.ke);
/* 119 */             KendokuSolve.this.restoreIfDone();
/* 120 */             KendokuSolve.saveKendoku(Op.ke[Op.KE.KePuz.ordinal()]);
/* 121 */             CrosswordExpress.transfer(110, KendokuSolve.jfSolveKendoku);
/*     */           }
/*     */         });
/*     */     
/* 125 */     Methods.closeHelp();
/*     */ 
/*     */     
/* 128 */     this.solveThread = (() -> {
/*     */         for (int j = 0; j < Grid.ySz; j++) {
/*     */           for (int i = 0; i < Grid.xSz; i++) {
/*     */             if (Grid.sol[i][j] != Grid.letter[i][j])
/*     */               return; 
/*     */           } 
/*     */         }  Methods.congratulations(jfSolveKendoku);
/*     */       });
/* 136 */     jl1 = new JLabel(); jfSolveKendoku.add(jl1);
/* 137 */     jl2 = new JLabel(); jfSolveKendoku.add(jl2);
/*     */ 
/*     */     
/* 140 */     menuBar = new JMenuBar();
/* 141 */     menuBar.setBackground(Def.COLOR_MENUBAR);
/* 142 */     jfSolveKendoku.setJMenuBar(menuBar);
/*     */     
/* 144 */     this.menu = new JMenu("File");
/* 145 */     menuBar.add(this.menu);
/* 146 */     this.menuItem = new JMenuItem("Load a Puzzle");
/* 147 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(76, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 148 */     this.menu.add(this.menuItem);
/* 149 */     this.menuItem
/* 150 */       .addActionListener(ae -> {
/*     */           if (Def.building == 1) {
/*     */             return;
/*     */           }
/*     */           restoreIfDone();
/*     */           saveKendoku(Op.ke[Op.KE.KePuz.ordinal()]);
/*     */           new Select(jfSolveKendoku, "kendoku", "kendoku", Op.ke, Op.KE.KePuz.ordinal(), false);
/*     */         });
/* 158 */     this.menuItem = new JMenuItem("Quit Solving");
/* 159 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(81, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 160 */     this.menu.add(this.menuItem);
/* 161 */     this.menuItem
/* 162 */       .addActionListener(ae -> {
/*     */           Op.saveOptions("kendoku.opt", Op.ke);
/*     */           
/*     */           restoreIfDone();
/*     */           
/*     */           saveKendoku(Op.ke[Op.KE.KePuz.ordinal()]);
/*     */           
/*     */           CrosswordExpress.transfer(110, jfSolveKendoku);
/*     */         });
/* 171 */     this.menu = new JMenu("View");
/* 172 */     menuBar.add(this.menu);
/* 173 */     this.menuItem = new JMenuItem("Display Options");
/* 174 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(89, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 175 */     this.menu.add(this.menuItem);
/* 176 */     this.menuItem
/* 177 */       .addActionListener(ae -> {
/*     */           KendokuBuild.printOptions(jfSolveKendoku, "Display Options");
/*     */           
/*     */           restoreFrame();
/*     */         });
/* 182 */     this.menuItem = new JMenuItem("Toggle Assist Mode");
/* 183 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(77, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 184 */     this.menu.add(this.menuItem);
/* 185 */     this.menuItem
/* 186 */       .addActionListener(ae -> {
/*     */           this.assist = !this.assist;
/*     */           
/*     */           Def.dispGuideDigits = Boolean.valueOf(this.assist);
/*     */           
/*     */           restoreFrame();
/*     */         });
/* 193 */     this.menu = new JMenu("Tasks");
/* 194 */     menuBar.add(this.menu);
/*     */     
/* 196 */     ActionListener errorTimer = ae -> {
/*     */         Def.dispErrors = Boolean.valueOf(false);
/*     */         pp.repaint();
/*     */         this.myTimer.stop();
/*     */       };
/* 201 */     this.myTimer = new Timer(1500, errorTimer);
/*     */     
/* 203 */     this.menuItem = new JMenuItem("Undo");
/* 204 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(85, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 205 */     this.menu.add(this.menuItem);
/* 206 */     this.menuItem
/* 207 */       .addActionListener(ae -> {
/*     */           if (this.undoIndex > 0) {
/*     */             do {
/*     */               this.undoIndex--;
/*     */               
/*     */               Grid.xstatus[Grid.xCur = this.undoX[this.undoIndex]][Grid.yCur = this.undoY[this.undoIndex]][this.undoI[this.undoIndex]] = 1;
/*     */               
/*     */               Grid.sol[Grid.xCur][Grid.yCur] = 0;
/*     */             } while (this.undoS[this.undoIndex] == 0);
/*     */           }
/*     */           restoreFrame();
/*     */         });
/* 219 */     this.menuItem = new JMenuItem("Reveal One Number");
/* 220 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(79, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 221 */     this.menu.add(this.menuItem);
/* 222 */     this.menuItem
/* 223 */       .addActionListener(ae -> {
/*     */           if (Methods.noReveal == 0) {
/*     */             char ch = (char)Grid.sol[Grid.xCur][Grid.yCur];
/*     */             
/*     */             if (ch == '\000') {
/*     */               int i = Grid.letter[Grid.xCur][Grid.yCur] - 49;
/*     */               
/*     */               Grid.sol[Grid.xCur][Grid.yCur] = Grid.letter[Grid.xCur][Grid.yCur];
/*     */               updateUndo(Grid.xCur, Grid.yCur, i);
/*     */               this.undoS[this.undoIndex - 1] = 1;
/*     */               updateSolveStatus(Grid.xCur, Grid.yCur, i);
/*     */             } 
/*     */           } else {
/*     */             Methods.noReveal(jfSolveKendoku);
/*     */           } 
/*     */           restoreFrame();
/*     */         });
/* 240 */     this.menuItem = new JMenuItem("Reveal Errors");
/* 241 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(69, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 242 */     this.menu.add(this.menuItem);
/* 243 */     this.menuItem
/* 244 */       .addActionListener(ae -> {
/*     */           if (Methods.noErrors == 0) {
/*     */             this.myTimer.start();
/*     */             
/*     */             Def.dispErrors = Boolean.valueOf(true);
/*     */           } else {
/*     */             Methods.noReveal(jfSolveKendoku);
/*     */           } 
/*     */           
/*     */           pp.repaint();
/*     */         });
/* 255 */     this.menuItem = new JMenuItem("Reveal Solution");
/* 256 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(83, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 257 */     this.menu.add(this.menuItem);
/* 258 */     this.menuItem
/* 259 */       .addActionListener(ae -> {
/*     */           if (Methods.noReveal == 0) {
/*     */             for (int j = 0; j < Grid.ySz; j++) {
/*     */               for (int i = 0; i < Grid.xSz; i++) {
/*     */                 Grid.sol[i][j] = Grid.letter[i][j];
/*     */               }
/*     */             } 
/*     */             recalculateStatus();
/*     */             this.undoIndex = 0;
/*     */           } else {
/*     */             Methods.noReveal(jfSolveKendoku);
/*     */           } 
/*     */           restoreFrame();
/*     */         });
/* 273 */     this.menuItem = new JMenuItem("Begin again");
/* 274 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(66, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 275 */     this.menu.add(this.menuItem);
/* 276 */     this.menuItem
/* 277 */       .addActionListener(ae -> {
/*     */           Methods.clearGrid(Grid.sol);
/*     */           
/*     */           recalculateStatus();
/*     */           
/*     */           this.undoIndex = 0;
/*     */           
/*     */           restoreFrame();
/*     */         });
/* 286 */     this.menu = new JMenu("Help");
/* 287 */     menuBar.add(this.menu);
/* 288 */     this.menuItem = new JMenuItem("Kendoku Help");
/* 289 */     this.menu.add(this.menuItem);
/* 290 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(72, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 291 */     this.menuItem
/* 292 */       .addActionListener(ae -> Methods.cweHelp(jfSolveKendoku, null, "Solving Kendoku Puzzles", this.kendokuSolve));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 298 */     loadKendoku(Op.ke[Op.KE.KePuz.ordinal()]);
/* 299 */     recalculateStatus();
/* 300 */     this.undoIndex = 0;
/*     */     
/* 302 */     pp = new KendokuSolvePP(0, 37);
/* 303 */     jfSolveKendoku.add(pp);
/*     */     
/* 305 */     pp
/* 306 */       .addMouseListener(new MouseAdapter() {
/*     */           public void mousePressed(MouseEvent e) {
/* 308 */             KendokuSolve.this.updateGrid(e);
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 313 */     pp
/* 314 */       .addMouseMotionListener(new MouseAdapter() {
/*     */           public void mouseMoved(MouseEvent e) {
/* 316 */             if (Def.isMac) {
/* 317 */               KendokuSolve.jfSolveKendoku.setResizable((KendokuSolve.jfSolveKendoku.getWidth() - e.getX() < 15 && KendokuSolve.jfSolveKendoku
/* 318 */                   .getHeight() - e.getY() < 95));
/*     */             }
/*     */           }
/*     */         });
/*     */     
/* 323 */     jfSolveKendoku
/* 324 */       .addKeyListener(new KeyAdapter() {
/*     */           public void keyPressed(KeyEvent e) {
/* 326 */             KendokuSolve.this.handleKeyPressed(e);
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 331 */     restoreFrame();
/*     */   }
/*     */   
/*     */   static void restoreFrame() {
/* 335 */     jfSolveKendoku.setVisible(true);
/* 336 */     Insets insets = jfSolveKendoku.getInsets();
/* 337 */     panelW = jfSolveKendoku.getWidth() - insets.left + insets.right;
/* 338 */     panelH = jfSolveKendoku.getHeight() - insets.top + insets.bottom + 37 + menuBar.getHeight();
/* 339 */     pp.setSize(panelW, panelH);
/* 340 */     jfSolveKendoku.requestFocusInWindow();
/* 341 */     pp.repaint();
/* 342 */     Methods.infoPanel(jl1, jl2, "Solve Kendoku", "Puzzle : " + Op.ke[Op.KE.KePuz.ordinal()], panelW);
/*     */   }
/*     */   
/*     */   static void setSizesAndOffsets(int x, int y, int width, int height, int inset) {
/* 346 */     int i = (width - inset) / Grid.xSz;
/* 347 */     int j = (height - inset) / Grid.ySz;
/* 348 */     Grid.xCell = Grid.yCell = (i < j) ? i : j;
/* 349 */     Grid.xOrg = x + ((Def.puzzleMode == 8) ? ((width - Grid.xSz * Grid.xCell) / 2) : 10);
/* 350 */     Grid.yOrg = y + ((Def.puzzleMode == 8) ? ((height - Grid.ySz * Grid.yCell) / 2) : 10);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void saveKendoku(String kendokuName) {
/*     */     try {
/* 358 */       DataOutputStream dataOut = new DataOutputStream(new FileOutputStream("kendoku/" + kendokuName));
/* 359 */       dataOut.writeInt(Grid.xSz);
/* 360 */       dataOut.writeInt(Grid.ySz);
/* 361 */       dataOut.writeByte(Methods.noReveal);
/* 362 */       dataOut.writeByte(Methods.noErrors);
/* 363 */       for (int i = 0; i < 54; i++)
/* 364 */         dataOut.writeByte(0); 
/* 365 */       for (int j = 0; j < Grid.ySz; j++) {
/* 366 */         for (int k = 0; k < Grid.xSz; k++) {
/* 367 */           dataOut.writeInt(Grid.mode[k][j]);
/* 368 */           dataOut.writeInt(Grid.sol[k][j]);
/* 369 */           dataOut.writeInt(Grid.letter[k][j]);
/*     */         } 
/* 371 */       }  dataOut.writeUTF(Methods.puzzleTitle);
/* 372 */       dataOut.writeUTF(Methods.author);
/* 373 */       dataOut.writeUTF(Methods.copyright);
/* 374 */       dataOut.writeUTF(Methods.puzzleNumber);
/* 375 */       dataOut.writeUTF(Methods.puzzleNotes);
/* 376 */       dataOut.close();
/*     */     }
/* 378 */     catch (IOException exc) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void loadKendoku(String kendokuName) {
/*     */     try {
/* 386 */       File fl = new File("kendoku/" + kendokuName);
/* 387 */       if (!fl.exists()) {
/* 388 */         fl = new File("kendoku/");
/* 389 */         String[] s = fl.list(); int k;
/* 390 */         for (k = 0; k < s.length && (
/* 391 */           s[k].lastIndexOf(".kendoku") == -1 || s[k].charAt(0) == '.'); k++);
/*     */         
/* 393 */         kendokuName = s[k];
/* 394 */         Op.ke[Op.KE.KePuz.ordinal()] = kendokuName;
/*     */       } 
/*     */       
/* 397 */       DataInputStream dataIn = new DataInputStream(new FileInputStream("kendoku/" + kendokuName));
/* 398 */       Grid.xSz = dataIn.readInt();
/* 399 */       Grid.ySz = dataIn.readInt();
/* 400 */       Methods.noReveal = dataIn.readByte();
/* 401 */       Methods.noErrors = dataIn.readByte(); int i;
/* 402 */       for (i = 0; i < 54; i++)
/* 403 */         dataIn.readByte(); 
/* 404 */       for (int j = 0; j < Grid.ySz; j++) {
/* 405 */         for (i = 0; i < Grid.xSz; i++) {
/* 406 */           Grid.mode[i][j] = dataIn.readInt();
/* 407 */           Grid.sol[i][j] = dataIn.readInt();
/* 408 */           Grid.letter[i][j] = dataIn.readInt();
/*     */         } 
/* 410 */       }  Methods.puzzleTitle = dataIn.readUTF();
/* 411 */       Methods.author = dataIn.readUTF();
/* 412 */       Methods.copyright = dataIn.readUTF();
/* 413 */       Methods.puzzleNumber = dataIn.readUTF();
/* 414 */       Methods.puzzleNotes = dataIn.readUTF();
/* 415 */       dataIn.close();
/*     */     }
/* 417 */     catch (IOException exc) {}
/*     */   }
/*     */ 
/*     */   
/*     */   static void drawKendoku(Graphics2D g2, int[][] puzzleArray) {
/* 422 */     String op = "+x-/123456789";
/*     */ 
/*     */     
/* 425 */     Stroke normalStroke = new BasicStroke(Grid.xCell / 25.0F, 2, 2);
/* 426 */     Stroke wideStroke = new BasicStroke(Grid.xCell / 12.0F, 2, 2);
/* 427 */     g2.setStroke(normalStroke);
/*     */     
/* 429 */     RenderingHints rh = g2.getRenderingHints();
/* 430 */     rh.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
/* 431 */     g2.setRenderingHints(rh);
/*     */     
/*     */     int j;
/* 434 */     for (j = 0; j < Grid.ySz; j++) {
/* 435 */       for (int i = 0; i < Grid.xSz; i++) {
/* 436 */         if (Grid.mode[i][j] != 2) {
/* 437 */           int theColor; if (Def.dispWithColor.booleanValue()) {
/* 438 */             if (Def.dispErrors.booleanValue() && Grid.sol[i][j] != 0 && Grid.sol[i][j] != Grid.letter[i][j]) {
/* 439 */               theColor = Op.getColorInt(Op.KE.KeError.ordinal(), Op.ke);
/*     */             } else {
/*     */               
/* 442 */               theColor = Op.getColorInt(Op.KE.KeCell.ordinal(), Op.ke);
/*     */             } 
/*     */           } else {
/* 445 */             theColor = 16777215;
/* 446 */           }  g2.setColor(new Color(theColor));
/* 447 */           g2.fillRect(Grid.xOrg + i * Grid.xCell, Grid.yOrg + j * Grid.yCell, Grid.xCell, Grid.yCell);
/*     */         } 
/*     */       } 
/* 450 */     }  Stroke thisStroke = new BasicStroke((Grid.xCell < 50) ? 1.0F : (Grid.xCell / 50)); int k;
/* 451 */     for (k = 0; k < 2; k++) {
/* 452 */       for (j = 0; j < Grid.ySz; j++) {
/* 453 */         for (int i = 0; i < Grid.xSz; i++) {
/* 454 */           int x = Grid.xOrg + i * Grid.xCell, y = Grid.yOrg + j * Grid.yCell;
/* 455 */           if (k == 1 && Grid.mode[i][j] > 65536) {
/* 456 */             g2.setFont(new Font(Op.ke[Op.KE.KeHintFont.ordinal()], 0, Grid.yCell / 4));
/* 457 */             g2.setColor(new Color(Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.KE.KeHint.ordinal(), Op.ke) : 0));
/* 458 */             FontMetrics fontMetrics = g2.getFontMetrics();
/* 459 */             g2.drawString("" + (Grid.mode[i][j] / 65536) + op.charAt(Grid.mode[i][j] / 256 % 256), x + Grid.xCell / 10, y + fontMetrics.getAscent());
/*     */           }  int c;
/* 461 */           for (c = 3; c < 768; c *= 4) {
/* 462 */             int v = ((Grid.mode[i][j] & c) == 0) ? 0 : 1;
/* 463 */             g2.setColor(new Color(Def.dispWithColor.booleanValue() ? ((v == 0) ? Op.getColorInt(Op.KE.KeGrid.ordinal(), Op.ke) : Op.getColorInt(Op.KE.KeLine.ordinal(), Op.ke)) : 0));
/* 464 */             g2.setStroke((v == 0) ? thisStroke : wideStroke);
/* 465 */             if (v == k)
/* 466 */               switch (c) { case 3:
/* 467 */                   g2.drawLine(x, y, x + Grid.xCell, y); break;
/* 468 */                 case 12: g2.drawLine(x + Grid.xCell, y, x + Grid.xCell, y + Grid.yCell); break;
/* 469 */                 case 48: g2.drawLine(x, y + Grid.yCell, x + Grid.xCell, y + Grid.yCell); break;
/* 470 */                 case 192: g2.drawLine(x, y + Grid.yCell, x, y); break; }  
/*     */           } 
/*     */         } 
/*     */       } 
/* 474 */     }  g2.setColor(new Color(Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.KE.KeLine.ordinal(), Op.ke) : 0));
/* 475 */     g2.setStroke(wideStroke);
/* 476 */     g2.drawRect(Grid.xOrg, Grid.yOrg, Grid.xSz * Grid.xCell, Grid.ySz * Grid.yCell);
/*     */     
/* 478 */     if (Def.dispCursor.booleanValue()) {
/* 479 */       int c; for (c = 3; c < 768; c *= 4) {
/* 480 */         int x = Grid.xOrg + Grid.xCur * Grid.xCell, y = Grid.yOrg + Grid.yCur * Grid.yCell;
/* 481 */         int v = ((Grid.mode[Grid.xCur][Grid.yCur] & c) == 0) ? 0 : 1;
/* 482 */         g2.setColor(Def.COLOR_RED);
/* 483 */         g2.setStroke((v == 0) ? thisStroke : wideStroke);
/* 484 */         switch (c) { case 3:
/* 485 */             g2.drawLine(x, y, x + Grid.xCell, y); break;
/* 486 */           case 12: g2.drawLine(x + Grid.xCell, y, x + Grid.xCell, y + Grid.yCell); break;
/* 487 */           case 48: g2.drawLine(x, y + Grid.yCell, x + Grid.xCell, y + Grid.yCell); break;
/* 488 */           case 192: g2.drawLine(x, y + Grid.yCell, x, y); break; }
/*     */       
/*     */       } 
/*     */     } 
/* 492 */     g2.setStroke(normalStroke);
/*     */ 
/*     */     
/* 495 */     g2.setFont(new Font(Op.ke[Op.KE.KeFont.ordinal()], 0, 8 * Grid.yCell / 10));
/* 496 */     FontMetrics fm = g2.getFontMetrics();
/* 497 */     g2.setColor(new Color(Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.KE.KeNumber.ordinal(), Op.ke) : 0));
/* 498 */     for (j = 0; j < Grid.ySz; j++) {
/* 499 */       for (int i = 0; i < Grid.xSz; i++) {
/* 500 */         char ch = (char)puzzleArray[i][j];
/* 501 */         if (Character.isDigit(ch)) {
/* 502 */           int w = fm.stringWidth("" + ch);
/* 503 */           g2.drawString("" + ch, Grid.xOrg + i * Grid.xCell + (Grid.xCell - w) / 2, Grid.yOrg + j * Grid.yCell + (Grid.yCell + fm.getAscent() - fm.getDescent()) / 2);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 508 */     if (Def.dispGuideDigits.booleanValue()) {
/* 509 */       g2.setFont(new Font(Op.ke[Op.KE.KeGuideFont.ordinal()], 0, Grid.yCell / 5));
/* 510 */       g2.setColor(new Color(Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.KE.KeGuide.ordinal(), Op.ke) : 0));
/* 511 */       fm = g2.getFontMetrics();
/* 512 */       for (j = 0; j < Grid.ySz; j++) {
/* 513 */         for (int i = 0; i < Grid.xSz; i++) {
/* 514 */           if (Grid.sol[i][j] == 0)
/* 515 */             for (k = 0; k < Grid.xSz; k++) {
/* 516 */               if (Grid.xstatus[i][j][k] != 0) {
/* 517 */                 int w = (Grid.xCell / 3 - fm.stringWidth("" + (k + 1))) / 2;
/* 518 */                 g2.drawString("" + (k + 1), Grid.xOrg + i * Grid.xCell + Grid.xCell * k % 3 / 3 + w, Grid.yOrg + j * Grid.yCell + Grid.yCell * (1 + k / 3) / 4 + fm.getAscent());
/*     */               } 
/*     */             }  
/*     */         } 
/*     */       } 
/* 523 */     }  g2.setStroke(new BasicStroke(1.0F));
/*     */   }
/*     */   
/*     */   void updateUndo(int x, int y, int i) {
/* 527 */     if (Grid.xstatus[x][y][i] > 0) {
/* 528 */       Grid.xstatus[x][y][i] = 0;
/* 529 */       this.undoX[this.undoIndex] = x;
/* 530 */       this.undoY[this.undoIndex] = y;
/* 531 */       this.undoI[this.undoIndex] = i;
/* 532 */       this.undoS[this.undoIndex++] = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void updateSolveStatus(int x, int y, int i) {
/* 539 */     for (int j = 0; j < Grid.xSz; j++) {
/* 540 */       updateUndo(x, y, j);
/* 541 */       updateUndo(j, y, i);
/* 542 */       updateUndo(x, j, i);
/*     */     } 
/*     */   }
/*     */   
/*     */   void updateStatus(int x, int y, int i) {
/* 547 */     for (int j = 0; j < Grid.xSz; j++) {
/* 548 */       Grid.xstatus[x][j][i] = 0; Grid.xstatus[j][y][i] = 0; Grid.xstatus[x][y][j] = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void recalculateStatus() {
/* 555 */     for (int z = 0; z < Grid.ySz; z++) {
/* 556 */       for (int i = 0; i < Grid.xSz; i++) {
/* 557 */         for (int x = 0; x < Grid.xSz; x++)
/* 558 */           Grid.xstatus[x][i][z] = 1; 
/*     */       } 
/* 560 */     }  for (int y = 0; y < Grid.ySz; y++) {
/* 561 */       for (int x = 0; x < Grid.xSz; x++) {
/* 562 */         int cIndex = Grid.sol[x][y] - 49;
/* 563 */         if (cIndex >= 0)
/* 564 */           updateStatus(x, y, cIndex); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   void restoreIfDone() {
/* 569 */     for (int j = 0; j < Grid.ySz; j++) {
/* 570 */       for (int i = 0; i < Grid.xSz; i++)
/* 571 */       { if (Grid.sol[i][j] == 0)
/*     */           return;  } 
/* 573 */     }  Methods.clearGrid(Grid.sol);
/*     */   }
/*     */   
/*     */   void updateGrid(MouseEvent e) {
/* 577 */     int x = e.getX(), y = e.getY();
/*     */     
/* 579 */     if (x < Grid.xOrg || y < Grid.yOrg)
/*     */       return; 
/* 581 */     int i = (x - Grid.xOrg) / Grid.xCell;
/* 582 */     int j = (y - Grid.yOrg) / Grid.yCell;
/* 583 */     if (i >= Grid.xSz || j >= Grid.ySz)
/*     */       return; 
/* 585 */     Grid.xCur = i;
/* 586 */     Grid.yCur = j;
/*     */     
/* 588 */     if (!this.assist) { restoreFrame(); return; }
/* 589 */      i = (x - Grid.xOrg - i * Grid.xCell) / Grid.xCell / 3;
/* 590 */     j = (y - Grid.yOrg - j * Grid.yCell) / Grid.yCell / 4 - 1;
/* 591 */     if (j < 0)
/* 592 */       return;  int val = i + 3 * j;
/* 593 */     if (Grid.xstatus[Grid.xCur][Grid.yCur][val] == 0) {
/* 594 */       restoreFrame(); return;
/*     */     } 
/* 596 */     updateUndo(Grid.xCur, Grid.yCur, val);
/* 597 */     this.undoS[this.undoIndex - 1] = 1;
/*     */     int count;
/* 599 */     for (x = count = 0; x < Grid.xSz; x++) {
/* 600 */       if (Grid.xstatus[Grid.xCur][Grid.yCur][x] != 0)
/* 601 */         count++; 
/* 602 */     }  if (count == 0) {
/* 603 */       Grid.sol[Grid.xCur][Grid.yCur] = val + 49;
/* 604 */       updateSolveStatus(Grid.xCur, Grid.yCur, val);
/* 605 */       (new Thread(this.solveThread)).start();
/*     */     } 
/* 607 */     restoreFrame();
/*     */   } void handleKeyPressed(KeyEvent e) {
/*     */     char ch;
/*     */     int i;
/* 611 */     if (e.isAltDown())
/* 612 */       return;  switch (e.getKeyCode()) { case 38:
/* 613 */         if (Grid.yCur > 0) Grid.yCur--;  break;
/* 614 */       case 40: if (Grid.yCur < Grid.ySz - 1) Grid.yCur++;  break;
/* 615 */       case 37: if (Grid.xCur > 0) Grid.xCur--;  break;
/* 616 */       case 39: if (Grid.xCur < Grid.xSz - 1) Grid.xCur++;  break;
/* 617 */       case 36: Grid.xCur = 0; break;
/* 618 */       case 35: Grid.xCur = Grid.xSz - 1; break;
/* 619 */       case 33: Grid.yCur = 0; break;
/* 620 */       case 34: Grid.yCur = Grid.ySz - 1; break;
/*     */       default:
/* 622 */         ch = e.getKeyChar();
/* 623 */         i = ch - 49;
/* 624 */         if (i >= 0 && i < Grid.xSz) {
/* 625 */           updateUndo(Grid.xCur, Grid.yCur, i);
/* 626 */           this.undoS[this.undoIndex - 1] = 1;
/* 627 */           Grid.sol[Grid.xCur][Grid.yCur] = ch;
/* 628 */           updateSolveStatus(Grid.xCur, Grid.yCur, i);
/* 629 */           (new Thread(this.solveThread)).start();
/*     */         } 
/*     */         break; }
/*     */     
/* 633 */     restoreFrame();
/*     */   }
/*     */ }


/* Location:              C:\Users\mustapha\Desktop\Crossword-Express.jar!\crosswordexpress\KendokuSolve.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */