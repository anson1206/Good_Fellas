package Slots;

public class SlotMachineSingleton {
    private static SlotMachineSingleton instance;

    private SlotMachineSingleton(){
    }

    public static SlotMachineSingleton getInstance(){
        if (instance == null){
            instance = new SlotMachineSingleton();
        }
        return instance;
    }

    public void playSlotMachine(SlotMachinesTemplate slotMachinesTemplate){
        slotMachinesTemplate.getGameName();
        slotMachinesTemplate.play();
    }


}