/*      */ package crosswordexpress;
/*      */ import java.awt.FontMetrics;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.Toolkit;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.io.DataInputStream;
/*      */ import java.io.DataOutputStream;
/*      */ import javax.swing.JButton;
/*      */ import javax.swing.JComboBox;
/*      */ import javax.swing.JDialog;
/*      */ import javax.swing.JLabel;
/*      */ import javax.swing.JMenuItem;
/*      */ import javax.swing.KeyStroke;
/*      */ 
/*      */ public class FutoshikiBuild extends JPanel {
/*      */   static JFrame jfFutoshiki;
/*      */   static JMenuBar menuBar;
/*      */   JMenu menu;
/*      */   JMenu submenu;
/*      */   JMenuItem menuItem;
/*      */   JMenuItem buildMenuItem;
/*   23 */   int howMany = 1; static JPanel pp; static int panelW; static int panelH; static JLabel jl1; static JLabel jl2; Timer myTimer; Thread thread; int startPuz = Integer.parseInt((new SimpleDateFormat("yyyyMMdd")).format(new Date())); int hmCount;
/*      */   boolean sixpack;
/*   25 */   static String rules = "Place numbers into the grid so that each row and column contains just one of the digits from 1 to <n1>. The < and > symbols between certain pairs of cells indicate the relative sizes of the numbers within those cells, and will give you enough information to completely solve the puzzle.";
/*      */ 
/*      */ 
/*      */   
/*      */   static void def() {
/*   30 */     Op.updateOption(Op.FU.FuW.ordinal(), "500", Op.fu);
/*   31 */     Op.updateOption(Op.FU.FuH.ordinal(), "580", Op.fu);
/*   32 */     Op.updateOption(Op.FU.FuSize.ordinal(), "5", Op.fu);
/*   33 */     Op.updateOption(Op.FU.FuHints.ordinal(), "3", Op.fu);
/*   34 */     Op.updateOption(Op.FU.FuDifficulty.ordinal(), "1", Op.fu);
/*   35 */     Op.updateOption(Op.FU.FuBorder.ordinal(), "006666", Op.fu);
/*   36 */     Op.updateOption(Op.FU.FuBg.ordinal(), "FFFFDD", Op.fu);
/*   37 */     Op.updateOption(Op.FU.FuCell.ordinal(), "AAEEDD", Op.fu);
/*   38 */     Op.updateOption(Op.FU.FuNumbers.ordinal(), "DD0000", Op.fu);
/*   39 */     Op.updateOption(Op.FU.FuGuide.ordinal(), "000066", Op.fu);
/*   40 */     Op.updateOption(Op.FU.FuInequality.ordinal(), "880000", Op.fu);
/*   41 */     Op.updateOption(Op.FU.FuError.ordinal(), "DD0000", Op.fu);
/*   42 */     Op.updateOption(Op.FU.FuPuz.ordinal(), "sample.futoshiki", Op.fu);
/*   43 */     Op.updateOption(Op.FU.FuFont.ordinal(), "SansSerif", Op.fu);
/*   44 */     Op.updateOption(Op.FU.FuGuideFont.ordinal(), "SansSerif", Op.fu);
/*   45 */     Op.updateOption(Op.FU.FuPuzColor.ordinal(), "false", Op.fu);
/*   46 */     Op.updateOption(Op.FU.FuSolColor.ordinal(), "false", Op.fu);
/*      */   }
/*      */   
/*   49 */   String futoshikiHelp = "<div><b>FUTOSHIKI</b> puzzles are built on square grids of typically 5x5 cells (although puzzles having sizes in the range 4x4 up to 8x8 can also be made). To solve them you must place numbers into the puzzle cells in such a way that each row and column contains each of the digits less than the size of the puzzle. In this respect they are similar to Sudoku puzzles. To give the solver a start, there will normally be several numbers already in the puzzle.<p/>In addition, a few pairs of adjacent cells will have <b>&lt;</b> and <b>&gt;</b> symbols inserted between them to indicate the relative sizes of the numbers within those cells.<br/><br/></div><span class='m'>Menu Functions</span><ul><li/><span class='s'>File Menu</span><ul><li/><span >Load a Puzzle</span><br/>Use this option to choose your puzzle from the pool of FUTOSHIKI puzzles currently available on your computer.<p/><li/><span >Save</span><br/>If you have done some manual editing of the puzzle, this option will save those changes under the existing file name.<p/><li/><span >SaveAs</span><br/>This option allows you to make an exact copy of the current puzzle using a different file name. The copy will be saved in the <b>futoshiki</b> folder along with all of the Futoshiki puzzles you have made. Alternatively, if you don't enter a new name for the puzzle, you can change the Puzzle Description, or any of the other descriptive items without changing the puzzle name.<p/><li/><span >Quit Construction</span><br/>Returns you to the Crossword Express opening screen.</ul><li/><span class='s'>Build Menu</span><ul><li/><span >Start a New Puzzle</span><br/>This option presents you with a dialog into which you can enter a file name for your new puzzle. You can also enter several other pieces of information such as a <b>Puzzle Title, Author</b> and <b>Copyright</b> information.<p/><li/><span >Build Options</span><br/>Use this option to access a Build Options dialog where you can set some rules to be followed by the puzzle building function as it builds your puzzle.<p/><li/><span >Start Building / Stop Building</span><br/>Construction of the puzzle will commence when you select the <b>Start Building</b> option. If puzzle building is successful you will receive a message containing the name of the puzzle file, and the location where it was saved. If it becomes necessary, you can interrupt the building process by selecting this option a second time. Note that during the construction phase, the text of this option is changed to <b>Stop Building</b><p/><li/><span >Test Puzzle Validity</span><br/>If you wish to build your FUTOSHIKI puzzles manually, you can do so by typing digits directly into the puzzle. The <b>&lt;</b> and <b>&gt;</b> symbols can be entered by issuing a mouse click when the mouse cursor is pointing to the space between a pair of adjacent cells. You would normally only adopt this strategy if you were wanting to enter an existing puzzle...possibly one which you found published in a magazine or puzzle book. Clicking the <b>Test Puzzle Validity</b> button will tell you the status of your design:-<p/><ul><li/>If there is a single unique solution to the puzzle you have entered, the puzzle will be saved automatically, and you will receive a message telling you where it was saved.<p/><li/>Otherwise you will receive a message box telling you that <b>The current puzzle is invalid.</b></ul></ul><li/><span class='s'>View Menu</span><ul><li/><span >Display Options</span><br/>This leads you to a dialog box in which you can change the colors of various elements within the puzzle, and control the fonts which will be used for the puzzle's text components. You can also decide if printing of the puzzle will be done in black and white or in color.</ul><li/><span class='s'>Export Menu</span><br/><ul><li/><span>Print a Futoshiki KDP puzzle book.</span><br/>The letters KDP stand for <b>Kindle Direct Publishing</b>. This is a free publishing service operared by Amazon, in which they handle all matters related to printing, advertising and sales of books created by members of the public. A portion of the proceeds are retained by Amazon while the remainder is paid to the author. Fifteen of the Puzzles created by Crossword Express can be printed into PDF format files ready for publication by Amazon. When you select this option, you will be presented with a dialog which allows you to control the process. Please study the Help offered by this dialog before attempting to make use of it.</ul><li/><span class='s'>Tasks Menu</span><ul><li/><span >Print this Puzzle</span><br/>This will take you to a custom print screen where you can control the details involved with printing your puzzle.<p/><li/><span >Solve this Puzzle</span><br/>This will take you to a Solve screen which provides a fully interactive environment for solving the puzzle.<p/><li/><span >Delete this Puzzle</span><br/>Use this option to eliminate unwanted FUTOSHIKI puzzles from your file system.</ul><li/><span class='s'>Help Menu</span><ul><li/><span >Futoshiki Help</span><br/>Displays the Help screen which you are now reading.</ul></ul></body>";
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
/*  127 */   String futoshikiOptions = "<div>Before you give the command to build the <b>Futoshiki</b> puzzle, you must first set some options which the program will use during the construction process.</div><br/><ul><li/><b>Futoshiki Size: </b>Enter a single digit which tells the program how big you want the puzzle to be. This number can range from 4 to 9. To some extent, this will have an impact on the difficulty of solving the puzzle.<p/><li/><b>Preset Numbers: </b>Enter a single digit to tell the program how many preset numbers should be built into the puzzle. This number can range between 0 and the puzzle dimension described above.<p/><li/><b>Difficulty: </b>A combo box allows you to choose between difficulty settings of <b>Easy</b> and <b>Hard</b>.<p/><li/>If you want to make a number of puzzles all having the same dimensions, simply type a number into the <b>How many puzzles</b> input field. When you issue the Make command, Crossword Express will make that number of puzzles. The puzzle names will be numbers which represent a date in <b>yyyymmdd</b> format. The default value presented by Crossword Express is always the current date, but you can change this to any date that suits your needs. As the series of puzzles is created, CWE will automatically step on to the next date in the sequence, taking into account such factors as the varying number of days in the months, and of course leap years. Virtually any number of puzzles can be made in a single operation using this feature.<p/><li/><b>HOWEVER:</b> If you prefer a simpler numbering scheme for your puzzles, you can enter any number of 7 digits or less to be used for your first puzzle, and Crossword Express will number the remainder of the puzzles sequentially starting with your number.<p/><li/>If you do choose to make multiple puzzles, then by default, Crossword Express will change the difficulty of the resulting puzzles over a cycle of seven puzzles. This would be useful for a daily newspaper so that the week could start with a very easy puzzle, with quite difficult puzzles reserved for the weekend. If you don't want this feature, clearing the <b>Vary Difficulty on 7 day cycle</sb> check-box will disable it.</ul></body>";
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
/*      */ 
/*      */ 
/*      */   
/*      */   FutoshikiBuild(JFrame jf, boolean auto, int hm, int start) {
/*  154 */     Def.puzzleMode = 80;
/*  155 */     Methods.havePuzzle = false;
/*  156 */     Def.dispCursor = Boolean.valueOf(true); Def.dispGuideDigits = Boolean.valueOf(false);
/*  157 */     Def.building = 0;
/*  158 */     makeGrid();
/*      */     
/*  160 */     jfFutoshiki = new JFrame("Futoshiki");
/*  161 */     if (Op.getInt(Op.FU.FuH.ordinal(), Op.fu) > Methods.scrH - 200) {
/*  162 */       int diff = Op.getInt(Op.FU.FuH.ordinal(), Op.fu) - Op.getInt(Op.FU.FuW.ordinal(), Op.fu);
/*  163 */       Op.setInt(Op.FU.FuH.ordinal(), Methods.scrH - 200, Op.fu);
/*  164 */       Op.setInt(Op.FU.FuW.ordinal(), Methods.scrH - 200 + diff, Op.fu);
/*  165 */     }  jfFutoshiki.setSize(Op.getInt(Op.FU.FuW.ordinal(), Op.fu), Op.getInt(Op.FU.FuH.ordinal(), Op.fu));
/*  166 */     int frameX = (jf.getX() + jfFutoshiki.getWidth() > Methods.scrW) ? (Methods.scrW - jfFutoshiki.getWidth() - 10) : jf.getX();
/*  167 */     jfFutoshiki.setLocation(frameX, jf.getY());
/*  168 */     jfFutoshiki.setLayout((LayoutManager)null);
/*  169 */     jfFutoshiki.setDefaultCloseOperation(0);
/*  170 */     jfFutoshiki
/*  171 */       .addComponentListener(new ComponentAdapter() {
/*      */           public void componentResized(ComponentEvent ce) {
/*  173 */             int oldw = Op.getInt(Op.FU.FuW.ordinal(), Op.fu);
/*  174 */             int oldh = Op.getInt(Op.FU.FuH.ordinal(), Op.fu);
/*  175 */             Methods.frameResize(FutoshikiBuild.jfFutoshiki, oldw, oldh, 500, 580);
/*  176 */             Op.setInt(Op.FU.FuW.ordinal(), FutoshikiBuild.jfFutoshiki.getWidth(), Op.fu);
/*  177 */             Op.setInt(Op.FU.FuH.ordinal(), FutoshikiBuild.jfFutoshiki.getHeight(), Op.fu);
/*  178 */             FutoshikiBuild.restoreFrame();
/*      */           }
/*      */         });
/*      */     
/*  182 */     jfFutoshiki
/*  183 */       .addWindowListener(new WindowAdapter() {
/*      */           public void windowClosing(WindowEvent we) {
/*  185 */             if (Def.building == 1 || Def.selecting)
/*  186 */               return;  Op.saveOptions("futoshiki.opt", Op.fu);
/*  187 */             CrosswordExpress.transfer(1, FutoshikiBuild.jfFutoshiki);
/*      */           }
/*      */         });
/*      */     
/*  191 */     Methods.closeHelp();
/*      */ 
/*      */     
/*  194 */     Runnable buildThread = () -> {
/*      */         if (this.howMany == 1) {
/*      */           buildFutoshiki();
/*      */         } else {
/*      */           multiBuild();
/*      */           
/*      */           if (this.sixpack) {
/*      */             Sixpack.trigger();
/*      */             jfFutoshiki.dispose();
/*      */             Def.building = 0;
/*      */             return;
/*      */           } 
/*      */         } 
/*      */         this.buildMenuItem.setText("Start Building");
/*      */         if (Def.building == 2) {
/*      */           Def.building = 0;
/*      */           Methods.interrupted(jfFutoshiki);
/*      */           makeGrid();
/*      */           restoreFrame();
/*      */           return;
/*      */         } 
/*      */         Methods.havePuzzle = true;
/*      */         restoreFrame();
/*      */         Methods.puzzleSaved(jfFutoshiki, "futoshiki", Op.fu[Op.FU.FuPuz.ordinal()]);
/*      */         Def.building = 0;
/*      */       };
/*  220 */     jl1 = new JLabel(); jfFutoshiki.add(jl1);
/*  221 */     jl2 = new JLabel(); jfFutoshiki.add(jl2);
/*      */ 
/*      */     
/*  224 */     menuBar = new JMenuBar();
/*  225 */     menuBar.setBackground(Def.COLOR_MENUBAR);
/*  226 */     jfFutoshiki.setJMenuBar(menuBar);
/*      */     
/*  228 */     this.menu = new JMenu("File");
/*  229 */     menuBar.add(this.menu);
/*  230 */     this.menuItem = new JMenuItem("Load a Puzzle");
/*  231 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(76, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  232 */     this.menu.add(this.menuItem);
/*  233 */     this.menuItem
/*  234 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1)
/*      */             return; 
/*      */           pp.invalidate();
/*      */           pp.repaint();
/*      */           new Select(jfFutoshiki, "futoshiki", "futoshiki", Op.fu, Op.FU.FuPuz.ordinal(), false);
/*      */         });
/*  241 */     this.menuItem = new JMenuItem("Save");
/*  242 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(83, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  243 */     this.menu.add(this.menuItem);
/*  244 */     this.menuItem
/*  245 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           saveFutoshiki(Op.fu[Op.FU.FuPuz.ordinal()]);
/*      */           Methods.puzzleSaved(jfFutoshiki, "futoshiki", Op.fu[Op.FU.FuPuz.ordinal()]);
/*      */         });
/*  252 */     this.menuItem = new JMenuItem("SaveAs");
/*  253 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(65, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  254 */     this.menu.add(this.menuItem);
/*  255 */     this.menuItem
/*  256 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           Methods.puzzleDescriptionDialog(jfFutoshiki, Op.fu[Op.FU.FuPuz.ordinal()].substring(0, Op.fu[Op.FU.FuPuz.ordinal()].indexOf(".futoshiki")), "futoshiki", ".futoshiki");
/*      */           if (Methods.clickedOK) {
/*      */             saveFutoshiki(Op.fu[Op.FU.FuPuz.ordinal()] = Methods.theFileName);
/*      */             restoreFrame();
/*      */             Methods.puzzleSaved(jfFutoshiki, "futoshiki", Op.fu[Op.FU.FuPuz.ordinal()]);
/*      */           } 
/*      */         });
/*  267 */     this.menuItem = new JMenuItem("Quit Construction");
/*  268 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(81, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  269 */     this.menu.add(this.menuItem);
/*  270 */     this.menuItem
/*  271 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           
/*      */           Op.saveOptions("futoshiki.opt", Op.fu);
/*      */           CrosswordExpress.transfer(1, jfFutoshiki);
/*      */         });
/*  279 */     this.menu = new JMenu("Build");
/*  280 */     menuBar.add(this.menu);
/*  281 */     this.menuItem = new JMenuItem("Start a new Puzzle");
/*  282 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(78, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  283 */     this.menu.add(this.menuItem);
/*  284 */     this.menuItem
/*  285 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           Methods.puzzleDescriptionDialog(jfFutoshiki, Op.fu[Op.FU.FuPuz.ordinal()].substring(0, Op.fu[Op.FU.FuPuz.ordinal()].indexOf(".futoshiki")), "futoshiki", ".futoshiki");
/*      */           if (Methods.clickedOK) {
/*      */             Op.fu[Op.FU.FuPuz.ordinal()] = Methods.theFileName;
/*      */             makeGrid();
/*      */           } 
/*      */           restoreFrame();
/*      */         });
/*  296 */     this.menuItem = new JMenuItem("Build Options");
/*  297 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(79, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  298 */     this.menu.add(this.menuItem);
/*  299 */     this.menuItem
/*  300 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1)
/*      */             return; 
/*      */           futoshikiOptions();
/*      */           if (Methods.clickedOK) {
/*      */             makeGrid();
/*      */             if (this.howMany > 1)
/*      */               Op.fu[Op.FU.FuPuz.ordinal()] = "" + this.startPuz + ".futoshiki"; 
/*      */           } 
/*      */           restoreFrame();
/*      */         });
/*  311 */     this.buildMenuItem = new JMenuItem("Start Building");
/*  312 */     this.buildMenuItem.setAccelerator(KeyStroke.getKeyStroke(66, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  313 */     this.menu.add(this.buildMenuItem);
/*  314 */     this.buildMenuItem
/*  315 */       .addActionListener(ae -> {
/*      */           if (Op.fu[Op.FU.FuPuz.ordinal()].length() == 0 && this.howMany == 1) {
/*      */             Methods.noName(jfFutoshiki);
/*      */             
/*      */             return;
/*      */           } 
/*      */           
/*      */           if (Def.building == 0) {
/*      */             this.thread = new Thread(paramRunnable);
/*      */             this.thread.start();
/*      */             Def.building = 1;
/*      */             this.buildMenuItem.setText("Stop Building");
/*      */           } else {
/*      */             Def.building = 2;
/*      */           } 
/*      */         });
/*  331 */     this.menuItem = new JMenuItem("Test Puzzle Validity");
/*  332 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(84, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  333 */     this.menu.add(this.menuItem);
/*  334 */     this.menuItem
/*  335 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1)
/*      */             return;  int j;
/*      */           for (j = 0; j < Grid.ySz; j++) {
/*      */             for (int i = 0; i < Grid.xSz; i++)
/*      */               Grid.copy[i][j] = Grid.sol[i][j]; 
/*      */           } 
/*      */           if (!uniqueTest(4)) {
/*      */             JOptionPane.showMessageDialog(jfFutoshiki, "This is not a valid puzzle", "Test Result", 1);
/*      */           } else {
/*      */             for (j = 0; j < Grid.ySz; j++) {
/*      */               for (int i = 0; i < Grid.xSz; i++) {
/*      */                 Grid.letter[i][j] = Grid.sol[i][j];
/*      */                 Grid.sol[i][j] = Grid.copy[i][j];
/*      */               } 
/*      */             } 
/*      */             Methods.havePuzzle = true;
/*      */             saveFutoshiki(Op.fu[Op.FU.FuPuz.ordinal()]);
/*      */             Methods.puzzleSaved(jfFutoshiki, "futoshiki", Op.fu[Op.FU.FuPuz.ordinal()]);
/*      */           } 
/*      */         });
/*  356 */     this.menu = new JMenu("View");
/*  357 */     menuBar.add(this.menu);
/*  358 */     this.menuItem = new JMenuItem("Display Options");
/*  359 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(89, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  360 */     this.menu.add(this.menuItem);
/*  361 */     this.menuItem
/*  362 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           
/*      */           printOptions(jfFutoshiki, "Display Options");
/*      */           restoreFrame();
/*      */         });
/*  370 */     this.menu = new JMenu("Export");
/*  371 */     menuBar.add(this.menu);
/*  372 */     this.menuItem = new JMenuItem("Print a Futoshiki KDP puzzle book.");
/*  373 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(75, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  374 */     this.menu.add(this.menuItem);
/*  375 */     this.menuItem
/*  376 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           
/*      */           Methods.printKdpDialog(jfFutoshiki, 80, 6);
/*      */         });
/*  383 */     this.menu = new JMenu("Tasks");
/*  384 */     menuBar.add(this.menu);
/*  385 */     this.menuItem = new JMenuItem("Print this Puzzle");
/*  386 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(80, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  387 */     this.menu.add(this.menuItem);
/*  388 */     this.menuItem
/*  389 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           CrosswordExpress.toPrint(jfFutoshiki, Op.fu[Op.FU.FuPuz.ordinal()]);
/*      */         });
/*  395 */     this.menuItem = new JMenuItem("Solve this Puzzle");
/*  396 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(86, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  397 */     this.menu.add(this.menuItem);
/*  398 */     this.menuItem
/*  399 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1)
/*      */             return; 
/*      */           if (Methods.havePuzzle) {
/*      */             CrosswordExpress.transfer(81, jfFutoshiki);
/*      */           } else {
/*      */             Methods.noPuzzle(jfFutoshiki, "Solve");
/*      */           } 
/*      */         });
/*  408 */     this.menuItem = new JMenuItem("Delete this Puzzle");
/*  409 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(68, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  410 */     this.menu.add(this.menuItem);
/*  411 */     this.menuItem
/*  412 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           
/*      */           if (Methods.deleteAPuzzle(jfFutoshiki, Op.fu[Op.FU.FuPuz.ordinal()], "futoshiki", pp)) {
/*      */             makeGrid();
/*      */             loadFutoshiki(Op.fu[Op.FU.FuPuz.ordinal()]);
/*      */             restoreFrame();
/*      */           } 
/*      */         });
/*  423 */     this.menu = new JMenu("Help");
/*  424 */     menuBar.add(this.menu);
/*  425 */     this.menuItem = new JMenuItem("Futoshiki Help");
/*  426 */     this.menu.add(this.menuItem);
/*  427 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(72, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  428 */     this.menuItem
/*  429 */       .addActionListener(ae -> Methods.cweHelp(jfFutoshiki, null, "Building Futoshiki Puzzles", this.futoshikiHelp));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  434 */     pp = new FutoshikiPP(0, 37);
/*  435 */     jfFutoshiki.add(pp);
/*      */     
/*  437 */     pp
/*  438 */       .addMouseListener(new MouseAdapter() {
/*      */           public void mousePressed(MouseEvent e) {
/*  440 */             FutoshikiBuild.updateGrid(e);
/*      */           }
/*      */         });
/*      */ 
/*      */     
/*  445 */     pp
/*  446 */       .addMouseMotionListener(new MouseAdapter() {
/*      */           public void mouseMoved(MouseEvent e) {
/*  448 */             if (Def.isMac) {
/*  449 */               FutoshikiBuild.jfFutoshiki.setResizable((FutoshikiBuild.jfFutoshiki.getWidth() - e.getX() < 15 && FutoshikiBuild.jfFutoshiki
/*  450 */                   .getHeight() - e.getY() < 95));
/*      */             }
/*      */           }
/*      */         });
/*      */     
/*  455 */     jfFutoshiki
/*  456 */       .addKeyListener(new KeyAdapter() {
/*      */           public void keyPressed(KeyEvent e) {
/*  458 */             FutoshikiBuild.this.handleKeyPressed(e);
/*      */           }
/*      */         });
/*      */ 
/*      */     
/*  463 */     loadFutoshiki(Op.fu[Op.FU.FuPuz.ordinal()]);
/*  464 */     restoreFrame();
/*      */ 
/*      */     
/*  467 */     ActionListener timerAL = ae -> {
/*      */         this.myTimer.stop();
/*      */         this.thread = new Thread(paramRunnable);
/*      */         this.thread.start();
/*      */         Def.building = 1;
/*      */       };
/*  473 */     this.myTimer = new Timer(1000, timerAL);
/*      */     
/*  475 */     if (auto) {
/*  476 */       this.sixpack = true;
/*  477 */       this.howMany = hm; this.startPuz = start;
/*  478 */       this.myTimer.start();
/*      */     } 
/*      */   }
/*      */   
/*      */   static void restoreFrame() {
/*  483 */     jfFutoshiki.setVisible(true);
/*  484 */     Insets insets = jfFutoshiki.getInsets();
/*  485 */     panelW = jfFutoshiki.getWidth() - insets.left + insets.right;
/*  486 */     panelH = jfFutoshiki.getHeight() - insets.top + insets.bottom + 37 + menuBar.getHeight();
/*  487 */     pp.setSize(panelW, panelH);
/*  488 */     jfFutoshiki.requestFocusInWindow();
/*  489 */     pp.repaint();
/*  490 */     Methods.infoPanel(jl1, jl2, "Build Futoshiki", "Puzzle : " + Op.fu[Op.FU.FuPuz.ordinal()], panelW);
/*      */   }
/*      */   
/*      */   static void setSizesAndOffsets(int x, int y, int width, int height, int inset) {
/*  494 */     int v1 = (int)((width - inset) / (Grid.xSz + 0.2D));
/*  495 */     int v2 = (int)((height - inset) / (Grid.ySz + 0.2D));
/*  496 */     Grid.xCell = Grid.yCell = (v1 < v2) ? v1 : v2;
/*  497 */     Grid.xOrg = x + (width + Grid.xCell / 3 - Grid.xSz * Grid.xCell) / 2;
/*  498 */     Grid.yOrg = y + (height + Grid.yCell / 3 - Grid.ySz * Grid.yCell) / 2;
/*      */   }
/*      */   
/*      */   private void futoshikiOptions() {
/*  502 */     String[] diffString = { "  Easy", "  Hard" };
/*      */ 
/*      */ 
/*      */     
/*  506 */     JDialog jdlgFutoshiki = new JDialog(jfFutoshiki, "Futoshiki Options", true);
/*  507 */     jdlgFutoshiki.setSize(270, 323);
/*  508 */     jdlgFutoshiki.setResizable(false);
/*  509 */     jdlgFutoshiki.setLayout((LayoutManager)null);
/*  510 */     jdlgFutoshiki.setLocation(jfFutoshiki.getX(), jfFutoshiki.getY());
/*      */     
/*  512 */     jdlgFutoshiki
/*  513 */       .addWindowListener(new WindowAdapter() {
/*      */           public void windowClosing(WindowEvent we) {
/*  515 */             Methods.closeHelp();
/*      */           }
/*      */         });
/*      */     
/*  519 */     Methods.closeHelp();
/*      */     
/*  521 */     JLabel jlSize = new JLabel("Futoshiki Size:");
/*  522 */     jlSize.setForeground(Def.COLOR_LABEL);
/*  523 */     jlSize.setSize(100, 20);
/*  524 */     jlSize.setLocation(30, 8);
/*  525 */     jlSize.setHorizontalAlignment(4);
/*  526 */     jdlgFutoshiki.add(jlSize);
/*      */     
/*  528 */     JComboBox<Integer> jcbbSize = new JComboBox<>();
/*  529 */     for (int i = 4; i <= 9; i++)
/*  530 */       jcbbSize.addItem(Integer.valueOf(i)); 
/*  531 */     jcbbSize.setSize(80, 23);
/*  532 */     jcbbSize.setLocation(150, 8);
/*  533 */     jdlgFutoshiki.add(jcbbSize);
/*  534 */     jcbbSize.setBackground(Def.COLOR_BUTTONBG);
/*  535 */     jcbbSize.setSelectedIndex(Op.getInt(Op.FU.FuSize.ordinal(), Op.fu) - 4);
/*      */     
/*  537 */     JLabel jlPreset = new JLabel("Preset Numbers:");
/*  538 */     jlPreset.setForeground(Def.COLOR_LABEL);
/*  539 */     jlPreset.setSize(110, 20);
/*  540 */     jlPreset.setLocation(20, 38);
/*  541 */     jlPreset.setHorizontalAlignment(4);
/*  542 */     jdlgFutoshiki.add(jlPreset);
/*      */     
/*  544 */     JComboBox<Integer> jcbbPreset = new JComboBox<>();
/*  545 */     for (int j = 0; j <= 8; j++)
/*  546 */       jcbbPreset.addItem(Integer.valueOf(j)); 
/*  547 */     jcbbPreset.setSize(80, 23);
/*  548 */     jcbbPreset.setLocation(150, 38);
/*  549 */     jdlgFutoshiki.add(jcbbPreset);
/*  550 */     jcbbPreset.setBackground(Def.COLOR_BUTTONBG);
/*  551 */     jcbbPreset.setSelectedIndex(Op.getInt(Op.FU.FuHints.ordinal(), Op.fu));
/*      */     
/*  553 */     JLabel jlDiff = new JLabel("Difficulty:");
/*  554 */     jlDiff.setForeground(Def.COLOR_LABEL);
/*  555 */     jlDiff.setSize(70, 20);
/*  556 */     jlDiff.setLocation(20, 68);
/*  557 */     jlDiff.setHorizontalAlignment(4);
/*  558 */     jdlgFutoshiki.add(jlDiff);
/*      */     
/*  560 */     JComboBox<String> jcbbDifficulty = new JComboBox<>(diffString);
/*  561 */     jcbbDifficulty.setSize(120, 23);
/*  562 */     jcbbDifficulty.setLocation(110, 68);
/*  563 */     jdlgFutoshiki.add(jcbbDifficulty);
/*  564 */     jcbbDifficulty.setBackground(Def.COLOR_BUTTONBG);
/*  565 */     jcbbDifficulty.setSelectedIndex(Op.getInt(Op.FU.FuDifficulty.ordinal(), Op.fu));
/*      */     
/*  567 */     HowManyPuzzles hmp = new HowManyPuzzles(jdlgFutoshiki, 10, 105, this.howMany, this.startPuz, true);
/*      */     
/*  569 */     JButton jbOK = Methods.cweButton("OK", 13, 220, 80, 26, null);
/*  570 */     jbOK.addActionListener(e -> {
/*      */           int i = paramJComboBox1.getSelectedIndex() + 4; Op.setInt(Op.FU.FuSize.ordinal(), i, Op.fu); Grid.xSz = Grid.ySz = i;
/*      */           Op.setInt(Op.FU.FuHints.ordinal(), paramJComboBox2.getSelectedIndex(), Op.fu);
/*      */           if (Op.getInt(Op.FU.FuHints.ordinal(), Op.fu) < 0)
/*      */             Op.setInt(Op.FU.FuHints.ordinal(), 0, Op.fu); 
/*      */           if (Op.getInt(Op.FU.FuHints.ordinal(), Op.fu) > i)
/*      */             Op.setInt(Op.FU.FuHints.ordinal(), i, Op.fu); 
/*      */           this.howMany = Integer.parseInt(paramHowManyPuzzles.jtfHowMany.getText());
/*      */           this.startPuz = Integer.parseInt(paramHowManyPuzzles.jtfStartPuz.getText());
/*      */           Op.setInt(Op.FU.FuDifficulty.ordinal(), paramJComboBox3.getSelectedIndex(), Op.fu);
/*      */           Op.setBool(Op.SX.VaryDiff.ordinal(), Boolean.valueOf(paramHowManyPuzzles.jcbVaryDiff.isSelected()), Op.sx);
/*      */           Methods.clickedOK = true;
/*      */           paramJDialog.dispose();
/*      */           Methods.closeHelp();
/*      */         });
/*  585 */     jdlgFutoshiki.add(jbOK);
/*      */     
/*  587 */     JButton jbCancel = Methods.cweButton("Cancel", 13, 255, 80, 26, null);
/*  588 */     jbCancel.addActionListener(e -> {
/*      */           Methods.clickedOK = false;
/*      */           paramJDialog.dispose();
/*      */           Methods.closeHelp();
/*      */         });
/*  593 */     jdlgFutoshiki.add(jbCancel);
/*      */     
/*  595 */     JButton jbHelp = Methods.cweButton("<html><font size=6 color=BB0000 face=Serif>Help ", 100, 220, 150, 61, new ImageIcon("graphics/help.png"));
/*  596 */     jbHelp.addActionListener(e -> Methods.cweHelp(null, paramJDialog, "Futoshiki Options", this.futoshikiOptions));
/*      */     
/*  598 */     jdlgFutoshiki.add(jbHelp);
/*      */     
/*  600 */     Methods.setDialogSize(jdlgFutoshiki, 260, 291);
/*      */   }
/*      */   
/*      */   static void printOptions(JFrame jf, String type) {
/*  604 */     String[] colorLabel = { "Border Color", "Background Color", "Cell Color", "Number Color", "Guide Digit Color", "Inequality Color", "Error Color" };
/*  605 */     int[] colorInt = { Op.FU.FuBorder.ordinal(), Op.FU.FuBg.ordinal(), Op.FU.FuCell.ordinal(), Op.FU.FuNumbers.ordinal(), Op.FU.FuGuide.ordinal(), Op.FU.FuInequality.ordinal(), Op.FU.FuError.ordinal() };
/*  606 */     String[] fontLabel = { "Puzzle Font", "Guide Digit Font" };
/*  607 */     int[] fontInt = { Op.FU.FuFont.ordinal(), Op.FU.FuGuideFont.ordinal() };
/*  608 */     String[] checkLabel = { "PPrint Puzzle with color.", "SPrint Solution with color." };
/*  609 */     int[] checkInt = { Op.FU.FuPuzColor.ordinal(), Op.FU.FuSolColor.ordinal() };
/*  610 */     Methods.stdPrintOptions(jf, "Futoshiki " + type, Op.fu, colorLabel, colorInt, fontLabel, fontInt, checkLabel, checkInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void saveFutoshiki(String futoshikiName) {
/*      */     try {
/*  619 */       DataOutputStream dataOut = new DataOutputStream(new FileOutputStream("futoshiki/" + futoshikiName));
/*  620 */       dataOut.writeInt(Grid.xSz);
/*  621 */       dataOut.writeInt(Grid.ySz);
/*  622 */       dataOut.writeByte(Methods.noReveal);
/*  623 */       dataOut.writeByte(Methods.noErrors);
/*  624 */       for (int i = 0; i < 54; i++)
/*  625 */         dataOut.writeByte(0); 
/*  626 */       for (int j = 0; j < Grid.ySz; j++) {
/*  627 */         for (int k = 0; k < Grid.xSz; k++) {
/*  628 */           dataOut.writeInt(Grid.mode[k][j]);
/*  629 */           dataOut.writeInt(Grid.sol[k][j]);
/*  630 */           dataOut.writeInt(Grid.copy[k][j]);
/*  631 */           dataOut.writeInt(Grid.letter[k][j]);
/*      */         } 
/*  633 */       }  dataOut.writeUTF(Methods.puzzleTitle);
/*  634 */       dataOut.writeUTF(Methods.author);
/*  635 */       dataOut.writeUTF(Methods.copyright);
/*  636 */       dataOut.writeUTF(Methods.puzzleNumber);
/*  637 */       dataOut.writeUTF(Methods.puzzleNotes);
/*  638 */       dataOut.close();
/*      */     }
/*  640 */     catch (IOException exc) {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void loadFutoshiki(String futoshikiName) {
/*      */     
/*  648 */     try { File fl = new File("futoshiki/" + futoshikiName);
/*  649 */       if (!fl.exists()) {
/*  650 */         fl = new File("futoshiki/");
/*  651 */         String[] s = fl.list(); int k;
/*  652 */         for (k = 0; k < s.length && (
/*  653 */           s[k].lastIndexOf(".futoshiki") == -1 || s[k].charAt(0) == '.'); k++);
/*      */         
/*  655 */         if (k == s.length) { makeGrid(); return; }
/*  656 */          futoshikiName = s[k];
/*  657 */         Op.fu[Op.FU.FuPuz.ordinal()] = futoshikiName;
/*      */       } 
/*      */ 
/*      */       
/*  661 */       DataInputStream dataIn = new DataInputStream(new FileInputStream("futoshiki/" + futoshikiName));
/*  662 */       Grid.xSz = dataIn.readInt();
/*  663 */       Grid.ySz = dataIn.readInt();
/*  664 */       Methods.noReveal = dataIn.readByte();
/*  665 */       Methods.noErrors = dataIn.readByte(); int i;
/*  666 */       for (i = 0; i < 54; i++)
/*  667 */         dataIn.readByte(); 
/*  668 */       for (int j = 0; j < Grid.ySz; j++) {
/*  669 */         for (i = 0; i < Grid.xSz; i++) {
/*  670 */           Grid.mode[i][j] = dataIn.readInt();
/*  671 */           Grid.sol[i][j] = dataIn.readInt();
/*  672 */           Grid.copy[i][j] = dataIn.readInt();
/*  673 */           Grid.letter[i][j] = dataIn.readInt();
/*      */         } 
/*  675 */       }  Methods.puzzleTitle = dataIn.readUTF();
/*  676 */       Methods.author = dataIn.readUTF();
/*  677 */       Methods.copyright = dataIn.readUTF();
/*  678 */       Methods.puzzleNumber = dataIn.readUTF();
/*  679 */       Methods.puzzleNotes = dataIn.readUTF();
/*  680 */       dataIn.close(); }
/*      */     
/*  682 */     catch (IOException exc) { return; }
/*  683 */      Methods.havePuzzle = true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static void drawFutoshiki(Graphics2D g2, int[][] puzzleArray) {
/*  689 */     Stroke normalStroke = new BasicStroke(Grid.xCell / 25.0F, 2, 2);
/*  690 */     Stroke roundStroke = new BasicStroke(Grid.xCell / 25.0F, 1, 2);
/*  691 */     g2.setStroke(normalStroke);
/*      */     
/*  693 */     RenderingHints rh = g2.getRenderingHints();
/*  694 */     rh.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
/*  695 */     rh.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
/*  696 */     g2.setRenderingHints(rh);
/*      */ 
/*      */     
/*  699 */     g2.setColor(new Color(Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.FU.FuBg.ordinal(), Op.fu) : 16777215));
/*  700 */     g2.fillRect(Grid.xOrg - Grid.xCell / 5, Grid.yOrg - Grid.yCell / 5, Grid.xSz * Grid.xCell + Grid.xCell / 15, Grid.ySz * Grid.yCell + Grid.yCell / 15);
/*  701 */     g2.setColor(new Color(Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.FU.FuBorder.ordinal(), Op.fu) : 0));
/*  702 */     g2.drawRect(Grid.xOrg - Grid.xCell / 5, Grid.yOrg - Grid.yCell / 5, Grid.xSz * Grid.xCell + Grid.xCell / 15, Grid.ySz * Grid.yCell + Grid.yCell / 15);
/*      */     
/*  704 */     for (int j = 0; j < Grid.ySz; j++) {
/*  705 */       for (int k = 0; k < Grid.xSz; k++) {
/*  706 */         g2.setColor(new Color(Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.FU.FuCell.ordinal(), Op.fu) : 16777215));
/*  707 */         g2.fillRect(Grid.xOrg + k * Grid.xCell, Grid.yOrg + j * Grid.yCell, 2 * Grid.xCell / 3, 2 * Grid.yCell / 3);
/*  708 */         g2.setColor(new Color(Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.FU.FuBorder.ordinal(), Op.fu) : 0));
/*  709 */         g2.drawRect(Grid.xOrg + k * Grid.xCell, Grid.yOrg + j * Grid.yCell, 2 * Grid.xCell / 3, 2 * Grid.yCell / 3);
/*      */       } 
/*      */     } 
/*      */     
/*  713 */     if (Def.dispCursor.booleanValue()) {
/*  714 */       g2.setColor(Def.COLOR_RED);
/*  715 */       g2.drawRect(Grid.xOrg + Grid.xCur * Grid.xCell, Grid.yOrg + Grid.yCur * Grid.yCell, 2 * Grid.xCell / 3, 2 * Grid.yCell / 3);
/*      */     } 
/*      */ 
/*      */     
/*  719 */     g2.setColor(new Color(Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.FU.FuNumbers.ordinal(), Op.fu) : 0));
/*  720 */     g2.setFont(new Font(Op.fu[Op.FU.FuFont.ordinal()], 0, 5 * Grid.yCell / 10));
/*  721 */     FontMetrics fm = g2.getFontMetrics();
/*  722 */     for (int i = 0; i < Grid.ySz; i++) {
/*  723 */       for (int k = 0; k < Grid.xSz; k++) {
/*  724 */         char ch = (char)puzzleArray[k][i];
/*  725 */         if (ch != '\000') {
/*  726 */           int w = fm.stringWidth("" + ch);
/*  727 */           g2.drawString("" + ch, Grid.xOrg + k * Grid.xCell + (2 * Grid.xCell / 3 - w) / 2, Grid.yOrg + i * Grid.yCell + (2 * Grid.yCell / 3 + fm.getAscent() - fm.getDescent()) / 2);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  732 */     g2.setStroke(roundStroke);
/*  733 */     g2.setColor(new Color(Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.FU.FuInequality.ordinal(), Op.fu) : 0));
/*  734 */     for (int y = 0; y < Grid.ySz; y++) {
/*  735 */       for (int x = 0; x < Grid.xSz; x++) {
/*  736 */         int top = Grid.yOrg + y * Grid.yCell, left = Grid.xOrg + x * Grid.xCell;
/*  737 */         int rght = left + 2 * Grid.xCell / 3, bot = top + 2 * Grid.yCell / 3;
/*  738 */         int reach = Grid.xCell / 8;
/*      */         
/*  740 */         if ((Grid.mode[x][y] & 0x1) != 0) {
/*  741 */           int equalityOrgX = rght + Grid.xCell / 9, equalityOrgY = (top + bot) / 2;
/*  742 */           g2.drawLine(equalityOrgX, equalityOrgY, equalityOrgX + reach, equalityOrgY + reach);
/*  743 */           g2.drawLine(equalityOrgX, equalityOrgY, equalityOrgX + reach, equalityOrgY - reach);
/*      */         } 
/*  745 */         if ((Grid.mode[x][y] & 0x2) != 0) {
/*  746 */           int equalityOrgX = rght + Grid.xCell / 9 + reach, equalityOrgY = (top + bot) / 2;
/*  747 */           g2.drawLine(equalityOrgX, equalityOrgY, equalityOrgX - reach, equalityOrgY + reach);
/*  748 */           g2.drawLine(equalityOrgX, equalityOrgY, equalityOrgX - reach, equalityOrgY - reach);
/*      */         } 
/*  750 */         if ((Grid.mode[x][y] & 0x4) != 0) {
/*  751 */           int equalityOrgX = (rght + left) / 2, equalityOrgY = bot + Grid.yCell / 9;
/*  752 */           g2.drawLine(equalityOrgX, equalityOrgY, equalityOrgX + reach, equalityOrgY + reach);
/*  753 */           g2.drawLine(equalityOrgX, equalityOrgY, equalityOrgX - reach, equalityOrgY + reach);
/*      */         } 
/*  755 */         if ((Grid.mode[x][y] & 0x8) != 0) {
/*  756 */           int equalityOrgX = (rght + left) / 2, equalityOrgY = bot + Grid.yCell / 9 + reach;
/*  757 */           g2.drawLine(equalityOrgX, equalityOrgY, equalityOrgX + reach, equalityOrgY - reach);
/*  758 */           g2.drawLine(equalityOrgX, equalityOrgY, equalityOrgX - reach, equalityOrgY - reach);
/*      */         } 
/*      */       } 
/*      */     } 
/*  762 */     g2.setStroke(normalStroke);
/*      */   }
/*      */   
/*      */   static void printPuz(Graphics2D g2, int left, int top, int width, int height) {
/*  766 */     loadFutoshiki(Op.fu[Op.FU.FuPuz.ordinal()]);
/*  767 */     setSizesAndOffsets(left, top, width, height, 0);
/*  768 */     FutoshikiSolve.clearSolution();
/*  769 */     Def.dispWithColor = Op.getBool(Op.FU.FuPuzColor.ordinal(), Op.fu);
/*  770 */     boolean mem = Def.dispGuideDigits.booleanValue(); Def.dispGuideDigits = Boolean.valueOf(false);
/*  771 */     FutoshikiSolve.drawFutoshiki(g2, Grid.copy);
/*  772 */     Def.dispGuideDigits = Boolean.valueOf(mem);
/*  773 */     Def.dispWithColor = Boolean.valueOf(true);
/*      */   }
/*      */   
/*      */   static void printSol(Graphics2D g2, int left, int top, int width, int height, String solutionPuzzle) {
/*  777 */     loadFutoshiki(solutionPuzzle);
/*  778 */     setSizesAndOffsets(left, top, width, height, 0);
/*  779 */     Def.dispWithColor = Op.getBool(Op.FU.FuSolColor.ordinal(), Op.fu);
/*  780 */     drawFutoshiki(g2, Grid.letter);
/*  781 */     Def.dispWithColor = Boolean.valueOf(true);
/*  782 */     loadFutoshiki(Op.fu[Op.FU.FuPuz.ordinal()]);
/*      */   }
/*      */   
/*      */   static void printSolTitle(Graphics2D g2, int left, int top, int width, int height, String solutionPuzzle) {
/*  786 */     loadFutoshiki(solutionPuzzle);
/*  787 */     Print.outputTextItem(g2, left, top, width, height, "SansSerif", 0, Methods.puzzleTitle);
/*  788 */     loadFutoshiki(Op.fu[Op.FU.FuPuz.ordinal()]);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static void printSixpackPuz(Graphics2D g2, int left, int top, int dim, int gap, String puzName) {
/*  794 */     FontMetrics fm = g2.getFontMetrics();
/*      */     
/*  796 */     String st = Op.sx[Op.SX.SxFu.ordinal()];
/*  797 */     if (st.length() < 3) st = "FUTOSHIKI"; 
/*  798 */     int w = fm.stringWidth(st);
/*  799 */     g2.drawString(st, left + (dim - w) / 2, top - gap / 3);
/*  800 */     FutoshikiSolve.loadFutoshiki(puzName + ".futoshiki");
/*  801 */     setSizesAndOffsets(left, top, dim, dim, 0);
/*  802 */     FutoshikiSolve.clearSolution();
/*  803 */     Def.dispSolArray = Boolean.valueOf(true); FutoshikiSolve.drawFutoshiki(g2, Grid.copy); Def.dispSolArray = Boolean.valueOf(false);
/*  804 */     if (Op.sx[Op.SX.SxRuleLang.ordinal()].equals("English")) {
/*  805 */       st = rules;
/*      */     } else {
/*  807 */       st = Op.fu[Op.FU.FuRule1.ordinal() + Op.getInt(Op.SX.SxRuleLangIndex.ordinal(), Op.sx) - 1];
/*  808 */     }  String n1 = "" + Grid.xSz;
/*  809 */     st = st.replace("<n1>", n1);
/*  810 */     if (Op.getBool(Op.SX.SxInstructions.ordinal(), Op.sx).booleanValue()) {
/*  811 */       Methods.renderText(g2, left, top + dim + dim / 50, dim, dim / 4, "SansSerif", 1, st, 3, 4, true, 0, 0);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static void printSixpackSol(Graphics2D g2, int left, int top, int dim, int gap, String solName) {
/*  817 */     FontMetrics fm = g2.getFontMetrics();
/*      */     
/*  819 */     String st = Op.sx[Op.SX.SxFu.ordinal()];
/*  820 */     if (st.length() < 3) st = "FUTOSHIKI"; 
/*  821 */     int w = fm.stringWidth(st);
/*  822 */     g2.drawString(st, left + (dim - w) / 2, top - gap / 3);
/*  823 */     loadFutoshiki(solName + ".futoshiki");
/*  824 */     setSizesAndOffsets(left, top, dim, dim, 0);
/*  825 */     drawFutoshiki(g2, Grid.letter);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static void printKDPPuz(Graphics2D g2, int left, int top, int dim, int gap, String puzName) {
/*  831 */     FontMetrics fm = g2.getFontMetrics();
/*      */     
/*  833 */     String st = puzName;
/*  834 */     int w = fm.stringWidth(st);
/*  835 */     g2.drawString(st, left + (dim - w) / 2, top - gap / 3);
/*  836 */     FutoshikiSolve.loadFutoshiki(puzName + ".futoshiki");
/*  837 */     setSizesAndOffsets(left, top, dim, dim, 0);
/*  838 */     FutoshikiSolve.clearSolution();
/*  839 */     Def.dispSolArray = Boolean.valueOf(true); FutoshikiSolve.drawFutoshiki(g2, Grid.copy); Def.dispSolArray = Boolean.valueOf(false);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static void printKDPSol(Graphics2D g2, int left, int top, int dim, int gap, String solName) {
/*  845 */     FontMetrics fm = g2.getFontMetrics();
/*      */     
/*  847 */     String st = solName;
/*  848 */     int w = fm.stringWidth(st);
/*  849 */     g2.drawString(st, left + (dim - w) / 2, top - gap / 3);
/*  850 */     loadFutoshiki(solName + ".futoshiki");
/*  851 */     setSizesAndOffsets(left, top, dim, dim, 0);
/*  852 */     drawFutoshiki(g2, Grid.letter);
/*      */   }
/*      */   
/*      */   static void clearSol() {
/*  856 */     for (int j = 0; j < Grid.ySz; j++) {
/*  857 */       for (int i = 0; i < Grid.xSz; i++)
/*  858 */         Grid.sol[i][j] = Grid.copy[i][j]; 
/*      */     } 
/*      */   }
/*      */   static void makeGrid() {
/*  862 */     Methods.havePuzzle = false;
/*  863 */     Grid.clearGrid();
/*  864 */     Grid.xSz = Grid.ySz = Op.getInt(Op.FU.FuSize.ordinal(), Op.fu);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static void updateFutoshikiStatus(int x, int y, int i) {
/*  870 */     for (int j = 0; j < Grid.xSz; j++) {
/*  871 */       if (j != i) Grid.xstatus[x][y][j] = 0; 
/*  872 */       if (j != x) Grid.xstatus[j][y][i] = 0; 
/*  873 */       if (j != y) Grid.xstatus[x][j][i] = 0;
/*      */     
/*      */     } 
/*      */   }
/*      */   
/*      */   static void recalculateFutoshikiStatus() {
/*      */     int y;
/*  880 */     for (y = 0; y < Grid.ySz; y++) {
/*  881 */       for (int x = 0; x < Grid.xSz; x++) {
/*  882 */         for (int k = 0; k < Grid.xSz; k++)
/*  883 */           Grid.xstatus[x][y][k] = 1; 
/*      */       } 
/*  885 */     }  for (y = 0; y < Grid.ySz; y++) {
/*  886 */       for (int x = 0; x < Grid.xSz; x++) {
/*  887 */         if (Grid.sol[x][y] != 0)
/*  888 */           updateFutoshikiStatus(x, y, Grid.sol[x][y] - 49); 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   void fillFutoshiki() {
/*  893 */     boolean duplicate = false;
/*  894 */     Random r = new Random();
/*      */     int y;
/*  896 */     for (y = 0; y < Grid.ySz; y++) {
/*  897 */       for (int x = 0; x < Grid.xSz; x++)
/*  898 */       { Grid.sol[x][y] = r.nextInt(Grid.xSz - 1); Grid.mode[x][y] = r.nextInt(Grid.xSz - 1); } 
/*  899 */     }  for (int j = 0; j < Grid.xSz * Grid.xSz; ) {
/*  900 */       int x = j % Grid.xSz; y = j / Grid.xSz;
/*  901 */       if (duplicate) {
/*  902 */         Grid.sol[x][y] = (Grid.sol[x][y] + 1) % Grid.xSz;
/*  903 */         if (Grid.sol[x][y] == Grid.mode[x][y]) {
/*  904 */           j--;
/*      */           continue;
/*      */         } 
/*      */       } 
/*      */       while (true) {
/*  909 */         duplicate = false; int i;
/*  910 */         for (i = 0; i < x; i++) {
/*  911 */           if (Grid.sol[i][y] == Grid.sol[x][y])
/*  912 */             duplicate = true; 
/*  913 */         }  for (i = 0; i < y; i++) {
/*  914 */           if (Grid.sol[x][i] == Grid.sol[x][y])
/*  915 */             duplicate = true; 
/*  916 */         }  if (duplicate) {
/*  917 */           Grid.sol[x][y] = (Grid.sol[x][y] + 1) % Grid.xSz;
/*  918 */           if (Grid.sol[x][y] == Grid.mode[x][y])
/*  919 */             j--; 
/*      */           continue;
/*      */         } 
/*      */         break;
/*      */       } 
/*  924 */       j++;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  929 */     for (y = 0; y < Grid.ySz; y++) {
/*  930 */       for (int x = 0; x < Grid.xSz; x++) {
/*  931 */         Grid.mode[x][y] = 0;
/*  932 */         Grid.sol[x][y] = Grid.sol[x][y] + 49; Grid.letter[x][y] = Grid.sol[x][y] + 49;
/*      */       } 
/*      */     } 
/*  935 */     for (y = 0; y < Grid.ySz; y++) {
/*  936 */       for (int x = 0; x < Grid.xSz; x++) {
/*  937 */         if (x < Grid.xSz - 1)
/*  938 */           Grid.mode[x][y] = Grid.mode[x][y] | ((Grid.sol[x][y] < Grid.sol[x + 1][y]) ? 1 : 2); 
/*  939 */         if (y < Grid.ySz - 1) {
/*  940 */           Grid.mode[x][y] = Grid.mode[x][y] | ((Grid.sol[x][y] < Grid.sol[x][y + 1]) ? 4 : 8);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   void inequalityScanPhase1() {
/*  948 */     for (int j = 0; j < Grid.ySz; j++) {
/*  949 */       for (int i = 0; i < Grid.xSz; i++) {
/*  950 */         int k; int count; for (count = 0, k = 0; k + i < Grid.xSz - 1 && (
/*  951 */           Grid.mode[k + i][j] & 0x1) != 0; k++) count++;
/*      */         
/*  953 */         for (k = -1; k + i > -1 && (
/*  954 */           Grid.mode[k + i][j] & 0x2) != 0; k--) count++;
/*      */         
/*  956 */         for (k = 0; k < count; k++) {
/*  957 */           Grid.xstatus[i][j][Grid.xSz - 1 - k] = 0;
/*      */         }
/*  959 */         for (count = 0, k = 0; k + i < Grid.xSz - 1 && (
/*  960 */           Grid.mode[k + i][j] & 0x2) != 0; k++) count++;
/*      */         
/*  962 */         for (k = -1; k + i > -1 && (
/*  963 */           Grid.mode[k + i][j] & 0x1) != 0; k--) count++;
/*      */         
/*  965 */         for (k = 0; k < count; k++) {
/*  966 */           Grid.xstatus[i][j][k] = 0;
/*      */         }
/*  968 */         for (count = 0, k = 0; k + j < Grid.ySz - 1 && (
/*  969 */           Grid.mode[i][k + j] & 0x4) != 0; k++) {
/*  970 */           count++;
/*      */         }
/*      */         
/*  973 */         for (k = -1; k + j > -1 && (
/*  974 */           Grid.mode[i][k + j] & 0x8) != 0; k--) {
/*  975 */           count++;
/*      */         }
/*      */         
/*  978 */         for (k = 0; k < count; k++) {
/*  979 */           Grid.xstatus[i][j][Grid.ySz - 1 - k] = 0;
/*      */         }
/*  981 */         for (count = 0, k = 0; k + j < Grid.ySz - 1 && (
/*  982 */           Grid.mode[i][k + j] & 0x8) != 0; k++) {
/*  983 */           count++;
/*      */         }
/*      */         
/*  986 */         for (k = -1; k + j > -1 && (
/*  987 */           Grid.mode[i][k + j] & 0x4) != 0; k--) {
/*  988 */           count++;
/*      */         }
/*      */         
/*  991 */         for (k = 0; k < count; k++) {
/*  992 */           Grid.xstatus[i][j][k] = 0;
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   void inequalityScanPhase2() {
/* 1000 */     for (int j = 0; j < Grid.ySz; j++) {
/* 1001 */       for (int i = 0; i < Grid.xSz; i++) {
/* 1002 */         if ((Grid.mode[i][j] & 0x3) == 1) {
/* 1003 */           Grid.xstatus[i][j][Grid.xSz] = 0; int k;
/* 1004 */           for (k = Grid.xSz - 1; k > 0 && 
/* 1005 */             Grid.xstatus[i + 1][j][k] == 0; k--) {
/* 1006 */             Grid.xstatus[i][j][k - 1] = 0;
/*      */           }
/*      */           
/* 1009 */           Grid.xstatus[i + 1][j][0] = 0;
/* 1010 */           for (k = 0; k < 9 && 
/* 1011 */             Grid.xstatus[i][j][k] == 0; k++) {
/* 1012 */             Grid.xstatus[i + 1][j][k + 1] = 0;
/*      */           }
/*      */         } 
/*      */         
/* 1016 */         if ((Grid.mode[i][j] & 0x3) == 2) {
/* 1017 */           Grid.xstatus[i][j][0] = 0; int k;
/* 1018 */           for (k = 0; k < 9 && 
/* 1019 */             Grid.xstatus[i + 1][j][k] == 0; k++) {
/* 1020 */             Grid.xstatus[i][j][k + 1] = 0;
/*      */           }
/*      */           
/* 1023 */           Grid.xstatus[i + 1][j][Grid.xSz] = 0;
/* 1024 */           for (k = Grid.xSz - 1; k > 0 && 
/* 1025 */             Grid.xstatus[i][j][k] == 0; k--) {
/* 1026 */             Grid.xstatus[i + 1][j][k - 1] = 0;
/*      */           }
/*      */         } 
/*      */ 
/*      */         
/* 1031 */         if ((Grid.mode[i][j] & 0xC) == 4) {
/* 1032 */           Grid.xstatus[i][j][Grid.xSz] = 0; int k;
/* 1033 */           for (k = Grid.xSz - 1; k > 0 && 
/* 1034 */             Grid.xstatus[i][j + 1][k] == 0; k--) {
/* 1035 */             Grid.xstatus[i][j][k - 1] = 0;
/*      */           }
/*      */           
/* 1038 */           Grid.xstatus[i][j + 1][0] = 0;
/* 1039 */           for (k = 0; k < 9 && 
/* 1040 */             Grid.xstatus[i][j][k] == 0; k++) {
/* 1041 */             Grid.xstatus[i][j + 1][k + 1] = 0;
/*      */           }
/*      */         } 
/*      */         
/* 1045 */         if ((Grid.mode[i][j] & 0xC) == 8) {
/* 1046 */           Grid.xstatus[i][j][0] = 0; int k;
/* 1047 */           for (k = 0; k < 9 && 
/* 1048 */             Grid.xstatus[i][j + 1][k] == 0; k++) {
/* 1049 */             Grid.xstatus[i][j][k + 1] = 0;
/*      */           }
/*      */           
/* 1052 */           Grid.xstatus[i][j + 1][Grid.xSz] = 0;
/* 1053 */           for (k = Grid.xSz - 1; k > 0 && 
/* 1054 */             Grid.xstatus[i][j][k] == 0; k--) {
/* 1055 */             Grid.xstatus[i][j + 1][k - 1] = 0;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean singleCandidate() {
/* 1067 */     boolean found = false;
/*      */     
/* 1069 */     for (int y = 0; y < Grid.ySz; y++) {
/* 1070 */       for (int x = 0; x < Grid.xSz; x++) {
/* 1071 */         if (Grid.sol[x][y] == 0) {
/* 1072 */           int c; int i; for (c = i = 0; i < Grid.xSz; i++) {
/* 1073 */             if (Grid.xstatus[x][y][i] != 0)
/* 1074 */               c++; 
/* 1075 */           }  if (c == 1) {
/* 1076 */             found = true;
/* 1077 */             for (i = 0; i < Grid.xSz; i++) {
/* 1078 */               if (Grid.xstatus[x][y][i] != 0)
/* 1079 */               { Grid.sol[x][y] = i + 49;
/* 1080 */                 updateFutoshikiStatus(x, y, i); break; } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/* 1085 */     }  return found;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean singlePosition() {
/* 1092 */     int[] count = new int[Grid.xSz];
/* 1093 */     boolean found = false;
/*      */     
/*      */     int y;
/* 1096 */     for (y = 0; y < Grid.ySz; y++) {
/* 1097 */       for (int j = 0; j < Grid.xSz; count[j++] = 0); int k;
/* 1098 */       for (k = 0; k < Grid.xSz; k++) {
/* 1099 */         if (Grid.sol[k][y] == 0)
/* 1100 */           for (int m = 0; m < Grid.xSz; m++)
/* 1101 */           { if (Grid.xstatus[k][y][m] != 0)
/* 1102 */               count[m] = count[m] + 1;  }  
/* 1103 */       }  for (int i = 0; i < Grid.xSz; i++) {
/* 1104 */         if (count[i] == 1) {
/* 1105 */           found = true;
/* 1106 */           for (k = 0; k < Grid.xSz; k++) {
/* 1107 */             if (Grid.xstatus[k][y][i] != 0) {
/* 1108 */               Grid.sol[k][y] = i + 49;
/* 1109 */               updateFutoshikiStatus(k, y, i);
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/* 1116 */     for (int x = 0; x < Grid.xSz; x++) {
/* 1117 */       for (int j = 0; j < Grid.ySz; count[j++] = 0);
/* 1118 */       for (y = 0; y < Grid.ySz; y++) {
/* 1119 */         if (Grid.sol[x][y] == 0)
/* 1120 */           for (int k = 0; k < Grid.xSz; k++)
/* 1121 */           { if (Grid.xstatus[x][y][k] != 0)
/* 1122 */               count[k] = count[k] + 1;  }  
/* 1123 */       }  for (int i = 0; i < Grid.xSz; i++) {
/* 1124 */         if (count[i] == 1)
/* 1125 */           for (y = 0; y < Grid.ySz; y++) {
/* 1126 */             found = true;
/* 1127 */             if (Grid.xstatus[x][y][i] != 0) {
/* 1128 */               Grid.sol[x][y] = i + 49;
/* 1129 */               updateFutoshikiStatus(x, y, i); break;
/*      */             } 
/*      */           }  
/*      */       } 
/*      */     } 
/* 1134 */     return found;
/*      */   }
/*      */   
/*      */   boolean nakedSets() {
/* 1138 */     int[] guideMask = new int[36], guideMaskHits = new int[36];
/* 1139 */     boolean found = false;
/*      */     int i, m;
/* 1141 */     for (m = 0, i = 1; i < 256; i *= 2) {
/* 1142 */       for (int j = i * 2; j < 512; j *= 2)
/* 1143 */         guideMask[m++] = i | j; 
/*      */     } 
/*      */     int y;
/* 1146 */     for (y = 0; y < Grid.ySz; y++) {
/* 1147 */       int j; for (j = 0; j < 36; guideMaskHits[j++] = 0); int k;
/* 1148 */       for (k = 0; k < Grid.xSz; k++) {
/* 1149 */         int theMask; for (theMask = i = 0, j = 1; i < Grid.xSz; i++, j *= 2) {
/* 1150 */           if (Grid.xstatus[k][y][i] > 0)
/* 1151 */             theMask |= j; 
/* 1152 */         }  for (m = 0; m < 36; m++) {
/* 1153 */           if (theMask == guideMask[m])
/* 1154 */             guideMaskHits[m] = guideMaskHits[m] + 1; 
/*      */         } 
/* 1156 */       }  for (m = 0; m < 36; m++) {
/* 1157 */         if (guideMaskHits[m] == 2) {
/* 1158 */           for (k = 0; k < Grid.xSz; k++) {
/* 1159 */             int theMask; for (theMask = i = 0, j = 1; i < Grid.xSz; i++, j *= 2) {
/* 1160 */               if (Grid.xstatus[k][y][i] > 0)
/* 1161 */                 theMask |= j; 
/* 1162 */             }  if ((theMask & guideMask[m]) != 0 && (
/* 1163 */               theMask & (guideMask[m] ^ 0x1FF)) != 0) {
/* 1164 */               int n; for (j = 1, n = 0; n < Grid.xSz; n++, j *= 2) {
/* 1165 */                 if ((guideMask[m] & j) != 0)
/* 1166 */                   Grid.xstatus[k][y][n] = 0; 
/* 1167 */               }  found = true;
/*      */             } 
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1174 */     for (int x = 0; x < Grid.xSz; x++) {
/* 1175 */       int j; for (j = 0; j < 36; guideMaskHits[j++] = 0);
/* 1176 */       for (y = 0; y < Grid.ySz; y++) {
/* 1177 */         int theMask; for (theMask = i = 0, j = 1; i < Grid.xSz; i++, j *= 2) {
/* 1178 */           if (Grid.xstatus[x][y][i] > 0)
/* 1179 */             theMask |= j; 
/* 1180 */         }  for (m = 0; m < 36; m++) {
/* 1181 */           if (theMask == guideMask[m])
/* 1182 */             guideMaskHits[m] = guideMaskHits[m] + 1; 
/*      */         } 
/* 1184 */       }  for (m = 0; m < 36; m++) {
/* 1185 */         if (guideMaskHits[m] == 2)
/* 1186 */           for (y = 0; y < Grid.ySz; y++) {
/* 1187 */             int theMask; for (theMask = i = 0, j = 1; i < Grid.xSz; i++, j *= 2) {
/* 1188 */               if (Grid.xstatus[x][y][i] > 0)
/* 1189 */                 theMask |= j; 
/* 1190 */             }  if ((theMask & guideMask[m]) != 0 && (
/* 1191 */               theMask & (guideMask[m] ^ 0x1FF)) != 0) {
/* 1192 */               int k; for (j = 1, k = 0; k < Grid.xSz; k++, j *= 2) {
/* 1193 */                 if ((guideMask[m] & j) != 0)
/* 1194 */                   Grid.xstatus[x][y][k] = 0; 
/* 1195 */               }  found = true;
/*      */             } 
/*      */           }  
/*      */       } 
/*      */     } 
/* 1200 */     return found;
/*      */   }
/*      */   
/*      */   private boolean uniqueTest(int diffCont) {
/* 1204 */     boolean diff = false;
/*      */     
/* 1206 */     recalculateFutoshikiStatus();
/* 1207 */     inequalityScanPhase1();
/*      */     while (true) {
/* 1209 */       inequalityScanPhase2();
/* 1210 */       if (singleCandidate())
/* 1211 */         continue;  if (singlePosition() || nakedSets()) { diff = true; continue; }
/*      */        break;
/*      */     } 
/* 1214 */     for (int j = 0; j < Grid.xSz; j++) {
/* 1215 */       for (int i = 0; i < Grid.ySz; i++)
/* 1216 */       { if (Grid.sol[i][j] == 0)
/* 1217 */           return false;  } 
/* 1218 */     }  if (diffCont == 4) return true; 
/* 1219 */     if (diffCont == 0 && diff) return false; 
/* 1220 */     if (diffCont == 1 && !diff) return false;
/*      */     
/* 1222 */     return true;
/*      */   }
/*      */   
/*      */   private void multiBuild() {
/* 1226 */     String title = Methods.puzzleTitle;
/* 1227 */     int[] sizeDef = { 5, 5, 5, 6, 6, 7, 8 };
/* 1228 */     int[] numbDef = { 3, 2, 1, 5, 4, 6, 7 };
/* 1229 */     int[] diffDef = { 0, 0, 0, 1, 1, 1, 1 };
/*      */     
/* 1231 */     int saveFutoshikiSize = Op.getInt(Op.FU.FuSize.ordinal(), Op.fu);
/* 1232 */     int saveFutoshikiDiff = Op.getInt(Op.FU.FuDifficulty.ordinal(), Op.fu);
/* 1233 */     int saveFutoshikiNumb = Op.getInt(Op.FU.FuHints.ordinal(), Op.fu);
/*      */     
/* 1235 */     SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
/* 1236 */     Calendar c = Calendar.getInstance();
/*      */     
/* 1238 */     for (this.hmCount = 1; this.hmCount <= this.howMany; this.hmCount++) {
/* 1239 */       if (this.startPuz > 9999999) { try {
/* 1240 */           c.setTime(sdf.parse("" + this.startPuz));
/* 1241 */         } catch (ParseException ex) {}
/* 1242 */         this.startPuz = Integer.parseInt(sdf.format(c.getTime())); }
/*      */       
/* 1244 */       Methods.puzzleTitle = "FUTOSHIKI Puzzle : " + this.startPuz;
/* 1245 */       if (Op.getBool(Op.SX.VaryDiff.ordinal(), Op.sx).booleanValue()) {
/* 1246 */         Grid.xSz = Grid.ySz = sizeDef[(this.startPuz - 1) % 7];
/* 1247 */         Op.setInt(Op.FU.FuSize.ordinal(), sizeDef[(this.startPuz - 1) % 7], Op.fu);
/* 1248 */         Op.setInt(Op.FU.FuDifficulty.ordinal(), diffDef[(this.startPuz - 1) % 7], Op.fu);
/* 1249 */         Op.setInt(Op.FU.FuHints.ordinal(), numbDef[(this.startPuz - 1) % 7], Op.fu);
/*      */       } 
/*      */       
/* 1252 */       Methods.buildProgress(jfFutoshiki, Op.fu[Op.FU.FuPuz
/* 1253 */             .ordinal()] = "" + this.startPuz + ".futoshiki");
/* 1254 */       buildFutoshiki();
/* 1255 */       restoreFrame();
/* 1256 */       Wait.shortWait(100);
/* 1257 */       if (Def.building == 2)
/*      */         return; 
/* 1259 */       this.startPuz++;
/*      */     } 
/* 1261 */     this.howMany = 1;
/* 1262 */     Methods.puzzleTitle = title;
/* 1263 */     Op.setInt(Op.FU.FuSize.ordinal(), saveFutoshikiSize, Op.fu);
/* 1264 */     Op.setInt(Op.FU.FuDifficulty.ordinal(), saveFutoshikiDiff, Op.fu);
/* 1265 */     Op.setInt(Op.FU.FuHints.ordinal(), saveFutoshikiNumb, Op.fu);
/*      */   }
/*      */ 
/*      */   
/*      */   private void buildFutoshiki() {
/* 1270 */     int[] inequalityMaximum = { 0, 0, 0, 0, 4, 7, 10, 14, 24, 40 };
/* 1271 */     Random r = new Random();
/* 1272 */     int[] v = new int[162];
/*      */     int displayCount;
/*      */     boolean valid;
/* 1275 */     for (displayCount = 0, valid = false; !valid; ) {
/* 1276 */       Grid.clearGrid();
/*      */       
/* 1278 */       fillFutoshiki();
/*      */       int j;
/* 1280 */       for (j = 0; j < Grid.ySz; j++) {
/* 1281 */         for (int m = 0; m < Grid.xSz; m++) {
/* 1282 */           Grid.letter[m][j] = Grid.sol[m][j];
/* 1283 */           Grid.copy[m][j] = 0; Grid.sol[m][j] = 0;
/*      */         } 
/* 1285 */       }  int i; for (i = 0; i < Op.getInt(Op.FU.FuHints.ordinal(), Op.fu); ) {
/* 1286 */         int m = r.nextInt(Grid.xSz * Grid.xSz - 1);
/* 1287 */         int x = m % Grid.xSz, y = m / Grid.xSz;
/* 1288 */         if (Grid.copy[x][y] == 0) {
/* 1289 */           Grid.copy[x][y] = Grid.letter[x][y];
/* 1290 */           i++;
/*      */         } 
/*      */       } 
/*      */       
/* 1294 */       int w = 2 * Grid.xSz * Grid.xSz; int k;
/* 1295 */       for (k = 0; k < w; v[k] = k++);
/* 1296 */       for (k = 0; k < w; k++) {
/* 1297 */         int z = r.nextInt(w - 1);
/* 1298 */         int c = v[z]; v[z] = v[k]; v[k] = c;
/*      */       } 
/*      */       int inequalityCount;
/* 1301 */       for (inequalityCount = 0, k = 0; k < w; k++) {
/* 1302 */         int x = v[k] / 2 % Grid.xSz, y = v[k] / 2 / Grid.xSz, z = (v[k] % 2 == 0) ? 3 : 12;
/* 1303 */         if (x != Grid.xSz - 1 || z != 12)
/*      */         {
/* 1305 */           if (y != Grid.ySz - 1 || z != 3) {
/*      */ 
/*      */             
/* 1308 */             int mem = Grid.mode[x][y];
/* 1309 */             Grid.mode[x][y] = Grid.mode[x][y] & z;
/*      */             
/* 1311 */             for (j = 0; j < Grid.ySz; j++) {
/* 1312 */               for (i = 0; i < Grid.xSz; i++)
/* 1313 */                 Grid.sol[i][j] = Grid.copy[i][j]; 
/*      */             } 
/* 1315 */             if (!uniqueTest(Op.getInt(Op.FU.FuDifficulty.ordinal(), Op.fu)))
/* 1316 */             { Grid.mode[x][y] = mem;
/* 1317 */               inequalityCount++; }
/*      */             else
/*      */             
/* 1320 */             { valid = true; } 
/*      */           }  } 
/* 1322 */       }  if (inequalityCount > inequalityMaximum[Grid.xSz]) valid = false;
/*      */       
/* 1324 */       for (j = 0; j < Grid.ySz; j++) {
/* 1325 */         for (i = 0; i < Grid.xSz; i++)
/* 1326 */           Grid.sol[i][j] = Grid.copy[i][j]; 
/*      */       } 
/* 1328 */       if (Def.building == 2)
/*      */         return; 
/* 1330 */       if (this.howMany == 1 && displayCount++ % 100 == 0) {
/* 1331 */         restoreFrame();
/* 1332 */         Methods.buildProgress(jfFutoshiki, Op.fu[Op.FU.FuPuz.ordinal()]);
/*      */       } 
/*      */     } 
/* 1335 */     saveFutoshiki(Op.fu[Op.FU.FuPuz.ordinal()]);
/*      */   }
/*      */   
/*      */   static void updateGrid(MouseEvent e) {
/* 1339 */     int x = e.getX(), y = e.getY();
/*      */     
/* 1341 */     if (Def.building == 1)
/* 1342 */       return;  if (x < Grid.xOrg || y < Grid.yOrg)
/*      */       return; 
/* 1344 */     int i = (x - Grid.xOrg) / Grid.xCell;
/* 1345 */     int j = (y - Grid.yOrg) / Grid.yCell;
/* 1346 */     if (i >= Grid.xSz || j >= Grid.ySz)
/*      */       return; 
/* 1348 */     if (x < Grid.xOrg + i * Grid.xCell + 2 * Grid.xCell / 3 && y < Grid.yOrg + j * Grid.yCell + 2 * Grid.yCell / 3) {
/* 1349 */       Grid.xCur = i;
/* 1350 */       Grid.yCur = j;
/* 1351 */       pp.repaint();
/*      */       return;
/*      */     } 
/* 1354 */     int c = Grid.mode[i][j];
/* 1355 */     if (x > Grid.xOrg + i * Grid.xCell + 2 * Grid.xCell / 3 && y < Grid.yOrg + j * Grid.yCell + 2 * Grid.yCell / 3 && i != Grid.xSz - 1) {
/* 1356 */       c = c & 0xC | ((c & 0x3) + 1) % 3;
/* 1357 */     } else if (x < Grid.xOrg + i * Grid.xCell + 2 * Grid.xCell / 3 && y > Grid.yOrg + j * Grid.yCell + 2 * Grid.yCell / 3 && j != Grid.ySz - 1) {
/* 1358 */       c = c & 0x3 | ((c & 0xC) + 4) % 12;
/* 1359 */     }  Grid.mode[i][j] = c;
/* 1360 */     pp.repaint();
/*      */   } void handleKeyPressed(KeyEvent e) {
/*      */     char ch;
/*      */     int i;
/* 1364 */     if (Def.building == 1)
/* 1365 */       return;  if (e.isAltDown())
/* 1366 */       return;  switch (e.getKeyCode()) { case 38:
/* 1367 */         if (Grid.yCur > 0) Grid.yCur--;  break;
/* 1368 */       case 40: if (Grid.yCur < Grid.ySz - 1) Grid.yCur++;  break;
/* 1369 */       case 37: if (Grid.xCur > 0) Grid.xCur--;  break;
/* 1370 */       case 39: if (Grid.xCur < Grid.xSz - 1) Grid.xCur++;  break;
/* 1371 */       case 36: Grid.xCur = 0; break;
/* 1372 */       case 35: Grid.xCur = Grid.xSz - 1; break;
/* 1373 */       case 33: Grid.yCur = 0; break;
/* 1374 */       case 34: Grid.yCur = Grid.ySz - 1; break;
/*      */       case 8:
/*      */       case 127:
/* 1377 */         Grid.sol[Grid.xCur][Grid.yCur] = 0;
/*      */         break;
/*      */       default:
/* 1380 */         ch = e.getKeyChar();
/* 1381 */         if (ch == ' ') {
/* 1382 */           Grid.sol[Grid.xCur][Grid.yCur] = 0; break;
/*      */         } 
/* 1384 */         i = ch - 49;
/* 1385 */         if (i >= 0 && i < Grid.xSz) {
/* 1386 */           Grid.sol[Grid.xCur][Grid.yCur] = ch;
/*      */         }
/*      */         break; }
/*      */     
/* 1390 */     pp.repaint();
/*      */   }
/*      */ }


/* Location:              C:\Users\mustapha\Desktop\Crossword-Express.jar!\crosswordexpress\FutoshikiBuild.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */