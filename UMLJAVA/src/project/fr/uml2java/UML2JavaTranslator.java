package fr.uml2java;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.*;

public class UML2JavaTranslator {
    private FileReader fileReader; // reader to extract .mdj file content
    private JSONObject jsonFile; // .mdj file to translate
    private UMLProject project; // .mdj file interpreted (refer to the uml class diagram of the .mdj file structure
    private FileWriter fileWriter; // writer to translate to java

    public UML2JavaTranslator(String file) {
        try {
            fileReader = new FileReader(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * whole .mdj file converted into a string then into a JSONObject
     * @throws IOException
     */
    public void getFile() throws IOException {
        StringBuilder parsable = new StringBuilder();
        int r;
        while ((r = fileReader.read()) != -1) {

            parsable.append((char) r);
        }
        jsonFile = new JSONObject(parsable.toString());
    }

    /*
    Parsing from JSON to make Objects to translate into Java
     */
    public void translate() {
        try {
            this.getFile();
            this.project = new UMLProject(jsonFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JSONObject getJsonFile() {
        return jsonFile;
    }

    public UMLProject getProject() {
        return project;
    }
}