package com.billcom.connectionpools.initializer;

import com.lhs.ccb.cfw.sgu.CDAInitializer;
import com.lhs.ccb.common.DelayAction;

import javax.servlet.ServletContextEvent;
import java.io.File;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WsInitializer extends CDAInitializer {
    public WsInitializer() {
    }

    public void contextInitialized(ServletContextEvent var1) {
        Logger var2 = Logger.getLogger("SystemLog");

        try {
            URL var3 = WsInitializer.class.getClassLoader().getResource("Registry.xml");
            if (null != var3) {
                File var4 = new File(var3.getPath());
                if (var2.isLoggable(Level.FINE)) {
                    var2.fine("set BSCS_RESOURCE=" + var4.getParent());
                }

                System.setProperty("BSCS_RESOURCE", var4.getParent());
            }
        } catch (Throwable var5) {
            if (var2.isLoggable(Level.WARNING)) {
                var2.warning("Missing file: Registry.xml under WEB-INF/classes. It is not possible to set BSCS_RESOURCE.");
            }
        }

        super.contextInitialized(var1);
    }

    public void contextDestroyed(ServletContextEvent var1) {
        super.contextDestroyed(var1);
        DelayAction.getInstance().shutdown();
    }
}
