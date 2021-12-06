package com.yxy.monitorthread;


import static com.yxy.monitorthread.ClassConstant.S_ProxyThread;
import static com.yxy.monitorthread.ClassConstant.S_Thread;

import org.objectweb.asm.MethodVisitor;


public class ChangeSuperMethodVisitor extends MethodVisitor {
    private String className;

    ChangeSuperMethodVisitor(int api, MethodVisitor methodVisitor, String className) {
        super(api, methodVisitor);
        this.className = className;
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
        if (name.equalsIgnoreCase("<init>")) {
            switch (owner) {
                case S_Thread:
                    mv.visitMethodInsn(opcode, S_ProxyThread, name, descriptor, false);
                    return;
            }
        }
        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
    }
}
