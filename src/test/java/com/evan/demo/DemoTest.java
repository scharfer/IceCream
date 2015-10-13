package com.evan.demo;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.evan.demo.dao.DAO;
import com.evan.demo.dao.IceCreamFlavorDao;
import com.evan.demo.model.ICOrder;
import com.evan.demo.model.IceCreamFlavor;
import com.evan.demo.service.IceCreamService;
import com.evan.demo.service.OrderService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("servlet-context-test.xml")

@Transactional
public class DemoTest {

    @Autowired WebApplicationContext wac; 
    @Autowired MockHttpSession session;
    @Autowired MockHttpServletRequest request;
    
    @Autowired
	OrderService os;
    
    @Autowired
	IceCreamService ics;

    private MockMvc mockMvc;

    @Before
    public void setup() {
       this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void getMainPage() throws Exception {    	
        this.mockMvc.perform(get("/").session(session)
        .accept(MediaType.TEXT_HTML))
        .andExpect(status().isOk());
        
    }
    
    @Test
    public void saveFlavor() {    	
    	ics.addFlavor("Test","10.00",2);
    	
    	List<IceCreamFlavor> flavors = ics.getAllFlavors();
    	boolean flavorFound = false;
    	for (IceCreamFlavor flavor : flavors) {
    		if (flavor.getFlavorName().equals("Test")) {
    			flavorFound = true;
    		}
    	}
    	    	
    	assertTrue(flavorFound);
    	    	
    }
    
    @Test
    public void createOrder () {
    	ICOrder myOrder = os.createQuote();    	
    	assertTrue(myOrder.getOrderNumber() > 0);
    	    	
    }
    
    @Test
    public void getFlavors() throws Exception {    	
        this.mockMvc.perform(get("/icecream/flavors").session(session)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());        
    }
}