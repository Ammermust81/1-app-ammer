/*      */ package crosswordexpress;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.DataInputStream;
/*      */ import java.io.DataOutputStream;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import javax.swing.ButtonGroup;
/*      */ import javax.swing.JButton;
/*      */ import javax.swing.JCheckBox;
/*      */ import javax.swing.JComboBox;
/*      */ import javax.swing.JDialog;
/*      */ import javax.swing.JFileChooser;
/*      */ import javax.swing.JFrame;
/*      */ import javax.swing.JLabel;
/*      */ import javax.swing.JMenuItem;
/*      */ import javax.swing.JOptionPane;
/*      */ import javax.swing.JPanel;
/*      */ import javax.swing.JScrollPane;
/*      */ import javax.swing.JTextField;
/*      */ import javax.swing.KeyStroke;
/*      */ 
/*      */ public final class DictionaryMtce {
/*      */   static JFrame jfDictionaryMtce;
/*   25 */   static JLabel jlChart = null; static JMenuBar menuBar; static JMenuBar editMenuBar; static JMenu menu; static JMenu submenu;
/*      */   static JMenuItem menuItem;
/*      */   static String dlDic;
/*      */   private static JProgressBar progressBar;
/*      */   static DefaultListModel<String> lmDic;
/*      */   static JList<String> jlDic;
/*      */   JScrollPane jspDic;
/*      */   static DefaultListModel<String> lmWebDic;
/*      */   static JList<String> jlWebDic;
/*      */   static JScrollPane jspWebDic;
/*   35 */   static int progressBarValue = 0;
/*      */   static int frameX;
/*      */   static int frameY;
/*      */   static JList<String> jlWord;
/*      */   static DefaultListModel<String> lmWord;
/*      */   static JScrollPane jspWord;
/*      */   static JTextField jtfWord;
/*      */   static JTextArea jtaClue;
/*   43 */   static JRadioButton[] jrbLenSpec = new JRadioButton[3]; static JRadioButton[] jrbCase = new JRadioButton[2];
/*   44 */   static JCheckBox[] jcbR2L = new JCheckBox[2];
/*      */   
/*      */   static JCheckBox jcbDev;
/*   47 */   static int editIndex = -1;
/*   48 */   static int wordsAdded = 0; static int cluesChanged = 0; static int wordsDeleted = 0; static final int WORD = 0;
/*      */   static final int UPDATE = 2;
/*   50 */   static byte[] dicHeader = new byte[128];
/*   51 */   static byte WORDLENGTH = 0;
/*   52 */   static byte CASE = 1;
/*   53 */   static byte AUXKB = 2;
/*   54 */   static byte ALPHABETNUM = 3;
/*   55 */   static byte OTHERALPHA = 4;
/*   56 */   static byte R2L_WORD = 5;
/*   57 */   static byte R2L_CLUE = 6;
/*   58 */   static byte DEVANAGARI = 7;
/*      */   
/*      */   static Timer clueTimer;
/*      */   static Timer listenerTimer;
/*   62 */   static Boolean inhibitListUpdate = Boolean.valueOf(false); static Boolean inhibitListener = Boolean.valueOf(false); static Boolean editWord = Boolean.valueOf(true); static Boolean focusWord = Boolean.valueOf(true);
/*   63 */   static String listWord = ""; static String listClue = ""; JLabel jlTotal; JLabel jlLockout; JLabel jlWith; JLabel jlWO; JLabel jlClues; JLabel jlTotalDat; JLabel jlLockoutDat; JLabel jlWithDat; JLabel jlWODat; JLabel jlCluesDat; static JLabel jlAdded; static JLabel jlChanged; static JLabel jlDeleted;
/*      */   static JLabel jlAddedScore;
/*      */   static JLabel jlChangedScore;
/*      */   static JLabel jlDeletedScore;
/*      */   static JLabel jlTotWords;
/*      */   static JLabel jlTotWordsScore;
/*      */   DefaultListModel<String> lmFirstLetter;
/*      */   DefaultListModel<String> lmLength;
/*      */   static JPanel kbp;
/*      */   static String path;
/*   73 */   static String[] alphabets = new String[] { "Latin", "Latin Extended A", "Latin Extended B", "Greek & Coptic", "Cyrillic", "Armenian/Hebrew", "Arabic" };
/*      */ 
/*      */   
/*   76 */   String dictionaryMtce = "<div>Crossword Express can support a virtually unlimited number of both <b>Standard</b> and <b>Theme</b> dictionaries. As the name implies, the contents of a <b>Theme</b> dictionary follow a particular theme such as Music, Sports, Science, Films etc. They will normally have a limited number of words (a few hundred at most), and their names must begin with the <b>$</b> character.<p/>When you enter the Dictionary Maintenance screen, the name of the current dictionary will be included in the title bar of the screen. The body of the screen contains a set of statistics describing the current dictionary.<br/><br/></div><span class='m'>Menu Functions</span><ul><li/><span class='s'>File Menu</span><ul><li/><span>Select a Dictionary</span><br/>Begin a Dictionary Maintenance session by selecting the dictionary which you intend to maintain. This is done using the <b>Select a Dictionary</b> option. <p/><li/><span>Quit Dictionary Maintenance</span><br/>When you have finished your Maintenance activities, this option will return you to the Crossword Express opening screen.</ul><li/><span class='s'>Edit Menu</span><br/>This menu contains the two Editing functions involved with Dictionary Maintenance:-<ul><li/><span>Edit Dictionary</span><br/>This option gives you access to the Dictionary Edit screen for the currently selected dictionary. To make proper use of the facilities provided by the Edit screen, you will certainly need to study the Help which it provides.<p/><li/><span>Edit Properties</span><br/>Crossword Express currently supports properties involving <b>Word Length</b> specification, <b>Word Case</b> preference, <b>Auxiliary Keyboard</b> control and <b>Right-to-Left word direction</b> control.</ul><li/><span class='s'>Tasks Menu</span><br/>This menu leads you to a set of useful Dictionary based functions:-<ul><li/><span>Create a Standard Dictionary</span><br/>This option will take you to a dialog which invites you to enter a name for your new Standard Dictionary. If you enter the name of an existing dictionary, you will receive a warning to this effect. Otherwise you will be automatically transferred to a dictionary edit screen where you can begin adding content to your new dictionary.<p/><li/><span>Create a Theme Dictionary</span><br/>This is similar to the <b>Create a Standard Dictionary</b> function, except that a Theme dictionary will be created. The names of such dictionaries always commence with a <b>$</b> character. If you forget to enter this character, the program will do it for you.<p/>Theme dictionaries are normally very limited in size (usually less than 100 words) and are used in the following two ways:-<ul><li/>As the source dictionary for a <b>Freeform Crossword</b>.<li/>As a <b>Theme Dictionary</b> when building a <b>Standard Crossword</b>. A <b>Standard Dictionary</b> must be specified as the source dictionary for such a puzzle, and Crossword Express insists that this happens.</ul><li/><span>Import and Merge a WordList</span><br/>A WordList file is a <b>Unicode (UTF-8)</b> file in which each line of text consists of one dictionary word, followed by one or more <b>Space</b> or <b>Tab</b> characters, followed by the clue. Such a file can be edited using a suitable text editor or word processor. In Windows, use <b>Notepad</b>, and in Macintosh, use <b>TextEdit</b>. If you do this, be VERY sure that when you save your editing, you do so in the <b>Unicode (UTF-8)</b> mode.<p/><br/>A WordList file can be Imported into a newly created (empty) dictionary, or it can be Merged with an existing dictionary. The WordList file need not be in alphabetical order, as this action will be taken care of by the program.<p/><li/><span>Import a Crossword Express Dictionary</span><br/>The <b>Import a Crossword Express Dictionary</b> option opens a File Chooser Dialog which allows you to select the <b>xword.dic</b> file that you want to import from a Crossword Express dictionary. The contents of the imported dictionary will be inserted into your current Crossword Express dictionary which should be a newly created and empty dictionary. If it is an existing dictionary, it will be overwritten by the imported words and clues. This function will only import those words which are already provided with clues.<p/><li/><span>Import Words from a Text Document</span><br/>This option allows you to very quickly and easily populate an empty Dictionary with a set of words. The words are derived from a text file stored anywhere on your hard drive. A very convenient and free source of such files on the Internet is the <b>Gutenberg project</b> which has over 40,000 books available for download. These books are available in many different languages, so it is a simple matter to create dictionaries in the language of your choice. The resulting dictionary of course will not contain clues, but when you build a Standard Crossword using such a dictionary, the Build function allows you to add clues directly to the puzzle. It also offers an option to be able to copy these clues from the puzzle into the dictionary. In this way, the dictionary will gradually acquire clues to all of its words.<p/><li/><span>Download a Dictionary</span><br/>This function gives you access to a library of dictionaries which are stored on servers at the Crossword Express web site. A Help button on the <b>Download a Dictionary</b> dialog will provide you with any assistance you may need in performing the download operation.<p/><li/><span>Export as WordList</span><br/>The entire contents of a dictionary can be exported to a WordList file located anywhere you choose on your hard drive.<p/><li/><span>Unlock Words</span><br/>If you make regular use of the <b>Lockout Words</b> function which is available on the Standard Crossword Construction screen, you will ultimately reach the point where Crossword Express will not have enough words available to it to reliably construct a puzzle. When you get to this point, you can place all of the locked out words back into service using the <b>Unlock Words</b> function.<p/><li/><span>Delete this Dictionary</span><br/>Any dictionary which is no longer required may be eliminated using the <b>Delete this Dictionary</b> option. This operation will remove both the dictionary and any puzzles which you may have constructed using it. This is a radical step to take, and naturally, a warning message is provided to give you a chance to change your mind.</ul></ul></body>";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  166 */   static String downloadDic = "<div>When the <b>Download a Dictionary</b> dialog opens, there is a short delay as Crossword Express obtains the complete list of available dictionaries. At present this consists of 30 bilingual dictionaries, the names of which take the form <b>eng-oth</b> or <b>oth-eng</b> where oth will be one of the following:- <b>dan, dut, fin, fre, ger, grk, hun, ita, nor, pol, por, rus, spa, swe or tur.</b> When these dictionary names appear in the selection panel, they will be accompanied by a number (eg. eng-spa : 38274) which tells you how many entries there are in that dictionary.<p>Click the name of the dictionary you want to download, and then click the Download button. If you already have a dictionary with that name you will receive a warning. Otherwise the download will commence immediately. Download progress is indicated by means of a progress bar, and when it is finished the <b>Download a Dictionary</b> dialog closes and you will be returned to the Dictionary Maintenance screen. The dictionary you just downloaded becomes the current dictionary, and you can begin editing it if you wish, or you can exit the Dictionary Maintenance screen, and immediately start using the new dictionary to make Crossword puzzles, or any of the other puzzles which require the use of a dictionary.</div></body>";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  181 */   static String createDicHelp = "<div>This function allows you to create a new Standard dictionary, and to immediately begin the task of entering words and clues into it. The dialog has the following input fields and controls:-</div><ul><li/><b>Dictionary Name</b> input field. Enter an appropriate name for the new dictionary. The only restriction is that the name must not start with the <b>$</b> character. If you do enter a <b>$</b>, the program will remove it.<p/><li/><b>Word Length Specification.</b> Crossword Express can generate a specification for the length of the word as you enter it into the Dictionary Edit function. The specification will automatically appear in the Clue input field when you press Enter after typing in the new Word. You can elect to not insert a length specification at all, you can have it entered for all words, or you can have it entered only for multi-words such as <b>RED HOT</b>. This becomes a <b>Property</b> of the dictionary, and can be changed anytime using the <b>Dictionary Properties</b> function.<p/><li/><b>Case Control.</b> When you enter a Word during Dictionary Editing, Crossword Express will convert that word to either all Uppercase Letters or all Lowercase letters. You can control this behaviour using the two radio buttons available in <b>Case Control.</b> This becomes a <b>Property</b> of the dictionary, and can be changed anytime using the <b>Dictionary Properties</b> function.<p/><li/><span>Auxiliary Keyboard.</span><br/>Crossword Express uses the Unicode character set which means that puzzles can be created in virtually any language. A typical computer keyboard caters for only a limited number of characters, and so a facility is required to enable the vast array of Unicode characters to be entered into a Crossword Express dictionary. This is done by means of an <b>Auxiliary Keyboard</b> which is an array of 256 characters selected from Unicode and displayed as part of the Dictionary Edit screen. When one of these characters is required in either a Word or a Clue, a single mouse click on the required character will enter that character into the dictionary at the current insertion point. This behaviour is selected separately for each dictionary, so that each dictionary may be associated with a different character set, or it may simply default to the one provided by the computer keyboard. It is controlled using the following three options:-<p/><ul><li/><span>Connect Keyboard.</span><br/>Select this check-box if you want an Auxiliary Keyboard to be connected.<p/><li/><span>Select Alphabet.</span><br/>This combo-box provides quick access to the seven most commonly used character sets. All seven of these character sets appear to be available in both iMac and Windows operating systems.<p/><li/><span>Other Alphabets.</span><br/>Enter a number between 1 and 255 into this text field to select any of the the other character pages which may or may not be available, depending on the OS you are using. Any non-zero number entered here will over-ride the selection made in the combo-box described above. You will probably prefer to use the more convenient combo-box selection, so you should enter a zero here.</ul><li/><span>Right to Left Control.</span><br/>Languages such as Arabic and Hebrew (plus a few other very exotic examples) are written in the Right to Left mode. When a Crossword Express dictionary is constructed using one of these languages, information must be encoded into the dictionary so that when Crossword Express is printing the puzzle it can take the necessary steps to print the clues and the puzzle grid with the words flowing in the correct direction. The two checkboxes labelled <b>Words are Right to Left</b> and <b>Clues are Right to Left</b> can be used to achieve this. Having a separate checkbox for each direction allows the possibility of a dictionary having different languages for the Words and the Clues, with one being Left to Right and the other being Right to Left.<p/><li/>Clicking the <b>OK</b> button after a legal name has been entered will automatically put you into a <b>Dictionary Edit</b> screen where you can begin to build your new dictionary.</ul></body>";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  230 */   static String createThemeDicHelp = "<div>This function allows you to create a new Theme dictionary, and to immediately begin the task of entering words and clues into it. The dialog has the following input fields and controls:-</div><ul><li/><b>Dictionary Name</b> input field. Enter an appropriate name for the new dictionary. The only restriction is that the name must start with the <b>$</b> character. If you omit the <b>$</b> character, the program will automatically insert it.<p/><li/><b>Word Length Specification.</b> Crossword Express can generate a specification for the length of the word as you enter it into the Dictionary Edit function. The specification will automatically appear in the Clue input field when you press Enter after typing in the new Word. You can elect to not insert a length specification at all, you can have it entered for all words, or you can have it entered only for multi-words such as <b>RED HOT</b>. This becomes a <b>Property</b> of the dictionary, and can be changed anytime using the <b>Dictionary Properties</b> function.<p/><li/><b>Case Control.</b> When you enter a Word during Dictionary Editing, Crossword Express will convert that word to either all Uppercase Letters or all Lowercase letters. You can control this behaviour using the two radio buttons available in <b>Case Control.</b> This becomes a <b>Property</b> of the dictionary, and can be changed anytime using the <b>Dictionary Properties</b> function.<p/><li/><span>Auxiliary Keyboard.</span><br/>Crossword Express uses the Unicode character set which means that puzzles can be created in virtually any language. A typical computer keyboard caters for only a limited number of characters, and so a facility is required to enable the vast array of Unicode characters to be entered into a Crossword Express dictionary. This is done by means of an <b>Auxiliary Keyboard</b> which is an array of 256 characters selected from Unicode and displayed as part of the Dictionary Edit screen. When one of these characters is required in either a Word or a Clue, a single mouse click on the required character will enter that character into the dictionary at the current insertion point. This behaviour is selected separately for each dictionary, so that each dictionary may be associated with a different character set, or it may simply default to the one provided by the computer keyboard. It is controlled using the following three options:-<p/><ul><li/><span>Connect Keyboard.</span><br/>Select this check-box if you want an Auxiliary Keyboard to be connected.<p/><li/><span>Select Alphabet.</span><br/>This combo-box provides quick access to the seven most commonly used character sets. All seven of these character sets appear to be available in both iMac and Windows operating systems.<p/><li/><span>Other Alphabets.</span><br/>Enter a number between 1 and 255 into this text field to select any of the the other character pages which may or may not be available, depending on the OS you are using. Any non-zero number entered here will over-ride the selection made in the combo-box described above. You will probably prefer to use the more convenient combo-box selection, so you should enter a zero here.</ul><li/><span>Right to Left Control.</span><br/>Languages such as Arabic and Hebrew (plus a few other very exotic examples) are written in the Right to Left mode. When a Crossword Express dictionary is constructed using one of these languages, information must be encoded into the dictionary so that when Crossword Express is printing the puzzle it can take the necessary steps to print the clues and the puzzle grid with the words flowing in the correct direction. The two checkboxes labelled <b>Words are Right to Left</b> and <b>Clues are Right to Left</b> can be used to achieve this. Having a separate checkbox for each direction allows the possibility of a dictionary having different languages for the Words and the Clues, with one being Left to Right and the other being Right to Left.<p/><li/>Clicking the <b>OK</b> button after a legal name has been entered will automatically put you into a <b>Dictionary Edit</b> screen where you can begin to build your new dictionary.</ul></body>";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  280 */   static String importTextOptions = "<div>This function allows you to add content to the current dictionary using words ged from a text file. The dialog which controls this function has the following controls:-</div><ul><li/><b>Select Import File.</b> This presents you with a Selection Dialog which you can use to select the Text file which may be located anywhere on your hard drive. Failure to select a file will subsequently result in a warning message.<p/><li/><b>Select Alphabet.</b> The program extracts words from the text file using the following three step process:-<ul><li/>Firstly select a group of characters which are delineated by white space (spaces, tabs, new lines, and carriage returns).<p/><li/>Secondly, remove from these groups any leading and trailing characters which are not members of the alphabet in which the text file is rendered. The resulting character group is a \"potential word\".<p/><li/>Finally, reject any \"potential word\" which contains characters which are not members of the above mentioned alphabet. Words which are not rejected are saved for inclusion in the dictionary after sorting into alphabetical order, and elimination of duplicates.</ul>The alphabet to be used in this process must be selected from the thirteen alphabets included in the drop-down list provided.<p/><li/><b>Edit the Alphabet.</b> The Alphabet that you select is displayed in an edit field. You have the opportunity to adjust this alphabet if it is not exactly what you require.<p/><li/>Clicking the <b>OK</b> button will result in one of three events:-<ul><li/>You will receive a warning message if you failed to select the Text File to be imported.<p/><li/>If the current dictionary already contains content, you will receive a warning that its content will be overwritten if you continue.<p/><li/>Otherwise the words wil be imported into the dictionary, and you will receive a message confirming this fact.</ul><li/>You can also click the <b>Cancel</b> button if you decide not to proceed with the importation.</ul></body>";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  311 */   static String dicProperties = "<div>The Properties of a Dictionary are normally set at the time of its creation. If you forget to do this, or if you want to alter a Property, this function will allow you to do so. The dialog has the following controls:-</div><ul><li/><span>Word Length Specification.</span><br/>Crossword Express can generate a specification for the length of the word as you enter it into the Dictionary Edit function. The specification will automatically appear in the Clue input field when you press Enter after typing in the new Word. You can elect to not insert a length specification at all, you can have it entered for all words, or you can have it entered only for multi-words such as <b>RED HERRING</b>. For <b>RED HERRING</b> the length specification will appear as (3,7). If the multi-word is <b>RED-HOT</b> then the length specification will be (3-3).<p/><li/><span>Case Control.</span><br/>When you enter a Word during Dictionary Editing, Crossword Express will convert that word to either all Uppercase Letters or all Lowercase letters. You can control this behaviour using the two radio buttons available under <b>Case Control.</b><p/><li/><span>Auxiliary Keyboard.</span><br/>Crossword Express uses the Unicode character set which means that puzzles can be created in virtually any language. A typical computer keyboard caters for only a limited number of characters, and so a facility is required to enable the vast array of Unicode characters to be entered into a Crossword Express dictionary. This is done by means of an <b>Auxiliary Keyboard</b> which is an array of 256 characters selected from Unicode and displayed as part of the Dictionary Edit screen. When one of these characters is required in either a Word or a Clue, a single mouse click on the required character will enter that character into the dictionary at the current insertion point. This behaviour is selected separately for each dictionary, so that each dictionary may be associated with a different character set, or it may simply default to the one provided by the computer keyboard. It is controlled using the following three options:-<p/><ul><li/><span>Connect Keyboard.</span><br/>Select this check-box if you want an Auxiliary Keyboard to be connected.<p/><li/><span>Select Alphabet.</span><br/>This combo-box provides quick access to the seven most commonly used character sets. All seven of these character sets appear to be available in both iMac and Windows operating systems.<p/><li/><span>Other Alphabets.</span><br/>Enter a number between 1 and 255 into this text field to select any of the the other character pages which may or may not be available, depending on the OS you are using. Any non-zero number entered here will over-ride the selection made in the combo-box described above. You will probably prefer to use the more convenient combo-box selection, so you should enter a zero here.</ul><li/><span>Right to Left Control.</span><br/>Languages such as Arabic and Hebrew (plus a few other very exotic examples) are written in the Right to Left mode. When a Crossword Express dictionary is constructed using one of these languages, information must be encoded into the dictionary so that when Crossword Express is printing the puzzle it can take the necessary steps to print the clues and the puzzle grid with the words flowing in the correct direction. The two checkboxes labelled <b>Words are Right to Left</b> and <b>Clues are Right to Left</b> can be used to achieve this. Having a separate checkbox for each direction allows the possibility of a dictionary having different languages for the Words and the Clues, with one being Left to Right and the other being Right to Left.</ul></body>";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  358 */   static String editDic = "<div>When you first enter the <span class=\"hi\">Edit Dictionary</span> dialog, there may be a short delay while the contents of the dictionary are loaded into memory.<p/>When the dictionary has loaded, a portion of the words and clues will be displayed in a list box in the top portion of the dialog.<p/>The edit cursor will be located in the <b>Edit Word</b> field ready for you to type in a new word.<br/><br/></div><span class='m'>Navigating the Dictionary</span><ul><li/>The simplest method of moving around the dictionary is to use the Scroll bar associated with the Words and Clues list box. When the word you are interested in appears, you can select it by pointing and clicking with the mouse.<p/><li/>The quickest way to get to a desired location in the dictionary is to type a word into the <b>Edit Word</b> field.<p/><li/>If the word happens to be already in the dictionary, its clue will be displayed, and you can edit that clue by typing Enter, or by clicking into the <b>Edit Clue</b> field.<p/><li/>If the word is not already in the dictionary, the <b>Edit Clue</b> field will be empty, but you can enter a new clue by typing Enter, or by clicking into the <b>Edit Clue</b> field.<p/></ul><span class='m'>Adding and Deleting words</span><ul><li/><span>Adding Words</span><br/>After typing a new Word into the <b>Edit Word</b> field, pressing the <b>Enter</b> key will move the cursor to the <b>Edit Clue</b> field where you can type in your Clue. When the clue is entered, pressing the <b>Enter</b> key again will save the new Word and Clue into their correct alphabetical position in the Words and Clues list box, and the cursor will be moved back to the <b>Edit Word</b> field so that the cycle can begin again. As with any editing function, it is recommended that you save your work at regular intervals.<p/><li/><span>Deleting Words</span><br/>Selecting the <b>Edit / Delete this Word</b> menu option, or clicking the <b>Delete Current Word</b> button will remove the current word and its clue from the edit screen. It will be deleted from the dictionary the next time you do a Save operation.<p/><li/><span>Editing Clues</span><br/> The number of characters which may be typed into each clue is virtually unlimited. Thus, you can use very long clues in your puzzles, or if you prefer, you can enter a number of clues for each word. The individual sub-clues must be separated by a single <b>*</b> character.<p/><li/><span>Hi-lighting Clues</span><br/>Individual portions of a clue may be highlighted as either <b>Bold</b> or <i>Italic.</i> This is done by placing tags into the clue in a way which will be quite familiar to people who maintain their own web page using <b>HTML.</b> Inclusion of the tag <b>&lt;b&gt;</b> at any point in the clue will result in the text being rendered in <b>Bold</b> type face until such time as a <b>&lt;/b&gt;</b> tag is encountered. Similarly, <b>&lt;i&gt;</b> will turn on <i>Italic</i> type face, and <b>&lt;/i&gt;</b> will turn it off.<p/><li/><span>Select All, Copy, Cut and Paste commands</span><br/>All of these operations are fully supported during Dictionary Editing, and can be executed in the following ways:-<ul><li>Choose from the Select All, Cut, Copy and Paste options of the Edit menu.<li>Click the required button from the Select All, Cut, Copy and Paste buttons.<li>Use the shortcut keyboard commands Ctrl-A, Ctrl-X, Ctrl-C and Ctrl-V on either Windows or Mac computers.<li>Use the shortcut keyboard commands Cmd-A, Cmd-X, Cmd-C and Cmd-V on Mac computers.</ul><li/><span>Entering Clueless Words</span><br/>If you fail to enter a clue for a word, that word will still be saved into the dictionary, and become a Clueless Word. If you do this by mistake, it is a simple matter to immediately delete the unwanted word.<p/><li/><span>Entering 'Special' Unicode characters</span><br/>Any character of the Unicode specification may be used in a Crossword Express dictionary. This is done by attaching an <b>Auxiliary Keyboard</b> to the Dictionary Edit screen. Simply clicking a character within such a keyboard will insert that character at the current insertion point.<p/>Complete control of the auxiliary keyboard is possible from within the dictionary edit screen by means of the following controls:-<br/><ul><li/>The Auxiliary Keyboard can be added or removed at any time by means of the <b>Attach Auxiliary Keyboard</b> and <b>Detach Auxiliary Keyboard</b> menu options available from the <b>Task</b> menu, or by using the similarly named buttons.<p/><li/>The actual keyboard you use can be controlled using the <b>Select Keyboard</b> spinner control immediately above the top right corner of the auxiliary keyboard. You can step through the keyboards one at a time using the up and down arrows of the spinner control or if you know the number of the keyboard you want, you can click into the edit field, type it in directly, and then press Enter.<p/></ul></ul><span class='m'>Saving the changes</span><ul><li/>To avoid the frustration of losing your work in a power failure, you should perform a save operation at regular intervals. Mostly you will use the <b>File / Save and Resume</b> option which allows you to continue editing after the save.<li/>If you are done with editing, you can use the <b>File / Save and Quit</b> option which results in the Edit Dictionary dialog being closed after the save is completed.<li/>If you want to finish editing without saving your changes, then you should use the <b>File / Quit Editing</b> option. Note that a warning will be given in this case if changes have been made.</ul></body>";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   DictionaryMtce(JFrame jfCWE) {
/*  435 */     Def.puzzleMode = 3;
/*      */     
/*  437 */     jfDictionaryMtce = new JFrame("Maintain Dictionary : " + Op.msc[Op.MSC.DictionaryName.ordinal()]);
/*  438 */     jfDictionaryMtce.setDefaultCloseOperation(0);
/*  439 */     jfDictionaryMtce.setVisible(true);
/*  440 */     jfDictionaryMtce.setResizable(false);
/*  441 */     Def.insets = jfDictionaryMtce.getInsets();
/*  442 */     jfDictionaryMtce.setSize(460 + Def.insets.left + Def.insets.right, 505 + Def.insets.bottom);
/*      */     
/*  444 */     final int frameX = (jfCWE.getX() + jfDictionaryMtce.getWidth() > Methods.scrW) ? (Methods.scrW - jfDictionaryMtce.getWidth() - 10) : jfCWE.getX();
/*  445 */     frameY = jfCWE.getY();
/*  446 */     jfDictionaryMtce.setLocation(frameX, frameY);
/*  447 */     jfDictionaryMtce.setLayout((LayoutManager)null);
/*      */     
/*  449 */     jfDictionaryMtce
/*  450 */       .addComponentListener(new ComponentAdapter() {
/*      */           public void componentShown(ComponentEvent ce) {
/*  452 */             DictionaryMtce.jfDictionaryMtce.setLocation(frameX, DictionaryMtce.frameY + 1);
/*      */           }
/*      */         });
/*      */     
/*  456 */     jfDictionaryMtce
/*  457 */       .addWindowListener(new WindowAdapter() {
/*      */           public void windowClosing(WindowEvent we) {
/*  459 */             Op.saveOptions("miscellaneous.opt", Op.msc);
/*  460 */             CrosswordExpress.jfCWE.setVisible(true);
/*  461 */             DictionaryMtce.jfDictionaryMtce.dispose();
/*  462 */             Methods.closeHelp();
/*  463 */             Def.puzzleMode = 1;
/*      */           }
/*      */         });
/*      */     
/*  467 */     Methods.closeHelp();
/*      */ 
/*      */     
/*  470 */     menuBar = new JMenuBar();
/*  471 */     menuBar.setBackground(Def.COLOR_MENUBAR);
/*  472 */     jfDictionaryMtce.setJMenuBar(menuBar);
/*      */     
/*  474 */     menu = new JMenu("File");
/*  475 */     menuBar.add(menu);
/*  476 */     menuItem = new JMenuItem("Select a Dictionary");
/*  477 */     menuItem.setAccelerator(KeyStroke.getKeyStroke(68, 2));
/*  478 */     menu.add(menuItem);
/*  479 */     menuItem
/*  480 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           Methods.selectDictionary(jfDictionaryMtce, Op.msc[Op.MSC.DictionaryName.ordinal()], 3);
/*      */           Op.msc[Op.MSC.DictionaryName.ordinal()] = Methods.dictionaryName;
/*      */           jfDictionaryMtce.setTitle("Maintain Dictionary : " + Op.msc[Op.MSC.DictionaryName.ordinal()]);
/*      */           jlDic.setSelectedValue(" " + Op.msc[Op.MSC.DictionaryName.ordinal()], true);
/*      */           dictionaryStats();
/*      */         });
/*  490 */     menuItem = new JMenuItem("Quit Dictionary Maintenance");
/*  491 */     menuItem.setAccelerator(KeyStroke.getKeyStroke(81, 2));
/*  492 */     menu.add(menuItem);
/*  493 */     menuItem
/*  494 */       .addActionListener(ae -> {
/*      */           Op.saveOptions("miscellaneous.opt", Op.msc);
/*      */           
/*      */           CrosswordExpress.jfCWE.setVisible(true);
/*      */           
/*      */           jfDictionaryMtce.dispose();
/*      */           
/*      */           Methods.closeHelp();
/*      */           Def.puzzleMode = 1;
/*      */         });
/*  504 */     menu = new JMenu("Edit");
/*  505 */     menuBar.add(menu);
/*  506 */     menuItem = new JMenuItem("Edit Dictionary");
/*  507 */     menu.add(menuItem);
/*  508 */     menuItem.setAccelerator(KeyStroke.getKeyStroke(69, 2));
/*  509 */     menuItem
/*  510 */       .addActionListener(ae -> {
/*      */           editDictionary(jfDictionaryMtce, Op.msc[Op.MSC.DictionaryName.ordinal()]);
/*      */ 
/*      */           
/*      */           dictionaryStats();
/*      */         });
/*      */     
/*  517 */     menuItem = new JMenuItem("Edit Properties");
/*  518 */     menu.add(menuItem);
/*  519 */     menuItem.setAccelerator(KeyStroke.getKeyStroke(80, 2));
/*  520 */     menuItem
/*  521 */       .addActionListener(ae -> {
/*      */           dictionaryProperties();
/*      */ 
/*      */           
/*      */           dictionaryStats();
/*      */         });
/*      */     
/*  528 */     menu = new JMenu("Tasks");
/*  529 */     menuBar.add(menu);
/*  530 */     menuItem = new JMenuItem("Create a Standard Dictionary");
/*  531 */     menu.add(menuItem);
/*  532 */     menuItem.setAccelerator(KeyStroke.getKeyStroke(83, 2));
/*  533 */     menuItem
/*  534 */       .addActionListener(ae -> {
/*      */           createDic(jfDictionaryMtce, 0);
/*      */           
/*      */           if (Methods.clickedOK) {
/*      */             jfDictionaryMtce.setTitle("Maintain Dictionary : " + Op.msc[Op.MSC.DictionaryName.ordinal()]);
/*      */             
/*      */             editDictionary(jfDictionaryMtce, Op.msc[Op.MSC.DictionaryName.ordinal()]);
/*      */             dictionaryStats();
/*      */           } 
/*      */         });
/*  544 */     menuItem = new JMenuItem("Create a Theme Dictionary");
/*  545 */     menu.add(menuItem);
/*  546 */     menuItem.setAccelerator(KeyStroke.getKeyStroke(84, 2));
/*  547 */     menuItem
/*  548 */       .addActionListener(ae -> {
/*      */           createDic(jfDictionaryMtce, 1);
/*      */           
/*      */           if (Methods.clickedOK) {
/*      */             jfDictionaryMtce.setTitle("Maintain Dictionary : " + Op.msc[Op.MSC.DictionaryName.ordinal()]);
/*      */             editDictionary(jfDictionaryMtce, Op.msc[Op.MSC.DictionaryName.ordinal()]);
/*      */             dictionaryStats();
/*      */           } 
/*      */         });
/*  557 */     menu.addSeparator();
/*      */     
/*  559 */     menuItem = new JMenuItem("Import and Merge a WordList");
/*  560 */     menu.add(menuItem);
/*  561 */     menuItem.setAccelerator(KeyStroke.getKeyStroke(87, 2));
/*  562 */     menuItem
/*  563 */       .addActionListener(ae -> importWordList());
/*      */ 
/*      */ 
/*      */     
/*  567 */     menuItem = new JMenuItem("Import a Crossword Express Dictionary");
/*  568 */     menu.add(menuItem);
/*  569 */     menuItem.setAccelerator(KeyStroke.getKeyStroke(77, 2));
/*  570 */     menuItem
/*  571 */       .addActionListener(ae -> importCWEdic());
/*      */ 
/*      */ 
/*      */     
/*  575 */     menuItem = new JMenuItem("Import Words from Text Document");
/*  576 */     menu.add(menuItem);
/*  577 */     menuItem.setAccelerator(KeyStroke.getKeyStroke(73, 2));
/*  578 */     menuItem
/*  579 */       .addActionListener(ae -> importText());
/*      */ 
/*      */ 
/*      */     
/*  583 */     menu.addSeparator();
/*      */     
/*  585 */     menuItem = new JMenuItem("Download Dictionary");
/*  586 */     menu.add(menuItem);
/*  587 */     menuItem.setAccelerator(KeyStroke.getKeyStroke(89, 2));
/*  588 */     menuItem
/*  589 */       .addActionListener(ae -> {
/*      */           CrosswordExpress.networkStatus("access.txt");
/*      */           
/*      */           if (!CrosswordExpress.networkOn) {
/*      */             Methods.networkError(jfDictionaryMtce);
/*      */           } else {
/*      */             downloadDic(jfDictionaryMtce);
/*      */             dictionaryStats();
/*      */           } 
/*      */         });
/*  599 */     menu.addSeparator();
/*      */     
/*  601 */     menuItem = new JMenuItem("Export as WordList");
/*  602 */     menu.add(menuItem);
/*  603 */     menuItem.setAccelerator(KeyStroke.getKeyStroke(88, 2));
/*  604 */     menuItem
/*  605 */       .addActionListener(ae -> exportWordList());
/*      */ 
/*      */ 
/*      */     
/*  609 */     menuItem = new JMenuItem("Unlock Words");
/*  610 */     menu.add(menuItem);
/*  611 */     menuItem.setAccelerator(KeyStroke.getKeyStroke(85, 2));
/*  612 */     menuItem
/*  613 */       .addActionListener(ae -> unlockWords());
/*      */ 
/*      */ 
/*      */     
/*  617 */     menuItem = new JMenuItem("Delete this Dictionary");
/*  618 */     menu.add(menuItem);
/*  619 */     menuItem.setAccelerator(KeyStroke.getKeyStroke(76, 2));
/*  620 */     menuItem
/*  621 */       .addActionListener(ae -> {
/*      */           deleteDic("");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           dictionaryStats();
/*      */         });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  636 */     menu = new JMenu("Help");
/*  637 */     menuBar.add(menu);
/*  638 */     menuItem = new JMenuItem("Dictionary Maintenance Help");
/*  639 */     menu.add(menuItem);
/*  640 */     menuItem.setAccelerator(KeyStroke.getKeyStroke(72, 2));
/*  641 */     menuItem
/*  642 */       .addActionListener(ae -> Methods.cweHelp(jfDictionaryMtce, null, "Dictionary Maintenance", this.dictionaryMtce));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  650 */     JLabel jl1 = new JLabel("<html><font size=4>Total Words : "); jl1.setSize(170, 16); jl1.setLocation(110, 5); jl1.setHorizontalAlignment(4); jfDictionaryMtce.add(jl1);
/*  651 */     jl1 = new JLabel("<html><font size=4>Locked Words : "); jl1.setSize(170, 16); jl1.setLocation(110, 23); jl1.setHorizontalAlignment(4); jfDictionaryMtce.add(jl1);
/*  652 */     jl1 = new JLabel("<html><font size=4>Words with Clues : "); jl1.setSize(170, 16); jl1.setLocation(110, 41); jl1.setHorizontalAlignment(4); jfDictionaryMtce.add(jl1);
/*  653 */     jl1 = new JLabel("<html><font size=4>Words without Clues : "); jl1.setSize(170, 16); jl1.setLocation(110, 59); jl1.setHorizontalAlignment(4); jfDictionaryMtce.add(jl1);
/*  654 */     jl1 = new JLabel("<html><font size=4>Total Clues : "); jl1.setSize(170, 16); jl1.setLocation(110, 77); jl1.setHorizontalAlignment(4); jfDictionaryMtce.add(jl1);
/*      */     
/*  656 */     this.jlTotalDat = new JLabel(""); this.jlTotalDat.setSize(55, 16); this.jlTotalDat.setLocation(285, 5); this.jlTotalDat.setHorizontalAlignment(4); jfDictionaryMtce.add(this.jlTotalDat);
/*  657 */     this.jlLockoutDat = new JLabel(""); this.jlLockoutDat.setSize(55, 16); this.jlLockoutDat.setLocation(285, 23); this.jlLockoutDat.setHorizontalAlignment(4); jfDictionaryMtce.add(this.jlLockoutDat);
/*  658 */     this.jlWithDat = new JLabel(""); this.jlWithDat.setSize(55, 16); this.jlWithDat.setLocation(285, 41); this.jlWithDat.setHorizontalAlignment(4); jfDictionaryMtce.add(this.jlWithDat);
/*  659 */     this.jlWODat = new JLabel(""); this.jlWODat.setSize(55, 16); this.jlWODat.setLocation(285, 59); this.jlWODat.setHorizontalAlignment(4); jfDictionaryMtce.add(this.jlWODat);
/*  660 */     this.jlCluesDat = new JLabel(""); this.jlCluesDat.setSize(55, 16); this.jlCluesDat.setLocation(285, 77); this.jlCluesDat.setHorizontalAlignment(4); jfDictionaryMtce.add(this.jlCluesDat);
/*      */     
/*  662 */     lmDic = new DefaultListModel<>();
/*  663 */     jlDic = new JList<>(lmDic);
/*  664 */     jlDic.setFont(new Font("SansSerif", 1, 14));
/*  665 */     this.jspDic = new JScrollPane(jlDic);
/*  666 */     this.jspDic.setSize(180, 328);
/*  667 */     this.jspDic.setLocation(10, 120);
/*  668 */     this.jspDic.setBorder(BorderFactory.createLineBorder(new Color(8912896), 2));
/*  669 */     this.jspDic.setHorizontalScrollBarPolicy(31);
/*  670 */     jfDictionaryMtce.add(this.jspDic);
/*      */     
/*  672 */     File fl = new File(System.getProperty("user.dir"));
/*  673 */     String[] s = fl.list();
/*  674 */     for (int i = 0; i < s.length; i++) {
/*  675 */       if (s[i].endsWith(".dic") && !s[i].startsWith(".")) {
/*  676 */         s[i] = s[i].substring(0, s[i].lastIndexOf('.'));
/*  677 */         lmDic.addElement(" " + s[i]);
/*      */       } 
/*  679 */     }  jlDic.setSelectedValue(" " + Op.msc[Op.MSC.DictionaryName.ordinal()], true);
/*  680 */     jlDic
/*  681 */       .addListSelectionListener(le -> {
/*      */           if (!jlDic.getValueIsAdjusting()) {
/*      */             Op.msc[Op.MSC.DictionaryName.ordinal()] = ((String)jlDic.getSelectedValue()).trim();
/*      */             
/*      */             jfDictionaryMtce.setTitle("Maintain Dictionary : " + Op.msc[Op.MSC.DictionaryName.ordinal()]);
/*      */             
/*      */             dictionaryStats();
/*      */           } 
/*      */         });
/*  690 */     jl1 = new JLabel("<html><font size=4>Select a Dictionary"); jl1.setSize(180, 16); jl1.setLocation(10, 100); jl1.setHorizontalAlignment(4); jfDictionaryMtce.add(jl1);
/*  691 */     jl1 = new JLabel("<html><font size=4>By Length"); jl1.setSize(120, 16); jl1.setLocation(200, 100); jl1.setHorizontalAlignment(4); jfDictionaryMtce.add(jl1);
/*  692 */     jl1 = new JLabel("<html><font size=4>By First Letter"); jl1.setSize(120, 16); jl1.setLocation(330, 100); jl1.setHorizontalAlignment(4); jfDictionaryMtce.add(jl1);
/*      */     
/*  694 */     this.lmLength = new DefaultListModel<>();
/*  695 */     JList<String> jl = new JList<>(this.lmLength);
/*  696 */     jl.setFont(new Font("Monospaced", 0, 14));
/*  697 */     JScrollPane jsp1 = new JScrollPane(jl);
/*  698 */     jsp1.setSize(120, 328);
/*  699 */     jsp1.setLocation(200, 120);
/*  700 */     jsp1.setBorder(BorderFactory.createLineBorder(new Color(8912896), 2));
/*  701 */     jfDictionaryMtce.add(jsp1);
/*      */     
/*  703 */     this.lmFirstLetter = new DefaultListModel<>();
/*  704 */     JList<String> jl5 = new JList<>(this.lmFirstLetter);
/*  705 */     jl5.setFont(new Font("Monospaced", 0, 14));
/*  706 */     JScrollPane jsp2 = new JScrollPane(jl5);
/*  707 */     jsp2.setSize(120, 328);
/*  708 */     jsp2.setLocation(330, 120);
/*  709 */     jsp2.setBorder(BorderFactory.createLineBorder(new Color(8912896), 2));
/*  710 */     jfDictionaryMtce.add(jsp2);
/*      */     
/*  712 */     dictionaryStats();
/*      */     
/*  714 */     jfCWE.setVisible(false);
/*  715 */     jfDictionaryMtce.setVisible(true);
/*  716 */     jfDictionaryMtce.setResizable(false);
/*      */   }
/*      */   
/*      */   static void downloadDic(JFrame jfParent) {
/*  720 */     JDialog jdlgSelDic = new JDialog(jfParent, "Download a Dictionary", true);
/*  721 */     jdlgSelDic.setSize(240, 450);
/*  722 */     jdlgSelDic.setResizable(false);
/*  723 */     jdlgSelDic.setLayout((LayoutManager)null);
/*  724 */     jdlgSelDic.setLocation(jfParent.getX(), jfParent.getY());
/*      */     
/*  726 */     jdlgSelDic
/*  727 */       .addWindowListener(new WindowAdapter() {
/*      */           public void windowClosing(WindowEvent we) {
/*  729 */             Methods.closeHelp();
/*      */           }
/*      */         });
/*      */     
/*  733 */     Methods.closeHelp();
/*      */     
/*  735 */     lmWebDic = new DefaultListModel<>();
/*  736 */     jlWebDic = new JList<>(lmWebDic);
/*  737 */     jlWebDic.setFont(new Font("Courier", 0, 14));
/*  738 */     jspWebDic = new JScrollPane(jlWebDic);
/*  739 */     jspWebDic.setSize(220, 296);
/*  740 */     jspWebDic.setLocation(10, 10);
/*  741 */     jspWebDic.setBorder(BorderFactory.createLineBorder(new Color(8912896), 2));
/*  742 */     jspWebDic.setHorizontalScrollBarPolicy(31);
/*  743 */     jdlgSelDic.add(jspWebDic);
/*  744 */     lmWebDic.addElement("<html><font color='blue'>Getting Dictionary Names");
/*      */     
/*  746 */     progressBar = new JProgressBar(0, 100);
/*  747 */     progressBar.setLocation(10, 316);
/*  748 */     progressBar.setSize(220, 25);
/*  749 */     progressBar.setValue(0);
/*  750 */     progressBar.setStringPainted(true);
/*  751 */     jdlgSelDic.add(progressBar);
/*      */     
/*  753 */     Runnable loadDics = () -> {
/*      */         
/*      */         try { URL url = new URL("http://www.crauswords.com/dictionaries/dicindex.txt");
/*      */           
/*      */           InputStreamReader isr = new InputStreamReader(url.openStream());
/*      */           
/*      */           BufferedReader in = new BufferedReader(isr);
/*      */           
/*      */           lmWebDic.remove(0);
/*      */           String s;
/*      */           while ((s = in.readLine()) != null) {
/*      */             lmWebDic.addElement(" " + s);
/*      */           }
/*      */           in.close();
/*      */           isr.close();
/*      */           jlWebDic.setSelectedIndex(0); }
/*  769 */         catch (MalformedURLException mal) {  }
/*  770 */         catch (IOException io) {}
/*      */       };
/*  772 */     (new Thread(loadDics)).start();
/*      */     
/*  774 */     JButton jbDownload = Methods.cweButton("Download", 10, 351, 125, 26, null);
/*  775 */     jbDownload.addActionListener(e -> {
/*      */           dlDic = jlWebDic.getSelectedValue();
/*      */           dlDic = dlDic.substring(1, dlDic.indexOf(" : "));
/*      */           File fl = new File(System.getProperty("user.dir"));
/*      */           String[] s = fl.list();
/*      */           for (String item : s) {
/*      */             if (item.endsWith(".dic") && !item.startsWith(".") && item.substring(0, item.indexOf('.')).equalsIgnoreCase(dlDic) && JOptionPane.showConfirmDialog(CrosswordBuild.jfCrossword, "You already have a dictionary with that name.Continue anyway?", "Warning", 0) == 1) {
/*      */               return;
/*      */             }
/*      */           } 
/*      */           File fDic = new File(dlDic + ".dic");
/*      */           if (!fDic.exists()) {
/*      */             fDic.mkdir();
/*      */             for (int i = 0; i < lmDic.size(); i++) {
/*      */               if (((String)lmDic.get(i)).compareTo(" " + dlDic) > 0) {
/*      */                 lmDic.add(i, " " + dlDic);
/*      */                 break;
/*      */               } 
/*      */             } 
/*      */           } else {
/*      */             (new File(dlDic + ".dic/xword.dic")).delete();
/*      */           } 
/*      */           jlDic.setSelectedValue(" " + dlDic, true);
/*      */           Op.msc[Op.MSC.DictionaryName.ordinal()] = dlDic;
/*      */           (new MySwingWorker(progressBar, paramJDialog)).execute();
/*      */           Methods.closeHelp();
/*      */         });
/*  802 */     jdlgSelDic.add(jbDownload);
/*      */     
/*  804 */     JButton jbCancel = Methods.cweButton("Cancel", 145, 351, 85, 26, null);
/*  805 */     jbCancel.addActionListener(e -> {
/*      */           paramJDialog.dispose();
/*      */           Methods.closeHelp();
/*      */         });
/*  809 */     jdlgSelDic.add(jbCancel);
/*      */     
/*  811 */     JButton jbHelp = Methods.cweButton("<html><font size=6 color=BB0000 face=Serif>Help ", 10, 386, 220, 61, new ImageIcon("graphics/help.png"));
/*  812 */     jbHelp.addActionListener(e -> Methods.cweHelp(null, paramJDialog, "Download a Dictionary", downloadDic));
/*      */     
/*  814 */     jdlgSelDic.add(jbHelp);
/*      */     
/*  816 */     Methods.setDialogSize(jdlgSelDic, 240, 455);
/*      */   }
/*      */   
/*      */   private static class MySwingWorker extends SwingWorker<String, Double> { private final JProgressBar fProgressBar;
/*      */     private final JDialog parentDlg;
/*      */     
/*      */     private MySwingWorker(JProgressBar aProgressBar, JDialog theDialog) {
/*  823 */       this.fProgressBar = aProgressBar;
/*  824 */       this.parentDlg = theDialog;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected String doInBackground() throws Exception {
/*      */       
/*  832 */       try { int count = 0;
/*  833 */         URL url = new URL("http://www.crauswords.com/dictionaries/" + DictionaryMtce.dlDic + ".dic/xword.dic");
/*  834 */         HttpURLConnection conn = (HttpURLConnection)url.openConnection();
/*  835 */         conn.setRequestMethod("GET");
/*  836 */         conn.getInputStream();
/*  837 */         int sz = conn.getContentLength();
/*  838 */         conn.disconnect();
/*  839 */         InputStream input = url.openConnection().getInputStream();
/*  840 */         byte[] buffer = new byte[4096];
/*      */         
/*  842 */         OutputStream output = new FileOutputStream(DictionaryMtce.dlDic + ".dic/xword.dic");
/*      */         int i;
/*  844 */         while ((i = input.read(buffer)) != -1) {
/*  845 */           count += i;
/*  846 */           output.write(buffer, 0, i);
/*  847 */           publish(new Double[] { Double.valueOf((count * 100) / sz) });
/*      */         } 
/*      */         
/*  850 */         output.close();
/*  851 */         input.close();
/*  852 */         conn.setConnectTimeout(2); }
/*      */       
/*  854 */       catch (MalformedURLException mal) {  }
/*  855 */       catch (IOException io) {}
/*  856 */       return "Finished";
/*      */     }
/*      */     
/*      */     protected void process(List<Double> aDoubles) {
/*  860 */       this.fProgressBar.setValue((int)(0.0D + ((Double)aDoubles.get(aDoubles.size() - 1)).doubleValue()));
/*      */     }
/*      */     protected void done() {
/*  863 */       this.parentDlg.dispose();
/*      */     } }
/*      */   
/*      */   static void createDic(JFrame jfParent, int dicType) {
/*  867 */     JDialog jdlgCreateDic = new JDialog(jfParent, (dicType == 0) ? "Create a new Dictionary" : "Create a Theme Dictionary", true);
/*  868 */     jdlgCreateDic.setSize(295, 475);
/*  869 */     jdlgCreateDic.setResizable(false);
/*  870 */     jdlgCreateDic.setLayout((LayoutManager)null);
/*  871 */     jdlgCreateDic.setLocation(jfParent.getX(), jfParent.getY());
/*      */     
/*  873 */     jdlgCreateDic
/*  874 */       .addWindowListener(new WindowAdapter() {
/*      */           public void windowClosing(WindowEvent we) {
/*  876 */             Methods.closeHelp();
/*      */           }
/*      */         });
/*      */     
/*  880 */     Methods.closeHelp();
/*      */     
/*  882 */     JLabel jlDicName = new JLabel("Dictionary Name:");
/*  883 */     jlDicName.setForeground(Def.COLOR_LABEL);
/*  884 */     jlDicName.setSize(125, 20);
/*  885 */     jlDicName.setLocation(10, 5);
/*  886 */     jlDicName.setHorizontalAlignment(4);
/*  887 */     jdlgCreateDic.add(jlDicName);
/*      */     
/*  889 */     JTextField jtfDictionaryName = new JTextField("untitled", 15);
/*  890 */     jtfDictionaryName.setSize(125, 20);
/*  891 */     jtfDictionaryName.setLocation(140, 5);
/*  892 */     jtfDictionaryName.selectAll();
/*  893 */     jtfDictionaryName.setHorizontalAlignment(2);
/*  894 */     jdlgCreateDic.add(jtfDictionaryName);
/*      */     
/*  896 */     JPanel jpWLS = new JPanel();
/*  897 */     jpWLS.setLayout((LayoutManager)null);
/*  898 */     jpWLS.setSize(275, 90);
/*  899 */     jpWLS.setLocation(10, 30);
/*  900 */     jpWLS.setOpaque(true);
/*  901 */     jpWLS.setBorder(BorderFactory.createEtchedBorder());
/*  902 */     jdlgCreateDic.add(jpWLS);
/*      */     
/*  904 */     JLabel jl = new JLabel("Word Length Specification");
/*  905 */     jl.setForeground(Def.COLOR_LABEL);
/*  906 */     jl.setSize(250, 20);
/*  907 */     jl.setLocation(5, 5);
/*  908 */     jl.setHorizontalAlignment(2);
/*  909 */     jpWLS.add(jl);
/*      */     
/*  911 */     ButtonGroup bgLenSpec = new ButtonGroup();
/*  912 */     jrbLenSpec[0] = new JRadioButton();
/*  913 */     jrbLenSpec[0].setForeground(Def.COLOR_LABEL);
/*  914 */     jrbLenSpec[0].setText("Don't insert word lengths");
/*  915 */     jrbLenSpec[0].setSelected(true);
/*  916 */     jrbLenSpec[0].setOpaque(false);
/*  917 */     jrbLenSpec[0].setSize(245, 20);
/*  918 */     jrbLenSpec[0].setLocation(5, 25);
/*      */     
/*  920 */     jpWLS.add(jrbLenSpec[0]);
/*  921 */     if (bgLenSpec != null) bgLenSpec.add(jrbLenSpec[0]);
/*      */     
/*  923 */     jrbLenSpec[1] = new JRadioButton();
/*  924 */     jrbLenSpec[1].setForeground(Def.COLOR_LABEL);
/*  925 */     jrbLenSpec[1].setText("Insert all word lengths");
/*  926 */     jrbLenSpec[1].setOpaque(false);
/*  927 */     jrbLenSpec[1].setSize(245, 20);
/*  928 */     jrbLenSpec[1].setLocation(5, 45);
/*  929 */     jpWLS.add(jrbLenSpec[1]);
/*  930 */     if (bgLenSpec != null) bgLenSpec.add(jrbLenSpec[1]);
/*      */     
/*  932 */     jrbLenSpec[2] = new JRadioButton();
/*  933 */     jrbLenSpec[2].setForeground(Def.COLOR_LABEL);
/*  934 */     jrbLenSpec[2].setText("Insert only multi-word lengths");
/*  935 */     jrbLenSpec[2].setOpaque(false);
/*  936 */     jrbLenSpec[2].setSize(245, 20);
/*  937 */     jrbLenSpec[2].setLocation(5, 65);
/*  938 */     jpWLS.add(jrbLenSpec[2]);
/*  939 */     if (bgLenSpec != null) bgLenSpec.add(jrbLenSpec[2]);
/*      */     
/*  941 */     JPanel jpCase = new JPanel();
/*  942 */     jpCase.setLayout((LayoutManager)null);
/*  943 */     jpCase.setSize(275, 70);
/*  944 */     jpCase.setLocation(10, 127);
/*  945 */     jpCase.setOpaque(true);
/*  946 */     jpCase.setBorder(BorderFactory.createEtchedBorder());
/*  947 */     jdlgCreateDic.add(jpCase);
/*      */     
/*  949 */     jl = new JLabel("Case Control");
/*  950 */     jl.setForeground(Def.COLOR_LABEL);
/*  951 */     jl.setSize(250, 20);
/*  952 */     jl.setLocation(5, 5);
/*  953 */     jl.setHorizontalAlignment(2);
/*  954 */     jpCase.add(jl);
/*      */     
/*  956 */     ButtonGroup bgCase = new ButtonGroup();
/*  957 */     jrbCase[0] = new JRadioButton();
/*  958 */     jrbCase[0].setSelected(true);
/*  959 */     jrbCase[0].setForeground(Def.COLOR_LABEL);
/*  960 */     jrbCase[0].setOpaque(false);
/*  961 */     jrbCase[0].setSize(245, 20);
/*  962 */     jrbCase[0].setLocation(5, 25);
/*  963 */     jrbCase[0].setText("Convert WORDS to Uppercase");
/*  964 */     jpCase.add(jrbCase[0]);
/*  965 */     if (bgCase != null) bgCase.add(jrbCase[0]);
/*      */     
/*  967 */     jrbCase[1] = new JRadioButton();
/*  968 */     jrbCase[1].setForeground(Def.COLOR_LABEL);
/*  969 */     jrbCase[1].setOpaque(false);
/*  970 */     jrbCase[1].setSize(245, 20);
/*  971 */     jrbCase[1].setLocation(5, 45);
/*  972 */     jrbCase[1].setText("Convert WORDS to Lowercase");
/*  973 */     jpCase.add(jrbCase[1]);
/*  974 */     if (bgCase != null) bgCase.add(jrbCase[1]);
/*      */     
/*  976 */     JPanel jpAlphabet = new JPanel();
/*  977 */     jpAlphabet.setLayout((LayoutManager)null);
/*  978 */     jpAlphabet.setSize(275, 116);
/*  979 */     jpAlphabet.setLocation(10, 204);
/*  980 */     jpAlphabet.setOpaque(true);
/*  981 */     jpAlphabet.setBorder(BorderFactory.createEtchedBorder());
/*  982 */     jdlgCreateDic.add(jpAlphabet);
/*      */     
/*  984 */     jl = new JLabel("Auxiliary Keyboard");
/*  985 */     jl.setForeground(Def.COLOR_LABEL);
/*  986 */     jl.setSize(270, 20);
/*  987 */     jl.setLocation(5, 5);
/*  988 */     jl.setHorizontalAlignment(2);
/*  989 */     jpAlphabet.add(jl);
/*      */     
/*  991 */     JCheckBox jcbAuxKeyboard = new JCheckBox("Connect Keyboard", (dicHeader[AUXKB] == 1));
/*  992 */     jcbAuxKeyboard.setForeground(Def.COLOR_LABEL);
/*  993 */     jcbAuxKeyboard.setOpaque(false);
/*  994 */     jcbAuxKeyboard.setSize(260, 20);
/*  995 */     jcbAuxKeyboard.setLocation(5, 25);
/*  996 */     jpAlphabet.add(jcbAuxKeyboard);
/*      */     
/*  998 */     jl = new JLabel("Select Alphabet");
/*  999 */     jl.setForeground(Def.COLOR_LABEL);
/* 1000 */     jl.setSize(110, 20);
/* 1001 */     jl.setLocation(5, 50);
/* 1002 */     jl.setHorizontalAlignment(2);
/* 1003 */     jpAlphabet.add(jl);
/*      */     
/* 1005 */     JComboBox<String> jcbbSelectAlpha = new JComboBox<>(alphabets);
/* 1006 */     jcbbSelectAlpha.setSize(145, 26);
/* 1007 */     jcbbSelectAlpha.setLocation(120, 50);
/* 1008 */     jcbbSelectAlpha.setBackground(Def.COLOR_BUTTONBG);
/* 1009 */     jcbbSelectAlpha.setSelectedIndex(dicHeader[ALPHABETNUM]);
/* 1010 */     jpAlphabet.add(jcbbSelectAlpha);
/*      */     
/* 1012 */     jl = new JLabel("Other Alphabets");
/* 1013 */     jl.setForeground(Def.COLOR_LABEL);
/* 1014 */     jl.setSize(110, 20);
/* 1015 */     jl.setLocation(5, 80);
/* 1016 */     jl.setHorizontalAlignment(2);
/* 1017 */     jpAlphabet.add(jl);
/*      */     
/* 1019 */     JTextField jtfAlpha = new JTextField("" + (dicHeader[OTHERALPHA] + ((dicHeader[OTHERALPHA] < 0) ? 256 : 0)), 15);
/* 1020 */     jtfAlpha.setSize(145, 26);
/* 1021 */     jtfAlpha.setLocation(120, 80);
/* 1022 */     jtfAlpha.selectAll();
/* 1023 */     jtfAlpha.setHorizontalAlignment(2);
/* 1024 */     jpAlphabet.add(jtfAlpha);
/*      */     
/* 1026 */     JPanel jpR2L = new JPanel();
/* 1027 */     jpR2L.setLayout((LayoutManager)null);
/* 1028 */     jpR2L.setSize(275, 70);
/* 1029 */     jpR2L.setLocation(10, 327);
/* 1030 */     jpR2L.setOpaque(true);
/* 1031 */     jpR2L.setBorder(BorderFactory.createEtchedBorder());
/* 1032 */     jdlgCreateDic.add(jpR2L);
/*      */     
/* 1034 */     jl = new JLabel("Right to Left Control");
/* 1035 */     jl.setForeground(Def.COLOR_LABEL);
/* 1036 */     jl.setSize(270, 20);
/* 1037 */     jl.setLocation(5, 5);
/* 1038 */     jl.setHorizontalAlignment(2);
/* 1039 */     jpR2L.add(jl);
/*      */     
/* 1041 */     jcbR2L[0] = new JCheckBox("Words are Right to Left", (dicHeader[R2L_WORD] == 1));
/* 1042 */     jcbR2L[0].setForeground(Def.COLOR_LABEL);
/* 1043 */     jcbR2L[0].setOpaque(false);
/* 1044 */     jcbR2L[0].setSize(260, 20);
/* 1045 */     jcbR2L[0].setLocation(5, 25);
/* 1046 */     jcbR2L[0].setSelected((dicHeader[R2L_WORD] == 1));
/* 1047 */     jpR2L.add(jcbR2L[0]);
/*      */     
/* 1049 */     jcbR2L[1] = new JCheckBox("Clues are Right to Left", (dicHeader[AUXKB] == 1));
/* 1050 */     jcbR2L[1].setForeground(Def.COLOR_LABEL);
/* 1051 */     jcbR2L[1].setOpaque(false);
/* 1052 */     jcbR2L[1].setSize(260, 20);
/* 1053 */     jcbR2L[1].setLocation(5, 45);
/* 1054 */     jcbR2L[1].setSelected((dicHeader[R2L_CLUE] == 1));
/* 1055 */     jpR2L.add(jcbR2L[1]);
/*      */     
/* 1057 */     jcbDev = new JCheckBox("Use Devanagari alphabet", (dicHeader[DEVANAGARI] == 0));
/* 1058 */     jcbDev.setForeground(Def.COLOR_LABEL);
/* 1059 */     jcbDev.setOpaque(false);
/* 1060 */     jcbDev.setSize(260, 20);
/* 1061 */     jcbDev.setLocation(15, 400);
/* 1062 */     jcbDev.setSelected((dicHeader[DEVANAGARI] == 0));
/* 1063 */     jdlgCreateDic.add(jcbDev);
/*      */     
/* 1065 */     JButton jbOK = Methods.cweButton("OK", 10, 430, 100, 26, null);
/* 1066 */     jbOK.addActionListener(e -> {
/*      */           String s; for (s = paramJTextField.getText(); s.startsWith("$"); s = s.substring(1)); if (paramInt == 1)
/*      */             s = "$" + s;  File fl = new File(s + ".dic");
/*      */           if (fl.exists()) {
/*      */             int response = JOptionPane.showConfirmDialog(paramJDialog, "<html>You already have a dictionary called <font color=880000>" + s + "</font>!<br>Continue anyway?", "Warning", 0);
/*      */             if (response == 0) {
/*      */               deleteDic(s);
/*      */             } else {
/*      */               return;
/*      */             } 
/*      */           } 
/*      */           fl.mkdir();
/*      */           if (paramInt == 0) {
/*      */             Op.cw[CrosswordBuild.dicList[0]] = s;
/*      */           } else {
/*      */             Op.ff[Op.FF.FfDic.ordinal()] = s;
/*      */           } 
/*      */           Op.msc[Op.MSC.DictionaryName.ordinal()] = s;
/*      */           if (Def.puzzleMode == 3)
/*      */             for (int i = 0; i < lmDic.size(); i++) {
/*      */               if (((String)lmDic.get(i)).compareTo(" " + s) > 0) {
/*      */                 lmDic.add(i, " " + s);
/*      */                 jlDic.setSelectedValue(" " + s, true);
/*      */                 break;
/*      */               } 
/*      */             }  
/*      */           try {
/*      */             DataOutputStream dataOut = new DataOutputStream(new FileOutputStream(s + ".dic/xword.dic"));
/*      */             for (int i = 0; i < 128; i++)
/*      */               dicHeader[i] = 0; 
/*      */             if (jrbLenSpec[0].isSelected()) {
/*      */               dicHeader[WORDLENGTH] = 0;
/*      */             } else if (jrbLenSpec[1].isSelected()) {
/*      */               dicHeader[WORDLENGTH] = 1;
/*      */             } else {
/*      */               dicHeader[WORDLENGTH] = 2;
/*      */             } 
/*      */             if (jrbCase[0].isSelected()) {
/*      */               dicHeader[CASE] = 0;
/*      */             } else {
/*      */               dicHeader[CASE] = 1;
/*      */             } 
/*      */             dicHeader[R2L_WORD] = (byte)(jcbR2L[0].isSelected() ? 1 : 0);
/*      */             dicHeader[R2L_CLUE] = (byte)(jcbR2L[1].isSelected() ? 1 : 0);
/*      */             dataOut.write(dicHeader, 0, 128);
/*      */             dataOut.close();
/* 1112 */           } catch (IOException exc) {
/*      */             return;
/*      */           }  Methods.clickedOK = true;
/*      */           paramJDialog.dispose();
/*      */           Methods.closeHelp();
/*      */         });
/* 1118 */     jdlgCreateDic.add(jbOK);
/*      */     
/* 1120 */     JButton jbCancel = Methods.cweButton("Cancel", 10, 465, 100, 26, null);
/* 1121 */     jbCancel.addActionListener(e -> {
/*      */           Methods.clickedOK = false;
/*      */           paramJDialog.dispose();
/*      */           Methods.closeHelp();
/*      */         });
/* 1126 */     jdlgCreateDic.add(jbCancel);
/*      */     
/* 1128 */     JButton jbHelp = Methods.cweButton("<html><font size=6 color=BB0000 face=Serif>Help ", 120, 430, 165, 61, new ImageIcon("graphics/help.png"));
/* 1129 */     jbHelp.addActionListener(e -> Methods.cweHelp(null, paramJDialog, (paramInt == 0) ? "Create a new Dictionary" : "Create a Theme Dictionary", (paramInt == 0) ? createDicHelp : createThemeDicHelp));
/*      */ 
/*      */ 
/*      */     
/* 1133 */     jdlgCreateDic.add(jbHelp);
/*      */     
/* 1135 */     jdlgCreateDic.getRootPane().setDefaultButton(jbOK);
/* 1136 */     Methods.setDialogSize(jdlgCreateDic, 295, 501);
/*      */   }
/*      */ 
/*      */   
/*      */   void importText() {
/* 1141 */     String[] alphabet = { "Arabic غظضذخثتشرقصفعسنملكيطحزوهدجبأ", "Danish ABCDEFGHIJKLMNOPQRSTUVWXYZÆØÅabcdefghijklmnopqrstuvwxyzæøå", "Dutch ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz", "English ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz", "French ABCDEFGHIJKLMNOPQRSTUVWXYZÀÂÆÇÉÈÊËÎÏÔŒÙÛÜŸabcdefghijklmnopqrstuvwxyzàâæçéèêëîïôœùûüÿ", "German ABCDEFGHIJKLMNOPQRSTUVWXYZÄÖÜßabcdefghijklmnopqrstuvwxyzäöü", "Greek ΑΒΓΔΕΖΗΘΙΚΛΜΝΞΟΠΡΣΤΥΦΧΨΩαβγδεζηθικλμνξοπρσςτυφχψω", "Hebrew אבגדהוזחטיךכלםמןנסעףפץצקרשת", "Italian ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz", "Norwegian ABCDEFGHIJKLMNOPQRSTUVWXYZÆØÅabcdefghijklmnopqrstuvwxyzæøå", "Polish AĄBCĆDEĘFGHIJKLŁMNŃOÓPRSŚTUWYZŹŻaąbcćdeęfghijklłmnńoóprsśtuwyzźż", "Russian АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯабвгдеёжзийклмнопрстуфхцчшщъыьэюя", "Spanish ABCDEFGHIJKLMNÑOPQRSTUVWXYZabcdefghijklmnñopqrstuvwxyz", "Swedish ABCDEFGHIJKLMNOPQRSTUVWXYZÅÄÖabcdefghijklmnopqrstuvwxyzåäö", "Turkish ABCÇDEFGĞHIİJKLMNOÖPRSŞTUÜVYZabcçdefgğhıijklmnoöprsştuüvyz" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1161 */     JDialog jdlgImportText = new JDialog(jfDictionaryMtce, "Import words from a text file", true);
/* 1162 */     jdlgImportText.setSize(350, 400);
/* 1163 */     jdlgImportText.setResizable(false);
/* 1164 */     jdlgImportText.setLayout((LayoutManager)null);
/* 1165 */     jdlgImportText.setLocation(jfDictionaryMtce.getX(), jfDictionaryMtce.getY());
/*      */     
/* 1167 */     jdlgImportText
/* 1168 */       .addWindowListener(new WindowAdapter() {
/*      */           public void windowClosing(WindowEvent we) {
/* 1170 */             Methods.closeHelp();
/*      */           }
/*      */         });
/*      */     
/* 1174 */     Methods.closeHelp();
/*      */     
/* 1176 */     JLabel jlImportFile = new JLabel((path != null) ? path.substring(path.lastIndexOf("/") + 1) : "Not yet selected");
/* 1177 */     jlImportFile.setForeground(Def.COLOR_LABEL);
/* 1178 */     jlImportFile.setSize(330, 20);
/* 1179 */     jlImportFile.setLocation(10, 40);
/* 1180 */     jlImportFile.setHorizontalAlignment(0);
/* 1181 */     jdlgImportText.add(jlImportFile);
/*      */     
/* 1183 */     JButton jbImportFile = Methods.cweButton("Select Import File", 10, 10, 330, 26, null);
/* 1184 */     jbImportFile.addActionListener(e -> {
/*      */           Methods.clickedOK = false;
/*      */           JFileChooser chooser = new JFileChooser(System.getProperty("user.dir") + "/");
/*      */           chooser.setDialogTitle("Select Text File to import");
/*      */           chooser.setFileFilter(new FileNameExtensionFilter("Text File", new String[] { "txt" }));
/*      */           if (chooser.showOpenDialog(paramJDialog) == 0)
/*      */             path = chooser.getSelectedFile().getAbsolutePath(); 
/*      */           paramJLabel.setText((path != null) ? path.substring(path.lastIndexOf("/") + 1) : "Not yet selected");
/*      */         });
/* 1193 */     jdlgImportText.add(jbImportFile);
/*      */     
/* 1195 */     JLabel jlSelectAlphabet = new JLabel();
/* 1196 */     jlSelectAlphabet.setForeground(Def.COLOR_LABEL);
/* 1197 */     jlSelectAlphabet.setText("Select Alphabet");
/* 1198 */     jlSelectAlphabet.setLocation(40, 70);
/* 1199 */     jlSelectAlphabet.setSize(140, 20);
/* 1200 */     jdlgImportText.add(jlSelectAlphabet);
/*      */     
/* 1202 */     JComboBox<String> jcbbAlpha = new JComboBox<>();
/* 1203 */     jcbbAlpha.setSize(150, 26);
/* 1204 */     jcbbAlpha.setLocation(160, 70);
/* 1205 */     for (int n = 0; n < alphabet.length; n++)
/* 1206 */       jcbbAlpha.addItem(alphabet[n].substring(0, alphabet[n].indexOf(" "))); 
/* 1207 */     jcbbAlpha.setSelectedItem(Op.msc[Op.MSC.ImportAlphabet.ordinal()]);
/* 1208 */     jdlgImportText.add(jcbbAlpha);
/*      */     
/* 1210 */     int index = jcbbAlpha.getSelectedIndex();
/* 1211 */     JTextField jtfAlphabet = new JTextField(alphabet[index].substring(alphabet[index].indexOf(" ") + 1), 100);
/* 1212 */     jtfAlphabet.setSize(330, 26);
/* 1213 */     jtfAlphabet.setLocation(10, 105);
/* 1214 */     jtfAlphabet.selectAll();
/* 1215 */     jtfAlphabet.setHorizontalAlignment(2);
/* 1216 */     jdlgImportText.add(jtfAlphabet);
/* 1217 */     jcbbAlpha
/* 1218 */       .addActionListener(ce -> {
/*      */           int index1 = paramJComboBox.getSelectedIndex();
/*      */           
/*      */           paramJTextField.setText(paramArrayOfString[index1].substring(paramArrayOfString[index1].indexOf(" ") + 1));
/*      */           
/*      */           Op.msc[Op.MSC.ImportAlphabet.ordinal()] = paramJComboBox.getSelectedItem().toString();
/*      */         });
/* 1225 */     JLabel jlShortestWord = new JLabel();
/* 1226 */     jlShortestWord.setForeground(Def.COLOR_LABEL);
/* 1227 */     jlShortestWord.setText("Shortest Word to Import");
/* 1228 */     jlShortestWord.setLocation(20, 140);
/* 1229 */     jlShortestWord.setSize(160, 20);
/* 1230 */     jdlgImportText.add(jlShortestWord);
/*      */     
/* 1232 */     JComboBox<Integer> jcbbShort = new JComboBox<>();
/* 1233 */     for (int i = 1; i <= 6; i++)
/* 1234 */       jcbbShort.addItem(Integer.valueOf(i)); 
/* 1235 */     jcbbShort.setSize(50, 20);
/* 1236 */     jcbbShort.setLocation(180, 140);
/* 1237 */     jdlgImportText.add(jcbbShort);
/* 1238 */     jcbbShort.setBackground(Def.COLOR_BUTTONBG);
/* 1239 */     jcbbShort.setSelectedIndex(Op.getInt(Op.MSC.ShortestImport.ordinal(), Op.msc) - 1);
/*      */     
/* 1241 */     JButton jbOK = Methods.cweButton("OK", 45, 180, 90, 26, null);
/* 1242 */     jbOK.addActionListener(e -> {
/*      */           String thisAlphabet = paramJTextField.getText();
/*      */           
/*      */           ArrayList<String> al = new ArrayList();
/*      */           
/*      */           if (paramJLabel.getText().equalsIgnoreCase("Not yet selected")) {
/*      */             JOptionPane.showMessageDialog(jfDictionaryMtce, "You must select an Import File.");
/*      */             
/*      */             return;
/*      */           } 
/*      */           
/*      */           Op.setInt(Op.MSC.ShortestImport.ordinal(), paramJComboBox.getSelectedIndex() + 1, Op.msc);
/*      */           
/*      */           try {
/*      */             int response = 0;
/*      */             DataInputStream dataIn = new DataInputStream(new FileInputStream(Op.msc[Op.MSC.DictionaryName.ordinal()] + ".dic/xword.dic"));
/*      */             if (dataIn.available() > 128) {
/*      */               response = JOptionPane.showConfirmDialog(jfDictionaryMtce, "The current dictionary has content.If you continue it will be overwritten.Continue ayway?", "Warning", 0);
/*      */             }
/*      */             dataIn.close();
/*      */             if (response == 1) {
/*      */               return;
/*      */             }
/* 1265 */           } catch (IOException exc) {}
/*      */           try {
/*      */             BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
/*      */             String line;
/*      */             label106: while ((line = br.readLine()) != null) {
/*      */               if (line.length() == 0) {
/*      */                 continue;
/*      */               }
/*      */               while (true) {
/*      */                 int len = line.indexOf(' ');
/*      */                 if (len != -1) {
/*      */                   word = line.substring(0, len);
/*      */                   line = line.substring(len + 1);
/*      */                 } else {
/*      */                   word = line;
/*      */                 } 
/*      */                 int start = 0;
/*      */                 while (start < word.length() && !thisAlphabet.contains("" + word.charAt(start))) {
/*      */                   start++;
/*      */                 }
/*      */                 if (start == word.length()) {
/*      */                   if (len == -1) {
/*      */                     continue label106;
/*      */                   }
/*      */                   continue;
/*      */                 } 
/*      */                 int end;
/*      */                 for (end = word.length(); !thisAlphabet.contains("" + word.charAt(end - 1)); end--);
/*      */                 String word = word.substring(start, end);
/*      */                 start = 0;
/*      */                 while (start < word.length() && (thisAlphabet.contains("" + word.charAt(start)) || len != -1)) {
/*      */                   start++;
/*      */                 }
/*      */                 if (word.length() < Op.getInt(Op.MSC.ShortestImport.ordinal(), Op.msc)) {
/*      */                   if (len == -1)
/*      */                     continue label106; 
/*      */                   continue;
/*      */                 } 
/*      */                 al.add((dicHeader[CASE] == 0) ? word.toUpperCase() : word.toLowerCase());
/*      */                 if (len == -1)
/*      */                   continue label106; 
/*      */               } 
/*      */             } 
/*      */             br.close();
/* 1309 */           } catch (IOException exc) {} Object[] list = al.toArray(); Arrays.sort(list); if (al.isEmpty()) {
/*      */             JOptionPane.showMessageDialog(jfDictionaryMtce, "The Import File did not contain any wordsmatching the chosen Alphabet.");
/*      */             return;
/*      */           } 
/*      */           int j = 1;
/*      */           int wCount = j;
/*      */           while (j < al.size()) {
/*      */             if (!list[wCount - 1].toString().equalsIgnoreCase(list[j].toString()))
/*      */               list[wCount++] = list[j]; 
/*      */             j++;
/*      */           } 
/*      */           int i = j = 0;
/*      */           while (j < wCount) {
/*      */             String word = list[j].toString();
/*      */             int k = 0;
/*      */             while (k < word.length() && thisAlphabet.contains(String.valueOf(word.charAt(k))))
/*      */               k++; 
/*      */             if (k == word.length())
/*      */               list[i++] = list[j]; 
/*      */             j++;
/*      */           } 
/*      */           wCount = i;
/*      */           try {
/*      */             DataOutputStream dataOut = new DataOutputStream(new FileOutputStream(Op.msc[Op.MSC.DictionaryName.ordinal()] + ".dic/xword.dic"));
/*      */             dataOut.write(dicHeader, 0, 128);
/*      */             for (i = 0; i < wCount; i++) {
/*      */               dataOut.writeInt(0);
/*      */               dataOut.writeUTF(list[i].toString());
/*      */               dataOut.writeUTF("");
/*      */             } 
/*      */             dataOut.close();
/* 1340 */           } catch (IOException exc) {}
/*      */           paramJDialog.dispose();
/*      */           dictionaryStats();
/*      */           JOptionPane.showMessageDialog(jfDictionaryMtce, "Import of the text file has been completed.");
/*      */           Methods.closeHelp();
/*      */         });
/* 1346 */     jdlgImportText.add(jbOK);
/*      */     
/* 1348 */     JButton jbCancel = Methods.cweButton("Cancel", 45, 215, 90, 26, null);
/* 1349 */     jbCancel.addActionListener(e -> {
/*      */           Methods.clickedOK = false;
/*      */           paramJDialog.dispose();
/*      */           Methods.closeHelp();
/*      */         });
/* 1354 */     jdlgImportText.add(jbCancel);
/*      */     
/* 1356 */     JButton jbHelp = Methods.cweButton("<html><font size=6 color=BB0000 face=Serif>Help ", 150, 180, 155, 61, new ImageIcon("graphics/help.png"));
/* 1357 */     jbHelp.addActionListener(e -> Methods.cweHelp(null, paramJDialog, "Import A Text File", importTextOptions));
/*      */ 
/*      */     
/* 1360 */     jdlgImportText.add(jbHelp);
/*      */     
/* 1362 */     Methods.setDialogSize(jdlgImportText, 350, 255);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   void importCWEdic() {
/* 1368 */     String path = "";
/*      */ 
/*      */ 
/*      */     
/* 1372 */     JFileChooser chooser = new JFileChooser(System.getProperty("user.dir") + "/");
/* 1373 */     chooser.setDialogTitle("Import a Crossword Express Dictionary");
/* 1374 */     chooser.setFileFilter(new FileNameExtensionFilter("Dictionary", new String[] { "dic" }));
/* 1375 */     if (chooser.showOpenDialog(jfDictionaryMtce) == 0)
/* 1376 */       path = chooser.getSelectedFile().getAbsolutePath(); 
/* 1377 */     if (path.length() == 0)
/*      */       return; 
/*      */     try {
/* 1380 */       DataInputStream dataIn = new DataInputStream(new FileInputStream(path));
/* 1381 */       DataOutputStream dataOut = new DataOutputStream(new FileOutputStream(Op.msc[Op.MSC.DictionaryName.ordinal()] + ".dic/xword.dic"));
/*      */       
/* 1383 */       dataIn.read(dicHeader, 0, 128);
/* 1384 */       dataOut.write(dicHeader, 0, 128);
/* 1385 */       while (dataIn.available() > 0) {
/* 1386 */         int wordDat = dataIn.readInt();
/* 1387 */         String wordOld = dataIn.readUTF();
/* 1388 */         String clueOld = dataIn.readUTF();
/*      */         
/* 1390 */         if (clueOld.length() > 0) {
/* 1391 */           dataOut.writeInt(wordDat);
/* 1392 */           dataOut.writeUTF(wordOld);
/* 1393 */           dataOut.writeUTF(clueOld);
/*      */         } 
/*      */       } 
/*      */       
/* 1397 */       dataIn.close();
/* 1398 */       dataOut.close();
/*      */     }
/* 1400 */     catch (IOException exc) {}
/*      */     
/* 1402 */     dictionaryStats();
/* 1403 */     JOptionPane.showMessageDialog(jfDictionaryMtce, "Import of the dictionary has been completed.");
/*      */   }
/*      */   
/*      */   void exportWordList() {
/* 1407 */     String path = "";
/*      */ 
/*      */ 
/*      */     
/* 1411 */     JFileChooser chooser = new JFileChooser(System.getProperty("user.dir") + "/");
/* 1412 */     chooser.setDialogTitle("Export a Word-list File");
/* 1413 */     chooser.setFileFilter(new FileNameExtensionFilter("Word-list", new String[] { "lst" }));
/* 1414 */     chooser.setSelectedFile(new File("wordlist.lst"));
/* 1415 */     if (chooser.showSaveDialog(jfDictionaryMtce) == 0)
/* 1416 */       path = chooser.getSelectedFile().getAbsolutePath(); 
/* 1417 */     if (path.length() == 0)
/*      */       return; 
/*      */     try {
/* 1420 */       OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(path), "UTF-8");
/* 1421 */       DataInputStream dataIn = new DataInputStream(new FileInputStream(Op.msc[Op.MSC.DictionaryName.ordinal()] + ".dic/xword.dic"));
/*      */       
/* 1423 */       dataIn.read(dicHeader, 0, 128);
/* 1424 */       while (dataIn.available() > 0) {
/* 1425 */         dataIn.readInt();
/* 1426 */         String wordOld = dataIn.readUTF();
/* 1427 */         String clueOld = dataIn.readUTF();
/* 1428 */         out.write(wordOld + " ");
/* 1429 */         out.write(clueOld + "\r");
/*      */       } 
/* 1431 */       dataIn.close();
/* 1432 */       out.close();
/*      */     }
/* 1434 */     catch (IOException exc) {}
/*      */     
/* 1436 */     JOptionPane.showMessageDialog(jfDictionaryMtce, "Export of the Dictionary has been completed.");
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
/*      */   void importWordList() {
/* 1516 */     String path = "";
/* 1517 */     ArrayList<String> al = new ArrayList<>();
/*      */     
/* 1519 */     String wordOld = "", clueOld = "", wordLast = "";
/* 1520 */     int wordDat = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1525 */     boolean fail = false;
/*      */     
/* 1527 */     JFileChooser chooser = new JFileChooser(System.getProperty("user.dir") + "/");
/* 1528 */     chooser.setDialogTitle("Import a Word-list File");
/* 1529 */     chooser.setFileFilter(new FileNameExtensionFilter("Word-list", new String[] { "lst" }));
/* 1530 */     chooser.setSelectedFile(new File("wordlist.lst"));
/* 1531 */     if (chooser.showOpenDialog(jfDictionaryMtce) == 0)
/* 1532 */       path = chooser.getSelectedFile().getAbsolutePath(); 
/* 1533 */     if (path.length() == 0)
/*      */       return; 
/*      */     try {
/* 1536 */       BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8")); String line;
/* 1537 */       while ((line = br.readLine()) != null) {
/* 1538 */         String wordNew, clueNew; int j = line.indexOf('\t');
/* 1539 */         if (j == -1)
/* 1540 */           j = line.indexOf(' '); 
/* 1541 */         if (j == -1) {
/* 1542 */           wordNew = line;
/* 1543 */           clueNew = "";
/*      */         } else {
/*      */           
/* 1546 */           wordNew = line.substring(0, j).trim();
/* 1547 */           clueNew = line.substring(j).trim();
/*      */         } 
/*      */         
/* 1550 */         if (dicHeader[DEVANAGARI] == 1)
/* 1551 */           fail = DevanagariBuild.devInvalid(wordNew); 
/* 1552 */         if (!fail) {
/* 1553 */           wordNew = (dicHeader[CASE] == 0) ? wordNew.toUpperCase() : wordNew.toLowerCase();
/* 1554 */           al.add(wordNew + " " + clueNew);
/*      */         } 
/*      */       } 
/* 1557 */       Object[] list = al.toArray();
/* 1558 */       Arrays.sort(list);
/*      */       
/* 1560 */       DataInputStream dataIn = new DataInputStream(new FileInputStream(Op.msc[Op.MSC.DictionaryName.ordinal()] + ".dic/xword.dic"));
/* 1561 */       DataOutputStream dataOut = new DataOutputStream(new FileOutputStream(Op.msc[Op.MSC.DictionaryName.ordinal()] + ".dic/xword.new"));
/*      */       
/* 1563 */       dataIn.read(dicHeader, 0, 128);
/* 1564 */       if (dataIn.available() > 0) {
/* 1565 */         wordDat = dataIn.readInt();
/* 1566 */         wordOld = dataIn.readUTF();
/* 1567 */         clueOld = dataIn.readUTF();
/*      */       } 
/*      */       
/* 1570 */       dataOut.write(dicHeader, 0, 128);
/* 1571 */       for (int i = 0; i < al.size(); i++) {
/* 1572 */         String clueNew; line = list[i].toString();
/* 1573 */         int j = line.indexOf('\t');
/* 1574 */         if (j == -1)
/* 1575 */           j = line.indexOf(' '); 
/* 1576 */         if (j == -1) {
/* 1577 */           wordNew = line;
/* 1578 */           clueNew = "";
/*      */         } else {
/*      */           
/* 1581 */           wordNew = line.substring(0, j).toUpperCase();
/* 1582 */           clueNew = line.substring(j).trim();
/* 1583 */           if (clueNew.length() == 0) clueNew = ""; 
/*      */         } 
/* 1585 */         String wordNew = (dicHeader[CASE] == 0) ? wordNew.toUpperCase() : wordNew.toLowerCase();
/* 1586 */         if (!wordNew.equalsIgnoreCase(wordLast)) {
/* 1587 */           wordLast = wordNew;
/*      */           
/*      */           while (true) {
/* 1590 */             if (wordOld.length() == 0 || wordNew.compareToIgnoreCase(wordOld) < 0) {
/* 1591 */               if (wordNew.length() < 50) {
/* 1592 */                 dataOut.writeInt(0);
/* 1593 */                 dataOut.writeUTF(wordNew);
/* 1594 */                 dataOut.writeUTF(clueNew);
/*      */               } 
/*      */               break;
/*      */             } 
/* 1598 */             if (wordNew.compareTo(wordOld) == 0) {
/* 1599 */               dataOut.writeInt(wordDat);
/* 1600 */               dataOut.writeUTF(wordNew);
/* 1601 */               if (clueOld.length() == 0) {
/* 1602 */                 dataOut.writeUTF(clueNew);
/* 1603 */               } else if (clueNew.length() == 0) {
/* 1604 */                 dataOut.writeUTF(clueOld);
/*      */               } else {
/* 1606 */                 dataOut.writeUTF(clueOld);
/* 1607 */                 if (!clueOld.contains(clueNew)) {
/* 1608 */                   dataOut.writeUTF("*" + clueNew);
/*      */                 }
/*      */               } 
/* 1611 */               wordDat = 0; wordOld = clueOld = "";
/* 1612 */               if (dataIn.available() > 0) {
/* 1613 */                 wordDat = dataIn.readInt();
/* 1614 */                 wordOld = dataIn.readUTF();
/* 1615 */                 clueOld = dataIn.readUTF();
/*      */               } 
/*      */               
/*      */               break;
/*      */             } 
/* 1620 */             dataOut.writeInt(wordDat);
/* 1621 */             dataOut.writeUTF(wordOld);
/* 1622 */             dataOut.writeUTF(clueOld);
/* 1623 */             wordDat = 0; wordOld = clueOld = "";
/* 1624 */             if (dataIn.available() > 0) {
/* 1625 */               wordDat = dataIn.readInt();
/* 1626 */               wordOld = dataIn.readUTF();
/* 1627 */               clueOld = dataIn.readUTF();
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/* 1632 */       if (wordOld.length() > 0) {
/*      */         while (true) {
/*      */           
/* 1635 */           dataOut.writeInt(wordDat);
/* 1636 */           dataOut.writeUTF(wordOld);
/* 1637 */           dataOut.writeUTF(clueOld);
/* 1638 */           if (dataIn.available() > 0) {
/* 1639 */             wordDat = dataIn.readInt();
/* 1640 */             wordOld = dataIn.readUTF();
/* 1641 */             clueOld = dataIn.readUTF();
/*      */             continue;
/*      */           } 
/*      */           break;
/*      */         } 
/*      */       }
/* 1647 */       br.close();
/* 1648 */       dataIn.close();
/* 1649 */       dataOut.close();
/*      */     }
/* 1651 */     catch (IOException exc) {}
/*      */     
/* 1653 */     File fl = new File(Op.msc[Op.MSC.DictionaryName.ordinal()] + ".dic/xword.dic");
/* 1654 */     fl.delete();
/* 1655 */     fl = new File(Op.msc[Op.MSC.DictionaryName.ordinal()] + ".dic/xword.new");
/* 1656 */     fl.renameTo(new File(Op.msc[Op.MSC.DictionaryName.ordinal()] + ".dic/xword.dic"));
/*      */     
/* 1658 */     dictionaryStats();
/* 1659 */     JOptionPane.showMessageDialog(jfDictionaryMtce, "Import of the WordList has been completed.");
/*      */   }
/*      */ 
/*      */   
/*      */   void unlockWords() {
/* 1664 */     int count = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1670 */       DataInputStream dataIn = new DataInputStream(new FileInputStream(Op.msc[Op.MSC.DictionaryName.ordinal()] + ".dic/xword.dic"));
/* 1671 */       DataOutputStream dataOut = new DataOutputStream(new FileOutputStream(Op.msc[Op.MSC.DictionaryName.ordinal()] + ".dic/xword.new"));
/* 1672 */       for (int i = 0; i < 128; ) { dataOut.writeByte(dataIn.readByte()); i++; }
/* 1673 */        while (dataIn.available() > 2) {
/* 1674 */         int wordDat = dataIn.readInt();
/* 1675 */         if (wordDat % 256 == 255) count++; 
/* 1676 */         wordDat &= 0xFFFFFF00;
/* 1677 */         String word = dataIn.readUTF();
/* 1678 */         String clue = dataIn.readUTF();
/* 1679 */         dataOut.writeInt(wordDat);
/* 1680 */         dataOut.writeUTF(word);
/* 1681 */         dataOut.writeUTF(clue);
/*      */       } 
/* 1683 */       dataOut.close();
/* 1684 */       dataIn.close();
/*      */     }
/* 1686 */     catch (IOException exc) {}
/*      */     
/* 1688 */     File fl = new File(Op.msc[Op.MSC.DictionaryName.ordinal()] + ".dic/xword.dic");
/* 1689 */     fl.delete();
/* 1690 */     fl = new File(Op.msc[Op.MSC.DictionaryName.ordinal()] + ".dic/xword.new");
/* 1691 */     fl.renameTo(new File(Op.msc[Op.MSC.DictionaryName.ordinal()] + ".dic/xword.dic"));
/* 1692 */     dictionaryStats();
/*      */     
/* 1694 */     JOptionPane.showMessageDialog(jfDictionaryMtce, "<html><center>" + count + " locked words in the <font color=880000>" + Op.msc[Op.MSC.DictionaryName
/* 1695 */           .ordinal()] + "</font> dictionary<br>" + "have been placed back into service.");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static void deleteDic(String dicName) {
/* 1701 */     if (dicName.length() == 0) {
/* 1702 */       if (JOptionPane.showConfirmDialog(jfDictionaryMtce, "<html>Delete the Dictionary <font color=880000 size=4>" + Op.msc[Op.MSC.DictionaryName.ordinal()] + "</font>?", "Delete a Dictionary", 0) == 1) {
/*      */         return;
/*      */       }
/*      */     } else {
/*      */       
/* 1707 */       Op.msc[Op.MSC.DictionaryName.ordinal()] = dicName;
/*      */     } 
/* 1709 */     File fl = new File(Op.msc[Op.MSC.DictionaryName.ordinal()] + ".dic");
/* 1710 */     String[] st = fl.list(); int i;
/* 1711 */     for (i = 0; i < st.length; i++) {
/* 1712 */       File fl2 = new File(Op.msc[Op.MSC.DictionaryName.ordinal()] + ".dic/" + st[i]);
/* 1713 */       fl2.delete();
/*      */     } 
/* 1715 */     fl.delete();
/*      */     
/* 1717 */     if (Def.puzzleMode == 3)
/* 1718 */       for (i = 0; i < lmDic.size(); i++) {
/* 1719 */         if (((String)lmDic.get(i)).compareTo(" " + Op.msc[Op.MSC.DictionaryName.ordinal()]) == 0) {
/* 1720 */           Op.msc[Op.MSC.DictionaryName.ordinal()] = ((String)lmDic.get((i == 0) ? 1 : (i - 1))).trim();
/* 1721 */           jlDic.setSelectedValue(" " + Op.msc[Op.MSC.DictionaryName.ordinal()], true);
/* 1722 */           lmDic.remove(i);
/* 1723 */           if (jfDictionaryMtce != null) {
/* 1724 */             jfDictionaryMtce.setTitle("Maintain Dictionary : " + Op.msc[Op.MSC.DictionaryName.ordinal()]);
/*      */           }
/*      */           break;
/*      */         } 
/*      */       }  
/*      */   }
/*      */   
/*      */   static void dictionaryProperties() {
/* 1732 */     JDialog jdlgDicProperties = new JDialog(jfDictionaryMtce, "Dictionary Properties", true);
/* 1733 */     jdlgDicProperties.setSize(300, 360);
/* 1734 */     jdlgDicProperties.setResizable(false);
/* 1735 */     jdlgDicProperties.setLayout((LayoutManager)null);
/* 1736 */     jdlgDicProperties.setLocation(jfDictionaryMtce.getX(), jfDictionaryMtce.getY());
/*      */     
/* 1738 */     jdlgDicProperties
/* 1739 */       .addWindowListener(new WindowAdapter() {
/*      */           public void windowClosing(WindowEvent we) {
/* 1741 */             Methods.closeHelp();
/*      */           }
/*      */         });
/*      */     
/* 1745 */     Methods.closeHelp();
/*      */     
/*      */     try {
/* 1748 */       DataInputStream dataIn = new DataInputStream(new FileInputStream(Op.msc[Op.MSC.DictionaryName.ordinal()] + ".dic/xword.dic"));
/* 1749 */       dataIn.read(dicHeader, 0, 128);
/* 1750 */       dataIn.close();
/*      */     }
/* 1752 */     catch (IOException exc) {}
/*      */     
/* 1754 */     JPanel jpWLS = new JPanel();
/* 1755 */     jpWLS.setLayout((LayoutManager)null);
/* 1756 */     jpWLS.setSize(275, 90);
/* 1757 */     jpWLS.setLocation(10, 10);
/* 1758 */     jpWLS.setOpaque(true);
/* 1759 */     jpWLS.setBorder(BorderFactory.createEtchedBorder());
/* 1760 */     jdlgDicProperties.add(jpWLS);
/*      */     
/* 1762 */     JLabel jl = new JLabel("Word Length Specification");
/* 1763 */     jl.setForeground(Def.COLOR_LABEL);
/* 1764 */     jl.setSize(270, 20);
/* 1765 */     jl.setLocation(5, 5);
/* 1766 */     jl.setHorizontalAlignment(2);
/* 1767 */     jpWLS.add(jl);
/*      */     
/* 1769 */     ButtonGroup bgLenSpec = new ButtonGroup();
/* 1770 */     jrbLenSpec[0] = new JRadioButton();
/* 1771 */     jrbLenSpec[0].setForeground(Def.COLOR_LABEL);
/* 1772 */     jrbLenSpec[0].setText("Don't insert word lengths");
/* 1773 */     jrbLenSpec[0].setOpaque(false);
/* 1774 */     jrbLenSpec[0].setSize(260, 20);
/* 1775 */     jrbLenSpec[0].setLocation(5, 25);
/* 1776 */     jpWLS.add(jrbLenSpec[0]);
/* 1777 */     if (bgLenSpec != null) bgLenSpec.add(jrbLenSpec[0]);
/*      */     
/* 1779 */     jrbLenSpec[1] = new JRadioButton();
/* 1780 */     jrbLenSpec[1].setForeground(Def.COLOR_LABEL);
/* 1781 */     jrbLenSpec[1].setText("Insert all word lengths");
/* 1782 */     jrbLenSpec[1].setOpaque(false);
/* 1783 */     jrbLenSpec[1].setSize(260, 20);
/* 1784 */     jrbLenSpec[1].setLocation(5, 45);
/* 1785 */     jpWLS.add(jrbLenSpec[1]);
/* 1786 */     if (bgLenSpec != null) bgLenSpec.add(jrbLenSpec[1]);
/*      */     
/* 1788 */     jrbLenSpec[2] = new JRadioButton();
/* 1789 */     jrbLenSpec[2].setForeground(Def.COLOR_LABEL);
/* 1790 */     jrbLenSpec[2].setText("Insert only multi-word lengths");
/* 1791 */     jrbLenSpec[2].setOpaque(false);
/* 1792 */     jrbLenSpec[2].setSize(260, 20);
/* 1793 */     jrbLenSpec[2].setLocation(5, 65);
/* 1794 */     jpWLS.add(jrbLenSpec[2]);
/* 1795 */     if (bgLenSpec != null) bgLenSpec.add(jrbLenSpec[2]);
/*      */     
/* 1797 */     jrbLenSpec[dicHeader[WORDLENGTH]].setSelected(true);
/*      */     
/* 1799 */     JPanel jpCase = new JPanel();
/* 1800 */     jpCase.setLayout((LayoutManager)null);
/* 1801 */     jpCase.setSize(275, 70);
/* 1802 */     jpCase.setLocation(10, 110);
/* 1803 */     jpCase.setOpaque(true);
/* 1804 */     jpCase.setBorder(BorderFactory.createEtchedBorder());
/* 1805 */     jdlgDicProperties.add(jpCase);
/*      */     
/* 1807 */     jl = new JLabel("Case Control");
/* 1808 */     jl.setForeground(Def.COLOR_LABEL);
/* 1809 */     jl.setSize(270, 20);
/* 1810 */     jl.setLocation(5, 5);
/* 1811 */     jl.setHorizontalAlignment(2);
/* 1812 */     jpCase.add(jl);
/*      */     
/* 1814 */     ButtonGroup bgCase = new ButtonGroup();
/* 1815 */     jrbCase[0] = new JRadioButton();
/* 1816 */     jrbCase[0].setForeground(Def.COLOR_LABEL);
/* 1817 */     jrbCase[0].setText("Convert WORDS to Uppercase");
/* 1818 */     jrbCase[0].setOpaque(false);
/* 1819 */     jrbCase[0].setSize(260, 20);
/* 1820 */     jrbCase[0].setLocation(5, 25);
/* 1821 */     jpCase.add(jrbCase[0]);
/* 1822 */     if (bgCase != null) bgCase.add(jrbCase[0]);
/*      */     
/* 1824 */     jrbCase[1] = new JRadioButton();
/* 1825 */     jrbCase[1].setForeground(Def.COLOR_LABEL);
/* 1826 */     jrbCase[1].setText("Convert WORDS to Lowercase");
/* 1827 */     jrbCase[1].setOpaque(false);
/* 1828 */     jrbCase[1].setSize(260, 20);
/* 1829 */     jrbCase[1].setLocation(5, 45);
/* 1830 */     jpCase.add(jrbCase[1]);
/* 1831 */     if (bgCase != null) bgCase.add(jrbCase[1]);
/*      */     
/* 1833 */     jrbCase[dicHeader[CASE]].setSelected(true);
/*      */     
/* 1835 */     JPanel jpAlphabet = new JPanel();
/* 1836 */     jpAlphabet.setLayout((LayoutManager)null);
/* 1837 */     jpAlphabet.setSize(275, 116);
/* 1838 */     jpAlphabet.setLocation(10, 190);
/* 1839 */     jpAlphabet.setOpaque(true);
/* 1840 */     jpAlphabet.setBorder(BorderFactory.createEtchedBorder());
/* 1841 */     jdlgDicProperties.add(jpAlphabet);
/*      */     
/* 1843 */     jl = new JLabel("Auxiliary Keyboard");
/* 1844 */     jl.setForeground(Def.COLOR_LABEL);
/* 1845 */     jl.setSize(270, 20);
/* 1846 */     jl.setLocation(5, 5);
/* 1847 */     jl.setHorizontalAlignment(2);
/* 1848 */     jpAlphabet.add(jl);
/*      */     
/* 1850 */     JCheckBox jcbAuxKeyboard = new JCheckBox("Connect Keyboard", (dicHeader[AUXKB] == 1));
/* 1851 */     jcbAuxKeyboard.setForeground(Def.COLOR_LABEL);
/* 1852 */     jcbAuxKeyboard.setOpaque(false);
/* 1853 */     jcbAuxKeyboard.setSize(260, 20);
/* 1854 */     jcbAuxKeyboard.setLocation(5, 25);
/* 1855 */     jpAlphabet.add(jcbAuxKeyboard);
/*      */     
/* 1857 */     jl = new JLabel("Select Alphabet");
/* 1858 */     jl.setForeground(Def.COLOR_LABEL);
/* 1859 */     jl.setSize(110, 20);
/* 1860 */     jl.setLocation(5, 50);
/* 1861 */     jl.setHorizontalAlignment(2);
/* 1862 */     jpAlphabet.add(jl);
/*      */     
/* 1864 */     JComboBox<String> jcbbSelectAlpha = new JComboBox<>(alphabets);
/* 1865 */     jcbbSelectAlpha.setSize(145, 26);
/* 1866 */     jcbbSelectAlpha.setLocation(120, 50);
/* 1867 */     jcbbSelectAlpha.setBackground(Def.COLOR_BUTTONBG);
/* 1868 */     jcbbSelectAlpha.setSelectedIndex(dicHeader[ALPHABETNUM]);
/* 1869 */     jpAlphabet.add(jcbbSelectAlpha);
/*      */     
/* 1871 */     jl = new JLabel("Other Alphabets");
/* 1872 */     jl.setForeground(Def.COLOR_LABEL);
/* 1873 */     jl.setSize(110, 20);
/* 1874 */     jl.setLocation(5, 80);
/* 1875 */     jl.setHorizontalAlignment(2);
/* 1876 */     jpAlphabet.add(jl);
/*      */     
/* 1878 */     JTextField jtfAlpha = new JTextField("" + (dicHeader[OTHERALPHA] + ((dicHeader[OTHERALPHA] < 0) ? 256 : 0)), 15);
/* 1879 */     jtfAlpha.setSize(145, 26);
/* 1880 */     jtfAlpha.setLocation(120, 80);
/* 1881 */     jtfAlpha.selectAll();
/* 1882 */     jtfAlpha.setHorizontalAlignment(2);
/* 1883 */     jpAlphabet.add(jtfAlpha);
/*      */     
/* 1885 */     JPanel jpR2L = new JPanel();
/* 1886 */     jpR2L.setLayout((LayoutManager)null);
/* 1887 */     jpR2L.setSize(275, 70);
/* 1888 */     jpR2L.setLocation(10, 316);
/* 1889 */     jpR2L.setOpaque(true);
/* 1890 */     jpR2L.setBorder(BorderFactory.createEtchedBorder());
/* 1891 */     jdlgDicProperties.add(jpR2L);
/*      */     
/* 1893 */     jl = new JLabel("Right to Left Control");
/* 1894 */     jl.setForeground(Def.COLOR_LABEL);
/* 1895 */     jl.setSize(270, 20);
/* 1896 */     jl.setLocation(5, 5);
/* 1897 */     jl.setHorizontalAlignment(2);
/* 1898 */     jpR2L.add(jl);
/*      */     
/* 1900 */     jcbR2L[0] = new JCheckBox("Words are Right to Left", (dicHeader[R2L_WORD] == 1));
/* 1901 */     jcbR2L[0].setForeground(Def.COLOR_LABEL);
/* 1902 */     jcbR2L[0].setOpaque(false);
/* 1903 */     jcbR2L[0].setSize(260, 20);
/* 1904 */     jcbR2L[0].setLocation(5, 25);
/* 1905 */     jcbR2L[0].setSelected((dicHeader[R2L_WORD] == 1));
/* 1906 */     jpR2L.add(jcbR2L[0]);
/*      */     
/* 1908 */     jcbR2L[1] = new JCheckBox("Clues are Right to Left", (dicHeader[AUXKB] == 1));
/* 1909 */     jcbR2L[1].setForeground(Def.COLOR_LABEL);
/* 1910 */     jcbR2L[1].setOpaque(false);
/* 1911 */     jcbR2L[1].setSize(260, 20);
/* 1912 */     jcbR2L[1].setLocation(5, 45);
/* 1913 */     jcbR2L[1].setSelected((dicHeader[R2L_CLUE] == 1));
/* 1914 */     jpR2L.add(jcbR2L[1]);
/*      */     
/* 1916 */     jcbDev = new JCheckBox("Use Devanagari alphabet", (dicHeader[DEVANAGARI] == 1));
/* 1917 */     jcbDev.setForeground(Def.COLOR_LABEL);
/* 1918 */     jcbDev.setOpaque(false);
/* 1919 */     jcbDev.setSize(260, 20);
/* 1920 */     jcbDev.setLocation(15, 391);
/*      */     
/* 1922 */     jdlgDicProperties.add(jcbDev);
/*      */     
/* 1924 */     JButton jbOK = Methods.cweButton("OK", 10, 421, 100, 26, null);
/* 1925 */     jbOK.addActionListener(e -> {
/*      */           byte[] byt = new byte[1024]; try {
/*      */             DataInputStream dataIn1 = new DataInputStream(new FileInputStream(Op.msc[Op.MSC.DictionaryName.ordinal()] + ".dic/xword.dic")); dataIn1.skip(128L);
/*      */             DataOutputStream dataOut = new DataOutputStream(new FileOutputStream(Op.msc[Op.MSC.DictionaryName.ordinal()] + ".dic/xword.new"));
/*      */             if (jrbLenSpec[0].isSelected()) {
/*      */               dicHeader[WORDLENGTH] = 0;
/*      */             } else if (jrbLenSpec[1].isSelected()) {
/*      */               dicHeader[WORDLENGTH] = 1;
/*      */             } else {
/*      */               dicHeader[WORDLENGTH] = 2;
/*      */             } 
/*      */             if (jrbCase[0].isSelected()) {
/*      */               dicHeader[CASE] = 0;
/*      */             } else {
/*      */               dicHeader[CASE] = 1;
/*      */             } 
/*      */             dicHeader[R2L_WORD] = (byte)(jcbR2L[0].isSelected() ? 1 : 0);
/*      */             dicHeader[R2L_CLUE] = (byte)(jcbR2L[1].isSelected() ? 1 : 0);
/*      */             dicHeader[AUXKB] = (byte)(paramJCheckBox.isSelected() ? 1 : 0);
/*      */             dicHeader[ALPHABETNUM] = intToByte(paramJComboBox.getSelectedIndex());
/*      */             dicHeader[OTHERALPHA] = intToByte(Integer.parseInt(paramJTextField.getText()));
/*      */             dicHeader[DEVANAGARI] = (byte)(jcbDev.isSelected() ? 1 : 0);
/*      */             dataOut.write(dicHeader, 0, 128);
/*      */             while (dataIn1.available() > 0)
/*      */               dataOut.write(byt, 0, dataIn1.read(byt, 0, 1024)); 
/*      */             dataOut.close();
/*      */             dataIn1.close();
/* 1952 */           } catch (IOException exc) {
/*      */             return;
/*      */           }  File fl = new File(Op.msc[Op.MSC.DictionaryName.ordinal()] + ".dic/xword.dic"); fl.delete();
/*      */           fl = new File(Op.msc[Op.MSC.DictionaryName.ordinal()] + ".dic/xword.new");
/*      */           fl.renameTo(new File(Op.msc[Op.MSC.DictionaryName.ordinal()] + ".dic/xword.dic"));
/*      */           Methods.clickedOK = true;
/*      */           paramJDialog.dispose();
/*      */           Methods.closeHelp();
/*      */         });
/* 1961 */     jdlgDicProperties.add(jbOK);
/*      */     
/* 1963 */     JButton jbCancel = Methods.cweButton("Cancel", 10, 456, 100, 26, null);
/* 1964 */     jbCancel.addActionListener(e -> {
/*      */           Methods.clickedOK = false;
/*      */           paramJDialog.dispose();
/*      */           Methods.closeHelp();
/*      */         });
/* 1969 */     jdlgDicProperties.add(jbCancel);
/*      */     
/* 1971 */     JButton jbHelp = Methods.cweButton("<html><font size=6 color=BB0000 face=Serif>Help ", 120, 421, 165, 61, new ImageIcon("graphics/help.png"));
/* 1972 */     jbHelp.addActionListener(e -> Methods.cweHelp(null, paramJDialog, "Edit Dictionary Properties", dicProperties));
/*      */     
/* 1974 */     jdlgDicProperties.add(jbHelp);
/*      */     
/* 1976 */     jdlgDicProperties.getRootPane().setDefaultButton(jbOK);
/* 1977 */     Methods.setDialogSize(jdlgDicProperties, 295, 491);
/*      */   }
/*      */   
/*      */   void dictionaryStats() {
/* 1981 */     int count = 0, total = 0, with = 0, without = 0, clues = 0, countList[] = new int[400], lenList[] = new int[50];
/* 1982 */     int loCount = 0;
/*      */     
/* 1984 */     char[] cList = new char[400];
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1989 */       DataInputStream dataIn = new DataInputStream(new FileInputStream(Op.msc[Op.MSC.DictionaryName.ordinal()] + ".dic/xword.dic"));
/* 1990 */       dataIn.read(dicHeader, 0, 128);
/* 1991 */       while (dataIn.available() > 2) {
/* 1992 */         int wordDat = dataIn.readInt();
/* 1993 */         if (wordDat % 256 == 255)
/* 1994 */           loCount++; 
/* 1995 */         String word = dataIn.readUTF();
/* 1996 */         String clue = dataIn.readUTF();
/*      */         
/* 1998 */         if (dicHeader[DEVANAGARI] == 1) {
/* 1999 */           int k; if ((k = DevanagariBuild.devLength(word)) > 1) { lenList[k] = lenList[k] + 1; }
/*      */           else { continue; }
/*      */         
/*      */         } else {
/* 2003 */           lenList[word.length()] = lenList[word.length()] + 1;
/*      */         } 
/* 2005 */         total++;
/* 2006 */         if (!clue.isEmpty()) {
/* 2007 */           with++;
/* 2008 */           clues++; int k;
/* 2009 */           while ((k = clue.indexOf("*")) != -1) {
/* 2010 */             clues++;
/* 2011 */             clue = clue.substring(k + 1);
/*      */           } 
/*      */         } else {
/*      */           
/* 2015 */           without++;
/* 2016 */         }  int j; for (j = 0; j < count; j++) {
/* 2017 */           if (word.charAt(0) == cList[j]) {
/* 2018 */             countList[j] = countList[j] + 1; break;
/*      */           } 
/*      */         } 
/* 2021 */         if (j == count) {
/* 2022 */           cList[j] = word.charAt(0);
/* 2023 */           countList[j] = countList[j] + 1;
/* 2024 */           count++;
/*      */         } 
/*      */       } 
/* 2027 */       dataIn.close();
/*      */     }
/* 2029 */     catch (IOException exc) {}
/*      */     
/* 2031 */     this.jlTotalDat.setText("<html><font size=4 color='880000'>" + total);
/* 2032 */     this.jlLockoutDat.setText("<html><font size=4 color='880000'>" + loCount);
/* 2033 */     this.jlWithDat.setText("<html><font size=4 color='880000'>" + with);
/* 2034 */     this.jlWODat.setText("<html><font size=4 color='880000'>" + without);
/* 2035 */     this.jlCluesDat.setText("<html><font size=4 color='880000'>" + clues);
/*      */     int i, len;
/* 2037 */     for (i = len = 0; i < count; i++) {
/* 2038 */       StringBuffer sb = new StringBuffer("  " + cList[i] + "  " + countList[i]);
/* 2039 */       if (sb.length() > len)
/* 2040 */         len = sb.length(); 
/*      */     } 
/* 2042 */     this.lmFirstLetter.clear();
/* 2043 */     for (i = 0; i < count; i++) {
/* 2044 */       StringBuffer sb = new StringBuffer("  " + cList[i] + "  " + countList[i]);
/* 2045 */       while (sb.length() < len)
/* 2046 */         sb.insert(3, " "); 
/* 2047 */       this.lmFirstLetter.addElement(new String(sb));
/*      */     } 
/*      */     
/* 2050 */     for (i = len = 2; i < 50; i++) {
/* 2051 */       if (lenList[i] != 0) {
/* 2052 */         StringBuffer sb = new StringBuffer("  " + i + "  " + lenList[i]);
/* 2053 */         if (sb.length() > len)
/* 2054 */           len = sb.length(); 
/*      */       } 
/* 2056 */     }  this.lmLength.clear();
/* 2057 */     for (i = 2; i < 50; i++) {
/* 2058 */       if (lenList[i] != 0) {
/* 2059 */         StringBuffer sb = new StringBuffer("  " + i + "  " + lenList[i]);
/* 2060 */         while (sb.length() < len)
/* 2061 */           sb.insert(4, " "); 
/* 2062 */         this.lmLength.addElement(new String(sb));
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   static void listUpdate(int mode) {
/*      */     int ensure;
/* 2070 */     if (inhibitListUpdate.booleanValue())
/*      */       return; 
/* 2072 */     editIndex = jlWord.getSelectedIndex();
/* 2073 */     if (editIndex < 8) { ensure = 0; }
/* 2074 */     else if (editIndex >= lmWord.size() - 9) { ensure = lmWord.size() - 1; }
/* 2075 */     else if (editIndex - jlWord.getFirstVisibleIndex() < 8) { ensure = editIndex - 8; }
/* 2076 */     else { ensure = editIndex + 8; }
/* 2077 */      jlWord.ensureIndexIsVisible(ensure);
/* 2078 */     if (mode == 2)
/*      */       return; 
/* 2080 */     String wc = lmWord.getElementAt(editIndex);
/* 2081 */     int j = wc.indexOf(' ');
/* 2082 */     listWord = wc.substring(0, j);
/* 2083 */     listClue = wc.substring(j + 3);
/* 2084 */     focusWord = Boolean.valueOf(false);
/* 2085 */     clueTimer.start();
/*      */   }
/*      */   
/*      */   static void deleteCurrentWord(JDialog theDialog) {
/* 2089 */     if (JOptionPane.showConfirmDialog(theDialog, "<html>Are you sure you want to delete the word <font color=880000>" + ((String)jlWord
/*      */         
/* 2091 */         .getSelectedValue()).substring(0, ((String)jlWord.getSelectedValue()).indexOf(' ')), "Warning", 0) == 1) {
/*      */       return;
/*      */     }
/*      */     
/* 2095 */     inhibitListUpdate = Boolean.valueOf(true);
/*      */     
/* 2097 */     lmWord.removeElementAt(editIndex);
/* 2098 */     jlWord.setSelectedIndex(0);
/* 2099 */     listWord = listClue = "";
/* 2100 */     focusWord = Boolean.valueOf(true);
/* 2101 */     clueTimer.start();
/* 2102 */     wordsDeleted++;
/* 2103 */     jlDeletedScore.setText("<html><font size=4>" + wordsDeleted);
/* 2104 */     jlTotWordsScore.setText("<html><font size=4>" + lmWord.getSize());
/* 2105 */     inhibitListUpdate = Boolean.valueOf(false);
/*      */   }
/*      */   
/*      */   static void editDictionary(final JFrame jfParent, String theDictionary) {
/* 2109 */     String s = "", s2 = "";
/*      */ 
/*      */     
/* 2112 */     ActionListener timerClu = ae -> {
/*      */         jtfWord.setText(listWord); jtaClue.setText(listClue); if (focusWord.booleanValue()) {
/*      */           jtfWord.requestFocusInWindow();
/*      */         } else {
/*      */           jtaClue.requestFocusInWindow();
/*      */         }  clueTimer.stop();
/*      */       };
/* 2119 */     clueTimer = new Timer(100, timerClu);
/*      */     
/* 2121 */     ActionListener timerListen = ae -> {
/*      */         inhibitListener = Boolean.valueOf(false);
/*      */         listenerTimer.stop();
/*      */       };
/* 2125 */     listenerTimer = new Timer(100, timerListen);
/*      */     
/* 2127 */     Op.msc[Op.MSC.DictionaryName.ordinal()] = theDictionary;
/* 2128 */     editIndex = -1;
/*      */     
/* 2130 */     final JDialog jdlgEditDic = new JDialog(jfParent, "Edit " + Op.msc[Op.MSC.DictionaryName.ordinal()] + " Dictionary", true);
/* 2131 */     jdlgEditDic.setSize(570, 595);
/* 2132 */     jdlgEditDic.setResizable(false);
/* 2133 */     jdlgEditDic.setLayout((LayoutManager)null);
/* 2134 */     jdlgEditDic.setLocation(jfParent.getX(), jfParent.getY());
/*      */     
/* 2136 */     jdlgEditDic
/* 2137 */       .addWindowListener(new WindowAdapter() {
/*      */           public void windowClosing(WindowEvent we) {
/* 2139 */             if ((DictionaryMtce.wordsAdded > 0 || DictionaryMtce.cluesChanged > 0 || DictionaryMtce.wordsDeleted > 0) && 
/* 2140 */               JOptionPane.showConfirmDialog(jdlgEditDic, "<html>You have made changes to this dictionary.<br>Do you want to save the changes?", "Warning", 0) == 0)
/*      */             {
/*      */               
/* 2143 */               DictionaryMtce.saveDictionary(); } 
/* 2144 */             jdlgEditDic.dispose();
/* 2145 */             jfParent.setVisible(true);
/* 2146 */             Methods.closeHelp();
/* 2147 */             DictionaryMtce.wordsAdded = DictionaryMtce.cluesChanged = DictionaryMtce.wordsDeleted = 0;
/*      */           }
/*      */         });
/*      */     
/* 2151 */     Methods.closeHelp();
/*      */ 
/*      */     
/* 2154 */     editMenuBar = new JMenuBar();
/* 2155 */     editMenuBar.setBackground(Def.COLOR_MENUBAR);
/* 2156 */     jdlgEditDic.setJMenuBar(editMenuBar);
/*      */     
/* 2158 */     menu = new JMenu("File");
/* 2159 */     editMenuBar.add(menu);
/* 2160 */     menuItem = new JMenuItem("Save and Quit");
/* 2161 */     menuItem.setAccelerator(KeyStroke.getKeyStroke(83, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 2162 */     menu.add(menuItem);
/* 2163 */     menuItem
/* 2164 */       .addActionListener(ae -> {
/*      */           saveDictionary();
/*      */           
/*      */           paramJDialog.dispose();
/*      */           
/*      */           paramJFrame.setVisible(true);
/*      */           Methods.closeHelp();
/*      */           wordsAdded = cluesChanged = wordsDeleted = 0;
/*      */         });
/* 2173 */     menuItem = new JMenuItem("Save and Resume");
/* 2174 */     menuItem.setAccelerator(KeyStroke.getKeyStroke(82, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 2175 */     menu.add(menuItem);
/* 2176 */     menuItem
/* 2177 */       .addActionListener(ae -> {
/*      */           saveDictionary();
/*      */           
/*      */           wordsAdded = cluesChanged = wordsDeleted = 0;
/*      */         });
/*      */     
/* 2183 */     menuItem = new JMenuItem("Quit Editing");
/* 2184 */     menu.add(menuItem);
/* 2185 */     menuItem.setAccelerator(KeyStroke.getKeyStroke(81, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 2186 */     menuItem
/* 2187 */       .addActionListener(ae -> {
/*      */           if ((wordsAdded > 0 || cluesChanged > 0 || wordsDeleted > 0) && JOptionPane.showConfirmDialog(paramJDialog, "<html>You have made changes to this dictionary.<br>Do you want to save the changes?", "Warning", 0) == 0) {
/*      */             saveDictionary();
/*      */           }
/*      */           
/*      */           paramJDialog.dispose();
/*      */           
/*      */           paramJFrame.setVisible(true);
/*      */           
/*      */           Methods.closeHelp();
/*      */           
/*      */           wordsAdded = cluesChanged = wordsDeleted = 0;
/*      */         });
/*      */     
/* 2201 */     menu = new JMenu("Edit");
/* 2202 */     editMenuBar.add(menu);
/* 2203 */     menuItem = new JMenuItem("Select All");
/* 2204 */     menu.add(menuItem);
/* 2205 */     menuItem.setAccelerator(KeyStroke.getKeyStroke(65, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 2206 */     menuItem
/* 2207 */       .addActionListener(ae -> {
/*      */           if (editWord.booleanValue()) {
/*      */             jtfWord.selectAll();
/*      */           } else {
/*      */             jtaClue.selectAll();
/*      */             jtaClue.requestFocusInWindow();
/*      */           } 
/*      */           listenerTimer.start();
/*      */         });
/* 2216 */     menuItem = new JMenuItem("Cut");
/* 2217 */     menu.add(menuItem);
/* 2218 */     menuItem.setAccelerator(KeyStroke.getKeyStroke(88, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 2219 */     menuItem
/* 2220 */       .addActionListener(ae -> {
/*      */           inhibitListener = Boolean.valueOf(true); if (editWord.booleanValue()) {
/*      */             jtfWord.cut();
/*      */             jtfWord.requestFocusInWindow();
/*      */           } else {
/*      */             jtaClue.cut();
/*      */             jtaClue.requestFocusInWindow();
/*      */           } 
/*      */           listenerTimer.start();
/*      */         });
/* 2230 */     menuItem = new JMenuItem("Copy");
/* 2231 */     menu.add(menuItem);
/* 2232 */     menuItem.setAccelerator(KeyStroke.getKeyStroke(67, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 2233 */     menuItem
/* 2234 */       .addActionListener(ae -> {
/*      */           if (editWord.booleanValue()) {
/*      */             jtfWord.copy(); jtfWord.requestFocusInWindow();
/*      */           } else {
/*      */             jtaClue.copy();
/*      */             jtaClue.requestFocusInWindow();
/*      */           } 
/*      */         });
/* 2242 */     menuItem = new JMenuItem("Paste");
/* 2243 */     menu.add(menuItem);
/* 2244 */     menuItem.setAccelerator(KeyStroke.getKeyStroke(86, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 2245 */     menuItem
/* 2246 */       .addActionListener(ae -> {
/*      */           if (editWord.booleanValue()) {
/*      */             jtfWord.paste(); jtfWord.requestFocusInWindow();
/*      */           } else {
/*      */             jtaClue.paste();
/*      */             jtaClue.requestFocusInWindow();
/*      */           } 
/*      */         });
/* 2254 */     menuItem = new JMenuItem("Delete this Word");
/* 2255 */     menuItem.setAccelerator(KeyStroke.getKeyStroke(68, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/* 2256 */     menu.add(menuItem);
/* 2257 */     menuItem
/* 2258 */       .addActionListener(ae -> deleteCurrentWord(paramJDialog));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2263 */     JButton jbSelectAll = Methods.cweButton("Select All", 470, 357, 100, 26, null);
/* 2264 */     jbSelectAll.addActionListener(e -> {
/*      */           if (editWord.booleanValue()) {
/*      */             jtfWord.selectAll();
/*      */           } else {
/*      */             jtaClue.selectAll(); jtaClue.requestFocusInWindow();
/*      */           }  listenerTimer.start();
/*      */         });
/* 2271 */     jdlgEditDic.add(jbSelectAll);
/*      */     
/* 2273 */     JButton jbCut = Methods.cweButton("Cut", 470, 392, 100, 26, null);
/* 2274 */     jbCut.addActionListener(e -> {
/*      */           inhibitListener = Boolean.valueOf(true); if (editWord.booleanValue()) {
/*      */             jtfWord.cut(); jtfWord.requestFocusInWindow();
/*      */           } else {
/*      */             jtaClue.cut(); jtaClue.requestFocusInWindow();
/*      */           } 
/*      */           listenerTimer.start();
/*      */         });
/* 2282 */     jdlgEditDic.add(jbCut);
/*      */     
/* 2284 */     JButton jbCopy = Methods.cweButton("Copy", 470, 427, 100, 26, null);
/* 2285 */     jbCopy.addActionListener(e -> {
/*      */           if (editWord.booleanValue()) {
/*      */             jtfWord.copy(); jtfWord.requestFocusInWindow();
/*      */           } else {
/*      */             jtaClue.copy(); jtaClue.requestFocusInWindow();
/*      */           } 
/* 2291 */         }); jdlgEditDic.add(jbCopy);
/*      */     
/* 2293 */     JButton jbPaste = Methods.cweButton("Paste", 470, 462, 100, 26, null);
/* 2294 */     jbPaste.addActionListener(e -> {
/*      */           if (editWord.booleanValue()) {
/*      */             jtfWord.paste(); jtfWord.requestFocusInWindow();
/*      */           } else {
/*      */             jtaClue.paste(); jtaClue.requestFocusInWindow();
/*      */           } 
/* 2300 */         }); jdlgEditDic.add(jbPaste);
/*      */     
/* 2302 */     JButton jbDelete = Methods.cweButton("Delete Current Word", 580, 357, 180, 26, null);
/* 2303 */     jbDelete.addActionListener(e -> deleteCurrentWord(paramJDialog));
/*      */     
/* 2305 */     jdlgEditDic.add(jbDelete);
/*      */     
/* 2307 */     JButton jbAttach = Methods.cweButton("Attach Auxiliary Keyboard", 580, 392, 180, 26, null);
/* 2308 */     jbAttach.addActionListener(e -> { Methods.setDialogSize(paramJDialog, 1340, 595 + editMenuBar.getHeight()); if (jtfWord.getText().length() == 0) {
/*      */             jtfWord.requestFocusInWindow();
/*      */           } else {
/*      */             jtaClue.requestFocusInWindow();
/*      */           } 
/* 2313 */         }); jdlgEditDic.add(jbAttach);
/*      */     
/* 2315 */     JButton jbDetach = Methods.cweButton("Detach Auxiliary Keyboard", 580, 427, 180, 26, null);
/* 2316 */     jbDetach.addActionListener(e -> { Methods.setDialogSize(paramJDialog, 770, 595 + editMenuBar.getHeight()); if (jtfWord.getText().length() == 0) {
/*      */             jtfWord.requestFocusInWindow();
/*      */           } else {
/*      */             jtaClue.requestFocusInWindow();
/*      */           } 
/* 2321 */         }); jdlgEditDic.add(jbDetach);
/*      */     
/* 2323 */     menu = new JMenu("Task");
/* 2324 */     editMenuBar.add(menu);
/* 2325 */     menuItem = new JMenuItem("Attach Auxiliary Keyboard");
/* 2326 */     menu.add(menuItem);
/* 2327 */     menuItem.setAccelerator(KeyStroke.getKeyStroke(75, 2));
/* 2328 */     menuItem
/* 2329 */       .addActionListener(ae -> {
/*      */           Methods.setDialogSize(paramJDialog, 1340, 595 + editMenuBar.getHeight()); if (jtfWord.getText().length() == 0) {
/*      */             jtfWord.requestFocusInWindow();
/*      */           } else {
/*      */             jtaClue.requestFocusInWindow();
/*      */           } 
/*      */         });
/* 2336 */     menuItem = new JMenuItem("Detach Auxiliary Keyboard");
/* 2337 */     menu.add(menuItem);
/* 2338 */     menuItem.setAccelerator(KeyStroke.getKeyStroke(89, 2));
/* 2339 */     menuItem
/* 2340 */       .addActionListener(ae -> {
/*      */           Methods.setDialogSize(paramJDialog, 770, 595 + editMenuBar.getHeight());
/*      */           if (jtfWord.getText().length() == 0) {
/*      */             jtfWord.requestFocusInWindow();
/*      */           } else {
/*      */             jtaClue.requestFocusInWindow();
/*      */           } 
/*      */         });
/* 2348 */     menu = new JMenu("Help");
/* 2349 */     editMenuBar.add(menu);
/* 2350 */     menuItem = new JMenuItem("Dictionary Edit Help");
/* 2351 */     menu.add(menuItem);
/* 2352 */     menuItem.setAccelerator(KeyStroke.getKeyStroke(72, 2));
/* 2353 */     menuItem
/* 2354 */       .addActionListener(ae -> Methods.cweHelp(null, paramJDialog, "Editing a Dictionary", editDic));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2360 */     JLabel jl = new JLabel("<html><font size=5>WORDS & Clues");
/* 2361 */     jl.setForeground(Def.COLOR_LABEL);
/* 2362 */     jl.setSize(180, 16);
/* 2363 */     jl.setLocation(10, 5);
/* 2364 */     jl.setHorizontalAlignment(2);
/* 2365 */     jdlgEditDic.add(jl);
/*      */     
/* 2367 */     lmWord = new DefaultListModel<>();
/* 2368 */     jlWord = new JList<>(lmWord);
/* 2369 */     jlWord.setFont(new Font("SansSerif", 0, 18));
/* 2370 */     jspWord = new JScrollPane(jlWord);
/* 2371 */     jspWord.setSize(750, 309);
/* 2372 */     jspWord.setLocation(10, 25);
/* 2373 */     jspWord.setHorizontalScrollBarPolicy(31);
/* 2374 */     jdlgEditDic.add(jspWord);
/* 2375 */     jlWord
/* 2376 */       .addListSelectionListener(le -> {
/*      */           if (!jlWord.getValueIsAdjusting()) {
/*      */             listUpdate(0);
/*      */           }
/*      */         });
/*      */     
/* 2382 */     jl = new JLabel("Edit Word");
/* 2383 */     jl.setForeground(Def.COLOR_LABEL);
/* 2384 */     jl.setSize(160, 16);
/* 2385 */     jl.setLocation(10, 337);
/* 2386 */     jl.setHorizontalAlignment(2);
/* 2387 */     jdlgEditDic.add(jl);
/*      */     
/* 2389 */     jtfWord = new JTextField("", 15);
/* 2390 */     jtfWord.setHorizontalAlignment(2);
/* 2391 */     if (dicHeader[R2L_WORD] == 1) {
/* 2392 */       jtfWord.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
/* 2393 */       jtfWord.setHorizontalAlignment(4);
/*      */     } 
/* 2395 */     jtfWord.setSize(450, 28);
/* 2396 */     jtfWord.setLocation(10, 357);
/* 2397 */     jtfWord.selectAll();
/* 2398 */     jdlgEditDic.add(jtfWord);
/*      */     
/* 2400 */     jtfWord.setFont(new Font("SansSerif", 0, 20));
/* 2401 */     jtfWord
/* 2402 */       .addActionListener(le -> {
/*      */           if (jtfWord.getText().trim().length() == 0)
/*      */             return; 
/*      */           jtaClue.requestFocusInWindow();
/*      */         });
/* 2407 */     jtfWord
/* 2408 */       .addFocusListener(new FocusListener() { public void focusGained(FocusEvent fe) {
/* 2409 */             DictionaryMtce.editWord = Boolean.valueOf(true);
/*      */           }
/*      */           public void focusLost(FocusEvent fe) {} }
/*      */       );
/* 2413 */     jtfWord
/* 2414 */       .addCaretListener(ce -> {
/*      */           if (inhibitListener.booleanValue()) {
/*      */             return;
/*      */           }
/*      */           if (lmWord.isEmpty()) {
/*      */             return;
/*      */           }
/*      */           inhibitListUpdate = Boolean.valueOf(true);
/*      */           String newword = jtfWord.getText().trim();
/*      */           int i = jlWord.getNextMatch(newword, 0, Position.Bias.Forward);
/*      */           if (i == -1) {
/*      */             jtaClue.setText("");
/*      */             if (newword.length() == 1)
/*      */               jlWord.setSelectedIndex(0); 
/*      */             jlWord.ensureIndexIsVisible(editIndex);
/*      */             inhibitListUpdate = Boolean.valueOf(false);
/*      */             return;
/*      */           } 
/*      */           String s1 = lmWord.getElementAt(editIndex = i);
/*      */           s1 = s1.substring(0, s1.indexOf(' '));
/*      */           String oldword = (i >= 0) ? s1 : "";
/*      */           if (newword.equalsIgnoreCase(oldword)) {
/*      */             String oldclue = lmWord.getElementAt(editIndex);
/*      */             int j = oldclue.indexOf(' ');
/*      */             listWord = (dicHeader[CASE] == 0) ? newword.toUpperCase() : newword.toLowerCase();
/*      */             listClue = oldclue.substring(j + 3);
/*      */             focusWord = Boolean.valueOf(true);
/*      */             clueTimer.start();
/*      */           } 
/*      */           jtaClue.setText("");
/*      */           jlWord.setSelectedIndex(editIndex);
/*      */           jlWord.ensureIndexIsVisible(editIndex);
/*      */           inhibitListUpdate = Boolean.valueOf(false);
/*      */         });
/* 2448 */     jl = new JLabel("Edit Clue");
/* 2449 */     jl.setForeground(Def.COLOR_LABEL);
/* 2450 */     jl.setSize(90, 16);
/* 2451 */     jl.setLocation(10, 390);
/* 2452 */     jl.setHorizontalAlignment(2);
/* 2453 */     jdlgEditDic.add(jl);
/*      */     
/* 2455 */     jtaClue = new JTextArea();
/* 2456 */     if (dicHeader[R2L_CLUE] == 1)
/* 2457 */       jtaClue.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT); 
/* 2458 */     jtaClue.setLineWrap(true);
/* 2459 */     jtaClue.setWrapStyleWord(true);
/* 2460 */     jtaClue.setBorder(BorderFactory.createLineBorder(new Color(16777215), 4));
/* 2461 */     JScrollPane jscrlp = new JScrollPane(jtaClue);
/* 2462 */     jscrlp.setSize(450, 175);
/* 2463 */     jscrlp.setLocation(10, 410);
/* 2464 */     jdlgEditDic.add(jscrlp);
/* 2465 */     jtaClue.setFont(new Font("SansSerif", 0, 20));
/*      */     
/* 2467 */     jtaClue
/* 2468 */       .addFocusListener(new FocusListener() {
/*      */           public void focusGained(FocusEvent fe) {
/* 2470 */             String clue = " (";
/*      */ 
/*      */ 
/*      */             
/* 2474 */             DictionaryMtce.inhibitListener = Boolean.valueOf(true);
/* 2475 */             DictionaryMtce.editWord = Boolean.valueOf(false);
/* 2476 */             if (DictionaryMtce.jtfWord.getText().trim().length() == 0) {
/* 2477 */               DictionaryMtce.jtfWord.requestFocusInWindow();
/* 2478 */             } else if (DictionaryMtce.jtaClue.getText().length() == 0) {
/* 2479 */               String word = DictionaryMtce.jtfWord.getText().trim();
/* 2480 */               word = (DictionaryMtce.dicHeader[DictionaryMtce.CASE] == 0) ? word.toUpperCase() : word.toLowerCase();
/* 2481 */               DictionaryMtce.jtfWord.setText(word); int count;
/* 2482 */               for (int i = 0; i < word.length(); i++) {
/* 2483 */                 char ch = word.charAt(i);
/* 2484 */                 if (Character.isLetter(ch)) {
/* 2485 */                   count++;
/* 2486 */                 } else if (ch == ' ' || ch == '-') {
/* 2487 */                   if (ch == ' ') ch = ','; 
/* 2488 */                   if (count > 0) {
/* 2489 */                     clue = clue + count + ch;
/* 2490 */                     count = 0;
/*      */                   } 
/*      */                 } 
/*      */               } 
/* 2494 */               clue = clue + count + ")";
/* 2495 */               if (clue.length() > 5) {
/* 2496 */                 word = word.replace(" ", "");
/* 2497 */                 word = word.replace("-", "");
/* 2498 */                 DictionaryMtce.jtfWord.setText(word);
/*      */               } 
/*      */               
/* 2501 */               if ((DictionaryMtce.dicHeader[DictionaryMtce.WORDLENGTH] == 2 && clue.length() > 5) || DictionaryMtce.dicHeader[DictionaryMtce.WORDLENGTH] == 1) {
/* 2502 */                 DictionaryMtce.jtaClue.setText(clue);
/* 2503 */                 DictionaryMtce.jtaClue.setCaretPosition(0);
/*      */               } 
/*      */             } 
/* 2506 */             DictionaryMtce.listenerTimer.start();
/*      */           }
/*      */           
/*      */           public void focusLost(FocusEvent fe) {}
/*      */         });
/* 2511 */     jtaClue
/* 2512 */       .addCaretListener(ce -> {
/*      */           char[] clueArray = new char[2048];
/*      */           if (inhibitListener.booleanValue()) {
/*      */             return;
/*      */           }
/*      */           String newClue = jtaClue.getText();
/*      */           if (newClue.length() == 0) {
/*      */             return;
/*      */           }
/*      */           if (newClue.indexOf('\n') != -1 || newClue.indexOf('\t') != -1) {
/*      */             String oldClue;
/*      */             String oldWord;
/*      */             int i = 0;
/*      */             int j = i;
/*      */             while (i < newClue.length()) {
/*      */               char ch = newClue.charAt(i);
/*      */               if (ch != '\n' && ch != '\t') {
/*      */                 clueArray[j++] = ch;
/*      */               }
/*      */               i++;
/*      */             } 
/*      */             newClue = (new String(clueArray, 0, j)).trim();
/*      */             String newWord = jtfWord.getText().trim();
/*      */             if (editIndex >= 0) {
/*      */               String wc = lmWord.getElementAt(editIndex);
/*      */               j = wc.indexOf(' ');
/*      */               oldWord = wc.substring(0, j);
/*      */               oldClue = wc.substring(j + 3);
/*      */             } else {
/*      */               oldWord = oldClue = "";
/*      */             } 
/*      */             if (oldWord.equals(newWord)) {
/*      */               if (!oldClue.equals(newClue)) {
/*      */                 lmWord.set(editIndex, newWord + "   " + newClue);
/*      */                 cluesChanged++;
/*      */                 jlChangedScore.setText("<html><font size=4>" + cluesChanged);
/*      */               } 
/*      */             } else {
/*      */               while (newWord.compareTo(oldWord) >= 0) {
/*      */                 if (++editIndex == lmWord.getSize())
/*      */                   break; 
/*      */                 String wc = lmWord.getElementAt(editIndex);
/*      */                 oldWord = wc.substring(0, wc.indexOf(' '));
/*      */               } 
/*      */               if (editIndex == lmWord.getSize()) {
/*      */                 lmWord.addElement(newWord + "   " + newClue);
/*      */               } else {
/*      */                 lmWord.add(editIndex, newWord + "   " + newClue);
/*      */               } 
/*      */               wordsAdded++;
/*      */               jlAddedScore.setText("<html><font size=4>" + wordsAdded);
/*      */               jlTotWordsScore.setText("<html><font size=4>" + lmWord.getSize());
/*      */             } 
/*      */             listWord = listClue = "";
/*      */             focusWord = Boolean.valueOf(true);
/*      */             clueTimer.start();
/*      */           } 
/*      */         });
/* 2570 */     jlAdded = new JLabel("<html><font size=4>Words Added");
/* 2571 */     jlAdded.setForeground(Def.COLOR_LABEL);
/* 2572 */     jlAdded.setSize(110, 16);
/* 2573 */     jlAdded.setLocation(545, 498);
/* 2574 */     jlAdded.setHorizontalAlignment(2);
/* 2575 */     jdlgEditDic.add(jlAdded);
/*      */     
/* 2577 */     jlAddedScore = new JLabel("<html><font size=4>0");
/* 2578 */     jlAddedScore.setForeground(Def.COLOR_LABEL);
/* 2579 */     jlAddedScore.setSize(100, 16);
/* 2580 */     jlAddedScore.setLocation(665, 498);
/* 2581 */     jlAddedScore.setHorizontalAlignment(2);
/* 2582 */     jdlgEditDic.add(jlAddedScore);
/*      */     
/* 2584 */     jlChanged = new JLabel("<html><font size=4>Clues Changed");
/* 2585 */     jlChanged.setForeground(Def.COLOR_LABEL);
/* 2586 */     jlChanged.setSize(110, 16);
/* 2587 */     jlChanged.setLocation(545, 521);
/* 2588 */     jlChanged.setHorizontalAlignment(2);
/* 2589 */     jdlgEditDic.add(jlChanged);
/*      */     
/* 2591 */     jlChangedScore = new JLabel("<html><font size=4>0");
/* 2592 */     jlChangedScore.setForeground(Def.COLOR_LABEL);
/* 2593 */     jlChangedScore.setSize(100, 16);
/* 2594 */     jlChangedScore.setLocation(665, 521);
/* 2595 */     jlChangedScore.setHorizontalAlignment(2);
/* 2596 */     jdlgEditDic.add(jlChangedScore);
/*      */     
/* 2598 */     jlDeleted = new JLabel("<html><font size=4>Words Deleted");
/* 2599 */     jlDeleted.setForeground(Def.COLOR_LABEL);
/* 2600 */     jlDeleted.setSize(110, 16);
/* 2601 */     jlDeleted.setLocation(545, 544);
/* 2602 */     jlDeleted.setHorizontalAlignment(2);
/* 2603 */     jdlgEditDic.add(jlDeleted);
/*      */     
/* 2605 */     jlDeletedScore = new JLabel("<html><font size=4>0");
/* 2606 */     jlDeletedScore.setForeground(Def.COLOR_LABEL);
/* 2607 */     jlDeletedScore.setSize(100, 16);
/* 2608 */     jlDeletedScore.setLocation(665, 544);
/* 2609 */     jlDeletedScore.setHorizontalAlignment(2);
/* 2610 */     jdlgEditDic.add(jlDeletedScore);
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 2615 */       DataInputStream dataIn = new DataInputStream(new FileInputStream(Op.msc[Op.MSC.DictionaryName.ordinal()] + ".dic/xword.dic"));
/* 2616 */       dataIn.read(dicHeader, 0, 128);
/* 2617 */       while (dataIn.available() > 2) {
/* 2618 */         dataIn.readInt();
/* 2619 */         s = dataIn.readUTF();
/* 2620 */         s2 = dataIn.readUTF();
/* 2621 */         lmWord.addElement(s + "   " + s2);
/*      */       } 
/* 2623 */       dataIn.close();
/*      */     }
/* 2625 */     catch (IOException exc) {}
/*      */     
/* 2627 */     jlTotWords = new JLabel("<html><font size=4>Total Words");
/* 2628 */     jlTotWords.setForeground(Def.COLOR_LABEL);
/* 2629 */     jlTotWords.setSize(110, 16);
/* 2630 */     jlTotWords.setLocation(545, 567);
/* 2631 */     jlTotWords.setHorizontalAlignment(2);
/* 2632 */     jdlgEditDic.add(jlTotWords);
/*      */     
/* 2634 */     jlTotWordsScore = new JLabel("<html><font size=4>" + lmWord.getSize());
/* 2635 */     jlTotWordsScore.setForeground(Def.COLOR_LABEL);
/* 2636 */     jlTotWordsScore.setSize(100, 16);
/* 2637 */     jlTotWordsScore.setLocation(665, 567);
/* 2638 */     jlTotWordsScore.setHorizontalAlignment(2);
/* 2639 */     jdlgEditDic.add(jlTotWordsScore);
/*      */ 
/*      */     
/* 2642 */     SpinnerNumberModel spm = new SpinnerNumberModel(0, 0, 255, 1);
/* 2643 */     JSpinner jspin = new JSpinner(spm);
/* 2644 */     jspin.setSize(50, 24);
/* 2645 */     jspin.setLocation(1280, 0);
/* 2646 */     jdlgEditDic.add(jspin);
/* 2647 */     JLabel jlT = new JLabel("Select Keyboard");
/* 2648 */     jlT.setSize(160, 21);
/* 2649 */     jlT.setLocation(1110, 2);
/* 2650 */     jlT.setHorizontalAlignment(4);
/* 2651 */     jdlgEditDic.add(jlT);
/*      */     
/* 2653 */     jlWord.setSelectedIndex(0);
/* 2654 */     jlWord.ensureIndexIsVisible(0);
/* 2655 */     jtfWord.requestFocusInWindow();
/*      */     
/* 2657 */     jspin
/* 2658 */       .addChangeListener(ce -> {
/*      */           dicHeader[OTHERALPHA] = intToByte(paramSpinnerNumberModel.getNumber().intValue());
/*      */           if (dicHeader[OTHERALPHA] == 0) {
/*      */             dicHeader[ALPHABETNUM] = 0;
/*      */           }
/*      */           AuxKB.base = byteToInt(dicHeader[OTHERALPHA]);
/*      */           kbp.repaint();
/*      */           jlChart.setText(AuxKB.getAlphabetName());
/*      */           if (jtfWord.getText().length() == 0) {
/*      */             jtfWord.requestFocusInWindow();
/*      */           } else {
/*      */             jtaClue.requestFocusInWindow();
/*      */           } 
/*      */         });
/* 2672 */     AuxKB.base = byteToInt(dicHeader[OTHERALPHA]);
/* 2673 */     if (AuxKB.base == 0)
/* 2674 */       AuxKB.base = byteToInt(dicHeader[ALPHABETNUM]); 
/* 2675 */     jlChart = new JLabel(AuxKB.getAlphabetName());
/* 2676 */     jlChart.setSize(300, 16);
/* 2677 */     jlChart.setLocation(770, 5);
/* 2678 */     jlChart.setHorizontalAlignment(2);
/* 2679 */     jdlgEditDic.add(jlChart);
/*      */     
/* 2681 */     kbp = new AuxKB(770, 25, jdlgEditDic);
/* 2682 */     kbp.addMouseListener(new MouseAdapter() {
/* 2683 */           public void mousePressed(MouseEvent e) { AuxKB.getCharacter(e, DictionaryMtce.jtaClue.isFocusOwner() ? DictionaryMtce.jtaClue : null, DictionaryMtce.jtfWord.isFocusOwner() ? DictionaryMtce.jtfWord : null); }
/*      */         });
/* 2685 */     jdlgEditDic.pack();
/* 2686 */     listWord = listClue = "";
/* 2687 */     focusWord = Boolean.valueOf(true);
/* 2688 */     clueTimer.start();
/* 2689 */     Methods.setDialogSize(jdlgEditDic, (dicHeader[AUXKB] == 1) ? 1040 : 770, 595 + editMenuBar.getHeight());
/*      */   }
/*      */   
/*      */   static int byteToInt(byte bv) {
/* 2693 */     int target = bv;
/* 2694 */     if (target < 0) target += 256; 
/* 2695 */     return target;
/*      */   }
/*      */   
/*      */   static byte intToByte(int v) {
/*      */     byte target;
/* 2700 */     if (v > 127) {
/* 2701 */       target = (byte)(v - 256);
/*      */     } else {
/* 2703 */       target = (byte)v;
/* 2704 */     }  return target;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void saveDictionary() {
/*      */     try {
/* 2712 */       DataOutputStream dataOut = new DataOutputStream(new FileOutputStream(Op.msc[Op.MSC.DictionaryName.ordinal()] + ".dic/xword.dic"));
/* 2713 */       dataOut.write(dicHeader, 0, 128);
/* 2714 */       for (int i = 0; i < lmWord.size(); i++) {
/* 2715 */         dataOut.writeInt(0);
/* 2716 */         String wc = lmWord.getElementAt(i);
/* 2717 */         int j = wc.indexOf(' ');
/* 2718 */         dataOut.writeUTF(wc.substring(0, j));
/* 2719 */         dataOut.writeUTF(wc.substring(j + 3));
/*      */       } 
/* 2721 */       dataOut.close();
/*      */     }
/* 2723 */     catch (IOException exc) {}
/*      */   }
/*      */ }


/* Location:              C:\Users\mustapha\Desktop\Crossword-Express.jar!\crosswordexpress\DictionaryMtce.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */