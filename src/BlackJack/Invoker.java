package BlackJack;

import java.util.List;

/***
 * Invoker Class
 * This class is used to represent the invoker
 * This class is the invoker in the command pattern
 * Anson Graumann
 */
public class Invoker {
    private Command command;

    //executes the commands
    public void executeCommands() {
       command.execute();
    }

    //adds the command
    public void addCommand(Command command) {
        this.command = command;
    }

    //removes the command
    public void removeCommand(Command command) {
        this.command = null;
    }
}
