/*     */ package crosswordexpress;
/*     */ import java.awt.Color;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Insets;
/*     */ import java.awt.RenderingHints;
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
/*     */ public final class FutoshikiSolve extends JPanel {
/*     */   static JFrame jfSolveFutoshiki;
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
/*  33 */   String futoshikiSolve = "<div><b>Futoshiki</b> puzzles are built on square grids of typically 5x5 cells (although puzzles having sizes in the range 4x4 up to 8x8 can also be made). To solve them you must place numbers into the puzzle cells in such a way that each row and column contains each of the digits less than the size of the puzzle. In this respect they are similar to Sudoku puzzles. To give the solver a start, there will normally be several numbers already in the puzzle.<p/>In addition, a few pairs of adjacent cells will have <b>&lt;</b> and <b>&gt;</b> symbols inserted between them to indicate the relative sizes of the numbers within those cells.<p/>Any character which is typed at the keyboard will be placed into the focus cell (outlined in red), provided that it is not greater than the dimension of the puzzle.<p/>The location of the focus cell may be shifted by means of the cursor control keys or by pointing and clicking with the mouse.<p/>Under default conditions, the program displays in each unsolved cell, the set of candidate symbols which might legally be placed into that cell. Whenever a solution symbol is placed into one of the cells, the list of candidate symbols in other cells is automatically updated.<p/>By the application of some logical reasoning, you will see that certain of the listed candidate symbols cannot possibly be a solution for the cell in which they are shown. Such candidates may be removed by pointing and clicking with the mouse. When the last candidate symbol is removed from a cell, it becomes the solution symbol for that cell.<br/><br/></div><span class='m'>Menu Functions</span><ul><li/><span class='s'>File Menu</span><ul><li/><span>Load a Puzzle</span><br/>Use this option to choose the puzzle you want to solve from the pool of FUTOSHIKI puzzles currently available on your computer.<p/><li/><span>Quit Solving</span><br/>Returns you to the FUTOSHIKI Construction screen.</ul><li/><span class='s'>View Menu</span><ul><li/><span>Display Options</span><br/>This leads you to a dialog box in which you can change the colors of various elements within the puzzle, and control the fonts which will be used for the puzzle's text components. You can also decide if printing of the puzzle will be done in black and white or in color.<p/><li/><span>Toggle Assist Mode</span><br/>If you prefer to solve these puzzles without the assistance of the candidate symbols, they can be turned on and off using this option.</ul><li/><span class='s'>Tasks Menu</span><ul><li/><span>Undo</span><br/>This option will undo in reverse order, each of the operations you have done on the puzzle.<p/><li/><span>Reveal One Number</span><br/>If you need a little help to get started, this option will place the correct number into the current focus cell.<p/><li/><span>Reveal Errors</span><br/>If you think you may have made errors, this option will show you where they are by highlighting them for a period of 1.5 seconds.<p/><li/><span>Reveal Solution</span><br/>The entire solution can be seen by selecting this option.<p/><li/><span>Begin Again</span><br/>You can restart the entire solution process at any time by selecting this option.</ul><li/><span class='s'>Help Menu</span><ul><li/><span>Futoshiki Help</span><br/>Displays the Help screen which you are now reading.</ul></ul></body>";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   FutoshikiSolve(JFrame jf) {
/*  86 */     this.memMode = Def.puzzleMode;
/*  87 */     Def.puzzleMode = 81;
/*  88 */     Def.dispCursor = Boolean.valueOf(true); Def.dispGuideDigits = Boolean.valueOf(true);
/*     */ 
/*     */     
/*  91 */     Grid.clearGrid();
/*     */     
/*  93 */     jfSolveFutoshiki = new JFrame("Futoshiki");
/*  94 */     jfSolveFutoshiki.setSize(Op.getInt(Op.FU.FuW.ordinal(), Op.fu), Op.getInt(Op.FU.FuH.ordinal(), Op.fu));
/*  95 */     int frameX = (jf.getX() + jfSolveFutoshiki.getWidth() > Methods.scrW) ? (Methods.scrW - jfSolveFutoshiki.getWidth() - 10) : jf.getX();
/*  96 */     jfSolveFutoshiki.setLocation(frameX, jf.getY());
/*  97 */     jfSolveFutoshiki.setLayout((LayoutManager)null);
/*  98 */     jfSolveFutoshiki.setDefaultCloseOperation(0);
/*  99 */     jfSolveFutoshiki
/* 100 */       .addComponentListener(new ComponentAdapter() {
/*     */           public void componentResized(ComponentEvent ce) {
/* 102 */             int oldw = Op.getInt(Op.FU.FuW.ordinal(), Op.fu);
/* 103 */             int oldh = Op.getInt(Op.FU.FuH.ordinal(), Op.fu);
/* 104 */             Methods.frameResize(FutoshikiSolve.jfSolveFutoshiki, oldw, oldh, 500, 580);
/* 105 */             Op.setInt(Op.FU.FuW.ordinal(), FutoshikiSolve.jfSolveFutoshiki.getWidth(), Op.fu);
/* 106 */             Op.setInt(Op.FU.FuH.ordinal(), FutoshikiSolve.jfSolveFutoshiki.getHeight(), Op.fu);
/* 107 */             FutoshikiSolve.restoreFrame();
/*     */           }
/*     */         });
/*     */     
/* 111 */     jfSolveFutoshiki
/* 112 */       .addWindowListener(new WindowAdapter() {
/*     */           public void windowClosing(WindowEvent we) {
/* 114 */             if (Def.selecting)
/* 115 */               return;  Op.saveOptions("futoshiki.opt", Op.fu);
/* 116 */             FutoshikiSolve.this.restoreIfDone();
/* 117 */             FutoshikiSolve.saveFutoshiki(Op.fu[Op.FU.FuPuz.ordinal()]);
/* 118 */             CrosswordExpress.transfer(80, FutoshikiSolve.jfSolveFutoshiki);
/*     */           }
/*     */         });
/*     */     
/* 122 */     Methods.closeHelp();
/*     */ 
/*     */     
/* 125 */     this.solveThread = (() -> {
/*     */         for (int j = 0; j < Grid.ySz; j++) {
/*     */           for (int i = 0; i < Grid.xSz; i++) {
/*     */             if (Grid.sol[i][j] != Grid.letter[i][j])
/*     */               return; 
/*     */           } 
/*     */         }  Methods.congratulations(jfSolveFutoshiki);
/*     */       });
/* 133 */     jl1 = new JLabel(); jfSolveFutoshiki.add(jl1);
/* 134 */     jl2 = new JLabel(); jfSolveFutoshiki.add(jl2);
/*     */ 
/*     */     
/* 137 */     menuBar = new JMenuBar();
/* 138 */     menuBar.setBackground(Def.COLOR_MENUBAR);
/* 139 */     jfSolveFutoshiki.setJMenuBar(menuBar);
/*     */     
/* 141 */     this.menu = new JMenu("File");
/* 142 */     menuBar.add(this.menu);
/* 143 */     this.menuItem = new JMenuItem("Load a Puzzle");
/* 144 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(76, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 145 */     this.menu.add(this.menuItem);
/* 146 */     this.menuItem
/* 147 */       .addActionListener(ae -> {
/*     */           if (Def.building == 1) {
/*     */             return;
/*     */           }
/*     */           restoreIfDone();
/*     */           saveFutoshiki(Op.fu[Op.FU.FuPuz.ordinal()]);
/*     */           new Select(jfSolveFutoshiki, "futoshiki", "futoshiki", Op.fu, Op.FU.FuPuz.ordinal(), false);
/*     */         });
/* 155 */     this.menuItem = new JMenuItem("Quit Solving");
/* 156 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(81, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 157 */     this.menu.add(this.menuItem);
/* 158 */     this.menuItem
/* 159 */       .addActionListener(ae -> {
/*     */           Op.saveOptions("futoshiki.opt", Op.fu);
/*     */           
/*     */           restoreIfDone();
/*     */           
/*     */           saveFutoshiki(Op.fu[Op.FU.FuPuz.ordinal()]);
/*     */           
/*     */           CrosswordExpress.transfer(80, jfSolveFutoshiki);
/*     */         });
/* 168 */     this.menu = new JMenu("View");
/* 169 */     menuBar.add(this.menu);
/* 170 */     this.menuItem = new JMenuItem("Display Options");
/* 171 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(89, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 172 */     this.menu.add(this.menuItem);
/* 173 */     this.menuItem
/* 174 */       .addActionListener(ae -> {
/*     */           FutoshikiBuild.printOptions(jfSolveFutoshiki, "Display Options");
/*     */           
/*     */           restoreFrame();
/*     */         });
/*     */     
/* 180 */     this.menuItem = new JMenuItem("Toggle Assist Mode");
/* 181 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(77, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 182 */     this.menu.add(this.menuItem);
/* 183 */     this.menuItem
/* 184 */       .addActionListener(ae -> {
/*     */           this.assist = !this.assist;
/*     */           
/*     */           Def.dispGuideDigits = Boolean.valueOf(this.assist);
/*     */           
/*     */           restoreFrame();
/*     */         });
/*     */     
/* 192 */     this.menu = new JMenu("Tasks");
/* 193 */     menuBar.add(this.menu);
/* 194 */     this.menuItem = new JMenuItem("Undo");
/* 195 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(85, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 196 */     this.menu.add(this.menuItem);
/* 197 */     this.menuItem
/* 198 */       .addActionListener(ae -> {
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
/* 210 */     this.menuItem = new JMenuItem("Reveal One Number");
/* 211 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(79, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 212 */     this.menu.add(this.menuItem);
/* 213 */     this.menuItem
/* 214 */       .addActionListener(ae -> {
/*     */           if (Methods.noReveal == 0) {
/*     */             char ch = (char)Grid.sol[Grid.xCur][Grid.yCur];
/*     */             
/*     */             if (ch == '\000') {
/*     */               int i = Grid.letter[Grid.xCur][Grid.yCur] - 49;
/*     */               
/*     */               Grid.sol[Grid.xCur][Grid.yCur] = Grid.letter[Grid.xCur][Grid.yCur];
/*     */               
/*     */               updateUndo(Grid.xCur, Grid.yCur, i);
/*     */               this.undoS[this.undoIndex - 1] = 1;
/*     */               updateSolveStatus(Grid.xCur, Grid.yCur, i);
/*     */             } 
/*     */           } else {
/*     */             Methods.noReveal(jfSolveFutoshiki);
/*     */           } 
/*     */           restoreFrame();
/*     */         });
/* 232 */     ActionListener errorTimer = ae -> {
/*     */         Def.dispErrors = Boolean.valueOf(false);
/*     */         pp.repaint();
/*     */         this.myTimer.stop();
/*     */       };
/* 237 */     this.myTimer = new Timer(1500, errorTimer);
/*     */     
/* 239 */     this.menuItem = new JMenuItem("Reveal Errors");
/* 240 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(69, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 241 */     this.menu.add(this.menuItem);
/* 242 */     this.menuItem
/* 243 */       .addActionListener(ae -> {
/*     */           if (Methods.noErrors == 0) {
/*     */             this.myTimer.start();
/*     */             
/*     */             Def.dispErrors = Boolean.valueOf(true);
/*     */           } else {
/*     */             Methods.noReveal(jfSolveFutoshiki);
/*     */           } 
/*     */           
/*     */           pp.repaint();
/*     */         });
/* 254 */     this.menuItem = new JMenuItem("Reveal Solution");
/* 255 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(83, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 256 */     this.menu.add(this.menuItem);
/* 257 */     this.menuItem
/* 258 */       .addActionListener(ae -> {
/*     */           if (Methods.noReveal == 0) {
/*     */             for (int j = 0; j < Grid.ySz; j++) {
/*     */               for (int i = 0; i < Grid.xSz; i++) {
/*     */                 Grid.sol[i][j] = Grid.letter[i][j];
/*     */               }
/*     */             } 
/*     */             recalculateStatus();
/*     */             this.undoIndex = 0;
/*     */           } else {
/*     */             Methods.noReveal(jfSolveFutoshiki);
/*     */           } 
/*     */           restoreFrame();
/*     */         });
/* 272 */     this.menuItem = new JMenuItem("Begin again");
/* 273 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(66, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 274 */     this.menu.add(this.menuItem);
/* 275 */     this.menuItem
/* 276 */       .addActionListener(ae -> {
/*     */           clearSolution();
/*     */           
/*     */           recalculateStatus();
/*     */           
/*     */           this.undoIndex = 0;
/*     */           
/*     */           restoreFrame();
/*     */         });
/* 285 */     this.menu = new JMenu("Help");
/* 286 */     menuBar.add(this.menu);
/* 287 */     this.menuItem = new JMenuItem("Futoshiki Help");
/* 288 */     this.menu.add(this.menuItem);
/* 289 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(72, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 290 */     this.menuItem
/* 291 */       .addActionListener(ae -> Methods.cweHelp(jfSolveFutoshiki, null, "Solving Futoshiki Puzzles", this.futoshikiSolve));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 297 */     loadFutoshiki(Op.fu[Op.FU.FuPuz.ordinal()]);
/* 298 */     recalculateStatus();
/* 299 */     this.undoIndex = 0;
/* 300 */     pp = new FutoshikiSolvePP(0, 37);
/* 301 */     jfSolveFutoshiki.add(pp);
/*     */     
/* 303 */     jfSolveFutoshiki
/* 304 */       .addKeyListener(new KeyAdapter() {
/*     */           public void keyPressed(KeyEvent e) {
/* 306 */             FutoshikiSolve.this.handleKeyPressed(e);
/* 307 */             FutoshikiSolve.pp.repaint();
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 312 */     pp
/* 313 */       .addMouseMotionListener(new MouseAdapter() {
/*     */           public void mouseMoved(MouseEvent e) {
/* 315 */             if (Def.isMac) {
/* 316 */               FutoshikiSolve.jfSolveFutoshiki.setResizable((FutoshikiSolve.jfSolveFutoshiki.getWidth() - e.getX() < 15 && FutoshikiSolve.jfSolveFutoshiki
/* 317 */                   .getHeight() - e.getY() < 95));
/*     */             }
/*     */           }
/*     */         });
/*     */     
/* 322 */     pp
/* 323 */       .addMouseListener(new MouseAdapter() {
/*     */           public void mousePressed(MouseEvent e) {
/* 325 */             FutoshikiSolve.this.updateGrid(e);
/* 326 */             FutoshikiSolve.pp.repaint();
/*     */           }
/*     */         });
/*     */     
/* 330 */     restoreFrame();
/*     */   }
/*     */   
/*     */   static void restoreFrame() {
/* 334 */     jfSolveFutoshiki.setVisible(true);
/* 335 */     Insets insets = jfSolveFutoshiki.getInsets();
/* 336 */     panelW = jfSolveFutoshiki.getWidth() - insets.left + insets.right;
/* 337 */     panelH = jfSolveFutoshiki.getHeight() - insets.top + insets.bottom + 37 + menuBar.getHeight();
/* 338 */     pp.setSize(panelW, panelH);
/*     */ 
/*     */     
/* 341 */     jfSolveFutoshiki.requestFocusInWindow();
/* 342 */     pp.repaint();
/* 343 */     Methods.infoPanel(jl1, jl2, "Solve Futoshiki", "Puzzle : " + Op.fu[Op.FU.FuPuz.ordinal()], panelW);
/*     */   }
/*     */   
/*     */   static void setSizesAndOffsets(int x, int y, int width, int height, int inset) {
/* 347 */     int v1 = (int)((width - inset) / (Grid.xSz + 0.2D));
/* 348 */     int v2 = (int)((height - inset) / (Grid.ySz + 0.2D));
/* 349 */     Grid.xCell = Grid.yCell = (v1 < v2) ? v1 : v2;
/* 350 */     Grid.xOrg = x + (width + Grid.xCell / 3 - Grid.xSz * Grid.xCell) / 2;
/* 351 */     Grid.yOrg = y + (height + Grid.yCell / 3 - Grid.ySz * Grid.yCell) / 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void saveFutoshiki(String futoshikiName) {
/*     */     try {
/* 357 */       DataOutputStream dataOut = new DataOutputStream(new FileOutputStream("futoshiki/" + futoshikiName));
/* 358 */       dataOut.writeInt(Grid.xSz);
/* 359 */       dataOut.writeInt(Grid.ySz);
/* 360 */       dataOut.writeByte(Methods.noReveal);
/* 361 */       dataOut.writeByte(Methods.noErrors);
/* 362 */       for (int i = 0; i < 54; i++)
/* 363 */         dataOut.writeByte(0); 
/* 364 */       for (int j = 0; j < Grid.ySz; j++) {
/* 365 */         for (int k = 0; k < Grid.xSz; k++) {
/* 366 */           dataOut.writeInt(Grid.mode[k][j]);
/* 367 */           dataOut.writeInt(Grid.sol[k][j]);
/* 368 */           dataOut.writeInt(Grid.copy[k][j]);
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
/*     */   static void loadFutoshiki(String futoshikiName) {
/*     */     try {
/* 385 */       File fl = new File("futoshiki/" + futoshikiName);
/* 386 */       if (!fl.exists()) {
/* 387 */         fl = new File("futoshiki/");
/* 388 */         String[] s = fl.list(); int k;
/* 389 */         for (k = 0; k < s.length && (
/* 390 */           s[k].lastIndexOf(".futoshiki") == -1 || s[k].charAt(0) == '.'); k++);
/*     */         
/* 392 */         futoshikiName = s[k];
/* 393 */         Op.fu[Op.FU.FuPuz.ordinal()] = futoshikiName;
/*     */       } 
/*     */ 
/*     */       
/* 397 */       DataInputStream dataIn = new DataInputStream(new FileInputStream("futoshiki/" + futoshikiName));
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
/* 408 */           Grid.copy[i][j] = dataIn.readInt();
/* 409 */           Grid.letter[i][j] = dataIn.readInt();
/*     */         } 
/* 411 */       }  Methods.puzzleTitle = dataIn.readUTF();
/* 412 */       Methods.author = dataIn.readUTF();
/* 413 */       Methods.copyright = dataIn.readUTF();
/* 414 */       Methods.puzzleNumber = dataIn.readUTF();
/* 415 */       Methods.puzzleNotes = dataIn.readUTF();
/* 416 */       dataIn.close();
/*     */     }
/* 418 */     catch (IOException exc) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static void drawFutoshiki(Graphics2D g2, int[][] puzzleArray) {
/* 424 */     Stroke normalStroke = new BasicStroke(Grid.xCell / 25.0F, 2, 2);
/* 425 */     Stroke roundStroke = new BasicStroke(Grid.xCell / 25.0F, 1, 2);
/* 426 */     g2.setStroke(normalStroke);
/*     */     
/* 428 */     RenderingHints rh = g2.getRenderingHints();
/* 429 */     rh.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
/* 430 */     rh.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
/* 431 */     g2.setRenderingHints(rh);
/*     */ 
/*     */     
/* 434 */     g2.setColor(new Color(Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.FU.FuBg.ordinal(), Op.fu) : 16777215));
/* 435 */     g2.fillRect(Grid.xOrg - Grid.xCell / 5, Grid.yOrg - Grid.yCell / 5, Grid.xSz * Grid.xCell + Grid.xCell / 15, Grid.ySz * Grid.yCell + Grid.yCell / 15);
/* 436 */     g2.setColor(new Color(Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.FU.FuBorder.ordinal(), Op.fu) : 0));
/* 437 */     g2.drawRect(Grid.xOrg - Grid.xCell / 5, Grid.yOrg - Grid.yCell / 5, Grid.xSz * Grid.xCell + Grid.xCell / 15, Grid.ySz * Grid.yCell + Grid.yCell / 15);
/*     */     
/* 439 */     for (int j = 0; j < Grid.ySz; j++) {
/* 440 */       for (int k = 0; k < Grid.xSz; k++) {
/* 441 */         int theColor; if (Def.dispWithColor.booleanValue()) {
/* 442 */           if (Def.dispErrors.booleanValue() && Grid.sol[k][j] != 0 && Grid.sol[k][j] != Grid.letter[k][j]) {
/* 443 */             theColor = Op.getColorInt(Op.FU.FuError.ordinal(), Op.fu);
/*     */           } else {
/* 445 */             theColor = Op.getColorInt(Op.FU.FuCell.ordinal(), Op.fu);
/*     */           } 
/*     */         } else {
/* 448 */           theColor = 16777215;
/* 449 */         }  g2.setColor(new Color(theColor));
/* 450 */         g2.fillRect(Grid.xOrg + k * Grid.xCell, Grid.yOrg + j * Grid.yCell, 2 * Grid.xCell / 3, 2 * Grid.yCell / 3);
/* 451 */         g2.setColor(new Color(Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.FU.FuBorder.ordinal(), Op.fu) : 0));
/* 452 */         g2.drawRect(Grid.xOrg + k * Grid.xCell, Grid.yOrg + j * Grid.yCell, 2 * Grid.xCell / 3, 2 * Grid.yCell / 3);
/*     */       } 
/*     */     } 
/*     */     
/* 456 */     if (Def.dispCursor.booleanValue()) {
/* 457 */       g2.setColor(Def.COLOR_RED);
/* 458 */       g2.drawRect(Grid.xOrg + Grid.xCur * Grid.xCell, Grid.yOrg + Grid.yCur * Grid.yCell, 2 * Grid.xCell / 3, 2 * Grid.yCell / 3);
/*     */     } 
/*     */ 
/*     */     
/* 462 */     g2.setColor(new Color(Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.FU.FuNumbers.ordinal(), Op.fu) : 0));
/* 463 */     g2.setFont(new Font(Op.fu[Op.FU.FuFont.ordinal()], 0, 5 * Grid.yCell / 10));
/* 464 */     FontMetrics fm = g2.getFontMetrics(); int i;
/* 465 */     for (i = 0; i < Grid.ySz; i++) {
/* 466 */       for (int k = 0; k < Grid.xSz; k++) {
/* 467 */         char ch = (char)puzzleArray[k][i];
/* 468 */         if (ch != '\000') {
/* 469 */           int w = fm.stringWidth("" + ch);
/* 470 */           g2.drawString("" + ch, Grid.xOrg + k * Grid.xCell + (2 * Grid.xCell / 3 - w) / 2, Grid.yOrg + i * Grid.yCell + (2 * Grid.yCell / 3 + fm.getAscent() - fm.getDescent()) / 2);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 475 */     g2.setStroke(roundStroke);
/* 476 */     g2.setColor(new Color(Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.FU.FuInequality.ordinal(), Op.fu) : 0));
/* 477 */     for (int y = 0; y < Grid.ySz; y++) {
/* 478 */       for (int x = 0; x < Grid.xSz; x++) {
/* 479 */         int top = Grid.yOrg + y * Grid.yCell, left = Grid.xOrg + x * Grid.xCell;
/* 480 */         int rght = left + 2 * Grid.xCell / 3, bot = top + 2 * Grid.yCell / 3;
/* 481 */         int reach = Grid.xCell / 8;
/*     */         
/* 483 */         if ((Grid.mode[x][y] & 0x1) != 0) {
/* 484 */           int equalityOrgX = rght + Grid.xCell / 9, equalityOrgY = (top + bot) / 2;
/* 485 */           g2.drawLine(equalityOrgX, equalityOrgY, equalityOrgX + reach, equalityOrgY + reach);
/* 486 */           g2.drawLine(equalityOrgX, equalityOrgY, equalityOrgX + reach, equalityOrgY - reach);
/*     */         } 
/* 488 */         if ((Grid.mode[x][y] & 0x2) != 0) {
/* 489 */           int equalityOrgX = rght + Grid.xCell / 9 + reach, equalityOrgY = (top + bot) / 2;
/* 490 */           g2.drawLine(equalityOrgX, equalityOrgY, equalityOrgX - reach, equalityOrgY + reach);
/* 491 */           g2.drawLine(equalityOrgX, equalityOrgY, equalityOrgX - reach, equalityOrgY - reach);
/*     */         } 
/* 493 */         if ((Grid.mode[x][y] & 0x4) != 0) {
/* 494 */           int equalityOrgX = (rght + left) / 2, equalityOrgY = bot + Grid.yCell / 9;
/* 495 */           g2.drawLine(equalityOrgX, equalityOrgY, equalityOrgX + reach, equalityOrgY + reach);
/* 496 */           g2.drawLine(equalityOrgX, equalityOrgY, equalityOrgX - reach, equalityOrgY + reach);
/*     */         } 
/* 498 */         if ((Grid.mode[x][y] & 0x8) != 0) {
/* 499 */           int equalityOrgX = (rght + left) / 2, equalityOrgY = bot + Grid.yCell / 9 + reach;
/* 500 */           g2.drawLine(equalityOrgX, equalityOrgY, equalityOrgX + reach, equalityOrgY - reach);
/* 501 */           g2.drawLine(equalityOrgX, equalityOrgY, equalityOrgX - reach, equalityOrgY - reach);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 506 */     if (Def.dispGuideDigits.booleanValue()) {
/* 507 */       g2.setFont(new Font(Op.fu[Op.FU.FuGuideFont.ordinal()], 0, Grid.yCell / 5));
/* 508 */       g2.setColor(new Color(Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.FU.FuGuide.ordinal(), Op.fu) : 0));
/* 509 */       fm = g2.getFontMetrics();
/* 510 */       for (i = 0; i < Grid.ySz; i++) {
/* 511 */         for (int k = 0; k < Grid.xSz; k++) {
/* 512 */           if (Grid.sol[k][i] == 0)
/* 513 */             for (int m = 0; m < Grid.xSz; m++) {
/* 514 */               if (Grid.xstatus[k][i][m] != 0)
/* 515 */               { int w = (2 * Grid.xCell / 9 - fm.stringWidth("" + (m + 1))) / 2;
/* 516 */                 g2.drawString("" + (m + 1), Grid.xOrg + k * Grid.xCell + 2 * Grid.xCell * m % 3 / 9 + w, Grid.yOrg + i * Grid.yCell + 2 * Grid.yCell * m / 3 / 9 + fm.getAscent()); } 
/*     */             }  
/*     */         } 
/*     */       } 
/* 520 */     }  g2.setStroke(normalStroke);
/*     */   }
/*     */   
/*     */   void updateUndo(int x, int y, int i) {
/* 524 */     if (Grid.xstatus[x][y][i] > 0) {
/* 525 */       Grid.xstatus[x][y][i] = 0;
/* 526 */       this.undoX[this.undoIndex] = x;
/* 527 */       this.undoY[this.undoIndex] = y;
/* 528 */       this.undoI[this.undoIndex] = i;
/* 529 */       this.undoS[this.undoIndex++] = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void updateSolveStatus(int x, int y, int i) {
/* 536 */     for (int j = 0; j < Grid.xSz; j++) {
/* 537 */       updateUndo(x, y, j);
/* 538 */       updateUndo(j, y, i);
/* 539 */       updateUndo(x, j, i);
/*     */     } 
/*     */   }
/*     */   
/*     */   void updateStatus(int x, int y, int i) {
/* 544 */     for (int j = 0; j < Grid.xSz; j++) {
/* 545 */       Grid.xstatus[x][j][i] = 0; Grid.xstatus[j][y][i] = 0; Grid.xstatus[x][y][j] = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void recalculateStatus() {
/* 552 */     for (int z = 0; z < Grid.ySz; z++) {
/* 553 */       for (int i = 0; i < Grid.xSz; i++) {
/* 554 */         for (int x = 0; x < Grid.xSz; x++)
/* 555 */           Grid.xstatus[x][i][z] = 1; 
/*     */       } 
/* 557 */     }  for (int y = 0; y < Grid.ySz; y++) {
/* 558 */       for (int x = 0; x < Grid.xSz; x++) {
/* 559 */         int cIndex = Grid.sol[x][y] - 49;
/* 560 */         if (cIndex >= 0)
/* 561 */           updateStatus(x, y, cIndex); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   static void clearSolution() {
/* 566 */     for (int j = 0; j < Grid.ySz; j++) {
/* 567 */       for (int i = 0; i < Grid.xSz; i++)
/* 568 */         Grid.sol[i][j] = Grid.copy[i][j]; 
/*     */     } 
/*     */   }
/*     */   void restoreIfDone() {
/* 572 */     for (int j = 0; j < Grid.ySz; j++) {
/* 573 */       for (int i = 0; i < Grid.xSz; i++)
/* 574 */       { if (Grid.sol[i][j] == 0)
/*     */           return;  } 
/* 576 */     }  clearSolution();
/*     */   }
/*     */   
/*     */   void updateGrid(MouseEvent e) {
/* 580 */     int x = e.getX(), y = e.getY();
/*     */     
/* 582 */     if (x < Grid.xOrg || y < Grid.yOrg)
/*     */       return; 
/* 584 */     int i = (x - Grid.xOrg) / Grid.xCell;
/* 585 */     int j = (y - Grid.yOrg) / Grid.yCell;
/* 586 */     if (i >= Grid.xSz || j >= Grid.ySz)
/*     */       return; 
/* 588 */     if (x > Grid.xOrg + i * Grid.xCell + 2 * Grid.xCell / 3 || y > Grid.yOrg + j * Grid.yCell + 2 * Grid.yCell / 3)
/*     */       return; 
/* 590 */     Grid.xCur = i;
/* 591 */     Grid.yCur = j;
/* 592 */     i = (x - Grid.xOrg - i * Grid.xCell) / 2 * Grid.xCell / 9;
/* 593 */     j = (y - Grid.yOrg - j * Grid.yCell) / 2 * Grid.yCell / 9;
/* 594 */     int val = i + 3 * j;
/* 595 */     if (Grid.xstatus[Grid.xCur][Grid.yCur][val] == 0) {
/*     */       return;
/*     */     }
/* 598 */     updateUndo(Grid.xCur, Grid.yCur, val);
/* 599 */     this.undoS[this.undoIndex - 1] = 1;
/*     */     int count;
/* 601 */     for (x = count = 0; x < Grid.xSz; x++) {
/* 602 */       if (Grid.xstatus[Grid.xCur][Grid.yCur][x] != 0)
/* 603 */         count++; 
/* 604 */     }  if (count == 0) {
/* 605 */       Grid.sol[Grid.xCur][Grid.yCur] = val + 49;
/* 606 */       updateSolveStatus(Grid.xCur, Grid.yCur, val);
/* 607 */       (new Thread(this.solveThread)).start();
/*     */     } 
/*     */   }
/*     */   
/*     */   void handleKeyPressed(KeyEvent e) {
/* 612 */     if (e.isAltDown())
/* 613 */       return;  switch (e.getKeyCode()) { case 38:
/* 614 */         if (Grid.yCur > 0) Grid.yCur--;  return;
/* 615 */       case 40: if (Grid.yCur < Grid.ySz - 1) Grid.yCur++;  return;
/* 616 */       case 37: if (Grid.xCur > 0) Grid.xCur--;  return;
/* 617 */       case 39: if (Grid.xCur < Grid.xSz - 1) Grid.xCur++;  return;
/* 618 */       case 36: Grid.xCur = 0; return;
/* 619 */       case 35: Grid.xCur = Grid.xSz - 1; return;
/* 620 */       case 33: Grid.yCur = 0; return;
/* 621 */       case 34: Grid.yCur = Grid.ySz - 1; return; }
/*     */     
/* 623 */     char ch = e.getKeyChar();
/* 624 */     int i = ch - 49;
/* 625 */     if (i >= 0 && i < Grid.xSz) {
/* 626 */       updateUndo(Grid.xCur, Grid.yCur, i);
/* 627 */       this.undoS[this.undoIndex - 1] = 1;
/* 628 */       Grid.sol[Grid.xCur][Grid.yCur] = ch;
/* 629 */       updateSolveStatus(Grid.xCur, Grid.yCur, i);
/* 630 */       (new Thread(this.solveThread)).start();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mustapha\Desktop\Crossword-Express.jar!\crosswordexpress\FutoshikiSolve.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */