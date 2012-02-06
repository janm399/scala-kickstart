import os
import sys
import distutils
import distutils.dir_util
import os.path

def prune(source, destination, extensions):
   for root, dirs, files in os.walk(destination):
      for name in files:
         filename = os.path.join(root, name)
         match = False
         for extension in extensions:
            if filename.endswith(extension):
               match = True
               break
         if match:
              do_prune(filename)

def do_prune(file):
   s = open(file, 'r')
   d = open(file + "-", 'w')
   
   c = True
   for line in s:
       stripped = line.strip()
       if stripped == '//{' or stripped == '<!--{-->':
           c = False
           continue
       if stripped == '//}' or stripped == '<!--}-->' and c == False:
           c = True
           continue
       ir = line.find('//{}')
       if ir != -1:
           line = line[ir + 4:]
       if c:
           d.write(line)
   
   os.rename(file + "-", file)
   print 'pruned ' + file

def main():
   source = 'project'
   destination = 'labs'
   if os.path.exists(destination):
      if '-D' in sys.argv:
          distutils.dir_util.remove_tree(destination)
          print 'Removed destination.'
      else:
         print 'Destination exists. Delete first.'
         exit(0)
   distutils.dir_util.copy_tree(source, destination)
   prune('project', 'labs', ['java', 'xml', 'jsp'])
   os.chdir(destination)
   os.execvp('mvn', ['mvn', 'clean'])

main()