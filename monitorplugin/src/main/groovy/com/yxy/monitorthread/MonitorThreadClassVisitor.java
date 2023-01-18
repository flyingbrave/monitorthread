package com.yxy.monitorthread;

import static com.yxy.monitorthread.ClassConstant.S_ProxyThread;
import static com.yxy.monitorthread.ClassConstant.S_Thread;

import com.android.ddmlib.Log;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class MonitorThreadClassVisitor extends ClassVisitor implements Opcodes{
    private String className;
    private boolean changingSuper = false; // 是否处于改继承状态
    private boolean buildingPackage = false; // 是否处于建立用户可达代码包列表中
    private String jarName = null; // 不是jar包则为空
    public MonitorThreadClassVisitor(ClassVisitor cv, String jarName) {
        super(Opcodes.ASM6, cv);
        this.jarName = jarName;
        Log.i("tag5","MonitorThreadClassVisitor");
        System.out.println("MonitorThreadClassVisitor");
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        changingSuper = false;
        buildingPackage = false;
        className = name;
        Log.i("tag5","MonitorThreadClassVisitor visit ");
        System.out.println("MonitorThreadClassVisitor  visit");
        if (filterClass(className)) {
            super.visit(version, access, name, signature, superName, interfaces);
            return;
        }
        if (!buildingPackage && (access & Opcodes.ACC_SUPER) > 0) {
            switch (superName) {
                case S_Thread:
                    changingSuper = true;
                    Log.i("tag5","MonitorThreadClassVisitor visit 222");
                    System.out.println("MonitorThreadClassVisitor  visit 222");
                    super.visit(version, access, name, signature, S_ProxyThread, interfaces);
                    return;

            }
        }
        super.visit(version, access, name, signature, superName, interfaces);

    }
    @Override
    public MethodVisitor visitMethod(int access0, String name0, String desc0, String signature0, String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access0, name0, desc0, signature0, exceptions);
        if (filterClass(className)) {
            return mv;
        }

        if (changingSuper) { // 改继承
            mv = new ChangeSuperMethodVisitor(ASM6, mv, className);
        } else {
            if (buildingPackage && name0.equals("buildPackageList")) {
                mv = new AddPackageMethodVisitor(ASM6, mv);
            } else {
                mv = new ChangeProxyMethodVisitor(ASM6, mv, className);
            }
        }

        return mv;
    }
    private boolean filterClass(String className) {
        return className.contains("com/yxy/monitormodel/proxy");
    }
}
