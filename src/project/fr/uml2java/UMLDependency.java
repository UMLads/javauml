package fr.uml2java;

import org.json.JSONObject;

public class UMLDependency extends UMLObject {
    private String source;
    private String target;

    public UMLDependency(JSONObject jsonObject) {
        super(jsonObject);

        this.source = jsonObject.getJSONObject("source").getString("$ref");

        this.target = jsonObject.getJSONObject("target").getString("$ref");
    }

    @Override
    public String toString() {
        return super.toString().replaceAll("UMLObject", "UMLDependency");
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
}
