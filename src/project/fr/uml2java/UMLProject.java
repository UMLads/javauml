package fr.uml2java;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import static java.lang.Math.*;


public class UMLProject extends UMLObject {
    private int mat_size;
    private List<UMLObject> ownedElements = new ArrayList<>();

    private List<UMLClass> ownedClasses = new ArrayList<>();
    private List<UMLObjectView> ownedViewClasses = new ArrayList<>();
    private List<UMLObjectView> ownedViewClassesOrganised = new ArrayList<>();
    private List<UMLEdge>  ownedViewEdges = new ArrayList<>();
    private JSONObject jsonObject_h;

    public UMLProject(JSONObject jsonObject) {
        super(jsonObject);

        //Création des classes
        JSONArray ownedClass =jsonObject.getJSONArray("ownedElements").getJSONObject(0).getJSONArray("ownedElements");
        for (int i = 0; i < ownedClass.length(); ++i)
        {
            JSONObject elem = ownedClass.getJSONObject(i);
            if (elem.getString("_type").equals("UMLClass")){
                this.ownedClasses.add(new UMLClass(elem));
            }
        }

        //Incrémentation du nombre d'associations des classes visées (obsolète)
        for (UMLClass umlClass : ownedClasses ){
            if (umlClass.getAssociationTargets() != null)
            for (String ref : umlClass.getAssociationTargets()){
                for(UMLClass targetclass : ownedClasses){
                    if (ref.equals(targetclass.getId())){
                        targetclass.setNbassociations(targetclass.getNbassociations() + 1);
                    }
                }
            }
        }

        // Création des classViews
        JSONArray ownedViewClass = jsonObject.getJSONArray("ownedElements")
                .getJSONObject(0).getJSONArray("ownedElements").getJSONObject(0).getJSONArray("ownedViews");

        for (int i = 0; i < ownedViewClass.length(); ++i) {
            JSONObject elem = ownedViewClass.getJSONObject(i);
            if (elem.getString("_type").equals("UMLClassView")) {
                this.ownedViewClasses.add(new UMLObjectView(elem));
            }
        }

        this.jsonObject_h = jsonObject;
    }

    // Organiser le placement des classes sur le diagramme
    public void OrganiseDiagram (String file, UML2JavaTranslator uml2JavaTranslator) throws IOException {

        //Definition de la taille du plan
        this.mat_size = (this.ownedClasses.size() / 5) * 1000;

        //tri des classes en fonction de leur nombre d'associations (obsolète)
        Collections.sort(this.ownedClasses, UMLClass.ComparatornbAssociations);

        //Associations des classes avec les classesView
        linkNewClasses ();

        //Associations des liens des classes avec les liens des classesView
        linkTargetsClass();

        //Positionnement des classes aléatoirement
        randomDisplay (mat_size);

        //Création des associations
        createEdgesList();

        for (UMLObjectView umlObjectView : ownedViewClassesOrganised) {
            System.out.println(umlObjectView.getName() + " :! " + umlObjectView.getLeft() + " top : " + umlObjectView.getTop()); }

        // Optimisation du diagramme à l'aide de l'algorithme de fruchterman-reingold
        resolveUsingForceDirected();

        for (UMLObjectView umlObjectView : ownedViewClassesOrganised) {
            System.out.println(umlObjectView.getName() + " :! " + umlObjectView.getLeft() + " top : " + umlObjectView.getTop()); }

        //  modification du fichier de base  !NON FONCTIONNEL!
      /*  JSONArray ownedViewClass = uml2JavaTranslator.getJsonFile().getJSONArray("ownedElements")
                .getJSONObject(0).getJSONArray("ownedElements").getJSONObject(0).getJSONArray("ownedViews");

        for (int i = 0; i < ownedViewClass.length(); ++i) {
            JSONObject elem = ownedViewClass.getJSONObject(i);
            if (elem.getString("_type").equals("UMLClassView")) {
                for (UMLObjectView umlObjectView : this.ownedViewClassesOrganised){
                    if(elem.getString("_id").equals(umlObjectView.getId())){
                        uml2JavaTranslator.getJsonFile().getJSONArray("ownedElements")
                                .getJSONObject(0).getJSONArray("ownedElements").getJSONObject(0).getJSONArray("ownedViews").getJSONObject(i).remove("_left");
                        uml2JavaTranslator.getJsonFile().getJSONArray("ownedElements")
                                .getJSONObject(0).getJSONArray("ownedElements").getJSONObject(0).getJSONArray("ownedViews").getJSONObject(i).remove("_top");
                        uml2JavaTranslator.getJsonFile().getJSONArray("ownedElements")
                                .getJSONObject(0).getJSONArray("ownedElements").getJSONObject(0).getJSONArray("ownedViews").getJSONObject(i).put("_left", umlObjectView.getLeft());
                        uml2JavaTranslator.getJsonFile().getJSONArray("ownedElements")
                                .getJSONObject(0).getJSONArray("ownedElements").getJSONObject(0).getJSONArray("ownedViews").getJSONObject(i).put("_top", umlObjectView.getTop());
                        //System.out.println("fait");
                    }
                }
               }
        }
        FileWriter writer = new FileWriter(file);
        writer.write(uml2JavaTranslator.getJsonFile().toString());
        writer.close();
*/
    }

    // Sources des calculs utilisés
    // https://www.labri.fr/perso/bourqui/downloads/cours/Master/2019/TP2/TP_Force_Directed.pdf
    // https://www.youtube.com/watch?v=WWm-g2nLHds
    //Optimisation du diagramme à l'aide de l'algorithme de fruchterman-reingold
    public void resolveUsingForceDirected(){
        int t = 0; // iteration courante
        int max_iterations = 1; // nombre max d'iterations
        int length = 20;// taille minimale d'une association
        int temp = this.mat_size/10; // variable de refroidissement "cooling"
        int epsilon = 20;// déplacement maximum pour arrêter le programme
        int nombremaxforce = epsilon + 1;//nombre maximum courant de forces sur un sommet


        while (t < max_iterations && nombremaxforce > epsilon ){
            nombremaxforce = 0;
            int dx = 0; // force de deplacement horizontal
            int dy = 0; // force de deplacement vertical

            //attribution force repulsion
            for (UMLObjectView v : ownedViewClassesOrganised){
                for ( UMLObjectView u : this.ownedViewClassesOrganised){
                    if (!u.getId().equals(v.getId())){
                        dx = v.getLeft() - u.getLeft();
                        //if (v.getName().equals("Class5")) System.out.println(t + " dx : " + dx);
                        dy = v.getTop() - u.getTop();
                        //if (v.getName().equals("Class5")) System.out.println(t +" dy: " + dy);
                        int distance = dist(dx,dy);
                        if (distance != 0 && dx != 0 && dy != 0)
                        {
                            v.setDx(v.getDx() + (dx/abs(dx)) * fr(abs(dx),length));
                            //if (v.getName().equals("Class5")) System.out.println(t +" setDx: " + v.getDx());
                            v.setDy(v.getDy() + (dy/abs(dy)) * fr(abs(dy), length));
                            //if (v.getName().equals("Class5")) System.out.println(t + " setDy: " + v.getDy());
                        }
                    }
                }
            }
            // Attribution force attraction
            for (UMLEdge e : ownedViewEdges){
                 dx = e.getSrc().getLeft() - e.getDest().getLeft();
                 dy = e.getSrc().getTop() - e.getDest().getLeft();
                int distance = dist(dx,dy);
                if (distance != 0 && dx != 0 && dy != 0){
                    e.getSrc().setDx(e.getSrc().getDx() - (dx/abs(dx)) * (fa(abs(dx),length))/abs(dx));
                    //if (e.getSrc().getName().equals("Class5")) System.out.println(t + " setDxedge: " + e.getSrc().getDx());
                    e.getDest().setDx(e.getDest().getDx() + (dx/abs(dx)) * (fa(abs(dx), length))/abs(dx));
                    //if (e.getDest().getName().equals("Class5")) System.out.println(t + "setDxedge: " + e.getDest().getDx());
                    e.getSrc().setDy(e.getSrc().getDy() - (dy/abs(dy)) * (fa(abs(dy), length))/abs(dx));
                    //if (e.getSrc().getName().equals("Class5")) System.out.println(t + " setDyedge: " + e.getSrc().getDy());
                    e.getDest().setDy(e.getDest().getDy() + (dy/abs(dy)) * (fa(abs(dy),length))/abs(dx));
                    //if (e.getDest().getName().equals("Class5")) System.out.println( t +  " setDyedge: " + e.getDest().getDy());
                }
            }
            // Déplacement des sommets
            for (UMLObjectView v : ownedViewClassesOrganised){
                if (v.getDx() != 0 && v.getDy() != 0){
                    //System.out.println(v.getDx() + "," + t);
                    //System.out.println(v.getDy() + "," + t);
                    //Verification que les sommets ne sortent pas du cadre
                    v.setLeft(v.getLeft() + ((v.getDx()/ abs(v.getDx())) * min(v.getDx(), temp)));
                    v.setTop((v.getTop() + (v.getDy()/ abs(v.getDy())) * min (v.getDy(), temp)));
                    v.setLeft(min((mat_size), max(-(mat_size),v.getLeft())));
                    v.setTop(min((mat_size), max(-(mat_size),v.getTop())));}

               // System.out.println(v.getLeft() + " x ");

                // Attribution de nombremaxforce
                if (v.getDx() > nombremaxforce){ nombremaxforce = v.getDx();
                    System.out.println("nb " + nombremaxforce + " "+ t);}
                if (v.getDy() > nombremaxforce){ nombremaxforce = v.getDy();
                    System.out.println("nb " + nombremaxforce + " "+ t);}
            }
            //Fonction de refroidissement (non fonctionelle)
            temp -= - (temp / (t + 1) );

            t = t + 1;
        }
    }
    public int fa(int x, int k) { return ( (x*x)/(k*k));} // force d'attraction
    public int fr(int x, int k) { return ((k*k)/x);} // force de répulsion

    // Fonction pour calculer la distance entre deux points
    public int dist (int dx, int dy) {
        double distance = Math.sqrt((dx * dx) + (dy * dy));
        if (distance != 0){
            return (int) distance;
        }
        return 1;
    };

    //Création des associations
    public void createEdgesList (){

        for (UMLObjectView umlObjectView : ownedViewClassesOrganised){
            for (String str : umlObjectView.getAssociationTargetsFromMe()){
                for (UMLObjectView umlObjectView1 : ownedViewClassesOrganised){
                    if (str.equals(umlObjectView1.getId())){
                        UMLEdge edge = new UMLEdge(umlObjectView, umlObjectView1);
                        this.ownedViewEdges.add(edge);
                    }
                }
            }

        }
    }


    //Positionnement des classes aléatoirement
    public void randomDisplay (int mat_size){
        System.out.println(" MAT / " + mat_size);
        for (UMLObjectView umlObjectView : ownedViewClassesOrganised){
            double left =  10 + (Math.random() * (mat_size - 10));
            umlObjectView.setLeft((int) left);
            double top = 10 + (Math.random()* (mat_size - 10));
            umlObjectView.setTop((int) top);
        }
    }

    //Associations des classes avec les classesView
    public void linkNewClasses() {
        for (UMLClass umlClass : ownedClasses) {
            for (UMLObjectView umlObjectView : ownedViewClasses) {
                if (umlClass.getId().equals(umlObjectView.getModel())) {
                    umlObjectView.setName(umlClass.getName());
                    umlObjectView.setNbAssociations(umlClass.getNbassociations());
                    umlObjectView.setAssociationTargets(umlClass.getAssociationTargets());
                    this.ownedViewClassesOrganised.add(umlObjectView);
                }
            }
        }
    }

    //Associations des liens des classes avec les liens des classesView
    public void linkTargetsClass() {

        for (UMLObjectView umlObjectView : ownedViewClassesOrganised) {
            if (umlObjectView.getAssociationTargets() != null) {
                for (String target : umlObjectView.getAssociationTargets()) {
                    for (UMLObjectView umlObjectView2 : ownedViewClassesOrganised) {
                        if (target.equals(umlObjectView2.getModel())) {
                            umlObjectView2.addTrueAssociationTarget(umlObjectView.getId());
                            umlObjectView.addAssociationTargetFromme(umlObjectView2.getId());
                        }

                    }
                }
            }
        }
            for (UMLObjectView liens : ownedViewClassesOrganised) {

                for (String lien : liens.getTrueAssociationTargets()) {
                    liens.addAlllinks(lien);
                }
                for (String lien : liens.getAssociationTargetsFromMe()) {
                    liens.addAlllinks(lien);
                }

            }
    }


    public int getMat_size() {
        return mat_size;
    }

    public void setMat_size(int mat_size) {
        this.mat_size = mat_size;
    }

    public List<UMLClass> getOwnedClasses() {
        return ownedClasses;
    }

    public void setOwnedClasses(List<UMLClass> ownedClasses) {
        this.ownedClasses = ownedClasses;
    }

    public List<UMLObjectView> getOwnedViewClasses() {
        return ownedViewClasses;
    }

    public void setOwnedViewClasses(List<UMLObjectView> ownedViewClasses) {
        this.ownedViewClasses = ownedViewClasses;
    }

    public List<UMLObjectView> getOwnedViewClassesOrganised() {
        return ownedViewClassesOrganised;
    }

    public void setOwnedViewClassesOrganised(List<UMLObjectView> ownedViewClassesOrganised) {
        this.ownedViewClassesOrganised = ownedViewClassesOrganised;
    }

    public List<UMLEdge> getOwnedViewEdges() {
        return ownedViewEdges;
    }

    public void setOwnedViewEdges(List<UMLEdge> ownedViewEdges) {
        this.ownedViewEdges = ownedViewEdges;
    }

    public List<UMLObject> getOwnedElements() {
        return ownedElements;
    }

    public void setOwnedElements(List<UMLObject> ownedElements) {
        this.ownedElements = ownedElements;
    }

    @Override
    public String toString() {
        return "UMLProject{" +
                "id='" + getId() + '\'' +
                ", name='" + getName() + '\'' +
                ", ownedElements=" + ownedClasses +
                "}";
    }
}