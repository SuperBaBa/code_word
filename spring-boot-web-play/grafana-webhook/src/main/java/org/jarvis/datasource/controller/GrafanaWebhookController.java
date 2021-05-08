package org.jarvis.consumer.controller;

import org.jarvis.consumer.model.GrafanaJsonRoot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author marcus
 * @date 2020/8/19-15:00
 */
@RestController
public class GrafanaWebhookController {
    Logger logger = LoggerFactory.getLogger(GrafanaWebhookController.class);

    @PostMapping("grafana")
    public String grafana(@RequestBody GrafanaJsonRoot grafanaJsonRoot) {
        logger.info("receive grafana message");
        logger.info(grafanaJsonRoot.toString());
        return "hello";
    }
}
