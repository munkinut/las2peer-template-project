package net.munki.play.las2peer;

import java.util.logging.Level;

import i5.las2peer.api.Context;
import i5.las2peer.api.Service;
import i5.las2peer.api.logging.MonitoringEvent;
import i5.las2peer.api.persistency.Envelope;
import io.swagger.annotations.Api;
import io.swagger.annotations.Contact;
import io.swagger.annotations.Info;
import io.swagger.annotations.License;
import io.swagger.annotations.SwaggerDefinition;

@Api
@SwaggerDefinition(
        info = @Info(
                title = "JBotNet Service",
                version = "1.0.0",
                description = "An experimental service to provide a network of bots.",
                termsOfService = "http://your-terms-of-service-url.com",
                contact = @Contact(
                        name = "Warren Milburn",
                        url = "https://www.munki.net",
                        email = "warren@munki.net"),
                license = @License(
                        name = "your software license name",
                        url = "http://your-software-license-url.com")))
public class JBotNetService extends Service {

    public boolean storeJBot(String identifier, JBot jBot) {
        boolean success;
        try {
            Envelope env = null;
            try {
                // fetch existing container object from network storage
                env = Context.get().requestEnvelope(identifier);
                // place the new object inside container
                env.setContent(jBot);
                success = true;
            } catch (Exception e) {
                // write info message to logfile and console
                Context.get().getLogger(this.getClass()).log(Level.INFO, "Network storage container not found. Creating new one. " + e.toString());
                // create new container object with current ServiceAgent as owner
                env = Context.get().createEnvelope(identifier);
                env.setContent(jBot);
                success = true;
            }
            // upload the updated storage container back to the network
            Context.get().storeEnvelope(env);
        } catch (Exception e) {
            // write error to logfile and console
            Context.get().getLogger(this.getClass()).log(Level.SEVERE, "Can't persist to network storage!", e);
            // create and publish a monitoring message
            Context.get().monitorEvent(this, MonitoringEvent.SERVICE_ERROR, e.toString());
            success = false;
        }

        return success;
    }

    public JBot fetchJBot(String identifier) {
        JBot retrieved = null;
        try {
            // fetch existing container object from network storage
            Envelope env = Context.get().requestEnvelope(identifier);
            // deserialize content from envelope
            retrieved = (JBot) env.getContent();
        } catch (Exception e) {
            // write error to logfile and console
            Context.get().getLogger(this.getClass()).log(Level.SEVERE, "Can't fetch from network storage!", e);
            // create and publish a monitoring message
            Context.get().monitorEvent(this, MonitoringEvent.SERVICE_ERROR, e.toString());
        }
        return retrieved;

    }

    public boolean messageAll(String message) {
        return true;
    }

    public boolean messageBot(String identifier, String message) {
        return true;
    }

}
