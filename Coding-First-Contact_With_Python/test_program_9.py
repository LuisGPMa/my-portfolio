integer= int(input("type a number: "))
if integer%3==0 and integer%2==0:
    print ("BOTH")
elif integer%3==0 or integer%2==0:
    print("ONE")
else:
    print('NEITHER')
    
