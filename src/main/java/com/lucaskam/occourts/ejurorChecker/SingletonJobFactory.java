package com.lucaskam.occourts.ejurorChecker;

import org.quartz.Job;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;

public class SingletonJobFactory implements JobFactory {
    public Job job;

    public SingletonJobFactory(Job job) {
        this.job = job;
    }

    @Override
    public Job newJob(TriggerFiredBundle bundle, Scheduler scheduler) throws SchedulerException {
        return job;
    }
}
