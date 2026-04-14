rates = {
    "Economy": 10,
    "Premium": 18,
    "SUV": 25
}

def calculate_fare(km, vehicle_type, hour):

    if vehicle_type not in rates:
        return "Service Not Available"

    base_fare = km * rates[vehicle_type]

    if 17 <= hour <= 20:
        base_fare *= 1.5

    return base_fare


km = float(input("Enter distance (km): "))
vehicle_type = input("Enter vehicle type (Economy/Premium/SUV): ")
hour = int(input("Enter hour (0-23): "))
fare = calculate_fare(km, vehicle_type, hour)
if fare == "Service Not Available":
    print(fare)
else:
    print("\n--- Price Receipt ---")
    print("Distance:", km, "km")
    print("Vehicle:", vehicle_type)
    print("Hour:", hour)
    print("Total Fare:", round(fare, 2))