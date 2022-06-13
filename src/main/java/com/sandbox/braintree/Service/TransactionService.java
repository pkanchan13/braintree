package com.sandbox.braintree.Service;

import com.sandbox.braintree.model.PaymentMethodResponse;
import com.sandbox.braintree.model.TransactionEntity;
import com.sandbox.braintree.model.TransactionResponse;


public interface TransactionService {

    PaymentMethodResponse addTransaction(TransactionEntity transactions, Integer cus_id, String settlement);

    TransactionResponse captureTransaction(String transactionId);

    TransactionResponse refundTransaction(String transactionId);

    TransactionResponse voidTransaction(String transactionId);
}
