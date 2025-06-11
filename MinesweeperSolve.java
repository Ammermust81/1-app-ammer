/*     */ package crosswordexpress;
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Color;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Insets;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.Stroke;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.MouseAdapter;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JMenu;
/*     */ import javax.swing.JMenuItem;
/*     */ import javax.swing.KeyStroke;
/*     */ 
/*     */ public final class MinesweeperSolve {
/*     */   static JFrame jfSolveMinesweeper;
/*     */   static JMenuBar menuBar;
/*     */   JMenu menu;
/*     */   JMenu submenu;
/*  27 */   String[] minesweeperTitle = new String[] { "An error has been detected", "Required Clear", "Required Mine", "Invalid Clear", "Invalid Mine" }; JMenuItem menuItem; static JPanel pp; static int panelW; static int panelH; static JLabel jl1; static JLabel jl2;
/*     */   Timer myTimer;
/*     */   Runnable solveThread;
/*     */   int memMode;
/*     */   static final int PENDING = 0;
/*     */   static final int CLEAR = 1;
/*     */   static final int MINED = 2;
/*     */   static final int RESERVED = 3;
/*  35 */   String[] minesweeperHint = new String[] { "<div>Your partial solution has been checked and found to be in error in those cells which are highlighted in blue. Under these circumstances, it is not possible to provide a trustworthy hint as to the next step in the solution.<p/>Please investigate, and correct the errors before proceeding with the solution process.</div></body>", "<div>This is one of the most fundamental strategies available for the solving of Minesweeper puzzles. If you make a puzzle having a difficulty level of 1, then you will be able to solve it completely using only this strategy plus the <b>Required Mine</b> strategy.<p/>The highlighted cells surround a hazard cell which has already achieved its required complement of adjacent mines. As no further mines are required, the highlighted cells may all be cleared by means of a single mouse click.</div></body>", "<div>The highlighted cells are adjacent to a hazard cell which has an outstanding requirement for a number of mines which is equal to the number highlighted cells. Clearly mines must be placed into each of the highlighted cells by means of a double mouse click.</div></body>", "<div>If any of the highlighted cells were to be cleared, then a nearby hazard cell could only achieve its required complement of mines by forcing another nearby hazard cell to have more than its required complement of mines. This is not permitted, and so the highlighted cells must not be cleared. They must in fact contain mines.</div></body>", "<div>If any of the highlighted cells were to contain a mine, then a nearby hazard cell would acquire its full complement of mines. When all of its pending adjacent cells were cleared it would prevent another nearby cell from ever aquiring its complement of mines. Consequently, none of the highlighted cells can be allowed to contain a mine, and must be cleared.</div></body>" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  57 */   String minesweeperSolveHelp = "<div>A MINESWEEPER puzzle consists of a square or rectangular grid in which a number of mines have been laid. Some of the cells contain a hazard flag which has a number indicating how many of the adjacent cells (horizontally, vertically and diagonally) contain a mine. To solve the puzzle, you must determine which of the un-flagged cells contain a mine. Each puzzle has a unique solution and no guessing is required to reach a correct solution. Puzzles can be built either manually or automatically in sizes from 6x6 up to 14x14.<p/>Solving a MINESWEEPER puzzle is a mouse only operation. A single click in any DARK cell will convert that cell to a NO-MINE cell which has a default color of white. A second click will convert it to a MINE cell, and one more click will convert it back to a DARK cell.<p/></div><span class='m'>Menu Functions</span><ul><li/><span class='s'>File Menu</span><ul><li/><span>Load a Puzzle</span><br/>Use this option to choose the puzzle you want to solve from the pool of MINESWEEPER puzzles currently available on your computer.<p/><li/><span>Quit Solving</span><br/>Returns you to the MINESWEEPER Construction screen.<p/></ul><li/><span class='s'>View Menu</span><ul><li/><span>Display Options</span><br/>This leads you to a dialog box in which you can change the colors of various elements within the puzzle, and control the fonts which will be used for the puzzle's text components. You can also decide if printing of the puzzle will be done in black and white or in color.<p/></ul><li/><span class='s'>Tasks Menu</span><ul><li/><span>Reveal Errors</span><br/>If you think you may have made errors, this option will show you where they are by highlighting them for a period of 1.5 seconds.<p/><li/><span>Reveal Solution</span><br/>The entire solution can be seen by selecting this option.<p/><li/><span>Request a Hint</span><br/>This option will highlight one or more puzzle cells in blue to indicate the location within the puzzle where further progress can be made. In addition an automatic Help screen will appear giving you a detailed description of the strategy you will need to apply.<p/><li/><span>Begin Again</span><br/>You can restart the entire solution process at any time by selecting this option.<p/></ul><li/><span class='s'>Help Menu</span><ul><li/><span>Minesweeper Help</span><br/>Displays the Help screen which you are now reading.<p/></ul></ul></body>";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   MinesweeperSolve(JFrame jf) {
/*  99 */     this.memMode = Def.puzzleMode;
/* 100 */     Def.puzzleMode = 133;
/* 101 */     Def.dispCursor = Boolean.valueOf(true);
/*     */     
/* 103 */     jfSolveMinesweeper = new JFrame("Minesweeper");
/* 104 */     jfSolveMinesweeper.setSize(Op.getInt(Op.MS.MsW.ordinal(), Op.ms), Op.getInt(Op.MS.MsH.ordinal(), Op.ms));
/* 105 */     int frameX = (jf.getX() + jfSolveMinesweeper.getWidth() > Methods.scrW) ? (Methods.scrW - jfSolveMinesweeper.getWidth() - 10) : jf.getX();
/* 106 */     jfSolveMinesweeper.setLocation(frameX, jf.getY());
/* 107 */     jfSolveMinesweeper.setLayout((LayoutManager)null);
/* 108 */     jfSolveMinesweeper.setDefaultCloseOperation(0);
/* 109 */     jfSolveMinesweeper
/* 110 */       .addComponentListener(new ComponentAdapter() {
/*     */           public void componentResized(ComponentEvent ce) {
/* 112 */             int oldw = Op.getInt(Op.MS.MsW.ordinal(), Op.ms);
/* 113 */             int oldh = Op.getInt(Op.MS.MsH.ordinal(), Op.ms);
/* 114 */             Methods.frameResize(MinesweeperSolve.jfSolveMinesweeper, oldw, oldh, 500, 580);
/* 115 */             Op.setInt(Op.MS.MsW.ordinal(), MinesweeperSolve.jfSolveMinesweeper.getWidth(), Op.ms);
/* 116 */             Op.setInt(Op.MS.MsH.ordinal(), MinesweeperSolve.jfSolveMinesweeper.getHeight(), Op.ms);
/* 117 */             MinesweeperSolve.restoreFrame("");
/*     */           }
/*     */         });
/*     */     
/* 121 */     jfSolveMinesweeper
/* 122 */       .addWindowListener(new WindowAdapter() {
/*     */           public void windowClosing(WindowEvent we) {
/* 124 */             if (Def.selecting)
/* 125 */               return;  MinesweeperSolve.this.restoreIfDone();
/* 126 */             MinesweeperSolve.saveMinesweeper(Op.ms[Op.MS.MsPuz.ordinal()]);
/* 127 */             CrosswordExpress.transfer(132, MinesweeperSolve.jfSolveMinesweeper);
/*     */           }
/*     */         });
/*     */     
/* 131 */     Methods.closeHelp();
/*     */ 
/*     */     
/* 134 */     this.solveThread = (() -> {
/*     */         for (int j = 0; j < Grid.ySz; j++) {
/*     */           for (int i = 0; i < Grid.xSz; i++) {
/*     */             if (Grid.letter[i][j] == 0 && Grid.sol[i][j] != Grid.copy[i][j])
/*     */               return; 
/*     */           } 
/*     */         }  Methods.congratulations(jfSolveMinesweeper);
/*     */       });
/* 142 */     jl1 = new JLabel(); jfSolveMinesweeper.add(jl1);
/* 143 */     jl2 = new JLabel(); jfSolveMinesweeper.add(jl2);
/*     */ 
/*     */     
/* 146 */     menuBar = new JMenuBar();
/* 147 */     menuBar.setBackground(Def.COLOR_MENUBAR);
/* 148 */     jfSolveMinesweeper.setJMenuBar(menuBar);
/*     */     
/* 150 */     this.menu = new JMenu("File");
/* 151 */     menuBar.add(this.menu);
/* 152 */     this.menuItem = new JMenuItem("Load a Puzzle");
/* 153 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(76, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 154 */     this.menu.add(this.menuItem);
/* 155 */     this.menuItem
/* 156 */       .addActionListener(ae -> {
/*     */           if (Def.building == 1) {
/*     */             return;
/*     */           }
/*     */           restoreIfDone();
/*     */           saveMinesweeper(Op.ms[Op.MS.MsPuz.ordinal()]);
/*     */           new Select(jfSolveMinesweeper, "minesweeper", "minesweeper", Op.ms, Op.MS.MsPuz.ordinal(), false);
/*     */         });
/* 164 */     this.menuItem = new JMenuItem("Quit Solving");
/* 165 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(81, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 166 */     this.menu.add(this.menuItem);
/* 167 */     this.menuItem
/* 168 */       .addActionListener(ae -> {
/*     */           restoreIfDone();
/*     */           
/*     */           saveMinesweeper(Op.ms[Op.MS.MsPuz.ordinal()]);
/*     */           
/*     */           CrosswordExpress.transfer(132, jfSolveMinesweeper);
/*     */         });
/*     */     
/* 176 */     this.menu = new JMenu("View");
/* 177 */     menuBar.add(this.menu);
/* 178 */     this.menuItem = new JMenuItem("Display Options");
/* 179 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(89, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 180 */     this.menu.add(this.menuItem);
/* 181 */     this.menuItem
/* 182 */       .addActionListener(ae -> {
/*     */           MinesweeperBuild.printOptions(jfSolveMinesweeper, "Display Options");
/*     */ 
/*     */           
/*     */           restoreFrame("");
/*     */         });
/*     */     
/* 189 */     this.menu = new JMenu("Task");
/* 190 */     menuBar.add(this.menu);
/*     */     
/* 192 */     ActionListener errorTimer = ae -> {
/*     */         Def.dispErrors = Boolean.valueOf(false);
/*     */         pp.repaint();
/*     */         this.myTimer.stop();
/*     */       };
/* 197 */     this.myTimer = new Timer(1500, errorTimer);
/*     */     
/* 199 */     this.menuItem = new JMenuItem("Reveal Errors");
/* 200 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(69, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 201 */     this.menu.add(this.menuItem);
/* 202 */     this.menuItem
/* 203 */       .addActionListener(ae -> {
/*     */           if (Methods.noErrors == 0) {
/*     */             this.myTimer.start();
/*     */             
/*     */             Def.dispErrors = Boolean.valueOf(true);
/*     */           } else {
/*     */             Methods.noReveal(jfSolveMinesweeper);
/*     */           } 
/*     */           
/*     */           pp.repaint();
/*     */         });
/* 214 */     this.menuItem = new JMenuItem("Reveal Solution");
/* 215 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(83, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 216 */     this.menu.add(this.menuItem);
/* 217 */     this.menuItem
/* 218 */       .addActionListener(ae -> {
/*     */           if (Methods.noReveal == 0) {
/*     */             for (int j = 0; j < Grid.ySz; j++) {
/*     */               for (int i = 0; i < Grid.xSz; i++) {
/*     */                 Grid.sol[i][j] = Grid.copy[i][j];
/*     */               }
/*     */             } 
/*     */           } else {
/*     */             Methods.noReveal(jfSolveMinesweeper);
/*     */           } 
/*     */           restoreFrame("");
/*     */         });
/* 230 */     this.menuItem = new JMenuItem("Request a Hint");
/* 231 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(82, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 232 */     this.menu.add(this.menuItem);
/* 233 */     this.menuItem
/* 234 */       .addActionListener(ae -> {
/*     */           Grid.findHint = Boolean.valueOf(true);
/*     */           int hintNum = MinesweeperBuild.nextHint();
/*     */           if (hintNum == -1) {
/*     */             hintNum = 0;
/*     */           }
/*     */           Methods.closeHelp();
/*     */           Methods.cweHelp(jfSolveMinesweeper, null, this.minesweeperTitle[hintNum], this.minesweeperHint[hintNum]);
/*     */           restoreFrame("");
/*     */           Grid.findHint = Boolean.valueOf(false);
/*     */         });
/* 245 */     this.menuItem = new JMenuItem("Begin again");
/* 246 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(66, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 247 */     this.menu.add(this.menuItem);
/* 248 */     this.menuItem
/* 249 */       .addActionListener(ae -> {
/*     */           Methods.clearGrid(Grid.sol);
/*     */ 
/*     */           
/*     */           restoreFrame("");
/*     */         });
/*     */     
/* 256 */     this.menu = new JMenu("Help");
/* 257 */     menuBar.add(this.menu);
/* 258 */     this.menuItem = new JMenuItem("Minesweeper Help");
/* 259 */     this.menu.add(this.menuItem);
/* 260 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(72, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 261 */     this.menuItem
/* 262 */       .addActionListener(ae -> Methods.cweHelp(jfSolveMinesweeper, null, "Solving Minesweeper Puzzles", this.minesweeperSolveHelp));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 268 */     loadMinesweeper(Op.ms[Op.MS.MsPuz.ordinal()]);
/* 269 */     pp = new MinesweeperSolvePP(0, 37);
/* 270 */     jfSolveMinesweeper.add(pp);
/*     */     
/* 272 */     pp
/* 273 */       .addMouseListener(new MouseAdapter() {
/*     */           public void mousePressed(MouseEvent e) {
/* 275 */             MinesweeperSolve.this.updateGrid(e);
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 280 */     pp
/* 281 */       .addMouseMotionListener(new MouseAdapter() {
/*     */           public void mouseMoved(MouseEvent e) {
/* 283 */             if (Def.isMac) {
/* 284 */               MinesweeperSolve.jfSolveMinesweeper.setResizable((MinesweeperSolve.jfSolveMinesweeper.getWidth() - e.getX() < 15 && MinesweeperSolve.jfSolveMinesweeper
/* 285 */                   .getHeight() - e.getY() < 95));
/*     */             }
/*     */           }
/*     */         });
/*     */     
/* 290 */     restoreFrame("");
/*     */   }
/*     */   
/*     */   static void restoreFrame(String hint) {
/* 294 */     jfSolveMinesweeper.setVisible(true);
/* 295 */     Insets insets = jfSolveMinesweeper.getInsets();
/* 296 */     panelW = jfSolveMinesweeper.getWidth() - insets.left + insets.right;
/* 297 */     panelH = jfSolveMinesweeper.getHeight() - insets.top + insets.bottom + 37 + menuBar.getHeight();
/* 298 */     pp.setSize(panelW, panelH);
/* 299 */     jfSolveMinesweeper.requestFocusInWindow();
/* 300 */     pp.repaint();
/* 301 */     Methods.infoPanel(jl1, jl2, "Solve Minesweeper", (hint.length() > 0) ? ("  Hint : " + hint) : ("Puzzle : " + Op.ms[Op.MS.MsPuz.ordinal()]), panelW);
/*     */   }
/*     */   
/*     */   static void setSizesAndOffsets(int x, int y, int width, int height, int inset) {
/* 305 */     int i = (width - inset) / Grid.xSz;
/* 306 */     int j = (height - inset) / Grid.ySz;
/* 307 */     Grid.xCell = Grid.yCell = (i < j) ? i : j;
/* 308 */     Grid.xOrg = x + ((Def.puzzleMode == 8) ? ((width - Grid.xSz * Grid.xCell) / 2) : 10);
/* 309 */     Grid.yOrg = y + ((Def.puzzleMode == 8) ? ((height - Grid.ySz * Grid.yCell) / 2) : 10);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void saveMinesweeper(String minesweeperName) {
/*     */     try {
/* 315 */       DataOutputStream dataOut = new DataOutputStream(new FileOutputStream("minesweeper/" + minesweeperName));
/* 316 */       dataOut.writeInt(Grid.xSz);
/* 317 */       dataOut.writeInt(Grid.ySz);
/* 318 */       dataOut.writeByte(Methods.noReveal);
/* 319 */       dataOut.writeByte(Methods.noErrors);
/* 320 */       for (int i = 0; i < 54; i++)
/* 321 */         dataOut.writeByte(0); 
/* 322 */       for (int j = 0; j < Grid.ySz; j++) {
/* 323 */         for (int k = 0; k < Grid.xSz; k++) {
/* 324 */           dataOut.writeInt(Grid.sol[k][j]);
/* 325 */           dataOut.writeInt(Grid.copy[k][j]);
/* 326 */           dataOut.writeInt(Grid.letter[k][j]);
/*     */         } 
/* 328 */       }  dataOut.writeUTF(Methods.puzzleTitle);
/* 329 */       dataOut.writeUTF(Methods.author);
/* 330 */       dataOut.writeUTF(Methods.copyright);
/* 331 */       dataOut.writeUTF(Methods.puzzleNumber);
/* 332 */       dataOut.writeUTF(Methods.puzzleNotes);
/* 333 */       dataOut.close();
/*     */     }
/* 335 */     catch (IOException exc) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static void loadMinesweeper(String minesweeperName) {
/*     */     try {
/* 342 */       File fl = new File("minesweeper/" + minesweeperName);
/* 343 */       if (!fl.exists()) {
/* 344 */         fl = new File("minesweeper/");
/* 345 */         String[] s = fl.list(); int k;
/* 346 */         for (k = 0; k < s.length && (
/* 347 */           s[k].lastIndexOf(".minesweeper") == -1 || s[k].charAt(0) == '.'); k++);
/*     */         
/* 349 */         minesweeperName = s[k];
/* 350 */         Op.ms[Op.MS.MsPuz.ordinal()] = minesweeperName;
/*     */       } 
/*     */ 
/*     */       
/* 354 */       DataInputStream dataIn = new DataInputStream(new FileInputStream("minesweeper/" + minesweeperName));
/* 355 */       Grid.xSz = dataIn.readInt();
/* 356 */       Grid.ySz = dataIn.readInt();
/* 357 */       Methods.noReveal = dataIn.readByte();
/* 358 */       Methods.noErrors = dataIn.readByte(); int i;
/* 359 */       for (i = 0; i < 54; i++)
/* 360 */         dataIn.readByte(); 
/* 361 */       for (int j = 0; j < Grid.ySz; j++) {
/* 362 */         for (i = 0; i < Grid.xSz; i++) {
/* 363 */           Grid.sol[i][j] = dataIn.readInt();
/* 364 */           Grid.copy[i][j] = dataIn.readInt();
/* 365 */           Grid.letter[i][j] = dataIn.readInt();
/*     */         } 
/* 367 */       }  Methods.puzzleTitle = dataIn.readUTF();
/* 368 */       Methods.author = dataIn.readUTF();
/* 369 */       Methods.copyright = dataIn.readUTF();
/* 370 */       Methods.puzzleNumber = dataIn.readUTF();
/* 371 */       Methods.puzzleNotes = dataIn.readUTF();
/* 372 */       dataIn.close();
/*     */     }
/* 374 */     catch (IOException exc) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static void drawMinesweeper(Graphics2D g2, int[][] puzzleArray) {
/* 380 */     int[] xPoints = new int[3], yPoints = new int[3];
/*     */     
/* 382 */     Stroke normalStroke = new BasicStroke(Grid.xCell / 25.0F, 2, 2);
/* 383 */     Stroke roundStroke = new BasicStroke(Grid.xCell / 25.0F, 1, 2);
/* 384 */     Stroke wideStroke = new BasicStroke(Grid.xCell / 10.0F, 0, 2);
/* 385 */     g2.setStroke(normalStroke);
/*     */     
/* 387 */     RenderingHints rh = g2.getRenderingHints();
/* 388 */     rh.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
/* 389 */     rh.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
/* 390 */     g2.setRenderingHints(rh);
/*     */     
/*     */     int j;
/* 393 */     for (j = 0; j < Grid.ySz; j++) {
/* 394 */       for (int i = 0; i < Grid.xSz; i++) {
/* 395 */         int theColor; if (Def.dispWithColor.booleanValue())
/* 396 */         { if (Def.dispErrors.booleanValue() && Grid.sol[i][j] != 0 && Grid.sol[i][j] != Grid.copy[i][j]) {
/* 397 */             theColor = Op.getColorInt(Op.MS.MsError.ordinal(), Op.ms);
/*     */           } else {
/* 399 */             theColor = (Grid.sol[i][j] > 0 || puzzleArray[i][j] >= 48) ? Op.getColorInt(Op.MS.MsCell.ordinal(), Op.ms) : Op.getColorInt(Op.MS.MsClear.ordinal(), Op.ms);
/*     */           }  }
/* 401 */         else { theColor = 16777215; }
/* 402 */          g2.setColor(new Color(theColor));
/* 403 */         g2.fillRect(Grid.xOrg + i * Grid.xCell, Grid.yOrg + j * Grid.yCell, Grid.xCell, Grid.yCell);
/* 404 */         g2.setColor(Def.dispWithColor.booleanValue() ? new Color(Op.getColorInt(Op.MS.MsLines.ordinal(), Op.ms)) : Def.COLOR_BLACK);
/*     */       } 
/*     */     } 
/*     */     
/* 408 */     for (j = 0; j < Grid.ySz + 1; j++)
/* 409 */       g2.drawLine(Grid.xOrg, Grid.yOrg + j * Grid.yCell, Grid.xOrg + Grid.xSz * Grid.xCell, Grid.yOrg + j * Grid.yCell); 
/* 410 */     for (j = 0; j < Grid.xSz + 1; j++) {
/* 411 */       g2.drawLine(Grid.xOrg + j * Grid.xCell, Grid.yOrg, Grid.xOrg + j * Grid.xCell, Grid.yOrg + Grid.xSz * Grid.yCell);
/*     */     }
/*     */     
/* 414 */     g2.setStroke(roundStroke);
/* 415 */     for (j = 0; j < Grid.ySz; j++) {
/* 416 */       for (int i = 0; i < Grid.xSz; i++) {
/* 417 */         if (puzzleArray[i][j] != 0) {
/* 418 */           xPoints[1] = Grid.xOrg + i * Grid.xCell + Grid.xCell / 6; xPoints[0] = Grid.xOrg + i * Grid.xCell + Grid.xCell / 6;
/* 419 */           xPoints[2] = xPoints[0] + 3 * Grid.xCell / 4;
/* 420 */           yPoints[0] = Grid.yOrg + j * Grid.yCell + Grid.yCell / 8;
/* 421 */           yPoints[1] = yPoints[0] + 3 * Grid.xCell / 4;
/* 422 */           yPoints[2] = yPoints[0] + 3 * Grid.xCell / 8;
/*     */           
/* 424 */           g2.setColor(Def.dispWithColor.booleanValue() ? new Color(Op.getColorInt(Op.MS.MsFlag.ordinal(), Op.ms)) : Def.COLOR_BLACK);
/* 425 */           g2.fillPolygon(xPoints, yPoints, 3);
/* 426 */           g2.setColor(Def.dispWithColor.booleanValue() ? new Color(Op.getColorInt(Op.MS.MsLines.ordinal(), Op.ms)) : Def.COLOR_BLACK);
/* 427 */           g2.drawPolygon(xPoints, yPoints, 3);
/*     */         } 
/*     */       } 
/*     */     } 
/* 431 */     g2.setFont(new Font(Op.ms[Op.MS.MsFont.ordinal()], 0, 4 * Grid.yCell / 10));
/* 432 */     FontMetrics fm = g2.getFontMetrics();
/* 433 */     g2.setColor(Def.dispWithColor.booleanValue() ? new Color(Op.getColorInt(Op.MS.MsNumbers.ordinal(), Op.ms)) : Def.COLOR_WHITE);
/* 434 */     for (j = 0; j < Grid.ySz; j++) {
/* 435 */       for (int i = 0; i < Grid.xSz; i++) {
/* 436 */         char ch = (char)puzzleArray[i][j];
/* 437 */         if (Character.isDigit(ch)) {
/* 438 */           int w = fm.stringWidth("" + ch);
/* 439 */           g2.drawString("" + ch, Grid.xOrg + i * Grid.xCell + Grid.xCell / 4, Grid.yOrg + j * Grid.yCell + (Grid.yCell + fm.getAscent() - fm.getDescent() - 2) / 2);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 444 */     for (j = 0; j < Grid.ySz; j++) {
/* 445 */       for (int i = 0; i < Grid.xSz; i++) {
/* 446 */         if (Grid.sol[i][j] == 2) {
/* 447 */           g2.setColor(Def.dispWithColor.booleanValue() ? new Color(Op.getColorInt(Op.MS.MsMine.ordinal(), Op.ms)) : Def.COLOR_BLACK);
/* 448 */           g2.fillArc(Grid.xOrg + i * Grid.xCell + Grid.xCell / 8, Grid.yOrg + j * Grid.yCell + 3 * Grid.yCell / 8, Grid.xCell / 2, Grid.yCell / 2, 0, 360);
/* 449 */           g2.drawArc(Grid.xOrg + i * Grid.xCell + 3 * Grid.xCell / 8, Grid.yOrg + j * Grid.yCell + 2 * Grid.yCell / 8, 6 * Grid.xCell / 8, 9 * Grid.yCell / 8, 180, -90);
/* 450 */           g2.setColor(Def.dispWithColor.booleanValue() ? new Color(Op.getColorInt(Op.MS.MsFuse.ordinal(), Op.ms)) : Def.COLOR_BLACK);
/* 451 */           int fuseX = Grid.xOrg + i * Grid.xCell + 6 * Grid.xCell / 8;
/* 452 */           int fuseY = Grid.yOrg + j * Grid.yCell + 2 * Grid.yCell / 8;
/* 453 */           int fuse0 = Grid.xCell / 12;
/* 454 */           g2.drawLine(fuseX - fuse0, fuseY - fuse0, fuseX + fuse0, fuseY + fuse0);
/* 455 */           g2.drawLine(fuseX - fuse0, fuseY + fuse0, fuseX + fuse0, fuseY - fuse0);
/*     */         } 
/*     */       } 
/*     */     } 
/* 459 */     if (Grid.hintIndexb > 0) {
/* 460 */       Grid.xCur = Grid.hintXb[0];
/* 461 */       Grid.yCur = Grid.hintYb[0];
/* 462 */       g2.setColor(Def.COLOR_BLUE);
/* 463 */       g2.setStroke(wideStroke);
/* 464 */       for (int i = 0; i < Grid.hintIndexb; i++)
/* 465 */         g2.drawRect(Grid.xOrg + Grid.hintXb[i] * Grid.xCell, Grid.yOrg + Grid.hintYb[i] * Grid.yCell, Grid.xCell, Grid.yCell); 
/* 466 */       Grid.hintIndexb = 0;
/*     */     } 
/*     */   }
/*     */   
/*     */   static void clearSolution() {
/* 471 */     for (int j = 0; j < Grid.ySz; j++) {
/* 472 */       for (int i = 0; i < Grid.xSz; i++)
/* 473 */         Grid.sol[i][j] = Grid.sol[i][j] & 0x80; 
/*     */     } 
/*     */   }
/*     */   void restoreIfDone() {
/* 477 */     for (int j = 0; j < Grid.ySz; j++) {
/* 478 */       for (int i = 0; i < Grid.xSz; i++)
/* 479 */       { if ((Grid.sol[i][j] & 0x5) != (Grid.copy[i][j] & 0x5))
/*     */           return;  } 
/* 481 */     }  clearSolution();
/*     */   }
/*     */   
/*     */   void updateGrid(MouseEvent e) {
/* 485 */     int x = e.getX(), y = e.getY();
/*     */     
/* 487 */     if (x < Grid.xOrg || y < Grid.yOrg)
/* 488 */       return;  x = (x - Grid.xOrg) / Grid.xCell;
/* 489 */     y = (y - Grid.yOrg) / Grid.yCell;
/* 490 */     if (x >= Grid.xSz || y >= Grid.ySz)
/*     */       return; 
/* 492 */     if (Grid.letter[x][y] == 0) {
/* 493 */       Grid.sol[x][y] = (Grid.sol[x][y] + 1) % 3;
/*     */     }
/* 495 */     (new Thread(this.solveThread)).start();
/* 496 */     restoreFrame("");
/*     */   }
/*     */ }


/* Location:              C:\Users\mustapha\Desktop\Crossword-Express.jar!\crosswordexpress\MinesweeperSolve.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */