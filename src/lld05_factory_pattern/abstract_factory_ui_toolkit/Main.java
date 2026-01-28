package lld05_factory_pattern.abstract_factory_ui_toolkit;

/**
 ASSIGNMENT 2: Abstract Factory Pattern — Cross-Platform UI Component System

PROBLEM STATEMENT
You are building a UI toolkit for a product that runs on multiple platforms.
The same application must support different platforms, but UI components
must look and behave consistently within a platform.

Supported platforms:
- Windows
- MacOS

Each platform provides its own family of UI components:
- Button
- Checkbox

The client application should be able to:
- Select a platform at startup
- Create UI components for that platform
- Use those components without knowing their concrete classes

IMPORTANT:
A Windows Button must NEVER be used with a MacOS Checkbox.
Component families must stay consistent.

--------------------------------------------------

REQUIREMENTS

1. Create abstract product interfaces:
   - Button
       Method:
       - void render()

   - Checkbox
       Method:
       - void render()

2. Create concrete product implementations:

   Windows family:
   - WindowsButton
   - WindowsCheckbox

   MacOS family:
   - MacButton
   - MacCheckbox

   Each concrete class should:
   - Implement its interface
   - Print platform-specific rendering behavior

3. Create an Abstract Factory interface:
   UIComponentFactory

   Methods:
   - Button createButton()
   - Checkbox createCheckbox()

4. Create concrete factory implementations:
   - WindowsUIFactory
   - MacUIFactory

   Each factory:
   - Creates ONLY its own platform’s components
   - Ensures product compatibility within the family

5. Client Code:
   - Chooses the platform ONCE (Windows or Mac)
   - Gets the corresponding factory
   - Uses the factory to create buttons and checkboxes
   - Never instantiates concrete UI classes directly

--------------------------------------------------

CONSTRAINTS & RULES

- Client code must depend ONLY on interfaces
- No `new` of concrete products in client code
- No platform checks inside product classes
- No mixing of component families
- Adding a new platform should require:
  - New concrete products
  - New concrete factory
  - ZERO changes to client code

--------------------------------------------------

DELIVERABLES

- Java code for:
  - Product interfaces
  - Concrete product classes
  - Abstract factory interface
  - Concrete factory classes
  - Client / demo class
- Short written explanation:
  "Why Abstract Factory is needed instead of a simple Factory"

--------------------------------------------------

DESIGN INTENT

This assignment exists to make you feel:
- Why “multiple related objects” break normal factories
- How Abstract Factory enforces consistency by design
- How object families evolve without client-side changes

If your solution allows mixing Mac and Windows components,
the pattern has failed — and so has the design.

 */

// <============================ Solution =============================>


/**
 - created a interface for  moving part button and checkbox, Becuase each os will have its own implementation of these moving parts
 - There are two type of os for now Windows and MacOS, so created two concrete classes for each os implementing the button and checkbox interface
 */

interface Button {
    void render();
}

interface Checkbox {
    void render();
}

class WindowsButton implements Button {

   @Override
   public void render() {
      System.out.println("Rendering Windows Button");
   }
}

class WindowsCheckbox implements Checkbox {

   @Override
   public void render() {
      System.out.println("Rendering Windows Checkbox");
   }
}

class MacButton implements Button {

   @Override
   public void render() {
      System.out.println("Rendering MacOS Button");
   }
}

class MacCheckbox implements Checkbox {

   @Override
   public void render() {
      System.out.println("Rendering MacOS Checkbox");
   }
}

interface UIComponentFactory {
    Button createButton();
    Checkbox createCheckbox();
} 

class WindowsUIFactory implements UIComponentFactory {

   @Override
   public Button createButton() {
      return new WindowsButton();
   }

   @Override
   public Checkbox createCheckbox() {
      return new WindowsCheckbox();
   }
}

class MacUIFactory implements UIComponentFactory {

   @Override
   public Button createButton() {
      return new MacButton();
   }

   @Override
   public Checkbox createCheckbox() {
      return new MacCheckbox();
   }
}

enum Platform {
    WINDOWS,
    MACOS
}

class FactoryProducer {
    public static UIComponentFactory getFactory(Platform platform) {
        switch (platform) {
            case WINDOWS:
                return new WindowsUIFactory();
            case MACOS:
                return new MacUIFactory();
            default:
                throw new IllegalArgumentException("Invalid platform: " + platform);
        }
    }
}


public class Main {

    public static void main(String[] args) {
        // Create Windows UI components
        UIComponentFactory windowsFactory = FactoryProducer.getFactory(Platform.WINDOWS);
        Button windowsButton = windowsFactory.createButton();
        Checkbox windowsCheckbox = windowsFactory.createCheckbox();
        windowsButton.render();
        windowsCheckbox.render();
    }
}
