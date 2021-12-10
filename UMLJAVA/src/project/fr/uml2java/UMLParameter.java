package fr.uml2java;

import org.json.JSONObject;

public class UMLParameter extends UMLObject {
    private String type;
    private String direction;

    public UMLParameter() {}

    public UMLParameter(JSONObject jsonObject) {
        super(jsonObject);

        if (jsonObject.has("direction")) {
            this.direction = jsonObject.getString("direction");
        }

        if (jsonObject.has("type")) {
            this.type = jsonObject.getString("type");
        }
    }

    @Override
    public String toString() {
        return "UMLParameter{" +
                "_id='" + getId() + '\'' +
                ", parentRef='" + getParentRef() + '\'' +
                ", name='" + getName() + '\'' +
                ", visibility='" + getVisibility() + '\'' +
                ", type='" + type + '\'' +
                ", direction='" + direction + '\'' +
                '}';
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
