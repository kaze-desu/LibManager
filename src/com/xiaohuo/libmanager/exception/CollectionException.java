package com.xiaohuo.libmanager.exception;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a function for collecting exceptions.
 * It can collect all the exceptions and throw them together, I thought it would be useful in sometimes (Like when you need to solve a lot of exceptions in a loop), so I wrote it.
 * @author xiaohuo(WANG BOYUN)
 */
public class CollectionException extends Exception
{
    private final List<Throwable> exceptions = new ArrayList<>();

    public CollectionException(List<? extends Throwable> exception)
    {
        exceptions.addAll(exception);
    }
    public List<Throwable>getExceptions()
    {
        return exceptions;
    }
}
