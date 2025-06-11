/*      */ package crosswordexpress;
/*      */ import java.awt.Color;
/*      */ import java.awt.FontMetrics;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.Stroke;
/*      */ import java.awt.Toolkit;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.io.DataInputStream;
/*      */ import java.io.DataOutputStream;
/*      */ import javax.swing.JButton;
/*      */ import javax.swing.JDialog;
/*      */ import javax.swing.JLabel;
/*      */ import javax.swing.JMenuItem;
/*      */ import javax.swing.KeyStroke;
/*      */ 
/*      */ public class KsudokuBuild extends JPanel {
/*      */   static JFrame jfKsudoku;
/*      */   static JMenuBar menuBar;
/*      */   JMenu menu;
/*      */   JMenu submenu;
/*      */   JMenuItem menuItem;
/*      */   JMenuItem buildMenuItem;
/*   24 */   int howMany = 1; static JPanel pp; static int panelW; static int panelH; static JLabel jl1; static JLabel jl2; Timer myTimer; Thread thread; int startPuz = Integer.parseInt((new SimpleDateFormat("yyyyMMdd")).format(new Date())); int hmCount; static boolean sixpack; static boolean wizardRunning = false;
/*      */   static boolean activeMouse = false;
/*      */   static boolean activeKB = false;
/*      */   JDialog jdlgWizard;
/*      */   JTextField jtfCageScore;
/*   29 */   static int[] bit = new int[] { 2, 4, 8, 16, 32, 64, 128, 256, 512 };
/*   30 */   static CageDat[] cData = new CageDat[82];
/*   31 */   static UnitDat[] vData = new UnitDat[100];
/*   32 */   static int scoreEntryTarget = -1;
/*      */   
/*   34 */   static Color STRONG_LINK = new Color(34816);
/*   35 */   static Color WEAK_LINK = new Color(8978312);
/*   36 */   static int solution = 0; static int delete = 1;
/*   37 */   static Color[] candC = new Color[] { new Color(65484), new Color(16711935) };
/*   38 */   static int thisCage = -1;
/*      */   
/*   40 */   static String xStr = "                                                                           ";
/*      */   
/*      */   static boolean mouseDown = false;
/*   43 */   static String str = "";
/*      */   
/*   45 */   static byte[][] zone = new byte[][] { { 0, 0, 1, 1, 1, 2, 2, 2, 3, 4, 5, 6, 7, 8, 9, 9, 9, 3, 4, 5, 6, 7, 8, 10, 10, 10, 11, 12, 12, 6, 7, 13, 14, 14, 14, 11, 15, 15, 15, 13, 13, 13, 16, 16, 16, 17, 18, 18, 18, 13, 19, 20, 21, 21, 17, 22, 22, 22, 23, 19, 20, 24, 25, 26, 27, 27, 27, 23, 19, 20, 24, 25, 26, 28, 28, 28, 29, 29, 29, 30, 30 }, { 0, 1, 1, 2, 2, 2, 3, 3, 4, 0, 0, 0, 2, 5, 2, 3, 4, 4, 6, 7, 0, 8, 5, 9, 9, 4, 10, 6, 7, 11, 8, 12, 12, 12, 13, 10, 7, 7, 11, 14, 14, 14, 15, 13, 13, 16, 7, 17, 17, 17, 18, 15, 13, 19, 16, 20, 21, 21, 22, 18, 23, 13, 19, 20, 20, 24, 25, 22, 25, 23, 23, 23, 20, 24, 24, 25, 25, 25, 26, 26, 23 }, { 0, 0, 1, 2, 2, 2, 3, 3, 3, 0, 4, 1, 5, 6, 7, 3, 8, 8, 9, 4, 5, 5, 6, 7, 7, 10, 8, 9, 11, 5, 12, 6, 12, 7, 10, 10, 11, 11, 11, 12, 12, 12, 13, 13, 13, 14, 14, 15, 12, 16, 12, 17, 13, 18, 19, 14, 15, 15, 16, 17, 17, 20, 18, 19, 19, 21, 15, 16, 17, 22, 20, 23, 21, 21, 21, 24, 24, 24, 22, 23, 23 }, { 0, 0, 1, 2, 2, 3, 4, 4, 5, 0, 1, 1, 6, 6, 3, 7, 8, 5, 9, 9, 10, 10, 6, 3, 7, 8, 5, 11, 11, 12, 12, 13, 14, 14, 15, 16, 17, 18, 19, 19, 13, 20, 20, 15, 16, 17, 18, 21, 21, 13, 22, 22, 23, 23, 24, 25, 26, 27, 28, 29, 29, 30, 30, 24, 25, 26, 27, 28, 28, 31, 31, 32, 24, 33, 33, 27, 34, 34, 31, 32, 32 }, { 0, 0, 1, 2, 3, 3, 3, 4, 5, 6, 0, 1, 2, 7, 3, 8, 4, 5, 6, 9, 1, 10, 7, 7, 8, 8, 5, 11, 9, 9, 10, 12, 13, 13, 13, 14, 11, 11, 11, 12, 12, 12, 14, 14, 14, 11, 15, 15, 15, 12, 16, 17, 17, 14, 18, 19, 19, 20, 20, 16, 21, 17, 22, 18, 23, 19, 24, 20, 25, 21, 26, 22, 18, 23, 24, 24, 24, 25, 21, 26, 26 }, { 0, 0, 1, 1, 2, 2, 2, 3, 4, 5, 0, 6, 6, 7, 2, 8, 3, 4, 5, 9, 6, 10, 7, 7, 8, 11, 4, 12, 9, 9, 10, 13, 14, 14, 11, 15, 12, 12, 12, 13, 13, 13, 15, 15, 15, 12, 16, 17, 17, 13, 18, 19, 19, 15, 20, 16, 21, 22, 22, 18, 23, 19, 24, 20, 25, 21, 26, 22, 23, 23, 27, 24, 20, 25, 26, 26, 26, 28, 28, 27, 27 } };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   53 */   String ksudokuHelp = "<div><b>KILLER SUDOKU</b> puzzles have much in common with standard SUDOKU puzzles. They are solved by placing numbers into the puzzles in such a way that every row, column and 3X3 box contains each of the numbers 1 thru 9. However, there are no prefilled cells in the puzzle to get you started. Instead, the cells of the puzzle are collected into groups called cages having random shapes and sizes. Each cage includes a one or two digit score which is the sum of all the solution digits for the cells within the cage. Many innovative techniques are available to use this information to complete the solution.<br/><br/></div><span class='m'>Menu Functions</span><ul><li/><span class='s'>File Menu</span><ul><li/><span>Load a Puzzle</span><br/>Use this option to choose your puzzle from the pool of KILLER SUDOKU puzzles currently available on your computer.<p/><li/><span>SaveAs</span><br/>This option allows you to make an exact copy of the current puzzle using a different file name. The copy will be saved in the <b>ksudoku</b> folder along with all of the Ksudoku puzzles you have made. Alternatively, if you don't enter a new name for the puzzle, you can change the Puzzle Description, or any of the other descriptive items without changing the puzzle name.<p/><li/><span>Quit Construction</span><br/>Returns you to the Crossword Express opening screen.</ul><li/><span class='s'>Build Menu</span><ul><li/><span>Start a New Puzzle</span><br/>This option presents you with a dialog into which you can enter a file name for your new puzzle. You can also enter several other pieces of information such as a <b>Puzzle Title, Author</b> and <b>Copyright</b> information.<p/><li/><span>Build Options</span><br/>Use this option to access a Build Options dialog where you can set some rules to be followed by the puzzle building function as it builds your puzzle.<p/><li/><span>Start Building / Stop Building</span><br/>Construction of the puzzle will commence when you select the <b>Start Building</b> option. If puzzle building is successful you will receive a message containing the name of the puzzle file, and the location where it was saved. If it becomes necessary, you can interrupt the building process by selecting this option a second time. Note that during the construction phase, the text of this option is changed to <b>Stop Building</b><p/><li/><span>Manual Construction Wizard</span><br/>If you should find a Killer Sudoku puzzle published in the printed media, or elsewhere on the Internet, this function allows you to enter that puzzle into Crossword Express, so that you can use the integrated Solve function to assist you in solving the puzzle.</ul><li/><span class='s'>View Menu</span><ul><li/><span>Display Options</span><br/>This leads you to a dialog box in which you can change the colors of various elements within the puzzle, and control the fonts which will be used for the puzzle's text components. You can also decide if printing of the puzzle will be done in black and white or in color.</ul><li/><span class='s'>Tasks Menu</span><ul><li/><span>Print this Puzzle</span><br/>This will take you to a custom print screen where you can control the details involved with printing your puzzle.<p/><li/><span>Solve this Puzzle</span><br/>This will take you to a Solve screen which provides a fully interactive environment for solving the puzzle.<p/><li/><span>Delete this Puzzle</span><br/>Use this option to eliminate unwanted KILLER SUDOKU puzzles from your file system.</ul><li/><span class='s'>Help Menu</span><ul><li/><span>Ksudoku Help</span><br/>Displays the Help screen which you are now reading.</ul></ul></body>";
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
/*  115 */   String ksudokuOptions = "<div>The single control in this dialog is a drop down list which allows you to select the difficulty level of the puzzle you are about to create in the range from 2 to 19. This parallels the situation which applies in the case of standard SUDOKU puzzles with the exception that level 1 is not applicable to KILLER SUDOKU puzzles.</div></body>";
/*      */   
/*      */   int state;
/*      */   
/*      */   int score;
/*      */   
/*      */   static void def() {
/*  122 */     Op.updateOption(Op.KS.KsW.ordinal(), "750", Op.ks);
/*  123 */     Op.updateOption(Op.KS.KsH.ordinal(), "830", Op.ks);
/*  124 */     Op.updateOption(Op.KS.KsDifficulty.ordinal(), "2", Op.ks);
/*  125 */     Op.updateOption(Op.KS.KsCell.ordinal(), "FFFFFF", Op.ks);
/*  126 */     Op.updateOption(Op.KS.KsGrid.ordinal(), "66BBFF", Op.ks);
/*  127 */     Op.updateOption(Op.KS.KsBox.ordinal(), "000000", Op.ks);
/*  128 */     Op.updateOption(Op.KS.KsCage.ordinal(), "00AA00", Op.ks);
/*      */     
/*  130 */     Op.updateOption(Op.KS.KsNumber.ordinal(), "444444", Op.ks);
/*  131 */     Op.updateOption(Op.KS.KsGuide.ordinal(), "000066", Op.ks);
/*  132 */     Op.updateOption(Op.KS.KsHint.ordinal(), "0000FF", Op.ks);
/*  133 */     Op.updateOption(Op.KS.KsError.ordinal(), "FF0000", Op.ks);
/*  134 */     Op.updateOption(Op.KS.KsGuideBg.ordinal(), "FFFF00", Op.ks);
/*      */     
/*  136 */     Op.updateOption(Op.KS.KsPuz.ordinal(), "sample.ksudoku", Op.ks);
/*  137 */     Op.updateOption(Op.KS.KsFont.ordinal(), "SansSerif", Op.ks);
/*  138 */     Op.updateOption(Op.KS.KsGuideFont.ordinal(), "SansSerif", Op.ks);
/*  139 */     Op.updateOption(Op.KS.KsHintFont.ordinal(), "SansSerif", Op.ks);
/*  140 */     Op.updateOption(Op.KS.KsPuzColor.ordinal(), "true", Op.ks);
/*  141 */     Op.updateOption(Op.KS.KsSolColor.ordinal(), "true", Op.ks);
/*  142 */     Op.updateOption(Op.KS.KsAssist.ordinal(), "000002", Op.ks);
/*      */   }
/*      */   
/*      */   KsudokuBuild(JFrame jf, boolean auto, int hm, int start) {
/*  146 */     Def.puzzleMode = 112;
/*  147 */     Grid.xSz = Grid.ySz = 9;
/*  148 */     Def.building = 0;
/*  149 */     Def.dispCursor = Boolean.valueOf(false); Def.dispGuideDigits = Boolean.valueOf(false);
/*  150 */     makeGrid(0);
/*      */     
/*  152 */     jfKsudoku = new JFrame("Killer Sudoku");
/*  153 */     if (Op.getInt(Op.KS.KsH.ordinal(), Op.ks) > Methods.scrH - 200) {
/*  154 */       int diff = Op.getInt(Op.KS.KsH.ordinal(), Op.ks) - Op.getInt(Op.KS.KsW.ordinal(), Op.ks);
/*  155 */       Op.setInt(Op.KS.KsH.ordinal(), Methods.scrH - 200, Op.ks);
/*  156 */       Op.setInt(Op.KS.KsW.ordinal(), Methods.scrH - 200 + diff, Op.ks);
/*      */     } 
/*  158 */     jfKsudoku.setSize(Op.getInt(Op.KS.KsW.ordinal(), Op.ks), Op.getInt(Op.KS.KsH.ordinal(), Op.ks));
/*  159 */     int frameX = (jf.getX() + jfKsudoku.getWidth() > Methods.scrW) ? (Methods.scrW - jfKsudoku.getWidth() - 10) : jf.getX();
/*  160 */     jfKsudoku.setLocation(frameX, jf.getY());
/*  161 */     jfKsudoku.setLayout((LayoutManager)null);
/*  162 */     jfKsudoku.setDefaultCloseOperation(0);
/*  163 */     jfKsudoku
/*  164 */       .addComponentListener(new ComponentAdapter() {
/*      */           public void componentResized(ComponentEvent ce) {
/*  166 */             int oldw = Op.getInt(Op.KS.KsW.ordinal(), Op.ks);
/*  167 */             int oldh = Op.getInt(Op.KS.KsH.ordinal(), Op.ks);
/*  168 */             Methods.frameResize(KsudokuBuild.jfKsudoku, oldw, oldh, 750, 830);
/*  169 */             Op.setInt(Op.KS.KsW.ordinal(), KsudokuBuild.jfKsudoku.getWidth(), Op.ks);
/*  170 */             Op.setInt(Op.KS.KsH.ordinal(), KsudokuBuild.jfKsudoku.getHeight(), Op.ks);
/*  171 */             if (KsudokuBuild.wizardRunning)
/*  172 */               KsudokuBuild.this.jdlgWizard.setLocation(KsudokuBuild.jfKsudoku.getX() + KsudokuBuild.jfKsudoku.getWidth() + 10, KsudokuBuild.jfKsudoku.getY()); 
/*  173 */             KsudokuBuild.restoreFrame();
/*      */           }
/*      */           public void componentMoved(ComponentEvent ce) {
/*  176 */             if (KsudokuBuild.wizardRunning) {
/*  177 */               KsudokuBuild.this.jdlgWizard.setLocation(KsudokuBuild.jfKsudoku.getX() + KsudokuBuild.jfKsudoku.getWidth() + 10, KsudokuBuild.jfKsudoku.getY());
/*      */             }
/*      */           }
/*      */         });
/*  181 */     jfKsudoku
/*  182 */       .addWindowListener(new WindowAdapter() {
/*      */           public void windowClosing(WindowEvent we) {
/*  184 */             if (Def.building == 1 || Def.selecting)
/*  185 */               return;  if (KsudokuBuild.wizardRunning) {
/*  186 */               KsudokuBuild.restoreFrame();
/*  187 */               KsudokuBuild.this.jdlgWizard.dispose();
/*  188 */               KsudokuBuild.wizardRunning = false;
/*      */             } 
/*  190 */             Op.saveOptions("ksudoku.opt", Op.ks);
/*  191 */             CrosswordExpress.transfer(1, KsudokuBuild.jfKsudoku);
/*      */           }
/*      */         });
/*      */     
/*  195 */     Methods.closeHelp();
/*      */ 
/*      */     
/*  198 */     Runnable buildThread = () -> {
/*      */         if (this.howMany == 1) {
/*      */           buildKsudoku();
/*      */         } else {
/*      */           multiBuild();
/*      */           
/*      */           if (sixpack) {
/*      */             Sixpack.trigger();
/*      */             jfKsudoku.dispose();
/*      */             Def.building = 0;
/*      */             return;
/*      */           } 
/*      */         } 
/*      */         this.buildMenuItem.setText("Start Building");
/*      */         if (Def.building == 2) {
/*      */           Def.building = 0;
/*      */           Methods.interrupted(jfKsudoku);
/*      */           makeGrid(0);
/*      */           restoreFrame();
/*      */           return;
/*      */         } 
/*      */         Methods.havePuzzle = true;
/*      */         restoreFrame();
/*      */         Methods.puzzleSaved(jfKsudoku, "ksudoku", Op.ks[Op.KS.KsPuz.ordinal()]);
/*      */         Def.building = 0;
/*      */       };
/*  224 */     jl1 = new JLabel(); jfKsudoku.add(jl1);
/*  225 */     jl2 = new JLabel(); jfKsudoku.add(jl2);
/*      */ 
/*      */     
/*  228 */     menuBar = new JMenuBar();
/*  229 */     menuBar.setBackground(Def.COLOR_MENUBAR);
/*  230 */     jfKsudoku.setJMenuBar(menuBar);
/*      */     
/*  232 */     this.menu = new JMenu("File");
/*  233 */     menuBar.add(this.menu);
/*  234 */     this.menuItem = new JMenuItem("Load a Puzzle");
/*  235 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(76, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  236 */     this.menu.add(this.menuItem);
/*  237 */     this.menuItem
/*  238 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1 || wizardRunning)
/*      */             return; 
/*      */           pp.invalidate();
/*      */           pp.repaint();
/*      */           new Select(jfKsudoku, "ksudoku", "ksudoku", Op.ks, Op.KS.KsPuz.ordinal(), false);
/*      */         });
/*  245 */     this.menuItem = new JMenuItem("SaveAs");
/*  246 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(65, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  247 */     this.menu.add(this.menuItem);
/*  248 */     this.menuItem
/*  249 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1 || wizardRunning) {
/*      */             return;
/*      */           }
/*      */           Methods.puzzleDescriptionDialog(jfKsudoku, Op.ks[Op.KS.KsPuz.ordinal()].substring(0, Op.ks[Op.KS.KsPuz.ordinal()].indexOf(".ksudoku")), "ksudoku", ".ksudoku");
/*      */           if (Methods.clickedOK) {
/*      */             saveKsudoku(Op.ks[Op.KS.KsPuz.ordinal()] = Methods.theFileName);
/*      */             restoreFrame();
/*      */             Methods.puzzleSaved(jfKsudoku, "ksudoku", Op.ks[Op.KS.KsPuz.ordinal()]);
/*      */           } 
/*      */         });
/*  260 */     this.menuItem = new JMenuItem("Quit Construction");
/*  261 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(81, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  262 */     this.menu.add(this.menuItem);
/*  263 */     this.menuItem
/*  264 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           
/*      */           if (wizardRunning) {
/*      */             restoreFrame();
/*      */             this.jdlgWizard.dispose();
/*      */             wizardRunning = false;
/*      */           } 
/*      */           Op.saveOptions("ksudoku.opt", Op.ks);
/*      */           CrosswordExpress.transfer(1, jfKsudoku);
/*      */         });
/*  277 */     this.menu = new JMenu("Build");
/*  278 */     menuBar.add(this.menu);
/*  279 */     this.menuItem = new JMenuItem("Start a new Puzzle");
/*  280 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(78, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  281 */     this.menu.add(this.menuItem);
/*  282 */     this.menuItem
/*  283 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1 || wizardRunning) {
/*      */             return;
/*      */           }
/*      */           Methods.puzzleDescriptionDialog(jfKsudoku, Op.ks[Op.KS.KsPuz.ordinal()].substring(0, Op.ks[Op.KS.KsPuz.ordinal()].indexOf(".ksudoku")), "ksudoku", ".ksudoku");
/*      */           if (Methods.clickedOK)
/*      */             Op.ks[Op.KS.KsPuz.ordinal()] = Methods.theFileName; 
/*      */           restoreFrame();
/*      */         });
/*  292 */     this.menuItem = new JMenuItem("Build Options");
/*  293 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(79, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  294 */     this.menu.add(this.menuItem);
/*  295 */     this.menuItem
/*  296 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1 || wizardRunning)
/*      */             return; 
/*      */           ksudokuOptions();
/*      */           if (Methods.clickedOK) {
/*      */             makeGrid(0);
/*      */             if (this.howMany > 1)
/*      */               Op.ks[Op.KS.KsPuz.ordinal()] = "" + this.startPuz + ".ksudoku"; 
/*      */           } 
/*      */           restoreFrame();
/*      */         });
/*  307 */     this.buildMenuItem = new JMenuItem("Start Building");
/*  308 */     this.buildMenuItem.setAccelerator(KeyStroke.getKeyStroke(66, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  309 */     this.menu.add(this.buildMenuItem);
/*  310 */     this.buildMenuItem
/*  311 */       .addActionListener(ae -> {
/*      */           if (Op.ks[Op.KS.KsPuz.ordinal()].length() == 0 && this.howMany == 1) {
/*      */             Methods.noName(jfKsudoku);
/*      */             
/*      */             return;
/*      */           } 
/*      */           
/*      */           if (Def.building == 0 && !wizardRunning) {
/*      */             this.thread = new Thread(paramRunnable);
/*      */             this.thread.start();
/*      */             Def.building = 1;
/*      */             this.buildMenuItem.setText("Stop Building");
/*      */           } else {
/*      */             Def.building = 2;
/*      */           } 
/*      */         });
/*  327 */     this.menuItem = new JMenuItem("Manual Construction Wizard");
/*  328 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(87, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  329 */     this.menu.add(this.menuItem);
/*  330 */     this.menuItem
/*  331 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1 || wizardRunning) {
/*      */             return;
/*      */           }
/*      */           for (int y = 0; y < 9; y++) {
/*      */             for (int x = 0; x < 9; x++) {
/*      */               Grid.sol[x][y] = 0;
/*      */             }
/*      */           } 
/*      */           ksudokuWizard();
/*      */           restoreFrame();
/*      */         });
/*  343 */     this.menu = new JMenu("View");
/*  344 */     menuBar.add(this.menu);
/*  345 */     this.menuItem = new JMenuItem("Display Options");
/*  346 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(89, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  347 */     this.menu.add(this.menuItem);
/*  348 */     this.menuItem
/*  349 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1 || wizardRunning) {
/*      */             return;
/*      */           }
/*      */           
/*      */           printOptions(jfKsudoku, "Display Options");
/*      */           restoreFrame();
/*      */         });
/*  357 */     this.menu = new JMenu("Tasks");
/*  358 */     menuBar.add(this.menu);
/*  359 */     this.menuItem = new JMenuItem("Print this Puzzle");
/*  360 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(80, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  361 */     this.menu.add(this.menuItem);
/*  362 */     this.menuItem
/*  363 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1 || wizardRunning) {
/*      */             return;
/*      */           }
/*      */           CrosswordExpress.toPrint(jfKsudoku, Op.ks[Op.KS.KsPuz.ordinal()]);
/*      */         });
/*  369 */     this.menuItem = new JMenuItem("Solve this Puzzle");
/*  370 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(86, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  371 */     this.menu.add(this.menuItem);
/*  372 */     this.menuItem
/*  373 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1 || wizardRunning)
/*      */             return; 
/*      */           if (Methods.havePuzzle) {
/*      */             CrosswordExpress.transfer(113, jfKsudoku);
/*      */           } else {
/*      */             Methods.noPuzzle(jfKsudoku, "Solve");
/*      */           } 
/*      */         });
/*  382 */     this.menuItem = new JMenuItem("Delete this Puzzle");
/*  383 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(68, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  384 */     this.menu.add(this.menuItem);
/*  385 */     this.menuItem
/*  386 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1 || wizardRunning) {
/*      */             return;
/*      */           }
/*      */           
/*      */           if (Methods.deleteAPuzzle(jfKsudoku, Op.ks[Op.KS.KsPuz.ordinal()], "ksudoku", pp)) {
/*      */             makeGrid(0);
/*      */             loadKsudoku(Op.ks[Op.KS.KsPuz.ordinal()]);
/*      */             restoreFrame();
/*      */           } 
/*      */         });
/*  397 */     this.menu = new JMenu("Help");
/*  398 */     menuBar.add(this.menu);
/*  399 */     this.menuItem = new JMenuItem("Ksudoku Help");
/*  400 */     this.menu.add(this.menuItem);
/*  401 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(72, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  402 */     this.menuItem
/*  403 */       .addActionListener(ae -> Methods.cweHelp(jfKsudoku, null, "Building Ksudoku Puzzles", this.ksudokuHelp));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  408 */     pp = new KsudokuPP(0, 37);
/*  409 */     jfKsudoku.add(pp);
/*      */     
/*  411 */     pp
/*  412 */       .addMouseListener(new MouseAdapter() {
/*      */           public void mousePressed(MouseEvent e) {
/*  414 */             KsudokuBuild.this.handleMouseDown(e);
/*      */           }
/*      */         });
/*      */ 
/*      */     
/*  419 */     pp
/*  420 */       .addMouseMotionListener(new MouseAdapter() {
/*      */           public void mouseMoved(MouseEvent e) {
/*  422 */             if (Def.isMac) {
/*  423 */               KsudokuBuild.jfKsudoku.setResizable((KsudokuBuild.jfKsudoku.getWidth() - e.getX() < 15 && KsudokuBuild.jfKsudoku
/*  424 */                   .getHeight() - e.getY() < 95));
/*      */             }
/*      */           }
/*      */         });
/*      */     
/*  429 */     jfKsudoku
/*  430 */       .addKeyListener(new KeyAdapter() {
/*      */           public void keyPressed(KeyEvent e) {
/*  432 */             KsudokuBuild.this.handleKeyPressed(e);
/*      */           }
/*      */         });
/*      */ 
/*      */     
/*  437 */     loadKsudoku(Op.ks[Op.KS.KsPuz.ordinal()]);
/*  438 */     restoreFrame();
/*      */   }
/*      */   
/*      */   static void restoreFrame() {
/*  442 */     jfKsudoku.setVisible(true);
/*  443 */     Insets insets = jfKsudoku.getInsets();
/*  444 */     panelW = jfKsudoku.getWidth() - insets.left + insets.right;
/*  445 */     panelH = jfKsudoku.getHeight() - insets.top + insets.bottom + 37 + menuBar.getHeight();
/*  446 */     pp.setSize(panelW, panelH);
/*  447 */     pp.repaint();
/*  448 */     Methods.infoPanel(jl1, jl2, "Build Killer Sudoku", "Puzzle : " + Op.ks[Op.KS.KsPuz.ordinal()], panelW);
/*      */   }
/*      */   
/*      */   static void setSizesAndOffsets(int x, int y, int width, int height, int inset) {
/*  452 */     int i = (width - inset) / Grid.xSz;
/*  453 */     int j = (height - inset) / Grid.ySz;
/*  454 */     Grid.xCell = Grid.yCell = (i < j) ? i : j;
/*  455 */     Grid.xOrg = x + 10;
/*  456 */     Grid.yOrg = y + 10;
/*      */   }
/*      */   
/*      */   final void ksudokuOptions() {
/*  460 */     final JDialog jdlgKsudoku = new JDialog(jfKsudoku, "Ksudoku Options", true);
/*  461 */     jdlgKsudoku.setSize(260, 120);
/*  462 */     jdlgKsudoku.setResizable(false);
/*  463 */     jdlgKsudoku.setLayout((LayoutManager)null);
/*  464 */     jdlgKsudoku.setLocation(jfKsudoku.getX(), jfKsudoku.getY());
/*      */     
/*  466 */     jdlgKsudoku
/*  467 */       .addWindowListener(new WindowAdapter() {
/*      */           public void windowClosing(WindowEvent we) {
/*  469 */             Methods.closeHelp();
/*      */           }
/*      */         });
/*      */     
/*  473 */     Methods.closeHelp();
/*      */     
/*  475 */     JLabel jlDiff = new JLabel("Difficulty");
/*  476 */     jlDiff.setForeground(Def.COLOR_LABEL);
/*  477 */     jlDiff.setSize(120, 20);
/*  478 */     jlDiff.setLocation(0, 16);
/*  479 */     jlDiff.setHorizontalAlignment(4);
/*  480 */     jdlgKsudoku.add(jlDiff);
/*      */     
/*  482 */     final JComboBox<Integer> jcbbDiff = new JComboBox<>();
/*  483 */     for (int n = 2; n <= 19; n++)
/*  484 */       jcbbDiff.addItem(Integer.valueOf(n)); 
/*  485 */     jcbbDiff.setSize(50, 20);
/*  486 */     jcbbDiff.setLocation(130, 14);
/*  487 */     jdlgKsudoku.add(jcbbDiff);
/*  488 */     jcbbDiff.setBackground(Def.COLOR_BUTTONBG);
/*  489 */     jcbbDiff.setSelectedIndex(Op.getInt(Op.KS.KsDifficulty.ordinal(), Op.ks) - 2);
/*      */     
/*  491 */     Action doOK = new AbstractAction("OK") {
/*      */         public void actionPerformed(ActionEvent e) {
/*  493 */           Methods.clickedOK = true;
/*  494 */           jdlgKsudoku.dispose();
/*  495 */           Methods.closeHelp();
/*  496 */           Op.setInt(Op.KS.KsDifficulty.ordinal(), jcbbDiff.getSelectedIndex() + 2, Op.ks);
/*      */         }
/*      */       };
/*  499 */     JButton jbOK = Methods.newButton("doOK", doOK, 79, 13, 50, 80, 26);
/*  500 */     jdlgKsudoku.add(jbOK);
/*      */     
/*  502 */     Action doCancel = new AbstractAction("Cancel") {
/*      */         public void actionPerformed(ActionEvent e) {
/*  504 */           Methods.clickedOK = false;
/*  505 */           jdlgKsudoku.dispose();
/*  506 */           Methods.closeHelp();
/*      */         }
/*      */       };
/*  509 */     JButton jbCancel = Methods.newButton("doCancel", doCancel, 67, 13, 84, 80, 26);
/*  510 */     jdlgKsudoku.add(jbCancel);
/*      */     
/*  512 */     Action doHelp = new AbstractAction("<html><font size=6 color=BB0000 face=Serif>Help ", new ImageIcon("graphics/help.png")) {
/*      */         public void actionPerformed(ActionEvent e) {
/*  514 */           Methods.cweHelp(null, jdlgKsudoku, "Ksudoku Options", KsudokuBuild.this.ksudokuOptions);
/*      */         }
/*      */       };
/*  517 */     JButton jbHelp = Methods.newButton("doHelp", doHelp, 72, 100, 50, 155, 60);
/*  518 */     jdlgKsudoku.add(jbHelp);
/*      */     
/*  520 */     jdlgKsudoku.getRootPane().setDefaultButton(jbOK);
/*  521 */     Methods.setDialogSize(jdlgKsudoku, 260, 120);
/*      */   }
/*      */   
/*      */   static void printOptions(JFrame jf, String type) {
/*  525 */     String[] colorLabel = { "Cell Color", "Grid Color", "Box Border Color", "Cage Border Color", "Number Color", "Guide Digit Color", "Hint Color", "Error Color" };
/*      */ 
/*      */     
/*  528 */     int[] colorInt = { Op.KS.KsCell.ordinal(), Op.KS.KsGrid.ordinal(), Op.KS.KsBox.ordinal(), Op.KS.KsCage.ordinal(), Op.KS.KsNumber.ordinal(), Op.KS.KsGuide.ordinal(), Op.KS.KsHint.ordinal(), Op.KS.KsError.ordinal() };
/*  529 */     String[] fontLabel = { "Puzzle Font", "Guide Digit Font", "Hint Font" };
/*  530 */     int[] fontInt = { Op.KS.KsFont.ordinal(), Op.KS.KsGuideFont.ordinal(), Op.KS.KsHintFont.ordinal() };
/*  531 */     String[] checkLabel = { "PPrint Puzzle with color.", "SPrint Solution with color." };
/*  532 */     int[] checkInt = { Op.KS.KsPuzColor.ordinal(), Op.KS.KsSolColor.ordinal() };
/*  533 */     Methods.stdPrintOptions(jf, "Ksudoku " + type, Op.ks, colorLabel, colorInt, fontLabel, fontInt, checkLabel, checkInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void saveKsudoku(String ksudokuName) {
/*      */     try {
/*  544 */       DataOutputStream dataOut = new DataOutputStream(new FileOutputStream("ksudoku/" + ksudokuName));
/*  545 */       dataOut.writeInt(Grid.xSz);
/*  546 */       dataOut.writeInt(Grid.ySz);
/*  547 */       dataOut.writeByte(Methods.noReveal);
/*  548 */       dataOut.writeByte(Methods.noErrors); int i;
/*  549 */       for (i = 0; i < 54; i++)
/*  550 */         dataOut.writeByte(0); 
/*  551 */       for (int j = 0; j < Grid.ySz; j++) {
/*  552 */         for (i = 0; i < Grid.xSz; i++) {
/*  553 */           dataOut.writeInt(Grid.grid[i][j]);
/*  554 */           dataOut.writeInt(Grid.sol[i][j]);
/*  555 */           dataOut.writeInt(Grid.mode[i][j]);
/*  556 */           dataOut.writeInt(Grid.status[i][j]);
/*      */         } 
/*  558 */       }  dataOut.writeUTF(Methods.puzzleTitle);
/*  559 */       dataOut.writeUTF(Methods.author);
/*  560 */       dataOut.writeUTF(Methods.copyright);
/*  561 */       dataOut.writeUTF(Methods.puzzleNumber);
/*  562 */       dataOut.writeUTF(Methods.puzzleNotes);
/*      */       
/*  564 */       dataOut.writeInt(KsudokuSolve.undoIndex);
/*  565 */       for (i = 0; i < 750; i++) {
/*  566 */         dataOut.writeInt(KsudokuSolve.undoX[i]);
/*  567 */         dataOut.writeInt(KsudokuSolve.undoY[i]);
/*  568 */         dataOut.writeInt(KsudokuSolve.undoI[i]);
/*  569 */         dataOut.writeInt(KsudokuSolve.undoM[i]);
/*  570 */         dataOut.writeInt(KsudokuSolve.undoS[i]);
/*      */       } 
/*      */       
/*  573 */       dataOut.close();
/*      */     }
/*  575 */     catch (IOException exc) {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void loadKsudoku(String ksudokuName) {
/*      */     
/*  583 */     try { File fl = new File("ksudoku/" + ksudokuName);
/*  584 */       if (!fl.exists()) {
/*  585 */         fl = new File("ksudoku/");
/*  586 */         String[] s = fl.list(); int k;
/*  587 */         for (k = 0; k < s.length && (
/*  588 */           s[k].lastIndexOf(".ksudoku") == -1 || s[k].charAt(0) == '.'); k++);
/*      */         
/*  590 */         if (k == s.length) { makeGrid(0); return; }
/*  591 */          ksudokuName = s[k];
/*  592 */         Op.ks[Op.KS.KsPuz.ordinal()] = ksudokuName;
/*      */       } 
/*      */       
/*  595 */       DataInputStream dataIn = new DataInputStream(new FileInputStream("ksudoku/" + ksudokuName));
/*  596 */       Grid.xSz = dataIn.readInt();
/*  597 */       Grid.ySz = dataIn.readInt();
/*  598 */       Methods.noReveal = dataIn.readByte();
/*  599 */       Methods.noErrors = dataIn.readByte(); int i;
/*  600 */       for (i = 0; i < 54; i++)
/*  601 */         dataIn.readByte(); 
/*  602 */       for (int j = 0; j < Grid.ySz; j++) {
/*  603 */         for (i = 0; i < Grid.xSz; i++) {
/*  604 */           Grid.grid[i][j] = dataIn.readInt();
/*  605 */           Grid.sol[i][j] = dataIn.readInt();
/*  606 */           Grid.mode[i][j] = dataIn.readInt();
/*  607 */           Grid.status[i][j] = dataIn.readInt();
/*      */         } 
/*  609 */       }  Methods.puzzleTitle = dataIn.readUTF();
/*  610 */       Methods.author = dataIn.readUTF();
/*  611 */       Methods.copyright = dataIn.readUTF();
/*  612 */       Methods.puzzleNumber = dataIn.readUTF();
/*  613 */       Methods.puzzleNotes = dataIn.readUTF();
/*      */       
/*  615 */       KsudokuSolve.undoIndex = dataIn.readInt();
/*  616 */       for (i = 0; i < 750; i++) {
/*  617 */         KsudokuSolve.undoX[i] = dataIn.readInt();
/*  618 */         KsudokuSolve.undoY[i] = dataIn.readInt();
/*  619 */         KsudokuSolve.undoI[i] = dataIn.readInt();
/*  620 */         KsudokuSolve.undoM[i] = dataIn.readInt();
/*  621 */         KsudokuSolve.undoS[i] = dataIn.readInt();
/*      */       } 
/*      */       
/*  624 */       dataIn.close(); }
/*      */     
/*  626 */     catch (IOException exc) { return; }
/*  627 */      controlFromMode();
/*  628 */     getCageStats();
/*  629 */     Methods.havePuzzle = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void drawKsudoku(Graphics2D g2, String opt) {
/*  636 */     float[] dotPos = new float[11];
/*  637 */     int cBoxDim = 22 * Grid.xCell / 100, candOffset = 9 * Grid.xCell / 30;
/*  638 */     boolean hintsDrawn = false, redrawRequired = false;
/*      */     
/*  640 */     float endGap = Grid.xCell / 25.0F, span = Grid.xCell - 2.0F * endGap; int i;
/*  641 */     for (i = 0; i < 11; i++) {
/*  642 */       dotPos[i] = endGap + span / 20.0F + span * i / 10.0F;
/*      */     }
/*  644 */     Stroke normalStroke = new BasicStroke(Grid.xCell / 40.0F, 2, 2);
/*  645 */     Stroke narrowStroke = new BasicStroke(Grid.xCell / 45.0F, 2, 2);
/*  646 */     Stroke wideStroke = new BasicStroke(Grid.xCell / 20.0F, 1, 2);
/*  647 */     g2.setStroke(normalStroke);
/*      */     
/*  649 */     RenderingHints rh = g2.getRenderingHints();
/*  650 */     rh.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
/*  651 */     g2.setRenderingHints(rh);
/*      */     
/*      */     int j;
/*  654 */     for (j = 0; j < Grid.ySz; j++) {
/*  655 */       for (i = 0; i < Grid.xSz; i++) {
/*  656 */         g2.setColor(new Color(Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.KS.KsCell.ordinal(), Op.ks) : 16777215));
/*  657 */         if (Def.dispErrors.booleanValue() && ((Grid.grid[i][j] != 0 && Grid.grid[i][j] != Grid.sol[i][j]) || (Grid.grid[i][j] == 0 && (
/*      */           
/*  659 */           getStatus(i, j) & bit[Grid.sol[i][j] - 1]) == 0)))
/*  660 */           g2.setColor(new Color(16711680)); 
/*  661 */         g2.fillRect(Grid.xOrg + i * Grid.xCell, Grid.yOrg + j * Grid.yCell, Grid.xCell, Grid.yCell);
/*      */       } 
/*      */     } 
/*  664 */     if (Grid.findHint.booleanValue()) {
/*  665 */       g2.setColor(Def.COLOR_HINT_B);
/*  666 */       for (int k = 0; k < SudokuBuild.hintUnitIndex; k++) {
/*  667 */         int a, x, y; j = SudokuBuild.hintUnit[k].charAt(1) - 48;
/*  668 */         switch (SudokuBuild.hintUnit[k].charAt(0)) {
/*      */           case 'R':
/*  670 */             for (i = 0; i < 9; i++)
/*  671 */               g2.fillRect(Grid.xOrg + i * Grid.xCell, Grid.yOrg + j * Grid.yCell, Grid.xCell, Grid.yCell); 
/*      */             break;
/*      */           case 'C':
/*  674 */             for (i = 0; i < 9; i++)
/*  675 */               g2.fillRect(Grid.xOrg + j * Grid.xCell, Grid.yOrg + i * Grid.yCell, Grid.xCell, Grid.yCell); 
/*      */             break;
/*      */           case 'B':
/*  678 */             x = j % 3 * 3; y = j / 3 * 3;
/*  679 */             for (a = 0; a < 3; a++) {
/*  680 */               for (int b = 0; b < 3; b++) {
/*  681 */                 g2.fillRect(Grid.xOrg + (x + a) * Grid.xCell, Grid.yOrg + (y + b) * Grid.yCell, Grid.xCell, Grid.yCell);
/*      */               }
/*      */             } 
/*      */             break;
/*      */         } 
/*      */       } 
/*      */     } 
/*  688 */     if (SudokuBuild.hintIndexb > 0) {
/*  689 */       if (SudokuBuild.candc[0] == 0) {
/*  690 */         Grid.xCur = SudokuBuild.hintXb[0];
/*  691 */         Grid.yCur = SudokuBuild.hintYb[0];
/*      */       } 
/*  693 */       g2.setColor(Def.COLOR_HINT_B);
/*  694 */       for (i = 0; i < SudokuBuild.hintIndexb; i++) {
/*  695 */         g2.fillRect(Grid.xOrg + SudokuBuild.hintXb[i] * Grid.xCell, Grid.yOrg + SudokuBuild.hintYb[i] * Grid.yCell, Grid.xCell, Grid.yCell);
/*      */       }
/*      */     } 
/*  698 */     if (SudokuBuild.hintIndexg > 0) {
/*  699 */       g2.setColor(Def.COLOR_HINT_G);
/*  700 */       for (i = 0; i < SudokuBuild.hintIndexg; i++) {
/*  701 */         g2.fillRect(Grid.xOrg + SudokuBuild.hintXg[i] * Grid.xCell, Grid.yOrg + SudokuBuild.hintYg[i] * Grid.yCell, Grid.xCell, Grid.yCell);
/*      */       }
/*      */     } 
/*  704 */     if (SudokuBuild.hintIndexr > 0) {
/*  705 */       g2.setColor(Def.COLOR_HINT_R);
/*  706 */       for (i = 0; i < SudokuBuild.hintIndexr; i++) {
/*  707 */         g2.fillRect(Grid.xOrg + SudokuBuild.hintXr[i] * Grid.xCell, Grid.yOrg + SudokuBuild.hintYr[i] * Grid.yCell, Grid.xCell, Grid.yCell);
/*      */       }
/*      */     } 
/*  710 */     if (thisCage != -1) {
/*  711 */       int a; g2.setColor(Def.COLOR_HINT_R);
/*  712 */       switch ((cData[thisCage]).zone) {
/*      */         case 'B':
/*  714 */           g2.fillRect(Grid.xOrg + (cData[thisCage]).member % 3 * 3 * Grid.xCell, Grid.yOrg + (cData[thisCage]).member / 3 * 3 * Grid.yCell, 3 * Grid.xCell, 3 * Grid.yCell);
/*      */           break;
/*      */         
/*      */         case 'H':
/*  718 */           a = (cData[thisCage]).member;
/*  719 */           g2.fillRect(Grid.xOrg, Grid.yOrg + a / 10 * Grid.yCell, 9 * Grid.xCell, (a % 10 - a / 10 + 1) * Grid.yCell);
/*      */           break;
/*      */         case 'V':
/*  722 */           a = (cData[thisCage]).member;
/*  723 */           g2.fillRect(Grid.xOrg + a / 10 * Grid.xCell, Grid.yOrg, (a % 10 - a / 10 + 1) * Grid.xCell, 9 * Grid.yCell);
/*      */           break;
/*      */       } 
/*      */       
/*  727 */       g2.setColor(Def.COLOR_HINT_G);
/*  728 */       for (j = 0; j < (cData[thisCage]).area; j++) {
/*  729 */         g2.fillRect(Grid.xOrg + (cData[thisCage]).x[j] * Grid.xCell, Grid.yOrg + (cData[thisCage]).y[j] * Grid.yCell, Grid.xCell, Grid.yCell);
/*      */       }
/*      */     } 
/*      */     
/*  733 */     g2.setColor(Def.dispWithColor.booleanValue() ? new Color(Op.getColorInt(Op.KS.KsGrid.ordinal(), Op.ks)) : Def.COLOR_BLACK);
/*  734 */     g2.setStroke(normalStroke);
/*  735 */     for (j = 0; j < Grid.ySz; j++) {
/*  736 */       for (i = 0; i < Grid.xSz; i++) {
/*  737 */         g2.drawRect(Grid.xOrg + i * Grid.xCell, Grid.yOrg + j * Grid.yCell, Grid.xCell, Grid.yCell);
/*      */       }
/*      */     } 
/*  740 */     g2.setColor(Def.dispWithColor.booleanValue() ? new Color(Op.getColorInt(Op.KS.KsBox.ordinal(), Op.ks)) : Def.COLOR_BLACK);
/*  741 */     g2.setStroke(wideStroke);
/*  742 */     g2.drawLine(Grid.xOrg, Grid.yOrg + 3 * Grid.yCell, Grid.xOrg + 9 * Grid.xCell, Grid.yOrg + 3 * Grid.yCell);
/*  743 */     g2.drawLine(Grid.xOrg, Grid.yOrg + 6 * Grid.yCell, Grid.xOrg + 9 * Grid.xCell, Grid.yOrg + 6 * Grid.yCell);
/*  744 */     g2.drawLine(Grid.xOrg + 3 * Grid.xCell, Grid.yOrg, Grid.xOrg + 3 * Grid.xCell, Grid.yOrg + 9 * Grid.yCell);
/*  745 */     g2.drawLine(Grid.xOrg + 6 * Grid.xCell, Grid.yOrg, Grid.xOrg + 6 * Grid.xCell, Grid.yOrg + 9 * Grid.yCell);
/*  746 */     g2.drawRect(Grid.xOrg, Grid.yOrg, Grid.xSz * Grid.xCell, Grid.ySz * Grid.yCell);
/*      */ 
/*      */     
/*  749 */     g2.setColor(Def.dispWithColor.booleanValue() ? new Color(Op.getColorInt(Op.KS.KsCage.ordinal(), Op.ks)) : Def.COLOR_BLACK);
/*  750 */     g2.setStroke(normalStroke);
/*  751 */     float dotLen = Grid.xCell / 60.0F;
/*  752 */     for (j = 0; j < 9; j++) {
/*  753 */       for (i = 0; i < 9; i++) {
/*  754 */         if ((Grid.control[i][j] & 0x1) > 0) {
/*  755 */           int startx = Grid.xOrg + i * Grid.xCell;
/*  756 */           int starty = Grid.yOrg + j * Grid.yCell + Grid.yCell / 18;
/*  757 */           for (int k = 0; k < 10; k++)
/*  758 */             g2.drawLine(startx + (int)(dotPos[k] - dotLen), starty, startx + (int)(dotPos[k] + dotLen), starty); 
/*  759 */           if ((Grid.control[i][j] & 0x2) == 0)
/*  760 */             g2.drawLine(startx + (int)(dotPos[10] - dotLen), starty, startx + (int)(dotPos[10] + dotLen), starty); 
/*  761 */           if ((Grid.control[i][j] & 0x8) == 0) {
/*  762 */             g2.drawLine(startx - (int)dotLen, starty, startx + (int)dotLen, starty);
/*      */           }
/*      */         } 
/*  765 */         if ((Grid.control[i][j] & 0x2) > 0) {
/*  766 */           int startx = Grid.xOrg + (i + 1) * Grid.xCell - Grid.xCell / 18;
/*  767 */           int starty = Grid.yOrg + j * Grid.yCell;
/*  768 */           for (int k = 0; k < 10; k++)
/*  769 */             g2.drawLine(startx, starty + (int)(dotPos[k] - dotLen), startx, starty + (int)(dotPos[k] + dotLen)); 
/*  770 */           if ((Grid.control[i][j] & 0x4) == 0)
/*  771 */             g2.drawLine(startx, starty + (int)(dotPos[10] - dotLen), startx, starty + (int)(dotPos[10] + dotLen)); 
/*  772 */           if ((Grid.control[i][j] & 0x1) == 0) {
/*  773 */             g2.drawLine(startx, starty - (int)dotLen, startx, starty + (int)dotLen);
/*      */           }
/*      */         } 
/*  776 */         if ((Grid.control[i][j] & 0x4) > 0) {
/*  777 */           int startx = Grid.xOrg + i * Grid.xCell;
/*  778 */           int starty = Grid.yOrg + (j + 1) * Grid.yCell - Grid.yCell / 18;
/*  779 */           for (int k = 0; k < 10; k++)
/*  780 */             g2.drawLine(startx + (int)(dotPos[k] - dotLen), starty, startx + (int)(dotPos[k] + dotLen), starty); 
/*  781 */           if ((Grid.control[i][j] & 0x2) == 0)
/*  782 */             g2.drawLine(startx + (int)(dotPos[10] - dotLen), starty, startx + (int)(dotPos[10] + dotLen), starty); 
/*  783 */           if ((Grid.control[i][j] & 0x8) == 0) {
/*  784 */             g2.drawLine(startx - (int)dotLen, starty, startx + (int)dotLen, starty);
/*      */           }
/*      */         } 
/*  787 */         if ((Grid.control[i][j] & 0x8) > 0) {
/*  788 */           int startx = Grid.xOrg + i * Grid.xCell + Grid.xCell / 18;
/*  789 */           int starty = Grid.yOrg + j * Grid.yCell;
/*  790 */           for (int k = 0; k < 10; k++)
/*  791 */             g2.drawLine(startx, starty + (int)(dotPos[k] - dotLen), startx, starty + (int)(dotPos[k] + dotLen)); 
/*  792 */           if ((Grid.control[i][j] & 0x4) == 0)
/*  793 */             g2.drawLine(startx, starty + (int)(dotPos[10] - dotLen), startx, starty + (int)(dotPos[10] + dotLen)); 
/*  794 */           if ((Grid.control[i][j] & 0x1) == 0) {
/*  795 */             g2.drawLine(startx, starty - (int)dotLen, startx, starty + (int)dotLen);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*  800 */     for (int cageTarget = 0; j < 9; j++) {
/*  801 */       for (i = 0; i < 9; i++) {
/*  802 */         if (Grid.mode[i][j] == cageTarget) {
/*  803 */           if (cageTarget == scoreEntryTarget) {
/*  804 */             g2.setColor(new Color(16711680));
/*      */           } else {
/*  806 */             g2.setColor(new Color(Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.KS.KsCell.ordinal(), Op.ks) : 16777215));
/*  807 */           }  g2.fillRect(Grid.xOrg + i * Grid.xCell, Grid.yOrg + j * Grid.yCell, Grid.xCell / 3, Grid.yCell / 3);
/*  808 */           g2.setColor(new Color(Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.KS.KsNumber.ordinal(), Op.ks) : 0));
/*  809 */           g2.setStroke(narrowStroke);
/*  810 */           g2.drawRect(Grid.xOrg + i * Grid.xCell, Grid.yOrg + j * Grid.yCell, Grid.xCell / 3, Grid.yCell / 3);
/*  811 */           g2.setFont(new Font(Op.ks[Op.KS.KsFont.ordinal()], 0, 12 * Grid.yCell / 50));
/*  812 */           FontMetrics fontMetrics = g2.getFontMetrics();
/*  813 */           String st = "" + (cData[cageTarget]).score;
/*  814 */           g2.drawString("" + (cData[cageTarget]).score, Grid.xOrg + i * Grid.xCell + (Grid.xCell / 3 - fontMetrics.stringWidth(st)) / 2, Grid.yOrg + j * Grid.yCell + fontMetrics.getAscent());
/*  815 */           cageTarget++;
/*      */         } 
/*      */       } 
/*  818 */     }  if (Def.dispCursor.booleanValue()) {
/*  819 */       int c; for (c = 3; c < 768; c *= 4) {
/*  820 */         int x = Grid.xOrg + Grid.xCur * Grid.xCell, y = Grid.yOrg + Grid.yCur * Grid.yCell;
/*  821 */         g2.setStroke(wideStroke);
/*  822 */         g2.setColor(Def.COLOR_RED);
/*  823 */         switch (c) { case 3:
/*  824 */             g2.drawLine(x, y, x + Grid.xCell, y); break;
/*  825 */           case 12: g2.drawLine(x + Grid.xCell, y, x + Grid.xCell, y + Grid.yCell); break;
/*  826 */           case 48: g2.drawLine(x, y + Grid.yCell, x + Grid.xCell, y + Grid.yCell); break;
/*  827 */           case 192: g2.drawLine(x, y + Grid.yCell, x, y); break; }
/*      */       
/*      */       } 
/*      */     } 
/*  831 */     g2.setStroke(normalStroke);
/*  832 */     if (Def.dispCursor.booleanValue() && !Grid.findHint.booleanValue()) {
/*  833 */       g2.setColor(Def.COLOR_RED);
/*  834 */       g2.setStroke(wideStroke);
/*  835 */       g2.drawRect(Grid.xOrg + Grid.xCur * Grid.xCell, Grid.yOrg + Grid.yCur * Grid.yCell, Grid.xCell, Grid.yCell);
/*      */     } 
/*      */     
/*  838 */     if (Grid.findHint.booleanValue() && SudokuBuild.linkDatIndex > 0) {
/*  839 */       int k = SudokuBuild.linkCandidateNum - 1;
/*  840 */       for (i = 0; i < SudokuBuild.linkDatIndex; i++) {
/*  841 */         g2.setStroke(wideStroke);
/*  842 */         int x1 = (SudokuBuild.linkDat[i]).start.x, y1 = (SudokuBuild.linkDat[i]).start.y;
/*  843 */         int x2 = (SudokuBuild.linkDat[i]).end.x, y2 = (SudokuBuild.linkDat[i]).end.y;
/*      */         
/*  845 */         if ((SudokuBuild.linkDat[i]).Y > 1) {
/*  846 */           g2.setColor(STRONG_LINK);
/*  847 */           g2.drawLine(Grid.xOrg + x1 * Grid.xCell + candOffset + k % 3 * cBoxDim + cBoxDim / 2, Grid.yOrg + y1 * Grid.yCell + candOffset + k / 3 * cBoxDim + cBoxDim / 2, Grid.xOrg + x2 * Grid.xCell + candOffset + k % 3 * cBoxDim + cBoxDim / 2, Grid.yOrg + y2 * Grid.yCell + candOffset + k / 3 * cBoxDim + cBoxDim / 2);
/*      */ 
/*      */ 
/*      */           
/*  851 */           g2.setStroke(normalStroke);
/*  852 */           if ((getStatus(x1, y1) & bit[k]) > 0) {
/*  853 */             g2.setColor(((SudokuBuild.linkDat[i]).Y == 2) ? Def.COLOR_GREEN : Def.COLOR_RED);
/*  854 */             g2.fillRect(Grid.xOrg + x1 * Grid.xCell + candOffset + k % 3 * cBoxDim, Grid.yOrg + y1 * Grid.yCell + candOffset + k / 3 * cBoxDim, cBoxDim, cBoxDim);
/*      */             
/*  856 */             g2.setColor(new Color(Op.getColorInt(Op.SU.SuBorder.ordinal(), Op.su)));
/*  857 */             g2.drawRect(Grid.xOrg + x1 * Grid.xCell + candOffset + k % 3 * cBoxDim, Grid.yOrg + y1 * Grid.yCell + candOffset + k / 3 * cBoxDim, cBoxDim, cBoxDim);
/*      */             
/*  859 */             if (SudokuBuild.hintNum == 10) {
/*  860 */               hintsDrawn = true;
/*      */             }
/*      */           } 
/*  863 */           if ((getStatus(x2, y2) & bit[k]) > 0) {
/*  864 */             g2.setColor(((SudokuBuild.linkDat[i]).N == 2) ? Def.COLOR_GREEN : Def.COLOR_RED);
/*  865 */             g2.fillRect(Grid.xOrg + x2 * Grid.xCell + candOffset + k % 3 * cBoxDim, Grid.yOrg + y2 * Grid.yCell + candOffset + k / 3 * cBoxDim, cBoxDim, cBoxDim);
/*      */             
/*  867 */             g2.setColor(new Color(Op.getColorInt(Op.SU.SuBorder.ordinal(), Op.su)));
/*  868 */             g2.drawRect(Grid.xOrg + x2 * Grid.xCell + candOffset + k % 3 * cBoxDim, Grid.yOrg + y2 * Grid.yCell + candOffset + k / 3 * cBoxDim, cBoxDim, cBoxDim);
/*      */             
/*  870 */             if (SudokuBuild.hintNum == 10)
/*  871 */               hintsDrawn = true; 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*  876 */     g2.setStroke(normalStroke);
/*      */     
/*  878 */     if (Grid.findHint.booleanValue()) {
/*  879 */       int k = SudokuBuild.hintCandidate - 1;
/*  880 */       for (i = 0; i < SudokuBuild.hintCellIndex; i++) {
/*  881 */         g2.setStroke(wideStroke);
/*  882 */         g2.setColor(STRONG_LINK);
/*  883 */         g2.drawLine(Grid.xOrg + SudokuBuild.hintCellx[i] * Grid.xCell + candOffset + k % 3 * cBoxDim + cBoxDim / 2, Grid.yOrg + SudokuBuild.hintCelly[i] * Grid.yCell + candOffset + k / 3 * cBoxDim + cBoxDim / 2, Grid.xOrg + SudokuBuild.hintCellx[(i + 1) % SudokuBuild.hintCellIndex] * Grid.xCell + candOffset + k % 3 * cBoxDim + cBoxDim / 2, Grid.yOrg + SudokuBuild.hintCelly[(i + 1) % SudokuBuild.hintCellIndex] * Grid.yCell + candOffset + k / 3 * cBoxDim + cBoxDim / 2);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  889 */       for (i = 0; i < SudokuBuild.weakLinkIndex; i++) {
/*  890 */         g2.setStroke(wideStroke);
/*  891 */         g2.setColor(WEAK_LINK);
/*  892 */         g2.drawLine(Grid.xOrg + SudokuBuild.weakLinkx1[i] * Grid.xCell + candOffset + k % 3 * cBoxDim + cBoxDim / 2, Grid.yOrg + SudokuBuild.weakLinky1[i] * Grid.yCell + candOffset + k / 3 * cBoxDim + cBoxDim / 2, Grid.xOrg + SudokuBuild.weakLinkx2[i] * Grid.xCell + candOffset + k % 3 * cBoxDim + cBoxDim / 2, Grid.yOrg + SudokuBuild.weakLinky2[i] * Grid.yCell + candOffset + k / 3 * cBoxDim + cBoxDim / 2);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  898 */       for (i = 0; i < SudokuBuild.hintCellIndex; i++) {
/*  899 */         g2.setColor((i % 2 == 0) ? Def.COLOR_GREEN : Def.COLOR_RED);
/*  900 */         g2.fillRect(Grid.xOrg + SudokuBuild.hintCellx[i] * Grid.xCell + candOffset + k % 3 * cBoxDim, Grid.yOrg + SudokuBuild.hintCelly[i] * Grid.yCell + candOffset + k / 3 * cBoxDim, cBoxDim, cBoxDim);
/*      */         
/*  902 */         g2.setColor(new Color(Op.getColorInt(Op.SU.SuBorder.ordinal(), Op.su)));
/*  903 */         g2.setStroke(normalStroke);
/*  904 */         g2.drawRect(Grid.xOrg + SudokuBuild.hintCellx[i] * Grid.xCell + candOffset + k % 3 * cBoxDim, Grid.yOrg + SudokuBuild.hintCelly[i] * Grid.yCell + candOffset + k / 3 * cBoxDim, cBoxDim, cBoxDim);
/*      */       } 
/*      */ 
/*      */       
/*  908 */       for (i = 0; i < SudokuBuild.candIndex; i++) {
/*  909 */         if ((getStatus(SudokuBuild.candx[i], SudokuBuild.candy[i]) & bit[SudokuBuild.candv[i]]) != 0) {
/*  910 */           g2.setColor(candC[SudokuBuild.candc[i]]);
/*  911 */           g2.fillRect(Grid.xOrg + SudokuBuild.candx[i] * Grid.xCell + candOffset + SudokuBuild.candv[i] % 3 * cBoxDim, Grid.yOrg + SudokuBuild.candy[i] * Grid.yCell + candOffset + SudokuBuild.candv[i] / 3 * cBoxDim, cBoxDim, cBoxDim);
/*      */           
/*  913 */           g2.setColor(new Color(Op.getColorInt(Op.SU.SuBorder.ordinal(), Op.su)));
/*  914 */           g2.setStroke(normalStroke);
/*  915 */           g2.drawRect(Grid.xOrg + SudokuBuild.candx[i] * Grid.xCell + candOffset + SudokuBuild.candv[i] % 3 * cBoxDim, Grid.yOrg + SudokuBuild.candy[i] * Grid.yCell + candOffset + SudokuBuild.candv[i] / 3 * cBoxDim, cBoxDim, cBoxDim);
/*      */           
/*  917 */           hintsDrawn = true;
/*      */         } 
/*      */       } 
/*      */     } 
/*  921 */     g2.setStroke(normalStroke);
/*      */ 
/*      */     
/*  924 */     g2.setColor(new Color(Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.KS.KsNumber.ordinal(), Op.ks) : 0));
/*  925 */     g2.setFont(new Font(Op.ks[Op.KS.KsFont.ordinal()], 0, 8 * Grid.yCell / 10));
/*  926 */     FontMetrics fm = g2.getFontMetrics();
/*  927 */     int[][] thePuzzleArray = (opt.indexOf('G') != -1) ? Grid.grid : Grid.sol;
/*  928 */     for (j = 0; j < Grid.ySz; j++) {
/*  929 */       for (i = 0; i < Grid.xSz; i++) {
/*  930 */         int ch = thePuzzleArray[i][j];
/*  931 */         if (ch != 0) {
/*  932 */           int w = fm.stringWidth("" + ch);
/*  933 */           g2.drawString("" + ch, Grid.xOrg + i * Grid.xCell + (Grid.xCell - w) / 2, Grid.yOrg + j * Grid.yCell + (Grid.yCell + fm.getAscent() - fm.getDescent()) / 2);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  938 */     if (opt.indexOf('B') == -1) {
/*  939 */       g2.setFont(new Font(Op.ks[Op.KS.KsGuideFont.ordinal()], 0, cBoxDim));
/*  940 */       fm = g2.getFontMetrics();
/*  941 */       for (j = 0; j < Grid.ySz; j++) {
/*  942 */         for (i = 0; i < Grid.xSz; i++) {
/*  943 */           if (Grid.grid[i][j] == 0) {
/*  944 */             for (int k = 0; k < 9; k++) {
/*  945 */               if ((getStatus(i, j) & bit[k]) > 0) {
/*  946 */                 if (Grid.grid[Grid.xCur][Grid.yCur] == k + 1 && 
/*  947 */                   !Grid.findHint.booleanValue()) {
/*  948 */                   g2.setColor(new Color(Op.getColorInt(Op.KS.KsGuideBg.ordinal(), Op.ks)));
/*  949 */                   g2.fillRect(Grid.xOrg + i * Grid.xCell + candOffset + k % 3 * cBoxDim, Grid.yOrg + j * Grid.yCell + candOffset + k / 3 * cBoxDim, cBoxDim, cBoxDim);
/*      */                   
/*  951 */                   g2.setColor(new Color(Op.getColorInt(Op.KS.KsBox.ordinal(), Op.ks)));
/*  952 */                   g2.setStroke(normalStroke);
/*  953 */                   g2.drawRect(Grid.xOrg + i * Grid.xCell + candOffset + k % 3 * cBoxDim, Grid.yOrg + j * Grid.yCell + candOffset + k / 3 * cBoxDim, cBoxDim, cBoxDim);
/*      */                 } 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  959 */                 if (opt.indexOf('S') != -1) {
/*  960 */                   char ch = ((getStatus(i, j) & bit[k]) > 0) ? (char)(49 + k) : ' ';
/*  961 */                   g2.setColor(new Color(Op.getColorInt(Op.KS.KsGuide.ordinal(), Op.ks)));
/*  962 */                   int w = (cBoxDim - fm.stringWidth("" + ch)) / 2;
/*  963 */                   g2.drawString("" + ch, Grid.xOrg + i * Grid.xCell + candOffset + k % 3 * cBoxDim + w, Grid.yOrg + j * Grid.yCell + candOffset + k / 3 * cBoxDim + 9 * cBoxDim / 10);
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*  971 */     if (opt.indexOf('I') != -1) {
/*  972 */       for (i = 0; i < Grid.ySz; i++) {
/*  973 */         g2.setFont(new Font(Op.su[Op.SU.SuGuideFont.ordinal()], 1, 12 * Grid.yCell / 32));
/*  974 */         fm = g2.getFontMetrics();
/*  975 */         g2.setColor(new Color(11141120));
/*  976 */         int w = fm.stringWidth("" + (i + 1));
/*  977 */         g2.drawString("" + (i + 1), Grid.xOrg + i * Grid.xCell + (Grid.xCell - w) / 2, Grid.yOrg - Grid.yCell / 6);
/*  978 */         char ch = (char)(65 + i);
/*  979 */         g2.drawString("" + ch, Grid.xOrg - (6 * Grid.xCell / 10 + fm.charWidth(ch)) / 2, Grid.yOrg + i * Grid.yCell + Grid.yCell / 2 + fm.getAscent() / 2);
/*      */       } 
/*      */     }
/*  982 */     g2.setStroke(new BasicStroke(1.0F));
/*  983 */     if (!hintsDrawn)
/*  984 */       if (Grid.findHint.booleanValue()) {
/*  985 */         Grid.findHint = Boolean.valueOf(false);
/*  986 */         SudokuBuild.linkDatIndex = 0;
/*  987 */         if (!hintsDrawn) redrawRequired = true; 
/*  988 */         thisCage = -1;
/*  989 */         Methods.clearHintData();
/*  990 */         if (redrawRequired && !Grid.errorExists.booleanValue())
/*  991 */           KsudokuSolve.pp.repaint(); 
/*  992 */         Grid.errorExists = Boolean.valueOf(false);
/*      */       } else {
/*      */         
/*  995 */         SudokuBuild.hintIndexb = 0;
/*  996 */         if (!Grid.errorExists.booleanValue())
/*  997 */           Methods.closeHelp(); 
/*      */       }  
/*      */   }
/*      */   
/*      */   static int getStatus(int x, int y) {
/* 1002 */     if (Grid.grid[x][y] != 0) return 0; 
/* 1003 */     return Grid.status[x][y];
/*      */   }
/*      */ 
/*      */   
/*      */   static boolean cageSumEliminations(boolean findLast) {
/* 1008 */     int[] oldStatus = new int[9];
/* 1009 */     int[] newStatus = new int[9];
/* 1010 */     int[] unsolvedCell = new int[9];
/* 1011 */     int[] sum = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
/*      */     
/* 1013 */     boolean found = false;
/* 1014 */     String st = "";
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
/* 1040 */     for (int cNum = 0; (cData[cNum]).area > 0; cNum++) {
/* 1041 */       int cells = (cData[cNum]).area;
/* 1042 */       int pendingSum = (cData[cNum]).score; int k, cCount;
/* 1043 */       for (cCount = k = 0; k < cells; k++) {
/* 1044 */         if (Grid.grid[(cData[cNum]).x[k]][(cData[cNum]).y[k]] == 0) {
/* 1045 */           unsolvedCell[cCount] = k;
/* 1046 */           newStatus[cCount] = 0;
/* 1047 */           oldStatus[cCount++] = 
/* 1048 */             SudokuBuild.getStatus((cData[cNum]).x[k], (cData[cNum]).y[k]);
/*      */         } else {
/*      */           
/* 1051 */           pendingSum -= Grid.grid[(cData[cNum]).x[k]][(cData[cNum]).y[k]];
/*      */         } 
/*      */       } 
/* 1054 */       if (!findLast || cCount == 1) {
/*      */         int x; int y; int slot;
/* 1056 */         switch (cCount) { case 0:
/*      */             break;
/*      */           case 1:
/* 1059 */             x = (cData[cNum]).x[unsolvedCell[0]];
/* 1060 */             y = (cData[cNum]).y[unsolvedCell[0]];
/* 1061 */             if (Grid.findHint.booleanValue()) {
/* 1062 */               thisCage = cNum;
/* 1063 */               SudokuBuild.setCand(x, y, Grid.sol[x][y] - 1, 0);
/* 1064 */               Grid.hintTitle = "Last Cell in Cage";
/* 1065 */               if ((cData[cNum]).zone == 'Z') {
/* 1066 */                 SudokuBuild.hintNum = 17;
/* 1067 */                 Grid.hintWord[0] = "" + (cData[cNum]).score;
/* 1068 */                 Grid.hintWord[1] = "" + Grid.sol[x][y];
/* 1069 */                 Grid.hintWrdCnt = 2;
/*      */               } else {
/*      */                 int n;
/* 1072 */                 SudokuBuild.hintNum = 18;
/* 1073 */                 if ((cData[cNum]).zone == 'B') { n = 1; } else { n = (cData[cNum]).member % 10 - (cData[cNum]).member / 10 + 1; }
/* 1074 */                  Grid.hintWord[0] = "" + n;
/* 1075 */                 switch ((cData[cNum]).zone) { case 'B': st = "box"; break;case 'H': st = "row(s)"; break;case 'V': st = "col(s)"; break; }
/* 1076 */                  Grid.hintWord[1] = st;
/* 1077 */                 Grid.hintWord[2] = "" + (n * 45);
/* 1078 */                 Grid.hintWord[3] = "" + (n * 45 - (cData[cNum]).score);
/* 1079 */                 Grid.hintWord[4] = "" + (cData[cNum]).score;
/* 1080 */                 Grid.hintWrdCnt = 5;
/*      */               } 
/* 1082 */               return true;
/*      */             } 
/*      */             
/* 1085 */             Grid.grid[x][y] = pendingSum;
/* 1086 */             SudokuBuild.updateStatus(x, y, pendingSum - 1);
/* 1087 */             return true;
/*      */           
/*      */           default:
/* 1090 */             if (sum[slot = 0] != 0) slot = cCount - 1; 
/*      */             while (true) {
/*      */               int cand;
/* 1093 */               for (cand = sum[slot] + 1; cand < 10; cand++) {
/* 1094 */                 if ((oldStatus[slot] & bit[cand - 1]) != 0) {
/* 1095 */                   for (k = 0; k < cCount && 
/* 1096 */                     sum[k] != cand; k++);
/*      */                   
/* 1098 */                   if (k == cCount) {
/* 1099 */                     sum[slot] = cand;
/* 1100 */                     if (slot < cCount - 1) slot++; 
/*      */                     break;
/*      */                   } 
/*      */                 } 
/*      */               } 
/* 1105 */               if (cand == 10) {
/* 1106 */                 sum[slot] = 0;
/* 1107 */                 slot--;
/* 1108 */                 if (slot < 0)
/*      */                   break; 
/* 1110 */               }  if (sum[cCount - 1] == 0)
/*      */                 continue;  int calcSum; int slot2;
/* 1112 */               for (calcSum = slot2 = 0; slot2 < cCount; slot2++)
/* 1113 */                 calcSum += sum[slot2]; 
/* 1114 */               if (calcSum == pendingSum) {
/* 1115 */                 for (slot2 = 0; slot2 < cCount; slot2++) {
/* 1116 */                   newStatus[slot2] = newStatus[slot2] | bit[sum[slot2] - 1];
/*      */                 }
/*      */               }
/*      */             } 
/* 1120 */             for (k = 0; k < 9; k++) {
/* 1121 */               for (x = 0; x < cCount; x++) {
/* 1122 */                 if ((oldStatus[x] & bit[k]) != (newStatus[x] & bit[k])) {
/* 1123 */                   if (!Grid.findHint.booleanValue()) {
/* 1124 */                     Grid.status[(cData[cNum]).x[unsolvedCell[x]]][(cData[cNum]).y[unsolvedCell[x]]] = newStatus[x];
/* 1125 */                     return true;
/*      */                   } 
/*      */                   
/* 1128 */                   found = true;
/* 1129 */                   thisCage = cNum;
/* 1130 */                   SudokuBuild.hintXb[SudokuBuild.hintIndexb] = (cData[cNum]).x[unsolvedCell[x]];
/* 1131 */                   SudokuBuild.hintYb[SudokuBuild.hintIndexb++] = (cData[cNum]).y[unsolvedCell[x]];
/* 1132 */                   SudokuBuild.setCand((cData[cNum]).x[unsolvedCell[x]], (cData[cNum]).y[unsolvedCell[x]], k, 1);
/*      */                 } 
/*      */               } 
/*      */             } 
/* 1136 */             if (found) {
/* 1137 */               Grid.hintTitle = "Cage Sum Eliminations";
/* 1138 */               if ((cData[cNum]).zone == 'Z') {
/* 1139 */                 SudokuBuild.hintNum = 15;
/* 1140 */                 Grid.hintWord[0] = "" + (cData[cNum]).score;
/* 1141 */                 Grid.hintWrdCnt = 1;
/*      */               } else {
/*      */                 int n;
/* 1144 */                 SudokuBuild.hintNum = 19;
/* 1145 */                 if ((cData[cNum]).zone == 'B') { n = 1; } else { n = (cData[cNum]).member % 10 - (cData[cNum]).member / 10 + 1; }
/* 1146 */                  Grid.hintWord[0] = "" + n;
/* 1147 */                 switch ((cData[cNum]).zone) { case 'B': st = "box"; break;case 'H': st = "row(s)"; break;case 'V': st = "col(s)"; break; }
/* 1148 */                  Grid.hintWord[1] = st;
/* 1149 */                 Grid.hintWord[2] = "" + (n * 45);
/* 1150 */                 Grid.hintWord[3] = "" + (n * 45 - (cData[cNum]).score);
/* 1151 */                 Grid.hintWord[4] = "" + (cData[cNum]).score;
/* 1152 */                 Grid.hintWord[5] = "" + (cData[cNum]).score;
/* 1153 */                 Grid.hintWrdCnt = 6;
/*      */               } 
/* 1155 */               return true;
/*      */             }  break; }
/*      */       
/*      */       } 
/*      */     } 
/* 1160 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   static boolean removeRedundantCandidates() {
/* 1165 */     boolean found = false;
/*      */     
/* 1167 */     for (int y = 0; y < 9; y++) {
/* 1168 */       for (int x = 0; x < 9; x++) {
/* 1169 */         int val; if ((val = Grid.grid[x][y] - 1) >= 0) {
/* 1170 */           for (int z = 0; z < 9; z++) {
/* 1171 */             if (Grid.grid[z][y] == 0 && (getStatus(z, y) & bit[val]) > 0) {
/* 1172 */               found = true;
/* 1173 */               if (Grid.findHint.booleanValue()) {
/* 1174 */                 SudokuBuild.setCand(z, y, val, 1);
/*      */               } else {
/* 1176 */                 Grid.status[z][y] = Grid.status[z][y] ^ bit[val];
/*      */               } 
/* 1178 */             }  if (Grid.grid[x][z] == 0 && (getStatus(x, z) & bit[val]) > 0) {
/* 1179 */               found = true;
/* 1180 */               if (Grid.findHint.booleanValue()) {
/* 1181 */                 SudokuBuild.setCand(x, z, val, 1);
/*      */               } else {
/* 1183 */                 Grid.status[x][z] = Grid.status[x][z] ^ bit[val];
/*      */               } 
/* 1185 */             }  int x1 = 3 * x / 3 + z % 3, y1 = 3 * y / 3 + z / 3;
/* 1186 */             if (Grid.grid[x1][y1] == 0 && (getStatus(x1, y1) & bit[val]) > 0) {
/* 1187 */               found = true;
/* 1188 */               if (Grid.findHint.booleanValue()) {
/* 1189 */                 SudokuBuild.setCand(x1, y1, val, 1);
/*      */               } else {
/* 1191 */                 Grid.status[x1][y1] = Grid.status[x1][y1] ^ bit[val];
/*      */               } 
/*      */             } 
/*      */           } 
/* 1195 */           for (int cNum = 0; (cData[cNum]).area > 0; cNum++) {
/* 1196 */             int cells = (cData[cNum]).area;
/* 1197 */             for (int j = 0; j < cells; j++) {
/* 1198 */               if ((cData[cNum]).x[j] == x && (cData[cNum]).y[j] == y) {
/* 1199 */                 for (j = 0; j < cells; j++) {
/* 1200 */                   int x1 = (cData[cNum]).x[j];
/* 1201 */                   int y1 = (cData[cNum]).y[j];
/* 1202 */                   if (Grid.grid[x1][y1] == 0 && (getStatus(x1, y1) & bit[val]) > 0) {
/* 1203 */                     found = true;
/* 1204 */                     if (Grid.findHint.booleanValue()) {
/* 1205 */                       SudokuBuild.setCand(x1, y1, val, 1);
/*      */                     } else {
/* 1207 */                       Grid.status[x1][y1] = Grid.status[x1][y1] ^ bit[val];
/*      */                     } 
/*      */                   } 
/*      */                 } 
/*      */                 break;
/*      */               } 
/*      */             } 
/*      */           } 
/* 1215 */           if (found && Grid.findHint.booleanValue())
/* 1216 */           { SudokuBuild.hintXb[SudokuBuild.hintIndexb] = x;
/* 1217 */             SudokuBuild.hintYb[SudokuBuild.hintIndexb++] = y;
/* 1218 */             SudokuBuild.hintNum = 16;
/* 1219 */             Grid.hintWord[0] = "" + Grid.grid[x][y];
/* 1220 */             Grid.hintWord[1] = "" + Grid.grid[x][y];
/* 1221 */             Grid.hintWrdCnt = 2;
/* 1222 */             Grid.hintTitle = "Remove Redundant Candidates";
/* 1223 */             return true; } 
/*      */         } 
/*      */       } 
/* 1226 */     }  return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public static int nextHint() {
/* 1231 */     for (int j = 0; j < 9; j++) {
/* 1232 */       for (int i = 0; i < 9; i++) {
/* 1233 */         if ((Grid.grid[i][j] != 0 && Grid.sol[i][j] != 0 && Grid.grid[i][j] != Grid.sol[i][j]) || (Grid.grid[i][j] == 0 && (
/*      */ 
/*      */ 
/*      */           
/* 1237 */           getStatus(i, j) & bit[Grid.sol[i][j] - 1]) == 0))
/* 1238 */         { SudokuBuild.hintXb[SudokuBuild.hintIndexb] = i;
/* 1239 */           SudokuBuild.hintYb[SudokuBuild.hintIndexb] = j;
/* 1240 */           SudokuBuild.hintIndexb++; } 
/*      */       } 
/* 1242 */     }  if (SudokuBuild.hintIndexb > 0) {
/* 1243 */       Grid.hintTitle = "Error in Solution";
/* 1244 */       SudokuBuild.hintNum = 0;
/* 1245 */       Grid.errorExists = Boolean.valueOf(true);
/* 1246 */       Grid.findHint = Boolean.valueOf(false);
/* 1247 */       return 0;
/*      */     } 
/*      */     
/* 1250 */     if (removeRedundantCandidates()) return SudokuBuild.hintNum; 
/* 1251 */     if (SudokuBuild.singleCandidate() == 1) return SudokuBuild.hintNum; 
/* 1252 */     if (SudokuBuild.singlePosition().booleanValue()) return SudokuBuild.hintNum; 
/* 1253 */     if (cageSumEliminations(true)) return SudokuBuild.hintNum; 
/* 1254 */     if (SudokuBuild.nakedSets(2, 2)) return SudokuBuild.hintNum; 
/* 1255 */     if (SudokuBuild.nakedSets(3, 3)) return SudokuBuild.hintNum; 
/* 1256 */     if (SudokuBuild.nakedSets(4, 4)) return SudokuBuild.hintNum; 
/* 1257 */     if (SudokuBuild.unitIntersection().booleanValue()) return SudokuBuild.hintNum; 
/* 1258 */     if (cageSumEliminations(false)) return SudokuBuild.hintNum; 
/* 1259 */     if (SudokuBuild.hiddenSets(2, 2).booleanValue()) return SudokuBuild.hintNum; 
/* 1260 */     if (SudokuBuild.hiddenSets(3, 3).booleanValue()) return SudokuBuild.hintNum; 
/* 1261 */     if (SudokuBuild.hiddenSets(4, 4).booleanValue()) return SudokuBuild.hintNum; 
/* 1262 */     if (SudokuBuild.xWing(2).booleanValue()) return SudokuBuild.hintNum; 
/* 1263 */     if (SudokuBuild.singlesChain(0).booleanValue()) return SudokuBuild.hintNum; 
/* 1264 */     if (SudokuBuild.singlesChain(1).booleanValue()) return SudokuBuild.hintNum; 
/* 1265 */     if (SudokuBuild.yWing()) return SudokuBuild.hintNum; 
/* 1266 */     if (SudokuBuild.xWing(3).booleanValue()) return SudokuBuild.hintNum; 
/* 1267 */     if (SudokuBuild.xyzWing()) return SudokuBuild.hintNum; 
/* 1268 */     if (SudokuBuild.singlesChain(2).booleanValue()) return SudokuBuild.hintNum; 
/* 1269 */     if (SudokuBuild.singlesChain(3).booleanValue()) return SudokuBuild.hintNum; 
/* 1270 */     if (SudokuBuild.singlesChain(4).booleanValue()) return SudokuBuild.hintNum; 
/* 1271 */     if (SudokuBuild.xWing(4).booleanValue()) return SudokuBuild.hintNum; 
/* 1272 */     Grid.hintTitle = "No Hint Available";
/* 1273 */     return 14;
/*      */   }
/*      */   
/*      */   static void printPuz(Graphics2D g2, int left, int top, int width, int height) {
/* 1277 */     loadKsudoku(Op.ks[Op.KS.KsPuz.ordinal()]);
/* 1278 */     setSizesAndOffsets(left, top, width, height, 0);
/* 1279 */     Methods.clearGrid(Grid.sol);
/* 1280 */     Def.dispWithColor = Op.getBool(Op.KS.KsPuzColor.ordinal(), Op.ks);
/* 1281 */     drawKsudoku(g2, "G");
/* 1282 */     Def.dispWithColor = Boolean.valueOf(true);
/*      */   }
/*      */   
/*      */   static void printSol(Graphics2D g2, int left, int top, int width, int height, String solutionPuzzle) {
/* 1286 */     loadKsudoku(solutionPuzzle);
/* 1287 */     setSizesAndOffsets(left, top, width, height, 0);
/* 1288 */     Def.dispWithColor = Op.getBool(Op.KS.KsSolColor.ordinal(), Op.ks);
/* 1289 */     drawKsudoku(g2, "BS");
/* 1290 */     Def.dispWithColor = Boolean.valueOf(true);
/* 1291 */     loadKsudoku(Op.ks[Op.KS.KsPuz.ordinal()]);
/*      */   }
/*      */   
/*      */   static void printSolTitle(Graphics2D g2, int left, int top, int width, int height, String solutionPuzzle) {
/* 1295 */     loadKsudoku(solutionPuzzle);
/* 1296 */     Print.outputTextItem(g2, left, top, width, height, "SansSerif", 0, Methods.puzzleTitle);
/* 1297 */     loadKsudoku(Op.ks[Op.KS.KsPuz.ordinal()]);
/*      */   }
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
/*      */   static void getCageStats() {
/*      */     int x;
/* 1322 */     for (x = 0; x < 82; x++)
/* 1323 */       cData[x] = new CageDat(); 
/* 1324 */     for (x = 0; x < 100; x++)
/* 1325 */       vData[x] = new UnitDat(); 
/*      */     int cCount;
/* 1327 */     for (int y = 0; y < 9; y++) {
/* 1328 */       for (x = 0; x < 9; x++) {
/* 1329 */         int cNum = Grid.mode[x][y];
/* 1330 */         (cData[cNum]).area++;
/* 1331 */         (cData[cNum]).score += Grid.sol[x][y];
/* 1332 */         (cData[cNum]).x[(cData[cNum]).area - 1] = x;
/* 1333 */         (cData[cNum]).y[(cData[cNum]).area - 1] = y;
/* 1334 */         (cData[cCount]).zone = 'Z';
/* 1335 */         (cData[cCount]).member = 0;
/* 1336 */         if (cNum > cCount) cCount = cNum; 
/*      */       } 
/* 1338 */     }  cCount++;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static void buildVirtualCages() {
/* 1344 */     int cCount = 0;
/* 1345 */     while ((cData[cCount]).area != 0)
/* 1346 */       cCount++;  int pCount = cCount;
/*      */     
/*      */     int y;
/* 1349 */     for (y = 0; y < 9; ) { for (int x = 0; x < 9; ) { Grid.scratch[x][y] = 0; x++; }  y++; }
/* 1350 */      int i; for (i = 0; i < 9; ) { (vData[i]).area = 9; (vData[i]).score = 45; i++; }
/*      */     
/* 1352 */     for (i = 0; i < pCount; i++) {
/* 1353 */       int a = (cData[i]).area; int j;
/* 1354 */       for (j = 1; j < a && 
/* 1355 */         (cData[i]).x[j] / 3 == (cData[i]).x[0] / 3 && (cData[i]).y[j] / 3 == (cData[i]).y[0] / 3; j++);
/*      */       
/* 1357 */       if (j == a) {
/* 1358 */         for (int k = 0; k < a; k++)
/* 1359 */           Grid.scratch[(cData[i]).x[k]][(cData[i]).y[k]] = 1; 
/* 1360 */         int m = (cData[i]).y[0] / 3 * 3 + (cData[i]).x[0] / 3;
/* 1361 */         (vData[m]).area -= (cData[i]).area;
/* 1362 */         (vData[m]).score -= (cData[i]).score;
/*      */       } 
/*      */     } 
/*      */     
/* 1366 */     for (int box = 0; box < 9; box++) {
/* 1367 */       if ((vData[box]).area > 0 && (vData[box]).area < 8) {
/* 1368 */         (cData[cCount]).area = (vData[box]).area;
/* 1369 */         (cData[cCount]).score = (vData[box]).score;
/* 1370 */         int x = 3 * box % 3; y = 3 * box / 3;
/* 1371 */         for (int b = 0, v = b; b < 3; b++) {
/* 1372 */           for (int a = 0; a < 3; a++) {
/* 1373 */             if (Grid.scratch[x + a][y + b] == 0)
/* 1374 */             { (cData[cCount]).x[v] = x + a;
/* 1375 */               (cData[cCount]).y[v++] = y + b;
/* 1376 */               (cData[cCount]).zone = 'B';
/* 1377 */               (cData[cCount]).member = box; } 
/*      */           } 
/* 1379 */         }  cCount++;
/*      */       } 
/*      */     } 
/*      */     
/*      */     int q;
/* 1384 */     for (q = 0; q < 9; q++) {
/* 1385 */       for (int r = q; r < 9; r++) {
/* 1386 */         for (y = 0; y < 9; ) { for (int x = 0; x < 9; ) { Grid.scratch[x][y] = (y >= q && y <= r) ? 0 : -1; x++; }  y++; }
/* 1387 */          (vData[0]).area = (r - q + 1) * 9; (vData[0]).score = (r - q + 1) * 45;
/*      */         
/* 1389 */         for (i = 0; i < pCount; i++) {
/* 1390 */           int a = (cData[i]).area; int j;
/* 1391 */           for (j = 0; j < a && 
/* 1392 */             Grid.scratch[(cData[i]).x[j]][(cData[i]).y[j]] == 0; j++);
/* 1393 */           if (j == a) {
/* 1394 */             for (int k = 0; k < a; k++)
/* 1395 */               Grid.scratch[(cData[i]).x[k]][(cData[i]).y[k]] = 1; 
/* 1396 */             (vData[0]).area -= (cData[i]).area;
/* 1397 */             (vData[0]).score -= (cData[i]).score;
/*      */           } 
/*      */         } 
/*      */         
/* 1401 */         if ((vData[0]).area > 0 && (vData[0]).area < 9 && validVirtual("XYBC")) {
/* 1402 */           (cData[cCount]).area = (vData[0]).area;
/* 1403 */           (cData[cCount]).score = (vData[0]).score;
/* 1404 */           (cData[cCount]).zone = 'H';
/* 1405 */           (cData[cCount]).member = q * 10 + r;
/* 1406 */           for (int v = y = 0; y < 9; y++) {
/* 1407 */             for (int x = 0; x < 9; x++) {
/* 1408 */               if (Grid.scratch[x][y] == 0)
/* 1409 */               { (cData[cCount]).x[v] = x;
/* 1410 */                 (cData[cCount]).y[v++] = y; } 
/*      */             } 
/* 1412 */           }  cCount++;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1417 */     for (q = 0; q < 9; q++) {
/* 1418 */       for (int r = q; r < 9; r++) {
/* 1419 */         for (y = 0; y < 9; ) { for (int x = 0; x < 9; ) { Grid.scratch[x][y] = (x >= q && x <= r) ? 0 : -1; x++; }  y++; }
/* 1420 */          (vData[0]).area = (r - q + 1) * 9; (vData[0]).score = (r - q + 1) * 45;
/*      */         
/* 1422 */         for (i = 0; i < pCount; i++) {
/* 1423 */           int a = (cData[i]).area; int j;
/* 1424 */           for (j = 0; j < a && 
/* 1425 */             Grid.scratch[(cData[i]).x[j]][(cData[i]).y[j]] == 0; j++);
/* 1426 */           if (j == a) {
/* 1427 */             for (int k = 0; k < a; k++)
/* 1428 */               Grid.scratch[(cData[i]).x[k]][(cData[i]).y[k]] = 1; 
/* 1429 */             (vData[0]).area -= (cData[i]).area;
/* 1430 */             (vData[0]).score -= (cData[i]).score;
/*      */           } 
/*      */         } 
/*      */         
/* 1434 */         if ((vData[0]).area > 0 && (vData[0]).area < 9 && validVirtual("XYBC")) {
/* 1435 */           (cData[cCount]).area = (vData[0]).area;
/* 1436 */           (cData[cCount]).score = (vData[0]).score;
/* 1437 */           (cData[cCount]).zone = 'V';
/* 1438 */           (cData[cCount]).member = q * 10 + r;
/* 1439 */           for (int v = y = 0; y < 9; y++) {
/* 1440 */             for (int x = 0; x < 9; x++) {
/* 1441 */               if (Grid.scratch[x][y] == 0)
/* 1442 */               { (cData[cCount]).x[v] = x;
/* 1443 */                 (cData[cCount]).y[v++] = y; } 
/*      */             } 
/* 1445 */           }  cCount++;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   static boolean validVirtual(String ctrl) {
/* 1451 */     int thisX = 0, thisY = 0, thisB = 0, thisC = 0;
/*      */ 
/*      */     
/* 1454 */     boolean boolX = ctrl.contains("X");
/* 1455 */     boolean boolY = ctrl.contains("Y");
/* 1456 */     boolean boolB = ctrl.contains("B");
/* 1457 */     boolean boolC = ctrl.contains("C");
/*      */     
/* 1459 */     for (int i = 0; i < 81; i++) {
/* 1460 */       int x = i % 9, j = i / 9;
/* 1461 */       if (Grid.scratch[x][j] == 0) {
/* 1462 */         thisX = x;
/* 1463 */         thisY = j;
/* 1464 */         thisB = j / 3 * 3 + x / 3;
/* 1465 */         thisC = Grid.mode[x][j];
/*      */         break;
/*      */       } 
/*      */     } 
/* 1469 */     for (int y = 0; y < 9; y++) {
/* 1470 */       for (int x = 0; x < 9; x++) {
/* 1471 */         if (Grid.scratch[x][y] == 0) {
/* 1472 */           if (x != thisX) boolX = false; 
/* 1473 */           if (y != thisY) boolY = false; 
/* 1474 */           if (y / 3 * 3 + x / 3 != thisB) boolB = false; 
/* 1475 */           if (Grid.mode[x][y] != thisC) boolC = false; 
/*      */         } 
/*      */       } 
/* 1478 */     }  return (boolX || boolY || boolB || boolC);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static void manualConstruction() {
/* 1484 */     for (int y = 0; y < 9; y++) {
/* 1485 */       for (int x = 0; x < 9; x++) {
/* 1486 */         Grid.mode[x][y] = 100 + 9 * y + x;
/* 1487 */         Grid.control[x][y] = 15;
/*      */       } 
/* 1489 */     }  activeMouse = true;
/* 1490 */     restoreFrame();
/*      */   }
/*      */   
/*      */   private static void makeGrid(int z) {
/* 1494 */     Methods.havePuzzle = false;
/* 1495 */     Grid.clearGrid();
/* 1496 */     for (int y = 0; y < Grid.ySz; y++) {
/* 1497 */       for (int x = 0; x < Grid.xSz; x++) {
/* 1498 */         Grid.mode[x][y] = zone[z][y * 9 + x];
/* 1499 */         Grid.sol[x][y] = 0;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   static void controlFromMode() {
/* 1506 */     for (int y = 0; y < 9; y++) {
/* 1507 */       for (int x = 0; x < 9; x++) {
/* 1508 */         Grid.control[x][y] = 0;
/* 1509 */         if (y == 0 || Grid.mode[x][y] != Grid.mode[x][y - 1])
/* 1510 */           Grid.control[x][y] = (byte)(Grid.control[x][y] | 0x1); 
/* 1511 */         if (x == 8 || Grid.mode[x][y] != Grid.mode[x + 1][y])
/* 1512 */           Grid.control[x][y] = (byte)(Grid.control[x][y] | 0x2); 
/* 1513 */         if (y == 8 || Grid.mode[x][y] != Grid.mode[x][y + 1])
/* 1514 */           Grid.control[x][y] = (byte)(Grid.control[x][y] | 0x4); 
/* 1515 */         if (x == 0 || Grid.mode[x][y] != Grid.mode[x - 1][y])
/* 1516 */           Grid.control[x][y] = (byte)(Grid.control[x][y] | 0x8); 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   static void modeFromControl() {
/*      */     int y;
/* 1523 */     for (y = 0; y < 9; y++) {
/* 1524 */       for (int x = 0; x < 9; x++) {
/* 1525 */         Grid.mode[x][y] = -1;
/* 1526 */         Grid.sol[x][y] = 0;
/*      */       } 
/*      */     } 
/* 1529 */     for (int count = y = 0; y < 9; y++) {
/* 1530 */       for (int x = 0; x < 9; x++) {
/* 1531 */         if (recurse(x, y, count) == 1)
/* 1532 */           count++; 
/*      */       } 
/*      */     } 
/*      */   } static int recurse(int x, int y, int count) {
/* 1536 */     if (Grid.mode[x][y] >= 0) return 0; 
/* 1537 */     Grid.mode[x][y] = count;
/* 1538 */     if ((Grid.control[x][y] & 0x1) == 0)
/* 1539 */       recurse(x, y - 1, count); 
/* 1540 */     if ((Grid.control[x][y] & 0x2) == 0)
/* 1541 */       recurse(x + 1, y, count); 
/* 1542 */     if ((Grid.control[x][y] & 0x4) == 0)
/* 1543 */       recurse(x, y + 1, count); 
/* 1544 */     if ((Grid.control[x][y] & 0x8) == 0)
/* 1545 */       recurse(x - 1, y, count); 
/* 1546 */     return 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void ksudokuWizard() {
/* 1553 */     this.state = 1;
/*      */     
/* 1555 */     this.jdlgWizard = new JDialog(jfKsudoku);
/* 1556 */     this.jdlgWizard.setUndecorated(true);
/* 1557 */     this.jdlgWizard.setSize(330, 490);
/* 1558 */     this.jdlgWizard.setResizable(false);
/* 1559 */     this.jdlgWizard.setLayout((LayoutManager)null);
/* 1560 */     this.jdlgWizard.setLocation(jfKsudoku.getX() + jfKsudoku.getWidth() + 10, jfKsudoku.getY());
/* 1561 */     this.jdlgWizard.setVisible(true);
/* 1562 */     wizardRunning = true;
/*      */     
/* 1564 */     String namePuzzle = "Enter the name of the file into which the puzzle will be saved after construction<p><p>There are several other input fields and options which you can also use. Use the Help button to see complete instructions.<p><p>Click OK to continue, or click Cancel to abandon the process, and terminate the Wizard.";
/*      */ 
/*      */ 
/*      */     
/* 1568 */     String buildCages = "Define the size and location of your cages.<p><p>A mouse click near any cell border will remove the cage boundary from that cell. A second click will replace it if you make a mistake.<p><p>Click OK when you have defined all cages.";
/*      */ 
/*      */ 
/*      */     
/* 1572 */     final String cageTotals = "Type the score value for the highlighted cage directly into the puzzle. Type the Enter key to move to the next cage.<p><p>Delete or Backspace will delete one digit from the score, or move to the previous cage score if the current cage score is zero.<p><p>When you have entered all of the cage scores, click OK to validate the puzzle.";
/*      */ 
/*      */ 
/*      */     
/* 1576 */     final String validated = "Your puzzle has passed the Crossword Express validation process successfully.<p><p>It has been saved to the hard drive using the file name which you provided.<p><p>Click OK to complete the process, and terminate the Wizard.";
/*      */ 
/*      */ 
/*      */     
/* 1580 */     final String invalid = "Your puzzle failed to pass the validation process.<p><p>The information you entered may be invalid, you may have made an error, or perhaps the puzzle is just too difficult for the program to solve.<p><p>Click OK to terminate the Wizard.";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1586 */     final JLabel wizardLabel = new JLabel(Methods.wizardText("Name your Puzzle.", namePuzzle));
/* 1587 */     wizardLabel.setSize(320, 430);
/* 1588 */     wizardLabel.setLocation(5, 5);
/* 1589 */     wizardLabel.setHorizontalAlignment(0);
/* 1590 */     this.jdlgWizard.add(wizardLabel);
/*      */     
/* 1592 */     Action doOK = new AbstractAction("OK")
/*      */       {
/*      */         public void actionPerformed(ActionEvent e) {
/* 1595 */           switch (KsudokuBuild.this.state) {
/*      */             case 1:
/* 1597 */               KsudokuBuild.activeMouse = false;
/* 1598 */               KsudokuBuild.modeFromControl();
/* 1599 */               KsudokuBuild.getCageStats();
/* 1600 */               wizardLabel.setText(Methods.wizardText("Enter Cage Totals", cageTotals));
/* 1601 */               KsudokuBuild.scoreEntryTarget = 0;
/* 1602 */               KsudokuBuild.restoreFrame();
/* 1603 */               KsudokuBuild.activeKB = true;
/* 1604 */               KsudokuBuild.this.state = 2;
/*      */               break;
/*      */             case 2:
/* 1607 */               KsudokuBuild.scoreEntryTarget = -1;
/* 1608 */               KsudokuBuild.activeKB = false;
/* 1609 */               KsudokuBuild.buildVirtualCages();
/* 1610 */               KsudokuSolve.setCageValues();
/*      */               
/* 1612 */               if (SudokuBuild.solveSudoku(19) > 0L) {
/* 1613 */                 for (int y = 0; y < 9; y++) {
/* 1614 */                   for (int x = 0; x < 9; x++) {
/* 1615 */                     Grid.sol[x][y] = Grid.grid[x][y];
/* 1616 */                     Grid.grid[x][y] = 0;
/*      */                   } 
/* 1618 */                 }  KsudokuSolve.clearSolution();
/* 1619 */                 if ((Op.getInt(Op.KS.KsAssist.ordinal(), Op.ks) & 0x2) == 2) {
/* 1620 */                   KsudokuSolve.setCageValues();
/*      */                 } else {
/* 1622 */                   KsudokuSolve.recalculateStatus();
/* 1623 */                 }  KsudokuSolve.undoIndex = 0;
/* 1624 */                 KsudokuBuild.saveKsudoku(Op.ks[Op.KS.KsPuz.ordinal()]);
/* 1625 */                 Methods.havePuzzle = true;
/* 1626 */                 wizardLabel.setText(Methods.wizardText("Validated and Saved", validated));
/*      */               } else {
/*      */                 
/* 1629 */                 wizardLabel.setText(Methods.wizardText("Invalid Puzzle", invalid));
/* 1630 */               }  KsudokuBuild.this.state = 3;
/*      */               break;
/*      */             case 3:
/* 1633 */               KsudokuBuild.restoreFrame();
/* 1634 */               KsudokuBuild.this.jdlgWizard.dispose();
/* 1635 */               KsudokuBuild.wizardRunning = false;
/*      */               break;
/*      */           } 
/*      */         }
/*      */       };
/* 1640 */     JButton jbOK = Methods.newButton("doOK", doOK, 87, 10, 450, 40, 26);
/* 1641 */     jbOK.setVisible(false);
/* 1642 */     this.jdlgWizard.add(jbOK);
/*      */     
/* 1644 */     Methods.puzzleDescriptionDialog(jfKsudoku, Op.ks[Op.KS.KsPuz.ordinal()].substring(0, Op.ks[Op.KS.KsPuz.ordinal()].indexOf(".ksudoku")), "ksudoku", ".ksudoku");
/* 1645 */     if (Methods.clickedOK) {
/* 1646 */       Op.ks[Op.KS.KsPuz.ordinal()] = Methods.theFileName;
/* 1647 */       jbOK.setVisible(true);
/* 1648 */       wizardLabel.setText(Methods.wizardText("Define Cages", buildCages));
/* 1649 */       manualConstruction();
/*      */     } else {
/*      */       
/* 1652 */       restoreFrame();
/* 1653 */       this.jdlgWizard.dispose();
/* 1654 */       wizardRunning = false;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void multiBuild() {
/* 1659 */     String title = Methods.puzzleTitle;
/* 1660 */     int[] sizeDef = { 5, 5, 5, 6, 6, 6, 7 };
/* 1661 */     int[] diffDef = { 0, 1, 2, 0, 1, 2, 2 };
/*      */ 
/*      */     
/* 1664 */     int saveKsudokuDiff = Op.getInt(Op.KS.KsDifficulty.ordinal(), Op.ks);
/*      */ 
/*      */     
/* 1667 */     SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
/* 1668 */     Calendar c = Calendar.getInstance();
/*      */     
/* 1670 */     for (this.hmCount = 1; this.hmCount <= this.howMany; this.hmCount++) {
/* 1671 */       if (this.startPuz > 9999999) { try {
/* 1672 */           c.setTime(sdf.parse("" + this.startPuz));
/* 1673 */         } catch (ParseException ex) {}
/* 1674 */         this.startPuz = Integer.parseInt(sdf.format(c.getTime())); }
/*      */       
/* 1676 */       Methods.puzzleTitle = "KILLER SUDOKU Puzzle : " + this.startPuz;
/* 1677 */       if (Op.getBool(Op.SX.VaryDiff.ordinal(), Op.sx).booleanValue()) {
/* 1678 */         Grid.xSz = Grid.ySz = sizeDef[(this.startPuz - 1) % 7];
/* 1679 */         Op.setInt(Op.KS.KsDifficulty.ordinal(), diffDef[(this.startPuz - 1) % 7], Op.ks);
/*      */       } 
/*      */       
/* 1682 */       Methods.buildProgress(jfKsudoku, Op.ks[Op.KS.KsPuz
/* 1683 */             .ordinal()] = "" + this.startPuz + ".ksudoku");
/* 1684 */       buildKsudoku();
/* 1685 */       restoreFrame();
/* 1686 */       Wait.shortWait(100);
/* 1687 */       if (Def.building == 2)
/*      */         return; 
/* 1689 */       this.startPuz++;
/*      */     } 
/* 1691 */     this.howMany = 1;
/* 1692 */     Methods.puzzleTitle = title;
/*      */ 
/*      */     
/* 1695 */     Op.setInt(Op.KS.KsDifficulty.ordinal(), saveKsudokuDiff, Op.ks);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void buildKsudoku() {
/* 1701 */     long ret = 0L;
/* 1702 */     Random r = new Random();
/*      */     
/*      */     while (true) {
/* 1705 */       makeGrid(5);
/*      */       
/* 1707 */       controlFromMode();
/* 1708 */       KsudokuSolve.recalculateStatus();
/* 1709 */       SudokuBuild.fillSudokuAt(0);
/*      */       
/* 1711 */       boolean valid = true; int i;
/* 1712 */       for (i = 0; i < 80; i++) {
/* 1713 */         int x1 = i % 9, y1 = i / 9;
/* 1714 */         for (int j = i + 1; j < 81; j++) {
/* 1715 */           int x2 = j % 9, y2 = j / 9;
/* 1716 */           if (Grid.grid[x1][y1] == Grid.grid[x2][y2] && Grid.mode[x1][y1] == Grid.mode[x2][y2])
/*      */           {
/* 1718 */             valid = false; } 
/*      */         } 
/*      */       } 
/* 1721 */       if (valid) {
/* 1722 */         for (int y = 0; y < 9; y++) {
/* 1723 */           for (int x = 0; x < 9; x++) {
/* 1724 */             Grid.sol[x][y] = Grid.grid[x][y];
/* 1725 */             Grid.grid[x][y] = 0;
/*      */           } 
/* 1727 */         }  getCageStats();
/* 1728 */         buildVirtualCages();
/* 1729 */         KsudokuSolve.recalculateStatus();
/* 1730 */         ret = SudokuBuild.solveSudoku(Op.getInt(Op.KS.KsDifficulty.ordinal(), Op.ks)); long requiredScore;
/* 1731 */         for (requiredScore = 1L, i = 1; i < Op.getInt(Op.KS.KsDifficulty.ordinal(), Op.ks); ) { requiredScore *= 2L; i++; }
/* 1732 */          if (ret > requiredScore && ret < requiredScore * 2L + 1L)
/*      */           break; 
/* 1734 */         if (Def.building == 2)
/*      */           return; 
/*      */       } 
/*      */     } 
/* 1738 */     KsudokuSolve.clearSolution();
/* 1739 */     if ((Op.getInt(Op.KS.KsAssist.ordinal(), Op.ks) & 0x2) == 2) {
/* 1740 */       KsudokuSolve.setCageValues();
/*      */     } else {
/* 1742 */       KsudokuSolve.recalculateStatus();
/* 1743 */     }  KsudokuSolve.undoIndex = 0;
/* 1744 */     Methods.clearHintData();
/* 1745 */     saveKsudoku(Op.ks[Op.KS.KsPuz.ordinal()]);
/*      */   }
/*      */   
/*      */   void handleMouseDown(MouseEvent e) {
/* 1749 */     int loc[] = new int[4], mouseX = e.getX(), mouseY = e.getY();
/*      */     
/* 1751 */     if (!activeMouse)
/* 1752 */       return;  if (mouseX < Grid.xOrg || mouseY < Grid.yOrg)
/* 1753 */       return;  int i = (mouseX - Grid.xOrg) / Grid.xCell;
/* 1754 */     int j = (mouseY - Grid.yOrg) / Grid.yCell;
/* 1755 */     if (i >= Grid.xSz || j >= Grid.ySz)
/*      */       return; 
/* 1757 */     loc[0] = (mouseY - Grid.yOrg) % Grid.yCell;
/* 1758 */     loc[3] = (mouseX - Grid.xOrg) % Grid.xCell;
/* 1759 */     loc[1] = Grid.xCell - loc[3];
/* 1760 */     loc[2] = Grid.yCell - loc[0]; int best;
/* 1761 */     for (int min = 1000, a = 0; a < 4; a++) {
/* 1762 */       if (loc[a] < min) {
/* 1763 */         min = loc[a];
/* 1764 */         best = a;
/*      */       } 
/* 1766 */     }  switch (best) { case 0:
/* 1767 */         if (j > 0) { Grid.control[i][j] = (byte)(Grid.control[i][j] ^ 0x1); Grid.control[i][j - 1] = (byte)(Grid.control[i][j - 1] ^ 0x4); }  break;
/* 1768 */       case 1: if (i < 8) { Grid.control[i][j] = (byte)(Grid.control[i][j] ^ 0x2); Grid.control[i + 1][j] = (byte)(Grid.control[i + 1][j] ^ 0x8); }  break;
/* 1769 */       case 2: if (j < 8) { Grid.control[i][j] = (byte)(Grid.control[i][j] ^ 0x4); Grid.control[i][j + 1] = (byte)(Grid.control[i][j + 1] ^ 0x1); }  break;
/* 1770 */       case 3: if (i > 0) { Grid.control[i][j] = (byte)(Grid.control[i][j] ^ 0x8); Grid.control[i - 1][j] = (byte)(Grid.control[i - 1][j] ^ 0x2); }
/*      */          break; }
/* 1772 */      restoreFrame();
/*      */   }
/*      */   void handleKeyPressed(KeyEvent e) {
/*      */     int i;
/* 1776 */     if (!activeKB)
/* 1777 */       return;  if (e.isAltDown())
/* 1778 */       return;  switch (e.getKeyCode()) {
/*      */       case 9:
/*      */       case 10:
/* 1781 */         if ((cData[scoreEntryTarget + 1]).area > 0)
/* 1782 */           scoreEntryTarget++; 
/*      */         break;
/*      */       case 8:
/*      */       case 127:
/* 1786 */         if ((cData[scoreEntryTarget]).score == 0 && scoreEntryTarget > 0) {
/* 1787 */           scoreEntryTarget--;
/*      */           break;
/*      */         } 
/* 1790 */         (cData[scoreEntryTarget]).score /= 10;
/*      */         break;
/*      */       default:
/* 1793 */         i = e.getKeyChar() - 48;
/* 1794 */         if (i < 0 || i > 9 || 
/* 1795 */           (cData[scoreEntryTarget]).score * 10 + i > 45)
/* 1796 */           break;  (cData[scoreEntryTarget]).score = (cData[scoreEntryTarget]).score * 10 + i;
/*      */         break;
/*      */     } 
/*      */     
/* 1800 */     restoreFrame();
/*      */   }
/*      */ }


/* Location:              C:\Users\mustapha\Desktop\Crossword-Express.jar!\crosswordexpress\KsudokuBuild.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */