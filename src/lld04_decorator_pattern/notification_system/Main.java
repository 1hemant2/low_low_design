package lld04_decorator_pattern.notification_system;

/**
PROBLEM STATEMENT: Notification System with Optional Behaviors

You are designing a Notification System for a growing software product.

At its core, the system can send a simple notification using Email.
This basic capability already exists and is used in multiple places.
You are NOT allowed to modify this existing code once it is written.

New requirements arrive over time:

1. Notifications may also need to be sent via:
   - SMS
   - Push Notification

2. Some notifications must support additional behaviors:
   - Logging (record when a notification is sent)
   - Encryption (secure the message before sending)
   - Rate Limiting (prevent sending too frequently)

3. These behaviors are OPTIONAL and COMBINABLE.
   Any notification can have any combination of these features.
   Examples:
   - Email only
   - Email + SMS
   - Email + Push + Logging
   - SMS + Push + Encryption + Logging
   - Email + SMS + Push + Encryption + Rate Limiting

4. The combinations are not known in advance.
   They must be decided at RUNTIME.

5. You must avoid:
   - Creating a subclass for every combination
   - Large conditional blocks (if/else or switch chains)
   - Modifying existing notification classes when new features are added

6. The system should be OPEN for extension but CLOSED for modification.
   New behaviors (e.g., Slack, WhatsApp, Auditing) may be added later
   without changing existing code.

TASK:
Design and implement this system so that:
- Core notification logic stays simple
- Optional behaviors can be layered dynamically
- Behavior combinations do not cause class explosion
- The solution clearly demonstrates WHEN and WHY the Decorator pattern is useful

Deliverables:
- A common Notification abstraction
- One concrete base notification (Email)
- At least one optional behavior implemented in a reusable, composable way
- Ability to stack multiple behaviors around a notification at runtime

 */

// <============================= Solution ========================> 

/**
First: anchor the idea (no mysticism)

   This system has two moving truths:
      What a notification does → send a message
      How it behaves → logging, encryption, rate limiting, multi-channel delivery
   Those behavios must:

      be optional
      be stackable
      be decided at runtime
      not mutate existing code
   That screams Decorator, not inheritance, not strategy, not if-else soup.
 */

/*
Step 1: The core abstraction (non-negotiable)
Every decorator and concrete class must speak the same language.
*/
interface Notification {
    void send(String message);
}

/**
 Step 2: The concrete base (Email)
This is your already-existing, sacred, don’t-touch-again code.
 */
class EmailNotification implements Notification {

    @Override
    public void send(String message) {
        System.out.println("Sending EMAIL: " + message);
    }
}

/**
Step 3: The Decorator base class (the spine)
All decorators must:
   implement Notification
   wrap another Notification
   delegate by default
 */
abstract class NotificationDecorator implements Notification {

    protected final Notification wrapped;

    protected NotificationDecorator(Notification wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public void send(String message) {
        wrapped.send(message);
    }
}

/**
Step 4: One optional behavior (Logging)
 This is where the pattern clicks.
 */
class LoggingDecorator extends NotificationDecorator {

    public LoggingDecorator(Notification wrapped) {
        super(wrapped);
    }

    @Override
    public void send(String message) {
        System.out.println("[LOG] Notification about to be sent");
        super.send(message);
        System.out.println("[LOG] Notification sent");
    }
}

/**
 Step 5: Another behavior (Encryption)
 */
class EncryptionDecorator extends NotificationDecorator {

    public EncryptionDecorator(Notification wrapped) {
        super(wrapped);
    }

    @Override
    public void send(String message) {
        System.out.println("[ENCRYPTION] Encrypting message");
        super.send(message);
        System.out.println("[ENCRYPTION] Message encrypted and sent");
    }
}

public class Main {
   public static void main(String[] args) {
         Notification notification =
            new LoggingDecorator(
                new EncryptionDecorator(
                    new EmailNotification()
                )
            );

         notification.send("Your OTP is 123456");
   }
}
