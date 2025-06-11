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
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.awt.event.MouseAdapter;
/*     */ import java.awt.event.MouseEvent;
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
/*     */ public final class FillominoSolve {
/*  25 */   String fillominoSolve = "<div>A FILLOMINO puzzle consists of a square grid in which some of the squares contain a single digit number. To complete the puzzle, a Solver must place numbers in the blank squares so that the puzzle is divided into zones with the area of each zone equal to the number contained within its squares. The zones may take on any shape, but zones having the same area may not touch each other.<p/> When you have determined what number should be contained within a square, you can move the cursor to that square using the arrow keys, or you can mouse click directly into the square. Any number typed on the keyboard will then be placed into that square.<p/> If you make a mistake, you can overtype it with a different number, or you can remove it entirely using the Space Bar.<br/><br/></div><span class='parhead'>Menu Functions</span><ul><li/><span class='s'>File Menu</span><ul><li/><span>Load a Puzzle</span><br/>Use this option to choose the puzzle you want to solve from the pool of FILLOMINO puzzles currently available on your computer.<p/><li/><span>Quit Solving</span><br/>Returns you to the FILLOMINO Construction screen.</ul><li/><span class='s'>View Menu</span><ul><li/><span>Display Options</span><br/>This leads you to a dialog box in which you can change the colors of various elements within the puzzle, and control the fonts which will be used for the puzzle's text components. You can also decide if printing of the puzzle will be done in black and white or in color.</ul><li/><span class='s'>Tasks Menu</span><ul><li/><span>Reveal One Number</span><br/>If you need a little help to get started, this option will place the correct number into the current focus cell.<p/><li/><span>Reveal Errors</span><br/>If you think you may have made errors, this option will show you where they are by highlighting them for a period of 1.5 seconds.<p/><li/><span>Reveal Solution</span><br/>The entire solution can be seen by selecting this option.<p/><li/><span>Begin Again</span><br/>You can restart the entire solution process at any time by selecting this option.</ul><li/><span class='s'>Help Menu</span><ul><li/><span>Fillomino Help</span><br/>Displays the Help screen which you are now reading.</ul></ul></body>";
/*     */ 
/*     */   
/*     */   static JFrame jfSolveFillomino;
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
/*     */   
/*     */   static int panelH;
/*     */ 
/*     */   
/*     */   static JLabel jl1;
/*     */ 
/*     */   
/*     */   static JLabel jl2;
/*     */ 
/*     */   
/*     */   Timer myTimer;
/*     */   
/*     */   Runnable solveThread;
/*     */   
/*     */   int memMode;
/*     */ 
/*     */   
/*     */   FillominoSolve(JFrame jf) {
/*  66 */     this.memMode = Def.puzzleMode;
/*  67 */     Def.puzzleMode = 71;
/*  68 */     Def.dispCursor = Boolean.valueOf(true);
/*     */     
/*  70 */     jfSolveFillomino = new JFrame("Fillomino");
/*  71 */     jfSolveFillomino.setSize(Op.getInt(Op.FI.FiW.ordinal(), Op.fi), Op.getInt(Op.FI.FiH.ordinal(), Op.fi));
/*  72 */     int frameX = (jf.getX() + jfSolveFillomino.getWidth() > Methods.scrW) ? (Methods.scrW - jfSolveFillomino.getWidth() - 10) : jf.getX();
/*  73 */     jfSolveFillomino.setLocation(frameX, jf.getY());
/*  74 */     jfSolveFillomino.setLayout((LayoutManager)null);
/*  75 */     jfSolveFillomino.setDefaultCloseOperation(0);
/*  76 */     jfSolveFillomino
/*  77 */       .addComponentListener(new ComponentAdapter() {
/*     */           public void componentResized(ComponentEvent ce) {
/*  79 */             int oldw = Op.getInt(Op.FI.FiW.ordinal(), Op.fi);
/*  80 */             int oldh = Op.getInt(Op.FI.FiH.ordinal(), Op.fi);
/*  81 */             Methods.frameResize(FillominoSolve.jfSolveFillomino, oldw, oldh, 500, 580);
/*  82 */             Op.setInt(Op.FI.FiW.ordinal(), FillominoSolve.jfSolveFillomino.getWidth(), Op.fi);
/*  83 */             Op.setInt(Op.FI.FiH.ordinal(), FillominoSolve.jfSolveFillomino.getHeight(), Op.fi);
/*  84 */             FillominoSolve.restoreFrame();
/*     */           }
/*     */         });
/*     */     
/*  88 */     jfSolveFillomino
/*  89 */       .addWindowListener(new WindowAdapter() {
/*     */           public void windowClosing(WindowEvent we) {
/*  91 */             if (Def.selecting)
/*  92 */               return;  Op.saveOptions("fillomino.opt", Op.fi);
/*  93 */             FillominoSolve.this.restoreIfDone();
/*  94 */             FillominoSolve.saveFillomino(Op.fi[Op.FI.FiPuz.ordinal()]);
/*  95 */             CrosswordExpress.transfer(70, FillominoSolve.jfSolveFillomino);
/*     */           }
/*     */         });
/*     */     
/*  99 */     Methods.closeHelp();
/*     */ 
/*     */     
/* 102 */     this.solveThread = (() -> {
/*     */         for (int j = 0; j < Grid.ySz; j++) {
/*     */           for (int i = 0; i < Grid.xSz; i++) {
/*     */             if (Grid.sol[i][j] != Grid.letter[i][j])
/*     */               return; 
/*     */           } 
/*     */         }  Methods.congratulations(jfSolveFillomino);
/*     */       });
/* 110 */     jl1 = new JLabel(); jfSolveFillomino.add(jl1);
/* 111 */     jl2 = new JLabel(); jfSolveFillomino.add(jl2);
/*     */ 
/*     */     
/* 114 */     menuBar = new JMenuBar();
/* 115 */     menuBar.setBackground(Def.COLOR_MENUBAR);
/* 116 */     jfSolveFillomino.setJMenuBar(menuBar);
/*     */     
/* 118 */     this.menu = new JMenu("File");
/* 119 */     menuBar.add(this.menu);
/* 120 */     this.menuItem = new JMenuItem("Load a Puzzle");
/* 121 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(76, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 122 */     this.menu.add(this.menuItem);
/* 123 */     this.menuItem
/* 124 */       .addActionListener(ae -> {
/*     */           if (Def.building == 1) {
/*     */             return;
/*     */           }
/*     */           restoreIfDone();
/*     */           saveFillomino(Op.fi[Op.FI.FiPuz.ordinal()]);
/*     */           new Select(jfSolveFillomino, "fillomino", "fillomino", Op.fi, Op.FI.FiPuz.ordinal(), false);
/*     */         });
/* 132 */     this.menuItem = new JMenuItem("Quit Solving");
/* 133 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(81, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 134 */     this.menu.add(this.menuItem);
/* 135 */     this.menuItem
/* 136 */       .addActionListener(ae -> {
/*     */           Op.saveOptions("fillomino.opt", Op.fi);
/*     */           
/*     */           restoreIfDone();
/*     */           
/*     */           saveFillomino(Op.fi[Op.FI.FiPuz.ordinal()]);
/*     */           
/*     */           CrosswordExpress.transfer(70, jfSolveFillomino);
/*     */         });
/* 145 */     this.menu = new JMenu("View");
/* 146 */     menuBar.add(this.menu);
/* 147 */     this.menuItem = new JMenuItem("Display Options");
/* 148 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(89, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 149 */     this.menu.add(this.menuItem);
/* 150 */     this.menuItem
/* 151 */       .addActionListener(ae -> {
/*     */           FillominoBuild.printOptions(jfSolveFillomino, "Display Options");
/*     */ 
/*     */           
/*     */           restoreFrame();
/*     */         });
/*     */     
/* 158 */     this.menu = new JMenu("Tasks");
/* 159 */     menuBar.add(this.menu);
/* 160 */     this.menuItem = new JMenuItem("Reveal One Number");
/* 161 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(79, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 162 */     this.menu.add(this.menuItem);
/* 163 */     this.menuItem
/* 164 */       .addActionListener(ae -> {
/*     */           for (int j = 0; j < Grid.ySz; j++) {
/*     */             for (int i = 0; i < Grid.xSz; i++) {
/*     */               Grid.sol[Grid.xCur][Grid.yCur] = Grid.letter[Grid.xCur][Grid.yCur];
/*     */             }
/*     */           } 
/*     */           
/*     */           restoreFrame();
/*     */         });
/* 173 */     ActionListener errorTimer = ae -> {
/*     */         Def.dispErrors = Boolean.valueOf(false);
/*     */         pp.repaint();
/*     */         this.myTimer.stop();
/*     */       };
/* 178 */     this.myTimer = new Timer(1500, errorTimer);
/*     */     
/* 180 */     this.menuItem = new JMenuItem("Reveal Errors");
/* 181 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(69, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 182 */     this.menu.add(this.menuItem);
/* 183 */     this.menuItem
/* 184 */       .addActionListener(ae -> {
/*     */           if (Methods.noErrors == 0) {
/*     */             this.myTimer.start();
/*     */             
/*     */             Def.dispErrors = Boolean.valueOf(true);
/*     */           } else {
/*     */             Methods.noReveal(jfSolveFillomino);
/*     */           } 
/*     */           
/*     */           pp.repaint();
/*     */         });
/* 195 */     this.menuItem = new JMenuItem("Reveal Solution");
/* 196 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(83, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 197 */     this.menu.add(this.menuItem);
/* 198 */     this.menuItem
/* 199 */       .addActionListener(ae -> {
/*     */           if (Methods.noReveal == 0) {
/*     */             for (int j = 0; j < Grid.ySz; j++) {
/*     */               for (int i = 0; i < Grid.xSz; i++) {
/*     */                 Grid.sol[i][j] = Grid.letter[i][j];
/*     */               }
/*     */             } 
/*     */           } else {
/*     */             Methods.noReveal(jfSolveFillomino);
/*     */           } 
/*     */           restoreFrame();
/*     */         });
/* 211 */     this.menuItem = new JMenuItem("Begin again");
/* 212 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(66, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 213 */     this.menu.add(this.menuItem);
/* 214 */     this.menuItem
/* 215 */       .addActionListener(ae -> {
/*     */           clearSolution();
/*     */ 
/*     */           
/*     */           restoreFrame();
/*     */         });
/*     */     
/* 222 */     this.menu = new JMenu("Help");
/* 223 */     menuBar.add(this.menu);
/* 224 */     this.menuItem = new JMenuItem("Fillomino Help");
/* 225 */     this.menu.add(this.menuItem);
/* 226 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(72, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 227 */     this.menuItem
/* 228 */       .addActionListener(ae -> Methods.cweHelp(jfSolveFillomino, null, "Solving Fillomino Puzzles", this.fillominoSolve));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 234 */     loadFillomino(Op.fi[Op.FI.FiPuz.ordinal()]);
/* 235 */     pp = new FillominoSolvePP(0, 37);
/* 236 */     jfSolveFillomino.add(pp);
/* 237 */     pp
/* 238 */       .addMouseListener(new MouseAdapter() {
/*     */           public void mousePressed(MouseEvent e) {
/* 240 */             FillominoSolve.this.updateGrid(e);
/*     */           }
/*     */         });
/*     */     
/* 244 */     pp
/* 245 */       .addMouseMotionListener(new MouseAdapter() {
/*     */           public void mouseMoved(MouseEvent e) {
/* 247 */             if (Def.isMac) {
/* 248 */               FillominoSolve.jfSolveFillomino.setResizable((FillominoSolve.jfSolveFillomino.getWidth() - e.getX() < 15 && FillominoSolve.jfSolveFillomino
/* 249 */                   .getHeight() - e.getY() < 95));
/*     */             }
/*     */           }
/*     */         });
/*     */     
/* 254 */     jfSolveFillomino
/* 255 */       .addKeyListener(new KeyAdapter() {
/*     */           public void keyPressed(KeyEvent e) {
/* 257 */             FillominoSolve.this.handleKeyPressed(e);
/*     */           }
/*     */         });
/* 260 */     restoreFrame();
/*     */   }
/*     */   
/*     */   static void restoreFrame() {
/* 264 */     jfSolveFillomino.setVisible(true);
/* 265 */     Insets insets = jfSolveFillomino.getInsets();
/* 266 */     panelW = jfSolveFillomino.getWidth() - insets.left + insets.right;
/* 267 */     panelH = jfSolveFillomino.getHeight() - insets.top + insets.bottom + 37 + menuBar.getHeight();
/* 268 */     pp.setSize(panelW, panelH);
/* 269 */     setSizesAndOffsets(0, 0, panelW, panelH, 20, false);
/* 270 */     jfSolveFillomino.requestFocusInWindow();
/* 271 */     pp.repaint();
/* 272 */     Methods.infoPanel(jl1, jl2, "Solve Fillomino", "Puzzle : " + Op.fi[Op.FI.FiPuz.ordinal()], panelW);
/*     */   }
/*     */   
/*     */   static void setSizesAndOffsets(int x, int y, int width, int height, int inset, boolean print) {
/* 276 */     int i = (width - inset) / Grid.xSz;
/* 277 */     int j = (height - inset) / Grid.ySz;
/* 278 */     Grid.xCell = Grid.yCell = (i < j) ? i : j;
/* 279 */     Grid.xOrg = x + (print ? ((width - Grid.xSz * Grid.xCell) / 2) : 10);
/* 280 */     Grid.yOrg = y + (print ? ((height - Grid.ySz * Grid.yCell) / 2) : 10);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void saveFillomino(String fillominoName) {
/*     */     try {
/* 286 */       DataOutputStream dataOut = new DataOutputStream(new FileOutputStream("fillomino/" + fillominoName));
/* 287 */       dataOut.writeInt(Grid.xSz);
/* 288 */       dataOut.writeInt(Grid.ySz);
/* 289 */       dataOut.writeByte(Methods.noReveal);
/* 290 */       dataOut.writeByte(Methods.noErrors);
/* 291 */       for (int i = 0; i < 54; i++)
/* 292 */         dataOut.writeByte(0); 
/* 293 */       for (int j = 0; j < Grid.ySz; j++) {
/* 294 */         for (int k = 0; k < Grid.xSz; k++) {
/* 295 */           dataOut.writeInt(Grid.sol[k][j]);
/* 296 */           dataOut.writeInt(Grid.letter[k][j]);
/* 297 */           dataOut.writeInt(Grid.copy[k][j]);
/*     */         } 
/* 299 */       }  dataOut.writeUTF(Methods.puzzleTitle);
/* 300 */       dataOut.writeUTF(Methods.author);
/* 301 */       dataOut.writeUTF(Methods.copyright);
/* 302 */       dataOut.writeUTF(Methods.puzzleNumber);
/* 303 */       dataOut.writeUTF(Methods.puzzleNotes);
/* 304 */       dataOut.close();
/*     */     }
/* 306 */     catch (IOException exc) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void loadFillomino(String fillominoName) {
/*     */     try {
/* 314 */       File fl = new File("fillomino/" + fillominoName);
/* 315 */       if (!fl.exists()) {
/* 316 */         fl = new File("fillomino/");
/* 317 */         String[] s = fl.list(); int k;
/* 318 */         for (k = 0; k < s.length && (
/* 319 */           s[k].lastIndexOf(".fillomino") == -1 || s[k].charAt(0) == '.'); k++);
/*     */         
/* 321 */         fillominoName = s[k];
/* 322 */         Op.fi[Op.FI.FiPuz.ordinal()] = fillominoName;
/*     */       } 
/*     */ 
/*     */       
/* 326 */       DataInputStream dataIn = new DataInputStream(new FileInputStream("fillomino/" + fillominoName));
/* 327 */       Grid.xSz = dataIn.readInt();
/* 328 */       Grid.ySz = dataIn.readInt();
/* 329 */       Methods.noReveal = dataIn.readByte();
/* 330 */       Methods.noErrors = dataIn.readByte(); int i;
/* 331 */       for (i = 0; i < 54; i++)
/* 332 */         dataIn.readByte(); 
/* 333 */       for (int j = 0; j < Grid.ySz; j++) {
/* 334 */         for (i = 0; i < Grid.xSz; i++) {
/* 335 */           Grid.sol[i][j] = dataIn.readInt();
/* 336 */           Grid.letter[i][j] = dataIn.readInt();
/* 337 */           Grid.copy[i][j] = dataIn.readInt();
/*     */         } 
/* 339 */       }  Methods.puzzleTitle = dataIn.readUTF();
/* 340 */       Methods.author = dataIn.readUTF();
/* 341 */       Methods.copyright = dataIn.readUTF();
/* 342 */       Methods.puzzleNumber = dataIn.readUTF();
/* 343 */       Methods.puzzleNotes = dataIn.readUTF();
/* 344 */       dataIn.close();
/*     */     }
/* 346 */     catch (IOException exc) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void drawFillomino(Graphics2D g2, int[][] puzzleArray) {
/* 353 */     boolean isError = false;
/*     */ 
/*     */     
/* 356 */     Stroke normalStroke = new BasicStroke(Grid.xCell / 25.0F, 2, 2);
/* 357 */     Stroke wideStroke = new BasicStroke(Grid.xCell / 10.0F, 2, 2);
/* 358 */     g2.setStroke(normalStroke);
/*     */     
/* 360 */     RenderingHints rh = g2.getRenderingHints();
/* 361 */     rh.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
/* 362 */     rh.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
/* 363 */     g2.setRenderingHints(rh);
/*     */     
/*     */     int j;
/* 366 */     for (j = 0; j < Grid.ySz; j++) {
/* 367 */       for (int i = 0; i < Grid.xSz; i++) {
/* 368 */         if (Grid.sol[i][j] != 0 && Grid.sol[i][j] != Grid.letter[i][j])
/* 369 */           isError = true; 
/*     */       } 
/*     */     } 
/* 372 */     for (j = 0; j < Grid.ySz; j++) {
/* 373 */       for (int i = 0; i < Grid.xSz; i++) {
/* 374 */         int q = puzzleArray[i][j];
/* 375 */         if (q != 88) {
/* 376 */           int theColor; if (Def.dispWithColor.booleanValue())
/* 377 */           { if (Def.dispErrors.booleanValue()) {
/* 378 */               if (Grid.sol[i][j] != 0 && Grid.sol[i][j] != Grid.letter[i][j]) {
/* 379 */                 theColor = Op.getColorInt(Op.FI.FiError.ordinal(), Op.fi);
/*     */               }
/* 381 */               else if (isError) {
/* 382 */                 theColor = 16777215;
/*     */               } else {
/* 384 */                 theColor = (q == 0) ? Op.getColorInt(Op.FI.FiEmpty.ordinal(), Op.fi) : Op.getColorInt(Op.FI.Fi1.ordinal() + q - 49, Op.fi);
/*     */               } 
/*     */             } else {
/*     */               
/* 388 */               theColor = Def.dispWithColor.booleanValue() ? ((q == 0) ? Op.getColorInt(Op.FI.FiEmpty.ordinal(), Op.fi) : Op.getColorInt(Op.FI.Fi1.ordinal() + q - 49, Op.fi)) : 16777215;
/*     */             }  }
/* 390 */           else { theColor = 16777215; }
/* 391 */            g2.setColor(new Color(theColor));
/* 392 */           g2.fillRect(Grid.xOrg + i * Grid.xCell, Grid.yOrg + j * Grid.yCell, Grid.xCell, Grid.yCell);
/* 393 */           g2.setColor(new Color(Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.FI.FiLines.ordinal(), Op.fi) : 0));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 399 */     for (j = 0; j < Grid.ySz + 1; j++)
/* 400 */       g2.drawLine(Grid.xOrg, Grid.yOrg + j * Grid.yCell, Grid.xOrg + Grid.xSz * Grid.xCell, Grid.yOrg + j * Grid.yCell); 
/* 401 */     for (j = 0; j < Grid.xSz + 1; j++) {
/* 402 */       g2.drawLine(Grid.xOrg + j * Grid.xCell, Grid.yOrg, Grid.xOrg + j * Grid.xCell, Grid.yOrg + Grid.xSz * Grid.yCell);
/*     */     }
/*     */     
/* 405 */     g2.setFont(new Font(Op.fi[Op.FI.FiFont.ordinal()], 0, 8 * Grid.yCell / 10));
/* 406 */     FontMetrics fm = g2.getFontMetrics();
/* 407 */     g2.setColor(new Color(Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.FI.FiNumber.ordinal(), Op.fi) : 0));
/* 408 */     for (j = 0; j < Grid.ySz; j++) {
/* 409 */       for (int i = 0; i < Grid.xSz; i++) {
/* 410 */         char ch = (char)puzzleArray[i][j];
/* 411 */         if (Character.isDigit(ch)) {
/* 412 */           int w = fm.stringWidth("" + ch);
/* 413 */           g2.drawString("" + ch, Grid.xOrg + i * Grid.xCell + (Grid.xCell - w) / 2, Grid.yOrg + j * Grid.yCell + (Grid.yCell + fm.getAscent() - fm.getDescent()) / 2);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 418 */     if (Def.dispCursor.booleanValue()) {
/* 419 */       g2.setStroke(wideStroke);
/* 420 */       g2.setColor(Def.COLOR_RED);
/* 421 */       g2.drawRect(Grid.xOrg + Grid.xCur * Grid.xCell, Grid.yOrg + Grid.yCur * Grid.yCell, Grid.xCell, Grid.yCell);
/*     */     } 
/*     */     
/* 424 */     g2.setStroke(new BasicStroke(1.0F));
/*     */   }
/*     */   
/*     */   static void clearSolution() {
/* 428 */     for (int y = 0; y < Grid.ySz; y++) {
/* 429 */       for (int x = 0; x < Grid.xSz; x++)
/* 430 */         Grid.sol[x][y] = Grid.copy[x][y]; 
/*     */     } 
/*     */   }
/*     */   void restoreIfDone() {
/* 434 */     for (int j = 0; j < Grid.ySz; j++) {
/* 435 */       for (int i = 0; i < Grid.xSz; i++)
/* 436 */       { if (Grid.sol[i][j] == 0)
/*     */           return;  } 
/* 438 */     }  clearSolution();
/*     */   }
/*     */   
/*     */   void updateGrid(MouseEvent e) {
/* 442 */     int x = e.getX(), y = e.getY();
/* 443 */     if (x < Grid.xOrg || y < Grid.yOrg)
/* 444 */       return;  x = (x - Grid.xOrg) / Grid.xCell;
/* 445 */     y = (y - Grid.yOrg) / Grid.yCell;
/* 446 */     if (x >= Grid.xSz || y >= Grid.ySz)
/*     */       return; 
/* 448 */     Grid.xCur = x; Grid.yCur = y;
/* 449 */     restoreFrame();
/*     */   }
/*     */   void handleKeyPressed(KeyEvent e) {
/*     */     char ch;
/* 453 */     if (e.isAltDown())
/* 454 */       return;  switch (e.getKeyCode()) { case 38:
/* 455 */         if (Grid.yCur > 0) Grid.yCur--;  break;
/* 456 */       case 40: if (Grid.yCur < Grid.ySz - 1) Grid.yCur++;  break;
/* 457 */       case 37: if (Grid.xCur > 0) Grid.xCur--;  break;
/* 458 */       case 39: if (Grid.xCur < Grid.xSz - 1) Grid.xCur++;  break;
/* 459 */       case 36: Grid.xCur = 0; break;
/* 460 */       case 35: Grid.xCur = Grid.xSz - 1; break;
/* 461 */       case 33: Grid.yCur = 0; break;
/* 462 */       case 34: Grid.yCur = Grid.ySz - 1; break;
/*     */       case 8:
/*     */       case 10:
/*     */       case 32:
/*     */       case 127:
/* 467 */         Grid.sol[Grid.xCur][Grid.yCur] = 0;
/*     */         break;
/*     */       default:
/* 470 */         ch = e.getKeyChar();
/* 471 */         if (Character.isDigit(ch) && ch != '0' && ch != Grid.sol[Grid.xCur][Grid.yCur]) {
/* 472 */           Grid.sol[Grid.xCur][Grid.yCur] = ch;
/* 473 */           (new Thread(this.solveThread)).start();
/*     */         } 
/*     */         break; }
/*     */     
/* 477 */     restoreFrame();
/*     */   }
/*     */ }


/* Location:              C:\Users\mustapha\Desktop\Crossword-Express.jar!\crosswordexpress\FillominoSolve.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */