package com.rbkmoney.provider.googlepay.iface.decrypt;

import com.rbkmoney.damsel.payment_tool_provider.PaymentToolProviderSrv;
import com.rbkmoney.woody.thrift.impl.http.THServiceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet("/provider/google")
public class ProviderServlet extends GenericServlet {
    private final Servlet handlerServlet;

    @Autowired
    public ProviderServlet(@Qualifier("decryptHandler") PaymentToolProviderSrv.Iface handler) {
        this.handlerServlet = new THServiceBuilder()
                .build(PaymentToolProviderSrv.Iface.class, handler);
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        handlerServlet.service(req, res);
    }

}
