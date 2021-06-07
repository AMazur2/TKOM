package Parser;

import java.util.HashMap;

public class Scope {

    private Scope parent;
    private HashMap<String, Variable> variables;

    public Scope()
    {
        this.parent = null;
        this.variables = new HashMap<>();
    }

    public Scope(Scope p)
    {
        this.parent = p;
        this.variables = new HashMap<>();
    }

    public boolean inScope(String name)
    {
        return variables.containsKey(name) || (parent != null && parent.inScope(name));
    }

    public void putVariable(String name, Variable var) throws Exception {
        if(inScope(name))
            throw new Exception("Powtorzona deklaracja zmiennej : (Nazwa :" + name + " Zmienna: " + var.getInfo() +" )" );
        variables.put(name, var);
    }

    public void setValue(String name, Object value) throws Exception {
        if(!inScope(name))
            throw new Exception("Niezdefiniowano zmiennej o nazwie: " + name);

        Scope current = this;
        while(current != null && current.variables.get(name) == null)
            current = current.parent;

        current.variables.get(name).setValue(checkValueType(current.variables.get(name).getVt(), value));
    }

    private Object checkValueType(VariableType vt, Object value) throws Exception {
        switch(vt)
        {
            case INT:
                if(!(value instanceof Integer))
                    throw new Exception("Nie mozna zrzutowac zmiennej typu : " + value.getClass() + " na Integer");
                return value;
            case BOOL:
                if(!(value instanceof  Boolean))
                    throw new Exception("Nie mozna zrzutowac zmiennej typu : " + value.getClass() + " na Boolean");
                return value;
            case STRING:
                return value.toString();
            case CLASS:
                if(!(value instanceof HashMap))
                    throw new Exception("Nie mozna zrzutowac zmiennej typu : " + value.getClass() + " na HashMap");
                return value;
        }
        return null;
    }

    public Object getValue(String name) throws Exception {
        if(!inScope(name))
            throw new Exception("Niezdefiniowano zmiennej o nazwie : " + name );
        Scope current = this;
        while( current != null && current.variables.get(name) == null )
                current = current.parent;
        return current.variables.get(name).getValue();
    }

    public Variable getVariable(String name) throws Exception {
        if(!inScope(name))
            throw new Exception("Niezdefiniowano zmiennej o nazwie: " + name);

        Scope current = this;
        while(current != null && current.variables.get(name) == null)
            current = current.parent;

        return current.variables.get(name);
    }

    public HashMap<String, Variable> getVariables() {
        return variables;
    }
}