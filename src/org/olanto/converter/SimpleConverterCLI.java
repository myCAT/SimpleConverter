/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.olanto.converter;

/**
 *
 * @author ang
 */
public class SimpleConverterCLI {
   private static final int EXIT_CODE_TOO_FEW_ARGS = 255;

    
    public static void main(String[] arguments) throws Exception {
        
        ConfigUtil.loadConfigFromXml();
        
        if (arguments.length < 1) {
            System.out.println("SimpleConverterCLI config-file \n");
            System.exit(EXIT_CODE_TOO_FEW_ARGS);
        }
        
        
    }

}
