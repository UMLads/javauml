package fr.uml2java;

import org.json.JSONObject;

public class UMLGeneralization extends UMLObject {
    private String source;
    private String target;

    public UMLGeneralization(JSONObject jsonObject) {
        super(jsonObject);

        this.source = jsonObject.getJSONObject("source").getString("$ref");

        this.target = jsonObject.getJSONObject("target").getString("$ref");
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    @Override
    public String toString() {
        return "UMLGeneralization{" +
                "_id='" + getId() + '\'' +
                ", parentRef='" + getParentRef() + '\'' +
                ", name='" + getName() + '\'' +
                ", visibility='" + getVisibility() + '\'' +
                ", source='" + source + '\'' +
                ", target='" + target + '\'' +
                "} " + super.toString();
    }
}
