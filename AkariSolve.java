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
/*     */ import java.awt.event.WindowAdapter;
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
/*     */ public final class AkariSolve {
/*     */   static JFrame jfSolveAkari;
/*     */   static JMenuBar menuBar;
/*  27 */   String[] akariTitle = new String[] { "An error has been detected", "REQUIRED LAMP(S)", "TYPE 1 PROHIBITED LAMP(S)", "ORPHAN CELLS", "TYPE 2 PROHIBITED LAMP(S)", "ILLUMINATE PROHIBITED LAMPS", "TYPE 3 PROHIBITED LAMP(S)", "THREE / ONE CONFIGURATION", "TWO / ONE CONFIGURATION" }; JMenu menu; JMenu submenu; JMenuItem menuItem; static JPanel pp;
/*     */   static int panelW;
/*     */   static int panelH;
/*     */   static JLabel jl1;
/*     */   static JLabel jl2;
/*     */   Timer myTimer;
/*     */   Runnable solveThread;
/*     */   int memMode;
/*     */   static final int LAMP = 1;
/*     */   static final int NOLAMP = 2;
/*     */   static final int LIT = 4;
/*     */   static final int PATTERN = 8;
/*  39 */   String akariSolve = "<div>An AKARI puzzle consists of a square grid in which some of the cells are painted black in the same way as in a crossword puzzle. Think of it as the floor plan of a building in which the black cells represent pillars or wall segments. The remainder of the cells represent floor area which must be illuminated by lamps placed in certain of the cells. A lamp illuminates all cells in its row and column up to the edge of the puzzle, or up to an intervening black cell. No lamp is allowed to illuminate any other lamp, and the numbers in some of the black cells indicate the number of lamps which are adjacent to that cell both horizontally and vertically. Each puzzle has a unique solution which does not require any guesswork to achieve. Puzzles can be built either manually or automatically in sizes up to 16x16.<p/>Solving an AKARI puzzle is a mouse only operation. A single click in any DARK cell will convert that cell to a NOLAMP cell which has a default color of yellow. A second click will convert it to a LAMP cell, and all cells in its row and column will be converted to the LIT condition which has a default color of white. One more click will convert it back to a DARK cell, and appropriate LIT cells will also be converted back to the DARK condition.<p/></div><span class='m'>Menu Functions</span><br/><br/><ul><li/><span class ='s'>File Menu</span><br/><ul><li/><span>Load a Puzzle</span><br/>Use this option to choose the puzzle you want to solve from the pool of AKARI puzzles currently available on your computer.<p/><li/><span>Quit Solving</span><br/>Returns you to the AKARI Build screen.<p/></ul><li/><span class='s'>View Menu</span><br/><ul><li/><span>Display Options</span><br/>This leads you to a dialog box in which you can change the colors of various elements within the puzzle, and control the fonts which will be used for the puzzle's text components. You can also decide if printing of the puzzle will be done in black and white or in color.<p/></ul><li/><span class='s'>Tasks Menu</span><br/><ul><li/><span>Reveal Errors</span><br/>If you think you may have made errors, this option will show you where they are by highlighting them for a period of 1.5 seconds.<p/><li/><span>Reveal Solution</span><br/>The entire solution can be seen by selecting this option.<p/><li/><span>Request a Hint</span><br/>This option will highlight one or more puzzle cells in blue to indicate the location within the puzzle where further progress can be made. In addition an automatic Help screen will appear giving you a detailed description of the strategy you will need to apply.<p/><li/><span>Begin Again</span><br/>You can restart the entire solution process at any time by selecting this option.<p/></ul><li/><span class='s'>Help Menu</span><ul><li/><span>Akari Help</span><br/>Displays the Help screen which you are now reading.<p/></ul></ul></body>";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  84 */   String[] akariHint = new String[] { "<div>Your partial solution has been checked and found to be in error in those cells which are highlighted in blue. Under these circumstances, it is not possible to provide a trustworthy hint as to the next step in the solution.<p/>Please investigate, and correct the errors before proceeding with the solution process.</div></body>", "<div>This is the most fundamental strategy available for the solving of Akari puzzles. If you make a puzzle having a difficulty level of 1, then you will be able to solve it completely using only this strategy.<p/>The highlighted cells all adjoin a number cell for which the number of additional lamps required to complete its quota is equal to the number of highlighted cells. As a result, all of the highlighted cells may be converted to lamps by means of two mouse clicks.</div></body>", "<div>The highlighted cells adjoin a number cell which either requires no adjacent lamps, or has already achieved its quota of adjacent lamps. Consequently, all of the highlighted cells may be declared as prohibited cells by means of a single mouse click.</div></body>", "<div>The single highlighted cell needs to be illuminated, but an examination of the row and column in which it is located reveals that there are no vacant cells which can see the highlighted cell. It can only be illuminated by inserting a lamp into it by means of two clicks of the mouse.</div></body>", "<div>Each of the highlighted cell(s) can be 'seen' by a pair of cells, one in the same row and one in the same column, such that one of these cells MUST contain a lamp. We don't know at this stage which one it will be, but in any event, we know that the highlighted cell in question will be illuminated by it. Each of the highlighted cells may therefore be declared prohibited by means of a single mouse click.</div></body>", "<div>The highlighted vacant cell can 'see' a prohibited cell which cannot be 'seen' by any other vacant cell in the puzzle. It follows then, that a lamp must be placed in this cell.</div></body>", "<div>Nearby to the highlighted cell, there is a prohibited cell which will ultimately be illuminated by one of two cells, both of which can 'see' the highlighted cell. This means that when the prohibited cell is finally illuminated, the highlighted cell will also be illuminated. Consequently, the highlighted cell can be converted to a prohibited cell by means of a single mouse click.</div></body>", "<div>The highlighted cell draws your attention to a number cell containing a 3. This cell requires three lamps, but has four vacant adjacent cells. The diagonally adjacent number cell containing a 1 shares two vacant cells with the '3' number cell, and so only one of these cells can contain a lamp. We don't know at this stage which one it will be, but we do know that both the highlighted cells must contain a lamp.", "<div>The highlighted cell draws your attention to a number cell containing a 2. This cell requires two lamps, but has three vacant adjacent cells. The diagonally adjacent number cell containing a 1 shares two vacant cells with the '2' number cell, and so only one of these cells can contain a lamp. We don't know at this stage which one it will be, but we do know that the highlighted cell must contain a lamp." };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   AkariSolve(JFrame jf) {
/* 123 */     this.memMode = Def.puzzleMode;
/* 124 */     Def.puzzleMode = 21;
/* 125 */     Def.dispCursor = Boolean.valueOf(true);
/*     */     
/* 127 */     jfSolveAkari = new JFrame("Akari");
/* 128 */     jfSolveAkari.setSize(Op.getInt(Op.AK.AkW.ordinal(), Op.ak), Op.getInt(Op.AK.AkH.ordinal(), Op.ak));
/* 129 */     int frameX = (jf.getX() + jfSolveAkari.getWidth() > Methods.scrW) ? (Methods.scrW - jfSolveAkari.getWidth() - 10) : jf.getX();
/* 130 */     jfSolveAkari.setLocation(frameX, jf.getY());
/* 131 */     jfSolveAkari.setLayout((LayoutManager)null);
/* 132 */     jfSolveAkari.getContentPane().setBackground(Def.COLOR_FRAMEBG);
/* 133 */     jfSolveAkari.setDefaultCloseOperation(0);
/* 134 */     jfSolveAkari
/* 135 */       .addComponentListener(new ComponentAdapter() {
/*     */           public void componentResized(ComponentEvent ce) {
/* 137 */             int oldw = Op.getInt(Op.AK.AkW.ordinal(), Op.ak);
/* 138 */             int oldh = Op.getInt(Op.AK.AkH.ordinal(), Op.ak);
/* 139 */             Methods.frameResize(AkariSolve.jfSolveAkari, oldw, oldh, 500, 580);
/* 140 */             Op.setInt(Op.AK.AkW.ordinal(), AkariSolve.jfSolveAkari.getWidth(), Op.ak);
/* 141 */             Op.setInt(Op.AK.AkH.ordinal(), AkariSolve.jfSolveAkari.getHeight(), Op.ak);
/* 142 */             AkariSolve.restoreFrame("");
/*     */           }
/*     */         });
/*     */     
/* 146 */     jfSolveAkari
/* 147 */       .addWindowListener(new WindowAdapter() {
/*     */           public void windowClosing(WindowEvent we) {
/* 149 */             if (Def.selecting)
/* 150 */               return;  Op.saveOptions("akari.opt", Op.ak);
/* 151 */             AkariSolve.this.restoreIfDone();
/* 152 */             AkariSolve.saveAkari(Op.ak[Op.AK.AkPuz.ordinal()]);
/* 153 */             CrosswordExpress.transfer(20, AkariSolve.jfSolveAkari);
/*     */           }
/*     */         });
/*     */     
/* 157 */     Methods.closeHelp();
/*     */ 
/*     */     
/* 160 */     this.solveThread = (() -> {
/*     */         for (int j = 0; j < Grid.ySz; j++) {
/*     */           for (int i = 0; i < Grid.xSz; i++) {
/*     */             if ((Grid.sol[i][j] & 0x5) != (Grid.answer[i][j] & 0x5))
/*     */               return; 
/*     */           } 
/*     */         }  Methods.congratulations(jfSolveAkari);
/*     */       });
/* 168 */     jl1 = new JLabel(); jfSolveAkari.add(jl1);
/* 169 */     jl2 = new JLabel(); jfSolveAkari.add(jl2);
/*     */ 
/*     */     
/* 172 */     menuBar = new JMenuBar();
/* 173 */     menuBar.setBackground(Def.COLOR_MENUBAR);
/* 174 */     jfSolveAkari.setJMenuBar(menuBar);
/*     */     
/* 176 */     this.menu = new JMenu("File");
/* 177 */     menuBar.add(this.menu);
/* 178 */     this.menuItem = new JMenuItem("Load a Puzzle");
/* 179 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(76, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 180 */     this.menu.add(this.menuItem);
/* 181 */     this.menuItem
/* 182 */       .addActionListener(ae -> {
/*     */           if (Def.building == 1) {
/*     */             return;
/*     */           }
/*     */           restoreIfDone();
/*     */           saveAkari(Op.ak[Op.AK.AkPuz.ordinal()]);
/*     */           new Select(jfSolveAkari, "akari", "akari", Op.ak, Op.AK.AkPuz.ordinal(), false);
/*     */         });
/* 190 */     this.menuItem = new JMenuItem("Quit Solving");
/* 191 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(81, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 192 */     this.menu.add(this.menuItem);
/* 193 */     this.menuItem
/* 194 */       .addActionListener(ae -> {
/*     */           Op.saveOptions("akari.opt", Op.ak);
/*     */           
/*     */           restoreIfDone();
/*     */           
/*     */           saveAkari(Op.ak[Op.AK.AkPuz.ordinal()]);
/*     */           
/*     */           CrosswordExpress.transfer(20, jfSolveAkari);
/*     */         });
/* 203 */     this.menu = new JMenu("View");
/* 204 */     menuBar.add(this.menu);
/* 205 */     this.menuItem = new JMenuItem("Display Options");
/* 206 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(89, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 207 */     this.menu.add(this.menuItem);
/* 208 */     this.menuItem
/* 209 */       .addActionListener(ae -> {
/*     */           AkariBuild.printOptions(jfSolveAkari, "Display Options");
/*     */ 
/*     */           
/*     */           restoreFrame("");
/*     */         });
/*     */     
/* 216 */     this.menu = new JMenu("Tasks");
/* 217 */     menuBar.add(this.menu);
/*     */     
/* 219 */     ActionListener errorTimer = ae -> {
/*     */         Def.dispErrors = Boolean.valueOf(false);
/*     */         pp.repaint();
/*     */         this.myTimer.stop();
/*     */       };
/* 224 */     this.myTimer = new Timer(1500, errorTimer);
/*     */     
/* 226 */     this.menuItem = new JMenuItem("Reveal Errors");
/* 227 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(69, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 228 */     this.menu.add(this.menuItem);
/* 229 */     this.menuItem
/* 230 */       .addActionListener(ae -> {
/*     */           if (Methods.noErrors == 0) {
/*     */             this.myTimer.start();
/*     */             
/*     */             Def.dispErrors = Boolean.valueOf(true);
/*     */           } else {
/*     */             Methods.noReveal(jfSolveAkari);
/*     */           } 
/*     */           
/*     */           pp.repaint();
/*     */         });
/* 241 */     this.menuItem = new JMenuItem("Reveal Solution");
/* 242 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(83, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 243 */     this.menu.add(this.menuItem);
/* 244 */     this.menuItem
/* 245 */       .addActionListener(ae -> {
/*     */           if (Methods.noReveal == 0) {
/*     */             for (int j = 0; j < Grid.ySz; j++) {
/*     */               for (int i = 0; i < Grid.xSz; i++) {
/*     */                 Grid.sol[i][j] = Grid.answer[i][j];
/*     */               }
/*     */             } 
/*     */           } else {
/*     */             Methods.noReveal(jfSolveAkari);
/*     */           } 
/*     */           restoreFrame("");
/*     */         });
/* 257 */     this.menuItem = new JMenuItem("Request a Hint");
/* 258 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(82, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 259 */     this.menu.add(this.menuItem);
/* 260 */     this.menuItem
/* 261 */       .addActionListener(ae -> {
/*     */           Grid.findHint = Boolean.valueOf(true);
/*     */           
/*     */           int hintNum = AkariBuild.nextHint();
/*     */           
/*     */           Methods.closeHelp();
/*     */           Methods.cweHelp(jfSolveAkari, null, this.akariTitle[hintNum + 1], this.akariHint[hintNum + 1]);
/*     */           restoreFrame("");
/*     */           Methods.toFront(jfSolveAkari);
/*     */           Grid.findHint = Boolean.valueOf(false);
/*     */         });
/* 272 */     this.menuItem = new JMenuItem("Begin again");
/* 273 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(66, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 274 */     this.menu.add(this.menuItem);
/* 275 */     this.menuItem
/* 276 */       .addActionListener(ae -> {
/*     */           clearSolution();
/*     */ 
/*     */           
/*     */           restoreFrame("");
/*     */         });
/*     */     
/* 283 */     this.menu = new JMenu("Help");
/* 284 */     menuBar.add(this.menu);
/* 285 */     this.menuItem = new JMenuItem("Akari Help");
/* 286 */     this.menu.add(this.menuItem);
/* 287 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(72, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 288 */     this.menuItem
/* 289 */       .addActionListener(ae -> Methods.cweHelp(jfSolveAkari, null, "Solving an Akari Puzzle", this.akariSolve));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 295 */     loadAkari(Op.ak[Op.AK.AkPuz.ordinal()]);
/* 296 */     pp = new AkariSolvePP(0, 37);
/* 297 */     jfSolveAkari.add(pp);
/* 298 */     pp
/* 299 */       .addMouseListener(new MouseAdapter() {
/*     */           public void mousePressed(MouseEvent e) {
/* 301 */             AkariSolve.this.updateGrid(e);
/*     */           }
/*     */         });
/*     */     
/* 305 */     pp
/* 306 */       .addMouseMotionListener(new MouseAdapter() {
/*     */           public void mouseMoved(MouseEvent e) {
/* 308 */             if (Def.isMac) {
/* 309 */               AkariSolve.jfSolveAkari.setResizable((AkariSolve.jfSolveAkari.getWidth() - e.getX() < 15 && AkariSolve.jfSolveAkari
/* 310 */                   .getHeight() - e.getY() < 95));
/*     */             }
/*     */           }
/*     */         });
/*     */     
/* 315 */     restoreFrame("");
/*     */   }
/*     */   
/*     */   static void restoreFrame(String hint) {
/* 319 */     jfSolveAkari.setVisible(true);
/* 320 */     Insets insets = jfSolveAkari.getInsets();
/* 321 */     panelW = jfSolveAkari.getWidth() - insets.left + insets.right;
/* 322 */     panelH = jfSolveAkari.getHeight() - insets.top + insets.bottom + 37 + menuBar.getHeight();
/* 323 */     pp.setSize(panelW, panelH);
/* 324 */     jfSolveAkari.requestFocusInWindow();
/* 325 */     pp.repaint();
/* 326 */     Methods.infoPanel(jl1, jl2, "Solve Akari", (hint.length() > 0) ? ("  Hint : " + hint) : ("Puzzle : " + Op.ak[Op.AK.AkPuz.ordinal()]), panelW);
/*     */   }
/*     */   
/*     */   static void setSizesAndOffsets(int x, int y, int width, int height, int inset, boolean print) {
/* 330 */     int i = (width - inset) / Grid.xSz;
/* 331 */     int j = (height - inset) / Grid.ySz;
/* 332 */     Grid.xCell = Grid.yCell = (i < j) ? i : j;
/* 333 */     Grid.xOrg = print ? (x + (width - Grid.xSz * Grid.xCell) / 2) : (x + 10);
/* 334 */     Grid.yOrg = print ? (y + (height - Grid.ySz * Grid.yCell) / 2) : (y + 10);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void saveAkari(String akariName) {
/*     */     try {
/* 341 */       DataOutputStream dataOut = new DataOutputStream(new FileOutputStream("akari/" + akariName));
/* 342 */       dataOut.writeInt(Grid.xSz);
/* 343 */       dataOut.writeInt(Grid.ySz);
/* 344 */       dataOut.writeByte(Methods.noReveal);
/* 345 */       dataOut.writeByte(Methods.noErrors);
/* 346 */       for (int i = 0; i < 54; i++)
/* 347 */         dataOut.writeByte(0); 
/* 348 */       for (int j = 0; j < Grid.ySz; j++) {
/* 349 */         for (int k = 0; k < Grid.xSz; k++) {
/* 350 */           dataOut.writeInt(Grid.sol[k][j]);
/* 351 */           dataOut.writeInt(Grid.answer[k][j]);
/* 352 */           dataOut.writeInt(Grid.puzzle[k][j]);
/*     */         } 
/* 354 */       }  dataOut.writeUTF(Methods.puzzleTitle);
/* 355 */       dataOut.writeUTF(Methods.author);
/* 356 */       dataOut.writeUTF(Methods.copyright);
/* 357 */       dataOut.writeUTF(Methods.puzzleNumber);
/* 358 */       dataOut.writeUTF(Methods.puzzleNotes);
/* 359 */       dataOut.close();
/*     */     }
/* 361 */     catch (IOException exc) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void loadAkari(String akariName) {
/*     */     try {
/* 369 */       File fl = new File("akari/" + akariName);
/* 370 */       if (!fl.exists()) {
/* 371 */         fl = new File("akari/");
/* 372 */         String[] s = fl.list(); int m;
/* 373 */         for (m = 0; m < s.length && (
/* 374 */           s[m].lastIndexOf(".akari") == -1 || s[m].charAt(0) == '.'); m++);
/*     */         
/* 376 */         akariName = s[m];
/* 377 */         Op.ak[Op.AK.AkPuz.ordinal()] = akariName;
/*     */       } 
/*     */       
/* 380 */       DataInputStream dataIn = new DataInputStream(new FileInputStream("akari/" + akariName));
/* 381 */       Grid.xSz = dataIn.readInt();
/* 382 */       Grid.ySz = dataIn.readInt();
/* 383 */       Methods.noReveal = dataIn.readByte();
/* 384 */       Methods.noErrors = dataIn.readByte(); int i;
/* 385 */       for (i = 0; i < 54; i++)
/* 386 */         dataIn.readByte(); 
/* 387 */       for (int k = 0; k < Grid.ySz; k++) {
/* 388 */         for (i = 0; i < Grid.xSz; i++) {
/* 389 */           Grid.sol[i][k] = dataIn.readInt();
/* 390 */           Grid.answer[i][k] = dataIn.readInt();
/* 391 */           Grid.puzzle[i][k] = dataIn.readInt();
/*     */         } 
/* 393 */       }  Methods.puzzleTitle = dataIn.readUTF();
/* 394 */       Methods.author = dataIn.readUTF();
/* 395 */       Methods.copyright = dataIn.readUTF();
/* 396 */       Methods.puzzleNumber = dataIn.readUTF();
/* 397 */       Methods.puzzleNotes = dataIn.readUTF();
/* 398 */       dataIn.close();
/*     */     }
/* 400 */     catch (IOException exc) {
/*     */       return;
/*     */     } 
/* 403 */     for (int j = 0; j < Grid.ySz; j++) {
/* 404 */       for (int i = 0; i < Grid.xSz; i++) {
/* 405 */         Grid.sol[i][j] = Grid.sol[i][j] | Grid.answer[i][j] & 0x8;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   static void drawAkari(Graphics2D g2, int[][] puzzleArray) {
/* 412 */     Stroke normalStroke = new BasicStroke(Grid.xCell / 25.0F, 2, 2);
/* 413 */     Stroke wideStroke = new BasicStroke(Grid.xCell / 10.0F, 2, 2);
/* 414 */     g2.setStroke(normalStroke);
/*     */     
/* 416 */     RenderingHints rh = g2.getRenderingHints();
/* 417 */     rh.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
/* 418 */     rh.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
/* 419 */     g2.setRenderingHints(rh);
/*     */     
/*     */     int j;
/* 422 */     for (j = 0; j < Grid.ySz; j++) {
/* 423 */       for (int i = 0; i < Grid.xSz; i++) {
/* 424 */         int theColor; if (Def.dispWithColor.booleanValue()) {
/* 425 */           if (Def.dispErrors.booleanValue() && (((Grid.sol[i][j] & 0x1) == 1 && (Grid.answer[i][j] & 0x1) != 1) || ((Grid.sol[i][j] & 0x2) == 2 && (Grid.answer[i][j] & 0x1) == 1))) {
/*     */ 
/*     */             
/* 428 */             theColor = Op.getColorInt(Op.AK.AkError.ordinal(), Op.ak);
/*     */           
/*     */           }
/*     */           else {
/*     */             
/* 433 */             theColor = ((Grid.answer[i][j] & 0x8) == 8) ? Op.getColorInt(Op.AK.AkPattern.ordinal(), Op.ak) : (((Grid.sol[i][j] & 0x1) == 1 || (Grid.sol[i][j] & 0x4) == 4) ? Op.getColorInt(Op.AK.AkLit.ordinal(), Op.ak) : (((Grid.sol[i][j] & 0x2) == 2) ? Op.getColorInt(Op.AK.AkNoLamp.ordinal(), Op.ak) : Op.getColorInt(Op.AK.AkDark.ordinal(), Op.ak)));
/*     */           } 
/*     */         } else {
/* 436 */           theColor = ((Grid.answer[i][j] & 0x8) == 8) ? 0 : 16777215;
/* 437 */         }  g2.setColor(new Color(theColor));
/* 438 */         g2.fillRect(Grid.xOrg + i * Grid.xCell, Grid.yOrg + j * Grid.yCell, Grid.xCell, Grid.yCell);
/* 439 */         g2.setColor(Def.dispWithColor.booleanValue() ? new Color(Op.getColorInt(Op.AK.AkLines.ordinal(), Op.ak)) : Def.COLOR_BLACK);
/* 440 */         g2.drawRect(Grid.xOrg + i * Grid.xCell, Grid.yOrg + j * Grid.yCell, Grid.xCell, Grid.yCell);
/*     */       } 
/*     */     } 
/*     */     
/* 444 */     g2.setColor(Def.dispWithColor.booleanValue() ? new Color(Op.getColorInt(Op.AK.AkLines.ordinal(), Op.ak)) : Def.COLOR_BLACK);
/* 445 */     for (j = 0; j < Grid.ySz + 1; j++)
/* 446 */       g2.drawLine(Grid.xOrg, Grid.yOrg + j * Grid.yCell, Grid.xOrg + Grid.xSz * Grid.xCell, Grid.yOrg + j * Grid.yCell); 
/* 447 */     for (j = 0; j < Grid.xSz + 1; j++) {
/* 448 */       g2.drawLine(Grid.xOrg + j * Grid.xCell, Grid.yOrg, Grid.xOrg + j * Grid.xCell, Grid.yOrg + Grid.xSz * Grid.yCell);
/*     */     }
/*     */     
/* 451 */     g2.setColor(Def.dispWithColor.booleanValue() ? new Color(Op.getColorInt(Op.AK.AkLamp.ordinal(), Op.ak)) : Def.COLOR_BLACK);
/* 452 */     for (j = 0; j < Grid.ySz; j++) {
/* 453 */       for (int i = 0; i < Grid.xSz; i++) {
/* 454 */         if ((Grid.sol[i][j] & 0x1) > 0) {
/* 455 */           int t = Grid.yOrg + j * Grid.yCell + Grid.yCell / 10;
/* 456 */           int l = Grid.xOrg + i * Grid.xCell + Grid.xCell / 10;
/* 457 */           int b = Grid.yOrg + (j + 1) * Grid.yCell - Grid.yCell / 10;
/* 458 */           int r = Grid.xOrg + (i + 1) * Grid.xCell - Grid.xCell / 10;
/* 459 */           int mv = Grid.yOrg + j * Grid.yCell + Grid.yCell / 2;
/* 460 */           int mh = Grid.xOrg + i * Grid.xCell + Grid.xCell / 2;
/* 461 */           g2.drawLine(l, mv, r, mv);
/* 462 */           g2.drawLine(mh, t, mh, b);
/* 463 */           t += Grid.yCell / 10;
/* 464 */           l += Grid.xCell / 10;
/* 465 */           b -= Grid.yCell / 10;
/* 466 */           r -= Grid.xCell / 10;
/* 467 */           g2.drawLine(l, t, r, b);
/* 468 */           g2.drawLine(r, t, l, b);
/* 469 */           g2.fillArc(Grid.xOrg + i * Grid.xCell + Grid.xCell / 4, Grid.yOrg + j * Grid.yCell + Grid.xCell / 4, 2 * Grid.xCell / 4 + 2, 2 * Grid.xCell / 4 + 2, 0, 360);
/*     */         } 
/*     */       } 
/*     */     } 
/* 473 */     g2.setFont(new Font(Op.ak[Op.AK.AkFont.ordinal()], 0, 8 * Grid.yCell / 10));
/* 474 */     FontMetrics fm = g2.getFontMetrics();
/* 475 */     g2.setColor(Def.dispWithColor.booleanValue() ? new Color(Op.getColorInt(Op.AK.AkNumbers.ordinal(), Op.ak)) : Def.COLOR_WHITE);
/* 476 */     for (j = 0; j < Grid.ySz; j++) {
/* 477 */       for (int i = 0; i < Grid.xSz; i++) {
/* 478 */         char ch = (char)puzzleArray[i][j];
/* 479 */         if (Character.isDigit(ch)) {
/* 480 */           int w = fm.stringWidth("" + ch);
/* 481 */           g2.drawString("" + ch, Grid.xOrg + i * Grid.xCell + (Grid.xCell - w) / 2, Grid.yOrg + j * Grid.yCell + (Grid.yCell + fm.getAscent() - fm.getDescent()) / 2);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 486 */     if (Grid.hintIndexb > 0) {
/* 487 */       Grid.xCur = Grid.hintXb[0];
/* 488 */       Grid.yCur = Grid.hintYb[0];
/* 489 */       g2.setColor(Def.COLOR_BLUE);
/* 490 */       g2.setStroke(wideStroke);
/* 491 */       for (int i = 0; i < Grid.hintIndexb; i++)
/* 492 */         g2.drawRect(Grid.xOrg + Grid.hintXb[i] * Grid.xCell, Grid.yOrg + Grid.hintYb[i] * Grid.yCell, Grid.xCell, Grid.yCell); 
/* 493 */       Grid.hintIndexb = 0;
/*     */     } 
/*     */     
/* 496 */     g2.setStroke(new BasicStroke(1.0F));
/*     */   }
/*     */   
/*     */   static void clearSolution() {
/* 500 */     for (int j = 0; j < Grid.ySz; j++) {
/* 501 */       for (int i = 0; i < Grid.xSz; i++)
/* 502 */         Grid.sol[i][j] = Grid.sol[i][j] & 0x80; 
/*     */     } 
/*     */   }
/*     */   void restoreIfDone() {
/* 506 */     for (int j = 0; j < Grid.ySz; j++) {
/* 507 */       for (int i = 0; i < Grid.xSz; i++)
/* 508 */       { if ((Grid.sol[i][j] & 0x5) != (Grid.answer[i][j] & 0x5))
/*     */           return;  } 
/* 510 */     }  clearSolution();
/*     */   }
/*     */   
/*     */   void updateGrid(MouseEvent e) {
/* 514 */     int x = e.getX(), y = e.getY();
/*     */     
/* 516 */     if (x < Grid.xOrg || y < Grid.yOrg)
/* 517 */       return;  x = (x - Grid.xOrg) / Grid.xCell;
/* 518 */     y = (y - Grid.yOrg) / Grid.yCell;
/* 519 */     if (x >= Grid.xSz || y >= Grid.ySz)
/* 520 */       return;  if ((Grid.answer[x][y] & 0x8) == 8)
/*     */       return; 
/* 522 */     Grid.xCur = x; Grid.yCur = y;
/* 523 */     if ((Grid.sol[x][y] & 0x4) == 4)
/* 524 */       return;  Grid.sol[x][y] = (Grid.sol[x][y] + 2) % 3;
/*     */ 
/*     */     
/* 527 */     for (y = 0; y < Grid.ySz; y++) {
/* 528 */       for (x = 0; x < Grid.xSz; x++)
/* 529 */         Grid.sol[x][y] = Grid.sol[x][y] & 0xB; 
/*     */     } 
/* 531 */     for (y = 0; y < Grid.ySz; y++) {
/* 532 */       for (x = 0; x < Grid.xSz; x++) {
/* 533 */         if ((Grid.sol[x][y] & 0x1) == 1)
/* 534 */         { int i; for (i = x - 1; i >= 0 && (Grid.answer[i][y] & 0x8) != 8; i--)
/* 535 */             Grid.sol[i][y] = Grid.sol[i][y] | 0x4; 
/* 536 */           for (i = x + 1; i < Grid.xSz && (Grid.answer[i][y] & 0x8) != 8; i++)
/* 537 */             Grid.sol[i][y] = Grid.sol[i][y] | 0x4; 
/* 538 */           for (i = y - 1; i >= 0 && (Grid.answer[x][i] & 0x8) != 8; i--)
/* 539 */             Grid.sol[x][i] = Grid.sol[x][i] | 0x4; 
/* 540 */           for (i = y + 1; i < Grid.ySz && (Grid.answer[x][i] & 0x8) != 8; i++)
/* 541 */             Grid.sol[x][i] = Grid.sol[x][i] | 0x4;  } 
/*     */       } 
/* 543 */     }  (new Thread(this.solveThread)).start();
/* 544 */     restoreFrame("");
/*     */   }
/*     */ }


/* Location:              C:\Users\mustapha\Desktop\Crossword-Express.jar!\crosswordexpress\AkariSolve.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */