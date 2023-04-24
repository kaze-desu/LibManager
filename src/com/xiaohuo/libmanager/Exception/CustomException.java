package com.xiaohuo.libmanager.Exception;

import java.util.ArrayList;
import java.util.List;

/**
 * CustomException is a class that extends Exception
 * It is used to throw multiple exceptions at the same time
 * It is used in AddService.java
 * @see com.xiaohuo.libmanager.Services.AddService
 * @author xiaohuo(WANG BOYUN)
 */
public class CustomException extends Exception
{
    private List<Throwable> exceptions = new ArrayList<>();

    public CustomException(List<? extends Throwable> exception)
    {
        exceptions.addAll(exception);
    }
    public List<Throwable>getExceptions()
    {
        return exceptions;
    }
}
