package com.p6e.germ.jurisdiction.model;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 用户的认证的数据，需要用户自己定义
 * 这里定义一个父类，用户自己继承并扩展
 * @author lidashuang
 * @version 1.0
 */
public class P6eJurisdictionModel implements Serializable {

    /** 权限组和权限的数据 */
    private final List<Group> groupData = new ArrayList<>();

    /** 计算后得到的权限数据 */
    private final List<Jurisdiction> jurisdictionData = new ArrayList<>();

    /**
     * 构造方法注入参数的数据
     */
    public P6eJurisdictionModel(List<Group> groupData) {
        if (groupData != null) {
            // 添加 groupData 数据
            this.groupData.addAll(groupData);
            // 计算出 jurisdictionData 数据
            final List<Group> groupListData = groupData.stream().sorted(
                    Comparator.comparing(Group::getWeight).reversed()).collect(Collectors.toList());
            // 遍历数据
            for (final Group group : groupListData) {
                for (final Jurisdiction jurisdiction : group.getList()) {
                    for (final Jurisdiction j : this.jurisdictionData) {
                        if (j.getName().equals(jurisdiction.getName())) {
                            break;
                        }
                    }
                    // 之前不存在数据，就添加到数组中
                    jurisdictionData.add(jurisdiction);
                }
            }
        }
    }

    /**
     * 获取权限组
     * @return 权限组称迭代器
     */
    public Iterator<Group> getGroups() {
        return groupData.iterator();
    }

    /**
     * 获取权限组
     * @param name 权限组名称
     * @return 权限组
     */
    public Group getGroups(String name) {
        for (Group group : groupData) {
            if (group.getName().equals(name)) {
                return group;
            }
        }
        return null;
    }

    /**
     * 获取权限组
     * @return 权限组名称迭代器
     */
    public Iterator<String> getGroupsName() {
        return groupData.stream().map(Group::getName).collect(Collectors.toSet()).iterator();
    }

    /**
     * 获取权限
     * @return 权限迭代器
     */
    public Iterator<Jurisdiction> getJurisdictions() {
        return jurisdictionData.iterator();
    }

    /**
     * 获取权限
     * @param name 权限名称
     * @return 权限
     */
    public Jurisdiction getJurisdictions(String name) {
        for (Jurisdiction jurisdiction : jurisdictionData) {
            if (jurisdiction.getName().equals(name)) {
                return jurisdiction;
            }
        }
        return null;
    }

    public boolean isExist(String content) {
        for (final Jurisdiction jurisdiction : jurisdictionData) {
            if (jurisdiction.isHave(content)) {
                return true;
            }
        }
        return false;
    }



    /**
     * 获取权限名称
     * @return 权限名称迭代器
     */
    public Iterator<String> getJurisdictionsName() {
        return jurisdictionData.stream().map(Jurisdiction::getName).collect(Collectors.toSet()).iterator();
    }

    /**
     * 组模型
     */
    public static class Group {
        private final String name;
        private final Integer weight;
        private final List<Jurisdiction> list;

        private Group(String name, Integer weight, List<Jurisdiction> list) {
            this.name = name;
            this.weight = weight;
            this.list = list;
        }

        public String getName() {
            return name;
        }

        public Integer getWeight() {
            return weight;
        }

        public List<Jurisdiction> getList() {
            return list;
        }

        public boolean isName(String name) {
            return this.name.equals(name);
        }
    }

    /**
     * 权限模型
     */
    public static class Jurisdiction {
        private final String name;
        private final String content;
        private final String param;

        private Jurisdiction(String name, String content, String param) {
            this.name = name;
            this.content = content;
            this.param = param;
        }

        public String getName() {
            return name;
        }

        public String getContent() {
            return content;
        }

        public String getParam() {
            return param;
        }

        public boolean isHave(String content) {
            final int index = content.lastIndexOf('_');
            if (index > -1) {
                return isHave(content.substring(0, index), content.substring(index + 1));
            }
            return false;
        }

        public boolean isHave(String name, String param) {
            return this.name.equals(name)
                    && this.param.equals(param)
                    && P6eJsonUtil.fromJsonToMap(this.getContent(), String.class, String.class).get(param) != null;
        }

        public boolean isHave(Jurisdiction jurisdiction) {
            if (jurisdiction != null) {
                return this.name.equals(jurisdiction.getContent())
                        && this.content.equals(jurisdiction.getName())
                        && this.param.equals(jurisdiction.getParam());
            } else {
                return false;
            }
        }
    }
}
