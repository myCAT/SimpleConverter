/**********
    Copyright © 2010-2012 Olanto Foundation Geneva

   This file is part of myCAT.

   myCAT is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as
    published by the Free Software Foundation, either version 3 of
    the License, or (at your option) any later version.

    myCAT is distributed in the hope that it will be useful, but
    WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
    See the GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with myCAT.  If not, see <http://www.gnu.org/licenses/>.

**********/

package org.olanto.converter;

import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.artofsolving.jodconverter.office.OfficeException;

/** pour les DOC
 */
public class ConverterFactoryDOC extends ConverterFactoryOffice {

    private static final Logger _logger = Logger.getLogger(ConverterFactoryDOC.class);

    public static ConverterFactoryDOC getInstance() {
        return new ConverterFactoryDOC();
    }

    @Override
    public void init(Document docSource, Document docTarget) {
        super.init(docSource, docTarget);
    }

    @Override
    public void startConvertion() {
        _logger.debug("Start converting " + source.getName());
        long startTime = System.currentTimeMillis();
        if (ConverterFactoryOffice.getOfficeManager() == null) {
            _logger.debug("convertion time: " + (System.currentTimeMillis() - startTime) + " ms");
            return;
        }

        OfficeDocumentConverter converter = new OfficeDocumentConverter(ConverterFactoryOffice.getOfficeManager());
        Map<String,Object> loadProperties = new HashMap<String,Object>();
        loadProperties.put("Hidden", true);
        loadProperties.put("ReadOnly", true);
       // loadProperties.put("FilterName", "Text (encoded)");
       loadProperties.put("FilterOptions", "UTF8");
       // loadProperties.put("charset", "utf-8");
        converter.setDefaultLoadProperties(loadProperties);

        try {
            converter.convert(source, target);
            success = true;
        } catch (OfficeException e) {
            close();
            _logger.error(e.getMessage());
        }
        _logger.debug("convertion time: " + (System.currentTimeMillis() - startTime) + " ms");
    }
}
