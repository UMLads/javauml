package fr.uml2java;

import org.json.JSONObject;

public class UMLAttribute extends UMLObject {
    private boolean _static = false;
    private String multiplicity = "1";
    private boolean readOnly = false;
    private boolean derived = false;
    private boolean isId = false;
    private String aggregation = "none";
    private String defaultValue = "none";
    private String type;

    public UMLAttribute(JSONObject jsonObject) {
        super(jsonObject);

        if (jsonObject.has("isDerived")) {
            this.setDerived();
            return;
        }

        if (jsonObject.has("isStatic")) {
            this.setStatic();
        }

        this.type = jsonObject.getString("_type");

        if (jsonObject.has("multiplicity")) {
            this.multiplicity = jsonObject.getString("multiplicity");
        }

        if (jsonObject.has("defaultValue")) {
            this.defaultValue = jsonObject.getString("defaultValue");
        }

        if (jsonObject.has("isId")) {
            this.setIsId();
        }

        if (jsonObject.has("aggregation")) {
            this.aggregation = jsonObject.getString("aggregation");
        }
    }

    @Override
    public String toString() {
        return "UMLAttribute{" +

                '}';
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isStatic() {
        return _static;
    }

    public void setStatic() {
        this._static = true;
    }

    public String getMultiplicity() {
        return multiplicity;
    }

    public void setMultiplicity(String multiplicity) {
        this.multiplicity = multiplicity;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly() {
        this.readOnly = true;
    }

    public boolean isDerived() {
        return derived;
    }

    public void setDerived() {
        this.derived = true;
    }

    public boolean isId() {
        return isId;
    }

    public void setIsId() {
        isId = true;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getAggregation() {
        return aggregation;
    }

    public void setAggregation(String aggregation) {
        this.aggregation = aggregation;
    }
}
