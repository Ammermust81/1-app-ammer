/*     */ package crosswordexpress;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import javax.swing.JMenuItem;
/*     */ 
/*     */ public final class AcrosticSolve {
/*     */   static JFrame jfSolveAcrostic;
/*     */   static JMenuBar menuBar;
/*     */   JMenu menu;
/*     */   JMenu submenu;
/*     */   JMenuItem menuItem;
/*     */   static JPanel ppParagraph;
/*     */   static JPanel ppWords;
/*     */   static JPanel jpClue;
/*     */   static int panelParagraphW;
/*     */   static int panelParagraphH;
/*     */   static int panelWordsW;
/*     */   static int panelWordsH;
/*     */   static int panelParagraphX;
/*  23 */   static int keyCol = 0; static int panelParagraphY; static int panelWordsX; static int panelWordsY; static JLabel jl1; static JLabel jl2; Timer myTimer; static int memMode; static int width; static int height; Runnable solveThread; static JLabel jlClue; static boolean cursorInWords; static int acrosticLen;
/*  24 */   static int[] acSol = new int[512];
/*  25 */   static int[] acColor = new int[512];
/*     */   
/*  27 */   String acrosticSolve = "<div>The clue which is displayed at any point applies to that word in the word list which currently contains the focus cell. This cell will be outlined in either red or blue.<p/>Any character which is typed at the keyboard will be placed into both the red and blue focus cells, and the red focus cell will automatically move forward to the next character of the word in which it is located. The blue focus cell will be relocated to the cell which has the same identifying number as the red focus cell.<p/>The location of the red focus cell may be shifted by pointing and clicking with the mouse, or by means of the cursor control keys (Up, Down, Left, Right, Page Up, Page Down, Home, End). Each time the red cursor cell is moved, the blue cursor will also be relocated to the cell containing the same identifying number. Other keys which provide useful functions during the solution process are Space bar, Back Space, Delete and Return.<p/></div><span class='m'>Menu Functions</span><br/><br/><ul><li/><span class='s'>File Menu</span><ul><li/><span>Select a Dictionary</span><br/>When loading a new puzzle, you begin by selecting the dictionary which was used to build the ACROSTIC puzzle which you want to solve.<p/><li/><span>Load a Puzzle</span><br/>Then you choose your puzzle from the pool of ACROSTIC puzzles currently available in the selected dictionary.<p/><li/><span>Quit Solving</span><br/>Returns you to either the ACROSTIC Build screen or the Crossword Express opening screen.<p/></ul><li/><span class='s'>View Menu</span><ul><li/><span>Display Options</span><br/>This leads you to a dialog box in which you can change the colors of various elements within the puzzle, and control the fonts which will be used for the puzzle's text components. You can also decide if printing of the puzzle will be done in black and white or in color.<p/></ul><li/><span class='s'>Tasks Menu</span><ul><li/><span>Reveal One Letter</span><br/>If you need a little help to get started, this option will place the correct letter into the current focus cell.<p/><li/><span>Reveal Errors</span><br/>If you think you may have made errors, this option will show you where they are by highlighting them for a period of 1.5 seconds.<p/><li/><span>Reveal Solution</span><br/>The entire solution can be seen by selecting this option.<p/><li/><span>Begin Again</span><br/>You can restart the entire solution process at any time by selecting this option.<p/></ul><li/><span class='s'>Help Menu</span><ul><li/><span>Acrostic Help</span><br/>Displays the Help screen which you are now reading.<p/></ul></ul></body>";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   AcrosticSolve(JFrame jf) {
/*  73 */     memMode = Def.puzzleMode;
/*  74 */     Def.puzzleMode = 11;
/*  75 */     Def.dispSolArray = Boolean.valueOf(true); Def.dispCursor = Boolean.valueOf(true); Def.dispNullCells = Boolean.valueOf(false);
/*  76 */     Grid.clearGrid();
/*  77 */     Grid.xSzW = Grid.ySzW = 10;
/*     */     
/*  79 */     jfSolveAcrostic = new JFrame("Solve an Acrostic Puzzle");
/*  80 */     jfSolveAcrostic.setSize(Op.getInt(Op.AC.AcW.ordinal(), Op.ac), Op.getInt(Op.AC.AcH.ordinal(), Op.ac));
/*  81 */     int frameX = (jf.getX() + jfSolveAcrostic.getWidth() > Methods.scrW) ? (Methods.scrW - jfSolveAcrostic.getWidth() - 10) : jf.getX();
/*  82 */     jfSolveAcrostic.setLocation(frameX, jf.getY());
/*  83 */     jfSolveAcrostic.setLayout((LayoutManager)null);
/*  84 */     jfSolveAcrostic.getContentPane().setBackground(Def.COLOR_FRAMEBG);
/*  85 */     jfSolveAcrostic.setDefaultCloseOperation(0);
/*  86 */     jfSolveAcrostic
/*  87 */       .addComponentListener(new ComponentAdapter()
/*     */         {
/*     */           public void componentResized(ComponentEvent ce) {
/*  90 */             int w = (AcrosticSolve.jfSolveAcrostic.getWidth() < 600) ? 600 : AcrosticSolve.jfSolveAcrostic.getWidth();
/*  91 */             int h = (AcrosticSolve.jfSolveAcrostic.getHeight() < 700) ? 700 : AcrosticSolve.jfSolveAcrostic.getHeight();
/*  92 */             AcrosticSolve.jfSolveAcrostic.setSize(w, h);
/*  93 */             Op.setInt(Op.AC.AcW.ordinal(), w, Op.ac);
/*  94 */             Op.setInt(Op.AC.AcH.ordinal(), h, Op.ac);
/*  95 */             AcrosticSolve.restoreFrame();
/*     */           }
/*     */         });
/*     */     
/*  99 */     jfSolveAcrostic
/* 100 */       .addWindowListener(new WindowAdapter() {
/*     */           public void windowClosing(WindowEvent we) {
/* 102 */             if (Def.selecting)
/* 103 */               return;  Op.saveOptions("acrostic.opt", Op.ac);
/* 104 */             AcrosticSolve.this.restoreIfDone();
/* 105 */             AcrosticSolve.saveAcrostic(Op.ac[Op.AC.AcPuz.ordinal()]);
/* 106 */             CrosswordExpress.transfer(10, AcrosticSolve.jfSolveAcrostic);
/*     */           }
/*     */         });
/*     */     
/* 110 */     Methods.closeHelp();
/*     */     
/* 112 */     jl1 = new JLabel(); jfSolveAcrostic.add(jl1);
/* 113 */     jl2 = new JLabel(); jfSolveAcrostic.add(jl2);
/*     */ 
/*     */     
/* 116 */     menuBar = new JMenuBar();
/* 117 */     menuBar.setBackground(Def.COLOR_MENUBAR);
/* 118 */     jfSolveAcrostic.setJMenuBar(menuBar);
/* 119 */     this.menu = new JMenu("File");
/* 120 */     menuBar.add(this.menu);
/* 121 */     this.menuItem = new JMenuItem("Select a Dictionary");
/* 122 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(68, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 123 */     this.menu.add(this.menuItem);
/* 124 */     this.menuItem
/* 125 */       .addActionListener(ae -> {
/*     */           Methods.selectDictionary(jfSolveAcrostic, Op.ac[Op.AC.AcDic.ordinal()], 1);
/*     */           
/*     */           if (!Methods.fileAvailable(Methods.dictionaryName + ".dic", "acrostic")) {
/*     */             JOptionPane.showMessageDialog(jfSolveAcrostic, "<html>No Acrostic puzzles are available in this dictionary.<br>Use the <font color=880000>Build</font> option to create one.");
/*     */             
/*     */             return;
/*     */           } 
/*     */           
/*     */           restoreIfDone();
/*     */           
/*     */           saveAcrostic(Op.ac[Op.AC.AcPuz.ordinal()]);
/*     */           
/*     */           Op.ac[Op.AC.AcDic.ordinal()] = Methods.dictionaryName;
/*     */           loadAcrostic(Op.ac[Op.AC.AcPuz.ordinal()]);
/*     */           restoreFrame();
/*     */         });
/* 142 */     this.menuItem = new JMenuItem("Load a Puzzle");
/* 143 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(76, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 144 */     this.menu.add(this.menuItem);
/* 145 */     this.menuItem
/* 146 */       .addActionListener(ae -> {
/*     */           if (Def.building == 1) {
/*     */             return;
/*     */           }
/*     */           restoreIfDone();
/*     */           saveAcrostic(Op.ac[Op.AC.AcPuz.ordinal()]);
/*     */           new Select(jfSolveAcrostic, Op.ac[Op.AC.AcDic.ordinal()] + ".dic", "acrostic", Op.ac, Op.AC.AcPuz.ordinal(), false);
/*     */         });
/* 154 */     this.menuItem = new JMenuItem("Quit Solving");
/* 155 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(81, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 156 */     this.menu.add(this.menuItem);
/* 157 */     this.menuItem
/* 158 */       .addActionListener(ae -> {
/*     */           Op.saveOptions("acrostic.opt", Op.ac);
/*     */           
/*     */           restoreIfDone();
/*     */           
/*     */           saveAcrostic(Op.ac[Op.AC.AcPuz.ordinal()]);
/*     */           
/*     */           CrosswordExpress.transfer(10, jfSolveAcrostic);
/*     */         });
/* 167 */     this.menu = new JMenu("View");
/* 168 */     menuBar.add(this.menu);
/* 169 */     this.menuItem = new JMenuItem("Display Options");
/* 170 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(89, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 171 */     this.menu.add(this.menuItem);
/* 172 */     this.menuItem
/* 173 */       .addActionListener(ae -> {
/*     */           AcrosticBuild.printOptions(jfSolveAcrostic, "Display Options");
/*     */ 
/*     */           
/*     */           restoreFrame();
/*     */         });
/*     */     
/* 180 */     this.menu = new JMenu("Tasks");
/* 181 */     menuBar.add(this.menu);
/* 182 */     this.menuItem = new JMenuItem("Reveal One Letter");
/* 183 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(79, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 184 */     this.menu.add(this.menuItem);
/* 185 */     this.menuItem
/* 186 */       .addActionListener(ae -> {
/*     */           if (Methods.noReveal == 0) {
/*     */             acSol[Grid.xCur + Grid.yCur * Grid.xSz] = Character.toUpperCase(Op.ac[Op.AC.AcParagraph.ordinal()].charAt(Grid.xCur + Grid.yCur * Grid.xSz));
/*     */           } else {
/*     */             Methods.noReveal(jfSolveAcrostic);
/*     */           } 
/*     */           
/*     */           restoreFrame();
/*     */           
/*     */           (new Thread(this.solveThread)).start();
/*     */         });
/* 197 */     ActionListener errorTimer = ae -> {
/*     */         Def.dispErrors = Boolean.valueOf(false);
/*     */         restoreFrame();
/*     */         this.myTimer.stop();
/*     */       };
/* 202 */     this.myTimer = new Timer(1500, errorTimer);
/*     */     
/* 204 */     this.menuItem = new JMenuItem("Reveal Errors");
/* 205 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(69, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 206 */     this.menu.add(this.menuItem);
/* 207 */     this.menuItem
/* 208 */       .addActionListener(ae -> {
/*     */           if (Methods.noErrors == 0) {
/*     */             this.myTimer.start();
/*     */             
/*     */             Def.dispErrors = Boolean.valueOf(true);
/*     */           } else {
/*     */             Methods.noReveal(jfSolveAcrostic);
/*     */           } 
/*     */           
/*     */           restoreFrame();
/*     */         });
/* 219 */     this.menuItem = new JMenuItem("Reveal Solution");
/* 220 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(83, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 221 */     this.menu.add(this.menuItem);
/* 222 */     this.menuItem
/* 223 */       .addActionListener(ae -> {
/*     */           if (Methods.noReveal == 0) {
/*     */             for (int j = 0; j < acrosticLen; j++) {
/*     */               if (Character.isLetter(Op.ac[Op.AC.AcParagraph.ordinal()].charAt(j))) {
/*     */                 acSol[j] = Character.toUpperCase(Op.ac[Op.AC.AcParagraph.ordinal()].charAt(j));
/*     */               }
/*     */             } 
/*     */           } else {
/*     */             Methods.noReveal(jfSolveAcrostic);
/*     */           } 
/*     */           restoreFrame();
/*     */         });
/* 235 */     this.menuItem = new JMenuItem("Begin again");
/* 236 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(66, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 237 */     this.menu.add(this.menuItem);
/* 238 */     this.menuItem
/* 239 */       .addActionListener(ae -> {
/*     */           for (int j = 0; j < acrosticLen; j++) {
/*     */             if (Character.isLetter(Op.ac[Op.AC.AcParagraph.ordinal()].charAt(j))) {
/*     */               acSol[j] = 0;
/*     */             }
/*     */           } 
/*     */           
/*     */           restoreFrame();
/*     */         });
/* 248 */     this.menu = new JMenu("Help");
/* 249 */     menuBar.add(this.menu);
/* 250 */     this.menuItem = new JMenuItem("Acrostic Help");
/* 251 */     this.menu.add(this.menuItem);
/* 252 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(72, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 253 */     this.menuItem
/* 254 */       .addActionListener(ae -> Methods.cweHelp(jfSolveAcrostic, null, "Solving Acrostic Puzzles", this.acrosticSolve));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 260 */     this.solveThread = (() -> {
/*     */         for (int j = 0; j < acrosticLen; j++) {
/*     */           if (acSol[j] != Character.toUpperCase(Op.ac[Op.AC.AcParagraph.ordinal()].charAt(j)) && Op.ac[Op.AC.AcParagraph.ordinal()].charAt(j) != ' ')
/*     */             return; 
/*     */         } 
/*     */         Methods.congratulations(jfSolveAcrostic);
/*     */       });
/* 267 */     ppWords = new AcrosticSolveWordsPP(390, 37);
/* 268 */     jfSolveAcrostic.add(ppWords);
/* 269 */     ppParagraph = new AcrosticSolveParagraphPP(10, 120);
/* 270 */     jfSolveAcrostic.add(ppParagraph);
/*     */     
/* 272 */     ppWords.addMouseListener(new MouseAdapter() { public void mousePressed(MouseEvent e) { AcrosticSolve.this.updateWords(e); } });
/* 273 */     ppParagraph.addMouseListener(new MouseAdapter() { public void mousePressed(MouseEvent e) { AcrosticSolve.this.updateParagraph(e); } });
/* 274 */     jfSolveAcrostic.addKeyListener(new KeyAdapter() { public void keyPressed(KeyEvent e) { AcrosticSolve.this.handleKeyPressed(e); } }
/*     */       );
/* 276 */     jlClue = new JLabel("Clue");
/* 277 */     jlClue.setSize(100, 16);
/* 278 */     jlClue.setLocation(10, 75);
/* 279 */     jlClue.setHorizontalAlignment(2);
/* 280 */     jfSolveAcrostic.add(jlClue);
/* 281 */     jlClue.setFont(new Font(null, 1, 18));
/* 282 */     jpClue = new TheCluePanel(60, 47, 382, 56);
/* 283 */     jfSolveAcrostic.add(jpClue);
/*     */     
/* 285 */     jfSolveAcrostic.setVisible(true);
/* 286 */     loadAcrostic(Op.ac[Op.AC.AcPuz.ordinal()]);
/* 287 */     Methods.havePuzzle = true;
/* 288 */     restoreFrame();
/*     */   }
/*     */   
/*     */   static void restoreFrame() {
/* 292 */     jfSolveAcrostic.setVisible(true);
/* 293 */     Insets insets = jfSolveAcrostic.getInsets();
/* 294 */     width = jfSolveAcrostic.getWidth() - insets.left + insets.right;
/* 295 */     height = jfSolveAcrostic.getHeight() - insets.top + insets.bottom + 37 + menuBar.getHeight();
/*     */     
/* 297 */     panelParagraphW = width / 2 - 10;
/* 298 */     panelParagraphH = height - 92;
/* 299 */     panelWordsW = width / 2 - 8;
/* 300 */     panelWordsH = height - 20;
/*     */     
/* 302 */     panelParagraphX = 10;
/* 303 */     panelParagraphY = 119;
/* 304 */     panelWordsX = width / 2 - 2;
/* 305 */     panelWordsY = 47;
/*     */ 
/*     */     
/* 308 */     jpClue.setSize(panelWordsX - 58, 74);
/* 309 */     ppParagraph.setLocation(panelParagraphX, panelParagraphY);
/* 310 */     ppParagraph.setSize(panelParagraphW, panelParagraphH);
/* 311 */     ppWords.setLocation(panelWordsX, panelWordsY);
/* 312 */     ppWords.setSize(panelWordsW, panelWordsH);
/*     */     
/* 314 */     adjustParagraph(0, 0, panelParagraphW, panelParagraphH, 20);
/* 315 */     setSizesAndOffsetsW(0, 0, panelWordsW, panelWordsH, 20);
/*     */     
/* 317 */     Def.dispCursor = Boolean.valueOf(true);
/* 318 */     AcrosticBuild.acrosticLinkage();
/* 319 */     setParagraphCursor();
/* 320 */     jfSolveAcrostic.requestFocusInWindow();
/* 321 */     jpClue.repaint();
/* 322 */     ppParagraph.repaint();
/* 323 */     ppWords.repaint();
/*     */     
/* 325 */     Methods.infoPanel(jl1, jl2, "Solve Acrostic", "Dictionary : " + Op.ac[Op.AC.AcDic.ordinal()] + "  -|-  Puzzle : " + Op.ac[Op.AC.AcPuz
/* 326 */           .ordinal()], width);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static void adjustParagraph(int x, int y, int width, int height, int inset) {
/* 332 */     int len = Op.ac[Op.AC.AcParagraph.ordinal()].length(); Grid.xCell = width / 3;
/* 333 */     while ((Grid.xSz = (width - 20) / Grid.xCell) * (Grid.ySz = (height - 20) / Grid.xCell) < len)
/*     */       Grid.xCell--; 
/* 335 */     int i = (width - 20) / Grid.xSz;
/* 336 */     int j = (height - 20) / Grid.ySz;
/* 337 */     Grid.xCell = Grid.yCell = (i < j) ? i : j;
/* 338 */     Grid.xOrg = x + (width - Grid.xCell * Grid.xSz) / 2;
/* 339 */     Grid.yOrg = y + (height - Grid.yCell * Grid.ySz) / 2;
/*     */   }
/*     */ 
/*     */   
/*     */   static void setSizesAndOffsetsW(int x, int y, int width, int height, int inset) {
/* 344 */     if (NodeList.nodeListLength > 0) {
/* 345 */       for (int k = 0; k < Op.ac[Op.AC.AcWords.ordinal()].length(); k++) {
/* 346 */         if ((NodeList.nodeList[k]).length > Grid.xSzW)
/* 347 */           Grid.xSzW = (NodeList.nodeList[k]).length; 
/*     */       } 
/*     */     } else {
/* 350 */       Grid.xSzW = 12;
/* 351 */     }  int i = (width - inset) / (Grid.xSzW + 1);
/* 352 */     int j = (height - inset) / Grid.ySzW;
/* 353 */     Grid.xCellW = Grid.yCellW = (i < j) ? i : j;
/* 354 */     Grid.xOrgW = x + (width - Grid.xCellW * (Grid.xSzW - 1)) / 2;
/* 355 */     Grid.yOrgW = y + (height - Grid.yCellW * Grid.ySzW) / 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void saveAcrostic(String acrosticName) {
/*     */     try {
/* 363 */       DataOutputStream dataOut = new DataOutputStream(new FileOutputStream(Op.ac[Op.AC.AcDic.ordinal()] + ".dic/" + acrosticName));
/* 364 */       dataOut.writeInt(Grid.xSz);
/* 365 */       dataOut.writeInt(Grid.ySz);
/* 366 */       dataOut.writeByte(Methods.noReveal);
/* 367 */       dataOut.writeByte(Methods.noErrors); int i;
/* 368 */       for (i = 0; i < 54; i++) {
/* 369 */         dataOut.writeByte(0);
/*     */       }
/* 371 */       dataOut.writeUTF(Op.ac[Op.AC.AcParagraph.ordinal()]);
/* 372 */       dataOut.writeUTF(Op.ac[Op.AC.AcWords.ordinal()]);
/*     */       
/* 374 */       for (i = 0; i < acrosticLen; i++) {
/* 375 */         dataOut.writeInt(acSol[i]);
/* 376 */         dataOut.writeInt(acColor[i]);
/*     */       } 
/*     */       
/* 379 */       dataOut.writeUTF(Methods.puzzleTitle);
/* 380 */       dataOut.writeUTF(Methods.author);
/* 381 */       dataOut.writeUTF(Methods.copyright);
/* 382 */       dataOut.writeUTF(Methods.puzzleNumber);
/* 383 */       dataOut.writeUTF(Methods.puzzleNotes);
/*     */       
/* 385 */       for (i = 0; i < Op.ac[Op.AC.AcWords.ordinal()].length(); i++) {
/* 386 */         dataOut.writeUTF((NodeList.nodeList[i]).word);
/* 387 */         dataOut.writeUTF(((NodeList.nodeList[i]).word.length() == 1) ? "No Clue" : (NodeList.nodeList[i]).clue);
/*     */       } 
/* 389 */       dataOut.close();
/*     */     }
/* 391 */     catch (IOException exc) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void loadAcrostic(String acrosticName) {
/*     */     try {
/* 399 */       File fl = new File(Op.ac[Op.AC.AcDic.ordinal()] + ".dic/" + acrosticName);
/* 400 */       if (!fl.exists()) {
/* 401 */         fl = new File(Op.ac[Op.AC.AcDic.ordinal()] + ".dic/");
/* 402 */         String[] s = fl.list(); int j;
/* 403 */         for (j = 0; j < s.length && (
/* 404 */           s[j].lastIndexOf(".acrostic") == -1 || s[j].charAt(0) == '.'); j++);
/*     */         
/* 406 */         acrosticName = s[j];
/* 407 */         Op.ac[Op.AC.AcPuz.ordinal()] = acrosticName;
/*     */       } 
/*     */       
/* 410 */       DataInputStream dataIn = new DataInputStream(new FileInputStream(Op.ac[Op.AC.AcDic.ordinal()] + ".dic/" + acrosticName));
/* 411 */       Grid.xSz = dataIn.readInt();
/* 412 */       Grid.ySz = dataIn.readInt();
/* 413 */       Methods.noReveal = dataIn.readByte();
/* 414 */       Methods.noErrors = dataIn.readByte(); int i;
/* 415 */       for (i = 0; i < 54; i++) {
/* 416 */         dataIn.readByte();
/*     */       }
/* 418 */       Op.ac[Op.AC.AcParagraph.ordinal()] = dataIn.readUTF();
/* 419 */       Op.ac[Op.AC.AcWords.ordinal()] = dataIn.readUTF();
/*     */       
/* 421 */       for (i = 0; i < (acrosticLen = Op.ac[Op.AC.AcParagraph.ordinal()].length()); i++) {
/* 422 */         acSol[i] = dataIn.readInt();
/* 423 */         acColor[i] = dataIn.readInt();
/*     */       } 
/*     */       
/* 426 */       Methods.puzzleTitle = dataIn.readUTF();
/* 427 */       Methods.author = dataIn.readUTF();
/* 428 */       Methods.copyright = dataIn.readUTF();
/* 429 */       Methods.puzzleNumber = dataIn.readUTF();
/* 430 */       Methods.puzzleNotes = dataIn.readUTF();
/*     */       
/* 432 */       prepareNodeList();
/* 433 */       for (i = 0; i < Op.ac[Op.AC.AcWords.ordinal()].length(); i++) {
/* 434 */         (NodeList.nodeList[i]).word = dataIn.readUTF();
/* 435 */         if (((NodeList.nodeList[i]).length = (NodeList.nodeList[i]).word.length()) > 1)
/* 436 */           (NodeList.nodeList[i]).preset = true; 
/* 437 */         (NodeList.nodeList[i]).clue = dataIn.readUTF();
/*     */       } 
/*     */ 
/*     */       
/* 441 */       for (keyCol = 0;; keyCol++) {
/* 442 */         for (i = 0; i < Op.ac[Op.AC.AcWords.ordinal()].length() && 
/* 443 */           (NodeList.nodeList[i]).word.charAt(keyCol) == Op.ac[Op.AC.AcWords.ordinal()].charAt(i); i++);
/*     */         
/* 445 */         if (i == Op.ac[Op.AC.AcWords.ordinal()].length()) {
/*     */           break;
/*     */         }
/*     */       } 
/* 449 */       dataIn.close();
/*     */     }
/* 451 */     catch (IOException exc) {
/*     */       return;
/* 453 */     }  cursorInWords = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void drawAcrosticParagraph(Graphics2D g2) {
/* 460 */     boolean isError = false;
/*     */     
/* 462 */     Stroke normalStroke = new BasicStroke(Grid.xCell / 20.0F, 2, 0);
/* 463 */     Stroke wideStroke = new BasicStroke((Grid.xCell < 20) ? 2.0F : (2 * Grid.xCell / 20), 2, 0);
/* 464 */     RenderingHints rh = g2.getRenderingHints();
/* 465 */     rh.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
/* 466 */     g2.setRenderingHints(rh);
/* 467 */     g2.setStroke(normalStroke);
/*     */     
/*     */     int index;
/* 470 */     for (index = 0; index < acrosticLen; index++) {
/* 471 */       int theColor; if (Def.dispErrors.booleanValue() && acSol[index] != 0 && acSol[index] != 32 && acSol[index] != Character.toUpperCase(Op.ac[Op.AC.AcParagraph.ordinal()].charAt(index))) {
/* 472 */         theColor = Op.getColorInt(Op.AC.AcError.ordinal(), Op.ac);
/* 473 */         isError = true;
/*     */       }
/* 475 */       else if (Character.isWhitespace(Op.ac[Op.AC.AcParagraph.ordinal()].charAt(index))) {
/* 476 */         theColor = Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.AC.AcPattern.ordinal(), Op.ac) : 0;
/*     */       } else {
/* 478 */         theColor = Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.AC.AcCell.ordinal(), Op.ac) : 16777215;
/* 479 */       }  int i = index % Grid.xSz, j = index / Grid.xSz;
/* 480 */       g2.setColor(new Color(theColor));
/* 481 */       g2.fillRect(Grid.xOrg + i * Grid.xCell, Grid.yOrg + j * Grid.yCell, Grid.xCell, Grid.yCell);
/*     */     } 
/* 483 */     if (!isError) Def.dispErrors = Boolean.valueOf(false);
/*     */ 
/*     */     
/* 486 */     g2.setColor(new Color(Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.AC.AcGrid.ordinal(), Op.ac) : 0));
/* 487 */     for (index = 0; index < acrosticLen; index++) {
/* 488 */       int i = index % Grid.xSz, j = index / Grid.xSz;
/* 489 */       g2.drawRect(Grid.xOrg + i * Grid.xCell, Grid.yOrg + j * Grid.yCell, Grid.xCell, Grid.yCell);
/*     */     } 
/*     */ 
/*     */     
/* 493 */     g2.setColor(new Color(Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.AC.AcLetter.ordinal(), Op.ac) : 0));
/* 494 */     g2.setFont(new Font(Op.ac[Op.AC.AcFont.ordinal()], 0, 8 * Grid.yCell / 10));
/* 495 */     FontMetrics fm = g2.getFontMetrics();
/* 496 */     for (index = 0; index < acrosticLen; index++) {
/* 497 */       char ch = (char)(Def.dispSolArray.booleanValue() ? acSol[index] : Op.ac[Op.AC.AcParagraph.ordinal()].charAt(index));
/* 498 */       if (Character.isLowerCase(Op.ac[Op.AC.AcParagraph.ordinal()].charAt(index))) ch = Character.toLowerCase(ch); 
/* 499 */       if (ch != '\000') {
/* 500 */         int w = fm.stringWidth("" + ch);
/* 501 */         int i = index % Grid.xSz, j = index / Grid.xSz;
/* 502 */         g2.drawString("" + ch, Grid.xOrg + i * Grid.xCell + (Grid.xCell - w) / 2, Grid.yOrg + j * Grid.yCell + 8 * Grid.yCell / 10);
/*     */       } 
/*     */     } 
/*     */     
/* 506 */     if (Def.dispCursor.booleanValue()) {
/*     */       
/* 508 */       g2.setColor(new Color(Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.AC.AcCode.ordinal(), Op.ac) : 0));
/* 509 */       g2.setFont(new Font(Op.ac[Op.AC.AcCodeFont.ordinal()], 0, 3 * Grid.yCell / 10));
/* 510 */       fm = g2.getFontMetrics(); int id;
/* 511 */       for (id = index = 0; index < acrosticLen; index++) {
/* 512 */         if (Character.isLetter(Op.ac[Op.AC.AcParagraph.ordinal()].charAt(index))) {
/* 513 */           int i = index % Grid.xSz, j = index / Grid.xSz;
/* 514 */           g2.drawString("" + ++id, Grid.xOrg + i * Grid.xCell + Grid.xCell / 10, Grid.yOrg + j * Grid.yCell + 3 * Grid.yCell / 10);
/*     */         } 
/*     */       } 
/*     */       
/* 518 */       for (id = 1, index = 0; index < acrosticLen; index++) {
/* 519 */         if (Character.isLetter(Op.ac[Op.AC.AcParagraph.ordinal()].charAt(index))) {
/* 520 */           for (int i = 0; i < NodeList.nodeListLength; i++) {
/* 521 */             for (int j = 0; j < (NodeList.nodeList[i]).length; j++) {
/* 522 */               if (((NodeList.nodeList[i]).cellLoc[j]).x == id)
/* 523 */               { char ch = (char)(((j < 26) ? 65 : 97) + i);
/* 524 */                 int w = fm.stringWidth("" + ch);
/* 525 */                 int x = index % Grid.xSz, y = index / Grid.xSz;
/* 526 */                 g2.drawString("" + ch, Grid.xOrg + (x + 1) * Grid.xCell - w - Grid.xCell / 8, Grid.yOrg + y * Grid.yCell + 3 * Grid.yCell / 10); } 
/*     */             } 
/* 528 */           }  id++;
/*     */         } 
/*     */       } 
/* 531 */       if (Def.dispCursor.booleanValue()) {
/* 532 */         g2.setColor(cursorInWords ? Def.COLOR_BLUE : Def.COLOR_RED);
/* 533 */         g2.setStroke(wideStroke);
/* 534 */         g2.drawRect(Grid.xOrg + Grid.xCur * Grid.xCell, Grid.yOrg + Grid.yCur * Grid.yCell, Grid.xCell, Grid.yCell);
/*     */       } 
/*     */     } 
/* 537 */     g2.setStroke(new BasicStroke(1.0F));
/*     */   }
/*     */ 
/*     */   
/*     */   static void drawAcrosticWords(Graphics2D g2) {
/* 542 */     char ch = ' ';
/*     */     
/* 544 */     boolean anError = false, isError = false;
/*     */     
/* 546 */     Stroke normalStroke = new BasicStroke(Grid.xCellW / 20.0F, 2, 0);
/* 547 */     Stroke wideStroke = new BasicStroke((Grid.xCellW < 20) ? 2.0F : (2 * Grid.xCellW / 20), 2, 0);
/* 548 */     RenderingHints rh = g2.getRenderingHints();
/* 549 */     rh.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
/* 550 */     g2.setRenderingHints(rh);
/* 551 */     g2.setStroke(normalStroke);
/*     */     
/*     */     int j;
/* 554 */     for (j = 0; j < Grid.ySzW; j++) {
/* 555 */       for (int k = 0; k < (NodeList.nodeList[j]).length; k++) {
/* 556 */         int theColor, id = ((NodeList.nodeList[j]).cellLoc[k]).x;
/* 557 */         for (int index = 0, idP = index; index < acrosticLen; index++) {
/* 558 */           if (Character.isLetter(Op.ac[Op.AC.AcParagraph.ordinal()].charAt(index)) && 
/* 559 */             ++idP == id)
/* 560 */             anError = (acSol[index] != 0 && acSol[index] != 32 && acSol[index] != Character.toUpperCase(Op.ac[Op.AC.AcParagraph.ordinal()].charAt(index))); 
/* 561 */         }  if (anError && Def.dispErrors.booleanValue()) {
/* 562 */           theColor = Op.getColorInt(Op.AC.AcError.ordinal(), Op.ac);
/* 563 */           isError = true;
/*     */         } else {
/*     */           
/* 566 */           theColor = (k == keyCol) ? Op.getColorInt(Op.AC.AcFirstLetter.ordinal(), Op.ac) : ((j == Grid.yCurW) ? Op.getColorInt(Op.AC.AcCurrent.ordinal(), Op.ac) : Op.getColorInt(Op.AC.AcCell.ordinal(), Op.ac));
/* 567 */         }  g2.setColor(new Color(theColor));
/* 568 */         g2.fillRect(Grid.xOrgW + k * Grid.xCellW, Grid.yOrgW + j * Grid.yCellW, Grid.xCellW, Grid.yCellW);
/*     */       } 
/* 570 */     }  if (!isError) Def.dispErrors = Boolean.valueOf(false);
/*     */ 
/*     */     
/* 573 */     g2.setColor(new Color(Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.AC.AcGrid.ordinal(), Op.ac) : 0));
/* 574 */     for (j = 0; j < Grid.ySzW; j++) {
/* 575 */       for (int k = 0; k < (NodeList.nodeList[j]).length; k++) {
/* 576 */         g2.drawRect(Grid.xOrgW + k * Grid.xCellW, Grid.yOrgW + j * Grid.yCellW, Grid.xCellW, Grid.yCellW);
/*     */       }
/*     */     } 
/* 579 */     g2.setColor(new Color(Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.AC.AcLetter.ordinal(), Op.ac) : 0));
/* 580 */     g2.setFont(new Font(Op.ac[Op.AC.AcFont.ordinal()], 0, 8 * Grid.yCellW / 10));
/* 581 */     FontMetrics fm = g2.getFontMetrics();
/* 582 */     for (j = 0; j < Grid.ySzW; j++) {
/* 583 */       for (int k = 0; k < (NodeList.nodeList[j]).word.length(); k++) {
/* 584 */         int id = ((NodeList.nodeList[j]).cellLoc[k]).x;
/* 585 */         for (int index = 0, idP = index; index < acrosticLen; index++) {
/* 586 */           if (Character.isLetter(Op.ac[Op.AC.AcParagraph.ordinal()].charAt(index)) && 
/* 587 */             ++idP == id)
/* 588 */             ch = Character.toUpperCase((char)acSol[index]); 
/* 589 */         }  if (ch != '\000') {
/* 590 */           int w = fm.stringWidth("" + ch);
/* 591 */           g2.drawString("" + ch, Grid.xOrgW + k * Grid.xCellW + (Grid.xCellW - w) / 2, Grid.yOrgW + j * Grid.yCellW + 9 * Grid.yCellW / 10);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 596 */     g2.setColor(new Color(Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.AC.AcCode.ordinal(), Op.ac) : 0));
/* 597 */     g2.setFont(new Font(Op.ac[Op.AC.AcCodeFont.ordinal()], 0, 3 * Grid.yCellW / 10));
/* 598 */     for (int i = 0; i < NodeList.nodeListLength; i++) {
/* 599 */       for (j = 0; j < (NodeList.nodeList[i]).length; j++) {
/* 600 */         int id = ((NodeList.nodeList[i]).cellLoc[j]).x;
/* 601 */         g2.drawString("" + id, Grid.xOrgW + j * Grid.xCellW + Grid.xCellW / 10, Grid.yOrgW + i * Grid.yCellW + 3 * Grid.yCellW / 10);
/*     */       } 
/*     */     } 
/*     */     
/* 605 */     g2.setFont(new Font(Op.ac[Op.AC.AcCodeFont.ordinal()], 0, 6 * Grid.yCellW / 10));
/* 606 */     fm = g2.getFontMetrics();
/* 607 */     for (j = 0; j < Grid.ySzW; j++) {
/* 608 */       ch = (char)(((j < 26) ? 65 : 97) + j);
/* 609 */       int w = fm.stringWidth("" + ch);
/* 610 */       g2.drawString("" + ch, Grid.xOrgW - Grid.xCellW + (Grid.xCellW - w) / 2, Grid.yOrgW + j * Grid.yCellW + 9 * Grid.yCellW / 10);
/*     */     } 
/*     */     
/* 613 */     g2.setColor(cursorInWords ? Def.COLOR_RED : Def.COLOR_BLUE);
/* 614 */     g2.setStroke(wideStroke);
/* 615 */     g2.drawRect(Grid.xOrgW + Grid.xCurW * Grid.xCellW, Grid.yOrgW + Grid.yCurW * Grid.yCellW, Grid.xCellW, Grid.yCellW);
/*     */     
/* 617 */     g2.setStroke(new BasicStroke(1.0F));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static void prepareNodeList() {
/* 623 */     if (Op.ac[Op.AC.AcWords.ordinal()].length() > 0) {
/* 624 */       Grid.xSzW = 12; Grid.ySzW = Op.ac[Op.AC.AcWords.ordinal()].length();
/* 625 */       NodeList.nodeListLength = 0;
/* 626 */       for (int i = 0; i < Op.ac[Op.AC.AcWords.ordinal()].length(); i++) {
/* 627 */         Grid.copy[0][i] = Op.ac[Op.AC.AcWords.ordinal()].charAt(i);
/* 628 */         Grid.color[0][i] = Op.getColorInt(Op.AC.AcCurrent.ordinal(), Op.ac);
/* 629 */         NodeList.nodeList[NodeList.nodeListLength] = new Node();
/* 630 */         (NodeList.nodeList[NodeList.nodeListLength]).cellLoc = new java.awt.Point[50];
/* 631 */         (NodeList.nodeList[NodeList.nodeListLength]).direction = 0;
/* 632 */         (NodeList.nodeList[NodeList.nodeListLength]).id = i + 1;
/* 633 */         (NodeList.nodeList[NodeList.nodeListLength]).x = 0;
/* 634 */         (NodeList.nodeList[NodeList.nodeListLength]).y = i;
/* 635 */         (NodeList.nodeList[NodeList.nodeListLength]).length = 12;
/* 636 */         (NodeList.nodeList[NodeList.nodeListLength]).word = "" + Op.ac[Op.AC.AcWords.ordinal()].charAt(i);
/* 637 */         (NodeList.nodeList[NodeList.nodeListLength]).source = 0;
/* 638 */         (NodeList.nodeList[NodeList.nodeListLength]).preset = false;
/* 639 */         NodeList.nodeListLength++;
/*     */       } 
/*     */     } 
/* 642 */     Grid.xCurW = Grid.yCurW = 0;
/*     */     
/* 644 */     setSizesAndOffsetsW(0, 0, panelWordsW, panelWordsH, 20);
/*     */   }
/*     */ 
/*     */   
/*     */   void restoreIfDone() {
/*     */     int i;
/* 650 */     for (i = 0; i < acrosticLen; i++) {
/* 651 */       if (Character.isLetter(Op.ac[Op.AC.AcParagraph.ordinal()].charAt(i)) && 
/* 652 */         acSol[i] != Character.toUpperCase(Op.ac[Op.AC.AcParagraph.ordinal()].charAt(i)))
/*     */         return; 
/* 654 */     }  for (i = 0; i < acrosticLen; i++) {
/* 655 */       if (Character.isLetter(Op.ac[Op.AC.AcParagraph.ordinal()].charAt(i))) {
/* 656 */         acSol[i] = 0;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   static void setWordCursor() {
/* 663 */     for (int j = 0, idP = j; j < Grid.ySz; j++) {
/* 664 */       for (int i = 0; i < Grid.xSz; i++) {
/* 665 */         if (Character.isLetter(Op.ac[Op.AC.AcParagraph.ordinal()].charAt(i + j * Grid.xSz))) {
/* 666 */           idP++;
/* 667 */           if (i == Grid.xCur && j == Grid.yCur)
/*     */           {
/* 669 */             for (Grid.yCurW = 0; Grid.yCurW < NodeList.nodeListLength; Grid.yCurW++) {
/* 670 */               for (Grid.xCurW = 0; Grid.xCurW < (NodeList.nodeList[Grid.yCurW]).length; Grid.xCurW++) {
/* 671 */                 if (((NodeList.nodeList[Grid.yCurW]).cellLoc[Grid.xCurW]).x == idP)
/*     */                   return; 
/*     */               } 
/*     */             }  } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   } static void setParagraphCursor() {
/* 679 */     int idA = ((NodeList.nodeList[Grid.yCurW]).cellLoc[Grid.xCurW]).x;
/* 680 */     for (int idB = Grid.yCur = 0; Grid.yCur < Grid.ySz; Grid.yCur++) {
/* 681 */       for (Grid.xCur = 0; Grid.xCur < Grid.xSz; Grid.xCur++) {
/* 682 */         int x; if ((x = Grid.xCur + Grid.yCur * Grid.xSz) < acrosticLen && Character.isLetter(Op.ac[Op.AC.AcParagraph.ordinal()].charAt(x)) && 
/* 683 */           ++idB == idA)
/*     */           return; 
/*     */       } 
/*     */     } 
/*     */   } void updateWords(MouseEvent e) {
/* 688 */     int x = e.getX(), y = e.getY();
/*     */     
/* 690 */     if (x < Grid.xOrgW || y < Grid.yOrgW)
/* 691 */       return;  int i = (x - Grid.xOrgW) / Grid.xCellW;
/* 692 */     int j = (y - Grid.yOrgW) / Grid.yCellW;
/* 693 */     if (j >= Grid.ySzW || i >= (NodeList.nodeList[j]).length)
/* 694 */       return;  Grid.xCurW = i; Grid.yCurW = j;
/* 695 */     setParagraphCursor();
/* 696 */     cursorInWords = true;
/* 697 */     restoreFrame();
/*     */   }
/*     */   
/*     */   void updateParagraph(MouseEvent e) {
/* 701 */     int x = e.getX(), y = e.getY();
/*     */     
/* 703 */     if (x < Grid.xOrg || y < Grid.yOrg)
/* 704 */       return;  int i = (x - Grid.xOrg) / Grid.xCell;
/* 705 */     int j = (y - Grid.yOrg) / Grid.yCell;
/* 706 */     if (i >= Grid.xSz || j >= Grid.ySz || !Character.isLetter(Op.ac[Op.AC.AcParagraph.ordinal()].charAt(i + j * Grid.xSz)))
/* 707 */       return;  Grid.xCur = i; Grid.yCur = j;
/* 708 */     setWordCursor();
/* 709 */     cursorInWords = false;
/* 710 */     restoreFrame();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void handleKeyPressed(KeyEvent e) {
/* 716 */     if (e.isAltDown())
/* 717 */       return;  if (cursorInWords) {
/* 718 */       switch (e.getKeyCode()) { case 38:
/* 719 */           if (Grid.yCurW > 0) Grid.yCurW--;  break;
/* 720 */         case 40: if (Grid.yCurW < Grid.ySzW - 1) Grid.yCurW++;  break;
/* 721 */         case 33: Grid.yCurW = 0; break;
/* 722 */         case 34: Grid.yCurW = Grid.ySzW - 1; break;
/* 723 */         case 37: if (Grid.xCurW > 0) Grid.xCurW--;  break;
/* 724 */         case 39: if (Grid.xCurW < (NodeList.nodeList[Grid.yCurW]).length - 1) Grid.xCurW++;  break;
/* 725 */         case 36: Grid.xCurW = 0; break;
/* 726 */         case 35: Grid.xCurW = (NodeList.nodeList[Grid.yCurW]).length - 1; break; }
/*     */       
/* 728 */       if (Grid.xCurW >= (NodeList.nodeList[Grid.yCurW]).length)
/* 729 */         Grid.xCurW = (NodeList.nodeList[Grid.yCurW]).length - 1; 
/* 730 */       setParagraphCursor();
/*     */     } else {
/*     */       int i;
/* 733 */       switch (e.getKeyCode()) { case 38:
/* 734 */           for (i = Grid.yCur - 1; i >= 0; i--)
/* 735 */           { int x; if ((x = Grid.xCur + i * Grid.xSz) < acrosticLen && Character.isLetter(Op.ac[Op.AC.AcParagraph.ordinal()].charAt(x))) { Grid.yCur = i; break; }  }  break;
/* 736 */         case 40: for (i = Grid.yCur + 1; i < Grid.ySz; i++)
/* 737 */           { int x; if ((x = Grid.xCur + i * Grid.xSz) < acrosticLen && Character.isLetter(Op.ac[Op.AC.AcParagraph.ordinal()].charAt(x))) { Grid.yCur = i; break; }  }  break;
/* 738 */         case 33: for (i = 0;; i++)
/* 739 */           { int x; if ((x = Grid.xCur + i * Grid.xSz) < acrosticLen && Character.isLetter(Op.ac[Op.AC.AcParagraph.ordinal()].charAt(x))) { Grid.yCur = i; break; }  }  break;
/* 740 */         case 34: for (i = Grid.ySz - 1;; i--)
/* 741 */           { int x; if ((x = Grid.xCur + i * Grid.xSz) < acrosticLen && Character.isLetter(Op.ac[Op.AC.AcParagraph.ordinal()].charAt(x))) { Grid.yCur = i; break; }  }  break;
/* 742 */         case 37: for (i = Grid.xCur - 1; i >= 0; i--)
/* 743 */           { int x; if ((x = i + Grid.yCur * Grid.xSz) < acrosticLen && Character.isLetter(Op.ac[Op.AC.AcParagraph.ordinal()].charAt(x))) { Grid.xCur = i; break; }  }  break;
/* 744 */         case 39: for (i = Grid.xCur + 1; i < Grid.xSz; i++)
/* 745 */           { int x; if ((x = i + Grid.yCur * Grid.xSz) < acrosticLen && Character.isLetter(Op.ac[Op.AC.AcParagraph.ordinal()].charAt(x))) { Grid.xCur = i; break; }  }  break;
/* 746 */         case 36: for (i = 0;; i++)
/* 747 */           { int x; if ((x = i + Grid.yCur * Grid.xSz) < acrosticLen && Character.isLetter(Op.ac[Op.AC.AcParagraph.ordinal()].charAt(x))) { Grid.xCur = i; break; }  }  break;
/* 748 */         case 35: for (i = Grid.xSz - 1;; i--) {
/* 749 */             int x; if ((x = i + Grid.yCur * Grid.xSz) < acrosticLen && Character.isLetter(Op.ac[Op.AC.AcParagraph.ordinal()].charAt(x))) { Grid.xCur = i; break; } 
/*     */           }  break; }
/* 751 */        setWordCursor();
/*     */     } 
/* 753 */     char ch = e.getKeyChar();
/* 754 */     if (Character.isLetter(ch) || Character.isWhitespace(ch)) {
/* 755 */       if (acSol[Grid.xCur + Grid.yCur * Grid.xSz] != Character.toUpperCase(ch)) {
/* 756 */         acSol[Grid.xCur + Grid.yCur * Grid.xSz] = Character.toUpperCase(ch);
/* 757 */         (new Thread(this.solveThread)).start();
/*     */       } 
/* 759 */       if (cursorInWords) {
/* 760 */         if (Grid.xCurW < (NodeList.nodeList[Grid.yCurW]).length - 1)
/* 761 */           Grid.xCurW++; 
/* 762 */         setParagraphCursor();
/*     */       } else {
/*     */         
/* 765 */         int i = Grid.xCur + 1, j = Grid.yCur;
/* 766 */         for (; i + j * Grid.xSz != acrosticLen; i++) {
/* 767 */           if (i == Grid.xSz) { i = -1; j++; }
/* 768 */           else { if (j == Grid.ySz)
/* 769 */               break;  if (Character.isLetter(Op.ac[Op.AC.AcParagraph.ordinal()].charAt(i + j * Grid.xSz))) {
/* 770 */               Grid.xCur = i;
/* 771 */               Grid.yCur = j; break;
/*     */             }  }
/*     */         
/*     */         } 
/* 775 */         setWordCursor();
/*     */       } 
/*     */     } 
/* 778 */     restoreFrame();
/*     */   }
/*     */ }


/* Location:              C:\Users\mustapha\Desktop\Crossword-Express.jar!\crosswordexpress\AcrosticSolve.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */