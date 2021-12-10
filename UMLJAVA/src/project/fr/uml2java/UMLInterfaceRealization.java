package fr.uml2java;

import org.json.JSONObject;

public class UMLInterfaceRealization extends UMLObject {
    private String target;
    private String source;

    public UMLInterfaceRealization(JSONObject jsonObject) {
        super(jsonObject);

        this.source = jsonObject.getJSONObject("source").getString("$ref");

        this.target = jsonObject.getJSONObject("target").getString("$ref");
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
