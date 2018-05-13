''' An auxiliar script to make it easier to map keywords in tokens using JFlex.

Input example:
  break        default      func         interface    select
case         defer        go           map          struct
chan         else         goto         package      switch
const        fallthrough  if           range        type
continue     for          import       return       var
'''
syntax = []

while True:
  try:
    tokens = input().split()
  except:
    break
  
  print()
  for t in tokens:
    print(('"%s"' % t).ljust(30) + '{ return symbol(%s, "%s"); }' % (t.upper(), t))
    syntax.append(t.upper())

print('terminal ' + ', '.join(syntax))
