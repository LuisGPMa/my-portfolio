n=int(input("how many hours have you worked this week?"))
if n < 0 or n > 168:
    print("INVALID")
elif n <= 40:
    print("you have made",n * 8,"dollars this week")
elif n <= 50:
    print ("you have made",(40*8) + ((n-40)*9),"dollars this week")
else:
    print("you have made",(40*8) + (10*9) + ((n-50)*10),"dollars this week")
