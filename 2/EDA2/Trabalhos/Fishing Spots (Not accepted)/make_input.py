f = open("input", "w")
f.write("4000 4000\n")
for i in range(1,8001):
    f.write( "{} {} {}\n".format(i,i*2,i))

f.close()


