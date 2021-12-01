package cli;

public class WrongInputException extends Exception{
    private final String message;

    public WrongInputException(String message){
        this.message=message;
    }

    public void printIssue(){
        System.err.println(message);
    }

}
