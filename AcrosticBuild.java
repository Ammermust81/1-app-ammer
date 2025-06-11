/*      */ package crosswordexpress;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.io.DataInputStream;
/*      */ import javax.swing.JDialog;
/*      */ import javax.swing.JLabel;
/*      */ import javax.swing.JMenuItem;
/*      */ 
/*      */ public final class AcrosticBuild {
/*      */   static JFrame jfAcrostic;
/*      */   static JMenuBar menuBar;
/*      */   JMenu menu;
/*      */   JMenu submenu;
/*      */   JMenuItem menuItem;
/*      */   JMenuItem buildMenuItem;
/*      */   JDialog jdlgWizard;
/*      */   static JPanel ppParagraph;
/*      */   static JPanel ppWords;
/*      */   static JPanel jpSpareLetters;
/*      */   static int panelParagraphW;
/*      */   static int panelParagraphH;
/*      */   static int panelWordsW;
/*      */   static int panelWordsH;
/*   24 */   static char[] spareLetters = new char[512]; static int panelSpareW; static int panelSpareH; static int panelParagraphX; static int panelParagraphY; static int panelWordsX; static int panelWordsY; static int panelSpareX; static int panelSpareY; static JLabel jl1; static JLabel jl2; static int width; static int height; boolean wizardRunning = false; static JLabel jlSpare;
/*   25 */   static int[] acSol = new int[512];
/*   26 */   static int[] acColor = new int[512]; static int acrosticLen;
/*   27 */   static int keyCol = 0;
/*      */   
/*      */   Thread thread;
/*   30 */   int count = 0; int acrosticTarget;
/*   31 */   ArrayList<String> allWords = new ArrayList<>();
/*   32 */   char[] listChar = new char[256];
/*   33 */   int[] listIndex = new int[256];
/*   34 */   int[] rowTable = new int[100];
/*   35 */   long myCount = 0L;
/*      */ 
/*      */   
/*      */   static void def() {
/*   39 */     Op.updateOption(Op.AC.AcW.ordinal(), "600", Op.ac);
/*   40 */     Op.updateOption(Op.AC.AcH.ordinal(), "700", Op.ac);
/*   41 */     Op.updateOption(Op.AC.AcShort.ordinal(), "4", Op.ac);
/*   42 */     Op.updateOption(Op.AC.AcCell.ordinal(), "FFFFFF", Op.ac);
/*   43 */     Op.updateOption(Op.AC.AcFirstLetter.ordinal(), "0099FF", Op.ac);
/*   44 */     Op.updateOption(Op.AC.AcPattern.ordinal(), "000000", Op.ac);
/*   45 */     Op.updateOption(Op.AC.AcGrid.ordinal(), "000000", Op.ac);
/*   46 */     Op.updateOption(Op.AC.AcCode.ordinal(), "000000", Op.ac);
/*   47 */     Op.updateOption(Op.AC.AcLetter.ordinal(), "000000", Op.ac);
/*   48 */     Op.updateOption(Op.AC.AcClue.ordinal(), "666666", Op.ac);
/*   49 */     Op.updateOption(Op.AC.AcError.ordinal(), "FF0000", Op.ac);
/*   50 */     Op.updateOption(Op.AC.AcCurrent.ordinal(), "33FFCC", Op.ac);
/*   51 */     Op.updateOption(Op.AC.AcPuz.ordinal(), "sample.acrostic", Op.ac);
/*   52 */     Op.updateOption(Op.AC.AcDic.ordinal(), "english", Op.ac);
/*   53 */     Op.updateOption(Op.AC.AcParagraph.ordinal(), "What is the popular name for the large hairy creature resembling a human or a bear, said to live in the highest parts of the Himalayan mountains?", Op.ac);
/*   54 */     Op.updateOption(Op.AC.AcWords.ordinal(), "ABOMINABLESNOWMAN", Op.ac);
/*   55 */     Op.updateOption(Op.AC.AcFont.ordinal(), "SansSerif", Op.ac);
/*   56 */     Op.updateOption(Op.AC.AcClueFont.ordinal(), "SansSerif", Op.ac);
/*   57 */     Op.updateOption(Op.AC.AcCodeFont.ordinal(), "SansSerif", Op.ac);
/*   58 */     Op.updateOption(Op.AC.AcPuzColor.ordinal(), "true", Op.ac);
/*   59 */     Op.updateOption(Op.AC.AcSolColor.ordinal(), "true", Op.ac);
/*      */   }
/*      */   
/*   62 */   String acrosticHelp = "<span class='m'>Firstly, what is an Acrostic Puzzle?</span><div>There are two parts to an Acrostic puzzle:-</div><ul><li/>A list of <b>Words</b> with the identity of each word being indicated by a <b>Clue</b>, just as happens in a standard crossword puzzle. These words do not interlink with each other, but the set of first letters, taken in order, spell out an additional word (or phrase or sentence) which is referred to as the <b>Key Word.</b><p/><li/>A <b>Key Paragraph</b> which appears in a grid with the spaces between words being represented by means of black cells. This results in an appearance which is very similar to that of a traditional crossword puzzle. Typically, the <b>Key Paragraph</b> will be a clue to the identity of the <b>Key Word</b> mentioned above.</ul><div>Each letter in the list of <b>Words</b> also appears in the <b>Key Paragraph</b>, and its location in the <b>Key Paragraph</b> is indicated by means of an identification number. Similarly, each letter in the <b>Key Paragraph</b> appears in the list of <b>Words</b>, and its location is indicated by means of both the identification number, and an identifying letter to show the <b>Word</b> which contains it.</div><br/><span class='m'>Confused?</span><div>All will become clear when you make your first Acrostic.</div><hr class='teal'><span class='m'>Menu Functions</span><br/><br/><ul><li/><span class='s'>File Menu</span><ul><li/><span>Select a Dictionary</span><br/>When loading a new puzzle into the Build screen, you begin by selecting the dictionary which was used to build the ACROSTIC puzzle which you want to load.<p/><li/><span>Load a Puzzle</span><br/>Then you choose your puzzle from the pool of ACROSTIC puzzles currently available in the selected dictionary.<p/><li/><span>Save</span><br/>If you have done some manual editing of the puzzle, this option will save those changes under the existing file name.<p/><li/><span>SaveAs</span><br/>This option allows you to make an exact copy of the current puzzle using a different file name. The copy will be saved into the folder of the dictionary that was used to construct it. Alternatively, if you don't enter a new name for the puzzle, you can change the Puzzle Description, or any of the other descriptive items without changing the puzzle name.<p/><li/><span>Quit Construction</span><br/>Returns you to the Crossword Express opening screen.<p/></ul><li/><span class='s'>Build Menu</span><ul><li/><span>Select a Dictionary</span><br/>Use this option to select the dictionary which you want to use to build the new ACROSTIC puzzle.<p/><li/><span>Start a New Puzzle</span><br/>This option presents you with a dialog into which you can enter a file name for your new puzzle. You can also enter several other pieces of information such as a <b>Puzzle Title, Author</b> and <b>Copyright</b> information.<p/><li/><span>Build Options</span><br/>Use this option to access a Build Options dialog where you can set some rules to be followed by the puzzle building function as it builds your puzzle.<p/><li/><span>Start Building / Stop Building</span><br/>Construction of the puzzle will commence when you select the <b>Start Building</b> option. If puzzle building is successful you will receive a message containing the name of the puzzle file, and the location where it was saved. If it becomes necessary, you can interrupt the building process by selecting this option a second time. Note that during the construction phase, the text of this option is changed to <b>Stop Building</b><p/><li/><span>Suggest a Word</span><br/>Manual construction of Acrostic puzzles is also possible. If you select a word by means of the arrow keys or the mouse, and then select the <b>Suggest a Word</b> option, you will be presented with a list of suitable words. Select a word from this list and click <b>OK</b> and the word will be inserted into the puzzle for you. This is the only method by which you can enter your own words into a puzzle. If you know in advance which words you would like to place into the puzzle, you should use Dictionary Maintenance to make quite sure that those words are in fact contained in the dictionary.<p/><li/><span>Edit a Clue</span><br/>If you are using a dictionary which has clues, then the clues will be automatically added to the puzzle as you build it. If the dictionary does not have clues, or if you want to make changes to an existing clue, then selecting this option will present you with a dialog which has extensive facilities for both composing and editing the clue.<p/><li/><span>Extract Word</span><br/>Inevitably, as you manually build a puzzle, there will come a time when you will want to remove one or more of the words that you have entered. Position the red cursor over the word in question and select the <b>Extract Word</b> option, and the word will be removed.<p/></ul><li/><span class='s'>View Menu</span><ul><li/><span>Display Options</span><br/>This leads you to a dialog box in which you can change the colors of various elements within the puzzle, and control the fonts which will be used for the puzzle's text components. You can also decide if printing of the puzzle will be done in black and white or in color.<p/></ul><li/><span class='s'>Tasks Menu</span><ul><li/><span>Print this Puzzle</span><br/>This will take you to a custom print screen where you can control the details involved with printing your puzzle.<p/><li/><span>Solve this Puzzle</span><br/>This will take you to a Solve screen which provides a fully interactive environment for solving the puzzle.<p/><li/><span>Export Puzzle as Text</span><br/>Under normal circumstances, the Print function will provide all of the layout flexibility you will need when printing your puzzles. Inevitably of course special cases will arise where you need to intervene in the printing of either the words or the clues to achieve some special effect. To meet this need, a text export feature offers the following choices:-<ul><li/><b>Export Words.</b> Each line of text has the format <b>1. WORD</b><li/><b>Export Clues.</b> Each line of text has the format <b>1. Clue</b><li/><b>Export Words and Clues.</b> Each line of text has the format <b>1. WORD : Clue</b></ul>In addition, you have the choice of exporting the text to a text file located anywhere on your computer's hard drive, or to the System Clipboard from where you can Paste into any Word Processor or Desk Top Publishing application.<p/><li/><span>Delete this Puzzle</span><br/>Use this option to eliminate unwanted ACROSTIC puzzles from your file system.<p/></ul><li/><span class='s'>Help Menu</span><ul><li/><span>Acrostic Help</span><br/>Displays the Help screen which you are now reading.<p/></ul></ul></body>";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  160 */   String acrosticOptions = "<div>The following options are available for adjustment before you start to build your Acrostic puzzle:-</div><ul><li/><b>Key Paragraph:</b> Enter your paragraph into this Text Area. If you wish, you can enter numbers and punctuation, but remember that just like the spaces between the words, they play no part in the construction or solution of the puzzle.<p/><li/><b>Key Words:</b> An important point to remember here is that every letter contained in the <b>Key Words</b> must also appear in the <b>Key Paragraph</b>. This is dictated by the definition of the structure of the puzzle, and no latitude is permitted. An error message will be displayed if you break this rule.<p/><li/><b>Shortest Word:</b> This combo box allows you to specify the shortest word which will be used in the construction of the puzzle. The lengths of all other words will be determined by the program. If you specify a length which is longer than the average word length for this puzzle, or is too short, considering the lengths of the Key Paragraph and the Key Words which you entered, then you will receive an appropriate warning.<p/><li/><b>Key Words Column:</b> Under default conditions, the letters of Key Words provide the starting letters for each of the words within the Acrostic puzzle. In other words, the Key Words occupies the first column of letters of the words. This option allows you to control the column occupied by the Key Words. It may be set from one up to the value of the Shortest Word. If you choose a value greater than Shortest Word, the program will set it instead to equal Shortest Word, after issuing a warning to this effect.</ul><span class='m'>SOME WORDS OF ADVICE.</span><div>The process of building an Acrostic is a <b>VERY</b> complex business, and there are a number of things which can easily stop the process from proceeding to a successful conclusion:-</div><ul><li/>Construction will fail if the dictionary you are using does not have quite a large number of words. As a general rule you should aim to have 2000 or more words for each word length you intend to use.<p/><li/>In any given language certain letters are used far less frequently than others. For example, in the English language, the letters <b>J K Q X</b> and <b>Z</b> are used less often than <b>A E R S</b> and <b>T</b>. To give yourself the best chance of achieving a successful outcome you should try to minimize the number of unpopular letters in your <b>Key Paragraph</b> and maximize the number of popular letters.<p/><li/>Naturally, the unpopular letters will differ from one language to another. This being the case, it would be unwise to try to build a mixed language puzzle where the dictionary was for example English, and the <b>Key Paragraph</b> was Spanish.</ul></body";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   AcrosticBuild(JFrame jfCWE) {
/*  195 */     Def.puzzleMode = 10;
/*  196 */     Def.dispSolArray = Boolean.valueOf(false); Def.dispCursor = Boolean.valueOf(true); Def.dispNullCells = Boolean.valueOf(false);
/*  197 */     Def.building = 0;
/*  198 */     Grid.clearGrid();
/*  199 */     acrosticLen = Op.ac[Op.AC.AcParagraph.ordinal()].length();
/*      */     
/*  201 */     jfAcrostic = new JFrame("Acrostic Construction");
/*  202 */     jfAcrostic.setSize(Op.getInt(Op.AC.AcW.ordinal(), Op.ac), Op.getInt(Op.AC.AcH.ordinal(), Op.ac));
/*      */     
/*  204 */     int frameX = (jfCWE.getX() + jfAcrostic.getWidth() > Methods.scrW) ? (Methods.scrW - jfAcrostic.getWidth() - 10) : jfCWE.getX();
/*  205 */     jfAcrostic.setLocation(frameX, jfCWE.getY());
/*  206 */     jfAcrostic.setLayout((LayoutManager)null);
/*  207 */     jfAcrostic.getContentPane().setBackground(Def.COLOR_FRAMEBG);
/*  208 */     jfAcrostic.setDefaultCloseOperation(0);
/*  209 */     jfAcrostic
/*  210 */       .addComponentListener(new ComponentAdapter()
/*      */         {
/*      */           public void componentResized(ComponentEvent ce) {
/*  213 */             int w = (AcrosticBuild.jfAcrostic.getWidth() < 600) ? 600 : AcrosticBuild.jfAcrostic.getWidth();
/*  214 */             int h = (AcrosticBuild.jfAcrostic.getHeight() < 700) ? 700 : AcrosticBuild.jfAcrostic.getHeight();
/*  215 */             AcrosticBuild.jfAcrostic.setSize(w, h);
/*  216 */             Op.setInt(Op.AC.AcW.ordinal(), w, Op.ac);
/*  217 */             Op.setInt(Op.AC.AcH.ordinal(), h, Op.ac);
/*  218 */             AcrosticBuild.restoreFrame();
/*      */           }
/*      */         });
/*      */     
/*  222 */     jfAcrostic
/*  223 */       .addWindowListener(new WindowAdapter() {
/*      */           public void windowClosing(WindowEvent we) {
/*  225 */             if (Def.building == 1 || Def.selecting)
/*  226 */               return;  Op.saveOptions("acrostic.opt", Op.ac);
/*  227 */             CrosswordExpress.transfer(1, AcrosticBuild.jfAcrostic);
/*      */           }
/*      */         });
/*      */     
/*  231 */     Methods.closeHelp();
/*      */ 
/*      */     
/*  234 */     Runnable buildThread = () -> {
/*      */         Def.dispCursor = Boolean.valueOf(false);
/*      */         
/*      */         Methods.havePuzzle = false;
/*      */         
/*      */         Methods.havePuzzle = buildAcrostic();
/*      */         this.buildMenuItem.setText("Step 4: Start Building");
/*      */         if (Def.building == 2) {
/*      */           Def.building = 0;
/*      */           Methods.interrupted(jfAcrostic);
/*      */           makeGrid();
/*      */           restoreFrame();
/*      */           return;
/*      */         } 
/*      */         Def.building = 0;
/*      */         if (Methods.havePuzzle) {
/*      */           saveAcrostic(Op.ac[Op.AC.AcPuz.ordinal()]);
/*      */           Methods.puzzleSaved(jfAcrostic, Op.ac[Op.AC.AcDic.ordinal()] + ".dic", Op.ac[Op.AC.AcPuz.ordinal()]);
/*      */           restoreFrame();
/*      */         } 
/*      */       };
/*  255 */     jl1 = new JLabel(); jfAcrostic.add(jl1);
/*  256 */     jl2 = new JLabel(); jfAcrostic.add(jl2);
/*      */ 
/*      */     
/*  259 */     menuBar = new JMenuBar();
/*  260 */     menuBar.setBackground(Def.COLOR_MENUBAR);
/*  261 */     jfAcrostic.setJMenuBar(menuBar);
/*      */     
/*  263 */     this.menu = new JMenu("File");
/*  264 */     menuBar.add(this.menu);
/*  265 */     this.menuItem = new JMenuItem("Select a Dictionary");
/*  266 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(68, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  267 */     this.menu.add(this.menuItem);
/*  268 */     this.menuItem
/*  269 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           Methods.selectDictionary(jfAcrostic, Op.ac[Op.AC.AcDic.ordinal()], 1);
/*      */           Op.ac[Op.AC.AcDic.ordinal()] = Methods.dictionaryName;
/*      */           loadAcrostic(Op.ac[Op.AC.AcPuz.ordinal()]);
/*      */           acrosticLinkage();
/*      */           restoreFrame();
/*      */         });
/*  279 */     this.menuItem = new JMenuItem("Load a Puzzle");
/*  280 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(76, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  281 */     this.menu.add(this.menuItem);
/*  282 */     this.menuItem
/*  283 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1)
/*      */             return; 
/*      */           ppParagraph.invalidate();
/*      */           ppParagraph.repaint();
/*      */           new Select(jfAcrostic, Op.ac[Op.AC.AcDic.ordinal()] + ".dic", "acrostic", Op.ac, Op.AC.AcPuz.ordinal(), false);
/*      */         });
/*  290 */     this.menuItem = new JMenuItem("Save");
/*  291 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(83, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  292 */     this.menu.add(this.menuItem);
/*  293 */     this.menuItem
/*  294 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           saveAcrostic(Op.ac[Op.AC.AcPuz.ordinal()]);
/*      */           Methods.puzzleSaved(jfAcrostic, Op.ac[Op.AC.AcDic.ordinal()] + ".dic", Op.ac[Op.AC.AcPuz.ordinal()]);
/*      */           restoreFrame();
/*      */         });
/*  302 */     this.menuItem = new JMenuItem("SaveAs");
/*  303 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(65, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  304 */     this.menu.add(this.menuItem);
/*  305 */     this.menuItem
/*  306 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           Methods.puzzleDescriptionDialog(jfAcrostic, Op.ac[Op.AC.AcPuz.ordinal()].substring(0, Op.ac[Op.AC.AcPuz.ordinal()].indexOf(".acrostic")), Op.ac[Op.AC.AcDic.ordinal()] + ".dic", ".acrostic");
/*      */           if (Methods.clickedOK) {
/*      */             saveAcrostic(Op.ac[Op.AC.AcPuz.ordinal()] = Methods.theFileName);
/*      */             restoreFrame();
/*      */             Methods.puzzleSaved(jfAcrostic, Op.ac[Op.AC.AcDic.ordinal()] + ".dic", Op.ac[Op.AC.AcPuz.ordinal()]);
/*      */           } 
/*      */         });
/*  317 */     this.menuItem = new JMenuItem("Quit Construction");
/*  318 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(81, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  319 */     this.menu.add(this.menuItem);
/*  320 */     this.menuItem
/*  321 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           
/*      */           Op.saveOptions("acrostic.opt", Op.ac);
/*      */           CrosswordExpress.transfer(1, jfAcrostic);
/*      */         });
/*  329 */     this.menu = new JMenu("Build");
/*  330 */     menuBar.add(this.menu);
/*  331 */     this.menuItem = new JMenuItem("Use Construction Wizard");
/*  332 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(87, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  333 */     this.menu.add(this.menuItem);
/*  334 */     this.menuItem
/*  335 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1)
/*      */             return; 
/*      */           acrosticWizard();
/*      */           restoreFrame();
/*      */         });
/*  341 */     this.menu.addSeparator();
/*  342 */     this.menuItem = new JMenuItem("     -: OR :-");
/*  343 */     this.menu.add(this.menuItem);
/*  344 */     this.menu.addSeparator();
/*      */     
/*  346 */     this.menuItem = new JMenuItem("Step 1: Start a New Puzzle");
/*  347 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(78, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  348 */     this.menu.add(this.menuItem);
/*  349 */     this.menuItem
/*  350 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           Methods.puzzleDescriptionDialog(jfAcrostic, Op.ac[Op.AC.AcPuz.ordinal()].substring(0, Op.ac[Op.AC.AcPuz.ordinal()].indexOf(".acrostic")), Op.ac[Op.AC.AcDic.ordinal()] + ".dic", ".acrostic");
/*      */           if (Methods.clickedOK) {
/*      */             Op.ac[Op.AC.AcPuz.ordinal()] = Methods.theFileName;
/*      */             makeGrid();
/*      */           } 
/*      */           restoreFrame();
/*      */         });
/*  361 */     this.menuItem = new JMenuItem("Step 2: Select a Dictionary");
/*  362 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(68, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  363 */     this.menu.add(this.menuItem);
/*  364 */     this.menuItem
/*  365 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           Methods.selectDictionary(jfAcrostic, Op.ac[Op.AC.AcDic.ordinal()], 1);
/*      */           Op.ac[Op.AC.AcDic.ordinal()] = Methods.dictionaryName;
/*      */           loadAcrostic(Op.ac[Op.AC.AcPuz.ordinal()]);
/*      */           restoreFrame();
/*      */         });
/*  374 */     this.menuItem = new JMenuItem("Step 3: Build Options");
/*  375 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(79, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  376 */     this.menu.add(this.menuItem);
/*  377 */     this.menuItem
/*  378 */       .addActionListener(ae -> {
/*      */           acrosticOptions();
/*      */           
/*      */           if (Methods.clickedOK) {
/*      */             makeGrid();
/*      */           }
/*      */           restoreFrame();
/*      */         });
/*  386 */     this.buildMenuItem = new JMenuItem("Step 4: Start Building");
/*  387 */     this.buildMenuItem.setAccelerator(KeyStroke.getKeyStroke(66, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  388 */     this.menu.add(this.buildMenuItem);
/*  389 */     this.buildMenuItem
/*  390 */       .addActionListener(ae -> {
/*      */           if (Op.ac[Op.AC.AcPuz.ordinal()].length() == 0 || Methods.havePuzzle) {
/*      */             Methods.noName(jfAcrostic);
/*      */             
/*      */             return;
/*      */           } 
/*      */           if (Def.building == 0) {
/*      */             this.thread = new Thread(paramRunnable);
/*      */             this.thread.start();
/*      */             Def.building = 1;
/*      */             this.buildMenuItem.setText("Step 4: Stop Building");
/*      */           } else {
/*      */             Def.building = 2;
/*      */           } 
/*      */         });
/*  405 */     this.menu.addSeparator();
/*      */     
/*  407 */     this.menuItem = new JMenuItem("Suggest a Word");
/*  408 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(87, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  409 */     this.menu.add(this.menuItem);
/*  410 */     this.menuItem
/*  411 */       .addActionListener(ae -> {
/*      */           if ((NodeList.nodeList[Grid.yCurW]).word.length() == 1 || (NodeList.nodeList[Grid.yCurW]).word.charAt(0) == ' ') {
/*      */             Op.msc[Op.MSC.WordToolsDic.ordinal()] = Op.ac[Op.AC.AcDic.ordinal()];
/*      */             
/*      */             suggest();
/*      */           } else {
/*      */             JOptionPane.showMessageDialog(jfAcrostic, "You already have a word in this slot!");
/*      */           } 
/*      */           
/*      */           restoreFrame();
/*      */         });
/*  422 */     this.menuItem = new JMenuItem("Edit a Clue");
/*  423 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(67, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  424 */     this.menu.add(this.menuItem);
/*  425 */     this.menuItem
/*  426 */       .addActionListener(ae -> {
/*      */           if ((NodeList.nodeList[Grid.yCurW]).word.length() > 1) {
/*      */             Op.msc[Op.MSC.WordToolsDic.ordinal()] = Op.ac[Op.AC.AcDic.ordinal()];
/*      */             
/*      */             CrosswordBuild.addAClue(jfAcrostic);
/*      */             if (Methods.clickedOK) {
/*      */               saveAcrostic(Op.ac[Op.AC.AcPuz.ordinal()]);
/*      */             }
/*      */           } else {
/*      */             JOptionPane.showMessageDialog(jfAcrostic, "You can't add a clue until you have entered a word!");
/*      */           } 
/*      */           restoreFrame();
/*      */         });
/*  439 */     this.menuItem = new JMenuItem("Extract Word");
/*  440 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(69, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  441 */     this.menu.add(this.menuItem);
/*  442 */     this.menuItem
/*  443 */       .addActionListener(ae -> {
/*      */           if (Methods.havePuzzle) {
/*      */             JOptionPane.showMessageDialog(jfAcrostic, "Can't extract a word from a completed puzzle!");
/*      */           } else if ((NodeList.nodeList[Grid.yCurW]).word.length() > 1) {
/*      */             extractWord(Grid.yCurW);
/*      */           } else {
/*      */             JOptionPane.showMessageDialog(jfAcrostic, "You must select a word before you can extract it!");
/*      */           } 
/*      */           
/*      */           restoreFrame();
/*      */         });
/*      */     
/*  455 */     this.menu = new JMenu("View");
/*  456 */     menuBar.add(this.menu);
/*  457 */     this.menuItem = new JMenuItem("DisplaY Options");
/*  458 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(89, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  459 */     this.menu.add(this.menuItem);
/*  460 */     this.menuItem
/*  461 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           
/*      */           printOptions(jfAcrostic, "Display Options");
/*      */           restoreFrame();
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
/*      */           CrosswordExpress.toPrint(jfAcrostic, Op.ac[Op.AC.AcPuz.ordinal()]);
/*      */         });
/*  481 */     this.menuItem = new JMenuItem("Solve this Puzzle");
/*  482 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(86, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  483 */     this.menu.add(this.menuItem);
/*  484 */     this.menuItem
/*  485 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1)
/*      */             return; 
/*      */           if (Methods.havePuzzle) {
/*      */             CrosswordExpress.transfer(11, jfAcrostic);
/*      */           } else {
/*      */             Methods.noPuzzle(jfAcrostic, "Solve");
/*      */           } 
/*      */         });
/*  494 */     this.menuItem = new JMenuItem("Export as Text");
/*  495 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(84, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  496 */     this.menu.add(this.menuItem);
/*  497 */     this.menuItem
/*  498 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1)
/*      */             return; 
/*      */           if (Methods.havePuzzle) {
/*      */             NodeList.exportText(jfAcrostic, true);
/*      */           } else {
/*      */             Methods.noPuzzle(jfAcrostic, "Export");
/*      */           } 
/*      */         });
/*  507 */     this.menuItem = new JMenuItem("Delete this Puzzle");
/*  508 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(90, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  509 */     this.menu.add(this.menuItem);
/*  510 */     this.menuItem
/*  511 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           
/*      */           if (Methods.deleteAPuzzle(jfAcrostic, Op.ac[Op.AC.AcPuz.ordinal()], Op.db[Op.DB.DbDic.ordinal()] + ".dic", ppParagraph)) {
/*      */             loadAcrostic(Op.ac[Op.AC.AcPuz.ordinal()]);
/*      */             restoreFrame();
/*      */           } 
/*      */         });
/*  521 */     this.menu = new JMenu("Help");
/*  522 */     menuBar.add(this.menu);
/*  523 */     this.menuItem = new JMenuItem("Acrostic Help");
/*  524 */     this.menu.add(this.menuItem);
/*  525 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(72, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  526 */     this.menuItem
/*  527 */       .addActionListener(ae -> Methods.cweHelp(jfAcrostic, null, "Building Acrostic Puzzles", this.acrosticHelp));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  532 */     ppParagraph = new AcrosticParagraphPP(10, 335);
/*  533 */     jfAcrostic.add(ppParagraph);
/*  534 */     ppWords = new AcrosticWordsPP(390, 150);
/*  535 */     jfAcrostic.add(ppWords);
/*      */     
/*  537 */     ppWords
/*  538 */       .addMouseListener(new MouseAdapter() {
/*      */           public void mousePressed(MouseEvent e) {
/*  540 */             AcrosticBuild.this.updateGridWords(e);
/*      */           }
/*      */         });
/*      */ 
/*      */     
/*  545 */     jfAcrostic
/*  546 */       .addKeyListener(new KeyAdapter() {
/*      */           public void keyPressed(KeyEvent e) {
/*  548 */             AcrosticBuild.this.handleKeyPressed(e);
/*      */           }
/*      */         });
/*      */ 
/*      */     
/*  553 */     jlSpare = new JLabel("Unused Letters");
/*  554 */     jlSpare.setSize(150, 16);
/*  555 */     jlSpare.setLocation(10, 40);
/*  556 */     jlSpare.setHorizontalAlignment(2);
/*  557 */     jfAcrostic.add(jlSpare);
/*  558 */     jlSpare.setFont(new Font(null, 1, 16));
/*  559 */     jpSpareLetters = new SpareLetterPanel(10, 60);
/*  560 */     jfAcrostic.add(jpSpareLetters);
/*  561 */     loadAcrostic(Op.ac[Op.AC.AcPuz.ordinal()]);
/*  562 */     acrosticLinkage();
/*  563 */     restoreFrame();
/*      */   }
/*      */   
/*      */   static void restoreFrame() {
/*  567 */     jfAcrostic.setVisible(true);
/*  568 */     Insets insets = jfAcrostic.getInsets();
/*  569 */     width = jfAcrostic.getWidth() - insets.left + insets.right;
/*  570 */     height = jfAcrostic.getHeight() - insets.top + insets.bottom + 37 + menuBar.getHeight();
/*      */     
/*  572 */     panelSpareW = width / 2 - 10;
/*  573 */     panelSpareH = (height - 20) / 4;
/*  574 */     panelSpareX = 10;
/*  575 */     panelSpareY = 60;
/*      */     
/*  577 */     panelWordsW = width / 2 - 8;
/*  578 */     panelWordsH = height - 20;
/*  579 */     panelWordsX = width / 2 - 2;
/*  580 */     panelWordsY = 47;
/*      */     
/*  582 */     panelParagraphW = width / 2 - 10;
/*  583 */     panelParagraphH = panelWordsH - 11 - panelSpareH;
/*  584 */     panelParagraphX = 10;
/*  585 */     panelParagraphY = panelSpareY + panelSpareH - 2;
/*      */     
/*  587 */     jpSpareLetters.setSize(panelSpareW, panelSpareH);
/*  588 */     jpSpareLetters.setLocation(panelSpareX, panelSpareY);
/*  589 */     ppParagraph.setLocation(panelParagraphX, panelParagraphY);
/*  590 */     ppParagraph.setSize(panelParagraphW, panelParagraphH);
/*  591 */     ppWords.setLocation(panelWordsX, panelWordsY);
/*  592 */     ppWords.setSize(panelWordsW, panelWordsH);
/*      */     
/*  594 */     setSizesAndOffsetsW(0, 0, panelWordsW, panelWordsH, 20);
/*      */     
/*  596 */     Def.dispSolArray = Boolean.valueOf(false); Def.dispCursor = Boolean.valueOf(true);
/*  597 */     jfAcrostic.requestFocusInWindow();
/*  598 */     jpSpareLetters.repaint();
/*  599 */     ppWords.repaint();
/*  600 */     ppParagraph.repaint();
/*  601 */     Methods.infoPanel(jl1, jl2, "Build Acrostic", "Dictionary : " + Op.ac[Op.AC.AcDic.ordinal()] + "  -|-  Puzzle : " + Op.ac[Op.AC.AcPuz
/*  602 */           .ordinal()], jfAcrostic.getWidth());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static void adjustParagraph(int x, int y, int width, int height, int inset) {
/*  608 */     Grid.xCell = width / 3;
/*  609 */     while ((Grid.xSz = (width - 20) / Grid.xCell) * (Grid.ySz = (height - 20) / Grid.xCell) < acrosticLen)
/*      */       Grid.xCell--; 
/*  611 */     int i = (width - 20) / Grid.xSz;
/*  612 */     int j = (height - 20) / Grid.ySz;
/*  613 */     Grid.xCell = Grid.yCell = (i < j) ? i : j;
/*  614 */     Grid.xOrg = x + (width - Grid.xCell * Grid.xSz) / 2;
/*  615 */     Grid.yOrg = y + (height - Grid.yCell * Grid.ySz) / 2;
/*      */   }
/*      */ 
/*      */   
/*      */   static void setSizesAndOffsetsW(int x, int y, int width, int height, int inset) {
/*  620 */     if (NodeList.nodeListLength > 0) {
/*  621 */       for (int k = 0; k < Op.ac[Op.AC.AcWords.ordinal()].length(); k++) {
/*  622 */         if ((NodeList.nodeList[k]).length > Grid.xSzW)
/*  623 */           Grid.xSzW = (NodeList.nodeList[k]).length; 
/*      */       } 
/*      */     } else {
/*  626 */       Grid.xSzW = 12;
/*  627 */     }  int i = (width - inset) / (Grid.xSzW + 1);
/*  628 */     int j = (height - inset) / Grid.ySzW;
/*  629 */     Grid.xCellW = Grid.yCellW = (i < j) ? i : j;
/*  630 */     Grid.xOrgW = x + (width - Grid.xCellW * (Grid.xSzW - 1)) / 2;
/*  631 */     Grid.yOrgW = y + (height - Grid.yCellW * Grid.ySzW) / 2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void acrosticOptions() {
/*  638 */     final JDialog jdlgAcrostic = new JDialog(jfAcrostic, "Acrostic Options", true);
/*  639 */     jdlgAcrostic.setSize(352, 408);
/*  640 */     jdlgAcrostic.setResizable(false);
/*  641 */     jdlgAcrostic.setLayout((LayoutManager)null);
/*  642 */     jdlgAcrostic.setLocation(jfAcrostic.getX(), jfAcrostic.getY());
/*      */     
/*  644 */     jdlgAcrostic
/*  645 */       .addWindowListener(new WindowAdapter() {
/*      */           public void windowClosing(WindowEvent we) {
/*  647 */             Methods.closeHelp();
/*      */           }
/*      */         });
/*      */     
/*  651 */     Methods.closeHelp();
/*      */     
/*  653 */     JLabel jlParagraph = new JLabel("Key Paragraph");
/*  654 */     jlParagraph.setForeground(Def.COLOR_LABEL);
/*  655 */     jlParagraph.setSize(120, 20);
/*  656 */     jlParagraph.setLocation(10, 5);
/*  657 */     jlParagraph.setHorizontalAlignment(2);
/*  658 */     jdlgAcrostic.add(jlParagraph);
/*      */     
/*  660 */     final JTextArea jtaParagraph = new JTextArea(Op.ac[Op.AC.AcParagraph.ordinal()]);
/*  661 */     jtaParagraph.setLineWrap(true);
/*  662 */     jtaParagraph.setWrapStyleWord(true);
/*  663 */     JScrollPane jsp = new JScrollPane(jtaParagraph);
/*  664 */     jsp.setSize(330, 150);
/*  665 */     jsp.setLocation(10, 30);
/*  666 */     jtaParagraph.selectAll();
/*  667 */     jdlgAcrostic.add(jsp);
/*  668 */     jtaParagraph.setFont(new Font("SansSerif", 1, 13));
/*      */     
/*  670 */     JLabel jlWords = new JLabel("Key Words");
/*  671 */     jlWords.setForeground(Def.COLOR_LABEL);
/*  672 */     jlWords.setSize(120, 20);
/*  673 */     jlWords.setLocation(10, 180);
/*  674 */     jlWords.setHorizontalAlignment(2);
/*  675 */     jdlgAcrostic.add(jlWords);
/*      */     
/*  677 */     final JTextArea jtaWords = new JTextArea(Op.ac[Op.AC.AcWords.ordinal()]);
/*  678 */     jtaWords.setLineWrap(true);
/*  679 */     jtaWords.setWrapStyleWord(true);
/*  680 */     JScrollPane jsp2 = new JScrollPane(jtaWords);
/*  681 */     jsp2.setSize(330, 50);
/*  682 */     jsp2.setLocation(10, 205);
/*  683 */     jtaWords.selectAll();
/*  684 */     jdlgAcrostic.add(jsp2);
/*  685 */     jtaWords.setFont(new Font("SansSerif", 1, 13));
/*      */     
/*  687 */     JLabel jlShort = new JLabel("Shortest Word:");
/*  688 */     jlShort.setForeground(Def.COLOR_LABEL);
/*  689 */     jlShort.setSize(120, 20);
/*  690 */     jlShort.setLocation(30, 265);
/*  691 */     jlShort.setHorizontalAlignment(4);
/*  692 */     jdlgAcrostic.add(jlShort);
/*      */     
/*  694 */     final JComboBox<Integer> jcbbShort = new JComboBox<>();
/*  695 */     for (int i = 3; i <= 10; i++)
/*  696 */       jcbbShort.addItem(Integer.valueOf(i)); 
/*  697 */     jcbbShort.setSize(80, 23);
/*  698 */     jcbbShort.setLocation(160, 265);
/*  699 */     jdlgAcrostic.add(jcbbShort);
/*  700 */     jcbbShort.setBackground(Def.COLOR_BUTTONBG);
/*  701 */     jcbbShort.setSelectedIndex(Op.getInt(Op.AC.AcShort.ordinal(), Op.ac) - 3);
/*      */     
/*  703 */     JLabel jlCol = new JLabel("Key Words Column:");
/*  704 */     jlCol.setForeground(Def.COLOR_LABEL);
/*  705 */     jlCol.setSize(140, 20);
/*  706 */     jlCol.setLocation(10, 298);
/*  707 */     jlCol.setHorizontalAlignment(4);
/*  708 */     jdlgAcrostic.add(jlCol);
/*      */     
/*  710 */     final JComboBox<Integer> jcbbCol = new JComboBox<>();
/*  711 */     for (int j = 1; j <= 8; j++)
/*  712 */       jcbbCol.addItem(Integer.valueOf(j)); 
/*  713 */     jcbbCol.setSize(80, 23);
/*  714 */     jcbbCol.setLocation(160, 298);
/*  715 */     jdlgAcrostic.add(jcbbCol);
/*  716 */     jcbbCol.setBackground(Def.COLOR_BUTTONBG);
/*  717 */     jcbbCol.setSelectedIndex(keyCol);
/*      */     
/*  719 */     Action doOK = new AbstractAction("OK")
/*      */       {
/*      */         public void actionPerformed(ActionEvent e) {
/*  722 */           Methods.clickedOK = true;
/*  723 */           String sP = jtaParagraph.getText();
/*  724 */           String sW = jtaWords.getText();
/*  725 */           char[] p = sP.toUpperCase().toCharArray();
/*  726 */           char[] w = sW.toUpperCase().toCharArray();
/*  727 */           boolean fail = false;
/*  728 */           for (int aW = 0; aW < sW.length(); aW++) {
/*  729 */             int aP; for (aP = 0; aP < sP.length(); aP++) {
/*  730 */               if (w[aW] == p[aP]) {
/*  731 */                 p[aP] = ' '; break;
/*      */               } 
/*      */             } 
/*  734 */             if (aP == Op.ac[Op.AC.AcParagraph.ordinal()].length())
/*  735 */               fail = true; 
/*      */           } 
/*  737 */           if (fail) {
/*  738 */             JOptionPane.showMessageDialog(jdlgAcrostic, "<html><center>There are letters in the Key Word which are<br>not included in the Key Paragraph!");
/*      */           } else {
/*  740 */             Op.setInt(Op.AC.AcShort.ordinal(), jcbbShort.getSelectedIndex() + 3, Op.ac);
/*  741 */             AcrosticBuild.keyCol = jcbbCol.getSelectedIndex();
/*  742 */             Op.ac[Op.AC.AcParagraph.ordinal()] = jtaParagraph.getText();
/*  743 */             Op.ac[Op.AC.AcWords.ordinal()] = jtaWords.getText().toUpperCase(); int i, count1;
/*  744 */             for (count1 = i = 0; i < sP.length(); i++) {
/*  745 */               if (Character.isLetter(sP.charAt(i)))
/*  746 */                 count1++; 
/*  747 */             }  i = count1 / jtaWords.getText().length();
/*  748 */             if (Op.getInt(Op.AC.AcShort.ordinal(), Op.ac) > i) {
/*  749 */               JOptionPane.showMessageDialog(jdlgAcrostic, "<html><center>You have specified a Shortest word which is longer than the average word length!<br>The average word length will be used instead.");
/*      */               
/*  751 */               Op.setInt(Op.AC.AcShort.ordinal(), i, Op.ac);
/*      */             } 
/*  753 */             if (AcrosticBuild.keyCol > Op.getInt(Op.AC.AcShort.ordinal(), Op.ac) - 1) {
/*  754 */               JOptionPane.showMessageDialog(jdlgAcrostic, "<html><center>You have specified a Key Words Column which is larger than the Shortest word length!<br>The Shortest word length will be used instead.");
/*      */               
/*  756 */               AcrosticBuild.keyCol = Op.getInt(Op.AC.AcShort.ordinal(), Op.ac) - 1;
/*      */             } 
/*  758 */             if (Op.getInt(Op.AC.AcShort.ordinal(), Op.ac) < i - 3) {
/*  759 */               JOptionPane.showMessageDialog(jdlgAcrostic, "<html><center>You have specified a Shortest word which is too short<br>for the Key Paragraph and Key Words you have entered.<br>A more suitable Shortest word length will be used instead.");
/*      */               
/*  761 */               Op.setInt(Op.AC.AcShort.ordinal(), i - 3, Op.ac);
/*      */             } 
/*      */             
/*  764 */             AcrosticBuild.acrosticLen = Op.ac[Op.AC.AcParagraph.ordinal()].length();
/*  765 */             jdlgAcrostic.dispose();
/*  766 */             Methods.closeHelp();
/*      */           } 
/*      */         }
/*      */       };
/*  770 */     JButton jbOK = Methods.newButton("doOK", doOK, 79, 35, 331, 110, 26);
/*  771 */     jdlgAcrostic.add(jbOK);
/*      */     
/*  773 */     Action doCancel = new AbstractAction("Cancel") {
/*      */         public void actionPerformed(ActionEvent e) {
/*  775 */           Methods.clickedOK = false;
/*  776 */           jdlgAcrostic.dispose();
/*  777 */           Methods.closeHelp();
/*      */         }
/*      */       };
/*  780 */     JButton jbCancel = Methods.newButton("doCancel", doCancel, 67, 35, 366, 110, 26);
/*  781 */     jdlgAcrostic.add(jbCancel);
/*      */     
/*  783 */     Action doHelp = new AbstractAction("<html><font size=6 color=BB0000 face=Serif>Help ", new ImageIcon("graphics/help.png")) {
/*      */         public void actionPerformed(ActionEvent e) {
/*  785 */           Methods.cweHelp(null, jdlgAcrostic, "Acrostic Options", AcrosticBuild.this.acrosticOptions);
/*      */         }
/*      */       };
/*  788 */     JButton jbHelp = Methods.newButton("doHelp", doHelp, 72, 155, 331, 160, 61);
/*  789 */     jdlgAcrostic.add(jbHelp);
/*      */     
/*  791 */     Methods.setDialogSize(jdlgAcrostic, 350, 402);
/*      */   }
/*      */   
/*      */   static void printOptions(JFrame jf, String type) {
/*  795 */     String[] colorLabel = { "Cell Color", "First Letter", "Pattern Color", "Grid Color", "Code Color", "Letter Color", "Clue Color", "Error Color", "Current Word" };
/*  796 */     int[] colorInt = { Op.AC.AcCell.ordinal(), Op.AC.AcFirstLetter.ordinal(), Op.AC.AcPattern.ordinal(), Op.AC.AcGrid.ordinal(), Op.AC.AcCode.ordinal(), Op.AC.AcLetter.ordinal(), Op.AC.AcClue.ordinal(), Op.AC.AcError.ordinal(), Op.AC.AcCurrent.ordinal() };
/*  797 */     String[] fontLabel = { "Puzzle Font", "Clue Font", "Code Font" };
/*  798 */     int[] fontInt = { Op.AC.AcFont.ordinal(), Op.AC.AcClueFont.ordinal(), Op.AC.AcCodeFont.ordinal() };
/*  799 */     String[] checkLabel = { "PPrint Puzzle with color.", "SPrint Solution with color." };
/*  800 */     int[] checkInt = { Op.AC.AcPuzColor.ordinal(), Op.AC.AcSolColor.ordinal() };
/*  801 */     Methods.stdPrintOptions(jf, "Acrostic " + type, Op.ac, colorLabel, colorInt, fontLabel, fontInt, checkLabel, checkInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void saveAcrostic(String acrosticName) {
/*      */     int i;
/*  811 */     for (i = 0; i < (acrosticLen = Op.ac[Op.AC.AcParagraph.ordinal()].length()); i++) {
/*  812 */       acColor[i] = 16777215;
/*  813 */       char ch = Op.ac[Op.AC.AcParagraph.ordinal()].charAt(i);
/*  814 */       acSol[i] = (Character.isLetter(ch) || Character.isWhitespace(ch)) ? 0 : ch;
/*      */     } 
/*      */ 
/*      */     
/*      */     try {
/*  819 */       DataOutputStream dataOut = new DataOutputStream(new FileOutputStream(Op.ac[Op.AC.AcDic.ordinal()] + ".dic/" + acrosticName));
/*  820 */       dataOut.writeInt(Grid.xSz);
/*  821 */       dataOut.writeInt(Grid.ySz);
/*  822 */       dataOut.writeByte(Methods.noReveal);
/*  823 */       dataOut.writeByte(Methods.noErrors);
/*  824 */       for (i = 0; i < 54; i++) {
/*  825 */         dataOut.writeByte(0);
/*      */       }
/*  827 */       dataOut.writeUTF(Op.ac[Op.AC.AcParagraph.ordinal()]);
/*  828 */       dataOut.writeUTF(Op.ac[Op.AC.AcWords.ordinal()]);
/*      */       
/*  830 */       for (i = 0; i < acrosticLen; i++) {
/*  831 */         dataOut.writeInt(acSol[i]);
/*  832 */         dataOut.writeInt(acColor[i]);
/*      */       } 
/*      */       
/*  835 */       dataOut.writeUTF(Methods.puzzleTitle);
/*  836 */       dataOut.writeUTF(Methods.author);
/*  837 */       dataOut.writeUTF(Methods.copyright);
/*  838 */       dataOut.writeUTF(Methods.puzzleNumber);
/*  839 */       dataOut.writeUTF(Methods.puzzleNotes);
/*      */       
/*  841 */       for (i = 0; i < Op.ac[Op.AC.AcWords.ordinal()].length(); i++) {
/*  842 */         dataOut.writeUTF((NodeList.nodeList[i]).word);
/*  843 */         dataOut.writeUTF(((NodeList.nodeList[i]).clue == null) ? "No Clue" : (NodeList.nodeList[i]).clue);
/*      */       } 
/*  845 */       dataOut.close();
/*      */     }
/*  847 */     catch (IOException exc) {}
/*      */   }
/*      */ 
/*      */   
/*      */   static void loadAcrostic(String acrosticName) {
/*  852 */     boolean complete = true;
/*      */     
/*  854 */     Op.ac[Op.AC.AcDic.ordinal()] = Methods.confirmDictionary(Op.ac[Op.AC.AcDic.ordinal()] + ".dic", false);
/*      */ 
/*      */ 
/*      */     
/*  858 */     try { File fl = new File(Op.ac[Op.AC.AcDic.ordinal()] + ".dic/" + acrosticName);
/*  859 */       if (!fl.exists()) {
/*  860 */         fl = new File(Op.ac[Op.AC.AcDic.ordinal()] + ".dic/");
/*  861 */         String[] s = fl.list(); int j;
/*  862 */         for (j = 0; j < s.length && (
/*  863 */           s[j].lastIndexOf(".acrostic") == -1 || s[j].charAt(0) == '.'); j++);
/*      */         
/*  865 */         if (j == s.length) { makeGrid(); return; }
/*  866 */          acrosticName = s[j];
/*  867 */         Op.ac[Op.AC.AcPuz.ordinal()] = acrosticName;
/*      */       } 
/*      */       
/*  870 */       DataInputStream dataIn = new DataInputStream(new FileInputStream(Op.ac[Op.AC.AcDic.ordinal()] + ".dic/" + acrosticName));
/*  871 */       Grid.xSz = dataIn.readInt();
/*  872 */       Grid.ySz = dataIn.readInt();
/*  873 */       Methods.noReveal = dataIn.readByte();
/*  874 */       Methods.noErrors = dataIn.readByte(); int i;
/*  875 */       for (i = 0; i < 54; i++) {
/*  876 */         dataIn.readByte();
/*      */       }
/*  878 */       Op.ac[Op.AC.AcParagraph.ordinal()] = dataIn.readUTF();
/*  879 */       Op.ac[Op.AC.AcWords.ordinal()] = dataIn.readUTF();
/*      */       
/*  881 */       for (i = 0; i < (acrosticLen = Op.ac[Op.AC.AcParagraph.ordinal()].length()); i++) {
/*  882 */         acSol[i] = dataIn.readInt();
/*  883 */         acColor[i] = dataIn.readInt();
/*      */       } 
/*  885 */       Methods.puzzleTitle = dataIn.readUTF();
/*  886 */       Methods.author = dataIn.readUTF();
/*  887 */       Methods.copyright = dataIn.readUTF();
/*  888 */       Methods.puzzleNumber = dataIn.readUTF();
/*  889 */       Methods.puzzleNotes = dataIn.readUTF();
/*      */       
/*  891 */       prepareNodeList();
/*  892 */       for (i = 0; i < Op.ac[Op.AC.AcWords.ordinal()].length(); i++) {
/*  893 */         (NodeList.nodeList[i]).word = dataIn.readUTF();
/*  894 */         if ((NodeList.nodeList[i]).word.length() < 2) complete = false; 
/*  895 */         if (((NodeList.nodeList[i]).length = (NodeList.nodeList[i]).word.length()) > 1)
/*  896 */           (NodeList.nodeList[i]).preset = true; 
/*  897 */         (NodeList.nodeList[i]).clue = dataIn.readUTF();
/*      */       } 
/*      */ 
/*      */       
/*  901 */       for (keyCol = 0;; keyCol++) {
/*  902 */         for (i = 0; i < Op.ac[Op.AC.AcWords.ordinal()].length() && 
/*  903 */           (NodeList.nodeList[i]).word.charAt(keyCol) == Op.ac[Op.AC.AcWords.ordinal()].charAt(i); i++);
/*      */         
/*  905 */         if (i == Op.ac[Op.AC.AcWords.ordinal()].length()) {
/*      */           break;
/*      */         }
/*      */       } 
/*  909 */       for (i = 0; i < Op.ac[Op.AC.AcWords.ordinal()].length(); i++) {
/*  910 */         if (((NodeList.nodeList[i]).length = (NodeList.nodeList[i]).word.length()) > 1)
/*  911 */           extractFromSpareLetters((NodeList.nodeList[i]).word, keyCol); 
/*  912 */       }  dataIn.close(); }
/*      */     
/*  914 */     catch (IOException exc) { return; }
/*  915 */      acrosticLinkage();
/*  916 */     Methods.havePuzzle = complete;
/*      */   }
/*      */   
/*      */   static void drawAcrosticParagraph(Graphics2D g2) {
/*  920 */     int i = 0, j = 0;
/*      */ 
/*      */ 
/*      */     
/*  924 */     Stroke normalStroke = new BasicStroke(Grid.xCell / 20.0F, 2, 0);
/*  925 */     RenderingHints rh = g2.getRenderingHints();
/*  926 */     rh.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
/*  927 */     g2.setRenderingHints(rh);
/*  928 */     g2.setStroke(normalStroke);
/*      */     
/*      */     int index;
/*  931 */     for (index = 0; index < acrosticLen; index++) {
/*  932 */       int k; if (Character.isWhitespace(Op.ac[Op.AC.AcParagraph.ordinal()].charAt(index))) {
/*  933 */         k = Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.AC.AcPattern.ordinal(), Op.ac) : 0;
/*      */       } else {
/*  935 */         k = Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.AC.AcCell.ordinal(), Op.ac) : 16777215;
/*  936 */       }  i = index % Grid.xSz; j = index / Grid.xSz;
/*  937 */       g2.setColor(new Color(k));
/*  938 */       g2.fillRect(Grid.xOrg + i * Grid.xCell, Grid.yOrg + j * Grid.yCell, Grid.xCell, Grid.yCell);
/*      */     } 
/*      */ 
/*      */     
/*  942 */     int theColor = Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.AC.AcPattern.ordinal(), Op.ac) : 0;
/*  943 */     g2.setColor(new Color(theColor)); int x;
/*  944 */     for (x = i + 1; x < Grid.xSz; x++) {
/*  945 */       g2.fillRect(Grid.xOrg + x * Grid.xCell, Grid.yOrg + j * Grid.yCell, Grid.xCell, Grid.yCell);
/*  946 */       g2.drawRect(Grid.xOrg + x * Grid.xCell, Grid.yOrg + j * Grid.yCell, Grid.xCell, Grid.yCell);
/*      */     } 
/*      */ 
/*      */     
/*  950 */     g2.setColor(new Color(Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.AC.AcGrid.ordinal(), Op.ac) : 0));
/*  951 */     for (index = 0; index < acrosticLen; index++) {
/*  952 */       i = index % Grid.xSz; j = index / Grid.xSz;
/*  953 */       g2.drawRect(Grid.xOrg + i * Grid.xCell, Grid.yOrg + j * Grid.yCell, Grid.xCell, Grid.yCell);
/*      */     } 
/*      */ 
/*      */     
/*  957 */     g2.setColor(new Color(Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.AC.AcLetter.ordinal(), Op.ac) : 0));
/*  958 */     g2.setFont(new Font(Op.ac[Op.AC.AcFont.ordinal()], 0, 8 * Grid.yCell / 10));
/*  959 */     FontMetrics fm = g2.getFontMetrics();
/*  960 */     for (index = 0; index < acrosticLen; index++) {
/*  961 */       char ch = (char)(Def.dispSolArray.booleanValue() ? acSol[index] : Op.ac[Op.AC.AcParagraph.ordinal()].charAt(index));
/*  962 */       if (ch != '\000') {
/*  963 */         int w = fm.stringWidth("" + ch);
/*  964 */         i = index % Grid.xSz; j = index / Grid.xSz;
/*  965 */         g2.drawString("" + ch, Grid.xOrg + i * Grid.xCell + (Grid.xCell - w) / 2, Grid.yOrg + j * Grid.yCell + 8 * Grid.yCell / 10);
/*      */       } 
/*      */     } 
/*      */     
/*  969 */     if (Methods.havePuzzle) {
/*      */       
/*  971 */       g2.setColor(new Color(Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.AC.AcCode.ordinal(), Op.ac) : 0));
/*  972 */       g2.setFont(new Font(Op.ac[Op.AC.AcCodeFont.ordinal()], 0, 3 * Grid.yCell / 10));
/*  973 */       fm = g2.getFontMetrics(); int id;
/*  974 */       for (id = index = 0; index < acrosticLen; index++) {
/*  975 */         if (Character.isLetter(Op.ac[Op.AC.AcParagraph.ordinal()].charAt(index))) {
/*  976 */           i = index % Grid.xSz; j = index / Grid.xSz;
/*  977 */           g2.drawString("" + ++id, Grid.xOrg + i * Grid.xCell + Grid.xCell / 10, Grid.yOrg + j * Grid.yCell + 3 * Grid.yCell / 10);
/*      */         } 
/*      */       } 
/*      */       
/*  981 */       for (id = 1, index = 0; index < acrosticLen; index++) {
/*  982 */         if (Character.isLetter(Op.ac[Op.AC.AcParagraph.ordinal()].charAt(index))) {
/*  983 */           for (i = 0; i < NodeList.nodeListLength; i++) {
/*  984 */             for (j = 0; j < (NodeList.nodeList[i]).length; j++) {
/*  985 */               if (((NodeList.nodeList[i]).cellLoc[j]).x == id) {
/*  986 */                 char ch = (char)(((j < 26) ? 65 : 97) + i);
/*  987 */                 int w = fm.stringWidth("" + ch);
/*  988 */                 x = index % Grid.xSz; int y = index / Grid.xSz;
/*  989 */                 g2.drawString("" + ch, Grid.xOrg + (x + 1) * Grid.xCell - w - Grid.xCell / 8, Grid.yOrg + y * Grid.yCell + 3 * Grid.yCell / 10);
/*      */               } 
/*      */             } 
/*  992 */           }  id++;
/*      */         } 
/*      */       } 
/*      */     } 
/*  996 */     g2.setStroke(new BasicStroke(1.0F));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void drawAcrosticWords(Graphics2D g2) {
/* 1004 */     Stroke normalStroke = new BasicStroke(Grid.xCellW / 20.0F, 2, 0);
/* 1005 */     Stroke wideStroke = new BasicStroke((Grid.xCellW < 20) ? 2.0F : (2 * Grid.xCellW / 20), 2, 0);
/* 1006 */     RenderingHints rh = g2.getRenderingHints();
/* 1007 */     rh.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
/* 1008 */     g2.setRenderingHints(rh);
/* 1009 */     g2.setStroke(normalStroke);
/*      */     
/*      */     int j;
/* 1012 */     for (j = 0; j < Grid.ySzW; j++) {
/* 1013 */       for (int i = 0; i < (NodeList.nodeList[j]).length; i++) {
/* 1014 */         int theColor = (i == keyCol) ? Op.getColorInt(Op.AC.AcFirstLetter.ordinal(), Op.ac) : (Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.AC.AcCell.ordinal(), Op.ac) : 16777215);
/* 1015 */         g2.setColor(new Color(theColor));
/* 1016 */         g2.fillRect(Grid.xOrgW + i * Grid.xCellW, Grid.yOrgW + j * Grid.yCellW, Grid.xCellW, Grid.yCellW);
/*      */       } 
/*      */     } 
/*      */     
/* 1020 */     g2.setColor(new Color(Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.AC.AcGrid.ordinal(), Op.ac) : 0));
/* 1021 */     for (j = 0; j < Grid.ySzW; j++) {
/* 1022 */       for (int i = 0; i < (NodeList.nodeList[j]).length; i++) {
/* 1023 */         g2.drawRect(Grid.xOrgW + i * Grid.xCellW, Grid.yOrgW + j * Grid.yCellW, Grid.xCellW, Grid.yCellW);
/*      */       }
/*      */     } 
/* 1026 */     g2.setColor(new Color(Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.AC.AcLetter.ordinal(), Op.ac) : 0));
/* 1027 */     if (!Def.dispSolArray.booleanValue()) {
/* 1028 */       g2.setFont(new Font(Op.ac[Op.AC.AcFont.ordinal()], 0, 8 * Grid.yCellW / 10));
/* 1029 */       FontMetrics fontMetrics = g2.getFontMetrics();
/* 1030 */       for (j = 0; j < Grid.ySzW; j++) {
/* 1031 */         String oneWord = (NodeList.nodeList[j]).word;
/* 1032 */         for (int i = 0; i < oneWord.length(); i++) {
/* 1033 */           char ch = oneWord.charAt(i);
/* 1034 */           if (ch != '\000') {
/* 1035 */             int w = fontMetrics.stringWidth("" + ch);
/* 1036 */             g2.drawString("" + ch, Grid.xOrgW + i * Grid.xCellW + (Grid.xCellW - w) / 2, Grid.yOrgW + j * Grid.yCellW + 9 * Grid.yCellW / 10);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1043 */     g2.setColor(new Color(Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.AC.AcCode.ordinal(), Op.ac) : 0));
/* 1044 */     if (Methods.havePuzzle) {
/* 1045 */       g2.setFont(new Font(Op.ac[Op.AC.AcCodeFont.ordinal()], 0, 3 * Grid.yCellW / 10));
/* 1046 */       for (int i = 0; i < NodeList.nodeListLength; i++) {
/* 1047 */         for (j = 0; j < (NodeList.nodeList[i]).length; j++) {
/* 1048 */           int id = ((NodeList.nodeList[i]).cellLoc[j]).x;
/* 1049 */           g2.drawString("" + id, Grid.xOrgW + j * Grid.xCellW + Grid.xCellW / 10, Grid.yOrgW + i * Grid.yCellW + 3 * Grid.yCellW / 10);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1054 */     g2.setFont(new Font(Op.ac[Op.AC.AcCodeFont.ordinal()], 0, 6 * Grid.yCellW / 10));
/* 1055 */     FontMetrics fm = g2.getFontMetrics();
/* 1056 */     for (j = 0; j < Grid.ySzW; j++) {
/* 1057 */       char ch = (char)(((j < 26) ? 65 : 97) + j);
/* 1058 */       int w = fm.stringWidth("" + ch);
/* 1059 */       g2.drawString("" + ch, Grid.xOrgW - Grid.xCellW + (Grid.xCellW - w) / 2, Grid.yOrgW + j * Grid.yCellW + 9 * Grid.yCellW / 10);
/*      */     } 
/*      */     
/* 1062 */     if (Def.dispCursor.booleanValue()) {
/* 1063 */       g2.setColor(Def.COLOR_RED);
/* 1064 */       g2.setStroke(wideStroke);
/* 1065 */       g2.drawRect(Grid.xOrgW + Grid.xCurW * Grid.xCellW, Grid.yOrgW + Grid.yCurW * Grid.yCellW, Grid.xCellW, Grid.yCellW);
/*      */     } 
/* 1067 */     g2.setStroke(new BasicStroke(1.0F));
/*      */   }
/*      */   
/*      */   static void printPuz(Graphics2D g2, int left, int top, int width, int height) {
/* 1071 */     loadAcrostic(Op.ac[Op.AC.AcPuz.ordinal()]);
/* 1072 */     adjustParagraph(left, top, width, height, 0);
/* 1073 */     Def.dispSolArray = Boolean.valueOf(true);
/* 1074 */     Def.dispWithColor = Op.getBool(Op.AC.AcPuzColor.ordinal(), Op.ac);
/* 1075 */     drawAcrosticParagraph(g2);
/* 1076 */     Def.dispWithColor = Boolean.valueOf(true);
/* 1077 */     Def.dispSolArray = Boolean.valueOf(false);
/*      */   }
/*      */   
/*      */   static void printSol(Graphics2D g2, int left, int top, int width, int height, String solutionPuzzle) {
/* 1081 */     loadAcrostic(solutionPuzzle);
/* 1082 */     Def.dispWithColor = Op.getBool(Op.AC.AcSolColor.ordinal(), Op.ac);
/* 1083 */     drawSolution(left, top, width, height, g2);
/* 1084 */     Def.dispWithColor = Boolean.valueOf(true);
/* 1085 */     loadAcrostic(Op.ac[Op.AC.AcPuz.ordinal()]);
/*      */   }
/*      */   
/*      */   static void printSolTitle(Graphics2D g2, int left, int top, int width, int height, String solutionPuzzle) {
/* 1089 */     loadAcrostic(solutionPuzzle);
/* 1090 */     Print.outputTextItem(g2, left, top, width, height, "SansSerif", 0, Methods.puzzleTitle);
/* 1091 */     loadAcrostic(Op.ac[Op.AC.AcPuz.ordinal()]);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static void drawSolution(int left, int top, int width, int height, Graphics2D g2) {
/* 1097 */     float fontSize = 80.0F;
/*      */     
/*      */     String s;
/*      */     int i;
/* 1101 */     for (s = "<b> " + Op.msc[Op.MSC.Langwords.ordinal()] + " :- </b> ", i = 0; i < NodeList.nodeListLength; i++) {
/* 1102 */       String w = (NodeList.nodeList[i]).word;
/* 1103 */       s = s + w.substring(0, 1) + w.substring(1).toLowerCase() + " ";
/*      */     } 
/*      */     
/* 1106 */     g2.setColor(new Color(Def.dispWithColor.booleanValue() ? Op.getColorInt(Op.AC.AcLetter.ordinal(), Op.ac) : 0));
/* 1107 */     boolean draw = false; while (true) {
/* 1108 */       float y = top + fontSize;
/* 1109 */       FontMetrics fm = g2.getFontMetrics();
/*      */       
/* 1111 */       g2.setFont(new Font(Op.ac[Op.AC.AcFont.ordinal()], 0, (int)fontSize));
/* 1112 */       float res = Methods.renderOneString(g2, left, y, width, top + height, fontSize, fm, "<b> " + Op.msc[Op.MSC.Langtext.ordinal()] + " :- </b> " + Op.ac[Op.AC.AcParagraph.ordinal()], 0, 3, draw);
/* 1113 */       if (res <= 0.0F) {
/* 1114 */         fontSize = (float)(fontSize - 0.1D);
/*      */         continue;
/*      */       } 
/* 1117 */       y = res + fontSize;
/* 1118 */       res = Methods.renderOneString(g2, left, y, width, top + height, fontSize, fm, s, 0, 3, draw);
/* 1119 */       if (draw)
/* 1120 */         break;  if (res <= 0.0F) {
/* 1121 */         fontSize = (float)(fontSize - 0.1D); continue;
/*      */       } 
/* 1123 */       draw = true;
/*      */     } 
/*      */   }
/*      */   
/*      */   String wizardText(String title, String text) {
/* 1128 */     return "<html><table width='310' bgcolor='CCCCCC' border='0' cellspacing='0' cellpadding='10' align='center'><tr><td bgcolor='004400' colspan='4'><center><font color='FFFFFF' size='6'><b>Construction Wizard</b></font></center></td></tr></table><table width='310'><tr><td bgcolor='004400'><table width='304' align='center' cellpadding='5'><tr><td bgcolor='FFFFEE'><table><tr><td><img src='file:graphics/wizard2.png'></img></td><td align='center'><font color='005500' size='6'>" + title + "</font></td></tr></table>" + "<div align='justify'><font size='4' color='555500' face='Arial'>" + text + "</div></td></tr></table></td></tr></table>";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void acrosticWizard() {
/* 1137 */     this.jdlgWizard = new JDialog(jfAcrostic);
/* 1138 */     this.jdlgWizard.setUndecorated(true);
/* 1139 */     this.jdlgWizard.setSize(330, 430);
/* 1140 */     this.jdlgWizard.setResizable(false);
/* 1141 */     this.jdlgWizard.setLayout((LayoutManager)null);
/* 1142 */     this.jdlgWizard.setLocation(jfAcrostic.getX() + jfAcrostic.getWidth() + 10, jfAcrostic.getY());
/* 1143 */     this.jdlgWizard.setVisible(true);
/* 1144 */     this.wizardRunning = true;
/*      */ 
/*      */     
/* 1147 */     Runnable buildThread = () -> {
/*      */         Def.dispCursor = Boolean.valueOf(false);
/*      */         
/*      */         Methods.havePuzzle = false;
/*      */         
/*      */         Methods.havePuzzle = buildAcrostic();
/*      */         if (Def.building == 2) {
/*      */           Def.building = 0;
/*      */           Methods.interrupted(jfAcrostic);
/*      */           makeGrid();
/*      */           restoreFrame();
/*      */           return;
/*      */         } 
/*      */         Def.building = 0;
/*      */         if (Methods.havePuzzle) {
/*      */           saveAcrostic(Op.ac[Op.AC.AcPuz.ordinal()]);
/*      */           Methods.puzzleSaved(jfAcrostic, Op.ac[Op.AC.AcDic.ordinal()] + ".dic", Op.ac[Op.AC.AcPuz.ordinal()]);
/*      */           restoreFrame();
/*      */         } 
/*      */       };
/* 1167 */     String seldic = "Use the drop down list to select the dictionary you would like to use for this puzzle.<p><p>Click OK when you are satisfied with your choice.<p><p>If you click Cancel, this Wizard will terminate.";
/*      */ 
/*      */ 
/*      */     
/* 1171 */     String puzopt = "Adjust any of the Acrostic Options you would like to change for this puzzle.<p><p>The Help button will explain all you need to know about the Options.<p><p>Click OK to continue, or click Cancel to abandon the process, and terminate the Wizard.";
/*      */ 
/*      */ 
/*      */     
/* 1175 */     String puzname = "Enter the name of the file into which the puzzle will be saved after construction<p><p>There are several other input fields and options which you can also use. Use the Help button to see complete instructions.<p><p>Click OK to continue, or click Cancel to abandon the process, and terminate the Wizard.";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1181 */     JLabel wizardText = new JLabel(Methods.wizardText("Select a Dictionary", seldic));
/* 1182 */     wizardText.setSize(320, 420);
/* 1183 */     wizardText.setLocation(5, 5);
/* 1184 */     wizardText.setHorizontalAlignment(0);
/* 1185 */     this.jdlgWizard.add(wizardText);
/*      */     
/* 1187 */     Methods.selectDictionary(jfAcrostic, Op.ac[Op.AC.AcDic.ordinal()], 1);
/* 1188 */     if (Methods.clickedOK) {
/* 1189 */       Op.ac[Op.AC.AcDic.ordinal()] = Methods.dictionaryName;
/* 1190 */       restoreFrame();
/*      */       
/* 1192 */       wizardText.setText(Methods.wizardText("Acrostic Options", puzopt));
/* 1193 */       acrosticOptions();
/* 1194 */       if (Methods.clickedOK) {
/* 1195 */         wizardText.setText(Methods.wizardText("Name the Puzzle", puzname));
/* 1196 */         Methods.puzzleDescriptionDialog(jfAcrostic, Op.ac[Op.AC.AcPuz.ordinal()].substring(0, Op.ac[Op.AC.AcPuz.ordinal()].indexOf(".acrostic")), Op.ac[Op.AC.AcDic.ordinal()] + ".dic", ".acrostic");
/* 1197 */         if (Methods.clickedOK) {
/* 1198 */           Op.ac[Op.AC.AcPuz.ordinal()] = Methods.theFileName;
/* 1199 */           makeGrid();
/* 1200 */           restoreFrame();
/*      */           
/* 1202 */           this.thread = new Thread(buildThread);
/* 1203 */           this.thread.start();
/* 1204 */           Def.building = 1;
/*      */         } 
/*      */       } 
/*      */     } 
/* 1208 */     this.jdlgWizard.dispose();
/* 1209 */     this.wizardRunning = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void prepareNodeList() {
/* 1216 */     if (Op.ac[Op.AC.AcWords.ordinal()].length() > 0) {
/* 1217 */       Grid.xSzW = 12; Grid.ySzW = Op.ac[Op.AC.AcWords.ordinal()].length(); int j;
/* 1218 */       for (j = 0; j < Op.ac[Op.AC.AcWords.ordinal()].length(); j++) {
/* 1219 */         Grid.color[0][j] = Op.getColorInt(Op.AC.AcCurrent.ordinal(), Op.ac);
/* 1220 */         NodeList.nodeList[j] = new Node();
/* 1221 */         (NodeList.nodeList[j]).cellLoc = new Point[50];
/* 1222 */         (NodeList.nodeList[j]).direction = 0;
/* 1223 */         (NodeList.nodeList[j]).id = j + 1;
/* 1224 */         (NodeList.nodeList[j]).x = 0;
/* 1225 */         (NodeList.nodeList[j]).y = j;
/* 1226 */         (NodeList.nodeList[j]).length = 12;
/* 1227 */         (NodeList.nodeList[j]).word = "       ".substring(0, keyCol) + Op.ac[Op.AC.AcWords.ordinal()].charAt(j);
/* 1228 */         (NodeList.nodeList[j]).preset = false;
/*      */       } 
/* 1230 */       NodeList.nodeListLength = j;
/*      */     } 
/* 1232 */     Grid.xCurW = Grid.yCurW = 0;
/*      */     int i, x;
/* 1234 */     for (i = x = 0; i < Op.ac[Op.AC.AcParagraph.ordinal()].length(); i++) {
/* 1235 */       char ch; if (Character.isLetter(ch = Op.ac[Op.AC.AcParagraph.ordinal()].charAt(i)))
/* 1236 */         spareLetters[x++] = Character.toUpperCase(ch); 
/*      */     } 
/* 1238 */     for (i = 0; i < x - 1; i++) {
/* 1239 */       for (int j = i + 1; j < x; j++) {
/* 1240 */         if (spareLetters[i] > spareLetters[j]) {
/* 1241 */           char ch = spareLetters[i];
/* 1242 */           spareLetters[i] = spareLetters[j];
/* 1243 */           spareLetters[j] = ch;
/*      */         } 
/*      */       } 
/* 1246 */     }  extractFromSpareLetters(Op.ac[Op.AC.AcWords.ordinal()], -1);
/* 1247 */     setSizesAndOffsetsW(0, 0, panelWordsW, panelWordsH, 20);
/*      */   }
/*      */   
/*      */   static void makeGrid() {
/* 1251 */     Methods.havePuzzle = false;
/*      */     
/* 1253 */     Dimension dim = jfAcrostic.getSize();
/* 1254 */     Op.setInt(Op.AC.AcW.ordinal(), dim.width, Op.ac);
/* 1255 */     Op.setInt(Op.AC.AcH.ordinal(), dim.height, Op.ac);
/*      */     
/* 1257 */     panelWordsW = dim.width - 405;
/* 1258 */     panelWordsH = dim.height - 190;
/* 1259 */     prepareNodeList();
/*      */     
/* 1261 */     panelParagraphW = 382;
/* 1262 */     panelParagraphH = dim.height - 375;
/* 1263 */     adjustParagraph(0, 0, panelParagraphW, panelParagraphH, 0);
/*      */   }
/*      */   
/* 1266 */   static char[] letter = new char[500];
/* 1267 */   static long[] wordID = new long[500];
/* 1268 */   static int[] letterPos = new int[500];
/*      */   
/* 1270 */   static long[] occupied = new long[100];
/* 1271 */   static int[][] solArray = new int[100][15];
/*      */   
/*      */   static int lap;
/*      */   
/*      */   static boolean acrosticLinkage() {
/* 1276 */     int[] pStep = { 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109 };
/* 1277 */     String para = Op.ac[Op.AC.AcParagraph.ordinal()];
/*      */     
/* 1279 */     boolean exception = false;
/* 1280 */     String[] word = new String[100];
/* 1281 */     int[] stWord = new int[100];
/* 1282 */     long[] idWord0 = new long[100];
/*      */     
/* 1284 */     int numWord = 0, j = numWord, i = j; word[0] = ""; stWord[0] = 0; idWord0[0] = 1L;
/* 1285 */     for (; i < para.length(); i++) {
/* 1286 */       char ch = para.charAt(i);
/* 1287 */       if (Character.isLetter(ch)) { word[numWord] = word[numWord] + ch; j++; }
/* 1288 */        if (ch == ' ' || i == para.length() - 1) {
/* 1289 */         word[++numWord] = "";
/* 1290 */         stWord[numWord] = j;
/* 1291 */         idWord0[numWord] = idWord0[numWord - 1] * 2L;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1311 */     for (i = 0; i < 500; ) { letter[i] = ' '; wordID[i] = 0L; i++; }
/*      */ 
/*      */     
/* 1314 */     for (i = 0; i < NodeList.nodeListLength; i++) {
/* 1315 */       for (j = 0; j < (NodeList.nodeList[i]).length; j++) {
/* 1316 */         (NodeList.nodeList[i]).cellLoc[j] = new Point(0, 0);
/*      */       }
/*      */     } 
/*      */     
/* 1320 */     for (i = 0; i < numWord - 1; i++) {
/* 1321 */       for (j = i + 1; j < numWord; j++) {
/* 1322 */         if (word[i].length() < word[j].length()) {
/* 1323 */           String swapS = word[i]; word[i] = word[j]; word[j] = swapS;
/* 1324 */           int swapI = stWord[i]; stWord[i] = stWord[j]; stWord[j] = swapI;
/* 1325 */           long swapL = idWord0[i]; idWord0[i] = idWord0[j]; idWord0[j] = swapL;
/*      */         } 
/*      */       } 
/*      */     } 
/* 1329 */     for (int x = i = 0; i < numWord; i++) {
/* 1330 */       for (j = 0; j < word[i].length(); j++) {
/* 1331 */         letter[x] = Character.toUpperCase(word[i].charAt(j));
/* 1332 */         wordID[x] = idWord0[i];
/* 1333 */         letterPos[x++] = stWord[i] + j + 1;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1354 */     if (word[0].length() > Op.ac[Op.AC.AcWords.ordinal()].length())
/* 1355 */       exception = true; 
/* 1356 */     for (int stAns = 0; stAns < NodeList.nodeListLength; stAns++) {
/* 1357 */       for (int step = 0; step < 7; step++) {
/* 1358 */         lap = 0;
/* 1359 */         for (i = 0; i < 60; i++) {
/* 1360 */           occupied[i] = 0L;
/* 1361 */           for (j = 0; j < 15; ) { solArray[i][j] = 0; j++; }
/*      */         
/* 1363 */         }  int res = linkLetter(0, stAns, pStep[step], exception);
/* 1364 */         if (res == 1) {
/* 1365 */           for (i = 0; i < NodeList.nodeListLength; i++) {
/* 1366 */             for (j = 0; j < (NodeList.nodeList[i]).word.length(); j++)
/* 1367 */               ((NodeList.nodeList[i]).cellLoc[j]).x = solArray[i][j]; 
/* 1368 */           }  return true;
/*      */         } 
/*      */       } 
/* 1371 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int linkLetter(int theLetter, int answer, int step, boolean exception) {
/* 1380 */     if (letter[theLetter] == ' ') return 1; 
/* 1381 */     if (++lap == 2000) return -1;  int a;
/* 1382 */     for (a = 0; a < NodeList.nodeListLength * step; a += step) {
/* 1383 */       int thisAnswer = (answer + a) % NodeList.nodeListLength;
/* 1384 */       if ((occupied[thisAnswer] & wordID[theLetter]) <= 0L || exception) {
/* 1385 */         for (int l = 0; l < (NodeList.nodeList[thisAnswer]).word.length(); l++) {
/* 1386 */           if ((NodeList.nodeList[thisAnswer]).word.charAt(l) == letter[theLetter] && 
/* 1387 */             solArray[thisAnswer][l] == 0) {
/* 1388 */             occupied[thisAnswer] = occupied[thisAnswer] | wordID[theLetter];
/* 1389 */             solArray[thisAnswer][l] = letterPos[theLetter];
/* 1390 */             int res = linkLetter(theLetter + 1, thisAnswer + 1, step, exception);
/* 1391 */             if (res == 1 || res == -1) return res; 
/* 1392 */             occupied[thisAnswer] = occupied[thisAnswer] ^ wordID[theLetter];
/* 1393 */             solArray[thisAnswer][l] = 0;
/*      */           } 
/*      */         } 
/*      */       }
/*      */     } 
/* 1398 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void extractFromSpareLetters(String w, int col) {
/*      */     int i;
/* 1406 */     for (i = 0; i < w.length(); i++) {
/* 1407 */       if (i != col) {
/* 1408 */         char ch = w.charAt(i);
/* 1409 */         for (int j = 0;; j++) {
/* 1410 */           if (ch == spareLetters[j]) {
/* 1411 */             spareLetters[j] = ' ';
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/* 1417 */     for (int k = 0; spareLetters[i] != '\000'; i++) {
/* 1418 */       if (spareLetters[i] == ' ') {
/* 1419 */         spareLetters[i] = Character.MIN_VALUE;
/*      */       } else {
/* 1421 */         spareLetters[k] = spareLetters[i];
/* 1422 */         if (i != k++)
/* 1423 */           spareLetters[i] = Character.MIN_VALUE; 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   void insertWord(String w, int row) {
/* 1428 */     (NodeList.nodeList[row]).word = w;
/* 1429 */     (NodeList.nodeList[row]).length = w.length();
/* 1430 */     extractFromSpareLetters(w, keyCol);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void insertIntoSpareLetters(String w, int col) {
/*      */     int x;
/* 1438 */     for (x = 0; spareLetters[x] != '\000'; x++); int y; int i;
/* 1439 */     for (i = 0, y = x; i < w.length(); i++) {
/* 1440 */       if (i != keyCol)
/* 1441 */         spareLetters[y++] = w.charAt(i); 
/* 1442 */     }  spareLetters[y] = Character.MIN_VALUE;
/*      */ 
/*      */     
/* 1445 */     for (i = 0; i < y - 1; i++) {
/* 1446 */       for (int j = i + 1; j < y; j++) {
/* 1447 */         if (spareLetters[i] > spareLetters[j]) {
/* 1448 */           char ch = spareLetters[i];
/* 1449 */           spareLetters[i] = spareLetters[j];
/* 1450 */           spareLetters[j] = ch;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   } void extractWord(int row) {
/* 1455 */     insertIntoSpareLetters((NodeList.nodeList[row]).word, keyCol);
/* 1456 */     String str = "       ";
/* 1457 */     (NodeList.nodeList[row])
/* 1458 */       .word = str.substring(0, keyCol) + Op.ac[Op.AC.AcWords.ordinal()].charAt(row);
/* 1459 */     (NodeList.nodeList[row]).preset = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void suggest() {
/* 1469 */     final JDialog jdlgSuggest = new JDialog(jfAcrostic, "Suggest a Word", true);
/* 1470 */     jdlgSuggest.setSize(280, 430);
/* 1471 */     jdlgSuggest.setResizable(false);
/* 1472 */     jdlgSuggest.setLayout((LayoutManager)null);
/* 1473 */     jdlgSuggest.setLocation(jfAcrostic.getX(), jfAcrostic.getY());
/*      */     
/* 1475 */     jdlgSuggest
/* 1476 */       .addWindowListener(new WindowAdapter() {
/*      */           public void windowClosing(WindowEvent we) {
/* 1478 */             Methods.closeHelp();
/*      */           }
/*      */         });
/*      */     
/* 1482 */     Methods.closeHelp();
/*      */     
/* 1484 */     JLabel jlw = new JLabel("The Suggested words are:");
/* 1485 */     jlw.setForeground(Def.COLOR_LABEL);
/* 1486 */     jlw.setSize(200, 20);
/* 1487 */     jlw.setLocation(10, 10);
/* 1488 */     jlw.setHorizontalAlignment(2);
/* 1489 */     jdlgSuggest.add(jlw);
/*      */     
/* 1491 */     DefaultListModel<String> lmMatch = new DefaultListModel<>();
/* 1492 */     final JList<String> jl = new JList<>(lmMatch);
/* 1493 */     JScrollPane jsp = new JScrollPane(jl);
/* 1494 */     jsp.setSize(255, 272);
/* 1495 */     jsp.setLocation(10, 31);
/* 1496 */     jdlgSuggest.add(jsp);
/*      */     
/* 1498 */     JLabel jlCount = new JLabel("");
/* 1499 */     jlCount.setForeground(Def.COLOR_LABEL);
/* 1500 */     jlCount.setSize(200, 20);
/* 1501 */     jlCount.setLocation(10, 305);
/* 1502 */     jlCount.setHorizontalAlignment(2);
/* 1503 */     jdlgSuggest.add(jlCount);
/*      */ 
/*      */     
/*      */     try {
/* 1507 */       DataInputStream dataIn = new DataInputStream(new FileInputStream(Op.ac[Op.AC.AcDic.ordinal()] + ".dic/xword.dic")); int i;
/* 1508 */       for (i = 0; i < 128; i++)
/* 1509 */         dataIn.readByte(); 
/* 1510 */       while (dataIn.available() > 2) {
/* 1511 */         dataIn.readInt();
/* 1512 */         String s1 = dataIn.readUTF();
/* 1513 */         dataIn.readUTF();
/*      */         
/* 1515 */         int len = s1.length();
/* 1516 */         if (len < 4 || len > 15 || len < keyCol || 
/* 1517 */           s1.charAt(keyCol) != Op.ac[Op.AC.AcWords.ordinal()].charAt(Grid.yCurW))
/* 1518 */           continue;  char[] buf = s1.toCharArray();
/* 1519 */         for (i = 1; i < len - 1; i++) {
/* 1520 */           for (int k = i + 1; k < len; k++) {
/* 1521 */             if (buf[i] > buf[k])
/* 1522 */             { char ch = buf[i]; buf[i] = buf[k]; buf[k] = ch; } 
/*      */           } 
/* 1524 */         }  int j; for (i = 1, j = 0; i < len && 
/* 1525 */           spareLetters[j] != '\000'; j++) {
/* 1526 */           if (buf[i] == spareLetters[j]) {
/* 1527 */             i++;
/*      */           }
/* 1529 */           else if (buf[i] < spareLetters[j]) {
/*      */             break;
/*      */           } 
/* 1532 */         }  if (i < len)
/* 1533 */           continue;  lmMatch.addElement("  " + s1);
/*      */       } 
/* 1535 */       dataIn.close();
/*      */     }
/* 1537 */     catch (IOException exc) {}
/* 1538 */     jlCount.setText(lmMatch.size() + " Item(s) in list.");
/*      */     
/* 1540 */     Action doOK = new AbstractAction("OK")
/*      */       {
/*      */         public void actionPerformed(ActionEvent e) {
/* 1543 */           if (jl.getSelectedIndex() != -1) {
/* 1544 */             String s = ((String)jl.getSelectedValue()).trim();
/* 1545 */             AcrosticBuild.this.insertWord(s, Grid.yCurW);
/* 1546 */             (NodeList.nodeList[Grid.yCurW]).preset = true;
/* 1547 */             if (AcrosticBuild.spareLetters[0] == '\000') {
/* 1548 */               Methods.havePuzzle = true;
/*      */             }
/*      */             try {
/* 1551 */               DataInputStream dataIn = new DataInputStream(new FileInputStream(Op.ac[Op.AC.AcDic.ordinal()] + ".dic/xword.dic"));
/* 1552 */               for (int i1 = 0; i1 < 128; i1++)
/* 1553 */                 dataIn.readByte(); 
/* 1554 */               while (dataIn.available() > 2) {
/* 1555 */                 dataIn.readInt();
/* 1556 */                 String s1 = dataIn.readUTF();
/* 1557 */                 String s2 = dataIn.readUTF();
/* 1558 */                 if (s1.equalsIgnoreCase((NodeList.nodeList[Grid.yCurW]).word)) {
/* 1559 */                   (NodeList.nodeList[Grid.yCurW]).clue = s2;
/*      */                   break;
/*      */                 } 
/*      */               } 
/* 1563 */               dataIn.close();
/*      */             }
/* 1565 */             catch (IOException exc) {}
/*      */           } 
/* 1567 */           jdlgSuggest.dispose();
/* 1568 */           Methods.closeHelp();
/*      */         }
/*      */       };
/* 1571 */     JButton jbOK = Methods.newButton("doOK", doOK, 79, 10, 330, 85, 26);
/* 1572 */     jdlgSuggest.add(jbOK);
/*      */     
/* 1574 */     Action doCancel = new AbstractAction("Cancel") {
/*      */         public void actionPerformed(ActionEvent e) {
/* 1576 */           jdlgSuggest.dispose();
/* 1577 */           Methods.closeHelp();
/*      */         }
/*      */       };
/* 1580 */     JButton jbCancel = Methods.newButton("doCancel", doCancel, 67, 10, 365, 85, 26);
/* 1581 */     jdlgSuggest.add(jbCancel);
/*      */     
/* 1583 */     Action doHelp = new AbstractAction("<html><font size=6 color=BB0000 face=Serif>Help ", new ImageIcon("graphics/help.png")) {
/*      */         public void actionPerformed(ActionEvent e) {
/* 1585 */           Methods.cweHelp(null, jdlgSuggest, "Suggest a Word", CrosswordBuild.suggestHelp);
/*      */         }
/*      */       };
/* 1588 */     JButton jbHelp = Methods.newButton("doHelp", doHelp, 72, 105, 330, 160, 61);
/* 1589 */     jdlgSuggest.add(jbHelp);
/*      */     
/* 1591 */     jdlgSuggest.getRootPane().setDefaultButton(jbOK);
/* 1592 */     Methods.setDialogSize(jdlgSuggest, 275, 401);
/*      */   }
/*      */   
/*      */   void loadDictionary() {
/* 1596 */     int idx = 0, wordCount = 0;
/*      */     
/* 1598 */     this.allWords.clear();
/* 1599 */     this.listChar[idx] = ' ';
/* 1600 */     this.listIndex[idx] = 0;
/*      */ 
/*      */     
/*      */     try {
/* 1604 */       DataInputStream dataIn = new DataInputStream(new FileInputStream(Op.ac[Op.AC.AcDic.ordinal()] + ".dic/xword.dic")); int i;
/* 1605 */       for (i = 0; i < 128; ) { dataIn.readByte(); i++; }
/* 1606 */        while (dataIn.available() > 2) {
/* 1607 */         dataIn.readInt();
/* 1608 */         String s = dataIn.readUTF();
/*      */         
/* 1610 */         dataIn.readUTF();
/* 1611 */         int len = s.length();
/* 1612 */         if (len < Op.getInt(Op.AC.AcShort.ordinal(), Op.ac))
/* 1613 */           continue;  char[] buf = s.toCharArray();
/*      */         
/* 1615 */         char swap = buf[0]; buf[0] = buf[keyCol]; buf[keyCol] = swap;
/*      */         
/* 1617 */         for (i = 1; i < len - 1; i++) {
/* 1618 */           for (int k = i + 1; k < len; k++) {
/* 1619 */             if (buf[i] > buf[k])
/* 1620 */             { swap = buf[i]; buf[i] = buf[k]; buf[k] = swap; } 
/*      */           } 
/* 1622 */         }  int j; for (i = 1, j = 0; i < len && 
/* 1623 */           spareLetters[j] != '\000'; j++) {
/* 1624 */           if (buf[i] == spareLetters[j]) { i++; }
/*      */           
/* 1626 */           else if (buf[i] < spareLetters[j]) { break; }
/*      */         
/* 1628 */         }  if (i != len)
/*      */           continue; 
/* 1630 */         if (s.charAt(0) != this.listChar[idx]) {
/* 1631 */           this.listChar[++idx] = s.charAt(0);
/* 1632 */           this.listIndex[idx] = wordCount;
/*      */         } 
/* 1634 */         this.allWords.add(s + new String(buf, 0, len));
/* 1635 */         wordCount++;
/*      */       } 
/* 1637 */       this.listIndex[++idx] = wordCount;
/* 1638 */       dataIn.close();
/*      */     }
/* 1640 */     catch (IOException exc) {}
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void selectWords(ArrayList<String> al, int thisLen, int row) {
/* 1676 */     if (keyCol == 0) {
/* 1677 */       char ch = Op.ac[Op.AC.AcWords.ordinal()].charAt(row); int n;
/* 1678 */       for (n = 0; ch != this.listChar[n]; n++);
/* 1679 */       for (int k = this.listIndex[n]; k < this.listIndex[n + 1]; k++) {
/* 1680 */         String word = this.allWords.get(k); int len;
/* 1681 */         if ((len = word.length() / 2) == thisLen) {
/* 1682 */           char[] buf = word.substring(len).toCharArray();
/* 1683 */           word = word.substring(0, len);
/*      */           int i, j;
/* 1685 */           for (i = 1, j = 0; i < len && 
/* 1686 */             spareLetters[j] != '\000'; j++) {
/* 1687 */             if (buf[i] == spareLetters[j]) {
/* 1688 */               i++;
/*      */             }
/* 1690 */             else if (buf[i] < spareLetters[j]) {
/*      */               break;
/*      */             } 
/* 1693 */           }  if (i == len)
/* 1694 */             al.add(word); 
/*      */         } 
/*      */       } 
/*      */     } else {
/* 1698 */       for (int k = 0; k < this.allWords.size(); k++) {
/* 1699 */         String word = this.allWords.get(k); int len;
/* 1700 */         if ((len = word.length() / 2) == thisLen && 
/* 1701 */           word.charAt(keyCol) == Op.ac[Op.AC.AcWords.ordinal()].charAt(row)) {
/* 1702 */           char[] buf = word.substring(len).toCharArray();
/* 1703 */           word = word.substring(0, len);
/*      */           int i, j;
/* 1705 */           for (i = 1, j = 0; i < len && 
/* 1706 */             spareLetters[j] != '\000'; j++) {
/* 1707 */             if (buf[i] == spareLetters[j]) {
/* 1708 */               i++;
/*      */             }
/* 1710 */             else if (buf[i] < spareLetters[j]) {
/*      */               break;
/*      */             } 
/*      */           } 
/* 1714 */           if (i == len) {
/* 1715 */             al.add(word);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   int addWordFunction(int rowIndex) {
/* 1725 */     ArrayList<String> al = new ArrayList<>();
/*      */     
/* 1727 */     char[] localSpareLetters = new char[512];
/* 1728 */     char[] sl = new char[256];
/* 1729 */     int[] sCount = new int[256];
/* 1730 */     int[] aCount = new int[256];
/*      */     int spareCount;
/* 1732 */     for (spareCount = 0; spareLetters[spareCount] != '\000'; spareCount++) {
/* 1733 */       localSpareLetters[spareCount] = spareLetters[spareCount];
/*      */     }
/* 1735 */     int row = this.rowTable[rowIndex];
/* 1736 */     int thisLen = (NodeList.nodeList[row]).length;
/* 1737 */     al.clear();
/* 1738 */     selectWords(al, thisLen, row);
/* 1739 */     int max = al.size();
/* 1740 */     int[] alCount = new int[al.size()];
/* 1741 */     int[] alOrder = new int[al.size()];
/*      */     int i;
/* 1743 */     for (i = 0; i < spareCount; ) {
/* 1744 */       int j = 0; for (;; i++) {
/* 1745 */         if (sl[j] == '\000')
/* 1746 */           sl[j] = spareLetters[i]; 
/* 1747 */         if (sl[j] == spareLetters[i])
/* 1748 */         { sCount[j] = sCount[j] + 1; }
/*      */         else { j++; continue; }
/*      */       
/*      */       } 
/* 1752 */     }  int k; for (k = 0; k < max; k++) {
/* 1753 */       for (i = 0; i < thisLen; i++) {
/* 1754 */         for (int j = 0; j < 256; j++) {
/* 1755 */           if (((String)al.get(k)).charAt(i) == sl[j]) {
/* 1756 */             aCount[j] = aCount[j] + 1; break;
/*      */           } 
/*      */         } 
/*      */       } 
/* 1760 */     }  for (i = 0; sCount[i] > 0; i++) {
/* 1761 */       aCount[i] = 100 * sCount[i] / ((aCount[i] > 0) ? aCount[i] : 10000);
/*      */     }
/* 1763 */     if (!al.isEmpty()) {
/* 1764 */       for (k = 0; k < max; k++) {
/* 1765 */         alOrder[k] = k; int cnt;
/* 1766 */         for (cnt = i = 0; i < thisLen; i++) {
/* 1767 */           for (int j = 0; j < 256; j++) {
/* 1768 */             if (((String)al.get(k)).charAt(i) == sl[j]) {
/* 1769 */               cnt += aCount[j]; break;
/*      */             } 
/*      */           } 
/* 1772 */         }  alCount[k] = cnt;
/*      */       } 
/*      */       
/* 1775 */       for (i = 0; i < max - 1; i++) {
/* 1776 */         for (int j = i + 1; j < max; j++)
/* 1777 */         { if (alCount[alOrder[j]] > alCount[alOrder[i]])
/* 1778 */           { k = alOrder[j]; alOrder[j] = alOrder[i]; alOrder[i] = k; }  } 
/* 1779 */       }  for (i = 0; i < max; i++) {
/* 1780 */         insertWord(al.get(alOrder[i]), row);
/* 1781 */         if (rowIndex == this.acrosticTarget - 1)
/* 1782 */           return 1; 
/*      */         int ret;
/* 1784 */         if ((ret = addWordFunction(rowIndex + 1)) > 0) {
/* 1785 */           return ret;
/*      */         }
/* 1787 */         (NodeList.nodeList[row]).word = "" + Op.ac[Op.AC.AcWords.ordinal()].charAt(row);
/* 1788 */         for (int j = 0; j < spareCount; j++) {
/* 1789 */           spareLetters[j] = localSpareLetters[j];
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1794 */     if (this.count >= 20000) return 3; 
/* 1795 */     if (++this.count % 100 == 10) {
/* 1796 */       restoreFrame();
/*      */     }
/* 1798 */     if (Def.building == 2)
/* 1799 */       return 2; 
/* 1800 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   void buildPrep() {
/*      */     int i;
/*      */     int x;
/* 1808 */     for (i = x = 0; i < Op.ac[Op.AC.AcParagraph.ordinal()].length(); i++) {
/* 1809 */       char ch; if (Character.isLetter(ch = Op.ac[Op.AC.AcParagraph.ordinal()].charAt(i)))
/* 1810 */         spareLetters[x++] = Character.toUpperCase(ch); 
/*      */     } 
/* 1812 */     for (i = 0; i < x - 1; i++) {
/* 1813 */       for (int j = i + 1; j < x; j++) {
/* 1814 */         if (spareLetters[i] > spareLetters[j]) {
/* 1815 */           char ch = spareLetters[i];
/* 1816 */           spareLetters[i] = spareLetters[j];
/* 1817 */           spareLetters[j] = ch;
/*      */         } 
/*      */       } 
/* 1820 */     }  extractFromSpareLetters(Op.ac[Op.AC.AcWords.ordinal()], -1);
/*      */ 
/*      */     
/* 1823 */     for (i = 0; i < NodeList.nodeListLength; i++) {
/* 1824 */       if ((NodeList.nodeList[i]).preset)
/* 1825 */         extractFromSpareLetters((NodeList.nodeList[i]).word, keyCol); 
/*      */     } 
/*      */   }
/*      */   boolean buildAcrostic() {
/* 1829 */     Random r = new Random();
/* 1830 */     int ret = 3;
/*      */ 
/*      */     
/* 1833 */     buildPrep();
/* 1834 */     loadDictionary();
/* 1835 */     while (ret == 3 || ret == 0) {
/* 1836 */       buildPrep();
/* 1837 */       this.count = 0;
/*      */       
/*      */       int i;
/* 1840 */       for (this.acrosticTarget = i = 0; i < NodeList.nodeListLength; i++) {
/* 1841 */         if (!(NodeList.nodeList[i]).preset)
/* 1842 */           this.rowTable[this.acrosticTarget++] = i; 
/*      */       } 
/* 1844 */       for (i = 0; i < this.acrosticTarget; i++) {
/* 1845 */         int k = r.nextInt(this.acrosticTarget);
/* 1846 */         int j = this.rowTable[k]; this.rowTable[k] = this.rowTable[i]; this.rowTable[i] = j;
/*      */       } 
/*      */       int area;
/* 1849 */       for (area = 0; spareLetters[area] != '\000'; area++);
/* 1850 */       area += this.acrosticTarget;
/* 1851 */       for (i = 0; i < this.acrosticTarget; i++) {
/* 1852 */         (NodeList.nodeList[this.rowTable[i]]).length = area * 2 / (this.acrosticTarget - i) - Op.getInt(Op.AC.AcShort.ordinal(), Op.ac);
/* 1853 */         area -= (NodeList.nodeList[this.rowTable[i]]).length;
/*      */       } 
/*      */       
/* 1856 */       ret = addWordFunction(0);
/*      */       
/* 1858 */       if (ret == 1) {
/* 1859 */         for (i = 0; i < NodeList.nodeListLength - 1; i++) {
/* 1860 */           for (int j = i + 1; j < NodeList.nodeListLength; j++)
/* 1861 */           { if ((NodeList.nodeList[i]).word.equalsIgnoreCase((NodeList.nodeList[j]).word))
/* 1862 */               ret = 3;  } 
/* 1863 */         }  if (ret == 3)
/* 1864 */           continue;  NodeList.attachClues(Op.ac[Op.AC.AcDic.ordinal()], Boolean.valueOf(false));
/* 1865 */         if (!acrosticLinkage())
/* 1866 */           ret = 3; 
/*      */       } 
/*      */     } 
/* 1869 */     Def.dispCursor = Boolean.valueOf(true);
/* 1870 */     setSizesAndOffsetsW(0, 0, panelWordsW, panelWordsH, 20);
/* 1871 */     restoreFrame();
/*      */     
/* 1873 */     return (ret == 1);
/*      */   }
/*      */   
/*      */   void updateGridWords(MouseEvent e) {
/* 1877 */     int x = e.getX(), y = e.getY();
/*      */     
/* 1879 */     if (Def.building == 1)
/* 1880 */       return;  if (x < Grid.xOrgW || y < Grid.yOrgW)
/* 1881 */       return;  int i = (x - Grid.xOrgW) / Grid.xCellW;
/* 1882 */     int j = (y - Grid.yOrgW) / Grid.yCellW;
/* 1883 */     if (i >= Grid.xSzW || j >= Grid.ySzW)
/* 1884 */       return;  Grid.xCurW = 0; Grid.yCurW = j;
/* 1885 */     restoreFrame();
/*      */   }
/*      */   
/*      */   void handleKeyPressed(KeyEvent e) {
/* 1889 */     if (Def.building == 1)
/* 1890 */       return;  if (e.isAltDown())
/* 1891 */       return;  switch (e.getKeyCode()) { case 38:
/* 1892 */         if (Grid.yCurW > 0) Grid.yCurW--;  break;
/* 1893 */       case 40: if (Grid.yCurW < Grid.ySzW - 1) Grid.yCurW++;  break;
/* 1894 */       case 33: Grid.yCurW = 0; break;
/* 1895 */       case 34: Grid.yCurW = Grid.ySzW - 1; break; }
/*      */     
/* 1897 */     restoreFrame();
/*      */   }
/*      */ }


/* Location:              C:\Users\mustapha\Desktop\Crossword-Express.jar!\crosswordexpress\AcrosticBuild.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */