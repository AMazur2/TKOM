package uni.projects.parser;

import java.util.HashMap;

public class ObjectVariable extends Variable {

    private final String className;

    public ObjectVariable(VariableType vt, String cn, HashMap<String, Variable> ca) {
        super(vt, ca);
        this.className = cn;
    }

    public String getClassName() {
        return this.className;
    }


}
