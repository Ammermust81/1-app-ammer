/*      */ package crosswordexpress;
/*      */ import java.awt.FontMetrics;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.Toolkit;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.io.DataInputStream;
/*      */ import java.io.DataOutputStream;
/*      */ import java.io.File;
/*      */ import javax.swing.JButton;
/*      */ import javax.swing.JComboBox;
/*      */ import javax.swing.JDialog;
/*      */ import javax.swing.JFileChooser;
/*      */ import javax.swing.JLabel;
/*      */ import javax.swing.JMenuItem;
/*      */ import javax.swing.KeyStroke;
/*      */ 
/*      */ public final class AkariBuild {
/*      */   static JFrame jfAkari;
/*      */   static JMenuBar menuBar;
/*      */   JMenu menu;
/*      */   JMenu submenu;
/*      */   JMenuItem menuItem;
/*      */   JMenuItem buildMenuItem;
/*   25 */   int howMany = 1; static JPanel pp; static int panelW; static int panelH; static JLabel jl1; static JLabel jl2; Timer myTimer; Thread thread; int startPuz = Integer.parseInt((new SimpleDateFormat("yyyyMMdd")).format(new Date())); int hmCount; static boolean sixpack;
/*      */   static boolean badSolution;
/*   27 */   int[] depopulateList = new int[50]; File[] grids; static final int LAMP = 1; static final int NOLAMP = 2;
/*      */   static final int LIT = 4;
/*      */   static final int PATTERN = 8;
/*   30 */   static String rules = "A lamp placed in a white cell will light up all cells in its row and column up to the edge of the puzzle, or the first black cell. All cells must be lit, but no lamp may light up another lamp. Numbers in black cells tell you how many lamps touch that cell, vertically and horizontally.";
/*      */ 
/*      */ 
/*      */   
/*   34 */   String akariHelp = "<div>An AKARI puzzle consists of a square grid in which some of the cells are painted black in the same way as in a crossword puzzle. Think of it as the floor plan of a building in which the black cells represent pillars or wall segments. The remainder of the cells represent floor area which must be illuminated by lamps placed in certain of the cells. A lamp illuminates all cells in its row and column up to the edge of the puzzle, or up to an intervening black cell. No lamp is allowed to illuminate any other lamp, and the numbers in some of the black cells indicate the number of lamps which are adjacent to that cell both horizontally and vertically. Each puzzle has a unique solution which does not require any guesswork to achieve. Puzzles can be built either manually or automatically in sizes up to 16x16.<p/>An AKARI puzzle is constructed on a crossword grid just like a standard crossword. A number of grids suitable for building AKARI puzzles are distributed with this program, and you also have the option of making an unlimited number of new ones using the <b>Grid Maintenance</b> function. Be aware though that it is easy to make a grid on which Crossword Express will find it impossible to build a puzzle so it is advisable to try a few test builds on any new grid that you do make.</div><br/><span class='m'>Menu Functions</span><ul><li/><span class='s'>File Menu</span><br/><ul><li/><span>Load a Puzzle</span><br/>Use this option to choose your puzzle from the pool of AKARI puzzles currently available on your computer.<p/><li/><span>Save</span><br/>If you are part-way through manually entering an Akari puzzle, and need to interrupt the process, this option will allow you to save the work you have done so far. You will be able to return to it whenever you please.<p/><li/><span>SaveAs</span><br/>This option allows you to make an exact copy of the current puzzle using a different file name. The copy will be saved in the <b>akari</b> folder along with all of the Akari puzzles you have made. Alternatively, if you don't enter a new name for the puzzle, you can change the Puzzle Description, or any of the other descriptive items without changing the puzzle name.<p/><li/><span class='menusub'>Quit Construction</span><br/>Returns you to the Crossword Express opening screen.<p/></ul><li/><span class='s'>Build Menu</span><ul><li/><span>Start a New Puzzle</span><br/>This option presents you with a dialog into which you can enter a file name for your new puzzle. You can also enter several other pieces of information such as a <b>Puzzle Title, Author</b> and <b>Copyright</b> information.<p/><li/><span>Build Options</span><br/>Use this option to access a Build Options dialog where you can set some rules to be followed by the puzzle building function as it builds your puzzle.<p/><li/><span>Select a Grid</span><br/>Use this option to select the grid which is to be used for the puzzle. You will probably need to make use of the <b>Grid Maintenance</b> function to create suitable grids, and to prevent confusion, it may be a good idea to adopt a naming convention for your AKARI grids. For example you could begin their names with <b>A_</b> or something similar. If you are intending to make a series of puzzles using the Multi-Make function, then you can select a number of grids...simply hold down the Control key (Command Key on Mac) as you click-select them with the mouse. Subsequently when you make the puzzles, the grids you have selected will be used sequentially as the puzzles are built.<p/><li/><span>Start Building / Stop Building</span><br/>Construction of the puzzle will commence when you select the <b>Start Building</b> option. If puzzle building is successful you will receive a message containing the name of the puzzle file, and the location where it was saved. If it becomes necessary, you can interrupt the building process by selecting this option a second time. Note that during the construction phase, the text of this option is changed to <b>Stop Building</b><p/><li/><span>Test Puzzle Validity</span><br/>Manual constructiom of an Akari puzzle is not recommended. You should only attempt this if you have a valid puzzle (possibly one published in a magazine) which you would like to enter into the program. In such a case you will need to construct a new grid which matches the one you are aiming to create. When you have done this, and selected the grid into the Construction screen, simply move the cursor cell around the puzzle, and type the required values into the pattern cells. Selecting the <b>Test Puzzle Validity</b> option will check the validity of the puzzle. If it has a unique solution, it will be saved, and you will be advised of this. If not, you will receive the message that the puzzle is not valid.<p/></ul><li/><span class='s'>View Menu</span><ul><li/><span>Display Options</span><br/>This leads you to a dialog box in which you can change the colors of various elements within the puzzle, and control the fonts which will be used for the puzzle's text components. You can also decide if printing of the puzzle will be done in black and white or in color.<p/></ul><li/><span class='s'>Export Menu</span><br/><ul><li/><span>Export Akari Web-App</span><br/>This function allows you to export a Web Application Program which you can then upload to your own web site to provide a fully interactive Akari puzzle for the entertainment of visitors to your site. For a full description of the facilities provided by this Web-App, please refer to the Help available at <b>Help / Akari Web Application</b> on the menu bar of the <b>Build Akari</b> window of this program.<p/><li/><span>Launch a Demo Akari Web App</span><br/>Take a first look at the Akari Web App. See what it could do to enhance your web site.<p/><li/><span>Print a Domino KDP puzzle book.</span><br/>The letters KDP stand for <b>Kindle Direct Publishing</b>. This is a free publishing service operared by Amazon, in which they handle all matters related to printing, advertising and sales of books created by members of the public. A portion of the proceeds are retained by Amazon while the remainder is paid to the author. Fifteen of the Puzzles created by Crossword Express can be printed into PDF format files ready for publication by Amazon. When you select this option, you will be presented with a dialog which allows you to control the process. Please study the Help offered by this dialog before attempting to make use of it.<p/></ul><li/><span class='s'>Tasks Menu</span><ul><li/><span>Print this Puzzle</span><br/>This will take you to a custom print screen where you can control the details involved with printing your puzzle.<p/><li/><span>Solve this Puzzle</span><br/>This will take you to a Solve screen which provides a fully interactive environment for solving the puzzle.<p/><li/><span>Delete this Puzzle</span><br/>Use this option to eliminate unwanted AKARI puzzles from your file system.<p/></ul><li/><span class='s'>Help Menu</span><ul><li/><span>Akari Help</span><br/>Displays the Help screen which you are now reading.<p/></ul></ul></body>";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  130 */   String akariOptionsHelp = "<div>The following options are available for adjustment before you start to build your Akari puzzle:-</div><br/><ul><li/><b>Difficulty: </b>A combo box allows you to choose between difficulty settings of <b>Easy, Moderate</b> and <b>Hard</>.<p/><li/>If you want to make a number of puzzles all having the same dimensions, simply type a number into the <b>How many puzzles</b> input field. When you issue the Make command, Crossword Express will make that number of puzzles. The puzzle names will be numbers which represent a date in <b>yyyymmdd</b> format. The default value presented by Crossword Express is always the current date, but you can change this to any date that suits your needs. As the series of puzzles is created, CWE will automatically step on to the next date in the sequence, taking into account such factors as the varying number of days in the months, and of course leap years. Virtually any number of puzzles can be made in a single operation using this feature.<p/><li/><b>HOWEVER:</b> If you prefer a simpler numbering scheme for your puzzles, you can enter any number of 7 digits or less to be used for your first puzzle, and Crossword Express will number the remainder of the puzzles sequentially starting with your number.<p/><li/>If you do choose to make multiple puzzles, then by default, Crossword Express will change the difficulty of the resulting puzzles over a cycle of seven puzzles. This would be useful for a daily newspaper so that the week could start with a very easy puzzle, with quite difficult puzzles reserved for the weekend. If you don't want this feature, clearing the <b>Vary Difficulty on 7 day cycle</b> check-box will disable it.</ul></body>";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  150 */   static String webAppHelp = "<div><span>Step 1: Export</span><br/>To export the Web Application, select the function <b>Export / Export Puzzle as Web App</b> from the menu bar of the <b>Build Akari</b> window of this program. The necessary files will be exported into a folder called <b>Web-App</b> located on your computer's Desktop. Please note that you will need a working Internet connection for this function to operate correctly. The exported files are as follows:-<ul><li><span>akari.js</span> This is the file which contains all of the Java Script which implements the interactive features of the Web App.<li><span>akari.html</span> This file creates the URL address used to access the Web App. Its main purpose is to start the operation of the akari.js Java Script file. If you open akari.html with a simple text editor, you will find two appearances of the following text fragment ... <b>&lt;!-- Your HTML code here. --></b>. If you are an experienced HTML coder and would like to add some content of your own to the Web App window, simply replace these fragments with your own HTML code.</ul><span>Step 2: Familiarization</span><br/>Having exported the App, you can give it a first run by starting your web browser, and using it to open the <b>akari.html</b> file mentioned above, or more simply, just double click the akari.html icon. <p/><span>Step 3: Installation</span><br/>Installation is a three step process:-<ul><li>Create a new folder somewhere on your web server.<li>Upload the two files already discussed into this new folder.<li>Create a link from a convenient point within your web site to the akari.html file in your newly created folder.</ul>At this point, any visitor will be able to make full use of the Akari Web-App. There is no need to upload any Akari puzzle files. This App makes new puzzles on demand as described in the Help information available from the Help button on the App.<p/></div></body>";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void def() {
/*  181 */     Op.updateOption(Op.AK.AkW.ordinal(), "500", Op.ak);
/*  182 */     Op.updateOption(Op.AK.AkH.ordinal(), "580", Op.ak);
/*  183 */     Op.updateOption(Op.AK.AkDifficulty.ordinal(), "1", Op.ak);
/*  184 */     Op.updateOption(Op.AK.AkPattern.ordinal(), "006666", Op.ak);
/*  185 */     Op.updateOption(Op.AK.AkNumbers.ordinal(), "FFFFFF", Op.ak);
/*  186 */     Op.updateOption(Op.AK.AkLit.ordinal(), "FFFFCC", Op.ak);
/*  187 */     Op.updateOption(Op.AK.AkDark.ordinal(), "DDDDDD", Op.ak);
/*  188 */     Op.updateOption(Op.AK.AkNoLamp.ordinal(), "AAAA00", Op.ak);
/*  189 */     Op.updateOption(Op.AK.AkLamp.ordinal(), "FF0000", Op.ak);
/*  190 */     Op.updateOption(Op.AK.AkLines.ordinal(), "000000", Op.ak);
/*  191 */     Op.updateOption(Op.AK.AkError.ordinal(), "FF0000", Op.ak);
/*  192 */     Op.updateOption(Op.AK.AkPuz.ordinal(), "sample.akari", Op.ak);
/*  193 */     Op.updateOption(Op.AK.AkGrid.ordinal(), "akari1.grid", Op.ak);
/*  194 */     Op.updateOption(Op.AK.AkFont.ordinal(), "SansSerif", Op.ak);
/*  195 */     Op.updateOption(Op.AK.AkPuzColor.ordinal(), "false", Op.ak);
/*      */   }
/*      */   
/*      */   AkariBuild(JFrame jf, boolean auto, int hm, int start) {
/*  199 */     Def.puzzleMode = 20;
/*  200 */     Def.building = 0;
/*  201 */     Def.dispCursor = Boolean.valueOf(true);
/*  202 */     makeGrid();
/*      */     
/*  204 */     jfAkari = new JFrame("Akari");
/*  205 */     if (Op.getInt(Op.AK.AkH.ordinal(), Op.ak) > Methods.scrH - 200) {
/*  206 */       int diff = Op.getInt(Op.AK.AkH.ordinal(), Op.ak) - Op.getInt(Op.AK.AkW.ordinal(), Op.ak);
/*  207 */       Op.setInt(Op.AK.AkH.ordinal(), Methods.scrH - 200, Op.ak);
/*  208 */       Op.setInt(Op.AK.AkW.ordinal(), Methods.scrH - 200 + diff, Op.ak);
/*      */     } 
/*  210 */     jfAkari.setSize(Op.getInt(Op.AK.AkW.ordinal(), Op.ak), Op.getInt(Op.AK.AkH.ordinal(), Op.ak));
/*  211 */     int frameX = (jf.getX() + jfAkari.getWidth() > Methods.scrW) ? (Methods.scrW - jfAkari.getWidth() - 10) : jf.getX();
/*  212 */     jfAkari.setLocation(frameX, jf.getY());
/*  213 */     jfAkari.setLayout((LayoutManager)null);
/*  214 */     jfAkari.setDefaultCloseOperation(0);
/*  215 */     jfAkari
/*  216 */       .addComponentListener(new ComponentAdapter() {
/*      */           public void componentResized(ComponentEvent ce) {
/*  218 */             int oldw = Op.getInt(Op.AK.AkW.ordinal(), Op.ak);
/*  219 */             int oldh = Op.getInt(Op.AK.AkH.ordinal(), Op.ak);
/*  220 */             Methods.frameResize(AkariBuild.jfAkari, oldw, oldh, 500, 580);
/*  221 */             Op.setInt(Op.AK.AkW.ordinal(), AkariBuild.jfAkari.getWidth(), Op.ak);
/*  222 */             Op.setInt(Op.AK.AkH.ordinal(), AkariBuild.jfAkari.getHeight(), Op.ak);
/*  223 */             AkariBuild.restoreFrame();
/*      */           }
/*      */         });
/*      */     
/*  227 */     jfAkari
/*  228 */       .addWindowListener(new WindowAdapter() {
/*      */           public void windowClosing(WindowEvent we) {
/*  230 */             if (Def.building == 1 || Def.selecting)
/*  231 */               return;  Op.saveOptions("akari.opt", Op.ak);
/*  232 */             CrosswordExpress.transfer(1, AkariBuild.jfAkari);
/*      */           }
/*      */         });
/*      */     
/*  236 */     Methods.closeHelp();
/*      */ 
/*      */     
/*  239 */     Runnable buildThread = () -> {
/*      */         if (this.howMany == 1) {
/*      */           buildAkari();
/*      */         } else {
/*      */           multiBuild();
/*      */           
/*      */           if (sixpack) {
/*      */             Sixpack.trigger();
/*      */             jfAkari.dispose();
/*      */             Def.building = 0;
/*      */             return;
/*      */           } 
/*      */         } 
/*      */         this.buildMenuItem.setText("Start Building");
/*      */         if (Def.building == 2) {
/*      */           Def.building = 0;
/*      */           Methods.interrupted(jfAkari);
/*      */           makeGrid();
/*      */           restoreFrame();
/*      */           return;
/*      */         } 
/*      */         Methods.havePuzzle = true;
/*      */         restoreFrame();
/*      */         Methods.puzzleSaved(jfAkari, "akari", Op.ak[Op.AK.AkPuz.ordinal()]);
/*      */         Def.building = 0;
/*      */       };
/*  265 */     jl1 = new JLabel(); jfAkari.add(jl1);
/*  266 */     jl2 = new JLabel(); jfAkari.add(jl2);
/*      */ 
/*      */     
/*  269 */     menuBar = new JMenuBar();
/*  270 */     menuBar.setBackground(Def.COLOR_MENUBAR);
/*  271 */     jfAkari.setJMenuBar(menuBar);
/*      */     
/*  273 */     this.menu = new JMenu("File");
/*  274 */     menuBar.add(this.menu);
/*  275 */     this.menuItem = new JMenuItem("Load a Puzzle");
/*  276 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(76, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  277 */     this.menu.add(this.menuItem);
/*  278 */     this.menuItem
/*  279 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1)
/*      */             return; 
/*      */           pp.invalidate();
/*      */           pp.repaint();
/*      */           new Select(jfAkari, "akari", "akari", Op.ak, Op.AK.AkPuz.ordinal(), false);
/*      */         });
/*  286 */     this.menuItem = new JMenuItem("Save");
/*  287 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(83, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  288 */     this.menu.add(this.menuItem);
/*  289 */     this.menuItem
/*  290 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           saveAkari(Op.ak[Op.AK.AkPuz.ordinal()]);
/*      */           Methods.puzzleSaved(jfAkari, "akari", Op.ak[Op.AK.AkPuz.ordinal()]);
/*      */         });
/*  297 */     this.menuItem = new JMenuItem("SaveAs");
/*  298 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(65, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  299 */     this.menu.add(this.menuItem);
/*  300 */     this.menuItem
/*  301 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           Methods.puzzleDescriptionDialog(jfAkari, Op.ak[Op.AK.AkPuz.ordinal()].substring(0, Op.ak[Op.AK.AkPuz.ordinal()].indexOf(".akari")), "akari", ".akari");
/*      */           if (Methods.clickedOK) {
/*      */             saveAkari(Op.ak[Op.AK.AkPuz.ordinal()] = Methods.theFileName);
/*      */             restoreFrame();
/*      */             Methods.puzzleSaved(jfAkari, "akari", Op.ak[Op.AK.AkPuz.ordinal()]);
/*      */           } 
/*      */         });
/*  312 */     this.menuItem = new JMenuItem("Quit Construction");
/*  313 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(81, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  314 */     this.menu.add(this.menuItem);
/*  315 */     this.menuItem
/*  316 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           
/*      */           Op.saveOptions("akari.opt", Op.ak);
/*      */           CrosswordExpress.transfer(1, jfAkari);
/*      */         });
/*  324 */     this.menu = new JMenu("Build");
/*  325 */     menuBar.add(this.menu);
/*  326 */     this.menuItem = new JMenuItem("Start a new Puzzle");
/*  327 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(78, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  328 */     this.menu.add(this.menuItem);
/*  329 */     this.menuItem
/*  330 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           Methods.puzzleDescriptionDialog(jfAkari, Op.ak[Op.AK.AkPuz.ordinal()].substring(0, Op.ak[Op.AK.AkPuz.ordinal()].indexOf(".akari")), "akari", ".akari");
/*      */           if (Methods.clickedOK) {
/*      */             Op.ak[Op.AK.AkPuz.ordinal()] = Methods.theFileName;
/*      */             makeGrid();
/*      */           } 
/*      */           restoreFrame();
/*      */         });
/*  341 */     this.menuItem = new JMenuItem("Build Options");
/*  342 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(79, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  343 */     this.menu.add(this.menuItem);
/*  344 */     this.menuItem
/*  345 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1)
/*      */             return; 
/*      */           akariOptions();
/*      */           if (Methods.clickedOK) {
/*      */             makeGrid();
/*      */             if (this.howMany > 1)
/*      */               Op.ak[Op.AK.AkPuz.ordinal()] = "" + this.startPuz + ".akari"; 
/*      */           } 
/*      */           restoreFrame();
/*      */         });
/*  356 */     this.menuItem = new JMenuItem("Select a Grid");
/*  357 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(71, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  358 */     this.menu.add(this.menuItem);
/*  359 */     this.menuItem
/*  360 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           Def.puzzleMode = 2;
/*      */           JFileChooser chooser = new JFileChooser(System.getProperty("user.dir") + "/grids");
/*      */           chooser.setFileFilter(new FileNameExtensionFilter("Grid File", new String[] { "grid" }));
/*      */           chooser.setSelectedFile(new File(Op.ak[Op.AK.AkGrid.ordinal()]));
/*      */           chooser.setAccessory(new Preview(chooser));
/*      */           chooser.setMultiSelectionEnabled(true);
/*      */           if (chooser.showDialog(jfAkari, "Select Grid") == 0)
/*      */             this.grids = chooser.getSelectedFiles(); 
/*      */           Def.puzzleMode = 20;
/*      */           if (this.grids != null)
/*      */             Op.ak[Op.AK.AkGrid.ordinal()] = this.grids[0].getName(); 
/*      */           makeGrid();
/*      */           restoreFrame();
/*      */         });
/*  378 */     this.buildMenuItem = new JMenuItem("Start Building");
/*  379 */     this.buildMenuItem.setAccelerator(KeyStroke.getKeyStroke(66, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  380 */     this.menu.add(this.buildMenuItem);
/*  381 */     this.buildMenuItem
/*  382 */       .addActionListener(ae -> {
/*      */           if (Op.ak[Op.AK.AkPuz.ordinal()].length() == 0 && this.howMany == 1) {
/*      */             Methods.noName(jfAkari);
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
/*  398 */     this.menuItem = new JMenuItem("Test Puzzle Validity");
/*  399 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(84, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  400 */     this.menu.add(this.menuItem);
/*  401 */     this.menuItem
/*  402 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1)
/*      */             return;  badSolution = false;
/*      */           int j;
/*      */           for (j = 0; j < Grid.ySz; j++) {
/*      */             for (int i = 0; i < Grid.xSz; i++) {
/*      */               Grid.answer[i][j] = Grid.answer[i][j] & 0x8;
/*      */               Grid.sol[i][j] = Grid.answer[i][j] & 0x8;
/*      */             } 
/*      */           } 
/*      */           if (solveAkari(true) > 0 && !badSolution) {
/*      */             for (j = 0; j < Grid.ySz; j++) {
/*      */               for (int i = 0; i < Grid.xSz; i++) {
/*      */                 Grid.answer[i][j] = Grid.sol[i][j];
/*      */                 Grid.sol[i][j] = Grid.answer[i][j] & 0x8;
/*      */               } 
/*      */             } 
/*      */             Methods.havePuzzle = true;
/*      */             restoreFrame();
/*      */             saveAkari(Op.ak[Op.AK.AkPuz.ordinal()]);
/*      */             Methods.puzzleSaved(jfAkari, "akari", Op.ak[Op.AK.AkPuz.ordinal()]);
/*      */           } else {
/*      */             JOptionPane.showMessageDialog(jfAkari, "There is no solution to this puzzle", "Test Result", 1);
/*      */           } 
/*      */           restoreFrame();
/*      */         });
/*  428 */     this.menu = new JMenu("View");
/*  429 */     menuBar.add(this.menu);
/*  430 */     this.menuItem = new JMenuItem("Display Options");
/*  431 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(89, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  432 */     this.menu.add(this.menuItem);
/*  433 */     this.menuItem
/*  434 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           
/*      */           printOptions(jfAkari, "Display Options");
/*      */           restoreFrame();
/*      */         });
/*  442 */     this.menu = new JMenu("Export");
/*  443 */     menuBar.add(this.menu);
/*  444 */     this.menuItem = new JMenuItem("Export Akari Web-App");
/*  445 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(87, 1));
/*  446 */     this.menu.add(this.menuItem);
/*  447 */     this.menuItem
/*  448 */       .addActionListener(ae -> Methods.exportWebApp(jfAkari, "akari"));
/*      */ 
/*      */     
/*  451 */     this.menuItem = new JMenuItem("Launch a Demo Akari Web App");
/*  452 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(76, 1));
/*  453 */     this.menu.add(this.menuItem);
/*  454 */     this.menuItem
/*  455 */       .addActionListener(ae -> Methods.launchWebApp(jfAkari, "akari"));
/*      */ 
/*      */     
/*  458 */     this.menuItem = new JMenuItem("Print an Akari KDP puzzle book.");
/*  459 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(75, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  460 */     this.menu.add(this.menuItem);
/*  461 */     this.menuItem
/*  462 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           
/*      */           Methods.printKdpDialog(jfAkari, 20, 6);
/*      */         });
/*  469 */     this.menu = new JMenu("Tasks");
/*  470 */     menuBar.add(this.menu);
/*  471 */     this.menuItem = new JMenuItem("Print this Puzzle");
/*  472 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(80, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  473 */     this.menu.add(this.menuItem);
/*  474 */     this.menuItem
/*  475 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           CrosswordExpress.toPrint(jfAkari, Op.ak[Op.AK.AkPuz.ordinal()]);
/*      */         });
/*  481 */     this.menuItem = new JMenuItem("Solve this Puzzle");
/*  482 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(86, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  483 */     this.menu.add(this.menuItem);
/*  484 */     this.menuItem
/*  485 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1)
/*      */             return; 
/*      */           if (Methods.havePuzzle) {
/*      */             CrosswordExpress.transfer(21, jfAkari);
/*      */           } else {
/*      */             Methods.noPuzzle(jfAkari, "Solve");
/*      */           } 
/*      */         });
/*  494 */     this.menuItem = new JMenuItem("Delete this Puzzle");
/*  495 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(68, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  496 */     this.menu.add(this.menuItem);
/*  497 */     this.menuItem
/*  498 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           
/*      */           if (Methods.deleteAPuzzle(jfAkari, Op.ak[Op.AK.AkPuz.ordinal()], "akari", pp)) {
/*      */             loadAkari(Op.ak[Op.AK.AkPuz.ordinal()]);
/*      */             restoreFrame();
/*      */           } 
/*      */         });
/*  508 */     this.menu = new JMenu("Help");
/*  509 */     menuBar.add(this.menu);
/*  510 */     this.menuItem = new JMenuItem("Akari Help");
/*  511 */     this.menu.add(this.menuItem);
/*  512 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(72, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  513 */     this.menuItem
/*  514 */       .addActionListener(ae -> Methods.cweHelp(jfAkari, null, "Building Akari Puzzles", this.akariHelp));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  519 */     this.menuItem = new JMenuItem("Akari Web Application");
/*  520 */     this.menu.add(this.menuItem);
/*  521 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(87, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  522 */     this.menuItem
/*  523 */       .addActionListener(ae -> Methods.cweHelp(jfAkari, null, "Akari Web Application", webAppHelp));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  529 */     pp = new AkariBuildPP(0, 37, jfAkari);
/*      */     
/*  531 */     pp
/*  532 */       .addMouseListener(new MouseAdapter() {
/*      */           public void mousePressed(MouseEvent e) {
/*  534 */             AkariBuild.this.updateGrid(e);
/*      */           }
/*      */         });
/*      */ 
/*      */     
/*  539 */     pp
/*  540 */       .addMouseMotionListener(new MouseAdapter() {
/*      */           public void mouseMoved(MouseEvent e) {
/*  542 */             if (Def.isMac) {
/*  543 */               AkariBuild.jfAkari.setResizable((AkariBuild.jfAkari.getWidth() - e.getX() < 15 && AkariBuild.jfAkari
/*  544 */                   .getHeight() - e.getY() < 95));
/*      */             }
/*      */           }
/*      */         });
/*      */     
/*  549 */     jfAkari
/*  550 */       .addKeyListener(new KeyAdapter() {
/*      */           public void keyPressed(KeyEvent e) {
/*  552 */             AkariBuild.this.handleKeyPressed(e);
/*      */           }
/*      */         });
/*      */     
/*  556 */     loadAkari(Op.ak[Op.AK.AkPuz.ordinal()]);
/*  557 */     restoreFrame();
/*      */ 
/*      */     
/*  560 */     ActionListener timerAL = ae -> {
/*      */         this.myTimer.stop();
/*      */         this.thread = new Thread(paramRunnable);
/*      */         this.thread.start();
/*      */         Def.building = 1;
/*      */       };
/*  566 */     this.myTimer = new Timer(1000, timerAL);
/*      */     
/*  568 */     if (auto) {
/*  569 */       sixpack = true;
/*  570 */       this.howMany = hm; this.startPuz = start;
/*  571 */       this.myTimer.start();
/*      */     } 
/*      */   }
/*      */   
/*      */   static void restoreFrame() {
/*  576 */     jfAkari.setVisible(true);
/*  577 */     Insets insets = jfAkari.getInsets();
/*  578 */     panelW = jfAkari.getWidth() - insets.left + insets.right;
/*  579 */     panelH = jfAkari.getHeight() - insets.top + insets.bottom + 37 + menuBar.getHeight();
/*  580 */     pp.setSize(panelW, panelH);
/*  581 */     jfAkari.requestFocusInWindow();
/*  582 */     pp.repaint();
/*  583 */     Methods.infoPanel(jl1, jl2, "Build Akari", "Puzzle : " + Op.ak[Op.AK.AkPuz.ordinal()], panelW);
/*      */   }
/*      */   
/*      */   static void setSizesAndOffsets(int x, int y, int width, int height, int inset, boolean print) {
/*  587 */     int i = (width - inset) / Grid.xSz;
/*  588 */     int j = (height - inset) / Grid.ySz;
/*  589 */     Grid.xCell = Grid.yCell = (i < j) ? i : j;
/*  590 */     Grid.xOrg = print ? (x + (width - Grid.xSz * Grid.xCell) / 2) : (x + 10);
/*  591 */     Grid.yOrg = print ? (y + (height - Grid.ySz * Grid.yCell) / 2) : (y + 10);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   void akariOptions() {
/*  597 */     JDialog jdlgAkari = new JDialog(jfAkari, "Akari Options", true);
/*  598 */     jdlgAkari.setSize(270, 270);
/*  599 */     jdlgAkari.setResizable(false);
/*  600 */     jdlgAkari.setLayout((LayoutManager)null);
/*  601 */     jdlgAkari.setLocation(jfAkari.getX(), jfAkari.getY());
/*      */     
/*  603 */     jdlgAkari
/*  604 */       .addWindowListener(new WindowAdapter() {
/*      */           public void windowClosing(WindowEvent we) {
/*  606 */             Methods.closeHelp();
/*      */           }
/*      */         });
/*      */     
/*  610 */     Methods.closeHelp();
/*      */     
/*  612 */     JLabel jlDiff = new JLabel("Difficulty:");
/*  613 */     jlDiff.setForeground(Def.COLOR_LABEL);
/*  614 */     jlDiff.setSize(80, 20);
/*  615 */     jlDiff.setLocation(10, 18);
/*  616 */     jlDiff.setHorizontalAlignment(4);
/*  617 */     jdlgAkari.add(jlDiff);
/*      */     
/*  619 */     JComboBox<Integer> jcbbDifficulty = new JComboBox<>();
/*  620 */     for (int i = 1; i <= 8; i++)
/*  621 */       jcbbDifficulty.addItem(Integer.valueOf(i)); 
/*  622 */     jcbbDifficulty.setSize(120, 23);
/*  623 */     jcbbDifficulty.setLocation(100, 18);
/*  624 */     jdlgAkari.add(jcbbDifficulty);
/*  625 */     jcbbDifficulty.setBackground(Def.COLOR_BUTTONBG);
/*  626 */     jcbbDifficulty.setSelectedIndex(Op.getInt(Op.AK.AkDifficulty.ordinal(), Op.ak) - 1);
/*      */     
/*  628 */     HowManyPuzzles hmp = new HowManyPuzzles(jdlgAkari, 10, 55, this.howMany, this.startPuz, Op.getBool(Op.SX.VaryDiff.ordinal(), Op.sx).booleanValue());
/*      */     
/*  630 */     JButton jbOK = Methods.cweButton("OK", 10, 169, 80, 26, null);
/*  631 */     jbOK.addActionListener(e -> {
/*      */           this.howMany = Integer.parseInt(paramHowManyPuzzles.jtfHowMany.getText());
/*      */           this.startPuz = Integer.parseInt(paramHowManyPuzzles.jtfStartPuz.getText());
/*      */           Op.setInt(Op.AK.AkDifficulty.ordinal(), paramJComboBox.getSelectedIndex() + 1, Op.ak);
/*      */           Op.setBool(Op.SX.VaryDiff.ordinal(), Boolean.valueOf(paramHowManyPuzzles.jcbVaryDiff.isSelected()), Op.sx);
/*      */           Methods.clickedOK = true;
/*      */           paramJDialog.dispose();
/*      */           Methods.closeHelp();
/*      */         });
/*  640 */     jdlgAkari.add(jbOK);
/*      */     
/*  642 */     JButton jbCancel = Methods.cweButton("Cancel", 10, 204, 80, 26, null);
/*  643 */     jbCancel.addActionListener(e -> {
/*      */           Methods.clickedOK = false;
/*      */           paramJDialog.dispose();
/*      */           Methods.closeHelp();
/*      */         });
/*  648 */     jdlgAkari.add(jbCancel);
/*      */     
/*  650 */     JButton jbHelp = Methods.cweButton("<html><font size=6 color=BB0000 face=Serif>Help ", 100, 169, 150, 61, new ImageIcon("graphics/help.png"));
/*  651 */     jbHelp.addActionListener(e -> Methods.cweHelp(null, paramJDialog, "Akari Options", this.akariOptionsHelp));
/*      */     
/*  653 */     jdlgAkari.add(jbHelp);
/*      */     
/*  655 */     Methods.setDialogSize(jdlgAkari, 260, 240);
/*      */   }
/*      */   
/*      */   static void printOptions(JFrame jf, String type) {
/*  659 */     String[] colorLabel = { "PATTERN Cell Color", "Lamp Count Color", "LIT Cell Color", "DARK Cell Color", "NOLAMP Cell Color", "LAMP Color", "Line Color", "Solve Error Color" };
/*  660 */     int[] colorInt = { Op.AK.AkPattern.ordinal(), Op.AK.AkNumbers.ordinal(), Op.AK.AkLit.ordinal(), Op.AK.AkDark.ordinal(), Op.AK.AkNoLamp.ordinal(), Op.AK.AkLamp.ordinal(), Op.AK.AkLines.ordinal(), Op.AK.AkError.ordinal() };
/*  661 */     String[] fontLabel = { "Puzzle Font" };
/*  662 */     int[] fontInt = { Op.AK.AkFont.ordinal() };
/*  663 */     String[] checkLabel = { "PPrint Puzzle with color.", "SPrint Solution with color." };
/*  664 */     int[] checkInt = { Op.AK.AkPuzColor.ordinal(), Op.AK.AkSolColor.ordinal() };
/*  665 */     Methods.stdPrintOptions(jf, "Akari " + type, Op.ak, colorLabel, colorInt, fontLabel, fontInt, checkLabel, checkInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void saveAkari(String akariName) {
/*      */     try {
/*  674 */       DataOutputStream dataOut = new DataOutputStream(new FileOutputStream("akari/" + akariName));
/*  675 */       dataOut.writeInt(Grid.xSz);
/*  676 */       dataOut.writeInt(Grid.ySz);
/*  677 */       dataOut.writeByte(Methods.noReveal);
/*  678 */       dataOut.writeByte(Methods.noErrors);
/*  679 */       for (int i = 0; i < 54; i++)
/*  680 */         dataOut.writeByte(0); 
/*  681 */       for (int j = 0; j < Grid.ySz; j++) {
/*  682 */         for (int k = 0; k < Grid.xSz; k++) {
/*  683 */           dataOut.writeInt(Grid.sol[k][j]);
/*  684 */           dataOut.writeInt(Grid.answer[k][j]);
/*  685 */           dataOut.writeInt(Grid.puzzle[k][j]);
/*      */         } 
/*  687 */       }  dataOut.writeUTF(Methods.puzzleTitle);
/*  688 */       dataOut.writeUTF(Methods.author);
/*  689 */       dataOut.writeUTF(Methods.copyright);
/*  690 */       dataOut.writeUTF(Methods.puzzleNumber);
/*  691 */       dataOut.writeUTF(Methods.puzzleNotes);
/*  692 */       dataOut.close();
/*      */     }
/*  694 */     catch (IOException exc) {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void loadAkari(String akariName) {
/*      */     
/*  702 */     try { File fl = new File("akari/" + akariName);
/*  703 */       if (!fl.exists()) {
/*  704 */         fl = new File("akari/");
/*  705 */         String[] s = fl.list(); int k;
/*  706 */         for (k = 0; k < s.length && (
/*  707 */           s[k].lastIndexOf(".akari") == -1 || s[k].charAt(0) == '.'); k++);
/*      */         
/*  709 */         if (k == s.length)
/*  710 */           return;  akariName = s[k];
/*  711 */         Op.ak[Op.AK.AkPuz.ordinal()] = akariName;
/*      */       } 
/*      */       
/*  714 */       DataInputStream dataIn = new DataInputStream(new FileInputStream("akari/" + akariName));
/*  715 */       Grid.xSz = dataIn.readInt();
/*  716 */       Grid.ySz = dataIn.readInt();
/*  717 */       Methods.noReveal = dataIn.readByte();
/*  718 */       Methods.noErrors = dataIn.readByte(); int i;
/*  719 */       for (i = 0; i < 54; i++)
/*  720 */         dataIn.readByte(); 
/*  721 */       for (int j = 0; j < Grid.ySz; j++) {
/*  722 */         for (i = 0; i < Grid.xSz; i++) {
/*  723 */           Grid.sol[i][j] = dataIn.readInt();
/*  724 */           Grid.answer[i][j] = dataIn.readInt();
/*  725 */           Grid.puzzle[i][j] = dataIn.readInt();
/*      */         } 
/*  727 */       }  Methods.puzzleTitle = dataIn.readUTF();
/*  728 */       Methods.author = dataIn.readUTF();
/*  729 */       Methods.copyright = dataIn.readUTF();
/*  730 */       Methods.puzzleNumber = dataIn.readUTF();
/*  731 */       Methods.puzzleNotes = dataIn.readUTF();
/*  732 */       dataIn.close(); }
/*      */     
/*  734 */     catch (IOException exc) { return; }
/*  735 */      Methods.havePuzzle = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void drawAkari(Graphics2D g2, int[][] puzzleArray) {
/*  742 */     Stroke normalStroke = new BasicStroke(Grid.xCell / 25.0F, 2, 2);
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
/*      */ 
/*      */           
/*  757 */           theColor = ((Grid.answer[i][j] & 0x8) == 8) ? Op.getColorInt(Op.AK.AkPattern.ordinal(), Op.ak) : (((Grid.answer[i][j] & 0x1) == 1 || (Grid.answer[i][j] & 0x4) == 4) ? Op.getColorInt(Op.AK.AkLit.ordinal(), Op.ak) : (((Grid.answer[i][j] & 0x2) == 2) ? Op.getColorInt(Op.AK.AkNoLamp.ordinal(), Op.ak) : Op.getColorInt(Op.AK.AkDark.ordinal(), Op.ak)));
/*      */         } else {
/*  759 */           theColor = ((Grid.answer[i][j] & 0x8) == 8) ? 0 : 16777215;
/*  760 */         }  g2.setColor(new Color(theColor));
/*  761 */         g2.fillRect(Grid.xOrg + i * Grid.xCell, Grid.yOrg + j * Grid.yCell, Grid.xCell, Grid.yCell);
/*      */       } 
/*      */     } 
/*      */     
/*  765 */     g2.setColor(Def.dispWithColor.booleanValue() ? new Color(Op.getColorInt(Op.AK.AkLines.ordinal(), Op.ak)) : Def.COLOR_BLACK);
/*  766 */     for (j = 0; j < Grid.ySz + 1; j++)
/*  767 */       g2.drawLine(Grid.xOrg, Grid.yOrg + j * Grid.yCell, Grid.xOrg + Grid.xSz * Grid.xCell, Grid.yOrg + j * Grid.yCell); 
/*  768 */     for (j = 0; j < Grid.xSz + 1; j++) {
/*  769 */       g2.drawLine(Grid.xOrg + j * Grid.xCell, Grid.yOrg, Grid.xOrg + j * Grid.xCell, Grid.yOrg + Grid.xSz * Grid.yCell);
/*      */     }
/*      */     
/*  772 */     g2.setColor(Def.dispWithColor.booleanValue() ? new Color(Op.getColorInt(Op.AK.AkLamp.ordinal(), Op.ak)) : Def.COLOR_BLACK);
/*  773 */     for (j = 0; j < Grid.ySz; j++) {
/*  774 */       for (int i = 0; i < Grid.xSz; i++) {
/*  775 */         if ((Grid.answer[i][j] & 0x1) == 1) {
/*  776 */           int t = Grid.yOrg + j * Grid.yCell + Grid.yCell / 10;
/*  777 */           int l = Grid.xOrg + i * Grid.xCell + Grid.xCell / 10;
/*  778 */           int b = Grid.yOrg + (j + 1) * Grid.yCell - Grid.yCell / 10;
/*  779 */           int r = Grid.xOrg + (i + 1) * Grid.xCell - Grid.xCell / 10;
/*  780 */           int mv = Grid.yOrg + j * Grid.yCell + Grid.yCell / 2;
/*  781 */           int mh = Grid.xOrg + i * Grid.xCell + Grid.xCell / 2;
/*  782 */           g2.drawLine(l, mv, r, mv);
/*  783 */           g2.drawLine(mh, t, mh, b);
/*  784 */           t += Grid.yCell / 10;
/*  785 */           l += Grid.xCell / 10;
/*  786 */           b -= Grid.yCell / 10;
/*  787 */           r -= Grid.xCell / 10;
/*  788 */           g2.drawLine(l, t, r, b);
/*  789 */           g2.drawLine(r, t, l, b);
/*  790 */           g2.fillArc(Grid.xOrg + i * Grid.xCell + Grid.xCell / 4, Grid.yOrg + j * Grid.yCell + Grid.xCell / 4, 2 * Grid.xCell / 4 + 1, 2 * Grid.xCell / 4 + 1, 0, 360);
/*      */         } 
/*      */       } 
/*      */     } 
/*  794 */     g2.setFont(new Font(Op.ak[Op.AK.AkFont.ordinal()], 0, 8 * Grid.yCell / 10));
/*  795 */     FontMetrics fm = g2.getFontMetrics();
/*  796 */     g2.setColor(Def.dispWithColor.booleanValue() ? new Color(Op.getColorInt(Op.AK.AkNumbers.ordinal(), Op.ak)) : Def.COLOR_WHITE);
/*  797 */     for (j = 0; j < Grid.ySz; j++) {
/*  798 */       for (int i = 0; i < Grid.xSz; i++) {
/*  799 */         char ch = (char)puzzleArray[i][j];
/*  800 */         if (Character.isDigit(ch)) {
/*  801 */           int w = fm.stringWidth("" + ch);
/*  802 */           g2.drawString("" + ch, Grid.xOrg + i * Grid.xCell + (Grid.xCell - w) / 2, Grid.yOrg + j * Grid.yCell + (Grid.yCell + fm.getAscent() - fm.getDescent()) / 2);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  807 */     if (Def.dispCursor.booleanValue()) {
/*  808 */       g2.setStroke(wideStroke);
/*  809 */       g2.setColor(Def.COLOR_RED);
/*  810 */       g2.drawRect(Grid.xOrg + Grid.xCur * Grid.xCell, Grid.yOrg + Grid.yCur * Grid.yCell, Grid.xCell, Grid.yCell);
/*      */     } 
/*      */     
/*  813 */     g2.setStroke(new BasicStroke(1.0F));
/*      */   }
/*      */   
/*      */   static void printPuz(Graphics2D g2, int left, int top, int width, int height) {
/*  817 */     loadAkari(Op.ak[Op.AK.AkPuz.ordinal()]);
/*  818 */     setSizesAndOffsets(left, top, width, height, 0, false);
/*  819 */     AkariSolve.clearSolution();
/*  820 */     Def.dispWithColor = Op.getBool(Op.AK.AkPuzColor.ordinal(), Op.ak);
/*  821 */     AkariSolve.drawAkari(g2, Grid.puzzle);
/*  822 */     Def.dispWithColor = Boolean.valueOf(true);
/*  823 */     loadAkari(Op.ak[Op.AK.AkPuz.ordinal()]);
/*      */   }
/*      */   
/*      */   static void printSol(Graphics2D g2, int left, int top, int width, int height, String solutionPuzzle) {
/*  827 */     loadAkari(solutionPuzzle);
/*  828 */     setSizesAndOffsets(left, top, width, height, 0, false);
/*  829 */     Def.dispWithColor = Op.getBool(Op.AK.AkSolColor.ordinal(), Op.ak);
/*  830 */     drawAkari(g2, Grid.puzzle);
/*  831 */     Def.dispWithColor = Boolean.valueOf(true);
/*  832 */     loadAkari(Op.ak[Op.AK.AkPuz.ordinal()]);
/*      */   }
/*      */   
/*      */   static void printSolTitle(Graphics2D g2, int left, int top, int width, int height, String solutionPuzzle) {
/*  836 */     loadAkari(solutionPuzzle);
/*  837 */     Print.outputTextItem(g2, left, top, width, height, "SansSerif", 0, Methods.puzzleTitle);
/*  838 */     loadAkari(Op.ak[Op.AK.AkPuz.ordinal()]);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static void printSixpackPuz(Graphics2D g2, int left, int top, int dim, int gap, String puzName) {
/*  844 */     FontMetrics fm = g2.getFontMetrics();
/*      */     
/*  846 */     String st = Op.sx[Op.SX.SxAk.ordinal()];
/*  847 */     if (st.length() < 3) st = "AKARI"; 
/*  848 */     int w = fm.stringWidth(st);
/*  849 */     g2.drawString(st, left + (dim - w) / 2, top - gap / 3);
/*  850 */     AkariSolve.loadAkari(puzName + ".akari");
/*  851 */     setSizesAndOffsets(left, top, dim, dim, 0, true);
/*  852 */     AkariSolve.clearSolution();
/*  853 */     AkariSolve.drawAkari(g2, Grid.puzzle);
/*  854 */     if (Op.sx[Op.SX.SxRuleLang.ordinal()].equals("English")) {
/*  855 */       st = rules;
/*      */     } else {
/*  857 */       st = Op.ak[Op.AK.AkRule1.ordinal() + Op.getInt(Op.SX.SxRuleLangIndex.ordinal(), Op.sx) - 1];
/*  858 */     }  if (Op.getBool(Op.SX.SxInstructions.ordinal(), Op.sx).booleanValue()) {
/*  859 */       Methods.renderText(g2, left, top + dim + dim / 50, dim, dim / 4, "SansSerif", 1, st, 3, 4, true, 0, 0);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static void printSixpackSol(Graphics2D g2, int left, int top, int dim, int gap, String solName) {
/*  865 */     FontMetrics fm = g2.getFontMetrics();
/*      */     
/*  867 */     String st = Op.sx[Op.SX.SxAk.ordinal()];
/*  868 */     if (st.length() < 3) st = "AKARI"; 
/*  869 */     int w = fm.stringWidth(st);
/*  870 */     g2.drawString(st, left + (dim - w) / 2, top - gap / 3);
/*  871 */     loadAkari(solName + ".akari");
/*  872 */     setSizesAndOffsets(left, top, dim, dim, 0, true);
/*  873 */     drawAkari(g2, Grid.puzzle);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static void printKDPPuz(Graphics2D g2, int left, int top, int dim, int gap, String puzName) {
/*  879 */     FontMetrics fm = g2.getFontMetrics();
/*      */     
/*  881 */     String st = puzName;
/*  882 */     int w = fm.stringWidth(st);
/*  883 */     g2.drawString(st, left + (dim - w) / 2, top - gap / 3);
/*  884 */     AkariSolve.loadAkari(puzName + ".akari");
/*  885 */     setSizesAndOffsets(left, top, dim, dim, 0, true);
/*  886 */     AkariSolve.clearSolution();
/*  887 */     AkariSolve.drawAkari(g2, Grid.puzzle);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static void printKDPSol(Graphics2D g2, int left, int top, int dim, int gap, String solName) {
/*  893 */     FontMetrics fm = g2.getFontMetrics();
/*      */     
/*  895 */     String st = solName;
/*  896 */     int w = fm.stringWidth(st);
/*  897 */     g2.drawString(st, left + (dim - w) / 2, top - gap / 3);
/*  898 */     loadAkari(solName + ".akari");
/*  899 */     setSizesAndOffsets(left, top, dim, dim, 0, true);
/*  900 */     drawAkari(g2, Grid.letter);
/*      */   }
/*      */   
/*      */   static void makeGrid() {
/*  904 */     Methods.havePuzzle = false;
/*  905 */     Grid.clearGrid();
/*  906 */     Grid.loadGrid(Op.ak[Op.AK.AkGrid.ordinal()]);
/*  907 */     for (int y = 0; y < Grid.ySz; y++) {
/*  908 */       for (int x = 0; x < Grid.xSz; x++) {
/*  909 */         if (Grid.mode[x][y] == 1) {
/*  910 */           Grid.sol[x][y] = 8; Grid.answer[x][y] = 8;
/*      */         } 
/*      */       } 
/*      */     }  } void updateGrid(MouseEvent e) {
/*  914 */     int x = e.getX(), y = e.getY();
/*      */     
/*  916 */     if (Def.building == 1)
/*  917 */       return;  if (x < Grid.xOrg || y < Grid.yOrg)
/*  918 */       return;  x = (x - Grid.xOrg) / Grid.xCell;
/*  919 */     y = (y - Grid.yOrg) / Grid.yCell;
/*  920 */     if (x >= Grid.xSz || y >= Grid.ySz)
/*      */       return; 
/*  922 */     Grid.xCur = x; Grid.yCur = y;
/*  923 */     pp.repaint();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static void insertLamp(int x, int y) {
/*  929 */     if (Grid.findHint.booleanValue()) { setHint(x, y); return; }
/*  930 */      Grid.sol[x][y] = Grid.sol[x][y] | 0x1; int i;
/*  931 */     for (i = x - 1; i >= 0 && (Grid.sol[i][y] & 0x8) != 8; ) { Grid.sol[i][y] = Grid.sol[i][y] | 0x4; i--; }
/*  932 */      for (i = x + 1; i < Grid.xSz && (Grid.sol[i][y] & 0x8) != 8; ) { Grid.sol[i][y] = Grid.sol[i][y] | 0x4; i++; }
/*  933 */      for (i = y - 1; i >= 0 && (Grid.sol[x][i] & 0x8) != 8; ) { Grid.sol[x][i] = Grid.sol[x][i] | 0x4; i--; }
/*  934 */      for (i = y + 1; i < Grid.ySz && (Grid.sol[x][i] & 0x8) != 8; ) { Grid.sol[x][i] = Grid.sol[x][i] | 0x4; i++; }
/*      */   
/*      */   }
/*      */   static boolean insertNoLamp(int x, int y) {
/*  938 */     if (Grid.sol[x][y] != 0 || Grid.answer[x][y] == 8) return false; 
/*  939 */     if (Grid.findHint.booleanValue()) { setHint(x, y); return true; }
/*  940 */      Grid.sol[x][y] = Grid.sol[x][y] | 0x2;
/*  941 */     return true;
/*      */   }
/*      */   
/*      */   static int[] countNeighbours(int x, int y) {
/*  945 */     int[] count = { 0, 0, 0, 0, 0 };
/*      */     
/*  947 */     if (x > 0) {
/*  948 */       if (Grid.sol[x - 1][y] == 0 && Grid.answer[x - 1][y] != 8) count[0] = count[0] + 1; 
/*  949 */       if ((Grid.sol[x - 1][y] & 0x1) == 1) count[1] = count[1] + 1; 
/*  950 */       if ((Grid.sol[x - 1][y] & 0x2) == 2) count[2] = count[2] + 1; 
/*  951 */       if ((Grid.sol[x - 1][y] & 0x4) == 4) count[3] = count[3] + 1; 
/*      */     } 
/*  953 */     if (x < Grid.xSz - 1) {
/*  954 */       if (Grid.sol[x + 1][y] == 0 && Grid.answer[x + 1][y] != 8) count[0] = count[0] + 1; 
/*  955 */       if ((Grid.sol[x + 1][y] & 0x1) == 1) count[1] = count[1] + 1; 
/*  956 */       if ((Grid.sol[x + 1][y] & 0x2) == 2) count[2] = count[2] + 1; 
/*  957 */       if ((Grid.sol[x + 1][y] & 0x4) == 4) count[3] = count[3] + 1; 
/*      */     } 
/*  959 */     if (y > 0) {
/*  960 */       if (Grid.sol[x][y - 1] == 0 && Grid.answer[x][y - 1] != 8) count[0] = count[0] + 1; 
/*  961 */       if ((Grid.sol[x][y - 1] & 0x1) == 1) count[1] = count[1] + 1; 
/*  962 */       if ((Grid.sol[x][y - 1] & 0x2) == 2) count[2] = count[2] + 1; 
/*  963 */       if ((Grid.sol[x][y - 1] & 0x4) == 4) count[3] = count[3] + 1; 
/*      */     } 
/*  965 */     if (y < Grid.ySz - 1) {
/*  966 */       if (Grid.sol[x][y + 1] == 0 && Grid.answer[x][y + 1] != 8) count[0] = count[0] + 1; 
/*  967 */       if ((Grid.sol[x][y + 1] & 0x1) == 1) count[1] = count[1] + 1; 
/*  968 */       if ((Grid.sol[x][y + 1] & 0x2) == 2) count[2] = count[2] + 1; 
/*  969 */       if ((Grid.sol[x][y + 1] & 0x4) == 4) count[3] = count[3] + 1;
/*      */     
/*      */     } 
/*  972 */     return count;
/*      */   }
/*      */   
/*      */   static void setHint(int x, int y) {
/*  976 */     Grid.hintXb[Grid.hintIndexb] = x;
/*  977 */     Grid.hintYb[Grid.hintIndexb] = y;
/*  978 */     Grid.hintIndexb++;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean requiredLamps() {
/*  984 */     boolean found = false;
/*      */     
/*  986 */     Grid.hintIndexb = 0;
/*  987 */     for (int y = 0; y < Grid.ySz; y++) {
/*  988 */       for (int x = 0; x < Grid.xSz; x++) {
/*  989 */         if (Grid.puzzle[x][y] > 48) {
/*  990 */           int lamps = Grid.puzzle[x][y] - 48;
/*  991 */           int[] count = countNeighbours(x, y);
/*  992 */           if (lamps > count[0] + count[1])
/*  993 */             badSolution = true; 
/*  994 */           if (lamps == count[0] + count[1] && count[0] > 0) {
/*  995 */             if (x > 0 && Grid.sol[x - 1][y] == 0 && Grid.answer[x - 1][y] != 8) insertLamp(x - 1, y); 
/*  996 */             if (x < Grid.xSz - 1 && Grid.sol[x + 1][y] == 0 && Grid.answer[x + 1][y] != 8) insertLamp(x + 1, y); 
/*  997 */             if (y > 0 && Grid.sol[x][y - 1] == 0 && Grid.answer[x][y - 1] != 8) insertLamp(x, y - 1); 
/*  998 */             if (y < Grid.ySz - 1 && Grid.sol[x][y + 1] == 0 && Grid.answer[x][y + 1] != 8) insertLamp(x, y + 1); 
/*  999 */             if (Grid.findHint.booleanValue()) return true; 
/* 1000 */             found = true;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/* 1005 */     return found;
/*      */   }
/*      */ 
/*      */   
/*      */   static boolean prohibitedLamps() {
/* 1010 */     boolean found = false;
/*      */ 
/*      */     
/* 1013 */     for (int y = 0; y < Grid.ySz; y++) {
/* 1014 */       for (int x = 0; x < Grid.xSz; x++) {
/* 1015 */         if (Grid.puzzle[x][y] > 0) {
/* 1016 */           int lamps = Grid.puzzle[x][y] - 48;
/* 1017 */           int[] count = countNeighbours(x, y);
/*      */           
/* 1019 */           if (lamps == count[1] && count[0] > 0) {
/* 1020 */             if (x > 0) insertNoLamp(x - 1, y); 
/* 1021 */             if (x < Grid.xSz - 1) insertNoLamp(x + 1, y); 
/* 1022 */             if (y > 0) insertNoLamp(x, y - 1); 
/* 1023 */             if (y < Grid.ySz - 1) insertNoLamp(x, y + 1); 
/* 1024 */             if (Grid.findHint.booleanValue()) return true; 
/* 1025 */             found = true;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/* 1030 */     return found;
/*      */   }
/*      */ 
/*      */   
/*      */   static boolean orphanCells() {
/* 1035 */     boolean found = false;
/*      */     
/* 1037 */     for (int y = 0; y < Grid.ySz; y++) {
/* 1038 */       for (int x = 0; x < Grid.xSz; x++) {
/* 1039 */         if (Grid.sol[x][y] == 0 && (Grid.answer[x][y] & 0x8) == 0) {
/* 1040 */           int count = 0; int i;
/* 1041 */           for (i = x - 1; i >= 0 && (Grid.answer[i][y] & 0x8) == 0; ) { if (Grid.sol[i][y] == 0) count++;  i--; }
/* 1042 */            for (i = x + 1; i < Grid.xSz && (Grid.answer[i][y] & 0x8) == 0; ) { if (Grid.sol[i][y] == 0) count++;  i++; }
/* 1043 */            for (i = y - 1; i >= 0 && (Grid.answer[x][i] & 0x8) == 0; ) { if (Grid.sol[x][i] == 0) count++;  i--; }
/* 1044 */            for (i = y + 1; i < Grid.ySz && (Grid.answer[x][i] & 0x8) == 0; ) { if (Grid.sol[x][i] == 0) count++;  i++; }
/* 1045 */            if (count == 0) {
/* 1046 */             insertLamp(x, y);
/* 1047 */             if (Grid.findHint.booleanValue()) return true; 
/* 1048 */             found = true;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/* 1053 */     return found;
/*      */   }
/*      */ 
/*      */   
/*      */   static boolean illuminateProhibitedLamps(int mode) {
/* 1058 */     boolean found = false;
/*      */     
/* 1060 */     for (int y = 0; y < Grid.ySz; y++) {
/* 1061 */       for (int x = 0; x < Grid.xSz; x++) {
/* 1062 */         if (Grid.sol[x][y] == 2) {
/* 1063 */           int count = 0, b = count, a = b;
/* 1064 */           int d = -1, c = d; int i;
/* 1065 */           for (i = x - 1; i >= 0 && (Grid.answer[i][y] & 0x8) == 0; ) { if (Grid.sol[i][y] == 0) { count++; a = i; b = y; if (c == -1) { c = a; d = b; }  }  i--; }
/* 1066 */            for (i = x + 1; i < Grid.xSz && (Grid.answer[i][y] & 0x8) == 0; ) { if (Grid.sol[i][y] == 0) { count++; a = i; b = y; if (c == -1) { c = a; d = b; }  }  i++; }
/* 1067 */            for (i = y - 1; i >= 0 && (Grid.answer[x][i] & 0x8) == 0; ) { if (Grid.sol[x][i] == 0) { count++; a = x; b = i; if (c == -1) { c = a; d = b; }  }  i--; }
/* 1068 */            for (i = y + 1; i < Grid.ySz && (Grid.answer[x][i] & 0x8) == 0; ) { if (Grid.sol[x][i] == 0) { count++; a = x; b = i; if (c == -1) { c = a; d = b; }  }  i++; }
/*      */ 
/*      */           
/* 1071 */           if (mode == 1 && count == 1) {
/* 1072 */             insertLamp(a, b);
/* 1073 */             if (Grid.findHint.booleanValue()) return true; 
/* 1074 */             found = true;
/*      */           } 
/*      */           
/* 1077 */           if (mode == 2 && count == 2 && a != c && b != d) {
/* 1078 */             int m = (a == x) ? c : a;
/* 1079 */             int n = (b == y) ? d : b;
/* 1080 */             if (Grid.sol[m][n] == 0) {
/* 1081 */               found = true;
/* 1082 */               for (i = ((m < x) ? m : x) + 1; i < ((m < x) ? x : m); i++) {
/* 1083 */                 if ((Grid.sol[i][n] & 0x8) == 8)
/* 1084 */                   found = false; 
/* 1085 */               }  for (i = ((n < y) ? n : y) + 1; i < ((n < y) ? y : n); i++) {
/* 1086 */                 if ((Grid.sol[m][i] & 0x8) == 8)
/* 1087 */                   found = false; 
/* 1088 */               }  if (found) {
/* 1089 */                 insertNoLamp(m, n);
/* 1090 */                 if (Grid.findHint.booleanValue()) return true; 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/* 1097 */     return found;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean secondaryProhibitedLamps() {
/* 1103 */     boolean found = false;
/*      */     
/* 1105 */     for (int y = 0; y < Grid.ySz; y++) {
/* 1106 */       for (int x = 0; x < Grid.xSz; x++) {
/* 1107 */         if (Grid.puzzle[x][y] > 0) {
/* 1108 */           int lamps = Grid.puzzle[x][y] - 48;
/* 1109 */           int[] count = countNeighbours(x, y);
/*      */           
/* 1111 */           if (lamps - count[1] == count[0] - 1) {
/* 1112 */             if (x > 0 && y > 0 && Grid.sol[x - 1][y] == 0 && Grid.sol[x][y - 1] == 0 && Grid.answer[x - 1][y] != 8 && Grid.answer[x][y - 1] != 8 && 
/* 1113 */               insertNoLamp(x - 1, y - 1)) found = true; 
/* 1114 */             if (x > 0 && y < Grid.ySz - 1 && Grid.sol[x - 1][y] == 0 && Grid.sol[x][y + 1] == 0 && Grid.answer[x - 1][y] != 8 && Grid.answer[x][y + 1] != 8 && 
/* 1115 */               insertNoLamp(x - 1, y + 1)) found = true; 
/* 1116 */             if (x < Grid.xSz - 1 && y < Grid.ySz - 1 && Grid.sol[x + 1][y] == 0 && Grid.sol[x][y + 1] == 0 && Grid.answer[x + 1][y] != 8 && Grid.answer[x][y + 1] != 8 && 
/* 1117 */               insertNoLamp(x + 1, y + 1)) found = true; 
/* 1118 */             if (x < Grid.xSz - 1 && y > 0 && Grid.sol[x + 1][y] == 0 && Grid.sol[x][y - 1] == 0 && Grid.answer[x + 1][y] != 8 && Grid.answer[x][y - 1] != 8 && 
/* 1119 */               insertNoLamp(x + 1, y - 1)) found = true; 
/* 1120 */             if (found && Grid.findHint.booleanValue()) return true; 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/* 1125 */     return found;
/*      */   }
/*      */ 
/*      */   
/*      */   static boolean threeAndOne() {
/* 1130 */     boolean found = false;
/*      */     
/* 1132 */     for (int y = 1; y < Grid.ySz - 1; y++) {
/* 1133 */       for (int x = 1; x < Grid.xSz - 1; x++) {
/* 1134 */         if (Grid.puzzle[x][y] == 51 && countNeighbours(x, y)[0] == 4)
/* 1135 */         { if (Grid.puzzle[x - 1][y - 1] == 49) { insertLamp(x + 1, y); insertLamp(x, y + 1); found = true; }
/* 1136 */            if (Grid.puzzle[x - 1][y + 1] == 49) { insertLamp(x, y - 1); insertLamp(x + 1, y); found = true; }
/* 1137 */            if (Grid.puzzle[x + 1][y - 1] == 49) { insertLamp(x - 1, y); insertLamp(x, y + 1); found = true; }
/* 1138 */            if (Grid.puzzle[x + 1][y + 1] == 49) { insertLamp(x - 1, y); insertLamp(x, y - 1); found = true; }  } 
/*      */       } 
/* 1140 */     }  return found;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean twoAndOne() {
/* 1146 */     boolean found = false;
/*      */     
/* 1148 */     for (int y = 1; y < Grid.ySz; y++) {
/* 1149 */       for (int x = 1; x < Grid.xSz; x++) {
/* 1150 */         int[] count = countNeighbours(x, y);
/* 1151 */         if (Grid.puzzle[x][y] == 50 && count[0] == 3 && count[1] == 0) {
/* 1152 */           if (x > 0 && y > 0 && Grid.puzzle[x - 1][y - 1] == 49 && Grid.sol[x - 1][y] == 0 && Grid.sol[x][y - 1] == 0) {
/* 1153 */             if (x < Grid.xSz - 1 && Grid.sol[x + 1][y] == 0) { insertLamp(x + 1, y); } else { insertLamp(x, y + 1); }
/* 1154 */              if (Grid.findHint.booleanValue()) return true; 
/* 1155 */             found = true;
/*      */           } 
/* 1157 */           if (x > 0 && y < Grid.ySz - 1 && Grid.puzzle[x - 1][y + 1] == 49 && Grid.sol[x - 1][y] == 0 && Grid.sol[x][y + 1] == 0) {
/* 1158 */             if (x < Grid.xSz - 1 && Grid.sol[x + 1][y] == 0) { insertLamp(x + 1, y); } else { insertLamp(x, y - 1); }
/* 1159 */              if (Grid.findHint.booleanValue()) return true; 
/* 1160 */             found = true;
/*      */           } 
/* 1162 */           if (x < Grid.xSz - 1 && y < Grid.ySz - 1 && Grid.puzzle[x + 1][y + 1] == 49 && Grid.sol[x + 1][y] == 0 && Grid.sol[x][y + 1] == 0) {
/* 1163 */             if (x > 0 && Grid.sol[x - 1][y] == 0) { insertLamp(x - 1, y); } else { insertLamp(x, y - 1); }
/* 1164 */              if (Grid.findHint.booleanValue()) return true; 
/* 1165 */             found = true;
/*      */           } 
/* 1167 */           if (x < Grid.xSz - 1 && y > 0 && Grid.puzzle[x + 1][y - 1] == 49 && Grid.sol[x + 1][y] == 0 && Grid.sol[x][y - 1] == 0) {
/* 1168 */             if (x > 0 && Grid.sol[x - 1][y] == 0) { insertLamp(x - 1, y); } else { insertLamp(x, y + 1); }
/* 1169 */              if (Grid.findHint.booleanValue()) return true; 
/* 1170 */             found = true;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/* 1175 */     return found;
/*      */   }
/*      */ 
/*      */   
/*      */   static int nextHint() {
/* 1180 */     for (int j = 0; j < Grid.ySz; j++) {
/* 1181 */       for (int i = 0; i < Grid.xSz; i++) {
/* 1182 */         if ((Grid.answer[i][j] & 0x8) != 8 && (((Grid.sol[i][j] & 0x1) == 1 && (Grid.answer[i][j] & 0x1) != 1) || ((Grid.sol[i][j] & 0x2) == 2 && (Grid.answer[i][j] & 0x1) == 1)))
/*      */         
/*      */         { 
/* 1185 */           Grid.hintXb[Grid.hintIndexb] = i;
/* 1186 */           Grid.hintYb[Grid.hintIndexb] = j;
/* 1187 */           Grid.hintIndexb++; } 
/*      */       } 
/* 1189 */     }  if (Grid.hintIndexb > 0) {
/* 1190 */       return -1;
/*      */     }
/* 1192 */     if (requiredLamps()) return 0; 
/* 1193 */     if (prohibitedLamps()) return 1; 
/* 1194 */     if (orphanCells()) return 2; 
/* 1195 */     if (secondaryProhibitedLamps()) return 3; 
/* 1196 */     if (illuminateProhibitedLamps(1)) return 4; 
/* 1197 */     if (illuminateProhibitedLamps(2)) return 5; 
/* 1198 */     if (threeAndOne()) return 6; 
/* 1199 */     if (twoAndOne()) return 7; 
/* 1200 */     return 8;
/*      */   }
/*      */   
/*      */   int solveAkari(boolean uniqueTest) {
/* 1204 */     int thisDiff = 0;
/*      */     
/* 1206 */     for (int y = 0; y < Grid.ySz; y++) {
/* 1207 */       for (int x = 0; x < Grid.xSz; x++)
/* 1208 */         Grid.sol[x][y] = Grid.sol[x][y] & 0x8; 
/*      */     } 
/*      */     while (true) {
/* 1211 */       while (requiredLamps()) { if (thisDiff < 1) thisDiff = 1;  }
/* 1212 */        if ((Op.getInt(Op.AK.AkDifficulty.ordinal(), Op.ak) > 1 || uniqueTest) && prohibitedLamps()) { if (thisDiff < 2) thisDiff = 2;  continue; }
/* 1213 */        if ((Op.getInt(Op.AK.AkDifficulty.ordinal(), Op.ak) > 2 || uniqueTest) && orphanCells()) { if (thisDiff < 3) thisDiff = 3;  continue; }
/* 1214 */        if ((Op.getInt(Op.AK.AkDifficulty.ordinal(), Op.ak) > 3 || uniqueTest) && secondaryProhibitedLamps()) { if (thisDiff < 4) thisDiff = 4;  continue; }
/* 1215 */        if ((Op.getInt(Op.AK.AkDifficulty.ordinal(), Op.ak) > 4 || uniqueTest) && illuminateProhibitedLamps(1)) { if (thisDiff < 5) thisDiff = 5;  continue; }
/* 1216 */        if ((Op.getInt(Op.AK.AkDifficulty.ordinal(), Op.ak) > 5 || uniqueTest) && illuminateProhibitedLamps(2)) { if (thisDiff < 6) thisDiff = 6;  continue; }
/* 1217 */        if ((Op.getInt(Op.AK.AkDifficulty.ordinal(), Op.ak) > 6 || uniqueTest) && threeAndOne()) { if (thisDiff < 7) thisDiff = 7;  continue; }
/* 1218 */        if ((Op.getInt(Op.AK.AkDifficulty.ordinal(), Op.ak) > 7 || uniqueTest) && twoAndOne()) { if (thisDiff < 8) thisDiff = 8;  continue; }
/*      */       
/*      */       break;
/*      */     } 
/* 1222 */     for (int j = 0; j < Grid.ySz; j++) {
/* 1223 */       for (int i = 0; i < Grid.xSz; i++) {
/* 1224 */         if ((Grid.sol[i][j] & 0xD) == 0)
/* 1225 */           return 0; 
/*      */       } 
/* 1227 */     }  if (!uniqueTest && thisDiff != Op.getInt(Op.AK.AkDifficulty.ordinal(), Op.ak)) return 0; 
/* 1228 */     return thisDiff;
/*      */   }
/*      */   
/*      */   void depopulate() {
/* 1232 */     Random r = new Random();
/* 1233 */     int sz = Grid.xSz * Grid.ySz, vec[] = new int[sz];
/*      */     int i;
/* 1235 */     for (i = 0; i < sz; ) { vec[i] = i; i++; }
/* 1236 */      for (i = 0; i < sz; i++) {
/* 1237 */       int j = r.nextInt(sz);
/* 1238 */       int k = vec[i]; vec[i] = vec[j]; vec[j] = k;
/*      */     } 
/*      */     
/* 1241 */     for (int v = 0; v < sz; v++) {
/* 1242 */       i = vec[v] % Grid.xSz; int j = vec[v] / Grid.xSz;
/* 1243 */       if ((Grid.sol[i][j] & 0x8) == 8) {
/* 1244 */         int mem = Grid.puzzle[i][j];
/* 1245 */         Grid.puzzle[i][j] = 0;
/* 1246 */         if (solveAkari(false) == 0)
/* 1247 */           Grid.puzzle[i][j] = mem; 
/* 1248 */         for (int y = 0; y < Grid.ySz; y++) {
/* 1249 */           for (int x = 0; x < Grid.xSz; x++)
/* 1250 */             Grid.sol[x][y] = Grid.sol[x][y] & 0x8; 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   private void multiBuild() {
/* 1256 */     String title = Methods.puzzleTitle;
/* 1257 */     String[] grid = { "akari1.grid", "akari2.grid", "akari3.grid", "akari4.grid", "akari5.grid", "akari6.grid", "akari7.grid" };
/* 1258 */     int[] diffDef = { 1, 2, 3, 4, 5, 6, 8 };
/*      */     
/* 1260 */     SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
/* 1261 */     Calendar c = Calendar.getInstance();
/*      */     
/* 1263 */     String saveAkariGrid = Op.ak[Op.AK.AkGrid.ordinal()];
/* 1264 */     int diffMem = Op.getInt(Op.AK.AkDifficulty.ordinal(), Op.ak);
/*      */     
/* 1266 */     for (this.hmCount = 0; this.hmCount < this.howMany; this.hmCount++) {
/* 1267 */       if (this.startPuz > 9999999) { try {
/* 1268 */           c.setTime(sdf.parse("" + this.startPuz));
/* 1269 */         } catch (ParseException ex) {}
/* 1270 */         this.startPuz = Integer.parseInt(sdf.format(c.getTime())); }
/*      */ 
/*      */       
/* 1273 */       Methods.puzzleTitle = "AKARI Puzzle : " + this.startPuz;
/* 1274 */       if (Op.getBool(Op.SX.VaryDiff.ordinal(), Op.sx).booleanValue()) {
/* 1275 */         Op.ak[Op.AK.AkGrid.ordinal()] = grid[(this.startPuz - 1) % 7];
/*      */       }
/* 1277 */       Grid.clearGrid();
/* 1278 */       if (this.grids != null) {
/* 1279 */         Grid.loadGrid(this.grids[this.hmCount % this.grids.length].getName());
/*      */       } else {
/* 1281 */         Grid.loadGrid(Op.ak[Op.AK.AkGrid.ordinal()]);
/* 1282 */       }  for (int y = 0; y < Grid.ySz; y++) {
/* 1283 */         for (int x = 0; x < Grid.xSz; x++)
/* 1284 */         { if (Grid.mode[x][y] == 1)
/* 1285 */           { Grid.answer[x][y] = 8; Grid.sol[x][y] = 8; }  } 
/* 1286 */       }  Op.setInt(Op.AK.AkDifficulty.ordinal(), diffDef[(this.startPuz - 1) % 7], Op.ak);
/*      */       
/* 1288 */       Methods.buildProgress(jfAkari, Op.ak[Op.AK.AkPuz
/* 1289 */             .ordinal()] = "" + this.startPuz + ".akari");
/* 1290 */       buildAkari();
/* 1291 */       restoreFrame();
/* 1292 */       Wait.shortWait(100);
/* 1293 */       if (Def.building == 2)
/*      */         return; 
/* 1295 */       this.startPuz++;
/*      */     } 
/* 1297 */     this.howMany = 1;
/* 1298 */     Methods.puzzleTitle = title;
/* 1299 */     Op.ak[Op.AK.AkGrid.ordinal()] = saveAkariGrid;
/* 1300 */     Op.setInt(Op.AK.AkDifficulty.ordinal(), diffMem, Op.ak);
/*      */   }
/*      */   
/*      */   boolean buildAkari() {
/* 1304 */     int sz = Grid.xSz * Grid.ySz, vec[] = new int[256];
/* 1305 */     Random r = new Random();
/*      */     int count;
/* 1307 */     for (count = 0; count < 10000; count++) {
/* 1308 */       int k; for (k = 0; k < Grid.ySz; k++) {
/* 1309 */         for (int m = 0; m < Grid.xSz; m++)
/* 1310 */           Grid.sol[m][k] = Grid.answer[m][k] & 0x8; 
/*      */       }  int i;
/* 1312 */       for (i = 0; i < sz; ) { vec[i] = i; i++; }
/* 1313 */        for (i = 0; i < sz; i++) {
/* 1314 */         k = r.nextInt(sz);
/* 1315 */         int m = vec[i]; vec[i] = vec[k]; vec[k] = m;
/*      */       } 
/*      */       
/* 1318 */       for (int v = 0; v < sz; v++) {
/* 1319 */         i = vec[v] % Grid.xSz;
/* 1320 */         k = vec[v] / Grid.xSz;
/* 1321 */         if (Grid.sol[i][k] == 0) {
/* 1322 */           insertLamp(i, k);
/*      */         }
/*      */       } 
/* 1325 */       for (k = 0; k < Grid.ySz; k++) {
/* 1326 */         for (i = 0; i < Grid.xSz; i++) {
/* 1327 */           if ((Grid.sol[i][k] & 0x8) == 8)
/* 1328 */             Grid.puzzle[i][k] = 48 + countNeighbours(i, k)[1]; 
/*      */         } 
/* 1330 */       }  for (k = 0; k < Grid.ySz; k++) {
/* 1331 */         for (i = 0; i < Grid.xSz; i++) {
/* 1332 */           Grid.answer[i][k] = Grid.answer[i][k] & 0x8; Grid.sol[i][k] = Grid.answer[i][k] & 0x8;
/*      */         } 
/* 1334 */       }  depopulate();
/* 1335 */       if (solveAkari(false) == Op.getInt(Op.AK.AkDifficulty.ordinal(), Op.ak))
/* 1336 */         break;  if (Def.building == 2) return true;
/*      */       
/* 1338 */       if (this.howMany == 1 && count % 500 == 0) {
/* 1339 */         restoreFrame();
/* 1340 */         Methods.buildProgress(jfAkari, Op.ak[Op.AK.AkPuz.ordinal()]);
/*      */       } 
/*      */     } 
/* 1343 */     if (count >= 10000) return false;
/*      */     
/* 1345 */     for (int j = 0; j < Grid.ySz; j++) {
/* 1346 */       for (int i = 0; i < Grid.xSz; i++) {
/* 1347 */         Grid.answer[i][j] = Grid.answer[i][j] | Grid.sol[i][j];
/* 1348 */         Grid.sol[i][j] = 0;
/*      */       } 
/*      */     } 
/* 1351 */     saveAkari(Op.ak[Op.AK.AkPuz.ordinal()]);
/* 1352 */     return true;
/*      */   }
/*      */   void handleKeyPressed(KeyEvent e) {
/*      */     char ch;
/* 1356 */     if (Def.building == 1)
/* 1357 */       return;  if (e.isAltDown())
/* 1358 */       return;  switch (e.getKeyCode()) { case 38:
/* 1359 */         if (Grid.yCur > 0) Grid.yCur--;  break;
/* 1360 */       case 40: if (Grid.yCur < Grid.ySz - 1) Grid.yCur++;  break;
/* 1361 */       case 37: if (Grid.xCur > 0) Grid.xCur--;  break;
/* 1362 */       case 39: if (Grid.xCur < Grid.xSz - 1) Grid.xCur++;  break;
/* 1363 */       case 36: Grid.xCur = 0; break;
/* 1364 */       case 35: Grid.xCur = Grid.xSz - 1; break;
/* 1365 */       case 33: Grid.yCur = 0; break;
/* 1366 */       case 34: Grid.yCur = Grid.ySz - 1; break;
/*      */       case 8:
/*      */       case 10:
/*      */       case 32:
/*      */       case 127:
/* 1371 */         Grid.puzzle[Grid.xCur][Grid.yCur] = 0;
/*      */         break;
/*      */       default:
/* 1374 */         ch = e.getKeyChar();
/* 1375 */         if (Character.isDigit(ch) && ch < '5' && Grid.mode[Grid.xCur][Grid.yCur] == 1)
/* 1376 */           Grid.puzzle[Grid.xCur][Grid.yCur] = ch; 
/*      */         break; }
/*      */     
/* 1379 */     pp.repaint();
/*      */   }
/*      */ }


/* Location:              C:\Users\mustapha\Desktop\Crossword-Express.jar!\crosswordexpress\AkariBuild.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */