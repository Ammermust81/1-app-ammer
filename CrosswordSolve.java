/*      */ package crosswordexpress;
/*      */ import java.awt.Color;
/*      */ import java.awt.Font;
/*      */ import java.awt.FontMetrics;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.Insets;
/*      */ import java.awt.Stroke;
/*      */ import java.awt.Toolkit;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.KeyEvent;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.io.DataInputStream;
/*      */ import java.io.DataOutputStream;
/*      */ import java.io.File;
/*      */ import javax.swing.JFrame;
/*      */ import javax.swing.JMenuItem;
/*      */ import javax.swing.KeyStroke;
/*      */ 
/*      */ public final class CrosswordSolve extends JPanel {
/*      */   static JFrame jfSolveCrossword;
/*      */   static JMenuBar menuBar;
/*      */   JMenu menu;
/*      */   JMenu submenu;
/*      */   JMenuItem menuItem;
/*      */   static JPanel pp;
/*   26 */   static int CCPREF = 300; static int puzW; static int puzH; static JLabel jl1; static JLabel jl2; Timer myTimer; static ccSpec[] ccDat; static int width; static int height;
/*      */   static boolean R2LClue;
/*      */   static boolean R2LWord;
/*      */   Runnable solveThread;
/*      */   int w;
/*      */   int h;
/*   32 */   String crosswordSolveHelp = "<div><span class='m'>Standard Crossword Solve</span><br/>This function allows you to solve crossword puzzles in the traditional way using clues. The clues are presented to you in much the same way as in a printed puzzle with all of the clues continuously in view. In addition, the clue for the  word you are currently working on is highlighted for your convenience. The puzzle is presented within a resizable window, and you are encouraged to experiment with the size of the window. The appearance of the window is heavily dependent on the size of the puzzle, the number and length of the clues, and the length of the longest word within the clue. Adjust the window size until you get a clue layout and font size which you find most appealing.<p/>Any character which is typed at the keyboard will be placed into the focus cell, and the focus cell will automatically move forward to the next character of the focus word. The location of the focus cell and its corresponding word may be shifted by pointing and clicking with the mouse, or by means of the cursor control keys. You can also move around the puzzle by pointing with the mouse and clicking any clue of interest. This will also automatically select the word associated with that clue.<p/>Other keys which provide useful functions during the solution process are Space bar, Delete, Backspace and Return.<p/><span class='m'>Crossword Solve in Audience Mode</span><br/>This is similar to the standard Solve function, except that only a single clue is displayed at a time, and the puzzle is displayed in a much larger format. The intention is that the puzzle be displayed using a video projector or big screen digital TV connected by an HDMI cable to the computer running the Crossword Express program. This configuration has been found useful for the entertainment of residents of retirement homes, and teachers find that students enjoy solving puzzles in this way.<p/>A dictionary suitable for the construction of these puzzles can be downloaded to your computer using the <b>Dictionary Maintenance / Tasks / Download Dictionary</b> menu option of Crossword Express. The name of this dictionary is <b>audience</b>. It contains words having 7 or less letters, so if you decide to make use of it you must take this into account when designing the grid that you will use to make your puzzles.</div><p/><span class='m'>Menu Functions</span><br/><ul><li/><span class='s'>File Menu</span><ul><li/><span>Select a Dictionary</span><br/>When loading a new puzzle, you begin by selecting the dictionary which was used to build the CROSSWORD puzzle which you want to solve.<p/><li/><span>Load a Puzzle</span><br/>Then you choose your puzzle from the pool of CROSSWORD puzzles currently available in the selected dictionary.<p/><li/><span>Quit Solving</span><br/>Returns you to either the CROSSWORD Build screen.<p/></ul><li/><span class='s'>View Menu</span><ul><li/><span>Display Options</span><br/>This leads you to a dialog box in which you can change the colors of various elements within the puzzle, and control the fonts which will be used for the puzzle's text components. You can also decide if printing of the puzzle will be done in black and white or in color.<p/></ul><li/><span class='s'>Tasks Menu</span><ul><li/><span>Reveal One Letter</span><br/>If you need a little help to get started, this option will place the correct letter into the current focus cell.<p/><li/><span>Reveal One Word</span><br/>If you need more help, this option will fill in the entire current word.<p/><li/><span>Google It</span><br/>This menu option will start your default web browser, and send the current clue to Google as a search string. This generally result in a number of links to reference pages which will provide the answer you seek. A more convenient method of accessing this function is simply to issue a mouse click on the currently highlighted clue.<p/><li/><span>Reveal Errors</span><br/>If you think you may have made errors, this option will show you where they are by highlighting them for a period of 1.5 seconds.<p/><li/><span>Reveal Solution</span><br/>The entire solution can be seen by selecting this option.<p/><li/><span>Begin Again</span><br/>You can restart the entire solution process at any time by selecting this option.<p/></ul><li/><span class='s'>Help Menu</span><ul><li/><span>Crossword Help</span><br/>Displays the Help screen which you are now reading.<p/></ul></ul></body>";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int tSz;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int pSz;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   CrosswordSolve(JFrame jf) {
/*  101 */     switch (Def.prevMode) { case 4:
/*  102 */         Op.cw[Op.CW.CwSolveDic.ordinal()] = Op.cw[CrosswordBuild.dicList[0]];
/*  103 */         Op.cw[Op.CW.CwSolvePuz.ordinal()] = Op.cw[Op.CW.CwPuz.ordinal()]; break;
/*      */       case 210:
/*  105 */         Op.cw[Op.CW.CwSolveDic.ordinal()] = Op.sw[Op.SW.SwDic.ordinal()];
/*  106 */         Op.cw[Op.CW.CwSolvePuz.ordinal()] = Op.sw[Op.SW.SwPuz.ordinal()]; break;
/*      */       case 6:
/*  108 */         Op.cw[Op.CW.CwSolveDic.ordinal()] = Op.ff[Op.FF.FfDic.ordinal()];
/*  109 */         Op.cw[Op.CW.CwSolvePuz.ordinal()] = Op.ff[Op.FF.FfPuz.ordinal()];
/*  110 */         Def.dispWithShadow = Boolean.valueOf(true);
/*      */         break; }
/*      */ 
/*      */     
/*  114 */     Def.puzzleMode = 5;
/*  115 */     Def.dispSolArray = Boolean.valueOf(true); Def.dispCursor = Boolean.valueOf(true); Def.dispGuideDigits = Boolean.valueOf(true);
/*  116 */     Grid.clearGrid();
/*      */     
/*  118 */     jfSolveCrossword = new JFrame("Solve a Crossword Puzzle");
/*      */     
/*  120 */     this.w = Op.getInt(Op.CW.CwSolveW.ordinal(), Op.cw);
/*  121 */     this.h = Op.getInt(Op.CW.CwSolveH.ordinal(), Op.cw);
/*  122 */     if (Def.audienceMode) { this.w = 500; this.h = this.w + 150; }
/*  123 */      jfSolveCrossword.setSize(this.w, this.h);
/*  124 */     int frameX = (jf.getX() + jfSolveCrossword.getWidth() > Methods.scrW) ? (Methods.scrW - jfSolveCrossword.getWidth() - 10) : jf.getX();
/*  125 */     jfSolveCrossword.setLocation(frameX, jf.getY());
/*  126 */     jfSolveCrossword.setLayout((LayoutManager)null);
/*  127 */     jfSolveCrossword.setDefaultCloseOperation(0);
/*  128 */     jfSolveCrossword
/*  129 */       .addComponentListener(new ComponentAdapter() {
/*      */           public void componentResized(ComponentEvent ce) {
/*  131 */             CrosswordSolve.this.w = CrosswordSolve.jfSolveCrossword.getWidth();
/*  132 */             CrosswordSolve.this.h = CrosswordSolve.jfSolveCrossword.getHeight();
/*  133 */             if (!Def.audienceMode) {
/*  134 */               if (CrosswordSolve.this.w < 30 * Grid.xSz) CrosswordSolve.this.w = 30 * Grid.xSz; 
/*  135 */               if (CrosswordSolve.this.h < 30 * Grid.ySz) CrosswordSolve.this.h = 30 * Grid.ySz; 
/*  136 */               if (CrosswordSolve.this.w < 600 || CrosswordSolve.this.h < 600) {
/*  137 */                 CrosswordSolve.this.w = 600; CrosswordSolve.this.h = 600;
/*      */               } 
/*      */             } else {
/*  140 */               CrosswordSolve.this.w = (CrosswordSolve.jfSolveCrossword.getWidth() < 500) ? 500 : CrosswordSolve.jfSolveCrossword.getWidth();
/*  141 */               CrosswordSolve.this.h = (CrosswordSolve.jfSolveCrossword.getHeight() < 650) ? 650 : CrosswordSolve.jfSolveCrossword.getHeight();
/*      */             } 
/*  143 */             CrosswordSolve.jfSolveCrossword.setSize(CrosswordSolve.this.w, CrosswordSolve.this.h);
/*  144 */             Op.setInt(Op.CW.CwSolveW.ordinal(), CrosswordSolve.this.w, Op.cw);
/*  145 */             Op.setInt(Op.CW.CwSolveH.ordinal(), CrosswordSolve.this.h, Op.cw);
/*  146 */             CrosswordSolve.restoreFrame();
/*      */           }
/*      */         });
/*      */     
/*  150 */     jfSolveCrossword
/*  151 */       .addWindowListener(new WindowAdapter() {
/*      */           public void windowClosing(WindowEvent we) {
/*  153 */             if (Def.selecting)
/*  154 */               return;  CrosswordSolve.this.restoreIfDone();
/*  155 */             CrosswordSolve.saveCrossword(Op.cw[Op.CW.CwSolvePuz.ordinal()]);
/*  156 */             if (Def.prevMode == 4) {
/*  157 */               Op.cw[CrosswordBuild.dicList[0]] = Op.cw[Op.CW.CwSolveDic.ordinal()];
/*  158 */               Op.cw[Op.CW.CwPuz.ordinal()] = Op.cw[Op.CW.CwSolvePuz.ordinal()];
/*  159 */               CrosswordExpress.transfer(4, CrosswordSolve.jfSolveCrossword);
/*      */             }
/*  161 */             else if (Def.prevMode == 6) {
/*  162 */               Op.ff[Op.FF.FfDic.ordinal()] = Op.cw[Op.CW.CwSolveDic.ordinal()];
/*  163 */               Op.ff[Op.FF.FfPuz.ordinal()] = Op.cw[Op.CW.CwSolvePuz.ordinal()];
/*  164 */               CrosswordExpress.transfer(6, CrosswordSolve.jfSolveCrossword);
/*      */             }
/*  166 */             else if (Def.prevMode == 210) {
/*  167 */               Op.sw[Op.SW.SwDic.ordinal()] = Op.cw[Op.CW.CwSolveDic.ordinal()];
/*  168 */               Op.sw[Op.SW.SwPuz.ordinal()] = Op.cw[Op.CW.CwSolvePuz.ordinal()];
/*  169 */               CrosswordExpress.transfer(210, CrosswordSolve.jfSolveCrossword);
/*      */             } 
/*  171 */             Def.dispWithShadow = Boolean.valueOf(false);
/*  172 */             Def.audienceMode = false;
/*      */           }
/*      */         });
/*      */     
/*  176 */     Methods.closeHelp();
/*      */     
/*  178 */     jl1 = new JLabel(); jfSolveCrossword.add(jl1);
/*  179 */     jl2 = new JLabel(); jfSolveCrossword.add(jl2);
/*      */ 
/*      */     
/*  182 */     menuBar = new JMenuBar();
/*  183 */     menuBar.setBackground(Def.COLOR_MENUBAR);
/*  184 */     jfSolveCrossword.setJMenuBar(menuBar);
/*  185 */     this.menu = new JMenu("File");
/*  186 */     menuBar.add(this.menu);
/*  187 */     this.menuItem = new JMenuItem("Select a Dictionary");
/*  188 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(68, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  189 */     this.menu.add(this.menuItem);
/*  190 */     this.menuItem
/*  191 */       .addActionListener(ae -> {
/*      */           Methods.selectDictionary(jfSolveCrossword, Op.cw[Op.CW.CwSolveDic.ordinal()], 3);
/*      */           
/*      */           if (!Methods.fileAvailable(Methods.dictionaryName + ".dic", "crossword")) {
/*      */             JOptionPane.showMessageDialog(jfSolveCrossword, "<html>No Crossword puzzles are available in this dictionary.<br>Use the <font color=880000>Build</font> option to create one.");
/*      */             
/*      */             return;
/*      */           } 
/*      */           
/*      */           restoreIfDone();
/*      */           
/*      */           saveCrossword(Op.cw[Op.CW.CwSolvePuz.ordinal()]);
/*      */           
/*      */           Op.cw[Op.CW.CwSolveDic.ordinal()] = Methods.dictionaryName;
/*      */           
/*      */           loadSolvePuzzle();
/*      */           restoreFrame();
/*      */         });
/*  209 */     this.menuItem = new JMenuItem("Load a Puzzle");
/*  210 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(76, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  211 */     this.menu.add(this.menuItem);
/*  212 */     this.menuItem
/*  213 */       .addActionListener(ae -> {
/*      */           restoreIfDone();
/*      */           
/*      */           saveCrossword(Op.cw[Op.CW.CwSolvePuz.ordinal()]);
/*      */           
/*      */           new Select(jfSolveCrossword, Op.cw[Op.CW.CwSolveDic.ordinal()] + ".dic", "crossword", Op.cw, Op.CW.CwSolvePuz.ordinal(), false);
/*      */         });
/*  220 */     this.menuItem = new JMenuItem("Quit Solving");
/*  221 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(81, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  222 */     this.menu.add(this.menuItem);
/*  223 */     this.menuItem
/*  224 */       .addActionListener(ae -> {
/*      */           restoreIfDone();
/*      */           
/*      */           saveCrossword(Op.cw[Op.CW.CwSolvePuz.ordinal()]);
/*      */           
/*      */           if (Def.prevMode == 4) {
/*      */             Op.cw[CrosswordBuild.dicList[0]] = Op.cw[Op.CW.CwSolveDic.ordinal()];
/*      */             
/*      */             Op.cw[Op.CW.CwPuz.ordinal()] = Op.cw[Op.CW.CwSolvePuz.ordinal()];
/*      */             
/*      */             CrosswordExpress.transfer(4, jfSolveCrossword);
/*      */           } else if (Def.prevMode == 6) {
/*      */             Op.ff[Op.FF.FfDic.ordinal()] = Op.cw[Op.CW.CwSolveDic.ordinal()];
/*      */             
/*      */             Op.ff[Op.FF.FfPuz.ordinal()] = Op.cw[Op.CW.CwSolvePuz.ordinal()];
/*      */             CrosswordExpress.transfer(6, jfSolveCrossword);
/*      */           } else if (Def.prevMode == 210) {
/*      */             Op.sw[Op.SW.SwDic.ordinal()] = Op.cw[Op.CW.CwSolveDic.ordinal()];
/*      */             Op.sw[Op.SW.SwPuz.ordinal()] = Op.cw[Op.CW.CwSolvePuz.ordinal()];
/*      */             CrosswordExpress.transfer(210, jfSolveCrossword);
/*      */           } 
/*      */           Def.audienceMode = false;
/*      */         });
/*  247 */     this.menu = new JMenu("View");
/*  248 */     menuBar.add(this.menu);
/*  249 */     this.menuItem = new JMenuItem("Display Options");
/*  250 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(89, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  251 */     this.menu.add(this.menuItem);
/*  252 */     this.menuItem
/*  253 */       .addActionListener(ae -> {
/*      */           FreeformBuild.printOptions(jfSolveCrossword, "Display Options");
/*      */ 
/*      */           
/*      */           restoreFrame();
/*      */         });
/*      */     
/*  260 */     this.menu = new JMenu("Tasks");
/*  261 */     menuBar.add(this.menu);
/*  262 */     this.menuItem = new JMenuItem("Reveal One Letter");
/*  263 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(79, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  264 */     this.menu.add(this.menuItem);
/*  265 */     this.menuItem
/*  266 */       .addActionListener(ae -> {
/*      */           if (Methods.noReveal == 0) {
/*      */             Grid.sol[Grid.xCur][Grid.yCur] = Grid.letter[Grid.xCur][Grid.yCur];
/*      */           } else {
/*      */             Methods.noReveal(jfSolveCrossword);
/*      */           } 
/*      */           
/*      */           nextLocation(1);
/*      */           restoreFrame();
/*      */         });
/*  276 */     this.menuItem = new JMenuItem("Reveal One Word");
/*  277 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(87, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  278 */     this.menu.add(this.menuItem);
/*  279 */     this.menuItem
/*  280 */       .addActionListener(ae -> {
/*      */           if (Methods.noReveal == 0) {
/*      */             for (int i = 0; i < (NodeList.nodeList[Grid.nCur]).word.length(); i++) {
/*      */               int x = ((NodeList.nodeList[Grid.nCur]).cellLoc[i]).x;
/*      */               
/*      */               int y = ((NodeList.nodeList[Grid.nCur]).cellLoc[i]).y;
/*      */               
/*      */               Grid.sol[x][y] = Grid.letter[x][y];
/*      */             } 
/*      */           } else {
/*      */             Methods.noReveal(jfSolveCrossword);
/*      */           } 
/*      */           
/*      */           restoreFrame();
/*      */         });
/*  295 */     ActionListener errorTimer = ae -> {
/*      */         Def.dispErrors = Boolean.valueOf(false);
/*      */         restoreFrame();
/*      */         this.myTimer.stop();
/*      */       };
/*  300 */     this.myTimer = new Timer(1500, errorTimer);
/*      */     
/*  302 */     this.menuItem = new JMenuItem("Google It");
/*  303 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(71, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  304 */     this.menu.add(this.menuItem);
/*  305 */     this.menuItem
/*  306 */       .addActionListener(ae -> {
/*      */           String url = "http://www.google.com/search?q=" + (NodeList.nodeList[Grid.nCur]).clue;
/*      */           
/*      */           url = url.replace(' ', '+');
/*      */           
/*      */           browserLaunch.openURL(jfSolveCrossword, url);
/*      */           Methods.toFront(paramJFrame);
/*      */         });
/*  314 */     this.menuItem = new JMenuItem("Reveal Errors");
/*  315 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(69, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  316 */     this.menu.add(this.menuItem);
/*  317 */     this.menuItem
/*  318 */       .addActionListener(ae -> {
/*      */           if (Methods.noErrors == 0) {
/*      */             this.myTimer.start();
/*      */             
/*      */             Def.dispErrors = Boolean.valueOf(true);
/*      */           } else {
/*      */             Methods.noReveal(jfSolveCrossword);
/*      */           } 
/*      */           
/*      */           restoreFrame();
/*      */         });
/*  329 */     this.menuItem = new JMenuItem("Reveal Solution");
/*  330 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(83, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  331 */     this.menu.add(this.menuItem);
/*  332 */     this.menuItem
/*  333 */       .addActionListener(ae -> {
/*      */           if (Methods.noReveal == 0) {
/*      */             for (int j = 0; j < Grid.ySz; j++) {
/*      */               for (int i = 0; i < Grid.xSz; i++)
/*      */                 Grid.sol[i][j] = Grid.letter[i][j]; 
/*      */             } 
/*      */           } else {
/*      */             Methods.noReveal(jfSolveCrossword);
/*      */           } 
/*      */           restoreFrame();
/*      */         });
/*  344 */     this.menuItem = new JMenuItem("Begin Again");
/*  345 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(66, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  346 */     this.menu.add(this.menuItem);
/*  347 */     this.menuItem
/*  348 */       .addActionListener(ae -> {
/*      */           for (int j = 0; j < Grid.ySz; j++) {
/*      */             for (int i = 0; i < Grid.xSz; i++) {
/*      */               Grid.sol[i][j] = 0;
/*      */             }
/*      */           } 
/*      */           
/*      */           restoreFrame();
/*      */         });
/*  357 */     this.menu = new JMenu("Help");
/*  358 */     menuBar.add(this.menu);
/*  359 */     this.menuItem = new JMenuItem("Crossword Help");
/*  360 */     this.menu.add(this.menuItem);
/*  361 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(72, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  362 */     this.menuItem
/*  363 */       .addActionListener(ae -> Methods.cweHelp(jfSolveCrossword, null, "Solving a Crossword Puzzle", this.crosswordSolveHelp));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  369 */     this.solveThread = (() -> {
/*      */         for (int j = 0; j < Grid.ySz; j++) {
/*      */           for (int i = 0; i < Grid.xSz; i++) {
/*      */             if (Grid.sol[i][j] != Grid.letter[i][j])
/*      */               return; 
/*      */           } 
/*      */         }  Methods.congratulations(jfSolveCrossword);
/*      */       });
/*  377 */     pp = new CrosswordSolvePP(0, 0, jfSolveCrossword);
/*      */     
/*  379 */     pp
/*  380 */       .addMouseListener(new MouseAdapter() {
/*      */           public void mouseReleased(MouseEvent e) {
/*  382 */             CrosswordSolve.this.updateGrid(e);
/*      */           }
/*      */         });
/*      */ 
/*      */     
/*  387 */     pp
/*  388 */       .addMouseMotionListener(new MouseAdapter() {
/*      */           public void mouseMoved(MouseEvent e) {
/*  390 */             if (Def.isMac) {
/*  391 */               CrosswordSolve.jfSolveCrossword.setResizable((CrosswordSolve.jfSolveCrossword.getWidth() - e.getX() < 15 && CrosswordSolve.jfSolveCrossword
/*  392 */                   .getHeight() - e.getY() < 95));
/*      */             }
/*      */           }
/*      */         });
/*      */     
/*  397 */     jfSolveCrossword
/*  398 */       .addKeyListener(new KeyAdapter() {
/*      */           public void keyPressed(KeyEvent e) {
/*  400 */             CrosswordSolve.this.handleKeyPressed(e);
/*      */           }
/*      */         });
/*      */ 
/*      */     
/*  405 */     ccDat = new ccSpec[50];
/*  406 */     for (int i = 0; i < 50; i++) {
/*  407 */       ccDat[i] = new ccSpec();
/*      */     }
/*  409 */     loadSolvePuzzle();
/*  410 */     restoreFrame();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void restoreFrame() {
/*  417 */     jfSolveCrossword.setVisible(true);
/*  418 */     Insets insets = jfSolveCrossword.getInsets();
/*  419 */     int wDec = insets.left + insets.right;
/*  420 */     int hDec = insets.top + insets.bottom + 37 + menuBar.getHeight();
/*  421 */     width = jfSolveCrossword.getWidth() - wDec;
/*  422 */     height = jfSolveCrossword.getHeight() - hDec;
/*  423 */     jfSolveCrossword.setSize(width + wDec, height + hDec);
/*  424 */     pp.setSize(width, height);
/*  425 */     pp.setLocation(0, 37);
/*      */     
/*  427 */     tSz = width * height;
/*  428 */     pSz = tSz * 4 / 10;
/*  429 */     double theCell = Math.sqrt((pSz / Grid.xSz * Grid.ySz));
/*  430 */     if (theCell * Grid.xSz >= (width * 4 / 5)) {
/*  431 */       theCell = width / Grid.xSz;
/*  432 */     } else if (theCell * Grid.ySz >= (height * 4 / 5)) {
/*  433 */       theCell = height / Grid.ySz;
/*  434 */     }  Grid.xCell = Grid.yCell = (int)Math.round(theCell);
/*  435 */     puzW = Grid.xCell * Grid.xSz;
/*  436 */     puzH = Grid.yCell * Grid.ySz;
/*      */     
/*  438 */     jfSolveCrossword.requestFocusInWindow();
/*  439 */     pp.repaint();
/*  440 */     Methods.infoPanel(jl1, jl2, "Solve Crossword", "Dictionary : " + Op.cw[Op.CW.CwSolveDic.ordinal()] + "  -|-  Puzzle : " + Op.cw[Op.CW.CwSolvePuz
/*  441 */           .ordinal()], jfSolveCrossword.getWidth());
/*      */   }
/*      */ 
/*      */   
/*      */   static void setSizesAndOffsets(int x, int y, int w, int h, int inset) {
/*  446 */     if (Def.audienceMode && !Def.selecting) {
/*  447 */       Insets insets = jfSolveCrossword.getInsets();
/*  448 */       int i = (jfSolveCrossword.getWidth() - 20 - insets.left - insets.right) / Grid.xSz;
/*  449 */       int j = (jfSolveCrossword.getHeight() - 200 - insets.top - insets.bottom) / Grid.ySz;
/*  450 */       Grid.xCell = Grid.yCell = (i < j) ? i : j;
/*  451 */       Grid.xOrg = (jfSolveCrossword.getWidth() - insets.left - insets.right - Grid.xSz * Grid.xCell) / 2;
/*  452 */       Grid.yOrg = 10;
/*      */     } else {
/*      */       
/*  455 */       int i = (w - inset) / Grid.xSz;
/*  456 */       int j = (h - inset) / Grid.ySz;
/*  457 */       Grid.xCell = Grid.yCell = (i < j) ? i : j;
/*  458 */       Grid.xOrg = (w == h) ? (x + (w - Grid.xSz * Grid.xCell) / 2) : 10;
/*  459 */       Grid.yOrg = (w == h) ? (y + (h - Grid.ySz * Grid.yCell) / 2) : 10;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void saveCrossword(String crosswordName) {
/*      */     try {
/*  468 */       DataOutputStream dataOut = new DataOutputStream(new FileOutputStream(Op.cw[Op.CW.CwSolveDic.ordinal()] + ".dic/" + crosswordName));
/*  469 */       dataOut.writeInt(Grid.xSz);
/*  470 */       dataOut.writeInt(Grid.ySz);
/*  471 */       dataOut.writeByte(Methods.noReveal);
/*  472 */       dataOut.writeByte(Methods.noErrors); int i;
/*  473 */       for (i = 0; i < 54; i++)
/*  474 */         dataOut.writeByte(0); 
/*  475 */       for (int j = 0; j < Grid.ySz; j++) {
/*  476 */         for (i = 0; i < Grid.xSz; i++) {
/*  477 */           dataOut.writeInt(Grid.mode[i][j]);
/*  478 */           dataOut.writeInt(Grid.letter[i][j]);
/*  479 */           dataOut.writeInt(Grid.sol[i][j]);
/*  480 */           dataOut.writeInt(Grid.color[i][j]);
/*      */         } 
/*  482 */       }  dataOut.writeUTF(Methods.puzzleTitle);
/*  483 */       dataOut.writeUTF(Methods.author);
/*  484 */       dataOut.writeUTF(Methods.copyright);
/*  485 */       dataOut.writeUTF(Methods.puzzleNumber);
/*  486 */       dataOut.writeUTF(Methods.puzzleNotes);
/*      */       
/*  488 */       for (i = 0; i < NodeList.nodeListLength; i++) {
/*  489 */         dataOut.writeUTF((NodeList.nodeList[i]).word);
/*  490 */         dataOut.writeUTF((NodeList.nodeList[i]).clue);
/*      */       } 
/*      */       
/*  493 */       dataOut.close();
/*      */     }
/*  495 */     catch (IOException exc) {}
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static void loadCrossword(String crosswordName) {
/*  501 */     char v = Character.MIN_VALUE;
/*  502 */     String ss = "";
/*      */     
/*  504 */     File fl = new File(Op.cw[Op.CW.CwSolveDic.ordinal()] + ".dic/" + crosswordName);
/*  505 */     if (!fl.exists()) {
/*  506 */       fl = new File(Op.cw[Op.CW.CwSolveDic.ordinal()] + ".dic/");
/*  507 */       String[] s = fl.list(); int i;
/*  508 */       for (i = 0; i < s.length && (
/*  509 */         s[i].lastIndexOf(".crossword") == -1 || s[i].charAt(0) == '.'); i++);
/*      */       
/*  511 */       crosswordName = s[i];
/*  512 */       Op.cw[Op.CW.CwSolvePuz.ordinal()] = crosswordName;
/*      */     } 
/*      */     
/*      */     try {
/*  516 */       DataInputStream dataIn = new DataInputStream(new FileInputStream(Op.cw[Op.CW.CwSolveDic.ordinal()] + ".dic/" + crosswordName));
/*  517 */       Grid.xSz = dataIn.readInt();
/*  518 */       Grid.ySz = dataIn.readInt();
/*  519 */       Methods.noReveal = dataIn.readByte();
/*  520 */       Methods.noErrors = dataIn.readByte(); int i;
/*  521 */       for (i = 0; i < 54; i++)
/*  522 */         dataIn.readByte(); 
/*  523 */       for (int j = 0; j < Grid.ySz; j++) {
/*  524 */         for (i = 0; i < Grid.xSz; i++) {
/*  525 */           Grid.mode[i][j] = dataIn.readInt();
/*  526 */           Grid.letter[i][j] = dataIn.readInt();
/*  527 */           Grid.sol[i][j] = dataIn.readInt();
/*  528 */           Grid.color[i][j] = dataIn.readInt();
/*      */         } 
/*  530 */       }  Methods.puzzleTitle = dataIn.readUTF();
/*  531 */       Methods.author = dataIn.readUTF();
/*  532 */       Methods.copyright = dataIn.readUTF();
/*  533 */       Methods.puzzleNumber = dataIn.readUTF();
/*  534 */       Methods.puzzleNotes = dataIn.readUTF();
/*      */       
/*  536 */       NodeList.buildNodeList();
/*  537 */       for (i = 0; i < NodeList.nodeListLength; i++) {
/*  538 */         (NodeList.nodeList[i]).word = dataIn.readUTF();
/*  539 */         (NodeList.nodeList[i]).clue = dataIn.readUTF();
/*  540 */         if ((NodeList.nodeList[i]).clue.length() < 2)
/*  541 */           (NodeList.nodeList[i]).clue = "No clue"; 
/*  542 */         (NodeList.nodeList[i]).clue = removeStyle((NodeList.nodeList[i]).clue);
/*      */       } 
/*  544 */       dataIn.close();
/*      */     }
/*  546 */     catch (IOException exc) {}
/*      */     
/*  548 */     Grid.xCur = (NodeList.nodeList[0]).x;
/*  549 */     Grid.yCur = (NodeList.nodeList[0]).y;
/*  550 */     Methods.havePuzzle = true;
/*      */   }
/*      */   
/*      */   static String removeStyle(String str) {
/*  554 */     String st = "";
/*      */ 
/*      */     
/*  557 */     boolean copy = true;
/*      */     
/*  559 */     if (!str.contains("<")) return str; 
/*  560 */     for (int i = 0; i < str.length(); i++) {
/*  561 */       char ch = str.charAt(i);
/*  562 */       if (ch == '<') { copy = false; }
/*  563 */       else if (ch == '>') { copy = true; }
/*  564 */       else if (copy) { st = st + ch; }
/*      */     
/*  566 */     }  return st;
/*      */   }
/*      */   
/*      */   static void drawCrossword(Graphics2D g2)
/*      */   {
/*  571 */     int ccMax = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  576 */     R2LClue = (DictionaryMtce.dicHeader[DictionaryMtce.R2L_CLUE] == 1);
/*  577 */     R2LWord = (DictionaryMtce.dicHeader[DictionaryMtce.R2L_WORD] == 1);
/*      */     
/*  579 */     if (Def.puzzleMode == 5) {
/*  580 */       g2.setColor(Def.COLOR_WHITE);
/*  581 */       g2.fillRect(0, 0, jfSolveCrossword.getWidth(), jfSolveCrossword.getHeight());
/*      */     } 
/*      */     
/*  584 */     int nL = (int)Math.ceil((Grid.xCell / 60.0F)), wL = (int)Math.ceil((Grid.xCell / 10.0F));
/*  585 */     Stroke normalStroke = new BasicStroke(nL, 2, 0);
/*  586 */     Stroke wideStroke = new BasicStroke(wL, 0, 0);
/*  587 */     g2.setStroke(normalStroke);
/*      */     
/*  589 */     RenderingHints rh = g2.getRenderingHints();
/*  590 */     rh.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
/*  591 */     g2.setRenderingHints(rh);
/*      */ 
/*      */     
/*  594 */     if (Def.puzzleMode == 12) {
/*  595 */       g2.setColor(Def.dispWithColor.booleanValue() ? new Color(Op.getColorInt(Op.CW.CwLettersC.ordinal(), Op.cw)) : Def.COLOR_BLACK);
/*  596 */       g2.setFont(new Font(Op.cw[Op.CW.CwFont.ordinal()], 0, 6 * Grid.yCell / 10));
/*  597 */       FontMetrics fontMetrics = g2.getFontMetrics(); int i;
/*  598 */       for (i = 1; i <= Grid.xSz; i++) {
/*  599 */         String str; int w = fontMetrics.stringWidth(str = !Op.getBool(Op.CW.CwFrenchAcrossId.ordinal(), Op.cw).booleanValue() ? ("" + (char)(64 + i)) : ("" + i));
/*  600 */         g2.drawString(str, Grid.xOrg + (i - 1) * Grid.xCell + (Grid.xCell - w) / 2, Grid.yOrg - Grid.yCell + 8 * Grid.yCell / 10);
/*      */       } 
/*  602 */       for (i = 1; i <= Grid.ySz; i++) {
/*  603 */         String str; int w = fontMetrics.stringWidth(str = !Op.getBool(Op.CW.CwFrenchDownId.ordinal(), Op.cw).booleanValue() ? ("" + (char)(64 + i)) : ("" + i));
/*  604 */         g2.drawString(str, Grid.xOrg - Grid.xCell + (Grid.xCell - w) / 2, Grid.yOrg + (i - 1) * Grid.yCell + 8 * Grid.yCell / 10);
/*      */       } 
/*      */     } 
/*      */     
/*  608 */     if (Def.prevMode == 6)
/*      */     {
/*  610 */       if (FreeformBuild.thePic.length() > 0)
/*  611 */         try { BufferedImage img = ImageIO.read(new File(FreeformBuild.thePic));
/*  612 */           g2.drawImage(img, Grid.xOrg, Grid.yOrg, Grid.xCell * Grid.xSz + Grid.xCell / 6, Grid.yCell * Grid.ySz + Grid.yCell / 6, null);
/*  613 */           g2.drawRect(Grid.xOrg, Grid.yOrg, Grid.xCell * Grid.xSz + Grid.xCell / 6, Grid.yCell * Grid.ySz + Grid.yCell / 6); }
/*      */         
/*  615 */         catch (IOException e) {} 
/*      */     }
/*      */     int j;
/*  618 */     for (j = 0; j < Grid.ySz; j++) {
/*  619 */       for (int i = 0; i < Grid.xSz; i++) {
/*  620 */         if (Grid.mode[i][j] != 2 && 
/*  621 */           Grid.mode[i][j] != 1) {
/*  622 */           int theColor; if (Def.dispWithShadow.booleanValue()) {
/*  623 */             g2.setColor(new Color(Op.getColorInt(Op.CW.CwShadowC.ordinal(), Op.cw)));
/*  624 */             g2.fillRect(Grid.xOrg + i * Grid.xCell + ((Grid.xCell / 7 > 1) ? (Grid.xCell / 7) : 1), Grid.yOrg + j * Grid.yCell + ((Grid.yCell / 7 > 1) ? (Grid.yCell / 7) : 1), Grid.xCell, Grid.yCell);
/*      */           } 
/*  626 */           if (Def.dispWithColor.booleanValue()) {
/*  627 */             if (Grid.color[i][j] != 16777215) {
/*  628 */               theColor = Grid.color[i][j];
/*      */             } else {
/*  630 */               theColor = Op.getColorInt(Op.CW.CwCellC.ordinal(), Op.cw);
/*  631 */             }  if (Def.dispCursor.booleanValue() && (
/*  632 */               Grid.horz[i][j] == Grid.nCur || Grid.vert[i][j] == Grid.nCur))
/*  633 */               theColor = 61132; 
/*  634 */             if (Def.dispErrors.booleanValue() && Grid.sol[i][j] != 0 && Grid.sol[i][j] != Grid.letter[i][j] && Grid.sol[i][j] != 32) {
/*  635 */               theColor = Op.getColorInt(Op.CW.CwErrorC.ordinal(), Op.cw);
/*      */             }
/*      */           } else {
/*  638 */             theColor = 16579836;
/*  639 */           }  g2.setColor(new Color(theColor));
/*  640 */           int drawI = Grid.RorL(i);
/*  641 */           g2.fillRect(Grid.xOrg + drawI * Grid.xCell, Grid.yOrg + j * Grid.yCell, Grid.xCell, Grid.yCell);
/*      */         } 
/*      */       } 
/*      */     } 
/*  645 */     if (!Def.dispWithShadow.booleanValue()) {
/*  646 */       if (Def.dispWithColor.booleanValue()) {
/*  647 */         Grid.drawPatternCells(g2, new Color(Op.getColorInt(Op.CW.CwPatternC.ordinal(), Op.cw)), new Color(
/*  648 */               Op.getColorInt(Op.CW.CwCellC.ordinal(), Op.cw)), false);
/*      */       } else {
/*  650 */         Grid.drawPatternCells(g2, Def.COLOR_BLACK, Def.COLOR_WHITE, false);
/*  651 */       }  Grid.drawOutline(g2, true);
/*      */     } 
/*      */ 
/*      */     
/*  655 */     g2.setColor(Def.dispWithColor.booleanValue() ? new Color(Op.getColorInt(Op.CW.CwGridC.ordinal(), Op.cw)) : Def.COLOR_BLACK);
/*  656 */     for (j = 0; j < Grid.ySz; j++) {
/*  657 */       for (int i = 0; i < Grid.xSz; i++) {
/*  658 */         if (Grid.mode[i][j] != 2 && (Grid.mode[i][j] != 1 || !Def.dispWithShadow.booleanValue())) {
/*  659 */           int drawI = Grid.RorL(i);
/*  660 */           g2.drawRect(Grid.xOrg + drawI * Grid.xCell, Grid.yOrg + j * Grid.yCell, Grid.xCell, Grid.yCell);
/*      */         } 
/*      */       } 
/*      */     } 
/*  664 */     g2.setColor(Def.dispWithColor.booleanValue() ? new Color(Op.getInt(Op.CD.CdLetters.ordinal(), Op.cd)) : Def.COLOR_BLACK);
/*  665 */     g2.setFont(new Font(Op.cw[Op.CW.CwFont.ordinal()], 0, 8 * Grid.yCell / 10));
/*  666 */     FontMetrics fm = g2.getFontMetrics();
/*  667 */     for (j = 0; j < Grid.ySz; j++) {
/*  668 */       for (int i = 0; i < Grid.xSz; i++) {
/*  669 */         char ch = (char)Grid.sol[i][j];
/*  670 */         if (ch != '\000') {
/*  671 */           int w = fm.stringWidth("" + ch);
/*  672 */           int drawI = Grid.RorL(i);
/*  673 */           g2.drawString("" + ch, Grid.xOrg + drawI * Grid.xCell + (Grid.xCell - w) / 2, Grid.yOrg + j * Grid.yCell + 9 * Grid.yCell / 10);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  678 */     g2.setStroke(wideStroke);
/*  679 */     Grid.drawBars(g2);
/*      */ 
/*      */     
/*  682 */     if (Def.dispCursor.booleanValue()) {
/*  683 */       g2.setColor(Def.COLOR_RED);
/*  684 */       g2.setStroke(wideStroke);
/*  685 */       g2.drawRect(Grid.xOrg + Grid.RorL(Grid.xCur) * Grid.xCell, Grid.yOrg + Grid.yCur * Grid.yCell, Grid.xCell, Grid.yCell);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  690 */     int[] wl = new int[10];
/*      */     
/*  692 */     if (Op.getBool(Op.CW.CwCrosswordSep.ordinal(), Op.cw).booleanValue()) {
/*  693 */       for (int i = 0; i < NodeList.nodeListLength; i++) {
/*  694 */         j = (NodeList.nodeList[i]).clue.lastIndexOf('(');
/*  695 */         if (j != -1) {
/*  696 */           String str = (NodeList.nodeList[i]).clue.substring(j + 1, (NodeList.nodeList[i]).clue.length() - 1);
/*  697 */           for (j = 0; j < 10; ) { wl[j] = 0; j++; }
/*      */           
/*  699 */           for (int k = 0; k < str.length(); k++) {
/*  700 */             int v = Character.getNumericValue(str.charAt(k));
/*  701 */             if (v >= 0 && v <= 9) {
/*  702 */               wl[j] = wl[j] * 10 + v;
/*      */             } else {
/*  704 */               if (j > 0) wl[j] = wl[j] + wl[j - 1]; 
/*  705 */               j++;
/*      */             } 
/*      */           } 
/*      */           
/*  709 */           for (j = 0; j < 10 && 
/*  710 */             wl[j + 1] != 0; j++) {
/*  711 */             int x = ((NodeList.nodeList[i]).cellLoc[wl[j] - 1]).x;
/*  712 */             int y = ((NodeList.nodeList[i]).cellLoc[wl[j] - 1]).y;
/*  713 */             int right = Grid.xOrg + (x + 1) * Grid.xCell;
/*  714 */             int bottom = Grid.yOrg + (y + 1) * Grid.yCell;
/*  715 */             if (((NodeList.nodeList[i]).cellLoc[wl[j]]).y == y) {
/*  716 */               g2.drawLine(right, bottom - Grid.yCell, right, bottom);
/*      */             } else {
/*  718 */               g2.drawLine(right - Grid.xCell, bottom, right, bottom);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*  724 */     if (Def.dispGuideDigits.booleanValue() && Def.puzzleMode != 12) {
/*  725 */       g2.setFont(new Font(Op.cw[Op.CW.CwIDFont.ordinal()], 0, Grid.yCell / 3));
/*  726 */       g2.setColor(Def.dispWithColor.booleanValue() ? new Color(Op.getColorInt(Op.CW.CwIDC.ordinal(), Op.cw)) : Def.COLOR_BLACK);
/*  727 */       fm = g2.getFontMetrics();
/*  728 */       for (int i = 0; NodeList.nodeList[i] != null; i++) {
/*  729 */         int drawI = Grid.RorL((NodeList.nodeList[i]).x);
/*      */         
/*  731 */         int xCoord = Grid.xOrg + drawI * Grid.xCell + ((DictionaryMtce.dicHeader[DictionaryMtce.R2L_WORD] == 1) ? (Grid.xCell - fm.stringWidth("" + (NodeList.nodeList[i]).id) - 1) : ((Grid.xCell / 20 > 1) ? (Grid.xCell / 20) : 1));
/*      */         
/*  733 */         g2.drawString("" + (NodeList.nodeList[i]).id, xCoord, Grid.yOrg + (NodeList.nodeList[i]).y * Grid.yCell + fm.getAscent());
/*      */       } 
/*      */     } 
/*      */     
/*  737 */     if (Def.audienceMode && !Def.selecting && Def.puzzleMode == 5)
/*      */     
/*      */     { 
/*      */ 
/*      */       
/*  742 */       int clueFontSize = 45;
/*  743 */       g2.setFont(new Font(Op.cw[Op.CW.CwClueFont.ordinal()], 1, clueFontSize));
/*  744 */       String str = (NodeList.nodeList[Grid.nCur]).clue;
/*  745 */       fm = g2.getFontMetrics();
/*  746 */       int w = jfSolveCrossword.getWidth();
/*  747 */       boolean printIt = false; int yy = 20 + Grid.yCell * Grid.ySz; while (true) { while (true)
/*  748 */         { if (fm.stringWidth(str) <= w - 30) {
/*  749 */             yy += clueFontSize;
/*  750 */             if (printIt) {
/*  751 */               g2.drawString(str, (w - fm.stringWidth(str)) / 2, yy);
/*      */             } else {
/*      */               
/*  754 */               printIt = true;
/*  755 */               yy = 20 + Grid.yCell * Grid.ySz;
/*  756 */               str = (NodeList.nodeList[Grid.nCur]).clue;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*      */               continue;
/*      */             } 
/*      */           } else {
/*      */             break;
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  780 */           g2.setStroke(new BasicStroke(1.0F)); return; }  int cut = str.length(); for (; --cut > 0; cut--) { if (str.charAt(cut) == ' ') { String str2 = str.substring(0, cut); if (fm.stringWidth(str2) <= w - 30) { yy += clueFontSize; if (printIt)
/*      */                 g2.drawString(str2, (w - fm.stringWidth(str2)) / 2, yy);  str = str.substring(cut + 1); break; }  }  }  if (cut == 0 || yy + 2 * clueFontSize > jfSolveCrossword.getHeight() - 50) { yy = 20 + Grid.xCell * Grid.xSz; clueFontSize--; g2.setFont(new Font(Op.cw[Op.CW.CwClueFont.ordinal()], 1, clueFontSize)); fm = g2.getFontMetrics(); str = (NodeList.nodeList[Grid.nCur]).clue; }
/*      */          }
/*      */        }
/*  784 */     else { if (!Def.selecting && Def.puzzleMode == 5) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  791 */         Color clueC = new Color(0), curClueC = new Color(17476);
/*  792 */         Color clueBgC = new Color(61132), clueOlC = new Color(0);
/*      */         
/*      */         int i;
/*      */         
/*  796 */         for (i = 0; i < 50; i++) {
/*  797 */           (ccDat[i]).w = 0;
/*      */         }
/*  799 */         int ccNum = 0;
/*  800 */         if (height - 10 - Grid.ySz * Grid.yCell > 50) {
/*  801 */           int k = Grid.xSz * Grid.xCell + 10;
/*  802 */           i = ccMax = 2;
/*  803 */           int ccw = k / i;
/*  804 */           for (; ccNum < i; ccNum++) {
/*  805 */             (ccDat[ccNum]).x = Grid.xOrg + ccNum * ccw;
/*  806 */             (ccDat[ccNum]).y = Grid.yOrg + Grid.ySz * Grid.yCell;
/*  807 */             (ccDat[ccNum]).w = ccw;
/*      */           } 
/*      */         } 
/*      */         
/*  811 */         int cw = width - Grid.xSz * Grid.xCell - 30;
/*  812 */         if (cw > 50) {
/*  813 */           i = 2;
/*  814 */           if (i > 0) {
/*  815 */             ccMax += i;
/*  816 */             int ccw = cw / i;
/*  817 */             for (j = 0; ccNum < 50; j++) {
/*  818 */               (ccDat[ccNum]).x = Grid.xOrg + Grid.xSz * Grid.xCell + 10 + j * ccw;
/*  819 */               (ccDat[ccNum]).y = 0;
/*  820 */               (ccDat[ccNum++]).w = ccw;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/*  826 */         int ccMin = (((ccDat[0]).w < (ccDat[49]).w || (ccDat[49]).w == 0) ? (ccDat[0]).w : (ccDat[49]).w) - 1;
/*  827 */         int clueFontSize = 150; int z;
/*  828 */         for (z = 0; z < NodeList.nodeListLength; z++) {
/*  829 */           String idStr = (NodeList.nodeList[z]).id + ". ";
/*  830 */           g2.setFont(new Font(Op.cw[Op.CW.CwClueFont.ordinal()], 1, clueFontSize));
/*  831 */           fm = g2.getFontMetrics();
/*  832 */           int inset = fm.stringWidth(idStr);
/*  833 */           String str = (NodeList.nodeList[z]).clue;
/*  834 */           for (i = 0, j = 1; j <= str.length(); j++) {
/*  835 */             if (j == str.length() || str.charAt(j) == ' ') {
/*  836 */               String str2 = str.substring(i, j);
/*  837 */               i = j + 1;
/*  838 */               while (fm.stringWidth(str2) >= ccMin - inset - 10) {
/*  839 */                 clueFontSize--;
/*  840 */                 g2.setFont(new Font(Op.cw[Op.CW.CwClueFont.ordinal()], 0, clueFontSize));
/*  841 */                 fm = g2.getFontMetrics();
/*  842 */                 inset = fm.stringWidth(idStr);
/*      */               } 
/*      */             } 
/*      */           } 
/*  846 */           if (str.charAt(j - 2) == ')') {
/*  847 */             for (j -= 2; str.charAt(j) != ' '; j--);
/*  848 */             for (; str.charAt(--j) != ' ' && j > 0; j--);
/*  849 */             String str2 = str.substring(j + 1);
/*  850 */             while (fm.stringWidth(str2) >= ccMin - inset - 10) {
/*  851 */               clueFontSize--;
/*  852 */               g2.setFont(new Font(Op.cw[Op.CW.CwClueFont.ordinal()], 0, clueFontSize));
/*  853 */               fm = g2.getFontMetrics();
/*  854 */               inset = fm.stringWidth(idStr);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */         
/*  859 */         int fontSp = clueFontSize;
/*  860 */         int cluB = height;
/*  861 */         g2.setColor(new Color(Op.CW.AwClue.ordinal()));
/*  862 */         fm = g2.getFontMetrics();
/*  863 */         int printSt = 0; while (true) {
/*  864 */           ccNum = 0;
/*  865 */           int colW = (ccDat[ccNum]).w;
/*  866 */           int ccTop = Grid.yOrg + Grid.ySz * Grid.yCell;
/*      */           
/*  868 */           int lY = (ccDat[ccNum]).y;
/*  869 */           if ((ccDat[ccNum]).y < ccTop) lY += ccTop % fontSp; 
/*  870 */           for (int lX = (ccDat[ccNum]).x, pass = 0; pass < 2; pass++) {
/*  871 */             if (pass == 0) {
/*  872 */               lY += fontSp;
/*      */             }
/*  874 */             else if (lY == (ccDat[ccNum]).y + ccTop % fontSp) {
/*  875 */               lY += fontSp;
/*      */             }
/*  877 */             else if (lY + 3 * fontSp < cluB - fm.getAscent()) {
/*  878 */               lY += 2 * fontSp;
/*      */             } else {
/*  880 */               lX = (ccDat[++ccNum]).x;
/*  881 */               lY = (ccDat[ccNum]).y + fontSp;
/*  882 */               if ((ccDat[ccNum]).y < ccTop) lY += ccTop % fontSp;
/*      */             
/*      */             } 
/*  885 */             g2.setFont(new Font(Op.cw[Op.CW.CwClueFont.ordinal()], 1, clueFontSize));
/*  886 */             fm = g2.getFontMetrics();
/*  887 */             if (printSt == 2) {
/*      */               
/*  889 */               String str = (pass == 0) ? ((Def.prevMode == 210) ? "CLUES" : Op.msc[Op.MSC.Langacross.ordinal()]) : Op.msc[Op.MSC.Langdown.ordinal()];
/*  890 */               int xCoord = lX + 5 + (R2LClue ? (colW - fm.stringWidth(str)) : 0);
/*  891 */               g2.drawString(str, xCoord, lY);
/*      */             } 
/*  893 */             for (z = 0; z < NodeList.nodeListLength; z++) {
/*  894 */               if (pass == (NodeList.nodeList[z]).direction)
/*  895 */               { if (z == Grid.nCur && printSt == 2) {
/*  896 */                   g2.setStroke(normalStroke);
/*  897 */                   int t = (NodeList.nodeList[z]).nlCt;
/*  898 */                   int l = (NodeList.nodeList[z]).nlCl;
/*  899 */                   int b = (NodeList.nodeList[z]).nlCb;
/*  900 */                   int r = (NodeList.nodeList[z]).nlCr;
/*  901 */                   int T = (NodeList.nodeList[z]).nlTop;
/*  902 */                   int B = (NodeList.nodeList[z]).nlBot;
/*  903 */                   int x1 = l, y1 = t, h1 = B - t, w1 = colW;
/*  904 */                   int x2 = l + colW, y2 = T + 2, h2 = b - T, w2 = (ccDat[ccNum + 1]).w;
/*  905 */                   if (b < t) {
/*  906 */                     g2.setColor(clueBgC);
/*  907 */                     g2.fillRect(x1, y1, w1, h1);
/*  908 */                     g2.fillRect(x2, y2, w2, h2);
/*  909 */                     g2.setColor(clueOlC);
/*  910 */                     g2.drawRect(x1, y1, w1, h1);
/*  911 */                     g2.drawRect(x2, y2, w2, h2);
/*      */                   } else {
/*      */                     
/*  914 */                     h1 = b - t;
/*  915 */                     g2.setColor(clueBgC);
/*  916 */                     g2.fillRect(x1, y1, w1, h1);
/*  917 */                     g2.setColor(clueOlC);
/*  918 */                     g2.drawRect(x1, y1, w1, h1);
/*      */                   } 
/*  920 */                   g2.setColor(curClueC);
/*      */                 } 
/*  922 */                 g2.setFont(new Font(Op.cw[Op.CW.CwClueFont.ordinal()], 1, clueFontSize));
/*  923 */                 fm = g2.getFontMetrics();
/*  924 */                 String str = R2LClue ? (" ." + (NodeList.nodeList[z]).id) : ((NodeList.nodeList[z]).id + ". ");
/*  925 */                 int inset = fm.stringWidth(str);
/*  926 */                 int xCoord = R2LClue ? (lX + colW - inset) : (lX + 5);
/*  927 */                 if (printSt == 2) g2.drawString(str, xCoord, lY + fontSp); 
/*  928 */                 g2.setFont(new Font(Op.cw[Op.CW.CwClueFont.ordinal()], 0, clueFontSize));
/*  929 */                 fm = g2.getFontMetrics();
/*  930 */                 str = (NodeList.nodeList[z]).clue;
/*  931 */                 (NodeList.nodeList[z]).nlCt = lY + fontSp - fm.getAscent();
/*  932 */                 (NodeList.nodeList[z]).nlCl = lX; do {
/*      */                   int cut;
/*  934 */                   if (fm.stringWidth(str) <= colW - inset - 10) {
/*  935 */                     lY += fontSp;
/*  936 */                     xCoord = R2LClue ? (lX + colW - inset - fm.stringWidth(str)) : (lX + 5 + inset);
/*  937 */                     if (printSt == 2) {
/*  938 */                       g2.drawString(str, xCoord, lY);
/*      */                     } else {
/*  940 */                       (NodeList.nodeList[z]).nlCb = lY + fm.getDescent();
/*  941 */                       (NodeList.nodeList[z]).nlCr = lX + colW;
/*      */                     } 
/*  943 */                     if (lY + fontSp > cluB - fm.getAscent()) {
/*  944 */                       lX = (ccDat[++ccNum]).x;
/*  945 */                       lY = (ccDat[ccNum]).y;
/*  946 */                       if ((ccDat[ccNum]).y < ccTop) lY += ccTop % fontSp; 
/*  947 */                       colW = (ccDat[ccNum]).w;
/*      */                     } 
/*      */                     
/*      */                     break;
/*      */                   } 
/*  952 */                   if (str.charAt(str.length() - 1) == ')' && str.indexOf(' ') != -1 && str.indexOf(' ') != str.lastIndexOf(' '))
/*  953 */                   { cut = str.lastIndexOf(' '); }
/*  954 */                   else { cut = str.length(); }
/*  955 */                    for (; --cut > 0; cut--) {
/*  956 */                     if (str.charAt(cut) == ' ') {
/*  957 */                       String str2 = str.substring(0, cut);
/*  958 */                       if (fm.stringWidth(str2) <= colW - inset - 10) {
/*  959 */                         lY += fontSp;
/*  960 */                         xCoord = R2LClue ? (lX + colW - inset - fm.stringWidth(str2)) : (lX + 5 + inset);
/*  961 */                         if (printSt == 2) {
/*  962 */                           g2.drawString(str2, xCoord, lY);
/*      */                         } else {
/*  964 */                           (NodeList.nodeList[z]).nlCb = lY + fm.getDescent();
/*  965 */                           (NodeList.nodeList[z]).nlCr = lX + colW;
/*      */                         } 
/*  967 */                         if (lY + fontSp > cluB - fm.getAscent()) {
/*  968 */                           (NodeList.nodeList[z]).nlBot = lY + fm.getDescent();
/*  969 */                           lX = (ccDat[++ccNum]).x;
/*  970 */                           lY = (ccDat[ccNum]).y;
/*  971 */                           if ((ccDat[ccNum]).y < ccTop) lY += ccTop % fontSp; 
/*  972 */                           colW = (ccDat[ccNum]).w;
/*  973 */                           (NodeList.nodeList[z]).nlTop = lY + fontSp - fm.getAscent();
/*      */                         } 
/*  975 */                         str = str.substring(cut + 1);
/*      */                         break;
/*      */                       } 
/*      */                     } 
/*      */                   } 
/*  980 */                 } while (colW != 0);
/*      */                 
/*  982 */                 g2.setColor(clueC);
/*  983 */                 if (colW == 0)
/*      */                   break;  } 
/*  985 */             }  if (colW == 0)
/*      */               break; 
/*  987 */           }  if (printSt == 2)
/*      */             break; 
/*  989 */           if (ccMax <= ccNum) {
/*  990 */             if (printSt == 0) { fontSp = --clueFontSize; continue; }
/*  991 */              fontSp--; continue;
/*  992 */           }  if (printSt == 0) { printSt = 1; fontSp += 40; continue; }
/*  993 */            printSt = 2;
/*      */         } 
/*      */       } 
/*      */       
/*  997 */       g2.setStroke(new BasicStroke(1.0F));
/*      */       return; }
/*      */     
/*      */     g2.setStroke(new BasicStroke(1.0F)); } static void loadSolvePuzzle() {
/* 1001 */     Grid.clearGrid();
/* 1002 */     loadCrossword(Op.cw[Op.CW.CwSolvePuz.ordinal()]);
/* 1003 */     restoreFrame();
/* 1004 */     Def.dispCursor = Boolean.valueOf(true); Def.dispSolArray = Boolean.valueOf(true);
/*      */   }
/*      */   void restoreIfDone() {
/*      */     int j;
/* 1008 */     for (j = 0; j < Grid.ySz; j++) {
/* 1009 */       for (int i = 0; i < Grid.xSz; i++)
/* 1010 */       { if (Character.isLetter(Grid.letter[i][j]) && Grid.sol[i][j] != Grid.letter[i][j])
/*      */           return;  } 
/* 1012 */     }  for (j = 0; j < Grid.ySz; j++) {
/* 1013 */       for (int i = 0; i < Grid.xSz; i++)
/* 1014 */         Grid.sol[i][j] = 0; 
/*      */     } 
/*      */   }
/*      */   void updateGrid(MouseEvent e) {
/* 1018 */     int x = e.getX(), y = e.getY();
/*      */     
/* 1020 */     if (Def.dispErrors.booleanValue()) { Def.dispErrors = Boolean.valueOf(false); return; }
/* 1021 */      if (x < Grid.xOrg || y < Grid.yOrg)
/* 1022 */       return;  int i = (x - Grid.xOrg) / Grid.xCell;
/* 1023 */     int j = (y - Grid.yOrg) / Grid.yCell;
/* 1024 */     if (i < Grid.xSz && j < Grid.ySz) {
/* 1025 */       i = Grid.RorL(i);
/* 1026 */       if (i == Grid.xCur && j == Grid.yCur) {
/* 1027 */         int w = (Grid.horz[Grid.xCur][Grid.yCur] != Grid.nCur) ? Grid.horz[Grid.xCur][Grid.yCur] : Grid.vert[Grid.xCur][Grid.yCur];
/*      */         
/* 1029 */         if (w != -1) Grid.nCur = w; 
/* 1030 */         restoreFrame();
/*      */         return;
/*      */       } 
/* 1033 */       if (Grid.letter[i][j] == 0)
/* 1034 */         return;  Grid.xCur = i; Grid.yCur = j;
/* 1035 */       Grid.nCur = (Grid.horz[Grid.xCur][Grid.yCur] != -1) ? Grid.horz[Grid.xCur][Grid.yCur] : Grid.vert[Grid.xCur][Grid.yCur];
/* 1036 */       restoreFrame();
/* 1037 */       Def.dispCursor = Boolean.valueOf(true);
/*      */     } else {
/*      */       
/* 1040 */       for (i = 0; i < NodeList.nodeListLength; i++) {
/* 1041 */         if ((NodeList.nodeList[i]).nlCb < (NodeList.nodeList[i]).nlCt) {
/* 1042 */           int w = (int)Math.floor((((NodeList.nodeList[i]).nlCr - (NodeList.nodeList[i]).nlCl - 10) / 2));
/* 1043 */           if ((x > (NodeList.nodeList[i]).nlCl && x < (NodeList.nodeList[i]).nlCl + w && y > (NodeList.nodeList[i]).nlCt && y < (NodeList.nodeList[i]).nlBot) || (x > (NodeList.nodeList[i]).nlCr - w && x < (NodeList.nodeList[i]).nlCr && y > (NodeList.nodeList[i]).nlTop && y < (NodeList.nodeList[i]).nlCb)) {
/*      */             
/* 1045 */             if (Grid.xCur == (NodeList.nodeList[i]).x && Grid.yCur == (NodeList.nodeList[i]).y) {
/* 1046 */               String url = "http://www.google.com/search?q=Dictionary+" + (NodeList.nodeList[Grid.nCur]).clue;
/* 1047 */               url = url.replace(' ', '+');
/* 1048 */               browserLaunch.openURL(jfSolveCrossword, url);
/*      */             } 
/* 1050 */             Grid.xCur = (NodeList.nodeList[i]).x;
/* 1051 */             Grid.yCur = (NodeList.nodeList[i]).y;
/*      */             
/*      */             break;
/*      */           } 
/* 1055 */         } else if (x > (NodeList.nodeList[i]).nlCl && x < (NodeList.nodeList[i]).nlCr && y > (NodeList.nodeList[i]).nlCt && y < (NodeList.nodeList[i]).nlCb) {
/* 1056 */           if (Grid.xCur == (NodeList.nodeList[i]).x && Grid.yCur == (NodeList.nodeList[i]).y) {
/* 1057 */             String url = "http://www.google.com/search?q=Dictionary+" + (NodeList.nodeList[Grid.nCur]).clue;
/* 1058 */             url = url.replace(' ', '+');
/* 1059 */             browserLaunch.openURL(jfSolveCrossword, url);
/*      */           } 
/* 1061 */           Grid.xCur = (NodeList.nodeList[i]).x;
/* 1062 */           Grid.yCur = (NodeList.nodeList[i]).y;
/*      */           break;
/*      */         } 
/*      */       } 
/* 1066 */       if (i == NodeList.nodeListLength)
/* 1067 */         return;  Grid.nCur = i;
/* 1068 */       restoreFrame();
/* 1069 */       Def.dispCursor = Boolean.valueOf(true);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   void nextLocation(int dir) {
/*      */     int i;
/* 1076 */     for (i = 0; i < (NodeList.nodeList[Grid.nCur]).length && (
/* 1077 */       Grid.xCur != ((NodeList.nodeList[Grid.nCur]).cellLoc[i]).x || Grid.yCur != ((NodeList.nodeList[Grid.nCur]).cellLoc[i]).y); i++);
/* 1078 */     if ((dir == 1 && i < (NodeList.nodeList[Grid.nCur]).length - 1) || (dir == -1 && i > 0)) {
/* 1079 */       int x = ((NodeList.nodeList[Grid.nCur]).cellLoc[i + dir]).x, y = ((NodeList.nodeList[Grid.nCur]).cellLoc[i + dir]).y;
/* 1080 */       Grid.xCur = x; Grid.yCur = y;
/*      */     } 
/*      */   }
/*      */   
/*      */   boolean noLetterAt(int i, int j) {
/* 1085 */     return ((NodeList.cellSignature(i, j) & 0x10) == 0);
/*      */   }
/*      */   
/*      */   void handleKeyPressed(KeyEvent e) {
/* 1089 */     int i = Grid.xCur, j = Grid.yCur;
/*      */     
/* 1091 */     if (Def.dispErrors.booleanValue()) { Def.dispErrors = Boolean.valueOf(false); return; }
/* 1092 */      if (e.isAltDown())
/* 1093 */       return;  switch ((char)e.getKeyCode()) {
/*      */       case '&':
/* 1095 */         for (; --j >= 0 && noLetterAt(i, j); j--);
/* 1096 */         if (j >= 0)
/* 1097 */           Grid.yCur = j; 
/*      */         break;
/*      */       case '(':
/* 1100 */         for (; ++j < Grid.ySz && noLetterAt(i, j); j++);
/* 1101 */         if (j < Grid.ySz)
/* 1102 */           Grid.yCur = j; 
/*      */         break;
/*      */       case '%':
/* 1105 */         if (R2LWord) {
/* 1106 */           for (; ++i < Grid.xSz && noLetterAt(i, j); i++);
/* 1107 */           if (i < Grid.xSz)
/* 1108 */             Grid.xCur = i; 
/*      */           break;
/*      */         } 
/* 1111 */         for (; --i >= 0 && noLetterAt(i, j); i--);
/* 1112 */         if (i >= 0) {
/* 1113 */           Grid.xCur = i;
/*      */         }
/*      */         break;
/*      */       case '\'':
/* 1117 */         if (R2LWord) {
/* 1118 */           for (; --i >= 0 && noLetterAt(i, j); i--);
/* 1119 */           if (i >= 0)
/* 1120 */             Grid.xCur = i; 
/*      */           break;
/*      */         } 
/* 1123 */         for (; ++i < Grid.xSz && noLetterAt(i, j); i++);
/* 1124 */         if (i < Grid.xSz) {
/* 1125 */           Grid.xCur = i;
/*      */         }
/*      */         break;
/*      */       case '$':
/* 1129 */         if (R2LWord) {
/* 1130 */           for (i = Grid.xSz - 1; noLetterAt(i, j); i--);
/* 1131 */           Grid.xCur = i;
/*      */           break;
/*      */         } 
/* 1134 */         for (i = 0; noLetterAt(i, j); i++);
/* 1135 */         Grid.xCur = i;
/*      */         break;
/*      */       
/*      */       case '#':
/* 1139 */         if (R2LWord) {
/* 1140 */           for (i = 0; noLetterAt(i, j); i++);
/* 1141 */           Grid.xCur = i;
/*      */           break;
/*      */         } 
/* 1144 */         for (i = Grid.xSz - 1; noLetterAt(i, j); i--);
/* 1145 */         Grid.xCur = i;
/*      */         break;
/*      */       
/*      */       case '!':
/* 1149 */         for (j = 0; noLetterAt(i, j); j++);
/* 1150 */         Grid.yCur = j;
/*      */         break;
/*      */       case '"':
/* 1153 */         for (j = Grid.ySz - 1; noLetterAt(i, j); j--);
/* 1154 */         Grid.yCur = j;
/*      */         break;
/*      */       case '\n':
/* 1157 */         if (Grid.nCur == Grid.horz[Grid.xCur][Grid.yCur]) {
/* 1158 */           Grid.nCur = Grid.vert[Grid.xCur][Grid.yCur]; break;
/*      */         } 
/* 1160 */         Grid.nCur = Grid.horz[Grid.xCur][Grid.yCur];
/*      */         break;
/*      */       case '':
/* 1163 */         Grid.sol[Grid.xCur][Grid.yCur] = 0;
/* 1164 */         nextLocation(1);
/*      */         break;
/*      */       case '\b':
/* 1167 */         Grid.sol[Grid.xCur][Grid.yCur] = 0;
/* 1168 */         nextLocation(-1);
/*      */         break;
/*      */       default:
/* 1171 */         if (!Character.isLetter(e.getKeyChar()) && !Character.isSpaceChar(e.getKeyChar()))
/*      */           return; 
/* 1173 */         if (Grid.sol[Grid.xCur][Grid.yCur] != Character.toUpperCase(e.getKeyChar())) {
/* 1174 */           Grid.sol[Grid.xCur][Grid.yCur] = Character.toUpperCase(e.getKeyChar());
/* 1175 */           (new Thread(this.solveThread)).start();
/*      */         } 
/* 1177 */         nextLocation(1); break;
/*      */     } 
/* 1179 */     if (Grid.nCur != Grid.horz[Grid.xCur][Grid.yCur] && Grid.nCur != Grid.vert[Grid.xCur][Grid.yCur])
/* 1180 */       Grid.nCur = (Grid.horz[Grid.xCur][Grid.yCur] == -1) ? Grid.vert[Grid.xCur][Grid.yCur] : Grid.horz[Grid.xCur][Grid.yCur]; 
/* 1181 */     restoreFrame();
/*      */   }
/*      */ }


/* Location:              C:\Users\mustapha\Desktop\Crossword-Express.jar!\crosswordexpress\CrosswordSolve.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */