package com.sandbox.braintree.Controller;


import com.sandbox.braintree.Config.BraintreeGatewayConfig;
import com.sandbox.braintree.Service.InitializeService;
import com.sandbox.braintree.model.PaymentEntity;
import com.sandbox.braintree.model.PaymentMethodResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping(value = "/payment/instrument/v1")
public class InstrumentOnboardingController {
    private static String val = "";
    @Autowired
    private BraintreeGatewayConfig braintreeGatewayConfig;

    @Autowired
    private InitializeService initializeService;


    @GetMapping(value = "/StorePaymentMethod")
    public String StorePaymentMethod(Model model, @RequestParam int cus_id) {
        String clientToken = braintreeGatewayConfig.getBraintreeGatewayInstance().clientToken().generate();

        model.addAttribute("clientToken", clientToken);
        model.addAttribute("cus_id", cus_id);
        return "checkouts/welcome";
    }


    @PostMapping(value = "/add")
    @ResponseBody
    public PaymentMethodResponse addMethodOfPayment(@RequestParam("payment_method_nonce") String nonce, @RequestParam("cus_id") Integer cus_id) {
        PaymentEntity payment = new PaymentEntity();
        PaymentMethodResponse response = initializeService.onboard(payment, nonce, cus_id);
        return response;
    }

    @PostMapping(value = "/customer")
    @ResponseBody
    public PaymentMethodResponse addCustomer(@RequestBody PaymentEntity paymentRequest) {
        PaymentEntity payment = new PaymentEntity();
        PaymentMethodResponse response = initializeService.addCustomer(paymentRequest);
        return response;
    }


}