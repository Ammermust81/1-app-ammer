/*      */ package crosswordexpress;
/*      */ import java.awt.FontMetrics;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.RenderingHints;
/*      */ import java.awt.Toolkit;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.io.DataInputStream;
/*      */ import java.io.DataOutputStream;
/*      */ import java.util.Random;
/*      */ import javax.swing.JComboBox;
/*      */ import javax.swing.JDialog;
/*      */ import javax.swing.JLabel;
/*      */ import javax.swing.JMenuItem;
/*      */ import javax.swing.KeyStroke;
/*      */ 
/*      */ public class MinesweeperBuild {
/*      */   static JFrame jfMinesweeper;
/*      */   static JMenuBar menuBar;
/*      */   JMenu menu;
/*      */   JMenu submenu;
/*      */   JMenuItem menuItem;
/*      */   JMenuItem buildMenuItem;
/*   24 */   int howMany = 1; static JPanel pp; static int panelW; static int panelH; static JLabel jl1; static JLabel jl2; Timer myTimer; Thread thread; int startPuz = Integer.parseInt((new SimpleDateFormat("yyyyMMdd")).format(new Date())); int hmCount;
/*      */   boolean sixpack;
/*   26 */   static String rules = "Some of the un-numbered cells contain a mine, while the others are clear. The numbered flags tell you how many adjacent cells (horizontally, vertically and diagonally) contain a mine. Where are the mines?"; static final int PENDING = 0;
/*      */   static final int CLEAR = 1;
/*      */   static final int MINED = 2;
/*      */   static final int RESERVED = 3;
/*   30 */   static int[] listX = new int[100]; static int[] listY = new int[100]; static int listIndex;
/*   31 */   static int[] oX = new int[] { -1, 0, 1, 1, 1, 0, -1, -1 };
/*   32 */   static int[] oY = new int[] { -1, -1, -1, 0, 1, 1, 1, 0 };
/*      */   
/*      */   static void def() {
/*   35 */     Op.updateOption(Op.MS.MsW.ordinal(), "500", Op.ms);
/*   36 */     Op.updateOption(Op.MS.MsH.ordinal(), "580", Op.ms);
/*   37 */     Op.updateOption(Op.MS.MsAcross.ordinal(), "8", Op.ms);
/*   38 */     Op.updateOption(Op.MS.MsDown.ordinal(), "8", Op.ms);
/*   39 */     Op.updateOption(Op.MS.MsDiff.ordinal(), "1", Op.ms);
/*   40 */     Op.updateOption(Op.MS.MsCell.ordinal(), "AAEEDD", Op.ms);
/*   41 */     Op.updateOption(Op.MS.MsClear.ordinal(), "BBBBBB", Op.ms);
/*   42 */     Op.updateOption(Op.MS.MsFlag.ordinal(), "FF0000", Op.ms);
/*   43 */     Op.updateOption(Op.MS.MsNumbers.ordinal(), "FFFF00", Op.ms);
/*   44 */     Op.updateOption(Op.MS.MsLines.ordinal(), "000000", Op.ms);
/*   45 */     Op.updateOption(Op.MS.MsMine.ordinal(), "000000", Op.ms);
/*   46 */     Op.updateOption(Op.MS.MsError.ordinal(), "FF0000", Op.ms);
/*   47 */     Op.updateOption(Op.MS.MsFuse.ordinal(), "000000", Op.ms);
/*   48 */     Op.updateOption(Op.MS.MsPuz.ordinal(), "sample.minesweeper", Op.ms);
/*   49 */     Op.updateOption(Op.MS.MsFont.ordinal(), "SansSerif", Op.ms);
/*   50 */     Op.updateOption(Op.MS.MsPuzColor.ordinal(), "false", Op.ms);
/*   51 */     Op.updateOption(Op.MS.MsSolColor.ordinal(), "false", Op.ms);
/*      */   }
/*      */   
/*   54 */   String minesweeperHelp = "<div>A MINESWEEPER puzzle consists of a square or rectangular grid in which a number of mines have been laid. Some of the cells contain a hazard flag which has a number indicating how many of the adjacent cells (horizontally, vertically and diagonally) contain a mine. To solve the puzzle, you must determine which of the un-flagged cells contain a mine. Each puzzle has a unique solution and no guessing is required to reach a correct solution. Puzzles can be built either manually or automatically in sizes from 6x6 up to 14x14.</div><br/><span class='m'>Menu Functions</span><ul><li/><span class='s'>File Menu</span><ul><li/><span>Load a Puzzle</span><br/>Use this option to choose your puzzle from the pool of MINESWEEPER puzzles currently available on your computer.<p/><li/><span>Save</span><br/>If you have done some manual editing of the puzzle, this option will save those changes under the existing file name.<p/><li/><span>SaveAs</span><br/>This option allows you to make an exact copy of the current puzzle using a different file name. The copy will be saved in the <b>minesweeper</b> folder along with all of the Minesweeper puzzles you have made. Alternatively, if you don't enter a new name for the puzzle, you can change the Puzzle Description, or any of the other descriptive items without changing the puzzle name.<p/><li/><span>Quit Construction</span><br/>Returns you to the Crossword Express opening screen.<p/></ul><li/><span class='s'>Build Menu</span><ul><li/><span>Start a New Puzzle</span><br/>This option presents you with a dialog into which you can enter a file name for your new puzzle. You can also enter several other pieces of information such as a <b>Puzzle Title, Author</b> and <b>Copyright</b> information.<p/><li/><span>Build Options</span><br/>Use this option to access a Build Options dialog where you can set some rules to be followed by the puzzle building function as it builds your puzzle.<p/><li/><span>Start Building / Stop Building</span><br/>Construction of the puzzle will commence when you select the <b>Start Building</b> option. If puzzle building is successful you will receive a message containing the name of the puzzle file, and the location where it was saved. If it becomes necessary, you can interrupt the building process by selecting this option a second time. Note that during the construction phase, the text of this option is changed to <b>Stop Building</b><p/><li/><span>Test Puzzle Validity</span><br/>Manual construction of a MINESWEEPER puzzle is not recommended. You should only attempt this if you have a valid puzzle (possibly one published in a magazine) which you would like to enter into the program. Simply move the cursor cell around the puzzle, and type the required values into the appropriate cells. Selecting the <b>Test Puzzle Validity</b> option will check the validity of the puzzle. If it has a unique solution, it will be saved, and you will be advised of this. If not, you will receive a message that the puzzle is not valid.<p/></ul><li/><span class='s'>View Menu</span><ul><li/><span>Display Options</span><br/>This leads you to a dialog box in which you can change the colors of various elements within the puzzle, and control the fonts which will be used for the puzzle's text components. You can also decide if printing of the puzzle will be done in black and white or in color.<p/></ul><li/><span class='s'>Export Menu</span><br/><ul><li/><span>Export Minesweeper Web-App</span><br/>This function allows you to export a Web Application Program which you can then upload to your own web site to provide a fully interactive Minesweeper puzzle for the entertainment of visitors to your site. For a full description of the facilities provided by this Web-App, please refer to the Help available at <b>Help / Minesweeper Web Application</b> on the menu bar of the <b>Build Minesweeper</b> window of this program.<p/><li/><span>Launch a Demo Minesweeper Web App</span><br/>Take a first look at the Minesweeper Web App. See what it could do to enhance your web site.<p/><li/><span>Print a Minesweeper KDP puzzle book.</span><br/>The letters KDP stand for <b>Kindle Direct Publishing</b>. This is a free publishing service operared by Amazon, in which they handle all matters related to printing, advertising and sales of books created by members of the public. A portion of the proceeds are retained by Amazon while the remainder is paid to the author. Fifteen of the Puzzles created by Crossword Express can be printed into PDF format files ready for publication by Amazon. When you select this option, you will be presented with a dialog which allows you to control the process. Please study the Help offered by this dialog before attempting to make use of it.</ul><li/><span class='s'>Tasks Menu</span><ul><li/><span>Print this Puzzle</span><br/>This will take you to a custom print screen where you can control the details involved with printing your puzzle.<p/><li/><span>Solve this Puzzle</span><br/>This will take you to a Solve screen which provides a fully interactive environment for solving the puzzle.<p/><li/><span>Delete this Puzzle</span><br/>Use this option to eliminate unwanted MINESWEEPER puzzles from your file system.<p/></ul><li/><span class='s'>Help Menu</span><ul><li/><span>Minesweeper Help</span><br/>Displays the Help screen which you are now reading.<p/></ul></ul></body>";
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
/*  132 */   String minesweeperOptions = "<div>The following options are available for adjustment before you start to build your Minesweeper puzzle:-</div><br/><ul><li/>Minesweeper puzzles can be made in sizes ranging from 6x6 up to 14x14. This can be controlled using the <b>Cells Across</b> and <b>Cells Down</b> combo-boxes.<p/><li/><b>Difficulty: </b>A combo box allows you to choose between difficulty settings of <b>Easy, Moderate, Hard</b> and <b>Very Hard</b>.<p/><li/>If you want to make a number of puzzles all having the same dimensions, simply type a number into the <b>How many puzzles</b> input field. When you issue the Make command, Crossword Express will make that number of puzzles. The puzzle names will be numbers which represent a date in <b>yyyymmdd</b> format. The default value presented by Crossword Express is always the current date, but you can change this to any date that suits your needs. As the series of puzzles is created, CWE will automatically step on to the next date in the sequence, taking into account such factors as the varying number of days in the months, and of course leap years. Virtually any number of puzzles can be made in a single operation using this feature.<p/><li/><b>HOWEVER:</b> If you prefer a simpler numbering scheme for your puzzles, you can enter any number of 7 digits or less to be used for your first puzzle, and Crossword Express will number the remainder of the puzzles sequentially starting with your number.<p/><li/>If you do choose to make multiple puzzles, then by default, Crossword Express will change the difficulty of the resulting puzzles over a cycle of seven puzzles. This would be useful for a daily newspaper so that the week could start with a very easy puzzle, with quite difficult puzzles reserved for the weekend. If you don't want this feature, clearing the <b>Vary Difficulty on 7 day cycle</b> check-box will disable it.</ul></body>";
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
/*  154 */   static String webAppHelp = "<div><span>Step 1: Export</span><br/>To export the Web Application, select the function <b>Export / Export Puzzle as Web App</b> from the menu bar of the <b>Build Minesweeper</b> window of this program. The necessary files will be exported into a folder called <b>Web-App</b> located on your computer's Desktop. Please note that you will need a working Internet connection for this function to operate correctly. The exported files are as follows:-<ul><li><span>minesweeper.js</span> This is the file which contains all of the Java Script which implements the interactive features of the Web App.<li><span>minesweeper.html</span> This file creates the URL address used to access the Web App. Its main purpose is to start the operation of the minesweeper.js Java Script file. If you open minesweeper.html with a simple text editor, you will find two appearances of the following text fragment ... <b>&lt;!-- Your HTML code here. --></b>. If you are an experienced HTML coder and would like to add some content of your own to the Web App window, simply replace these fragments with your own HTML code.</ul><span>Step 2: Familiarization</span><br/>Having exported the App, you can give it a first run by starting your web browser, and using it to open the <b>minesweeper.html</b> file mentioned above, or more simply, just double click the minesweeper.html icon. <p/><span>Step 3: Installation</span><br/>Installation is a three step process:-<ul><li>Create a new folder somewhere on your web server.<li>Upload the two files already discussed into this new folder.<li>Create a link from a convenient point within your web site to the minesweeper.html file in your newly created folder.</ul>At this point, any visitor will be able to make full use of the Minesweeper Web-App. There is no need to upload any Minesweeper puzzle files. This App makes new puzzles on demand as described in the Help information available from the Help button on the App.<p/></div></body>";
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
/*      */   MinesweeperBuild(JFrame jf, boolean auto, int hm, int start) {
/*  185 */     Def.puzzleMode = 132;
/*  186 */     Def.building = 0;
/*  187 */     Def.dispCursor = Boolean.valueOf(true);
/*  188 */     makeGrid();
/*      */     
/*  190 */     jfMinesweeper = new JFrame("Minesweeper");
/*  191 */     if (Op.getInt(Op.MS.MsH.ordinal(), Op.ms) > Methods.scrH - 200) {
/*  192 */       int diff = Op.getInt(Op.MS.MsH.ordinal(), Op.ms) - Op.getInt(Op.MS.MsW.ordinal(), Op.ms);
/*  193 */       Op.setInt(Op.MS.MsH.ordinal(), Methods.scrH - 200, Op.ms);
/*  194 */       Op.setInt(Op.MS.MsW.ordinal(), Methods.scrH - 200 + diff, Op.ms);
/*      */     } 
/*  196 */     jfMinesweeper.setSize(Op.getInt(Op.MS.MsW.ordinal(), Op.ms), Op.getInt(Op.MS.MsH.ordinal(), Op.ms));
/*  197 */     int frameX = (jf.getX() + jfMinesweeper.getWidth() > Methods.scrW) ? (Methods.scrW - jfMinesweeper.getWidth() - 10) : jf.getX();
/*  198 */     jfMinesweeper.setLocation(frameX, jf.getY());
/*  199 */     jfMinesweeper.setLayout((LayoutManager)null);
/*  200 */     jfMinesweeper.setDefaultCloseOperation(0);
/*  201 */     jfMinesweeper
/*  202 */       .addComponentListener(new ComponentAdapter() {
/*      */           public void componentResized(ComponentEvent ce) {
/*  204 */             int oldw = Op.getInt(Op.MS.MsW.ordinal(), Op.ms);
/*  205 */             int oldh = Op.getInt(Op.MS.MsH.ordinal(), Op.ms);
/*  206 */             Methods.frameResize(MinesweeperBuild.jfMinesweeper, oldw, oldh, 500, 580);
/*  207 */             Op.setInt(Op.MS.MsW.ordinal(), MinesweeperBuild.jfMinesweeper.getWidth(), Op.ms);
/*  208 */             Op.setInt(Op.MS.MsH.ordinal(), MinesweeperBuild.jfMinesweeper.getHeight(), Op.ms);
/*  209 */             MinesweeperBuild.restoreFrame();
/*      */           }
/*      */         });
/*      */     
/*  213 */     jfMinesweeper
/*  214 */       .addWindowListener(new WindowAdapter() {
/*      */           public void windowClosing(WindowEvent we) {
/*  216 */             if (Def.building == 1 || Def.selecting)
/*  217 */               return;  Op.saveOptions("minesweeper.opt", Op.ms);
/*  218 */             CrosswordExpress.transfer(1, MinesweeperBuild.jfMinesweeper);
/*      */           }
/*      */         });
/*      */     
/*  222 */     Methods.closeHelp();
/*      */ 
/*      */     
/*  225 */     Runnable buildThread = () -> {
/*      */         if (this.howMany == 1) {
/*      */           buildMinesweeper();
/*      */         } else {
/*      */           multiBuild();
/*      */           
/*      */           if (this.sixpack) {
/*      */             Sixpack.trigger();
/*      */             jfMinesweeper.dispose();
/*      */             Def.building = 0;
/*      */             return;
/*      */           } 
/*      */         } 
/*      */         this.buildMenuItem.setText("Start Building");
/*      */         if (Def.building == 2) {
/*      */           Def.building = 0;
/*      */           Methods.interrupted(jfMinesweeper);
/*      */           makeGrid();
/*      */           restoreFrame();
/*      */           return;
/*      */         } 
/*      */         Methods.havePuzzle = true;
/*      */         restoreFrame();
/*      */         Methods.puzzleSaved(jfMinesweeper, "minesweeper", Op.ms[Op.MS.MsPuz.ordinal()]);
/*      */         Def.building = 0;
/*      */       };
/*  251 */     jl1 = new JLabel(); jfMinesweeper.add(jl1);
/*  252 */     jl2 = new JLabel(); jfMinesweeper.add(jl2);
/*      */ 
/*      */     
/*  255 */     menuBar = new JMenuBar();
/*  256 */     menuBar.setBackground(Def.COLOR_MENUBAR);
/*  257 */     jfMinesweeper.setJMenuBar(menuBar);
/*      */     
/*  259 */     this.menu = new JMenu("File");
/*  260 */     menuBar.add(this.menu);
/*  261 */     this.menuItem = new JMenuItem("Load a Puzzle");
/*  262 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(76, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  263 */     this.menu.add(this.menuItem);
/*  264 */     this.menuItem
/*  265 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1)
/*      */             return; 
/*      */           pp.invalidate();
/*      */           pp.repaint();
/*      */           new Select(jfMinesweeper, "minesweeper", "minesweeper", Op.ms, Op.MS.MsPuz.ordinal(), false);
/*      */         });
/*  272 */     this.menuItem = new JMenuItem("Save");
/*  273 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(83, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  274 */     this.menu.add(this.menuItem);
/*  275 */     this.menuItem
/*  276 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           saveMinesweeper(Op.ms[Op.MS.MsPuz.ordinal()]);
/*      */           Methods.puzzleSaved(jfMinesweeper, "minesweeper", Op.ms[Op.MS.MsPuz.ordinal()]);
/*      */         });
/*  283 */     this.menuItem = new JMenuItem("SaveAs");
/*  284 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(65, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  285 */     this.menu.add(this.menuItem);
/*  286 */     this.menuItem
/*  287 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           Methods.puzzleDescriptionDialog(jfMinesweeper, Op.ms[Op.MS.MsPuz.ordinal()].substring(0, Op.ms[Op.MS.MsPuz.ordinal()].indexOf(".minesweeper")), "minesweeper", ".minesweeper");
/*      */           if (Methods.clickedOK) {
/*      */             saveMinesweeper(Op.ms[Op.MS.MsPuz.ordinal()] = Methods.theFileName);
/*      */             restoreFrame();
/*      */             Methods.puzzleSaved(jfMinesweeper, "minesweeper", Op.ms[Op.MS.MsPuz.ordinal()]);
/*      */           } 
/*      */         });
/*  298 */     this.menuItem = new JMenuItem("Quit Construction");
/*  299 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(81, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  300 */     this.menu.add(this.menuItem);
/*  301 */     this.menuItem
/*  302 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           
/*      */           Op.saveOptions("minesweeper.opt", Op.ms);
/*      */           CrosswordExpress.transfer(1, jfMinesweeper);
/*      */         });
/*  310 */     this.menu = new JMenu("Build");
/*  311 */     menuBar.add(this.menu);
/*  312 */     this.menuItem = new JMenuItem("Start a new Puzzle");
/*  313 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(78, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  314 */     this.menu.add(this.menuItem);
/*  315 */     this.menuItem
/*  316 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           Methods.puzzleDescriptionDialog(jfMinesweeper, Op.ms[Op.MS.MsPuz.ordinal()].substring(0, Op.ms[Op.MS.MsPuz.ordinal()].indexOf(".minesweeper")), "minesweeper", ".minesweeper");
/*      */           if (Methods.clickedOK) {
/*      */             Op.ms[Op.MS.MsPuz.ordinal()] = Methods.theFileName;
/*      */             makeGrid();
/*      */           } 
/*      */           restoreFrame();
/*      */         });
/*  327 */     this.menuItem = new JMenuItem("Build Options");
/*  328 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(79, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  329 */     this.menu.add(this.menuItem);
/*  330 */     this.menuItem
/*  331 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1)
/*      */             return; 
/*      */           minesweeperOptions();
/*      */           if (Methods.clickedOK) {
/*      */             makeGrid();
/*      */             if (this.howMany > 0)
/*      */               Op.ms[Op.MS.MsPuz.ordinal()] = "" + this.startPuz + ".minesweeper"; 
/*      */           } 
/*      */           restoreFrame();
/*      */         });
/*  342 */     this.buildMenuItem = new JMenuItem("Start Building");
/*  343 */     this.buildMenuItem.setAccelerator(KeyStroke.getKeyStroke(66, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  344 */     this.menu.add(this.buildMenuItem);
/*  345 */     this.buildMenuItem
/*  346 */       .addActionListener(ae -> {
/*      */           if (Op.ms[Op.MS.MsPuz.ordinal()].length() == 0 && this.howMany == 1) {
/*      */             Methods.noName(jfMinesweeper);
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
/*  362 */     this.menuItem = new JMenuItem("Test Puzzle Validity");
/*  363 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(84, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  364 */     this.menu.add(this.menuItem);
/*  365 */     this.menuItem
/*  366 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           
/*      */           if (solveMinesweeper(5) > 0 && validMinesweeper()) {
/*      */             Methods.havePuzzle = true;
/*      */             for (int y1 = 0; y1 < Grid.ySz; y1++) {
/*      */               for (int x1 = 0; x1 < Grid.xSz; x1++) {
/*      */                 Grid.copy[x1][y1] = Grid.sol[x1][y1];
/*      */                 Grid.sol[x1][y1] = 0;
/*      */               } 
/*      */             } 
/*      */             saveMinesweeper(Op.ms[Op.MS.MsPuz.ordinal()]);
/*      */             restoreFrame();
/*      */             Methods.puzzleSaved(jfMinesweeper, "minesweeper", Op.ms[Op.MS.MsPuz.ordinal()]);
/*      */           } else {
/*      */             JOptionPane.showMessageDialog(jfMinesweeper, "There is no solution to this puzzle", "Test Result", 1);
/*      */           } 
/*      */           restoreFrame();
/*      */         });
/*  387 */     this.menu = new JMenu("View");
/*  388 */     menuBar.add(this.menu);
/*  389 */     this.menuItem = new JMenuItem("Display Options");
/*  390 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(89, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  391 */     this.menu.add(this.menuItem);
/*  392 */     this.menuItem
/*  393 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           
/*      */           printOptions(jfMinesweeper, "Display Options");
/*      */           restoreFrame();
/*      */         });
/*  401 */     this.menu = new JMenu("Export");
/*  402 */     menuBar.add(this.menu);
/*  403 */     this.menuItem = new JMenuItem("Export Minesweeper Web-App");
/*  404 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(87, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  405 */     this.menu.add(this.menuItem);
/*  406 */     this.menuItem
/*  407 */       .addActionListener(ae -> Methods.exportWebApp(jfMinesweeper, "minesweeper"));
/*      */ 
/*      */     
/*  410 */     this.menuItem = new JMenuItem("Launch a Demo Minesweeper Web App");
/*  411 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(85, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  412 */     this.menu.add(this.menuItem);
/*  413 */     this.menuItem
/*  414 */       .addActionListener(ae -> Methods.launchWebApp(jfMinesweeper, "minesweeper"));
/*      */ 
/*      */     
/*  417 */     this.menuItem = new JMenuItem("Print a Minesweeper KDP puzzle book.");
/*  418 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(75, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  419 */     this.menu.add(this.menuItem);
/*  420 */     this.menuItem
/*  421 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           
/*      */           Methods.printKdpDialog(jfMinesweeper, 132, 6);
/*      */         });
/*  428 */     this.menu = new JMenu("Tasks");
/*  429 */     menuBar.add(this.menu);
/*  430 */     this.menuItem = new JMenuItem("Print this Puzzle");
/*  431 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(80, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  432 */     this.menu.add(this.menuItem);
/*  433 */     this.menuItem
/*  434 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           CrosswordExpress.toPrint(jfMinesweeper, Op.ms[Op.MS.MsPuz.ordinal()]);
/*      */         });
/*  440 */     this.menuItem = new JMenuItem("Solve this Puzzle");
/*  441 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(86, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  442 */     this.menu.add(this.menuItem);
/*  443 */     this.menuItem
/*  444 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1)
/*      */             return; 
/*      */           if (Methods.havePuzzle) {
/*      */             CrosswordExpress.transfer(133, jfMinesweeper);
/*      */           } else {
/*      */             Methods.noPuzzle(jfMinesweeper, "Solve");
/*      */           } 
/*      */         });
/*  453 */     this.menuItem = new JMenuItem("Delete this Puzzle");
/*  454 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(68, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  455 */     this.menu.add(this.menuItem);
/*  456 */     this.menuItem
/*  457 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           
/*      */           if (Methods.deleteAPuzzle(jfMinesweeper, Op.ms[Op.MS.MsPuz.ordinal()], "minesweeper", pp)) {
/*      */             loadMinesweeper(Op.ms[Op.MS.MsPuz.ordinal()]);
/*      */             restoreFrame();
/*      */           } 
/*      */         });
/*  467 */     this.menu = new JMenu("Help");
/*  468 */     menuBar.add(this.menu);
/*  469 */     this.menuItem = new JMenuItem("Minesweeper Help");
/*  470 */     this.menu.add(this.menuItem);
/*  471 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(72, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  472 */     this.menuItem
/*  473 */       .addActionListener(ae -> Methods.cweHelp(jfMinesweeper, null, "Building Minesweeper Puzzles", this.minesweeperHelp));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  478 */     this.menuItem = new JMenuItem("Minesweeper Web Application");
/*  479 */     this.menu.add(this.menuItem);
/*  480 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(77, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  481 */     this.menuItem
/*  482 */       .addActionListener(ae -> Methods.cweHelp(jfMinesweeper, null, "Minesweeper Web Application", webAppHelp));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  488 */     pp = new MinesweeperBuildPP(0, 37);
/*  489 */     jfMinesweeper.add(pp);
/*      */     
/*  491 */     pp
/*  492 */       .addMouseListener(new MouseAdapter() {
/*      */           public void mousePressed(MouseEvent e) {
/*  494 */             MinesweeperBuild.this.updateGrid(e);
/*      */           }
/*      */         });
/*      */ 
/*      */     
/*  499 */     pp
/*  500 */       .addMouseMotionListener(new MouseAdapter() {
/*      */           public void mouseMoved(MouseEvent e) {
/*  502 */             if (Def.isMac) {
/*  503 */               MinesweeperBuild.jfMinesweeper.setResizable((MinesweeperBuild.jfMinesweeper.getWidth() - e.getX() < 15 && MinesweeperBuild.jfMinesweeper
/*  504 */                   .getHeight() - e.getY() < 95));
/*      */             }
/*      */           }
/*      */         });
/*      */     
/*  509 */     jfMinesweeper
/*  510 */       .addKeyListener(new KeyAdapter() {
/*      */           public void keyPressed(KeyEvent e) {
/*  512 */             MinesweeperBuild.this.handleKeyPressed(e);
/*      */           }
/*      */         });
/*      */ 
/*      */     
/*  517 */     loadMinesweeper(Op.ms[Op.MS.MsPuz.ordinal()]);
/*  518 */     restoreFrame();
/*      */ 
/*      */     
/*  521 */     ActionListener timerAL = ae -> {
/*      */         this.myTimer.stop();
/*      */         this.thread = new Thread(paramRunnable);
/*      */         this.thread.start();
/*      */         Def.building = 1;
/*      */       };
/*  527 */     this.myTimer = new Timer(1000, timerAL);
/*      */     
/*  529 */     if (auto) {
/*  530 */       this.sixpack = true;
/*  531 */       this.howMany = hm; this.startPuz = start;
/*  532 */       this.myTimer.start();
/*      */     } 
/*      */   }
/*      */   
/*      */   static void restoreFrame() {
/*  537 */     jfMinesweeper.setVisible(true);
/*  538 */     Insets insets = jfMinesweeper.getInsets();
/*  539 */     panelW = jfMinesweeper.getWidth() - insets.left + insets.right;
/*  540 */     panelH = jfMinesweeper.getHeight() - insets.top + insets.bottom + 37 + menuBar.getHeight();
/*  541 */     pp.setSize(panelW, panelH);
/*  542 */     jfMinesweeper.requestFocusInWindow();
/*  543 */     pp.repaint();
/*  544 */     Methods.infoPanel(jl1, jl2, "Build Minesweeper", "Puzzle : " + Op.ms[Op.MS.MsPuz.ordinal()], panelW);
/*      */   }
/*      */   
/*      */   static void setSizesAndOffsets(int x, int y, int width, int height, int inset) {
/*  548 */     int i = (width - inset) / Grid.xSz;
/*  549 */     int j = (height - inset) / Grid.ySz;
/*  550 */     Grid.xCell = Grid.yCell = (i < j) ? i : j;
/*  551 */     Grid.xOrg = x + ((Def.puzzleMode == 8) ? ((width - Grid.xSz * Grid.xCell) / 2) : 10);
/*  552 */     Grid.yOrg = y + ((Def.puzzleMode == 8) ? ((height - Grid.ySz * Grid.yCell) / 2) : 10);
/*      */   }
/*      */   
/*      */   private void minesweeperOptions() {
/*  556 */     String[] diffString = { "  Easy", "  Moderate", "  Hard", "  Very Hard" };
/*  557 */     String[] size = { "6", "7", "8", "9", "10", "11", "12", "13", "14" };
/*      */ 
/*      */     
/*  560 */     final JDialog jdlgMinesweeper = new JDialog(jfMinesweeper, "Minesweeper Options", true);
/*  561 */     jdlgMinesweeper.setSize(270, 312);
/*  562 */     jdlgMinesweeper.setResizable(false);
/*  563 */     jdlgMinesweeper.setLayout((LayoutManager)null);
/*  564 */     jdlgMinesweeper.setLocation(jfMinesweeper.getX(), jfMinesweeper.getY());
/*      */     
/*  566 */     jdlgMinesweeper
/*  567 */       .addWindowListener(new WindowAdapter() {
/*      */           public void windowClosing(WindowEvent we) {
/*  569 */             Methods.closeHelp();
/*      */           }
/*      */         });
/*      */     
/*  573 */     Methods.closeHelp();
/*      */     
/*  575 */     JLabel jlAcross = new JLabel("Cells Across:");
/*  576 */     jlAcross.setForeground(Def.COLOR_LABEL);
/*  577 */     jlAcross.setSize(80, 20);
/*  578 */     jlAcross.setLocation(20, 8);
/*  579 */     jlAcross.setHorizontalAlignment(4);
/*  580 */     jdlgMinesweeper.add(jlAcross);
/*  581 */     final JComboBox<String> jcbbAcross = new JComboBox<>(size);
/*  582 */     jcbbAcross.setSize(60, 20);
/*  583 */     jcbbAcross.setLocation(120, 8);
/*  584 */     jdlgMinesweeper.add(jcbbAcross);
/*  585 */     jcbbAcross.setBackground(Def.COLOR_BUTTONBG);
/*  586 */     jcbbAcross.setSelectedIndex(Op.getInt(Op.MS.MsAcross.ordinal(), Op.ms) - 6);
/*      */     
/*  588 */     JLabel jlDown = new JLabel("Cells Down:");
/*  589 */     jlDown.setForeground(Def.COLOR_LABEL);
/*  590 */     jlDown.setSize(80, 20);
/*  591 */     jlDown.setLocation(20, 30);
/*  592 */     jlDown.setHorizontalAlignment(4);
/*  593 */     jdlgMinesweeper.add(jlDown);
/*  594 */     final JComboBox<String> jcbbDown = new JComboBox<>(size);
/*  595 */     jcbbDown.setSize(60, 20);
/*  596 */     jcbbDown.setLocation(120, 30);
/*  597 */     jdlgMinesweeper.add(jcbbDown);
/*  598 */     jcbbDown.setBackground(Def.COLOR_BUTTONBG);
/*  599 */     jcbbDown.setSelectedIndex(Op.getInt(Op.MS.MsDown.ordinal(), Op.ms) - 6);
/*      */     
/*  601 */     JLabel jlDiff = new JLabel("Difficulty:");
/*  602 */     jlDiff.setForeground(Def.COLOR_LABEL);
/*  603 */     jlDiff.setSize(80, 20);
/*  604 */     jlDiff.setLocation(20, 60);
/*  605 */     jlDiff.setHorizontalAlignment(4);
/*  606 */     jdlgMinesweeper.add(jlDiff);
/*  607 */     final JComboBox<String> jcbbDifficulty = new JComboBox<>(diffString);
/*  608 */     jcbbDifficulty.setSize(120, 23);
/*  609 */     jcbbDifficulty.setLocation(120, 60);
/*  610 */     jdlgMinesweeper.add(jcbbDifficulty);
/*  611 */     jcbbDifficulty.setBackground(Def.COLOR_BUTTONBG);
/*  612 */     jcbbDifficulty.setSelectedIndex(Op.getInt(Op.MS.MsDiff.ordinal(), Op.ms) - 1);
/*      */     
/*  614 */     final HowManyPuzzles hmp = new HowManyPuzzles(jdlgMinesweeper, 10, 97, this.howMany, this.startPuz, Op.getBool(Op.SX.VaryDiff.ordinal(), Op.sx).booleanValue());
/*      */     
/*  616 */     Action doOK = new AbstractAction("OK") {
/*      */         public void actionPerformed(ActionEvent e) {
/*  618 */           Grid.xSz = jcbbAcross.getSelectedIndex() + 6;
/*  619 */           Op.setInt(Op.MS.MsAcross.ordinal(), Grid.xSz, Op.ms);
/*  620 */           Grid.ySz = jcbbDown.getSelectedIndex() + 6;
/*  621 */           Op.setInt(Op.MS.MsDown.ordinal(), Grid.ySz, Op.ms);
/*  622 */           MinesweeperBuild.this.howMany = Integer.parseInt(hmp.jtfHowMany.getText());
/*  623 */           MinesweeperBuild.this.startPuz = Integer.parseInt(hmp.jtfStartPuz.getText());
/*  624 */           Op.setInt(Op.MS.MsDiff.ordinal(), jcbbDifficulty.getSelectedIndex() + 1, Op.ms);
/*  625 */           Op.setBool(Op.SX.VaryDiff.ordinal(), Boolean.valueOf(hmp.jcbVaryDiff.isSelected()), Op.sx);
/*  626 */           Methods.clickedOK = true;
/*  627 */           jdlgMinesweeper.dispose();
/*  628 */           Methods.closeHelp();
/*      */         }
/*      */       };
/*  631 */     JButton jbOK = Methods.newButton("doOK", doOK, 79, 10, 211, 80, 26);
/*  632 */     jdlgMinesweeper.add(jbOK);
/*      */     
/*  634 */     Action doCancel = new AbstractAction("Cancel") {
/*      */         public void actionPerformed(ActionEvent e) {
/*  636 */           Methods.clickedOK = false;
/*  637 */           jdlgMinesweeper.dispose();
/*  638 */           Methods.closeHelp();
/*      */         }
/*      */       };
/*  641 */     JButton jbCancel = Methods.newButton("doCancel", doCancel, 67, 10, 246, 80, 26);
/*  642 */     jdlgMinesweeper.add(jbCancel);
/*      */     
/*  644 */     Action doHelp = new AbstractAction("<html><font size=6 color=BB0000 face=Serif>Help ", new ImageIcon("graphics/help.png")) {
/*      */         public void actionPerformed(ActionEvent e) {
/*  646 */           Methods.cweHelp(null, jdlgMinesweeper, "Minesweeper Options", MinesweeperBuild.this.minesweeperOptions);
/*      */         }
/*      */       };
/*  649 */     JButton jbHelp = Methods.newButton("doHelp", doHelp, 72, 100, 211, 150, 61);
/*  650 */     jdlgMinesweeper.add(jbHelp);
/*      */     
/*  652 */     jdlgMinesweeper.getRootPane().setDefaultButton(jbOK);
/*  653 */     Methods.setDialogSize(jdlgMinesweeper, 260, 282);
/*      */   }
/*      */   
/*      */   static void printOptions(JFrame jf, String type) {
/*  657 */     String[] colorLabel = { "Cell Color", "No Mine Color", "Flag Color", "Number Color", "Line Color", "Mine Color", "Solve Error Color" };
/*  658 */     int[] colorInt = { Op.MS.MsCell.ordinal(), Op.MS.MsClear.ordinal(), Op.MS.MsFlag.ordinal(), Op.MS.MsNumbers.ordinal(), Op.MS.MsLines.ordinal(), Op.MS.MsMine.ordinal(), Op.MS.MsError.ordinal() };
/*  659 */     String[] fontLabel = { "Puzzle Font" };
/*  660 */     int[] fontInt = { Op.MS.MsFont.ordinal() };
/*  661 */     String[] checkLabel = { "PPrint Puzzle with color.", "SPrint Solution with color." };
/*  662 */     int[] checkInt = { Op.MS.MsPuzColor.ordinal(), Op.MS.MsSolColor.ordinal() };
/*  663 */     Methods.stdPrintOptions(jf, "Minesweeper " + type, Op.ms, colorLabel, colorInt, fontLabel, fontInt, checkLabel, checkInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void saveMinesweeper(String minesweeperName) {
/*      */     try {
/*  672 */       DataOutputStream dataOut = new DataOutputStream(new FileOutputStream("minesweeper/" + minesweeperName));
/*  673 */       dataOut.writeInt(Grid.xSz);
/*  674 */       dataOut.writeInt(Grid.ySz);
/*  675 */       dataOut.writeByte(Methods.noReveal);
/*  676 */       dataOut.writeByte(Methods.noErrors);
/*  677 */       for (int i = 0; i < 54; i++)
/*  678 */         dataOut.writeByte(0); 
/*  679 */       for (int j = 0; j < Grid.ySz; j++) {
/*  680 */         for (int k = 0; k < Grid.xSz; k++) {
/*  681 */           dataOut.writeInt(Grid.sol[k][j]);
/*  682 */           dataOut.writeInt(Grid.copy[k][j]);
/*  683 */           dataOut.writeInt(Grid.letter[k][j]);
/*      */         } 
/*  685 */       }  dataOut.writeUTF(Methods.puzzleTitle);
/*  686 */       dataOut.writeUTF(Methods.author);
/*  687 */       dataOut.writeUTF(Methods.copyright);
/*  688 */       dataOut.writeUTF(Methods.puzzleNumber);
/*  689 */       dataOut.writeUTF(Methods.puzzleNotes);
/*  690 */       dataOut.close();
/*      */     }
/*  692 */     catch (IOException exc) {}
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static void loadMinesweeper(String minesweeperName) {
/*      */     
/*  699 */     try { File fl = new File("minesweeper/" + minesweeperName);
/*  700 */       if (!fl.exists()) {
/*  701 */         fl = new File("minesweeper/");
/*  702 */         String[] s = fl.list(); int k;
/*  703 */         for (k = 0; k < s.length && (
/*  704 */           s[k].lastIndexOf(".minesweeper") == -1 || s[k].charAt(0) == '.'); k++);
/*      */         
/*  706 */         if (k == s.length) { makeGrid(); return; }
/*  707 */          minesweeperName = s[k];
/*  708 */         Op.ms[Op.MS.MsPuz.ordinal()] = minesweeperName;
/*      */       } 
/*      */ 
/*      */       
/*  712 */       DataInputStream dataIn = new DataInputStream(new FileInputStream("minesweeper/" + minesweeperName));
/*  713 */       Grid.xSz = dataIn.readInt();
/*  714 */       Grid.ySz = dataIn.readInt();
/*  715 */       Methods.noReveal = dataIn.readByte();
/*  716 */       Methods.noErrors = dataIn.readByte(); int i;
/*  717 */       for (i = 0; i < 54; i++)
/*  718 */         dataIn.readByte(); 
/*  719 */       for (int j = 0; j < Grid.ySz; j++) {
/*  720 */         for (i = 0; i < Grid.xSz; i++) {
/*  721 */           Grid.sol[i][j] = dataIn.readInt();
/*  722 */           Grid.copy[i][j] = dataIn.readInt();
/*  723 */           Grid.letter[i][j] = dataIn.readInt();
/*      */         } 
/*  725 */       }  Methods.puzzleTitle = dataIn.readUTF();
/*  726 */       Methods.author = dataIn.readUTF();
/*  727 */       Methods.copyright = dataIn.readUTF();
/*  728 */       Methods.puzzleNumber = dataIn.readUTF();
/*  729 */       Methods.puzzleNotes = dataIn.readUTF();
/*  730 */       dataIn.close(); }
/*      */     
/*  732 */     catch (IOException exc) { return; }
/*  733 */      Methods.havePuzzle = true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static void drawMinesweeper(Graphics2D g2, int[][] puzzleArray) {
/*  739 */     int[] xPoints = new int[3], yPoints = new int[3];
/*      */     
/*  741 */     Stroke normalStroke = new BasicStroke(Grid.xCell / 25.0F, 2, 2);
/*  742 */     Stroke roundStroke = new BasicStroke(Grid.xCell / 25.0F, 1, 2);
/*  743 */     Stroke wideStroke = new BasicStroke(Grid.xCell / 10.0F, 2, 2);
/*  744 */     g2.setStroke(normalStroke);
/*      */     
/*  746 */     RenderingHints rh = g2.getRenderingHints();
/*  747 */     rh.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
/*  748 */     rh.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
/*  749 */     g2.setRenderingHints(rh);
/*      */     
/*      */     int j;
/*  752 */     for (j = 0; j < Grid.ySz; j++) {
/*  753 */       for (int i = 0; i < Grid.xSz; i++) {
/*  754 */         int theColor; if (Def.dispWithColor.booleanValue()) {
/*  755 */           theColor = (Grid.sol[i][j] > 0 || puzzleArray[i][j] >= 48) ? Op.getColorInt(Op.MS.MsCell.ordinal(), Op.ms) : Op.getColorInt(Op.MS.MsClear.ordinal(), Op.ms);
/*      */         } else {
/*  757 */           theColor = 16777215;
/*  758 */         }  g2.setColor(new Color(theColor));
/*  759 */         g2.fillRect(Grid.xOrg + i * Grid.xCell, Grid.yOrg + j * Grid.yCell, Grid.xCell, Grid.yCell);
/*  760 */         g2.setColor(Def.dispWithColor.booleanValue() ? new Color(Op.getColorInt(Op.MS.MsLines.ordinal(), Op.ms)) : Def.COLOR_BLACK);
/*      */       } 
/*      */     } 
/*      */     
/*  764 */     for (j = 0; j < Grid.ySz + 1; j++)
/*  765 */       g2.drawLine(Grid.xOrg, Grid.yOrg + j * Grid.yCell, Grid.xOrg + Grid.xSz * Grid.xCell, Grid.yOrg + j * Grid.yCell); 
/*  766 */     for (j = 0; j < Grid.xSz + 1; j++) {
/*  767 */       g2.drawLine(Grid.xOrg + j * Grid.xCell, Grid.yOrg, Grid.xOrg + j * Grid.xCell, Grid.yOrg + Grid.xSz * Grid.yCell);
/*      */     }
/*      */     
/*  770 */     g2.setStroke(roundStroke);
/*  771 */     for (j = 0; j < Grid.ySz; j++) {
/*  772 */       for (int i = 0; i < Grid.xSz; i++) {
/*  773 */         if (puzzleArray[i][j] != 0) {
/*  774 */           xPoints[1] = Grid.xOrg + i * Grid.xCell + Grid.xCell / 6; xPoints[0] = Grid.xOrg + i * Grid.xCell + Grid.xCell / 6;
/*  775 */           xPoints[2] = xPoints[0] + 3 * Grid.xCell / 4;
/*  776 */           yPoints[0] = Grid.yOrg + j * Grid.yCell + Grid.yCell / 8;
/*  777 */           yPoints[1] = yPoints[0] + 3 * Grid.xCell / 4;
/*  778 */           yPoints[2] = yPoints[0] + 3 * Grid.xCell / 8;
/*      */           
/*  780 */           g2.setColor(Def.dispWithColor.booleanValue() ? new Color(Op.getColorInt(Op.MS.MsFlag.ordinal(), Op.ms)) : Def.COLOR_BLACK);
/*  781 */           g2.fillPolygon(xPoints, yPoints, 3);
/*  782 */           g2.setColor(Def.dispWithColor.booleanValue() ? new Color(Op.getColorInt(Op.MS.MsLines.ordinal(), Op.ms)) : Def.COLOR_BLACK);
/*  783 */           g2.drawPolygon(xPoints, yPoints, 3);
/*      */         } 
/*      */       } 
/*      */     } 
/*  787 */     g2.setFont(new Font(Op.ms[Op.MS.MsFont.ordinal()], 0, 4 * Grid.yCell / 10));
/*  788 */     FontMetrics fm = g2.getFontMetrics();
/*  789 */     g2.setColor(Def.dispWithColor.booleanValue() ? new Color(Op.getColorInt(Op.MS.MsNumbers.ordinal(), Op.ms)) : Def.COLOR_WHITE);
/*  790 */     for (j = 0; j < Grid.ySz; j++) {
/*  791 */       for (int i = 0; i < Grid.xSz; i++) {
/*  792 */         char ch = (char)puzzleArray[i][j];
/*  793 */         if (Character.isDigit(ch)) {
/*  794 */           int w = fm.stringWidth("" + ch);
/*  795 */           g2.drawString("" + ch, Grid.xOrg + i * Grid.xCell + Grid.xCell / 4, Grid.yOrg + j * Grid.yCell + (Grid.yCell + fm.getAscent() - fm.getDescent() - 2) / 2);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  800 */     for (j = 0; j < Grid.ySz; j++) {
/*  801 */       for (int i = 0; i < Grid.xSz; i++) {
/*  802 */         if (Grid.copy[i][j] == 2) {
/*  803 */           g2.setColor(Def.dispWithColor.booleanValue() ? new Color(Op.getColorInt(Op.MS.MsMine.ordinal(), Op.ms)) : Def.COLOR_BLACK);
/*  804 */           g2.fillArc(Grid.xOrg + i * Grid.xCell + Grid.xCell / 8, Grid.yOrg + j * Grid.yCell + 3 * Grid.yCell / 8, Grid.xCell / 2, Grid.yCell / 2, 0, 360);
/*  805 */           g2.drawArc(Grid.xOrg + i * Grid.xCell + 3 * Grid.xCell / 8, Grid.yOrg + j * Grid.yCell + 2 * Grid.yCell / 8, 6 * Grid.xCell / 8, 9 * Grid.yCell / 8, 180, -90);
/*  806 */           g2.setColor(Def.dispWithColor.booleanValue() ? new Color(Op.getColorInt(Op.MS.MsFuse.ordinal(), Op.ms)) : Def.COLOR_BLACK);
/*  807 */           int fuseX = Grid.xOrg + i * Grid.xCell + 6 * Grid.xCell / 8;
/*  808 */           int fuseY = Grid.yOrg + j * Grid.yCell + 2 * Grid.yCell / 8;
/*  809 */           int fuse0 = Grid.xCell / 12;
/*  810 */           g2.drawLine(fuseX - fuse0, fuseY - fuse0, fuseX + fuse0, fuseY + fuse0);
/*  811 */           g2.drawLine(fuseX - fuse0, fuseY + fuse0, fuseX + fuse0, fuseY - fuse0);
/*      */         } 
/*      */       } 
/*      */     } 
/*  815 */     if (Def.dispCursor.booleanValue()) {
/*  816 */       g2.setStroke(wideStroke);
/*  817 */       g2.setColor(Def.COLOR_RED);
/*  818 */       g2.drawRect(Grid.xOrg + Grid.xCur * Grid.xCell, Grid.yOrg + Grid.yCur * Grid.yCell, Grid.xCell, Grid.yCell);
/*      */     } 
/*      */   }
/*      */   
/*      */   static void printPuz(Graphics2D g2, int left, int top, int width, int height) {
/*  823 */     loadMinesweeper(Op.ms[Op.MS.MsPuz.ordinal()]);
/*  824 */     setSizesAndOffsets(left, top, width, height, 0);
/*  825 */     MinesweeperSolve.clearSolution();
/*  826 */     Def.dispWithColor = Op.getBool(Op.MS.MsPuzColor.ordinal(), Op.ms);
/*  827 */     MinesweeperSolve.drawMinesweeper(g2, Grid.letter);
/*  828 */     Def.dispWithColor = Boolean.valueOf(true);
/*  829 */     loadMinesweeper(Op.ms[Op.MS.MsPuz.ordinal()]);
/*      */   }
/*      */   
/*      */   static void printSol(Graphics2D g2, int left, int top, int width, int height, String solutionPuzzle) {
/*  833 */     loadMinesweeper(solutionPuzzle);
/*  834 */     setSizesAndOffsets(left, top, width, height, 0);
/*  835 */     Def.dispWithColor = Op.getBool(Op.MS.MsSolColor.ordinal(), Op.ms);
/*  836 */     drawMinesweeper(g2, Grid.letter);
/*  837 */     Def.dispWithColor = Boolean.valueOf(true);
/*  838 */     loadMinesweeper(Op.ms[Op.MS.MsPuz.ordinal()]);
/*      */   }
/*      */   
/*      */   static void printSolTitle(Graphics2D g2, int left, int top, int width, int height, String solutionPuzzle) {
/*  842 */     loadMinesweeper(solutionPuzzle);
/*  843 */     Print.outputTextItem(g2, left, top, width, height, "SansSerif", 0, Methods.puzzleTitle);
/*  844 */     loadMinesweeper(Op.ms[Op.MS.MsPuz.ordinal()]);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static void printSixpackPuz(Graphics2D g2, int left, int top, int dim, int gap, String puzName) {
/*  850 */     FontMetrics fm = g2.getFontMetrics();
/*      */     
/*  852 */     String st = Op.sx[Op.SX.SxMs.ordinal()];
/*  853 */     if (st.length() < 3) st = "MINESWEEPER"; 
/*  854 */     int w = fm.stringWidth(st);
/*  855 */     g2.drawString(st, left + (dim - w) / 2, top - gap / 3);
/*  856 */     MinesweeperSolve.loadMinesweeper(puzName + ".minesweeper");
/*  857 */     setSizesAndOffsets(left, top, dim, dim, 0);
/*  858 */     MinesweeperSolve.clearSolution();
/*  859 */     MinesweeperSolve.drawMinesweeper(g2, Grid.letter);
/*  860 */     if (Op.sx[Op.SX.SxRuleLang.ordinal()].equals("English")) {
/*  861 */       st = rules;
/*      */     } else {
/*  863 */       st = Op.ms[Op.MS.MsRule1.ordinal() + Op.getInt(Op.SX.SxRuleLangIndex.ordinal(), Op.sx) - 1];
/*  864 */     }  if (Op.getBool(Op.SX.SxInstructions.ordinal(), Op.sx).booleanValue()) {
/*  865 */       Methods.renderText(g2, left, top + dim + dim / 50, dim, dim / 4, "SansSerif", 1, st, 3, 4, true, 0, 0);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static void printSixpackSol(Graphics2D g2, int left, int top, int dim, int gap, String solName) {
/*  871 */     FontMetrics fm = g2.getFontMetrics();
/*      */     
/*  873 */     String st = Op.sx[Op.SX.SxMs.ordinal()];
/*  874 */     if (st.length() < 3) st = "MINESWEEPER"; 
/*  875 */     int w = fm.stringWidth(st);
/*  876 */     g2.drawString(st, left + (dim - w) / 2, top - gap / 3);
/*  877 */     loadMinesweeper(solName + ".minesweeper");
/*  878 */     setSizesAndOffsets(left, top, dim, dim, 0);
/*  879 */     drawMinesweeper(g2, Grid.letter);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static void printKDPPuz(Graphics2D g2, int left, int top, int dim, int gap, String puzName) {
/*  885 */     FontMetrics fm = g2.getFontMetrics();
/*      */     
/*  887 */     String st = puzName;
/*  888 */     int w = fm.stringWidth(st);
/*  889 */     g2.drawString(st, left + (dim - w) / 2, top - gap / 3);
/*  890 */     MinesweeperSolve.loadMinesweeper(puzName + ".minesweeper");
/*  891 */     setSizesAndOffsets(left, top, dim, dim, 0);
/*  892 */     MinesweeperSolve.clearSolution();
/*  893 */     MinesweeperSolve.drawMinesweeper(g2, Grid.letter);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static void printKDPSol(Graphics2D g2, int left, int top, int dim, int gap, String solName) {
/*  899 */     FontMetrics fm = g2.getFontMetrics();
/*      */     
/*  901 */     String st = solName;
/*  902 */     int w = fm.stringWidth(st);
/*  903 */     g2.drawString(st, left + (dim - w) / 2, top - gap / 3);
/*  904 */     loadMinesweeper(solName + ".minesweeper");
/*  905 */     setSizesAndOffsets(left, top, dim, dim, 0);
/*  906 */     drawMinesweeper(g2, Grid.letter);
/*      */   }
/*      */   
/*      */   static void makeGrid() {
/*  910 */     Methods.havePuzzle = false;
/*  911 */     Grid.xSz = Op.getInt(Op.MS.MsAcross.ordinal(), Op.ms);
/*  912 */     Grid.ySz = Op.getInt(Op.MS.MsDown.ordinal(), Op.ms);
/*  913 */     Grid.clearGrid();
/*      */   }
/*      */   
/*      */   void updateGrid(MouseEvent e) {
/*  917 */     int x = e.getX(), y = e.getY();
/*      */     
/*  919 */     if (Def.building == 1)
/*  920 */       return;  if (x < Grid.xOrg || y < Grid.yOrg)
/*  921 */       return;  x = (x - Grid.xOrg) / Grid.xCell;
/*  922 */     y = (y - Grid.yOrg) / Grid.yCell;
/*  923 */     if (x >= Grid.xSz || y >= Grid.ySz)
/*      */       return; 
/*  925 */     Grid.xCur = x; Grid.yCur = y;
/*  926 */     restoreFrame();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean validMinesweeper() {
/*  932 */     for (int y = 0; y < Grid.ySz; y++) {
/*  933 */       for (int x = 0; x < Grid.xSz; x++)
/*  934 */       { if (Grid.letter[x][y] != 0 && 
/*  935 */           countNeighbours(x, y)[2] != Grid.letter[x][y] - 48)
/*  936 */           return false;  } 
/*  937 */     }  return true;
/*      */   }
/*      */   
/*      */   static int[] countNeighbours(int x, int y) {
/*  941 */     int[] count = { 0, 0, 0, 0 };
/*      */     
/*  943 */     for (int v = 0; v < 8; v++) {
/*  944 */       int i = x + oX[v]; if (i < 0 || i >= Grid.xSz) { count[1] = count[1] + 1; }
/*  945 */       else { int j = y + oY[v]; if (j < 0 || j >= Grid.ySz) { count[1] = count[1] + 1; }
/*  946 */         else if (Grid.letter[i][j] == 0)
/*  947 */         { count[Grid.sol[i][j]] = count[Grid.sol[i][j]] + 1; }  }
/*      */     
/*  949 */     }  return count;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static void setCells(int x, int y, int state) {
/*  955 */     for (int v = 0; v < 8; v++) {
/*  956 */       int i = x + oX[v]; if (i >= 0 && i < Grid.xSz) {
/*  957 */         int j = y + oY[v]; if (j >= 0 && j < Grid.ySz && 
/*  958 */           Grid.sol[i][j] == 0 && Grid.letter[i][j] == 0)
/*  959 */           setOneCell(i, j, state); 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   static void setOneCell(int x, int y, int op) {
/*  964 */     if (Grid.findHint.booleanValue() && op != 3) {
/*  965 */       Grid.hintXb[Grid.hintIndexb] = x;
/*  966 */       Grid.hintYb[Grid.hintIndexb] = y;
/*  967 */       Grid.hintIndexb++;
/*      */     } else {
/*      */       
/*  970 */       Grid.sol[x][y] = op;
/*      */     } 
/*      */   }
/*      */   
/*      */   static boolean required(int mode) {
/*  975 */     boolean found = false;
/*      */     
/*  977 */     for (int y = 0; y < Grid.ySz; y++) {
/*  978 */       for (int x = 0; x < Grid.xSz; x++) {
/*  979 */         if (Grid.letter[x][y] != 0) {
/*  980 */           int[] count = countNeighbours(x, y);
/*  981 */           if (count[0] == 0)
/*  982 */             continue;  if (mode == 1 && 
/*  983 */             count[2] == Grid.letter[x][y] - 48) {
/*  984 */             setCells(x, y, 1); found = true;
/*  985 */           }  if (mode == 2 && 
/*  986 */             count[0] + count[2] == Grid.letter[x][y] - 48) {
/*  987 */             setCells(x, y, 2); found = true;
/*      */           } 
/*  989 */         }  if (Grid.findHint.booleanValue() && found) return true;  continue;
/*      */       } 
/*  991 */     }  return found;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean cellsBlocked() {
/*  997 */     for (int y = 0; y < Grid.ySz; y++) {
/*  998 */       for (int x = 0; x < Grid.xSz; x++) {
/*  999 */         if (Grid.letter[x][y] != 0) {
/* 1000 */           int[] count = countNeighbours(x, y);
/* 1001 */           if (count[0] + count[2] < Grid.letter[x][y] - 48)
/* 1002 */             return true; 
/*      */         } 
/*      */       } 
/* 1005 */     }  return false;
/*      */   }
/*      */ 
/*      */   
/*      */   static boolean scanInvalidMines() {
/* 1010 */     boolean found = false;
/*      */     
/* 1012 */     listIndex = 0;
/* 1013 */     for (int y = 0; y < Grid.ySz; y++) {
/* 1014 */       for (int x = 0; x < Grid.xSz; x++) {
/* 1015 */         if (Grid.letter[x][y] != 0) {
/* 1016 */           int[] count = countNeighbours(x, y);
/* 1017 */           if (count[0] != 0 && 
/* 1018 */             count[2] == Grid.letter[x][y] - 48) {
/* 1019 */             for (int v = 0; v < 8; v++) {
/* 1020 */               int i = x + oX[v]; if (i >= 0 && i < Grid.xSz) {
/* 1021 */                 int j = y + oY[v]; if (j >= 0 && j < Grid.ySz && 
/* 1022 */                   Grid.sol[i][j] == 0 && Grid.letter[i][j] == 0) {
/* 1023 */                   Grid.sol[i][j] = 1;
/* 1024 */                   listX[listIndex] = i;
/* 1025 */                   listY[listIndex++] = j;
/*      */                 } 
/*      */               } 
/* 1028 */             }  found = true;
/*      */           } 
/*      */         } 
/*      */       } 
/* 1032 */     }  return found;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean invalidMine() {
/* 1038 */     for (int y = 0; y < Grid.ySz; y++) {
/* 1039 */       for (int x = 0; x < Grid.xSz; x++) {
/* 1040 */         if (Grid.sol[x][y] == 0) {
/* 1041 */           Grid.sol[x][y] = 2;
/* 1042 */           scanInvalidMines();
/* 1043 */           if (cellsBlocked()) {
/* 1044 */             Grid.sol[x][y] = 1;
/* 1045 */             if (Grid.findHint.booleanValue()) {
/* 1046 */               Grid.sol[x][y] = 0;
/* 1047 */               setOneCell(x, y, 0);
/*      */             } 
/* 1049 */             for (int j = 0; j < listIndex; j++)
/* 1050 */               Grid.sol[listX[j]][listY[j]] = 0; 
/* 1051 */             return true;
/*      */           } 
/*      */           
/* 1054 */           Grid.sol[x][y] = 0;
/* 1055 */           for (int i = 0; i < listIndex; i++) {
/* 1056 */             Grid.sol[listX[i]][listY[i]] = 0;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/* 1061 */     return false;
/*      */   }
/*      */   
/*      */   static int clearExcessPendingCells() {
/* 1065 */     int found = 0;
/*      */ 
/*      */     
/* 1068 */     for (int y = 0; y < Grid.ySz; y++) {
/* 1069 */       for (int x = 0; x < Grid.xSz; x++)
/* 1070 */       { if (Grid.letter[x][y] > 0) {
/*      */           
/* 1072 */           int[] count = countNeighbours(x, y);
/* 1073 */           if (count[0] > Grid.letter[x][y] - 48 - count[2])
/* 1074 */             for (int i = y - 2; i < y + 3; i++) {
/* 1075 */               if (i >= 0 && i < Grid.ySz)
/* 1076 */                 for (int k = x - 2; k < x + 3; k++) {
/* 1077 */                   if (k >= 0 && k < Grid.xSz && (k != x || i != y) && 
/* 1078 */                     Grid.letter[k][i] > 0) {
/* 1079 */                     count = countNeighbours(k, i);
/* 1080 */                     if (count[3] == 0 && count[0] == 2) {
/* 1081 */                       int v; for (v = 0; v < 8; v++) {
/* 1082 */                         int a = k + oX[v]; if (a >= 0 && a < Grid.xSz) {
/* 1083 */                           int b = i + oY[v]; if (b >= 0 && b < Grid.ySz && 
/* 1084 */                             Grid.sol[a][b] == 0 && Grid.letter[a][b] == 0 && (
/* 1085 */                             a < x - 1 || a > x + 1 || b < y - 1 || b > y + 1))
/*      */                             break; 
/*      */                         } 
/* 1088 */                       }  if (v == 8) {
/* 1089 */                         setCells(k, i, 3);
/*      */                       }
/*      */                     } 
/*      */                   } 
/*      */                 }  
/*      */             }  
/* 1095 */           count = countNeighbours(x, y);
/* 1096 */           if (count[0] > 0 && count[3] == (Grid.letter[x][y] - 48 - count[2]) * 2) {
/* 1097 */             setCells(x, y, 1);
/* 1098 */             found = 1;
/*      */           } 
/* 1100 */           if (count[0] > 0 && count[3] == (Grid.letter[x][y] - 48 - count[2] - count[0]) * 2) {
/* 1101 */             setCells(x, y, 2);
/* 1102 */             found = 2;
/*      */           } 
/*      */ 
/*      */           
/* 1106 */           for (int j = 0; j < Grid.ySz; j++) {
/* 1107 */             for (int i = 0; i < Grid.xSz; i++) {
/* 1108 */               if (Grid.sol[i][j] == 3)
/* 1109 */                 Grid.sol[i][j] = 0; 
/*      */             } 
/* 1111 */           }  if (found > 0 && Grid.findHint.booleanValue()) return found; 
/* 1112 */           if (found > 0)
/*      */             break; 
/* 1114 */         }  if (found > 0)
/*      */           break;  } 
/* 1116 */     }  return found;
/*      */   }
/*      */   
/*      */   static boolean invalidClear() {
/* 1120 */     boolean found = false;
/*      */ 
/*      */     
/* 1123 */     for (int y = 0; y < Grid.ySz; y++) {
/* 1124 */       for (int x = 0; x < Grid.xSz; x++)
/* 1125 */       { if (Grid.letter[x][y] > 49)
/* 1126 */         { if (x > 0 && x < Grid.xSz - 1 && Grid.letter[x - 1][y] == 49 && selectiveCount(x - 1, y - 1, x, y - 1, x - 1, y + 1, x, y + 1)[0] > 0) {
/* 1127 */             int[] count = selectiveCount(x + 1, y - 1, x + 1, y, x + 1, y + 1, x, y);
/* 1128 */             int num = Grid.letter[x][y] - 48 - count[2] - 1;
/* 1129 */             for (int v = y - 1; v < y + 2; v++) {
/* 1130 */               if (v >= 0 && v < Grid.ySz && Grid.letter[x + 1][v] == 0 && 
/* 1131 */                 Grid.sol[x + 1][v] == 0)
/* 1132 */                 if (num == count[0])
/* 1133 */                 { setOneCell(x + 1, v, 2); found = true; }
/*      */                 
/* 1135 */                 else if (num == 0 && selectiveCount(x - 2, y - 1, x - 2, y, x - 2, y + 1, x - 1, y)[0] == 0)
/* 1136 */                 { setOneCell(x + 1, v, 1); found = true; }  
/* 1137 */             }  if (Grid.findHint.booleanValue() && found) return true; 
/*      */           } 
/* 1139 */           if (y > 0 && y < Grid.ySz - 1 && Grid.letter[x][y + 1] == 49 && selectiveCount(x - 1, y, x - 1, y + 1, x + 1, y, x + 1, y + 1)[0] > 0) {
/* 1140 */             int[] count = selectiveCount(x - 1, y - 1, x, y - 1, x + 1, y - 1, x, y);
/* 1141 */             int num = Grid.letter[x][y] - 48 - count[2] - 1;
/* 1142 */             for (int v = x - 1; v < x + 2; v++) {
/* 1143 */               if (v >= 0 && v < Grid.xSz && Grid.letter[v][y - 1] == 0 && 
/* 1144 */                 Grid.sol[v][y - 1] == 0)
/* 1145 */                 if (num == count[0])
/* 1146 */                 { setOneCell(v, y - 1, 2); found = true; }
/*      */                 
/* 1148 */                 else if (num == 0 && selectiveCount(x - 1, y + 2, x, y + 2, x + 1, y + 2, x, y + 1)[0] == 0)
/* 1149 */                 { setOneCell(v, y - 1, 1); found = true; }  
/* 1150 */             }  if (Grid.findHint.booleanValue() && found) return true; 
/*      */           } 
/* 1152 */           if (x > 0 && x < Grid.xSz - 1 && Grid.letter[x + 1][y] == 49 && selectiveCount(x + 1, y - 1, x, y - 1, x + 1, y + 1, x, y + 1)[0] > 0) {
/* 1153 */             int[] count = selectiveCount(x - 1, y - 1, x - 1, y, x - 1, y + 1, x, y);
/* 1154 */             int num = Grid.letter[x][y] - 48 - count[2] - 1;
/* 1155 */             for (int v = y - 1; v < y + 2; v++) {
/* 1156 */               if (v >= 0 && v < Grid.ySz && Grid.letter[x - 1][v] == 0 && 
/* 1157 */                 Grid.sol[x - 1][v] == 0)
/* 1158 */                 if (num == count[0])
/* 1159 */                 { setOneCell(x - 1, v, 2); found = true; }
/*      */                 
/* 1161 */                 else if (num == 0 && selectiveCount(x + 2, y - 1, x + 2, y, x + 2, y + 1, x + 1, y)[0] == 0)
/* 1162 */                 { setOneCell(x - 1, v, 1); found = true; }  
/* 1163 */             }  if (Grid.findHint.booleanValue() && found) return true; 
/*      */           } 
/* 1165 */           if (y > 0 && y < Grid.ySz - 1 && Grid.letter[x][y - 1] == 49 && selectiveCount(x - 1, y, x - 1, y - 1, x + 1, y, x + 1, y - 1)[0] > 0) {
/* 1166 */             int[] count = selectiveCount(x - 1, y + 1, x, y + 1, x + 1, y + 1, x, y);
/* 1167 */             int num = Grid.letter[x][y] - 48 - count[2] - 1;
/* 1168 */             for (int v = x - 1; v < x + 2; v++) {
/* 1169 */               if (v >= 0 && v < Grid.xSz && Grid.letter[v][y + 1] == 0 && 
/* 1170 */                 Grid.sol[v][y + 1] == 0)
/* 1171 */                 if (num == count[0])
/* 1172 */                 { setOneCell(v, y + 1, 2); found = true; }
/*      */                 
/* 1174 */                 else if (num == 0 && selectiveCount(x - 1, y - 2, x, y - 2, x + 1, y - 2, x, y - 1)[0] == 0)
/* 1175 */                 { setOneCell(v, y + 1, 1); found = true; }  
/* 1176 */             }  if (Grid.findHint.booleanValue() && found) return true; 
/*      */           } 
/* 1178 */           if (found == true)
/*      */             break;  }  } 
/* 1180 */     }  return found;
/*      */   }
/*      */   
/*      */   static int[] selectiveCount(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
/* 1184 */     int[] count = { 0, 0, 0 };
/* 1185 */     int[] X = { x1, x2, x3, x4 }, Y = { y1, y2, y3, y4 };
/*      */     
/* 1187 */     for (int v = 0; v < 3; v++) {
/* 1188 */       if (X[v] >= 0 && X[v] < Grid.xSz && Y[v] >= 0 && Y[v] < Grid.ySz && Grid.letter[X[v]][Y[v]] == 0)
/* 1189 */         count[Grid.sol[X[v]][Y[v]]] = count[Grid.sol[X[v]][Y[v]]] + 1; 
/* 1190 */     }  return count;
/*      */   }
/*      */ 
/*      */   
/*      */   static int nextHint() {
/* 1195 */     for (int j = 0; j < Grid.ySz - 1; j++) {
/* 1196 */       for (int i = 0; i < Grid.xSz - 1; i++) {
/* 1197 */         if (Grid.sol[i][j] != 0 && Grid.sol[i][j] != Grid.copy[i][j])
/* 1198 */         { Grid.hintXb[Grid.hintIndexb] = i;
/* 1199 */           Grid.hintYb[Grid.hintIndexb] = j;
/* 1200 */           Grid.hintIndexb++; } 
/*      */       } 
/* 1202 */     }  if (Grid.hintIndexb > 0) {
/* 1203 */       return -1;
/*      */     }
/* 1205 */     if (required(1)) return 1; 
/* 1206 */     if (required(2)) return 2; 
/* 1207 */     if (invalidClear()) return 3; 
/* 1208 */     if (clearExcessPendingCells() == 2) return 3; 
/* 1209 */     if (clearExcessPendingCells() == 1) return 4; 
/* 1210 */     if (invalidMine()) return 4; 
/* 1211 */     return 5;
/*      */   }
/*      */   
/*      */   static int solveMinesweeper(int targetDifficulty) {
/* 1215 */     int thisDiff = 0;
/*      */     
/* 1217 */     Methods.clearGrid(Grid.sol);
/*      */     
/*      */     while (true) {
/* 1220 */       if (required(1) || required(2)) { if (thisDiff == 0) thisDiff = 1;  continue; }
/* 1221 */        if (targetDifficulty > 1 && 
/* 1222 */         invalidClear()) { if (thisDiff == 1) thisDiff = 2;  continue; }
/* 1223 */        if (targetDifficulty > 2 && 
/* 1224 */         clearExcessPendingCells() > 0) { if (thisDiff == 2) thisDiff = 3;  continue; }
/* 1225 */        if (targetDifficulty > 3 && 
/* 1226 */         invalidMine()) { if (thisDiff == 3) thisDiff = 4;  continue; }
/*      */       
/*      */       break;
/*      */     } 
/* 1230 */     for (int y = 0; y < Grid.ySz; y++) {
/* 1231 */       for (int x = 0; x < Grid.xSz; x++) {
/* 1232 */         if (Grid.letter[x][y] == 0 && Grid.sol[x][y] == 0)
/* 1233 */           return 0; 
/*      */       } 
/* 1235 */     }  return thisDiff;
/*      */   }
/*      */   
/*      */   int depopulate() {
/* 1239 */     Random r = new Random();
/* 1240 */     int sz = Grid.xSz * Grid.ySz, vec[] = new int[sz];
/*      */     int i;
/* 1242 */     for (i = 0; i < sz; ) { vec[i] = i; i++; }
/* 1243 */      for (i = 0; i < sz; i++) {
/* 1244 */       int j = r.nextInt(sz);
/* 1245 */       int k = vec[i]; vec[i] = vec[j]; vec[j] = k;
/*      */     } 
/*      */     
/*      */     int v;
/* 1249 */     for (v = 0; v < sz; v++) {
/* 1250 */       i = vec[v] % Grid.xSz;
/* 1251 */       int j = vec[v] / Grid.xSz;
/* 1252 */       if (Grid.letter[i][j] == 48 || Grid.letter[i][j] >= 52) {
/* 1253 */         int mem = Grid.letter[i][j];
/* 1254 */         Grid.letter[i][j] = 0;
/* 1255 */         if (solveMinesweeper(Op.getInt(Op.MS.MsDiff.ordinal(), Op.ms)) == 0) {
/* 1256 */           Grid.letter[i][j] = mem;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1261 */     for (v = 0; v < sz; v++) {
/* 1262 */       i = vec[v] % Grid.xSz;
/* 1263 */       int j = vec[v] / Grid.xSz;
/* 1264 */       if (Grid.letter[i][j] != 0) {
/* 1265 */         int mem = Grid.letter[i][j];
/* 1266 */         Grid.letter[i][j] = 0;
/* 1267 */         if (solveMinesweeper(Op.getInt(Op.MS.MsDiff.ordinal(), Op.ms)) == 0)
/* 1268 */           Grid.letter[i][j] = mem; 
/*      */       } 
/*      */     } 
/* 1271 */     return solveMinesweeper(Op.getInt(Op.MS.MsDiff.ordinal(), Op.ms));
/*      */   }
/*      */   
/*      */   private void multiBuild() {
/* 1275 */     String title = Methods.puzzleTitle;
/* 1276 */     int[] diffDef = { 1, 1, 2, 2, 3, 3, 4 }, sizeDef = { 6, 8, 6, 8, 6, 8, 8 };
/*      */ 
/*      */     
/* 1279 */     int diffMem = Op.getInt(Op.MS.MsDiff.ordinal(), Op.ms);
/* 1280 */     int acrossMem = Op.getInt(Op.MS.MsAcross.ordinal(), Op.ms);
/* 1281 */     int downMem = Op.getInt(Op.MS.MsDown.ordinal(), Op.ms);
/*      */ 
/*      */     
/* 1284 */     SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
/* 1285 */     Calendar c = Calendar.getInstance();
/*      */     
/* 1287 */     for (this.hmCount = 0; this.hmCount < this.howMany; this.hmCount++) {
/* 1288 */       if (this.startPuz > 9999999) { try {
/* 1289 */           c.setTime(sdf.parse("" + this.startPuz));
/* 1290 */         } catch (ParseException ex) {}
/* 1291 */         this.startPuz = Integer.parseInt(sdf.format(c.getTime())); }
/*      */ 
/*      */       
/* 1294 */       Methods.puzzleTitle = "MINESWEEPER Puzzle : " + this.startPuz;
/* 1295 */       if (Op.getBool(Op.SX.VaryDiff.ordinal(), Op.sx).booleanValue()) {
/* 1296 */         Grid.xSz = Grid.ySz = sizeDef[(this.startPuz - 1) % 7];
/* 1297 */         Op.setInt(Op.MS.MsAcross.ordinal(), Grid.xSz, Op.ms);
/* 1298 */         Op.setInt(Op.MS.MsDown.ordinal(), Grid.xSz, Op.ms);
/* 1299 */         Op.setInt(Op.MS.MsDiff.ordinal(), diffDef[(this.startPuz - 1) % 7], Op.ms);
/*      */       } 
/*      */       
/* 1302 */       Methods.buildProgress(jfMinesweeper, Op.ms[Op.MS.MsPuz
/* 1303 */             .ordinal()] = "" + this.startPuz + ".minesweeper");
/* 1304 */       buildMinesweeper();
/* 1305 */       restoreFrame();
/* 1306 */       Wait.shortWait(100);
/* 1307 */       if (Def.building == 2)
/*      */         return; 
/* 1309 */       this.startPuz++;
/*      */     } 
/* 1311 */     this.howMany = 1;
/* 1312 */     Methods.puzzleTitle = title;
/*      */     
/* 1314 */     Op.setInt(Op.MS.MsDiff.ordinal(), diffMem, Op.ms);
/* 1315 */     Op.setInt(Op.MS.MsAcross.ordinal(), acrossMem, Op.ms);
/* 1316 */     Op.setInt(Op.MS.MsDown.ordinal(), downMem, Op.ms);
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean buildMinesweeper() {
/* 1321 */     Random r = new Random();
/* 1322 */     int[][] q = new int[20][20];
/*      */     int count;
/* 1324 */     for (count = 0; count < 10000; count++) {
/*      */       int j;
/* 1326 */       for (j = 0; j < Grid.ySz; j++) {
/* 1327 */         for (int k = 0; k < Grid.xSz; k++) {
/* 1328 */           Grid.sol[k][j] = 0; Grid.copy[k][j] = 0;
/* 1329 */           Grid.letter[k][j] = 48;
/* 1330 */           q[k][j] = 100;
/*      */         } 
/*      */       } 
/*      */       
/* 1334 */       int i = r.nextInt(Grid.xSz);
/* 1335 */       j = r.nextInt(Grid.ySz);
/*      */       int a;
/* 1337 */       for (a = 0; a < 15 * Grid.xSz * Grid.ySz / 100; a++) {
/*      */         
/* 1339 */         Grid.sol[i][j] = 2;
/* 1340 */         Grid.letter[i][j] = 0;
/* 1341 */         q[i][j] = -1;
/*      */         
/*      */         int k;
/* 1344 */         for (k = 0; k < Grid.ySz; k++) {
/* 1345 */           for (int m = 0; m < Grid.xSz; m++) {
/* 1346 */             if (q[m][k] != -1) {
/* 1347 */               int b = Math.abs(m - i) + Math.abs(k - j);
/* 1348 */               if (q[m][k] > b)
/* 1349 */                 q[m][k] = b; 
/*      */             } 
/*      */           } 
/*      */         }  int w;
/* 1353 */         for (count = w = k = 0; k < Grid.ySz; k++) {
/* 1354 */           for (int m = 0; m < Grid.xSz; m++) {
/* 1355 */             if (q[m][k] > w) {
/* 1356 */               w = q[m][k];
/* 1357 */               count = 1;
/*      */             }
/* 1359 */             else if (q[m][k] == w) {
/* 1360 */               count++;
/*      */             } 
/*      */           } 
/* 1363 */         }  int v = r.nextInt(count);
/*      */         
/* 1365 */         for (int x = j = 0; j < Grid.ySz; j++) {
/* 1366 */           for (i = 0; i < Grid.xSz; i++) {
/* 1367 */             if (q[i][j] == w) {
/* 1368 */               if (x == v) {
/*      */                 break;
/*      */               }
/* 1371 */               x++;
/*      */             } 
/* 1373 */           }  if (i < Grid.xSz) {
/*      */             break;
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/* 1379 */       for (j = 0; j < Grid.ySz; j++) {
/* 1380 */         for (i = 0; i < Grid.xSz; i++) {
/* 1381 */           if (Grid.sol[i][j] == 2)
/* 1382 */             for (int b = -1; b < 2; b++) {
/* 1383 */               int k = j + b;
/* 1384 */               if (k >= 0 && k < Grid.ySz)
/* 1385 */                 for (a = -1; a < 2; a++) {
/* 1386 */                   int x = i + a;
/* 1387 */                   if (x >= 0 && x < Grid.xSz && 
/* 1388 */                     Grid.sol[x][k] != 2)
/* 1389 */                     Grid.letter[x][k] = Grid.letter[x][k] + 1; 
/*      */                 }  
/*      */             }  
/*      */         } 
/* 1393 */       }  if (Op.getInt(Op.MS.MsDiff.ordinal(), Op.ms) == depopulate())
/*      */         break; 
/* 1395 */       if (Def.building == 2)
/* 1396 */         return true; 
/* 1397 */       if (this.howMany == 1 && count % 500 == 0) {
/* 1398 */         restoreFrame();
/* 1399 */         Methods.buildProgress(jfMinesweeper, Op.ms[Op.MS.MsPuz.ordinal()]);
/*      */       } 
/*      */     } 
/*      */     
/* 1403 */     if (count >= 10000) return false; 
/* 1404 */     for (int y = 0; y < Grid.ySz; y++) {
/* 1405 */       for (int x = 0; x < Grid.xSz; x++) {
/* 1406 */         Grid.copy[x][y] = Grid.sol[x][y];
/* 1407 */         Grid.sol[x][y] = 0;
/*      */       } 
/* 1409 */     }  saveMinesweeper(Op.ms[Op.MS.MsPuz.ordinal()]);
/* 1410 */     return true;
/*      */   }
/*      */   void handleKeyPressed(KeyEvent e) {
/*      */     char ch;
/* 1414 */     if (Def.building == 1)
/* 1415 */       return;  if (e.isAltDown())
/* 1416 */       return;  switch (e.getKeyCode()) { case 38:
/* 1417 */         if (Grid.yCur > 0) Grid.yCur--;  break;
/* 1418 */       case 40: if (Grid.yCur < Grid.ySz - 1) Grid.yCur++;  break;
/* 1419 */       case 37: if (Grid.xCur > 0) Grid.xCur--;  break;
/* 1420 */       case 39: if (Grid.xCur < Grid.xSz - 1) Grid.xCur++;  break;
/* 1421 */       case 36: Grid.xCur = 0; break;
/* 1422 */       case 35: Grid.xCur = Grid.xSz - 1; break;
/* 1423 */       case 33: Grid.yCur = 0; break;
/* 1424 */       case 34: Grid.yCur = Grid.ySz - 1; break;
/*      */       case 8:
/*      */       case 10:
/*      */       case 32:
/*      */       case 127:
/* 1429 */         Grid.letter[Grid.xCur][Grid.yCur] = 0;
/*      */         break;
/*      */       default:
/* 1432 */         ch = e.getKeyChar();
/* 1433 */         if (Character.isDigit(ch) && ch < '9')
/* 1434 */           Grid.letter[Grid.xCur][Grid.yCur] = ch; 
/*      */         break; }
/*      */     
/* 1437 */     restoreFrame();
/*      */   }
/*      */ }


/* Location:              C:\Users\mustapha\Desktop\Crossword-Express.jar!\crosswordexpress\MinesweeperBuild.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */