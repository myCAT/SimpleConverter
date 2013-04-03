/*
 * 
 */
package org.olanto.converter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 */
public class SimpleConverterCLI {

    private static final int EXIT_CODE_TOO_FEW_ARGS = 255;

    public static void main(String[] arguments) throws Exception {

        ConfigUtil.loadConfigFromXml();

//        if (arguments.length < 1) {
//            System.out.println("Usage: SimpleConverterCLI config-file \n");
//            System.exit(EXIT_CODE_TOO_FEW_ARGS);
//        }
        Document inputFile = new Document(ConfigUtil.docPath);
        Document outFile = new Document(ConfigUtil.sourcePath);
        String badfilesPath = ConfigUtil.badPath;

        outFile.mkdirs();
        SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        long startTime = System.currentTimeMillis();
        String dateStamp = df.format(new Date());
        ConverterReport.statictics.info("Start time: " + dateStamp);
        ConverterReport.badFiles.info("Start time: " + dateStamp);
        ConverterReport.notSupportedExtention.info("Start time: " + dateStamp);
        ConverterReport.convertedFiles.info("Start time: " + dateStamp);

        SimpleConverterApplication converter = SimpleConverterApplication.getInstance();
        converter.setMaxRetry(ConfigUtil.maxRetry);
        converter.setOutputFormat(ConfigUtil.targetFormat);
        converter.convertObject(inputFile, outFile, badfilesPath);

        ConverterReport.statictics.info("------------------------------ Statistics --------------------------");
        ConverterReport.statictics.info("Number of treated files                    : " + converter.getGlobalFilesCount());
        ConverterReport.statictics.info("Number of converted files                  : " + converter.getConvertedCount());
        ConverterReport.statictics.info("------------------------------ Unsuccess ---------------------------");
        ConverterReport.statictics.info("Number of not supported file's extentions  : " + converter.getNotSupportedCount());
        ConverterReport.statictics.info("  list of extentions [" + converter.getNotSupportedExtentionList() + "]");
        ConverterReport.statictics.info("Number of bad files                        : " + converter.getBadfilesCount());
        ConverterReport.statictics.info("------------------------------ End ---------------------------------");

        dateStamp = df.format(new Date());
        ConverterReport.statictics.info("Stop time: " + dateStamp);
        ConverterReport.badFiles.info("Stop time: " + dateStamp);
        ConverterReport.notSupportedExtention.info("Stop time: " + dateStamp);
        ConverterReport.convertedFiles.info("Stop time: " + dateStamp);
        ConverterReport.statictics.info("Total time: " + (System.currentTimeMillis() - startTime) + "ms");

        // Close OpenOffice instance if any
        ConverterFactoryOffice.close();

    }
}
