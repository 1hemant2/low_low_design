package lld02_strategy_pattern.shipping_cost_calculator;


/**
PROBLEM STATEMENT: Shipping Cost Calculator (Strategy Pattern)

==> You are designing the shipping cost calculation module for an e-commerce system.

# Each order has:
    - weight (in kg)
    - distance (in km)
    - delivery preference

    The way shipping cost is calculated varies based on the shipping type.
    This calculation logic is expected to change frequently.

# CURRENT SHIPPING TYPES
    1. Standard Shipping
       - Lowest cost
       - Slow delivery
       - Uses its own cost formula

    2. Express Shipping
       - Faster delivery
       - Higher base charge
       - Uses a different cost formula

    3. Same-Day Shipping
       - Premium delivery
       - Aggressive pricing
       - Uses another cost formula

# REQUIREMENTS
    - The system must allow switching shipping cost calculation logic at runtime.
    - Adding a new shipping type must NOT require modifying existing code.
    - The order or checkout flow must NOT know how the cost is calculated.
    - Avoid if/else or switch statements based on shipping type.
    - Avoid inheritance hierarchies like StandardOrder, ExpressOrder, etc.

# FUTURE CHANGES (GUARANTEED)
    - Eco Shipping (discount-based logic)
    - Festival Surge Shipping
    - Partner-specific shipping rules

# CONSTRAINTS
    - Focus ONLY on the Strategy design pattern.
    - Do NOT introduce Factory, Observer, Decorator, or other patterns.
    - Keep the design minimal and intentional.
    - Make it obvious what changes over time and what remains stable.

# DELIVERABLES
    1. Java interfaces/classes for the design.
    2. A short comment (2–3 lines) explaining:
       - What is changing over time
       - Why Strategy is the correct choice

# EVALUATION CRITERIA
    - Behavior is encapsulated and swappable.
    - Context depends on abstraction, not concrete implementations.
    - New shipping types can be added without modifying existing logic.
    - Code reads like a design decision, not a workaround.

 */


// < ======================================== Solution ============================================= >

/**
  -----My thought----
  # voilatile behaviour(low level)
  - Here i am seeing the diffrent diffrent startegy to ship the product. 
  - Here each startergy have there own to cost formulla & requirement are varying 
  - Since here i am not allowing to use the if-else/switch-case, I am going to implement the each shiping class individually and implement shiping type method. 
  - Since here shipping type is voilatile behvaviour i am going to make an interface.
 
  # Static data (domain data)
  - Each order will contain these data(weight : float, distance: float, preferance: string)
  - I am going to keep it as domain data

  # high level(execution flow)
  - checkout method which will use low  level class to calculate the cost.
 */

/*
Important line to Remember : Startergy should not store any state, it should only depend on input data and implement build the startergy according to that specific data only 
*/

class Order {
    float weight;
    float distance; 
    String prefrence;

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public String getPrefrence() {
        return prefrence;
    }

    public void setPrefrence(String prefrence) {
        this.prefrence = prefrence;
    }
}

interface Shiping {
    int calculateCost(Order order);
}

class StandardShiping implements Shiping {
    @Override
    public int calculateCost(Order order) {
        //complex algo
        if(order.getDistance() > 3) {
            return 3;
        } 
        return 5;
    } 
}

class ExpressShiping implements Shiping {
    @Override
    public int calculateCost(Order order) {
        if(order.getDistance() > 5) {
            return 5;
        } 
        return 7;
    }   
}

class SameDayShiping implements Shiping {
    @Override
    public int calculateCost(Order order) {
        //some complex algorithm
        if(order.getDistance() > 10) {
            return 10;
        } 
        return 5;
    }   
}

class Checkout {
   Shiping shiping;

   Checkout(Shiping shiping) {
      this.shiping = shiping;
   }
   
   void shipProduct(Order order) {
      System.out.println("Your total cost is : " + shiping.calculateCost(order) + " , distance : " + order.getDistance());
   }

}

public class Main {
    int calculatePrice() {
        Order order = new Order();
        order.setDistance(10);
        order.setWeight(10);
        order.setPrefrence("Please handover to security personal");
        //example with standard day shiping 
        Shiping standardShiping = new StandardShiping();
        Checkout checkout = new Checkout(standardShiping);
        checkout.shipProduct(order);
        return 0;
    }
}
