celsius= float(input ("What's the temperature in Celsius? "))
fahreinheit= ((celsius*9)/5)+32
print (celsius,"º in celsius is the same temperature as", fahreinheit, "º in fahreinheit.")
if fahreinheit>90:
    print("It's hot")
else:
    print("It's not hot")
