package com.github.verhagen.timesheet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActivityVisitor implements  Visitor<Activity> {
    private Logger logger = LoggerFactory.getLogger(ActivityVisitor.class);


    @Override
    public void visit(Activity activity) {
        logger.info("" + activity);
    }

}
