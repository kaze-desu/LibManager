package com.xiaohuo.libmanager.Exception;

import java.util.ArrayList;
import java.util.List;

public class CustomException extends Exception
{
    private List<Throwable> exceptions = new ArrayList<Throwable>();

    public CustomException(List<? extends Throwable> exception)
    {
        exceptions.addAll(exception);
    }
    public List<Throwable>getExceptions()
    {
        return exceptions;
    }
}
