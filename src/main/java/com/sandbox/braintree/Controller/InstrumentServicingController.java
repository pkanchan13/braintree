package com.sandbox.braintree.Controller;

import com.sandbox.braintree.Service.InitializeService;
import com.sandbox.braintree.model.PaymentMethodResponse;
import com.sandbox.braintree.model.TransactionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/payment/v1/instrument")
public class InstrumentServicingController {
    @Autowired
    private InitializeService initializeService;

    @DeleteMapping(value = "/delete")
    @ResponseBody
    public PaymentMethodResponse delete(@RequestParam("cus_id") Integer cus_id) {

        PaymentMethodResponse response = initializeService.deleteCustomer(cus_id);
        return response;
    }

    @GetMapping(value = "/find")
    @ResponseBody
    public PaymentMethodResponse Search(@RequestParam("cus_id") Integer cus_id) {
        TransactionEntity transactionEntity = new TransactionEntity();
        PaymentMethodResponse response = initializeService.seachCustomer(cus_id);
        return response;
    }
}
