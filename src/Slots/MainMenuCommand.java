package Slots;

public class MainMenuCommand implements Command {
    private SlotMachineUI slotMachineUI;

    public MainMenuCommand(SlotMachineUI slotMachineUI) {
        this.slotMachineUI = slotMachineUI;
    }

    @Override
    public void execute() {
        // Return to the main menu
        slotMachineUI.returnToMainMenu();
    }
}
