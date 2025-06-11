/*     */ package crosswordexpress;
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JMenuItem;
/*     */ import javax.swing.KeyStroke;
/*     */ 
/*     */ public final class CodewordSolve extends JPanel {
/*     */   static JFrame jfSolveCodeword;
/*     */   static JMenuBar menuBar;
/*     */   JMenu menu;
/*     */   JMenu submenu;
/*     */   JMenuItem menuItem;
/*     */   static JPanel pp;
/*     */   static JPanel cwp;
/*  24 */   static char[] letter = new char[256]; static int panelW; static int panelH; static JLabel jl1; static JLabel jl2; Timer myTimer; static int memMode; Runnable solveThread; static char[] solLetter = new char[256]; static char[] usedLetter = new char[256];
/*  25 */   static int[] code = new int[256]; static int numLetters; static int rows; static int cols; static int cell; static int xOrg; static int yOrg1; static int yOrg2; static int xCur;
/*     */   static int yCur;
/*     */   
/*     */   static void def() {
/*  29 */     Op.updateOption(Op.CD.CdW.ordinal(), "1000", Op.cd);
/*  30 */     Op.updateOption(Op.CD.CdH.ordinal(), "680", Op.cd);
/*  31 */     Op.updateOption(Op.CD.CdCell.ordinal(), "FFFFE5", Op.cd);
/*  32 */     Op.updateOption(Op.CD.CdGrid.ordinal(), "003333", Op.cd);
/*  33 */     Op.updateOption(Op.CD.CdPattern.ordinal(), "003333", Op.cd);
/*  34 */     Op.updateOption(Op.CD.CdLetters.ordinal(), "006666", Op.cd);
/*  35 */     Op.updateOption(Op.CD.CdID.ordinal(), "880000", Op.cd);
/*  36 */     Op.updateOption(Op.CD.CdFocusWord.ordinal(), "55BBBB", Op.cd);
/*  37 */     Op.updateOption(Op.CD.CdError.ordinal(), "FF0000", Op.cd);
/*  38 */     Op.updateOption(Op.CD.CdUsed.ordinal(), "CCCCCC", Op.cd);
/*  39 */     Op.updateOption(Op.CD.CdCodePanel.ordinal(), "EEEE00", Op.cd);
/*  40 */     Op.updateOption(Op.CD.CdPuz.ordinal(), "sample.crossword", Op.cd);
/*  41 */     Op.updateOption(Op.CD.CdDic.ordinal(), "english", Op.cd);
/*  42 */     Op.updateOption(Op.CD.CdPuzzleFont.ordinal(), "SansSerif", Op.cd);
/*  43 */     Op.updateOption(Op.CD.CdCodeFont.ordinal(), "SansSerif", Op.cd);
/*  44 */     Op.updateOption(Op.CD.CdPuzColor.ordinal(), "false", Op.cd);
/*  45 */     Op.updateOption(Op.CD.CdSolColor.ordinal(), "false", Op.cd);
/*     */   }
/*     */   
/*  48 */   String codewordSolve = "<div>Each letter which appears in a <b>CODEWORD</b> puzzle is associated with a unique code number, and these numbers are displayed in the puzzle grid. To solve such a puzzle you must determine which letter is associated with each code.</div><p/><div>The solution process is aided by a code table which consists of a number of cells with each cell containing one code number, plus space to enter the solution letter for that code. Three of these cells will contain a letter to get you started. A cursor surrounds one of them and can be moved from cell to cell in three different ways:-</div><p/><ul><li/>By means of the cursor control keys.<p/><li/>By clicking onto a new cell using the mouse.<p/><li/>By clicking into an active cell within the puzzle itself. The cursor will move to the cell whose code corresponds to the code in the puzzle cell.<p/></ul><div>Puzzle cells whose code corresponds to the code of the cursor cell will be highlighted in a distinctive color. Typing a letter on the keyboard will place that letter into the cursor cell, and into all highlighted cells within the puzzle. A letter can also be entered by clicking an unused letter in the bottom half of the code table. When a letter has been used, its color in the code table will be changed to remind you that it is no longer available.<p/></div><span class='m'>Menu Functions</span><br?><br/><ul><li/><span class='s'>File Menu</span><br/><ul><li/><span>Select a Dictionary</span><br/>When loading a new puzzle, you begin by selecting the dictionary which was used to build the CODEWORD puzzle which you want to solve.<p/><li/><span>Load a Puzzle</span><br/>Then you choose your puzzle from the pool of CODEWORD puzzles currently available in the selected dictionary.<p/><li/><span>Quit Solving</span><br/>Returns you to either the CROSSWORD Build screen or the Crossword Express opening screen.<p/></ul><li/><span class='s'>View Menu</span><br/><ul><li/><span>Display Options</span><br/>This leads you to a dialog box in which you can change the colors of various elements within the puzzle, and control the fonts which will be used for the puzzle's text components. You can also decide if printing of the puzzle will be done in black and white or in color.<p/></ul><li/><span class='s'>Tasks Menu</span><br/><ul><li/><span>Reveal One Letter</span><br/>If you need a little help to get started, this option will place the correct letter into the current focus cell.<p/><li/><span>Reveal Errors</span><br/>If you think you may have made errors, this option will show you where they are by highlighting them for a period of 1.5 seconds.<p/><li/><span>Reveal Solution</span><br/>The entire solution can be seen by selecting this option.<p/><li/><span>Begin Again</span><br/>You can restart the entire solution process at any time by selecting this option.<p/></ul><li/><span class=s>Help Menu</span><br/><ul><li/><span>Codeword Help</span><br/>Displays the Help screen which you are now reading.<p/></ul></ul></body>";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   CodewordSolve(JFrame jf) {
/* 100 */     memMode = Def.puzzleMode;
/* 101 */     if (memMode == 4) {
/* 102 */       Op.cd[Op.CD.CdDic.ordinal()] = Op.cw[Op.CW.CwDic.ordinal()];
/* 103 */       Op.cd[Op.CD.CdPuz.ordinal()] = Op.cw[Op.CW.CwPuz.ordinal()];
/*     */     } 
/* 105 */     Def.puzzleMode = 30;
/* 106 */     Def.dispCursor = Boolean.valueOf(true);
/* 107 */     Def.dispSolArray = Boolean.valueOf(true);
/* 108 */     Def.dispGuideDigits = Boolean.valueOf(true);
/* 109 */     Grid.clearGrid();
/*     */     
/* 111 */     jfSolveCodeword = new JFrame("Solve a Codeword Puzzle");
/* 112 */     jfSolveCodeword.setSize(Op.getInt(Op.CD.CdW.ordinal(), Op.cd), Op.getInt(Op.CD.CdH.ordinal(), Op.cd));
/* 113 */     int frameX = (jf.getX() + jfSolveCodeword.getWidth() > Methods.scrW) ? (Methods.scrW - jfSolveCodeword.getWidth() - 10) : jf.getX();
/* 114 */     jfSolveCodeword.setLocation(frameX, jf.getY());
/* 115 */     jfSolveCodeword.setLayout((LayoutManager)null);
/* 116 */     jfSolveCodeword.getContentPane().setBackground(Def.COLOR_FRAMEBG);
/* 117 */     jfSolveCodeword.setDefaultCloseOperation(0);
/*     */     
/* 119 */     jfSolveCodeword
/* 120 */       .addComponentListener(new ComponentAdapter() {
/*     */           public void componentResized(ComponentEvent ce) {
/* 122 */             int oldw = Op.getInt(Op.CD.CdW.ordinal(), Op.cd);
/* 123 */             int oldh = Op.getInt(Op.CD.CdH.ordinal(), Op.cd);
/* 124 */             Methods.frameResize(CodewordSolve.jfSolveCodeword, oldw, oldh, 1000, 680);
/* 125 */             Op.setInt(Op.CD.CdW.ordinal(), CodewordSolve.jfSolveCodeword.getWidth(), Op.cd);
/* 126 */             Op.setInt(Op.CD.CdH.ordinal(), CodewordSolve.jfSolveCodeword.getHeight(), Op.cd);
/* 127 */             CodewordSolve.restoreFrame();
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 132 */     jfSolveCodeword
/* 133 */       .addWindowListener(new WindowAdapter() {
/*     */           public void windowClosing(WindowEvent we) {
/* 135 */             if (Def.selecting)
/* 136 */               return;  CodewordSolve.this.restoreIfDone();
/* 137 */             Op.saveOptions("codeword.opt", Op.cd);
/* 138 */             CodewordSolve.saveCodeword(Op.cd[Op.CD.CdPuz.ordinal()]);
/* 139 */             CrosswordExpress.transfer(4, CodewordSolve.jfSolveCodeword);
/*     */           }
/*     */         });
/*     */     
/* 143 */     Methods.closeHelp();
/*     */     
/* 145 */     jl1 = new JLabel(); jfSolveCodeword.add(jl1);
/* 146 */     jl2 = new JLabel(); jfSolveCodeword.add(jl2);
/*     */ 
/*     */     
/* 149 */     menuBar = new JMenuBar();
/* 150 */     menuBar.setBackground(Def.COLOR_MENUBAR);
/* 151 */     jfSolveCodeword.setJMenuBar(menuBar);
/*     */     
/* 153 */     this.menu = new JMenu("File");
/* 154 */     menuBar.add(this.menu);
/* 155 */     this.menuItem = new JMenuItem("Select Dictionary");
/* 156 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(68, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 157 */     this.menu.add(this.menuItem);
/* 158 */     this.menuItem
/* 159 */       .addActionListener(ae -> {
/*     */           Methods.selectDictionary(jfSolveCodeword, Op.cd[Op.CD.CdDic.ordinal()], 1);
/*     */           
/*     */           if (!Methods.fileAvailable(Methods.dictionaryName + ".dic", "crossword")) {
/*     */             JOptionPane.showMessageDialog(jfSolveCodeword, "<html>No Crossword puzzles are available in this dictionary.<br>Use the <font color=880000>Build</font> option to create one.");
/*     */             
/*     */             return;
/*     */           } 
/*     */           
/*     */           restoreIfDone();
/*     */           
/*     */           saveCodeword(Op.cd[Op.CD.CdPuz.ordinal()]);
/*     */           
/*     */           Op.cd[Op.CD.CdDic.ordinal()] = Methods.dictionaryName;
/*     */           loadCodeword(Op.cd[Op.CD.CdDic.ordinal()], Op.cd[Op.CD.CdPuz.ordinal()]);
/*     */           restoreFrame();
/*     */         });
/* 176 */     this.menuItem = new JMenuItem("Load a Puzzle");
/* 177 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(76, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 178 */     this.menu.add(this.menuItem);
/* 179 */     this.menuItem
/* 180 */       .addActionListener(ae -> {
/*     */           if (Def.building == 1) {
/*     */             return;
/*     */           }
/*     */           restoreIfDone();
/*     */           saveCodeword(Op.cd[Op.CD.CdPuz.ordinal()]);
/*     */           new Select(jfSolveCodeword, Op.cd[Op.CD.CdDic.ordinal()] + ".dic", "crossword", Op.cd, Op.CD.CdPuz.ordinal(), false);
/*     */         });
/* 188 */     this.menuItem = new JMenuItem("Quit Solving");
/* 189 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(81, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 190 */     this.menu.add(this.menuItem);
/* 191 */     this.menuItem
/* 192 */       .addActionListener(ae -> {
/*     */           restoreIfDone();
/*     */           
/*     */           Op.saveOptions("codeword.opt", Op.cd);
/*     */           
/*     */           saveCodeword(Op.cd[Op.CD.CdPuz.ordinal()]);
/*     */           
/*     */           CrosswordExpress.transfer(4, jfSolveCodeword);
/*     */         });
/* 201 */     this.menu = new JMenu("View");
/* 202 */     menuBar.add(this.menu);
/* 203 */     this.menuItem = new JMenuItem("Display Options");
/* 204 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(89, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 205 */     this.menu.add(this.menuItem);
/* 206 */     this.menuItem
/* 207 */       .addActionListener(ae -> {
/*     */           printOptions(jfSolveCodeword, "Display Options");
/*     */ 
/*     */           
/*     */           changeCursor();
/*     */         });
/*     */     
/* 214 */     this.menu = new JMenu("Task");
/* 215 */     menuBar.add(this.menu);
/* 216 */     this.menuItem = new JMenuItem("Reveal One Letter");
/* 217 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(79, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 218 */     this.menu.add(this.menuItem);
/* 219 */     this.menuItem
/* 220 */       .addActionListener(ae -> {
/*     */           if (Methods.noReveal == 0) {
/*     */             Def.dispErrors = Boolean.valueOf(false);
/*     */             
/*     */             solLetter[xCur + yCur * cols] = letter[xCur + yCur * cols];
/*     */             
/*     */             updateCodeword();
/*     */             
/*     */             changeCursor();
/*     */           } else {
/*     */             Methods.noReveal(jfSolveCodeword);
/*     */           } 
/*     */         });
/* 233 */     ActionListener errorTimer = ae -> {
/*     */         Def.dispErrors = Boolean.valueOf(false);
/*     */         restoreFrame();
/*     */         this.myTimer.stop();
/*     */       };
/* 238 */     this.myTimer = new Timer(1500, errorTimer);
/*     */     
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
/*     */             Methods.noReveal(jfSolveCodeword);
/*     */           } 
/*     */           
/*     */           restoreFrame();
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
/*     */             System.arraycopy(letter, 0, solLetter, 0, numLetters);
/*     */           } else {
/*     */             Methods.noReveal(jfSolveCodeword);
/*     */           } 
/*     */           restoreFrame();
/*     */         });
/* 272 */     this.menuItem = new JMenuItem("Begin again");
/* 273 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(66, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 274 */     this.menu.add(this.menuItem);
/* 275 */     this.menuItem
/* 276 */       .addActionListener(ae -> {
/*     */           for (int j = 0; j < Grid.ySz; j++) {
/*     */             for (int k = 0; k < Grid.xSz; k++) {
/*     */               Grid.sol[k][j] = 0;
/*     */             }
/*     */           } 
/*     */           for (int i = 0; i < numLetters; i++) {
/*     */             solLetter[i] = Character.MIN_VALUE;
/*     */           }
/*     */           allocateCodes();
/*     */           restoreFrame();
/*     */         });
/* 288 */     this.menu = new JMenu("Help");
/* 289 */     menuBar.add(this.menu);
/* 290 */     this.menuItem = new JMenuItem("Codeword Help");
/* 291 */     this.menu.add(this.menuItem);
/* 292 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(72, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 293 */     this.menuItem
/* 294 */       .addActionListener(ae -> Methods.cweHelp(jfSolveCodeword, null, "Solving a Codeword Puzzle", this.codewordSolve));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 301 */     this.solveThread = (() -> {
/*     */         for (int j = 0; j < Grid.ySz; j++) {
/*     */           for (int i = 0; i < Grid.xSz; i++) {
/*     */             if (Grid.sol[i][j] != Grid.letter[i][j])
/*     */               return; 
/*     */           } 
/*     */         }  Methods.congratulations(jfSolveCodeword);
/*     */       });
/* 309 */     pp = new CodewordSolvePP(300, 37);
/* 310 */     jfSolveCodeword.add(pp);
/* 311 */     cwp = new CodewordPanel(0, 37);
/* 312 */     jfSolveCodeword.add(cwp);
/*     */     
/* 314 */     pp
/* 315 */       .addMouseListener(new MouseAdapter() {
/*     */           public void mousePressed(MouseEvent e) {
/* 317 */             CodewordSolve.this.updateGrid(e);
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 322 */     pp
/* 323 */       .addMouseMotionListener(new MouseAdapter() {
/*     */           public void mouseMoved(MouseEvent e) {
/* 325 */             if (Def.isMac) {
/* 326 */               int i = (e.getX() - Grid.xOrg) / Grid.xCell;
/* 327 */               int j = (e.getY() - Grid.yOrg) / Grid.yCell;
/* 328 */               CodewordSolve.jfSolveCodeword.setResizable((i >= Grid.xSz && j >= Grid.ySz));
/*     */             } 
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 334 */     cwp
/* 335 */       .addMouseListener(new MouseAdapter() {
/*     */           public void mousePressed(MouseEvent e) {
/* 337 */             CodewordSolve.this.updateCWGrid(e);
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 342 */     jfSolveCodeword.addKeyListener(new KeyAdapter() { public void keyPressed(KeyEvent e) { CodewordSolve.this.handleKeyPressed(e); } });
/* 343 */     loadSolvePuzzle();
/*     */   }
/*     */   
/*     */   static void restoreFrame() {
/* 347 */     jfSolveCodeword.setVisible(true);
/* 348 */     Insets insets = jfSolveCodeword.getInsets();
/* 349 */     panelW = jfSolveCodeword.getWidth() - insets.left + insets.right;
/* 350 */     panelH = jfSolveCodeword.getHeight() - insets.top + insets.bottom + 37 + menuBar.getHeight();
/* 351 */     pp.setSize(6 * panelW / 10, panelH);
/* 352 */     pp.setLocation(4 * panelW / 10, 37);
/* 353 */     cwp.setSize(4 * panelW / 10, panelH);
/* 354 */     jfSolveCodeword.requestFocusInWindow();
/* 355 */     pp.repaint();
/* 356 */     cwp.repaint();
/* 357 */     Methods.infoPanel(jl1, jl2, "Solve Codeword", "Dictionary : " + Op.cd[Op.CD.CdDic.ordinal()] + "  -|-  Puzzle : " + Op.cd[Op.CD.CdPuz
/* 358 */           .ordinal()], panelW);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static void setSizesAndOffsets(int x, int y, int width, int height, int inset, boolean print) {
/* 364 */     int i = (width - inset) / Grid.xSz;
/* 365 */     int j = (height - inset) / Grid.ySz;
/* 366 */     Grid.xCell = Grid.yCell = (i < j) ? i : j;
/* 367 */     Grid.xOrg = x + (width - Grid.xCell * Grid.xSz) / 2;
/* 368 */     Grid.yOrg = y + (height - Grid.yCell * Grid.ySz) / 2;
/*     */   }
/*     */   
/*     */   static void printOptions(JFrame jf, String type) {
/* 372 */     String[] colorLabel = { "Cell Color", "Grid Color", "Pattern Cell Color", "Letter Color", "ID Color", "Focus Word Color", "Error Color", "Used Letter", "Code Panel" };
/* 373 */     int[] colorInt = { Op.CD.CdCell.ordinal(), Op.CD.CdGrid.ordinal(), Op.CD.CdPattern.ordinal(), Op.CD.CdLetters.ordinal(), Op.CD.CdID.ordinal(), Op.CD.CdFocusWord.ordinal(), Op.CD.CdError.ordinal(), Op.CD.CdUsed.ordinal(), Op.CD.CdCodePanel.ordinal() };
/* 374 */     String[] fontLabel = { "Select Puzzle Font", "Select Code Font" };
/* 375 */     int[] fontInt = { Op.CD.CdPuzzleFont.ordinal(), Op.CD.CdCodeFont.ordinal() };
/* 376 */     String[] checkLabel = { "PPrint Puzzle with color.", "SPrint Solution with color." };
/* 377 */     int[] checkInt = { Op.CD.CdPuzColor.ordinal(), Op.CD.CdSolColor.ordinal() };
/* 378 */     Methods.stdPrintOptions(jf, "Codeword " + type, Op.cd, colorLabel, colorInt, fontLabel, fontInt, checkLabel, checkInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void saveCodeword(String crosswordName) {
/*     */     try {
/* 389 */       DataOutputStream dataOut = new DataOutputStream(new FileOutputStream(Op.cd[Op.CD.CdDic.ordinal()] + ".dic/" + crosswordName));
/* 390 */       dataOut.writeInt(Grid.xSz);
/* 391 */       dataOut.writeInt(Grid.ySz);
/* 392 */       dataOut.writeByte(Methods.noReveal);
/* 393 */       dataOut.writeByte(Methods.noErrors); int i;
/* 394 */       for (i = 0; i < 54; i++)
/* 395 */         dataOut.writeByte(0); 
/* 396 */       for (int j = 0; j < Grid.ySz; j++) {
/* 397 */         for (i = 0; i < Grid.xSz; i++) {
/* 398 */           dataOut.writeInt(Grid.mode[i][j]);
/* 399 */           dataOut.writeInt(Grid.letter[i][j]);
/* 400 */           dataOut.writeInt(Grid.sol[i][j]);
/* 401 */           dataOut.writeInt(Grid.color[i][j]);
/*     */         } 
/* 403 */       }  dataOut.writeUTF(Methods.puzzleTitle);
/* 404 */       dataOut.writeUTF(Methods.author);
/* 405 */       dataOut.writeUTF(Methods.copyright);
/* 406 */       dataOut.writeUTF(Methods.puzzleNumber);
/* 407 */       dataOut.writeUTF(Methods.puzzleNotes);
/*     */       
/* 409 */       for (i = 0; i < NodeList.nodeListLength; i++) {
/* 410 */         dataOut.writeUTF((NodeList.nodeList[i]).word);
/* 411 */         dataOut.writeUTF((NodeList.nodeList[i]).clue);
/*     */       } 
/*     */       
/* 414 */       dataOut.close();
/*     */     }
/* 416 */     catch (IOException exc) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void loadCodeword(String dictionaryName, String crosswordName) {
/* 423 */     File fl = new File(dictionaryName + ".dic/" + crosswordName);
/* 424 */     if (!fl.exists()) {
/* 425 */       fl = new File(dictionaryName + ".dic/");
/* 426 */       String[] s = fl.list(); int i;
/* 427 */       for (i = 0; i < s.length && (
/* 428 */         s[i].lastIndexOf(".crossword") == -1 || s[i].charAt(0) == '.'); i++);
/*     */       
/* 430 */       crosswordName = s[i];
/* 431 */       Op.cd[Op.CD.CdPuz.ordinal()] = crosswordName;
/*     */     } 
/*     */     
/*     */     try {
/* 435 */       DataInputStream dataIn = new DataInputStream(new FileInputStream(dictionaryName + ".dic/" + crosswordName));
/* 436 */       Grid.xSz = dataIn.readInt();
/* 437 */       Grid.ySz = dataIn.readInt();
/* 438 */       Methods.noReveal = dataIn.readByte();
/* 439 */       Methods.noErrors = dataIn.readByte(); int i;
/* 440 */       for (i = 0; i < 54; i++)
/* 441 */         dataIn.readByte(); 
/* 442 */       for (int j = 0; j < Grid.ySz; j++) {
/* 443 */         for (i = 0; i < Grid.xSz; i++) {
/* 444 */           Grid.mode[i][j] = dataIn.readInt();
/* 445 */           Grid.letter[i][j] = dataIn.readInt();
/* 446 */           Grid.sol[i][j] = dataIn.readInt();
/* 447 */           Grid.color[i][j] = dataIn.readInt();
/*     */         } 
/* 449 */       }  Methods.puzzleTitle = dataIn.readUTF();
/* 450 */       Methods.author = dataIn.readUTF();
/* 451 */       Methods.copyright = dataIn.readUTF();
/* 452 */       Methods.puzzleNumber = dataIn.readUTF();
/* 453 */       Methods.puzzleNotes = dataIn.readUTF();
/*     */       
/* 455 */       NodeList.buildNodeList();
/*     */       
/* 457 */       for (i = 0; i < NodeList.nodeListLength; i++) {
/* 458 */         (NodeList.nodeList[i]).word = dataIn.readUTF();
/* 459 */         (NodeList.nodeList[i]).clue = dataIn.readUTF();
/*     */       } 
/*     */       
/* 462 */       dataIn.close();
/*     */     }
/* 464 */     catch (IOException exc) {}
/*     */ 
/*     */     
/* 467 */     allocateCodes();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void drawCodeword(Graphics2D g2) {
/* 474 */     boolean isError = false;
/*     */     
/* 476 */     int nL = (int)Math.ceil((Grid.xCell / 60.0F)), wL = (int)Math.ceil((Grid.xCell / 10.0F));
/* 477 */     Stroke normalStroke = new BasicStroke(nL, 2, 0);
/* 478 */     Stroke wideStroke = new BasicStroke(wL, 0, 0);
/* 479 */     g2.setStroke(normalStroke);
/*     */     
/* 481 */     RenderingHints rh = g2.getRenderingHints();
/* 482 */     rh.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
/* 483 */     g2.setRenderingHints(rh);
/*     */     
/*     */     int j;
/* 486 */     for (j = 0; j < Grid.ySz; j++) {
/* 487 */       for (int i = 0; i < Grid.xSz; i++) {
/* 488 */         if (Grid.mode[i][j] != 2)
/* 489 */         { int theColor; if (Def.dispWithColor.booleanValue()) {
/* 490 */             if (Def.dispErrors.booleanValue() && Grid.sol[i][j] != 0 && Grid.sol[i][j] != Grid.letter[i][j]) {
/* 491 */               theColor = Op.getColorInt(Op.CD.CdError.ordinal(), Op.cd);
/* 492 */               isError = true;
/*     */             }
/* 494 */             else if (Grid.curColor[i][j] != 16777215 && Def.dispCursor.booleanValue()) {
/* 495 */               theColor = Grid.curColor[i][j];
/* 496 */             } else if (Grid.color[i][j] != 16777215) {
/* 497 */               theColor = Grid.color[i][j];
/*     */             } else {
/* 499 */               theColor = Op.getColorInt(Op.CD.CdCell.ordinal(), Op.cd);
/*     */             } 
/*     */           } else {
/* 502 */             theColor = 16777215;
/* 503 */           }  g2.setColor(new Color(theColor));
/* 504 */           g2.fillRect(Grid.xOrg + i * Grid.xCell, Grid.yOrg + j * Grid.yCell, Grid.xCell, Grid.yCell); } 
/*     */       } 
/* 506 */     }  if (!isError) Def.dispErrors = Boolean.valueOf(false);
/*     */     
/* 508 */     Grid.drawPatternCells(g2, Def.dispWithColor.booleanValue() ? new Color(Op.getColorInt(Op.CD.CdPattern.ordinal(), Op.cd)) : Def.COLOR_BLACK, 
/* 509 */         Def.dispWithColor.booleanValue() ? new Color(Op.getColorInt(Op.CD.CdCell.ordinal(), Op.cd)) : Def.COLOR_WHITE, false);
/*     */ 
/*     */     
/* 512 */     g2.setColor(Def.dispWithColor.booleanValue() ? new Color(Op.getColorInt(Op.CD.CdGrid.ordinal(), Op.cd)) : Def.COLOR_BLACK);
/* 513 */     for (j = 0; j < Grid.ySz; j++) {
/* 514 */       for (int i = 0; i < Grid.xSz; i++) {
/* 515 */         if (Grid.mode[i][j] != 2)
/* 516 */           g2.drawRect(Grid.xOrg + i * Grid.xCell, Grid.yOrg + j * Grid.yCell, Grid.xCell, Grid.yCell); 
/*     */       } 
/* 518 */     }  g2.setFont(new Font(Op.cd[Op.CD.CdPuzzleFont.ordinal()], 0, 8 * Grid.yCell / 10));
/* 519 */     FontMetrics fm = g2.getFontMetrics();
/* 520 */     g2.setColor(Def.dispWithColor.booleanValue() ? new Color(Op.getColorInt(Op.CD.CdLetters.ordinal(), Op.cd)) : Def.COLOR_BLACK);
/* 521 */     for (j = 0; j < Grid.ySz; j++) {
/* 522 */       for (int i = 0; i < Grid.xSz; i++) {
/* 523 */         char ch = (char)(Def.dispSolArray.booleanValue() ? Grid.sol[i][j] : Grid.letter[i][j]);
/* 524 */         if (ch != '\000') {
/* 525 */           int w = fm.stringWidth("" + ch);
/* 526 */           g2.drawString("" + ch, Grid.xOrg + i * Grid.xCell + (Grid.xCell - w) / 2, Grid.yOrg + j * Grid.yCell + 9 * Grid.yCell / 10);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 531 */     g2.setStroke(wideStroke);
/* 532 */     Grid.drawBars(g2);
/*     */     
/* 534 */     if (Def.dispGuideDigits.booleanValue()) {
/* 535 */       g2.setFont(new Font(Op.cd[Op.CD.CdCodeFont.ordinal()], 0, Grid.yCell / 3));
/* 536 */       g2.setColor(Def.dispWithColor.booleanValue() ? new Color(Op.getColorInt(Op.CD.CdID.ordinal(), Op.cd)) : Def.COLOR_BLACK);
/* 537 */       fm = g2.getFontMetrics();
/* 538 */       for (j = 0; j < Grid.ySz; j++) {
/* 539 */         for (int i = 0; i < Grid.xSz; i++)
/* 540 */         { if (Grid.horz[i][j] > 0)
/* 541 */             g2.drawString("" + Grid.horz[i][j], Grid.xOrg + i * Grid.xCell + ((Grid.xCell / 20 > 1) ? (Grid.xCell / 20) : 1), Grid.yOrg + j * Grid.yCell + fm
/* 542 */                 .getAscent());  } 
/*     */       } 
/* 544 */     }  g2.setStroke(new BasicStroke(1.0F));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void drawCWTables(Graphics2D g2, int l, int t, int w, int h, int inset) {
/* 554 */     for (cols = 2;; cols++) {
/* 555 */       cell = (w - inset) / cols;
/* 556 */       rows = numLetters / cols + ((numLetters % cols > 0) ? 1 : 0);
/* 557 */       if (h - inset - rows * (cell + cell + cell / 2) > 10) {
/*     */         break;
/*     */       }
/*     */     } 
/* 561 */     xOrg = l + (w - cols * cell) / 2;
/* 562 */     int gap = (h - 10 - rows * (2 * cell + cell / 2)) / 2;
/* 563 */     yOrg1 = t + gap;
/* 564 */     yOrg2 = t + h - gap - rows * cell;
/*     */     
/* 566 */     Stroke normalStroke = new BasicStroke(cell / 30.0F, 2, 0);
/* 567 */     Stroke wideStroke = new BasicStroke((Grid.xCell < 30) ? 4.0F : (4 * Grid.xCell / 30), 2, 0);
/* 568 */     g2.setStroke(normalStroke);
/* 569 */     RenderingHints rh = g2.getRenderingHints();
/* 570 */     rh.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
/* 571 */     rh.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
/* 572 */     g2.setRenderingHints(rh);
/*     */     
/* 574 */     Font codeFont = new Font(Op.cd[Op.CD.CdCodeFont.ordinal()], 0, 2 * cell / 5);
/* 575 */     Font letterFont = new Font(Op.cd[Op.CD.CdPuzzleFont.ordinal()], 0, 4 * cell / 5);
/*     */     
/* 577 */     for (int i = 0; i < numLetters; i++) {
/* 578 */       int theColor, x = i % cols, y = i / cols;
/* 579 */       g2.setColor(Def.dispWithColor.booleanValue() ? new Color(Op.getColorInt(Op.CD.CdCodePanel.ordinal(), Op.cd)) : Def.COLOR_WHITE);
/* 580 */       g2.fillRect(xOrg + x * cell, yOrg1 + y * (cell + cell / 2), cell, cell / 2);
/*     */       
/* 582 */       g2.setColor(Def.dispWithColor.booleanValue() ? new Color(Op.getColorInt(Op.CD.CdGrid.ordinal(), Op.cd)) : Def.COLOR_BLACK);
/* 583 */       g2.drawRect(xOrg + x * cell, yOrg1 + y * (cell + cell / 2), cell, cell / 2);
/* 584 */       g2.setFont(codeFont);
/* 585 */       FontMetrics fm = g2.getFontMetrics(); String str;
/* 586 */       int strW = fm.stringWidth(str = "" + code[i]);
/* 587 */       g2.setColor(Def.dispWithColor.booleanValue() ? new Color(Op.getColorInt(Op.CD.CdID.ordinal(), Op.cd)) : Def.COLOR_BLACK);
/* 588 */       g2.drawString(str, xOrg + x * cell + (cell - strW) / 2, yOrg1 + y * (cell + cell / 2) + cell / 2 - cell / 10);
/*     */ 
/*     */       
/* 591 */       if (Def.dispWithColor.booleanValue()) {
/* 592 */         if (Def.dispErrors.booleanValue() && solLetter[i] != '\000' && solLetter[i] != letter[i]) {
/* 593 */           theColor = Op.getColorInt(Op.CD.CdError.ordinal(), Op.cd);
/*     */         } else {
/* 595 */           theColor = Op.getColorInt(Op.CD.CdCell.ordinal(), Op.cd);
/*     */         } 
/*     */       } else {
/* 598 */         theColor = 16777215;
/* 599 */       }  g2.setColor(new Color(theColor));
/* 600 */       g2.fillRect(xOrg + x * cell, yOrg1 + y * (cell + cell / 2) + cell / 2, cell, cell);
/*     */       
/* 602 */       g2.setColor(Def.dispWithColor.booleanValue() ? new Color(Op.getColorInt(Op.CD.CdGrid.ordinal(), Op.cd)) : Def.COLOR_BLACK);
/* 603 */       g2.drawRect(xOrg + x * cell, yOrg1 + y * (cell + cell / 2) + cell / 2, cell, cell);
/* 604 */       g2.setFont(letterFont);
/* 605 */       fm = g2.getFontMetrics();
/* 606 */       if (solLetter[i] != '\000') {
/* 607 */         strW = fm.stringWidth(str = "" + solLetter[i]);
/* 608 */         g2.setColor(Def.dispWithColor.booleanValue() ? new Color(Op.getColorInt(Op.CD.CdLetters.ordinal(), Op.cd)) : Def.COLOR_BLACK);
/* 609 */         g2.drawString(str, xOrg + x * cell + (cell - strW) / 2, yOrg1 + (y + 1) * (cell + cell / 2) - cell / 5);
/*     */       } 
/*     */ 
/*     */       
/* 613 */       g2.setColor(Def.dispWithColor.booleanValue() ? new Color(Op.getColorInt(Op.CD.CdCell.ordinal(), Op.cd)) : Def.COLOR_WHITE);
/* 614 */       g2.fillRect(xOrg + x * cell, yOrg2 + y * cell, cell, cell);
/* 615 */       g2.setColor(Def.dispWithColor.booleanValue() ? new Color(Op.getColorInt(Op.CD.CdGrid.ordinal(), Op.cd)) : Def.COLOR_BLACK);
/* 616 */       g2.drawRect(xOrg + x * cell, yOrg2 + y * cell, cell, cell); int j;
/*     */       boolean isUsed;
/* 618 */       for (isUsed = false, j = 0; j < numLetters; j++) {
/* 619 */         if (usedLetter[i] == solLetter[j])
/* 620 */           isUsed = true; 
/* 621 */       }  strW = fm.stringWidth(str = "" + usedLetter[i]);
/* 622 */       if (Def.dispWithColor.booleanValue()) {  } else {  }  g2.setColor(Def.COLOR_BLACK);
/* 623 */       g2.drawString(str, xOrg + x * cell + (cell - strW) / 2, yOrg2 + (y + 1) * cell - cell / 5);
/*     */     } 
/*     */ 
/*     */     
/* 627 */     if (Def.dispCursor.booleanValue()) {
/* 628 */       g2.setColor(Def.COLOR_RED);
/* 629 */       g2.setStroke(wideStroke);
/* 630 */       g2.drawRect(xOrg + xCur * cell, yOrg1 + yCur * (cell + cell / 2), cell, cell + cell / 2);
/*     */     } 
/*     */     
/* 633 */     g2.setStroke(new BasicStroke(1.0F));
/*     */   }
/*     */   
/*     */   static void printPuz(Graphics2D g2, int left, int top, int width, int height) {
/* 637 */     loadCodeword(Op.cw[Op.CW.CwDic.ordinal()], Op.cw[Op.CW.CwPuz.ordinal()]);
/* 638 */     for (int y = 0; y < Grid.ySz; y++) {
/* 639 */       for (int x = 0; x < Grid.xSz; x++)
/* 640 */         Grid.sol[x][y] = 0; 
/* 641 */     }  setSizesAndOffsets(left, top, width, height, 0, true);
/* 642 */     Def.dispWithColor = Op.getBool(Op.CD.CdPuzColor.ordinal(), Op.cd);
/* 643 */     Def.dispSolArray = Boolean.valueOf(true);
/* 644 */     drawCodeword(g2);
/* 645 */     Def.dispSolArray = Boolean.valueOf(false);
/* 646 */     Def.dispWithColor = Boolean.valueOf(true);
/*     */   }
/*     */   
/*     */   static void printSol(Graphics2D g2, int left, int top, int width, int height, String solutionPuzzle) {
/* 650 */     loadCodeword(Op.cw[Op.CW.CwDic.ordinal()], solutionPuzzle);
/* 651 */     setSizesAndOffsets(left, top, width, height, 0, true);
/* 652 */     Def.dispWithColor = Op.getBool(Op.CD.CdSolColor.ordinal(), Op.cd);
/* 653 */     Def.dispGuideDigits = Boolean.valueOf(false);
/* 654 */     drawCodeword(g2);
/* 655 */     Def.dispWithColor = Boolean.valueOf(true);
/* 656 */     Def.dispGuideDigits = Boolean.valueOf(true);
/* 657 */     loadCodeword(Op.cw[Op.CW.CwDic.ordinal()], Op.cw[Op.CW.CwPuz.ordinal()]);
/*     */   }
/*     */   
/*     */   static void printSolTitle(Graphics2D g2, int left, int top, int width, int height, String solutionPuzzle) {
/* 661 */     loadCodeword(Op.cw[Op.CW.CwDic.ordinal()], solutionPuzzle);
/* 662 */     Print.outputTextItem(g2, left, top, width, height, "SansSerif", 0, Methods.puzzleTitle);
/* 663 */     loadCodeword(Op.cw[Op.CW.CwDic.ordinal()], Op.cd[Op.CD.CdPuz.ordinal()]);
/*     */   }
/*     */   
/*     */   static void allocateCodes() {
/* 667 */     int z = 0, count[] = new int[256];
/*     */ 
/*     */     
/* 670 */     Methods.clearGrid(Grid.horz); int i;
/* 671 */     for (i = 0; i < 256; letter[i++] = Character.MIN_VALUE);
/*     */     int y;
/* 673 */     for (numLetters = y = 0; y < Grid.ySz; y++) {
/* 674 */       for (int j = 0; j < Grid.xSz; j++) {
/* 675 */         char ch; if (Character.isLetter(ch = (char)Grid.letter[j][y])) {
/* 676 */           for (i = 0; i <= numLetters && 
/* 677 */             ch != letter[i]; i++);
/*     */           
/* 679 */           if (i > numLetters) {
/* 680 */             letter[numLetters] = ch;
/* 681 */             usedLetter[numLetters] = ch;
/* 682 */             solLetter[numLetters] = Character.MIN_VALUE;
/* 683 */             code[numLetters] = ++numLetters;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }  int x;
/* 688 */     for (x = 0; x < numLetters - 1; x++) {
/* 689 */       for (y = x + 1; y < numLetters; y++) {
/* 690 */         if (usedLetter[x] > usedLetter[y]) {
/* 691 */           char ch = usedLetter[x]; usedLetter[x] = usedLetter[y]; usedLetter[y] = ch;
/*     */         } 
/*     */       } 
/* 694 */     }  for (i = 0; i < numLetters; i++) {
/* 695 */       for (y = 0; y < Grid.ySz; y++) {
/* 696 */         for (x = 0; x < Grid.xSz; x++) {
/* 697 */           if (letter[i] == (char)Grid.letter[x][y])
/* 698 */             Grid.horz[x][y] = code[i]; 
/*     */         } 
/*     */       } 
/* 701 */     }  for (y = 0; y < Grid.ySz; y++) {
/* 702 */       for (x = 0; x < Grid.xSz; x++) {
/* 703 */         if (Character.isLetter((char)Grid.sol[x][y]))
/* 704 */           solLetter[Grid.horz[x][y] - 1] = (char)Grid.sol[x][y]; 
/*     */       } 
/* 706 */     }  for (i = 0; i < numLetters; i++) {
/* 707 */       count[i] = 0;
/* 708 */       if (solLetter[i] != '\000')
/*     */         break; 
/*     */     } 
/* 711 */     if (i == numLetters) {
/*     */       
/* 713 */       for (y = 0; y < Grid.ySz; y++) {
/* 714 */         for (x = 0; x < Grid.xSz; x++)
/* 715 */         { if (Grid.horz[x][y] > 0)
/* 716 */             count[Grid.horz[x][y] - 1] = count[Grid.horz[x][y] - 1] + 1;  } 
/* 717 */       }  for (i = 0; i < 7; i++) {
/* 718 */         for (y = 0, x = 0; x < numLetters; x++) {
/* 719 */           if (count[x] > y) {
/* 720 */             y = count[x];
/* 721 */             z = x;
/*     */           } 
/* 723 */         }  count[z] = 0;
/* 724 */         if (i > 3)
/* 725 */           solLetter[z] = letter[z]; 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   void loadSolvePuzzle() {
/* 731 */     Grid.clearGrid();
/* 732 */     loadCodeword(Op.cd[Op.CD.CdDic.ordinal()], Op.cd[Op.CD.CdPuz.ordinal()]);
/* 733 */     changeCursor();
/* 734 */     restoreFrame();
/*     */   }
/*     */   void restoreIfDone() {
/*     */     int j;
/* 738 */     for (j = 0; j < Grid.ySz; j++) {
/* 739 */       for (int i = 0; i < Grid.xSz; i++)
/* 740 */       { if (Character.isLetter(Grid.letter[i][j]) && Grid.sol[i][j] != Grid.letter[i][j])
/*     */           return;  } 
/* 742 */     }  for (j = 0; j < Grid.ySz; j++) {
/* 743 */       for (int i = 0; i < Grid.xSz; i++)
/* 744 */         Grid.sol[i][j] = 0; 
/*     */     } 
/*     */   }
/*     */   
/*     */   void changeCursor() {
/*     */     int j;
/* 750 */     for (j = 0; j < Grid.ySz; j++) {
/* 751 */       for (int i = 0; i < Grid.xSz; i++)
/* 752 */       { if (Grid.horz[i][j] != 0)
/* 753 */           Grid.curColor[i][j] = 16777215;  } 
/* 754 */     }  for (j = 0; j < Grid.ySz; j++) {
/* 755 */       for (int i = 0; i < Grid.xSz; i++)
/* 756 */       { if (Grid.horz[i][j] == code[xCur + yCur * cols])
/* 757 */           Grid.curColor[i][j] = Op.getColorInt(Op.CD.CdFocusWord.ordinal(), Op.cd);  } 
/* 758 */     }  restoreFrame();
/*     */   }
/*     */   
/*     */   void updateCWGrid(MouseEvent e) {
/* 762 */     int x = e.getX(), y = e.getY();
/*     */     
/* 764 */     if (Def.dispErrors.booleanValue()) { Def.dispErrors = Boolean.valueOf(false); restoreFrame(); return; }
/* 765 */      if (x < xOrg || y < yOrg1)
/* 766 */       return;  int i = (x - xOrg) / cell;
/* 767 */     int j = (y - yOrg1) / (cell + cell / 2);
/* 768 */     if (i >= cols)
/* 769 */       return;  if (j >= rows) {
/* 770 */       if (y < yOrg2)
/* 771 */         return;  j = (y - yOrg2) / cell;
/* 772 */       if (i + j * cols >= numLetters)
/*     */         return; 
/* 774 */       for (int v = 0; v < numLetters; v++) {
/* 775 */         if (solLetter[v] == usedLetter[i + j * cols])
/*     */           return; 
/* 777 */       }  solLetter[xCur + cols * yCur] = usedLetter[i + j * cols];
/* 778 */       updateCodeword();
/* 779 */       changeCursor();
/* 780 */       (new Thread(this.solveThread)).start();
/*     */       return;
/*     */     } 
/* 783 */     if (i + j * cols >= numLetters)
/*     */       return; 
/* 785 */     xCur = i; yCur = j;
/* 786 */     changeCursor();
/*     */   }
/*     */   
/*     */   void updateGrid(MouseEvent e) {
/* 790 */     int x = e.getX(), y = e.getY();
/*     */     
/* 792 */     if (Def.dispErrors.booleanValue()) { Def.dispErrors = Boolean.valueOf(false); restoreFrame(); return; }
/* 793 */      if (x < Grid.xOrg || y < Grid.yOrg)
/* 794 */       return;  int i = (x - Grid.xOrg) / Grid.xCell;
/* 795 */     int j = (y - Grid.yOrg) / Grid.yCell;
/* 796 */     if (i >= Grid.xSz || j >= Grid.ySz)
/* 797 */       return;  if (Grid.letter[i][j] == 0)
/*     */       return; 
/* 799 */     xCur = (Grid.horz[i][j] - 1) % cols; yCur = (Grid.horz[i][j] - 1) / cols;
/* 800 */     changeCursor();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void handleKeyPressed(KeyEvent e) {
/* 806 */     if (e.isAltDown())
/* 807 */       return;  if (Def.dispErrors.booleanValue()) { Def.dispErrors = Boolean.valueOf(false); restoreFrame(); return; }
/* 808 */      char ch; switch (ch = (char)e.getKeyCode()) { case '&':
/* 809 */         if (yCur > 0) yCur--;  break;
/* 810 */       case '(': if (xCur + (yCur + 1) * cols < numLetters) yCur++;  break;
/* 811 */       case '%': if (xCur > 0) xCur--;  break;
/* 812 */       case '\'': if (xCur < cols - 1 && xCur + yCur * cols < numLetters - 1) xCur++;  break;
/* 813 */       case '$': xCur = 0; break;
/* 814 */       case '#': for (xCur = cols - 1; xCur + yCur * cols > numLetters - 1; xCur--); break;
/* 815 */       case '!': yCur = 0; break;
/* 816 */       case '"': for (yCur = rows - 1; xCur + yCur * cols > numLetters - 1; yCur--); break;
/* 817 */       case '': solLetter[xCur + yCur * cols] = Character.MIN_VALUE; break;
/*     */       case '\n': break;
/*     */       default:
/* 820 */         if (ch == ' ') {
/* 821 */           solLetter[xCur + yCur * cols] = Character.MIN_VALUE;
/*     */         } else {
/* 823 */           if (!Character.isLetter(ch = e.getKeyChar()))
/* 824 */             return;  solLetter[xCur + yCur * cols] = Character.toUpperCase(ch);
/*     */         } 
/* 826 */         updateCodeword();
/* 827 */         (new Thread(this.solveThread)).start();
/*     */         break; }
/*     */     
/* 830 */     changeCursor();
/* 831 */     restoreFrame();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static void updateCodeword() {
/* 837 */     for (int j = 0; j < Grid.ySz; j++) {
/* 838 */       for (int i = 0; i < Grid.xSz; i++) {
/* 839 */         if (Grid.horz[i][j] == code[xCur + yCur * cols])
/* 840 */           Grid.sol[i][j] = solLetter[xCur + yCur * cols]; 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mustapha\Desktop\Crossword-Express.jar!\crosswordexpress\CodewordSolve.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */