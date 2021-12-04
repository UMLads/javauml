package fr.uml2java;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UMLOperation extends UMLObject {
    private boolean _static = false;
    private boolean _abstract = false;

    private List<UMLParameter> parameterList;

    public UMLOperation() {}

    public UMLOperation(JSONObject jsonObject) {
        super(jsonObject);

        if (jsonObject.has("isStatic")) {
            this.setStatic();
        }

        if (jsonObject.has("isAbstract")) {
            this.setAbstract();
        }

        this.parameterList = new ArrayList<>();
        JSONArray parameters = jsonObject.getJSONArray("parameters");
        for (int i = 0; i < parameters.length(); ++i) {
            this.parameterList.add(new UMLParameter(parameters.getJSONObject(i)));
        }
    }

    @Override
    public String toString() {
        return "UMLOperation{" +
                "_id='" + getId() + '\'' +
                ", parentRef'" + getParentRef() + '\'' +
                ", name='" + getName() + '\'' +
                ", visibility='" + getVisibility() + '\'' +
                ", _static=" + _static +
                ", _abstract=" + _abstract +
                ", parameterList=" + parameterList +
                '}';
    }

    public void addParameter(UMLParameter umlParameter) {
        parameterList.add(umlParameter);
    }

    public List<UMLParameter> getParameterList() {
        return parameterList;
    }

    public boolean isStatic() {
        return _static;
    }

    public void setStatic() {
        this._static = true;
    }

    public boolean isAbstract() {
        return _abstract;
    }

    public void setAbstract() {
        this._abstract = true;
    }
}
