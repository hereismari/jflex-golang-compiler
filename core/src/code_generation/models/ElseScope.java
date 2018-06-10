package code_generation.models;

public class ElseScope {

    public Integer label;
    public String code;

    public ElseScope(Integer label){
        this.label = label;
        this.code = "";
    }

    public String getCode(Integer currentLabel) {
        String actualCode =  label + ": BR #" + currentLabel + "\n";
        actualCode += code;
        return actualCode;
    }

}