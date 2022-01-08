package me.kolterdyx.game42editor.components.controller;

import me.kolterdyx.game42editor.components.enums.Instruction;
import me.kolterdyx.game42editor.components.utils.Counter;

public class Function {

    public final int size;
    public final int id;
    public Instruction[] instructions;

    public Function(int size){
        this.size = size;
        this.id = Counter.getFunctionId();
        this.instructions = new Instruction[size];
    }

    public void setInstruction(int index, Instruction instruction){
        instructions[index] = instruction;
    }

    public Instruction[] getInstructions(){
        return instructions;
    }

}
