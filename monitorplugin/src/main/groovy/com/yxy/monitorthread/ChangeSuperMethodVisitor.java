package com.yxy.monitorthread;


import static com.yxy.monitorthread.ClassConstant.S_TBaseThread;
import static com.yxy.monitorthread.ClassConstant.S_Thread;

import com.android.ddmlib.Log;

import org.objectweb.asm.MethodVisitor;


public class ChangeSuperMethodVisitor extends MethodVisitor {
    private String className;

    ChangeSuperMethodVisitor(int api, MethodVisitor methodVisitor, String className) {
        super(api, methodVisitor);
        this.className = className;
        Log.i("tag5","ChangeSuperMethodVisitor");
        System.out.println("ChangeSuperMethodVisitor");
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
        if (name.equalsIgnoreCase("<init>")) {
            switch (owner) {
                case S_Thread:
                    Log.i("tag5","visitMethodInsn 改继承");
                    System.out.println("visitMethodInsn 改继承");
                    mv.visitMethodInsn(opcode, S_TBaseThread, name, descriptor, false);
                    return;
            }
        }
        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
    }
}
