package fr.uml2java;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class UMLClass extends UMLObject {
    private boolean _abstract = false;
    private List<UMLAttribute> attributeList;
    private List<UMLOperation> operationsList;
    private List<UMLAssociation> associationList;
    private List<UMLGeneralization> generalizationList;
    private List<UMLDependency> dependencyList;
    private List<UMLInterfaceRealization> implementedInterfaces;
    private int nbassociations;
    private List<String> associationTargets = new ArrayList<>();
    private List<String> associationTargetsFromMe = new ArrayList<>();



    public UMLClass(JSONObject jsonObject) {
        super(jsonObject);


        if (jsonObject.has("isAbstract")) {
            this.setAbstract();
        }
/*
        if (jsonObject.has("attributes")) {
            this.attributeList = new ArrayList<>();
            JSONArray attributes = jsonObject.getJSONArray("attributes");
            for (int i = 0; i < attributes.length(); ++i) {
                this.attributeList.add(new UMLAttribute(attributes.getJSONObject(i)));
            }
        }

        if (jsonObject.has("operations")) {
            this.operationsList = new ArrayList<>();
            JSONArray operations = jsonObject.getJSONArray("operations");
            for (int i = 0; i < operations.length(); ++i) {
                this.operationsList.add(new UMLOperation(operations.getJSONObject(i)));
            }
        }
*/
        if (jsonObject.has("ownedElements")) {
            JSONArray ownedElements = jsonObject.getJSONArray("ownedElements");
            for (int i = 0; i < ownedElements.length(); ++i) {
                JSONObject ownedElement = ownedElements.getJSONObject(i);
                if (ownedElement.getString("_type").equals("UMLAssociation")) {

                    if (this.associationList == null) this.associationList = new ArrayList<>();


                    nbassociations = nbassociations + 1;

                    UMLAssociation newAsso = new UMLAssociation(ownedElement);

                    this.associationTargets.add(newAsso.getEnd2().getReference());
                    this.associationList.add(newAsso);


                /*}
                } else if (ownedElement.has("UMLInterfaceRealization")) {
                    if (this.implementedInterfaces == null) {
                        this.implementedInterfaces = new ArrayList<>();

                    }
                    this.implementedInterfaces.add(new UMLInterfaceRealization(ownedElement));
                } else if (ownedElement.has("UMLDependency")) {
                    if (this.dependencyList == null) {
                        this.dependencyList = new ArrayList<>();
                    }

                    this.dependencyList.add(new UMLDependency(ownedElement));
                } else {
                    if (this.generalizationList == null) {
                        this.generalizationList = new ArrayList<>();
                    }
                    this.generalizationList.add(new UMLGeneralization(ownedElement));
              */  }
            }
        }
    }

    @Override
    public String toString() {
        return "UMLClass{" +
               /* "_id='" + getId() + '\'' +
                ", parentRef='" + getParentRef() + '\'' +*/
                ", name='" + getName() + '\'' +/*
                ", visibility='" + getVisibility() + '\'' +
                ", _abstract=" + _abstract +
                ", attributeList=" + attributeList +
                ", operationsList=" + operationsList +
                ", associationList=" + associationList +
                ", motherClasses=" + generalizationList +
                ", dependenciesIDList=" + dependencyList +
                ", implementedInterfaces=" + implementedInterfaces +*/
                ", getassociations targets = " + associationTargets +
                ", nbAssociations= " + nbassociations +
                '}';
    }

    public int compareTo(UMLClass umlClass){
        return  umlClass.getNbassociations ()- this.nbassociations;
    };

    public static Comparator<UMLClass> ComparatornbAssociations = new Comparator<UMLClass>() {
        @Override
        public int compare(UMLClass o1, UMLClass o2) {
            return o1.compareTo(o2);
        }
    };

    public int getNbassociations() {
        return nbassociations;
    }

    public List<String> getAssociationTargetsFromMe() {
        return associationTargetsFromMe;
    }

    public void setAssociationTargetsFromMe(List<String> associationTargetsFromMe) {
        this.associationTargetsFromMe = associationTargetsFromMe;
    }

    public List<String> getAssociationTargets() {
        return associationTargets;
    }

    public void setAssociationTargets(List<String> associationTargets) {
        this.associationTargets = associationTargets;
    }

    public void setNbassociations(int nbassociations) {
        this.nbassociations = nbassociations;
    }

    public List<UMLAttribute> getAttributeList() {
        return attributeList;
    }

    public List<UMLOperation> getOperationsList() {
        return operationsList;
    }

    public List<UMLAssociation> getAssociationList() {
        return associationList;
    }

    public List<UMLDependency> getDependencyList() {
        return dependencyList;
    }

    public List<UMLGeneralization> getGeneralizationList() {
        return generalizationList;
    }

    public List<UMLInterfaceRealization> getImplementedInterfaces() {
        return implementedInterfaces;
    }

    public Boolean isAbstract() {
        return _abstract;
    }

    public void setAbstract() {
        this._abstract = true;
    }

    public void addAttribute(UMLAttribute umlAttribute) {
        attributeList.add(umlAttribute);
    }

    public void addOperation(UMLOperation umlOperation) {
        operationsList.add(umlOperation);
    }

    public void addAssociation(UMLAssociation umlAssociation) {
        associationList.add(umlAssociation);
    }

    public void addGeneralization(UMLGeneralization generalization) {
        this.generalizationList.add(generalization);
    }

    public void addDependency(UMLDependency dependency) {
        this.dependencyList.add(dependency);
    }

    public void addInterfaceRealization(UMLInterfaceRealization umlInterfaceRealization) {
        this.implementedInterfaces.add(umlInterfaceRealization);
    }
}
