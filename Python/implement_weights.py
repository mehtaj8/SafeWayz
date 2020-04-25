import json
import csv
import pandas as pd

def open_json(path): # Function to read json files
    with open(path) as file:
        hashmap = json.load(file)
    return hashmap

def get_weight(to_fill_hashtable, data_hashtable): # Function to create weights 
    weight = 0

    for key, count in to_fill_hashtable.items(): # Generates weights for respective hashtable by relying on JSON file of respective weights
        key_weight = data_hashtable.get(key)
        calculated_key_weight = key_weight * count
        weight += calculated_key_weight

    return weight

versionA = open_json("JSON/FinalIntersectionData.json")
crime_weights = open_json("JSON/CrimeWeights.json")
crime_recency_weights = open_json("JSON/CrimeRecencyWeights.json")

counter = 1
for key, val in versionA.items(): # Accesses the main street as key and val as dictionary of connecting streets
    for keyA, valA in val.items(): # Accesses the secondary street as key and its inner details that represents the intersection details (main street / secondary street)
        street = keyA

        valA["Crime_Recency"] = get_weight(valA.get("Crime_Per_Year"), crime_recency_weights)
        valA["Crime_Weight"] = get_weight(valA.get("Crime_List"), crime_weights)
        valA["Intersection_Crime_Weight"] = ((valA["Crime_Recency"] * valA["Crime_Weight"]) //2) * valA["Num_Crimes"]
        print(counter)
    counter += 1

print("Generating JSON")
with open ("JSON/WeightedData.json", "w") as file:
    json.dump(versionA, file)



