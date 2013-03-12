package com.searchengine.bool.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class SearchConfig {
    private static final String PROPERTY_FILE = "application.properties";

    private static final String INT_STR = "int";
//    private static final String UAT_STR = "uat";
//    private static final String PROD_STR = "prod";
//    private static final String LOCAL_STR = "local";
//
//    private static final String INT_PREFIX = "int.";
//    private static final String UAT_PREFIX = "uat.";
//    private static final String PROD_PREFIX = "prod.";
//    private static final String LOCAL_PREFIX = "local.";
//
//
//    protected static final String QUERY_SELECT = "SELECT PARAM_VALUE FROM SEARCH_CONFIG_PARAMS " +
//            "WHERE UPPER(SYSTEM) = UPPER('%s') " +
//            "AND UPPER(PARAM_KEY) = UPPER('%s')";

//    private static final String className = SearchConfig.class.getName();

    // possible states
//    private enum ENV { INT, UAT, PROD, LOCAL };

    // we want to load the file only once, so use static
    private static boolean isInitialized = false;
    private static Properties properties;

//    private ENV envSetting;
    private String keyPrefix;
    private boolean doLogging = true;

//    private QueryRunner queryRunner = new QueryRunner();
//    private SearchConfigDatabaseHelper SearchConfigDatabaseHelper = new SearchConfigDatabaseHelper();

    private static SearchConfig instance = null;// = new SearchConfig();


    public static synchronized SearchConfig getInstance() {
        if (instance == null) {
            instance = new SearchConfig();
        }

        return instance;
    }

    /**
     * Additional constructor when Log4J is not available - goes System.out
     */
    public SearchConfig(boolean doLogging) {
        this.doLogging = doLogging;

        loadProperties(PROPERTY_FILE);

//        info("loaded property file");
//
//        envSetting = getEnvSetting();
//
//        if (isINT()) {
//            info("using INT env for configuration");
//            keyPrefix = INT_PREFIX;
//        } else if (isUAT()) {
//            info("using UAT env for configuration");
//            keyPrefix = UAT_PREFIX;
//        } else if (isPROD()) {
//            info("using PROD env for configuration");
//            keyPrefix = PROD_PREFIX;
//        } else if (isLOCAL()) {
//            info("using LOCAL env for configuration");
//            keyPrefix = LOCAL_PREFIX;
//        }
    }
    /**
     * Default constructor to enable Log4J logging
     */
    public SearchConfig() {
        this(true);
    }

//    public boolean isINT() {
//        return envSetting.equals(ENV.INT);
//    }
//
//    public boolean isUAT() {
//        return envSetting.equals(ENV.UAT);
//    }
//
//    public boolean isPROD() {
//        return envSetting.equals(ENV.PROD);
//    }
//
//    public boolean isLOCAL() {
//        return envSetting.equals(ENV.LOCAL);
//    }

//    private void info(String msg) {
//        if (doLogging) {
//            MoleLogger.info(msg, className);
//        } else {
//            System.out.println(msg);
//        }
//    }
//
//    private void warn(String msg) {
//        if (doLogging) {
//            MoleLogger.warn(msg, className);
//        } else {
//            System.err.println(msg);
//        }
//    }
//
//    private void error(String msg) {
//        if (doLogging) {
//            MoleLogger.error(msg, className);
//        } else {
//            System.err.println(msg);
//        }
//    }

    /**
     * @return value for key, using env-specific prefix
     */
    public String getProperty(String key) {
        String realKey = keyPrefix + key;

        String value = properties.getProperty(realKey);

        if (value == null) {
//            info("could not find '" + realKey + "' in config file. This may be normal. Searching database...");
//            value = SearchConfigDatabaseHelper.getProperty(key, realKey);
        }

        return value;
    }

    /**
     * load env-specific property file into this class
     */
    private synchronized void loadProperties(String propertyFile) {
        if (!isInitialized) {
            InputStream inputStream = null;

            try {
                inputStream = this.getClass().getClassLoader().getResourceAsStream(propertyFile);

                properties = new Properties();
                properties.load(inputStream);
                isInitialized = true;
            } catch (IOException ex) {
                String msg = "IO error trying to read property file: " + propertyFile;
//                error(msg);
                throw new IllegalStateException(msg);
            } finally {
                try {
                    inputStream.close();
                } catch(IOException ioex) {
//                    String msg = "IO error trying to close property file: " + propertyFile;
//                    warn(msg);
                    // do not throw exception since we should already have the properties by now
                }
            }
        }
    }

//    /**
//     * @return appropriate ENV value, depending on system property
//     */
//    private ENV getEnvSetting() {
//        ENV envSetting = null;
//
//        String envValue = System.getProperty(ENV_KEY);
//
//        if (envValue != null) {
//            info("value for " + ENV_KEY + " : " + envValue);
//
//            String lowerEnvValue = envValue.toLowerCase();
//            boolean isINT = (lowerEnvValue.indexOf(INT_STR) != -1);
//            boolean isUAT = (lowerEnvValue.indexOf(UAT_STR) != -1);
//            boolean isPROD = (lowerEnvValue.indexOf(PROD_STR) != -1);
//            boolean isLOCAL = (lowerEnvValue.indexOf(LOCAL_STR) != -1);
//
//            if (isPROD) {
//                info("detected env setting for PROD");
//                envSetting = ENV.PROD;
//            } else if (isUAT) {
//                info("detected env setting for UAT");
//                envSetting = ENV.UAT;
//            } else if (isINT) {
//                info("detected env setting for INT");
//                envSetting = ENV.INT;
//            } else if (isLOCAL) {
//                info("detected env setting for LOCAL");
//                envSetting = ENV.LOCAL;
//            }
//        }
//
//        if (envSetting == null) {
//            // use INT as default, but warn
//            warn("could not find value for key = " + ENV_KEY + " ; defaulting to INT env");
//            envSetting = ENV.INT;
//        }
//
//        return envSetting;
//    }
//
//    public String getEnvironment() {
//        return getEnvSetting().toString();
//    }

//    protected String getDBProperty(String keyPrefix, String keyValue){
//        String  env = keyPrefix.replaceAll("\\.", "");
//        String dbPropertyValue = runQuery(env,keyValue);
//
//        return dbPropertyValue;
//    }

    public int getPropertyAsInt(String key) {
        String property = getProperty(key);
        int propertyAsInt = 0;

        if (property != null){
            propertyAsInt = Integer.parseInt(property.trim());
        }

        return propertyAsInt;
    }

//    public List<String> getPropertyAsList(String key) {
//        String property = getProperty(key);
//        List<String> propertyAsList = new ArrayList<String>();
//
//        String[] arrayOfItems = property.split(",");
//
//        StringUtils stringUtils = new StringUtils();
//        for (String item : arrayOfItems) {
//            // Only add non-null and non-empty IDs
//            String trimmedId = stringUtils.safeToString(item).trim();
//            if (!trimmedId.isEmpty()) {
//                propertyAsList.add(trimmedId);
//            }
//        }
//
//        return propertyAsList;
//    }
//
//    protected String runQuery(String env, String key){
//        String finalResult = "";
//
//        String finalSQL = String.format(QUERY_SELECT, env, key);
//
//        QueryResponse response = queryRunner.run(finalSQL, QueryRunner.DATASRC_ID_search,
//                QueryRunner.GENERIC_QUERY_ID,false);
//        if (response.isSuccessful()){
//            finalResult = response.getOneResultValue();
//        } else { // Nothing was found in DB
//            finalResult = "";
//            error(response.getException().toString());
//        }
//        return  finalResult;
//    }
//
//    // For testing only
//    protected void setQueryRunner (QueryRunner queryRunner){
//        this.queryRunner = queryRunner;
//    }

    // For testing only
//    protected void setSearchConfigDatabaseHelper (SearchConfigDatabaseHelper SearchConfigDatabaseHelper){
//        this.SearchConfigDatabaseHelper = SearchConfigDatabaseHelper;
//    }
//
//    /**
//     * For getting property value from search Database (search_CONFIG_PARAMS table)
//     * according to Current Environment
//     *
//     * @param  key - name of the property
//     * @return value of the property
//     * @author avvolkov
//     */
//    public String getPropertyFromDB(String key) {
//        String realKey = keyPrefix + key;
//
//        return SearchConfigDatabaseHelper.getProperty(key, realKey);
//    }
//
//    protected class SearchConfigDatabaseHelper {
//        protected String getProperty(String key, String realKey){
//            String value = null;
//
//            String  dbValue = getDBProperty(keyPrefix, key);
//            if (dbValue == null || dbValue.equalsIgnoreCase("")){
//                // get default values for
//                String msg = "could not find key : " + realKey + " deduced from env: " + envSetting.toString();
//                error(msg);
//                throw new IllegalArgumentException(msg);
//
//            } else {
//                value = dbValue;
//                info("Using database value: " + value);
//            }
//
//            return value;
//        }
//    }

}
