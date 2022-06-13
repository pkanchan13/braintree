package com.sandbox.braintree.Service;

import com.sandbox.braintree.model.PaymentEntity;
import com.sandbox.braintree.model.PaymentMethodResponse;
import com.sandbox.braintree.model.TransactionEntity;


public interface InitializeService {
    PaymentMethodResponse onboard(PaymentEntity payment, String nonce, Integer id);

    PaymentMethodResponse addCustomer(PaymentEntity payment);

    PaymentMethodResponse deleteCustomer(Integer id);

    PaymentMethodResponse seachCustomer(Integer cus_id);
}
