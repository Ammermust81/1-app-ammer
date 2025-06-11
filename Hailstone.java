/*     */ package crosswordexpress;
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.io.BufferedWriter;
/*     */ import java.util.Random;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JCheckBox;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JMenuItem;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTextArea;
/*     */ import javax.swing.JTextField;
/*     */ import org.apfloat.Apint;
/*     */ 
/*     */ public final class Hailstone {
/*     */   static JFrame jfCollatz;
/*     */   static JPanel pp;
/*     */   static JPanel jpHs;
/*     */   static JLabel jlNum;
/*     */   static JLabel jlSig;
/*     */   static JTextArea jtaNumber;
/*     */   static JTextArea jtaSignature;
/*     */   static JButton jbNumSig;
/*     */   static JButton jbSigNum;
/*     */   static JComboBox<String> jcbbSpacing;
/*  30 */   Apint zero = new Apint(0L); static JComboBox<String> jcbbDisplay; static JScrollPane spn; static JScrollPane sps; static JScrollPane spr; static JButton jbClear; static JDialog jdlgCollatzResults; static JMenuBar menuBar; static JTextArea jtaRes; JMenu menu; JMenuItem menuItem; Apint one = new Apint(1L); Apint two = new Apint(2L); Apint three = new Apint(3L); Apint four = new Apint(4L); Apint x; Apint y;
/*     */   Apint a;
/*     */   Apint b;
/*  33 */   static String numDigits = "30";
/*  34 */   static String numNumbers = "1000";
/*     */   
/*     */   static String manualResult;
/*     */   static String ssProfile;
/*     */   static String profileRes;
/*     */   static String longSignatures;
/*  40 */   static String biggestNumRes = "\n\nBiggest Number in series.";
/*  41 */   Apint biggestNumber = new Apint(0L);
/*     */   
/*  43 */   static int boundary = 0;
/*  44 */   static int spacing = 1;
/*  45 */   static int display = 20;
/*     */   
/*  47 */   static int hstW = 3 * Methods.scrW / 5, hstH = 5 * Methods.scrH / 8;
/*  48 */   static int resW = 3 * Methods.scrW / 5; static int resH = hstH / 2; static int lowestSyllableCount;
/*     */   static int highestSyllableCount;
/*     */   static int areaH;
/*     */   static int CollatzW;
/*     */   static int CollatzH;
/*  53 */   static int[] sigLength = new int[1000000]; static int testNum; static int testNumDraw;
/*     */   static int hmDigits;
/*     */   static int hmNumbers;
/*     */   static long totalSyllablesDraw;
/*  57 */   static Color hClr = new Color(6710886); static Color taClr = new Color(16777215);
/*  58 */   static Font theFont = new Font("Courier", 0, 15);
/*  59 */   static Font labelFont = new Font("Arial", 1, 25);
/*  60 */   static Font theFont2 = new Font("Courier", 0, 12); static boolean running = false; static boolean signumRecord = true; static boolean stop;
/*     */   static boolean magnify = false;
/*     */   Thread thread;
/*  63 */   static long[] cumSyllableLength = new long[1000];
/*  64 */   static long totalSyllables = 0L;
/*     */ 
/*     */   
/*  67 */   static String numberSignature = "<span class='m'>Number / Signature Conversion operations</span><br/><br/><ul><li/><b>Calculate a Signature from a Number.</b> Type the number directly into the <b>Number</b> field, and click the <b>Number to Signature</b> button. The Signature will appear in the <b>Signature</b> field, and some explanatiry notes will appear in the <b>Collatz Results</b> field.<p><li/><b>Calculate a Number from a Signature.</b> Type the signature directly into the <b>Signature</b> field, and click the <b>Signature to Number</b> button. The Number will appear in the <b>Number</b> field, and the <b>Collatz Results</b> field will be populated with an abbreviated description of the algorithm used to calculate the number. Note especially the format of the number dispaly. It represents an infinite series of numbers, all of which have signatures which begin with a common set of characters.<p/><li/><b>How to design a number which has very special characteristics.</b> For example say you wanted a number which would start its Collatz series with ten consecutive odd numbers, followed by ten even mumbers, followed by another ten odd numbers. All you need to do is to create a signature with ten consecutive OE syllables followed by ten consecutive E syllables, followed again by ten consecutive OE syllables. In other words, the complete signature would be<br>OEOEOEOEOEOEOEOEOEOE<br>EEEEEEEEEE<br>OEOEOEOEOEOEOEOEOEOE<br> Type it into the <b>Signature</b> field, and click the <b>Signature to Number</b> button. You should receive the number 451306495. Use a calculator to extract the Collatz series for this number, and you will find that the series begins with the odd and even numbers appearing in exactly the order you specified.<p/><li/><b>Try some signatures of your own choosing.</b> You should be able to create numbers with some very unlikely characteirtics. Relax about the size of the numbers involved. The program should have no problem whatever handling numbers with at least a few thousand digits. There is no end to the interesting games you can play in this way.</ul>";
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
/*  93 */   String optionsHelp = "<span class='m'>Rule-of-8 Options</span><br/><br/><div>To get the most out of the <b>Rule of 8</b> program you will want to adjust some of the following options before starting a batch processing run.<br/><br/><ul><li/><b>Digits per Number:</b> The default value for this option is 30. You probably wouldn't want to use a lower value than this, but you can use much larger numbers. The program has been successfully tested with 5,000 digit numbers. Perhaps you wouldn't be interested in exceeding this number.<p/><li/><b>How Many Numbers:</b> The default value for this number is 1,000, but much larger numbers are recommended if you want to gain a full appreciation of the Rule-of-8. For example, if you want to see just how closely the <b>Average Signature Syllable Count</b> corresponds to the predicted value of eight times the number of digits in the numbers being processed, you should consider opting for a figure of some (perhaps many) thousands. That in turn may require some hours of processing time, so don't be afraid to let your computer work on it while you sleep.<p/><li/><b>Bar Graph Spacing:</b> By default the program places a one pixel wide gap between the bars of the Bar Graph. This is quite satisfactory for most situations, but if you are processing very large numbers you will quickly find out that the graph is too wide for yor screen. You can gain some additional space by reducing the gap between bars to the minimum value of 0. Also, don't forget that the program windows are resizable over a very wide range, even to such an extent that they overflow the edge of the screen.<p/><li/><b>Display Bar Graph every ? numbers:</b> You can choose to redisplay the bar graph after the calculation of each and every number (suitable for very large numbers which take a long time to calculate) or only after a number of numbers (up to a maximum of 50) have been processed. This will minimise wasted time when shorter numbers are being processed.<p/><li/><b>Report Signature Length and Number if Signature Length exceeds...</b> If you insert a number into this option, then when you run the Rule of 8 option, any number which produces a signature length greater than the number you inserted will be listed, along with its signature length in the Collatz Results window.<p/><li/><b>Magnify outlier signature counts:</b> As the Rule of 8 program runs, it generates a bar graph of the results obtained. The lengths of the bars at the extreme left and right ends of this graph become very short, and it becomes quite difficult to judge what is happening here. This option allows you to magnify the shortest elements in the graph and thereby gain a better appreciation of what is happening.<p/></ul>";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int dialogWidth;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int dialogHeight;
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
/*     */   Hailstone(JFrame jf) {
/* 125 */     jfCollatz = new JFrame("Collatz");
/* 126 */     jfCollatz.setLocation(300, 0);
/* 127 */     jfCollatz.setSize(hstW, hstH);
/* 128 */     jfCollatz.getContentPane().setBackground(hClr);
/* 129 */     jfCollatz.setLayout((LayoutManager)null);
/* 130 */     jfCollatz.setDefaultCloseOperation(0);
/* 131 */     jfCollatz.setResizable(true);
/*     */     
/* 133 */     jpHs = new JPanel();
/* 134 */     jpHs.setLayout((LayoutManager)null);
/* 135 */     jpHs.setSize(jfCollatz.getWidth(), jfCollatz.getHeight());
/* 136 */     jpHs.setLocation(0, 0);
/* 137 */     jpHs.setBackground(hClr);
/* 138 */     jpHs.setOpaque(true);
/* 139 */     jpHs.setBorder(BorderFactory.createEtchedBorder());
/* 140 */     jfCollatz.add(jpHs);
/*     */     
/* 142 */     jfCollatz
/* 143 */       .addComponentListener(new ComponentAdapter() {
/*     */           public void componentResized(ComponentEvent ce) {
/* 145 */             Hailstone.jpHs.setSize(Hailstone.jfCollatz.getWidth(), Hailstone.jfCollatz.getHeight());
/* 146 */             Hailstone.CollatzW = Hailstone.jfCollatz.getWidth() - Def.insets.left - Def.insets.right;
/* 147 */             Hailstone.CollatzH = Hailstone.jfCollatz.getHeight() - Def.insets.top - Def.insets.bottom;
/* 148 */             Hailstone.areaH = (Hailstone.CollatzH - 110) / 2;
/* 149 */             Hailstone.spn.setSize(Hailstone.CollatzW - 20, Hailstone.areaH);
/* 150 */             Hailstone.sps.setSize(Hailstone.CollatzW - 20, Hailstone.areaH);
/* 151 */             Hailstone.spn.setLocation(10, 30);
/* 152 */             Hailstone.sps.setLocation(10, Hailstone.CollatzH - 34 - Hailstone.areaH);
/* 153 */             Hailstone.jbNumSig.setLocation(140, Hailstone.areaH + 34);
/* 154 */             Hailstone.jbSigNum.setLocation(409, Hailstone.areaH + 34);
/* 155 */             Hailstone.jlSig.setLocation(10, Hailstone.areaH + 49);
/* 156 */             if (Hailstone.running) {
/* 157 */               Hailstone.pp.setSize(Hailstone.jfCollatz.getWidth() + 100, Hailstone.jfCollatz.getHeight() + 100);
/* 158 */               Hailstone.pp.invalidate();
/* 159 */               Hailstone.pp.repaint();
/*     */             } 
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 165 */     jfCollatz
/* 166 */       .addWindowListener(new WindowAdapter() {
/*     */           public void windowClosing(WindowEvent we) {
/* 168 */             CrosswordExpress.transfer(1, Hailstone.jfCollatz);
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 173 */     Runnable testThread = () -> test();
/*     */ 
/*     */     
/* 176 */     menuBar = new JMenuBar();
/* 177 */     menuBar.setBackground(Def.COLOR_MENUBAR);
/* 178 */     jfCollatz.setJMenuBar(menuBar);
/*     */     
/* 180 */     this.menu = new JMenu("Rule of 8 Demonstration");
/* 181 */     menuBar.add(this.menu);
/* 182 */     this.menuItem = new JMenuItem("Execute the Rule of 8 program");
/* 183 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(82, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 184 */     this.menu.add(this.menuItem);
/* 185 */     this.menuItem
/* 186 */       .addActionListener(ae -> {
/*     */           this.thread = new Thread(paramRunnable);
/*     */           
/*     */           this.thread.start();
/*     */         });
/*     */     
/* 192 */     this.menuItem = new JMenuItem("Rule of 8 Options");
/* 193 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(79, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 194 */     this.menu.add(this.menuItem);
/* 195 */     this.menuItem
/* 196 */       .addActionListener(ae -> collatzOptions());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 202 */     this.menu = new JMenu("Help");
/* 203 */     menuBar.add(this.menu);
/* 204 */     this.menuItem = new JMenuItem("Number / Signature Conversions");
/* 205 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(72, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 206 */     this.menu.add(this.menuItem);
/* 207 */     this.menuItem
/* 208 */       .addActionListener(ae -> Methods.cweHelp(jfCollatz, null, "<b>Number / Signature Conversions</b>", numberSignature));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 213 */     this.menuItem = new JMenuItem("Rule of 8 Options");
/* 214 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(79, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 215 */     this.menu.add(this.menuItem);
/* 216 */     this.menuItem
/* 217 */       .addActionListener(ae -> collatzOptions());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 223 */     jtaNumber = new JTextArea();
/* 224 */     jtaNumber.setBackground(taClr);
/* 225 */     jtaNumber.setLineWrap(true);
/* 226 */     jtaNumber.setWrapStyleWord(false);
/* 227 */     jtaNumber.setFont(theFont);
/* 228 */     jtaNumber.setMargin(new Insets(10, 10, 10, 10));
/* 229 */     spn = new JScrollPane(jtaNumber);
/* 230 */     spn.setHorizontalScrollBarPolicy(31);
/* 231 */     spn.setVerticalScrollBarPolicy(20);
/* 232 */     jpHs.add(spn);
/* 233 */     jtaNumber.requestFocusInWindow();
/*     */     
/* 235 */     jtaSignature = new JTextArea();
/* 236 */     jtaSignature.setBackground(taClr);
/* 237 */     jtaSignature.setLineWrap(true);
/* 238 */     jtaSignature.setWrapStyleWord(false);
/* 239 */     jtaSignature.setFont(theFont);
/* 240 */     jtaSignature.setMargin(new Insets(10, 10, 10, 10));
/* 241 */     sps = new JScrollPane(jtaSignature);
/* 242 */     sps.setHorizontalScrollBarPolicy(31);
/* 243 */     sps.setVerticalScrollBarPolicy(20);
/* 244 */     jpHs.add(sps);
/* 245 */     jfCollatz.setVisible(true);
/*     */     
/* 247 */     jbNumSig = Methods.cweButton(" Number to Signature", 10, 204, 250, 37, new ImageIcon("graphics/darrow.png"));
/* 248 */     jbNumSig.setFont(theFont);
/* 249 */     jpHs.add(jbNumSig);
/* 250 */     jbNumSig
/* 251 */       .addActionListener(ae -> numberToSignature("0"));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 256 */     jbSigNum = Methods.cweButton(" Signature to Number", 10, 204, 250, 37, new ImageIcon("graphics/uarrow.png"));
/* 257 */     jbSigNum.setFont(theFont);
/* 258 */     jpHs.add(jbSigNum);
/* 259 */     jbSigNum
/* 260 */       .addActionListener(ae -> signatureToNumber());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 265 */     jlNum = new JLabel("Number");
/* 266 */     jlNum.setForeground(Color.WHITE);
/* 267 */     jlNum.setSize(120, 20);
/* 268 */     jlNum.setLocation(10, 10);
/* 269 */     jlNum.setHorizontalAlignment(2);
/* 270 */     jlNum.setFont(labelFont);
/* 271 */     jpHs.add(jlNum);
/*     */     
/* 273 */     jlSig = new JLabel("Signature");
/* 274 */     jlSig.setForeground(Color.WHITE);
/* 275 */     jlSig.setSize(120, 25);
/* 276 */     jlSig.setLocation(10, areaH + 50);
/* 277 */     jlSig.setHorizontalAlignment(2);
/* 278 */     jlSig.setFont(labelFont);
/* 279 */     jpHs.add(jlSig);
/*     */     
/* 281 */     CollatzResults("");
/*     */   }
/*     */ 
/*     */   
/*     */   int numberToSignature(String theNum) {
/* 286 */     int syllableCount = 0;
/*     */ 
/*     */     
/* 289 */     String correctCollatz = "";
/* 290 */     String signature = "";
/* 291 */     long limit = 99999999L;
/* 292 */     long[] syllableLength = new long[1000];
/*     */     
/* 294 */     if (theNum.equals("0")) {
/* 295 */       collatz = jtaNumber.getText();
/*     */     } else {
/* 297 */       collatz = theNum;
/* 298 */     }  if (collatz.contains(" + "))
/* 299 */       collatz = collatz.substring(collatz.indexOf(" + ") + 3); 
/*     */     int i;
/* 301 */     for (i = 0; i < collatz.length(); i++) {
/* 302 */       char ch = collatz.charAt(i);
/* 303 */       if (ch >= '0' && ch <= '9')
/* 304 */         correctCollatz = correctCollatz + ch; 
/*     */     } 
/* 306 */     String collatz = correctCollatz;
/*     */     
/* 308 */     if (collatz.length() == 0) return 0;
/*     */     
/* 310 */     if (collatz.length() > 20) limit = (collatz.length() * 30);
/*     */     
/* 312 */     Apint num = new Apint(collatz);
/* 313 */     signature = ""; int letterCount = 0; while (true) {
/* 314 */       if (num.equals(this.one)) {
/* 315 */         signature = signature + "O";
/* 316 */         syllableLength[letterCount] = syllableLength[letterCount] + 1L;
/* 317 */         cumSyllableLength[letterCount] = cumSyllableLength[letterCount] + 1L;
/* 318 */         if (theNum.equals("0")) jtaSignature.setText(signature); 
/*     */         break;
/*     */       } 
/* 321 */       if (num.mod(this.two).equals(this.zero)) {
/* 322 */         num = num.divide(this.two); signature = signature + "E"; letterCount++; continue;
/*     */       } 
/* 324 */       num = num.multiply(this.three).add(this.one);
/* 325 */       syllableLength[letterCount] = syllableLength[letterCount] + 1L;
/* 326 */       cumSyllableLength[letterCount] = cumSyllableLength[letterCount] + 1L;
/* 327 */       letterCount = 0;
/* 328 */       signature = signature + "O";
/* 329 */       syllableCount++;
/* 330 */       if (syllableCount > limit) {
/* 331 */         return -syllableCount;
/*     */       }
/* 333 */       if (num.compareTo(this.biggestNumber) == 1) {
/* 334 */         this.biggestNumber = num;
/* 335 */         biggestNumRes += "\n" + this.biggestNumber.toString();
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 342 */     if (theNum.equals("0")) {
/*     */       
/* 344 */       manualResult = "The initial number has " + collatz.length() + " digits." + "\nThe number of Signature letters is " + signature.length() + "\nThe number of Signature Syllables is " + syllableCount + "\nSignature Syllable Profile:-";
/*     */ 
/*     */       
/* 347 */       for (i = 1, signature = "O"; i < 1000; i++) {
/* 348 */         signature = signature + "E";
/* 349 */         if (syllableLength[i] > 0L)
/* 350 */           manualResult += "\n" + signature + " " + syllableLength[i]; 
/*     */       } 
/* 352 */       jtaRes.setText(manualResult);
/* 353 */       jdlgCollatzResults.setVisible(true); buttonEnabled(false);
/*     */     } 
/*     */     
/* 356 */     return syllableCount;
/*     */   }
/*     */   
/*     */   String gap(String str1, int w) {
/* 360 */     for (; str1.length() < w; str1 = str1 + " ");
/* 361 */     return str1;
/*     */   }
/*     */   
/*     */   String xyab() {
/* 365 */     return gap(xy(this.x, this.y), 26) + xy(this.a, this.b);
/*     */   }
/*     */ 
/*     */   
/*     */   String xy(Apint x, Apint y) {
/* 370 */     String xStr = (("" + x).length() > 10) ? ("[" + ("" + x).length() + " digits]") : ("" + x);
/* 371 */     String yStr = (("" + y).length() > 10) ? ("[" + ("" + y).length() + " digits]") : ("" + y);
/* 372 */     return xStr + "n+" + yStr;
/*     */   }
/*     */   
/*     */   void signatureToNumber() {
/* 376 */     int gap = 63;
/*     */ 
/*     */     
/* 379 */     this.x = new Apint(2L); this.y = new Apint(1L);
/* 380 */     this.a = new Apint(2L); this.b = new Apint(1L);
/*     */     
/* 382 */     jdlgCollatzResults.setVisible(true); buttonEnabled(false);
/* 383 */     String signature = jtaSignature.getText();
/* 384 */     String str = signature.toUpperCase();
/* 385 */     signature = ""; int i;
/* 386 */     for (i = 0; i < str.length(); i++) {
/* 387 */       char ch = str.charAt(i);
/* 388 */       if (ch == 'O' || ch == 'E')
/* 389 */         signature = signature + ch; 
/*     */     } 
/* 391 */     jtaSignature.setText(signature);
/*     */     
/* 393 */     String str2 = gap("", 63) + gap("Ω", 26) + gap("Α\n", 4) + gap("Ω", 61) + "2n+1                      2n+1\n" + "-----------------------------------------------------------------------------------------------\n";
/*     */     
/* 395 */     jtaRes.setText(str2); str2 = "";
/* 396 */     for (i = 0; i < signature.length(); i++) {
/* 397 */       jtaRes.append(signature.charAt(i) + " " + xy(this.x, this.y) + "\n");
/*     */       
/* 399 */       if (signature.charAt(i) == 'O') {
/* 400 */         if (this.x.mod(this.two).equals(this.one) && this.y.mod(this.two).equals(this.zero)) {
/* 401 */           str2 = str2 + gap("For this to be odd, n must be odd, so set n to 2n+1.", gap);
/* 402 */           this.y = this.y.add(this.x); this.x = this.x.multiply(this.two); this.b = this.b.add(this.a); this.a = this.a.multiply(this.two);
/* 403 */           str2 = str2 + xyab() + "*";
/*     */         
/*     */         }
/* 406 */         else if (this.x.mod(this.two).equals(this.one) && this.y.mod(this.two).equals(this.one)) {
/* 407 */           str2 = str2 + gap("For this to be odd, n must be even, so set n to 2n.", gap);
/* 408 */           this.x = this.x.multiply(this.two); this.a = this.a.multiply(this.two);
/* 409 */           str2 = str2 + xyab();
/*     */         } 
/* 411 */         if (i < signature.length() - 1) {
/* 412 */           if (i > 0) str2 = str2 + gap("\n", 0); 
/* 413 */           str2 = str2 + gap(xy(this.x, this.y) + " is odd so Multiply by 3 and add 1.", gap);
/*     */           
/* 415 */           this.x = this.x.multiply(this.three); this.y = this.y.multiply(this.three).add(this.one);
/* 416 */           str2 = str2 + xyab() + "\n";
/*     */         } else {
/*     */           
/* 419 */           str2 = str2 + "\n" + xy(this.x, this.y) + " is odd as required.";
/*     */         } 
/*     */       } else {
/* 422 */         if (this.x.mod(this.two).equals(this.one) && this.y.mod(this.two).equals(this.one)) {
/* 423 */           str2 = str2 + gap("For this to be even, n must be odd, so set n to 2n+1.", gap);
/* 424 */           this.y = this.y.add(this.x); this.x = this.x.multiply(this.two); this.b = this.b.add(this.a); this.a = this.a.multiply(this.two);
/* 425 */           str2 = str2 + xyab() + "*\n" + gap("", 0);
/*     */         
/*     */         }
/* 428 */         else if (this.x.mod(this.two).equals(this.one) && this.y.mod(this.two).equals(this.zero)) {
/* 429 */           str2 = str2 + gap("For this to be even, n must be even, so set n to 2n.", gap);
/* 430 */           this.x = this.x.multiply(this.two); this.a = this.a.multiply(this.two);
/* 431 */           str2 = str2 + gap(xyab() + "\n", 16) + gap("", 0);
/*     */         } 
/* 433 */         str2 = str2 + gap(xy(this.x, this.y) + " is even, so divide by 2.", gap);
/* 434 */         this.x = this.x.divide(this.two); this.y = this.y.divide(this.two);
/* 435 */         str2 = str2 + gap(xyab(), 200) + "\n";
/*     */       } 
/* 437 */       jtaRes.append(str2);
/* 438 */       str2 = "";
/*     */     } 
/* 440 */     jtaNumber.setText(this.a + "n + " + this.b);
/*     */   }
/*     */ 
/*     */   
/*     */   static void CollatzResults(String res) {
/* 445 */     jdlgCollatzResults = new JDialog(jfCollatz, "Collatz Results", true);
/* 446 */     jdlgCollatzResults.setLocation(300, jfCollatz.getY() + jfCollatz.getHeight());
/* 447 */     jdlgCollatzResults.setSize(resW, resH);
/* 448 */     jdlgCollatzResults.getContentPane().setBackground(hClr);
/* 449 */     jdlgCollatzResults.setLayout((LayoutManager)null);
/* 450 */     jdlgCollatzResults.setResizable(true);
/* 451 */     jdlgCollatzResults.setModalityType(Dialog.ModalityType.MODELESS);
/* 452 */     jdlgCollatzResults.setVisible(true);
/*     */     
/* 454 */     jdlgCollatzResults
/* 455 */       .addComponentListener(new ComponentAdapter() {
/*     */           public void componentResized(ComponentEvent ce) {
/* 457 */             Hailstone.dialogWidth = Hailstone.jdlgCollatzResults.getWidth() - Def.insets.left - Def.insets.right;
/* 458 */             Hailstone.dialogHeight = Hailstone.jdlgCollatzResults.getHeight() - Def.insets.top - Def.insets.bottom;
/* 459 */             Hailstone.spr.setSize(Hailstone.dialogWidth - 20, Hailstone.dialogHeight - 55);
/* 460 */             Hailstone.jbClear.setLocation(10, Hailstone.dialogHeight - 36);
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 465 */     jtaRes = new JTextArea();
/* 466 */     jtaRes.setLocation(10, 10);
/* 467 */     jtaRes.setSize(resW - 20, resH - 85);
/* 468 */     jtaRes.setBackground(taClr);
/* 469 */     jtaRes.setLineWrap(true);
/* 470 */     jtaRes.setWrapStyleWord(false);
/* 471 */     jtaRes.setFont(theFont2);
/* 472 */     jtaRes.setMargin(new Insets(10, 10, 10, 10));
/* 473 */     jdlgCollatzResults.add(jtaRes);
/*     */     
/* 475 */     spr = new JScrollPane(jtaRes);
/* 476 */     spr.setLocation(10, 10);
/* 477 */     spr.setHorizontalScrollBarPolicy(31);
/* 478 */     spr.setVerticalScrollBarPolicy(20);
/* 479 */     jdlgCollatzResults.add(spr);
/*     */     
/* 481 */     dialogWidth = resW - Def.insets.left - Def.insets.right;
/* 482 */     dialogHeight = resH - Def.insets.top - Def.insets.bottom;
/* 483 */     spr.setSize(dialogWidth - 20, dialogHeight - 55);
/*     */     
/* 485 */     jtaRes.setVisible(true);
/* 486 */     spr.setVisible(true);
/*     */     
/* 488 */     jbClear = Methods.cweButton("", 10, 224, 400, 26, null);
/* 489 */     jdlgCollatzResults.add(jbClear);
/* 490 */     jbClear.setLocation(10, dialogHeight - 36);
/* 491 */     jbClear.setVisible(true);
/*     */     
/* 493 */     jbClear
/* 494 */       .addActionListener(e -> {
/*     */           if (pp != null) {
/*     */             stop = true;
/*     */             running = false;
/*     */             jtaRes.setText("");
/*     */             jpHs.setVisible(true);
/*     */             jdlgCollatzResults.setVisible(false);
/*     */           } 
/*     */         });
/*     */   }
/*     */   private void collatzOptions() {
/* 505 */     String[] freq = { "1", "5", "10", "20", "50" };
/*     */     
/* 507 */     JDialog jdlgCollatz = new JDialog(jfCollatz, "Rule of 8 Options", true);
/* 508 */     jdlgCollatz.setSize(300, 238);
/* 509 */     jdlgCollatz.setResizable(false);
/* 510 */     jdlgCollatz.setLayout((LayoutManager)null);
/* 511 */     jdlgCollatz.setLocation(jfCollatz.getX() + 100, jfCollatz.getY() + 100);
/*     */     
/* 513 */     jdlgCollatz
/* 514 */       .addWindowListener(new WindowAdapter() {
/*     */           public void windowClosing(WindowEvent we) {
/* 516 */             Methods.closeHelp();
/*     */           }
/*     */         });
/*     */     
/* 520 */     Methods.closeHelp();
/*     */     
/* 522 */     JLabel jlDigits = new JLabel("Digits per Number:");
/* 523 */     jlDigits.setForeground(Def.COLOR_LABEL);
/* 524 */     jlDigits.setSize(180, 20);
/* 525 */     jlDigits.setLocation(11, 8);
/* 526 */     jlDigits.setHorizontalAlignment(4);
/* 527 */     jdlgCollatz.add(jlDigits);
/*     */     
/* 529 */     JTextField jtfDigits = new JTextField(numDigits, 15);
/* 530 */     jtfDigits.setSize(60, 20);
/* 531 */     jtfDigits.setLocation(210, 8);
/* 532 */     jtfDigits.selectAll();
/* 533 */     jtfDigits.setHorizontalAlignment(2);
/* 534 */     jdlgCollatz.add(jtfDigits);
/*     */     
/* 536 */     JLabel jlNumbers = new JLabel("How Many Numbers:");
/* 537 */     jlNumbers.setForeground(Def.COLOR_LABEL);
/* 538 */     jlNumbers.setSize(190, 20);
/* 539 */     jlNumbers.setLocation(10, 30);
/* 540 */     jlNumbers.setHorizontalAlignment(4);
/* 541 */     jdlgCollatz.add(jlNumbers);
/*     */     
/* 543 */     JTextField jtfNumbers = new JTextField(numNumbers, 15);
/* 544 */     jtfNumbers.setSize(80, 20);
/* 545 */     jtfNumbers.setLocation(210, 30);
/* 546 */     jtfNumbers.selectAll();
/* 547 */     jtfNumbers.setHorizontalAlignment(2);
/* 548 */     jdlgCollatz.add(jtfNumbers);
/*     */     
/* 550 */     JLabel jlSpacing = new JLabel("Bar Graph Spacing:");
/* 551 */     jlSpacing.setForeground(Def.COLOR_LABEL);
/* 552 */     jlSpacing.setSize(140, 20);
/* 553 */     jlSpacing.setLocation(20, 52);
/* 554 */     jlSpacing.setHorizontalAlignment(4);
/* 555 */     jdlgCollatz.add(jlSpacing);
/*     */     
/* 557 */     jcbbSpacing = new JComboBox<>();
/* 558 */     for (int i = 0; i <= 3; i++)
/* 559 */       jcbbSpacing.addItem("" + i); 
/* 560 */     jcbbSpacing.setSize(60, 20);
/* 561 */     jcbbSpacing.setLocation(180, 52);
/* 562 */     jcbbSpacing.setSelectedItem("" + spacing);
/* 563 */     jdlgCollatz.add(jcbbSpacing);
/* 564 */     jcbbSpacing.setBackground(Def.COLOR_BUTTONBG);
/* 565 */     jcbbSpacing.setSelectedIndex(spacing);
/*     */     
/* 567 */     JLabel jlDisplay1 = new JLabel("Display Bar Graph every");
/* 568 */     jlDisplay1.setForeground(Def.COLOR_LABEL);
/* 569 */     jlDisplay1.setSize(160, 20);
/* 570 */     jlDisplay1.setLocation(10, 74);
/* 571 */     jlDisplay1.setHorizontalAlignment(4);
/* 572 */     jdlgCollatz.add(jlDisplay1);
/*     */     
/* 574 */     JLabel jlDisplay2 = new JLabel("numbers");
/* 575 */     jlDisplay2.setForeground(Def.COLOR_LABEL);
/* 576 */     jlDisplay2.setSize(280, 20);
/* 577 */     jlDisplay2.setLocation(230, 74);
/* 578 */     jlDisplay2.setHorizontalAlignment(2);
/* 579 */     jdlgCollatz.add(jlDisplay2);
/*     */     
/* 581 */     jcbbDisplay = new JComboBox<>(freq);
/* 582 */     jcbbDisplay.setSize(45, 20);
/* 583 */     jcbbDisplay.setLocation(180, 74);
/* 584 */     jdlgCollatz.add(jcbbDisplay);
/* 585 */     jcbbDisplay.setBackground(Def.COLOR_BUTTONBG);
/* 586 */     jcbbDisplay.setSelectedIndex(4);
/*     */     
/* 588 */     JCheckBox jcbMagnify = new JCheckBox("Magnify outlier signature values.", magnify);
/* 589 */     jcbMagnify.setForeground(Def.COLOR_LABEL);
/* 590 */     jcbMagnify.setOpaque(false);
/* 591 */     jcbMagnify.setSize(260, 20);
/* 592 */     jcbMagnify.setLocation(20, 96);
/* 593 */     jdlgCollatz.add(jcbMagnify);
/*     */     
/* 595 */     JLabel jlReport1 = new JLabel("Report Signature Length and Number");
/* 596 */     jlReport1.setForeground(Def.COLOR_LABEL);
/* 597 */     jlReport1.setSize(280, 20);
/* 598 */     jlReport1.setLocation(20, 118);
/* 599 */     jlReport1.setHorizontalAlignment(2);
/* 600 */     jdlgCollatz.add(jlReport1);
/*     */     
/* 602 */     JLabel jlReport2 = new JLabel("if Signature Length exceeds");
/* 603 */     jlReport2.setForeground(Def.COLOR_LABEL);
/* 604 */     jlReport2.setSize(280, 20);
/* 605 */     jlReport2.setLocation(20, 140);
/* 606 */     jlReport2.setHorizontalAlignment(2);
/* 607 */     jdlgCollatz.add(jlReport2);
/*     */     
/* 609 */     JTextField jtfBoundary = new JTextField("" + boundary, 15);
/* 610 */     jtfBoundary.setSize(80, 20);
/* 611 */     jtfBoundary.setLocation(210, 140);
/* 612 */     jtfBoundary.selectAll();
/* 613 */     jtfBoundary.setHorizontalAlignment(2);
/* 614 */     jdlgCollatz.add(jtfBoundary);
/*     */     
/* 616 */     JButton jbOK = Methods.cweButton("OK", 30, 168, 80, 26, null);
/* 617 */     jbOK.addActionListener(e -> {
/*     */           paramJDialog.dispose();
/*     */           numDigits = paramJTextField1.getText();
/*     */           numNumbers = paramJTextField2.getText();
/*     */           boundary = Integer.parseInt(paramJTextField3.getText());
/*     */           spacing = Integer.parseInt(jcbbSpacing.getSelectedItem().toString());
/*     */           display = Integer.parseInt(jcbbDisplay.getSelectedItem().toString());
/*     */           magnify = paramJCheckBox.isSelected();
/*     */           Methods.closeHelp();
/*     */         });
/* 627 */     jdlgCollatz.add(jbOK);
/*     */     
/* 629 */     JButton jbCancel = Methods.cweButton("Cancel", 30, 203, 80, 26, null);
/* 630 */     jbCancel.addActionListener(e -> {
/*     */           paramJDialog.dispose();
/*     */           Methods.closeHelp();
/*     */         });
/* 634 */     jdlgCollatz.add(jbCancel);
/*     */     
/* 636 */     JButton jbHelp = Methods.cweButton("<html><font size=6 color=BB0000 face=Serif>Help ", 120, 168, 150, 61, new ImageIcon("graphics/help.png"));
/* 637 */     jbHelp.addActionListener(e -> Methods.cweHelp(null, paramJDialog, "Collatz Options", this.optionsHelp));
/*     */     
/* 639 */     jdlgCollatz.add(jbHelp);
/*     */     
/* 641 */     jdlgCollatz.getRootPane().setDefaultButton(jbOK);
/* 642 */     Methods.setDialogSize(jdlgCollatz, 300, 238);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void test() {
/* 649 */     Random r = new Random();
/* 650 */     String digits = "0123456789", odds = "13579";
/*     */     
/* 652 */     jdlgCollatzResults.setVisible(true);
/*     */     
/* 654 */     longSignatures = "";
/*     */ 
/*     */     
/* 657 */     hmDigits = Integer.parseInt(numDigits);
/* 658 */     hmNumbers = Integer.parseInt(numNumbers);
/* 659 */     buttonEnabled(true); int i;
/* 660 */     for (i = 0; i < 100000; ) { sigLength[i] = 0; i++; }
/* 661 */      for (i = 0; i < 1000; ) { cumSyllableLength[i] = 0L; i++; }
/*     */     
/* 663 */     running = true;
/* 664 */     pp = new HailstonePP(0, 0, jfCollatz);
/* 665 */     pp.setSize(jfCollatz.getWidth() + 100, jfCollatz.getHeight() + 100);
/* 666 */     pp.repaint();
/*     */     
/* 668 */     jpHs.setVisible(false);
/* 669 */     pp.repaint();
/*     */     
/* 671 */     highestSyllableCount = 0; lowestSyllableCount = 1000000;
/* 672 */     totalSyllables = 0L;
/* 673 */     for (stop = false, testNum = 0; testNum < hmNumbers && 
/* 674 */       !stop; testNum++) {
/* 675 */       if (testNum != 0 && testNum % display == 0) {
/* 676 */         testNumDraw = testNum; totalSyllablesDraw = totalSyllables;
/* 677 */         pp.repaint();
/*     */       } 
/*     */       char ch;
/* 680 */       for (ch = '0'; ch == '0'; ch = digits.charAt(r.nextInt(10)));
/* 681 */       String collatzStr = "" + ch;
/* 682 */       int loop = hmDigits - 2;
/*     */       
/* 684 */       for (i = 0; i < loop; ) { collatzStr = collatzStr + digits.charAt(r.nextInt(10)); i++; }
/* 685 */        collatzStr = collatzStr + odds.charAt(r.nextInt(5));
/*     */       
/* 687 */       int hmSyllables = numberToSignature(collatzStr);
/*     */       
/* 689 */       if (hmSyllables > boundary && boundary != 0) {
/* 690 */         longSignatures += "" + hmSyllables + "\n" + collatzStr + "\n\n";
/*     */       }
/*     */       
/* 693 */       if (hmSyllables < 0) {
/* 694 */         try (BufferedWriter writer = new BufferedWriter(new FileWriter("hailstoneResult.txt"))) {
/* 695 */           DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
/* 696 */           LocalDateTime now = LocalDateTime.now();
/* 697 */           writer.write("\n\n\n\tDate and time of the result event was " + dtf.format(now));
/* 698 */           writer.write("\n\n\t" + hmDigits + " digit numbers were being tested.");
/* 699 */           writer.write("\n\n\t" + testNum + " of " + hmNumbers + " numbers had been completed.");
/* 700 */           writer.write("\n\n\tThe number being tested was " + collatzStr);
/*     */         }
/* 702 */         catch (IOException ioe) {}
/*     */         
/* 704 */         browserLaunch.openURL(jfCollatz, "http://www.crauswords.com/hailstone/hailstone-result.html");
/*     */         
/*     */         return;
/*     */       } 
/* 708 */       sigLength[hmSyllables] = sigLength[hmSyllables] + 1;
/* 709 */       totalSyllables += hmSyllables;
/* 710 */       if (hmSyllables > highestSyllableCount) {
/* 711 */         highestSyllableCount = hmSyllables;
/*     */       }
/* 713 */       if (hmSyllables < lowestSyllableCount) {
/* 714 */         lowestSyllableCount = hmSyllables;
/*     */       }
/*     */     } 
/* 717 */     testNumDraw = testNum; totalSyllablesDraw = totalSyllables;
/* 718 */     pp.repaint();
/*     */   }
/*     */   
/*     */   static String stretchCollatz(String collatzStr, int hmSyllables) {
/* 722 */     Apint one = new Apint(1L), two = new Apint(2L), three = new Apint(3L);
/* 723 */     Apint inC = new Apint(collatzStr);
/* 724 */     String str = "\n" + hmSyllables + "\n" + collatzStr + "\n";
/*     */ 
/*     */     
/* 727 */     if (inC.multiply(two).mod(three).equals(one)) {
/*     */       
/* 729 */       while (inC.multiply(two).mod(three).equals(one)) {
/* 730 */         inC = inC.multiply(two).subtract(one).divide(three);
/* 731 */         str = str + inC + "\n";
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 736 */       return str;
/*     */     } 
/*     */     
/* 739 */     while (str.endsWith(" ")) {
/* 740 */       StringBuffer sb = new StringBuffer(str);
/* 741 */       sb.deleteCharAt(sb.length() - 1);
/*     */     } 
/* 743 */     return "";
/*     */   }
/*     */ 
/*     */   
/*     */   static void drawCollatz(Graphics2D g2) {
/* 748 */     int firstSigLengthIndex = 0, lastSigLengthIndex = 100000, highestSignatureSyllableCount = 1;
/* 749 */     int longestBar = 0;
/*     */ 
/*     */     
/* 752 */     int outliers = 10;
/* 753 */     String Result = "";
/*     */ 
/*     */     
/* 756 */     Stroke wideStroke = new BasicStroke(3.0F, 0, 0);
/* 757 */     Stroke narrowStroke = new BasicStroke(1.0F, 0, 0);
/* 758 */     g2.setStroke(narrowStroke);
/*     */     
/* 760 */     g2.setColor(new Color(13421755));
/* 761 */     g2.fillRect(0, 0, jfCollatz.getWidth(), jfCollatz.getHeight());
/*     */     
/* 763 */     g2.setColor(Color.WHITE);
/* 764 */     g2.fillRect(0, 80, jfCollatz.getWidth(), jfCollatz.getHeight() - 160);
/*     */     
/* 766 */     g2.setColor(Color.BLUE);
/* 767 */     Font drawFont = new Font("Arial", 1, 15);
/* 768 */     g2.setFont(drawFont);
/* 769 */     FontMetrics fm = g2.getFontMetrics();
/* 770 */     g2.drawString("" + lowestSyllableCount, 10, CollatzH - 30);
/* 771 */     g2.drawString("" + highestSyllableCount, 10 + (spacing + 1) * (highestSyllableCount - lowestSyllableCount) - fm.stringWidth("" + highestSyllableCount), CollatzH - 30);
/*     */     
/* 773 */     if (magnify) {
/* 774 */       int j; for (j = 0; j < 1000000; j++) {
/* 775 */         if (sigLength[j] > 0 && sigLength[j] <= outliers) {
/* 776 */           if (firstSigLengthIndex == 0)
/* 777 */             firstSigLengthIndex = j; 
/* 778 */           lastSigLengthIndex = j;
/* 779 */           if (sigLength[j] > highestSignatureSyllableCount) {
/* 780 */             highestSignatureSyllableCount = sigLength[j];
/*     */           }
/*     */         } 
/*     */       } 
/* 784 */       g2.setColor(Color.RED);
/* 785 */       for (j = firstSigLengthIndex; j <= lastSigLengthIndex; j++) {
/* 786 */         if (sigLength[j] > 0) {
/* 787 */           if (sigLength[j] > 0 && sigLength[j] <= outliers) {
/* 788 */             int thisLineX = 14 + (spacing + 1) * (j - firstSigLengthIndex);
/* 789 */             g2.drawLine(thisLineX, CollatzH - 59, thisLineX, CollatzH - 60 - (CollatzH - 140) * sigLength[j] / highestSignatureSyllableCount);
/*     */           } 
/*     */           
/* 792 */           if (sigLength[j] > longestBar) longestBar = sigLength[j]; 
/*     */         } 
/*     */       } 
/*     */     }  int sigLengthIndex;
/* 796 */     for (sigLengthIndex = 0; sigLengthIndex < 1000000; sigLengthIndex++) {
/* 797 */       if (sigLength[sigLengthIndex] > 0) {
/* 798 */         if (firstSigLengthIndex == 0)
/* 799 */           firstSigLengthIndex = sigLengthIndex; 
/* 800 */         lastSigLengthIndex = sigLengthIndex;
/* 801 */         if (sigLength[sigLengthIndex] > highestSignatureSyllableCount) {
/* 802 */           highestSignatureSyllableCount = sigLength[sigLengthIndex];
/*     */         }
/*     */       } 
/*     */     } 
/* 806 */     g2.setColor(Color.BLACK);
/* 807 */     for (sigLengthIndex = firstSigLengthIndex; sigLengthIndex <= lastSigLengthIndex; sigLengthIndex++) {
/* 808 */       if (sigLength[sigLengthIndex] > 0) {
/* 809 */         g2.drawLine(14 + (spacing + 1) * (sigLengthIndex - firstSigLengthIndex), CollatzH - 59, 14 + (spacing + 1) * (sigLengthIndex - firstSigLengthIndex), CollatzH - 60 - (CollatzH - 140) * sigLength[sigLengthIndex] / highestSignatureSyllableCount);
/*     */         
/* 811 */         if (sigLength[sigLengthIndex] > longestBar) longestBar = sigLength[sigLengthIndex]; 
/*     */       } 
/*     */     } 
/* 814 */     g2.setColor(Color.BLUE); int i;
/* 815 */     for (i = 14; i < 14 + (lastSigLengthIndex - firstSigLengthIndex) * (spacing + 1); i += 100 * (spacing + 1)) {
/* 816 */       g2.drawLine(i, CollatzH - 55, i, CollatzH - 50);
/*     */     }
/* 818 */     g2.drawString("" + longestBar, 5, 100);
/* 819 */     g2.drawString("0", 5, CollatzH - 60);
/*     */     
/* 821 */     int legendX = (spacing + 1) * (hmDigits * 8 - lowestSyllableCount) - 50;
/* 822 */     if (legendX < 400) {
/* 823 */       legendX = 380;
/*     */     }
/* 825 */     g2.setStroke(wideStroke);
/* 826 */     g2.drawLine(1, CollatzH - 58, 1, 80);
/* 827 */     g2.drawLine(0, 78, jfCollatz.getWidth(), 78);
/* 828 */     g2.drawLine(0, CollatzH - 57, jfCollatz.getWidth(), CollatzH - 57);
/*     */     
/* 830 */     float SyllablesPerNumber = (float)totalSyllablesDraw / testNumDraw;
/* 831 */     g2.drawString("^Average [" + SyllablesPerNumber + "]", 14.0F + (spacing + 1) * (SyllablesPerNumber - lowestSyllableCount), (CollatzH - 30));
/* 832 */     g2.setColor(Color.RED);
/* 833 */     g2.drawString("˅", 14 + (spacing + 1) * (hmDigits * 8 - lowestSyllableCount), CollatzH - 44);
/*     */     
/* 835 */     g2.setColor(Color.BLUE);
/* 836 */     g2.drawString("Processing " + hmDigits + " digit Numbers", legendX - 320, 33);
/* 837 */     g2.drawString("Completed " + testNumDraw + " of " + hmNumbers, legendX - 320, 55);
/*     */     
/* 839 */     g2.setColor(Color.BLACK);
/* 840 */     g2.drawString("Shortest Signature has " + lowestSyllableCount + " Syllables", legendX - 80, 22);
/* 841 */     g2.drawString("Average  Signature has " + (int)SyllablesPerNumber + " Syllables", legendX - 80, 44);
/* 842 */     g2.drawString("Longest  Signature has " + highestSyllableCount + " Syllables", legendX - 80, 66);
/*     */     
/* 844 */     g2.setColor(Color.BLUE);
/* 845 */     g2.drawString("Total Signature Syllables is " + totalSyllablesDraw, legendX + 200, 22);
/* 846 */     g2.drawString("Syllables per number = " + SyllablesPerNumber, legendX + 200, 44);
/* 847 */     g2.drawString("Syllables per digit = " + ((float)totalSyllablesDraw / testNumDraw / hmDigits), legendX + 200, 66);
/*     */     
/* 849 */     if (testNum % 1000 == 0) {
/* 850 */       profileRes = "Signature Syllable Profile.";
/* 851 */       for (i = 1, ssProfile = "O"; i < 1000; i++) {
/* 852 */         ssProfile += "E";
/* 853 */         if (cumSyllableLength[i] > 0L)
/* 854 */           profileRes += "\n" + ssProfile + " " + cumSyllableLength[i]; 
/*     */       } 
/* 856 */       profileRes += "\nTotal Signature Syllables is " + totalSyllablesDraw + "\n";
/*     */       
/* 858 */       Result = profileRes;
/*     */       
/* 860 */       if (longSignatures.length() == 0) {
/* 861 */         Result = Result + "\nNo long Signatures";
/*     */       } else {
/* 863 */         Result = Result + "\nLong Signatures\n" + longSignatures;
/*     */       } 
/* 865 */       Result = Result + biggestNumRes;
/*     */       
/* 867 */       jtaRes.setText(Result);
/*     */       
/* 869 */       buttonEnabled(true);
/*     */     } 
/*     */   }
/*     */   
/*     */   static void buttonEnabled(boolean enable) {
/* 874 */     if (enable) {
/* 875 */       jbClear.setText("Return to Manual Processing");
/* 876 */       jbClear.setEnabled(true);
/*     */     } else {
/*     */       
/* 879 */       jbClear.setText("Disabled");
/* 880 */       jbClear.setEnabled(false);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mustapha\Desktop\Crossword-Express.jar!\crosswordexpress\Hailstone.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */