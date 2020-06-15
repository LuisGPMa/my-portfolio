squareside = int(input ("What is the square's side measurement? "))
squareperimeter = squareside * 4
squarearea = squareside**(2)
squarediagonal = squareside * 2 **(0.5)
answers=[squareperimeter, squarearea, squarediagonal]
print ("perimeter=", answers[0])
print ("area=", answers[1], "²")
print ("diagonal=", answers[2])
