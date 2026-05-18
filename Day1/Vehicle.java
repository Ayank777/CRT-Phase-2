import java.util.*;

abstract class Vehicle {
    String number;

    Vehicle(String number) {
        this.number = number;
    }

    abstract int calculateFee(int hours);
}

class Car extends Vehicle {
    Car(String number) {
        super(number);
    }

    @Override
    int calculateFee(int hours) {
        return hours * 20;
    }
}

class ParkingSlot {
    int slotNumber;
    Vehicle vehicle;

    ParkingSlot(int slotNumber) {
        this.slotNumber = slotNumber;
    }

    boolean isEmpty() {
        return vehicle == null;
    }
}

class ParkingLot {
    ArrayList<ParkingSlot> slots = new ArrayList<>();

    ParkingLot(int size) {
        for (int i = 1; i <= size; i++) {
            slots.add(new ParkingSlot(i));
        }
    }

    void parkVehicle(Vehicle vehicle) {
        for (ParkingSlot slot : slots) {
            if (slot.isEmpty()) {
                slot.vehicle = vehicle;
                System.out.println(vehicle.number + " parked at slot " + slot.slotNumber);
                return;
            }
        }

        System.out.println("Parking lot is full");
    }

    public static void main(String[] args) {
        ParkingLot lot = new ParkingLot(2);

        Vehicle car1 = new Car("MH01AB1234");
        Vehicle car2 = new Car("MH02CD5678");
        Vehicle car3 = new Car("MH03EF9999");

        lot.parkVehicle(car1);
        lot.parkVehicle(car2);
        lot.parkVehicle(car3);
    }
}