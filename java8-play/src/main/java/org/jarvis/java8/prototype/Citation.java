package org.jarvis.java8.prototype;

/**
 * author:Lovel  date:2020/5/18 原型设计模式，用一个实例作为原型创建相似的对象
 */
public class Citation implements Cloneable{
    private String name;
    private String info;
    private String college;

    public Citation() {

    }

    public Citation(String name, String info, String college) {
        this.name = name;
        this.info = info;
        this.college = college;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    @Override
    protected Citation clone() throws CloneNotSupportedException {
        return (Citation) super.clone();
    }

    @Override
    public String toString() {
        return "Citation{" +
                "name='" + name + '\'' +
                ", info='" + info + '\'' +
                ", college='" + college + '\'' +
                '}';
    }
}
