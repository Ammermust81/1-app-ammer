/*     */ package crosswordexpress;
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Point;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.Stroke;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.awt.event.MouseAdapter;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JMenu;
/*     */ import javax.swing.JMenuItem;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.KeyStroke;
/*     */ 
/*     */ public final class LadderwordSolve extends JPanel {
/*  26 */   String ladderwordSolve = "<div>The process of solving a LADDER-WORD Puzzle has much in common with solving Crossword Puzzles. The clue which is presented to you at any given time is the one for the word in which the red focus cell is currently located. Any character which is typed at the keyboard will be placed into the focus cell, and the focus cell will automatically move forward to the next character of the focus word. The location of the focus cell and its corresponding word may be shifted by pointing and clicking with the mouse, or by means of the cursor control keys. Other keys which provide useful functions during the solution process are Space bar and Delete.<br/><br/></div><span class='m'>Menu Functions</span><ul><li/><span class='s'>File Menu</span><ul><li/><span>Select a Dictionary</span><br/>When loading a new puzzle, you begin by selecting the dictionary which was used to build the LADDER-WORD puzzle which you want to solve.<p/><li/><span>Load a Puzzle</span><br/>Then you choose your puzzle from the pool of LADDER-WORD puzzles currently available in the selected dictionary.<p/><li/><span>Quit Solving</span><br/>Returns you to the LADDER-WORD Construction screen.</ul><li/><span class='s'>View Menu</span><ul><li/><span>Display Options</span><br/>This leads you to a dialog box in which you can change the colors of various elements within the puzzle, and control the fonts which will be used for the puzzle's text components. You can also decide if printing of the puzzle will be done in black and white or in color.</ul><li/><span class='s'>Tasks Menu</span><ul><li/><span>Reveal One Letter</span><br/>If you need a little help to get started, this option will place the correct letter into the current focus cell.<p/><li/><span>Reveal Errors</span><br/>If you think you may have made errors, this option will show you where they are by highlighting them for a period of 1.5 seconds.<p/><li/><span>Reveal Solution</span><br/>The entire solution can be seen by selecting this option.<p/><li/><span>Begin Again</span><br/>You can restart the entire solution process at any time by selecting this option.</ul><li/><span class='s'>Help Menu</span><ul><li/><span>Ladder-word Help</span><br/>Displays the Help screen which you are now reading.</ul></ul></body>";
/*     */ 
/*     */   
/*     */   static JFrame jfSolveLadderword;
/*     */ 
/*     */   
/*     */   static JMenuBar menuBar;
/*     */ 
/*     */   
/*     */   JMenu menu;
/*     */ 
/*     */   
/*     */   JMenu submenu;
/*     */ 
/*     */   
/*     */   JMenuItem menuItem;
/*     */ 
/*     */   
/*     */   static JPanel pp;
/*     */ 
/*     */   
/*     */   static JPanel jpClue;
/*     */ 
/*     */   
/*     */   static int panelW;
/*     */   
/*     */   static int panelH;
/*     */   
/*     */   static JLabel jl1;
/*     */   
/*     */   static JLabel jl2;
/*     */   
/*     */   Timer myTimer;
/*     */   
/*     */   Runnable solveThread;
/*     */   
/*     */   static int memMode;
/*     */   
/*     */   static JLabel jlAcross;
/*     */ 
/*     */   
/*     */   LadderwordSolve(JFrame jf) {
/*  68 */     memMode = Def.puzzleMode;
/*  69 */     Def.puzzleMode = 121;
/*  70 */     Def.dispSolArray = Boolean.valueOf(true); Def.dispCursor = Boolean.valueOf(true);
/*  71 */     Grid.clearGrid();
/*     */     
/*  73 */     jfSolveLadderword = new JFrame("Solve a Ladderword Puzzle");
/*  74 */     jfSolveLadderword.setSize(Op.getInt(Op.LW.LwW.ordinal(), Op.lw), Op.getInt(Op.LW.LwH.ordinal(), Op.lw));
/*  75 */     jfSolveLadderword.getContentPane().setBackground(Def.COLOR_FRAMEBG);
/*  76 */     int frameX = (jf.getX() + jfSolveLadderword.getWidth() > Methods.scrW) ? (Methods.scrW - jfSolveLadderword.getWidth() - 10) : jf.getX();
/*  77 */     jfSolveLadderword.setLocation(frameX, jf.getY());
/*  78 */     jfSolveLadderword.setLayout((LayoutManager)null);
/*  79 */     jfSolveLadderword.setDefaultCloseOperation(0);
/*  80 */     jfSolveLadderword
/*  81 */       .addComponentListener(new ComponentAdapter()
/*     */         {
/*     */           public void componentResized(ComponentEvent ce) {
/*  84 */             int w = (LadderwordSolve.jfSolveLadderword.getWidth() < 600) ? 600 : LadderwordSolve.jfSolveLadderword.getWidth();
/*  85 */             int h = (LadderwordSolve.jfSolveLadderword.getHeight() < 500) ? 500 : LadderwordSolve.jfSolveLadderword.getHeight();
/*  86 */             LadderwordSolve.jfSolveLadderword.setSize(w, h);
/*  87 */             Op.setInt(Op.LW.LwW.ordinal(), w, Op.lw);
/*  88 */             Op.setInt(Op.LW.LwH.ordinal(), h, Op.lw);
/*  89 */             LadderwordSolve.restoreFrame();
/*     */           }
/*     */         });
/*     */     
/*  93 */     jfSolveLadderword
/*  94 */       .addWindowListener(new WindowAdapter() {
/*     */           public void windowClosing(WindowEvent we) {
/*  96 */             if (Def.selecting)
/*  97 */               return;  Op.saveOptions("ladderword.opt", Op.lw);
/*  98 */             LadderwordSolve.this.restoreIfDone();
/*  99 */             LadderwordSolve.saveLadderword(Op.lw[Op.LW.LwPuz.ordinal()]);
/* 100 */             CrosswordExpress.transfer(120, LadderwordSolve.jfSolveLadderword);
/*     */           }
/*     */         });
/*     */     
/* 104 */     Methods.closeHelp();
/*     */     
/* 106 */     jl1 = new JLabel(); jfSolveLadderword.add(jl1);
/* 107 */     jl2 = new JLabel(); jfSolveLadderword.add(jl2);
/*     */ 
/*     */     
/* 110 */     menuBar = new JMenuBar();
/* 111 */     menuBar.setBackground(Def.COLOR_MENUBAR);
/* 112 */     jfSolveLadderword.setJMenuBar(menuBar);
/* 113 */     this.menu = new JMenu("File");
/* 114 */     menuBar.add(this.menu);
/* 115 */     this.menuItem = new JMenuItem("Select a Dictionary");
/* 116 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(68, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 117 */     this.menu.add(this.menuItem);
/* 118 */     this.menuItem
/* 119 */       .addActionListener(ae -> {
/*     */           Methods.selectDictionary(jfSolveLadderword, Op.lw[Op.LW.LwDic.ordinal()], 1);
/*     */           
/*     */           if (!Methods.fileAvailable(Methods.dictionaryName + ".dic", "ladderword")) {
/*     */             JOptionPane.showMessageDialog(jfSolveLadderword, "<html>No Ladderword puzzles are available in this dictionary.<br>Use the <font color=880000>Build</font> option to create one.");
/*     */             
/*     */             return;
/*     */           } 
/*     */           
/*     */           restoreIfDone();
/*     */           
/*     */           saveLadderword(Op.lw[Op.LW.LwPuz.ordinal()]);
/*     */           
/*     */           Op.lw[Op.LW.LwDic.ordinal()] = Methods.dictionaryName;
/*     */           loadLadderword(Op.lw[Op.LW.LwPuz.ordinal()]);
/*     */           restoreFrame();
/*     */         });
/* 136 */     this.menuItem = new JMenuItem("Load a Puzzle");
/* 137 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(76, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 138 */     this.menu.add(this.menuItem);
/* 139 */     this.menuItem
/* 140 */       .addActionListener(ae -> {
/*     */           if (Def.building == 1) {
/*     */             return;
/*     */           }
/*     */           restoreIfDone();
/*     */           saveLadderword(Op.lw[Op.LW.LwPuz.ordinal()]);
/*     */           new Select(jfSolveLadderword, Op.lw[Op.LW.LwDic.ordinal()] + ".dic", "ladderword", Op.lw, Op.LW.LwPuz.ordinal(), false);
/*     */         });
/* 148 */     this.menuItem = new JMenuItem("Quit Solving");
/* 149 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(81, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 150 */     this.menu.add(this.menuItem);
/* 151 */     this.menuItem
/* 152 */       .addActionListener(ae -> {
/*     */           Op.saveOptions("ladderword.opt", Op.lw);
/*     */           
/*     */           restoreIfDone();
/*     */           
/*     */           saveLadderword(Op.lw[Op.LW.LwPuz.ordinal()]);
/*     */           
/*     */           CrosswordExpress.transfer(120, jfSolveLadderword);
/*     */         });
/* 161 */     this.menu = new JMenu("View");
/* 162 */     menuBar.add(this.menu);
/* 163 */     this.menuItem = new JMenuItem("Display Options");
/* 164 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(89, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 165 */     this.menu.add(this.menuItem);
/* 166 */     this.menuItem
/* 167 */       .addActionListener(ae -> {
/*     */           LadderwordBuild.printOptions(jfSolveLadderword, "Display Options");
/*     */ 
/*     */           
/*     */           restoreFrame();
/*     */         });
/*     */     
/* 174 */     this.menu = new JMenu("Tasks");
/* 175 */     menuBar.add(this.menu);
/* 176 */     this.menuItem = new JMenuItem("Reveal One Letter");
/* 177 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(79, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 178 */     this.menu.add(this.menuItem);
/* 179 */     this.menuItem
/* 180 */       .addActionListener(ae -> {
/*     */           if (Methods.noReveal == 0) {
/*     */             Grid.sol[Grid.xCur][Grid.yCur] = Grid.letter[Grid.xCur][Grid.yCur];
/*     */           } else {
/*     */             Methods.noReveal(jfSolveLadderword);
/*     */           } 
/*     */           
/*     */           restoreFrame();
/*     */         });
/*     */     
/* 190 */     ActionListener errorTimer = ae -> {
/*     */         Def.dispErrors = Boolean.valueOf(false);
/*     */         restoreFrame();
/*     */         this.myTimer.stop();
/*     */       };
/* 195 */     this.myTimer = new Timer(1500, errorTimer);
/*     */     
/* 197 */     this.menuItem = new JMenuItem("Reveal Errors");
/* 198 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(69, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 199 */     this.menu.add(this.menuItem);
/* 200 */     this.menuItem
/* 201 */       .addActionListener(ae -> {
/*     */           if (Methods.noErrors == 0) {
/*     */             this.myTimer.start();
/*     */             
/*     */             Def.dispErrors = Boolean.valueOf(true);
/*     */           } else {
/*     */             Methods.noReveal(jfSolveLadderword);
/*     */           } 
/*     */           
/*     */           restoreFrame();
/*     */         });
/* 212 */     this.menuItem = new JMenuItem("Reveal Solution");
/* 213 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(83, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 214 */     this.menu.add(this.menuItem);
/* 215 */     this.menuItem
/* 216 */       .addActionListener(ae -> {
/*     */           if (Methods.noReveal == 0) {
/*     */             for (int j = 0; j < Grid.ySz; j++) {
/*     */               for (int i = 0; i < Grid.xSz; i++)
/*     */                 Grid.sol[i][j] = Grid.letter[i][j]; 
/*     */             } 
/*     */           } else {
/*     */             Methods.noReveal(jfSolveLadderword);
/*     */           } 
/*     */           restoreFrame();
/*     */         });
/* 227 */     this.menuItem = new JMenuItem("Begin again");
/* 228 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(66, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 229 */     this.menu.add(this.menuItem);
/* 230 */     this.menuItem
/* 231 */       .addActionListener(ae -> {
/*     */           for (int j = 0; j < Grid.ySz; j++) {
/*     */             for (int i = 0; i < Grid.xSz; i++) {
/*     */               Grid.sol[i][j] = 0;
/*     */             }
/*     */           } 
/*     */           
/*     */           restoreFrame();
/*     */         });
/* 240 */     this.menu = new JMenu("Help");
/* 241 */     menuBar.add(this.menu);
/* 242 */     this.menuItem = new JMenuItem("Ladderword Help");
/* 243 */     this.menu.add(this.menuItem);
/* 244 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(72, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 245 */     this.menuItem
/* 246 */       .addActionListener(ae -> Methods.cweHelp(jfSolveLadderword, null, "Solving Ladderword Puzzles", this.ladderwordSolve));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 252 */     jlAcross = new JLabel("CLUE");
/* 253 */     jlAcross.setSize(70, 16);
/* 254 */     jlAcross.setLocation(10, 55);
/* 255 */     jlAcross.setHorizontalAlignment(2);
/* 256 */     jfSolveLadderword.add(jlAcross);
/*     */     
/* 258 */     jlAcross.setFont(new Font(null, 1, 16));
/* 259 */     jpClue = new TheCluePanel(210, 35, 280, 45);
/* 260 */     jfSolveLadderword.add(jpClue);
/*     */ 
/*     */     
/* 263 */     this.solveThread = (() -> {
/*     */         for (int j = 0; j < Grid.ySz; j++) {
/*     */           for (int i = 0; i < Grid.xSz; i++) {
/*     */             if (Grid.sol[i][j] != Grid.letter[i][j])
/*     */               return; 
/*     */           } 
/*     */         }  Methods.congratulations(jfSolveLadderword);
/*     */       });
/* 271 */     loadLadderword(Op.lw[Op.LW.LwPuz.ordinal()]);
/* 272 */     pp = new LadderwordSolvePP(0, 90);
/* 273 */     jfSolveLadderword.add(pp);
/*     */     
/* 275 */     pp
/* 276 */       .addMouseListener(new MouseAdapter() {
/*     */           public void mousePressed(MouseEvent e) {
/* 278 */             LadderwordSolve.this.updateGrid(e);
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 283 */     pp
/* 284 */       .addMouseMotionListener(new MouseAdapter() {
/*     */           public void mouseMoved(MouseEvent e) {
/* 286 */             if (Def.isMac) {
/* 287 */               LadderwordSolve.jfSolveLadderword.setResizable((LadderwordSolve.jfSolveLadderword.getWidth() - e.getX() < 15 && LadderwordSolve.jfSolveLadderword
/* 288 */                   .getHeight() - e.getY() < 150));
/*     */             }
/*     */           }
/*     */         });
/*     */     
/* 293 */     jfSolveLadderword
/* 294 */       .addKeyListener(new KeyAdapter() {
/*     */           public void keyPressed(KeyEvent e) {
/* 296 */             LadderwordSolve.this.handleKeyPressed(e);
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 301 */     restoreFrame();
/*     */   }
/*     */   
/*     */   static void restoreFrame() {
/* 305 */     jfSolveLadderword.setVisible(true);
/* 306 */     Insets insets = jfSolveLadderword.getInsets();
/* 307 */     panelW = jfSolveLadderword.getWidth() - insets.left + insets.right;
/* 308 */     panelH = jfSolveLadderword.getHeight() - insets.top + insets.bottom + 92 + menuBar.getHeight();
/* 309 */     pp.setSize(panelW, panelH);
/* 310 */     focusColor(true);
/* 311 */     setSizesAndOffsets(0, 0, panelW, panelH, 20);
/* 312 */     jfSolveLadderword.requestFocusInWindow();
/* 313 */     jlAcross.setLocation(Grid.xOrg, 55);
/* 314 */     jpClue.setSize(panelW - 2 * Grid.xOrg - 50, 45);
/* 315 */     jpClue.setLocation(Grid.xOrg + 50, 45);
/* 316 */     jpClue.repaint();
/* 317 */     pp.repaint();
/* 318 */     Methods.infoPanel(jl1, jl2, "Solve Ladderword", "Dictionary : " + Op.lw[Op.LW.LwDic.ordinal()] + "  -|-  Puzzle : " + Op.lw[Op.LW.LwPuz
/* 319 */           .ordinal()], panelW);
/*     */   }
/*     */   
/*     */   static void setSizesAndOffsets(int x, int y, int width, int height, int inset) {
/* 323 */     int i = (width - inset) / Grid.xSz;
/* 324 */     int j = (height - inset) / Grid.ySz;
/* 325 */     Grid.xCell = Grid.yCell = (i < j) ? i : j;
/* 326 */     Grid.xOrg = x + (width - Grid.xCell * Grid.xSz) / 2;
/* 327 */     Grid.yOrg = y + (height - Grid.yCell * Grid.ySz) / 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void saveLadderword(String ladderwordName) {
/*     */     try {
/* 333 */       DataOutputStream dataOut = new DataOutputStream(new FileOutputStream(Op.lw[Op.LW.LwDic.ordinal()] + ".dic/" + ladderwordName));
/* 334 */       dataOut.writeInt(Grid.xSz);
/* 335 */       dataOut.writeInt(Grid.ySz);
/* 336 */       dataOut.writeByte(Methods.noReveal);
/* 337 */       dataOut.writeByte(Methods.noErrors);
/* 338 */       for (int k = 0; k < 54; k++)
/* 339 */         dataOut.writeByte(0); 
/* 340 */       for (int j = 0; j < Grid.ySz; j++) {
/* 341 */         for (int m = 0; m < Grid.xSz; m++) {
/* 342 */           dataOut.writeInt(Grid.mode[m][j]);
/* 343 */           dataOut.writeInt(Grid.letter[m][j]);
/* 344 */           dataOut.writeInt(Grid.sol[m][j]);
/* 345 */           dataOut.writeInt(Grid.color[m][j]);
/*     */         } 
/* 347 */       }  dataOut.writeUTF(Methods.puzzleTitle);
/* 348 */       dataOut.writeUTF(Methods.author);
/* 349 */       dataOut.writeUTF(Methods.copyright);
/* 350 */       dataOut.writeUTF(Methods.puzzleNumber);
/* 351 */       dataOut.writeUTF(Methods.puzzleNotes);
/*     */       
/* 353 */       for (int i = 0; i < NodeList.nodeListLength; i++) {
/* 354 */         dataOut.writeUTF((NodeList.nodeList[i]).word);
/* 355 */         dataOut.writeUTF((NodeList.nodeList[i]).clue);
/*     */       } 
/* 357 */       dataOut.close();
/*     */     }
/* 359 */     catch (IOException exc) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void loadLadderword(String ladderwordName) {
/*     */     try {
/* 367 */       File fl = new File(Op.lw[Op.LW.LwDic.ordinal()] + ".dic/" + ladderwordName);
/* 368 */       if (!fl.exists()) {
/* 369 */         fl = new File(Op.lw[Op.LW.LwDic.ordinal()] + ".dic/");
/* 370 */         String[] s = fl.list(); int k;
/* 371 */         for (k = 0; k < s.length && (
/* 372 */           s[k].lastIndexOf(".ladderword") == -1 || s[k].charAt(0) == '.'); k++);
/*     */         
/* 374 */         ladderwordName = s[k];
/* 375 */         Op.lw[Op.LW.LwPuz.ordinal()] = ladderwordName;
/*     */       } 
/*     */ 
/*     */       
/* 379 */       DataInputStream dataIn = new DataInputStream(new FileInputStream(Op.lw[Op.LW.LwDic.ordinal()] + ".dic/" + ladderwordName));
/* 380 */       Grid.xSz = dataIn.readInt();
/* 381 */       Grid.ySz = dataIn.readInt();
/* 382 */       Methods.noReveal = dataIn.readByte();
/* 383 */       Methods.noErrors = dataIn.readByte(); int i;
/* 384 */       for (i = 0; i < 54; i++)
/* 385 */         dataIn.readByte(); 
/* 386 */       for (int j = 0; j < Grid.ySz; j++) {
/* 387 */         for (i = 0; i < Grid.xSz; i++) {
/* 388 */           Grid.mode[i][j] = dataIn.readInt();
/* 389 */           Grid.letter[i][j] = dataIn.readInt();
/* 390 */           Grid.sol[i][j] = dataIn.readInt();
/* 391 */           Grid.color[i][j] = dataIn.readInt();
/*     */         } 
/* 393 */       }  Methods.puzzleTitle = dataIn.readUTF();
/* 394 */       Methods.author = dataIn.readUTF();
/* 395 */       Methods.copyright = dataIn.readUTF();
/* 396 */       Methods.puzzleNumber = dataIn.readUTF();
/* 397 */       Methods.puzzleNotes = dataIn.readUTF();
/*     */       
/* 399 */       NodeList.buildNodeList();
/* 400 */       for (i = 0; i < NodeList.nodeListLength; i++) {
/* 401 */         (NodeList.nodeList[i]).word = dataIn.readUTF();
/* 402 */         (NodeList.nodeList[i]).clue = dataIn.readUTF();
/*     */       } 
/*     */       
/* 405 */       dataIn.close();
/*     */     }
/* 407 */     catch (IOException exc) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void drawLadderword(Graphics2D g2) {
/* 414 */     boolean isError = false;
/*     */     
/* 416 */     Stroke normalStroke = new BasicStroke(Grid.xCell / 20.0F, 2, 0);
/* 417 */     Stroke wideStroke = new BasicStroke(Grid.xCell / 8.0F, 0, 0);
/* 418 */     RenderingHints rh = g2.getRenderingHints();
/* 419 */     rh.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
/* 420 */     g2.setRenderingHints(rh);
/* 421 */     g2.setStroke(normalStroke);
/*     */     
/*     */     int j;
/* 424 */     for (j = 0; j < Grid.ySz; j++) {
/* 425 */       for (int k = 0; k < Grid.xSz; k++) {
/* 426 */         int theColor; if (Def.dispErrors.booleanValue() && Grid.sol[k][j] != 0 && Grid.sol[k][j] != Grid.letter[k][j]) {
/* 427 */           theColor = Op.getColorInt(Op.LW.LwError.ordinal(), Op.lw);
/* 428 */           isError = true;
/*     */         }
/* 430 */         else if (Grid.curColor[k][j] != 16777215) {
/* 431 */           theColor = Op.getColorInt(Op.LW.LwFocus.ordinal(), Op.lw);
/*     */         } else {
/* 433 */           theColor = Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.LW.LwCell.ordinal(), Op.lw) : 16777215;
/* 434 */         }  g2.setColor(new Color(theColor));
/* 435 */         g2.fillRect(Grid.xOrg + k * Grid.xCell, Grid.yOrg + j * Grid.yCell, Grid.xCell, Grid.yCell);
/*     */       } 
/* 437 */     }  if (!isError) Def.dispErrors = Boolean.valueOf(false);
/*     */ 
/*     */     
/* 440 */     g2.setFont(new Font(Op.lw[Op.LW.LwFont.ordinal()], 0, 8 * Grid.yCell / 10));
/* 441 */     g2.setColor(new Color(Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.LW.LwLetter.ordinal(), Op.lw) : 0));
/* 442 */     FontMetrics fm = g2.getFontMetrics();
/* 443 */     for (j = 0; j < Grid.ySz; j++) {
/* 444 */       for (int k = 0; k < Grid.xSz; k++) {
/* 445 */         char ch = (char)Grid.sol[k][j];
/* 446 */         if (ch != '\000') {
/* 447 */           int w = fm.stringWidth("" + ch);
/* 448 */           g2.drawString("" + ch, Grid.xOrg + k * Grid.xCell + (Grid.xCell - w) / 2, Grid.yOrg + j * Grid.yCell + 9 * Grid.yCell / 10);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 453 */     g2.setColor(new Color(Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.LW.LwGrid.ordinal(), Op.lw) : 0));
/* 454 */     for (j = 0; j < Grid.ySz; j++) {
/* 455 */       for (int k = 0; k < Grid.xSz; k++) {
/* 456 */         if (Grid.mode[k][j] != 2)
/* 457 */           g2.drawRect(Grid.xOrg + k * Grid.xCell, Grid.yOrg + j * Grid.yCell, Grid.xCell, Grid.yCell); 
/*     */       } 
/*     */     } 
/* 460 */     g2.setStroke(wideStroke);
/* 461 */     for (j = 0; j < Grid.ySz; j++) {
/* 462 */       for (int k = 0; k < Grid.xSz; k++) {
/* 463 */         int right = Grid.xOrg + (k + 1) * Grid.xCell;
/* 464 */         int bottom = Grid.yOrg + (j + 1) * Grid.yCell;
/* 465 */         switch (Grid.mode[k][j]) {
/*     */           case 3:
/*     */           case 5:
/* 468 */             g2.drawLine(right, bottom - Grid.yCell, right, bottom);
/* 469 */             if (Grid.mode[k][j] == 3);
/*     */             break;
/*     */         } 
/*     */       
/*     */       } 
/*     */     } 
/* 475 */     if (Def.dispCursor.booleanValue()) {
/* 476 */       g2.setColor(Def.COLOR_RED);
/* 477 */       g2.setStroke(wideStroke);
/* 478 */       g2.drawRect(Grid.xOrg + Grid.xCur * Grid.xCell, Grid.yOrg + Grid.yCur * Grid.yCell, Grid.xCell, Grid.yCell);
/*     */     } 
/*     */ 
/*     */     
/* 482 */     g2.setFont(new Font(Op.lw[Op.LW.LwIDFont.ordinal()], 0, Grid.yCell / 3));
/* 483 */     g2.setColor(new Color(Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.LW.LwID.ordinal(), Op.lw) : 0));
/* 484 */     fm = g2.getFontMetrics();
/* 485 */     for (int i = 0; NodeList.nodeList[i] != null; i++) {
/* 486 */       g2.drawString("" + (NodeList.nodeList[i]).id, Grid.xOrg + (NodeList.nodeList[i]).x * Grid.xCell + Grid.xCell / 8, Grid.yOrg + (NodeList.nodeList[i]).y * Grid.yCell + fm
/* 487 */           .getAscent());
/*     */     }
/* 489 */     g2.setStroke(new BasicStroke(1.0F));
/*     */   }
/*     */   void restoreIfDone() {
/*     */     int j;
/* 493 */     for (j = 0; j < Grid.ySz; j++) {
/* 494 */       for (int i = 0; i < Grid.xSz; i++)
/* 495 */       { if (Character.isLetter(Grid.letter[i][j]) && Grid.sol[i][j] != Grid.letter[i][j])
/*     */           return;  } 
/* 497 */     }  for (j = 0; j < Grid.ySz; j++) {
/* 498 */       for (int i = 0; i < Grid.xSz; i++)
/* 499 */         Grid.sol[i][j] = 0; 
/*     */     } 
/*     */   }
/*     */   static void focusColor(boolean set) {
/* 503 */     for (int i = 0; i < (NodeList.nodeList[Grid.nCur]).length; i++) {
/* 504 */       Grid.curColor[((NodeList.nodeList[Grid.nCur]).cellLoc[i]).x][((NodeList.nodeList[Grid.nCur]).cellLoc[i]).y] = set ? 61132 : 16777215;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   void changeCursor() {
/* 510 */     if (Grid.nCur != -1) focusColor(false);
/*     */     
/* 512 */     int targetNodeA = Grid.horz[Grid.xNew][Grid.yNew];
/* 513 */     int targetNodeD = Grid.vert[Grid.xNew][Grid.yNew];
/* 514 */     if (Grid.xCur == Grid.xNew && Grid.yCur == Grid.yNew) {
/* 515 */       if (targetNodeA != -1 && targetNodeD != -1) {
/* 516 */         Grid.nCur = (Grid.nCur == targetNodeA) ? targetNodeD : targetNodeA;
/*     */       }
/*     */     } else {
/* 519 */       if ((targetNodeA != Grid.nCur && targetNodeD != Grid.nCur) || Grid.nCur == -1)
/*     */       {
/*     */ 
/*     */         
/* 523 */         if (Grid.xCur == Grid.xNew) {
/* 524 */           Grid.nCur = (targetNodeD != -1) ? targetNodeD : targetNodeA;
/*     */         } else {
/* 526 */           Grid.nCur = (targetNodeA != -1) ? targetNodeA : targetNodeD;
/*     */         }  } 
/* 528 */       Grid.xCur = Grid.xNew;
/* 529 */       Grid.yCur = Grid.yNew;
/*     */     } 
/*     */     
/* 532 */     if (Grid.nCur != -1) focusColor(true); 
/*     */   }
/*     */   
/*     */   void updateGrid(MouseEvent e) {
/* 536 */     int x = e.getX(), y = e.getY();
/*     */     
/* 538 */     if (Def.dispErrors.booleanValue()) { Def.dispErrors = Boolean.valueOf(false); return; }
/* 539 */      if (x < Grid.xOrg || y < Grid.yOrg)
/* 540 */       return;  int i = (x - Grid.xOrg) / Grid.xCell;
/* 541 */     int j = (y - Grid.yOrg) / Grid.yCell;
/* 542 */     if (i >= Grid.xSz || j >= Grid.ySz)
/* 543 */       return;  if (Grid.letter[i][j] == 0)
/*     */       return; 
/* 545 */     Grid.xNew = i; Grid.yNew = j;
/* 546 */     changeCursor();
/* 547 */     Def.dispCursor = Boolean.valueOf(true);
/* 548 */     restoreFrame();
/*     */   }
/*     */ 
/*     */   
/*     */   void handleKeyPressed(KeyEvent e) {
/*     */     Point[] cellLoc;
/*     */     int i;
/* 555 */     if (e.isAltDown())
/* 556 */       return;  if (Def.dispErrors.booleanValue()) { Def.dispErrors = Boolean.valueOf(false); return; }
/* 557 */      switch (e.getKeyCode()) { case 38:
/* 558 */         if (Grid.yCur > 0) Grid.yNew = Grid.yCur - 1;  break;
/* 559 */       case 40: if (Grid.yCur < Grid.ySz - 1) Grid.yNew = Grid.yCur + 1;  break;
/* 560 */       case 37: if (Grid.xCur > 0) Grid.xNew = Grid.xCur - 1;  break;
/* 561 */       case 39: if (Grid.xCur < Grid.xSz - 1) Grid.xNew = Grid.xCur + 1;  break;
/* 562 */       case 33: Grid.yNew = 0; break;
/* 563 */       case 34: Grid.yNew = Grid.ySz - 1; break;
/* 564 */       case 36: Grid.xNew = 0; break;
/* 565 */       case 35: Grid.xNew = Grid.xSz - 1; break;
/*     */       case 32: case 127:
/* 567 */         Grid.sol[Grid.xCur][Grid.yCur] = 0; return;
/*     */       case 10: break;
/*     */       default:
/* 570 */         if (!Character.isLetter(e.getKeyChar()))
/* 571 */           return;  if (Grid.sol[Grid.xCur][Grid.yCur] != Character.toUpperCase(e.getKeyChar())) {
/* 572 */           Grid.sol[Grid.xCur][Grid.yCur] = Character.toUpperCase(e.getKeyChar());
/* 573 */           (new Thread(this.solveThread)).start();
/*     */         } 
/* 575 */         cellLoc = (NodeList.nodeList[Grid.nCur]).cellLoc;
/* 576 */         for (i = 0; i < (NodeList.nodeList[Grid.nCur]).length; i++) {
/* 577 */           if ((cellLoc[i]).x == Grid.xCur && (cellLoc[i]).y == Grid.yCur && 
/* 578 */             i < (NodeList.nodeList[Grid.nCur]).length - 1) {
/* 579 */             Grid.xNew = (cellLoc[i + 1]).x;
/* 580 */             Grid.yNew = (cellLoc[i + 1]).y; break;
/*     */           } 
/*     */         } 
/*     */         break; }
/*     */     
/* 585 */     changeCursor();
/* 586 */     restoreFrame();
/*     */   }
/*     */ }


/* Location:              C:\Users\mustapha\Desktop\Crossword-Express.jar!\crosswordexpress\LadderwordSolve.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */