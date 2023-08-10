package ib.develop.matstore.common.util;

import ib.develop.matstore.common.enums.MeasureUnit;
import ib.develop.matstore.dto.requests.ItemRequest;
import ib.develop.matstore.dto.requests.OrderRequest;
import ib.develop.matstore.entities.Info;
import ib.develop.matstore.entities.Product;
import ib.develop.matstore.services.InfoService;
import ib.develop.matstore.services.OrderService;
import ib.develop.matstore.services.ProductService;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.*;


@Component
public class FirstTimeInitializer implements CommandLineRunner {
    Log logger = LogFactory.getLog(FirstTimeInitializer.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private InfoService infoService;

    @Override
    public void run(String... args) throws Exception {
        if (productService.findAll().isEmpty()){
            logger.info("there are no products,create some products");
            Product product = Product.builder().date(LocalDateTime.now()).label("product1").measureUnit(MeasureUnit.U).quantity(4).unitPrice(10).build();
            productService.save(product);
        }
        if (infoService.findAll().isEmpty()){
            logger.info("there are no Info,create some info");
            Info info = Info.builder()
                    .title("CERAM BRAHIM TAHRI")
                    .subTitle("كارلاج - سانيتير - بريكو")
                    .address("حي تجزئة خيبر 164 - ابراهيم الطاهري")
                    .telephone("0602932466")
                    .email("brahimtahri02@gmail.com")
                    .build();
            infoService.save(info);
        }
    }
}
