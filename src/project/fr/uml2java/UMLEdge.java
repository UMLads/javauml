package fr.uml2java;

public class UMLEdge {
    private UMLObjectView src;
    private UMLObjectView dest;

    public UMLEdge(UMLObjectView src, UMLObjectView dest) {
        this.src = src;
        this.dest = dest;
    }

    public UMLEdge(){}

    public UMLObjectView getSrc() {
        return src;
    }

    public void setSrc(UMLObjectView src) {
        this.src = src;
    }

    public UMLObjectView getDest() {
        return dest;
    }

    public void setDest(UMLObjectView dest) {
        this.dest = dest;
    }
}
