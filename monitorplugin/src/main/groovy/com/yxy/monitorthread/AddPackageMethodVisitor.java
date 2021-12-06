package com.yxy.monitorthread;


import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class AddPackageMethodVisitor extends MethodVisitor {

    AddPackageMethodVisitor(int api, MethodVisitor methodVisitor) {
        super(api, methodVisitor);
    }

    @Override
    public void visitInsn(int opcode) {
        super.visitInsn(opcode);
    }
}
