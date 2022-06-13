package com.sandbox.braintree.Controller;

import com.sandbox.braintree.Service.TransactionService;
import com.sandbox.braintree.model.PaymentMethodResponse;
import com.sandbox.braintree.model.TransactionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/payment/v1/transaction")
public class InitiateTransactionController {
    @Autowired
    TransactionService transactionService;

    @PostMapping(value = "/init")
    @ResponseBody
    public PaymentMethodResponse addTransaction(@RequestBody TransactionEntity transactionEntity, @RequestParam("cus_id") Integer cus_id, @RequestParam("settlement") String settlement) {

        PaymentMethodResponse response = transactionService.addTransaction(transactionEntity, cus_id, settlement);
        return response;
    }


}
