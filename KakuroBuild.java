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
/*      */ import javax.swing.JFileChooser;
/*      */ import javax.swing.JLabel;
/*      */ import javax.swing.JMenuItem;
/*      */ import javax.swing.KeyStroke;
/*      */ 
/*      */ public class KakuroBuild {
/*      */   static JFrame jfKakuro;
/*      */   static JMenuBar menuBar;
/*      */   JMenu menu;
/*      */   JMenu submenu;
/*      */   JMenuItem menuItem;
/*      */   JMenuItem buildMenuItem;
/*   24 */   int howMany = 1; static JPanel pp; static int panelW; static int panelH; static JLabel jl1; static JLabel jl2; Timer myTimer; Thread thread; int startPuz = Integer.parseInt((new SimpleDateFormat("yyyyMMdd")).format(new Date())); int hmCount;
/*      */   boolean sixpack;
/*      */   File[] grids;
/*   27 */   static String rules = "This is a crossword puzzle which uses numbers instead of letters. The clue for each 'word' is the sum of the digits which make up that word, and all clues are included within the body of the puzzle. Keep in mind that there are no repeated digits within any word.";
/*      */ 
/*      */   
/*      */   int undoIndex;
/*      */   
/*   32 */   int[] undoI = new int[750];
/*   33 */   int[] undoM = new int[750];
/*   34 */   int[] undoS = new int[750];
/*   35 */   int[] undoX = new int[750];
/*   36 */   int[] undoY = new int[750];
/*   37 */   int[] bit = new int[] { 1, 2, 4, 8, 16, 32, 64, 128, 256 };
/*   38 */   int[] invbit = new int[] { 510, 509, 507, 503, 495, 479, 447, 383, 255 };
/*      */   
/*      */   static void def() {
/*   41 */     Op.updateOption(Op.KK.KkW.ordinal(), "500", Op.kk);
/*   42 */     Op.updateOption(Op.KK.KkH.ordinal(), "580", Op.kk);
/*   43 */     Op.updateOption(Op.KK.KkBg.ordinal(), "003333", Op.kk);
/*   44 */     Op.updateOption(Op.KK.KkCell.ordinal(), "FFFFDD", Op.kk);
/*   45 */     Op.updateOption(Op.KK.KkClueBg.ordinal(), "CCCC88", Op.kk);
/*   46 */     Op.updateOption(Op.KK.KkNumber.ordinal(), "000066", Op.kk);
/*   47 */     Op.updateOption(Op.KK.KkClue.ordinal(), "006600", Op.kk);
/*   48 */     Op.updateOption(Op.KK.KkGuide.ordinal(), "880000", Op.kk);
/*   49 */     Op.updateOption(Op.KK.KkError.ordinal(), "FF0000", Op.kk);
/*   50 */     Op.updateOption(Op.KK.KkPuz.ordinal(), "sample.kakuro", Op.kk);
/*   51 */     Op.updateOption(Op.KK.KkGrid.ordinal(), "kakuro1.grid", Op.kk);
/*   52 */     Op.updateOption(Op.KK.KkFont.ordinal(), "SansSerif", Op.kk);
/*   53 */     Op.updateOption(Op.KK.KkGuideFont.ordinal(), "SansSerif", Op.kk);
/*   54 */     Op.updateOption(Op.KK.KkClueFont.ordinal(), "SansSerif", Op.kk);
/*   55 */     Op.updateOption(Op.KK.KkPuzColor.ordinal(), "false", Op.kk);
/*   56 */     Op.updateOption(Op.KK.KkSolColor.ordinal(), "false", Op.kk);
/*   57 */     Op.updateOption(Op.KK.KkMode.ordinal(), "0", Op.kk);
/*      */   }
/*      */   
/*   60 */   String kakuroHelp = "<div>A KAKURO puzzle is constructed on a crossword grid just like a standard crossword, but the digits 1 to 9 are used instead of the letters of the alphabet. In a standard KAKURO puzzle, the Across and Down clues are simply the <b>sums</b> of the digits in the across and down <b>words</b>. A variant of this standard is to use the <b>products</b> of the digits for the clues. Crossword Express will make both of these variants.<br/><br/></div><span class='parhead'>Menu Functions</span><ul><li/><span class='s'>File Menu</span><ul><li/><span class='menusub'>Load a Puzzle</span><br/>Use this option to choose your puzzle from the pool of KAKURO puzzles currently available on your computer.<p/><li/><span class='menusub'>SaveAs</span><br/>This option allows you to make an exact copy of the current puzzle using a different file name. The copy will be saved in the <b>kakuro</b> folder along with all of the Kakuro puzzles you have made. Alternatively, if you don't enter a new name for the puzzle, you can change the Puzzle Description, or any of the other descriptive items without changing the puzzle name.<p/><li/><span class='menusub'>Quit Construction</span><br/>Returns you to the Crossword Express opening screen.</ul><li/><span class='s'>Build Menu</span><ul><li/><span class='menusub'>Start a New Puzzle</span><br/>This option presents you with a dialog into which you can enter a file name for your new puzzle. You can also enter several other pieces of information such as a <b>Puzzle Title, Author</b> and <b>Copyright</b> information.<p/><li/><span class='menusub'>Build Options</span><br/>Use this option to access a Build Options dialog where you can set some rules to be followed by the puzzle building function as it builds your puzzle.<p/><li/><span class='menusub'>Select a Grid</span><br/>Use this option to select the grid which is to be used for the puzzle. You will probably need to make use of the Grid Maintenance function to create suitable grids, and to prevent confusion, it may be a good idea to adopt a naming convention for your KAKURO grids. For example you could begin their names with K_ or something similar. If you are intending to make a series of puzzles (see Kakuro Options), then you can select a number of grids...simply hold down the Control key as you click-select them with the mouse. Subsequently when you make the puzzles, the grids you have selected will be used sequentially as the puzzles are built.<p/><li/><span class='menusub'>Start Building / Stop Building</span><br/>Construction of the puzzle will commence when you select the <b>Start Building</b> option. If puzzle building is successful you will receive a message containing the name of the puzzle file, and the location where it was saved. If it becomes necessary, you can interrupt the building process by selecting this option a second time. Note that during the construction phase, the text of this option is changed to <b>Stop Building</b></ul><li/><span class='s'>View Menu</span><ul><li/><span class='menusub'>Display Options</span><br/>This leads you to a dialog box in which you can change the colors of various elements within the puzzle, and control the fonts which will be used for the puzzle's text components. You can also decide if printing of the puzzle will be done in black and white or in color.</ul><li/><span class='s'>Export Menu</span><br/><ul><li/><span>Print a Kakuro KDP puzzle book.</span><br/>The letters KDP stand for <b>Kindle Direct Publishing</b>. This is a free publishing service operared by Amazon, in which they handle all matters related to printing, advertising and sales of books created by members of the public. A portion of the proceeds are retained by Amazon while the remainder is paid to the author. Fifteen of the Puzzles created by Crossword Express can be printed into PDF format files ready for publication by Amazon. When you select this option, you will be presented with a dialog which allows you to control the process. Please study the Help offered by this dialog before attempting to make use of it.</ul><li/><span class='s'>Tasks Menu</span><ul><li/><span class='menusub'>Print this Puzzle</span><br/>This will take you to a custom print screen where you can control the details involved with printing your puzzle.<p/><li/><span class='menusub'>Solve this Puzzle</span><br/>This will take you to a Solve screen which provides a fully interactive environment for solving the puzzle.<p/><li/><span class='menusub'>Delete this Puzzle</span><br/>Use this option to eliminate unwanted KAKURO puzzles from your file system.</ul><li/><span class='s'>Help Menu</span><ul><li/><span class='menusub'>Kakuro Help</span><br/>Displays the Help screen which you are now reading.</ul></ul></body>";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  130 */   String kakuroOptions = "<div>Before you give the command to build the <b>Kakuro</b> puzzle, there are several options which you may care to alter. </div><br/><ul><li/>There are two varieties of Kakuro puzzle, depending on whether the clues represent the sum or the product of the digits within the \"words\". You can select which of these two options will be used when the puzzle is made using the <b>Puzzle Mode</b> drop down list.<p/><li/>If you want to make a number of puzzles all having the same dimensions, simply type a number into the <b>How many puzzles</b> input field. When you issue the Make command, Crossword Express will make that number of puzzles. The puzzle names will be numbers which represent a date in <b>yyyymmdd</b> format. The default value presented by Crossword Express is always the current date, but you can change this to any date that suits your needs. As the series of puzzles is created, CWE will automatically step on to the next date in the sequence, taking into account such factors as the varying number of days in the months, and of course leap years. Virtually any number of puzzles can be made in a single operation using this feature.<p/><li/><b>HOWEVER:</b> If you prefer a simpler numbering scheme for your puzzles, you can enter any number of 7 digits or less to be used for your first puzzle, and Crossword Express will number the remainder of the puzzles sequentially starting with your number.<p/><li/>If you do choose to make multiple puzzles, then by default, Crossword Express will change the difficulty of the resulting puzzles over a cycle of seven puzzles. This would be useful for a daily newspaper so that the week could start with a very easy puzzle, with quite difficult puzzles reserved for the weekend. If you don't want this feature, clearing the <b>Vary Difficulty on 7 day cycle</b> check-box will disable it.</ul></body>";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   KakuroBuild(JFrame jf, boolean auto, int hm, int start) {
/*  154 */     Def.puzzleMode = 100;
/*  155 */     Def.building = 0;
/*  156 */     Def.dispGuideDigits = Boolean.valueOf(false); Def.dispCursor = Boolean.valueOf(true);
/*      */ 
/*      */     
/*  159 */     makeGrid();
/*      */     
/*  161 */     jfKakuro = new JFrame("Kakuro");
/*  162 */     if (Op.getInt(Op.KK.KkH.ordinal(), Op.kk) > Methods.scrH - 200) {
/*  163 */       int diff = Op.getInt(Op.KK.KkH.ordinal(), Op.kk) - Op.getInt(Op.KK.KkW.ordinal(), Op.kk);
/*  164 */       Op.setInt(Op.KK.KkH.ordinal(), Methods.scrH - 200, Op.kk);
/*  165 */       Op.setInt(Op.KK.KkW.ordinal(), Methods.scrH - 200 + diff, Op.kk);
/*      */     } 
/*  167 */     jfKakuro.setSize(Op.getInt(Op.KK.KkW.ordinal(), Op.kk), Op.getInt(Op.KK.KkH.ordinal(), Op.kk));
/*  168 */     int frameX = (jf.getX() + jfKakuro.getWidth() > Methods.scrW) ? (Methods.scrW - jfKakuro.getWidth() - 10) : jf.getX();
/*  169 */     jfKakuro.setLocation(frameX, jf.getY());
/*  170 */     jfKakuro.setLayout((LayoutManager)null);
/*  171 */     jfKakuro.setDefaultCloseOperation(0);
/*      */     
/*  173 */     jfKakuro
/*  174 */       .addComponentListener(new ComponentAdapter() {
/*      */           public void componentResized(ComponentEvent ce) {
/*  176 */             int oldw = Op.getInt(Op.KK.KkW.ordinal(), Op.kk);
/*  177 */             int oldh = Op.getInt(Op.KK.KkH.ordinal(), Op.kk);
/*  178 */             Methods.frameResize(KakuroBuild.jfKakuro, oldw, oldh, 500, 580);
/*  179 */             Op.setInt(Op.KK.KkW.ordinal(), KakuroBuild.jfKakuro.getWidth(), Op.kk);
/*  180 */             Op.setInt(Op.KK.KkH.ordinal(), KakuroBuild.jfKakuro.getHeight(), Op.kk);
/*  181 */             KakuroBuild.restoreFrame();
/*      */           }
/*      */         });
/*      */ 
/*      */     
/*  186 */     jfKakuro
/*  187 */       .addWindowListener(new WindowAdapter() {
/*      */           public void windowClosing(WindowEvent we) {
/*  189 */             if (Def.building == 1 || Def.selecting)
/*  190 */               return;  Op.saveOptions("kakuro.opt", Op.kk);
/*  191 */             CrosswordExpress.transfer(1, KakuroBuild.jfKakuro);
/*      */           }
/*      */         });
/*      */     
/*  195 */     Methods.closeHelp();
/*      */ 
/*      */     
/*  198 */     Runnable buildThread = () -> {
/*      */         boolean res = true;
/*      */         
/*      */         if (this.howMany == 1) {
/*      */           res = buildKakuro();
/*      */         } else {
/*      */           multiBuild();
/*      */           if (this.sixpack) {
/*      */             Sixpack.trigger();
/*      */             jfKakuro.dispose();
/*      */             Def.building = 0;
/*      */             return;
/*      */           } 
/*      */         } 
/*      */         this.buildMenuItem.setText("Start Building");
/*      */         if (Def.building == 2) {
/*      */           Def.building = 0;
/*      */           Methods.interrupted(jfKakuro);
/*      */           makeGrid();
/*      */           restoreFrame();
/*      */           return;
/*      */         } 
/*      */         if (res) {
/*      */           Methods.puzzleSaved(jfKakuro, "kakuro", Op.kk[Op.KK.KkPuz.ordinal()]);
/*      */           Methods.havePuzzle = true;
/*      */         } else {
/*      */           JOptionPane.showMessageDialog(jfKakuro, "<html>It is not possible to build this puzzle.<br><center>Please read the Help.");
/*      */         } 
/*      */         restoreFrame();
/*      */         Def.building = 0;
/*      */       };
/*  229 */     jl1 = new JLabel(); jfKakuro.add(jl1);
/*  230 */     jl2 = new JLabel(); jfKakuro.add(jl2);
/*      */ 
/*      */     
/*  233 */     menuBar = new JMenuBar();
/*  234 */     menuBar.setBackground(Def.COLOR_MENUBAR);
/*  235 */     jfKakuro.setJMenuBar(menuBar);
/*      */     
/*  237 */     this.menu = new JMenu("File");
/*  238 */     menuBar.add(this.menu);
/*  239 */     this.menuItem = new JMenuItem("Load a Puzzle");
/*  240 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(76, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  241 */     this.menu.add(this.menuItem);
/*  242 */     this.menuItem
/*  243 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1)
/*      */             return; 
/*      */           pp.invalidate();
/*      */           pp.repaint();
/*      */           new Select(jfKakuro, "kakuro", "kakuro", Op.kk, Op.KK.KkPuz.ordinal(), false);
/*      */         });
/*  250 */     this.menuItem = new JMenuItem("SaveAs");
/*  251 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(65, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  252 */     this.menu.add(this.menuItem);
/*  253 */     this.menuItem
/*  254 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           Methods.puzzleDescriptionDialog(jfKakuro, Op.kk[Op.KK.KkPuz.ordinal()].substring(0, Op.kk[Op.KK.KkPuz.ordinal()].indexOf(".kakuro")), "kakuro", ".kakuro");
/*      */           if (Methods.clickedOK) {
/*      */             saveKakuro(Op.kk[Op.KK.KkPuz.ordinal()] = Methods.theFileName);
/*      */             restoreFrame();
/*      */             Methods.puzzleSaved(jfKakuro, "kakuro", Op.kk[Op.KK.KkPuz.ordinal()]);
/*      */           } 
/*      */         });
/*  265 */     this.menuItem = new JMenuItem("Quit Construction");
/*  266 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(81, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  267 */     this.menu.add(this.menuItem);
/*  268 */     this.menuItem
/*  269 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           
/*      */           Op.saveOptions("kakuro.opt", Op.kk);
/*      */           CrosswordExpress.transfer(1, jfKakuro);
/*      */         });
/*  277 */     this.menu = new JMenu("Build");
/*  278 */     menuBar.add(this.menu);
/*  279 */     this.menuItem = new JMenuItem("Start a new Puzzle");
/*  280 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(78, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  281 */     this.menu.add(this.menuItem);
/*  282 */     this.menuItem
/*  283 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           Methods.puzzleDescriptionDialog(jfKakuro, Op.kk[Op.KK.KkPuz.ordinal()].substring(0, Op.kk[Op.KK.KkPuz.ordinal()].indexOf(".kakuro")), "kakuro", ".kakuro");
/*      */           if (Methods.clickedOK) {
/*      */             Op.kk[Op.KK.KkPuz.ordinal()] = Methods.theFileName;
/*      */             makeGrid();
/*      */           } 
/*      */           restoreFrame();
/*      */         });
/*  294 */     this.menuItem = new JMenuItem("Build Options");
/*  295 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(79, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  296 */     this.menu.add(this.menuItem);
/*  297 */     this.menuItem
/*  298 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1)
/*      */             return; 
/*      */           kakuroOptions();
/*      */           if (Methods.clickedOK) {
/*      */             makeGrid();
/*      */             if (this.howMany > 1)
/*      */               Op.kk[Op.KK.KkPuz.ordinal()] = "" + this.startPuz + ".kakuro"; 
/*      */           } 
/*      */           restoreFrame();
/*      */         });
/*  309 */     this.menuItem = new JMenuItem("Select a Grid");
/*  310 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(71, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  311 */     this.menu.add(this.menuItem);
/*  312 */     this.menuItem
/*  313 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           Def.puzzleMode = 2;
/*      */           JFileChooser chooser = new JFileChooser(System.getProperty("user.dir") + "/grids");
/*      */           chooser.setFileFilter(new FileNameExtensionFilter("Grid", new String[] { "grid" }));
/*      */           chooser.setSelectedFile(new File(Op.kk[Op.KK.KkGrid.ordinal()]));
/*      */           chooser.setAccessory(new Preview(chooser));
/*      */           chooser.setMultiSelectionEnabled(true);
/*      */           if (chooser.showDialog(jfKakuro, "Select Grid") == 0) {
/*      */             this.grids = chooser.getSelectedFiles();
/*      */           }
/*      */           if (this.grids != null) {
/*      */             Op.kk[Op.KK.KkGrid.ordinal()] = this.grids[0].getName();
/*      */           }
/*      */           Grid.clearGrid();
/*      */           Grid.loadGrid(Op.kk[Op.KK.KkGrid.ordinal()]);
/*      */           Def.puzzleMode = 100;
/*      */           restoreFrame();
/*      */         });
/*  334 */     this.buildMenuItem = new JMenuItem("Start Building");
/*  335 */     this.buildMenuItem.setAccelerator(KeyStroke.getKeyStroke(66, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  336 */     this.menu.add(this.buildMenuItem);
/*  337 */     this.buildMenuItem
/*  338 */       .addActionListener(ae -> {
/*      */           if (Op.kk[Op.KK.KkPuz.ordinal()].length() == 0 && this.howMany == 1) {
/*      */             Methods.noName(jfKakuro);
/*      */             
/*      */             return;
/*      */           } 
/*      */           
/*      */           if (Def.building == 0) {
/*      */             this.thread = new Thread(paramRunnable);
/*      */             
/*      */             this.thread.start();
/*      */             Def.building = 1;
/*      */             this.buildMenuItem.setText("Stop Building");
/*      */           } else {
/*      */             Def.building = 2;
/*      */           } 
/*      */         });
/*  355 */     this.menu = new JMenu("View");
/*  356 */     menuBar.add(this.menu);
/*  357 */     this.menuItem = new JMenuItem("Display Options");
/*  358 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(89, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  359 */     this.menu.add(this.menuItem);
/*  360 */     this.menuItem
/*  361 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           
/*      */           printOptions(jfKakuro, "Display Options");
/*      */           restoreFrame();
/*      */         });
/*  369 */     this.menu = new JMenu("Export");
/*  370 */     menuBar.add(this.menu);
/*  371 */     this.menuItem = new JMenuItem("Print a Kakuro KDP puzzle book.");
/*  372 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(75, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  373 */     this.menu.add(this.menuItem);
/*  374 */     this.menuItem
/*  375 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           
/*      */           Methods.printKdpDialog(jfKakuro, 100, 6);
/*      */         });
/*  382 */     this.menu = new JMenu("Tasks");
/*  383 */     menuBar.add(this.menu);
/*  384 */     this.menuItem = new JMenuItem("Print this Puzzle");
/*  385 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(80, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  386 */     this.menu.add(this.menuItem);
/*  387 */     this.menuItem
/*  388 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           CrosswordExpress.toPrint(jfKakuro, Op.kk[Op.KK.KkPuz.ordinal()]);
/*      */         });
/*  394 */     this.menuItem = new JMenuItem("Solve this Puzzle");
/*  395 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(86, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  396 */     this.menu.add(this.menuItem);
/*  397 */     this.menuItem
/*  398 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1)
/*      */             return; 
/*      */           if (Methods.havePuzzle) {
/*      */             CrosswordExpress.transfer(101, jfKakuro);
/*      */           } else {
/*      */             Methods.noPuzzle(jfKakuro, "Solve");
/*      */           } 
/*      */         });
/*  407 */     this.menuItem = new JMenuItem("Delete this Puzzle");
/*  408 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(68, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  409 */     this.menu.add(this.menuItem);
/*  410 */     this.menuItem
/*  411 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           
/*      */           if (Methods.deleteAPuzzle(jfKakuro, Op.kk[Op.KK.KkPuz.ordinal()], "kakuro", pp)) {
/*      */             makeGrid();
/*      */             loadKakuro(Op.kk[Op.KK.KkPuz.ordinal()]);
/*      */             restoreFrame();
/*      */           } 
/*      */         });
/*  422 */     this.menu = new JMenu("Help");
/*  423 */     menuBar.add(this.menu);
/*      */     
/*  425 */     this.menuItem = new JMenuItem("Kakuro Help");
/*  426 */     this.menu.add(this.menuItem);
/*  427 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(72, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  428 */     this.menuItem
/*  429 */       .addActionListener(ae -> Methods.cweHelp(jfKakuro, null, "Building Kakuro Puzzles", this.kakuroHelp));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  434 */     pp = new KakuroPP(0, 37);
/*  435 */     jfKakuro.add(pp);
/*      */     
/*  437 */     pp
/*  438 */       .addMouseMotionListener(new MouseAdapter() {
/*      */           public void mouseMoved(MouseEvent e) {
/*  440 */             if (Def.isMac) {
/*  441 */               KakuroBuild.jfKakuro.setResizable((KakuroBuild.jfKakuro.getWidth() - e.getX() < 15 && KakuroBuild.jfKakuro
/*  442 */                   .getHeight() - e.getY() < 95));
/*      */             }
/*      */           }
/*      */         });
/*      */     
/*  447 */     loadKakuro(Op.kk[Op.KK.KkPuz.ordinal()]);
/*  448 */     restoreFrame();
/*      */ 
/*      */     
/*  451 */     ActionListener timerAL = ae -> {
/*      */         this.myTimer.stop();
/*      */         this.thread = new Thread(paramRunnable);
/*      */         this.thread.start();
/*      */         Def.building = 1;
/*      */       };
/*  457 */     this.myTimer = new Timer(1000, timerAL);
/*      */     
/*  459 */     if (auto) {
/*  460 */       this.sixpack = true;
/*  461 */       this.howMany = hm; this.startPuz = start;
/*  462 */       this.myTimer.start();
/*      */     } 
/*      */   }
/*      */   
/*      */   static void restoreFrame() {
/*  467 */     Insets insets = jfKakuro.getInsets();
/*  468 */     panelW = jfKakuro.getWidth() - insets.left + insets.right;
/*  469 */     panelH = jfKakuro.getHeight() - insets.top + insets.bottom + 37 + menuBar.getHeight();
/*  470 */     pp.setSize(panelW, panelH);
/*  471 */     jfKakuro.requestFocusInWindow();
/*  472 */     pp.repaint();
/*  473 */     jfKakuro.setVisible(true);
/*  474 */     Methods.infoPanel(jl1, jl2, "Build Kakuro", "Puzzle : " + Op.kk[Op.KK.KkPuz.ordinal()], panelW);
/*      */   }
/*      */   
/*      */   static void setSizesAndOffsets(int x, int y, int width, int height, int inset) {
/*  478 */     int i = (int)((width - inset) / (Grid.xSz + 0.5D));
/*  479 */     int j = (int)((height - inset) / (Grid.ySz + 0.5D));
/*  480 */     Grid.xCell = Grid.yCell = (i < j) ? i : j;
/*  481 */     Grid.xOrg = (Def.puzzleMode == 8) ? (x + (width - (int)((Grid.xSz - 0.5D) * Grid.xCell)) / 2) : (x + 10 + Grid.xCell / 2);
/*  482 */     Grid.yOrg = (Def.puzzleMode == 8) ? (y + (height - (int)((Grid.ySz - 0.5D) * Grid.yCell)) / 2) : (y + 10 + Grid.yCell / 2);
/*      */   }
/*      */ 
/*      */   
/*      */   private void kakuroOptions() {
/*  487 */     String[] mode = { "  Sum", "  Product" };
/*      */     
/*  489 */     JDialog jdlgKakuro = new JDialog(jfKakuro, "Kakuro Options", true);
/*  490 */     jdlgKakuro.setSize(270, 265);
/*  491 */     jdlgKakuro.setResizable(false);
/*  492 */     jdlgKakuro.setLayout((LayoutManager)null);
/*  493 */     jdlgKakuro.setLocation(jfKakuro.getX(), jfKakuro.getY());
/*      */     
/*  495 */     jdlgKakuro
/*  496 */       .addWindowListener(new WindowAdapter() {
/*      */           public void windowClosing(WindowEvent we) {
/*  498 */             Methods.closeHelp();
/*      */           }
/*      */         });
/*      */     
/*  502 */     Methods.closeHelp();
/*      */     
/*  504 */     JLabel jlMode = new JLabel("Puzzle Mode");
/*  505 */     jlMode.setForeground(Def.COLOR_LABEL);
/*  506 */     jlMode.setSize(80, 24);
/*  507 */     jlMode.setLocation(50, 12);
/*  508 */     jlMode.setHorizontalAlignment(0);
/*  509 */     jdlgKakuro.add(jlMode);
/*      */     
/*  511 */     JComboBox<String> jcbbMode = new JComboBox<>(mode);
/*  512 */     jcbbMode.setSize(100, 24);
/*  513 */     jcbbMode.setLocation(140, 12);
/*  514 */     jdlgKakuro.add(jcbbMode);
/*  515 */     jcbbMode.setBackground(Def.COLOR_BUTTONBG);
/*  516 */     jcbbMode.setSelectedIndex(Op.getInt(Op.KK.KkMode.ordinal(), Op.kk));
/*      */     
/*  518 */     HowManyPuzzles hmp = new HowManyPuzzles(jdlgKakuro, 10, 45, this.howMany, this.startPuz, true);
/*      */     
/*  520 */     JButton jbOK = Methods.cweButton("OK", 10, 162, 80, 26, null);
/*  521 */     jbOK.addActionListener(e -> {
/*      */           this.howMany = Integer.parseInt(paramHowManyPuzzles.jtfHowMany.getText());
/*      */           this.startPuz = Integer.parseInt(paramHowManyPuzzles.jtfStartPuz.getText());
/*      */           Op.setBool(Op.SX.VaryDiff.ordinal(), Boolean.valueOf(paramHowManyPuzzles.jcbVaryDiff.isSelected()), Op.sx);
/*      */           Methods.clickedOK = true;
/*      */           Op.setInt(Op.KK.KkMode.ordinal(), paramJComboBox.getSelectedIndex(), Op.kk);
/*      */           paramJDialog.dispose();
/*      */           Methods.closeHelp();
/*      */         });
/*  530 */     jdlgKakuro.add(jbOK);
/*      */     
/*  532 */     JButton jbCancel = Methods.cweButton("Cancel", 10, 197, 80, 26, null);
/*  533 */     jbCancel.addActionListener(e -> {
/*      */           Methods.clickedOK = false;
/*      */           paramJDialog.dispose();
/*      */           Methods.closeHelp();
/*      */         });
/*  538 */     jdlgKakuro.add(jbCancel);
/*      */     
/*  540 */     JButton jbHelp = Methods.cweButton("<html><font size=6 color=BB0000 face=Serif>Help ", 100, 162, 150, 61, new ImageIcon("graphics/help.png"));
/*  541 */     jbHelp.addActionListener(e -> Methods.cweHelp(null, paramJDialog, "Kakuro Options", this.kakuroOptions));
/*      */     
/*  543 */     jdlgKakuro.add(jbHelp);
/*      */     
/*  545 */     jdlgKakuro.getRootPane().setDefaultButton(jbOK);
/*  546 */     Methods.setDialogSize(jdlgKakuro, 260, 233);
/*      */   }
/*      */   
/*      */   static void printOptions(JFrame jf, String type) {
/*  550 */     String[] colorLabel = { "Puzzle Background", "Cell Color", "Clue Background", "Number Color", "Clue Color", "Guide Digit Color", "Error Color" };
/*  551 */     int[] colorInt = { Op.KK.KkBg.ordinal(), Op.KK.KkCell.ordinal(), Op.KK.KkClueBg.ordinal(), Op.KK.KkNumber.ordinal(), Op.KK.KkClue.ordinal(), Op.KK.KkGuide.ordinal(), Op.KK.KkError.ordinal() };
/*  552 */     String[] fontLabel = { "Puzzle Font", "Guide Digit Font", "Clue Font" };
/*  553 */     int[] fontInt = { Op.KK.KkFont.ordinal(), Op.KK.KkGuideFont.ordinal(), Op.KK.KkClueFont.ordinal() };
/*  554 */     String[] checkLabel = { "PPrint Puzzle with color.", "SPrint Solution with color." };
/*  555 */     int[] checkInt = { Op.KK.KkPuzColor.ordinal(), Op.KK.KkSolColor.ordinal() };
/*  556 */     Methods.stdPrintOptions(jf, "Kakuro " + type, Op.kk, colorLabel, colorInt, fontLabel, fontInt, checkLabel, checkInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void saveKakuro(String kakuroName) {
/*      */     try {
/*  566 */       DataOutputStream dataOut = new DataOutputStream(new FileOutputStream("kakuro/" + kakuroName));
/*  567 */       dataOut.writeInt(Grid.xSz);
/*  568 */       dataOut.writeInt(Grid.ySz);
/*  569 */       dataOut.writeByte(Methods.noReveal);
/*  570 */       dataOut.writeByte(Methods.noErrors);
/*  571 */       for (int k = 0; k < 54; k++)
/*  572 */         dataOut.writeByte(0); 
/*  573 */       for (int j = 0; j < Grid.ySz; j++) {
/*  574 */         for (int m = 0; m < Grid.xSz; m++) {
/*  575 */           dataOut.writeInt(Grid.mode[m][j]);
/*  576 */           dataOut.writeInt(Grid.sol[m][j]);
/*  577 */           dataOut.writeInt(Grid.letter[m][j]);
/*      */         } 
/*  579 */       }  dataOut.writeUTF(Methods.puzzleTitle);
/*  580 */       dataOut.writeUTF(Methods.author);
/*  581 */       dataOut.writeUTF(Methods.copyright);
/*  582 */       dataOut.writeUTF(Methods.puzzleNumber);
/*  583 */       dataOut.writeUTF(Methods.puzzleNotes);
/*  584 */       for (int i = 0; i < NodeList.nodeListLength; i++)
/*  585 */         dataOut.writeInt((NodeList.nodeList[i]).iClue); 
/*  586 */       dataOut.close();
/*      */     }
/*  588 */     catch (IOException exc) {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void loadKakuro(String kakuroName) {
/*      */     
/*  596 */     try { File fl = new File("kakuro/" + kakuroName);
/*  597 */       if (!fl.exists()) {
/*  598 */         fl = new File("kakuro/");
/*  599 */         String[] s = fl.list(); int k;
/*  600 */         for (k = 0; k < s.length && (
/*  601 */           s[k].lastIndexOf(".kakuro") == -1 || s[k].charAt(0) == '.'); k++);
/*      */         
/*  603 */         if (k == s.length) { makeGrid(); return; }
/*  604 */          kakuroName = s[k];
/*  605 */         Op.kk[Op.KK.KkPuz.ordinal()] = kakuroName;
/*      */       } 
/*      */       
/*  608 */       DataInputStream dataIn = new DataInputStream(new FileInputStream("kakuro/" + kakuroName));
/*  609 */       Grid.xSz = dataIn.readInt();
/*  610 */       Grid.ySz = dataIn.readInt();
/*  611 */       Methods.noReveal = dataIn.readByte();
/*  612 */       Methods.noErrors = dataIn.readByte(); int i;
/*  613 */       for (i = 0; i < 54; i++)
/*  614 */         dataIn.readByte(); 
/*  615 */       for (int j = 0; j < Grid.ySz; j++) {
/*  616 */         for (i = 0; i < Grid.xSz; i++) {
/*  617 */           Grid.mode[i][j] = dataIn.readInt();
/*  618 */           Grid.sol[i][j] = dataIn.readInt();
/*  619 */           Grid.letter[i][j] = dataIn.readInt();
/*      */         } 
/*  621 */       }  Methods.puzzleTitle = dataIn.readUTF();
/*  622 */       Methods.author = dataIn.readUTF();
/*  623 */       Methods.copyright = dataIn.readUTF();
/*  624 */       Methods.puzzleNumber = dataIn.readUTF();
/*  625 */       Methods.puzzleNotes = dataIn.readUTF();
/*  626 */       NodeList.buildNodeList();
/*  627 */       for (i = 0; i < NodeList.nodeListLength; i++)
/*  628 */         (NodeList.nodeList[i]).iClue = dataIn.readInt(); 
/*  629 */       dataIn.close(); }
/*      */     
/*  631 */     catch (IOException exc) { return; }
/*  632 */      Methods.havePuzzle = true;
/*      */   }
/*      */   
/*      */   static void drawKakuro(Graphics2D g2, int[][] puzzleArray) {
/*  636 */     int[] xPoints = new int[4], yPoints = new int[4];
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  641 */     Stroke normalStroke = new BasicStroke(Grid.xCell / 25.0F, 2, 2);
/*  642 */     Stroke diagStroke = new BasicStroke(Grid.xCell / 25.0F, 1, 2);
/*      */     
/*  644 */     RenderingHints rh = g2.getRenderingHints();
/*  645 */     rh.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
/*  646 */     g2.setRenderingHints(rh);
/*      */ 
/*      */     
/*  649 */     g2.setColor(new Color(Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.KK.KkBg.ordinal(), Op.kk) : 0));
/*  650 */     int h = Grid.yCell, w = Grid.xCell;
/*  651 */     g2.fillRect(Grid.xOrg - w / 2, Grid.yOrg - h / 2, Grid.xSz * w + w / 2, Grid.ySz * h + h / 2);
/*      */ 
/*      */     
/*  654 */     g2.setStroke(diagStroke); int j;
/*  655 */     for (j = Grid.ySz - 1; j >= 0; j--) {
/*  656 */       for (int i = Grid.xSz - 1; i >= 0; i--) {
/*  657 */         if (Grid.mode[i][j] == 0) {
/*  658 */           g2.setColor(new Color(Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.KK.KkCell.ordinal(), Op.kk) : 16777215));
/*  659 */           int x = Grid.xOrg + i * w;
/*  660 */           int y = Grid.yOrg + j * h;
/*  661 */           g2.fillRect(x, y, w, h);
/*  662 */           g2.setColor(new Color(Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.KK.KkClueBg.ordinal(), Op.kk) : 13421772));
/*  663 */           xPoints[0] = x; yPoints[0] = y;
/*  664 */           xPoints[1] = x + w; yPoints[1] = y;
/*  665 */           xPoints[2] = x + w / 2; yPoints[2] = y - h / 2;
/*  666 */           xPoints[3] = x - w / 2; yPoints[3] = y - h / 2;
/*  667 */           g2.fillPolygon(xPoints, yPoints, 4);
/*  668 */           xPoints[0] = x; yPoints[0] = y;
/*  669 */           xPoints[1] = x; yPoints[1] = y + h;
/*  670 */           xPoints[2] = x - w / 2; yPoints[2] = y + h / 2;
/*  671 */           xPoints[3] = x - w / 2; yPoints[3] = y - h / 2;
/*  672 */           g2.fillPolygon(xPoints, yPoints, 4);
/*      */           
/*  674 */           g2.setColor(new Color(Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.KK.KkBg.ordinal(), Op.kk) : 0));
/*  675 */           int inc = (i == 0 || j == 0) ? 0 : -5;
/*  676 */           g2.drawLine(x, y, x + inc - w / 2, y + inc - h / 2);
/*  677 */           inc = (j == 0) ? 0 : -2;
/*  678 */           g2.drawLine(x + w, y, x + w + inc - w / 2, y + inc - h / 2);
/*  679 */           inc = (i == 0) ? 0 : -2;
/*  680 */           g2.drawLine(x, y + h, x + inc - w / 2, y + h + inc - h / 2);
/*      */         } 
/*      */       } 
/*      */     } 
/*  684 */     g2.setColor(new Color(Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.KK.KkNumber.ordinal(), Op.kk) : 0));
/*  685 */     g2.setFont(new Font(Op.kk[Op.KK.KkFont.ordinal()], 0, 8 * Grid.yCell / 10));
/*  686 */     FontMetrics fm = g2.getFontMetrics();
/*  687 */     for (j = 0; j < Grid.ySz; j++) {
/*  688 */       for (int i = 0; i < Grid.xSz; i++) {
/*  689 */         char ch = (char)puzzleArray[i][j];
/*  690 */         if (Character.isDigit(ch)) {
/*  691 */           int x = Grid.xOrg + i * w;
/*  692 */           int y = Grid.yOrg + j * h;
/*  693 */           g2.drawString("" + ch, x + (w - fm.stringWidth("" + ch)) / 2, y + (h + fm.getAscent() - fm.getDescent()) / 2);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  699 */     g2.setColor(new Color(Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.KK.KkClue.ordinal(), Op.kk) : 0)); int fontSize;
/*  700 */     g2.setFont(new Font(Op.kk[Op.KK.KkClueFont.ordinal()], 0, fontSize = Grid.yCell / 3));
/*  701 */     fm = g2.getFontMetrics();
/*  702 */     for (int loop = 0; loop < 2; loop++) {
/*  703 */       for (int z = 0; z < NodeList.nodeListLength; z++) { boolean proceed; 
/*  704 */         try { proceed = ((NodeList.nodeList[z]).direction == 0); }
/*  705 */         catch (Exception e) { proceed = false; }
/*  706 */          if (proceed) {
/*  707 */           int x = Grid.xOrg + w * ((NodeList.nodeList[z]).x - 1);
/*  708 */           int y = Grid.yOrg + h * (NodeList.nodeList[z]).y;
/*  709 */           if ((NodeList.nodeList[z]).iClue > 0) {
/*  710 */             String s = "" + (NodeList.nodeList[z]).iClue + " ";
/*  711 */             while (fm.stringWidth(s) > 45 * Grid.xCell / 100) {
/*  712 */               fontSize--;
/*  713 */               fm = g2.getFontMetrics();
/*  714 */               g2.setFont(new Font(Op.kk[Op.KK.KkClueFont.ordinal()], 0, fontSize));
/*      */             } 
/*  716 */             if (loop == 1)
/*  717 */               g2.drawString(s, x + w - fm.stringWidth(s), y + fm.getAscent() + 1); 
/*      */           } 
/*      */         }  
/*  720 */         try { proceed = ((NodeList.nodeList[z]).direction == 1); }
/*  721 */         catch (Exception e) { proceed = false; }
/*  722 */          if (proceed) {
/*  723 */           int x = Grid.xOrg + w * (NodeList.nodeList[z]).x;
/*  724 */           int y = Grid.yOrg + h * ((NodeList.nodeList[z]).y - 1);
/*  725 */           if ((NodeList.nodeList[z]).iClue > 0) {
/*  726 */             String s = " " + (NodeList.nodeList[z]).iClue;
/*  727 */             while (fm.stringWidth(s) > 45 * Grid.xCell / 100) {
/*  728 */               fontSize--;
/*  729 */               fm = g2.getFontMetrics();
/*  730 */               g2.setFont(new Font(Op.kk[Op.KK.KkClueFont.ordinal()], 0, fontSize));
/*      */             } 
/*  732 */             if (loop == 1) {
/*  733 */               g2.drawString(s, x, y + 14 * h / 15);
/*      */             }
/*      */           } 
/*      */         }  }
/*      */     
/*      */     } 
/*  739 */     g2.setStroke(normalStroke);
/*  740 */     g2.setColor(new Color(Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.KK.KkBg.ordinal(), Op.kk) : 0));
/*  741 */     for (j = 0; j < Grid.ySz; j++) {
/*  742 */       for (int i = 0; i < Grid.xSz; i++) {
/*  743 */         if (Grid.mode[i][j] == 0)
/*  744 */         { int x = Grid.xOrg + i * w;
/*  745 */           int y = Grid.yOrg + j * h;
/*  746 */           g2.drawRect(x, y, w, h); } 
/*      */       } 
/*  748 */     }  g2.drawRect(Grid.xOrg - w / 2, Grid.yOrg - h / 2, Grid.xSz * w + w / 2, Grid.ySz * h + h / 2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  756 */     g2.setStroke(new BasicStroke(1.0F));
/*      */   }
/*      */   
/*      */   static void printPuz(Graphics2D g2, int left, int top, int width, int height) {
/*  760 */     loadKakuro(Op.kk[Op.KK.KkPuz.ordinal()]);
/*  761 */     setSizesAndOffsets(left, top, width, height, 0);
/*  762 */     Methods.clearGrid(Grid.sol);
/*  763 */     Def.dispWithColor = Op.getBool(Op.KK.KkPuzColor.ordinal(), Op.kk);
/*  764 */     boolean mem = Def.dispGuideDigits.booleanValue(); Def.dispGuideDigits = Boolean.valueOf(false);
/*  765 */     KakuroSolve.drawKakuro(g2, Grid.sol);
/*  766 */     Def.dispGuideDigits = Boolean.valueOf(mem);
/*  767 */     Def.dispWithColor = Boolean.valueOf(true);
/*      */   }
/*      */   
/*      */   static void printSol(Graphics2D g2, int left, int top, int width, int height, String solutionPuzzle) {
/*  771 */     loadKakuro(solutionPuzzle);
/*  772 */     setSizesAndOffsets(left, top, width, height, 0);
/*  773 */     Def.dispWithColor = Op.getBool(Op.KK.KkSolColor.ordinal(), Op.kk);
/*  774 */     drawKakuro(g2, Grid.letter);
/*  775 */     Def.dispWithColor = Boolean.valueOf(true);
/*  776 */     loadKakuro(Op.kk[Op.KK.KkPuz.ordinal()]);
/*      */   }
/*      */   
/*      */   static void printSolTitle(Graphics2D g2, int left, int top, int width, int height, String solutionPuzzle) {
/*  780 */     loadKakuro(solutionPuzzle);
/*  781 */     Print.outputTextItem(g2, left, top, width, height, "SansSerif", 0, Methods.puzzleTitle);
/*  782 */     loadKakuro(Op.kk[Op.KK.KkPuz.ordinal()]);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static void printSixpackPuz(Graphics2D g2, int left, int top, int dim, int gap, String puzName) {
/*  788 */     FontMetrics fm = g2.getFontMetrics();
/*      */     
/*  790 */     String st = Op.sx[Op.SX.SxKk.ordinal()];
/*  791 */     if (st.length() < 3) st = "KAKURO"; 
/*  792 */     int w = fm.stringWidth(st);
/*  793 */     g2.drawString(st, left + (dim - w) / 2, top - gap / 3);
/*  794 */     KakuroSolve.loadKakuro(puzName + ".kakuro");
/*  795 */     setSizesAndOffsets(left, top, dim, dim, 0);
/*  796 */     Methods.clearGrid(Grid.sol);
/*  797 */     KakuroSolve.drawKakuro(g2, Grid.sol);
/*  798 */     if (Op.sx[Op.SX.SxRuleLang.ordinal()].equals("English")) {
/*  799 */       st = rules;
/*      */     } else {
/*  801 */       st = Op.kk[Op.KK.KkRule1.ordinal() + Op.getInt(Op.SX.SxRuleLangIndex.ordinal(), Op.sx) - 1];
/*  802 */     }  if (Op.getBool(Op.SX.SxInstructions.ordinal(), Op.sx).booleanValue()) {
/*  803 */       Methods.renderText(g2, left, top + dim + dim / 50, dim, dim / 4, "SansSerif", 1, st, 3, 4, true, 0, 0);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static void printSixpackSol(Graphics2D g2, int left, int top, int dim, int gap, String solName) {
/*  809 */     FontMetrics fm = g2.getFontMetrics();
/*      */     
/*  811 */     String st = Op.sx[Op.SX.SxKk.ordinal()];
/*  812 */     if (st.length() < 3) st = "KAKURO"; 
/*  813 */     int w = fm.stringWidth(st);
/*  814 */     g2.drawString(st, left + (dim - w) / 2, top - gap / 3);
/*  815 */     loadKakuro(solName + ".kakuro");
/*  816 */     setSizesAndOffsets(left, top, dim, dim, 0);
/*  817 */     drawKakuro(g2, Grid.letter);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static void printKDPPuz(Graphics2D g2, int left, int top, int dim, int gap, String puzName) {
/*  823 */     FontMetrics fm = g2.getFontMetrics();
/*      */     
/*  825 */     String st = puzName;
/*  826 */     int w = fm.stringWidth(st);
/*  827 */     g2.drawString(st, left + (dim - w) / 2, top - gap / 3);
/*  828 */     KakuroSolve.loadKakuro(puzName + ".kakuro");
/*  829 */     setSizesAndOffsets(left, top, dim, dim, 0);
/*  830 */     Methods.clearGrid(Grid.sol);
/*  831 */     KakuroSolve.drawKakuro(g2, Grid.sol);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static void printKDPSol(Graphics2D g2, int left, int top, int dim, int gap, String solName) {
/*  837 */     FontMetrics fm = g2.getFontMetrics();
/*      */     
/*  839 */     String st = solName;
/*  840 */     int w = fm.stringWidth(st);
/*  841 */     g2.drawString(st, left + (dim - w) / 2, top - gap / 3);
/*  842 */     loadKakuro(solName + ".kakuro");
/*  843 */     setSizesAndOffsets(left, top, dim, dim, 0);
/*  844 */     drawKakuro(g2, Grid.letter);
/*      */   }
/*      */   
/*      */   static void makeGrid() {
/*  848 */     Methods.havePuzzle = false;
/*  849 */     Grid.clearGrid();
/*  850 */     Grid.loadGrid(Op.kk[Op.KK.KkGrid.ordinal()]);
/*  851 */     Grid.xCur = Grid.yCur = 0;
/*  852 */     while (Grid.mode[Grid.xCur][Grid.yCur] != 0) {
/*      */       Grid.xCur++;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateKakuroNode(int item) {
/*  859 */     NodeList.buildTemplate(item); int i, count;
/*  860 */     for (count = Op.getInt(Op.KK.KkMode.ordinal(), Op.kk), i = 0; i < (NodeList.nodeList[item]).length; i++) {
/*  861 */       int val = (NodeList.nodeList[item]).template.charAt(i) - 48;
/*  862 */       if (Op.getInt(Op.KK.KkMode.ordinal(), Op.kk) == 1) { count *= val; }
/*  863 */       else { count += val; }
/*      */     
/*  865 */     }  (NodeList.nodeList[item]).iClue = count;
/*      */   }
/*      */   
/*  868 */   byte[][] prior = new byte[9][10]; byte[][] post = new byte[9][10]; byte[] kakList = new byte[10];
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void permutation(int len, int start) {
/*  874 */     int j = start + 1;
/*  875 */     for (int i = start; i < len; i++) {
/*  876 */       if (this.prior[start][this.kakList[start]] != 0)
/*  877 */         if (len - start == 2) {
/*  878 */           if (this.prior[j][this.kakList[j]] != 0)
/*  879 */             for (int m = 0; m < len; m++) {
/*  880 */               this.post[m][this.kakList[m]] = 1;
/*      */             } 
/*      */         } else {
/*  883 */           permutation(len, j);
/*      */         }   int k; byte mem;
/*  885 */       for (mem = this.kakList[start], k = start + 1; k < len; k++)
/*  886 */         this.kakList[k - 1] = this.kakList[k]; 
/*  887 */       this.kakList[len - 1] = mem;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean combination(int item) {
/*  893 */     byte[] group = new byte[10];
/*  894 */     boolean ret = false;
/*      */     int i;
/*  896 */     for (i = 0; i < 10; group[i++] = 0);
/*  897 */     int len = (NodeList.nodeList[item]).length;
/*  898 */     int target = (NodeList.nodeList[item]).iClue;
/*      */     
/*  900 */     for (i = 0; i < len; i++) {
/*  901 */       for (int j = 0; j < 10; j++) {
/*  902 */         this.prior[i][j] = Grid.xstatus[((NodeList.nodeList[item]).cellLoc[i]).x][((NodeList.nodeList[item]).cellLoc[i]).y][j];
/*  903 */         this.post[i][j] = 0;
/*      */       } 
/*      */     } 
/*  906 */     i = group[0] = 0; int count = Op.getInt(Op.KK.KkMode.ordinal(), Op.kk); while (true) {
/*  907 */       group[i] = (byte)(group[i] + 1);
/*  908 */       if (Op.getInt(Op.KK.KkMode.ordinal(), Op.kk) == 1) {
/*  909 */         if (group[i] > 1)
/*  910 */           count /= group[i] - 1; 
/*  911 */         count *= group[i];
/*      */       } else {
/*      */         
/*  914 */         count++;
/*  915 */       }  if (group[i] == 10 || count > target) {
/*  916 */         if (i == 0)
/*      */           break; 
/*  918 */         if (Op.getInt(Op.KK.KkMode.ordinal(), Op.kk) == 1) { count /= group[i]; }
/*  919 */         else { count -= group[i]; }
/*  920 */          i--;
/*      */         continue;
/*      */       } 
/*  923 */       if (i < len - 1) {
/*  924 */         i++;
/*  925 */         group[i] = group[i - 1];
/*  926 */         if (Op.getInt(Op.KK.KkMode.ordinal(), Op.kk) == 1) {
/*  927 */           count *= group[i]; continue;
/*      */         } 
/*  929 */         count += group[i];
/*      */         continue;
/*      */       } 
/*  932 */       if (count == target) {
/*  933 */         for (int j = 0; j < len; j++)
/*  934 */           this.kakList[j] = group[j]; 
/*  935 */         permutation(len, 0);
/*      */       } 
/*      */     } 
/*      */     
/*  939 */     for (i = 0; i < len; i++) {
/*  940 */       for (int j = 1; j < 10; j++) {
/*  941 */         if (Grid.xstatus[((NodeList.nodeList[item]).cellLoc[i]).x][((NodeList.nodeList[item]).cellLoc[i]).y][j] != this.post[i][j]) {
/*  942 */           Grid.xstatus[((NodeList.nodeList[item]).cellLoc[i]).x][((NodeList.nodeList[item]).cellLoc[i]).y][j] = this.post[i][j];
/*  943 */           ret = true;
/*      */         } 
/*      */       } 
/*  946 */     }  return ret;
/*      */   }
/*      */ 
/*      */   
/*      */   public int uniqueKakuro() {
/*  951 */     boolean cont = true;
/*      */     int j;
/*  953 */     for (j = 0; j < Grid.ySz; j++) {
/*  954 */       for (int i = 0; i < Grid.xSz; i++) {
/*  955 */         for (int k = 0; k < 10; k++)
/*  956 */           Grid.xstatus[i][j][k] = 1; 
/*      */       } 
/*  958 */     }  int count; for (count = 0; cont; count++) {
/*  959 */       int i; for (cont = false, i = 0; i < NodeList.nodeListLength; i++)
/*  960 */         cont |= combination(i); 
/*      */     } 
/*  962 */     for (count = j = 0; j < Grid.ySz; j++) {
/*  963 */       for (int i = 0; i < Grid.xSz; i++) {
/*  964 */         if (Grid.horz[i][j] != -1 || Grid.vert[i][j] != -1) {
/*  965 */           count--; int k, cellCount;
/*  966 */           for (cellCount = 0, k = 1; k < 10; k++) {
/*  967 */             if (Grid.xstatus[i][j][k] != 0)
/*  968 */               cellCount++; 
/*  969 */           }  count += cellCount;
/*  970 */           Grid.color[i][j] = (cellCount > 1) ? 13434828 : 16777215;
/*      */         } 
/*      */       } 
/*      */     } 
/*  974 */     return count;
/*      */   }
/*      */   
/*      */   public boolean minimiseFlaws() {
/*  978 */     int bestCount = 10000;
/*      */     
/*  980 */     Random r = new Random();
/*      */     
/*  982 */     for (int z = 0; z < 6; z++) {
/*  983 */       for (int y = 0; y < Grid.ySz; y++) {
/*  984 */         for (int x = 0; x < Grid.xSz; x++) {
/*  985 */           if ((Grid.horz[x][y] != -1 || Grid.vert[x][y] != -1) && Grid.color[x][y] != 16777215) {
/*  986 */             String s = "";
/*  987 */             if (Grid.vert[x][y] != -1) s = s + (NodeList.nodeList[Grid.vert[x][y]]).template; 
/*  988 */             if (Grid.horz[x][y] != -1) s = s + (NodeList.nodeList[Grid.horz[x][y]]).template;  int j, i, bestChar, oldChar;
/*  989 */             for (bestChar = 0, oldChar = Grid.letter[x][y], i = 49 + r.nextInt(9), j = 0; j < 9; j++, i = (i == 57) ? 49 : (i + 1)) {
/*  990 */               if (s.indexOf(i) == -1 || oldChar == i) {
/*  991 */                 Grid.letter[x][y] = i;
/*  992 */                 if (Grid.vert[x][y] != -1) updateKakuroNode(Grid.vert[x][y]); 
/*  993 */                 if (Grid.horz[x][y] != -1) updateKakuroNode(Grid.horz[x][y]); 
/*  994 */                 int unique = uniqueKakuro();
/*  995 */                 if (unique <= bestCount) {
/*  996 */                   bestCount = unique;
/*  997 */                   bestChar = i;
/*      */                 } 
/*      */               } 
/*      */             } 
/* 1001 */             Grid.letter[x][y] = (bestChar > 0) ? bestChar : oldChar;
/* 1002 */             if (Grid.vert[x][y] != -1) updateKakuroNode(Grid.vert[x][y]); 
/* 1003 */             if (Grid.horz[x][y] != -1) updateKakuroNode(Grid.horz[x][y]); 
/*      */           } 
/* 1005 */         }  uniqueKakuro();
/* 1006 */         if (bestCount == 0)
/* 1007 */           return true; 
/* 1008 */         restoreFrame();
/* 1009 */         if (Def.building == 2)
/* 1010 */           return false; 
/*      */       } 
/*      */     } 
/* 1013 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean preFillKakuro() {
/* 1019 */     Random r = new Random();
/*      */     
/* 1021 */     for (int y = 0; y < Grid.ySz; y++) {
/* 1022 */       for (int x = 0; x < Grid.xSz; x++) {
/* 1023 */         if (Grid.horz[x][y] != -1 || Grid.vert[x][y] != -1)
/* 1024 */         { String s = " ";
/* 1025 */           if (Grid.vert[x][y] != -1)
/* 1026 */             s = s + (NodeList.nodeList[Grid.vert[x][y]]).template; 
/* 1027 */           if (Grid.horz[x][y] != -1)
/* 1028 */             s = s + (NodeList.nodeList[Grid.horz[x][y]]).template;  int j;
/* 1029 */           for (int i = 1 + r.nextInt(9); j < 9; j++, i = (i == 9) ? 1 : (i + 1)) {
/* 1030 */             char ch = (char)(48 + i);
/* 1031 */             if (s.indexOf(ch) == -1) {
/* 1032 */               Grid.letter[x][y] = ch;
/* 1033 */               if (Grid.vert[x][y] != -1) updateKakuroNode(Grid.vert[x][y]); 
/* 1034 */               if (Grid.horz[x][y] != -1) updateKakuroNode(Grid.horz[x][y]); 
/*      */               break;
/*      */             } 
/*      */           } 
/* 1038 */           if (j == 9) return false; 
/* 1039 */           Grid.color[x][y] = 13434828; } 
/*      */       } 
/* 1041 */     }  return true;
/*      */   }
/*      */   
/*      */   private void multiBuild() {
/* 1045 */     String title = Methods.puzzleTitle;
/* 1046 */     String[] grid = { "kakuro1.grid", "kakuro2.grid", "kakuro3.grid", "kakuro4.grid", "kakuro5.grid", "kakuro6.grid", "kakuro7.grid" };
/*      */     
/* 1048 */     String saveKakuroGrid = Op.kk[Op.KK.KkGrid.ordinal()];
/*      */     
/* 1050 */     SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
/* 1051 */     Calendar c = Calendar.getInstance();
/*      */     
/* 1053 */     for (this.hmCount = 0; this.hmCount < this.howMany; this.hmCount++) {
/* 1054 */       if (this.startPuz > 9999999) { try {
/* 1055 */           c.setTime(sdf.parse("" + this.startPuz));
/* 1056 */         } catch (ParseException ex) {}
/* 1057 */         this.startPuz = Integer.parseInt(sdf.format(c.getTime())); }
/*      */       
/* 1059 */       Methods.puzzleTitle = "KAKURO Puzzle : " + this.startPuz;
/* 1060 */       if (Op.getBool(Op.SX.VaryDiff.ordinal(), Op.sx).booleanValue()) {
/* 1061 */         Op.kk[Op.KK.KkGrid.ordinal()] = grid[(this.startPuz - 1) % 7];
/*      */       }
/* 1063 */       if (this.grids != null) {
/* 1064 */         Grid.loadGrid(this.grids[this.hmCount % this.grids.length].getName());
/*      */       } else {
/* 1066 */         Grid.loadGrid(Op.kk[Op.KK.KkGrid.ordinal()]);
/*      */       } 
/* 1068 */       Methods.buildProgress(jfKakuro, Op.kk[Op.KK.KkPuz
/* 1069 */             .ordinal()] = "" + this.startPuz + ".kakuro");
/* 1070 */       buildKakuro();
/* 1071 */       restoreFrame();
/* 1072 */       Wait.shortWait(100);
/* 1073 */       if (Def.building == 2)
/*      */         return; 
/* 1075 */       this.startPuz++;
/*      */     } 
/* 1077 */     this.howMany = 1;
/* 1078 */     Methods.puzzleTitle = title;
/* 1079 */     Op.kk[Op.KK.KkGrid.ordinal()] = saveKakuroGrid;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean buildKakuro() {
/* 1085 */     for (int j = 0; j < Grid.ySz; j++) {
/* 1086 */       for (int k = 0; k < Grid.xSz; k++)
/* 1087 */         Grid.letter[k][j] = 0; 
/* 1088 */     }  for (int i = 0;; i++) {
/* 1089 */       if (i == 10) return false; 
/* 1090 */       while (!preFillKakuro());
/* 1091 */       if (minimiseFlaws())
/*      */         break; 
/* 1093 */       if (Def.building == 2)
/* 1094 */         return false; 
/* 1095 */       if (this.howMany == 1) {
/* 1096 */         restoreFrame();
/* 1097 */         Methods.buildProgress(jfKakuro, Op.kk[Op.KK.KkPuz.ordinal()]);
/*      */       } 
/*      */     } 
/* 1100 */     saveKakuro(Op.kk[Op.KK.KkPuz.ordinal()]);
/* 1101 */     return true;
/*      */   }
/*      */   
/*      */   void updateGrid(MouseEvent e) {
/* 1105 */     int x = e.getX(), y = e.getY();
/*      */     
/* 1107 */     if (Def.building == 1)
/* 1108 */       return;  if (x < Grid.xOrg || y < Grid.yOrg)
/*      */       return; 
/* 1110 */     x = (x - Grid.xOrg) / Grid.xCell;
/* 1111 */     y = (y - Grid.yOrg) / Grid.yCell;
/* 1112 */     if (x >= Grid.xSz || y >= Grid.ySz)
/*      */       return; 
/* 1114 */     if (Grid.mode[x][y] == 0) {
/* 1115 */       Grid.xCur = x; Grid.yCur = y;
/* 1116 */     }  restoreFrame();
/*      */   }
/*      */   
/*      */   void handleKeyPressed(KeyEvent e) {
/*      */     int i;
/*      */     char ch;
/* 1122 */     if (Def.building == 1)
/* 1123 */       return;  if (e.isAltDown())
/* 1124 */       return;  switch (e.getKeyCode()) { case 38:
/* 1125 */         for (i = Grid.yCur - 1; i >= 0 && Grid.mode[Grid.xCur][i] > 0; i--); if (i >= 0) Grid.yCur = i;  break;
/* 1126 */       case 40: for (i = Grid.yCur + 1; i < Grid.ySz && Grid.mode[Grid.xCur][i] > 0; i++); if (i < Grid.ySz) Grid.yCur = i;  break;
/* 1127 */       case 37: for (i = Grid.xCur - 1; i >= 0 && Grid.mode[i][Grid.yCur] > 0; i--); if (i >= 0) Grid.xCur = i;  break;
/* 1128 */       case 39: for (i = Grid.xCur + 1; i < Grid.xSz && Grid.mode[i][Grid.yCur] > 0; i++); if (i < Grid.xSz) Grid.xCur = i;  break;
/* 1129 */       case 36: for (i = 0; Grid.mode[i][Grid.yCur] > 0; i++); Grid.xCur = i; break;
/* 1130 */       case 35: for (i = Grid.xSz - 1; Grid.mode[i][Grid.yCur] > 0; i--); Grid.xCur = i; break;
/* 1131 */       case 33: for (i = 0; Grid.mode[Grid.xCur][i] > 0; i++); Grid.yCur = i; break;
/* 1132 */       case 34: for (i = Grid.ySz - 1; Grid.mode[Grid.xCur][i] > 0; i--); Grid.yCur = i; break;
/*      */       case 8:
/*      */       case 32:
/*      */       case 127:
/* 1136 */         Grid.letter[Grid.xCur][Grid.yCur] = 0;
/*      */         break;
/*      */       default:
/* 1139 */         ch = e.getKeyChar();
/* 1140 */         if (ch > '0' && ch <= '9')
/* 1141 */           Grid.letter[Grid.xCur][Grid.yCur] = ch; 
/*      */         break; }
/*      */     
/* 1144 */     restoreFrame();
/*      */   }
/*      */ }


/* Location:              C:\Users\mustapha\Desktop\Crossword-Express.jar!\crosswordexpress\KakuroBuild.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */