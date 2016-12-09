package com.lucaskam.occourts.ejurorChecker;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class CheckGroupStatusJobRunner {

    private final DocumentRetriever documentRetriever;
    private final DocumentParser documentParser;
    private final PushOverNotificationSender groupStatusNotificationSender;
    private final int groupNumber;
    private final JusticeCenter justiceCenter;
    private int intervalInMinutes;

    public CheckGroupStatusJobRunner(DocumentRetriever documentRetriever, DocumentParser documentParser, PushOverNotificationSender
        groupStatusNotificationSender, int groupNumber, JusticeCenter justiceCenter, int intervalInMinutes) {
        this.documentRetriever = documentRetriever;
        this.documentParser = documentParser;
        this.groupStatusNotificationSender = groupStatusNotificationSender;
        this.groupNumber = groupNumber;
        this.justiceCenter = justiceCenter;
        this.intervalInMinutes = intervalInMinutes;
    }

    public void run() throws SchedulerException {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();

        Scheduler scheduler = schedulerFactory.getScheduler();

        String previousLastUpdated = "";
        Job job = new CheckGroupStatusJob(documentRetriever, documentParser, groupStatusNotificationSender, groupNumber, previousLastUpdated, justiceCenter);

        scheduler.setJobFactory(new SingletonJobFactory(job));

        JobDetail jobDetail = newJob(CheckGroupStatusJob.class)
            .withIdentity("job1", "group1")
            .build();
        
        Trigger trigger = newTrigger()
            .withIdentity("trigger1", "group1")
            .startNow()
            .withSchedule(simpleSchedule()
                              .withIntervalInMinutes(intervalInMinutes)
                              .repeatForever())
            .build();

        scheduler.scheduleJob(jobDetail, trigger);

        scheduler.start();
    }
}
