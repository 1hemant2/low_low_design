package lld03_observer_pattern.order_status_notification_system;

import java.util.ArrayList;
import java.util.List;

/**
# DESIGN PROBLEM: Observer Pattern (Behavioral)

# You are designing a backend Order Tracking System.

An Order goes through multiple state changes:
    - CREATED
    - PAID
    - SHIPPED
    - DELIVERED
    - CANCELLED

Whenever the order state changes, multiple independent systems may need to react:

    - EmailService → send email to user on any state change
    - SMSService → send SMS on any state change
    - InvoiceService → generate invoice ONLY when order becomes PAID
    - AnalyticsService → log ALL state transitions
    - DeliveryService → act ONLY when order becomes SHIPPED

Requirements:
    1. The Order class must NOT:
       - Know about concrete services like EmailService, SMSService, etc.
       - Contain conditional logic like if(email) / if(sms)
    2. Observers must:
       - Be independently addable/removable
       - Not know about each other
    3. The system must follow:
       - Open for extension
       - Closed for modification
    4. Observers should be able to subscribe and unsubscribe at runtime.
    5. Adding a new observer (e.g., WhatsAppService) should NOT require changes in existing classes.

Constraints:
    - Language: Java
    - No frameworks (no Spring, no annotations)
    - Clean interfaces and naming
    - Use Observer Design Pattern
    - Decide between PUSH vs PULL model and justify it

Your Tasks:
    1. Identify the Subject.
    2. Define the Observer interface.
    3. Implement subscription and notification mechanism.
    4. Trigger notifications on order state change.
    5. Write 3–4 lines explaining:
       - Why Observer is appropriate here
       - What varies and what remains stable
 */

/**
 - Pull → evolving data, observer-specific logic, core domain stability
 - Push → stable event, explicit contract, fast and dumb observers

 - Push: Observable defines the payload → observers adapt
 - Pull: Observable exposes state → observers choose what to read

 - common in both: both push and pull keep the observer list

 */

/**
  volatile behavious: 
     - emailService, smsService 
     - inovice service  
     - analytics service 
     - delivery service  
     - in this all will implement notifies method and difne according them puting it in interface 
  static data: order details {id, name, status, userEmail, ...} these three field are minimal we require.
  high level class(not effected by business changes): class from where 
 */

enum OrderEventType {
    ORDER_CREATED,
    ORDER_PAID,
    ORDER_SHIPPED,
    ORDER_DELIVERED,
    ORDER_CANCELLED
}

interface OrderObserver {
    void onOrderEvent(OrderEventType event, Order order);
}

interface OrderSubject {
    void addObserver(OrderObserver observer);
    void removeObserver(OrderObserver observer);
}


class Order implements OrderSubject {

    private int id;
    private String name;
    private String userEmail;
    private String status;

    private final List<OrderObserver> observers = new ArrayList<>();

    public Order(int id, String name, String userEmail) {
        this.id = id;
        this.name = name;
        this.userEmail = userEmail;
        this.status = "CREATED";
        notifyObservers(OrderEventType.ORDER_CREATED);
    }

    // --- observable behavior ---
    @Override
    public void addObserver(OrderObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(OrderObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers(OrderEventType event) {
        for (OrderObserver observer : observers) {
            observer.onOrderEvent(event, this);
        }
    }

    // --- domain behavior ---
    public void markPaid() {
        this.status = "PAID";
        notifyObservers(OrderEventType.ORDER_PAID);
    }

    public void markShipped() {
        this.status = "SHIPPED";
        notifyObservers(OrderEventType.ORDER_SHIPPED);
    }

    // --- getters (pull happens here) ---
    public int getId() {
        return id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getStatus() {
        return status;
    }
}

class EmailService implements OrderObserver {

    @Override
    public void onOrderEvent(OrderEventType event, Order order) {
        if (event == OrderEventType.ORDER_PAID) {
            System.out.println("Email sent to " + order.getUserEmail()
                    + " for order " + order.getId());
        }
    }
}

class InvoiceService implements OrderObserver {

    @Override
    public void onOrderEvent(OrderEventType event, Order order) {
        if (event == OrderEventType.ORDER_PAID) {
            System.out.println("Invoice generated for order " + order.getId());
        }
    }
}


class AnalyticsService implements OrderObserver {

    @Override
    public void onOrderEvent(OrderEventType event, Order order) {
        System.out.println("Analytics: Order " + order.getId()
                + " changed state to " + order.getStatus());
    }
}


public class Main {
    public static void main(String[] args) {

        Order order = new Order(1, "iPhone", "user@email.com");

        order.addObserver(new EmailService());
        order.addObserver(new InvoiceService());
        order.addObserver(new AnalyticsService());

        order.markPaid();
        order.markShipped();
    }
}
