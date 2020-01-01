package com.example.recipe_q.model;

public class Direction {
    private String mInstructionOriginal;

    Direction(
            String instruction
    ) {
        mInstructionOriginal = instruction;
    }

    public String getInstructionOriginal() { return mInstructionOriginal; }
    public void setInstructionOriginal(String instructionOriginal) {
        this.mInstructionOriginal = instructionOriginal;
    }
}
