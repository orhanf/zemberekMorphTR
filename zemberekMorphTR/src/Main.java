
import java.io.*;

import trs.SentenceParser;
import utils.Utility;

public class Main {

	public static boolean IS_DISAMBIGUATE = false;
	public static boolean IS_SHORT_WORDS  = false;
	public static String  INPUT_FILENAME  = "";
	public static String  OUTPUT_FILENAME = "zemberek.seg.txt";

	/*
	 * I hate java
	 */
	public static void main(String[] args) {
		try {

			parseArgs(args);

			int nLinesInp = Utility.getNumberOfLines(INPUT_FILENAME);
			int ctr = 0;

			FileInputStream fis = new FileInputStream(new File(INPUT_FILENAME));
			BufferedReader in   = new BufferedReader(new InputStreamReader(fis));
			FileWriter fstream  = new FileWriter(OUTPUT_FILENAME, false);
			BufferedWriter out  = new BufferedWriter(fstream);
			SentenceParser sp   = new SentenceParser(IS_DISAMBIGUATE,IS_SHORT_WORDS);

			String line = null;
			String flag1 = (IS_DISAMBIGUATE)?"using":"no";
			String flag2 = (IS_SHORT_WORDS) ? "short":"long";
			System.out.println( "Segmenting " + nLinesInp  + " sentences with " + flag1 + 
								" disambiguation and getting " + flag2 + " suffix list for words" );
			System.out.println("Starting...");

			//Process each line and write				
			while ((line = in.readLine()) != null) {

				if(IS_DISAMBIGUATE)
					out.write(sp.parseSentenceGetFirstDisambiguate(line));
				else
					out.write(sp.parseSentenceGetFirst(line));

				out.newLine();
				ctr++;
				if( (ctr % 10000) == 0)
					System.out.println(ctr + "/" + nLinesInp);
			}

			// close buffers
			in.close();
			out.close();

			System.out.println("Done!");
			System.out.print("Number of lines in the input file:");
			System.out.println(nLinesInp);
			System.out.print("Number of lines in the output file:");
			System.out.println(Utility.getNumberOfLines(args[1]));

		} catch (FileNotFoundException e) {
			System.err.println("FileStreamsTest: " + e);
		} catch (IOException e) {
			System.err.println("FileStreamsTest: " + e);
		} catch(Exception e){
			System.err.println("An Unknown Error has Occured");
			e.printStackTrace();
		}
	}

	private static void parseArgs(String[] args){
		
		int i=0;
		String arg;
		
		while (i < args.length && args[i].startsWith("-")) {
			arg = args[i++];
			if (arg.equals("-o")) {
				if (i < args.length)
					OUTPUT_FILENAME = args[i++];
				else
					System.err.println("-o requires a filename");
			}else if (arg.equals("-i")) {
				if (i < args.length)
					INPUT_FILENAME = args[i++];
				else
					System.err.println("-i requires a filename");
			}else if (arg.equals("-d")) {
				IS_DISAMBIGUATE = true;
			}else if (arg.equals("-s")) {
				IS_SHORT_WORDS = true;
			}else if (arg.equals("-h")){
				usage();
				System.exit(-1);
			}
		}
	}

	private static void usage(){
		System.out.println("java Main.java -i inputFilename -o outputFilename -d -s -h");
		System.out.println("		-d : flag for disambiguation (default false)");
		System.out.println("		-s : flag for short suffixes for words (default false)");
		System.out.println("		-h : help");
	}

}
