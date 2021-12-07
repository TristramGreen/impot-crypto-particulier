package org.btax;

import org.apache.commons.cli.*;

public class CommandLine {
    private final org.apache.commons.cli.CommandLine commandLine;
    private final Options options = new Options();

    public CommandLine(String[] args) throws Exception {
        options.addOption(Option.builder("f")
                .longOpt("csvFile")
                .hasArg()
                .argName("CSV_FILE")
                .desc("full file path of the csv containing transaction data")
                .required()
                .build());
        options.addOption(Option.builder("h")
                .longOpt("help")
                .desc("display help")
                .build());
        CommandLineParser parser = new DefaultParser();
        commandLine = parser.parse(options, args);
    }

    public org.apache.commons.cli.CommandLine getCommandLine() {
        return commandLine;
    }

    public String getOptionValue(String opt) {
        return commandLine.getOptionValue(opt);
    }

    public void printHelp() {
        HelpFormatter formatter = new HelpFormatter();
        String header = "Command line tool to help french investors calculate their crypto gains";
        formatter.printHelp("baguette-crypto-tax", header, options, "", true);
    }
}