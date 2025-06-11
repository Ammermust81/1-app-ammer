/*     */ package crosswordexpress;
/*     */ import java.awt.Color;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Insets;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ComponentEvent;
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
/*     */ public final class DominoSolve {
/*  25 */   String dominoSolve = "<div>A <b>DOMINO</b> puzzle consists of a rectangular grid, with each cell of the grid occupied by a single digit number. The grid represents a set of domino pieces laid out in a rectangular array. For example, if you use a \"standard\" domino set having pieces ranging from 0-0 spots to 6-6 spots, then the rectangle will be 8 cells across and 7 cells down. The puzzles can have different sizes, depending on the size of the domino set you use. To solve such a puzzle, it is necessary to outline pairs of adjacent numbers in such a way that every piece of the domino set is revealed. Each puzzle has a unique solution, and can be solved without recourse to guesswork.<p/>Study the puzzle until you discover an adjacent pair of cells which you believe represent a domino piece. Then point the mouse cursor to approximately the mid-point of the line separating the pair of cells, and click the mouse. This will remove the line. If you make an error, another mouse click in the same location will restore the line.<br/><br/></div><span class='m'>Menu Functions</span><ul><li/><span class='s'>File Menu</span><ul><li/><span>Load a Puzzle</span><br/>Use this option to choose the puzzle you want to solve from the pool of DOMINO puzzles currently available on your computer.<p/><li/><span>Quit Solving</span><br/>Returns you to the DOMINO Construction screen.</ul><li/><span class='s'>View Menu</span><ul><li/><span>Display Options</span><br/>This leads you to a dialog box in which you can change the colors of various elements within the puzzle, and control the fonts which will be used for the puzzle's text components. You can also decide if printing of the puzzle will be done in black and white or in color.</ul><li/><span class='s'>Tasks Menu</span><ul><li/><span>Reveal Errors</span><br/>If you think you may have made errors, this option will show you where they are by highlighting them for a period of 1.5 seconds.<p/><li/><span>Reveal Solution</span><br/>The entire solution can be seen by selecting this option.<p/><li/><span>Begin Again</span><br/>You can restart the entire solution process at any time by selecting this option.</ul><li/><span class='s'>Help Menu</span><ul><li/><span>Domino Help</span><br/>Displays the Help screen which you are now reading.</ul></ul></body>";
/*     */ 
/*     */   
/*     */   static JFrame jfSolveDomino;
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
/*     */   DominoSolve(JFrame jf) {
/*  66 */     this.memMode = Def.puzzleMode;
/*  67 */     Def.puzzleMode = 41;
/*     */ 
/*     */     
/*  70 */     Grid.clearGrid();
/*     */     
/*  72 */     jfSolveDomino = new JFrame("Domino");
/*  73 */     jfSolveDomino.setSize(Op.getInt(Op.DM.DmW.ordinal(), Op.dm), Op.getInt(Op.DM.DmH.ordinal(), Op.dm));
/*  74 */     int frameX = (jf.getX() + jfSolveDomino.getWidth() > Methods.scrW) ? (Methods.scrW - jfSolveDomino.getWidth() - 10) : jf.getX();
/*  75 */     jfSolveDomino.setLocation(frameX, jf.getY());
/*  76 */     jfSolveDomino.setLayout((LayoutManager)null);
/*  77 */     jfSolveDomino.setDefaultCloseOperation(0);
/*  78 */     jfSolveDomino
/*  79 */       .addComponentListener(new ComponentAdapter() {
/*     */           public void componentResized(ComponentEvent ce) {
/*  81 */             int oldw = Op.getInt(Op.DM.DmW.ordinal(), Op.dm);
/*  82 */             int oldh = Op.getInt(Op.DM.DmH.ordinal(), Op.dm);
/*  83 */             Methods.frameResize(DominoSolve.jfSolveDomino, oldw, oldh, 500, 480);
/*  84 */             Op.setInt(Op.DM.DmW.ordinal(), DominoSolve.jfSolveDomino.getWidth(), Op.dm);
/*  85 */             Op.setInt(Op.DM.DmH.ordinal(), DominoSolve.jfSolveDomino.getHeight(), Op.dm);
/*  86 */             DominoSolve.restoreFrame();
/*     */           }
/*     */         });
/*     */     
/*  90 */     jfSolveDomino
/*  91 */       .addWindowListener(new WindowAdapter() {
/*     */           public void windowClosing(WindowEvent we) {
/*  93 */             if (Def.selecting)
/*  94 */               return;  Op.saveOptions("domino.opt", Op.dm);
/*  95 */             DominoSolve.this.restoreIfDone();
/*  96 */             DominoSolve.this.saveDomino(Op.dm[Op.DM.DmPuz.ordinal()]);
/*  97 */             CrosswordExpress.transfer(40, DominoSolve.jfSolveDomino);
/*     */           }
/*     */         });
/*     */     
/* 101 */     Methods.closeHelp();
/*     */ 
/*     */     
/* 104 */     this.solveThread = (() -> {
/*     */         for (int j = 0; j < Grid.ySz; j++) {
/*     */           for (int i = 0; i < Grid.xSz; i++) {
/*     */             if (Grid.mode[i][j] != Grid.copy[i][j])
/*     */               return; 
/*     */           } 
/*     */         }  Methods.congratulations(jfSolveDomino);
/*     */       });
/* 112 */     jl1 = new JLabel(); jfSolveDomino.add(jl1);
/* 113 */     jl2 = new JLabel(); jfSolveDomino.add(jl2);
/*     */ 
/*     */     
/* 116 */     menuBar = new JMenuBar();
/* 117 */     menuBar.setBackground(Def.COLOR_MENUBAR);
/* 118 */     jfSolveDomino.setJMenuBar(menuBar);
/*     */     
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
/*     */           saveDomino(Op.dm[Op.DM.DmPuz.ordinal()]);
/*     */           new Select(jfSolveDomino, "domino", "domino", Op.dm, Op.DM.DmPuz.ordinal(), false);
/*     */         });
/* 134 */     this.menuItem = new JMenuItem("Quit Solving");
/* 135 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(81, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 136 */     this.menu.add(this.menuItem);
/* 137 */     this.menuItem
/* 138 */       .addActionListener(ae -> {
/*     */           Op.saveOptions("domino.opt", Op.dm);
/*     */           
/*     */           restoreIfDone();
/*     */           
/*     */           saveDomino(Op.dm[Op.DM.DmPuz.ordinal()]);
/*     */           
/*     */           CrosswordExpress.transfer(40, jfSolveDomino);
/*     */         });
/* 147 */     this.menu = new JMenu("View");
/* 148 */     menuBar.add(this.menu);
/* 149 */     this.menuItem = new JMenuItem("Display Options");
/* 150 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(89, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 151 */     this.menu.add(this.menuItem);
/* 152 */     this.menuItem
/* 153 */       .addActionListener(ae -> {
/*     */           DominoBuild.printOptions(jfSolveDomino, "Display Options");
/*     */ 
/*     */           
/*     */           restoreFrame();
/*     */         });
/*     */     
/* 160 */     this.menu = new JMenu("Tasks");
/* 161 */     menuBar.add(this.menu);
/*     */     
/* 163 */     ActionListener errorTimer = ae -> {
/*     */         Def.dispErrors = Boolean.valueOf(false);
/*     */         pp.repaint();
/*     */         this.myTimer.stop();
/*     */       };
/* 168 */     this.myTimer = new Timer(1500, errorTimer);
/*     */     
/* 170 */     this.menuItem = new JMenuItem("Reveal Errors");
/* 171 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(69, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 172 */     this.menu.add(this.menuItem);
/* 173 */     this.menuItem
/* 174 */       .addActionListener(ae -> {
/*     */           if (Methods.noErrors == 0) {
/*     */             this.myTimer.start();
/*     */             
/*     */             Def.dispErrors = Boolean.valueOf(true);
/*     */           } else {
/*     */             Methods.noReveal(jfSolveDomino);
/*     */           } 
/*     */           
/*     */           pp.repaint();
/*     */         });
/* 185 */     this.menuItem = new JMenuItem("Reveal Solution");
/* 186 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(83, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 187 */     this.menu.add(this.menuItem);
/* 188 */     this.menuItem
/* 189 */       .addActionListener(ae -> {
/*     */           if (Methods.noReveal == 0) {
/*     */             for (int j = 0; j < Grid.ySz; j++) {
/*     */               for (int i = 0; i < Grid.xSz; i++)
/*     */                 Grid.mode[i][j] = Grid.copy[i][j]; 
/*     */             } 
/*     */           } else {
/*     */             Methods.noReveal(jfSolveDomino);
/*     */           } 
/*     */           restoreFrame();
/*     */         });
/* 200 */     this.menuItem = new JMenuItem("Begin again");
/* 201 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(66, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 202 */     this.menu.add(this.menuItem);
/* 203 */     this.menuItem
/* 204 */       .addActionListener(ae -> {
/*     */           Methods.clearGrid(Grid.mode);
/*     */ 
/*     */           
/*     */           restoreFrame();
/*     */         });
/*     */     
/* 211 */     this.menu = new JMenu("Help");
/* 212 */     menuBar.add(this.menu);
/* 213 */     this.menuItem = new JMenuItem("Domino Help");
/* 214 */     this.menu.add(this.menuItem);
/* 215 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(72, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 216 */     this.menuItem
/* 217 */       .addActionListener(ae -> Methods.cweHelp(jfSolveDomino, null, "Solving Domini Puzzles", this.dominoSolve));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 223 */     loadDomino(Op.dm[Op.DM.DmPuz.ordinal()]);
/* 224 */     pp = new DominoSolvePP(0, 37);
/* 225 */     jfSolveDomino.add(pp);
/*     */     
/* 227 */     pp
/* 228 */       .addMouseListener(new MouseAdapter() {
/*     */           public void mousePressed(MouseEvent e) {
/* 230 */             DominoSolve.this.updateGrid(e);
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 235 */     pp
/* 236 */       .addMouseMotionListener(new MouseAdapter() {
/*     */           public void mouseMoved(MouseEvent e) {
/* 238 */             if (Def.isMac) {
/* 239 */               DominoSolve.jfSolveDomino.setResizable((DominoSolve.jfSolveDomino.getWidth() - e.getX() < 15 && DominoSolve.jfSolveDomino
/* 240 */                   .getHeight() - e.getY() < 95));
/*     */             }
/*     */           }
/*     */         });
/*     */     
/* 245 */     restoreFrame();
/*     */   }
/*     */   
/*     */   static void restoreFrame() {
/* 249 */     jfSolveDomino.setVisible(true);
/* 250 */     Insets insets = jfSolveDomino.getInsets();
/* 251 */     panelW = jfSolveDomino.getWidth() - insets.left + insets.right;
/* 252 */     panelH = jfSolveDomino.getHeight() - insets.top + insets.bottom + 37 + menuBar.getHeight();
/* 253 */     pp.setSize(panelW, panelH);
/* 254 */     jfSolveDomino.requestFocusInWindow();
/* 255 */     pp.repaint();
/* 256 */     Methods.infoPanel(jl1, jl2, "Solve Domino", "Puzzle : " + Op.dm[Op.DM.DmPuz.ordinal()], panelW);
/*     */   }
/*     */   
/*     */   static void setSizesAndOffsets(int x, int y, int width, int height, int inset) {
/* 260 */     int i = (width - inset) / Grid.xSz;
/* 261 */     int j = (height - inset) / Grid.ySz;
/* 262 */     Grid.xCell = Grid.yCell = (i < j) ? i : j;
/* 263 */     Grid.xOrg = x + (width - Grid.xSz * Grid.xCell) / 2;
/* 264 */     Grid.yOrg = y + (height - Grid.ySz * Grid.yCell) / 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void loadDomino(String dominoName) {
/*     */     try {
/* 272 */       File fl = new File("domino/" + dominoName);
/* 273 */       if (!fl.exists()) {
/* 274 */         fl = new File("domino/");
/* 275 */         String[] s = fl.list(); int m;
/* 276 */         for (m = 0; m < s.length && (
/* 277 */           s[m].lastIndexOf(".domino") == -1 || s[m].charAt(0) == '.'); m++);
/*     */         
/* 279 */         dominoName = s[m];
/* 280 */         Op.dm[Op.DM.DmPuz.ordinal()] = dominoName;
/*     */       } 
/*     */       
/* 283 */       DataInputStream dataIn = new DataInputStream(new FileInputStream("domino/" + dominoName));
/* 284 */       Grid.xSz = dataIn.readInt();
/* 285 */       Grid.ySz = dataIn.readInt();
/* 286 */       Methods.noReveal = dataIn.readByte();
/* 287 */       Methods.noErrors = dataIn.readByte(); int i;
/* 288 */       for (i = 0; i < 54; i++)
/* 289 */         dataIn.readByte(); 
/* 290 */       for (int k = 0; k < Grid.ySz; k++) {
/* 291 */         for (i = 0; i < Grid.xSz; i++) {
/* 292 */           Grid.mode[i][k] = dataIn.readInt();
/* 293 */           Grid.sol[i][k] = dataIn.readInt();
/* 294 */           Grid.letter[i][k] = dataIn.readInt();
/*     */         } 
/* 296 */       }  Methods.puzzleTitle = dataIn.readUTF();
/* 297 */       Methods.author = dataIn.readUTF();
/* 298 */       Methods.copyright = dataIn.readUTF();
/* 299 */       Methods.puzzleNumber = dataIn.readUTF();
/* 300 */       Methods.puzzleNotes = dataIn.readUTF();
/* 301 */       dataIn.close();
/*     */     }
/* 303 */     catch (IOException exc) {
/*     */       return;
/* 305 */     }  Methods.havePuzzle = true;
/*     */     
/* 307 */     for (int j = 0; j < Grid.ySz; j++) {
/* 308 */       for (int i = 0; i < Grid.xSz; i++) {
/* 309 */         Grid.copy[i][j] = Grid.mode[i][j];
/* 310 */         Grid.mode[i][j] = Grid.sol[i][j];
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   void saveDomino(String dominoName) {
/* 317 */     for (int y = 0; y < Grid.ySz; y++) {
/* 318 */       for (int x = 0; x < Grid.xSz; x++) {
/* 319 */         Grid.sol[x][y] = Grid.mode[x][y];
/* 320 */         Grid.mode[x][y] = Grid.copy[x][y];
/*     */       } 
/*     */     }  try {
/* 323 */       DataOutputStream dataOut = new DataOutputStream(new FileOutputStream("domino/" + dominoName));
/* 324 */       dataOut.writeInt(Grid.xSz);
/* 325 */       dataOut.writeInt(Grid.ySz);
/* 326 */       dataOut.writeByte(Methods.noReveal);
/* 327 */       dataOut.writeByte(Methods.noErrors);
/* 328 */       for (int i = 0; i < 54; i++)
/* 329 */         dataOut.writeByte(0); 
/* 330 */       for (int j = 0; j < Grid.ySz; j++) {
/* 331 */         for (int k = 0; k < Grid.xSz; k++) {
/* 332 */           dataOut.writeInt(Grid.mode[k][j]);
/* 333 */           dataOut.writeInt(Grid.sol[k][j]);
/* 334 */           dataOut.writeInt(Grid.letter[k][j]);
/*     */         } 
/* 336 */       }  dataOut.writeUTF(Methods.puzzleTitle);
/* 337 */       dataOut.writeUTF(Methods.author);
/* 338 */       dataOut.writeUTF(Methods.copyright);
/* 339 */       dataOut.writeUTF(Methods.puzzleNumber);
/* 340 */       dataOut.writeUTF(Methods.puzzleNotes);
/* 341 */       dataOut.close();
/*     */     }
/* 343 */     catch (IOException exc) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static void drawDomino(Graphics2D g2, int[][] puzzleArray) {
/* 349 */     Stroke normalStroke = new BasicStroke(Grid.xCell / 25.0F, 2, 2);
/* 350 */     RenderingHints rh = g2.getRenderingHints();
/* 351 */     rh.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
/* 352 */     g2.setRenderingHints(rh);
/* 353 */     g2.setStroke(normalStroke);
/*     */     
/*     */     int j;
/* 356 */     for (j = 0; j < Grid.ySz; j++) {
/* 357 */       for (int i = 0; i < Grid.xSz; i++) {
/* 358 */         int theColor; if (Def.dispWithColor.booleanValue())
/* 359 */         { if (Def.dispErrors.booleanValue() && Grid.mode[i][j] != 0 && Grid.mode[i][j] != Grid.copy[i][j]) {
/* 360 */             theColor = Op.getColorInt(Op.DM.DmError.ordinal(), Op.dm);
/*     */           } else {
/*     */             
/* 363 */             theColor = (Grid.mode[i][j] == 0) ? Op.getColorInt(Op.DM.DmCell.ordinal(), Op.dm) : Op.getColorInt(Op.DM.DmSolved.ordinal(), Op.dm);
/*     */           }  }
/* 365 */         else { theColor = 16777215; }
/* 366 */          g2.setColor(new Color(theColor));
/* 367 */         g2.fillRect(Grid.xOrg + i * Grid.xCell, Grid.yOrg + j * Grid.yCell, Grid.xCell, Grid.yCell);
/*     */       } 
/*     */     } 
/*     */     
/* 371 */     g2.setColor(new Color(Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.DM.DmLine.ordinal(), Op.dm) : 16777215));
/* 372 */     for (j = 0; j < Grid.ySz; j++) {
/* 373 */       for (int i = 0; i < Grid.xSz; i++) {
/* 374 */         int x = Grid.xOrg + i * Grid.xCell;
/* 375 */         int y = Grid.yOrg + j * Grid.yCell;
/* 376 */         for (int c = 1; c < 16; c *= 2) {
/* 377 */           if ((Grid.mode[i][j] & c) == 0)
/* 378 */             switch (c) { case 1:
/* 379 */                 g2.drawLine(x, y, x + Grid.xCell, y); break;
/* 380 */               case 2: g2.drawLine(x + Grid.xCell, y, x + Grid.xCell, y + Grid.yCell); break;
/* 381 */               case 4: g2.drawLine(x, y + Grid.yCell, x + Grid.xCell, y + Grid.yCell); break;
/* 382 */               case 8: g2.drawLine(x, y + Grid.yCell, x, y); break; }
/*     */              
/*     */         } 
/*     */       } 
/* 386 */     }  if (!Def.dispWithColor.booleanValue()) {
/* 387 */       g2.setColor(Def.COLOR_BLACK);
/* 388 */       g2.drawRect(Grid.xOrg, Grid.yOrg, Grid.xCell * Grid.xSz, Grid.yCell * Grid.ySz);
/*     */     } 
/*     */ 
/*     */     
/* 392 */     g2.setFont(new Font(Op.dm[Op.DM.DmFont.ordinal()], 0, 8 * Grid.yCell / 10));
/* 393 */     FontMetrics fm = g2.getFontMetrics();
/* 394 */     for (j = 0; j < Grid.ySz; j++) {
/* 395 */       for (int i = 0; i < Grid.xSz; i++) {
/* 396 */         if (Def.dispWithColor.booleanValue()) {
/* 397 */           g2.setColor(new Color((Grid.mode[i][j] == 0) ? Op.getColorInt(Op.DM.DmNumber.ordinal(), Op.dm) : Op.getColorInt(Op.DM.DmSolvedNumber.ordinal(), Op.dm)));
/*     */         } else {
/* 399 */           g2.setColor(Def.COLOR_BLACK);
/* 400 */         }  char ch = (char)puzzleArray[i][j];
/* 401 */         if (Character.isDigit(ch)) {
/* 402 */           int w = fm.stringWidth("" + ch);
/* 403 */           g2.drawString("" + ch, Grid.xOrg + i * Grid.xCell + (Grid.xCell - w) / 2, Grid.yOrg + j * Grid.yCell + (Grid.yCell + fm.getAscent() - fm.getDescent()) / 2);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   void restoreIfDone() {
/* 409 */     for (int j = 0; j < Grid.ySz; j++) {
/* 410 */       for (int i = 0; i < Grid.xSz; i++)
/* 411 */       { if (Grid.mode[i][j] != Grid.copy[i][j])
/*     */           return;  } 
/* 413 */     }  Methods.clearGrid(Grid.mode);
/*     */   }
/*     */   
/*     */   void updateGrid(MouseEvent e) {
/* 417 */     int loc[] = new int[4], mouseX = e.getX(), mouseY = e.getY();
/*     */ 
/*     */     
/* 420 */     if (mouseX < Grid.xOrg) mouseX = Grid.xOrg + 1; 
/* 421 */     if (mouseY < Grid.yOrg) mouseY = Grid.yOrg + 1; 
/* 422 */     int x = Grid.xOrg + Grid.xSz * Grid.xCell; if (mouseX >= x) mouseX = x - 1; 
/* 423 */     int y = Grid.yOrg + Grid.ySz * Grid.yCell; if (mouseY >= y) mouseY = y - 1; 
/* 424 */     x = (mouseX - Grid.xOrg) / Grid.xCell;
/* 425 */     y = (mouseY - Grid.yOrg) / Grid.yCell;
/*     */     
/* 427 */     loc[0] = (mouseY - Grid.yOrg) % Grid.yCell;
/* 428 */     loc[3] = (mouseX - Grid.xOrg) % Grid.xCell;
/* 429 */     loc[1] = Grid.xCell - loc[3];
/* 430 */     loc[2] = Grid.yCell - loc[0]; int best;
/* 431 */     for (int min = 1000, i = 0; i < 4; i++) {
/* 432 */       if (loc[i] < min) {
/* 433 */         min = loc[i];
/* 434 */         best = i;
/*     */       } 
/*     */     } 
/* 437 */     switch (best) {
/*     */       case 0:
/* 439 */         if (y > 0) {
/* 440 */           if (Grid.mode[x][y] == 0 && Grid.mode[x][y - 1] == 0) {
/* 441 */             Grid.mode[x][y] = 1;
/* 442 */             Grid.mode[x][y - 1] = 4; break;
/*     */           } 
/* 444 */           if (Grid.mode[x][y] == 1)
/* 445 */           { Grid.mode[x][y - 1] = 0; Grid.mode[x][y] = 0; } 
/*     */         }  break;
/*     */       case 1:
/* 448 */         if (x < Grid.xSz - 1) {
/* 449 */           if (Grid.mode[x][y] == 0 && Grid.mode[x + 1][y] == 0) {
/* 450 */             Grid.mode[x][y] = 2;
/* 451 */             Grid.mode[x + 1][y] = 8; break;
/*     */           } 
/* 453 */           if (Grid.mode[x][y] == 2)
/* 454 */           { Grid.mode[x + 1][y] = 0; Grid.mode[x][y] = 0; } 
/*     */         }  break;
/*     */       case 2:
/* 457 */         if (y < Grid.ySz - 1) {
/* 458 */           if (Grid.mode[x][y] == 0 && Grid.mode[x][y + 1] == 0) {
/* 459 */             Grid.mode[x][y] = 4;
/* 460 */             Grid.mode[x][y + 1] = 1; break;
/*     */           } 
/* 462 */           if (Grid.mode[x][y] == 4)
/* 463 */           { Grid.mode[x][y + 1] = 0; Grid.mode[x][y] = 0; } 
/*     */         }  break;
/*     */       case 3:
/* 466 */         if (x > 0) {
/* 467 */           if (Grid.mode[x][y] == 0 && Grid.mode[x - 1][y] == 0) {
/* 468 */             Grid.mode[x][y] = 8;
/* 469 */             Grid.mode[x - 1][y] = 2; break;
/*     */           } 
/* 471 */           if (Grid.mode[x][y] == 8) {
/* 472 */             Grid.mode[x - 1][y] = 0; Grid.mode[x][y] = 0;
/*     */           } 
/*     */         }  break;
/* 475 */     }  (new Thread(this.solveThread)).start();
/* 476 */     restoreFrame();
/*     */   }
/*     */ }


/* Location:              C:\Users\mustapha\Desktop\Crossword-Express.jar!\crosswordexpress\DominoSolve.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */