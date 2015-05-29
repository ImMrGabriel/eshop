package com.gabriel.eshop.dao.impl.jdbc.tx;

public interface TransactionManager {

    /**
     * The method gets a piece of work which should be done in a transaction.
     * @param unitOfWork it is a generic parameter that specifies the return class and
     *                   the exception for unwanted behavior.
     * @param <T>
     * @param <E>
     * @return <T>(<never return NULL!)
     * @throws E
     */
    public <T, E extends Exception> T doInTransaction(UnitOfWork<T, E> unitOfWork) throws E;

}
