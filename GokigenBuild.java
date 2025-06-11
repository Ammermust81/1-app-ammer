/*      */ package crosswordexpress;
/*      */ import java.awt.FontMetrics;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.Toolkit;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.io.DataInputStream;
/*      */ import java.io.DataOutputStream;
/*      */ import javax.swing.JComboBox;
/*      */ import javax.swing.JDialog;
/*      */ import javax.swing.JLabel;
/*      */ import javax.swing.JMenuItem;
/*      */ import javax.swing.JPanel;
/*      */ import javax.swing.KeyStroke;
/*      */ 
/*      */ public class GokigenBuild {
/*      */   static JFrame jfGokigen;
/*      */   static JMenuBar menuBar;
/*      */   JMenu menu;
/*      */   JMenu submenu;
/*      */   JMenuItem menuItem;
/*      */   JMenuItem buildMenuItem;
/*   23 */   static int howMany = 1; static JPanel pp; static int panelW; static int panelH; static JLabel jl1; static JLabel jl2; Timer myTimer; Thread thread; static int startPuz = Integer.parseInt((new SimpleDateFormat("yyyyMMdd")).format(new Date())); static int hmCount;
/*      */   boolean sixpack;
/*      */   static int mazeID;
/*   26 */   static String rules = "Place a diagonal line in each square of the puzzle. The circled numbers indicate the number of diagonals which must converge at that point. The diagonal lines must not form a closed loop of any size.";
/*      */ 
/*      */   
/*      */   static void def() {
/*   30 */     Op.updateOption(Op.GK.GkW.ordinal(), "500", Op.gk);
/*   31 */     Op.updateOption(Op.GK.GkH.ordinal(), "580", Op.gk);
/*   32 */     Op.updateOption(Op.GK.GkAcross.ordinal(), "9", Op.gk);
/*   33 */     Op.updateOption(Op.GK.GkDown.ordinal(), "9", Op.gk);
/*   34 */     Op.updateOption(Op.GK.GkDifficulty.ordinal(), "1", Op.gk);
/*   35 */     Op.updateOption(Op.GK.GkCell.ordinal(), "FFFFDD", Op.gk);
/*   36 */     Op.updateOption(Op.GK.GkCircle.ordinal(), "CCFFCC", Op.gk);
/*   37 */     Op.updateOption(Op.GK.GkLine.ordinal(), "883300", Op.gk);
/*   38 */     Op.updateOption(Op.GK.GkNumber.ordinal(), "000000", Op.gk);
/*   39 */     Op.updateOption(Op.GK.GkDiagonal.ordinal(), "006666", Op.gk);
/*   40 */     Op.updateOption(Op.GK.GkError.ordinal(), "FF0000", Op.gk);
/*   41 */     Op.updateOption(Op.GK.GkPuz.ordinal(), "sample.gokigen", Op.gk);
/*   42 */     Op.updateOption(Op.GK.GkFont.ordinal(), "SansSerif", Op.gk);
/*   43 */     Op.updateOption(Op.GK.GkPuzColor.ordinal(), "false", Op.gk);
/*   44 */     Op.updateOption(Op.GK.GkSolColor.ordinal(), "false", Op.gk);
/*      */   }
/*      */   
/*   47 */   String gokigenHelp = "<div>A GOKIGEN puzzle consists of a square or rectangular grid. To solve a GOKIGEN puzzle, you must place a diagonal line into every cell of the puzzle. The numbers in the circles at the corners of some of the cells indicates the number of diagonals which converge on that point. The diagonal lines are not permitted to combine to form a loop of any size. Each puzzle has a single unique solution which does not require any guesswork to achieve. The Crossword Express GOKIGEN construction function allows you to build puzzles either manually or automatically in sizes from 5x5 up to 10x10.<p/></div><span class='m'>Menu Functions</span><br/><br/><ul><li/><span class='s'>File Menu</span><br/><ul><li/><span>Load a Puzzle</span><br/>Use this option to choose your puzzle from the pool of GOKIGEN puzzles currently available on your computer.<p/><li/><span>Save</span><br/>If you have done some manual editing of the puzzle, this option will save those changes under the existing file name.<p/><li/><span>SaveAs</span><br/>This option allows you to make an exact copy of the current puzzle using a different file name. The copy will be saved in the <b>gokigen</b> folder along with all of the Gokigen puzzles you have made. Alternatively, if you don't enter a new name for the puzzle, you can change the Puzzle Description, or any of the other descriptive items without changing the puzzle name.<p/><li/><span>Quit Construction</span><br/>Returns you to the Crossword Express opening screen.<p/></ul><li/><span class='s'>Build Menu</span><br/><ul><li/><span>Start a New Puzzle</span><br/>This option presents you with a dialog into which you can enter a file name for your new puzzle. You can also enter several other pieces of information such as a <b>Puzzle Title, Author</b> and <b>Copyright</b> information.<p/><li/><span>Build Options</span><br/>Use this option to access a Build Options dialog where you can set some rules to be followed by the puzzle building function as it builds your puzzle.<p/><li/><span>Start Building / Stop Building</span><br/>Construction of the puzzle will commence when you select the <b>Start Building</b> option. If puzzle building is successful you will receive a message containing the name of the puzzle file, and the location where it was saved. If it becomes necessary, you can interrupt the building process by selecting this option a second time. Note that during the construction phase, the text of this option is changed to <b>Stop Building</b><p/><li/><span>Test Puzzle Validity</span><br/>Manual construction of a GOKIGEN puzzle is not recommended. You should only attempt this if you have a valid puzzle (possibly one published in a magazine) which you would like to enter into the program. Simply move the cursor cell around the puzzle, and type the required values into the appropriate cells. Selecting the <b>Test Puzzle Validity</b> option will check the validity of the puzzle. If it has a unique solution, it will be saved, and you will be advised of this. If not, you will receive a message that the puzzle is not valid.<p/> </ul><li/><span class='s'>View Menu</span><br/><ul><li/><span/>Display Options</span><br/>This leads you to a dialog box in which you can change the colors of various elements within the puzzle, and control the fonts which will be used for the puzzle's text components. You can also decide if printing of the puzzle will be done in black and white or in color.<p/></ul><li/><span class='s'>Export Menu</span><br/><ul><li/><span>Export Gokigen Web-App</span><br/>This function allows you to export a Web Application Program which you can then upload to your own web site to provide a fully interactive Gokigen puzzle for the entertainment of visitors to your site. For a full description of the facilities provided by this Web-App, please refer to the Help available at <b>Help / Gokigen Web Application</b> on the menu bar of the <b>Build Gokigen</b> window of this program.<p/><li/><span>Launch a Demo Gokigen Web App</span><br/>Take a first look at the Gokigen Web App. See what it could do to enhance your web site.<p/><li/><span>Print a Gokigen KDP puzzle book.</span><br/>The letters KDP stand for <b>Kindle Direct Publishing</b>. This is a free publishing service operared by Amazon, in which they handle all matters related to printing, advertising and sales of books created by members of the public. A portion of the proceeds are retained by Amazon while the remainder is paid to the author. Fifteen of the Puzzles created by Crossword Express can be printed into PDF format files ready for publication by Amazon. When you select this option, you will be presented with a dialog which allows you to control the process. Please study the Help offered by this dialog before attempting to make use of it.</ul><li/><span class='s'>Tasks Menu</span><br/><ul><li/><span>Print this Puzzle</span><br/>This will take you to a custom print screen where you can control the details involved with printing your puzzle.<p/><li/><span>Solve this Puzzle</span><br/>This will take you to a Solve screen which provides a fully interactive environment for solving the puzzle.<p/><li/><span>Delete this Puzzle</span><br/>Use this option to eliminate unwanted GOKIGEN puzzles from your file system.<p/></ul><li/><span class='s'>Help Menu</span><br/><ul><li/><span>Gokigen Help</span><br/>Displays the Help screen which you are now reading.<p/></ul></ul></body>";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  126 */   String gokigenOptions = "<div>Before you give the command to build the <b>Gokigen</b> puzzle, you can set some options which the program will use during the construction process.</div><br/><ul><li/><b>Difficulty:</b> Select the difficulty level to be used for your puzzle. The difficulty can be varied between <b>Trivial</b> and <b>Too Hard</b>, and is selected by means of a slider control.<p/><li/>Gokigen puzzles can be made in sizes ranging from 5x5 up to 10x10. This can be controlled using the <b>Cells Across</b> and <b>Cells Down</b> combo-boxes.<p/><li/>If you want to make a number of puzzles all having the same dimensions, simply type a number into the <b>How many puzzles</b> input field. When you issue the Make command, Crossword Express will make that number of puzzles. The puzzle names will be numbers which represent a date in <b>yyyymmdd</b> format. The default value presented by Crossword Express is always the current date, but you can change this to any date that suits your needs. As the series of puzzles is created, CWE will automatically step on to the next date in the sequence, taking into account such factors as the varying number of days in the months, and of course leap years. Virtually any number of puzzles can be made in a single operation using this feature.<p/><li/><b>HOWEVER:</b> If you prefer a simpler numbering scheme for your puzzles, you can enter any number of 7 digits or less to be used for your first puzzle, and Crossword Express will number the remainder of the puzzles sequentially starting with your number.<p/><li/>If you do choose to make multiple puzzles, then by default, Crossword Express will change the difficulty of the resulting puzzles over a cycle of seven puzzles. This would be useful for a daily newspaper so that the week could start with a very easy puzzle, with quite difficult puzzles reserved for the weekend. If you don't want this feature, clearing the <b>Vary Difficulty on 7 day cycle</b> checkbox will disable it.</ul></body>";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  150 */   static String webAppHelp = "<div><span>Step 1: Export</span><br/>To export the Web Application, select the function <b>Export / Export Puzzle as Web App</b> from the menu bar of the <b>Build Gokigen</b> window of this program. The necessary files will be exported into a folder called <b>Web-App</b> located on your computer's Desktop. Please note that you will need a working Internet connection for this function to operate correctly. The exported files are as follows:-<ul><li><span>gokigen.js</span> This is the file which contains all of the Java Script which implements the interactive features of the Web App.<li><span>gokigen.html</span> This file creates the URL address used to access the Web App. Its main purpose is to start the operation of the gokigen.js Java Script file. If you open gokigen.html with a simple text editor, you will find two appearances of the following text fragment ... <b>&lt;!-- Your HTML code here. --></b>. If you are an experienced HTML coder and would like to add some content of your own to the Web App window, simply replace these fragments with your own HTML code.</ul><span>Step 2: Familiarization</span><br/>Having exported the App, you can give it a first run by starting your web browser, and using it to open the <b>gokigen.html</b> file mentioned above, or more simply, just double click the gokigen.html icon. <p/><span>Step 3: Installation</span><br/>Installation is a three step process:-<ul><li>Create a new folder somewhere on your web server.<li>Upload the two files already discussed into this new folder.<li>Create a link from a convenient point within your web site to the gokigen.html file in your newly created folder.</ul>At this point, any visitor will be able to make full use of the Gokigen Web-App. There is no need to upload any Gokigen puzzle files. This App makes new puzzles on demand as described in the Help information available from the Help button on the App.<p/></div></body>";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   GokigenBuild(JFrame jf, boolean auto, int hm, int start) {
/*  181 */     Def.puzzleMode = 90;
/*  182 */     Def.building = 0;
/*  183 */     Def.dispCursor = Boolean.valueOf(true);
/*      */     
/*  185 */     makeGrid();
/*      */     
/*  187 */     jfGokigen = new JFrame("Gokigen");
/*  188 */     if (Op.getInt(Op.GK.GkH.ordinal(), Op.gk) > Methods.scrH - 200) {
/*  189 */       int diff = Op.getInt(Op.GK.GkH.ordinal(), Op.gk) - Op.getInt(Op.GK.GkW.ordinal(), Op.gk);
/*  190 */       Op.setInt(Op.GK.GkH.ordinal(), Methods.scrH - 200, Op.gk);
/*  191 */       Op.setInt(Op.GK.GkW.ordinal(), Methods.scrH - 200 + diff, Op.gk);
/*      */     } 
/*  193 */     jfGokigen.setSize(Op.getInt(Op.GK.GkW.ordinal(), Op.gk), Op.getInt(Op.GK.GkH.ordinal(), Op.gk));
/*  194 */     int frameX = (jf.getX() + jfGokigen.getWidth() > Methods.scrW) ? (Methods.scrW - jfGokigen.getWidth() - 10) : jf.getX();
/*  195 */     jfGokigen.setLocation(frameX, jf.getY());
/*  196 */     jfGokigen.setLayout((LayoutManager)null);
/*  197 */     jfGokigen.setDefaultCloseOperation(0);
/*      */     
/*  199 */     jfGokigen
/*  200 */       .addComponentListener(new ComponentAdapter() {
/*      */           public void componentResized(ComponentEvent ce) {
/*  202 */             int oldw = Op.getInt(Op.GK.GkW.ordinal(), Op.gk);
/*  203 */             int oldh = Op.getInt(Op.GK.GkH.ordinal(), Op.gk);
/*  204 */             Methods.frameResize(GokigenBuild.jfGokigen, oldw, oldh, 500, 580);
/*  205 */             Op.setInt(Op.GK.GkW.ordinal(), GokigenBuild.jfGokigen.getWidth(), Op.gk);
/*  206 */             Op.setInt(Op.GK.GkH.ordinal(), GokigenBuild.jfGokigen.getHeight(), Op.gk);
/*  207 */             GokigenBuild.restoreFrame();
/*      */           }
/*      */         });
/*      */     
/*  211 */     jfGokigen
/*  212 */       .addWindowListener(new WindowAdapter() {
/*      */           public void windowClosing(WindowEvent we) {
/*  214 */             if (Def.building == 1 || Def.selecting)
/*  215 */               return;  Op.saveOptions("gokigen.opt", Op.gk);
/*  216 */             CrosswordExpress.transfer(1, GokigenBuild.jfGokigen);
/*      */           }
/*      */         });
/*      */     
/*  220 */     Methods.closeHelp();
/*      */ 
/*      */     
/*  223 */     Runnable buildThread = () -> {
/*      */         if (howMany == 1) {
/*      */           buildGokigen();
/*      */         } else {
/*      */           multiBuild();
/*      */           
/*      */           if (this.sixpack) {
/*      */             Sixpack.trigger();
/*      */             jfGokigen.dispose();
/*      */             Def.building = 0;
/*      */             return;
/*      */           } 
/*      */         } 
/*      */         this.buildMenuItem.setText("Start Building");
/*      */         if (Def.building == 2) {
/*      */           Def.building = 0;
/*      */           Methods.interrupted(jfGokigen);
/*      */           makeGrid();
/*      */           restoreFrame();
/*      */           return;
/*      */         } 
/*      */         Methods.havePuzzle = true;
/*      */         restoreFrame();
/*      */         Methods.puzzleSaved(jfGokigen, "gokigen", Op.gk[Op.GK.GkPuz.ordinal()]);
/*      */         Def.building = 0;
/*      */       };
/*  249 */     jl1 = new JLabel(); jfGokigen.add(jl1);
/*  250 */     jl2 = new JLabel(); jfGokigen.add(jl2);
/*      */ 
/*      */     
/*  253 */     menuBar = new JMenuBar();
/*  254 */     menuBar.setBackground(Def.COLOR_MENUBAR);
/*  255 */     jfGokigen.setJMenuBar(menuBar);
/*      */     
/*  257 */     this.menu = new JMenu("File");
/*  258 */     menuBar.add(this.menu);
/*  259 */     this.menuItem = new JMenuItem("Load a Puzzle");
/*  260 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(76, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  261 */     this.menu.add(this.menuItem);
/*  262 */     this.menuItem
/*  263 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1)
/*      */             return; 
/*      */           pp.invalidate();
/*      */           pp.repaint();
/*      */           new Select(jfGokigen, "gokigen", "gokigen", Op.gk, Op.GK.GkPuz.ordinal(), false);
/*      */         });
/*  270 */     this.menuItem = new JMenuItem("Save");
/*  271 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(83, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  272 */     this.menu.add(this.menuItem);
/*  273 */     this.menuItem
/*  274 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           saveGokigen(Op.gk[Op.GK.GkPuz.ordinal()]);
/*      */           Methods.puzzleSaved(jfGokigen, "gokigen", Op.gk[Op.GK.GkPuz.ordinal()]);
/*      */         });
/*  281 */     this.menuItem = new JMenuItem("SaveAs");
/*  282 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(65, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  283 */     this.menu.add(this.menuItem);
/*  284 */     this.menuItem
/*  285 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           Methods.puzzleDescriptionDialog(jfGokigen, Op.gk[Op.GK.GkPuz.ordinal()].substring(0, Op.gk[Op.GK.GkPuz.ordinal()].indexOf(".gokigen")), "gokigen", ".gokigen");
/*      */           if (Methods.clickedOK) {
/*      */             saveGokigen(Op.gk[Op.GK.GkPuz.ordinal()] = Methods.theFileName);
/*      */             restoreFrame();
/*      */             Methods.puzzleSaved(jfGokigen, "gokigen", Op.gk[Op.GK.GkPuz.ordinal()]);
/*      */           } 
/*      */         });
/*  296 */     this.menuItem = new JMenuItem("Quit Construction");
/*  297 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(81, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  298 */     this.menu.add(this.menuItem);
/*  299 */     this.menuItem
/*  300 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           
/*      */           Op.saveOptions("gokigen.opt", Op.gk);
/*      */           CrosswordExpress.transfer(1, jfGokigen);
/*      */         });
/*  308 */     this.menu = new JMenu("Build");
/*  309 */     menuBar.add(this.menu);
/*  310 */     this.menuItem = new JMenuItem("Start a new Puzzle");
/*  311 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(78, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  312 */     this.menu.add(this.menuItem);
/*  313 */     this.menuItem
/*  314 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           Methods.puzzleDescriptionDialog(jfGokigen, Op.gk[Op.GK.GkPuz.ordinal()].substring(0, Op.gk[Op.GK.GkPuz.ordinal()].indexOf(".gokigen")), "gokigen", ".gokigen");
/*      */           if (Methods.clickedOK) {
/*      */             Op.gk[Op.GK.GkPuz.ordinal()] = Methods.theFileName;
/*      */             makeGrid();
/*      */           } 
/*      */           restoreFrame();
/*      */         });
/*  325 */     this.menuItem = new JMenuItem("Build Options");
/*  326 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(79, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  327 */     this.menu.add(this.menuItem);
/*  328 */     this.menuItem
/*  329 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1)
/*      */             return; 
/*      */           gokigenOptions();
/*      */           if (Methods.clickedOK) {
/*      */             makeGrid();
/*      */             if (howMany > 1)
/*      */               Op.gk[Op.GK.GkPuz.ordinal()] = "" + startPuz + ".gokigen"; 
/*      */           } 
/*      */           restoreFrame();
/*      */         });
/*  340 */     this.buildMenuItem = new JMenuItem("Start Building");
/*  341 */     this.buildMenuItem.setAccelerator(KeyStroke.getKeyStroke(66, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  342 */     this.menu.add(this.buildMenuItem);
/*  343 */     this.buildMenuItem
/*  344 */       .addActionListener(ae -> {
/*      */           if (Op.gk[Op.GK.GkPuz.ordinal()].length() == 0 && howMany == 1) {
/*      */             Methods.noName(jfGokigen);
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
/*  360 */     this.menuItem = new JMenuItem("Test Puzzle Validity");
/*  361 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(84, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  362 */     this.menu.add(this.menuItem);
/*  363 */     this.menuItem
/*  364 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           
/*      */           if (solveGokigen() && validGokigen()) {
/*      */             Methods.havePuzzle = true;
/*      */             saveGokigen(Op.gk[Op.GK.GkPuz.ordinal()]);
/*      */             restoreFrame();
/*      */             Methods.puzzleSaved(jfGokigen, "gokigen", Op.gk[Op.GK.GkPuz.ordinal()]);
/*      */             return;
/*      */           } 
/*      */           JOptionPane.showMessageDialog(jfGokigen, "There is no solution to this puzzle", "Test Result", 1);
/*      */           restoreFrame();
/*      */         });
/*  379 */     this.menu = new JMenu("View");
/*  380 */     menuBar.add(this.menu);
/*  381 */     this.menuItem = new JMenuItem("Display Options");
/*  382 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(89, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  383 */     this.menu.add(this.menuItem);
/*  384 */     this.menuItem
/*  385 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           
/*      */           printOptions(jfGokigen, "Display Options");
/*      */           restoreFrame();
/*      */         });
/*  393 */     this.menu = new JMenu("Export");
/*  394 */     menuBar.add(this.menu);
/*  395 */     this.menuItem = new JMenuItem("Export Gokigen Web-App");
/*  396 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(71, 1));
/*  397 */     this.menu.add(this.menuItem);
/*  398 */     this.menuItem
/*  399 */       .addActionListener(ae -> Methods.exportWebApp(jfGokigen, "gokigen"));
/*      */ 
/*      */     
/*  402 */     this.menuItem = new JMenuItem("Launch a Demo Gokigen Web App");
/*  403 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(76, 1));
/*  404 */     this.menu.add(this.menuItem);
/*  405 */     this.menuItem
/*  406 */       .addActionListener(ae -> Methods.launchWebApp(jfGokigen, "gokigen"));
/*      */ 
/*      */     
/*  409 */     this.menuItem = new JMenuItem("Print a Gokigen KDP puzzle book.");
/*  410 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(75, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  411 */     this.menu.add(this.menuItem);
/*  412 */     this.menuItem
/*  413 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           
/*      */           Methods.printKdpDialog(jfGokigen, 90, 6);
/*      */         });
/*  420 */     this.menu = new JMenu("Tasks");
/*  421 */     menuBar.add(this.menu);
/*  422 */     this.menuItem = new JMenuItem("Print this Puzzle");
/*  423 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(80, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  424 */     this.menu.add(this.menuItem);
/*  425 */     this.menuItem
/*  426 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           CrosswordExpress.toPrint(jfGokigen, Op.gk[Op.GK.GkPuz.ordinal()]);
/*      */         });
/*  432 */     this.menuItem = new JMenuItem("Solve this Puzzle");
/*  433 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(86, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  434 */     this.menu.add(this.menuItem);
/*  435 */     this.menuItem
/*  436 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1)
/*      */             return; 
/*      */           if (Methods.havePuzzle) {
/*      */             CrosswordExpress.transfer(91, jfGokigen);
/*      */           } else {
/*      */             Methods.noPuzzle(jfGokigen, "Solve");
/*      */           } 
/*      */         });
/*  445 */     this.menuItem = new JMenuItem("Delete this Puzzle");
/*  446 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(68, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  447 */     this.menu.add(this.menuItem);
/*  448 */     this.menuItem
/*  449 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           
/*      */           if (Methods.deleteAPuzzle(jfGokigen, Op.gk[Op.GK.GkPuz.ordinal()], "gokigen", pp)) {
/*      */             makeGrid();
/*      */             loadGokigen(Op.gk[Op.GK.GkPuz.ordinal()]);
/*      */             restoreFrame();
/*      */           } 
/*      */         });
/*  460 */     this.menu = new JMenu("Help");
/*  461 */     menuBar.add(this.menu);
/*  462 */     this.menuItem = new JMenuItem("Gokigen Help");
/*  463 */     this.menu.add(this.menuItem);
/*  464 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(72, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  465 */     this.menuItem
/*  466 */       .addActionListener(ae -> Methods.cweHelp(jfGokigen, null, "Building Gokigen Puzzles", this.gokigenHelp));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  471 */     this.menuItem = new JMenuItem("Gokigen Web Application");
/*  472 */     this.menu.add(this.menuItem);
/*  473 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(71, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  474 */     this.menuItem
/*  475 */       .addActionListener(ae -> Methods.cweHelp(jfGokigen, null, "Gokigen Web Application", webAppHelp));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  481 */     pp = new GokigenBuildPP(0, 37);
/*  482 */     jfGokigen.add(pp);
/*      */     
/*  484 */     pp
/*  485 */       .addMouseListener(new MouseAdapter() {
/*      */           public void mousePressed(MouseEvent e) {
/*  487 */             GokigenBuild.this.updateGrid(e);
/*      */           }
/*      */         });
/*      */ 
/*      */     
/*  492 */     pp
/*  493 */       .addMouseMotionListener(new MouseAdapter() {
/*      */           public void mouseMoved(MouseEvent e) {
/*  495 */             if (Def.isMac) {
/*  496 */               GokigenBuild.jfGokigen.setResizable((GokigenBuild.jfGokigen.getWidth() - e.getX() < 15 && GokigenBuild.jfGokigen
/*  497 */                   .getHeight() - e.getY() < 95));
/*      */             }
/*      */           }
/*      */         });
/*      */     
/*  502 */     jfGokigen
/*  503 */       .addKeyListener(new KeyAdapter() {
/*      */           public void keyPressed(KeyEvent e) {
/*  505 */             GokigenBuild.this.handleKeyPressed(e);
/*      */           }
/*      */         });
/*      */ 
/*      */     
/*  510 */     loadGokigen(Op.gk[Op.GK.GkPuz.ordinal()]);
/*  511 */     restoreFrame();
/*      */ 
/*      */     
/*  514 */     ActionListener timerAL = ae -> {
/*      */         this.myTimer.stop();
/*      */         this.thread = new Thread(paramRunnable);
/*      */         this.thread.start();
/*      */         Def.building = 1;
/*      */       };
/*  520 */     this.myTimer = new Timer(1000, timerAL);
/*      */     
/*  522 */     if (auto) {
/*  523 */       this.sixpack = true;
/*  524 */       howMany = hm; startPuz = start;
/*  525 */       this.myTimer.start();
/*      */     } 
/*      */   }
/*      */   
/*      */   static void restoreFrame() {
/*  530 */     jfGokigen.setVisible(true);
/*  531 */     Insets insets = jfGokigen.getInsets();
/*  532 */     panelW = jfGokigen.getWidth() - insets.left + insets.right;
/*  533 */     panelH = jfGokigen.getHeight() - insets.top + insets.bottom + 37 + menuBar.getHeight();
/*  534 */     pp.setSize(panelW, panelH);
/*  535 */     jfGokigen.requestFocusInWindow();
/*  536 */     pp.repaint();
/*  537 */     Methods.infoPanel(jl1, jl2, "Build Gokigen", "Puzzle : " + Op.gk[Op.GK.GkPuz.ordinal()], panelW);
/*      */   }
/*      */   
/*      */   static void setSizesAndOffsets(int x, int y, int width, int height) {
/*  541 */     int i = (width - 10) / Grid.xSz;
/*  542 */     int j = (height - 10) / Grid.ySz;
/*  543 */     Grid.xCell = Grid.yCell = (i < j) ? i : j;
/*  544 */     Grid.xOrg = x + (Grid.xCell + width - Grid.xSz * Grid.xCell) / 2;
/*  545 */     Grid.yOrg = y + (Grid.yCell + height - Grid.ySz * Grid.yCell) / 2;
/*      */   }
/*      */   
/*      */   static void makeGrid() {
/*  549 */     Methods.havePuzzle = false;
/*  550 */     Grid.clearGrid();
/*  551 */     Grid.xSz = Op.getInt(Op.GK.GkAcross.ordinal(), Op.gk);
/*  552 */     Grid.ySz = Op.getInt(Op.GK.GkDown.ordinal(), Op.gk);
/*  553 */     for (int j = 0; j < Grid.ySz; j++) {
/*  554 */       for (int i = 0; i < Grid.xSz; i++) {
/*  555 */         Grid.copy[i][j] = 5;
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private void gokigenOptions() {
/*  561 */     JDialog jdlgGokigen = new JDialog(jfGokigen, "Gokigen Options", true);
/*  562 */     jdlgGokigen.setSize(390, 270);
/*  563 */     jdlgGokigen.setResizable(false);
/*  564 */     jdlgGokigen.setLayout((LayoutManager)null);
/*  565 */     jdlgGokigen.setLocation(jfGokigen.getX(), jfGokigen.getY());
/*      */     
/*  567 */     jdlgGokigen
/*  568 */       .addWindowListener(new WindowAdapter() {
/*      */           public void windowClosing(WindowEvent we) {
/*  570 */             Methods.closeHelp();
/*      */           }
/*      */         });
/*      */     
/*  574 */     Methods.closeHelp();
/*      */     
/*  576 */     JPanel diffPanel = new JPanel();
/*  577 */     diffPanel.setLayout((LayoutManager)null);
/*  578 */     diffPanel.setSize(110, 220);
/*  579 */     diffPanel.setLocation(10, 10);
/*  580 */     diffPanel.setOpaque(true);
/*  581 */     diffPanel.setBorder(BorderFactory.createEtchedBorder());
/*  582 */     jdlgGokigen.add(diffPanel);
/*      */     
/*  584 */     JLabel jlDiff = new JLabel("Difficulty");
/*  585 */     jlDiff.setForeground(Def.COLOR_LABEL);
/*  586 */     jlDiff.setSize(100, 20);
/*  587 */     jlDiff.setLocation(5, 5);
/*  588 */     jlDiff.setHorizontalAlignment(0);
/*  589 */     diffPanel.add(jlDiff);
/*      */     
/*  591 */     JLabel jl = new JLabel("Too Hard");
/*  592 */     jl.setForeground(Def.COLOR_LABEL);
/*  593 */     jl.setSize(80, 20);
/*  594 */     jl.setLocation(35, 25);
/*  595 */     jl.setHorizontalAlignment(2);
/*  596 */     diffPanel.add(jl);
/*      */     
/*  598 */     jl = new JLabel("Very Hard");
/*  599 */     jl.setForeground(Def.COLOR_LABEL);
/*  600 */     jl.setSize(80, 20);
/*  601 */     jl.setLocation(35, 51);
/*  602 */     jl.setHorizontalAlignment(2);
/*  603 */     diffPanel.add(jl);
/*      */     
/*  605 */     jl = new JLabel("Hard");
/*  606 */     jl.setForeground(Def.COLOR_LABEL);
/*  607 */     jl.setSize(80, 20);
/*  608 */     jl.setLocation(35, 77);
/*  609 */     jl.setHorizontalAlignment(2);
/*  610 */     diffPanel.add(jl);
/*      */     
/*  612 */     jl = new JLabel("Moderate");
/*  613 */     jl.setForeground(Def.COLOR_LABEL);
/*  614 */     jl.setSize(80, 20);
/*  615 */     jl.setLocation(35, 103);
/*  616 */     jl.setHorizontalAlignment(2);
/*  617 */     diffPanel.add(jl);
/*      */     
/*  619 */     jl = new JLabel("Easy");
/*  620 */     jl.setForeground(Def.COLOR_LABEL);
/*  621 */     jl.setSize(80, 20);
/*  622 */     jl.setLocation(35, 130);
/*  623 */     jl.setHorizontalAlignment(2);
/*  624 */     diffPanel.add(jl);
/*      */     
/*  626 */     jl = new JLabel("Very Easy");
/*  627 */     jl.setForeground(Def.COLOR_LABEL);
/*  628 */     jl.setSize(80, 20);
/*  629 */     jl.setLocation(35, 155);
/*  630 */     jl.setHorizontalAlignment(2);
/*  631 */     diffPanel.add(jl);
/*      */     
/*  633 */     jl = new JLabel("Trivial");
/*  634 */     jl.setForeground(Def.COLOR_LABEL);
/*  635 */     jl.setSize(80, 20);
/*  636 */     jl.setLocation(35, 181);
/*  637 */     jl.setHorizontalAlignment(2);
/*  638 */     diffPanel.add(jl);
/*      */     
/*  640 */     JSlider jsldrDiff = new JSlider(1, 0, 49, Op.getInt(Op.GK.GkDifficulty.ordinal(), Op.gk));
/*  641 */     jsldrDiff.setBackground(Def.COLOR_DIALOGBG);
/*  642 */     jsldrDiff.setSize(20, 180);
/*  643 */     jsldrDiff.setLocation(10, 25);
/*  644 */     diffPanel.add(jsldrDiff);
/*      */     
/*  646 */     JLabel jlAcross = new JLabel("Cells Across:");
/*  647 */     jlAcross.setForeground(Def.COLOR_LABEL);
/*  648 */     jlAcross.setSize(120, 20);
/*  649 */     jlAcross.setLocation(120, 8);
/*  650 */     jlAcross.setHorizontalAlignment(4);
/*  651 */     jdlgGokigen.add(jlAcross);
/*      */     
/*  653 */     JComboBox<Integer> jcbbAcross = new JComboBox<>();
/*  654 */     for (int i = 5; i <= 10; i++)
/*  655 */       jcbbAcross.addItem(Integer.valueOf(i)); 
/*  656 */     jcbbAcross.setSize(60, 20);
/*  657 */     jcbbAcross.setLocation(250, 8);
/*  658 */     jdlgGokigen.add(jcbbAcross);
/*  659 */     jcbbAcross.setBackground(Def.COLOR_BUTTONBG);
/*  660 */     jcbbAcross.setSelectedIndex(Op.getInt(Op.GK.GkAcross.ordinal(), Op.gk) - 5);
/*      */     
/*  662 */     JLabel jlDown = new JLabel("Cells Down:");
/*  663 */     jlDown.setForeground(Def.COLOR_LABEL);
/*  664 */     jlDown.setSize(120, 20);
/*  665 */     jlDown.setLocation(120, 30);
/*  666 */     jlDown.setHorizontalAlignment(4);
/*  667 */     jdlgGokigen.add(jlDown);
/*      */     
/*  669 */     JComboBox<Integer> jcbbDown = new JComboBox<>();
/*  670 */     for (int j = 5; j <= 10; j++)
/*  671 */       jcbbDown.addItem(Integer.valueOf(j)); 
/*  672 */     jcbbDown.setSize(60, 20);
/*  673 */     jcbbDown.setLocation(250, 30);
/*  674 */     jdlgGokigen.add(jcbbDown);
/*  675 */     jcbbDown.setBackground(Def.COLOR_BUTTONBG);
/*  676 */     jcbbDown.setSelectedIndex(Op.getInt(Op.GK.GkDown.ordinal(), Op.gk) - 5);
/*      */     
/*  678 */     HowManyPuzzles hmp = new HowManyPuzzles(jdlgGokigen, 130, 55, howMany, startPuz, Op.getBool(Op.SX.VaryDiff.ordinal(), Op.sx).booleanValue());
/*      */     
/*  680 */     JButton jbOK = Methods.cweButton("OK", 130, 169, 80, 26, null);
/*  681 */     jbOK.addActionListener(e -> {
/*      */           Grid.xSz = paramJComboBox1.getSelectedIndex() + 5;
/*      */           Op.setInt(Op.GK.GkAcross.ordinal(), Grid.xSz, Op.gk);
/*      */           Grid.ySz = paramJComboBox2.getSelectedIndex() + 5;
/*      */           Op.setInt(Op.GK.GkDown.ordinal(), Grid.ySz, Op.gk);
/*      */           howMany = Integer.parseInt(paramHowManyPuzzles.jtfHowMany.getText());
/*      */           startPuz = Integer.parseInt(paramHowManyPuzzles.jtfStartPuz.getText());
/*      */           Op.setBool(Op.SX.VaryDiff.ordinal(), Boolean.valueOf(paramHowManyPuzzles.jcbVaryDiff.isSelected()), Op.sx);
/*      */           Methods.clickedOK = true;
/*      */           paramJDialog.dispose();
/*      */           Methods.closeHelp();
/*      */           Op.setInt(Op.GK.GkDifficulty.ordinal(), paramJSlider.getValue(), Op.gk);
/*      */         });
/*  694 */     jdlgGokigen.add(jbOK);
/*      */     
/*  696 */     JButton jbCancel = Methods.cweButton("Cancel", 130, 204, 80, 26, null);
/*  697 */     jbCancel.addActionListener(e -> {
/*      */           Methods.clickedOK = false;
/*      */           paramJDialog.dispose();
/*      */           Methods.closeHelp();
/*      */         });
/*  702 */     jdlgGokigen.add(jbCancel);
/*      */     
/*  704 */     JButton jbHelp = Methods.cweButton("<html><font size=6 color=BB0000 face=Serif>Help ", 220, 169, 150, 61, new ImageIcon("graphics/help.png"));
/*  705 */     jbHelp.addActionListener(e -> Methods.cweHelp(null, paramJDialog, "Gokigen Options", this.gokigenOptions));
/*      */     
/*  707 */     jdlgGokigen.add(jbHelp);
/*      */     
/*  709 */     jdlgGokigen.getRootPane().setDefaultButton(jbOK);
/*  710 */     Methods.setDialogSize(jdlgGokigen, 380, 240);
/*      */   }
/*      */   
/*      */   static void printOptions(JFrame jf, String type) {
/*  714 */     String[] colorLabel = { "Cell Color", "Circle Color", "Line Color", "Number Color", "Diagonal Color", "Error Color" };
/*  715 */     int[] colorInt = { Op.GK.GkCell.ordinal(), Op.GK.GkCircle.ordinal(), Op.GK.GkLine.ordinal(), Op.GK.GkNumber.ordinal(), Op.GK.GkDiagonal.ordinal(), Op.GK.GkError.ordinal() };
/*  716 */     String[] fontLabel = { "Puzzle Font" };
/*  717 */     int[] fontInt = { Op.GK.GkFont.ordinal() };
/*  718 */     String[] checkLabel = { "PPrint Puzzle with color.", "SPrint Solution with color." };
/*  719 */     int[] checkInt = { Op.GK.GkPuzColor.ordinal(), Op.GK.GkSolColor.ordinal() };
/*  720 */     Methods.stdPrintOptions(jf, "Gokigen " + type, Op.gk, colorLabel, colorInt, fontLabel, fontInt, checkLabel, checkInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void saveGokigen(String gokigenName) {
/*      */     try {
/*  729 */       DataOutputStream dataOut = new DataOutputStream(new FileOutputStream("gokigen/" + gokigenName));
/*  730 */       dataOut.writeInt(Grid.xSz);
/*  731 */       dataOut.writeInt(Grid.ySz);
/*  732 */       dataOut.writeByte(Methods.noReveal);
/*  733 */       dataOut.writeByte(Methods.noErrors);
/*  734 */       for (int i = 0; i < 54; i++)
/*  735 */         dataOut.writeByte(0); 
/*  736 */       for (int j = 0; j < Grid.ySz; j++) {
/*  737 */         for (int k = 0; k < Grid.xSz; k++) {
/*  738 */           dataOut.writeInt(Grid.sol[k][j]);
/*  739 */           dataOut.writeInt(Grid.letter[k][j]);
/*  740 */           dataOut.writeInt(Grid.copy[k][j]);
/*      */         } 
/*  742 */       }  dataOut.writeUTF(Methods.puzzleTitle);
/*  743 */       dataOut.writeUTF(Methods.author);
/*  744 */       dataOut.writeUTF(Methods.copyright);
/*  745 */       dataOut.writeUTF(Methods.puzzleNumber);
/*  746 */       dataOut.writeUTF(Methods.puzzleNotes);
/*  747 */       dataOut.close();
/*      */     }
/*  749 */     catch (IOException exc) {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void loadGokigen(String gokigenName) {
/*      */     
/*  757 */     try { File fl = new File("gokigen/" + gokigenName);
/*  758 */       if (!fl.exists()) {
/*  759 */         fl = new File("gokigen/");
/*  760 */         String[] s = fl.list(); int k;
/*  761 */         for (k = 0; k < s.length && (
/*  762 */           s[k].lastIndexOf(".gokigen") == -1 || s[k].charAt(0) == '.'); k++);
/*      */         
/*  764 */         if (k == s.length) { makeGrid(); return; }
/*  765 */          gokigenName = s[k];
/*  766 */         Op.gk[Op.GK.GkPuz.ordinal()] = gokigenName;
/*      */       } 
/*      */       
/*  769 */       DataInputStream dataIn = new DataInputStream(new FileInputStream("gokigen/" + gokigenName));
/*  770 */       Grid.xSz = dataIn.readInt();
/*  771 */       Grid.ySz = dataIn.readInt();
/*  772 */       Methods.noReveal = dataIn.readByte();
/*  773 */       Methods.noErrors = dataIn.readByte(); int i;
/*  774 */       for (i = 0; i < 54; i++)
/*  775 */         dataIn.readByte(); 
/*  776 */       for (int j = 0; j < Grid.ySz; j++) {
/*  777 */         for (i = 0; i < Grid.xSz; i++) {
/*  778 */           Grid.sol[i][j] = dataIn.readInt();
/*  779 */           Grid.letter[i][j] = dataIn.readInt();
/*  780 */           Grid.copy[i][j] = dataIn.readInt();
/*      */         } 
/*  782 */       }  Methods.puzzleTitle = dataIn.readUTF();
/*  783 */       Methods.author = dataIn.readUTF();
/*  784 */       Methods.copyright = dataIn.readUTF();
/*  785 */       Methods.puzzleNumber = dataIn.readUTF();
/*  786 */       Methods.puzzleNotes = dataIn.readUTF();
/*  787 */       dataIn.close(); }
/*      */     
/*  789 */     catch (IOException exc) { return; }
/*  790 */      Methods.havePuzzle = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void drawGokigen(Graphics2D g2, int[][] puzzleArray) {
/*  798 */     Stroke wideStrokeS = new BasicStroke(Grid.xCell / 25.0F, 2, 1);
/*  799 */     g2.setStroke(wideStrokeS);
/*      */     
/*  801 */     RenderingHints rh = g2.getRenderingHints();
/*  802 */     rh.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
/*  803 */     rh.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
/*  804 */     g2.setRenderingHints(rh);
/*      */     
/*      */     int j;
/*  807 */     for (j = 0; j < Grid.ySz - 1; j++) {
/*  808 */       for (int i = 0; i < Grid.xSz - 1; i++) {
/*  809 */         g2.setColor(new Color(Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.GK.GkCell.ordinal(), Op.gk) : 16777215));
/*  810 */         g2.fillRect(Grid.xOrg + i * Grid.xCell, Grid.yOrg + j * Grid.yCell, Grid.xCell, Grid.yCell);
/*      */       } 
/*      */     } 
/*      */     
/*  814 */     for (j = 0; j < Grid.ySz - 1; j++) {
/*  815 */       for (int i = 0; i < Grid.xSz - 1; i++) {
/*  816 */         g2.setColor(new Color(Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.GK.GkLine.ordinal(), Op.gk) : 0));
/*      */       }
/*      */     } 
/*  819 */     for (j = 0; j < Grid.ySz; j++)
/*  820 */       g2.drawLine(Grid.xOrg, Grid.yOrg + j * Grid.yCell, Grid.xOrg + (Grid.xSz - 1) * Grid.xCell, Grid.yOrg + j * Grid.yCell); 
/*  821 */     for (j = 0; j < Grid.xSz; j++) {
/*  822 */       g2.drawLine(Grid.xOrg + j * Grid.xCell, Grid.yOrg, Grid.xOrg + j * Grid.xCell, Grid.yOrg + (Grid.ySz - 1) * Grid.yCell);
/*      */     }
/*      */     
/*  825 */     g2.setFont(new Font(Op.gk[Op.GK.GkFont.ordinal()], 0, 10 * Grid.yCell / 20));
/*  826 */     FontMetrics fm = g2.getFontMetrics();
/*  827 */     for (j = 0; j < Grid.ySz; j++) {
/*  828 */       for (int i = 0; i < Grid.xSz; i++) {
/*  829 */         if (Grid.copy[i][j] < 5 || (Def.dispCursor.booleanValue() && i == Grid.xCur && j == Grid.yCur)) {
/*  830 */           g2.setColor(new Color(Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.GK.GkCircle.ordinal(), Op.gk) : 16777215));
/*  831 */           g2.fillArc(Grid.xOrg + i * Grid.xCell - Grid.xCell / 3, Grid.yOrg + j * Grid.yCell - Grid.xCell / 3, 2 * Grid.xCell / 3, 2 * Grid.xCell / 3, 0, 360);
/*  832 */           if (Def.dispWithColor.booleanValue()) {
/*  833 */             g2.setColor(new Color((Def.dispCursor.booleanValue() && i == Grid.xCur && j == Grid.yCur) ? 16711680 : Op.getColorInt(Op.GK.GkLine.ordinal(), Op.gk)));
/*      */           } else {
/*  835 */             g2.setColor(Def.COLOR_BLACK);
/*      */           } 
/*  837 */           g2.drawArc(Grid.xOrg + i * Grid.xCell - Grid.xCell / 3, Grid.yOrg + j * Grid.yCell - Grid.xCell / 3, 2 * Grid.xCell / 3, 2 * Grid.xCell / 3, 0, 360); String st;
/*  838 */           int w = fm.stringWidth(st = "" + Grid.copy[i][j]);
/*  839 */           g2.setColor(new Color(Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.GK.GkNumber.ordinal(), Op.gk) : 0));
/*  840 */           if (Grid.copy[i][j] < 5)
/*  841 */             g2.drawString(st, Grid.xOrg + i * Grid.xCell - w / 2, Grid.yOrg + j * Grid.yCell + (fm.getAscent() - fm.getDescent()) / 2); 
/*      */         } 
/*      */       } 
/*  844 */     }  g2.setStroke(new BasicStroke(1.0F));
/*      */   }
/*      */   
/*      */   static void printPuz(Graphics2D g2, int left, int top, int width, int height) {
/*  848 */     loadGokigen(Op.gk[Op.GK.GkPuz.ordinal()]);
/*  849 */     setSizesAndOffsets(left, top, width, height);
/*  850 */     Methods.clearGrid(Grid.sol);
/*  851 */     Def.dispWithColor = Op.getBool(Op.GK.GkPuzColor.ordinal(), Op.gk);
/*  852 */     drawGokigen(g2, Grid.copy);
/*  853 */     Def.dispWithColor = Boolean.valueOf(true);
/*      */   }
/*      */   
/*      */   static void printSol(Graphics2D g2, int left, int top, int width, int height, String solutionPuzzle) {
/*  857 */     loadGokigen(solutionPuzzle);
/*  858 */     setSizesAndOffsets(left, top, width, height);
/*  859 */     Def.dispWithColor = Op.getBool(Op.GK.GkSolColor.ordinal(), Op.gk);
/*  860 */     GokigenSolve.drawGokigen(g2, Grid.letter);
/*  861 */     Def.dispWithColor = Boolean.valueOf(true);
/*  862 */     loadGokigen(Op.gk[Op.GK.GkPuz.ordinal()]);
/*      */   }
/*      */   
/*      */   static void printSolTitle(Graphics2D g2, int left, int top, int width, int height, String solutionPuzzle) {
/*  866 */     loadGokigen(solutionPuzzle);
/*  867 */     Print.outputTextItem(g2, left, top, width, height, "SansSerif", 0, Methods.puzzleTitle);
/*  868 */     loadGokigen(Op.gk[Op.GK.GkPuz.ordinal()]);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static void printSixpackPuz(Graphics2D g2, int left, int top, int dim, int gap, String puzName) {
/*  874 */     FontMetrics fm = g2.getFontMetrics();
/*      */     
/*  876 */     String st = Op.sx[Op.SX.SxGk.ordinal()];
/*  877 */     if (st.length() < 3) st = "GOKIGEN"; 
/*  878 */     int w = fm.stringWidth(st);
/*  879 */     g2.drawString(st, left + (dim - w) / 2, top - gap / 3);
/*  880 */     GokigenSolve.loadGokigen(puzName + ".gokigen");
/*  881 */     setSizesAndOffsets(left, top, dim, dim);
/*  882 */     Methods.clearGrid(Grid.sol);
/*  883 */     drawGokigen(g2, Grid.copy);
/*      */     
/*  885 */     if (Op.sx[Op.SX.SxRuleLang.ordinal()].equals("English")) {
/*  886 */       st = rules;
/*      */     } else {
/*  888 */       st = Op.gk[Op.GK.GkRule1.ordinal() + Op.getInt(Op.SX.SxRuleLangIndex.ordinal(), Op.sx) - 1];
/*  889 */     }  if (Op.getBool(Op.SX.SxInstructions.ordinal(), Op.sx).booleanValue()) {
/*  890 */       Methods.renderText(g2, left, top + dim + dim / 50, dim, dim / 4, "SansSerif", 1, st, 3, 4, true, 0, 0);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static void printSixpackSol(Graphics2D g2, int left, int top, int dim, int gap, String solName) {
/*  896 */     FontMetrics fm = g2.getFontMetrics();
/*      */     
/*  898 */     String st = Op.sx[Op.SX.SxGk.ordinal()];
/*  899 */     if (st.length() < 3) st = "GOKIGEN"; 
/*  900 */     int w = fm.stringWidth(st);
/*  901 */     g2.drawString(st, left + (dim - w) / 2, top - gap / 3);
/*  902 */     loadGokigen(solName + ".gokigen");
/*  903 */     setSizesAndOffsets(left, top, dim, dim);
/*  904 */     GokigenSolve.drawGokigen(g2, Grid.letter);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static void printKDPPuz(Graphics2D g2, int left, int top, int dim, int gap, String puzName) {
/*  910 */     FontMetrics fm = g2.getFontMetrics();
/*      */     
/*  912 */     String st = puzName;
/*  913 */     int w = fm.stringWidth(st);
/*  914 */     g2.drawString(st, left + (dim - w) / 2, top - gap / 3);
/*  915 */     GokigenSolve.loadGokigen(puzName + ".gokigen");
/*  916 */     setSizesAndOffsets(left, top, dim, dim);
/*  917 */     Methods.clearGrid(Grid.sol);
/*  918 */     drawGokigen(g2, Grid.copy);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static void printKDPSol(Graphics2D g2, int left, int top, int dim, int gap, String solName) {
/*  924 */     FontMetrics fm = g2.getFontMetrics();
/*      */     
/*  926 */     String st = solName;
/*  927 */     int w = fm.stringWidth(st);
/*  928 */     g2.drawString(st, left + (dim - w) / 2, top - gap / 3);
/*  929 */     loadGokigen(solName + ".gokigen");
/*  930 */     setSizesAndOffsets(left, top, dim, dim);
/*  931 */     GokigenSolve.drawGokigen(g2, Grid.letter);
/*      */   }
/*      */   
/*      */   void updateGrid(MouseEvent e) {
/*  935 */     int x = e.getX(), y = e.getY();
/*      */     
/*  937 */     if (Def.building == 1)
/*  938 */       return;  x += Grid.xCell / 2; y += Grid.yCell / 2;
/*  939 */     if (x < Grid.xOrg || y < Grid.yOrg)
/*  940 */       return;  x = (x - Grid.xOrg) / Grid.xCell;
/*  941 */     y = (y - Grid.yOrg) / Grid.yCell;
/*  942 */     if (x > Grid.xSz || y > Grid.ySz)
/*      */       return; 
/*  944 */     Grid.xCur = x; Grid.yCur = y;
/*  945 */     restoreFrame();
/*      */   }
/*      */   
/*      */   static void createMaze() {
/*  949 */     int id = 1;
/*  950 */     Random r = new Random();
/*      */     int j;
/*  952 */     for (j = 0; j < Grid.ySz; j++) {
/*  953 */       for (int i = 0; i < Grid.xSz; i++)
/*  954 */         Grid.copy[i][j] = 0; 
/*      */     } 
/*  956 */     for (j = 0; j < Grid.ySz - 1; j++) {
/*  957 */       for (int i = 0; i < Grid.xSz - 1; ) {
/*      */         int thisId, val; while (true) {
/*  959 */           int neighbour1, neighbour2; val = 1 + r.nextInt(2);
/*  960 */           if (val == 1) { neighbour1 = Grid.copy[i + 1][j]; neighbour2 = Grid.copy[i][j + 1]; }
/*  961 */           else { neighbour1 = Grid.copy[i][j]; neighbour2 = Grid.copy[i + 1][j + 1]; }
/*      */           
/*  963 */           if (neighbour1 == 0 && neighbour2 == 0) { int k = id++; break; }
/*  964 */            if (neighbour1 == neighbour2)
/*  965 */             continue;  if (neighbour1 == 0) { int k = neighbour2; break; }
/*  966 */            if (neighbour2 == 0) { int k = neighbour1; break; }
/*      */           
/*  968 */           thisId = neighbour1;
/*  969 */           for (int y = 0; y < Grid.ySz; y++) {
/*  970 */             for (int x = 0; x < Grid.xSz; x++)
/*  971 */             { if (Grid.copy[x][y] == neighbour2)
/*  972 */                 Grid.copy[x][y] = neighbour1;  } 
/*      */           }  break;
/*  974 */         }  Grid.letter[i][j] = val;
/*  975 */         if (val == 1) {
/*  976 */           Grid.copy[i][j + 1] = thisId; Grid.copy[i + 1][j] = thisId;
/*      */         } else {
/*  978 */           Grid.copy[i + 1][j + 1] = thisId; Grid.copy[i][j] = thisId;
/*      */         } 
/*      */         i++;
/*      */       } 
/*      */     } 
/*  983 */     for (j = 0; j < Grid.ySz; j++) {
/*  984 */       for (int i = 0; i < Grid.xSz; i++) {
/*  985 */         Grid.copy[i][j] = 0;
/*      */       }
/*      */     } 
/*  988 */     for (j = 0; j < Grid.ySz - 1; j++) {
/*  989 */       for (int i = 0; i < Grid.xSz - 1; i++) {
/*  990 */         if (Grid.letter[i][j] == 1) {
/*  991 */           Grid.copy[i + 1][j] = Grid.copy[i + 1][j] + 1; Grid.copy[i][j + 1] = Grid.copy[i][j + 1] + 1;
/*      */         } else {
/*  993 */           Grid.copy[i][j] = Grid.copy[i][j] + 1; Grid.copy[i + 1][j + 1] = Grid.copy[i + 1][j + 1] + 1;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   static boolean insertDiagonal(int x, int y, int direction) {
/*      */     int neighbour1, neighbour2, thisID;
/* 1000 */     if (x < 0 || y < 0 || x > Grid.xSz - 2 || y > Grid.ySz - 2) return false; 
/* 1001 */     if (Grid.findHint.booleanValue()) {
/* 1002 */       if (Grid.sol[x][y] == 0) {
/* 1003 */         Grid.hintXb[Grid.hintIndexb] = x;
/* 1004 */         Grid.hintYb[Grid.hintIndexb] = y;
/* 1005 */         Grid.hintIndexb++;
/* 1006 */         return true;
/*      */       } 
/* 1008 */       return false;
/*      */     } 
/*      */     
/* 1011 */     if (Grid.letter[x][y] != 0) return false;
/*      */     
/* 1013 */     if (direction == 1) {
/* 1014 */       Grid.vert[x + 1][y] = Grid.vert[x + 1][y] + 1;
/* 1015 */       Grid.vert[x][y + 1] = Grid.vert[x][y + 1] + 1;
/* 1016 */       Grid.horz[x][y] = Grid.horz[x][y] + 1;
/* 1017 */       Grid.horz[x + 1][y + 1] = Grid.horz[x + 1][y + 1] + 1;
/* 1018 */       neighbour1 = Grid.iSol[x + 1][y];
/* 1019 */       neighbour2 = Grid.iSol[x][y + 1];
/*      */     } else {
/*      */       
/* 1022 */       Grid.vert[x][y] = Grid.vert[x][y] + 1;
/* 1023 */       Grid.vert[x + 1][y + 1] = Grid.vert[x + 1][y + 1] + 1;
/* 1024 */       Grid.horz[x + 1][y] = Grid.horz[x + 1][y] + 1;
/* 1025 */       Grid.horz[x][y + 1] = Grid.horz[x][y + 1] + 1;
/* 1026 */       neighbour1 = Grid.iSol[x][y];
/* 1027 */       neighbour2 = Grid.iSol[x + 1][y + 1];
/*      */     } 
/* 1029 */     Grid.letter[x][y] = direction;
/*      */     
/* 1031 */     if (neighbour1 == 0 && neighbour2 == 0) { thisID = mazeID++; }
/* 1032 */     else { if (neighbour1 == neighbour2) return true; 
/* 1033 */       if (neighbour1 == 0) { thisID = neighbour2; }
/* 1034 */       else if (neighbour2 == 0) { thisID = neighbour1; }
/*      */       else
/* 1036 */       { thisID = neighbour1;
/* 1037 */         for (int j = 0; j < Grid.ySz; j++)
/* 1038 */         { for (int i = 0; i < Grid.xSz; i++)
/* 1039 */           { if (Grid.iSol[i][j] == neighbour2)
/* 1040 */               Grid.iSol[i][j] = neighbour1;  }  }  }
/*      */        }
/* 1042 */      if (direction == 1) {
/* 1043 */       Grid.iSol[x][y + 1] = thisID; Grid.iSol[x + 1][y] = thisID;
/*      */     } else {
/* 1045 */       Grid.iSol[x + 1][y + 1] = thisID; Grid.iSol[x][y] = thisID;
/* 1046 */     }  return true;
/*      */   }
/*      */ 
/*      */   
/*      */   static boolean fourInMiddle() {
/* 1051 */     boolean found = false;
/*      */     
/* 1053 */     for (int j = 1; j < Grid.ySz - 1; j++) {
/* 1054 */       for (int i = 1; i < Grid.xSz - 1; i++) {
/* 1055 */         if (Grid.copy[i][j] == 4) {
/* 1056 */           found |= insertDiagonal(i - 1, j - 1, 2);
/* 1057 */           found |= insertDiagonal(i - 1, j, 1);
/* 1058 */           found |= insertDiagonal(i, j - 1, 1);
/* 1059 */           found |= insertDiagonal(i, j, 2);
/* 1060 */           if (Grid.findHint.booleanValue() && found)
/* 1061 */             return true; 
/*      */         } 
/*      */       } 
/* 1064 */     }  return found;
/*      */   }
/*      */   
/*      */   static boolean zeroInCorner() {
/* 1068 */     if (Grid.copy[0][0] == 0 && insertDiagonal(0, 0, 1) && Grid.findHint.booleanValue()) return true; 
/* 1069 */     if (Grid.copy[0][Grid.ySz - 1] == 0 && insertDiagonal(0, Grid.ySz - 2, 2) && Grid.findHint.booleanValue()) return true; 
/* 1070 */     if (Grid.copy[Grid.xSz - 1][0] == 0 && insertDiagonal(Grid.xSz - 2, 0, 2) && Grid.findHint.booleanValue()) return true; 
/* 1071 */     if (Grid.copy[Grid.xSz - 1][Grid.ySz - 1] == 0 && insertDiagonal(Grid.xSz - 1, Grid.ySz - 1, 1) && Grid.findHint.booleanValue()) return true; 
/* 1072 */     return false;
/*      */   }
/*      */   
/*      */   static boolean oneInCorner() {
/* 1076 */     if (Grid.copy[0][0] == 1 && insertDiagonal(0, 0, 2) && Grid.findHint.booleanValue()) return true; 
/* 1077 */     if (Grid.copy[0][Grid.ySz - 1] == 1 && insertDiagonal(0, Grid.ySz - 2, 1) && Grid.findHint.booleanValue()) return true; 
/* 1078 */     if (Grid.copy[Grid.xSz - 1][0] == 1 && insertDiagonal(Grid.xSz - 2, 0, 1) && Grid.findHint.booleanValue()) return true; 
/* 1079 */     if (Grid.copy[Grid.xSz - 1][Grid.ySz - 1] == 1 && insertDiagonal(Grid.xSz - 2, Grid.ySz - 2, 2) && Grid.findHint.booleanValue()) return true; 
/* 1080 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   static boolean zeroOnEdge() {
/* 1085 */     boolean found = false;
/*      */     int i;
/* 1087 */     for (i = 1; i < Grid.xSz - 1; i++) {
/* 1088 */       if (Grid.copy[i][0] == 0) {
/* 1089 */         found |= insertDiagonal(i, 0, 1);
/* 1090 */         found |= insertDiagonal(i - 1, 0, 2);
/* 1091 */         if (Grid.findHint.booleanValue() && found) return true; 
/*      */       } 
/* 1093 */       if (Grid.copy[i][Grid.ySz - 1] == 0) {
/* 1094 */         found |= insertDiagonal(i, Grid.ySz - 2, 2);
/* 1095 */         found |= insertDiagonal(i - 1, Grid.ySz - 2, 1);
/* 1096 */         if (Grid.findHint.booleanValue() && found) return true;
/*      */       
/*      */       } 
/*      */     } 
/* 1100 */     for (i = 1; i < Grid.ySz - 1; i++) {
/* 1101 */       if (Grid.copy[0][i] == 0) {
/* 1102 */         found |= insertDiagonal(0, i, 1);
/* 1103 */         found |= insertDiagonal(0, i - 1, 2);
/* 1104 */         if (Grid.findHint.booleanValue() && found) return true; 
/*      */       } 
/* 1106 */       if (Grid.copy[Grid.xSz - 1][i] == 0) {
/* 1107 */         found |= insertDiagonal(Grid.xSz - 2, i, 2);
/* 1108 */         found |= insertDiagonal(Grid.xSz - 2, i - 1, 1);
/* 1109 */         if (Grid.findHint.booleanValue() && found) return true; 
/*      */       } 
/*      */     } 
/* 1112 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   static boolean twoOnEdge() {
/* 1117 */     boolean found = false;
/*      */     int i;
/* 1119 */     for (i = 1; i < Grid.xSz - 1; i++) {
/* 1120 */       if (Grid.copy[i][0] == 2) {
/* 1121 */         found |= insertDiagonal(i, 0, 2);
/* 1122 */         found |= insertDiagonal(i - 1, 0, 1);
/* 1123 */         if (Grid.findHint.booleanValue() && found) return true; 
/*      */       } 
/* 1125 */       if (Grid.copy[i][Grid.ySz - 1] == 2) {
/* 1126 */         found |= insertDiagonal(i, Grid.ySz - 2, 1);
/* 1127 */         found |= insertDiagonal(i - 1, Grid.ySz - 2, 2);
/* 1128 */         if (Grid.findHint.booleanValue() && found) return true;
/*      */       
/*      */       } 
/*      */     } 
/* 1132 */     for (i = 1; i < Grid.ySz - 1; i++) {
/* 1133 */       if (Grid.copy[0][i] == 2) {
/* 1134 */         found |= insertDiagonal(0, i, 2);
/* 1135 */         found |= insertDiagonal(0, i - 1, 1);
/* 1136 */         if (Grid.findHint.booleanValue() && found) return true; 
/*      */       } 
/* 1138 */       if (Grid.copy[Grid.xSz - 1][i] == 2) {
/* 1139 */         found |= insertDiagonal(Grid.xSz - 2, i, 1);
/* 1140 */         found |= insertDiagonal(Grid.xSz - 2, i - 1, 2);
/* 1141 */         if (Grid.findHint.booleanValue() && found) return true; 
/*      */       } 
/*      */     } 
/* 1144 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   static boolean onesInLine() {
/* 1149 */     boolean found = false;
/*      */     int j;
/* 1151 */     for (j = 1; j < Grid.ySz - 1; j++) {
/* 1152 */       for (int k = 0; k < Grid.xSz - 1; k++) {
/* 1153 */         if (Grid.copy[k][j] == 1)
/* 1154 */           for (int m = k + 1; m < Grid.xSz; m++) {
/* 1155 */             if (Grid.copy[m][j] == 1)
/* 1156 */             { found |= insertDiagonal(k - 1, j - 1, 1);
/* 1157 */               found |= insertDiagonal(k - 1, j, 2);
/* 1158 */               found |= insertDiagonal(m, j - 1, 2);
/* 1159 */               found |= insertDiagonal(m, j, 1);
/* 1160 */               if (Grid.findHint.booleanValue() && found) return true;
/*      */                }
/* 1162 */             else if (Grid.copy[m][j] != 2) { break; }
/*      */           
/*      */           }  
/*      */       } 
/* 1166 */     }  for (int i = 1; i < Grid.xSz - 1; i++) {
/* 1167 */       for (j = 0; j < Grid.ySz - 1; j++) {
/* 1168 */         if (Grid.copy[i][j] == 1)
/* 1169 */           for (int k = j + 1; k < Grid.ySz; k++) {
/* 1170 */             if (Grid.copy[i][k] == 1)
/* 1171 */             { found |= insertDiagonal(i - 1, j - 1, 1);
/* 1172 */               found |= insertDiagonal(i, j - 1, 2);
/* 1173 */               found |= insertDiagonal(i - 1, k, 2);
/* 1174 */               found |= insertDiagonal(i, k, 1);
/* 1175 */               if (Grid.findHint.booleanValue() && found) return true;
/*      */                }
/* 1177 */             else if (Grid.copy[i][k] != 2) { break; } 
/*      */           }  
/*      */       } 
/* 1180 */     }  return false;
/*      */   }
/*      */ 
/*      */   
/*      */   static boolean threesInLine() {
/* 1185 */     boolean found = false;
/*      */     int j;
/* 1187 */     for (j = 1; j < Grid.ySz - 1; j++) {
/* 1188 */       for (int i = 0; i < Grid.xSz - 1; i++) {
/* 1189 */         if (Grid.copy[i][j] == 3)
/* 1190 */           for (int k = i + 1; k < Grid.xSz; k++) {
/* 1191 */             if (Grid.copy[k][j] == 3)
/* 1192 */             { found |= insertDiagonal(i - 1, j - 1, 2);
/* 1193 */               found |= insertDiagonal(i - 1, j, 1);
/* 1194 */               found |= insertDiagonal(k, j - 1, 1);
/* 1195 */               found |= insertDiagonal(k, j, 2);
/* 1196 */               if (Grid.findHint.booleanValue() && found) return true;
/*      */                }
/* 1198 */             else if (Grid.copy[k][j] != 2) { break; } 
/*      */           }  
/*      */       } 
/* 1201 */     }  for (j = 0; j < Grid.ySz - 1; j++) {
/* 1202 */       for (int i = 1; i < Grid.xSz - 1; i++) {
/* 1203 */         if (Grid.copy[i][j] == 3)
/* 1204 */           for (int k = j + 1; k < Grid.ySz; k++) {
/* 1205 */             if (Grid.copy[i][k] == 3)
/* 1206 */             { found |= insertDiagonal(i - 1, j - 1, 2);
/* 1207 */               found |= insertDiagonal(i, j - 1, 1);
/* 1208 */               found |= insertDiagonal(i - 1, k, 1);
/* 1209 */               found |= insertDiagonal(i, k, 2);
/* 1210 */               if (Grid.findHint.booleanValue() && found) return true;
/*      */                }
/* 1212 */             else if (Grid.copy[i][k] != 2) { break; } 
/*      */           }  
/*      */       } 
/* 1215 */     }  return false;
/*      */   }
/*      */ 
/*      */   
/*      */   static boolean twoOnes() {
/*      */     int j;
/* 1221 */     for (j = 1; j < Grid.ySz - 2; j++) {
/* 1222 */       for (int i = 1; i < Grid.xSz - 2; i++) {
/* 1223 */         if (Grid.copy[i][j] == 1 && Grid.copy[i + 1][j + 1] == 1 && 
/* 1224 */           insertDiagonal(i, j, 1) && 
/* 1225 */           Grid.findHint.booleanValue()) return true; 
/*      */       } 
/* 1227 */     }  for (j = 1; j < Grid.ySz - 2; j++) {
/* 1228 */       for (int i = 2; i < Grid.xSz - 1; i++)
/* 1229 */       { if (Grid.copy[i][j] == 1 && Grid.copy[i - 1][j + 1] == 1 && 
/* 1230 */           insertDiagonal(i - 1, j, 2) && 
/* 1231 */           Grid.findHint.booleanValue()) return true;  } 
/* 1232 */     }  return false;
/*      */   }
/*      */ 
/*      */   
/*      */   static int forcedDiagonals() {
/* 1237 */     boolean found = false;
/*      */     int j;
/* 1239 */     for (j = 0; j < Grid.ySz; j++) {
/* 1240 */       for (int i = 0; i < Grid.xSz; i++) {
/* 1241 */         int order = 4;
/* 1242 */         if (i == 0 || i == Grid.xSz - 1) order /= 2; 
/* 1243 */         if (j == 0 || j == Grid.ySz - 1) order /= 2; 
/* 1244 */         if (Grid.horz[i][j] == order - Grid.copy[i][j]) {
/* 1245 */           found |= insertDiagonal(i - 1, j - 1, 2);
/* 1246 */           found |= insertDiagonal(i - 1, j, 1);
/* 1247 */           found |= insertDiagonal(i, j - 1, 1);
/* 1248 */           found |= insertDiagonal(i, j, 2);
/* 1249 */           if (Grid.findHint.booleanValue() && found) return 1; 
/*      */         } 
/*      */       } 
/*      */     } 
/* 1253 */     for (j = 0; j < Grid.ySz; j++) {
/* 1254 */       for (int i = 0; i < Grid.xSz; i++) {
/* 1255 */         if (Grid.vert[i][j] == Grid.copy[i][j])
/* 1256 */         { found |= insertDiagonal(i - 1, j - 1, 1);
/* 1257 */           found |= insertDiagonal(i - 1, j, 2);
/* 1258 */           found |= insertDiagonal(i, j - 1, 2);
/* 1259 */           found |= insertDiagonal(i, j, 1);
/* 1260 */           if (Grid.findHint.booleanValue() && found) return 2;  } 
/*      */       } 
/* 1262 */     }  return found ? 1 : 0;
/*      */   }
/*      */ 
/*      */   
/*      */   static boolean avoidLoops() {
/* 1267 */     boolean found = false;
/*      */     
/* 1269 */     for (int j = 0; j < Grid.ySz - 1; j++) {
/* 1270 */       for (int i = 0; i < Grid.xSz - 1; i++) {
/* 1271 */         if ((!Grid.findHint.booleanValue() && Grid.letter[i][j] == 0) || (Grid.findHint.booleanValue() && Grid.sol[i][j] == 0))
/* 1272 */         { if (Grid.iSol[i + 1][j] == Grid.iSol[i][j + 1] && Grid.iSol[i + 1][j] != 0) {
/* 1273 */             found = insertDiagonal(i, j, 2);
/* 1274 */           } else if (Grid.iSol[i][j] == Grid.iSol[i + 1][j + 1] && Grid.iSol[i][j] != 0) {
/* 1275 */             found = insertDiagonal(i, j, 1);
/* 1276 */           }  if (Grid.findHint.booleanValue() && found) return true;  } 
/*      */       } 
/* 1278 */     }  return found;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static void countDiagonals() {
/* 1284 */     Methods.clearGrid(Grid.vert);
/* 1285 */     Methods.clearGrid(Grid.horz);
/* 1286 */     for (int y = 0; y < Grid.ySz; y++) {
/* 1287 */       for (int x = 0; x < Grid.xSz; x++) {
/* 1288 */         if (Grid.copy[x][y] > 0) {
/* 1289 */           if (x > 0 && y > 0) {
/* 1290 */             if (Grid.sol[x - 1][y - 1] == 1) Grid.horz[x][y] = Grid.horz[x][y] + 1; 
/* 1291 */             if (Grid.sol[x - 1][y - 1] == 2) Grid.vert[x][y] = Grid.vert[x][y] + 1; 
/*      */           } 
/* 1293 */           if (y > 0) {
/* 1294 */             if (Grid.sol[x][y - 1] == 2) Grid.horz[x][y] = Grid.horz[x][y] + 1; 
/* 1295 */             if (Grid.sol[x][y - 1] == 1) Grid.vert[x][y] = Grid.vert[x][y] + 1; 
/*      */           } 
/* 1297 */           if (x > 0) {
/* 1298 */             if (Grid.sol[x - 1][y] == 2) Grid.horz[x][y] = Grid.horz[x][y] + 1; 
/* 1299 */             if (Grid.sol[x - 1][y] == 1) Grid.vert[x][y] = Grid.vert[x][y] + 1; 
/*      */           } 
/* 1301 */           if (Grid.sol[x][y] == 1) Grid.horz[x][y] = Grid.horz[x][y] + 1; 
/* 1302 */           if (Grid.sol[x][y] == 2) Grid.vert[x][y] = Grid.vert[x][y] + 1; 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   static void identifyLoops() {
/* 1309 */     Methods.clearGrid(Grid.iSol);
/* 1310 */     mazeID = 1;
/*      */     
/* 1312 */     for (int y = 0; y < Grid.ySz; y++) {
/* 1313 */       for (int x = 0; x < Grid.xSz; x++) {
/* 1314 */         if (Grid.sol[x][y] != 0) {
/* 1315 */           int neighbour1, neighbour2, thisID, direction = Grid.sol[x][y];
/* 1316 */           if (direction == 1) {
/* 1317 */             neighbour1 = Grid.iSol[x + 1][y];
/* 1318 */             neighbour2 = Grid.iSol[x][y + 1];
/*      */           } else {
/*      */             
/* 1321 */             neighbour1 = Grid.iSol[x][y];
/* 1322 */             neighbour2 = Grid.iSol[x + 1][y + 1];
/*      */           } 
/*      */           
/* 1325 */           if (neighbour1 == 0 && neighbour2 == 0) { thisID = mazeID++; }
/* 1326 */           else if (neighbour1 == 0) { thisID = neighbour2; }
/* 1327 */           else if (neighbour2 == 0) { thisID = neighbour1; }
/*      */           else
/* 1329 */           { thisID = neighbour1;
/* 1330 */             for (int j = 0; j < Grid.ySz; j++) {
/* 1331 */               for (int i = 0; i < Grid.xSz; i++)
/* 1332 */               { if (Grid.iSol[i][j] == neighbour2)
/* 1333 */                   Grid.iSol[i][j] = neighbour1;  } 
/*      */             }  }
/* 1335 */            if (direction == 1) {
/* 1336 */             Grid.iSol[x][y + 1] = thisID; Grid.iSol[x + 1][y] = thisID;
/*      */           } else {
/* 1338 */             Grid.iSol[x + 1][y + 1] = thisID; Grid.iSol[x][y] = thisID;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   static int nextHint() {
/* 1345 */     for (int j = 0; j < Grid.ySz - 1; j++) {
/* 1346 */       for (int i = 0; i < Grid.xSz - 1; i++) {
/* 1347 */         if (Grid.sol[i][j] != 0 && Grid.sol[i][j] != Grid.letter[i][j])
/* 1348 */         { Grid.hintXb[Grid.hintIndexb] = i;
/* 1349 */           Grid.hintYb[Grid.hintIndexb] = j;
/* 1350 */           Grid.hintIndexb++; } 
/*      */       } 
/* 1352 */     }  if (Grid.hintIndexb > 0) {
/* 1353 */       return -1;
/*      */     }
/* 1355 */     if (fourInMiddle()) return 1; 
/* 1356 */     if (zeroInCorner()) return 2; 
/* 1357 */     if (oneInCorner()) return 3; 
/* 1358 */     if (zeroOnEdge()) return 4; 
/* 1359 */     if (twoOnEdge()) return 5; 
/* 1360 */     if (onesInLine()) return 6; 
/* 1361 */     if (threesInLine()) return 7; 
/* 1362 */     if (twoOnes()) return 8; 
/* 1363 */     countDiagonals();
/* 1364 */     if (forcedDiagonals() == 1) return 9; 
/* 1365 */     if (forcedDiagonals() == 2) return 10; 
/* 1366 */     identifyLoops();
/* 1367 */     if (avoidLoops()) return 11; 
/* 1368 */     return 12;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean solveGokigen() {
/* 1378 */     mazeID = 1; int j;
/* 1379 */     for (j = 0; j < Grid.ySz; j++) {
/* 1380 */       for (int i = 0; i < Grid.xSz; i++) {
/* 1381 */         Grid.iSol[i][j] = 0; Grid.horz[i][j] = 0; Grid.vert[i][j] = 0; Grid.letter[i][j] = 0;
/*      */       } 
/* 1383 */     }  zeroInCorner();
/* 1384 */     oneInCorner();
/* 1385 */     zeroOnEdge();
/* 1386 */     twoOnEdge();
/* 1387 */     onesInLine();
/* 1388 */     threesInLine();
/* 1389 */     fourInMiddle();
/* 1390 */     twoOnes();
/* 1391 */     while (avoidLoops() || forcedDiagonals() == 1);
/*      */     
/* 1393 */     for (j = 0; j < Grid.ySz - 1; j++) {
/* 1394 */       for (int i = 0; i < Grid.xSz - 1; i++) {
/* 1395 */         if (Grid.letter[i][j] == 0)
/* 1396 */           return false; 
/*      */       } 
/* 1398 */     }  return true;
/*      */   }
/*      */   
/*      */   static boolean validGokigen() {
/*      */     int j;
/*      */     int x;
/* 1404 */     for (x = j = 0; j < Grid.ySz; j++) {
/* 1405 */       for (int i = 0; i < Grid.xSz; i++) {
/* 1406 */         if (Grid.copy[i][j] < 5 && Grid.copy[i][j] != Grid.vert[i][j])
/* 1407 */           return false; 
/* 1408 */         Grid.vert[i][j] = x++;
/*      */       } 
/*      */     } 
/* 1411 */     for (j = 0; j < Grid.ySz - 1; j++) {
/* 1412 */       for (int i = 0; i < Grid.xSz; i++) {
/* 1413 */         x = Grid.vert[i][j];
/* 1414 */         if (i > 0 && Grid.letter[i - 1][j] == 1) {
/* 1415 */           int y = Grid.vert[i - 1][j + 1];
/* 1416 */           if (x == y) {
/* 1417 */             return false;
/*      */           }
/* 1419 */           for (int b = 0; b < Grid.ySz; b++) {
/* 1420 */             for (int a = 0; a < Grid.xSz; a++)
/* 1421 */             { if (Grid.vert[a][b] == y)
/* 1422 */                 Grid.vert[a][b] = x;  } 
/*      */           } 
/* 1424 */         }  if (i < Grid.xSz - 1 && Grid.letter[i][j] == 2) {
/* 1425 */           int y = Grid.vert[i + 1][j + 1];
/* 1426 */           if (x == y)
/* 1427 */             return false; 
/* 1428 */           for (int b = 0; b < Grid.ySz; b++) {
/* 1429 */             for (int a = 0; a < Grid.xSz; a++)
/* 1430 */             { if (Grid.vert[a][b] == y)
/* 1431 */                 Grid.vert[a][b] = x;  } 
/*      */           } 
/*      */         } 
/*      */       } 
/* 1435 */     }  return true;
/*      */   }
/*      */   
/*      */   static int depopulate() {
/* 1439 */     int[] vec = new int[2500];
/* 1440 */     Random r = new Random();
/*      */     
/*      */     int j;
/* 1443 */     for (j = 0; j < Grid.ySz; j++) {
/* 1444 */       for (int i = 0; i < Grid.xSz; i++) {
/* 1445 */         if (Op.getInt(Op.GK.GkDifficulty.ordinal(), Op.gk) > 8 && 
/* 1446 */           Grid.copy[i][j] == 4)
/* 1447 */           Grid.copy[i][j] = 5; 
/* 1448 */         if (Op.getInt(Op.GK.GkDifficulty.ordinal(), Op.gk) > 16 && ((
/* 1449 */           i == 0 && (j == 0 || j == Grid.ySz - 1)) || (i == Grid.xSz - 1 && (j == 0 || j == Grid.ySz - 1))) && 
/* 1450 */           Grid.copy[i][j] == 0)
/* 1451 */           Grid.copy[i][j] = 5; 
/* 1452 */         if (Op.getInt(Op.GK.GkDifficulty.ordinal(), Op.gk) > 24 && ((
/* 1453 */           i == 0 && (j == 0 || j == Grid.ySz - 1)) || (i == Grid.xSz - 1 && (j == 0 || j == Grid.ySz - 1))) && 
/* 1454 */           Grid.copy[i][j] == 1)
/* 1455 */           Grid.copy[i][j] = 5; 
/* 1456 */         if (Op.getInt(Op.GK.GkDifficulty.ordinal(), Op.gk) > 32 && (
/* 1457 */           i == 0 || j == 0 || i == Grid.xSz - 1 || j == Grid.ySz - 1) && 
/* 1458 */           Grid.copy[i][j] == 0)
/* 1459 */           Grid.copy[i][j] = 5; 
/* 1460 */         if (Op.getInt(Op.GK.GkDifficulty.ordinal(), Op.gk) > 40 && (
/* 1461 */           i == 0 || j == 0 || i == Grid.xSz - 1 || j == Grid.ySz - 1) && 
/* 1462 */           Grid.copy[i][j] == 2)
/* 1463 */           Grid.copy[i][j] = 5; 
/*      */       } 
/*      */     } 
/* 1466 */     int max = Grid.xSz * Grid.ySz; int k;
/* 1467 */     for (k = 0; k < max; k++)
/* 1468 */       vec[k] = k; 
/* 1469 */     for (k = 0; k < max; k++) {
/* 1470 */       int i = r.nextInt(max);
/* 1471 */       j = vec[k]; vec[k] = vec[i]; vec[i] = j;
/*      */     } 
/*      */     
/* 1474 */     for (k = 0; k < max; k++) {
/* 1475 */       int i = vec[k] % Grid.xSz; j = vec[k] / Grid.xSz;
/* 1476 */       if (Grid.copy[i][j] != 5) {
/* 1477 */         int save = Grid.copy[i][j];
/* 1478 */         Grid.copy[i][j] = 5;
/* 1479 */         if (!solveGokigen())
/* 1480 */           Grid.copy[i][j] = save; 
/*      */       } 
/*      */     } 
/*      */     int count;
/* 1484 */     for (count = j = 0; j < Grid.ySz; j++) {
/* 1485 */       for (int i = 0; i < Grid.xSz; i++)
/* 1486 */       { if (Grid.copy[i][j] < 5)
/* 1487 */           count++;  } 
/* 1488 */     }  return count;
/*      */   }
/*      */   
/*      */   private void multiBuild() {
/* 1492 */     String title = Methods.puzzleTitle;
/* 1493 */     int[] diffDef = { 7, 14, 21, 28, 35, 42, 49 };
/* 1494 */     int[] sizeDef = { 5, 6, 6, 7, 8, 9, 10 };
/*      */ 
/*      */     
/* 1497 */     int saveDiff = Op.getInt(Op.GK.GkDifficulty.ordinal(), Op.gk);
/* 1498 */     int saveAcross = Op.getInt(Op.GK.GkAcross.ordinal(), Op.gk);
/* 1499 */     int saveDown = Op.getInt(Op.GK.GkDown.ordinal(), Op.gk);
/*      */     
/* 1501 */     SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
/* 1502 */     Calendar c = Calendar.getInstance();
/*      */     
/* 1504 */     for (hmCount = 1; hmCount <= howMany; hmCount++) {
/* 1505 */       if (startPuz > 9999999) { try {
/* 1506 */           c.setTime(sdf.parse("" + startPuz));
/* 1507 */         } catch (ParseException ex) {}
/* 1508 */         startPuz = Integer.parseInt(sdf.format(c.getTime())); }
/*      */       
/* 1510 */       Methods.puzzleTitle = "GOKIGEN Puzzle : " + startPuz;
/* 1511 */       if (Op.getBool(Op.SX.VaryDiff.ordinal(), Op.sx).booleanValue()) {
/* 1512 */         Op.setInt(Op.GK.GkDifficulty.ordinal(), diffDef[(startPuz - 1) % 7], Op.gk);
/* 1513 */         Op.setInt(Op.GK.GkAcross.ordinal(), sizeDef[(startPuz - 1) % 7], Op.gk);
/* 1514 */         Op.setInt(Op.GK.GkDown.ordinal(), sizeDef[(startPuz - 1) % 7], Op.gk);
/*      */       } 
/*      */       
/* 1517 */       Methods.buildProgress(jfGokigen, Op.gk[Op.GK.GkPuz
/* 1518 */             .ordinal()] = "" + startPuz + ".gokigen");
/* 1519 */       buildGokigen();
/* 1520 */       restoreFrame();
/* 1521 */       Wait.shortWait(100);
/* 1522 */       if (Def.building == 2)
/*      */         return; 
/* 1524 */       startPuz++;
/*      */     } 
/* 1526 */     howMany = 1;
/* 1527 */     Methods.puzzleTitle = title;
/* 1528 */     Op.setInt(Op.GK.GkDifficulty.ordinal(), saveDiff, Op.gk);
/* 1529 */     Op.setInt(Op.GK.GkAcross.ordinal(), saveAcross, Op.gk);
/* 1530 */     Op.setInt(Op.GK.GkDown.ordinal(), saveDown, Op.gk);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean buildGokigen() {
/* 1535 */     for (int loop = 0;; loop++) {
/* 1536 */       makeGrid();
/* 1537 */       createMaze();
/* 1538 */       if (depopulate() < 40 * Grid.xSz * Grid.ySz / 100)
/*      */         break; 
/* 1540 */       if (Def.building == 2) {
/* 1541 */         return true;
/*      */       }
/* 1543 */       if (howMany == 1 && loop % 100 == 0) {
/* 1544 */         restoreFrame();
/* 1545 */         Methods.buildProgress(jfGokigen, Op.gk[Op.GK.GkPuz.ordinal()]);
/*      */       } 
/*      */     } 
/* 1548 */     solveGokigen();
/* 1549 */     saveGokigen(Op.gk[Op.GK.GkPuz.ordinal()]);
/*      */     
/* 1551 */     return true;
/*      */   }
/*      */   void handleKeyPressed(KeyEvent e) {
/*      */     int ch;
/* 1555 */     if (Def.building == 1)
/* 1556 */       return;  if (e.isAltDown())
/* 1557 */       return;  switch (e.getKeyCode()) { case 38:
/* 1558 */         if (Grid.yCur > 0) Grid.yCur--;  break;
/* 1559 */       case 40: if (Grid.yCur < Grid.ySz - 1) Grid.yCur++;  break;
/* 1560 */       case 37: if (Grid.xCur > 0) Grid.xCur--;  break;
/* 1561 */       case 39: if (Grid.xCur < Grid.xSz - 1) Grid.xCur++;  break;
/* 1562 */       case 36: Grid.xCur = 0; break;
/* 1563 */       case 35: Grid.xCur = Grid.xSz - 1; break;
/* 1564 */       case 33: Grid.yCur = 0; break;
/* 1565 */       case 34: Grid.yCur = Grid.ySz - 1; break;
/*      */       default:
/* 1567 */         ch = e.getKeyChar();
/* 1568 */         if (ch >= 48 && ch <= 52)
/* 1569 */           Grid.copy[Grid.xCur][Grid.yCur] = ch - 48; 
/* 1570 */         if (ch == 32)
/* 1571 */           Grid.copy[Grid.xCur][Grid.yCur] = 5; 
/*      */         break; }
/*      */     
/* 1574 */     restoreFrame();
/*      */   }
/*      */ }


/* Location:              C:\Users\mustapha\Desktop\Crossword-Express.jar!\crosswordexpress\GokigenBuild.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */