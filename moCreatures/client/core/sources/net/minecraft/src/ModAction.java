// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.src;

import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ModAction
    implements Runnable
{

    public ModAction(String s, Class aclass[])
    {
        handlerObjects = new ArrayList();
        handlerMethods = new ArrayList();
        name = s;
        params = aclass;
    }

    public ModAction(Object obj, String s, Class aclass[])
    {
        handlerObjects = new ArrayList();
        handlerMethods = new ArrayList();
        name = s;
        params = aclass;
        addHandler(obj, s);
    }

    public ModAction(Object obj, String s, String s1, Class aclass[])
    {
        handlerObjects = new ArrayList();
        handlerMethods = new ArrayList();
        name = s1;
        params = aclass;
        addHandler(obj, s);
    }

    public ModAction(Object obj, String s, Object obj1, Class aclass[])
    {
        handlerObjects = new ArrayList();
        handlerMethods = new ArrayList();
        name = s;
        data = obj1;
        params = aclass;
        addHandler(obj, s);
    }

    public ModAction addHandler(Object obj, String s)
    {
        Method method;
        try
        {
            method = GetMethodRecursively(obj, s);
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
            throw new RuntimeException("invalid method parameters");
        }
        for(int i = 0; i < handlerObjects.size(); i++)
        {
            Object obj1 = handlerObjects.get(i);
            String s1 = (String)handlerMethods.get(i);
            if(obj1 == obj && s1.equals(s))
            {
                System.err.println(String.format("WARNING: event handler is already registered: %s: %s.%s", new Object[] {
                    name, obj.getClass().getName(), s
                }));
                return this;
            }
        }

        handlerObjects.add(obj);
        handlerMethods.add(s);
        return this;
    }

    public Object[] call(Object aobj[])
    {
        Object aobj1[] = new Object[handlerObjects.size()];
        for(int i = 0; i < handlerObjects.size(); i++)
        {
            Object obj = handlerObjects.get(i);
            String s = (String)handlerMethods.get(i);
            try
            {
                Method method = GetMethodRecursively(obj, s);
                aobj1[i] = method.invoke((obj instanceof Class) ? null : obj, aobj);
            }
            catch(Exception exception)
            {
                exception.printStackTrace();
                throw new RuntimeException("error calling callback");
            }
        }

        return aobj1;
    }

    public boolean hasHandler(Object obj, String s)
    {
        for(int i = 0; i < handlerObjects.size(); i++)
        {
            Object obj1 = handlerObjects.get(i);
            String s1 = (String)handlerMethods.get(i);
            if(obj1 == obj && s1.equals(s))
            {
                return true;
            }
        }

        return false;
    }

    public boolean removeHandler(Object obj, String s)
    {
        for(int i = 0; i < handlerObjects.size(); i++)
        {
            Object obj1 = handlerObjects.get(i);
            String s1 = (String)handlerMethods.get(i);
            if(obj1 == obj && s1.equals(s))
            {
                handlerObjects.remove(i);
                handlerMethods.remove(i);
                return true;
            }
        }

        return false;
    }

    Method GetMethodRecursively(Object obj, String s)
    {
        Class class1 = (obj instanceof Class) ? (Class)obj : obj.getClass();
        do
        {
            if(class1 == null)
            {
                return null;
            }
            try
            {
                Method method = class1.getDeclaredMethod(s, params);
                if(method != null)
                {
                    method.setAccessible(true);
                    return method;
                }
            }
            catch(Throwable throwable) { }
            class1 = class1.getSuperclass();
        } while(true);
    }

    public void run()
    {
        call(new Object[0]);
    }

    protected List handlerObjects;
    protected List handlerMethods;
    protected Class params[];
    public String name;
    public Object data;
}
