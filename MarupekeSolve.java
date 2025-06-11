/*     */ package crosswordexpress;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.MouseAdapter;
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
/*     */ public final class MarupekeSolve {
/*     */   static JFrame jfSolveMarupeke;
/*     */   static JMenuBar menuBar;
/*     */   JMenu menu;
/*     */   JMenu submenu;
/*     */   JMenuItem menuItem;
/*     */   static JPanel pp;
/*  23 */   int undoIndex = 0; static int panelW; static int panelH; static JLabel jl1; static JLabel jl2; Timer myTimer; Runnable solveThread; int memMode;
/*  24 */   int[] undox = new int[300]; int[] undoy = new int[300];
/*  25 */   static int hintx = -1; static int hinty = -1; static int errorx = -1; static int errory = -1;
/*     */   
/*  27 */   String marupekeSolve = "<div>A MARUPEKE puzzle consists of a square grid in which some of the squares are blacked out in the manner of a crossword puzzle. Some of the remaining squares contain either an <b>X</b> or a <b>O</b>. To solve such a puzzle, the solver must place an <b>X</b> or a <b>O</b> into each of the remaining squares in such a way that there are never three <b>Xs</b> or three <b>Os</b> in line, either horizontally, vertically or diagonally. Each puzzle has a unique solution which does not require any guesswork to achieve. Puzzles can be built in sizes from 4x4 up to 10x10.<br/><br/></div><span class='parhead'>Menu Functions</span><ul><li/><span class='s'>File Menu</span><ul><li/><span>Load a Puzzle</span><br/>Use this option to choose the puzzle you want to solve from the pool of MARUPEKE puzzles currently available on your computer.<p/><li/><span>Quit Solving</span><br/>Returns you to the MARUPEKE Construction screen.</ul><li/><span class='s'>Tasks Menu</span><ul><li/><span>Reveal Errors</span><br/>If you think you may have made errors, this option will show you where they are by highlighting them for a period of 1.5 seconds.<p/><li/><span>Reveal Solution</span><br/>The entire solution can be seen by selecting this option.<p/><li/><span>Begin Again</span><br/>You can restart the entire solution process at any time by selecting this option.<li/><span>Request a Hint</span><br/>This option will highlight one of the puzzle cells in blue to indicate the location within the puzzle where further progress can be made.<p/><li/><span>Undo</span><br/>This option will undo in reverse order, each of the operations you have done on the puzzle.<p/></ul><li/><span class='s'>Help Menu</span><ul><li/><span>Marupeke Help</span><br/>Displays the Help screen which you are now reading.</ul></ul></body>";
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
/*  65 */   String marupekeHint = "<div>Your partial solution has been checked and found to be in error in those cells which are highlighted in red. Under these circumstances, it is not possible to provide a trustworthy hint as to the next step in the solution.<p/>Please investigate, and correct the errors before proceeding with the solution process.</div></body>";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   MarupekeSolve(JFrame jf) {
/*  71 */     this.memMode = Def.puzzleMode;
/*  72 */     Def.puzzleMode = 231;
/*  73 */     Def.dispCursor = Boolean.valueOf(true);
/*  74 */     jfSolveMarupeke = new JFrame("Marupeke");
/*  75 */     jfSolveMarupeke.setSize(Op.getInt(Op.MA.MaW.ordinal(), Op.ma), Op.getInt(Op.MA.MaH.ordinal(), Op.ma));
/*  76 */     int frameX = (jf.getX() + jfSolveMarupeke.getWidth() > Methods.scrW) ? (Methods.scrW - jfSolveMarupeke.getWidth() - 10) : jf.getX();
/*  77 */     jfSolveMarupeke.setLocation(frameX, jf.getY());
/*  78 */     jfSolveMarupeke.setLayout((LayoutManager)null);
/*  79 */     jfSolveMarupeke.setDefaultCloseOperation(0);
/*  80 */     jfSolveMarupeke
/*  81 */       .addComponentListener(new ComponentAdapter() {
/*     */           public void componentResized(ComponentEvent ce) {
/*  83 */             int oldw = Op.getInt(Op.MA.MaW.ordinal(), Op.ma);
/*  84 */             int oldh = Op.getInt(Op.MA.MaH.ordinal(), Op.ma);
/*  85 */             Methods.frameResize(MarupekeSolve.jfSolveMarupeke, oldw, oldh, 500, 580);
/*  86 */             Op.setInt(Op.MA.MaW.ordinal(), MarupekeSolve.jfSolveMarupeke.getWidth(), Op.ma);
/*  87 */             Op.setInt(Op.MA.MaH.ordinal(), MarupekeSolve.jfSolveMarupeke.getHeight(), Op.ma);
/*  88 */             MarupekeSolve.restoreFrame();
/*     */           }
/*     */         });
/*     */     
/*  92 */     jfSolveMarupeke
/*  93 */       .addWindowListener(new WindowAdapter() {
/*     */           public void windowClosing(WindowEvent we) {
/*  95 */             if (Def.selecting)
/*  96 */               return;  Op.saveOptions("marupeke.opt", Op.ma);
/*  97 */             MarupekeSolve.this.restoreIfDone();
/*  98 */             MarupekeSolve.saveMarupeke(Op.ma[Op.MA.MaPuz.ordinal()]);
/*  99 */             CrosswordExpress.transfer(230, MarupekeSolve.jfSolveMarupeke);
/*     */           }
/*     */         });
/*     */     
/* 103 */     Methods.closeHelp();
/*     */ 
/*     */     
/* 106 */     this.solveThread = (() -> {
/*     */         for (int j = 0; j < Grid.ySz; j++) {
/*     */           for (int i = 0; i < Grid.xSz; i++) {
/*     */             if (Grid.sol[i][j] != Grid.work[i][j])
/*     */               return; 
/*     */           } 
/*     */         }  Methods.congratulations(jfSolveMarupeke);
/*     */       });
/* 114 */     jl1 = new JLabel(); jfSolveMarupeke.add(jl1);
/* 115 */     jl2 = new JLabel(); jfSolveMarupeke.add(jl2);
/*     */ 
/*     */     
/* 118 */     menuBar = new JMenuBar();
/* 119 */     menuBar.setBackground(Def.COLOR_MENUBAR);
/* 120 */     jfSolveMarupeke.setJMenuBar(menuBar);
/*     */     
/* 122 */     this.menu = new JMenu("File");
/* 123 */     menuBar.add(this.menu);
/* 124 */     this.menuItem = new JMenuItem("Load a Puzzle");
/* 125 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(76, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 126 */     this.menu.add(this.menuItem);
/* 127 */     this.menuItem
/* 128 */       .addActionListener(ae -> {
/*     */           if (Def.building == 1) {
/*     */             return;
/*     */           }
/*     */           restoreIfDone();
/*     */           saveMarupeke(Op.ma[Op.MA.MaPuz.ordinal()]);
/*     */           new Select(jfSolveMarupeke, "marupeke", "marupeke", Op.ma, Op.MA.MaPuz.ordinal(), false);
/*     */         });
/* 136 */     this.menuItem = new JMenuItem("Quit Solving");
/* 137 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(81, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 138 */     this.menu.add(this.menuItem);
/* 139 */     this.menuItem
/* 140 */       .addActionListener(ae -> {
/*     */           Op.saveOptions("marupeke.opt", Op.ma);
/*     */           
/*     */           restoreIfDone();
/*     */           
/*     */           saveMarupeke(Op.ma[Op.MA.MaPuz.ordinal()]);
/*     */           
/*     */           CrosswordExpress.transfer(230, jfSolveMarupeke);
/*     */         });
/* 149 */     this.menu = new JMenu("Tasks");
/* 150 */     menuBar.add(this.menu);
/*     */     
/* 152 */     ActionListener errorTimer = ae -> {
/*     */         Def.dispErrors = Boolean.valueOf(false);
/*     */         pp.repaint();
/*     */         this.myTimer.stop();
/*     */       };
/* 157 */     this.myTimer = new Timer(1500, errorTimer);
/*     */     
/* 159 */     this.menuItem = new JMenuItem("Reveal Errors");
/* 160 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(69, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 161 */     this.menu.add(this.menuItem);
/* 162 */     this.menuItem
/* 163 */       .addActionListener(ae -> {
/*     */           if (Methods.noErrors == 0) {
/*     */             this.myTimer.start();
/*     */             
/*     */             Def.dispErrors = Boolean.valueOf(true);
/*     */           } else {
/*     */             Methods.noReveal(jfSolveMarupeke);
/*     */           } 
/*     */           
/*     */           pp.repaint();
/*     */         });
/* 174 */     this.menuItem = new JMenuItem("Reveal Solution");
/* 175 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(83, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 176 */     this.menu.add(this.menuItem);
/* 177 */     this.menuItem
/* 178 */       .addActionListener(ae -> {
/*     */           if (Methods.noReveal == 0) {
/*     */             for (int j = 0; j < Grid.ySz; j++) {
/*     */               for (int i = 0; i < Grid.xSz; i++) {
/*     */                 Grid.work[i][j] = Grid.sol[i][j];
/*     */               }
/*     */             } 
/*     */           } else {
/*     */             Methods.noReveal(jfSolveMarupeke);
/*     */           } 
/*     */           restoreFrame();
/*     */         });
/* 190 */     this.menuItem = new JMenuItem("Begin again");
/* 191 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(66, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 192 */     this.menu.add(this.menuItem);
/* 193 */     this.menuItem
/* 194 */       .addActionListener(ae -> {
/*     */           clearSolution();
/*     */           
/*     */           restoreFrame();
/*     */           
/*     */           this.undoIndex = 0;
/*     */         });
/* 201 */     this.menuItem = new JMenuItem("Request a Hint");
/* 202 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(82, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 203 */     this.menu.add(this.menuItem);
/* 204 */     this.menuItem
/* 205 */       .addActionListener(ae -> {
/*     */           hint();
/*     */           
/*     */           restoreFrame();
/*     */         });
/*     */     
/* 211 */     this.menuItem = new JMenuItem("Undo");
/* 212 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(85, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 213 */     this.menu.add(this.menuItem);
/* 214 */     this.menuItem
/* 215 */       .addActionListener(ae -> undo());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 221 */     this.menu = new JMenu("Help");
/* 222 */     menuBar.add(this.menu);
/* 223 */     this.menuItem = new JMenuItem("Marupeke Help");
/* 224 */     this.menu.add(this.menuItem);
/* 225 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(72, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 226 */     this.menuItem
/* 227 */       .addActionListener(ae -> Methods.cweHelp(jfSolveMarupeke, null, "Solving Marupeke Puzzles", this.marupekeSolve));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 233 */     loadMarupeke(Op.ma[Op.MA.MaPuz.ordinal()]);
/* 234 */     pp = new MarupekeSolvePP(0, 37);
/* 235 */     jfSolveMarupeke.add(pp);
/* 236 */     pp
/* 237 */       .addMouseListener(new MouseAdapter() {
/*     */           public void mousePressed(MouseEvent e) {
/* 239 */             MarupekeSolve.this.updateGrid(e);
/*     */           }
/*     */         });
/*     */     
/* 243 */     pp
/* 244 */       .addMouseMotionListener(new MouseAdapter() {
/*     */           public void mouseMoved(MouseEvent e) {
/* 246 */             if (Def.isMac) {
/* 247 */               MarupekeSolve.jfSolveMarupeke.setResizable((MarupekeSolve.jfSolveMarupeke.getWidth() - e.getX() < 15 && MarupekeSolve.jfSolveMarupeke
/* 248 */                   .getHeight() - e.getY() < 95));
/*     */             }
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 259 */     restoreFrame();
/*     */   }
/*     */   
/*     */   static void restoreFrame() {
/* 263 */     jfSolveMarupeke.setVisible(true);
/* 264 */     Insets insets = jfSolveMarupeke.getInsets();
/* 265 */     panelW = jfSolveMarupeke.getWidth() - insets.left + insets.right;
/* 266 */     panelH = jfSolveMarupeke.getHeight() - insets.top + insets.bottom + 37 + menuBar.getHeight();
/* 267 */     pp.setSize(panelW, panelH);
/* 268 */     setSizesAndOffsets(0, 0, panelW, panelH, 20, false);
/* 269 */     jfSolveMarupeke.requestFocusInWindow();
/* 270 */     pp.repaint();
/* 271 */     Methods.infoPanel(jl1, jl2, "Solve Marupeke", "Puzzle : " + Op.ma[Op.MA.MaPuz.ordinal()], panelW);
/*     */   }
/*     */   
/*     */   static void setSizesAndOffsets(int x, int y, int width, int height, int inset, boolean print) {
/* 275 */     int i = (width - inset) / Grid.xSz;
/* 276 */     int j = (height - inset) / Grid.ySz;
/* 277 */     Grid.xCell = Grid.yCell = (i < j) ? i : j;
/* 278 */     Grid.xOrg = x + (print ? ((width - Grid.xSz * Grid.xCell) / 2) : 10);
/* 279 */     Grid.yOrg = y + (print ? ((height - Grid.ySz * Grid.yCell) / 2) : 10);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void saveMarupeke(String marupekeName) {
/*     */     try {
/* 285 */       DataOutputStream dataOut = new DataOutputStream(new FileOutputStream("marupeke/" + marupekeName));
/* 286 */       dataOut.writeInt(Grid.xSz);
/* 287 */       dataOut.writeInt(Grid.ySz);
/* 288 */       dataOut.writeByte(Methods.noReveal);
/* 289 */       dataOut.writeByte(Methods.noErrors);
/* 290 */       for (int i = 0; i < 54; i++)
/* 291 */         dataOut.writeByte(0); 
/* 292 */       for (int j = 0; j < Grid.ySz; j++) {
/* 293 */         for (int k = 0; k < Grid.xSz; k++) {
/* 294 */           dataOut.writeInt(Grid.sol[k][j]);
/* 295 */           dataOut.writeInt(Grid.puz[k][j]);
/* 296 */           dataOut.writeInt(Grid.work[k][j]);
/*     */         } 
/* 298 */       }  dataOut.writeUTF(Methods.puzzleTitle);
/* 299 */       dataOut.writeUTF(Methods.author);
/* 300 */       dataOut.writeUTF(Methods.copyright);
/* 301 */       dataOut.writeUTF(Methods.puzzleNumber);
/* 302 */       dataOut.writeUTF(Methods.puzzleNotes);
/* 303 */       dataOut.close();
/*     */     }
/* 305 */     catch (IOException exc) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void loadMarupeke(String marupekeName) {
/*     */     try {
/* 313 */       File fl = new File("marupeke/" + marupekeName);
/* 314 */       if (!fl.exists()) {
/* 315 */         fl = new File("marupeke/");
/* 316 */         String[] s = fl.list(); int k;
/* 317 */         for (k = 0; k < s.length && (
/* 318 */           s[k].lastIndexOf(".marupeke") == -1 || s[k].charAt(0) == '.'); k++);
/*     */         
/* 320 */         marupekeName = s[k];
/* 321 */         Op.ma[Op.MA.MaPuz.ordinal()] = marupekeName;
/*     */       } 
/*     */ 
/*     */       
/* 325 */       DataInputStream dataIn = new DataInputStream(new FileInputStream("marupeke/" + marupekeName));
/* 326 */       Grid.xSz = dataIn.readInt();
/* 327 */       Grid.ySz = dataIn.readInt();
/* 328 */       Methods.noReveal = dataIn.readByte();
/* 329 */       Methods.noErrors = dataIn.readByte(); int i;
/* 330 */       for (i = 0; i < 54; i++)
/* 331 */         dataIn.readByte(); 
/* 332 */       for (int j = 0; j < Grid.ySz; j++) {
/* 333 */         for (i = 0; i < Grid.xSz; i++) {
/* 334 */           Grid.sol[i][j] = dataIn.readInt();
/* 335 */           Grid.puz[i][j] = dataIn.readInt();
/* 336 */           Grid.work[i][j] = dataIn.readInt();
/*     */         } 
/* 338 */       }  Methods.puzzleTitle = dataIn.readUTF();
/* 339 */       Methods.author = dataIn.readUTF();
/* 340 */       Methods.copyright = dataIn.readUTF();
/* 341 */       Methods.puzzleNumber = dataIn.readUTF();
/* 342 */       Methods.puzzleNotes = dataIn.readUTF();
/* 343 */       dataIn.close();
/*     */     }
/* 345 */     catch (IOException exc) {}
/*     */   }
/*     */ 
/*     */   
/*     */   static void clearSolution() {
/* 350 */     for (int j = 0; j < Grid.ySz; j++) {
/* 351 */       for (int i = 0; i < Grid.xSz; i++) {
/* 352 */         if ((Grid.work[i][j] & 0xC) == 0)
/* 353 */           Grid.work[i][j] = 0; 
/*     */       } 
/*     */     } 
/*     */   } void restoreIfDone() {
/* 357 */     for (int j = 0; j < Grid.ySz; j++) {
/* 358 */       for (int i = 0; i < Grid.xSz; i++)
/* 359 */       { if (Grid.work[i][j] != Grid.sol[i][j])
/*     */           return;  } 
/* 361 */     }  clearSolution();
/*     */   }
/*     */   
/*     */   void undo() {
/* 365 */     if (this.undoIndex == 0)
/* 366 */       return;  this.undoIndex--;
/* 367 */     switch (Grid.work[this.undox[this.undoIndex]][this.undoy[this.undoIndex]]) { case 0:
/* 368 */         Grid.work[this.undox[this.undoIndex]][this.undoy[this.undoIndex]] = 2; break;
/* 369 */       case 1: Grid.work[this.undox[this.undoIndex]][this.undoy[this.undoIndex]] = 0; break;
/* 370 */       case 2: Grid.work[this.undox[this.undoIndex]][this.undoy[this.undoIndex]] = 1; break; }
/*     */     
/* 372 */     restoreFrame();
/*     */   }
/*     */ 
/*     */   
/*     */   void hint() {
/* 377 */     boolean hint = false;
/*     */     int j;
/* 379 */     for (j = 0; j < Grid.ySz - 1; j++) {
/* 380 */       for (int i = 0; i < Grid.xSz - 1; i++) {
/* 381 */         if (Grid.work[i][j] != 0 && Grid.work[i][j] != Grid.sol[i][j]) {
/* 382 */           Methods.closeHelp();
/* 383 */           Methods.cweHelp(jfSolveMarupeke, null, "Error(s) in puzzle", this.marupekeHint);
/* 384 */           Def.dispErrors = Boolean.valueOf(true); return;
/*     */         } 
/*     */       } 
/*     */     } 
/* 388 */     for (j = 0; j < Grid.ySz + 4; j++) {
/* 389 */       for (int i = 0; i < Grid.xSz + 4; i++)
/* 390 */         Grid.scratch[i][j] = 0; 
/*     */     } 
/* 392 */     for (j = 0; j < Grid.ySz; j++) {
/* 393 */       for (int i = 0; i < Grid.xSz; i++)
/* 394 */         Grid.scratch[i + 2][j + 2] = Grid.work[i][j] & 0xB; 
/*     */     } 
/* 396 */     for (j = 2; j < Grid.ySz + 2; j++) {
/* 397 */       for (int i = 2; i < Grid.xSz + 2; i++) {
/* 398 */         if (Grid.scratch[i][j] == 0) {
/* 399 */           int v = Grid.scratch[i - 1][j - 1]; if ((v == 1 || v == 2) && v == Grid.scratch[i + 1][j + 1]) hint = true; 
/* 400 */           v = Grid.scratch[i][j - 1]; if ((v == 1 || v == 2) && v == Grid.scratch[i][j + 1]) hint = true; 
/* 401 */           v = Grid.scratch[i + 1][j - 1]; if ((v == 1 || v == 2) && v == Grid.scratch[i - 1][j + 1]) hint = true; 
/* 402 */           v = Grid.scratch[i + 1][j]; if ((v == 1 || v == 2) && v == Grid.scratch[i - 1][j]) hint = true; 
/* 403 */           v = Grid.scratch[i - 1][j - 1]; if ((v == 1 || v == 2) && v == Grid.scratch[i - 2][j - 2]) hint = true; 
/* 404 */           v = Grid.scratch[i][j - 1]; if ((v == 1 || v == 2) && v == Grid.scratch[i][j - 2]) hint = true; 
/* 405 */           v = Grid.scratch[i + 1][j - 1]; if ((v == 1 || v == 2) && v == Grid.scratch[i + 2][j - 2]) hint = true; 
/* 406 */           v = Grid.scratch[i + 1][j]; if ((v == 1 || v == 2) && v == Grid.scratch[i + 2][j]) hint = true; 
/* 407 */           v = Grid.scratch[i + 1][j + 1]; if ((v == 1 || v == 2) && v == Grid.scratch[i + 2][j + 2]) hint = true; 
/* 408 */           v = Grid.scratch[i][j + 1]; if ((v == 1 || v == 2) && v == Grid.scratch[i][j + 2]) hint = true; 
/* 409 */           v = Grid.scratch[i - 1][j + 1]; if ((v == 1 || v == 2) && v == Grid.scratch[i - 2][j + 2]) hint = true; 
/* 410 */           v = Grid.scratch[i - 1][j]; if ((v == 1 || v == 2) && v == Grid.scratch[i - 2][j]) hint = true; 
/* 411 */           if (hint) {
/* 412 */             hintx = i - 2; hinty = j - 2;
/*     */             return;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   } void updateGrid(MouseEvent e) {
/* 419 */     int x = e.getX(), y = e.getY();
/*     */     
/* 421 */     if (Def.building == 1)
/* 422 */       return;  if (x < Grid.xOrg || y < Grid.yOrg)
/* 423 */       return;  x = (x - Grid.xOrg) / Grid.xCell;
/* 424 */     y = (y - Grid.yOrg) / Grid.yCell;
/* 425 */     if (x >= Grid.xSz || y >= Grid.ySz)
/*     */       return; 
/* 427 */     this.undox[this.undoIndex] = x; this.undoy[this.undoIndex] = y; this.undoIndex++;
/* 428 */     switch (Grid.work[x][y]) { case 0:
/* 429 */         Grid.work[x][y] = 1; break;
/* 430 */       case 1: Grid.work[x][y] = 2; break;
/* 431 */       case 2: Grid.work[x][y] = 0; break; }
/*     */     
/* 433 */     (new Thread(this.solveThread)).start();
/* 434 */     restoreFrame();
/*     */   }
/*     */ }


/* Location:              C:\Users\mustapha\Desktop\Crossword-Express.jar!\crosswordexpress\MarupekeSolve.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */