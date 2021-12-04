package fr.uml2java;

import org.json.JSONObject;

public class UMLAssociationEnd extends UMLObject {
    private String reference; // id of the class with that attribute

    private UMLAttribute value;

    public UMLAssociationEnd(JSONObject jsonObject) {
        super(jsonObject);

        this.reference = jsonObject.getJSONObject("reference").getString("$ref");

        this.value = new UMLAttribute(jsonObject);
    }

    @Override
    public String toString() {
        return "UMLAssociationEnd{" +
                "_id='" + getId() + '\'' +
                ", parentRef'" + getParentRef() + '\'' +
                ", name='" + getName() + '\'' +
                ", visibility='" + getVisibility() + '\'' +
                ", reference='" + reference + '\'' +
                ", value={" + value +
                "}}";
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public UMLAttribute getValue() {
        return value;
    }

    public void setValue(UMLAttribute value) {
        this.value = value;
    }
}
