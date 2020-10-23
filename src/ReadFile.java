import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ReadFile {
	String[] stopWords = new String[571];
	String DELIMITERS = "[-+=" +
	        " " +        //space
	        "\r\n " +    //carriage return line fit
			"1234567890" + //numbers
			"’'\"" +       // apostrophe
			"(){}<>\\[\\]" + // brackets
			":" +        // colon
			"," +        // comma
			"‒–—―" +     // dashes
			"…" +        // ellipsis
			"!" +        // exclamation mark
			"." +        // full stop/period
			"«»" +       // guillemets
			"-‐" +       // hyphen
			"?" +        // question mark
			"‘’“”" +     // quotation marks
			";" +        // semicolon
			"/" +        // slash/stroke
			"⁄" +        // solidus
			"␠" +        // space?   
			"·" +        // interpunct
			"&" +        // ampersand
			"@" +        // at sign
			"*" +        // asterisk
			"\\" +       // backslash
			"•" +        // bullet
			"^" +        // caret
			"¤¢$€£¥₩₪" + // currency
			"†‡" +       // dagger
			"°" +        // degree
			"¡" +        // inverted exclamation point
			"¿" +        // inverted question mark
			"¬" +        // negation
			"#" +        // number sign (hashtag)
			"№" +        // numero sign ()
			"%‰‱" +      // percent and related signs
			"¶" +        // pilcrow
			"′" +        // prime
			"§" +        // section sign
			"~" +        // tilde/swung dash
			"¨" +        // umlaut/diaeresis
			"_" +        // underscore/understrike
			"|¦" +       // vertical/pipe/broken bar
			"⁂" +        // asterism
			"☞" +        // index/fist
			"∴" +        // therefore sign
			"‽" +        // interrobang
			"※" +          // reference mark
	        "]";

			

		
	ReadFile(){
		File stopWTXT = new File("/stop_words_en.txt");
		try {
			
			BufferedReader br = new BufferedReader(new FileReader(stopWTXT));
			
			for (int i = 0; i < stopWords.length; i++) {
				stopWords[i]=br.readLine();

			}
		} catch (FileNotFoundException e) {
			System.out.println("Error detected while reading file!!");
		} catch (IOException e) {
			System.out.println("Error detected while reading file!!");
		}
		
		
	
	}
	
	public boolean checkStopWord(String control) {
		
		for (int i = 0; i < stopWords.length; i++) {
			if (control.equals(stopWords[i])) {
				return false;
			}
		}
		return true;
	}


}
