package org.waddys.xcloud.common.base.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesUtil {
    private static final Logger s_logger = LoggerFactory.getLogger(PropertiesUtil.class);

    /**
     * Searches the class path and local paths to find the config file.
     * 
     * @param path
     *            path to find. if it starts with / then it's absolute path.
     * @return File or null if not found at all.
     */
    public static File findConfigFile(String path) {
        ClassLoader cl = PropertiesUtil.class.getClassLoader();
        URL url = cl.getResource(path);
        if (url != null) {
            return new File(url.getFile());
        }

        url = ClassLoader.getSystemResource(path);
        if (url != null) {
            return new File(url.getFile());
        }

        File file = new File(path);
        if (file.exists()) {
            return file;
        }

        String newPath = "conf" + (path.startsWith(File.separator) ? "" : "/") + path;
        url = ClassLoader.getSystemResource(newPath);
        if (url != null) {
            return new File(url.getFile());
        }

        url = cl.getResource(newPath);
        if (url != null) {
            return new File(url.getFile());
        }

        newPath = "conf" + (path.startsWith(File.separator) ? "" : File.separator) + path;
        file = new File(newPath);
        if (file.exists()) {
            return file;
        }

        newPath = System.getProperty("catalina.home");
        if (newPath == null) {
            newPath = System.getenv("CATALINA_HOME");
        }

        if (newPath == null) {
            newPath = System.getenv("CATALINA_BASE");
        }

        if (newPath == null) {
            return null;
        }

        file = new File(newPath + File.separator + "conf" + File.separator + path);
        if (file.exists()) {
            return file;
        }

        return null;
    }

    public static Map<String, Object> toMap(Properties props) {
        Set<String> names = props.stringPropertyNames();
        HashMap<String, Object> map = new HashMap<String, Object>(names.size());
        for (String name : names) {
            map.put(name, props.getProperty(name));
        }

        return map;
    }

    /*
     * Returns an InputStream for the given resource This is needed to read the
     * files within a jar in classpath.
     */
    public static InputStream openStreamFromURL(String path) {
        ClassLoader cl = PropertiesUtil.class.getClassLoader();
        URL url = cl.getResource(path);
        if (url != null) {
            try {
                InputStream stream = url.openStream();
                return stream;
            } catch (IOException ioex) {
                return null;
            }
        }
        return null;
    }

    // Returns key=value pairs by parsing a commands.properties/config file
    // with syntax; key=cmd;value (with this syntax cmd is stripped) and
    // key=value
    public static Map<String, String> processConfigFile(String[] configFiles) {
        Map<String, String> configMap = new HashMap<String, String>();
        Properties preProcessedCommands = new Properties();
        for (String configFile : configFiles) {
            File commandsFile = findConfigFile(configFile);
            if (commandsFile != null) {
                FileInputStream file = null;
                try {
                    file = new FileInputStream(commandsFile);
                    preProcessedCommands.load(file);
                } catch (FileNotFoundException fnfex) {
                    // in case of a file within a jar in classpath, try to open
                    // stream using url
                    InputStream stream = PropertiesUtil.openStreamFromURL(configFile);
                    if (stream != null) {
                        try {
                            preProcessedCommands.load(stream);
                        } catch (IOException e) {
                            s_logger.error("IO Exception, unable to find properties file:", fnfex);
                        }
                    } else {
                        s_logger.error("Unable to find properites file", fnfex);
                    }
                } catch (IOException ioe) {
                    s_logger.error("IO Exception loading properties file", ioe);
                } finally {
                    if (file != null) {
                        try {
                            file.close();
                        } catch (IOException e) {
                            s_logger.error("", e);
                        }
                    }
                }
            }
        }

        for (Object key : preProcessedCommands.keySet()) {
            String preProcessedCommand = preProcessedCommands.getProperty((String) key);
            int splitIndex = preProcessedCommand.lastIndexOf(";");
            String value = preProcessedCommand.substring(splitIndex + 1);
            configMap.put((String) key, value);
        }
        return configMap;
    }
}
