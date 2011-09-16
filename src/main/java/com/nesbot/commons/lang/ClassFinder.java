package com.nesbot.commons.lang;

import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.StringTokenizer;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassFinder
{
   private static String EXT = ".class";
   private String _packageNamePrefix;
   private String _classNamePrefix;

   private ClassFinder(String packageNamePrefix, String classNamePrefix)
   {
      _packageNamePrefix = packageNamePrefix;
      _classNamePrefix = classNamePrefix;
   }
   public static List<Class<?>> findClasses(String packageNamePrefix, String classNamePrefix)
   {
      return new ClassFinder(packageNamePrefix, classNamePrefix).findClasses();
   }
   public List<Class<?>> findClasses()
   {
      List<Class<?>> list = new ArrayList<Class<?>>();

      String classpath = System.getProperty("java.class.path");

      for (String path : Strings.split(classpath, Strings.PS))
      {
         File dir = new File(path);
         if (dir.isDirectory())
         {
            lookInDirectory("", dir, list);
         }
         if (dir.isFile())
         {
            String name = dir.getName().toLowerCase();
            if (name.endsWith(".zip") || name.endsWith(".jar"))
            {
               lookInArchive(dir, list);
            }
         }
      }
      return list;
   }

   private void lookInDirectory(String name, File dir, List<Class<?>> list)
   {
      for (File file : dir.listFiles())
      {
         String fileName = file.getName();
         if (file.isFile() && fileName.startsWith(_classNamePrefix) && fileName.toLowerCase().endsWith(EXT))
         {
            try
            {
               Class c = Class.forName(name + fileName.substring(0, fileName.lastIndexOf(".")));

               if (!c.getPackage().getName().startsWith(_packageNamePrefix))
               {
                  continue;
               }

               list.add(c);
            }
            catch (ClassNotFoundException e)
            {
               System.err.println(Strings.format("Class not found [{0}]", file.getAbsoluteFile()));
            }
         }

         if (file.isDirectory())
         {
            lookInDirectory(name + fileName + ".", file, list);
         }
      }
   }
   private void lookInArchive(File archive, List<Class<?>> list)
   {
      JarFile jarFile = null;
      try
      {
         jarFile = new JarFile(archive);
      }
      catch (IOException e)
      {
         System.err.println("Non fatal error. Unable to read jar item.");
         return;
      }
      Enumeration entries = jarFile.entries();
      JarEntry entry;
      String entryName;
      while (entries.hasMoreElements())
      {
         entry = (JarEntry) entries.nextElement();
         entryName = entry.getName();

         if (!entryName.toLowerCase().endsWith(EXT))
         {
            continue;
         }

         try
         {
            // convert name into java classloader notation
            entryName = entryName.substring(0, entryName.lastIndexOf("."));
            entryName = entryName.replace('/', '.');

            if (!entryName.startsWith(_packageNamePrefix))
            {
               continue;
            }

            Class c = Class.forName(entryName);

            if (!c.getSimpleName().startsWith(_classNamePrefix))
            {
               continue;
            }

            list.add(c);
         }
         catch (ClassNotFoundException e)
         {
            System.err.println("Unable to load resource [" + entryName + "] from file [" + archive.getAbsolutePath() + "].");
         }
      }
   }
}