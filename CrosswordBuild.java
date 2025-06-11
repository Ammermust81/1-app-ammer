/*      */ package crosswordexpress;
/*      */ import java.awt.Color;
/*      */ import java.awt.FontMetrics;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.io.DataInputStream;
/*      */ import java.io.DataOutputStream;
/*      */ import java.io.File;
/*      */ import java.util.ArrayList;
/*      */ import javax.swing.DefaultListModel;
/*      */ import javax.swing.JButton;
/*      */ import javax.swing.JCheckBox;
/*      */ import javax.swing.JComboBox;
/*      */ import javax.swing.JDialog;
/*      */ import javax.swing.JFileChooser;
/*      */ import javax.swing.JFrame;
/*      */ import javax.swing.JLabel;
/*      */ import javax.swing.JMenuItem;
/*      */ import javax.swing.JPanel;
/*      */ import javax.swing.JScrollPane;
/*      */ import javax.swing.JTextField;
/*      */ import javax.swing.KeyStroke;
/*      */ 
/*      */ public final class CrosswordBuild implements ActionListener {
/*      */   static JFrame jfCrossword;
/*      */   static JMenuBar menuBar;
/*      */   JMenu menu;
/*      */   JMenu submenu;
/*      */   JMenuItem menuItem;
/*      */   JMenuItem buildMenuItem;
/*      */   static JPanel pp;
/*   33 */   static String clueText = " "; static int panelW; static int panelH; static JLabel jl1; static JLabel jl2; JButton jbButton; JButton jbReview; JButton jbColorize; JDialog jdlgWizard; static boolean reviewMode = false;
/*      */   static boolean buildThemed = false;
/*      */   static boolean wizardRunning = false;
/*      */   static JPanel jpReviewClue;
/*   37 */   int thisColor = 56797;
/*   38 */   int howMany = 1; int startPuz = Integer.parseInt((new SimpleDateFormat("yyyyMMdd")).format(new Date())); int hmCount;
/*      */   Thread thread;
/*   40 */   JButton[] jbArray = new JButton[21]; JButton[] jbCell = new JButton[15];
/*      */   
/*      */   boolean colorizing = false;
/*      */   
/*   44 */   static int[][] wordCount = new int[5][50];
/*   45 */   static char[][][][][] chWord = new char[2][5][50][][];
/*   46 */   static int[][][] revLink = new int[5][50][];
/*   47 */   static boolean[][][] busy = new boolean[5][50][]; static boolean[] themeDic = new boolean[5];
/*      */   File[] grids;
/*      */   File[] files;
/*   50 */   static int[] dicList = new int[] { Op.CW.CwDic
/*   51 */       .ordinal(), Op.CW.CwSiDic1
/*   52 */       .ordinal(), Op.CW.CwSiDic2
/*   53 */       .ordinal(), Op.CW.CwSiDic3
/*   54 */       .ordinal(), Op.CW.CwSiDic4
/*   55 */       .ordinal() };
/*      */ 
/*      */   
/*   58 */   static String addCluesHelp = "<ul><li/>If you have been using a dictionary having clues associated with the words to build your puzzle, then most of the words will already be provided with clues.<p/><li/>Selecting a word in the puzzle (by using the arrow keys or by clicking with the mouse) and clicking the <b>Add Clue</b> button will display the clue, if one exists.<p/><li/>If no clue exists for the word, then you can use the <b>Clue</b> edit box to type in a suitable clue.<p/><li/>Three buttons are provided to assist people who like to compose cryptic clues or similar for use in their puzzles:-<ul><li/><span>Container Words</span> A list of all of the single words which contain all of the letters of the subject word will be displayed.<p/><li/><span>Contained Words</span> A list of all of the single words which can be made from the letters contained within the subject word will be displayed.<p/><li/><span>Anagrams</span> This button will display a list of all possible <b>Anagrams</b> of the word. These may be single words or they may be lists of two, three or even more words, depending on the length of the subject Word.<br/><br/></ul><li/>The behaviour of the <b>Anagram</b> and <b>Contained Words</b> functions can be modified by selecting a number from the <b>Shortest Word</b> combo box. The length of the words which appear in the list for these two functions will be limited to the value that you specify here.<p/><li/>When you click the <b>OK</b> button, the contents of the <b>Clue</b> edit box will be attached to the puzzle, and the puzzle will be saved to disk, but if you click the <b>Cancel</b> button, any changes you may have made to the clue will be abandoned.</ul></body>";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   82 */   static String suggestHelp = "<ul><li/>As you build your puzzle, you will inevitably encounter the situation where you have some of the letters for a word, and you would like to know what words would fit in with those letters.<p/><li/>Clicking the <b>Suggest</b> button will present you with a list box which contains all of the words in the current dictionary which do in fact fit with those letters. Simply use the mouse to point to the word which you would like to use and click on it to select it.<p/><li/>Clicking OK will exit from the list box, and place your selected word into the puzzle at the location of the current word.<p/><li/>If the dictionary contains a clue for the word which you selected, then this will also be attached to the puzzle.<p/><li/>Sometimes the dictionary will not contain any suitable words, and you will be shown an empty list box. Click Cancel to get back to your puzzle, and make some changes to the puzzle to avoid the <b>impossible word</b> situation.<p/><li/>Alternatively you could use Dictionary Maintenance to add some more words to the dictionary, or you might know of a suitable word which is not actually in the dictionary. You can type such a word directly into the puzzle.</ul></body>";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   96 */   String crosswordOptionsHelp = "<div>A number of options described in the following are available to you before you begin to construct a crossword puzzle:-<br/><br/></div><ul><li/>If you want to make a number of puzzles all having the same dimensions, simply type a number into the <b>How many puzzles</b> input field. When you issue the Make command, Crossword Express will make that number of puzzles. The puzzle names will be numbers which represent a date in <b>yyyymmdd</b> format. The default value presented by Crossword Express is always the current date, but you can change this to any date that suits your needs. As the series of puzzles is created, CWE will automatically step on to the next date in the sequence, taking into account such factors as the varying number of days in the months, and of course leap years. Virtually any number of puzzles can be made in a single operation using this feature.<p/><li/><b>HOWEVER:</b> If you prefer a simpler numbering scheme for your puzzles, you can enter any number of 7 digits or less to be used for your first puzzle, and Crossword Express will number the remainder of the puzzles sequentially starting with your number.<p/><li/>When you use the Multi-Make function described above, you have the option of locking out the words used in a puzzle so that they won't be used in subsequent puzzles. If you set the <b>Use Automatic Lockout &amp; Unlock</b> check box, then each time a puzzle is built, the words used in it will be locked out of the dictionary. If you have made more than 3 puzzles, then the words used in the puzzle numbered 3 less than the current one will be unlocked, and placed back into operation. Note that this mechanism applies only if you are using the Multi-Make feature.<p/><li/>There are two steps which must be taken if a <b>Theme</b> puzzle is to be built:-<ul><li/>Select the <b>Build a Theme Puzzle</b> check box<p/><li/>Select the theme dictionaries to be used for this puzzle. A maximum of four such dictionaries can be selected. If you want to use less than this maximum number simply select the <b>&lt;None&gt;</b> option for one or more of the theme dictionaries. When the puzzle is built, the theme dictionaries will be searched in order for a suitable word. If a suitable word cannot be found in any of the theme dictionaries, one will be taken from the standard dictionary selected within the Crossword Construction screen.<p/></ul><li/>When you build a puzzle, the normal mode of operation is for the program to use only those words which have not been withdrawn from service by the word Lockout function. You can change this behaviour by checking the <b>Use all words (including locked words)</b> check-box. This will result in all of the words within the dictionary being used to build the puzzle, and if a word has more than one clue then one of these clues will be randomly selected for use in the puzzle.</ul></body>";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  129 */   String crosswordHelp = "<span class='m'>Menu Functions</span><br/><br/><ul><li><span class='s'>File Menu</span><ul><li><span>Select a Dictionary</span><br/>When loading a new puzzle into the Build screen, you begin by selecting the dictionary which was used to build the CROSSWORD puzzle which you want to load.<p/><li><span>Load a Puzzle</span><br/>Then you choose your puzzle from the pool of CROSSWORD puzzles currently available in the selected dictionary.<p/><li><span>Save</span><br/>If you have done some manual editing of the puzzle, this option will save those changes under the existing file name. Note that this option will only be effective if the puzzle has already been saved on a previous occasion using the SaveAs option. For subsequent Saves as you continue your manual construction it will function as expected.<p/><li><span>SaveAs</span><br/>This option allows you to make an exact copy of the current puzzle using a different file name. The copy will be saved in the folder of the dictionary that was used to construct it. Alternatively, if you don't enter a new name for the puzzle, you can change the Puzzle Description, or any of the other descriptive items without changing the puzzle name.<p/><li><span>Quit Construction</span><br/>Returns you to the Crossword Express opening screen.</ul><li><span class='s'>Build Menu</span><ul><li><span>Construction Wizard</span><br/>The Wizard is intended for people who are new to Crossword Express. It guides you, step by step, through the individual processes involved in the construction of a new puzzle. Experienced users will soon realize that some of the steps are not always required (selecting a dictionary for example) and will prefer to use only those of the following options which are required for their particular circumstances.<p/><li/><span >Start a New Puzzle</span><br/>This option presents you with a dialog into which you can enter a file name for your new puzzle. You can also enter several other pieces of optional information such as a <b>Puzzle Title, Author</b> and <b>Copyright</b> information.<p/><li/><span>Select a Dictionary</span><br/>Use this option to select the dictionary which you want to use to build the new CROSSWORD puzzle.<p/><li/><span>Select a Grid</span><br/>Crossword puzzles are built on a predefined grid, which you can select using this option. You can select a single grid for normal operation, or you can select a number of grids which will be useful if you are going to use the Multi-Make function described in the Help available from the Crossword Options dialog. As the multiple puzzles are constructed, the program will use a different grid for each puzzle by cycling through the grids you have selected. You can build an unlimited number of additional grids using functions available via the <b>GRID MAINTENANCE</b> button on the Crossword Express opening screen.<p/><li/><span>Build Options</span><br/>Use this option to access a Build Options dialog where you can set some rules to be followed by the puzzle building function as it builds your puzzle.<p/><li/><span>Start Building / Stop Building</span><br/> Construction of the puzzle will commence when you select the <b>Start Building</b> option. If puzzle building is successful you will receive a message containing the name of the puzzle file, and the location where it was saved. If it becomes necessary, you can interrupt the building process by selecting this option a second time. Note that during the construction phase, the text of this option is changed to <b>Stop Building</b></ul><li/><span class='s'>View Menu</span><ul><li/><span>Display Options</span><br/>This leads you to a dialog box in which you can change the colors of various elements within the puzzle, and control the fonts which will be used for the puzzle's text components. You can also decide if printing of the puzzle will be done in black and white or in color.</ul><li/><span class='s'>Print Menu</span><br/><ul><li/><span>Print Crossword</span><br/>The current puzzle can be printed as a standard CROSSWORD.<p/><li/><span>Print French-Style Crossword</span><br/>The current puzzle can be printed in the French-Style preferred in some European countries. Note that in this case, the clues are printed in a single column which should probably extend to the same width as the puzzle itself. Among the Print Layouts available from the Print screen there is one called <b>frenchstyle</b> which you can use as is, or modify to suit your requirements.<p/><li/><span>Print Codeword</span><br/>The current puzzle can be printed as a CODEWORD puzzle.<p/><li/><span>Print Fillin</span><br/>The current puzzle can be printed as a FILLIN puzzle.<p/><li/><span>Print Arrowword</span><br/>The current puzzle can be printed in the ARROWWORD format.<p/><li/><span>Print a Publication Submission</span><br/>Some Crossword Express users may wish to submit their puzzles to publishers for inclusion in newspapers and magazines. In many cases this will require that the puzzle be presented in a very particular format which unfortunately differs significantly between publishers. This option will print a Submission Document according to the specification provided by the <b>New York Times,</b> and if it proves to be popular, consideration will be given to creation of a series of options which cater for other major publications. If you have such a requirement by all means contact me via email with a detailed specification of the Submission Document required.</ul><li/><span class='s'>Solve Menu</span><br/><ul><li/><span>Solve as Crossword</span><br/>The current puzzle can be solved as a standard CROSSWORD.<p/><li/><span>Solve as Crossword (Audience Mode)</span><br/>Similar to the normal Solve function, except that only a single clue is displayed at a time, and the puzzle is displayed in a large format. The intention is that the puzzle be displayed using a video projector or big screen digital TV connected by an HDMI cable to the computer running the Crossword Express program. This configuration has been found useful for the entertainment of residents of retirement homes.<p/><li/><span>Solve as Codeword</span><br/>The current puzzle can be solved as a CODEWORD puzzle.<p/><li/><span>Solve as Fillin</span><br/>The current puzzle can be solved as a FILLIN puzzle.</ul><li/><span class='s'>Export Menu</span><br/><ul><li/><span>Export Crossword Web-App</span><br/>This function allows you to export a Web Application Program which you can then upload to your own web site to provide a fully interactive crossword puzzle for the entertainment of visitors to your site. For a full description of the facilities provided by this Web-App, please refer to the Help available at <b>Help / Web Application</b> on the menu bar of the <b>Build Crossword</b> window of this program.<p/><li/><span>Launch a Demo Web App</span><br/>Take a first look at the Crossword Web App. See what it could do to enhance your web site.<p/><li/><span>Export Puzzle as Text</span><br/>Under normal circumstances, the Print function will provide all of the layout flexibility you will need when printing your puzzles. Inevitably of course special cases will arise where you need to intervene in the printing of either the words or the clues to achieve some special effect. To meet this need, a text export feature offers the following choices:-<p/><ul><li/><b>Export Words.</b> Each line of text has the format <b>Id. WORD</b><li/><b>Export Clues.</b> Each line of text has the format <b>Id. Clue</b><li/><b>Export Words and Clues.</b> Each line of text has the format <b>Id. WORD : Clue</b><li/><b>Export Puzzle Grid.</b> The puzzle grid is exported as a simple square or rectangular array of letters.</ul>In addition, you have the choice of exporting the text to a text file located anywhere on your computer's hard drive, or to the System Clipboard from where you can Paste into any Word Processor or Desk Top Publishing application.</ul><li/><span class='s'>Tasks Menu</span><ul><li/><span>Delete this Puzzle</span><br/>Use this option to eliminate unwanted CROSSWORD puzzles from your file system.<p/><li/><span'>Lockout Words</span><br/>Use this option to condition the current dictionary so that the words used in this puzzle will not be used in subsequent puzzles.<p/><li/><span>Transfer Puzzle Clues to Dictionary</span><br/>If you have used manual processing to partially or completely build the puzzle, it may be that the puzzle will contain some words and clues which do not yet appear in the dictionary. This option will find all such words, and merge them into the current dictionary.<p/><li/><span>Rebuild the Current Puzzle</span><br/>Use of this option will not affect the words within the puzzle grid, but will refresh the content of the clues. The clues will then reflect any changes which have been made to the dictionary clues since the time the puzzle was first constructed.</ul><li/><span class='s'>Help Menu</span><ul><li/><span>Crossword Help</span><br/>Displays the Help screen which you are now reading.</ul></ul><span class='m'>Manual Construction</span><div>Manual puzzle construction allows you to click into a puzzle cell and type a character directly into that cell. You can build an entire puzzle in this way if you like, but the <b>Suggest a Word</b> and <b>Add a Clue</b> buttons described below will certainly make your job much easier. You can also insert just a few words (or even just a few letters) and then use the automatic build features to complete the puzzle. Be aware though that if you enter too many words and/or letters in this way, you may well create a situation in which it is quite impossible to build the puzzle. Some experimenting with the <b>Suggest a Word</b> button will demonstrate just how readily this can happen.<br/><br/></div><ul><li><span>Suggest a Word:</span><br/>This button is used during manual puzzle construction. Its operation is described fully in the Help available from the <b>Suggest a Word</b> dialog.<p/><li/><span>Add a Clue:</span><br/>This button also is used during manual puzzle construction, and its operation is described fully in the Help available from the <b>Add a Clue</b> dialog.<p/><li/><span>Review Clues:</span><br/>When you click this button, the text area immediately below the button will contain the clue for the word which is currently selected in the displayed puzzle. Each time you make a change to the selected word, the clue displayed will change accordingly. There are three ways of changing the selected word:-<ul><li/>Use the arrow keys to move the red cursor cell to a new location. One of the words passing through the new cursor cell will become the selected word.<li/>Use the mouse to point and click on a new cell within the puzzle.<li/>If you wish to review all of the clues within a puzzle, the recommended method is to use the buttons immediately to the left and right of the <b>Review Clues</b> button. This will allow you to step through all of the clues within the puzzle in sequence.</ul>When you first clicked the <b>Review Clues</b> button, its label changed to <b>End Review</>. This reminds you that you can terminate the review process by clicking the same button you used to start it.<p/><li/><span>Colorize Puzzle:</span><br/>At times it can be quite useful to introduce color into a crossword puzzle. For example, you can arrange your puzzle so that a message appears in a series of colored cells within the puzzle as it is solved. Clicking the <b>Colorize Puzzle</b> button puts the program into a colorize mode in which a single click of the mouse inside a puzzle cell will give that cell the color currently selected using the <b>Choose Hilite Color</b> button. A second click will restore any colored cell back to white. The colorize mode is terminated by means of a second click of the button which will now be labelled <b>End Colorizing</b>.<p/><li/><span>Choose Hilite Color:</span><br/> Use this button to access a color selection dialog. Whatever color you choose will be used in the colorize mode mentioned above. You can change the hilite color as many times as you like while colorizing a puzzle.<p/><li/><span>Cell Type Selection:</span><br/>Crossword Express supports a total of 15 different <b>Cell Types</b> as displayed on the set of 15 cell type buttons. Clicking any of these buttons will set the currently selected puzzle cell to be that type. A discussion on <b>Cell Types</b> is available by selecting Help while editing Grids or creating new ones.</ul></body>";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  309 */   static String megaHelp = "<div>Australian crossword fans of a certain age would remember a puzzle called <b>Mr Wisdom's Whopper</b> which was for many years the largest regularly published crossword in Australia. It was 32 cells across and 42 cells down, and during much of the 1990s an early version of <b>Crossword Express</b> was used to create the puzzles. The person responsible for these puzzles was so delighted at the amount of time and effort which was saved by using <b>Crossword Express</b>, he finally presented me with a copy of the dictionary which he was using to make the puzzles, and said that I could use it in any way I chose. He is no longer making puzzles, so there is now nothing to stop me including that dictionary as part of <b>Crossword Express</b>. I have simply merged it with the <b>english</b> dictionary which I distribute with the program.<p/>I have also included a total of five new grids called <b>mega1</b> to <b>mega5</b> which are suitable for making the large puzzles. For Copyright reasons they are naturally not the same as the original <b>Mr Wisdom's Whopper</b> grids. <p/> Have fun making your own Whopper puzzles!</div></body>";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  324 */   static String webAppHelp = "<div><span>Step 1: Export</span><br/>To export the Web Application, select the function <b>Export / Export Puzzle as Web App</b> from the menu bar of the <b>Build Crossword</b> window of this program. The necessary files will be exported into a folder called <b>Web-App</b> located on your computer's Desktop. Please note that you will need a working Internet connection for this function to operate correctly. The exported files are as follows:-<ul><li><span>crossword.js</span> This is the file which contains all of the Java Script which implements the interactive features of the Web App.<li><span>crossword.html</span> This file creates the URL address used to access the Web App. Its main purpose is to start the operation of the crossword.js Java Script file. If you open crossword.html with a simple text editor, you will find two appearances of the following text fragment ... <b>&lt;!-- Your HTML code here. --></b>. If you are an experienced HTML coder and would like to add some content of your own to the Web App window, simply replace these fragments with your own HTML code.<li><span>puzzle.crossword</span> This is a standard Crossword Express puzzle file. Depending on how you choose to make use of the Web App, this file may be loaded by the App and presented as an interactive puzzle to be solved by visitors to your site. Its function will be discussed in more detail in the remainder of this Help screen.</ul><span>Step 2: Familiarization</span><br/>Having exported the App, you can give it a first run by starting your web browser, and using it to open the <b>crossword.html</b> file mentioned above. Note particularly that you must not use the <b>Chrome </b> browser for this test. The makers of the Chrome browser have included a security feature which prevents a Web App from loading content from the local hard drive. Any other browser will function correctly, and you can rest assured that when you have installed the App on your web server, the Chrome browser will also function correctly.<p/><span>Step 3: Installation</span><br/>Installation is a three step process:-<ul><li>Create a new folder somewhere on your web server.<li>Upload the three files already discussed into this new folder.<li>Create a link from a convenient point within your web site to the crossword.html file in your newly created folder.</ul>Now you will be able to start the Web App via the new link. Any modern browser will operate the App, including Chrome.<p/><span>Step 4: Publishing your Puzzle(s)</span><br/><ul><li><span>Single puzzle.</span> If all you need is a single unchanging puzzle, create that puzzle using either the <b>Standard Crossword</b> function or the <b>Freeform Crossword</b> function of Crossword Express, and give it the name <b>puzzle.crossword</b> Upload the resulting puzzle file to your web server to replace the existing file of the same name which came with the export package.<li><span>Daily puzzle.</span> If you are really serious about publishing crossword puzzles for your visitors to solve you will probably want to publish a new puzzle every day, and it would be natural to want this all to happen without the need for daily intervention by yourself. The good news is that Crossword Express takes care of the daily puzzle changeover without any action whatever on your part. The puzzles that you need for this purpose can be made using the <b>Multi-Puzzle control</b> feature when constructing your puzzles. This feature allows you to specify how many puzzles you require the program to make. The names of these puzzles will be eight digit numbers representing a date in the form <b>yyyymmdd</b>. The name of the first puzzle it makes will default to the current date, but you can change this to any date which is appropriate to your needs. Subsequent puzzles will have the date incremented by one until the requested number of puzzles have been completed.<p/><br/>When you have created your puzzle files, they must be uploaded to your web server and into the same folder which contains the crossword.js and crossword.html files. Then, when the Web App begins operating, the first thing it does is to interrogate the system clock of the computer on which it is running. It uses this information to calculate the current date in yyyymmdd format, and hence create the name of the puzzle file appropriate to the day. It loads this file from the web server, and presents it to your web visitor as an interactive puzzle, ready to be solved. If, for any reason, the puzzle file whose name was calculated is not available, the App simply loads the puzzle.crossword file mentioned previously and presents this as the puzzle to be solved. This being the case, you should always be sure that you do have a valid puzzle.crossword file present in your Web App folder.</ul><span>Step 5: Warning:</span><br/><ul><li>When you try to operate the Web-App from your web site, it sometimes happens that the required puzzle doesn't appear as expected. This problem happens with some web servers when the Web-App attempts to download the .crossword puzzle file that it needs to complete its initialization. Under normal circumstances, there is only a limited number of file types which can be guaranteed to be down-loadable from all web servers, and unfortunately .crossword is not one of them. On the other hand some web servers are more forgiving in this regard, and work quite happily. As things stand at the moment, if you are caught by this problem, you will need to contact your server administrator and request that a configuration change be made. The required change will be quite clear to the administrator if you pass on the following quote from a server administrator who was the first to encounter and fix this problem:-<p/><br>&#34;I have gone ahead and added a mimetype for .crossword to be an application&#47octet-stream. This simply allows it to download.&#34;</ul></div></body>";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  398 */   static String printSubmission = "<div>This dialog provides an interface to the process which prints the <b<Publication Submission</b> document required by the publishers of certain newspapers and magazines when a puzzle is submitted to them.<br/><br/><ul><li><span>Name and Address Details</span> Type your details into these input fields. The program will remember these details until the next time you call upon it to print a submission.<p/><li><span>Preferred Font Size</span> This control allows you to exercise control over the size of the characters used to print the documents.<p/><li><span>Pint</span> Clicking this button will print the NYT style Publication Submission document.</ul>Initially, the program will only produce submission documents relating to the New York Times. Other documents will be added to its capabilities in response to your Email requests.</div></body>";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*  415 */     Op.updateOption(Op.CW.AwStyle.ordinal(), "2", Op.cw);
/*  416 */     Op.updateOption(Op.CW.AwDir.ordinal(), "true", Op.cw);
/*  417 */     Op.updateOption(Op.CW.AwBold.ordinal(), "false", Op.cw);
/*  418 */     Op.updateOption(Op.CW.AwAlign.ordinal(), "3", Op.cw);
/*  419 */     Op.updateOption(Op.CW.AwWidth.ordinal(), "4", Op.cw);
/*  420 */     Op.updateOption(Op.CW.AwClue.ordinal(), "EEEECC", Op.cw);
/*  421 */     Op.updateOption(Op.CW.AwCell.ordinal(), "FFFFFF", Op.cw);
/*  422 */     Op.updateOption(Op.CW.AwArrow.ordinal(), "000000", Op.cw);
/*  423 */     Op.updateOption(Op.CW.AwPattern.ordinal(), "000000", Op.cw);
/*  424 */     Op.updateOption(Op.CW.AwLine.ordinal(), "000000", Op.cw);
/*  425 */     Op.updateOption(Op.CW.AwText.ordinal(), "000000", Op.cw);
/*  426 */     Op.updateOption(Op.CW.AwOverlap.ordinal(), "0", Op.cw);
/*  427 */     Op.updateOption(Op.CW.CwPuz.ordinal(), "sample.crossword", Op.cw);
/*  428 */     Op.updateOption(Op.CW.CwGrid.ordinal(), "am13-1.grid", Op.cw);
/*  429 */     Op.updateOption(Op.CW.CwDic.ordinal(), "english", Op.cw);
/*  430 */     Op.updateOption(Op.CW.CwSiDic1.ordinal(), "", Op.cw);
/*  431 */     Op.updateOption(Op.CW.CwSiDic2.ordinal(), "", Op.cw);
/*  432 */     Op.updateOption(Op.CW.CwSiDic3.ordinal(), "", Op.cw);
/*  433 */     Op.updateOption(Op.CW.CwSiDic4.ordinal(), "", Op.cw);
/*  434 */     Op.updateOption(Op.CW.CwW.ordinal(), "960", Op.cw);
/*  435 */     Op.updateOption(Op.CW.CwH.ordinal(), "700", Op.cw);
/*  436 */     Op.updateOption(Op.CW.CwSolveW.ordinal(), "600", Op.cw);
/*  437 */     Op.updateOption(Op.CW.CwSolveH.ordinal(), "600", Op.cw);
/*  438 */     Op.updateOption(Op.CW.CwPuzC.ordinal(), "false", Op.cw);
/*  439 */     Op.updateOption(Op.CW.CwSolC.ordinal(), "false", Op.cw);
/*  440 */     Op.updateOption(Op.CW.CwFrenchAcrossId.ordinal(), "false", Op.cw);
/*  441 */     Op.updateOption(Op.CW.CwFrenchDownId.ordinal(), "true", Op.cw);
/*  442 */     Op.updateOption(Op.CW.CwCellC.ordinal(), "FFFFFF", Op.cw);
/*  443 */     Op.updateOption(Op.CW.CwGridC.ordinal(), "000000", Op.cw);
/*  444 */     Op.updateOption(Op.CW.CwLettersC.ordinal(), "000000", Op.cw);
/*  445 */     Op.updateOption(Op.CW.CwIDC.ordinal(), "000000", Op.cw);
/*  446 */     Op.updateOption(Op.CW.CwShadowC.ordinal(), "000000", Op.cw);
/*  447 */     Op.updateOption(Op.CW.CwPatternC.ordinal(), "000000", Op.cw);
/*  448 */     Op.updateOption(Op.CW.CwErrorC.ordinal(), "FF0000", Op.cw);
/*  449 */     Op.updateOption(Op.CW.CwClueC.ordinal(), "000000", Op.cw);
/*  450 */     Op.updateOption(Op.CW.CwGeminiA.ordinal(), "EEEE88", Op.cw);
/*  451 */     Op.updateOption(Op.CW.CwGeminiB.ordinal(), "FFAAAA", Op.cw);
/*  452 */     Op.updateOption(Op.CW.CwFont.ordinal(), "Serif", Op.cw);
/*  453 */     Op.updateOption(Op.CW.CwIDFont.ordinal(), "SansSerif", Op.cw);
/*  454 */     Op.updateOption(Op.CW.CwClueFont.ordinal(), "SansSerif", Op.cw);
/*  455 */     Op.updateOption(Op.CW.CwIgnoreLockout.ordinal(), "false", Op.cw);
/*  456 */     Op.updateOption(Op.CW.CwAutoLock.ordinal(), "false", Op.cw);
/*  457 */     Op.updateOption(Op.CW.CwSolvePuz.ordinal(), "sample.crossword", Op.cw);
/*  458 */     Op.updateOption(Op.CW.CwSolveDic.ordinal(), "english", Op.cw);
/*  459 */     Op.updateOption(Op.CW.CwCrosswordSep.ordinal(), "false", Op.cw);
/*  460 */     Op.updateOption(Op.CW.CwReverseArrow.ordinal(), "FFFFFF", Op.cw);
/*  461 */     Op.updateOption(Op.CW.CwIdInSol.ordinal(), "false", Op.cw);
/*  462 */     Op.updateOption(Op.CW.CwName.ordinal(), "", Op.cw);
/*  463 */     Op.updateOption(Op.CW.CwStreet.ordinal(), "", Op.cw);
/*  464 */     Op.updateOption(Op.CW.CwTown.ordinal(), "", Op.cw);
/*  465 */     Op.updateOption(Op.CW.CwState.ordinal(), "", Op.cw);
/*  466 */     Op.updateOption(Op.CW.CwZip.ordinal(), "", Op.cw);
/*  467 */     Op.updateOption(Op.CW.CwCountry.ordinal(), "", Op.cw);
/*  468 */     Op.updateOption(Op.CW.CwEmail.ordinal(), "", Op.cw);
/*  469 */     Op.updateOption(Op.CW.CwSubFont.ordinal(), "15", Op.cw);
/*      */   }
/*      */   
/*      */   CrosswordBuild(JFrame jfCWE) {
/*  473 */     Def.puzzleMode = 4;
/*  474 */     Def.dispSolArray = Boolean.valueOf(false);
/*  475 */     Def.dispCursor = Boolean.valueOf(true);
/*  476 */     Def.dispNullCells = Boolean.valueOf(true);
/*  477 */     Def.dispGuideDigits = Boolean.valueOf(true);
/*  478 */     Def.building = 0;
/*  479 */     Def.dispWithColor = Boolean.valueOf(true);
/*  480 */     makeGrid();
/*      */     
/*  482 */     jfCrossword = new JFrame("Crossword Construction");
/*  483 */     if (Op.getInt(Op.CW.CwH.ordinal(), Op.cw) > Methods.scrH - 200) {
/*  484 */       int diff = Op.getInt(Op.CW.CwH.ordinal(), Op.cw) - Op.getInt(Op.CW.CwW.ordinal(), Op.cw);
/*  485 */       Op.setInt(Op.CW.CwH.ordinal(), Methods.scrH - 200, Op.cw);
/*  486 */       Op.setInt(Op.CW.CwW.ordinal(), Methods.scrH - 200 + diff, Op.cw);
/*      */     } 
/*  488 */     jfCrossword.setSize(Op.getInt(Op.CW.CwW.ordinal(), Op.cw), Op.getInt(Op.CW.CwH.ordinal(), Op.cw));
/*  489 */     int frameX = (jfCWE.getX() + jfCrossword.getWidth() > Methods.scrW) ? (Methods.scrW - jfCrossword.getWidth() - 10) : jfCWE.getX();
/*  490 */     jfCrossword.setLocation(frameX, jfCWE.getY());
/*  491 */     jfCrossword.setLayout((LayoutManager)null);
/*  492 */     jfCrossword.getContentPane().setBackground(Def.COLOR_FRAMEBG);
/*  493 */     jfCrossword.setDefaultCloseOperation(0);
/*  494 */     jfCrossword
/*  495 */       .addComponentListener(new ComponentAdapter() {
/*      */           public void componentResized(ComponentEvent ce) {
/*  497 */             int oldw = Op.getInt(Op.CW.CwW.ordinal(), Op.cw);
/*  498 */             int oldh = Op.getInt(Op.CW.CwH.ordinal(), Op.cw);
/*  499 */             Methods.frameResize(CrosswordBuild.jfCrossword, oldw, oldh, 760, 500);
/*  500 */             Op.setInt(Op.CW.CwW.ordinal(), CrosswordBuild.jfCrossword.getWidth(), Op.cw);
/*  501 */             Op.setInt(Op.CW.CwH.ordinal(), CrosswordBuild.jfCrossword.getHeight(), Op.cw);
/*  502 */             CrosswordBuild.restoreFrame();
/*      */           }
/*      */           public void componentMoved(ComponentEvent ce) {
/*  505 */             if (CrosswordBuild.wizardRunning) {
/*  506 */               CrosswordBuild.this.jdlgWizard.setLocation(CrosswordBuild.jfCrossword.getX() + CrosswordBuild.jfCrossword.getWidth() + 10, CrosswordBuild.jfCrossword.getY());
/*      */             }
/*      */           }
/*      */         });
/*      */     
/*  511 */     jfCrossword
/*  512 */       .addWindowListener(new WindowAdapter() {
/*      */           public void windowClosing(WindowEvent we) {
/*  514 */             if (Def.building == 1 || Def.selecting)
/*      */               return; 
/*  516 */             Op.saveOptions("crossword.opt", Op.cw);
/*  517 */             CrosswordExpress.transfer(1, CrosswordBuild.jfCrossword);
/*      */           }
/*      */         });
/*      */     
/*  521 */     Methods.closeHelp();
/*      */ 
/*      */     
/*  524 */     Runnable buildThread = () -> {
/*      */         Def.dispCursor = Boolean.valueOf(false);
/*      */         
/*      */         Methods.havePuzzle = false;
/*      */         
/*      */         focusColor(false);
/*      */         
/*      */         if (buildThemed) {
/*      */           Methods.havePuzzle = themeBuild();
/*      */         } else if (this.howMany == 1) {
/*      */           if (buildCrossword(true)) {
/*      */             Methods.havePuzzle = true;
/*      */             saveCrossword(Op.cw[Op.CW.CwPuz.ordinal()]);
/*      */           } 
/*      */         } else {
/*      */           multiBuild();
/*      */           Methods.havePuzzle = true;
/*      */         } 
/*      */         this.buildMenuItem.setText("Start Building");
/*      */         if (Def.building == 2) {
/*      */           Def.building = 0;
/*      */           Methods.interrupted(jfCrossword);
/*      */           makeGrid();
/*      */           restoreFrame();
/*      */           return;
/*      */         } 
/*      */         Def.building = 0;
/*      */         if (Methods.havePuzzle) {
/*      */           if (!buildThemed) {
/*      */             saveCrossword(Op.cw[Op.CW.CwPuz.ordinal()]);
/*      */           }
/*      */           Methods.puzzleSaved(jfCrossword, Op.cw[Op.CW.CwDic.ordinal()] + ".dic", Op.cw[Op.CW.CwPuz.ordinal()]);
/*      */         } else {
/*      */           makeGrid();
/*      */           Methods.cantBuild(jfCrossword);
/*      */         } 
/*      */       };
/*  561 */     jl1 = new JLabel();
/*  562 */     jfCrossword.add(jl1);
/*  563 */     jl2 = new JLabel();
/*  564 */     jfCrossword.add(jl2);
/*      */     
/*  566 */     menuBar = new JMenuBar();
/*  567 */     menuBar.setBackground(Def.COLOR_MENUBAR);
/*  568 */     jfCrossword.setJMenuBar(menuBar);
/*  569 */     this.menu = new JMenu("File");
/*      */     
/*  571 */     menuBar.add(this.menu);
/*  572 */     this.menuItem = new JMenuItem("Select a Dictionary");
/*  573 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(68, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  574 */     this.menu.add(this.menuItem);
/*  575 */     this.menuItem
/*  576 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           
/*      */           Methods.selectDictionary(jfCrossword, Op.cw[Op.CW.CwDic.ordinal()], 1);
/*      */           Op.cw[Op.CW.CwDic.ordinal()] = Methods.dictionaryName;
/*      */           Grid.clearGrid();
/*      */           loadCrossword(Op.cw[Op.CW.CwPuz.ordinal()]);
/*      */           focusColor(true);
/*      */           restoreFrame();
/*      */         });
/*  588 */     this.menuItem = new JMenuItem("Load a Puzzle");
/*  589 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(76, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  590 */     this.menu.add(this.menuItem);
/*  591 */     this.menuItem
/*  592 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           
/*      */           pp.invalidate();
/*      */           restoreFrame();
/*      */           focusColor(false);
/*      */           new Select(jfCrossword, Op.cw[Op.CW.CwDic.ordinal()] + ".dic", "crossword", Op.cw, Op.CW.CwPuz.ordinal(), false);
/*      */         });
/*  602 */     this.menuItem = new JMenuItem("Save");
/*  603 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(83, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  604 */     this.menu.add(this.menuItem);
/*  605 */     this.menuItem
/*  606 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           
/*      */           saveCrossword(Op.cw[Op.CW.CwPuz.ordinal()]);
/*      */           Methods.puzzleSaved(jfCrossword, Op.cw[Op.CW.CwDic.ordinal()] + ".dic", Op.cw[Op.CW.CwPuz.ordinal()]);
/*      */           restoreFrame();
/*      */         });
/*  615 */     this.menuItem = new JMenuItem("SaveAs");
/*  616 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(65, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  617 */     this.menu.add(this.menuItem);
/*  618 */     this.menuItem
/*  619 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           
/*      */           Methods.puzzleDescriptionDialog(jfCrossword, Op.cw[Op.CW.CwPuz.ordinal()].substring(0, Op.cw[Op.CW.CwPuz.ordinal()].indexOf(".crossword")), Op.cw[Op.CW.CwDic.ordinal()] + ".dic", ".crossword");
/*      */           if (Methods.clickedOK) {
/*      */             saveCrossword(Op.cw[Op.CW.CwPuz.ordinal()] = Methods.theFileName);
/*      */             restoreFrame();
/*      */             Methods.puzzleSaved(jfCrossword, Op.cw[Op.CW.CwDic.ordinal()] + ".dic", Op.cw[Op.CW.CwPuz.ordinal()]);
/*      */           } 
/*      */         });
/*  631 */     this.menuItem = new JMenuItem("Quit Construction");
/*  632 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(81, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  633 */     this.menu.add(this.menuItem);
/*  634 */     this.menuItem
/*  635 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           
/*      */           Op.saveOptions("crossword.opt", Op.cw);
/*      */           
/*      */           CrosswordExpress.transfer(1, jfCrossword);
/*      */         });
/*  644 */     this.menu = new JMenu("Build");
/*  645 */     menuBar.add(this.menu);
/*  646 */     this.menuItem = new JMenuItem("Use the Construction Wizard");
/*  647 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(90, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  648 */     this.menu.add(this.menuItem);
/*  649 */     this.menuItem
/*  650 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           crosswordWizard();
/*      */           restoreFrame();
/*      */         });
/*  657 */     this.menu.addSeparator();
/*  658 */     this.menuItem = new JMenuItem("     -: OR :-");
/*  659 */     this.menu.add(this.menuItem);
/*  660 */     this.menu.addSeparator();
/*      */     
/*  662 */     this.menuItem = new JMenuItem("Step 1: Start a New Puzzle");
/*  663 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(78, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  664 */     this.menu.add(this.menuItem);
/*  665 */     this.menuItem
/*  666 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           Methods.puzzleDescriptionDialog(jfCrossword, Op.cw[Op.CW.CwPuz.ordinal()].substring(0, Op.cw[Op.CW.CwPuz.ordinal()].indexOf(".crossword")), Op.cw[Op.CW.CwDic.ordinal()] + ".dic", ".crossword");
/*      */ 
/*      */ 
/*      */           
/*      */           if (Methods.clickedOK) {
/*      */             Op.cw[Op.CW.CwPuz.ordinal()] = Methods.theFileName;
/*      */ 
/*      */ 
/*      */             
/*      */             makeGrid();
/*      */ 
/*      */ 
/*      */             
/*      */             Grid.loadGrid(Op.cw[Op.CW.CwGrid.ordinal()]);
/*      */ 
/*      */ 
/*      */             
/*      */             reviewClueControl(false);
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/*      */           restoreFrame();
/*      */         });
/*      */ 
/*      */ 
/*      */     
/*  700 */     this.menuItem = new JMenuItem("Step 2: Select a Dictionary");
/*  701 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(68, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  702 */     this.menu.add(this.menuItem);
/*  703 */     this.menuItem
/*  704 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           
/*      */           Methods.selectDictionary(jfCrossword, Op.cw[Op.CW.CwDic.ordinal()], 1);
/*      */           Op.cw[Op.CW.CwDic.ordinal()] = Methods.dictionaryName;
/*      */           restoreFrame();
/*      */         });
/*  713 */     this.menuItem = new JMenuItem("Step 3: Select a Grid");
/*  714 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(71, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  715 */     this.menu.add(this.menuItem);
/*  716 */     this.menuItem
/*  717 */       .addActionListener(ae -> {
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
/*      */           chooser.setSelectedFile(new File(Op.cw[Op.CW.CwGrid.ordinal()]));
/*      */           chooser.setAccessory(new Preview(chooser));
/*      */           chooser.setMultiSelectionEnabled(true);
/*      */           if (chooser.showDialog(jfCrossword, "Select Grid") == 0) {
/*      */             this.grids = chooser.getSelectedFiles();
/*      */           }
/*      */           Def.puzzleMode = 4;
/*      */           if (this.grids != null) {
/*      */             Op.cw[Op.CW.CwGrid.ordinal()] = this.grids[0].getName();
/*      */           }
/*      */           Grid.loadGrid(Op.cw[Op.CW.CwGrid.ordinal()]);
/*      */           Def.dispSolArray = Boolean.valueOf(false);
/*      */           Def.dispCursor = Boolean.valueOf(true);
/*      */           reviewClueControl(false);
/*      */           focusColor(true);
/*      */           restoreFrame();
/*      */         });
/*  746 */     this.menuItem = new JMenuItem("Step 4: Build Options");
/*  747 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(79, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  748 */     this.menu.add(this.menuItem);
/*  749 */     this.menuItem
/*  750 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           
/*      */           crosswordOptions();
/*      */           if (Methods.clickedOK) {
/*      */             reviewClueControl(false);
/*      */             makeGrid();
/*      */             focusColor(true);
/*      */           } 
/*      */           restoreFrame();
/*      */         });
/*  763 */     this.buildMenuItem = new JMenuItem("Step 5: Start Building");
/*  764 */     this.buildMenuItem.setAccelerator(KeyStroke.getKeyStroke(66, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  765 */     this.menu.add(this.buildMenuItem);
/*  766 */     this.buildMenuItem
/*  767 */       .addActionListener(ae -> {
/*      */           if (Op.cw[Op.CW.CwPuz.ordinal()].length() == 0 && this.howMany == 1) {
/*      */             Methods.noName(jfCrossword);
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
/*  784 */     this.menu = new JMenu("View");
/*  785 */     menuBar.add(this.menu);
/*  786 */     this.menuItem = new JMenuItem("Display Options");
/*  787 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(89, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  788 */     this.menu.add(this.menuItem);
/*  789 */     this.menuItem
/*  790 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           
/*      */           FreeformBuild.printOptions(jfCrossword, "Display Options");
/*      */           
/*      */           restoreFrame();
/*      */         });
/*  799 */     this.menu = new JMenu("Print");
/*  800 */     menuBar.add(this.menu);
/*  801 */     this.menuItem = new JMenuItem("Print Crossword");
/*  802 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(80, 1));
/*  803 */     this.menu.add(this.menuItem);
/*  804 */     this.menuItem
/*  805 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           Def.puzzleMode = 4;
/*      */           CrosswordExpress.toPrint(jfCrossword, Op.cw[Op.CW.CwPuz.ordinal()]);
/*      */         });
/*  812 */     this.menuItem = new JMenuItem("Print French-Style Crossword");
/*  813 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(70, 1));
/*  814 */     this.menu.add(this.menuItem);
/*  815 */     this.menuItem
/*  816 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           
/*      */           Def.puzzleMode = 12;
/*      */           CrosswordExpress.toPrint(jfCrossword, Op.cw[Op.CW.CwPuz.ordinal()]);
/*      */         });
/*  824 */     this.menuItem = new JMenuItem("Print Codeword");
/*  825 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(67, 1));
/*  826 */     this.menu.add(this.menuItem);
/*  827 */     this.menuItem
/*  828 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           
/*      */           Def.puzzleMode = 30;
/*      */           CrosswordExpress.toPrint(jfCrossword, Op.cw[Op.CW.CwPuz.ordinal()]);
/*      */         });
/*  836 */     this.menuItem = new JMenuItem("Print Fillin");
/*  837 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(73, 1));
/*  838 */     this.menu.add(this.menuItem);
/*  839 */     this.menuItem
/*  840 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           
/*      */           Def.puzzleMode = 60;
/*      */           CrosswordExpress.toPrint(jfCrossword, Op.cw[Op.CW.CwPuz.ordinal()]);
/*      */         });
/*  848 */     this.menuItem = new JMenuItem("Print Arrowword");
/*  849 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(65, 1));
/*  850 */     this.menu.add(this.menuItem);
/*  851 */     this.menuItem
/*  852 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           Def.puzzleMode = 7;
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           CrosswordExpress.toPrint(jfCrossword, Op.cw[Op.CW.CwPuz.ordinal()]);
/*      */         });
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  872 */     this.menuItem = new JMenuItem("Print a Publication Submission");
/*  873 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(83, 1));
/*  874 */     this.menu.add(this.menuItem);
/*  875 */     this.menuItem
/*  876 */       .addActionListener(ae -> {
/*      */           printSubmission(jfCrossword);
/*      */ 
/*      */           
/*      */           restoreFrame();
/*      */         });
/*      */     
/*  883 */     this.menu = new JMenu("Solve");
/*  884 */     menuBar.add(this.menu);
/*  885 */     this.menuItem = new JMenuItem("Solve as Crossword");
/*  886 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(86, 8));
/*  887 */     this.menu.add(this.menuItem);
/*  888 */     this.menuItem
/*  889 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           if (Methods.havePuzzle) {
/*      */             CrosswordExpress.transfer(5, jfCrossword);
/*      */           } else {
/*      */             Methods.noPuzzle(jfCrossword, "Solve");
/*      */           } 
/*      */         });
/*  899 */     this.menuItem = new JMenuItem("Solve as Crossword (Audience Mode)");
/*  900 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(67, 8));
/*  901 */     this.menu.add(this.menuItem);
/*  902 */     this.menuItem
/*  903 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           
/*      */           if (Methods.havePuzzle) {
/*      */             Def.audienceMode = true;
/*      */             CrosswordExpress.transfer(5, jfCrossword);
/*      */           } else {
/*      */             Methods.noPuzzle(jfCrossword, "Solve");
/*      */           } 
/*      */         });
/*  915 */     this.menuItem = new JMenuItem("Solve as Codeword");
/*  916 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(87, 8));
/*  917 */     this.menu.add(this.menuItem);
/*  918 */     this.menuItem
/*  919 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           if (Methods.havePuzzle) {
/*      */             CrosswordExpress.transfer(30, jfCrossword);
/*      */           } else {
/*      */             Methods.noPuzzle(jfCrossword, "Solve");
/*      */           } 
/*      */         });
/*  929 */     this.menuItem = new JMenuItem("Solve as Fillin");
/*  930 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(70, 8));
/*  931 */     this.menu.add(this.menuItem);
/*  932 */     this.menuItem
/*  933 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           
/*      */           if (Methods.havePuzzle) {
/*      */             CrosswordExpress.transfer(60, jfCrossword);
/*      */           } else {
/*      */             Methods.noPuzzle(jfCrossword, "Solve");
/*      */           } 
/*      */         });
/*  944 */     this.menu = new JMenu("Export");
/*  945 */     menuBar.add(this.menu);
/*  946 */     this.menuItem = new JMenuItem("Export Crossword Web-App");
/*  947 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(87, 1));
/*  948 */     this.menu.add(this.menuItem);
/*  949 */     this.menuItem
/*  950 */       .addActionListener(ae -> Methods.exportWebApp(jfCrossword, "crossword"));
/*      */ 
/*      */     
/*  953 */     this.menuItem = new JMenuItem("Launch a Demo Web App");
/*  954 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(76, 1));
/*  955 */     this.menu.add(this.menuItem);
/*  956 */     this.menuItem
/*  957 */       .addActionListener(ae -> Methods.launchWebApp(jfCrossword, "crossword"));
/*      */ 
/*      */     
/*  960 */     this.menuItem = new JMenuItem("Export Puzzle as Text");
/*  961 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(84, 1));
/*  962 */     this.menu.add(this.menuItem);
/*  963 */     this.menuItem
/*  964 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           
/*      */           if (Methods.havePuzzle) {
/*      */             NodeList.exportText(jfCrossword, true);
/*      */           } else {
/*      */             Methods.noPuzzle(jfCrossword, "Export");
/*      */           } 
/*      */         });
/*  975 */     this.menu = new JMenu("Tasks");
/*  976 */     menuBar.add(this.menu);
/*  977 */     this.menuItem = new JMenuItem("Delete this Puzzle");
/*  978 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(90, 8));
/*  979 */     this.menu.add(this.menuItem);
/*  980 */     this.menuItem
/*  981 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           
/*      */           if (Methods.deleteAPuzzle(jfCrossword, Op.cw[Op.CW.CwPuz.ordinal()], Op.cw[Op.CW.CwDic.ordinal()] + ".dic", pp)) {
/*      */             loadCrossword(Op.cw[Op.CW.CwPuz.ordinal()]);
/*      */             restoreFrame();
/*      */           } 
/*      */         });
/*  991 */     this.menuItem = new JMenuItem("Lockout Words");
/*  992 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(75, 8));
/*  993 */     this.menu.add(this.menuItem);
/*  994 */     this.menuItem
/*  995 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           
/*      */           if (!Methods.havePuzzle) {
/*      */             Methods.noPuzzle(jfCrossword, "Lockout");
/*      */             
/*      */             return;
/*      */           } 
/*      */           lockoutWords();
/*      */           JOptionPane.showMessageDialog(jfCrossword, "<html><center>The words used in this puzzle<br>have been locked out of the <font color=880000>" + Op.cw[Op.CW.CwDic.ordinal()] + "</font> dictionary");
/*      */           restoreFrame();
/*      */         });
/* 1009 */     this.menuItem = new JMenuItem("Transfer Puzzle Clues to Dictionary");
/* 1010 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(84, 8));
/* 1011 */     this.menu.add(this.menuItem);
/* 1012 */     this.menuItem
/* 1013 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           
/*      */           if (!Methods.havePuzzle) {
/*      */             Methods.noPuzzle(jfCrossword, "Move Clues");
/*      */             
/*      */             return;
/*      */           } 
/*      */           Methods.moveCluesToDic(Op.cw[Op.CW.CwDic.ordinal()]);
/*      */           JOptionPane.showMessageDialog(jfCrossword, "<html><center>The clues used in this puzzle<br>have been moved to the <font color=880000>" + Op.cw[Op.CW.CwDic.ordinal()] + "</font> dictionary");
/*      */           restoreFrame();
/*      */         });
/* 1027 */     this.menuItem = new JMenuItem("Rebuild the current puzzle");
/* 1028 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(82, 8));
/* 1029 */     this.menu.add(this.menuItem);
/* 1030 */     this.menuItem
/* 1031 */       .addActionListener(ae -> {
/*      */           if (Def.building == 1) {
/*      */             return;
/*      */           }
/*      */           
/*      */           if (!Methods.havePuzzle) {
/*      */             Methods.noPuzzle(jfCrossword, "Rebuild");
/*      */             
/*      */             return;
/*      */           } 
/*      */           NodeList.attachClues(Op.cw[Op.CW.CwDic.ordinal()], Boolean.valueOf(buildThemed));
/*      */           saveCrossword(Op.cw[Op.CW.CwPuz.ordinal()]);
/*      */           JOptionPane.showMessageDialog(jfCrossword, "<html><center>The puzzle has been rebuilt and saved.");
/*      */           restoreFrame();
/*      */         });
/* 1046 */     this.menu = new JMenu("Help");
/* 1047 */     menuBar.add(this.menu);
/* 1048 */     this.menuItem = new JMenuItem("Crossword Help");
/* 1049 */     this.menu.add(this.menuItem);
/* 1050 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(72, 1));
/* 1051 */     this.menuItem
/* 1052 */       .addActionListener(ae -> Methods.cweHelp(jfCrossword, null, "Building Standard Crossword Puzzles", this.crosswordHelp));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1057 */     this.menuItem = new JMenuItem("Web Application");
/* 1058 */     this.menu.add(this.menuItem);
/* 1059 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(66, 1));
/* 1060 */     this.menuItem
/* 1061 */       .addActionListener(ae -> Methods.cweHelp(jfCrossword, null, "Web Application", webAppHelp));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1066 */     this.menuItem = new JMenuItem("Mega Crosswords");
/* 1067 */     this.menu.add(this.menuItem);
/* 1068 */     this.menuItem.setAccelerator(KeyStroke.getKeyStroke(77, 1));
/* 1069 */     this.menuItem
/* 1070 */       .addActionListener(ae -> Methods.cweHelp(jfCrossword, null, "Mega Crosswords", megaHelp));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1076 */     this.jbButton = Methods.cweButton("Suggest a Word", 10, 45, 180, 26, null);
/* 1077 */     this.jbButton.addActionListener(e -> {
/*      */           if (Def.building == 1)
/*      */             return; 
/*      */           Op.msc[Op.MSC.WordToolsDic.ordinal()] = Op.cw[Op.CW.CwDic.ordinal()];
/*      */           suggest();
/*      */           restoreFrame();
/*      */         });
/* 1084 */     jfCrossword.add(this.jbButton);
/*      */     
/* 1086 */     this.jbButton = Methods.cweButton("Add a Clue", 10, 80, 180, 26, null);
/* 1087 */     this.jbButton.addActionListener(e -> {
/*      */           if (Def.building == 1)
/*      */             return; 
/*      */           Op.msc[Op.MSC.WordToolsDic.ordinal()] = Op.cw[Op.CW.CwDic.ordinal()];
/*      */           addAClue(jfCrossword);
/*      */           restoreFrame();
/*      */         });
/* 1094 */     jfCrossword.add(this.jbButton);
/*      */     
/* 1096 */     jpReviewClue = new ReviewCluePanel(10, 165, 181, 109, jfCrossword);
/* 1097 */     jfCrossword.add(jpReviewClue);
/*      */     
/* 1099 */     this.jbButton = Methods.cweButton("<", 10, 115, 35, 40, null);
/* 1100 */     this.jbButton.addActionListener(e -> {
/*      */           if (Def.building == 1)
/*      */             return;  if (!Methods.havePuzzle)
/*      */             return;  if (Grid.nCur == 0 || !reviewMode)
/*      */             return;  focusColor(false);
/*      */           clueText = (NodeList.nodeList[--Grid.nCur]).clue;
/*      */           jpReviewClue.repaint();
/*      */           Grid.xNew = Grid.xCur = (NodeList.nodeList[Grid.nCur]).x;
/*      */           Grid.yNew = Grid.yCur = (NodeList.nodeList[Grid.nCur]).y;
/*      */           focusColor(true);
/*      */           Def.dispCursor = Boolean.valueOf(true);
/*      */           restoreFrame();
/*      */         });
/* 1113 */     jfCrossword.add(this.jbButton);
/*      */     
/* 1115 */     this.jbReview = Methods.cweButton("Review", 45, 115, 110, 40, null);
/* 1116 */     this.jbReview.addActionListener(e -> {
/*      */           if (Def.building == 1)
/*      */             return; 
/*      */           if (!Methods.havePuzzle) {
/*      */             Methods.noPuzzle(jfCrossword, "Review");
/*      */             return;
/*      */           } 
/*      */           reviewClueControl(true);
/*      */         });
/* 1125 */     jfCrossword.add(this.jbReview);
/*      */     
/* 1127 */     this.jbButton = Methods.cweButton(">", 155, 115, 35, 40, null);
/* 1128 */     this.jbButton.addActionListener(e -> {
/*      */           if (Def.building == 1)
/*      */             return; 
/*      */           if (!Methods.havePuzzle)
/*      */             return; 
/*      */           if (Grid.nCur == NodeList.nodeListLength - 1 || !reviewMode)
/*      */             return; 
/*      */           focusColor(false);
/*      */           clueText = (NodeList.nodeList[++Grid.nCur]).clue;
/*      */           jpReviewClue.repaint();
/*      */           Grid.xNew = Grid.xCur = (NodeList.nodeList[Grid.nCur]).x;
/*      */           Grid.yNew = Grid.yCur = (NodeList.nodeList[Grid.nCur]).y;
/*      */           focusColor(true);
/*      */           Def.dispCursor = Boolean.valueOf(true);
/*      */           restoreFrame();
/*      */         });
/* 1144 */     jfCrossword.add(this.jbButton);
/*      */     
/* 1146 */     this.jbColorize = Methods.cweButton("Colorize Puzzle", 10, 284, 180, 26, null);
/* 1147 */     this.jbColorize.addActionListener(e -> {
/*      */           if (Def.building == 1)
/*      */             return; 
/*      */           if (!Methods.havePuzzle) {
/*      */             Methods.noPuzzle(jfCrossword, "Colorize");
/*      */             return;
/*      */           } 
/*      */           Def.dispCursor = Boolean.valueOf(this.colorizing = !this.colorizing);
/*      */           this.jbColorize.setText(this.colorizing ? "End Colorizing" : "Colorize Puzzle");
/*      */           if (Grid.nCur != -1)
/*      */             focusColor(!this.colorizing); 
/*      */           if (!this.colorizing)
/*      */             saveCrossword(Op.cw[Op.CW.CwPuz.ordinal()]); 
/*      */           restoreFrame();
/*      */         });
/* 1162 */     jfCrossword.add(this.jbColorize);
/*      */     
/* 1164 */     this.jbButton = Methods.cweButton("Choose Hilite Color", 10, 319, 180, 26, null);
/* 1165 */     this.jbButton.addActionListener(e -> {
/*      */           if (Def.building == 1)
/*      */             return; 
/*      */           Color hiliteColor = JColorChooser.showDialog(jfCrossword, "Choose the Hilite Color", new Color(this.thisColor));
/*      */           if (hiliteColor != null)
/*      */             this.thisColor = hiliteColor.getRGB(); 
/*      */           restoreFrame();
/*      */         });
/* 1173 */     jfCrossword.add(this.jbButton);
/*      */     
/* 1175 */     JLabel jl = new JLabel("Cell Type Selection");
/* 1176 */     jl.setForeground(Def.COLOR_LABEL);
/* 1177 */     jl.setSize(125, 16);
/* 1178 */     jl.setLocation(205, 45);
/* 1179 */     jl.setHorizontalAlignment(0);
/* 1180 */     jfCrossword.add(jl);
/*      */     
/* 1182 */     for (int j = 0; j < 15; j++) {
/* 1183 */       ImageIcon myIcon = new ImageIcon("graphics/cellmode" + j + ".png");
/* 1184 */       this.jbCell[j] = new JButton(myIcon);
/* 1185 */       this.jbCell[j].setSize(35, 35);
/* 1186 */       this.jbCell[j].setLocation(205 + 45 * j % 3, 66 + 45 * j / 3);
/* 1187 */       this.jbCell[j].setActionCommand("" + j);
/* 1188 */       this.jbCell[j].addActionListener(this);
/* 1189 */       jfCrossword.add(this.jbCell[j]);
/*      */     } 
/* 1191 */     loadCrossword(Op.cw[Op.CW.CwPuz.ordinal()]);
/* 1192 */     pp = new CrosswordPP(330, 37, jfCrossword);
/*      */     
/* 1194 */     pp
/* 1195 */       .addMouseListener(new MouseAdapter() {
/*      */           public void mousePressed(MouseEvent e) {
/* 1197 */             CrosswordBuild.this.updateGrid(e);
/*      */           }
/*      */         });
/*      */ 
/*      */     
/* 1202 */     if (Def.isMac) {
/* 1203 */       pp
/* 1204 */         .addMouseMotionListener(new MouseAdapter() {
/*      */             public void mouseMoved(MouseEvent e) {
/* 1206 */               if (Def.isMac) {
/* 1207 */                 CrosswordBuild.jfCrossword.setResizable((CrosswordBuild.jfCrossword.getWidth() - e.getX() < 345 && CrosswordBuild.jfCrossword
/* 1208 */                     .getHeight() - e.getY() < 95));
/*      */               }
/*      */             }
/*      */           });
/*      */     }
/* 1213 */     jfCrossword
/* 1214 */       .addKeyListener(new KeyAdapter() {
/*      */           public void keyPressed(KeyEvent e) {
/* 1216 */             CrosswordBuild.this.handleKeyPressed(e);
/*      */           }
/*      */         });
/*      */     
/* 1220 */     focusColor(true);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1225 */     try { DataInputStream dataIn = new DataInputStream(new FileInputStream(Op.cw[dicList[0]] + ".dic/xword.dic"));
/* 1226 */       dataIn.read(DictionaryMtce.dicHeader, 0, 128);
/* 1227 */       dataIn.close(); }
/* 1228 */     catch (IOException exc) { System.out.println("Error"); }
/* 1229 */      addThemeColors(false);
/* 1230 */     restoreFrame();
/*      */   }
/*      */   
/* 1233 */   int[] startIndex = new int[32]; int[] startDir = new int[32]; int links; int contax; int contax2; int earlyLink; int form;
/*      */   
/*      */   void printSubmission(JFrame jf) {
/* 1236 */     Font theFont = new Font("SansSerif", 0, 15);
/*      */     
/* 1238 */     JDialog jdlgPrintSubmission = new JDialog(jf, "Publication Submission", true);
/* 1239 */     jdlgPrintSubmission.setSize(370, 385);
/* 1240 */     jdlgPrintSubmission.setResizable(false);
/* 1241 */     jdlgPrintSubmission.setLayout((LayoutManager)null);
/* 1242 */     jdlgPrintSubmission.setLocation(jf.getX(), jf.getY());
/*      */     
/* 1244 */     jdlgPrintSubmission
/* 1245 */       .addWindowListener(new WindowAdapter() {
/*      */           public void windowClosing(WindowEvent we) {
/* 1247 */             Methods.closeHelp();
/*      */           }
/*      */         });
/*      */     
/* 1251 */     Methods.closeHelp();
/*      */     
/* 1253 */     JLabel jlLabel = new JLabel("Name and Address Details");
/* 1254 */     jlLabel.setForeground(Def.COLOR_LABEL);
/* 1255 */     jlLabel.setSize(375, 25);
/* 1256 */     jlLabel.setLocation(5, 5);
/* 1257 */     jlLabel.setHorizontalAlignment(0);
/* 1258 */     jdlgPrintSubmission.add(jlLabel);
/*      */     
/* 1260 */     JLabel jlName = new JLabel("Name:");
/* 1261 */     jlName.setForeground(Def.COLOR_LABEL);
/* 1262 */     jlName.setSize(60, 20);
/* 1263 */     jlName.setLocation(5, 30);
/* 1264 */     jlName.setHorizontalAlignment(4);
/* 1265 */     jdlgPrintSubmission.add(jlName);
/*      */     
/* 1267 */     JTextField jtfCwName = new JTextField(Op.cw[Op.CW.CwName.ordinal()], 15);
/* 1268 */     jtfCwName.setSize(282, 24);
/* 1269 */     jtfCwName.setLocation(73, 30);
/* 1270 */     jtfCwName.selectAll();
/* 1271 */     jtfCwName.setHorizontalAlignment(2);
/* 1272 */     jtfCwName.setFont(theFont);
/* 1273 */     jdlgPrintSubmission.add(jtfCwName);
/*      */     
/* 1275 */     JLabel jlStreet = new JLabel("Street:");
/* 1276 */     jlStreet.setForeground(Def.COLOR_LABEL);
/* 1277 */     jlStreet.setSize(60, 20);
/* 1278 */     jlStreet.setLocation(5, 58);
/* 1279 */     jlStreet.setHorizontalAlignment(4);
/* 1280 */     jdlgPrintSubmission.add(jlStreet);
/*      */     
/* 1282 */     JTextField jtfCwStreet = new JTextField(Op.cw[Op.CW.CwStreet.ordinal()], 15);
/* 1283 */     jtfCwStreet.setSize(282, 24);
/* 1284 */     jtfCwStreet.setLocation(73, 58);
/* 1285 */     jtfCwStreet.selectAll();
/* 1286 */     jtfCwStreet.setHorizontalAlignment(2);
/* 1287 */     jtfCwStreet.setFont(theFont);
/* 1288 */     jdlgPrintSubmission.add(jtfCwStreet);
/*      */     
/* 1290 */     JLabel jlTown = new JLabel("Town:");
/* 1291 */     jlTown.setForeground(Def.COLOR_LABEL);
/* 1292 */     jlTown.setSize(60, 20);
/* 1293 */     jlTown.setLocation(5, 86);
/* 1294 */     jlTown.setHorizontalAlignment(4);
/* 1295 */     jdlgPrintSubmission.add(jlTown);
/*      */     
/* 1297 */     JTextField jtfCwTown = new JTextField(Op.cw[Op.CW.CwTown.ordinal()], 15);
/* 1298 */     jtfCwTown.setSize(282, 24);
/* 1299 */     jtfCwTown.setLocation(73, 86);
/* 1300 */     jtfCwTown.selectAll();
/* 1301 */     jtfCwTown.setHorizontalAlignment(2);
/* 1302 */     jtfCwTown.setFont(theFont);
/* 1303 */     jdlgPrintSubmission.add(jtfCwTown);
/*      */     
/* 1305 */     JLabel jlState = new JLabel("State:");
/* 1306 */     jlState.setForeground(Def.COLOR_LABEL);
/* 1307 */     jlState.setSize(60, 20);
/* 1308 */     jlState.setLocation(5, 114);
/* 1309 */     jlState.setHorizontalAlignment(4);
/* 1310 */     jdlgPrintSubmission.add(jlState);
/*      */     
/* 1312 */     JTextField jtfCwState = new JTextField(Op.cw[Op.CW.CwState.ordinal()], 15);
/* 1313 */     jtfCwState.setSize(282, 24);
/* 1314 */     jtfCwState.setLocation(73, 114);
/* 1315 */     jtfCwState.selectAll();
/* 1316 */     jtfCwState.setHorizontalAlignment(2);
/* 1317 */     jtfCwState.setFont(theFont);
/* 1318 */     jdlgPrintSubmission.add(jtfCwState);
/*      */     
/* 1320 */     JLabel jlZip = new JLabel("Zip:");
/* 1321 */     jlZip.setForeground(Def.COLOR_LABEL);
/* 1322 */     jlZip.setSize(60, 20);
/* 1323 */     jlZip.setLocation(5, 142);
/* 1324 */     jlZip.setHorizontalAlignment(4);
/* 1325 */     jdlgPrintSubmission.add(jlZip);
/*      */     
/* 1327 */     JTextField jtfCwZip = new JTextField(Op.cw[Op.CW.CwZip.ordinal()], 15);
/* 1328 */     jtfCwZip.setSize(282, 24);
/* 1329 */     jtfCwZip.setLocation(73, 142);
/* 1330 */     jtfCwZip.selectAll();
/* 1331 */     jtfCwZip.setHorizontalAlignment(2);
/* 1332 */     jtfCwZip.setFont(theFont);
/* 1333 */     jdlgPrintSubmission.add(jtfCwZip);
/*      */     
/* 1335 */     JLabel jlCountry = new JLabel("Country:");
/* 1336 */     jlCountry.setForeground(Def.COLOR_LABEL);
/* 1337 */     jlCountry.setSize(60, 20);
/* 1338 */     jlCountry.setLocation(5, 169);
/* 1339 */     jlCountry.setHorizontalAlignment(4);
/* 1340 */     jdlgPrintSubmission.add(jlCountry);
/*      */     
/* 1342 */     JTextField jtfCwCountry = new JTextField(Op.cw[Op.CW.CwCountry.ordinal()], 15);
/* 1343 */     jtfCwCountry.setSize(282, 24);
/* 1344 */     jtfCwCountry.setLocation(73, 169);
/* 1345 */     jtfCwCountry.selectAll();
/* 1346 */     jtfCwCountry.setHorizontalAlignment(2);
/* 1347 */     jtfCwCountry.setFont(theFont);
/* 1348 */     jdlgPrintSubmission.add(jtfCwCountry);
/*      */     
/* 1350 */     JLabel jlEmail = new JLabel("Email:");
/* 1351 */     jlEmail.setForeground(Def.COLOR_LABEL);
/* 1352 */     jlEmail.setSize(60, 20);
/* 1353 */     jlEmail.setLocation(5, 198);
/* 1354 */     jlEmail.setHorizontalAlignment(4);
/* 1355 */     jdlgPrintSubmission.add(jlEmail);
/*      */     
/* 1357 */     JTextField jtfCwEmail = new JTextField(Op.cw[Op.CW.CwEmail.ordinal()], 15);
/* 1358 */     jtfCwEmail.setSize(282, 24);
/* 1359 */     jtfCwEmail.setLocation(73, 198);
/* 1360 */     jtfCwEmail.selectAll();
/* 1361 */     jtfCwEmail.setHorizontalAlignment(2);
/* 1362 */     jtfCwEmail.setFont(theFont);
/* 1363 */     jdlgPrintSubmission.add(jtfCwEmail);
/*      */     
/* 1365 */     JLabel jlFontSz = new JLabel("Preferred Font Size");
/* 1366 */     jlFontSz.setForeground(Def.COLOR_LABEL);
/* 1367 */     jlFontSz.setSize(150, 25);
/* 1368 */     jlFontSz.setLocation(50, 250);
/* 1369 */     jlFontSz.setHorizontalAlignment(4);
/* 1370 */     jdlgPrintSubmission.add(jlFontSz);
/*      */     
/* 1372 */     JTextField jtfCwSubFont = new JTextField(Op.cw[Op.CW.CwSubFont.ordinal()], 15);
/* 1373 */     jtfCwSubFont.setSize(40, 24);
/* 1374 */     jtfCwSubFont.setLocation(220, 250);
/* 1375 */     jtfCwSubFont.selectAll();
/* 1376 */     jtfCwSubFont.setHorizontalAlignment(2);
/* 1377 */     jtfCwSubFont.setFont(theFont);
/* 1378 */     jdlgPrintSubmission.add(jtfCwSubFont);
/*      */     
/* 1380 */     JButton jbPrint = Methods.cweButton("Print", 42, 280, 100, 26, null);
/* 1381 */     jbPrint.addActionListener(e -> {
/*      */           Op.cw[Op.CW.CwName.ordinal()] = paramJTextField1.getText(); Op.cw[Op.CW.CwStreet.ordinal()] = paramJTextField2.getText(); Op.cw[Op.CW.CwTown.ordinal()] = paramJTextField3.getText();
/*      */           Op.cw[Op.CW.CwState.ordinal()] = paramJTextField4.getText();
/*      */           Op.cw[Op.CW.CwZip.ordinal()] = paramJTextField5.getText();
/*      */           Op.cw[Op.CW.CwCountry.ordinal()] = paramJTextField6.getText();
/*      */           Op.cw[Op.CW.CwEmail.ordinal()] = paramJTextField7.getText();
/*      */           Op.cw[Op.CW.CwSubFont.ordinal()] = paramJTextField8.getText();
/*      */           NodeList.sortNodeList(1);
/*      */           this.startIndex[0] = 0;
/*      */           this.startDir[0] = 0;
/*      */           PrinterJob job = PrinterJob.getPrinterJob();
/*      */           job.setPrintable(new NYTsubmission());
/*      */           if (job.printDialog())
/*      */             try {
/*      */               job.print();
/* 1396 */             } catch (Exception pe) {
/*      */               System.out.println("Error");
/*      */             }   NodeList.sortNodeList(2);
/*      */           paramJDialog.dispose();
/*      */         });
/* 1401 */     jdlgPrintSubmission.add(jbPrint);
/*      */     
/* 1403 */     JButton jbCancel = Methods.cweButton("Cancel", 42, 315, 100, 26, null);
/* 1404 */     jbCancel.addActionListener(e -> {
/*      */           Methods.clickedOK = false;
/*      */           paramJDialog.dispose();
/*      */           Methods.closeHelp();
/*      */         });
/* 1409 */     jdlgPrintSubmission.add(jbCancel);
/*      */     
/* 1411 */     JButton jbHelp = Methods.cweButton("<html><font size=6 color=BB0000 face=Serif>Help ", 162, 280, 160, 61, new ImageIcon("graphics/help.png"));
/* 1412 */     jbHelp.addActionListener(e -> Methods.cweHelp(null, paramJDialog, "Puzzle Submission", printSubmission));
/*      */     
/* 1414 */     jdlgPrintSubmission.add(jbHelp);
/*      */     
/* 1416 */     jdlgPrintSubmission.getRootPane().setDefaultButton(jbPrint);
/* 1417 */     Methods.setDialogSize(jdlgPrintSubmission, 365, 351);
/*      */   }
/*      */   public class NYTsubmission implements Printable { int start; int linesPerPage; int nodeID; int line; int theDir;
/*      */     int wLen;
/*      */     int wordSt;
/*      */     int wPage;
/*      */     int naStart;
/*      */     String strP;
/*      */     String strM;
/*      */     
/*      */     public int print(Graphics g, PageFormat pf, int pageIndex) {
/* 1428 */       g.setFont(new Font("SansSerif", 0, Op.getInt(Op.CW.CwSubFont.ordinal(), Op.cw)));
/* 1429 */       FontMetrics fm = g.getFontMetrics();
/* 1430 */       int lineHeight = fm.getHeight();
/* 1431 */       this.linesPerPage = (int)(pf.getImageableHeight() / lineHeight);
/* 1432 */       Graphics2D g2d = (Graphics2D)g;
/* 1433 */       g2d.translate(pf.getImageableX(), pf.getImageableY());
/*      */       
/* 1435 */       for (this.wLen = 0, this.nodeID = 0; this.nodeID < NodeList.nodeListLength; this.nodeID++) {
/* 1436 */         int len = fm.stringWidth((NodeList.nodeList[this.nodeID]).word);
/* 1437 */         if (len > this.wLen)
/* 1438 */           this.wLen = len; 
/*      */       } 
/* 1440 */       this.wPage = (int)pf.getImageableWidth();
/* 1441 */       this.wordSt = this.wPage - this.wLen;
/* 1442 */       this.naStart = (this.wPage - fm.stringWidth(Op.cw[Op.CW.CwName.ordinal()])) / 2;
/*      */       
/* 1444 */       Grid.xCell = Grid.yCell = this.wPage / (Grid.xSz + 1); Grid.yOrg = 6 * lineHeight;
/* 1445 */       Grid.xOrg = (this.wPage - Grid.xCell * Grid.xSz) / 2;
/* 1446 */       if (CrosswordBuild.this.startIndex[pageIndex] == 1001) {
/* 1447 */         this.naStart = (this.wPage - fm.stringWidth(Op.cw[Op.CW.CwName.ordinal()])) / 3;
/* 1448 */         Def.displaySubmission = Boolean.valueOf(true);
/* 1449 */         Def.displaySubmissionPuz = Boolean.valueOf(true);
/* 1450 */         g.drawString("" + (pageIndex + 1), 0, lineHeight);
/* 1451 */         g.drawString(Op.cw[Op.CW.CwName.ordinal()], this.naStart, lineHeight);
/* 1452 */         g.drawString(Op.cw[Op.CW.CwStreet.ordinal()], this.naStart, 2 * lineHeight);
/* 1453 */         g.drawString(Op.cw[Op.CW.CwTown.ordinal()] + " " + Op.cw[Op.CW.CwState.ordinal()] + " " + Op.cw[Op.CW.CwZip.ordinal()], this.naStart, 3 * lineHeight);
/*      */         
/* 1455 */         g.drawString(Op.cw[Op.CW.CwCountry.ordinal()], this.naStart, 4 * lineHeight);
/* 1456 */         if (Op.cw[Op.CW.CwEmail.ordinal()].length() > 0)
/* 1457 */           g.drawString(Op.cw[Op.CW.CwEmail.ordinal()], this.naStart, 5 * lineHeight); 
/* 1458 */         CrosswordBuild.drawCrossword(g2d);
/* 1459 */         Def.displaySubmission = Boolean.valueOf(false);
/* 1460 */         Def.displaySubmissionPuz = Boolean.valueOf(false);
/* 1461 */         CrosswordBuild.this.startIndex[pageIndex + 1] = 1002;
/* 1462 */         return 0;
/*      */       } 
/* 1464 */       if (CrosswordBuild.this.startIndex[pageIndex] == 1002) {
/* 1465 */         this.naStart = (this.wPage - fm.stringWidth(Op.cw[Op.CW.CwName.ordinal()])) / 3;
/* 1466 */         Def.displaySubmission = Boolean.valueOf(true);
/* 1467 */         g.drawString("" + (pageIndex + 1), 0, lineHeight);
/* 1468 */         g.drawString(Op.cw[Op.CW.CwName.ordinal()], this.naStart, lineHeight);
/* 1469 */         g.drawString(Op.cw[Op.CW.CwStreet.ordinal()], this.naStart, 2 * lineHeight);
/* 1470 */         g.drawString(Op.cw[Op.CW.CwTown.ordinal()] + " " + Op.cw[Op.CW.CwState.ordinal()] + " " + Op.cw[Op.CW.CwZip.ordinal()], this.naStart, 3 * lineHeight);
/*      */         
/* 1472 */         g.drawString(Op.cw[Op.CW.CwCountry.ordinal()], this.naStart, 4 * lineHeight);
/* 1473 */         if (Op.cw[Op.CW.CwEmail.ordinal()].length() > 0)
/* 1474 */           g.drawString(Op.cw[Op.CW.CwEmail.ordinal()], this.naStart, 5 * lineHeight); 
/* 1475 */         CrosswordBuild.drawCrossword(g2d);
/* 1476 */         Def.displaySubmission = Boolean.valueOf(false);
/* 1477 */         CrosswordBuild.this.startIndex[pageIndex + 1] = 1003;
/* 1478 */         return 0;
/*      */       } 
/* 1480 */       if (CrosswordBuild.this.startIndex[pageIndex] == 1003) {
/* 1481 */         return 1;
/*      */       }
/* 1483 */       this.line = 1;
/* 1484 */       g.drawString("" + (pageIndex + 1), 0, lineHeight);
/* 1485 */       g.drawString(Op.cw[Op.CW.CwName.ordinal()], this.naStart, lineHeight);
/* 1486 */       this.line += 2;
/*      */       
/* 1488 */       if (pageIndex == 0) {
/* 1489 */         g.drawString("ACROSS", 0, this.line++ * lineHeight);
/* 1490 */         this.line++;
/*      */       } 
/*      */       
/* 1493 */       this.theDir = CrosswordBuild.this.startDir[pageIndex];
/* 1494 */       for (this.nodeID = CrosswordBuild.this.startIndex[pageIndex]; this.nodeID < NodeList.nodeListLength; this.nodeID++) {
/* 1495 */         if (this.theDir == 0 && (NodeList.nodeList[this.nodeID]).direction == 1) {
/* 1496 */           if (this.linesPerPage - this.line > 4) {
/* 1497 */             this.theDir = 1;
/* 1498 */             g.drawString("DOWN", 0, this.line++ * lineHeight);
/* 1499 */             this.line++;
/* 1500 */             CrosswordBuild.this.startDir[pageIndex + 1] = 1;
/*      */           } else {
/*      */             
/* 1503 */             CrosswordBuild.this.startDir[pageIndex + 1] = 0;
/* 1504 */             CrosswordBuild.this.startIndex[pageIndex + 1] = this.nodeID + 1;
/* 1505 */             return 0;
/*      */           } 
/*      */         }
/*      */         
/* 1509 */         this.strP = (NodeList.nodeList[this.nodeID]).id + " " + (NodeList.nodeList[this.nodeID]).clue;
/* 1510 */         if (fm.stringWidth(this.strP) / this.wordSt > this.linesPerPage - this.line - 1) {
/* 1511 */           CrosswordBuild.this.startIndex[pageIndex + 1] = this.nodeID;
/* 1512 */           CrosswordBuild.this.startDir[pageIndex + 1] = this.theDir;
/* 1513 */           return 0;
/*      */         } 
/* 1515 */         g.drawString((NodeList.nodeList[this.nodeID]).word, this.wordSt, this.line * lineHeight);
/*      */         
/* 1517 */         label59: while (this.strP.length() > 0) {
/* 1518 */           if (fm.stringWidth(this.strP) < this.wordSt) {
/* 1519 */             g.drawString(this.strP, 0, this.line++ * lineHeight);
/* 1520 */             this.strP = "";
/*      */             continue;
/*      */           } 
/* 1523 */           for (int len = this.strP.length();; len--) {
/* 1524 */             if (this.strP.charAt(len - 1) == ' ' && fm.stringWidth(this.strP.substring(0, len)) < this.wordSt) {
/* 1525 */               g.drawString(this.strP.substring(0, len), 0, this.line++ * lineHeight);
/* 1526 */               this.strP = this.strP.substring(len);
/*      */               
/*      */               continue label59;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */         
/* 1533 */         this.line++;
/* 1534 */         if (this.line >= this.linesPerPage) {
/* 1535 */           CrosswordBuild.this.startIndex[pageIndex + 1] = this.nodeID + 1;
/* 1536 */           CrosswordBuild.this.startDir[pageIndex + 1] = this.theDir;
/*      */           break;
/*      */         } 
/*      */       } 
/* 1540 */       if (this.nodeID >= NodeList.nodeListLength - 1)
/* 1541 */         CrosswordBuild.this.startIndex[pageIndex + 1] = 1001; 
/* 1542 */       return 0;
/*      */     } }
/*      */ 
/*      */   
/*      */   static void restoreFrame() {
/* 1547 */     jfCrossword.setVisible(true);
/* 1548 */     Insets insets = jfCrossword.getInsets();
/* 1549 */     panelW = jfCrossword.getWidth() - insets.left + insets.right + 330;
/* 1550 */     panelH = jfCrossword.getHeight() - insets.top + insets.bottom + 37 + menuBar.getHeight();
/* 1551 */     pp.setSize(panelW, panelH);
/* 1552 */     Def.dispSolArray = Boolean.valueOf(false);
/* 1553 */     Def.dispCursor = Boolean.valueOf(true);
/* 1554 */     Def.dispNullCells = Boolean.valueOf(true);
/* 1555 */     jfCrossword.requestFocusInWindow();
/* 1556 */     jpReviewClue.repaint();
/* 1557 */     pp.repaint();
/* 1558 */     Methods.infoPanel(jl1, jl2, "Build Crossword", "Dictionary : " + Op.cw[Op.CW.CwDic.ordinal()] + "  -|-  Puzzle : " + Op.cw[Op.CW.CwPuz
/* 1559 */           .ordinal()], jfCrossword.getWidth());
/*      */   }
/*      */   
/*      */   static void setSizesAndOffsets(int x, int y, int width, int height, int inset, boolean print) {
/* 1563 */     if (Def.puzzleMode == 12) {
/* 1564 */       Grid.xSz++;
/* 1565 */       Grid.ySz++;
/*      */     } 
/* 1567 */     int i = (width - inset) / Grid.xSz;
/* 1568 */     int j = (height - inset) / Grid.ySz;
/* 1569 */     Grid.xCell = Grid.yCell = (i < j) ? i : j;
/* 1570 */     Grid.xOrg = print ? (x + (width - Grid.xSz * Grid.xCell) / 2) : 10;
/* 1571 */     Grid.yOrg = print ? (y + (height - Grid.ySz * Grid.yCell) / 2) : 10;
/* 1572 */     if (Def.puzzleMode == 12) {
/* 1573 */       Grid.xSz--;
/* 1574 */       Grid.ySz--;
/* 1575 */       Grid.xOrg += Grid.xCell;
/* 1576 */       Grid.yOrg += Grid.yCell;
/*      */     } 
/*      */   }
/*      */   
/*      */   static JComboBox JComboBoxTheme(int x, int y, int w, int h) {
/* 1581 */     File fl = new File(System.getProperty("user.dir"));
/* 1582 */     String[] s = fl.list();
/* 1583 */     JComboBox<String> jcbb = new JComboBox<>();
/* 1584 */     jcbb.setSize(w, h);
/* 1585 */     jcbb.setLocation(x, y);
/* 1586 */     jcbb.addItem("   <None>");
/* 1587 */     for (int i = 0; i < s.length; i++) {
/* 1588 */       if (s[i].endsWith(".dic") && s[i].startsWith("$") && !s[i].startsWith(".")) {
/* 1589 */         s[i] = s[i].substring(0, s[i].lastIndexOf('.'));
/* 1590 */         jcbb.addItem("   " + s[i]);
/*      */       } 
/* 1592 */     }  jcbb.setBackground(Def.COLOR_BUTTONBG);
/* 1593 */     return jcbb;
/*      */   }
/*      */ 
/*      */   
/*      */   void crosswordOptions() {
/* 1598 */     JDialog jdlgCrosswordOptions = new JDialog(jfCrossword, "Build Options", true);
/* 1599 */     jdlgCrosswordOptions.setSize(270, 485);
/* 1600 */     jdlgCrosswordOptions.setResizable(false);
/* 1601 */     jdlgCrosswordOptions.setLayout((LayoutManager)null);
/* 1602 */     jdlgCrosswordOptions.setLocation(jfCrossword.getX(), jfCrossword.getY());
/*      */     
/* 1604 */     jdlgCrosswordOptions
/* 1605 */       .addWindowListener(new WindowAdapter() {
/*      */           public void windowClosing(WindowEvent we) {
/* 1607 */             Methods.closeHelp();
/*      */           }
/*      */         });
/*      */     
/* 1611 */     Methods.closeHelp();
/*      */     
/* 1613 */     HowManyPuzzles hmp = new HowManyPuzzles(jdlgCrosswordOptions, 10, 10, this.howMany, this.startPuz, true);
/* 1614 */     hmp.jcbVaryDiff.setEnabled(false);
/*      */     
/* 1616 */     JCheckBox jcbAutoLock = new JCheckBox("Use Automatic Lockout & Unlock", Op.getBool(Op.CW.CwAutoLock.ordinal(), Op.cw).booleanValue());
/* 1617 */     jcbAutoLock.setForeground(Def.COLOR_LABEL);
/* 1618 */     jcbAutoLock.setOpaque(false);
/* 1619 */     jcbAutoLock.setSize(250, 20);
/* 1620 */     jcbAutoLock.setLocation(10, 120);
/* 1621 */     jdlgCrosswordOptions.add(jcbAutoLock);
/*      */     
/* 1623 */     JPanel jpCWO = new JPanel();
/* 1624 */     jpCWO.setLayout((LayoutManager)null);
/* 1625 */     jpCWO.setLocation(10, 147);
/* 1626 */     jpCWO.setSize(240, 190);
/* 1627 */     jpCWO.setOpaque(true);
/* 1628 */     jpCWO.setBorder(BorderFactory.createEtchedBorder());
/* 1629 */     jdlgCrosswordOptions.add(jpCWO);
/*      */     
/* 1631 */     JCheckBox jcbTheme = new JCheckBox("Build a Theme Puzzle", buildThemed);
/* 1632 */     jcbTheme.setForeground(Def.COLOR_LABEL);
/* 1633 */     jcbTheme.setOpaque(false);
/* 1634 */     jcbTheme.setSize(175, 20);
/* 1635 */     jcbTheme.setLocation(10, 5);
/* 1636 */     jpCWO.add(jcbTheme);
/*      */     
/* 1638 */     JLabel jlSelDic = new JLabel("Select Dictionaries");
/* 1639 */     jlSelDic.setForeground(Def.COLOR_LABEL);
/* 1640 */     jlSelDic.setSize(170, 16);
/* 1641 */     jlSelDic.setLocation(10, 30);
/* 1642 */     jlSelDic.setHorizontalAlignment(2);
/* 1643 */     jpCWO.add(jlSelDic);
/*      */     
/* 1645 */     JComboBox jcbbDic1 = JComboBoxTheme(10, 50, 220, 26);
/* 1646 */     jpCWO.add(jcbbDic1);
/* 1647 */     if (!Op.cw[dicList[4]].startsWith("$")) {
/* 1648 */       Op.cw[dicList[4]] = "<None>";
/*      */     }
/*      */     
/* 1651 */     jcbbDic1.setSelectedItem("   " + Op.cw[dicList[4]]);
/*      */     
/* 1653 */     jcbbDic1
/* 1654 */       .addActionListener(ae -> Op.cw[dicList[4]] = paramJComboBox.getSelectedItem().toString().trim());
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1659 */     JComboBox jcbbDic2 = JComboBoxTheme(10, 85, 220, 26);
/* 1660 */     jpCWO.add(jcbbDic2);
/* 1661 */     jcbbDic2.setSelectedItem("   " + Op.cw[dicList[3]]);
/* 1662 */     jcbbDic2
/* 1663 */       .addActionListener(ae -> Op.cw[dicList[3]] = paramJComboBox.getSelectedItem().toString().trim());
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1668 */     JComboBox jcbbDic3 = JComboBoxTheme(10, 120, 220, 26);
/* 1669 */     jpCWO.add(jcbbDic3);
/* 1670 */     jcbbDic3.setSelectedItem("   " + Op.cw[dicList[2]]);
/* 1671 */     jcbbDic3
/* 1672 */       .addActionListener(ae -> Op.cw[dicList[2]] = paramJComboBox.getSelectedItem().toString().trim());
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1677 */     JComboBox jcbbDic4 = JComboBoxTheme(10, 155, 220, 26);
/* 1678 */     jpCWO.add(jcbbDic4);
/* 1679 */     jcbbDic4.setSelectedItem("   " + Op.cw[dicList[1]]);
/* 1680 */     jcbbDic4
/* 1681 */       .addActionListener(ae -> Op.cw[dicList[1]] = paramJComboBox.getSelectedItem().toString().trim());
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1686 */     JCheckBox jcbNoLockout = new JCheckBox("<html><u>U</u>se all words<br>(including locked words)", Op.getBool(Op.CW.CwIgnoreLockout.ordinal(), Op.cw).booleanValue());
/* 1687 */     jcbNoLockout.setForeground(Def.COLOR_LABEL);
/* 1688 */     jcbNoLockout.setOpaque(false);
/* 1689 */     jcbNoLockout.setSize(280, 30);
/* 1690 */     jcbNoLockout.setLocation(10, 345);
/* 1691 */     jdlgCrosswordOptions.add(jcbNoLockout);
/*      */     
/* 1693 */     JButton jbOK = Methods.cweButton("OK", 10, 382, 80, 26, null);
/* 1694 */     jbOK.addActionListener(e -> {
/*      */           buildThemed = paramJCheckBox1.isSelected();
/*      */           Op.setBool(Op.CW.CwIgnoreLockout.ordinal(), Boolean.valueOf(paramJCheckBox2.isSelected()), Op.cw);
/*      */           Op.setBool(Op.CW.CwAutoLock.ordinal(), Boolean.valueOf(paramJCheckBox3.isSelected()), Op.cw);
/*      */           this.howMany = Integer.parseInt(paramHowManyPuzzles.jtfHowMany.getText());
/*      */           this.startPuz = Integer.parseInt(paramHowManyPuzzles.jtfStartPuz.getText());
/*      */           Methods.clickedOK = true;
/*      */           for (int i = 1; i < 5; i++)
/*      */             themeDic[i] = Op.cw[dicList[i]].startsWith("$"); 
/*      */           paramJDialog.dispose();
/*      */           Methods.closeHelp();
/*      */         });
/* 1706 */     jdlgCrosswordOptions.add(jbOK);
/*      */     
/* 1708 */     JButton jbCancel = Methods.cweButton("Cancel", 10, 417, 80, 26, null);
/* 1709 */     jbCancel.addActionListener(e -> {
/*      */           Methods.clickedOK = false;
/*      */           paramJDialog.dispose();
/*      */           Methods.closeHelp();
/*      */         });
/* 1714 */     jdlgCrosswordOptions.add(jbCancel);
/*      */     
/* 1716 */     JButton jbHelp = Methods.cweButton("<html><font size=6 color=BB0000 face=Serif>Help ", 100, 382, 150, 61, new ImageIcon("graphics/help.png"));
/* 1717 */     jbHelp.addActionListener(e -> Methods.cweHelp(null, paramJDialog, "Crossword Options", this.crosswordOptionsHelp));
/*      */     
/* 1719 */     jdlgCrosswordOptions.add(jbHelp);
/*      */     
/* 1721 */     jdlgCrosswordOptions.getRootPane().setDefaultButton(jbOK);
/* 1722 */     Methods.setDialogSize(jdlgCrosswordOptions, 260, 453);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void saveCrossword(String crosswordName) {
/*      */     try {
/* 1730 */       DataOutputStream dataOut = new DataOutputStream(new FileOutputStream(Op.cw[Op.CW.CwDic.ordinal()] + ".dic/" + crosswordName));
/* 1731 */       dataOut.writeInt(Grid.xSz);
/* 1732 */       dataOut.writeInt(Grid.ySz);
/* 1733 */       dataOut.writeByte(Methods.noReveal);
/* 1734 */       dataOut.writeByte(Methods.noErrors); int i;
/* 1735 */       for (i = 0; i < 54; i++)
/* 1736 */         dataOut.writeByte(0); 
/* 1737 */       for (int j = 0; j < Grid.ySz; j++) {
/* 1738 */         for (i = 0; i < Grid.xSz; i++) {
/* 1739 */           dataOut.writeInt(Grid.mode[i][j]);
/* 1740 */           dataOut.writeInt(Grid.letter[i][j]);
/* 1741 */           dataOut.writeInt(Grid.sol[i][j]);
/* 1742 */           dataOut.writeInt(Grid.color[i][j]);
/*      */         } 
/* 1744 */       }  dataOut.writeUTF(Methods.puzzleTitle);
/* 1745 */       dataOut.writeUTF(Methods.author);
/* 1746 */       dataOut.writeUTF(Methods.copyright);
/* 1747 */       dataOut.writeUTF(Methods.puzzleNumber);
/* 1748 */       dataOut.writeUTF(Methods.puzzleNotes);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1755 */       for (i = 0; i < NodeList.nodeListLength; i++) {
/* 1756 */         dataOut.writeUTF((NodeList.nodeList[i]).word);
/* 1757 */         dataOut.writeUTF((NodeList.nodeList[i]).clue);
/*      */       } 
/*      */       
/* 1760 */       if (buildThemed)
/* 1761 */         for (i = 4; i > 0; i--)
/* 1762 */           dataOut.writeUTF((Op.cw[dicList[i]].length() > 0) ? Op.cw[dicList[i]] : "");  
/* 1763 */       dataOut.close();
/*      */     } catch (IOException exc) {
/* 1765 */       System.out.println("Error");
/* 1766 */     }  Methods.havePuzzle = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void loadCrossword(String crosswordName) {
/* 1773 */     Op.cw[Op.CW.CwDic.ordinal()] = Methods.confirmDictionary(Op.cw[Op.CW.CwDic.ordinal()] + ".dic", false);
/*      */     
/* 1775 */     File fl = new File(Op.cw[Op.CW.CwDic.ordinal()] + ".dic/" + crosswordName);
/* 1776 */     if (!fl.exists()) {
/* 1777 */       fl = new File(Op.cw[Op.CW.CwDic.ordinal()] + ".dic/");
/* 1778 */       String[] s = fl.list(); int i;
/* 1779 */       for (i = 0; i < s.length && (
/* 1780 */         s[i].lastIndexOf(".crossword") == -1 || s[i].charAt(0) == '.'); i++);
/*      */ 
/*      */       
/* 1783 */       if (i == s.length) {
/* 1784 */         makeGrid();
/*      */         return;
/*      */       } 
/* 1787 */       crosswordName = s[i];
/* 1788 */       Op.cw[Op.CW.CwPuz.ordinal()] = crosswordName;
/*      */     } 
/*      */     try {
/* 1791 */       DataInputStream dataIn = new DataInputStream(new FileInputStream(Op.cw[Op.CW.CwDic.ordinal()] + ".dic/" + crosswordName));
/* 1792 */       Grid.xSz = dataIn.readInt();
/* 1793 */       Grid.ySz = dataIn.readInt();
/* 1794 */       Methods.noReveal = dataIn.readByte();
/* 1795 */       Methods.noErrors = dataIn.readByte(); int i;
/* 1796 */       for (i = 0; i < 54; i++)
/* 1797 */         dataIn.readByte(); 
/* 1798 */       for (int j = 0; j < Grid.ySz; j++) {
/* 1799 */         for (i = 0; i < Grid.xSz; i++) {
/* 1800 */           Grid.mode[i][j] = dataIn.readInt();
/* 1801 */           Grid.letter[i][j] = dataIn.readInt();
/* 1802 */           Grid.sol[i][j] = dataIn.readInt();
/* 1803 */           Grid.color[i][j] = dataIn.readInt();
/*      */         } 
/*      */       } 
/* 1806 */       Methods.puzzleTitle = dataIn.readUTF();
/* 1807 */       Methods.author = dataIn.readUTF();
/* 1808 */       Methods.copyright = dataIn.readUTF();
/* 1809 */       Methods.puzzleNumber = dataIn.readUTF();
/* 1810 */       Methods.puzzleNotes = dataIn.readUTF();
/* 1811 */       NodeList.buildNodeList();
/* 1812 */       for (i = 0; i < NodeList.nodeListLength; i++) {
/* 1813 */         (NodeList.nodeList[i]).word = dataIn.readUTF();
/* 1814 */         (NodeList.nodeList[i]).clue = dataIn.readUTF();
/* 1815 */         if ((NodeList.nodeList[i]).clue.length() < 2)
/* 1816 */           (NodeList.nodeList[i]).clue = "No clue"; 
/*      */       } 
/* 1818 */       dataIn.close();
/*      */     } catch (IOException exc) {
/* 1820 */       System.out.println("Error");
/*      */     } 
/* 1822 */     Methods.havePuzzle = true;
/* 1823 */     Grid.xCur = (NodeList.nodeList[0]).x;
/* 1824 */     Grid.yCur = (NodeList.nodeList[0]).y;
/*      */   }
/*      */   
/*      */   String readString(DataInputStream dataIn, boolean skip) {
/* 1828 */     int c = 0;
/* 1829 */     char[] wordArray = new char[100];
/*      */ 
/*      */     
/* 1832 */     try { c = 0; while (true) {
/* 1833 */         char ch = (char)dataIn.readByte();
/* 1834 */         if (skip) {
/* 1835 */           dataIn.readByte();
/* 1836 */           dataIn.readByte();
/* 1837 */           dataIn.readByte();
/* 1838 */           skip = false;
/*      */           continue;
/*      */         } 
/* 1841 */         if (ch == '\r') {
/* 1842 */           dataIn.readByte();
/*      */           
/*      */           break;
/*      */         } 
/* 1846 */         wordArray[c++] = ch;
/*      */       }  }
/* 1848 */     catch (IOException exc) { System.out.println("Error"); }
/* 1849 */      String str = new String(wordArray, 0, c);
/* 1850 */     return str;
/*      */   }
/*      */   
/*      */   void crosswordWizard() {
/* 1854 */     this.jdlgWizard = new JDialog(jfCrossword);
/* 1855 */     this.jdlgWizard.setUndecorated(true);
/* 1856 */     this.jdlgWizard.setSize(330, 420);
/* 1857 */     this.jdlgWizard.setResizable(false);
/* 1858 */     this.jdlgWizard.setLayout((LayoutManager)null);
/* 1859 */     this.jdlgWizard.setLocation(jfCrossword.getX() + jfCrossword.getWidth() + 10, jfCrossword.getY());
/* 1860 */     this.jdlgWizard.setVisible(true);
/* 1861 */     wizardRunning = true;
/*      */     
/* 1863 */     Runnable buildThread = () -> {
/*      */         Def.dispCursor = Boolean.valueOf(false);
/*      */         
/*      */         Methods.havePuzzle = false;
/*      */         focusColor(false);
/*      */         if (buildThemed) {
/*      */           Methods.havePuzzle = themeBuild();
/*      */         } else if (this.howMany == 1) {
/*      */           if (buildCrossword(true)) {
/*      */             Methods.havePuzzle = true;
/*      */             saveCrossword(Op.cw[Op.CW.CwPuz.ordinal()]);
/*      */           } 
/*      */         } else {
/*      */           multiBuild();
/*      */           Methods.havePuzzle = true;
/*      */         } 
/*      */         if (Def.building == 2) {
/*      */           Def.building = 0;
/*      */           Methods.interrupted(jfCrossword);
/*      */           makeGrid();
/*      */           restoreFrame();
/*      */           return;
/*      */         } 
/*      */         Def.building = 0;
/*      */         if (Methods.havePuzzle) {
/*      */           if (!buildThemed) {
/*      */             saveCrossword(Op.cw[Op.CW.CwPuz.ordinal()]);
/*      */           }
/*      */           Methods.puzzleSaved(jfCrossword, Op.cw[Op.CW.CwDic.ordinal()] + ".dic", Op.cw[Op.CW.CwPuz.ordinal()]);
/*      */         } else {
/*      */           makeGrid();
/*      */           Methods.cantBuild(jfCrossword);
/*      */         } 
/*      */       };
/* 1897 */     String seldic = "Use the drop down list to select the dictionary you would like to use for this puzzle.<p><p>Click OK when you are satisfied with your choice.<p><p>If you click Cancel, this Wizard will terminate.";
/*      */ 
/*      */ 
/*      */     
/* 1901 */     String puzopt = "Adjust any of the Crossword Options you would like to change for this puzzle.<p><p>The Help button will explain all you need to know about the Options.<p><p>Click OK to continue, or click Cancel to abandon the process, and terminate the Wizard.";
/*      */ 
/*      */ 
/*      */     
/* 1905 */     String puzname = "Enter the name of the file into which the puzzle will be saved after construction<p><p>There are several other input fields and options which you can also use. Use the Help button to see complete instructions.<p><p>Click OK to continue, or click Cancel to abandon the process, and terminate the Wizard.";
/*      */ 
/*      */ 
/*      */     
/* 1909 */     String gridsel = "Select the Grid you would like to use in the construction of your puzzle.<p><p>If you are making multiple puzzles, you can select any number of Grids which will be used sequentially during construction.<p><p>Click OK to start constructing, or click Cancel to abandon the process, and terminate the Wizard.";
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1914 */     JLabel wizardText = new JLabel(Methods.wizardText("Select a Dictionary", seldic));
/* 1915 */     wizardText.setSize(320, 410);
/* 1916 */     wizardText.setLocation(5, 5);
/* 1917 */     wizardText.setHorizontalAlignment(0);
/* 1918 */     this.jdlgWizard.add(wizardText);
/*      */     
/* 1920 */     Methods.selectDictionary(jfCrossword, Op.cw[Op.CW.CwDic.ordinal()], 1);
/* 1921 */     if (Methods.clickedOK) {
/* 1922 */       Op.cw[Op.CW.CwDic.ordinal()] = Methods.dictionaryName;
/* 1923 */       restoreFrame();
/* 1924 */       wizardText.setText(Methods.wizardText("Crossword Options", puzopt));
/* 1925 */       crosswordOptions();
/* 1926 */       if (Methods.clickedOK) {
/* 1927 */         makeGrid();
/* 1928 */         if (this.howMany == 1) {
/* 1929 */           wizardText.setText(Methods.wizardText("Name the Puzzle", puzname));
/* 1930 */           Methods.puzzleDescriptionDialog(jfCrossword, Op.cw[Op.CW.CwPuz.ordinal()].substring(0, Op.cw[Op.CW.CwPuz.ordinal()].indexOf(".crossword")), Op.cw[Op.CW.CwDic.ordinal()] + ".dic", ".crossword");
/*      */         } 
/* 1932 */         if (Methods.clickedOK) {
/* 1933 */           Op.cw[Op.CW.CwPuz.ordinal()] = Methods.theFileName;
/* 1934 */           makeGrid();
/* 1935 */           Grid.loadGrid(Op.cw[Op.CW.CwGrid.ordinal()]);
/* 1936 */           wizardText.setText(Methods.wizardText("Select the Grid(s)", gridsel));
/*      */           
/* 1938 */           Methods.clearGrid(Grid.letter);
/* 1939 */           Def.dispCursor = Boolean.valueOf(false);
/* 1940 */           Def.dispSolArray = Boolean.valueOf(true);
/* 1941 */           Def.puzzleMode = 2;
/*      */           
/* 1943 */           JFileChooser chooser = new JFileChooser(System.getProperty("user.dir") + "/grids");
/* 1944 */           chooser.setFileFilter(new FileNameExtensionFilter("Grid", new String[] { "grid" }));
/* 1945 */           chooser.setSelectedFile(new File(Op.cw[Op.CW.CwGrid.ordinal()]));
/* 1946 */           chooser.setAccessory(new Preview(chooser));
/* 1947 */           chooser.setMultiSelectionEnabled(true);
/* 1948 */           if (chooser.showDialog(jfCrossword, "Select Grid") == 0) {
/* 1949 */             this.grids = chooser.getSelectedFiles();
/*      */           }
/* 1951 */           Def.puzzleMode = 4;
/* 1952 */           if (this.grids != null)
/* 1953 */             Op.cw[Op.CW.CwGrid.ordinal()] = this.grids[0].getName(); 
/* 1954 */           Grid.loadGrid(Op.cw[Op.CW.CwGrid.ordinal()]);
/* 1955 */           Def.dispSolArray = Boolean.valueOf(false);
/* 1956 */           Def.dispCursor = Boolean.valueOf(true);
/* 1957 */           if (this.grids != null) {
/* 1958 */             this.thread = new Thread(buildThread);
/* 1959 */             this.thread.start();
/* 1960 */             Def.building = 1;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/* 1965 */     this.jdlgWizard.dispose();
/* 1966 */     wizardRunning = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void drawCrossword(Graphics2D g2) {
/* 1974 */     int nL = (int)Math.ceil((Grid.xCell / 60.0F)), wL = (int)Math.ceil((Grid.xCell / 10.0F));
/* 1975 */     Stroke normalStroke = new BasicStroke(nL, 2, 0);
/* 1976 */     Stroke wideStroke = new BasicStroke(wL, 0, 0);
/* 1977 */     g2.setStroke(normalStroke);
/* 1978 */     RenderingHints rh = g2.getRenderingHints();
/* 1979 */     rh.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
/* 1980 */     rh.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
/* 1981 */     g2.setRenderingHints(rh);
/*      */     
/*      */     int j;
/* 1984 */     for (j = 0; j < Grid.ySz; j++) {
/* 1985 */       for (int i = 0; i < Grid.xSz; i++) {
/* 1986 */         if (Grid.mode[i][j] != 2) {
/* 1987 */           int k; if (Def.dispWithColor.booleanValue() && !Def.displaySubmission.booleanValue()) {
/* 1988 */             if (Grid.curColor[i][j] != 16777215 && Def.dispCursor.booleanValue()) {
/* 1989 */               k = Grid.curColor[i][j];
/* 1990 */             } else if (Grid.color[i][j] != 16777215) {
/* 1991 */               k = Grid.color[i][j];
/*      */             } else {
/* 1993 */               k = Op.getColorInt(Op.CW.CwCellC.ordinal(), Op.cw);
/*      */             } 
/*      */           } else {
/* 1996 */             k = 16777215;
/* 1997 */           }  if (Grid.mode[i][j] == 2)
/* 1998 */             k = 16777215; 
/* 1999 */           g2.setColor(new Color(k));
/* 2000 */           int drawI = Grid.RorL(i);
/* 2001 */           g2.fillRect(Grid.xOrg + drawI * Grid.xCell, Grid.yOrg + j * Grid.yCell, Grid.xCell, Grid.yCell);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 2006 */     if (Def.dispWithColor.booleanValue()) {
/* 2007 */       Grid.drawPatternCells(g2, new Color(Op.getColorInt(Op.CW.CwPatternC.ordinal(), Op.cw)), new Color(Op.getColorInt(Op.CW.CwCellC.ordinal(), Op.cw)), false);
/*      */     } else {
/* 2009 */       Grid.drawPatternCells(g2, Def.COLOR_BLACK, Def.COLOR_WHITE, false);
/*      */     } 
/*      */     
/* 2012 */     g2.setStroke(normalStroke);
/* 2013 */     g2.setColor(Def.dispWithColor.booleanValue() ? new Color(Op.getColorInt(Op.CW.CwGridC.ordinal(), Op.cw)) : Def.COLOR_BLACK);
/* 2014 */     for (j = 0; j < Grid.ySz; j++) {
/* 2015 */       for (int i = 0; i < Grid.xSz; i++) {
/* 2016 */         if (Grid.mode[i][j] != 2) {
/* 2017 */           int drawI = Grid.RorL(i);
/* 2018 */           g2.drawRect(Grid.xOrg + drawI * Grid.xCell, Grid.yOrg + j * Grid.yCell, Grid.xCell, Grid.yCell);
/*      */         } 
/*      */       } 
/* 2021 */     }  if (!Def.displaySubmissionPuz.booleanValue()) {
/* 2022 */       g2.setFont(new Font(Op.cw[Op.CW.CwFont.ordinal()], 0, 8 * Grid.yCell / 10));
/* 2023 */       g2.setColor(Def.dispWithColor.booleanValue() ? new Color(Op.getColorInt(Op.CW.CwLettersC.ordinal(), Op.cw)) : Def.COLOR_BLACK);
/* 2024 */       FontMetrics fm = g2.getFontMetrics();
/* 2025 */       for (j = 0; j < Grid.ySz; j++) {
/* 2026 */         for (int i = 0; i < Grid.xSz; i++) {
/* 2027 */           char ch = (char)(Def.dispSolArray.booleanValue() ? Grid.sol[i][j] : Grid.letter[i][j]);
/* 2028 */           if (ch != '\000') {
/* 2029 */             int w = fm.stringWidth("" + ch);
/* 2030 */             int drawI = Grid.RorL(i);
/* 2031 */             g2.drawString("" + ch, Grid.xOrg + drawI * Grid.xCell + (Grid.xCell - w) / 2, Grid.yOrg + j * Grid.yCell + 9 * Grid.yCell / 10);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 2038 */     g2.setStroke(wideStroke);
/* 2039 */     Grid.drawBars(g2);
/*      */     
/* 2041 */     Grid.drawOutline(g2, true);
/*      */ 
/*      */     
/* 2044 */     if (Def.dispCursor.booleanValue() && Def.building != 1 && !Def.displaySubmission.booleanValue()) {
/* 2045 */       g2.setStroke(wideStroke);
/* 2046 */       g2.setColor(Def.COLOR_RED);
/* 2047 */       g2.drawRect(Grid.xOrg + Grid.RorL(Grid.xCur) * Grid.xCell, Grid.yOrg + Grid.yCur * Grid.yCell, Grid.xCell, Grid.yCell);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2053 */     int[] wl = new int[10];
/* 2054 */     if (Op.getBool(Op.CW.CwCrosswordSep.ordinal(), Op.cw).booleanValue()) {
/* 2055 */       for (int i = 0; i < NodeList.nodeListLength; i++) {
/* 2056 */         j = (NodeList.nodeList[i]).clue.lastIndexOf('(');
/* 2057 */         if (j != -1) {
/* 2058 */           String str = (NodeList.nodeList[i]).clue.substring(j + 1, (NodeList.nodeList[i]).clue.length() - 1);
/* 2059 */           for (j = 0; j < 10; j++)
/* 2060 */             wl[j] = 0; 
/* 2061 */           for (int k = 0; k < str.length(); k++) {
/* 2062 */             int v = Character.getNumericValue(str.charAt(k));
/* 2063 */             if (v >= 0 && v <= 9) {
/* 2064 */               wl[j] = wl[j] * 10 + v;
/*      */             } else {
/* 2066 */               if (j > 0)
/* 2067 */                 wl[j] = wl[j] + wl[j - 1]; 
/* 2068 */               j++;
/*      */             } 
/*      */           } 
/* 2071 */           for (j = 0; j < 10 && 
/* 2072 */             wl[j + 1] != 0; j++) {
/*      */             
/* 2074 */             int x = ((NodeList.nodeList[i]).cellLoc[wl[j] - 1]).x;
/* 2075 */             int y = ((NodeList.nodeList[i]).cellLoc[wl[j] - 1]).y;
/* 2076 */             int right = Grid.xOrg + (x + 1) * Grid.xCell;
/* 2077 */             int bottom = Grid.yOrg + (y + 1) * Grid.yCell;
/* 2078 */             if (((NodeList.nodeList[i]).cellLoc[wl[j]]).y == y) {
/* 2079 */               g2.drawLine(right, bottom - Grid.yCell, right, bottom);
/*      */             } else {
/* 2081 */               g2.drawLine(right - Grid.xCell, bottom, right, bottom);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/* 2087 */     if (Def.dispGuideDigits.booleanValue() || Op.getBool(Op.CW.CwIdInSol.ordinal(), Op.cw).booleanValue()) {
/* 2088 */       g2.setFont(new Font(Op.cw[Op.CW.CwIDFont.ordinal()], 0, Grid.yCell / 3));
/* 2089 */       g2.setColor(Def.dispWithColor.booleanValue() ? new Color(Op.getColorInt(Op.CW.CwIDC.ordinal(), Op.cw)) : Def.COLOR_BLACK);
/* 2090 */       FontMetrics fm = g2.getFontMetrics();
/* 2091 */       for (int i = 0; NodeList.nodeList[i] != null; i++) {
/* 2092 */         int drawI = Grid.RorL((NodeList.nodeList[i]).x);
/* 2093 */         int xCoord = Grid.xOrg + drawI * Grid.xCell + ((DictionaryMtce.dicHeader[DictionaryMtce.R2L_WORD] == 1) ? (Grid.xCell - fm.stringWidth("" + (NodeList.nodeList[i]).id) - 1) : ((Grid.xCell / 20 > 1) ? (Grid.xCell / 20) : 1));
/*      */         
/* 2095 */         g2.drawString("" + (NodeList.nodeList[i]).id, xCoord, Grid.yOrg + (NodeList.nodeList[i]).y * Grid.yCell + fm.getAscent());
/*      */       } 
/*      */     } 
/* 2098 */     g2.setStroke(new BasicStroke(1.0F));
/*      */   }
/*      */   
/*      */   static void printPuz(Graphics2D g2, int left, int top, int width, int height) {
/* 2102 */     loadCrossword(Op.cw[Op.CW.CwPuz.ordinal()]);
/* 2103 */     setSizesAndOffsets(left, top, width, height, 0, true);
/* 2104 */     Methods.clearGrid(Grid.sol);
/* 2105 */     Def.dispGuideDigits = Boolean.valueOf(true);
/* 2106 */     Def.dispWithColor = Op.getBool(Op.CW.CwPuzC.ordinal(), Op.cw);
/* 2107 */     CrosswordSolve.drawCrossword(g2);
/* 2108 */     Def.dispWithColor = Boolean.valueOf(true);
/*      */   }
/*      */   
/*      */   static void printSol(Graphics2D g2, int left, int top, int width, int height, String solutionPuzzle) {
/* 2112 */     loadCrossword(solutionPuzzle);
/* 2113 */     setSizesAndOffsets(left, top, width, height, 0, true);
/* 2114 */     Def.dispGuideDigits = Boolean.valueOf(false);
/* 2115 */     Def.dispWithColor = Op.getBool(Op.CW.CwSolC.ordinal(), Op.cw);
/* 2116 */     drawCrossword(g2);
/* 2117 */     Def.dispWithColor = Boolean.valueOf(true);
/* 2118 */     Def.dispGuideDigits = Boolean.valueOf(true);
/* 2119 */     loadCrossword(Op.cw[Op.CW.CwPuz.ordinal()]);
/*      */   }
/*      */   
/*      */   static void printSolTitle(Graphics2D g2, int left, int top, int width, int height, String solutionPuzzle) {
/* 2123 */     loadCrossword(solutionPuzzle);
/* 2124 */     Print.outputTextItem(g2, left, top, width, height, "SansSerif", 0, Methods.puzzleTitle);
/* 2125 */     loadCrossword(Op.cw[Op.CW.CwPuz.ordinal()]);
/*      */   }
/*      */   
/*      */   static void lockoutWords() {
/* 2129 */     String word = "", clue = "";
/*      */ 
/*      */     
/* 2132 */     int wordDat = 0;
/* 2133 */     boolean read = true;
/*      */ 
/*      */     
/* 2136 */     NodeList.sortNodeList(0);
/*      */     try {
/* 2138 */       DataInputStream oldDic = new DataInputStream(new FileInputStream(Op.cw[Op.CW.CwDic.ordinal()] + ".dic/xword.dic"));
/* 2139 */       DataOutputStream newDic = new DataOutputStream(new FileOutputStream(Op.cw[Op.CW.CwDic.ordinal()] + ".dic/xword.new")); int i;
/* 2140 */       for (i = 0; i < 128; i++)
/* 2141 */         newDic.writeByte(oldDic.readByte()); 
/* 2142 */       for (i = 0; oldDic.available() > 2; ) {
/* 2143 */         if (read) {
/* 2144 */           wordDat = oldDic.readInt();
/* 2145 */           word = oldDic.readUTF();
/* 2146 */           clue = oldDic.readUTF();
/*      */         } 
/* 2148 */         read = true;
/* 2149 */         int comp = word.compareToIgnoreCase((NodeList.nodeList[i]).word);
/* 2150 */         if (comp == 0) {
/* 2151 */           int loCount = wordDat % 256; int clueCount;
/* 2152 */           for (int j = 0; j < clue.length(); j++) {
/* 2153 */             if (clue.charAt(j) == '*')
/* 2154 */               clueCount++; 
/* 2155 */           }  if (loCount != 255 && 
/* 2156 */             ++loCount == clueCount)
/* 2157 */             loCount = 255; 
/* 2158 */           wordDat = wordDat & 0xFFFFFF00 | loCount;
/* 2159 */           newDic.writeInt(wordDat);
/* 2160 */           newDic.writeUTF(word);
/* 2161 */           newDic.writeUTF(clue);
/*      */           
/* 2163 */           if (i < NodeList.nodeListLength - 1)
/* 2164 */             i++;  continue;
/*      */         } 
/* 2166 */         if (comp < 0) {
/* 2167 */           newDic.writeInt(wordDat);
/* 2168 */           newDic.writeUTF(word);
/* 2169 */           newDic.writeUTF(clue); continue;
/*      */         } 
/* 2171 */         if (comp > 0) {
/* 2172 */           if (i < NodeList.nodeListLength - 1) {
/* 2173 */             i++;
/* 2174 */             read = false;
/*      */             continue;
/*      */           } 
/* 2177 */           newDic.writeInt(wordDat);
/* 2178 */           newDic.writeUTF(word);
/* 2179 */           newDic.writeUTF(clue);
/*      */         } 
/*      */       } 
/*      */       
/* 2183 */       newDic.close();
/* 2184 */       oldDic.close();
/*      */     } catch (IOException exc) {
/* 2186 */       System.out.println("Error");
/* 2187 */     }  File fl = new File(Op.cw[Op.CW.CwDic.ordinal()] + ".dic/xword.dic");
/* 2188 */     fl.delete();
/* 2189 */     fl = new File(Op.cw[Op.CW.CwDic.ordinal()] + ".dic/xword.new");
/* 2190 */     fl.renameTo(new File(Op.cw[Op.CW.CwDic.ordinal()] + ".dic/xword.dic"));
/* 2191 */     NodeList.sortNodeList(2);
/*      */   }
/*      */   
/*      */   void unlockWords(int puzNum) {
/* 2195 */     String word = "", clue = "";
/*      */ 
/*      */     
/* 2198 */     int wordDat = 0;
/* 2199 */     boolean read = true;
/*      */ 
/*      */     
/* 2202 */     loadCrossword("" + puzNum + ".crossword");
/* 2203 */     NodeList.sortNodeList(0);
/*      */     
/* 2205 */     try { DataInputStream oldDic = new DataInputStream(new FileInputStream(Op.cw[Op.CW.CwDic.ordinal()] + ".dic/xword.dic"));
/* 2206 */       DataOutputStream newDic = new DataOutputStream(new FileOutputStream(Op.cw[Op.CW.CwDic.ordinal()] + ".dic/xword.new")); int i;
/* 2207 */       for (i = 0; i < 128; i++)
/* 2208 */         newDic.writeByte(oldDic.readByte()); 
/* 2209 */       for (i = 0; oldDic.available() > 2; ) {
/* 2210 */         if (read) {
/* 2211 */           wordDat = oldDic.readInt();
/* 2212 */           word = oldDic.readUTF();
/* 2213 */           clue = oldDic.readUTF();
/*      */         } 
/* 2215 */         read = true;
/* 2216 */         int comp = word.compareToIgnoreCase((NodeList.nodeList[i]).word);
/* 2217 */         if (comp == 0) {
/* 2218 */           int loCount = wordDat % 256;
/* 2219 */           if (loCount == 255)
/* 2220 */             wordDat &= 0xFFFFFF00; 
/* 2221 */           newDic.writeInt(wordDat);
/* 2222 */           newDic.writeUTF(word);
/* 2223 */           newDic.writeUTF(clue);
/* 2224 */           if (i < NodeList.nodeListLength - 1) {
/* 2225 */             i++; continue;
/*      */           } 
/* 2227 */           while (oldDic.available() > 2) {
/* 2228 */             wordDat = oldDic.readInt();
/* 2229 */             word = oldDic.readUTF();
/* 2230 */             clue = oldDic.readUTF();
/* 2231 */             newDic.writeInt(wordDat);
/* 2232 */             newDic.writeUTF(word);
/* 2233 */             newDic.writeUTF(clue);
/*      */           } 
/*      */           continue;
/*      */         } 
/* 2237 */         if (comp < 0) {
/* 2238 */           newDic.writeInt(wordDat);
/* 2239 */           newDic.writeUTF(word);
/* 2240 */           newDic.writeUTF(clue); continue;
/*      */         } 
/* 2242 */         if (comp > 0 && 
/* 2243 */           i < NodeList.nodeListLength - 1) {
/* 2244 */           i++;
/* 2245 */           read = false;
/*      */         } 
/*      */       } 
/*      */       
/* 2249 */       newDic.close();
/* 2250 */       oldDic.close(); }
/* 2251 */     catch (IOException exc) { System.out.println("Error"); }
/*      */     
/* 2253 */     File fl = new File(Op.cw[Op.CW.CwDic.ordinal()] + ".dic/xword.dic");
/* 2254 */     fl.delete();
/* 2255 */     fl = new File(Op.cw[Op.CW.CwDic.ordinal()] + ".dic/xword.new");
/* 2256 */     fl.renameTo(new File(Op.cw[Op.CW.CwDic.ordinal()] + ".dic/xword.dic"));
/* 2257 */     loadCrossword("" + puzNum + ".crossword");
/*      */   }
/*      */ 
/*      */   
/*      */   void reviewClueControl(boolean user) {
/* 2262 */     if (!Methods.havePuzzle || (!reviewMode && !user)) {
/*      */       return;
/*      */     }
/*      */     
/* 2266 */     reviewMode = !reviewMode;
/* 2267 */     this.jbReview.setText(reviewMode ? "<html><center>End<br><u>R</u>eview" : "<html><center><u>R</u>eview<br>Clues");
/* 2268 */     clueText = (NodeList.nodeList[Grid.nCur]).clue;
/* 2269 */     jpReviewClue.repaint();
/* 2270 */     focusColor(true);
/* 2271 */     Def.dispCursor = Boolean.valueOf(true);
/* 2272 */     restoreFrame();
/*      */   }
/*      */ 
/*      */   
/*      */   public void actionPerformed(ActionEvent e) {
/* 2277 */     int oldX = Grid.xCur, oldY = Grid.yCur;
/*      */     
/* 2279 */     if (Grid.letter[Grid.xCur][Grid.yCur] != 0) {
/* 2280 */       JOptionPane.showMessageDialog(jfCrossword, "Changing a cell type is not permitted if the cell contains a letter.");
/*      */       
/*      */       return;
/*      */     } 
/* 2284 */     for (int i = 0; i < 15; i++) {
/* 2285 */       if (e.getActionCommand().equals("" + i)) {
/* 2286 */         int previous = Grid.mode[Grid.xCur][Grid.yCur];
/* 2287 */         Grid.mode[Grid.xCur][Grid.yCur] = i;
/* 2288 */         if (GridMaintenance.validCellType(Grid.xCur, Grid.yCur)) {
/* 2289 */           if (!GridMaintenance.symmetryUpdate(Grid.xCur, Grid.yCur, previous, Grid.mode[Grid.xCur][Grid.yCur])) {
/* 2290 */             JOptionPane.showMessageDialog(jfCrossword, "A symmetrical cell already contains a letter.\nThe process cannot be completed.");
/* 2291 */             Grid.mode[Grid.xCur][Grid.yCur] = previous;
/*      */           } 
/*      */         } else {
/*      */           
/* 2295 */           JOptionPane.showMessageDialog(jfCrossword, "The cell type you have selected is not valid for\nthis combination of location and symmetry.");
/* 2296 */           Grid.mode[Grid.xCur][Grid.yCur] = previous;
/*      */           return;
/*      */         } 
/* 2299 */         NodeList.buildNodeList();
/* 2300 */         Grid.xCur = oldX; Grid.yCur = oldY;
/* 2301 */         for (int y = 0; y < Grid.ySz; y++) {
/* 2302 */           for (int x = 0; x < Grid.xSz; x++)
/* 2303 */             Grid.curColor[x][y] = 16777215; 
/* 2304 */         }  pp.repaint();
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   static void rebuildNodeList() {
/* 2310 */     int oldX = Grid.xCur, oldY = Grid.yCur;
/* 2311 */     int oldNodeListLength = NodeList.nodeListLength;
/* 2312 */     Node[] saveNodeList = new Node[800]; int i;
/* 2313 */     for (i = 0; i < oldNodeListLength; i++)
/* 2314 */       saveNodeList[i] = NodeList.nodeList[i]; 
/* 2315 */     NodeList.buildNodeList();
/* 2316 */     for (i = 0; i < oldNodeListLength; i++) {
/* 2317 */       for (int j = 0; j < NodeList.nodeListLength; j++) {
/* 2318 */         if ((saveNodeList[i]).word.equalsIgnoreCase((NodeList.nodeList[j]).word) && (saveNodeList[i]).id == (NodeList.nodeList[j]).id)
/*      */         {
/* 2320 */           (NodeList.nodeList[j]).clue = (saveNodeList[i]).clue; } 
/*      */       } 
/* 2322 */     }  focusColor(false);
/* 2323 */     Grid.xNew = oldX;
/* 2324 */     Grid.yNew = oldY;
/* 2325 */     changeCursor();
/*      */   }
/*      */   
/*      */   static void makeGrid() {
/* 2329 */     Methods.havePuzzle = false;
/* 2330 */     Grid.clearGrid();
/* 2331 */     Grid.loadGrid(Op.cw[Op.CW.CwGrid.ordinal()]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void suggest() {
/* 2339 */     if ((NodeList.CELL_MODE[Grid.mode[Grid.xCur][Grid.yCur]] & 0x10) == 0)
/*      */       return; 
/* 2341 */     JDialog jdlgSuggest = new JDialog(jfCrossword, "Suggest a Word", true);
/* 2342 */     jdlgSuggest.setSize(280, 430);
/* 2343 */     jdlgSuggest.setResizable(false);
/* 2344 */     jdlgSuggest.setLayout((LayoutManager)null);
/* 2345 */     jdlgSuggest.setLocation(jfCrossword.getX(), jfCrossword.getY());
/*      */     
/* 2347 */     JLabel jl1 = new JLabel("The Suggested words are:");
/* 2348 */     jl1.setForeground(Def.COLOR_LABEL);
/* 2349 */     jl1.setSize(200, 20);
/* 2350 */     jl1.setLocation(10, 10);
/* 2351 */     jl1.setHorizontalAlignment(2);
/* 2352 */     jdlgSuggest.add(jl1);
/*      */     
/* 2354 */     DefaultListModel<String> lmMatch = new DefaultListModel<>();
/* 2355 */     JList<String> jl = new JList<>(lmMatch);
/* 2356 */     JScrollPane jsp = new JScrollPane(jl);
/* 2357 */     jsp.setSize(255, 272);
/* 2358 */     jsp.setLocation(10, 31);
/* 2359 */     jdlgSuggest.add(jsp);
/*      */     
/* 2361 */     JLabel jlCount = new JLabel("");
/* 2362 */     jlCount.setForeground(Def.COLOR_LABEL);
/* 2363 */     jlCount.setSize(200, 20);
/* 2364 */     jlCount.setLocation(10, 305);
/* 2365 */     jlCount.setHorizontalAlignment(2);
/* 2366 */     jdlgSuggest.add(jlCount);
/*      */     
/* 2368 */     NodeList.buildTemplate(Grid.nCur);
/* 2369 */     WordTools.findMatchingWords(lmMatch, (NodeList.nodeList[Grid.nCur]).template);
/* 2370 */     jlCount.setText(lmMatch.size() + " Item(s) in list.");
/*      */     
/* 2372 */     jdlgSuggest
/* 2373 */       .addWindowListener(new WindowAdapter() {
/*      */           public void windowClosing(WindowEvent we) {
/* 2375 */             Methods.closeHelp();
/*      */           }
/*      */         });
/*      */     
/* 2379 */     Methods.closeHelp();
/*      */     
/* 2381 */     JButton jbOK = Methods.cweButton("OK", 10, 330, 80, 26, null);
/* 2382 */     jbOK.addActionListener(e -> {
/*      */           if (paramJList.getSelectedIndex() != -1) {
/*      */             String s = ((String)paramJList.getSelectedValue()).trim();
/*      */             int i;
/*      */             for (i = 0; i < (NodeList.nodeList[Grid.nCur]).length; i++) {
/*      */               Grid.letter[((NodeList.nodeList[Grid.nCur]).cellLoc[i]).x][((NodeList.nodeList[Grid.nCur]).cellLoc[i]).y] = s.charAt(i);
/*      */             }
/*      */             (NodeList.nodeList[Grid.nCur]).word = s;
/*      */             try {
/*      */               DataInputStream dataIn = new DataInputStream(new FileInputStream(Op.cw[Op.CW.CwDic.ordinal()] + ".dic/xword.dic"));
/*      */               for (i = 0; i < 128; i++)
/*      */                 dataIn.readByte(); 
/*      */               while (dataIn.available() > 2) {
/*      */                 dataIn.readInt();
/*      */                 String s1 = dataIn.readUTF();
/*      */                 String s2 = dataIn.readUTF();
/*      */                 if (s1.equalsIgnoreCase((NodeList.nodeList[Grid.nCur]).word)) {
/*      */                   (NodeList.nodeList[Grid.nCur]).clue = s2;
/*      */                   break;
/*      */                 } 
/*      */               } 
/*      */               dataIn.close();
/* 2404 */             } catch (IOException exc) {
/*      */               System.out.println("Error");
/*      */             } 
/*      */           }  paramJDialog.dispose(); Methods.closeHelp();
/*      */         });
/* 2409 */     jdlgSuggest.add(jbOK);
/*      */     
/* 2411 */     JButton jbCancel = Methods.cweButton("Cancel", 10, 365, 80, 26, null);
/* 2412 */     jbCancel.addActionListener(e -> {
/*      */           paramJDialog.dispose();
/*      */           Methods.closeHelp();
/*      */         });
/* 2416 */     jdlgSuggest.add(jbCancel);
/*      */     
/* 2418 */     JButton jbHelp = Methods.cweButton("<html><font size=6 color=BB0000 face=Serif>Help ", 105, 330, 160, 61, new ImageIcon("graphics/help.png"));
/* 2419 */     jbHelp.addActionListener(e -> Methods.cweHelp(null, paramJDialog, "Suggest a Word", suggestHelp));
/*      */ 
/*      */     
/* 2422 */     jdlgSuggest.add(jbHelp);
/*      */     
/* 2424 */     jdlgSuggest.getRootPane().setDefaultButton(jbOK);
/* 2425 */     Methods.setDialogSize(jdlgSuggest, 275, 401);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void addAClue(JFrame jf) {
/* 2433 */     ArrayList<String> al = new ArrayList<>();
/* 2434 */     ArrayList<String> alSubject = new ArrayList<>();
/* 2435 */     ArrayList<String> alAnagram = new ArrayList<>();
/* 2436 */     if (Grid.nCur == -1)
/*      */       return; 
/* 2438 */     if (Def.puzzleMode == 10) {
/* 2439 */       Grid.nCur = Grid.yCurW;
/*      */     } else {
/* 2441 */       int memNode = Grid.nCur;
/* 2442 */       rebuildNodeList();
/* 2443 */       focusColor(false);
/* 2444 */       Grid.nCur = memNode;
/* 2445 */       focusColor(true);
/* 2446 */       (NodeList.nodeList[Grid.nCur]).word = (NodeList.nodeList[Grid.nCur]).template;
/*      */     } 
/* 2448 */     JDialog jdlgAddClue = new JDialog(jf, "Add a Clue", true);
/* 2449 */     jdlgAddClue.setSize(400, 537);
/* 2450 */     jdlgAddClue.setResizable(false);
/* 2451 */     jdlgAddClue.setLayout((LayoutManager)null);
/* 2452 */     jdlgAddClue.setLocation(jf.getX(), jf.getY());
/*      */     
/* 2454 */     JLabel jl1 = new JLabel("WORD");
/* 2455 */     jl1.setForeground(Def.COLOR_LABEL);
/* 2456 */     jl1.setSize(90, 20);
/* 2457 */     jl1.setLocation(10, 5);
/* 2458 */     jl1.setHorizontalAlignment(2);
/* 2459 */     jdlgAddClue.add(jl1);
/*      */     
/* 2461 */     JLabel jl2 = new JLabel((NodeList.nodeList[Grid.nCur]).word);
/* 2462 */     jl2.setForeground(Def.COLOR_BLACK);
/* 2463 */     jl2.setSize(200, 20);
/* 2464 */     jl2.setLocation(60, 5);
/* 2465 */     jl2.setHorizontalAlignment(2);
/* 2466 */     jdlgAddClue.add(jl2);
/*      */     
/* 2468 */     JLabel jlTheClue = new JLabel("<html>C<u>L</u>UE");
/* 2469 */     jlTheClue.setForeground(Def.COLOR_LABEL);
/* 2470 */     jlTheClue.setSize(60, 20);
/* 2471 */     jlTheClue.setLocation(10, 20);
/* 2472 */     jlTheClue.setHorizontalAlignment(2);
/* 2473 */     jdlgAddClue.add(jlTheClue);
/*      */     
/* 2475 */     JTextArea jtaTheClue = new JTextArea((NodeList.nodeList[Grid.nCur]).clue);
/* 2476 */     jtaTheClue.setLineWrap(true);
/* 2477 */     jtaTheClue.setWrapStyleWord(true);
/* 2478 */     JScrollPane jsp = new JScrollPane(jtaTheClue);
/* 2479 */     jsp.setSize(375, 150);
/* 2480 */     jsp.setLocation(10, 40);
/* 2481 */     jtaTheClue.selectAll();
/* 2482 */     jdlgAddClue.add(jsp);
/*      */     
/* 2484 */     jtaTheClue.setFont(new Font("SansSerif", 1, 13));
/* 2485 */     JLabel jlList = new JLabel("Suggestions:");
/* 2486 */     jlList.setForeground(Def.COLOR_LABEL);
/* 2487 */     jlList.setSize(200, 20);
/* 2488 */     jlList.setLocation(170, 190);
/* 2489 */     jlList.setHorizontalAlignment(2);
/* 2490 */     jdlgAddClue.add(jlList);
/*      */     
/* 2492 */     DefaultListModel<String> lmMatch = new DefaultListModel<>();
/* 2493 */     JList<String> jl = new JList<>(lmMatch);
/* 2494 */     jsp = new JScrollPane(jl);
/* 2495 */     jsp.setSize(215, 276);
/* 2496 */     jsp.setLocation(170, 210);
/* 2497 */     jdlgAddClue.add(jsp);
/*      */     
/* 2499 */     JLabel jlShort = new JLabel("<html><u>S</u>hortest word to<br>include in the list");
/* 2500 */     jlShort.setForeground(Def.COLOR_LABEL);
/* 2501 */     jlShort.setSize(200, 35);
/* 2502 */     jlShort.setLocation(10, 300);
/* 2503 */     jlShort.setHorizontalAlignment(2);
/* 2504 */     jdlgAddClue.add(jlShort);
/*      */     
/* 2506 */     JComboBox<Integer> jcbbLen = new JComboBox<>();
/* 2507 */     for (int i = 1; i <= 15; i++)
/* 2508 */       jcbbLen.addItem(Integer.valueOf(i)); 
/* 2509 */     jcbbLen.setSize(50, 21);
/* 2510 */     jcbbLen.setLocation(10, 335);
/* 2511 */     jdlgAddClue.add(jcbbLen);
/* 2512 */     jcbbLen.setBackground(Def.COLOR_BUTTONBG);
/* 2513 */     JLabel jlCount = new JLabel("");
/* 2514 */     jlCount.setSize(200, 20);
/* 2515 */     jlCount.setLocation(170, 483);
/* 2516 */     jlCount.setHorizontalAlignment(2);
/* 2517 */     jdlgAddClue.add(jlCount);
/*      */     
/* 2519 */     jdlgAddClue
/* 2520 */       .addWindowListener(new WindowAdapter() {
/*      */           public void windowClosing(WindowEvent we) {
/* 2522 */             Methods.closeHelp();
/*      */           }
/*      */         });
/*      */     
/* 2526 */     Methods.closeHelp();
/*      */     
/* 2528 */     JButton jbContainer = Methods.cweButton("Container Words", 10, 200, 150, 26, null);
/* 2529 */     jbContainer.addActionListener(e -> {
/*      */           WordTools.findContainerWords(paramArrayList, (NodeList.nodeList[Grid.nCur]).word, true);
/*      */           paramDefaultListModel.clear();
/*      */           paramArrayList.stream().forEach(());
/*      */           paramJLabel1.setText(paramDefaultListModel.size() + " Item(s) in list.");
/*      */           paramJLabel1.setForeground(Def.COLOR_LABEL);
/*      */           paramJLabel2.setText("Container Words");
/*      */         });
/* 2537 */     jdlgAddClue.add(jbContainer);
/*      */     
/* 2539 */     JButton jbContained = Methods.cweButton("Contained Words", 10, 235, 150, 26, null);
/* 2540 */     jbContained.addActionListener(e -> {
/*      */           WordTools.findContainedWords(paramArrayList, (NodeList.nodeList[Grid.nCur]).word, false, true, paramJComboBox.getSelectedIndex() + 1);
/*      */           
/*      */           paramDefaultListModel.clear();
/*      */           
/*      */           paramArrayList.stream().forEach(());
/*      */           
/*      */           paramJLabel1.setText(paramDefaultListModel.size() + " Item(s) in list.");
/*      */           
/*      */           paramJLabel1.setForeground(Def.COLOR_LABEL);
/*      */           paramJLabel2.setText("Contained Words");
/*      */         });
/* 2552 */     jdlgAddClue.add(jbContained);
/*      */     
/* 2554 */     JButton jbAnagram = Methods.cweButton("Anagrams", 10, 270, 150, 26, null);
/* 2555 */     jbAnagram.addActionListener(e -> {
/*      */           StringBuffer sbSubject = new StringBuffer((NodeList.nodeList[Grid.nCur]).word); for (int i = 0; i < sbSubject.length(); i++) {
/*      */             if (sbSubject.charAt(i) == ' ')
/*      */               sbSubject.deleteCharAt(i--); 
/*      */           }  paramArrayList1.clear();
/*      */           WordTools.findContainedWords(paramArrayList1, sbSubject.toString(), false, false, paramJComboBox.getSelectedIndex() + 1);
/*      */           if (paramJComboBox.getSelectedIndex() == 0) {
/*      */             if (sbSubject.toString().contains("I"))
/*      */               paramArrayList1.add(0, "I"); 
/*      */             if (sbSubject.toString().contains("A"))
/*      */               paramArrayList1.add(0, "A"); 
/*      */           } 
/*      */           StringBuffer sbAnagram = new StringBuffer();
/*      */           paramArrayList2.clear();
/*      */           WordTools.anagramRecursion(paramArrayList1, paramArrayList2, sbSubject, sbAnagram, 0, paramArrayList1.size());
/*      */           paramDefaultListModel.clear();
/*      */           paramArrayList2.stream().forEach(());
/*      */           paramJLabel1.setText(paramDefaultListModel.size() + " Item(s) in list.");
/*      */           paramJLabel1.setForeground(Def.COLOR_LABEL);
/*      */           paramJLabel2.setText("Anagrams");
/*      */         });
/* 2576 */     jdlgAddClue.add(jbAnagram);
/*      */     
/* 2578 */     JButton jbOK = Methods.cweButton("OK", 10, 365, 150, 26, null);
/* 2579 */     jbOK.addActionListener(e -> {
/*      */           Methods.clickedOK = true;
/*      */           (NodeList.nodeList[Grid.nCur]).clue = paramJTextArea.getText();
/*      */           paramJDialog.dispose();
/*      */           Methods.closeHelp();
/*      */           saveCrossword(Op.cw[Op.CW.CwPuz.ordinal()]);
/*      */         });
/* 2586 */     jdlgAddClue.add(jbOK);
/*      */     
/* 2588 */     JButton jbCancel = Methods.cweButton("Cancel", 10, 400, 150, 26, null);
/* 2589 */     jbCancel.addActionListener(e -> {
/*      */           Methods.clickedOK = false;
/*      */           paramJDialog.dispose();
/*      */           Methods.closeHelp();
/*      */         });
/* 2594 */     jdlgAddClue.add(jbCancel);
/*      */     
/* 2596 */     JButton jbHelp = Methods.cweButton("<html><font size=6 color=BB0000 face=Serif>Help ", 10, 435, 150, 61, new ImageIcon("graphics/help.png"));
/* 2597 */     jbHelp.addActionListener(e -> Methods.cweHelp(null, paramJDialog, "Attaching Clues to a Puzzle", addCluesHelp));
/*      */     
/* 2599 */     jdlgAddClue.add(jbHelp);
/*      */     
/* 2601 */     jdlgAddClue.getRootPane().setDefaultButton(jbOK);
/* 2602 */     Methods.setDialogSize(jdlgAddClue, 395, 506);
/*      */   }
/*      */   
/*      */   static void focusColor(boolean set) {
/* 2606 */     for (int i = 0; i < (NodeList.nodeList[Grid.nCur]).length; i++) {
/* 2607 */       Grid.curColor[((NodeList.nodeList[Grid.nCur]).cellLoc[i]).x][((NodeList.nodeList[Grid.nCur]).cellLoc[i]).y] = set ? 61132 : 16777215;
/*      */     }
/*      */   }
/*      */   
/*      */   static void changeCursor() {
/* 2612 */     if (Grid.nCur != -1) {
/* 2613 */       focusColor(false);
/*      */     }
/* 2615 */     int targetNodeA = Grid.horz[Grid.xNew][Grid.yNew];
/* 2616 */     int targetNodeD = Grid.vert[Grid.xNew][Grid.yNew];
/* 2617 */     if (Grid.xCur == Grid.xNew && Grid.yCur == Grid.yNew) {
/* 2618 */       if (targetNodeA != -1 && targetNodeD != -1) {
/* 2619 */         Grid.nCur = (Grid.nCur == targetNodeA) ? targetNodeD : targetNodeA;
/*      */       }
/*      */     } else {
/* 2622 */       if ((targetNodeA != Grid.nCur && targetNodeD != Grid.nCur) || Grid.nCur == -1)
/*      */       {
/*      */         
/* 2625 */         if (Grid.xCur == Grid.xNew) {
/* 2626 */           Grid.nCur = (targetNodeD != -1) ? targetNodeD : targetNodeA;
/*      */         } else {
/* 2628 */           Grid.nCur = (targetNodeA != -1) ? targetNodeA : targetNodeD;
/*      */         }  } 
/* 2630 */       Grid.xCur = Grid.xNew;
/* 2631 */       Grid.yCur = Grid.yNew;
/*      */     } 
/* 2633 */     if (Grid.nCur != -1)
/* 2634 */       focusColor(true); 
/* 2635 */     if (reviewMode) {
/* 2636 */       clueText = (Grid.nCur != -1) ? (NodeList.nodeList[Grid.nCur]).clue : " ";
/* 2637 */       jpReviewClue.repaint();
/*      */     } 
/*      */   }
/*      */   
/*      */   void insertWord(int index) {
/* 2642 */     Node thisNode = NodeList.nodeList[index];
/* 2643 */     thisNode.pending = false;
/* 2644 */     if ((NodeList.nodeList[index]).preset) {
/*      */       return;
/*      */     }
/* 2647 */     for (int i = 0; i < thisNode.length; i++) {
/* 2648 */       char ch = thisNode.word.charAt(i);
/* 2649 */       int x = (thisNode.cellLoc[i]).x;
/* 2650 */       int y = (thisNode.cellLoc[i]).y;
/* 2651 */       Grid.control[x][y] = (byte)(Grid.control[x][y] + 1); if ((byte)(Grid.control[x][y] + 1) == 1)
/* 2652 */         Grid.letter[x][y] = ch; 
/*      */     } 
/* 2654 */     if (thisNode.mode == 0) {
/* 2655 */       busy[thisNode.source][thisNode.length][thisNode.wordIndex] = true;
/*      */     } else {
/* 2657 */       busy[thisNode.source][thisNode.length][revLink[thisNode.source][thisNode.length][thisNode.wordIndex]] = true;
/*      */     } 
/*      */   }
/*      */   void extractWord(int index) {
/* 2661 */     Node thisNode = NodeList.nodeList[index];
/* 2662 */     thisNode.pending = true;
/* 2663 */     if ((NodeList.nodeList[index]).preset) {
/*      */       return;
/*      */     }
/* 2666 */     for (int i = 0; i < thisNode.length; i++) {
/* 2667 */       int x = (thisNode.cellLoc[i]).x;
/* 2668 */       int y = (thisNode.cellLoc[i]).y;
/* 2669 */       Grid.control[x][y] = (byte)(Grid.control[x][y] - 1); if ((byte)(Grid.control[x][y] - 1) == 0) {
/* 2670 */         Grid.letter[x][y] = Grid.copy[x][y];
/*      */       }
/*      */     } 
/* 2673 */     if (thisNode.mode == 0) {
/* 2674 */       busy[thisNode.source][thisNode.length][thisNode.wordIndex] = false;
/*      */     } else {
/* 2676 */       busy[thisNode.source][thisNode.length][revLink[thisNode.source][thisNode.length][thisNode.wordIndex]] = false;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   boolean depend(int subjectIndex, int compareIndex) {
/* 2682 */     Node subjectNode = NodeList.nodeList[subjectIndex];
/* 2683 */     for (int i = 0; i < subjectNode.length; i++) {
/* 2684 */       int x = (subjectNode.cellLoc[i]).x;
/* 2685 */       int y = (subjectNode.cellLoc[i]).y;
/* 2686 */       if (compareIndex == Grid.horz[x][y] || compareIndex == Grid.vert[x][y])
/* 2687 */         return true; 
/*      */     } 
/* 2689 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void busyNode(int index) {
/* 2697 */     Node thisNode = NodeList.nodeList[index];
/* 2698 */     for (int i = 0; i < thisNode.length; i++) {
/* 2699 */       int x = (thisNode.cellLoc[i]).x;
/* 2700 */       int y = (thisNode.cellLoc[i]).y;
/* 2701 */       Grid.letter[x][y] = -1;
/* 2702 */       int scanIndex = (index == Grid.horz[x][y]) ? Grid.vert[x][y] : Grid.horz[x][y];
/* 2703 */       if (scanIndex != -1) {
/* 2704 */         Node scanNode = NodeList.nodeList[scanIndex];
/* 2705 */         for (int j = 0; j < scanNode.length; j++) {
/* 2706 */           int scanX = (scanNode.cellLoc[j]).x;
/* 2707 */           int scanY = (scanNode.cellLoc[j]).y;
/* 2708 */           Grid.control[scanX][scanY] = (byte)(Grid.control[scanX][scanY] + 1);
/*      */         } 
/*      */       } 
/*      */     } 
/* 2712 */     thisNode.pending = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void evaluateNode(int index) {
/* 2719 */     this.links = this.contax = this.contax2 = this.form = 0;
/* 2720 */     this.earlyLink = 500;
/* 2721 */     Node thisNode = NodeList.nodeList[index];
/* 2722 */     for (int i = 0; i < thisNode.length; i++) {
/* 2723 */       int x = (thisNode.cellLoc[i]).x;
/* 2724 */       int y = (thisNode.cellLoc[i]).y;
/* 2725 */       if (Grid.letter[x][y] == -1) {
/* 2726 */         this.links++;
/*      */       } else {
/* 2728 */         this.contax += Grid.control[x][y];
/* 2729 */         if (this.form == 0)
/* 2730 */           this.form = 1; 
/* 2731 */         int scanIndex = (index == Grid.horz[x][y]) ? Grid.vert[x][y] : Grid.horz[x][y];
/* 2732 */         if (scanIndex != -1) {
/* 2733 */           this.form = 2;
/* 2734 */           if (scanIndex < this.earlyLink)
/* 2735 */             this.earlyLink = scanIndex; 
/* 2736 */           Node scanNode = NodeList.nodeList[scanIndex];
/* 2737 */           int sLinks = 0;
/* 2738 */           for (int j = 0; j < scanNode.length; j++) {
/* 2739 */             int scanX = (scanNode.cellLoc[j]).x;
/* 2740 */             int scanY = (scanNode.cellLoc[j]).y;
/* 2741 */             if (Grid.letter[scanX][scanY] == -1) {
/* 2742 */               sLinks++;
/* 2743 */             } else if (x != scanX || y != scanY) {
/* 2744 */               this.contax2 += Grid.control[scanX][scanY];
/*      */             } 
/* 2746 */           }  this.contax2 -= sLinks * (scanNode.length - 1 - sLinks);
/*      */         } 
/*      */       } 
/*      */     } 
/* 2750 */     this.contax -= this.links * (thisNode.length - this.links);
/* 2751 */     if (this.form == 2 && (this.contax > 0 || this.contax2 > 0))
/* 2752 */       this.form = 3; 
/*      */   }
/*      */   
/*      */   void orderNodeList() {
/* 2756 */     int rIndex = 0;
/*      */     
/* 2758 */     int[] nodeOrder = new int[800];
/*      */     int i;
/* 2760 */     for (i = 0; i < 50; i++) {
/* 2761 */       for (int j = 0; j < 50; j++)
/* 2762 */         Grid.control[i][j] = 0; 
/*      */     } 
/* 2764 */     for (i = 0; i < NodeList.nodeListLength; i++) {
/* 2765 */       (NodeList.nodeList[i]).pending = true;
/*      */     }
/* 2767 */     busyNode(nodeOrder[0] = 0);
/* 2768 */     for (i = 1; i < NodeList.nodeListLength; i++) {
/* 2769 */       for (int bestForm = 0, maxContax2 = bestForm, maxContax = maxContax2, maxLinks = maxContax, leastEarlyLink = 10000, index = 1; index < NodeList.nodeListLength; index++) {
/* 2770 */         if (!(NodeList.nodeList[index]).pending) {
/*      */           continue;
/*      */         }
/* 2773 */         evaluateNode(index);
/* 2774 */         if (this.form >= 2 && (
/* 2775 */           bestForm != 2 || this.links <= 0 || this.form != 3)) {
/* 2776 */           if ((bestForm == 3 && maxLinks > 0 && this.form == 2) || 
/* 2777 */             this.links < maxLinks)
/* 2778 */             continue;  if (this.links == maxLinks) {
/* 2779 */             if (this.contax < maxContax)
/*      */               continue; 
/* 2781 */             if (this.contax == maxContax && (
/* 2782 */               this.earlyLink >= leastEarlyLink || 
/* 2783 */               this.contax2 < maxContax2))
/*      */               continue; 
/*      */           } 
/*      */         } 
/* 2787 */         maxLinks = this.links;
/* 2788 */         maxContax = this.contax;
/* 2789 */         maxContax2 = this.contax2;
/* 2790 */         leastEarlyLink = this.earlyLink;
/* 2791 */         bestForm = this.form;
/* 2792 */         rIndex = index;
/* 2793 */         if (bestForm < 2)
/*      */           break;  continue;
/*      */       } 
/* 2796 */       busyNode(nodeOrder[i] = rIndex);
/*      */     } 
/*      */ 
/*      */     
/* 2800 */     for (i = 1; i < NodeList.nodeListLength; i++) {
/* 2801 */       Node thisNode = NodeList.nodeList[i];
/* 2802 */       NodeList.nodeList[i] = NodeList.nodeList[nodeOrder[i]];
/* 2803 */       NodeList.nodeList[nodeOrder[i]] = thisNode;
/* 2804 */       for (int j = i; j < NodeList.nodeListLength; j++) {
/* 2805 */         if (nodeOrder[j] == i) {
/* 2806 */           nodeOrder[j] = nodeOrder[i];
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     } 
/* 2812 */     for (i = 0; i < 50; i++) {
/* 2813 */       for (int j = 0; j < 50; j++) {
/* 2814 */         Grid.control[i][j] = 0; Grid.letter[i][j] = 0;
/* 2815 */         Grid.vert[i][j] = -1; Grid.horz[i][j] = -1;
/*      */       } 
/*      */     } 
/* 2818 */     for (i = 0; i < NodeList.nodeListLength; i++) {
/* 2819 */       (NodeList.nodeList[i]).pending = true;
/* 2820 */       (NodeList.nodeList[i]).wordIndex = -1;
/* 2821 */       for (int j = 0; j < (NodeList.nodeList[i]).length; j++) {
/* 2822 */         int x = ((NodeList.nodeList[i]).cellLoc[j]).x;
/* 2823 */         int y = ((NodeList.nodeList[i]).cellLoc[j]).y;
/* 2824 */         if (Grid.horz[x][y] == -1) {
/* 2825 */           Grid.horz[x][y] = i;
/*      */         } else {
/* 2827 */           Grid.vert[x][y] = i;
/*      */         } 
/*      */       } 
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
/*      */   static boolean loadDictionary() {
/* 2851 */     int[] puzWords = new int[50];
/*      */     
/*      */     int i;
/*      */     
/* 2855 */     for (i = 0; i < NodeList.nodeListLength; i++) {
/* 2856 */       puzWords[(NodeList.nodeList[i]).length] = puzWords[(NodeList.nodeList[i]).length] + 1;
/*      */     }
/* 2858 */     for (int source = 0; source < (buildThemed ? 5 : 1); source++) {
/* 2859 */       for (i = 0; i < 50; wordCount[source][i++] = 0);
/*      */       
/* 2861 */       try { DataInputStream dataIn = new DataInputStream(new FileInputStream(Op.cw[dicList[source]] + ".dic/xword.dic"));
/* 2862 */         dataIn.read(DictionaryMtce.dicHeader, 0, 128);
/* 2863 */         while (dataIn.available() > 2) {
/* 2864 */           int wordData = dataIn.readInt();
/* 2865 */           String word = dataIn.readUTF();
/* 2866 */           if (word.length() > 1 && (
/* 2867 */             wordData % 256 != 255 || Op.getBool(Op.CW.CwIgnoreLockout.ordinal(), Op.cw).booleanValue()))
/* 2868 */             wordCount[source][word.length()] = wordCount[source][word.length()] + 1; 
/* 2869 */           dataIn.readUTF();
/*      */         } 
/* 2871 */         dataIn.close(); }
/* 2872 */       catch (IOException exc) { System.out.println("Error"); }
/*      */       
/*      */       int len;
/* 2875 */       for (len = 2; len < 50; len++) {
/* 2876 */         if (wordCount[source][len] > 0) {
/* 2877 */           chWord[0][source][len] = new char[wordCount[source][len]][len];
/* 2878 */           chWord[1][source][len] = new char[wordCount[source][len]][len];
/* 2879 */           busy[source][len] = new boolean[wordCount[source][len]];
/* 2880 */           revLink[source][len] = new int[wordCount[source][len]];
/* 2881 */           for (i = 0; i < wordCount[source][len]; ) { revLink[source][len][i] = i; i++; }
/*      */         
/*      */         } 
/*      */       } 
/* 2885 */       for (i = 0; i < 50; wordCount[source][i++] = 0);
/*      */       
/* 2887 */       try { DataInputStream dataIn = new DataInputStream(new FileInputStream(Op.cw[dicList[source]] + ".dic/xword.dic"));
/* 2888 */         for (i = 0; i < 128; i++)
/* 2889 */           dataIn.readByte(); 
/* 2890 */         while (dataIn.available() > 2) {
/* 2891 */           int wordData = dataIn.readInt();
/* 2892 */           String word = dataIn.readUTF();
/* 2893 */           len = word.length();
/* 2894 */           if (len > 1 && (
/* 2895 */             wordData % 256 != 255 || Op.getBool(Op.CW.CwIgnoreLockout.ordinal(), Op.cw).booleanValue())) {
/* 2896 */             wordCount[source][len] = wordCount[source][len] + 1; chWord[0][source][len][wordCount[source][len]] = word.toCharArray();
/* 2897 */           }  dataIn.readUTF();
/*      */         } 
/* 2899 */         dataIn.close(); }
/* 2900 */       catch (IOException exc) { System.out.println("Error"); }
/*      */       
/* 2902 */       for (len = 2; len < 50; len++) {
/* 2903 */         for (i = 0; i < wordCount[source][len]; i++) {
/* 2904 */           for (int j = 0; j < len; j++)
/* 2905 */             chWord[1][source][len][i][j] = chWord[0][source][len][i][len - 1 - j]; 
/*      */         } 
/* 2907 */         if (wordCount[source][len] > 1) {
/* 2908 */           qSort(chWord[1][source][len], revLink[source][len], 0, wordCount[source][len] - 1);
/*      */         }
/*      */       } 
/*      */     } 
/* 2912 */     themeDic[0] = true;
/* 2913 */     for (i = 0; i < 50; i++) {
/* 2914 */       if (puzWords[i] > wordCount[0][i])
/* 2915 */         return false; 
/*      */     } 
/* 2917 */     return true;
/*      */   }
/*      */   
/*      */   static void qSort(char[][] list, int[] link, int low, int high) {
/* 2921 */     int top = low, bot = high;
/* 2922 */     String pivot = Arrays.toString(list[low + (high - low) / 2]);
/* 2923 */     char[][] swp = new char[1][];
/*      */     
/* 2925 */     while (top <= bot) {
/* 2926 */       for (; Arrays.toString(list[top]).compareTo(pivot) < 0; top++);
/* 2927 */       for (; Arrays.toString(list[bot]).compareTo(pivot) > 0; bot--);
/*      */       
/* 2929 */       if (top <= bot) {
/* 2930 */         swp[0] = list[top]; list[top] = list[bot]; list[bot] = swp[0];
/* 2931 */         int iSwp = link[top]; link[top] = link[bot]; link[bot] = iSwp;
/* 2932 */         top++;
/* 2933 */         bot--;
/*      */       } 
/*      */     } 
/*      */     
/* 2937 */     if (low < bot)
/* 2938 */       qSort(list, link, low, bot); 
/* 2939 */     if (top < high)
/* 2940 */       qSort(list, link, top, high); 
/*      */   }
/*      */   
/*      */   int firstBinarySearch(char[] template, int wlen, int tlen, int source, int m) {
/* 2944 */     int first = 0, last = wordCount[source][wlen] - 1;
/*      */     
/*      */     while (true) {
/* 2947 */       int current = (first + last) / 2; int i;
/* 2948 */       for (i = 0; i < tlen; i++) {
/* 2949 */         if (template[i] > chWord[m][source][wlen][current][i]) {
/* 2950 */           first = current;
/*      */           break;
/*      */         } 
/* 2953 */         if (template[i] < chWord[m][source][wlen][current][i]) {
/* 2954 */           last = current;
/*      */           break;
/*      */         } 
/*      */       } 
/* 2958 */       if (i == tlen)
/* 2959 */         last = current; 
/* 2960 */       if (last - first <= 1)
/* 2961 */         return first; 
/*      */     } 
/*      */   }
/*      */   
/*      */   int lastBinarySearch(char[] template, int wlen, int tlen, int source, int m) {
/* 2966 */     int first = 0, last = wordCount[source][wlen] - 1;
/*      */     
/*      */     while (true) {
/* 2969 */       int current = (first + last) / 2; int i;
/* 2970 */       for (i = 0; i < tlen; i++) {
/* 2971 */         if (template[i] < chWord[m][source][wlen][current][i]) {
/* 2972 */           last = current;
/*      */           break;
/*      */         } 
/* 2975 */         if (template[i] > chWord[m][source][wlen][current][i]) {
/* 2976 */           first = current;
/*      */           break;
/*      */         } 
/*      */       } 
/* 2980 */       if (i == tlen)
/* 2981 */         first = current; 
/* 2982 */       if (last - first <= 1)
/* 2983 */         return last; 
/*      */     } 
/*      */   }
/*      */   
/*      */   boolean findMatchingWord(int nodeIndex) {
/* 2988 */     Random r = new Random();
/* 2989 */     int j = 0;
/*      */     
/* 2991 */     Node thisNode = NodeList.nodeList[nodeIndex];
/* 2992 */     int mode = thisNode.mode;
/* 2993 */     int length = thisNode.length;
/* 2994 */     int source = thisNode.source;
/* 2995 */     if (wordCount[source][length] == 0)
/* 2996 */       return false; 
/* 2997 */     if (!themeDic[source]) {
/* 2998 */       return false;
/*      */     }
/* 3000 */     char[] temp = thisNode.template.toCharArray();
/* 3001 */     if (thisNode.wordIndex != -1) {
/*      */       do {
/* 3003 */         if (++thisNode.wordIndex > thisNode.last)
/* 3004 */           thisNode.wordIndex = thisNode.first; 
/* 3005 */         if (thisNode.wordIndex == thisNode.start) {
/* 3006 */           thisNode.wordIndex = -1;
/* 3007 */           return false;
/*      */         } 
/* 3009 */       } while (busy[source][length][thisNode.wordIndex]);
/*      */     } else {
/* 3011 */       if (temp[0] == ' ') {
/* 3012 */         thisNode.first = 0;
/* 3013 */         thisNode.last = wordCount[source][length] - 1;
/*      */       } else {
/*      */         int tlen;
/* 3016 */         for (tlen = 0; tlen < length && 
/* 3017 */           temp[tlen] != ' '; tlen++);
/*      */         
/* 3019 */         thisNode.first = firstBinarySearch(temp, length, tlen, source, mode);
/* 3020 */         thisNode.last = lastBinarySearch(temp, length, tlen, source, mode);
/* 3021 */         if (thisNode.last - thisNode.first < 0)
/* 3022 */           return false; 
/*      */       } 
/* 3024 */       thisNode.wordIndex = thisNode.start = thisNode.first + r.nextInt(thisNode.last - thisNode.first + 1);
/*      */     } 
/*      */     
/*      */     label54: while (true) {
/* 3028 */       if (!busy[source][length][thisNode.wordIndex]) {
/* 3029 */         for (j = 0; j < length && (
/* 3030 */           temp[j] == ' ' || temp[j] == chWord[mode][source][length][thisNode.wordIndex][j]); j++);
/*      */       }
/*      */       
/* 3033 */       if (j == length) {
/* 3034 */         if (mode == 0) {
/* 3035 */           thisNode.word = new String(chWord[0][source][length][thisNode.wordIndex], 0, length);
/*      */         } else {
/* 3037 */           thisNode.word = new String(chWord[0][source][length][revLink[source][length][thisNode.wordIndex]], 0, length);
/* 3038 */         }  return true;
/*      */       } 
/*      */       while (true) {
/* 3041 */         if (++thisNode.wordIndex > thisNode.last)
/* 3042 */           thisNode.wordIndex = thisNode.first; 
/* 3043 */         if (thisNode.wordIndex == thisNode.start) {
/* 3044 */           thisNode.wordIndex = -1;
/* 3045 */           return false;
/*      */         } 
/* 3047 */         if (!busy[source][length][thisNode.wordIndex])
/*      */           continue label54; 
/*      */       } 
/*      */       break;
/*      */     }  } boolean findMatch(int index) {
/*      */     while (true) {
/* 3053 */       if ((NodeList.nodeList[index]).preset)
/* 3054 */         return true; 
/* 3055 */       if (findMatchingWord(index))
/* 3056 */         return true; 
/* 3057 */       if ((NodeList.nodeList[index]).source > 0) {
/* 3058 */         (NodeList.nodeList[index]).source--;
/* 3059 */         (NodeList.nodeList[index]).wordIndex = -1; continue;
/*      */       }  break;
/*      */     } 
/* 3062 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean wordOK(int index) {
/* 3070 */     Node thisNode = NodeList.nodeList[index];
/* 3071 */     for (int i = 0; i < thisNode.length; i++) {
/* 3072 */       int x = (thisNode.cellLoc[i]).x, y = (thisNode.cellLoc[i]).y;
/* 3073 */       int scanIndex = (index == Grid.horz[x][y]) ? Grid.vert[x][y] : Grid.horz[x][y];
/* 3074 */       if (scanIndex != -1) {
/* 3075 */         Node scanNode = NodeList.nodeList[scanIndex];
/* 3076 */         if (scanNode.pending) {
/* 3077 */           NodeList.buildTemplate(scanIndex);
/* 3078 */           scanNode.source = buildThemed ? 4 : 0;
/* 3079 */           scanNode.wordIndex = -1;
/* 3080 */           if (!findMatch(scanIndex))
/* 3081 */             return false; 
/*      */         } 
/*      */       } 
/*      */     } 
/* 3085 */     return true;
/*      */   }
/*      */   
/*      */   boolean lookAhead(int index) {
/* 3089 */     int count = 0, wordsIn[] = new int[50];
/*      */ 
/*      */     
/* 3092 */     if (!wordOK(index)) return false; 
/* 3093 */     Node thisNode = NodeList.nodeList[index]; int i;
/* 3094 */     for (i = 0; i < thisNode.length; i++) {
/* 3095 */       int x = (thisNode.cellLoc[i]).x, y = (thisNode.cellLoc[i]).y;
/* 3096 */       int scanIndex = (index == Grid.horz[x][y]) ? Grid.vert[x][y] : Grid.horz[x][y];
/* 3097 */       if (scanIndex != -1 && 
/* 3098 */         (NodeList.nodeList[scanIndex]).pending) {
/* 3099 */         wordsIn[count++] = scanIndex;
/* 3100 */         NodeList.buildTemplate(scanIndex);
/* 3101 */         (NodeList.nodeList[scanIndex]).source = buildThemed ? 4 : 0;
/* 3102 */         (NodeList.nodeList[scanIndex]).wordIndex = -1;
/*      */       } 
/*      */     } 
/*      */     
/* 3106 */     for (i = 0; i < count; ) {
/* 3107 */       int scanIndex = wordsIn[i];
/* 3108 */       if (findMatch(scanIndex)) {
/* 3109 */         insertWord(scanIndex);
/* 3110 */         if (wordOK(scanIndex)) {
/* 3111 */           i++; continue;
/*      */         } 
/* 3113 */         extractWord(scanIndex);
/*      */         continue;
/*      */       } 
/* 3116 */       for (; --i >= 0; i--) {
/* 3117 */         extractWord(wordsIn[i]);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 3122 */     if (i == count) {
/* 3123 */       for (i = 0; i < count; i++) {
/* 3124 */         extractWord(wordsIn[i]);
/* 3125 */         (NodeList.nodeList[wordsIn[i]]).wordIndex = -1;
/*      */       } 
/* 3127 */       return true;
/*      */     } 
/* 3129 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   void treeExtract(int index) {
/* 3134 */     for (int scanIndex = index + 1; scanIndex < NodeList.nodeListLength; scanIndex++) {
/* 3135 */       if (depend(index, scanIndex) && !(NodeList.nodeList[scanIndex]).pending)
/* 3136 */         treeExtract(scanIndex); 
/* 3137 */     }  extractWord(index);
/*      */   }
/*      */   
/*      */   static void addThemeColors(boolean add) {
/* 3141 */     if (!buildThemed)
/*      */       return; 
/*      */     int index;
/* 3144 */     for (index = 0; index < NodeList.nodeListLength; index++) {
/* 3145 */       for (int i = 0; i < (NodeList.nodeList[index]).length; i++)
/* 3146 */         Grid.color[((NodeList.nodeList[index]).cellLoc[i]).x][((NodeList.nodeList[index]).cellLoc[i]).y] = Def.themeColor[0]; 
/* 3147 */     }  if (!add)
/*      */       return; 
/* 3149 */     for (index = 0; index < NodeList.nodeListLength; index++) {
/* 3150 */       for (int i = 0; i < (NodeList.nodeList[index]).length; i++) {
/* 3151 */         if ((NodeList.nodeList[index]).source > 0)
/* 3152 */           Grid.color[((NodeList.nodeList[index]).cellLoc[i]).x][((NodeList.nodeList[index]).cellLoc[i]).y] = Def.themeColor[(NodeList.nodeList[index]).source]; 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   int fillCrossword() {
/*      */     int nodeIndex;
/* 3158 */     NodeList.buildTemplate(nodeIndex = 0);
/* 3159 */     (NodeList.nodeList[nodeIndex]).source = buildThemed ? 4 : 0;
/* 3160 */     int j = 0; while (true) {
/* 3161 */       if (findMatch(nodeIndex)) {
/* 3162 */         insertWord(nodeIndex);
/* 3163 */         if (wordOK(nodeIndex)) {
/* 3164 */           (NodeList.nodeList[nodeIndex]).failCount = 0;
/*      */           
/* 3166 */           for (; nodeIndex < NodeList.nodeListLength && !(NodeList.nodeList[nodeIndex]).pending; nodeIndex++);
/*      */           
/* 3168 */           if (++j % 500 == 0 && !buildThemed) {
/* 3169 */             restoreFrame();
/* 3170 */             Methods.buildProgress(jfCrossword, Op.cw[Op.CW.CwPuz.ordinal()]);
/* 3171 */             if (Def.building == 2)
/* 3172 */               return 0; 
/*      */           } 
/* 3174 */           if (nodeIndex < NodeList.nodeListLength) {
/* 3175 */             (NodeList.nodeList[nodeIndex]).wordIndex = -1;
/* 3176 */             NodeList.buildTemplate(nodeIndex);
/* 3177 */             (NodeList.nodeList[nodeIndex]).source = buildThemed ? 4 : 0;
/*      */             
/*      */             continue;
/*      */           } 
/*      */           break;
/*      */         } 
/* 3183 */         extractWord(nodeIndex);
/*      */       } else {
/*      */         
/* 3186 */         if (nodeIndex == 0) {
/* 3187 */           return 0;
/*      */         }
/* 3189 */         if (++(NodeList.nodeList[nodeIndex]).failCount > 500) {
/* 3190 */           (NodeList.nodeList[nodeIndex]).failCount = 0;
/* 3191 */           nodeIndex = problemNode(nodeIndex);
/*      */         } else {
/*      */           
/* 3194 */           nodeIndex = (NodeList.nodeList[nodeIndex]).revert;
/* 3195 */         }  treeExtract(nodeIndex);
/*      */       } 
/* 3197 */       if (j > 500000)
/* 3198 */         return 2; 
/*      */     } 
/* 3200 */     return 1;
/*      */   }
/*      */   
/*      */   int problemNode(int subjectNode) {
/* 3204 */     int problemNode = 1000;
/*      */     
/* 3206 */     for (int i = 0; i < (NodeList.nodeList[subjectNode]).length; i++) {
/* 3207 */       int x = ((NodeList.nodeList[subjectNode]).cellLoc[i]).x, y = ((NodeList.nodeList[subjectNode]).cellLoc[i]).y;
/* 3208 */       int testNode = (Grid.horz[x][y] == subjectNode) ? Grid.vert[x][y] : Grid.horz[x][y];
/* 3209 */       if (testNode != -1 && 
/* 3210 */         !(NodeList.nodeList[testNode]).pending && testNode < problemNode)
/* 3211 */         problemNode = testNode; 
/*      */     } 
/* 3213 */     return problemNode;
/*      */   }
/*      */   
/*      */   boolean buildCrossword(boolean loadDic) {
/* 3217 */     boolean ret = true;
/*      */     
/*      */     while (true) {
/*      */       boolean incomplete;
/*      */       int j;
/* 3222 */       for (j = 0, incomplete = false; j < Grid.ySz; j++) {
/* 3223 */         for (int k = 0; k < Grid.xSz; k++)
/* 3224 */         { if (Grid.mode[k][j] < 6 && Grid.mode[k][j] != 1 && Grid.mode[k][j] != 2 && Grid.letter[k][j] == 0)
/* 3225 */             incomplete = true;  } 
/* 3226 */       }  if (incomplete)
/* 3227 */         for (j = 0; j < Grid.ySz; j++) {
/* 3228 */           for (int k = 0; k < Grid.xSz; k++)
/* 3229 */             Grid.copy[k][j] = Grid.letter[k][j]; 
/*      */         }  
/* 3231 */       orderNodeList();
/* 3232 */       if (loadDic) {
/* 3233 */         if (!loadDictionary()) {
/* 3234 */           return false;
/*      */         }
/*      */       } else {
/* 3237 */         for (int source = 0; source < 5; source++) {
/* 3238 */           for (int length = 2; length < 50; length++)
/* 3239 */           { for (int n = 0; n < wordCount[source][length]; n++)
/* 3240 */               busy[source][length][n] = false;  } 
/*      */         } 
/* 3242 */       }  for (j = 0; j < Grid.ySz; j++) {
/* 3243 */         for (int k = 0; k < Grid.xSz; k++) {
/* 3244 */           Grid.letter[k][j] = Grid.copy[k][j];
/* 3245 */           Grid.color[k][j] = 16777215;
/*      */         } 
/*      */       } 
/*      */       int i;
/* 3249 */       for (i = 0; i < NodeList.nodeListLength; i++) {
/* 3250 */         NodeList.buildTemplate(i);
/* 3251 */         (NodeList.nodeList[i]).preset = !(NodeList.nodeList[i]).template.contains(" ");
/* 3252 */         (NodeList.nodeList[i]).template = "";
/*      */       } 
/*      */ 
/*      */       
/* 3256 */       for (int index = 1; index < NodeList.nodeListLength; index++) {
/* 3257 */         Node thisNode = NodeList.nodeList[index];
/* 3258 */         for (int rIndex = index - 1; rIndex > 0; rIndex--) {
/* 3259 */           if (depend(rIndex, index) && !(NodeList.nodeList[rIndex]).preset) {
/* 3260 */             thisNode.revert = (rIndex == 0) ? (index - 1) : rIndex;
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/* 3266 */       switch (fillCrossword()) {
/*      */         case 0:
/* 3268 */           ret = false;
/*      */           break;
/*      */         case 1:
/* 3271 */           ret = true;
/*      */           break;
/*      */         case 2:
/* 3274 */           for (j = 0; j < Grid.ySz; j++) {
/* 3275 */             for (i = 0; i < Grid.xSz; i++)
/* 3276 */               Grid.letter[i][j] = Grid.copy[i][j]; 
/*      */           }  continue;
/*      */       }  break;
/* 3279 */     }  if (ret) {
/* 3280 */       for (byte b = 0; b < NodeList.nodeListLength; b++) {
/* 3281 */         if ((NodeList.nodeList[b]).preset == true) {
/* 3282 */           NodeList.buildTemplate(b);
/* 3283 */           (NodeList.nodeList[b]).word = (NodeList.nodeList[b]).template;
/* 3284 */           (NodeList.nodeList[b]).template = "";
/*      */         } 
/*      */       } 
/* 3287 */       NodeList.attachClues(Op.cw[Op.CW.CwDic.ordinal()], Boolean.valueOf(buildThemed));
/* 3288 */       NodeList.sortNodeList(2);
/* 3289 */       NodeList.rebuildHorzAndVert();
/*      */     } 
/* 3291 */     if (!buildThemed)
/* 3292 */       restoreFrame(); 
/* 3293 */     return ret;
/*      */   }
/*      */ 
/*      */   
/*      */   private void multiBuild() {
/* 3298 */     String title = Methods.puzzleTitle;
/* 3299 */     int[] puz = new int[4];
/*      */ 
/*      */     
/* 3302 */     SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
/* 3303 */     Calendar c = Calendar.getInstance();
/*      */     
/* 3305 */     for (this.hmCount = 0; this.hmCount < this.howMany; this.hmCount++) {
/* 3306 */       if (this.startPuz > 9999999) { 
/* 3307 */         try { c.setTime(sdf.parse("" + this.startPuz)); }
/* 3308 */         catch (ParseException ex) { System.out.println("Error"); }
/* 3309 */          this.startPuz = Integer.parseInt(sdf.format(c.getTime())); }
/*      */ 
/*      */       
/* 3312 */       Methods.puzzleTitle = "CROSSWORD Puzzle : " + this.startPuz;
/*      */       
/* 3314 */       if (this.grids != null) {
/* 3315 */         String s = this.grids[this.hmCount % this.grids.length].getName();
/* 3316 */         makeGrid();
/* 3317 */         Grid.loadGrid(s);
/*      */       } else {
/*      */         
/* 3320 */         Grid.loadGrid(Op.cw[Op.CW.CwGrid.ordinal()]);
/*      */       } 
/* 3322 */       Methods.buildProgress(jfCrossword, Op.cw[Op.CW.CwPuz
/* 3323 */             .ordinal()] = "" + this.startPuz + ".crossword");
/* 3324 */       Methods.havePuzzle = buildCrossword(true);
/* 3325 */       restoreFrame();
/* 3326 */       Wait.shortWait(100);
/* 3327 */       if (Def.building == 2) {
/*      */         return;
/*      */       }
/* 3330 */       saveCrossword(Op.cw[Op.CW.CwPuz.ordinal()]);
/*      */       
/* 3332 */       for (int i = 3; i > 0; i--)
/* 3333 */         puz[i] = puz[i - 1]; 
/* 3334 */       puz[0] = this.startPuz;
/*      */       
/* 3336 */       if (Op.getBool(Op.CW.CwAutoLock.ordinal(), Op.cw).booleanValue()) {
/* 3337 */         lockoutWords();
/* 3338 */         if (puz[3] > 0)
/* 3339 */           unlockWords(puz[3]); 
/*      */       } 
/* 3341 */       this.startPuz++;
/*      */     } 
/*      */     
/* 3344 */     if (Op.getBool(Op.CW.CwAutoLock.ordinal(), Op.cw).booleanValue())
/* 3345 */       for (int i = 0; i < 3; i++) {
/* 3346 */         unlockWords(puz[i]);
/*      */       } 
/* 3348 */     this.howMany = 1;
/* 3349 */     Methods.puzzleTitle = title;
/*      */   }
/*      */   
/*      */   boolean themeBuild() {
/* 3353 */     int bestNumThemes = 0;
/* 3354 */     for (int loop = 0; loop < 50; loop++) {
/* 3355 */       if (buildCrossword((loop == 0))) {
/* 3356 */         Methods.havePuzzle = true;
/*      */         int numThemes;
/* 3358 */         for (int index = numThemes = 0; index < NodeList.nodeListLength; index++) {
/* 3359 */           if ((NodeList.nodeList[index]).source > 0)
/* 3360 */             numThemes++; 
/*      */         } 
/* 3362 */         if (numThemes > bestNumThemes) {
/* 3363 */           bestNumThemes = numThemes;
/* 3364 */           restoreFrame();
/* 3365 */           Wait.shortWait(100);
/* 3366 */           saveCrossword(Op.cw[Op.CW.CwPuz.ordinal()]);
/*      */         } 
/*      */       } 
/* 3369 */       if (Def.building == 2) {
/* 3370 */         Def.building = 1;
/*      */         break;
/*      */       } 
/*      */     } 
/* 3374 */     if (Methods.havePuzzle)
/* 3375 */       loadCrossword(Op.cw[Op.CW.CwPuz.ordinal()]); 
/* 3376 */     return true;
/*      */   }
/*      */   
/*      */   void updateGrid(MouseEvent e) {
/* 3380 */     int x = e.getX(), y = e.getY();
/*      */     
/* 3382 */     if (Def.building == 1)
/* 3383 */       return;  if (x < Grid.xOrg || y < Grid.yOrg)
/* 3384 */       return;  int i = (x - Grid.xOrg) / Grid.xCell;
/* 3385 */     int j = (y - Grid.yOrg) / Grid.yCell;
/* 3386 */     if (i >= Grid.xSz || j >= Grid.ySz)
/* 3387 */       return;  i = Grid.RorL(i);
/* 3388 */     if (this.colorizing) {
/* 3389 */       Grid.color[i][j] = (Grid.color[i][j] == 16777215) ? this.thisColor : 16777215;
/* 3390 */       restoreFrame();
/*      */       return;
/*      */     } 
/* 3393 */     Grid.xNew = i;
/* 3394 */     Grid.yNew = j;
/* 3395 */     changeCursor();
/* 3396 */     Def.dispCursor = Boolean.valueOf(true);
/* 3397 */     restoreFrame();
/*      */   }
/*      */   void handleKeyPressed(KeyEvent e) {
/*      */     Point[] cellLoc;
/*      */     int i;
/* 3402 */     if (Def.building == 1)
/*      */       return; 
/* 3404 */     if (e.isAltDown())
/*      */       return; 
/* 3406 */     switch (e.getKeyCode()) { case 38:
/* 3407 */         if (Grid.yCur > 0) Grid.yNew--;  break;
/* 3408 */       case 40: if (Grid.yCur < Grid.ySz - 1) Grid.yNew++;  break;
/* 3409 */       case 37: if (DictionaryMtce.dicHeader[DictionaryMtce.R2L_WORD] == 1) {
/* 3410 */           if (Grid.xCur < Grid.xSz - 1) Grid.xNew++;  break;
/*      */         } 
/* 3412 */         if (Grid.xCur > 0) Grid.xNew--;  break;
/* 3413 */       case 39: if (DictionaryMtce.dicHeader[DictionaryMtce.R2L_WORD] == 1) {
/* 3414 */           if (Grid.xCur > 0) Grid.xNew--;  break;
/*      */         } 
/* 3416 */         if (Grid.xCur < Grid.xSz - 1) Grid.xNew++;  break;
/* 3417 */       case 36: if (DictionaryMtce.dicHeader[DictionaryMtce.R2L_WORD] == 1) {
/* 3418 */           Grid.xNew = Grid.xSz - 1; break;
/*      */         } 
/* 3420 */         Grid.xNew = 0; break;
/* 3421 */       case 35: if (DictionaryMtce.dicHeader[DictionaryMtce.R2L_WORD] == 1) {
/* 3422 */           Grid.xNew = 0; break;
/*      */         } 
/* 3424 */         Grid.xNew = Grid.xSz - 1; break;
/* 3425 */       case 33: Grid.yNew = 0; break;
/* 3426 */       case 34: Grid.yNew = Grid.ySz - 1; break;
/*      */       case 8: case 32:
/*      */       case 127:
/* 3429 */         Grid.letter[Grid.xCur][Grid.yCur] = 0; break;
/*      */       case 10: break;
/*      */       default:
/* 3432 */         if (!Character.isLetter(e.getKeyChar()))
/*      */           return; 
/* 3434 */         if (Grid.mode[Grid.xCur][Grid.yCur] != 0 && (Grid.mode[Grid.xCur][Grid.yCur] < 3 || Grid.mode[Grid.xCur][Grid.yCur] > 5))
/*      */           return; 
/* 3436 */         Grid.letter[Grid.xCur][Grid.yCur] = Character.toUpperCase(e.getKeyChar());
/* 3437 */         cellLoc = (NodeList.nodeList[Grid.nCur]).cellLoc;
/* 3438 */         for (i = 0; i < (NodeList.nodeList[Grid.nCur]).length; i++) {
/* 3439 */           if ((cellLoc[i]).x == Grid.xCur && (cellLoc[i]).y == Grid.yCur && 
/* 3440 */             i < (NodeList.nodeList[Grid.nCur]).length - 1) {
/* 3441 */             Grid.xNew = (cellLoc[i + 1]).x;
/* 3442 */             Grid.yNew = (cellLoc[i + 1]).y;
/*      */             break;
/*      */           } 
/*      */         } 
/*      */         break; }
/*      */ 
/*      */     
/* 3449 */     if (e.getKeyCode() != 127 && e.getKeyCode() != 8 && e.getKeyCode() != 32)
/* 3450 */       changeCursor(); 
/* 3451 */     restoreFrame();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static void printKdpDialog(Graphics2D g2, int left, int top, int dim, int gap, String puzName) {
/* 3457 */     FontMetrics fm = g2.getFontMetrics();
/*      */     
/* 3459 */     String st = puzName;
/* 3460 */     int w = fm.stringWidth(st);
/* 3461 */     g2.drawString(st, left + (dim - w) / 2, top - gap / 3);
/* 3462 */     CrosswordSolve.loadCrossword(puzName + ".crossword");
/* 3463 */     setSizesAndOffsets(left, top, dim, dim, 0, false);
/* 3464 */     Methods.clearGrid(Grid.sol);
/* 3465 */     CrosswordSolve.drawCrossword(g2);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static void printKDPSol(Graphics2D g2, int left, int top, int dim, int gap, String solName) {
/* 3471 */     FontMetrics fm = g2.getFontMetrics();
/*      */     
/* 3473 */     String st = solName;
/* 3474 */     int w = fm.stringWidth(st);
/* 3475 */     g2.drawString(st, left + (dim - w) / 2, top - gap / 3);
/* 3476 */     loadCrossword(solName + ".crossword");
/* 3477 */     setSizesAndOffsets(left, top, dim, dim, 0, false);
/* 3478 */     drawCrossword(g2);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static void printKDPPuz(Graphics2D g2, int left, int top, int dim, int gap, String solName) {
/* 3484 */     FontMetrics fm = g2.getFontMetrics();
/*      */     
/* 3486 */     String st = solName;
/* 3487 */     int w = fm.stringWidth(st);
/* 3488 */     g2.drawString(st, left + (dim - w) / 2, top - gap / 3);
/* 3489 */     loadCrossword(solName + ".crossword");
/* 3490 */     setSizesAndOffsets(left, top, dim, dim, 0, false);
/* 3491 */     drawCrossword(g2);
/*      */   }
/*      */ }


/* Location:              C:\Users\mustapha\Desktop\Crossword-Express.jar!\crosswordexpress\CrosswordBuild.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */