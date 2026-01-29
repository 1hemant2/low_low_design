/*
1) Design Tic Tac Toe
   - Support 2 players (extendable to more later)
   - Clean separation between Board, Player, Game, and Winning Logic
   - No UI required, focus on core game engine
   - Game should be easily extendable (board size, player type, etc.)

2) Design Parking Lot
   - Support multiple vehicle types (Car, Bike, Truck)
   - Different parking spot types
   - Entry and exit gates
   - Slot allocation strategy (nearest, random, etc.)
   - Ability to extend pricing and allocation logic without rewriting code

3) Design Car Rental System
   - Manage vehicle inventory
   - Support different vehicle categories
   - Pricing strategies (hourly, daily, surge)
   - Optional add-ons (insurance, GPS, child seat)
   - Booking, pickup, and return flow

4) Design Notification System
   - Support multiple notification channels (Email, SMS, Push)
   - Ability to add new channels easily
   - Optional features like retry, priority, formatting
   - Core notification sender must remain closed for modification

5) Design Logger Framework
   - Support multiple log levels (INFO, DEBUG, ERROR, etc.)
   - Multiple log destinations (console, file, remote)
   - Configurable log formatting
   - Easy to extend without modifying existing logger code

6) Design Vending Machine
   - Support multiple items with prices and quantities
   - Accept different payment types
   - Handle states (idle, selecting, payment, dispensing)
   - Clear separation of responsibilities
   - Design should handle state transitions cleanly

7) Design Elevator System (Simplified)
   - Multiple elevators and floors
   - Handle external (floor) and internal (elevator) requests
   - Elevator selection strategy
   - Movement and direction handling
   - Design should be extensible for future optimizations

8) Design Rate Limiter (In-Memory, Single Node)
   - Limit requests per user or client
   - Support different limiting strategies
   - Configurable limits and time windows
   - Clean API for checking and consuming requests
   - Easy to extend with new algorithms

*/