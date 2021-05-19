package com.p6e.germ.jurisdiction;

/**
 * @author lidashuang
 * @version 1.0
 */
public class Test {
//    public static void main(String[] args) {
//        String name = "describe";
//        System.out.println(name.toUpperCase());
//    }

    public static void main(String[] args) {
        String name = "<li class=\"col-12 col-md-4\">\n" +
                "                <article class=\"two-line-ellipsis\">\n" +
                "                  PDT集群系统技术培训\n" +
                "                </article>\n" +
                "              </li>\n" +
                "              <li class=\"col-12 col-md-4\">\n" +
                "                <article class=\"two-line-ellipsis\">\n" +
                "                  商业小集群系统技术培训\n" +
                "                </article>\n" +
                "              </li>\n" +
                "              <li class=\"col-12 col-md-4\">\n" +
                "                <article class=\"two-line-ellipsis\">\n" +
                "                  PDT数字同播系统技术培训\n" +
                "                </article>\n" +
                "              </li>\n" +
                "              <li class=\"col-12 col-md-4\">\n" +
                "                <article class=\"two-line-ellipsis\">\n" +
                "                  TETRA集群系统技术培训\n" +
                "                </article>\n" +
                "              </li>\n" +
                "              <li class=\"col-12 col-md-4\">\n" +
                "                <article class=\"two-line-ellipsis\">\n" +
                "                  宽带集群系统技术培训\n" +
                "                </article>\n" +
                "              </li>";
        System.out.println(
                name.replaceAll("\n", "")
                .replaceAll("<", "&lt;")
                .replaceAll(">", "&gt;")
        );
    }
}
