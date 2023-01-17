package com.yxy.monitorthread;


import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import static com.yxy.monitorthread.ClassConstant.S_ProxyThread;
import static com.yxy.monitorthread.ClassConstant.S_Thread;

import com.android.ddmlib.Log;


public class ChangeProxyMethodVisitor extends MethodVisitor {
    private String className;

    ChangeProxyMethodVisitor(int api, MethodVisitor methodVisitor, String className) {
        super(api, methodVisitor);
        this.className = className;
        Log.i("tag5","ChangeProxyMethodVisitor");
        System.out.println("ChangeProxyMethodVisitor");
    }

    @Override
    public void visitTypeInsn(int opcode, String type) {
        if (opcode == Opcodes.NEW) {
            switch (type) {
                case S_Thread:
                    mv.visitTypeInsn(Opcodes.NEW, S_ProxyThread);
                    return;
            }
        }
        super.visitTypeInsn(opcode, type);
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
        if (owner.equals(S_Thread) && name.equalsIgnoreCase("<init>")) {
            mv.visitMethodInsn(opcode, S_ProxyThread, name, descriptor, false);
            return;
        }
        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
    }
}
