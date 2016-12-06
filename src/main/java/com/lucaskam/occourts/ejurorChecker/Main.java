package com.lucaskam.occourts.ejurorChecker;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class Main {
    public static String previousLastUpdated = "";
    public static String pushoverToken;
    public static String pushoverUser;

    public static void main(String[] args) throws Exception {
        pushoverToken = System.getenv("PUSHOVER_TOKEN");
        pushoverUser = System.getenv("PUSHOVER_USER");
        
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();

        Scheduler scheduler = schedulerFactory.getScheduler();

        JobDetail jobDetail = newJob(CheckGroupStatusJob.class)
            .withIdentity("job1", "group1")
            .build();

        Trigger trigger = newTrigger()
            .withIdentity("trigger1", "group1")
            .startNow()
            .withSchedule(simpleSchedule()
                              .withIntervalInMinutes(5)
                              .repeatForever())
            .build();

        scheduler.scheduleJob(jobDetail, trigger);

        scheduler.start();
    }

}
