seconds=int(input("write a number of seconds: "))
years=seconds//(365*24*60*60)
leftover_secs_1%(24*60*60)
days=seconds//(24*60*60)
leftover_secs_2=seconds%(24*60*60)
hours=leftover_secs_2//(60*60)
leftover_secs_3=leftover_secs_2%(60*60)
minutes=leftover_secs_3//60
seconds=leftover_secs_3%60
print(years,"years",days,"days",hours,"hours",minutes,"minutes",seconds,"seconds")
