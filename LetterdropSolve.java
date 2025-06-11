/*     */ package crosswordexpress;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Insets;
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
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JMenu;
/*     */ import javax.swing.JMenuItem;
/*     */ import javax.swing.KeyStroke;
/*     */ 
/*     */ public final class LetterdropSolve extends JPanel {
/*  20 */   String letterdropSolve = "<div>Crossword Express <b>LETTER-DROP</b> puzzles consist of upper and lower sections which are more or less rectangular in shape. In each column of the puzzle, there are a number of vacant cells in the top section, and a corresponding number of letters in the cells of the lower section.<br/><br/> To solve such a puzzle, the solver must place the given letters into the vacant cells in the top section of the puzzle to reveal a message which will normally be a well known literary quotation.<br/><br/> Spaces in the quotation appear as black cells in the upper section, and any punctuation characters in the message will already appear in the upper section in their correct locations.<br/><br/></div> <span class='m'>Solving the puzzle</span><br/> <div>The process of solving a <b>LETTER-DROP</b> puzzle is controlled by the current location of a <b>cursor</b> cell. This cell may be moved around freely within the top half of the puzzle by means of a mouse click in the destination cell, or it may be shifted using the cursor control keys.<p/> If the cursor cell is indicating a vacant cell, then a solution character may be placed into that cell by means of one of the following:-</div> <ul> <li/>Type a character from the keyboard. This character will only be accepted if it matches one of the remaining characters in the hopper for the column in question. If it is accepted, the character will be removed from the hopper, and the cursor will advance to the next cell of the puzzle.<p/> <li/>Clicking onto one of the letters in the hopper will remove that letter from the hopper and place it into the cursor cell. Again, the cursor will advance to the next cell of the puzzle. </ul> <span class='m'>Menu Functions</span> <ul> <li/><span class='s'>File Menu</span> <ul> <li/><span >Load a Puzzle</span><br/> Use this option to choose the puzzle you want to solve from the pool of LETTER-DROP puzzles currently available on your computer.<p/> <li/><span >Quit Solving</span><br/> Returns you to the LETTER-DROP Construction screen.</ul> <li/><span class='s'>View Menu</span> <ul> <li/><span >Display Options</span><br/> This leads you to a dialog box in which you can change the colors of various elements within the puzzle, and control the fonts which will be used for the puzzle's text components. You can also decide if printing of the puzzle will be done in black and white or in color. </ul> <li/><span class='s'>Tasks Menu</span> <ul> <li/><span >Reveal One Letter</span><br/> If you need a little help to get started, this option will place the correct letter into the current focus cell.<p/> <li/><span >Reveal Errors</span><br/> If you think you may have made errors, this option will show you where they are by highlighting them for a period of 1.5 seconds.<p/> <li/><span >Reveal Solution</span><br/> The entire solution can be seen by selecting this option.<p/> <li/><span >Begin Again</span><br/> You can restart the entire solution process at any time by selecting this option. </ul> <li/><span class='s'>Help Menu</span> <ul> <li/><span >Letter-drop Help</span><br/> Displays the Help screen which you are now reading. </ul> </ul></body>";
/*     */ 
/*     */ 
/*     */   
/*     */   static JFrame jfSolveLetterdrop;
/*     */ 
/*     */ 
/*     */   
/*     */   static JMenuBar menuBar;
/*     */ 
/*     */ 
/*     */   
/*     */   JMenu menu;
/*     */ 
/*     */ 
/*     */   
/*     */   JMenu submenu;
/*     */ 
/*     */ 
/*     */   
/*     */   JMenuItem menuItem;
/*     */ 
/*     */ 
/*     */   
/*     */   static JPanel pp;
/*     */ 
/*     */ 
/*     */   
/*     */   static int panelW;
/*     */ 
/*     */ 
/*     */   
/*     */   static int panelH;
/*     */ 
/*     */ 
/*     */   
/*     */   static JLabel jl1;
/*     */ 
/*     */ 
/*     */   
/*     */   static JLabel jl2;
/*     */ 
/*     */   
/*     */   Timer myTimer;
/*     */ 
/*     */   
/*     */   Runnable solveThread;
/*     */ 
/*     */   
/*     */   int memMode;
/*     */ 
/*     */ 
/*     */   
/*     */   LetterdropSolve(JFrame jf) {
/*  74 */     this.memMode = Def.puzzleMode;
/*  75 */     Def.puzzleMode = 131;
/*  76 */     Def.dispCursor = Boolean.valueOf(true); Def.dispSolArray = Boolean.valueOf(true);
/*     */ 
/*     */     
/*  79 */     Grid.clearGrid();
/*     */     
/*  81 */     jfSolveLetterdrop = new JFrame("Solve a Letterdrop Puzzle");
/*  82 */     jfSolveLetterdrop.setSize(Op.getInt(Op.LD.LdW.ordinal(), Op.ld), Op.getInt(Op.LD.LdH.ordinal(), Op.ld));
/*  83 */     int frameX = (jf.getX() + jfSolveLetterdrop.getWidth() > Methods.scrW) ? (Methods.scrW - jfSolveLetterdrop.getWidth() - 10) : jf.getX();
/*  84 */     jfSolveLetterdrop.setLocation(frameX, jf.getY());
/*  85 */     jfSolveLetterdrop.setLayout((LayoutManager)null);
/*  86 */     jfSolveLetterdrop.setDefaultCloseOperation(0);
/*  87 */     jfSolveLetterdrop
/*  88 */       .addComponentListener(new ComponentAdapter()
/*     */         {
/*     */           public void componentResized(ComponentEvent ce) {
/*  91 */             int w = (LetterdropSolve.jfSolveLetterdrop.getWidth() < 600) ? 600 : LetterdropSolve.jfSolveLetterdrop.getWidth();
/*  92 */             int h = (LetterdropSolve.jfSolveLetterdrop.getHeight() < 400) ? 400 : LetterdropSolve.jfSolveLetterdrop.getHeight();
/*  93 */             LetterdropSolve.jfSolveLetterdrop.setSize(w, h);
/*  94 */             Op.setInt(Op.LD.LdW.ordinal(), w, Op.ld);
/*  95 */             Op.setInt(Op.LD.LdH.ordinal(), h, Op.ld);
/*  96 */             LetterdropSolve.restoreFrame();
/*     */           }
/*     */         });
/*     */     
/* 100 */     jfSolveLetterdrop
/* 101 */       .addWindowListener(new WindowAdapter() {
/*     */           public void windowClosing(WindowEvent we) {
/* 103 */             if (Def.selecting)
/* 104 */               return;  Op.saveOptions("letterdrop.opt", Op.ld);
/* 105 */             LetterdropSolve.this.restoreIfDone();
/* 106 */             LetterdropSolve.saveLetterdrop(Op.ld[Op.LD.LdPuz.ordinal()]);
/* 107 */             CrosswordExpress.transfer(130, LetterdropSolve.jfSolveLetterdrop);
/*     */           }
/*     */         });
/*     */     
/* 111 */     Methods.closeHelp();
/*     */     
/* 113 */     jl1 = new JLabel(); jfSolveLetterdrop.add(jl1);
/* 114 */     jl2 = new JLabel(); jfSolveLetterdrop.add(jl2);
/*     */ 
/*     */     
/* 117 */     menuBar = new JMenuBar();
/* 118 */     menuBar.setBackground(Def.COLOR_MENUBAR);
/* 119 */     jfSolveLetterdrop.setJMenuBar(menuBar);
/* 120 */     this.menu = new JMenu("File");
/* 121 */     menuBar.add(this.menu);
/* 122 */     this.menuItem = new JMenuItem("Load a Puzzle");
/* 123 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(76, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 124 */     this.menu.add(this.menuItem);
/* 125 */     this.menuItem
/* 126 */       .addActionListener(ae -> {
/*     */           if (Def.building == 1) {
/*     */             return;
/*     */           }
/*     */           restoreIfDone();
/*     */           saveLetterdrop(Op.ld[Op.LD.LdPuz.ordinal()]);
/*     */           new Select(jfSolveLetterdrop, "letterdrop", "letterdrop", Op.ld, Op.LD.LdPuz.ordinal(), false);
/*     */         });
/* 134 */     this.menuItem = new JMenuItem("Quit Solving");
/* 135 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(81, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 136 */     this.menu.add(this.menuItem);
/* 137 */     this.menuItem
/* 138 */       .addActionListener(ae -> {
/*     */           Op.saveOptions("letterdrop.opt", Op.ld);
/*     */           
/*     */           restoreIfDone();
/*     */           
/*     */           saveLetterdrop(Op.ld[Op.LD.LdPuz.ordinal()]);
/*     */           
/*     */           CrosswordExpress.transfer(130, jfSolveLetterdrop);
/*     */         });
/* 147 */     this.menu = new JMenu("View");
/* 148 */     menuBar.add(this.menu);
/* 149 */     this.menuItem = new JMenuItem("Display Options");
/* 150 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(89, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 151 */     this.menu.add(this.menuItem);
/* 152 */     this.menuItem
/* 153 */       .addActionListener(ae -> {
/*     */           LetterdropBuild.printOptions(jfSolveLetterdrop, "Display Options");
/*     */ 
/*     */           
/*     */           restoreFrame();
/*     */         });
/*     */     
/* 160 */     this.menu = new JMenu("Task");
/* 161 */     menuBar.add(this.menu);
/* 162 */     this.menuItem = new JMenuItem("Reveal one Letter");
/* 163 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(79, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 164 */     this.menu.add(this.menuItem);
/* 165 */     this.menuItem
/* 166 */       .addActionListener(ae -> {
/*     */           if (Methods.noReveal == 0) {
/*     */             if (Grid.sol[Grid.xCur][Grid.yCur] == 0) {
/*     */               Grid.sol[Grid.xCur][Grid.yCur] = Grid.letter[Grid.xCur][Grid.yCur];
/*     */               
/*     */               char ch = (char)Grid.letter[Grid.xCur][Grid.yCur];
/*     */               for (int i = Grid.ySz / 2 + 1; i < Grid.ySz; i++) {
/*     */                 if (Grid.sol[Grid.xCur][i] == ch) {
/*     */                   Grid.sol[Grid.xCur][i] = 0;
/*     */                   break;
/*     */                 } 
/*     */               } 
/*     */               advanceCursor();
/*     */               (new Thread(this.solveThread)).start();
/*     */             } 
/*     */           } else {
/*     */             Methods.noReveal(jfSolveLetterdrop);
/*     */           } 
/*     */           restoreFrame();
/*     */         });
/* 186 */     ActionListener errorTimer = ae -> {
/*     */         Def.dispErrors = Boolean.valueOf(false);
/*     */         pp.repaint();
/*     */         this.myTimer.stop();
/*     */       };
/* 191 */     this.myTimer = new Timer(1500, errorTimer);
/*     */     
/* 193 */     this.menuItem = new JMenuItem("Reveal Errors");
/* 194 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(69, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 195 */     this.menu.add(this.menuItem);
/* 196 */     this.menuItem
/* 197 */       .addActionListener(ae -> {
/*     */           if (Methods.noErrors == 0) {
/*     */             this.myTimer.start();
/*     */             
/*     */             Def.dispErrors = Boolean.valueOf(true);
/*     */           } else {
/*     */             Methods.noReveal(jfSolveLetterdrop);
/*     */           } 
/*     */           
/*     */           restoreFrame();
/*     */         });
/* 208 */     this.menuItem = new JMenuItem("Reveal Solution");
/* 209 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(83, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 210 */     this.menu.add(this.menuItem);
/* 211 */     this.menuItem
/* 212 */       .addActionListener(ae -> {
/*     */           if (Methods.noReveal == 0) {
/*     */             for (int j = 0; j < Grid.ySz; j++) {
/*     */               for (int i = 0; i < Grid.xSz; i++)
/*     */                 Grid.sol[i][j] = (j < Grid.ySz / 2) ? Grid.letter[i][j] : 0; 
/*     */             } 
/*     */           } else {
/*     */             Methods.noReveal(jfSolveLetterdrop);
/*     */           } 
/*     */           restoreFrame();
/*     */         });
/* 223 */     this.menuItem = new JMenuItem("Begin again");
/* 224 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(66, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 225 */     this.menu.add(this.menuItem);
/* 226 */     this.menuItem
/* 227 */       .addActionListener(ae -> {
/*     */           for (int j = 0; j < Grid.ySz; j++) {
/*     */             for (int i = 0; i < Grid.xSz; i++) {
/*     */               Grid.sol[i][j] = Grid.copy[i][j];
/*     */             }
/*     */           } 
/*     */           
/*     */           restoreFrame();
/*     */         });
/* 236 */     this.menu = new JMenu("Help");
/* 237 */     menuBar.add(this.menu);
/* 238 */     this.menuItem = new JMenuItem("Letterdrop Help");
/* 239 */     this.menu.add(this.menuItem);
/* 240 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(72, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 241 */     this.menuItem
/* 242 */       .addActionListener(ae -> Methods.cweHelp(jfSolveLetterdrop, null, "Solving Letterdrop Puzzles", this.letterdropSolve));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 249 */     this.solveThread = (() -> {
/*     */         for (int j = 0; j < Grid.ySz / 2; j++) {
/*     */           for (int i = 0; i < Grid.xSz; i++) {
/*     */             if (Grid.sol[i][j] != Grid.letter[i][j])
/*     */               return; 
/*     */           } 
/*     */         }  Methods.congratulations(jfSolveLetterdrop);
/*     */       });
/* 257 */     loadLetterdrop(Op.ld[Op.LD.LdPuz.ordinal()]);
/* 258 */     pp = new LetterdropSolvePP(0, 37);
/* 259 */     jfSolveLetterdrop.add(pp);
/*     */     
/* 261 */     pp
/* 262 */       .addMouseListener(new MouseAdapter() {
/*     */           public void mousePressed(MouseEvent e) {
/* 264 */             LetterdropSolve.this.updateGrid(e);
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 269 */     pp
/* 270 */       .addMouseMotionListener(new MouseAdapter() {
/*     */           public void mouseMoved(MouseEvent e) {
/* 272 */             if (Def.isMac) {
/* 273 */               LetterdropSolve.jfSolveLetterdrop.setResizable((LetterdropSolve.jfSolveLetterdrop.getWidth() - e.getX() < 15 && LetterdropSolve.jfSolveLetterdrop
/* 274 */                   .getHeight() - e.getY() < 95));
/*     */             }
/*     */           }
/*     */         });
/*     */     
/* 279 */     jfSolveLetterdrop
/* 280 */       .addKeyListener(new KeyAdapter() {
/*     */           public void keyPressed(KeyEvent e) {
/* 282 */             LetterdropSolve.this.handleKeyPressed(e);
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 287 */     restoreFrame();
/*     */   }
/*     */   
/*     */   static void restoreFrame() {
/* 291 */     jfSolveLetterdrop.setVisible(true);
/* 292 */     Insets insets = jfSolveLetterdrop.getInsets();
/* 293 */     panelW = jfSolveLetterdrop.getWidth() - insets.left + insets.right;
/* 294 */     panelH = jfSolveLetterdrop.getHeight() - insets.top + insets.bottom + 37 + menuBar.getHeight();
/* 295 */     pp.setSize(panelW, panelH);
/* 296 */     jfSolveLetterdrop.requestFocusInWindow();
/* 297 */     pp.repaint();
/* 298 */     Methods.infoPanel(jl1, jl2, "Solve Letterdrop", "Puzzle : " + Op.ld[Op.LD.LdPuz.ordinal()], panelW);
/*     */   }
/*     */   
/*     */   static void setSizesAndOffsets(int x, int y, int width, int height, int inset) {
/* 302 */     int i = (width - 20) / Grid.xSz;
/* 303 */     int j = (height - 20) / Grid.ySz;
/* 304 */     Grid.xCell = Grid.yCell = (i < j) ? i : j;
/* 305 */     Grid.xOrg = x + 10;
/* 306 */     Grid.yOrg = y + (height - Grid.yCell * Grid.ySz) / 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void saveLetterdrop(String letterdropName) {
/*     */     try {
/* 312 */       DataOutputStream dataOut = new DataOutputStream(new FileOutputStream("letterdrop/" + letterdropName));
/* 313 */       dataOut.writeInt(Grid.xSz);
/* 314 */       dataOut.writeInt(Grid.ySz);
/* 315 */       dataOut.writeByte(Methods.noReveal);
/* 316 */       dataOut.writeByte(Methods.noErrors);
/* 317 */       for (int i = 0; i < 54; i++)
/* 318 */         dataOut.writeByte(0); 
/* 319 */       for (int j = 0; j < Grid.ySz; j++) {
/* 320 */         for (int k = 0; k < Grid.xSz; k++) {
/* 321 */           dataOut.writeInt(Grid.mode[k][j]);
/* 322 */           dataOut.writeChar(Grid.letter[k][j]);
/* 323 */           dataOut.writeChar(Grid.sol[k][j]);
/* 324 */           dataOut.writeChar(Grid.copy[k][j]);
/*     */         } 
/* 326 */       }  dataOut.writeUTF(Methods.puzzleTitle);
/* 327 */       dataOut.writeUTF(Methods.author);
/* 328 */       dataOut.writeUTF(Methods.copyright);
/* 329 */       dataOut.writeUTF(Methods.puzzleNumber);
/* 330 */       dataOut.writeUTF(Methods.puzzleNotes);
/* 331 */       dataOut.close();
/*     */     }
/* 333 */     catch (IOException exc) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void loadLetterdrop(String letterdropName) {
/*     */     try {
/* 343 */       File fl = new File("letterdrop/" + letterdropName);
/* 344 */       if (!fl.exists()) {
/*     */         
/* 346 */         fl = new File("letterdrop/");
/* 347 */         String[] s = fl.list(); int m;
/* 348 */         for (m = 0; m < s.length && (
/* 349 */           s[m].lastIndexOf(".letterdrop") == -1 || s[m].charAt(0) == '.'); m++);
/*     */         
/* 351 */         letterdropName = s[m];
/* 352 */         Op.ld[Op.LD.LdPuz.ordinal()] = letterdropName;
/*     */       } 
/*     */ 
/*     */       
/* 356 */       DataInputStream dataIn = new DataInputStream(new FileInputStream("letterdrop/" + letterdropName));
/* 357 */       Grid.xSz = dataIn.readInt();
/* 358 */       Grid.ySz = dataIn.readInt();
/* 359 */       Methods.noReveal = dataIn.readByte();
/* 360 */       Methods.noErrors = dataIn.readByte(); int i;
/* 361 */       for (i = 0; i < 54; i++)
/* 362 */         dataIn.readByte(); 
/* 363 */       for (int k = 0; k < Grid.ySz; k++) {
/* 364 */         for (i = 0; i < Grid.xSz; i++) {
/* 365 */           Grid.mode[i][k] = dataIn.readInt();
/* 366 */           Grid.letter[i][k] = dataIn.readChar();
/* 367 */           Grid.sol[i][k] = dataIn.readChar();
/* 368 */           Grid.copy[i][k] = dataIn.readChar();
/*     */         } 
/* 370 */       }  Methods.puzzleTitle = dataIn.readUTF();
/* 371 */       Methods.author = dataIn.readUTF();
/* 372 */       Methods.copyright = dataIn.readUTF();
/* 373 */       Methods.puzzleNumber = dataIn.readUTF();
/* 374 */       Methods.puzzleNotes = dataIn.readUTF();
/* 375 */       dataIn.close();
/*     */     }
/* 377 */     catch (IOException exc) {
/*     */       return;
/*     */     } 
/*     */     
/* 381 */     StringBuilder buf = new StringBuilder(200);
/* 382 */     for (int j = 0; j < Grid.ySz / 2; j++) {
/* 383 */       for (int i = 0; i < Grid.xSz; i++) {
/* 384 */         char ch = (char)Grid.letter[i][j];
/* 385 */         if (Grid.mode[i][j] == 0) {
/* 386 */           buf.append(ch);
/* 387 */         } else if (Grid.mode[i][j] == 1) {
/* 388 */           buf.append(' ');
/*     */         } else {
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   static void drawLetterdrop(Graphics2D g2) {
/* 397 */     boolean isError = false;
/*     */     
/* 399 */     Stroke normalStroke = new BasicStroke(Grid.xCell / 20.0F, 2, 0);
/* 400 */     Stroke wideStroke = new BasicStroke((Grid.xCell < 30) ? 4.0F : (4 * Grid.xCell / 30), 2, 0);
/* 401 */     g2.setStroke(normalStroke);
/*     */     
/* 403 */     RenderingHints rh = g2.getRenderingHints();
/* 404 */     rh.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
/* 405 */     g2.setRenderingHints(rh);
/*     */     
/*     */     int j;
/* 408 */     for (j = 0; j < Grid.ySz; j++) {
/* 409 */       for (int i = 0; i < Grid.xSz; i++) {
/* 410 */         if (Grid.mode[i][j] != 2)
/* 411 */         { int theColor; if (Grid.mode[i][j] == 1) {
/* 412 */             theColor = Op.getColorInt(Op.LD.LdPattern.ordinal(), Op.ld);
/*     */           }
/* 414 */           else if (Def.dispErrors.booleanValue() && Grid.sol[i][j] != 0 && Grid.sol[i][j] != Grid.letter[i][j]) {
/* 415 */             theColor = Op.getColorInt(Op.LD.LdError.ordinal(), Op.ld);
/* 416 */             isError = true;
/*     */           } else {
/*     */             
/* 419 */             theColor = Op.getColorInt(Op.LD.LdCell.ordinal(), Op.ld);
/* 420 */           }  g2.setColor(new Color(theColor));
/* 421 */           g2.fillRect(Grid.xOrg + i * Grid.xCell, Grid.yOrg + j * Grid.yCell, Grid.xCell, Grid.yCell); } 
/*     */       } 
/* 423 */     }  if (!isError) Def.dispErrors = Boolean.valueOf(false);
/*     */ 
/*     */     
/* 426 */     g2.setColor(new Color(Op.getColorInt(Op.LD.LdGrid.ordinal(), Op.ld)));
/* 427 */     for (j = 0; j < Grid.ySz; j++) {
/* 428 */       for (int i = 0; i < Grid.xSz; i++) {
/* 429 */         if (Grid.mode[i][j] != 2)
/* 430 */           g2.drawRect(Grid.xOrg + i * Grid.xCell, Grid.yOrg + j * Grid.yCell, Grid.xCell, Grid.yCell); 
/*     */       } 
/*     */     } 
/* 433 */     g2.setColor(new Color(Op.getColorInt(Op.LD.LdLetter.ordinal(), Op.ld)));
/* 434 */     g2.setFont(new Font(Op.ld[Op.LD.LdFont.ordinal()], 0, 8 * Grid.yCell / 10));
/* 435 */     FontMetrics fm = g2.getFontMetrics();
/* 436 */     for (j = 0; j < Grid.ySz; j++) {
/* 437 */       for (int i = 0; i < Grid.xSz; i++) {
/* 438 */         char ch = (char)Grid.sol[i][j];
/* 439 */         if (ch != '\000') {
/* 440 */           int w = fm.stringWidth("" + ch);
/* 441 */           g2.drawString("" + ch, Grid.xOrg + i * Grid.xCell + (Grid.xCell - w) / 2, Grid.yOrg + j * Grid.yCell + (Grid.yCell + fm.getAscent() - fm.getDescent()) / 2);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 446 */     if (Def.dispCursor.booleanValue()) {
/* 447 */       g2.setColor(Def.COLOR_RED);
/* 448 */       g2.setStroke(wideStroke);
/* 449 */       g2.drawRect(Grid.xOrg + Grid.xCur * Grid.xCell, Grid.yOrg + Grid.yCur * Grid.yCell, Grid.xCell, Grid.yCell);
/*     */     } 
/*     */     
/* 452 */     g2.setStroke(new BasicStroke(1.0F));
/*     */   }
/*     */   void restoreIfDone() {
/*     */     int j;
/* 456 */     for (j = 0; j < Grid.ySz / 2; j++) {
/* 457 */       for (int i = 0; i < Grid.xSz; i++)
/* 458 */       { if (Grid.sol[i][j] == 0 && Grid.mode[i][j] == 0)
/*     */           return;  } 
/* 460 */     }  for (j = 0; j < Grid.ySz; j++) {
/* 461 */       for (int i = 0; i < Grid.xSz; i++)
/* 462 */         Grid.sol[i][j] = Grid.copy[i][j]; 
/*     */     } 
/*     */   }
/*     */   void updateGrid(MouseEvent e) {
/* 466 */     int x = e.getX(), y = e.getY();
/*     */     
/* 468 */     if (Def.dispErrors.booleanValue()) { Def.dispErrors = Boolean.valueOf(false); restoreFrame(); return; }
/* 469 */      if (x < Grid.xOrg || y < Grid.yOrg)
/*     */       return; 
/* 471 */     x = (x - Grid.xOrg) / Grid.xCell;
/* 472 */     y = (y - Grid.yOrg) / Grid.yCell;
/* 473 */     if (x >= Grid.xSz || y > Grid.ySz || Grid.mode[x][y] != 0)
/*     */       return; 
/* 475 */     if (y < Grid.ySz / 2) {
/* 476 */       Grid.xCur = x;
/* 477 */       Grid.yCur = y;
/*     */     
/*     */     }
/* 480 */     else if (x == Grid.xCur && Grid.sol[x][y] != 0 && Grid.sol[x][Grid.yCur] == 0) {
/* 481 */       Grid.sol[x][Grid.yCur] = Grid.sol[x][y];
/* 482 */       Grid.sol[x][y] = 0;
/* 483 */       advanceCursor();
/* 484 */       (new Thread(this.solveThread)).start();
/*     */     } 
/* 486 */     restoreFrame();
/*     */   }
/*     */ 
/*     */   
/*     */   void handleKeyPressed(KeyEvent e) {
/*     */     int i;
/*     */     char ch;
/* 493 */     if (Def.dispErrors.booleanValue()) { Def.dispErrors = Boolean.valueOf(false); restoreFrame(); return; }
/* 494 */      if (e.isAltDown())
/* 495 */       return;  switch (e.getKeyCode()) { case 38:
/* 496 */         for (i = Grid.yCur - 1; Grid.yCur > 0; ) { if (Grid.mode[Grid.xCur][i] == 0) { Grid.yCur = i; break; }  i--; }  break;
/* 497 */       case 40: for (i = Grid.yCur + 1; i < Grid.ySz / 2; ) { if (Grid.mode[Grid.xCur][i] == 0) { Grid.yCur = i; break; }  i++; }  break;
/* 498 */       case 37: for (i = Grid.xCur - 1; Grid.xCur > 0; ) { if (Grid.mode[i][Grid.yCur] == 0) { Grid.xCur = i; break; }  i--; }  break;
/* 499 */       case 39: for (i = Grid.xCur + 1; i < Grid.xSz; ) { if (Grid.mode[i][Grid.yCur] == 0) { Grid.xCur = i; break; }  i++; }  break;
/* 500 */       case 36: for (Grid.xCur = 0; Grid.mode[Grid.xCur][Grid.yCur] != 0; Grid.xCur++); break;
/* 501 */       case 35: for (Grid.xCur = Grid.xSz - 1; Grid.mode[Grid.xCur][Grid.yCur] != 0; Grid.xCur--); break;
/* 502 */       case 33: for (Grid.yCur = 0; Grid.mode[Grid.xCur][Grid.yCur] != 0; Grid.yCur++); break;
/* 503 */       case 34: for (Grid.yCur = Grid.ySz / 2 - 1; Grid.mode[Grid.xCur][Grid.yCur] != 0; Grid.yCur--); break;
/* 504 */       case 127: deleteCharacter(); break;
/*     */       default:
/* 506 */         ch = e.getKeyChar();
/* 507 */         if (ch == ' ') {
/* 508 */           deleteCharacter();
/*     */           break;
/*     */         } 
/* 511 */         if (Grid.sol[Grid.xCur][Grid.yCur] != 0)
/*     */           break; 
/* 513 */         for (i = Grid.ySz / 2 + 1; i < Grid.ySz; i++) {
/* 514 */           if (Grid.sol[Grid.xCur][i] == ch) {
/* 515 */             Grid.sol[Grid.xCur][i] = 0;
/* 516 */             Grid.sol[Grid.xCur][Grid.yCur] = ch;
/* 517 */             advanceCursor();
/* 518 */             (new Thread(this.solveThread)).start(); break;
/*     */           } 
/*     */         }  break; }
/*     */     
/* 522 */     restoreFrame();
/*     */   }
/*     */   
/*     */   void deleteCharacter() {
/* 526 */     if (Grid.copy[Grid.xCur][Grid.yCur] != 0)
/*     */       return; 
/* 528 */     char ch = (char)Grid.sol[Grid.xCur][Grid.yCur];
/* 529 */     Grid.sol[Grid.xCur][Grid.yCur] = 0;
/* 530 */     for (int i = Grid.ySz - 1;; i--) {
/* 531 */       if (Grid.sol[Grid.xCur][i] == 0 && Grid.copy[Grid.xCur][i] == ch) {
/* 532 */         Grid.sol[Grid.xCur][i] = ch;
/* 533 */         advanceCursor();
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   void advanceCursor() {
/* 539 */     if ((Grid.xCur == Grid.xSz - 1 && Grid.yCur == Grid.ySz - 1) || Grid.mode[Grid.xCur + 1][Grid.yCur] == 2)
/*     */       return; 
/*     */     do {
/* 542 */       Grid.xCur++;
/* 543 */       if (Grid.xCur != Grid.xSz)
/* 544 */         continue;  Grid.xCur = 0;
/* 545 */       Grid.yCur++;
/*     */     }
/* 547 */     while (Grid.mode[Grid.xCur][Grid.yCur] == 1);
/*     */   }
/*     */ }


/* Location:              C:\Users\mustapha\Desktop\Crossword-Express.jar!\crosswordexpress\LetterdropSolve.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */