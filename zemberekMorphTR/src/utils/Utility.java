/**
 * 
 */
package utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

/**
 * @author orf
 *
 */
public class Utility {
	public static int getNumberOfLines(String filename){

		int linenumber = 0;

		try{

			File file =new File(filename);

			if(file.exists()){

				FileReader fr = new FileReader(file);
				LineNumberReader lnr = new LineNumberReader(fr);

				while (lnr.readLine() != null){
					linenumber++;
				}

				lnr.close();

			}else{
				System.out.println("File does not exists!");
			}

		}catch(IOException e){
			linenumber = 0;
			e.printStackTrace();
		}
		return linenumber;
	}
}
