/*      */ package crosswordexpress;
/*      */ import java.awt.Color;
/*      */ import java.awt.Font;
/*      */ import java.awt.FontMetrics;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.io.DataInputStream;
/*      */ import java.io.File;
/*      */ import javax.swing.AbstractAction;
/*      */ import javax.swing.Action;
/*      */ import javax.swing.JButton;
/*      */ import javax.swing.JCheckBox;
/*      */ import javax.swing.JDialog;
/*      */ import javax.swing.JFrame;
/*      */ import javax.swing.JLabel;
/*      */ import javax.swing.JPanel;
/*      */ import javax.swing.JTextField;
/*      */ 
/*      */ public class Methods {
/*      */   static boolean havePuzzle;
/*      */   static boolean clickedOK;
/*   22 */   static String puzzleTitle = ""; static byte noReveal; static byte noErrors; static String theFileName;
/*   23 */   static String author = "";
/*   24 */   static String copyright = "";
/*   25 */   static String puzzleNumber = "";
/*   26 */   static String puzzleNotes = "";
/*      */   static String dictionaryName;
/*      */   static Timer tfTimer;
/*      */   static int scrW;
/*      */   static int scrH;
/*   31 */   static int hmPuz = 36;
/*      */   static int puzType;
/*   33 */   static int itemsPP = 1;
/*      */   
/*   35 */   static String newPuzzle = "<div>This dialog contains six input fields and four check boxes which enable you to input some descriptive material concerning the puzzle, or to configure its mode of operation when it is used with the on-screen <b>Solve</b> function.</div><p/><div>When you enter this dialog, you may find that some or all of the fields already contain data. This will be the data which applied to the most recent puzzle with which you interacted. Any or all of these fields may be retained for the new puzzle if you wish, or you can edit the fields with new data.</div><p/><span class='m'>The input fields are:-</span><ul><li/><b>File Name: </b>Enter a file name for the puzzle which you are about to construct. The maximum number of characters you can use for the name will depend on the operating system of your computer. It is suggested that you limit the name to a maximum of 20 characters, and that you refrain from using space characters within the name. If you type in a name which is already in use, you will receive a warning. If you ignore this warning, then the existing puzzle will be  destroyed when the newly constructed one is automatically saved to disk.<p/><li/><b>Puzzle Name: </b>This is an optional field. If a Name is entered, it can be used by the printing function to associate a title with the printed puzzle.<p/><li/><b>Author: </b>If you are creating puzzles for publication in newspapers or magazines, you can enter an author attribution which can be associated with the puzzle when it is printed.<p/><li/><b>Copyright: </b>Similarly, a copyright notice entered here can be associated with the puzzle when it is printed.<p/><li/><b>Puzzle Number: </b>If you like to print a serial number with your puzzles, this field will provide you with a very simple way of doing so.<p/><li/><p/><b>Puzzle Notes: </b>Some puzzles have special features built into them, and details of these features need to be conveyed to the solver. The print function of this program provides a simple method of associating these notes with the puzzle when it is printed.<p/></ul><span class='m'>The check-boxes are:-</span><ul><li/><b>Disable Reveal Feature: </b>Setting this check box will result in the puzzle being saved in a way which prevents the <b>Reveal one Symbol</b> and <b>Reveal Solution</b> buttons from working in the integrated Solve functions.<p/><li/><b>Disable Show Errors Feature: </b>Similarly, the <b>Reveal Errors</b> function will not work if this check box is selected.</ul></body>";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   66 */   static String dandpOptions = "<div>Before you print any puzzle, you should consider the set of options which you can use to control the appearance of the printed output. Most of these options will also be carried through to the on-screen display of the puzzle.<p/></div><ul><li/><b>Color Options</b><br/>The Dialog contains a list of the colors which can be changed, and alongside of each element is a small rectangle which is set to the currently defined color for that element. This colored rectangle is actually a button, and clicking it will lead you to a Color Selection dialog where you can select a new color for the element in question.<p/><li/><b>Check-box Controls</b><br/><ul><li/>Normally, the puzzle and its solution will be printed in black and white, but if you are producing puzzles for a color publication, you can elect to have them printed in the colors you nominated using the Color Options described above. Check boxes are provided to allow independent control of this function for puzzles and solutions.<p/><li/>An additional check-box will appear if you are printing Word-search puzzles. This gives you the choice of displaying a list of the words hidden in the puzzle, or as an interesting alternative, to print a list of the clues which apply to those words.<p/><li/>If you are printing a crossword, there will be a pair of check-boxes labeled <b>French Puzzles : Across clues use letter IDs</b> and <b>French Puzzles : Down clues use letter IDs.</b> As the labels suggest you can control the appearance of the clues printed with a French style puzzle.<p/><li/>If you are printing a crossword, there will also be a check-box labeled <b>Insert word separators</b>. If the puzzle you are going to print contains a <b>Multi-Word</b> word such as <b>BILLCLINTON</b> AND the clue contains a length specification eg. <b>42nd president of the US (4,7)</b>, AND if you check <b>Insert word separators</b> THEN separators will be inserted into the puzzle at the break point between the individual words.<p/><li/>When a crossword solution is printed or exported, the default situation is that the word IDs will not be included. On occasions, it may be desirable to have the IDs included, and checking the <b>Include IDs in Solution</b> check box will achieve this result.<p/></ul><li/><b>Font Options</b><br/>Most of the puzzles within Crossword Express will require the printing of at least some alpha or numeric content. In these cases, the Dialog will contain one or more Combo Boxes which can be used to select the fonts which will be used. Each Combo Box has an attached label which describes the text element which it controls, and a text area which displays a sample of the currently selected font.<p/><li/><b>OK and Cancel</b><br/>When you have made all of the changes you require, clicking the <span class=\"hi\">OK</span> button will make those changes effective. If you click the <span class=\"hi\">Cancel</span> button, all of the changes you have made will be abandoned.</ul></body>";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  100 */   static String editCluesHelp = "<div>This function allows you to:-</div><ul><li/>Add clues to the puzzle in the case that some or all of the words in the dictionary used to create the puzzle had not been provided with clues.<li/>Alter the clue attached to the word if it is not to your liking.</ul><div>You can step sequentially through the clues by using the <b>Next</b> and <b>Prev</b> buttons, or you can jump to any clue in the puzzle by typing its number into the <b>INDEX</b> data field and clicking the <b>GO</b> button. If you type a number which is outside of the range of numbers for the puzzle, the program will default to either the first word or the last word as appropriate.</body";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  113 */   static String printKDPbook = "<div>The controls in this dialog are as follows:-<li/><b>Number of Pages to Print.</b><br/>Remember that each page accomodates six puzzles, so before using this function you should be very sure that you have created a number of puzzles equal to six times the number of pages you request.<p/><li/><b>Print Puzzle Book with Color.</b><br/>That's right! Simply clicking this checkbox will result in all puzzles and solutions being printed in color.<p/><li/><b>Export KDP Puzzles</b> and <b>Export KDP Solutions</b>.<br/>This pair of radio buttons determines what will be printed when you subsequently click the Export button.<p/><li/><b>Export button</b>.<br/>Clicking this button will result in the printing of the KDP book according to your specifications.</ul></body";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int clueIndex;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static String wizardText(String title, String text) {
/*  129 */     return "<html><table width='320' cellpadding='5' align='center'><tr><td bgcolor='004400' colspan='4'><center><font color='FFFFFF' size='6'><b>Construction Wizard</b></font></center></td></tr></table><table width='319' cellpadding='5' align='center'><tr><td bgcolor='666600'><table width='306' cellpadding='4' align='center'><tr><td bgcolor='FFFFEE'><table><tr><td><img src='file:graphics/wizard2.png'></img></td><td align='center'><font color='005500' size='6'>" + title + "</font>" + "</td>" + "</tr>" + "</table>" + "<div align='justify'>" + "<font size='4' color='555500' face='Arial'>" + text + "</div>" + "</td>" + "</tr>" + "</table>" + "</td>" + "</tr>" + "</table>";
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int renderText(Graphics2D g2, int left, int top, int width, int height, String face, int style, String text, int align, int vAlign, boolean print, int color, int suggestedFontSize) {
/*      */     int fontSize;
/*  175 */     String[] token = new String[500];
/*  176 */     int[] fontNum = new int[500], theStyle = { 0, 2, 1, 3 };
/*  177 */     int[] boldStyle = { 1, 3, 1, 3 };
/*  178 */     int adj = 0;
/*  179 */     Font[] theFont = new Font[4];
/*      */ 
/*      */ 
/*      */     
/*  183 */     if (suggestedFontSize == 0) {
/*  184 */       fontSize = (height < 20) ? height : 18;
/*      */     } else {
/*  186 */       fontSize = suggestedFontSize;
/*  187 */     }  StringTokenizer st = new StringTokenizer(text); int numTokens;
/*  188 */     for (numTokens = 0; st.hasMoreTokens(); numTokens++) {
/*  189 */       token[numTokens] = st.nextToken();
/*      */     }
/*      */     int i, j;
/*  192 */     for (j = i = 0; i < numTokens; i++) {
/*  193 */       if (token[i].contains("<i>")) { token[i] = token[i].replace("<i>", ""); j++; }
/*  194 */        if (token[i].contains("<I>")) { token[i] = token[i].replace("<I>", ""); j++; }
/*  195 */        if (token[i].contains("<b>")) { token[i] = token[i].replace("<b>", ""); j += 2; }
/*  196 */        if (token[i].contains("<B>")) { token[i] = token[i].replace("<B>", ""); j += 2; }
/*  197 */        fontNum[i] = j;
/*  198 */       if (token[i].contains("</i>")) { token[i] = token[i].replace("</i>", ""); j--; }
/*  199 */        if (token[i].contains("</I>")) { token[i] = token[i].replace("</I>", ""); j--; }
/*  200 */        if (token[i].contains("</b>")) { token[i] = token[i].replace("</b>", ""); j -= 2; }
/*  201 */        if (token[i].contains("</B>")) { token[i] = token[i].replace("</B>", ""); j -= 2; }
/*      */     
/*      */     } 
/*      */     
/*      */     while (true) {
/*  206 */       for (i = 0; i < 4; i++)
/*  207 */         theFont[i] = new Font(face, Op.getBool(Op.CW.AwBold.ordinal(), Op.cw).booleanValue() ? boldStyle[i] : theStyle[i], fontSize); 
/*      */       boolean reduce;
/*  209 */       for (reduce = false, i = 0; i < numTokens; i++) {
/*  210 */         g2.setFont(theFont[fontNum[i]]);
/*  211 */         FontMetrics fm = g2.getFontMetrics();
/*  212 */         if (fm.stringWidth(token[i]) > width) reduce = true; 
/*      */       } 
/*  214 */       if (reduce) { fontSize--; continue; }
/*      */        break;
/*      */     } 
/*  217 */     g2.setColor(new Color(color));
/*  218 */     for (j = 0; j < 2; j++) {
/*  219 */       int startToken = 0, y = top;
/*  220 */       while (startToken < numTokens) {
/*      */         
/*  222 */         int span = 1;
/*  223 */         int lineWidth = g2.getFontMetrics(theFont[fontNum[startToken]]).stringWidth(token[startToken]);
/*  224 */         while (startToken + span < numTokens) {
/*  225 */           int increment = g2.getFontMetrics(theFont[fontNum[startToken + span]]).stringWidth(" " + token[startToken + span]);
/*  226 */           if (lineWidth + increment > width)
/*  227 */             break;  lineWidth += increment;
/*  228 */           span++;
/*      */         } 
/*      */ 
/*      */         
/*  232 */         if (print && j == 1) {
/*  233 */           int thisAlign = align;
/*  234 */           if (align == 3 && startToken + span == numTokens)
/*  235 */             thisAlign = 0; 
/*  236 */           drawTokens(g2, y + fontSize + adj, left, token, fontNum, theFont, startToken, span, thisAlign, width - lineWidth);
/*      */         } 
/*  238 */         startToken += span;
/*  239 */         y += fontSize;
/*  240 */         if (y + fontSize > top + height) {
/*      */           
/*  242 */           fontSize--;
/*  243 */           for (i = 0; i < 4; i++)
/*  244 */             theFont[i] = new Font(face, Op.getBool(Op.CW.AwBold.ordinal(), Op.cw).booleanValue() ? boldStyle[i] : theStyle[i], fontSize); 
/*  245 */           startToken = 0;
/*  246 */           y = top;
/*      */         } 
/*      */       } 
/*      */       
/*  250 */       if (vAlign == 5) {
/*  251 */         adj = (top + height - y) / 2;
/*      */       }
/*      */     } 
/*  254 */     return fontSize;
/*      */   }
/*      */   
/*      */   static void drawTokens(Graphics2D g2, int t, int l, String[] token, int[] fontNum, Font[] theFont, int start, int span, int align, int micro) {
/*  258 */     int vSpan = span - 1;
/*      */     
/*  260 */     if (align == 2)
/*  261 */       l += micro / 2; 
/*  262 */     if (align == 1)
/*  263 */       l += micro; 
/*  264 */     for (int i = start; i < start + span; i++) {
/*  265 */       g2.setFont(theFont[fontNum[i]]);
/*  266 */       g2.drawString(token[i], l, t);
/*  267 */       l += g2.getFontMetrics(theFont[fontNum[i]]).stringWidth(" " + token[i]);
/*  268 */       if (align == 3 && vSpan > 0) {
/*  269 */         int extra = micro / vSpan;
/*  270 */         l += extra;
/*  271 */         micro -= extra;
/*  272 */         vSpan--;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static String confirmDictionary(String folder, boolean theme) {
/*  282 */     File fl = new File(System.getProperty("user.dir"));
/*  283 */     String[] s = fl.list();
/*      */     int i;
/*  285 */     for (i = 0; i < s.length && !s[i].equals(folder); i++);
/*  286 */     if (i == s.length) {
/*  287 */       fl = new File(System.getProperty("user.dir"));
/*  288 */       s = fl.list();
/*  289 */       i = 0;
/*  290 */       while (i < s.length && (!s[i].endsWith(".dic") || (theme && !s[i].startsWith("$")) || (!theme && s[i].startsWith("$"))))
/*  291 */         i++; 
/*  292 */       folder = s[i];
/*      */     } 
/*  294 */     return folder.substring(0, folder.indexOf(".dic"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static String hintReplacements(String hint, int count) {
/*  301 */     String str = hint;
/*  302 */     for (int i = 0; i < count; i++)
/*  303 */       str = str.replaceFirst("~", Grid.hintWord[i]); 
/*  304 */     return str;
/*      */   }
/*      */   
/*      */   static void clearGrid(int[][] gridName) {
/*  308 */     for (int j = 0; j < Grid.gridMax; j++) {
/*  309 */       for (int i = 0; i < Grid.gridMax; i++)
/*  310 */         gridName[i][j] = 0; 
/*      */     } 
/*      */   }
/*      */   static JButton newButton(String name, Action action, int key, int x, int y, int w, int h) {
/*  314 */     final JButton jb = new JButton(action);
/*  315 */     jb.setLocation(x, y);
/*  316 */     jb.setSize(w, h);
/*  317 */     jb.setBorder(BorderFactory.createLineBorder(new Color(136), 2));
/*      */     
/*  319 */     jb.getActionMap().put(name, action);
/*  320 */     KeyStroke keyGo = KeyStroke.getKeyStroke(key, 2);
/*  321 */     jb.getInputMap(2).put(keyGo, name);
/*      */     
/*  323 */     jb
/*  324 */       .addMouseListener(new MouseAdapter() {
/*      */           public void mouseEntered(MouseEvent evt) {
/*  326 */             if (jb.getBackground() == Def.COLOR_BUTTONBG)
/*  327 */               jb.setBackground(Def.COLOR_BUTTONHL); 
/*      */           }
/*      */           public void mouseExited(MouseEvent evt) {
/*  330 */             if (jb.getBackground() == Def.COLOR_BUTTONHL) {
/*  331 */               jb.setBackground(Def.COLOR_BUTTONBG);
/*      */             }
/*      */           }
/*      */         });
/*  335 */     return jb;
/*      */   }
/*      */   
/*      */   static JButton cweButton(String name, int x, int y, int w, int h, ImageIcon theIcon) {
/*  339 */     final JButton jb = new JButton(name, theIcon);
/*  340 */     jb.setLocation(x, y);
/*  341 */     jb.setSize(w, h);
/*  342 */     jb.setBorder(BorderFactory.createLineBorder(new Color(136), 2));
/*      */     
/*  344 */     jb
/*  345 */       .addMouseListener(new MouseAdapter() {
/*      */           public void mouseEntered(MouseEvent evt) {
/*  347 */             if (jb.getBackground() == Def.COLOR_BUTTONBG)
/*  348 */               jb.setBackground(Def.COLOR_BUTTONHL); 
/*      */           }
/*      */           public void mouseExited(MouseEvent evt) {
/*  351 */             if (jb.getBackground() == Def.COLOR_BUTTONHL) {
/*  352 */               jb.setBackground(Def.COLOR_BUTTONBG);
/*      */             }
/*      */           }
/*      */         });
/*  356 */     return jb;
/*      */   }
/*      */ 
/*      */   
/*      */   static void editClues(JFrame jf, final String mode) {
/*  361 */     clueIndex = 0;
/*      */     
/*  363 */     final JDialog jdlgEditClues = new JDialog(jf, "Edit Clues", true);
/*  364 */     jdlgEditClues.setSize(400, 537);
/*  365 */     jdlgEditClues.setResizable(false);
/*  366 */     jdlgEditClues.setLayout((LayoutManager)null);
/*  367 */     jdlgEditClues.setLocation((jf.getLocation()).x, (jf.getLocation()).y);
/*      */     
/*  369 */     JLabel jl = new JLabel("Index");
/*  370 */     jl.setForeground(Def.COLOR_LABEL);
/*  371 */     jl.setSize(300, 20);
/*  372 */     jl.setLocation(10, 5);
/*  373 */     jl.setHorizontalAlignment(2);
/*  374 */     jdlgEditClues.add(jl);
/*      */     
/*  376 */     final JTextField jtfIndex = new JTextField("" + (clueIndex + 1), 15);
/*  377 */     jtfIndex.setSize(50, 20);
/*  378 */     jtfIndex.setLocation(55, 5);
/*  379 */     jtfIndex.selectAll();
/*  380 */     jtfIndex.setHorizontalAlignment(2);
/*  381 */     jdlgEditClues.add(jtfIndex);
/*      */     
/*  383 */     final JLabel jlTheWord = new JLabel("Word");
/*  384 */     jlTheWord.setForeground(Def.COLOR_LABEL);
/*  385 */     jlTheWord.setSize(300, 20);
/*  386 */     jlTheWord.setLocation(10, 25);
/*  387 */     jlTheWord.setHorizontalAlignment(2);
/*  388 */     jdlgEditClues.add(jlTheWord);
/*      */     
/*  390 */     JLabel jl2 = new JLabel("Clue");
/*  391 */     jl2.setForeground(Def.COLOR_LABEL);
/*  392 */     jl2.setSize(60, 20);
/*  393 */     jl2.setLocation(10, 45);
/*  394 */     jl2.setHorizontalAlignment(2);
/*  395 */     jdlgEditClues.add(jl2);
/*      */     
/*  397 */     final JTextArea jtaTheClue = new JTextArea((NodeList.nodeList[clueIndex]).clue);
/*  398 */     jtaTheClue.setLineWrap(true);
/*  399 */     jtaTheClue.setWrapStyleWord(true);
/*  400 */     JScrollPane jsp = new JScrollPane(jtaTheClue);
/*  401 */     jsp.setSize(340, 150);
/*  402 */     jsp.setLocation(10, 65);
/*  403 */     jtaTheClue.selectAll();
/*  404 */     jdlgEditClues.add(jsp);
/*  405 */     jtaTheClue.setFont(new Font("SansSerif", 1, 13));
/*      */     
/*  407 */     Action doGo = new AbstractAction("Go") {
/*      */         public void actionPerformed(ActionEvent e) {
/*  409 */           Methods.clueIndex = Integer.parseInt(jtfIndex.getText()) - 1;
/*  410 */           if (Methods.clueIndex >= NodeList.nodeListLength) Methods.clueIndex = NodeList.nodeListLength - 1; 
/*  411 */           if (Methods.clueIndex < 0) Methods.clueIndex = 0; 
/*  412 */           jtfIndex.setText("" + (Methods.clueIndex + 1));
/*  413 */           jlTheWord.setText("WORD " + (NodeList.nodeList[Methods.clueIndex]).word);
/*  414 */           jtaTheClue.setText((NodeList.nodeList[Methods.clueIndex]).clue);
/*      */         }
/*      */       };
/*  417 */     JButton jbGo = newButton("doGo", doGo, 71, 110, 5, 60, 20);
/*  418 */     jdlgEditClues.add(jbGo);
/*      */     
/*  420 */     Action doNext = new AbstractAction("Next") {
/*      */         public void actionPerformed(ActionEvent e) {
/*  422 */           (NodeList.nodeList[Methods.clueIndex]).clue = jtaTheClue.getText();
/*  423 */           if (Methods.clueIndex < NodeList.nodeListLength - 1) Methods.clueIndex++; 
/*  424 */           jtfIndex.setText("" + (Methods.clueIndex + 1));
/*  425 */           jlTheWord.setText("<html><font color=006644 size=3>WORD &nbsp;</font>" + (NodeList.nodeList[Methods.clueIndex]).word);
/*  426 */           jtaTheClue.setText((NodeList.nodeList[Methods.clueIndex]).clue);
/*      */         }
/*      */       };
/*  429 */     JButton jbNext = newButton("doNext", doNext, 78, 10, 225, 70, 26);
/*  430 */     jdlgEditClues.add(jbNext);
/*      */     
/*  432 */     Action doPrev = new AbstractAction("Prev") {
/*      */         public void actionPerformed(ActionEvent e) {
/*  434 */           (NodeList.nodeList[Methods.clueIndex]).clue = jtaTheClue.getText();
/*  435 */           if (Methods.clueIndex > 0) Methods.clueIndex--; 
/*  436 */           jtfIndex.setText("" + (Methods.clueIndex + 1));
/*  437 */           jlTheWord.setText("<html><font color=006644 size=3>WORD &nbsp;</font>" + (NodeList.nodeList[Methods.clueIndex]).word);
/*  438 */           jtaTheClue.setText((NodeList.nodeList[Methods.clueIndex]).clue);
/*      */         }
/*      */       };
/*  441 */     JButton jbPrev = newButton("doPrev", doPrev, 80, 10, 260, 70, 26);
/*  442 */     jdlgEditClues.add(jbPrev);
/*      */     
/*  444 */     Action doOK = new AbstractAction("OK") {
/*      */         public void actionPerformed(ActionEvent e) {
/*  446 */           (NodeList.nodeList[Methods.clueIndex]).clue = jtaTheClue.getText();
/*  447 */           switch (mode) {
/*      */             case "Ouroboros":
/*  449 */               OuroborosBuild.saveOuroboros(Op.ob[Op.OB.ObPuz.ordinal()]);
/*      */               break;
/*      */             case "Wordsearch":
/*  452 */               WordsearchBuild.saveWordsearch(Op.ws[Op.WS.WsPuz.ordinal()]);
/*      */               break;
/*      */           } 
/*  455 */           jdlgEditClues.dispose();
/*      */         }
/*      */       };
/*  458 */     JButton jbOK = newButton("doOK", doOK, 79, 90, 225, 100, 26);
/*  459 */     jdlgEditClues.add(jbOK);
/*      */     
/*  461 */     Action doCancel = new AbstractAction("Cancel") {
/*      */         public void actionPerformed(ActionEvent e) {
/*  463 */           switch (mode) {
/*      */             case "Ouroboros":
/*  465 */               OuroborosBuild.loadOuroboros(Op.ob[Op.OB.ObPuz.ordinal()]);
/*      */               break;
/*      */             case "Wordsearch":
/*  468 */               WordsearchBuild.loadWordsearch(Op.ws[Op.WS.WsPuz.ordinal()]);
/*      */               break;
/*      */           } 
/*  471 */           jdlgEditClues.dispose();
/*      */         }
/*      */       };
/*  474 */     JButton jbCancel = newButton("doCancel", doCancel, 67, 90, 260, 100, 26);
/*  475 */     jdlgEditClues.add(jbCancel);
/*      */     
/*  477 */     Action doHelp = new AbstractAction("<html><font size=6 color=BB0000 face=Serif>Help ", new ImageIcon("graphics/help.png")) {
/*      */         public void actionPerformed(ActionEvent e) {
/*  479 */           Methods.cweHelp(null, jdlgEditClues, "Edit Clues", Methods.editCluesHelp);
/*      */         }
/*      */       };
/*  482 */     JButton jbHelp = newButton("doHelp", doHelp, 72, 200, 225, 150, 63);
/*  483 */     jdlgEditClues.add(jbHelp);
/*      */     
/*  485 */     setDialogSize(jdlgEditClues, 360, 295);
/*      */   }
/*      */   
/*      */   static void moveCluesToDic(String dicName) {
/*  489 */     int i = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  494 */     byte[] dicHeader = new byte[128];
/*      */     
/*  496 */     NodeList.sortNodeList(0);
/*      */     
/*  498 */     try { DataInputStream oldDicIn = new DataInputStream(new FileInputStream(dicName + ".dic/xword.dic"));
/*  499 */       DataOutputStream newDicOut = new DataOutputStream(new FileOutputStream(dicName + ".dic/xword.new"));
/*  500 */       oldDicIn.read(dicHeader, 0, 128);
/*  501 */       int wordDat = oldDicIn.readInt();
/*  502 */       String wordOld = oldDicIn.readUTF();
/*  503 */       String clueOld = oldDicIn.readUTF();
/*  504 */       newDicOut.write(dicHeader, 0, 128);
/*      */       while (true)
/*  506 */       { if (i == NodeList.nodeListLength || (NodeList.nodeList[i]).word.compareTo(wordOld) > 0) {
/*      */           
/*  508 */           newDicOut.writeInt(wordDat);
/*  509 */           newDicOut.writeUTF(wordOld);
/*  510 */           newDicOut.writeUTF(clueOld);
/*  511 */           wordDat = oldDicIn.readInt();
/*  512 */           wordOld = oldDicIn.readUTF();
/*  513 */           clueOld = oldDicIn.readUTF();
/*      */         }
/*  515 */         else if ((NodeList.nodeList[i]).word.compareTo(wordOld) < 0) {
/*  516 */           i++;
/*      */         } else {
/*  518 */           int j; for (j = 0; j < clueOld.length() && 
/*  519 */             !clueOld.substring(j).startsWith((NodeList.nodeList[i]).clue); j++);
/*      */           
/*  521 */           if (j == clueOld.length()) {
/*  522 */             if (j > 0)
/*  523 */               clueOld = clueOld + "*"; 
/*  524 */             clueOld = clueOld + (NodeList.nodeList[i]).clue;
/*      */           } 
/*  526 */           newDicOut.writeInt(wordDat);
/*  527 */           newDicOut.writeUTF(wordOld);
/*  528 */           newDicOut.writeUTF(clueOld);
/*  529 */           wordDat = oldDicIn.readInt();
/*  530 */           wordOld = oldDicIn.readUTF();
/*  531 */           clueOld = oldDicIn.readUTF();
/*  532 */           i++;
/*      */         } 
/*  534 */         if (oldDicIn.available() == 0)
/*  535 */         { newDicOut.writeInt(wordDat);
/*  536 */           newDicOut.writeUTF(wordOld);
/*  537 */           newDicOut.writeUTF(clueOld);
/*      */ 
/*      */ 
/*      */           
/*  541 */           oldDicIn.close();
/*  542 */           newDicOut.close(); }
/*      */         else { continue; }
/*  544 */          File fl = new File(dicName + ".dic/xword.dic");
/*  545 */         fl.delete();
/*  546 */         fl = new File(dicName + ".dic/xword.new");
/*  547 */         fl.renameTo(new File(dicName + ".dic/xword.dic"));
/*  548 */         NodeList.sortNodeList(2); return; }  } catch (IOException exc) {} File file = new File(dicName + ".dic/xword.dic"); file.delete(); file = new File(dicName + ".dic/xword.new"); file.renameTo(new File(dicName + ".dic/xword.dic")); NodeList.sortNodeList(2);
/*      */   }
/*      */   
/*      */   static String myPrepareName(String fileName) {
/*  552 */     return "<html><font color=006644 size=3>" + fileName.substring(0, fileName.lastIndexOf('.'));
/*      */   }
/*      */   
/*      */   static void setDialogSize(JDialog theDialog, int width, int height) {
/*  556 */     theDialog.pack();
/*  557 */     Insets insets = theDialog.getInsets();
/*  558 */     theDialog.setSize(width + insets.left + insets.right, height + insets.top + insets.bottom);
/*  559 */     theDialog.setVisible(true);
/*      */   }
/*      */   
/*      */   static void setFrameSize(JFrame jf, int width, int height) {
/*  563 */     jf.pack();
/*  564 */     Insets insets = jf.getInsets();
/*  565 */     jf.setSize(width + insets.left + insets.right, height + insets.top + insets.bottom);
/*  566 */     jf.setVisible(true);
/*      */   }
/*      */ 
/*      */   
/*      */   static void toFront(JFrame jfParent) {
/*  571 */     ActionListener timerTF = ae -> {
/*      */         paramJFrame.setAlwaysOnTop(true);
/*      */         paramJFrame.setAlwaysOnTop(false);
/*      */         tfTimer.stop();
/*      */       };
/*  576 */     tfTimer = new Timer(3000, timerTF);
/*  577 */     tfTimer.start();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void selectDictionary(JFrame jfParent, String dicName, int mode) {
/*  585 */     dictionaryName = dicName;
/*  586 */     clickedOK = false;
/*  587 */     final JDialog jdlgSelDic = new JDialog(jfParent, "Select Dictionary", true);
/*  588 */     jdlgSelDic.setSize(200, 220);
/*  589 */     jdlgSelDic.setResizable(false);
/*  590 */     jdlgSelDic.setLayout((LayoutManager)null);
/*  591 */     jdlgSelDic.setLocation(jfParent.getX(), jfParent.getY());
/*      */     
/*  593 */     DefaultListModel<String> lmDic = new DefaultListModel<>();
/*  594 */     final JList<String> jlDic = new JList<>(lmDic);
/*  595 */     jlDic.setFont(new Font("SansSerif", 1, 14));
/*  596 */     JScrollPane jspDic = new JScrollPane(jlDic);
/*  597 */     jspDic.setSize(180, 196);
/*  598 */     jspDic.setLocation(10, 10);
/*  599 */     jspDic.setBorder(BorderFactory.createLineBorder(new Color(8912896), 2));
/*  600 */     jspDic.setHorizontalScrollBarPolicy(31);
/*  601 */     jdlgSelDic.add(jspDic);
/*      */     
/*  603 */     File fl = new File(System.getProperty("user.dir"));
/*  604 */     String[] s = fl.list();
/*  605 */     for (int i = 0; i < s.length; i++) {
/*  606 */       if (s[i].endsWith(".dic") && !s[i].startsWith(".") && ((
/*  607 */         mode == 1 && !s[i].startsWith("$")) || (mode == 2 && s[i]
/*  608 */         .startsWith("$")) || mode == 3 || (mode == 4 && s[i]
/*      */         
/*  610 */         .startsWith("dev")))) {
/*  611 */         s[i] = s[i].substring(0, s[i].lastIndexOf('.'));
/*  612 */         lmDic.addElement(" " + s[i]);
/*      */       } 
/*  614 */     }  jlDic.setSelectedValue(" " + dicName, true);
/*      */     
/*  616 */     Action doOK = new AbstractAction("OK") {
/*      */         public void actionPerformed(ActionEvent e) {
/*  618 */           Methods.dictionaryName = ((String)jlDic.getSelectedValue()).trim();
/*  619 */           jdlgSelDic.dispose();
/*  620 */           Methods.clickedOK = true;
/*      */ 
/*      */           
/*      */           try {
/*  624 */             DataInputStream dataIn = new DataInputStream(new FileInputStream(Methods.dictionaryName + ".dic/xword.dic"));
/*  625 */             dataIn.read(DictionaryMtce.dicHeader, 0, 128);
/*  626 */             dataIn.close();
/*  627 */           } catch (IOException exc) {}
/*      */         }
/*      */       };
/*  630 */     JButton jbOK = newButton("doOK", doOK, 79, 10, 215, 85, 26);
/*  631 */     jdlgSelDic.add(jbOK);
/*      */     
/*  633 */     Action doCancel = new AbstractAction("Cancel") {
/*      */         public void actionPerformed(ActionEvent e) {
/*  635 */           jdlgSelDic.dispose();
/*      */         }
/*      */       };
/*  638 */     JButton jbCancel = newButton("doCancel", doCancel, 67, 105, 215, 85, 26);
/*  639 */     jdlgSelDic.add(jbCancel);
/*      */     
/*  641 */     jdlgSelDic.getRootPane().setDefaultButton(jbOK);
/*  642 */     setDialogSize(jdlgSelDic, 200, 250);
/*      */   }
/*      */   
/*      */   static void puzzleDescriptionDialog(JFrame jf, String fileName, final String folder, final String extension) {
/*  646 */     final JDialog jdlgNewPuzzle = new JDialog(jf, "Puzzle Description", true);
/*  647 */     jdlgNewPuzzle.setSize(370, 385);
/*  648 */     jdlgNewPuzzle.setResizable(false);
/*  649 */     jdlgNewPuzzle.setLayout((LayoutManager)null);
/*  650 */     jdlgNewPuzzle.setLocation(jf.getX(), jf.getY());
/*      */     
/*  652 */     jdlgNewPuzzle
/*  653 */       .addWindowListener(new WindowAdapter() {
/*      */           public void windowClosing(WindowEvent we) {
/*  655 */             Methods.closeHelp();
/*      */           }
/*      */         });
/*      */     
/*  659 */     closeHelp();
/*      */     
/*  661 */     JLabel jlFileName = new JLabel("File Name:");
/*  662 */     jlFileName.setForeground(Def.COLOR_LABEL);
/*  663 */     jlFileName.setSize(117, 20);
/*  664 */     jlFileName.setLocation(5, 5);
/*  665 */     jlFileName.setHorizontalAlignment(4);
/*  666 */     jdlgNewPuzzle.add(jlFileName);
/*      */     
/*  668 */     final JTextField jtfFileName = new JTextField(fileName, 15);
/*  669 */     jtfFileName.setSize(225, 20);
/*  670 */     jtfFileName.setLocation(130, 5);
/*  671 */     jtfFileName.selectAll();
/*  672 */     jtfFileName.setHorizontalAlignment(2);
/*  673 */     jdlgNewPuzzle.add(jtfFileName);
/*      */     
/*  675 */     JLabel jlPuzzleName = new JLabel("Puzzle Name:");
/*  676 */     jlPuzzleName.setForeground(Def.COLOR_LABEL);
/*  677 */     jlPuzzleName.setSize(117, 20);
/*  678 */     jlPuzzleName.setLocation(5, 30);
/*  679 */     jlPuzzleName.setHorizontalAlignment(4);
/*  680 */     jdlgNewPuzzle.add(jlPuzzleName);
/*      */     
/*  682 */     final JTextField jtfPuzzleName = new JTextField(puzzleTitle, 15);
/*  683 */     jtfPuzzleName.setSize(225, 20);
/*  684 */     jtfPuzzleName.setLocation(130, 30);
/*  685 */     jtfPuzzleName.selectAll();
/*  686 */     jtfPuzzleName.setHorizontalAlignment(2);
/*  687 */     jdlgNewPuzzle.add(jtfPuzzleName);
/*      */     
/*  689 */     JLabel jlAuthor = new JLabel("Author:");
/*  690 */     jlAuthor.setForeground(Def.COLOR_LABEL);
/*  691 */     jlAuthor.setSize(117, 20);
/*  692 */     jlAuthor.setLocation(5, 55);
/*  693 */     jlAuthor.setHorizontalAlignment(4);
/*  694 */     jdlgNewPuzzle.add(jlAuthor);
/*      */     
/*  696 */     final JTextField jtfAuthor = new JTextField(author, 15);
/*  697 */     jtfAuthor.setSize(225, 20);
/*  698 */     jtfAuthor.setLocation(130, 55);
/*  699 */     jtfAuthor.selectAll();
/*  700 */     jtfAuthor.setHorizontalAlignment(2);
/*  701 */     jdlgNewPuzzle.add(jtfAuthor);
/*      */     
/*  703 */     JLabel jlCopyright = new JLabel("Copyright:");
/*  704 */     jlCopyright.setForeground(Def.COLOR_LABEL);
/*  705 */     jlCopyright.setSize(117, 20);
/*  706 */     jlCopyright.setLocation(5, 80);
/*  707 */     jlCopyright.setHorizontalAlignment(4);
/*  708 */     jdlgNewPuzzle.add(jlCopyright);
/*      */     
/*  710 */     final JTextField jtfCopyright = new JTextField(copyright, 15);
/*  711 */     jtfCopyright.setSize(225, 20);
/*  712 */     jtfCopyright.setLocation(130, 80);
/*  713 */     jtfCopyright.selectAll();
/*  714 */     jtfCopyright.setHorizontalAlignment(2);
/*  715 */     jdlgNewPuzzle.add(jtfCopyright);
/*      */     
/*  717 */     JLabel jlPuzzleNumber = new JLabel("Puzzle Number:");
/*  718 */     jlPuzzleNumber.setForeground(Def.COLOR_LABEL);
/*  719 */     jlPuzzleNumber.setSize(117, 20);
/*  720 */     jlPuzzleNumber.setLocation(5, 105);
/*  721 */     jlPuzzleNumber.setHorizontalAlignment(4);
/*  722 */     jdlgNewPuzzle.add(jlPuzzleNumber);
/*      */     
/*  724 */     final JTextField jtfPuzzleNumber = new JTextField(puzzleNumber, 15);
/*  725 */     jtfPuzzleNumber.setSize(225, 20);
/*  726 */     jtfPuzzleNumber.setLocation(130, 105);
/*  727 */     jtfPuzzleNumber.selectAll();
/*  728 */     jtfPuzzleNumber.setHorizontalAlignment(2);
/*  729 */     jdlgNewPuzzle.add(jtfPuzzleNumber);
/*      */     
/*  731 */     JLabel jlPuzzleNotes = new JLabel("Puzzle Notes:");
/*  732 */     jlPuzzleNotes.setForeground(Def.COLOR_LABEL);
/*  733 */     jlPuzzleNotes.setSize(117, 20);
/*  734 */     jlPuzzleNotes.setLocation(5, 130);
/*  735 */     jlPuzzleNotes.setHorizontalAlignment(4);
/*  736 */     jdlgNewPuzzle.add(jlPuzzleNotes);
/*      */     
/*  738 */     final JTextArea jtaPuzzleNotes = new JTextArea(puzzleNotes);
/*  739 */     jtaPuzzleNotes.setLineWrap(true);
/*  740 */     jtaPuzzleNotes.setWrapStyleWord(true);
/*  741 */     JScrollPane jsp = new JScrollPane(jtaPuzzleNotes);
/*  742 */     jsp.setSize(225, 90);
/*  743 */     jsp.setLocation(130, 130);
/*  744 */     jtaPuzzleNotes.selectAll();
/*  745 */     jdlgNewPuzzle.add(jsp);
/*      */     
/*  747 */     final JCheckBox jcbNoReveal = new JCheckBox("Disable Reveal Feature", (noReveal != 0));
/*  748 */     jcbNoReveal.setForeground(Def.COLOR_LABEL);
/*  749 */     jcbNoReveal.setOpaque(false);
/*  750 */     jcbNoReveal.setSize(300, 20);
/*  751 */     jcbNoReveal.setLocation(20, 225);
/*  752 */     jdlgNewPuzzle.add(jcbNoReveal);
/*      */     
/*  754 */     final JCheckBox jcbNoErrors = new JCheckBox("Disable Show Errors Feature", (noErrors != 0));
/*  755 */     jcbNoErrors.setForeground(Def.COLOR_LABEL);
/*  756 */     jcbNoErrors.setOpaque(false);
/*  757 */     jcbNoErrors.setSize(300, 20);
/*  758 */     jcbNoErrors.setLocation(20, 250);
/*  759 */     jdlgNewPuzzle.add(jcbNoErrors);
/*      */     
/*  761 */     Action doOK = new AbstractAction("OK") {
/*      */         public void actionPerformed(ActionEvent e) {
/*  763 */           int response = 0;
/*      */ 
/*      */           
/*  766 */           String fName = jtfFileName.getText();
/*  767 */           if (fName.length() == 0 || fName.charAt(0) == '.') {
/*  768 */             Methods.clickedOK = false;
/*  769 */             jdlgNewPuzzle.dispose();
/*      */             return;
/*      */           } 
/*  772 */           File fl = new File(folder + '/' + fName + extension);
/*  773 */           if (fl.exists()) {
/*  774 */             response = JOptionPane.showConfirmDialog(jdlgNewPuzzle, "<html>You already have a file by that name!<br>Continue anyway?", "Warning", 0);
/*      */           }
/*      */ 
/*      */           
/*  778 */           if (!fl.exists() || response == 0) {
/*  779 */             Methods.theFileName = fName + extension;
/*  780 */             Methods.puzzleTitle = jtfPuzzleName.getText();
/*  781 */             Methods.author = jtfAuthor.getText();
/*  782 */             Methods.copyright = jtfCopyright.getText();
/*  783 */             Methods.puzzleNumber = jtfPuzzleNumber.getText();
/*  784 */             Methods.puzzleNotes = jtaPuzzleNotes.getText();
/*  785 */             Methods.noReveal = (byte)(jcbNoReveal.isSelected() ? 1 : 0);
/*  786 */             Methods.noErrors = (byte)(jcbNoErrors.isSelected() ? 1 : 0);
/*  787 */             jdlgNewPuzzle.dispose();
/*  788 */             Methods.clickedOK = true;
/*      */           } 
/*      */         }
/*      */       };
/*  792 */     JButton jbOK = newButton("doOK", doOK, 79, 20, 280, 100, 26);
/*  793 */     jdlgNewPuzzle.add(jbOK);
/*      */     
/*  795 */     Action doCancel = new AbstractAction("Cancel") {
/*      */         public void actionPerformed(ActionEvent e) {
/*  797 */           Methods.clickedOK = false;
/*  798 */           jdlgNewPuzzle.dispose();
/*  799 */           Methods.closeHelp();
/*      */         }
/*      */       };
/*  802 */     JButton jbCancel = newButton("doCancel", doCancel, 67, 20, 315, 100, 26);
/*  803 */     jdlgNewPuzzle.add(jbCancel);
/*      */     
/*  805 */     Action doHelp = new AbstractAction("<html><font size=6 color=BB0000 face=Serif>Help ", new ImageIcon("graphics/help.png")) {
/*      */         public void actionPerformed(ActionEvent e) {
/*  807 */           Methods.cweHelp(null, jdlgNewPuzzle, "Puzzle Description", Methods.newPuzzle);
/*      */         }
/*      */       };
/*  810 */     JButton jbHelp = newButton("doHelp", doHelp, 72, 140, 280, 160, 61);
/*  811 */     jdlgNewPuzzle.add(jbHelp);
/*      */     
/*  813 */     jdlgNewPuzzle.getRootPane().setDefaultButton(jbOK);
/*  814 */     setDialogSize(jdlgNewPuzzle, 365, 351);
/*      */   }
/*      */   
/*      */   static boolean deleteAPuzzle(JFrame jf, String file, String folder, JPanel pp) {
/*  818 */     if (!havePuzzle) {
/*  819 */       noPuzzle(jf, "Delete");
/*      */     } else {
/*  821 */       if (JOptionPane.showConfirmDialog(jf, "<html>Delete the Puzzle <font size=3 color=444488>" + file + "</font>?", "Delete a Puzzle", 0) == 0);
/*      */       
/*  823 */       File fl = new File(folder + "/" + file);
/*  824 */       fl.delete();
/*  825 */       return true;
/*      */     } 
/*      */     
/*  828 */     return false;
/*      */   }
/*      */   
/*      */   static void printAPuzzle(JFrame jf, String file) {
/*  832 */     if (!havePuzzle) {
/*  833 */       noPuzzle(jf, "Print");
/*      */     } else {
/*  835 */       new Print(jf, file);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   static boolean fileAvailable(String folder, String extension) {
/*  841 */     String[] s = (new File(folder)).list();
/*  842 */     if (s == null)
/*  843 */       return false;  int i;
/*  844 */     for (i = 0; i < s.length && (
/*  845 */       s[i].lastIndexOf("." + extension) == -1 || s[i].charAt(0) == '.'); i++);
/*      */     
/*  847 */     return (i != s.length);
/*      */   }
/*      */   
/*      */   static void savePuzzle(JFrame jf) {
/*  851 */     if (havePuzzle) {
/*      */       
/*  853 */       switch (Def.puzzleMode) {
/*      */         case 50:
/*  855 */           DoubletBuild.saveDoublet(Op.db[Op.DB.DbPuz.ordinal()]);
/*  856 */           puzzleSaved(jf, Op.msc[Op.MSC.DictionaryName.ordinal()], Op.db[Op.DB.DbPuz.ordinal()]);
/*      */           break;
/*      */         case 130:
/*  859 */           LetterdropBuild.saveLetterdrop(Op.ld[Op.LD.LdPuz.ordinal()]);
/*  860 */           puzzleSaved(jf, "letterdrop", Op.ld[Op.LD.LdPuz.ordinal()]);
/*      */           break;
/*      */       } 
/*      */     
/*      */     } else {
/*  865 */       noPuzzle(jf, "Save");
/*      */     } 
/*      */   }
/*      */   static String removeHTML(String str) {
/*  869 */     StringBuffer buf = new StringBuffer(str);
/*      */     
/*  871 */     for (int i = 0; i < buf.length(); i++) {
/*  872 */       if (buf.charAt(i) == '<')
/*      */         while (true) {
/*  874 */           buf.delete(i, i + 1);
/*  875 */           if (buf.charAt(i) == '>') {
/*  876 */             buf.delete(i, i + 1);
/*  877 */             i--; break;
/*      */           } 
/*      */         }  
/*  880 */     }  return new String(buf);
/*      */   }
/*      */ 
/*      */   
/*      */   static String tokenizeHTML(String text) {
/*  885 */     StringBuffer buf = new StringBuffer(text);
/*  886 */     int len = buf.length();
/*  887 */     for (int i = 1; i < len - 1; i++) {
/*  888 */       if (buf.charAt(i) == '<')
/*  889 */       { buf.insert(i, ' '); len++; i++; }
/*  890 */       else if (buf.charAt(i) == '>')
/*  891 */       { buf.insert(i + 1, ' '); len++; } 
/*  892 */     }  return new String(buf);
/*      */   }
/*      */   
/*  895 */   static int lastIdWidth = 0;
/*      */   static boolean R2LClue;
/*      */   static JDialog jdHelp;
/*      */   
/*      */   static void drawTheClues(Graphics2D g2, int[][] puzzleItem, float scale, String theFont, int theColor) {
/*  900 */     int i = 8, minColWidth = 1000;
/*      */     
/*  902 */     int newColumn = 0;
/*      */     
/*  904 */     float res = 0.0F;
/*      */ 
/*      */     
/*      */     int j;
/*      */     
/*  909 */     for (j = 8; j <= 19; j++) {
/*  910 */       if (puzzleItem[j][2] > 0 && puzzleItem[j][2] < minColWidth)
/*  911 */         minColWidth = puzzleItem[j][2]; 
/*  912 */     }  minColWidth = (int)(minColWidth * scale);
/*      */     
/*  914 */     R2LClue = (DictionaryMtce.dicHeader[DictionaryMtce.R2L_CLUE] == 1);
/*      */ 
/*      */     
/*  917 */     float fontSize = 200.0F;
/*  918 */     g2.setFont(new Font(theFont, 0, (int)fontSize));
/*  919 */     FontMetrics fm = g2.getFontMetrics();
/*  920 */     for (j = 0; j < NodeList.nodeListLength; j++) {
/*  921 */       String text = (NodeList.nodeList[j]).clue;
/*  922 */       StringTokenizer st = new StringTokenizer(text);
/*  923 */       while (st.hasMoreTokens()) {
/*  924 */         String word = st.nextToken();
/*  925 */         while (minColWidth < fm.stringWidth("12. " + word)) {
/*  926 */           fontSize = (float)(fontSize - 0.5D);
/*  927 */           g2.setFont(new Font(theFont, 0, (int)fontSize));
/*  928 */           fm = g2.getFontMetrics();
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  933 */     float lineSpace = fontSize;
/*  934 */     g2.setColor(new Color(theColor));
/*  935 */     int state = 0; while (true) {
/*  936 */       g2.setFont(new Font(theFont, 0, (int)fontSize));
/*      */       
/*  938 */       int left = (int)(puzzleItem[i][0] * scale);
/*  939 */       int top = (int)(puzzleItem[i][1] * scale);
/*  940 */       int width = (int)(puzzleItem[i][2] * scale);
/*  941 */       int height = (int)(puzzleItem[i][3] * scale);
/*      */ 
/*      */       
/*  944 */       float y = top + lineSpace, benchMark = y;
/*  945 */       g2.setFont(new Font(theFont, 1, (int)fontSize));
/*  946 */       fm = g2.getFontMetrics();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  951 */       String clueHead = (Def.puzzleMode == 50 || Def.puzzleMode == 10 || Def.puzzleMode == 134) ? Op.msc[Op.MSC.Langclues.ordinal()] : Op.msc[Op.MSC.Langacross.ordinal()];
/*  952 */       lastIdWidth = 0;
/*  953 */       y = renderOneString(g2, left, y, width, top + height, lineSpace, fm, clueHead, 0, 0, (state == 2));
/*  954 */       g2.setFont(new Font(theFont, 0, (int)fontSize));
/*      */       
/*  956 */       for (j = 0; j < NodeList.nodeListLength; j++) {
/*  957 */         if (Def.puzzleMode != 50 || (NodeList.nodeList[j]).id != 0) {
/*      */           int idWidth;
/*  959 */           String text = (NodeList.nodeList[j]).clue.trim();
/*  960 */           if (text.length() > 0 && 
/*  961 */             text.charAt(text.length() - 1) == ']')
/*  962 */             text = text.substring(0, text.indexOf('[')); 
/*  963 */           text = text.trim();
/*  964 */           text = tokenizeHTML(text);
/*  965 */           int id = (NodeList.nodeList[j]).id;
/*  966 */           if (Def.puzzleMode != 134 && (NodeList.nodeList[j]).direction == 1 && (NodeList.nodeList[j - 1]).direction == 0 && newColumn != 2) {
/*      */ 
/*      */ 
/*      */             
/*  970 */             idWidth = 0;
/*  971 */             j--;
/*  972 */             if ((top + height) - y >= 3.0F * lineSpace) {
/*  973 */               if (newColumn != 1)
/*  974 */                 y += lineSpace; 
/*  975 */               newColumn = 2;
/*  976 */               clueHead = Op.msc[Op.MSC.Langdown.ordinal()];
/*  977 */               g2.setFont(new Font(theFont, 1, (int)fontSize));
/*  978 */               fm = g2.getFontMetrics();
/*  979 */               lastIdWidth = 0;
/*  980 */               res = renderOneString(g2, left, y, width - idWidth, top + height, lineSpace, fm, clueHead, 0, 0, (state == 2));
/*      */             } else {
/*      */               
/*  983 */               res = 0.0F;
/*      */             } 
/*      */           } else {
/*  986 */             String idString; newColumn = 0;
/*  987 */             g2.setFont(new Font(theFont, 1, (int)fontSize));
/*  988 */             if (Def.puzzleMode == 10) {
/*  989 */               idString = "" + (char)(((id < 26) ? 65 : 97) + id - 1) + ".";
/*  990 */             } else if (R2LClue) {
/*  991 */               idString = " ." + id;
/*      */             } else {
/*  993 */               idString = "" + id + ".";
/*  994 */             }  if (Def.puzzleMode == 10) {
/*  995 */               idWidth = fm.stringWidth("A. ");
/*      */             } else {
/*  997 */               idWidth = fm.stringWidth((id < 10) ? "3. " : ((id > 99) ? "333. " : "33. "));
/*      */             } 
/*  999 */             lastIdWidth = idWidth;
/* 1000 */             if (state == 2)
/* 1001 */               if (R2LClue) {
/* 1002 */                 g2.drawString(idString, (left + width - idWidth), y);
/*      */               } else {
/* 1004 */                 g2.drawString(idString, left, y);
/* 1005 */               }   g2.setFont(new Font(theFont, 0, (int)fontSize));
/* 1006 */             if (text.length() < 1)
/* 1007 */               text = "No clue"; 
/* 1008 */             if (R2LClue) {
/* 1009 */               res = renderOneString(g2, left, y, width, top + height, lineSpace, fm, text, 0, 0, (state == 2));
/*      */             } else {
/* 1011 */               res = renderOneString(g2, left + idWidth, y, width - idWidth, top + height, lineSpace, fm, text, 0, 0, (state == 2));
/*      */             } 
/* 1013 */           }  if (res > 0.0F) y = res;
/*      */           
/* 1015 */           if (res <= 0.0F) {
/* 1016 */             newColumn = 1;
/*      */             
/* 1018 */             while (i++ != 19) {
/* 1019 */               left = (int)(puzzleItem[i][0] * scale);
/* 1020 */               top = (int)(puzzleItem[i][1] * scale);
/* 1021 */               width = (int)(puzzleItem[i][2] * scale);
/* 1022 */               height = (int)(puzzleItem[i][3] * scale);
/* 1023 */               if (width > 0) {
/* 1024 */                 y = top + lineSpace;
/* 1025 */                 if (y > benchMark) {
/* 1026 */                   float v; for (v = benchMark; v < y; v += lineSpace);
/* 1027 */                   y = v;
/*      */                 }
/* 1029 */                 else if (y < benchMark) {
/* 1030 */                   float v; for (v = benchMark; v >= y; v -= lineSpace);
/* 1031 */                   y = v += lineSpace;
/*      */                 } 
/* 1033 */                 if (res != 0.0F) {
/* 1034 */                   if (R2LClue) {
/* 1035 */                     y = res = renderOneString(g2, left, y, width, top + height, lineSpace, fm, text, (int)-res - 1, 1, (state == 2));
/*      */                   } else {
/* 1037 */                     y = res = renderOneString(g2, left + idWidth, y, width - idWidth, top + height, lineSpace, fm, text, (int)-res - 1, 0, (state == 2));
/* 1038 */                   }  newColumn = 0;
/*      */                 } 
/*      */                 break;
/*      */               } 
/*      */             } 
/*      */           } 
/* 1044 */           if (i > 19)
/*      */             break; 
/*      */         } 
/* 1047 */       }  switch (state) {
/*      */         case 0:
/* 1049 */           if (res <= 0.0F) {
/* 1050 */             lineSpace = fontSize = (float)(fontSize - 0.01D); break;
/*      */           } 
/* 1052 */           state = 1;
/* 1053 */           lineSpace = (float)(lineSpace + 0.01D);
/*      */           break;
/*      */         
/*      */         case 1:
/* 1057 */           if (res <= 0.0F) {
/* 1058 */             lineSpace = (float)(lineSpace - 0.01D); break;
/*      */           } 
/* 1060 */           state = 2; break;
/*      */         case 2:
/*      */           return;
/*      */       } 
/* 1064 */       i = 8;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   static void drawWordsearchClues(Graphics2D g2, int[][] puzzleItem, float scale, String theFont, int theColor) {
/* 1070 */     int i = 8;
/*      */     
/* 1072 */     float res = 0.0F;
/*      */ 
/*      */ 
/*      */     
/* 1076 */     R2LClue = (DictionaryMtce.dicHeader[DictionaryMtce.R2L_CLUE] == 1);
/* 1077 */     float fontSize = 200.0F;
/* 1078 */     g2.setColor(new Color(theColor)); int j;
/* 1079 */     for (j = 0; j < NodeList.nodeListLength; j++) {
/* 1080 */       g2.setFont(new Font(theFont, 0, (int)fontSize));
/* 1081 */       FontMetrics fm = g2.getFontMetrics();
/* 1082 */       while (fm.stringWidth((NodeList.nodeList[j]).word) > (int)(puzzleItem[i][2] * scale)) {
/* 1083 */         fontSize--;
/* 1084 */         g2.setFont(new Font(theFont, 0, (int)fontSize));
/* 1085 */         fm = g2.getFontMetrics();
/*      */       } 
/*      */     } 
/*      */     
/* 1089 */     float lineSpace = fontSize;
/* 1090 */     int state = 0; while (true) {
/* 1091 */       g2.setFont(new Font(theFont, 0, (int)fontSize));
/* 1092 */       FontMetrics fm = g2.getFontMetrics();
/*      */       
/* 1094 */       int left = (int)(puzzleItem[i][0] * scale);
/* 1095 */       int top = (int)(puzzleItem[i][1] * scale);
/* 1096 */       int width = (int)(puzzleItem[i][2] * scale);
/* 1097 */       int height = (int)(puzzleItem[i][3] * scale);
/*      */ 
/*      */       
/* 1100 */       float y = top + lineSpace, benchMark = y;
/* 1101 */       y = renderOneString(g2, left, y, width, top + height, lineSpace, fm, "<b>" + ((
/* 1102 */           Op.getBool(Op.WS.WsHint.ordinal(), Op.ws).booleanValue() && (Def.puzzleMode == 200 || Def.puzzleMode == 202)) ? Op.msc[Op.MSC.Langclues
/* 1103 */             .ordinal()] : Op.msc[Op.MSC.Langwords.ordinal()]), 0, 0, (state == 2));
/*      */       
/* 1105 */       for (j = 0; j < NodeList.nodeListLength; j++) {
/* 1106 */         String thisClue = (NodeList.nodeList[j]).clue;
/* 1107 */         if (thisClue.contains("*")) {
/* 1108 */           thisClue = thisClue.substring(0, thisClue.indexOf("*"));
/*      */         }
/*      */         
/* 1111 */         String text = (Op.getBool(Op.WS.WsHint.ordinal(), Op.ws).booleanValue() && (Def.puzzleMode == 200 || Def.puzzleMode == 202)) ? tokenizeHTML("<b>* </b>" + thisClue) : (NodeList.nodeList[j]).word;
/* 1112 */         if (Def.puzzleMode == 202) {
/* 1113 */           text = (NodeList.nodeList[j]).id + " " + (Op.getBool(Op.WS.WsHint.ordinal(), Op.ws).booleanValue() ? (NodeList.nodeList[j]).clue : text);
/*      */         }
/* 1115 */         g2.setFont(new Font(theFont, 1, (int)fontSize));
/* 1116 */         g2.setFont(new Font(theFont, 0, (int)fontSize));
/* 1117 */         res = renderOneString(g2, left, y, width, top + height, lineSpace, fm, text, 0, 0, (state == 2));
/* 1118 */         if (res > 0.0F) y = res;
/*      */         
/* 1120 */         if (res <= 0.0F)
/*      */         {
/* 1122 */           while (i++ != 19) {
/* 1123 */             left = (int)(puzzleItem[i][0] * scale);
/* 1124 */             top = (int)(puzzleItem[i][1] * scale);
/* 1125 */             width = (int)(puzzleItem[i][2] * scale);
/* 1126 */             height = (int)(puzzleItem[i][3] * scale);
/* 1127 */             if (width > 0) {
/* 1128 */               y = top + lineSpace;
/* 1129 */               if (y > benchMark) {
/* 1130 */                 float v; for (v = benchMark; v < y; v += lineSpace);
/* 1131 */                 y = v;
/*      */               }
/* 1133 */               else if (y < benchMark) {
/* 1134 */                 float v; for (v = benchMark; v >= y; v -= lineSpace);
/* 1135 */                 y = v += lineSpace;
/*      */               } 
/* 1137 */               if (res != 0.0F)
/* 1138 */                 y = res = renderOneString(g2, left, y, width, top + height, lineSpace, fm, text, (int)-res - 1, 0, (state == 2)); 
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         }
/* 1143 */         if (i > 19)
/*      */           break; 
/*      */       } 
/* 1146 */       switch (state) {
/*      */         case 0:
/* 1148 */           if (res <= 0.0F) {
/* 1149 */             lineSpace = fontSize = (float)(fontSize - 0.01D); break;
/*      */           } 
/* 1151 */           state = 1;
/* 1152 */           lineSpace += 3.0F;
/*      */           break;
/*      */         
/*      */         case 1:
/* 1156 */           if (res <= 0.0F) {
/* 1157 */             lineSpace = (float)(lineSpace - 0.01D); break;
/*      */           } 
/* 1159 */           state = 2; break;
/*      */         case 2:
/*      */           return;
/*      */       } 
/* 1163 */       i = 8;
/*      */     } 
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static Graphics2D adjustGraphicsContext(Graphics2D g2, String word) {
/* 1262 */     Font theFont = g2.getFont();
/* 1263 */     int theStyle = theFont.getStyle();
/* 1264 */     if (word.charAt(1) == 'b' || word.charAt(1) == 'B') {
/* 1265 */       theStyle |= 0x1;
/* 1266 */     } else if (word.charAt(1) == 'i' || word.charAt(1) == 'I') {
/* 1267 */       theStyle |= 0x2;
/* 1268 */     } else if (word.charAt(2) == 'b' || word.charAt(2) == 'B') {
/* 1269 */       theStyle &= 0x2;
/* 1270 */     } else if (word.charAt(2) == 'i' || word.charAt(2) == 'I') {
/* 1271 */       theStyle &= 0x1;
/* 1272 */     }  g2.setFont(theFont.deriveFont(theStyle));
/* 1273 */     return g2;
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
/*      */   static float renderOneString(Graphics2D g2, int left, float top, int width, int bottom, float fontSize, FontMetrics fm, String text, int numTokens, int align, boolean draw) {
/* 1292 */     Graphics2D lineg2 = (Graphics2D)g2.create();
/* 1293 */     text = tokenizeHTML(text);
/* 1294 */     float y = top;
/* 1295 */     int space = fm.stringWidth(" ");
/* 1296 */     String line = ""; int lineWidth = 0, count = -1;
/* 1297 */     StringTokenizer st = new StringTokenizer(text);
/* 1298 */     for (int i = 0; i < numTokens; ) { st.nextToken(); i++; }
/* 1299 */      while (st.hasMoreTokens()) {
/* 1300 */       String word = st.nextToken();
/* 1301 */       numTokens++;
/* 1302 */       if (word.startsWith("<")) {
/* 1303 */         g2 = adjustGraphicsContext(g2, word);
/*      */       } else {
/* 1305 */         fm = g2.getFontMetrics();
/* 1306 */         int wordWidth = fm.stringWidth(word);
/* 1307 */         if (lineWidth + ((lineWidth > 0) ? space : 0) + wordWidth > width - lastIdWidth) {
/* 1308 */           if (draw) {
/* 1309 */             renderOneLine(line, lineWidth, width, left, y, align, count, space, lineg2);
/*      */           }
/* 1311 */           if (lineWidth > 0 && (
/* 1312 */             y += fontSize) >= bottom)
/* 1313 */             return -numTokens; 
/* 1314 */           line = ""; lineWidth = 0; count = -1;
/*      */         } 
/* 1316 */         count++;
/* 1317 */         lineWidth += ((lineWidth > 0) ? space : 0) + wordWidth;
/*      */       } 
/* 1319 */       line = line + ((lineWidth > 0) ? " " : "") + word;
/*      */     } 
/*      */     
/* 1322 */     if (lineWidth > 0) {
/* 1323 */       if (draw) {
/* 1324 */         if (align == 3) align = 0; 
/* 1325 */         renderOneLine(line, lineWidth, width, left, y, align, count, space, lineg2);
/*      */       } 
/* 1327 */       if ((y += fontSize) >= bottom)
/* 1328 */         return 0.0F; 
/*      */     } 
/* 1330 */     return y;
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
/*      */   static void renderOneLine(String line, int lineWidth, int w, int x, float y, int align, int count, int space, Graphics2D lineg2) {
/* 1344 */     int excess = w - lineWidth;
/*      */     
/* 1346 */     if (R2LClue) {
/* 1347 */       x = x + w - lastIdWidth;
/*      */     }
/* 1349 */     line = tokenizeHTML(line);
/* 1350 */     if (!R2LClue)
/* 1351 */       switch (align) { case 1:
/* 1352 */           x += w - lineWidth; break;
/* 1353 */         case 2: x += (w - lineWidth) / 2;
/*      */           break; }
/*      */        
/* 1356 */     StringTokenizer st = new StringTokenizer(line);
/* 1357 */     while (st.hasMoreTokens()) {
/* 1358 */       int extra; String word = st.nextToken();
/* 1359 */       if (word.startsWith("<")) {
/* 1360 */         lineg2 = adjustGraphicsContext(lineg2, word);
/*      */         continue;
/*      */       } 
/* 1363 */       FontMetrics fm = lineg2.getFontMetrics();
/* 1364 */       int wordWidth = fm.stringWidth(word);
/*      */       
/* 1366 */       lineg2.drawString(word, (x - (R2LClue ? wordWidth : 0)), y);
/* 1367 */       if (count == 0)
/* 1368 */         continue;  if (align == 3) {
/* 1369 */         excess -= extra = excess / count--;
/*      */       } else {
/* 1371 */         extra = 0;
/* 1372 */       }  if (R2LClue) {
/* 1373 */         x -= wordWidth + space + extra; continue;
/*      */       } 
/* 1375 */       x += wordWidth + space + extra;
/*      */     } 
/*      */   }
/*      */   
/*      */   static void networkError(JFrame jf) {
/* 1380 */     JOptionPane.showMessageDialog(jf, "<html>This operation requires Internet access.<br>Please establish access and try again.", "No Network Connection.", 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static void noCandidate(JFrame jf) {
/* 1386 */     JOptionPane.showMessageDialog(jf, "<html><center>The key you typed has been rejected because it does not correspond<br>to any of the candidate values in the current cell.", "Input rejected", 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static void insufficientWords(JFrame jf) {
/* 1392 */     JOptionPane.showMessageDialog(jf, "<html>Not enough words to build this puzzle.<br>Please refer to Help for further information.");
/*      */   }
/*      */   
/*      */   static void puzzleSaved(JFrame jf, String folder, String file) {
/* 1396 */     JOptionPane.showMessageDialog(jf, "<html>The puzzle has been saved in...<br>Folder: " + folder + "<br>File: " + file);
/*      */   }
/*      */   static void puzzleSaved(JDialog jdlg, String folder, String file) {
/* 1399 */     JOptionPane.showMessageDialog(jdlg, "<html>The puzzle has been saved in...<br>Folder: " + folder + "<br>File: " + file);
/*      */   }
/*      */   
/*      */   static void reportSaved(JFrame jf, String folder) {
/* 1403 */     JOptionPane.showMessageDialog(jf, "<html>The report has been saved in...<br>Folder: " + folder + "<br>File: report.txt");
/*      */   }
/*      */   
/*      */   static void cantBuild(JFrame jf) {
/* 1407 */     JOptionPane.showMessageDialog(jf, "Not possible to build this puzzle.");
/*      */   }
/*      */   
/*      */   static void pending(JFrame jf) {
/* 1411 */     JOptionPane.showMessageDialog(jf, "<html>This function is not yet implemented.<br>It will be included in future versions.");
/*      */   }
/*      */   
/*      */   static void noPuzzle(JFrame jf, String operation) {
/* 1415 */     JOptionPane.showMessageDialog(jf, "There is no puzzle to " + operation + ".");
/*      */   }
/*      */   
/*      */   static void noPuzzleName(JFrame jf) {
/* 1419 */     JOptionPane.showMessageDialog(jf, "<html>You haven't entered a name for the puzzle.<br>Please use the <font color=880000>SaveAs</font> button.");
/*      */   }
/*      */   
/*      */   static void noDictionary(JFrame jf) {
/* 1423 */     JOptionPane.showMessageDialog(jf, "<html>Before you can do this, you must select a dictionary.");
/*      */   }
/*      */   
/*      */   static void interrupted(JFrame jf) {
/* 1427 */     JOptionPane.showMessageDialog(jf, "Puzzle building has been interrupted.");
/*      */   }
/*      */   
/*      */   static void noName(JFrame jf) {
/* 1431 */     JOptionPane.showMessageDialog(jf, "<html>Please provide a file name for your new puzzle.<br>Use <font color=880000>Start a New Puzzle");
/*      */   }
/*      */   
/*      */   static void noReveal(JFrame jf) {
/* 1435 */     JOptionPane.showMessageDialog(jf, "The Reveal function has been disabled on this puzzle.");
/*      */   }
/*      */   
/*      */   static void invalidPuzzle(JFrame jf) {
/* 1439 */     JOptionPane.showMessageDialog(jf, "The current puzzle is invalid.");
/*      */   }
/*      */   
/*      */   static void congratulations(JFrame jf) {
/* 1443 */     JOptionPane.showMessageDialog(jf, "Your solution is correct.", "CONGRATULATIONS", 1);
/*      */   }
/*      */   
/*      */   static void copyFile(String src, String dest) {
/* 1447 */     byte[] byt = new byte[1024];
/*      */ 
/*      */     
/*      */     try {
/* 1451 */       DataInputStream dataIn = new DataInputStream(new FileInputStream(src));
/* 1452 */       DataOutputStream dataOut = new DataOutputStream(new FileOutputStream(dest));
/* 1453 */       while (dataIn.available() > 0)
/* 1454 */         dataOut.write(byt, 0, dataIn.read(byt, 0, 1024)); 
/* 1455 */       dataIn.close();
/* 1456 */       dataOut.close();
/*      */     }
/* 1458 */     catch (IOException exc) {}
/*      */   }
/*      */   
/*      */   static void launchWebApp(JFrame theFrame, String type) {
/* 1462 */     CrosswordExpress.networkStatus("access.txt");
/* 1463 */     if (!CrosswordExpress.networkOn) {
/* 1464 */       networkError(theFrame);
/*      */     } else {
/* 1466 */       browserLaunch.openURL(theFrame, "http://www.crauswords.com/webapp/" + type + "/" + type + ".html");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void exportWebApp(JFrame theFrame, String type) {
/* 1477 */     CrosswordExpress.networkStatus("access.txt");
/* 1478 */     if (!CrosswordExpress.networkOn) {
/* 1479 */       networkError(theFrame);
/*      */       
/*      */       return;
/*      */     } 
/* 1483 */     String path = System.getProperty("user.home") + "/Desktop/Web-App";
/* 1484 */     deleteFolder(new File(path));
/* 1485 */     (new File(path)).mkdir();
/*      */     
/* 1487 */     String[] src = new String[3];
/* 1488 */     src[0] = "http://www.crauswords.com/webapp/" + type + "/" + type + ".html";
/* 1489 */     src[1] = "http://www.crauswords.com/webapp/" + type + "/" + type + ".js";
/* 1490 */     src[2] = "http://www.crauswords.com/webapp/" + type + "/" + "puzzle." + type;
/*      */     
/* 1492 */     String[] dest = new String[3];
/* 1493 */     dest[0] = path + "/" + type + ".html";
/* 1494 */     dest[1] = path + "/" + type + ".js";
/* 1495 */     dest[2] = path + "/" + "puzzle." + type;
/*      */     
/* 1497 */     for (int i = 0; i < 3; i++) {
/*      */       
/* 1499 */       try { URL theURL = new URL(src[i]);
/* 1500 */         HttpURLConnection httpConn = (HttpURLConnection)theURL.openConnection();
/* 1501 */         InputStream in = httpConn.getInputStream();
/* 1502 */         OutputStream out = new FileOutputStream(dest[i]);
/* 1503 */         byte[] buf = new byte[10000]; int bytesRead;
/* 1504 */         while ((bytesRead = in.read(buf)) != -1)
/* 1505 */           out.write(buf, 0, bytesRead); 
/* 1506 */         out.close();
/* 1507 */         in.close();
/* 1508 */         httpConn.setConnectTimeout(2000); }
/*      */       
/* 1510 */       catch (MalformedURLException ex) {  }
/* 1511 */       catch (IOException ex) {}
/*      */     } 
/* 1513 */     if ((new File(path + "/" + type + ".js")).exists()) {
/* 1514 */       JOptionPane.showMessageDialog(theFrame, "<html>The Web-App files have been saved<br>on the Desktop in folder Web-App");
/*      */     }
/*      */   }
/*      */   
/*      */   static void deleteFolder(File file) {
/* 1519 */     if (!file.exists())
/*      */       return; 
/* 1521 */     if (file.isDirectory())
/* 1522 */       for (File f : file.listFiles())
/* 1523 */         deleteFolder(f);  
/* 1524 */     file.delete();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void stdPrintOptions(JFrame jf, String type, final String[] opts, final String[] colorLabel, final int[] colorInt, final String[] fontLabel, final int[] fontInt, final String[] checkLabel, final int[] checkInt) {
/* 1532 */     final JComboBox[] jcbb = new JComboBox[fontLabel.length];
/* 1533 */     JLabel[] jl = new JLabel[fontLabel.length];
/* 1534 */     final JButton[] jb = new JButton[colorLabel.length];
/* 1535 */     final JCheckBox[] jcb = new JCheckBox[checkLabel.length];
/*      */ 
/*      */     
/* 1538 */     int colorPanelTop = 10;
/* 1539 */     int colorPanelH = 5 + 28 * (colorLabel.length / 2 + colorLabel.length % 2);
/* 1540 */     int checkPanelTop = colorPanelTop + colorPanelH + 5;
/* 1541 */     int checkPanelH = 5 + 25 * checkLabel.length;
/* 1542 */     int fontPanelTop = checkPanelTop + checkPanelH + 5;
/* 1543 */     int fontPanelH = (fontLabel == null) ? 0 : (11 + 50 * fontLabel.length);
/* 1544 */     int buttonTop = fontPanelTop + fontPanelH + 5;
/*      */     
/* 1546 */     final JDialog jdlgPrintOptions = new JDialog(jf, type, true);
/* 1547 */     jdlgPrintOptions.setSize(415, buttonTop + 100);
/* 1548 */     jdlgPrintOptions.setResizable(false);
/* 1549 */     jdlgPrintOptions.setLayout((LayoutManager)null);
/* 1550 */     jdlgPrintOptions.setLocation(jf.getX(), jf.getY());
/*      */     
/* 1552 */     jdlgPrintOptions
/* 1553 */       .addWindowListener(new WindowAdapter() {
/*      */           public void windowClosing(WindowEvent we) {
/* 1555 */             Methods.closeHelp();
/*      */           }
/*      */         });
/*      */     
/* 1559 */     closeHelp();
/*      */ 
/*      */     
/* 1562 */     JPanel jpSC = new JPanel();
/* 1563 */     jpSC.setLayout((LayoutManager)null);
/* 1564 */     jpSC.setSize(400, colorPanelH);
/* 1565 */     jpSC.setLocation(10, colorPanelTop);
/* 1566 */     jpSC.setOpaque(true);
/* 1567 */     jpSC.setBorder(BorderFactory.createEtchedBorder());
/* 1568 */     jdlgPrintOptions.add(jpSC); int j;
/* 1569 */     for (j = 0; j < colorLabel.length; j++) {
/* 1570 */       jb[j] = new JButton("" + (j + 1));
/* 1571 */       jb[j].setSize(55, 20);
/* 1572 */       jb[j].setLocation(10 + 200 * j % 2, 6 + 28 * j / 2);
/* 1573 */       jb[j].setBorder(BorderFactory.createLineBorder(new Color(136), 2));
/*      */       
/* 1575 */       JLabel jl1 = new JLabel(colorLabel[j]);
/* 1576 */       jl1.setForeground(Def.COLOR_LABEL);
/* 1577 */       jl1.setSize(140, 20);
/* 1578 */       jl1.setLocation(75 + 200 * j % 2, 6 + 28 * j / 2);
/* 1579 */       jl1.setHorizontalAlignment(2);
/* 1580 */       jpSC.add(jl1);
/*      */       
/* 1582 */       jb[j].setActionCommand("" + j);
/* 1583 */       jb[j].setBackground(new Color(Op.getColorInt(colorInt[j], opts)));
/* 1584 */       jpSC.add(jb[j]);
/* 1585 */       jb[j]
/* 1586 */         .addActionListener(e -> {
/*      */             for (int i1 = 0; i1 < paramArrayOfString1.length; i1++) {
/*      */               if (e.getActionCommand().equals("" + i1)) {
/*      */                 Color color = JColorChooser.showDialog(paramJDialog, paramArrayOfString1[i1], new Color(Op.getColorInt(paramArrayOfint[i1], paramArrayOfString2)));
/*      */                 
/*      */                 if (color != null) {
/*      */                   paramArrayOfJButton[i1].setBackground(color);
/*      */                 }
/*      */               } 
/*      */             } 
/*      */           });
/*      */     } 
/*      */     
/* 1599 */     if (checkLabel[0].length() > 0) {
/* 1600 */       JPanel jpCB = new JPanel();
/* 1601 */       jpCB.setLayout((LayoutManager)null);
/* 1602 */       jpCB.setSize(400, checkPanelH);
/* 1603 */       jpCB.setLocation(10, checkPanelTop);
/* 1604 */       jpCB.setOpaque(true);
/* 1605 */       jpCB.setBorder(BorderFactory.createEtchedBorder());
/* 1606 */       jdlgPrintOptions.add(jpCB);
/* 1607 */       for (j = 0; j < checkLabel.length; j++) {
/* 1608 */         jcb[j] = new JCheckBox(checkLabel[j].substring(1), Op.getBool(checkInt[j], opts).booleanValue());
/* 1609 */         jcb[j].setForeground(Def.COLOR_LABEL);
/* 1610 */         jcb[j].setOpaque(false);
/* 1611 */         jcb[j].setSize(300, 20);
/* 1612 */         jcb[j].setLocation(25, 5 + 25 * j);
/* 1613 */         jpCB.add(jcb[j]);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1618 */     if (fontLabel[0].length() > 0) {
/* 1619 */       String[] fontList = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
/* 1620 */       JPanel jpSF = new JPanel();
/* 1621 */       jpSF.setLayout((LayoutManager)null);
/* 1622 */       jpSF.setSize(400, fontPanelH);
/* 1623 */       jpSF.setLocation(10, fontPanelTop);
/* 1624 */       jpSF.setOpaque(true);
/* 1625 */       jpSF.setBorder(BorderFactory.createEtchedBorder());
/* 1626 */       jdlgPrintOptions.add(jpSF);
/*      */       
/* 1628 */       for (j = 0; j < fontLabel.length; j++) {
/* 1629 */         JLabel jl2 = new JLabel(fontLabel[j]);
/* 1630 */         jl2.setForeground(Def.COLOR_LABEL);
/* 1631 */         jl2.setSize(180, 16);
/* 1632 */         jl2.setLocation(10, 3 + j * 50);
/* 1633 */         jl2.setHorizontalAlignment(2);
/* 1634 */         jpSF.add(jl2);
/*      */         
/* 1636 */         jcbb[j] = new JComboBox();
/* 1637 */         jcbb[j].setBackground(Def.COLOR_BUTTONBG);
/* 1638 */         jcbb[j].setSize(160, 26);
/* 1639 */         jcbb[j].setLocation(10, 20 + j * 50);
/* 1640 */         for (int i = 0; i < fontList.length; i++)
/* 1641 */           jcbb[j].addItem(fontList[i]); 
/* 1642 */         jcbb[j].setSelectedItem(opts[fontInt[j]]);
/*      */         
/* 1644 */         jl[j] = new JLabel("A B C 1 2 3");
/* 1645 */         jl[j].setSize(210, 40);
/* 1646 */         jl[j].setLocation(180, 7 + j * 50);
/* 1647 */         jl[j].setHorizontalAlignment(0);
/* 1648 */         jpSF.add(jl[j]);
/*      */         
/* 1650 */         jl[j].setBorder(BorderFactory.createEtchedBorder());
/* 1651 */         jl[j].setOpaque(true);
/* 1652 */         jl[j].setBackground(new Color(16777215));
/* 1653 */         jl[j].setFont(new Font(jcbb[j].getSelectedItem().toString(), 0, 26));
/* 1654 */         jcbb[j]
/* 1655 */           .addActionListener(ce -> {
/*      */               for (int k = 0; k < paramArrayOfString.length; k++) {
/*      */                 paramArrayOfJLabel[k].setFont(new Font(paramArrayOfJComboBox[k].getSelectedItem().toString(), 0, 26));
/*      */               }
/*      */             });
/* 1660 */         jpSF.add(jcbb[j]);
/*      */       } 
/*      */     } 
/*      */     
/* 1664 */     Action doOK = new AbstractAction("OK") {
/*      */         public void actionPerformed(ActionEvent e) {
/*      */           int i1;
/* 1667 */           for (i1 = 0; i1 < colorLabel.length; i1++)
/* 1668 */             Op.setColorInt(colorInt[i1], jb[i1].getBackground().getRGB() & 0xFFFFFF, opts); 
/* 1669 */           if (checkLabel[0].length() > 0)
/* 1670 */             for (i1 = 0; i1 < checkLabel.length; i1++)
/* 1671 */               Op.setBool(checkInt[i1], Boolean.valueOf(jcb[i1].isSelected()), opts);  
/* 1672 */           if (fontLabel[0].length() > 0)
/* 1673 */             for (i1 = 0; i1 < fontLabel.length; i1++)
/* 1674 */               opts[fontInt[i1]] = jcbb[i1].getSelectedItem().toString().trim();  
/* 1675 */           jdlgPrintOptions.dispose();
/* 1676 */           Methods.closeHelp();
/*      */         }
/*      */       };
/* 1679 */     JButton jbOK = newButton("doOK", doOK, 0, 80, buttonTop, 90, 26);
/* 1680 */     jdlgPrintOptions.add(jbOK);
/*      */     
/* 1682 */     Action doCancel = new AbstractAction("Cancel") {
/*      */         public void actionPerformed(ActionEvent e) {
/* 1684 */           jdlgPrintOptions.dispose();
/* 1685 */           Methods.closeHelp();
/*      */         }
/*      */       };
/* 1688 */     JButton jbCancel = newButton("doCancel", doCancel, 0, 80, buttonTop + 35, 90, 26);
/* 1689 */     jdlgPrintOptions.add(jbCancel);
/*      */     
/* 1691 */     Action doHelp = new AbstractAction("<html><font size=6 color=BB0000 face=Serif>Help ", new ImageIcon("graphics/help.png")) {
/*      */         public void actionPerformed(ActionEvent e) {
/* 1693 */           Methods.cweHelp(null, jdlgPrintOptions, "Display and Print Options", Methods.dandpOptions);
/*      */         }
/*      */       };
/* 1696 */     JButton jbHelp = newButton("doHelp", doHelp, 72, 180, buttonTop, 160, 61);
/* 1697 */     jdlgPrintOptions.add(jbHelp);
/*      */     
/* 1699 */     setDialogSize(jdlgPrintOptions, 420, buttonTop + 71);
/*      */   }
/*      */   
/*      */   static void infoPanel(JLabel jl1, JLabel jl2, String title, String info, int pWidth) {
/* 1703 */     jl1.setFont(new Font("SansSerif", 1, 18));
/* 1704 */     jl1.setLocation(0, 0);
/* 1705 */     jl1.setHorizontalAlignment(0);
/* 1706 */     jl1.setForeground(new Color(21845));
/* 1707 */     jl1.setBackground(new Color(52428));
/* 1708 */     jl1.setOpaque(true);
/* 1709 */     jl1.setBorder(BorderFactory.createLineBorder(new Color(21845), 3));
/* 1710 */     jl1.setText(title);
/* 1711 */     int wide = jl1.getGraphics().getFontMetrics().stringWidth(title) + 30;
/* 1712 */     jl1.setSize(wide, 37);
/*      */     
/* 1714 */     jl2.setFont(new Font("Arial", 1, 14));
/* 1715 */     jl2.setLocation(wide, 0);
/* 1716 */     jl2.setHorizontalAlignment(0);
/* 1717 */     jl2.setSize(pWidth - wide, 37);
/* 1718 */     jl2.setForeground(Color.white);
/* 1719 */     jl2.setBackground(new Color(21845));
/* 1720 */     jl2.setOpaque(true);
/* 1721 */     jl2.setText(info);
/*      */   }
/*      */ 
/*      */   
/*      */   static void buildProgress(JFrame target, String puzzleName) {
/* 1726 */     JLabel jl = new JLabel(); target.add(jl);
/* 1727 */     jl.setLocation((target.getWidth() - 320) / 2 + ((Def.puzzleMode == 4) ? 30 : 0), (target.getHeight() - 37) / 2);
/* 1728 */     jl.setFont(new Font("SansSerif", 1, 18));
/* 1729 */     jl.setSize(320, 37);
/* 1730 */     jl.setHorizontalAlignment(0);
/* 1731 */     jl.setForeground(new Color(21845));
/* 1732 */     jl.setBackground(new Color(52428));
/* 1733 */     jl.setOpaque(true);
/* 1734 */     jl.setBorder(BorderFactory.createLineBorder(new Color(21845), 3));
/* 1735 */     jl.setText("Building " + puzzleName);
/*      */   }
/*      */   
/*      */   static boolean HelpActive = false;
/*      */   JPanel hp;
/*      */   static boolean useColor;
/*      */   
/*      */   static void cweHelp(JFrame jfm, JDialog jdlg, String title, String helpText) {
/*      */     int wide, high, x, y;
/* 1744 */     String helpHeader = "<html lang='Ne'><head><style><meta charset='UTF-8'>span.m {font-family:Arial,sans-serif; font-size:26pt; font-weight: bold; color: #880000; text-align: justify}span.s {font-family:Arial,sans-serif; font-size:18pt; font-weight: bold; color: #0000CC; text-align: justify}span {font-family:Verdana,sans-serif; font-size:16pt;  color:#006666; font-weight:bold}li {font:16pt arial; color:#000000; text-align:justify}li.d {font:16pt arial; color:#000000; text-align:left}div {margin-top: -10px; font:16pt arial; color:#000000; text-align:justify}span.d  {font:16pt arial; color:#000000; text-align:left}ul {margin-top: -10px}.imgborder { border: 3px solid #008800; margin: 0px 10px 0px 0px;}</style></head><body><br/>";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1758 */     if (helpText.length() < 600) {
/* 1759 */       wide = 600; high = 300;
/* 1760 */     } else if (helpText.length() < 2000) {
/* 1761 */       wide = 600; high = 800;
/*      */     } else {
/* 1763 */       wide = 900; high = 1000;
/*      */     } 
/* 1765 */     if (high > scrH - 90) high = scrH - 90;
/*      */     
/* 1767 */     closeHelp();
/* 1768 */     HelpActive = true;
/*      */     
/* 1770 */     if (jdlg != null) {
/* 1771 */       jdHelp = new JDialog(jdlg);
/* 1772 */       x = jdlg.getX() + jdlg.getWidth() + 10;
/* 1773 */       y = jdlg.getY();
/*      */     } else {
/*      */       
/* 1776 */       jdHelp = new JDialog(jfm);
/* 1777 */       x = jfm.getX() + jfm.getWidth() + 10;
/* 1778 */       y = jfm.getY();
/*      */     } 
/* 1780 */     if (x + wide > scrW) x = scrW - wide;
/*      */     
/* 1782 */     jdHelp.setTitle("Crossword Express Help");
/* 1783 */     jdHelp.setVisible(true);
/* 1784 */     jdHelp.setLocation(x, y);
/* 1785 */     jdHelp.setDefaultCloseOperation(0);
/* 1786 */     jdHelp.setLayout((LayoutManager)null);
/* 1787 */     jdHelp.setSize(wide, high + (jdHelp.getInsets()).top);
/* 1788 */     jdHelp.setResizable(false);
/*      */     
/* 1790 */     JPanel jp = new JPanel();
/* 1791 */     jp.setSize(wide, 90);
/* 1792 */     jp.setLocation(0, 0);
/* 1793 */     jp.setLayout((LayoutManager)null);
/* 1794 */     jp.setBackground(new Color(11184793));
/* 1795 */     JLabel jl = new JLabel("<html><br/><font face=Serif color=AA0000 size=6><center>" + title + "</font>", new ImageIcon("graphics/cweicon.png"), 0);
/*      */     
/* 1797 */     jl.setLocation(0, 0);
/* 1798 */     jl.setSize(wide, 90);
/* 1799 */     jp.add(jl);
/* 1800 */     jdHelp.add(jp);
/*      */     
/* 1802 */     JEditorPane jta = new JEditorPane();
/* 1803 */     jta.setEditorKit(new HTMLEditorKit());
/* 1804 */     jta.setContentType("text/html");
/* 1805 */     jta.setBackground(Def.COLOR_HELPBG);
/* 1806 */     jta.setText(helpHeader + helpText);
/* 1807 */     jta.setCaretPosition(0);
/* 1808 */     jta.setEditable(false);
/* 1809 */     jta.setMargin(new Insets(0, 15, 15, 15));
/*      */     
/* 1811 */     JScrollPane jsp = new JScrollPane(jta);
/* 1812 */     jsp.setSize(wide, high - 90);
/* 1813 */     jsp.setLocation(0, 90);
/* 1814 */     jsp.setVerticalScrollBarPolicy(20);
/*      */     
/* 1816 */     jdHelp.getContentPane().add(jsp);
/*      */     
/* 1818 */     jdHelp
/* 1819 */       .addWindowListener(new WindowAdapter() {
/*      */           public void windowClosing(WindowEvent we) {
/* 1821 */             Methods.HelpActive = false;
/* 1822 */             Methods.jdHelp.dispose();
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */   
/*      */   static void closeHelp() {
/* 1829 */     if (HelpActive) {
/* 1830 */       HelpActive = false;
/* 1831 */       jdHelp.dispose();
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void clearHintData() {
/* 1836 */     SudokuBuild.linkDatIndex = 0;
/* 1837 */     SudokuBuild.hintUnitIndex = SudokuBuild.hintCellIndex = SudokuBuild.weakLinkIndex = SudokuBuild.candIndex = 0;
/* 1838 */     SudokuBuild.hintIndexb = SudokuBuild.hintIndexg = SudokuBuild.hintIndexr = 0;
/* 1839 */     KsudokuBuild.thisCage = -1;
/*      */   }
/*      */   
/*      */   public static void frameResize(JFrame jf, int oldw, int oldh, int frameW, int frameH) {
/* 1843 */     int diff = frameH - frameW;
/*      */     
/* 1845 */     int neww = jf.getWidth();
/* 1846 */     int newh = jf.getHeight();
/* 1847 */     if (neww == oldw && newh != oldh) {
/* 1848 */       newh = oldh;
/*      */     }
/* 1850 */     else if (newh == oldh && neww != oldw) {
/* 1851 */       neww = oldw;
/* 1852 */     }  if (newh < neww + diff) {
/* 1853 */       newh = neww + diff;
/*      */     } else {
/* 1855 */       neww = newh - diff;
/* 1856 */     }  if (neww < frameW) neww = frameW; 
/* 1857 */     if (newh < frameH) newh = frameH; 
/* 1858 */     if (newh > scrH - 100) {
/* 1859 */       newh = scrH - 100;
/* 1860 */       neww = newh - diff;
/*      */     } 
/* 1862 */     if (Def.isMac) {
/* 1863 */       jf.setSize(neww, newh);
/*      */     } else {
/* 1865 */       jf.setSize(jf.getWidth(), jf.getHeight());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static void printKdpDialog(JFrame jf, final int puzzleType, int puzzlesPerPage) {
/* 1872 */     itemsPP = puzzlesPerPage;
/*      */     
/* 1874 */     final JDialog jdlgKdpExport = new JDialog(jf, "Export a KDP book", true);
/* 1875 */     jdlgKdpExport.setSize(370, 385);
/* 1876 */     jdlgKdpExport.setResizable(false);
/* 1877 */     jdlgKdpExport.setLayout((LayoutManager)null);
/* 1878 */     jdlgKdpExport.setLocation(jf.getX(), jf.getY());
/*      */     
/* 1880 */     jdlgKdpExport
/* 1881 */       .addWindowListener(new WindowAdapter() {
/*      */           public void windowClosing(WindowEvent we) {
/* 1883 */             Methods.closeHelp();
/*      */           }
/*      */         });
/* 1886 */     closeHelp();
/*      */     
/* 1888 */     JLabel jlPages = new JLabel("Number of Pages to Print:");
/* 1889 */     jlPages.setForeground(Def.COLOR_LABEL);
/* 1890 */     jlPages.setSize(190, 20);
/* 1891 */     jlPages.setLocation(30, 20);
/* 1892 */     jlPages.setHorizontalAlignment(2);
/* 1893 */     jdlgKdpExport.add(jlPages);
/*      */     
/* 1895 */     final JTextField jtfPages = new JTextField("50", 4);
/* 1896 */     jtfPages.setSize(40, 20);
/* 1897 */     jtfPages.setLocation(210, 20);
/* 1898 */     jtfPages.selectAll();
/* 1899 */     jtfPages.setHorizontalAlignment(2);
/* 1900 */     jdlgKdpExport.add(jtfPages);
/*      */     
/* 1902 */     final JCheckBox jcbColor = new JCheckBox("Print Puzzle Book with Color", false);
/* 1903 */     jcbColor.setForeground(Def.COLOR_LABEL);
/* 1904 */     jcbColor.setOpaque(false);
/* 1905 */     jcbColor.setSize(225, 20);
/* 1906 */     jcbColor.setLocation(60, 50);
/* 1907 */     jdlgKdpExport.add(jcbColor);
/*      */     
/* 1909 */     final JRadioButton[] jrbKDP = new JRadioButton[2];
/* 1910 */     ButtonGroup bgKDP = new ButtonGroup();
/*      */     
/* 1912 */     jrbKDP[0] = new JRadioButton();
/* 1913 */     jrbKDP[0].setText("Export KDP Puzzles");
/* 1914 */     jrbKDP[0].setForeground(Def.COLOR_LABEL);
/* 1915 */     jrbKDP[0].setOpaque(false);
/* 1916 */     jrbKDP[0].setSize(250, 20);
/* 1917 */     jrbKDP[0].setLocation(60, 80);
/* 1918 */     jdlgKdpExport.add(jrbKDP[0]);
/* 1919 */     bgKDP.add(jrbKDP[0]);
/*      */     
/* 1921 */     jrbKDP[1] = new JRadioButton();
/* 1922 */     jrbKDP[1].setText("Export KDP Solutions");
/* 1923 */     jrbKDP[1].setForeground(Def.COLOR_LABEL);
/* 1924 */     jrbKDP[1].setOpaque(false);
/* 1925 */     jrbKDP[1].setSize(250, 20);
/* 1926 */     jrbKDP[1].setLocation(60, 110);
/* 1927 */     jdlgKdpExport.add(jrbKDP[1]);
/* 1928 */     bgKDP.add(jrbKDP[1]);
/*      */     
/* 1930 */     Action doExport = new AbstractAction("Export") {
/*      */         public void actionPerformed(ActionEvent e) {
/* 1932 */           if (jrbKDP[0].isSelected()) {
/* 1933 */             Methods.useColor = jcbColor.isSelected();
/* 1934 */             Methods.printProcessKpuz(Integer.parseInt(jtfPages.getText()) * Methods.itemsPP, puzzleType);
/*      */           }
/* 1936 */           else if (jrbKDP[1].isSelected()) {
/* 1937 */             Methods.useColor = jcbColor.isSelected();
/* 1938 */             Methods.printProcessKsol(Integer.parseInt(jtfPages.getText()) * Methods.itemsPP, puzzleType);
/*      */           } 
/*      */         }
/*      */       };
/* 1942 */     JButton jbExport = newButton("doExport", doExport, 79, 10, 140, 100, 26);
/* 1943 */     jdlgKdpExport.add(jbExport);
/*      */     
/* 1945 */     Action doCancel = new AbstractAction("Cancel") {
/*      */         public void actionPerformed(ActionEvent e) {
/* 1947 */           Methods.clickedOK = false;
/* 1948 */           jdlgKdpExport.dispose();
/* 1949 */           Methods.closeHelp();
/*      */         }
/*      */       };
/* 1952 */     JButton jbCancel = newButton("doCancel", doCancel, 67, 10, 175, 100, 26);
/* 1953 */     jdlgKdpExport.add(jbCancel);
/*      */     
/* 1955 */     Action doHelp = new AbstractAction("<html><font size=6 color=BB0000 face=Serif>Help ", new ImageIcon("graphics/help.png")) {
/*      */         public void actionPerformed(ActionEvent e) {
/* 1957 */           Methods.cweHelp(null, jdlgKdpExport, "Puzzle Description", Methods.printKDPbook);
/*      */         }
/*      */       };
/* 1960 */     JButton jbHelp = newButton("doHelp", doHelp, 72, 120, 140, 160, 61);
/* 1961 */     jdlgKdpExport.add(jbHelp);
/*      */     
/* 1963 */     jdlgKdpExport.getRootPane().setDefaultButton(jbExport);
/* 1964 */     setDialogSize(jdlgKdpExport, 290, 211);
/*      */   }
/*      */   
/*      */   static void drawKdpPuz(Graphics2D g2, int left, int top, int width, int page, int puzType, String title) {
/* 1968 */     int rows = 3, columns = 2;
/*      */ 
/*      */ 
/*      */     
/* 1972 */     RenderingHints rh = g2.getRenderingHints();
/* 1973 */     rh.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
/* 1974 */     rh.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
/* 1975 */     g2.setRenderingHints(rh);
/*      */     
/* 1977 */     int v = width * 10 / (11 * columns + 1);
/* 1978 */     int g = v / 10;
/*      */     
/* 1980 */     g2.setColor(new Color(16777215));
/* 1981 */     g2.fillRect(left, top, g + columns * (v + g), 2 * g + rows * (v + 2 * g));
/* 1982 */     g2.setColor(Def.COLOR_BLACK);
/* 1983 */     g2.setStroke(new BasicStroke(1.0F, 2, 0));
/* 1984 */     g2.drawRect(left, top, g + columns * (v + g), 2 * g + rows * (v + 2 * g));
/* 1985 */     g2.fillRect(left, top, g + columns * (v + g), 5 * g / 3);
/*      */     
/* 1987 */     g2.setFont(new Font("SansSerif", 1, g));
/* 1988 */     FontMetrics fm = g2.getFontMetrics();
/* 1989 */     String st = title + " " + (page + 1);
/* 1990 */     int w = fm.stringWidth(st);
/* 1991 */     g2.setColor(Def.COLOR_WHITE);
/* 1992 */     g2.drawString(st, left + (g + columns * (v + g) - w) / 2, top + (5 * g / 3 + fm.getAscent()) / 2);
/*      */     
/* 1994 */     Def.dispGuideDigits = Boolean.valueOf(false);
/* 1995 */     Def.dispWithColor = Boolean.valueOf(useColor);
/* 1996 */     for (int i = 0; i < itemsPP; i++) {
/* 1997 */       int x = left + g + i % columns * (v + g);
/* 1998 */       int y = top + 3 * g + i / columns * (v + 2 * g);
/* 1999 */       g2.setColor(Def.COLOR_BLACK);
/* 2000 */       g2.setFont(new Font("SansSerif", 1, 3 * g / 4));
/* 2001 */       switch (puzType) { case 20:
/* 2002 */           AkariBuild.printKDPPuz(g2, x, y, v, g, "" + (page * itemsPP + i + 1)); break;
/* 2003 */         case 40: DominoBuild.printKDPPuz(g2, x, y, v, g, "" + (page * itemsPP + i + 1)); break;
/* 2004 */         case 70: FillominoBuild.printKDPPuz(g2, x, y, v, g, "" + (page * itemsPP + i + 1)); break;
/* 2005 */         case 80: FutoshikiBuild.printKDPPuz(g2, x, y, v, g, "" + (page * itemsPP + i + 1)); break;
/* 2006 */         case 90: GokigenBuild.printKDPPuz(g2, x, y, v, g, "" + (page * itemsPP + i + 1)); break;
/* 2007 */         case 100: KakuroBuild.printKDPPuz(g2, x, y, v, g, "" + (page * itemsPP + i + 1)); break;
/* 2008 */         case 110: KendokuBuild.printKDPPuz(g2, x, y, v, g, "" + (page * itemsPP + i + 1)); break;
/* 2009 */         case 230: MarupekeBuild.printKDPPuz(g2, x, y, v, g, "" + (page * itemsPP + i + 1)); break;
/* 2010 */         case 132: MinesweeperBuild.printKDPPuz(g2, x, y, v, g, "" + (page * itemsPP + i + 1)); break;
/* 2011 */         case 150: RoundaboutsBuild.printKDPPuz(g2, x, y, v, g, "" + (page * itemsPP + i + 1)); break;
/* 2012 */         case 160: SikakuBuild.printKDPPuz(g2, x, y, v, g, "" + (page * itemsPP + i + 1)); break;
/* 2013 */         case 170: SlitherlinkBuild.printKDPPuz(g2, x, y, v, g, "" + (page * itemsPP + i + 1)); break;
/* 2014 */         case 180: SudokuBuild.printKDPPuz(g2, x, y, v, g, "" + (page * itemsPP + i + 1)); break;
/* 2015 */         case 182: TatamiBuild.printKDPPuz(g2, x, y, v, g, "" + (page * itemsPP + i + 1)); break;
/* 2016 */         case 190: TentsBuild.printKDPPuz(g2, x, y, v, g, "" + (page * itemsPP + i + 1)); break;
/* 2017 */         case 4: CrosswordBuild.printKDPPuz(g2, x, y, v, g, "" + (page * itemsPP + i + 1)); break; }
/*      */     
/*      */     } 
/* 2020 */     Def.dispWithColor = Boolean.valueOf(true);
/*      */   }
/*      */   
/*      */   static void drawKdpSol(Graphics2D g2, int left, int top, int width, int page, int puzType, String title) {
/* 2024 */     int rows = 3, columns = 2;
/*      */ 
/*      */ 
/*      */     
/* 2028 */     RenderingHints rh = g2.getRenderingHints();
/* 2029 */     rh.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
/* 2030 */     rh.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
/* 2031 */     g2.setRenderingHints(rh);
/*      */     
/* 2033 */     int v = width * 10 / (11 * columns + 1);
/* 2034 */     int g = v / 10;
/*      */     
/* 2036 */     g2.setColor(new Color(16777215));
/* 2037 */     g2.fillRect(left, top, g + columns * (v + g), 2 * g + rows * (v + 2 * g));
/* 2038 */     g2.setColor(Def.COLOR_BLACK);
/* 2039 */     g2.setStroke(new BasicStroke(1.0F, 2, 0));
/* 2040 */     g2.drawRect(left, top, g + columns * (v + g), 2 * g + rows * (v + 2 * g));
/* 2041 */     g2.fillRect(left, top, g + columns * (v + g), 5 * g / 3);
/*      */     
/* 2043 */     g2.setFont(new Font("SansSerif", 1, g));
/* 2044 */     FontMetrics fm = g2.getFontMetrics();
/* 2045 */     String st = title + " " + (page + 1);
/* 2046 */     int w = fm.stringWidth(st);
/* 2047 */     g2.setColor(Def.COLOR_WHITE);
/* 2048 */     g2.drawString(st, left + (g + columns * (v + g) - w) / 2, top + (5 * g / 3 + fm.getAscent()) / 2);
/*      */     
/* 2050 */     Def.dispGuideDigits = Boolean.valueOf(false);
/* 2051 */     Def.dispSolArray = Boolean.valueOf(false);
/* 2052 */     Def.dispWithColor = Boolean.valueOf(useColor);
/*      */     
/* 2054 */     for (int i = 0; i < itemsPP; i++) {
/* 2055 */       int x, y; if (itemsPP == 2) {
/* 2056 */         x = left + g;
/* 2057 */         y = top + 3 * g;
/*      */       } else {
/*      */         
/* 2060 */         x = left + g + i % columns * (v + g);
/* 2061 */         y = top + 3 * g + i / columns * (v + 2 * g);
/*      */       } 
/* 2063 */       g2.setColor(Def.COLOR_BLACK);
/* 2064 */       g2.setFont(new Font("SansSerif", 1, 3 * g / 4));
/*      */       
/* 2066 */       switch (puzType) { case 20:
/* 2067 */           AkariBuild.printKDPSol(g2, x, y, v, g, "" + (page * itemsPP + i + 1)); break;
/* 2068 */         case 40: DominoBuild.printKDPSol(g2, x, y, v, g, "" + (page * itemsPP + i + 1)); break;
/* 2069 */         case 70: FillominoBuild.printKDPSol(g2, x, y, v, g, "" + (page * itemsPP + i + 1)); break;
/* 2070 */         case 80: FutoshikiBuild.printKDPSol(g2, x, y, v, g, "" + (page * itemsPP + i + 1)); break;
/* 2071 */         case 90: GokigenBuild.printKDPSol(g2, x, y, v, g, "" + (page * itemsPP + i + 1)); break;
/* 2072 */         case 100: KakuroBuild.printKDPSol(g2, x, y, v, g, "" + (page * itemsPP + i + 1)); break;
/* 2073 */         case 110: KendokuBuild.printKDPSol(g2, x, y, v, g, "" + (page * itemsPP + i + 1)); break;
/* 2074 */         case 230: MarupekeBuild.printKDPSol(g2, x, y, v, g, "" + (page * itemsPP + i + 1)); break;
/* 2075 */         case 132: MinesweeperBuild.printKDPSol(g2, x, y, v, g, "" + (page * itemsPP + i + 1)); break;
/* 2076 */         case 150: RoundaboutsBuild.printKDPSol(g2, x, y, v, g, "" + (page * itemsPP + i + 1)); break;
/* 2077 */         case 160: SikakuBuild.printKDPSol(g2, x, y, v, g, "" + (page * itemsPP + i + 1)); break;
/* 2078 */         case 170: SlitherlinkBuild.printKDPSol(g2, x, y, v, g, "" + (page * itemsPP + i + 1)); break;
/* 2079 */         case 180: SudokuBuild.printKDPSol(g2, x, y, v, g, "" + (page * itemsPP + i + 1)); break;
/* 2080 */         case 182: TatamiBuild.printKDPSol(g2, x, y, v, g, "" + (page * itemsPP + i + 1)); break;
/* 2081 */         case 190: TentsBuild.printKDPSol(g2, x, y, v, g, "" + (page * itemsPP + i + 1)); break;
/* 2082 */         case 4: CrosswordBuild.printKDPSol(g2, x, y, v, g, "" + (page * itemsPP + i + 1));
/*      */           break; }
/*      */     
/*      */     } 
/* 2086 */     Def.dispWithColor = Boolean.valueOf(true);
/*      */   }
/*      */ 
/*      */   
/*      */   static void printProcessKpuz(int howmany, int puzzleType) {
/* 2091 */     hmPuz = howmany;
/* 2092 */     puzType = puzzleType;
/*      */     
/* 2094 */     PrinterJob job = PrinterJob.getPrinterJob();
/* 2095 */     job.setPrintable(new PrintKDPpuz());
/* 2096 */     if (job.printDialog()) {
/* 2097 */       try { job.print(); }
/* 2098 */       catch (Exception pe) {}
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static void printProcessKsol(int howmany, int puzzleType) {
/* 2104 */     hmPuz = howmany;
/* 2105 */     puzType = puzzleType;
/*      */     
/* 2107 */     PrinterJob job = PrinterJob.getPrinterJob();
/* 2108 */     job.setPrintable(new PrintKDPsol());
/* 2109 */     if (job.printDialog())
/* 2110 */       try { job.print(); }
/* 2111 */       catch (Exception pe) {} 
/*      */   }
/*      */ }


/* Location:              C:\Users\mustapha\Desktop\Crossword-Express.jar!\crosswordexpress\Methods.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */