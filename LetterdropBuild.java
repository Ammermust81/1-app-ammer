/*     */ package crosswordexpress;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import javax.swing.JCheckBox;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JMenuItem;
/*     */ import javax.swing.KeyStroke;
/*     */ 
/*     */ public class LetterdropBuild extends JPanel {
/*     */   static JFrame jfLetterdrop;
/*     */   static JMenuBar menuBar;
/*     */   JMenu menu;
/*     */   JMenu submenu;
/*     */   JMenuItem menuItem;
/*     */   static JPanel pp;
/*     */   
/*     */   static void def() {
/*  20 */     Op.updateOption(Op.LD.LdW.ordinal(), "600", Op.ld);
/*  21 */     Op.updateOption(Op.LD.LdH.ordinal(), "400", Op.ld);
/*  22 */     Op.updateOption(Op.LD.LdCell.ordinal(), "FFFFEE", Op.ld);
/*  23 */     Op.updateOption(Op.LD.LdGrid.ordinal(), "000000", Op.ld);
/*  24 */     Op.updateOption(Op.LD.LdPattern.ordinal(), "000000", Op.ld);
/*  25 */     Op.updateOption(Op.LD.LdLetter.ordinal(), "006666", Op.ld);
/*  26 */     Op.updateOption(Op.LD.LdError.ordinal(), "FF0000", Op.ld);
/*  27 */     Op.updateOption(Op.LD.LdPuz.ordinal(), "sample.letterdrop", Op.ld);
/*  28 */     Op.updateOption(Op.LD.LdFont.ordinal(), "SansSerif", Op.ld);
/*  29 */     Op.updateOption(Op.LD.LdPuzColor.ordinal(), "true", Op.ld);
/*  30 */     Op.updateOption(Op.LD.LdSolColor.ordinal(), "true", Op.ld);
/*     */   }
/*     */   static int panelW; static int panelH; static JLabel jl1; static JLabel jl2; static String ldString; static boolean holdNumbers; static boolean holdPunctuation;
/*  33 */   String letterdropHelp = "<div>Crossword Express <b>LETTER-DROP</b> puzzles consist of upper and lower sections which are more or less rectangular in shape. In each column of the puzzle, there are a number of vacant cells in the top section, and a corresponding number of letters in the cells of the lower section.<p/>To solve such a puzzle, the solver must place the given letters into the vacant cells in the top section of the puzzle to reveal a message which will normally be a well known literary quotation.<p/>Spaces in the quotation appear as black cells in the upper section, and any punctuation characters in the message will already appear in the upper section in their correct locations.<br/><br/></div><span class='m'>Menu Functions</span><ul><li/><span class='s'>File Menu</span><ul><li/><span>Load a Puzzle</span><br/>Use this option to choose your puzzle from the pool of LETTER-DROP puzzles currently available on your computer.<p/><li/><span>Save</span><br/>If you have done some manual editing of the puzzle, this option will save those changes under the existing file name.<p/><li/><span>SaveAs</span><br/>This option allows you to make an exact copy of the current puzzle using a different file name. The copy will be saved in the <b>letter-drop</b> folder along with all of the Letter-drop puzzles you have made. Alternatively, if you don't enter a new name for the puzzle, you can change the Puzzle Description, or any of the other descriptive items without changing the puzzle name.<p/><li/><span>Quit Construction</span><br/>Returns you to the Crossword Express opening screen.</ul><li/><span class='s'>Build Menu</span><ul><li/><span>Start a New Puzzle</span><br/>This option presents you with a dialog into which you can enter a file name for your new puzzle. You can also enter several other pieces of information such as a <b>Puzzle Title, Author</b> and <b>Copyright</b> information.<p/><li/><span>Build Options</span><br/>Use this option to access a Build Options dialog where you can set some rules to be followed by the puzzle building function as it builds your puzzle.<p/><li/><span>Start Building / Stop Building</span><br/>Construction of the puzzle will commence when you select the <b>Start Building</b> option. If puzzle building is successful you will receive a message containing the name of the puzzle file, and the location where it was saved. If it becomes necessary, you can interrupt the building process by selecting this option a second time. Note that during the construction phase, the text of this option is changed to <b>Stop Building</b><p/><li/><span class=\"menusub\">&lt; Expand &gt; and &gt; Compress  &lt;</span><br/>If you don't like the look of the puzzle which results (perhaps the pattern cells are clumped together in a tight formation) you can vary the shape of the puzzle using these two options.</ul><li/><span class='s'>View Menu</span><ul><li/><span>Display Options</span><br/>This leads you to a dialog box in which you can change the colors of various elements within the puzzle, and control the fonts which will be used for the puzzle's text components. You can also decide if printing of the puzzle will be done in black and white or in color.</ul><li/><span class='s'>Tasks Menu</span><ul><li/><span>Print this Puzzle</span><br/>This will take you to a custom print screen where you can control the details involved with printing your puzzle.<p/><li/><span>Solve this Puzzle</span><br/>This will take you to a Solve screen which provides a fully interactive environment for solving the puzzle.<p/><li/><span>Delete this Puzzle</span><br/>Use this option to eliminate unwanted LETTER-DROP puzzles from your file system.</ul><li/><span class='s'>Help Menu</span><ul><li/><span>Letter-drop Help</span><br/>Displays the Help screen which you are now reading.</ul></ul></body>";
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
/*  95 */   String letterdropOptions = "<div>Before you can build a <b>Letter-drop</b> puzzle, there is a set of options which must first be attended to:-</div><ul><li/><b>The Message:</bn><br/>Here you should enter a single paragraph which will form the basis of your Letter-drop puzzle.<p/><li/><b>Don't drop digits in message:</b><br/>By default, all characters in the message will be dropped into the hopper in the lower half of the puzzle. If you select this check-box, then digits will be retained in the top half of the puzzle.<p/><li/><b>Don't drop punctuation:</b><br/>Similarly, if you select this check-box, all punctuation characters will be retained in the upper half of the puzzle.<p/></ul></body>";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   LetterdropBuild(JFrame jf) {
/* 106 */     Def.puzzleMode = 130;
/* 107 */     ldString = "";
/* 108 */     makeGrid();
/*     */     
/* 110 */     jfLetterdrop = new JFrame("LetterDrop Construction");
/* 111 */     jfLetterdrop.setSize(Op.getInt(Op.LD.LdW.ordinal(), Op.ld), Op.getInt(Op.LD.LdH.ordinal(), Op.ld));
/* 112 */     int frameX = (jf.getX() + jfLetterdrop.getWidth() > Methods.scrW) ? (Methods.scrW - jfLetterdrop.getWidth() - 10) : jf.getX();
/* 113 */     jfLetterdrop.setLocation(frameX, jf.getY());
/* 114 */     jfLetterdrop.setLayout((LayoutManager)null);
/* 115 */     jfLetterdrop.setDefaultCloseOperation(0);
/* 116 */     jfLetterdrop
/* 117 */       .addComponentListener(new ComponentAdapter()
/*     */         {
/*     */           public void componentResized(ComponentEvent ce) {
/* 120 */             int w = (LetterdropBuild.jfLetterdrop.getWidth() < 600) ? 600 : LetterdropBuild.jfLetterdrop.getWidth();
/* 121 */             int h = (LetterdropBuild.jfLetterdrop.getHeight() < 400) ? 400 : LetterdropBuild.jfLetterdrop.getHeight();
/* 122 */             LetterdropBuild.jfLetterdrop.setSize(w, h);
/* 123 */             Op.setInt(Op.LD.LdW.ordinal(), w, Op.ld);
/* 124 */             Op.setInt(Op.LD.LdH.ordinal(), h, Op.ld);
/* 125 */             LetterdropBuild.restoreFrame();
/*     */           }
/*     */         });
/*     */     
/* 129 */     jfLetterdrop
/* 130 */       .addWindowListener(new WindowAdapter() {
/*     */           public void windowClosing(WindowEvent we) {
/* 132 */             if (Def.selecting)
/* 133 */               return;  Op.saveOptions("letterdrop.opt", Op.ld);
/* 134 */             CrosswordExpress.transfer(1, LetterdropBuild.jfLetterdrop);
/*     */           }
/*     */         });
/*     */     
/* 138 */     Methods.closeHelp();
/*     */     
/* 140 */     jl1 = new JLabel(); jfLetterdrop.add(jl1);
/* 141 */     jl2 = new JLabel(); jfLetterdrop.add(jl2);
/*     */ 
/*     */     
/* 144 */     menuBar = new JMenuBar();
/* 145 */     menuBar.setBackground(Def.COLOR_MENUBAR);
/* 146 */     jfLetterdrop.setJMenuBar(menuBar);
/*     */     
/* 148 */     this.menu = new JMenu("File");
/* 149 */     menuBar.add(this.menu);
/* 150 */     this.menuItem = new JMenuItem("Load a Puzzle");
/* 151 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(76, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 152 */     this.menu.add(this.menuItem);
/* 153 */     this.menuItem
/* 154 */       .addActionListener(ae -> {
/*     */           if (Def.building == 1)
/*     */             return; 
/*     */           pp.invalidate();
/*     */           pp.repaint();
/*     */           new Select(jfLetterdrop, "letterdrop", "letterdrop", Op.ld, Op.LD.LdPuz.ordinal(), false);
/*     */         });
/* 161 */     this.menuItem = new JMenuItem("Save");
/* 162 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(83, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 163 */     this.menu.add(this.menuItem);
/* 164 */     this.menuItem
/* 165 */       .addActionListener(ae -> {
/*     */           saveLetterdrop(Op.ld[Op.LD.LdPuz.ordinal()]);
/*     */           
/*     */           Methods.puzzleSaved(jfLetterdrop, "letterdrop", Op.ld[Op.LD.LdPuz.ordinal()]);
/*     */           
/*     */           restoreFrame();
/*     */         });
/* 172 */     this.menuItem = new JMenuItem("SaveAs");
/* 173 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(65, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 174 */     this.menu.add(this.menuItem);
/* 175 */     this.menuItem
/* 176 */       .addActionListener(ae -> {
/*     */           if (Def.building == 1) {
/*     */             return;
/*     */           }
/*     */           Methods.puzzleDescriptionDialog(jfLetterdrop, Op.ld[Op.LD.LdPuz.ordinal()].substring(0, Op.ld[Op.LD.LdPuz.ordinal()].indexOf(".letterdrop")), "letterdrop", ".letterdrop");
/*     */           if (Methods.clickedOK) {
/*     */             saveLetterdrop(Op.ld[Op.LD.LdPuz.ordinal()] = Methods.theFileName);
/*     */             restoreFrame();
/*     */             Methods.puzzleSaved(jfLetterdrop, "letterdrop", Op.ld[Op.LD.LdPuz.ordinal()]);
/*     */           } 
/*     */         });
/* 187 */     this.menuItem = new JMenuItem("Quit Construction");
/* 188 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(81, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 189 */     this.menu.add(this.menuItem);
/* 190 */     this.menuItem
/* 191 */       .addActionListener(ae -> {
/*     */           Op.saveOptions("letterdrop.opt", Op.ld);
/*     */ 
/*     */           
/*     */           CrosswordExpress.transfer(1, jfLetterdrop);
/*     */         });
/*     */     
/* 198 */     this.menu = new JMenu("Build");
/* 199 */     menuBar.add(this.menu);
/* 200 */     this.menuItem = new JMenuItem("Start a New Puzzle");
/* 201 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(78, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 202 */     this.menu.add(this.menuItem);
/* 203 */     this.menuItem
/* 204 */       .addActionListener(ae -> {
/*     */           if (Def.building == 1) {
/*     */             return;
/*     */           }
/*     */           Methods.puzzleDescriptionDialog(jfLetterdrop, Op.ld[Op.LD.LdPuz.ordinal()].substring(0, Op.ld[Op.LD.LdPuz.ordinal()].indexOf(".letterdrop")), "letterdrop", ".letterdrop");
/*     */           if (Methods.clickedOK) {
/*     */             Op.ld[Op.LD.LdPuz.ordinal()] = Methods.theFileName;
/*     */             makeGrid();
/*     */           } 
/*     */           restoreFrame();
/*     */         });
/* 215 */     this.menuItem = new JMenuItem("Build Options");
/* 216 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(79, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 217 */     this.menu.add(this.menuItem);
/* 218 */     this.menuItem
/* 219 */       .addActionListener(ae -> {
/*     */           LetterdropOptions();
/*     */           
/*     */           if (Methods.clickedOK) {
/*     */             makeGrid();
/*     */             
/*     */             letterDropDimensions(0);
/*     */             formatThePuzzle();
/*     */             Methods.havePuzzle = true;
/*     */           } 
/*     */           restoreFrame();
/*     */         });
/* 231 */     this.menuItem = new JMenuItem("< Expand >");
/* 232 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(69, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 233 */     this.menu.add(this.menuItem);
/* 234 */     this.menuItem
/* 235 */       .addActionListener(ae -> {
/*     */           letterDropDimensions(1);
/*     */           
/*     */           formatThePuzzle();
/*     */           
/*     */           restoreFrame();
/*     */         });
/* 242 */     this.menuItem = new JMenuItem("> Compress <");
/* 243 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(67, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 244 */     this.menu.add(this.menuItem);
/* 245 */     this.menuItem
/* 246 */       .addActionListener(ae -> {
/*     */           letterDropDimensions(-1);
/*     */           
/*     */           formatThePuzzle();
/*     */           
/*     */           restoreFrame();
/*     */         });
/*     */     
/* 254 */     this.menu = new JMenu("View");
/* 255 */     menuBar.add(this.menu);
/* 256 */     this.menuItem = new JMenuItem("Display Options");
/* 257 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(89, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 258 */     this.menu.add(this.menuItem);
/* 259 */     this.menuItem
/* 260 */       .addActionListener(ae -> {
/*     */           if (Def.building == 1) {
/*     */             return;
/*     */           }
/*     */           
/*     */           printOptions(jfLetterdrop, "Display Options");
/*     */           restoreFrame();
/*     */         });
/* 268 */     this.menu = new JMenu("Tasks");
/* 269 */     menuBar.add(this.menu);
/* 270 */     this.menuItem = new JMenuItem("Print this Puzzle");
/* 271 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(80, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 272 */     this.menu.add(this.menuItem);
/* 273 */     this.menuItem
/* 274 */       .addActionListener(ae -> {
/*     */           if (Def.building == 1) {
/*     */             return;
/*     */           }
/*     */           formatThePuzzle();
/*     */           CrosswordExpress.toPrint(jfLetterdrop, Op.ld[Op.LD.LdPuz.ordinal()]);
/*     */         });
/* 281 */     this.menuItem = new JMenuItem("Solve this Puzzle");
/* 282 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(86, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 283 */     this.menu.add(this.menuItem);
/* 284 */     this.menuItem
/* 285 */       .addActionListener(ae -> {
/*     */           if (Def.building == 1)
/*     */             return; 
/*     */           if (Methods.havePuzzle) {
/*     */             CrosswordExpress.transfer(131, jfLetterdrop);
/*     */           } else {
/*     */             Methods.noPuzzle(jfLetterdrop, "Solve");
/*     */           } 
/*     */         });
/* 294 */     this.menuItem = new JMenuItem("Delete this Puzzle");
/* 295 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(68, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 296 */     this.menu.add(this.menuItem);
/* 297 */     this.menuItem
/* 298 */       .addActionListener(ae -> {
/*     */           if (Methods.deleteAPuzzle(jfLetterdrop, Op.ld[Op.LD.LdPuz.ordinal()], "letterdrop", pp)) {
/*     */             ldString = "";
/*     */             
/*     */             makeGrid();
/*     */             
/*     */             loadLetterdrop(Op.ld[Op.LD.LdPuz.ordinal()]);
/*     */             
/*     */             restoreFrame();
/*     */           } 
/*     */         });
/* 309 */     this.menu = new JMenu("Help");
/* 310 */     menuBar.add(this.menu);
/*     */     
/* 312 */     this.menuItem = new JMenuItem("Letterdrop Help");
/* 313 */     this.menu.add(this.menuItem);
/* 314 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(72, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 315 */     this.menuItem
/* 316 */       .addActionListener(ae -> Methods.cweHelp(jfLetterdrop, null, "Building Letterdrop Puzzles", this.letterdropHelp));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 321 */     pp = new LetterdropPP(0, 37);
/* 322 */     jfLetterdrop.add(pp);
/*     */     
/* 324 */     pp
/* 325 */       .addMouseMotionListener(new MouseAdapter() {
/*     */           public void mouseMoved(MouseEvent e) {
/* 327 */             if (Def.isMac) {
/* 328 */               LetterdropBuild.jfLetterdrop.setResizable((LetterdropBuild.jfLetterdrop.getWidth() - e.getX() < 15 && LetterdropBuild.jfLetterdrop
/* 329 */                   .getHeight() - e.getY() < 95));
/*     */             }
/*     */           }
/*     */         });
/*     */     
/* 334 */     pp
/* 335 */       .addMouseListener(new MouseAdapter() {
/*     */           public void mousePressed(MouseEvent e) {
/* 337 */             LetterdropBuild.this.updateGrid(e);
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 342 */     loadLetterdrop(Op.ld[Op.LD.LdPuz.ordinal()]);
/* 343 */     restoreFrame();
/*     */   }
/*     */   
/*     */   static void restoreFrame() {
/* 347 */     jfLetterdrop.setVisible(true);
/* 348 */     Insets insets = jfLetterdrop.getInsets();
/* 349 */     panelW = jfLetterdrop.getWidth() - insets.left + insets.right;
/* 350 */     panelH = jfLetterdrop.getHeight() - insets.top + insets.bottom + 37 + menuBar.getHeight();
/* 351 */     pp.setSize(panelW, panelH);
/* 352 */     jfLetterdrop.requestFocusInWindow();
/* 353 */     formatThePuzzle();
/* 354 */     pp.repaint();
/* 355 */     Methods.infoPanel(jl1, jl2, "Build Letterdrop", "Puzzle : " + Op.ld[Op.LD.LdPuz.ordinal()], panelW);
/*     */   }
/*     */   
/*     */   static void setSizesAndOffsets(int x, int y, int width, int height, int inset) {
/* 359 */     int i = (width - inset) / Grid.xSz;
/* 360 */     int j = (height - inset) / Grid.ySz;
/* 361 */     Grid.xCell = Grid.yCell = (i < j) ? i : j;
/* 362 */     Grid.xOrg = x + 10;
/* 363 */     Grid.yOrg = y + (height - Grid.yCell * Grid.ySz) / 2;
/*     */   }
/*     */   
/*     */   private void LetterdropOptions() {
/* 367 */     final JDialog jdlgLetterdrop = new JDialog(jfLetterdrop, "Enter the Letterdrop Message", true);
/* 368 */     jdlgLetterdrop.setSize(305, 283);
/* 369 */     jdlgLetterdrop.setResizable(false);
/* 370 */     jdlgLetterdrop.setLayout((LayoutManager)null);
/* 371 */     jdlgLetterdrop.setLocation(jfLetterdrop.getX(), jfLetterdrop.getY());
/*     */     
/* 373 */     jdlgLetterdrop
/* 374 */       .addWindowListener(new WindowAdapter() {
/*     */           public void windowClosing(WindowEvent we) {
/* 376 */             Methods.closeHelp();
/*     */           }
/*     */         });
/*     */     
/* 380 */     Methods.closeHelp();
/*     */     
/* 382 */     JLabel jlMsg = new JLabel("The Message:");
/* 383 */     jlMsg.setForeground(Def.COLOR_LABEL);
/* 384 */     jlMsg.setDisplayedMnemonic('M');
/* 385 */     jlMsg.setSize(270, 20);
/* 386 */     jlMsg.setLocation(10, 5);
/* 387 */     jlMsg.setHorizontalAlignment(2);
/* 388 */     jdlgLetterdrop.add(jlMsg);
/*     */     
/* 390 */     final JTextArea jtaLetterdropMsg = new JTextArea(ldString);
/* 391 */     jtaLetterdropMsg.setLineWrap(true);
/* 392 */     jtaLetterdropMsg.setWrapStyleWord(true);
/* 393 */     JScrollPane jsp = new JScrollPane(jtaLetterdropMsg);
/* 394 */     jsp.setSize(270, 90);
/* 395 */     jsp.setLocation(10, 30);
/* 396 */     jtaLetterdropMsg.selectAll();
/* 397 */     jdlgLetterdrop.add(jsp);
/* 398 */     jtaLetterdropMsg.setFont(new Font("SansSerif", 1, 13));
/* 399 */     jlMsg.setLabelFor(jtaLetterdropMsg);
/*     */     
/* 401 */     final JCheckBox jcbNums = new JCheckBox("Don't drop digits", holdNumbers);
/* 402 */     jcbNums.setForeground(Def.COLOR_LABEL);
/* 403 */     jcbNums.setMnemonic('D');
/* 404 */     jcbNums.setOpaque(false);
/* 405 */     jcbNums.setSize(200, 20);
/* 406 */     jcbNums.setLocation(10, 125);
/* 407 */     jdlgLetterdrop.add(jcbNums);
/*     */     
/* 409 */     final JCheckBox jcbPunc = new JCheckBox("Don't drop punctuation", holdPunctuation);
/* 410 */     jcbPunc.setForeground(Def.COLOR_LABEL);
/* 411 */     jcbPunc.setMnemonic(80);
/* 412 */     jcbPunc.setOpaque(false);
/* 413 */     jcbPunc.setSize(200, 20);
/* 414 */     jcbPunc.setLocation(10, 150);
/* 415 */     jdlgLetterdrop.add(jcbPunc);
/*     */     
/* 417 */     Action doOK = new AbstractAction("OK") {
/*     */         public void actionPerformed(ActionEvent e) {
/* 419 */           LetterdropBuild.ldString = jtaLetterdropMsg.getText();
/* 420 */           LetterdropBuild.holdNumbers = jcbNums.isSelected();
/* 421 */           LetterdropBuild.holdPunctuation = jcbPunc.isSelected();
/* 422 */           jdlgLetterdrop.dispose();
/* 423 */           Methods.closeHelp();
/* 424 */           if (LetterdropBuild.ldString.length() > 0)
/* 425 */             Methods.clickedOK = true; 
/*     */         }
/*     */       };
/* 428 */     JButton jbOK = Methods.newButton("doOK", doOK, 79, 10, 180, 100, 26);
/* 429 */     jdlgLetterdrop.add(jbOK);
/*     */     
/* 431 */     Action doCancel = new AbstractAction("Cancel") {
/*     */         public void actionPerformed(ActionEvent e) {
/* 433 */           jdlgLetterdrop.dispose();
/* 434 */           Methods.closeHelp();
/* 435 */           Methods.clickedOK = false;
/*     */         }
/*     */       };
/* 438 */     JButton jbCancel = Methods.newButton("doCancel", doCancel, 67, 10, 215, 100, 26);
/* 439 */     jdlgLetterdrop.add(jbCancel);
/*     */     
/* 441 */     Action doHelp = new AbstractAction("<html><font size=6 color=BB0000 face=Serif>Help ", new ImageIcon("graphics/help.png")) {
/*     */         public void actionPerformed(ActionEvent e) {
/* 443 */           Methods.cweHelp(null, jdlgLetterdrop, "Letterdrop Options", LetterdropBuild.this.letterdropOptions);
/*     */         }
/*     */       };
/* 446 */     JButton jbHelp = Methods.newButton("doHelp", doHelp, 72, 120, 180, 160, 61);
/* 447 */     jdlgLetterdrop.add(jbHelp);
/*     */     
/* 449 */     jdlgLetterdrop.getRootPane().setDefaultButton(jbOK);
/* 450 */     Methods.setDialogSize(jdlgLetterdrop, 290, 251);
/*     */   }
/*     */   
/*     */   static void printOptions(JFrame jf, String type) {
/* 454 */     String[] colorLabel = { "Cell Color", "Grid Color", "Pattern Color", "Letter Color", "Error Color" };
/* 455 */     int[] colorInt = { Op.LD.LdCell.ordinal(), Op.LD.LdGrid.ordinal(), Op.LD.LdPattern.ordinal(), Op.LD.LdLetter.ordinal(), Op.LD.LdError.ordinal() };
/* 456 */     String[] fontLabel = { "Select Puzzle Font" };
/* 457 */     int[] fontInt = { Op.LD.LdFont.ordinal() };
/* 458 */     String[] checkLabel = { "PPrint Puzzle with color.", "SPrint Solution with color." };
/* 459 */     int[] checkInt = { Op.LD.LdPuzColor.ordinal(), Op.LD.LdSolColor.ordinal() };
/* 460 */     Methods.stdPrintOptions(jf, "Letterdrop " + type, Op.ld, colorLabel, colorInt, fontLabel, fontInt, checkLabel, checkInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void saveLetterdrop(String letterdropName) {
/*     */     try {
/* 470 */       DataOutputStream dataOut = new DataOutputStream(new FileOutputStream("letterdrop/" + letterdropName));
/* 471 */       dataOut.writeInt(Grid.xSz);
/* 472 */       dataOut.writeInt(Grid.ySz);
/* 473 */       dataOut.writeByte(Methods.noReveal);
/* 474 */       dataOut.writeByte(Methods.noErrors);
/* 475 */       for (int i = 0; i < 54; i++)
/* 476 */         dataOut.writeByte(0); 
/* 477 */       for (int j = 0; j < Grid.ySz; j++) {
/* 478 */         for (int k = 0; k < Grid.xSz; k++) {
/* 479 */           dataOut.writeInt(Grid.mode[k][j]);
/* 480 */           dataOut.writeChar(Grid.letter[k][j]);
/* 481 */           dataOut.writeChar(Grid.sol[k][j]);
/* 482 */           dataOut.writeChar(Grid.copy[k][j]);
/*     */         } 
/* 484 */       }  dataOut.writeUTF(Methods.puzzleTitle);
/* 485 */       dataOut.writeUTF(Methods.author);
/* 486 */       dataOut.writeUTF(Methods.copyright);
/* 487 */       dataOut.writeUTF(Methods.puzzleNumber);
/* 488 */       dataOut.writeUTF(Methods.puzzleNotes);
/* 489 */       dataOut.close();
/*     */     }
/* 491 */     catch (IOException exc) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void loadLetterdrop(String letterdropName) {
/*     */     
/* 501 */     try { File fl = new File("letterdrop/" + letterdropName);
/* 502 */       if (!fl.exists()) {
/*     */         
/* 504 */         fl = new File("letterdrop/");
/* 505 */         String[] s = fl.list(); int m;
/* 506 */         for (m = 0; m < s.length && (
/* 507 */           s[m].lastIndexOf(".letterdrop") == -1 || s[m].charAt(0) == '.'); m++);
/*     */         
/* 509 */         if (m == s.length) { makeGrid(); return; }
/* 510 */          letterdropName = s[m];
/* 511 */         Op.ld[Op.LD.LdPuz.ordinal()] = letterdropName;
/*     */       } 
/*     */       
/* 514 */       DataInputStream dataIn = new DataInputStream(new FileInputStream("letterdrop/" + letterdropName));
/* 515 */       Grid.xSz = dataIn.readInt();
/* 516 */       Grid.ySz = dataIn.readInt();
/* 517 */       Methods.noReveal = dataIn.readByte();
/* 518 */       Methods.noErrors = dataIn.readByte(); int i;
/* 519 */       for (i = 0; i < 54; i++)
/* 520 */         dataIn.readByte(); 
/* 521 */       for (int k = 0; k < Grid.ySz; k++) {
/* 522 */         for (i = 0; i < Grid.xSz; i++) {
/* 523 */           Grid.mode[i][k] = dataIn.readInt();
/* 524 */           Grid.letter[i][k] = dataIn.readChar();
/* 525 */           Grid.sol[i][k] = dataIn.readChar();
/* 526 */           Grid.copy[i][k] = dataIn.readChar();
/*     */         } 
/* 528 */       }  Methods.puzzleTitle = dataIn.readUTF();
/* 529 */       Methods.author = dataIn.readUTF();
/* 530 */       Methods.copyright = dataIn.readUTF();
/* 531 */       Methods.puzzleNumber = dataIn.readUTF();
/* 532 */       Methods.puzzleNotes = dataIn.readUTF();
/* 533 */       dataIn.close(); }
/*     */     
/* 535 */     catch (IOException exc) { return; }
/* 536 */      Methods.havePuzzle = true;
/*     */ 
/*     */     
/* 539 */     StringBuilder buf = new StringBuilder(200);
/* 540 */     for (int j = 0; j < Grid.ySz / 2; j++) {
/* 541 */       for (int i = 0; i < Grid.xSz; i++) {
/* 542 */         char ch = (char)Grid.letter[i][j];
/* 543 */         if (Grid.mode[i][j] == 0)
/* 544 */         { buf.append(ch); }
/* 545 */         else if (Grid.mode[i][j] == 1)
/* 546 */         { buf.append(' '); }
/*     */         else { break; }
/*     */       
/*     */       } 
/* 550 */     }  ldString = buf.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void drawLetterdrop(Graphics2D g2) {
/* 557 */     Stroke normalStroke = new BasicStroke(Grid.xCell / 20.0F, 2, 0);
/* 558 */     g2.setStroke(normalStroke);
/*     */     
/* 560 */     RenderingHints rh = g2.getRenderingHints();
/* 561 */     rh.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
/* 562 */     g2.setRenderingHints(rh);
/*     */     
/*     */     int j;
/* 565 */     for (j = 0; j < Grid.ySz; j++) {
/* 566 */       for (int i = 0; i < Grid.xSz; i++) {
/* 567 */         if (Grid.mode[i][j] != 2) {
/* 568 */           int theColor; if (Grid.mode[i][j] == 1) {
/* 569 */             theColor = Op.getColorInt(Op.LD.LdPattern.ordinal(), Op.ld);
/*     */           } else {
/* 571 */             theColor = Op.getColorInt(Op.LD.LdCell.ordinal(), Op.ld);
/* 572 */           }  g2.setColor(new Color(theColor));
/* 573 */           g2.fillRect(Grid.xOrg + i * Grid.xCell, Grid.yOrg + j * Grid.yCell, Grid.xCell, Grid.yCell);
/*     */         } 
/*     */       } 
/*     */     } 
/* 577 */     g2.setColor(new Color(Op.getColorInt(Op.LD.LdGrid.ordinal(), Op.ld)));
/* 578 */     for (j = 0; j < Grid.ySz; j++) {
/* 579 */       for (int i = 0; i < Grid.xSz; i++) {
/* 580 */         if (Grid.mode[i][j] != 2)
/* 581 */           g2.drawRect(Grid.xOrg + i * Grid.xCell, Grid.yOrg + j * Grid.yCell, Grid.xCell, Grid.yCell); 
/*     */       } 
/*     */     } 
/* 584 */     g2.setFont(new Font(Op.ld[Op.LD.LdFont.ordinal()], 0, 8 * Grid.yCell / 10));
/* 585 */     g2.setColor(new Color(Op.getColorInt(Op.LD.LdLetter.ordinal(), Op.ld)));
/* 586 */     FontMetrics fm = g2.getFontMetrics();
/* 587 */     for (j = 0; j < Grid.ySz; j++) {
/* 588 */       for (int i = 0; i < Grid.xSz; i++) {
/* 589 */         char ch = (char)Grid.sol[i][j];
/* 590 */         if (ch != '\000') {
/* 591 */           int w = fm.stringWidth("" + ch);
/* 592 */           g2.drawString("" + ch, Grid.xOrg + i * Grid.xCell + (Grid.xCell - w) / 2, Grid.yOrg + j * Grid.yCell + (Grid.yCell + fm.getAscent() - fm.getDescent()) / 2);
/*     */         } 
/*     */       } 
/* 595 */     }  g2.setStroke(new BasicStroke(1.0F));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void printLetterdrop(Graphics2D g2, boolean puz) {
/*     */     int y;
/* 603 */     for (y = 0; y < Grid.ySz / 2; y++) {
/* 604 */       for (int i = 0; i < Grid.xSz; i++)
/* 605 */         Grid.puzzle[i][y] = 32; 
/*     */     } 
/* 607 */     for (y = Grid.ySz / 2 + 1; y < Grid.ySz; y++) {
/* 608 */       for (int i = 0; i < Grid.xSz; i++)
/* 609 */         Grid.puzzle[i][y] = 1; 
/*     */     }  int j;
/* 611 */     for (j = 0; j < Grid.ySz / 2; j++) {
/* 612 */       for (int i = 0; i < Grid.xSz; i++) {
/* 613 */         char ch = (char)Grid.letter[i][j];
/* 614 */         if (Character.isLetter(ch)) {
/* 615 */           Grid.puzzle[i][j] = ch;
/* 616 */           if (!puz) {
/* 617 */             Grid.puzzle[i][j + Grid.ySz / 2 + 1] = ch;
/*     */           } else {
/* 619 */             Grid.puzzle[i][j + Grid.ySz / 2 + 1] = 32;
/*     */           } 
/* 621 */         }  if (Character.isDigit(ch)) {
/* 622 */           if (holdNumbers) {
/* 623 */             Grid.puzzle[i][j + Grid.ySz / 2 + 1] = ch;
/*     */           } else {
/* 625 */             Grid.puzzle[i][j] = ch;
/* 626 */             Grid.puzzle[i][j + Grid.ySz / 2 + 1] = 32;
/*     */           } 
/* 628 */           if (!puz)
/* 629 */             Grid.puzzle[i][j + Grid.ySz / 2 + 1] = ch; 
/*     */         } 
/* 631 */         if (!Character.isLetter(ch) && !Character.isDigit(ch) && !Character.isSpaceChar(ch)) {
/* 632 */           if (holdPunctuation) {
/* 633 */             Grid.puzzle[i][j + Grid.ySz / 2 + 1] = ch;
/*     */           } else {
/* 635 */             Grid.puzzle[i][j] = ch;
/* 636 */             Grid.puzzle[i][j + Grid.ySz / 2 + 1] = 32;
/*     */           } 
/* 638 */           if (!puz)
/* 639 */             Grid.puzzle[i][j + Grid.ySz / 2 + 1] = ch; 
/*     */         } 
/*     */       } 
/*     */     }  int x;
/* 643 */     for (x = 0; x < Grid.xSz; x++) {
/* 644 */       for (int i = 0; i < Grid.ySz / 2 - 1; i++) {
/* 645 */         for (j = i + 1; j < Grid.ySz / 2; j++) {
/* 646 */           if (Grid.puzzle[x][j] < Grid.puzzle[x][i])
/* 647 */           { char ch = (char)Grid.puzzle[x][i];
/* 648 */             Grid.puzzle[x][i] = Grid.puzzle[x][j];
/* 649 */             Grid.puzzle[x][j] = ch; } 
/*     */         } 
/*     */       } 
/* 652 */     }  for (x = 0; x < Grid.xSz; x++) {
/* 653 */       for (int n = 0; n < Grid.ySz / 2; n++) {
/* 654 */         if (Grid.puzzle[x][0] < 33) {
/* 655 */           for (y = 1; y < Grid.ySz / 2; y++)
/* 656 */             Grid.puzzle[x][y - 1] = Grid.puzzle[x][y]; 
/* 657 */           Grid.puzzle[x][y - 1] = 32;
/*     */         } 
/*     */       } 
/* 660 */     }  Stroke normalStroke = new BasicStroke(Grid.xCell / 20.0F, 2, 2);
/* 661 */     g2.setStroke(normalStroke);
/*     */     
/* 663 */     RenderingHints rh = g2.getRenderingHints();
/* 664 */     rh.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
/* 665 */     g2.setRenderingHints(rh);
/*     */     
/* 667 */     g2.setFont(new Font(Op.ld[Op.LD.LdFont.ordinal()], 0, 8 * Grid.yCell / 10));
/* 668 */     FontMetrics fm = g2.getFontMetrics();
/* 669 */     for (j = 0; j < Grid.ySz / 2; j++) {
/* 670 */       for (int i = 0; i < Grid.xSz; i++) {
/* 671 */         g2.setColor(new Color(Op.getColorInt(Op.LD.LdCell.ordinal(), Op.ld)));
/* 672 */         g2.fillRect(Grid.xOrg + i * Grid.xCell, Grid.yOrg + j * Grid.yCell, Grid.xCell, Grid.yCell);
/* 673 */         g2.setColor(new Color(Op.getColorInt(Op.LD.LdGrid.ordinal(), Op.ld)));
/* 674 */         g2.drawRect(Grid.xOrg + i * Grid.xCell, Grid.yOrg + j * Grid.yCell, Grid.xCell, Grid.yCell);
/* 675 */         char ch = (char)Grid.puzzle[i][j];
/* 676 */         if (ch != '\000') {
/* 677 */           g2.setColor(new Color(Op.getColorInt(Op.LD.LdLetter.ordinal(), Op.ld)));
/* 678 */           int w = fm.stringWidth("" + ch);
/* 679 */           g2.drawString("" + ch, Grid.xOrg + i * Grid.xCell + (Grid.xCell - w) / 2, Grid.yOrg + j * Grid.yCell + (Grid.yCell + fm.getAscent() - fm.getDescent()) / 2);
/*     */         } 
/*     */       } 
/*     */     } 
/* 683 */     g2.setColor(new Color(Op.getColorInt(Op.LD.LdLetter.ordinal(), Op.ld)));
/* 684 */     for (j = Grid.ySz / 2 + 1; j < Grid.ySz; j++) {
/* 685 */       for (int i = 0; i < Grid.xSz; i++) {
/* 686 */         int theColor; if (Grid.puzzle[i][j] == 1) {
/* 687 */           theColor = Op.getColorInt(Op.LD.LdPattern.ordinal(), Op.ld);
/*     */         } else {
/* 689 */           theColor = Op.getColorInt(Op.LD.LdCell.ordinal(), Op.ld);
/* 690 */         }  g2.setColor(new Color(theColor));
/* 691 */         g2.fillRect(Grid.xOrg + i * Grid.xCell, Grid.yOrg + j * Grid.yCell, Grid.xCell, Grid.yCell);
/* 692 */         g2.setColor(new Color(Op.getColorInt(Op.LD.LdGrid.ordinal(), Op.ld)));
/* 693 */         g2.drawRect(Grid.xOrg + i * Grid.xCell, Grid.yOrg + j * Grid.yCell, Grid.xCell, Grid.yCell);
/* 694 */         char ch = (char)Grid.puzzle[i][j];
/* 695 */         if (Grid.puzzle[i][j] != 1 && !Character.isSpaceChar(ch)) {
/* 696 */           g2.setColor(new Color(Op.getColorInt(Op.LD.LdLetter.ordinal(), Op.ld)));
/* 697 */           int w = fm.stringWidth("" + ch);
/* 698 */           g2.drawString("" + ch, Grid.xOrg + i * Grid.xCell + (Grid.xCell - w) / 2, Grid.yOrg + j * Grid.yCell + (Grid.yCell + fm.getAscent() - fm.getDescent()) / 2);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   static void printPuz(Graphics2D g2, int left, int top, int width, int height) {
/* 704 */     loadLetterdrop(Op.ld[Op.LD.LdPuz.ordinal()]);
/* 705 */     for (int y = 0; y < Grid.ySz / 2; y++) {
/* 706 */       for (int x = 0; x < Grid.xSz; x++)
/* 707 */         Grid.sol[x][y] = 0; 
/* 708 */     }  setSizesAndOffsets(left, top, width, height, 0);
/* 709 */     printLetterdrop(g2, true);
/*     */   }
/*     */   
/*     */   static void printSol(Graphics2D g2, int left, int top, int width, int height, String solutionPuzzle) {
/* 713 */     loadLetterdrop(solutionPuzzle);
/* 714 */     for (int y = 0; y < Grid.ySz; y++) {
/* 715 */       for (int x = 0; x < Grid.xSz; x++)
/* 716 */         Grid.sol[x][y] = Grid.letter[x][y]; 
/* 717 */     }  setSizesAndOffsets(left, top, width, height, 0);
/* 718 */     printLetterdrop(g2, false);
/* 719 */     loadLetterdrop(Op.ld[Op.LD.LdPuz.ordinal()]);
/*     */   }
/*     */   
/*     */   static void printSolTitle(Graphics2D g2, int left, int top, int width, int height, String solutionPuzzle) {
/* 723 */     loadLetterdrop(solutionPuzzle);
/* 724 */     Print.outputTextItem(g2, left, top, width, height, "SansSerif", 0, Methods.puzzleTitle);
/* 725 */     loadLetterdrop(Op.ld[Op.LD.LdPuz.ordinal()]);
/*     */   }
/*     */   
/*     */   static void makeGrid() {
/* 729 */     Methods.havePuzzle = false;
/* 730 */     Grid.clearGrid();
/* 731 */     letterDropDimensions(0);
/* 732 */     formatThePuzzle();
/*     */   }
/*     */ 
/*     */   
/*     */   static void letterDropDimensions(int increment) {
/*     */     int n;
/* 738 */     if ((n = ldString.length()) == 0) {
/* 739 */       Grid.xSz = 20;
/* 740 */       n = 80;
/*     */     }
/* 742 */     else if (increment == 0) {
/* 743 */       Grid.xSz = n / 4;
/* 744 */       if (n % 4 > 0) Grid.xSz++;
/*     */     
/* 746 */     } else if ((Grid.xSz += increment) == 51) {
/* 747 */       Grid.xSz--;
/*     */     } 
/*     */     while (true) {
/* 750 */       Grid.ySz = n / Grid.xSz;
/* 751 */       if (n % Grid.xSz > 0) Grid.ySz++; 
/* 752 */       Grid.ySz = Grid.ySz * 2 + 1;
/* 753 */       if (Grid.ySz < 7) {
/* 754 */         Grid.xSz--; continue;
/* 755 */       }  if (Grid.ySz > 13) {
/* 756 */         Grid.xSz++;
/*     */         continue;
/*     */       } 
/*     */       break;
/*     */     } 
/* 761 */     Grid.yCell = Grid.xCell = (panelW - 20) / Grid.xSz; n = (panelH - 20) / Grid.ySz;
/* 762 */     if (n < Grid.xCell) {
/* 763 */       Grid.xCell = Grid.yCell = n;
/*     */     }
/* 765 */     Grid.xOrg = (panelW - Grid.xSz * Grid.xCell) / 2;
/* 766 */     Grid.yOrg = (panelH - Grid.ySz * Grid.yCell) / 2;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static void formatThePuzzle()
/*     */   {
/*     */     int y;
/* 774 */     for (y = 0; y <= Grid.ySz / 2; y++) {
/* 775 */       for (int i = 0; i < Grid.xSz; i++) {
/* 776 */         Grid.mode[i][y] = 2;
/* 777 */         Grid.letter[i][y] = 32;
/*     */       } 
/*     */     } 
/*     */     int len;
/* 781 */     if ((len = ldString.length()) > 0)
/* 782 */     { for (int n = 0; n < len; n++) {
/* 783 */         char ch; if ((ch = ldString.charAt(n)) == ' ') {
/* 784 */           Grid.mode[n % Grid.xSz][n / Grid.xSz] = 1;
/*     */         } else {
/* 786 */           Grid.mode[n % Grid.xSz][n / Grid.xSz] = 0;
/* 787 */           Grid.letter[n % Grid.xSz][n / Grid.xSz] = ch;
/*     */         } 
/*     */       }  }
/* 790 */     else { for (int n = 0; n < Grid.xSz * Grid.ySz / 2; n++) {
/* 791 */         Grid.mode[n % Grid.xSz][n / Grid.xSz] = 0;
/* 792 */         Grid.letter[n % Grid.xSz][n / Grid.xSz] = 120;
/*     */       }  }
/*     */     
/* 795 */     for (int x = 0; x < Grid.xSz; x++) {
/* 796 */       int k; for (k = Grid.ySz - 1, y = 0; y < Grid.ySz / 2; y++) {
/*     */         
/* 798 */         char ch = (char)Grid.letter[x][y];
/* 799 */         if ((Character.isLetter(ch) || (Character.isDigit(ch) && !holdNumbers) || (!Character.isLetter(ch) && !Character.isDigit(ch) && ch != ' ' && !holdPunctuation)) && Grid.color[x][y] == 16777215) {
/* 800 */           Grid.mode[x][k] = 0;
/* 801 */           Grid.letter[x][k--] = ch;
/*     */         } 
/*     */       } 
/*     */       
/*     */       int i;
/* 806 */       for (i = k + 1; i < Grid.ySz - 1; i++) {
/* 807 */         for (int m = i + 1; m < Grid.ySz; m++) {
/* 808 */           if (Grid.letter[x][m] < Grid.letter[x][i]) {
/* 809 */             char ch = (char)Grid.letter[x][m];
/* 810 */             Grid.letter[x][m] = Grid.letter[x][i];
/* 811 */             Grid.letter[x][i] = ch;
/*     */           } 
/*     */         } 
/* 814 */       }  for (i = Grid.ySz / 2 + 1; i <= k; i++) {
/* 815 */         Grid.mode[x][i] = 2;
/* 816 */         Grid.letter[x][i] = 32;
/*     */       } 
/*     */     } 
/*     */     
/* 820 */     for (int j = 0; j < Grid.ySz; j++) {
/* 821 */       for (int i = 0; i < Grid.xSz; i++) {
/* 822 */         if (j < Grid.ySz / 2) {
/* 823 */           char ch = (char)Grid.letter[i][j];
/* 824 */           if ((Character.isLetter(ch) || (Character.isDigit(ch) && !holdNumbers) || (!Character.isLetter(ch) && !Character.isDigit(ch) && ch != ' ' && !holdPunctuation)) && Grid.color[i][j] == 16777215) {
/* 825 */             Grid.sol[i][j] = 0; Grid.copy[i][j] = 0;
/*     */           } else {
/* 827 */             Grid.sol[i][j] = ch; Grid.copy[i][j] = ch;
/*     */           } 
/*     */         } else {
/* 830 */           Grid.sol[i][j] = Grid.letter[i][j]; Grid.copy[i][j] = Grid.letter[i][j];
/*     */         } 
/*     */       } 
/*     */     }  } void updateGrid(MouseEvent e) {
/* 834 */     int x = e.getX(), y = e.getY();
/*     */     
/* 836 */     if (x < Grid.xOrg || y < Grid.yOrg)
/*     */       return; 
/* 838 */     x = (x - Grid.xOrg) / Grid.xCell;
/* 839 */     y = (y - Grid.yOrg) / Grid.yCell;
/* 840 */     if (x >= Grid.xSz || y > Grid.ySz / 2 || Grid.mode[x][y] != 0)
/*     */       return; 
/* 842 */     Grid.color[x][y] = 16777215 - Grid.color[x][y];
/* 843 */     formatThePuzzle();
/* 844 */     restoreFrame();
/*     */   }
/*     */ }


/* Location:              C:\Users\mustapha\Desktop\Crossword-Express.jar!\crosswordexpress\LetterdropBuild.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */