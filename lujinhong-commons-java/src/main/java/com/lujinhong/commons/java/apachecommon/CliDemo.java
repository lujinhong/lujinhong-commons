/**
 * 
 */
package com.lujinhong.commons.java.apachecommon;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
* date: 2016年4月19日 下午2:15:42
* @author LUJINHONG lu_jin_hong@163.com
* Function: TODO ADD FUNCTION.
* last modified: 2016年4月19日 下午2:15:42
*/

public class CliDemo {
	
    private static Options options;
    private static String inputDir;
    private static String outputDir;
    private static String encoding = "utf-8";
	
	//TODO
    public static void printUsage() {
        new HelpFormatter().printHelp("CliDemo [OPTIONS] inputDir outputDir", options);
    }
	
    public static boolean parseArgs(String[] args) throws ParseException {
        CommandLineParser parser = new GnuParser();
        options = new Options( );
        options.addOption("h", "help", false, "Print this usage information");
        options.addOption(
                 OptionBuilder.withArgName("encoding")
                        .hasArg()
                        .withDescription("")
                        .withLongOpt("encoding")
                        .create());
        CommandLine commandLine = parser.parse(options, args);

        String[] nonOptions = commandLine.getArgs();
        if (commandLine.hasOption("help")) {
            printUsage();
            return false;
        }
        if(nonOptions.length != 2) {
            printUsage();
            return false;
        }
        inputDir = nonOptions[0];
        outputDir = nonOptions[1];

        if(commandLine.hasOption("encoding")) {
            encoding = commandLine.getOptionValue("encoding");
        }

        return true;
    }

	public static void main(String[] args) throws ParseException {
		if(!parseArgs(args)){
			System.exit(0);
		}
		
		System.out.println("encoding=" + encoding + "\t arg0=" + inputDir + "\t arg1=" + outputDir);

	}

}
