/*     */ package crosswordexpress;
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics2D;
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
/*     */ public final class KakuroSolve extends JPanel {
/*     */   static JFrame jfSolveKakuro;
/*     */   static JMenuBar menuBar;
/*     */   JMenu menu;
/*     */   JMenu submenu;
/*     */   JMenuItem menuItem;
/*     */   static JPanel pp;
/*     */   static int panelW;
/*  27 */   int[] undoX = new int[750]; static int panelH; static JLabel jl1; static JLabel jl2; Timer myTimer; Runnable solveThread; int memMode; boolean assist = true; static int undoIndex;
/*  28 */   int[] undoY = new int[750];
/*  29 */   int[] undoI = new int[750];
/*  30 */   int[] undoM = new int[750];
/*  31 */   int[] undoS = new int[750];
/*     */   
/*  33 */   String kakuroSolve = "<div>Crossword Express <b>Kakuro</b> puzzles are similar to crossword puzzles in that they are built on standard crossword grids, but instead of using letters, the digits 1 to 9 are used.<p/>The clue for each <b>word</b> of these puzzles is the number which results from adding the digits of the <b>word.</b> There is also a less common variant of the puzzle in which the clue is formed by multiplying the digits of the <b>word.</b><p/> By tradition, the clues are displayed within the puzzle, inside either the normal black pattern cells, or in a set of special black cells which run across the top of the puzzle and down the left hand side.<p/> When first displayed, each active cell contains an array of the complete set of nine possible digits. Solving the puzzle consists of determining which of these digits cannot possibly fit into the cell, and removing them by pointing and clicking with the mouse. When only a single digit remains, this becomes the solution for that cell.<p/> Any digit which is typed at the keyboard will be placed into the red focus cell to become part of the solution.<p/> The location of the red focus cell may be shifted by pointing and clicking with the mouse, or by means of the cursor control keys.<br/><br/></div><span class='m'>Menu Functions</span><ul><li/><span class='s'>File Menu</span><ul><li/><span>Load a Puzzle</span><br/>Use this option to choose the puzzle you want to solve from the pool of KAKURO puzzles currently available on your computer.<p/><li/><span>Quit Solving</span><br/>Returns you to the KAKURO Construction screen.</ul><li/><span class='s'>View Menu</span><ul><li/><span>Display Options</span><br/>This leads you to a dialog box in which you can change the colors of various elements within the puzzle, and control the fonts which will be used for the puzzle's text components. You can also decide if printing of the puzzle will be done in black and white or in color.<p/><li/><span>Toggle Assist Mode</span><br/>If you prefer to solve these puzzles without the assistance of the candidate symbols, they can be turned on and off using this option.</ul><li/><span class='s'>Tasks Menu</span><ul><li/><span>Undo</span><br/>This option will undo in reverse order, each of the operations you have done on the puzzle.<p/><li/><span>Reveal One Number</span><br/>If you need a little help to get started, this option will place the correct number into the current focus cell.<p/><li/><span>Reveal Errors</span><br/>If you think you may have made errors, this option will show you where they are by highlighting them for a period of 1.5 seconds.<p/><li/><span>Reveal Solution</span><br/>The entire solution can be seen by selecting this option.<p/><li/><span>Begin Again</span><br/>You can restart the entire solution process at any time by selecting this option.</ul><li/><span class='s'>Help Menu</span><ul><li/><span>Kakuro Help</span><br/>Displays the Help screen which you are now reading.</ul></ul></body>";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   KakuroSolve(JFrame jf) {
/*  83 */     this.memMode = Def.puzzleMode;
/*  84 */     Def.puzzleMode = 101;
/*  85 */     Def.dispSolArray = Boolean.valueOf(true); Def.dispCursor = Boolean.valueOf(true); Def.dispGuideDigits = Boolean.valueOf(true);
/*     */ 
/*     */     
/*  88 */     Grid.clearGrid();
/*  89 */     jfSolveKakuro = new JFrame("Kakuro");
/*  90 */     jfSolveKakuro.setSize(Op.getInt(Op.KK.KkW.ordinal(), Op.kk), Op.getInt(Op.KK.KkH.ordinal(), Op.kk));
/*  91 */     int frameX = (jf.getX() + jfSolveKakuro.getWidth() > Methods.scrW) ? (Methods.scrW - jfSolveKakuro.getWidth() - 10) : jf.getX();
/*  92 */     jfSolveKakuro.setLocation(frameX, jf.getY());
/*  93 */     jfSolveKakuro.setLayout((LayoutManager)null);
/*  94 */     jfSolveKakuro.setDefaultCloseOperation(0);
/*  95 */     jfSolveKakuro
/*  96 */       .addComponentListener(new ComponentAdapter() {
/*     */           public void componentResized(ComponentEvent ce) {
/*  98 */             int oldw = Op.getInt(Op.KK.KkW.ordinal(), Op.kk);
/*  99 */             int oldh = Op.getInt(Op.KK.KkH.ordinal(), Op.kk);
/* 100 */             Methods.frameResize(KakuroSolve.jfSolveKakuro, oldw, oldh, 500, 580);
/* 101 */             Op.setInt(Op.KK.KkW.ordinal(), KakuroSolve.jfSolveKakuro.getWidth(), Op.kk);
/* 102 */             Op.setInt(Op.KK.KkH.ordinal(), KakuroSolve.jfSolveKakuro.getHeight(), Op.kk);
/* 103 */             KakuroSolve.restoreFrame();
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 108 */     jfSolveKakuro
/* 109 */       .addWindowListener(new WindowAdapter() {
/*     */           public void windowClosing(WindowEvent we) {
/* 111 */             if (Def.selecting)
/* 112 */               return;  Op.saveOptions("kakuro.opt", Op.kk);
/* 113 */             KakuroSolve.this.restoreIfDone();
/* 114 */             KakuroSolve.saveKakuro(Op.kk[Op.KK.KkPuz.ordinal()]);
/* 115 */             CrosswordExpress.transfer(100, KakuroSolve.jfSolveKakuro);
/*     */           }
/*     */         });
/*     */     
/* 119 */     Methods.closeHelp();
/*     */ 
/*     */     
/* 122 */     this.solveThread = (() -> {
/*     */         for (int j = 0; j < Grid.ySz; j++) {
/*     */           for (int i = 0; i < Grid.xSz; i++) {
/*     */             if (Grid.mode[i][j] == 0 && Grid.sol[i][j] != Grid.letter[i][j])
/*     */               return; 
/*     */           } 
/*     */         }  Methods.congratulations(jfSolveKakuro);
/*     */       });
/* 130 */     jl1 = new JLabel(); jfSolveKakuro.add(jl1);
/* 131 */     jl2 = new JLabel(); jfSolveKakuro.add(jl2);
/*     */ 
/*     */     
/* 134 */     menuBar = new JMenuBar();
/* 135 */     menuBar.setBackground(Def.COLOR_MENUBAR);
/* 136 */     jfSolveKakuro.setJMenuBar(menuBar);
/*     */     
/* 138 */     this.menu = new JMenu("File");
/* 139 */     menuBar.add(this.menu);
/* 140 */     this.menuItem = new JMenuItem("Load a Puzzle");
/* 141 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(76, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 142 */     this.menu.add(this.menuItem);
/* 143 */     this.menuItem
/* 144 */       .addActionListener(ae -> {
/*     */           if (Def.building == 1) {
/*     */             return;
/*     */           }
/*     */           restoreIfDone();
/*     */           saveKakuro(Op.kk[Op.KK.KkPuz.ordinal()]);
/*     */           new Select(jfSolveKakuro, "kakuro", "kakuro", Op.kk, Op.KK.KkPuz.ordinal(), false);
/*     */         });
/* 152 */     this.menuItem = new JMenuItem("Quit Solving");
/* 153 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(81, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 154 */     this.menu.add(this.menuItem);
/* 155 */     this.menuItem
/* 156 */       .addActionListener(ae -> {
/*     */           Op.saveOptions("kakuro.opt", Op.kk);
/*     */           
/*     */           restoreIfDone();
/*     */           
/*     */           saveKakuro(Op.kk[Op.KK.KkPuz.ordinal()]);
/*     */           
/*     */           CrosswordExpress.transfer(100, jfSolveKakuro);
/*     */         });
/* 165 */     this.menu = new JMenu("View");
/* 166 */     menuBar.add(this.menu);
/* 167 */     this.menuItem = new JMenuItem("Display Options");
/* 168 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(89, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 169 */     this.menu.add(this.menuItem);
/* 170 */     this.menuItem
/* 171 */       .addActionListener(ae -> {
/*     */           KakuroBuild.printOptions(jfSolveKakuro, "Display Options");
/*     */           
/*     */           restoreFrame();
/*     */         });
/*     */     
/* 177 */     this.menuItem = new JMenuItem("Toggle Assist Mode");
/* 178 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(77, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 179 */     this.menu.add(this.menuItem);
/* 180 */     this.menuItem
/* 181 */       .addActionListener(ae -> {
/*     */           this.assist = !this.assist;
/*     */           
/*     */           Def.dispGuideDigits = Boolean.valueOf(this.assist);
/*     */           
/*     */           pp.repaint();
/*     */         });
/*     */     
/* 189 */     this.menu = new JMenu("Tasks");
/* 190 */     menuBar.add(this.menu);
/*     */     
/* 192 */     ActionListener errorTimer = ae -> {
/*     */         Def.dispErrors = Boolean.valueOf(false);
/*     */         pp.repaint();
/*     */         this.myTimer.stop();
/*     */       };
/* 197 */     this.myTimer = new Timer(1500, errorTimer);
/*     */     
/* 199 */     this.menuItem = new JMenuItem("Undo");
/* 200 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(85, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 201 */     this.menu.add(this.menuItem);
/* 202 */     this.menuItem
/* 203 */       .addActionListener(ae -> {
/*     */           if (undoIndex > 0) {
/*     */             do {
/*     */               undoIndex--;
/*     */               
/*     */               Grid.xstatus[Grid.xCur = this.undoX[undoIndex]][Grid.yCur = this.undoY[undoIndex]][this.undoI[undoIndex]] = 1;
/*     */               
/*     */               Grid.sol[Grid.xCur][Grid.yCur] = 0;
/*     */             } while (this.undoS[undoIndex] == 0);
/*     */           }
/*     */           pp.repaint();
/*     */         });
/* 215 */     this.menuItem = new JMenuItem("Reveal Errors");
/* 216 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(69, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 217 */     this.menu.add(this.menuItem);
/* 218 */     this.menuItem
/* 219 */       .addActionListener(ae -> {
/*     */           if (Methods.noErrors == 0) {
/*     */             this.myTimer.start();
/*     */             
/*     */             Def.dispErrors = Boolean.valueOf(true);
/*     */           } else {
/*     */             Methods.noReveal(jfSolveKakuro);
/*     */           } 
/*     */           
/*     */           pp.repaint();
/*     */         });
/* 230 */     this.menuItem = new JMenuItem("Reveal One Number");
/* 231 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(79, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 232 */     this.menu.add(this.menuItem);
/* 233 */     this.menuItem
/* 234 */       .addActionListener(ae -> {
/*     */           if (Methods.noReveal == 0) {
/*     */             char ch = (char)Grid.sol[Grid.xCur][Grid.yCur];
/*     */             
/*     */             if (ch == '\000') {
/*     */               ch = (char)Grid.letter[Grid.xCur][Grid.yCur];
/*     */               
/*     */               int i = ch - 49;
/*     */               Grid.sol[Grid.xCur][Grid.yCur] = ch;
/*     */               updateUndo(Grid.xCur, Grid.yCur, i);
/*     */               this.undoS[undoIndex - 1] = 1;
/*     */             } 
/*     */           } else {
/*     */             Methods.noReveal(jfSolveKakuro);
/*     */           } 
/*     */           restoreFrame();
/*     */         });
/* 251 */     this.menuItem = new JMenuItem("Reveal Solution");
/* 252 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(83, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 253 */     this.menu.add(this.menuItem);
/* 254 */     this.menuItem
/* 255 */       .addActionListener(ae -> {
/*     */           if (Methods.noReveal == 0) {
/*     */             for (int j = 0; j < Grid.ySz; j++) {
/*     */               for (int i = 0; i < Grid.xSz; i++) {
/*     */                 Grid.sol[i][j] = Grid.letter[i][j];
/*     */               }
/*     */             } 
/*     */             setStatus();
/*     */             undoIndex = 0;
/*     */           } else {
/*     */             Methods.noReveal(jfSolveKakuro);
/*     */           } 
/*     */           restoreFrame();
/*     */         });
/* 269 */     this.menuItem = new JMenuItem("Begin again");
/* 270 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(66, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 271 */     this.menu.add(this.menuItem);
/* 272 */     this.menuItem
/* 273 */       .addActionListener(ae -> {
/*     */           Methods.clearGrid(Grid.sol);
/*     */ 
/*     */           
/*     */           restoreFrame();
/*     */         });
/*     */     
/* 280 */     this.menu = new JMenu("Help");
/* 281 */     menuBar.add(this.menu);
/* 282 */     this.menuItem = new JMenuItem("Kakuro Help");
/* 283 */     this.menu.add(this.menuItem);
/* 284 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(72, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 285 */     this.menuItem
/* 286 */       .addActionListener(ae -> Methods.cweHelp(jfSolveKakuro, null, "Solving Kakuro Puzzles", this.kakuroSolve));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 292 */     loadKakuro(Op.kk[Op.KK.KkPuz.ordinal()]);
/* 293 */     undoIndex = 0;
/* 294 */     pp = new KakuroSolvePP(0, 37);
/* 295 */     jfSolveKakuro.add(pp);
/*     */     
/* 297 */     pp
/* 298 */       .addMouseListener(new MouseAdapter() {
/*     */           public void mousePressed(MouseEvent e) {
/* 300 */             KakuroSolve.this.updateGrid(e);
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 305 */     pp
/* 306 */       .addMouseMotionListener(new MouseAdapter() {
/*     */           public void mouseMoved(MouseEvent e) {
/* 308 */             if (Def.isMac) {
/* 309 */               KakuroSolve.jfSolveKakuro.setResizable((KakuroSolve.jfSolveKakuro.getWidth() - e.getX() < 15 && KakuroSolve.jfSolveKakuro
/* 310 */                   .getHeight() - e.getY() < 95));
/*     */             }
/*     */           }
/*     */         });
/*     */     
/* 315 */     jfSolveKakuro
/* 316 */       .addKeyListener(new KeyAdapter() {
/*     */           public void keyPressed(KeyEvent e) {
/* 318 */             KakuroSolve.this.handleKeyPressed(e);
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 323 */     restoreFrame();
/*     */   }
/*     */   
/*     */   static void restoreFrame() {
/* 327 */     jfSolveKakuro.setVisible(true);
/* 328 */     Insets insets = jfSolveKakuro.getInsets();
/* 329 */     panelW = jfSolveKakuro.getWidth() - insets.left + insets.right;
/* 330 */     panelH = jfSolveKakuro.getHeight() - insets.top + insets.bottom + 37 + menuBar.getHeight();
/* 331 */     pp.setSize(panelW, panelH);
/* 332 */     jfSolveKakuro.requestFocusInWindow();
/* 333 */     pp.repaint();
/* 334 */     Methods.infoPanel(jl1, jl2, "Solve Kakuro", "Puzzle : " + Op.kk[Op.KK.KkPuz.ordinal()], panelW);
/*     */   }
/*     */   
/*     */   static void setSizesAndOffsets(int x, int y, int width, int height, int inset) {
/* 338 */     int i = (int)((width - inset) / (Grid.xSz + 0.5D));
/* 339 */     int j = (int)((height - inset) / (Grid.ySz + 0.5D));
/* 340 */     Grid.xCell = Grid.yCell = (i < j) ? i : j;
/* 341 */     Grid.xOrg = (Def.puzzleMode == 8) ? (x + (width - (int)((Grid.xSz - 0.5D) * Grid.xCell)) / 2) : (x + 10 + Grid.xCell / 2);
/* 342 */     Grid.yOrg = (Def.puzzleMode == 8) ? (y + (height - (int)((Grid.ySz - 0.5D) * Grid.yCell)) / 2) : (y + 10 + Grid.yCell / 2);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void saveKakuro(String kakuroName) {
/*     */     try {
/* 348 */       DataOutputStream dataOut = new DataOutputStream(new FileOutputStream("kakuro/" + kakuroName));
/* 349 */       dataOut.writeInt(Grid.xSz);
/* 350 */       dataOut.writeInt(Grid.ySz);
/* 351 */       dataOut.writeByte(Methods.noReveal);
/* 352 */       dataOut.writeByte(Methods.noErrors);
/* 353 */       for (int k = 0; k < 54; k++)
/* 354 */         dataOut.writeByte(0); 
/* 355 */       for (int j = 0; j < Grid.ySz; j++) {
/* 356 */         for (int m = 0; m < Grid.xSz; m++) {
/* 357 */           dataOut.writeInt(Grid.mode[m][j]);
/* 358 */           dataOut.writeInt(Grid.sol[m][j]);
/* 359 */           dataOut.writeInt(Grid.letter[m][j]);
/*     */         } 
/* 361 */       }  dataOut.writeUTF(Methods.puzzleTitle);
/* 362 */       dataOut.writeUTF(Methods.author);
/* 363 */       dataOut.writeUTF(Methods.copyright);
/* 364 */       dataOut.writeUTF(Methods.puzzleNumber);
/* 365 */       dataOut.writeUTF(Methods.puzzleNotes);
/* 366 */       for (int i = 0; i < NodeList.nodeListLength; i++)
/* 367 */         dataOut.writeInt((NodeList.nodeList[i]).iClue); 
/* 368 */       dataOut.close();
/*     */     }
/* 370 */     catch (IOException exc) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static void loadKakuro(String kakuroName) {
/*     */     try {
/* 377 */       File fl = new File("kakuro/" + kakuroName);
/* 378 */       if (!fl.exists()) {
/* 379 */         fl = new File("kakuro/");
/* 380 */         String[] s = fl.list(); int k;
/* 381 */         for (k = 0; k < s.length && (
/* 382 */           s[k].lastIndexOf(".kakuro") == -1 || s[k].charAt(0) == '.'); k++);
/*     */         
/* 384 */         kakuroName = s[k];
/* 385 */         Op.kk[Op.KK.KkPuz.ordinal()] = kakuroName;
/*     */       } 
/*     */ 
/*     */       
/* 389 */       DataInputStream dataIn = new DataInputStream(new FileInputStream("kakuro/" + kakuroName));
/* 390 */       Grid.xSz = dataIn.readInt();
/* 391 */       Grid.ySz = dataIn.readInt();
/* 392 */       Methods.noReveal = dataIn.readByte();
/* 393 */       Methods.noErrors = dataIn.readByte(); int i;
/* 394 */       for (i = 0; i < 54; i++)
/* 395 */         dataIn.readByte(); 
/* 396 */       for (int j = 0; j < Grid.ySz; j++) {
/* 397 */         for (i = 0; i < Grid.xSz; i++) {
/* 398 */           Grid.mode[i][j] = dataIn.readInt();
/* 399 */           Grid.sol[i][j] = dataIn.readInt();
/* 400 */           Grid.letter[i][j] = dataIn.readInt();
/*     */         } 
/* 402 */       }  Methods.puzzleTitle = dataIn.readUTF();
/* 403 */       Methods.author = dataIn.readUTF();
/* 404 */       Methods.copyright = dataIn.readUTF();
/* 405 */       Methods.puzzleNumber = dataIn.readUTF();
/* 406 */       Methods.puzzleNotes = dataIn.readUTF();
/* 407 */       NodeList.buildNodeList();
/* 408 */       for (i = 0; i < NodeList.nodeListLength; i++)
/* 409 */         (NodeList.nodeList[i]).iClue = dataIn.readInt(); 
/* 410 */       dataIn.close();
/* 411 */       setStatus();
/*     */     }
/* 413 */     catch (IOException exc) {}
/*     */   }
/*     */   
/*     */   static void drawKakuro(Graphics2D g2, int[][] puzzleArray) {
/* 417 */     int[] xPoints = new int[4], yPoints = new int[4];
/*     */ 
/*     */ 
/*     */     
/* 421 */     Stroke normalStroke = new BasicStroke(Grid.xCell / 25.0F, 2, 2);
/* 422 */     Stroke diagStroke = new BasicStroke(Grid.xCell / 25.0F, 1, 2);
/*     */     
/* 424 */     RenderingHints rh = g2.getRenderingHints();
/* 425 */     rh.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
/* 426 */     g2.setRenderingHints(rh);
/*     */ 
/*     */     
/* 429 */     g2.setColor(new Color(Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.KK.KkBg.ordinal(), Op.kk) : 0));
/* 430 */     int h = Grid.yCell, w = Grid.xCell;
/* 431 */     g2.fillRect(Grid.xOrg - w / 2, Grid.yOrg - h / 2, Grid.xSz * w + w / 2, Grid.ySz * h + h / 2);
/*     */ 
/*     */     
/* 434 */     g2.setStroke(diagStroke); int j;
/* 435 */     for (j = Grid.ySz - 1; j >= 0; j--) {
/* 436 */       for (int i = Grid.xSz - 1; i >= 0; i--) {
/* 437 */         if (Grid.mode[i][j] == 0) {
/* 438 */           int theColor; if (Def.dispErrors.booleanValue() && Grid.sol[i][j] != 0 && Grid.sol[i][j] != Grid.letter[i][j]) {
/* 439 */             theColor = Op.getColorInt(Op.KK.KkError.ordinal(), Op.kk);
/*     */           } else {
/*     */             
/* 442 */             theColor = Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.KK.KkCell.ordinal(), Op.kk) : 16777215;
/* 443 */           }  g2.setColor(new Color(theColor));
/* 444 */           int x = Grid.xOrg + i * w;
/* 445 */           int y = Grid.yOrg + j * h;
/* 446 */           g2.fillRect(x, y, w, h);
/* 447 */           g2.setColor(new Color(Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.KK.KkClueBg.ordinal(), Op.kk) : 13421772));
/* 448 */           xPoints[0] = x; yPoints[0] = y;
/* 449 */           xPoints[1] = x + w; yPoints[1] = y;
/* 450 */           xPoints[2] = x + w / 2; yPoints[2] = y - h / 2;
/* 451 */           xPoints[3] = x - w / 2; yPoints[3] = y - h / 2;
/* 452 */           g2.fillPolygon(xPoints, yPoints, 4);
/* 453 */           xPoints[0] = x; yPoints[0] = y;
/* 454 */           xPoints[1] = x; yPoints[1] = y + h;
/* 455 */           xPoints[2] = x - w / 2; yPoints[2] = y + h / 2;
/* 456 */           xPoints[3] = x - w / 2; yPoints[3] = y - h / 2;
/* 457 */           g2.fillPolygon(xPoints, yPoints, 4);
/*     */           
/* 459 */           g2.setColor(new Color(Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.KK.KkBg.ordinal(), Op.kk) : 0));
/* 460 */           int inc = (i == 0 || j == 0) ? 0 : -5;
/* 461 */           g2.drawLine(x, y, x + inc - w / 2, y + inc - h / 2);
/* 462 */           inc = (j == 0) ? 0 : -2;
/* 463 */           g2.drawLine(x + w, y, x + w + inc - w / 2, y + inc - h / 2);
/* 464 */           inc = (i == 0) ? 0 : -2;
/* 465 */           g2.drawLine(x, y + h, x + inc - w / 2, y + h + inc - h / 2);
/*     */         } 
/*     */       } 
/*     */     } 
/* 469 */     g2.setColor(new Color(Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.KK.KkNumber.ordinal(), Op.kk) : 0));
/* 470 */     g2.setFont(new Font(Op.kk[Op.KK.KkFont.ordinal()], 0, 8 * Grid.yCell / 10));
/* 471 */     FontMetrics fm = g2.getFontMetrics();
/* 472 */     for (j = 0; j < Grid.ySz; j++) {
/* 473 */       for (int i = 0; i < Grid.xSz; i++) {
/* 474 */         char ch = (char)puzzleArray[i][j];
/* 475 */         if (Character.isDigit(ch)) {
/* 476 */           int x = Grid.xOrg + i * Grid.xCell;
/* 477 */           int y = Grid.yOrg + j * Grid.yCell;
/* 478 */           g2.drawString("" + ch, x + (Grid.xCell - fm.stringWidth("" + ch)) / 2, y + (Grid.yCell + fm.getAscent() - fm.getDescent()) / 2);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 484 */     g2.setColor(new Color(Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.KK.KkClue.ordinal(), Op.kk) : 0)); int fontSize;
/* 485 */     g2.setFont(new Font(Op.kk[Op.KK.KkClueFont.ordinal()], 0, fontSize = Grid.yCell / 3));
/* 486 */     fm = g2.getFontMetrics();
/* 487 */     for (int loop = 0; loop < 2; loop++) {
/* 488 */       for (int z = 0; z < NodeList.nodeListLength; z++) {
/* 489 */         if ((NodeList.nodeList[z]).direction == 0) {
/* 490 */           int x = Grid.xOrg + w * ((NodeList.nodeList[z]).x - 1);
/* 491 */           int y = Grid.yOrg + h * (NodeList.nodeList[z]).y;
/* 492 */           if ((NodeList.nodeList[z]).iClue > 0) {
/* 493 */             String s = "" + (NodeList.nodeList[z]).iClue + " ";
/* 494 */             while (fm.stringWidth(s) > 45 * Grid.xCell / 100) {
/* 495 */               fontSize--;
/* 496 */               fm = g2.getFontMetrics();
/* 497 */               g2.setFont(new Font(Op.kk[Op.KK.KkClueFont.ordinal()], 0, fontSize));
/*     */             } 
/* 499 */             if (loop == 1)
/* 500 */               g2.drawString(s, x + w - fm.stringWidth(s), y + fm.getAscent() + 1); 
/*     */           } 
/*     */         } 
/* 503 */         if ((NodeList.nodeList[z]).direction == 1) {
/* 504 */           int x = Grid.xOrg + w * (NodeList.nodeList[z]).x;
/* 505 */           int y = Grid.yOrg + h * ((NodeList.nodeList[z]).y - 1);
/* 506 */           if ((NodeList.nodeList[z]).iClue > 0) {
/* 507 */             String s = " " + (NodeList.nodeList[z]).iClue;
/* 508 */             while (fm.stringWidth(s) > 45 * Grid.xCell / 100) {
/* 509 */               fontSize--;
/* 510 */               fm = g2.getFontMetrics();
/* 511 */               g2.setFont(new Font(Op.kk[Op.KK.KkClueFont.ordinal()], 0, fontSize));
/*     */             } 
/* 513 */             if (loop == 1) {
/* 514 */               g2.drawString(s, x, y + 14 * h / 15);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 520 */     g2.setStroke(normalStroke);
/* 521 */     g2.setColor(new Color(Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.KK.KkBg.ordinal(), Op.kk) : 0));
/* 522 */     for (j = 0; j < Grid.ySz; j++) {
/* 523 */       for (int i = 0; i < Grid.xSz; i++) {
/* 524 */         if (Grid.mode[i][j] == 0) {
/* 525 */           int x = Grid.xOrg + i * w;
/* 526 */           int y = Grid.yOrg + j * h;
/* 527 */           g2.drawRect(x, y, w, h);
/*     */         } 
/*     */       } 
/* 530 */     }  g2.drawRect(Grid.xOrg - w / 2, Grid.yOrg - h / 2, Grid.xSz * w + w / 2, Grid.ySz * h + h / 2);
/*     */ 
/*     */     
/* 533 */     if (Def.dispCursor.booleanValue()) {
/* 534 */       g2.setColor(Def.COLOR_RED);
/* 535 */       g2.drawRect(Grid.xOrg + Grid.xCur * w, Grid.yOrg + Grid.yCur * h, w, h);
/*     */     } 
/*     */ 
/*     */     
/* 539 */     if (Def.dispGuideDigits.booleanValue()) {
/* 540 */       g2.setFont(new Font(Op.kk[Op.KK.KkGuideFont.ordinal()], 0, Grid.yCell / 4));
/* 541 */       g2.setColor(new Color(Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.KK.KkGuide.ordinal(), Op.kk) : 0));
/* 542 */       fm = g2.getFontMetrics();
/* 543 */       for (j = 0; j < Grid.ySz; j++) {
/* 544 */         for (int i = 0; i < Grid.xSz; i++) {
/* 545 */           if (Grid.sol[i][j] == 0 && Grid.mode[i][j] == 0)
/* 546 */             for (int k = 0; k < 9; k++) {
/* 547 */               if (Grid.xstatus[i][j][k] == 1) {
/* 548 */                 String s = "" + (k + 1);
/* 549 */                 w = (Grid.xCell / 3 - fm.stringWidth(s)) / 2;
/* 550 */                 g2.drawString(s, Grid.xOrg + i * Grid.xCell + Grid.xCell * k % 3 / 3 + w, Grid.yOrg + j * Grid.yCell + Grid.yCell * k / 3 / 3 + fm.getAscent());
/*     */               } 
/*     */             }  
/*     */         } 
/*     */       } 
/* 555 */     }  g2.setStroke(new BasicStroke(1.0F));
/*     */   }
/*     */   
/*     */   void updateUndo(int x, int y, int i) {
/* 559 */     if (Grid.xstatus[x][y][i] > 0) {
/* 560 */       Grid.xstatus[x][y][i] = 0;
/* 561 */       this.undoX[undoIndex] = x;
/* 562 */       this.undoY[undoIndex] = y;
/* 563 */       this.undoI[undoIndex] = i;
/* 564 */       this.undoS[undoIndex++] = 0;
/*     */     } 
/*     */   }
/*     */   
/*     */   void restoreIfDone() {
/* 569 */     for (int j = 0; j < Grid.ySz; j++) {
/* 570 */       for (int i = 0; i < Grid.xSz; i++)
/* 571 */       { if (Grid.sol[i][j] == 0 && Grid.mode[i][j] == 0)
/*     */           return;  } 
/* 573 */     }  Methods.clearGrid(Grid.sol);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static void setStatus() {
/* 579 */     for (int z = 0; z < 9; z++) {
/* 580 */       for (int y = 0; y < Grid.ySz; y++) {
/* 581 */         for (int x = 0; x < Grid.xSz; x++)
/* 582 */           Grid.xstatus[x][y][z] = 1; 
/*     */       } 
/*     */     } 
/*     */   } void updateGrid(MouseEvent e) {
/* 586 */     int x = e.getX(), y = e.getY();
/*     */     
/* 588 */     if (x < Grid.xOrg || y < Grid.yOrg)
/* 589 */       return;  int i = (x - Grid.xOrg) / Grid.xCell;
/* 590 */     int j = (y - Grid.yOrg) / Grid.yCell;
/* 591 */     if (i >= Grid.xSz || j >= Grid.ySz)
/* 592 */       return;  if (Grid.mode[i][j] != 0)
/* 593 */       return;  Grid.xCur = i; Grid.yCur = j;
/* 594 */     if (this.assist) {
/* 595 */       i = (x - Grid.xOrg - i * Grid.xCell) / Grid.xCell / 3;
/* 596 */       j = (y - Grid.yOrg - j * Grid.yCell) / Grid.yCell / 3;
/* 597 */       int val = j * 3 + i;
/* 598 */       if (Grid.xstatus[Grid.xCur][Grid.yCur][val] > 0) {
/* 599 */         updateUndo(Grid.xCur, Grid.yCur, val);
/* 600 */         this.undoS[undoIndex - 1] = 1; int count;
/* 601 */         for (i = 0, count = 0; i < 9; i++)
/* 602 */           count += Grid.xstatus[Grid.xCur][Grid.yCur][i]; 
/* 603 */         if (count == 0) {
/* 604 */           Grid.sol[Grid.xCur][Grid.yCur] = 49 + val;
/* 605 */           (new Thread(this.solveThread)).start();
/*     */         } 
/*     */       } 
/*     */     } 
/* 609 */     restoreFrame();
/*     */   }
/*     */   void handleKeyPressed(KeyEvent e) {
/*     */     int i;
/*     */     char ch;
/* 614 */     if (e.isAltDown())
/* 615 */       return;  switch (e.getKeyCode()) { case 38:
/* 616 */         for (i = Grid.yCur - 1; i >= 0 && Grid.mode[Grid.xCur][i] > 0; i--); if (i >= 0) Grid.yCur = i;  break;
/* 617 */       case 40: for (i = Grid.yCur + 1; i < Grid.ySz && Grid.mode[Grid.xCur][i] > 0; i++); if (i < Grid.ySz) Grid.yCur = i;  break;
/* 618 */       case 37: for (i = Grid.xCur - 1; i >= 0 && Grid.mode[i][Grid.yCur] > 0; i--); if (i >= 0) Grid.xCur = i;  break;
/* 619 */       case 39: for (i = Grid.xCur + 1; i < Grid.xSz && Grid.mode[i][Grid.yCur] > 0; i++); if (i < Grid.xSz) Grid.xCur = i;  break;
/* 620 */       case 36: for (i = 0; Grid.mode[i][Grid.yCur] > 0; i++); Grid.xCur = i; break;
/* 621 */       case 35: for (i = Grid.xSz - 1; Grid.mode[i][Grid.yCur] > 0; i--); Grid.xCur = i; break;
/* 622 */       case 33: for (i = 0; Grid.mode[Grid.xCur][i] > 0; i++); Grid.yCur = i; break;
/* 623 */       case 34: for (i = Grid.ySz - 1; Grid.mode[Grid.xCur][i] > 0; i--); Grid.yCur = i; break;
/*     */       case 8:
/*     */       case 32:
/*     */       case 127:
/* 627 */         Grid.sol[Grid.xCur][Grid.yCur] = 0;
/*     */         break;
/*     */       default:
/* 630 */         ch = e.getKeyChar();
/* 631 */         i = ch - 49;
/* 632 */         if (i >= 0 && i < 9) {
/* 633 */           if (Grid.xstatus[Grid.xCur][Grid.yCur][i] > 0) {
/* 634 */             updateUndo(Grid.xCur, Grid.yCur, i);
/* 635 */             this.undoS[undoIndex - 1] = 1;
/* 636 */             Grid.sol[Grid.xCur][Grid.yCur] = ch;
/* 637 */             (new Thread(this.solveThread)).start();
/*     */             break;
/*     */           } 
/* 640 */           Methods.noCandidate(jfSolveKakuro);
/*     */         }  break; }
/*     */     
/* 643 */     restoreFrame();
/*     */   }
/*     */ }


/* Location:              C:\Users\mustapha\Desktop\Crossword-Express.jar!\crosswordexpress\KakuroSolve.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */