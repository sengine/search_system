package com.searchengine.bool.web.job;

import com.searchengine.bool.anootation.LoggingBefore;
import com.searchengine.bool.service.SearchService;
import com.searchengine.bool.web.config.ApplicationServiceImpl;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: avvolkov
 * Date: 05.03.13
 * Time: 19:06
 * To change this template use File | Settings | File Templates.
 */

@Component("indexUpdater")
public class UpdateIndexJob {

    @LoggingBefore("sdasdasd/45/4/54/54/54/5/")
    public void  updateIndex() throws Exception {

        ApplicationServiceImpl service = new ApplicationServiceImpl();
        service.getBean("messageSource");
//        service.getMessage("", null);
        SearchService.updateIndex();
    }
}
