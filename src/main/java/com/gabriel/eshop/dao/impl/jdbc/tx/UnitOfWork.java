package com.gabriel.eshop.dao.impl.jdbc.tx;

/**
 * <p>Maintains a list of objects affected by a business transaction and
 * coordinates the writing out of changes and the resolution of concurrency problems.</p>
 *
 * <p>When you're pulling data in and out of a database, it's important to keep track of what you've changed;
 * otherwise, that data won't be written back into the database. Similarly you have to insert new objects
 * you create and remove any objects you delete.</p>
 *
 * <p>A Unit of Work keeps track of everything you do during a business transaction that
 * can affect the database. When you're done, it figures out everything that needs
 * to be done to alter the database as a result of your work.</p>
 *
 * <a href="http://martinfowler.com/eaaCatalog/unitOfWork.html">http://martinfowler.com/eaaCatalog/unitOfWork.html</a>
 * <a href="http://design-pattern.ru/patterns/unit-of-work.html">http://design-pattern.ru/patterns/unit-of-work.html</a>
 * @param <T>   returned object
 * @param <E>   thrown exception
 */
public interface UnitOfWork<T, E extends Exception> {

    /**
     * Execution of a specified business transaction
     * @return T
     * @throws E
     */
    public T doInTx() throws E;
}
