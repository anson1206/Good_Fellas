package Slots;

public class README {
    /*
    The Slot Machine game includes two slot machine themes: the Wild West Slot Machine with symbols like
Horse, Sheriff, and Bandit, and the Superhero Slot Machine with symbols like Batman, Superman, and Ironman.
Each machine has its own betting limits and winning multipliers to make the gameplay more interesting.

    The design uses three key design patterns: Observer, Singleton, and Template. The Observer Pattern is
used to keep the user interface updated whenever the game state changes, such as the result of the spin or the
player's chip balance changes. The Singleton Pattern ensures there is only one instance of the slot machine
UI, so the game state remains consistent and there’s no duplication. The Template Pattern is used in the
SlotMachinesTemplate class, which provides a basic structure for creating different slot machine types
while allowing customization like symbols and win multipliers. This is how the different games Superhero
and Wild West maintain similar functionality with different outcomes, specifications, and reels.

     The game also includes a sound for spinning reels to make it more engaging. Players can
place bets, spin the reels, and see their chip balance update in real time. The code follows SOLID
principles, particularly the Liskov Substitution Principle (LSP) by ensuring that all slot machine
subclasses work correctly when used in place of the parent class. It also respects the Interface
Segregation Principle (ISP) by keeping each class focused on its specific role, avoiding unnecessary
responsibilities.


     */
}
