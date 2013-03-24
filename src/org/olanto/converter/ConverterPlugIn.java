/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.olanto.converter;

import java.util.concurrent.Callable;

/**
 *
 * @author ang
 */
public abstract class ConverterPlugIn implements Callable {

    private String plugInName;
    private String plugInCommand;
    private String plugInProcess;
    
    public ConverterPlugIn(String plugInName, String plugInCommand, String plugInProcess){
            this.plugInName = plugInName;
            this.plugInCommand = plugInCommand;
            this.plugInProcess = plugInProcess;
    }
    
    public String getPlugInName(){
        return this.plugInName;
    }
    
    public String getPlugInCommand(){
        return this.plugInCommand;
    }
    
    public String getPlugInProcess(){
        return this.plugInProcess;
    }
    
    @Override
    public Object call() throws Exception {      
        Process p = new ProcessBuilder(this.plugInCommand).start();
        return p.exitValue();
    }
    
    public void stopProcess() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
}
