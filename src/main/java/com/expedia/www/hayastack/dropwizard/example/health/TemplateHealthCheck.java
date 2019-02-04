package com.expedia.www.hayastack.dropwizard.example.health;

import com.codahale.metrics.health.HealthCheck;

/**
 * Created by ragsingh on 04/02/19.
 */
public class TemplateHealthCheck extends HealthCheck {

    private final String template;

    public TemplateHealthCheck(String template) {
        this.template = template;
    }

    protected Result check() throws Exception {
        final String saying = String.format(template, "TEST");
        if (! saying.contains("TEST")) {
            return Result.unhealthy("template doesn't include a name");
        }
        return Result.healthy();
    }
}
