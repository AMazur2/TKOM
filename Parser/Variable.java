package Parser;

public class Variable {

    private VariableType vt;
    private Object value;

    public Variable(VariableType vt, Object v)
    {
        this.vt = vt;
        this.value = v;
    }

    public VariableType getVt() { return this.vt; }

    public void setVt( VariableType vt ) { this.vt = vt; }

    public Object getValue() { return this.value; }

    public void setValue(Object v) { this.value = v; }

    public String getInfo()
    {
        String s = "";

        if(this.vt != null)
            s = s.concat("Typ: " + vt.toString());
        if(this.value != null)
            s = s.concat(" Value: " + value.toString());
        return s;
    }
}
