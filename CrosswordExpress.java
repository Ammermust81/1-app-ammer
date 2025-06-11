/*     */ package crosswordexpress;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.LayoutManager;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.MouseAdapter;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.event.WindowAdapter;
/*     */ import java.awt.event.WindowEvent;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.DefaultListModel;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JList;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.UnsupportedLookAndFeelException;
/*     */ import javax.swing.event.ListSelectionEvent;
/*     */ import javax.swing.plaf.ColorUIResource;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CrosswordExpress
/*     */   extends JFrame
/*     */ {
/*     */   static JFrame jfCWE;
/*     */   static JLabel jlPuz;
/*     */   static JLabel jl;
/*     */   int index;
/*     */   int i;
/*  51 */   int sp = 0; static DefaultListModel<String> lmPuzz; static JList<String> jlPuzz;
/*     */   static JScrollPane jspPuzz;
/*     */   static int xPos;
/*     */   static int yPos;
/*     */   static boolean CWEupgrade = false;
/*     */   static boolean upgraded = false;
/*     */   static boolean networkOn;
/*     */   static JPanel pp;
/*  59 */   String cweHelp = "<span class='m'>A brief History</span><br/><br/><div>During its development, this program has undergone very many changes, including a number of name changes as listed below:-<ul><li/><span class='s'>1992 - 1994</span><br/><div><b>The Daily Crossword</b> was an MS-DOS program which was limited to the creation and printing of Crossword puzzles only.</div><li/><span class='s'>1994 - 2009</span><br/><div><b>Crossword Express</b> began its life as a Windows program limited to just Crossword puzzles. It quickly became apparent from user enquiries that there was a requirement for an Apple Macintosh version of the program, and this was introduced in 1997. Both versions were written in the C programming language. A number of new puzzle types were also introduced during this period. Development of the program ceased in 2007 when sales were reduced to insignificant levels by software piracy.</div><li/><span class='s'>2009 - 2015</span><br/><div>The entire program was re-written in the Java language, and released under the new name of <b>Magnum Opus</b> to differentiate if from its predecessor. Java has the significant advantage that the resulting program will operate equally well on both Wndows and Macintosh. It was released as Freeware as it was obvious that Shareware was no longer a viable proposition.</div><li/><span class='s'>2015 onward</span><br/>Six years after the demise of the original Crossword Express it was considered appropriate to revive the name, so that in future all new releases will have the name Crossword Express.</div></ul><span class='m'>Menu Screen Functions</span><br/><br/><ul><li/>A Selection List allows you to select the type of puzzle you want to process. Simply click the name of the puzzle type, and then click the <b>and GO</b> button. A new window will appear giving you access to all of the functions required to construct, solve and print that type of puzzle.<p/><li/><b>Create new Dictionaries / Edit existing Dictionaries</b><p/>This button leads you to an extensive suite of <b>Dictionary Maintenance</b> functions.<p/><li/><b>Create new Grids and Templates / Edit existing Grids and Templates</b><p/>This button leads you to an extensive suite of <b>Grid Maintenance</b> functions.<p/><li/>The <b>Word Tools</b> button will take you to a sub-menu where you can access the following useful tools:-<p/><ul><li/><b>Find Matching Words</b><p/><li/><b>Find Anagrams</b><p/><li/><b>Find Contained Words</b><p/><li/><b>Find Container Words</b></ul><li/><b>You are using The latest version of Crossword Express.</b><p/>Normally, this button just confirms that you have the latest version of the program. The first time you use the program after the release of a new version, this button will have a red background, and will contain the text<br><center><b>A more recent version of Crossword Express is available. Click to upgrade now.</b></center>Click the button, and within seconds, the new version will be downloaded and installed on your computer.<p/><li/><b>Crossword Express</b> uses a considerable number of options by which the user can make changes to the appearance and behaviour of the program. The <b>Default Options</b> button will set all of these options to the initial default values.</ul></body>";
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
/*     */   CrosswordExpress() {
/* 110 */     if (System.getProperty("os.name").startsWith("Mac OS X")) {
/* 111 */       System.setProperty("apple.awt.graphics.UseQuartz", "true");
/*     */     }
/* 113 */     Op.loadAllOptions();
/* 114 */     File fl = new File("mosite!.txt");
/* 115 */     if (!fl.exists()) {
/* 116 */       networkStatus("startup.txt");
/*     */     }
/* 118 */     String[] puzzleTypes = { " Standard Crossword", " देवनागरी Crossword", " Freeform Crossword", " Wordsquare", " Acrostic", " Akari", " Domino", " Doublet", " Fillomino", " FindAQuote", " Futoshiki", " Gokigen", " Collatz", " Kakuro", " Kendoku", " Killer Sudoku", " LadderWord", " Letterdrop", " Marupeke", " Minesweeper", " Ouroboros", " PyramidWord", " Roundabouts", " Sikaku", " Slitherlink", " Sudoku", " Tatami", " Tents", " WordSearch", " Sixpack" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 126 */     int CROSSWORD = 0, DEVANAGARI = 1, FREEFORM = 2, WORDSQUARE = 3;
/* 127 */     int ACROSTIC = 4, AKARI = 5, DOMINO = 6, DOUBLET = 7;
/* 128 */     int FILLOMINO = 8, FINDAQUOTE = 9, FUTOSHIKI = 10, GOKIGEN = 11;
/* 129 */     int HAILSTONE = 12, KAKURO = 13, KENDOKU = 14, KSUDOKU = 15;
/* 130 */     int LADDERWORD = 16, LETTERDROP = 17, MARUPEKE = 18, MINESWEEPER = 19;
/* 131 */     int OUROBOROS = 20, PYRAMIDWORD = 21, ROUNDABOUTS = 22, SIKAKU = 23;
/* 132 */     int SLITHERLINK = 24, SUDOKU = 25, TATAMI = 26;
/* 133 */     int TENTS = 27, WORDSEARCH = 28, SIXPACK = 29;
/* 134 */     String[] ext = { "crossword", "devanagari", "crossword", "wordsquare", "acrostic", "akari", "domino", "doublet", "fillomino", "findaquote", "futoshiki", "gokigen", "hailstone", "kakuro", "kendoku", "ksudoku", "ladderword", "letterdrop", "marupeke", "minesweeper", "ouroboros", "pyramidword", "roundabouts", "sikaku", "slitherlink", "sudoku", "tatami", "tents", "wordsearch", "sixpack" };
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
/* 145 */     createFolders();
/* 146 */     downloadMissingFiles();
/*     */     
/* 148 */     Def.isMac = System.getProperty("os.name").startsWith("Mac OS");
/*     */     
/* 150 */     Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
/* 151 */     Methods.scrW = (int)screenSize.getWidth();
/* 152 */     Methods.scrH = (int)screenSize.getHeight();
/*     */     
/* 154 */     Def.puzzleMode = 1;
/* 155 */     jfCWE = new JFrame("Crossword Express Control Center");
/* 156 */     jfCWE.setDefaultCloseOperation(0);
/* 157 */     jfCWE.setVisible(true);
/* 158 */     jfCWE.setResizable(false);
/*     */     
/* 160 */     Def.insets = jfCWE.getInsets();
/* 161 */     jfCWE.setSize(964 + Def.insets.left + Def.insets.right, 450 + Def.insets.top + Def.insets.bottom);
/* 162 */     xPos = 100;
/* 163 */     int frameW = jfCWE.getWidth() + 10;
/* 164 */     if (xPos + frameW > Methods.scrW) xPos = Methods.scrW - frameW; 
/* 165 */     jfCWE.setLocation(xPos, yPos = 100);
/* 166 */     jfCWE
/* 167 */       .addWindowListener(new WindowAdapter() {
/*     */           public void windowClosing(WindowEvent we) {
/* 169 */             Op.saveOptions("miscellaneous.opt", Op.msc);
/* 170 */             CrosswordExpress.jfCWE.dispose();
/* 171 */             Op.saveOptions("miscellaneous.opt", Op.msc);
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 176 */     int width = jfCWE.getWidth() - Def.insets.left - Def.insets.right;
/* 177 */     int height = jfCWE.getHeight() - Def.insets.top - Def.insets.bottom;
/*     */     
/* 179 */     JPanel jpMenu = new JPanel();
/* 180 */     jpMenu.setBackground(Def.COLOR_MENUBG);
/* 181 */     jpMenu.setLayout((LayoutManager)null);
/* 182 */     jpMenu.setLocation(0, 0);
/* 183 */     jpMenu.setSize(width, height - 22);
/* 184 */     jpMenu.setOpaque(true);
/* 185 */     jpMenu.setBorder(BorderFactory.createLineBorder(new Color(128), 2));
/* 186 */     jfCWE.add(jpMenu);
/*     */     
/* 188 */     JPanel jpHead = new JPanel();
/* 189 */     jpHead.setBackground(Def.COLOR_HEADBG);
/* 190 */     jpHead.setLayout((LayoutManager)null);
/* 191 */     jpHead.setLocation(0, 0);
/* 192 */     jpHead.setSize(539, 116);
/* 193 */     jpHead.setOpaque(true);
/* 194 */     jpHead.setBorder(BorderFactory.createLineBorder(new Color(128), 2));
/* 195 */     jpMenu.add(jpHead);
/*     */     
/* 197 */     ImageIcon theIcon = new ImageIcon("graphics/cweicon.png");
/* 198 */     jl = new JLabel(theIcon);
/* 199 */     jl.setSize(80, 80);
/* 200 */     jl.setLocation(25, 18);
/* 201 */     jpHead.add(jl);
/*     */     
/* 203 */     jl = new JLabel("<html><center><font face=Serif color=000088 size=6>Crossword Express</font><font face=Verdana color=000088 size=3></font><br><font face=SansSerif color=000088 size=3></i> Ver " + Def.ver + "<br>" + "<font color=006666 size=4>Word, Number and Logic Puzzles.</font><br>" + "<font color=222222 size=3>Copyright &copy; 1993-2021 AUS-PC-SOFT Shareware<br>" + "email : crauswords@bigpond.com<br>" + "web : www.crauswords.com</font></font>");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 210 */     jl.setSize(428, 115);
/* 211 */     jl.setLocation(100, 0);
/* 212 */     jl.setHorizontalAlignment(0);
/* 213 */     jpHead.add(jl);
/*     */     
/* 215 */     jl = new JLabel("<html><font size=4>Select a Puzzle");
/* 216 */     jl.setSize(170, 20);
/* 217 */     jl.setLocation(10, 126);
/* 218 */     jl.setHorizontalAlignment(2);
/* 219 */     jpMenu.add(jl);
/*     */     
/* 221 */     jlPuz = new JLabel();
/* 222 */     jlPuz.setBorder(BorderFactory.createLineBorder(new Color(128), 2));
/* 223 */     jlPuz.setOpaque(true);
/* 224 */     jlPuz.setBackground(Color.white);
/* 225 */     jlPuz.setHorizontalAlignment(0);
/* 226 */     jlPuz.setSize(324, 320);
/* 227 */     jlPuz.setLocation(215, 120);
/* 228 */     jpMenu.add(jlPuz);
/*     */     
/* 230 */     lmPuzz = new DefaultListModel<>();
/* 231 */     jlPuzz = new JList<>(lmPuzz);
/* 232 */     jlPuzz.setFont(new Font("SansSerif", 1, 14));
/* 233 */     jspPuzz = new JScrollPane(jlPuzz);
/* 234 */     jspPuzz.setSize(200, 287);
/* 235 */     jspPuzz.setLocation(10, 153);
/* 236 */     jspPuzz.setBorder(BorderFactory.createLineBorder(new Color(128), 2));
/* 237 */     jspPuzz.setHorizontalScrollBarPolicy(31);
/* 238 */     jpMenu.add(jspPuzz);
/* 239 */     jlPuzz
/* 240 */       .addListSelectionListener(le -> {
/*     */           if (!jlPuzz.getValueIsAdjusting()) {
/*     */             this.index = jlPuzz.getSelectedIndex();
/*     */             
/*     */             jlPuz.setIcon(new ImageIcon("graphics/" + ((this.index == 2) ? "freeform" : paramArrayOfString[this.index]) + ".png"));
/*     */             
/*     */             Op.setInt(Op.MSC.LastOtherPuzzle.ordinal(), jlPuzz.getSelectedIndex(), Op.msc);
/*     */           } 
/*     */         });
/* 249 */     for (this.i = 0; this.i < ext.length - this.sp; this.i++)
/* 250 */       lmPuzz.addElement(puzzleTypes[this.i]); 
/* 251 */     jlPuzz.setSelectedIndex(Op.getInt(Op.MSC.LastOtherPuzzle.ordinal(), Op.msc));
/* 252 */     jlPuzz.ensureIndexIsVisible(Op.getInt(Op.MSC.LastOtherPuzzle.ordinal(), Op.msc));
/*     */     
/* 254 */     JButton jbGo = Methods.cweButton("and GO", 130, 120, 80, 28, null);
/* 255 */     jbGo.addActionListener(e -> { Methods.closeHelp(); jfCWE.setVisible(false); switch (Op.getInt(Op.MSC.LastOtherPuzzle.ordinal(), Op.msc)) { case 0: new CrosswordBuild(jfCWE); break;
/*     */             case 1: new DevanagariBuild(jfCWE); break;
/*     */             case 2: new FreeformBuild(jfCWE); break;
/*     */             case 3: new WordsquareBuild(jfCWE); break;
/*     */             case 4: new AcrosticBuild(jfCWE); break;
/*     */             case 5: new AkariBuild(jfCWE, false, 1, 1); break;
/*     */             case 6: new DominoBuild(jfCWE, false, 1, 1); break;
/*     */             case 7: new DoubletBuild(jfCWE); break;
/*     */             case 8: new FillominoBuild(jfCWE, false, 1, 1); break;
/*     */             case 9: new FindAQuote(jfCWE); break;
/*     */             case 10: new FutoshikiBuild(jfCWE, false, 1, 1); break;
/*     */             case 11: new GokigenBuild(jfCWE, false, 1, 1); break;
/*     */             case 12: new Hailstone(jfCWE); break;
/*     */             case 13: new KakuroBuild(jfCWE, false, 1, 1); break;
/*     */             case 14: new KendokuBuild(jfCWE, false, 1, 1); break;
/*     */             case 15: new KsudokuBuild(jfCWE, false, 1, 1); break;
/*     */             case 16: new LadderwordBuild(jfCWE); break;
/*     */             case 17: new LetterdropBuild(jfCWE); break;
/*     */             case 18: new MarupekeBuild(jfCWE, false, 1, 1); break;
/*     */             case 19: new MinesweeperBuild(jfCWE, false, 1, 1); break;
/*     */             case 20: new OuroborosBuild(jfCWE); break;
/*     */             case 21: new PyramidwordBuild(jfCWE); break;
/*     */             case 22:
/*     */               new RoundaboutsBuild(jfCWE, false, 1, 1); break;
/*     */             case 23:
/*     */               new SikakuBuild(jfCWE, false, 1, 1); break;
/*     */             case 24:
/*     */               new SlitherlinkBuild(jfCWE, false, 1, 1); break;
/*     */             case 25:
/*     */               new SudokuBuild(jfCWE, false, 1, 1); break;
/*     */             case 26:
/*     */               new TatamiBuild(jfCWE, false, 1, 1); break;
/*     */             case 27:
/*     */               new TentsBuild(jfCWE, false, 1, 1); break;
/*     */             case 28:
/*     */               new WordsearchBuild(jfCWE); break;
/*     */             case 29:
/* 292 */               new Sixpack(jfCWE); break; }  jlPuzz.requestFocus(); }); jpMenu.add(jbGo);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 297 */     JButton jbDictionary = Methods.cweButton("<html><center><font size=5 color=000088>DICTIONARY MAINTENANCE</font><hr><font size=4 color=004444>Create new Dictionaries<br>Edit existing Dictionaries", 544, 5, 410, 98, new ImageIcon("graphics/dic.png"));
/* 298 */     jbDictionary.addActionListener(e -> new DictionaryMtce(jfCWE));
/*     */     
/* 300 */     jpMenu.add(jbDictionary);
/*     */     
/* 302 */     JButton jbGridMain = Methods.cweButton("<html><center><font size=5 color=000088>GRID MAINTENANCE</font><hr><font size=4 color=004444>Create new Grids and Templates<br>Edit existing Grids and Templates", 544, 106, 410, 98, new ImageIcon("graphics/grid.png"));
/* 303 */     jbGridMain.addActionListener(e -> {
/*     */           jfCWE.setVisible(false);
/*     */           new GridMaintenance(jfCWE);
/*     */         });
/* 307 */     jpMenu.add(jbGridMain);
/*     */     
/* 309 */     JButton jbWordTools = Methods.cweButton("<html><center><font size=5 color=000088>WORD TOOLS</font><hr><font size=4 color=004444>Matching Words - Anagrams<br>Contained and Container Words", 544, 208, 410, 78, null);
/* 310 */     jbWordTools.addActionListener(e -> {
/*     */           jfCWE.setVisible(false);
/*     */           new WordTools(jfCWE);
/*     */         });
/* 314 */     jpMenu.add(jbWordTools);
/*     */     
/* 316 */     final JButton jbUpgrade = new JButton(CWEupgrade ? "<html><font face=arial size=4 color=FFFF00 style=bold><center><u>A</u> more recent version of Crossword Express is available.<br>Click to upgrade now." : "<html><center>You are using the latest version of Crossword Express.<br>When a new version is released, this button will become red and you will be able to use it to download the new version.");
/*     */     
/* 318 */     if (CWEupgrade) {
/* 319 */       jbUpgrade.setBackground(new Color(13369344));
/* 320 */       jbUpgrade.setForeground(Color.yellow);
/*     */     } 
/* 322 */     jbUpgrade.setSize(410, 54);
/* 323 */     jbUpgrade.setLocation(544, 290);
/* 324 */     jpMenu.add(jbUpgrade);
/* 325 */     jbUpgrade.setBorder(BorderFactory.createLineBorder(new Color(128), 2));
/* 326 */     jbUpgrade
/* 327 */       .addMouseListener(new MouseAdapter() {
/*     */           public void mouseEntered(MouseEvent evt) {
/* 329 */             jbUpgrade.setBackground(new Color(56763));
/*     */           } public void mouseExited(MouseEvent evt) {
/* 331 */             jbUpgrade.setBackground(Def.COLOR_BUTTONBG);
/*     */           }
/*     */         });
/* 334 */     jbUpgrade
/* 335 */       .addActionListener(ae -> {
/*     */           if (CWEupgrade) {
/*     */             InputStream in = null;
/*     */ 
/*     */             
/*     */             try { URL theURL = new URL("http://www.crauswords.com/program/CWEupgrade.jar");
/*     */               
/*     */               HttpURLConnection httpConn = (HttpURLConnection)theURL.openConnection();
/*     */               
/*     */               in = httpConn.getInputStream();
/*     */               
/*     */               FileOutputStream out = new FileOutputStream("CWEupgrade.jar");
/*     */               byte[] buffer = new byte[10000];
/*     */               int bytesRead;
/*     */               while ((bytesRead = in.read(buffer)) != -1) {
/*     */                 out.write(buffer, 0, bytesRead);
/*     */               }
/*     */               out.close();
/*     */               httpConn.setConnectTimeout(2); }
/* 354 */             catch (MalformedURLException ex) {  }
/* 355 */             catch (IOException ex) {  }
/*     */             finally { if (in != null) {
/*     */                 try {
/*     */                   in.close();
/* 359 */                 } catch (IOException ex) {}
/*     */               } }
/*     */             
/*     */             try {
/*     */               Runtime.getRuntime().exec("java -jar CWEupgrade.jar");
/* 364 */             } catch (IOException ex) {}
/*     */             
/*     */             jfCWE.dispose();
/*     */             
/*     */             CWEupgrade = false;
/*     */           } 
/*     */         });
/* 371 */     JButton jbDefault = Methods.cweButton("Set all Crossword Express Options to the default values", 544, 348, 410, 28, null);
/* 372 */     jbDefault.addActionListener(e -> {
/*     */           if (JOptionPane.showConfirmDialog(jfCWE, "Are you sure you want to default all options", "Confirm Default operation", 0) == 1) {
/*     */             return;
/*     */           }
/*     */           Op.defaultOptions();
/*     */           JOptionPane.showMessageDialog(jfCWE, "All program Options have been returned to their original default values.");
/*     */           jlPuzz.setSelectedIndex(Op.getInt(Op.MSC.LastOtherPuzzle.ordinal(), Op.msc));
/*     */         });
/* 380 */     jpMenu.add(jbDefault);
/*     */     
/* 382 */     JButton jbVisit = Methods.cweButton("Visit Crossword Express Web Site", 544, 380, 240, 28, null);
/* 383 */     jbVisit.addActionListener(e -> {
/*     */           browserLaunch.openURL(jfCWE, "http://www.crauswords.com/index.html");
/*     */           Methods.toFront(jfCWE);
/*     */         });
/* 387 */     jpMenu.add(jbVisit);
/*     */     
/* 389 */     JButton jbExit = Methods.cweButton("Exit Crossword Express", 544, 412, 240, 28, null);
/* 390 */     jbExit.addActionListener(e -> {
/*     */           jfCWE.dispose();
/*     */           Op.saveOptions("miscellaneous.opt", Op.msc);
/*     */         });
/* 394 */     jpMenu.add(jbExit);
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
/* 410 */     JButton jbHelp = Methods.cweButton("<html><font size=6 color=BB0000 face=Serif>Help ", 794, 380, 160, 60, new ImageIcon("graphics/help.png"));
/* 411 */     jbHelp.addActionListener(e -> Methods.cweHelp(jfCWE, null, "Crossword Express", this.cweHelp));
/*     */ 
/*     */     
/* 414 */     jpMenu.add(jbHelp);
/*     */     
/* 416 */     jlPuzz.requestFocus();
/*     */     
/* 418 */     if (upgraded) {
/* 419 */       browserLaunch.openURL(jfCWE, "http://www.crauswords.com/history.html");
/* 420 */       Methods.toFront(jfCWE);
/*     */     } 
/*     */   }
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
/*     */   public static void toPrint(JFrame oldjf, String file) {
/* 439 */     if (!Methods.havePuzzle) {
/* 440 */       Methods.noPuzzle(oldjf, "Print");
/*     */     } else {
/* 442 */       Methods.closeHelp();
/* 443 */       Def.prevMode = Def.puzzleMode;
/* 444 */       new Print(oldjf, file);
/* 445 */       oldjf.dispose();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void transfer(int newMode, JFrame oldjf) {
/* 450 */     Methods.closeHelp();
/* 451 */     Def.prevMode = Def.puzzleMode;
/* 452 */     Def.puzzleMode = newMode;
/* 453 */     switch (newMode) { case 1:
/* 454 */         jfCWE.setVisible(true); break;
/* 455 */       case 4: new CrosswordBuild(oldjf); break;
/* 456 */       case 5: new CrosswordSolve(oldjf); break;
/* 457 */       case 220: new DevanagariBuild(oldjf); break;
/* 458 */       case 210: new WordsquareBuild(oldjf); break;
/* 459 */       case 10: new AcrosticBuild(oldjf); break;
/* 460 */       case 11: new AcrosticSolve(oldjf); break;
/* 461 */       case 20: new AkariBuild(oldjf, false, 1, 1); break;
/* 462 */       case 21: new AkariSolve(oldjf); break;
/* 463 */       case 30: new CodewordSolve(oldjf); break;
/* 464 */       case 40: new DominoBuild(oldjf, false, 1, 1); break;
/* 465 */       case 41: new DominoSolve(oldjf); break;
/* 466 */       case 50: new DoubletBuild(oldjf); break;
/* 467 */       case 51: new DoubletSolve(oldjf); break;
/* 468 */       case 60: new FillinSolve(oldjf); break;
/* 469 */       case 70: new FillominoBuild(oldjf, false, 1, 1); break;
/* 470 */       case 71: new FillominoSolve(oldjf); break;
/* 471 */       case 72: new FindAQuote(oldjf); break;
/* 472 */       case 6: new FreeformBuild(oldjf); break;
/* 473 */       case 80: new FutoshikiBuild(oldjf, false, 1, 1); break;
/* 474 */       case 81: new FutoshikiSolve(oldjf); break;
/* 475 */       case 90: new GokigenBuild(oldjf, false, 1, 1); break;
/* 476 */       case 91: new GokigenSolve(oldjf); break;
/* 477 */       case 100: new KakuroBuild(oldjf, false, 1, 1); break;
/* 478 */       case 101: new KakuroSolve(oldjf); break;
/* 479 */       case 110: new KendokuBuild(oldjf, false, 1, 1); break;
/* 480 */       case 111: new KendokuSolve(oldjf); break;
/* 481 */       case 112: new KsudokuBuild(oldjf, false, 1, 1); break;
/* 482 */       case 113: new KsudokuSolve(oldjf); break;
/* 483 */       case 120: new LadderwordBuild(oldjf); break;
/* 484 */       case 121: new LadderwordSolve(oldjf); break;
/* 485 */       case 130: new LetterdropBuild(oldjf); break;
/* 486 */       case 131: new LetterdropSolve(oldjf); break;
/* 487 */       case 230: new MarupekeBuild(oldjf, false, 1, 1); break;
/* 488 */       case 231: new MarupekeSolve(oldjf); break;
/* 489 */       case 132: new MinesweeperBuild(oldjf, false, 1, 1); break;
/* 490 */       case 133: new MinesweeperSolve(oldjf); break;
/* 491 */       case 134: new OuroborosBuild(oldjf); break;
/* 492 */       case 135: new OuroborosSolve(oldjf); break;
/* 493 */       case 140: new PyramidwordBuild(oldjf); break;
/* 494 */       case 141: new PyramidwordSolve(oldjf); break;
/* 495 */       case 150: new RoundaboutsBuild(oldjf, false, 1, 1); break;
/* 496 */       case 151: new RoundaboutsSolve(oldjf); break;
/* 497 */       case 160: new SikakuBuild(oldjf, false, 1, 1); break;
/* 498 */       case 161: new SikakuSolve(oldjf); break;
/* 499 */       case 170: new SlitherlinkBuild(oldjf, false, 1, 1); break;
/* 500 */       case 171: new SlitherlinkSolve(oldjf); break;
/* 501 */       case 180: new SudokuBuild(oldjf, false, 1, 1); break;
/* 502 */       case 181: new SudokuSolve(oldjf); break;
/* 503 */       case 182: new TatamiBuild(oldjf, false, 1, 1); break;
/* 504 */       case 183: new TatamiSolve(oldjf); break;
/* 505 */       case 190: new TentsBuild(oldjf, false, 1, 1); break;
/* 506 */       case 191: new TentsSolve(oldjf); break;
/* 507 */       case 200: new WordsearchBuild(oldjf); break;
/* 508 */       case 201: new WordsearchSolve(oldjf); break; }
/*     */     
/* 510 */     oldjf.dispose();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void networkStatus(String theFile) {
/* 515 */     networkOn = true;
/*     */     
/* 517 */     try { URL url = new URL("http://www.crauswords.com/stats/" + theFile);
/* 518 */       BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
/* 519 */       String ver = br.readLine();
/* 520 */       if (ver.substring(0, 2).equalsIgnoreCase("20") && ver.compareToIgnoreCase(Def.ver) > 0)
/* 521 */         CWEupgrade = true; 
/* 522 */       br.close(); }
/*     */     
/* 524 */     catch (MalformedURLException mal) {  }
/* 525 */     catch (IOException io)
/* 526 */     { networkOn = false; }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public static void createFolders() {
/* 532 */     String[] newFolder = { "akari", "domino", "futoshiki", "fillomino", "findaquote", "gokigen", "hailstone", "kakuro", "kendoku", "ksudoku", "lib", "marupeke", "minesweeper", "sikaku", "slitherlink", "sudoku", "tatami", "tents", "roundabouts", "" };
/*     */ 
/*     */     
/* 535 */     for (int i = 0; newFolder[i].length() > 0; i++) {
/* 536 */       File fl = new File(newFolder[i]);
/* 537 */       if (!fl.exists()) {
/* 538 */         fl.mkdir();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void downloadMissingFiles() {
/* 547 */     InputStream in = null;
/*     */ 
/*     */     
/* 550 */     String[] newFile = { "graphics/devanagari.png", "graphics/dic.png", "graphics/grid.png", "graphics/ksudoku.png", "graphics/ouroboros.png", "graphics/wordsquare.png", "graphics/marupeke.png", "graphics/darrow.png", "graphics/uarrow.png", "graphics/hailstone.png", "grids/ouroboros1.template", "grids/ouroboros2.template", "grids/ouroboros3.template", "grids/dev8A.grid", "grids/dev8B.grid", "grids/dev11A.grid", "grids/dev11B.grid", "grids/dev13A.grid", "grids/dev13B.grid", "ksudoku/difficulty-2.ksudoku", "ksudoku/difficulty-3.ksudoku", "ksudoku/difficulty-4.ksudoku", "layouts/wordweave.layout", "ouroboros.dic/xword.dic", "devHindi.dic/xword.dic", "devHindi.dic/sample1.crossword", "devHindi.dic/sample2.crossword", "devHindi.dic/sample3.crossword", "devHindi.dic/sample4.crossword", "devHindi.dic/sample5.crossword", "devHindi.dic/sample6.crossword", "devNepali.dic/xword.dic", "devNepali.dic/sample1.crossword", "devNepali.dic/sample2.crossword", "devNepali.dic/sample3.crossword", "devNepali.dic/sample4.crossword", "devNepali.dic/sample5.crossword", "devNepali.dic/sample6.crossword", "" };
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
/* 591 */     String[] contentFile = { "graphics-devanagari.png", "graphics-dic.png", "graphics-grid.png", "graphics-ksudoku.png", "graphics-ouroboros.png", "graphics-wordsquare.png", "graphics-marupeke.png", "graphics-darrow.png", "graphics-uarrow.png", "graphics-hailstone.png", "grids-ouroboros1.template", "grids-ouroboros2.template", "grids-ouroboros3.template", "grids-dev8A.grid", "grids-dev8B.grid", "grids-dev11A.grid", "grids-dev11B.grid", "grids-dev13A.grid", "grids-dev13B.grid", "ksudoku-difficulty-2.ksudoku", "ksudoku-difficulty-3.ksudoku", "ksudoku-difficulty-4.ksudoku", "layouts-wordweave.layout", "ouroboros.dic-xword.dic", "devHindi.dic-xword.dic", "devHindi.dic-sample1.crossword", "devHindi.dic-sample2.crossword", "devHindi.dic-sample3.crossword", "devHindi.dic-sample4.crossword", "devHindi.dic-sample5.crossword", "devHindi.dic-sample6.crossword", "devNepali.dic-xword.dic", "devNepali.dic-sample1.crossword", "devNepali.dic-sample2.crossword", "devNepali.dic-sample3.crossword", "devNepali.dic-sample4.crossword", "devNepali.dic-sample5.crossword", "devNepali.dic-sample6.crossword", "" };
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
/* 633 */     for (int i = 0; newFile[i].length() > 0; i++) {
/* 634 */       if (newFile[i].contains("/")) {
/* 635 */         String theFolder = newFile[i].substring(0, newFile[i].indexOf('/'));
/* 636 */         File file = new File(theFolder);
/* 637 */         if (!file.exists()) {
/* 638 */           file.mkdir();
/*     */         }
/*     */       } 
/* 641 */       File fl = new File(newFile[i]);
/* 642 */       if (!fl.exists())
/*     */         
/* 644 */         try { URL theURL = new URL("http://www.crauswords.com/newfiles/" + contentFile[i]);
/* 645 */           HttpURLConnection httpConn = (HttpURLConnection)theURL.openConnection();
/* 646 */           in = httpConn.getInputStream();
/* 647 */           FileOutputStream out = new FileOutputStream(newFile[i]);
/* 648 */           byte[] buffer = new byte[10000]; int bytesRead;
/* 649 */           while ((bytesRead = in.read(buffer)) != -1)
/* 650 */             out.write(buffer, 0, bytesRead); 
/* 651 */           out.close(); in.close();
/* 652 */           httpConn.setConnectTimeout(2); }
/*     */         
/* 654 */         catch (MalformedURLException ex) {  }
/* 655 */         catch (IOException ex) {  }
/*     */         finally
/* 657 */         { if (in != null)
/* 658 */             try { in.close(); }
/* 659 */             catch (IOException ex) {}  }
/*     */          
/*     */       try {
/* 662 */         Thread.sleep(2L);
/* 663 */       } catch (InterruptedException e) {}
/*     */     } 
/*     */   }
/*     */   
/*     */   static void def() {
/* 668 */     Op.updateOption(Op.MSC.LastOtherPuzzle.ordinal(), "0", Op.msc);
/* 669 */     Op.updateOption(Op.MSC.GridW.ordinal(), "870", Op.msc);
/* 670 */     Op.updateOption(Op.MSC.GridH.ordinal(), "733", Op.msc);
/* 671 */     Op.updateOption(Op.MSC.PrintW.ordinal(), "990", Op.msc);
/* 672 */     Op.updateOption(Op.MSC.PrintH.ordinal(), "780", Op.msc);
/* 673 */     Op.updateOption(Op.MSC.LayoutName.ordinal(), "puzzle", Op.msc);
/* 674 */     Op.updateOption(Op.MSC.DictionaryName.ordinal(), "english", Op.msc);
/* 675 */     Op.updateOption(Op.MSC.GridName.ordinal(), "am13-1.grid", Op.msc);
/* 676 */     Op.updateOption(Op.MSC.TemplateName.ordinal(), "sample.template", Op.msc);
/* 677 */     Op.updateOption(Op.MSC.WordToolsDic.ordinal(), "english", Op.msc);
/* 678 */     Op.updateOption(Op.MSC.Langacross.ordinal(), "ACROSS", Op.msc);
/* 679 */     Op.updateOption(Op.MSC.Langdown.ordinal(), "DOWN", Op.msc);
/* 680 */     Op.updateOption(Op.MSC.Langwords.ordinal(), "WORDS", Op.msc);
/* 681 */     Op.updateOption(Op.MSC.Langclues.ordinal(), "CLUES", Op.msc);
/* 682 */     Op.updateOption(Op.MSC.Langtext.ordinal(), "TEXT", Op.msc);
/* 683 */     Op.updateOption(Op.MSC.SolutionPuz.ordinal(), "sample.crossword", Op.msc);
/* 684 */     Op.updateOption(Op.MSC.ImportAlphabet.ordinal(), "English", Op.msc);
/* 685 */     Op.updateOption(Op.MSC.ShortestImport.ordinal(), "3", Op.msc);
/* 686 */     Op.updateOption(Op.MSC.Ppi.ordinal(), "300", Op.msc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] args) throws IOException {
/*     */     try {
/* 696 */       UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
/* 697 */     } catch (UnsupportedLookAndFeelException|ClassNotFoundException|InstantiationException|IllegalAccessException e) {}
/* 698 */     UIManager.put("OptionPane.background", new ColorUIResource(Def.COLOR_DIALOGBG));
/* 699 */     UIManager.put("Panel.background", new ColorUIResource(Def.COLOR_DIALOGBG));
/* 700 */     UIManager.put("Button.background", Def.COLOR_BUTTONBG);
/* 701 */     if (args.length > 0 && args[0].equalsIgnoreCase("CWEupgrade")) upgraded = true; 
/* 702 */     SwingUtilities.invokeLater(() -> new CrosswordExpress());
/*     */   }
/*     */ }


/* Location:              C:\Users\mustapha\Desktop\Crossword-Express.jar!\crosswordexpress\CrosswordExpress.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */