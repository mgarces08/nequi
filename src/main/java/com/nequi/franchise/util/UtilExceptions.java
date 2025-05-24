package com.nequi.franchise.util;

import org.apache.commons.lang3.exception.ExceptionUtils;

public class UtilExceptions {

    private UtilExceptions() { }

    /**
     * Check if the class exists inside the chain.
     *
     * @param throwable the throwable
     * @param clazz     the clazz
     * @return true if exists, false if not
     */
    public static boolean existsInChain(Throwable throwable, Class<? extends Throwable> clazz) {
        return ExceptionUtils.indexOfType(throwable, clazz) != -1;
    }

    private static int indexOf(Throwable throwable, Class<? extends Throwable> clazz) {
        return ExceptionUtils.indexOfType(throwable, clazz);
    }

    /**
     * If the class exists inside the chain, return the Throwable related this class. If not, the throwable.
     *
     * @param throwable the throwable
     * @param clazz     the clazz
     * @return the Throwable
     */
    public static Throwable getFromChain(Throwable throwable, Class<? extends Throwable> clazz) {
        return existsInChain(throwable, clazz) ? ExceptionUtils.getThrowables(throwable)[indexOf(throwable, clazz)]
                : throwable;
    }
}