/*     */ package crosswordexpress;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.KeyEvent;
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
/*     */ public final class DoubletSolve {
/*     */   static JFrame jfSolveDoublet;
/*     */   static JMenuBar menuBar;
/*     */   JMenu menu;
/*     */   JMenu submenu;
/*     */   JMenuItem menuItem;
/*     */   static JPanel pp;
/*     */   static JPanel jpClue;
/*  24 */   int xSz = 10; static int panelW; static int panelH; static JLabel jl1; static JLabel jl2; static JLabel jlClue; Timer myTimer; Runnable solveThread; int memMode; int ySz = 10; int xNew = 5; int yNew = 5; int xOrg = 10; int yOrg = 10; int xCell = 10;
/*     */   int yCell;
/*  26 */   String doubletSolve = "<div>The process of solving a DOUBLET Puzzle has much in common with solving a Crossword Puzzle. The clue which is presented to you at any given time is the one for the word in which the red focus cell is currently located. Any character which is typed at the keyboard will be placed into the focus cell, and the focus cell will automatically move forward to the next character of the focus word. The location of the focus cell and its corresponding word may be shifted by pointing and clicking with the mouse, or by means of the cursor control keys.<br/><br/></div><span class='m'>Menu Functions</span><ul><li/><span class='s'>File Menu</span><ul><li/><span>Select a Dictionary</span><br/>When loading a new puzzle, you begin by selecting the dictionary which was used to build the DOUBLET puzzle which you want to solve.<p/><li/><span>Load a Puzzle</span><br/>Then you choose your puzzle from the pool of DOUBLET puzzles currently available in the selected dictionary.<p/><li/><span>Quit Solving</span><br/>Returns you to the DOUBLET Construction screen.</ul><li/><span class='s'>View Menu</span><ul><li/><span>Display Options</span><br/>This leads you to a dialog box in which you can change the colors of various elements within the puzzle, and control the fonts which will be used for the puzzle's text components. You can also decide if printing of the puzzle will be done in black and white or in color.</ul><li/><span class='s'>Tasks Menu</span><ul><li/><span>Reveal One Letter</span><br/>If you need a little help to get started, this option will place the correct letter into the current focus cell.<p/><li/><span>Reveal Errors</span><br/>If you think you may have made errors, this option will show you where they are by highlighting them for a period of 1.5 seconds.<p/><li/><span>Reveal Solution</span><br/>The entire solution can be seen by selecting this option.<p/><li/><span>Begin Again</span><br/>You can restart the entire solution process at any time by selecting this option.</ul><li/><span class='s'>Help Menu</span><ul><li/><span>Doublet Help</span><br/>Displays the Help screen which you are now reading.</ul></ul></body>";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   DoubletSolve(JFrame jf) {
/*  71 */     this.memMode = Def.puzzleMode;
/*  72 */     Def.puzzleMode = 51;
/*  73 */     Def.dispCursor = Boolean.valueOf(true); Def.dispGuideDigits = Boolean.valueOf(false);
/*     */ 
/*     */     
/*  76 */     Grid.clearGrid();
/*  77 */     Grid.xSz = 5; Grid.ySz = 10;
/*  78 */     Grid.xCur = 0; Grid.yCur = 1;
/*     */     
/*  80 */     jfSolveDoublet = new JFrame("Solve a Doublet Puzzle");
/*  81 */     jfSolveDoublet.setSize(Op.getInt(Op.DB.DbW.ordinal(), Op.db), Op.getInt(Op.DB.DbH.ordinal(), Op.db));
/*  82 */     jfSolveDoublet.getContentPane().setBackground(Def.COLOR_FRAMEBG);
/*  83 */     int frameX = (jf.getX() + jfSolveDoublet.getWidth() > Methods.scrW) ? (Methods.scrW - jfSolveDoublet.getWidth() - 10) : jf.getX();
/*  84 */     jfSolveDoublet.setLocation(frameX, jf.getY());
/*  85 */     jfSolveDoublet.setLayout((LayoutManager)null);
/*  86 */     jfSolveDoublet.setDefaultCloseOperation(0);
/*  87 */     jfSolveDoublet
/*  88 */       .addComponentListener(new ComponentAdapter()
/*     */         {
/*     */           public void componentResized(ComponentEvent ce) {
/*  91 */             int w = (DoubletSolve.jfSolveDoublet.getWidth() < 500) ? 500 : DoubletSolve.jfSolveDoublet.getWidth();
/*  92 */             int h = (DoubletSolve.jfSolveDoublet.getHeight() < 650) ? 650 : DoubletSolve.jfSolveDoublet.getHeight();
/*  93 */             DoubletSolve.jfSolveDoublet.setSize(w, h);
/*  94 */             Op.setInt(Op.DB.DbW.ordinal(), w, Op.db);
/*  95 */             Op.setInt(Op.DB.DbH.ordinal(), h, Op.db);
/*  96 */             DoubletSolve.restoreFrame();
/*     */           }
/*     */         });
/*     */     
/* 100 */     jfSolveDoublet
/* 101 */       .addWindowListener(new WindowAdapter() {
/*     */           public void windowClosing(WindowEvent we) {
/* 103 */             if (Def.selecting)
/* 104 */               return;  Op.saveOptions("doublet.opt", Op.db);
/* 105 */             DoubletSolve.this.restoreIfDone();
/* 106 */             DoubletSolve.saveDoublet(Op.db[Op.DB.DbPuz.ordinal()]);
/* 107 */             CrosswordExpress.transfer(50, DoubletSolve.jfSolveDoublet);
/*     */           }
/*     */         });
/*     */     
/* 111 */     Methods.closeHelp();
/*     */     
/* 113 */     jl1 = new JLabel(); jfSolveDoublet.add(jl1);
/* 114 */     jl2 = new JLabel(); jfSolveDoublet.add(jl2);
/*     */ 
/*     */     
/* 117 */     menuBar = new JMenuBar();
/* 118 */     menuBar.setBackground(Def.COLOR_MENUBAR);
/* 119 */     jfSolveDoublet.setJMenuBar(menuBar);
/*     */     
/* 121 */     this.menu = new JMenu("File");
/* 122 */     menuBar.add(this.menu);
/* 123 */     this.menuItem = new JMenuItem("Select a Dictionary");
/* 124 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(68, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 125 */     this.menu.add(this.menuItem);
/* 126 */     this.menuItem
/* 127 */       .addActionListener(ae -> {
/*     */           Methods.selectDictionary(jfSolveDoublet, Op.db[Op.DB.DbDic.ordinal()], 1);
/*     */           
/*     */           if (!Methods.fileAvailable(Methods.dictionaryName + ".dic", "doublet")) {
/*     */             JOptionPane.showMessageDialog(jfSolveDoublet, "<html>No Doublet puzzles are available in this dictionary.<br>Use the <font color=880000>Build</font> option to create one.");
/*     */             
/*     */             return;
/*     */           } 
/*     */           
/*     */           restoreIfDone();
/*     */           
/*     */           saveDoublet(Op.db[Op.DB.DbPuz.ordinal()]);
/*     */           
/*     */           Op.db[Op.DB.DbDic.ordinal()] = Methods.dictionaryName;
/*     */           loadDoublet(Op.db[Op.DB.DbPuz.ordinal()]);
/*     */           restoreFrame();
/*     */         });
/* 144 */     this.menuItem = new JMenuItem("Load a Puzzle");
/* 145 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(76, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 146 */     this.menu.add(this.menuItem);
/* 147 */     this.menuItem
/* 148 */       .addActionListener(ae -> {
/*     */           if (Def.building == 1) {
/*     */             return;
/*     */           }
/*     */           restoreIfDone();
/*     */           saveDoublet(Op.db[Op.DB.DbPuz.ordinal()]);
/*     */           new Select(jfSolveDoublet, Op.db[Op.DB.DbDic.ordinal()] + ".dic", "doublet", Op.db, Op.DB.DbPuz.ordinal(), false);
/*     */         });
/* 156 */     this.menuItem = new JMenuItem("Quit Solving");
/* 157 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(81, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 158 */     this.menu.add(this.menuItem);
/* 159 */     this.menuItem
/* 160 */       .addActionListener(ae -> {
/*     */           Op.saveOptions("doublet.opt", Op.db);
/*     */           
/*     */           restoreIfDone();
/*     */           
/*     */           saveDoublet(Op.db[Op.DB.DbPuz.ordinal()]);
/*     */           
/*     */           CrosswordExpress.transfer(50, jfSolveDoublet);
/*     */         });
/* 169 */     this.menu = new JMenu("View");
/* 170 */     menuBar.add(this.menu);
/* 171 */     this.menuItem = new JMenuItem("Display Options");
/* 172 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(89, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 173 */     this.menu.add(this.menuItem);
/* 174 */     this.menuItem
/* 175 */       .addActionListener(ae -> {
/*     */           DoubletBuild.printOptions(jfSolveDoublet, "Display Options");
/*     */ 
/*     */           
/*     */           restoreFrame();
/*     */         });
/*     */     
/* 182 */     this.menu = new JMenu("Tasks");
/* 183 */     menuBar.add(this.menu);
/* 184 */     this.menuItem = new JMenuItem("Reveal One Letter");
/* 185 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(79, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 186 */     this.menu.add(this.menuItem);
/* 187 */     this.menuItem
/* 188 */       .addActionListener(ae -> {
/*     */           if (Methods.noReveal == 0) {
/*     */             Grid.sol[Grid.xCur][Grid.yCur] = Grid.letter[Grid.xCur][Grid.yCur];
/*     */           } else {
/*     */             Methods.noReveal(jfSolveDoublet);
/*     */           } 
/*     */           
/*     */           restoreFrame();
/*     */         });
/*     */     
/* 198 */     ActionListener errorTimer = ae -> {
/*     */         Def.dispErrors = Boolean.valueOf(false);
/*     */         restoreFrame();
/*     */         this.myTimer.stop();
/*     */       };
/* 203 */     this.myTimer = new Timer(1500, errorTimer);
/*     */     
/* 205 */     this.menuItem = new JMenuItem("Reveal Errors");
/* 206 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(69, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 207 */     this.menu.add(this.menuItem);
/* 208 */     this.menuItem
/* 209 */       .addActionListener(ae -> {
/*     */           if (Methods.noErrors == 0) {
/*     */             this.myTimer.start();
/*     */             
/*     */             Def.dispErrors = Boolean.valueOf(true);
/*     */           } else {
/*     */             Methods.noReveal(jfSolveDoublet);
/*     */           } 
/*     */           
/*     */           restoreFrame();
/*     */         });
/* 220 */     this.menuItem = new JMenuItem("Reveal Solution");
/* 221 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(83, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 222 */     this.menu.add(this.menuItem);
/* 223 */     this.menuItem
/* 224 */       .addActionListener(ae -> {
/*     */           if (Methods.noReveal == 0) {
/*     */             for (int j = 0; j < Grid.ySz; j++) {
/*     */               for (int i = 0; i < Grid.xSz; i++)
/*     */                 Grid.sol[i][j] = Grid.letter[i][j]; 
/*     */             } 
/*     */           } else {
/*     */             Methods.noReveal(jfSolveDoublet);
/*     */           } 
/*     */           restoreFrame();
/*     */         });
/* 235 */     this.menuItem = new JMenuItem("Begin again");
/* 236 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(66, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 237 */     this.menu.add(this.menuItem);
/* 238 */     this.menuItem
/* 239 */       .addActionListener(ae -> {
/*     */           for (int j = 0; j < Grid.ySz; j++) {
/*     */             if (Grid.color[0][j] == 16777215 || j == Grid.yCur) {
/*     */               for (int i = 0; i < Grid.xSz; i++) {
/*     */                 Grid.sol[i][j] = 0;
/*     */               }
/*     */             }
/*     */           } 
/*     */           changeFocusWord(Grid.yCur, 1);
/*     */           Grid.xCur = 0;
/*     */           Grid.yCur = 1;
/*     */           restoreFrame();
/*     */         });
/* 252 */     this.menu = new JMenu("Help");
/* 253 */     menuBar.add(this.menu);
/* 254 */     this.menuItem = new JMenuItem("Doublet Help");
/* 255 */     this.menu.add(this.menuItem);
/* 256 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(72, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 257 */     this.menuItem
/* 258 */       .addActionListener(ae -> Methods.cweHelp(jfSolveDoublet, null, "Solving Doublet Puzzles", this.doubletSolve));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 265 */     jlClue = new JLabel("Clue");
/* 266 */     jlClue.setSize(50, 16);
/* 267 */     jlClue.setLocation(10, 55);
/* 268 */     jlClue.setHorizontalAlignment(2);
/* 269 */     jfSolveDoublet.add(jlClue);
/*     */     
/* 271 */     jlClue.setFont(new Font(null, 1, 18));
/* 272 */     jpClue = new TheCluePanel(180, 35, 300, 45);
/* 273 */     jfSolveDoublet.add(jpClue);
/*     */ 
/*     */     
/* 276 */     this.solveThread = (() -> {
/*     */         for (int j = 0; j < Grid.ySz; j++) {
/*     */           for (int i = 0; i < Grid.xSz; i++) {
/*     */             if (Grid.sol[i][j] != Grid.letter[i][j])
/*     */               return; 
/*     */           } 
/*     */         }  Methods.congratulations(jfSolveDoublet);
/*     */       });
/* 284 */     loadDoublet(Op.db[Op.DB.DbPuz.ordinal()]);
/* 285 */     changeFocusWord(1, 1);
/* 286 */     pp = new DoubletSolvePP(0, 90);
/* 287 */     jfSolveDoublet.add(pp);
/*     */     
/* 289 */     pp
/* 290 */       .addMouseListener(new MouseAdapter() {
/*     */           public void mousePressed(MouseEvent e) {
/* 292 */             DoubletSolve.this.updateGrid(e);
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 297 */     pp
/* 298 */       .addMouseMotionListener(new MouseAdapter() {
/*     */           public void mouseMoved(MouseEvent e) {
/* 300 */             if (Def.isMac) {
/* 301 */               DoubletSolve.jfSolveDoublet.setResizable((DoubletSolve.jfSolveDoublet.getWidth() - e.getX() < 15 && DoubletSolve.jfSolveDoublet
/* 302 */                   .getHeight() - e.getY() < 150));
/*     */             }
/*     */           }
/*     */         });
/*     */     
/* 307 */     jfSolveDoublet
/* 308 */       .addKeyListener(new KeyAdapter() {
/*     */           public void keyPressed(KeyEvent e) {
/* 310 */             DoubletSolve.this.handleKeyPressed(e);
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 315 */     restoreFrame();
/*     */   }
/*     */   
/*     */   static void restoreFrame() {
/* 319 */     jfSolveDoublet.setVisible(true);
/* 320 */     Insets insets = jfSolveDoublet.getInsets();
/* 321 */     panelW = jfSolveDoublet.getWidth() - insets.left + insets.right;
/* 322 */     panelH = jfSolveDoublet.getHeight() - insets.top + insets.bottom + 92 + menuBar.getHeight();
/* 323 */     pp.setSize(panelW, panelH);
/* 324 */     jfSolveDoublet.requestFocusInWindow();
/* 325 */     jlClue.setLocation(Grid.xOrg, 55);
/* 326 */     jpClue.setSize(panelW - 2 * Grid.xOrg - 60, 45);
/* 327 */     jpClue.setLocation(Grid.xOrg + 60, 45);
/* 328 */     jpClue.repaint();
/* 329 */     pp.repaint();
/* 330 */     Methods.infoPanel(jl1, jl2, "Solve Doublet", "Dictionary : " + Op.db[Op.DB.DbDic.ordinal()] + "  -|-  Puzzle : " + Op.db[Op.DB.DbPuz
/* 331 */           .ordinal()], panelW);
/*     */   }
/*     */   
/*     */   static void setSizesAndOffsets(int x, int y, int width, int height, int inset) {
/* 335 */     int i = (width - inset) / Grid.xSz;
/* 336 */     int j = (height - inset) / Grid.ySz;
/* 337 */     Grid.xCell = Grid.yCell = (i < j) ? i : j;
/* 338 */     Grid.xOrg = x + (width - Grid.xCell * Grid.xSz) / 2;
/* 339 */     Grid.yOrg = y + (height - Grid.yCell * Grid.ySz) / 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void saveDoublet(String doubletName) {
/*     */     try {
/* 345 */       DataOutputStream dataOut = new DataOutputStream(new FileOutputStream(Op.db[Op.DB.DbDic.ordinal()] + ".dic/" + doubletName));
/* 346 */       dataOut.writeInt(Grid.xSz);
/* 347 */       dataOut.writeInt(Grid.ySz);
/* 348 */       dataOut.writeByte(Methods.noReveal);
/* 349 */       dataOut.writeByte(Methods.noErrors);
/* 350 */       for (int k = 0; k < 54; k++)
/* 351 */         dataOut.writeByte(0); 
/* 352 */       for (int j = 0; j < Grid.ySz; j++) {
/* 353 */         for (int m = 0; m < Grid.xSz; m++) {
/* 354 */           dataOut.writeInt(Grid.sol[m][j]);
/* 355 */           dataOut.writeInt(Grid.color[m][j]);
/* 356 */           dataOut.writeInt(Grid.letter[m][j]);
/*     */         } 
/* 358 */       }  dataOut.writeUTF(Methods.puzzleTitle);
/* 359 */       dataOut.writeUTF(Methods.author);
/* 360 */       dataOut.writeUTF(Methods.copyright);
/* 361 */       dataOut.writeUTF(Methods.puzzleNumber);
/* 362 */       dataOut.writeUTF(Methods.puzzleNotes);
/*     */       
/* 364 */       for (int i = 0; i < NodeList.nodeListLength; i++) {
/* 365 */         dataOut.writeUTF((NodeList.nodeList[i]).word);
/* 366 */         dataOut.writeUTF((NodeList.nodeList[i]).clue);
/*     */       } 
/* 368 */       dataOut.close();
/*     */     }
/* 370 */     catch (IOException exc) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static void loadDoublet(String doubletName) {
/*     */     
/* 377 */     try { File fl = new File(Op.db[Op.DB.DbDic.ordinal()] + ".dic/" + doubletName);
/* 378 */       if (!fl.exists()) {
/* 379 */         fl = new File(Op.db[Op.DB.DbDic.ordinal()] + ".dic/");
/* 380 */         String[] s = fl.list(); int k;
/* 381 */         for (k = 0; k < s.length && (
/* 382 */           s[k].lastIndexOf(".doublet") == -1 || s[k].charAt(0) == '.'); k++);
/*     */         
/* 384 */         doubletName = s[k];
/* 385 */         Op.db[Op.DB.DbPuz.ordinal()] = doubletName;
/*     */       } 
/*     */ 
/*     */       
/* 389 */       DataInputStream dataIn = new DataInputStream(new FileInputStream(Op.db[Op.DB.DbDic.ordinal()] + ".dic/" + doubletName));
/* 390 */       Grid.xSz = dataIn.readInt();
/* 391 */       Grid.ySz = dataIn.readInt();
/* 392 */       Methods.noReveal = dataIn.readByte();
/* 393 */       Methods.noErrors = dataIn.readByte(); int i;
/* 394 */       for (i = 0; i < 54; i++)
/* 395 */         dataIn.readByte(); 
/* 396 */       for (int j = 0; j < Grid.ySz; j++) {
/* 397 */         for (i = 0; i < Grid.xSz; i++) {
/* 398 */           Grid.sol[i][j] = dataIn.readInt();
/* 399 */           Grid.color[i][j] = dataIn.readInt();
/* 400 */           Grid.letter[i][j] = dataIn.readInt();
/*     */         } 
/* 402 */       }  Methods.puzzleTitle = dataIn.readUTF();
/* 403 */       Methods.author = dataIn.readUTF();
/* 404 */       Methods.copyright = dataIn.readUTF();
/* 405 */       Methods.puzzleNumber = dataIn.readUTF();
/* 406 */       Methods.puzzleNotes = dataIn.readUTF();
/*     */       
/*     */       int count;
/* 409 */       for (i = count = 0; i < Grid.ySz; i++) {
/* 410 */         if (Grid.color[0][i] != 16777215)
/* 411 */           count++; 
/*     */       }  int id;
/* 413 */       for (NodeList.nodeListLength = i = 0, id = 1; i < Grid.ySz; i++) {
/* 414 */         NodeList.nodeList[NodeList.nodeListLength] = new Node();
/* 415 */         (NodeList.nodeList[NodeList.nodeListLength]).direction = 0;
/* 416 */         (NodeList.nodeList[NodeList.nodeListLength]).id = (NodeList.nodeListLength < count) ? 0 : id++;
/* 417 */         (NodeList.nodeList[NodeList.nodeListLength]).x = 0;
/* 418 */         (NodeList.nodeList[NodeList.nodeListLength]).y = i;
/* 419 */         (NodeList.nodeList[NodeList.nodeListLength]).length = Grid.xSz;
/* 420 */         (NodeList.nodeList[NodeList.nodeListLength]).word = dataIn.readUTF();
/* 421 */         (NodeList.nodeList[NodeList.nodeListLength]).clue = dataIn.readUTF();
/* 422 */         NodeList.nodeListLength++;
/*     */       } 
/*     */       
/* 425 */       dataIn.close(); }
/*     */     
/* 427 */     catch (IOException exc) { return; }
/* 428 */      Grid.xCur = 0; Grid.yCur = 1;
/*     */   }
/*     */   
/*     */   static void drawDoublet(Graphics2D g2) {
/* 432 */     boolean isError = false;
/*     */ 
/*     */     
/* 435 */     Stroke normalStroke = new BasicStroke(Grid.xCell / 20.0F, 2, 0);
/* 436 */     Stroke wideStroke = new BasicStroke((Grid.xCell < 30) ? 4.0F : (4 * Grid.xCell / 30), 2, 0);
/* 437 */     g2.setStroke(normalStroke);
/*     */     
/* 439 */     RenderingHints rh = g2.getRenderingHints();
/* 440 */     rh.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
/* 441 */     g2.setRenderingHints(rh);
/*     */     
/*     */     int j;
/* 444 */     for (j = 0; j < Grid.ySz; j++) {
/* 445 */       for (int n = 0; n < Grid.xSz; n++) {
/* 446 */         int theColor; if (Def.dispErrors.booleanValue() && Grid.sol[n][j] != 0 && Grid.sol[n][j] != Grid.letter[n][j]) {
/* 447 */           theColor = Op.getColorInt(Op.DB.DbError.ordinal(), Op.db);
/* 448 */           isError = true;
/*     */         }
/* 450 */         else if (Grid.color[n][j] == 14548957) {
/* 451 */           theColor = Op.getColorInt(Op.DB.DbAnchor.ordinal(), Op.db);
/* 452 */         } else if (Grid.color[n][j] != 16777215) {
/* 453 */           theColor = Grid.color[n][j];
/*     */         } else {
/* 455 */           theColor = Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.DB.DbCell.ordinal(), Op.db) : 16777215;
/* 456 */         }  g2.setColor(new Color(theColor));
/* 457 */         g2.fillRect(Grid.xOrg + n * Grid.xCell, Grid.yOrg + j * Grid.yCell, Grid.xCell, Grid.yCell);
/*     */       } 
/* 459 */     }  if (!isError) Def.dispErrors = Boolean.valueOf(false);
/*     */ 
/*     */     
/* 462 */     g2.setColor(new Color(Op.getColorInt(Op.DB.DbGrid.ordinal(), Op.db)));
/* 463 */     for (j = 0; j < Grid.ySz; j++) {
/* 464 */       for (int n = 0; n < Grid.xSz; n++)
/* 465 */         g2.drawRect(Grid.xOrg + n * Grid.xCell, Grid.yOrg + j * Grid.yCell, Grid.xCell, Grid.yCell); 
/*     */     } 
/* 467 */     g2.setFont(new Font(Op.db[Op.DB.DbFont.ordinal()], 0, 8 * Grid.yCell / 10));
/* 468 */     g2.setColor(new Color(Op.getColorInt(Op.DB.DbLetter.ordinal(), Op.db)));
/* 469 */     FontMetrics fm = g2.getFontMetrics();
/* 470 */     for (int k = 0; k < Grid.ySz; k++) {
/* 471 */       for (int n = 0; n < Grid.xSz; n++) {
/* 472 */         char ch = (char)((Grid.color[n][k] == 16777215 || k == Grid.yCur) ? Grid.sol[n][k] : Grid.letter[n][k]);
/* 473 */         if (ch != '\000') {
/* 474 */           int w = fm.stringWidth("" + ch);
/* 475 */           g2.drawString("" + ch, Grid.xOrg + n * Grid.xCell + (Grid.xCell - w) / 2, Grid.yOrg + k * Grid.yCell + (Grid.yCell + fm.getAscent() - fm.getDescent()) / 2);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 480 */     g2.setColor(new Color(Op.getColorInt(Op.DB.DbID.ordinal(), Op.db)));
/* 481 */     g2.setFont(new Font(Op.db[Op.DB.DbIDFont.ordinal()], 0, Grid.yCell / 3));
/* 482 */     fm = g2.getFontMetrics();
/* 483 */     for (int i = 1, m = 0; m < Grid.ySz; m++) {
/* 484 */       if (Grid.color[0][m] == 16777215 || m == Grid.yCur) {
/* 485 */         g2.drawString("" + i++, Grid.xOrg + Grid.xCell / 10, Grid.yOrg + m * Grid.yCell + fm.getAscent());
/*     */       }
/*     */     } 
/*     */     
/* 489 */     if (Def.dispCursor.booleanValue()) {
/* 490 */       g2.setColor(Def.COLOR_RED);
/* 491 */       g2.setStroke(wideStroke);
/* 492 */       g2.drawRect(Grid.xOrg + Grid.xCur * Grid.xCell, Grid.yOrg + Grid.yCur * Grid.yCell, Grid.xCell, Grid.yCell);
/*     */     } 
/* 494 */     g2.setStroke(new BasicStroke(1.0F));
/*     */   }
/*     */ 
/*     */   
/*     */   void restoreIfDone() {
/*     */     int i;
/* 500 */     for (i = 0; i < Grid.xSz; i++)
/* 501 */       Grid.color[i][Grid.yCur] = 16777215;  int j;
/* 502 */     for (j = 0; j < Grid.ySz; j++) {
/* 503 */       for (i = 0; i < Grid.xSz; i++)
/* 504 */       { if (Grid.sol[i][j] == 0)
/*     */           return;  } 
/* 506 */     }  for (j = 0; j < Grid.ySz; j++) {
/* 507 */       if (Grid.color[0][j] == 16777215 || j == Grid.yCur)
/* 508 */         for (i = 0; i < Grid.xSz; i++)
/* 509 */           Grid.sol[i][j] = 0;  
/*     */     } 
/*     */   }
/*     */   
/*     */   void changeFocusWord(int oldWord, int newWord) {
/*     */     int i;
/* 515 */     for (i = 0; i < Grid.xSz; i++) {
/* 516 */       Grid.color[i][oldWord] = 16777215;
/* 517 */       Grid.color[i][newWord] = Op.getColorInt(Op.DB.DbFocus.ordinal(), Op.db);
/*     */     } 
/* 519 */     for (int adjust = 0; i < NodeList.nodeListLength; i++) {
/* 520 */       if (Grid.color[0][i] != 16777215 && 
/* 521 */         i > newWord)
/* 522 */         adjust++; 
/*     */     } 
/*     */   }
/*     */   void updateGrid(MouseEvent e) {
/* 526 */     int x = e.getX(), y = e.getY();
/*     */     
/* 528 */     if (Def.dispErrors.booleanValue()) { Def.dispErrors = Boolean.valueOf(false); return; }
/* 529 */      if (x < Grid.xOrg || y < Grid.yOrg)
/* 530 */       return;  int i = (x - Grid.xOrg) / Grid.xCell;
/* 531 */     int j = (y - Grid.yOrg) / Grid.yCell;
/* 532 */     if (i >= Grid.xSz || j >= Grid.ySz)
/* 533 */       return;  if (Grid.color[i][j] != 16777215 && j != Grid.yCur)
/* 534 */       return;  if (Grid.yCur != j) changeFocusWord(Grid.yCur, j); 
/* 535 */     Grid.xCur = i; Grid.yCur = j;
/* 536 */     restoreFrame();
/*     */   }
/*     */   
/*     */   void handleKeyPressed(KeyEvent e) {
/* 540 */     int i = Grid.yCur;
/*     */     
/* 542 */     if (Def.dispErrors.booleanValue()) { Def.dispErrors = Boolean.valueOf(false); return; }
/* 543 */      if (e.isAltDown())
/* 544 */       return;  switch (e.getKeyCode()) { case 38:
/* 545 */         if (Grid.yCur > 1) { Grid.yCur--;
/* 546 */           if (Grid.color[Grid.xCur][Grid.yCur] != 16777215) Grid.yCur--;  }  break;
/* 547 */       case 40: if (Grid.yCur < Grid.ySz - 2) { Grid.yCur++;
/* 548 */           if (Grid.color[Grid.xCur][Grid.yCur] != 16777215) Grid.yCur++;  }  break;
/* 549 */       case 37: if (Grid.xCur > 0) Grid.xCur--;  break;
/* 550 */       case 39: if (Grid.xCur < Grid.xSz - 1) Grid.xCur++;  break;
/* 551 */       case 36: Grid.xCur = 0; break;
/* 552 */       case 35: Grid.xCur = Grid.xSz - 1; break;
/* 553 */       case 33: Grid.yCur = 1; break;
/* 554 */       case 34: Grid.yCur = Grid.ySz - 2; break;
/*     */       case 8:
/*     */       case 127:
/* 557 */         Grid.sol[Grid.xCur][Grid.yCur] = 0; break;
/*     */       case 16:
/*     */       case 18:
/*     */         break;
/*     */       default:
/* 562 */         if (Grid.sol[Grid.xCur][Grid.yCur] != Character.toUpperCase(e.getKeyChar())) {
/* 563 */           Grid.sol[Grid.xCur][Grid.yCur] = Character.toUpperCase(e.getKeyChar());
/* 564 */           (new Thread(this.solveThread)).start();
/*     */         } 
/* 566 */         if (Grid.xCur < Grid.xSz - 1) Grid.xCur++; 
/*     */         break; }
/*     */     
/* 569 */     changeFocusWord(i, Grid.yCur);
/* 570 */     restoreFrame();
/*     */   }
/*     */ }


/* Location:              C:\Users\mustapha\Desktop\Crossword-Express.jar!\crosswordexpress\DoubletSolve.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */