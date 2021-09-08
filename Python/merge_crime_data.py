import json
import csv
import pandas as pd

def open_json(path):
    with open(path) as file:
        hashmap = json.load(file)
    return hashmap

crime_data = pd.read_csv("Data/CrimeData.csv")
general_data = pd.read_csv("Data/All_Intersections.csv")

crime_list = open_json("JSON/CrimeTypesData.json")
crime_dates = open_json("JSON/CrimeDates.json")
hashtable = open_json("JSON/GeneralIntersectionData.json")

third_hashtable = {"Longitude": 0, "Latitude": 0, "Num_Crimes": 0, "Crime_Weight": 0, "Crime_Recency": 0, "Intersection_Crime_Weight": 0, "Crime_Per_Year": crime_dates, "Crime_List": crime_list}

# Merge with Crime Data
print(crime_data)

for index, row in crime_data.iterrows():
    address = row["Address"]
    if "/" in address:
        # Grab Addresses and remove spaces, they are treated diff w/ spaces
        address_list = address.split("/")
        first_st = address_list[0].strip()
        second_st = address_list[1].strip()

        # If the 1st street doesn't exist, then give birth and make exist stupid code
        if hashtable.get(first_st) == None:
            hashtable[first_st] = {}

            # If the 2nd street also doesn't exist, make it exst
            if hashtable[first_st].get(second_st) == None:
                hashtable[first_st][second_st] = third_hashtable

        else:
            # If the 1st street exists byt the 2nd doesn't
            if hashtable[first_st].get(second_st) == None:
                hashtable[first_st][second_st] = third_hashtable

        print(index)

# Output JSON
print("Generating JSON")
with open ("JSON/MergedIntersectionData.json", "w") as file:
    json.dump(hashtable, file)



