/*
 * 
 */
package org.olanto.converter;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.apache.log4j.Logger;

/**
 *
 */
public class PluginConverterFactory extends AbstractConverterFactory {

    private static final Logger _logger = Logger.getLogger(PluginConverterFactory.class);

    public static AbstractConverterFactory getInstance() {
        _logger.debug("Build new : PluginConverterFactory");
        return new PluginConverterFactory();
    }

    @Override
    public void startConvertion() {
        _logger.debug("Start converting " + source.getName());
        ArrayList<ConverterPlugin> converters = ConfigUtil.mapping.get(source.getExtention());
        try {
            for (ConverterPlugin conv : converters) {
                ExecutorService executor = Executors.newFixedThreadPool(1);
                Future<Integer> future = executor.submit(conv);             
                int retValue=future.get();
                if (retValue == 0) {
                    this.success = true;
                    return;
                }
            }
        } catch (Exception ex) {
            _logger.info(ex);
        }
        this.success = false;
    }
}
