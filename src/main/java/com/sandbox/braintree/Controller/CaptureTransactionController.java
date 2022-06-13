package com.sandbox.braintree.Controller;

import com.sandbox.braintree.Service.TransactionService;
import com.sandbox.braintree.model.PaymentMethodResponse;
import com.sandbox.braintree.model.TransactionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.sandbox.braintree.model.TransactionResponse;

@Controller
@RequestMapping(value = "/payment/v1/transaction")
public class CaptureTransactionController {
    @Autowired
    TransactionService transactionService;

    @PostMapping(value = "/capture")
    @ResponseBody
    public TransactionResponse captureTransaction(@RequestParam("transaction_id") String transactionId) {

        TransactionResponse response = transactionService.captureTransaction(transactionId);
        return response;
    }


}

