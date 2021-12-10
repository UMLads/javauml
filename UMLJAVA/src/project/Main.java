import fr.uml2java.*;

import java.io.FileWriter;
import java.sql.SQLOutput;

public class Main {
    public static void main(String[] args) {

        try {
            String file = "UMLTestFiles/Eltest.mdj"; // Write here which file to organise
            UML2JavaTranslator uml2JavaTranslator =
                    new UML2JavaTranslator(file);
            uml2JavaTranslator.translate();
            System.out.println(uml2JavaTranslator.getProject() + "\n");
            uml2JavaTranslator.getProject().OrganiseDiagram(file, uml2JavaTranslator);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
