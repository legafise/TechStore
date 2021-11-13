package by.lashkevich.logic.service;

import by.lashkevich.logic.dao.DaoException;
import by.lashkevich.logic.dao.transaction.TransactionFactory;
import by.lashkevich.logic.service.impl.JWDGoodService;

public enum ServiceFactory {
    GOOD_SERVICE() {
        @Override
        public Service<?> createService() throws ServiceException {
            try {
                GoodService goodService = new JWDGoodService();
                TransactionFactory factory = new TransactionFactory();
                goodService.setTransaction(factory.createTransaction());
                goodService.setTransactionFactory(factory);
                return goodService;
            } catch (DaoException e) {
                throw new ServiceException(e.getMessage());
            }
        }
    };

    private TransactionFactory factory;

    public abstract Service<?> createService() throws ServiceException;
}