package com.p6e.germ.jurisdiction.tool;

import com.p6e.germ.common.utils.P6eJsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author lidashuang
 * @version 1.0
 */
public final class P6eGermJurisdictionTool {

    /** 注入日志对象 */
    private static final Logger LOGGER = LoggerFactory.getLogger(P6eGermJurisdictionTool.class);
    private static String URL = "jdbc:mysql://myIP:3306/myDB?autoReconnect=true&useSSL=false&characterEncoding=UTF-8";
    private static String PASSWORD = "myPassword";
    private static String USERNAME = "myUsername";

    /**
     * 初始化参数
     * @param url DB URL
     * @param password DB PASSWORD
     * @param username DB USERNAME
     */
    public static void init(String url, String password, String username) {
        URL = url;
        PASSWORD = password;
        USERNAME = username;
    }

    /**
     * 生成配置文件的 JAVA 代码文件，避免手写
     * @param packagePath 包名称（文件路径名称）
     */
    public static void generateJurisdictionFile(String packagePath) {
        generateJurisdictionFile("./src/main/java/"
                + packagePath.replaceAll("\\.", "/"), packagePath);
    }

    /**
     * 生成配置文件的 JAVA 代码文件，避免手写
     * @param path 文件路径名称
     */
    public static void generateJurisdictionFile(final String path,
                                                final String packagePath) {
        final File file = new File(path + "/P6eJurisdictionConstant.java");
        FileWriter fileWriter = null;
        try {
            if (file.exists()) {
                LOGGER.info("文件存在，创建关闭");
                return;
            }
            LOGGER.info("开始创建的文件");
            LOGGER.info("文件创建结果，" + file.createNewFile());
            final List<DataModel> jurisdictionModelList = getData();
            fileWriter = new FileWriter(file);
            fileWriter.write("package " + packagePath + ";\r\n");
            fileWriter.write("\r\n");
            fileWriter.write("import java.io.Serializable;\r\n");
            fileWriter.write("\r\n");
            fileWriter.write("/**\r\n");
            fileWriter.write(" * 权限配置文件\r\n");
            fileWriter.write(" * @author system generation\r\n");
            fileWriter.write(" * @version 1.0\r\n");
            fileWriter.write(" */\r\n");
            fileWriter.write("public final class P6eJurisdictionConstant implements Serializable {\r\n");
            LOGGER.info("获取的数据为 ==> " + jurisdictionModelList);
            for (final DataModel item : jurisdictionModelList) {
                final Map<String, String> contentMap =
                        P6eJsonUtil.fromJsonToMap(item.getContent(), String.class, String.class);
                // 如果为 null 进行下一次循环
                if (contentMap == null) {
                    continue;
                }
                for (String key : contentMap.keySet()) {
                    LOGGER.info("写入的数据对象为 ==> [ " + key + " ] " + item);
                    fileWriter.write("\r\n");
                    fileWriter.write("\t/**\r\n");
                    fileWriter.write("\t * id: " + item.getId() + "\r\n");
                    fileWriter.write("\t * code: " + item.getCode() + "\r\n");
                    fileWriter.write("\t * name: " + item.getName() + "\r\n");
                    fileWriter.write("\t * describe: " + item.getDescribe() + "\r\n");
                    fileWriter.write("\t * content: " + item.getContent() + "\r\n");
                    fileWriter.write("\t */\r\n");
                    fileWriter.write("\tpublic static final String "
                            + item.getCode().toUpperCase()
                            + "_"
                            + key.toUpperCase()
                            + " = \""
                            + item.getCode().toUpperCase()
                            + "_"
                            + key.toUpperCase()
                            + "\";\r\n");
                }
            }
            fileWriter.write("\r\n");
            fileWriter.write("}\r\n");
            fileWriter.write("\r\n");
            fileWriter.flush();
            LOGGER.info("结束创建的文件");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileWriter != null) {
                    fileWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读取数据对象
     * @return 读取的数据源对象
     */
    private static List<DataModel> getData() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver") ;
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT " +
                    " `id` AS `id`, " +
                    " `code` AS `code`, " +
                    " `name` AS `name`, " +
                    " `describe` AS `describe`, " +
                    " `content` AS `content`, " +
                    " `create_date` AS `createDate`, " +
                    " `update_date` AS `updateDate`, " +
                    " `operate` AS `operate` " +
                    " FROM `p6e_jurisdiction`;") ;
            ResultSet resultSet = preparedStatement.executeQuery();
            List<DataModel> list = new ArrayList<>();
            while (resultSet.next()) {
                DataModel model = new DataModel();
                model.setId(resultSet.getInt(1));
                model.setCode(resultSet.getString(2));
                model.setName(resultSet.getString(3));
                model.setDescribe(resultSet.getString(4));
                model.setContent(resultSet.getString(5));
                model.setCreateDate(resultSet.getString(6));
                model.setUpdateDate(resultSet.getString(7));
                model.setOperate(resultSet.getString(8));
                list.add(model);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * 模型
     */
    public static class DataModel {
        private Integer id;
        private String code;
        private String name;
        private String describe;
        private String content;
        private String createDate;
        private String updateDate;
        private String operate;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescribe() {
            return describe;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(String updateDate) {
            this.updateDate = updateDate;
        }

        public String getOperate() {
            return operate;
        }

        public void setOperate(String operate) {
            this.operate = operate;
        }
    }
}
