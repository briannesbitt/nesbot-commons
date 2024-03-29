package com.nesbot.commons.collections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ConcurrentOrderedMap<K, V>
{
   private final ArrayList<V> values = new ArrayList<V>();
   private final HashMap<K, Integer> keysToIndex = new HashMap<K, Integer>();
   private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
   private final ReentrantReadWriteLock.ReadLock rLock = rwLock.readLock();
   private final ReentrantReadWriteLock.WriteLock wLock = rwLock.writeLock();

   public int size()
   {
      rLock.lock();
      try
      {
         return values.size();
      }
      finally
      {
         rLock.unlock();
      }
   }

   public boolean append(K key, V value)
   {
      if (key == null || value == null)
      {
         throw new NullPointerException("null key and/or value is not allowed");
      }
      wLock.lock();
      try
      {
         if (keysToIndex.containsKey(key))
         {
            return false;
         }
         values.add(value);
         keysToIndex.put(key, values.size() - 1);
         return true;
      }
      finally
      {
         wLock.unlock();
      }
   }
   // Complexity O(1)
   public V get(K key)
   {
      rLock.lock();
      try
      {
         Integer i = keysToIndex.get(key);
         return (i == null) ? null : values.get(i);
      }
      finally
      {
         rLock.unlock();
      }
   }
   // Complexity O(1)
   public V get(int index)
   {
      rLock.lock();
      try
      {
         return values.get(index);
      }
      finally
      {
         rLock.unlock();
      }
   }
   // Complexity O(1)
   public int indexOf(K key)
   {
      rLock.lock();
      try
      {
         Integer index = keysToIndex.get(key);
         return (index == null) ? -1 : index;
      }
      finally
      {
         rLock.unlock();
      }
   }
   // Complexity O(1)
   public boolean contains(K key)
   {
      return indexOf(key) >= 0;
   }

   @SuppressWarnings("unchecked")
   public List<V> list()
   {
      rLock.lock();
      try
      {
         return (List<V>) values.clone();
      }
      finally
      {
         rLock.unlock();
      }
   }
   public Object[] values()
   {
      rLock.lock();
      try
      {
         return values.toArray();
      }
      finally
      {
         rLock.unlock();
      }
   }

   public V remove(K key)
   {
      wLock.lock();
      try
      {
         Integer index = keysToIndex.remove(key);
         if (index == null)
         {
            return null;
         }

         V removed = values.remove(index.intValue());

         // must update the indexes of the remaining keys only if they were more than the index that was removed.
         for (Map.Entry<K, Integer> entry : keysToIndex.entrySet())
         {
            if (entry.getValue() > index)
            {
               entry.setValue(entry.getValue() - 1);
            }
         }

         return removed;
      }
      finally
      {
         wLock.unlock();
      }
   }
   public void clear()
   {
      wLock.lock();
      try
      {
         keysToIndex.clear();
         values.clear();
      }
      finally
      {
         wLock.unlock();
      }
   }
}