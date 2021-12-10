package fr.uml2java;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class UMLObjectView extends UMLObject {
    private int left;
    private int right;
    private int top;
    private int height;
    private int width;
    private String model;
    private int nbAssociations;
    private int dx;
    private int dy;
    private List<String> associationTargets = new ArrayList<>();
    private List<String> trueAssociationTargets  = new ArrayList<>();
    private List<String> associationTargetsFromMe  = new ArrayList<>();
    private List <String> alllinks  = new ArrayList<>();

    public UMLObjectView () {}

    public UMLObjectView(JSONObject jsonObject) {
        super(jsonObject);
        if (jsonObject.has("model")){
            this.model = jsonObject.getJSONObject("model").getString("$ref");
        }
        if (jsonObject.has("left")) {
            this.left = jsonObject.getInt("left");
        }
        if (jsonObject.has("right")) {
            this.right = jsonObject.getInt("right");
        }
        if (jsonObject.has("top")) {
            this.top = jsonObject.getInt("top");
        }
        if (jsonObject.has("height")) {
            this.height = jsonObject.getInt("height");
        }
        if (jsonObject.has("width")) {
            this.width = jsonObject.getInt("width");
        }
    }

        @Override
        public String toString() {
            return "UMLObjectView {"+
                    /*"_id='" + getId() + '\'' +
                    ", parentRef'" + getParentRef() + '\'' +
                    ", name='" + getName() + '\'' +
                    ", visibility='" + getVisibility() + '\'' +
                    ", left ='" + left + '\'' +
                    ", left ='" + right + '\'' +
                    ", left ='" + top + '\'' +
                    ", left ='" + height + '\'' +
                    ", left ='" + width + '\'' */
                    ", name='" + getName() + '\'' +
                    trueAssociationTargets + associationTargetsFromMe + nbAssociations + alllinks +
                    '}'; }


    public int getDx() {
        return dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public int getDy() {
        return dy;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public List<String> getAlllinks() {
        return alllinks;
    }

    public void setAlllinks(List<String> alllinks) {
        this.alllinks = alllinks;
    }

    public List<String> getAssociationTargetsFromMe() {
        return associationTargetsFromMe;
    }

    public void setAssociationTargetsFromMe(List<String> associationTargetsFromMe) {
        this.associationTargetsFromMe = associationTargetsFromMe;
    }

    public List<String> getTrueAssociationTargets() {
        return trueAssociationTargets;
    }

    public void setTrueAssociationTargets(List<String> trueAssociationTargets) {
        this.trueAssociationTargets = trueAssociationTargets;
    }

    public List<String> getAssociationTargets() {
        return associationTargets;
    }

    public void setAssociationTargets(List<String> associationTargets) {
        this.associationTargets = associationTargets;
    }

    public int getNbAssociations() {
        return nbAssociations;
    }

    public void setNbAssociations(int nbAssociations) {
        this.nbAssociations = nbAssociations;
    }


    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void addTrueAssociationTarget(String id) {

        if (id != null) this.trueAssociationTargets.add(id);
    }

    public void addAssociationTargetFromme(String id) {

        this.associationTargetsFromMe.add(id);
    }

    public void addAlllinks(String id) {

        this.alllinks.add(id);
    }

}

