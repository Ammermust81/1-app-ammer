/*      */ package crosswordexpress;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.io.DataInputStream;
/*      */ import javax.swing.JDialog;
/*      */ import javax.swing.JFrame;
/*      */ 
/*      */ public class FindAQuote {
/*      */   static JFrame jfFindAQuote;
/*      */   static JMenuBar menuBar;
/*      */   JMenu menu;
/*      */   JMenu submenu;
/*      */   JMenuItem menuItem;
/*      */   JMenuItem buildMenuItem;
/*      */   static JPanel pp;
/*      */   static int panelW;
/*      */   static int panelH;
/*   18 */   static int[] vec = new int[2500]; static int t; static int l; static int w; static int h; static int b; static JLabel jl1; static JLabel jl2; Timer myTimer; Thread thread;
/*      */   static boolean commandDrawPuz;
/*      */   static boolean drawAll = true;
/*      */   
/*      */   static void def() {
/*   23 */     Op.updateOption(Op.FQ.FqBg.ordinal(), "FFFFFF", Op.fq);
/*   24 */     Op.updateOption(Op.FQ.FqLetterColor.ordinal(), "550000", Op.fq);
/*   25 */     Op.updateOption(Op.FQ.FqLetterFont.ordinal(), "SansSerif", Op.fq);
/*   26 */     Op.updateOption(Op.FQ.FqPuzColor.ordinal(), "true", Op.fq);
/*   27 */     Op.updateOption(Op.FQ.FqW.ordinal(), "750", Op.fq);
/*   28 */     Op.updateOption(Op.FQ.FqH.ordinal(), "1080", Op.fq);
/*   29 */     Op.updateOption(Op.FQ.FqPuz.ordinal(), "sample.findaquote", Op.fq);
/*   30 */     Op.updateOption(Op.FQ.FqBorder.ordinal(), "00AAAA", Op.fq);
/*   31 */     Op.updateOption(Op.FQ.FqSolColor.ordinal(), "DDDDCC", Op.fq);
/*   32 */     Op.updateOption(Op.FQ.FqQ.ordinal(), "\"The quality of mercy is not strained; It droppeth as the gentle rain from heaven Upon the place beneath. It is twice blest; It blesseth him that gives and him that takes:\" The Merchant of Venice, Act IV, Scene I : William Shakespeare", Op.fq);
/*   33 */     Op.updateOption(Op.FQ.FqTitleFont.ordinal(), "SansSerif", Op.fq);
/*   34 */     Op.updateOption(Op.FQ.FqTitleColor.ordinal(), "666600", Op.fq);
/*      */   }
/*      */   
/*   37 */   String findAQuoteHelp = "<div>A <b>FIND-A-QUOTE</b> puzzle doesn't really qualify as a puzzle. It is in fact an education aid to assist with the teaching of letter and word recognition skills to young students. It consists of a fragment of text printed above a maze of letters, and within this maze of letters, the text can be found starting at the top left of the maze, and ending at the bottom right. Between these two points, the text meanders randomly throughout the maze. The task of the student is to trace the path of the text by drawing a circle around each successive letter of the text.</div><span class='m'>Menu Functions</span><ul><li/><span class='s'>File Menu</span><ul><li/><span>Load a Puzzle</span><br/>This option provides you with a list of available Find-A-Quote puzzles from which you can select the required puzzle.<p/><li/><span>SaveAs</span><br/>This option allows you to make an exact copy of the current puzzle using a different file name. The copy will be saved in the same folder as the original puzzle. Alternatively, if you don't enter a new name for the puzzle, you can change the Puzzle Description, or any of the other descriptive items without changing the puzzle name.<p/><li/><span>Quit Construction</span><br/>Returns you to the Crossword Express menu screen.</ul><li/><span class='s'>Build Menu</span><ul><li/><span>Start a New Puzzle</span><br/>This option presents you with a dialog into which you can enter a file name for your new puzzle. You can also enter several other pieces of information such as a <b>Puzzle Title, Author</b> and <b>Copyright</b> information.<p/><li/><span>Build Options</span><br/>Use this option to access a Build Options dialog where you can set some rules to be followed by the puzzle building function as it builds your puzzle.<p/><li/><span>Edit Offensive Words List</span><br/>After Crossword Express has positioned the quote into the maze area, it places random letters into the unoccupied locations of the maze. It is possible for these letters to combine randomly with the letters of the quote to create extraneous unwanted words, including possibly offensive words. Using this option, you can create a list of all of the words which you consider to be offensive, and Crossword Express will ensure that they do not appear anywhere within you puzzle.<p/><li/><span>Start Building / Stop Building</span><br/>Construction of the puzzle will commence when you select the <b>Start Building</b> option. When puzzle building has been completed you will receive a message containing the name of the puzzle file, and the location where it was saved. If it becomes necessary, you can interrupt the building process by selecting this option a second time. Note that during the construction phase, the text of this option is changed to <b>Stop Building</b></ul><li/><span class='s'>View Menu</span><ul><li/><span>Display Options</span><br/>This leads you to a dialog box in which you can change the colors of various elements within the puzzle, and control the fonts which will be used for the puzzle's text components.</ul><li/><span class='s'>Tasks Menu</span><ul><li/><span>Print this Puzzle</span><br/>This will take you to a custom print screen where you can control the details involved with printing your puzzle.<p/><li/><span>Print the Solution</span><br/>As above, except that a solution will be printed. In a solution, the background of letters contained in the quote are printed in a different color.<p/><li/><span>Print Letter Matrix</span><br/>In this function, only the matrix of letters will be printed or exported. This will allow the user to apply whatever border treatment suits their requirements.<p/><li/><span>Delete this Puzzle</span><br/>Use this option to eliminate unwanted Find-A-Quote puzzles from your file system.</ul><li/><span class='s'>Help Menu</span><li/><span>Word-search Help</span><br/>Displays the Help screen which you are now reading.</ul></ul></body>";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  100 */   String findAQuoteOptions = "<div>The only option involved here is the text of the quote. There are several rules which must be observed:-<br/><br/><ul><li/>The portion of the text which is to be included in the letter maze must be surrounded by double quote characters. If there are any quotes characters contained within this text, then they must be included as single quote characters.<p/><li/>The additional text following the quoted text will not be included in the letter maze. Normally, this will be an attribution as to the source of the quote. It will however be included in the text printed immediately above the puzzle. Crossword Express demands that an attribution be included, as the build function will not proceed without it.<p/><li/>The recommended maximum length for the quote is 150 characters. You will receive an error message if you exceed this value.<p/><li/>The recommended minimum length for the quote is 80 characters. Once again, you will receive an error message if you provide a quote having a shorter length.</ul></body>";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   FindAQuote(JFrame jf) {
/*  116 */     Def.puzzleMode = 72;
/*  117 */     Def.building = 0;
/*  118 */     Def.dispSolArray = Boolean.valueOf(true); Def.dispCursor = Boolean.valueOf(true); Def.dispGuideDigits = Boolean.valueOf(false);
/*  119 */     makeGrid();
/*  120 */     jfFindAQuote = new JFrame("Find A Quote");
/*  121 */     jfFindAQuote.setSize(Op.getInt(Op.FQ.FqW.ordinal(), Op.fq), Op.getInt(Op.FQ.FqH.ordinal(), Op.fq));
/*  122 */     int frameX = (jf.getX() + jfFindAQuote.getWidth() > Methods.scrW) ? (Methods.scrW - jfFindAQuote.getWidth() - 10) : jf.getX();
/*  123 */     jfFindAQuote.setLocation(frameX, jf.getY());
/*  124 */     jfFindAQuote.setLayout((LayoutManager)null);
/*  125 */     jfFindAQuote.setDefaultCloseOperation(0);
/*  126 */     jfFindAQuote
/*  127 */       .addComponentListener(new ComponentAdapter() {
/*      */           public void componentResized(ComponentEvent ce) {
/*  129 */             int oldw = Op.getInt(Op.FQ.FqW.ordinal(), Op.fq);
/*  130 */             int oldh = Op.getInt(Op.FQ.FqH.ordinal(), Op.fq);
/*  131 */             Methods.frameResize(FindAQuote.jfFindAQuote, oldw, oldh, 750, 1080);
/*  132 */             Op.setInt(Op.FQ.FqW.ordinal(), FindAQuote.jfFindAQuote.getWidth(), Op.fq);
/*  133 */             Op.setInt(Op.FQ.FqH.ordinal(), FindAQuote.jfFindAQuote.getHeight(), Op.fq);
/*  134 */             FindAQuote.restoreFrame();
/*      */           }
/*      */         });
/*      */     
/*  138 */     jfFindAQuote
/*  139 */       .addWindowListener(new WindowAdapter() {
/*      */           public void windowClosing(WindowEvent we) {
/*  141 */             if (Def.building == 1 || Def.selecting)
/*  142 */               return;  Op.saveOptions("findaquote.opt", Op.fq);
/*  143 */             CrosswordExpress.transfer(1, FindAQuote.jfFindAQuote);
/*      */           }
/*      */         });
/*      */     
/*  147 */     Methods.closeHelp();
/*      */ 
/*      */     
/*  150 */     Runnable buildThread = () -> {
/*      */         do {
/*      */         
/*      */         } while (buildFindAQuote() != true);
/*      */         this.buildMenuItem.setText("Start Building");
/*      */         if (Def.building == 2) {
/*      */           Def.building = 0;
/*      */           Methods.interrupted(jfFindAQuote);
/*      */           makeGrid();
/*      */           restoreFrame();
/*      */           return;
/*      */         } 
/*      */         Methods.havePuzzle = true;
/*      */         restoreFrame();
/*      */         Methods.puzzleSaved(jfFindAQuote, "findaquote", Op.fq[Op.FQ.FqPuz.ordinal()]);
/*      */         Def.building = 0;
/*      */       };
/*  167 */     jl1 = new JLabel(); jfFindAQuote.add(jl1);
/*  168 */     jl2 = new JLabel(); jfFindAQuote.add(jl2);
/*      */ 
/*      */     
/*  171 */     menuBar = new JMenuBar();
/*  172 */     menuBar.setBackground(Def.COLOR_MENUBAR);
/*  173 */     jfFindAQuote.setJMenuBar(menuBar);
/*      */     
/*  175 */     this.menu = new JMenu("File");
/*  176 */     menuBar.add(this.menu);
/*  177 */     this.menuItem = new JMenuItem("Load a Puzzle");
/*  178 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(76, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  179 */     this.menu.add(this.menuItem);
/*  180 */     this.menuItem
/*  181 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1)
/*      */             return; 
/*      */           pp.invalidate();
/*      */           pp.repaint();
/*      */           new Select(jfFindAQuote, "findaquote", "findaquote", Op.fq, Op.FQ.FqPuz.ordinal(), false);
/*      */         });
/*  188 */     this.menuItem = new JMenuItem("SaveAs");
/*  189 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(65, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  190 */     this.menu.add(this.menuItem);
/*  191 */     this.menuItem
/*  192 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           Methods.puzzleDescriptionDialog(jfFindAQuote, Op.fq[Op.FQ.FqPuz.ordinal()].substring(0, Op.fq[Op.FQ.FqPuz.ordinal()].indexOf(".findaquote")), "findaquote", ".findaquote");
/*      */           if (Methods.clickedOK) {
/*      */             saveFindAQuote(Op.fq[Op.FQ.FqPuz.ordinal()] = Methods.theFileName);
/*      */             restoreFrame();
/*      */             Methods.puzzleSaved(jfFindAQuote, "findaquote", Op.fq[Op.FQ.FqPuz.ordinal()]);
/*      */           } 
/*      */         });
/*  203 */     this.menuItem = new JMenuItem("Quit Construction");
/*  204 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(81, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  205 */     this.menu.add(this.menuItem);
/*  206 */     this.menuItem
/*  207 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           
/*      */           Op.saveOptions("findaquote.opt", Op.fq);
/*      */           CrosswordExpress.transfer(1, jfFindAQuote);
/*      */         });
/*  215 */     this.menu = new JMenu("Build");
/*  216 */     menuBar.add(this.menu);
/*  217 */     this.menuItem = new JMenuItem("Start a new Puzzle");
/*  218 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(78, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  219 */     this.menu.add(this.menuItem);
/*  220 */     this.menuItem
/*  221 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           Methods.puzzleDescriptionDialog(jfFindAQuote, Op.fq[Op.FQ.FqPuz.ordinal()].substring(0, Op.fq[Op.FQ.FqPuz.ordinal()].indexOf(".findaquote")), "findaquote", ".findaquote");
/*      */           if (Methods.clickedOK) {
/*      */             Op.fq[Op.FQ.FqPuz.ordinal()] = Methods.theFileName;
/*      */             makeGrid();
/*      */           } 
/*      */           restoreFrame();
/*      */         });
/*  232 */     this.menuItem = new JMenuItem("Build Options");
/*  233 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(79, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  234 */     this.menu.add(this.menuItem);
/*  235 */     this.menuItem
/*  236 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           findAQuoteOptions(paramJFrame);
/*      */           if (Methods.clickedOK)
/*      */             makeGrid(); 
/*      */           restoreFrame();
/*      */         });
/*  245 */     this.menuItem = new JMenuItem("Edit Offensive Word List");
/*  246 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(69, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  247 */     this.menu.add(this.menuItem);
/*  248 */     this.menuItem
/*  249 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           
/*      */           WordsearchBuild.editOffensiveWords(jfFindAQuote);
/*      */         });
/*  256 */     this.buildMenuItem = new JMenuItem("Start Building");
/*  257 */     this.buildMenuItem.setAccelerator(KeyStroke.getKeyStroke(66, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  258 */     this.menu.add(this.buildMenuItem);
/*  259 */     this.buildMenuItem
/*  260 */       .addActionListener(ae -> {
/*      */           int quoteLen = extractQuote(Op.fq[Op.FQ.FqQ.ordinal()]).length();
/*      */ 
/*      */           
/*      */           if (quoteLen > 150) {
/*      */             JOptionPane.showMessageDialog(paramJFrame, "<html><center>Your quote is too long.<br>Please limit the number of letters and/or digits to 150.", "Input rejected", 0);
/*      */           } else if (quoteLen < 80) {
/*      */             JOptionPane.showMessageDialog(paramJFrame, "<html><center>Your quote is too short.<br>Please expand the number of letters and/or digits to at least 80.", "Input rejected", 0);
/*      */           } else if (Op.fq[Op.FQ.FqPuz.ordinal()].length() == 0) {
/*      */             Methods.noName(jfFindAQuote);
/*      */           } else if (Def.building == 0) {
/*      */             this.thread = new Thread(paramRunnable);
/*      */ 
/*      */             
/*      */             this.thread.start();
/*      */ 
/*      */             
/*      */             Def.building = 1;
/*      */ 
/*      */             
/*      */             this.buildMenuItem.setText("Stop Building");
/*      */           } else {
/*      */             Def.building = 2;
/*      */           } 
/*      */         });
/*      */     
/*  286 */     this.menu = new JMenu("View");
/*  287 */     menuBar.add(this.menu);
/*  288 */     this.menuItem = new JMenuItem("Display Options");
/*  289 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(89, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  290 */     this.menu.add(this.menuItem);
/*  291 */     this.menuItem
/*  292 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           
/*      */           printOptions(jfFindAQuote, "Display Options");
/*      */           restoreFrame();
/*      */         });
/*  300 */     this.menu = new JMenu("Tasks");
/*  301 */     menuBar.add(this.menu);
/*  302 */     this.menuItem = new JMenuItem("Print this Puzzle");
/*  303 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(80, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  304 */     this.menu.add(this.menuItem);
/*  305 */     this.menuItem
/*  306 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           commandDrawPuz = true;
/*      */           CrosswordExpress.toPrint(jfFindAQuote, Op.fq[Op.FQ.FqPuz.ordinal()]);
/*      */         });
/*  313 */     this.menuItem = new JMenuItem("Print the Solution");
/*  314 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(83, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  315 */     this.menu.add(this.menuItem);
/*  316 */     this.menuItem
/*  317 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           
/*      */           CrosswordExpress.toPrint(jfFindAQuote, Op.fq[Op.FQ.FqPuz.ordinal()]);
/*      */         });
/*  324 */     this.menuItem = new JMenuItem("Print Letter Matrix");
/*  325 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(77, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  326 */     this.menu.add(this.menuItem);
/*  327 */     this.menuItem
/*  328 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           drawAll = false;
/*      */           commandDrawPuz = true;
/*      */           Methods.printAPuzzle(jfFindAQuote, Op.fq[Op.FQ.FqPuz.ordinal()]);
/*      */           jfFindAQuote.setVisible(false);
/*      */         });
/*  337 */     this.menuItem = new JMenuItem("Delete this Puzzle");
/*  338 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(68, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  339 */     this.menu.add(this.menuItem);
/*  340 */     this.menuItem
/*  341 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           
/*      */           if (Methods.deleteAPuzzle(jfFindAQuote, Op.fq[Op.FQ.FqPuz.ordinal()], "findaquote", pp)) {
/*      */             makeGrid();
/*      */             loadFindAQuote(Op.fq[Op.FQ.FqPuz.ordinal()]);
/*      */             restoreFrame();
/*      */           } 
/*      */         });
/*  352 */     this.menu = new JMenu("Help");
/*  353 */     menuBar.add(this.menu);
/*  354 */     this.menuItem = new JMenuItem("Find A Quote Help");
/*  355 */     this.menu.add(this.menuItem);
/*  356 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(72, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  357 */     this.menuItem
/*  358 */       .addActionListener(ae -> Methods.cweHelp(jfFindAQuote, null, "Building Find A Quote Puzzles", this.findAQuoteHelp));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  364 */     pp = new FindAQuotePP(0, 37);
/*  365 */     jfFindAQuote.add(pp);
/*      */     
/*  367 */     loadFindAQuote(Op.fq[Op.FQ.FqPuz.ordinal()]);
/*  368 */     restoreFrame();
/*      */   }
/*      */   
/*      */   static void restoreFrame() {
/*  372 */     jfFindAQuote.setVisible(true);
/*  373 */     Insets insets = jfFindAQuote.getInsets();
/*  374 */     panelW = jfFindAQuote.getWidth() - insets.left + insets.right;
/*  375 */     panelH = jfFindAQuote.getHeight() - insets.top + insets.bottom + 37 + menuBar.getHeight();
/*  376 */     pp.setSize(panelW, panelH);
/*  377 */     jfFindAQuote.requestFocusInWindow();
/*  378 */     pp.repaint();
/*  379 */     Methods.infoPanel(jl1, jl2, "Build FindAQuote", "Puzzle : " + Op.fq[Op.FQ.FqPuz.ordinal()], panelW);
/*  380 */     commandDrawPuz = false;
/*  381 */     drawAll = true;
/*      */   }
/*      */   
/*      */   static void setSizesAndOffsets(int x, int y, int width, int height, int inset) {
/*  385 */     if (drawAll) {
/*  386 */       if (height / width > 1.2978723F) {
/*  387 */         l = x + inset;
/*  388 */         w = width - 2 * inset;
/*  389 */         h = w * 610 / 470;
/*  390 */         t = y + (height - h) / 2;
/*      */       } else {
/*      */         
/*  393 */         t = y + inset;
/*  394 */         h = height - 2 * inset;
/*  395 */         w = h * 470 / 610;
/*  396 */         l = x + (width - w) / 2;
/*      */       } 
/*  398 */       b = 52 * w / 470;
/*  399 */       Grid.xCell = Grid.yCell = (w - 2 * b) / 19;
/*  400 */       Grid.xOrg = l + (w - 15 * Grid.xCell) / 2;
/*  401 */       Grid.yOrg = t + h - b - 24 * Grid.yCell;
/*      */     } else {
/*      */       
/*  404 */       Grid.xCell = Grid.yCell = width / 15;
/*  405 */       Grid.xOrg = x;
/*  406 */       Grid.yOrg = y;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final void findAQuoteOptions(JFrame jf) {
/*  413 */     JDialog jdlgFindAQuote = new JDialog(jfFindAQuote, "Find A Quote Options", true);
/*  414 */     jdlgFindAQuote.setSize(365, 281);
/*  415 */     jdlgFindAQuote.setResizable(false);
/*  416 */     jdlgFindAQuote.setLayout((LayoutManager)null);
/*  417 */     jdlgFindAQuote.setLocation(jfFindAQuote.getX(), jfFindAQuote.getY());
/*      */     
/*  419 */     jdlgFindAQuote
/*  420 */       .addWindowListener(new WindowAdapter() {
/*      */           public void windowClosing(WindowEvent we) {
/*  422 */             Methods.closeHelp();
/*      */           }
/*      */         });
/*      */     
/*  426 */     Methods.closeHelp();
/*      */     
/*  428 */     JLabel jlQuote = new JLabel("Quote:");
/*  429 */     jlQuote.setForeground(Def.COLOR_LABEL);
/*  430 */     jlQuote.setSize(117, 20);
/*  431 */     jlQuote.setLocation(5, 5);
/*  432 */     jlQuote.setHorizontalAlignment(2);
/*  433 */     jdlgFindAQuote.add(jlQuote);
/*      */     
/*  435 */     JTextArea jtaQuote = new JTextArea(Op.fq[Op.FQ.FqQ.ordinal()]);
/*  436 */     jtaQuote.setLineWrap(true);
/*  437 */     jtaQuote.setWrapStyleWord(true);
/*  438 */     JScrollPane jsp = new JScrollPane(jtaQuote);
/*  439 */     jsp.setSize(580, 120);
/*  440 */     jsp.setLocation(5, 30);
/*  441 */     jtaQuote.selectAll();
/*  442 */     jdlgFindAQuote.add(jsp);
/*  443 */     jtaQuote.setFont(new Font("Arial", 0, 18));
/*  444 */     jtaQuote.setMargin(new Insets(10, 10, 10, 10));
/*      */     
/*  446 */     JButton jbOK = Methods.cweButton("OK", 180, 160, 80, 26, null);
/*  447 */     jbOK.addActionListener(e -> {
/*      */           Methods.clickedOK = true;
/*      */           Op.fq[Op.FQ.FqQ.ordinal()] = paramJTextArea.getText();
/*      */           paramJDialog.dispose();
/*      */           Methods.closeHelp();
/*      */         });
/*  453 */     jdlgFindAQuote.add(jbOK);
/*      */     
/*  455 */     JButton jbCancel = Methods.cweButton("Cancel", 180, 195, 80, 26, null);
/*  456 */     jbCancel.addActionListener(e -> {
/*      */           Methods.clickedOK = false;
/*      */           paramJDialog.dispose();
/*      */           Methods.closeHelp();
/*      */         });
/*  461 */     jdlgFindAQuote.add(jbCancel);
/*      */     
/*  463 */     JButton jbHelp = Methods.cweButton("<html><font size=6 color=BB0000 face=Serif>Help ", 270, 160, 150, 61, new ImageIcon("graphics/help.png"));
/*  464 */     jbHelp.addActionListener(e -> Methods.cweHelp(null, paramJDialog, "Find A Quote Options", this.findAQuoteOptions));
/*      */     
/*  466 */     jdlgFindAQuote.add(jbHelp);
/*      */     
/*  468 */     jdlgFindAQuote.getRootPane().setDefaultButton(jbOK);
/*  469 */     Methods.setDialogSize(jdlgFindAQuote, 600, 230);
/*      */   }
/*      */   
/*      */   static void printOptions(JFrame jf, String type) {
/*  473 */     String[] colorLabel = { "Background Color", "Letter Color", "Border Color", "Title Color" };
/*  474 */     int[] colorInt = { Op.FQ.FqBg.ordinal(), Op.FQ.FqLetterColor.ordinal(), Op.FQ.FqBorder.ordinal(), Op.FQ.FqTitleColor.ordinal() };
/*  475 */     String[] fontLabel = { "Select Letter Font", "Select Title Font" };
/*  476 */     int[] fontInt = { Op.FQ.FqLetterFont.ordinal(), Op.FQ.FqTitleFont.ordinal() };
/*  477 */     String[] checkLabel = { "" };
/*  478 */     int[] checkInt = { 0 };
/*  479 */     Methods.stdPrintOptions(jf, "FindAQuote " + type, Op.fq, colorLabel, colorInt, fontLabel, fontInt, checkLabel, checkInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void saveFindAQuote(String findaquoteName) {
/*      */     try {
/*  489 */       DataOutputStream dataOut = new DataOutputStream(new FileOutputStream("findaquote/" + findaquoteName));
/*  490 */       dataOut.writeInt(Grid.xSz);
/*  491 */       dataOut.writeInt(Grid.ySz);
/*  492 */       dataOut.writeByte(Methods.noReveal);
/*  493 */       dataOut.writeByte(Methods.noErrors); int x;
/*  494 */       for (x = 0; x < 54; x++)
/*  495 */         dataOut.writeByte(0); 
/*  496 */       for (int y = 0; y < Grid.ySz; y++) {
/*  497 */         for (x = 0; x < Grid.xSz; x++) {
/*  498 */           dataOut.writeInt(Grid.mode[x][y]);
/*  499 */           dataOut.writeInt(Grid.puz[x][y]);
/*      */         } 
/*  501 */       }  dataOut.writeUTF(Op.fq[Op.FQ.FqQ.ordinal()]);
/*  502 */       dataOut.writeUTF(Methods.puzzleTitle);
/*  503 */       dataOut.writeUTF(Methods.author);
/*  504 */       dataOut.writeUTF(Methods.copyright);
/*  505 */       dataOut.writeUTF(Methods.puzzleNumber);
/*  506 */       dataOut.writeUTF(Methods.puzzleNotes);
/*  507 */       dataOut.close();
/*      */     }
/*  509 */     catch (IOException exc) {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void loadFindAQuote(String findaquoteName) {
/*      */     
/*  517 */     try { File fl = new File("findaquote/" + findaquoteName);
/*  518 */       if (!fl.exists()) {
/*  519 */         fl = new File("findaquote/");
/*  520 */         String[] s = fl.list(); int k;
/*  521 */         for (k = 0; k < s.length && (
/*  522 */           s[k].lastIndexOf(".findaquote") == -1 || s[k].charAt(0) == '.'); k++);
/*      */         
/*  524 */         if (k == s.length) { makeGrid(); return; }
/*  525 */          findaquoteName = s[k];
/*  526 */         Op.fq[Op.FQ.FqPuz.ordinal()] = findaquoteName;
/*      */       } 
/*      */       
/*  529 */       DataInputStream dataIn = new DataInputStream(new FileInputStream("findaquote/" + findaquoteName));
/*  530 */       Grid.xSz = dataIn.readInt();
/*  531 */       Grid.ySz = dataIn.readInt();
/*  532 */       Methods.noReveal = dataIn.readByte();
/*  533 */       Methods.noErrors = dataIn.readByte(); int i;
/*  534 */       for (i = 0; i < 54; i++)
/*  535 */         dataIn.readByte(); 
/*  536 */       for (int j = 0; j < Grid.ySz; j++) {
/*  537 */         for (i = 0; i < Grid.xSz; i++) {
/*  538 */           Grid.mode[i][j] = dataIn.readInt();
/*  539 */           Grid.puz[i][j] = dataIn.readInt();
/*      */         } 
/*  541 */       }  Op.fq[Op.FQ.FqQ.ordinal()] = dataIn.readUTF();
/*  542 */       Methods.puzzleTitle = dataIn.readUTF();
/*  543 */       Methods.author = dataIn.readUTF();
/*  544 */       Methods.copyright = dataIn.readUTF();
/*  545 */       Methods.puzzleNumber = dataIn.readUTF();
/*  546 */       Methods.puzzleNotes = dataIn.readUTF();
/*  547 */       dataIn.close(); }
/*      */     
/*  549 */     catch (IOException exc) { return; }
/*  550 */      Methods.havePuzzle = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void drawFindAQuote(Graphics2D g2) {
/*  558 */     int[] xPoints = new int[8], yPoints = new int[8];
/*      */     
/*  560 */     float normal = Grid.xCell / 25.0F;
/*  561 */     Stroke normalStroke = new BasicStroke(normal, 2, 2);
/*  562 */     Stroke wideStroke = new BasicStroke(Grid.xCell / 8.0F, 2, 2);
/*  563 */     g2.setStroke(normalStroke);
/*      */     
/*  565 */     RenderingHints rh = g2.getRenderingHints();
/*  566 */     rh.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
/*  567 */     rh.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
/*  568 */     g2.setRenderingHints(rh);
/*      */ 
/*      */     
/*  571 */     if (drawAll) {
/*  572 */       g2.setColor(new Color(Op.getColorInt(Op.FQ.FqBg.ordinal(), Op.fq)));
/*  573 */       g2.fillRect(l, t, w, h);
/*  574 */       g2.setStroke(wideStroke);
/*  575 */       g2.setColor(new Color(Op.getColorInt(Op.FQ.FqBorder.ordinal(), Op.fq)));
/*  576 */       g2.drawRect(l, t, w, h);
/*  577 */       g2.drawRect(l + b, t + b, w - 2 * b, h - 2 * b);
/*      */       
/*  579 */       g2.drawOval(l, t + h - b, b, b);
/*  580 */       g2.drawOval(l + w - b, t + h - b, b, b); int i;
/*  581 */       for (i = 1; i < 21; i++) {
/*  582 */         g2.drawOval(l, t + h - b - i * b / 2, b, b / 2);
/*  583 */         g2.drawOval(l + w - b, t + h - b - i * b / 2, b, b / 2);
/*      */       } 
/*  585 */       for (i = 1; i < 8; i++) {
/*  586 */         g2.drawOval(l + (i + 1) * b / 2, t + h - b, b / 2, b);
/*  587 */         g2.drawOval(l + w - b - i * b / 2, t + h - b, b / 2, b);
/*      */       } 
/*      */     } 
/*      */     
/*      */     int j;
/*  592 */     for (j = 0; j < Grid.ySz; j++) {
/*  593 */       for (int i = 0; i < Grid.xSz; i++) {
/*  594 */         int k; if (!commandDrawPuz) {
/*  595 */           k = (Grid.mode[i][j] == 0) ? Op.getColorInt(Op.FQ.FqBg.ordinal(), Op.fq) : Op.getColorInt(Op.FQ.FqSolColor.ordinal(), Op.fq);
/*      */         } else {
/*  597 */           k = 16777215;
/*  598 */         }  g2.setColor(new Color(k));
/*  599 */         g2.fillRect(Grid.xOrg + i * Grid.xCell, Grid.yOrg + j * Grid.yCell, Grid.xCell, Grid.yCell);
/*      */       } 
/*      */     } 
/*  602 */     int theColor = Op.getColorInt(Op.FQ.FqBg.ordinal(), Op.fq);
/*  603 */     g2.setColor(new Color(theColor));
/*  604 */     g2.fillRect(Grid.xOrg - Grid.xCell, Grid.yOrg, Grid.xCell, Grid.yCell);
/*  605 */     g2.fillRect(Grid.xOrg + Grid.xCell * Grid.xSz, Grid.yOrg + Grid.yCell * (Grid.ySz - 1), Grid.xCell, Grid.yCell);
/*      */     
/*  607 */     g2.setStroke(normalStroke);
/*  608 */     g2.setColor(Def.COLOR_BLACK);
/*  609 */     int x = Grid.xOrg - Grid.xCell, y = Grid.yOrg;
/*  610 */     if (drawAll) {
/*  611 */       for (int i = 0; i < 8; i++) {
/*  612 */         switch (i) { case 0:
/*  613 */             x = Grid.xOrg - Grid.xCell; y = Grid.yOrg; break;
/*  614 */           case 1: x += Grid.xCell * (Grid.xSz + 1); break;
/*  615 */           case 2: y += Grid.yCell * (Grid.ySz - 1); break;
/*  616 */           case 3: x += Grid.xCell; break;
/*  617 */           case 4: y += Grid.yCell; break;
/*  618 */           case 5: x -= Grid.xCell * (Grid.xSz + 1); break;
/*  619 */           case 6: y -= Grid.yCell * (Grid.ySz - 1); break;
/*  620 */           case 7: x -= Grid.xCell; break; }
/*      */         
/*  622 */         xPoints[i] = x; yPoints[i] = y;
/*      */       } 
/*  624 */       g2.drawPolygon(xPoints, yPoints, 8);
/*      */     } else {
/*      */       
/*  627 */       g2.drawLine(Grid.xOrg, Grid.yOrg, Grid.xOrg + Grid.xCell * Grid.xSz, Grid.yOrg);
/*  628 */       g2.drawLine(Grid.xOrg + Grid.xCell * Grid.xSz, Grid.yOrg, Grid.xOrg + Grid.xCell * Grid.xSz, Grid.yOrg + Grid.yCell * (Grid.ySz - 1));
/*  629 */       g2.drawLine(Grid.xOrg, Grid.yOrg + Grid.yCell, Grid.xOrg, Grid.yOrg + Grid.yCell * Grid.ySz);
/*  630 */       g2.drawLine(Grid.xOrg, Grid.yOrg + Grid.yCell * Grid.ySz, Grid.xOrg + Grid.xCell * Grid.xSz, Grid.yOrg + Grid.yCell * Grid.ySz);
/*      */     } 
/*      */ 
/*      */     
/*  634 */     g2.setColor(new Color(Op.getColorInt(Op.FQ.FqLetterColor.ordinal(), Op.fq)));
/*  635 */     g2.setFont(new Font(Op.fq[Op.FQ.FqLetterFont.ordinal()], 0, 6 * Grid.yCell / 10));
/*  636 */     FontMetrics fm = g2.getFontMetrics();
/*  637 */     for (j = 0; j < Grid.ySz; j++) {
/*  638 */       for (int i = 0; i < Grid.xSz; i++) {
/*  639 */         String str = "" + (char)Grid.puz[i][j];
/*  640 */         int gap = fm.stringWidth(str);
/*  641 */         g2.drawString(str, Grid.xOrg + i * Grid.xCell + (Grid.xCell - gap) / 2, Grid.yOrg + j * Grid.yCell + (Grid.yCell + fm.getAscent() - fm.getDescent()) / 2);
/*      */       } 
/*  643 */     }  if (drawAll) {
/*  644 */       g2.setFont(new Font(Op.fq[Op.FQ.FqLetterFont.ordinal()], 0, 10 * Grid.yCell / 10));
/*  645 */       fm = g2.getFontMetrics();
/*  646 */       String str = "☺";
/*  647 */       int gap = fm.stringWidth(str);
/*  648 */       g2.drawString(str, Grid.xOrg + Grid.xCell * Grid.xSz + (Grid.xCell - gap) / 2, Grid.yOrg + Grid.yCell * Grid.ySz - Grid.yCell / 7);
/*  649 */       str = "→";
/*  650 */       gap = fm.stringWidth(str);
/*  651 */       g2.drawString(str, Grid.xOrg - Grid.xCell + (Grid.xCell - gap) / 2, Grid.yOrg + Grid.yCell - Grid.yCell / 7);
/*      */ 
/*      */       
/*  654 */       g2.setFont(new Font(Op.fq[Op.FQ.FqLetterFont.ordinal()], 0, 10));
/*  655 */       Methods.renderText(g2, Grid.xOrg - Grid.xCell, t + b, 17 * Grid.xCell, Grid.yOrg - t + b, "SansSerif", 0, Op.fq[Op.FQ.FqQ.ordinal()], 2, 5, true, 
/*  656 */           Op.getColorInt(Op.FQ.FqLetterColor.ordinal(), Op.fq), 0);
/*      */ 
/*      */       
/*  659 */       for (int fs = 200;; fs--) {
/*  660 */         g2.setFont(new Font(Op.fq[Op.FQ.FqTitleFont.ordinal()], 0, fs));
/*  661 */         g2.setColor(new Color(Op.getColorInt(Op.FQ.FqTitleColor.ordinal(), Op.fq)));
/*  662 */         fm = g2.getFontMetrics();
/*  663 */         gap = fm.stringWidth(Methods.puzzleTitle);
/*  664 */         if (fm.getHeight() < b && gap < w)
/*      */           break; 
/*  666 */       }  g2.drawString(Methods.puzzleTitle, l + (w - gap) / 2, t + (b - fm.getHeight()) / 2 + fm.getAscent());
/*      */     } 
/*      */   }
/*      */   
/*      */   static void printPuz(Graphics2D g2, int left, int top, int width, int height) {
/*  671 */     loadFindAQuote(Op.fq[Op.FQ.FqPuz.ordinal()]);
/*  672 */     setSizesAndOffsets(left, top, width, height, 0);
/*  673 */     drawFindAQuote(g2);
/*      */   }
/*      */   
/*      */   static void makeGrid() {
/*  677 */     Methods.havePuzzle = false;
/*  678 */     Grid.clearGrid();
/*  679 */     Grid.xSz = 15; Grid.ySz = 23;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean buildFindAQuote() {
/*  686 */     Random rnd = new Random();
/*      */     
/*  688 */     String qStr = extractQuote(Op.fq[Op.FQ.FqQ.ordinal()]);
/*      */     
/*  690 */     qStr = qStr.toUpperCase();
/*  691 */     int len = qStr.length();
/*  692 */     Grid.xSz = 15; Grid.ySz = 22;
/*  693 */     if (qStr.length() % 2 == 1)
/*  694 */       Grid.ySz++; 
/*  695 */     Grid.clearGrid();
/*      */     int y;
/*  697 */     for (y = 0; y < Grid.ySz; y++) {
/*  698 */       for (int k = 0; k < Grid.xSz; k++)
/*  699 */         Grid.puz[k][y] = 126; 
/*      */     }  int x;
/*  701 */     for (int i = 0; i < 6; i++) {
/*  702 */       int z; for (z = 0; z < 3; z++) {
/*  703 */         Grid.puz[x][y] = 94;
/*  704 */         if (x < Grid.xSz - 1) x++; 
/*      */       } 
/*  706 */       for (z = 0; z < 5; z++) {
/*  707 */         Grid.puz[x][y] = 94;
/*  708 */         if (y < Grid.ySz - 1) y++;
/*      */       
/*      */       } 
/*      */     } 
/*  712 */     bl(5); tr(5);
/*      */     
/*  714 */     for (int loop = 0;; loop++) {
/*  715 */       if (loop == 200) return false;  int z;
/*  716 */       for (z = x = 0; x < Grid.xSz; x++) {
/*  717 */         for (y = 0; y < Grid.ySz; y++)
/*  718 */         { if ((char)Grid.puz[x][y] == '^')
/*  719 */             z++;  } 
/*  720 */       }  int shortfall = len - z;
/*  721 */       if (shortfall > 12) {
/*  722 */         switch (rnd.nextInt(8)) { case 0:
/*  723 */             tl(4); break;
/*  724 */           case 1: tr(4); break;
/*  725 */           case 2: bl(4); break;
/*  726 */           case 3: br(4); break;
/*  727 */           case 4: tl(3); break;
/*  728 */           case 5: tr(3); break;
/*  729 */           case 6: bl(3); break;
/*  730 */           case 7: br(3);
/*      */             break; }
/*      */       
/*  733 */       } else if (shortfall > 0) {
/*  734 */         switch (rnd.nextInt(4)) { case 0:
/*  735 */             t(); break;
/*  736 */           case 1: l(); break;
/*  737 */           case 2: b(); break;
/*  738 */           case 3: r(); break; }
/*      */       
/*      */       } else {
/*      */         break;
/*      */       } 
/*      */     } 
/*  744 */     for (y = 0; y < Grid.ySz; y++) {
/*  745 */       for (x = 0; x < Grid.xSz; x++)
/*  746 */         Grid.mode[x][y] = 0; 
/*  747 */     }  for (int j = x = y = 0;; j++) {
/*  748 */       if (j == len) {
/*  749 */         Grid.mode[Grid.xSz - 1][Grid.ySz - 1] = -1;
/*      */         break;
/*      */       } 
/*  752 */       Grid.puz[x][y] = qStr.charAt(j);
/*  753 */       if (x < Grid.xSz - 1 && (char)Grid.puz[x + 1][y] == '^' && Grid.mode[x + 1][y] == 0) { Grid.mode[x][y] = y * Grid.xSz + ++x; }
/*  754 */       else if (y < Grid.ySz - 1 && (char)Grid.puz[x][y + 1] == '^' && Grid.mode[x][y + 1] == 0) { Grid.mode[x][y++] = y * Grid.xSz + x; }
/*  755 */       else if (x > 0 && (char)Grid.puz[x - 1][y] == '^' && Grid.mode[x - 1][y] == 0) { Grid.mode[x][y] = y * Grid.xSz + --x; }
/*  756 */       else if (y > 0 && (char)Grid.puz[x][y - 1] == '^' && Grid.mode[x][y - 1] == 0) { Grid.mode[x][y--] = y * Grid.xSz + x; }
/*      */     
/*      */     } 
/*  759 */     for (y = 0; y < Grid.ySz; y++) {
/*  760 */       for (x = 0; x < Grid.xSz; x++) {
/*  761 */         fillCell(qStr, x, y);
/*      */       }
/*      */     } 
/*      */     
/*      */     try {
/*  766 */       BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("badwords.txt"), "UTF-8")); String badword;
/*  767 */       while ((badword = br.readLine()) != null) {
/*  768 */         if (badword.length() > 0 && 
/*  769 */           unwantedWord(badword) > 0)
/*  770 */           return false; 
/*      */       } 
/*  772 */       br.close();
/*      */     }
/*  774 */     catch (IOException exc) {}
/*      */     
/*  776 */     saveFindAQuote(Op.fq[Op.FQ.FqPuz.ordinal()]);
/*  777 */     return true;
/*      */   }
/*      */   
/*      */   static int unwantedWord(String word) {
/*  781 */     int count = 0, l = word.length();
/*      */     
/*  783 */     for (int y = 0; y < Grid.ySz; y++) {
/*  784 */       for (int x = 0; x < Grid.xSz; x++) {
/*  785 */         if (Grid.puz[x][y] == word.charAt(0))
/*  786 */         { if (x + l <= Grid.xSz) { int i; for (i = 0; i < l && Grid.puz[x + i][y] == word.charAt(i); i++); if (i == l) count++;  }
/*  787 */            if (x + l <= Grid.xSz && y + l <= Grid.ySz) { int i; for (i = 0; i < l && Grid.puz[x + i][y + i] == word.charAt(i); i++); if (i == l) count++;  }
/*  788 */            if (y + l <= Grid.ySz) { int i; for (i = 0; i < l && Grid.puz[x][y + i] == word.charAt(i); i++); if (i == l) count++;  }
/*  789 */            if (x - l >= -1 && y + l <= Grid.ySz) { int i; for (i = 0; i < l && Grid.puz[x - i][y + i] == word.charAt(i); i++); if (i == l) count++;  }
/*  790 */            if (x - l >= -1) { int i; for (i = 0; i < l && Grid.puz[x - i][y] == word.charAt(i); i++); if (i == l) count++;  }
/*  791 */            if (x - l >= -1 && y - l >= -1) { int i; for (i = 0; i < l && Grid.puz[x - i][y - i] == word.charAt(i); i++); if (i == l) count++;  }
/*  792 */            if (y - l >= -1) { int i; for (i = 0; i < l && Grid.puz[x][y - i] == word.charAt(i); i++); if (i == l) count++;  }
/*  793 */            if (x + l <= Grid.xSz && y - l >= -1) { int i; for (i = 0; i < l && Grid.puz[x + i][y - i] == word.charAt(i); i++); if (i == l) count++;  }  } 
/*      */       } 
/*  795 */     }  return count;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static void fillCell(String qStr, int i, int j) {
/*  801 */     Random rnd = new Random();
/*      */ 
/*      */     
/*  804 */     if (Grid.mode[i][j] != 0 || (i == Grid.xSz - 1 && j == Grid.ySz - 1))
/*      */       return;  while (true) {
/*  806 */       char ch = qStr.charAt(rnd.nextInt(qStr.length()));
/*  807 */       boolean done = true;
/*  808 */       if (i < Grid.xSz - 1 && Grid.mode[i + 1][j] > 0) { int x = Grid.mode[i + 1][j] % Grid.xSz, y = Grid.mode[i + 1][j] / Grid.xSz; if ((char)Grid.puz[x][y] == ch) done = false;  }
/*  809 */        if (i > 0 && Grid.mode[i - 1][j] > 0) { int x = Grid.mode[i - 1][j] % Grid.xSz, y = Grid.mode[i - 1][j] / Grid.xSz; if ((char)Grid.puz[x][y] == ch) done = false;  }
/*  810 */        if (j < Grid.ySz - 1 && Grid.mode[i][j + 1] > 0) { int x = Grid.mode[i][j + 1] % Grid.xSz, y = Grid.mode[i][j + 1] / Grid.xSz; if ((char)Grid.puz[x][y] == ch) done = false;  }
/*  811 */        if (j > 0 && Grid.mode[i][j - 1] > 0) { int x = Grid.mode[i][j - 1] % Grid.xSz, y = Grid.mode[i][j - 1] / Grid.xSz; if ((char)Grid.puz[x][y] == ch) done = false;  }
/*  812 */        if (done) {
/*  813 */         Grid.puz[i][j] = ch;
/*      */         return;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static Boolean tl(int mode) {
/*  822 */     makeVec(); for (int i = 0; i < Grid.xSz * Grid.ySz; i++) {
/*  823 */       int x = vec[i] % Grid.xSz, y = vec[i] / Grid.xSz;
/*  824 */       if (x > 0 && x <= Grid.xSz - 2 && y > 0 && y <= Grid.ySz - 2 && (char)Grid.puz[x][y] == '^' && (char)Grid.puz[x][y + 1] == '^' && (char)Grid.puz[x + 1][y] == '^') {
/*      */         
/*  826 */         int t = y - mode + 1, l = x - mode + 1;
/*  827 */         int b = t + mode + 1, r = l + mode + 1;
/*  828 */         if (t >= -1 && l >= -1 && b <= Grid.ySz && r <= Grid.xSz) {
/*  829 */           int m; int count; for (int scan = mode + 2; m < scan; m++) {
/*  830 */             for (int n = 0; n < scan; n++) {
/*  831 */               if ((n != 0 || m != 0) && (n != 0 || m != scan - 1) && (m != 0 || n != scan - 1) && (m <= mode - 2 || n <= mode - 2) && 
/*  832 */                 t + n >= 0 && l + m >= 0 && (char)Grid.puz[l + m][t + n] == '^')
/*  833 */                 count++; 
/*      */             } 
/*  835 */           }  if (count == 0) {
/*  836 */             l++; t++;
/*  837 */             for (m = 0; m < mode; m++) {
/*  838 */               Grid.puz[l + m][t + mode - 1] = 94; Grid.puz[l + m][t] = 94;
/*  839 */               Grid.puz[l + mode - 1][t + m] = 94; Grid.puz[l][t + m] = 94;
/*      */             } 
/*  841 */             Grid.puz[x + 1][y + 1] = 126; Grid.puz[x][y] = 126;
/*  842 */             return Boolean.valueOf(true);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*  847 */     return Boolean.valueOf(false);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static Boolean bl(int mode) {
/*  853 */     makeVec(); for (int i = 0; i < Grid.xSz * Grid.ySz; i++) {
/*  854 */       int x = vec[i] % Grid.xSz, y = vec[i] / Grid.xSz;
/*  855 */       if (y > 0 && (char)Grid.puz[x][y] == '^' && (char)Grid.puz[x][y - 1] == '^' && (char)Grid.puz[x + 1][y] == '^') {
/*  856 */         int t = y - 2, l = x - mode + 1;
/*  857 */         int b = t + mode + 1, r = l + mode + 1;
/*  858 */         if (t >= -1 && l >= -1 && b <= Grid.ySz && r <= Grid.xSz) {
/*  859 */           int m; int count; for (int scan = mode + 2; m < scan; m++) {
/*  860 */             for (int n = 0; n < scan; n++) {
/*  861 */               if ((n != 0 || m != 0) && (n != scan - 1 || m != scan - 1) && (m != 0 || n != scan - 1) && (m <= mode - 2 || n >= 3) && 
/*  862 */                 l + m >= 0 && t + n >= 0 && (char)Grid.puz[l + m][t + n] == '^')
/*  863 */                 count++; 
/*      */             } 
/*  865 */           }  if (count == 0) {
/*  866 */             l++; t++;
/*  867 */             for (m = 0; m < mode; m++) {
/*  868 */               Grid.puz[l + m][t + mode - 1] = 94; Grid.puz[l + m][t] = 94;
/*  869 */               Grid.puz[l + mode - 1][t + m] = 94; Grid.puz[l][t + m] = 94;
/*      */             } 
/*  871 */             Grid.puz[x + 1][y - 1] = 126; Grid.puz[x][y] = 126;
/*  872 */             return Boolean.valueOf(true);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*  877 */     return Boolean.valueOf(false);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static Boolean tr(int mode) {
/*  883 */     makeVec(); for (int i = 0; i < Grid.xSz * Grid.ySz; i++) {
/*  884 */       int x = vec[i] % Grid.xSz, y = vec[i] / Grid.xSz;
/*  885 */       if (x > 0 && (char)Grid.puz[x][y] == '^' && (char)Grid.puz[x - 1][y] == '^' && (char)Grid.puz[x][y + 1] == '^') {
/*  886 */         int t = y - mode + 1, l = x - 2;
/*  887 */         int b = t + mode + 1, r = l + mode + 1;
/*  888 */         if (t >= -1 && l >= -1 && b <= Grid.ySz && r <= Grid.xSz) {
/*  889 */           int m; int count; for (int scan = mode + 2; m < scan; m++) {
/*  890 */             for (int n = 0; n < scan; n++) {
/*  891 */               if ((n != 0 || m != 0) && (n != scan - 1 || m != scan - 1) && (n != 0 || m != scan - 1) && (n <= mode - 2 || m >= 3) && 
/*  892 */                 t + n >= 0 && l + m >= 0 && (char)Grid.puz[l + m][t + n] == '^')
/*  893 */                 count++; 
/*      */             } 
/*  895 */           }  if (count == 0) {
/*  896 */             l++; t++;
/*  897 */             for (m = 0; m < mode; m++) {
/*  898 */               Grid.puz[l + m][t + mode - 1] = 94; Grid.puz[l + m][t] = 94;
/*  899 */               Grid.puz[l + mode - 1][t + m] = 94; Grid.puz[l][t + m] = 94;
/*      */             } 
/*  901 */             Grid.puz[x - 1][y + 1] = 126; Grid.puz[x][y] = 126;
/*  902 */             return Boolean.valueOf(true);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*  907 */     return Boolean.valueOf(false);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static Boolean br(int mode) {
/*  913 */     makeVec(); for (int i = 0; i < Grid.xSz * Grid.ySz; i++) {
/*  914 */       int x = vec[i] % Grid.xSz, y = vec[i] / Grid.xSz;
/*  915 */       if (x > 1 && y > 1 && (char)Grid.puz[x][y] == '^' && (char)Grid.puz[x - 1][y] == '^' && (char)Grid.puz[x][y - 1] == '^') {
/*  916 */         int t = y - 2, l = x - 2;
/*  917 */         int b = t + mode + 1, r = l + mode + 1;
/*  918 */         if (t >= -1 && l >= -1 && b <= Grid.ySz && r <= Grid.xSz) {
/*  919 */           int m; int count; for (int scan = mode + 2; m < scan; m++) {
/*  920 */             for (int n = 0; n < scan; n++) {
/*  921 */               if ((n != 0 || m != scan - 1) && (n != scan - 1 || m != 0) && (n != scan - 1 || m != scan - 1) && (n >= 3 || m >= 3) && 
/*  922 */                 t + n >= 0 && l + m >= 0 && (char)Grid.puz[l + n][t + m] == '^')
/*  923 */                 count++; 
/*      */             } 
/*  925 */           }  if (count == 0) {
/*  926 */             l++; t++;
/*  927 */             for (m = 0; m < mode; m++) {
/*  928 */               Grid.puz[l + m][t + mode - 1] = 94; Grid.puz[l + m][t] = 94;
/*  929 */               Grid.puz[l + mode - 1][t + m] = 94; Grid.puz[l][t + m] = 94;
/*      */             } 
/*  931 */             Grid.puz[x - 1][y - 1] = 126; Grid.puz[x][y] = 126;
/*  932 */             return Boolean.valueOf(true);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*  937 */     return Boolean.valueOf(false);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static Boolean t() {
/*  943 */     makeVec(); for (int i = 0; i < Grid.xSz * Grid.ySz; i++) {
/*  944 */       int x = vec[i] % Grid.xSz, y = vec[i] / Grid.xSz;
/*  945 */       if (x > 0 && y > 0 && (char)Grid.puz[x][y] == '^' && (char)Grid.puz[x - 1][y] == '^' && (char)Grid.puz[x + 1][y] == '^') {
/*  946 */         int t = y - 2, l = x - 2; int count;
/*  947 */         for (int m = 0; m < 5; m++) {
/*  948 */           for (int n = 0; n < 2; n++) {
/*  949 */             if ((m != 0 || n != 0) && (m != 4 || n != 0) && 
/*  950 */               t + n >= 0 && l + m >= 0 && (char)Grid.puz[l + m][t + n] == '^')
/*  951 */               count++; 
/*      */           } 
/*  953 */         }  if (count == 0) {
/*  954 */           Grid.puz[x + 1][y - 1] = 94; Grid.puz[x][y - 1] = 94; Grid.puz[x - 1][y - 1] = 94;
/*  955 */           Grid.puz[x][y] = 126;
/*  956 */           return Boolean.valueOf(true);
/*      */         } 
/*      */       } 
/*      */     } 
/*  960 */     return Boolean.valueOf(false);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static Boolean l() {
/*  966 */     makeVec(); for (int i = 0; i < Grid.xSz * Grid.ySz; i++) {
/*  967 */       int x = vec[i] % Grid.xSz, y = vec[i] / Grid.xSz;
/*  968 */       if (x > 0 && y > 0 && (char)Grid.puz[x][y] == '^' && (char)Grid.puz[x][y - 1] == '^' && (char)Grid.puz[x][y + 1] == '^') {
/*  969 */         int t = y - 2, l = x - 2; int count;
/*  970 */         for (int m = 0; m < 2; m++) {
/*  971 */           for (int n = 0; n < 5; n++) {
/*  972 */             if ((m != 0 || n != 0) && (m != 0 || n != 4) && 
/*  973 */               t + n >= 0 && l + m >= 0 && (char)Grid.puz[l + m][t + n] == '^')
/*  974 */               count++; 
/*      */           } 
/*  976 */         }  if (count == 0) {
/*  977 */           Grid.puz[x - 1][y + 1] = 94; Grid.puz[x - 1][y] = 94; Grid.puz[x - 1][y - 1] = 94;
/*  978 */           Grid.puz[x][y] = 126;
/*  979 */           return Boolean.valueOf(true);
/*      */         } 
/*      */       } 
/*      */     } 
/*  983 */     return Boolean.valueOf(false);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static Boolean b() {
/*  989 */     makeVec(); for (int i = 0; i < Grid.xSz * Grid.ySz; i++) {
/*  990 */       int x = vec[i] % Grid.xSz, y = vec[i] / Grid.xSz;
/*  991 */       if (x > 0 && y < Grid.ySz - 2 && (char)Grid.puz[x][y] == '^' && (char)Grid.puz[x - 1][y] == '^' && (char)Grid.puz[x + 1][y] == '^') {
/*  992 */         int t = y + 1, l = x - 2; int count;
/*  993 */         for (int m = 0; m < 5; m++) {
/*  994 */           for (int n = 0; n < 2; n++) {
/*  995 */             if ((m != 0 || n != 1) && (m != 4 || n != 1) && 
/*  996 */               t + n < Grid.ySz && l + m >= 0 && (char)Grid.puz[l + m][t + n] == '^')
/*  997 */               count++; 
/*      */           } 
/*  999 */         }  if (count == 0) {
/* 1000 */           Grid.puz[x + 1][y + 1] = 94; Grid.puz[x][y + 1] = 94; Grid.puz[x - 1][y + 1] = 94;
/* 1001 */           Grid.puz[x][y] = 126;
/* 1002 */           return Boolean.valueOf(true);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1006 */     return Boolean.valueOf(false);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static Boolean r() {
/* 1012 */     makeVec(); for (int i = 0; i < Grid.xSz * Grid.ySz; i++) {
/* 1013 */       int x = vec[i] % Grid.xSz, y = vec[i] / Grid.xSz;
/* 1014 */       if (y > 0 && x < Grid.xSz - 1 && (char)Grid.puz[x][y] == '^' && (char)Grid.puz[x][y - 1] == '^' && (char)Grid.puz[x][y + 1] == '^') {
/* 1015 */         int t = y - 2, l = x + 1; int count;
/* 1016 */         for (int m = 0; m < 2; m++) {
/* 1017 */           for (int n = 0; n < 5; n++) {
/* 1018 */             if ((m != 1 || n != 0) && (m != 1 || n != 4) && 
/* 1019 */               t + n >= 0 && (char)Grid.puz[l + m][t + n] == '^')
/* 1020 */               count++; 
/*      */           } 
/* 1022 */         }  if (count == 0) {
/* 1023 */           Grid.puz[x + 1][y + 1] = 94; Grid.puz[x + 1][y] = 94; Grid.puz[x + 1][y - 1] = 94;
/* 1024 */           Grid.puz[x][y] = 126;
/* 1025 */           return Boolean.valueOf(true);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1029 */     return Boolean.valueOf(false);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static String extractQuote(String st) {
/*      */     String theQuote;
/*      */     int i;
/* 1037 */     for (theQuote = "", i = 1;; i++) {
/* 1038 */       char ch = Op.fq[Op.FQ.FqQ.ordinal()].charAt(i);
/* 1039 */       if (ch == '"')
/* 1040 */         break;  if (Character.isLetterOrDigit(ch)) theQuote = theQuote + ch; 
/*      */     } 
/* 1042 */     return theQuote;
/*      */   }
/*      */   
/*      */   static void makeVec() {
/* 1046 */     int len = Grid.xSz * Grid.ySz;
/* 1047 */     Random rnd = new Random();
/*      */     int i;
/* 1049 */     for (i = 0; i < len; i++)
/* 1050 */       vec[i] = i; 
/* 1051 */     for (i = 0; i < len; i++) {
/* 1052 */       int target = rnd.nextInt(len);
/* 1053 */       int mem = vec[i];
/* 1054 */       vec[i] = vec[target];
/* 1055 */       vec[target] = mem;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\mustapha\Desktop\Crossword-Express.jar!\crosswordexpress\FindAQuote.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */