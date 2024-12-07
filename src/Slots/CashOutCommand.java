package Slots;

public class CashOutCommand implements Command {
    private MessageManager messageManager;
    private SlotMachinesTemplate machine;

    public CashOutCommand(SlotMachinesTemplate machine, MessageManager messageManager) {
        this.machine = machine;
        this.messageManager = messageManager;
    }

    @Override
    public void execute() {
        double balance = machine.balance; // Get the current balance from the machine

        // Notify the user via the MessageManager
        if (balance > 0) {
            messageManager.setMessage("You cashed out with $" + balance + ". Thanks for playing!");
        } else if (balance == 0) {
            messageManager.setMessage("You have no money left to cash out. Better luck next time!");
        } else {
            messageManager.setMessage("An error occurred while cashing out. Please try again.");
        }

        // Reset balance and notify the MessageManager
        machine.balance = 0; // Reset balance after cashing out
        messageManager.setMessage("Balance: $0.0"); // Notify the balance reset
    }
}
