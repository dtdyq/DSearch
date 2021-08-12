
/*
 * 
 */

package dyq.dsearch.util;

import ch.qos.logback.core.PropertyDefinerBase;

public class LoggerCfg extends PropertyDefinerBase {

    @Override
    public String getPropertyValue() {
        String ROOT_PATH = System.getProperty("user.home") + "/.dsearch/";

        return ROOT_PATH + ".log";
    }
}
