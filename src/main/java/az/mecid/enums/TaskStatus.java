package az.mecid.enums;

public enum TaskStatus {

    Done("Done"),
    Not_started("Not started"),
    In_work("In work"),
    Revision("Revision"),
    Testing("Testing");


    TaskStatus(String str){
        text=str;
    }
    public  String text;

    public String getText() {
        return text;
    }

}