package fr.uml2java;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UMLPackage extends UMLObject {
    private final List<UMLClass> ownedElements;

    public UMLPackage(JSONObject jsonObject) {
        super(jsonObject);

        this.ownedElements = new ArrayList<>();

        JSONArray ownedElems = jsonObject.getJSONArray("ownedElements");
        for (int i = 0; i < ownedElems.length(); ++i) {
            JSONObject ownedElem = ownedElems.getJSONObject(i);
            switch (ownedElem.getString("_type")) {
                case "UMLClass" -> this.ownedElements.add(new UMLClass(ownedElem));
                case "UMLInterface" -> this.ownedElements.add(new UMLInterface(ownedElem));
                default -> {
                }
            }
        }

        // Add attributes on classes based on the association they had
        for (UMLClass umlClass : ownedElements) {
            for (UMLAssociation association : umlClass.getAssociationList()) {
                umlClass.addAttribute(association.getEnd1().getValue());
                for (UMLClass nUmlClass : ownedElements) {
                    if (umlClass == nUmlClass) {
                        continue;
                    }
                    if (nUmlClass.getId().equals(association.getEnd2().getReference())) {
                        nUmlClass.addAttribute(association.getEnd2().getValue());
                        break;
                    }
                }
            }

            for (UMLDependency umlDependency : umlClass.getDependencyList()) {
                String target = umlDependency.getTarget();

                String funcName;
                for (UMLClass nUmlClass : this.ownedElements) {
                    if (umlClass == nUmlClass) {
                        continue;
                    }

                    if (nUmlClass.getId().equals(target)) {
                        funcName = "compute" + nUmlClass.getName();

                        UMLOperation operation = new UMLOperation();
                        operation.setName(funcName);

                        UMLParameter parameter = new UMLParameter();
                        parameter.setType(nUmlClass.getName());

                        operation.addParameter(parameter);
                        break;
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        return "UMLPackage{" +
                "id='" + getId() + '\'' +
                ", parentRef='" + getParentRef() + '\'' +
                ", name='" + getName() + '\'' +
                ", ownedElements=" + ownedElements +
                "}";
    }

    public List<UMLClass> getOwnedElements() {
        return ownedElements;
    }
}