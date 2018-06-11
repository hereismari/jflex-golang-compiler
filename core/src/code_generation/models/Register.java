package code_generation.models;


public enum Register {
    R1("R1"), R2("R2"), R3("R3"), R4("R4"), R5("R5"), R6("R6"), R7("R7"),
    R8("R8"), R9("R9"), R10("R10"), R11("R11"), R12("R12") ,R13("R13"),
    R14("R14"), R15("R15") , R16("R16"), R17("R17"), R18("R18"), R19("R19"),
    SP("SP"), _SP("*0(SP)"), SP0("0(SP)");

    private String value;

    Register(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String toString(){
        return value;
    }
}