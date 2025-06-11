/*      */ package crosswordexpress;
/*      */ import java.awt.Color;
/*      */ import java.awt.Font;
/*      */ import java.awt.FontMetrics;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.Insets;
/*      */ import java.awt.RenderingHints;
/*      */ import java.awt.Stroke;
/*      */ import java.awt.Toolkit;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.io.DataInputStream;
/*      */ import java.io.DataOutputStream;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import javax.swing.JFileChooser;
/*      */ import javax.swing.JFrame;
/*      */ import javax.swing.JLabel;
/*      */ import javax.swing.JMenu;
/*      */ import javax.swing.JMenuItem;
/*      */ import javax.swing.JOptionPane;
/*      */ import javax.swing.KeyStroke;
/*      */ 
/*      */ public final class DevanagariBuild {
/*      */   static JFrame jfDevanagari;
/*      */   static JMenuBar menuBar;
/*      */   JMenu menu;
/*   28 */   int thisColor = 56797; JMenu submenu; JMenuItem menuItem; JMenuItem buildMenuItem; static JTextArea jtaTheClue; JScrollPane jsp; static JPanel pp; static int panelW; static int panelH;
/*      */   static JLabel jl1;
/*      */   static JLabel jl2;
/*      */   JButton jbButton;
/*      */   Thread thread;
/*      */   File[] grids;
/*      */   static JPanel jpDevClue;
/*   35 */   static int[] wordCount = new int[50];
/*   36 */   static int[][][] dWord = new int[50][][];
/*      */ 
/*      */   
/*   39 */   static int[][] revLink = new int[50][];
/*   40 */   static boolean[][] busy = new boolean[50][];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   53 */   String crosswordHelp = "<span class='m'>Menu Functions</span><br/><br/><ul><li><span class='s'>File Menu</span><ul><li><span>Select a Dictionary</span><br/>When loading a new puzzle into the Build screen, you begin by selecting the dictionary which was used to build the CROSSWORD puzzle which you want to load. Since this Crossword Express function deals only with Devanagari languages, the only dictionaries offered for selection are those beginning with the letters <b>dev</b><p/><li><span>Load a Puzzle</span><br/>Then you choose your puzzle from the pool of CROSSWORD puzzles currently available in the selected dictionary.<p/><li><span>Save</span><br/>If you have done some manual editing of the puzzle clues, this option will save those changes under the existing file name.<p/><li><span>SaveAs</span><br/>This option allows you to make an exact copy of the current puzzle using a different file name. The copy will be saved in the folder of the dictionary that was used to construct it. Alternatively, if you don't enter a new name for the puzzle, you can change the Puzzle Description, or any of the other descriptive items without changing the puzzle name.<p/><li><span>Quit Construction</span><br/>Returns you to the Crossword Express opening screen.</ul><li><span class='s'>Build Menu</span><ul><li/><span >Start a New Puzzle</span><br/>This option presents you with a dialog into which you can enter a file name for your new puzzle. You can also enter several other pieces of optional information such as a <b>Puzzle Title, Author</b> and <b>Copyright</b> information.<p/><li/><span>Select a Dictionary</span><br/>Use this option to select the dictionary which you want to use to build the new CROSSWORD puzzle. Please note that since your are building puzzles in a Devanagari based language, you will only be offered Devanagari based dictionaries. The names of these dictionaries all commence with the letters <b>dev</b><p/><li/><span>Select a Grid</span><br/>Crossword puzzles are built on a predefined grid, which you can select using this option. Please note that Devanagari based languages tend to have much shorter words than most other languages. This in turn requires that the grids to be used in the construction of puzzles contain mostly quite short words. A set of suitable grids is bundled with the program, and all of these begin with the letters <b>dev</b>.<p/><li/><span>Start Building / Stop Building</span><br/> Construction of the puzzle will commence when you select the <b>Start Building</b> option. If puzzle building is successful you will receive a message containing the name of the puzzle file, and the location where it was saved. If it becomes necessary, you can interrupt the building process by selecting this option a second time. Note that during the construction phase, the text of this option is changed to <b>Stop Building.</b><p/><br>Following a successful build, you can step through the words of the puzzle and type in any missing clues, or edit existing clues.</ul><li/><span class='s'>View Menu</span><ul><li/><span>Display Options</span><br/>This leads you to a dialog box in which you can change the colors of various elements within the puzzle, and control the fonts which will be used for the puzzle's text components. You can also decide if printing of the puzzle will be done in black and white or in color.</ul><li/><span class='s'>Print Menu</span><br/><ul><li/><span>Print Crossword</span><br/>The current puzzle can be printed in standard CROSSWORD format.<p/></ul><li/><span class='s'>Tasks Menu</span><ul><li/><span>Delete this Puzzle</span><br/>Use this option to eliminate unwanted CROSSWORD puzzles from your file system.<p/><li/><span>Transfer Puzzle Clues to Dictionary</span><br/>This option will update the current dictionary to reflect any changes you may have made to the clues during the puzzle construction phase.<p/><li/><span>Rebuild the Current Puzzle</span><br/>Use of this option will not affect the words within the puzzle grid, but will refresh the content of the clues. The clues will then reflect any changes which have been made to the dictionary clues since the time the puzzle was first constructed.</ul><li/><span class='s'>Help Menu</span><ul><li/><span>Crossword Help</span><br/>Displays the Help screen which you are now reading.</ul></ul></ul></body>";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int links;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int contax;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int contax2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int earlyLink;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int form;
/*      */ 
/*      */ 
/*      */ 
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
/*  129 */     Op.updateOption(Op.DV.DvPuz.ordinal(), "sample1.crossword", Op.dv);
/*  130 */     Op.updateOption(Op.DV.DvTemplate.ordinal(), "", Op.dv);
/*  131 */     Op.updateOption(Op.DV.DvDic.ordinal(), "devNepali", Op.dv);
/*  132 */     Op.updateOption(Op.DV.DvW.ordinal(), "600", Op.dv);
/*  133 */     Op.updateOption(Op.DV.DvH.ordinal(), "790", Op.dv);
/*  134 */     Op.updateOption(Op.DV.DvAcross.ordinal(), "10", Op.dv);
/*  135 */     Op.updateOption(Op.DV.DvDown.ordinal(), "10", Op.dv);
/*  136 */     Op.updateOption(Op.DV.DvGrid.ordinal(), "am13-1.grid", Op.dv);
/*  137 */     Op.updateOption(Op.DV.DvCellC.ordinal(), "FFFFFF", Op.dv);
/*  138 */     Op.updateOption(Op.DV.DvPatternC.ordinal(), "000000", Op.dv);
/*  139 */     Op.updateOption(Op.DV.DvGridC.ordinal(), "000000", Op.dv);
/*  140 */     Op.updateOption(Op.DV.DvLettersC.ordinal(), "000000", Op.dv);
/*  141 */     Op.updateOption(Op.DV.DvIDC.ordinal(), "000000", Op.dv);
/*  142 */     Op.updateOption(Op.DV.DvClueFont.ordinal(), "SansSerif", Op.dv);
/*  143 */     Op.updateOption(Op.DV.DvErrorC.ordinal(), "000000", Op.dv);
/*  144 */     Op.updateOption(Op.DV.DvClueC.ordinal(), "000000", Op.dv);
/*      */   }
/*      */   
/*      */   DevanagariBuild(JFrame jfCWE) {
/*  148 */     Def.puzzleMode = 220;
/*  149 */     Def.dispSolArray = Boolean.valueOf(false);
/*  150 */     Def.dispCursor = Boolean.valueOf(true);
/*  151 */     Def.dispNullCells = Boolean.valueOf(true);
/*  152 */     Def.dispGuideDigits = Boolean.valueOf(true);
/*  153 */     Def.building = 0;
/*  154 */     Def.dispWithColor = Boolean.valueOf(true);
/*  155 */     makeGrid();
/*      */     
/*  157 */     jfDevanagari = new JFrame("Devanagari Construction");
/*  158 */     if (Op.getInt(Op.DV.DvH.ordinal(), Op.dv) > Methods.scrH - 200) {
/*  159 */       int diff = Op.getInt(Op.DV.DvH.ordinal(), Op.dv) - Op.getInt(Op.DV.DvW.ordinal(), Op.dv);
/*  160 */       Op.setInt(Op.DV.DvH.ordinal(), Methods.scrH - 200, Op.dv);
/*  161 */       Op.setInt(Op.DV.DvW.ordinal(), Methods.scrH - 200 + diff, Op.dv);
/*      */     } 
/*  163 */     jfDevanagari.setSize(Op.getInt(Op.DV.DvW.ordinal(), Op.dv), Op.getInt(Op.DV.DvH.ordinal(), Op.dv));
/*  164 */     int frameX = (jfCWE.getX() + jfDevanagari.getWidth() > Methods.scrW) ? (Methods.scrW - jfDevanagari.getWidth() - 10) : jfCWE.getX();
/*  165 */     jfDevanagari.setLocation(frameX, jfCWE.getY());
/*  166 */     jfDevanagari.setLayout((LayoutManager)null);
/*  167 */     jfDevanagari.getContentPane().setBackground(Def.COLOR_FRAMEBG);
/*  168 */     jfDevanagari.setDefaultCloseOperation(0);
/*  169 */     jfDevanagari
/*  170 */       .addComponentListener(new ComponentAdapter() {
/*      */           public void componentResized(ComponentEvent ce) {
/*  172 */             int oldw = Op.getInt(Op.DV.DvW.ordinal(), Op.dv);
/*  173 */             int oldh = Op.getInt(Op.DV.DvH.ordinal(), Op.dv);
/*  174 */             Methods.frameResize(DevanagariBuild.jfDevanagari, oldw, oldh, 420, 600);
/*  175 */             Op.setInt(Op.DV.DvW.ordinal(), DevanagariBuild.jfDevanagari.getWidth(), Op.dv);
/*  176 */             Op.setInt(Op.DV.DvH.ordinal(), DevanagariBuild.jfDevanagari.getHeight(), Op.dv);
/*  177 */             DevanagariBuild.this.jsp.setSize(DevanagariBuild.jfDevanagari.getWidth() - 20, 60);
/*  178 */             DevanagariBuild.restoreFrame();
/*      */           }
/*      */         });
/*      */ 
/*      */     
/*  183 */     jfDevanagari
/*  184 */       .addWindowListener(new WindowAdapter() {
/*      */           public void windowClosing(WindowEvent we) {
/*  186 */             if (Def.building == 1 || Def.selecting)
/*      */               return; 
/*  188 */             if (Grid.nCur > -1)
/*  189 */               (NodeList.nodeList[Grid.nCur]).clue = DevanagariBuild.jtaTheClue.getText(); 
/*  190 */             DevanagariBuild.saveDevanagari(Op.dv[Op.DV.DvPuz.ordinal()]);
/*  191 */             Op.saveOptions("devanagari.opt", Op.dv);
/*  192 */             CrosswordExpress.transfer(1, DevanagariBuild.jfDevanagari);
/*      */           }
/*      */         });
/*      */     
/*  196 */     Methods.closeHelp();
/*      */     
/*  198 */     Runnable buildThread = () -> {
/*      */         Def.dispCursor = Boolean.valueOf(false);
/*      */         
/*      */         Methods.havePuzzle = false;
/*      */         
/*      */         focusColor(false);
/*      */         makeGrid();
/*      */         buildDevanagari(true);
/*      */         this.buildMenuItem.setText("Start Building");
/*      */         if (Def.building == 2) {
/*      */           Def.building = 0;
/*      */           Methods.interrupted(jfDevanagari);
/*      */           makeGrid();
/*      */           restoreFrame();
/*      */           return;
/*      */         } 
/*      */         Def.building = 0;
/*      */         if (Methods.havePuzzle) {
/*      */           saveDevanagari(Op.dv[Op.DV.DvPuz.ordinal()]);
/*      */           jtaTheClue.requestFocusInWindow();
/*      */           Grid.xCur = (NodeList.nodeList[2]).x;
/*      */           Grid.yCur = (NodeList.nodeList[2]).y;
/*      */           Grid.nCur = 2;
/*      */           Grid.xNew = (NodeList.nodeList[0]).x;
/*      */           Grid.yNew = (NodeList.nodeList[0]).y;
/*      */           Grid.nNew = 0;
/*      */           changeCursor();
/*      */           restoreFrame();
/*      */           Methods.puzzleSaved(jfDevanagari, Op.dv[Op.DV.DvDic.ordinal()] + ".dic", Op.dv[Op.DV.DvPuz.ordinal()]);
/*      */         } else {
/*      */           makeGrid();
/*      */           Methods.cantBuild(jfDevanagari);
/*      */         } 
/*      */       };
/*  232 */     jl1 = new JLabel();
/*  233 */     jfDevanagari.add(jl1);
/*  234 */     jl2 = new JLabel();
/*  235 */     jfDevanagari.add(jl2);
/*      */     
/*  237 */     menuBar = new JMenuBar();
/*  238 */     menuBar.setBackground(Def.COLOR_MENUBAR);
/*  239 */     jfDevanagari.setJMenuBar(menuBar);
/*      */     
/*  241 */     this.menu = new JMenu("<html><font color=AA0000 size=4><i>!! Read this first !!");
/*  242 */     menuBar.add(this.menu);
/*  243 */     this.menuItem = new JMenuItem("Alphabets and Abugidas");
/*  244 */     this.menu.add(this.menuItem);
/*  245 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(72, 1));
/*  246 */     this.menuItem
/*  247 */       .addActionListener(ae -> {
/*      */           browserLaunch.openURL(paramJFrame, "http://www.crauswords.com/abugida.html");
/*      */ 
/*      */           
/*      */           Methods.toFront(jfDevanagari);
/*      */         });
/*      */     
/*  254 */     this.menu = new JMenu("File");
/*  255 */     menuBar.add(this.menu);
/*  256 */     this.menuItem = new JMenuItem("Select a Dictionary");
/*  257 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(68, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  258 */     this.menu.add(this.menuItem);
/*  259 */     this.menuItem
/*  260 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           
/*      */           Methods.selectDictionary(jfDevanagari, Op.dv[Op.DV.DvDic.ordinal()], 4);
/*      */           Op.dv[Op.DV.DvDic.ordinal()] = Methods.dictionaryName;
/*      */           Grid.clearGrid();
/*      */           loadDevanagari(Op.dv[Op.DV.DvPuz.ordinal()]);
/*      */           focusColor(true);
/*      */           restoreFrame();
/*      */         });
/*  272 */     this.menuItem = new JMenuItem("Load a Puzzle");
/*  273 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(76, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  274 */     this.menu.add(this.menuItem);
/*  275 */     this.menuItem
/*  276 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           
/*      */           pp.invalidate();
/*      */           restoreFrame();
/*      */           focusColor(false);
/*      */           new Select(jfDevanagari, Op.dv[Op.DV.DvDic.ordinal()] + ".dic", "crossword", Op.dv, Op.DV.DvPuz.ordinal(), false);
/*      */         });
/*  286 */     this.menuItem = new JMenuItem("Save");
/*  287 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(83, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  288 */     this.menu.add(this.menuItem);
/*  289 */     this.menuItem
/*  290 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           
/*      */           saveDevanagari(Op.dv[Op.DV.DvPuz.ordinal()]);
/*      */           Methods.puzzleSaved(jfDevanagari, Op.dv[Op.DV.DvDic.ordinal()] + ".dic", Op.dv[Op.DV.DvPuz.ordinal()]);
/*      */           restoreFrame();
/*      */         });
/*  299 */     this.menuItem = new JMenuItem("SaveAs");
/*  300 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(65, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  301 */     this.menu.add(this.menuItem);
/*  302 */     this.menuItem
/*  303 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           
/*      */           Methods.puzzleDescriptionDialog(jfDevanagari, Op.dv[Op.DV.DvPuz.ordinal()].substring(0, Op.dv[Op.DV.DvPuz.ordinal()].indexOf(".crossword")), Op.dv[Op.DV.DvDic.ordinal()] + ".dic", ".crossword");
/*      */           if (Methods.clickedOK) {
/*      */             saveDevanagari(Op.dv[Op.DV.DvPuz.ordinal()] = Methods.theFileName);
/*      */             restoreFrame();
/*      */             Methods.puzzleSaved(jfDevanagari, Op.dv[Op.DV.DvDic.ordinal()] + ".dic", Op.dv[Op.DV.DvPuz.ordinal()]);
/*      */           } 
/*      */         });
/*  315 */     this.menuItem = new JMenuItem("Quit Construction");
/*  316 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(81, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  317 */     this.menu.add(this.menuItem);
/*  318 */     this.menuItem
/*  319 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           
/*      */           (NodeList.nodeList[Grid.nCur]).clue = jtaTheClue.getText();
/*      */           
/*      */           saveDevanagari(Op.dv[Op.DV.DvPuz.ordinal()]);
/*      */           Op.saveOptions("devanagari.opt", Op.dv);
/*      */           CrosswordExpress.transfer(1, jfDevanagari);
/*      */         });
/*  330 */     this.menu = new JMenu("Build");
/*  331 */     menuBar.add(this.menu);
/*  332 */     this.menuItem = new JMenuItem("Start a New Puzzle");
/*  333 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(78, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  334 */     this.menu.add(this.menuItem);
/*  335 */     this.menuItem
/*  336 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           
/*      */           Methods.puzzleDescriptionDialog(jfDevanagari, Op.dv[Op.DV.DvPuz.ordinal()].substring(0, Op.dv[Op.DV.DvPuz.ordinal()].indexOf(".crossword")), Op.dv[Op.DV.DvDic.ordinal()] + ".dic", ".crossword");
/*      */           if (Methods.clickedOK) {
/*      */             Op.dv[Op.DV.DvPuz.ordinal()] = Methods.theFileName;
/*      */             makeGrid();
/*      */             Grid.loadGrid(Op.msc[Op.MSC.GridName.ordinal()]);
/*      */           } 
/*      */           restoreFrame();
/*      */         });
/*  349 */     this.menuItem = new JMenuItem("Select a Dictionary");
/*  350 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(68, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  351 */     this.menu.add(this.menuItem);
/*  352 */     this.menuItem
/*  353 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           
/*      */           Methods.selectDictionary(jfDevanagari, Op.dv[Op.DV.DvDic.ordinal()], 4);
/*      */           Op.dv[Op.DV.DvDic.ordinal()] = Methods.dictionaryName;
/*      */           restoreFrame();
/*      */         });
/*  362 */     this.menuItem = new JMenuItem("Select a Grid");
/*  363 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(71, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  364 */     this.menu.add(this.menuItem);
/*  365 */     this.menuItem
/*  366 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           
/*      */           Methods.clearGrid(Grid.letter);
/*      */           focusColor(false);
/*      */           Def.dispCursor = Boolean.valueOf(false);
/*      */           Def.dispSolArray = Boolean.valueOf(true);
/*      */           Def.puzzleMode = 2;
/*      */           JFileChooser chooser = new JFileChooser(System.getProperty("user.dir") + "/grids");
/*      */           chooser.setFileFilter(new FileNameExtensionFilter("Grid", new String[] { "grid" }));
/*      */           chooser.setSelectedFile(new File(Op.msc[Op.MSC.GridName.ordinal()]));
/*      */           chooser.setAccessory(new Preview(chooser));
/*      */           chooser.setMultiSelectionEnabled(true);
/*      */           if (chooser.showDialog(jfDevanagari, "Select Grid") == 0) {
/*      */             this.grids = chooser.getSelectedFiles();
/*      */           }
/*      */           Def.puzzleMode = 4;
/*      */           if (this.grids != null) {
/*      */             Op.msc[Op.MSC.GridName.ordinal()] = this.grids[0].getName();
/*      */           }
/*      */           Grid.loadGrid(Op.msc[Op.MSC.GridName.ordinal()]);
/*      */           Def.dispSolArray = Boolean.valueOf(false);
/*      */           Def.dispCursor = Boolean.valueOf(true);
/*      */           focusColor(true);
/*      */           restoreFrame();
/*      */         });
/*  394 */     this.buildMenuItem = new JMenuItem("Start Building");
/*  395 */     this.buildMenuItem.setAccelerator(KeyStroke.getKeyStroke(66, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  396 */     this.menu.add(this.buildMenuItem);
/*  397 */     this.buildMenuItem
/*  398 */       .addActionListener(ae -> {
/*      */           if (Op.dv[Op.DV.DvPuz.ordinal()].length() == 0) {
/*      */             Methods.noName(jfDevanagari);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             return;
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
/*      */ 
/*      */ 
/*      */           
/*      */           if (Def.building == 0) {
/*      */             this.thread = new Thread(paramRunnable);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             this.thread.start();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             Def.building = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             this.buildMenuItem.setText("Stop Building");
/*      */           } else {
/*      */             Def.building = 2;
/*      */           } 
/*      */         });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  482 */     this.menu = new JMenu("View");
/*  483 */     menuBar.add(this.menu);
/*  484 */     this.menuItem = new JMenuItem("Display Options");
/*  485 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(89, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  486 */     this.menu.add(this.menuItem);
/*  487 */     this.menuItem
/*  488 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           
/*      */           printOptions(jfDevanagari, "Display Options");
/*      */           
/*      */           restoreFrame();
/*      */         });
/*  497 */     this.menu = new JMenu("Print");
/*  498 */     menuBar.add(this.menu);
/*  499 */     this.menuItem = new JMenuItem("Print Crossword");
/*  500 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(80, 1));
/*  501 */     this.menu.add(this.menuItem);
/*  502 */     this.menuItem
/*  503 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           Def.puzzleMode = 220;
/*      */           CrosswordExpress.toPrint(jfDevanagari, Op.dv[Op.DV.DvPuz.ordinal()]);
/*      */         });
/*  510 */     this.menuItem = new JMenuItem("Export Puzzle as Text");
/*  511 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(84, 1));
/*  512 */     this.menu.add(this.menuItem);
/*  513 */     this.menuItem
/*  514 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           
/*      */           if (Methods.havePuzzle) {
/*      */             NodeList.exportText(jfDevanagari, true);
/*      */           } else {
/*      */             Methods.noPuzzle(jfDevanagari, "Export");
/*      */           } 
/*      */         });
/*  525 */     this.menu = new JMenu("Tasks");
/*  526 */     menuBar.add(this.menu);
/*  527 */     this.menuItem = new JMenuItem("Delete this Puzzle");
/*  528 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(90, 8));
/*  529 */     this.menu.add(this.menuItem);
/*  530 */     this.menuItem
/*  531 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           
/*      */           if (Methods.deleteAPuzzle(jfDevanagari, Op.dv[Op.DV.DvPuz.ordinal()], Op.dv[Op.DV.DvDic.ordinal()] + ".dic", pp)) {
/*      */             loadDevanagari(Op.dv[Op.DV.DvPuz.ordinal()]);
/*      */             restoreFrame();
/*      */           } 
/*      */         });
/*  541 */     this.menuItem = new JMenuItem("Transfer Puzzle Clues to Dictionary");
/*  542 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(84, 8));
/*  543 */     this.menu.add(this.menuItem);
/*  544 */     this.menuItem
/*  545 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           
/*      */           if (!Methods.havePuzzle) {
/*      */             Methods.noPuzzle(jfDevanagari, "Move Clues");
/*      */             
/*      */             return;
/*      */           } 
/*      */           Methods.moveCluesToDic(Op.dv[Op.DV.DvDic.ordinal()]);
/*      */           JOptionPane.showMessageDialog(jfDevanagari, "<html><center>The clues used in this puzzle<br>have been moved to the <font color=880000>" + Op.dv[Op.DV.DvDic.ordinal()] + "</font> dictionary");
/*      */           restoreFrame();
/*      */         });
/*  559 */     this.menuItem = new JMenuItem("Rebuild the current puzzle");
/*  560 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(82, 8));
/*  561 */     this.menu.add(this.menuItem);
/*  562 */     this.menuItem
/*  563 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           
/*      */           if (!Methods.havePuzzle) {
/*      */             Methods.noPuzzle(jfDevanagari, "Rebuild");
/*      */             
/*      */             return;
/*      */           } 
/*      */           NodeList.attachClues(Op.dv[Op.DV.DvDic.ordinal()], Boolean.valueOf(false));
/*      */           saveDevanagari(Op.dv[Op.DV.DvPuz.ordinal()]);
/*      */           JOptionPane.showMessageDialog(jfDevanagari, "<html><center>The puzzle has been rebuilt and saved.");
/*      */           restoreFrame();
/*      */         });
/*  578 */     this.menu = new JMenu("Help");
/*  579 */     menuBar.add(this.menu);
/*  580 */     this.menuItem = new JMenuItem("Crossword Help");
/*  581 */     this.menu.add(this.menuItem);
/*  582 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(72, 1));
/*  583 */     this.menuItem
/*  584 */       .addActionListener(ae -> Methods.cweHelp(jfDevanagari, null, "Building Devanagari Crossword Puzzles", this.crosswordHelp));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  590 */     JLabel jl = new JLabel("This Clue");
/*  591 */     jl.setFont(new Font("SansSerif", 0, 18));
/*  592 */     jl.setForeground(Def.COLOR_LABEL);
/*  593 */     jl.setSize(125, 16);
/*  594 */     jl.setLocation(10, 50);
/*  595 */     jl.setHorizontalAlignment(2);
/*  596 */     jfDevanagari.add(jl);
/*      */     
/*  598 */     jtaTheClue = new JTextArea((NodeList.nodeList[Grid.nCur]).clue);
/*  599 */     jtaTheClue.setLineWrap(true);
/*  600 */     jtaTheClue.setWrapStyleWord(true);
/*  601 */     jtaTheClue.setFont(new Font("SansSerif", 0, 20));
/*  602 */     this.jsp = new JScrollPane(jtaTheClue);
/*  603 */     this.jsp.setSize(580, 60);
/*  604 */     this.jsp.setLocation(10, 70);
/*  605 */     jfDevanagari.add(this.jsp);
/*      */ 
/*      */     
/*  608 */     loadDevanagari(Op.dv[Op.DV.DvPuz.ordinal()]);
/*  609 */     jtaTheClue.setText((NodeList.nodeList[0]).clue);
/*  610 */     pp = new DevanagariPP(0, 135, jfDevanagari);
/*      */     
/*  612 */     pp
/*  613 */       .addMouseListener(new MouseAdapter() {
/*      */           public void mousePressed(MouseEvent e) {
/*  615 */             DevanagariBuild.this.updateGrid(e);
/*      */           }
/*      */         });
/*      */ 
/*      */     
/*  620 */     if (Def.isMac) {
/*  621 */       pp
/*  622 */         .addMouseMotionListener(new MouseAdapter() {
/*      */             public void mouseMoved(MouseEvent e) {
/*  624 */               if (Def.isMac) {
/*  625 */                 DevanagariBuild.jfDevanagari.setResizable((DevanagariBuild.jfDevanagari.getWidth() - e.getX() < 600 && DevanagariBuild.jfDevanagari
/*  626 */                     .getHeight() - e.getY() < 600));
/*      */               }
/*      */             }
/*      */           });
/*      */     }
/*  631 */     focusColor(true);
/*  632 */     restoreFrame();
/*      */   }
/*      */   
/*      */   static void restoreFrame() {
/*  636 */     jfDevanagari.setVisible(true);
/*  637 */     Insets insets = jfDevanagari.getInsets();
/*  638 */     panelW = jfDevanagari.getWidth() - insets.left + insets.right;
/*  639 */     panelH = jfDevanagari.getHeight() - insets.top + insets.bottom + 37 + menuBar.getHeight();
/*  640 */     pp.setSize(panelW, panelH);
/*  641 */     Def.dispSolArray = Boolean.valueOf(false);
/*  642 */     Def.dispCursor = Boolean.valueOf(true);
/*  643 */     Def.dispNullCells = Boolean.valueOf(true);
/*  644 */     jfDevanagari.requestFocusInWindow();
/*  645 */     pp.repaint();
/*  646 */     Methods.infoPanel(jl1, jl2, "Build देवनागरी", "Dictionary : " + Op.dv[Op.DV.DvDic.ordinal()] + "  -|-  Puzzle : " + Op.dv[Op.DV.DvPuz
/*  647 */           .ordinal()], jfDevanagari.getWidth());
/*  648 */     jtaTheClue.requestFocusInWindow();
/*      */   }
/*      */   
/*      */   static void setSizesAndOffsets(int x, int y, int width, int height, int inset, boolean print) {
/*  652 */     if (Def.puzzleMode == 12) {
/*  653 */       Grid.xSz++;
/*  654 */       Grid.ySz++;
/*      */     } 
/*  656 */     int i = (width - inset) / Grid.xSz;
/*  657 */     int j = (height - inset) / Grid.ySz;
/*  658 */     Grid.xCell = Grid.yCell = (i < j) ? i : j;
/*  659 */     Grid.xOrg = print ? (x + (width - Grid.xSz * Grid.xCell) / 2) : 10;
/*  660 */     Grid.yOrg = print ? (y + (height - Grid.ySz * Grid.yCell) / 2) : 10;
/*  661 */     if (Def.puzzleMode == 12) {
/*  662 */       Grid.xSz--;
/*  663 */       Grid.ySz--;
/*  664 */       Grid.xOrg += Grid.xCell;
/*  665 */       Grid.yOrg += Grid.yCell;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static String recoverDevStringFromInt(int devInt) {
/*  672 */     byte[] res = new byte[4];
/*  673 */     ByteBuffer.wrap(res).putInt(devInt);
/*  674 */     String returnVal = "";
/*      */     
/*  676 */     for (int i = 0; i < 4; i++) {
/*  677 */       if (res[i] != 0) {
/*  678 */         returnVal = returnVal + (char)((res[i] & Byte.MAX_VALUE) + 2304);
/*  679 */         if ((res[i] & 0x80) == 128)
/*  680 */           returnVal = returnVal + '्'; 
/*      */       } 
/*      */     } 
/*  683 */     return returnVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static String recoverDevStringFromDevLetterArray(int[] devLetter, int wLen) {
/*  689 */     String recWord = "";
/*      */     
/*  691 */     for (int i = 0; i < wLen; i++)
/*  692 */       recWord = recWord + recoverDevStringFromInt(devLetter[i]); 
/*  693 */     return recWord;
/*      */   }
/*      */   
/*      */   static boolean devInvalid(String word) {
/*  697 */     int state = 0;
/*  698 */     boolean badWord = false;
/*      */     
/*  700 */     for (int i = 0; i < word.length() && 
/*  701 */       word.charAt(i) != ' '; i++) {
/*  702 */       switch (state) {
/*      */         case 0:
/*  704 */           switch (devType(word.charAt(i))) {
/*      */             case 1:
/*      */               break;
/*      */             case 2:
/*  708 */               state = 1;
/*      */               break;
/*      */           } 
/*  711 */           badWord = true;
/*  712 */           System.out.println("0  " + word);
/*      */           break;
/*      */         
/*      */         case 1:
/*  716 */           switch (devType(word.charAt(i))) {
/*      */             case 1:
/*  718 */               state = 0;
/*      */               break;
/*      */             case 2:
/*      */               break;
/*      */             case 3:
/*  723 */               state = 0;
/*      */               break;
/*      */             case 4:
/*  726 */               state = 2;
/*      */               break;
/*      */           } 
/*  729 */           badWord = true;
/*  730 */           System.out.println("1  " + word);
/*      */           break;
/*      */         
/*      */         case 2:
/*  734 */           switch (devType(word.charAt(i))) {
/*      */             case 2:
/*  736 */               state = 1;
/*      */               break;
/*      */           } 
/*  739 */           badWord = true;
/*  740 */           System.out.println("2  " + word);
/*      */           break;
/*      */       } 
/*      */     } 
/*  744 */     return badWord;
/*      */   }
/*      */   
/*      */   static int devLength(String theWord) {
/*  748 */     int wLen = 0, state = 0;
/*      */     
/*  750 */     for (int i = 0; i < theWord.length() && 
/*  751 */       theWord.charAt(i) != ' '; i++) {
/*  752 */       switch (state) {
/*      */         case 0:
/*  754 */           switch (devType(theWord.charAt(i))) {
/*      */             case 1:
/*  756 */               wLen++;
/*      */               break;
/*      */             case 2:
/*  759 */               wLen++;
/*  760 */               state = 1;
/*      */               break;
/*      */           } 
/*      */           break;
/*      */         case 1:
/*  765 */           switch (devType(theWord.charAt(i))) {
/*      */             case 1:
/*  767 */               wLen++;
/*  768 */               state = 0;
/*      */               break;
/*      */             case 2:
/*  771 */               wLen++;
/*      */               break;
/*      */             case 3:
/*  774 */               state = 0;
/*      */               break;
/*      */             case 4:
/*  777 */               state = 2;
/*      */               break;
/*      */           } 
/*      */           break;
/*      */         case 2:
/*  782 */           switch (devType(theWord.charAt(i))) {
/*      */             case 2:
/*  784 */               state = 1;
/*      */               break;
/*      */           } 
/*      */           break;
/*      */       } 
/*      */     } 
/*  790 */     return wLen;
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
/*      */   static byte devType(char uPoint) {
/*  805 */     if (uPoint < 'ऀ' || uPoint > 'ঀ') return 0; 
/*  806 */     byte[] theType = { 0, 9, 9, 9, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 9, 9, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 0, 0, 5, 5, 5, 5, 5, 0, 0, 0, 6, 6, 6, 6, 6, 6, 6, 6, 7, 7, 7, 7, 0, 0, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  815 */     return theType[uPoint % 128];
/*      */   }
/*      */   
/*      */   static void printOptions(JFrame jf, String type) {
/*  819 */     String[] colorLabel = { "Cell Color", "Grid Line Color", "Letter Color", "ID Number Color", "Pattern Color", "Error Color", "Clue Color" };
/*  820 */     int[] colorInt = { Op.DV.DvCellC.ordinal(), Op.DV.DvGridC.ordinal(), Op.DV.DvLettersC.ordinal(), Op.DV.DvIDC.ordinal(), Op.DV.DvPatternC.ordinal(), Op.DV.DvErrorC.ordinal(), Op.DV.DvClueC.ordinal() };
/*  821 */     String[] fontLabel = { "Select Puzzle Font", "Select ID Font", "Select Clue Font" };
/*  822 */     int[] fontInt = { Op.DV.DvFont.ordinal(), Op.DV.DvIDFont.ordinal(), Op.DV.DvClueFont.ordinal() };
/*  823 */     String[] checkLabel = { "PPrint Puzzle with color.", "SPrint Solution with color." };
/*  824 */     int[] checkInt = { Op.DV.DvPuzC.ordinal(), Op.DV.DvSolC.ordinal() };
/*  825 */     Methods.stdPrintOptions(jf, "Devanagari " + type, Op.dv, colorLabel, colorInt, fontLabel, fontInt, checkLabel, checkInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void saveDevanagari(String devanagariName) {
/*      */     try {
/*  836 */       DataOutputStream dataOut = new DataOutputStream(new FileOutputStream(Op.dv[Op.DV.DvDic.ordinal()] + ".dic/" + devanagariName));
/*  837 */       dataOut.writeInt(Grid.xSz);
/*  838 */       dataOut.writeInt(Grid.ySz);
/*  839 */       dataOut.writeByte(Methods.noReveal);
/*  840 */       dataOut.writeByte(Methods.noErrors); int i;
/*  841 */       for (i = 0; i < 54; i++)
/*  842 */         dataOut.writeByte(0); 
/*  843 */       for (int j = 0; j < Grid.ySz; j++) {
/*  844 */         for (i = 0; i < Grid.xSz; i++) {
/*  845 */           dataOut.writeInt(Grid.mode[i][j]);
/*  846 */           dataOut.writeInt(Grid.letter[i][j]);
/*  847 */           dataOut.writeInt(Grid.sol[i][j]);
/*  848 */           dataOut.writeInt(Grid.color[i][j]);
/*      */         } 
/*  850 */       }  dataOut.writeUTF(Methods.puzzleTitle);
/*  851 */       dataOut.writeUTF(Methods.author);
/*  852 */       dataOut.writeUTF(Methods.copyright);
/*  853 */       dataOut.writeUTF(Methods.puzzleNumber);
/*  854 */       dataOut.writeUTF(Methods.puzzleNotes);
/*  855 */       for (i = 0; i < NodeList.nodeListLength; i++) {
/*  856 */         dataOut.writeUTF((NodeList.nodeList[i]).word);
/*  857 */         dataOut.writeUTF((NodeList.nodeList[i]).clue);
/*      */       } 
/*  859 */       dataOut.close();
/*      */     }
/*  861 */     catch (IOException exc) {}
/*  862 */     Methods.havePuzzle = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void loadDevanagari(String devanagariName) {
/*  869 */     Op.dv[Op.DV.DvDic.ordinal()] = Methods.confirmDictionary(Op.dv[Op.DV.DvDic.ordinal()] + ".dic", false);
/*  870 */     File fl = new File(Op.dv[Op.DV.DvDic.ordinal()] + ".dic/" + devanagariName);
/*  871 */     if (!fl.exists()) {
/*  872 */       fl = new File(Op.dv[Op.DV.DvDic.ordinal()] + ".dic/");
/*  873 */       String[] s = fl.list(); int i;
/*  874 */       for (i = 0; i < s.length && (
/*  875 */         s[i].lastIndexOf(".devanagari") == -1 || s[i].charAt(0) == '.'); i++);
/*      */ 
/*      */       
/*  878 */       if (i == s.length) {
/*  879 */         makeGrid();
/*      */         return;
/*      */       } 
/*  882 */       devanagariName = s[i];
/*  883 */       Op.dv[Op.DV.DvPuz.ordinal()] = devanagariName;
/*      */     } 
/*      */     try {
/*  886 */       DataInputStream dataIn = new DataInputStream(new FileInputStream(Op.dv[Op.DV.DvDic.ordinal()] + ".dic/" + devanagariName));
/*  887 */       Grid.xSz = dataIn.readInt();
/*  888 */       Grid.ySz = dataIn.readInt();
/*  889 */       Methods.noReveal = dataIn.readByte();
/*  890 */       Methods.noErrors = dataIn.readByte(); int i;
/*  891 */       for (i = 0; i < 54; i++)
/*  892 */         dataIn.readByte(); 
/*  893 */       for (int j = 0; j < Grid.ySz; j++) {
/*  894 */         for (i = 0; i < Grid.xSz; i++) {
/*  895 */           Grid.mode[i][j] = dataIn.readInt();
/*  896 */           Grid.letter[i][j] = dataIn.readInt();
/*  897 */           Grid.sol[i][j] = dataIn.readInt();
/*  898 */           Grid.color[i][j] = dataIn.readInt();
/*      */         } 
/*      */       } 
/*  901 */       Methods.puzzleTitle = dataIn.readUTF();
/*  902 */       Methods.author = dataIn.readUTF();
/*  903 */       Methods.copyright = dataIn.readUTF();
/*  904 */       Methods.puzzleNumber = dataIn.readUTF();
/*  905 */       Methods.puzzleNotes = dataIn.readUTF();
/*  906 */       NodeList.buildNodeList();
/*  907 */       for (i = 0; i < NodeList.nodeListLength; i++) {
/*  908 */         (NodeList.nodeList[i]).word = dataIn.readUTF();
/*  909 */         (NodeList.nodeList[i]).clue = dataIn.readUTF();
/*      */       } 
/*      */ 
/*      */       
/*  913 */       dataIn.close();
/*      */     }
/*  915 */     catch (IOException exc) {}
/*      */     
/*  917 */     Methods.havePuzzle = true;
/*  918 */     Grid.xCur = (NodeList.nodeList[0]).x;
/*  919 */     Grid.yCur = (NodeList.nodeList[0]).y;
/*      */   }
/*      */   
/*      */   String readString(DataInputStream dataIn, boolean skip) {
/*  923 */     int c = 0;
/*  924 */     char[] wordArray = new char[100];
/*      */     
/*      */     try {
/*  927 */       c = 0; while (true) {
/*  928 */         char ch = (char)dataIn.readByte();
/*  929 */         if (skip) {
/*  930 */           dataIn.readByte();
/*  931 */           dataIn.readByte();
/*  932 */           dataIn.readByte();
/*  933 */           skip = false;
/*      */           continue;
/*      */         } 
/*  936 */         if (ch == '\r') {
/*  937 */           dataIn.readByte();
/*      */           
/*      */           break;
/*      */         } 
/*  941 */         wordArray[c++] = ch;
/*      */       } 
/*  943 */     } catch (IOException exc) {}
/*  944 */     String str = new String(wordArray, 0, c);
/*  945 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void drawDevanagari(Graphics2D g2, String opt) {
/*  956 */     int nL = (int)Math.ceil((Grid.xCell / 60.0F)), wL = (int)Math.ceil((Grid.xCell / 10.0F));
/*  957 */     Stroke normalStroke = new BasicStroke(nL, 2, 0);
/*  958 */     Stroke wideStroke = new BasicStroke(wL, 0, 0);
/*  959 */     g2.setStroke(normalStroke);
/*  960 */     RenderingHints rh = g2.getRenderingHints();
/*  961 */     rh.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
/*  962 */     rh.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
/*  963 */     g2.setRenderingHints(rh);
/*      */     
/*      */     int j;
/*  966 */     for (j = 0; j < Grid.ySz; j++) {
/*  967 */       for (int i = 0; i < Grid.xSz; i++) {
/*  968 */         if (Grid.mode[i][j] != 2) {
/*  969 */           int k; if (Def.dispWithColor.booleanValue() && !Def.displaySubmission.booleanValue()) {
/*  970 */             if (Grid.curColor[i][j] != 16777215 && Def.dispCursor.booleanValue()) {
/*  971 */               k = Grid.curColor[i][j];
/*  972 */             } else if (Grid.color[i][j] != 16777215) {
/*  973 */               k = Grid.color[i][j];
/*      */             } else {
/*  975 */               k = Op.getColorInt(Op.DV.DvCellC.ordinal(), Op.dv);
/*      */             } 
/*      */           } else {
/*  978 */             k = 16777215;
/*  979 */           }  if (Grid.mode[i][j] == 2)
/*  980 */             k = 16777215; 
/*  981 */           g2.setColor(new Color(k));
/*  982 */           g2.fillRect(Grid.xOrg + i * Grid.xCell, Grid.yOrg + j * Grid.yCell, Grid.xCell, Grid.yCell);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  987 */     if (Def.dispWithColor.booleanValue()) {
/*  988 */       Grid.drawPatternCells(g2, new Color(Op.getColorInt(Op.DV.DvPatternC.ordinal(), Op.dv)), new Color(Op.getColorInt(Op.DV.DvCellC.ordinal(), Op.dv)), false);
/*      */     } else {
/*  990 */       Grid.drawPatternCells(g2, Def.COLOR_BLACK, Def.COLOR_WHITE, false);
/*      */     } 
/*      */     
/*  993 */     g2.setStroke(normalStroke);
/*  994 */     g2.setColor(Def.dispWithColor.booleanValue() ? new Color(Op.getColorInt(Op.DV.DvGridC.ordinal(), Op.dv)) : Def.COLOR_BLACK);
/*  995 */     for (j = 0; j < Grid.ySz; j++) {
/*  996 */       for (int i = 0; i < Grid.xSz; i++) {
/*  997 */         if (Grid.mode[i][j] != 2)
/*  998 */           g2.drawRect(Grid.xOrg + i * Grid.xCell, Grid.yOrg + j * Grid.yCell, Grid.xCell, Grid.yCell); 
/*      */       } 
/*      */     } 
/* 1001 */     if (opt.contains("l")) {
/* 1002 */       g2.setColor(Def.dispWithColor.booleanValue() ? new Color(Op.getColorInt(Op.DV.DvLettersC.ordinal(), Op.dv)) : Def.COLOR_BLACK);
/* 1003 */       for (j = 0; j < Grid.ySz; j++) {
/* 1004 */         for (int i = 0; i < Grid.xSz; i++) {
/* 1005 */           int width; String devLetter = recoverDevStringFromInt(Grid.letter[i][j]);
/* 1006 */           int fontMult = 24;
/*      */           while (true) {
/* 1008 */             int fontSize = fontMult * Grid.yCell / 40;
/* 1009 */             g2.setFont(new Font(Op.dv[Op.DV.DvFont.ordinal()], 0, fontSize));
/* 1010 */             FontMetrics fm = g2.getFontMetrics();
/* 1011 */             width = fm.stringWidth(devLetter);
/* 1012 */             if (width < 9 * Grid.xCell / 10)
/* 1013 */               break;  fontMult--;
/*      */           } 
/* 1015 */           g2.drawString(devLetter, Grid.xOrg + i * Grid.xCell + (Grid.xCell - width) / 2, Grid.yOrg + j * Grid.yCell + 8 * Grid.yCell / 10);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1022 */     g2.setStroke(wideStroke);
/* 1023 */     Grid.drawBars(g2);
/*      */     
/* 1025 */     Grid.drawOutline(g2, true);
/*      */ 
/*      */     
/* 1028 */     if (Def.dispCursor.booleanValue() && Def.building != 1 && !Def.displaySubmission.booleanValue()) {
/* 1029 */       g2.setStroke(wideStroke);
/* 1030 */       g2.setColor(Def.COLOR_RED);
/* 1031 */       g2.drawRect(Grid.xOrg + Grid.RorL(Grid.xCur) * Grid.xCell, Grid.yOrg + Grid.yCur * Grid.yCell, Grid.xCell, Grid.yCell);
/*      */     } 
/*      */ 
/*      */     
/* 1035 */     if (Def.dispGuideDigits.booleanValue() || Op.getBool(Op.DV.DvIDC.ordinal(), Op.dv).booleanValue()) {
/* 1036 */       g2.setFont(new Font(Op.dv[Op.DV.DvIDFont.ordinal()], 0, Grid.yCell / 3));
/* 1037 */       g2.setColor(Def.dispWithColor.booleanValue() ? new Color(Op.getColorInt(Op.DV.DvIDC.ordinal(), Op.dv)) : Def.COLOR_BLACK);
/* 1038 */       FontMetrics fm = g2.getFontMetrics();
/* 1039 */       for (int i = 0; NodeList.nodeList[i] != null; i++) {
/* 1040 */         int drawI = (NodeList.nodeList[i]).x;
/* 1041 */         int xCoord = Grid.xOrg + drawI * Grid.xCell + ((Grid.xCell / 20 > 1) ? (Grid.xCell / 20) : 1);
/* 1042 */         g2.drawString("" + (NodeList.nodeList[i]).id, xCoord, Grid.yOrg + (NodeList.nodeList[i]).y * Grid.yCell + fm.getAscent());
/*      */       } 
/*      */     } 
/* 1045 */     g2.setStroke(new BasicStroke(1.0F));
/*      */   }
/*      */   
/*      */   static void printPuz(Graphics2D g2, int left, int top, int width, int height) {
/* 1049 */     loadDevanagari(Op.dv[Op.DV.DvPuz.ordinal()]);
/* 1050 */     setSizesAndOffsets(left, top, width, height, 0, true);
/* 1051 */     Methods.clearGrid(Grid.sol);
/* 1052 */     Def.dispGuideDigits = Boolean.valueOf(true);
/* 1053 */     Def.dispWithColor = Op.getBool(Op.DV.DvPuzC.ordinal(), Op.dv);
/* 1054 */     drawDevanagari(g2, "");
/* 1055 */     Def.dispWithColor = Boolean.valueOf(true);
/*      */   }
/*      */   
/*      */   static void printSol(Graphics2D g2, int left, int top, int width, int height, String solutionPuzzle) {
/* 1059 */     loadDevanagari(solutionPuzzle);
/* 1060 */     setSizesAndOffsets(left, top, width, height, 0, true);
/* 1061 */     Def.dispGuideDigits = Boolean.valueOf(false);
/* 1062 */     Def.dispWithColor = Op.getBool(Op.DV.DvSolC.ordinal(), Op.dv);
/* 1063 */     drawDevanagari(g2, "l");
/* 1064 */     Def.dispWithColor = Boolean.valueOf(true);
/* 1065 */     Def.dispGuideDigits = Boolean.valueOf(true);
/* 1066 */     loadDevanagari(Op.dv[Op.DV.DvPuz.ordinal()]);
/*      */   }
/*      */   
/*      */   static void printSolTitle(Graphics2D g2, int left, int top, int width, int height, String solutionPuzzle) {
/* 1070 */     loadDevanagari(solutionPuzzle);
/* 1071 */     Print.outputTextItem(g2, left, top, width, height, "SansSerif", 0, Methods.puzzleTitle);
/* 1072 */     loadDevanagari(Op.dv[Op.DV.DvPuz.ordinal()]);
/*      */   }
/*      */   
/*      */   static void makeGrid() {
/* 1076 */     Methods.havePuzzle = false;
/* 1077 */     Grid.clearGrid();
/* 1078 */     Grid.loadGrid(Op.msc[Op.MSC.GridName.ordinal()]);
/*      */   }
/*      */   
/*      */   void insertWord(int index) {
/* 1082 */     Node thisNode = NodeList.nodeList[index];
/* 1083 */     thisNode.pending = false;
/*      */     
/* 1085 */     for (int i = 0; i < thisNode.length; i++) {
/* 1086 */       int x = (thisNode.cellLoc[i]).x;
/* 1087 */       int y = (thisNode.cellLoc[i]).y;
/* 1088 */       Grid.control[x][y] = (byte)(Grid.control[x][y] + 1); if ((byte)(Grid.control[x][y] + 1) == 1)
/* 1089 */         Grid.letter[x][y] = thisNode.devWord[i]; 
/*      */     } 
/* 1091 */     busy[thisNode.length][thisNode.wordIndex] = true;
/*      */   }
/*      */   
/*      */   void extractWord(int index) {
/* 1095 */     Node thisNode = NodeList.nodeList[index];
/* 1096 */     thisNode.pending = true;
/* 1097 */     for (int i = 0; i < thisNode.length; i++) {
/* 1098 */       int x = (thisNode.cellLoc[i]).x;
/* 1099 */       int y = (thisNode.cellLoc[i]).y;
/* 1100 */       Grid.control[x][y] = (byte)(Grid.control[x][y] - 1); if ((byte)(Grid.control[x][y] - 1) == 0)
/* 1101 */         Grid.letter[x][y] = 0; 
/*      */     } 
/* 1103 */     busy[thisNode.length][thisNode.wordIndex] = false;
/*      */   }
/*      */   
/*      */   boolean depend(int subjectIndex, int compareIndex) {
/* 1107 */     Node subjectNode = NodeList.nodeList[subjectIndex];
/* 1108 */     for (int i = 0; i < subjectNode.length; i++) {
/* 1109 */       int x = (subjectNode.cellLoc[i]).x;
/* 1110 */       int y = (subjectNode.cellLoc[i]).y;
/* 1111 */       if (compareIndex == Grid.horz[x][y] || compareIndex == Grid.vert[x][y])
/*      */       {
/* 1113 */         return true; } 
/*      */     } 
/* 1115 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void busyNode(int index) {
/* 1123 */     Node thisNode = NodeList.nodeList[index];
/* 1124 */     for (int i = 0; i < thisNode.length; i++) {
/* 1125 */       int x = (thisNode.cellLoc[i]).x;
/* 1126 */       int y = (thisNode.cellLoc[i]).y;
/* 1127 */       Grid.letter[x][y] = -1;
/* 1128 */       int scanIndex = (index == Grid.horz[x][y]) ? Grid.vert[x][y] : Grid.horz[x][y];
/* 1129 */       if (scanIndex != -1) {
/* 1130 */         Node scanNode = NodeList.nodeList[scanIndex];
/* 1131 */         for (int j = 0; j < scanNode.length; j++) {
/* 1132 */           int scanX = (scanNode.cellLoc[j]).x;
/* 1133 */           int scanY = (scanNode.cellLoc[j]).y;
/* 1134 */           Grid.control[scanX][scanY] = (byte)(Grid.control[scanX][scanY] + 1);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1138 */     thisNode.pending = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void evaluateNode(int index) {
/* 1145 */     this.links = this.contax = this.contax2 = this.form = 0;
/* 1146 */     this.earlyLink = 500;
/* 1147 */     Node thisNode = NodeList.nodeList[index];
/* 1148 */     for (int i = 0; i < thisNode.length; i++) {
/* 1149 */       int x = (thisNode.cellLoc[i]).x;
/* 1150 */       int y = (thisNode.cellLoc[i]).y;
/* 1151 */       if (Grid.letter[x][y] == -1) {
/* 1152 */         this.links++;
/*      */       } else {
/* 1154 */         this.contax += Grid.control[x][y];
/* 1155 */         if (this.form == 0)
/* 1156 */           this.form = 1; 
/* 1157 */         int scanIndex = (index == Grid.horz[x][y]) ? Grid.vert[x][y] : Grid.horz[x][y];
/* 1158 */         if (scanIndex != -1) {
/* 1159 */           this.form = 2;
/* 1160 */           if (scanIndex < this.earlyLink)
/* 1161 */             this.earlyLink = scanIndex; 
/* 1162 */           Node scanNode = NodeList.nodeList[scanIndex];
/* 1163 */           int sLinks = 0;
/* 1164 */           for (int j = 0; j < scanNode.length; j++) {
/* 1165 */             int scanX = (scanNode.cellLoc[j]).x;
/* 1166 */             int scanY = (scanNode.cellLoc[j]).y;
/* 1167 */             if (Grid.letter[scanX][scanY] == -1) {
/* 1168 */               sLinks++;
/* 1169 */             } else if (x != scanX || y != scanY) {
/* 1170 */               this.contax2 += Grid.control[scanX][scanY];
/*      */             } 
/* 1172 */           }  this.contax2 -= sLinks * (scanNode.length - 1 - sLinks);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1176 */     this.contax -= this.links * (thisNode.length - this.links);
/* 1177 */     if (this.form == 2 && (this.contax > 0 || this.contax2 > 0))
/* 1178 */       this.form = 3; 
/*      */   }
/*      */   
/*      */   void orderNodeList() {
/* 1182 */     int rIndex = 0;
/*      */     
/* 1184 */     int[] nodeOrder = new int[800];
/*      */     int i;
/* 1186 */     for (i = 0; i < 50; i++) {
/* 1187 */       for (int j = 0; j < 50; j++)
/* 1188 */         Grid.control[i][j] = 0; 
/* 1189 */     }  for (i = 0; i < NodeList.nodeListLength; i++) {
/* 1190 */       (NodeList.nodeList[i]).pending = true;
/*      */     }
/* 1192 */     busyNode(nodeOrder[0] = 0);
/* 1193 */     for (i = 1; i < NodeList.nodeListLength; i++) {
/* 1194 */       for (int bestForm = 0, maxContax2 = bestForm, maxContax = maxContax2, maxLinks = maxContax, leastEarlyLink = 10000, index = 1; index < NodeList.nodeListLength; index++) {
/* 1195 */         if (!(NodeList.nodeList[index]).pending) {
/*      */           continue;
/*      */         }
/* 1198 */         evaluateNode(index);
/* 1199 */         if (this.form >= 2 && (
/* 1200 */           bestForm != 2 || this.links <= 0 || this.form != 3)) {
/* 1201 */           if ((bestForm == 3 && maxLinks > 0 && this.form == 2) || 
/* 1202 */             this.links < maxLinks)
/* 1203 */             continue;  if (this.links == maxLinks) {
/* 1204 */             if (this.contax < maxContax)
/*      */               continue; 
/* 1206 */             if (this.contax == maxContax && (
/* 1207 */               this.earlyLink >= leastEarlyLink || 
/* 1208 */               this.contax2 < maxContax2))
/*      */               continue; 
/*      */           } 
/*      */         } 
/* 1212 */         maxLinks = this.links;
/* 1213 */         maxContax = this.contax;
/* 1214 */         maxContax2 = this.contax2;
/* 1215 */         leastEarlyLink = this.earlyLink;
/* 1216 */         bestForm = this.form;
/* 1217 */         rIndex = index;
/* 1218 */         if (bestForm < 2)
/*      */           break;  continue;
/*      */       } 
/* 1221 */       busyNode(nodeOrder[i] = rIndex);
/*      */     } 
/*      */ 
/*      */     
/* 1225 */     for (i = 1; i < NodeList.nodeListLength; i++) {
/* 1226 */       Node thisNode = NodeList.nodeList[i];
/* 1227 */       NodeList.nodeList[i] = NodeList.nodeList[nodeOrder[i]];
/* 1228 */       NodeList.nodeList[nodeOrder[i]] = thisNode;
/* 1229 */       for (int j = i; j < NodeList.nodeListLength; j++) {
/* 1230 */         if (nodeOrder[j] == i) {
/* 1231 */           nodeOrder[j] = nodeOrder[i];
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     } 
/* 1237 */     for (i = 0; i < 50; i++) {
/* 1238 */       for (int j = 0; j < 50; j++) {
/* 1239 */         Grid.control[i][j] = 0; Grid.letter[i][j] = 0;
/* 1240 */         Grid.vert[i][j] = -1; Grid.horz[i][j] = -1;
/*      */       } 
/*      */     } 
/* 1243 */     for (i = 0; i < NodeList.nodeListLength; i++) {
/* 1244 */       (NodeList.nodeList[i]).pending = true;
/* 1245 */       (NodeList.nodeList[i]).wordIndex = -1;
/* 1246 */       for (int j = 0; j < (NodeList.nodeList[i]).length; j++) {
/* 1247 */         int x = ((NodeList.nodeList[i]).cellLoc[j]).x;
/* 1248 */         int y = ((NodeList.nodeList[i]).cellLoc[j]).y;
/* 1249 */         if (Grid.horz[x][y] == -1) {
/* 1250 */           Grid.horz[x][y] = i;
/*      */         } else {
/* 1252 */           Grid.vert[x][y] = i;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   static boolean loadDictionary() {
/* 1258 */     int[] puzWords = new int[50];
/* 1259 */     int state = 0;
/* 1260 */     int[] devLetter = new int[50];
/*      */ 
/*      */     
/*      */     int i;
/*      */ 
/*      */     
/* 1266 */     for (i = 0; i < NodeList.nodeListLength; i++)
/* 1267 */       puzWords[(NodeList.nodeList[i]).length] = puzWords[(NodeList.nodeList[i]).length] + 1; 
/* 1268 */     for (i = 0; i < 50; wordCount[i++] = 0);
/*      */ 
/*      */     
/*      */     try {
/* 1272 */       DataInputStream dataIn = new DataInputStream(new FileInputStream(Op.dv[Op.DV.DvDic.ordinal()] + ".dic/xword.dic"));
/* 1273 */       dataIn.read(DictionaryMtce.dicHeader, 0, 128);
/* 1274 */       while (dataIn.available() > 2) {
/* 1275 */         dataIn.readInt();
/* 1276 */         String word = dataIn.readUTF(); int j;
/* 1277 */         if ((j = devLength(word)) > 1)
/* 1278 */           wordCount[j] = wordCount[j] + 1; 
/* 1279 */         dataIn.readUTF();
/*      */       } 
/* 1281 */       dataIn.close();
/* 1282 */     } catch (IOException exc) {}
/*      */ 
/*      */     
/* 1285 */     for (int len = 2; len < 50; len++) {
/* 1286 */       if (wordCount[len] > 0) {
/* 1287 */         dWord[len] = new int[wordCount[len]][len];
/* 1288 */         busy[len] = new boolean[wordCount[len]];
/* 1289 */         revLink[len] = new int[wordCount[len]];
/* 1290 */         for (i = 0; i < wordCount[len]; ) { revLink[len][i] = i; i++; }
/*      */       
/*      */       } 
/*      */     } 
/* 1294 */     for (i = 0; i < 50; wordCount[i++] = 0);
/*      */     try {
/* 1296 */       DataInputStream dataIn = new DataInputStream(new FileInputStream(Op.dv[Op.DV.DvDic.ordinal()] + ".dic/xword.dic"));
/* 1297 */       for (i = 0; i < 128; i++)
/* 1298 */         dataIn.readByte(); 
/* 1299 */       while (dataIn.available() > 2) {
/* 1300 */         int wordData = dataIn.readInt();
/* 1301 */         String word = dataIn.readUTF();
/* 1302 */         int wLen = 0;
/* 1303 */         boolean badWord = false;
/*      */         
/* 1305 */         for (i = 0; i < 50; i++)
/* 1306 */           devLetter[i] = 0; 
/*      */         int devLetterIndex;
/* 1308 */         for (state = 0, devLetterIndex = i = 0; i < word.length() && 
/* 1309 */           word.charAt(i) != ' '; i++) {
/* 1310 */           switch (state) {
/*      */             case 0:
/* 1312 */               switch (devType(word.charAt(i))) {
/*      */                 case 1:
/* 1314 */                   devLetter[devLetterIndex++] = (char)(word.charAt(i) % 256);
/* 1315 */                   wLen++;
/*      */                   break;
/*      */                 case 2:
/* 1318 */                   devLetter[devLetterIndex] = (char)(word.charAt(i) % 256);
/* 1319 */                   wLen++;
/* 1320 */                   state = 1;
/*      */                   break;
/*      */               } 
/* 1323 */               badWord = true;
/*      */               break;
/*      */             
/*      */             case 1:
/* 1327 */               switch (devType(word.charAt(i))) {
/*      */                 case 1:
/* 1329 */                   wLen++;
/* 1330 */                   devLetter[++devLetterIndex] = (char)(word.charAt(i) % 256);
/* 1331 */                   devLetterIndex++;
/* 1332 */                   state = 0;
/*      */                   break;
/*      */                 case 2:
/* 1335 */                   wLen++;
/* 1336 */                   devLetter[++devLetterIndex] = (char)(word.charAt(i) % 256);
/*      */                   break;
/*      */                 case 3:
/* 1339 */                   devLetter[devLetterIndex] = devLetter[devLetterIndex] * 256;
/* 1340 */                   devLetter[devLetterIndex] = devLetter[devLetterIndex] + (char)(word.charAt(i) % 256);
/* 1341 */                   devLetterIndex++;
/* 1342 */                   state = 0;
/*      */                   break;
/*      */                 case 4:
/* 1345 */                   devLetter[devLetterIndex] = devLetter[devLetterIndex] | 0x80;
/* 1346 */                   state = 2;
/*      */                   break;
/*      */               } 
/* 1349 */               badWord = true;
/*      */               break;
/*      */             
/*      */             case 2:
/* 1353 */               switch (devType(word.charAt(i))) {
/*      */                 case 2:
/* 1355 */                   devLetter[devLetterIndex] = devLetter[devLetterIndex] * 256;
/* 1356 */                   devLetter[devLetterIndex] = devLetter[devLetterIndex] + (char)(word.charAt(i) % 256);
/* 1357 */                   state = 1;
/*      */                   break;
/*      */               } 
/* 1360 */               badWord = true;
/*      */               break;
/*      */           } 
/*      */         
/*      */         } 
/* 1365 */         if (wLen > 1 && !badWord && word.equals(recoverDevStringFromDevLetterArray(devLetter, wLen)) && 
/* 1366 */           wordData % 256 != 255) {
/* 1367 */           for (i = 0; i < wLen; i++)
/* 1368 */             dWord[wLen][wordCount[wLen]][i] = devLetter[i]; 
/* 1369 */           wordCount[wLen] = wordCount[wLen] + 1;
/*      */         } 
/*      */         
/* 1372 */         dataIn.readUTF();
/*      */       } 
/* 1374 */       dataIn.close();
/* 1375 */     } catch (IOException exc) {}
/*      */     
/* 1377 */     for (i = 0; i < 50; i++) {
/* 1378 */       if (puzWords[i] > wordCount[i])
/* 1379 */         return false; 
/* 1380 */     }  return true;
/*      */   }
/*      */   
/*      */   int firstBinarySearch(int[] devTemplate, int wlen, int tlen) {
/* 1384 */     int first = 0, last = wordCount[wlen] - 1;
/*      */     
/*      */     while (true) {
/* 1387 */       int current = (first + last) / 2; int i;
/* 1388 */       for (i = 0; i < tlen; i++) {
/* 1389 */         if (devTemplate[i] > dWord[wlen][current][i]) {
/* 1390 */           first = current;
/*      */           break;
/*      */         } 
/* 1393 */         if (devTemplate[i] < dWord[wlen][current][i]) {
/* 1394 */           last = current;
/*      */           break;
/*      */         } 
/*      */       } 
/* 1398 */       if (i == tlen)
/* 1399 */         last = current; 
/* 1400 */       if (last - first <= 1)
/* 1401 */         return first; 
/*      */     } 
/*      */   }
/*      */   
/*      */   int lastBinarySearch(int[] devTemplate, int wlen, int tlen) {
/* 1406 */     int first = 0, last = wordCount[wlen] - 1;
/*      */     while (true) {
/* 1408 */       int current = (first + last) / 2; int i;
/* 1409 */       for (i = 0; i < tlen; i++) {
/* 1410 */         if (devTemplate[i] < dWord[wlen][current][i]) {
/* 1411 */           last = current;
/*      */           break;
/*      */         } 
/* 1414 */         if (devTemplate[i] > dWord[wlen][current][i]) {
/* 1415 */           first = current;
/*      */           break;
/*      */         } 
/*      */       } 
/* 1419 */       if (i == tlen)
/* 1420 */         first = current; 
/* 1421 */       if (last - first <= 1)
/* 1422 */         return last; 
/*      */     } 
/*      */   }
/*      */   
/*      */   boolean findMatchingWord(int nodeIndex) {
/* 1427 */     Random r = new Random();
/*      */     
/* 1429 */     int j = 0;
/* 1430 */     Node thisNode = NodeList.nodeList[nodeIndex];
/* 1431 */     int length = thisNode.length;
/* 1432 */     if (wordCount[length] == 0) {
/* 1433 */       return false;
/*      */     }
/* 1435 */     int[] temp = thisNode.devTemplate;
/* 1436 */     if (thisNode.wordIndex != -1) {
/*      */       do {
/* 1438 */         if (++thisNode.wordIndex > thisNode.last)
/* 1439 */           thisNode.wordIndex = thisNode.first; 
/* 1440 */         if (thisNode.wordIndex == thisNode.start) {
/* 1441 */           thisNode.wordIndex = -1;
/* 1442 */           return false;
/*      */         } 
/* 1444 */       } while (busy[length][thisNode.wordIndex]);
/*      */     } else {
/* 1446 */       if (temp[0] == 32) {
/* 1447 */         thisNode.first = 0;
/* 1448 */         thisNode.last = wordCount[length] - 1;
/*      */       } else {
/*      */         int tlen;
/* 1451 */         for (tlen = 0; tlen < length && 
/* 1452 */           temp[tlen] != 0; tlen++);
/*      */         
/* 1454 */         thisNode.first = firstBinarySearch(temp, length, tlen);
/* 1455 */         thisNode.last = lastBinarySearch(temp, length, tlen);
/* 1456 */         if (thisNode.last - thisNode.first < 0)
/* 1457 */           return false; 
/*      */       } 
/* 1459 */       thisNode.wordIndex = thisNode.start = thisNode.first + r.nextInt(thisNode.last - thisNode.first + 1);
/*      */     } 
/*      */     
/*      */     label48: while (true) {
/* 1463 */       if (!busy[length][thisNode.wordIndex]) {
/* 1464 */         for (j = 0; j < length && (
/* 1465 */           temp[j] == 0 || temp[j] == dWord[length][thisNode.wordIndex][j]); j++);
/*      */       }
/*      */       
/* 1468 */       if (j == length) {
/* 1469 */         thisNode.devWord = dWord[length][thisNode.wordIndex];
/* 1470 */         return true;
/*      */       } 
/*      */       while (true) {
/* 1473 */         if (++thisNode.wordIndex > thisNode.last)
/* 1474 */           thisNode.wordIndex = thisNode.first; 
/* 1475 */         if (thisNode.wordIndex == thisNode.start) {
/* 1476 */           thisNode.wordIndex = -1;
/* 1477 */           return false;
/*      */         } 
/* 1479 */         if (!busy[length][thisNode.wordIndex])
/*      */           continue label48; 
/*      */       } 
/*      */       break;
/*      */     } 
/*      */   }
/*      */   
/*      */   boolean wordOK(int index) {
/* 1487 */     Node thisNode = NodeList.nodeList[index];
/* 1488 */     for (int i = 0; i < thisNode.length; i++) {
/* 1489 */       int x = (thisNode.cellLoc[i]).x, y = (thisNode.cellLoc[i]).y;
/* 1490 */       int scanIndex = (index == Grid.horz[x][y]) ? Grid.vert[x][y] : Grid.horz[x][y];
/* 1491 */       if (scanIndex != -1) {
/* 1492 */         Node scanNode = NodeList.nodeList[scanIndex];
/* 1493 */         if (scanNode.pending) {
/* 1494 */           NodeList.buildDevTemplate(scanIndex);
/* 1495 */           scanNode.source = 0;
/* 1496 */           scanNode.wordIndex = -1;
/* 1497 */           if (!findMatchingWord(scanIndex))
/* 1498 */             return false; 
/*      */         } 
/*      */       } 
/*      */     } 
/* 1502 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   void treeExtract(int index) {
/* 1507 */     for (int sIndex = index + 1; sIndex < NodeList.nodeListLength; sIndex++) {
/* 1508 */       if (depend(index, sIndex) && !(NodeList.nodeList[sIndex]).pending)
/* 1509 */         treeExtract(sIndex); 
/* 1510 */     }  extractWord(index);
/*      */   }
/*      */ 
/*      */   
/*      */   int fillCrossword() {
/*      */     int nodeIndex;
/* 1516 */     NodeList.buildDevTemplate(nodeIndex = 0);
/* 1517 */     (NodeList.nodeList[nodeIndex]).source = 0;
/* 1518 */     int j = 0; while (true) {
/* 1519 */       if (findMatchingWord(nodeIndex)) {
/* 1520 */         insertWord(nodeIndex);
/* 1521 */         if (wordOK(nodeIndex)) {
/* 1522 */           (NodeList.nodeList[nodeIndex]).failCount = 0;
/* 1523 */           for (; nodeIndex < NodeList.nodeListLength && !(NodeList.nodeList[nodeIndex]).pending; nodeIndex++);
/*      */ 
/*      */           
/* 1526 */           if (++j % 50 == 0) {
/* 1527 */             restoreFrame();
/* 1528 */             if (Def.building == 2)
/* 1529 */               return 0; 
/*      */           } 
/* 1531 */           if (nodeIndex < NodeList.nodeListLength) {
/* 1532 */             (NodeList.nodeList[nodeIndex]).wordIndex = -1;
/* 1533 */             NodeList.buildDevTemplate(nodeIndex);
/* 1534 */             (NodeList.nodeList[nodeIndex]).source = 0;
/*      */             
/*      */             continue;
/*      */           } 
/*      */           break;
/*      */         } 
/* 1540 */         extractWord(nodeIndex);
/*      */       } else {
/*      */         
/* 1543 */         if (nodeIndex == 0) {
/* 1544 */           return 0;
/*      */         }
/* 1546 */         if (++(NodeList.nodeList[nodeIndex]).failCount > 500) {
/* 1547 */           (NodeList.nodeList[nodeIndex]).failCount = 0;
/* 1548 */           nodeIndex = problemNode(nodeIndex);
/*      */         } else {
/*      */           
/* 1551 */           nodeIndex = (NodeList.nodeList[nodeIndex]).revert;
/* 1552 */         }  treeExtract(nodeIndex);
/*      */       } 
/* 1554 */       if (j > 500000) {
/* 1555 */         return 2;
/*      */       }
/*      */     } 
/* 1558 */     return 1;
/*      */   }
/*      */   
/*      */   int problemNode(int subjectNode) {
/* 1562 */     int problemNode = 1000;
/*      */     
/* 1564 */     for (int i = 0; i < (NodeList.nodeList[subjectNode]).length; i++) {
/* 1565 */       int x = ((NodeList.nodeList[subjectNode]).cellLoc[i]).x, y = ((NodeList.nodeList[subjectNode]).cellLoc[i]).y;
/* 1566 */       int testNode = (Grid.horz[x][y] == subjectNode) ? Grid.vert[x][y] : Grid.horz[x][y];
/* 1567 */       if (testNode != -1 && 
/* 1568 */         !(NodeList.nodeList[testNode]).pending && testNode < problemNode)
/* 1569 */         problemNode = testNode; 
/*      */     } 
/* 1571 */     return problemNode;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean buildDevanagari(boolean loadDic) {
/*      */     // Byte code:
/*      */     //   0: iconst_1
/*      */     //   1: istore_2
/*      */     //   2: aload_0
/*      */     //   3: invokevirtual orderNodeList : ()V
/*      */     //   6: iload_1
/*      */     //   7: ifeq -> 18
/*      */     //   10: invokestatic loadDictionary : ()Z
/*      */     //   13: ifne -> 79
/*      */     //   16: iconst_0
/*      */     //   17: ireturn
/*      */     //   18: iconst_0
/*      */     //   19: istore #8
/*      */     //   21: iload #8
/*      */     //   23: iconst_5
/*      */     //   24: if_icmpge -> 79
/*      */     //   27: iconst_2
/*      */     //   28: istore #9
/*      */     //   30: iload #9
/*      */     //   32: bipush #50
/*      */     //   34: if_icmpge -> 73
/*      */     //   37: iconst_0
/*      */     //   38: istore #10
/*      */     //   40: iload #10
/*      */     //   42: getstatic crosswordexpress/DevanagariBuild.wordCount : [I
/*      */     //   45: iload #9
/*      */     //   47: iaload
/*      */     //   48: if_icmpge -> 67
/*      */     //   51: getstatic crosswordexpress/DevanagariBuild.busy : [[Z
/*      */     //   54: iload #9
/*      */     //   56: aaload
/*      */     //   57: iload #10
/*      */     //   59: iconst_0
/*      */     //   60: bastore
/*      */     //   61: iinc #10, 1
/*      */     //   64: goto -> 40
/*      */     //   67: iinc #9, 1
/*      */     //   70: goto -> 30
/*      */     //   73: iinc #8, 1
/*      */     //   76: goto -> 21
/*      */     //   79: iconst_1
/*      */     //   80: istore #5
/*      */     //   82: iload #5
/*      */     //   84: getstatic crosswordexpress/NodeList.nodeListLength : I
/*      */     //   87: if_icmpge -> 160
/*      */     //   90: getstatic crosswordexpress/NodeList.nodeList : [Lcrosswordexpress/Node;
/*      */     //   93: iload #5
/*      */     //   95: aaload
/*      */     //   96: astore #7
/*      */     //   98: aload #7
/*      */     //   100: iload #5
/*      */     //   102: iconst_1
/*      */     //   103: isub
/*      */     //   104: dup
/*      */     //   105: istore #6
/*      */     //   107: putfield revert : I
/*      */     //   110: iload #6
/*      */     //   112: ifle -> 154
/*      */     //   115: aload_0
/*      */     //   116: iload #6
/*      */     //   118: iload #5
/*      */     //   120: invokevirtual depend : (II)Z
/*      */     //   123: ifeq -> 148
/*      */     //   126: aload #7
/*      */     //   128: iload #6
/*      */     //   130: ifne -> 140
/*      */     //   133: iload #5
/*      */     //   135: iconst_1
/*      */     //   136: isub
/*      */     //   137: goto -> 142
/*      */     //   140: iload #6
/*      */     //   142: putfield revert : I
/*      */     //   145: goto -> 154
/*      */     //   148: iinc #6, -1
/*      */     //   151: goto -> 110
/*      */     //   154: iinc #5, 1
/*      */     //   157: goto -> 82
/*      */     //   160: aload_0
/*      */     //   161: invokevirtual fillCrossword : ()I
/*      */     //   164: tableswitch default -> 209, 0 -> 192, 1 -> 197, 2 -> 206
/*      */     //   192: iconst_0
/*      */     //   193: istore_2
/*      */     //   194: goto -> 209
/*      */     //   197: iconst_1
/*      */     //   198: putstatic crosswordexpress/Methods.havePuzzle : Z
/*      */     //   201: iconst_1
/*      */     //   202: istore_2
/*      */     //   203: goto -> 209
/*      */     //   206: goto -> 2
/*      */     //   209: iload_2
/*      */     //   210: ifeq -> 332
/*      */     //   213: iconst_0
/*      */     //   214: istore #4
/*      */     //   216: iload #4
/*      */     //   218: getstatic crosswordexpress/NodeList.nodeListLength : I
/*      */     //   221: if_icmpge -> 308
/*      */     //   224: getstatic crosswordexpress/NodeList.nodeList : [Lcrosswordexpress/Node;
/*      */     //   227: iload #4
/*      */     //   229: aaload
/*      */     //   230: astore #8
/*      */     //   232: aload #8
/*      */     //   234: ldc ''
/*      */     //   236: putfield word : Ljava/lang/String;
/*      */     //   239: iconst_0
/*      */     //   240: istore_3
/*      */     //   241: iload_3
/*      */     //   242: aload #8
/*      */     //   244: getfield devWord : [I
/*      */     //   247: arraylength
/*      */     //   248: if_icmpge -> 302
/*      */     //   251: aload #8
/*      */     //   253: getfield devWord : [I
/*      */     //   256: iload_3
/*      */     //   257: iaload
/*      */     //   258: ifeq -> 296
/*      */     //   261: new java/lang/StringBuilder
/*      */     //   264: dup
/*      */     //   265: invokespecial <init> : ()V
/*      */     //   268: aload #8
/*      */     //   270: dup_x1
/*      */     //   271: getfield word : Ljava/lang/String;
/*      */     //   274: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   277: aload #8
/*      */     //   279: getfield devWord : [I
/*      */     //   282: iload_3
/*      */     //   283: iaload
/*      */     //   284: invokestatic recoverDevStringFromInt : (I)Ljava/lang/String;
/*      */     //   287: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   290: invokevirtual toString : ()Ljava/lang/String;
/*      */     //   293: putfield word : Ljava/lang/String;
/*      */     //   296: iinc #3, 1
/*      */     //   299: goto -> 241
/*      */     //   302: iinc #4, 1
/*      */     //   305: goto -> 216
/*      */     //   308: getstatic crosswordexpress/Op.dv : [Ljava/lang/String;
/*      */     //   311: getstatic crosswordexpress/Op$DV.DvDic : Lcrosswordexpress/Op$DV;
/*      */     //   314: invokevirtual ordinal : ()I
/*      */     //   317: aaload
/*      */     //   318: iconst_0
/*      */     //   319: invokestatic valueOf : (Z)Ljava/lang/Boolean;
/*      */     //   322: invokestatic attachClues : (Ljava/lang/String;Ljava/lang/Boolean;)V
/*      */     //   325: iconst_2
/*      */     //   326: invokestatic sortNodeList : (I)V
/*      */     //   329: invokestatic rebuildHorzAndVert : ()V
/*      */     //   332: invokestatic restoreFrame : ()V
/*      */     //   335: iload_2
/*      */     //   336: ireturn
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #1575	-> 0
/*      */     //   #1580	-> 2
/*      */     //   #1581	-> 6
/*      */     //   #1582	-> 10
/*      */     //   #1583	-> 16
/*      */     //   #1586	-> 18
/*      */     //   #1587	-> 27
/*      */     //   #1588	-> 37
/*      */     //   #1589	-> 51
/*      */     //   #1588	-> 61
/*      */     //   #1587	-> 67
/*      */     //   #1586	-> 73
/*      */     //   #1592	-> 79
/*      */     //   #1593	-> 90
/*      */     //   #1594	-> 98
/*      */     //   #1595	-> 115
/*      */     //   #1596	-> 126
/*      */     //   #1597	-> 145
/*      */     //   #1594	-> 148
/*      */     //   #1592	-> 154
/*      */     //   #1602	-> 160
/*      */     //   #1604	-> 192
/*      */     //   #1605	-> 194
/*      */     //   #1607	-> 197
/*      */     //   #1608	-> 201
/*      */     //   #1609	-> 203
/*      */     //   #1611	-> 206
/*      */     //   #1614	-> 209
/*      */     //   #1615	-> 213
/*      */     //   #1616	-> 224
/*      */     //   #1617	-> 232
/*      */     //   #1618	-> 239
/*      */     //   #1619	-> 251
/*      */     //   #1620	-> 261
/*      */     //   #1618	-> 296
/*      */     //   #1615	-> 302
/*      */     //   #1622	-> 308
/*      */     //   #1623	-> 325
/*      */     //   #1624	-> 329
/*      */     //   #1626	-> 332
/*      */     //   #1627	-> 335
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   40	27	10	n	I
/*      */     //   30	43	9	length	I
/*      */     //   21	58	8	source	I
/*      */     //   107	50	6	rIndex	I
/*      */     //   98	59	7	thisNode	Lcrosswordexpress/Node;
/*      */     //   232	70	8	jNL	Lcrosswordexpress/Node;
/*      */     //   241	64	3	i	I
/*      */     //   216	116	4	j	I
/*      */     //   0	337	0	this	Lcrosswordexpress/DevanagariBuild;
/*      */     //   0	337	1	loadDic	Z
/*      */     //   2	335	2	ret	Z
/*      */     //   82	255	5	index	I
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void focusColor(boolean set) {
/* 1632 */     if (Grid.nCur != -1) {
/* 1633 */       for (int i = 0; i < (NodeList.nodeList[Grid.nCur]).length; i++) {
/* 1634 */         Grid.curColor[((NodeList.nodeList[Grid.nCur]).cellLoc[i]).x][((NodeList.nodeList[Grid.nCur]).cellLoc[i]).y] = set ? 61132 : 16777215;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   static void changeCursor() {
/* 1640 */     if (Grid.nCur != -1) {
/* 1641 */       focusColor(false);
/* 1642 */       (NodeList.nodeList[Grid.nCur]).clue = jtaTheClue.getText();
/*      */     } 
/*      */     
/* 1645 */     int targetNodeA = Grid.horz[Grid.xNew][Grid.yNew];
/* 1646 */     int targetNodeD = Grid.vert[Grid.xNew][Grid.yNew];
/* 1647 */     if (Grid.xCur == Grid.xNew && Grid.yCur == Grid.yNew) {
/* 1648 */       if (targetNodeA != -1 && targetNodeD != -1) {
/* 1649 */         Grid.nCur = (Grid.nCur == targetNodeA) ? targetNodeD : targetNodeA;
/*      */       }
/*      */     } else {
/* 1652 */       if ((targetNodeA != Grid.nCur && targetNodeD != Grid.nCur) || Grid.nCur == -1)
/*      */       {
/*      */         
/* 1655 */         if (Grid.xCur == Grid.xNew) {
/* 1656 */           Grid.nCur = (targetNodeD != -1) ? targetNodeD : targetNodeA;
/*      */         } else {
/* 1658 */           Grid.nCur = (targetNodeA != -1) ? targetNodeA : targetNodeD;
/*      */         }  } 
/* 1660 */       Grid.xCur = Grid.xNew;
/* 1661 */       Grid.yCur = Grid.yNew;
/*      */     } 
/* 1663 */     if (Grid.nCur != -1) {
/* 1664 */       focusColor(true);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1669 */       jtaTheClue.setText((NodeList.nodeList[Grid.nCur]).clue);
/* 1670 */       jtaTheClue.requestFocusInWindow();
/* 1671 */       StringSelection stringSelection = new StringSelection((NodeList.nodeList[Grid.nCur]).word);
/* 1672 */       Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
/*      */     } 
/*      */   }
/*      */   
/*      */   void updateGrid(MouseEvent e) {
/* 1677 */     int x = e.getX(), y = e.getY();
/*      */     
/* 1679 */     if (Def.building == 1)
/*      */       return; 
/* 1681 */     if (x < Grid.xOrg || y < Grid.yOrg)
/*      */       return; 
/* 1683 */     int i = (x - Grid.xOrg) / Grid.xCell;
/* 1684 */     int j = (y - Grid.yOrg) / Grid.yCell;
/* 1685 */     if (i >= Grid.xSz || j >= Grid.ySz)
/*      */       return; 
/* 1687 */     i = Grid.RorL(i);
/* 1688 */     Grid.xNew = i;
/* 1689 */     Grid.yNew = j;
/* 1690 */     changeCursor();
/* 1691 */     Def.dispCursor = Boolean.valueOf(true);
/* 1692 */     restoreFrame();
/*      */   }
/*      */ }


/* Location:              C:\Users\mustapha\Desktop\Crossword-Express.jar!\crosswordexpress\DevanagariBuild.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */