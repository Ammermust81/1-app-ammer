/*     */ package crosswordexpress;
/*     */ 
/*     */ import java.awt.Toolkit;
/*     */ import java.net.URI;
/*     */ import java.net.URL;
/*     */ import java.util.Arrays;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JOptionPane;
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
/*     */ class browserLaunch
/*     */ {
/*  74 */   static final String[] browsers = new String[] { "google-chrome", "firefox", "opera", "epiphany", "konqueror", "conkeror", "midori", "kazehakase", "mozilla" };
/*     */   static final String errMsg = "Error attempting to launch web browser";
/*     */   static String url;
/*     */   static URL versionURL;
/*     */   static Boolean netOn;
/*     */   
/*     */   public static void openURL(JFrame theFrame, String theUrl) {
/*  81 */     url = theUrl;
/*     */     
/*  83 */     CrosswordExpress.networkStatus("access.txt");
/*  84 */     if (!CrosswordExpress.networkOn) {
/*  85 */       Methods.networkError(theFrame);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*     */     try {
/*  91 */       Class<?> d = Class.forName("java.awt.Desktop");
/*  92 */       d.getDeclaredMethod("browse", new Class[] { URI.class }).invoke(d
/*  93 */           .getDeclaredMethod("getDesktop", new Class[0]).invoke(null, new Object[0]), new Object[] {
/*  94 */             URI.create(url)
/*     */           });
/*     */     }
/*  97 */     catch (ClassNotFoundException|NoSuchMethodException|SecurityException|IllegalAccessException|IllegalArgumentException|java.lang.reflect.InvocationTargetException ignore) {
/*     */       
/*  99 */       String osName = System.getProperty("os.name");
/*     */       try {
/* 101 */         if (osName.startsWith("Mac OS")) {
/* 102 */           Toolkit.getDefaultToolkit();
/* 103 */           Class.forName("com.apple.eio.FileManager").getDeclaredMethod("openURL", new Class[] { String.class }).invoke(null, new Object[] { url });
/*     */         }
/* 105 */         else if (osName.startsWith("Windows")) {
/* 106 */           Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
/*     */         } else {
/*     */           
/* 109 */           String browser = null;
/* 110 */           for (String b : browsers) {
/* 111 */             if (browser == null && Runtime.getRuntime().exec(new String[] { "which", b }).getInputStream().read() != -1)
/* 112 */               Runtime.getRuntime().exec(new String[] { browser = b, url }); 
/* 113 */           }  if (browser == null) {
/* 114 */             throw new Exception(Arrays.toString(browsers));
/*     */           }
/*     */         } 
/* 117 */       } catch (Exception e) {
/* 118 */         JOptionPane.showMessageDialog(null, "Error attempting to launch web browser\n" + e.toString());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mustapha\Desktop\Crossword-Express.jar!\crosswordexpress\browserLaunch.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */