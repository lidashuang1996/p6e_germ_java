package com.p6e.germ.security.achieve;

import org.aspectj.lang.JoinPoint;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * @author lidashuang
 * @version 1.0
 */
public final class P6eSecurityAchieveApplication {

    /** 缓存获取数据的对象 */
    private static P6eSecurityAchieveInterface ACHIEVE;

    /**
     * 初始化操作
     */
    public static void init() {
        init(P6eSecurityAchieveDefaultRequest.class);
    }

    public synchronized static void init(Class<? extends P6eSecurityAchieveInterface> clazz) {
        try {
            // 创建实现对象
            ACHIEVE = clazz.newInstance();
            // 初始化缓存
            P6eSecurityAchieveCache.init(ACHIEVE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public P6eSecurityAchieveGroupModel getGroupCache(String id) {
        return P6eSecurityAchieveCache.getGroupCache(id);
    }

    public P6eSecurityAchieveJurisdictionModel getJurisdictionCache(String code) {
        return P6eSecurityAchieveCache.getJurisdictionCache(code);
    }

    public static void generateJurisdictionFile(String packagePath) {
        final File file = new File(
                "./src/main/java/"
                + packagePath.replaceAll("\\.", "/")
                + "/P6eSecurityJurisdictionConfig.java");
        FileWriter fileWriter = null;
        try {
            System.out.println("开始创建的文件");
            System.out.println("文件创建结果，" + file.createNewFile());
            final List<P6eSecurityAchieveJurisdictionModel> jurisdictionModelList = ACHIEVE.getJurisdictionData();
//            final List<P6eSecurityAchieveJurisdictionModel> jurisdictionModelList = new ArrayList<>();
            jurisdictionModelList.add(new P6eSecurityAchieveJurisdictionModel(1, "JAAA", "NAME", "DDDD", "CCCC"));
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
            fileWriter.write("public final class P6eSecurityJurisdictionConfig implements Serializable {\r\n");
            for (final P6eSecurityAchieveJurisdictionModel item : jurisdictionModelList) {
                fileWriter.write("\r\n");
                fileWriter.write("\t/**\r\n");
                fileWriter.write("\t * id: " + item.getId() + "\r\n");
                fileWriter.write("\t * code: " + item.getCode() + "\r\n");
                fileWriter.write("\t * name: " + item.getName() + "\r\n");
                fileWriter.write("\t * describe: " + item.getDescribe() + "\r\n");
                fileWriter.write("\t * content: " + item.getContent() + "\r\n");
                fileWriter.write("\t */\r\n");
                fileWriter.write("\tpublic static final String "
                        + item.getCode().toUpperCase() + " = \""
                        + item.getCode().toUpperCase() + "\";\r\n");
            }
            fileWriter.write("\r\n");
            fileWriter.write("}\r\n");
            fileWriter.write("\r\n");
            fileWriter.flush();
            System.out.println("结束创建的文件");
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
     * 获取请求的方法的包名类名方法名
     * @param jp 代理的对象
     * @return 包名和类名组成的路径
     */
    private static String getPath(final JoinPoint jp) {
        final String className = jp.getThis().getClass().getName();
        final String methodsName = jp.getSignature().getName();
        final String[] cs = className.split("\\$\\$");
        return cs[0] + "." + methodsName + "()";
    }

    public static void main(String[] args) {
        generateJurisdictionFile("com.p6e.germ");
    }

}
