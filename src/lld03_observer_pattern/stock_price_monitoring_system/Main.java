package lld03_observer_pattern.stock_price_monitoring_system;

import java.util.ArrayList;
import java.util.List;

/*
TASK 2: Observer Pattern (Event-only Pull)

Design a Stock Price Monitoring System.

----------------------------------------
DOMAIN
----------------------------------------

Core domain object:
- Stock

Stock properties:
- symbol (String)        // e.g. "AAPL", "TSLA"
- currentPrice (double)
- previousPrice (double)

----------------------------------------
DOMAIN EVENTS (FACTS ONLY)
----------------------------------------

These represent what happened in the domain, not actions.

Examples:
- PRICE_UPDATED
- PRICE_INCREASED
- PRICE_DECREASED

You may choose which ones are necessary, but they must be:
- domain-specific
- observer-agnostic

----------------------------------------
OBSERVERS (VOLATILE BEHAVIOR)
----------------------------------------

1. AlertService
   - Sends alert if price crosses a threshold (e.g. > 1000)

2. AnalyticsService
   - Logs every price change

3. TradingService
   - Reacts only when price drops sharply

4. DashboardService
   - Updates UI on every price change

Rules:
- Observers must not know about each other
- Observers must decide whether they care about an event
- Observers must pull data from Stock if needed

----------------------------------------
CONSTRAINTS
----------------------------------------

- Use Observer Design Pattern
- Use EVENT-ONLY PULL model
- Stock must NOT:
  - know concrete observers
  - contain if-else logic for observer behavior
- Observers must be addable/removable at runtime
- Adding a new observer must NOT require modifying Stock

----------------------------------------
DELIVERABLES (DO IN ORDER)
----------------------------------------

STEP 1:
- Domain event enum
- Observer interface (method signature only)
- Subject interface (method signatures only)

STEP 2:
- Stock class (domain + observable)

STEP 3:
- At least two observer implementations

----------------------------------------
MENTAL CHECK
----------------------------------------

"Stock reports price facts.
Observers decide what to do.
Data is pulled, not pushed."

*/


// ========================================Solution=================================
/**
  - staic data : symbol, currentPrice, PreviousPrice (These are the pure data)
  - Event: PRICE_UPDATED, PRICE_INCREASED, PRICE_DECREASED

 */

  /**
   * The Subject may compute domain facts,
but must not shape or tailor data for any specific observer.
   */


enum StockEventType {
  PRICE_UPDATED
}

interface StockObserver {
    void onStockUpdate(StockEventType stockEventType, Stock Stock);
}

interface StockSubject {
    void addObserver(StockObserver stockObserver);
    void removeObserver(StockObserver stockObserver);
}

class Stock implements StockSubject{

    String symbol;      // e.g. "AAPL", "TSLA"
    double currentPrice;
    double previousPrice;

    private List<StockObserver> observers = new ArrayList<>();

    

    public Stock(String symbol, double currentPrice, double previousPrice) {
        this.symbol = symbol;
        this.currentPrice = currentPrice;
        this.previousPrice = previousPrice;
    }

    @Override
    public void addObserver(StockObserver stockObserver) {
                
    }

    @Override
    public void removeObserver(StockObserver stockObserver) {
        
    }

    void notifyObserver(StockEventType stockEventType) {
        for(StockObserver stockObserver : observers) {
            stockObserver.onStockUpdate(stockEventType, this);
        }
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public double getPreviousPrice() {
        return previousPrice;
    }

    public void setPreviousPrice(double previousPrice) {
        this.previousPrice = previousPrice;
    }

    public void updatePrice(double price) {
        this.previousPrice = this.currentPrice;
        this.currentPrice = price;
        notifyObserver(StockEventType.PRICE_UPDATED);
    }
}

class AlertService implements StockObserver{
    @Override
    public void onStockUpdate(StockEventType stockEventType, Stock Stock) {
        if(stockEventType == StockEventType.PRICE_UPDATED) {
            double currentPrice = Stock.getCurrentPrice();
            if(currentPrice > 1000) {
                System.out.println("Stock price crosseed the threshold");
            }
        }
    }
}

class AnalyticsService implements StockObserver{
    @Override
    public void onStockUpdate(StockEventType stockEventType, Stock Stock) {
        //sending event to logger service
            double previousPrice = Stock.getPreviousPrice();
            double currentPrice = Stock.getCurrentPrice();
           // double diff = Math.abs(currentPrice - previousPrice);
            if(currentPrice < previousPrice) {
                //send event price decrease 
            } else if(currentPrice > previousPrice) {
                //send event as price increased
            }
    }
}

class TradingService implements StockObserver {
   @Override
   public void onStockUpdate(StockEventType stockEventType, Stock Stock) {
       // do something
   }
}

class DashboardService implements StockObserver{
    @Override
    public void onStockUpdate(StockEventType stockEventType, Stock Stock) {
        //sending data to  dashboard service
    }
}

public class Main {
    public static void main(String[] args) {
        Stock Stock = new Stock("HEM", 10, 5);
        Stock.addObserver(new AlertService());
        Stock.addObserver(new AnalyticsService());
        Stock.addObserver(new TradingService());
        Stock.addObserver(new DashboardService());
        Stock.updatePrice(20);
    }
}
