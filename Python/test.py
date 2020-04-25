import json
import pandas as pd
import numpy as np
import csv

def open_json(path):
    with open(path) as file:
        hashmap = json.load(file)
    return hashmap

#crime_data = pd.read_csv("Data/CrimeData.csv")
#general_data = pd.read_csv("Data/All_Intersections.csv")a
#crime_list = open_json("CrimeTypesData.json")
#crime_dates = open_json("CrimeDates.json")

all_data = open_json("JSON/FinalIntersectionData.json")

summ = 0
maximum = float("-inf")
maximum_key = ""
for key, value in all_data.items():
    second_street = value
    for key2, value2 in second_street.items():
        if value2.get("Num_Crimes") > maximum:
            maximum = value2.get("Num_Crimes")
            maximum_key = key2
        summ += value2.get("Num_Crimes")


print(summ) # Finds all the number of crimes
print(maximum) # Finds maximum number of crimes associated with an intersection
print(maximum_key)
