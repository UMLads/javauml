package fr.uml2java;

import org.json.JSONObject;

public class UMLInterface extends UMLClass {
    public UMLInterface(JSONObject jsonObject) {
        super(jsonObject);
    }

    @Override
    public String toString() {
        return super.toString().replaceAll("UMLClass", "UMLInterface");
    }
}
