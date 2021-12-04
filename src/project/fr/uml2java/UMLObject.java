package fr.uml2java;

import org.json.*;

public class UMLObject {
    private String visibility = "public";
    private String id;
    private String parentRef;
    private String name;

    public UMLObject() {}

    public UMLObject(JSONObject jsonObject) {
        this.id = jsonObject.getString("_id");

        if (jsonObject.has("_parent")) {
            this.parentRef = jsonObject.getJSONObject("_parent").getString("$ref");
        }

        if (jsonObject.has("name")) {
            this.name = jsonObject.getString("name");
        }

        if (jsonObject.has("visibility")) {
            this.visibility = jsonObject.getString("visibility");
        }
    }

    @Override
    public String toString() {
        return "UMLObject{" +
                "id='" + id + '\'' +
                ", parentRef='" + parentRef + '\'' +
                ", name='" + name + '\'' +
                ", visibility='" + visibility + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentRef() {
        return parentRef;
    }

    public void setParentRef(String parentRef) {
        this.parentRef = parentRef;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }
}
