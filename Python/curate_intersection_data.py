import json
import pandas as pd
import numpy as np
import csv

# Function to read JSON Files
def open_json(path): 
    with open(path) as file:
        hashmap = json.load(file)
    return hashmap

crime_data = pd.read_csv("Data/CrimeData.csv")
general_data = pd.read_csv("Data/All_Intersections.csv")

crime_list = open_json("JSON/CrimeTypesData.json")
crime_dates = open_json("JSON/CrimeDates.json")

# Understand data
'''
print(crime_data)
print(general_data)
for index, row in general_data.iterrows():
    print(general_data["to_st"])
'''

# Filter General Data
print("Retrieving Valid Intersections")
grab_st_blanks = (general_data.loc[general_data["to_st"].isnull()])
grab_st_blanks = grab_st_blanks.drop_duplicates("from_st")

# Initialize hashtable
hashtable = {}
third_hashtable = {"Longitude": 0, "Latitude": 0, "Num_Crimes": 0, "Crime_Weight": 0, "Crime_Recency": 0, "Intersection_Crime_Weight": 0, "Crime_Per_Year": crime_dates, "Crime_List": crime_list}

print("Filling hashtable")
for index, row in grab_st_blanks.iterrows():
    first_st = row["streetname"].strip()
    second_st = row["from_st"]

    # Some entries in "from_st" are seperated by \\
    # Those are valid entries & intersections
    if "\\" in second_st:       
        second_st_array = second_st.split("\\") # Split
        second_st_A = second_st_array[0].strip()
        second_st_B = second_st_array[1].strip()

        #second_hashtableA = {second_st_array[0].strip(): third_hashtable} # Since each are valid intersections, create two intersection entries
        #second_hashtableB = {second_st_array[1].strip(): third_hashtable}

        # On first occurence of main_st, initialize hash sequence
        if hashtable.get(first_st) == None: 
            hashtable[first_st] = {}
            hashtable[first_st][second_st_A] = third_hashtable
            hashtable[first_st][second_st_B] = third_hashtable
        else:
            # On 1+ occurence of main_st, simply append to hash sequence
            hashtable[first_st][second_st_A] = third_hashtable
            hashtable[first_st][second_st_B] = third_hashtable
    else:       
        second_hashtable = {second_st: third_hashtable}
        if hashtable.get(first_st) == None:
            hashtable[first_st] = {}
            hashtable[first_st][second_st] = third_hashtable
        else:
            hashtable[first_st][second_st] = third_hashtable

# Check data
'''
print(hashtable.get("01ST ST"))
print(len(hashtable))

for key, value in hashtable.items():
    print(grab_st_blanks["from_st"].value_counts(), len(value))
'''
# Output JSON
print("Generating JSON")
with open ("JSON/GeneralIntersectionData.json", "w") as file:
    json.dump(hashtable, file)







