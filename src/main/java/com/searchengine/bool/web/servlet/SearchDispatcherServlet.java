package com.searchengine.bool.web.servlet;

import com.searchengine.bool.repository.DataService;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * Created with IntelliJ IDEA.
 * User: avvolkov
 * Date: 11.03.13
 * Time: 19:24
 * To change this template use File | Settings | File Templates.
 */
public class SearchDispatcherServlet extends DispatcherServlet {

    public void destroy() {
        DataService.saveIndex();
        super.destroy();
    }
}
