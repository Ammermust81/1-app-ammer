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
/*     */ public final class GokigenSolve {
/*  25 */   String[] gokigenTitle = new String[] { "Error in Partial Solution", "4 in the middle", "Zero In Corner", "One In Corner", "Zero On Edge", "Two On Edge", "Ones In Line", "Threes In Line", "Two Ones", "Forced Diagonal Type 1", "Forced Diagonal Type 2", "Avoid Loop", "No hint available" };
/*     */   
/*     */   static JFrame jfSolveGokigen;
/*     */   
/*     */   static JMenuBar menuBar;
/*     */   JMenu menu;
/*     */   JMenu submenu;
/*     */   JMenuItem menuItem;
/*     */   static JPanel pp;
/*     */   static int panelW;
/*     */   static int panelH;
/*     */   static JLabel jl1;
/*     */   static JLabel jl2;
/*     */   Timer myTimer;
/*     */   Runnable solveThread;
/*     */   int memMode;
/*  41 */   String[] gokigenHint = new String[] { "<div>Your partial solution has been checked and found to be in error in those cells which are highlighted. Under these circumstances, it is not possible to provide a trustworthy hint as to the next step in the solution.<p/>Please investigate, and correct the errors before proceeding with the solution process.</div></body>", "<div>This is a very simple case which will only ever be seen in puzzles of the lowest difficulty level. The highlighted cells surround a circle which contains the digit 4. This requires that the diagonals in all four highlighted cells must point toward the circle.</div></body>", "<div>The single highlighted cell draws your attention to a circle at a corner of the puzzle which contains a 0. This requires that the diagonal in the highlighted cell must NOT point toward the circle.</div></body>", "<div>The single highlighted cell draws your attention to a circle at a corner of the puzzle which contains a 1. This requires that the diagonal in the highlighted cell must point toward the circle.</div></body>", "<div>The highlighted cells surround a circle on the edge of the puzzle which contains a 0. This requires that the diagonals in the highlighted cells must NOT point toward the circle.</div></body>", "<div>The highlighted cells surround a circle on the edge of the puzzle which contains a 2. This requires that the diagonals in the highlighted cells must point toward the circle.</div></body>", "<div>This hint involves two circles which are adjacent to each other either horizontally or vertically, with both of them containing a 1. A small amount of doodling will quickly convince you that the diagonals in the highlighted cells must NOT point toward the circle containing 1.<p/>This rule continues to apply when an unbroken series of circles containing 2s intrudes between the circles containing the 1s.</div></body>", "<div>This hint involves two circles which are adjacent to each other either horizontally or vertically, with both of them containing a 3. A small amount of doodling will quickly convince you that the diagonals in the highlighted cells must point toward the circle containing 3.<p/>This rule continues to apply when an unbroken series of circles containing 2s intrudes between the circles containing the 3s.</div></body>", "<div>When a 2 diagonally adjacent 1s appear in the body of the puzzle, they must not be connected by a diagonal, as that would force an invalid loop around the pair.</div></body>", "<div>When the number of unsolved but highlighted cells surrounding a numbered circle is equal to the number of additional diagonals required to complete the quota for that circle, then those unsolved cells must contain a diagonal which converges on the circle.</div></body>", "<div>The highlighted cells surround a circle which has already achieved its quota of converging diagonals, and so doesn't need any more. Therefore the diagonals in the highlighted cells must NOT point toward the circle.</div></body>", "<div>One of the possible diagonals in the highlighted cell would create a loop which is not permitted in Gokigen puzzles. The other diagonal must therefore be inserted. Note that the loop to be avoided may be quite long, and could extend over a significant portion of the area of the puzzle.</div></body>", "<div>Sorry ... A hint cannot be found at this time.</div></body>" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  85 */   String gokigenSolve = "<div>To solve a GOKIGEN puzzle you must insert a diagonal line into every cell of the puzzle. The numbers at the corners of some of the cells indicates the number of diagonals which converge on that point. The diagonal lines must not combine to form a loop of any size.<p/> Solving the puzzle is a purely mouse operation. If you click the mouse when the mouse cursor is within a cell, a diagonal will be placed in the cell, and that diagonal will pass through the cell corner which is nearest to the mouse cursor. If you make a mistake, a second click in approximately the same location will remove the diagonal.<br/><br/></div><span class='m'>Menu Functions</span><br/><br/><ul><li/><span class='s'>File Menu</span><br/><ul><li/><span>Load a Puzzle</span><br/>Use this option to choose the puzzle you want to solve from the pool of GOKIGEN puzzles currently available on your computer.<p/><li/><span>Quit Solving</span><br/>Returns you to the GOKIGEN Construction screen.<p/></ul><li/><span class='s'>View Menu</span><br/><ul><li/><span>Display Options</span><br/>This leads you to a dialog box in which you can change the colors of various elements within the puzzle, and control the fonts which will be used for the puzzle's text components. You can also decide if printing of the puzzle will be done in black and white or in color.<p/></ul><li/><span class='s'>Tasks Menu</span><br/><ul><li/><span>Reveal Errors</span><br/>If you think you may have made errors, this option will show you where they are by highlighting them for a period of 1.5 seconds.<p/><li/><span>Reveal Solution</span><br/>The entire solution can be seen by selecting this option.<p/><li/><span>Begin Again</span><br/>You can restart the entire solution process at any time by selecting this option.<p/></ul><li/><span class='s'>Help Menu</span><br/><ul><li/><span>Gokigen Help</span><br/>Displays the Help screen which you are now reading.<p/></ul></ul></body>";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   GokigenSolve(JFrame jf) {
/* 123 */     this.memMode = Def.puzzleMode;
/* 124 */     Def.puzzleMode = 91;
/*     */     
/* 126 */     jfSolveGokigen = new JFrame("Gokigen");
/* 127 */     jfSolveGokigen.setSize(Op.getInt(Op.GK.GkW.ordinal(), Op.gk), Op.getInt(Op.GK.GkH.ordinal(), Op.gk));
/* 128 */     int frameX = (jf.getX() + jfSolveGokigen.getWidth() > Methods.scrW) ? (Methods.scrW - jfSolveGokigen.getWidth() - 10) : jf.getX();
/* 129 */     jfSolveGokigen.setLocation(frameX, jf.getY());
/* 130 */     jfSolveGokigen.setLayout((LayoutManager)null);
/* 131 */     jfSolveGokigen.setDefaultCloseOperation(0);
/* 132 */     jfSolveGokigen
/* 133 */       .addComponentListener(new ComponentAdapter() {
/*     */           public void componentResized(ComponentEvent ce) {
/* 135 */             int oldw = Op.getInt(Op.GK.GkW.ordinal(), Op.gk);
/* 136 */             int oldh = Op.getInt(Op.GK.GkH.ordinal(), Op.gk);
/* 137 */             Methods.frameResize(GokigenSolve.jfSolveGokigen, oldw, oldh, 500, 580);
/* 138 */             Op.setInt(Op.GK.GkW.ordinal(), GokigenSolve.jfSolveGokigen.getWidth(), Op.gk);
/* 139 */             Op.setInt(Op.GK.GkH.ordinal(), GokigenSolve.jfSolveGokigen.getHeight(), Op.gk);
/* 140 */             GokigenSolve.restoreFrame("");
/*     */           }
/*     */         });
/*     */     
/* 144 */     jfSolveGokigen
/* 145 */       .addWindowListener(new WindowAdapter() {
/*     */           public void windowClosing(WindowEvent we) {
/* 147 */             if (Def.selecting)
/* 148 */               return;  Op.saveOptions("gokigen.opt", Op.gk);
/* 149 */             GokigenSolve.this.restoreIfDone();
/* 150 */             GokigenSolve.saveGokigen(Op.gk[Op.GK.GkPuz.ordinal()]);
/* 151 */             CrosswordExpress.transfer(90, GokigenSolve.jfSolveGokigen);
/*     */           }
/*     */         });
/*     */     
/* 155 */     Methods.closeHelp();
/*     */ 
/*     */     
/* 158 */     this.solveThread = (() -> {
/*     */         for (int j = 0; j < Grid.ySz - 1; j++) {
/*     */           for (int i = 0; i < Grid.xSz - 1; i++) {
/*     */             if (Grid.sol[i][j] != Grid.letter[i][j])
/*     */               return; 
/*     */           } 
/*     */         }  Methods.congratulations(jfSolveGokigen);
/*     */       });
/* 166 */     jl1 = new JLabel(); jfSolveGokigen.add(jl1);
/* 167 */     jl2 = new JLabel(); jfSolveGokigen.add(jl2);
/*     */ 
/*     */     
/* 170 */     menuBar = new JMenuBar();
/* 171 */     menuBar.setBackground(Def.COLOR_MENUBAR);
/* 172 */     jfSolveGokigen.setJMenuBar(menuBar);
/*     */     
/* 174 */     this.menu = new JMenu("File");
/* 175 */     menuBar.add(this.menu);
/* 176 */     this.menuItem = new JMenuItem("Load a Puzzle");
/* 177 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(76, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 178 */     this.menu.add(this.menuItem);
/* 179 */     this.menuItem
/* 180 */       .addActionListener(ae -> {
/*     */           if (Def.building == 1) {
/*     */             return;
/*     */           }
/*     */           restoreIfDone();
/*     */           saveGokigen(Op.gk[Op.GK.GkPuz.ordinal()]);
/*     */           new Select(jfSolveGokigen, "gokigen", "gokigen", Op.gk, Op.GK.GkPuz.ordinal(), false);
/*     */         });
/* 188 */     this.menuItem = new JMenuItem("Quit Solving");
/* 189 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(81, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 190 */     this.menu.add(this.menuItem);
/* 191 */     this.menuItem
/* 192 */       .addActionListener(ae -> {
/*     */           Op.saveOptions("gokigen.opt", Op.gk);
/*     */           
/*     */           restoreIfDone();
/*     */           
/*     */           saveGokigen(Op.gk[Op.GK.GkPuz.ordinal()]);
/*     */           
/*     */           CrosswordExpress.transfer(90, jfSolveGokigen);
/*     */         });
/* 201 */     this.menu = new JMenu("View");
/* 202 */     menuBar.add(this.menu);
/* 203 */     this.menuItem = new JMenuItem("Display Options");
/* 204 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(89, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 205 */     this.menu.add(this.menuItem);
/* 206 */     this.menuItem
/* 207 */       .addActionListener(ae -> {
/*     */           GokigenBuild.printOptions(jfSolveGokigen, "Display Options");
/*     */ 
/*     */           
/*     */           restoreFrame("");
/*     */         });
/*     */ 
/*     */     
/* 215 */     this.menu = new JMenu("Tasks");
/* 216 */     menuBar.add(this.menu);
/*     */     
/* 218 */     ActionListener errorTimer = ae -> {
/*     */         Def.dispErrors = Boolean.valueOf(false);
/*     */         pp.repaint();
/*     */         this.myTimer.stop();
/*     */       };
/* 223 */     this.myTimer = new Timer(1500, errorTimer);
/*     */     
/* 225 */     this.menuItem = new JMenuItem("Reveal Errors");
/* 226 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(69, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 227 */     this.menu.add(this.menuItem);
/* 228 */     this.menuItem
/* 229 */       .addActionListener(ae -> {
/*     */           if (Methods.noErrors == 0) {
/*     */             this.myTimer.start();
/*     */             
/*     */             Def.dispErrors = Boolean.valueOf(true);
/*     */           } else {
/*     */             Methods.noReveal(jfSolveGokigen);
/*     */           } 
/*     */           
/*     */           pp.repaint();
/*     */         });
/* 240 */     this.menuItem = new JMenuItem("Reveal Solution");
/* 241 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(83, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 242 */     this.menu.add(this.menuItem);
/* 243 */     this.menuItem
/* 244 */       .addActionListener(ae -> {
/*     */           if (Methods.noReveal == 0) {
/*     */             for (int j = 0; j < Grid.ySz - 1; j++) {
/*     */               for (int i = 0; i < Grid.xSz - 1; i++) {
/*     */                 Grid.sol[i][j] = Grid.letter[i][j];
/*     */               }
/*     */             } 
/*     */           } else {
/*     */             Methods.noReveal(jfSolveGokigen);
/*     */           } 
/*     */           restoreFrame("");
/*     */         });
/* 256 */     this.menuItem = new JMenuItem("Request a Hint");
/* 257 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(82, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 258 */     this.menu.add(this.menuItem);
/* 259 */     this.menuItem
/* 260 */       .addActionListener(ae -> {
/*     */           Grid.findHint = Boolean.valueOf(true);
/*     */           int hintNum = GokigenBuild.nextHint();
/*     */           if (hintNum == -1) {
/*     */             hintNum = 0;
/*     */           }
/*     */           Methods.closeHelp();
/*     */           Methods.cweHelp(jfSolveGokigen, null, this.gokigenTitle[hintNum], this.gokigenHint[hintNum]);
/*     */           restoreFrame("");
/*     */           Grid.findHint = Boolean.valueOf(false);
/*     */         });
/* 271 */     this.menuItem = new JMenuItem("Begin again");
/* 272 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(66, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 273 */     this.menu.add(this.menuItem);
/* 274 */     this.menuItem
/* 275 */       .addActionListener(ae -> {
/*     */           Methods.clearGrid(Grid.sol);
/*     */ 
/*     */           
/*     */           restoreFrame("");
/*     */         });
/*     */     
/* 282 */     this.menu = new JMenu("Help");
/* 283 */     menuBar.add(this.menu);
/* 284 */     this.menuItem = new JMenuItem("Gokigen Help");
/* 285 */     this.menu.add(this.menuItem);
/* 286 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(72, 2));
/* 287 */     this.menuItem
/* 288 */       .addActionListener(ae -> Methods.cweHelp(jfSolveGokigen, null, "Solving Gokigen Puzzles", this.gokigenSolve));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 294 */     loadGokigen(Op.gk[Op.GK.GkPuz.ordinal()]);
/* 295 */     pp = new GokigenSolvePP(0, 37);
/* 296 */     jfSolveGokigen.add(pp);
/*     */     
/* 298 */     pp
/* 299 */       .addMouseListener(new MouseAdapter() {
/*     */           public void mousePressed(MouseEvent e) {
/* 301 */             GokigenSolve.this.updateGrid(e);
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 306 */     pp
/* 307 */       .addMouseMotionListener(new MouseAdapter() {
/*     */           public void mouseMoved(MouseEvent e) {
/* 309 */             if (Def.isMac) {
/* 310 */               GokigenSolve.jfSolveGokigen.setResizable((GokigenSolve.jfSolveGokigen.getWidth() - e.getX() < 15 && GokigenSolve.jfSolveGokigen
/* 311 */                   .getHeight() - e.getY() < 95));
/*     */             }
/*     */           }
/*     */         });
/*     */     
/* 316 */     restoreFrame("");
/*     */   }
/*     */   
/*     */   static void restoreFrame(String hint) {
/* 320 */     jfSolveGokigen.setVisible(true);
/* 321 */     Insets insets = jfSolveGokigen.getInsets();
/* 322 */     panelW = jfSolveGokigen.getWidth() - insets.left + insets.right;
/* 323 */     panelH = jfSolveGokigen.getHeight() - insets.top + insets.bottom + 37 + menuBar.getHeight();
/* 324 */     pp.setSize(panelW, panelH);
/* 325 */     jfSolveGokigen.requestFocusInWindow();
/* 326 */     pp.repaint();
/* 327 */     Methods.infoPanel(jl1, jl2, "Solve Gokigen", (hint.length() > 0) ? ("  Hint : " + hint) : ("Puzzle : " + Op.gk[Op.GK.GkPuz.ordinal()]), panelW);
/*     */   }
/*     */   
/*     */   static void setSizesAndOffsets(int x, int y, int width, int height) {
/* 331 */     int i = (width - 10) / Grid.xSz;
/* 332 */     int j = (height - 10) / Grid.ySz;
/* 333 */     Grid.xCell = Grid.yCell = (i < j) ? i : j;
/* 334 */     Grid.xOrg = x + (Grid.xCell + width - Grid.xSz * Grid.xCell) / 2;
/* 335 */     Grid.yOrg = y + (Grid.yCell + height - Grid.ySz * Grid.yCell) / 2;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void saveGokigen(String gokigenName) {
/*     */     try {
/* 342 */       DataOutputStream dataOut = new DataOutputStream(new FileOutputStream("gokigen/" + gokigenName));
/* 343 */       dataOut.writeInt(Grid.xSz);
/* 344 */       dataOut.writeInt(Grid.ySz);
/* 345 */       dataOut.writeByte(Methods.noReveal);
/* 346 */       dataOut.writeByte(Methods.noErrors);
/* 347 */       for (int i = 0; i < 54; i++)
/* 348 */         dataOut.writeByte(0); 
/* 349 */       for (int j = 0; j < Grid.ySz; j++) {
/* 350 */         for (int k = 0; k < Grid.xSz; k++) {
/* 351 */           dataOut.writeInt(Grid.sol[k][j]);
/* 352 */           dataOut.writeInt(Grid.letter[k][j]);
/* 353 */           dataOut.writeInt(Grid.copy[k][j]);
/*     */         } 
/* 355 */       }  dataOut.writeUTF(Methods.puzzleTitle);
/* 356 */       dataOut.writeUTF(Methods.author);
/* 357 */       dataOut.writeUTF(Methods.copyright);
/* 358 */       dataOut.writeUTF(Methods.puzzleNumber);
/* 359 */       dataOut.writeUTF(Methods.puzzleNotes);
/* 360 */       dataOut.close();
/*     */     }
/* 362 */     catch (IOException exc) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void loadGokigen(String gokigenName) {
/*     */     try {
/* 370 */       File fl = new File("gokigen/" + gokigenName);
/* 371 */       if (!fl.exists()) {
/* 372 */         fl = new File("gokigen/");
/* 373 */         String[] s = fl.list(); int k;
/* 374 */         for (k = 0; k < s.length && (
/* 375 */           s[k].lastIndexOf(".gokigen") == -1 || s[k].charAt(0) == '.'); k++);
/*     */         
/* 377 */         gokigenName = s[k];
/* 378 */         Op.gk[Op.GK.GkPuz.ordinal()] = gokigenName;
/*     */       } 
/*     */       
/* 381 */       DataInputStream dataIn = new DataInputStream(new FileInputStream("gokigen/" + gokigenName));
/* 382 */       Grid.xSz = dataIn.readInt();
/* 383 */       Grid.ySz = dataIn.readInt();
/* 384 */       Methods.noReveal = dataIn.readByte();
/* 385 */       Methods.noErrors = dataIn.readByte(); int i;
/* 386 */       for (i = 0; i < 54; i++)
/* 387 */         dataIn.readByte(); 
/* 388 */       for (int j = 0; j < Grid.ySz; j++) {
/* 389 */         for (i = 0; i < Grid.xSz; i++) {
/* 390 */           Grid.sol[i][j] = dataIn.readInt();
/* 391 */           Grid.letter[i][j] = dataIn.readInt();
/* 392 */           Grid.copy[i][j] = dataIn.readInt();
/*     */         } 
/* 394 */       }  Methods.puzzleTitle = dataIn.readUTF();
/* 395 */       Methods.author = dataIn.readUTF();
/* 396 */       Methods.copyright = dataIn.readUTF();
/* 397 */       Methods.puzzleNumber = dataIn.readUTF();
/* 398 */       Methods.puzzleNotes = dataIn.readUTF();
/* 399 */       dataIn.close();
/*     */     }
/* 401 */     catch (IOException exc) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void drawGokigen(Graphics2D g2, int[][] puzzleArray) {
/* 409 */     Stroke wideStrokeB = new BasicStroke(Grid.xCell / 25.0F, 0, 1);
/* 410 */     Stroke wideStrokeS = new BasicStroke(Grid.xCell / 25.0F, 2, 1);
/* 411 */     Stroke wideStrokeR = new BasicStroke(Grid.xCell / 25.0F, 1, 1);
/*     */     
/* 413 */     RenderingHints rh = g2.getRenderingHints();
/* 414 */     rh.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
/* 415 */     rh.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
/* 416 */     g2.setRenderingHints(rh);
/*     */     
/*     */     int j;
/* 419 */     for (j = 0; j < Grid.ySz - 1; j++) {
/* 420 */       for (int i = 0; i < Grid.xSz - 1; i++) {
/* 421 */         g2.setColor(new Color(Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.GK.GkCell.ordinal(), Op.gk) : 16777215));
/* 422 */         g2.fillRect(Grid.xOrg + i * Grid.xCell, Grid.yOrg + j * Grid.yCell, Grid.xCell, Grid.yCell);
/*     */       } 
/*     */     } 
/*     */     
/* 426 */     for (j = 0; j < Grid.ySz - 1; j++) {
/* 427 */       for (int i = 0; i < Grid.xSz - 1; i++) {
/* 428 */         if (Def.dispWithColor.booleanValue())
/* 429 */         { if (Def.dispErrors.booleanValue() && Grid.sol[i][j] != 0 && Grid.sol[i][j] != Grid.letter[i][j]) {
/* 430 */             g2.setColor(new Color(Op.getColorInt(Op.GK.GkError.ordinal(), Op.gk)));
/*     */           } else {
/*     */             
/* 433 */             g2.setColor(new Color(Op.getColorInt(Op.GK.GkDiagonal.ordinal(), Op.gk)));
/*     */           }  }
/* 435 */         else { g2.setColor(Def.COLOR_BLACK); }
/* 436 */          g2.setStroke(wideStrokeR);
/* 437 */         if (puzzleArray[i][j] == 1)
/* 438 */           g2.drawLine(Grid.xOrg + i * Grid.xCell, Grid.yOrg + (j + 1) * Grid.yCell, Grid.xOrg + (i + 1) * Grid.xCell, Grid.yOrg + j * Grid.yCell); 
/* 439 */         if (puzzleArray[i][j] == 2)
/* 440 */           g2.drawLine(Grid.xOrg + i * Grid.xCell, Grid.yOrg + j * Grid.yCell, Grid.xOrg + (i + 1) * Grid.xCell, Grid.yOrg + (j + 1) * Grid.yCell); 
/* 441 */         g2.setStroke(wideStrokeS);
/* 442 */         g2.setColor(new Color(Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.GK.GkLine.ordinal(), Op.gk) : 0));
/*     */       } 
/*     */     } 
/*     */     
/* 446 */     for (j = 0; j < Grid.ySz; j++)
/* 447 */       g2.drawLine(Grid.xOrg, Grid.yOrg + j * Grid.yCell, Grid.xOrg + (Grid.xSz - 1) * Grid.xCell, Grid.yOrg + j * Grid.yCell); 
/* 448 */     for (j = 0; j < Grid.xSz; j++) {
/* 449 */       g2.drawLine(Grid.xOrg + j * Grid.xCell, Grid.yOrg, Grid.xOrg + j * Grid.xCell, Grid.yOrg + (Grid.ySz - 1) * Grid.yCell);
/*     */     }
/*     */     
/* 452 */     if (Grid.hintIndexb > 0) {
/* 453 */       Grid.xCur = Grid.hintXb[0];
/* 454 */       Grid.yCur = Grid.hintYb[0];
/* 455 */       g2.setColor(Def.COLOR_BLUE);
/* 456 */       g2.setStroke(wideStrokeR);
/* 457 */       for (int i = 0; i < Grid.hintIndexb; i++)
/* 458 */         g2.drawRect(Grid.xOrg + Grid.hintXb[i] * Grid.xCell, Grid.yOrg + Grid.hintYb[i] * Grid.yCell, Grid.xCell, Grid.yCell); 
/* 459 */       Grid.hintIndexb = 0;
/*     */     } 
/*     */ 
/*     */     
/* 463 */     g2.setFont(new Font(Op.gk[Op.GK.GkFont.ordinal()], 0, 10 * Grid.yCell / 20));
/* 464 */     FontMetrics fm = g2.getFontMetrics();
/* 465 */     for (j = 0; j < Grid.ySz; j++) {
/* 466 */       for (int i = 0; i < Grid.xSz; i++) {
/* 467 */         if (Grid.copy[i][j] < 5) {
/* 468 */           g2.setColor(new Color(Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.GK.GkCircle.ordinal(), Op.gk) : 16777215));
/* 469 */           g2.fillArc(Grid.xOrg + i * Grid.xCell - Grid.xCell / 3, Grid.yOrg + j * Grid.yCell - Grid.xCell / 3, 2 * Grid.xCell / 3, 2 * Grid.xCell / 3, 0, 360);
/* 470 */           g2.setColor(new Color(Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.GK.GkLine.ordinal(), Op.gk) : 0));
/* 471 */           g2.drawArc(Grid.xOrg + i * Grid.xCell - Grid.xCell / 3, Grid.yOrg + j * Grid.yCell - Grid.xCell / 3, 2 * Grid.xCell / 3, 2 * Grid.xCell / 3, 0, 360);
/* 472 */           int w = fm.stringWidth("" + Grid.copy[i][j]);
/* 473 */           g2.setColor(new Color(Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.GK.GkNumber.ordinal(), Op.gk) : 0));
/* 474 */           g2.drawString("" + Grid.copy[i][j], Grid.xOrg + i * Grid.xCell - w / 2, Grid.yOrg + j * Grid.yCell + (fm.getAscent() - fm.getDescent()) / 2);
/*     */         } 
/*     */       } 
/* 477 */     }  g2.setStroke(new BasicStroke(1.0F));
/*     */   }
/*     */   
/*     */   void restoreIfDone() {
/* 481 */     for (int j = 0; j < Grid.ySz - 1; j++) {
/* 482 */       for (int i = 0; i < Grid.xSz - 1; i++)
/* 483 */       { if (Grid.sol[i][j] == 0)
/*     */           return;  } 
/* 485 */     }  Methods.clearGrid(Grid.sol);
/*     */   }
/*     */   
/*     */   void checkForCompletion() {
/* 489 */     (new Thread(this.solveThread)).start();
/*     */   }
/*     */   
/*     */   void updateGrid(MouseEvent e) {
/* 493 */     int x = e.getX(), y = e.getY();
/*     */     
/* 495 */     if (x < Grid.xOrg || y < Grid.yOrg)
/* 496 */       return;  int i = (x - Grid.xOrg) / Grid.xCell;
/* 497 */     int j = (y - Grid.yOrg) / Grid.yCell;
/* 498 */     if (i >= Grid.xSz - 1 || j >= Grid.ySz - 1)
/* 499 */       return;  int lx = x - Grid.xOrg + i * Grid.xCell;
/* 500 */     int ly = y - Grid.yOrg + j * Grid.yCell;
/* 501 */     if ((lx > Grid.xCell / 2 && ly < Grid.yCell / 2) || (lx < Grid.xCell / 2 && ly > Grid.yCell / 2)) {
/* 502 */       Grid.sol[i][j] = (Grid.sol[i][j] == 1) ? 0 : 1;
/*     */     } else {
/* 504 */       Grid.sol[i][j] = (Grid.sol[i][j] == 2) ? 0 : 2;
/* 505 */     }  checkForCompletion();
/* 506 */     restoreFrame("");
/*     */   }
/*     */ }


/* Location:              C:\Users\mustapha\Desktop\Crossword-Express.jar!\crosswordexpress\GokigenSolve.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */