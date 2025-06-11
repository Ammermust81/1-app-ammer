/*     */ package crosswordexpress;
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Point;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.Stroke;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ComponentEvent;
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.awt.event.MouseAdapter;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.event.WindowAdapter;
/*     */ import java.awt.event.WindowEvent;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JMenu;
/*     */ import javax.swing.JMenuBar;
/*     */ import javax.swing.JMenuItem;
/*     */ import javax.swing.KeyStroke;
/*     */ 
/*     */ public final class FillinSolve {
/*  29 */   String fillinSolve = "<div>This function provides an interesting variation to normal crossword puzzles in that you are given an empty grid, and a list of words. Your task is to recreate the puzzle by fitting the words back into the grid. It's like building a puzzle from scratch, except that you have only a very small list of words and an assurance that those words will exactly fill the puzzle.</div><p/><div>Any character which is typed at the keyboard will be placed into the focus cell, and the focus cell will automatically move forward to the next character of the focus word. The location of the focus cell and its corresponding word may be shifted by pointing and clicking with the mouse, or by means of the cursor control keys. Other keys which provide useful functions during the solution process are Space bar, Delete, Backspace and Return.<p/></div><span class='m'>Menu Functions</span><br/><br/><ul><li/><span class='s'>File Menu</span><br/><ul><li/><span>Select a Dictionary</span><br/>When loading a new puzzle, you begin by selecting the dictionary which was used to build the FILLIN puzzle which you want to solve.<p/><li/><span>Load a Puzzle</span><br/>Then you choose your puzzle from the pool of FILLIN puzzles currently available in the selected dictionary.<p/><li/><span>Quit Solving</span><br/>Returns you to the CROSSWORD Build screen.<p/></ul><li/><span class='s'>View Menu</span><br/><ul><li/><span>Display Options</span><br/>This leads you to a dialog box in which you can change the colors of various elements within the puzzle, and control the fonts which will be used for the puzzle's text components. You can also decide if printing of the puzzle will be done in black and white or in color.<p/></ul><li/><span class='s'>Tasks Menu</span><br/><ul><li/><span>Reveal One Letter</span><br/>If you need a little help to get started, this option will place the correct letter into the current focus cell.<p/><li/><span>Reveal Errors</span><br/>If you think you may have made errors, this option will show you where they are by highlighting them for a period of 1.5 seconds.<p/><li/><span>Reveal Solution</span><br/>The entire solution can be seen by selecting this option.<p/><li/><span>Begin Again</span><br/>You can restart the entire solution process at any time by selecting this option.<p/></ul><li/><span class='s'>Help Menu</span><br/><ul><li/><span>Fillin Help</span><br/>Displays the Help screen which you are now reading.<p/></ul></ul></body";
/*     */ 
/*     */   
/*     */   static JFrame jfSolveFillin;
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
/*     */   static int panelW;
/*     */   
/*     */   static int panelH;
/*     */   
/*     */   static JLabel jl1;
/*     */   
/*     */   static JLabel jl2;
/*     */   
/*     */   static JLabel jlWords;
/*     */   
/*     */   Timer myTimer;
/*     */   
/*     */   static int memMode;
/*     */   
/*     */   static DefaultListModel<String> lmWords;
/*     */   
/*     */   static JList<String> jlstWords;
/*     */   
/*     */   static JScrollPane jspWords;
/*     */   
/*     */   Runnable solveThread;
/*     */ 
/*     */   
/*     */   static void def() {
/*  74 */     Op.updateOption(Op.FL.FlW.ordinal(), "800", Op.fl);
/*  75 */     Op.updateOption(Op.FL.FlH.ordinal(), "700", Op.fl);
/*  76 */     Op.updateOption(Op.FL.FlCell.ordinal(), "FFFFEE", Op.fl);
/*  77 */     Op.updateOption(Op.FL.FlGrid.ordinal(), "003333", Op.fl);
/*  78 */     Op.updateOption(Op.FL.FlPattern.ordinal(), "003333", Op.fl);
/*  79 */     Op.updateOption(Op.FL.FlLetters.ordinal(), "006666", Op.fl);
/*  80 */     Op.updateOption(Op.FL.FlID.ordinal(), "880000", Op.fl);
/*  81 */     Op.updateOption(Op.FL.FlFocus.ordinal(), "FFFFBB", Op.fl);
/*  82 */     Op.updateOption(Op.FL.FlError.ordinal(), "FF0000", Op.fl);
/*  83 */     Op.updateOption(Op.FL.FlPuz.ordinal(), "sample.crossword", Op.fl);
/*  84 */     Op.updateOption(Op.FL.FlDic.ordinal(), "english", Op.fl);
/*  85 */     Op.updateOption(Op.FL.FlPuzzleFont.ordinal(), "SansSerif", Op.fl);
/*  86 */     Op.updateOption(Op.FL.FlIDFont.ordinal(), "SansSerif", Op.fl);
/*  87 */     Op.updateOption(Op.FL.FlPuzColor.ordinal(), "false", Op.fl);
/*  88 */     Op.updateOption(Op.FL.FlSolColor.ordinal(), "false", Op.fl);
/*     */   }
/*     */   
/*     */   FillinSolve(JFrame jf) {
/*  92 */     memMode = Def.puzzleMode;
/*  93 */     if (memMode == 4) {
/*  94 */       Op.fl[Op.FL.FlDic.ordinal()] = Op.cw[Op.CW.CwDic.ordinal()];
/*  95 */       Op.fl[Op.FL.FlPuz.ordinal()] = Op.cw[Op.CW.CwPuz.ordinal()];
/*     */     } 
/*  97 */     Def.puzzleMode = 60;
/*  98 */     Def.dispCursor = Boolean.valueOf(true);
/*  99 */     Def.dispSolArray = Boolean.valueOf(true);
/* 100 */     Def.dispGuideDigits = Boolean.valueOf(true);
/* 101 */     Grid.clearGrid();
/*     */     
/* 103 */     jfSolveFillin = new JFrame("Solve a Fillin Puzzle");
/* 104 */     jfSolveFillin.setSize(Op.getInt(Op.FL.FlW.ordinal(), Op.fl), Op.getInt(Op.FL.FlH.ordinal(), Op.fl));
/* 105 */     int frameX = (jf.getX() + jfSolveFillin.getWidth() > Methods.scrW) ? (Methods.scrW - jfSolveFillin.getWidth() - 10) : jf.getX();
/* 106 */     jfSolveFillin.setLocation(frameX, jf.getY());
/* 107 */     jfSolveFillin.setLayout((LayoutManager)null);
/* 108 */     jfSolveFillin.getContentPane().setBackground(Def.COLOR_FRAMEBG);
/* 109 */     jfSolveFillin.setDefaultCloseOperation(0);
/* 110 */     jfSolveFillin
/* 111 */       .addComponentListener(new ComponentAdapter() {
/*     */           public void componentResized(ComponentEvent ce) {
/* 113 */             int oldw = Op.getInt(Op.FL.FlW.ordinal(), Op.fl);
/* 114 */             int oldh = Op.getInt(Op.FL.FlH.ordinal(), Op.fl);
/* 115 */             Methods.frameResize(FillinSolve.jfSolveFillin, oldw, oldh, 810, 700);
/* 116 */             Op.setInt(Op.FL.FlW.ordinal(), FillinSolve.jfSolveFillin.getWidth(), Op.fl);
/* 117 */             Op.setInt(Op.FL.FlH.ordinal(), FillinSolve.jfSolveFillin.getHeight(), Op.fl);
/* 118 */             FillinSolve.restoreFrame();
/*     */           }
/*     */         });
/*     */     
/* 122 */     jfSolveFillin
/* 123 */       .addWindowListener(new WindowAdapter() {
/*     */           public void windowClosing(WindowEvent we) {
/* 125 */             if (Def.selecting)
/* 126 */               return;  FillinSolve.this.restoreIfDone();
/* 127 */             FillinSolve.focusColor(false);
/* 128 */             FillinSolve.saveFillin(Op.fl[Op.FL.FlPuz.ordinal()]);
/* 129 */             CrosswordExpress.transfer(4, FillinSolve.jfSolveFillin);
/*     */           }
/*     */         });
/*     */     
/* 133 */     Methods.closeHelp();
/*     */     
/* 135 */     jl1 = new JLabel(); jfSolveFillin.add(jl1);
/* 136 */     jl2 = new JLabel(); jfSolveFillin.add(jl2);
/*     */ 
/*     */     
/* 139 */     menuBar = new JMenuBar();
/* 140 */     menuBar.setBackground(Def.COLOR_MENUBAR);
/* 141 */     jfSolveFillin.setJMenuBar(menuBar);
/* 142 */     this.menu = new JMenu("File");
/* 143 */     menuBar.add(this.menu);
/* 144 */     this.menuItem = new JMenuItem("Select Dictionary");
/* 145 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(68, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 146 */     this.menu.add(this.menuItem);
/* 147 */     this.menuItem
/* 148 */       .addActionListener(ae -> {
/*     */           Methods.selectDictionary(jfSolveFillin, Op.fl[Op.FL.FlDic.ordinal()], 3);
/*     */           
/*     */           if (!Methods.fileAvailable(Methods.dictionaryName + ".dic", "crossword")) {
/*     */             JOptionPane.showMessageDialog(jfSolveFillin, "<html>No Crossword puzzles are available in this dictionary.<br>Use the <font color=880000>Build</font> option to create one.");
/*     */             
/*     */             return;
/*     */           } 
/*     */           
/*     */           restoreIfDone();
/*     */           
/*     */           saveFillin(Op.fl[Op.FL.FlPuz.ordinal()]);
/*     */           
/*     */           Op.fl[Op.FL.FlDic.ordinal()] = Methods.dictionaryName;
/*     */           loadSolvePuzzle();
/*     */           restoreFrame();
/*     */         });
/* 165 */     this.menuItem = new JMenuItem("Load a Puzzle");
/* 166 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(76, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 167 */     this.menu.add(this.menuItem);
/* 168 */     this.menuItem
/* 169 */       .addActionListener(ae -> {
/*     */           if (Def.building == 1) {
/*     */             return;
/*     */           }
/*     */           restoreIfDone();
/*     */           saveFillin(Op.fl[Op.FL.FlPuz.ordinal()]);
/*     */           focusColor(false);
/*     */           new Select(jfSolveFillin, Op.fl[Op.FL.FlDic.ordinal()] + ".dic", "crossword", Op.fl, Op.FL.FlPuz.ordinal(), false);
/*     */         });
/* 178 */     this.menuItem = new JMenuItem("Quit Solving");
/* 179 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(81, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 180 */     this.menu.add(this.menuItem);
/* 181 */     this.menuItem
/* 182 */       .addActionListener(ae -> {
/*     */           restoreIfDone();
/*     */           
/*     */           focusColor(false);
/*     */           
/*     */           saveFillin(Op.fl[Op.FL.FlPuz.ordinal()]);
/*     */           
/*     */           CrosswordExpress.transfer(Def.prevMode, jfSolveFillin);
/*     */         });
/* 191 */     this.menu = new JMenu("View");
/* 192 */     menuBar.add(this.menu);
/* 193 */     this.menuItem = new JMenuItem("Display Options");
/* 194 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(89, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 195 */     this.menu.add(this.menuItem);
/* 196 */     this.menuItem
/* 197 */       .addActionListener(ae -> {
/*     */           printOptions(jfSolveFillin, "Display Options");
/*     */           
/*     */           restoreFrame();
/*     */           
/*     */           jlWords.setText("<html><font color=006644 size=3><font size=4>" + Op.msc[Op.MSC.Langwords.ordinal()]);
/*     */         });
/*     */     
/* 205 */     this.menu = new JMenu("Task");
/* 206 */     menuBar.add(this.menu);
/* 207 */     this.menuItem = new JMenuItem("Reveal One Letter");
/* 208 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(79, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 209 */     this.menu.add(this.menuItem);
/* 210 */     this.menuItem
/* 211 */       .addActionListener(ae -> {
/*     */           if (Methods.noReveal == 0) {
/*     */             Grid.sol[Grid.xCur][Grid.yCur] = Grid.letter[Grid.xCur][Grid.yCur];
/*     */           } else {
/*     */             Methods.noReveal(jfSolveFillin);
/*     */           } 
/*     */           
/*     */           updateWordList();
/*     */           
/*     */           restoreFrame();
/*     */         });
/* 222 */     ActionListener errorTimer = ae -> {
/*     */         Def.dispErrors = Boolean.valueOf(false);
/*     */         restoreFrame();
/*     */         this.myTimer.stop();
/*     */       };
/* 227 */     this.myTimer = new Timer(1500, errorTimer);
/*     */     
/* 229 */     this.menuItem = new JMenuItem("Reveal Errors");
/* 230 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(69, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 231 */     this.menu.add(this.menuItem);
/* 232 */     this.menuItem
/* 233 */       .addActionListener(ae -> {
/*     */           if (Methods.noErrors == 0) {
/*     */             this.myTimer.start();
/*     */             
/*     */             Def.dispErrors = Boolean.valueOf(true);
/*     */           } else {
/*     */             Methods.noReveal(jfSolveFillin);
/*     */           } 
/*     */           
/*     */           restoreFrame();
/*     */         });
/* 244 */     this.menuItem = new JMenuItem("Reveal Solution");
/* 245 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(83, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 246 */     this.menu.add(this.menuItem);
/* 247 */     this.menuItem
/* 248 */       .addActionListener(ae -> {
/*     */           if (Methods.noReveal == 0) {
/*     */             for (int j = 0; j < Grid.ySz; j++) {
/*     */               for (int i = 0; i < Grid.xSz; i++)
/*     */                 Grid.sol[i][j] = Grid.letter[i][j]; 
/*     */             } 
/*     */           } else {
/*     */             Methods.noReveal(jfSolveFillin);
/*     */           } 
/*     */           updateWordList();
/*     */           restoreFrame();
/*     */         });
/* 260 */     this.menuItem = new JMenuItem("Begin again");
/* 261 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(66, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 262 */     this.menu.add(this.menuItem);
/* 263 */     this.menuItem
/* 264 */       .addActionListener(ae -> {
/*     */           for (int j = 0; j < Grid.ySz; j++) {
/*     */             for (int i = 0; i < Grid.xSz; i++) {
/*     */               Grid.sol[i][j] = 0;
/*     */             }
/*     */           } 
/*     */           
/*     */           updateWordList();
/*     */           restoreFrame();
/*     */         });
/* 274 */     this.menu = new JMenu("Help");
/* 275 */     menuBar.add(this.menu);
/* 276 */     this.menuItem = new JMenuItem("Fillin Help");
/* 277 */     this.menu.add(this.menuItem);
/* 278 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(72, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 279 */     this.menuItem
/* 280 */       .addActionListener(ae -> Methods.cweHelp(jfSolveFillin, null, "Solving a Fillin Puzzle", this.fillinSolve));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 286 */     JLabel jl = new JLabel("<html><font color=006644 size=3><font size=4>Words");
/* 287 */     jl.setSize(180, 16);
/* 288 */     jl.setLocation(10, 39);
/* 289 */     jl.setHorizontalAlignment(2);
/* 290 */     jfSolveFillin.add(jl);
/*     */     
/* 292 */     lmWords = new DefaultListModel<>();
/* 293 */     jlstWords = new JList<>(lmWords);
/* 294 */     jlstWords.setFont(new Font("SansSerif", 1, 12));
/* 295 */     jspWords = new JScrollPane(jlstWords);
/* 296 */     jspWords.setLocation(10, 57);
/* 297 */     jspWords.setSize(180, 367);
/* 298 */     jspWords.setHorizontalScrollBarPolicy(31);
/* 299 */     jfSolveFillin.add(jspWords);
/*     */ 
/*     */     
/* 302 */     this.solveThread = (() -> {
/*     */         for (int j = 0; j < Grid.ySz; j++) {
/*     */           for (int i = 0; i < Grid.xSz; i++) {
/*     */             if (Grid.sol[i][j] != Grid.letter[i][j])
/*     */               return; 
/*     */           } 
/*     */         }  Methods.congratulations(jfSolveFillin);
/*     */       });
/* 310 */     pp = new FillinSolvePP(190, 37);
/* 311 */     jfSolveFillin.add(pp);
/*     */     
/* 313 */     pp
/* 314 */       .addMouseListener(new MouseAdapter() {
/*     */           public void mousePressed(MouseEvent e) {
/* 316 */             FillinSolve.this.updateGrid(e);
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 321 */     pp
/* 322 */       .addMouseMotionListener(new MouseAdapter() {
/*     */           public void mouseMoved(MouseEvent e) {
/* 324 */             if (Def.isMac) {
/* 325 */               FillinSolve.jfSolveFillin.setResizable((FillinSolve.jfSolveFillin.getWidth() - e.getX() < 200 && FillinSolve.jfSolveFillin
/* 326 */                   .getHeight() - e.getY() < 95));
/*     */             }
/*     */           }
/*     */         });
/*     */     
/* 331 */     jfSolveFillin
/* 332 */       .addKeyListener(new KeyAdapter() {
/*     */           public void keyPressed(KeyEvent e) {
/* 334 */             FillinSolve.this.handleKeyPressed(e);
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 339 */     loadSolvePuzzle();
/*     */   }
/*     */   
/*     */   static void restoreFrame() {
/* 343 */     jfSolveFillin.setVisible(true);
/* 344 */     Insets insets = jfSolveFillin.getInsets();
/* 345 */     panelW = jfSolveFillin.getWidth() - insets.left + insets.right + 190;
/* 346 */     panelH = jfSolveFillin.getHeight() - insets.top + insets.bottom + 37 + menuBar.getHeight();
/* 347 */     pp.setSize(panelW, panelH);
/* 348 */     setSizesAndOffsets(0, 0, panelW, panelH, 10, false);
/* 349 */     jfSolveFillin.requestFocusInWindow();
/* 350 */     jspWords.setSize(180, panelH - 30);
/* 351 */     jspWords.revalidate();
/* 352 */     pp.repaint();
/* 353 */     Methods.infoPanel(jl1, jl2, "Solve Fillin", "Dictionary : " + Op.fl[Op.FL.FlDic.ordinal()] + "  -|-  Puzzle : " + Op.fl[Op.FL.FlPuz
/* 354 */           .ordinal()], panelW + 190);
/*     */   }
/*     */   
/*     */   static void setSizesAndOffsets(int x, int y, int width, int height, int inset, boolean print) {
/* 358 */     int i = (width - inset) / Grid.xSz;
/* 359 */     int j = (height - inset) / Grid.ySz;
/* 360 */     Grid.xCell = Grid.yCell = (i < j) ? i : j;
/* 361 */     Grid.xOrg = print ? (x + (width - Grid.xSz * Grid.xCell) / 2) : 12;
/* 362 */     Grid.yOrg = print ? (y + (height - Grid.ySz * Grid.yCell) / 2) : 12;
/*     */   }
/*     */   
/*     */   static void printOptions(JFrame jf, String type) {
/* 366 */     String[] colorLabel = { "Cell Color", "Grid Color", "Pattern Cell Color", "Letter Color", "ID Color", "Focus Word Color", "Error Color" };
/* 367 */     int[] colorInt = { Op.FL.FlCell.ordinal(), Op.FL.FlGrid.ordinal(), Op.FL.FlPattern.ordinal(), Op.FL.FlLetters.ordinal(), Op.FL.FlID.ordinal(), Op.FL.FlFocus.ordinal(), Op.FL.FlError.ordinal() };
/* 368 */     String[] fontLabel = { "Select Puzzle Font", "Select ID Font" };
/* 369 */     int[] fontInt = { Op.FL.FlPuzzleFont.ordinal(), Op.FL.FlIDFont.ordinal() };
/* 370 */     String[] checkLabel = { "PPrint Puzzle with color.", "SPrint Solution with color." };
/* 371 */     int[] checkInt = { Op.FL.FlPuzColor.ordinal(), Op.FL.FlSolColor.ordinal() };
/* 372 */     Methods.stdPrintOptions(jf, "Fillin " + type, Op.fl, colorLabel, colorInt, fontLabel, fontInt, checkLabel, checkInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void saveFillin(String crosswordName) {
/*     */     try {
/* 383 */       DataOutputStream dataOut = new DataOutputStream(new FileOutputStream(Op.fl[Op.FL.FlDic.ordinal()] + ".dic/" + crosswordName));
/* 384 */       dataOut.writeInt(Grid.xSz);
/* 385 */       dataOut.writeInt(Grid.ySz);
/* 386 */       dataOut.writeByte(Methods.noReveal);
/* 387 */       dataOut.writeByte(Methods.noErrors); int i;
/* 388 */       for (i = 0; i < 54; i++)
/* 389 */         dataOut.writeByte(0); 
/* 390 */       for (int j = 0; j < Grid.ySz; j++) {
/* 391 */         for (i = 0; i < Grid.xSz; i++) {
/* 392 */           dataOut.writeInt(Grid.mode[i][j]);
/* 393 */           dataOut.writeInt(Grid.letter[i][j]);
/* 394 */           dataOut.writeInt(Grid.sol[i][j]);
/* 395 */           dataOut.writeInt(Grid.color[i][j]);
/*     */         } 
/* 397 */       }  dataOut.writeUTF(Methods.puzzleTitle);
/* 398 */       dataOut.writeUTF(Methods.author);
/* 399 */       dataOut.writeUTF(Methods.copyright);
/* 400 */       dataOut.writeUTF(Methods.puzzleNumber);
/* 401 */       dataOut.writeUTF(Methods.puzzleNotes);
/*     */       
/* 403 */       for (i = 0; i < NodeList.nodeListLength; i++) {
/* 404 */         dataOut.writeUTF((NodeList.nodeList[i]).word);
/* 405 */         dataOut.writeUTF((NodeList.nodeList[i]).clue);
/*     */       } 
/*     */       
/* 408 */       dataOut.close();
/*     */     }
/* 410 */     catch (IOException exc) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void loadFillin(String dictionaryName, String crosswordName) {
/* 417 */     File fl = new File(dictionaryName + ".dic/" + crosswordName);
/* 418 */     if (!fl.exists()) {
/* 419 */       fl = new File(dictionaryName + ".dic/");
/* 420 */       String[] s = fl.list(); int i;
/* 421 */       for (i = 0; i < s.length && (
/* 422 */         s[i].lastIndexOf(".crossword") == -1 || s[i].charAt(0) == '.'); i++);
/*     */       
/* 424 */       crosswordName = s[i];
/* 425 */       Op.fl[Op.FL.FlPuz.ordinal()] = crosswordName;
/*     */     } 
/*     */     
/*     */     try {
/* 429 */       DataInputStream dataIn = new DataInputStream(new FileInputStream(dictionaryName + ".dic/" + crosswordName));
/* 430 */       Grid.xSz = dataIn.readInt();
/* 431 */       Grid.ySz = dataIn.readInt();
/* 432 */       Methods.noReveal = dataIn.readByte();
/* 433 */       Methods.noErrors = dataIn.readByte(); int i;
/* 434 */       for (i = 0; i < 54; i++)
/* 435 */         dataIn.readByte(); 
/* 436 */       for (int j = 0; j < Grid.ySz; j++) {
/* 437 */         for (i = 0; i < Grid.xSz; i++) {
/* 438 */           Grid.mode[i][j] = dataIn.readInt();
/* 439 */           Grid.letter[i][j] = dataIn.readInt();
/* 440 */           Grid.sol[i][j] = dataIn.readInt();
/* 441 */           Grid.color[i][j] = dataIn.readInt();
/*     */         } 
/* 443 */       }  Methods.puzzleTitle = dataIn.readUTF();
/* 444 */       Methods.author = dataIn.readUTF();
/* 445 */       Methods.copyright = dataIn.readUTF();
/* 446 */       Methods.puzzleNumber = dataIn.readUTF();
/* 447 */       Methods.puzzleNotes = dataIn.readUTF();
/*     */       
/* 449 */       NodeList.buildNodeList();
/*     */       
/* 451 */       for (i = 0; i < NodeList.nodeListLength; i++) {
/* 452 */         (NodeList.nodeList[i]).word = dataIn.readUTF();
/* 453 */         (NodeList.nodeList[i]).clue = dataIn.readUTF();
/*     */       } 
/*     */       
/* 456 */       dataIn.close();
/*     */     }
/* 458 */     catch (IOException exc) {}
/*     */ 
/*     */     
/* 461 */     Grid.xCur = (NodeList.nodeList[0]).x;
/* 462 */     Grid.yCur = (NodeList.nodeList[0]).y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void drawFillin(Graphics2D g2) {
/* 469 */     boolean isError = false;
/*     */     
/* 471 */     int nL = (int)Math.ceil((Grid.xCell / 60.0F)), wL = (int)Math.ceil((Grid.xCell / 10.0F));
/* 472 */     Stroke normalStroke = new BasicStroke(nL, 2, 0);
/* 473 */     Stroke wideStroke = new BasicStroke(wL, 0, 0);
/* 474 */     g2.setStroke(normalStroke);
/*     */     
/* 476 */     RenderingHints rh = g2.getRenderingHints();
/* 477 */     rh.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
/* 478 */     g2.setRenderingHints(rh);
/*     */     
/*     */     int j;
/* 481 */     for (j = 0; j < Grid.ySz; j++) {
/* 482 */       for (int i = 0; i < Grid.xSz; i++) {
/* 483 */         if (Grid.mode[i][j] != 2)
/* 484 */         { int theColor; if (Def.dispWithColor.booleanValue()) {
/* 485 */             if (Def.dispErrors.booleanValue() && Grid.sol[i][j] != 0 && Grid.sol[i][j] != Grid.letter[i][j]) {
/* 486 */               theColor = Op.getColorInt(Op.FL.FlError.ordinal(), Op.fl);
/* 487 */               isError = true;
/*     */             }
/* 489 */             else if (Grid.curColor[i][j] != 16777215 && Def.dispCursor.booleanValue()) {
/* 490 */               theColor = Grid.curColor[i][j];
/* 491 */             } else if (Grid.color[i][j] != 16777215) {
/* 492 */               theColor = Grid.color[i][j];
/*     */             } else {
/* 494 */               theColor = Op.getColorInt(Op.FL.FlCell.ordinal(), Op.fl);
/*     */             } 
/*     */           } else {
/* 497 */             theColor = 16777215;
/* 498 */           }  g2.setColor(new Color(theColor));
/* 499 */           g2.fillRect(Grid.xOrg + i * Grid.xCell, Grid.yOrg + j * Grid.yCell, Grid.xCell, Grid.yCell); } 
/*     */       } 
/* 501 */     }  if (!isError) Def.dispErrors = Boolean.valueOf(false);
/*     */     
/* 503 */     Grid.drawPatternCells(g2, Def.dispWithColor.booleanValue() ? new Color(Op.getColorInt(Op.FL.FlPattern.ordinal(), Op.fl)) : Def.COLOR_BLACK, 
/* 504 */         Def.dispWithColor.booleanValue() ? new Color(Op.getColorInt(Op.FL.FlCell.ordinal(), Op.fl)) : Def.COLOR_WHITE, false);
/*     */ 
/*     */     
/* 507 */     g2.setColor(Def.dispWithColor.booleanValue() ? new Color(Op.getColorInt(Op.FL.FlGrid.ordinal(), Op.fl)) : Def.COLOR_BLACK);
/* 508 */     for (j = 0; j < Grid.ySz; j++) {
/* 509 */       for (int i = 0; i < Grid.xSz; i++) {
/* 510 */         if (Grid.mode[i][j] != 2)
/* 511 */           g2.drawRect(Grid.xOrg + i * Grid.xCell, Grid.yOrg + j * Grid.yCell, Grid.xCell, Grid.yCell); 
/*     */       } 
/* 513 */     }  g2.setFont(new Font(Op.fl[Op.FL.FlPuzzleFont.ordinal()], 0, 8 * Grid.yCell / 10));
/* 514 */     FontMetrics fm = g2.getFontMetrics();
/* 515 */     g2.setColor(Def.dispWithColor.booleanValue() ? new Color(Op.getColorInt(Op.FL.FlLetters.ordinal(), Op.fl)) : Def.COLOR_BLACK);
/* 516 */     for (j = 0; j < Grid.ySz; j++) {
/* 517 */       for (int i = 0; i < Grid.xSz; i++) {
/* 518 */         char ch = (char)(Def.dispSolArray.booleanValue() ? Grid.sol[i][j] : Grid.letter[i][j]);
/* 519 */         if (ch != '\000') {
/* 520 */           int w = fm.stringWidth("" + ch);
/* 521 */           g2.drawString("" + ch, Grid.xOrg + i * Grid.xCell + (Grid.xCell - w) / 2, Grid.yOrg + j * Grid.yCell + 9 * Grid.yCell / 10);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 526 */     g2.setStroke(wideStroke);
/* 527 */     Grid.drawBars(g2);
/*     */ 
/*     */     
/* 530 */     if (Def.dispCursor.booleanValue()) {
/* 531 */       g2.setColor(Def.COLOR_RED);
/* 532 */       g2.setStroke(wideStroke);
/* 533 */       g2.drawRect(Grid.xOrg + Grid.xCur * Grid.xCell, Grid.yOrg + Grid.yCur * Grid.yCell, Grid.xCell, Grid.yCell);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 544 */     g2.setStroke(new BasicStroke(1.0F));
/*     */   }
/*     */   
/*     */   static void printPuz(Graphics2D g2, int left, int top, int width, int height) {
/* 548 */     if (Def.puzzleMode == 60) {
/* 549 */       loadFillin(Op.cw[Op.CW.CwDic.ordinal()], Op.cw[Op.CW.CwPuz.ordinal()]);
/* 550 */     } else if (Def.puzzleMode == 13) {
/* 551 */       loadFillin(Op.ff[Op.FF.FfDic.ordinal()], Op.ff[Op.FF.FfPuz.ordinal()]);
/* 552 */     }  for (int y = 0; y < Grid.ySz; y++) {
/* 553 */       for (int x = 0; x < Grid.xSz; x++)
/* 554 */         Grid.sol[x][y] = 0; 
/* 555 */     }  setSizesAndOffsets(left, top, width, height, 0, true);
/* 556 */     Def.dispWithColor = Op.getBool(Op.FL.FlPuzColor.ordinal(), Op.fl);
/* 557 */     Def.dispSolArray = Boolean.valueOf(true);
/* 558 */     drawFillin(g2);
/* 559 */     Def.dispSolArray = Boolean.valueOf(false);
/* 560 */     Def.dispWithColor = Boolean.valueOf(true);
/*     */   }
/*     */   
/*     */   static void printSol(Graphics2D g2, int left, int top, int width, int height, String solutionPuzzle) {
/* 564 */     loadFillin(Op.cw[Op.CW.CwDic.ordinal()], solutionPuzzle);
/* 565 */     setSizesAndOffsets(left, top, width, height, 0, true);
/* 566 */     Def.dispWithColor = Op.getBool(Op.FL.FlSolColor.ordinal(), Op.fl);
/* 567 */     Def.dispGuideDigits = Boolean.valueOf(false);
/* 568 */     drawFillin(g2);
/* 569 */     Def.dispWithColor = Boolean.valueOf(true);
/* 570 */     Def.dispGuideDigits = Boolean.valueOf(true);
/* 571 */     loadFillin(Op.cw[Op.CW.CwDic.ordinal()], Op.cw[Op.CW.CwPuz.ordinal()]);
/*     */   }
/*     */   
/*     */   static void printSolTitle(Graphics2D g2, int left, int top, int width, int height, String solutionPuzzle) {
/* 575 */     loadFillin(Op.cw[Op.CW.CwDic.ordinal()], solutionPuzzle);
/* 576 */     Print.outputTextItem(g2, left, top, width, height, "SansSerif", 0, Methods.puzzleTitle);
/* 577 */     loadFillin(Op.cw[Op.CW.CwDic.ordinal()], Op.cw[Op.CW.CwPuz.ordinal()]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void updateWordList() {
/* 584 */     lmWords.clear();
/* 585 */     NodeList.sortNodeList(3); int i;
/* 586 */     for (i = 0; i < NodeList.nodeListLength; i++)
/* 587 */       lmWords.addElement(" " + (NodeList.nodeList[i]).word); 
/* 588 */     NodeList.sortNodeList(2);
/* 589 */     for (i = 0; i < NodeList.nodeListLength; i++) {
/* 590 */       String st = ""; int j;
/* 591 */       for (j = 0; j < (NodeList.nodeList[i]).length; j++) {
/* 592 */         int x = ((NodeList.nodeList[i]).cellLoc[j]).x;
/* 593 */         int y = ((NodeList.nodeList[i]).cellLoc[j]).y;
/* 594 */         if (!Character.isLetter(Grid.sol[x][y]) && !Character.isDigit(Grid.sol[x][y]))
/*     */           break; 
/* 596 */         st = st + (char)Grid.sol[x][y];
/*     */       } 
/* 598 */       if (j == (NodeList.nodeList[i]).length)
/* 599 */         lmWords.removeElement(" " + st); 
/*     */     } 
/*     */   }
/*     */   
/*     */   static void loadSolvePuzzle() {
/* 604 */     Grid.clearGrid();
/* 605 */     loadFillin(Op.fl[Op.FL.FlDic.ordinal()], Op.fl[Op.FL.FlPuz.ordinal()]);
/* 606 */     updateWordList();
/* 607 */     focusColor(true);
/* 608 */     restoreFrame();
/*     */   }
/*     */   void restoreIfDone() {
/*     */     int j;
/* 612 */     for (j = 0; j < Grid.ySz; j++) {
/* 613 */       for (int i = 0; i < Grid.xSz; i++)
/* 614 */       { if (Character.isLetter(Grid.letter[i][j]) && Grid.sol[i][j] != Grid.letter[i][j])
/*     */           return;  } 
/* 616 */     }  for (j = 0; j < Grid.ySz; j++) {
/* 617 */       for (int i = 0; i < Grid.xSz; i++)
/* 618 */         Grid.sol[i][j] = 0; 
/*     */     } 
/*     */   }
/*     */   static void focusColor(boolean set) {
/* 622 */     for (int i = 0; i < (NodeList.nodeList[Grid.nCur]).length; i++) {
/* 623 */       Grid.curColor[((NodeList.nodeList[Grid.nCur]).cellLoc[i]).x][((NodeList.nodeList[Grid.nCur]).cellLoc[i]).y] = set ? Op.getColorInt(Op.FL.FlFocus.ordinal(), Op.fl) : 16777215;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   void changeCursor() {
/* 629 */     if (Grid.nCur != -1) focusColor(false);
/*     */     
/* 631 */     int targetNodeA = Grid.horz[Grid.xNew][Grid.yNew];
/* 632 */     int targetNodeD = Grid.vert[Grid.xNew][Grid.yNew];
/* 633 */     if (Grid.xCur == Grid.xNew && Grid.yCur == Grid.yNew) {
/* 634 */       if (targetNodeA != -1 && targetNodeD != -1) {
/* 635 */         Grid.nCur = (Grid.nCur == targetNodeA) ? targetNodeD : targetNodeA;
/*     */       }
/*     */     } else {
/* 638 */       if ((targetNodeA != Grid.nCur && targetNodeD != Grid.nCur) || Grid.nCur == -1)
/*     */       {
/*     */ 
/*     */         
/* 642 */         if (Grid.xCur == Grid.xNew) {
/* 643 */           Grid.nCur = (targetNodeD != -1) ? targetNodeD : targetNodeA;
/*     */         } else {
/* 645 */           Grid.nCur = (targetNodeA != -1) ? targetNodeA : targetNodeD;
/*     */         }  } 
/* 647 */       Grid.xCur = Grid.xNew;
/* 648 */       Grid.yCur = Grid.yNew;
/*     */     } 
/*     */     
/* 651 */     if (Grid.nCur != -1) focusColor(true); 
/*     */   }
/*     */   
/*     */   void updateGrid(MouseEvent e) {
/* 655 */     int x = e.getX(), y = e.getY();
/*     */     
/* 657 */     if (Def.dispErrors.booleanValue()) { Def.dispErrors = Boolean.valueOf(false); return; }
/* 658 */      if (x < Grid.xOrg || y < Grid.yOrg)
/* 659 */       return;  int i = (x - Grid.xOrg) / Grid.xCell;
/* 660 */     int j = (y - Grid.yOrg) / Grid.yCell;
/* 661 */     if (i >= Grid.xSz || j >= Grid.ySz)
/* 662 */       return;  if (Grid.letter[i][j] == 0)
/*     */       return; 
/* 664 */     Grid.xNew = i; Grid.yNew = j;
/* 665 */     changeCursor();
/* 666 */     restoreFrame();
/*     */   }
/*     */   
/*     */   void handleKeyPressed(KeyEvent e) {
/*     */     Point[] cellLoc;
/* 671 */     int i = Grid.xCur, j = Grid.yCur;
/*     */ 
/*     */     
/* 674 */     if (e.isAltDown())
/* 675 */       return;  if (Def.dispErrors.booleanValue()) { Def.dispErrors = Boolean.valueOf(false); return; }
/* 676 */      char ch; switch (ch = (char)e.getKeyCode()) { case '&':
/* 677 */         for (; --j >= 0 && (NodeList.cellSignature(i, j) & 0x10) == 0; j--); if (j >= 0) Grid.yNew = j;  break;
/* 678 */       case '(': for (; ++j < Grid.ySz && (NodeList.cellSignature(i, j) & 0x10) == 0; j++); if (j < Grid.ySz) Grid.yNew = j;  break;
/* 679 */       case '%': for (; --i >= 0 && (NodeList.cellSignature(i, j) & 0x10) == 0; i--); if (i >= 0) Grid.xNew = i;  break;
/* 680 */       case '\'': for (; ++i < Grid.xSz && (NodeList.cellSignature(i, j) & 0x10) == 0; i++); if (i < Grid.xSz) Grid.xNew = i;  break;
/* 681 */       case '$': for (i = 0; (NodeList.cellSignature(i, j) & 0x10) == 0; i++); Grid.xNew = i; break;
/* 682 */       case '#': for (i = Grid.xSz - 1; (NodeList.cellSignature(i, j) & 0x10) == 0; i--); Grid.xNew = i; break;
/* 683 */       case '!': for (j = 0; (NodeList.cellSignature(i, j) & 0x10) == 0; j++); Grid.yNew = j; break;
/* 684 */       case '"': for (j = Grid.ySz - 1; (NodeList.cellSignature(i, j) & 0x10) == 0; j--); Grid.yNew = j; break;
/* 685 */       case '': Grid.sol[Grid.xCur][Grid.yCur] = 0; return;
/*     */       case '\n': break;
/*     */       case '\b':
/* 688 */         cellLoc = (NodeList.nodeList[Grid.nCur]).cellLoc;
/* 689 */         for (i = 0; i < (NodeList.nodeList[Grid.nCur]).length; i++) {
/* 690 */           if ((cellLoc[i]).x == Grid.xCur && (cellLoc[i]).y == Grid.yCur) {
/* 691 */             if (i > 0) {
/* 692 */               if (i != (NodeList.nodeList[Grid.nCur]).length - 1 || Grid.sol[Grid.xCur][Grid.yCur] == 0) {
/* 693 */                 Grid.xNew = (cellLoc[i - 1]).x;
/* 694 */                 Grid.yNew = (cellLoc[i - 1]).y;
/*     */               } 
/* 696 */               Grid.sol[Grid.xNew][Grid.yNew] = 0; break;
/*     */             }  return;
/*     */           } 
/*     */         } 
/*     */         break;
/*     */       default:
/* 702 */         if (ch == ' ') {
/* 703 */           Grid.sol[Grid.xCur][Grid.yCur] = 0;
/*     */         } else {
/* 705 */           if (!Character.isLetter(ch = e.getKeyChar()))
/* 706 */             return;  if (Grid.sol[Grid.xCur][Grid.yCur] != Character.toUpperCase(ch)) {
/* 707 */             Grid.sol[Grid.xCur][Grid.yCur] = Character.toUpperCase(ch);
/* 708 */             (new Thread(this.solveThread)).start();
/*     */           } 
/*     */         } 
/* 711 */         updateWordList();
/* 712 */         cellLoc = (NodeList.nodeList[Grid.nCur]).cellLoc;
/* 713 */         for (i = 0; i < (NodeList.nodeList[Grid.nCur]).length; i++) {
/* 714 */           if ((cellLoc[i]).x == Grid.xCur && (cellLoc[i]).y == Grid.yCur) {
/* 715 */             if (i < (NodeList.nodeList[Grid.nCur]).length - 1) {
/* 716 */               Grid.xNew = (cellLoc[i + 1]).x;
/* 717 */               Grid.yNew = (cellLoc[i + 1]).y;
/*     */               
/*     */               break;
/*     */             } 
/* 721 */             restoreFrame();
/*     */             return;
/*     */           } 
/*     */         } 
/*     */         break; }
/*     */     
/* 727 */     changeCursor();
/* 728 */     restoreFrame();
/*     */   }
/*     */ }


/* Location:              C:\Users\mustapha\Desktop\Crossword-Express.jar!\crosswordexpress\FillinSolve.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */