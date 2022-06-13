package com.sandbox.braintree.Controller;

import com.sandbox.braintree.Service.TransactionService;
import com.sandbox.braintree.model.TransactionEntity;
import com.sandbox.braintree.model.TransactionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping(value = "/payment/v1/transaction")
public class VoidTransactionController {
    @Autowired
    TransactionService transactionService;

    @PostMapping(value = "/void")
    @ResponseBody
    public TransactionResponse voidTransaction(@RequestParam("transaction_id") String transactionId) {

        TransactionResponse response = transactionService.voidTransaction(transactionId);
        return response;
    }


}