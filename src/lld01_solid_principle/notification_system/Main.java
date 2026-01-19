package lld01_solid_principle.notification_system;

import javax.management.RuntimeErrorException;

/**
 * @Ps: - Design and implement a Notification Dispatch Module using only SOLID principles.  
 *      - The goal is to produce code that is extensible, testable, and maintainable under changing requirements.
        - You are not asked to build a full application. Only the core domain + services.
 */

/**
 * Functional Requirements: 

1. Notification
 - A notification consists of:
   recipient (string)
   content (string)
   channel (email / sms for now)

2. Sending Notifications
 - The system must expose one public entry point
 - notificationService.send(notification);
  -> Calling this method should send the notification via the appropriate channel.

3. Supported Channels (Initial Scope)
  - Email
  - SMS
  -> Each channel has its own sending logic.

 */

/**
 * Future Requirements (Design MUST support these without rewriting existing logic)

Rule: Your design must allow the following without modifying existing classes (except wiring):

1. New Channels:
   - WhatsApp
   - Slack
   - Push Notification

2. Different Message Formats
   -Plain text
   -HTML
   -Markdown

3. Different Failure Behaviors
    -Fail silently
    -Retry on failure
    -Throw exception upward

4. Testing
  - All logic must be unit-testable
  - No real external service calls inside tests

 */

/**
 * Constraints (These are strict)

❌ Forbidden
    -No if / else or switch on channel type
    -No “one class does everything”
    -No hard-coded dependencies (new EmailSender() inside services)
    -No design pattern talk (Factory, Strategy, etc.)
    -No framework magic (Spring annotations optional but not required)

✅ Required
    -Use interfaces and composition
    -Each class must have one reason to change
    -High-level modules must not depend on low-level implementations
    -Adding a new channel must involve adding a class, not modifying existing ones
 */
//<==========================================Solution============================================>
/*
   - low level class(keep changing) -> channel, formating, failurePolicy (create a interface for these) 
   - domain (Static data) -> recipient (never change), content(not changing), channel( don't take in domain becuase it's not data it's a medium to deliver the content)
   - high level(don't know anything about the how low class work & use them) -> just need to send notification, route will call send method of notificationService 
 */

/*
Rule: “If this thing changes tomorrow, should business logic feel it?”
       Yes → low-level
       No → high-level or domain
*/


class Notification {
   private String recipient;
   private String content;

   public String getRecipient() {
    return recipient;
   }

   public void setRecipient(String recipient) {
    this.recipient = recipient;
   }

   public String getContent() {
    return content;
   }

   public void setContent(String content) {
    this.content = content;
   }

}

interface NotificationChannel {
    void sendNotification(Notification notification);
}

interface ContentFormating {
  String format(String content);
}

interface FailurePolicy {
    void handle(Runnable operation, Exception e);
}


class NotificationService {

    NotificationChannel notificationChannel;

    NotificationService(NotificationChannel notificationChannel) {
        this.notificationChannel = notificationChannel;
    }
    
    void send(Notification notification) {
        notificationChannel.sendNotification(notification);
    }
}

class SmsNotficationChannel implements NotificationChannel {

    FailurePolicy failurePolicy;
    ContentFormating contentFormating;

    SmsNotficationChannel(FailurePolicy failurePolicy, ContentFormating contentFormating) {
        this.failurePolicy = failurePolicy;
        this.contentFormating = contentFormating;
    }

    @Override
    public void sendNotification(Notification notification) {
    Runnable operation = () -> {
        String content = contentFormating.format(notification.getContent());
        System.out.println("sending message --> " + content);
    };

    try {
        operation.run();
    } catch (Exception e) {
        failurePolicy.handle(operation, e);
    }

    }
}

class HtmlFormating implements ContentFormating {
    @Override
    public String format(String content) {
        return "html formated data";
    }
}

class Retry implements FailurePolicy {

    private final int maxRetries;

    Retry(int maxRetries) {
        this.maxRetries = maxRetries;
    }

    @Override
    public void handle(Runnable operation, Exception e) {
        int attempts = 0;

        while (attempts < maxRetries) {
            try {
                attempts++;
                System.out.println("Retry attempt " + attempts);
                operation.run();
                return; // success → stop retrying
            } catch (Exception ex) {
                if (attempts == maxRetries) {
                    throw new RuntimeException("Retry failed after " + maxRetries + " attempts", ex);
                }
            }
        }
    }
}


class ThrowOnFailed implements FailurePolicy {
    @Override
    public void handle(Runnable operation,Exception e) {
        throw new RuntimeException(e.getMessage());
    }
}

class FailedSilently implements FailurePolicy{
   @Override
   public void handle(Runnable operation, Exception e) {
       System.out.println(e); 
   }
}

public class Main {
    public static void main(String [] args) {
        Retry retry = new Retry(3);
        FailedSilently failedSilently = new FailedSilently();
        ThrowOnFailed throwOnFailed = new ThrowOnFailed();
        HtmlFormating htmlFormating = new HtmlFormating();
        NotificationChannel smsChannel = new SmsNotficationChannel(throwOnFailed, htmlFormating);
        NotificationService notificationService = new NotificationService(smsChannel);
        Notification notification =  new Notification();
        notificationService.send(notification);
    }
}
