package rain.designpattern.command;

public class Control {
    Command[] onCommands;
    Command[] offCommands;

    public Control(Command[] onCommands, Command[] offCommands) {
        this.onCommands = onCommands;
        this.offCommands = offCommands;
    }

    public void pushOnButton(int slot) {
        onCommands[slot].execute();
    }

    public void pushOffButton(int slot) {
        offCommands[slot].execute();
    }

    public static void main(String[] args) {
        Command[] onCommands = {new LightOnCommand(new Light())};
        Command[] offCommands = {new LightOffCommand(new Light())};

        Control control = new Control(onCommands, offCommands);
        control.pushOnButton(0);
        control.pushOffButton(0);
    }
}
