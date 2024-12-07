package Slots;

public class GameSelectionCommand implements Command {
    private SlotMachinesTemplate machine;
    private SlotMachineUI ui;
    private MessageManager messageManager;

    public GameSelectionCommand(SlotMachinesTemplate machine, SlotMachineUI ui, MessageManager messageManager) {
        this.machine = machine;
        this.ui = ui;
        this.messageManager = messageManager;
    }

    @Override
    public void execute() {
        ui.setMachine(machine); // Set the selected machine in the UI
    }
}
