package lld04_decorator_pattern.subscription_pricing_system;

/**
PROBLEM STATEMENT: Subscription Pricing System with Optional Add-ons

You are designing a Subscription Pricing System for a SaaS product.

At its core, the system provides different base subscription plans:
- Free Plan
- Pro Plan
- Enterprise Plan

Each plan has a base price and basic features.
These base plans already exist and must NOT be modified once implemented.

New requirements arrive over time:

1. Subscriptions may include OPTIONAL add-ons:
   - Priority Support
   - Extra Storage
   - Audit Logs
   - Custom Branding

2. These add-ons are:
   - Optional
   - Combinable in any order
   - Decided at RUNTIME

3. Any subscription can have any combination of add-ons.
   Examples:
   - Pro Plan only
   - Pro Plan + Extra Storage
   - Enterprise Plan + Priority Support + Audit Logs
   - Free Plan + Custom Branding + Extra Storage

4. The combinations are NOT known in advance.

5. You must avoid:
   - Creating subclasses for every possible combination
   - Large conditional blocks (if/else or switch statements)
   - Modifying existing plan classes when new add-ons are introduced

6. The system must follow the Open/Closed Principle:
   - OPEN for extension (new add-ons can be added)
   - CLOSED for modification (existing code remains untouched)

TASK:
Design and implement the system such that:
- Core subscription plans remain simple and stable
- Optional add-ons can be layered dynamically at runtime
- Add-on combinations do not lead to class explosion
- The solution clearly demonstrates WHEN and WHY the Decorator Pattern is useful

Deliverables:
- A common Plan abstraction
- At least one concrete base plan
- A reusable Decorator base class
- At least two add-on decorators
- A runtime example showing multiple add-ons stacked together
*/

// <======================================= Solution ========================================>

/**
Moving Parts: 
   - Each subscription plan have a base price, description
   - Behaviours:  Each plan can support combinations of add-ons which is not known in advance
   - It's clearly a decorator pattern.
 */

interface Plan {
    double getPrice();
    String getDescription();
}


abstract class PlanDecorator implements Plan {
    protected Plan decoratedPlan;

    public PlanDecorator(Plan decoratedPlan) {
        this.decoratedPlan = decoratedPlan;
    }
}

class PrioritySupportDecorator extends PlanDecorator {
    public PrioritySupportDecorator(Plan decoratedPlan) {
        super(decoratedPlan);
    }

    @Override
    public double getPrice() {
        return decoratedPlan.getPrice() + 20.0; // Adding cost for priority support
    }

    @Override
    public String getDescription() {
        return decoratedPlan.getDescription() + ", Priority Support";
    }
}

class ExtraStorageDecorator extends PlanDecorator {

    public ExtraStorageDecorator(Plan decoratedPlan) {
        super(decoratedPlan);
    }

    @Override
    public double getPrice() {
        return decoratedPlan.getPrice() + 10.0; // Adding cost for extra storage
    }

    @Override
    public String getDescription() {
        return decoratedPlan.getDescription() + ", Extra Storage";
    }
}

class ProPlan implements Plan {

    @Override
    public double getPrice() {
        return 50.0;
    }  

    @Override
    public String getDescription() {    
        return "Pro Plan";
    }   
}

class AuditLogsDecorator extends PlanDecorator {
    public AuditLogsDecorator(Plan decoratedPlan) {
        super(decoratedPlan);
    }

    @Override
    public double getPrice() {
        return decoratedPlan.getPrice() + 15.0; // Adding cost for audit logs
    }

    @Override
    public String getDescription() {
        return decoratedPlan.getDescription() + ", Audit Logs";
    }
}

class FreePlan implements Plan {

    @Override
    public double getPrice() {
        return 0.0;
    }  

    @Override
    public String getDescription() {    
        return "Free Plan";
    }   
}

public class Main {
    public static void main(String[] args) {
        
        Plan myPlan = new ExtraStorageDecorator(
                          new PrioritySupportDecorator(
                              new ProPlan()
                          )
                      );

        Plan anotherPlan = new AuditLogsDecorator(
                              new ExtraStorageDecorator(
                                  new FreePlan()
                              )
                          );
        System.out.println("Plan Description: " + myPlan.getDescription());
        System.out.println("Total Price: $" + myPlan.getPrice());

        System.out.println("Plan Description: " + anotherPlan.getDescription());
        System.out.println("Total Price: $" + anotherPlan.getPrice());
    }
}
