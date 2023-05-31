package ma.sir.easystock.zynerator.service;

public class Attribute {
    private String name;
    private String type;
    private Class complexType;

    public Attribute() {
    }

    public Attribute(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public Attribute(String name) {
        this.name = name;
        this.type = "String";
    }

    public Attribute(String name, String type, Class complexType) {
        this.name = name;
        this.type = type;
        this.complexType = complexType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Class getComplexType() {
        return complexType;
    }

    public void setComplexType(Class complexType) {
        this.complexType = complexType;
    }
}
