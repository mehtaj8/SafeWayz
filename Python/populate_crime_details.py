import json
import csv
import pandas as pd

def open_json(path):
    with open(path) as file:
        hashmap = json.load(file)
    return hashmap

crime_data = pd.read_csv("Data/CrimeData.csv")
crime_list = open_json("JSON/CrimeTypesData.json")
crime_dates = open_json("JSON/CrimeDates.json")
hashtable = open_json("JSON/MergedIntersectionData.json") # Merged Intersections Data Set

# Custom Format Hash
third_hashtable = {"Longitude": 0, "Latitude": 0, "Num_Crimes": 0, "Crime_Weight": 0, "Crime_Recency": 0, "Intersection_Crime_Weight": 0, "Crime_Per_Year": crime_dates, "Crime_List": crime_list}

for index, row in crime_data.iterrows():
    address = row["Address"]
    if "/" in address: # Some of the "Address" values from the csv are actual house numbers, but we want intersections. Intersections are seperated by "/"
        streets_array = address.split("/")
        first_st = streets_array[0].strip() # Removes spaces before and after because I'm performing literal string matching, spaces make "a " vs "a" == different
        second_st = streets_array[1].strip()
        
        hashtable[first_st][second_st]["Longitude"] = float(row["X"])
        hashtable[first_st][second_st]["Latitude"] = float(row["Y"])

        hashtable[first_st][second_st]["Num_Crimes"] += 1 # Increments value by 1

        year_date = (((row["Dates"].split(" "))[0]).split("-"))[0]
        hashtable[first_st][second_st]["Crime_Per_Year"][year_date] += 1

        crime_type = row["Category"]
        hashtable[first_st][second_st]["Crime_List"][crime_type] += 1

        print(index, "\t", hashtable[first_st][second_st]["Num_Crimes"])

print("Generating JSON")
with open ("JSON/FinalIntersectionData.json", "w") as file:
    json.dump(hashtable, file)